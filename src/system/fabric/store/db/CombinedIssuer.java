package fabric.store.db;

import static fabric.common.Logging.HOTOS_LOGGER;
import static fabric.common.Logging.STORE_DB_LOGGER;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.Lease;
import fabric.common.Warranty;
import fabric.common.exceptions.InternalError;
import fabric.lang.security.Principal;
import fabric.worker.Store;

/**
 * A lease issuer maintains the mapping from keys to leases on those keys and
 * uses access metrics for keys to suggest how long newly issued leases should
 * last. This class is thread-safe.
 */
public class CombinedIssuer<K, L extends Lease, W extends Warranty> {

  // BEGIN TUNING PARAMETERS /////////////////////////////////////////////////

  /**
   * The maximum length of time (in milliseconds) for which each issued lease
   * should be valid.
   */
  private static final int MAX_LEASE_LENGTH = 15000;

  /**
   * The maximum length of time (in milliseconds) for which each issued warranty
   * should be valid.
   */
  private static final int MAX_WARRANTY_LENGTH = 10000;

  /**
   * The amount by which write intervals are scaled to determine lease length.
   */
  private static final double LEASE_K2 = 1;

  /**
   * The amount by which write intervals are scaled to determine warranty
   * length.
   */
  private static final double WARRANTY_K2 = 0.5;

  /**
   * The minimum read-to-write ratio. If the read-to-write ratio for an object
   * is below this threshold, then no warranties will be issued for that object.
   */
  private static final double WARRANTY_K3 = 2;

  /**
   * The number of samples to take after a (warranty or lease) term before
   * issuing another warranty or lease.
   */
  public static final int SAMPLE_SIZE = 5;

  // END TUNING PARAMETERS ///////////////////////////////////////////////////

  /**
   * The popularity cutoff. If the average read interval of an object is above
   * this threshold, then no warranties will be issued for that object.
   */
  private static final double MAX_READ_PREP_INTERVAL = MAX_WARRANTY_LENGTH
      / (WARRANTY_K2 * WARRANTY_K3);

  /**
   * The default lease for keys that aren't yet in the table. All leases in the
   * table should expire after the default lease.
   */
  private volatile L defaultLease;

  /**
   * The default warranty for keys that aren't yet in the table. All warranties
   * in the table should expire after the default warranty.
   */
  private volatile W defaultWarranty;

  private class Entry {
    final K key;
    volatile L leaseIssued;
    volatile W warrantyIssued;

    Entry(K key) {
      this.key = key;
      this.leaseIssued = defaultLease;
      this.warrantyIssued = defaultWarranty;
    }

    synchronized void setLeaseIssued(L lease) {
      this.leaseIssued = lease;
    }

    synchronized void setWarrantyIssued(W warranty) {
      this.warrantyIssued = warranty;
    }

    synchronized boolean replaceLease(L oldLease, L newLease) {
      boolean success = this.leaseIssued.equals(oldLease);
      if (success) {
        AccessMetrics<K>.Metrics metrics = getMetrics(key, false);
        if (metrics != null) {
          long expiry = newLease.expiry();
          synchronized (metrics) {
            boolean updated = metrics.updateTerm(expiry);
            if (updated) this.leaseIssued = newLease;
            return true;
          }
        }

        this.leaseIssued = newLease;
      }
      return success;
    }

    synchronized boolean replaceWarranty(W oldWarranty, W newWarranty) {
      boolean success = this.warrantyIssued.equals(oldWarranty);
      if (success) {
        AccessMetrics<K>.Metrics metrics = getMetrics(key, false);
        if (metrics != null) {
          long expiry = newWarranty.expiry();
          synchronized (metrics) {
            boolean updated = metrics.updateTerm(expiry);
            if (updated) this.warrantyIssued = newWarranty;
            return true;
          }
        }

        this.warrantyIssued = newWarranty;
      }
      return success;
    }
  }

  private static int count = 0;

  private final ConcurrentMap<K, Entry> table;

  private final AccessMetrics<K> accessMetrics;

  protected CombinedIssuer(L defaultLease, W defaultWarranty, AccessMetrics<K> accessMetrics) {
    this.table = new ConcurrentHashMap<K, Entry>();
    this.defaultLease = defaultLease;
    this.defaultWarranty = defaultWarranty;
    this.accessMetrics = accessMetrics;
  }

