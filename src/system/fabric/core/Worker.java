package fabric.core;

import java.util.logging.Logger;

import fabric.client.TransactionCommitFailedException;
import fabric.client.TransactionPrepareFailedException;
import fabric.common.AbstractWorkerThread;
import fabric.common.ObjectGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.ProtocolError;
import fabric.dissemination.Glob;
import fabric.messages.*;

public class Worker extends AbstractWorkerThread<SessionAttributes, Worker> {

  private static final Logger logger = Logger.getLogger("fabric.core.worker");

  /**
   * A factory for creating Worker instances. This is used by WorkerThread.Pool.
   */
  static class Factory implements AbstractWorkerThread.Factory<Worker> {
    public Worker createWorker(Pool<Worker> pool) {
      return new Worker(pool);
    }
  }

  /**
   * Instantiates a new worker thread and starts it running.
   */
  private Worker(Pool<Worker> pool) {
    super("Core worker", pool);

    fabric.client.transaction.TransactionManager.startThread(this);
  }

  @Override
  protected Logger getLogger() {
    return logger;
  }

  public void handle(AbortTransactionMessage message) throws AccessException,
      ProtocolError {
    if (session.clientIsDissem)
      throw new ProtocolError("Message not supported.");

    logger.finer("Handling Abort Message");
    session.core.tm.abortTransaction(session.clientPrincipal,
        message.tid.topTid);
    logger.fine("Transaction " + message.tid.topTid + " aborted");
  }

  /**
   * Processes the given request for new OIDs.
   */
  public AllocateMessage.Response handle(AllocateMessage msg)
      throws AccessException, ProtocolError {
    if (session.clientIsDissem)
      throw new ProtocolError("Message not supported.");

    logger.finer("Handling Allocate Message");
    long[] onums = session.core.tm.newOnums(session.clientPrincipal, msg.num);
    return new AllocateMessage.Response(onums);
  }

  /**
   * Processes the given commit request
   */
  public CommitTransactionMessage.Response handle(
      CommitTransactionMessage message) throws ProtocolError {
    if (session.clientIsDissem)
      throw new ProtocolError("Message not supported.");

    logger.finer("Handling Commit Message, client="
        + session.clientPrincipalName + ", tid=" + message.transactionID);
    session.recordCommitAttempt();

    try {
      session.core.tm.commitTransaction(session.remoteNode,
          session.clientPrincipal, message.transactionID);
      logger.fine("Transaction " + message.transactionID + " committed");

      session.recordCommitSuccess(message.transactionID);

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
    if (session.clientIsDissem)
      throw new ProtocolError("Message not supported.");

    logger.finer("Handling Prepare Message, client="
        + session.clientPrincipalName + ", tid=" + msg.tid);
    session.recordPrepare();

    PrepareRequest req =
        new PrepareRequest(msg.tid, msg.commitTime, msg.serializedCreates,
            msg.serializedWrites, msg.reads);

    session.core.sm.createSurrogates(req);

    try {
      boolean subTransactionCreated =
          session.core.tm.prepare(session.clientPrincipal, req);

      logger.fine("Transaction " + req.tid + " prepared");
      // Store the size of the transaction for debugging at the end of the
      // session
      // Note: this number does not include surrogates
      session.addPendingLog(req.tid, msg.serializedCreates.size(),
          msg.serializedWrites.size());

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
    if (session.clientIsDissem)
      throw new ProtocolError("Message not supported.");

    logger.finer("Handling Read Message");
    session.recordRead();

    ObjectGroup group =
        session.core.tm.getGroup(session.clientPrincipal, session.remoteNode,
            msg.onum, this);
    return new ReadMessage.Response(group);
  }

  /**
   * Processes the given dissemination-read request.
   */
  public DissemReadMessage.Response handle(DissemReadMessage msg)
      throws AccessException {
    logger.finer("Handling DissemRead message");
    session.recordRead();

    Glob glob = session.core.tm.getGlob(msg.onum, session.remoteNode, this);
    if (glob != null) session.recordGlobSent();

    return new DissemReadMessage.Response(glob);
  }
}
