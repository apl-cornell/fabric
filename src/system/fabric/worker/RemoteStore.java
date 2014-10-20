package fabric.worker;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import fabric.common.Crypto;
import fabric.common.Logging;
import fabric.common.ONumConstants;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.TransactionID;
import fabric.common.VersionWarranty;
import fabric.common.WarrantyGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.FabricGeneralSecurityException;
import fabric.common.exceptions.FabricRuntimeException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.exceptions.RuntimeFetchException;
import fabric.common.net.SubSocketFactory;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.dissemination.WarrantyGlob;
import fabric.lang.Object;
import fabric.lang.Object._Impl;
import fabric.lang.security.NodePrincipal;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.AllocateMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.DissemReadMessage;
import fabric.messages.GetCertChainMessage;
import fabric.messages.MakePrincipalMessage;
import fabric.messages.Message;
import fabric.messages.Message.NoException;
import fabric.messages.Message.Response;
import fabric.messages.PrepareTransactionReadsMessage;
import fabric.messages.PrepareTransactionWritesMessage;
import fabric.messages.ReadMessage;
import fabric.messages.StalenessCheckMessage;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;
import fabric.util.Map;

/**
 * Encapsulates a Store. This class maintains two connections to the store (one
 * with SSL, for worker requests; and one without, for dissemination requests)
 * and manages all communication. Stores can only be obtained through the
 * <code>Worker.getStore()</code> interface. For each remote store, there should
 * be at most one <code>RemoteStore</code> object representing that store.
 */
