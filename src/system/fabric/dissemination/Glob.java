package fabric.dissemination;

import java.io.*;
import java.security.*;

import javax.crypto.*;

import fabric.lang.security.Label;
import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.worker.Worker.Code;
import fabric.common.*;
import fabric.common.exceptions.InternalError;
import fabric.lang.security.SecretKeyObject;

/**
 * A glob is an ObjectGroup that has been encrypted and signed.
 */
public class Glob implements FastSerializable {
  /**
   * The time at which this glob was created.
   */
  private final long timestamp;

  /**
   * A pointer to the encryption key. This can be null if the null cipher was
   * used. (This can happen, for example, if the data was public.)
   */
  private final SecretKeyObject keyObject;

  /**
   * The initialization vector for decrypting. Can be null if the null cipher
   * was used. (This can happen, for example, if the data was public.)
   */
  private final byte[] iv;

  /**
   * The encrypted data.
   */
  private final byte[] data;

  /**
   * The signature on the version, keyOnum, iv, and data.
   */
  private final byte[] signature;

  private transient int level;
  private transient int frequency;
  private transient int popularity;

  private transient boolean home;

  /**
   * Used by the store to encrypt and sign an object group.
   * 
   * @param store
   *          The store at which the group resides.
   * @param group
   *          The group to encapsulate.
   * @param key
   *          The store's private key. Used to sign the glob.
   */
  public Glob(Store store, ObjectGroup group, PrivateKey key) {
    this.timestamp = System.currentTimeMillis();

    this.keyObject = getLabel(store, group).keyObject();
    if (keyObject == null) {
      this.iv = null;
    } else {
      this.iv = Crypto.makeIV();
    }

    try {
      // Set up the crypto for encrypting the object group.
      Cipher cipher = makeCipher(keyObject, Cipher.ENCRYPT_MODE, iv);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      CipherOutputStream cos = new CipherOutputStream(bos, cipher);
      DataOutputStream out = new DataOutputStream(cos);

      // Encrypt the group.
      group.write(out);

      // Retrieve the encrypted blob.
      out.flush();
      cos.close();
      this.data = bos.toByteArray();

      // Sign things.
      Signature signer = Crypto.signatureInstance();
      signer.initSign(key);
      updateSignature(signer);
      this.signature = signer.sign();
    } catch (IOException e) {
      throw new InternalError(e);
    } catch (GeneralSecurityException e) {
      throw new InternalError(e);
    }
  }

  /**
   * Updates the given signature with the data to be signed or verified.
   * 
   * @throws SignatureException
   */
  private void updateSignature(Signature sig) throws SignatureException {
    // Update with version number.
    sig.update((byte) (timestamp >>> 56));
    sig.update((byte) (timestamp >>> 48));
    sig.update((byte) (timestamp >>> 40));
    sig.update((byte) (timestamp >>> 32));
    sig.update((byte) (timestamp >>> 24));
    sig.update((byte) (timestamp >>> 16));
    sig.update((byte) (timestamp >>> 8));
    sig.update((byte) timestamp);

    // Update with keyObject pointer, if non-null.
    if (keyObject != null) {
      try {
        sig.update(keyObject.$getStore().name().getBytes("UTF8"));
      } catch (UnsupportedEncodingException e) {
        throw new InternalError(e);
      }
      
      long val = keyObject.$getOnum();
      sig.update((byte) (val >>> 56));
      sig.update((byte) (val >>> 48));
      sig.update((byte) (val >>> 40));
      sig.update((byte) (val >>> 32));
      sig.update((byte) (val >>> 24));
      sig.update((byte) (val >>> 16));
      sig.update((byte) (val >>> 8));
      sig.update((byte) val);
    }

    // Update with iv, if non-null.
    if (iv != null) sig.update(iv);

    // Update with data.
    sig.update(data);
  }

