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
        
        public static final byte[] $classHash = new byte[] { 34, -105, -3, 106,
        18, -32, -83, 70, -22, -35, 10, 67, -37, -11, -104, -45, -5, 72, 51, 47,
        -101, 69, -79, -42, -72, -49, 39, 63, -93, 73, -36, -57 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1507234250000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.MetricContract mc);
    
    public void unapply(fabric.metrics.contracts.MetricContract mc);
    
    public java.lang.String toString();
    
    public boolean equals(fabric.lang.Object other);
    
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
    
    public static final byte[] $classHash = new byte[] { 118, 104, 2, -57, 95,
    -105, 51, -41, 86, -35, -64, -78, -106, 70, -17, 121, -31, -106, 1, -89,
    -51, -23, -126, -103, 84, -109, -4, 120, -50, 79, 29, -8 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507234250000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
