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
        
        public static final byte[] $classHash = new byte[] { -101, 91, 12, -71,
        76, 105, 62, -107, -85, -100, -99, 14, 68, -52, 110, -72, 56, -45, -77,
        -42, 33, 124, 34, 26, -5, -127, -36, -51, -84, -56, -6, 0 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1526344812000L;
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
                    fabric.worker.transaction.TransactionManager $tm577 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled580 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff578 = 1;
                    boolean $doBackoff579 = true;
                    boolean $retry574 = true;
                    $label572: for (boolean $commit573 = false; !$commit573; ) {
                        if ($backoffEnabled580) {
                            if ($doBackoff579) {
                                if ($backoff578 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff578);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e575) {
                                            
                                        }
                                    }
                                }
                                if ($backoff578 < 5000) $backoff578 *= 2;
                            }
                            $doBackoff579 = $backoff578 <= 32 || !$doBackoff579;
                        }
                        $commit573 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
                        catch (final fabric.worker.RetryException $e575) {
                            $commit573 = false;
                            continue $label572;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e575) {
                            $commit573 = false;
                            fabric.common.TransactionID $currentTid576 =
                              $tm577.getCurrentTid();
                            if ($e575.tid.isDescendantOf($currentTid576))
                                continue $label572;
                            if ($currentTid576.parent != null) {
                                $retry574 = false;
                                throw $e575;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e575) {
                            $commit573 = false;
                            if ($tm577.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid576 =
                              $tm577.getCurrentTid();
                            if ($e575.tid.isDescendantOf($currentTid576)) {
                                $retry574 = true;
                            }
                            else if ($currentTid576.parent != null) {
                                $retry574 = false;
                                throw $e575;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e575) {
                            $commit573 = false;
                            if ($tm577.checkForStaleObjects())
                                continue $label572;
                            $retry574 = false;
                            throw new fabric.worker.AbortException($e575);
                        }
                        finally {
                            if ($commit573) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e575) {
                                    $commit573 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e575) {
                                    $commit573 = false;
                                    fabric.common.TransactionID $currentTid576 =
                                      $tm577.getCurrentTid();
                                    if ($currentTid576 != null) {
                                        if ($e575.tid.equals($currentTid576) ||
                                              !$e575.tid.isDescendantOf(
                                                           $currentTid576)) {
                                            throw $e575;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit573 && $retry574) {
                                {  }
                                continue $label572;
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
                        java.util.concurrent.Callable c$var581 = c;
                        int i$var582 = i;
                        fabric.worker.transaction.TransactionManager $tm588 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled591 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff589 = 1;
                        boolean $doBackoff590 = true;
                        boolean $retry585 = true;
                        $label583: for (boolean $commit584 = false; !$commit584;
                                        ) {
                            if ($backoffEnabled591) {
                                if ($doBackoff590) {
                                    if ($backoff589 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff589);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e586) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff589 < 5000) $backoff589 *= 2;
                                }
                                $doBackoff590 = $backoff589 <= 32 ||
                                                  !$doBackoff590;
                            }
                            $commit584 = true;
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
                            catch (final fabric.worker.RetryException $e586) {
                                $commit584 = false;
                                continue $label583;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e586) {
                                $commit584 = false;
                                fabric.common.TransactionID $currentTid587 =
                                  $tm588.getCurrentTid();
                                if ($e586.tid.isDescendantOf($currentTid587))
                                    continue $label583;
                                if ($currentTid587.parent != null) {
                                    $retry585 = false;
                                    throw $e586;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e586) {
                                $commit584 = false;
                                if ($tm588.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid587 =
                                  $tm588.getCurrentTid();
                                if ($e586.tid.isDescendantOf($currentTid587)) {
                                    $retry585 = true;
                                }
                                else if ($currentTid587.parent != null) {
                                    $retry585 = false;
                                    throw $e586;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e586) {
                                $commit584 = false;
                                if ($tm588.checkForStaleObjects())
                                    continue $label583;
                                $retry585 = false;
                                throw new fabric.worker.AbortException($e586);
                            }
                            finally {
                                if ($commit584) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e586) {
                                        $commit584 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e586) {
                                        $commit584 = false;
                                        fabric.common.TransactionID
                                          $currentTid587 =
                                          $tm588.getCurrentTid();
                                        if ($currentTid587 != null) {
                                            if ($e586.tid.equals(
                                                            $currentTid587) ||
                                                  !$e586.tid.
                                                  isDescendantOf(
                                                    $currentTid587)) {
                                                throw $e586;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit584 && $retry585) {
                                    {
                                        c = c$var581;
                                        i = i$var582;
                                    }
                                    continue $label583;
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
                    fabric.worker.transaction.TransactionManager $tm597 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled600 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff598 = 1;
                    boolean $doBackoff599 = true;
                    boolean $retry594 = true;
                    $label592: for (boolean $commit593 = false; !$commit593; ) {
                        if ($backoffEnabled600) {
                            if ($doBackoff599) {
                                if ($backoff598 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff598);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e595) {
                                            
                                        }
                                    }
                                }
                                if ($backoff598 < 5000) $backoff598 *= 2;
                            }
                            $doBackoff599 = $backoff598 <= 32 || !$doBackoff599;
                        }
                        $commit593 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.set$activated(true); }
                        catch (final fabric.worker.RetryException $e595) {
                            $commit593 = false;
                            continue $label592;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e595) {
                            $commit593 = false;
                            fabric.common.TransactionID $currentTid596 =
                              $tm597.getCurrentTid();
                            if ($e595.tid.isDescendantOf($currentTid596))
                                continue $label592;
                            if ($currentTid596.parent != null) {
                                $retry594 = false;
                                throw $e595;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e595) {
                            $commit593 = false;
                            if ($tm597.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid596 =
                              $tm597.getCurrentTid();
                            if ($e595.tid.isDescendantOf($currentTid596)) {
                                $retry594 = true;
                            }
                            else if ($currentTid596.parent != null) {
                                $retry594 = false;
                                throw $e595;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e595) {
                            $commit593 = false;
                            if ($tm597.checkForStaleObjects())
                                continue $label592;
                            $retry594 = false;
                            throw new fabric.worker.AbortException($e595);
                        }
                        finally {
                            if ($commit593) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e595) {
                                    $commit593 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e595) {
                                    $commit593 = false;
                                    fabric.common.TransactionID $currentTid596 =
                                      $tm597.getCurrentTid();
                                    if ($currentTid596 != null) {
                                        if ($e595.tid.equals($currentTid596) ||
                                              !$e595.tid.isDescendantOf(
                                                           $currentTid596)) {
                                            throw $e595;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit593 && $retry594) {
                                {  }
                                continue $label592;
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
                    long expiry$var601 = expiry;
                    boolean atLeastOnce$var602 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm608 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled611 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff609 = 1;
                    boolean $doBackoff610 = true;
                    boolean $retry605 = true;
                    $label603: for (boolean $commit604 = false; !$commit604; ) {
                        if ($backoffEnabled611) {
                            if ($doBackoff610) {
                                if ($backoff609 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff609);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e606) {
                                            
                                        }
                                    }
                                }
                                if ($backoff609 < 5000) $backoff609 *= 2;
                            }
                            $doBackoff610 = $backoff609 <= 32 || !$doBackoff610;
                        }
                        $commit604 = true;
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
                        catch (final fabric.worker.RetryException $e606) {
                            $commit604 = false;
                            continue $label603;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e606) {
                            $commit604 = false;
                            fabric.common.TransactionID $currentTid607 =
                              $tm608.getCurrentTid();
                            if ($e606.tid.isDescendantOf($currentTid607))
                                continue $label603;
                            if ($currentTid607.parent != null) {
                                $retry605 = false;
                                throw $e606;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e606) {
                            $commit604 = false;
                            if ($tm608.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid607 =
                              $tm608.getCurrentTid();
                            if ($e606.tid.isDescendantOf($currentTid607)) {
                                $retry605 = true;
                            }
                            else if ($currentTid607.parent != null) {
                                $retry605 = false;
                                throw $e606;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e606) {
                            $commit604 = false;
                            if ($tm608.checkForStaleObjects())
                                continue $label603;
                            $retry605 = false;
                            throw new fabric.worker.AbortException($e606);
                        }
                        finally {
                            if ($commit604) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e606) {
                                    $commit604 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e606) {
                                    $commit604 = false;
                                    fabric.common.TransactionID $currentTid607 =
                                      $tm608.getCurrentTid();
                                    if ($currentTid607 != null) {
                                        if ($e606.tid.equals($currentTid607) ||
                                              !$e606.tid.isDescendantOf(
                                                           $currentTid607)) {
                                            throw $e606;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit604 && $retry605) {
                                {
                                    expiry = expiry$var601;
                                    atLeastOnce = atLeastOnce$var602;
                                }
                                continue $label603;
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
                        fabric.worker.transaction.TransactionManager $tm617 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled620 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff618 = 1;
                        boolean $doBackoff619 = true;
                        boolean $retry614 = true;
                        $label612: for (boolean $commit613 = false; !$commit613;
                                        ) {
                            if ($backoffEnabled620) {
                                if ($doBackoff619) {
                                    if ($backoff618 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff618);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e615) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff618 < 5000) $backoff618 *= 2;
                                }
                                $doBackoff619 = $backoff618 <= 32 ||
                                                  !$doBackoff619;
                            }
                            $commit613 = true;
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
                            catch (final fabric.worker.RetryException $e615) {
                                $commit613 = false;
                                continue $label612;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e615) {
                                $commit613 = false;
                                fabric.common.TransactionID $currentTid616 =
                                  $tm617.getCurrentTid();
                                if ($e615.tid.isDescendantOf($currentTid616))
                                    continue $label612;
                                if ($currentTid616.parent != null) {
                                    $retry614 = false;
                                    throw $e615;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e615) {
                                $commit613 = false;
                                if ($tm617.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid616 =
                                  $tm617.getCurrentTid();
                                if ($e615.tid.isDescendantOf($currentTid616)) {
                                    $retry614 = true;
                                }
                                else if ($currentTid616.parent != null) {
                                    $retry614 = false;
                                    throw $e615;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e615) {
                                $commit613 = false;
                                if ($tm617.checkForStaleObjects())
                                    continue $label612;
                                $retry614 = false;
                                throw new fabric.worker.AbortException($e615);
                            }
                            finally {
                                if ($commit613) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e615) {
                                        $commit613 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e615) {
                                        $commit613 = false;
                                        fabric.common.TransactionID
                                          $currentTid616 =
                                          $tm617.getCurrentTid();
                                        if ($currentTid616 != null) {
                                            if ($e615.tid.equals(
                                                            $currentTid616) ||
                                                  !$e615.tid.
                                                  isDescendantOf(
                                                    $currentTid616)) {
                                                throw $e615;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit613 && $retry614) {
                                    {  }
                                    continue $label612;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -69, 14, 126, -105,
    108, 124, -8, -17, 4, 21, 62, -63, 100, 81, -104, -6, 78, -23, 113, -116,
    -98, 56, -15, 58, 26, 81, 101, -29, -13, 13, -13, 25 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526344812000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
