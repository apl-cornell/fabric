package fabric.common;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.NullCipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;

import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import fabric.common.exceptions.InternalError;

/**
 * This is the clearing house for all things crypto.
 */
public final class Crypto {
  public static final String ALG_SIGNATURE = "SHA256withRSA";

  public static final String ALG_SECRET_KEY_GEN = "AES";
  public static final int SIZE_SECRET_KEY = 128;
  public static final String ALG_SECRET_CRYPTO = "AES/CBC/PKCS5Padding";

  public static final String ALG_PUBLIC_KEY_GEN = "RSA";
  public static final int SIZE_PUBLIC_KEY = 1024;

  public static final String ALG_HASH = "SHA-256";

  private static final KeyGenerator secretKeyGen;
  private static final KeyPairGenerator publicKeyGen;
  private static final Provider sigProvider;
  private static final SecureRandom random = new SecureRandom();

  static {
    secretKeyGen = secretKeyGenInstance();
    publicKeyGen = publicKeyGenInstance();
    sigProvider = signatureInstance().getProvider();
  }

  public static MessageDigest digestInstance() {
    try {
      return MessageDigest.getInstance(ALG_HASH);
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    }
  }

  public static Signature signatureInstance() {
    try {
      return Signature.getInstance(ALG_SIGNATURE);
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    }
  }

  public static KeyGenerator secretKeyGenInstance() {
    try {
      KeyGenerator result = KeyGenerator.getInstance(ALG_SECRET_KEY_GEN);
      result.init(SIZE_SECRET_KEY);
      return result;
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    }
  }

  public static SecretKey genSecretKey() {
    synchronized (secretKeyGen) {
      return secretKeyGen.generateKey();
    }
  }

  public static KeyPairGenerator publicKeyGenInstance() {
    try {
      KeyPairGenerator result =
          KeyPairGenerator.getInstance(ALG_PUBLIC_KEY_GEN);
      result.initialize(SIZE_PUBLIC_KEY);
      return result;
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    }
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

  /**
   * Validates the given certificate chain against the given trust store.
   */
  public static void validateCertificateChain(Certificate[] certificateChain,
      Set<TrustAnchor> trustStore) throws GeneralSecurityException {
    PKIXParameters params = new PKIXParameters(trustStore);
    params.setSigProvider(sigProvider.getName());
    params.setRevocationEnabled(false);
    CertificateFactory certFactory = CertificateFactory.getInstance("X509");
    CertPath certPath =
        certFactory.generateCertPath(Arrays.asList(certificateChain));
    CertPathValidator pathValidator = CertPathValidator.getInstance("PKIX");
    pathValidator.validate(certPath, params);
  }

  /**
   * Generates a certificate, signed by the issuer, binding the subject's name
   * to their public key.
   */
  public static X509Certificate createCertificate(String subjectName,
      PublicKey subjectKey, String issuerName, PrivateKey issuerKey)
      throws GeneralSecurityException {

    Calendar expiry = Calendar.getInstance();
    expiry.add(Calendar.YEAR, 1);

    X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();

    certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
    certGen.setIssuerDN(new X509Name("CN=" + issuerName));
    certGen.setSubjectDN(new X509Name("CN=" + subjectName));
    certGen.setSignatureAlgorithm(ALG_SIGNATURE);
    certGen.setPublicKey(subjectKey);
    certGen.setNotBefore(new Date(System.currentTimeMillis()));
    certGen.setNotAfter(expiry.getTime());

    return certGen.generate(issuerKey);
  }

  /**
   * Extracts the CN component of a Distinguished Name.
   */
  public static String getCN(String dn) {
    try {
      LdapName ldapName = new LdapName(dn);
      for (int i = 0; i < ldapName.size(); i++) {
        String component = ldapName.get(i);
        if (component.substring(0, 3).equalsIgnoreCase("cn=")) {
          return component.substring(3);
        }
      }
    } catch (InvalidNameException e) {
    }

    return null;
  }
}
