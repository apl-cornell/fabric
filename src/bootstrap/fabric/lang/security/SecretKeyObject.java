package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
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
public interface SecretKeyObject
  extends fabric.lang.Object
{
    
    public javax.crypto.SecretKey get$key();
    
    public javax.crypto.SecretKey set$key(javax.crypto.SecretKey val);
    
    public fabric.lang.security.SecretKeyObject
      fabric$lang$security$SecretKeyObject$();
    
    public javax.crypto.SecretKey getKey();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.SecretKeyObject
    {
        
        public javax.crypto.SecretKey get$key() {
            return ((fabric.lang.security.SecretKeyObject._Impl) fetch()).
              get$key();
        }
        
        public javax.crypto.SecretKey set$key(javax.crypto.SecretKey val) {
            return ((fabric.lang.security.SecretKeyObject._Impl) fetch()).
              set$key(val);
        }
        
        public native fabric.lang.security.SecretKeyObject
          fabric$lang$security$SecretKeyObject$();
        
        public native javax.crypto.SecretKey getKey();
        
        public _Proxy(SecretKeyObject._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.SecretKeyObject
    {
        
        public javax.crypto.SecretKey get$key() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.key;
        }
        
        public javax.crypto.SecretKey set$key(javax.crypto.SecretKey val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.key = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private javax.crypto.SecretKey key;
        
        public native fabric.lang.security.SecretKeyObject
          fabric$lang$security$SecretKeyObject$();
        
        public native javax.crypto.SecretKey getKey();
        
        public native fabric.lang.Object $initLabels();
        
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
                     long expiry, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
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
          implements fabric.lang.security.SecretKeyObject._Static
        {
            
            public _Proxy(fabric.lang.security.SecretKeyObject._Static.
                            _Impl impl) {
                super(impl);
            }
            
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
                  _Impl impl =
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
          implements fabric.lang.security.SecretKeyObject._Static
        {
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.security.SecretKeyObject._Static._Proxy(
                  this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
