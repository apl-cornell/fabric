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
    
    public static final byte[] $classHash = new byte[] { -56, 5, 38, 38, 127,
    68, -6, -60, 115, 91, -34, -74, 33, -122, -7, 73, -46, 57, 125, -88, -27,
    92, 10, -112, 93, -53, -91, 33, 79, 2, -3, -90 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
