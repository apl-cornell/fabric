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
    
    public static final byte[] $classHash = new byte[] { -66, 117, 29, 29, 47,
    -103, 6, -48, -69, -18, 111, 69, 112, -7, -111, 43, 7, 109, -22, -72, -81,
    75, -23, -42, -110, -89, 104, -90, -84, 50, 36, 16 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1529598589000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
