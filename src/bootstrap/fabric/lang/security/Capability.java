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
    
    public static final byte[] $classHash = new byte[] { 106, 61, -26, 115, -45,
    110, -47, 39, 107, -59, 65, -99, -79, 9, -42, 103, -116, 111, -21, -32, 69,
    -23, -54, 24, -127, 9, 101, 114, -55, 63, -118, -47 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
