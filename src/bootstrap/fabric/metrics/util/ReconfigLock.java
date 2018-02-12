package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
    
    public static final byte[] $classHash = new byte[] { 116, -121, -52, -99,
    -34, -67, -119, 124, -18, -59, -16, -103, 39, -75, -3, -39, -86, -8, -18,
    111, 70, -89, 55, -101, 7, 68, 104, 95, 62, -124, 120, -121 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518448064000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
