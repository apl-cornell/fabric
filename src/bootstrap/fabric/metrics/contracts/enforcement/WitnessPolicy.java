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
import java.util.logging.Level;
import fabric.common.Logging;
import java.io.StringWriter;
import java.io.PrintWriter;

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
        
        public static final byte[] $classHash = new byte[] { -117, -120, -51,
        81, 78, -14, 91, -118, -12, 27, 1, 16, -23, -49, -109, -85, 54, 52, -62,
        39, 12, -73, 25, -103, 75, 49, 54, -72, -16, 4, 114, -86 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527629368000L;
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
                        try { if (tmp.get$activated()) return; }
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
                                {  }
                                continue $label540;
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
                  new java.util.concurrent.Future[tmp.get$witnesses().length() -
                                                    1];
                for (int i = 1; i < tmp.get$witnesses().length(); i++) {
                    final fabric.metrics.contracts.Contract w =
                      tmp.get$witnesses().get(i);
                    java.util.concurrent.Callable c = null;
                    {
                        java.util.concurrent.Callable c$var549 = c;
                        int i$var550 = i;
                        fabric.worker.transaction.TransactionManager $tm556 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled559 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff557 = 1;
                        boolean $doBackoff558 = true;
                        boolean $retry553 = true;
                        $label551: for (boolean $commit552 = false; !$commit552;
                                        ) {
                            if ($backoffEnabled559) {
                                if ($doBackoff558) {
                                    if ($backoff557 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff557);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e554) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff557 < 5000) $backoff557 *= 2;
                                }
                                $doBackoff558 = $backoff557 <= 32 ||
                                                  !$doBackoff558;
                            }
                            $commit552 = true;
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
                            catch (final fabric.worker.RetryException $e554) {
                                $commit552 = false;
                                continue $label551;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e554) {
                                $commit552 = false;
                                fabric.common.TransactionID $currentTid555 =
                                  $tm556.getCurrentTid();
                                if ($e554.tid.isDescendantOf($currentTid555))
                                    continue $label551;
                                if ($currentTid555.parent != null) {
                                    $retry553 = false;
                                    throw $e554;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e554) {
                                $commit552 = false;
                                if ($tm556.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid555 =
                                  $tm556.getCurrentTid();
                                if ($e554.tid.isDescendantOf($currentTid555)) {
                                    $retry553 = true;
                                }
                                else if ($currentTid555.parent != null) {
                                    $retry553 = false;
                                    throw $e554;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e554) {
                                $commit552 = false;
                                if ($tm556.checkForStaleObjects())
                                    continue $label551;
                                $retry553 = false;
                                throw new fabric.worker.AbortException($e554);
                            }
                            finally {
                                if ($commit552) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e554) {
                                        $commit552 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e554) {
                                        $commit552 = false;
                                        fabric.common.TransactionID
                                          $currentTid555 =
                                          $tm556.getCurrentTid();
                                        if ($currentTid555 != null) {
                                            if ($e554.tid.equals(
                                                            $currentTid555) ||
                                                  !$e554.tid.
                                                  isDescendantOf(
                                                    $currentTid555)) {
                                                throw $e554;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit552 && $retry553) {
                                    {
                                        c = c$var549;
                                        i = i$var550;
                                    }
                                    continue $label551;
                                }
                            }
                        }
                    }
                    futures[i - 1] =
                      fabric.metrics.contracts.enforcement.WitnessPolicy._Static._Proxy.$instance.
                        get$service().submit(c);
                }
                fabric.metrics.contracts.Contract w =
                  tmp.get$witnesses().get(0);
                if (!w.$getStore().name().equals(
                                            fabric.worker.Worker.getWorkerName(
                                                                   ))) {
                    fabric.worker.remote.RemoteWorker worker =
                      fabric.worker.Worker.getWorker().getWorker(
                                                         w.$getStore().name());
                    ((fabric.metrics.contracts.Contract._Proxy) w).
                      refresh$remote(worker, null, false, weakStats);
                } else {
                    w.refresh(false, weakStats);
                }
                for (int i = 0; i < futures.length; i++) {
                    try { futures[i].get(); }
                    catch (java.util.concurrent.ExecutionException e) {
                        java.io.StringWriter sw = new java.io.StringWriter();
                        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
                        e.printStackTrace(pw);
                        java.lang.String sStackTrace = sw.toString();
                        fabric.common.Logging.METRICS_LOGGER.
                          warning("Failure of parallel activation child: " + e +
                                    " Stack\n" + sStackTrace);
                    }
                    catch (java.lang.InterruptedException e) {
                        java.io.StringWriter sw = new java.io.StringWriter();
                        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
                        e.printStackTrace(pw);
                        java.lang.String sStackTrace = sw.toString();
                        fabric.common.Logging.METRICS_LOGGER.
                          warning("Failure of parallel activation child: " + e +
                                    " Stack\n" + sStackTrace);
                    }
                }
            }
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.set$activated(true);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm565 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled568 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff566 = 1;
                    boolean $doBackoff567 = true;
                    boolean $retry562 = true;
                    $label560: for (boolean $commit561 = false; !$commit561; ) {
                        if ($backoffEnabled568) {
                            if ($doBackoff567) {
                                if ($backoff566 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff566);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e563) {
                                            
                                        }
                                    }
                                }
                                if ($backoff566 < 5000) $backoff566 *= 2;
                            }
                            $doBackoff567 = $backoff566 <= 32 || !$doBackoff567;
                        }
                        $commit561 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.set$activated(true); }
                        catch (final fabric.worker.RetryException $e563) {
                            $commit561 = false;
                            continue $label560;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e563) {
                            $commit561 = false;
                            fabric.common.TransactionID $currentTid564 =
                              $tm565.getCurrentTid();
                            if ($e563.tid.isDescendantOf($currentTid564))
                                continue $label560;
                            if ($currentTid564.parent != null) {
                                $retry562 = false;
                                throw $e563;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e563) {
                            $commit561 = false;
                            if ($tm565.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid564 =
                              $tm565.getCurrentTid();
                            if ($e563.tid.isDescendantOf($currentTid564)) {
                                $retry562 = true;
                            }
                            else if ($currentTid564.parent != null) {
                                $retry562 = false;
                                throw $e563;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e563) {
                            $commit561 = false;
                            if ($tm565.checkForStaleObjects())
                                continue $label560;
                            $retry562 = false;
                            throw new fabric.worker.AbortException($e563);
                        }
                        finally {
                            if ($commit561) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e563) {
                                    $commit561 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e563) {
                                    $commit561 = false;
                                    fabric.common.TransactionID $currentTid564 =
                                      $tm565.getCurrentTid();
                                    if ($currentTid564 != null) {
                                        if ($e563.tid.equals($currentTid564) ||
                                              !$e563.tid.isDescendantOf(
                                                           $currentTid564)) {
                                            throw $e563;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit561 && $retry562) {
                                {  }
                                continue $label560;
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
                    long expiry$var569 = expiry;
                    boolean atLeastOnce$var570 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm576 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled579 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff577 = 1;
                    boolean $doBackoff578 = true;
                    boolean $retry573 = true;
                    $label571: for (boolean $commit572 = false; !$commit572; ) {
                        if ($backoffEnabled579) {
                            if ($doBackoff578) {
                                if ($backoff577 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff577);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e574) {
                                            
                                        }
                                    }
                                }
                                if ($backoff577 < 5000) $backoff577 *= 2;
                            }
                            $doBackoff578 = $backoff577 <= 32 || !$doBackoff578;
                        }
                        $commit572 = true;
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
                        catch (final fabric.worker.RetryException $e574) {
                            $commit572 = false;
                            continue $label571;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e574) {
                            $commit572 = false;
                            fabric.common.TransactionID $currentTid575 =
                              $tm576.getCurrentTid();
                            if ($e574.tid.isDescendantOf($currentTid575))
                                continue $label571;
                            if ($currentTid575.parent != null) {
                                $retry573 = false;
                                throw $e574;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e574) {
                            $commit572 = false;
                            if ($tm576.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid575 =
                              $tm576.getCurrentTid();
                            if ($e574.tid.isDescendantOf($currentTid575)) {
                                $retry573 = true;
                            }
                            else if ($currentTid575.parent != null) {
                                $retry573 = false;
                                throw $e574;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e574) {
                            $commit572 = false;
                            if ($tm576.checkForStaleObjects())
                                continue $label571;
                            $retry573 = false;
                            throw new fabric.worker.AbortException($e574);
                        }
                        finally {
                            if ($commit572) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e574) {
                                    $commit572 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e574) {
                                    $commit572 = false;
                                    fabric.common.TransactionID $currentTid575 =
                                      $tm576.getCurrentTid();
                                    if ($currentTid575 != null) {
                                        if ($e574.tid.equals($currentTid575) ||
                                              !$e574.tid.isDescendantOf(
                                                           $currentTid575)) {
                                            throw $e574;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit572 && $retry573) {
                                {
                                    expiry = expiry$var569;
                                    atLeastOnce = atLeastOnce$var570;
                                }
                                continue $label571;
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
                        fabric.worker.transaction.TransactionManager $tm585 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled588 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff586 = 1;
                        boolean $doBackoff587 = true;
                        boolean $retry582 = true;
                        $label580: for (boolean $commit581 = false; !$commit581;
                                        ) {
                            if ($backoffEnabled588) {
                                if ($doBackoff587) {
                                    if ($backoff586 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff586);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e583) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff586 < 5000) $backoff586 *= 2;
                                }
                                $doBackoff587 = $backoff586 <= 32 ||
                                                  !$doBackoff587;
                            }
                            $commit581 = true;
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
                            catch (final fabric.worker.RetryException $e583) {
                                $commit581 = false;
                                continue $label580;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e583) {
                                $commit581 = false;
                                fabric.common.TransactionID $currentTid584 =
                                  $tm585.getCurrentTid();
                                if ($e583.tid.isDescendantOf($currentTid584))
                                    continue $label580;
                                if ($currentTid584.parent != null) {
                                    $retry582 = false;
                                    throw $e583;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e583) {
                                $commit581 = false;
                                if ($tm585.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid584 =
                                  $tm585.getCurrentTid();
                                if ($e583.tid.isDescendantOf($currentTid584)) {
                                    $retry582 = true;
                                }
                                else if ($currentTid584.parent != null) {
                                    $retry582 = false;
                                    throw $e583;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e583) {
                                $commit581 = false;
                                if ($tm585.checkForStaleObjects())
                                    continue $label580;
                                $retry582 = false;
                                throw new fabric.worker.AbortException($e583);
                            }
                            finally {
                                if ($commit581) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e583) {
                                        $commit581 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e583) {
                                        $commit581 = false;
                                        fabric.common.TransactionID
                                          $currentTid584 =
                                          $tm585.getCurrentTid();
                                        if ($currentTid584 != null) {
                                            if ($e583.tid.equals(
                                                            $currentTid584) ||
                                                  !$e583.tid.
                                                  isDescendantOf(
                                                    $currentTid584)) {
                                                throw $e583;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit581 && $retry582) {
                                    {  }
                                    continue $label580;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -18, -21, 78, 47, 121,
    69, -93, -19, -108, 95, -22, -15, 15, -94, -99, -21, -99, 58, -117, -105,
    113, -91, 90, -4, -71, 85, 43, 108, 45, 85, -6, 105 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527629368000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
