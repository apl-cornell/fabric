package fabric.common;

import fabric.worker.remote.RemoteWorker;
import java.util.Collections;
import java.util.Set;

public abstract class Lease /*implements Comparable<Lease>*/ {
  /**
   * Assumed maximum clock skew, in milliseconds.
   */
  public static final long CLOCK_SKEW = 100;

  /**
   * Expiry time, in milliseconds since the epoch.
   */
  private final long expiry;

  /**
   * Is the object writable during the term?
   */
  private final boolean writeable;

  /**
   * Set of RemoteWorkers that should be contacted in addition to the store
   * during in-term write prepare.
   */
  private final Set<RemoteWorker> lessees;

  /**
   * @param expiry expiry time, in milliseconds since the epoch.
   * @param writeable boolean indicating if the object under this lease may be
   * written during the term.
   * @param lessees Set of RemoteWorker nodes which should be contacted on an
   * in-term write prepare.
   */
  public Lease(long expiry, boolean writeable, Set<RemoteWorker> lessees) {
    this.expiry = expiry;
    this.writeable = writeable;
    this.lessees = lessees;
  }
  
  // Deserialization constructor.
  protected Lease() {
    this.expiry = 0;
    this.writeable = true;
    this.lessees = Collections.emptySet();
  }

  /**
   * @return the expiry time, in milliseconds since the epoch.
   */
  public long expiry() {
    return expiry;
  }

  /**
   * @return whether the object can be written before expiry.
   */
  public boolean writeable() {
    return writeable;
  }

  /**
   * @return an unmodifiable view of the lessee set.
   */
  public Set<RemoteWorker> lessees() {
    return Collections.unmodifiableSet(lessees);
  }

  /**
   * Determines whether this lease has expired.
   * 
   * @param defaultResult
   *         the value to return if the current time is within CLOCK_SKEW of the
   *         lease's expiry.
   */
  public boolean expired(boolean defaultResult) {
    return expiresBefore(System.currentTimeMillis(), defaultResult);
  }

  /**
   * Determines whether this lease expires after the given lease. Because lease
   * can be directly compared with one another, clock skew is not taken into
   * account (nor does it need to be).
   */
  public boolean expiresAfter(Lease lease) {
    return expiresAfterStrict(lease.expiry);
  }

  /**
   * Determines whether this lease expires before a certain time, taking clock
   * skew into account.
   * 
   * @param defaultResult
   *         the value to return if the time given is within CLOCK_SKEW of the
   *         lease's expiry.
   */
  public boolean expiresBefore(long time, boolean defaultResult) {
    return isBefore(expiry, time, defaultResult);
  }

  /**
   * Determines whether this lease expires after a certain time, taking clock
   * skew into account.
   * 
   * @param defaultResult
   *         the value to return if the time given is within CLOCK_SKEW of the
   *         lease's expiry.
   */
  public boolean expiresAfter(long time, boolean defaultResult) {
    return isAfter(expiry, time, defaultResult);
  }

  /**
   * Determines whether the lease expires after a certain time. Clock skew is
   * <i>not</i> taken into account.
   */
  public boolean expiresAfterStrict(long time) {
    return expiry > time;
  }

  /**
   * Orders by expiry time.
   */
  /*
  Not totally convinced we need comparable, we'll see.  One issue is that it's
  unclear how to compare same-time but different lessees...
  @Override
  public int compareTo(Lease o) {
    if (expiry > o.expiry) return 1;
    if (expiry < o.expiry) return -1;
    return 0;
  }
  */

  /**
   * Determines whether time1 is before time2, taking clock skew into account.
   * 
   * @param defaultResult
   *         the value to return if the given times are within CLOCK_SKEW of
   *         each other.
   */
  public static boolean isBefore(long time1, long time2, boolean defaultResult) {
    return isAfter(time2, time1, defaultResult);
  }

  /**
   * Determines whether time1 is after time2, taking clock skew into account.
   * 
   * @param defaultResult
   *         the value to return if the given times are within CLOCK_SKEW of
   *         each other.
   */
  public static boolean isAfter(long time1, long time2, boolean defaultResult) {
    if (time1 < time2 - CLOCK_SKEW) return false;
    if (time2 + CLOCK_SKEW < time1) return true;
    return defaultResult;
  }
}
