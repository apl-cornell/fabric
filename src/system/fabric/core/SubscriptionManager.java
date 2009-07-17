package fabric.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import fabric.client.remote.RemoteClient;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyCache;
import fabric.common.util.Pair;
import fabric.dissemination.Glob;

/**
 * Keeps track of who's subscribed to what object. Handles subscriptions for a
 * single core.
 */
public class SubscriptionManager extends Thread {
  /**
   * A set of onums that have been updated, paired with the client that issued
   * the update.
   */
  private final Set<Pair<Long, RemoteClient>> updatedOnums;

  /**
   * The set of clients subscribed to each onum.
   */
  private final LongKeyCache<Set<RemoteClient>> subscriptions;

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
    this.core = core;
    this.updatedOnums = new LinkedHashSet<Pair<Long, RemoteClient>>();
    this.tm = tm;
    this.subscriptions = new LongKeyCache<Set<RemoteClient>>();

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
      Set<RemoteClient> subscribers;
      synchronized (subscriptions) {
        subscribers = subscriptions.remove(onum);
      }

      if (subscribers == null) continue;

      // Get the update.
      Glob glob;
      try {
        glob = tm.getGlob(onum, null, null);
      } catch (AccessException e) {
        throw new InternalError(e);
      }

      // Notify subscribers of updates.
      Set<RemoteClient> newSubscribers = new HashSet<RemoteClient>();
      for (RemoteClient subscriber : subscribers) {
        // No need to notify the client that issued the updates. Just
        // resubscribe the client.
        boolean resubscribe =
            subscriber == updater
                || subscriber.notifyObjectUpdate(core, onum, glob);

        if (resubscribe) newSubscribers.add(subscriber);
      }

      subscribe(onum, newSubscribers);
    }
  }

  /**
   * Subscribes the given set of clients to the given onum.
   */
  private void subscribe(long onum, Set<RemoteClient> newSubscribers) {
    if (newSubscribers.isEmpty()) return;

    synchronized (subscriptions) {
      Set<RemoteClient> subscribers = subscriptions.get(onum);
      if (subscribers == null) {
        subscriptions.put(onum, newSubscribers);
      } else {
        subscribers.addAll(newSubscribers);
      }
    }
  }

  /**
   * Subscribes the given client to the given onum.
   */
  public void subscribe(long onum, RemoteClient client) {
    synchronized (subscriptions) {
      Set<RemoteClient> subscribers = subscriptions.get(onum);
      if (subscribers == null) {
        subscribers = new HashSet<RemoteClient>();
        subscriptions.put(onum, subscribers);
      }

      subscribers.add(client);
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
