package fabric.worker;

import static fabric.common.Logging.TIMING_LOGGER;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.SerializedObjectAndTokens;
import fabric.common.Surrogate;
import fabric.common.VersionWarranty;
import fabric.common.VersionWarranty.Binding;
import fabric.common.WarrantyGroup;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyCache;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.lang.FClass;
import fabric.lang.Object;
import fabric.lang.Object._Impl;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.worker.transaction.TransactionManager;

/**
 * A per-store object cache. This class is thread safe.
 */
public final class ObjectCache {
  /**
   * Entries hold either an _Impl or a SerializedObject (but not both). This
   * class is thread safe.
   * <p>
   * Possible states:
   * <ol>
   * <li><b>overridden:</b> <code>next</code> is non-null, <code>impl</code> and
   * <code>serialized</code> are null (the entry in <code>next</code> should be
   * used instead of this)
   * <ul>
   * <li>This is done because we maintain the invariant that exactly one Entry
   * object is associated with any given _Impl or SerializedObject.</li>
   * </ul>
   * </li>
   * <li><b>serialized:</b> <code>impl</code> and <code>next</code> are null,
   * <code>serialized</code> and <code>store</code> are non-null</li>
   * <li><b>deserialized:</b> <code>serialized</code> and <code>next</code> are
   * null, <code>impl</code> is non-null</li>
   * <li><b>evicted:</b> <code>impl</code>, <code>serialized</code>, and
   * <code>next</code> are null
   * </ol>
   * <p>
   */
  public static final class Entry {
    private Object._Impl impl;
    private Store store;
    private SerializedObjectAndTokens serialized;

    private Entry next;

    /**
     * Constructs an <code>Entry</code> object in <b>deserialized</b> state.
     */
    public Entry(Object._Impl obj) {
      this.impl = obj;

      this.store = null;
      this.serialized = null;
      this.next = null;
    }

    /**
     * Constructs an <code>Entry</code> object in <b>serialized</b> state.
     */
    private Entry(Store store, SerializedObjectAndTokens obj) {
      this.impl = null;

      this.store = store;
      if (obj.getWarranty() == null) {
        new InternalError();
      }
      this.serialized = obj;
      this.next = null;
    }

    /**
     * Flattens chains of <code>next</code> links. Ensures either
     * <code>next</code> or <code>next.next</code> is null.
     */
    private synchronized void snapNextLinks() {
      if (next == null) return;
      synchronized (next) {
        if (next.next != null) {
          next.snapNextLinks();
          next = next.next;
        }
      }
    }

    /**
     * Ensures this entry is not in <b>serialized</b> state.
     * <p>
     * When this method returns, either <code>next</code> or
     * <code>next.next</code> is null.
     */
    private synchronized void ensureDeserialized() {
      if (next != null) {
        // Entry is overridden.
        synchronized (next) {
          next.ensureDeserialized();
          if (next.next != null) next = next.next;
        }
        return;
      }

      if (impl != null || serialized == null) {
        // This entry is either already deserialized or has been evicted.
        return;
      }

      // This entry is in serialized state. Deserialize.
      // XXX BEGIN HACK FOR OAKLAND 2012 TIMING STUFF
      boolean fclass =
          FClass.class.getName().equals(serialized.getSerializedObject().getClassName());
      if (fclass) {
        TIMING_LOGGER.log(Level.FINE, "Start deserializing FClass ({0} bytes)",
            serialized.getSerializedObject().size());
      }
      try {
        // XXX END HACK FOR OAKLAND 2012 TIMING STUFF
        _Impl impl = serialized.getDeserializedObject(store);
        next = impl.$cacheEntry;
        store = null;
        serialized = null;
        impl.$getStore().cache(impl);
        // XXX BEGIN HACK FOR OAKLAND 2012 TIMING STUFF
      } finally {
        if (fclass) {
          TIMING_LOGGER.log(Level.FINE, "Done deserializing FClass ({0})",
              ((FClass) next.impl).getName());
        }
      }
      // XXX END HACK FOR OAKLAND 2012 TIMING STUFF
    }

