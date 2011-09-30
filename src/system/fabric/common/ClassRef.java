package fabric.common;

import java.io.*;
import java.net.URL;
import java.util.Arrays;

import fabric.common.exceptions.InternalError;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.FClass._Proxy;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Encapsulates a reference to a class object.
 */
public abstract class ClassRef implements FastSerializable {

  /**
   * Gives a mapping between ClassRef types and ordinals. Used for efficient
   * encoding and decoding of the type of a ClassRef during serialization and
   * deserialization.
   */
  static enum ClassRefType {
    PLATFORM {
      @Override
      /**
       * @param pos
       *          the starting position of a serialized representation of a
       *          PlatformClassRef object.
       */
      ClassRef deserialize(byte[] data, int pos) {
        return new PlatformClassRef(data, pos);
      }

      @Override
      void copySerialization(DataInput in, DataOutput out, byte[] buf)
          throws IOException {
        PlatformClassRef.copySerialization(in, out, buf);
      }

      /**
       * @param pos
       *          the starting position of a serialized representation of a
       *          PlatformClassRef object.
       */
      @Override
      int lengthAt(byte[] data, int pos) {
        return PlatformClassRef.lengthAt(data, pos);
      }
    },
    FABRIC {
      /**
       * @param pos
       *          the starting position of a serialized representation of a
       *          FabricClassRef object.
       */
      @Override
      ClassRef deserialize(byte[] data, int pos) {
        return new FabricClassRef(data, pos);
      }

      @Override
      void copySerialization(DataInput in, DataOutput out, byte[] buf)
          throws IOException {
        FabricClassRef.copySerialization(in, out, buf);
      }

      /**
       * @param pos
       *          the starting position of a serialized representation of a
       *          FabricClassRef object.
       */
      @Override
      int lengthAt(byte[] data, int pos) {
        return FabricClassRef.lengthAt(data, pos);
      }
    };

    abstract ClassRef deserialize(byte[] data, int pos);

    abstract void copySerialization(DataInput in, DataOutput out, byte[] buf)
        throws IOException;

    abstract int lengthAt(byte[] data, int pos);
  }

  /**
   * ClassRef for fabric.common.Surrogate.
   */
  public static final ClassRef SURROGATE = new PlatformClassRef(
      Surrogate.class.getName());

  /**
   * The <code>ClassRefType</code> corresponding to this class.
   */
  private ClassRefType type;

  /**
   * Memoizes the class's hash. This is null if the hash has not yet been
   * computed.
   */
  private byte[] hash;

  private ClassRef(ClassRefType type) {
    this.type = type;
    this.hash = null;
  }

  /**
   * Factory method.
   */
  @SuppressWarnings("unchecked")
  public static ClassRef makeRef(Class<?> clazz) {
    boolean isPlatformClass = SysUtil.codebasePart(clazz.getName()).equals("");
    if (isPlatformClass) return new PlatformClassRef(clazz.getName());
    return new FabricClassRef((Class<? extends fabric.lang.Object>) clazz);
  }

  /**
   * Computes and returns the class's hash.
   */
  abstract byte[] getHashImpl();

  byte[] getHash() {
    if (hash != null) return hash;
    return this.hash = getHashImpl();
  }

  public abstract String javaClassName();

  /**
   * @return the Java Class object for this class.
   */
  public final Class<?> toClass() {
    try {
      return Class.forName(javaClassName());
    } catch (ClassNotFoundException e) {
      // Class not loaded yet.
      if (Worker.isInitialized()) {
        try {
          return Worker.getWorker().getClassLoader().findClass(javaClassName());
        } catch (ClassNotFoundException e1) {
          throw new InternalError(e1);
        }
      }
      throw new InternalError(e);
    }
  }

  protected void checkHash(byte[] hash) {
    byte[] myHash = getHash();

    boolean badHash = hash.length != myHash.length;

    for (int i = 0; !badHash && i < hash.length; i++) {
      badHash |= hash[i] != myHash[i];
    }

    if (badHash) {
      try {
        URL path = SysUtil.locateClass(javaClassName());
        throw new InternalError(new InvalidClassException(javaClassName(),
            "A class of the same name was found, but its hash did not match "
                + "the hash given in a network message" + "\n" + "hash from: "
                + path));
      } catch (ClassNotFoundException e) {
        throw new InternalError(e);
      }
    }
  }

  /**
   * ClassRef for classes not stored in Fabric.
   */
  private static final class PlatformClassRef extends ClassRef {
    private String className;

    /**
     * @param className
     *          the name of the class being referenced.
     */
    private PlatformClassRef(String className) {
      super(ClassRefType.PLATFORM);
      this.className = className;
    }

