package fabric.store;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.common.AbstractMessageHandlerThread;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.util.LongKeyMap;
import fabric.dissemination.Glob;
import fabric.messages.*;

public class MessageHandlerThread extends
    AbstractMessageHandlerThread<SessionAttributes, MessageHandlerThread> {

  private static final Logger logger =
      Logger.getLogger("fabric.store.MessageHandler");

  /**
   * A factory for creating MessageHandlerThread instances. This is used by
   * AbstractMessageHandlerThread.Pool.
   */
  static class Factory implements
      AbstractMessageHandlerThread.Factory<MessageHandlerThread> {
    public MessageHandlerThread createMessageHandler(
        Pool<MessageHandlerThread> pool) {
      return new MessageHandlerThread(pool);
    }
  }

  /**
   * Instantiates a new message-handler thread and starts it running.
   */
  private MessageHandlerThread(Pool<MessageHandlerThread> pool) {
    super("Store message handler", pool);

    fabric.worker.transaction.TransactionManager.startThread(this);
  }

  @Override
  protected Logger getLogger() {
    return logger;
  }

  public void handle(AbortTransactionMessage message) throws AccessException,
      ProtocolError {
    if (session.workerIsDissem)
      throw new ProtocolError("Message not supported.");

    logger.finer("Handling Abort Message");
    session.store.tm.abortTransaction(session.workerPrincipal,
        message.tid.topTid);
    logger.fine("Transaction " + message.tid.topTid + " aborted");
  }

  /**
   * Processes the given request for new OIDs.
   */
  public AllocateMessage.Response handle(AllocateMessage msg)
      throws AccessException, ProtocolError {
    if (session.workerIsDissem)
      throw new ProtocolError("Message not supported.");

    logger.finer("Handling Allocate Message");
    long[] onums = session.store.tm.newOnums(session.workerPrincipal, msg.num);
    return new AllocateMessage.Response(onums);
  }

  /**
   * Processes the given commit request
   */
  public CommitTransactionMessage.Response handle(
      CommitTransactionMessage message) throws ProtocolError {
    if (session.workerIsDissem)
      throw new ProtocolError("Message not supported.");

    try {
      commitTransaction(message.transactionID);
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
    if (session.workerIsDissem)
      throw new ProtocolError("Message not supported.");

    logger.finer("Handling Prepare Message, worker="
        + session.workerPrincipalName + ", tid=" + msg.tid);

    try {
      boolean subTransactionCreated =
          prepareTransaction(msg.tid, msg.commitTime, msg.serializedCreates,
              msg.serializedWrites, msg.reads);
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
    if (session.workerIsDissem)
      throw new ProtocolError("Message not supported.");

    logger.finer("Handling Read Message");
    session.recordRead();

    ObjectGroup group =
        session.store.tm.getGroup(session.workerPrincipal, session.remoteNode,
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

    Glob glob = session.store.tm.getGlob(msg.onum, session.remoteNode, this);
    if (glob != null) session.recordGlobSent();

    return new DissemReadMessage.Response(glob);
  }

  /**
   * Processes the given unauthenticated prepare request.
   */
  public UnauthenticatedPrepareTransactionMessage.Response handle(
      UnauthenticatedPrepareTransactionMessage msg) {
    logger.finer("Handling Unauthenticated Prepare Message, worker="
        + session.remoteNode.name + ", tid=" + msg.tid);

    try {
      final Collection<SerializedObject> EMPTY_COLLECTION =
          Collections.emptyList();
      boolean subTransactionCreated =
          prepareTransaction(msg.tid, msg.commitTime, EMPTY_COLLECTION,
              EMPTY_COLLECTION, msg.reads);
      return new UnauthenticatedPrepareTransactionMessage.Response(
          subTransactionCreated);
    } catch (TransactionPrepareFailedException e) {
      return new UnauthenticatedPrepareTransactionMessage.Response(e
          .getMessage(), e.versionConflicts);
    }
  }

  /**
   * @return true iff a subtransaction was created for making Statistics
   *         objects.
   */
  private boolean prepareTransaction(long tid, long commitTime,
      Collection<SerializedObject> serializedCreates,
      Collection<SerializedObject> serializedWrites, LongKeyMap<Integer> reads)
      throws TransactionPrepareFailedException {
    session.recordPrepare();

    PrepareRequest req =
        new PrepareRequest(tid, commitTime, serializedCreates,
            serializedWrites, reads);

    session.store.sm.createSurrogates(req);

    boolean subTransactionCreated =
        session.store.tm.prepare(session.workerPrincipal, req);

    logger.fine("Transaction " + req.tid + " prepared");
    // Store the size of the transaction for debugging at the end of the
    // session
    // Note: this number does not include surrogates
    session.addPendingLog(req.tid, serializedCreates.size(), serializedWrites
        .size());

    return subTransactionCreated;
  }

  /**
   * Processes the given unauthenticated commit request.
   */
  public UnauthenticatedCommitTransactionMessage.Response handle(
      UnauthenticatedCommitTransactionMessage message) {
    logger.finer("Handling Unauthenticated Commit Message, worker="
        + session.remoteNode.name + ", tid=" + message.transactionID);

    try {
      commitTransaction(message.transactionID);
      return new UnauthenticatedCommitTransactionMessage.Response(true);
    } catch (TransactionCommitFailedException e) {
      return new UnauthenticatedCommitTransactionMessage.Response(false, e
          .getMessage());
    }
  }

  private void commitTransaction(long transactionID)
      throws TransactionCommitFailedException {
    session.recordCommitAttempt();

    session.store.tm.commitTransaction(session.remoteNode,
        session.workerPrincipal, transactionID);
    logger.fine("Transaction " + transactionID + " committed");

    session.recordCommitSuccess(transactionID);
  }

  /**
   * Processes the unauthenticated abort request.
   */
  public void handle(UnauthenticatedAbortTransactionMessage message)
      throws AccessException {
    logger.finer("Handling Abort Message");
    session.store.tm.abortTransaction(session.workerPrincipal,
        message.tid.topTid);
    logger.fine("Transaction " + message.tid.topTid + " aborted");
  }
}
