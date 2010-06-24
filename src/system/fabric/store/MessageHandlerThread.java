package fabric.store;

import static fabric.common.Logging.STORE_REQUEST_LOGGER;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.Threading.NamedRunnable;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.util.LongKeyMap;
import fabric.dissemination.Glob;
import fabric.lang.security.NodePrincipal;
import fabric.messages.*;
import fabric.messages.ObjectUpdateMessage.Response;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;

public class MessageHandlerThread
     extends NamedRunnable
  implements MessageToStoreHandler {

  private final SessionAttributes session;
  
  /**
   * Instantiates a new message-handler thread and starts it running.
   */
  private MessageHandlerThread() {
    super("Store message handler");

    // TODO
    session = null;
    throw new NotImplementedException();
  }
  
  public SessionAttributes getSession() {
    return this.session;
  }

  public AbortTransactionMessage.Response handle(NodePrincipal p, AbortTransactionMessage message)
  throws AccessException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling Abort Message from {0} for tid={1}",
        session.workerPrincipalName, message.tid.topTid);
    session.store.tm.abortTransaction(session.workerPrincipal,
        message.tid.topTid);
    return new AbortTransactionMessage.Response();
  }

  /**
   * Processes the given request for new OIDs.
   */
  public AllocateMessage.Response handle(NodePrincipal p, AllocateMessage msg)
      throws AccessException, ProtocolError {
    STORE_REQUEST_LOGGER.log(Level.FINER, "Handling Allocate Message from {0}",
        session.workerPrincipalName);
    long[] onums = session.store.tm.newOnums(session.workerPrincipal, msg.num);
    return new AllocateMessage.Response(onums);
  }

  /**
   * Processes the given commit request
   */
  public CommitTransactionMessage.Response handle(
      NodePrincipal p, CommitTransactionMessage message) throws ProtocolError {
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
  public PrepareTransactionMessage.Response handle(NodePrincipal p, PrepareTransactionMessage msg)
      throws ProtocolError {
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
  public ReadMessage.Response handle(NodePrincipal p, ReadMessage msg) throws AccessException,
      ProtocolError {
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
  public DissemReadMessage.Response handle(NodePrincipal p, DissemReadMessage msg)
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
   * Processes the given request for the store's SSL certificate chain.
   */
  public GetCertChainMessage.Response handle(
      NodePrincipal p, GetCertChainMessage msg) {
    STORE_REQUEST_LOGGER.log(Level.FINER,
        "Handling request for SSL cert chain, worker={0}", session.remoteNode);
    return new GetCertChainMessage.Response(
        session.store.certificateChain);
  }

  /**
   * Processes the given request for a new node principal
   */
  public MakePrincipalMessage.Response handle(NodePrincipal p, MakePrincipalMessage msg) {
    // TODO
    throw new NotImplementedException();
    
    // Create the principal
    
    // Create certificate binding requester key to the OID
    
    // construct the cert chain
    
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

  private void commitTransaction(long transactionID)
      throws TransactionCommitFailedException {
    session.recordCommitAttempt();

    session.store.tm.commitTransaction(session.remoteNode,
        session.workerPrincipal, transactionID);

    session.recordCommitSuccess(transactionID);
  }

  @Override
  protected void runImpl() {
    // TODO Auto-generated method stub
    fabric.worker.transaction.TransactionManager.startThread(Thread.currentThread());
    throw new NotImplementedException();
  }

}
