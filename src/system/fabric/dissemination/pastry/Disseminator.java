package fabric.dissemination.pastry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
import rice.pastry.leafset.LeafSet;
import rice.pastry.routing.RouteSet;
import rice.pastry.routing.RoutingTable;
import fabric.client.Client;
import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.common.Pair;
import fabric.dissemination.pastry.messages.Fetch;
import fabric.dissemination.pastry.messages.Replicate;
import fabric.dissemination.pastry.messages.ReplicateInterval;
import fabric.lang.Object.$Impl;

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
  
  protected Random rand;
  
  /** The cache of fetched objects. */
  protected Cache cache;
  
  /** Outstanding fetch messages awaiting replies. */
  protected Map<Id, Fetch> outstanding;
  
  public Disseminator(PastryNode node) {
    this.node = node;
    endpoint = node.buildEndpoint(this, null);
    endpoint.register();
    idf = new PastryIdFactory(node.getEnvironment());
    rand = new Random();
    
    cache = new Cache();
    outstanding = new HashMap<Id, Fetch>();
    
    endpoint.scheduleMessage(new ReplicateInterval(), 60000, 60000);
  }

  private static final Continuation halt = new Continuation() {
    public void receiveException(Exception result) {}
    public void receiveResult(Object result) {}
  };

  /**
    * Schedules a task on the task processing thread. When messages are
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
  protected NodeHandle localHandle() {
    return endpoint.getLocalNodeHandle();
  }

  public void deliver(Id id, Message message) {
    if (message instanceof Fetch) {
      fetch((Fetch) message);
    } else if (message instanceof Fetch.Reply) {
      fetch((Fetch.Reply) message);
    } else if (message instanceof ReplicateInterval) {
      replicateInterval();
    } else if (message instanceof Replicate) {
      
    } else if (message instanceof Replicate.Reply) {
      
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
  
  /** Reply to a Fetch message with given glob. */
  protected void reply(Glob g, Fetch msg) {
    Fetch.Reply r = msg.new Reply(g.obj(), g.related());
    route(null, r, msg.sender());
  }
  
  /** Process a Fetch.Reply. */
  protected void fetch(final Fetch.Reply msg) {
    process(new Executable() {
      public Object execute() {
        Fetch f = null;
        
        synchronized (outstanding) {
          f = outstanding.remove(msg.id());
        }
        
        if (f != null) {
          synchronized (f) {
            f.reply(msg);
            f.notifyAll();
          }
        }
        
        return null;
      }
    });
  }
  
  /** Called by a FetchManager to fetch the specified object. */
  public $Impl fetch(Core c, long onum) {
    Id id = idf.buildRandomId(rand);
    Fetch f = new Fetch(localHandle(), id, ((RemoteCore) c).name, onum);
    
    synchronized (outstanding) {
      outstanding.put(id, f);
    }
    
    route(idf.buildId(f.toString()), f, null);
    
    synchronized (f) {
      try {
        f.wait();
      } catch (InterruptedException e) {}
      
      try {
        return f.reply().obj().deserialize(c);
      } catch (ClassNotFoundException e) {}
    }
    
    return null;
  }
  
  /**
   * Called once every replicate interval.
   */
  protected void replicateInterval() {
    process(new Executable() {
      public Object execute() {
        rice.pastry.Id me = (rice.pastry.Id) localHandle().getId();
        
        LeafSet ls = node.getLeafSet();
        NodeHandle n = ls.get(-1);
        
        if (n != null) {
          Replicate msg = new Replicate(localHandle(), -1);
          route(null, msg, n);
        }
        
        NodeHandle m = ls.get(1);
        
        if (m != null && m != n) {
          Replicate msg = new Replicate(localHandle(), -1);
          route(null, msg, m);
        }
        
        RoutingTable rt = node.getRoutingTable();
        int k = rt.numRows();

        for (int i = 0; i < k; i++) {
          int d = me.getDigit(i, rt.baseBitLength());

          for (int j = 0; j < rt.numColumns(); j++) {
            if (j == d) {
              continue;
            }

            RouteSet rs = rt.getRouteSet(i, j);

            if (rs != null && rs.size() > 0) {
              n = rs.closestNode();
              Replicate msg = new Replicate(localHandle(), k - i - 1);
              route(null, msg, n);
            }
          }
        }
        
        return null;
      }
    });
  }
  
  protected void replicate(final Replicate msg) {
    process(new Executable() {
      public Object execute() {
        NodeHandle sender = msg.sender();
        int level = msg.level();
        rice.pastry.Id me = (rice.pastry.Id) localHandle().getId();
        RoutingTable rt = node.getRoutingTable();
        byte baselen = rt.idBaseBitLength;
        int idlen = rt.numRows();
        
        Map<Pair<String, Long>, Glob> globs = 
          new HashMap<Pair<String, Long>, Glob>();
        
        for (Pair<Core, Long> k : cache.map.keySet()) {
          rice.pastry.Id id = (rice.pastry.Id) idf.buildId(k.first + "/" + k.second);
          boolean send = false;
          
          if (level != -1) {
            send = id.indexOfMSDD(me, baselen) < idlen - level;
          } else {
            send = id.indexOfMSDD((rice.pastry.Id) sender.getId(), baselen) <
              id.indexOfMSDD(me, baselen);
          }
          
          if (send) {
            RemoteCore c = (RemoteCore) k.first;
            Long onum = k.second;
            Glob g = cache.get(c, onum);
            
            globs.put(new Pair<String, Long>(c.name(), onum), g);
          }
        }
        
        if (globs.size() > 0) {
          Replicate.Reply r = new Replicate.Reply(globs);
          route(null, r, sender);
        }
        
        return null;
      }
    });
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
  
  /**
   * See if we should keep routing the given Fetch message or if we can reply
   * to it using our cache.
   * 
   * @param msg the Fetch message
   * @return true if message should be further routed
   */
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
  
  /**
   * Cache glob from Fetch.Reply if we don't already have it.
   * 
   * @param msg the Fetch.Reply message
   * @return always true, indicating message should be further routed
   */
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
