package fabric.store.db;

import static fabric.common.Logging.STORE_DB_LOGGER;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.Warranty;
import fabric.common.exceptions.InternalError;

/**
 * A table containing version warranties, keyed by onum, and supporting
 * concurrent accesses.
 */
class WarrantyTable<K, V extends Warranty> {
  /**
   * The default warranty for onums that aren't yet in the table. All warranties
   * in the table should expire after the default warranty.
   */
  private volatile DefaultEntry defaultEntry;

  private final ConcurrentMap<K, Entry> table;

  class Entry {
    private final K key;
    private V virtualWarranty;
    private V issuedWarranty;

    /**
     * False iff this entry has been removed from the table.
     */
    boolean valid;

    Entry(K key, V virtualWarranty) {
      this.key = key;
      this.issuedWarranty = null;
      this.virtualWarranty = virtualWarranty;
      this.valid = true;
    }

    final V issuedWarranty() {
      return issuedWarranty == null ? defaultEntry.virtualWarranty()
          : issuedWarranty;
    }

    final V virtualWarranty() {
      return virtualWarranty;
    }

    /**
     * @return true iff successful. This fails if the entry has been removed
     *         from the warranty table, or if the old virtual warranty has been
     *         replaced already.
     */
    synchronized boolean replaceVirtualWarranty(V oldVirtualWarranty,
        V newVirtualWarranty) {
      if (this.virtualWarranty != oldVirtualWarranty) return false;

      if (issuedWarranty().expiresAfter(newVirtualWarranty)) {
        throw new InternalError("Attempted to use a virtual warranty that "
            + "expires before a previously issued warranty. Virtual warranty "
            + "expires " + newVirtualWarranty.expiry()
            + ", issued warranty expires " + issuedWarranty().expiry());
      }

      this.virtualWarranty = newVirtualWarranty;

      if (valid && STORE_DB_LOGGER.isLoggable(Level.FINEST)) {
        long expiry = newVirtualWarranty.expiry();
        long length = expiry - System.currentTimeMillis();
        STORE_DB_LOGGER.finest("Replacing virtual warranty for " + key
            + "; expiry=" + expiry + " (in " + length + " ms)");

      }
      return valid;
    }

    /**
     * @return true iff successful. This fails if the entry has been removed
     *         from the warranty table, or if the old issued warranty has been
     *         replaced already.
     */
    synchronized boolean replaceIssuedWarranty(V oldIssuedWarranty,
        V newIssuedWarranty) {
      if (issuedWarranty() != oldIssuedWarranty) return false;

      if (issuedWarranty().expiresAfter(newIssuedWarranty)) {
        throw new InternalError("Attempted to issue a warranty that expires "
            + "before a previously issued warranty.");
      }
      this.issuedWarranty = newIssuedWarranty;

      if (valid && STORE_DB_LOGGER.isLoggable(Level.FINEST)) {
        long expiry = newIssuedWarranty.expiry();
        long length = expiry - System.currentTimeMillis();
        STORE_DB_LOGGER.finest("Extending issued warranty for " + key
            + "; expiry=" + expiry + " (in " + length + " ms)");

      }
      return valid;
    }

    /**
     * Truncates the virtual warranty to the last issued warranty.
     * 
     * @return true iff successful. This fails if the entry has been removed
     *         from the warranty table.
     */
    synchronized boolean truncateVersionWarranty() {
      virtualWarranty = issuedWarranty();
      return valid;
    }
  }

  class DefaultEntry extends Entry {
    DefaultEntry(V defaultWarranty) {
      super(null, defaultWarranty);
    }

    @Override
    boolean replaceVirtualWarranty(V oldVirtualWarranty, V newVirtualWarranty) {
      throw new InternalError("Attempting to modify the warranty table's "
          + "default entry, which should be immutable.");
    }

    @Override
    boolean replaceIssuedWarranty(V oldIssuedWarranty, V newIssuedWarranty) {
      throw new InternalError("Attempting to modify the warranty table's "
          + "default entry, which should be immutable.");
    }
  }

//  private final Collector collector;

  /**
   * Priority-queue version of the table for ease of garbage collection.
   */
//  private final PriorityBlockingQueue<Pair<K, V>> gcQueue;

  WarrantyTable(V defaultWarranty) {
    this.defaultEntry = new DefaultEntry(defaultWarranty);
    table = new ConcurrentHashMap<>();
//    gcQueue = new PriorityBlockingQueue<>(11, new Comparator<Pair<K, V>>() {
//      @Override
//      public int compare(Pair<K, V> o1, Pair<K, V> o2) {
//        return Long.compare(o1.second.expiry(), o2.second.expiry());
//      }
//    });

//    collector = new Collector();
//    collector.start();
  }

  final Entry getEntry(K key) {
    return table.get(key);
  }

  final V getIssuedWarranty(K key) {
    Entry entry = getEntry(key);
    if (entry == null) return defaultEntry.virtualWarranty();
    return entry.issuedWarranty();
  }

