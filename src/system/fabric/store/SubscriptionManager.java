package fabric.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import fabric.common.FabricThread;
import fabric.common.Logging;
import fabric.common.ObjectGroup;
import fabric.common.Threading;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyCache;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.store.db.GroupContainer;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;

/**
 * Keeps track of who's subscribed to what object. Handles subscriptions for a
 * single store.
 */
public class SubscriptionManager extends FabricThread.Impl {
  private static abstract class NotificationEvent
      extends Threading.NamedRunnable {
    public NotificationEvent(String name) {
      super(name);
    }

    @Override
    protected final void runImpl() {
      handle();
    }

    protected abstract void handle();
  }

  private final class ObjectUpdateEvent extends NotificationEvent {
    /**
     * The set of onums that were updated.
     */
    final LongSet onums;

    /**
     * The worker that updated the onums.
     */
    final RemoteWorker writer;

    public ObjectUpdateEvent(LongSet onums, RemoteWorker writer) {
      super("Fabric subscription manager object-update notifier");
      this.onums = onums;
      this.writer = writer;
    }

    @Override
    protected void handle() {
      // Go through the onums and figure out which workers are interested in
      // which updates.
      Map<RemoteWorker, List<Long>> onumsToNotify = new HashMap<>();
      Map<RemoteWorker, LongSet> groupedOnumsToSend = new HashMap<>();

      Map<RemoteWorker, List<ObjectGroup>> workerNotificationMap =
          new HashMap<>();
      Map<RemoteWorker, LongKeyMap<ObjectGlob>> dissemNotificationMap =
          new HashMap<>();

      for (LongIterator it = onums.iterator(); it.hasNext();) {
        long onum = it.next();
        GroupContainer groupContainer;
        ObjectGlob glob;
        try {
          // Skip if the onum represents a surrogate.
          if (tm.read(onum).isSurrogate()) continue;

          groupContainer = tm.getGroupContainer(onum);
          glob = groupContainer.getGlob();
        } catch (AccessException e) {
          throw new InternalError(e);
        }

        Set<Pair<RemoteWorker, Boolean>> subscribers = subscriptions.get(onum);
        if (subscribers != null) {
          synchronized (subscribers) {
            for (Iterator<Pair<RemoteWorker, Boolean>> subscriberIt =
                subscribers.iterator(); subscriberIt.hasNext();) {
              Pair<RemoteWorker, Boolean> subscriber = subscriberIt.next();

              RemoteWorker subscribingNode = subscriber.first;
              boolean isDissem = subscriber.second;

              if (subscribingNode == writer && !isDissem) continue;
              subscriberIt.remove();

              if (isDissem) {
                // Add group to dissemNotificationMap.
                LongKeyMap<ObjectGlob> globs =
                    dissemNotificationMap.get(subscribingNode);
                if (globs == null) {
                  globs = new LongKeyHashMap<>();
                  dissemNotificationMap.put(subscribingNode, globs);
                }

                globs.put(onum, glob);
              } else {
                // Add onum to onumsToNotify.
                List<Long> toNotify = onumsToNotify.get(subscribingNode);
                if (toNotify == null) {
                  toNotify = new ArrayList<>();
                  onumsToNotify.put(subscribingNode, toNotify);
                }
                toNotify.add(onum);

                // Check whether onum is already being sent.
                LongSet toSend = groupedOnumsToSend.get(subscribingNode);
                if (toSend == null) {
                  toSend = new LongHashSet();
                  groupedOnumsToSend.put(subscribingNode, toSend);
                }

                if (!toSend.contains(onum)) {
                  // Add group to workerNotificationMap.
                  ObjectGroup group =
                      groupContainer.getGroup(subscribingNode.getPrincipal());

                  List<ObjectGroup> groups =
                      workerNotificationMap.get(subscribingNode);
                  if (groups == null) {
                    groups = new ArrayList<>();
                    workerNotificationMap.put(subscribingNode, groups);
                  }

                  groups.add(group);

                  // Add group's onums to onumsToSend.
                  toSend.addAll(group.objects().keySet());
                }
              }
            }
          }
        }
      }

      // Notify the workers and resubscribe them.
      for (final Entry<RemoteWorker, List<ObjectGroup>> entry : workerNotificationMap
          .entrySet()) {
        final RemoteWorker worker = entry.getKey();
        final List<ObjectGroup> updates = entry.getValue();
        final List<Long> updatedOnums = onumsToNotify.get(worker);
        Threading.getPool().submit(new Runnable() {
          @Override
          public void run() {
            // Notify.
            List<Long> resubscriptions =
                worker.notifyObjectUpdates(updatedOnums, updates);
            resubscriptions.retainAll(new HashSet<>(updatedOnums));

            // Resubscribe.
            for (long onum : resubscriptions) {
              subscribe(onum, worker, false);
            }
          }
        });
      }

      // Notify the dissemination nodes and resubscribe them.
      for (final Entry<RemoteWorker, LongKeyMap<ObjectGlob>> entry : dissemNotificationMap
          .entrySet()) {
        final RemoteWorker dissemNode = entry.getKey();
        final LongKeyMap<ObjectGlob> updates = entry.getValue();
        Threading.getPool().submit(new Runnable() {
          @Override
          public void run() {
            // Notify.
            List<Long> resubscriptions =
                dissemNode.notifyObjectUpdates(store, updates);

            // Resubscribe.
            for (long onum : resubscriptions) {
              if (updates.containsKey(onum)) {
                subscribe(onum, dissemNode, true);
              }
            }
          }
        });
      }
    }
  }

