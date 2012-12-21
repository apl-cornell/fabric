package fabric.common;

public class VersionWarranty extends Warranty {
  public static VersionWarranty EXPIRED_WARRANTY = new VersionWarranty(0);

  /**
   * @param expiry Expiry time, in milliseconds since the epoch.
   */
  public VersionWarranty(long expiry) {
    super(expiry);
  }
}
