package fabric.worker.remote;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import fabric.common.Crypto;
import fabric.common.FastSerializable;
import fabric.common.ONumConstants;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.lang.Object._Proxy;
import fabric.lang.security.Label;
import fabric.lang.security.SecretKeyObject;
import fabric.worker.LocalStore;
import fabric.worker.Store;
import fabric.worker.TransactionAbortingException;
import fabric.worker.Worker;

/**
 * Maps proxies to the host that holds the most up-to-date copy of that object.
 * Also maps proxies of newly created objects to their corresponding labels.
 */
public class WriterMap implements FastSerializable {

  /**
   * The transaction ID for the topmost transaction that this map is a part of.
   */
  private final long tid;

  /**
   * Maps hash(oid) to Label. These are the "create" entries.
   */
  private Map<Hash, Label> creates;

  /**
   * Maps hash(oid, object key) to (iv, enc(hostname, object key, iv)).
   * TODO include top-level TID in hash.
   */
  private Map<Hash, Pair<byte[], byte[]>> writers;

  /**
   * Cache for "writers" entries and non-entries that have been discovered.
   */
  private OidKeyHashMap<RemoteWorker> readCache;

  /**
   * Cache for "writers" entries that haven't been encrypted yet.
   */
  private OidKeyHashMap<Pair<_Proxy, RemoteWorker>> writeCache;

  public int version;

  /**
   * @param tid
   *          the transaction ID for the topmost transaction that this map is a
   *          part of.
   */
  public WriterMap(long tid) {
    this.creates = new HashMap<>();
    this.writers = new HashMap<>();
    this.readCache = new OidKeyHashMap<>();
    this.writeCache = new OidKeyHashMap<>();
    this.version = 0;
    this.tid = tid;
  }

  /**
   * Copy constructor.
   */
  public WriterMap(WriterMap map) {
    this.creates = new HashMap<>(map.creates);
    this.writers = new HashMap<>(map.writers);
    this.readCache = new OidKeyHashMap<>(map.readCache);
    this.writeCache = new OidKeyHashMap<>(map.writeCache);
    this.version = map.version;
    this.tid = map.tid;
  }

  /**
   * Deserialization constructor.
   */
  public WriterMap(DataInput in) throws IOException {
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

    // Read writers.
    size = in.readInt();
    for (int i = 0; i < size; i++) {
      byte[] buf = new byte[in.readInt()];
      in.readFully(buf);
      Hash key = new Hash(buf);

      byte[] iv = new byte[in.readInt()];
      in.readFully(iv);

      byte[] data = new byte[in.readInt()];
      in.readFully(data);

      writers.put(key, new Pair<>(iv, data));
    }
  }

  /**
   * Determines whether this map has a "create" entry for the given object.
   */
  public boolean containsCreate(_Proxy proxy) {
    if (creates.isEmpty()) return false;
    return creates.containsKey(hash(proxy));
  }

  public Label getCreate(_Proxy proxy) {
    if (creates.isEmpty()) return null;
    return creates.get(hash(proxy));
  }

  public RemoteWorker getWriter(_Proxy proxy) {
    // First, check the cache.
    if (readCache.containsKey(proxy)) return readCache.get(proxy);
    if (writers.isEmpty()) return null;

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
  public RemoteWorker getWriter(_Proxy proxy, Label label) {
    if (readCache.containsKey(proxy)) return readCache.get(proxy);
    if (writers.isEmpty()) return null;

    RemoteWorker result = slowLookup(proxy, getKey(label));
    readCache.put(proxy, result);
    return result;
  }

  private RemoteWorker slowLookup(_Proxy proxy, byte[] encryptKey) {
    try {
      Hash mapKey = hash(proxy, encryptKey);
      Pair<byte[], byte[]> encHost = writers.get(mapKey);

      if (encHost == null) return null;

      Cipher cipher =
          Crypto.cipherInstance(Cipher.DECRYPT_MODE, encryptKey, encHost.first);
      String hostname = new String(cipher.doFinal(encHost.second));
      RemoteWorker result = Worker.getWorker().getWorker(hostname);

      if (!isValidWriter(result, proxy)) {
        throw new TransactionAbortingException(
            "Invalid writer map entry found.");
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
        || proxy.$getStore() instanceof LocalStore)
      return;

    creates.put(hash(proxy), keyObject);
  }

  public void put(_Proxy proxy, RemoteWorker worker) {
    // Don't put in entries for global constants or objects on local store.
    if (ONumConstants.isGlobalConstant(proxy.$getOnum())
        || proxy.$getStore() instanceof LocalStore)
      return;

    writeCache.put(proxy, new Pair<>(proxy, worker));
    readCache.put(proxy, worker);
  }

  /**
   * Puts all the entries from the given map into this map.
   */
  public void putAll(WriterMap map) {
    this.creates.putAll(map.creates);

    if (map.writers.isEmpty()) return;

    flushWriteCache();
    map.flushWriteCache();
    this.writers.putAll(map.writers);
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
          new Pair<>(iv, cipher.doFinal(worker.name.getBytes()));
      writers.put(mapKey, encHost);
    } catch (GeneralSecurityException e) {
      throw new InternalError(e);
    }
  }

  private Hash hash(_Proxy proxy) {
    return hash(proxy, null);
  }

  /**
   * Given a proxy and an encryption key, hashes the object location with the
   * transaction ID and the key.
   */
  private Hash hash(_Proxy proxy, byte[] key) {
    MessageDigest digest = Crypto.digestInstance();
    Store store = proxy.$getStore();
    long onum = proxy.$getOnum();

    digest.update(store.name().getBytes());
    digest.update((byte) onum);
    digest.update((byte) (onum >>> 8));
    digest.update((byte) (onum >>> 16));
    digest.update((byte) (onum >>> 24));
    digest.update((byte) (onum >>> 32));
    digest.update((byte) (onum >>> 40));
    digest.update((byte) (onum >>> 48));
    digest.update((byte) (onum >>> 56));

    digest.update((byte) tid);
    digest.update((byte) (tid >>> 8));
    digest.update((byte) (tid >>> 16));
    digest.update((byte) (tid >>> 24));
    digest.update((byte) (tid >>> 32));
    digest.update((byte) (tid >>> 40));
    digest.update((byte) (tid >>> 48));
    digest.update((byte) (tid >>> 56));

    if (key != null) digest.update(key);

    return new Hash(digest.digest());
  }

  /**
   * Returns a byte array containing the symmetric encryption key protecting the
   * given object. If the object is public, null is returned.
   */
  private byte[] getKey(_Proxy proxy) {
    return getKey(proxy.get$$updateLabel());
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

  @Override
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

    // Write writers.
    out.writeInt(writers.size());
    for (Map.Entry<Hash, Pair<byte[], byte[]>> entry : writers.entrySet()) {
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
