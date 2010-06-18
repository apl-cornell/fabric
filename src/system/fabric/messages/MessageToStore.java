package fabric.messages;

import fabric.common.exceptions.FabricException;
import fabric.lang.security.NodePrincipal;

/** A Message destined for a store. */
public interface MessageToStore {
  /** visitor method */
  public Message.Response dispatch(NodePrincipal p, MessageToStoreHandler handler) throws FabricException;
}
