package fabric.store;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Representation of an extension to be run in the future.  Extends {@link
 * Delayed} to allow using this with {@link java.util.concurrent.DelayedQueue}
 * in the code that processes delayed extensions.
 */
public class DelayedExtension implements Delayed {

  public final long time;
  public final long onum;
  public final long treatyId;

  public DelayedExtension(long time, long onum, long treatyId) {
    this.time = time;
    this.onum = onum;
    this.treatyId = treatyId;
  }

  @Override
  public long getDelay(TimeUnit unit) {
    return unit.convert(time - System.currentTimeMillis(),
        TimeUnit.MILLISECONDS);
  }

  @Override
  public int compareTo(Delayed o) {
    if (o instanceof DelayedExtension) {
      return Long.compare(time, ((DelayedExtension) o).time);
    } else {
      return Long.compare(getDelay(TimeUnit.MILLISECONDS),
          o.getDelay(TimeUnit.MILLISECONDS));
    }
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof DelayedExtension)) {
      return false;
    }
    DelayedExtension that = (DelayedExtension) o;
    return this.time == that.time && this.onum == that.onum
        && this.treatyId == that.treatyId;
  }
}
