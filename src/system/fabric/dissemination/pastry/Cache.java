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
  
  public Glob get(RemoteCore c, long onum) {
    return get(c, onum, false);
  }
  
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
  
  public void put(Core c, long onum, Glob g) {
    Pair<Core, Long> key = new Pair<Core, Long>(c, onum);
    
    synchronized (map) {
      map.put(key, g);
    }
  }

}
