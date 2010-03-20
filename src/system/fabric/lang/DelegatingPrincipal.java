package fabric.lang;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.Iterator;

import jif.lang.Label;
import fabric.worker.Worker;
import fabric.worker.Core;
import fabric.common.RefTypeEnum;

public interface DelegatingPrincipal extends Principal {
  void addDelegatesTo(Principal p);

  void removeDelegatesTo(Principal p);

  public static class _Proxy extends Principal._Proxy implements
      DelegatingPrincipal {

    public _Proxy(DelegatingPrincipal._Impl impl) {
      super(impl);
    }

    public _Proxy(Core core, long onum) {
      super(core, onum);
    }

    public void addDelegatesTo(Principal p) {
      ((DelegatingPrincipal) fetch()).addDelegatesTo(p);
    }

    public void removeDelegatesTo(Principal p) {
      ((DelegatingPrincipal) fetch()).removeDelegatesTo(p);
    }

    public static DelegatingPrincipal $addDefaultDelegates(DelegatingPrincipal p) {
      return DelegatingPrincipal._Impl.$addDefaultDelegates(p);
    }
  }

  public abstract static class _Impl extends Principal._Impl implements
      DelegatingPrincipal {

    public _Impl(Core core, Label label) {
      super(core, label);
    }

    public _Impl(Core core, long onum, int version, long expiry, long label,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intracoreRefs) throws IOException,
        ClassNotFoundException {
      super(core, onum, version, expiry, label, in, refTypes, intracoreRefs);
    }

    public static DelegatingPrincipal $addDefaultDelegates(DelegatingPrincipal p) {
      NodePrincipal core = p.$getCore().getPrincipal();
      p.addDelegatesTo(core);
      
      NodePrincipal worker = Worker.getWorker().getPrincipal();
      p.addDelegatesTo(worker);
      
      return p;
    }
    
    public abstract void addDelegatesTo(Principal p);
    
    public abstract void removeDelegatesTo(Principal p);
  }
}
