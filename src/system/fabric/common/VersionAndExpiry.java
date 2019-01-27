package fabric.common;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Utility class for talking about a paired version and expiry, used for read
 * validations during the commit protocol.
 */
public class VersionAndExpiry implements FastSerializable {
  public final int version;
  public final long expiry;

  public VersionAndExpiry(int version, long expiry) {
    this.version = version;
    this.expiry = expiry;
  }

  public VersionAndExpiry(DataInput in) throws IOException {
    this.version = in.readInt();
    this.expiry = in.readLong();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeInt(version);
    out.writeLong(expiry);
  }
}
