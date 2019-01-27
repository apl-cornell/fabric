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
    
    public static final byte[] $classHash = new byte[] { 22, -4, 91, -82, 74,
    74, 45, -49, -39, 109, -20, 100, 15, -69, 51, 83, -43, 25, 94, -120, -92,
    -90, -18, -98, 95, 2, 117, 3, 102, -82, -77, 69 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
