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
package fabric.net;

import static fabric.common.Logging.NETWORK_CONNECTION_LOGGER;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.SocketChannel;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import javax.security.auth.x500.X500Principal;

import fabric.common.Options;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.NoSuchNodeError;
import fabric.common.net.naming.SocketAddress;
import fabric.lang.security.NodePrincipal;
import fabric.net.ChannelMultiplexerThread.CallbackHandler;
import fabric.worker.Worker;

/**
 * <p>
 * Manages a connection with a remote node. The connection is used to send
 * requests to and receive responses from the node.
 * </p>
 * <p>
 * XXX Assumes connections never get dropped.
 * </p>
 */
class CommManager {
  private final RemoteNode node;
  private final ChannelMultiplexerThread muxer;
  private final boolean useSSL;

  /**
   * @param useSSL
   *          whether the data should be sent on the network with SSL
   *          encryption.
   */
  CommManager(RemoteNode remoteNode, boolean useSSL) {
    this.node = remoteNode;
    this.useSSL = useSSL;

    // Set up the connection.
    SocketChannel socketChannel = connect();

    CallbackHandler handler = new CallbackHandler() {
      public void newStream(ChannelMultiplexerThread muxer, int streamID) {
        // We are creating all the streams here.
        throw new InternalError("Unexpected stream ID: " + streamID);
      }

      public void connectionClosed() {
        throw new InternalError("Connection to " + node
            + " unexpectedly closed.");
      }

      public void shutdown() {
      }
    };
    try {
      this.muxer =
          new ChannelMultiplexerThread(handler,
              "Thread for communicating with node " + remoteNode.name,
              socketChannel);
    } catch (IOException e) {
      throw new InternalError(e);
    }

    muxer.start();
  }

  /**
   * Connects to the remote node and initializes the session.
   * 
   * @see fabric.common.AbstractConnectionHandler
   * @return a connected SocketChannel configured in blocking mode.
   */
  private SocketChannel connect() {
    Worker worker = Worker.getWorker();
    int hostIdx = 0;

    // These will be filled in with real values if needed.
    List<InetSocketAddress> hosts = null;
    Principal nodePrincipal = null;
    int numHosts = 0;
    int startHostIdx = 0;

    for (int retry = 0; worker.retries < 0 || retry < worker.retries;) {
      try {
        if (hosts == null) {
          //
          // Note: This code originally cycled through a list of addresses, and
          //       thus looks more complicated than it is.  I'm not fixing it
          //       since it is soon to be replaced.  -mdg
          //
          SocketAddress addr = node.lookup();
          nodePrincipal      = new X500Principal("cn=" + node.name());

          hosts = Collections.singletonList(addr.toInetSocketAddress());

          numHosts = 1;
          startHostIdx = 0;
        }

        // Attempt to establish a connection.
        int hostNum = (startHostIdx + hostIdx) % numHosts;
        return connect(hosts.get(hostNum), nodePrincipal);
      } catch (NoSuchNodeError e) {
        // Connected to a system that doesn't host the node we're interested
        // in.
        // Increment loop counter variables.

        NETWORK_CONNECTION_LOGGER.log(Level.WARNING,
            "Failed to connect to " + node.name(), e);

        hostIdx++;
        if (hostIdx == numHosts) {
          hostIdx = 0;
          if (worker.retries >= 0) retry++;
        }
        continue;
      } catch (IOException e) {
        // Retry.
        NETWORK_CONNECTION_LOGGER.log(Level.WARNING, "Failed to connect to "
            + node.name(), e);

        if (hosts == null) {
          // Attempt to reuse an existing connection failed. Just restart the
          // loop.
          continue;
        }

        // Increment loop counter variables.
        hostIdx++;
        if (hostIdx == numHosts) {
          hostIdx = 0;
          if (worker.retries >= 0) retry++;
        }
        continue;
      }
    }

    throw new UnreachableNodeException(node);
  }

