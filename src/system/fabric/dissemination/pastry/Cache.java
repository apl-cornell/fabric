package fabric.dissemination.pastry;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.common.FetchException;
import fabric.common.Pair;
import fabric.dissemination.Glob;

/**
 * The cache object used by the disseminator to store globs. Essentially a
 * hashtable specialized for globs; it also fetches globs directly from cores
 * when needed.
 */
public class Cache {
  
  protected HashMap<Pair<Core, Long>, SoftReference<Glob>> map = 
      new HashMap<Pair<Core, Long>, SoftReference<Glob>>();
  
  /**
   * Retrieves a glob from the cache, without trying to fetch it from the core.
   * 
   * @param c the core of the object to retrieve.
   * @param onum the onum of the object.
   * @return the glob, if it is in the cache; null otherwise.
   */
  public Glob get(RemoteCore c, long onum) {
    return get(c, onum, false);
  }
  
  /**
   * Retrieves a glob from the cache, or fetches it from the core.
   * 
   * @param c the core of the object to retrieve.
   * @param onum the onum of the object.
   * @param fetch true if we should fetch from core.
   * @return the glob, or null if fetch is false and glob does not exists in
   * cache.
   */
  public Glob get(RemoteCore c, long onum, boolean fetch) {
    Pair<Core, Long> key = new Pair<Core, Long>(c, onum);
    Glob g = null;
    
    if (fetch) {
      g = fetch(c, onum);
    } else {
      synchronized (map) {
        SoftReference<Glob> r = map.get(key);
        
        if (r != null) {
          g = r.get();
          
          if (g == null) {
            map.remove(key);
          }
        }
      }
    }
    
    return g;
  }
  
  private Glob fetch(RemoteCore c, long onum) {
    Glob g = null;
    
    try {
      g = c.readEncryptedObjectFromCore(onum);
    } catch (FetchException e) {}
    
    if (g != null) {
      synchronized (map) {
        Pair<Core, Long> key = new Pair<Core, Long>(c, onum);
        map.put(key, new SoftReference<Glob>(g));
      }
    }
    
    return g;
  }
  
  /**
   * Put given glob into the cache.
   * 
   * @param c the core of the object.
   * @param onum the onum of the object.
   * @param g the glob.
   */
  public void put(RemoteCore c, long onum, Glob g) {
    Pair<Core, Long> key = new Pair<Core, Long>(c, onum);
    
    synchronized (map) {
      Glob old = get(c, onum);
      
      if (old == null || old.isOlderThan(g)) {
        map.put(key, new SoftReference<Glob>(g));
      }
    }
  }
  
  /**
   * Returns a snapshot of the keys (oids) currently in the cache. This set
   * is NOT backed by the underlying map. If new keys are inserted or removed
   * from the cache, they will not be reflected by the set returned. However,
   * no synchronization is needed for working with the set.
   */
  public Set<Pair<Core, Long>> keys() {
    synchronized (map) {
      return new HashSet<Pair<Core, Long>>(map.keySet());
    }
  }
  
  /**
   * Returns a snapshot set of keys (oids) currently in the cache. The keys are
   * sorted in descending order by the popularity of the corresponding objects.
   * Like {@code keys()}, the returned set is not backed by the underlying
   * table.
   */
  public List<Pair<Core, Long>> sortedKeys() {
    List<Pair<Core, Long>> k;
    
    synchronized (map) {
      k = new ArrayList<Pair<Core, Long>>(map.keySet());
    }
    
    Collections.sort(k, new Comparator<Pair<Core, Long>>() {
      public int compare(Pair<Core, Long> o1, Pair<Core, Long> o2) {
        Glob g1 = map.get(o1).get();
        Glob g2 = map.get(o2).get();
        
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
    });
    
    return k;
  }

}
