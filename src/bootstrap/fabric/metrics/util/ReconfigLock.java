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
    
    public static final byte[] $classHash = new byte[] { -61, -14, 36, 2, -126,
    16, -98, 125, 36, 53, -92, -40, -19, 64, -5, -11, 12, 34, -49, -22, -87, 74,
    55, -9, 118, 10, -99, 42, 50, 107, 112, 89 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518618069000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1YfWwcxRWfOztnnzlsx8EJGMdxnEukfHCnpEVtMK1ILjE5cjSW7SBwCmZud+688d7uZnbOuYS6BVRIQJA/WpOC1PgvVwVqEgkJ8QeyhMS3qFqBUEtbFfIPXw3+g1aUSv2g783s3e6tP5pItTTz5mbee/Pmze+9N+u5BbLK5aSvQPOGmRInHOamBmg+mxuk3GV6xqSuOwKzY9pVjdmzn/5S74mSaI4kNGrZlqFRc8xyBWnNHaWTNG0xkT48lO0/QuIaCh6g7rgg0SN7K5z0OrZ5omjawttkkf4ntqenf3ZP+/MNpG2UtBnWsKDC0DK2JVhFjJJEiZXyjLt7dJ3po2S1xZg+zLhBTeMkMNrWKOlwjaJFRZkzd4i5tjmJjB1u2WFc7lmdRPNtMJuXNWFzML9dmV8WhpnOGa7oz5FYwWCm7h4jPySNObKqYNIiMK7NVU+RlhrTAzgP7C0GmMkLVGNVkcYJw9IF2RCWqJ04eRAYQLSpxMS4Xduq0aIwQTqUSSa1iulhwQ2rCKyr7DLsIkjXskqBqdmh2gQtsjFBrg3zDaol4IpLt6CIIJ1hNqkJ7qwrdGeB21r43s1n7rMOWFESAZt1pplofzMI9YSEhliBcWZpTAkmtuXO0rXzp6OEAHNniFnxvPiDL27Z0fPym4rn+iV4DuWPMk2MabP51ne6M1t3N6AZzY7tGgiFupPLWx30VvorDqB9bU0jLqaqiy8PvX7X/c+yS1HSkiUxzTbLJUDVas0uOYbJ+K3MYpwKpmdJnFl6Rq5nSROMc4bF1OyhQsFlIksaTTkVs+VvcFEBVKCLmmBsWAW7OnaoGJfjikMIaYJGItBGCInfCDQBP3cIcig9bpdYOm+W2XGAdxoao1wbT0PcckNLu1xL87IlDGDypgBFQFx1/iEGiC8YxZytTaTAFOf/r7KCp2g/HomAgzdots7y1IXb8pCzd9CE4DhgmzrjY5p5Zj5L1sw/JdETR8S7gFrpnwjceHc4VwRlp8t7939xfuxthTyU9dwnSK+yM+XZqW43aCeYlsC4SkGmSkGmmotUUpmZ7K8kfGKujLOatgRou8kxqSjYvFQhkYg82jVSXmqGW5+AbAIJI7F1+O7b7j3d1wCAdY434h0CazIcPn7SycKIQkyMaW2nPv37hbNTth9IgiQXxfdiSYzPvrCfuK0xHfKfr35bL31hbH4qGcXcEgdPCArAhBzSE96jLk77qzkPvbEqR65CH1ATl6qJqkWMc/u4PyPvvxW7DgUFdFbIQJkuvzPsnHv/N599QxaSamZtC6TgYSb6A9GMytpk3K72fT/CGQO+Pz85+NMnFk4dkY4Hjk1LbZjEPgNRTCF8bf7Qm8f+8OEHs+9F/csSJOaU86ahVeRZVn8NfxFo/8GGIYkTSCExZ7x00FvLBw7uvMW3DTKDCdkJTHeTh62SrRsFg+ZNhkj5V9vmnS98fqZdXbcJM8p5nOz43wr8+ev2kvvfvuerHqkmomFl8v3ns6l0t8bXvIdzegLtqDzw7vqn3qDnAPmQrFzjJJP5h0h/EHmBu6QvbpD9ztDaN7HrU97qrgE+nPoHsIb6WBxNz/28K/PdSyrqa1hEHRuXiPo7aCBMdj1b+jLaF3stSppGSbss39QSd1DIXgCDUSjAbsabzJGr69bri6mqHP21WOsOx0Fg23AU+NkGxsiN4xYFfAUccEQLOqkXWis4Je/RHK6ucbC/phIhcnCTFNkk+y3Yba2CscnhxiQgq1JTGkWlcU/ZQY/uCygVcOgyh/gV5okD+HJZfBuD3ChBQE16hZidnn7069SZaYVE9VrZtOjBEJRRLxZ53Kux216BXTautIuUGPjkwtRLT0+dUtW8o7727rfKped+9+9fp568+NYSGb0pb9smozIHtFeWcRgOt/m+kn8xr3Zu9+jmgK8CqCV4hPXLPXOk+bMPTs/oh36xM+pBf78gcWE7N5hskpkBVfiM3rjoGX27fNz5OL54af3uzMRHReWMDaGdw9zP3D731q1btJ9ESUMNsItelPVC/fUwbeEMHsTWSB1Ye2u+QkiR3dA6AWPrFY0sBMGqUrl0PHbZekg2eyKfe/TjsJuXTh93rrA2it2wkL6EKp70inkS4ZIMFvOkb9pg/YHWQesBC+c9euHKDoQi5z369OUdiK6wpmH3fUEaxyEucZxZyuZuaJsJaTjp0eKV2YwiBY/ee3k2H11hTb7mIXMmtHGmTQzYfIhR9Ymyx4t8JPvgUJO2oS93B9+GqPjMo3+6svOgyB89+t7lnWdyhTXZHYN8QrVjZYPLWLCWM3sQTHjXo69emdko8opH5y/P7B+tsPYAdifBbM4gC7rK7ApcSzAOsMRfv8SD2/sM1DKvstmPDu7oXOaxfe2iD3NP7vxMW/O6mcO/l0/H2ideHF5mhbJpBkthYBxzOCsY0vi4KoyOJA8LsmaJ1zkACIk8/I8V5yOCtNZzCvmNjKMg32PwaFN8+Otx6ecuv5OsnVD7PV1Y/FOq+Mul68IPfKm0q8zx/xVzf1v3j1jzyEX5RMRy/spfk9EH22emkjfOvr9wyz+/TPT99i/P3PatryZbzm3bNeHc9V+Oni5aRxEAAA==";
}
