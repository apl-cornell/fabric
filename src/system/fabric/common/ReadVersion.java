package fabric.common;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Utility class for talking about info for a read including info for a version
 * check (a paired version and expiry), used for read validations during the
 * commit protocol.
 */
public class ReadVersion implements FastSerializable {
  public final int version;
  public final long expiry;
  // Flag indicating if stale expiry is acceptable.
  public final boolean strict;

  public ReadVersion(int version, long expiry) {
    this.version = version;
    this.expiry = expiry;
    this.strict = false;
  }

  public ReadVersion(DataInput in) throws IOException {
    this.version = in.readInt();
    this.expiry = in.readLong();
    this.strict = in.readBoolean();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeInt(version);
    out.writeLong(expiry);
    out.writeBoolean(strict);
  }
}
