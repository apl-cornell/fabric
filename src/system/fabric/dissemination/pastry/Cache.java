package fabric.dissemination.pastry;

import java.util.HashMap;
import java.util.Map;

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
  
  protected Map<Pair<Core, Long>, Glob> map = 
    new HashMap<Pair<Core, Long>, Glob>();
  
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
      g = c.readObjectFromCore(onum);
    
      synchronized (map) {
        map.put(key, g);
      }
    } else {
      synchronized (map) {
        g = map.get(key);
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
  public void put(Core c, long onum, Glob g) {
    Pair<Core, Long> key = new Pair<Core, Long>(c, onum);
    
    synchronized (map) {
      Glob old = map.get(key);
      
      if (old == null || old.obj().getVersion() < g.obj().getVersion()) {
        map.put(key, g);
      }
    }
  }

}
