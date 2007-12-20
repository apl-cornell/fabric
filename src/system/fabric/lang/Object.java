package fabric.lang;

import java.io.*;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.List;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.TransactionManager;
import fabric.client.UnreachableCoreException;
import fabric.common.*;
import fabric.common.InternalError;
import fabric.core.SerializedObject.RefTypeEnum;

/**
 * All Fabric objects implement this interface.
 */
public interface Object {
  Core $getCore();

  long $getOnum();

  $Proxy $getProxy();

  boolean equals(Object o);

  java.lang.Object $unwrap();

  /**
   * $Proxy objects behave like regular objects by delegating to $Impl objects,
   * pointed to by a soft reference. This class abstracts away the code for
   * maintaining that soft reference.
   */
  public static class $Proxy implements Object, Serializable {
    private final Core core;
    private final long onum;
    private transient SoftReference<$Impl> ref;

    public $Proxy(Core core, long onum) {
      this.core = core;
      this.onum = onum;
      setRef(null);
    }

    public $Proxy($Impl impl) {
      this.core = impl.$core;
      this.onum = impl.$onum;
      setRef(impl);
    }

    private void setRef($Impl impl) {
      ref = new SoftReference<$Impl>(impl);
    }

    protected final $Impl fetch() throws AccessError, UnreachableCoreException {
      $Impl result = ref.get();

      if (result == null) {
        // Object has been evicted.
        result = Client.getClient().fetchObject(core, onum);
        setRef(result);
      }

      return result;
    }

    public final Core $getCore() {
      return core;
    }

    public final long $getOnum() {
      return onum;
    }

    public final $Proxy $getProxy() {
      return fetch().$getProxy();
    }

    public final java.lang.Object $unwrap() {
      return this;
    }

    public static final java.lang.Object $getProxy(java.lang.Object o) {
      if (o instanceof Object) return ((Object) o).$getProxy();
      return o;
    }

    /**
     * A delegate for the default equals implementation.
     */
    @Override
    public boolean equals(java.lang.Object arg1) {
      return fetch().equals(arg1);
    }

    /**
     * A delegate for the default equals implementation.
     */
    public boolean equals(Object arg1) {
      return fetch().equals(arg1);
    }

    /**
     * A delegate for the default hashcode implementation.
     */
    @Override
    public int hashCode() {
      return fetch().hashCode();
    }
  }

  /**
   * $Impl objects hold the actual code and data of Fabric objects and may be
   * evicted from memory.
   */
  public static class $Impl implements Object, Serializable, Cloneable {
    private Core $core;
    private long $onum;
    private transient $Proxy $proxy;

    /**
     * A reference to the class object. TODO Figure out class loading.
     */
    protected $Proxy $class;

    protected Policy $policy;

    public transient int $version;

    /**
     * Creates a new Fabric object that will reside on the given Core.
     */
    public $Impl(Core core) throws UnreachableCoreException {
      // TODO Determine how Policy objects should be managed.
      this(core, new ACLPolicy());
    }

    /**
     * Creates a new Fabric object that will reside on the given Core.
     * 
     * @param core
     *                the location for the object
     * @param policy
     *                the security policy for the object
     */
    public $Impl(Core core, Policy policy) throws UnreachableCoreException {
      this.$core = core;
      this.$onum = core.createOnum();
      this.$version = 0;
      this.$policy = policy;

      // Register the new object with the transaction manager.
      TransactionManager.INSTANCE.registerCreate(this);
    }

    /**
     * Create an $Impl with the given object number
     * 
     * @deprecated this should only be called to create the root object. It does
     *             not contact the core and thus any attempt to commit this
     *             object will fail.
     */
    public $Impl(Core core, long onum) {
      this.$core = core;
      this.$onum = onum;
      this.$version = 0;
      this.$policy = new ACLPolicy();
    }

    @Override
    public final $Impl clone() {
      try {
        return ($Impl) super.clone();
      } catch (Exception e) {
        throw new InternalError(e);
      }
    }

