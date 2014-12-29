package fabric.worker.memoize;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.IOException;
import java.io.Serializable;

import fabric.common.SemanticWarranty;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;
import fabric.lang.Object;
import fabric.lang.WrappedJavaInlineable;

/**
 * Wrapper for the stored value and warranty on a call in the system.  Basically
 * just a nicer way of representing things than Pair<Object, SemanticWarranty>.
 */
public class WarrantiedCallResult implements Serializable {
  
  private Object value;
  private SemanticWarranty warranty;
  private LongSet createOids;

  public WarrantiedCallResult(Object value, SemanticWarranty warranty, LongSet creates) {
    this.value = value;
    this.warranty = warranty;
    this.createOids = creates;
  }
  
  /**
   * Get the warranty for this call.
   */
  public SemanticWarranty getWarranty() {
    return warranty;
  }

  /**
   * Update the warranty for this call.
   */
  public void setWarranty(SemanticWarranty newWarranty) {
    warranty = newWarranty;
  }

  /**
   * Get the value for this call without making any copy.
   */
  public Object getValue() {
    if (!(value instanceof WrappedJavaInlineable) && (value != null)) {
      return value.fetch();
    }
    return value;
  }

  /**
   * Get the value for this call.  Make a copy of the value if necessary.
   */
  public Object getValueCopy() {
    //if (!(value instanceof WrappedJavaInlineable) && (value != null) &&
        //createOids.contains(value.$getOnum())) {
      //return value.$makeSemiDeepCopy(createOids, new LongKeyHashMap<Object>());
    //}
    if (!(value instanceof WrappedJavaInlineable) && (value != null)) {
      return value.fetch();
    }
    return value;
  }

  /**
   * Get the LongSet of creates for this result.
   */
  public LongSet getCreates() {
    return createOids;
  }

  /**
   * Deserialization constructor.
   */
  public WarrantiedCallResult(DataInput in) throws IOException {
    warranty = new SemanticWarranty(in.readLong());
    value = new CallResult(in).value;
    createOids = new LongHashSet();
    int createCount = in.readInt();
    for (int i = 0; i < createCount; i++)
      createOids.add(in.readLong());
  }

  /**
   * Serializes the result into the given output stream.
   */
  public void write(DataOutput out) throws IOException {
    out.writeLong(warranty.expiry());
    (new CallResult(value)).write(out);
    out.writeInt(createOids.size());
    for (LongIterator it = createOids.iterator(); it.hasNext();)
      out.writeLong(it.next());
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (!(o instanceof WarrantiedCallResult)) return false;
    WarrantiedCallResult other = (WarrantiedCallResult) o;
    return other.value.equals(value)
      && (other.warranty.expiry() == warranty.expiry());
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    DataOutputStream outD = new DataOutputStream(out);
    write(outD);
    outD.flush();
  }

  private void readObject(ObjectInputStream in) throws IOException,
          ClassNotFoundException {
    WarrantiedCallResult copy = new WarrantiedCallResult(new DataInputStream(in));
    this.value = copy.value;
    this.warranty = copy.warranty;
    this.createOids = copy.createOids;
  }

  private void readObjectNoData(ObjectInputStream in) throws
    ObjectStreamException {
      throw new ObjectStreamException() {};
  }
}
