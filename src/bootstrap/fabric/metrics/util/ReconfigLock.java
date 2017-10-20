package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.common.Logging;
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
                    fabric.common.Logging.METRICS_LOGGER.
                      finer(
                        "ABORTING " +
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
            if (!this.get$currentlyHeld()) {
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
                    fabric.common.Logging.METRICS_LOGGER.
                      finer(
                        "ABORTING " +
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
    
    public static final byte[] $classHash = new byte[] { -44, -30, 59, -77, 50,
    118, -57, 69, -47, -66, 56, -72, -108, 95, -39, 32, -26, -68, 102, 116, -44,
    7, -117, -126, 8, 127, -93, -123, -7, 42, 23, 87 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1508290262000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
