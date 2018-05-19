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
                      refresh$remote(worker, null, false);
                }
                else {
                    this.get$w().refresh(false);
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
        
        public static final byte[] $classHash = new byte[] { -80, 105, 14, 0,
        -49, -124, 31, 54, 6, 115, -20, 58, -52, 118, 112, 44, 0, 2, -95, -81,
        -108, -70, -124, -91, 5, -122, 117, -23, 84, 42, -92, -37 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1526753800000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public long expiry();
    
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
                    tmp.get$witnesses().get(i).refresh(false);
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
                                            w));
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
        
        public long expiry() {
            return fabric.metrics.contracts.enforcement.WitnessPolicy._Impl.
              static_expiry((fabric.metrics.contracts.enforcement.WitnessPolicy)
                              this.$getProxy());
        }
        
        private static long static_expiry(
          fabric.metrics.contracts.enforcement.WitnessPolicy tmp) {
            if (!tmp.get$activated()) tmp.activate();
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
            if (!this.get$activated()) activate();
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
    
    public static final byte[] $classHash = new byte[] { 113, -47, 18, -76, -11,
    1, 74, -122, -97, 118, 42, 43, -12, 46, 35, 56, 33, 29, -128, 40, -31, 10,
    116, -91, 114, -42, -34, -73, -9, 70, -40, -19 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526753800000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
