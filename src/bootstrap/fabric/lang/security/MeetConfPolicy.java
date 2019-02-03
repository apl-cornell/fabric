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
    
    public static final byte[] $classHash = new byte[] { 119, -73, 79, -40,
    -128, 75, -109, -75, 70, -76, -121, 5, -80, -93, 38, -16, 47, -91, 90, -91,
    21, -127, 6, -112, -108, -47, -128, 67, 94, 80, 61, 4 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
