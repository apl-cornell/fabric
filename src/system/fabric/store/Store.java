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
import java.util.logging.Level;

import fabric.common.ConfigProperties;
import fabric.common.Crypto;
import fabric.common.KeyMaterial;
import fabric.common.Logging;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricGeneralSecurityException;
import fabric.common.exceptions.InternalError;
import fabric.common.net.SubServerSocket;
import fabric.common.net.SubServerSocketFactory;
import fabric.common.net.handshake.HandshakeAuthenticated;
import fabric.common.net.handshake.HandshakeBogus;
import fabric.common.net.handshake.HandshakeComposite;
import fabric.common.net.handshake.HandshakeUnauthenticated;
import fabric.common.net.handshake.Protocol;
import fabric.common.net.naming.DefaultNameService;
import fabric.common.net.naming.DefaultNameService.PortType;
import fabric.common.net.naming.NameService;
import fabric.common.util.LongKeyMap;
import fabric.dissemination.Glob;
import fabric.lang.security.NodePrincipal;
import fabric.lang.security.Principal;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.AllocateMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.DissemReadMessage;
import fabric.messages.GetCertChainMessage;
import fabric.messages.MakePrincipalMessage;
import fabric.messages.MessageToStoreHandler;
import fabric.messages.PrepareTransactionMessage;
import fabric.messages.ReadMessage;
import fabric.messages.StalenessCheckMessage;
import fabric.store.db.ObjectDB;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;

class Store extends MessageToStoreHandler {
  public final Node               node;
  public final TransactionManager tm;
  public final SurrogateManager   sm;
  public final ObjectDB           os;
  public final X509Certificate[]  certificateChain;
  public final PublicKey          publicKey;
  public final PrivateKey         privateKey;
  public final ConfigProperties   config;

  private final SubServerSocketFactory socketFactory;
  
  Store(Node node, String name) {
    super(name);
    
    //
    // read properties file
    //
    
    this.config = new ConfigProperties(name);
    
    KeyMaterial keyset = config.getKeyMaterial();
    
    this.certificateChain = keyset.getNameChain();
    this.publicKey        = keyset.getPublicKey();
    this.privateKey       = keyset.getPrivateKey();
    
    if (null == keyset.getPrincipalChain())
    {
      try {
        X509Certificate[] principalChain = new X509Certificate[certificateChain.length + 1];
        for (int i = 0; i < certificateChain.length; i++)
          principalChain[i+1] = certificateChain[i];
        principalChain[0] = Crypto.createCertificate(Long.toString(STORE_PRINCIPAL), publicKey, name, privateKey);
        keyset.setPrincipalChain(principalChain);
      } catch (GeneralSecurityException e) {
        throw new InternalError("failed to create store's principal certificate", e);
      }
    }
    
    this.socketFactory = createSocketFactory(keyset);
    
    this.node = node;
    this.os   = loadStore();
    this.tm   = new TransactionManager(this.os, this.privateKey);
    this.sm   = new SimpleSurrogateManager(tm);
  }

  //////////////////////////////////////////////////////////////////////////////
  // lifecycle                                                                //
  //////////////////////////////////////////////////////////////////////////////

  
  public void initialize() {
    // Ensure each store's object database has been properly initialized.
    os.ensureInit();
  }
  
  @Override
  protected SubServerSocket createServerSocket(){
    return socketFactory.createServerSocket();
  }

  public void shutdown() {
    try { os.close(); } catch(final IOException exc) { /* do nothing */ }
  }
  
  private ObjectDB loadStore() {
    try {
      // construct ObjectDB with class specified by properties file
      final Class<?>       osClass = Class.forName(config.backendClass);
      final Constructor<?> osCons  = osClass.getConstructor(String.class);
      final ObjectDB       os      = (ObjectDB) osCons.newInstance(config.name);

      return os;
    } catch (Exception exc) {
      throw new InternalError("could not initialize store", exc);
    }
  }
  
