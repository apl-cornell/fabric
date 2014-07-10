package fabric.common;

/**
 * This encapsulates the version of Fabric.
 *
 * DO NOT EDIT THIS CLASS BY HAND!  This is automatically generated by Ant.  If
 * you wish to change the version number, run one of the following:
 *
 *   ant bump-version  (Bumps the version number.)
 *   ant bump-major    (Bumps the major version number.)
 *   ant bump-minor    (Bumps the minor version number.)
 *   ant bump-patch    (Bumps the patch level.)
 *   ant mark-branch   (Changes the branch name.)
 */
public class Version {
  private int major = 0;
  private int minor = 2;
  private int patch = 2;
  private String branch = "warranties";

  public int major() { return major; }
  public int minor() { return minor; }
  public int patch() { return patch; }
  public String branch() { return branch; }

  @Override
  public String toString() {
    return "0.2.2-warranties (2014-07-10 12:27:22 EDT)";
  }
}
