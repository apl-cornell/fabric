package fabric.client.remote;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.OidKeyHashMap;
import fabric.common.Crypto;
import fabric.common.FastSerializable;
import fabric.common.InternalError;
import fabric.common.Pair;
import fabric.common.util.LongKeyMap;
import fabric.lang.KeyObject;
import fabric.lang.Object.$Proxy;

/**
 * Maps proxies to the host that holds the most up-to-date copy of that object.
 */
public class UpdateMap implements FastSerializable {
  /**
   * Maps md5(oid, object key) to (iv, enc(hostname, object key, iv)).
   */
  private Map<byte[], Pair<byte[], byte[]>> map;

  /**
   * Cache for map entries and non-entries that have been discovered.
   */
  private OidKeyHashMap<RemoteClient> readCache;

  /**
   * Cache for map entries that haven't been encrypted yet.
   */
  private OidKeyHashMap<Pair<$Proxy, RemoteClient>> writeCache;

  private static String ALG_HASH = "MD5";

  public UpdateMap() {
    this.map = new HashMap<byte[], Pair<byte[], byte[]>>();
    this.readCache = new OidKeyHashMap<RemoteClient>();
    this.writeCache = new OidKeyHashMap<Pair<$Proxy, RemoteClient>>();
  }

  /**
   * Deserialization constructor.
   */
  public UpdateMap(DataInput in) throws IOException {
    this();
    int size = in.readInt();
    for (int i = 0; i < size; i++) {
      byte[] key = new byte[in.readInt()];
      in.readFully(key);
      
      byte[] iv = new byte[in.readInt()];
      in.readFully(iv);
      
      byte[] data = new byte[in.readInt()];
      in.readFully(data);
      
      map.put(key, new Pair<byte[], byte[]>(iv, data));
    }
  }

  public RemoteClient lookup($Proxy proxy) {
    // First, check the cache.
    if (readCache.containsKey(proxy)) return readCache.get(proxy);

    RemoteClient result = slowLookup(proxy);
    readCache.put(proxy, result);
    return result;
  }

  private RemoteClient slowLookup($Proxy proxy) {
    try {
      byte[] encryptKey = getKey(proxy);
      byte[] mapKey = hash(proxy, encryptKey);
      Pair<byte[], byte[]> encHost = map.get(mapKey);

      if (encHost == null) return null;

      Cipher cipher =
          Crypto.cipherInstance(Cipher.DECRYPT_MODE, encryptKey, encHost.first);
      String hostname = new String(cipher.doFinal(encHost.second));
      return Client.getClient().getClient(hostname);
    } catch (GeneralSecurityException e) {
      throw new InternalError(e);
    }
  }

  public void put($Proxy proxy, RemoteClient client) {
    writeCache.put(proxy, new Pair<$Proxy, RemoteClient>(proxy, client));
    readCache.put(proxy, client);
  }

  /**
   * Puts all the entries from the given map into this map.
   */
  public void putAll(UpdateMap map) {
    if (map.map.isEmpty()) return;

    flushWriteCache();
    this.map.putAll(map.map);
    this.readCache.clear();
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
      byte[] mapKey = hash(proxy, encryptKey);
      byte[] iv = Crypto.makeIV();

      Cipher cipher =
          Crypto.cipherInstance(Cipher.ENCRYPT_MODE, encryptKey, iv);
      Pair<byte[], byte[]> encHost =
          new Pair<byte[], byte[]>(iv, cipher.doFinal(client.name.getBytes()));
      map.put(mapKey, encHost);
    } catch (GeneralSecurityException e) {
      throw new InternalError(e);
    }
  }

  /**
   * Given a proxy and an encryption key, hashes the object location and the
   * key.
   */
  private byte[] hash($Proxy proxy, byte[] key) throws NoSuchAlgorithmException {
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
    md5.update(key);

    return md5.digest();
  }

  /**
   * Returns a byte array containing the symmetric encryption key protecting the
   * given object. If the object is public, null is returned.
   */
  private byte[] getKey($Proxy proxy) {
    KeyObject keyObject = proxy.get$label().keyObject();
    if (keyObject == null) return null;

    return keyObject.getKey().getEncoded();
  }

  public void write(DataOutput out) throws IOException {
    flushWriteCache();

    out.writeInt(map.size());
    for (Map.Entry<byte[], Pair<byte[], byte[]>> entry : map.entrySet()) {
      byte[] key = entry.getKey();
      Pair<byte[], byte[]> val = entry.getValue();

      out.writeInt(key.length);
      out.write(key);
      out.writeInt(val.first.length);
      out.write(val.first);
      out.writeInt(val.second.length);
      out.write(val.second);
    }
  }
}
