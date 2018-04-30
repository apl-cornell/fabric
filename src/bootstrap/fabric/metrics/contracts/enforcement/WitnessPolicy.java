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
        
        public static final byte[] $classHash = new byte[] { 17, 14, 76, -50,
        -77, 4, 95, -79, 53, 88, -113, -85, 86, 44, 77, -77, 66, -53, -79, 2,
        81, -48, -99, 49, 3, -18, 79, 87, 50, 99, 95, -120 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525096282000L;
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
                    fabric.worker.transaction.TransactionManager $tm568 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled571 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff569 = 1;
                    boolean $doBackoff570 = true;
                    boolean $retry565 = true;
                    $label563: for (boolean $commit564 = false; !$commit564; ) {
                        if ($backoffEnabled571) {
                            if ($doBackoff570) {
                                if ($backoff569 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff569);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e566) {
                                            
                                        }
                                    }
                                }
                                if ($backoff569 < 5000) $backoff569 *= 2;
                            }
                            $doBackoff570 = $backoff569 <= 32 || !$doBackoff570;
                        }
                        $commit564 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
                        catch (final fabric.worker.RetryException $e566) {
                            $commit564 = false;
                            continue $label563;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e566) {
                            $commit564 = false;
                            fabric.common.TransactionID $currentTid567 =
                              $tm568.getCurrentTid();
                            if ($e566.tid.isDescendantOf($currentTid567))
                                continue $label563;
                            if ($currentTid567.parent != null) {
                                $retry565 = false;
                                throw $e566;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e566) {
                            $commit564 = false;
                            if ($tm568.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid567 =
                              $tm568.getCurrentTid();
                            if ($e566.tid.isDescendantOf($currentTid567)) {
                                $retry565 = true;
                            }
                            else if ($currentTid567.parent != null) {
                                $retry565 = false;
                                throw $e566;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e566) {
                            $commit564 = false;
                            if ($tm568.checkForStaleObjects())
                                continue $label563;
                            $retry565 = false;
                            throw new fabric.worker.AbortException($e566);
                        }
                        finally {
                            if ($commit564) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e566) {
                                    $commit564 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e566) {
                                    $commit564 = false;
                                    fabric.common.TransactionID $currentTid567 =
                                      $tm568.getCurrentTid();
                                    if ($currentTid567 != null) {
                                        if ($e566.tid.equals($currentTid567) ||
                                              !$e566.tid.isDescendantOf(
                                                           $currentTid567)) {
                                            throw $e566;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit564 && $retry565) {
                                {  }
                                continue $label563;
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
                        java.util.concurrent.Callable c$var572 = c;
                        int i$var573 = i;
                        fabric.worker.transaction.TransactionManager $tm579 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled582 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff580 = 1;
                        boolean $doBackoff581 = true;
                        boolean $retry576 = true;
                        $label574: for (boolean $commit575 = false; !$commit575;
                                        ) {
                            if ($backoffEnabled582) {
                                if ($doBackoff581) {
                                    if ($backoff580 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff580);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e577) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff580 < 5000) $backoff580 *= 2;
                                }
                                $doBackoff581 = $backoff580 <= 32 ||
                                                  !$doBackoff581;
                            }
                            $commit575 = true;
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
                            catch (final fabric.worker.RetryException $e577) {
                                $commit575 = false;
                                continue $label574;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e577) {
                                $commit575 = false;
                                fabric.common.TransactionID $currentTid578 =
                                  $tm579.getCurrentTid();
                                if ($e577.tid.isDescendantOf($currentTid578))
                                    continue $label574;
                                if ($currentTid578.parent != null) {
                                    $retry576 = false;
                                    throw $e577;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e577) {
                                $commit575 = false;
                                if ($tm579.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid578 =
                                  $tm579.getCurrentTid();
                                if ($e577.tid.isDescendantOf($currentTid578)) {
                                    $retry576 = true;
                                }
                                else if ($currentTid578.parent != null) {
                                    $retry576 = false;
                                    throw $e577;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e577) {
                                $commit575 = false;
                                if ($tm579.checkForStaleObjects())
                                    continue $label574;
                                $retry576 = false;
                                throw new fabric.worker.AbortException($e577);
                            }
                            finally {
                                if ($commit575) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e577) {
                                        $commit575 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e577) {
                                        $commit575 = false;
                                        fabric.common.TransactionID
                                          $currentTid578 =
                                          $tm579.getCurrentTid();
                                        if ($currentTid578 != null) {
                                            if ($e577.tid.equals(
                                                            $currentTid578) ||
                                                  !$e577.tid.
                                                  isDescendantOf(
                                                    $currentTid578)) {
                                                throw $e577;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit575 && $retry576) {
                                    {
                                        c = c$var572;
                                        i = i$var573;
                                    }
                                    continue $label574;
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
                    fabric.worker.transaction.TransactionManager $tm588 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled591 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff589 = 1;
                    boolean $doBackoff590 = true;
                    boolean $retry585 = true;
                    $label583: for (boolean $commit584 = false; !$commit584; ) {
                        if ($backoffEnabled591) {
                            if ($doBackoff590) {
                                if ($backoff589 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff589);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e586) {
                                            
                                        }
                                    }
                                }
                                if ($backoff589 < 5000) $backoff589 *= 2;
                            }
                            $doBackoff590 = $backoff589 <= 32 || !$doBackoff590;
                        }
                        $commit584 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.set$activated(true); }
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
                                    fabric.common.TransactionID $currentTid587 =
                                      $tm588.getCurrentTid();
                                    if ($currentTid587 != null) {
                                        if ($e586.tid.equals($currentTid587) ||
                                              !$e586.tid.isDescendantOf(
                                                           $currentTid587)) {
                                            throw $e586;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit584 && $retry585) {
                                {  }
                                continue $label583;
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
                    long expiry$var592 = expiry;
                    boolean atLeastOnce$var593 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm599 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled602 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff600 = 1;
                    boolean $doBackoff601 = true;
                    boolean $retry596 = true;
                    $label594: for (boolean $commit595 = false; !$commit595; ) {
                        if ($backoffEnabled602) {
                            if ($doBackoff601) {
                                if ($backoff600 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff600);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e597) {
                                            
                                        }
                                    }
                                }
                                if ($backoff600 < 5000) $backoff600 *= 2;
                            }
                            $doBackoff601 = $backoff600 <= 32 || !$doBackoff601;
                        }
                        $commit595 = true;
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
                        catch (final fabric.worker.RetryException $e597) {
                            $commit595 = false;
                            continue $label594;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e597) {
                            $commit595 = false;
                            fabric.common.TransactionID $currentTid598 =
                              $tm599.getCurrentTid();
                            if ($e597.tid.isDescendantOf($currentTid598))
                                continue $label594;
                            if ($currentTid598.parent != null) {
                                $retry596 = false;
                                throw $e597;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e597) {
                            $commit595 = false;
                            if ($tm599.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid598 =
                              $tm599.getCurrentTid();
                            if ($e597.tid.isDescendantOf($currentTid598)) {
                                $retry596 = true;
                            }
                            else if ($currentTid598.parent != null) {
                                $retry596 = false;
                                throw $e597;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e597) {
                            $commit595 = false;
                            if ($tm599.checkForStaleObjects())
                                continue $label594;
                            $retry596 = false;
                            throw new fabric.worker.AbortException($e597);
                        }
                        finally {
                            if ($commit595) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e597) {
                                    $commit595 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e597) {
                                    $commit595 = false;
                                    fabric.common.TransactionID $currentTid598 =
                                      $tm599.getCurrentTid();
                                    if ($currentTid598 != null) {
                                        if ($e597.tid.equals($currentTid598) ||
                                              !$e597.tid.isDescendantOf(
                                                           $currentTid598)) {
                                            throw $e597;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit595 && $retry596) {
                                {
                                    expiry = expiry$var592;
                                    atLeastOnce = atLeastOnce$var593;
                                }
                                continue $label594;
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
                        fabric.worker.transaction.TransactionManager $tm608 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled611 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff609 = 1;
                        boolean $doBackoff610 = true;
                        boolean $retry605 = true;
                        $label603: for (boolean $commit604 = false; !$commit604;
                                        ) {
                            if ($backoffEnabled611) {
                                if ($doBackoff610) {
                                    if ($backoff609 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff609);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e606) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff609 < 5000) $backoff609 *= 2;
                                }
                                $doBackoff610 = $backoff609 <= 32 ||
                                                  !$doBackoff610;
                            }
                            $commit604 = true;
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
                                        fabric.common.TransactionID
                                          $currentTid607 =
                                          $tm608.getCurrentTid();
                                        if ($currentTid607 != null) {
                                            if ($e606.tid.equals(
                                                            $currentTid607) ||
                                                  !$e606.tid.
                                                  isDescendantOf(
                                                    $currentTid607)) {
                                                throw $e606;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit604 && $retry605) {
                                    {  }
                                    continue $label603;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -20, -32, 63, 123, 74,
    99, 107, -7, -77, 36, -30, -49, 84, -87, 72, 19, 47, 97, 1, -104, 10, -44,
    -4, -79, -51, 60, 114, -51, 12, 74, -75, -69 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525096282000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
