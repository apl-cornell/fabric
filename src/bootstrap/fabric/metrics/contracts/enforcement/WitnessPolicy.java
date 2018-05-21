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
        public static final long jlc$SourceLastModified$fabil = 1526767069000L;
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
                    fabric.worker.transaction.TransactionManager $tm486 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled489 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff487 = 1;
                    boolean $doBackoff488 = true;
                    boolean $retry483 = true;
                    $label481: for (boolean $commit482 = false; !$commit482; ) {
                        if ($backoffEnabled489) {
                            if ($doBackoff488) {
                                if ($backoff487 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff487);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e484) {
                                            
                                        }
                                    }
                                }
                                if ($backoff487 < 5000) $backoff487 *= 2;
                            }
                            $doBackoff488 = $backoff487 <= 32 || !$doBackoff488;
                        }
                        $commit482 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
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
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e484) {
                            $commit482 = false;
                            if ($tm486.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid485 =
                              $tm486.getCurrentTid();
                            if ($e484.tid.isDescendantOf($currentTid485)) {
                                $retry483 = true;
                            }
                            else if ($currentTid485.parent != null) {
                                $retry483 = false;
                                throw $e484;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
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
                                    fabric.common.TransactionID $currentTid485 =
                                      $tm486.getCurrentTid();
                                    if ($currentTid485 != null) {
                                        if ($e484.tid.equals($currentTid485) ||
                                              !$e484.tid.isDescendantOf(
                                                           $currentTid485)) {
                                            throw $e484;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit482 && $retry483) {
                                {  }
                                continue $label481;
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
                        java.util.concurrent.Callable c$var490 = c;
                        int i$var491 = i;
                        fabric.worker.transaction.TransactionManager $tm497 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled500 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff498 = 1;
                        boolean $doBackoff499 = true;
                        boolean $retry494 = true;
                        $label492: for (boolean $commit493 = false; !$commit493;
                                        ) {
                            if ($backoffEnabled500) {
                                if ($doBackoff499) {
                                    if ($backoff498 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff498);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e495) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff498 < 5000) $backoff498 *= 2;
                                }
                                $doBackoff499 = $backoff498 <= 32 ||
                                                  !$doBackoff499;
                            }
                            $commit493 = true;
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
                            catch (final fabric.worker.RetryException $e495) {
                                $commit493 = false;
                                continue $label492;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e495) {
                                $commit493 = false;
                                fabric.common.TransactionID $currentTid496 =
                                  $tm497.getCurrentTid();
                                if ($e495.tid.isDescendantOf($currentTid496))
                                    continue $label492;
                                if ($currentTid496.parent != null) {
                                    $retry494 = false;
                                    throw $e495;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e495) {
                                $commit493 = false;
                                if ($tm497.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid496 =
                                  $tm497.getCurrentTid();
                                if ($e495.tid.isDescendantOf($currentTid496)) {
                                    $retry494 = true;
                                }
                                else if ($currentTid496.parent != null) {
                                    $retry494 = false;
                                    throw $e495;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e495) {
                                $commit493 = false;
                                if ($tm497.checkForStaleObjects())
                                    continue $label492;
                                $retry494 = false;
                                throw new fabric.worker.AbortException($e495);
                            }
                            finally {
                                if ($commit493) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e495) {
                                        $commit493 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e495) {
                                        $commit493 = false;
                                        fabric.common.TransactionID
                                          $currentTid496 =
                                          $tm497.getCurrentTid();
                                        if ($currentTid496 != null) {
                                            if ($e495.tid.equals(
                                                            $currentTid496) ||
                                                  !$e495.tid.
                                                  isDescendantOf(
                                                    $currentTid496)) {
                                                throw $e495;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit493 && $retry494) {
                                    {
                                        c = c$var490;
                                        i = i$var491;
                                    }
                                    continue $label492;
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
                        try { tmp.set$activated(true); }
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
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e504) {
                            $commit502 = false;
                            if ($tm506.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid505 =
                              $tm506.getCurrentTid();
                            if ($e504.tid.isDescendantOf($currentTid505)) {
                                $retry503 = true;
                            }
                            else if ($currentTid505.parent != null) {
                                $retry503 = false;
                                throw $e504;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
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
                                {  }
                                continue $label501;
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
                    long expiry$var510 = expiry;
                    boolean atLeastOnce$var511 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm517 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled520 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff518 = 1;
                    boolean $doBackoff519 = true;
                    boolean $retry514 = true;
                    $label512: for (boolean $commit513 = false; !$commit513; ) {
                        if ($backoffEnabled520) {
                            if ($doBackoff519) {
                                if ($backoff518 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff518);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e515) {
                                            
                                        }
                                    }
                                }
                                if ($backoff518 < 5000) $backoff518 *= 2;
                            }
                            $doBackoff519 = $backoff518 <= 32 || !$doBackoff519;
                        }
                        $commit513 = true;
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
                        catch (final fabric.worker.RetryException $e515) {
                            $commit513 = false;
                            continue $label512;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e515) {
                            $commit513 = false;
                            fabric.common.TransactionID $currentTid516 =
                              $tm517.getCurrentTid();
                            if ($e515.tid.isDescendantOf($currentTid516))
                                continue $label512;
                            if ($currentTid516.parent != null) {
                                $retry514 = false;
                                throw $e515;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e515) {
                            $commit513 = false;
                            if ($tm517.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid516 =
                              $tm517.getCurrentTid();
                            if ($e515.tid.isDescendantOf($currentTid516)) {
                                $retry514 = true;
                            }
                            else if ($currentTid516.parent != null) {
                                $retry514 = false;
                                throw $e515;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e515) {
                            $commit513 = false;
                            if ($tm517.checkForStaleObjects())
                                continue $label512;
                            $retry514 = false;
                            throw new fabric.worker.AbortException($e515);
                        }
                        finally {
                            if ($commit513) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e515) {
                                    $commit513 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e515) {
                                    $commit513 = false;
                                    fabric.common.TransactionID $currentTid516 =
                                      $tm517.getCurrentTid();
                                    if ($currentTid516 != null) {
                                        if ($e515.tid.equals($currentTid516) ||
                                              !$e515.tid.isDescendantOf(
                                                           $currentTid516)) {
                                            throw $e515;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit513 && $retry514) {
                                {
                                    expiry = expiry$var510;
                                    atLeastOnce = atLeastOnce$var511;
                                }
                                continue $label512;
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
                        fabric.worker.transaction.TransactionManager $tm526 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled529 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff527 = 1;
                        boolean $doBackoff528 = true;
                        boolean $retry523 = true;
                        $label521: for (boolean $commit522 = false; !$commit522;
                                        ) {
                            if ($backoffEnabled529) {
                                if ($doBackoff528) {
                                    if ($backoff527 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff527);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e524) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff527 < 5000) $backoff527 *= 2;
                                }
                                $doBackoff528 = $backoff527 <= 32 ||
                                                  !$doBackoff528;
                            }
                            $commit522 = true;
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
                            catch (final fabric.worker.RetryException $e524) {
                                $commit522 = false;
                                continue $label521;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e524) {
                                $commit522 = false;
                                fabric.common.TransactionID $currentTid525 =
                                  $tm526.getCurrentTid();
                                if ($e524.tid.isDescendantOf($currentTid525))
                                    continue $label521;
                                if ($currentTid525.parent != null) {
                                    $retry523 = false;
                                    throw $e524;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e524) {
                                $commit522 = false;
                                if ($tm526.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid525 =
                                  $tm526.getCurrentTid();
                                if ($e524.tid.isDescendantOf($currentTid525)) {
                                    $retry523 = true;
                                }
                                else if ($currentTid525.parent != null) {
                                    $retry523 = false;
                                    throw $e524;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e524) {
                                $commit522 = false;
                                if ($tm526.checkForStaleObjects())
                                    continue $label521;
                                $retry523 = false;
                                throw new fabric.worker.AbortException($e524);
                            }
                            finally {
                                if ($commit522) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e524) {
                                        $commit522 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e524) {
                                        $commit522 = false;
                                        fabric.common.TransactionID
                                          $currentTid525 =
                                          $tm526.getCurrentTid();
                                        if ($currentTid525 != null) {
                                            if ($e524.tid.equals(
                                                            $currentTid525) ||
                                                  !$e524.tid.
                                                  isDescendantOf(
                                                    $currentTid525)) {
                                                throw $e524;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit522 && $retry523) {
                                    {  }
                                    continue $label521;
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
    public static final long jlc$SourceLastModified$fabil = 1526767069000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
