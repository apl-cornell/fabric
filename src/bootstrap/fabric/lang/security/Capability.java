package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
import fabric.worker.Worker;
import fabric.worker.Store;

/**
 * This code is mostly copied from Jif.
 */
public interface Capability extends fabric.lang.Object {
    public fabric.lang.security.Principal get$jif$jif_lang_Capability_P();
    
    public fabric.lang.security.Principal set$jif$jif_lang_Capability_P(
      fabric.lang.security.Principal val);
    
    public fabric.lang.security.Label get$jif$jif_lang_Capability_L();
    
    public fabric.lang.security.Label set$jif$jif_lang_Capability_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Closure get$closure();
    
    public fabric.lang.security.Closure set$closure(
      fabric.lang.security.Closure val);
    
    public fabric.lang.security.Closure getClosure();
    
    public java.lang.Object invoke();
    
    public fabric.lang.security.Capability fabric$lang$security$Capability$(
      final fabric.lang.security.Principal jif$P,
      final fabric.lang.security.Label jif$L,
      final fabric.lang.security.Closure closure);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.Capability {
        public fabric.lang.security.Principal get$jif$jif_lang_Capability_P() {
            return ((fabric.lang.security.Capability._Impl) fetch()).
              get$jif$jif_lang_Capability_P();
        }
        
        public fabric.lang.security.Principal set$jif$jif_lang_Capability_P(
          fabric.lang.security.Principal val) {
            return ((fabric.lang.security.Capability._Impl) fetch()).
              set$jif$jif_lang_Capability_P(val);
        }
        
        public fabric.lang.security.Label get$jif$jif_lang_Capability_L() {
            return ((fabric.lang.security.Capability._Impl) fetch()).
              get$jif$jif_lang_Capability_L();
        }
        
        public fabric.lang.security.Label set$jif$jif_lang_Capability_L(
          fabric.lang.security.Label val) {
            return ((fabric.lang.security.Capability._Impl) fetch()).
              set$jif$jif_lang_Capability_L(val);
        }
        
        public fabric.lang.security.Closure get$closure() {
            return ((fabric.lang.security.Capability._Impl) fetch()).
              get$closure();
        }
        
        public fabric.lang.security.Closure set$closure(
          fabric.lang.security.Closure val) {
            return ((fabric.lang.security.Capability._Impl) fetch()).
              set$closure(val);
        }
        
        public native fabric.lang.security.Closure getClosure();
        
        public native java.lang.Object invoke();
        
        public native fabric.lang.security.Capability
          fabric$lang$security$Capability$(fabric.lang.security.Principal arg1,
                                           fabric.lang.security.Label arg2,
                                           fabric.lang.security.Closure arg3);
        
        public static native boolean jif$Instanceof(
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.Object arg3);
        
        public static native fabric.lang.security.Capability
          jif$cast$jif_lang_Capability(fabric.lang.security.Principal arg1,
                                       fabric.lang.security.Label arg2,
                                       fabric.lang.Object arg3);
        
        public _Proxy(Capability._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.Capability {
        public fabric.lang.security.Principal get$jif$jif_lang_Capability_P() {
            return this.jif$jif_lang_Capability_P;
        }
        
        public fabric.lang.security.Principal set$jif$jif_lang_Capability_P(
          fabric.lang.security.Principal val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.jif$jif_lang_Capability_P = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.Principal jif$jif_lang_Capability_P;
        
        public fabric.lang.security.Label get$jif$jif_lang_Capability_L() {
            return this.jif$jif_lang_Capability_L;
        }
        
        public fabric.lang.security.Label set$jif$jif_lang_Capability_L(
          fabric.lang.security.Label val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.jif$jif_lang_Capability_L = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.Label jif$jif_lang_Capability_L;
        
        public fabric.lang.security.Closure get$closure() {
            return this.closure;
        }
        
        public fabric.lang.security.Closure set$closure(
          fabric.lang.security.Closure val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.closure = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.Closure closure;
        
        public native fabric.lang.security.Closure getClosure();
        
        public native java.lang.Object invoke();
        
        public native fabric.lang.security.Capability
          fabric$lang$security$Capability$(
          final fabric.lang.security.Principal jif$P,
          final fabric.lang.security.Label jif$L,
          final fabric.lang.security.Closure closure);
        
        public static native boolean jif$Instanceof(
          final fabric.lang.security.Principal jif$P,
          final fabric.lang.security.Label jif$L, final fabric.lang.Object o);
        
        public static native fabric.lang.security.Capability
          jif$cast$jif_lang_Capability(
          final fabric.lang.security.Principal jif$P,
          final fabric.lang.security.Label jif$L, final fabric.lang.Object o);
        
        public native fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.Capability._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.jif$jif_lang_Capability_P, refTypes,
                      out, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.jif$jif_lang_Capability_L, refTypes,
                      out, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.closure, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.jif$jif_lang_Capability_P =
              (fabric.lang.security.Principal)
                $readRef(fabric.lang.security.Principal._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.jif$jif_lang_Capability_L =
              (fabric.lang.security.Label)
                $readRef(fabric.lang.security.Label._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.closure = (fabric.lang.security.Closure)
                             $readRef(fabric.lang.security.Closure._Proxy.class,
                                      (fabric.common.RefTypeEnum)
                                        refTypes.next(), in, store,
                                      intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.Capability._Impl src =
              (fabric.lang.security.Capability._Impl) other;
            this.jif$jif_lang_Capability_P = src.jif$jif_lang_Capability_P;
            this.jif$jif_lang_Capability_L = src.jif$jif_lang_Capability_L;
            this.closure = src.closure;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.Capability._Static {
            public _Proxy(fabric.lang.security.Capability._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.Capability._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  Capability.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.Capability._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.Capability._Static._Impl.class);
                $instance = (fabric.lang.security.Capability._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.Capability._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.security.Capability._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
