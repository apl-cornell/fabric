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
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.ReconfigLock._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -122, -62, 115, 7, 79,
    1, 28, 59, 119, -4, 12, 47, -43, -4, -96, -123, 22, -39, -28, -68, 75, 35,
    72, 23, 56, 117, 52, -41, -123, -7, -49, 97 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1529349674000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
