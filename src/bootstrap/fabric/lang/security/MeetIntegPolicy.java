package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.*;

/**
 * Represents the meet of integrity policies. This code is mostly copied from
 * Jif.
 */
public interface MeetIntegPolicy
  extends fabric.lang.security.IntegPolicy, fabric.lang.security.MeetPolicy {
    public fabric.lang.security.MeetIntegPolicy
      fabric$lang$security$MeetIntegPolicy$(fabric.util.Set policies);
    
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
    
    public static class _Proxy extends fabric.lang.security.MeetPolicy._Proxy
      implements fabric.lang.security.MeetIntegPolicy {
        public fabric.lang.security.MeetIntegPolicy
          fabric$lang$security$MeetIntegPolicy$(fabric.util.Set arg1) {
            return ((fabric.lang.security.MeetIntegPolicy) fetch()).
              fabric$lang$security$MeetIntegPolicy$(arg1);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.MeetIntegPolicy) fetch()).join(arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.MeetIntegPolicy) fetch()).meet(arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2) {
            return ((fabric.lang.security.MeetIntegPolicy) fetch()).join(arg1,
                                                                         arg2);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2) {
            return ((fabric.lang.security.MeetIntegPolicy) fetch()).meet(arg1,
                                                                         arg2);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.MeetIntegPolicy) fetch()).join(arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.MeetIntegPolicy) fetch()).meet(arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.MeetIntegPolicy) fetch()).join(arg1,
                                                                         arg2,
                                                                         arg3,
                                                                         arg4);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.MeetIntegPolicy) fetch()).meet(arg1,
                                                                         arg2,
                                                                         arg3,
                                                                         arg4);
        }
        
        public _Proxy(MeetIntegPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl
    extends fabric.lang.security.MeetPolicy._Impl
      implements fabric.lang.security.MeetIntegPolicy {
        public fabric.lang.security.MeetIntegPolicy
          fabric$lang$security$MeetIntegPolicy$(fabric.util.Set policies) {
            fabric$lang$security$MeetPolicy$(policies);
            return (fabric.lang.security.MeetIntegPolicy) this.$getProxy();
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
                   (fabric.lang.security.MeetIntegPolicy) this.$getProxy(), p,
                   simplify);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              meet(store,
                   (fabric.lang.security.MeetIntegPolicy) this.$getProxy(), p,
                   simplify);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          java.util.Set s, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              join(store,
                   (fabric.lang.security.MeetIntegPolicy) this.$getProxy(), p,
                   s, simplify);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          java.util.Set s, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              meet(store,
                   (fabric.lang.security.MeetIntegPolicy) this.$getProxy(), p,
                   s, simplify);
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.MeetIntegPolicy) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.MeetIntegPolicy._Proxy(this);
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
          implements fabric.lang.security.MeetIntegPolicy._Static {
            public _Proxy(fabric.lang.security.MeetIntegPolicy._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.MeetIntegPolicy._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  MeetIntegPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.MeetIntegPolicy._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.MeetIntegPolicy._Static._Impl.class);
                $instance = (fabric.lang.security.MeetIntegPolicy._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.MeetIntegPolicy._Static {
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
                return new fabric.lang.security.MeetIntegPolicy._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 61, 21, -29, -14, 50,
    -86, 103, -96, -36, 24, -56, 37, -43, -34, -43, 93, 22, 98, -91, 98, -103,
    -106, 16, -46, 83, -33, 89, -63, -71, -119, -4, 45 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