  /**
   * Puts the given virtual warranty in the table if no entry is currently
   * associated with the given key.
   * 
   * @return the resulting table entry for the key.
   */
  final Entry putIfAbsentAndGet(K key, V virtualWarranty) {
    sanityCheck(virtualWarranty);

    long expiry = virtualWarranty.expiry();
    long length = expiry - System.currentTimeMillis();
    STORE_DB_LOGGER.finest("Adding virtual warranty for " + key + "; expiry="
        + expiry + " (in " + length + " ms)");

    Entry newEntry = new Entry(key, virtualWarranty);
    Entry result = table.putIfAbsent(key, newEntry);
    if (result == null) {
      addGCEntry(key, virtualWarranty);
      return newEntry;
    }

    return result;
  }

  protected void sanityCheck(V virtualWarranty) {
    if (defaultEntry.virtualWarranty().expiresAfter(virtualWarranty)) {
      throw new InternalError("Attempted to insert a virtual warranty that "
          + "expires before the default warranty. This should not happen.");
    }
  }

  private final void addGCEntry(K key, V virtualWarranty) {
//    gcQueue.add(new Pair<>(key, virtualWarranty));
//
//    // Signal the collector thread that we have a new warranty.
//    collector.signalNewWarranty();
  }

  /**
   * Extends the warranty for an onum only if it currently has a specific
   * warranty.
   * 
   * @return true iff the warranty was replaced.
   */
  final boolean extend(K key, V oldVirtualWarranty, V newVirtualWarranty) {
    sanityCheck(newVirtualWarranty);

    boolean success = false;
    Entry newEntry = new Entry(key, newVirtualWarranty);
    Entry curEntry = table.get(key);

    if (curEntry != null
        && !curEntry.virtualWarranty.equals(oldVirtualWarranty)) {
      return false;
    }

    if (curEntry != null && curEntry.issuedWarranty != null
        && newVirtualWarranty.expiresAfter(curEntry.issuedWarranty)) {
      throw new InternalError(
          "Attempted to replace a virtual warranty with one that expires "
              + "sooner than the actual warranties already issued.");
    }

    if (oldVirtualWarranty == defaultEntry.virtualWarranty()) {
      success = table.putIfAbsent(key, newEntry) == null;
    }

    if (!success) success = table.replace(key, curEntry, newEntry);

    if (success) {
      long expiry = newVirtualWarranty.expiry();
      long length = expiry - System.currentTimeMillis();
      Logging.log(STORE_DB_LOGGER, Level.FINEST,
          "Extended virtual warranty for {0}" + "; expiry={1} (in {2} ms)",
          key, expiry, length);

      addGCEntry(key, newVirtualWarranty);
    }

    return success;
  }

  /**
   * Sets the default warranty for onums that aren't yet in the table.
   */
  void setDefaultWarranty(V warranty) {
    defaultEntry = new DefaultEntry(warranty);
  }

  /**
   * Removes the entry for the given key from the table.
   */
  void remove(K key) {
    table.remove(key);
  }

  /**
   * @return
   */
  public V getDefaultWarranty() {
    return defaultEntry.virtualWarranty();
  }

//  /**
//   * GC thread for expired warranties.
//   */
//  private final class Collector extends Thread {
//    private final long MIN_WAIT_TIME = 1000;
//    private final AtomicBoolean haveNewWarranty;
//
//    public Collector() {
//      super("Warranty GC");
//      setDaemon(true);
//
//      this.haveNewWarranty = new AtomicBoolean(false);
//    }
//
//    private void signalNewWarranty() {
//      haveNewWarranty.set(true);
//    }
//
//    @Override
//    public void run() {
//      while (true) {
//        long nextExpiryTime;
//        // Loop until the GC queue is empty or there is an unexpired warranty at
//        // its head. All expired warranties that we encounter are GCed.
//        while (true) {
//          Pair<K, V> gcQueueHead = gcQueue.peek();
//          if (gcQueueHead == null || gcQueueHead.second.expired(false)) {
//            nextExpiryTime =
//                gcQueueHead == null ? Long.MAX_VALUE : gcQueueHead.second
//                    .expiry();
//            break;
//          }
//
//          // GC.
//          gcQueueHead = gcQueue.poll();
//          if (gcQueueHead == null) {
//            throw new InternalError("Shouldn't happen.");
//          } else if (gcQueueHead.second.expired(false)) {
//            throw new InternalError("Shouldn't happen.");
//          }
//
//          table.remove(gcQueueHead.first, gcQueueHead.second);
//        }
//
//        // Wait until either the next warranty expires or we have more
//        // warranties.
//        long waitTime = nextExpiryTime - System.currentTimeMillis();
//        for (int timeWaited = 0; timeWaited < waitTime; timeWaited +=
//            MIN_WAIT_TIME) {
//          try {
//            Thread.sleep(MIN_WAIT_TIME);
//          } catch (InterruptedException e) {
//          }
//
//          if (haveNewWarranty.getAndSet(false)) {
//            break;
//          }
//        }
//      }
//    }
//  }
}
