package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * A Label is the runtime representation of a Fabric label. A Label consists of
 * a set of components, each of which is a {@link fabric.lang.security.Policy Policy}.
 * This code is mostly copied from Jif.
 */
public interface AbstractPolicy
  extends fabric.lang.security.Policy, fabric.lang.Object {
    public fabric.lang.security.AbstractPolicy
      fabric$lang$security$AbstractPolicy$();
    
    public abstract boolean equals(fabric.lang.Object that);
    
    public abstract int hashCode();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.AbstractPolicy {
        public fabric.lang.security.AbstractPolicy
          fabric$lang$security$AbstractPolicy$() {
            return ((fabric.lang.security.AbstractPolicy) fetch()).
              fabric$lang$security$AbstractPolicy$();
        }
        
        public boolean relabelsTo(fabric.lang.security.Policy arg1,
                                  java.util.Set arg2) {
            return ((fabric.lang.security.AbstractPolicy) fetch()).relabelsTo(
                                                                     arg1,
                                                                     arg2);
        }
        
        public _Proxy(AbstractPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.AbstractPolicy {
        public fabric.lang.security.AbstractPolicy
          fabric$lang$security$AbstractPolicy$() {
            fabric$lang$Object$();
            return (fabric.lang.security.AbstractPolicy) this.$getProxy();
        }
        
        public abstract boolean equals(fabric.lang.Object that);
        
        public abstract int hashCode();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.AbstractPolicy._Proxy(this);
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
          implements fabric.lang.security.AbstractPolicy._Static {
            public _Proxy(fabric.lang.security.AbstractPolicy._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.AbstractPolicy._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  AbstractPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.AbstractPolicy._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.AbstractPolicy._Static._Impl.class);
                $instance = (fabric.lang.security.AbstractPolicy._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.AbstractPolicy._Static {
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
                return new fabric.lang.security.AbstractPolicy._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -77, -30, -50, 41, -83,
    -16, -9, -33, 34, -121, -71, -4, -3, 15, -88, 55, -119, 100, 2, 1, 43, -125,
    -20, -36, 19, -73, 21, -115, 62, -99, 93, 63 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
