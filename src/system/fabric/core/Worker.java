package fabric.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import fabric.client.TransactionCommitFailedException;
import fabric.client.TransactionPrepareFailedException;
import fabric.common.AccessException;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.core.store.StoreException;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.AllocateMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.Message;
import fabric.messages.PrepareTransactionMessage;
import fabric.messages.ReadMessage;

public class Worker extends Thread {
  
  /** The node that we're working for. */
  private final Node node;

  /** The client that we're serving. */
  private Principal client;

  /** The transaction manager for the object core that the client is talking to. */
  private TransactionManager transactionManager;

  /** The surrogate creation policy object */
  private SurrogateManager surrogateManager;
  
  // The socket and associated I/O streams for communicating with the client.
  private Socket socket;
  private ObjectInputStream in;
  private ObjectOutputStream out;

  // Bookkeeping information for debugging/monitoring purposes:
  private int numReads;
  private int numObjectsSent;
  private int numPrepares;
  private int numCommits;
  private int numCreates;
  private int numWrites;
  private Map<String, Integer> numSendsByType;
  private static final Logger logger = Logger.getLogger("fabric.core.worker");

  /** associate debugging log messages with pending transactions */
  private Map<Integer, LogRecord> pendingLogs;
  
  private class LogRecord {
    public LogRecord(int creates, int writes) {
      this.creates = creates;
      this.writes  = writes;
    }
    public int creates;
    public int writes;
  }
  
  /**
   * Instantiates a new worker thread and starts it running.
   */
  public Worker(Node node) {
    this.node = node;
    start();
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
      // Wait for the node to signal this thread (done via a call to handle()).
      try {
        wait();
      } catch (InterruptedException e) {
        continue;
      }

      SSLSocket sslSocket = null;
      try {
        // Get the name of the core that the client is talking to and obtain the
        // corresponding object store.
        DataInput dataIn = new DataInputStream(socket.getInputStream());
        OutputStream out = socket.getOutputStream();
        String coreName = dataIn.readUTF();
        this.transactionManager = node.getTransactionManager(coreName);
        this.surrogateManager   = node.getSurrogateManager(coreName);
        this.pendingLogs        = new HashMap<Integer, LogRecord>();

        if (this.transactionManager != null) {
          // Indicate that the core exists.
          out.write(1);
          out.flush();

          if (node.opts.useSSL) {
            // Initiate the SSL handshake and initialize the fields.
            SSLSocketFactory sslSocketFactory =
                node.getSSLSocketFactory(coreName);
            synchronized (sslSocketFactory) {
              sslSocket =
                  (SSLSocket) sslSocketFactory.createSocket(socket, null, 0,
                      true);
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
            this.client = sslSocket.getSession().getPeerPrincipal();
          } else {
            this.out =
                new ObjectOutputStream(new BufferedOutputStream(socket
                    .getOutputStream()));
            this.out.flush();
            this.in =
                new ObjectInputStream(new BufferedInputStream(socket
                    .getInputStream()));
            this.client = (Principal) in.readObject();
          }

          logger.info("Accepted connection for " + coreName);
          logger.info("(" + client + ")");

          run_();
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
      } catch (final ClassNotFoundException e) {
        logger.log(Level.WARNING, "Connection closing", e);
      }

      logger.info(numReads       + " read requests");
      logger.info(numObjectsSent + " objects sent");
      logger.info(numPrepares    + " prepare requests");
      logger.info(numCommits     + " commit requests");
      logger.info(numCreates     + " objects created");
      logger.info(numWrites      + " objects updated");
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
      node.workerDone(this);
    }
  }

  /**
   * The execution body of the worker thread.
   */
  private void run_() throws IOException {
    // Reset the statistics counters.
    numReads =
        numObjectsSent = numPrepares = numCommits = numCreates = numWrites = 0;
    numSendsByType = new TreeMap<String, Integer>();

    while (true) {
      Message.receive(in, out, this);
    }
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

  public void handle(AbortTransactionMessage message) {
    logger.finer("Handling Abort Message");
    transactionManager.abortTransaction(client, message.transactionID);
    logger.fine("Transaction " + message.transactionID + " aborted");
  }

  /**
   * Processes the given request for new OIDs.
   */
  public AllocateMessage.Response handle(AllocateMessage msg) throws AccessException {
    logger.finer("Handling Allocate Message");
    try {
      long[] onums = transactionManager.newOIDs(client, msg.num);
      return new AllocateMessage.Response(onums);
    } catch (StoreException e) {
      throw new AccessException();
    }
  }

  /**
   * Processes the given commit request
   */
  public CommitTransactionMessage.Response handle(
      CommitTransactionMessage message) throws TransactionCommitFailedException {
    logger.finer("Handling Commit Message");
    this.numCommits++;
    
    transactionManager.commitTransaction(client, message.transactionID);
    logger.fine("Transaction " + message.transactionID + " committed");

    // updated object tallies
    LogRecord lr = pendingLogs.remove(message.transactionID);
    this.numCreates += lr.creates;
    this.numWrites  += lr.writes;

    return new CommitTransactionMessage.Response();
  }

  /**
   * Processes the given PREPARE request.
   */
  public PrepareTransactionMessage.Response handle(PrepareTransactionMessage msg)
      throws TransactionPrepareFailedException {
    logger.finer("Handling Prepare Message");
    this.numPrepares++;
    
    PrepareRequest req = new PrepareRequest(msg.serializedCreates,
                                            msg.serializedWrites,
                                            msg.reads);

    surrogateManager.createSurrogates(req);
    int transactionID = transactionManager.prepare(client, req);
    
    logger.fine("Transaction " + transactionID + " prepared");
    // Store the size of the transaction for debugging at the end of the session
    // Note: this number does not include surrogates
    pendingLogs.put(transactionID,
          new LogRecord(msg.serializedCreates.size(),
                        msg.serializedWrites.size()));

    return new PrepareTransactionMessage.Response(transactionID);
  }

  /**
   * Processes the given read request.
   */
  public ReadMessage.Response handle(ReadMessage msg) throws AccessException {
    logger.finer("Handling Read Message");
    this.numReads++;

    LongKeyMap<SerializedObject> group = new LongKeyHashMap<SerializedObject>();
    SerializedObject obj = null;
    
    try {
      obj = transactionManager.read(client, msg.onum);
    
      if (obj != null) {
        // Traverse object graph and add more objects to the object group.
        for (long onum : obj.getIntracoreRefs()) {
          SerializedObject related = transactionManager.read(client, onum);
          if (related != null) {
            group.put(onum, related);
            int count = 0;
            if (numSendsByType.containsKey(related.getClassName()))
              count = numSendsByType.get(related.getClassName());
            count++;
            numSendsByType.put(related.getClassName(), count);
          }
        }
  
        this.numObjectsSent += group.size() + 1;
        int count = 0;
        if (numSendsByType.containsKey(obj.getClassName()))
          count = numSendsByType.get(obj.getClassName());
        count++;
        numSendsByType.put(obj.getClassName(), count);
  
        return new ReadMessage.Response(obj, group);
      }
    } catch (StoreException e) {
      throw new AccessException();
    }

    throw new AccessException();
  }
  
}
