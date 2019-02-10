package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.*;

/**
 * Represents the join of integrity policies. This code is mostly copied from
 * Jif.
 */
public interface JoinIntegPolicy
  extends fabric.lang.security.IntegPolicy, fabric.lang.security.JoinPolicy {
    public fabric.lang.security.JoinIntegPolicy
      fabric$lang$security$JoinIntegPolicy$(fabric.util.Set policies);
    
    public fabric.lang.security.IntegPolicy join(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      java.util.Set s);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      java.util.Set s);
    
    public fabric.lang.security.IntegPolicy join(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p);
    
    public fabric.lang.security.IntegPolicy join(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      boolean simplify);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      boolean simplify);
    
    public fabric.lang.security.IntegPolicy join(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      java.util.Set s, boolean simplify);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      java.util.Set s, boolean simplify);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.security.JoinPolicy._Proxy
      implements fabric.lang.security.JoinIntegPolicy {
        public fabric.lang.security.JoinIntegPolicy
          fabric$lang$security$JoinIntegPolicy$(fabric.util.Set arg1) {
            return ((fabric.lang.security.JoinIntegPolicy) fetch()).
              fabric$lang$security$JoinIntegPolicy$(arg1);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.JoinIntegPolicy) fetch()).join(arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.JoinIntegPolicy) fetch()).meet(arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2) {
            return ((fabric.lang.security.JoinIntegPolicy) fetch()).join(arg1,
                                                                         arg2);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2) {
            return ((fabric.lang.security.JoinIntegPolicy) fetch()).meet(arg1,
                                                                         arg2);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.JoinIntegPolicy) fetch()).join(arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.JoinIntegPolicy) fetch()).meet(arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.JoinIntegPolicy) fetch()).join(arg1,
                                                                         arg2,
                                                                         arg3,
                                                                         arg4);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.JoinIntegPolicy) fetch()).meet(arg1,
                                                                         arg2,
                                                                         arg3,
                                                                         arg4);
        }
        
        public _Proxy(JoinIntegPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl
    extends fabric.lang.security.JoinPolicy._Impl
      implements fabric.lang.security.JoinIntegPolicy {
        public fabric.lang.security.JoinIntegPolicy
          fabric$lang$security$JoinIntegPolicy$(fabric.util.Set policies) {
            fabric$lang$security$JoinPolicy$(policies);
            return (fabric.lang.security.JoinIntegPolicy) this.$getProxy();
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          java.util.Set s) {
            return join(store, p, s, true);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          java.util.Set s) {
            return meet(store, p, s, true);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p) {
            return join(store, p, true);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p) {
            return meet(store, p, true);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              join(store,
                   (fabric.lang.security.JoinIntegPolicy) this.$getProxy(), p,
                   simplify);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              meet(store,
                   (fabric.lang.security.JoinIntegPolicy) this.$getProxy(), p,
                   simplify);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          java.util.Set s, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              join(store,
                   (fabric.lang.security.JoinIntegPolicy) this.$getProxy(), p,
                   s, simplify);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          java.util.Set s, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              meet(store,
                   (fabric.lang.security.JoinIntegPolicy) this.$getProxy(), p,
                   s, simplify);
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.JoinIntegPolicy) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.JoinIntegPolicy._Proxy(this);
        }
        
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
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.JoinIntegPolicy._Static {
            public _Proxy(fabric.lang.security.JoinIntegPolicy._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.JoinIntegPolicy._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  JoinIntegPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.JoinIntegPolicy._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.JoinIntegPolicy._Static._Impl.class);
                $instance = (fabric.lang.security.JoinIntegPolicy._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.JoinIntegPolicy._Static {
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
                return new fabric.lang.security.JoinIntegPolicy._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 120, 69, 95, 124, 17,
    28, -107, -25, -42, 5, -106, 61, -29, -126, -99, 87, 13, 97, -70, 94, 21,
    -19, 118, 35, -46, -2, -109, 22, 17, -47, -66, -57 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
