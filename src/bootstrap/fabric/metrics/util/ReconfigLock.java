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
                     fabric.worker.metrics.treaties.TreatySet treaties,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, treaties, labelStore,
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
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.metrics.treaties.TreatySet treaties,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, treaties, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { 52, -46, 72, 115, 118,
    54, -119, 115, 13, 20, -58, 81, -88, 55, 121, -14, -120, 23, 80, -29, 54,
    -15, -94, 109, -71, -97, -15, 106, -57, -54, -13, -81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527882698000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
