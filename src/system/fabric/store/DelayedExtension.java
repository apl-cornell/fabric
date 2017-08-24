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

  public DelayedExtension(long time, long onum) {
    this.time = time;
    this.onum = onum;
  }

  @Override
  public long getDelay(TimeUnit unit) {
    return unit.convert(time - System.currentTimeMillis(),
        TimeUnit.MILLISECONDS);
  }

  @Override
  public int compareTo(Delayed o) {
    return (int) (getDelay(TimeUnit.MILLISECONDS)
        - o.getDelay(TimeUnit.MILLISECONDS));
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof DelayedExtension)) {
      return false;
    }
    DelayedExtension that = (DelayedExtension) o;
    return this.time == that.time && this.onum == that.onum;
  }
}
