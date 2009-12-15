package fabric.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


/**
 * A pair of data I/O streams for the application to communicate with a remote
 * node.
 */
public class Stream {
  private final ChannelMultiplexerThread muxer;
  public final int id;
  public final DataInputStream in;
  public final DataOutputStream out;

  public Stream(ChannelMultiplexerThread muxer, int id, DataInputStream in,
      DataOutputStream out) {
    this.muxer = muxer;
    this.id = id;
    this.in = in;
    this.out = out;
  }

  public void close() throws IOException {
    in.close();
    out.flush();
    out.close();

    muxer.closeStream(id);
  }
}
