package fabric.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import fabric.common.Threading;
import fabric.common.exceptions.FabricException;
import fabric.common.net.SubServerSocket;
import fabric.common.net.SubSocket;

/**
 * Abstracts a server loop that listens for and processes messages from the
 * network.
 */
public abstract class AbstractMessageServer implements Runnable, MessageHandler {

  public final String name;
  public final Logger logger;

  public AbstractMessageServer(String name, Logger logger) {
    this.name = name;
    this.logger = logger;
  }

  /**
   * Creates and returns a SubServerSocket for listening to requests.
   */
  protected abstract SubServerSocket createServerSocket();

  /**
   * The main execution body of a message processor node.
   */
  @Override
  public final void run() {
    SubServerSocket server = createServerSocket();

    try {
      // Start listening.
      server.bind(name);

      // The main server loop.
      while (true) {
        // Accept a connection and handle it.
        final SubSocket connection = server.accept();

        Threading.getPool()
        .submit(
            new Threading.NamedRunnable(
                "Fabric network message handler thread") {
              @Override
              protected void runImpl() {
                try {
                  // Handle the connection.
                  DataInputStream in =
                      new DataInputStream(connection.getInputStream());
                  DataOutputStream out =
                      new DataOutputStream(connection.getOutputStream());

                  while (true) {
                    Message<?, ?> message = Message.receive(in);
                    try {
                      Message.Response response =
                          message.dispatch(connection.getPrincipal(),
                              AbstractMessageServer.this);
                      message.respond(out, response);
                    } catch (FabricException e) {
                      message.respond(out, e);
                    }

                    out.flush();
                  }
                } catch (IOException e) {
                  try {
                    connection.close();
                  } catch (IOException e1) {
                  }
                  logger.log(Level.WARNING,
                      "Network error while handling request", e);
                }
              }
            });
      }
    } catch (final IOException e) {
      logger.log(Level.WARNING, name + " (" + getClass().getSimpleName()
          + ") suffered communications failure, shutting down", e);
    }
  }
}
