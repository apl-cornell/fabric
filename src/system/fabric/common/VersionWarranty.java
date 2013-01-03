package fabric.common;

import java.io.Serializable;

public class VersionWarranty extends Warranty implements Serializable {
  public static VersionWarranty EXPIRED_WARRANTY = new VersionWarranty(0);

  /**
   * @param expiry Expiry time, in milliseconds since the epoch.
   */
  public VersionWarranty(long expiry) {
    super(expiry);
  }

  // Deserialization constructor.
  @SuppressWarnings("unused")
  private VersionWarranty() {
  }
}
