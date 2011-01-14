package fabric.worker;

import java.io.ObjectStreamException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import fabric.common.*;
import fabric.common.exceptions.*;
import fabric.common.exceptions.InternalError;
import fabric.common.util.*;
import fabric.dissemination.Glob;
import fabric.lang.Object;
import fabric.lang.Object._Impl;
import fabric.lang.security.NodePrincipal;
import fabric.messages.*;
import fabric.messages.Message.NoException;
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
public class RemoteStore extends RemoteNode implements Store {
  /**
   * A queue of fresh object identifiers.
   */
  private transient final Queue<Long> fresh_ids;

  /**
   * The object table: locally resident objects.
   */
  private transient final LongKeyMap<FabricSoftRef> objects;

  /**
   * The set of fetch locks. Used to prevent threads from concurrently
   * attempting to fetch the same object.
   */
  private transient final LongKeyMap<FetchLock> fetchLocks;

  /**
   * The store's public SSL key. Used for verifying signatures on object groups.
   * This is null until getPublicKey() is called.
   */
  private transient PublicKey publicKey;

  /**
   * Cache of serialized objects that the store has sent us.
   */
  private transient final LongKeyMap<SerializedObjectSoftRef> serialized;
  final transient ReferenceQueue<SerializedObject> serializedRefQueue;

  /**
   * A thread for removing entries from <code>serialized</code> as
   * SerializedObjects are collected from memory.
   */
  private transient final SerializedCollector collector;
  
  private class SerializedCollector extends Thread {
    private final ReferenceQueue<SerializedObject> queue;

    SerializedCollector() {
      super("Serialized object collector for store " + name);
      setDaemon(true);
      queue = new ReferenceQueue<SerializedObject>();
    }

    @Override
    public void run() {
      while (true) {
        try {
          SerializedObjectSoftRef ref =
              (SerializedObjectSoftRef) queue.remove();

          synchronized (serialized) {
            serialized.remove(ref.onum);
          }
        } catch (InterruptedException e) {
        }
      }
    }
  }

  private class FetchLock {
    private _Impl object;
    private AccessException error;
  }

  /**
   * Creates a store representing the store at the given host name.
   */
  protected RemoteStore(String name) {
    super(name);
    
    this.objects = new LongKeyHashMap<FabricSoftRef>();
    this.fetchLocks = new LongKeyHashMap<FetchLock>();
    this.fresh_ids = new LinkedList<Long>();
    this.serialized = new LongKeyHashMap<SerializedObjectSoftRef>();
    this.publicKey = null;
    this.serializedRefQueue = new ReferenceQueue<SerializedObject>();
    this.collector = new SerializedCollector();
    this.collector.start();
  }
  
  /**
   * Creates a store representing the store at the given host name.
   */
  protected RemoteStore(String name, PublicKey key) {
    this(name);
    this.publicKey = key;
  }

  public synchronized long createOnum() throws UnreachableNodeException {
    try {
      reserve(1);
    } catch (AccessException e) {
      throw new FabricRuntimeException(e);
    }
    return fresh_ids.poll();
  }

  /**
   * Sends a PREPARE message to the store.
   */
  public boolean prepareTransaction(long tid,
                                    long commitTime,
                                    Collection<Object._Impl> toCreate,
                                    LongKeyMap<Integer> reads,
                                    Collection<Object._Impl> writes)
          throws TransactionPrepareFailedException,
                 UnreachableNodeException {
    PrepareTransactionMessage.Response response =
        send(Worker.getWorker().authToStore, new PrepareTransactionMessage(tid,
            commitTime, toCreate, reads, writes));

    return response.subTransactionCreated;
  }

  /**
   * Returns the requested _Impl object. If the object is not resident, it is
   * fetched from the Store via dissemination.
   * 
   * @param onum
   *          The identifier of the requested object
   * @return The requested object
   * @throws FabricException
   */
  public final Object._Impl readObject(long onum) throws AccessException {
    return readObject(true, onum);
  }

  /*
   * (non-Javadoc)
   * @see fabric.worker.Store#readObjectNoDissem(long)
   */
  public final Object._Impl readObjectNoDissem(long onum)
      throws AccessException {
    return readObject(false, onum);
  }

