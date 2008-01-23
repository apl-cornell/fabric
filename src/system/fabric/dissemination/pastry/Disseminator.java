package fabric.dissemination.pastry;

import java.util.HashMap;
import java.util.Map;

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
import fabric.common.Pair;
import fabric.dissemination.pastry.messages.Fetch;
import fabric.lang.Object.$Impl;
import fabric.messages.ReadMessage;

/**
 * A pastry application that implements the functionality of a Fabric
 * dissemination network.
 */
public class Disseminator implements Application {

  /** The pastry node. */
  protected PastryNode node;
  
  /** The pastry endpoint. */
  protected Endpoint endpoint;
  
  /** The pastry id generating factory. */
  protected IdFactory idf;
  
  /** The cache of fetched objects. */
  protected Cache cache;
  
  /** The outstanding fetch message to be processed. */
  protected Fetch outstandingFetch;
  
  public Disseminator(PastryNode node) {
    this.node = node;
    endpoint = node.buildEndpoint(this, null);
    endpoint.register();
    idf = new PastryIdFactory(node.getEnvironment());
    
    cache = new Cache();
  }

  private static final Continuation halt = new Continuation() {
    public void receiveException(Exception result) {}
    public void receiveResult(Object result) {}
  };

  /**
    * Processes a task on the task processing thread. When messages are
    * received, handlers should run on the processing thread so as not to
    * block the message receiving thread.
    */
  protected void process(Executable task) {
    endpoint.process(task, halt);
  }

  /**
   * Routes a message on the pastry ring. At least one of id or hint must be
   * non-null.
   * 
   * @param id The id of this message (hash value where it should be routed)
   * @param message The message to be routed
   * @param hint NodeHandle of a starting node, if desired
   */
  protected void route(Id id, Message message, NodeHandle hint) {
    endpoint.route(id, message, hint);

    try {
      endpoint.getEnvironment().getTimeSource().sleep(10);
    } catch (InterruptedException e) {}
  }

  /** The NodeHandle of this pastry node. */
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
  
  /** Process the Fetch message. */
  protected void fetch(final Fetch msg) {
    process(new Executable() {
      public Object execute() {
        Client client = Client.getClient();
        RemoteCore c = client.getCore(msg.core());
        long onum = msg.onum();
        Glob g = cache.get(c, onum);
        
        if (g == null) {
          g = cache.get(c, onum, true);
        }
        
        reply(g, msg);
        return null;
      }
    });
  }
  
  protected void reply(Glob g, Fetch msg) {
    Fetch.Reply r = msg.new Reply(g.obj(), g.related());
    route(null, r, msg.sender());
  }
  
  /** Process a Fetch.Reply. */
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
  
  /** Called by a FetchManager to fetch the specified object. */
  public $Impl fetch(Core c, long onum) {
    outstandingFetch = new Fetch(getLocalHandle(), ((RemoteCore) c).name, onum);
    
    synchronized (outstandingFetch) {
      route(idf.buildId(outstandingFetch.toString()), outstandingFetch, null);
      
      try {
        outstandingFetch.wait();
      } catch (InterruptedException e) {}
      
      try {
        return outstandingFetch.reply().obj().deserialize(c);
      } catch (ClassNotFoundException e) {}
    }
    
    return null;
  }

  public boolean forward(RouteMessage message) {
    Message m = message.getMessage();
    
    if (m instanceof Fetch) {
      return forward((Fetch) m);
    } else if (m instanceof Fetch.Reply) {
      return forward((Fetch.Reply) m);
    }
    
    return true;
  }
  
  protected boolean forward(Fetch msg) {
    if (!msg.refresh()) {
      Client client = Client.getClient();
      RemoteCore c = client.getCore(msg.core());
      long onum = msg.onum();
      Glob g = cache.get(c, onum);
      
      if (g != null) {
        reply(g, msg);
        return false;
      }
    }
    
    return true;
  }
  
  protected boolean forward(Fetch.Reply msg) {
    Client client = Client.getClient();
    RemoteCore c = client.getCore(msg.core());
    long onum = msg.onum();
    Glob g = new Glob(msg.obj(), msg.related());
    cache.put(c, onum, g);
    
    return true;
  }

  public void update(NodeHandle handle, boolean joined) {
  }

}
