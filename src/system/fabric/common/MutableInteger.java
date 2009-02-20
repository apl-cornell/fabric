package fabric.common;

public class MutableInteger {
  public int value;

  public MutableInteger() {
    this(0);
  }

  public MutableInteger(int value) {
    this.value = value;
  }
}