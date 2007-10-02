package fabric.core;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLSocket;

import fabric.client.TransactionCommitFailedException;
import fabric.client.TransactionPrepareFailedException;
import fabric.common.AccessError;
import fabric.common.NoSuchCoreError;
import fabric.common.ProtocolError;
import fabric.messages.*;

public class Worker extends Thread {
  /** The node that we're working for. */
  private final Node node;

  /** The client that we're serving. */
  private Principal client;

  /** The transaction manager for the object core that the client is talking to. */
  private TransactionManager transactionManager;

  // The socket and associated I/O streams for communicating with the client.
  private SSLSocket socket;
  private ObjectInputStream in;
  private ObjectOutputStream out;

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
   * 
   * @throws IOException
   *           if extracting the I/O streams from the client socket fails.
   */
  public synchronized void handle(SSLSocket socket) throws IOException {
    this.socket = socket;
    this.in = new ObjectInputStream(socket.getInputStream());
    this.out = new ObjectOutputStream(socket.getOutputStream());
    this.out.flush();
    this.client = socket.getSession().getPeerPrincipal();

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

      try {
        run_();
      } catch (SocketException e) {
        String msg = e.getMessage();
        if ("Connection reset".equals(msg)) {
          System.err.println("Connection reset");
          System.err.println("(" + client + ")");
        } else e.printStackTrace();
        
        System.err.println();
      } catch (EOFException e) {
        // Client has closed the connection. Nothing to do here.
      } catch (IOException e) {
        e.printStackTrace();
      }

      try {
        out.flush();
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      node.workerDone(this);
    }
  }

  /**
   * The execution body of the worker thread.
   */
  private void run_() throws IOException {
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
    transactionManager.abortTransaction(client, message.transactionID);
  }

  /**
   * Processes the given request for new OIDs.
   */
  public AllocateMessage.Response handle(AllocateMessage msg) {
    long[] onums = transactionManager.newOIDs(client, msg.num);
    return new AllocateMessage.Response(onums);
  }

  /**
   * Processes the given begin-transaction request.
   */
  public BeginTransactionMessage.Response handle(BeginTransactionMessage message) {
    return new BeginTransactionMessage.Response(transactionManager
        .newTransaction(client));
  }

  public CommitTransactionMessage.Response handle(
      CommitTransactionMessage message) throws TransactionCommitFailedException {
    transactionManager.commitTransaction(client, message.transactionID);
    return new CommitTransactionMessage.Response();
  }

  /**
   * Processes the given PREPARE request.
   */
  public PrepareTransactionMessage.Response handle(PrepareTransactionMessage msg)
      throws TransactionPrepareFailedException {
    transactionManager.prepare(client, msg.transactionID, msg.toCreate,
        msg.reads, msg.writes);
    return new PrepareTransactionMessage.Response();
  }

  /**
   * Processes the given read request.
   */
  public ReadMessage.Response handle(ReadMessage msg) throws AccessError {
    Map<Long, SerializedObject> group = new HashMap<Long, SerializedObject>();
    SerializedObject obj = transactionManager.read(client, msg.onum);
    if (obj != null) {
      // TODO traverse object graph and add more objects to the object group.

      return new ReadMessage.Response(obj, group);
    }

    throw new AccessError();
  }

  public ConnectMessage.Response handle(ConnectMessage message)
      throws ProtocolError, NoSuchCoreError {
    // Client identifies the 48-bit ID for core it's interested in. Get the
    // corresponding object store.

    if (this.transactionManager != null) {
      throw new ProtocolError("Already connected");
    }

    this.transactionManager = node.getTransactionManager(message.coreID);

    // Validate the information given thus far.
    if (this.transactionManager == null) throw new NoSuchCoreError();

    // Everything looks good. Tell the client that we're ready to serve.
    return new ConnectMessage.Response();
  }
}
