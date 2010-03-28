package fabric.common.exceptions;

public class InternalError extends Error {
  public InternalError() {
    report();
  }

  public InternalError(Throwable cause) {
    super(cause);
    report();
  }

  public InternalError(String msg) {
    super(msg);
    report();
  }

  public InternalError(String msg, Throwable cause) {
    super(msg, cause);
    report();
  }
  
  private void report() {
    System.err.println("Creating InternalError exception:");
    System.err.println("================ BEGIN STACK TRACE ================");
    printStackTrace();
    System.err.println("================= END STACK TRACE =================");
    System.err.println();
  }
}