  private Entry getEntry(K key) {
    return getEntry(key, true);
  }

  private Entry getEntry(K key, boolean createIfAbsent) {
    Entry existingEntry = table.get(key);
    if (existingEntry != null) return existingEntry;

    if (!createIfAbsent) return null;

    Entry entry = new Entry(key);
    existingEntry = table.putIfAbsent(key, entry);
    return existingEntry == null ? entry : existingEntry;
  }

  private AccessMetrics<K>.Metrics getMetrics(K key, boolean createIfAbsent) {
    return accessMetrics.getMetrics(key, createIfAbsent);
  }

  private AccessMetrics<K>.Metrics getMetrics(K key) {
    return accessMetrics.getMetrics(key, true);
  }

  /**
   * @return the issued lease for the given key.
   */
  final L getLease(K key) {
    Entry e;
    return ((e = getEntry(key, false)) == null) ? defaultLease : e.leaseIssued;
  }

  /**
   * @return the issued warranty for the given key.
   */
  final W getWarranty(K key) {
    Entry e;
    return ((e = getEntry(key, false)) == null) ? defaultWarranty : e.warrantyIssued;
  }

  /**
   * Replaces the lease for an onum only if it currently has a specific lease.
   *
   * @return true iff the lease was replaced.
   */
  final boolean replace(K key, L oldLease, L newLease) {
    if (defaultLease.expiresAfter(newLease)) {
      throw new InternalError("Attempted to insert a lease that expires "
          + "before the default lease. This should not happen.");
    }

    if (oldLease.expiresAfter(newLease)) {
      throw new InternalError(
          "Attempted to replace a lease with one that expires sooner");
    }

    boolean success = getEntry(key).replaceLease(oldLease, newLease);
    if (STORE_DB_LOGGER.isLoggable(Level.FINEST) && success) {
      long expiry = newLease.expiry();
      long length = expiry - System.currentTimeMillis();
      Logging.log(STORE_DB_LOGGER, Level.FINEST, "Extended lease for {0}"
          + "; expiry={1} (in {2} ms)", key, expiry, length);
    }

    return success;
  }

  /**
   * Replaces the warranty for an onum only if it currently has a specific
   * warranty.
   * 
   * @return true iff the warranty was replaced.
   */
  final boolean replace(K key, W oldWarranty, W newWarranty) {
    if (defaultWarranty.expiresAfter(newWarranty)) {
      throw new InternalError("Attempted to insert a warranty that expires "
          + "before the default warranty. This should not happen.");
    }

    if (oldWarranty.expiresAfter(newWarranty)) {
      throw new InternalError(
          "Attempted to replace a warranty with one that expires sooner");
    }

    boolean success = getEntry(key).replaceWarranty(oldWarranty, newWarranty);
    if (STORE_DB_LOGGER.isLoggable(Level.FINEST) && success) {
      long expiry = newWarranty.expiry();
      long length = expiry - System.currentTimeMillis();
      Logging.log(STORE_DB_LOGGER, Level.FINEST, "Extended warranty for {0}"
          + "; expiry={1} (in {2} ms)", key, expiry, length);
    }

    return success;
  }

  /**
   * For recovering lease state from stable storage. Sets the issued lease for
   * the given key.
   */
  final void put(K key, L lease) {
    if (defaultLease.expiresAfter(lease)) {
      throw new InternalError("Attempted to insert a lease that expires "
          + "before the default lease. This should not happen.");
    }

    if (STORE_DB_LOGGER.isLoggable(Level.FINEST)) {
      long expiry = lease.expiry();
      long length = expiry - System.currentTimeMillis();
      Logging.log(STORE_DB_LOGGER, Level.FINEST,
          "Adding lease for {0}; expiry={1} (in {2} ms)", key, expiry, length);
    }

    getEntry(key).setLeaseIssued(lease);
  }

  /**
   * For recovering warranty state from stable storage. Sets the issued warranty
   * for the given key.
   */
  final void put(K key, W warranty) {
    if (defaultWarranty.expiresAfter(warranty)) {
      throw new InternalError("Attempted to insert a warranty that expires "
          + "before the default warranty. This should not happen.");
    }

    if (STORE_DB_LOGGER.isLoggable(Level.FINEST)) {
      long expiry = warranty.expiry();
      long length = expiry - System.currentTimeMillis();
      Logging.log(STORE_DB_LOGGER, Level.FINEST,
          "Adding warranty for {0}; expiry={1} (in {2} ms)", key, expiry,
          length);
    }

    getEntry(key).setWarrantyIssued(warranty);
  }

