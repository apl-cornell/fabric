package fabric.common;

public class InternalError extends Error {
  public InternalError() {
  }

  public InternalError(Throwable cause) {
    super(cause);
    reportCause(cause);
  }

  public InternalError(String msg) {
    super(msg);
  }

  public InternalError(String msg, Throwable cause) {
    super(msg, cause);
    reportCause(cause);
  }
  
  private void reportCause(Throwable cause) {
    System.err.println("Creating InternalError exception:");
    System.err.println("================ BEGIN STACK TRACE ================");
    printStackTrace();
    System.err.println("================= END STACK TRACE =================");
    System.err.println();
  }
}
