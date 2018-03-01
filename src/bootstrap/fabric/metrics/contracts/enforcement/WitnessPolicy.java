package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.metrics.contracts.Contract;
import fabric.worker.transaction.TransactionManager;
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
    public fabric.lang.arrays.ObjectArray get$witnesses();
    
    public fabric.lang.arrays.ObjectArray set$witnesses(
      fabric.lang.arrays.ObjectArray val);
    
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
                      activate$remote(worker, null);
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
        
        public static final byte[] $classHash = new byte[] { 100, 75, -109,
        -118, 36, -7, 125, -95, -49, -109, 8, -40, -62, -77, -19, 9, 0, 44, 20,
        -94, 28, 4, 38, -113, -94, -95, 78, -117, 87, 19, -51, 14 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1519623915000L;
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
   *        the array of {@link Contract}s used to enforce this
   *        policy. If any of the witnesses weren't already active, they
   *        are {@link Contract#activate() activated} here.
   */
        public fabric.metrics.contracts.enforcement.WitnessPolicy
          fabric$metrics$contracts$enforcement$WitnessPolicy$(
          fabric.metrics.contracts.Contract[] witnesses) {
            fabric$lang$Object$();
            this.
              set$witnesses(
                (fabric.lang.arrays.ObjectArray)
                  new fabric.lang.arrays.ObjectArray._Impl(this.$getStore()).
                  fabric$lang$arrays$ObjectArray$(
                    this.get$$updateLabel(),
                    this.get$$updateLabel().
                        confPolicy(),
                    fabric.metrics.contracts.Contract._Proxy.class,
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
                    fabric.worker.transaction.TransactionManager $tm529 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled532 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff530 = 1;
                    boolean $doBackoff531 = true;
                    boolean $retry526 = true;
                    $label524: for (boolean $commit525 = false; !$commit525; ) {
                        if ($backoffEnabled532) {
                            if ($doBackoff531) {
                                if ($backoff530 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff530);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e527) {
                                            
                                        }
                                    }
                                }
                                if ($backoff530 < 5000) $backoff530 *= 2;
                            }
                            $doBackoff531 = $backoff530 <= 32 || !$doBackoff531;
                        }
                        $commit525 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
                        catch (final fabric.worker.RetryException $e527) {
                            $commit525 = false;
                            continue $label524;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e527) {
                            $commit525 = false;
                            fabric.common.TransactionID $currentTid528 =
                              $tm529.getCurrentTid();
                            if ($e527.tid.isDescendantOf($currentTid528))
                                continue $label524;
                            if ($currentTid528.parent != null) {
                                $retry526 = false;
                                throw $e527;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e527) {
                            $commit525 = false;
                            if ($tm529.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid528 =
                              $tm529.getCurrentTid();
                            if ($e527.tid.isDescendantOf($currentTid528)) {
                                $retry526 = true;
                            }
                            else if ($currentTid528.parent != null) {
                                $retry526 = false;
                                throw $e527;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e527) {
                            $commit525 = false;
                            if ($tm529.checkForStaleObjects())
                                continue $label524;
                            $retry526 = false;
                            throw new fabric.worker.AbortException($e527);
                        }
                        finally {
                            if ($commit525) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e527) {
                                    $commit525 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e527) {
                                    $commit525 = false;
                                    fabric.common.TransactionID $currentTid528 =
                                      $tm529.getCurrentTid();
                                    if ($currentTid528 != null) {
                                        if ($e527.tid.equals($currentTid528) ||
                                              !$e527.tid.isDescendantOf(
                                                           $currentTid528)) {
                                            throw $e527;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit525 && $retry526) {
                                {  }
                                continue $label524;
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
                    ((fabric.metrics.contracts.Contract)
                       tmp.get$witnesses().get(i)).activate();
                }
            }
            else {
                java.util.concurrent.Future[] futures =
                  new java.util.concurrent.Future[tmp.get$witnesses(
                                                        ).get$length()];
                for (int i = 0; i < futures.length; i++) {
                    final fabric.metrics.contracts.Contract w =
                      (fabric.metrics.contracts.Contract)
                        tmp.get$witnesses().get(i);
                    java.util.concurrent.Callable c = null;
                    {
                        java.util.concurrent.Callable c$var533 = c;
                        int i$var534 = i;
                        fabric.worker.transaction.TransactionManager $tm540 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled543 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff541 = 1;
                        boolean $doBackoff542 = true;
                        boolean $retry537 = true;
                        $label535: for (boolean $commit536 = false; !$commit536;
                                        ) {
                            if ($backoffEnabled543) {
                                if ($doBackoff542) {
                                    if ($backoff541 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff541);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e538) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff541 < 5000) $backoff541 *= 2;
                                }
                                $doBackoff542 = $backoff541 <= 32 ||
                                                  !$doBackoff542;
                            }
                            $commit536 = true;
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
                            catch (final fabric.worker.RetryException $e538) {
                                $commit536 = false;
                                continue $label535;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e538) {
                                $commit536 = false;
                                fabric.common.TransactionID $currentTid539 =
                                  $tm540.getCurrentTid();
                                if ($e538.tid.isDescendantOf($currentTid539))
                                    continue $label535;
                                if ($currentTid539.parent != null) {
                                    $retry537 = false;
                                    throw $e538;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e538) {
                                $commit536 = false;
                                if ($tm540.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid539 =
                                  $tm540.getCurrentTid();
                                if ($e538.tid.isDescendantOf($currentTid539)) {
                                    $retry537 = true;
                                }
                                else if ($currentTid539.parent != null) {
                                    $retry537 = false;
                                    throw $e538;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e538) {
                                $commit536 = false;
                                if ($tm540.checkForStaleObjects())
                                    continue $label535;
                                $retry537 = false;
                                throw new fabric.worker.AbortException($e538);
                            }
                            finally {
                                if ($commit536) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e538) {
                                        $commit536 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e538) {
                                        $commit536 = false;
                                        fabric.common.TransactionID
                                          $currentTid539 =
                                          $tm540.getCurrentTid();
                                        if ($currentTid539 != null) {
                                            if ($e538.tid.equals(
                                                            $currentTid539) ||
                                                  !$e538.tid.
                                                  isDescendantOf(
                                                    $currentTid539)) {
                                                throw $e538;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit536 && $retry537) {
                                    {
                                        c = c$var533;
                                        i = i$var534;
                                    }
                                    continue $label535;
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
                    fabric.worker.transaction.TransactionManager $tm549 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled552 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff550 = 1;
                    boolean $doBackoff551 = true;
                    boolean $retry546 = true;
                    $label544: for (boolean $commit545 = false; !$commit545; ) {
                        if ($backoffEnabled552) {
                            if ($doBackoff551) {
                                if ($backoff550 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff550);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e547) {
                                            
                                        }
                                    }
                                }
                                if ($backoff550 < 5000) $backoff550 *= 2;
                            }
                            $doBackoff551 = $backoff550 <= 32 || !$doBackoff551;
                        }
                        $commit545 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.set$activated(true); }
                        catch (final fabric.worker.RetryException $e547) {
                            $commit545 = false;
                            continue $label544;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e547) {
                            $commit545 = false;
                            fabric.common.TransactionID $currentTid548 =
                              $tm549.getCurrentTid();
                            if ($e547.tid.isDescendantOf($currentTid548))
                                continue $label544;
                            if ($currentTid548.parent != null) {
                                $retry546 = false;
                                throw $e547;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e547) {
                            $commit545 = false;
                            if ($tm549.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid548 =
                              $tm549.getCurrentTid();
                            if ($e547.tid.isDescendantOf($currentTid548)) {
                                $retry546 = true;
                            }
                            else if ($currentTid548.parent != null) {
                                $retry546 = false;
                                throw $e547;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e547) {
                            $commit545 = false;
                            if ($tm549.checkForStaleObjects())
                                continue $label544;
                            $retry546 = false;
                            throw new fabric.worker.AbortException($e547);
                        }
                        finally {
                            if ($commit545) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e547) {
                                    $commit545 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e547) {
                                    $commit545 = false;
                                    fabric.common.TransactionID $currentTid548 =
                                      $tm549.getCurrentTid();
                                    if ($currentTid548 != null) {
                                        if ($e547.tid.equals($currentTid548) ||
                                              !$e547.tid.isDescendantOf(
                                                           $currentTid548)) {
                                            throw $e547;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit545 && $retry546) {
                                {  }
                                continue $label544;
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
                          ((fabric.metrics.contracts.Contract)
                             tmp.get$witnesses().get(i)).getExpiry() < expiry) {
                        atLeastOnce = true;
                        expiry = ((fabric.metrics.contracts.Contract)
                                    tmp.get$witnesses().get(i)).getExpiry();
                    }
                }
            }
            else {
                {
                    long expiry$var553 = expiry;
                    boolean atLeastOnce$var554 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm560 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled563 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff561 = 1;
                    boolean $doBackoff562 = true;
                    boolean $retry557 = true;
                    $label555: for (boolean $commit556 = false; !$commit556; ) {
                        if ($backoffEnabled563) {
                            if ($doBackoff562) {
                                if ($backoff561 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff561);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e558) {
                                            
                                        }
                                    }
                                }
                                if ($backoff561 < 5000) $backoff561 *= 2;
                            }
                            $doBackoff562 = $backoff561 <= 32 || !$doBackoff562;
                        }
                        $commit556 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            for (int i = 0;
                                 i < tmp.get$witnesses().get$length(); i++) {
                                if (!atLeastOnce ||
                                      ((fabric.metrics.contracts.Contract)
                                         tmp.get$witnesses().get(i)).getExpiry(
                                                                       ) <
                                      expiry) {
                                    atLeastOnce = true;
                                    expiry =
                                      ((fabric.metrics.contracts.Contract)
                                         tmp.get$witnesses().get(i)).getExpiry(
                                                                       );
                                }
                            }
                        }
                        catch (final fabric.worker.RetryException $e558) {
                            $commit556 = false;
                            continue $label555;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e558) {
                            $commit556 = false;
                            fabric.common.TransactionID $currentTid559 =
                              $tm560.getCurrentTid();
                            if ($e558.tid.isDescendantOf($currentTid559))
                                continue $label555;
                            if ($currentTid559.parent != null) {
                                $retry557 = false;
                                throw $e558;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e558) {
                            $commit556 = false;
                            if ($tm560.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid559 =
                              $tm560.getCurrentTid();
                            if ($e558.tid.isDescendantOf($currentTid559)) {
                                $retry557 = true;
                            }
                            else if ($currentTid559.parent != null) {
                                $retry557 = false;
                                throw $e558;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e558) {
                            $commit556 = false;
                            if ($tm560.checkForStaleObjects())
                                continue $label555;
                            $retry557 = false;
                            throw new fabric.worker.AbortException($e558);
                        }
                        finally {
                            if ($commit556) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e558) {
                                    $commit556 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e558) {
                                    $commit556 = false;
                                    fabric.common.TransactionID $currentTid559 =
                                      $tm560.getCurrentTid();
                                    if ($currentTid559 != null) {
                                        if ($e558.tid.equals($currentTid559) ||
                                              !$e558.tid.isDescendantOf(
                                                           $currentTid559)) {
                                            throw $e558;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit556 && $retry557) {
                                {
                                    expiry = expiry$var553;
                                    atLeastOnce = atLeastOnce$var554;
                                }
                                continue $label555;
                            }
                        }
                    }
                }
            }
            return expiry;
        }
        
        public void apply(fabric.metrics.contracts.Contract mc) {
            if (!this.get$activated()) activate();
            for (int i = 0; i < this.get$witnesses().get$length(); i++) {
                ((fabric.metrics.contracts.Contract)
                   this.get$witnesses().get(i)).
                  addObserver(
                    (fabric.metrics.util.Observer)
                      fabric.lang.Object._Proxy.$getProxy(mc.fetch()));
            }
        }
        
        public void unapply(fabric.metrics.contracts.Contract mc) {
            for (int i = 0; i < this.get$witnesses().get$length(); i++) {
                ((fabric.metrics.contracts.Contract)
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
                ((fabric.metrics.contracts.Contract)
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
                        fabric.worker.transaction.TransactionManager $tm569 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled572 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff570 = 1;
                        boolean $doBackoff571 = true;
                        boolean $retry566 = true;
                        $label564: for (boolean $commit565 = false; !$commit565;
                                        ) {
                            if ($backoffEnabled572) {
                                if ($doBackoff571) {
                                    if ($backoff570 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff570);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e567) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff570 < 5000) $backoff570 *= 2;
                                }
                                $doBackoff571 = $backoff570 <= 32 ||
                                                  !$doBackoff571;
                            }
                            $commit565 = true;
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
                            catch (final fabric.worker.RetryException $e567) {
                                $commit565 = false;
                                continue $label564;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e567) {
                                $commit565 = false;
                                fabric.common.TransactionID $currentTid568 =
                                  $tm569.getCurrentTid();
                                if ($e567.tid.isDescendantOf($currentTid568))
                                    continue $label564;
                                if ($currentTid568.parent != null) {
                                    $retry566 = false;
                                    throw $e567;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e567) {
                                $commit565 = false;
                                if ($tm569.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid568 =
                                  $tm569.getCurrentTid();
                                if ($e567.tid.isDescendantOf($currentTid568)) {
                                    $retry566 = true;
                                }
                                else if ($currentTid568.parent != null) {
                                    $retry566 = false;
                                    throw $e567;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e567) {
                                $commit565 = false;
                                if ($tm569.checkForStaleObjects())
                                    continue $label564;
                                $retry566 = false;
                                throw new fabric.worker.AbortException($e567);
                            }
                            finally {
                                if ($commit565) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e567) {
                                        $commit565 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e567) {
                                        $commit565 = false;
                                        fabric.common.TransactionID
                                          $currentTid568 =
                                          $tm569.getCurrentTid();
                                        if ($currentTid568 != null) {
                                            if ($e567.tid.equals(
                                                            $currentTid568) ||
                                                  !$e567.tid.
                                                  isDescendantOf(
                                                    $currentTid568)) {
                                                throw $e567;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit565 && $retry566) {
                                    {  }
                                    continue $label564;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 114, 2, -105, 89, 38,
    66, -19, 0, -99, 86, 4, 118, 19, 70, -38, -46, 99, 19, -73, 54, 28, 7, 7,
    40, -56, -39, 65, 71, 74, -103, -52, 2 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519623915000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
