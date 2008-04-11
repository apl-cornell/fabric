package fabric.dissemination.pastry;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Set;

import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.common.Pair;
import fabric.dissemination.Glob;

/**
 * The cache objects used by the disseminator to store globs. Essentially a
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
    Glob g = c.readObjectFromCore(onum);
    
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
      
      if (old == null || old.obj().getVersion() < g.obj().getVersion()) {
        map.put(key, new SoftReference<Glob>(g));
      }
    }
  }
  
  public Set<Pair<Core, Long>> keys() {
    return map.keySet();
  }

}
