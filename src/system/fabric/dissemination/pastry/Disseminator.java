package fabric.dissemination.pastry;

import static fabric.common.Logging.MISC_LOGGER;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

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
import fabric.common.FastSerializable;
import fabric.common.Logging;
import fabric.common.util.Cache;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.dissemination.AbstractGlob;
import fabric.dissemination.ObjectGlob;
import fabric.dissemination.pastry.messages.AbstractUpdate;
import fabric.dissemination.pastry.messages.AggregateInterval;
import fabric.dissemination.pastry.messages.Fetch;
import fabric.dissemination.pastry.messages.RawMessageType;
import fabric.dissemination.pastry.messages.Replicate;
import fabric.dissemination.pastry.messages.ReplicateInterval;
import fabric.worker.RemoteStore;
import fabric.worker.Worker;

/**
 * A pastry application that implements the functionality of a Fabric
 * dissemination network based on the beehive replication protocol.
 */
public class Disseminator implements Application {

  /** The pastry node. */
  protected final PastryNode node;

  /** The pastry endpoint. */
  protected final Endpoint endpoint;

  /** The pastry id generating factory. */
  protected final IdFactory idf;

  protected final int idDigits;
  protected final int baseBits;

  /** Random source for random ids. */
  protected final Random rand;

  /** The cache of fetched objects. */
  protected final fabric.dissemination.Cache cache;

  /** Outstanding fetch messages awaiting replies. */
  protected final Cache<Id, Fetch> outstandingFetches;

  /**
   * Associates update-message IDs with the queues on which replies should be
   * added.
   */
  private final Cache<Id, BlockingQueue<AbstractUpdate.Reply>> outstandingUpdates;

  protected final MessageDeserializer deserializer;

  /** Replication interval, in milliseconds. */
  protected long REPLICATION_INTERVAL = 10 * 60 * 1000;

  /** Aggregation interval, in milliseconds. */
  protected long AGGREGATION_INTERVAL = 20 * 60 * 1000;

  /** The set of nodes subscribed to each OID. */
  private final Cache<Pair<RemoteStore, Long>, Set<NodeHandle>> subscriptions;

  /**
   * Creates a disseminator attached to the given pastry node.
   *
   * @param node
   *          PastryNode where the disseminator is to run.
   */
  public Disseminator(PastryNode node, fabric.dissemination.Cache cache) {
    this.subscriptions = new Cache<>();
    this.node = node;
    endpoint = node.buildEndpoint(this, null);
    deserializer = new Deserializer();
    endpoint.setDeserializer(deserializer);
    idf = new PastryIdFactory(node.getEnvironment());
    rand = new Random();

    RoutingTable rt = node.getRoutingTable();
    baseBits = rt.baseBitLength();
    idDigits = rt.numRows();

    this.cache = cache;
    outstandingFetches = new Cache<>();
    outstandingUpdates = new Cache<>();

    Environment env = node.getEnvironment();
    Parameters params = env.getParameters();

    try {
      REPLICATION_INTERVAL = params.getLong("replication_interval");
    } catch (NumberFormatException e) {
    }

    try {
      AGGREGATION_INTERVAL = params.getLong("aggregation_interval");
    } catch (NumberFormatException e) {
    }

    // self-scheduled messages for period tasks
    endpoint.scheduleMessage(new ReplicateInterval(), REPLICATION_INTERVAL,
        REPLICATION_INTERVAL);
    endpoint.scheduleMessage(new AggregateInterval(), AGGREGATION_INTERVAL,
        AGGREGATION_INTERVAL);

    endpoint.register();
    MISC_LOGGER.info("Pastry disseminator created");
  }

  private static final Continuation<Object, Exception> halt =
      new Continuation<Object, Exception>() {
        @Override
        public void receiveException(Exception result) {
        }

        @Override
        public void receiveResult(Object result) {
        }
      };

  /**
   * Schedules a task on the task processing thread. When messages are received,
   * handlers should run on the processing thread so as not to block the message
   * receiving thread.
   *
   * @param task
   *          the task to run.
   */
  private void process(Executable<Void, RuntimeException> task) {
    endpoint.process(task, halt);
  }

  /**
   * Routes a message on the pastry ring. At least one of id or hint must be
   * non-null.
   *
   * @param id
   *          The id of this message (hash value where it should be routed)
   * @param message
   *          The message to be routed
   * @param hint
   *          NodeHandle of a starting node, if desired
   */
  protected void route(Id id, Message message, NodeHandle hint) {
    endpoint.route(id, message, hint);
  }

  /** The NodeHandle of this pastry node. */
  protected NodeHandle localHandle() {
    return endpoint.getLocalNodeHandle();
  }

