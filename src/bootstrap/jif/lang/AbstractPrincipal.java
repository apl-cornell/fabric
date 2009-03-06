package jif.lang;

import fabric.client.Core;
import fabric.lang.DelegatingPrincipal;
import fabric.lang.Principal;

public interface AbstractPrincipal extends DelegatingPrincipal,
    fabric.lang.Object {
  public static class $Proxy extends DelegatingPrincipal.$Proxy implements
      AbstractPrincipal {

    public $Proxy(AbstractPrincipal.$Impl impl) {
      super(impl);
    }

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    @Override
    public native void addDelegatesTo(Principal p);

    @Override
    public native void removeDelegatesTo(Principal p);
  }

  public abstract static class $Impl extends DelegatingPrincipal.$Impl
      implements AbstractPrincipal {

    public $Impl(Core core, Label label, String name) {
      super(core, label);
    }

    public native void addDelegatesTo(Principal p);

    public native void removeDelegatesTo(Principal p);
  }
}
