package fabric.dissemination.pastry;

import java.util.*;

import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.common.exceptions.FetchException;
import fabric.common.util.Pair;
import fabric.dissemination.Glob;

/**
 * The cache object used by the disseminator to store globs. Essentially a
 * hashtable specialized for globs; it also fetches globs directly from cores
 * when needed.
 */
public class Cache {

  /**
   * Cache of globs, indexed by the oid of the glob's head object.
   */
  private fabric.common.util.Cache<Pair<Core, Long>, Glob> map =
      new fabric.common.util.Cache<Pair<Core, Long>, Glob>();

  /**
   * Retrieves a glob from the cache, without trying to fetch it from the core.
   * 
   * @param c
   *          the core of the object to retrieve.
   * @param onum
   *          the onum of the object.
   * @return the glob, if it is in the cache; null otherwise.
   */
  public Glob get(RemoteCore c, long onum) {
    return get(c, onum, false);
  }

  /**
   * Retrieves a glob from the cache, or fetches it from the core.
   * 
   * @param c
   *          the core of the object to retrieve.
   * @param onum
   *          the onum of the object.
   * @param fetch
   *          true if we should fetch from core.
   * @return the glob, or null if fetch is false and glob does not exists in
   *         cache.
   */
  public Glob get(RemoteCore c, long onum, boolean fetch) {
    Pair<Core, Long> key = new Pair<Core, Long>(c, onum);
    Glob g = null;

    if (fetch) {
      g = fetch(c, onum);
    } else {
      g = map.get(key);
    }

    return g;
  }

  private Glob fetch(RemoteCore c, long onum) {
    Glob g = null;

    try {
      g = c.readEncryptedObjectFromCore(onum);
    } catch (FetchException e) {
    }

    if (g != null) {
      Pair<Core, Long> key = new Pair<Core, Long>(c, onum);
      map.put(key, g);
    }

    return g;
  }

  /**
   * Put given glob into the cache.
   * 
   * @param c
   *          the core of the object.
   * @param onum
   *          the onum of the object.
   * @param g
   *          the glob.
   */
  public void put(RemoteCore c, long onum, Glob g) {
    Pair<Core, Long> key = new Pair<Core, Long>(c, onum);

    synchronized (map) {
      Glob old = get(c, onum);

      if (old == null || old.isOlderThan(g)) {
        map.put(key, g);
      }
    }
  }

  /**
   * Updates a cache entry with the given glob. If the cache has no entry for
   * the given oid, then nothing is changed.
   * 
   * @return true iff there was a cache entry for the given oid.
   */
  public boolean updateEntry(RemoteCore core, long onum, Glob g) {
    Pair<Core, Long> key = new Pair<Core, Long>(core, onum);

    synchronized (map) {
      Glob old = get(core, onum);
      if (old == null) return false;

      if (old.isOlderThan(g)) {
        map.put(key, g);
      }
    }

    return true;
  }

  /**
   * Returns a snapshot of the timestamp for each OID currently in the cache.
   * This set is NOT backed by the underlying map. If new keys are inserted or
   * removed from the cache, they will not be reflected by the set returned.
   * However, no synchronization is needed for working with the set.
   */
  public Set<Pair<Pair<Core, Long>, Long>> timestamps() {
    Set<Pair<Pair<Core, Long>, Long>> result =
        new HashSet<Pair<Pair<Core, Long>, Long>>();

    for (Pair<Core, Long> key : map.keys()) {
      Glob glob = map.get(key);
      if (glob != null)
        result.add(new Pair<Pair<Core, Long>, Long>(key, glob.getTimestamp()));
    }

    return result;
  }

  /**
   * Returns a snapshot set of the timestamp for each OID currently in the
   * cache. The set is sorted in descending order by the popularity of the
   * corresponding objects. Like {@code timestamps()}, the returned set is not
   * backed by the underlying table.
   */
  public List<Pair<Pair<Core, Long>, Long>> sortedTimestamps() {
    List<Pair<Pair<Core, Long>, Long>> k =
        new ArrayList<Pair<Pair<Core, Long>, Long>>(timestamps());

    Collections.sort(k, TIMESTAMP_COMPARATOR);

    return k;
  }

  private final Comparator<Pair<Pair<Core, Long>, Long>> TIMESTAMP_COMPARATOR =
      new Comparator<Pair<Pair<Core, Long>, Long>>() {
        public int compare(Pair<Pair<Core, Long>, Long> o1,
            Pair<Pair<Core, Long>, Long> o2) {
          Glob g1 = map.get(o1.first);
          Glob g2 = map.get(o2.first);

          if (g1 == g2) {
            return 0;
          }

          if (g1 == null) {
            return 1;
          }

          if (g2 == null) {
            return -1;
          }

          return g2.frequency() - g1.frequency();
        }
      };

}
