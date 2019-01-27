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
    
    public static final byte[] $classHash = new byte[] { 66, -48, 114, -102, 74,
    -49, -95, 105, -71, -28, 122, 62, -3, -72, -21, 112, -38, -5, -122, 64, -35,
    103, 35, 62, 20, 84, 33, -14, 58, -31, 15, 19 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
