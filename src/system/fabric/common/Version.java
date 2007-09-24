package fabric.common;

/**
 * This encapsulates the version of Fabric.
 */
public class Version {
  private int major = 0;
  private int minor = 0;
  private int patch = 0;

  public int major() { return major; }
  public int minor() { return minor; }
  public int patch() { return patch; }

  @Override
  public String toString() {
    return major() + "." + minor() + "." + patch();
  }
}

