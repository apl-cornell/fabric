package fabric.common.exceptions;

/**
 * An exception used to indicate an command-line usage error.
 */
public class UsageError extends Exception {
  public int exitCode;

  public UsageError(String s) { this(s,1); }
  public UsageError(String s, int exitCode) {
    super(s);
    this.exitCode = exitCode;
  }
}

