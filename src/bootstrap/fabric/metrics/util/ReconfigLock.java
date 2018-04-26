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
    
    public static final byte[] $classHash = new byte[] { 117, 127, -20, 40, -7,
    26, 34, 52, 22, 38, -86, -70, 80, 77, 96, 69, -5, -68, -32, 53, -70, 101,
    -13, -40, -25, -109, -68, -56, -124, -123, -12, -83 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524675608000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1Yb2wcxRWfO9tnn3HiPyEOGMdxnGtE/nCnUPiQmFaNr3F85YItO0HUaWPmdud8G+/tbmbnnEvaNNCWJkIlVcEJQS35FARNDbSVEBUlEh9aGpSqEqhq6Ye2+RJBFfIB0b/qH/re7Nzt3vpsbKmWZt7czHtv3rz5vTdvPX+TNLmcDORpzjCT4pjD3OQwzWWyY5S7TE+b1HX3w+yUdktj5tz7z+t9URLNkjaNWrZlaNScslxBVmcP01masphIHRjPDB4kcQ0FR6hbECR6cKjMSb9jm8emTVuoTRboP7stNff0oY6fNJD2SdJuWBOCCkNL25ZgZTFJ2oqsmGPc3a3rTJ8knRZj+gTjBjWN48BoW5OkyzWmLSpKnLnjzLXNWWTscksO43LPyiSab4PZvKQJm4P5HZ75JWGYqazhisEsieUNZuruEfI10pglTXmTTgNjd7ZyipTUmBrGeWBvNcBMnqcaq4g0zhiWLsiGsET1xIn7gQFEm4tMFOzqVo0WhQnS5ZlkUms6NSG4YU0Da5Ndgl0E6VlUKTC1OFSbodNsSpDbwnxj3hJwxaVbUESQtWE2qQnurCd0Z4HbuvnAfWe+Yo1YURIBm3WmmWh/Cwj1hYTGWZ5xZmnME2zbmj1Huy+fjhICzGtDzB7Pq1/98HPb+9644vHcUYdnNHeYaWJKu5hb/XZvesvOBjSjxbFdA6FQc3J5q2NqZbDsANq7qxpxMVlZfGP8zS8+condiJLWDIlptlkqAqo6NbvoGCbje5nFOBVMz5A4s/S0XM+QZhhnDYt5s6P5vMtEhjSacipmy9/gojyoQBc1w9iw8nZl7FBRkOOyQwhphkYi0A4R0roR6Cr4mRFkNFWwiyyVM0vsKMA7BY1RrhVSELfc0FIu11K8ZAkDmNQUoAiI651/nAHi88Z01tZmkmCK8/9XWcZTdByNRMDBGzRbZznqwm0p5AyNmRAcI7apMz6lmWcuZ8iay89I9MQR8S6gVvonAjfeG84VQdm50tCeD1+auuohD2WV+wTp9+xMKju92w3aCaa1YVwlIVMlIVPNR8rJ9IXMDyV8Yq6Ms6q2NtC2yzGpyNu8WCaRiDzarVJeaoZbn4FsAgmjbcvEl7/w8OmBBgCsc7QR7xBYE+Hw8ZNOBkYUYmJKaz/1/t9ePnfC9gNJkMSC+F4oifE5EPYTtzWmQ/7z1W/tp69MXT6RiGJuiYMnBAVgQg7pC+9RE6eDlZyH3mjKklvQB9TEpUqiahUFbh/1Z+T9r8auy4MCOitkoEyXn5lwnn3313/+tHxIKpm1PZCCJ5gYDEQzKmuXcdvp+34/Zwz4/nB+7KmzN08dlI4Hjk31Nkxgn4YophC+Nn/sypHf/+mPF38T9S9LkJhTypmGVpZn6fwY/iLQ/osNQxInkEJiTqt00F/NBw7uvNm3DTKDCdkJTHcTB6yirRt5g+ZMhkj5d/undrzywZkO77pNmPGcx8n2T1bgz98+RB65eujvfVJNRMOXyfefz+aluzW+5t2c02NoR/nRd9Y/80v6LCAfkpVrHGcy/xDpDyIv8G7pi7tkvyO0dg92A563equAD6f+YXxDfSxOpua/35P+7A0v6qtYRB0b60T9gzQQJndfKv41OhD7RZQ0T5IO+XxTSzxIIXsBDCbhAXbTajJLVtWs1z6m3ssxWI213nAcBLYNR4GfbWCM3Dhu9YDvAQcc0YpO6ofWDk45qaiBq2sc7G8tR4gc7JIim2S/GbstFTA2O9yYBWSVq0qjqDSulBUUpQGlAg5d4hC/wjw2gpXLwtsY40YRAmpWPcTs9NzjHyfPzHlI9KqVTQsKhqCMV7HI467CblsZdtm41C5SYvi9l0/87IUTp7zXvKv27d1jlYov/vY/v0qev/ZWnYzenLNtk1GZAzrKizgMh1t9X8m/mHo7RxQdCvgqgFqCR1i/WJkjzb/49bkL+uhzO6IK+nsEiQvbuctks8wMqIqhMxaU0ftkcefj+NqN9TvTM9enPWdsCO0c5v7Bvvm39m7WnoyShipgF1SUtUKDtTBt5QwKYmt/DVj7q75CSJGd0LoBY7sUbQ2C1Uvl0vHYZWoh2aJE4oo2hN1cP308tMTaJHYTQvoSXvGEeswTCJdE8DFP+KaN1R5oHTQonhp2KrptZQdCka2KJpZ3ILrEmobdlwRpLEBc4jhdz+ZeaHfChtcVfWdlNqPI24peXZ7Nh5dYk9U8ZM42rcC0mWGbjzPqfaLsVpGP5PNwqFnb0OudB4vXewlp+pGi51d2HhR5WtHvLu88s0usye6IIJ1UO1IyOBt1oK6FZ9CQl2MtBqK9UJG/qehPV3YAFHlV0R8v7wAnl1h7FLvjkBDVAZY0+yEYf0/RJ1ZmNop8W9FvLc/s00usPY7dN8BsziCNu57ZZcBVMJCxRrmjzheD+o7V0j9nF6/fv33tIl8Lty34z4KSe+lCe8u6Cwd+J2vf6jdqHErLfMk0g295YBxzOMsb0vi497I7knxHkDV1Pi8gApDIwz/hcT4pyOpaTiE/8nEU5DsLVafHh7/OST/3+J1kXQvFi9KF1UvSq17k0u3hLxSptKfE8R8u8x+t+0esZf81WePCZfSXTn5w5z97Bu7p3nzptbF9D+/51+vX7n2NffTue0+9fuWbj/3lxf8BvKa/8ggSAAA=";
}
