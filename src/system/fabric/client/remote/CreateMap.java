package fabric.client.remote;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import fabric.client.Client;
import fabric.client.Core;
import fabric.common.InternalError;
import jif.lang.Label;
import fabric.lang.Object.$Proxy;

/**
 * Maps proxies to their corresponding Labels.
 */
public class CreateMap {

  private static String ALG_HASH = "MD5";

  /**
   * Maps md5(oid) to Label
   */
  private Map<byte[], Label> map;

  public CreateMap() {
    this.map = new HashMap<byte[], Label>();
  }

  /**
   * Copy constructor.
   */
  public CreateMap(CreateMap map) {
    this.map = new HashMap<byte[], Label>(map.map);
  }

  /**
   * Deserialization constructor.
   */
  public CreateMap(DataInput in) throws IOException {
    this();

    Client client = Client.getClient();
    int size = in.readInt();
    for (int i = 0; i < size; i++) {
      byte[] key = new byte[in.readInt()];
      in.readFully(key);

      Label.$Proxy val = null;
      if (in.readBoolean()) {
        Core core = client.getCore(in.readUTF());
        long onum = in.readLong();
        val = new Label.$Proxy(core, onum);
      }

      map.put(key, val);
    }
  }
  
  public boolean containsKey($Proxy proxy) {
    try {
      return map.containsKey(hash(proxy));
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    }
  }

  public Label lookup($Proxy proxy) {
    try {
      return map.get(hash(proxy));
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    }
  }

  public void put($Proxy proxy, Label keyObject) {
    try {
      map.put(hash(proxy), keyObject);
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    }
  }

  private byte[] hash($Proxy proxy) throws NoSuchAlgorithmException {
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

    return md5.digest();
  }

  public void write(DataOutput out) throws IOException {
    out.writeInt(map.size());
    for (Map.Entry<byte[], Label> entry : map.entrySet()) {
      byte[] key = entry.getKey();
      Label value = entry.getValue();

      out.writeInt(key.length);
      out.write(key);
      if (value != null) {
        out.writeBoolean(true);
        out.writeUTF(value.$getCore().name());
        out.writeLong(value.$getOnum());
      } else out.writeBoolean(false);
    }
  }
}
