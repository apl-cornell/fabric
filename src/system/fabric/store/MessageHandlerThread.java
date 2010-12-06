package fabric.store;

import static fabric.common.Logging.STORE_REQUEST_LOGGER;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

import fabric.common.*;
import fabric.common.Threading.NamedRunnable;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.util.LongKeyMap;
import fabric.dissemination.Glob;
import fabric.lang.security.NodePrincipal;
import fabric.messages.*;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;

public class MessageHandlerThread
     extends NamedRunnable
  implements MessageToStoreHandler {

  private final SessionAttributes session;
  private final TransactionManager tm;
  private Certificate[] certificateChain;
  
  /**
   * Instantiates a new message-handler thread and starts it running.
   */
  private MessageHandlerThread() {
    super("Store message handler");

    // TODO
    session = null;
    throw new NotImplementedException();
  }
  
  public AbortTransactionMessage.Response handle(NodePrincipal p, AbortTransactionMessage message)
  throws AccessException {
    
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling Abort Message from {0} for tid={1}",
                p.name(), message.tid.topTid);
    
    tm.abortTransaction(p, message.tid.topTid);
    return new AbortTransactionMessage.Response();
  }

  /**
   * Processes the given request for new OIDs.
   */
  public AllocateMessage.Response handle(NodePrincipal p, AllocateMessage msg)
  throws AccessException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling Allocate Message from {0}",
                p.name());
    
    long[] onums = tm.newOnums(p, msg.num);
    return new AllocateMessage.Response(onums);
  }

  /**
   * Processes the given commit request
   */
  public CommitTransactionMessage.Response handle(NodePrincipal p,
                                                  CommitTransactionMessage message)
  throws TransactionCommitFailedException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling Commit Message from {0} for tid={1}",
                session.workerPrincipalName, message.transactionID);
    tm.commitTransaction(session.remoteNode, p, message.transactionID);
    return new CommitTransactionMessage.Response();
  }

  /**
   * Processes the given PREPARE request.
   */
  public PrepareTransactionMessage.Response handle(NodePrincipal p,
                                                   PrepareTransactionMessage msg)
  throws TransactionPrepareFailedException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling Prepare Message, worker={0}, tid={1}",
                p.name(), msg.tid);
    boolean subTransactionCreated = prepareTransaction(msg.tid,
                                                       msg.commitTime,
                                                       msg.serializedCreates,
                                                       msg.serializedWrites,
                                                       msg.reads);
    return new PrepareTransactionMessage.Response(subTransactionCreated);
  }

  /**
   * Processes the given read request.
   */
  public ReadMessage.Response handle(NodePrincipal p, ReadMessage msg)
  throws AccessException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling Read Message from {0}, onum={1}",
                p.name(), msg.onum);

    ObjectGroup group = tm.getGroup(p, session.remoteNode, msg.onum, this);
    return new ReadMessage.Response(group);
  }

  /**
   * Processes the given dissemination-read request.
   */
  public DissemReadMessage.Response handle(NodePrincipal p, DissemReadMessage msg)
  throws AccessException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling DissemRead message from {0}, onum={1}",
                p.name(), msg.onum);

    Glob glob = tm.getGlob(msg.onum, session.remoteNode, this);

    return new DissemReadMessage.Response(glob);
  }

  /**
   * Processes the given request for the store's SSL certificate chain.
   */
  public GetCertChainMessage.Response handle(NodePrincipal p,
                                             GetCertChainMessage msg) {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling request for SSL cert chain, worker={0}",
                session.remoteNode);
    return new GetCertChainMessage.Response(certificateChain);
  }

  /**
   * Processes the given request for a new node principal
   */
  public MakePrincipalMessage.Response handle(NodePrincipal p,
      MakePrincipalMessage msg) throws GeneralSecurityException {
    // Note: p should always be null.
    
    // Get the store's node object and its signing key. 
    final String storeName = session.store.name;
    final fabric.worker.Store store = Worker.getWorker().getStore(storeName);
    final PrivateKey storeKey = session.store.privateKey;
    
    // Create a principal object on the store and get the resulting object's
    // onum.
    long principalOnum = Worker.runInTransaction(null, new Code<Long>() {
      public Long run() {
        NodePrincipal principal = new NodePrincipal._Impl(store, null, null);
        principal.addDelegatesTo(store.getPrincipal());
        return principal.$getOnum();
      }
    });
    
    // Create a certificate that binds the requester's key to the new principal
    // object's OID.
    X509Certificate cert =
        Crypto.createCertificate(Long.toString(principalOnum),
            msg.requesterKey, storeName, storeKey);
    
    return new MakePrincipalMessage.Response(principalOnum, cert);
  }

  /**
   * Processes the given staleness check request.
   */
  public StalenessCheckMessage.Response handle(NodePrincipal p,
      StalenessCheckMessage message) throws AccessException {
    STORE_REQUEST_LOGGER.log(Level.FINER,
	"Handling Staleness Check Message from {0}", p);
    return new StalenessCheckMessage.Response(session.store.tm.checkForStaleObjects(session.workerPrincipal, message.versions));
  }
  
  /**
   * @return true iff a subtransaction was created for making Statistics
   *         objects.
   */
  private boolean prepareTransaction(
      long tid, long commitTime,
      Collection<SerializedObject> serializedCreates,
      Collection<SerializedObject> serializedWrites, LongKeyMap<Integer> reads)
      throws TransactionPrepareFailedException {

    PrepareRequest req =
        new PrepareRequest(tid, commitTime, serializedCreates,
            serializedWrites, reads);

    session.store.sm.createSurrogates(req);

    boolean subTransactionCreated =
        session.store.tm.prepare(session.workerPrincipal, req);

    return subTransactionCreated;
  }
  
  @Override
  protected void runImpl() {
    // TODO Auto-generated method stub
    fabric.worker.transaction.TransactionManager.startThread(Thread.currentThread());
    throw new NotImplementedException();
  }

}
