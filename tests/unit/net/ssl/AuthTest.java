package net.ssl;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

import fabric.common.Crypto;

public final class AuthTest {

  private static class Client implements Runnable {
    public void run() {
    }
  }
  
  private static class Server implements Runnable {
    public void run() {
    }
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {

    // names
    String caName     = "CA";
    String storeName  = "store";
    String serverName = "server";
    String clientOnum = "17";
    String serverOnum = "35";
    char[] password   = "password".toCharArray();
    
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
    KeyStore clientKeystore = KeyStore.getInstance("JKS");
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
    KeyStore serverKeystore = KeyStore.getInstance("JKS");
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

  }

}
