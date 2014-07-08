/**
 * Copyright (C) 2010 Fabric project group, Cornell University
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
package fabric.common.net;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.net.SocketFactory;

import fabric.common.net.Channel.Connection;
import fabric.common.net.handshake.HandshakeProtocol;
import fabric.common.net.handshake.ShakenSocket;
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
  private final Map<String, ClientChannel> channels;

  /**
   * Create a new SubSocket factory that decorates the given SocketFactory.
   * Note that SubSockets created from different SubSocketFactories will not
   * attempt to share channels (as these channels may have different underlying
   * socket implementations).
   */ 
  public SubSocketFactory(HandshakeProtocol protocol, NameService nameService) {
    this.protocol    = protocol;
    this.nameService = nameService;
    this.channels    = new HashMap<String, ClientChannel>();
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
    SubSocket result = createSocket();
    result.connect(name);
    return result;
  }

  /**
   * return a channel associated with the given address, creating it if necessary.
   */
  synchronized ClientChannel getChannel(String name) throws IOException {
    ClientChannel result = channels.get(name);
    if (null == result) {
      result = new ClientChannel(name, nameService.resolve(name));
      channels.put(name, result);
    }

    return result;
  }
  
  
  /**
   * Client channels are capable of making outgoing requests, but not of receiving
   * new incoming requests.  They are only associated with a remote address, and
   * have no distinguished local address.
   * 
   * @author mdgeorge
   */
  class ClientChannel extends Channel {
    /* key for SubSocketFactory.this.channels */
    private final String name;

    /* the remote address */
    private final SocketAddress addr;
    
    /* the next sequence number to be created */
    private int nextSequenceNumber;
    
    public ClientChannel(String name, SocketAddress addr) throws IOException {
      super(protocol.initiate(name, addr));
      
      this.addr          = addr;
      this.name          = name;
      nextSequenceNumber = 1;
      
      setName("demultiplexer for " + toString());
    }

    /** initiate a new substream */
    public Connection connect() throws IOException {
      return new Connection(nextSequenceNumber++); 
    }

    @Override
    public Connection accept(int sequence) throws IOException {
      throw new IOException("unexpected accept request on client channel");
    }
    
    @Override
    public void cleanup() {
      throw new NotImplementedException();
    }

    @Override
    public String toString() {
      return "channel to " + name + " [" + addr.toString() + "]";
    }
  }
}
