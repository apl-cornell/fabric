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
        public static final long jlc$SourceLastModified$fabil = 1523829675000L;
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
    
    public static final byte[] $classHash = new byte[] { 114, 2, -105, 89, 38,
    66, -19, 0, -99, 86, 4, 118, 19, 70, -38, -46, 99, 19, -73, 54, 28, 7, 7,
    40, -56, -39, 65, 71, 74, -103, -52, 2 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1523829675000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
