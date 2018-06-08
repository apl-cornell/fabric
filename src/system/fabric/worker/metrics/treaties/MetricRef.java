package fabric.worker.metrics.treaties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import fabric.common.FastSerializable;
import fabric.common.util.Oid;
import fabric.metrics.Metric;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Utility for an inlineable reference to some Fabric object.  Primarily
 * intended to help with inlineable structures like TreatySet.
 */
@SuppressWarnings("serial")
public final class MetricRef implements FastSerializable, Serializable {

  public String objStoreName;
  public long objOnum;

  private transient Metric cached;

  public MetricRef(String objStoreName, long objOnum) {
    this.objStoreName = objStoreName;
    this.objOnum = objOnum;
  }

  public MetricRef(Store objStore, long objOnum) {
    this(objStore.name(), objOnum);
  }

  public MetricRef(Metric obj) {
    this(obj.$getStore(), obj.$getOnum());
  }

  /* Serializable definitions, need to special case fabric references. */

  private void writeObject(ObjectOutputStream out) throws IOException {
    write(out);
  }

  private void readObject(ObjectInputStream in)
      throws IOException, ClassNotFoundException {
    this.objStoreName = in.readUTF();
    this.objOnum = in.readLong();
  }

  private void readObjectNoData() throws ObjectStreamException {
    // Do nothing?
  }

  public MetricRef(DataInput in) throws IOException {
    this.objStoreName = in.readUTF();
    this.objOnum = in.readLong();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeUTF(this.objStoreName);
    out.writeLong(this.objOnum);
  }

  public Metric get() {
    if (cached == null) cached =
        new Metric._Proxy(Worker.getWorker().getStore(objStoreName), objOnum);
    return cached;
  }

  @Override
  public boolean equals(Object obj) {
    return obj == this || (obj instanceof MetricRef
        && objStoreName.equals(((MetricRef) obj).objStoreName)
        && objOnum == ((MetricRef) obj).objOnum);
  }

  @Override
  public int hashCode() {
    return objStoreName.hashCode() ^ Long.hashCode(objOnum);
  }

  @Override
  public String toString() {
    return objStoreName + "/" + objOnum;
  }
}
