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
    
    public static final byte[] $classHash = new byte[] { -4, -104, 33, -53, -8,
    -46, -11, 125, -81, 98, -108, -40, 46, 7, 120, -117, -106, -18, -91, 71,
    -87, -75, 122, -1, 18, -2, 40, -7, -100, 116, 68, 84 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518990638000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
