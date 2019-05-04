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
    
    public static final byte[] $classHash = new byte[] { -22, 80, 75, 114, 80,
    -7, -42, -91, -14, 105, 5, -24, -68, -128, 16, -116, 4, 48, 39, -50, 40, 34,
    51, -107, -92, 43, 106, -36, -74, -3, 113, 87 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
