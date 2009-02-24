package fabric.lang;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;

import jif.lang.Label;
import fabric.client.*;
import fabric.client.remote.RemoteClient;
import fabric.client.transaction.Log;
import fabric.client.transaction.ReadMapEntry;
import fabric.client.transaction.TransactionManager;
import fabric.common.*;
import fabric.common.InternalError;

/**
 * All Fabric objects implement this interface.
 */
public interface Object {

  /** The core where the object resides. */
  Core $getCore();

  /** The object's onum. */
  long $getOnum();

  /** A proxy for this object. */
  $Proxy $getProxy();

  /** Label for this object */
  Label get$label();

  Label set$label(Label label);

  /** Whether this object is "equal" to another object. */
  boolean equals(Object o);

  /** Unwraps a wrapped Java inlineable. */
  java.lang.Object $unwrap();

  /** Fetches the object if this is a proxy; returns itself if it's an impl. */
  Object fetch();

  /**
   * <p>
   * This method changes the onum of the object. Unless if you <i>really</i>
   * know what you're doing, you should <b>not</b> call this, as it leaves the
   * system in an inconsistent state.
   * </p>
   * <p>
   * This method is used to initialize object stores with objects at well-known
   * onums (e.g., naming map and core principal).
   * </p>
   * 
   * @deprecated
   */
  void $forceRenumber(long onum);

  /**
   * $Proxy objects behave like regular objects by delegating to $Impl objects,
   * pointed to by a soft reference. This class abstracts away the code for
   * maintaining that soft reference.
   */
  public static class $Proxy implements Object {
    private transient FabricSoftRef ref;

    /**
     * This is used only to pin the $Impl in the case where it's a object on the
     * local core.
     */
    @SuppressWarnings("unused")
    private transient final $Impl anchor;

    public $Proxy(Core core, long onum) {
      if (core.isLocalCore() && onum != ONumConstants.EMPTY_LABEL
          && onum != ONumConstants.PUBLIC_READONLY_LABEL)
        throw new InternalError(
            "Attempted to create unresolved reference to a local object.");

      this.ref = new FabricSoftRef(core, onum, null);
      this.anchor = null;
    }

    public $Proxy($Impl impl) {
      this.ref = impl.$ref;
      Core core = impl.$getCore();
      if (core instanceof LocalCore)
        this.anchor = impl;
      else this.anchor = null;
    }

    public final $Impl fetch() {
      $Impl result = ref.get();

      if (result == null) {
        // Object has been evicted.
        try {
          // Check the current transaction's create map.
          TransactionManager tm = TransactionManager.getInstance();
          RemoteClient client = tm.getFetchClient(this);
          if (client != null) {
            // Fetch from the client.
            result = client.readObject(tm.getCurrentTid(), ref.core, ref.onum);
          } else if (this instanceof KeyObject) {
            // Fetch from the core. Bypass dissemination when reading key
            // objects.
            result = ref.core.readObjectNoDissem(ref.onum);
          } else {
            // Fetch from the core.
            result = ref.core.readObject(ref.onum);
          }
        } catch (FetchException e) {
          // TODO figure out how to communicate error
          e.printStackTrace();
        }

        ref = result.$ref;
      }

      return result;
    }

    public final Core $getCore() {
      return ref.core;
    }

    public final long $getOnum() {
      return ref.onum;
    }

    public final Label get$label() {
      return fetch().get$label();
    }

    public final Label set$label(Label label) {
      return fetch().set$label(label);
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
     * A delegate for the default toString implementation.
     */
    @Override
    public String toString() {
      return fetch().toString();
    }

    /**
     * A delegate for the default hashcode implementation.
     */
    @Override
    public int hashCode() {
      return fetch().hashCode();
    }

    /**
     * <p>
     * This method changes the onum of the object. Unless if you <i>really</i>
     * know what you're doing, you should <b>not</b> call this, as it leaves the
     * system in an inconsistent state.
     * </p>
     * <p>
     * This method is used to initialize object stores with objects at
     * well-known onums (e.g., naming map and core principal).
     * </p>
     * 
     * @deprecated
     */
    public final void $forceRenumber(long onum) {
      fetch().$forceRenumber(onum);
    }
  }

  /**
   * $Impl objects hold the actual code and data of Fabric objects and may be
   * evicted from memory.
   */
  public static class $Impl implements Object, Cloneable {
    /**
     * The cached exact proxy for this object.
     */
    private $Proxy $proxy;

    public final FabricSoftRef $ref;

    /**
     * A reference to the class object. TODO Figure out class loading.
     */
    protected $Proxy $class;

    protected Label $label;

    public int $version;

    // *********************************************************
    // The following fields are used for transaction management.
    // They should stay on the client and should not be sent to
    // the core.
    // *********************************************************

