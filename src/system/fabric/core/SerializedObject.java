package fabric.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import fabric.common.InternalError;
import fabric.common.Policy;
import fabric.lang.Object.$Impl;

/**
 * <code>$Impl</code> objects are stored on cores in serialized form as
 * <code>SerializedObject</code>s.
 */
public class SerializedObject implements Serializable {
  private byte[] object;

  private long onum;

  private long[] related;

  private int version;

  private Policy policy;

  public SerializedObject($Impl obj) {
    // TODO Somehow locate all of the co-located references.
    this(obj, null);
  }

  public SerializedObject($Impl obj, long[] related) {
    this.object = serialize(obj);
    this.related = related;
    this.onum = obj.$getOnum();
    this.policy = obj.$getPolicy();
  }

  public $Impl getObject() throws ClassNotFoundException {
    $Impl result = deserialize(object);
    // TODO Update header information.
    result.$version = version;
    return result;
  }

  public long getOnum() {
    return onum;
  }

  public Policy getPolicy() {
    return policy;
  }

  public long[] getRelated() {
    if (this.related == null) return new long[0];
    return this.related;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(final int version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return onum + "v" + version;
  }

  protected byte[] serialize($Impl obj) {
    Class<?> c = obj.getClass();
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(bos);
    
    try {
      dos.writeUTF(c.getName());
      dos.writeInt(obj.$numFields());
      obj.$serializeHeader(dos);
      obj.$serialize(dos);
      return bos.toByteArray();
    } catch (IOException e) {}
    
    return null;
  }
  
  protected $Impl deserialize(byte[] buf) throws ClassNotFoundException {
    ByteArrayInputStream bis = new ByteArrayInputStream(buf);
    DataInputStream dis = new DataInputStream(bis);
    
    try {
      Class<?> c = Class.forName(dis.readUTF());
      int numFields = dis.readInt();
      dis.skip(numFields); // no need to actually read header information
      $Impl obj = ($Impl) c.getConstructor(DataInput.class).newInstance(dis);
      return obj;
    } catch (ClassNotFoundException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalError(e);
    }
  }
  
  /**
   * This interface is used solely for the deserialization constructor of
   * $Impls. It's purpose is to make sure the constructor signature does not
   * conflict with a user written constructor that also takes a
   * java.io.DataInput as its only argument.
   * 
   * @author xinz
   */
  public interface DataInput extends java.io.DataInput {}
  
  private class DataInputStream extends java.io.DataInputStream 
      implements DataInput {

    public DataInputStream(InputStream in) {
      super(in);
    }
    
  }

}