  private final BlockingQueue<NotificationEvent> notificationQueue;

  /**
   * The set of nodes subscribed to each onum. The second component of each pair
   * indicates whether the node is subscribed as a dissemination node. (true =
   * dissemination, false = worker)
   */
  private final LongKeyCache<Set<Pair<RemoteWorker, Boolean>>> subscriptions;

  /**
   * The name of the store for which we are managing subscriptions.
   */
  private final String store;

  /**
   * The transaction manager corresponding to the store for which we are
   * managing subscriptions.
   */
  private final TransactionManager tm;

  /**
   * @param tm
   *          The transaction manager corresponding to the store for which
   *          subscriptions are to be managed.
   */
  public SubscriptionManager(String store, TransactionManager tm) {
    super("subscription manager for store " + store);
    this.store = store;
    this.notificationQueue = new ArrayBlockingQueue<>(50);
    this.tm = tm;
    this.subscriptions = new LongKeyCache<>();

    start();
  }

  @Override
  public void run() {
    while (true) {
      try {
        Threading.getPool().submit(notificationQueue.take());
      } catch (InterruptedException e1) {
        Logging.logIgnoredInterruptedException(e1);
      }
    }
  }

  /**
   * Subscribes the given worker to the given onum.
   *
   * @param dissemSubscribe
   *          If true, then the given subscriber will be subscribed as a
   *          dissemination node; otherwise it will be subscribed as a worker.
   */
  public void subscribe(long onum, RemoteWorker worker,
      boolean dissemSubscribe) {
    Set<Pair<RemoteWorker, Boolean>> subscribers = subscriptions.get(onum);
    if (subscribers == null) {
      subscribers = new HashSet<>();
      Set<Pair<RemoteWorker, Boolean>> existing =
          subscriptions.putIfAbsent(onum, subscribers);
      if (existing != null) subscribers = existing;
    }

    synchronized (subscribers) {
      subscribers.add(new Pair<>(worker, dissemSubscribe));
    }
  }

  /**
   * Notifies the subscription manager that a set of objects has been updated by a
   * particular worker.
   */
  public void notifyUpdate(LongSet onums, RemoteWorker worker) {
    if (!Worker.getWorker().config.useSubscriptions) return;

    notificationQueue.offer(new ObjectUpdateEvent(onums, worker));
  }
}
