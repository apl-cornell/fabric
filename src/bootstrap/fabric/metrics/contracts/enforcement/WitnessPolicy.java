package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Arrays;
import fabric.metrics.contracts.Contract;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.metrics.ImmutableContractsVector;
import fabric.worker.metrics.StatsMap;
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
    public fabric.worker.metrics.ImmutableContractsVector get$witnesses();
    
    public fabric.worker.metrics.ImmutableContractsVector set$witnesses(
      fabric.worker.metrics.ImmutableContractsVector val);
    
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
    
    public void activate(fabric.worker.metrics.StatsMap weakStats);
    
    public static interface Activator
      extends java.util.concurrent.Callable, fabric.lang.Object {
        public fabric.metrics.contracts.Contract get$w();
        
        public fabric.metrics.contracts.Contract set$w(
          fabric.metrics.contracts.Contract val);
        
        public fabric.worker.metrics.StatsMap get$weakStats();
        
        public fabric.worker.metrics.StatsMap set$weakStats(
          fabric.worker.metrics.StatsMap val);
        
        public Activator
          fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
          fabric.metrics.contracts.Contract w,
          fabric.worker.metrics.StatsMap weakStats);
        
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
            
            public fabric.worker.metrics.StatsMap get$weakStats() {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          Activator._Impl) fetch()).get$weakStats();
            }
            
            public fabric.worker.metrics.StatsMap set$weakStats(
              fabric.worker.metrics.StatsMap val) {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          Activator._Impl) fetch()).set$weakStats(val);
            }
            
            public fabric.metrics.contracts.enforcement.WitnessPolicy.Activator
              fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
              fabric.metrics.contracts.Contract arg1,
              fabric.worker.metrics.StatsMap arg2) {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          Activator) fetch()).
                  fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
                    arg1, arg2);
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
            
            public fabric.worker.metrics.StatsMap get$weakStats() {
                return this.weakStats;
            }
            
            public fabric.worker.metrics.StatsMap set$weakStats(
              fabric.worker.metrics.StatsMap val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.weakStats = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            fabric.worker.metrics.StatsMap weakStats;
            
            public Activator
              fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
              fabric.metrics.contracts.Contract w,
              fabric.worker.metrics.StatsMap weakStats) {
                this.set$w(w);
                this.set$weakStats(weakStats);
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
                      refresh$remote(worker, null, false, this.get$weakStats());
                }
                else {
                    this.get$w().refresh(false, this.get$weakStats());
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
                $writeInline(out, this.weakStats);
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
                this.w =
                  (fabric.metrics.contracts.Contract)
                    $readRef(fabric.metrics.contracts.Contract._Proxy.class,
                             (fabric.common.RefTypeEnum) refTypes.next(), in,
                             store, intraStoreRefs, interStoreRefs);
                this.weakStats = (fabric.worker.metrics.StatsMap)
                                   in.readObject();
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
                this.weakStats = src.weakStats;
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
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             long expiry,
                             fabric.worker.metrics.
                               ImmutableObserverSet observers,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, expiry, observers, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.metrics.contracts.enforcement.
                             WitnessPolicy.Activator._Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 42, 7, 115, 116,
        -31, 56, -110, 118, -56, 26, -123, -47, 29, 53, -123, -70, -58, 70, 114,
        -21, 30, -1, -21, 72, 101, -19, 92, -20, 16, 121, -81, 67 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1526756233000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public long expiry(fabric.worker.metrics.StatsMap weakStats);
    
    public void apply(fabric.metrics.contracts.Contract mc);
    
    public void unapply(fabric.metrics.contracts.Contract mc);
    
    public java.lang.String toString();
    
    public boolean equals(fabric.lang.Object other);
    
    public void acquireReconfigLocks();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.enforcement.WitnessPolicy {
        public fabric.worker.metrics.ImmutableContractsVector get$witnesses() {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy._Impl)
                      fetch()).get$witnesses();
        }
        
        public fabric.worker.metrics.ImmutableContractsVector set$witnesses(
          fabric.worker.metrics.ImmutableContractsVector val) {
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
        
        public void activate(fabric.worker.metrics.StatsMap arg1) {
            ((fabric.metrics.contracts.enforcement.WitnessPolicy) fetch()).
              activate(arg1);
        }
        
        public long expiry(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      fetch()).expiry(arg1);
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
        public fabric.worker.metrics.ImmutableContractsVector get$witnesses() {
            return this.witnesses;
        }
        
        public fabric.worker.metrics.ImmutableContractsVector set$witnesses(
          fabric.worker.metrics.ImmutableContractsVector val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.witnesses = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.worker.metrics.ImmutableContractsVector witnesses;
        
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
            this.set$witnesses(
                   fabric.worker.metrics.ImmutableContractsVector.createVector(
                                                                    witnesses));
            fabric$lang$Object$();
            this.set$activated(false);
            return (fabric.metrics.contracts.enforcement.WitnessPolicy)
                     this.$getProxy();
        }
        
        public void activate(fabric.worker.metrics.StatsMap weakStats) {
            fabric.metrics.contracts.enforcement.WitnessPolicy._Impl.
              static_activate(
                (fabric.metrics.contracts.enforcement.WitnessPolicy)
                  this.$getProxy(), weakStats);
        }
        
        private static void static_activate(
          fabric.metrics.contracts.enforcement.WitnessPolicy tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.get$activated()) return;
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm514 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled517 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff515 = 1;
                    boolean $doBackoff516 = true;
                    boolean $retry511 = true;
                    $label509: for (boolean $commit510 = false; !$commit510; ) {
                        if ($backoffEnabled517) {
                            if ($doBackoff516) {
                                if ($backoff515 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff515);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e512) {
                                            
                                        }
                                    }
                                }
                                if ($backoff515 < 5000) $backoff515 *= 2;
                            }
                            $doBackoff516 = $backoff515 <= 32 || !$doBackoff516;
                        }
                        $commit510 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
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
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e512) {
                            $commit510 = false;
                            if ($tm514.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid513 =
                              $tm514.getCurrentTid();
                            if ($e512.tid.isDescendantOf($currentTid513)) {
                                $retry511 = true;
                            }
                            else if ($currentTid513.parent != null) {
                                $retry511 = false;
                                throw $e512;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
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
                                    fabric.common.TransactionID $currentTid513 =
                                      $tm514.getCurrentTid();
                                    if ($currentTid513 != null) {
                                        if ($e512.tid.equals($currentTid513) ||
                                              !$e512.tid.isDescendantOf(
                                                           $currentTid513)) {
                                            throw $e512;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit510 && $retry511) {
                                {  }
                                continue $label509;
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
                for (int i = 0; i < tmp.get$witnesses().length(); i++) {
                    tmp.get$witnesses().get(i).refresh(false, weakStats);
                }
            }
            else {
                java.util.concurrent.Future[] futures =
                  new java.util.concurrent.Future[tmp.get$witnesses().length()];
                for (int i = 0; i < futures.length; i++) {
                    final fabric.metrics.contracts.Contract w =
                      tmp.get$witnesses().get(i);
                    java.util.concurrent.Callable c = null;
                    {
                        java.util.concurrent.Callable c$var518 = c;
                        int i$var519 = i;
                        fabric.worker.transaction.TransactionManager $tm525 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled528 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff526 = 1;
                        boolean $doBackoff527 = true;
                        boolean $retry522 = true;
                        $label520: for (boolean $commit521 = false; !$commit521;
                                        ) {
                            if ($backoffEnabled528) {
                                if ($doBackoff527) {
                                    if ($backoff526 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff526);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e523) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff526 < 5000) $backoff526 *= 2;
                                }
                                $doBackoff527 = $backoff526 <= 32 ||
                                                  !$doBackoff527;
                            }
                            $commit521 = true;
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
                                            w, weakStats));
                            }
                            catch (final fabric.worker.RetryException $e523) {
                                $commit521 = false;
                                continue $label520;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e523) {
                                $commit521 = false;
                                fabric.common.TransactionID $currentTid524 =
                                  $tm525.getCurrentTid();
                                if ($e523.tid.isDescendantOf($currentTid524))
                                    continue $label520;
                                if ($currentTid524.parent != null) {
                                    $retry522 = false;
                                    throw $e523;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e523) {
                                $commit521 = false;
                                if ($tm525.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid524 =
                                  $tm525.getCurrentTid();
                                if ($e523.tid.isDescendantOf($currentTid524)) {
                                    $retry522 = true;
                                }
                                else if ($currentTid524.parent != null) {
                                    $retry522 = false;
                                    throw $e523;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e523) {
                                $commit521 = false;
                                if ($tm525.checkForStaleObjects())
                                    continue $label520;
                                $retry522 = false;
                                throw new fabric.worker.AbortException($e523);
                            }
                            finally {
                                if ($commit521) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e523) {
                                        $commit521 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e523) {
                                        $commit521 = false;
                                        fabric.common.TransactionID
                                          $currentTid524 =
                                          $tm525.getCurrentTid();
                                        if ($currentTid524 != null) {
                                            if ($e523.tid.equals(
                                                            $currentTid524) ||
                                                  !$e523.tid.
                                                  isDescendantOf(
                                                    $currentTid524)) {
                                                throw $e523;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit521 && $retry522) {
                                    {
                                        c = c$var518;
                                        i = i$var519;
                                    }
                                    continue $label520;
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
                        try { tmp.set$activated(true); }
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
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e532) {
                            $commit530 = false;
                            if ($tm534.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid533 =
                              $tm534.getCurrentTid();
                            if ($e532.tid.isDescendantOf($currentTid533)) {
                                $retry531 = true;
                            }
                            else if ($currentTid533.parent != null) {
                                $retry531 = false;
                                throw $e532;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
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
                                {  }
                                continue $label529;
                            }
                        }
                    }
                }
            }
        }
        
        public long expiry(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.contracts.enforcement.WitnessPolicy._Impl.
              static_expiry((fabric.metrics.contracts.enforcement.WitnessPolicy)
                              this.$getProxy(), weakStats);
        }
        
        private static long static_expiry(
          fabric.metrics.contracts.enforcement.WitnessPolicy tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (!tmp.get$activated()) tmp.activate(weakStats);
            long expiry = -1;
            boolean atLeastOnce = false;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                for (int i = 0; i < tmp.get$witnesses().length(); i++) {
                    if (!atLeastOnce || tmp.get$witnesses().get(i).getExpiry() <
                          expiry) {
                        atLeastOnce = true;
                        expiry = tmp.get$witnesses().get(i).getExpiry();
                    }
                }
            }
            else {
                {
                    long expiry$var538 = expiry;
                    boolean atLeastOnce$var539 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm545 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled548 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff546 = 1;
                    boolean $doBackoff547 = true;
                    boolean $retry542 = true;
                    $label540: for (boolean $commit541 = false; !$commit541; ) {
                        if ($backoffEnabled548) {
                            if ($doBackoff547) {
                                if ($backoff546 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff546);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e543) {
                                            
                                        }
                                    }
                                }
                                if ($backoff546 < 5000) $backoff546 *= 2;
                            }
                            $doBackoff547 = $backoff546 <= 32 || !$doBackoff547;
                        }
                        $commit541 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            for (int i = 0; i < tmp.get$witnesses().length();
                                 i++) {
                                if (!atLeastOnce ||
                                      tmp.get$witnesses().get(i).getExpiry() <
                                      expiry) {
                                    atLeastOnce = true;
                                    expiry =
                                      tmp.get$witnesses().get(i).getExpiry();
                                }
                            }
                        }
                        catch (final fabric.worker.RetryException $e543) {
                            $commit541 = false;
                            continue $label540;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e543) {
                            $commit541 = false;
                            fabric.common.TransactionID $currentTid544 =
                              $tm545.getCurrentTid();
                            if ($e543.tid.isDescendantOf($currentTid544))
                                continue $label540;
                            if ($currentTid544.parent != null) {
                                $retry542 = false;
                                throw $e543;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e543) {
                            $commit541 = false;
                            if ($tm545.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid544 =
                              $tm545.getCurrentTid();
                            if ($e543.tid.isDescendantOf($currentTid544)) {
                                $retry542 = true;
                            }
                            else if ($currentTid544.parent != null) {
                                $retry542 = false;
                                throw $e543;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e543) {
                            $commit541 = false;
                            if ($tm545.checkForStaleObjects())
                                continue $label540;
                            $retry542 = false;
                            throw new fabric.worker.AbortException($e543);
                        }
                        finally {
                            if ($commit541) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e543) {
                                    $commit541 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e543) {
                                    $commit541 = false;
                                    fabric.common.TransactionID $currentTid544 =
                                      $tm545.getCurrentTid();
                                    if ($currentTid544 != null) {
                                        if ($e543.tid.equals($currentTid544) ||
                                              !$e543.tid.isDescendantOf(
                                                           $currentTid544)) {
                                            throw $e543;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit541 && $retry542) {
                                {
                                    expiry = expiry$var538;
                                    atLeastOnce = atLeastOnce$var539;
                                }
                                continue $label540;
                            }
                        }
                    }
                }
            }
            return expiry;
        }
        
        public void apply(fabric.metrics.contracts.Contract mc) {
            if (!this.get$activated())
                activate(fabric.worker.metrics.StatsMap.emptyStats());
            for (int i = 0; i < this.get$witnesses().length(); i++) {
                this.get$witnesses().get(i).addObserver(mc);
            }
        }
        
        public void unapply(fabric.metrics.contracts.Contract mc) {
            for (int i = 0; i < this.get$witnesses().length(); i++) {
                this.get$witnesses().get(i).removeObserver(mc);
            }
        }
        
        public java.lang.String toString() {
            return java.util.Arrays.deepToString(this.get$witnesses().array());
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (!(fabric.lang.Object._Proxy.
                    $getProxy((java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.$unwrap(other)) instanceof fabric.metrics.contracts.enforcement.WitnessPolicy))
                return false;
            fabric.metrics.contracts.enforcement.WitnessPolicy that =
              (fabric.metrics.contracts.enforcement.WitnessPolicy)
                fabric.lang.Object._Proxy.$getProxy(other);
            return this.get$witnesses().equals(that.get$witnesses());
        }
        
        public void acquireReconfigLocks() {  }
        
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
            $writeInline(out, this.witnesses);
            out.writeBoolean(this.activated);
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
            this.witnesses = (fabric.worker.metrics.ImmutableContractsVector)
                               in.readObject();
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
                        fabric.worker.transaction.TransactionManager $tm554 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled557 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff555 = 1;
                        boolean $doBackoff556 = true;
                        boolean $retry551 = true;
                        $label549: for (boolean $commit550 = false; !$commit550;
                                        ) {
                            if ($backoffEnabled557) {
                                if ($doBackoff556) {
                                    if ($backoff555 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff555);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e552) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff555 < 5000) $backoff555 *= 2;
                                }
                                $doBackoff556 = $backoff555 <= 32 ||
                                                  !$doBackoff556;
                            }
                            $commit550 = true;
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
                            catch (final fabric.worker.RetryException $e552) {
                                $commit550 = false;
                                continue $label549;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e552) {
                                $commit550 = false;
                                fabric.common.TransactionID $currentTid553 =
                                  $tm554.getCurrentTid();
                                if ($e552.tid.isDescendantOf($currentTid553))
                                    continue $label549;
                                if ($currentTid553.parent != null) {
                                    $retry551 = false;
                                    throw $e552;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e552) {
                                $commit550 = false;
                                if ($tm554.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid553 =
                                  $tm554.getCurrentTid();
                                if ($e552.tid.isDescendantOf($currentTid553)) {
                                    $retry551 = true;
                                }
                                else if ($currentTid553.parent != null) {
                                    $retry551 = false;
                                    throw $e552;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e552) {
                                $commit550 = false;
                                if ($tm554.checkForStaleObjects())
                                    continue $label549;
                                $retry551 = false;
                                throw new fabric.worker.AbortException($e552);
                            }
                            finally {
                                if ($commit550) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e552) {
                                        $commit550 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e552) {
                                        $commit550 = false;
                                        fabric.common.TransactionID
                                          $currentTid553 =
                                          $tm554.getCurrentTid();
                                        if ($currentTid553 != null) {
                                            if ($e552.tid.equals(
                                                            $currentTid553) ||
                                                  !$e552.tid.
                                                  isDescendantOf(
                                                    $currentTid553)) {
                                                throw $e552;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit550 && $retry551) {
                                    {  }
                                    continue $label549;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -100, 91, 122, 38, 82,
    116, 8, -35, -2, -91, 62, 60, -34, 35, 87, -84, -104, 69, 28, -9, 26, 86,
    -71, -104, 26, -80, 105, 58, 48, 0, 83, 70 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526756233000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
