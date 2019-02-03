package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.util.Iterator;
import fabric.util.LinkedHashSet;
import fabric.util.Set;
import fabric.util.HashMap;
import fabric.util.Map;

public interface NodePrincipal extends fabric.lang.security.AbstractPrincipal {
    public fabric.lang.security.NodePrincipal
      fabric$lang$security$NodePrincipal$(java.lang.String name);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy
    extends fabric.lang.security.AbstractPrincipal._Proxy
      implements fabric.lang.security.NodePrincipal {
        public fabric.lang.security.NodePrincipal
          fabric$lang$security$NodePrincipal$(java.lang.String arg1) {
            return ((fabric.lang.security.NodePrincipal) fetch()).
              fabric$lang$security$NodePrincipal$(arg1);
        }
        
        public _Proxy(NodePrincipal._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl
    extends fabric.lang.security.AbstractPrincipal._Impl
      implements fabric.lang.security.NodePrincipal {
        public fabric.lang.security.NodePrincipal
          fabric$lang$security$NodePrincipal$(java.lang.String name) {
            fabric$lang$security$AbstractPrincipal$(name);
            return (fabric.lang.security.NodePrincipal) this.$getProxy();
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.NodePrincipal) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.NodePrincipal._Proxy(this);
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
          implements fabric.lang.security.NodePrincipal._Static {
            public _Proxy(fabric.lang.security.NodePrincipal._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.NodePrincipal._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  NodePrincipal.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.NodePrincipal._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.NodePrincipal._Static._Impl.class);
                $instance = (fabric.lang.security.NodePrincipal._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.NodePrincipal._Static {
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
                return new fabric.lang.security.NodePrincipal._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 56, -48, 25, 67, 110,
    -53, -78, -50, 13, -28, -50, 112, 41, 1, 26, -71, 111, 82, -97, -78, -75,
    70, -35, -6, -85, -112, -115, -34, -126, 11, 59, -69 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
