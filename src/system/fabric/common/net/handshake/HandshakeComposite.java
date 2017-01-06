package fabric.common.net.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

import fabric.common.exceptions.NotImplementedException;
import fabric.net.RemoteNode;
import fabric.worker.remote.RemoteWorker;

/**
 * @param <Node> the type of node at the remote endpoint.
 */
public class HandshakeComposite<Node extends RemoteNode<Node>>
    implements Protocol<Node> {

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
  public ShakenSocket<Node> initiate(Node node, SocketChannel s)
      throws IOException {
    DataOutputStream out = new DataOutputStream(s.socket().getOutputStream());
    out.writeUTF(outgoing.getClass().getName());
    return outgoing.initiate(node, s);
  }

  @Override
  public ShakenSocket<RemoteWorker> receive(SocketChannel s)
      throws IOException {
    DataInputStream in = new DataInputStream(s.socket().getInputStream());
    String protName = in.readUTF();

    Protocol<Node> protocol = this.handshakes.get(protName);
    if (null == protocol)
      // TODO
      throw new NotImplementedException(handshakes.keySet() + "||" + protName);

    return protocol.receive(s);
  }

}
