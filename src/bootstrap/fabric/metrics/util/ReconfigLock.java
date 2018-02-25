package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.common.TransactionID;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.metrics.LockConflictException;
import fabric.common.Logging;
import java.util.logging.Level;

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
            this.set$currentlyHeld(true);
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            tm.registerLockCreate((fabric.metrics.util.ReconfigLock)
                                    this.$getProxy());
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
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINE,
                "CHECKING READ OF {0} IN {1}",
                new java.lang.Object[] { (java.lang.Object)
                                           fabric.lang.WrappedJavaInlineable.
                                           $unwrap(
                                             (fabric.metrics.util.ReconfigLock)
                                               this.$getProxy()),
                  tm.
                    getCurrentTid() });
            if (this.get$currentlyHeld() &&
                  !tm.hasLock((fabric.metrics.util.ReconfigLock)
                                this.$getProxy()) && tm.inTxn()) {
                tm.registerLockConflict((fabric.metrics.util.ReconfigLock)
                                          this.$getProxy());
            }
        }
        
        /**
   * Acquire the lock.
   */
        public void acquire() {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINE,
                "ATTEMPTING ACQUIRE OF {0} IN {1}",
                new java.lang.Object[] { (java.lang.Object)
                                           fabric.lang.WrappedJavaInlineable.
                                           $unwrap(
                                             (fabric.metrics.util.ReconfigLock)
                                               this.$getProxy()),
                  tm.
                    getCurrentTid() });
            if (!tm.hasLock((fabric.metrics.util.ReconfigLock)
                              this.$getProxy())) {
                if (!this.get$currentlyHeld()) {
                    tm.registerLockAcquire((fabric.metrics.util.ReconfigLock)
                                             this.$getProxy());
                    this.set$currentlyHeld(true);
                } else {
                    tm.registerLockConflict((fabric.metrics.util.ReconfigLock)
                                              this.$getProxy());
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
    
    public static final byte[] $classHash = new byte[] { -8, -98, 21, 31, 28,
    -7, -33, 12, 125, 123, -48, 19, 41, 84, 18, 3, 23, -13, 62, 25, 16, 71, 115,
    108, 16, -50, 111, -47, 83, 21, 113, 36 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519598106000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
