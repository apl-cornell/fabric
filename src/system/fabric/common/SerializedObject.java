package fabric.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import fabric.common.exceptions.InternalError;
import fabric.common.util.ComparablePair;
import fabric.common.util.Pair;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.worker.LocalStore;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.ImmutableSet;

/**
 * <code>_Impl</code> objects are stored on stores in serialized form as
 * <code>SerializedObject</code>s.
 */
public final class SerializedObject implements FastSerializable, Serializable {
  /**
   * The serialized object. Wire and disk format:
   * <ul>
   *   <li>{@link Header} Data</li>
   *   <li>Object Data:<ul>
   *     <li>byte[] update label pointer, consisting of:<ul>
   *       <li>byte whether the update label pointer is an inter-store ref</li>
   *       <li>short update label's store's name length (only present if inter-store)</li>
   *       <li>byte[] update label's store's name data (only present if inter-store)</li>
   *       <li>long update label's onum</li>
   *     </ul></li>
   *     <li>byte[] access policy pointer, consisting of:<ul>
   *       <li>byte whether the access policy pointer is an inter-store ref</li>
   *       <li>short access policy's store's name length (only present if inter-store)</li>
   *       <li>byte[] access policy's store's name data (only present if inter-store)</li>
   *       <li>long access policy's onum</li>
   *     </ul></li>
   *     <li>ClassRef object's class</li>
   *     <li>int # ref types</li>
   *     <li>byte[] ref type data</li>
   *     <li>int # intra-store refs</li>
   *     <li>long[] intra-store refs</li>
   *     <li>int Java-serialized data length</li>
   *     <li>byte[] Java-serialized data</li>
   *     <li>int # inter-store refs</li>
   *     <li>(utf*long)[] inter-store refs</li>
   *     <li>int index of beginning of data for access policy pointer</li>
   *     <li>int index of beginning of data for class ref</li>
   *     <li>int index of beginning of data for ref types</li>
   *     <li>int index of beginning of data for intra-store refs</li>
   *     <li>int index of beginning of Java-serialized data</li>
   *     <li>int index of beginning of data for inter-store refs</li>
   *   </ul>
   * </ul>
   */

  /**
   * Data for the Header of a serialized object.
   * <p>
   * This is primarily information that will should be easily accessible even if
   * the object is still serialized (ie. on the Store).
   * <p>
   * Note: this should contain a very limited amount of data which is quick to
   * serialize and deserialize, to avoid overheads.
   */
  private static class Header implements FastSerializable, Serializable {
    private long onum;
    private int version;
    private long expiry;
    private ImmutableObserverSet observers;
    private ImmutableSet associated;

    /**
     * Construct default with onum
     */
    public Header(long onum) {
      this.onum = onum;
      this.version = 0;
      this.expiry = 0;
      this.observers = null;
      this.associated = null;
    }

    /**
     * Construct from _Impl
     */
    public Header(_Impl obj) {
      this.onum = obj.$getOnum();
      this.version = obj.$version;
      this.expiry = obj.$expiry;
      this.observers = obj.$observers;
      this.associated = obj.$associated;
    }

    /**
     * Construct from input stream
     */
    public Header(DataInput in) {
      try {
        this.onum = in.readLong();
        this.version = in.readInt();
        this.expiry = in.readLong();
        if (in.readBoolean()) {
          this.observers = new ImmutableObserverSet(in);
        }
        if (in.readBoolean()) {
          this.associated = new ImmutableSet(in);
        }
      } catch (IOException e) {
        throw new InternalError("This shouldn't be possible", e);
      }
    }

    @Override
    public void write(DataOutput out) {
      try {
        out.writeLong(onum);
        out.writeInt(version);
        out.writeLong(expiry);
        if (observers != null) {
          out.writeBoolean(true);
          observers.write(out);
        } else {
          out.writeBoolean(false);
        }
        if (associated != null) {
          out.writeBoolean(true);
          associated.write(out);
        } else {
          out.writeBoolean(false);
        }
      } catch (IOException e) {
        throw new InternalError("This shouldn't be possible", e);
      }
    }

    /**
     * @return the onum
     */
    public long getOnum() {
      return onum;
    }

