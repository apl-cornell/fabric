package fabric.worker.memoize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.SemanticWarranty;
import fabric.common.SerializedObject;
import fabric.lang.Object;

/**
 * Wrapper for the stored value and warranty on a call in the system.  Basically
 * just a nicer way of representing things than Pair<Object, SemanticWarranty>.
 */
public class CallResult {
  
  public final SerializedObject value;
  public final SemanticWarranty warranty;

  public CallResult(Object._Impl value, SemanticWarranty warranty) {
    this(new SerializedObject(value), warranty);
  }

  public CallResult(SerializedObject value, SemanticWarranty warranty) {
    this.value = value;
    this.warranty = warranty;
  }

  /**
   * Deserialization constructor.
   */
  public CallResult(DataInput in) throws IOException {
    warranty = new SemanticWarranty(in.readLong());
    value = new SerializedObject(in);
  }

  /**
   * Serializes the result into the given output stream.
   */
  public void write(DataOutput out) throws IOException {
    out.writeLong(warranty.expiry());
    value.write(out);
  }
}
