package fabric.common.util;

public class MutableLong {
  public long value;

  public MutableLong() {
    this(0);
  }

  public MutableLong(long value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "" + value;
  }
}