    /**
     * Ensures either this entry represents a non-Surrogate object or
     * <code>next</code> does. Assumes this entry hasn't been evicted.
     */
    private synchronized void resolveSurrogates() {
      if (next != null) {
        // Entry is overridden.
        synchronized (next) {
          next.resolveSurrogates();
          if (next.next != null) next = next.next;
        }
        return;
      }

      if (impl != null) {
        if (!(impl instanceof Surrogate)) return;

        Surrogate surrogate = (Surrogate) impl;
        next = new Object._Proxy(surrogate.store, surrogate.onum).fetchEntry();
      } else if (serialized.getSerializedObject().isSurrogate()) {
        next = serialized.getDeserializedObject(store, false).$cacheEntry;
      } else {
        return;
      }

      synchronized (next) {
        next.resolveSurrogates();
        if (next.next != null) next = next.next;
      }
    }

    /**
     * Determines whether this entry is <b>evicted</b>.
     */
    private synchronized boolean isEvicted() {
      if (next != null) {
        if (!next.isEvicted()) return false;

        next = null;
        return true;
      }

      return impl == null && serialized == null;
    }

    /**
     * Evicts this entry (but does not remove it from its corresponding table).
     */
    private void evict() {
      if (isLocalStoreObject()) {
        throw new InternalError("Local-store objects cannot be evicted.");
      }

      if (isEvicted()) return;

      Entry entry = this;
      while (entry != null) {
        synchronized (entry) {
          if (entry.impl != null) {
            entry.impl.$ref.clear();
            entry.impl.$ref.depin();
            entry.impl = null;
          }

          Entry next = entry.next;
          entry.store = null;
          entry.serialized = null;
          entry.next = null;

          entry = next;
        }
      }
    }

    /**
     * @param deserialize
     *          whether to deserialize this entry if it's <b>serialized</b>.
     * @return the Impl for this entry. This will be null if this entry is
     *         <b>serialized<b> and <code>deserialize</code> is false, or if
     *         this entry is <b>evicted</b>.
     */
    public synchronized Object._Impl getImpl(boolean deserialize) {
      if (deserialize) ensureDeserialized();
      if (next != null) return next.getImpl(deserialize);
      return impl;
    }

    public synchronized boolean updateWarranty(int versionNumber,
        VersionWarranty warranty) {
      if (next != null) {
        return next.updateWarranty(versionNumber, warranty);
      } else if (impl != null) {
        if (impl.$version != versionNumber) return false;

        return impl.$readMapEntry.updateWarranty(warranty);
      } else if (serialized != null) {
        if (serialized.getSerializedObject().getVersion() != versionNumber) return false;

        if (warranty.expiresAfter(serialized.getWarranty())) {
          serialized.setWarranty(warranty);
        }
        return true;
      } else {
        return false;
      }
    }

    /**
     * Obtains the object's version number. (Returns null if this entry has been
     * evicted.)
     */
    public synchronized Integer getVersion() {
      if (next != null) return next.getVersion();
      if (impl != null) return impl.$version;
      if (serialized != null) return serialized.getSerializedObject().getVersion();
      return null;
    }

    /**
     * Obtains a reference to the object's update label. (Returns null if this
     * entry has been evicted.)
     */
    public synchronized Label getLabel() {
      if (isEvicted()) return null;

      Object._Impl impl = getImpl(false);
      if (impl != null) return impl.get$$updateLabel();

      // We have a serialized entry.
      resolveSurrogates();

      if (next != null) return next.getLabel();
      return new Label._Proxy(store, serialized.getSerializedObject()
          .getUpdateLabelOnum());
    }

    /**
     * Obtains a reference to the object's access policy. (Returns null if this
     * entry has been evicted.)
     */
    public synchronized ConfPolicy getAccessPolicy() {
      if (isEvicted()) return null;

      Object._Impl impl = getImpl(false);
      if (impl != null) return impl.get$$accessPolicy();

      // We have a serialized entry.
      resolveSurrogates();

      if (next != null) return next.getAccessPolicy();
      return new ConfPolicy._Proxy(store, serialized.getSerializedObject()
          .getAccessPolicyOnum());
    }