  @Override
  public void deliver(Id id, Message msg) {
    if (msg instanceof fabric.dissemination.pastry.messages.Message) {
      ((fabric.dissemination.pastry.messages.Message) msg).dispatch(this);
      return;
    }

    throw new InternalError("Unknown Pastry message: " + msg);
  }

  /**
   * Called by a FetchManager to fetch the specified object.
   *
   * @throws DisseminationTimeoutException
   *           if the dissemination network takes too long.
   */
  public ObjectGlob fetch(RemoteStore c, long onum)
      throws DisseminationTimeoutException {
    fabric.dissemination.Cache.Entry entry = cache.get(c, onum);

    if (entry != null) {
      return entry.objectGlob;
    }

    Id id = idf.buildRandomId(rand);
    Fetch f = new Fetch(localHandle(), id, c.name(), onum);

    outstandingFetches.put(id, f);
    route(idf.buildId(f.toString()), f, null);

    synchronized (f) {
      if (f.reply() == null) {
        // XXX hack: wait at most 1 second for dissemination network. after
        // that, we fall back to the next fetch manager.
        try {
          f.wait(1000);
        } catch (InterruptedException e) {
          Logging.logIgnoredInterruptedException(e);
        }
      }

      if (f.reply() == null) {
        throw new DisseminationTimeoutException();
      }

      return f.reply().glob();
    }
  }

