/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

import fabric.common.net.naming.SocketAddress;
import fabric.lang.security.Principal;

/**
 * Client-side multiplexed socket implementation. The API mirrors that of
 * java.net.Socket. This class manages connection state, and provides a
 * front-end API.
 * 
 * @see java.net.Socket
 * @author mdgeorge
 */
public class SubSocket {
  // ////////////////////////////////////////////////////////////////////////////
  // public API //
  // ////////////////////////////////////////////////////////////////////////////

  /** @see SubSocketFactory */
  protected SubSocket(SubSocketFactory factory) {
    this.state = new Unconnected(factory);
  }

  /**
   * Create a connected SubSocket. This is used internally by ServerChannels for
   * accepting incoming streams.
   */
  SubSocket(Channel.Connection conn) {
    this.state = new Connected(conn);
  }

  /** @see java.net.Socket#close() */
  public synchronized final void close() throws IOException {
    state.close();
  }

  /** @see java.net.Socket#connect(SocketAddress) */
  public synchronized final void connect(String name) throws IOException {
    state.connect(name);
  }

  /**
   * Returns an output stream for this SubSocket. Buffering on this output
   * stream should not be necessary.
   * 
   * @see java.net.Socket#getOutputStream()
   */
  public synchronized final BufferedOutputStream getOutputStream()
      throws IOException {
    return state.getOutputStream();
  }

  /**
   * Returns an input stream for this SubSocket. Buffering on this input stream
   * should not be necessary.
   * 
   * @see java.net.Socket#getInputStream()
   */
  public synchronized final InputStream getInputStream() throws IOException {
    return state.getInputStream();
  }

  /**
   * Return the Principal that represents the remote endpoint of the connection
   */
  public synchronized final Principal getPrincipal() throws IOException {
    return state.getPrincipal();
  }

  @Override
  public String toString() {
    return state.toString();
  }

  // ////////////////////////////////////////////////////////////////////////////
  // State design pattern implementation //
  // //
  // connect close //
  // unconnected ---------> connected -------> closed //
  // | | | //
  // +-----------------------+------------------+---------------> error //
  // exception //
  // ////////////////////////////////////////////////////////////////////////////

  private State state;

  /**
   * default implementations of state methods - throws errors or returns default
   * values as appropriate.
   */
  private abstract class State {
    protected Exception cause = null;

    void close() throws IOException {
      throw new IOException("Cannot close socket: " + this, cause);
    }

    void connect(String name) throws IOException {
      throw new IOException("Cannot connect: " + this, cause);
    }

    InputStream getInputStream() throws IOException {
      throw new IOException("Cannot get an input stream: " + this, cause);
    }

    BufferedOutputStream getOutputStream() throws IOException {
      throw new IOException("Cannot get an output stream: " + this, cause);
    }

    Principal getPrincipal() throws IOException {
      throw new IOException(
          "There is no principal associated with the socket: " + this, cause);
    }
  }

  /**
   * implementation of methods in the Unconnected state
   */
  private final class Unconnected extends State {
    private final SubSocketFactory factory;

    @Override
    public String toString() {
      return "unconnected socket";
    }

    @Override
    void connect(String name) throws IOException {
      try {
        Channel.Connection conn = factory.getChannel(name).connect();
        state = new Connected(conn);
      } catch (final Exception exc) {
        IOException wrapped =
            new IOException("failed to connect to \"" + name + "\"", exc);
        state = new ErrorState(wrapped);
        throw wrapped;
      }
    }

    private Unconnected(SubSocketFactory factory) {
      this.factory = factory;
    }
  }

  /**
   * implementation of methods in the Connected(channel) state
   */
  private final class Connected extends State {
    final Channel.Connection conn;

    @Override
    public String toString() {
      return "connected socket (" + conn.toString() + ")";
    }

    @Override
    void close() throws IOException {
      try {
        conn.close();
        state = new Closed();
      } catch (final Exception exc) {
        IOException wrapped =
            new IOException("failed to close connection", exc);
        state = new ErrorState(wrapped);
        throw wrapped;
      }
    }

    @Override
    InputStream getInputStream() {
      return conn.in;
    }

    @Override
    BufferedOutputStream getOutputStream() {
      return conn.out;
    }

    @Override
    Principal getPrincipal() {
      return conn.getPrincipal();
    }

    Connected(Channel.Connection conn) {
      this.conn = conn;
    }
  }

  /**
   * implementation of methods in the Closed state
   */
  private final class Closed extends State {
    @Override
    public String toString() {
      return "socket closed";
    }
  }

  /**
   * implementations of methods in the Error state
   */
  private final class ErrorState extends State {
    @Override
    public String toString() {
      return "socket has received an exception";
    }

    private ErrorState(Exception exc) {
      cause = exc;
    }
  }
}
