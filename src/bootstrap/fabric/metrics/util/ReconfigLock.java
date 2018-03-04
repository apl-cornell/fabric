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
   * Optimistic acquire the lock.
   */
    public void acquireOptimistic();
    
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
        
        public void acquireOptimistic() {
            ((fabric.metrics.util.ReconfigLock) fetch()).acquireOptimistic();
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
            return fabric.worker.transaction.TransactionManager.getInstance().
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
   * Optimistic acquire the lock.
   */
        public void acquireOptimistic() {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINE,
                "ATTEMPTING OPTIMISTIC ACQUIRE OF {0} IN {1}",
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
                } else {
                    tm.registerLockConflict((fabric.metrics.util.ReconfigLock)
                                              this.$getProxy());
                }
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
            if (this.get$currentlyHeld()) {
                this.set$currentlyHeld(false);
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerLockRelease((fabric.metrics.util.ReconfigLock)
                                        this.$getProxy());
            }
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
    
    public static final byte[] $classHash = new byte[] { -99, 87, 20, -108, -47,
    -34, -128, 110, -40, 3, -29, -81, 5, 31, -124, 121, 71, 58, 21, -36, 110,
    -77, 17, -61, -15, -3, -13, -98, -1, 74, 51, -109 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520199002000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
