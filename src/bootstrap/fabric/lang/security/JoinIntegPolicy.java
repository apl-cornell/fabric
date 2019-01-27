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
    
    public static final byte[] $classHash = new byte[] { 116, -124, -7, -10,
    -106, -17, 21, -104, 115, -100, 31, -10, 111, 119, 65, -89, -64, -48, -92,
    -36, -57, 89, 121, -35, -90, -71, -92, 122, -96, -10, -114, -96 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
