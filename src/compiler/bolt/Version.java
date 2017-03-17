package bolt;

public class Version extends polyglot.main.Version {
  @Override
  public String name() {
    return "bolt";
  }

  @Override
  public int major() {
    return 0;
  }

  @Override
  public int minor() {
    return 3;
  }

  @Override
  public int patch_level() {
    return 0;
  }

  @Override
  public String toString() {
    return "0.3.0 (2016-06-27 19:55:11 EDT)";
  }
}
