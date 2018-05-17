package fabric.dissemination;

import static fabric.common.Logging.WORKER_LOGGER;

import java.util.Properties;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.OidKeyHashMap;
import fabric.worker.RemoteStore;
import fabric.worker.Worker;

/**
 * This simple FetchManger always goes directly to the store.
 */
public class DummyFetchManager implements FetchManager {
  /**
   * The set of fetch locks. Used to prevent threads from concurrently
   * attempting to fetch the same object.
   *
   * TODO: this should really be a concurrent map.
   */
  private final OidKeyHashMap<FetchLock> fetchLocks = new OidKeyHashMap<>();

  public DummyFetchManager(Worker worker, Properties dissemConfig) {
    // do nothing, this is a dummy!
  }

  public DummyFetchManager(Worker worker, Properties dissemConfig,
      Cache cache) {
    // do nothing, this is a dummy!
  }

  private class FetchLock {
    private boolean ready = false;
    private ObjectGlob result = null;
  }

  @Override
  public ObjectGroup fetch(RemoteStore store, long onum) {
    // Alwyas need to fetch. Use fetchLocks as a mutex to atomically check the
    // cache and create a mutex for the object cache in the event of a cache
    // miss.
    FetchLock fetchLock;
    boolean needToFetch = false;
    synchronized (fetchLocks) {
      fetchLock = fetchLocks.get(store, onum);
      if (fetchLock == null) {
        needToFetch = true;
        fetchLock = new FetchLock();
        fetchLocks.put(store, onum, fetchLock);
      }
    }

    synchronized (fetchLock) {
      if (needToFetch) {
        // We are responsible for fetching the object.
        try {
          LongKeyMap<ObjectGlob> fetched =
              store.readEncryptedObjectFromStore(onum);
          for (LongKeyMap.Entry<ObjectGlob> e : fetched.entrySet()) {
            if (e.getKey() == onum) {
              fetchLock.result = e.getValue();
            } else {
              for (SerializedObject o : e.getValue().decrypt().objects()
                  .values()) {
                store.forceCache(o);
              }
            }
          }
        } catch (AccessException e) {
          Logging.log(WORKER_LOGGER, Level.WARNING,
              "Access exception accessing fab://{0}/{1}", store, onum);
        }
        fetchLock.ready = true;

        // Object now cached. Remove our mutex from fetchLocks.
        synchronized (fetchLocks) {
          fetchLocks.remove(store, onum);
        }

        // Signal any other threads that might be waiting for our fetch.
        fetchLock.notifyAll();
      } else {
        // Wait for another thread to fetch the object for us.
        while (!fetchLock.ready) {
          try {
            fetchLock.wait();
          } catch (InterruptedException e) {
            Logging.logIgnoredInterruptedException(e);
          }
        }
      }

      return fetchLock.result.decrypt();
    }
  }

  @Override
  public void destroy() {
  }

  @Override
  public boolean updateCaches(RemoteStore store, LongSet onums,
      AbstractGlob<?> update) {
    if (update instanceof ObjectGlob) {
      ObjectGlob g = (ObjectGlob) update;
      ObjectGroup decrypted = g.decrypt();
      return Worker.getWorker().updateCache(store, decrypted);
    }
    return false;
  }

}
