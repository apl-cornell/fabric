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
package fabric.worker.remote;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import fabric.worker.Worker;
import fabric.common.FabricThread;

/**
 * A thread that handles incoming requests from other workers.
 */
public class RemoteCallManager extends FabricThread.AbstractImpl {

  volatile boolean shuttingDown;

  private final ConnectionHandler connectionHandler;

  public RemoteCallManager() {
    super("remote call manager");
    this.shuttingDown = false;
    this.connectionHandler = new ConnectionHandler(this);
  }

  @Override
  public void run() {
    try {
      // Start listening.
      ServerSocketChannel server = ServerSocketChannel.open();
      server.configureBlocking(true);
      server.socket().bind(new InetSocketAddress(Worker.getWorker().port));

      // The main server loop.
      while (!shuttingDown) {
        try {
          // Accept a connection and give it to the connection handler.
          SocketChannel connection = server.accept();
          connectionHandler.handle(connection);
        } catch (ClosedByInterruptException e) {
          continue;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void shutdown() {
    shuttingDown = true;
    connectionHandler.shutdown();
    interrupt();
  }
}
