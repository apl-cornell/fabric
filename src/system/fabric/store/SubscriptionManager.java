package fabric.store;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.ObjectGroup;
import fabric.common.Threading;
import fabric.common.exceptions.AccessException;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyCache;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.dissemination.ObjectGlob;
import fabric.store.db.GroupContainer;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;

/**
 * Keeps track of who's subscribed to what object. Handles subscriptions for a
 * single store.
 */
public class SubscriptionManager {

  private final ConcurrentMap<RemoteWorker, LongSet> notificationQueues;

  /**
   * The set of nodes subscribed to each onum. The second component of each pair
   * indicates whether the node is subscribed as a dissemination node. (true =
   * dissemination, false = worker)
   */
  private final LongKeyCache<ConcurrentMap<RemoteWorker, Boolean>> subscriptions;

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
    this.store = store;
    this.notificationQueues = new ConcurrentHashMap<>();
    this.tm = tm;
    this.subscriptions = new LongKeyCache<>();
  }

  /**
   * Unsubscribes the given worker to the given onums.
   *
   * @param worker
   * the worker being unsubscribed.
   * @param onums
   * the onums to be unsubscribed from.
   */
  public void unsubscribe(RemoteWorker worker, LongSet unsubscribes) {
    for (LongIterator iter = unsubscribes.iterator(); iter.hasNext();) {
      long onum = iter.next();
      ConcurrentMap<RemoteWorker, Boolean> subscribers =
          subscriptions.get(onum);
      if (subscribers != null) {
        subscribers.remove(worker);
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
    ConcurrentMap<RemoteWorker, Boolean> subscribers = subscriptions.get(onum);
    if (subscribers == null) {
      subscribers = new ConcurrentHashMap<>();
      ConcurrentMap<RemoteWorker, Boolean> existing =
          subscriptions.putIfAbsent(onum, subscribers);
      if (existing != null) subscribers = existing;
    }

    subscribers.putIfAbsent(worker, dissemSubscribe);
  }

  /**
   * @param onum
   *        Onum for the object being checked.
   * @param worker
   *        Worker whose subscription is being checked.
   * @return true iff the worker is currently subscribed to the onum.
   */
  public boolean subscribed(long onum, RemoteWorker worker) {
    ConcurrentMap<RemoteWorker, Boolean> subscribers = subscriptions.get(onum);
    return subscribers != null && subscribers.containsKey(worker);
  }

  private class UpdateNotifier extends Threading.NamedRunnable {

    private final RemoteWorker worker;
    private final LongSet queue;

    public UpdateNotifier(RemoteWorker worker) {
      super("Update notifier for " + worker.name());
      this.worker = worker;
      this.queue = notificationQueues.get(worker);
    }

    @Override
    protected final void runImpl() {
      handle();
    }

    protected void handle() {
      try {
        while (true) {
          LongSet curGroup = new LongHashSet();
          synchronized (queue) {
            while (queue.isEmpty()) {
              try {
                queue.wait();
              } catch (InterruptedException e) {
                Logging.logIgnoredInterruptedException(e);
              }
            }
            curGroup.addAll(queue);
          }
          runGroup(curGroup);
        }
      } catch (Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        Logging.MISC_LOGGER.log(Level.SEVERE,
            "Subscription runner exited with exception {0}\n{1}",
            new Object[] { t, sw });
      }
    }

    protected void runGroup(LongSet onums) {
      Map<ObjectGlob, LongSet> globs = new HashMap<>();
      LongSet onumsSent = new LongHashSet();
      Set<ObjectGroup> groups = new HashSet<>();
      LongKeyMap<LongSet> associatedOnums = new LongKeyHashMap<>();
      // Gather the updates.
      for (LongIterator it = onums.iterator(); it.hasNext();) {
        long onum = it.next();

        ConcurrentMap<RemoteWorker, Boolean> subMap = subscriptions.get(onum);
        if (subMap == null) {
          synchronized (queue) {
            queue.remove(onum);
          }
          continue;
        }
        Boolean isDissem = subMap.get(worker);

        // Skip if the worker has been unsubscribed from under us.
        if (isDissem == null) continue;

        GroupContainer groupContainer;
        try {
          // Skip if the onum represents a surrogate.
          if (tm.read(onum).isSurrogate()) continue;

          // Now grab it and open it up to more notifications.
          synchronized (queue) {
            queue.remove(onum);
          }
          groupContainer = tm.getGroupContainer(onum);
          LongSet curAssociatedOnums =
              tm.getAssociatedOnumsExcluded(onum, onums, worker);
          if (isDissem) {
            ObjectGlob glob = groupContainer.getGlob();
            if (!globs.containsKey(glob)) {
              globs.put(glob, new LongHashSet());
            }
            globs.get(glob).add(onum);
            LongSet trueOnums = new LongHashSet(curAssociatedOnums);
            for (LongIterator iter = curAssociatedOnums.iterator(); iter
                .hasNext();) {
              long associated = iter.next();
              if (!trueOnums.contains(associated)) continue;
              // Subscribe the worker to the associated, if it wasn't already.
              // Otherwise, skip since we'll already be handling the
              // subscription.
              // Don't bother packaging each onum separately
              GroupContainer associatedContainer =
                  tm.getGroupContainer(associated);
              trueOnums.removeAll(associatedContainer.onums);
              if (subscribed(associated, worker)) continue;
              subscribe(associated, worker, true);
              trueOnums.add(associated);
              ObjectGlob associatedGlob = associatedContainer.getGlob();
              if (!globs.containsKey(associatedGlob)) {
                globs.put(associatedGlob, new LongHashSet());
              }
              globs.get(associatedGlob).add(associated);
            }
            curAssociatedOnums = trueOnums;
          } else {
            onumsSent.add(onum);
            groups.add(groupContainer.getGroup(worker.getPrincipal()));
            LongSet trueOnums = new LongHashSet();
            for (LongIterator iter = curAssociatedOnums.iterator(); iter
                .hasNext();) {
              long associated = iter.next();
              // Subscribe the worker to the associated, if it wasn't already.
              // Otherwise, skip since we'll already be handling the
              // subscription.
              if (subscribed(associated, worker)) continue;
              subscribe(associated, worker, false);
              GroupContainer associatedContainer =
                  tm.getGroupContainer(associated);
              trueOnums.add(associated);
              groups.add(associatedContainer.getGroup(worker.getPrincipal()));
            }
            curAssociatedOnums = trueOnums;
          }
          associatedOnums.put(onum, curAssociatedOnums);
        } catch (AccessException e) {
          throw new InternalError(e);
        }
      }

      // Now send it.
      worker.notifyObjectUpdates(store, globs, onumsSent, groups,
          associatedOnums);
    }
  }

  private void submit(RemoteWorker worker, LongSet addition) {
    LongSet init = new LongHashSet();
    LongSet current = notificationQueues.putIfAbsent(worker, init);
    if (current == null) {
      current = init;
      Thread runner = new Thread(new UpdateNotifier(worker));
      runner.setDaemon(true);
      runner.start();
    }
    synchronized (current) {
      current.addAll(addition);
      current.notifyAll();
    }
  }

  /**
   * Notifies the subscription manager that a set of objects has been updated by a
   * particular worker.
   */
  public void notifyUpdate(LongSet onums, RemoteWorker worker) {
    if (!Worker.getWorker().config.useSubscriptions) return;

    Set<RemoteWorker> handled = new HashSet<>();
    for (LongIterator iter = onums.iterator(); iter.hasNext();) {
      long onum = iter.next();
      // TODO: Can I concurrently iterate over the entries like this?
      ConcurrentMap<RemoteWorker, Boolean> subMap = subscriptions.get(onum);
      if (subMap != null) {
        for (Map.Entry<RemoteWorker, Boolean> e : subMap.entrySet()) {
          RemoteWorker subbed = e.getKey();
          boolean isDissem = e.getValue();
          if (!isDissem || subbed.equals(worker) || handled.contains(subbed))
            continue;
          submit(subbed, onums);
          handled.add(subbed);
        }
      }
    }
  }
}
