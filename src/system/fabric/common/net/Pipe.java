package fabric.common.net;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Similar to java.io.PipedInputStream and java.io.PipedOutputStream, but
 * supports non-concurrent use of each pipe endpoint by multiple threads.
 */
public class Pipe {

  private final BlockingQueue<byte[]> queue;
  private final AtomicInteger bytesInQueue;

  private InputStream inputStream;

  private OutputStream outputStream;

  public Pipe() {
    this.queue = new LinkedBlockingQueue<>();
    this.bytesInQueue = new AtomicInteger(0);
    this.inputStream = new InputStream();
    this.outputStream = new OutputStream();
  }

  public InputStream getInputStream() {
    return inputStream;
  }

  public OutputStream getOutputStream() {
    return outputStream;
  }

  public class InputStream extends java.io.InputStream {
    private boolean isClosed;
    private byte[] curBuf;
    private int readPos;

    private InputStream() {
      this.isClosed = false;
      this.curBuf = null;
      this.readPos = 0;
    }

    @Override
    public int read() throws IOException {
      if (waitForBytes() < 1) return -1;

      int result = curBuf[readPos] & 0xff;
      seek(1);
      return result;
    }

    @Override
    public int read(byte[] b) throws IOException {
      return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
      if (len <= 0) return 0;

      int available = waitForBytes();
      if (available == -1) return -1;

      final int result = Math.min(len, available);
      int lenCopied = 0;

      while (lenCopied != result) {
        int bytesToCopy = Math.min(result - lenCopied, curBuf.length - readPos);
        System.arraycopy(curBuf, readPos, b, off, bytesToCopy);

        // Re-establish invariants.
        seek(bytesToCopy);
        off += bytesToCopy;
        lenCopied += bytesToCopy;
      }

      return result;
    }

    @Override
    public long skip(long n) throws IOException {
      if (n <= 0) return 0;

      int available = waitForBytes();
      if (available == -1) return -1;

      long result = Math.min(n, available);
      seek(result);
      return result;
    }

    @Override
    public int available() throws IOException {
      ensureOpen();

      int availableInCurBuf;
      if (curBuf == null) {
        availableInCurBuf = 0;
      } else {
        availableInCurBuf = curBuf.length - readPos;
      }

      return availableInCurBuf + bytesInQueue.get();
    }

    @Override
    public void close() throws IOException {
      isClosed = true;
    }

    /**
     * Seeks over the next <code>length</code> bytes. It is assumed that length
     * ≤ available() and curBuf != null. If length &lt; available, ensures the
     * next byte to read is curBuf[readPos].
     */
    private void seek(long length) throws IOException {
      final long bytesRemaining = available() - length;
      readPos += length;

      while (readPos > curBuf.length) {
        readPos -= curBuf.length;
        dequeue();
      }

      // Dequeue again if we're at the end of curBuf and there are more bytes
      // available.
      if (readPos == curBuf.length && bytesRemaining > 0) {
        readPos = 0;
        dequeue();
      }
    }

    /**
     * Dequeues arrays until a non-empty array is found.
     */
    private void dequeue() throws IOException {
      try {
        do {
          curBuf = queue.take();
        } while (curBuf.length == 0);
        bytesInQueue.addAndGet(-curBuf.length);
      } catch (InterruptedException e) {
        throw new IOException("Blocked read was interrupted");
      }
    }

    /**
     * Blocks until at least one byte is available to be read or the end of
     * stream is reached (i.e., the corresponding output stream is closed).
     * Ensures the next byte to read (if any) is curBuf[readPos].
     *
     * @return
     *          The number of bytes available to be read without blocking. If
     *          the end of the stream has been reached, -1 is returned.
     *
     * @throws IOException
     *          if the stream is closed.
     */
    private int waitForBytes() throws IOException {
      while (true) {
        int available = available();
        if (available > 0) {
          // Bytes are available. Ensure curBuf[readPos] is the next one.
          if (curBuf == null || readPos == curBuf.length) {
            readPos = 0;
            dequeue();
          }
          return available;
        }

        if (outputStream.isClosed) return -1;

        try {
          curBuf = queue.take();
          if (curBuf.length > 0) bytesInQueue.addAndGet(-curBuf.length);
          readPos = 0;
        } catch (InterruptedException e) {
          throw new IOException("Blocked read was interrupted");
        }
      }
    }

    /**
     * Throws an exception if this input stream is closed.
     */
    private void ensureOpen() throws IOException {
      if (isClosed) {
        throw new IOException("stream closed");
      }
    }
  }

  public class OutputStream {
    private boolean isClosed;

    private OutputStream() {
      this.isClosed = false;
    }

    /**
     * Enqueues the given buffer to be read by the corresponding reader. The
     * caller should not modify the buffer after calling this method.
     */
    public void write(byte[] b) throws IOException {
      ensureOpen();
      bytesInQueue.addAndGet(b.length);
      queue.add(b);
    }

    public void close() {
      isClosed = true;
      queue.add(new byte[0]);
    }

    /**
     * Throws an exception if either of this output stream or the corresponding
     * input stream is closed.
     */
    private void ensureOpen() throws IOException {
      if (isClosed) {
        throw new IOException("stream closed");
      }

      if (inputStream.isClosed) {
        throw new IOException("corresponding input stream has been closed");
      }
    }
  }
}
