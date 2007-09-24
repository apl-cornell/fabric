package fabric.common;

public class InternalError extends Error {
  public InternalError() {}
  public InternalError(Throwable cause) { super(cause); }
  public InternalError(String msg) { super(msg); }
  public InternalError(String msg, Throwable cause) { super(msg, cause); }
}

