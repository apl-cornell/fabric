package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.security.GeneralSecurityException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import fabric.common.Crypto;
import fabric.common.exceptions.InternalError;

/**
 * This wraps a Java SecretKey object. This is here so that we can have
 * immutable Label objects and so that we can be more lax about the
 * confidentiality of label objects. By not inlining the key object in the
 * corresponding label object, we can simply apply the label to the key object
 * and be certain of the key's confidentiality.
 */
public interface SecretKeyObject extends fabric.lang.Object {
    public javax.crypto.SecretKey get$key();
    
    public javax.crypto.SecretKey set$key(javax.crypto.SecretKey val);
    
    public fabric.lang.security.SecretKeyObject
      fabric$lang$security$SecretKeyObject$();
    
    public javax.crypto.SecretKey getKey();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.SecretKeyObject {
        public javax.crypto.SecretKey get$key() {
            return ((fabric.lang.security.SecretKeyObject._Impl) fetch()).
              get$key();
        }
        
        public javax.crypto.SecretKey set$key(javax.crypto.SecretKey val) {
            return ((fabric.lang.security.SecretKeyObject._Impl) fetch()).
              set$key(val);
        }
        
        public fabric.lang.security.SecretKeyObject
          fabric$lang$security$SecretKeyObject$() {
            return ((fabric.lang.security.SecretKeyObject) fetch()).
              fabric$lang$security$SecretKeyObject$();
        }
        
        public javax.crypto.SecretKey getKey() {
            return ((fabric.lang.security.SecretKeyObject) fetch()).getKey();
        }
        
        public _Proxy(SecretKeyObject._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.SecretKeyObject {
        public javax.crypto.SecretKey get$key() { return this.key; }
        
        public javax.crypto.SecretKey set$key(javax.crypto.SecretKey val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.key = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private javax.crypto.SecretKey key;
        
        public fabric.lang.security.SecretKeyObject
          fabric$lang$security$SecretKeyObject$() {
            this.set$key(fabric.common.Crypto.genSecretKey());
            fabric$lang$Object$();
            return (fabric.lang.security.SecretKeyObject) this.$getProxy();
        }
        
        public javax.crypto.SecretKey getKey() { return this.get$key(); }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.SecretKeyObject) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.SecretKeyObject._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeInline(out, this.key);
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
            this.key = (javax.crypto.SecretKey) in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.SecretKeyObject._Impl src =
              (fabric.lang.security.SecretKeyObject._Impl) other;
            this.key = src.key;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.SecretKeyObject._Static {
            public _Proxy(fabric.lang.security.SecretKeyObject._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.SecretKeyObject._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  SecretKeyObject.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.SecretKeyObject._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.SecretKeyObject._Static._Impl.class);
                $instance = (fabric.lang.security.SecretKeyObject._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.SecretKeyObject._Static {
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
                return new fabric.lang.security.SecretKeyObject._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 106, -106, -63, -58, 8,
    -49, -113, 88, 103, -1, -107, -85, -120, 15, 31, -111, 26, 23, 26, 124, -52,
    26, 25, 31, -31, -81, 28, 109, 6, -115, 39, 3 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
