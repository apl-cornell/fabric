package fabric.common;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;

import fabric.common.exceptions.InternalError;
import fabric.common.util.ComparablePair;
import fabric.common.util.Pair;
import fabric.lang.Object._Impl;
import fabric.lang.security.Label;
import fabric.worker.LocalStore;
import fabric.worker.Store;

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
   * <li>byte whether the update label pointer is an inter-store ref</li>
   * <li>short update label's store's name length (only present if inter-store)</li>
   * <li>byte[] update label's store's name data (only present if inter-store)</li>
   * <li>long update label's onum</li>
   * <li>byte whether the access label pointer is an inter-store ref</li>
   * <li>short access label's store's name length (only present if inter-store)</li>
   * <li>byte[] access label's store's name data (only present if inter-store)</li>
   * <li>long access label's onum</li>
   * <li>ClassRef object's class</li>
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
   * The ClassRef for this object's class. This is filled in lazily from the
   * data in objectData when getClassRef() is called.
   */
  private ClassRef classRef;

  private static final RefTypeEnum[] refTypeEnums = RefTypeEnum.values();

  /**
   * Creates a serialized representation of the given object.
   * 
   * @param obj
   *          The object to serialize.
   * @deprecated This should only be used by fabric.store.InProcessStore and for
   *             debugging (worker.debug.*).
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
   * @param updateLabel
   *          The onum for the surrogate's label object.
   * @param remoteRef
   *          The name of the remote object being referred to.
   */
  public SerializedObject(long onum, long updateLabel, long accessLabel,
      Pair<String, Long> remoteRef) {
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

      // Update Label reference.
      out.writeBoolean(false);
      out.writeLong(updateLabel);

      // Access Label reference.
      out.writeBoolean(false);
      out.writeLong(accessLabel);

      // Class ref.
      ClassRef.SURROGATE.write(out);

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
    return SerializationUtil.longAt(objectData, onumPos());
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
    return SerializationUtil.intAt(objectData, versionPos());
  }

  /**
   * Modifies the serialized object's version number.
   */
  public void setVersion(final int version) {
    SerializationUtil.setIntAt(objectData, versionPos(), version);
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
    return SerializationUtil.longAt(objectData, expiryPos());
  }

  /**
   * Modifies the serialized object's promise expiry
   * 
   * @param expiry
   */
  public void setExpiry(long expiry) {
    SerializationUtil.setLongAt(objectData, expiryPos(), expiry);
  }

  /**
   * @return an inter-store reference to the the serialized object's label.
   * @throws InternalError
   *           if the serialized object has an intra-store reference to its
   *           label.
   */
  public ComparablePair<String, Long> getInterStoreLabelRef(int labelPos) {
    int storeNameLength =
        SerializationUtil.unsignedShortAt(objectData, labelPos);
    int onumPos = labelPos + 2 + storeNameLength;
    DataInput in =
        new DataInputStream(new ByteArrayInputStream(objectData, labelPos,
            onumPos));
    try {
      return new ComparablePair<String, Long>(in.readUTF(),
          SerializationUtil.longAt(objectData, onumPos));
    } catch (IOException e) {
      throw new InternalError("Error while reading store name.", e);
    }
  }

  /**
   * @return the offset in objectData representing the start of a boolean that
   *         indicates whether the label pointer is an inter-store reference.
   */
  private final int isInterStoreUpdateLabelPos() {
    return expiryPos() + 8;
  }

  /**
   * @return whether the reference to the serialized object's label is an
   *         inter-store reference.
   */
  public boolean updateLabelRefIsInterStore() {
    return SerializationUtil
        .booleanAt(objectData, isInterStoreUpdateLabelPos());
  }

  /**
   * @return the offset in objectData representing the start of the label
   *         reference.
   */
  private final int updateLabelPos() {
    return isInterStoreUpdateLabelPos() + 1;
  }

  /**
   * @return an inter-store reference to the the serialized object's label.
   * @throws InternalError
   *           if the serialized object has an intra-store reference to its
   *           label.
   */
  public ComparablePair<String, Long> getInterStoreUpdateLabelRef() {
    if (!updateLabelRefIsInterStore())
      throw new InternalError("Unsupported operation: Attempted to get an "
          + "inter-store reference to an intra-store label.");
    return getInterStoreLabelRef(updateLabelPos());
  }

  /**
   * @return an intra-store reference to the serialized object's label.
   * @throws InternalError
   *           if the serialized object has an inter-store reference to its
   *           label.
   */
  public long getUpdateLabelOnum() {
    if (updateLabelRefIsInterStore())
      throw new InternalError("Unsupported operation: Attempted to get label "
          + "onum of an object whose inter-store references have not yet been "
          + "swizzled." + getInterStoreUpdateLabelRef());

    return SerializationUtil.longAt(objectData, updateLabelPos());
  }

  /**
   * @return the offset in objectData representing the start of a boolean that
   *         indicates whether the label pointer is an inter-store reference.
   */
  private final int isInterStoreAccessLabelPos() {
    int labelPos = updateLabelPos(); // access label is after the update label
    return labelPos
        + 8
        + (updateLabelRefIsInterStore() ? (SerializationUtil.unsignedShortAt(
            objectData, labelPos) + 2) : 0);
  }

  /**
   * @return whether the reference to the serialized object's label is an
   *         inter-store reference.
   */
  public boolean accessLabelRefIsInterStore() {
    return SerializationUtil
        .booleanAt(objectData, isInterStoreAccessLabelPos());
  }

  /**
   * @return the offset in objectData representing the start of the label
   *         reference.
   */
  private final int accessLabelPos() {
    return isInterStoreAccessLabelPos() + 1;
  }

  /**
   * @return an inter-store reference to the the serialized object's label.
   * @throws InternalError
   *           if the serialized object has an intra-store reference to its
   *           label.
   */
  public ComparablePair<String, Long> getInterStoreAccessLabelRef() {
    if (!accessLabelRefIsInterStore())
      throw new InternalError("Unsupported operation: Attempted to get an "
          + "inter-store reference to an intra-store label.");
    return getInterStoreLabelRef(accessLabelPos());
  }

  /**
   * @return an intra-store reference to the serialized object's label.
   * @throws InternalError
   *           if the serialized object has an inter-store reference to its
   *           label.
   */
  public long getAccessLabelOnum() {
    if (accessLabelRefIsInterStore())
      throw new InternalError("Unsupported operation: Attempted to get label "
          + "onum of an object whose inter-store references have not yet been "
          + "swizzled." + getInterStoreAccessLabelRef());

    return SerializationUtil.longAt(objectData, accessLabelPos());
  }

  /**
   * @return the offset in objectData representing the start of the serialized
   *         ClassRef for the object's class.
   */
  private final int classRefPos() {
    int labelPos = accessLabelPos();
    return labelPos
        + 8
        + (accessLabelRefIsInterStore() ? (SerializationUtil.unsignedShortAt(
            objectData, labelPos) + 2) : 0);
  }

  /**
   * @return a ClassRef for the object's class.
   */
  public ClassRef getClassRef() {
    if (classRef != null) return classRef;
    return classRef = ClassRef.deserialize(objectData, classRefPos());
  }

  /**
   * @return the offset in objectData representing the start of an int
   *         representing the number of reference types (i.e.,
   *         inter-store/intra-store/serialized).
   */
  private final int numRefTypesPos() {
    int classRefPos = classRefPos();
    return classRefPos + ClassRef.lengthAt(objectData, classRefPos);
  }

  /**
   * @return the number of references in the serialized object. (See
   *         RefTypeEnum.)
   */
  public final int getNumRefTypes() {
    return SerializationUtil.intAt(objectData, numRefTypesPos());
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
    return SerializationUtil.intAt(objectData, numIntraStoreRefsPos());
  }

  /**
   * @return the offset in objectData representing the start of an int
   *         representing the length of the data portion of the object.
   */
  private final int serializedDataLengthPos() {
    return numIntraStoreRefsPos() + 4;
  }

  private final int serializedDataLength() {
    return SerializationUtil.intAt(objectData, serializedDataLengthPos());
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
    return SerializationUtil.intAt(objectData, numInterStoreRefsPos());
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
        return SerializationUtil.longAt(objectData, offset + 8
            * (nextIntraStoreRefNum++));
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
      DataInput in = new DataInputStream(new ByteArrayInputStream(objectData,
          offset, objectData.length - offset));

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
      out.write(objectData, 0, isInterStoreUpdateLabelPos());

      // Write the update label reference.
      out.writeBoolean(false);
      if (updateLabelRefIsInterStore())
        out.writeLong(intraStoreRefIt.next());
      else out.writeLong(getUpdateLabelOnum());

      // Write the access label reference.
      out.writeBoolean(false);
      if (accessLabelRefIsInterStore())
        out.writeLong(intraStoreRefIt.next());
      else out.writeLong(getAccessLabelOnum());

      // Write the ClassRef and number of ref types.
      int classRefPos = classRefPos();
      out.write(objectData, classRefPos, numIntraStoreRefsPos() - classRefPos);

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
    Label updateLabel = impl.get$label();
    Store updateLabelStore = updateLabel.$getStore();
    long updateLabelOnum = updateLabel.$getOnum();
    boolean interStoreUpdateLabel =
        !ONumConstants.isGlobalConstant(updateLabelOnum)
            && !impl.$getStore().equals(updateLabelStore);

    // Write out the object header.
    out.writeLong(impl.$getOnum());
    out.writeInt(impl.$version);
    out.writeLong(0);

    // Write the update label
    out.writeBoolean(interStoreUpdateLabel);
    if (interStoreUpdateLabel) {
      if (updateLabelStore instanceof LocalStore) {
        Class<?> objClass = impl.getClass();
        String objStr = impl.toString();
        throw new InternalError("Creating remote ref to local store.  Remote "
            + "object has class " + objClass + ".  Its string representation "
            + "is \"" + objStr + "\", and its updateLabel is local.");
      }
      out.writeUTF(updateLabelStore.name());
    }
    out.writeLong(updateLabelOnum);

    // Write the access label
    Label accessLabel = impl.get$accesslabel();
    Store accessLabelStore = accessLabel.$getStore();
    long accessLabelOnum = accessLabel.$getOnum();
    boolean interStoreAccessLabel =
        !ONumConstants.isGlobalConstant(accessLabelOnum)
            && !impl.$getStore().equals(accessLabelStore);

    out.writeBoolean(interStoreAccessLabel);
    if (interStoreAccessLabel) {
      if (accessLabelStore instanceof LocalStore) {
        Class<?> objClass = impl.getClass();
        String objStr = impl.toString();
        throw new InternalError("Creating remote ref to local store.  Remote "
            + "object has class " + objClass + ".  Its string representation "
            + "is \"" + objStr + "\", and its accessLabel is local.");
      }
      out.writeUTF(accessLabelStore.name());
    }
    out.writeLong(accessLabelOnum);

    // Write the object's type information
    Class<?> implClass = impl.getClass();
    ClassRef classRef = ClassRef.makeRef(implClass.getEnclosingClass());
    classRef.write(out);

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
    byte[] buf = new byte[SerializationUtil.BUF_LEN];

    // Copy the onum, version number, and promise expiry.
    in.readFully(buf, 0, 20);
    out.write(buf, 0, 20);

    // Copy the update label pointer.
    boolean isInterStoreUpdateLabel = in.readBoolean();
    out.writeBoolean(isInterStoreUpdateLabel);
    int bytesToCopy = 8;
    if (isInterStoreUpdateLabel) {
      int storeNameLength = in.readUnsignedShort();
      out.writeShort(storeNameLength);
      bytesToCopy += storeNameLength;
    }
    SerializationUtil.copyBytes(in, out, bytesToCopy, buf);

    // Copy the access label pointer.
    boolean isInterStoreAccessLabel = in.readBoolean();
    out.writeBoolean(isInterStoreAccessLabel);
    bytesToCopy = 8;
    if (isInterStoreAccessLabel) {
      int storeNameLength = in.readUnsignedShort();
      out.writeShort(storeNameLength);
      bytesToCopy += storeNameLength;
    }
    SerializationUtil.copyBytes(in, out, bytesToCopy, buf);

    // Copy class information.
    ClassRef.copySerialization(in, out, buf);

    // Copy the body.
    int numRefTypes = in.readInt();
    out.writeInt(numRefTypes);

    int numIntraStoreRefs = in.readInt();
    out.writeInt(numIntraStoreRefs);

    int serializedDataLength = in.readInt();
    out.writeInt(serializedDataLength);

    int numInterStoreRefs = in.readInt();
    out.writeInt(numInterStoreRefs);

    SerializationUtil.copyBytes(in, out, numRefTypes + 8 * numIntraStoreRefs
        + serializedDataLength, buf);

    for (int i = 0; i < numInterStoreRefs; i++) {
      // Copy an inter-store ref.
      int len = in.readUnsignedShort();
      out.writeShort(len);
      SerializationUtil.copyBytes(in, out, len + 8, buf);
    }

    out.flush();
    bos.flush();
    this.objectData = bos.toByteArray();
  }

  /**
   * Maps class names to their deserialization constructors.
   */
  private static final Map<Class<? extends _Impl>, Constructor<?>> constructorTable =
      Collections
          .synchronizedMap(new HashMap<Class<? extends _Impl>, Constructor<?>>());

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
      Class<? extends _Impl> implClass = getClassRef().toImplClass();

      Constructor<?> constructor = constructorTable.get(implClass);

      if (constructor == null) {
        constructor =
            implClass.getConstructor(Store.class, long.class, int.class,
                long.class, long.class, long.class, ObjectInput.class,
                Iterator.class, Iterator.class);
        constructorTable.put(implClass, constructor);
      }

      return (_Impl) constructor.newInstance(store, getOnum(), getVersion(),
          getExpiry(), getUpdateLabelOnum(), getAccessLabelOnum(),
          new ObjectInputStream(getSerializedDataStream()),
          getRefTypeIterator(), getIntraStoreRefIterator());
    } catch (Exception e) {
      throw new InternalError(e);
    }
  }

}
