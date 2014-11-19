package fabric.common;

import java.io.Serializable;

public abstract class Lease implements Serializable {
  /**
   * Assumed maximum clock skew, in milliseconds.
   */
  public static final long CLOCK_SKEW = 100;

  /**
   * Expiry time, in milliseconds since the epoch.
   */
  private final long expiry;

  /**
   * @param expiry expiry time, in milliseconds since the epoch.
   */
  public Lease(long expiry) {
    this.expiry = expiry;
  }

  // Deserialization constructor.
  protected Lease() {
    this.expiry = 0;
  }

  /**
   * @return the expiry time, in milliseconds since the epoch.
   */
  public long expiry() {
    return expiry;
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
