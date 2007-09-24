package fabric.lang;

import java.io.Serializable;
import java.lang.ref.SoftReference;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.TransactionManager;
import fabric.client.UnreachableCoreException;
import fabric.common.ACLPolicy;
import fabric.common.AccessError;
import fabric.common.InternalError;
import fabric.common.Policy;

/**
 * All Fabric objects extend this class.
 */
public abstract class Object {
  final Core core;
  final long onum;

  protected Object(Core core, long onum) {
    this.core = core;
    this.onum = onum;
  }

  public Core $getCore() {
    return core;
  }

  public long $getOnum() {
    return onum;
  }

  /**
   * $Proxy objects behave like regular objects by delegating to $Impl objects,
   * pointed to by a soft reference. This class abstracts away the code for
   * maintaining that soft reference.
   */
  public abstract static class $Proxy extends Object implements Serializable {
    private transient SoftReference<$Impl> ref;

    public $Proxy(Core core, long onum) {
      super(core, onum);
      setRef(null);
    }

    public $Proxy($Impl impl) {
      super(impl.core, impl.onum);
      setRef(impl);
    }

    private void setRef($Impl impl) {
      ref = new SoftReference<$Impl>(impl);
    }

    protected $Impl fetch() throws AccessError, UnreachableCoreException {
      $Impl result = ref.get();

      if (result == null) {
        // Object has been evicted.
        result = Client.getClient().fetchObject(core, onum);
        setRef(result);
      }

      return result;
    }
  }

  /**
   * $Impl objects hold the actual code and data of Fabric objects and may be
   * evicted from memory.
   */
  public abstract static class $Impl extends Object implements Serializable,
      Cloneable {
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
      super(core, core.createOnum());
      this.$version = 0;
      this.$policy = policy;

      // Register the new object with the transaction manager.
      TransactionManager.INSTANCE.registerCreate(this);
    }

    @Override
    public $Impl clone() {
      try {
        return ($Impl) super.clone();
      } catch (Exception e) {
        throw new InternalError(e);
      }
    }

    /**
     * This is used to restore the state of the object during transaction
     * rollback. Overriding methods should call
     * <code>super.copyStateFrom(other)</code>.
     */
    public void $copyStateFrom($Impl other) {
    }

    public $Proxy $getClass() {
      return $class;
    }

    public Policy $getPolicy() {
      return $policy;
    }

    public int $getVersion() {
      return $version;
    }

    public abstract $Proxy $getProxy();
  }
}
