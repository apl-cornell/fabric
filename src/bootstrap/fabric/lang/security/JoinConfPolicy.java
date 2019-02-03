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
    
    public static final byte[] $classHash = new byte[] { 85, 102, -101, -103,
    53, 102, 105, 14, -15, 23, 127, 31, -56, -116, -22, -17, 97, -41, -46, 34,
    -94, 95, 111, 22, 106, 113, 111, -91, -47, 8, 10, -91 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