    /**
     * Default equals implementation uses pointer equality.
     */
    @Override
    public boolean equals(java.lang.Object o) {
      if (!(o instanceof Object)) return false;
      return equals((Object) o);
    }

    /**
     * Default equals implementation uses pointer equality.
     */
    public boolean equals(Object o) {
      return o.$getCore().equals($getCore()) && o.$getOnum() == $getOnum();
    }

    /**
     * Default hashCode implementation uses the OID to compute the hash value.
     */
    @Override
    public int hashCode() {
      return (int) ($getCore().hashCode() ^ $getOnum());
    }

    /**
     * This is used to restore the state of the object during transaction
     * roll-back. Subclasses should override this method and call
     * <code>super.copyStateFrom(other)</code>.
     */
    public void $copyStateFrom($Impl other) {
    }

    public final Core $getCore() {
      return $core;
    }

    public final long $getOnum() {
      return $onum;
    }

    public final $Proxy $getClass() {
      return $class;
    }

    public final Policy $getPolicy() {
      return $policy;
    }

    public final int $getVersion() {
      return $version;
    }

    public final $Proxy $getProxy() {
      if ($proxy == null) $proxy = $makeProxy();
      return $proxy;
    }

    /**
     * Serializes the non-transient fields of this object. Subclasses should
     * call the super method first so that inherited fields are written before
     * fields declared in this subclass. The order in which fields are written
     * must be fixed and the same as the order used by the deserialization
     * constructor.
     * 
     * @param serializedOutput
     *                An output stream for writing serialized primitive values
     *                and inlined objects.
     * @param refTypes
     *                A list to which <code>RefTypeEnum</code>s will be
     *                written to indicate the type of reference being serialized
     *                (e.g., null, inlined, intracore, intercore).
     * @param intracoreRefs
     *                A list to which onums denoting intracore references will
     *                be written.
     * @param intercoreRefs
     *                A list to which global object names (hostname/onum pairs),
     *                denoting intercore references, will be written.
     */
    @SuppressWarnings("unused")
    public void $serialize(ObjectOutput serializedOutput,
        List<RefTypeEnum> refTypes, List<Long> intracoreRefs,
        List<Pair<String, Long>> intercoreRefs) throws IOException {
      return;
    }

    /**
     * This is the deserialization constructor and reconstructs the object from
     * its serialized state. Subclasses should call the super constructor to
     * first read inherited fields. It should then read the value of each
     * non-transient field declared in this subclass. The order in which fields
     * are presented is the same as the order used by $serialize.
     * 
     * @param core
     *                The core on which the object lives.
     * @param onum
     *                The object's onum.
     * @param version
     *                The object's version number.
     * @param policy
     *                The object's policy.
     * @param serializedInput
     *                A stream of serialized primitive values and inlined
     *                objects.
     * @param refTypes
     *                An iterator of <code>RefTypeEnum</code>s indicating the
     *                type of each reference being deserialized (e.g., null,
     *                inlined, intracore).
     * @param intracoreRefs
     *                An iterator of intracore references, each represented by
     *                an onum.
     */
    @SuppressWarnings("unused")
    public $Impl(Core core, long onum, int version, Policy policy,
        ObjectInput serializedInput, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intracoreRefs) throws IOException,
        ClassNotFoundException {
      this.$core = core;
      this.$onum = onum;
      this.$policy = policy;
      this.$version = version;
      return;
    }

