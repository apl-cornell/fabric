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
        
        public static final byte[] $classHash = new byte[] { -64, 49, 46, 95,
        -67, 70, -98, 25, -35, 56, -101, -93, -45, 27, -107, 109, 99, -60, -22,
        -35, 91, 27, -82, 32, -92, 122, -47, 110, -106, -103, 82, 91 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1524349466000L;
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
                    fabric.worker.transaction.TransactionManager $tm644 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled647 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff645 = 1;
                    boolean $doBackoff646 = true;
                    boolean $retry641 = true;
                    $label639: for (boolean $commit640 = false; !$commit640; ) {
                        if ($backoffEnabled647) {
                            if ($doBackoff646) {
                                if ($backoff645 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff645);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e642) {
                                            
                                        }
                                    }
                                }
                                if ($backoff645 < 5000) $backoff645 *= 2;
                            }
                            $doBackoff646 = $backoff645 <= 32 || !$doBackoff646;
                        }
                        $commit640 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
                        catch (final fabric.worker.RetryException $e642) {
                            $commit640 = false;
                            continue $label639;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e642) {
                            $commit640 = false;
                            fabric.common.TransactionID $currentTid643 =
                              $tm644.getCurrentTid();
                            if ($e642.tid.isDescendantOf($currentTid643))
                                continue $label639;
                            if ($currentTid643.parent != null) {
                                $retry641 = false;
                                throw $e642;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e642) {
                            $commit640 = false;
                            if ($tm644.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid643 =
                              $tm644.getCurrentTid();
                            if ($e642.tid.isDescendantOf($currentTid643)) {
                                $retry641 = true;
                            }
                            else if ($currentTid643.parent != null) {
                                $retry641 = false;
                                throw $e642;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e642) {
                            $commit640 = false;
                            if ($tm644.checkForStaleObjects())
                                continue $label639;
                            $retry641 = false;
                            throw new fabric.worker.AbortException($e642);
                        }
                        finally {
                            if ($commit640) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e642) {
                                    $commit640 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e642) {
                                    $commit640 = false;
                                    fabric.common.TransactionID $currentTid643 =
                                      $tm644.getCurrentTid();
                                    if ($currentTid643 != null) {
                                        if ($e642.tid.equals($currentTid643) ||
                                              !$e642.tid.isDescendantOf(
                                                           $currentTid643)) {
                                            throw $e642;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit640 && $retry641) {
                                {  }
                                continue $label639;
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
                    tmp.get$witnesses().get(i).activate();
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
                        java.util.concurrent.Callable c$var648 = c;
                        int i$var649 = i;
                        fabric.worker.transaction.TransactionManager $tm655 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled658 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff656 = 1;
                        boolean $doBackoff657 = true;
                        boolean $retry652 = true;
                        $label650: for (boolean $commit651 = false; !$commit651;
                                        ) {
                            if ($backoffEnabled658) {
                                if ($doBackoff657) {
                                    if ($backoff656 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff656);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e653) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff656 < 5000) $backoff656 *= 2;
                                }
                                $doBackoff657 = $backoff656 <= 32 ||
                                                  !$doBackoff657;
                            }
                            $commit651 = true;
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
                            catch (final fabric.worker.RetryException $e653) {
                                $commit651 = false;
                                continue $label650;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e653) {
                                $commit651 = false;
                                fabric.common.TransactionID $currentTid654 =
                                  $tm655.getCurrentTid();
                                if ($e653.tid.isDescendantOf($currentTid654))
                                    continue $label650;
                                if ($currentTid654.parent != null) {
                                    $retry652 = false;
                                    throw $e653;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e653) {
                                $commit651 = false;
                                if ($tm655.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid654 =
                                  $tm655.getCurrentTid();
                                if ($e653.tid.isDescendantOf($currentTid654)) {
                                    $retry652 = true;
                                }
                                else if ($currentTid654.parent != null) {
                                    $retry652 = false;
                                    throw $e653;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e653) {
                                $commit651 = false;
                                if ($tm655.checkForStaleObjects())
                                    continue $label650;
                                $retry652 = false;
                                throw new fabric.worker.AbortException($e653);
                            }
                            finally {
                                if ($commit651) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e653) {
                                        $commit651 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e653) {
                                        $commit651 = false;
                                        fabric.common.TransactionID
                                          $currentTid654 =
                                          $tm655.getCurrentTid();
                                        if ($currentTid654 != null) {
                                            if ($e653.tid.equals(
                                                            $currentTid654) ||
                                                  !$e653.tid.
                                                  isDescendantOf(
                                                    $currentTid654)) {
                                                throw $e653;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit651 && $retry652) {
                                    {
                                        c = c$var648;
                                        i = i$var649;
                                    }
                                    continue $label650;
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
                    fabric.worker.transaction.TransactionManager $tm664 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled667 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff665 = 1;
                    boolean $doBackoff666 = true;
                    boolean $retry661 = true;
                    $label659: for (boolean $commit660 = false; !$commit660; ) {
                        if ($backoffEnabled667) {
                            if ($doBackoff666) {
                                if ($backoff665 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff665);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e662) {
                                            
                                        }
                                    }
                                }
                                if ($backoff665 < 5000) $backoff665 *= 2;
                            }
                            $doBackoff666 = $backoff665 <= 32 || !$doBackoff666;
                        }
                        $commit660 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.set$activated(true); }
                        catch (final fabric.worker.RetryException $e662) {
                            $commit660 = false;
                            continue $label659;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e662) {
                            $commit660 = false;
                            fabric.common.TransactionID $currentTid663 =
                              $tm664.getCurrentTid();
                            if ($e662.tid.isDescendantOf($currentTid663))
                                continue $label659;
                            if ($currentTid663.parent != null) {
                                $retry661 = false;
                                throw $e662;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e662) {
                            $commit660 = false;
                            if ($tm664.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid663 =
                              $tm664.getCurrentTid();
                            if ($e662.tid.isDescendantOf($currentTid663)) {
                                $retry661 = true;
                            }
                            else if ($currentTid663.parent != null) {
                                $retry661 = false;
                                throw $e662;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e662) {
                            $commit660 = false;
                            if ($tm664.checkForStaleObjects())
                                continue $label659;
                            $retry661 = false;
                            throw new fabric.worker.AbortException($e662);
                        }
                        finally {
                            if ($commit660) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e662) {
                                    $commit660 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e662) {
                                    $commit660 = false;
                                    fabric.common.TransactionID $currentTid663 =
                                      $tm664.getCurrentTid();
                                    if ($currentTid663 != null) {
                                        if ($e662.tid.equals($currentTid663) ||
                                              !$e662.tid.isDescendantOf(
                                                           $currentTid663)) {
                                            throw $e662;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit660 && $retry661) {
                                {  }
                                continue $label659;
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
                    long expiry$var668 = expiry;
                    boolean atLeastOnce$var669 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm675 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled678 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff676 = 1;
                    boolean $doBackoff677 = true;
                    boolean $retry672 = true;
                    $label670: for (boolean $commit671 = false; !$commit671; ) {
                        if ($backoffEnabled678) {
                            if ($doBackoff677) {
                                if ($backoff676 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff676);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e673) {
                                            
                                        }
                                    }
                                }
                                if ($backoff676 < 5000) $backoff676 *= 2;
                            }
                            $doBackoff677 = $backoff676 <= 32 || !$doBackoff677;
                        }
                        $commit671 = true;
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
                        catch (final fabric.worker.RetryException $e673) {
                            $commit671 = false;
                            continue $label670;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e673) {
                            $commit671 = false;
                            fabric.common.TransactionID $currentTid674 =
                              $tm675.getCurrentTid();
                            if ($e673.tid.isDescendantOf($currentTid674))
                                continue $label670;
                            if ($currentTid674.parent != null) {
                                $retry672 = false;
                                throw $e673;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e673) {
                            $commit671 = false;
                            if ($tm675.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid674 =
                              $tm675.getCurrentTid();
                            if ($e673.tid.isDescendantOf($currentTid674)) {
                                $retry672 = true;
                            }
                            else if ($currentTid674.parent != null) {
                                $retry672 = false;
                                throw $e673;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e673) {
                            $commit671 = false;
                            if ($tm675.checkForStaleObjects())
                                continue $label670;
                            $retry672 = false;
                            throw new fabric.worker.AbortException($e673);
                        }
                        finally {
                            if ($commit671) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e673) {
                                    $commit671 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e673) {
                                    $commit671 = false;
                                    fabric.common.TransactionID $currentTid674 =
                                      $tm675.getCurrentTid();
                                    if ($currentTid674 != null) {
                                        if ($e673.tid.equals($currentTid674) ||
                                              !$e673.tid.isDescendantOf(
                                                           $currentTid674)) {
                                            throw $e673;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit671 && $retry672) {
                                {
                                    expiry = expiry$var668;
                                    atLeastOnce = atLeastOnce$var669;
                                }
                                continue $label670;
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
                this.get$witnesses().get(i).
                  addObserver(
                    (fabric.metrics.util.Observer)
                      fabric.lang.Object._Proxy.$getProxy(mc.fetch()));
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
        
        public void acquireReconfigLocks() {
            for (int i = 0; i < this.get$witnesses().length(); i++) {
                this.get$witnesses().get(i).acquireReconfigLocks();
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
            $writeInline(out, this.witnesses);
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
                        fabric.worker.transaction.TransactionManager $tm684 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled687 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff685 = 1;
                        boolean $doBackoff686 = true;
                        boolean $retry681 = true;
                        $label679: for (boolean $commit680 = false; !$commit680;
                                        ) {
                            if ($backoffEnabled687) {
                                if ($doBackoff686) {
                                    if ($backoff685 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff685);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e682) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff685 < 5000) $backoff685 *= 2;
                                }
                                $doBackoff686 = $backoff685 <= 32 ||
                                                  !$doBackoff686;
                            }
                            $commit680 = true;
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
                            catch (final fabric.worker.RetryException $e682) {
                                $commit680 = false;
                                continue $label679;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e682) {
                                $commit680 = false;
                                fabric.common.TransactionID $currentTid683 =
                                  $tm684.getCurrentTid();
                                if ($e682.tid.isDescendantOf($currentTid683))
                                    continue $label679;
                                if ($currentTid683.parent != null) {
                                    $retry681 = false;
                                    throw $e682;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e682) {
                                $commit680 = false;
                                if ($tm684.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid683 =
                                  $tm684.getCurrentTid();
                                if ($e682.tid.isDescendantOf($currentTid683)) {
                                    $retry681 = true;
                                }
                                else if ($currentTid683.parent != null) {
                                    $retry681 = false;
                                    throw $e682;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e682) {
                                $commit680 = false;
                                if ($tm684.checkForStaleObjects())
                                    continue $label679;
                                $retry681 = false;
                                throw new fabric.worker.AbortException($e682);
                            }
                            finally {
                                if ($commit680) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e682) {
                                        $commit680 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e682) {
                                        $commit680 = false;
                                        fabric.common.TransactionID
                                          $currentTid683 =
                                          $tm684.getCurrentTid();
                                        if ($currentTid683 != null) {
                                            if ($e682.tid.equals(
                                                            $currentTid683) ||
                                                  !$e682.tid.
                                                  isDescendantOf(
                                                    $currentTid683)) {
                                                throw $e682;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit680 && $retry681) {
                                    {  }
                                    continue $label679;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -9, 24, 29, 125, -35,
    -107, -113, -6, -77, -105, -83, 99, -45, -91, 111, 120, 107, -124, -45, -21,
    34, -91, 42, -1, 116, 27, -75, 125, -36, -51, 32, 57 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524349466000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