  private final Object._Impl readObject(boolean useDissem, long onum)
      throws AccessException {
    // Intercept reads of global constants and redirect them to the local store.
    if (ONumConstants.isGlobalConstant(onum))
      return Worker.instance.localStore.readObject(onum);

    // check object table. Use fetchlocks as a mutex for atomically checking the
    // cache and creating a mutex for the object fetch in the event of a cache
    // miss.
    FetchLock fetchLock;
    boolean needToFetch = false;
    synchronized (fetchLocks) {
      Object._Impl result = readObjectFromCache(onum);
      if (result != null) return result;

      // Object not found in cache. Get/create a mutex for fetching the object.
      fetchLock = fetchLocks.get(onum);
      if (fetchLock == null) {
        needToFetch = true;
        fetchLock = new FetchLock();
        fetchLocks.put(onum, fetchLock);
      }
    }

    synchronized (fetchLock) {
      if (needToFetch) {
        // We are responsible for fetching the object.
        try {
          fetchLock.object = fetchObject(useDissem, onum);
        } catch (AccessException e) {
          fetchLock.error = e;
        }

        // Object now cached. Remove our mutex from fetchLocks.
        synchronized (fetchLocks) {
          fetchLocks.remove(onum);
        }

        // Signal any other threads that might be waiting for our fetch.
        fetchLock.notifyAll();
      } else {
        // Wait for another thread to fetch the object for us.
        while (fetchLock.object == null && fetchLock.error == null) {
          try {
            fetchLock.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }

      if (fetchLock.error != null) throw fetchLock.error;
      return fetchLock.object;
    }
  }

  public Object._Impl readObjectFromCache(long onum) {
    synchronized (objects) {
      FabricSoftRef ref = objects.get(onum);
      if (ref == null) return null;
      return ref.get();
    }
  }

  /**
   * Fetches the object from the serialized cache, or goes to the store if it is
   * not present. Places the result in the object cache.
   * 
   * @param useDissem
   *          Whether to use the dissemination network. If false, the
   *          dissemination network will be bypassed.
   * @param onum
   *          The object number to fetch
   * @return The constructed _Impl
   * @throws FabricException
   */
  private Object._Impl fetchObject(boolean useDissem, long onum)
      throws AccessException {
    Object._Impl result = null;
    SoftReference<SerializedObject> serialRef;
    // Lock the table to keep the serialized-reference collector from altering
    // it.
    synchronized (serialized) {
      serialRef = serialized.remove(onum);
    }

    if (serialRef != null) {
      SerializedObject serial = serialRef.get();
      if (serial != null) result = serial.deserialize(this);
    }

    if (result == null) {
      // no serial copy --- fetch from the network.
      ObjectGroup g;
      if (useDissem) {
        g = Worker.getWorker().fetchManager().fetch(this, onum);
      } else {
        g = readObjectFromStore(onum);
      }

      // Lock the table to keep the serialized-reference collector from altering
      // it.
      synchronized (serialized) {
        for (LongKeyMap.Entry<SerializedObject> entry : g.objects().entrySet()) {
          long curOnum = entry.getKey();
          SerializedObject curObj = entry.getValue();

          if (curOnum == onum) {
            result = curObj.deserialize(this);
          } else {
            // Add to the cache if object not already in memory.
            serialized.put(entry.getKey(), new SerializedObjectSoftRef(this,
                entry.getValue()));
          }
        }
      }
    }

    while (result instanceof Surrogate) {
      // XXX Track surrogates for reuse?
      Surrogate surrogate = (Surrogate) result;
      if (useDissem)
        result = surrogate.store.readObject(surrogate.onum);
      else result = surrogate.store.readObjectNoDissem(surrogate.onum);
    }

    synchronized (objects) {
      objects.put(onum, result.$ref);
    }

    return result;
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
  public ObjectGroup readObjectFromStore(long onum) throws AccessException {
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
  public final Glob readEncryptedObjectFromStore(long onum)
      throws AccessException {
    DissemReadMessage.Response response =
        send(Worker.getWorker().unauthToStore, new DissemReadMessage(onum));
    // TODO
    // PublicKey key = Worker.getWorker().getStore(name).getPublicKey();
    // response.glob.verifySignature(key);
    // return response.glob
    throw new NotImplementedException();
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
  protected void reserve(int num) throws AccessException, UnreachableNodeException {
    while (fresh_ids.size() < num) {
      // log.info("Requesting new onums, storeid=" + storeID);
      if (num < 512) num = 512;
      AllocateMessage.Response response =
          send(Worker.getWorker().authToStore, new AllocateMessage(num));

      for (long oid : response.oids)
        fresh_ids.add(oid);
    }
  }

  /*
   * (non-Javadoc)
   * @see fabric.worker.Store#abortTransaction(long)
   */
  public void abortTransaction(TransactionID tid) throws AccessException {
    send(Worker.getWorker().authToStore, new AbortTransactionMessage(tid));
  }

  /*
   * (non-Javadoc)
   * @see fabric.worker.Store#commitTransaction(int)
   */
  public void commitTransaction(long transactionID)
      throws UnreachableNodeException, TransactionCommitFailedException {
    send(Worker.getWorker().authToStore, new CommitTransactionMessage(
        transactionID));
  }

  public boolean checkForStaleObjects(LongKeyMap<Integer> reads) {
    List<SerializedObject> staleObjects = getStaleObjects(reads);
    
    for (SerializedObject obj : staleObjects)
      updateCache(obj);
    
    return !staleObjects.isEmpty();
  }

  /**
   * Helper for checkForStaleObjects.
   */
  protected List<SerializedObject> getStaleObjects(LongKeyMap<Integer> reads) {
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

  public Map getRoot() {
    return new Map._Proxy(this, ONumConstants.ROOT_MAP);
  }

  public NodePrincipal getPrincipal() {
    return new NodePrincipal._Proxy(this, ONumConstants.STORE_PRINCIPAL);
  }

  public final boolean isLocalStore() {
    return false;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  /**
   * Notifies that an object has been evicted from cache.
   */
  public boolean notifyEvict(long onum) {
    synchronized (objects) {
      FabricSoftRef r = objects.get(onum);

      if (r != null && r.get() == null) {
        objects.remove(onum);
        return true;
      }

      return false;
    }
  }

  public boolean evict(long onum) {
    synchronized (objects) {
      FabricSoftRef r = objects.get(onum);
      if (r == null) return false;
      return r.evict();
    }
  }

  /**
   * Updates the worker's cache of objects that originate from this store. If an
   * object with the given onum exists in cache, it is evicted and the given
   * update is placed in the cache of serialized objects. Otherwise, the cache
   * of serialized objects will only be updated if a pre-existing serialized
   * object exits for the given onum.
   * 
   * @return true iff an _Impl with the given onum was evicted from cache.
   */
  public boolean updateCache(SerializedObject update) {
    long onum = update.getOnum();

    synchronized (objects) {
      synchronized (serialized) {
        boolean evicted = evict(onum);

        if (evicted || serialized.containsKey(onum))
          serialized.put(onum, new SerializedObjectSoftRef(this, update));

        return evicted;
      }
    }
  }

  public void cache(_Impl impl) {
    FabricSoftRef ref = impl.$ref;
    if (ref.store != this)
      throw new InternalError("Caching object at wrong store");

    synchronized (objects) {
      if (objects.get(ref.onum) != null)
        throw new InternalError("Conflicting cache entry");

      objects.put(ref.onum, ref);
    }
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
        Crypto.validateCertificateChain(certificateChain, Worker.instance.config.getKeyMaterial().getTrustedCerts());
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
    synchronized (objects) {
      synchronized (serialized) {
        LongSet onums = new LongHashSet(objects.keySet());
        for (LongIterator it = onums.iterator(); it.hasNext();) {
          evict(it.next());
        }

        serialized.clear();
      }
    }
  }

  /**
   * Returns a certificate chain for a new principal object for the given worker
   * key. This certificate chain is not guaranteed to end in a trusted root.
   */
  public X509Certificate[] makeWorkerPrincipal(PublicKey workerKey) {
    MakePrincipalMessage.Response response;
    try {
      response = send(Worker.getWorker().unauthToStore, new MakePrincipalMessage(
          workerKey));
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
    
    X509Certificate[] result = new X509Certificate[response.certChain.length + 1];
    result[0] = cert;
    for (int i = 0; i < response.certChain.length; i++) {
      result[i + 1] = response.certChain[i];
    }
    
    return result;
  }
}
