package net.ssl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.concurrent.Semaphore;

import fabric.common.Crypto;
import fabric.common.net.handshake.HandshakeAuthenticated;
import fabric.common.net.handshake.Protocol;
import fabric.common.net.handshake.ShakenSocket;

public final class AuthTest {

  private static final int       port = 11111;
  private static final char[]    password = "password".toCharArray();
  private static final Semaphore barrier = new Semaphore(1);
  
  public static void main(String[] args) throws Exception {
    
    // names
    String caName     = "CA";
    String storeName  = "store";
    String serverName = "server";
    String clientOnum = "17";
    String serverOnum = "35";
    
    // keys
    KeyPairGenerator keygen = Crypto.publicKeyGenInstance();
    KeyPair caKeys          = keygen.generateKeyPair();
    KeyPair clientKeys      = keygen.generateKeyPair();
    KeyPair serverKeys      = keygen.generateKeyPair();
    KeyPair storeNameKeys   = keygen.generateKeyPair();
    KeyPair serverNameKeys  = keygen.generateKeyPair();
    
    // certs
    X509Certificate caCert         = Crypto.createCertificate(
        caName, caKeys.getPublic(),
        caName, caKeys.getPrivate());
    X509Certificate storeNameCert  = Crypto.createCertificate(
        storeName,  storeNameKeys.getPublic(),
        caName,     caKeys.getPrivate());
    X509Certificate serverNameCert = Crypto.createCertificate(
        serverName, serverNameKeys.getPublic(),
        caName,     caKeys.getPrivate());
    X509Certificate clientCert     = Crypto.createCertificate(
        storeName + "/" + clientOnum, clientKeys.getPublic(),
        storeName, storeNameKeys.getPrivate());
    X509Certificate serverCert     = Crypto.createCertificate(
        serverName + "/" + serverOnum, serverKeys.getPublic(),
        storeName, storeNameKeys.getPrivate());
    
    // client's keystore:
    // 1. trusted cert for CA
    // 2. CA cert for store
    // 3. store cert for principal
    // 4. principal's private key
    final KeyStore clientKeystore = KeyStore.getInstance("JKS");
    clientKeystore.load(null);
    clientKeystore.setCertificateEntry(caName, caCert);
    clientKeystore.setKeyEntry(
        "principal",
        clientKeys.getPrivate(), password,
        new X509Certificate[] {storeNameCert, clientCert});
    
    // server's keystore:
    // 1. trusted cert for CA
    // 2. CA cert for "server"
    // 3. CA cert for store
    // 4. store cert for prinicpal
    // 5. name private key
    // 6. principal private key
    final KeyStore serverKeystore = KeyStore.getInstance("JKS");
    serverKeystore.load(null);
    serverKeystore.setCertificateEntry(caName, caCert);
    serverKeystore.setKeyEntry(
        "principal",
        serverKeys.getPrivate(), password,
        new X509Certificate[] {storeNameCert, serverCert});
    serverKeystore.setKeyEntry(
        serverName,
        serverKeys.getPrivate(), password,
        new X509Certificate[] {serverNameCert});

    if (args.length == 0 || args[0].equals("--server")) {
      // start the server
      barrier.acquire();
      new Thread("server") {
        @Override public void run() {runServer(serverKeystore);}
      }.start();
    }

    if (args.length == 0 || args[0].equals("--client")) {
      // start the client
      new Thread("client") {
        @Override public void run() {runClient(clientKeystore);}
      }.start();
    }
  }

  private static void runServer(KeyStore keys) {
    try {
      HandshakeAuthenticated.Factory factory = new HandshakeAuthenticated.Factory(keys, password);
      Protocol p = factory.create();
      
      ServerSocket ssock = new ServerSocket(port);
      barrier.release();
      Socket       sock  = ssock.accept();
      ShakenSocket shake = p.receive(sock);
      
      DataInputStream  in  = new DataInputStream( shake.sock.getInputStream() );
      DataOutputStream out = new DataOutputStream(shake.sock.getOutputStream());
      
      System.out.println(in.readUTF());
      out.writeUTF("Hello Doctor Falken.  How about a nice game of global thermonuclear war?");

      shake.sock.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }
  
  private static void runClient(KeyStore keys) {
    try {
      HandshakeAuthenticated.Factory factory = new HandshakeAuthenticated.Factory(keys, password);
      Protocol p = factory.create();
      
      barrier.acquire();
      Socket sock        = new Socket("localhost", port);
      ShakenSocket shake = p.initiate("server", sock);
      
      DataInputStream  in  = new DataInputStream( shake.sock.getInputStream() );
      DataOutputStream out = new DataOutputStream(shake.sock.getOutputStream());
      
      out.writeUTF("Hello Server!");
      System.out.println(in.readUTF());
      
      shake.sock.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }
}
