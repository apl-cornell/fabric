package jif.lang;

import fabric.client.Core;
import fabric.client.UnreachableCoreException;
import fabric.lang.Object;

public interface PrincipalUtil extends Object {
  public interface TopPrincipal extends Principal, Object {
    public static class $Impl extends Object.$Impl implements TopPrincipal {
      public $Impl(Core core, Label label) throws UnreachableCoreException {
        super(core, label);
      }

      public native boolean delegatesTo(Principal p);

      public native boolean equals(Principal p);

      public native ActsForProof findProofDownto(Core core, Principal q,
          Object searchState);

      public native ActsForProof findProofUpto(Core core, Principal p,
          Object searchState);

      public native boolean isAuthorized(Object authPrf, Closure closure,
          Label lb, boolean executeNow);

      public native String name();
    }
  }
}
