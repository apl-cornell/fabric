package fabric.dissemination.pastry.messages;

import rice.p2p.commonapi.NodeHandle;
import fabric.dissemination.pastry.Disseminator;

/**
 * This should be sent when aggregation of object popularity data is to be
 * performed. Not currently used yet.
 */
public class Aggregate implements Message {

  private transient final NodeHandle sender;

  public Aggregate(NodeHandle sender) {
    this.sender = sender;
  }

  public NodeHandle sender() {
    return sender;
  }

  @Override
  public int getPriority() {
    return LOW_PRIORITY;
  }

  @Override
  public void dispatch(Disseminator disseminator) {
    // TODO Auto-generated method stub
  }

}
