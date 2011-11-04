package fabric.worker;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.lang.Object;

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
   * <li><b>serialized:</b> <code>impl</code> and <code>deserializedEntry</code>
   * are null, <code>serialized</code> and <code>store</code> are non-null</li>
   * <li><b>deserialized:</b> <code>serialized</code> is null,
   * <code>deserializedEntry</code> and <code>deserializedEntry.impl</code> are
   * non-null</li>
   * <li><b>evicted:</b> <code>impl</code>, <code>serialized</code>, and (
   * <code>deserializedEntry</code> or <code>deserializedEntry.impl</code>) are
   * null
   * </ol>
   */
  public static final class Entry {
    private Object._Impl impl;
    private Store store;
    private SerializedObject serialized;

    /**
     * Memoizes the deserialized version of this cache entry.
     */
    private Entry deserializedEntry;

    /**
     * Constructs an <code>Entry</code> object in <b>deserialized</b> state.
     */
    public Entry(Object._Impl obj) {
      this.impl = obj;

      this.store = null;
      this.serialized = null;
      this.deserializedEntry = this;
    }

    /**
     * Constructs an <code>Entry</code> object in <b>serialized</b> state.
     */
    private Entry(Store store, SerializedObject obj) {
      this.impl = null;

      this.store = store;
      this.serialized = obj;
      this.deserializedEntry = null;
    }

    /**
     * Ensures this entry is not in <b>serialized</b> state.
     */
    private void ensureDeserialized() {
      synchronized (this) {
        if (deserializedEntry != null) return;
        if (serialized == null) {
          // This entry has been evicted.
          return;
        }

        // This entry is in serialized state. Deserialize.
        deserializedEntry = serialized.deserialize(store).$cacheEntry;
        store = null;
        serialized = null;
      }
    }

    /**
     * @param deserialize
     *          whether to deserialize this entry if it's <b>serialized</b>.
     * @return the Impl for this entry. This will be null if this entry is
     *         <b>serialized<b> and <code>deserialize</code> is false, or if
     *         this entry is <b>evicted</b>.
     */
    public Object._Impl getImpl(boolean deserialize) {
      synchronized (this) {
        if (deserialize) ensureDeserialized();
        if (deserializedEntry != null) return deserializedEntry.impl;
        return null;
      }
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
        this.onum = entry.serialized.getOnum();
      }
    }

    private EntrySoftRef(Store store, SerializedObject obj) {
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

      synchronized (entry) {
        if (entry.store != null && entry.store.isLocalStore()) {
          throw new InternalError("evicting local store object");
        }

        clear();

        Entry deserializedEntry = entry.deserializedEntry;
        if (deserializedEntry != null) {
          synchronized (deserializedEntry) {
            if (deserializedEntry.impl != null) {
              if (deserializedEntry.impl.$getStore().isLocalStore()) {
                throw new InternalError("evicting local store object");
              }
              
              deserializedEntry.impl.$ref.clear();
              deserializedEntry.impl.$ref.depin();
              deserializedEntry.impl = null;
            }
          }
          
          entry.deserializedEntry = null;
        }
        
        entry.impl = null;
        entry.serialized = null;

        entries.remove(onum);
      }

      return true;
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

  Entry get(long onum) {
    synchronized (entries) {
      EntrySoftRef entrySoftRef = entries.get(onum);
      if (entrySoftRef == null) return null;

      Entry entry = entrySoftRef.get();
      if (entry.deserializedEntry != null && entry != entry.deserializedEntry) {
        // Snap the link.
        entrySoftRef.clear();
        entry = entry.deserializedEntry;
        entries.put(onum, new EntrySoftRef(entry));
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

  Entry put(Store store, SerializedObject obj) {
    return put(store, obj, false);
  }

  private Entry put(Store store, SerializedObject obj, boolean silenceConflicts) {
    long onum = obj.getOnum();

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
      for (SerializedObject obj : group.objects().values()) {
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
  boolean update(Store store, SerializedObject update) {
    synchronized (entries) {
      boolean evicted = evict(update.getOnum());

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
