package fabric.store;

import static fabric.common.Logging.STORE_REQUEST_LOGGER;
import static fabric.common.ONumConstants.STORE_PRINCIPAL;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;

import fabric.common.ConfigProperties;
import fabric.common.Crypto;
import fabric.common.KeyMaterial;
import fabric.common.Logging;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.SysUtil;
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
import fabric.common.util.LongSet;
import fabric.common.util.Oid;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.lang.security.NodePrincipal;
import fabric.lang.security.Principal;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.AllocateMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.ContractExtensionMessage;
import fabric.messages.DissemReadMessage;
import fabric.messages.GetCertChainMessage;
import fabric.messages.MakePrincipalMessage;
import fabric.messages.MessageToStoreHandler;
import fabric.messages.PrepareTransactionMessage;
import fabric.messages.ReadMessage;
import fabric.messages.StalenessCheckMessage;
import fabric.messages.WaitForUpdateMessage;
import fabric.store.db.ObjectDB;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;
import fabric.worker.metrics.ExpiryExtension;
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
    this.tm = new TransactionManager(this.os);
    //this.sm = new SimpleSurrogateManager(tm);
    this.sm = new DummySurrogateManager();
  }

  // ////////////////////////////////////////////////////////////////////////////
  // lifecycle //
  // ////////////////////////////////////////////////////////////////////////////

  public void initialize() {
    // Ensure each store's object database has been properly initialized.
    os.ensureInit();
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

      Protocol<RemoteWorker> handshake = new HandshakeComposite<>(authProt,
          new HandshakeUnauthenticated<RemoteWorker>(this.config.name));
      NameService nameService = new TransitionalNameService();

      return new SubServerSocketFactory(config, handshake, nameService,
          PortType.STORE);
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
        "Handling Abort Message from {0} for tid={1}", nameOf(client.principal),
        message.tid.topTid);

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
        "Handling Commit Message from {0} for tid={1}",
        nameOf(client.principal), message.transactionID);

    tm.commitTransaction(client, message.transactionID);
    return new CommitTransactionMessage.Response();
  }

  /**
   * Processes the given PREPARE request.
   */
  @Override
  public PrepareTransactionMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, PrepareTransactionMessage msg)
      throws TransactionPrepareFailedException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling Prepare Message, worker={0}, tid={1}",
        nameOf(client.principal), msg.tid);

    LongKeyMap<Long> longerContracts = prepareTransaction(client.principal,
        msg.tid, msg.serializedCreates, msg.serializedWrites, msg.reads,
        msg.extensions, msg.extensionsTriggered, msg.delayedExtensions);

    long prepareTime = System.currentTimeMillis();

    if (msg.singleStore || msg.readOnly) {
      if (msg.singleStore && prepareTime > msg.expiryToCheck) {
        try {
          tm.abortTransaction(client.principal, msg.tid);
        } catch (AccessException e) {
          // This should never happen.
          throw new InternalError("AccessException on abort but not prepare?");
        }
        throw new TransactionPrepareFailedException(
            new LongKeyHashMap<SerializedObject>(), longerContracts,
            "Single store prepare too late");
      }
      try {
        tm.commitTransaction(client, msg.tid);
      } catch (TransactionCommitFailedException e) {
        // Shouldn't happen.
        throw new InternalError("Single-store commit failed unexpectedly.", e);
      }
    }

    // Use the time we return, at this point we have locked all of the objects
    // for prepare.
    return new PrepareTransactionMessage.Response(prepareTime, longerContracts);
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

    ObjectGroup group = tm.getGroup(client.principal, client.node, msg.onum);
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

    ObjectGlob glob = tm.getGlob(msg.onum, client.node);
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
        NodePrincipal principal = new NodePrincipal._Impl(store)
            .fabric$lang$security$NodePrincipal$(null);
        principal.addDelegatesTo(store.getPrincipal());
        return principal.$getOnum();
      }
    }, true);

    // Create a certificate that binds the requester's key to both the
    // requester's name and the new principal object's OID.
    X509Certificate cert;
    try {
      cert = Crypto.createCertificate(
          SysUtil.getNodeCN(client.node.name, principalOnum), msg.requesterKey,
          name, storeKey);
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

    return new StalenessCheckMessage.Response(
        tm.checkForStaleObjects(client.principal, message.versionsAndExpiries));
  }

  /**
   * Processes the contract extension request.
   */
  @Override
  public ContractExtensionMessage.Response handle(
      RemoteIdentity<RemoteWorker> client,
      final ContractExtensionMessage message) {
    STORE_REQUEST_LOGGER.log(Level.FINER,
        "Handling Contract Extension Message from {0}",
        nameOf(client.principal));

    for (fabric.worker.RemoteStore s : message.updates.keySet()) {
      for (SerializedObject obj : message.updates.get(s)) {
        s.updateCache(obj);
      }
    }

    tm.queueExtensions(message.extensions);
    return new ContractExtensionMessage.Response();
  }

  /**
   * Processes the contract extension request.
   */
  @Override
  public WaitForUpdateMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, WaitForUpdateMessage message)
      throws AccessException {
    STORE_REQUEST_LOGGER.log(Level.FINER,
        "Handling Wait For Update Message from {0}", nameOf(client.principal));

    // TODO
    return new WaitForUpdateMessage.Response(
        tm.waitForUpdates(message.onumsAndVersions));
  }

  private LongKeyMap<Long> prepareTransaction(Principal p, long tid,
      Collection<SerializedObject> serializedCreates,
      Collection<SerializedObject> serializedWrites,
      LongKeyMap<Pair<Integer, Long>> reads,
      Collection<ExpiryExtension> extensions,
      LongKeyMap<Set<Oid>> extensionsTriggered, LongSet delayedExtensions)
      throws TransactionPrepareFailedException {

    PrepareRequest req =
        new PrepareRequest(tid, serializedCreates, serializedWrites, reads,
            extensions, extensionsTriggered, delayedExtensions);

    sm.createSurrogates(req);

    return tm.prepare(p, req);
  }

  private String nameOf(Principal p) {
    return p == null ? "BOTTOM"
        : ("fab://" + p.$getStore().name() + "/" + p.$getOnum());
  }
}
