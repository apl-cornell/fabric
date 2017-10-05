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
        
        public static final byte[] $classHash = new byte[] { 3, 63, -56, 120,
        39, -1, 86, 48, 42, 121, -63, -58, 49, 0, 52, 12, -93, 94, 79, -48, -51,
        112, -23, 68, -126, 113, -93, -68, -117, -25, 14, 38 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1507234491000L;
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
                fabric.worker.transaction.TransactionManager $tm448 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled451 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff449 = 1;
                boolean $doBackoff450 = true;
                $label444: for (boolean $commit445 = false; !$commit445; ) {
                    if ($backoffEnabled451) {
                        if ($doBackoff450) {
                            if ($backoff449 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff449);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e446) {
                                        
                                    }
                                }
                            }
                            if ($backoff449 < 5000) $backoff449 *= 2;
                        }
                        $doBackoff450 = $backoff449 <= 32 || !$doBackoff450;
                    }
                    $commit445 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (tmp.get$activated()) return; }
                    catch (final fabric.worker.RetryException $e446) {
                        $commit445 = false;
                        continue $label444;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e446) {
                        $commit445 = false;
                        fabric.common.TransactionID $currentTid447 =
                          $tm448.getCurrentTid();
                        if ($e446.tid.isDescendantOf($currentTid447))
                            continue $label444;
                        if ($currentTid447.parent != null) throw $e446;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e446) {
                        $commit445 = false;
                        if ($tm448.checkForStaleObjects()) continue $label444;
                        throw new fabric.worker.AbortException($e446);
                    }
                    finally {
                        if ($commit445) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e446) {
                                $commit445 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e446) {
                                $commit445 = false;
                                fabric.common.TransactionID $currentTid447 =
                                  $tm448.getCurrentTid();
                                if ($currentTid447 != null) {
                                    if ($e446.tid.equals($currentTid447) ||
                                          !$e446.tid.isDescendantOf(
                                                       $currentTid447)) {
                                        throw $e446;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit445) {
                            {  }
                            continue $label444;
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
                        java.util.concurrent.Callable c$var452 = c;
                        int i$var453 = i;
                        fabric.worker.transaction.TransactionManager $tm458 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled461 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff459 = 1;
                        boolean $doBackoff460 = true;
                        $label454: for (boolean $commit455 = false; !$commit455;
                                        ) {
                            if ($backoffEnabled461) {
                                if ($doBackoff460) {
                                    if ($backoff459 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff459);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e456) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff459 < 5000) $backoff459 *= 2;
                                }
                                $doBackoff460 = $backoff459 <= 32 ||
                                                  !$doBackoff460;
                            }
                            $commit455 = true;
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
                            catch (final fabric.worker.RetryException $e456) {
                                $commit455 = false;
                                continue $label454;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e456) {
                                $commit455 = false;
                                fabric.common.TransactionID $currentTid457 =
                                  $tm458.getCurrentTid();
                                if ($e456.tid.isDescendantOf($currentTid457))
                                    continue $label454;
                                if ($currentTid457.parent != null) throw $e456;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e456) {
                                $commit455 = false;
                                if ($tm458.checkForStaleObjects())
                                    continue $label454;
                                throw new fabric.worker.AbortException($e456);
                            }
                            finally {
                                if ($commit455) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e456) {
                                        $commit455 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e456) {
                                        $commit455 = false;
                                        fabric.common.TransactionID
                                          $currentTid457 =
                                          $tm458.getCurrentTid();
                                        if ($currentTid457 != null) {
                                            if ($e456.tid.equals(
                                                            $currentTid457) ||
                                                  !$e456.tid.
                                                  isDescendantOf(
                                                    $currentTid457)) {
                                                throw $e456;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit455) {
                                    {
                                        c = c$var452;
                                        i = i$var453;
                                    }
                                    continue $label454;
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
            {
                fabric.worker.transaction.TransactionManager $tm466 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled469 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff467 = 1;
                boolean $doBackoff468 = true;
                $label462: for (boolean $commit463 = false; !$commit463; ) {
                    if ($backoffEnabled469) {
                        if ($doBackoff468) {
                            if ($backoff467 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff467);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e464) {
                                        
                                    }
                                }
                            }
                            if ($backoff467 < 5000) $backoff467 *= 2;
                        }
                        $doBackoff468 = $backoff467 <= 32 || !$doBackoff468;
                    }
                    $commit463 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { tmp.set$activated(true); }
                    catch (final fabric.worker.RetryException $e464) {
                        $commit463 = false;
                        continue $label462;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e464) {
                        $commit463 = false;
                        fabric.common.TransactionID $currentTid465 =
                          $tm466.getCurrentTid();
                        if ($e464.tid.isDescendantOf($currentTid465))
                            continue $label462;
                        if ($currentTid465.parent != null) throw $e464;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e464) {
                        $commit463 = false;
                        if ($tm466.checkForStaleObjects()) continue $label462;
                        throw new fabric.worker.AbortException($e464);
                    }
                    finally {
                        if ($commit463) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e464) {
                                $commit463 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e464) {
                                $commit463 = false;
                                fabric.common.TransactionID $currentTid465 =
                                  $tm466.getCurrentTid();
                                if ($currentTid465 != null) {
                                    if ($e464.tid.equals($currentTid465) ||
                                          !$e464.tid.isDescendantOf(
                                                       $currentTid465)) {
                                        throw $e464;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit463) {
                            {  }
                            continue $label462;
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
                long expiry$var470 = expiry;
                boolean atLeastOnce$var471 = atLeastOnce;
                fabric.worker.transaction.TransactionManager $tm476 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled479 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff477 = 1;
                boolean $doBackoff478 = true;
                $label472: for (boolean $commit473 = false; !$commit473; ) {
                    if ($backoffEnabled479) {
                        if ($doBackoff478) {
                            if ($backoff477 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff477);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e474) {
                                        
                                    }
                                }
                            }
                            if ($backoff477 < 5000) $backoff477 *= 2;
                        }
                        $doBackoff478 = $backoff477 <= 32 || !$doBackoff478;
                    }
                    $commit473 = true;
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
                    catch (final fabric.worker.RetryException $e474) {
                        $commit473 = false;
                        continue $label472;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e474) {
                        $commit473 = false;
                        fabric.common.TransactionID $currentTid475 =
                          $tm476.getCurrentTid();
                        if ($e474.tid.isDescendantOf($currentTid475))
                            continue $label472;
                        if ($currentTid475.parent != null) throw $e474;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e474) {
                        $commit473 = false;
                        if ($tm476.checkForStaleObjects()) continue $label472;
                        throw new fabric.worker.AbortException($e474);
                    }
                    finally {
                        if ($commit473) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e474) {
                                $commit473 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e474) {
                                $commit473 = false;
                                fabric.common.TransactionID $currentTid475 =
                                  $tm476.getCurrentTid();
                                if ($currentTid475 != null) {
                                    if ($e474.tid.equals($currentTid475) ||
                                          !$e474.tid.isDescendantOf(
                                                       $currentTid475)) {
                                        throw $e474;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit473) {
                            {
                                expiry = expiry$var470;
                                atLeastOnce = atLeastOnce$var471;
                            }
                            continue $label472;
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
                        fabric.worker.transaction.TransactionManager $tm484 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled487 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff485 = 1;
                        boolean $doBackoff486 = true;
                        $label480: for (boolean $commit481 = false; !$commit481;
                                        ) {
                            if ($backoffEnabled487) {
                                if ($doBackoff486) {
                                    if ($backoff485 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff485);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e482) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff485 < 5000) $backoff485 *= 2;
                                }
                                $doBackoff486 = $backoff485 <= 32 ||
                                                  !$doBackoff486;
                            }
                            $commit481 = true;
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
                            catch (final fabric.worker.RetryException $e482) {
                                $commit481 = false;
                                continue $label480;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e482) {
                                $commit481 = false;
                                fabric.common.TransactionID $currentTid483 =
                                  $tm484.getCurrentTid();
                                if ($e482.tid.isDescendantOf($currentTid483))
                                    continue $label480;
                                if ($currentTid483.parent != null) throw $e482;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e482) {
                                $commit481 = false;
                                if ($tm484.checkForStaleObjects())
                                    continue $label480;
                                throw new fabric.worker.AbortException($e482);
                            }
                            finally {
                                if ($commit481) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e482) {
                                        $commit481 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e482) {
                                        $commit481 = false;
                                        fabric.common.TransactionID
                                          $currentTid483 =
                                          $tm484.getCurrentTid();
                                        if ($currentTid483 != null) {
                                            if ($e482.tid.equals(
                                                            $currentTid483) ||
                                                  !$e482.tid.
                                                  isDescendantOf(
                                                    $currentTid483)) {
                                                throw $e482;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit481) {
                                    {  }
                                    continue $label480;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 11, -9, 73, 94, 41, 66,
    7, -59, 17, 92, -107, 96, -22, -123, -54, -117, 105, 2, -14, 58, 93, 68,
    122, -56, 70, -45, 82, 20, -43, 96, -109, -37 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507234491000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
