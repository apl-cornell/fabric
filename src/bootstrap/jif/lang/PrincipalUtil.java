package jif.lang;

import fabric.client.Core;
import fabric.client.UnreachableNodeException;
import fabric.lang.Object;
import fabric.lang.Principal;

public interface PrincipalUtil extends Object {
  public interface TopPrincipal extends Principal, Object {
    public static class $Impl extends Principal.$Impl implements TopPrincipal {
      public $Impl(Core core, Label label) throws UnreachableNodeException {
        super(core, label);
      }

      @Override
      public native boolean delegatesTo(Principal p);

      @Override
      public native boolean equals(Principal p);

      @Override
      public native ActsForProof findProofDownto(Core core, Principal q,
          Object searchState);

      @Override
      public native ActsForProof findProofUpto(Core core, Principal p,
          Object searchState);

      @Override
      public native boolean isAuthorized(Object authPrf, Closure closure,
          Label lb, boolean executeNow);

      @Override
      public native String name();
    }
  }
}
