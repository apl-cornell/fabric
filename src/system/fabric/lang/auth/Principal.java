package fabric.lang.auth;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.List;

import fabric.client.Core;
import fabric.client.UnreachableCoreException;
import fabric.common.Pair;
import fabric.core.SerializedObject.RefTypeEnum;
import fabric.lang.Object;

/**
 * A class representing Fabric principals.
 */
//XXX This is currently an empty class.
public interface Principal extends Object {
  public static class $Proxy extends Object.$Proxy implements Principal {
    public static Principal topPrincipal() {
      return Principal.$Impl.topPrincipal();
    }

    public $Proxy(Principal.$Impl impl) {
      super(impl);
    }

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }
  }

  public static class $Impl extends Object.$Impl implements Principal {
    public static Principal topPrincipal() {
      return Principal.$Static.TOP_PRINCIPAL;
    }

    public $Impl(Core location) {
      super(location);
    }

    @Override
    protected Principal.$Proxy $makeProxy() {
      return new Principal.$Proxy(this);
    }

    @Override
    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intracoreRefs, List<Pair<String, Long>> intercoreRefs)
        throws IOException {
      super.$serialize(out, refTypes, intracoreRefs, intercoreRefs);
    }

    public $Impl(Core core, long onum, int version, long label,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intracoreRefs) throws IOException,
        ClassNotFoundException {
      super(core, onum, version, label, in, refTypes, intracoreRefs);
    }
  }

  interface $Static extends fabric.lang.Object.$Static {
    final public static Principal TOP_PRINCIPAL = null; // XXX

    class $Proxy extends Object.$Static.$Proxy implements Principal.$Static {
      public $Proxy(Principal.$Static.$Impl impl) {
        super(impl);
      }
      
      public $Proxy(Core core, long onum) {
        super(core, onum);
      }
    }
    
    class $Impl extends Object.$Static.$Impl implements Principal.$Static {
      public $Impl(Core core) throws UnreachableCoreException {
        super(core);
      }
      
      public $Impl(Core core, Label label) throws UnreachableCoreException {
        super(core, label);
      }
      
      @Override
      protected Object.$Proxy $makeProxy() {
        return new Principal.$Static.$Proxy(this);
      }
    }
  }
}
