/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
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

import fabric.common.net.RemoteIdentity;
import fabric.net.RemoteNode;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;

public class HandshakeUnauthenticated<Node extends RemoteNode<Node>> implements
    Protocol<Node> {
  //
  // an incredibly simple handshake:
  // client -> server : name
  //
  private final String localName;

  public HandshakeUnauthenticated(String localName) {
    this.localName = localName;
  }

  /**
   * @param remoteNode the node to connect to.
   */
  @Override
  public ShakenSocket<Node> initiate(Node remoteNode, Socket s)
      throws IOException {
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    out.writeUTF(remoteNode.name);
    out.writeUTF(localName);
    out.flush();
    return new ShakenSocket<>(remoteNode.name, new RemoteIdentity<>(remoteNode,
        null), s);
  }

  @Override
  public ShakenSocket<RemoteWorker> receive(Socket s) throws IOException {
    DataInputStream in = new DataInputStream(s.getInputStream());

    String name = in.readUTF();

    String remoteWorkerName = in.readUTF();
    RemoteWorker remoteWorker = Worker.getWorker().getWorker(remoteWorkerName);

    return new ShakenSocket<>(name, new RemoteIdentity<>(remoteWorker, null), s);
  }

}
