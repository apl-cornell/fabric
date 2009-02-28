package fabric.common;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import fabric.client.Core;
import fabric.lang.Object.$Impl;
import jif.lang.Label;

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
   * <li>long promise expiration</li>
   * <li>byte whether the label pointer is an intercore ref</li>
   * <li>short label's core's name length (only present if intercore)</li>
   * <li>byte[] label's core's name data (only present if intercore)</li>
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
   * be used by fabric.core.InProcessCore and for debugging (client.debug.*).
   * 
   * @param obj
   *          The object to serialize.
   * @deprecated
   */
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
   *          The local object number for the surrogate.
   * @param label
   *          The onum for the surrogate's label object.
   * @param remoteRef
   *          The name of the remote object being referred to.
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
      
      // Promise expiry
      out.writeLong(0);

      // Label reference.
      out.writeBoolean(false);
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
   * @return the offset in objectData representing the start of the onum.
   */
  private final int onumPos() {
    return 0;
  }

  public long getOnum() {
    return longAt(onumPos());
  }

  /**
   * @return the offset in objectData representing the start of the version
   *         number.
   */
  private final int versionPos() {
    return onumPos() + 8;
  }

  /**
   * @return the serialized object's version number.
   */
  public int getVersion() {
    return intAt(versionPos());
  }

  /**
   * Modifies the serialized object's version number.
   */
  public void setVersion(final int version) {
    setIntAt(versionPos(), version);
  }

  /**
   * @return the offset in objectData representing the start of the promise
   *         expiry
   */
  private final int expiryPos() {
    return versionPos() + 4;
  }
  
  /**
   * @return the serialized object's promise expiration time
   */
  public long getExpiry() {
    return longAt(expiryPos());
  }

  /**
   * Modifies the serialized object's promise expiry
   * @param expiry
   */
  public void setExpiry(long expiry) {
    setLongAt(expiryPos(), expiry);
  }
  
  /**
   * @return the offset in objectData representing the start of a boolean that
   *         indicates whether the label pointer is an intercore reference.
   */
  private final int isIntercoreLabelPos() {
    return expiryPos() + 8;
  }

  /**
   * @return whether the reference to the serialized object's label is an
   *         intercore reference.
   */
  public boolean labelRefIsIntercore() {
    return booleanAt(isIntercoreLabelPos());
  }

  /**
   * @return the offset in objectData representing the start of the label
   *         reference.
   */
  private final int labelPos() {
    return isIntercoreLabelPos() + 1;
  }

  /**
   * @return an intercore reference to the the serialized object's label.
   * @throws InternalError
   *           if the serialized object has an intracore reference to its label.
   */
  public ComparablePair<String, Long> getIntercoreLabelRef() {
    if (!labelRefIsIntercore())
      throw new InternalError("Unsupported operation: Attempted to get an "
          + "intercore reference to an intracore label.");

    int labelPos = labelPos();
    int coreNameLength = unsignedShortAt(labelPos);
    int onumPos = labelPos + 2 + coreNameLength;
    DataInput in =
        new DataInputStream(new ByteArrayInputStream(objectData, labelPos,
            onumPos));
    try {
      return new ComparablePair<String, Long>(in.readUTF(), longAt(onumPos));
    } catch (IOException e) {
      throw new InternalError("Error while reading core name.", e);
    }
  }

  /**
   * @return an intracore reference to the serialized object's label.
   * @throws InternalError
   *           if the serialized object has an intercore reference to its label.
   */
  public long getLabelOnum() {
    if (labelRefIsIntercore())
      throw new InternalError("Unsupported operation: Attempted to get label "
          + "onum of an object whose intercore references have not yet been "
          + "swizzled." + getIntercoreLabelRef());

    return longAt(labelPos());
  }

  /**
   * @return the offset in objectData representing the start of the class name.
   */
  private final int classNamePos() {
    int labelPos = labelPos();
    return labelPos + 8
        + (labelRefIsIntercore() ? (unsignedShortAt(labelPos) + 2) : 0);
  }

  /**
   * @return the serialized object's class name.
   */
  public String getClassName() {
    if (className == null) {
      int classNamePos = classNamePos();
      DataInput in =
          new DataInputStream(new ByteArrayInputStream(objectData,
              classNamePos, objectData.length - classNamePos));
      try {
        className = in.readUTF();
      } catch (IOException e) {
        throw new InternalError("Error while reading class name.", e);
      }
    }

    return className;
  }

  /**
   * @return the offset in objectData representing the start of an int
   *         representing the number of reference types (i.e.,
   *         intercore/intracore/serialized).
   */
  private final int numRefTypesPos() {
    int classPos = classNamePos();
    return classPos + 2 + unsignedShortAt(classPos);
  }

  /**
   * @return the number of references in the serialized object. (See
   *         RefTypeEnum.)
   */
  public final int getNumRefTypes() {
    return intAt(numRefTypesPos());
  }

  /**
   * @return the offset in objectData representing the start of an int
   *         representing the number of intracore references.
   */
  private final int numIntracoreRefsPos() {
    return numRefTypesPos() + 4;
  }

  /**
   * @return the number of intracore references in the serialized object.
   */
  public final int getNumIntracoreRefs() {
    return intAt(numIntracoreRefsPos());
  }

  /**
   * @return the offset in objectData representing the start of an int
   *         representing the length of the data portion of the object.
   */
  private final int serializedDataLengthPos() {
    return numIntracoreRefsPos() + 4;
  }

  private final int serializedDataLength() {
    return intAt(serializedDataLengthPos());
  }

  /**
   * @return the offset in objectData representing the start of an int
   *         representing the number of intercore references.
   */
  private final int numIntercoreRefsPos() {
    return serializedDataLengthPos() + 4;
  }

  /**
   * @return the number of intercore references in the serialized object.
   */
  public final int getNumIntercoreRefs() {
    return intAt(numIntercoreRefsPos());
  }

  /**
   * @return the offset in objectData representing the start of the reference
   *         type (i.e., intercore/intracore/serialized) data.
   */
  private final int refTypesPos() {
    return numIntercoreRefsPos() + 4;
  }

  /**
   * @return an Iterator for the serialized object's reference types.
   */
  public Iterator<RefTypeEnum> getRefTypeIterator() {
    final int numRefTypes = getNumRefTypes();
    final int offset = refTypesPos();

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

  /**
   * @return the offset in objectData representing the start of the
   *         intracore-reference data.
   */
  private final int intracoreRefsPos() {
    return refTypesPos() + getNumRefTypes();
  }

  /**
   * @return an Iterator for the serialized object's intracore references.
   */
  public Iterator<Long> getIntracoreRefIterator() {
    final int numIntracoreRefs = getNumIntracoreRefs();
    final int offset = intracoreRefsPos();

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

  /**
   * @return the offset in objectData representing the start of the serialized
   *         data.
   */
  private final int serializedDataPos() {
    return intracoreRefsPos() + 8 * getNumIntracoreRefs();
  }

  /**
   * @return an InputStream for the Java-serialized data segment in the
   *         serialized object.
   */
  public InputStream getSerializedDataStream() {
    int serializedDataLength = serializedDataLength();
    return new ByteArrayInputStream(objectData, serializedDataPos(),
        serializedDataLength);
  }

  /**
   * @return the offset in objectData representing the start of the intercore
   *         reference data.
   */
  private final int intercoreRefsPos() {
    return serializedDataPos() + serializedDataLength();
  }

  /**
   * @return an Iterator for the serialized object's intercore references.
   */
  public Iterator<ComparablePair<String, Long>> getIntercoreRefIterator() {
    final int numIntercoreRefs = getNumIntercoreRefs();
    final int offset = intercoreRefsPos();

    return new Iterator<ComparablePair<String, Long>>() {
      int nextIntercoreRefNum = 0;
      DataInput in =
          new DataInputStream(new ByteArrayInputStream(objectData, offset,
              objectData.length - offset));

      public boolean hasNext() {
        return nextIntercoreRefNum < numIntercoreRefs;
      }

      public ComparablePair<String, Long> next() {
        if (!hasNext()) throw new NoSuchElementException();
        nextIntercoreRefNum++;
        try {
          return new ComparablePair<String, Long>(in.readUTF(), in.readLong());
        } catch (IOException e) {
          throw new InternalError("Unexpected IO exception.", e);
        }
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
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

      int numIntracoreRefs = getNumIntracoreRefs();
      Iterator<Long> intracoreRefIt = intracoreRefs.iterator();

      // Write onum and version number.
      out.write(objectData, 0, isIntercoreLabelPos());

      // Write the label reference.
      out.writeBoolean(false);
      if (labelRefIsIntercore())
        out.writeLong(intracoreRefIt.next());
      else out.writeLong(getLabelOnum());

      // Write the class name and number of ref types.
      out.write(objectData, classNamePos(), numIntracoreRefsPos()
          - classNamePos());

      // Write number of intracore refs.
      out.writeInt(getNumIntercoreRefs() + numIntracoreRefs);

      // Write length of serialized data.
      out.write(objectData, serializedDataLengthPos(), 4);

      // Write number of intercore refs.
      out.writeInt(0);

      // Write ref type data.
      int numRefs = getNumRefTypes();
      final int REMOTE = RefTypeEnum.REMOTE.ordinal();
      final int ONUM = RefTypeEnum.ONUM.ordinal();
      int offset = refTypesPos();
      for (int i = 0; i < numRefs; i++) {
        if (objectData[offset] == REMOTE)
          out.write(ONUM);
        else out.write(objectData[offset]);
        offset++;
      }

      // Write intracore refs.
      while (intracoreRefIt.hasNext()) {
        out.writeLong(intracoreRefIt.next());
      }

      // Write serialized data.
      out.write(objectData, serializedDataPos(), serializedDataLength());

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
   * Writes the given $Impl out to the given output stream. The behaviour of
   * this method should mirror write(DataOutput).
   * 
   * @see SerializedObject#write(DataOutput)
   * @see SerializedObject#readImpl(Core, DataInput)
   * @see SerializedObject#SerializedObject(DataInput)
   */
  public static void write($Impl impl, DataOutput out) throws IOException {
    Label label = impl.get$label();
    Core labelCore = label.$getCore();
    long labelOnum = label.$getOnum();
    boolean interCoreLabel =
        !ONumConstants.isGlobalConstant(labelOnum)
            && !impl.$getCore().equals(labelCore);

    // Write out the object header.
    out.writeLong(impl.$getOnum());
    out.writeInt(impl.$version);
    out.writeLong(0);
    out.writeBoolean(interCoreLabel);
    if (interCoreLabel) out.writeUTF(labelCore.name());
    out.writeLong(labelOnum);
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
   *          An input stream containing a serialized object.
   * @see SerializedObject#write(DataOutput)
   * @see SerializedObject#write($Impl, DataOutput)
   * @see SerializedObject#readImpl(Core, DataInput)
   */
  public SerializedObject(DataInput in) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(bos);

    // The buffer for copying stuff.
    byte[] buf = new byte[BUF_LEN];

    // Copy the onum, version number, and promise expiry.
    in.readFully(buf, 0, 20);
    out.write(buf, 0, 20);

    // Copy the label pointer.
    boolean isIntercoreLabel = in.readBoolean();
    out.writeBoolean(isIntercoreLabel);
    int bytesToCopy = 8;
    if (isIntercoreLabel) {
      int coreNameLength = in.readUnsignedShort();
      out.writeShort(coreNameLength);
      bytesToCopy += coreNameLength;
    }
    copyBytes(in, out, bytesToCopy, buf);

    // Copy the class name.
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
   *          the DataInput to read from.
   * @param out
   *          the DataOutput to write to.
   * @param length
   *          the number of bytes to copy.
   * @param buf
   *          the buffer to use. Must be of length BUF_LEN.
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
   *          The core on which this object lives.
   * @return The deserialized object.
   * @throws ClassNotFoundException
   *           Thrown when the class for this object is unavailable.
   */
  public $Impl deserialize(Core core) throws ClassNotFoundException {
    Class<?> c = Class.forName(getClassName());

    try {
      return ($Impl) c.getConstructor(Core.class, long.class, int.class,
          long.class, long.class, ObjectInput.class, Iterator.class, Iterator.class)
          .newInstance(core, getOnum(), getVersion(), getExpiry(), getLabelOnum(),
              new ObjectInputStream(getSerializedDataStream()),
              getRefTypeIterator(), getIntracoreRefIterator());
    } catch (Exception e) {
      throw new InternalError(e);
    }
  }

  /**
   * Returns the boolean at the given position in objectData.
   */
  private final boolean booleanAt(int pos) {
    return objectData[pos] == 1;
  }

  /**
   * Returns the unsigned short that starts at the given position in objectData.
   */
  private final int unsignedShortAt(int pos) {
    return ((objectData[pos + 0] & 0xff) << 8)
         | ((objectData[pos + 1] & 0xff) << 0);
  }

  /**
   * Returns the int that starts at the given position in objectData.
   */
  private final int intAt(int pos) {
    return ((objectData[pos + 0] & 0xff) << 24)
         | ((objectData[pos + 1] & 0xff) << 16)
         | ((objectData[pos + 2] & 0xff) <<  8)
         | ((objectData[pos + 3] & 0xff) <<  0);
  }

  /**
   * Sets the int that starts at the given position in objectData.
   */
  private final void setIntAt(int pos, int value) {
    objectData[pos + 0] = (byte) (0xff & (value >> 24));
    objectData[pos + 1] = (byte) (0xff & (value >> 16));
    objectData[pos + 2] = (byte) (0xff & (value >>  8));
    objectData[pos + 3] = (byte) (0xff & (value >>  0));
  }

  /**
   * Returns the long that starts at the given position in objectData.
   */
  private final long longAt(int pos) {
    return ((long) (objectData[pos + 0] & 0xff) << 56)
         | ((long) (objectData[pos + 1] & 0xff) << 48)
         | ((long) (objectData[pos + 2] & 0xff) << 40)
         | ((long) (objectData[pos + 3] & 0xff) << 32)
         | ((long) (objectData[pos + 4] & 0xff) << 24)
         | ((long) (objectData[pos + 5] & 0xff) << 16)
         | ((long) (objectData[pos + 6] & 0xff) <<  8)
         | ((long) (objectData[pos + 7] & 0xff) <<  0);
  }
  
  /**
   * Sets the long that starts at the given position in objectData.
   */
  private final void setLongAt(int pos, long value) {
    objectData[pos + 0] = (byte) (0xff & (value >> 56));
    objectData[pos + 1] = (byte) (0xff & (value >> 48));
    objectData[pos + 2] = (byte) (0xff & (value >> 40));
    objectData[pos + 3] = (byte) (0xff & (value >> 32));
    objectData[pos + 4] = (byte) (0xff & (value >> 24));
    objectData[pos + 5] = (byte) (0xff & (value >> 16));
    objectData[pos + 6] = (byte) (0xff & (value >>  8));
    objectData[pos + 7] = (byte) (0xff & (value >>  0));
  }

}
