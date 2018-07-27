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
                     fabric.worker.metrics.treaties.TreatySet treaties,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, treaties, labelStore,
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
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.metrics.treaties.TreatySet treaties,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, treaties, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { 20, 98, -99, -96, 84,
    100, -76, 108, 36, -60, 36, 124, 57, -101, 76, 98, 46, -17, -51, -75, -90,
    -11, 30, -3, 29, -31, 99, -117, -43, 80, 14, 127 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1529598589000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1YfWwcxRWfO9tnn3H8lS8wjuM4R9SEcKfQIhFMEckRxwdHbdkOAofGzO3OnTfe293MzjmXlPDRCiUKECrqOIE2Uf9IPwBDEGrgjyoSf0CB0iJAfPWPQpBApUojhBBt/+Drvdm927312dgSlnbe3Mx7b95783tvZjxzgdTZnPRkaUbT42Kfxex4H82k0oOU20xN6tS2R2B0TLmoNjX9yR/UrjAJp0mTQg3T0BSqjxm2IM3p3XSSJgwmEjuGUr07SVRBwX5qjwsS3rm1yEm3Zer7crop3EVm6T96eWLq2K7WZ2pIyyhp0YxhQYWmJE1DsKIYJU15ls8wbm9RVaaOkjaDMXWYcY3q2n5gNI1R0m5rOYOKAmf2ELNNfRIZ2+2CxbhcszSI5ptgNi8owuRgfqtjfkFoeiKt2aI3TSJZjemqvYfcRWrTpC6r0xwwrkiXvEhIjYk+HAf2Rg3M5FmqsJJI7YRmqIKsDkqUPY7dBAwgWp9nYtwsL1VrUBgg7Y5JOjVyiWHBNSMHrHVmAVYRpGNOpcDUYFFlgubYmCAXB/kGnSngisqwoIggy4NsUhPsWUdgz3y7deEn1x75mdFvhEkIbFaZoqP9DSDUFRAaYlnGmaEwR7BpQ3qarjh7KEwIMC8PMDs8z9352fUbu55/2eG5tArPQGY3U8SYcirT/EZncv3mGjSjwTJtDaFQ4bnc1UF3prdoAdpXlDXiZLw0+fzQX26753F2PkwaUySimHohD6hqU8y8pemMb2cG41QwNUWizFCTcj5F6qGf1gzmjA5kszYTKVKry6GIKX9DiLKgAkNUD33NyJqlvkXFuOwXLUJIPXwkBN8uQhq7gS6Bn/2CDCTGzTxLZPQC2wvwTsDHKFfGE5C3XFMSNlcSvGAIDZjcIUARENvxf4gB4rNaLm0qE3Ewxfr+VRbRi9a9oRAEeLViqixDbdgtFzlbB3VIjn5TVxkfU/QjZ1Nk6dlHJHqiiHgbUCvjE4Id7wzWCr/sVGHrts+eGnvVQR7KuuETpNuxM+7a6eyu304wrQnzKg6VKg6VaiZUjCdPpp6Q8InYMs/K2ppA2zWWTkXW5PkiCYWka8ukvNQMuz4B1QQKRtP64Z/eeMehnhoArLW3FvcQWGPB9PGKTgp6FHJiTGk5+Ml/T08fML1EEiQ2K79nS2J+9gTjxE2FqVD/PPUbuumZsbMHYmGsLVGIhKAATKghXcE1KvK0t1TzMBp1aXIRxoDqOFUqVI1inJt7vRG5/83YtDtQwGAFDJTl8sfD1on3Xvv3D+VBUqqsLb4SPMxEry+bUVmLzNs2L/YjnDHg++fxwV8dvXBwpww8cKyttmAM2yRkMYX0Nfl9L+/5xwfvn3or7G2WIBGrkNE1pSh9afsG/kLwfY0fpiQOIIXCnHTLQXe5Hli48jrPNqgMOlQnMN2O7TDypqplNZrRGSLly5bLNp35z5FWZ7t1GHGCx8nG71bgjV+yldzz6q7/dUk1IQVPJi9+HptT7pZ6mrdwTvehHcV731z1yEv0BCAfipWt7Wey/hAZDyI38EoZiytkuykw9yNsepxodZYBHyz9fXiGelgcTcz8piN53Xkn68tYRB1rqmT9LdSXJlc+nv8i3BN5MUzqR0mrPL6pIW6hUL0ABqNwANtJdzBNllTMVx6mzsnRW861zmAe+JYNZoFXbaCP3NhvdIDvAAcC0YhBwtLdAkG5y6WyxC+1sF1WDBHZuUaKrJXtOmzWl8BYb3FtEpBVLCsNo9Koqyzn0jt8SgU4XeCQv0Lf1483l9m7Mci1PCTUpHsQs0NTh7+JH5lykOjcVtbOujD4ZZwbi3R3CTaXF2GVNfOtIiX6/nX6wJ//eOCgc5q3V56924xC/sl3vvpb/Pi5V6pU9PqMaeqMyhrQWpwjYNjd4MVK/kXcs3O7S7f4YuVDLUEXVs11zZHmn/r51El14Hebwi70twkSFaZ1hc4mme5TFcFgzLpG3ywvdx6Oz51ftTk58XHOCcbqwMpB7sdunnll+zrl4TCpKQN21o2yUqi3EqaNnMGF2BipAGt3OVYIKbIZvhWAsRKN+sHqlHIZeGxSlZBscEVKNBwMc/Xyces8c6PYDAsZSzjFY+5hHkO4xPyHecwzbbDSoZXwrSGk5mqXblicQyiy3qVrF+YQnWdOweZ2QWrHIS+xn6xmcyd8P4AFP3LpG4uzGUVed+lfF2bz7nnm5G0eKmeTMs6UiT6TDzHqPFG2uJmP5AZwatLU1Gr+QPDIVYTUnXbpscX5gyLTLv3lwvyZnGdONnsEaaPKnoLG2YAF91o4BjW5OcZcIILqUf+iS59bnAMo8qxLn16YA3fPM3cvNvuhILoOzGv2rdB/1KUPLM5sFLnfpfctzOxD88wdxuYXYDZnUMZtx+wi4MqfyHhHubTKi8F9xyrJF9ipj2/auHyO18LFs/6z4Mo9dbKlYeXJHe/Ku2/5jRqFq2W2oOv+s9zXj1icZTVpfNQ52S1JHhJkaZXnBWQAEun8gw7nw4I0V3IK+cjHnp/vKNw6HT78NS3j3OE1knU5XF5cXXh7iTu3Fzl1SfCFIpV2FDj+w2Xm85X/jzSMnJN3XNiM7mWZE78dUf+kx16I3bn51+lM/NO/n/n9F11frfpQuf/twea7vwVTmdL6CBIAAA==";
}
