package fabric.store;

import static fabric.common.Logging.STORE_REQUEST_LOGGER;
import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;
import static fabric.common.ONumConstants.STORE_PRINCIPAL;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import fabric.common.ConfigProperties;
import fabric.common.Crypto;
import fabric.common.KeyMaterial;
import fabric.common.Logging;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.SemanticWarranty;
import fabric.common.SysUtil;
import fabric.common.VersionWarranty;
import fabric.common.WarrantyGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricGeneralSecurityException;
import fabric.common.exceptions.InternalError;
import fabric.common.net.RemoteIdentity;
import fabric.common.net.SubServerSocket;
import fabric.common.net.SubServerSocketFactory;
import fabric.common.net.handshake.HandshakeAuthenticated;
import fabric.common.net.handshake.HandshakeBogus;
import fabric.common.net.handshake.HandshakeComposite;
import fabric.common.net.handshake.HandshakeUnauthenticated;
import fabric.common.net.handshake.Protocol;
import fabric.common.net.naming.NameService;
import fabric.common.net.naming.NameService.PortType;
import fabric.common.net.naming.TransitionalNameService;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.dissemination.WarrantyGlob;
import fabric.lang.security.NodePrincipal;
import fabric.lang.security.Principal;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.AllocateMessage;
import fabric.messages.ReuseCallMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.DissemReadMessage;
import fabric.messages.GetCertChainMessage;
import fabric.messages.MakePrincipalMessage;
import fabric.messages.MessageToStoreHandler;
import fabric.messages.PrepareTransactionReadsMessage;
import fabric.messages.PrepareTransactionWritesMessage;
import fabric.messages.ReadMessage;
import fabric.messages.StalenessCheckMessage;
import fabric.store.db.ObjectDB;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.WarrantiedCallResult;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;
import fabric.worker.remote.RemoteWorker;

class Store extends MessageToStoreHandler {
  public final Node node;
  public final TransactionManager tm;
  public final SurrogateManager sm;
  public final ObjectDB os;
  public final X509Certificate[] certificateChain;
  public final PublicKey publicKey;
  public final PrivateKey privateKey;
  public final ConfigProperties config;

  private final SubServerSocketFactory socketFactory;

  Store(Node node, String name) {
    super(name);

    //
    // read properties file
    //

    this.config = new ConfigProperties(name);

    KeyMaterial keyset = config.getKeyMaterial();

    this.certificateChain = keyset.getNameChain();
    this.publicKey = keyset.getPublicKey();
    this.privateKey = keyset.getPrivateKey();

    if (null == keyset.getPrincipalChain()) {
      try {
        X509Certificate[] principalChain =
            new X509Certificate[certificateChain.length + 1];
        for (int i = 0; i < certificateChain.length; i++)
          principalChain[i + 1] = certificateChain[i];
        principalChain[0] =
            Crypto.createCertificate(SysUtil.getNodeCN(name, STORE_PRINCIPAL),
                publicKey, name, privateKey);
        keyset.setPrincipalChain(principalChain);
      } catch (GeneralSecurityException e) {
        throw new InternalError(
            "failed to create store's principal certificate", e);
      }
    }

    this.socketFactory = createSocketFactory(keyset);

    this.node = node;
    this.os = loadStore();
    this.tm = new TransactionManager(this.os, privateKey);
    this.sm = new SimpleSurrogateManager(tm);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // lifecycle //
  // ////////////////////////////////////////////////////////////////////////////

  public void initialize() {
    // Ensure each store's object database has been properly initialized.
    os.ensureInit(tm);
  }

  @Override
  protected SubServerSocket createServerSocket() {
    return socketFactory.createServerSocket();
  }

  public void shutdown() {
    try {
      os.close();
    } catch (final IOException exc) { /* do nothing */
    }
  }

  private ObjectDB loadStore() {
    try {
      // construct ObjectDB with class specified by properties file
      final Class<?> osClass = Class.forName(config.backendClass);
      final Constructor<?> osCons =
          osClass.getConstructor(String.class, PrivateKey.class);
      final ObjectDB os =
          (ObjectDB) osCons.newInstance(config.name, this.privateKey);

      return os;
    } catch (Exception exc) {
      throw new InternalError("could not initialize store", exc);
    }
  }

  private SubServerSocketFactory createSocketFactory(KeyMaterial keys) {
    try {
      Protocol<RemoteWorker> authProt;
      if (config.useSSL)
        authProt = new HandshakeAuthenticated<>(keys);
      else authProt = new HandshakeBogus<>(this.config.name, STORE_PRINCIPAL);

      Protocol<RemoteWorker> handshake =
          new HandshakeComposite<>(authProt,
              new HandshakeUnauthenticated<RemoteWorker>(this.config.name));
      NameService nameService = new TransitionalNameService();

      return new SubServerSocketFactory(handshake, nameService, PortType.STORE);
    } catch (final Exception e) {
      throw new InternalError("Failed to initialize store", e);
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // message handlers //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public AbortTransactionMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, AbortTransactionMessage message)
          throws AccessException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling Abort Message from {0} for tid={1}",
        nameOf(client.principal), message.tid.topTid);

    tm.abortTransaction(client.principal, message.tid.topTid);
    return new AbortTransactionMessage.Response();
  }

  /**
   * Processes the given request for new OIDs.
   */
  @Override
  public AllocateMessage.Response handle(RemoteIdentity<RemoteWorker> client,
      AllocateMessage msg) throws AccessException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling Allocate Message from {0}", nameOf(client.principal));

    long[] onums = tm.newOnums(client.principal, msg.num);
    return new AllocateMessage.Response(onums);
  }

