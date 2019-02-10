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
    
    public static final byte[] $classHash = new byte[] { 43, 106, -75, 127, 84,
    50, 105, 42, -125, 29, -79, -26, 53, -52, 122, -28, 112, -95, 126, -8, -29,
    -96, -38, -39, 10, 25, 54, 75, -47, 105, -108, 103 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
