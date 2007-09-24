package fabric.core;

import java.io.Serializable;

import fabric.common.Policy;
import fabric.common.Util;
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
    this.object = Util.toArray(obj);
    this.related = related;
    this.onum = obj.$getOnum();
    this.policy = obj.$getPolicy();
  }

  public $Impl getObject() throws ClassNotFoundException {
    $Impl result = ($Impl) Util.fromArray(object);
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

}
