package fabric.worker.metrics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

import fabric.common.FastSerializable;
import fabric.worker.Store;

/**
 * An inlineable reference to a treaty. Represented as the oid of the object
 * it's defined on and the unique id for treaties on that object.
 */
@SuppressWarnings("serial")
public final class TreatyRef implements FastSerializable, Serializable {

  public final OidRef<fabric.lang.Object> objRef;
  public final long treatyId;

  public TreatyRef(OidRef<fabric.lang.Object> objRef, long treatyId) {
    this.objRef = objRef;
    this.treatyId = treatyId;
  }

  public TreatyRef(DataInput in) throws IOException {
    this.objRef = new OidRef<>(in);
    this.treatyId = in.readLong();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    this.objRef.write(out);
    out.writeLong(this.treatyId);
  }

  public TreatyRef(String objStoreName, long objOnum, long treatyId) {
    this.objRef = new OidRef<>(objStoreName, objOnum);
    this.treatyId = treatyId;
  }

  public TreatyRef(Store objStore, long objOnum, long treatyId) {
    this(objStore.name(), objOnum, treatyId);
  }

  public TreatyRef(fabric.lang.Object obj, long treatyId) {
    this(new OidRef<>(obj), treatyId);
  }

  public MetricTreaty get() {
    // TODO
    return null;
  }
}
