package fabric.common;


public abstract class Warranty implements Comparable<Warranty> {
  /**
   * Assumed maximum clock skew, in milliseconds.
   */
  public static final long CLOCK_SKEW = 100;

  /**
   * The period at which warranties should be proactively refreshed, in
   * milliseconds. This should be larger than CLOCK_SKEW.
   */
  public static final long REFRESH_INTERVAL_MS = 1500;

  /**
   * Expiry time, in milliseconds since the epoch.
   */
  private final long expiry;

  /**
   * @param expiry expiry time, in milliseconds since the epoch.
   */
  public Warranty(long expiry) {
    this.expiry = expiry;
  }

  // Deserialization constructor.
  protected Warranty() {
    this.expiry = 0;
  }

  /**
   * @return the expiry time, in milliseconds since the epoch.
   */
  public long expiry() {
    return expiry;
  }

  /**
   * Determines whether this warranty has expired.
   * 
   * @param defaultResult
   *         the value to return if the current time is within CLOCK_SKEW of
   *         the warranty's expiry.
   */
  public boolean expired(boolean defaultResult) {
    return expiresBefore(System.currentTimeMillis(), defaultResult);
  }

  /**
   * Determines whether this warranty expires after the given warranty. Because
   * warranties can be directly compared with one another, clock skew is not
   * taken into account (nor does it need to be).
   */
  public boolean expiresAfter(Warranty warranty) {
    return expiresAfterStrict(warranty.expiry);
  }

  /**
   * Determines whether this warranty expires before a certain time, taking
   * clock skew into account.
   * 
   * @param defaultResult
   *         the value to return if the time given is within CLOCK_SKEW of the
   *         warranty's expiry.
   */
  public boolean expiresBefore(long time, boolean defaultResult) {
    return isBefore(expiry, time, defaultResult);
  }

  /**
   * Determines whether this warranty expires after a certain time, taking
   * clock skew into account.
   * 
   * @param defaultResult
   *         the value to return if the time given is within CLOCK_SKEW of the
   *         warranty's expiry.
   */
  public boolean expiresAfter(long time, boolean defaultResult) {
    return isAfter(expiry, time, defaultResult);
  }

  /**
   * Determines whether the warranty expires after a certain time. Clock skew is
   * <i>not</i> taken into account.
   */
  public boolean expiresAfterStrict(long time) {
    return expiry > time;
  }

  /**
   * Orders by expiry time.
   */
  @Override
  public int compareTo(Warranty o) {
    if (expiry > o.expiry) return 1;
    if (expiry < o.expiry) return -1;
    return 0;
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