  /**
   * Process a Fetch.Reply message. Saves the glob from the reply, and returns
   * it to the worker if it is waiting for this object.
   */
  public void fetch(final Fetch.Reply msg) {
    forward(msg); // caches glob

    process(new Executable<Void, RuntimeException>() {
      @Override
      public Void execute() {
        Fetch f = outstandingFetches.remove(msg.id());

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
  public void fetch(final Fetch msg) {
    process(new Executable<Void, RuntimeException>() {
      @Override
      public Void execute() {
        Worker worker = Worker.getWorker();
        RemoteStore c = worker.getStore(msg.store());
        long onum = msg.onum();

        // Subscribe the remote endpoint.
        subscribe(msg.sender(), c, onum);

        fabric.dissemination.Cache.Entry entry = cache.get(c, onum, true);
        reply(entry, msg);
        return null;
      }
    });
  }

  /**
   * Subscribes the given node to the given OID.
   */
  private void subscribe(NodeHandle node, RemoteStore store, long onum) {
    // Ignore the local node.
    if (node.equals(localHandle())) return;

    Pair<RemoteStore, Long> key = new Pair<>(store, onum);
    while (true) {
      Set<NodeHandle> subscribers = new HashSet<>();
      Set<NodeHandle> existingSubscribers =
          subscriptions.putIfAbsent(key, subscribers);
      if (existingSubscribers != null) subscribers = existingSubscribers;

      synchronized (subscribers) {
        subscribers.add(node);
        if (subscriptions.get(key) == subscribers) return;
      }
    }
  }

  /**
   * Unsubscribes the given node from the given OID.
   */
  private void unsubscribe(NodeHandle node, RemoteStore store, long onum) {
    Pair<RemoteStore, Long> key = new Pair<>(store, onum);
    Set<NodeHandle> subscribers = subscriptions.get(key);
    if (subscribers == null) return;

    synchronized (subscribers) {
      subscribers.remove(node);
      if (subscribers.isEmpty()) subscriptions.remove(key, subscribers);
    }
  }

  /**
   * Helper function. Reply to a Fetch message with given cache entry.
   */
  protected void reply(fabric.dissemination.Cache.Entry entry, Fetch msg) {
    entry.touch();
    Fetch.Reply r = new Fetch.Reply(msg, entry.objectGlob);
    route(null, r, msg.sender());
  }

  /**
   * Updates the dissemination and worker caches and pushes the update through
   * the dissemination layer.
   *
   * @return true iff there was a dissemination-cache entry for the given oid or
   *          if the update was forwarded to another node.
   */
  <UpdateType extends FastSerializable> boolean updateCaches(RemoteStore store,
      long onum, AbstractGlob<UpdateType> update) {
    // Update the local caches.
    boolean result = update.updateCache(cache, store, onum);

    // Find the set of subscribers.
    Set<NodeHandle> subscribers = subscriptions.get(new Pair<>(store, onum));
    if (subscribers != null) {
      int numSubscribers;
      BlockingQueue<AbstractUpdate.Reply> replyQueue;
      synchronized (subscribers) {
        numSubscribers = subscribers.size();
        if (numSubscribers == 0) return result;

        replyQueue = new ArrayBlockingQueue<>(numSubscribers);

        // Forward to subscribers.
        for (NodeHandle subscriber : subscribers) {
          Id id = idf.buildRandomId(rand);
          AbstractUpdate<UpdateType> msg =
              AbstractUpdate.getInstance(localHandle(), id, store.name(), onum,
                  update);

          // TODO: Set up a timeout mechanism that removes entries for dead nodes.
          outstandingUpdates.put(id, replyQueue);

          route(null, msg, subscriber);
        }
      }

      // Wait until we know we're subscribed, we get all replies back, or our
      // update messages time out.
      // TODO: Set up a timeout mechanism that unsubscribes dead nodes.
      for (int i = 0; !result && i < numSubscribers; i++) {
        try {
          // Wait at most one second for a reply.
          AbstractUpdate.Reply reply = replyQueue.poll(1, TimeUnit.SECONDS);
          result = reply != null && reply.resubscribe();
        } catch (InterruptedException e) {
          Logging.logIgnoredInterruptedException(e);
        }
      }

    }

    return result;
  }

  /**
   * Processes the given UpdateCache message. Updates the local cache and
   * forwards the update to any subscribers.
   */
  public <UpdateType extends FastSerializable> void updateCache(
      final AbstractUpdate<UpdateType> msg) {
    process(new Executable<Void, RuntimeException>() {
      @Override
      public Void execute() throws RuntimeException {
        Worker worker = Worker.getWorker();
        RemoteStore store = worker.getStore(msg.store());
        long onum = msg.onum();
        AbstractGlob<UpdateType> update = msg.update();

        boolean result = updateCaches(store, onum, update);
        AbstractUpdate.Reply reply =
            new AbstractUpdate.Reply(localHandle(), msg, result);
        route(null, reply, msg.sender());

        return null;
      }
    });
  }

  /**
   * Processes the given UpdateCache.Reply message.
   */
  public void updateCache(final AbstractUpdate.Reply msg) {
    process(new Executable<Void, RuntimeException>() {
      @Override
      public Void execute() throws RuntimeException {
        Worker worker = Worker.getWorker();
        RemoteStore store = worker.getStore(msg.store());
        long onum = msg.onum();
        boolean resubscribe = msg.resubscribe();

        if (!resubscribe) {
          unsubscribe(msg.sender(), store, onum);
        }

        BlockingQueue<AbstractUpdate.Reply> responses =
            outstandingUpdates.remove(msg.id());
        if (responses != null) responses.offer(msg);

        return null;
      }
    });
  }

  /**
   * Called once every replicate interval. This is the method responsible for
   * contacting replication deciders for this node. The deciders then reply with
   * the globs that they want to replicate to this node. This implements the
   * pull protocol of beehive, since deciders don't know who they should
   * replicate to, and depend on those node to contact them. When sending a
   * replicate message, a list of globs that this node already has is sent to
   * the decider, so that they are not sent again.
   */
  public void replicateInterval() {
    process(new Executable<Void, RuntimeException>() {
      @Override
      public Void execute() {
        rice.pastry.Id me = (rice.pastry.Id) localHandle().getId();
        OidKeyHashMap<Long> skip;

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
        int numDigits = rt.numRows();

        // find deciders for each replication level
        for (int i = 0; i < numDigits; i++) {
          int d = me.getDigit(i, rt.baseBitLength());

          for (int j = 0; j < rt.numColumns(); j++) {
            if (j == d) {
              continue;
            }

            // rs contains possible deciders for level numDigits - i - 1, when
            // the next digit that differs is j
            RouteSet rs = rt.getRouteSet(i, j);
            int level = numDigits - i - 1;

            if (rs != null && rs.size() > 0) {
              NodeHandle n = rs.closestNode();
              skip = skipSet((rice.pastry.Id) n.getId(), level);
              Replicate msg = new Replicate(localHandle(), level, skip);
              route(null, msg, n);
            }
          }
        }

        return null;
      }
    });
  }

  /**
   * Builds a set of (oid, glob timestamp) pairs that do not need to be sent
   * again by a decider.
   *
   * @param deciderId
   *          Pastry id of the decider.
   * @param level
   *          Level under which globs are asked to be replicated.
   * @return A set of oids that don't need to be replicated again.
   */
  private OidKeyHashMap<Long> skipSet(rice.pastry.Id deciderId, int level) {
    rice.pastry.Id me = (rice.pastry.Id) localHandle().getId();
    OidKeyHashMap<Long> skip = new OidKeyHashMap<>();

    for (Pair<Pair<RemoteStore, Long>, Long> k : cache.timestamps()) {
      rice.pastry.Id id =
          (rice.pastry.Id) idf.buildId(k.first + "/" + k.second);
      boolean send = shouldReplicate(deciderId, me, id, level);

      if (send) {
        skip.put(k.first.first, k.first.second, k.second);
      }
    }

    return skip;
  }

  /**
   * Processes a Replicate message. Sends back to the sender objects that should
   * be replicated to that node.
   */
  public void replicate(final Replicate msg) {
    process(new Executable<Void, RuntimeException>() {
      @Override
      public Void execute() {
        NodeHandle sender = msg.sender();
        rice.pastry.Id senderId = (rice.pastry.Id) sender.getId();
        int level = msg.level();
        OidKeyHashMap<Long> skip = msg.skip();

        rice.pastry.Id me = (rice.pastry.Id) localHandle().getId();

        Map<Pair<RemoteStore, Long>, ObjectGlob> globs = new HashMap<>();

        for (Pair<Pair<RemoteStore, Long>, Long> k : cache.sortedTimestamps()) {
          Long skipTimestamp = skip.get(k.first.first, k.first.second);
          if (skipTimestamp != null && skipTimestamp >= k.second) {
            continue;
          }

          rice.pastry.Id id =
              (rice.pastry.Id) idf.buildId(k.first + "/" + k.second);
          boolean send = shouldReplicate(me, senderId, id, level);

          if (send) {
            RemoteStore c = k.first.first;
            Long onum = k.first.second;
            fabric.dissemination.Cache.Entry entry = cache.get(c, onum);
            if (entry.level() > level) continue;

            globs.put(k.first, entry.objectGlob);

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
   * Determines whether a glob should be replicated from a decider to a receiver
   * based on the level at which we want to replicate the object.
   *
   * @param deciderId
   *          Pastry id of the decider.
   * @param receiverId
   *          Pastry id of the receiving node.
   * @param oid
   *          Object to replicate.
   * @param level
   *          Level at which we want to replicate.
   * @return true if that object should be replicated from decider to receiver.
   */
  private boolean shouldReplicate(rice.pastry.Id deciderId,
      rice.pastry.Id receiverId, rice.pastry.Id oid, int level) {
    if (level != -1) {
      return oid.indexOfMSDD(deciderId, baseBits) < idDigits - level;
    } else {
      return oid.indexOfMSDD(receiverId, baseBits) < oid.indexOfMSDD(deciderId,
          baseBits);
    }
  }

  /**
   * Processes a Replicate.Reply message, and adds objects in the reply to the
   * cache.
   */
  public void replicate(final Replicate.Reply msg) {
    process(new Executable<Void, RuntimeException>() {
      @Override
      public Void execute() {
        for (Map.Entry<Pair<RemoteStore, Long>, ObjectGlob> e : msg.globs()
            .entrySet()) {
          RemoteStore c = e.getKey().first;
          long onum = e.getKey().second;
          ObjectGlob g = e.getValue();
          cache.put(c, onum, g);
        }

        return null;
      }
    });
  }

  /** Called once every aggregation interval. */
  public void aggregateInterval() {
    // TODO decide whether to aggregate or let nodes decide whether to increase
    // object dissemination unilaterally.
  }

  @Override
  public boolean forward(RouteMessage message) {
    try {
      Message m = message.getMessage(deserializer);

      if (m instanceof Fetch) {
        return forward((Fetch) m);
      } else if (m instanceof Fetch.Reply) {
        return forward((Fetch.Reply) m);
      }
    } catch (IOException e) {
    }

    return true;
  }

  /**
   * See if we should keep routing the given Fetch message or if we can reply to
   * it using our cache.
   *
   * @param msg
   *          the Fetch message
   * @return true if message should be further routed
   */
  protected boolean forward(Fetch msg) {
    Worker worker;
    try {
      worker = Worker.getWorker();
    } catch (IllegalStateException e) {
      // Worker not initialized yet.
      return true;
    }

    RemoteStore c = worker.getStore(msg.store());
    long onum = msg.onum();
    fabric.dissemination.Cache.Entry entry = cache.get(c, onum);

    if (entry != null) {
      // Subscribe the remote endpoint.
      subscribe(msg.sender(), c, onum);

      reply(entry, msg);
      return false;
    }

    return true;
  }

  /**
   * Cache glob from Fetch.Reply if we don't already have it.
   *
   * @param msg
   *          the Fetch.Reply message
   * @return always true, indicating message should be further routed
   */
  protected boolean forward(Fetch.Reply msg) {
    Worker worker = Worker.getWorker();
    RemoteStore c = worker.getStore(msg.store());
    long onum = msg.onum();
    ObjectGlob g = msg.glob();

    if (g != null) cache.put(c, onum, g);

    return true;
  }

  @Override
  public void update(NodeHandle handle, boolean joined) {
    // nothing to do
  }

  /**
   * Custom message deserializer for messages used by the pastry dissemination
   * node.
   */
  private class Deserializer implements MessageDeserializer {

    @Override
    public Message deserialize(InputBuffer buf, short type, int priority,
        NodeHandle sender) throws IOException {
      try {
        RawMessageType messageType = RawMessageType.values()[type];
        return messageType.parse(buf, endpoint, sender);
      } catch (ArrayIndexOutOfBoundsException e) {
        throw new IOException("Unrecognized message type: " + type);
      }
    }

  }

}
