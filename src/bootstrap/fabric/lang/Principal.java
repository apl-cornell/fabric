package fabric.lang;

import jif.lang.ActsForProof;
import jif.lang.Closure;
import jif.lang.Label;
import fabric.client.Core;

public interface Principal extends jif.lang.Principal, Object {
  public static class $Proxy extends fabric.lang.Object.$Proxy implements
      fabric.lang.Principal {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public native boolean delegatesTo(jif.lang.Principal p);

    public native boolean equals(jif.lang.Principal p);

    public native ActsForProof findProofDownto(Core core, jif.lang.Principal q,
        Object searchState);

    public native ActsForProof findProofUpto(Core core, jif.lang.Principal p,
        Object searchState);

    public native boolean isAuthorized(Object authPrf, Closure closure,
        Label lb, boolean executeNow);

    public native String name();
    
    public static native Principal getInstance(Core core, String name);
  }
}
