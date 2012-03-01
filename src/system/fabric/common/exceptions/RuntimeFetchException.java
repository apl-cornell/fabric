package fabric.common.exceptions;

/**
 * Represents an object fetch error at runtime.
 */
public class RuntimeFetchException extends FabricRuntimeException {
  public RuntimeFetchException(Throwable cause) {
    super(cause);
  }
}
