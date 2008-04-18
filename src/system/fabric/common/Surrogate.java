package fabric.common;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.List;

import fabric.client.Client;
import fabric.client.Core;
import fabric.core.SerializedObject.RefTypeEnum;
import fabric.lang.Object.$Impl;
import fabric.lang.auth.Label;

/**
 * Encapsulates an intercore pointer.
 */
public final class Surrogate extends $Impl {
  /**
   * The core for the object being pointed to.
   */
  public final Core core;

  /**
   * The onum for the object being pointed to.
   */
  public final long onum;

  /**
   * The host name of the core for the object being pointed to.
   */
  private final String coreName;

  public Surrogate(Core core, long onum, int version, Label label,
      ObjectInput serializedInput, Iterator<RefTypeEnum> refTypes,
      Iterator<Long> intracoreRefs) throws IOException, ClassNotFoundException {
    super(core, onum, version, label, serializedInput, refTypes, intracoreRefs);
    this.coreName = serializedInput.readUTF();
    this.core = Client.getClient().getCore(coreName);
    this.onum = serializedInput.readLong();
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.lang.Object.$Impl#$makeProxy()
   */
  @Override
  protected $Proxy $makeProxy() {
    throw new InternalError("Attempted to make proxy for a surrogate.");
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.lang.Object.$Impl#$serialize(java.io.ObjectOutput,
   *      java.util.List, java.util.List, java.util.List)
   */
  @Override
  public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
      List<Long> intracoreRefs, List<Pair<String, Long>> intercoreRefs) {
    // This should never be called. Surrogates are created in serialized form on
    // the core and should never be transmitted by the client.
    throw new InternalError("Attempted to serialize a surrogate.");
  }
}
