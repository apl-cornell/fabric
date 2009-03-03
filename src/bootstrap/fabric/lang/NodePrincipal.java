package fabric.lang;

import jif.lang.ActsForProof;
import jif.lang.Closure;
import jif.lang.Label;
import fabric.client.Core;

public interface NodePrincipal extends Principal, Object {
  public static class $Proxy extends Principal.$Proxy implements
      NodePrincipal {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    @Override
    public native boolean delegatesTo(Principal p);

    @Override
    public native boolean equals(Principal p);

    @Override
    public native ActsForProof findProofDownto(Core core, Principal q,
        java.lang.Object searchState);

    @Override
    public native ActsForProof findProofUpto(Core core, Principal p,
        java.lang.Object searchState);

    @Override
    public native boolean isAuthorized(java.lang.Object authPrf,
        Closure closure, Label lb, boolean executeNow);

    @Override
    public native String name();
  }

  public static final class $Impl extends Principal.$Impl implements
      NodePrincipal {
    public $Impl(fabric.client.Core $location, jif.lang.Label $label,
        java.lang.String name) {
      super($location, $label);
    }

    @Override
    public native boolean delegatesTo(Principal p);

    @Override
    public native boolean equals(Principal p);

    @Override
    public native ActsForProof findProofDownto(Core core, Principal q,
        java.lang.Object searchState);

    @Override
    public native ActsForProof findProofUpto(Core core, Principal p,
        java.lang.Object searchState);

    @Override
    public native boolean isAuthorized(java.lang.Object authPrf,
        Closure closure, Label lb, boolean executeNow);

    @Override
    public native String name();
  }
}
