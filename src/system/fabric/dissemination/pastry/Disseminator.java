package fabric.dissemination.pastry;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import rice.Continuation;
import rice.Executable;
import rice.p2p.commonapi.Application;
import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.IdFactory;
import rice.p2p.commonapi.Message;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.RouteMessage;
import rice.p2p.commonapi.rawserialization.InputBuffer;
import rice.p2p.commonapi.rawserialization.MessageDeserializer;
import rice.pastry.PastryNode;
import rice.pastry.commonapi.PastryIdFactory;
import rice.pastry.leafset.LeafSet;
import rice.pastry.routing.RouteSet;
import rice.pastry.routing.RoutingTable;
import fabric.client.Client;
import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.common.Pair;
import fabric.dissemination.Glob;
import fabric.dissemination.pastry.messages.AggregateInterval;
import fabric.dissemination.pastry.messages.DataInputBuffer;
import fabric.dissemination.pastry.messages.Fetch;
import fabric.dissemination.pastry.messages.MessageType;
import fabric.dissemination.pastry.messages.Replicate;
import fabric.dissemination.pastry.messages.ReplicateInterval;

/**
 * A pastry application that implements the functionality of a Fabric
 * dissemination network based on the beehive replication protocol.
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
  
  protected MessageDeserializer deserializer;
  
  /** Replication interval, in milliseconds. */
  protected static long REPLICATION_INTERVAL = 10 * 60 * 1000;
  
  /** Aggregation interval, in milliseconds. */
  protected static long AGGREGATION_INTERVAL = 20 * 60 * 1000;
  
  private Logger log = Logger.getLogger("fabric.dissem.pastry");
  
  /**
   * Creates a disseminator attached to the given pastry node.
   * 
   * @param node PastryNode where the disseminator is to run.
   */
  public Disseminator(PastryNode node) {
    this.node = node;
    endpoint = node.buildEndpoint(this, null);
    deserializer = new Deserializer();
    endpoint.setDeserializer(deserializer);
    idf = new PastryIdFactory(node.getEnvironment());
    rand = new Random();
    
    cache = new Cache();
    outstanding = new HashMap<Id, Fetch>();
    
    endpoint.scheduleMessage(new ReplicateInterval(), 
        REPLICATION_INTERVAL, REPLICATION_INTERVAL);
    endpoint.scheduleMessage(new AggregateInterval(), 
        AGGREGATION_INTERVAL, AGGREGATION_INTERVAL);

    endpoint.register();
    log.info("Pastry disseminator created");
  }

  private static final Continuation halt = new Continuation() {
    public void receiveException(Exception result) {}
    public void receiveResult(Object result) {}
  };

  /**
    * Schedules a task on the task processing thread. When messages are
    * received, handlers should run on the processing thread so as not to
    * block the message receiving thread.
    * 
    * @param tast the task to run.
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
  }

  /** The NodeHandle of this pastry node. */
  protected NodeHandle localHandle() {
    return endpoint.getLocalNodeHandle();
  }

  public void deliver(Id id, Message msg) {
    if (msg instanceof Fetch) {
      fetch((Fetch) msg);
    } else if (msg instanceof Fetch.Reply) {
      fetch((Fetch.Reply) msg);
    } else if (msg instanceof ReplicateInterval) {
      replicateInterval();
    } else if (msg instanceof Replicate) {
      replicate((Replicate) msg);
    } else if (msg instanceof Replicate.Reply) {
      replicate((Replicate.Reply) msg);
    } else if (msg instanceof AggregateInterval) {
      aggregateInterval();
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
    g.touch();
    Fetch.Reply r = new Fetch.Reply(msg, g);
    route(null, r, msg.sender());
  }
  
  /** Process a Fetch.Reply. */
  protected void fetch(final Fetch.Reply msg) {
    log.fine("Pastry dissem fetch reply");
    
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
  public Glob fetch(Core c, long onum) {
    log.fine("Pastry dissem fetch request " + c + " " + onum);
    
    Id id = idf.buildRandomId(rand);
    Fetch f = new Fetch(localHandle(), id, ((RemoteCore) c).name, onum);
    
    synchronized (outstanding) {
      outstanding.put(id, f);
    }
    
    route(idf.buildId(f.toString()), f, null);
    
    synchronized (f) {
      while (f.reply() == null) {
        try { f.wait(); } catch (InterruptedException e) {}
      }
      
      return f.reply().glob();
    }
  }
  
  /** Called once every replicate interval. */
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
  
  /**
   * Processes a Replicate message. It sends back to the sender objects that
   * should be replicated to the sender.
   */
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

  /**
   * Processes a Replicate.Reply message, and adds objects in the reply to the
   * cache.
   */
  protected void replicate(final Replicate.Reply msg) {
    process(new Executable() {
      public Object execute() {
        Client client = Client.getClient();
        
        for (Map.Entry<Pair<String, Long>, Glob> e : msg.globs().entrySet()) {
          RemoteCore c = client.getCore(e.getKey().first);
          long onum = e.getKey().second;
          Glob g = e.getValue();
          cache.put(c, onum, g);
        }
        
        return null;
      }
    });
  }
  
  /** Called once every aggregation interval. */
  protected void aggregateInterval() {
    // TODO
  }
  
  public boolean forward(RouteMessage message) {
    try {
      Message m = message.getMessage(deserializer);
      
      if (m instanceof Fetch) {
        return forward((Fetch) m);
      } else if (m instanceof Fetch.Reply) {
        return forward((Fetch.Reply) m);
      }
    } catch (IOException e) {}
    
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
  
  private class Deserializer implements MessageDeserializer {

    public Message deserialize(InputBuffer buf, short type, int priority,
        NodeHandle sender) throws IOException {
      DataInputBuffer b = new DataInputBuffer(buf);
      
      switch (type) {
      case MessageType.FETCH:
        return new Fetch(b, endpoint, sender);
      case MessageType.FETCH_REPLY:
        return new Fetch.Reply(b, endpoint);
      }
      
      return null;
    }
    
  }

}
