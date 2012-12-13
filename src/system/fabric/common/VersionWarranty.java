package fabric.common;

public class VersionWarranty extends Warranty {

  /**
   * @param expiry Expiry time, in milliseconds since the epoch.
   */
  public VersionWarranty(long expiry) {
    super(expiry);
  }
}
