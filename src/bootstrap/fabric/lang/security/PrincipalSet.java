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
    
    public static final byte[] $classHash = new byte[] { -44, -83, 45, -60, 113,
    -1, 84, 102, 22, 85, -46, 111, 90, -30, -13, -56, -19, 46, -65, -21, -70,
    -33, 119, -30, -117, 78, -88, -29, -89, -2, 118, -31 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
