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
            if (this.get$currentlyHeld() ||
                  tm.hasLock((fabric.metrics.util.ReconfigLock)
                               this.$getProxy())) {
                fabric.common.TransactionID current = tm.getCurrentTid();
                if (!fabric.lang.Object._Proxy.idEquals(current, null)) {
                    while (!fabric.lang.Object._Proxy.idEquals(current.parent,
                                                               null))
                        current = current.parent;
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
                    throw new fabric.worker.TransactionRestartingException(
                            new fabric.common.TransactionID(current.topTid));
                }
            }
        }
        
        /**
   * Release the lock.
   */
        public void release() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerLockRelease((fabric.metrics.util.ReconfigLock)
                                    this.$getProxy());
            this.set$currentlyHeld(false);
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
    
    public static final byte[] $classHash = new byte[] { -102, -74, 55, 30,
    -117, -122, -110, 115, 13, 95, 9, -28, -3, 120, -54, -16, 41, 79, -111, 1,
    -60, -85, 99, -117, 28, 86, 29, 34, -84, -62, -6, 26 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507217540000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
