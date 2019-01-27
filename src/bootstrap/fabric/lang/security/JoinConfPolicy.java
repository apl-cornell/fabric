package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.*;

/**
 * Represents the join of confidentiality policies. This code is mostly copied
 * from Jif.
 */
public interface JoinConfPolicy
  extends fabric.lang.security.ConfPolicy, fabric.lang.security.JoinPolicy {
    public fabric.lang.security.JoinConfPolicy
      fabric$lang$security$JoinConfPolicy$(fabric.util.Set policies);
    
    public fabric.lang.security.ConfPolicy join(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
      java.util.Set s);
    
    public fabric.lang.security.ConfPolicy meet(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
      java.util.Set s);
    
    public fabric.lang.security.ConfPolicy join(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p);
    
    public fabric.lang.security.ConfPolicy meet(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p);
    
    public fabric.lang.security.ConfPolicy join(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
      boolean simplify);
    
    public fabric.lang.security.ConfPolicy meet(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
      boolean simplify);
    
    public fabric.lang.security.ConfPolicy join(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
      java.util.Set s, boolean simplify);
    
    public fabric.lang.security.ConfPolicy meet(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
      java.util.Set s, boolean simplify);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.security.JoinPolicy._Proxy
      implements fabric.lang.security.JoinConfPolicy {
        public fabric.lang.security.JoinConfPolicy
          fabric$lang$security$JoinConfPolicy$(fabric.util.Set arg1) {
            return ((fabric.lang.security.JoinConfPolicy) fetch()).
              fabric$lang$security$JoinConfPolicy$(arg1);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.JoinConfPolicy) fetch()).join(arg1,
                                                                        arg2,
                                                                        arg3);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.JoinConfPolicy) fetch()).meet(arg1,
                                                                        arg2,
                                                                        arg3);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2) {
            return ((fabric.lang.security.JoinConfPolicy) fetch()).join(arg1,
                                                                        arg2);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2) {
            return ((fabric.lang.security.JoinConfPolicy) fetch()).meet(arg1,
                                                                        arg2);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.JoinConfPolicy) fetch()).join(arg1,
                                                                        arg2,
                                                                        arg3);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.JoinConfPolicy) fetch()).meet(arg1,
                                                                        arg2,
                                                                        arg3);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.JoinConfPolicy) fetch()).join(arg1,
                                                                        arg2,
                                                                        arg3,
                                                                        arg4);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.JoinConfPolicy) fetch()).meet(arg1,
                                                                        arg2,
                                                                        arg3,
                                                                        arg4);
        }
        
        public _Proxy(JoinConfPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl
    extends fabric.lang.security.JoinPolicy._Impl
      implements fabric.lang.security.JoinConfPolicy {
        public fabric.lang.security.JoinConfPolicy
          fabric$lang$security$JoinConfPolicy$(fabric.util.Set policies) {
            fabric$lang$security$JoinPolicy$(policies);
            return (fabric.lang.security.JoinConfPolicy) this.$getProxy();
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          java.util.Set s) {
            return join(store, p, s, true);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          java.util.Set s) {
            return meet(store, p, s, true);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p) {
            return join(store, p, true);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p) {
            return meet(store, p, true);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              join(store,
                   (fabric.lang.security.JoinConfPolicy) this.$getProxy(), p,
                   simplify);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              meet(store,
                   (fabric.lang.security.JoinConfPolicy) this.$getProxy(), p,
                   simplify);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          java.util.Set s, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              join(store,
                   (fabric.lang.security.JoinConfPolicy) this.$getProxy(), p, s,
                   simplify);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          java.util.Set s, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              meet(store,
                   (fabric.lang.security.JoinConfPolicy) this.$getProxy(), p, s,
                   simplify);
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.JoinConfPolicy) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.JoinConfPolicy._Proxy(this);
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
          implements fabric.lang.security.JoinConfPolicy._Static {
            public _Proxy(fabric.lang.security.JoinConfPolicy._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.JoinConfPolicy._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  JoinConfPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.JoinConfPolicy._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.JoinConfPolicy._Static._Impl.class);
                $instance = (fabric.lang.security.JoinConfPolicy._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.JoinConfPolicy._Static {
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
                return new fabric.lang.security.JoinConfPolicy._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 36, 9, 82, 82, -1, 12,
    -67, 20, -79, -31, 62, -21, 28, -52, 123, -55, 53, 6, 103, 8, -22, -18, -61,
    -66, -56, 96, -64, -124, 27, 52, 88, -81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
