package fabric.core;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.security.PrivateKey;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.TransactionCommitFailedException;
import fabric.client.TransactionPrepareFailedException;
import fabric.common.*;
import fabric.common.InternalError;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.dissemination.Glob;
import fabric.lang.NodePrincipal;
import fabric.messages.*;

/**
 * This implements FabricThread for performance reasons. It will be calling into
 * the in-process client to perform access control.
 */
public class Worker extends FabricThread.AbstractImpl implements MessageHandler {

  /**
   * The node that we're working for.
   */
  private final Node node;

  /**
   * The client that we're serving.
   */
  private NodePrincipal client;
  private String clientName;
  private boolean clientIsDissem;

  /**
   * The transaction manager for the object core that the client is talking to.
   */
  private TransactionManager transactionManager;

  /**
   * The surrogate creation policy object.
   */
  private SurrogateManager surrogateManager;

  /**
   * The private signing key for the object core that the client is talking to.
   * XXX This is currently the SSL private key.  Should use the NodePrincipal's key instead.
   */
  private PrivateKey privateKey;

  // The socket and associated I/O streams for communicating with the client.
  private Socket socket;
  private SSLSocket sslSocket;
  private DataInputStream in;
  private DataOutputStream out;

  // Bookkeeping information for debugging/monitoring purposes:
  private int numReads;
  int numObjectsSent;
  private int numGlobsSent;
  int numGlobbedObjects;
  int numGlobsCreated;
  private int numPrepares;
  private int numCommits;
  private int numCreates;
  private int numWrites;
  Map<String, Integer> numSendsByType;
  private static final Logger logger = Logger.getLogger("fabric.core.worker");

  /** Associates debugging log messages with pending transactions */
  private LongKeyMap<LogRecord> pendingLogs;

  private class LogRecord {
    public LogRecord(int creates, int writes) {
      this.creates = creates;
      this.writes = writes;
    }

    public int creates;
    public int writes;
  }

  /**
   * Instantiates a new worker thread and starts it running.
   */
  public Worker(Node node) {
    super("Core worker -- initializing");
    this.node = node;
    fabric.client.transaction.TransactionManager.startThread(this);
  }

  /**
   * Initialises this worker to handle the given client and signals this thread
   * to start processing the client's requests. This is invoked by a
   * <code>Node</code> to hand off a client to this worker.
   */
  public synchronized void handle(Socket socket) {
    this.socket = socket;

    // Get the worker thread running.
    notify();
  }

  /**
   * The main execution body of the worker thread. This is a wrapper for
   * <code>run_</code> to ensure that all exceptions are properly handled and
   * that the <code>Node</code> is properly notified when this worker is
   * finished with a client.
   */
  @Override
  public synchronized void run() {
    while (true) {
      Thread.currentThread().setName("Core worker -- idle");

      // Wait for the node to signal this thread (done via a call to handle()).
      try {
        wait();
      } catch (InterruptedException e) {
        continue;
      }

      Thread.currentThread().setName("Core worker -- active");

      reset();

      SSLSocket sslSocket = null;
      try {
        // Get the name of the core that the client is talking to and obtain the
        // corresponding object store.
        DataInput dataIn = new DataInputStream(socket.getInputStream());
        OutputStream out = socket.getOutputStream();
        String coreName = dataIn.readUTF();
        this.transactionManager = node.getTransactionManager(coreName);
        this.surrogateManager = node.getSurrogateManager(coreName);
        this.privateKey = node.getPrivateKey(coreName);
        this.pendingLogs = new LongKeyHashMap<LogRecord>();

        if (this.transactionManager != null) {
          // Indicate that the core exists.
          out.write(1);
          out.flush();

          if (initializeSession(coreName, dataIn)) {
            logger.info("Core " + coreName + " accepted connection");
            if (clientIsDissem) {
              logger.info("Client connected as a dissemination node");
            } else {
              logger.info("Client principal is " + clientName
                  + (client == null ? " (acting as null)" : ""));
            }
            run_();
          } else {
            logger.info("Core " + coreName
                + " rejected connection from client " + "claiming to be "
                + clientName);
          }
        } else {
          // Indicate that the core doesn't exist here.
          out.write(0);
          out.flush();
        }
      } catch (SocketException e) {
        // TODO: this logging/handler should be cleaned up.
        String msg = e.getMessage();
        if ("Connection reset".equals(msg)) {
          logger.info("Connection reset");
          logger.info("(" + client + ")");
        } else logger.log(Level.WARNING, "Connection closing", e);
      } catch (EOFException e) {
        // Client has closed the connection. Nothing to do here.
        logger.warning("Connection closed");
        logger.warning("(" + client + ")");
      } catch (final IOException e) {
        logger.log(Level.WARNING, "Connection closing", e);
      }

      logger.info(numReads + " read requests");
      logger.info(numObjectsSent + " objects sent");
      logger.info(numGlobsSent + " encrypted globs sent");
      logger.info(numGlobsCreated + " encrypted globs created");
      if (numGlobsCreated != 0)
        logger.info("  " + (numGlobbedObjects / numGlobsCreated)
            + " objects per glob");
      logger.info(numPrepares + " prepare requests");
      logger.info(numCommits + " commit requests");
      logger.info(numCreates + " objects created");
      logger.info(numWrites + " objects updated");

      for (Map.Entry<String, Integer> entry : numSendsByType.entrySet()) {
        logger.info("\t" + entry.getValue() + " " + entry.getKey() + " sent");
      }

      // Try to close our connection gracefully.
      try {
        if (out != null) out.flush();

        if (sslSocket != null)
          sslSocket.close();
        else socket.close();
      } catch (IOException e) {
        logger.log(Level.WARNING, "Failed to close connection gracefully", e);
        logger.log(Level.WARNING, "");
      }

      // Signal that this worker is now available.
      if (node.workerDone(this)) break;
    }

    fabric.client.transaction.TransactionManager.getInstance()
        .deregisterThread(this);
  }