public class RemoteStore extends RemoteNode<RemoteStore> implements Store,
    Serializable {
  /**
   * A queue of fresh object identifiers.
   */
  private transient final Queue<Long> fresh_ids;

  /**
   * The object table: locally resident objects.
   */
  private transient final ObjectCache cache;

  /**
   * The set of fetch locks. Used to prevent threads from concurrently
   * attempting to fetch the same object.
   */
  private transient final ConcurrentLongKeyMap<FetchLock> fetchLocks;

  /**
   * The store's public SSL key. Used for verifying signatures on object groups.
   * This is null until getPublicKey() is called.
   */
  private transient PublicKey publicKey;

  private static class FetchLock {
    private volatile ObjectCache.Entry object;
    private volatile AccessException error;
  }

  /**
   * Creates a store representing the store at the given host name.
   */
  protected RemoteStore(String name) {
    super(name);

    this.cache = new ObjectCache(name);
    this.fetchLocks = new ConcurrentLongKeyHashMap<>();
    this.fresh_ids = new LinkedList<>();
    this.publicKey = null;
  }

  /**
   * Creates a store representing the store at the given host name.
   */
  protected RemoteStore(String name, PublicKey key) {
    this(name);
    this.publicKey = key;
  }

  @Override
  protected <R extends Response, E extends FabricException> R send(
      SubSocketFactory<RemoteStore> subSocketFactory, Message<R, E> message)
      throws E {
    return super.send(subSocketFactory, message);
  }

  @Override
  public long createOnum() throws UnreachableNodeException {
    synchronized (fresh_ids) {
      try {
        reserve(1);
      } catch (AccessException e) {
        throw new FabricRuntimeException(e);
      }
      return fresh_ids.poll();
    }
  }

  @Override
  public long prepareTransactionWrites(long tid,
      Collection<Object._Impl> toCreate, Collection<Object._Impl> writes)
      throws TransactionPrepareFailedException, UnreachableNodeException {
    PrepareTransactionWritesMessage.Response response =
        send(Worker.getWorker().authToStore,
            new PrepareTransactionWritesMessage(tid, toCreate, writes));

    return response.minCommitTime;
  }

  @Override
  public LongKeyMap<VersionWarranty> prepareTransactionReads(long tid,
      boolean readOnly, LongKeyMap<Integer> reads, long commitTime)
      throws TransactionPrepareFailedException, UnreachableNodeException {
    PrepareTransactionReadsMessage.Response response =
        send(
            Worker.getWorker().authToStore,
            new PrepareTransactionReadsMessage(tid, readOnly, reads, commitTime));
    return response.newWarranties;
  }

  @Override
  public final ObjectCache.Entry readObject(long onum) throws AccessException {
    return readObject(true, onum);
  }

  @Override
  public final ObjectCache.Entry readObjectNoDissem(long onum)
      throws AccessException {
    return readObject(false, onum);
  }

  private final ObjectCache.Entry readObject(boolean useDissem, long onum)
      throws AccessException {
    // Get/create a mutex for fetching the object.
    FetchLock fetchLock = new FetchLock();
    FetchLock existingFetchLock = fetchLocks.putIfAbsent(onum, fetchLock);
    boolean needToFetch = true;
    if (existingFetchLock != null) {
      fetchLock = existingFetchLock;
      needToFetch = false;
    }

    if (needToFetch) {
      // We are responsible for fetching the object.

      // Check object table in case some other thread had just finished
      // fetching the object while we weren't looking.
      fetchLock.object = readFromCache(onum);

      if (fetchLock.object == null) {
        // Really need to fetch.
        try {
          fetchLock.object = fetchObject(useDissem, onum);
        } catch (AccessException e) {
          fetchLock.error = e;
        }
      }

      // Object now cached. Remove our mutex from fetchLocks.
      fetchLocks.remove(onum, fetchLock);

      // Signal any other threads that might be waiting for our fetch.
      synchronized (fetchLock) {
        fetchLock.notifyAll();
      }
    } else {
      // Wait for another thread to fetch the object for us.
      synchronized (fetchLock) {
        while (fetchLock.object == null && fetchLock.error == null) {
          try {
            fetchLock.wait();
          } catch (InterruptedException e) {
            Logging.logIgnoredInterruptedException(e);
          }
        }
      }
    }

    if (fetchLock.error != null) throw fetchLock.error;
    return fetchLock.object;
  }

  @Override
  public ObjectCache.Entry readFromCache(long onum) {
    return cache.get(onum);
  }

  /**
   * Fetches the object from the store. Places the object in the object cache
   * and returns the resulting cache entry.
   *
   * @param useDissem
   *          Whether to use the dissemination network. If false, the
   *          dissemination network will be bypassed.
   * @param onum
   *          The object number to fetch
   * @return The cache entry for the object.
   */
  private ObjectCache.Entry fetchObject(boolean useDissem, long onum)
      throws AccessException {
    Pair<ObjectGroup, WarrantyGroup> g;
    if (useDissem) {
      g = Worker.getWorker().fetchManager().fetch(this, onum);
      if (g == null) throw new AccessException(this, onum);
    } else {
      g = readObjectFromStore(onum);
    }

    return cache.put(this, g, onum);
  }

  /**
   * Goes to the store to get object.
   *
   * @param onum
   *          The object number to fetch
   * @return An ObjectGroup whose head object is the requested object.
   * @throws FetchException
   *           if there was an error while fetching the object from the store.
   */
  public Pair<ObjectGroup, WarrantyGroup> readObjectFromStore(long onum)
      throws AccessException {
    ReadMessage.Response response =
        send(Worker.getWorker().authToStore, new ReadMessage(onum));
    return response.group;
  }

  /**
   * Called by dissemination to fetch an encrypted object from the store.
   *
   * @param onum
   *          The object number to fetch.
   */
  public Pair<ObjectGlob, WarrantyGlob> readEncryptedObjectFromStore(long onum)
      throws AccessException {
    DissemReadMessage.Response response =
        send(Worker.getWorker().unauthToStore, new DissemReadMessage(onum));

    PublicKey key = getPublicKey();
    try {
      response.globs.first.verifySignature(key);
      if (response.globs.second != null) {
        response.globs.second.verifySignature(key);
      }
    } catch (GeneralSecurityException e) {
      return null;
    }
    return response.globs;
  }

  /**
   * Looks up the actual Store object when this store is deserialized. While
   * this method is not explicitly called in the code, it is used by the Java
   * serialization framework when deserializing a Store object.
   *
   * @return The canonical Store object corresponding to this Store's onum
   * @throws ObjectStreamException
   */
  public java.lang.Object readResolve() {
    return Worker.getWorker().getStore(name);
  }

  /**
   * Ensure that a given number of objects can be created without contacting the
   * store.
   *
   * @param num
   *          The number of objects to allocate
   */
  protected void reserve(int num) throws AccessException,
      UnreachableNodeException {
    synchronized (fresh_ids) {
      while (fresh_ids.size() < num) {
        // log.info("Requesting new onums, storeid=" + storeID);
        if (num < 512) num = 512;
        AllocateMessage.Response response =
            send(Worker.getWorker().authToStore, new AllocateMessage(num));

        for (long oid : response.oids)
          fresh_ids.add(oid);
      }
    }
  }

  @Override
  public void abortTransaction(TransactionID tid) throws AccessException {
    send(Worker.getWorker().authToStore, new AbortTransactionMessage(tid));
  }

  @Override
  public void commitTransaction(long transactionID, long commitTime)
      throws UnreachableNodeException, TransactionCommitFailedException {
    send(Worker.getWorker().authToStore, new CommitTransactionMessage(
        transactionID, commitTime));
  }

  @Override
  public boolean checkForStaleObjects(LongKeyMap<Integer> reads) {
    List<Pair<SerializedObject, VersionWarranty>> staleObjects =
        getStaleObjects(reads);

    for (Pair<SerializedObject, VersionWarranty> pair : staleObjects)
      updateCache(pair);

    return !staleObjects.isEmpty();
  }

  /**
   * Helper for checkForStaleObjects.
   */
  protected List<Pair<SerializedObject, VersionWarranty>> getStaleObjects(
      LongKeyMap<Integer> reads) {
    try {
      return send(Worker.getWorker().authToStore, new StalenessCheckMessage(
          reads)).staleObjects;
    } catch (final AccessException e) {
      throw new RuntimeFetchException(e);
    }
  }

  @Override
  public String toString() {
    return "Store@" + name;
  }

  @Override
  public Map getRoot() {
    return new Map._Proxy(this, ONumConstants.ROOT_MAP);
  }

  @Override
  public NodePrincipal getPrincipal() {
    return new NodePrincipal._Proxy(this, ONumConstants.STORE_PRINCIPAL);
  }

  @Override
  public final boolean isLocalStore() {
    return false;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public void evict(long onum) {
    cache.evict(onum);
  }

  /**
   * Updates the worker's cache of objects that originate from this store. If an
   * object with the given onum exists in cache, it is evicted and replaced with
   * the given serialized object; any transactions using the object are aborted
   * and retried. If the object does not exist in cache, then the cache is not
   * updated.
   */
  public void updateCache(Pair<SerializedObject, VersionWarranty> update) {
    cache.update(this, update);
  }

  /**
   * Adds the given object to the cache. If a cache entry already exists, it is
   * replaced. Any transactions currently using the object are aborted and
   * retried.
   */
  void forceCache(Pair<SerializedObject, VersionWarranty> obj) {
    cache.forcePut(this, obj);
  }

  /**
   * Updates the worker cache with the given object, as follows:
   * <ul>
   * <li>If the cache contains a deserialized copy of an old version of the
   * object, then that old version is replaced with a serialized copy of the new
   * version. Transactions using the object are aborted and retried.
   * <li>If the cache contains a serialized copy of an old version of the
   * object, then that old version is evicted.
   * </ul>
   *
   * @return true iff after this update operation, the cache contains the
   *     object.
   */
  boolean updateOrEvict(SerializedObject obj) {
    return cache.updateOrEvict(this, obj);
  }

  /**
   * Updates the worker's cache with the given set of warranties.
   * 
   * @return the set of onums for which a cache entry was found.
   */
  public List<Long> updateWarranties(WarrantyGroup warranties) {
    return cache.update(this, warranties);
  }

  @Override
  public void cache(_Impl impl) {
    FabricSoftRef ref = impl.$ref;
    if (ref.store != this)
      throw new InternalError("Caching object at wrong store");

    cache.put(impl);
  }

  @Override
  public ObjectCache.Entry cache(Pair<SerializedObject, VersionWarranty> obj) {
    return cache.put(this, obj);
  }

  /**
   * @return The store's public key for verifying Glob signatures.
   */
  public PublicKey getPublicKey() {
    if (publicKey == null) {
      // No key cached. Fetch the certificate chain from the store.
      GetCertChainMessage.Response response;
      try {
        response =
            send(Worker.getWorker().authToStore, new GetCertChainMessage());
      } catch (NoException e) {
        // This is not possible.
        throw new InternalError(e);
      }
      Certificate[] certificateChain = response.certificateChain;

      // Validate the certificate chain.
      try {
        Crypto.validateCertificateChain(certificateChain,
            Worker.instance.config.getKeyMaterial().getTrustedCerts());
        publicKey = certificateChain[0].getPublicKey();
      } catch (GeneralSecurityException e) {
        // do nothing
      }
    }
    return publicKey;
  }

  /**
   * Clears the worker's cache for this store. To be used for (performance)
   * testing only.
   *
   * @see fabric.worker.Worker#clearCache()
   */
  public void clearCache() {
    cache.clear();
  }

  /**
   * Returns a certificate chain for a new principal object for the given worker
   * key. This certificate chain is not guaranteed to end in a trusted root.
   */
  public X509Certificate[] makeWorkerPrincipal(Worker worker,
      PublicKey workerKey) {
    MakePrincipalMessage.Response response;
    try {
      response =
          send(worker.unauthToStore, new MakePrincipalMessage(workerKey));
    } catch (FabricGeneralSecurityException e) {
      throw new NotImplementedException();
    }

    X509Certificate cert = response.cert;

    // Check that the top certificate in the chain satisfies the following:
    // - signed by the store
    // - contains the worker's key

    Principal issuerDN = cert.getIssuerDN();
    // XXX This next line is really hacky.
    if (!name.equals(Crypto.getCN(issuerDN.getName()))) {
      throw new InternalError("Certificate signer (" + issuerDN.getName()
          + ") does not match store (" + name + ")");
    }

    if (!cert.getPublicKey().equals(workerKey)) {
      throw new InternalError("Key in certificate does not match worker key");
    }

    X509Certificate[] result =
        new X509Certificate[response.certChain.length + 1];
    result[0] = cert;
    for (int i = 0; i < response.certChain.length; i++) {
      result[i + 1] = response.certChain[i];
    }

    return result;
  }

  // ////////////////////////////////
  // Java custom-serialization gunk
  // ////////////////////////////////

  private java.lang.Object writeReplace() {
    return new SerializationProxy(name);
  }

  protected static final class SerializationProxy implements Serializable {
    private final String storeName;

    public SerializationProxy(String storeName) {
      this.storeName = storeName;
    }

    java.lang.Object readResolve() {
      return Worker.getWorker().getStore(storeName);
    }
  }

}