  /**
   * Processes the given commit request
   */
  @Override
  public CommitTransactionMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, CommitTransactionMessage message)
          throws TransactionCommitFailedException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling Commit Message from {0} for tid={1}, commitTime={2}",
        nameOf(client.principal), message.transactionID, message.commitTime);
    if (!message.readOnly)
      tm.commitTransaction(client, message.transactionID, message.commitTime);
    return new CommitTransactionMessage.Response();
  }

  /**
   * Processes the given PREPARE_WRITES request.
   */
  @Override
  public PrepareTransactionWritesMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, PrepareTransactionWritesMessage msg)
      throws TransactionPrepareFailedException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling Prepare-Writes Message, worker={0}, tid={1}",
        nameOf(client.principal), msg.tid);

    Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
        "Preparing {0} semantic warranty requests", msg.requests.size());

    long commitTime = prepareTransactionWrites(client, msg.tid,
        msg.serializedCreates, msg.serializedWrites, msg.requests);

    Map<CallInstance, SemanticWarranty> replies =
      prepareTransactionRequests(client.principal, msg.tid, msg.requests);

    Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
        "Returning {0} request replies", replies.size());

    PrepareWritesResult writeResult = new PrepareWritesResult(commitTime, replies);
    PrepareTransactionWritesMessage.Response res =
      new PrepareTransactionWritesMessage.Response(writeResult);
    STORE_REQUEST_LOGGER.finest("FINISHED PREPARING WRITES FOR TID " +
        Long.toHexString(msg.tid));
    return res;
  }

  /**
   * Processes the given PREPARE_READS request.
   */
  @Override
  public PrepareTransactionReadsMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, PrepareTransactionReadsMessage msg)
      throws TransactionPrepareFailedException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling Prepare Message, worker={0}, tid={1}",
        nameOf(client.principal), msg.tid);

    TransactionPrepareFailedException error = null;

    if (count++ % 10000 == 0) {
      Logging.HOTOS_LOGGER.info("Read prepare set size is " + msg.reads.size());
    }

    LongKeyMap<VersionWarranty> newWarranties = new LongKeyHashMap<>();
    try {
      newWarranties.putAll(prepareTransactionReads(client, msg.tid, msg.reads,
                            msg.commitTime));
    } catch (TransactionPrepareFailedException e) {
      error = e;
    }

    Map<CallInstance, WarrantiedCallResult> newSemWarranties = new HashMap<>();
    try {
      newSemWarranties.putAll(prepareTransactionCalls(client.principal, msg.tid,
            msg.calls, msg.commitTime));
    } catch (TransactionPrepareFailedException e) {
      if (error != null) {
        List<String> msgs = new LinkedList<>();
        if (error.messages != null) msgs.addAll(error.messages);
        if (e.messages != null) msgs.addAll(e.messages);
        throw new TransactionPrepareFailedException(error.versionConflicts,
            e.callConflictUpdates, msgs);
      }
      throw e;
    }
    if (error != null) throw error;

    if (msg.readOnly) {
      // Optimization for read-only transaction: commit the transaction right
      // away.

      // Nothing to commit -- warranties have already been extended during the
      // prepare phase.
    }

    return new PrepareTransactionReadsMessage.Response(newWarranties,
        newSemWarranties);
  }

  private static int count = 0;

  /**
   * Processes the given call request.
   */
  @Override
  public ReuseCallMessage.Response handle(RemoteIdentity<RemoteWorker> client,
      ReuseCallMessage msg) throws AccessException {
    Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
        "Handling Reuse Call Message from {0} for call {1}",
        nameOf(client.principal), msg.call);

    WarrantiedCallResult result = tm.getCall(client.principal, msg.call);
    return new ReuseCallMessage.Response(result);
  }

  /**
   * Processes the given read request.
   */
  @Override
  public ReadMessage.Response handle(RemoteIdentity<RemoteWorker> client,
      ReadMessage msg) throws AccessException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling Read Message from {0}, onum={1}", nameOf(client.principal),
        msg.onum);

    Pair<ObjectGroup, WarrantyGroup> group =
        tm.getGroup(client.principal, client.node, msg.onum);
    return new ReadMessage.Response(group);
  }

  /**
   * Processes the given dissemination-read request.
   */
  @Override
  public DissemReadMessage.Response handle(RemoteIdentity<RemoteWorker> client,
      DissemReadMessage msg) throws AccessException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling DissemRead message from {0}, onum={1}",
        nameOf(client.principal), msg.onum);

    Pair<ObjectGlob, WarrantyGlob> glob = tm.getGlobs(msg.onum, client.node);
    return new DissemReadMessage.Response(glob);
  }

  /**
   * Processes the given request for the store's SSL certificate chain.
   */
  @Override
  public GetCertChainMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, GetCertChainMessage msg) {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling request for SSL cert chain, worker={0}",
        nameOf(client.principal));

    return new GetCertChainMessage.Response(certificateChain);
  }

  /**
   * Processes the given request for a new node principal
   */
  @Override
  public MakePrincipalMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, MakePrincipalMessage msg)
          throws FabricGeneralSecurityException {
    // Note: p should always be null.

    // Get the store's node object and its signing key.
    final fabric.worker.Store store = Worker.getWorker().getStore(name);
    final PrivateKey storeKey = privateKey;

    // Create a principal object on the store and get the resulting object's
    // onum.
    long principalOnum = Worker.runInTopLevelTransaction(new Code<Long>() {
      @Override
      public Long run() {
        NodePrincipal principal =
            new NodePrincipal._Impl(store)
        .fabric$lang$security$NodePrincipal$(null);
        principal.addDelegatesTo(store.getPrincipal());
        return principal.$getOnum();
      }
    }, true);

    // Create a certificate that binds the requester's key to both the
    // requester's name and the new principal object's OID.
    X509Certificate cert;
    try {
      cert =
          Crypto.createCertificate(
              SysUtil.getNodeCN(client.node.name, principalOnum),
              msg.requesterKey, name, storeKey);
    } catch (GeneralSecurityException e) {
      throw new FabricGeneralSecurityException(e);
    }

    return new MakePrincipalMessage.Response(principalOnum, cert,
        certificateChain);
  }

  /**
   * Processes the given staleness check request.
   */
  @Override
  public StalenessCheckMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, StalenessCheckMessage message)
          throws AccessException {
    STORE_REQUEST_LOGGER.log(Level.FINER,
        "Handling Staleness Check Message from {0}", nameOf(client.principal));

    return new StalenessCheckMessage.Response(tm.checkForStaleObjects(client,
        message.versions));
  }

  /**
   * @return the transaction's minimum commit time.
   */
  private long prepareTransactionWrites(RemoteIdentity<RemoteWorker> r, long tid,
      Collection<SerializedObject> serializedCreates,
      Collection<SerializedObject> serializedWrites,
      Collection<SemanticWarrantyRequest> calls) throws
    TransactionPrepareFailedException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling PrepareWrites Message from {0}, tid={1}", nameOf(r.principal),
        tid);

    PrepareWritesRequest req =
        new PrepareWritesRequest(tid, serializedCreates, serializedWrites, calls);

    sm.createSurrogates(req);

    return tm.prepareWrites(r, req);
  }

  private LongKeyMap<VersionWarranty> prepareTransactionReads(
      RemoteIdentity<RemoteWorker> client, long tid, LongKeyMap<Integer> reads,
      long commitTime) throws TransactionPrepareFailedException {
    return tm.prepareReads(client, tid, reads, commitTime);
  }

  private Map<CallInstance, WarrantiedCallResult> prepareTransactionCalls(Principal
      p, long tid, Map<CallInstance, WarrantiedCallResult> calls, long
      commitTime) throws TransactionPrepareFailedException {
    return tm.prepareCalls(p, tid, calls, commitTime);
  }

  /**
   * Handles the <code>SemanticWarrantyRequest</code> for a transaction.
   */
  private Map<CallInstance, SemanticWarranty> prepareTransactionRequests(Principal p,
      long tid, Set<SemanticWarrantyRequest> requests) throws
    TransactionPrepareFailedException {
      /* throws TransactionPrepareFailedException { */
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling PrepareRequests Message from {0}, tid={1}", nameOf(p), tid);
    return tm.prepareRequests(p, tid, requests);
  }

  private String nameOf(Principal p) {
    return p == null ? "BOTTOM" : ("fab://" + p.$getStore().name() + "/" + p
        .$getOnum());
  }
}
