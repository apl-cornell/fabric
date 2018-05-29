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
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
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
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { 58, -34, 3, 16, -109,
    38, 41, 62, 19, 71, 93, 105, -77, -126, 107, -48, 38, 119, 97, -23, -48,
    108, -69, -45, 81, 109, -21, -115, -7, -103, -1, 116 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527629368000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1Ya2xcRxWeXdtrr+vEryZpXcdxnK1FHt1VCvxIDBXxEsdLt9jYSVUcGjN776z3xnfvvZk7a28CoQ8EiSoSROukidrmV1BLa1oeqopUReoPKK2CUFsQjx+U/IloFfKjQrwkIJwzd3bv3eu1sSUszZzZmXPOnDnznTPnevEmaXI5GcjTnGEmxXGHuckRmstkxyl3mZ42qesehNlp7bbGzPkPntf7oiSaJW0atWzL0Kg5bbmCrM8epXM0ZTGROjSRGTpM4hoKjlK3IEj08HCZk37HNo/PmLZQmyzRf25nauHpIx0/aiDtU6TdsCYFFYaWti3BymKKtBVZMce4u0/XmT5FOi3G9EnGDWoaJ4DRtqZIl2vMWFSUOHMnmGubc8jY5ZYcxuWelUk03wazeUkTNgfzOzzzS8IwU1nDFUNZEssbzNTdY+RrpDFLmvImnQHGjdnKKVJSY2oE54G91QAzeZ5qrCLSOGtYuiBbwhLVEyfuBwYQbS4yUbCrWzVaFCZIl2eSSa2Z1KTghjUDrE12CXYRpGdZpcDU4lBtls6waUHuCPONe0vAFZduQRFBNoTZpCa4s57QnQVu6+bnP3X2K9aoFSURsFlnmon2t4BQX0hoguUZZ5bGPMG2HdnzdOOV01FCgHlDiNnjee2rH31mV98bb3k8d9XhGcsdZZqY1i7n1r/bm96+pwHNaHFs10Ao1Jxc3uq4WhkqO4D2jVWNuJisLL4x8eYXH32R3YiS1gyJabZZKgKqOjW76Bgm4weYxTgVTM+QOLP0tFzPkGYYZw2LebNj+bzLRIY0mnIqZsvf4KI8qEAXNcPYsPJ2ZexQUZDjskMIaYZGItCOENK6Feg6+JkRZCxVsIsslTNLbB7gnYLGKNcKKYhbbmgpl2spXrKEAUxqClAExPXOP8EA8XljJmtrs0kwxfn/qyzjKTrmIxFw8BbN1lmOunBbCjnD4yYEx6ht6oxPa+bZKxnSfeWiRE8cEe8CaqV/InDjveFcEZRdKA3v/+jl6ase8lBWuU+Qfs/OpLLTu92gnWBaG8ZVEjJVEjLVYqScTF/KvCThE3NlnFW1tYG2vY5JRd7mxTKJROTRbpfyUjPc+ixkE0gYbdsnH/7cl08PNABgnflGvENgTYTDx086GRhRiIlprf3UB3975fxJ2w8kQRJL4nupJMbnQNhP3NaYDvnPV7+jn746feVkIoq5JQ6eEBSACTmkL7xHTZwOVXIeeqMpS25DH1ATlyqJqlUUuD3vz8j7X49dlwcFdFbIQJkuPz3pPPe7X374cfmQVDJreyAFTzIxFIhmVNYu47bT9/1Bzhjw/eHC+FPnbp46LB0PHNvqbZjAPg1RTCF8bf6Nt479/o/vX/511L8sQWJOKWcaWlmepfMW/EWg/QcbhiROIIXEnFbpoL+aDxzcedC3DTKDCdkJTHcTh6yirRt5g+ZMhkj5V/vdu1/989kO77pNmPGcx8mu/63An79zmDx69cjf+6SaiIYvk+8/n81Ld92+5n2c0+NoR/mx9zZf/Dl9DpAPyco1TjCZf4j0B5EXeK/0xT2y3x1a+wR2A563equAD6f+EXxDfSxOpRaf7Unfd8OL+ioWUcfWOlH/IA2Eyb0vFv8aHYj9LEqap0iHfL6pJR6kkL0ABlPwALtpNZkl62rWax9T7+UYqsZabzgOAtuGo8DPNjBGbhy3esD3gAOOaEUn9UNrB6c8oqiBq90O9reXI0QO9kqRbbIfxG57BYzNDjfmAFnlqtIoKo0rZQVFaUCpgEOXOMSvMI+PYuWy9DbGuVGEgJpTDzE7vfDEreTZBQ+JXrWybUnBEJTxKhZ53HXY7SzDLltX2kVKjPzplZOvv3DylPead9W+vfutUvH7v/n3L5IXrr1dJ6M352zbZFTmgI7yMg7D4Q7fV/Ivpt7OUUWHA74KoJbgETYvV+ZI8y8/vnBJH/vu7qiC/n5B4sJ27jHZHDMDqmLojCVl9AOyuPNxfO3G5j3p2esznjO2hHYOc3/vgcW3DwxqT0ZJQxWwSyrKWqGhWpi2cgYFsXWwBqz9VV8hpMgeaBsBY3sVbQ2C1Uvl0vHYZWoh2aJE4oo2hN1cP308tMLaFHaTQvoSXvGEeswTCJdE8DFP+KaN1x5oEzQonhr2KLpzbQdCkR2KJlZ3ILrCmobdlwRpLEBc4jhdz+ZeaB+DDa8r+t7abEaRdxW9ujqbj66wJqt5yJxtWoFpsyM2n2DU+0TZpyIfyWfhUHO2odc7DxavnySk6QeKXljbeVDkaUW/s7rzzK2wJrtjgnRS7VjJ4GzMgboWnkFDXo61HIgOQEX+pqI/WdsBUOQ1RX+4ugM8ssLaY9idgISoDrCi2Q/B+BlFz6zNbBT5lqLfXJ3Zp1dYewK7r4PZnEEadz2zy4CrYCBjjXJXnS8G9R2rpX/KLl+/f9eGZb4W7ljynwUl9/Kl9pZNlw79Vta+1W/UOJSW+ZJpBt/ywDjmcJY3pPFx72V3JPm2IN11Pi8gApDIw5/xOJ8UZH0tp5Af+TgK8p2DqtPjw1/npZ97/E6yboDiRenC6iXpVS9y6c7wF4pU2lPi+A+Xxb9s+kes5eA1WePCZfTvfb+h46nB7fd1H3jY+PHjs+8MztMP3zFf/9UXijfO/PPiLfFfQ8JKjggSAAA=";
}
