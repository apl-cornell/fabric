package fabric.common.net.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import fabric.common.exceptions.NotImplementedException;
import fabric.net.RemoteNode;
import fabric.worker.remote.RemoteWorker;

/**
 * @param <Node> the type of node at the remote endpoint.
 */
public class HandshakeComposite<Node extends RemoteNode<Node>> implements
Protocol<Node> {

  private Map<String, Protocol<Node>> handshakes;
  private Protocol<Node> outgoing;

  @SafeVarargs
  public HandshakeComposite(Protocol<Node>... protocols) {
    this.handshakes = new HashMap<>(protocols.length);
    for (Protocol<Node> p : protocols) {
      this.handshakes.put(p.getClass().getName(), p);
    }

    this.outgoing = protocols[0];
  }

  @Override
  public ShakenSocket<Node> initiate(Node node, Socket s) throws IOException {
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    out.writeUTF(outgoing.getClass().getName());
    return outgoing.initiate(node, s);
  }

  @Override
  public ShakenSocket<RemoteWorker> receive(Socket s) throws IOException {
    DataInputStream in = new DataInputStream(s.getInputStream());
    String protName = in.readUTF();

    Protocol<Node> protocol = this.handshakes.get(protName);
    if (null == protocol)
      // TODO
      throw new NotImplementedException(handshakes.keySet() + "||" + protName);

    return protocol.receive(s);
  }

}
