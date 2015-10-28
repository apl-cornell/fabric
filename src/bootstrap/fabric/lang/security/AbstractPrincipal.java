package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
import fabric.util.Map;
import fabric.util.HashMap;
import fabric.util.Iterator;

/**
 * This is an abstract implementation of the principal interface.
 * It provides convenience methods for delgating authority to superiors.
 */
public interface AbstractPrincipal
  extends fabric.lang.security.DelegatingPrincipal
{
    
    public java.lang.String get$name();
    
    public java.lang.String set$name(java.lang.String val);
    
    public fabric.util.Map get$superiors();
    
    public fabric.util.Map set$superiors(fabric.util.Map val);
    
    public fabric.lang.security.AbstractPrincipal
      fabric$lang$security$AbstractPrincipal$();
    
    public fabric.lang.security.AbstractPrincipal
      fabric$lang$security$AbstractPrincipal$(java.lang.String name);
    
    public fabric.lang.security.AbstractPrincipal
      fabric$lang$security$AbstractPrincipal$(
      java.lang.String name, fabric.lang.security.Principal superior);
    
    public java.lang.String name();
    
    public boolean delegatesTo(fabric.lang.security.Principal p);
    
    public void addDelegatesTo(fabric.lang.security.Principal p);
    
    public void removeDelegatesTo(fabric.lang.security.Principal p);
    
    public boolean superiorsContains(fabric.lang.security.Principal p);
    
    public boolean isAuthorized(java.lang.Object authPrf,
                                fabric.lang.security.Closure closure,
                                fabric.lang.security.Label lb,
                                boolean executeNow);
    
    public fabric.lang.security.ActsForProof findProofDownto(
      fabric.worker.Store store, fabric.lang.security.Principal q,
      java.lang.Object searchState);
    
    public fabric.lang.security.ActsForProof findProofUpto(
      fabric.worker.Store store, fabric.lang.security.Principal p,
      java.lang.Object searchState);
    
    public boolean equals(fabric.lang.Object o);
    
    public boolean equals(fabric.lang.security.Principal p);
    
    public static class _Proxy
    extends fabric.lang.security.DelegatingPrincipal._Proxy
      implements fabric.lang.security.AbstractPrincipal
    {
        
        public java.lang.String get$name() {
            return ((fabric.lang.security.AbstractPrincipal._Impl) fetch()).
              get$name();
        }
        
        public java.lang.String set$name(java.lang.String val) {
            return ((fabric.lang.security.AbstractPrincipal._Impl) fetch()).
              set$name(val);
        }
        
        public fabric.util.Map get$superiors() {
            return ((fabric.lang.security.AbstractPrincipal._Impl) fetch()).
              get$superiors();
        }
        
        public fabric.util.Map set$superiors(fabric.util.Map val) {
            return ((fabric.lang.security.AbstractPrincipal._Impl) fetch()).
              set$superiors(val);
        }
        
        public native fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$();
        
        public native fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$(java.lang.String arg1);
        
        public native fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$(
          java.lang.String arg1, fabric.lang.security.Principal arg2);
        
        public native boolean superiorsContains(
          fabric.lang.security.Principal arg1);
        
        public _Proxy(AbstractPrincipal._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.lang.security.DelegatingPrincipal._Impl
      implements fabric.lang.security.AbstractPrincipal
    {
        
        public java.lang.String get$name() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.name;
        }
        
        public java.lang.String set$name(java.lang.String val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.name = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private java.lang.String name;
        
        public fabric.util.Map get$superiors() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.superiors;
        }
        
        public fabric.util.Map set$superiors(fabric.util.Map val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.superiors = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Map superiors;
        
        public native fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$();
        
        private native void jif$init();
        
        public native fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$(java.lang.String name);
        
        public native fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$(
          java.lang.String name, fabric.lang.security.Principal superior);
        
        public native java.lang.String name();
        
        public native boolean delegatesTo(fabric.lang.security.Principal p);
        
        public native void addDelegatesTo(fabric.lang.security.Principal p);
        
        public native void removeDelegatesTo(fabric.lang.security.Principal p);
        
        public native boolean superiorsContains(
          fabric.lang.security.Principal p);
        
        public native boolean isAuthorized(java.lang.Object authPrf,
                                           fabric.lang.security.Closure closure,
                                           fabric.lang.security.Label lb,
                                           boolean executeNow);
        
        public native fabric.lang.security.ActsForProof findProofDownto(
          fabric.worker.Store store, fabric.lang.security.Principal q,
          java.lang.Object searchState);
        
        public native fabric.lang.security.ActsForProof findProofUpto(
          fabric.worker.Store store, fabric.lang.security.Principal p,
          java.lang.Object searchState);
        
        public native boolean equals(fabric.lang.Object o);
        
        public native boolean equals(fabric.lang.security.Principal p);
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.AbstractPrincipal._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeInline(out, this.name);
            $writeRef($getStore(), this.superiors, refTypes, out,
                      intraStoreRefs, interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.name = (java.lang.String) in.readObject();
            this.superiors = (fabric.util.Map)
                               $readRef(fabric.util.Map._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(),
                                        in,
                                        store,
                                        intraStoreRefs,
                                        interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.AbstractPrincipal._Impl src =
              (fabric.lang.security.AbstractPrincipal._Impl) other;
            this.name = src.name;
            this.superiors = src.superiors;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.AbstractPrincipal._Static
        {
            
            public _Proxy(fabric.lang.security.AbstractPrincipal._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.AbstractPrincipal._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  AbstractPrincipal.
                  _Static.
                  _Impl impl =
                  (fabric.
                    lang.
                    security.
                    AbstractPrincipal.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.AbstractPrincipal._Static.
                        _Impl.class);
                $instance = (fabric.lang.security.AbstractPrincipal._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.AbstractPrincipal._Static
        {
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.security.AbstractPrincipal._Static.
                  _Proxy(
                  this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
