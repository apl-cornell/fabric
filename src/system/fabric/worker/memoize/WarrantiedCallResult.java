package fabric.worker.memoize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.SemanticWarranty;
import fabric.lang.Object;

/**
 * Wrapper for the stored value and warranty on a call in the system.  Basically
 * just a nicer way of representing things than Pair<Object, SemanticWarranty>.
 */
public class WarrantiedCallResult {
  
  public final Object value;
  public final SemanticWarranty warranty;

  public WarrantiedCallResult(Object value, SemanticWarranty warranty) {
    this.value = value;
    this.warranty = warranty;
  }

  /**
   * Deserialization constructor.
   */
  public WarrantiedCallResult(DataInput in) throws IOException {
    warranty = new SemanticWarranty(in.readLong());
    value = new CallResult(in).value;
  }

  /**
   * Serializes the result into the given output stream.
   */
  public void write(DataOutput out) throws IOException {
    out.writeLong(warranty.expiry());
    (new CallResult(value)).write(out);
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (!(o instanceof WarrantiedCallResult)) return false;
    WarrantiedCallResult other = (WarrantiedCallResult) o;
    return other.value.equals(value)
      && (other.warranty.expiry() == warranty.expiry());
  }
}
