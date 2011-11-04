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
   */
  public static final class Entry {
    private Object._Impl impl;

    private final Store store;
    private SerializedObject serialized;

    private Entry(Object._Impl obj) {
      setImpl(obj);

      this.store = null;
      this.serialized = null;
    }

    private Entry(Store store, SerializedObject obj) {
      this.impl = null;
      
      this.store = store;
      this.serialized = obj;
    }

    private void setImpl(Object._Impl obj) {
      this.impl = obj;
      obj.$setCacheEntry(this);
    }

    /**
     * @return the onum for the object represented by this entry.
     */
    public synchronized long onum() {
      if (impl != null) return impl.$getOnum();
      return serialized.getOnum();
    }

    /**
     * Ensures the object has been deserialized.
     */
    private void ensureDeserialized() {
      if (impl != null) return;
      setImpl(serialized.deserialize(store));
    }

    /**
     * @param deserialize
     *          whether to deserialized the object if it hasn't been
     *          deserialized yet.
     * @return the Impl for this entry. This will be null if the object hasn't
     *         been deserialized yet, and <code>deserialize</code> is false.
     */
    public synchronized Object._Impl getImpl(boolean deserialize) {
      if (impl == null && deserialize) ensureDeserialized();
      return impl;
    }
  }

  final class EntrySoftRef extends SoftReference<Entry> {
    final long onum;

    private EntrySoftRef(Entry entry) {
      super(entry, refQueue);
      this.onum = entry.onum();
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
        if (entry.store.isLocalStore()) {
          throw new InternalError("evicting local store object");
        }

        clear();

        if (entry.impl != null) {
          entry.impl.$ref.clear();
        }

        entries.remove(onum);

        if (entry.impl != null) {
          entry.impl.$ref.depin();
        }
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

  public ObjectCache(String storeName) {
    this.entries = new LongKeyHashMap<ObjectCache.EntrySoftRef>();
    this.refQueue = new ReferenceQueue<Entry>();

    // Start a thread that will remove entries from this.entries as
    // Entries are collected from memory.
    new Collector(storeName).start();
  }

  public Entry get(long onum) {
    synchronized (entries) {
      EntrySoftRef entry = entries.get(onum);
      if (entry == null) return null;
      return entry.get();
    }
  }

  public void put(Object._Impl impl) {
    long onum = impl.$getOnum();

    synchronized (entries) {
      EntrySoftRef existingRef = entries.get(onum);
      if (existingRef != null) {
        Entry existingEntry = existingRef.get();
        if (impl.$getCacheEntry() == existingEntry) return;
        if (existingEntry != null)
          throw new InternalError("Conflicting cache entry");
      }

      entries.put(onum, new EntrySoftRef(impl));
    }
  }

  public Entry put(Store store, SerializedObject obj) {
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

  public void put(Store store, ObjectGroup group) {
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
  public boolean update(Store store, SerializedObject update) {
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
