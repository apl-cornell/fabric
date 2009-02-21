package fabric.client.remote;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import fabric.client.Client;
import fabric.client.Core;
import fabric.common.Crypto;
import fabric.common.InternalError;
import fabric.common.Pair;
import fabric.lang.KeyObject;
import fabric.lang.Object.$Proxy;

/**
 * Maps proxies to the host that holds the most up-to-date copy of that object.
 */
public class UpdateMap {
  /**
   * Maps md5(oid, object key) to (iv, enc(hostname, object key, iv)).
   */
  private Map<byte[], Pair<byte[], byte[]>> map;

  private static String ALG_HASH = "MD5";

  public UpdateMap() {
    this.map = new HashMap<byte[], Pair<byte[], byte[]>>();
  }

  public RemoteClient lookup($Proxy proxy) {
    try {
      byte[] encryptKey = getKey(proxy);
      byte[] mapKey = hash(proxy, encryptKey);
      Pair<byte[], byte[]> encHost = map.get(mapKey);

      Cipher cipher =
          Crypto.cipherInstance(Cipher.DECRYPT_MODE, encryptKey, encHost.first);
      String hostname = new String(cipher.doFinal(encHost.second));
      return Client.getClient().getClient(hostname);
    } catch (GeneralSecurityException e) {
      throw new InternalError(e);
    }
  }

  public void put($Proxy proxy, RemoteClient client) {
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
   * Puts all the entries from the given map into this map.
   */
  public void putAll(UpdateMap map) {
    this.map.putAll(map.map);
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
}
