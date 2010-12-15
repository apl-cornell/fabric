package fabric.common.net.handshake;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import fabric.common.exceptions.InternalError;
import fabric.common.net.naming.SocketAddress;

public abstract class HandshakeProtocol {
  private final ProtocolType protocolType;
  
  protected HandshakeProtocol(ProtocolType protocolType) {
    this.protocolType = protocolType;
  }
  
  /**
   * Sends a header indicating the handshake type and initiates a handshake with
   * a remote host at the given address.
   * 
   * @param name
   *          name of the virtual host
   * @param addr
   *          the IP address and port number for the host with which to initiate
   *          a handshake.
   */
  public final ShakenSocket initiate(String name, SocketAddress addr)
      throws IOException {
    Socket s = new Socket(addr.getAddress(), addr.getPort());
    fixSocket(s);
    
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    out.writeByte(protocolType.ordinal());
    out.flush();
    
    return initiateImpl(name, s);
  }

  /**
   * Initiates a handshake with a remote host via the given socket.
   * 
   * @param name
   *          name of the remote virtual host
   * @param s
   *          a socket connected to the remote node.
   */
  protected abstract ShakenSocket initiateImpl(String name, Socket s)
      throws IOException;

  /**
   * Receives a handshake via the given socket.
   * 
   * @param s
   *          the socket on which to receive a handshake.
   */
  protected abstract ShakenSocket receive(Socket s) throws IOException;
  
  private void fixSocket(Socket s) throws IOException {
    s.setSoLinger(false, 0);
    s.setTcpNoDelay(true);
  }

  public enum ProtocolType {
    AUTHENTICATED(HandshakeAuthenticated.class),
    BOGUS(BogusAuthenticatedHandshake.class),
    UNAUTHENTICATED(HandshakeUnauthenticated.class);
    
    private Class<? extends HandshakeProtocol> protocolClass;
    
    ProtocolType(Class<? extends HandshakeProtocol> protocolClass) {
      this.protocolClass = protocolClass;
    }
    
    public HandshakeProtocol instantiate() {
      try {
        return protocolClass.newInstance();
      } catch (InstantiationException e) {
        throw new InternalError(e);
      } catch (IllegalAccessException e) {
        throw new InternalError(e);
      }
    }
  }
}
