package fabric.worker.metrics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.FastSerializable;
import fabric.lang.Object._Impl;

/**
 * Utility class for describing each extension (avoids boxed values).
 */
public class ExpiryExtension implements FastSerializable {
  /** Onum for the extended object. */
  public final long onum;
  /** Version of the extended object. */
  public final int version;
  /** (New) Expiry of the extended object. */
  public long expiry;

  /** Create extension using values. */
  public ExpiryExtension(long onum, int version, long expiry) {
    this.onum = onum;
    this.version = version;
    this.expiry = expiry;
  }

  /** Create extension using impl that was extended. */
  public ExpiryExtension(_Impl extendedObj) {
    this.onum = extendedObj.$getOnum();
    this.version = extendedObj.$version;
    this.expiry = extendedObj.$expiry;
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeLong(onum);
    out.writeInt(version);
    out.writeLong(expiry);
  }

  /** Create extension from serialized input. */
  public ExpiryExtension(DataInput in) throws IOException {
    onum = in.readLong();
    version = in.readInt();
    expiry = in.readLong();
  }
}
