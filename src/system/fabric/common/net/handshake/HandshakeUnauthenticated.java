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