  /**
   * <p>
   * Establishes a connection with a node at the given host. A helper for
   * <code>send(ByteBuffer)</code>.
   * </p>
   * 
   * @param addr
   *          The host to connect to.
   * @param remotePrincipal
   *          The principal associated with the node we're connecting to.
   * @throws NoSuchNodeError
   *           If the node is not hosted at the given address.
   * @throws IOException
   *           If there was an I/O error.
   * @return a connected SocketChannel configured in blocking mode.
   */
  private SocketChannel connect(InetSocketAddress addr,
      Principal remotePrincipal) throws NoSuchNodeError, IOException {
    Worker worker = Worker.getWorker();
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.configureBlocking(true);

    Socket socket = socketChannel.socket();
    socket.setTcpNoDelay(true);
    socket.setKeepAlive(true);

    // Attempt to connect to the remote host.
    socketChannel.socket().connect(addr, worker.timeout);

    // Give the name of the node we're interested in.
    DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
    dataOut.writeUTF(node.name);
    
    // Give our node name.
    dataOut.writeUTF(Worker.getWorker().name);

    // Specify whether we're encrypting.
    dataOut.writeBoolean(useSSL);
    dataOut.flush();

    // Determine whether the store exists at the node.
    if (socket.getInputStream().read() == 0) throw new NoSuchNodeError();

    return initializeSession(socketChannel, dataOut);
  }

  private SocketChannel initializeSession(SocketChannel connection,
      DataOutputStream out) throws IOException {
    Worker worker = Worker.getWorker();

    // Nothing to do if we're connecting as a dissemination node.
    if (!useSSL) return connection;

    if (!Options.DEBUG_NO_SSL) {
      // XXX TODO Start encrypting.
      // SSLSocket sslSocket;
      // synchronized (worker.sslSocketFactory) {
      // sslSocket =
      // (SSLSocket) worker.sslSocketFactory.createSocket(socket, node.name,
      // addr.getPort(), true);
      // }
      // sslSocket.setUseClientMode(true);
      // sslSocket.startHandshake();
      //
      // // Make sure we're talking to the right node.
      // X500Principal peer =
      // (X500Principal) sslSocket.getSession().getPeerPrincipal();
      // if (!peer.equals(remotePrincipal)) {
      // Logger.getLogger(this.getClass().getName()).info(
      // "Rejecting connection to " + addr + ": got principal " + peer
      // + " when we expected " + remotePrincipal);
      // sslSocket.close();
      // throw new IOException();
      // }
      out.writeUTF(worker.javaPrincipal.getName());
      out.flush();
    } else {
      out.writeUTF(worker.javaPrincipal.getName());
      out.flush();
    }

    // Send to the store a pointer to our principal object.
    NodePrincipal principal = worker.getPrincipal();
    out.write(principal != null ? 1 : 0);
    if (principal != null) {
      out.writeUTF(principal.$getStore().name());
      out.writeLong(principal.$getOnum());
    }
    out.flush();

    return connection;
  }
  
  /**
   * @return a DataInputStream/DataOutputStream pair for a new substream
   */
  public Stream openStream() {
    try {
      // APP --[outbound]--> CommManager
      // APP <--[inbound ]-- CommManager
      Pipe inbound = Pipe.open();
      Pipe outbound = Pipe.open();

      int streamID = muxer.registerChannels(outbound.source(), inbound.sink());

      inbound.source().configureBlocking(true);
      outbound.sink().configureBlocking(true);

      DataInputStream in =
          new DataInputStream(new BufferedInputStream(Channels
              .newInputStream(inbound.source())));
      DataOutputStream out =
          new DataOutputStream(new BufferedOutputStream(Channels
              .newOutputStream(outbound.sink())));

      return new Stream(muxer, streamID, in, out);
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }

  public void shutdown() {
    muxer.shutdown();
  }
}