    @Override
    public String javaClassName() {
      return className;
    }

    @Override
    byte[] getHashImpl() {
      try {
        return SysUtil.hashClass(className);
      } catch (ClassNotFoundException e) {
        throw new InternalError(e);
      } catch (IOException e) {
        throw new InternalError(e);
      }
    }

    // ////////////////////////////////////////////////////////////////////////
    // Serialization cruft.

    /**
     * Serialized format:
     * <ul>
     * <li>short length of class name</li>
     * <li>byte[] class name</li>
     * <li>short class hash length</li>
     * <li>byte[] class hash</li>
     * </ul>
     */
    @Override
    protected void writeImpl(DataOutput out) throws IOException {
      byte[] className = this.className.getBytes("UTF-8");
      byte[] hash = getHash();

      out.writeShort(className.length);
      out.write(className);
      out.writeShort(hash.length);
      out.write(hash);
    }

    /**
     * Deserializes a PlatformClassRef object from the given byte array.
     * 
     * @param pos
     *          the starting position of a serialized representation of a
     *          PlatformClassRef object.
     */
    private PlatformClassRef(byte[] data, int pos) {
      this(className(data, pos));
      checkHash(classHash(data, pos));
    }

    /**
     * Copies a serialized PlatformClassRef from the given DataInput to the
     * given DataOutput.
     */
    static void copySerialization(DataInput in, DataOutput out, byte[] buf)
        throws IOException {
      int classNameLength = in.readShort();
      out.writeShort(classNameLength);
      SerializationUtil.copyBytes(in, out, classNameLength, buf);
      
      int classHashLength = in.readShort();
      out.writeShort(classHashLength);
      SerializationUtil.copyBytes(in, out, classHashLength, buf);
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          PlatformClassRef object.
     * @return the length of the serialized representation of a PlatformClassRef
     *         object starting at the given position in the given byte array.
     */
    static int lengthAt(byte[] data, int pos) {
      return classHashLengthPos(data, pos) + 2 + classHashLength(data, pos)
          - pos;
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          PlatformClassRef object.
     * @return position of classname-length field in serialized representation
     *         of PlatformClassRef starting at given position in the given byte
     *         array.
     */
    private static int classNameLengthPos(byte[] data, int pos) {
      return pos;
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          PlatformClassRef object.
     * @return length of class name in serialized representation of
     *         PlatformClassRef starting at given position in the given byte
     *         array.
     */
    private static int classNameLength(byte[] data, int pos) {
      return SerializationUtil.unsignedShortAt(data,
          classNameLengthPos(data, pos));
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          PlatformClassRef object.
     * @return class name in serialized representation of PlatformClassRef
     *         starting at given position in the given byte array.
     */
    private static String className(byte[] data, int pos) {
      int nameLength = classNameLength(data, pos);
      int nameDataPos = classNameLengthPos(data, pos) + 2;
      byte[] nameUTF8 =
          Arrays.copyOfRange(data, nameDataPos, nameDataPos + nameLength);
      try {
        return new String(nameUTF8, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        throw new InternalError(e);
      }
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          PlatformClassRef object.
     * @return position of class-hash-length field in serialized representation
     *         of PlatformClassRef starting at given position in the given byte
     *         array.
     */
    private static int classHashLengthPos(byte[] data, int pos) {
      return classNameLengthPos(data, pos) + 2 + classNameLength(data, pos);
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          PlatformClassRef object.
     * @return length of class hash in serialized representation of
     *         PlatformClassRef starting at given position in the given byte
     *         array.
     */
    private static int classHashLength(byte[] data, int pos) {
      return SerializationUtil.unsignedShortAt(data,
          classHashLengthPos(data, pos));
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          PlatformClassRef object.
     * @return the class hash in serialized representation of PlatformClassRef
     *         starting at given position in the given byte array.
     */
    private static byte[] classHash(byte[] data, int pos) {
      int classHashPos = classHashLengthPos(data, pos) + 2;
      return Arrays.copyOfRange(data, classHashPos, classHashPos
          + classHashLength(data, pos));
    }
  }

  /**
   * ClassRef for classes stored in Fabric.
   */
  public static final class FabricClassRef extends ClassRef {

    /**
     * The OID of the class object, encapsulated as a proxy for the class's
     * FClass object.
     * <p>
     * Either <code>clazz</code> or (<code>codebase</code> and
     * <code>className</code>) must be non-null. If both are non-null, they are
     * assumed to refer to the same class object.
     * </p>
     */
    private FClass._Proxy fClass;

    /**
     * The OID of the class's codebase, encapsulated as a proxy for the
     * codebase's Codebase object. This is null for system classes.
     * <p>
     * Either <code>clazz</code> or (<code>codebase</code> and
     * <code>className</code>) must be non-null. If both are non-null, they are
     * assumed to refer to the same class object.
     * </p>
     */
    private Codebase._Proxy codebase;

    /**
     * The Java name of the class, relative to <code>codebase</code>.
     * <p>
     * Either <code>clazz</code> or (<code>codebase</code> and
     * <code>className</code>) must be non-null. If both are non-null, they are
     * assumed to refer to the same class object.
     * </p>
     */
    private String className;

    /**
     * @param clazz
     *          the class being referenced. This must be the interface
     *          corresponding to the Fabric type, and not the _Proxy or _Impl
     *          classes.
     */
    public FabricClassRef(Class<? extends fabric.lang.Object> clazz) {
      super(ClassRefType.FABRIC);

      this.fClass = (FClass._Proxy) SysUtil.toProxy(clazz.getCanonicalName());
      this.codebase = null;
      this.className = null;
    }

    private FabricClassRef(Codebase._Proxy codebase, String className) {
      super(ClassRefType.FABRIC);
      this.fClass = null;
      this.codebase = codebase;
      this.className = className;
    }

    private FabricClassRef(FClass._Proxy fClass) {
      super(ClassRefType.FABRIC);
      this.fClass = fClass;
      this.codebase = null;
      this.className = null;
    }

    @Override
    public String javaClassName() {
      return SysUtil.pseudoname(getFClass());
    }

    @Override
    byte[] getHashImpl() {
      try {
        return SysUtil.hashClass(javaClassName());
      } catch (ClassNotFoundException e) {
        throw new InternalError(e);
      } catch (IOException e) {
        throw new InternalError(e);
      }
    }

    FClass._Proxy getFClass() {
      if (fClass != null) return fClass;
      return (_Proxy) codebase.resolveClassName(className);
    }

    // ////////////////////////////////////////////////////////////////////////
    // Serialization cruft.

    /**
     * Serialized format:
     * <ul>
     * <li>short length of class OID's store's name</li>
     * <li>byte[] class OID's store's name</li>
     * <li>long class OID's onum</li>
     * <li>short class hash length</li>
     * <li>byte[] class hash</li>
     * </ul>
     */
    @Override
    protected void writeImpl(DataOutput out) throws IOException {
      // Ensure the clazz field is populated and the class has been hashed.
      FClass._Proxy fClass = getFClass();
      byte[] hash = getHash();

      String storeName = fClass.$getStore().name();
      long onum = fClass.$getOnum();

      byte[] storeNameUTF = storeName.getBytes("UTF-8");

      out.writeShort(storeNameUTF.length);
      out.write(storeNameUTF);
      out.writeLong(onum);
      out.writeShort(hash.length);
      out.write(hash);
    }

    /**
     * Deserializes a FabricClassRef object from the given byte array.
     * 
     * @param pos
     *          the starting position of a serialized representation of a
     *          FabricClassRef object.
     */
    private FabricClassRef(byte[] data, int pos) {
      this(new FClass._Proxy(store(data, pos), onum(data, pos)));
      checkHash(classHash(data, pos));
    }

    /**
     * Deserializes a FabricClassRef object from the given DataInput.
     */
    public FabricClassRef(DataInput in) throws IOException {
      super(ClassRefType.FABRIC);
      
      byte[] storeNameData = new byte[in.readShort()];
      in.readFully(storeNameData);
      String storeName = new String(storeNameData, "UTF-8");
      Store store = Worker.getWorker().getStore(storeName);
      
      long onum = in.readLong();
      this.fClass = new FClass._Proxy(store, onum);
      this.codebase = null;
      this.className = null;
      
      byte[] hash = new byte[in.readShort()];
      in.readFully(hash);
      checkHash(hash);
    }

    /**
     * Copies a serialized FabricClassRef from the given DataInput to the given
     * DataOutput.
     */
    static void copySerialization(DataInput in, DataOutput out, byte[] buf)
        throws IOException {
      int storeNameLength = in.readShort();
      out.writeShort(storeNameLength);
      SerializationUtil.copyBytes(in, out, storeNameLength, buf);
      
      long onum = in.readLong();
      out.writeLong(onum);

      int classHashLength = in.readShort();
      out.writeShort(classHashLength);
      SerializationUtil.copyBytes(in, out, classHashLength, buf);
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          FabricClassRef object.
     * @return the length of the serialized representation of a FabricClassRef
     *         object starting at the given position in the given byte array.
     */
    static int lengthAt(byte[] data, int pos) {
      return classHashLengthPos(data, pos) + 2 + classHashLength(data, pos)
          - pos;
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          FabricClassRef object.
     * @return position of store-name-length field in serialized representation
     *         of FabricClassRef starting at given position in the given byte
     *         array.
     */
    private static int storeNameLengthPos(byte[] data, int pos) {
      return pos;
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          FabricClassRef object.
     * @return the length of store name in serialized representation of
     *         FabricClassRef starting at given position in the given byte
     *         array.
     */
    private static int storeNameLength(byte[] data, int pos) {
      int x =
          SerializationUtil
              .unsignedShortAt(data, storeNameLengthPos(data, pos));
      return x;
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          FabricClassRef object.
     * @return store name in serialized representation of FabricClassRef
     *         starting at given position in the given byte array.
     */
    private static String storeName(byte[] data, int pos) {
      int nameLength = storeNameLength(data, pos);
      int nameDataPos = storeNameLengthPos(data, pos) + 2;
      byte[] nameUTF8 =
          Arrays.copyOfRange(data, nameDataPos, nameDataPos + nameLength);
      try {
        return new String(nameUTF8, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        throw new InternalError(e);
      }
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          FabricClassRef object.
     * @return store named in serialized representation of FabricClassRef
     *         starting at given position in the given byte array.
     */
    private static Store store(byte[] data, int pos) {
      String name = storeName(data, pos);
      return Worker.getWorker().getStore(name);
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          FabricClassRef object.
     * @return position of onum field in serialized representation of
     *         FabricClassRef starting at given position in the given byte
     *         array.
     */
    private static int onumPos(byte[] data, int pos) {
      int x = storeNameLengthPos(data, pos) + 2 + storeNameLength(data, pos);
      return x;
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          FabricClassRef object.
     * @return onum in serialized representation of FabricClassRef starting at
     *         given position in the given byte array.
     */
    private static long onum(byte[] data, int pos) {
      return SerializationUtil.longAt(data, onumPos(data, pos));
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          FabricClassRef object.
     * @return position of class-hash-length field in serialized representation
     *         of FabricClassRef starting at given position in the given byte
     *         array.
     */
    private static int classHashLengthPos(byte[] data, int pos) {
      return onumPos(data, pos) + 4;
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          FabricClassRef object.
     * @return length of class hash in serialized representation of
     *         FabricClassRef starting at given position in the given byte
     *         array.
     */
    private static int classHashLength(byte[] data, int pos) {
      return SerializationUtil.unsignedShortAt(data,
          classHashLengthPos(data, pos));
    }

    /**
     * @param pos
     *          the starting position of a serialized representation of a
     *          FabricClassRef object.
     * @return the class hash in serialized representation of FabricClassRef
     *         starting at given position in the given byte array.
     */
    private static byte[] classHash(byte[] data, int pos) {
      int classHashPos = classHashLengthPos(data, pos) + 2;
      return Arrays.copyOfRange(data, classHashPos, classHashPos
          + classHashLength(data, pos));
    }
  }

  // //////////////////////////////////////////////////////////////////////////
  // Serialization cruft.

  /**
   * Serialized format:
   * <ul>
   * <li>byte ClassRefType</li>
   * <li>byte[] ClassRef-specific data, specified by <code>writeImpl</code></li>
   * </ul>
   */
  public final void write(DataOutput out) throws IOException {
    out.writeByte(type.ordinal());
    writeImpl(out);
  }

  protected abstract void writeImpl(DataOutput out) throws IOException;

  /**
   * Copies a deserialized ClassRef from the given DataInput to the given
   * DataOutput, using the given buffer.
   */
  static void copySerialization(DataInput in, DataOutput out, byte[] buf)
      throws IOException {
    int ord = in.readByte();
    out.writeByte(ord);
    ClassRefType type = ClassRefType.values()[ord];
    type.copySerialization(in, out, buf);
  }

  /**
   * Deserializes a ClassRef starting at the given position in the given byte
   * array.
   */
  public static ClassRef deserialize(byte[] data, int pos) {
    ClassRefType type = ClassRefType.values()[data[pos]];
    return type.deserialize(data, pos + 1);
  }

  /**
   * @return the length of the ClassRef data occurring at the given offset
   *         position in the given byte array.
   */
  static int lengthAt(byte[] data, int pos) {
    ClassRefType type = ClassRefType.values()[data[pos]];
    return 1 + type.lengthAt(data, pos + 1);
  }
}
