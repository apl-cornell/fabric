package fabric.client.remote;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.crypto.Cipher;

import jif.lang.Label;

import fabric.client.AbortException;
import fabric.client.Client;
import fabric.client.Core;
import fabric.client.LocalCore;
import fabric.common.*;
import fabric.common.InternalError;
import fabric.common.util.LongKeyMap;
import fabric.lang.SecretKeyObject;
import fabric.lang.Object.$Proxy;

/**
 * Maps proxies to the host that holds the most up-to-date copy of that object.
 * Also maps proxies of newly created objects to their corresponding labels.
 */
public class UpdateMap implements FastSerializable {

  private static String ALG_HASH = "MD5";

  /**
   * Maps md5(oid) to Label. These are the "create" entries.
   */
  private Map<Hash, Label> creates;

  /**
   * Maps md5(oid, object key) to (iv, enc(hostname, object key, iv)).
   */
  private Map<Hash, Pair<byte[], byte[]>> updates;

  /**
   * Cache for "update" entries and non-entries that have been discovered.
   */
  private OidKeyHashMap<RemoteClient> readCache;

  /**
   * Cache for "update" entries that haven't been encrypted yet.
   */
  private OidKeyHashMap<Pair<$Proxy, RemoteClient>> writeCache;

  public int version;

  public UpdateMap() {
    this.creates = new HashMap<Hash, Label>();
    this.updates = new HashMap<Hash, Pair<byte[], byte[]>>();
    this.readCache = new OidKeyHashMap<RemoteClient>();
    this.writeCache = new OidKeyHashMap<Pair<$Proxy, RemoteClient>>();
    this.version = 0;
  }

  /**
   * Copy constructor.
   */
  public UpdateMap(UpdateMap map) {
    this.creates = new HashMap<Hash, Label>(map.creates);
    this.updates = new HashMap<Hash, Pair<byte[], byte[]>>(map.updates);
    this.readCache = new OidKeyHashMap<RemoteClient>(map.readCache);
    this.writeCache =
        new OidKeyHashMap<Pair<$Proxy, RemoteClient>>(map.writeCache);
    this.version = map.version;
  }

  /**
   * Deserialization constructor.
   */
  public UpdateMap(DataInput in) throws IOException {
    this();
    this.version = -1;

    Client client = Client.getClient();

    // Read creates.
    int size = in.readInt();
    for (int i = 0; i < size; i++) {
      byte[] buf = new byte[in.readInt()];
      in.readFully(buf);

      Hash key = new Hash(buf);

      Label.$Proxy val = null;
      if (in.readBoolean()) {
        String coreName = in.readUTF();
        long onum = in.readLong();

        Core core = client.getLocalCore();
        if (!ONumConstants.isGlobalConstant(onum)) {
          core = client.getCore(coreName);
        }

        val = new Label.$Proxy(core, onum);
      }

      creates.put(key, val);
    }

    // Read updates.
    size = in.readInt();
    for (int i = 0; i < size; i++) {
      byte[] buf = new byte[in.readInt()];
      in.readFully(buf);
      Hash key = new Hash(buf);

      byte[] iv = new byte[in.readInt()];
      in.readFully(iv);

      byte[] data = new byte[in.readInt()];
      in.readFully(data);

      updates.put(key, new Pair<byte[], byte[]>(iv, data));
    }
  }

  /**
   * Determines whether this map has a "create" entry for the given object.
   */
  public boolean containsCreate($Proxy proxy) {
    if (creates.size() == 0) return false;
    
    try {
      return creates.containsKey(hash(proxy));
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    }
  }

  public Label getCreate($Proxy proxy) {
    try {
      return creates.get(hash(proxy));
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    }
  }

  public RemoteClient getUpdate($Proxy proxy) {
    // First, check the cache.
    if (readCache.containsKey(proxy)) return readCache.get(proxy);
    if (updates.isEmpty()) return null;

    RemoteClient result = slowLookup(proxy, getKey(proxy));
    readCache.put(proxy, result);
    return result;
  }

  /**
   * This version of the lookup avoids having to fetch the proxy to determine
   * its label.
   * 
   * @param label
   *          the label corresponding to the given proxy.
   */
  public RemoteClient getUpdate($Proxy proxy, Label label) {
    if (readCache.containsKey(proxy)) return readCache.get(proxy);
    if (updates.isEmpty()) return null;

    RemoteClient result = slowLookup(proxy, getKey(label));
    readCache.put(proxy, result);
    return result;
  }

  private RemoteClient slowLookup($Proxy proxy, byte[] encryptKey) {
    try {
      Hash mapKey = hash(proxy, encryptKey);
      Pair<byte[], byte[]> encHost = updates.get(mapKey);

      if (encHost == null) return null;

      Cipher cipher =
          Crypto.cipherInstance(Cipher.DECRYPT_MODE, encryptKey, encHost.first);
      String hostname = new String(cipher.doFinal(encHost.second));
      RemoteClient result = Client.getClient().getClient(hostname);

      if (!isValidWriter(result, proxy)) {
        throw new AbortException("Invalid update map entry found.");
      }

      return result;
    } catch (GeneralSecurityException e) {
      throw new InternalError(e);
    }
  }

  private boolean isValidWriter(RemoteClient client, $Proxy proxy) {
    // XXX TODO
    return true;
  }

