package fabric.common.exceptions;

/**
 * This exception signals termination of the Fabric node. It should be used
 * instead of <code>System.exit</code> to allow Fabric to be started from
 * within a JVM that wasn't started specifically for Fabric, e.g., the Apache
 * ANT framework.
 */
public class TerminationException extends RuntimeException {
  final public int exitCode;

  public TerminationException(String msg) {
    this(msg, 1);
  }

  public TerminationException(int exit) {
    this.exitCode = exit;
  }

  public TerminationException(String msg, int exit) {
    super(msg);
    this.exitCode = exit;
  }

  public TerminationException(Exception cause, int exit) {
    super(cause);
    this.exitCode = exit;
  }
}