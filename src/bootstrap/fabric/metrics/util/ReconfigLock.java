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
 * Utility class acting as a lock for treaties to help with reconfigurations.
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
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, labelStore, labelOnum,
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
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, labelStore, labelOnum,
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
    
    public static final byte[] $classHash = new byte[] { -8, -122, 84, 40, 16,
    -53, -99, 81, -91, -17, 77, 24, -41, 33, -33, 122, -113, -112, 69, 102, 126,
    -2, -62, 65, -13, 79, -21, -31, 107, 28, 24, 109 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527882698000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
