package fabric.common.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Queue;

/**
 * A channel manages a single socket, allowing it to be multiplexed across
 * multiple SubSockets.
 * 
 * @author mdgeorge
 */
class Channel {
  
  private Map<Integer, OutputStream> readers;
  private DataOutputStream           out;
  private Queue<OutputStream>        listeners;
  
  /**
   * a thread that reads data off of the input stream and dispatches it to the
   * appropriate reader.
   */
  private class Demuxer implements Runnable {
    public void run() {
      DataInputStream in = null;

      try {
        while(true) {
          int sequenceNumber = in.readInt();
          if (sequenceNumber == 0) {
            // close
            throw new NotImplementedException();
          }

          synchronized(Channel.this) {
            OutputStream reader = readers.get(sequenceNumber);
            if (reader == null) {
              // see if there's a listener, and if so, set up the pipes, else error
              reader = listeners.poll();
              if (reader == null) {
                // no listener, send error back to sender
                out.writeInt(sequenceNumber);
                out.writeInt(0);
                continue;
              }

              readers.put(sequenceNumber, reader);
            }

            int len = in.readInt();
            if (len == 0) {
              // error - deliver to reader
              throw new NotImplementedException();
            }

            for (int i = 0; i < len; i++)
              reader.write(in.readByte());

            reader.flush();
          }
        }
      } catch (final IOException exc) {
        // TODO cleanup
        throw new NotImplementedException();
      }
    }
  }
  
  /**
   * an OutputStream that expands written data to include the sequence number,
   * and writes to the channel's output stream. These should be wrapped in
   * BufferedOutputStreams before being returned.
   */
  private class MuxedOutputStream extends OutputStream {
    private int sequenceNumber;
    
    @Override
    public void write(int arg0) throws IOException {
      throw new NotImplementedException();
    }

    @Override
    public void flush() throws IOException {
      synchronized(out) {
        out.flush();
      }
    }

    @Override
    public void write(byte[] b, int offset, int len) throws IOException {
      synchronized(Channel.this)  {
        out.writeInt(sequenceNumber);
        out.writeInt(len);
        out.write(b, offset, len);
        out.flush();
      }
    }

    @Override
    public void write(byte[] b) throws IOException {
      write (b, 0, b.length);
    }
  }

}
