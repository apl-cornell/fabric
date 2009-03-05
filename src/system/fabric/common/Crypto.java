package fabric.common;

import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import fabric.common.exceptions.InternalError;

/**
 * This is the clearinghouse for all things crypto.
 */
public final class Crypto {
  public static final String ALG_SIGNATURE = "SHA1withRSA";

  public static final String ALG_SECRET_KEY_GEN = "AES";
  public static final int SIZE_SECRET_KEY = 128;
  public static final String ALG_SECRET_CRYPTO = "AES/CBC/PKCS5Padding";
  
  public static final String ALG_PUBLIC_KEY_GEN = "RSA";
  public static final int SIZE_PUBLIC_KEY = 1024;

  private static final KeyGenerator secretKeyGen;
  private static final KeyPairGenerator publicKeyGen;
  private static final SecureRandom random = new SecureRandom();

  static {
    try {
      secretKeyGen = secretKeyGenInstance();
      publicKeyGen = publicKeyGenInstance();
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    }
  }

  public static Signature signatureInstance() throws NoSuchAlgorithmException {
    return Signature.getInstance(ALG_SIGNATURE);
  }

  public static KeyGenerator secretKeyGenInstance()
      throws NoSuchAlgorithmException {
    KeyGenerator result = KeyGenerator.getInstance(ALG_SECRET_KEY_GEN);
    result.init(SIZE_SECRET_KEY);
    return result;
  }

  public static SecretKey genSecretKey() {
    synchronized (secretKeyGen) {
      return secretKeyGen.generateKey();
    }
  }
  
  public static KeyPairGenerator publicKeyGenInstance() throws NoSuchAlgorithmException {
    KeyPairGenerator result = KeyPairGenerator.getInstance(ALG_PUBLIC_KEY_GEN);
    result.initialize(SIZE_PUBLIC_KEY);
    return result;
  }
  
  public static KeyPair genKeyPair() {
    synchronized (publicKeyGen) {
      return publicKeyGen.generateKeyPair();
    }
  }

  /**
   * Fills an initialization vector with random bytes.
   */
  public static void fillIV(byte[] iv) {
    synchronized (random) {
      random.nextBytes(iv);
    }
  }
  
  /**
   * Creates a new initialization vector.
   */
  public static byte[] makeIV() {
    byte[] result = new byte[16];
    fillIV(result);
    return result;
  }

  /**
   * Creates an initializes a new Cipher instance with the given parameters.
   * 
   * @param opmode
   *          The mode of operation. One of the mode constants in Cipher.
   * @param key
   *          The secret key to use.
   * @param iv
   *          The initialization vector to use. For encryption, this should be
   *          randomly generated; for decryption, this should match the one used
   *          during encryption.
   */
  public static Cipher cipherInstance(int opmode, byte[] key, byte[] iv)
      throws NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidKeyException, InvalidAlgorithmParameterException {
    if (key == null) return new NullCipher();
    
    Cipher result = Cipher.getInstance(ALG_SECRET_CRYPTO);

    if (iv != null) {
      IvParameterSpec ivSpec = new IvParameterSpec(iv);
      result.init(opmode, new SecretKeySpec(key, ALG_SECRET_KEY_GEN), ivSpec);
    } else {
      result.init(opmode, new SecretKeySpec(key, ALG_SECRET_KEY_GEN));
    }

    return result;
  }
}