    /**
     * @return the versionNumber
     */
    public int getVersion() {
      return version;
    }

    /**
     * @param versionNumber the versionNumber to set
     */
    public void setVersion(int version) {
      this.version = version;
    }

    /**
     * @return the expiry
     */
    public long getExpiry() {
      return expiry;
    }

    /**
     * @param expiry the expiry to set
     */
    public void setExpiry(long expiry) {
      this.expiry = expiry;
    }

    /**
     * @return the observers
     */
    public ImmutableObserverSet getObservers() {
      return observers;
    }

    /**
     * @param observers the observers to set
     */
    public void setObservers(ImmutableObserverSet observers) {
      this.observers = observers;
    }

    /**
     * @return the associated
     */
    public ImmutableSet getAssociated() {
      return associated;
    }

    /**
     * @param associated the associated to set
     */
    public void setAssociated(ImmutableSet associated) {
      this.associated = associated;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
      write(out);
    }

    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException {
      this.onum = in.readLong();
      this.version = in.readInt();
      this.expiry = in.readLong();
      if (in.readBoolean()) {
        this.observers = new ImmutableObserverSet(in);
      }
      if (in.readBoolean()) {
        this.associated = new ImmutableSet(in);
      }
    }

    private void readObjectNoData() throws ObjectStreamException {
      // Do nothing..
    }
  }

  private Header header;
  private byte[] objectData;

  /** Index in objectData for update-label pointer. */
  private static final int UPDATE_LABEL_OFFSET = 0;

  //////////////////////////////////////////////////////////////////////////
  //
  // Offsets from end of serialized data. Note these are in backwards order,
  // starting from the end of the objectData array.
  //
  //////////////////////////////////////////////////////////////////////////

  private static final int INTERSTORE_REFS_POS_LENGTH = 4; // int
  /** Offset from end of objectData for interstore-ref data. */
  private static final int INTERSTORE_REFS_POS_OFFSET =
      INTERSTORE_REFS_POS_LENGTH;

  private static final int SERIALIZED_DATA_POS_LENGTH = 4; // int
  /** Offset from end of objectData for Java-serialized data. */
  private static final int SERIALIZED_DATA_POS_OFFSET =
      INTERSTORE_REFS_POS_OFFSET + SERIALIZED_DATA_POS_LENGTH;

  private static final int INTRASTORE_REFS_POS_LENGTH = 4; // int
  /** Offset from end of objectData for intrastore-ref data. */
  private static final int INTRASTORE_REFS_POS_OFFSET =
      SERIALIZED_DATA_POS_OFFSET + INTRASTORE_REFS_POS_LENGTH;

  private static final int REF_TYPES_POS_LENGTH = 4; // int
  /** Offset from end of objectData for index of ref-types data. */
  private static final int REF_TYPES_POS_OFFSET =
      INTRASTORE_REFS_POS_OFFSET + REF_TYPES_POS_LENGTH;

  private static final int CLASS_REF_POS_LENGTH = 4; // int
  /** Offset from end of objectData for index of class-ref pointer. */
  private static final int CLASS_REF_POS_OFFSET =
      REF_TYPES_POS_OFFSET + CLASS_REF_POS_LENGTH;

  private static final int ACCESS_POLICY_POS_LENGTH = 4; // int
  /** Offset from end of objectData for index of access-policy pointer. */
  private static final int ACCESS_POLICY_POS_OFFSET =
      CLASS_REF_POS_OFFSET + ACCESS_POLICY_POS_LENGTH;

  /**
   * The ClassRef for this object's class. This is filled in lazily from the
   * data in objectData when getClassRef() is called.
   */
  private transient ClassRef classRef;

  private static final RefTypeEnum[] refTypeEnums = RefTypeEnum.values();

  /**
   * Creates a serialized representation of the given object.
   *
   * @param obj
   *          The object to serialize.
   * @deprecated This is method is rather inefficient. Use sparingly.
   */
  @Deprecated
  public SerializedObject(_Impl obj) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(baos);

      write(obj, out, false);

