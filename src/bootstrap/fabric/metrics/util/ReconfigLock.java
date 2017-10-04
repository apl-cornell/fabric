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
                this.set$currentlyHeld(true);
                tm.registerLockAcquire((fabric.metrics.util.ReconfigLock)
                                         this.$getProxy());
            }
            else if (!tm.hasLock((fabric.metrics.util.ReconfigLock)
                                   this.$getProxy())) {
                fabric.common.TransactionID current =
                  tm.getCurrentTid();
                if (!fabric.lang.Object._Proxy.idEquals(
                                                 current, null)) {
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
    
    public static final byte[] $classHash = new byte[] { -108, 109, -60, 17, 73,
    -64, -26, 94, 70, 82, -23, 75, -92, 22, 50, -68, -38, -109, -93, 36, 89,
    -109, -52, -77, 26, 51, -117, -28, -121, -85, -120, -2 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507057525000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
