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
    
    public static final byte[] $classHash = new byte[] { 50, -92, -84, -10, -2,
    -56, 63, -117, 7, 54, 88, -19, -104, -128, 41, 30, 85, 57, -124, 74, 114,
    17, 89, 55, -73, -13, 79, 68, 6, -93, -128, 104 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
