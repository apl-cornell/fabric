package fabric.client.remote;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.transaction.TransactionManager;
import fabric.common.FabricThread;
import fabric.common.MessageHandler;
import fabric.lang.Principal;
import fabric.messages.Message;

public class Worker extends FabricThread.AbstractImpl implements MessageHandler {

  /**
   * The RCM that we're working for.
   */
  private final RemoteCallManager rcm;

  /**
   * The remote client that we're serving.
   */
  private Principal remoteClient;
  private String remoteClientName;

  // The socket and associated I/O streams for communicating with the remote
  // client.
  private Socket socket;
  private SSLSocket sslSocket;
  private ObjectInputStream in;
  private ObjectOutputStream out;

  private static final Logger logger =
      Logger.getLogger("fabric.client.remote.Worker");

  /**
   * Instantiates a new worker thread and starts it running.
   */
  public Worker(RemoteCallManager rcm) {
    super("RCM worker");
    this.rcm = rcm;
    TransactionManager.startThread(this);
  }

  /**
   * Initializes this worker to handle the given client and signals this thread
   * to start processing the client's requests. This is invoked by a
   * <code>RemoteCallManager</code> to hand off a client to this worker.
   */
  public synchronized void handle(Socket socket) {
    this.socket = socket;

    // Get the worker thread running.
    notify();
  }

  /**
   * The main execution body of the worker thread. This is a wrapper for
   * <code>run_</code> to ensure that all exceptions are properly handled and
   * that the <code>RemoteCallManager</code> is properly notified when this
   * worker is finished with a client.
   */
  @Override
  public synchronized void run() {
    while (true) {
      // Wait for the remote call manager to signal this thread (done via a call
      // to handle()).
      try {
        wait();
      } catch (InterruptedException e) {
        continue;
      }

      SSLSocket sslSocket = null;
      try {
        // Get the name of the client that the remote client is talking to.
        DataInput dataIn = new DataInputStream(socket.getInputStream());
        OutputStream out = socket.getOutputStream();
        String clientName = dataIn.readUTF();

        // Indicate that the client exists.
        out.write(1);
        out.flush();

        if (initializeSession(clientName, dataIn)) {
          logger.info("Client " + clientName + " accepted connection");
          logger.info("Remote client principal is " + remoteClient);
          run_();
        }
      } catch (SocketException e) {
        String msg = e.getMessage();
        if ("Connection reset".equals(msg)) {
          logger.info("Connection reset");
          logger.info("(" + remoteClient + ")");
        } else {
          logger.log(Level.WARNING, "Connection closing", e);
        }
      } catch (EOFException e) {
        // Remote client has closed the connection. Nothing to do here.
        logger.warning("Connection closed");
        logger.info("(" + remoteClient + ")");
      } catch (IOException e) {
        logger.warning("Connection closing");
        logger.info("(" + remoteClient + ")");
      }

      // Try to close the connection gracefully.
      try {
        if (out != null) out.flush();

        if (sslSocket != null)
          sslSocket.close();
        else socket.close();
      } catch (IOException e) {
        logger.log(Level.WARNING, "Failed to close connection gracefully", e);
        logger.warning("");
      }

      // Signal that this worker is now available.
      if (rcm.workerDone(this)) break;
    }

    TransactionManager.getInstance().deregisterThread(this);
  }

  /**
   * Performs session initialization.
   * 
   * @return whether the session was successfully initialized.
   */
  private boolean initializeSession(String clientName, DataInput dataIn)
      throws IOException {
    Client client = Client.getClient();
    if (client.useSSL) {
      // Initiate the SSL handshake and initialize the fields.
      SSLSocketFactory sslSocketFactory = rcm.getSSLSocketFactory(clientName);
      synchronized (sslSocketFactory) {
        sslSocket =
            (SSLSocket) sslSocketFactory.createSocket(socket, null, 0, true);
      }

      sslSocket.setUseClientMode(false);
      sslSocket.setNeedClientAuth(true);
      sslSocket.startHandshake();

      this.out =
          new ObjectOutputStream(new BufferedOutputStream(sslSocket
              .getOutputStream()));
      this.out.flush();

      this.in =
          new ObjectInputStream(new BufferedInputStream(sslSocket
              .getInputStream()));

      this.remoteClientName =
          sslSocket.getSession().getPeerPrincipal().getName();
    } else {
      this.out =
          new ObjectOutputStream(new BufferedOutputStream(socket
              .getOutputStream()));
      this.out.flush();
      this.in =
          new ObjectInputStream(
              new BufferedInputStream(socket.getInputStream()));
      this.remoteClientName = in.readUTF();
    }

    // Read in the pointer to the principal object.
    String principalCoreName = in.readUTF();
    Core principalCore = Client.getClient().getCore(principalCoreName);
    long principalOnum = in.readLong();
    this.remoteClient =
        new fabric.lang.Principal.$Proxy(principalCore, principalOnum);

    // Authenticate the client.
    return Client.runInTransaction(new Client.Code<Boolean>() {
      public Boolean run() {
        try {
          return remoteClient.name().equals(remoteClientName);
        } catch (RuntimeException e) {
          return false;
        }
      }
    });
  }

  /**
   * The execution body of the worker thread.
   */
  private void run_() throws IOException {
    while (true)
      Message.receive(in, out, this);
  }

  /**
   * Cleans up all client-specific state to ready this worker for another
   * client. This is invoked by a <code>RemoteCallManager</code> prior to
   * returning this worker to a thread pool.
   */
  protected void cleanup() {
    in = null;
    out = null;
    socket = null;
  }
}
