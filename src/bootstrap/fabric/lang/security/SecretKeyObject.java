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
    
    public static final byte[] $classHash = new byte[] { -89, 66, -94, 106, 105,
    80, 125, -55, -127, -19, -52, 110, 8, -115, 22, 59, -114, 45, 23, -126,
    -118, -75, 28, 112, 8, 84, -111, 80, -9, -23, 124, 59 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
