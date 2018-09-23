package fabric.worker;

/**
 * A <code>UserAbortException</code> is thrown to indicate that an
 * <code>abort</code> statement is executed.
 *
 * @author qixin
 */
public class UserAbortException extends RuntimeException {
  public UserAbortException() {
    this(null);
  }

  public UserAbortException(Throwable e) {
    super(e);
  }
}
