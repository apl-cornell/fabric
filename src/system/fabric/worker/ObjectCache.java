package fabric.worker;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import fabric.common.Logging;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.Surrogate;
import fabric.common.VersionWarranty;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.lang.FClass;
import fabric.lang.Object;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;

/**
 * A per-store object cache. This class is thread safe. Lock hierarchy:
 * ObjectCache.entries → EntrySoftRef → Entry
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
    private Pair<SerializedObject, VersionWarranty> serialized;

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
    private Entry(Store store, Pair<SerializedObject, VersionWarranty> obj) {
      this.impl = null;

      this.store = store;
      if (obj.second == null) {
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
          FClass.class.getName().equals(serialized.first.getClassName());
      if (fclass) {
        Logging.TIMING_LOGGER.fine("Start deserializing FClass ("
            + serialized.first.size() + " bytes)");
      }
      try {
        // XXX END HACK FOR OAKLAND 2012 TIMING STUFF
        next =
            serialized.first.deserialize(store, serialized.second).$cacheEntry;
        store = null;
        serialized = null;
        snapNextLinks();
        // XXX BEGIN HACK FOR OAKLAND 2012 TIMING STUFF
      } finally {
        if (fclass) {
          Logging.TIMING_LOGGER.fine("Done deserializing FClass ("
              + ((FClass) next.impl).getName() + ")");
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
      } else if (serialized.first.isSurrogate()) {
        next =
            serialized.first.deserialize(store, serialized.second, false).$cacheEntry;
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

    /**
     * Obtains a reference to the object's update label. (Returns null if this
     * entry has been evicted.
     */
    public synchronized Label getLabel() {
      if (isEvicted()) return null;

      Object._Impl impl = getImpl(false);
      if (impl != null) return impl.get$$updateLabel();

      // We have a serialized entry.
      resolveSurrogates();

      if (next != null) return next.getLabel();
      return new Label._Proxy(store, serialized.first.getUpdateLabelOnum());
    }

    /**
     * Obtains a reference to the object's access policy. (Returns null if this
     * entry has been evicted.
     */
    public synchronized ConfPolicy getAccessPolicy() {
      if (isEvicted()) return null;

      Object._Impl impl = getImpl(false);
      if (impl != null) return impl.get$$accessPolicy();

      // We have a serialized entry.
      resolveSurrogates();

      if (next != null) return next.getAccessPolicy();
      return new ConfPolicy._Proxy(store,
          serialized.first.getAccessPolicyOnum());
    }

    /**
     * Obtains a reference to the object's exact proxy. (Returns null if this
     * entry has been evicted.
     */
    public synchronized Object._Proxy getProxy() {
      if (isEvicted()) return null;

      Object._Impl impl = getImpl(false);
      if (impl != null) return impl.$getProxy();

      // We have a serialized entry.
      resolveSurrogates();

      if (next != null) return next.getProxy();

      Class<? extends Object._Proxy> proxyClass =
          serialized.first.getClassRef().toProxyClass();
      try {
        Constructor<? extends Object._Proxy> constructor =
            proxyClass.getConstructor(Store.class, long.class);
        return constructor.newInstance(store, serialized.first.getOnum());
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

  final class EntrySoftRef extends SoftReference<Entry> {
    final long onum;

    /**
     * @param entry
     *          assumed to be not <b>evicted</b>.
     */
    private EntrySoftRef(Entry entry) {
      super(entry, refQueue);
      if (entry.impl != null) {
        this.onum = entry.impl.$getOnum();
      } else {
        this.onum = entry.serialized.first.getOnum();
      }
    }

    private EntrySoftRef(Store store,
        Pair<SerializedObject, VersionWarranty> obj) {
      this(new Entry(store, obj));
    }

    private EntrySoftRef(Object._Impl obj) {
      this(new Entry(obj));
    }

    /**
     * Evicts the entry associated with this soft reference from the worker's
     * cache.
     * 
     * @return true if the Entry object was still in memory.
     */
    private synchronized boolean evict() {
      Entry entry = get();
      if (entry == null) return false;

      boolean result;
      synchronized (entry) {
        result = !entry.isEvicted();
        if (entry.isLocalStoreObject()) {
          throw new InternalError("evicting local store object");
        }

        clear();

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

        entries.remove(onum);
      }

      return result;
    }
  }

  /**
   * A thread for removing entries from <code>entries</code> as Entries are
   * collected from memory.
   */
  private class Collector extends Thread {
    private Collector(String storeName) {
      super("ObjectCache collector for store " + storeName);
      setDaemon(true);
    }

    @Override
    public void run() {
      while (true) {
        EntrySoftRef ref = null;
        try {
          ref = (EntrySoftRef) refQueue.remove();
        } catch (InterruptedException e) {
        }

        if (ref != null) {
          // An entry object has been GCed. Remove the corresponding element
          // from the entry table.
          synchronized (entries) {
            EntrySoftRef removed = entries.remove(ref.onum);
            if (removed != ref) {
              // Entry had been replaced since it was put on the ReferenceQueue.
              // Undo the remove.
              entries.put(ref.onum, removed);
            }
          }
        }
      }
    }
  }

  private final ReferenceQueue<Entry> refQueue;

  private final LongKeyMap<EntrySoftRef> entries;

  ObjectCache(String storeName) {
    this.entries = new LongKeyHashMap<ObjectCache.EntrySoftRef>();
    this.refQueue = new ReferenceQueue<Entry>();

    // Start a thread that will remove entries from this.entries as
    // Entries are collected from memory.
    new Collector(storeName).start();
  }

  /**
   * Obtains the cache entry for a given onum. If the return result is non-null,
   * it is guaranteed to not be evicted.
   */
  Entry get(long onum) {
    synchronized (entries) {
      EntrySoftRef entrySoftRef = entries.get(onum);
      if (entrySoftRef == null) return null;

      Entry entry = entrySoftRef.get();
      if (entry == null) return null;

      synchronized (entry) {
        if (entry.isEvicted()) {
          // Entry evicted. Remove from entries table.
          entries.remove(onum);
          return null;
        }

        entry.snapNextLinks();

        if (entry.next != null) {
          // Snap the link to the overriding entry.
          entrySoftRef.clear();
          entry = entry.next;
          entries.put(onum, new EntrySoftRef(entry));
        }
      }

      return entry;
    }
  }

  void put(Object._Impl impl) {
    long onum = impl.$getOnum();

    synchronized (entries) {
      EntrySoftRef existingRef = entries.get(onum);
      if (existingRef != null) {
        Entry existingEntry = existingRef.get();
        if (impl.$cacheEntry == existingEntry) return;
        if (existingEntry != null)
          throw new InternalError("Conflicting cache entry");
      }

      entries.put(onum, new EntrySoftRef(impl));
    }
  }

  Entry put(Store store, Pair<SerializedObject, VersionWarranty> obj) {
    return put(store, obj, false);
  }

  private Entry put(Store store, Pair<SerializedObject, VersionWarranty> obj,
      boolean silenceConflicts) {
    long onum = obj.first.getOnum();

    synchronized (entries) {
      EntrySoftRef existingRef = entries.get(onum);
      if (existingRef != null) {
        Entry existingEntry = existingRef.get();
        if (existingEntry != null) {
          if (!silenceConflicts) {
            throw new InternalError("Conflicting cache entry");
          }
          return existingEntry;
        }
      }

      EntrySoftRef newRef = new EntrySoftRef(store, obj);
      entries.put(onum, newRef);
      return newRef.get();
    }
  }

  void put(Store store, ObjectGroup group) {
    synchronized (entries) {
      for (Pair<SerializedObject, VersionWarranty> obj : group.objects()
          .values()) {
        put(store, obj, true);
      }
    }
  }

  /**
   * Updates the cache with the given serialized object. If an object with the
   * given onum exists in cache, it is evicted and the given update is placed in
   * the cache.
   * 
   * @return true iff an object with the given onum was evicted from cache.
   */
  boolean update(Store store, Pair<SerializedObject, VersionWarranty> update) {
    synchronized (entries) {
      boolean evicted = evict(update.first.getOnum());

      if (evicted) put(store, update);
      return evicted;
    }
  }

  /**
   * Evicts the entry with the given onum from cache.
   * 
   * @return true iff an entry for the onum was found in cache.
   */
  boolean evict(long onum) {
    synchronized (entries) {
      EntrySoftRef entry = entries.get(onum);
      if (entry == null) return false;
      return entry.evict();
    }
  }

  void clear() {
    synchronized (entries) {
      LongSet onums = new LongHashSet(entries.keySet());
      for (LongIterator it = onums.iterator(); it.hasNext();) {
        evict(it.next());
      }
    }
  }
}
