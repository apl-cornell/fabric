package fabric.common.exceptions;

/**
 * An exception used to indicate an command-line usage error.
 */
public class UsageError extends Exception {
  public final int exitCode;
  public final boolean showSecretMenu;

  public UsageError(String s) {
    this(s, 1);
  }

  public UsageError(String s, int exitCode) {
    this(s, exitCode, false);
  }

  public UsageError(String s, int exitCode, boolean showSecretMenu) {
    super(s);
    this.exitCode = exitCode;
    this.showSecretMenu = showSecretMenu;
  }
}