    /**
     * The unique running transaction that can write to the object, or null if
     * none. (This is either null or holds the same value as $writeLockHolder.
     */
    public Log $writer;

    /**
     * The innermost transaction that is holding a write lock on the object.
     */
    public Log $writeLockHolder;

    /**
     * Any transaction that has logged a read of the object, or null if none.
     */
    public Log $reader;

    /**
     * Modification log. Holds the state of the object at the beginning of the
     * transaction that currently holds the write lock. A transaction has
     * acquired a write lock on an object if any entry in this history has
     * $writer set to that transaction's log.
     */
    public $Impl $history;

    /**
     * A reference to the global read list for this object.
     * 
     * @see fabric.client.transaction.TransactionManager#readMap
     */
    public ReadMapEntry $readMapEntry;

    /**
     * The number of threads waiting on this object.
     */
    public int $numWaiting;

    /**
     * Whether this client owns the most up-to-date copy of the object.
     */
    public boolean $isOwned;

    /**
     * The version number on the last update-map that was checked.
     */
    public int $updateMapVersion;

    /**
     * A private constructor for initializing transaction-management state.
     */
    private $Impl(Core core, long onum, int version, Label label) {
      this.$version = version;
      this.$writer = null;
      this.$writeLockHolder = null;
      this.$reader = Log.NO_READER;
      this.$history = null;
      this.$numWaiting = 0;
      this.$ref = new FabricSoftRef(core, onum, this);
      this.$readMapEntry = TransactionManager.getReadMapEntry(this);
      this.$ref.readMapEntry(this.$readMapEntry);
      this.$isOwned = false;
      this.$updateMapVersion = -1;

      // By default, labels are public read-only.
      if (label == null && this instanceof Label)
        label = Client.getClient().getLocalCore().getPublicReadonlyLabel();

      if (label == null) throw new InternalError("Null label!");

      this.$label = label;
    }

    /**
     * Creates a new Fabric object that will reside on the given Core.
     * 
     * @param core
     *          the location for the object
     * @param label
     *          the security label for the object
     */
    public $Impl(Core core, Label label) throws UnreachableNodeException {
      this(core, core.createOnum(), 0, label);

      // Register the new object with the transaction manager.
      TransactionManager.getInstance().registerCreate(this);
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
      return (int) $getOnum();
    }

    /**
     * Default toString implementation prints out the class name and global
     * object name.
     */
    @Override
    public String toString() {
      return getClass().getName() + "@fab://" + $getCore().name() + "/"
          + $getOnum();
    }

    /**
     * This is used to restore the state of the object during transaction
     * roll-back.
     */
    public final void $copyStateFrom($Impl other) {
      $writer = null;
      $writeLockHolder = other.$writeLockHolder;
      $reader = other.$reader;
      $history = other.$history;
      $isOwned = other.$isOwned;
      $updateMapVersion = other.$updateMapVersion;
      $copyAppStateFrom(other);
    }

    /**
     * This copies the application state of the object. Subclasses should
     * override this method and call
     * <code>super.copyAppStateFrom(other)</code>.
     */
    public void $copyAppStateFrom($Impl other) {
    }

    public final Core $getCore() {
      return $ref.core;
    }

    public final long $getOnum() {
      return $ref.onum;
    }

    public final $Proxy $getClass() {
      return $class;
    }

    public final Label get$label() {
      return $label;
    }

    public final Label set$label(Label label) {
      $label = label;
      return label;
    }

    public final int $getVersion() {
      return $version;
    }

    public final $Proxy $getProxy() {
      if ($proxy == null) $proxy = $makeProxy();
      return $proxy;
    }

    public final $Impl fetch() {
      return this;
    }

