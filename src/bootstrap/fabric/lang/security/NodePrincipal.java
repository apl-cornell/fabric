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
    
    public static final byte[] $classHash = new byte[] { 2, -88, -46, 78, 45,
    72, -82, 1, -123, 105, -72, -61, 6, -33, 110, -74, 118, -90, -52, 79, 64,
    71, -123, 112, -77, 27, 39, -43, -5, -77, 110, 87 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
