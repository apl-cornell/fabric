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
    
    public static final byte[] $classHash = new byte[] { -128, -95, -71, -125,
    -99, -128, 74, -30, 39, 25, 15, -64, -95, -121, -119, -3, -48, 123, -55,
    -79, -63, 22, 116, -17, -108, 20, -53, 103, -81, 30, -109, -18 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
