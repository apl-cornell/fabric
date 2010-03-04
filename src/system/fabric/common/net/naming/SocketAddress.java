package fabric.common.net.naming;

import java.net.InetAddress;

/**
 * This class encapsulates a host name and a port number. It is similar to
 * java.net.InetSocketAddress, except that it does not attempt to perform any
 * name resolution.
 * 
 * @author mdgeorge
 */
public final class SocketAddress {
  private final InetAddress addr;
  private final int         port;

  public SocketAddress(String name, InetAddress addr, int port) {
    this.addr = addr;
    this.port = port;
  }

  public int getPort() {
    return this.port;
  }

  public InetAddress getAddress() {
    return this.addr;
  }
  
  @Override
  public String toString() {
    return this.addr + ":" + this.port;
  }
  
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof SocketAddress)) return false;

    SocketAddress a = (SocketAddress) other;
    return this.addr.equals(a.addr) && this.port == a.port;
  }

  @Override
  public int hashCode() {
    return this.addr.hashCode() ^ this.port;
  }
}
