package fabric.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import fabric.client.Core;
import fabric.common.FastSerializable;
import fabric.common.InternalError;
import fabric.common.Pair;
import fabric.common.Surrogate;
import fabric.lang.Object.$Impl;
import fabric.lang.auth.Label;

/**
 * <code>$Impl</code> objects are stored on cores in serialized form as
 * <code>SerializedObject</code>s.
 */
public final class SerializedObject implements FastSerializable {
  
  public static enum RefTypeEnum {
    NULL, INLINE, ONUM, REMOTE
  }

  /**
   * The core-specific object number for this object.
   */
  private long onum;

  /**
   * The name of the class's object. XXX This should be an OID referencing the
   * appropriate class object.
   */
  private final String className;

  /**
   * The object's version number.
   */
  private int version;

  /**
   * Onum of the object's security label. (Label must be on this core.)
   */
  private final long label;

  /**
   * The object's primitive field and inlined object data.
   */
  private final byte[] serializedData;

  /**
   * The type (intra-core, inter-core, inline) of each reference field.
   */
  private final List<RefTypeEnum> refTypes;

  /**
   * The onums representing the intra-core references in this object. This is
   * public for debugging purposes.
   * 
   * @see fabric.client.debug.ObjectCount#countReachable
   */
  private final List<Long> intracoreRefs;

  /**
   * Global object names representing the inter-core references in this object.
   * Before storing any <code>SerializedObject</code>, the core should
   * swizzle these references into intra-core references to surrogates.
   */
  private final List<Pair<String, Long>> intercoreRefs;

  /**
   * Creates a serialized representation of the given object. This should only
   * be used by GenMap and for debugging (client.debug.*).
   * 
   * @param obj
   *                The object to serialize.
   */
  @Deprecated
  public SerializedObject($Impl obj) {
    this.onum = obj.$getOnum();
    this.className = obj.getClass().getName();
    this.label = obj.$getLabel().$getOnum();
    this.version = obj.$version;

    ByteArrayOutputStream serializedData = new ByteArrayOutputStream();
    this.refTypes = new ArrayList<RefTypeEnum>();
    this.intracoreRefs = new ArrayList<Long>();
    this.intercoreRefs = new ArrayList<Pair<String, Long>>();

    try {
      ObjectOutputStream oos = new ObjectOutputStream(serializedData);
      obj
          .$serialize(oos, this.refTypes, this.intracoreRefs,
              this.intercoreRefs);
      oos.flush();
    } catch (IOException e) {
      throw new InternalError("Unexpected I/O error.", e);
    }
    this.serializedData = serializedData.toByteArray();
  }

  /**
   * Creates a serialized representation of a surrogate object.
   * 
   * @param onum
   *                The local object number for the surrogate.
   * @param label
   *                The policy for the surrogate.
   * @param remoteRef
   *                The name of the remote object being referred to.
   */
  SerializedObject(long onum, Label label, Pair<String, Long> remoteRef) {
    this.onum = onum;
    this.className = Surrogate.class.getName();
    this.label = label.$getOnum();

    ByteArrayOutputStream serializedData = new ByteArrayOutputStream();
    try {
      ObjectOutputStream oos = new ObjectOutputStream(serializedData);
      oos.writeUTF(remoteRef.first);
      oos.writeLong(remoteRef.second);
      oos.flush();
    } catch (IOException e) {
      throw new InternalError("Unexpected I/O error.", e);
    }

    this.serializedData = serializedData.toByteArray();
    this.refTypes = Collections.emptyList();
    this.intracoreRefs = Collections.emptyList();
    this.intercoreRefs = Collections.emptyList();
  }

  public long getOnum() {
    return onum;
  }
  
  public String getClassName() {
    return className;
  }

