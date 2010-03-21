package fabric.lang;

import jif.lang.AbstractPrincipal;
import jif.lang.ActsForProof;
import jif.lang.Closure;
import jif.lang.Label;
import fabric.worker.Store;

public interface NodePrincipal extends AbstractPrincipal, Object {
  public static class _Proxy extends AbstractPrincipal._Proxy implements
      NodePrincipal {

    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    @Override
    public native boolean delegatesTo(Principal p);

    @Override
    public native boolean equals(Principal p);

    @Override
    public native ActsForProof findProofDownto(Store store, Principal q,
        java.lang.Object searchState);

    @Override
    public native ActsForProof findProofUpto(Store store, Principal p,
        java.lang.Object searchState);

    @Override
    public native boolean isAuthorized(java.lang.Object authPrf,
        Closure closure, Label lb, boolean executeNow);

    @Override
    public native String name();
  }

  public static final class _Impl extends AbstractPrincipal._Impl implements
      NodePrincipal {
    public _Impl(fabric.worker.Store $location, jif.lang.Label $label,
        java.lang.String name) {
      super($location, $label, name);
    }

    @Override
    public native boolean delegatesTo(Principal p);

    @Override
    public native boolean equals(Principal p);

    @Override
    public native ActsForProof findProofDownto(Store store, Principal q,
        java.lang.Object searchState);

    @Override
    public native ActsForProof findProofUpto(Store store, Principal p,
        java.lang.Object searchState);

    @Override
    public native boolean isAuthorized(java.lang.Object authPrf,
        Closure closure, Label lb, boolean executeNow);

    @Override
    public native String name();
  }
}
