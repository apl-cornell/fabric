package fabric.core;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import fabric.client.Core;
import fabric.common.InternalError;
import fabric.common.Pair;
import fabric.common.Policy;
import fabric.common.Surrogate;
import fabric.lang.Object.$Impl;

/**
 * <code>$Impl</code> objects are stored on cores in serialized form as
 * <code>SerializedObject</code>s.
 */
public final class SerializedObject implements Serializable {
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

  private final Policy policy;

  /**
   * The object's primitive field and inlined object data.
   */
  private final byte[] serializedData;

  final List<RefTypeEnum> refTypes;

  /**
   * The onums representing the intra-core references in this object.
   */
  final List<Long> intracoreRefs;

  /**
   * Global object names representing the inter-core references in this object.
   * Before storing any <code>SerializedObject</code>, the core should
   * swizzle these references into intra-core references to surrogates.
   */
  List<Pair<String, Long>> intercoreRefs;

  /**
   * Creates a serialized representation of the given object.
   * 
   * @param obj
   *                The object to serialize.
   */
  public SerializedObject($Impl obj) {
    this.onum = obj.$getOnum();
    this.className = obj.getClass().getName();
    this.policy = obj.$getPolicy();

    ByteArrayOutputStream serializedData = new ByteArrayOutputStream();
    this.refTypes = new ArrayList<RefTypeEnum>();
    this.intracoreRefs = new ArrayList<Long>();
    this.intercoreRefs = new ArrayList<Pair<String, Long>>();

    try {
      ObjectOutputStream oos = new ObjectOutputStream(serializedData);
      obj.$serialize(oos, this.refTypes, this.intracoreRefs, this.intercoreRefs);
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
   * @param policy
   *                The policy for the surrogate.
   * @param remoteRef
   *                The name of the remote object being referred to.
   */
  SerializedObject(long onum, Policy policy, Pair<String, Long> remoteRef) {
    this.onum = onum;
    this.className = Surrogate.class.getName();
    this.policy = policy;
    
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

  public Policy getPolicy() {
    return policy;
  }

  public List<Long> getRelated() {
    return intracoreRefs;
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
          Policy.class, ObjectInput.class, Iterator.class, Iterator.class)
          .newInstance(core, onum, version, policy,
              new ObjectInputStream(new ByteArrayInputStream(serializedData)),
              refTypes.iterator(), intracoreRefs.iterator());
    } catch (Exception e) {
      throw new InternalError(e);
    }
  }
}
