import diaspora.common.*;
import diaspora.client.*;
import java.io.*;
import java.security.*;
import java.util.*;
import javax.crypto.*;

public class HelloWorld {
  public static void main(String[] args) throws Throwable {
    // Generate a key pair for signing stuff.
    KeyPairGenerator keygen =
      KeyPairGenerator.getInstance(Util.ALG_PUBLIC_KEY_GEN);
    KeyPair keyPair = keygen.genKeyPair();

    Client client = new Client(1000, 50, 2, 3);

    // Assume we have a fresh core.

    // Attempt to read OID 0; should get null.
    System.out.println();
    System.out.println("Attempting to read OID 0; should get null");
    try {
      System.out.println("Got " + client.readOID(0L));
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to write OID 0; should fail.
    System.out.println();
    System.out.println("Attempting to write OID 0; should fail");
    try {
      if (client.writeOID(0L, makeTestObj(keyPair)))
	System.out.println("Succeeded");
      else
	System.out.println("Failed");
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to insert at OID 0; should succeed.
    System.out.println();
    System.out.println("Attempting to insert at OID 0; should succeed");
    try {
      if (client.insertOID(0L, makeTestObj(keyPair)))
	System.out.println("Succeeded");
      else
	System.out.println("Failed");
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to read OID 0; should succeed.
    System.out.println();
    System.out.println("Attempting to read OID 0; should succeed");
    try {
      System.out.println("Got " + client.readOID(0L));
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to write that OID; should succeed.
    System.out.println();
    System.out.println("Attempting to write OID 0; should succeed");
    try {
      if (client.writeOID(0L, makeTestObj(keyPair)))
	System.out.println("Succeeded");
      else
	System.out.println("Failed");
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to read that OID; should fail with access error.
    System.out.println();
    System.out.println("Attempting to read OID 0; should fail with access error");
    try {
      System.out.println("Got " + client.readOID(0L));
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to write that OID; should succeed.
    System.out.println();
    System.out.println("Attempting to write OID 0; should succeed");
    try {
      if (client.writeOID(0L, makeTestObj(keyPair)))
	System.out.println("Succeeded");
      else
	System.out.println("Failed");
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to read that OID; should succeed.
    System.out.println();
    System.out.println("Attempting to read OID 0; should succeed");
    try {
      System.out.println("Got " + client.readOID(0L));
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to write that OID; should fail with access error.
    System.out.println();
    System.out.println("Attempting to write OID 0; should fail with access error");
    try {
      if (client.writeOID(0L, makeTestObj(keyPair)))
	System.out.println("Succeeded");
      else
	System.out.println("Failed");
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Get some OIDs.
    System.out.println();
    System.out.println("Obtaining new OIDs...");
    long[] oids = client.getNewOIDs(0);
    System.out.println("Got " + oids.length + " leases:");
    for (int i = 0; i < oids.length; i++) System.out.println(oids[i]);

    try {
      SecretKey key = KeyGenerator.getInstance(Util.ALG_SECRET_KEY_GEN).generateKey();
      System.out.println(makeTestObj(keyPair).encrypt(key));
      System.out.println(makeTestObj(keyPair).encrypt(key).decrypt(key));
    } catch (Exception e) { throw new RuntimeException(e); }
  }

  static DObject.Unencrypted makeTestObj(KeyPair keys) {
    DObject.Unencrypted result = new DObject.Unencrypted(0, 0, keys.getPublic(),
	null, new byte[0], 0, new byte[0], new Serializable() { });

    try {
      Signature signature = Signature.getInstance(Util.ALG_SIGNATURE);
      signature.initSign(keys.getPrivate(), new SecureRandom());
      signature.update(Util.toArray(result));
      result.signature = signature.sign();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    } catch (InvalidKeyException e) {
      throw new RuntimeException(e);
    } catch (SignatureException e) {
      throw new RuntimeException(e);
    } catch (java.io.IOException e) {
      throw new RuntimeException(e);
    }

    return result;
  }
}

