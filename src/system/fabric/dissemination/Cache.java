package fabric.dissemination;

import static fabric.common.Logging.WORKER_LOGGER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.exceptions.AccessException;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.worker.RemoteStore;
import fabric.worker.Worker;

/**
 * The cache object used by the disseminator to store globs. Essentially a
 * hashtable specialized for globs; it also fetches globs directly from stores
 * when needed.
 */
public class Cache {
  public static class Entry {
    public final ObjectGlob objectGlob;

    private int level;
    private int frequency;

    private Entry(ObjectGlob glob) {
      this.objectGlob = glob;
    }

    public boolean isOlderThan(ObjectGlob g) {
      return objectGlob.isOlderThan(g);
    }

    /**
     * @param g
     * @return
     */
    public Entry update(ObjectGlob g) {
      Entry result = new Entry(g);
      result.level = this.level;
      result.frequency = this.frequency;
      return result;
    }

    public int level() {
      return level;
    }

    public void touch() {
      frequency++;
    }
  }

  /**
   * Cache of globs, indexed by the oid of the glob's head object.
   */
  private final fabric.common.util.Cache<Pair<RemoteStore, Long>, Entry> map;

  /**
   * The set of fetch locks. Used to prevent threads from concurrently
   * attempting to fetch the same object.
   */
  private final OidKeyHashMap<FetchLock> fetchLocks;

  private class FetchLock {
    private boolean ready = false;
    private Entry result = null;
  }

  public Cache() {
    this.map = new fabric.common.util.Cache<>();
    this.fetchLocks = new OidKeyHashMap<>();
  }

  /**
   * Retrieves a glob from the cache, without trying to fetch it from the store.
   *
   * @param store
   *          the store of the object to retrieve.
   * @param onum
   *          the onum of the object.
   * @return the glob, if it is in the cache; null otherwise.
   */
  public Entry get(RemoteStore store, long onum) {
    return get(store, onum, false);
  }

  /**
   * Retrieves a glob from the cache or fetches it from the store.
   *
   * @param store
   *          the store of the object to retrieve.
   * @param onum
   *          the onum of the object.
   * @param fetch
   *          whether the glob should be should fetched from store in the event
   *          of a cache miss.
   * @return the glob, or null if fetch is false and glob does not exists in
   *         cache.
   */
  public Entry get(RemoteStore store, long onum, boolean fetch) {
    Pair<RemoteStore, Long> key = new Pair<>(store, onum);

    Entry entry = map.get(key);
    if (entry != null || !fetch) return entry;

    // Need to fetch. Check the object table in case some other thread fetched
    // the object while we weren't looking. Use fetchLocks as a mutex to
    // atomically check the cache and create a mutex for the object cache in
    // the event of a cache miss.
    FetchLock fetchLock;
    boolean needToFetch = false;
    synchronized (fetchLocks) {
      Entry result = map.get(key);
      if (result != null) return result;

      // Object not in cache. Get/create a mutex for fetching the object.
      fetchLock = fetchLocks.get(store, onum);
      if (fetchLock == null) {
        needToFetch = true;
        fetchLock = new FetchLock();
        fetchLocks.put(store, onum, fetchLock);
      }
    }

    synchronized (fetchLock) {
      if (needToFetch) {
        // We are responsible for fetching the object.
        fetchLock.result = fetch(key);
        fetchLock.ready = true;

        // Object now cached. Remove our mutex from fetchLocks.
        synchronized (fetchLocks) {
          fetchLocks.remove(store, onum);
        }

        // Signal any other threads that might be waiting for our fetch.
        fetchLock.notifyAll();
      } else {
        // Wait for another thread to fetch the object for us.
        while (!fetchLock.ready) {
          try {
            fetchLock.wait();
          } catch (InterruptedException e) {
            Logging.logIgnoredInterruptedException(e);
          }
        }
      }

      return fetchLock.result;
    }
  }

  /**
   * Fetches a glob from the store and caches it.
   */
  private Entry fetch(Pair<RemoteStore, Long> oid) {
    ObjectGlob g = null;
    RemoteStore store = oid.first;
    long onum = oid.second;

    try {
      g = store.readEncryptedObjectFromStore(onum);
    } catch (AccessException e) {
      Logging.log(WORKER_LOGGER, Level.WARNING,
          "Access exception accessing fab://{0}/{1}", oid.first, oid.second);
    }

    if (g == null) {
      // TODO: Make the worker more robust against this. NPEs will arise when
      // this return happens.
      return null;
    }
    return put(oid, g, false);
  }

