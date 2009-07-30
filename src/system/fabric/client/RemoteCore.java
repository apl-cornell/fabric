package fabric.client;

import java.io.ObjectStreamException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.security.PublicKey;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import fabric.common.*;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.FetchException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.dissemination.Glob;
import fabric.lang.NodePrincipal;
import fabric.lang.Object;
import fabric.lang.Object._Impl;
import fabric.messages.*;
import fabric.util.Map;

/**
 * Encapsulates a Core. This class maintains two connections to the core (one
 * with SSL, for client requests; and one without, for dissemination requests)
 * and manages all communication. Cores can only be obtained through the
 * <code>Client.getCore()</code> interface. For each remote core, there should
 * be at most one <code>RemoteCore</code> object representing that core.
 */
public class RemoteCore extends RemoteNode implements Core {
  /**
   * A queue of fresh object identifiers.
   */
  private transient Queue<Long> fresh_ids;

  /**
   * The object table: locally resident objects.
   */
  private transient LongKeyMap<FabricSoftRef> objects;

  /**
   * The core's public SSL key. Used for verifying signatures on object groups.
   * XXX Should use core's NodePrincipal key instead.
   */
  private transient final PublicKey publicKey;

  /**
   * Cache of serialized objects that the core has sent us.
   */
  private transient LongKeyMap<SerializedObjectSoftRef> serialized;
  final transient ReferenceQueue<SerializedObject> serializedRefQueue;

  /**
   * A thread for removing entries from <code>serialized</code> as
   * SerializedObjects are collected from memory.
   */
  private transient final SerializedCollector collector;

  private class SerializedCollector extends Thread {
    private final ReferenceQueue<SerializedObject> queue;
    private boolean destroyed;

    SerializedCollector() {
      super("Serialized object collector for core " + name);
      queue = new ReferenceQueue<SerializedObject>();
      destroyed = false;
    }

