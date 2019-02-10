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
    
    public static final byte[] $classHash = new byte[] { 86, -90, -49, -114,
    -107, 67, -21, -70, -84, 77, 107, -16, 122, 25, 41, -20, -81, 58, 89, -48,
    64, -72, -31, -110, -22, -5, 35, -53, -4, -7, -126, -24 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
