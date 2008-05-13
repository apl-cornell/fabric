package fabric.dissemination.pastry;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import rice.Continuation;
import rice.Executable;
import rice.environment.Environment;
import rice.environment.params.Parameters;
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
import fabric.client.UnreachableCoreException;
import fabric.common.Pair;
import fabric.dissemination.Glob;
import fabric.dissemination.pastry.messages.AggregateInterval;
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
  
  protected int idDigits;
  protected int baseBits;
  
  /** Random source for random ids. */
  protected Random rand;
  
  /** The cache of fetched objects. */
  protected Cache cache;
  
  /** Outstanding fetch messages awaiting replies. */
  protected Map<Id, Fetch> outstanding;
  
  protected MessageDeserializer deserializer;
  
  /** Replication interval, in milliseconds. */
  protected long REPLICATION_INTERVAL = 10 * 60 * 1000;
  
  /** Aggregation interval, in milliseconds. */
  protected long AGGREGATION_INTERVAL = 20 * 60 * 1000;
  
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

    RoutingTable rt = node.getRoutingTable();
    baseBits = rt.baseBitLength();
    idDigits = rt.numRows();
    
    cache = new Cache();
    outstanding = new HashMap<Id, Fetch>();
    
    Environment env = node.getEnvironment();
    Parameters params = env.getParameters();
    
    try {
      REPLICATION_INTERVAL = params.getLong("replication_interval");
    } catch (NumberFormatException e) {}
    
    try {
      AGGREGATION_INTERVAL = params.getLong("aggregation_interval");
    } catch (NumberFormatException e) {}
    
    // self-scheduled messages for period tasks
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
  
  /** Called by a FetchManager to fetch the specified object. */
  public Glob fetch(RemoteCore c, long onum) {
    log.fine("Pastry dissem fetch request " + c + " " + onum);
    
    Glob g = cache.get(c, onum);
    
    if (g != null) {
      return g;
    }
    
    Id id = idf.buildRandomId(rand);
    Fetch f = new Fetch(localHandle(), id, c.name(), onum);
    
    synchronized (outstanding) {
      outstanding.put(id, f);
    }
    
    route(idf.buildId(f.toString()), f, null);
    
    synchronized (f) {
      if (f.reply() == null) {
        try { f.wait(1000); } catch (InterruptedException e) {}
      }
      
      if (f.reply() == null) {
        throw new UnreachableCoreException(c);
      }
      
      return f.reply().glob();
    }
  }
  
  /** 
   * Process a Fetch.Reply message. Saves the glob from the reply, and returns
   * it to the client if it is waiting for this object.
   */
  protected void fetch(final Fetch.Reply msg) {
    log.fine("Pastry dissem fetch reply");
    forward(msg);  // cache glob
    
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

  /** 
   * Process the Fetch message. See if we have the object asked for. If so,
   * return it to sender via a Fetch.Reply message.
   */
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
  
  /**
   * Helper function. Reply to a Fetch message with given glob. 
   */
  protected void reply(Glob g, Fetch msg) {
    g.touch();
    Fetch.Reply r = new Fetch.Reply(msg, g);
    route(null, r, msg.sender());
  }

  /**
   * Called once every replicate interval. This is the method responsible for
   * contacting replication deciders for this node. The deciders then reply
   * with the globs that they want to replicate to this node. This implements
   * the pull protocol of beehive, since deciders don't know who they should
   * replicate to, and depend on those node to contact them. When sending a
   * replicate message, a list of globs that this node already has is sent to
   * the decider, so that they are not sent again.
   */
  protected void replicateInterval() {
    process(new Executable() {
      public Object execute() {
        rice.pastry.Id me = (rice.pastry.Id) localHandle().getId();
        Set<Pair<Core, Long>> skip;
        
        // get the closest neighbors in the leafset because they are special
        // cases in the beehive replication protocol. this is because the home
        // node of a glob might not have the longest matching prefix (e.g.,
        // 199 might be replicated at 200, but share more digits with 190).
        // the leafset is used to help get a glob back to the node with the
        // longest prefix.
        LeafSet ls = node.getLeafSet();
        NodeHandle n1 = ls.get(-1);
        
        if (n1 != null) {
          skip = skipSet((rice.pastry.Id) n1.getId(), -1);
          Replicate msg = new Replicate(localHandle(), -1, skip);
          route(null, msg, n1);
        }
        
        NodeHandle n2 = ls.get(1);
        
        if (n2 != null && !n2.equals(n1)) {
          skip = skipSet((rice.pastry.Id) n2.getId(), -1);
          Replicate msg = new Replicate(localHandle(), -1, skip);
          route(null, msg, n2);
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
              NodeHandle n = rs.closestNode();
              skip = skipSet((rice.pastry.Id) n.getId(), k - i - 1);
              Replicate msg = new Replicate(localHandle(), k - i - 1, skip);
              route(null, msg, n);
            }
          }
        }
        
        return null;
      }
    });
  }
  
  /**
   * Builds a set of oids that do not need to be sent again by a decider.
   * 
   * @param deciderId Pastry id of the decider.
   * @param level Level under which globs are asked to be replicated.
   * @return A set of oids that don't need to be replicated again.
   */
  private Set<Pair<Core, Long>> skipSet(rice.pastry.Id deciderId, int level) {
    rice.pastry.Id me = (rice.pastry.Id) localHandle().getId();
    Set<Pair<Core, Long>> skip = new HashSet<Pair<Core, Long>>();
    
    for (Pair<Core, Long> k : cache.keys()) {
      rice.pastry.Id id = (rice.pastry.Id) idf.buildId(k.first + "/" + k.second);
      boolean send = shouldReplicate(deciderId, me, id, level);
      
      if (send) {
        skip.add(k);
      }
    }
    
    return skip;
  }
  
  /**
   * Processes a Replicate message. Sends back to the sender objects that
   * should be replicated to that node.
   */
  protected void replicate(final Replicate msg) {
    process(new Executable() {
      public Object execute() {
        NodeHandle sender = msg.sender();
        rice.pastry.Id senderId = (rice.pastry.Id) sender.getId();
        int level = msg.level();
        Set<Pair<Core, Long>> skip = msg.skip();
        
        rice.pastry.Id me = (rice.pastry.Id) localHandle().getId();
        
        Map<Pair<Core, Long>, Glob> globs = 
          new HashMap<Pair<Core, Long>, Glob>();
        
        for (Pair<Core, Long> k : cache.sortedKeys()) {
          if (skip.contains(k)) {
            continue;
          }
          
          rice.pastry.Id id = (rice.pastry.Id) idf.buildId(k.first + "/" + k.second);
          boolean send = shouldReplicate(me, senderId, id, level);
          
          if (send) {
            RemoteCore c = (RemoteCore) k.first;
            Long onum = k.second;
            Glob g = cache.get(c, onum);
            
            globs.put(k, g);
            
            // XXX hack. limit reply message to 10 globs at a time. don't want
            // the message to get so large that pastry rejects it.
            if (globs.size() == 10) {
              break;
            }
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
   * Determines whether a glob should be replicated from a decider to a
   * receiver based on the level at which we want to replicate the object.
   * 
   * @param deciderId Pastry id of the decider.
   * @param receiverId Pastry id of the receiving node.
   * @param oid Object to replicate.
   * @param level Level at which we want to replicate.
   * @return true if that object should be replicated from decider to receiver.
   */
  private boolean shouldReplicate(rice.pastry.Id deciderId, 
      rice.pastry.Id receiverId, rice.pastry.Id oid, int level) {
    if (level != -1) {
      return oid.indexOfMSDD(deciderId, baseBits) < idDigits - level;
    } else {
      return oid.indexOfMSDD(receiverId, baseBits) <
        oid.indexOfMSDD(deciderId, baseBits);
    }
  }

  /**
   * Processes a Replicate.Reply message, and adds objects in the reply to the
   * cache.
   */
  protected void replicate(final Replicate.Reply msg) {
    process(new Executable() {
      public Object execute() {
        for (Map.Entry<Pair<Core, Long>, Glob> e : msg.globs().entrySet()) {
          RemoteCore c = (RemoteCore) e.getKey().first;
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
    // TODO decide whether to aggregate or let nodes decide whether to increase
    // object dissemination unilaterally.
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
    Glob g = msg.glob();
    cache.put(c, onum, g);
    
    return true;
  }

  public void update(NodeHandle handle, boolean joined) {
    // nothing to do
  }
  
  /**
   * Custom message deserializer for messages used by the pastry diseemination
   * node.
   */
  private class Deserializer implements MessageDeserializer {

    public Message deserialize(InputBuffer buf, short type, int priority,
        NodeHandle sender) throws IOException {
      switch (type) {
      case MessageType.FETCH:
        return new Fetch(buf, endpoint, sender);
      case MessageType.FETCH_REPLY:
        return new Fetch.Reply(buf, endpoint);
      case MessageType.REPLICATE:
        return new Replicate(buf, sender);
      case MessageType.REPLICATE_REPLY:
        return new Replicate.Reply(buf);
      }
      
      return null;
    }
    
  }

}
