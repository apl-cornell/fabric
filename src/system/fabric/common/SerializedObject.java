package fabric.common;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.*;

import fabric.worker.LocalStore;
import fabric.worker.Store;
import fabric.common.exceptions.InternalError;
import fabric.common.util.ComparablePair;
import fabric.common.util.Pair;
import fabric.lang.Object._Impl;
import fabric.lang.security.Label;

/**
 * <code>_Impl</code> objects are stored on stores in serialized form as
 * <code>SerializedObject</code>s.
 */
public final class SerializedObject implements FastSerializable, Serializable {
  /**
   * The serialized object. Format:
   * <ul>
   * <li>long onum</li>
   * <li>int version number</li>
   * <li>long promise expiration</li>
   * <li>byte whether the label pointer is an inter-store ref</li>
   * <li>short label's store's name length (only present if inter-store)</li>
   * <li>byte[] label's store's name data (only present if inter-store)</li>
   * <li>long label's onum</li>
   * <li>short class name length</li>
   * <li>byte[] class name data</li>
   * <li>short class hash length</li>
   * <li>byte[] class hash data</li>
   * <li>int # ref types</li>
   * <li>int # intra-store refs</li>
   * <li>int serialized data length</li>
   * <li>int # inter-store refs</li>
   * <li>byte[] ref type data</li>
   * <li>long[] intra-store refs</li>
   * <li>byte[] serialized data</li>
   * <li>(utf*long)[] inter-store refs</li>
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
   * be used by fabric.store.InProcessStore and for debugging (worker.debug.*).
   * 
   * @param obj
   *          The object to serialize.
   * @deprecated
   */
  public SerializedObject(_Impl obj) {
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
      byte[] className = Surrogate.class.getName().getBytes("UTF-8");
      out.writeShort(className.length);
      out.write(className);
      
      // Class hash.
      byte[] classHash = Util.hash(Surrogate.class);
      out.writeShort(classHash.length);
      out.write(classHash);

      // Number of ref types and intra-store refs.
      out.writeInt(0);
      out.writeInt(0);

      out.writeInt(serializedData.length);

      // Number of inter-store refs.
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
   * 
   * @param expiry
   */
  public void setExpiry(long expiry) {
    setLongAt(expiryPos(), expiry);
  }

  /**
   * @return the offset in objectData representing the start of a boolean that
   *         indicates whether the label pointer is an inter-store reference.
   */
  private final int isInterStoreLabelPos() {
    return expiryPos() + 8;
  }

  /**
   * @return whether the reference to the serialized object's label is an
   *         inter-store reference.
   */
  public boolean labelRefIsInterStore() {
    return booleanAt(isInterStoreLabelPos());
  }

  /**
   * @return the offset in objectData representing the start of the label
   *         reference.
   */
  private final int labelPos() {
    return isInterStoreLabelPos() + 1;
  }

  /**
   * @return an inter-store reference to the the serialized object's label.
   * @throws InternalError
   *           if the serialized object has an intra-store reference to its
   *           label.
   */
  public ComparablePair<String, Long> getInterStoreLabelRef() {
    if (!labelRefIsInterStore())
      throw new InternalError("Unsupported operation: Attempted to get an "
          + "inter-store reference to an intra-store label.");

    int labelPos = labelPos();
    int storeNameLength = unsignedShortAt(labelPos);
    int onumPos = labelPos + 2 + storeNameLength;
    DataInput in =
        new DataInputStream(new ByteArrayInputStream(objectData, labelPos,
            onumPos));
    try {
      return new ComparablePair<String, Long>(in.readUTF(), longAt(onumPos));
    } catch (IOException e) {
      throw new InternalError("Error while reading store name.", e);
    }
  }

  /**
   * @return an intra-store reference to the serialized object's label.
   * @throws InternalError
   *           if the serialized object has an inter-store reference to its
   *           label.
   */
  public long getLabelOnum() {
    if (labelRefIsInterStore())
      throw new InternalError("Unsupported operation: Attempted to get label "
          + "onum of an object whose inter-store references have not yet been "
          + "swizzled." + getInterStoreLabelRef());

    return longAt(labelPos());
  }

  /**
   * @return the offset in objectData representing the start of the class name.
   */
  private final int classNamePos() {
    int labelPos = labelPos();
    return labelPos + 8
        + (labelRefIsInterStore() ? (unsignedShortAt(labelPos) + 2) : 0);
  }

  /**
   * @return the serialized object's class name.
   */
  public String getClassName() {
    if (className == null) {
      int classNamePos = classNamePos();
      int length = unsignedShortAt(classNamePos);
      try {
        className = new String(objectData, classNamePos + 2, length, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        throw new InternalError(e);
      }
    }

    return className;
  }

  private final int classHashPos() {
    int classNamePos = classNamePos();
    return classNamePos + 2 + unsignedShortAt(classNamePos);
  }
  
  private boolean checkClassHash(byte[] hash) {
    int classHashPos = classHashPos();
    if (hash.length != unsignedShortAt(classHashPos)) return false;

    for (int i = 0; i < hash.length; i++) {
      if (hash[i] != objectData[classHashPos+i+2]) return false;
    }
    
    return true;
  }

  /**
   * @return the offset in objectData representing the start of an int
   *         representing the number of reference types (i.e.,
   *         inter-store/intra-store/serialized).
   */
  private final int numRefTypesPos() {
    int classHashPos = classHashPos();
    return classHashPos + 2 + unsignedShortAt(classHashPos);
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
   *         representing the number of intra-store references.
   */
  private final int numIntraStoreRefsPos() {
    return numRefTypesPos() + 4;
  }

  /**
   * @return the number of intra-store references in the serialized object.
   */
  public final int getNumIntraStoreRefs() {
    return intAt(numIntraStoreRefsPos());
  }

  /**
   * @return the offset in objectData representing the start of an int
   *         representing the length of the data portion of the object.
   */
  private final int serializedDataLengthPos() {
    return numIntraStoreRefsPos() + 4;
  }

  private final int serializedDataLength() {
    return intAt(serializedDataLengthPos());
  }

  /**
   * @return the offset in objectData representing the start of an int
   *         representing the number of inter-store references.
   */
  private final int numInterStoreRefsPos() {
    return serializedDataLengthPos() + 4;
  }

  /**
   * @return the number of inter-store references in the serialized object.
   */
  public final int getNumInterStoreRefs() {
    return intAt(numInterStoreRefsPos());
  }

  /**
   * @return the offset in objectData representing the start of the reference
   *         type (i.e., inter-store/intra-store/serialized) data.
   */
  private final int refTypesPos() {
    return numInterStoreRefsPos() + 4;
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
   *         intra-store-reference data.
   */
  private final int intraStoreRefsPos() {
    return refTypesPos() + getNumRefTypes();
  }

  /**
   * @return an Iterator for the serialized object's intra-store references.
   */
  public Iterator<Long> getIntraStoreRefIterator() {
    final int numIntraStoreRefs = getNumIntraStoreRefs();
    final int offset = intraStoreRefsPos();

    return new Iterator<Long>() {
      int nextIntraStoreRefNum = 0;

      public boolean hasNext() {
        return nextIntraStoreRefNum < numIntraStoreRefs;
      }

      public Long next() {
        if (!hasNext()) throw new NoSuchElementException();
        return longAt(offset + 8 * (nextIntraStoreRefNum++));
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
    return intraStoreRefsPos() + 8 * getNumIntraStoreRefs();
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
   * @return the offset in objectData representing the start of the inter-store
   *         reference data.
   */
  private final int interStoreRefsPos() {
    return serializedDataPos() + serializedDataLength();
  }

  /**
   * @return an Iterator for the serialized object's inter-store references.
   */
  public Iterator<ComparablePair<String, Long>> getInterStoreRefIterator() {
    final int numInterStoreRefs = getNumInterStoreRefs();
    final int offset = interStoreRefsPos();

    return new Iterator<ComparablePair<String, Long>>() {
      int nextInterStoreRefNum = 0;
      DataInput in =
          new DataInputStream(new ByteArrayInputStream(objectData, offset,
              objectData.length - offset));

      public boolean hasNext() {
        return nextInterStoreRefNum < numInterStoreRefs;
      }

      public ComparablePair<String, Long> next() {
        if (!hasNext()) throw new NoSuchElementException();
        nextInterStoreRefNum++;
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
   * Replaces the intra-store and inter-store references with the given
   * intra-store references. It is assumed that all inter-store references are
   * being replaced with intra-store references.
   */
  public void setRefs(List<Long> intraStoreRefs) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(baos);

      int numIntraStoreRefs = getNumIntraStoreRefs();
      Iterator<Long> intraStoreRefIt = intraStoreRefs.iterator();

      // Write onum and version number.
      out.write(objectData, 0, isInterStoreLabelPos());

      // Write the label reference.
      out.writeBoolean(false);
      if (labelRefIsInterStore())
        out.writeLong(intraStoreRefIt.next());
      else out.writeLong(getLabelOnum());

      // Write the class name and number of ref types.
      out.write(objectData, classNamePos(), numIntraStoreRefsPos()
          - classNamePos());

      // Write number of intra-store refs.
      out.writeInt(getNumInterStoreRefs() + numIntraStoreRefs);

      // Write length of serialized data.
      out.write(objectData, serializedDataLengthPos(), 4);

      // Write number of inter-store refs.
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

      // Write intra-store refs.
      while (intraStoreRefIt.hasNext()) {
        out.writeLong(intraStoreRefIt.next());
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
   * Writes the given _Impl out to the given output stream. The behaviour of
   * this method should mirror write(DataOutput).
   * 
   * @see #write(DataOutput)
   * @see #readImpl(Store, DataInput)
   * @see #SerializedObject(DataInput)
   */
  public static void write(_Impl impl, DataOutput out) throws IOException {
    Label label = impl.get$label();
    Store labelStore = label.$getStore();
    long labelOnum = label.$getOnum();
    boolean interStoreLabel =
        !ONumConstants.isGlobalConstant(labelOnum)
            && !impl.$getStore().equals(labelStore);

    // Write out the object header.
    out.writeLong(impl.$getOnum());
    out.writeInt(impl.$version);
    out.writeLong(0);
    out.writeBoolean(interStoreLabel);
    if (interStoreLabel) {
      if (labelStore instanceof LocalStore) {
        Class<?> objClass = impl.getClass();
        String objStr = impl.toString();
        throw new InternalError("Creating remote ref to local store.  Remote "
            + "object has class " + objClass + ".  Its string representation "
            + "is \"" + objStr + "\", and its label is local.");
      }
      out.writeUTF(labelStore.name());
    }
    out.writeLong(labelOnum);

    // Write out the object's type information.
    Class<?> implClass = impl.getClass();
    byte[] className = implClass.getName().getBytes("UTF-8");
    out.writeShort(className.length);
    out.write(className);
    byte[] hash = Util.hash(implClass);
    out.writeShort(hash.length);
    out.write(hash);

    // Get the object to serialize itself into a bunch of buffers.
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    List<RefTypeEnum> refTypes = new ArrayList<RefTypeEnum>();
    List<Long> intraStoreRefs = new ArrayList<Long>();
    List<Pair<String, Long>> interStoreRefs =
        new ArrayList<Pair<String, Long>>();
    impl.$serialize(oos, refTypes, intraStoreRefs, interStoreRefs);
    oos.flush();

    // Write the object's contents.
    byte[] serializedData = bos.toByteArray();
    out.writeInt(refTypes.size());
    out.writeInt(intraStoreRefs.size());
    out.writeInt(serializedData.length);
    out.writeInt(interStoreRefs.size());

    for (RefTypeEnum refType : refTypes)
      out.writeByte(refType.ordinal());

    for (Long onum : intraStoreRefs)
      out.writeLong(onum);

    out.write(serializedData);

    for (Pair<String, Long> oid : interStoreRefs) {
      out.writeUTF(oid.first);
      out.writeLong(oid.second);
    }
  }

  /**
   * Writes this SerializedObject out to the given output stream. The behavior
   * of this method should mirror write(_Impl, DataOutput).
   * 
   * @see SerializedObject#write(_Impl, DataOutput)
   * @see SerializedObject#readImpl(Store, DataInput)
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
   * @see #write(DataOutput)
   * @see #write(_Impl, DataOutput)
   * @see #readImpl(Store, DataInput)
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
    boolean isInterStoreLabel = in.readBoolean();
    out.writeBoolean(isInterStoreLabel);
    int bytesToCopy = 8;
    if (isInterStoreLabel) {
      int storeNameLength = in.readUnsignedShort();
      out.writeShort(storeNameLength);
      bytesToCopy += storeNameLength;
    }
    copyBytes(in, out, bytesToCopy, buf);

    // Copy the class name.
    int classNameLength = in.readUnsignedShort();
    out.writeShort(classNameLength);
    copyBytes(in, out, classNameLength, buf);

    // Copy the class hash.
    int classHashLength = in.readUnsignedShort();
    out.writeShort(classHashLength);
    copyBytes(in, out, classHashLength, buf);

    // Copy the body.
    int numRefTypes = in.readInt();
    out.writeInt(numRefTypes);

    int numIntraStoreRefs = in.readInt();
    out.writeInt(numIntraStoreRefs);

    int serializedDataLength = in.readInt();
    out.writeInt(serializedDataLength);

    int numInterStoreRefs = in.readInt();
    out.writeInt(numInterStoreRefs);

    copyBytes(in, out, numRefTypes + 8 * numIntraStoreRefs
        + serializedDataLength, buf);

    for (int i = 0; i < numInterStoreRefs; i++) {
      // Copy an inter-store ref.
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
   * Maps class names to their deserialization constructors.
   */
  private static final Map<String, Constructor<?>> constructorTable =
      Collections.synchronizedMap(new HashMap<String, Constructor<?>>());

  /**
   * Used by the worker to deserialize this object.
   * 
   * @param store
   *          The store on which this object lives.
   * @return The deserialized object.
   * @throws ClassNotFoundException
   *           Thrown when the class for this object is unavailable.
   */
  public _Impl deserialize(Store store) {
    try {
      String className = getClassName();

      // Check the class hash before deserializing.
      if (!checkClassHash(Util.hashClass(className))) {
        URL path = Util.locateClass(className);
        throw new InvalidClassException(
            className,
            "A class of the same name was found, but its hash did not match "
                + "the hash in the object fab://" + store.name() + "/"
                + getOnum() + "\n"
                + "hash from: " + path);
      }

      Constructor<?> constructor = constructorTable.get(className);

      if (constructor == null) {
        Class<?> c = Class.forName(getClassName());
        constructor =
            c.getConstructor(Store.class, long.class, int.class, long.class,
                long.class, ObjectInput.class, Iterator.class, Iterator.class);
        constructorTable.put(className, constructor);
      }

      return (_Impl) constructor.newInstance(store, getOnum(), getVersion(),
          getExpiry(), getLabelOnum(), new ObjectInputStream(
              getSerializedDataStream()), getRefTypeIterator(),
          getIntraStoreRefIterator());
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
        | ((objectData[pos + 2] & 0xff) << 8)
        | ((objectData[pos + 3] & 0xff) << 0);
  }

  /**
   * Sets the int that starts at the given position in objectData.
   */
  private final void setIntAt(int pos, int value) {
    objectData[pos + 0] = (byte) (0xff & (value >> 24));
    objectData[pos + 1] = (byte) (0xff & (value >> 16));
    objectData[pos + 2] = (byte) (0xff & (value >> 8));
    objectData[pos + 3] = (byte) (0xff & (value >> 0));
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
        | ((long) (objectData[pos + 6] & 0xff) << 8)
        | ((long) (objectData[pos + 7] & 0xff) << 0);
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
    objectData[pos + 6] = (byte) (0xff & (value >> 8));
    objectData[pos + 7] = (byte) (0xff & (value >> 0));
  }

}
