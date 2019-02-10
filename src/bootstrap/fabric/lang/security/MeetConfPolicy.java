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
    
    public static final byte[] $classHash = new byte[] { 88, -100, 61, 23, 95,
    -45, 29, -62, -30, -58, 50, -112, 108, -26, -8, 89, 25, -83, 48, -83, -88,
    36, -23, 93, -64, -35, 32, 100, 72, 103, -50, -28 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
