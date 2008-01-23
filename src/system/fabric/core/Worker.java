package fabric.core;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import fabric.client.TransactionCommitFailedException;
import fabric.client.TransactionPrepareFailedException;
import fabric.common.AccessError;
import fabric.messages.*;

public class Worker extends Thread {
  /** The node that we're working for. */
  private final Node node;

  /** The client that we're serving. */
  private Principal client;

  /** The transaction manager for the object core that the client is talking to. */
  private TransactionManager transactionManager;

  // The socket and associated I/O streams for communicating with the client.
  private Socket socket;
  private ObjectInputStream in;
  private ObjectOutputStream out;

  private int numReads;
  private int numObjectsSent;
  private int numPrepares;
  private int numCommits;
  private int numCreates;
  private int numWrites;

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

          System.err.println("Accepted connection for " + coreName);
          System.err.println("(" + client + ")");
          System.err.println();

          run_();
        } else {
          // Indicate that the core doesn't exist here.
          out.write(0);
          out.flush();
        }
      } catch (SocketException e) {
        String msg = e.getMessage();
        if ("Connection reset".equals(msg)) {
          System.err.println("Connection reset");
          System.err.println("(" + client + ")");
        } else e.printStackTrace();
      } catch (EOFException e) {
        // Client has closed the connection. Nothing to do here.
        System.err.println("Connection closed");
        System.err.println("(" + client + ")");
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }

      System.err.println(numReads + " read requests");
      System.err.println(numObjectsSent + " objects sent");
      System.err.println(numPrepares + " prepare requests");
      System.err.println(numCommits + " commit requests");
      System.err.println(numCreates + " objects created");
      System.err.println(numWrites + " objects updated");
      System.err.println();

      // Try to close our connection gracefully.
      try {
        if (out != null) out.flush();

        if (sslSocket != null)
          sslSocket.close();
        else socket.close();
      } catch (IOException e) {
        e.printStackTrace();
        System.err.println();
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

  public CommitTransactionMessage.Response handle(
      CommitTransactionMessage message) throws TransactionCommitFailedException {
    this.numCommits++;
    this.numCreates =
        transactionManager.numCreates(client, message.transactionID);
    this.numWrites =
        transactionManager.numWrites(client, message.transactionID);

    transactionManager.commitTransaction(client, message.transactionID);
    return new CommitTransactionMessage.Response();
  }

  /**
   * Processes the given PREPARE request.
   */
  public PrepareTransactionMessage.Response handle(PrepareTransactionMessage msg)
      throws TransactionPrepareFailedException {
    this.numPrepares++;

    int transactionID =
        transactionManager.prepare(client, msg.serializedCreates, msg.reads,
            msg.serializedWrites);
    return new PrepareTransactionMessage.Response(transactionID);
  }

  /**
   * Processes the given read request.
   */
  public ReadMessage.Response handle(ReadMessage msg) throws AccessError {
    this.numReads++;

    Map<Long, SerializedObject> group = new HashMap<Long, SerializedObject>();
    SerializedObject obj = transactionManager.read(client, msg.onum);
    if (obj != null) {
      // Traverse object graph and add more objects to the object group.
      for (long onum : obj.intracoreRefs) {
        SerializedObject related = transactionManager.read(client, onum);
        if (related != null) group.put(onum, related);
      }
      
      this.numObjectsSent += group.size() + 1;

      return new ReadMessage.Response(obj, group);
    }

    throw new AccessError();
  }
}
