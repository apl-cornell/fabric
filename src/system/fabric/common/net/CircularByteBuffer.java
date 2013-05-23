package fabric.common.net;

import java.io.IOException;

import fabric.common.Logging;

/**
 * Similar to java.io.PipedInputStream and java.io.PipedOutputStream, but
 * supports multithreaded use of each pipe endpoint. This class is thread-safe.
 */
public class CircularByteBuffer {

  private static int DEFAULT_CAPACITY = 16 * 1024;

  /**
   * Backing buffer. Also acts as the lock.
   */
  private byte[] buffer;

  /**
   * Index of next unread byte.
   */
  private int readPos;

  /**
   * Index of next byte to write.
   */
  private int writePos;

  private InputStream inputStream;

  private OutputStream outputStream;

  public CircularByteBuffer() {
    this(DEFAULT_CAPACITY);
  }

  public CircularByteBuffer(int capacity) {
    this.buffer = new byte[capacity];
    this.readPos = 0;
    this.writePos = 0;
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

    private InputStream() {
      this.isClosed = false;
    }

    @Override
    public int read() throws IOException {
      synchronized (buffer) {
        if (waitForBytes() < 1) return -1;

        int result = buffer[readPos] & 0xff;
        seek(1);
        return result;
      }
    }

    @Override
    public int read(byte[] b) throws IOException {
      return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
      if (len <= 0) return 0;

      synchronized (buffer) {
        int available = waitForBytes();
        int result = (len < available) ? len : available;

        // First, copy as far as the end of the buffer.
        int maxBytesToCopy = buffer.length - readPos;
        int bytesToCopy = (result < maxBytesToCopy) ? result : maxBytesToCopy;
        System.arraycopy(buffer, readPos, b, off, bytesToCopy);

        // Re-establish invariants.
        seek(bytesToCopy);
        off += bytesToCopy;
        bytesToCopy = result - bytesToCopy;

        // Copy the rest.
        System.arraycopy(buffer, readPos, b, off, bytesToCopy);
        seek(bytesToCopy);

        return result;
      }
    }

    @Override
    public long skip(long n) throws IOException {
      if (n <= 0) return 0;

      synchronized (buffer) {
        int available = waitForBytes();
        long result = (n < available) ? n : available;
        seek(result);
        return result;
      }
    }

    @Override
    public int available() throws IOException {
      ensureOpen();

      // Handle case where available data is all in one contiguous chunk.
      if (writePos >= readPos) return writePos - readPos;

      // Handle case where available data wraps around the end of the buffer.
      return writePos + buffer.length - readPos;
    }

    @Override
    public void close() throws IOException {
      synchronized (buffer) {
        isClosed = true;
        buffer.notifyAll();
      }
    }

    /**
     * Seeks over the next n bytes and notifies writers that space has been
     * freed. It is assumed that n is at most the number of bytes available to
     * be read.
     */
    private void seek(long length) {
      readPos += length;
      if (readPos >= buffer.length) readPos -= buffer.length;
      buffer.notifyAll();
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
          buffer.wait();
        } catch (InterruptedException e) {
          // Ignore. (Should really be handling this, but requires plumbing to
          // clean up any remote state that may have resulted from the message
          // whose reply we're waiting for.)
          Logging.logIgnoredInterruptedException(e);
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

  public class OutputStream extends java.io.OutputStream {
    private boolean isClosed;

    private OutputStream() {
      this.isClosed = false;
    }

    @Override
    public void write(int b) throws IOException {
      synchronized (buffer) {
        waitForSpace();
        buffer[writePos] = (byte) (b & 0xff);
        seek(1);
      }
    }

    @Override
    public void write(byte[] b) throws IOException {
      write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      synchronized (buffer) {
        while (true) {
          int avail = waitForSpace();
          int toWrite = (avail < len) ? avail : len;

          // First, copy as far as the end of the buffer.
          int maxBytesToCopy = buffer.length - writePos;
          int bytesToCopy =
              (toWrite < maxBytesToCopy) ? toWrite : maxBytesToCopy;
          System.arraycopy(b, off, buffer, writePos, bytesToCopy);

          // Re-establish invariants.
          seek(bytesToCopy);
          off += bytesToCopy;
          bytesToCopy = toWrite - bytesToCopy;

          // Copy the rest.
          System.arraycopy(b, off, buffer, writePos, bytesToCopy);

          // Re-establish invariants again.
          seek(bytesToCopy);
          off += bytesToCopy;

          len -= toWrite;
          if (len == 0) return;
        }
      }
    }

    @Override
    public void close() throws IOException {
      synchronized (buffer) {
        isClosed = true;
        buffer.notifyAll();
      }
    }

    /**
     * Advances the write pointer by n bytes and notifies readers that data is
     * available to be read. It is assumed that n is at most the amout of space
     * available to be written.
     */
    private void seek(long length) {
      writePos += length;
      if (writePos >= buffer.length) writePos -= buffer.length;
      buffer.notifyAll();
    }

    /**
     * Blocks until there is space available to write.
     * 
     * @return
     *          The number of bytes available to be written.
     *          
     * @throws IOException
     *          if this stream or the corresponding input stream is closed.
     */
    private int waitForSpace() throws IOException {
      while (true) {
        ensureOpen();

        // Calculate space available.
        final int spaceAvail;
        if (readPos > writePos) {
          // Available space is all in one contiguous chunk.
          spaceAvail = readPos - writePos - 1;
        } else {
          // Available space wraps around the end of the buffer.
          spaceAvail = readPos + buffer.length - 1 - writePos;
        }

        if (spaceAvail > 0) return spaceAvail;

        try {
          buffer.wait();
        } catch (InterruptedException e) {
          throw new IOException("Blocked write was interrupted");
        }
      }
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
