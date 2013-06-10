package fabric.dissemination;

import static fabric.common.Logging.NETWORK_MESSAGE_RECEIVE_LOGGER;
import static fabric.common.ONumConstants.EMPTY_LABEL;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Level;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;

import fabric.common.Crypto;
import fabric.common.FastSerializable;
import fabric.common.Logging;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.VersionWarranty;
import fabric.common.exceptions.InternalError;
import fabric.common.util.Pair;
import fabric.lang.security.Label;
import fabric.lang.security.SecretKeyObject;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;

/**
 * A glob is an ObjectGroup that has been encrypted and signed.
 */
public class Glob implements FastSerializable {
  /**
   * The time at which this glob was created. This acts as a version number.
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
   * The signature on the timestamp, keyObject OID, iv, and data.
   */
  private final byte[] signature;

  private transient int level;
  private transient int frequency;

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

    Label label = getLabel(store, group);
    this.keyObject = label.keyObject();
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

      if (label.$getOnum() == EMPTY_LABEL) {
        this.signature = new byte[0];
      } else {
        // Sign things.
        Signature signer = Crypto.signatureInstance();
        signer.initSign(key);
        updateSignature(signer);
        this.signature = signer.sign();
      }
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
    // Update with the timestamp (which acts as the version number).
    sig.update((byte) (timestamp >>> 56));
    sig.update((byte) (timestamp >>> 48));
    sig.update((byte) (timestamp >>> 40));
    sig.update((byte) (timestamp >>> 32));
    sig.update((byte) (timestamp >>> 24));
    sig.update((byte) (timestamp >>> 16));
    sig.update((byte) (timestamp >>> 8));
    sig.update((byte) timestamp);

    // Update with keyObject OID, if non-null.
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

  private Cipher makeCipher(final SecretKeyObject keyObject, int opmode,
      byte[] iv) throws GeneralSecurityException {
    byte[] key = null;
    if (keyObject != null) {
      key = Worker.runInSubTransaction(new Code<SecretKey>() {
        @Override
        public SecretKey run() {
          return keyObject.getKey();
        }
      }).getEncoded();
    }

    return Crypto.cipherInstance(opmode, key, iv);
  }

  private Label getLabel(Store store, ObjectGroup group) {
    SerializedObject obj =
        group.objects().entrySet().iterator().next().getValue().first;
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

  /**
   * Whether this Glob is older than the given Glob.
   */
  public boolean isOlderThan(Glob glob) {
    return timestamp < glob.timestamp;
  }

  public void verifySignature(PublicKey key) throws SignatureException,
      InvalidKeyException {
    if (signature.length == 0) {
      // No signature received. Verify that none was required.
      // XXX This is rather inefficient.
      ObjectGroup group = decrypt();
      for (Pair<SerializedObject, VersionWarranty> pair : group.objects()
          .values()) {
        if (pair.first.getUpdateLabelOnum() != EMPTY_LABEL)
          throw new SignatureException("Failed to verify signature");
      }
    } else {
      // Check the signature.
      Signature verifier = Crypto.signatureInstance();
      verifier.initVerify(key);
      updateSignature(verifier);
      if (!verifier.verify(signature))
        throw new SignatureException("Failed to verify signature");
    }
  }

  /** Serializer. */
  @Override
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

  public ObjectGroup decrypt() {
    try {
      Cipher cipher = makeCipher(keyObject, Cipher.DECRYPT_MODE, iv);
      ByteArrayInputStream bis = new ByteArrayInputStream(data);
      DataInputStream in =
          new DataInputStream(new CipherInputStream(bis, cipher));

      // Decrypt the group.
      ObjectGroup result = new ObjectGroup(in);
      if (NETWORK_MESSAGE_RECEIVE_LOGGER.isLoggable(Level.FINE)) {
        Logging.log(NETWORK_MESSAGE_RECEIVE_LOGGER, Level.FINE,
            "Decrypted object group containing {0} objects", result.objects()
                .size());
      }
      return result;
    } catch (IOException e) {
      throw new InternalError(e);
    } catch (GeneralSecurityException e) {
      throw new InternalError(e);
    }
  }

  public long getTimestamp() {
    return timestamp;
  }

  /**
   * Copies dissemination-related state to the given glob.
   */
  void copyDissemStateTo(Glob g) {
    g.level = level;
    g.frequency = frequency;
  }

}
