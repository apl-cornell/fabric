package fabric.dissemination.pastry.messages;

import fabric.dissemination.pastry.Disseminator;

/**
 * A self-scheduled message triggered once every replication interval.
 */
public class ReplicateInterval implements Message {

  @Override
  public int getPriority() {
    return LOW_PRIORITY;
  }

  @Override
  public void dispatch(Disseminator disseminator) {
    disseminator.replicateInterval();
  }

}