  /**
   * For recovering lease state from stable storage. Sets the default lease for
   * onums that don't yet have an entry in the issuer.
   */
  void setDefaultLease(L lease) {
    defaultLease = lease;
  }

  /**
   * For recovering warranty state from stable storage. Sets the default
   * warranty for onums that don't yet have an entry in the issuer.
   */
  void setDefaultWarranty(W warranty) {
    defaultWarranty = warranty;
  }

  /**
   * Utility class to represent a suggestion from the issuer.
   */
  public static class Suggestion {
    /**
     * Is a lease appropriate?  Otherwise, we're suggesting a warranty.
     */
    public final boolean leaseAppropriate;

    /**
     * How long should it last?
     */
    public final long expiry;

    public Suggestion(boolean leaseAppropriate, long expiry) {
      this.leaseAppropriate =  leaseAppropriate;
      this.expiry = expiry;
    }
  }


  /**
   * Suggests an appropriate warranty or lease for the given worker and object.
   *
   * @param worker the worker requesting the lease.
   * @param key key the lease is for.
   */
  public Suggestion suggestToken(Principal worker, K key) {
    return suggestToken(worker, key, System.currentTimeMillis());
  }

  /**
   * Suggests an appropriate expirable token that lasts beyond the given expiry
   * time.
   *
   * @return The suggested token.
   */
  public Suggestion suggestToken(Principal worker, K key, long expiry) {
    // Snapshot state to avoid locking for too long.
    final int numReadPrepares;
    final long readInterval;
    final long writeInterval;
    final boolean isUsed;
    final Store clientStore;
    final long clientOnum;
    AccessMetrics<K>.Metrics m = getMetrics(key);
    synchronized (m) {
      numReadPrepares = m.getNumReadPrepares();
      writeInterval = m.getWriteInterval();
      readInterval = m.getReadInterval();
      clientStore = m.getClientStore();
      clientOnum = m.getClientOnum();
      isUsed = m.isUsedSinceTerm();
    }

    // Only lease if the requester is the only user
    boolean leasePossible = !isUsed ||
                            (worker != null &&
                             clientStore != null &&
                             clientOnum == worker.$getOnum() &&
                             clientStore.equals(worker.$getStore()));

    // Stop now if we haven't sampled enough.
    if (numReadPrepares < SAMPLE_SIZE)
      return new Suggestion(leasePossible, expiry);

    // This only counts if we've sampled enough.  Log every 10000th.
    final int curCount = count++;
    boolean logResult = curCount % 10000 == 0;

    // LEASING
    if (leasePossible) {
      long suggestedTerm = Math.min((long) (LEASE_K2 * writeInterval),
          MAX_LEASE_LENGTH);

      if (logResult) 
        HOTOS_LOGGER.info("Token (Lease) #" + curCount + ": " + key + "," +
            readInterval + "," + writeInterval + "," + suggestedTerm);

      return new Suggestion(leasePossible,
          Math.max(expiry, System.currentTimeMillis() + suggestedTerm));
    }
    
    // WARRANTYING

    // Not enough reading overall
    if (readInterval > MAX_READ_PREP_INTERVAL) {
      if (logResult) 
        HOTOS_LOGGER.info("Token (Warranty) #" + curCount + ": " + key + "," +
            readInterval + "," + writeInterval + ",unpopular");

      return new Suggestion(leasePossible, expiry);
    }

    // Not enough reading vs. writing
    double ratio = writeInterval / (readInterval + 1.0);
    if (ratio < WARRANTY_K3) {
      if (logResult) 
        HOTOS_LOGGER.info("Token (Warranty) #" + curCount + ": " + key + "," +
            readInterval + "," + writeInterval + ",low read-write");

      return new Suggestion(leasePossible, expiry);
    }

    // Standard warranty
    long suggestedTerm = (long) (WARRANTY_K2 * writeInterval);

    if (logResult) 
      HOTOS_LOGGER.info("Token (Warranty) #" + curCount + ": " + key + "," +
          readInterval + "," + writeInterval + "," + suggestedTerm);

    return new Suggestion(leasePossible,
        Math.max(expiry, System.currentTimeMillis() + suggestedTerm));
  }
}