    @Override
    public void run() {
      while (!destroyed) {
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

  /**
   * Creates a core representing the core at the given host name.
   */
  protected RemoteCore(String name, PublicKey key) {
    super(name, true);

    this.objects = new LongKeyHashMap<FabricSoftRef>();
    this.fresh_ids = new LinkedList<Long>();
    this.serialized = new LongKeyHashMap<SerializedObjectSoftRef>();
    this.publicKey = key;
    this.serializedRefQueue = new ReferenceQueue<SerializedObject>();
    this.collector = new SerializedCollector();
    this.collector.start();
  }

  /**
   * Cleans up the SerializedObject collector thread.
   */
  @Override
  public void cleanup() {
    super.cleanup();
    collector.destroyed = true;
    collector.interrupt();
  }

  public synchronized long createOnum() throws UnreachableNodeException {
    reserve(1);
    return fresh_ids.poll();
  }

  /**
   * Sends a PREPARE message to the core.
   */
  public boolean prepareTransaction(long tid, long commitTime,
      Collection<Object._Impl> toCreate, LongKeyMap<Integer> reads,
      Collection<Object._Impl> writes)
      throws TransactionPrepareFailedException, UnreachableNodeException {
    PrepareTransactionMessage.Response response =
        new PrepareTransactionMessage(tid, commitTime, toCreate, reads, writes)
            .send(this);

    if (!response.success)
      throw new TransactionPrepareFailedException(response.versionConflicts,
          response.message);

    return response.subTransactionCreated;
  }

  /**
   * Returns the requested _Impl object. If the object is not resident, it is
   * fetched from the Core via dissemination.
   * 
   * @param onum
   *          The identifier of the requested object
   * @return The requested object
   * @throws FabricException
   */
  public final Object._Impl readObject(long onum) throws FetchException {
    return readObject(true, onum);
  }

  /*
   * (non-Javadoc)
   * @see fabric.client.Core#readObjectNoDissem(long)
   */
  public final Object._Impl readObjectNoDissem(long onum) throws FetchException {
    return readObject(false, onum);
  }

  private final Object._Impl readObject(boolean useDissem, long onum)
      throws FetchException {
    // Intercept reads of global constants and redirect them to the local core.
    if (ONumConstants.isGlobalConstant(onum))
      return Client.instance.localCore.readObject(onum);

    // Check object table. Lock it to avoid a race condition when the object is
    // not in the cache and another thread attempts to read the same object.

    // XXX Deadlock if we simultaneously fetch surrogates from two cores that
    // refer to each other.
    synchronized (objects) {
      Object._Impl result = readObjectFromCache(onum);
      if (result != null) return result;
      return fetchObject(useDissem, onum);
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
   * Fetches the object from the serialized cache, or goes to the core if it is
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
      throws FetchException {
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
        g = Client.getClient().fetchManager().fetch(this, onum);
      } else {
        g = readObjectFromCore(onum);
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
        result = surrogate.core.readObject(surrogate.onum);
      else result = surrogate.core.readObjectNoDissem(surrogate.onum);
    }

    synchronized (objects) {
      objects.put(onum, result.$ref);
    }

    return result;
  }

  /**
   * Goes to the core to get object.
   * 
   * @param onum
   *          The object number to fetch
   * @return An ObjectGroup whose head object is the requested object.
   * @throws FetchException
   *           if there was an error while fetching the object from the core.
   */
  public ObjectGroup readObjectFromCore(long onum) throws FetchException {
    ReadMessage.Response response = new ReadMessage(onum).send(this);
    return response.group;
  }

  /**
   * Called by dissemination to fetch an encrypted object from the core.
   * 
   * @param onum
   *          The object number to fetch.
   */
  public final Glob readEncryptedObjectFromCore(long onum)
      throws FetchException {
    DissemReadMessage.Response response =
        new DissemReadMessage(onum).send(this);
    return response.glob;
  }

  /**
   * Looks up the actual Core object when this core is deserialized. While this
   * method is not explicitly called in the code, it is used by the Java
   * serialization framework when deserializing a Core object.
   * 
   * @return The canonical Core object corresponding to this Core's onum
   * @throws ObjectStreamException
   */
  public java.lang.Object readResolve() {
    return Client.getClient().getCore(name);
  }

  /**
   * Ensure that a given number of objects can be created without contacting the
   * core.
   * 
   * @param num
   *          The number of objects to allocate
   */
  protected void reserve(int num) throws UnreachableNodeException {
    while (fresh_ids.size() < num) {
      // log.info("Requesting new onums, coreid=" + coreID);
      if (num < 512) num = 512;
      AllocateMessage.Response response = new AllocateMessage(num).send(this);

      for (long oid : response.oids)
        fresh_ids.add(oid);
    }
  }

  /*
   * (non-Javadoc)
   * @see fabric.client.Core#abortTransaction(long)
   */
  public void abortTransaction(TransactionID tid) {
    new AbortTransactionMessage(tid).send(this);
  }

  /*
   * (non-Javadoc)
   * @see fabric.client.Core#commitTransaction(int)
   */
  public void commitTransaction(long transactionID)
      throws UnreachableNodeException, TransactionCommitFailedException {
    CommitTransactionMessage.Response response =
        new CommitTransactionMessage(transactionID).send(this);
    if (!response.success)
      throw new TransactionCommitFailedException(response.message);
  }

  @Override
  public String toString() {
    return "Core@" + name;
  }

  public Map getRoot() {
    return new Map._Proxy(this, ONumConstants.ROOT_MAP);
  }

  public NodePrincipal getPrincipal() {
    return new NodePrincipal._Proxy(this, ONumConstants.CORE_PRINCIPAL);
  }

  public final boolean isLocalCore() {
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
   * Updates the client's cache of objects that originate from this core. If an
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
    if (ref.core != this)
      throw new InternalError("Caching object at wrong core");

    synchronized (objects) {
      if (objects.get(ref.onum) != null)
        throw new InternalError("Conflicting cache entry");

      objects.put(ref.onum, ref);
    }
  }

  /**
   * @return The core's public key for verifying Glob signatures.
   */
  public PublicKey getPublicKey() {
    return publicKey;
  }

  /**
   * Clears the client's cache for this core. To be used for (performance)
   * testing only.
   * 
   * @see fabric.client.Client#clearCache()
   */
  public void clearCache() {
    synchronized (objects) {
      synchronized (serialized) {
        for (LongIterator it = objects.keySet().iterator(); it.hasNext();) {
          evict(it.next());
        }
        
        serialized.clear();
      }
    }
  }

}
