package fabric.common.exceptions;

public class FabricRuntimeException extends RuntimeException {
  public FabricRuntimeException() {
    super();
  }

  public FabricRuntimeException(String message) {
    super(message);
  }

  public FabricRuntimeException(Throwable cause) {
    super(cause);
  }

  public FabricRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }
}
