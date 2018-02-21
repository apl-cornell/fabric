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
                                this.$getProxy())) {
                fabric.common.TransactionID current = tm.getCurrentTid();
                if (!fabric.lang.Object._Proxy.idEquals(current, null)) {
                    while (!fabric.lang.Object._Proxy.idEquals(current.parent,
                                                               null))
                        current = current.parent;
                    fabric.common.Logging.METRICS_LOGGER.
                      fine(
                        "ABORTING READ IN " +
                          current +
                          " FOR LOCK " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.
                                  $unwrap((fabric.metrics.util.ReconfigLock)
                                            this.$getProxy())));
                    throw new fabric.worker.metrics.LockConflictException(
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
                }
                else {
                    fabric.common.TransactionID current =
                      tm.getCurrentTid();
                    if (!fabric.lang.Object._Proxy.idEquals(
                                                     current, null)) {
                        while (!fabric.lang.Object._Proxy.idEquals(
                                                            current.parent,
                                                            null))
                            current = current.parent;
                        fabric.common.Logging.METRICS_LOGGER.
                          fine(
                            "ABORTING ACQUIRE " +
                              current +
                              " FOR LOCK " +
                              java.lang.String.
                                valueOf(
                                  fabric.lang.WrappedJavaInlineable.
                                      $unwrap((fabric.metrics.util.ReconfigLock)
                                                this.$getProxy())));
                        throw new fabric.worker.metrics.LockConflictException(
                                new fabric.common.TransactionID(
                                  current.topTid));
                    }
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
    
    public static final byte[] $classHash = new byte[] { 27, 87, 8, -6, 45, 47,
    -68, 96, -8, -54, 111, -120, -27, 56, -117, 86, -51, 68, 116, 61, 18, -87,
    95, -36, 126, -95, 109, 41, 76, -88, -67, -37 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519228622000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
