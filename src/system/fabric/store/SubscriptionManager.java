package fabric.store;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import fabric.common.FabricThread;
import fabric.common.ObjectGroup;
import fabric.common.Threading;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyCache;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.dissemination.Glob;
import fabric.net.UnreachableNodeException;
import fabric.store.db.GroupContainer;
import fabric.worker.remote.RemoteWorker;

/**
 * Keeps track of who's subscribed to what object. Handles subscriptions for a
 * single store.
 */
public class SubscriptionManager extends FabricThread.Impl {
  private static abstract class NotificationEvent extends
      Threading.NamedRunnable {
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
      for (LongIterator it = onums.iterator(); it.hasNext();) {
        long onum = it.next();

        // Get the list of subscribers.
        Set<Pair<RemoteWorker, Boolean>> subscribers =
            subscriptions.remove(onum);
        if (subscribers == null) continue;

        // Get the update.
        GroupContainer groupContainer;
        Glob glob;
        try {
          groupContainer = tm.getGroupContainer(onum);
          glob = groupContainer.getGlob();
        } catch (AccessException e) {
          throw new InternalError(e);
        }

        // Notify subscribers of updates.
        Set<Pair<RemoteWorker, Boolean>> newSubscribers =
            new HashSet<Pair<RemoteWorker, Boolean>>();
        synchronized (subscribers) {
          for (Pair<RemoteWorker, Boolean> subscriber : subscribers) {
            RemoteWorker node = subscriber.first;
            boolean isDissem = subscriber.second;
            // No need to notify the worker that issued the updates. Just
            // resubscribe the worker.
            boolean resubscribe;
            if (node == writer && !isDissem)
              resubscribe = true;
            else {
              try {
                if (isDissem)
                  resubscribe = node.notifyObjectUpdate(store, onum, glob);
                else {
                  ObjectGroup group =
                      groupContainer.getGroup(node.getPrincipal());
                  resubscribe =
                      group != null && node.notifyObjectUpdate(onum, group);
                }
              } catch (UnreachableNodeException e) {
                resubscribe = false;
              }
            }

            if (resubscribe) newSubscribers.add(subscriber);
          }
        }

        subscribe(onum, newSubscribers);
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
    this.notificationQueue = new ArrayBlockingQueue<NotificationEvent>(50);
    this.tm = tm;
    this.subscriptions = new LongKeyCache<Set<Pair<RemoteWorker, Boolean>>>();

    start();
  }

  @Override
  public void run() {
    while (true) {
      try {
        Threading.getPool().submit(notificationQueue.take());
      } catch (InterruptedException e1) {
      }
    }
  }

  /**
   * Subscribes the given set of workers to the given onum.
   */
  private void subscribe(long onum,
      Set<Pair<RemoteWorker, Boolean>> newSubscribers) {
    if (newSubscribers.isEmpty()) return;

    Set<Pair<RemoteWorker, Boolean>> subscribers = subscriptions.get(onum);
    if (subscribers == null) {
      subscribers = subscriptions.putIfAbsent(onum, newSubscribers);
    }

    if (subscribers != null) {
      synchronized (subscribers) {
        subscribers.addAll(newSubscribers);
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
  public void subscribe(long onum, RemoteWorker worker, boolean dissemSubscribe) {
    Set<Pair<RemoteWorker, Boolean>> subscribers = subscriptions.get(onum);
    if (subscribers == null) {
      subscribers = new HashSet<Pair<RemoteWorker, Boolean>>();
      Set<Pair<RemoteWorker, Boolean>> existing =
          subscriptions.putIfAbsent(onum, subscribers);
      if (existing != null) subscribers = existing;
    }

    synchronized (subscribers) {
      subscribers.add(new Pair<RemoteWorker, Boolean>(worker, dissemSubscribe));
    }
  }

  /**
   * Notifies the subscription manager that a set of objects has been updated by a
   * particular worker.
   */
  public void notifyUpdate(LongSet onums, RemoteWorker worker) {
    notificationQueue.offer(new ObjectUpdateEvent(onums, worker));
  }
}
