package fabric.common.net;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.net.SocketFactory;

import fabric.common.net.handshake.HandshakeProtocol;
import fabric.common.net.naming.NameService;
import fabric.common.net.naming.SocketAddress;


/**
 * A factory for creating SubSockets. The factory decorates a
 * javax.net.SocketFactory, which is used for creating the underlying channels.
 * 
 * @author mdgeorge
 */
public final class SubSocketFactory {
  private final HandshakeProtocol protocol;
  private final NameService       nameService;
  private final Map<SocketAddress, ClientChannel> channels;

  /**
   * Create a new SubSocket factory that decorates the given SocketFactory.
   * Note that SubSockets created from different SubSocketFactories will not
   * attempt to share channels (as these channels may have different underlying
   * socket implementations).
   */ 
  public SubSocketFactory(HandshakeProtocol protocol, NameService nameService) {
    this.protocol    = protocol;
    this.nameService = nameService;
    this.channels    = new HashMap<SocketAddress, ClientChannel>();
  }

  /**
   * Create an unconnected socket.
   */
  public SubSocket createSocket() {
    return new SubSocket(this);
  }

  /**
   * Convenience method.  Resolves the name using the NameService and calls
   * createSocket.
   */
  public SubSocket createSocket(String name) throws IOException {
    return createSocket(nameService.resolve(name));
  }

  /**
   * Convenience method.  Creates a socket and connects it to the given address.
   */
  public SubSocket createSocket(SocketAddress addr) throws IOException {
    SubSocket result = createSocket();
    result.connect(addr);
    return result;
  }

  /**
   * return a channel associated with the given address, creating it if necessary.
   */
  synchronized ClientChannel getChannel(SocketAddress addr) throws IOException {
    ClientChannel result = channels.get(addr);
    if (null == result) {
      result = new ClientChannel(protocol.initiate(addr));
      channels.put(addr, result);
    }

    return result;
  }
}
