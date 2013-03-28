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

  /**
   * Represents a version warranty bound to a particular version of an object.
   */
  public final class Binding {
    public final long onum;
    public final int versionNumber;

    public Binding(long onum, int versionNumber) {
      this.onum = onum;
      this.versionNumber = versionNumber;
    }

    public long expiry() {
      return VersionWarranty.this.expiry();
    }

    public VersionWarranty warranty() {
      return VersionWarranty.this;
    }
  }
}
