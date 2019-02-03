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
    
    public static final byte[] $classHash = new byte[] { -89, 127, 56, -96, 103,
    -30, -117, -73, -29, -102, -65, 83, -41, -21, 96, 27, 74, 0, -56, 14, -29,
    -34, -97, -75, -100, -76, 3, -26, 50, 117, 42, -1 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