    /**
     * Obtains a reference to the object's exact proxy. (Returns null if this
     * entry has been evicted.)
     */
    public synchronized Object._Proxy getProxy() {
      if (isEvicted()) return null;

      Object._Impl impl = getImpl(false);
      if (impl != null) return impl.$getProxy();

      // We have a serialized entry.
      resolveSurrogates();

      if (next != null) return next.getProxy();

      Class<? extends Object._Proxy> proxyClass =
          serialized.getSerializedObject().getClassRef().toProxyClass();
      try {
        Constructor<? extends Object._Proxy> constructor =
            proxyClass.getConstructor(Store.class, long.class);
        return constructor.newInstance(store, serialized.getSerializedObject().getOnum());
      } catch (NoSuchMethodException e) {
        throw new InternalError(e);
      } catch (SecurityException e) {
        throw new InternalError(e);
      } catch (InstantiationException e) {
        throw new InternalError(e);
      } catch (IllegalAccessException e) {
        throw new InternalError(e);
      } catch (IllegalArgumentException e) {
        throw new InternalError(e);
      } catch (InvocationTargetException e) {
        throw new InternalError(e);
      }
    }

    /**
     * Determines whether this entry represents an object on the local store.
     */
    private synchronized boolean isLocalStoreObject() {
      if (next != null) return next.isLocalStoreObject();
      if (impl != null) return impl.$getStore().isLocalStore();
      return store != null && store.isLocalStore();
    }
  }

  private final LongKeyCache<Entry> entries;

  ObjectCache(String storeName) {
    this.entries = new LongKeyCache<>();
  }

  /**
   * Obtains the cache entry for a given onum. If the return result is non-null,
   * it is guaranteed to not be evicted.
   */
  Entry get(long onum) {
    Entry entry = entries.get(onum);
    if (entry == null) return null;

    synchronized (entry) {
      if (entry.isEvicted()) {
        // Entry evicted. Remove from entries table.
        entries.remove(onum, entry);
        return null;
      }

      entry.snapNextLinks();

      if (entry.next != null) {
        // Snap the link to the overriding entry.
        entries.replace(onum, entry, entry.next);
        entry = entry.next;
      }
    }

    return entry;
  }

  /**
   * Adds the given impl to the cache. If a different impl already exists in
   * cache, then an internal error results, indicating that an invariant was
   * probably broken, resulting in a cache conflict.
   */
  void put(Object._Impl impl) {
    long onum = impl.$getOnum();

    while (true) {
      Entry existingEntry = entries.putIfAbsent(onum, impl.$cacheEntry);
      if (existingEntry == null) return;

      if (existingEntry.getImpl(false) != impl) {
        throw new InternalError("Conflicting cache entry");
      }

      if (entries.replace(onum, existingEntry, impl.$cacheEntry)) return;
    }
  }

  /**
   * If there is not already an entry for the given object's onum, add the given
   * object to the cache. If an entry already exists in cache, then an internal
   * error results, indicating that an invariant was probably broken.
   *
   * @return the Entry inserted into the cache.
   */
  Entry put(Store store, SerializedObjectAndTokens obj) {
    return putIfAbsent(store, obj, false);
  }

  /**
   * If there is not already an entry for the given object's onum, add the given
   * object to the cache.
   *
   * @param silenceConflicts if this is false and an entry already exists for
   *          the object, then an error is thrown.
   * @return the resulting cache entry associated with the object's onum.
   */
  private Entry putIfAbsent(Store store, SerializedObjectAndTokens obj,
      boolean silenceConflicts) {
    long onum = obj.getSerializedObject().getOnum();

    Entry newEntry = new Entry(store, obj);
    Entry existingEntry = entries.putIfAbsent(onum, newEntry);
    if (existingEntry != null) {
      if (!silenceConflicts) {
        throw new InternalError("Conflicting cache entry");
      }

      return existingEntry;
    }

    return newEntry;
  }

