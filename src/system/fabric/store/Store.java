package fabric.store;

import static fabric.common.Logging.STORE_REQUEST_LOGGER;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.logging.Level;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import fabric.common.*;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricGeneralSecurityException;
import fabric.common.exceptions.InternalError;
import fabric.common.net.SubServerSocket;
import fabric.common.util.LongKeyMap;
import fabric.dissemination.Glob;
import fabric.lang.security.NodePrincipal;
import fabric.messages.*;
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
  public final Certificate[]      certificateChain;
  public final PublicKey          publicKey;
  public final PrivateKey         privateKey;
  public final ConfigProperties   config;

  Store(Node node, String name) {
    super(name);
    
    //
    // read properties file
    //
    
    this.config = new ConfigProperties(name);
    
    //
    // Set up SSL
    //

    char[] password = config.password;
    
    KeyStore keyStore;
    FileInputStream in = null;
    try {
      keyStore = KeyStore.getInstance("JKS");
      in       = new FileInputStream(config.keystore);
      keyStore.load(in, password);
      in.close();
    } catch (KeyStoreException e) {
      throw new InternalError("Unable to open key store.", e);
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    } catch (CertificateException e) {
      throw new InternalError("Unable to open key store.", e);
    } catch (FileNotFoundException e) {
      throw new InternalError("File not found: " + e.getMessage());
    } catch (IOException e) {
      if (e.getCause() instanceof UnrecoverableKeyException)
        throw new InternalError("Unable to open key store: invalid password.");
      throw new InternalError("Unable to open key store.", e);
    }

    try {
      SSLContext sslContext = SSLContext.getInstance("TLS");
      KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
      kmf.init(keyStore, password);

      TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
      tmf.init(keyStore);
      TrustManager[] tm = tmf.getTrustManagers();
      sslContext.init(kmf.getKeyManagers(), tm, null);

      this.certificateChain =
          keyStore.getCertificateChain(name);
      Certificate publicKeyCert =
          keyStore.getCertificate(name);
      this.publicKey  = publicKeyCert.getPublicKey();
      this.privateKey = (PrivateKey) keyStore.getKey(name, password);
      
    } catch (KeyManagementException e) {
      throw new InternalError("Unable to initialise key manager factory.", e);
    } catch (UnrecoverableKeyException e1) {
      throw new InternalError("Unable to open key store.", e1);
    } catch (NoSuchAlgorithmException e1) {
      throw new InternalError(e1);
    } catch (KeyStoreException e1) {
      throw new InternalError("Unable to initialise key manager factory.", e1);
    }
    
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
    return node.getServerSocketFactory().createServerSocket();
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

  //////////////////////////////////////////////////////////////////////////////
  // message handlers                                                         //
  //////////////////////////////////////////////////////////////////////////////
  
  @Override
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
  @Override
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
  @Override
  public CommitTransactionMessage.Response handle(NodePrincipal p,
                                                  CommitTransactionMessage message)
  throws TransactionCommitFailedException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling Commit Message from {0} for tid={1}",
                p.name(), message.transactionID);
    tm.commitTransaction(p, message.transactionID);
    return new CommitTransactionMessage.Response();
  }

  /**
   * Processes the given PREPARE request.
   */
  @Override
  public PrepareTransactionMessage.Response handle(NodePrincipal p,
                                                   PrepareTransactionMessage msg)
  throws TransactionPrepareFailedException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling Prepare Message, worker={0}, tid={1}",
                p.name(), msg.tid);
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
  public ReadMessage.Response handle(NodePrincipal p, ReadMessage msg)
  throws AccessException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling Read Message from {0}, onum={1}",
                p.name(), msg.onum);

    ObjectGroup group = tm.getGroup(p, msg.onum);
    return new ReadMessage.Response(group);
  }

  /**
   * Processes the given dissemination-read request.
   */
  @Override
  public DissemReadMessage.Response handle(NodePrincipal p, DissemReadMessage msg)
  throws AccessException {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling DissemRead message from {0}, onum={1}",
                p.name(), msg.onum);

    Glob glob = tm.getGlob(msg.onum);

    return new DissemReadMessage.Response(glob);
  }

  /**
   * Processes the given request for the store's SSL certificate chain.
   */
  @Override
  public GetCertChainMessage.Response handle(NodePrincipal p,
                                             GetCertChainMessage msg) {
    Logging.log(STORE_REQUEST_LOGGER, Level.FINER,
                "Handling request for SSL cert chain, worker={0}",
                p.name());
    return new GetCertChainMessage.Response(certificateChain);
  }

  /**
   * Processes the given request for a new node principal
   */
  @Override
  public MakePrincipalMessage.Response handle(NodePrincipal p,
      MakePrincipalMessage msg) throws FabricGeneralSecurityException {
    // Note: p should always be null.
    
    // Get the store's node object and its signing key. 
    final String storeName = p.name();
    final fabric.worker.Store store = Worker.getWorker().getStore(storeName);
    final PrivateKey storeKey = privateKey;
    
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
    X509Certificate cert;
    try {
      cert = Crypto.createCertificate(Long.toString(principalOnum),
          msg.requesterKey, storeName, storeKey);
    } catch (GeneralSecurityException e) {
      throw new FabricGeneralSecurityException(e);
    }
    
    return new MakePrincipalMessage.Response(principalOnum, cert);
  }

  /**
   * Processes the given staleness check request.
   */
  @Override
  public StalenessCheckMessage.Response handle(NodePrincipal p,
      StalenessCheckMessage message) throws AccessException {
    STORE_REQUEST_LOGGER.log(Level.FINER,
        "Handling Staleness Check Message from {0}", p.name());
    return new StalenessCheckMessage.Response(tm.checkForStaleObjects(p, message.versions));
  }
  
  /**
   * @return true iff a subtransaction was created for making Statistics
   *         objects.
   */
  private boolean prepareTransaction(
      NodePrincipal p,
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
}