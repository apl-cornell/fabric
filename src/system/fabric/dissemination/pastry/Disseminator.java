package fabric.dissemination.pastry;

import rice.Continuation;
import rice.Executable;
import rice.p2p.commonapi.Application;
import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.IdFactory;
import rice.p2p.commonapi.Message;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.RouteMessage;
import rice.pastry.PastryNode;
import rice.pastry.commonapi.PastryIdFactory;
import fabric.client.Client;
import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.common.Concurrent2KeyHashMap;
import fabric.dissemination.pastry.messages.Fetch;
import fabric.lang.Object.$Impl;
import fabric.messages.ReadMessage;

public class Disseminator implements Application {

  protected PastryNode node;
  protected Endpoint endpoint;
  protected IdFactory idf;
  
  protected Concurrent2KeyHashMap<Core, Long, Glob> cache;
  
  protected Fetch outstandingFetch;
  
  public Disseminator(PastryNode node) {
    this.node = node;
    endpoint = node.buildEndpoint(this, null);
    endpoint.register();
    idf = new PastryIdFactory(node.getEnvironment());
    
    cache = new Concurrent2KeyHashMap<Core, Long, Glob>();
  }

  private static final Continuation halt = new Continuation() {
    public void receiveException(Exception result) {}
    public void receiveResult(Object result) {}
  };

  protected void process(Executable task) {
    endpoint.process(task, halt);
  }

  protected void route(Id id, Message message, NodeHandle hint) {
    endpoint.route(id, message, hint);

    try {
      endpoint.getEnvironment().getTimeSource().sleep(10);
    } catch (InterruptedException e) {}
  }

  protected NodeHandle getLocalHandle() {
    return endpoint.getLocalNodeHandle();
  }
  
  public void deliver(Id id, Message message) {
    if (message instanceof Fetch) {
      fetch((Fetch) message);
    } else if (message instanceof Fetch.Reply) {
      fetch((Fetch.Reply) message);
    }
  }
  
  protected void fetch(final Fetch msg) {
    process(new Executable() {
      public Object execute() {
        Client client = Client.getClient();
        RemoteCore c = (RemoteCore) client.getCore(msg.core());
        long onum = msg.onum();
        
        Glob g = cache.get(c, onum);
        
        if (g == null) {
          ReadMessage.Response response = new ReadMessage(onum).send(c);
          g = new Glob(response.result, response.related);
          cache.put(c, onum, g);
        }
        
        Fetch.Reply r = new Fetch.Reply(g.obj(), g.related());
        route(null, r, msg.sender());
        
        return null;
      }
    });
  }
  
  protected void fetch(final Fetch.Reply msg) {
    process(new Executable() {
      public Object execute() {
        if (outstandingFetch != null) {
          synchronized (outstandingFetch) {
            outstandingFetch.reply(msg);
            outstandingFetch.notifyAll();
          }
        }
        
        return null;
      }
    });
  }
  
  public $Impl fetch(Core c, long onum) {
    outstandingFetch = new Fetch(getLocalHandle(), ((RemoteCore) c).host, onum);
    
    synchronized (outstandingFetch) {
      route(idf.buildId(outstandingFetch.toString()), outstandingFetch, null);
      
      try {
        outstandingFetch.wait();
      } catch (InterruptedException e) {}
      
      try {
        return outstandingFetch.reply().obj().deserialize(c, onum);
      } catch (ClassNotFoundException e) {}
    }
    
    return null;
  }

  public boolean forward(RouteMessage message) {
    return true;
  }

  public void update(NodeHandle handle, boolean joined) {
  }

}
