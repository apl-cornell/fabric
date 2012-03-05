package fabric.lang.security;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.Iterator;

import fabric.common.RefTypeEnum;
import fabric.worker.Store;
import fabric.worker.Worker;

public interface DelegatingPrincipal extends Principal {
  /**
   * Jif initializer.
   */
  public DelegatingPrincipal fabric$lang$security$DelegatingPrincipal$();
  
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

    @Override
    public DelegatingPrincipal fabric$lang$security$DelegatingPrincipal$() {
      return ((DelegatingPrincipal) fetch())
          .fabric$lang$security$DelegatingPrincipal$();
    }

    @Override
    public void addDelegatesTo(Principal p) {
      ((DelegatingPrincipal) fetch()).addDelegatesTo(p);
    }

    @Override
    public void removeDelegatesTo(Principal p) {
      ((DelegatingPrincipal) fetch()).removeDelegatesTo(p);
    }

    public static DelegatingPrincipal $addDefaultDelegates(DelegatingPrincipal p) {
      return DelegatingPrincipal._Impl.$addDefaultDelegates(p);
    }
  }

  public abstract static class _Impl extends Principal._Impl implements
      DelegatingPrincipal {

    public _Impl(Store store) {
      super(store);
    }

    public _Impl(Store store, long onum, int version, long expiry, long label,
        long accessLabel, ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intraStoreRefs) throws IOException,
        ClassNotFoundException {
      super(store, onum, version, expiry, label, accessLabel, in, refTypes,
          intraStoreRefs);
    }

    public static DelegatingPrincipal $addDefaultDelegates(DelegatingPrincipal p) {
      NodePrincipal store = p.$getStore().getPrincipal();
      p.addDelegatesTo(store);
      
      NodePrincipal worker = Worker.getWorker().getPrincipal();
      p.addDelegatesTo(worker);
      
      return p;
    }
    
    @Override
    public DelegatingPrincipal fabric$lang$security$DelegatingPrincipal$() {
      fabric$lang$security$Principal$();
      return (DelegatingPrincipal) this.$getProxy();
    }

    @Override
    public abstract void addDelegatesTo(Principal p);
    
    @Override
    public abstract void removeDelegatesTo(Principal p);
  }
}