    /**
     * Serializes the non-transient fields of this object. Subclasses should
     * call the super method first so that inherited fields are written before
     * fields declared in this subclass. The order in which fields are written
     * must be fixed and the same as the order used by the deserialization
     * constructor.
     * 
     * @param serializedOutput
     *          An output stream for writing serialized primitive values and
     *          inlined objects.
     * @param refTypes
     *          A list to which <code>RefTypeEnum</code>s will be written to
     *          indicate the type of reference being serialized (e.g., null,
     *          inlined, intracore, intercore).
     * @param intracoreRefs
     *          A list to which onums denoting intracore references will be
     *          written.
     * @param intercoreRefs
     *          A list to which global object names (hostname/onum pairs),
     *          denoting intercore references, will be written.
     */
    @SuppressWarnings("unused")
    public void $serialize(ObjectOutput serializedOutput,
        List<RefTypeEnum> refTypes, List<Long> intracoreRefs,
        List<Pair<String, Long>> intercoreRefs) throws IOException {
      // Nothing to output here. SerializedObject.write($Impl, DataOutput) takes
      // care of writing the onum, version, label onum, and type information.
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
     *          The core on which the object lives.
     * @param onum
     *          The object's onum.
     * @param version
     *          The object's version number.
     * @param label
     *          Onum of the object's label.
     * @param serializedInput
     *          A stream of serialized primitive values and inlined objects.
     * @param refTypes
     *          An iterator of <code>RefTypeEnum</code>s indicating the type of
     *          each reference being deserialized (e.g., null, inlined,
     *          intracore).
     * @param intracoreRefs
     *          An iterator of intracore references, each represented by an
     *          onum.
     */
    @SuppressWarnings("unused")
    public $Impl(Core core, long onum, int version, long label,
        ObjectInput serializedInput, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intracoreRefs) throws IOException,
        ClassNotFoundException {
      this(core, onum, version, new Label.$Proxy(core, label));
    }

    /**
     * A helper method for reading a pointer during object deserialization.
     * 
     * @param proxyClass
     *          The expected proxy class for the reference being read.
     * @param refType
     *          The type of reference being read.
     * @param in
     *          The stream from which to read any inlined objects.
     * @param core
     *          The core to use when constructing any intracore references.
     * @param intracoreRefs
     *          An iterator of intracore references, each represented by an
     *          onum.
     * @throws ClassNotFoundException
     *           Thrown when the class for a wrapped object is unavailable.
     * @throws IOException
     *           Thrown when an I/O error has occurred in the given
     *           <code>ObjectInput</code> stream.
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
     *          The referring object's core.
     * @param obj
     *          The reference to be serialized.
     * @param refType
     *          A list to which a <code>RefTypeEnum</code> will be written to
     *          indicate the type of reference being serialized (e.g., null,
     *          inlined, intracore, intercore).
     * @param out
     *          An output stream for writing inlined objects.
     * @param intracoreRefs
     *          A list for writing intracore references, represented by onums.
     * @param intercoreRefs
     *          A list for writing denoting intercore references, represented by
     *          global object names (hostname/onum pairs).
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
      if (ONumConstants.isGlobalConstant(p.ref.onum) || p.ref.core.equals(core)) {
        // Intracore reference.
        refType.add(RefTypeEnum.ONUM);
        intracoreRefs.add(p.ref.onum);
        return;
      }

      // Remote reference.
      if (p.ref.core instanceof LocalCore) {
        Class<?> objClass = obj.getClass();
        String objStr = obj.toString();
        throw new InternalError(
            "Creating remote ref to local core.  Object on local core has class "
                + objClass + ".  Its string representation is \"" + objStr
                + "\".");
      }
      refType.add(RefTypeEnum.REMOTE);
      intercoreRefs.add(new Pair<String, Long>(p.ref.core.name(), p.ref.onum));
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
     * <p>
     * This method changes the onum of the object. Unless if you <i>really</i>
     * know what you're doing, you should <b>not</b> call this, as it leaves the
     * system in an inconsistent state.
     * </p>
     * <p>
     * This method is used to initialize object stores with objects at
     * well-known onums (e.g., naming map and core principal).
     * </p>
     * 
     * @deprecated
     */
    public final void $forceRenumber(long onum) {
      this.$ref.onum = onum;
    }
  }

  /**
   * $Static objects hold all static state for the class.
   */
  public static interface $Static extends Object, Cloneable {
    public static class $Proxy extends Object.$Proxy implements $Static {
      public $Proxy($Static.$Impl impl) {
        super(impl);
      }

      public $Proxy(Core core, long onum) {
        super(core, onum);
      }

      /**
       * Used to initialize the $Static.$Proxy.$instance variables.
       * 
       * @param c
       *          The class to instantiate.
       */
      public static final Object $makeStaticInstance(
          final Class<? extends Object.$Impl> c) {
        // XXX Need a real core and a real label. (Should be given as args.)
        final LocalCore core = Client.getClient().getLocalCore();

        return Client.runInTransaction(new Client.Code<Object>() {
          public Object run() {
            try {
              Constructor<? extends Object.$Impl> constr =
                  c.getConstructor(Core.class, Label.class);
              Label emptyLabel = core.getEmptyLabel();
              return constr.newInstance(core, emptyLabel);
            } catch (Exception e) {
              throw new AbortException(e);
            }
          }
        });
      }
    }

    public static class $Impl extends Object.$Impl implements $Static {
      public $Impl(Core core, Label label) throws UnreachableNodeException {
        super(core, label);
      }

      @Override
      protected Object.$Proxy $makeProxy() {
        return new $Static.$Proxy(this);
      }
    }
  }

}
