package fabric.worker.remote;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.crypto.Cipher;

import fabric.lang.security.Label;

import fabric.worker.AbortException;
import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.worker.LocalStore;
import fabric.common.*;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.lang.SecretKeyObject;
import fabric.lang.Object._Proxy;

/**
 * Maps proxies to the host that holds the most up-to-date copy of that object.
 * Also maps proxies of newly created objects to their corresponding labels.
 */
public class UpdateMap implements FastSerializable {

  private static final String ALG_HASH = "MD5";

  /**
   * The transaction ID for the topmost transaction that this map is a part of.
   */
  private final long tid;

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
  private OidKeyHashMap<RemoteWorker> readCache;

  /**
   * Cache for "update" entries that haven't been encrypted yet.
   */
  private OidKeyHashMap<Pair<_Proxy, RemoteWorker>> writeCache;

  public int version;

  /**
   * @param tid
   *          the transaction ID for the topmost transaction that this map is a
   *          part of.
   */
  public UpdateMap(long tid) {
    this.creates = new HashMap<Hash, Label>();
    this.updates = new HashMap<Hash, Pair<byte[], byte[]>>();
    this.readCache = new OidKeyHashMap<RemoteWorker>();
    this.writeCache = new OidKeyHashMap<Pair<_Proxy, RemoteWorker>>();
    this.version = 0;
    this.tid = tid;
  }

  /**
   * Copy constructor.
   */
  public UpdateMap(UpdateMap map) {
    this.creates = new HashMap<Hash, Label>(map.creates);
    this.updates = new HashMap<Hash, Pair<byte[], byte[]>>(map.updates);
    this.readCache = new OidKeyHashMap<RemoteWorker>(map.readCache);
    this.writeCache =
        new OidKeyHashMap<Pair<_Proxy, RemoteWorker>>(map.writeCache);
    this.version = map.version;
    this.tid = map.tid;
  }

