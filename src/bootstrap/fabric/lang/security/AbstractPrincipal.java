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
    
    public static final byte[] $classHash = new byte[] { -95, -120, -37, 43,
    -107, -47, 98, -89, 9, -75, 61, -6, -43, -100, -87, 85, 74, -88, 45, -43,
    -113, 1, -23, -49, 113, -103, 58, -20, -21, -92, -114, -31 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
