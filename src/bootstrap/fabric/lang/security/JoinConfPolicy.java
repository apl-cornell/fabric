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
    
    public static final byte[] $classHash = new byte[] { -101, -61, 8, -43, 100,
    -103, 68, 47, 121, -45, 121, -60, -125, 35, -70, 34, 42, -53, 117, -121,
    -88, -3, 18, 68, -97, 33, -110, -90, 104, -56, 45, 122 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
