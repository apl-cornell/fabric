package fabric.common.net;

import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * A buffer with a time at which it is available to be read.
 */
class DelayedBuffer implements Delayed {
  private static final int BUF_SIZE = 64 * 1024;
  private static final int BUF_POOL_CAP = 100;

  private static final BlockingQueue<DelayedBuffer> POOL =
      new LinkedBlockingQueue<>(BUF_POOL_CAP);

  /**
   * The time (relative to {@link System#nanoTime()}) at which this buffer
   * should be made available.
   */
  long expiry_ns;
  final ByteBuffer buf;

  private DelayedBuffer() {
    buf = ByteBuffer.allocate(BUF_SIZE);
  }

  /**
   * Factory method for obtaining a {@link DelayedBuffer}. Clients should call
   * {@link #recycle()} when done using the buffer.
   */
  public static DelayedBuffer getInstance() {
    DelayedBuffer buf = POOL.poll();
    return buf == null ? new DelayedBuffer() : buf;
  }

  @Override
  public int compareTo(Delayed o) {
    long otherExpiry = ((DelayedBuffer) o).expiry_ns;
    return expiry_ns > otherExpiry ? 1 : expiry_ns == otherExpiry ? 0 : -1;
  }

  @Override
  public long getDelay(TimeUnit unit) {
    long delay_ns = expiry_ns - System.nanoTime();
    return unit.convert(delay_ns, TimeUnit.NANOSECONDS);
  }

  /**
   * Called to indicate that the client is done using this buffer.
   */
  public void recycle() {
    buf.clear();
    POOL.offer(this);
  }
}
