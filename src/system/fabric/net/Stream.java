/**
 * Copyright (C) 2010 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
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
