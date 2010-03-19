package fabric.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import fabric.client.remote.RemoteClient;
import fabric.common.FabricThread;
import fabric.common.ObjectGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyCache;
import fabric.common.util.Pair;
import fabric.core.db.GroupContainer;
import fabric.dissemination.Glob;

/**
 * Keeps track of who's subscribed to what object. Handles subscriptions for a
 * single core.
 */
public class SubscriptionManager extends FabricThread.AbstractImpl {
  /**
   * A set of onums that have been updated, paired with the client that issued
   * the update.
   */
  private final Set<Pair<Long, RemoteClient>> updatedOnums;

  /**
   * The set of nodes subscribed to each onum. The second component of each pair
   * indicates whether the node is subscribed as a dissemination node. (true =
   * dissemination, false = client)
   */
  private final LongKeyCache<Set<Pair<RemoteClient, Boolean>>> subscriptions;

  /**
   * The name of the core for which we are managing subscriptions.
   */
  private final String core;

  /**
   * The transaction manager corresponding to the core for which we are managing
   * subscriptions.
   */
  private final TransactionManager tm;

  /**
   * @param tm
   *          The transaction manager corresponding to the core for which
   *          subscriptions are to be managed.
   */
  public SubscriptionManager(String core, TransactionManager tm) {
    super("subscription manager for core " + core);
    this.core = core;
    this.updatedOnums = new LinkedHashSet<Pair<Long, RemoteClient>>();
    this.tm = tm;
    this.subscriptions = new LongKeyCache<Set<Pair<RemoteClient, Boolean>>>();

    start();
  }

  @Override
  public void run() {
    while (true) {
      // Obtain an entry from the list of updated onums.
      Pair<Long, RemoteClient> updateEntry;
      synchronized (updatedOnums) {
        if (updatedOnums.isEmpty()) {
          try {
            updatedOnums.wait();
          } catch (InterruptedException e) {
          }
          continue;
        }

        Iterator<Pair<Long, RemoteClient>> updates = updatedOnums.iterator();
        updateEntry = updates.next();
        updates.remove();
      }

      long onum = updateEntry.first;
      RemoteClient updater = updateEntry.second;

      // Get the list of subscribers.
      Set<Pair<RemoteClient, Boolean>> subscribers;
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
      Set<Pair<RemoteClient, Boolean>> newSubscribers =
          new HashSet<Pair<RemoteClient, Boolean>>();
      for (Pair<RemoteClient, Boolean> subscriber : subscribers) {
        RemoteClient node = subscriber.first;
        boolean isDissem = subscriber.second;
        // No need to notify the client that issued the updates. Just
        // resubscribe the client.
        boolean resubscribe;
        if (node == updater && !isDissem)
          resubscribe = true;
        else if (isDissem)
          resubscribe = node.notifyObjectUpdate(core, onum, glob);
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
   * Subscribes the given set of clients to the given onum.
   */
  private void subscribe(long onum,
      Set<Pair<RemoteClient, Boolean>> newSubscribers) {
    if (newSubscribers.isEmpty()) return;

    synchronized (subscriptions) {
      Set<Pair<RemoteClient, Boolean>> subscribers = subscriptions.get(onum);
      if (subscribers == null) {
        subscriptions.put(onum, newSubscribers);
      } else {
        subscribers.addAll(newSubscribers);
      }
    }
  }

  /**
   * Subscribes the given client to the given onum.
   * 
   * @param dissemSubscribe
   *          If true, then the given subscriber will be subscribed as a
   *          dissemination node; otherwise it will be subscribed as a client.
   */
  public void subscribe(long onum, RemoteClient client, boolean dissemSubscribe) {
    synchronized (subscriptions) {
      Set<Pair<RemoteClient, Boolean>> subscribers = subscriptions.get(onum);
      if (subscribers == null) {
        subscribers = new HashSet<Pair<RemoteClient, Boolean>>();
        subscriptions.put(onum, subscribers);
      }

      subscribers.add(new Pair<RemoteClient, Boolean>(client, dissemSubscribe));
    }
  }

  /**
   * Notifies the subscription manager that an object has been updated by a
   * particular client.
   */
  public void notifyUpdate(long onum, RemoteClient client) {
    synchronized (updatedOnums) {
      updatedOnums.add(new Pair<Long, RemoteClient>(onum, client));
      updatedOnums.notify();
    }
  }
}
