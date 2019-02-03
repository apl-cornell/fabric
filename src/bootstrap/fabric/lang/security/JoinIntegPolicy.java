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
    
    public static final byte[] $classHash = new byte[] { -17, -45, 37, 82, -81,
    8, 91, -19, 119, -90, 120, 44, -94, -104, 34, -20, -28, 24, 35, 99, -8, 22,
    83, -45, 98, -12, -24, 101, -116, 111, 38, 107 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
