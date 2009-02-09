package fabric.dissemination;

import java.io.*;
import java.security.GeneralSecurityException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import jif.lang.Label;
import fabric.client.Client;
import fabric.client.Core;
import fabric.client.Client.Code;
import fabric.common.InternalError;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.lang.KeyObject;

/**
 * A glob is a serialized object and a set of related objects.
 */
public class Glob {
  /**
   * The head object's version number.
   */
  private final int version;

  private final Long keyOnum;
  private final byte[] data;

  private transient int level;
  private transient int frequency;
  private transient int popularity;

  private transient boolean home;

  /**
   * Used by the core to encrypt and sign an object group.
   */
  public Glob(Core core, ObjectGroup group) {
    this.version = group.obj().getVersion();

    final KeyObject keyObject = getLabel(core, group.obj()).keyObject();
    if (keyObject == null) {
      this.keyOnum = null;
    } else {
      this.keyOnum = keyObject.$getOnum();
    }

    try {
      // Set up the crypto for encrypting the object group.
      // TODO Sign the object too.
      Cipher cipher = makeCipher(keyObject, Cipher.ENCRYPT_MODE);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      CipherOutputStream cos = new CipherOutputStream(bos, cipher);
      DataOutputStream out = new DataOutputStream(cos);

      // Encrypt the group.
      group.write(out);

      // Retrieve the encrypted blob.
      out.flush();
      cos.close();
      this.data = bos.toByteArray();
    } catch (IOException e) {
      throw new InternalError(e);
    } catch (GeneralSecurityException e) {
      throw new InternalError(e);
    }
  }

  private Cipher makeCipher(final KeyObject keyObject, int opmode)
      throws GeneralSecurityException {
    if (keyObject == null) {
      return new NullCipher();
    } else {
      byte[] key = Client.runInTransaction(new Code<SecretKey>() {
        public SecretKey run() {
          return keyObject.getKey();
        }
      }).getEncoded();

      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(opmode, new SecretKeySpec(key, "AES"));
      return cipher;
    }
  }

  private Label getLabel(Core core, SerializedObject obj) {
    return new Label.$Proxy(core, obj.getLabelOnum());
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
    return version < glob.version;
  }

  /** Serializer. */
  public void write(DataOutput out) throws IOException {
    out.writeInt(version);
    if (keyOnum == null) {
      out.writeBoolean(false);
    } else {
      out.writeBoolean(true);
      out.writeLong(keyOnum);
    }
    out.writeInt(data.length);
    out.write(data);
  }

  /** Deserializer. */
  public Glob(DataInput in) throws IOException {
    this.version = in.readInt();
    if (in.readBoolean())
      this.keyOnum = in.readLong();
    else this.keyOnum = null;
    this.data = new byte[in.readInt()];
    in.readFully(this.data);
  }

  /**
   * @param core
   *          The core that this glob came from.
   */
  public ObjectGroup decrypt(Core core) {
    KeyObject keyObject =
        keyOnum == null ? null : new KeyObject.$Proxy(core, keyOnum);

    try {
      Cipher cipher = makeCipher(keyObject, Cipher.DECRYPT_MODE);
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

}
