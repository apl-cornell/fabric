package fabric.common;

public abstract class Warranty {
  /**
   * Assumed maximum clock skew, in milliseconds.
   */
  public static final long CLOCK_SKEW = 100;

  /**
   * Promise length, in milliseconds.
   */
  protected static final long PROMISE_LENGTH = 60000;

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

  /**
   * @return the expiry time, in milliseconds since the epoch.
   */
  public long expiry() {
    return expiry;
  }

  /**
   * @return true iff the warranty has expired.
   */
  public boolean expired() {
    return expiry < System.currentTimeMillis() + CLOCK_SKEW;
  }

  public boolean expiresAfter(Warranty warranty) {
    return expiry > warranty.expiry;
  }

  public boolean expiresAfter(long time) {
    return expiry > time + CLOCK_SKEW;
  }
}
