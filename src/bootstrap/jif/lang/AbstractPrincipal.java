package jif.lang;

import fabric.worker.Core;
import fabric.lang.DelegatingPrincipal;
import fabric.lang.Principal;

public interface AbstractPrincipal extends DelegatingPrincipal,
    fabric.lang.Object {
  public static class _Proxy extends DelegatingPrincipal._Proxy implements
      AbstractPrincipal {

    public _Proxy(AbstractPrincipal._Impl impl) {
      super(impl);
    }

    public _Proxy(Core core, long onum) {
      super(core, onum);
    }

    @Override
    public native void addDelegatesTo(Principal p);

    @Override
    public native void removeDelegatesTo(Principal p);
  }

  public abstract static class _Impl extends DelegatingPrincipal._Impl
      implements AbstractPrincipal {

    public _Impl(Core core, Label label, String name) {
      super(core, label);
    }

    public native void addDelegatesTo(Principal p);

    public native void removeDelegatesTo(Principal p);
  }
}
