package fabric.client.remote;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import jif.lang.Label;

import fabric.client.*;
import fabric.client.remote.messages.GetPrincipalMessage;
import fabric.client.remote.messages.ReadMessage;
import fabric.client.remote.messages.RemoteCallMessage;
import fabric.client.remote.messages.TakeOwnershipMessage;
import fabric.client.remote.messages.GetPrincipalMessage.Response;
import fabric.client.transaction.Log;
import fabric.client.transaction.TransactionManager;
import fabric.client.transaction.TransactionRegistry;
import fabric.common.AuthorizationUtil;
import fabric.common.FabricThread;
import fabric.common.MessageHandler;
import fabric.common.TransactionID;
import fabric.lang.Principal;
import fabric.lang.Object.$Impl;
import fabric.lang.Object.$Proxy;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.Message;
import fabric.messages.PrepareTransactionMessage;

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
    super("RCM worker -- initializing");
    this.rcm = rcm;
    synchronized (rcm.workers) {
      rcm.workers.add(this);
    }

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
    while (!rcm.shuttingDown) {
      Thread.currentThread().setName("RCM worker -- idle");
      
      // Wait for the remote call manager to signal this thread (done via a call
      // to handle()).
      try {
        wait();
      } catch (InterruptedException e) {
        continue;
      }
      
      Thread.currentThread().setName("RCM worker -- active");

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

    synchronized (rcm.workers) {
      rcm.workers.remove(this);
    }
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

  /**
   * Associates the given log with this worker's transaction manager and
   * synchronizes the log with the given tid.
   */
  private void associateAndSyncLog(Log log, TransactionID tid) {
    TransactionManager tm = getTransactionManager();
    tm.associateLog(log);

    TransactionID commonAncestor = log.getTid().getLowestCommonAncestor(tid);

    // Do the commits that we've missed.
    for (int i = log.getTid().depth; i > commonAncestor.depth; i--)
      tm.commitTransaction();

    // Start new transactions if necessary.
    if (commonAncestor.depth != tid.depth) tm.startTransaction(tid);
  }

  public RemoteCallMessage.Response handle(
      final RemoteCallMessage remoteCallMessage) throws RemoteCallException {
    // We assume that this thread's transaction manager is free (i.e., it's not
    // managing any tranaction's log) at the start of the method and ensure that
    // it will be free at the end of the method.

    // XXX TODO Security checks.

    TransactionID tid = remoteCallMessage.tid;
    if (tid != null) {
      Log log = TransactionRegistry.getOrCreateInnermostLog(tid);
      associateAndSyncLog(log, tid);
      
      TransactionManager tm = TransactionManager.getInstance();

      // Merge in the update map we got.
      tm.getUpdateMap().putAll(remoteCallMessage.updateMap);
    }

    try {
      // Execute the requested method.
      Object result = Client.runInTransaction(new Client.Code<Object>() {
        public Object run() {
          // This is ugly. Wrap all exceptions that can be thrown with a runtime
          // exception and do the actual handling below.
          try {
            // Ensure the receiver and arguments have the right dynamic types.
            fabric.lang.Object receiver =
                remoteCallMessage.receiver.fetch().$getProxy();
            Object[] args = new Object[remoteCallMessage.args.length + 1];
            args[0] = remoteClient;
            for (int i = 0; i < remoteCallMessage.args.length; i++) {
              Object arg = remoteCallMessage.args[i];
              if (arg instanceof fabric.lang.Object) {
                arg = ((fabric.lang.Object) arg).fetch().$getProxy();
              }
              args[i + 1] = arg;
            }

            return remoteCallMessage.getMethod().invoke(receiver, args);
          } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
          } catch (SecurityException e) {
            throw new RuntimeException(e);
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
          } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
          } catch (RuntimeException e) {
            throw new RuntimeException(e);
          }
        }
      });

      // Return the result.
      UpdateMap updateMap = TransactionManager.getInstance().getUpdateMap();
      return new RemoteCallMessage.Response(result, updateMap);
    } catch (RuntimeException e) {
      Throwable cause = e.getCause();
      if (cause instanceof IllegalArgumentException
          || cause instanceof SecurityException
          || cause instanceof IllegalAccessException
          || cause instanceof InvocationTargetException
          || cause instanceof NoSuchMethodException)
        throw new RemoteCallException(cause);

      throw e;
    }
  }

  public void handle(AbortTransactionMessage abortTransactionMessage) {
    // XXX TODO Security checks.
    Log log =
        TransactionRegistry.getInnermostLog(abortTransactionMessage.tid.topTid);
    if (log == null) return;

    TransactionManager tm = getTransactionManager();
    associateAndSyncLog(log, abortTransactionMessage.tid);
    tm.abortTransaction();
  }

  public PrepareTransactionMessage.Response handle(
      PrepareTransactionMessage prepareTransactionMessage) {
    // XXX TODO Security checks.
    Log log =
        TransactionRegistry.getInnermostLog(prepareTransactionMessage.tid);
    if (log == null)
      return new PrepareTransactionMessage.Response("No such transaction");

    TransactionManager tm = getTransactionManager();
    tm.associateLog(log);

    // Commit up to the top level.
    for (int i = 0; i < log.getTid().depth; i++)
      tm.commitTransactionAt(prepareTransactionMessage.commitTime);

    Map<RemoteNode, TransactionPrepareFailedException> failures =
        tm.sendPrepareMessages(prepareTransactionMessage.commitTime);

    if (failures.isEmpty())
      return new PrepareTransactionMessage.Response();
    else
      return new PrepareTransactionMessage.Response("Transaction prepare failed.");
  }

  public CommitTransactionMessage.Response handle(
      CommitTransactionMessage commitTransactionMessage) {
    // XXX TODO Security checks.
    Log log =
        TransactionRegistry
            .getInnermostLog(commitTransactionMessage.transactionID);
    if (log == null)
      return new CommitTransactionMessage.Response(false, "No such transaction");

    TransactionManager tm = getTransactionManager();
    tm.associateLog(log);
    try {
      tm.sendCommitMessagesAndCleanUp();
    } catch (TransactionAtomicityViolationException e) {
      return new CommitTransactionMessage.Response(false);
    }

    return new CommitTransactionMessage.Response(true);
  }

  public ReadMessage.Response handle(ReadMessage readMessage) {
    Log log = TransactionRegistry.getInnermostLog(readMessage.tid.topTid);
    if (log == null) return new ReadMessage.Response(null);

    associateAndSyncLog(log, readMessage.tid);

    $Impl obj = new $Proxy(readMessage.core, readMessage.onum).fetch();

    // Ensure this client owns the object.
    synchronized (obj) {
      if (!obj.$isOwned) {
        return new ReadMessage.Response(null);
      }
    }

    // Ensure that the remote client is allowed to read the object.
    Label label = obj.get$label();
    if (!AuthorizationUtil.isReadPermitted(remoteClient, label.$getCore(),
        label.$getOnum())) return new ReadMessage.Response(null);

    return new ReadMessage.Response(obj);
  }

  public TakeOwnershipMessage.Response handle(
      TakeOwnershipMessage takeOwnershipMessage) {
    Log log =
        TransactionRegistry.getInnermostLog(takeOwnershipMessage.tid.topTid);
    if (log == null) return new TakeOwnershipMessage.Response(false);

    associateAndSyncLog(log, takeOwnershipMessage.tid);

    $Impl obj =
        new $Proxy(takeOwnershipMessage.core, takeOwnershipMessage.onum)
            .fetch();

    // Ensure this client owns the object.
    synchronized (obj) {
      if (!obj.$isOwned) {
        return new TakeOwnershipMessage.Response(false);
      }
    }

    // Ensure that the remote client is allowed to write the object.
    Label label = obj.get$label();
    if (!AuthorizationUtil.isWritePermitted(remoteClient, label.$getCore(),
        label.$getOnum())) return new TakeOwnershipMessage.Response(false);
    
    // Relinquish ownership.
    obj.$isOwned = false;

    return new TakeOwnershipMessage.Response(true);
  }

  public Response handle(GetPrincipalMessage getPrincipalMessage) {
    return new GetPrincipalMessage.Response(Client.getClient().getPrincipal());
  }
}
