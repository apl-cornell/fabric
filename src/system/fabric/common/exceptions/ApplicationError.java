package fabric.common.exceptions;

public class ApplicationError extends Error {
  public ApplicationError() {
    report();
  }

  public ApplicationError(Throwable cause) {
    super(cause);
    report();
  }

  public ApplicationError(String msg) {
    super(msg);
    report();
  }

  public ApplicationError(String msg, Throwable cause) {
    super(msg, cause);
    report();
  }
  
  private void report() {
    System.err.println("Creating ApplicationError exception:");
    System.err.println("================ BEGIN STACK TRACE ================");
    printStackTrace();
    System.err.println("================= END STACK TRACE =================");
    System.err.println();
  }
}
