package fabric.dissemination.pastry;

import java.util.HashMap;
import java.util.Map;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.common.Pair;
import fabric.messages.ReadMessage;

public class Cache {
  
  protected Map<Pair<Core, Long>, Glob> map = 
    new HashMap<Pair<Core, Long>, Glob>();
  
  protected Client client = Client.getClient();
  
  /**
   * Retrieves a glob from the cache, without trying to fetch it from the core.
   * 
   * @param c the core of the object to retrieve
   * @param onum the onum of the object
   * @return the glob, if it is in the cache; null otherwise
   */
  public Glob get(RemoteCore c, long onum) {
    return get(c, onum, false);
  }
  
  /**
   * Retrieves a glob from the cache, or fetches it from the core.
   * 
   * @param c the core of the object to retrieve
   * @param onum the onum of the object
   * @param fetch true if we should fetch from core
   * @return the glob, or null if fetch is false and glob does not exists in
   * cache
   */
  public Glob get(RemoteCore c, long onum, boolean fetch) {
    Pair<Core, Long> key = new Pair<Core, Long>(c, onum);
    Glob g = null;
    
    if (fetch) {
      ReadMessage.Response response = new ReadMessage(onum).send(c);
      g = new Glob(response.serializedResult, response.related);
    
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
   * @param c the core of the object
   * @param onum the onum of the object
   * @param g the glob
   */
  public void put(Core c, long onum, Glob g) {
    Pair<Core, Long> key = new Pair<Core, Long>(c, onum);
    
    synchronized (map) {
      map.put(key, g);
    }
  }

}