  /**
   * Performs session initialization.
   * 
   * @return whether the session was successfully initialized.
   * @throws IOException
   */
  private boolean initializeSession(String coreName, DataInput dataIn)
      throws IOException {
    clientIsDissem = !dataIn.readBoolean();
    if (clientIsDissem) {
      // Connection from dissemination node.
      this.out =
          new DataOutputStream(new BufferedOutputStream(socket
              .getOutputStream()));
      this.out.flush();
      this.in =
          new DataInputStream(
              new BufferedInputStream(socket.getInputStream()));
      this.clientName = null;
      this.client = null;
      return true;
    }

    // Connection from client.
    if (node.opts.useSSL) {
      // Initiate the SSL handshake and initialize the fields.
      SSLSocketFactory sslSocketFactory = node.getSSLSocketFactory(coreName);
      synchronized (sslSocketFactory) {
        sslSocket =
            (SSLSocket) sslSocketFactory.createSocket(socket, null, 0, true);
      }
      sslSocket.setUseClientMode(false);
      sslSocket.setNeedClientAuth(true);
      sslSocket.startHandshake();
      this.out =
          new DataOutputStream(new BufferedOutputStream(sslSocket
              .getOutputStream()));
      this.out.flush();
      this.in =
          new DataInputStream(new BufferedInputStream(sslSocket
              .getInputStream()));
      this.clientName = sslSocket.getSession().getPeerPrincipal().getName();
    } else {
      this.out =
          new DataOutputStream(new BufferedOutputStream(socket
              .getOutputStream()));
      this.out.flush();
      this.in =
          new DataInputStream(
              new BufferedInputStream(socket.getInputStream()));
      this.clientName = in.readUTF();
    }

    // Read in the pointer to the principal object.
    if (in.readBoolean()) {
      String principalCoreName = in.readUTF();
      Core principalCore = Client.getClient().getCore(principalCoreName);
      long principalOnum = in.readLong();
      this.client =
          new NodePrincipal.$Proxy(principalCore, principalOnum);
    } else {
      this.client = null;
    }

    return authenticateClient(this.clientName);
  }

  /**
   * Determines whether the client principal matches the given name.
   */
  private boolean authenticateClient(final String name) {
    // Bypass authentication if we have a null client.
    // This is to allow bootstrapping the client principal.
    // This is safe because everyone acts for null anyway.
    if (client == null) return true;

    return Client.runInTransaction(null, new Client.Code<Boolean>() {
      public Boolean run() {
        try {
          return client.name().equals(name);
        } catch (ClassCastException e) {
          return false;
        } catch (InternalError e) {
          // XXX For ease of debugging, assume that if the client principal
          // XXX doesn't exist, it's about to be created.
          client = null;
          return true;
        }
      }
    });
  }

