package fabric.dissemination.pastry.messages;

import rice.p2p.commonapi.Message;

/**
 * A self-scheduled message triggered once every replication interval.
 */
public class ReplicateInterval implements Message {

  public int getPriority() {
    return LOW_PRIORITY;
  }

}
