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
    
    public static final byte[] $classHash = new byte[] { -51, -82, -27, 31, 107,
    73, -50, -85, 24, -104, -89, -94, -25, 114, -39, -24, -38, -106, 38, -116,
    -33, 112, 74, -79, -73, 10, 25, -35, -112, -124, -31, 53 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
