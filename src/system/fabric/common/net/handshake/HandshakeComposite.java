/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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
package fabric.common.net.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import fabric.common.exceptions.NotImplementedException;

public class HandshakeComposite implements Protocol {

  private Map<String, Protocol> handshakes;
  private Protocol outgoing;

  public HandshakeComposite(Protocol... protocols) {
    this.handshakes = new HashMap<String, Protocol>(protocols.length);
    for (Protocol p : protocols) {
      this.handshakes.put(p.getClass().getName(), p);
    }

    this.outgoing = protocols[0];
  }

  @Override
  public ShakenSocket initiate(String name, Socket s) throws IOException {
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    out.writeUTF(outgoing.getClass().getName());
    return outgoing.initiate(name, s);
  }

  @Override
  public ShakenSocket receive(Socket s) throws IOException {
    DataInputStream in = new DataInputStream(s.getInputStream());
    String protName = in.readUTF();

    Protocol protocol = this.handshakes.get(protName);
    if (null == protocol)
    // TODO
      throw new NotImplementedException(handshakes.keySet() + "||" + protName);

    return protocol.receive(s);
  }

}
