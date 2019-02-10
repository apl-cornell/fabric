package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
        
        public fabric.lang.security.Closure getClosure() {
            return ((fabric.lang.security.Capability) fetch()).getClosure();
        }
        
        public java.lang.Object invoke() {
            return ((fabric.lang.security.Capability) fetch()).invoke();
        }
        
        public fabric.lang.security.Capability fabric$lang$security$Capability$(
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Closure arg3) {
            return ((fabric.lang.security.Capability) fetch()).
              fabric$lang$security$Capability$(arg1, arg2, arg3);
        }
        
        public static boolean jif$Instanceof(
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.Object arg3) {
            return fabric.lang.security.Capability._Impl.jif$Instanceof(arg1,
                                                                        arg2,
                                                                        arg3);
        }
        
        public static fabric.lang.security.Capability
          jif$cast$jif_lang_Capability(fabric.lang.security.Principal arg1,
                                       fabric.lang.security.Label arg2,
                                       fabric.lang.Object arg3) {
            return fabric.lang.security.Capability._Impl.
              jif$cast$jif_lang_Capability(arg1, arg2, arg3);
        }
        
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
        
        public fabric.lang.security.Closure getClosure() {
            return this.get$closure();
        }
        
        public java.lang.Object invoke() {
            if (fabric.lang.Object._Proxy.idEquals(this.get$closure(), null))
                return null;
            return this.get$closure().invoke();
        }
        
        public fabric.lang.security.Capability fabric$lang$security$Capability$(
          final fabric.lang.security.Principal jif$P,
          final fabric.lang.security.Label jif$L,
          final fabric.lang.security.Closure closure) {
            this.set$jif$jif_lang_Capability_P(jif$P);
            this.set$jif$jif_lang_Capability_L(jif$L);
            this.set$closure(closure);
            fabric$lang$Object$();
            return (fabric.lang.security.Capability) this.$getProxy();
        }
        
        public static boolean jif$Instanceof(
          final fabric.lang.security.Principal jif$P,
          final fabric.lang.security.Label jif$L, final fabric.lang.Object o) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(o)) instanceof fabric.lang.security.Capability) {
                fabric.lang.security.Capability c =
                  (fabric.lang.security.Capability)
                    fabric.lang.Object._Proxy.$getProxy(o);
                boolean ok = true;
                ok =
                  ok &&
                    fabric.lang.security.PrincipalUtil._Impl.
                    equivalentTo(c.get$jif$jif_lang_Capability_P(), jif$P);
                ok =
                  ok &&
                    fabric.lang.security.LabelUtil._Impl.
                    equivalentTo(c.get$jif$jif_lang_Capability_L(), jif$L);
                return ok;
            }
            return false;
        }
        
        public static fabric.lang.security.Capability
          jif$cast$jif_lang_Capability(
          final fabric.lang.security.Principal jif$P,
          final fabric.lang.security.Label jif$L, final fabric.lang.Object o) {
            final fabric.worker.Store localStore =
              fabric.worker.Worker.getWorker().getLocalStore();
            if (fabric.lang.Object._Proxy.idEquals(o, null)) return null;
            if (fabric.lang.security.Capability._Impl.jif$Instanceof(jif$P,
                                                                     jif$L, o))
                return (fabric.lang.security.Capability)
                         fabric.lang.Object._Proxy.$getProxy(o);
            throw new java.lang.ClassCastException();
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.Capability) this.$getProxy();
        }
        
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
                return new fabric.lang.security.Capability._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -122, 67, -9, -115,
    -59, -15, -34, -62, -48, -107, -120, 21, -55, -54, 53, 52, 124, 77, -89,
    -54, 9, 9, -116, -99, -12, -62, -51, -127, 35, 74, -64, 127 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