  /**
   * Deserialization constructor.
   */
  public UpdateMap(DataInput in) throws IOException {
    this(in.readLong());
    this.version = -1;

    Worker worker = Worker.getWorker();

    // Read creates.
    int size = in.readInt();
    for (int i = 0; i < size; i++) {
      byte[] buf = new byte[in.readInt()];
      in.readFully(buf);

      Hash key = new Hash(buf);

      Label._Proxy val = null;
      if (in.readBoolean()) {
        String storeName = in.readUTF();
        long onum = in.readLong();

        Store store = worker.getLocalStore();
        if (!ONumConstants.isGlobalConstant(onum)) {
          store = worker.getStore(storeName);
        }

        val = new Label._Proxy(store, onum);
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
  public boolean containsCreate(_Proxy proxy) {
    if (creates.isEmpty()) return false;

    try {
      return creates.containsKey(hash(proxy));
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    }
  }

  public Label getCreate(_Proxy proxy) {
    if (creates.isEmpty()) return null;

    try {
      return creates.get(hash(proxy));
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    }
  }

  public RemoteWorker getUpdate(_Proxy proxy) {
    // First, check the cache.
    if (readCache.containsKey(proxy)) return readCache.get(proxy);
    if (updates.isEmpty()) return null;

    RemoteWorker result = slowLookup(proxy, getKey(proxy));
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
  public RemoteWorker getUpdate(_Proxy proxy, Label label) {
    if (readCache.containsKey(proxy)) return readCache.get(proxy);
    if (updates.isEmpty()) return null;

    RemoteWorker result = slowLookup(proxy, getKey(label));
    readCache.put(proxy, result);
    return result;
  }

  private RemoteWorker slowLookup(_Proxy proxy, byte[] encryptKey) {
    try {
      Hash mapKey = hash(proxy, encryptKey);
      Pair<byte[], byte[]> encHost = updates.get(mapKey);

      if (encHost == null) return null;

      Cipher cipher =
          Crypto.cipherInstance(Cipher.DECRYPT_MODE, encryptKey, encHost.first);
      String hostname = new String(cipher.doFinal(encHost.second));
      RemoteWorker result = Worker.getWorker().getWorker(hostname);

      if (!isValidWriter(result, proxy)) {
        throw new AbortException("Invalid update map entry found.");
      }

      return result;
    } catch (GeneralSecurityException e) {
      throw new InternalError(e);
    }
  }

  private boolean isValidWriter(RemoteWorker worker, _Proxy proxy) {
    // XXX TODO
    return true;
  }

  public void put(_Proxy proxy, Label keyObject) {
    // Don't put in entries for global constants or objects on local store.
    if (ONumConstants.isGlobalConstant(proxy.$getOnum())
        || proxy.$getStore() instanceof LocalStore) return;

    try {
      creates.put(hash(proxy), keyObject);
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    }
  }

  public void put(_Proxy proxy, RemoteWorker worker) {
    // Don't put in entries for global constants or objects on local store.
    if (ONumConstants.isGlobalConstant(proxy.$getOnum())
        || proxy.$getStore() instanceof LocalStore) return;

    writeCache.put(proxy, new Pair<_Proxy, RemoteWorker>(proxy, worker));
    readCache.put(proxy, worker);
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
    for (LongKeyMap<Pair<_Proxy, RemoteWorker>> entry : writeCache) {
      for (Pair<_Proxy, RemoteWorker> val : entry.values()) {
        slowPut(val.first, val.second);
      }
    }

    writeCache.clear();
  }

  private void slowPut(_Proxy proxy, RemoteWorker worker) {
    try {
      byte[] encryptKey = getKey(proxy);
      Hash mapKey = hash(proxy, encryptKey);
      byte[] iv = Crypto.makeIV();

      Cipher cipher =
          Crypto.cipherInstance(Cipher.ENCRYPT_MODE, encryptKey, iv);
      Pair<byte[], byte[]> encHost =
          new Pair<byte[], byte[]>(iv, cipher.doFinal(worker.name.getBytes()));
      updates.put(mapKey, encHost);
    } catch (GeneralSecurityException e) {
      throw new InternalError(e);
    }
  }

  private Hash hash(_Proxy proxy) throws NoSuchAlgorithmException {
    return hash(proxy, null);
  }

  /**
   * Given a proxy and an encryption key, hashes the object location with the
   * transaction ID and the key.
   */
  private Hash hash(_Proxy proxy, byte[] key) throws NoSuchAlgorithmException {
    MessageDigest md5 = MessageDigest.getInstance(ALG_HASH);
    Store store = proxy.$getStore();
    long onum = proxy.$getOnum();

    md5.update(store.name().getBytes());
    md5.update((byte) onum);
    md5.update((byte) (onum >>> 8));
    md5.update((byte) (onum >>> 16));
    md5.update((byte) (onum >>> 24));
    md5.update((byte) (onum >>> 32));
    md5.update((byte) (onum >>> 40));
    md5.update((byte) (onum >>> 48));
    md5.update((byte) (onum >>> 56));

    md5.update((byte) tid);
    md5.update((byte) (tid >>> 8));
    md5.update((byte) (tid >>> 16));
    md5.update((byte) (tid >>> 24));
    md5.update((byte) (tid >>> 32));
    md5.update((byte) (tid >>> 40));
    md5.update((byte) (tid >>> 48));
    md5.update((byte) (tid >>> 56));

    if (key != null) md5.update(key);

    return new Hash(md5.digest());
  }

  /**
   * Returns a byte array containing the symmetric encryption key protecting the
   * given object. If the object is public, null is returned.
   */
  private byte[] getKey(_Proxy proxy) {
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

    // Write tid.
    out.writeLong(tid);

    // Write creates.
    out.writeInt(creates.size());
    for (Map.Entry<Hash, Label> entry : creates.entrySet()) {
      Hash key = entry.getKey();
      Label value = entry.getValue();

      out.writeInt(key.hash.length);
      out.write(key.hash);

      if (value != null) {
        out.writeBoolean(true);
        out.writeUTF(value.$getStore().name());
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
   * A byte-array wrapper. This is here because Java is stupid.
   */
  private static class Hash {
    private byte[] hash;
    private int hashCode;

    Hash(byte[] hash) {
      this.hash = hash;
      this.hashCode =
          (hash[0] << 24) | (hash[1] << 16) | (hash[2] << 8) | hash[3];
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
