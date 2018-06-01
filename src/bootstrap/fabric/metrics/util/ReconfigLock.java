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
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, labelStore, labelOnum,
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
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, labelStore, labelOnum,
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
    
    public static final byte[] $classHash = new byte[] { -115, 102, -95, 73,
    -65, 111, 53, 79, -43, -19, -61, -12, -15, -29, 35, -125, -112, 25, -26,
    107, 81, 58, 122, -60, 4, 30, 116, 79, 75, -5, -59, 86 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527874708000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1YfWwcxRWfO9tnn3Hij5CEGMdx7CNqQrhToEhNDIjkiONrDmzsJAKnjZnbnTtvvLe7mZ1zLoHwKZQolBRRx0BVIiEF8WUIQqL8gSKQIC2UqiqIFvgDiAS0oJA/Ivol9YO+N7t3u7c+G1vC0s6bm3nvzXtvfu/NjKfPkzqbk+4szWh6XBywmB3vo5lUepBym6lJndr2DhgdVS6qTU19+ZTaGSbhNGlSqGEamkL1UcMWZHF6L52gCYOJxM6hVO9uElVQsJ/aY4KEd28pctJlmfqBnG4Kd5EZ+o9fnph8ZE/LSzWkeYQ0a8awoEJTkqYhWFGMkKY8y2cYtzerKlNHSKvBmDrMuEZ17SAwmsYIabO1nEFFgTN7iNmmPoGMbXbBYlyuWRpE800wmxcUYXIwv8UxvyA0PZHWbNGbJpGsxnTV3kfuJLVpUpfVaQ4Yl6VLXiSkxkQfjgN7owZm8ixVWEmkdlwzVEFWBSXKHse2AwOI1ueZGDPLS9UaFAZIm2OSTo1cYlhwzcgBa51ZgFUEaZ9VKTA1WFQZpzk2KsglQb5BZwq4ojIsKCLI0iCb1AR71h7YM99unb/pmmO3G/1GmITAZpUpOtrfAEKdAaEhlmWcGQpzBJvWpafostNHwoQA89IAs8Pzyh0Xrl/f+fpbDs+lVXgGMnuZIkaVk5nF73Yk126sQTMaLNPWEAoVnstdHXRneosWoH1ZWSNOxkuTrw/95ta7n2XnwqQxRSKKqRfygKpWxcxbms74NmYwTgVTUyTKDDUp51OkHvppzWDO6EA2azORIrW6HIqY8jeEKAsqMET10NeMrFnqW1SMyX7RIoTUw0dC8O0hpLEL6CL42S/IQGLMzLNERi+w/QDvBHyMcmUsAXnLNSVhcyXBC4bQgMkdAhQBsR3/hxggPqvl0qYyHgdTrO9fZRG9aNkfCkGAVymmyjLUht1ykbNlUIfk6Dd1lfFRRT92OkWWnH5MoieKiLcBtTI+IdjxjmCt8MtOFrZsvfDC6DsO8lDWDZ8gXY6dcddOZ3f9doJpTZhXcahUcahU06FiPHki9ZyET8SWeVbW1gTaNlk6FVmT54skFJKuXSzlpWbY9XGoJlAwmtYO//THtx3prgHAWvtrcQ+BNRZMH6/opKBHISdGlebDX/7j1NQh00skQWIz8numJOZndzBO3FSYCvXPU7+ui748evpQLIy1JQqREBSACTWkM7hGRZ72lmoeRqMuTS7CGFAdp0qFqlGMcXO/NyL3fzE2bQ4UMFgBA2W5vHbYevzDP3x1lTxISpW12VeCh5no9WUzKmuWedvqxX4HZwz4Pn508BfHzx/eLQMPHD3VFoxhm4QsppC+Jr//rX0fffrJyffD3mYJErEKGV1TitKX1m/hLwTf//DDlMQBpFCYk2456CrXAwtXXuPZBpVBh+oEptuxnUbeVLWsRjM6Q6T8p/myDS9/fazF2W4dRpzgcbL+uxV44yu2kLvf2fPPTqkmpODJ5MXPY3PK3RJP82bO6QG0o3jPeysf+y19HJAPxcrWDjJZf4iMB5EbeKWMxRWy3RCY+yE23U60OsqAD5b+PjxDPSyOJKZ/1Z687pyT9WUsoo7VVbJ+F/WlyZXP5v8e7o6cCZP6EdIij29qiF0UqhfAYAQOYDvpDqbJoor5ysPUOTl6y7nWEcwD37LBLPCqDfSRG/uNDvAd4EAgGjFIWLqbISh3ulSW+CUWthcXQ0R2NkmRHtmuwWZtCYz1FtcmAFnFstIwKo26ynIuvc2nVIDTBQ75K/QD/Xhzmbkbg1zLQ0JNuAcxOzJ59Nv4sUkHic5tpWfGhcEv49xYpLuLsLm8CKusnmsVKdH311OHXn360GHnNG+rPHu3GoX883/+7+/jj559u0pFr8+Yps6orAEtxVkCht11XqzkX8Q9O7e5dLMvVj7UEnRh5WzXHGn+yXsnT6gDT24Iu9DfKkhUmNYVOptguk9VBIMx4xp9o7zceTg+e27lxuT4FzknGKsCKwe5n7lx+u1ta5SHw6SmDNgZN8pKod5KmDZyBhdiY0cFWLvKsUJIkY3wLQOMlWjUD1anlMvAY5OqhGSDK1Ki4WCYq5ePW+aYG8FmWMhYwikecw/zGMIl5j/MY55pg5UOLYdvNSE1P3LpuoU5hCJrXdozP4foHHMKNj8RpHYM8hL7yWo2d8D3A1jwc5e+uzCbUeSPLv3d/GzeO8ecvM1D5WxSxpgy3mfyIUadJ8pmN/OR3ABOTZiaWs0fCB65mpC6Uy59ZGH+oMiUSx+anz8Tc8zJZp8grVTZV9A4G7DgXgvHoCY3x5gNRFA96s+49JWFOYAiv3bpi/Nz4K455u7B5iAURNeBOc2+Bfq/dOnPFmY2ijzg0vvnZ/aROeaOYnMfmM0ZlHHbMbsIuPInMt5RLq3yYnDfsUryTXbyi+3rl87yWrhkxn8WXLkXTjQ3LD+x8wN59y2/UaNwtcwWdN1/lvv6EYuzrCaNjzonuyXJzwVZUuV5ARmARDr/oMP5sCCLKzmFfORjz893HG6dDh/+mpJxbvcayboULi+uLry9xJ3bi5xaEXyhSKXtBY7/cJn+Zvm/Ig07zso7LmxG14PZJ1KvmVcP/On8G3+78FnPfQ+t+Mv4zZsOvlnbKQa2//vMrv8D7JSGLAgSAAA=";
}
