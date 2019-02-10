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
    
    public static final byte[] $classHash = new byte[] { 96, 83, -13, 68, 121,
    -103, -128, -36, 66, 91, -81, -85, 103, 18, 26, -68, -92, 107, -78, -21, 14,
    47, 114, -83, -89, 68, -20, 85, 89, -122, 119, -14 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
