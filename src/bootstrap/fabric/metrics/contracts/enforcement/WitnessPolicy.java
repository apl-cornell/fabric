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
        
        public static final byte[] $classHash = new byte[] { -115, -84, -72,
        -55, -57, -114, -18, 12, -100, 50, 107, 119, 44, 2, -95, -32, -73, 59,
        -99, -57, 125, -66, -28, 106, 41, -5, 61, -125, 51, -110, -31, 80 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527094903000L;
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
    
    public static final byte[] $classHash = new byte[] { -104, -39, 19, -78, 66,
    109, -39, -99, -119, 44, 81, -96, 15, 38, -10, -96, 31, 119, -39, 22, 19,
    68, -74, -93, -16, -126, -7, 25, 12, 56, 110, -10 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527094903000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAL1Zf3BURx3fu/y8EJKQEH4ESEK4olB6N4DDSKMdyQHl4GhiAmiDJb57t5e85t17x3t7yQWLg51xoO2YcSpQOlOwo9FqTcF2pujoZGTU2tIylaK18oct/1SpwBRkxFat+P3u7t29u7wcxOnIsPt9t2+/u9+fn/3uy/gVUmZbpC2uRDU9wEaS1A5sVKLhSJdi2TQW0hXb3gajfeqM0vDhi8/Emr3EGyHVqmKYhqYqep9hM1ITeVAZUoIGZcHt3eH2ncSnIuMmxR5gxLuzI22R1qSpj/TrJpObTFr/0J3Bg0/sqnuhhNT2klrN6GEK09SQaTCaZr2kOkETUWrZ62IxGuslswxKYz3U0hRd2wMTTaOX1Ntav6GwlEXtbmqb+hBOrLdTSWrxPTODKL4JYlsplZkWiF8nxE8xTQ9GNJu1R0h5XKN6zN5NvkpKI6Qsriv9MHFOJKNFkK8Y3IjjML1KAzGtuKLSDEvpoGbEGGkp5Mhq7N8CE4C1IkHZgJndqtRQYIDUC5F0xegP9jBLM/phapmZgl0YaZpyUZhUmVTUQaWf9jEyr3Bel3gFs3zcLMjCSGPhNL4S+KypwGcOb1257zOjXzE2GV7iAZljVNVR/kpgai5g6qZxalFDpYKxennksDJn4oCXEJjcWDBZzPnJQ9c+t6L51CtizgKXOZ3RB6nK+tSxaM0bC0PL1pagGJVJ09YwFPI0517tkm/a00mI9jnZFfFlIPPyVPdv7t/3LL3kJVVhUq6aeioBUTVLNRNJTafWvdSglsJoLEx81IiF+PswqYDniGZQMdoZj9uUhUmpzofKTf4bTBSHJdBEFfCsGXEz85xU2AB/TicJIRXQiAfabwlpigKdT0hJKyNKcMBM0GBUT9FhCO8gNKpY6kAQ8tbS1KBtqUErZTANJskhiCIgdhBCnVmKyuwghW0tlSaowYJf0JhBbbvL1DV1JADCJf8fm6RR07phjwec0KKaMRpVbPCojK6OLh0SaJOpx6jVp+qjE2HSMPEkjzAfZoUNkc1t6IGoWFiIJ07eg6mODdeO970mohN5pYkZWSUkD0jJA1nJAw7JA3mSg7DVmI0BwLcA4Nu4Jx0IHQv/iAdduc2zM7t+Nax/d1JXGCyWSBOPhys7m/PzaINYGQQMApipXtbzwOYvH2grgTBPDpei52GqvzDpclAVhicFMqlPrd1/8caJw3vNXPox4p+ECpM5MavbCi1nmSqNAWrmll/eqrzYN7HX70VE8qGJFAhnQJ7mwj3ysrs9g5RojbIImYE2UHR8lYG3KjZgmcO5ER4RNdjVi+BAYxUIyEH2sz3Jo398/b3V/PjJ4HGtA7h7KGt3YAAuVsuzfVbO9tssSmHen450fevQlf07ueFhxhK3Df3YhyD3FUh60/r6K7vPv/P22O+9OWcxUp5MRSFC0lyXWTfhnwfaf7BhIuMAUoDzkASR1iyKJHHnpTnZAE90wDQQ3fZvNxJmTItrSlSnGCn/rr1j5YuXR+uEu3UYEcazyIpbL5Abn99B9r226x/NfBmPiudZzn65aQIkG3Irr7MsZQTlSH/t3KInX1aOQuQDxNnaHspRi3B7EO7AVdwWd/F+ZcG7T2HXJqy1kI+X2pMPjI148uZisTc4/lRT6J5LAgeysYhrLHbBgR2KI01WPZv4u7et/CUvqegldfzQVwy2QwGEgzDohWPbDsnBCJmZ9z7/CBbnTXs21xYW5oFj28IsyOEPPONsfK4SgS8CBwxRi0ZaBW0hAP5pSb+PbxuS2M9Oewh/uJuzLOH9UuyWcUN68XE5Iz4tkUgxdDvf4E4YGRZYBv9xpJGRgITAYdMapFYWCcMZzlAGEndQXiAh2/xCZBPJiv2arBJVRAhOWsCzjZKWuCix3l0JyKiKpKUNQXqks4t6cVGfXMwraMm/HIuCiiAs54q5hFOXpSUAEYZk/UEPHHz0ZmD0oEglUaQtmVQnOXlEocYVnsltmoZdFhfbhXNs/MuJvT//wd79ooipzy85NhipxHN/+OhM4MiF0y6HVEXUNHWqGG5Gnov2WA7tUULqL0r6pouRu92NzP2xieHBhQU2/ronGytdnZ2Rvp5w7wbOtUWqi+Q+RkqgyJ1SovuhPUZIQ4ekxEWincUkwm5HnjQVNrWGNFlSQ9z6c4gEqaqmLAuP6g1pqqYgTHsckyFafRitugk3FBGq6SlCjudNLtr4v3JZeLVIOt+hiwO8PBnB1txWTbEh9yzqCuRuwmBaNFWdzQNp7OGDx2Kd31vplSjaCcoxM3mXToeo7hCnCsNy0j1uK79d5CDxwqVFa0OD7/aLsGwp2Llw9g+3jp++d6n6uJeUZLFv0pUmn6k9H/GqLAo3MmNbHu61Zu2NiU36oX2CkDIqaYUzdnIRtwQ7mg8MlZKlXFJS6KrcSeTJllgLnFbaDEHFDziBD7ugXDg78v5hYZ/CG5Bj4tXxdy6dm7noOK+RSrGQ5foVXh0n3wzzLnxcveqsTqgGqQHdYPuN6yRdy8iW/700Xw+XZLj0buU/ZaX/cS6XzmTB4imzIHOg8ICf5A8OJtjtyeDEvuJnXFlcM2RaA0qU69ToF3eoL2I3ks7u4BVsGQFFRYPnOWSYaVA86/BVNDNBVOmaGch+WMjMSLtKbQip+a6OCOWCFSmDRou8+yZ2j4GOKkqYEawuJ7moRIRQnGOoyGqHsNvNyGrhGr90jT/rGr8DoPx5lx5/Lu+M/GzFsycIobpG0sbpZSuyzJa05tbZKi3Q7F6z4Ecie6uSdK9RuDRPFbHPd7B7gpHKTAHhduyVDplarMAQ/KiIQFsNWvxY0tQUhnCNY3HeFZQ5dXIlJml8avt4c0vVYXeU7/hcEWVPYPcMXJrEwd+X0RmHx9wc/Uloo3CUPyTpA9NzNLJ8SdIdt3R0ToeTRXT4KXbPQ9LDxUOzRlzdBYnT7+auzdAeB1k+lPTUx+IuXOkXkj4/XXf9qoiqL2E3wchM6S6hMQ7+zM1ZrdDGCZnzV0l/Nz1nIcs5Sc/clrP28lXPFNHgdexeBixTkkl9ZMowa4N2kpB5PkHnXp2e5MjyvqTvTUPyN4tI/hZ2Z6ECTRnFZW+CNgGyf1vS0enJjizfkHT/1LI7RXu7yLsL2J0HOGOm+Gbscn44XkzCSzcN74D2SyiDawWdd316GiLL3yS9fEvvZOStl2jvOPGKIPylIia5ht2fETB2pxRRLW91U9MP7VVQ87Kkb0xPTWQ5K+mrt+fID4q8+yd216EgUdTdKc2i3RRO7LjWHzHVQc4wlgZgyDuv8ZPNApdPqvKPAWro13Ts3S0rGqf4nDpv0p9nJN/xY7WVc49tf0uUuZkP/b4IqYyndN35acPxXJ60aFzjqvjEh44kJzfhEnc7dyVGZjh+cY0/4it4vIzMm2oFJj4P8WcnTxkjNfk8jBfh+OScVwlhIubhLx/3YJNLd5nr0pSy8E9b49fnflBeue0C/y6IOHzkfMMLHYnzRx9Z8fmna5feeLpl+PychvUnv3v14Q/nV3/auPFfPS0AqXIbAAA=";
}
