package fabric.worker;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import fabric.common.SerializedObject;
import fabric.common.TransactionID;
import fabric.common.exceptions.AccessException;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.lang.Object._Impl;
import fabric.lang.security.NodePrincipal;
import fabric.net.UnreachableNodeException;
import fabric.worker.metrics.ExpiryExtension;
import fabric.worker.metrics.treaties.TreatySet;

public interface Store extends Serializable {
  /**
   * Returns this store's host name.
   */
  public String name();

  /**
   * Returns the NodePrincipal associated with this store.
   */
  public NodePrincipal getPrincipal();

  /**
   * Determines whether this is the local store.
   */
  public boolean isLocalStore();

  /**
   * Notifies the store that the transaction is entering the Prepare phase.
   * @param extensionsTriggered
   *          map from onums on this store to sets of oids that should be
   *          extended after this commits.
   */
  void prepareTransaction(long tid, boolean singleStore, boolean readOnly,
      long expiryToCheck, Collection<_Impl> toCreate,
      LongKeyMap<Pair<Integer, TreatySet>> reads, Collection<_Impl> writes,
      Collection<ExpiryExtension> extensions,
      LongKeyMap<OidKeyHashMap<LongSet>> extensionsTriggered,
      LongKeyMap<LongSet> delayedExtensions) throws UnreachableNodeException;

  /**
   * Creates a new cache entry for the given _Impl.
   */
  ObjectCache.Entry newCacheEntry(_Impl impl);

  /**
   * Returns the cache entry for the given onum. If the object is not resident,
   * it is fetched from the store via dissemination.
   *
   * @param onum
   *          The identifier of the requested object
   * @return cache entry for the requested object.
   */
  ObjectCache.Entry readObject(long onum) throws AccessException;

  /**
   * Returns the cache entry for the given onum. If the object is not resident,
   * it is fetched from the store via dissemination.  If the object is already
   * being fetched by another thread, return null.
   * <p>
   * This is primarily intended for allowing prefetching without prefetch
   * threads piling up.
   *
   * @param onum
   *          The identifier of the requested object
   * @return cache entry for the requested object.
   */
  ObjectCache.Entry readObjectNoWait(long onum) throws AccessException;

  /**
   * Returns the cache entry for the requested object. If the object is not
   * resident, it is fetched directly from the store.
   *
   * @param onum
   *          The identifier of the requested object
   * @return the cache entry for the requested object.
   */
  ObjectCache.Entry readObjectNoDissem(long onum) throws AccessException;

  /**
   * Returns the cache entry for the given onum.
   *
   * @param onum
   *          The identifier of the requested object.
   * @return The entry if it exists in the object cache; otherwise, null.
   */
  ObjectCache.Entry readFromCache(long onum);

  /**
   * Notifies the store that the transaction is being Aborted.
   *
   * @param tid
   *          the ID of the aborting transaction. This is assumed to specify a
   *          top-level transaction.
   */
  void abortTransaction(TransactionID tid);

  /**
   * Notifies the Store that the transaction should be committed.
   *
   * @param transactionID
   *          the ID of the transaction to commit
   */
  void commitTransaction(long transactionID);

  /**
   * Determines whether the given set of objects are stale.
   *
   * @return true iff stale objects were found.
   */
  boolean checkForStaleObjects(LongKeyMap<Pair<Integer, TreatySet>> reads);

  /**
   * Obtains a new, unused object number from the Store.
   *
   * @throws UnreachableNodeException
   */
  long createOnum() throws UnreachableNodeException;

  /**
   * Returns the root map of the Store
   */
  public fabric.util.Map getRoot();

  /**
   * Evicts the object with the given onum from cache.
   */
  public void evict(long onum);

  /**
   * Evicts the object with the given onum from cache unless the version in
   * cache is greater than or equal to the version given.
   */
  public void evict(long onum, int version);

  /**
   * Adds the given object to the cache.
   */
  public void cache(_Impl impl);

  /**
   * Adds the given object to the cache.
   *
   * @return the resulting cache entry.
   */
  public ObjectCache.Entry cache(SerializedObject obj);

  /**
   * Send extensions to handle.
   */
  public void sendExtensions(LongKeyMap<LongSet> extensions,
      Map<RemoteStore, Collection<SerializedObject>> updates);

  /**
   * Wait for an update past the given version for the given onum.
   */
  public void waitForUpdate(LongKeyMap<Integer> onumsAndVersions)
      throws AccessException;
}
