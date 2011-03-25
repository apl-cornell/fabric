package fabric.worker.shell;

class HandlerException extends Exception {
  public HandlerException(String message, Throwable cause) {
    super(message, cause);
  }

  public HandlerException(String message) {
    super(message);
  }
}