    /**
     * A helper method for reading a pointer during object deserialization.
     * 
     * @param proxyClass
     *                The expected proxy class for the reference being read.
     * @param refType
     *                The type of reference being read.
     * @param in
     *                The stream from which to read any inlined objects.
     * @param core
     *                The core to use when constructing any intracore
     *                references.
     * @param intracoreRefs
     *                An iterator of intracore references, each represented by
     *                an onum.
     * @throws ClassNotFoundException
     *                 Thrown when the class for a wrapped object is
     *                 unavailable.
     * @throws IOException
     *                 Thrown when an I/O error has occurred in the given
     *                 <code>ObjectInput</code> stream.
     */
    protected static final Object $readRef(
        Class<? extends Object.$Proxy> proxyClass, RefTypeEnum refType,
        ObjectInput in, Core core, Iterator<Long> intracoreRefs)
        throws IOException, ClassNotFoundException {
      switch (refType) {
      case NULL:
        return null;

      case INLINE:
        return WrappedJavaInlineable.$wrap(in.readObject());

      case ONUM:
        try {
          return proxyClass.getConstructor(Core.class, long.class).newInstance(
              core, intracoreRefs.next());
        } catch (Exception e) {
          throw new InternalError(e);
        }

      case REMOTE:
        // These should have been swizzled by the core.
        throw new InternalError(
            "Unexpected remote object reference encountered during deserialization.");
      }

      throw new InternalError(
          "Unknown pointer type while deserializing reference.");
    }

    /**
     * Serializes an object to the given ObjectOutput.
     */
    protected static final void $writeInline(ObjectOutput out,
        java.lang.Object obj) throws IOException {
      if (obj instanceof WrappedJavaInlineable) {
        obj = ((WrappedJavaInlineable<?>) obj).obj;
      }

      out.writeObject(obj);
    }

    /**
     * A helper method for serializing a reference during object serialization.
     * 
     * @param core
     *                The referring object's core.
     * @param obj
     *                The reference to be serialized.
     * @param refType
     *                A list to which a <code>RefTypeEnum</code> will be
     *                written to indicate the type of reference being serialized
     *                (e.g., null, inlined, intracore, intercore).
     * @param out
     *                An output stream for writing inlined objects.
     * @param intracoreRefs
     *                A list for writing intracore references, represented by
     *                onums.
     * @param intercoreRefs
     *                A list for writing denoting intercore references,
     *                represented by global object names (hostname/onum pairs).
     */
    protected static final void $writeRef(Core core, Object obj,
        List<RefTypeEnum> refType, ObjectOutput out, List<Long> intracoreRefs,
        List<Pair<String, Long>> intercoreRefs) throws IOException {
      if (obj == null) {
        refType.add(RefTypeEnum.NULL);
        return;
      }

      if (obj instanceof WrappedJavaInlineable<?>) {
        refType.add(RefTypeEnum.INLINE);
        out.writeObject(obj.$unwrap());
        return;
      }

      $Proxy p = ($Proxy) obj;
      if (p.core.equals(core)) {
        // Intracore reference.
        refType.add(RefTypeEnum.ONUM);
        intracoreRefs.add(p.onum);
        return;
      }

      // Remote reference.
      refType.add(RefTypeEnum.REMOTE);
      intercoreRefs.add(new Pair<String, Long>(p.core.name(), p.onum));
    }

    /**
     * Subclasses should override this method.
     */
    protected $Proxy $makeProxy() {
      return new $Proxy(this);
    }

    public final java.lang.Object $unwrap() {
      return this;
    }

    /**
     * This method changes the core and onum of the object. This method should
     * not be called as it leaves the system in an inconsistent state.
     * 
     * @deprecated
     */
    public final void $forceRelocate(Core c, long onum) {
      this.$core = c;
      this.$onum = onum;
    }
  }

  /**
   * $Static objects hold all static state for the class.
   */
  public static interface $Static extends Object, Serializable, Cloneable {
    public static class $Proxy extends Object.$Proxy implements $Static {
      public $Proxy($Static.$Impl impl) {
        super(impl);
      }

      public $Proxy(Core core, long onum) {
        super(core, onum);
      }
    }

    public static class $Impl extends Object.$Impl implements $Static {
      public $Impl(Core core, Policy policy) throws UnreachableCoreException {
        super(core, policy);
      }

      public $Impl(Core core) throws UnreachableCoreException {
        super(core);
      }

      @Override
      protected Object.$Proxy $makeProxy() {
        return new $Static.$Proxy(this);
      }
    }
  }
}
