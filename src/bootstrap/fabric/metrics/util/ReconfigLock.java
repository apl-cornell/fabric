package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.common.TransactionID;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.TransactionRestartingException;

/**
 * Utility class acting as a lock for Contracts to help with reconfigurations.
 */
public interface ReconfigLock extends fabric.lang.Object {
    public boolean get$currentlyHeld();
    
    public boolean set$currentlyHeld(boolean val);
    
    /**
   * @param startValue
   *        initial guess for the mean of the value we're keeping
   *        statistics on.
   */
    public fabric.metrics.util.ReconfigLock fabric$metrics$util$ReconfigLock$();
    
    /**
   * Check if we currently own the lock.
   */
    public boolean held();
    
    /**
   * Check that this item hasn't been locked by another process.
   */
    public void checkForRead();
    
    /**
   * Acquire the lock.
   */
    public void acquire();
    
    /**
   * Release the lock.
   */
    public void release();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.ReconfigLock {
        public boolean get$currentlyHeld() {
            return ((fabric.metrics.util.ReconfigLock._Impl) fetch()).
              get$currentlyHeld();
        }
        
        public boolean set$currentlyHeld(boolean val) {
            return ((fabric.metrics.util.ReconfigLock._Impl) fetch()).
              set$currentlyHeld(val);
        }
        
        public fabric.metrics.util.ReconfigLock
          fabric$metrics$util$ReconfigLock$() {
            return ((fabric.metrics.util.ReconfigLock) fetch()).
              fabric$metrics$util$ReconfigLock$();
        }
        
        public boolean held() {
            return ((fabric.metrics.util.ReconfigLock) fetch()).held();
        }
        
        public void checkForRead() {
            ((fabric.metrics.util.ReconfigLock) fetch()).checkForRead();
        }
        
        public void acquire() {
            ((fabric.metrics.util.ReconfigLock) fetch()).acquire();
        }
        
        public void release() {
            ((fabric.metrics.util.ReconfigLock) fetch()).release();
        }
        
        public _Proxy(ReconfigLock._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.ReconfigLock {
        public boolean get$currentlyHeld() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.currentlyHeld;
        }
        
        public boolean set$currentlyHeld(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.currentlyHeld = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private boolean currentlyHeld;
        
        /**
   * @param startValue
   *        initial guess for the mean of the value we're keeping
   *        statistics on.
   */
        public fabric.metrics.util.ReconfigLock
          fabric$metrics$util$ReconfigLock$() {
            fabric$lang$Object$();
            this.set$currentlyHeld(false);
            return (fabric.metrics.util.ReconfigLock) this.$getProxy();
        }
        
        /**
   * Check if we currently own the lock.
   */
        public boolean held() {
            return this.get$currentlyHeld() &&
              fabric.worker.transaction.TransactionManager.getInstance().
              hasLock((fabric.metrics.util.ReconfigLock) this.$getProxy());
        }
        
        /**
   * Check that this item hasn't been locked by another process.
   */
        public void checkForRead() {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            if (this.get$currentlyHeld() &&
                  !tm.hasLock((fabric.metrics.util.ReconfigLock)
                                this.$getProxy())) {
                fabric.common.TransactionID current = tm.getCurrentTid();
                if (!fabric.lang.Object._Proxy.idEquals(current, null)) {
                    while (!fabric.lang.Object._Proxy.idEquals(current.parent,
                                                               null))
                        current = current.parent;
                    tm.checkForStaleObjects();
                    throw new fabric.worker.TransactionRestartingException(
                            new fabric.common.TransactionID(current.topTid));
                }
            }
        }
        
        /**
   * Acquire the lock.
   */
        public void acquire() {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            if (!this.get$currentlyHeld() &&
                  !tm.hasLock((fabric.metrics.util.ReconfigLock)
                                this.$getProxy())) {
                tm.registerLockAcquire((fabric.metrics.util.ReconfigLock)
                                         this.$getProxy());
                this.set$currentlyHeld(true);
            }
            else if (!tm.hasLock((fabric.metrics.util.ReconfigLock)
                                   this.$getProxy())) {
                fabric.common.TransactionID current =
                  tm.getCurrentTid();
                if (!fabric.lang.Object._Proxy.idEquals(
                                                 current, null)) {
                    while (!fabric.lang.Object._Proxy.idEquals(
                                                        current.parent, null))
                        current =
                          current.parent;
                    tm.checkForStaleObjects();
                    throw new fabric.worker.TransactionRestartingException(
                            new fabric.common.TransactionID(current.topTid));
                }
            }
        }
        
        /**
   * Release the lock.
   */
        public void release() {
            this.set$currentlyHeld(false);
            fabric.worker.transaction.TransactionManager.getInstance().
              registerLockRelease((fabric.metrics.util.ReconfigLock)
                                    this.$getProxy());
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.ReconfigLock._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeBoolean(this.currentlyHeld);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.currentlyHeld = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.ReconfigLock._Impl src =
              (fabric.metrics.util.ReconfigLock._Impl) other;
            this.currentlyHeld = src.currentlyHeld;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.ReconfigLock._Static {
            public _Proxy(fabric.metrics.util.ReconfigLock._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.ReconfigLock._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  ReconfigLock.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.ReconfigLock._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.ReconfigLock._Static._Impl.class);
                $instance = (fabric.metrics.util.ReconfigLock._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.ReconfigLock._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.ReconfigLock._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -105, 105, -63, 36,
    -61, 28, -118, 38, 71, -45, -39, 58, 18, -89, 51, 89, 103, -102, 36, -88,
    -96, 12, 111, -26, 46, -6, 43, -92, -83, 1, 18, 116 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518550353000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
