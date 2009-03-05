package fabric.common.exceptions;

public class FetchException extends FabricException {
  public FetchException() {
  }

  public FetchException(String msg) {
    super(msg);
  }

  public FetchException(Exception cause) {
    super(cause);
  }

}
