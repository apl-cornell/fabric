package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.LinkedHashSet;
import fabric.util.Set;

/**
 * This code is mostly copied from Jif.
 */
public interface PrincipalSet extends fabric.lang.Object {
    public fabric.util.Set get$set();
    
    public fabric.util.Set set$set(fabric.util.Set val);
    
    public fabric.lang.security.PrincipalSet fabric$lang$security$PrincipalSet$(
      );
    
    public fabric.lang.security.PrincipalSet add(
      fabric.lang.security.Principal p);
    
    public fabric.util.Set getSet();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.PrincipalSet {
        public fabric.util.Set get$set() {
            return ((fabric.lang.security.PrincipalSet._Impl) fetch()).get$set(
                                                                         );
        }
        
        public fabric.util.Set set$set(fabric.util.Set val) {
            return ((fabric.lang.security.PrincipalSet._Impl) fetch()).set$set(
                                                                         val);
        }
        
        public fabric.lang.security.PrincipalSet
          fabric$lang$security$PrincipalSet$() {
            return ((fabric.lang.security.PrincipalSet) fetch()).
              fabric$lang$security$PrincipalSet$();
        }
        
        public fabric.lang.security.PrincipalSet add(
          fabric.lang.security.Principal arg1) {
            return ((fabric.lang.security.PrincipalSet) fetch()).add(arg1);
        }
        
        public fabric.util.Set getSet() {
            return ((fabric.lang.security.PrincipalSet) fetch()).getSet();
        }
        
        public _Proxy(PrincipalSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.PrincipalSet {
        public fabric.util.Set get$set() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.set;
        }
        
        public fabric.util.Set set$set(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.set = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Set set;
        
        public fabric.lang.security.PrincipalSet
          fabric$lang$security$PrincipalSet$() {
            fabric$lang$Object$();
            this.
              set$set(
                (fabric.util.LinkedHashSet)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.util.LinkedHashSet)
                       new fabric.util.LinkedHashSet._Impl(this.$getStore()).
                       $getProxy()).fabric$util$LinkedHashSet$()));
            return (fabric.lang.security.PrincipalSet) this.$getProxy();
        }
        
        public fabric.lang.security.PrincipalSet add(
          fabric.lang.security.Principal p) {
            fabric.lang.security.PrincipalSet
              ps =
              (fabric.lang.security.PrincipalSet)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.lang.security.PrincipalSet)
                     new fabric.lang.security.PrincipalSet._Impl(
                       this.$getStore()).$getProxy()).
                      fabric$lang$security$PrincipalSet$());
            ps.get$set().addAll(this.get$set());
            ps.get$set().add(p);
            return ps;
        }
        
        public fabric.util.Set getSet() { return this.get$set(); }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.PrincipalSet) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.PrincipalSet._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.set, refTypes, out, intraStoreRefs,
                      interStoreRefs);
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
            this.set = (fabric.util.Set)
                         $readRef(fabric.util.Set._Proxy.class,
                                  (fabric.common.RefTypeEnum) refTypes.next(),
                                  in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.PrincipalSet._Impl src =
              (fabric.lang.security.PrincipalSet._Impl) other;
            this.set = src.set;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.PrincipalSet._Static {
            public _Proxy(fabric.lang.security.PrincipalSet._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.PrincipalSet._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  PrincipalSet.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.PrincipalSet._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.PrincipalSet._Static._Impl.class);
                $instance = (fabric.lang.security.PrincipalSet._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.PrincipalSet._Static {
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
                return new fabric.lang.security.PrincipalSet._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -10, -128, 52, -45,
    -125, 41, 78, -126, 90, -64, 29, 95, 5, 90, 63, 15, 3, 120, 73, -64, 69, 28,
    28, 116, 15, 0, 27, -82, 35, 119, 70, -47 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
