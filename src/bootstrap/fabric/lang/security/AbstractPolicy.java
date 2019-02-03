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
    
    public static final byte[] $classHash = new byte[] { 34, -28, 13, 92, 109,
    17, 3, -101, -17, -107, -9, 40, 1, -112, 23, 109, 126, -76, 28, 90, -2, -68,
    -8, 93, 54, 88, -13, -77, -58, -76, -7, -54 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
