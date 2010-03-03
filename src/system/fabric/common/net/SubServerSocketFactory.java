package fabric.common.net;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import fabric.common.net.handshake.HandshakeProtocol;
import fabric.common.net.handshake.ShakenSocket;
import fabric.common.net.naming.NameService;
import fabric.common.net.naming.SocketAddress;


/**
 * factory for creating SubServerSockets. This class decorates a
 * javax.net.ServerSocketFactory, which is used for instantiating the underlying
 * channels.
 * 
 * @author mdgeorge
 */
public class SubServerSocketFactory {
  private final HandshakeProtocol handshake;
  private final NameService       nameService;
  private final Map<SocketAddress, Acceptor> acceptors;

  /** Creates a new SubServerSocketFactory decorating the given
   * ServerSocketFactory.
   * 
   * @param factory the ServerSocketFactory that will be used to create the
   *        ServerSockets used to implement SubServerSockets returned by this
   */
  public SubServerSocketFactory(HandshakeProtocol handshake, NameService nameService) {
    this.handshake   = handshake;
    this.nameService = nameService;
    this.acceptors   = new HashMap<SocketAddress, Acceptor> ();
  }

  /** create an unbound server socket. */
  public SubServerSocket createServerSocket() {
    return new SubServerSocket(this);
  }

  /** convenience method */
  public SubServerSocket createServerSocket(String host) throws IOException {
    return createServerSocket(nameService.localResolve(host));
  }

  /** convenience method */
  public SubServerSocket createServerSocket(String host, int backlog) throws IOException {
    return createServerSocket(nameService.localResolve(host), backlog);
  }

  /** convenience method */
  public SubServerSocket createServerSocket(SocketAddress local) throws IOException {
    return createServerSocket(local, 50);
  }
  
  /** create a server socket to await connections to the given local host name
   * and port number.
   *
   * @param local   the (virtual) local address
   * @param backlog the number of waiting connections to allow on this socket
   * @see javax.net.ServerSocketFactory#createServerSocket(int, int, InetAddress)
   */
  public SubServerSocket createServerSocket(SocketAddress local, int backlog) throws IOException {
    SubServerSocket result = new SubServerSocket(this);
    result.bind(local, backlog);
    return result;
  }

  /**
   * return an acceptor associated with the given (local) address, creating it
   * if necessary.
   */
  synchronized Acceptor getAcceptor(SocketAddress addr) throws IOException {
    Acceptor result = acceptors.get(addr);
    if (null == result) {
      result = new Acceptor(this, addr);
      acceptors.put(addr, result);
    }
    return result;
  }

  ShakenSocket receive(Socket s) throws IOException {
    return handshake.receive(s);
  }
}

