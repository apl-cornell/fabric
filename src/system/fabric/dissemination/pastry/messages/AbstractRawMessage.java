package fabric.dissemination.pastry.messages;

import rice.p2p.commonapi.rawserialization.RawMessage;

/**
 * Common functionality for RawMessages in the Pastry dissemination layer.
 */
public abstract class AbstractRawMessage implements Message, RawMessage {

  public final RawMessageType type;

  public AbstractRawMessage(RawMessageType type) {
    this.type = type;
  }

  @Override
  public final short getType() {
    return (short) type.ordinal();
  }

}
