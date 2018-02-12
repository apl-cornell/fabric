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
   *        the array of {@link MetricContract}s used to enforce this
   *        policy. If any of the witnesses weren't already active, they
   *        are {@link Contract#activate() activated} here.
   */
    public fabric.metrics.contracts.enforcement.WitnessPolicy
      fabric$metrics$contracts$enforcement$WitnessPolicy$(
      fabric.metrics.contracts.MetricContract[] witnesses);
    
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
        
        public static final byte[] $classHash = new byte[] { -39, -53, -94, 118,
        28, 12, 109, -117, -62, -7, -23, 65, -72, 104, -123, 59, 109, -97, 85,
        106, 126, 18, 18, 83, -32, -76, 57, -112, -104, 51, -128, 3 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1518448064000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.MetricContract mc);
    
    public void unapply(fabric.metrics.contracts.MetricContract mc);
    
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
          fabric.metrics.contracts.MetricContract[] arg1) {
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
        
        public void apply(fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.contracts.enforcement.WitnessPolicy) fetch()).
              apply(arg1);
        }
        
        public void unapply(fabric.metrics.contracts.MetricContract arg1) {
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
   *        the array of {@link MetricContract}s used to enforce this
   *        policy. If any of the witnesses weren't already active, they
   *        are {@link Contract#activate() activated} here.
   */
        public fabric.metrics.contracts.enforcement.WitnessPolicy
          fabric$metrics$contracts$enforcement$WitnessPolicy$(
          fabric.metrics.contracts.MetricContract[] witnesses) {
            fabric$lang$Object$();
            this.
              set$witnesses(
                (fabric.lang.arrays.ObjectArray)
                  new fabric.lang.arrays.ObjectArray._Impl(this.$getStore()).
                  fabric$lang$arrays$ObjectArray$(
                    this.get$$updateLabel(),
                    this.get$$updateLabel().
                        confPolicy(),
                    fabric.metrics.contracts.MetricContract._Proxy.class,
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
                    fabric.worker.transaction.TransactionManager $tm475 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled478 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff476 = 1;
                    boolean $doBackoff477 = true;
                    boolean $retry472 = true;
                    $label470: for (boolean $commit471 = false; !$commit471; ) {
                        if ($backoffEnabled478) {
                            if ($doBackoff477) {
                                if ($backoff476 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff476);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e473) {
                                            
                                        }
                                    }
                                }
                                if ($backoff476 < 5000) $backoff476 *= 2;
                            }
                            $doBackoff477 = $backoff476 <= 32 || !$doBackoff477;
                        }
                        $commit471 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
                        catch (final fabric.worker.RetryException $e473) {
                            $commit471 = false;
                            continue $label470;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e473) {
                            $commit471 = false;
                            fabric.common.TransactionID $currentTid474 =
                              $tm475.getCurrentTid();
                            if ($e473.tid.isDescendantOf($currentTid474))
                                continue $label470;
                            if ($currentTid474.parent != null) {
                                $retry472 = false;
                                throw $e473;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e473) {
                            $commit471 = false;
                            if ($tm475.checkForStaleObjects())
                                continue $label470;
                            $retry472 = false;
                            throw new fabric.worker.AbortException($e473);
                        }
                        finally {
                            if ($commit471) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e473) {
                                    $commit471 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e473) {
                                    $commit471 = false;
                                    fabric.common.TransactionID $currentTid474 =
                                      $tm475.getCurrentTid();
                                    if ($currentTid474 != null) {
                                        if ($e473.tid.equals($currentTid474) ||
                                              !$e473.tid.isDescendantOf(
                                                           $currentTid474)) {
                                            throw $e473;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit471 && $retry472) {
                                {  }
                                continue $label470;
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
                        java.util.concurrent.Callable c$var479 = c;
                        int i$var480 = i;
                        fabric.worker.transaction.TransactionManager $tm486 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled489 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff487 = 1;
                        boolean $doBackoff488 = true;
                        boolean $retry483 = true;
                        $label481: for (boolean $commit482 = false; !$commit482;
                                        ) {
                            if ($backoffEnabled489) {
                                if ($doBackoff488) {
                                    if ($backoff487 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff487);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e484) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff487 < 5000) $backoff487 *= 2;
                                }
                                $doBackoff488 = $backoff487 <= 32 ||
                                                  !$doBackoff488;
                            }
                            $commit482 = true;
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
                            catch (final fabric.worker.RetryException $e484) {
                                $commit482 = false;
                                continue $label481;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e484) {
                                $commit482 = false;
                                fabric.common.TransactionID $currentTid485 =
                                  $tm486.getCurrentTid();
                                if ($e484.tid.isDescendantOf($currentTid485))
                                    continue $label481;
                                if ($currentTid485.parent != null) {
                                    $retry483 = false;
                                    throw $e484;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e484) {
                                $commit482 = false;
                                if ($tm486.checkForStaleObjects())
                                    continue $label481;
                                $retry483 = false;
                                throw new fabric.worker.AbortException($e484);
                            }
                            finally {
                                if ($commit482) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e484) {
                                        $commit482 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e484) {
                                        $commit482 = false;
                                        fabric.common.TransactionID
                                          $currentTid485 =
                                          $tm486.getCurrentTid();
                                        if ($currentTid485 != null) {
                                            if ($e484.tid.equals(
                                                            $currentTid485) ||
                                                  !$e484.tid.
                                                  isDescendantOf(
                                                    $currentTid485)) {
                                                throw $e484;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit482 && $retry483) {
                                    {
                                        c = c$var479;
                                        i = i$var480;
                                    }
                                    continue $label481;
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
                    fabric.worker.transaction.TransactionManager $tm495 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled498 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff496 = 1;
                    boolean $doBackoff497 = true;
                    boolean $retry492 = true;
                    $label490: for (boolean $commit491 = false; !$commit491; ) {
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
                        $commit491 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.set$activated(true); }
                        catch (final fabric.worker.RetryException $e493) {
                            $commit491 = false;
                            continue $label490;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e493) {
                            $commit491 = false;
                            fabric.common.TransactionID $currentTid494 =
                              $tm495.getCurrentTid();
                            if ($e493.tid.isDescendantOf($currentTid494))
                                continue $label490;
                            if ($currentTid494.parent != null) {
                                $retry492 = false;
                                throw $e493;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e493) {
                            $commit491 = false;
                            if ($tm495.checkForStaleObjects())
                                continue $label490;
                            $retry492 = false;
                            throw new fabric.worker.AbortException($e493);
                        }
                        finally {
                            if ($commit491) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e493) {
                                    $commit491 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e493) {
                                    $commit491 = false;
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
                            if (!$commit491 && $retry492) {
                                {  }
                                continue $label490;
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
                          ((fabric.metrics.contracts.MetricContract)
                             tmp.get$witnesses().get(i)).getExpiry() < expiry) {
                        atLeastOnce = true;
                        expiry = ((fabric.metrics.contracts.MetricContract)
                                    tmp.get$witnesses().get(i)).getExpiry();
                    }
                }
            }
            else {
                {
                    long expiry$var499 = expiry;
                    boolean atLeastOnce$var500 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm506 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled509 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff507 = 1;
                    boolean $doBackoff508 = true;
                    boolean $retry503 = true;
                    $label501: for (boolean $commit502 = false; !$commit502; ) {
                        if ($backoffEnabled509) {
                            if ($doBackoff508) {
                                if ($backoff507 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff507);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e504) {
                                            
                                        }
                                    }
                                }
                                if ($backoff507 < 5000) $backoff507 *= 2;
                            }
                            $doBackoff508 = $backoff507 <= 32 || !$doBackoff508;
                        }
                        $commit502 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            for (int i = 0;
                                 i < tmp.get$witnesses().get$length(); i++) {
                                if (!atLeastOnce ||
                                      ((fabric.metrics.contracts.MetricContract)
                                         tmp.get$witnesses().get(i)).getExpiry(
                                                                       ) <
                                      expiry) {
                                    atLeastOnce = true;
                                    expiry =
                                      ((fabric.metrics.contracts.MetricContract)
                                         tmp.get$witnesses().get(i)).getExpiry(
                                                                       );
                                }
                            }
                        }
                        catch (final fabric.worker.RetryException $e504) {
                            $commit502 = false;
                            continue $label501;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e504) {
                            $commit502 = false;
                            fabric.common.TransactionID $currentTid505 =
                              $tm506.getCurrentTid();
                            if ($e504.tid.isDescendantOf($currentTid505))
                                continue $label501;
                            if ($currentTid505.parent != null) {
                                $retry503 = false;
                                throw $e504;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e504) {
                            $commit502 = false;
                            if ($tm506.checkForStaleObjects())
                                continue $label501;
                            $retry503 = false;
                            throw new fabric.worker.AbortException($e504);
                        }
                        finally {
                            if ($commit502) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e504) {
                                    $commit502 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e504) {
                                    $commit502 = false;
                                    fabric.common.TransactionID $currentTid505 =
                                      $tm506.getCurrentTid();
                                    if ($currentTid505 != null) {
                                        if ($e504.tid.equals($currentTid505) ||
                                              !$e504.tid.isDescendantOf(
                                                           $currentTid505)) {
                                            throw $e504;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit502 && $retry503) {
                                {
                                    expiry = expiry$var499;
                                    atLeastOnce = atLeastOnce$var500;
                                }
                                continue $label501;
                            }
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
                        fabric.worker.transaction.TransactionManager $tm515 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled518 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff516 = 1;
                        boolean $doBackoff517 = true;
                        boolean $retry512 = true;
                        $label510: for (boolean $commit511 = false; !$commit511;
                                        ) {
                            if ($backoffEnabled518) {
                                if ($doBackoff517) {
                                    if ($backoff516 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff516);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e513) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff516 < 5000) $backoff516 *= 2;
                                }
                                $doBackoff517 = $backoff516 <= 32 ||
                                                  !$doBackoff517;
                            }
                            $commit511 = true;
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
                            catch (final fabric.worker.RetryException $e513) {
                                $commit511 = false;
                                continue $label510;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e513) {
                                $commit511 = false;
                                fabric.common.TransactionID $currentTid514 =
                                  $tm515.getCurrentTid();
                                if ($e513.tid.isDescendantOf($currentTid514))
                                    continue $label510;
                                if ($currentTid514.parent != null) {
                                    $retry512 = false;
                                    throw $e513;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e513) {
                                $commit511 = false;
                                if ($tm515.checkForStaleObjects())
                                    continue $label510;
                                $retry512 = false;
                                throw new fabric.worker.AbortException($e513);
                            }
                            finally {
                                if ($commit511) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e513) {
                                        $commit511 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e513) {
                                        $commit511 = false;
                                        fabric.common.TransactionID
                                          $currentTid514 =
                                          $tm515.getCurrentTid();
                                        if ($currentTid514 != null) {
                                            if ($e513.tid.equals(
                                                            $currentTid514) ||
                                                  !$e513.tid.
                                                  isDescendantOf(
                                                    $currentTid514)) {
                                                throw $e513;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit511 && $retry512) {
                                    {  }
                                    continue $label510;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -68, 7, -8, 99, 77,
    -35, 103, 11, -77, -71, 50, -75, 82, -116, 20, -126, 107, 82, -87, -120,
    -58, 9, -53, 103, -64, -61, -92, -70, 44, -12, 69, -90 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518448064000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
