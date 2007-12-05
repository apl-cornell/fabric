package fabric.core;

import java.io.*;

import fabric.client.Core;
import fabric.common.InternalError;
import fabric.common.Policy;
import fabric.lang.Object.$Impl;

/**
 * <code>$Impl</code> objects are stored on cores in serialized form as
 * <code>SerializedObject</code>s.
 */
public final class SerializedObject implements Serializable {
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

    try {
      ObjectOutputStream dos = new ObjectOutputStream(bos);
      dos.writeUTF(c.getName());
      obj.$serialize(dos);
      dos.flush();
      return bos.toByteArray();
    } catch (IOException e) {
    }

    return null;
  }

  /**
   * Deserializes the object.
   * 
   * @param core
   *          The core on which the object lives.
   * @param onum
   *          The object's onum.
   */
  public $Impl deserialize(Core core, long onum) throws ClassNotFoundException {
    ByteArrayInputStream bis = new ByteArrayInputStream(object);

    try {
      ObjectInputStream dis = new ObjectInputStream(bis);
      Class<?> c = Class.forName(dis.readUTF());
      $Impl obj =
          ($Impl) c.getConstructor(Core.class, long.class, int.class,
              Policy.class, ObjectInput.class).newInstance(core, onum, version,
              policy, dis);
      return obj;
    } catch (ClassNotFoundException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalError(e);
    }
  }

  /**
   * This interface is used solely for the deserialization constructor of
   * $Impls. Its purpose is to make sure the constructor signature does not
   * conflict with a user written constructor that also takes a
   * java.io.ObjectInput as its only argument.
   * 
   * @author xinz
   */
  public interface ObjectInput extends java.io.ObjectInput {
  }

  private static class ObjectInputStream extends java.io.ObjectInputStream
      implements ObjectInput {

    public ObjectInputStream(InputStream in) throws IOException {
      super(in);
    }

  }

}
