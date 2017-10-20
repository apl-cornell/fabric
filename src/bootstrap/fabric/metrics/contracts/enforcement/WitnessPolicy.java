package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.MetricContract;
import fabric.worker.transaction.TransactionManager;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * An {@link EnforcementPolicy} which enforces a {@link MetricContract} by
 * relying on a set of <em>witnesses</em>, other {@link MetricContract}s that in
 * conjunction imply the enforced {@link MetricContract}.
 */
public interface WitnessPolicy
  extends fabric.metrics.contracts.enforcement.EnforcementPolicy {
    public fabric.lang.arrays.ObjectArray get$witnesses();
    
    public fabric.lang.arrays.ObjectArray set$witnesses(
      fabric.lang.arrays.ObjectArray val);
    
    public boolean get$activated();
    
    public boolean set$activated(boolean val);
    
    /**
   * @param witnesses
   *        the array of {@link MetricContract}s used to enforce this
   *        policy. If any of the witnesses weren't already active, they
   *        are {@link Contract#activate() activated} here.
   */
    public fabric.metrics.contracts.enforcement.WitnessPolicy
      fabric$metrics$contracts$enforcement$WitnessPolicy$(
      fabric.lang.arrays.ObjectArray witnesses);
    
    public void activate();
    
    public static interface Activator
      extends java.util.concurrent.Callable, fabric.lang.Object {
        public fabric.metrics.contracts.MetricContract get$w();
        
        public fabric.metrics.contracts.MetricContract set$w(
          fabric.metrics.contracts.MetricContract val);
        
        public Activator
          fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
          fabric.metrics.contracts.MetricContract w);
        
        public java.lang.Object call();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements Activator {
            public fabric.metrics.contracts.MetricContract get$w() {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          Activator._Impl) fetch()).get$w();
            }
            
            public fabric.metrics.contracts.MetricContract set$w(
              fabric.metrics.contracts.MetricContract val) {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          Activator._Impl) fetch()).set$w(val);
            }
            
            public fabric.metrics.contracts.enforcement.WitnessPolicy.Activator
              fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
              fabric.metrics.contracts.MetricContract arg1) {
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
            public fabric.metrics.contracts.MetricContract get$w() {
                return this.w;
            }
            
            public fabric.metrics.contracts.MetricContract set$w(
              fabric.metrics.contracts.MetricContract val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.w = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            fabric.metrics.contracts.MetricContract w;
            
            public Activator
              fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
              fabric.metrics.contracts.MetricContract w) {
                this.set$w(w);
                fabric$lang$Object$();
                return (Activator) this.$getProxy();
            }
            
            public java.lang.Object call() {
                fabric.common.Logging.METRICS_LOGGER.
                  finer(
                    "ACTIVATING CHILD " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(
                                                              this.get$w())) +
                      " IN PARALLEL IN " +
                      java.lang.Thread.currentThread());
                if (!this.get$w().$getStore().name().
                      equals(fabric.worker.Worker.getWorkerName())) {
                    fabric.worker.remote.RemoteWorker worker =
                      fabric.worker.Worker.getWorker().getWorker(
                                                         this.get$w().$getStore(
                                                                        ).name(
                                                                            ));
                    ((fabric.metrics.contracts.MetricContract._Proxy)
                       this.get$w()).activate$remote(worker, null);
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
                  (fabric.metrics.contracts.MetricContract)
                    $readRef(
                      fabric.metrics.contracts.MetricContract._Proxy.class,
                      (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                      intraStoreRefs, interStoreRefs);
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
        
        public static final byte[] $classHash = new byte[] { -37, -100, 11, -56,
        -33, 59, -115, 63, -2, -121, 38, 48, 22, 12, -80, -113, 27, -73, 29,
        -101, -127, 48, 48, 52, -127, 73, 12, 89, 106, 78, 53, 84 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1508274565000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.MetricContract mc);
    
    public void unapply(fabric.metrics.contracts.MetricContract mc);
    
    public java.lang.String toString();
    
    public boolean equals(fabric.lang.Object other);
    
    public void acquireReconfigLocks();
    
    public static class _Proxy
    extends fabric.metrics.contracts.enforcement.EnforcementPolicy._Proxy
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
          fabric.lang.arrays.ObjectArray arg1) {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      fetch()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(arg1);
        }
        
        public _Proxy(WitnessPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl
    extends fabric.metrics.contracts.enforcement.EnforcementPolicy._Impl
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
   *        the array of {@link MetricContract}s used to enforce this
   *        policy. If any of the witnesses weren't already active, they
   *        are {@link Contract#activate() activated} here.
   */
        public fabric.metrics.contracts.enforcement.WitnessPolicy
          fabric$metrics$contracts$enforcement$WitnessPolicy$(
          fabric.lang.arrays.ObjectArray witnesses) {
            fabric$metrics$contracts$enforcement$EnforcementPolicy$();
            this.
              set$witnesses(
                (fabric.lang.arrays.ObjectArray)
                  new fabric.lang.arrays.ObjectArray._Impl(this.$getStore()).
                  fabric$lang$arrays$ObjectArray$(
                    this.get$$updateLabel(),
                    this.get$$updateLabel().
                        confPolicy(),
                    fabric.metrics.contracts.MetricContract._Proxy.class,
                    witnesses.get$length()).$getProxy());
            fabric.util.Arrays._Impl.arraycopy(witnesses, 0,
                                               this.get$witnesses(), 0,
                                               witnesses.get$length());
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
            {
                fabric.worker.transaction.TransactionManager $tm467 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled470 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff468 = 1;
                boolean $doBackoff469 = true;
                $label463: for (boolean $commit464 = false; !$commit464; ) {
                    if ($backoffEnabled470) {
                        if ($doBackoff469) {
                            if ($backoff468 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff468);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e465) {
                                        
                                    }
                                }
                            }
                            if ($backoff468 < 5000) $backoff468 *= 2;
                        }
                        $doBackoff469 = $backoff468 <= 32 || !$doBackoff469;
                    }
                    $commit464 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (tmp.get$activated()) return; }
                    catch (final fabric.worker.RetryException $e465) {
                        $commit464 = false;
                        continue $label463;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e465) {
                        $commit464 = false;
                        fabric.common.TransactionID $currentTid466 =
                          $tm467.getCurrentTid();
                        if ($e465.tid.isDescendantOf($currentTid466))
                            continue $label463;
                        if ($currentTid466.parent != null) throw $e465;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e465) {
                        $commit464 = false;
                        if ($tm467.checkForStaleObjects()) continue $label463;
                        throw new fabric.worker.AbortException($e465);
                    }
                    finally {
                        if ($commit464) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e465) {
                                $commit464 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e465) {
                                $commit464 = false;
                                fabric.common.TransactionID $currentTid466 =
                                  $tm467.getCurrentTid();
                                if ($currentTid466 != null) {
                                    if ($e465.tid.equals($currentTid466) ||
                                          !$e465.tid.isDescendantOf(
                                                       $currentTid466)) {
                                        throw $e465;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit464) {
                            {  }
                            continue $label463;
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
                    ((fabric.metrics.contracts.MetricContract)
                       tmp.get$witnesses().get(i)).activate();
                }
            }
            else {
                java.util.concurrent.Future[] futures =
                  new java.util.concurrent.Future[tmp.get$witnesses(
                                                        ).get$length()];
                for (int i = 0; i < futures.length; i++) {
                    final fabric.metrics.contracts.MetricContract w =
                      (fabric.metrics.contracts.MetricContract)
                        tmp.get$witnesses().get(i);
                    java.util.concurrent.Callable c = null;
                    {
                        java.util.concurrent.Callable c$var471 = c;
                        int i$var472 = i;
                        fabric.worker.transaction.TransactionManager $tm477 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled480 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff478 = 1;
                        boolean $doBackoff479 = true;
                        $label473: for (boolean $commit474 = false; !$commit474;
                                        ) {
                            if ($backoffEnabled480) {
                                if ($doBackoff479) {
                                    if ($backoff478 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff478);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e475) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff478 < 5000) $backoff478 *= 2;
                                }
                                $doBackoff479 = $backoff478 <= 32 ||
                                                  !$doBackoff479;
                            }
                            $commit474 = true;
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
                            catch (final fabric.worker.RetryException $e475) {
                                $commit474 = false;
                                continue $label473;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e475) {
                                $commit474 = false;
                                fabric.common.TransactionID $currentTid476 =
                                  $tm477.getCurrentTid();
                                if ($e475.tid.isDescendantOf($currentTid476))
                                    continue $label473;
                                if ($currentTid476.parent != null) throw $e475;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e475) {
                                $commit474 = false;
                                if ($tm477.checkForStaleObjects())
                                    continue $label473;
                                throw new fabric.worker.AbortException($e475);
                            }
                            finally {
                                if ($commit474) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e475) {
                                        $commit474 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e475) {
                                        $commit474 = false;
                                        fabric.common.TransactionID
                                          $currentTid476 =
                                          $tm477.getCurrentTid();
                                        if ($currentTid476 != null) {
                                            if ($e475.tid.equals(
                                                            $currentTid476) ||
                                                  !$e475.tid.
                                                  isDescendantOf(
                                                    $currentTid476)) {
                                                throw $e475;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit474) {
                                    {
                                        c = c$var471;
                                        i = i$var472;
                                    }
                                    continue $label473;
                                }
                            }
                        }
                    }
                    fabric.common.Logging.METRICS_LOGGER.
                      finer(
                        "SUBMITTING CHILD " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.$unwrap(w)) +
                          " IN PARALLEL IN " +
                          java.lang.Thread.currentThread());
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
            {
                fabric.worker.transaction.TransactionManager $tm485 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled488 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff486 = 1;
                boolean $doBackoff487 = true;
                $label481: for (boolean $commit482 = false; !$commit482; ) {
                    if ($backoffEnabled488) {
                        if ($doBackoff487) {
                            if ($backoff486 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff486);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e483) {
                                        
                                    }
                                }
                            }
                            if ($backoff486 < 5000) $backoff486 *= 2;
                        }
                        $doBackoff487 = $backoff486 <= 32 || !$doBackoff487;
                    }
                    $commit482 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { tmp.set$activated(true); }
                    catch (final fabric.worker.RetryException $e483) {
                        $commit482 = false;
                        continue $label481;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e483) {
                        $commit482 = false;
                        fabric.common.TransactionID $currentTid484 =
                          $tm485.getCurrentTid();
                        if ($e483.tid.isDescendantOf($currentTid484))
                            continue $label481;
                        if ($currentTid484.parent != null) throw $e483;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e483) {
                        $commit482 = false;
                        if ($tm485.checkForStaleObjects()) continue $label481;
                        throw new fabric.worker.AbortException($e483);
                    }
                    finally {
                        if ($commit482) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e483) {
                                $commit482 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e483) {
                                $commit482 = false;
                                fabric.common.TransactionID $currentTid484 =
                                  $tm485.getCurrentTid();
                                if ($currentTid484 != null) {
                                    if ($e483.tid.equals($currentTid484) ||
                                          !$e483.tid.isDescendantOf(
                                                       $currentTid484)) {
                                        throw $e483;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit482) {
                            {  }
                            continue $label481;
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
            {
                long expiry$var489 = expiry;
                boolean atLeastOnce$var490 = atLeastOnce;
                fabric.worker.transaction.TransactionManager $tm495 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled498 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff496 = 1;
                boolean $doBackoff497 = true;
                $label491: for (boolean $commit492 = false; !$commit492; ) {
                    if ($backoffEnabled498) {
                        if ($doBackoff497) {
                            if ($backoff496 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff496);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e493) {
                                        
                                    }
                                }
                            }
                            if ($backoff496 < 5000) $backoff496 *= 2;
                        }
                        $doBackoff497 = $backoff496 <= 32 || !$doBackoff497;
                    }
                    $commit492 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        for (int i = 0; i < tmp.get$witnesses().get$length();
                             i++) {
                            if (!atLeastOnce ||
                                  ((fabric.metrics.contracts.MetricContract)
                                     tmp.get$witnesses().get(i)).getExpiry() <
                                  expiry) {
                                atLeastOnce = true;
                                expiry =
                                  ((fabric.metrics.contracts.MetricContract)
                                     tmp.get$witnesses().get(i)).getExpiry();
                            }
                        }
                    }
                    catch (final fabric.worker.RetryException $e493) {
                        $commit492 = false;
                        continue $label491;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e493) {
                        $commit492 = false;
                        fabric.common.TransactionID $currentTid494 =
                          $tm495.getCurrentTid();
                        if ($e493.tid.isDescendantOf($currentTid494))
                            continue $label491;
                        if ($currentTid494.parent != null) throw $e493;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e493) {
                        $commit492 = false;
                        if ($tm495.checkForStaleObjects()) continue $label491;
                        throw new fabric.worker.AbortException($e493);
                    }
                    finally {
                        if ($commit492) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e493) {
                                $commit492 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e493) {
                                $commit492 = false;
                                fabric.common.TransactionID $currentTid494 =
                                  $tm495.getCurrentTid();
                                if ($currentTid494 != null) {
                                    if ($e493.tid.equals($currentTid494) ||
                                          !$e493.tid.isDescendantOf(
                                                       $currentTid494)) {
                                        throw $e493;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit492) {
                            {
                                expiry = expiry$var489;
                                atLeastOnce = atLeastOnce$var490;
                            }
                            continue $label491;
                        }
                    }
                }
            }
            return expiry;
        }
        
        public void apply(fabric.metrics.contracts.MetricContract mc) {
            if (!this.get$activated()) activate();
            for (int i = 0; i < this.get$witnesses().get$length(); i++) {
                ((fabric.metrics.contracts.MetricContract)
                   this.get$witnesses().get(i)).
                  addObserver(
                    (fabric.metrics.util.Observer)
                      fabric.lang.Object._Proxy.$getProxy(mc.fetch()));
            }
            fabric.common.Logging.METRICS_LOGGER.
              finer(
                "DEFENDING " +
                  java.lang.String.
                    valueOf(fabric.lang.WrappedJavaInlineable.$unwrap(mc)) +
                  " WITH " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap(
                            (fabric.metrics.contracts.enforcement.WitnessPolicy)
                              this.$getProxy())));
        }
        
        public void unapply(fabric.metrics.contracts.MetricContract mc) {
            for (int i = 0; i < this.get$witnesses().get$length(); i++) {
                ((fabric.metrics.contracts.MetricContract)
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
                ((fabric.metrics.contracts.MetricContract)
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
                        fabric.worker.transaction.TransactionManager $tm503 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled506 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff504 = 1;
                        boolean $doBackoff505 = true;
                        $label499: for (boolean $commit500 = false; !$commit500;
                                        ) {
                            if ($backoffEnabled506) {
                                if ($doBackoff505) {
                                    if ($backoff504 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff504);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e501) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff504 < 5000) $backoff504 *= 2;
                                }
                                $doBackoff505 = $backoff504 <= 32 ||
                                                  !$doBackoff505;
                            }
                            $commit500 = true;
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
                            catch (final fabric.worker.RetryException $e501) {
                                $commit500 = false;
                                continue $label499;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e501) {
                                $commit500 = false;
                                fabric.common.TransactionID $currentTid502 =
                                  $tm503.getCurrentTid();
                                if ($e501.tid.isDescendantOf($currentTid502))
                                    continue $label499;
                                if ($currentTid502.parent != null) throw $e501;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e501) {
                                $commit500 = false;
                                if ($tm503.checkForStaleObjects())
                                    continue $label499;
                                throw new fabric.worker.AbortException($e501);
                            }
                            finally {
                                if ($commit500) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e501) {
                                        $commit500 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e501) {
                                        $commit500 = false;
                                        fabric.common.TransactionID
                                          $currentTid502 =
                                          $tm503.getCurrentTid();
                                        if ($currentTid502 != null) {
                                            if ($e501.tid.equals(
                                                            $currentTid502) ||
                                                  !$e501.tid.
                                                  isDescendantOf(
                                                    $currentTid502)) {
                                                throw $e501;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit500) {
                                    {  }
                                    continue $label499;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -35, 112, -45, -74, 77,
    108, 65, -37, -45, 71, -42, -37, 50, -93, -115, 62, 80, 18, 89, 62, 82, -17,
    55, 8, -120, -47, -111, -3, 92, -11, 90, 17 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1508274565000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
