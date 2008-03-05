package fabric.dissemination.pastry.messages;

import rice.p2p.commonapi.Message;

/**
 * A self-scheduled message triggered once every aggregation interval.
 */
public class AggregateInterval implements Message {

  public int getPriority() {
    return LOW_PRIORITY;
  }

}
