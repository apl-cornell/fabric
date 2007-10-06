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
  }

  /**
   * $Impl objects hold the actual code and data of Fabric objects and may be
   * evicted from memory.
   */
  public static class $Impl implements Object, Serializable, Cloneable {
    private final Core $core;
    private final long $onum;
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

    @Override
    public final $Impl clone() {
      try {
        return ($Impl) super.clone();
      } catch (Exception e) {
        throw new InternalError(e);
      }
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
     * Subclasses should override this method.
     */
    protected $Proxy $makeProxy() {
      return new $Proxy(this);
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