  public long getLabel() {
    return label;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(final int version) {
    this.version = version;
  }

  public byte[] getSerializedData() {
    return serializedData;
  }

  public List<RefTypeEnum> getRefTypes() {
    return refTypes;
  }

  public List<Long> getIntracoreRefs() {
    return intracoreRefs;
  }

  public List<Pair<String, Long>> getIntercoreRefs() {
    return intercoreRefs;
  }
  
  @Override
  public String toString() {
    return onum + "v" + version;
  }

  /**
   * Writes the given $Impl out to the given output stream. The behaviour of
   * this method should mirror write(ObjectOutputStream).
   * 
   * @see SerializedObject#write(ObjectOutputStream)
   * @see SerializedObject#readImpl(Core, ObjectInputStream)
   * @see SerializedObject#SerializedObject(ObjectInput)
   */
  public static void write($Impl impl, DataOutput out)
      throws IOException {
    // Write out the object header.
    out.writeUTF(impl.getClass().getName());
    out.writeLong(impl.$getOnum());
    out.writeInt(impl.$version);
    
    out.writeLong(impl.$getLabel().$getOnum());

    // Get the object to serialize itself into a bunch of buffers.
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    List<RefTypeEnum> refTypes = new ArrayList<RefTypeEnum>();
    List<Long> intracoreRefs = new ArrayList<Long>();
    List<Pair<String, Long>> intercoreRefs =
        new ArrayList<Pair<String, Long>>();
    impl.$serialize(oos, refTypes, intracoreRefs, intercoreRefs);
    oos.flush();

    // Write the object's contents.
    byte[] serializedData = bos.toByteArray();
    out.writeInt(serializedData.length);
    out.write(serializedData);

    out.writeInt(refTypes.size());
    for (RefTypeEnum refType : refTypes)
      out.writeByte(refType.ordinal());

    out.writeInt(intracoreRefs.size());
    for (Long onum : intracoreRefs)
      out.writeLong(onum);

    out.writeInt(intercoreRefs.size());
    for (Pair<String, Long> oid : intercoreRefs) {
      out.writeUTF(oid.first);
      out.writeLong(oid.second);
    }
  }

  /**
   * Writes this SerializedObject out to the given output stream. The behavior
   * of this method should mirror write($Impl, ObjectOutputStream).
   * 
   * @see SerializedObject#write($Impl, ObjectOutputStream)
   * @see SerializedObject#readImpl(Core, ObjectInputStream)
   * @see SerializedObject#SerializedObject(ObjectInput)
   */
  public void write(DataOutput out) throws IOException {
    // Write out the object header.
    out.writeUTF(className);
    out.writeLong(onum);
    out.writeInt(version);
    
    out.writeLong(label);

    // Write out the object's contents.
    out.writeInt(serializedData.length);
    out.write(serializedData);

    out.writeInt(refTypes.size());
    for (RefTypeEnum refType : refTypes)
      out.writeByte(refType.ordinal());

    out.writeInt(intracoreRefs.size());
    for (Long onum : intracoreRefs)
      out.writeLong(onum);

    out.writeInt(intercoreRefs.size());
    for (Pair<String, Long> oid : intercoreRefs) {
      out.writeUTF(oid.first);
      out.writeLong(oid.second);
    }
  }

  /**
   * A deserialization constructor.
   * 
   * @param in
   *                An input stream containing a serialized object.
   * @see SerializedObject#write(ObjectOutputStream)
   * @see SerializedObject#write($Impl, ObjectOutputStream)
   * @see SerializedObject#readImpl(Core, ObjectInputStream)
   */
  public SerializedObject(DataInput in) throws IOException {
    // Read the object header.
    this.className = in.readUTF();
    this.onum = in.readLong();
    this.version = in.readInt();
    
    this.label = in.readLong();

    // Read the object body.
    this.serializedData = new byte[in.readInt()];
    in.readFully(this.serializedData);

    int size = in.readInt();
    if (size == 0) {
      this.refTypes = Collections.emptyList();
    } else {
      this.refTypes = new ArrayList<RefTypeEnum>(size);
      RefTypeEnum[] refTypeEnums = RefTypeEnum.values();
      for (int i = 0; i < size; i++)
        this.refTypes.add(refTypeEnums[in.readByte()]);
    }

    size = in.readInt();
    if (size == 0) {
      this.intracoreRefs = new ArrayList<Long>(0);
    } else {
      this.intracoreRefs = new ArrayList<Long>(size);
      for (int i = 0; i < size; i++)
        this.intracoreRefs.add(in.readLong());
    }

    size = in.readInt();
    if (size == 0) {
      this.intercoreRefs = Collections.emptyList();
    } else {
      this.intercoreRefs = new ArrayList<Pair<String, Long>>(size);
      for (int i = 0; i < size; i++)
        this.intercoreRefs.add(new Pair<String, Long>(in.readUTF(), in
            .readLong()));
    }
  }

  /**
   * Reads an $Impl from the given input stream.
   * 
   * @param core
   *                The core on which the object lives.
   * @param in
   *                The input stream from which to read the object.
   * @see SerializedObject#write(ObjectOutputStream)
   * @see SerializedObject#write($Impl, ObjectOutputStream)
   * @see SerializedObject#SerializedObject(ObjectInput)
   */
  public static $Impl readImpl(Core core, DataInput in)
      throws ClassNotFoundException, IOException {
    // Read the object header.
    Class<?> c = Class.forName(in.readUTF());
    long onum = in.readLong();
    int version = in.readInt();
    
    long label = in.readLong();

    // Read the object body.
    byte[] serializedData = new byte[in.readInt()];
    in.readFully(serializedData);

    int size = in.readInt();
    List<RefTypeEnum> refTypes = new ArrayList<RefTypeEnum>(size);
    RefTypeEnum[] refTypeEnums = RefTypeEnum.values();
    for (int i = 0; i < size; i++)
      refTypes.add(refTypeEnums[in.readByte()]);

    size = in.readInt();
    List<Long> intracoreRefs = new ArrayList<Long>(size);
    for (int i = 0; i < size; i++)
      intracoreRefs.add(in.readLong());

    // There should be no intercore refs to read.
    if (in.readInt() != 0) {
      throw new InternalError(
          "Unexpected inter-core refs found during object deserialization.");
    }

    try {
      // Call the deserialization constructor.
      return ($Impl) c.getConstructor(Core.class, long.class, int.class,
          long.class, ObjectInput.class, Iterator.class, Iterator.class)
          .newInstance(core, onum, version, label,
              new ObjectInputStream(new ByteArrayInputStream(serializedData)),
              refTypes.iterator(), intracoreRefs.iterator());
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalError(e);
    }
  }

  /**
   * Used by the client to deserialize this object.
   * 
   * @param core
   *                The core on which this object lives.
   * @return The deserialized object.
   * @throws ClassNotFoundException
   *                 Thrown when the class for this object is unavailable.
   */
  public $Impl deserialize(Core core) throws ClassNotFoundException {
    Class<?> c = Class.forName(className);
    try {
      return ($Impl) c.getConstructor(Core.class, long.class, int.class,
          long.class, ObjectInput.class, Iterator.class, Iterator.class)
          .newInstance(core, onum, version, label,
              new ObjectInputStream(new ByteArrayInputStream(serializedData)),
              refTypes.iterator(), intracoreRefs.iterator());
    } catch (Exception e) {
      throw new InternalError(e);
    }
  }

}
