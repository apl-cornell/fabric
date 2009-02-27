package fabric.client.remote;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.LocalCore;
import fabric.common.InternalError;
import fabric.common.ONumConstants;
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
  private Map<List<Byte>, Label> map;

  public CreateMap() {
    this.map = new HashMap<List<Byte>, Label>();
  }

  /**
   * Copy constructor.
   */
  public CreateMap(CreateMap map) {
    this.map = new HashMap<List<Byte>, Label>(map.map);
  }

  /**
   * Deserialization constructor.
   */
  public CreateMap(DataInput in) throws IOException {
    this();

    Client client = Client.getClient();
    int size = in.readInt();
    for (int i = 0; i < size; i++) {
      byte[] buf = new byte[in.readInt()];
      in.readFully(buf);

      List<Byte> key = new ArrayList<Byte>(buf.length);
      for (byte b : buf)
        key.add(b);

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
    // Don't put in entries for global constants or objects on local core.
    if (ONumConstants.isGlobalConstant(proxy.$getOnum())
        || proxy.$getCore() instanceof LocalCore) return;
    
    try {
      map.put(hash(proxy), keyObject);
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    }
  }

  public void putAll(CreateMap map) {
    this.map.putAll(map.map);
  }

  private List<Byte> hash($Proxy proxy) throws NoSuchAlgorithmException {
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

    byte[] buf = md5.digest();

    List<Byte> result = new ArrayList<Byte>(buf.length);
    for (byte b : buf)
      result.add(b);

    return result;
  }

  public void write(DataOutput out) throws IOException {
    out.writeInt(map.size());
    for (Map.Entry<List<Byte>, Label> entry : map.entrySet()) {
      List<Byte> key = entry.getKey();
      Label value = entry.getValue();

      out.writeInt(key.size());
      for (byte b : key)
        out.writeByte(b);

      if (value != null) {
        out.writeBoolean(true);
        out.writeUTF(value.$getCore().name());
        out.writeLong(value.$getOnum());
      } else out.writeBoolean(false);
    }
  }
}
