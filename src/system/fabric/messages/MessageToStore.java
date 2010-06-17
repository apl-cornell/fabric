package fabric.messages;

import fabric.common.exceptions.FabricException;

/** A Message destined for a store. */
public interface MessageToStore {
  /** visitor method */
  public Message.Response dispatch(MessageToStoreHandler handler) throws FabricException;
}
