package fabric.common;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import fabric.client.Core;
import fabric.lang.Object.$Impl;
import fabric.lang.auth.Label;

/**
 * <code>$Impl</code> objects are stored on cores in serialized form as
 * <code>SerializedObject</code>s.
 */
public final class SerializedObject implements FastSerializable {
  /**
   * The serialized object. Format:
   * <ul>
   * <li>long onum</li>
   * <li>int version number</li>
   * <li>long label's onum</li>
   * <li>short class name length</li>
   * <li>byte[] class name data</li>
   * <li>int # ref types</li>
   * <li>int # intracore refs</li>
   * <li>int serialized data length</li>
   * <li>int # intercore refs</li>
   * <li>byte[] ref type data</li>
   * <li>long[] intracore refs</li>
   * <li>byte[] serialized data</li>
   * <li>(utf*long)[] intercore refs</li>
   * </ul>
   */
  private byte[] objectData;

  /**
   * The name of this object's class. This is filled in lazily from the data in
   * objectData when getClassName() is called.
   */
  private String className;

  private static final RefTypeEnum[] refTypeEnums = RefTypeEnum.values();

  /**
   * Creates a serialized representation of the given object. This should only
   * be used by GenMap and for debugging (client.debug.*).
   * 
   * @param obj
   *                The object to serialize.
   */
  @Deprecated
  public SerializedObject($Impl obj) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(baos);

      write(obj, out);

