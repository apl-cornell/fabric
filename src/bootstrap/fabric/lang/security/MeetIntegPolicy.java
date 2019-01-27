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
    
    public static final byte[] $classHash = new byte[] { 39, 6, 68, -87, 87, 85,
    -14, 64, 55, -22, -44, -31, -105, -20, -42, -4, -122, -118, 0, 88, 99, -31,
    17, 92, 24, 112, 112, -86, -126, 94, -94, 46 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
