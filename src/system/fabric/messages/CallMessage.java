package fabric.messages;

import java.lang.reflect.Method;

import fabric.lang.Object._Proxy;

/**
 * Interface unifying RemoteCallMessage and AsyncCallMessage
 */
public interface CallMessage {

  /**
   * Get the Method for the call.
   */
  public Method getMethod() throws SecurityException, NoSuchMethodException;

  /**
   * Get the receiver of the call.
   */
  public _Proxy getReceiver();

  /**
   * Get the arguments of the call.
   */
  public Object[] getArgs();
}