  private SubServerSocketFactory createSocketFactory(KeyMaterial keys) {
    try {
      Protocol authProt;
      if (config.useSSL)
        authProt = new HandshakeAuthenticated(keys);
      else
        authProt = new HandshakeBogus(this.config.name, STORE_PRINCIPAL);

      Protocol handshake = new HandshakeComposite(
          authProt,
          new HandshakeUnauthenticated());
      NameService nameService = new DefaultNameService(PortType.STORE);

      return new SubServerSocketFactory(handshake, nameService);
    } catch (final Exception e) {
      throw new InternalError("Failed to initialize store", e);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // message handlers                                                         //
  //////////////////////////////////////////////////////////////////////////////
  
  @Override
  public AbortTransactionMessage.Response handle(Principal p, AbortTransactionMessage message)
  throws AccessException {
    
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling Abort Message from {0} for tid={1}",
                nameOf(p), message.tid.topTid);
    
    tm.abortTransaction(p, message.tid.topTid);
    return new AbortTransactionMessage.Response();
  }

  /**
   * Processes the given request for new OIDs.
   */
  @Override
  public AllocateMessage.Response handle(Principal p, AllocateMessage msg)
  throws AccessException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling Allocate Message from {0}",
                nameOf(p));
    
    long[] onums = tm.newOnums(p, msg.num);
    return new AllocateMessage.Response(onums);
  }

  /**
   * Processes the given commit request
   */
  @Override
  public CommitTransactionMessage.Response handle(Principal p,
                                                  CommitTransactionMessage message)
  throws TransactionCommitFailedException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling Commit Message from {0} for tid={1}",
                nameOf(p), message.transactionID);
    tm.commitTransaction(p, message.transactionID);
    return new CommitTransactionMessage.Response();
  }

  /**
   * Processes the given PREPARE request.
   */
  @Override
  public PrepareTransactionMessage.Response handle(Principal p,
                                                   PrepareTransactionMessage msg)
  throws TransactionPrepareFailedException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling Prepare Message, worker={0}, tid={1}",
                nameOf(p), msg.tid);
    boolean subTransactionCreated = prepareTransaction(p,
                                                       msg.tid,
                                                       msg.commitTime,
                                                       msg.serializedCreates,
                                                       msg.serializedWrites,
                                                       msg.reads);
    return new PrepareTransactionMessage.Response(subTransactionCreated);
  }

  /**
   * Processes the given read request.
   */
  @Override
  public ReadMessage.Response handle(Principal p, ReadMessage msg)
  throws AccessException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
        "Handling Read Message from {0}, onum={1}", nameOf(p), msg.onum);

    ObjectGroup group = tm.getGroup(p, msg.onum);
    return new ReadMessage.Response(group);
  }

  /**
   * Processes the given dissemination-read request.
   */
  @Override
  public DissemReadMessage.Response handle(Principal p, DissemReadMessage msg)
  throws AccessException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling DissemRead message from {0}, onum={1}",
                nameOf(p), msg.onum);

    Glob glob = tm.getGlob(msg.onum);

    return new DissemReadMessage.Response(glob);
  }

  /**
   * Processes the given request for the store's SSL certificate chain.
   */
  @Override
  public GetCertChainMessage.Response handle(Principal p,
                                             GetCertChainMessage msg) {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling request for SSL cert chain, worker={0}",
                nameOf(p));
    return new GetCertChainMessage.Response(certificateChain);
  }

  /**
   * Processes the given request for a new node principal
   */
  @Override
  public MakePrincipalMessage.Response handle(Principal p,
      MakePrincipalMessage msg) throws FabricGeneralSecurityException {
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
            new NodePrincipal._Impl(store, null);
        principal.addDelegatesTo(store.getPrincipal());
        return principal.$getOnum();
      }
    }, true);
    
    // Create a certificate that binds the requester's key to the new principal
    // object's OID.
    X509Certificate cert;
    try {
      cert = Crypto.createCertificate(Long.toString(principalOnum),
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
  public StalenessCheckMessage.Response handle(Principal p,
      StalenessCheckMessage message) throws AccessException {
    STORE_REQUEST_LOGGER.log(Level.FINER,
        "Handling Staleness Check Message from {0}", nameOf(p));
    return new StalenessCheckMessage.Response(tm.checkForStaleObjects(p, message.versions));
  }
  
  /**
   * @return true iff a subtransaction was created for making Statistics
   *         objects.
   */
  private boolean prepareTransaction(
      Principal p,
      long tid, long commitTime,
      Collection<SerializedObject> serializedCreates,
      Collection<SerializedObject> serializedWrites, LongKeyMap<Integer> reads)
      throws TransactionPrepareFailedException {

    PrepareRequest req =
        new PrepareRequest(tid, commitTime, serializedCreates,
            serializedWrites, reads);

    sm.createSurrogates(req);

    boolean subTransactionCreated =
        tm.prepare(p, req);

    return subTransactionCreated;
  }
  
  private String nameOf(Principal p) {
    return p == null ? "BOTTOM" : ("fab://" + p.$getStore().name() + "/" + p
        .$getOnum());
  }
}