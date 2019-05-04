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
    
    public static final byte[] $classHash = new byte[] { -86, 124, 1, -85, -29,
    57, -116, -71, -100, 86, -64, -41, -32, -2, 116, -89, 6, -116, 4, -76, 31,
    -5, -94, 31, -76, -88, -122, -41, 28, -64, 86, -103 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
