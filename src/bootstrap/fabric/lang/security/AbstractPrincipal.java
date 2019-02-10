package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Map;
import fabric.util.HashMap;
import fabric.util.Iterator;

/**
 * This is an abstract implementation of the principal interface.
 * It provides convenience methods for delgating authority to superiors.
 */
public interface AbstractPrincipal
  extends fabric.lang.security.DelegatingPrincipal {
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
      implements fabric.lang.security.AbstractPrincipal {
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
        
        public fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$() {
            return ((fabric.lang.security.AbstractPrincipal) fetch()).
              fabric$lang$security$AbstractPrincipal$();
        }
        
        public fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$(java.lang.String arg1) {
            return ((fabric.lang.security.AbstractPrincipal) fetch()).
              fabric$lang$security$AbstractPrincipal$(arg1);
        }
        
        public fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$(
          java.lang.String arg1, fabric.lang.security.Principal arg2) {
            return ((fabric.lang.security.AbstractPrincipal) fetch()).
              fabric$lang$security$AbstractPrincipal$(arg1, arg2);
        }
        
        public boolean superiorsContains(fabric.lang.security.Principal arg1) {
            return ((fabric.lang.security.AbstractPrincipal) fetch()).
              superiorsContains(arg1);
        }
        
        public _Proxy(AbstractPrincipal._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.lang.security.DelegatingPrincipal._Impl
      implements fabric.lang.security.AbstractPrincipal {
        public java.lang.String get$name() { return this.name; }
        
        public java.lang.String set$name(java.lang.String val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.name = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private java.lang.String name;
        
        public fabric.util.Map get$superiors() { return this.superiors; }
        
        public fabric.util.Map set$superiors(fabric.util.Map val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.superiors = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Map superiors;
        
        public fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$() {
            ((fabric.lang.security.AbstractPrincipal._Impl) this.fetch()).
              jif$init();
            fabric$lang$security$DelegatingPrincipal$();
            return (fabric.lang.security.AbstractPrincipal) this.$getProxy();
        }
        
        private void jif$init() {
            this.
              set$superiors(
                (fabric.util.HashMap)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.util.HashMap)
                       new fabric.util.HashMap._Impl(this.$getStore()).
                       $getProxy()).fabric$util$HashMap$()));
            this.get$superiors().keySet();
        }
        
        public fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$(java.lang.String name) {
            ((fabric.lang.security.AbstractPrincipal._Impl) this.fetch()).
              jif$init();
            fabric$lang$security$AbstractPrincipal$();
            this.set$name(name);
            return (fabric.lang.security.AbstractPrincipal) this.$getProxy();
        }
        
        public fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$(
          java.lang.String name, fabric.lang.security.Principal superior) {
            fabric$lang$security$AbstractPrincipal$(name);
            addDelegatesTo(superior);
            return (fabric.lang.security.AbstractPrincipal) this.$getProxy();
        }
        
        public java.lang.String name() { return this.get$name(); }
        
        public boolean delegatesTo(fabric.lang.security.Principal p) {
            return superiorsContains(p);
        }
        
        public void addDelegatesTo(fabric.lang.security.Principal p) {
            if (fabric.lang.Object._Proxy.
                  idEquals(
                    this.get$superiors().
                        put(p,
                            (fabric.lang.security.AbstractPrincipal)
                              this.$getProxy()),
                    null)) {
                fabric.lang.security.PrincipalUtil._Impl.
                  notifyNewDelegation((fabric.lang.security.AbstractPrincipal)
                                        this.$getProxy(), p);
            }
        }
        
        public void removeDelegatesTo(fabric.lang.security.Principal p) {
            if (!fabric.lang.Object._Proxy.idEquals(
                                             this.get$superiors().remove(p),
                                             null)) {
                fabric.lang.security.PrincipalUtil._Impl.
                  notifyRevokeDelegation(
                    (fabric.lang.security.AbstractPrincipal) this.$getProxy(),
                    p);
            }
        }
        
        public boolean superiorsContains(fabric.lang.security.Principal p) {
            return this.get$superiors().containsKey(p);
        }
        
        public boolean isAuthorized(java.lang.Object authPrf,
                                    fabric.lang.security.Closure closure,
                                    fabric.lang.security.Label lb,
                                    boolean executeNow) { return false; }
        
        public fabric.lang.security.ActsForProof findProofDownto(
          fabric.worker.Store store, fabric.lang.security.Principal q,
          java.lang.Object searchState) {
            return null;
        }
        
        public fabric.lang.security.ActsForProof findProofUpto(
          fabric.worker.Store store, fabric.lang.security.Principal p,
          java.lang.Object searchState) {
            fabric.worker.LocalStore localStore =
              fabric.worker.Worker.getWorker().getLocalStore();
            fabric.util.Iterator i = this.get$superiors().keySet().iterator();
            while (i.hasNext()) {
                fabric.lang.security.Principal s =
                  (fabric.lang.security.Principal)
                    fabric.lang.Object._Proxy.$getProxy(i.next());
                fabric.lang.security.ActsForProof prf =
                  fabric.lang.security.PrincipalUtil._Impl.findActsForProof(
                                                             store, p, s,
                                                             searchState);
                if (!fabric.lang.Object._Proxy.idEquals(prf, null)) {
                    if (fabric.lang.security.PrincipalUtil._Impl.
                          actsFor(s,
                                  (fabric.lang.security.AbstractPrincipal)
                                    this.$getProxy())) {
                        return (fabric.lang.security.TransitiveProof)
                                 fabric.lang.Object._Proxy.
                                 $getProxy(
                                   ((fabric.lang.security.TransitiveProof)
                                      new fabric.lang.security.TransitiveProof.
                                        _Impl(localStore).
                                      $getProxy()).
                                       fabric$lang$security$TransitiveProof$(
                                         prf,
                                         s,
                                         (fabric.lang.security.DelegatesProof)
                                           fabric.lang.Object._Proxy.
                                           $getProxy(
                                             ((fabric.lang.security.DelegatesProof)
                                                new fabric.lang.security.
                                                  DelegatesProof._Impl(
                                                  localStore).
                                                $getProxy()).
                                                 fabric$lang$security$DelegatesProof$(
                                                   s,
                                                   (fabric.lang.security.AbstractPrincipal)
                                                     this.$getProxy()))));
                    }
                }
            }
            return null;
        }
        
        public boolean equals(fabric.lang.Object o) {
            return fabric.lang.Object._Proxy.
              $getProxy(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.
                  $unwrap(o)) instanceof fabric.lang.security.Principal &&
              equals((fabric.lang.security.Principal)
                       fabric.lang.Object._Proxy.$getProxy(o));
        }
        
        public boolean equals(fabric.lang.security.Principal p) {
            return fabric.lang.Object._Proxy.
              idEquals((fabric.lang.security.AbstractPrincipal)
                         this.$getProxy(), p);
        }
        
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
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.name = (java.lang.String) in.readObject();
            this.superiors = (fabric.util.Map)
                               $readRef(fabric.util.Map._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(), in, store,
                                        intraStoreRefs, interStoreRefs);
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
          implements fabric.lang.security.AbstractPrincipal._Static {
            public _Proxy(fabric.lang.security.AbstractPrincipal._Static.
                            _Impl impl) { super(impl); }
            
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
                  _Impl
                  impl =
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
          implements fabric.lang.security.AbstractPrincipal._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.security.AbstractPrincipal._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 98, -63, -32, 22, 12,
    85, 58, -20, 117, 60, 102, -11, 86, 97, 67, 124, -5, 28, -113, -27, 1, 38,
    99, 30, -2, 115, -50, -15, -91, -16, 56, 14 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
