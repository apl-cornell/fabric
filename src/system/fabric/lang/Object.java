package fabric.lang;

import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.SoftReference;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.client.TransactionManager;
import fabric.client.UnreachableCoreException;
import fabric.common.ACLPolicy;
import fabric.common.AccessError;
import fabric.common.InternalError;
import fabric.common.Policy;
import fabric.core.SerializedObject.DataInput;

/**
 * All Fabric objects implement this interface.
 */
public interface Object {
  Core $getCore();

  long $getOnum();

  $Proxy $getProxy();

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
    
    /**
     * A delegate for the default equals implementation.
     */
    @Override
    public boolean equals(java.lang.Object arg1) {
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
     *          the location for the object
     * @param policy
     *          the security policy for the object
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
      this.$policy  = new ACLPolicy();
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
      Object fobj = (Object) o;
      return fobj.$getCore().equals($getCore()) && fobj.$getOnum() == $getOnum();
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
     * rollback. Subclasses should override this method and call
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
     * Returns the number of non-transient fields in this class, including all
     * inherited fields. Public, protected, package, and private fields should
     * all be included. This is in other words the number of fields that will be
     * serialized for objects of this type.
     * 
     * Subclasses should call the super method and add the number of fields
     * declared in the particular subclass.
     */
    public int $numFields() {
      return 0;
    }
    
    /**
     * This method should write to the given DataOutput information about each
     * of the non-transient fields in this class. Subclasses should first call
     * the super method so that information about inherited fields are written
     * out first. The order in which fields are represented must be fixed and
     * the same as the order used by $serialize and $deserialize.
     * 
     * For each field, one byte of information should be written, according to
     * the following format:
     * 
     * TODO
     */
    public void $serializeHeader(DataOutput out) throws IOException {
      return;
    }
    
    /**
     * This method should write each of the non-transient fields of this object
     * to the given DataOutput. Subclasses should call the super method first
     * so that inherited fields are written before fields declared in this
     * subclass. The order in which fields are written must be fixed and the
     * same as the order used by $serializeHeader and $deserialize.
     */
    public void $serialize(DataOutput out) throws IOException {
      return;
    }
    
    /**
     * This is the deserialization constructor and reconstructs the object from
     * its serialized state. Subclasses should call the super constructor to
     * first read inherited fields. It should then read the value of each
     * non-transient field declared in this subclass. The order in which fields
     * are presented in the DataInput is the same as the order used by
     * $serialize.
     */
    public $Impl(DataInput in) throws IOException {
      return;
    }
    
    /**
     * This method serializes an object pointer to the given DataOutput.
     */
    protected static void $writeRef(DataOutput out, java.lang.Object obj)
        throws IOException {
      if (obj == null) {
        out.writeLong(0);
        out.writeLong(0);
      } else {
        $Proxy p = ($Proxy) obj;
        out.writeLong(((RemoteCore) p.core).coreID);
        out.writeLong(p.onum);
      }
    }
    
    /**
     * Subclasses should override this method.
     */
    protected $Proxy $makeProxy() {
      return new $Proxy(this);
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
