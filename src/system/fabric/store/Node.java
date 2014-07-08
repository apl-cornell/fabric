/**
 * Copyright (C) 2010 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabric.store;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.Map;

import javax.net.ssl.*;

import fabric.worker.Worker;
import fabric.worker.RemoteStore;
import fabric.common.ConfigProperties;
import fabric.common.ONumConstants;
import fabric.common.SSLSocketFactoryTable;
import fabric.common.exceptions.InternalError;
import fabric.store.db.ObjectDB;

public class Node {

  public Options opts;

  /**
   * A map from store host-names to corresponding <code>SSLSocketFactory</code>s
   * and <code>TransactionManager</code>s.
   */
  protected Store store;

  private final ConnectionHandler connectionHandler;

  protected class Store {
    public final String name;
    public final SSLSocketFactory factory;
    public final TransactionManager tm;
    public final SurrogateManager sm;
    public final ObjectDB os;
    public final Certificate[] certificateChain;
    public final PublicKey publicKey;
    public final PrivateKey privateKey;
    public final int port;

    private Store(String name) {
      this.name = name;

      //
      // read properties file
      //
      ConfigProperties props = new ConfigProperties(name);
      
      char[] password = props.password;
      
      //
      // Set up SSL
      //
      KeyStore keyStore;
      FileInputStream in = null;
      try {
        keyStore = KeyStore.getInstance("JKS");
        in       = new FileInputStream(props.keystore);
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
        this.factory = sslContext.getSocketFactory();


        this.certificateChain =
            keyStore.getCertificateChain(name);
        Certificate publicKeyCert =
            keyStore.getCertificate(name);
        this.publicKey  = publicKeyCert.getPublicKey();
        this.privateKey = (PrivateKey) keyStore.getKey(name, password);
        
        SSLSocketFactoryTable.register(name, this.factory);
      } catch (KeyManagementException e) {
        throw new InternalError("Unable to initialise key manager factory.", e);
      } catch (UnrecoverableKeyException e1) {
        throw new InternalError("Unable to open key store.", e1);
      } catch (NoSuchAlgorithmException e1) {
        throw new InternalError(e1);
      } catch (KeyStoreException e1) {
        throw new InternalError("Unable to initialise key manager factory.", e1);
      }
      
      this.os   = loadStore(props);
      this.tm   = new TransactionManager(this.os, this.privateKey);
      this.sm   = new SimpleSurrogateManager(tm);
      this.port = props.storePort;
    }
  }

  public Node(Options opts) {
    this.opts              = opts;
    this.connectionHandler = new ConnectionHandler(this);

    this.store = new Store(opts.storeName);
    
    // Start the worker before instantiating the stores in case their object
    // databases need initialization. (The initialization code will be run on
    // the worker.)
    startWorker();

    // Ensure each store's object database has been properly initialized.
    store.os.ensureInit();

    System.out.println("Store started");
  }

  private ObjectDB loadStore(ConfigProperties props) {
    try {
      final ObjectDB os =
          (ObjectDB) Class.forName(props.backendClass).getConstructor(String.class)
              .newInstance(props.name);

      // register a hook to close the object database gracefully.
      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
          try {
            os.close();
          } catch (final IOException exc) {
          }
        }
      });

      return os;
    } catch (Exception exc) {
      throw new InternalError("could not initialize store", exc);
    }
  }

  private void startWorker() {
    try {
      Map<String, RemoteStore> initStoreSet = Collections.singletonMap(
          store.name, (RemoteStore) new InProcessStore(store.name, store));

      Worker.initialize(store.name, "fab://" + store.name
          + "/" + ONumConstants.STORE_PRINCIPAL, initStoreSet);
    } catch (Exception e) {
      throw new InternalError(e);
    }
  }

  /**
   * Returns the store corresponding to the given name.
   * 
   * @param name
   *          Name of store to retrieve.
   * @return The requested store, or null if it does not exist.
   */
  public Store getStore(String name) {
    return name.equals(store.name) ? store : null;
  }

  /**
   * Given the host name for an object store, returns its corresponding
   * <code>TransactionManager</code>.
   * 
   * @return null if there is no corresponding binding.
   */
  public TransactionManager getTransactionManager(String storeName) {
    return storeName.equals(store.name) ? store.tm : null;
  }

  /**
   * Given the host name for an object store, returns its corresponding
   * <code>SSLSocketFactory</code>.
   */
  public SSLSocketFactory getSSLSocketFactory(String storeName) {
    return storeName.equals(store.name) ? store.factory : null; 
  }

  public SurrogateManager getSurrogateManager(String storeName) {
    return storeName.equals(store.name) ? store.sm : null; 
  }

  public PrivateKey getPrivateKey(String storeName) {
    return storeName.equals(store.name) ? store.privateKey : null;
  }

  /**
   * The main execution body of a store node.
   */
  public void start() throws IOException {
    // Start listening.
    ServerSocketChannel server = ServerSocketChannel.open();
    server.configureBlocking(true);
    // TODO: ugliness...soon to be replaced - mdg
    server.socket().bind(new InetSocketAddress(store.port));

    // The main server loop.
    while (true) {
      // Accept a connection and give it to the connection handler.
      SocketChannel connection = server.accept();

      // XXX not setting timeout
      // worker.setSoTimeout(opts.timeout * 1000);
      connectionHandler.handle(connection);
    }
  }
}