  /**
   * The execution body of the worker thread.
   */
  private void run_() throws IOException {
    while (true) {
      Message.receive(in, out, this);
    }
  }

  private void reset() {
    // Reset the statistics counters.
    numReads =
        numObjectsSent =
            numGlobsSent =
                numGlobsCreated =
                    numGlobbedObjects =
                        numPrepares = numCommits = numCreates = numWrites = 0;
    numSendsByType = new TreeMap<String, Integer>();
  }

  /**
   * Cleans up all client-specific state to ready this worker for another
   * client. This is invoked by a <code>Node</code> prior to returning this
   * worker to a thread pool.
   */
  protected void cleanup() {
    in = null;
    out = null;
    socket = null;
    transactionManager = null;
  }

  public void handle(AbortTransactionMessage message) throws AccessException,
      ProtocolError {
    if (clientIsDissem) throw new ProtocolError("Message not supported.");

    logger.finer("Handling Abort Message");
    transactionManager.abortTransaction(client, message.tid.topTid);
    logger.fine("Transaction " + message.tid.topTid + " aborted");
  }

  /**
   * Processes the given request for new OIDs.
   */
  public AllocateMessage.Response handle(AllocateMessage msg)
      throws AccessException, ProtocolError {
    if (clientIsDissem) throw new ProtocolError("Message not supported.");

    logger.finer("Handling Allocate Message");
    long[] onums = transactionManager.newOnums(client, msg.num);
    return new AllocateMessage.Response(onums);
  }

  /**
   * Processes the given commit request
   */
  public CommitTransactionMessage.Response handle(
      CommitTransactionMessage message) throws ProtocolError {
    if (clientIsDissem) throw new ProtocolError("Message not supported.");

    logger.finer("Handling Commit Message");
    this.numCommits++;

    try {
      transactionManager.commitTransaction(client, message.transactionID);
      logger.fine("Transaction " + message.transactionID + " committed");

      // updated object tallies
      LogRecord lr = pendingLogs.remove(message.transactionID);
      this.numCreates += lr.creates;
      this.numWrites += lr.writes;

      return new CommitTransactionMessage.Response(true);
    } catch (TransactionCommitFailedException e) {
      return new CommitTransactionMessage.Response(false, e.getMessage());
    }
  }

  /**
   * Processes the given PREPARE request.
   */
  public PrepareTransactionMessage.Response handle(PrepareTransactionMessage msg)
      throws ProtocolError {
    if (clientIsDissem) throw new ProtocolError("Message not supported.");

    logger.finer("Handling Prepare Message");
    this.numPrepares++;

    PrepareRequest req =
        new PrepareRequest(msg.tid, msg.commitTime, msg.serializedCreates,
            msg.serializedWrites, msg.reads);

    surrogateManager.createSurrogates(req);

    try {
      boolean subTransactionCreated = transactionManager.prepare(client, req);

      logger.fine("Transaction " + req.tid + " prepared");
      // Store the size of the transaction for debugging at the end of the
      // session
      // Note: this number does not include surrogates
      pendingLogs.put(req.tid, new LogRecord(msg.serializedCreates.size(),
          msg.serializedWrites.size()));

      return new PrepareTransactionMessage.Response(subTransactionCreated);
    } catch (TransactionPrepareFailedException e) {
      return new PrepareTransactionMessage.Response(e.getMessage(),
          e.versionConflicts);
    }
  }

  /**
   * Processes the given read request.
   */
  public ReadMessage.Response handle(ReadMessage msg) throws AccessException,
      ProtocolError {
    if (clientIsDissem) throw new ProtocolError("Message not supported.");

    logger.finer("Handling Read Message");
    this.numReads++;

    ObjectGroup group =
        transactionManager.getGroup(client, msg.onum, this);
    return new ReadMessage.Response(group);
  }

  /**
   * Processes the given dissemination-read request.
   */
  public DissemReadMessage.Response handle(DissemReadMessage msg)
      throws AccessException {
    logger.finer("Handling DissemRead message");
    this.numReads++;

    Glob glob = transactionManager.getGlob(msg.onum, privateKey, this);
    if (glob != null) numGlobsSent++;

    return new DissemReadMessage.Response(glob);
  }

}