  /**
   * Put given glob into the cache.
   *
   * @param store
   *          the store of the object.
   * @param onum
   *          the onum of the object.
   * @param g
   *          the glob.
   */
  public void put(RemoteStore store, long onum, ObjectGlob g) {
    Pair<RemoteStore, Long> key = new Pair<>(store, onum);
    put(key, g, false);
  }

  /**
   * Incorporates the given glob into the cache.
   *
   * @return the resulting cache entry.
   */
  private Entry put(Pair<RemoteStore, Long> oid, ObjectGlob g,
      boolean replaceOnly) {
    RemoteStore store = oid.first;
    long onum = oid.second;

    while (true) {
      Entry currentEntry = get(store, onum);

      if (currentEntry == null) {
        // No existing entry.
        if (replaceOnly) return null;

        // Add a new entry.
        Entry newEntry = new Entry(g);
        if (map.putIfAbsent(oid, newEntry) == null) return newEntry;

        // An entry was added while we weren't looking. Try again.
        continue;
      }

      // See if the existing entry is older than what we got.
      if (currentEntry.isOlderThan(g)) {
        // Existing entry is older, so replace it.
        Entry newEntry = currentEntry.update(g);
        if (map.replace(oid, currentEntry, newEntry)) {
          return newEntry;
        }

        // Entry was replaced while we weren't looking. Try again.
        continue;
      }

      return currentEntry;
    }
  }

  /**
   * Updates the dissemination and worker cache with the given object glob. If
   * the caches do not have entries for the given glob, then nothing is changed.
   *
   * @return true iff either of the caches was changed.
   */
  public boolean updateEntry(RemoteStore store, LongSet onums, ObjectGlob g) {
    // Update the local worker's cache.
    // XXX What happens if the worker isn't trusted to decrypt the glob?
    boolean updated = Worker.getWorker().updateCache(store, g.decrypt());

    for (LongIterator iter = onums.iterator(); iter.hasNext();) {
      long onum = iter.next();
      Pair<RemoteStore, Long> key = new Pair<>(store, onum);
      updated |= put(key, g, true) != null;
    }

    return updated;
  }

  /**
   * Returns a snapshot of the timestamp for each OID currently in the cache.
   * This set is NOT backed by the underlying map. If new keys are inserted or
   * removed from the cache, they will not be reflected by the set returned.
   * However, no synchronization is needed for working with the set.
   */
  public Set<Pair<Pair<RemoteStore, Long>, Long>> timestamps() {
    Set<Pair<Pair<RemoteStore, Long>, Long>> result = new HashSet<>();

    for (Pair<RemoteStore, Long> key : map.keys()) {
      Entry glob = map.get(key);
      if (glob != null)
        result.add(new Pair<>(key, glob.objectGlob.getTimestamp()));
    }

    return result;
  }

  /**
   * Returns a snapshot set of the timestamp for each OID currently in the
   * cache. The set is sorted in descending order by the popularity of the
   * corresponding objects. Like {@code timestamps()}, the returned set is not
   * backed by the underlying table.
   */
  public List<Pair<Pair<RemoteStore, Long>, Long>> sortedTimestamps() {
    List<Pair<Pair<RemoteStore, Long>, Long>> k = new ArrayList<>(timestamps());

    Collections.sort(k, TIMESTAMP_COMPARATOR);

    return k;
  }

  private final Comparator<Pair<Pair<RemoteStore, Long>, Long>> TIMESTAMP_COMPARATOR =
      new Comparator<Pair<Pair<RemoteStore, Long>, Long>>() {
        @Override
        public int compare(Pair<Pair<RemoteStore, Long>, Long> o1,
            Pair<Pair<RemoteStore, Long>, Long> o2) {
          Entry entry1 = map.get(o1.first);
          Entry entry2 = map.get(o2.first);

          if (entry1 == entry2) {
            return 0;
          }

          if (entry1 == null) {
            return 1;
          }

          if (entry2 == null) {
            return -1;
          }

          return entry2.frequency - entry1.frequency;
        }
      };

}
