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
    
    public static final byte[] $classHash = new byte[] { 52, -49, 1, 68, -22,
    23, -105, -20, 123, 11, -100, -2, 12, 126, 25, -110, 82, -59, -47, -19, -44,
    40, -119, 120, 27, 96, 34, 43, -15, -52, -27, -105 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
