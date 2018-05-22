package fabric.worker.metrics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.FastSerializable;
import fabric.lang.Object._Impl;
import fabric.worker.metrics.treaties.TreatySet;

/**
 * Utility class for describing each extension (avoids boxed values).
 */
public class ExpiryExtension implements FastSerializable {
  /** Onum for the extended object. */
  public final long onum;
  /** Version of the extended object. */
  public final int version;
  /** (New) Treaties of the extended object. */
  public TreatySet treaties;

  /** Create extension using values. */
  public ExpiryExtension(long onum, int version, TreatySet treaties) {
    this.onum = onum;
    this.version = version;
    this.treaties = treaties;
  }

  /** Create extension using impl that was extended. */
  public ExpiryExtension(_Impl extendedObj) {
    this.onum = extendedObj.$getOnum();
    this.version = extendedObj.$version;
    this.treaties = extendedObj.$treaties;
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeLong(onum);
    out.writeInt(version);
    treaties.write(out);
  }

  /** Create extension from serialized input. */
  public ExpiryExtension(DataInput in) throws IOException {
    onum = in.readLong();
    version = in.readInt();
    treaties = TreatySet.read(in);
  }
}
