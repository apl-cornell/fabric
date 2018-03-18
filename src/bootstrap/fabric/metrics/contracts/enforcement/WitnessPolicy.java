package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.metrics.contracts.Contract;
import fabric.worker.transaction.TransactionManager;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * An {@link EnforcementPolicy} which enforces a {@link Contract} by
 * relying on a set of <em>witnesses</em>, other {@link Contract}s that in
 * conjunction imply the enforced {@link Contract}.
 */
public interface WitnessPolicy
  extends fabric.metrics.contracts.enforcement.EnforcementPolicy,
          fabric.lang.Object
{
    public fabric.lang.arrays.ObjectArray get$witnesses();
    
    public fabric.lang.arrays.ObjectArray set$witnesses(
      fabric.lang.arrays.ObjectArray val);
    
    public boolean get$activated();
    
    public boolean set$activated(boolean val);
    
    /**
   * @param witnesses
   *        the array of {@link Contract}s used to enforce this
   *        policy. If any of the witnesses weren't already active, they
   *        are {@link Contract#activate() activated} here.
   */
    public fabric.metrics.contracts.enforcement.WitnessPolicy
      fabric$metrics$contracts$enforcement$WitnessPolicy$(
      fabric.metrics.contracts.Contract[] witnesses);
    
    public void activate();
    
    public static interface Activator
      extends java.util.concurrent.Callable, fabric.lang.Object {
        public fabric.metrics.contracts.Contract get$w();
        
        public fabric.metrics.contracts.Contract set$w(
          fabric.metrics.contracts.Contract val);
        
        public Activator
          fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
          fabric.metrics.contracts.Contract w);
        
        public java.lang.Object call();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements Activator {
            public fabric.metrics.contracts.Contract get$w() {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          Activator._Impl) fetch()).get$w();
            }
            
            public fabric.metrics.contracts.Contract set$w(
              fabric.metrics.contracts.Contract val) {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          Activator._Impl) fetch()).set$w(val);
            }
            
            public fabric.metrics.contracts.enforcement.WitnessPolicy.Activator
              fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
              fabric.metrics.contracts.Contract arg1) {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          Activator) fetch()).
                  fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
                    arg1);
            }
            
            public java.lang.Object call() {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          Activator) fetch()).call();
            }
            
            public _Proxy(Activator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl
          implements Activator {
            public fabric.metrics.contracts.Contract get$w() { return this.w; }
            
            public fabric.metrics.contracts.Contract set$w(
              fabric.metrics.contracts.Contract val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.w = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            fabric.metrics.contracts.Contract w;
            
            public Activator
              fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
              fabric.metrics.contracts.Contract w) {
                this.set$w(w);
                fabric$lang$Object$();
                return (Activator) this.$getProxy();
            }
            
            public java.lang.Object call() {
                if (!this.get$w().$getStore().name().
                      equals(fabric.worker.Worker.getWorkerName())) {
                    fabric.worker.remote.RemoteWorker worker =
                      fabric.worker.Worker.getWorker().getWorker(
                                                         this.get$w().$getStore(
                                                                        ).name(
                                                                            ));
                    ((fabric.metrics.contracts.Contract._Proxy) this.get$w()).
                      activate$remote(worker, null);
                }
                else {
                    this.get$w().activate();
                }
                return null;
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.enforcement.WitnessPolicy.
                         Activator._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.w, refTypes, out, intraStoreRefs,
                          interStoreRefs);
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
                this.w =
                  (fabric.metrics.contracts.Contract)
                    $readRef(fabric.metrics.contracts.Contract._Proxy.class,
                             (fabric.common.RefTypeEnum) refTypes.next(), in,
                             store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.
                  metrics.
                  contracts.
                  enforcement.
                  WitnessPolicy.
                  Activator.
                  _Impl
                  src =
                  (fabric.metrics.contracts.enforcement.WitnessPolicy.Activator.
                    _Impl) other;
                this.w = src.w;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy
            extends fabric.
              lang.
              Object.
              _Proxy
              implements fabric.metrics.contracts.enforcement.WitnessPolicy.
                           Activator._Static
            {
                public _Proxy(fabric.metrics.contracts.enforcement.
                                WitnessPolicy.Activator._Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.metrics.contracts.enforcement.
                  WitnessPolicy.Activator._Static $instance;
                
                static {
                    fabric.
                      metrics.
                      contracts.
                      enforcement.
                      WitnessPolicy.
                      Activator.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        metrics.
                        contracts.
                        enforcement.
                        WitnessPolicy.
                        Activator.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.metrics.contracts.enforcement.WitnessPolicy.
                            Activator._Static._Impl.class);
                    $instance =
                      (fabric.metrics.contracts.enforcement.WitnessPolicy.
                        Activator._Static) impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl
            extends fabric.
              lang.
              Object.
              _Impl
              implements fabric.metrics.contracts.enforcement.WitnessPolicy.
                           Activator._Static
            {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store, long onum, int version,
                             long expiry, fabric.worker.Store labelStore,
                             long labelOnum,
                             fabric.worker.Store accessPolicyStore,
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
                    return new fabric.metrics.contracts.enforcement.
                             WitnessPolicy.Activator._Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 100, 75, -109,
        -118, 36, -7, 125, -95, -49, -109, 8, -40, -62, -77, -19, 9, 0, 44, 20,
        -94, 28, 4, 38, -113, -94, -95, 78, -117, 87, 19, -51, 14 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1521305458000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAAL1XaWwbRRQeu64TJyFXmx4hSdNginrgVQt/2hREa1FqamiUtEW4UHe8O062He9uZ8fNphAESAgEqEj0ACSoBApCQCgSqOIPlRDiqsohDkH5AfRPJVDpD4S4JK43s2vvehMX+IOl3RnPvHnz5r3vffN2+gKaazM0UMQFnab4hEXs1CZcyGSHMLOJlqbYtrfBaF5tjmWOfvuc1hdF0SxqUbFhGrqKad6wOWrN7sH7sWIQrmwfzgzuRAlVLNyM7TGOojs3Ogz1WyadGKUm9zaZof/ISuXwY7vaX5mD2nKoTTdGOOa6mjYNThyeQy0lUioQZm/QNKLlUIdBiDZCmI6pfgAETSOHOm191MC8zIg9TGyT7heCnXbZIkzuWRkU5ptgNiur3GRgfrtrfpnrVMnqNh/MonhRJ1Sz96G7UCyL5hYpHgXBBdnKKRSpUdkkxkG8SQczWRGrpLIktlc3NI6WhFdUT5zcAgKwtKFE+JhZ3SpmYBhAna5JFBujyghnujEKonPNMuzCUXddpSDUaGF1Lx4leY4WheWG3CmQSki3iCUcdYXFpCaIWXcoZoFoXbh5/cE7jM1GFEXAZo2oVNjfCIv6QouGSZEwYqjEXdiyInsULzj5QBQhEO4KCbsyr935w3Wr+t54z5W5dBaZrYU9ROV5darQ+nFPevnaOcKMRsu0dQGFmpPLqA55M4OOBWhfUNUoJlOVyTeG37n17hfI+ShqyqC4atJyCVDVoZolS6eE3UAMwjAnWgYliKGl5XwGNUA/qxvEHd1aLNqEZ1CMyqG4Kf+Di4qgQrioAfq6UTQrfQvzMdl3LIRQBzxoDjw2Qq2LoM0j1PwwR1gZM0tEKdAyGQd4K/AQzNQxBfKW6apiM1VhZYPrIOQNAYqgsRWAOmdY5bZCYFumkhIxuHKLzg1i20Mm1dWJFBhn/R+bOOKk7eORCARhiWpqpIBtiKiHro1DFBJos0k1wvIqPXgyg+adfEIiLCGywgZkSx9GABU9YT4Jrj1c3nj9D8fzp110irWeizla71qe8ixPVS1PBSxP1Vie3KByfT8WXMFQi8jLFDBdCphuOuKk0scyL0r4xW2Zp9WdWmCndRbFHNSWHBSJyGPPl+sl7gA1e4GNgHBalo/cfuPuBwYg8o41HoO4C9FkOP180spAD0NO5dW2+7/9+eWjk6afiBwlZ/DDzJUivwfCPmSmSjTgT1/9in58In9yMhkV3JQQzsIAbOCgvvAeNXk+WOFM4Y25WdQsfICpmKoQXRMfY+a4PyKx0SpenS5MhLNCBkq6vWbEeurMh99dJS+iCjO3BSh8hPDBABsIZW0y7zt8329jhIDcV48PHTpy4f6d0vEgcdlsGybFOw0sgJkAwX3v7fvym6+nPov6weKowWICIsSRh+n4C34ReP4Uj8hpMSBaYPa0xyf9VUKxxNbLfOOAWijQG9huJ7cbJVPTizouUCKg8nvb5atPfH+w3Y03hRHXewyt+mcF/vjijeju07t+6ZNqIqq42nwH+mIuX87zNW9gDE8IO5x7Pul94l38FEAf2M7WDxBJYEg6BMkIrpG+uFK+V4fmrhavAddbPVXEh++OTeIS9sGYU6af7E5fe96lhCoYhY6ls1DCDhzIkzUvlH6KDsTfjqKGHGqX9z82+A4MZAc4yMENbqe9wSy6pGa+9jZ2r57BarL1hBMhsG04DXwqgr6QFv0mF/kucMARTcJJXfDsBu5/12tfF7PzLPGe70SQ7KyTSy6T72XitbyCxoReKpW5iLjUvZKjyLgU6+JoaV0CTHs9IdgtU9CZfYeo6K7ggvFEjeZUTY8K09u9a+shr50MmF4Tb8+gXh9dYItaZkwwcBpTKg4gpRbDkQSbUhMqTscBpPTWK01kWTV17+Fj2tZnV7sFRGftdX+9US699Pkf76ceP3tqlgsi7hWavqVR2G/pjAL5Jlm2+QA7e753bXrvuVF3zyUh+8LSz980feqGZeqjUTSniqQZtWLtosFa/DQxAqWusa0GRf3VUDSLUOyCp4BQyxVu2/xZEEUuydaDUNwqF2gwtjJ3mzxFn3rtB+HY+tkecTWJv9fJvXIXoYPbxGuEo2tccCY9cCar4EwGbudknds56Z9oqNYPA/CMgR+OeO1ddfwgXjtmnlgsmfTa8fonDh5IvcicjNcujmIAZlrJgnaZBYJhUi7DiPGMA8D3qw9PdM1/L2FkSgOML52l6PI+F9T0W2Tq3JZVXXUKrkUzPuC8dcePtTUuPLb9C1kiVD8FEnADF8uUBhkv0I9bjBR16Y6Ey3+WbKB2T/6b43HUHPgnPUNdDfug+q+ngbu3huwH14C61to1XH6XiV5QbhzywpUT/xyrypSBVyVKnZ7CQEgrXFZbGUrN3WUmPpSnf1z4a7xx21lZWwBe+rUthx5M/jb59EeHGs+8+eqFBFo1/5me2LJHnnn65odumfdB69+gy96/wA8AAA==";
    }
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.Contract mc);
    
    public void unapply(fabric.metrics.contracts.Contract mc);
    
    public java.lang.String toString();
    
    public boolean equals(fabric.lang.Object other);
    
    public void acquireReconfigLocks();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.enforcement.WitnessPolicy {
        public fabric.lang.arrays.ObjectArray get$witnesses() {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy._Impl)
                      fetch()).get$witnesses();
        }
        
        public fabric.lang.arrays.ObjectArray set$witnesses(
          fabric.lang.arrays.ObjectArray val) {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy._Impl)
                      fetch()).set$witnesses(val);
        }
        
        public boolean get$activated() {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy._Impl)
                      fetch()).get$activated();
        }
        
        public boolean set$activated(boolean val) {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy._Impl)
                      fetch()).set$activated(val);
        }
        
        public fabric.metrics.contracts.enforcement.WitnessPolicy
          fabric$metrics$contracts$enforcement$WitnessPolicy$(
          fabric.metrics.contracts.Contract[] arg1) {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      fetch()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(arg1);
        }
        
        public void activate() {
            ((fabric.metrics.contracts.enforcement.WitnessPolicy) fetch()).
              activate();
        }
        
        public long expiry() {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      fetch()).expiry();
        }
        
        public void apply(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.contracts.enforcement.WitnessPolicy) fetch()).
              apply(arg1);
        }
        
        public void unapply(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.contracts.enforcement.WitnessPolicy) fetch()).
              unapply(arg1);
        }
        
        public void acquireReconfigLocks() {
            ((fabric.metrics.contracts.enforcement.WitnessPolicy) fetch()).
              acquireReconfigLocks();
        }
        
        public _Proxy(WitnessPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.contracts.enforcement.WitnessPolicy {
        public fabric.lang.arrays.ObjectArray get$witnesses() {
            return this.witnesses;
        }
        
        public fabric.lang.arrays.ObjectArray set$witnesses(
          fabric.lang.arrays.ObjectArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.witnesses = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.lang.arrays.ObjectArray witnesses;
        
        public boolean get$activated() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.activated;
        }
        
        public boolean set$activated(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.activated = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** Is this currently actively enforced? */
        private boolean activated;
        
        /**
   * @param witnesses
   *        the array of {@link Contract}s used to enforce this
   *        policy. If any of the witnesses weren't already active, they
   *        are {@link Contract#activate() activated} here.
   */
        public fabric.metrics.contracts.enforcement.WitnessPolicy
          fabric$metrics$contracts$enforcement$WitnessPolicy$(
          fabric.metrics.contracts.Contract[] witnesses) {
            fabric$lang$Object$();
            this.
              set$witnesses(
                (fabric.lang.arrays.ObjectArray)
                  new fabric.lang.arrays.ObjectArray._Impl(this.$getStore()).
                  fabric$lang$arrays$ObjectArray$(
                    this.get$$updateLabel(),
                    this.get$$updateLabel().
                        confPolicy(),
                    fabric.metrics.contracts.Contract._Proxy.class,
                    witnesses.length).$getProxy());
            for (int i = 0; i < witnesses.length; i++)
                this.get$witnesses().set(i, witnesses[i]);
            this.set$activated(false);
            return (fabric.metrics.contracts.enforcement.WitnessPolicy)
                     this.$getProxy();
        }
        
        public void activate() {
            fabric.metrics.contracts.enforcement.WitnessPolicy._Impl.
              static_activate(
                (fabric.metrics.contracts.enforcement.WitnessPolicy)
                  this.$getProxy());
        }
        
        private static void static_activate(
          fabric.metrics.contracts.enforcement.WitnessPolicy tmp) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.get$activated()) return;
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm603 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled606 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff604 = 1;
                    boolean $doBackoff605 = true;
                    boolean $retry600 = true;
                    $label598: for (boolean $commit599 = false; !$commit599; ) {
                        if ($backoffEnabled606) {
                            if ($doBackoff605) {
                                if ($backoff604 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff604);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e601) {
                                            
                                        }
                                    }
                                }
                                if ($backoff604 < 5000) $backoff604 *= 2;
                            }
                            $doBackoff605 = $backoff604 <= 32 || !$doBackoff605;
                        }
                        $commit599 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
                        catch (final fabric.worker.RetryException $e601) {
                            $commit599 = false;
                            continue $label598;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e601) {
                            $commit599 = false;
                            fabric.common.TransactionID $currentTid602 =
                              $tm603.getCurrentTid();
                            if ($e601.tid.isDescendantOf($currentTid602))
                                continue $label598;
                            if ($currentTid602.parent != null) {
                                $retry600 = false;
                                throw $e601;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e601) {
                            $commit599 = false;
                            if ($tm603.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid602 =
                              $tm603.getCurrentTid();
                            if ($e601.tid.isDescendantOf($currentTid602)) {
                                $retry600 = true;
                            }
                            else if ($currentTid602.parent != null) {
                                $retry600 = false;
                                throw $e601;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e601) {
                            $commit599 = false;
                            if ($tm603.checkForStaleObjects())
                                continue $label598;
                            $retry600 = false;
                            throw new fabric.worker.AbortException($e601);
                        }
                        finally {
                            if ($commit599) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e601) {
                                    $commit599 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e601) {
                                    $commit599 = false;
                                    fabric.common.TransactionID $currentTid602 =
                                      $tm603.getCurrentTid();
                                    if ($currentTid602 != null) {
                                        if ($e601.tid.equals($currentTid602) ||
                                              !$e601.tid.isDescendantOf(
                                                           $currentTid602)) {
                                            throw $e601;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit599 && $retry600) {
                                {  }
                                continue $label598;
                            }
                        }
                    }
                }
            }
            if (!fabric.lang.Object._Proxy.
                  idEquals(
                    fabric.worker.transaction.TransactionManager.getInstance().
                        getCurrentLog(),
                    null)) {
                for (int i = 0; i < tmp.get$witnesses().get$length(); i++) {
                    ((fabric.metrics.contracts.Contract)
                       tmp.get$witnesses().get(i)).activate();
                }
            }
            else {
                java.util.concurrent.Future[] futures =
                  new java.util.concurrent.Future[tmp.get$witnesses(
                                                        ).get$length()];
                for (int i = 0; i < futures.length; i++) {
                    final fabric.metrics.contracts.Contract w =
                      (fabric.metrics.contracts.Contract)
                        tmp.get$witnesses().get(i);
                    java.util.concurrent.Callable c = null;
                    {
                        java.util.concurrent.Callable c$var607 = c;
                        int i$var608 = i;
                        fabric.worker.transaction.TransactionManager $tm614 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled617 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff615 = 1;
                        boolean $doBackoff616 = true;
                        boolean $retry611 = true;
                        $label609: for (boolean $commit610 = false; !$commit610;
                                        ) {
                            if ($backoffEnabled617) {
                                if ($doBackoff616) {
                                    if ($backoff615 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff615);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e612) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff615 < 5000) $backoff615 *= 2;
                                }
                                $doBackoff616 = $backoff615 <= 32 ||
                                                  !$doBackoff616;
                            }
                            $commit610 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                c =
                                  (java.util.concurrent.Callable)
                                    fabric.lang.WrappedJavaInlineable.
                                    $unwrap(
                                      ((Activator)
                                         new fabric.metrics.contracts.
                                           enforcement.WitnessPolicy.Activator.
                                           _Impl(
                                           fabric.metrics.contracts.enforcement.WitnessPolicy._Static._Proxy.$instance.$getStore(
                                                                                                                         )).
                                         $getProxy()).
                                          fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
                                            w));
                            }
                            catch (final fabric.worker.RetryException $e612) {
                                $commit610 = false;
                                continue $label609;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e612) {
                                $commit610 = false;
                                fabric.common.TransactionID $currentTid613 =
                                  $tm614.getCurrentTid();
                                if ($e612.tid.isDescendantOf($currentTid613))
                                    continue $label609;
                                if ($currentTid613.parent != null) {
                                    $retry611 = false;
                                    throw $e612;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e612) {
                                $commit610 = false;
                                if ($tm614.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid613 =
                                  $tm614.getCurrentTid();
                                if ($e612.tid.isDescendantOf($currentTid613)) {
                                    $retry611 = true;
                                }
                                else if ($currentTid613.parent != null) {
                                    $retry611 = false;
                                    throw $e612;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e612) {
                                $commit610 = false;
                                if ($tm614.checkForStaleObjects())
                                    continue $label609;
                                $retry611 = false;
                                throw new fabric.worker.AbortException($e612);
                            }
                            finally {
                                if ($commit610) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e612) {
                                        $commit610 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e612) {
                                        $commit610 = false;
                                        fabric.common.TransactionID
                                          $currentTid613 =
                                          $tm614.getCurrentTid();
                                        if ($currentTid613 != null) {
                                            if ($e612.tid.equals(
                                                            $currentTid613) ||
                                                  !$e612.tid.
                                                  isDescendantOf(
                                                    $currentTid613)) {
                                                throw $e612;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit610 && $retry611) {
                                    {
                                        c = c$var607;
                                        i = i$var608;
                                    }
                                    continue $label609;
                                }
                            }
                        }
                    }
                    futures[i] =
                      fabric.metrics.contracts.enforcement.WitnessPolicy._Static._Proxy.$instance.
                        get$service().submit(c);
                }
                for (int i = 0; i < futures.length; i++) {
                    try { futures[i].get(); }
                    catch (java.util.concurrent.ExecutionException e) {  }
                    catch (java.lang.InterruptedException e) {  }
                }
            }
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.set$activated(true);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm623 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled626 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff624 = 1;
                    boolean $doBackoff625 = true;
                    boolean $retry620 = true;
                    $label618: for (boolean $commit619 = false; !$commit619; ) {
                        if ($backoffEnabled626) {
                            if ($doBackoff625) {
                                if ($backoff624 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff624);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e621) {
                                            
                                        }
                                    }
                                }
                                if ($backoff624 < 5000) $backoff624 *= 2;
                            }
                            $doBackoff625 = $backoff624 <= 32 || !$doBackoff625;
                        }
                        $commit619 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.set$activated(true); }
                        catch (final fabric.worker.RetryException $e621) {
                            $commit619 = false;
                            continue $label618;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e621) {
                            $commit619 = false;
                            fabric.common.TransactionID $currentTid622 =
                              $tm623.getCurrentTid();
                            if ($e621.tid.isDescendantOf($currentTid622))
                                continue $label618;
                            if ($currentTid622.parent != null) {
                                $retry620 = false;
                                throw $e621;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e621) {
                            $commit619 = false;
                            if ($tm623.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid622 =
                              $tm623.getCurrentTid();
                            if ($e621.tid.isDescendantOf($currentTid622)) {
                                $retry620 = true;
                            }
                            else if ($currentTid622.parent != null) {
                                $retry620 = false;
                                throw $e621;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e621) {
                            $commit619 = false;
                            if ($tm623.checkForStaleObjects())
                                continue $label618;
                            $retry620 = false;
                            throw new fabric.worker.AbortException($e621);
                        }
                        finally {
                            if ($commit619) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e621) {
                                    $commit619 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e621) {
                                    $commit619 = false;
                                    fabric.common.TransactionID $currentTid622 =
                                      $tm623.getCurrentTid();
                                    if ($currentTid622 != null) {
                                        if ($e621.tid.equals($currentTid622) ||
                                              !$e621.tid.isDescendantOf(
                                                           $currentTid622)) {
                                            throw $e621;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit619 && $retry620) {
                                {  }
                                continue $label618;
                            }
                        }
                    }
                }
            }
        }
        
        public long expiry() {
            return fabric.metrics.contracts.enforcement.WitnessPolicy._Impl.
              static_expiry((fabric.metrics.contracts.enforcement.WitnessPolicy)
                              this.$getProxy());
        }
        
        private static long static_expiry(
          fabric.metrics.contracts.enforcement.WitnessPolicy tmp) {
            long expiry = -1;
            boolean atLeastOnce = false;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                for (int i = 0; i < tmp.get$witnesses().get$length(); i++) {
                    if (!atLeastOnce ||
                          ((fabric.metrics.contracts.Contract)
                             tmp.get$witnesses().get(i)).getExpiry() < expiry) {
                        atLeastOnce = true;
                        expiry = ((fabric.metrics.contracts.Contract)
                                    tmp.get$witnesses().get(i)).getExpiry();
                    }
                }
            }
            else {
                {
                    long expiry$var627 = expiry;
                    boolean atLeastOnce$var628 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm634 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled637 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff635 = 1;
                    boolean $doBackoff636 = true;
                    boolean $retry631 = true;
                    $label629: for (boolean $commit630 = false; !$commit630; ) {
                        if ($backoffEnabled637) {
                            if ($doBackoff636) {
                                if ($backoff635 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff635);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e632) {
                                            
                                        }
                                    }
                                }
                                if ($backoff635 < 5000) $backoff635 *= 2;
                            }
                            $doBackoff636 = $backoff635 <= 32 || !$doBackoff636;
                        }
                        $commit630 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            for (int i = 0;
                                 i < tmp.get$witnesses().get$length(); i++) {
                                if (!atLeastOnce ||
                                      ((fabric.metrics.contracts.Contract)
                                         tmp.get$witnesses().get(i)).getExpiry(
                                                                       ) <
                                      expiry) {
                                    atLeastOnce = true;
                                    expiry =
                                      ((fabric.metrics.contracts.Contract)
                                         tmp.get$witnesses().get(i)).getExpiry(
                                                                       );
                                }
                            }
                        }
                        catch (final fabric.worker.RetryException $e632) {
                            $commit630 = false;
                            continue $label629;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e632) {
                            $commit630 = false;
                            fabric.common.TransactionID $currentTid633 =
                              $tm634.getCurrentTid();
                            if ($e632.tid.isDescendantOf($currentTid633))
                                continue $label629;
                            if ($currentTid633.parent != null) {
                                $retry631 = false;
                                throw $e632;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e632) {
                            $commit630 = false;
                            if ($tm634.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid633 =
                              $tm634.getCurrentTid();
                            if ($e632.tid.isDescendantOf($currentTid633)) {
                                $retry631 = true;
                            }
                            else if ($currentTid633.parent != null) {
                                $retry631 = false;
                                throw $e632;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e632) {
                            $commit630 = false;
                            if ($tm634.checkForStaleObjects())
                                continue $label629;
                            $retry631 = false;
                            throw new fabric.worker.AbortException($e632);
                        }
                        finally {
                            if ($commit630) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e632) {
                                    $commit630 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e632) {
                                    $commit630 = false;
                                    fabric.common.TransactionID $currentTid633 =
                                      $tm634.getCurrentTid();
                                    if ($currentTid633 != null) {
                                        if ($e632.tid.equals($currentTid633) ||
                                              !$e632.tid.isDescendantOf(
                                                           $currentTid633)) {
                                            throw $e632;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit630 && $retry631) {
                                {
                                    expiry = expiry$var627;
                                    atLeastOnce = atLeastOnce$var628;
                                }
                                continue $label629;
                            }
                        }
                    }
                }
            }
            return expiry;
        }
        
        public void apply(fabric.metrics.contracts.Contract mc) {
            if (!this.get$activated()) activate();
            for (int i = 0; i < this.get$witnesses().get$length(); i++) {
                ((fabric.metrics.contracts.Contract)
                   this.get$witnesses().get(i)).
                  addObserver(
                    (fabric.metrics.util.Observer)
                      fabric.lang.Object._Proxy.$getProxy(mc.fetch()));
            }
        }
        
        public void unapply(fabric.metrics.contracts.Contract mc) {
            for (int i = 0; i < this.get$witnesses().get$length(); i++) {
                ((fabric.metrics.contracts.Contract)
                   this.get$witnesses().get(i)).removeObserver(mc);
            }
        }
        
        public java.lang.String toString() {
            return fabric.util.Arrays._Impl.deepToString(this.get$witnesses());
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (!(fabric.lang.Object._Proxy.
                    $getProxy((java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.$unwrap(other)) instanceof fabric.metrics.contracts.enforcement.WitnessPolicy))
                return false;
            fabric.metrics.contracts.enforcement.WitnessPolicy that =
              (fabric.metrics.contracts.enforcement.WitnessPolicy)
                fabric.lang.Object._Proxy.$getProxy(other);
            return ((java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                          this.get$witnesses(
                                                                 ))).
              equals(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                      that.get$witnesses()));
        }
        
        public void acquireReconfigLocks() {
            for (int i = 0; i < this.get$witnesses().get$length(); i++) {
                ((fabric.metrics.contracts.Contract)
                   this.get$witnesses().get(i)).acquireReconfigLocks();
            }
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.enforcement.WitnessPolicy.
                     _Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.witnesses, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            out.writeBoolean(this.activated);
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
            this.witnesses =
              (fabric.lang.arrays.ObjectArray)
                $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.activated = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.enforcement.WitnessPolicy._Impl src =
              (fabric.metrics.contracts.enforcement.WitnessPolicy._Impl) other;
            this.witnesses = src.witnesses;
            this.activated = src.activated;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public int get$POOL_SIZE();
        
        public int set$POOL_SIZE(int val);
        
        public int postInc$POOL_SIZE();
        
        public int postDec$POOL_SIZE();
        
        public java.util.concurrent.ExecutorService get$service();
        
        public java.util.concurrent.ExecutorService set$service(
          java.util.concurrent.ExecutorService val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.enforcement.WitnessPolicy._Static
        {
            public int get$POOL_SIZE() {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          _Static._Impl) fetch()).get$POOL_SIZE();
            }
            
            public int set$POOL_SIZE(int val) {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          _Static._Impl) fetch()).set$POOL_SIZE(val);
            }
            
            public int postInc$POOL_SIZE() {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          _Static._Impl) fetch()).postInc$POOL_SIZE();
            }
            
            public int postDec$POOL_SIZE() {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          _Static._Impl) fetch()).postDec$POOL_SIZE();
            }
            
            public java.util.concurrent.ExecutorService get$service() {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          _Static._Impl) fetch()).get$service();
            }
            
            public java.util.concurrent.ExecutorService set$service(
              java.util.concurrent.ExecutorService val) {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          _Static._Impl) fetch()).set$service(val);
            }
            
            public _Proxy(fabric.metrics.contracts.enforcement.WitnessPolicy.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.enforcement.
              WitnessPolicy._Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  enforcement.
                  WitnessPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    enforcement.
                    WitnessPolicy.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Static._Impl.class);
                $instance =
                  (fabric.metrics.contracts.enforcement.WitnessPolicy._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.enforcement.WitnessPolicy._Static
        {
            public int get$POOL_SIZE() { return this.POOL_SIZE; }
            
            public int set$POOL_SIZE(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.POOL_SIZE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$POOL_SIZE() {
                int tmp = this.get$POOL_SIZE();
                this.set$POOL_SIZE((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$POOL_SIZE() {
                int tmp = this.get$POOL_SIZE();
                this.set$POOL_SIZE((int) (tmp - 1));
                return tmp;
            }
            
            private int POOL_SIZE;
            
            public java.util.concurrent.ExecutorService get$service() {
                return this.service;
            }
            
            public java.util.concurrent.ExecutorService set$service(
              java.util.concurrent.ExecutorService val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.service = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private java.util.concurrent.ExecutorService service;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeInt(this.POOL_SIZE);
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
                this.POOL_SIZE = in.readInt();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.enforcement.WitnessPolicy.
                         _Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm643 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled646 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff644 = 1;
                        boolean $doBackoff645 = true;
                        boolean $retry640 = true;
                        $label638: for (boolean $commit639 = false; !$commit639;
                                        ) {
                            if ($backoffEnabled646) {
                                if ($doBackoff645) {
                                    if ($backoff644 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff644);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e641) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff644 < 5000) $backoff644 *= 2;
                                }
                                $doBackoff645 = $backoff644 <= 32 ||
                                                  !$doBackoff645;
                            }
                            $commit639 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.WitnessPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$POOL_SIZE((int) 32);
                                fabric.metrics.contracts.enforcement.WitnessPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$service(
                                    java.util.concurrent.Executors.
                                        newFixedThreadPool(
                                          fabric.metrics.contracts.enforcement.WitnessPolicy._Static._Proxy.$instance.
                                              get$POOL_SIZE()));
                            }
                            catch (final fabric.worker.RetryException $e641) {
                                $commit639 = false;
                                continue $label638;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e641) {
                                $commit639 = false;
                                fabric.common.TransactionID $currentTid642 =
                                  $tm643.getCurrentTid();
                                if ($e641.tid.isDescendantOf($currentTid642))
                                    continue $label638;
                                if ($currentTid642.parent != null) {
                                    $retry640 = false;
                                    throw $e641;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e641) {
                                $commit639 = false;
                                if ($tm643.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid642 =
                                  $tm643.getCurrentTid();
                                if ($e641.tid.isDescendantOf($currentTid642)) {
                                    $retry640 = true;
                                }
                                else if ($currentTid642.parent != null) {
                                    $retry640 = false;
                                    throw $e641;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e641) {
                                $commit639 = false;
                                if ($tm643.checkForStaleObjects())
                                    continue $label638;
                                $retry640 = false;
                                throw new fabric.worker.AbortException($e641);
                            }
                            finally {
                                if ($commit639) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e641) {
                                        $commit639 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e641) {
                                        $commit639 = false;
                                        fabric.common.TransactionID
                                          $currentTid642 =
                                          $tm643.getCurrentTid();
                                        if ($currentTid642 != null) {
                                            if ($e641.tid.equals(
                                                            $currentTid642) ||
                                                  !$e641.tid.
                                                  isDescendantOf(
                                                    $currentTid642)) {
                                                throw $e641;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit639 && $retry640) {
                                    {  }
                                    continue $label638;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 114, 2, -105, 89, 38,
    66, -19, 0, -99, 86, 4, 118, 19, 70, -38, -46, 99, 19, -73, 54, 28, 7, 7,
    40, -56, -39, 65, 71, 74, -103, -52, 2 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521305458000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