  /**
   * Adds the contents of the given object group to the cache. Returns the entry
   * for the given onum.
   *
   * @param store the store from which the group was obtained.
   * @param group the group to add to the cache.
   * @param onum the onum of the entry to return. This should be a member of the
   *          given group.
   */
  Entry put(Store store, Pair<ObjectGroup, WarrantyGroup> group, long onum) {
    Entry result = null;
    for (SerializedObject obj : group.first.objects().values()) {
      long curOnum = obj.getOnum();

      VersionWarranty warranty = VersionWarranty.EXPIRED_WARRANTY;
      if (group.second != null) warranty = group.second.get(curOnum);

      Entry curEntry =
          putIfAbsent(store, new SerializedObjectAndTokens(obj, warranty), true);
      if (result == null && onum == curOnum) {
        result = curEntry;
      }
    }

    if (result == null) throw new InternalError("Entry not found.");
    return result;
  }

  /**
   * Adds the given object to the cache. If a cache entry already exists, it is
   * replaced, and any transactions currently using the object are aborted and
   * retried.
   */
  void forcePut(Store store, SerializedObjectAndTokens obj) {
    update(store, obj, false);
  }

  /**
   * Updates the cache with the given serialized object. If an object with the
   * given onum exists in cache, it is evicted and the given update is placed in
   * the cache; any transactions currently using the replaced object are aborted
   * and retried. If the object does not exist in cache, then the cache is not
   * updated.
   */
  void update(Store store, SerializedObjectAndTokens update) {
    update(store, update, true);
  }

  /**
   * Updates the cache with the given serialized object. If
   * <code>replaceOnly</code> is true, then the cache is only updated if an
   * object with the given onum exists in cache. The existing object is evicted,
   * and the given update is placed in the cache. If the cache is updated, then
   * any transactions currently using the object are aborted and retried.
   */
  void update(Store store, SerializedObjectAndTokens update, boolean replaceOnly) {
    long onum = update.getSerializedObject().getOnum();
    Entry curEntry = entries.get(onum);

    if (curEntry == null) {
      if (!replaceOnly) putIfAbsent(store, update, true);
      return;
    }

    synchronized (curEntry) {
      if (replaceOnly && curEntry.isEvicted()) return;

      // Check if object in current entry is an older version.
      if (curEntry.getVersion() >= update.getSerializedObject().getVersion()) return;

      curEntry.evict();
    }

    Entry newEntry = new Entry(store, update);
    entries.replace(onum, curEntry, newEntry);

    TransactionManager.abortReaders(store, update.getSerializedObject().getOnum());
  }

  /**
   * Updates the cache with the given object, as follows:
   * <ul>
   * <li>If the cache contains a deserialized copy of an old version of the
   * object, then that old version is replaced with a serialized copy of the new
   * version. Transactions using the updated object are aborted and retried.
   * <li>If the cache contains a serialized copy of an old version of the
   * object, then that old version is evicted.
   * </ul>
   *
   * @return true iff after this update operation, the cache contains the
   *     object.
   */
  boolean updateOrEvict(Store store, SerializedObject obj) {
    long onum = obj.getOnum();
    Entry curEntry = entries.get(onum);
    if (curEntry == null) return false;

    if (curEntry.getImpl(false) == null) {
      curEntry.evict();
      return false;
    }

    forcePut(store, new SerializedObjectAndTokens(obj,
        VersionWarranty.EXPIRED_WARRANTY));
    return true;
  }

  /**
   * Updates the cache with the given set of warranties.
   *
   * @return the set of onums for which a cache entry was found.
   */
  public List<Long> update(RemoteStore store, WarrantyGroup warranties) {
    List<Long> result = new ArrayList<>();
    for (Binding update : warranties) {
      long onum = update.onum;

      Entry entry = entries.get(onum);
      if (entry == null) continue;

      if (entry.updateWarranty(update.versionNumber, update.warranty())) {
        result.add(onum);
      }
    }

    return result;
  }

  /**
   * Evicts the entry with the given onum from cache.
   *
   * @return true iff an entry for the onum was found in cache.
   */
  void evict(long onum) {
    Entry entry = entries.get(onum);
    if (entry == null) return;
    entry.evict();
    entries.remove(onum, entry);
  }

  void clear() {
    LongSet onums = new LongHashSet(entries.keySet());
    for (LongIterator it = onums.iterator(); it.hasNext();) {
      evict(it.next());
    }
  }
}
