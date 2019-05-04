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
    
    public static final byte[] $classHash = new byte[] { -103, 6, -91, -104, 34,
    -79, 100, 84, -37, -106, 119, 103, 95, 2, 74, -98, 18, -1, 51, -66, -94, 40,
    -68, -109, 10, 121, 72, -12, 49, 75, 3, 33 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
