package fabric.common.util;

public class MutableInteger {
  public int value;

  public MutableInteger() {
    this(0);
  }

  public MutableInteger(int value) {
    this.value = value;
  }
}