  private Cipher makeCipher(final SecretKeyObject keyObject, int opmode, byte[] iv)
      throws GeneralSecurityException {
    byte[] key = null;
    if (keyObject != null) {
      key = Worker.runInSubTransaction(new Code<SecretKey>() {
        public SecretKey run() {
          return keyObject.getKey();
        }
      }).getEncoded();
    }

    return Crypto.cipherInstance(opmode, key, iv);
  }

  private Label getLabel(Store store, ObjectGroup group) {
    SerializedObject obj =
        group.objects().entrySet().iterator().next().getValue();
    return new Label._Proxy(store, obj.getUpdateLabelOnum());
  }

  /** The dissemination level of the glob. 0 is replicated to all nodes. */
  public int level() {
    return level;
  }

  /** Sets the level. */
  public void level(int level) {
    this.level = level;
  }

  /** How many times the object has been accessed since last aggregation. */
  public int frequency() {
    return frequency;
  }

  /** Sets the frequency. */
  public void frequency(int frequency) {
    this.frequency = frequency;
  }

  /** Increments frequency by 1. */
  public void touch() {
    this.frequency++;
  }

  /** The popularity of the glob. An exponential-decayed valued. */
  public int popularity() {
    return popularity;
  }

  /** Sets the popularity. */
  public void popularity(int popularity) {
    this.popularity = popularity;
  }

  /** Whether this is the home node for this glob. */
  public boolean home() {
    return home;
  }

  /**
   * Whether this Glob is older than the given Glob.
   */
  public boolean isOlderThan(Glob glob) {
    return timestamp < glob.timestamp;
  }

  public void verifySignature(PublicKey key) throws SignatureException, InvalidKeyException {
    // Check the signature.
    Signature verifier = Crypto.signatureInstance();
    verifier.initVerify(key);
    updateSignature(verifier);
    if (!verifier.verify(signature))
      throw new SignatureException("Failed to verify signature");
  }

  /** Serializer. */
  public void write(DataOutput out) throws IOException {
    out.writeLong(timestamp);
    if (keyObject == null) {
      out.writeBoolean(false);
    } else {
      out.writeBoolean(true);
      out.writeUTF(keyObject.$getStore().name());
      out.writeLong(keyObject.$getOnum());
    }

    if (iv == null) {
      out.writeInt(0);
    } else {
      out.writeInt(iv.length);
      out.write(iv);
    }

    out.writeInt(data.length);
    out.write(data);

    out.writeInt(signature.length);
    out.write(signature);
  }

  /**
   * Deserializer.
   * 
   * @param key
   *          The public key for verifying the signature. (If null, signature
   *          verification is bypassed.)
   */
  public Glob(DataInput in) throws IOException {
    this.timestamp = in.readLong();
    if (in.readBoolean()) {
      Store store = Worker.getWorker().getStore(in.readUTF());
      this.keyObject = new SecretKeyObject._Proxy(store, in.readLong());
    } else this.keyObject = null;

    int ivLength = in.readInt();
    if (ivLength > 0) {
      this.iv = new byte[ivLength];
      in.readFully(this.iv);
    } else {
      this.iv = null;
    }

    this.data = new byte[in.readInt()];
    in.readFully(this.data);

    this.signature = new byte[in.readInt()];
    in.readFully(this.signature);
  }

  /**
   * @param store
   *          The store that this glob came from.
   */
  public ObjectGroup decrypt(Store store) {
    try {
      Cipher cipher = makeCipher(keyObject, Cipher.DECRYPT_MODE, iv);
      ByteArrayInputStream bis = new ByteArrayInputStream(data);
      DataInputStream in =
          new DataInputStream(new CipherInputStream(bis, cipher));

      // Decrypt the group.
      return new ObjectGroup(in);
    } catch (IOException e) {
      throw new InternalError(e);
    } catch (GeneralSecurityException e) {
      throw new InternalError(e);
    }
  }

  public long getTimestamp() {
    return timestamp;
  }

}