      out.flush();
      baos.flush();
      this.header = new Header(obj);
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
  public SerializedObject(long onum, long updateLabel, long accessPolicy,
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

      // header.
      this.header = new Header(onum);

      // Update label reference.
      out.writeBoolean(false);
      out.writeLong(updateLabel);

      // Save the offset for the access policy reference.
      out.flush();
      int accessPolicyPos = baos.size();

      // Access policy reference.
      out.writeBoolean(false);
      out.writeLong(accessPolicy);

      // Save the offset for the class ref.
      out.flush();
      int classRefPos = baos.size();

      // Class ref.
      ClassRef.SURROGATE.write(out);

      // Save the offset for the ref-type data.
      out.flush();
      int refTypePos = baos.size();

      // Number of ref types.
      out.writeInt(0);

      // Save the offset for the intrastore-ref data.
      out.flush();
      int intrastoreRefPos = baos.size();

      // Number of intra-store refs.
      out.writeInt(0);

      // Save the offset for the Java-serialized data.
      out.flush();
      int serializedDataPos = baos.size();

      out.writeInt(serializedData.length);
      out.write(serializedData);

      // Save the offset for the interstore-ref data.
      out.flush();
      int interstoreRefPos = baos.size();

      // Number of inter-store refs.
      out.writeInt(0);

      // Write out the offsets we've saved.
      out.writeInt(accessPolicyPos);
      out.writeInt(classRefPos);
      out.writeInt(refTypePos);
      out.writeInt(intrastoreRefPos);
      out.writeInt(serializedDataPos);
      out.writeInt(interstoreRefPos);

      out.flush();
      baos.flush();
      this.objectData = baos.toByteArray();
    } catch (IOException e) {
      throw new InternalError("Unexpected I/O error.", e);
    }
  }

  public long getOnum() {
    return header.getOnum();
  }

  /**
   * @return the serialized object's version number.
   */
  public int getVersion() {
    return header.getVersion();
  }

  /**
   * Modifies the serialized object's version number.
   */
  public void setVersion(final int version) {
    header.setVersion(version);
  }

  /**
   * @return the serialized object's promise expiration time
   */
  public long getExpiry() {
    return header.getExpiry();
  }

  /**
   * Modifies the serialized object's promise expiry
   *
   * @param expiry
   */
  public void setExpiry(long expiry) {
    header.setExpiry(expiry);
  }

  /**
   * @return the serialized object's observers
   */
  public ImmutableObserverSet getObservers() {
    return header.getObservers();
  }

  /**
   * Modifies the serialized object's observers
   *
   * @param observers
   */
  public void setObservers(ImmutableObserverSet observers) {
    header.setObservers(observers);
  }

  /**
   * @return the serialized object's associated
   */
  public ImmutableSet getAssociated() {
    return header.getAssociated();
  }

  /**
   * Modifies the serialized object's associated
   *
   * @param associated
   */
  public void setAssociated(ImmutableSet associated) {
    header.setAssociated(associated);
  }

  /**
   * Obtains an inter-store reference at the given position in the serialized
   * data.
   */
  private ComparablePair<String, Long> getInterStoreRef(int refPos) {
    int storeNameLength = SerializationUtil.unsignedShortAt(objectData, refPos);
    int onumPos = refPos + 2 + storeNameLength;
    DataInput in = new DataInputStream(
        new ByteArrayInputStream(objectData, refPos, onumPos));
    try {
      return new ComparablePair<>(in.readUTF(),
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
    return UPDATE_LABEL_OFFSET;
  }

  /**
   * @return whether the reference to the serialized object's label is an
   *         inter-store reference.
   */
  public boolean updateLabelRefIsInterStore() {
    return SerializationUtil.booleanAt(objectData,
        isInterStoreUpdateLabelPos());
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
    return getInterStoreRef(updateLabelPos());
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
   *         indicates whether the access policy pointer is an inter-store
   *         reference.
   */
  private final int isInterStoreAccessPolicyPos() {
    return SerializationUtil.intAt(objectData,
        objectData.length - ACCESS_POLICY_POS_OFFSET);
  }

  /**
   * @return whether the reference to the serialized object's access policy is
   *         an inter-store reference.
   */
  public boolean accessPolicyRefIsInterStore() {
    return SerializationUtil.booleanAt(objectData,
        isInterStoreAccessPolicyPos());
  }

  /**
   * @return the offset in objectData representing the start of the label
   *         reference.
   */
  private final int accessPolicyPos() {
    return isInterStoreAccessPolicyPos() + 1;
  }

  /**
   * @return an inter-store reference to the the serialized object's access
   *         policy.
   * @throws InternalError
   *           if the serialized object has an intra-store reference to its
   *           label.
   */
  public ComparablePair<String, Long> getInterStoreAccessPolicyRef() {
    if (!accessPolicyRefIsInterStore())
      throw new InternalError("Unsupported operation: Attempted to get an "
          + "inter-store reference to an intra-store label.");
    return getInterStoreRef(accessPolicyPos());
  }

  /**
   * @return an intra-store reference to the serialized object's access policy.
   * @throws InternalError
   *           if the serialized object has an inter-store reference to its
   *           label.
   */
  public long getAccessPolicyOnum() {
    if (accessPolicyRefIsInterStore())
      throw new InternalError("Unsupported operation: Attempted to get access "
          + "policy onum of an object whose inter-store references have not yet "
          + "been swizzled." + getInterStoreAccessPolicyRef());

    return SerializationUtil.longAt(objectData, accessPolicyPos());
  }

  /**
   * @return the offset in objectData representing the start of the serialized
   *         ClassRef for the object's class.
   */
  private final int classRefPos() {
    return SerializationUtil.intAt(objectData,
        objectData.length - CLASS_REF_POS_OFFSET);
  }

  /**
   * @return a ClassRef for the object's class.
   */
  public ClassRef getClassRef() {
    if (classRef != null) return classRef;
    return classRef = ClassRef.deserialize(objectData, classRefPos());
  }

  /**
   * @return the object's class's name.
   */
  public String getClassName() {
    return ClassRef.getClassName(objectData, classRefPos());
  }

  /**
   * Determines whether this object is a surrogate.
   */
  public boolean isSurrogate() {
    if (classRef != null) {
      return classRef.isSurrogate();
    }

    // Class not loaded yet. Avoid loading by examining the serialized data
    // directly.
    return ClassRef.isSurrogate(objectData, classRefPos());
  }

  /**
   * @return the offset in objectData representing the start of an int
   *         representing the number of reference types (i.e.,
   *         inter-store/intra-store/serialized).
   */
  private final int numRefTypesPos() {
    return SerializationUtil.intAt(objectData,
        objectData.length - REF_TYPES_POS_OFFSET);
  }

  /**
   * @return the number of references in the serialized object. (See
   *         RefTypeEnum.)
   */
  public final int getNumRefTypes() {
    return SerializationUtil.intAt(objectData, numRefTypesPos());
  }

  /**
   * @return the offset in objectData representing the start of the reference
   *         type (i.e., inter-store/intra-store/serialized) data.
   */
  private final int refTypesPos() {
    return numRefTypesPos() + 4;
  }

  /**
   * @return an Iterator for the serialized object's reference types.
   */
  public Iterator<RefTypeEnum> getRefTypeIterator() {
    final int numRefTypes = getNumRefTypes();
    final int offset = refTypesPos();

    return new Iterator<RefTypeEnum>() {
      int cur = offset;
      int finalOffset = offset + numRefTypes;

      @Override
      public boolean hasNext() {
        return cur < finalOffset;
      }

      @Override
      public RefTypeEnum next() {
        if (!hasNext()) throw new NoSuchElementException();
        return refTypeEnums[objectData[cur++]];
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  /**
   * @return the offset in objectData representing the start of an int
   *         representing the number of intra-store references.
   */
  private final int numIntraStoreRefsPos() {
    return SerializationUtil.intAt(objectData,
        objectData.length - INTRASTORE_REFS_POS_OFFSET);
  }

  /**
   * @return the number of intra-store references in the serialized object.
   */
  public final int getNumIntraStoreRefs() {
    return SerializationUtil.intAt(objectData, numIntraStoreRefsPos());
  }

  /**
   * @return the offset in objectData representing the start of the
   *         intra-store-reference data.
   */
  private final int intraStoreRefsPos() {
    return numIntraStoreRefsPos() + 4;
  }

  /**
   * @return an Iterator for the serialized object's intra-store references.
   */
  public Iterator<Long> getIntraStoreRefIterator() {
    final int numIntraStoreRefs = getNumIntraStoreRefs();
    final int offset = intraStoreRefsPos();

    return new Iterator<Long>() {
      int cur = offset;
      int finalOffset = offset + 8 * numIntraStoreRefs;

      @Override
      public boolean hasNext() {
        return cur < finalOffset;
      }

      @Override
      public Long next() {
        if (!hasNext()) throw new NoSuchElementException();
        int pos = cur;
        cur += 8;
        return SerializationUtil.longAt(objectData, pos);
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  /**
   * @return the offset in objectData representing the start of an int
   *         representing the length of the data portion of the object.
   */
  private final int serializedDataLengthPos() {
    return SerializationUtil.intAt(objectData,
        objectData.length - SERIALIZED_DATA_POS_OFFSET);
  }

  private final int serializedDataLength() {
    return SerializationUtil.intAt(objectData, serializedDataLengthPos());
  }

  /**
   * @return the offset in objectData representing the start of the serialized
   *         data.
   */
  private final int serializedDataPos() {
    return serializedDataLengthPos() + 4;
  }

  /**
   * @return an InputStream for the Java-serialized data segment in the
   *         serialized object.
   */
  public InputStream getSerializedDataStream() {
    return new ByteArrayInputStream(objectData, serializedDataPos(),
        serializedDataLength());
  }

  /**
   * @return the offset in objectData representing the start of an int
   *         representing the number of inter-store references.
   */
  private final int numInterStoreRefsPos() {
    return SerializationUtil.intAt(objectData,
        objectData.length - INTERSTORE_REFS_POS_OFFSET);
  }

  /**
   * @return the number of inter-store references in the serialized object.
   */
  public final int getNumInterStoreRefs() {
    return SerializationUtil.intAt(objectData, numInterStoreRefsPos());
  }

  /**
   * @return the offset in objectData representing the start of the inter-store
   *         reference data.
   */
  private final int interStoreRefsPos() {
    return numInterStoreRefsPos() + 4;
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
          offset, objectData.length - ACCESS_POLICY_POS_OFFSET - offset));

      @Override
      public boolean hasNext() {
        return nextInterStoreRefNum < numInterStoreRefs;
      }

      @Override
      public ComparablePair<String, Long> next() {
        if (!hasNext()) throw new NoSuchElementException();
        nextInterStoreRefNum++;
        try {
          return new ComparablePair<>(in.readUTF(), in.readLong());
        } catch (IOException e) {
          throw new InternalError("Unexpected IO exception.", e);
        }
      }

      @Override
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

      // Write the update label reference.
      out.writeBoolean(false);
      if (updateLabelRefIsInterStore())
        out.writeLong(intraStoreRefIt.next());
      else out.writeLong(getUpdateLabelOnum());

      // Save the offset for the access policy reference.
      out.flush();
      int accessPolicyPos = baos.size();

      // Write the access policy reference.
      out.writeBoolean(false);
      if (accessPolicyRefIsInterStore())
        out.writeLong(intraStoreRefIt.next());
      else out.writeLong(getAccessPolicyOnum());

      // Save the offset for the access policy reference.
      out.flush();
      int newClassRefPos = baos.size();

      // Write the ClassRef and number of ref types.
      int oldClassRefPos = classRefPos();
      out.write(objectData, oldClassRefPos, refTypesPos() - oldClassRefPos);

      // Save the offset for the ref-type data.
      out.flush();
      int refTypePos = baos.size() - 4;

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

      // Save the offset for the intrastore-ref data.
      out.flush();
      int intrastoreRefPos = baos.size();

      // Write number of intra-store refs.
      out.writeInt(getNumInterStoreRefs() + numIntraStoreRefs);

      // Write intra-store refs.
      while (intraStoreRefIt.hasNext()) {
        out.writeLong(intraStoreRefIt.next());
      }

      // Save the offset for the Java-serialized data.
      out.flush();
      int serializedDataPos = baos.size();

      // Write length of serialized data, and serialized data.
      out.write(objectData, serializedDataLengthPos(),
          serializedDataLength() + 4);

      // Save the offset for the interstore-ref data.
      out.flush();
      int interstoreRefPos = baos.size();

      // Write number of inter-store refs.
      out.writeInt(0);

      // Write the offsets we've saved.
      out.writeInt(accessPolicyPos);
      out.writeInt(newClassRefPos);
      out.writeInt(refTypePos);
      out.writeInt(intrastoreRefPos);
      out.writeInt(serializedDataPos);
      out.writeInt(interstoreRefPos);

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
   * Wraps a DataOutput and counts the number of bytes written.
   */
  private static class CountingDataOutput implements DataOutput {
    private final DataOutput out;
    private int bytes;

    private CountingDataOutput(DataOutput out) {
      this.out = out;
      this.bytes = 0;
    }

    public int getBytes() {
      return bytes;
    }

    @Override
    public void write(int b) throws IOException {
      out.write(b);
      bytes++;
    }

    @Override
    public void write(byte[] b) throws IOException {
      out.write(b);
      bytes += b.length;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      out.write(b, off, len);
      bytes += len;
    }

    @Override
    public void writeBoolean(boolean v) throws IOException {
      out.writeBoolean(v);
      bytes++;
    }

    @Override
    public void writeByte(int v) throws IOException {
      out.writeByte(v);
      bytes++;
    }

    @Override
    public void writeShort(int v) throws IOException {
      out.writeShort(v);
      bytes += 2;
    }

    @Override
    public void writeChar(int v) throws IOException {
      out.writeChar(v);
      bytes += 2;
    }

    @Override
    public void writeInt(int v) throws IOException {
      out.writeInt(v);
      bytes += 4;
    }

    @Override
    public void writeLong(long v) throws IOException {
      out.writeLong(v);
      bytes += 8;
    }

    @Override
    public void writeFloat(float v) throws IOException {
      out.writeFloat(v);
      bytes += 4;
    }

    @Override
    public void writeDouble(double v) throws IOException {
      out.writeDouble(v);
      bytes += 8;
    }

    @Override
    public void writeBytes(String s) throws IOException {
      out.writeBytes(s);
      bytes += s.length();
    }

    @Override
    public void writeChars(String s) throws IOException {
      out.writeChars(s);
      bytes += 2 * s.length();
    }

    @Override
    public void writeUTF(String s) throws IOException {
      ByteArrayOutputStream baos =
          new ByteArrayOutputStream(2 + 3 * s.length());
      DataOutput out = new DataOutputStream(baos);
      out.writeUTF(s);

      byte[] data = baos.toByteArray();
      this.out.write(data);
      bytes += data.length;
    }
  }

  /**
   * Write impl into a serialized form onto out, including the header.
   */
  public static void write(_Impl impl, DataOutput out) throws IOException {
    write(impl, out, true);
  }

  /**
   * Writes the given _Impl out to the given output stream. The behaviour of
   * this method should mirror write(DataOutput).
   *
   * @param writeHeader true if the header should be written onto out before the
   * rest of the objectData.
   *
   * @see #write(DataOutput)
   * @see #readImpl(Store, DataInput)
   * @see #SerializedObject(DataInput)
   */
  private static void write(_Impl impl, DataOutput out, boolean writeHeader)
      throws IOException {
    CountingDataOutput cdo = new CountingDataOutput(out);
    out = cdo;

    Label updateLabel = impl.get$$updateLabel();
    Store updateLabelStore = updateLabel.$getStore();
    long updateLabelOnum = updateLabel.$getOnum();
    boolean interStoreUpdateLabel =
        !ONumConstants.isGlobalConstant(updateLabelOnum)
            && !impl.$getStore().equals(updateLabelStore);

    // Write out the object header.
    int headerSize = 0;
    if (writeHeader) {
      new Header(impl).write(out);
      headerSize = cdo.getBytes();
    }

    // Write the update label
    out.writeBoolean(interStoreUpdateLabel);
    if (interStoreUpdateLabel) {
      if (updateLabelStore instanceof LocalStore) {
        Class<?> objClass = impl.getClass();
        String message =
            "Creating remote ref to local store.  Remote object has class "
                + objClass + " and its update label is local with onum "
                + updateLabelOnum + ".";
        if (impl.$stackTrace != null) {
          message +=
              "  A stack trace for the creation of the remote object follows.";
          for (StackTraceElement e : impl.$stackTrace)
            message += System.getProperty("line.separator") + "  " + e;

          message += System.getProperty("line.separator")
              + "A stack trace for the creation of the local object follows.";
          for (StackTraceElement e : ((_Impl) updateLabel.fetch()).$stackTrace)
            message += System.getProperty("line.separator") + "  " + e;
        }
        throw new InternalError(message);
      }
      out.writeUTF(updateLabelStore.name());
    }
    out.writeLong(updateLabelOnum);

    int accessPolicyPos = cdo.getBytes() - headerSize;

    // Write the access policy
    ConfPolicy accessPolicy = impl.get$$accessPolicy();
    Store accessPolicyStore = accessPolicy.$getStore();
    long accessPolicyOnum = accessPolicy.$getOnum();
    boolean interStoreAccessLabel =
        !ONumConstants.isGlobalConstant(accessPolicyOnum)
            && !impl.$getStore().equals(accessPolicyStore);

    out.writeBoolean(interStoreAccessLabel);
    if (interStoreAccessLabel) {
      if (accessPolicyStore instanceof LocalStore) {
        Class<?> objClass = impl.getClass();
        String message =
            "Creating remote ref to local store.  Remote object has class "
                + objClass + " and its access policy is local with onum "
                + accessPolicyOnum + ".";
        if (impl.$stackTrace != null) {
          message +=
              "  A stack trace for the creation of the remote object follows.";
          for (StackTraceElement e : impl.$stackTrace)
            message += System.getProperty("line.separator") + "  " + e;

          message += System.getProperty("line.separator")
              + "A stack trace for the creation of the local object follows.";
          for (StackTraceElement e : ((_Impl) updateLabel.fetch()).$stackTrace)
            message += System.getProperty("line.separator") + "  " + e;
        }
        throw new InternalError(message);
      }
      out.writeUTF(accessPolicyStore.name());
    }
    out.writeLong(accessPolicyOnum);

    int classRefPos = cdo.getBytes() - headerSize;

    // Write the object's type information
    Class<?> implClass = impl.getClass();
    ClassRef classRef = ClassRef.makeRef(implClass.getEnclosingClass());
    classRef.write(out);

    // Get the object to serialize itself into a bunch of buffers.
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    List<RefTypeEnum> refTypes = new ArrayList<>();
    List<Long> intraStoreRefs = new ArrayList<>();
    List<Pair<String, Long>> interStoreRefs = new ArrayList<>();
    impl.$serialize(oos, refTypes, intraStoreRefs, interStoreRefs);
    oos.flush();

    byte[] serializedData = bos.toByteArray();

    // Write the object's contents.
    int refTypePos = cdo.getBytes() - headerSize;
    out.writeInt(refTypes.size());
    for (RefTypeEnum refType : refTypes)
      out.writeByte(refType.ordinal());

    int intrastoreRefPos = cdo.getBytes() - headerSize;
    out.writeInt(intraStoreRefs.size());
    for (Long onum : intraStoreRefs)
      out.writeLong(onum);

    int serializedDataPos = cdo.getBytes() - headerSize;
    out.writeInt(serializedData.length);
    out.write(serializedData);

    int interstoreRefPos = cdo.getBytes() - headerSize;
    out.writeInt(interStoreRefs.size());
    for (Pair<String, Long> oid : interStoreRefs) {
      out.writeUTF(oid.first);
      out.writeLong(oid.second);
    }

    out.writeInt(accessPolicyPos);
    out.writeInt(classRefPos);
    out.writeInt(refTypePos);
    out.writeInt(intrastoreRefPos);
    out.writeInt(serializedDataPos);
    out.writeInt(interstoreRefPos);
  }

  /**
   * Writes this SerializedObject out to the given output stream, including the
   * header. The behavior of this method should mirror write(_Impl, DataOutput).
   *
   * @see SerializedObject#write(_Impl, DataOutput)
   * @see SerializedObject#readImpl(Store, DataInput)
   * @see SerializedObject#SerializedObject(DataInput)
   */
  @Override
  public void write(DataOutput out) throws IOException {
    header.write(out);
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

    // Copy the header
    this.header = new Header(in);

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
    SerializationUtil.copyBytes(in, out, numRefTypes, buf);

    int numIntraStoreRefs = in.readInt();
    out.writeInt(numIntraStoreRefs);
    SerializationUtil.copyBytes(in, out, 8 * numIntraStoreRefs, buf);

    int serializedDataLength = in.readInt();
    out.writeInt(serializedDataLength);
    SerializationUtil.copyBytes(in, out, serializedDataLength, buf);

    int numInterStoreRefs = in.readInt();
    out.writeInt(numInterStoreRefs);
    for (int i = 0; i < numInterStoreRefs; i++) {
      // Copy an inter-store ref.
      int len = in.readUnsignedShort();
      out.writeShort(len);
      SerializationUtil.copyBytes(in, out, len + 8, buf);
    }

    // Copy the offset data.
    SerializationUtil.copyBytes(in, out, 4 * 6, buf);

    out.flush();
    bos.flush();
    this.objectData = bos.toByteArray();
  }

  /**
   * Maps class names to their deserialization constructors.
   */
  private static final Map<Class<? extends _Impl>, Constructor<?>> constructorTable =
      Collections.synchronizedMap(
          new HashMap<Class<? extends _Impl>, Constructor<?>>());

  /**
   * Deserializes this object, traversing surrogates as necessary.
   *
   * @param store
   *          The store on which this object lives.
   * @return The deserialized object.
   * @throws ClassNotFoundException
   *           Thrown when the class for this object is unavailable.
   */
  public _Impl deserialize(Store store) {
    return deserialize(store, true);
  }

  /**
   * Deserializes this object.
   *
   * @param store
   *          The store on which this object lives.
   * @param chaseSurrogates
   *          whether surrogates should be traversed.
   * @return The deserialized object.
   * @throws ClassNotFoundException
   *           Thrown when the class for this object is unavailable.
   */
  public _Impl deserialize(Store store, boolean chaseSurrogates) {
    try {
      Class<? extends _Impl> implClass = getClassRef().toImplClass();

      Constructor<?> constructor = constructorTable.get(implClass);

      if (constructor == null) {
        constructor = implClass.getConstructor(Store.class, long.class,
            int.class, long.class, ImmutableObserverSet.class, Store.class,
            long.class, Store.class, long.class, ObjectInput.class,
            Iterator.class, Iterator.class, Iterator.class);
        constructorTable.put(implClass, constructor);
      }

      // Get the reference to the object's update label.
      Store updateLabelStore = store;
      long updateLabelOnum;
      if (updateLabelRefIsInterStore()) {
        ComparablePair<String, Long> ref = getInterStoreUpdateLabelRef();
        updateLabelStore = Worker.getWorker().getStore(ref.first);
        updateLabelOnum = ref.second;
      } else {
        updateLabelOnum = getUpdateLabelOnum();
      }

      // Get the reference to the object's access policy.
      Store accessPolicyStore = store;
      long accessPolicyOnum;
      if (accessPolicyRefIsInterStore()) {
        ComparablePair<String, Long> ref = getInterStoreAccessPolicyRef();
        accessPolicyStore = Worker.getWorker().getStore(ref.first);
        accessPolicyOnum = ref.second;
      } else {
        accessPolicyOnum = getAccessPolicyOnum();
      }

      _Impl result = (_Impl) constructor.newInstance(store, getOnum(),
          getVersion(), getExpiry(), getObservers(), updateLabelStore,
          updateLabelOnum, accessPolicyStore, accessPolicyOnum,
          new ObjectInputStream(getSerializedDataStream()),
          getRefTypeIterator(), getIntraStoreRefIterator(),
          getInterStoreRefIterator());
      result.$associated = getAssociated();

      if (chaseSurrogates && (result instanceof Surrogate)) {
        // Chase the surrogate pointer.
        Surrogate surrogate = (Surrogate) result;
        return new _Proxy(surrogate.store, surrogate.onum).fetch();
      }

      return result;
    } catch (Exception e) {
      throw new InternalError(e);
    }
  }

  /**
   * @return the size of this serialized object, in bytes.
   */
  public int size() {
    return objectData.length;
  }
}
