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
    
    public static final byte[] $classHash = new byte[] { -6, -95, 118, -32, -49,
    -121, -54, 81, -50, 112, 118, 61, 8, -62, 0, -47, 96, -46, 50, -56, 112, 30,
    108, -41, 86, -84, 125, -11, -94, -90, 52, 36 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
