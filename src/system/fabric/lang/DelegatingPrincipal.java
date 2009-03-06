package fabric.lang;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.Iterator;

import jif.lang.Label;
import fabric.client.Client;
import fabric.client.Core;
import fabric.common.ONumConstants;
import fabric.common.RefTypeEnum;
import fabric.lang.NodePrincipal;
import fabric.lang.Principal;

public interface DelegatingPrincipal extends Principal {
  void addDelegatesTo(Principal p);

  void removeDelegatesTo(Principal p);

  public static class $Proxy extends Principal.$Proxy implements
      DelegatingPrincipal {

    public $Proxy(DelegatingPrincipal.$Impl impl) {
      super(impl);
    }

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public void addDelegatesTo(Principal p) {
      ((DelegatingPrincipal) fetch()).addDelegatesTo(p);
    }

    public void removeDelegatesTo(Principal p) {
      ((DelegatingPrincipal) fetch()).removeDelegatesTo(p);
    }

    public static DelegatingPrincipal $addDefaultDelegates(DelegatingPrincipal p) {
      return DelegatingPrincipal.$Impl.$addDefaultDelegates(p);
    }
  }

  public abstract static class $Impl extends Principal.$Impl implements
      DelegatingPrincipal {

    public $Impl(Core core, Label label) {
      super(core, label);
    }

    public $Impl(Core core, long onum, int version, long expiry, long label,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intracoreRefs) throws IOException,
        ClassNotFoundException {
      super(core, onum, version, expiry, label, in, refTypes, intracoreRefs);
    }

    public static DelegatingPrincipal $addDefaultDelegates(DelegatingPrincipal p) {
      NodePrincipal core =
          new NodePrincipal.$Proxy(p.$getCore(), ONumConstants.CORE_PRINCIPAL);
      p.addDelegatesTo(core);
      
      NodePrincipal client = Client.getClient().getPrincipal();
      p.addDelegatesTo(client);
      
      return p;
    }
  }
}
