package fabric.worker.memoize;

import java.util.Arrays;

/**
 * Wrapper class for representing call ids with a hash that maps to the same
 * value for the same digest.
 */
public class CallID {

  private byte[] id;

  public CallID(byte[] id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(id);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof CallID)) return false;
    CallID other = (CallID) o;
    return Arrays.equals(id, other.id);
  }

  @Override
  public String toString() {
    String rtn = "";
    for (byte b : id) {
      rtn += String.format("%x", b);
    }
    return rtn;
  }

  public byte[] id() {
    return id;
  }
}
