package fabric.worker.metrics.treaties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import fabric.common.FastSerializable;
import fabric.metrics.Metric;
import fabric.worker.Store;

/**
 * An inlineable reference to a treaty. Represented as the oid of the object
 * it's defined on and the unique id for treaties on that object.
 */
@SuppressWarnings("serial")
public final class TreatyRef implements FastSerializable, Serializable {

  public TreatiesBoxRef objRef;
  public long treatyId;

  public static TreatyRef createRef(MetricTreaty treaty) {
    return new TreatyRef(treaty);
  }

  public TreatyRef(TreatiesBoxRef objRef, long treatyId) {
    this.objRef = objRef;
    this.treatyId = treatyId;
  }

  /* Serializable definitions, need to special case fabric references. */

  private void writeObject(ObjectOutputStream out) throws IOException {
    write(out);
  }

  private void readObject(ObjectInputStream in)
      throws IOException, ClassNotFoundException {
    this.objRef = new TreatiesBoxRef(in);
    this.treatyId = in.readLong();
  }

  private void readObjectNoData() throws ObjectStreamException {
    // Do nothing?
  }

  public TreatyRef(DataInput in) throws IOException {
    this.objRef = new TreatiesBoxRef(in);
    this.treatyId = in.readLong();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    this.objRef.write(out);
    out.writeLong(this.treatyId);
  }

  public TreatyRef(String objStoreName, long objOnum, long treatyId) {
    this.objRef = new TreatiesBoxRef(objStoreName, objOnum);
    this.treatyId = treatyId;
  }

  public TreatyRef(Store objStore, long objOnum, long treatyId) {
    this(objStore.name(), objOnum, treatyId);
  }

  public TreatyRef(Metric obj, long treatyId) {
    this(new TreatiesBoxRef(obj.get$treatiesBox()), treatyId);
  }

  public TreatyRef(MetricTreaty treaty) {
    this(treaty.metric.objStoreName, treaty.metric.objOnum, treaty.id);
  }

  public MetricTreaty get() {
    return objRef.get().get$treatiesBox().get$$treaties().get(treatyId);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (!(obj instanceof TreatyRef)) return false;
    TreatyRef other = (TreatyRef) obj;
    return objRef.equals(other.objRef) && treatyId == other.treatyId;
  }

  @Override
  public int hashCode() {
    return objRef.hashCode() ^ Long.hashCode(treatyId);
  }

  @Override
  public String toString() {
    return objRef.toString() + ":" + treatyId;
  }
}
