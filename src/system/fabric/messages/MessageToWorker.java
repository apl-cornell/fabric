package fabric.messages;

import fabric.common.exceptions.FabricException;

/** Message destined for a Worker. */
public interface MessageToWorker {
  /** Visitor method */
  public Message.Response dispatch(MessageToWorkerHandler handler) throws FabricException;
}