      out.flush();
      baos.flush();
      this.objectData = baos.toByteArray();
    } catch (IOException e) {
      throw new InternalError("Unexpected I/O error.", e);
    }
  }

  /**
   * Creates a serialized representation of a surrogate object.
   * 
   * @param onum
   *                The local object number for the surrogate.
   * @param label
   *                The onum for the surrogate's label object.
   * @param remoteRef
   *                The name of the remote object being referred to.
   */
  public SerializedObject(long onum, long label, Pair<String, Long> remoteRef) {
    try {
      // Create a byte array containing the surrogate object's serialized data.
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeUTF(remoteRef.first);
      oos.writeLong(remoteRef.second);
      oos.flush();

      byte[] serializedData = baos.toByteArray();

      // Write out the serialized object into a byte array.
      baos = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(baos);

      // onum.
      out.writeLong(onum);

      // Version number.
      out.writeInt(0);

      // Label's onum.
      out.writeLong(label);

      // Class name.
      out.writeUTF(Surrogate.class.getName());

      // Number of ref types and intracore refs.
      out.writeInt(0);
      out.writeInt(0);

      out.writeInt(serializedData.length);

      // Number of intercore refs.
      out.writeInt(0);

      out.write(serializedData);

      out.flush();
      baos.flush();
      this.objectData = baos.toByteArray();
    } catch (IOException e) {
      throw new InternalError("Unexpected I/O error.", e);
    }
  }

  /**
   * Returns the unsigned short that starts at the given position in objectData.
   */
  private final int unsignedShortAt(int pos) {
    return ((objectData[pos] & 0xff) << 8) | (objectData[pos + 1] & 0xff);
  }

  /**
   * Returns the int that starts at the given position in objectData.
   */
  private final int intAt(int pos) {
    return ((objectData[pos] & 0xff) << 24)
        | ((objectData[pos + 1] & 0xff) << 16)
        | ((objectData[pos + 2] & 0xff) << 8) | (objectData[pos + 3] & 0xff);
  }

  /**
   * Sets the int that starts at the given position in objectData.
   */
  private final void setIntAt(int pos, int value) {
    objectData[pos] = (byte) (0xff & (value >> 24));
    objectData[pos + 1] = (byte) (0xff & (value >> 16));
    objectData[pos + 2] = (byte) (0xff & (value >> 8));
    objectData[pos + 3] = (byte) (0xff & value);
  }

  /**
   * Returns the long that starts at the given position in objectData.
   */
  private final long longAt(int pos) {
    return ((long) (objectData[pos] & 0xff) << 56)
        | ((long) (objectData[pos + 1] & 0xff) << 48)
        | ((long) (objectData[pos + 2] & 0xff) << 40)
        | ((long) (objectData[pos + 3] & 0xff) << 32)
        | ((long) (objectData[pos + 4] & 0xff) << 24)
        | ((long) (objectData[pos + 5] & 0xff) << 16)
        | ((long) (objectData[pos + 6] & 0xff) << 8)
        | (objectData[pos + 7] & 0xff);
  }

  public long getOnum() {
    return longAt(0);
  }

  public String getClassName() {
    if (className == null) {
      DataInput in =
          new DataInputStream(new ByteArrayInputStream(objectData, 20,
              objectData.length - 20));
      try {
        className = in.readUTF();
      } catch (IOException e) {
        throw new InternalError("Error while reading class name.", e);
      }
    }

    return className;
  }

  public long getLabel() {
    return longAt(12);
  }

  public int getVersion() {
    return intAt(8);
  }

  public void setVersion(final int version) {
    setIntAt(8, version);
  }

  public Iterator<RefTypeEnum> getRefTypeIterator() {
    final int classNameLength = unsignedShortAt(20);
    final int numRefTypes = intAt(22 + classNameLength);
    final int offset = classNameLength + 38;

    return new Iterator<RefTypeEnum>() {
      int nextRefTypeNum = 0;

      public boolean hasNext() {
        return nextRefTypeNum < numRefTypes;
      }

      public RefTypeEnum next() {
        if (!hasNext()) throw new NoSuchElementException();
        return refTypeEnums[objectData[offset + nextRefTypeNum++]];
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  public Iterator<Long> getIntracoreRefIterator() {
    final int classNameLength = unsignedShortAt(20);
    final int numRefTypes = intAt(22 + classNameLength);
    final int numIntracoreRefs = intAt(26 + classNameLength);
    final int offset = classNameLength + numRefTypes + 38;

    return new Iterator<Long>() {
      int nextIntracoreRefNum = 0;

      public boolean hasNext() {
        return nextIntracoreRefNum < numIntracoreRefs;
      }

      public Long next() {
        if (!hasNext()) throw new NoSuchElementException();
        return longAt(offset + 8 * (nextIntracoreRefNum++));
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  public Iterator<Pair<String, Long>> getIntercoreRefIterator() {
    final int classNameLength = unsignedShortAt(20);
    final int numRefTypes = intAt(22 + classNameLength);
    final int numIntracoreRefs = intAt(26 + classNameLength);
    final int serializedDataLength = intAt(30 + classNameLength);
    final int numIntercoreRefs = intAt(34 + classNameLength);
    final int offset =
        38 + classNameLength + numRefTypes + 8 * numIntracoreRefs
            + serializedDataLength;

    return new Iterator<Pair<String, Long>>() {
      int nextIntercoreRefNum = 0;
      DataInput in =
          new DataInputStream(new ByteArrayInputStream(objectData, offset,
              objectData.length - offset));

      public boolean hasNext() {
        return nextIntercoreRefNum < numIntercoreRefs;
      }

      public Pair<String, Long> next() {
        if (!hasNext()) throw new NoSuchElementException();
        nextIntercoreRefNum++;
        try {
          return new Pair<String, Long>(in.readUTF(), in.readLong());
        } catch (IOException e) {
          throw new InternalError("Unexpected IO exception.", e);
        }
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  public InputStream getSerializedDataStream() {
    int classNameLength = unsignedShortAt(20);
    int numRefTypes = intAt(22 + classNameLength);
    int numIntracoreRefs = intAt(26 + classNameLength);
    int serializedDataLength = intAt(30 + classNameLength);
    return new ByteArrayInputStream(objectData, classNameLength + 38
        + numRefTypes + 8 * numIntracoreRefs, serializedDataLength);
  }

  public final int getNumRefTypes() {
    int classNameLength = unsignedShortAt(20);
    return intAt(22 + classNameLength);
  }

  public final int getNumIntracoreRefs() {
    int classNameLength = unsignedShortAt(20);
    return intAt(26 + classNameLength);
  }

  public final int getNumIntercoreRefs() {
    int classNameLength = unsignedShortAt(20);
    return intAt(34 + classNameLength);
  }

  /**
   * Replaces the intracore and intercore references with the given intracore
   * references. It is assumed that all intercore references are being replaced
   * with intracore references.
   */
  public void setRefs(List<Long> intracoreRefs) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(baos);

      int classNameLength = unsignedShortAt(20);
      int numIntracoreRefs = getNumIntracoreRefs();
      out.write(objectData, 0, 26 + classNameLength);
      // Write number of intracore refs.
      out.writeInt(getNumIntercoreRefs() + numIntracoreRefs);
      // Write length of serialized data.
      out.write(objectData, 30 + classNameLength, 4);
      // Write number of intercore refs.
      out.writeInt(0);

      // Write ref type data.
      int numRefs = getNumRefTypes();
      final int REMOTE = RefTypeEnum.REMOTE.ordinal();
      final int ONUM = RefTypeEnum.ONUM.ordinal();
      int offset = 38 + classNameLength;
      for (int i = 0; i < numRefs; i++) {
        if (objectData[offset] == REMOTE)
          out.write(ONUM);
        else out.write(objectData[offset]);
        offset++;
      }

      // Write intracore refs.
      for (Long ref : intracoreRefs)
        out.writeLong(ref);

      // Write serialized data.
      out.write(objectData, offset + 8 * numIntracoreRefs,
          intAt(30 + classNameLength));

      out.flush();
      baos.flush();
      this.objectData = baos.toByteArray();
    } catch (IOException e) {
      throw new InternalError("Unexpected I/O error.", e);
    }
  }

  @Override
  public String toString() {
    return getOnum() + "v" + getVersion();
  }

  /**
   * XXX HACK This should disappear once we have real labels in place.
   */
  private static long getLabelOnum(Label l) {
    if (l == null) {
      return -1;
    } else {
      return l.$getOnum();
    }
  }

  /**
   * Writes the given $Impl out to the given output stream. The behaviour of
   * this method should mirror write(DataOutput).
   * 
   * @see SerializedObject#write(DataOutput)
   * @see SerializedObject#readImpl(Core, DataInput)
   * @see SerializedObject#SerializedObject(DataInput)
   */
  public static void write($Impl impl, DataOutput out) throws IOException {
    // Write out the object header.
    out.writeLong(impl.$getOnum());
    out.writeInt(impl.$version);
    out.writeLong(getLabelOnum(impl.get$label()));
    out.writeUTF(impl.getClass().getName());

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
    out.writeInt(refTypes.size());
    out.writeInt(intracoreRefs.size());
    out.writeInt(serializedData.length);
    out.writeInt(intercoreRefs.size());

    for (RefTypeEnum refType : refTypes)
      out.writeByte(refType.ordinal());

    for (Long onum : intracoreRefs)
      out.writeLong(onum);

    out.write(serializedData);

    for (Pair<String, Long> oid : intercoreRefs) {
      out.writeUTF(oid.first);
      out.writeLong(oid.second);
    }
  }

  /**
   * Writes this SerializedObject out to the given output stream. The behavior
   * of this method should mirror write($Impl, DataOutput).
   * 
   * @see SerializedObject#write($Impl, DataOutput)
   * @see SerializedObject#readImpl(Core, DataInput)
   * @see SerializedObject#SerializedObject(DataInput)
   */
  public void write(DataOutput out) throws IOException {
    out.write(objectData);
  }

  /**
   * A deserialization constructor.
   * 
   * @param in
   *                An input stream containing a serialized object.
   * @see SerializedObject#write(DataOutput)
   * @see SerializedObject#write($Impl, DataOutput)
   * @see SerializedObject#readImpl(Core, DataInput)
   */
  public SerializedObject(DataInput in) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(bos);

    // The buffer for copying stuff.
    byte[] buf = new byte[BUF_LEN];

    // Copy the object header.
    in.readFully(buf, 0, 20);
    out.write(buf, 0, 20);

    // Copy the classname.
    int classNameLength = in.readUnsignedShort();
    out.writeShort(classNameLength);
    copyBytes(in, out, classNameLength, buf);

    // Copy the body.
    int numRefTypes = in.readInt();
    out.writeInt(numRefTypes);

    int numIntracoreRefs = in.readInt();
    out.writeInt(numIntracoreRefs);

    int serializedDataLength = in.readInt();
    out.writeInt(serializedDataLength);

    int numIntercoreRefs = in.readInt();
    out.writeInt(numIntercoreRefs);

    copyBytes(in, out, numRefTypes + 8 * numIntracoreRefs
        + serializedDataLength, buf);

    for (int i = 0; i < numIntercoreRefs; i++) {
      // Copy an intercore ref.
      int len = in.readUnsignedShort();
      out.writeShort(len);
      copyBytes(in, out, len + 8, buf);
    }

    out.flush();
    bos.flush();
    this.objectData = bos.toByteArray();
  }

  private static final int BUF_LEN = 128;
  private static final byte BUF_LEN_LOG_2 = 7;
  private static final byte BUF_LEN_MASK = 0x7f;

  /**
   * Copies the specified number of bytes from the given DataInput to the given
   * DataOutput.
   * 
   * @param in
   *                the DataInput to read from.
   * @param out
   *                the DataOutput to write to.
   * @param length
   *                the number of bytes to copy.
   * @param buf
   *                the buffer to use. Must be of length BUF_LEN.
   */
  private static final void copyBytes(DataInput in, DataOutput out, int length,
      byte[] buf) throws IOException {
    int numLoops = length >> BUF_LEN_LOG_2;
    for (int count = 0; count < numLoops; count++) {
      in.readFully(buf);
      out.write(buf);
    }
    in.readFully(buf, 0, length & BUF_LEN_MASK);
    out.write(buf, 0, length & BUF_LEN_MASK);
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
    Class<?> c = Class.forName(getClassName());

    try {
      return ($Impl) c.getConstructor(Core.class, long.class, int.class,
          long.class, ObjectInput.class, Iterator.class, Iterator.class)
          .newInstance(core, getOnum(), getVersion(), getLabel(),
              new ObjectInputStream(getSerializedDataStream()),
              getRefTypeIterator(), getIntracoreRefIterator());
    } catch (Exception e) {
      throw new InternalError(e);
    }
  }

}
