package fabric.common.net.naming;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * This class encapsulates a host name and a port number. It is similar to
 * java.net.InetSocketAddress, except that it does not attempt to perform any
 * name resolution.
 *
 * @author mdgeorge
 */
public final class SocketAddress {
  private final InetAddress addr;
  private final int port;

  public SocketAddress(InetAddress addr, int port) {
    this.addr = addr;
    this.port = port;
  }

  public int getPort() {
    return this.port;
  }

  public InetAddress getAddress() {
    return this.addr;
  }

  public InetSocketAddress toInetSocketAddress() {
    return new InetSocketAddress(getAddress(), getPort());
  }

  @Override
  public String toString() {
    String result = (this.addr == null) ? "" : this.addr.toString();
    return result + ":" + this.port;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SocketAddress)) return false;

    SocketAddress other = (SocketAddress) o;

    if (this.port != other.port) return false;

    if (null == this.addr && null == other.addr) return true;

    if (null == this.addr || null == other.addr) return false;

    return this.addr.equals(other.addr);
  }

  @Override
  public int hashCode() {
    // TODO: perhaps this could be better

    if (null == this.addr) return this.port;

    return this.addr.hashCode() ^ this.port;
  }
}
