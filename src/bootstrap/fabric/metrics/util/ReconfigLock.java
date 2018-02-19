package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.common.TransactionID;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.TransactionRestartingException;
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
                        tm.checkForStaleObjects();
                        throw new fabric.worker.TransactionRestartingException(
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
    
    public static final byte[] $classHash = new byte[] { 118, -52, 49, 19, -122,
    -22, 26, 47, 100, -121, -107, -65, 96, 101, 66, 85, 124, -20, -36, -62,
    -121, 111, -94, 96, 74, 25, -77, 40, 75, -96, -60, -102 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519057384000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
