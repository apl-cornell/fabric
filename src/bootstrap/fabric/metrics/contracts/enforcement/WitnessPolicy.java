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
        
        public static final byte[] $classHash = new byte[] { 4, -34, -21, -68,
        26, 97, -26, -72, -25, -94, 78, -2, 85, -87, 88, -96, -37, 79, 108,
        -125, 42, -41, 76, 27, 61, -101, -56, -95, 81, 68, -22, -67 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1518538111000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.MetricContract mc);
    
    public void unapply(fabric.metrics.contracts.MetricContract mc);
    
    public java.lang.String toString();
    
    public boolean equals(fabric.lang.Object other);
    
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
                    fabric.worker.transaction.TransactionManager $tm503 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled506 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff504 = 1;
                    boolean $doBackoff505 = true;
                    boolean $retry500 = true;
                    $label498: for (boolean $commit499 = false; !$commit499; ) {
                        if ($backoffEnabled506) {
                            if ($doBackoff505) {
                                if ($backoff504 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff504);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e501) {
                                            
                                        }
                                    }
                                }
                                if ($backoff504 < 5000) $backoff504 *= 2;
                            }
                            $doBackoff505 = $backoff504 <= 32 || !$doBackoff505;
                        }
                        $commit499 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
                        catch (final fabric.worker.RetryException $e501) {
                            $commit499 = false;
                            continue $label498;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e501) {
                            $commit499 = false;
                            fabric.common.TransactionID $currentTid502 =
                              $tm503.getCurrentTid();
                            if ($e501.tid.isDescendantOf($currentTid502))
                                continue $label498;
                            if ($currentTid502.parent != null) {
                                $retry500 = false;
                                throw $e501;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e501) {
                            $commit499 = false;
                            if ($tm503.checkForStaleObjects())
                                continue $label498;
                            $retry500 = false;
                            throw new fabric.worker.AbortException($e501);
                        }
                        finally {
                            if ($commit499) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e501) {
                                    $commit499 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e501) {
                                    $commit499 = false;
                                    fabric.common.TransactionID $currentTid502 =
                                      $tm503.getCurrentTid();
                                    if ($currentTid502 != null) {
                                        if ($e501.tid.equals($currentTid502) ||
                                              !$e501.tid.isDescendantOf(
                                                           $currentTid502)) {
                                            throw $e501;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit499 && $retry500) {
                                {  }
                                continue $label498;
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
                        java.util.concurrent.Callable c$var507 = c;
                        int i$var508 = i;
                        fabric.worker.transaction.TransactionManager $tm514 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled517 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff515 = 1;
                        boolean $doBackoff516 = true;
                        boolean $retry511 = true;
                        $label509: for (boolean $commit510 = false; !$commit510;
                                        ) {
                            if ($backoffEnabled517) {
                                if ($doBackoff516) {
                                    if ($backoff515 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff515);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e512) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff515 < 5000) $backoff515 *= 2;
                                }
                                $doBackoff516 = $backoff515 <= 32 ||
                                                  !$doBackoff516;
                            }
                            $commit510 = true;
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
                            catch (final fabric.worker.RetryException $e512) {
                                $commit510 = false;
                                continue $label509;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e512) {
                                $commit510 = false;
                                fabric.common.TransactionID $currentTid513 =
                                  $tm514.getCurrentTid();
                                if ($e512.tid.isDescendantOf($currentTid513))
                                    continue $label509;
                                if ($currentTid513.parent != null) {
                                    $retry511 = false;
                                    throw $e512;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e512) {
                                $commit510 = false;
                                if ($tm514.checkForStaleObjects())
                                    continue $label509;
                                $retry511 = false;
                                throw new fabric.worker.AbortException($e512);
                            }
                            finally {
                                if ($commit510) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e512) {
                                        $commit510 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e512) {
                                        $commit510 = false;
                                        fabric.common.TransactionID
                                          $currentTid513 =
                                          $tm514.getCurrentTid();
                                        if ($currentTid513 != null) {
                                            if ($e512.tid.equals(
                                                            $currentTid513) ||
                                                  !$e512.tid.
                                                  isDescendantOf(
                                                    $currentTid513)) {
                                                throw $e512;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit510 && $retry511) {
                                    {
                                        c = c$var507;
                                        i = i$var508;
                                    }
                                    continue $label509;
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
                    fabric.worker.transaction.TransactionManager $tm523 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled526 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff524 = 1;
                    boolean $doBackoff525 = true;
                    boolean $retry520 = true;
                    $label518: for (boolean $commit519 = false; !$commit519; ) {
                        if ($backoffEnabled526) {
                            if ($doBackoff525) {
                                if ($backoff524 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff524);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e521) {
                                            
                                        }
                                    }
                                }
                                if ($backoff524 < 5000) $backoff524 *= 2;
                            }
                            $doBackoff525 = $backoff524 <= 32 || !$doBackoff525;
                        }
                        $commit519 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.set$activated(true); }
                        catch (final fabric.worker.RetryException $e521) {
                            $commit519 = false;
                            continue $label518;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e521) {
                            $commit519 = false;
                            fabric.common.TransactionID $currentTid522 =
                              $tm523.getCurrentTid();
                            if ($e521.tid.isDescendantOf($currentTid522))
                                continue $label518;
                            if ($currentTid522.parent != null) {
                                $retry520 = false;
                                throw $e521;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e521) {
                            $commit519 = false;
                            if ($tm523.checkForStaleObjects())
                                continue $label518;
                            $retry520 = false;
                            throw new fabric.worker.AbortException($e521);
                        }
                        finally {
                            if ($commit519) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e521) {
                                    $commit519 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e521) {
                                    $commit519 = false;
                                    fabric.common.TransactionID $currentTid522 =
                                      $tm523.getCurrentTid();
                                    if ($currentTid522 != null) {
                                        if ($e521.tid.equals($currentTid522) ||
                                              !$e521.tid.isDescendantOf(
                                                           $currentTid522)) {
                                            throw $e521;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit519 && $retry520) {
                                {  }
                                continue $label518;
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
                    long expiry$var527 = expiry;
                    boolean atLeastOnce$var528 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm534 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled537 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff535 = 1;
                    boolean $doBackoff536 = true;
                    boolean $retry531 = true;
                    $label529: for (boolean $commit530 = false; !$commit530; ) {
                        if ($backoffEnabled537) {
                            if ($doBackoff536) {
                                if ($backoff535 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff535);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e532) {
                                            
                                        }
                                    }
                                }
                                if ($backoff535 < 5000) $backoff535 *= 2;
                            }
                            $doBackoff536 = $backoff535 <= 32 || !$doBackoff536;
                        }
                        $commit530 = true;
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
                        catch (final fabric.worker.RetryException $e532) {
                            $commit530 = false;
                            continue $label529;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e532) {
                            $commit530 = false;
                            fabric.common.TransactionID $currentTid533 =
                              $tm534.getCurrentTid();
                            if ($e532.tid.isDescendantOf($currentTid533))
                                continue $label529;
                            if ($currentTid533.parent != null) {
                                $retry531 = false;
                                throw $e532;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e532) {
                            $commit530 = false;
                            if ($tm534.checkForStaleObjects())
                                continue $label529;
                            $retry531 = false;
                            throw new fabric.worker.AbortException($e532);
                        }
                        finally {
                            if ($commit530) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e532) {
                                    $commit530 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e532) {
                                    $commit530 = false;
                                    fabric.common.TransactionID $currentTid533 =
                                      $tm534.getCurrentTid();
                                    if ($currentTid533 != null) {
                                        if ($e532.tid.equals($currentTid533) ||
                                              !$e532.tid.isDescendantOf(
                                                           $currentTid533)) {
                                            throw $e532;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit530 && $retry531) {
                                {
                                    expiry = expiry$var527;
                                    atLeastOnce = atLeastOnce$var528;
                                }
                                continue $label529;
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
                        fabric.worker.transaction.TransactionManager $tm543 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled546 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff544 = 1;
                        boolean $doBackoff545 = true;
                        boolean $retry540 = true;
                        $label538: for (boolean $commit539 = false; !$commit539;
                                        ) {
                            if ($backoffEnabled546) {
                                if ($doBackoff545) {
                                    if ($backoff544 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff544);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e541) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff544 < 5000) $backoff544 *= 2;
                                }
                                $doBackoff545 = $backoff544 <= 32 ||
                                                  !$doBackoff545;
                            }
                            $commit539 = true;
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
                            catch (final fabric.worker.RetryException $e541) {
                                $commit539 = false;
                                continue $label538;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e541) {
                                $commit539 = false;
                                fabric.common.TransactionID $currentTid542 =
                                  $tm543.getCurrentTid();
                                if ($e541.tid.isDescendantOf($currentTid542))
                                    continue $label538;
                                if ($currentTid542.parent != null) {
                                    $retry540 = false;
                                    throw $e541;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e541) {
                                $commit539 = false;
                                if ($tm543.checkForStaleObjects())
                                    continue $label538;
                                $retry540 = false;
                                throw new fabric.worker.AbortException($e541);
                            }
                            finally {
                                if ($commit539) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e541) {
                                        $commit539 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e541) {
                                        $commit539 = false;
                                        fabric.common.TransactionID
                                          $currentTid542 =
                                          $tm543.getCurrentTid();
                                        if ($currentTid542 != null) {
                                            if ($e541.tid.equals(
                                                            $currentTid542) ||
                                                  !$e541.tid.
                                                  isDescendantOf(
                                                    $currentTid542)) {
                                                throw $e541;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit539 && $retry540) {
                                    {  }
                                    continue $label538;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -74, 19, 108, -122, 74,
    9, -109, 122, -95, 8, 17, -49, 115, -110, -89, 125, 125, -38, -8, -77, -123,
    61, 31, 72, 104, 74, 3, 83, -124, 30, 21, -42 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518538111000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
