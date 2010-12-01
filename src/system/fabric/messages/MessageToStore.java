package fabric.messages;

import fabric.lang.security.NodePrincipal;

/**
 * A Message destined for a store.
 * 
 * @param E
 *          The exception type that may occur at the store while handling the
 *          message.
 */
public interface MessageToStore<E extends Exception> {
  /**
   * Visitor method.
   * 
   * @param p the principal of the node that is issuing the request.
   * @param handler the handler to which this message is to be dispatched.
   */
  public Message.Response dispatch(NodePrincipal p, MessageToStoreHandler handler) throws E;
}
