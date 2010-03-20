package jif.lang;

import fabric.worker.Core;
import fabric.net.UnreachableNodeException;
import fabric.lang.Object;
import fabric.lang.Principal;

public interface PrincipalUtil extends Object {
  public interface TopPrincipal extends Principal, Object {
    public static class _Impl extends Principal._Impl implements TopPrincipal {
      public _Impl(Core core, Label label) throws UnreachableNodeException {
        super(core, label);
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
}
