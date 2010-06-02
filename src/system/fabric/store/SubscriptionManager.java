package fabric.store;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import fabric.worker.remote.RemoteWorker;
import fabric.common.FabricThread;
import fabric.common.ObjectGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyCache;
import fabric.common.util.Pair;
import fabric.store.db.GroupContainer;
import fabric.dissemination.Glob;

/**
 * Keeps track of who's subscribed to what object. Handles subscriptions for a
 * single store.
 */
public class SubscriptionManager extends FabricThread.Impl {
  /**
   * A set of onums that have been updated, paired with the worker that issued
   * the update.
   */
  private final Set<Pair<Long, RemoteWorker>> updatedOnums;

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
    this.updatedOnums = new LinkedHashSet<Pair<Long, RemoteWorker>>();
    this.tm = tm;
    this.subscriptions = new LongKeyCache<Set<Pair<RemoteWorker, Boolean>>>();

    start();
  }

  @Override
  public void run() {
    while (true) {
      // Obtain an entry from the list of updated onums.
      Pair<Long, RemoteWorker> updateEntry;
      synchronized (updatedOnums) {
        if (updatedOnums.isEmpty()) {
          try {
            updatedOnums.wait();
          } catch (InterruptedException e) {
          }
          continue;
        }

        Iterator<Pair<Long, RemoteWorker>> updates = updatedOnums.iterator();
        updateEntry = updates.next();
        updates.remove();
      }

      long onum = updateEntry.first;
      RemoteWorker updater = updateEntry.second;

      // Get the list of subscribers.
      Set<Pair<RemoteWorker, Boolean>> subscribers;
      synchronized (subscriptions) {
        subscribers = subscriptions.remove(onum);
      }

      if (subscribers == null) continue;

      // Get the update.
      GroupContainer groupContainer;
      Glob glob;
      try {
        groupContainer =
            tm.getGroupContainerAndSubscribe(onum, null, false, null);
        glob = groupContainer.getGlob();
      } catch (AccessException e) {
        throw new InternalError(e);
      }

      // Notify subscribers of updates.
      Set<Pair<RemoteWorker, Boolean>> newSubscribers =
          new HashSet<Pair<RemoteWorker, Boolean>>();
      for (Pair<RemoteWorker, Boolean> subscriber : subscribers) {
        RemoteWorker node = subscriber.first;
        boolean isDissem = subscriber.second;
        // No need to notify the worker that issued the updates. Just
        // resubscribe the worker.
        boolean resubscribe;
        if (node == updater && !isDissem)
          resubscribe = true;
        else if (isDissem)
          resubscribe = node.notifyObjectUpdate(store, onum, glob);
        else {
          ObjectGroup group = groupContainer.getGroup(node.getPrincipal());
          resubscribe =
              group != null && node.notifyObjectUpdate(onum, group);
        }

        if (resubscribe) newSubscribers.add(subscriber);
      }

      subscribe(onum, newSubscribers);
    }
  }

  /**
   * Subscribes the given set of workers to the given onum.
   */
  private void subscribe(long onum,
      Set<Pair<RemoteWorker, Boolean>> newSubscribers) {
    if (newSubscribers.isEmpty()) return;

    synchronized (subscriptions) {
      Set<Pair<RemoteWorker, Boolean>> subscribers = subscriptions.get(onum);
      if (subscribers == null) {
        subscriptions.put(onum, newSubscribers);
      } else {
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
    synchronized (subscriptions) {
      Set<Pair<RemoteWorker, Boolean>> subscribers = subscriptions.get(onum);
      if (subscribers == null) {
        subscribers = new HashSet<Pair<RemoteWorker, Boolean>>();
        subscriptions.put(onum, subscribers);
      }

      subscribers.add(new Pair<RemoteWorker, Boolean>(worker, dissemSubscribe));
    }
  }

  /**
   * Notifies the subscription manager that an object has been updated by a
   * particular worker.
   */
  public void notifyUpdate(long onum, RemoteWorker worker) {
    synchronized (updatedOnums) {
      updatedOnums.add(new Pair<Long, RemoteWorker>(onum, worker));
      updatedOnums.notify();
    }
  }
}
