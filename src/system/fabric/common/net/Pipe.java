package fabric.common.net;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Similar to java.io.PipedInputStream and java.io.PipedOutputStream, but
 * supports non-concurrent use of each pipe endpoint by multiple threads.
 */
public class Pipe {

  private final BlockingQueue<byte[]> queue;

  private InputStream inputStream;

  private OutputStream outputStream;

  public Pipe() {
    this.queue = new LinkedBlockingQueue<>();
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
    private final List<byte[]> buffers;
    private int readPos;
    private int available;

    private InputStream() {
      this.isClosed = false;
      this.buffers = new LinkedList<>();
      this.readPos = 0;
      this.available = 0;
    }

    @Override
    public int read() throws IOException {
      if (waitForBytes() < 1) return -1;

      int result = buffers.get(0)[readPos] & 0xff;
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
      int result = Math.min(len, available);
      int lenCopied = 0;

      while (lenCopied != result) {
        byte[] curBuf = buffers.get(0);
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
      long result = Math.min(n, available);
      seek(result);
      return result;
    }

    @Override
    public int available() throws IOException {
      ensureOpen();

      // Pull buffers off the queue.
      while (true) {
        byte[] buffer = queue.poll();
        if (buffer == null) break;
        if (buffer.length == 0) continue;

        buffers.add(buffer);
        available += buffer.length;
      }

      return available;
    }

    @Override
    public void close() throws IOException {
      isClosed = true;
    }

    /**
     * Seeks over the next n bytes and notifies writers that space has been
     * freed. It is assumed that n is at most the number of bytes available to
     * be read.
     */
    private void seek(long length) {
      readPos += length;
      available -= length;
      while (readPos != 0 && readPos >= buffers.get(0).length) {
        byte[] spentBuffer = buffers.remove(0);
        readPos -= spentBuffer.length;
      }
    }

    /**
     * Blocks until at least one byte is available to be read or the end of
     * stream is reached (i.e., the corresponding output stream is closed).
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
        if (available > 0) return available;
        if (outputStream.isClosed) return -1;

        try {
          byte[] buffer = queue.take();
          if (buffer.length == 0) continue;

          buffers.add(buffer);
          this.available += buffer.length;
        } catch (InterruptedException e) {
          throw new IOException("Blocked read was interrupted");
        }
      }
    }

    /**
     * Ensures this input stream is open.
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
      queue.add(b);
    }

    public void close() {
      isClosed = true;
      queue.add(new byte[0]);
    }

    /**
     * Ensures this output stream and the corresponding input stream are both
     * open.
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
