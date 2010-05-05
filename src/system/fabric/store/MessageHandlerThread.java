package fabric.store;

import static fabric.common.Logging.STORE_REQUEST_LOGGER;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;

import fabric.common.AbstractMessageHandlerThread;
import fabric.common.Logging;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.util.LongKeyMap;
import fabric.dissemination.Glob;
import fabric.messages.*;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;

public class MessageHandlerThread extends
    AbstractMessageHandlerThread<SessionAttributes, MessageHandlerThread> {

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

  public void handle(AbortTransactionMessage message) throws AccessException,
      ProtocolError {
    if (session.workerIsDissem)
      throw new ProtocolError("Message not supported.");

    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling Abort Message from {0} for tid={1}",
        session.workerPrincipalName, message.tid.topTid);
    session.store.tm.abortTransaction(session.workerPrincipal,
        message.tid.topTid);
  }

  /**
   * Processes the given request for new OIDs.
   */
  public AllocateMessage.Response handle(AllocateMessage msg)
      throws AccessException, ProtocolError {
    if (session.workerIsDissem)
      throw new ProtocolError("Message not supported.");

    STORE_REQUEST_LOGGER.log(Level.FINER, "Handling Allocate Message from {0}",
        session.workerPrincipalName);
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

    STORE_REQUEST_LOGGER.log(Level.FINER, "Handling Prepare Message, worker="
        + session.workerPrincipalName + ", tid={0}", msg.tid);

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

    STORE_REQUEST_LOGGER.log(Level.FINER,
        "Handling Read Message from {0}, onum=" + msg.onum,
        session.workerPrincipalName);
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
    STORE_REQUEST_LOGGER.log(Level.FINER,
        "Handling DissemRead message from {0}, onum=" + msg.onum,
        session.remoteNode);
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
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling Unauthenticated Prepare Message from {0}, tid={1}",
        session.remoteNode, msg.tid);

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
   * Processes the given request for the store's SSL certificate chain.
   */
  public GetCertificateChainMessage.Response handle(
      GetCertificateChainMessage msg) {
    STORE_REQUEST_LOGGER.log(Level.FINER,
        "Handling request for SSL cert chain, worker={0}", session.remoteNode);
    return new GetCertificateChainMessage.Response(
        session.store.certificateChain);
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
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling Unauthenticated Commit Message from {0}, tid={1}",
        session.remoteNode, message.transactionID);

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

    session.recordCommitSuccess(transactionID);
  }

  /**
   * Processes the unauthenticated abort request.
   */
  public void handle(UnauthenticatedAbortTransactionMessage message)
      throws AccessException {
    STORE_REQUEST_LOGGER.log(Level.FINER, "Handling Abort Message from {0}",
        session.remoteNode);
    session.store.tm.abortTransaction(session.workerPrincipal,
        message.tid.topTid);
  }
}
