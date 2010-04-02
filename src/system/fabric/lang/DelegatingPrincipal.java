package fabric.lang;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.Iterator;

import fabric.lang.security.Label;
import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.common.RefTypeEnum;

public interface DelegatingPrincipal extends Principal {
  void addDelegatesTo(Principal p);

  void removeDelegatesTo(Principal p);

  public static class _Proxy extends Principal._Proxy implements
      DelegatingPrincipal {

    public _Proxy(DelegatingPrincipal._Impl impl) {
      super(impl);
    }

    public _Proxy(Store store, long onum) {
      super(store, onum);
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

    public _Impl(Store store, Label label) {
      super(store, label);
    }

    public _Impl(Store store, long onum, int version, long expiry, long label,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intraStoreRefs) throws IOException,
        ClassNotFoundException {
      super(store, onum, version, expiry, label, in, refTypes, intraStoreRefs);
    }

    public static DelegatingPrincipal $addDefaultDelegates(DelegatingPrincipal p) {
      NodePrincipal store = p.$getStore().getPrincipal();
      p.addDelegatesTo(store);
      
      NodePrincipal worker = Worker.getWorker().getPrincipal();
      p.addDelegatesTo(worker);
      
      return p;
    }
    
    public abstract void addDelegatesTo(Principal p);
    
    public abstract void removeDelegatesTo(Principal p);
  }
}
