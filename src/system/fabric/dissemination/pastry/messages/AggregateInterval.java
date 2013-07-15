package fabric.dissemination.pastry.messages;

import fabric.dissemination.pastry.Disseminator;

/**
 * A self-scheduled message triggered once every aggregation interval.
 */
public class AggregateInterval implements Message {

  @Override
  public int getPriority() {
    return LOW_PRIORITY;
  }

  @Override
  public void dispatch(Disseminator disseminator) {
    disseminator.aggregateInterval();
  }

}
