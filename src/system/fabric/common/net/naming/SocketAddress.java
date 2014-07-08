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
