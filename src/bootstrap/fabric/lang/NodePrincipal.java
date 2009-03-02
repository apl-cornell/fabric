package fabric.lang;

import jif.lang.ActsForProof;
import jif.lang.Closure;
import jif.lang.Label;
import fabric.client.Core;

public interface NodePrincipal extends Principal, Object {
  public static class $Proxy extends fabric.lang.Object.$Proxy implements
      NodePrincipal {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public native boolean delegatesTo(Principal p);

    public native boolean equals(Principal p);

    public native ActsForProof findProofDownto(Core core, Principal q,
        java.lang.Object searchState);

    public native ActsForProof findProofUpto(Core core, Principal p,
        java.lang.Object searchState);

    public native boolean isAuthorized(java.lang.Object authPrf,
        Closure closure, Label lb, boolean executeNow);

    public native String name();
  }

  public static final class $Impl extends fabric.lang.Object.$Impl implements
      NodePrincipal {
    public $Impl(fabric.client.Core $location, jif.lang.Label $label,
        java.lang.String name) {
      super($location, $label);
    }

    public native boolean delegatesTo(Principal p);

    public native boolean equals(Principal p);

    public native ActsForProof findProofDownto(Core core, Principal q,
        java.lang.Object searchState);

    public native ActsForProof findProofUpto(Core core, Principal p,
        java.lang.Object searchState);

    public native boolean isAuthorized(java.lang.Object authPrf,
        Closure closure, Label lb, boolean executeNow);

    public native String name();
  }
}
