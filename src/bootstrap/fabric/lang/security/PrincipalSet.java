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
    
    public static final byte[] $classHash = new byte[] { 54, -59, 74, 111, -33,
    106, 34, -81, 57, 85, 81, -60, -89, -117, -53, 125, -120, 105, 7, -117, 64,
    52, -90, 118, 91, -98, -21, 88, 88, 58, 21, 72 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