  public void put($Proxy proxy, Label keyObject) {
    // Don't put in entries for global constants or objects on local core.
    if (ONumConstants.isGlobalConstant(proxy.$getOnum())
        || proxy.$getCore() instanceof LocalCore) return;

    try {
      creates.put(hash(proxy), keyObject);
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    }
  }

  public void put($Proxy proxy, RemoteClient client) {
    // Don't put in entries for global constants or objects on local core.
    if (ONumConstants.isGlobalConstant(proxy.$getOnum())
        || proxy.$getCore() instanceof LocalCore) return;

    writeCache.put(proxy, new Pair<$Proxy, RemoteClient>(proxy, client));
    readCache.put(proxy, client);
  }

  /**
   * Puts all the entries from the given map into this map.
   */
  public void putAll(UpdateMap map) {
    this.creates.putAll(map.creates);

    if (map.updates.isEmpty()) return;

    flushWriteCache();
    map.flushWriteCache();
    this.updates.putAll(map.updates);
    this.readCache.clear();

    if (map.version > version)
      version = map.version + 1;
    else version++;
  }

  private void flushWriteCache() {
    for (LongKeyMap<Pair<$Proxy, RemoteClient>> entry : writeCache) {
      for (Pair<$Proxy, RemoteClient> val : entry.values()) {
        slowPut(val.first, val.second);
      }
    }

    writeCache.clear();
  }

  private void slowPut($Proxy proxy, RemoteClient client) {
    try {
      byte[] encryptKey = getKey(proxy);
      Hash mapKey = hash(proxy, encryptKey);
      byte[] iv = Crypto.makeIV();

      Cipher cipher =
          Crypto.cipherInstance(Cipher.ENCRYPT_MODE, encryptKey, iv);
      Pair<byte[], byte[]> encHost =
          new Pair<byte[], byte[]>(iv, cipher.doFinal(client.name.getBytes()));
      updates.put(mapKey, encHost);
    } catch (GeneralSecurityException e) {
      throw new InternalError(e);
    }
  }

  private Hash hash($Proxy proxy) throws NoSuchAlgorithmException {
    return hash(proxy, null);
  }

  /**
   * Given a proxy and an encryption key, hashes the object location and the
   * key.
   */
  private Hash hash($Proxy proxy, byte[] key)
      throws NoSuchAlgorithmException {
    MessageDigest md5 = MessageDigest.getInstance(ALG_HASH);
    Core core = proxy.$getCore();
    long onum = proxy.$getOnum();

    md5.update(core.name().getBytes());
    md5.update((byte) onum);
    md5.update((byte) (onum >>> 8));
    md5.update((byte) (onum >>> 16));
    md5.update((byte) (onum >>> 24));
    md5.update((byte) (onum >>> 32));
    md5.update((byte) (onum >>> 40));
    md5.update((byte) (onum >>> 48));
    md5.update((byte) (onum >>> 56));

    if (key != null) md5.update(key);

    return new Hash(md5.digest());
  }

  /**
   * Returns a byte array containing the symmetric encryption key protecting the
   * given object. If the object is public, null is returned.
   */
  private byte[] getKey($Proxy proxy) {
    return getKey(proxy.get$label());
  }

  /**
   * Returns a byte array containing the symmetric encryption key protecting
   * given label. If the label is not protected with such a key (e.g., the label
   * is publicly readable), then null is returned.
   */
  private byte[] getKey(Label label) {
    SecretKeyObject keyObject = label.keyObject();
    if (keyObject == null) return null;
    return keyObject.getKey().getEncoded();
  }

  public void write(DataOutput out) throws IOException {
    flushWriteCache();

    // Write creates.
    out.writeInt(creates.size());
    for (Map.Entry<Hash, Label> entry : creates.entrySet()) {
      Hash key = entry.getKey();
      Label value = entry.getValue();

      out.writeInt(key.hash.length);
      out.write(key.hash);

      if (value != null) {
        out.writeBoolean(true);
        out.writeUTF(value.$getCore().name());
        out.writeLong(value.$getOnum());
      } else out.writeBoolean(false);
    }

    // Write updates.
    out.writeInt(updates.size());
    for (Map.Entry<Hash, Pair<byte[], byte[]>> entry : updates.entrySet()) {
      Hash key = entry.getKey();
      Pair<byte[], byte[]> val = entry.getValue();

      out.writeInt(key.hash.length);
      out.write(key.hash);
      out.writeInt(val.first.length);
      out.write(val.first);
      out.writeInt(val.second.length);
      out.write(val.second);
    }
  }

  /**
   * A byte-array wrapper.  This is here because Java is stupid.
   */
  private static class Hash {
    private byte[] hash;
    private int hashCode;

    Hash(byte[] hash) {
      this.hash = hash;
      this.hashCode = (hash[0] << 24) | (hash[1] << 16) | (hash[2] << 8) | hash[3];
    }

    @Override
    public boolean equals(Object obj) {
      Hash other = (Hash) obj;
      
      if (hashCode != other.hashCode) return false;

      for (int i = 4; i < hash.length; i++)
        if (hash[i] != other.hash[i]) return false;

      return true;
    }

    @Override
    public int hashCode() {
      return hashCode;
    }
  }
}
