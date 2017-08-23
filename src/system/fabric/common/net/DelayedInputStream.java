package fabric.common.net;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

import fabric.common.Logging;
import fabric.common.Threading;
import fabric.common.exceptions.NotImplementedException;

/**
 * An InputStream that delays data coming from an underlying InputStream. The
 * {@link DelayedInputStream} eagerly reads data from the underlying
 * InputStream and will only make that data available after a certain time
 * delay.
 */
public class DelayedInputStream extends InputStream {

  private static final int READY_QUEUE_CAP = 100;

  /**
   * The underlying {@link InputStream}.
   */
  private final InputStream in;

  /**
   * The delay to impose on the incoming data, in nanoseconds.
   */
  private final long delay_ns;

  /**
   * Runnable for shuffling data from {@link in} to {@link delayQueue}.
   */
  private final Reader reader;

  /**
   * The queue of buffers that have been read from the underlying input stream,
   * and are undergoing delay.
   */
  private final DelayQueue<DelayedBuffer> delayQueue;

  /**
   * The queue of buffers that have undergone delay and are ready to be read.
   */
  private final ArrayDeque<DelayedBuffer> readyQueue;

  /**
   * Indicates whether the end of the stream has been reached.
   */
  private boolean endReached;

  /**
   * Indicates whether this input stream is closed.
   */
  private boolean closed;

  /**
   * Reads from {@link in} into {@link delayQueue}.
   */
  private class Reader extends Threading.NamedRunnable {
    private volatile boolean running = true;

    public Reader(String name) {
      super(name);
    }

    @Override
    public void runImpl() {
      try (InputStream in = DelayedInputStream.this.in) {
        // In a loop, read from the underlying input stream, and write to the
        // delay queue. If we ever get an IOException or reach the end of the
        // input stream, signal the end of the stream by writing an empty buffer
        // to the delay queue.
        while (running) {
          DelayedBuffer buf = DelayedBuffer.getInstance();
          try {
            int n = in.read(buf.buf.array());
            if (n == -1) {
              // End of input stream.
              return;
            }
            buf.buf.position(n);
          } catch (IOException e) {
            throw new NotImplementedException(e);
          } finally {
            buf.buf.flip();
            long now = System.nanoTime();
            buf.expiry_ns = now + delay_ns;
            delayQueue.put(buf);
          }
        }
      } catch (IOException e) {
      }
    }

    public void shutdown() {
      running = false;
    }
  }

  /**
   * @param name
   *         a name to associate with this input stream.
   * @param in
   *         the InputStream from which data is read.
   * @param delay_ms
   *         the delay to impose on the incoming data, in milliseconds.
   */
  public DelayedInputStream(String name, InputStream in, int delay_ms) {
    this.in = in;
    this.delay_ns =
        TimeUnit.NANOSECONDS.convert(delay_ms, TimeUnit.MILLISECONDS);
    this.delayQueue = new DelayQueue<>();
    this.readyQueue = new ArrayDeque<>(READY_QUEUE_CAP);
    this.endReached = false;
    this.closed = false;

    this.reader = new Reader("Reader for DelayedInputStream from " + name);
    Threading.getPool().submit(reader);
  }

  @Override
  public int read() throws IOException {
    if (closed) throw new IOException("Stream Closed");

    if (endReached) return -1;

    readReady(ReadMode.BLOCKING);
    if (checkEOF()) return -1;

    DelayedBuffer buf = readyQueue.peek();
    int result = buf.buf.get() & 0xff;
    if (!buf.buf.hasRemaining()) {
      readyQueue.remove().recycle();
    }

    // TODO Auto-generated method stub
    return result;
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    if (closed) throw new IOException("Stream Closed");

    if (endReached) return -1;

    readReady(ReadMode.BLOCKING);
    if (checkEOF()) return -1;

    int result = 0;
    while (result < len) {
      DelayedBuffer buf = readyQueue.peek();
      if (buf == null) return result;

      int n = Math.min(buf.buf.remaining(), len - result);
      buf.buf.get(b, off + result, n);
      result += n;

      if (!buf.buf.hasRemaining()) {
        readyQueue.poll().recycle();
        if (checkEOF()) return result;
      }
    }

    return result;
  }

  @Override
  public int available() throws IOException {
    if (closed) throw new IOException("Stream Closed");

    if (endReached) return 0;

    readReady(ReadMode.NONBLOCKING);
    if (checkEOF()) return 0;

    int result = 0;
    for (DelayedBuffer buf : readyQueue) {
      result += buf.buf.remaining();
    }

    return result;
  }

  private enum ReadMode {
    BLOCKING, NONBLOCKING
  }

  /**
   * Reads from the {@link #delayQueue} into the {@link #readyQueue} until
   * either there are no more buffers that are ready, or the {@link #readyQueue}
   * is full.
   * <p>
   * <b>Preconditions:</b>
   * <ul>
   * <li>{@link #endReached} is false.
   * <li> either the {@link #readyQueue} is empty or its first element still has
   * unread bytes.
   * </ul>
   * <p>
   * <b>Postcondition:</b> if {@link ReadMode#BLOCKING} mode is specified, then
   * the {@link #readyQueue} will be non-empty.
   */
  private void readReady(ReadMode mode) {
    if (mode == ReadMode.BLOCKING && readyQueue.isEmpty()) {
      // Need to read at least one delayed buffer.
      DelayedBuffer buf;
      while (true) {
        try {
          buf = delayQueue.take();
          break;
        } catch (InterruptedException e) {
          Logging.logIgnoredInterruptedException(e);
        }
      }
      readyQueue.add(buf);
    }

    while (readyQueue.size() < READY_QUEUE_CAP) {
      DelayedBuffer buf = delayQueue.poll();
      if (buf == null) break;
      readyQueue.add(buf);
    }
  }

  /**
   * Checks for the end of the stream. The end of the stream is reached when the
   * {@link #readyQueue} is non-empty and its first element is an empty buffer.
   * If the end of the stream is reached {@link #endReached} is updated.
   * <p>
   * <b>Precondition:</b> {@link #endReached} is false.
   *
   * @return the updated value of {@link endReached}.
   */
  private boolean checkEOF() {
    DelayedBuffer buf = readyQueue.peek();
    if (buf == null) return false;

    if (buf.buf.position() == 0 && buf.buf.remaining() == 0) {
      readyQueue.poll().recycle();
      endReached = true;
    }

    return endReached;
  }

  @Override
  public void close() throws IOException {
    reader.shutdown();
    closed = true;

    // Drain the ready queue.
    while (true) {
      DelayedBuffer buf = readyQueue.poll();
      if (buf == null) break;
      buf.recycle();
    }
  }
}
