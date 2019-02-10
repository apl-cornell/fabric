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
    
    public static final byte[] $classHash = new byte[] { -6, 84, 42, 86, -73,
    77, 126, 61, 86, -56, 83, 15, 41, -104, -121, -61, -121, 0, -109, 107, 8,
    20, -72, -112, 62, 43, -116, -103, -50, -54, 49, 118 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
