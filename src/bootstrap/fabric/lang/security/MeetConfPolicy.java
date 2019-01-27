package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.*;

/**
 * Represents the meet of confidentiality policies. This code is mostly copied
 * from Jif.
 */
public interface MeetConfPolicy
  extends fabric.lang.security.ConfPolicy, fabric.lang.security.MeetPolicy {
    public fabric.lang.security.MeetConfPolicy
      fabric$lang$security$MeetConfPolicy$(fabric.util.Set policies);
    
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
    
    public static class _Proxy extends fabric.lang.security.MeetPolicy._Proxy
      implements fabric.lang.security.MeetConfPolicy {
        public fabric.lang.security.MeetConfPolicy
          fabric$lang$security$MeetConfPolicy$(fabric.util.Set arg1) {
            return ((fabric.lang.security.MeetConfPolicy) fetch()).
              fabric$lang$security$MeetConfPolicy$(arg1);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.MeetConfPolicy) fetch()).join(arg1,
                                                                        arg2,
                                                                        arg3);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.MeetConfPolicy) fetch()).meet(arg1,
                                                                        arg2,
                                                                        arg3);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2) {
            return ((fabric.lang.security.MeetConfPolicy) fetch()).join(arg1,
                                                                        arg2);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2) {
            return ((fabric.lang.security.MeetConfPolicy) fetch()).meet(arg1,
                                                                        arg2);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.MeetConfPolicy) fetch()).join(arg1,
                                                                        arg2,
                                                                        arg3);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.MeetConfPolicy) fetch()).meet(arg1,
                                                                        arg2,
                                                                        arg3);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.MeetConfPolicy) fetch()).join(arg1,
                                                                        arg2,
                                                                        arg3,
                                                                        arg4);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.MeetConfPolicy) fetch()).meet(arg1,
                                                                        arg2,
                                                                        arg3,
                                                                        arg4);
        }
        
        public _Proxy(MeetConfPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl
    extends fabric.lang.security.MeetPolicy._Impl
      implements fabric.lang.security.MeetConfPolicy {
        public fabric.lang.security.MeetConfPolicy
          fabric$lang$security$MeetConfPolicy$(fabric.util.Set policies) {
            fabric$lang$security$MeetPolicy$(policies);
            return (fabric.lang.security.MeetConfPolicy) this.$getProxy();
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
                   (fabric.lang.security.MeetConfPolicy) this.$getProxy(), p,
                   simplify);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              meet(store,
                   (fabric.lang.security.MeetConfPolicy) this.$getProxy(), p,
                   simplify);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          java.util.Set s, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              join(store,
                   (fabric.lang.security.MeetConfPolicy) this.$getProxy(), p, s,
                   simplify);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          java.util.Set s, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              meet(store,
                   (fabric.lang.security.MeetConfPolicy) this.$getProxy(), p, s,
                   simplify);
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.MeetConfPolicy) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.MeetConfPolicy._Proxy(this);
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
          implements fabric.lang.security.MeetConfPolicy._Static {
            public _Proxy(fabric.lang.security.MeetConfPolicy._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.MeetConfPolicy._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  MeetConfPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.MeetConfPolicy._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.MeetConfPolicy._Static._Impl.class);
                $instance = (fabric.lang.security.MeetConfPolicy._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.MeetConfPolicy._Static {
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
                return new fabric.lang.security.MeetConfPolicy._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 82, 37, 69, 43, -47,
    -8, 91, -53, -28, -34, 96, 104, -114, 54, 27, -127, 89, -23, 123, -115, -66,
    -17, -53, 66, -19, -5, 123, 16, -23, 55, -111, 103 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
