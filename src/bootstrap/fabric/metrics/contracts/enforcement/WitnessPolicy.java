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
        public static final long jlc$SourceLastModified$fabil = 1522607901000L;
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
                    fabric.worker.transaction.TransactionManager $tm626 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled629 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff627 = 1;
                    boolean $doBackoff628 = true;
                    boolean $retry623 = true;
                    $label621: for (boolean $commit622 = false; !$commit622; ) {
                        if ($backoffEnabled629) {
                            if ($doBackoff628) {
                                if ($backoff627 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff627);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e624) {
                                            
                                        }
                                    }
                                }
                                if ($backoff627 < 5000) $backoff627 *= 2;
                            }
                            $doBackoff628 = $backoff627 <= 32 || !$doBackoff628;
                        }
                        $commit622 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
                        catch (final fabric.worker.RetryException $e624) {
                            $commit622 = false;
                            continue $label621;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e624) {
                            $commit622 = false;
                            fabric.common.TransactionID $currentTid625 =
                              $tm626.getCurrentTid();
                            if ($e624.tid.isDescendantOf($currentTid625))
                                continue $label621;
                            if ($currentTid625.parent != null) {
                                $retry623 = false;
                                throw $e624;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e624) {
                            $commit622 = false;
                            if ($tm626.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid625 =
                              $tm626.getCurrentTid();
                            if ($e624.tid.isDescendantOf($currentTid625)) {
                                $retry623 = true;
                            }
                            else if ($currentTid625.parent != null) {
                                $retry623 = false;
                                throw $e624;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e624) {
                            $commit622 = false;
                            if ($tm626.checkForStaleObjects())
                                continue $label621;
                            $retry623 = false;
                            throw new fabric.worker.AbortException($e624);
                        }
                        finally {
                            if ($commit622) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e624) {
                                    $commit622 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e624) {
                                    $commit622 = false;
                                    fabric.common.TransactionID $currentTid625 =
                                      $tm626.getCurrentTid();
                                    if ($currentTid625 != null) {
                                        if ($e624.tid.equals($currentTid625) ||
                                              !$e624.tid.isDescendantOf(
                                                           $currentTid625)) {
                                            throw $e624;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit622 && $retry623) {
                                {  }
                                continue $label621;
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
                        java.util.concurrent.Callable c$var630 = c;
                        int i$var631 = i;
                        fabric.worker.transaction.TransactionManager $tm637 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled640 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff638 = 1;
                        boolean $doBackoff639 = true;
                        boolean $retry634 = true;
                        $label632: for (boolean $commit633 = false; !$commit633;
                                        ) {
                            if ($backoffEnabled640) {
                                if ($doBackoff639) {
                                    if ($backoff638 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff638);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e635) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff638 < 5000) $backoff638 *= 2;
                                }
                                $doBackoff639 = $backoff638 <= 32 ||
                                                  !$doBackoff639;
                            }
                            $commit633 = true;
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
                            catch (final fabric.worker.RetryException $e635) {
                                $commit633 = false;
                                continue $label632;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e635) {
                                $commit633 = false;
                                fabric.common.TransactionID $currentTid636 =
                                  $tm637.getCurrentTid();
                                if ($e635.tid.isDescendantOf($currentTid636))
                                    continue $label632;
                                if ($currentTid636.parent != null) {
                                    $retry634 = false;
                                    throw $e635;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e635) {
                                $commit633 = false;
                                if ($tm637.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid636 =
                                  $tm637.getCurrentTid();
                                if ($e635.tid.isDescendantOf($currentTid636)) {
                                    $retry634 = true;
                                }
                                else if ($currentTid636.parent != null) {
                                    $retry634 = false;
                                    throw $e635;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e635) {
                                $commit633 = false;
                                if ($tm637.checkForStaleObjects())
                                    continue $label632;
                                $retry634 = false;
                                throw new fabric.worker.AbortException($e635);
                            }
                            finally {
                                if ($commit633) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e635) {
                                        $commit633 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e635) {
                                        $commit633 = false;
                                        fabric.common.TransactionID
                                          $currentTid636 =
                                          $tm637.getCurrentTid();
                                        if ($currentTid636 != null) {
                                            if ($e635.tid.equals(
                                                            $currentTid636) ||
                                                  !$e635.tid.
                                                  isDescendantOf(
                                                    $currentTid636)) {
                                                throw $e635;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit633 && $retry634) {
                                    {
                                        c = c$var630;
                                        i = i$var631;
                                    }
                                    continue $label632;
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
                    fabric.worker.transaction.TransactionManager $tm646 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled649 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff647 = 1;
                    boolean $doBackoff648 = true;
                    boolean $retry643 = true;
                    $label641: for (boolean $commit642 = false; !$commit642; ) {
                        if ($backoffEnabled649) {
                            if ($doBackoff648) {
                                if ($backoff647 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff647);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e644) {
                                            
                                        }
                                    }
                                }
                                if ($backoff647 < 5000) $backoff647 *= 2;
                            }
                            $doBackoff648 = $backoff647 <= 32 || !$doBackoff648;
                        }
                        $commit642 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.set$activated(true); }
                        catch (final fabric.worker.RetryException $e644) {
                            $commit642 = false;
                            continue $label641;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e644) {
                            $commit642 = false;
                            fabric.common.TransactionID $currentTid645 =
                              $tm646.getCurrentTid();
                            if ($e644.tid.isDescendantOf($currentTid645))
                                continue $label641;
                            if ($currentTid645.parent != null) {
                                $retry643 = false;
                                throw $e644;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e644) {
                            $commit642 = false;
                            if ($tm646.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid645 =
                              $tm646.getCurrentTid();
                            if ($e644.tid.isDescendantOf($currentTid645)) {
                                $retry643 = true;
                            }
                            else if ($currentTid645.parent != null) {
                                $retry643 = false;
                                throw $e644;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e644) {
                            $commit642 = false;
                            if ($tm646.checkForStaleObjects())
                                continue $label641;
                            $retry643 = false;
                            throw new fabric.worker.AbortException($e644);
                        }
                        finally {
                            if ($commit642) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e644) {
                                    $commit642 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e644) {
                                    $commit642 = false;
                                    fabric.common.TransactionID $currentTid645 =
                                      $tm646.getCurrentTid();
                                    if ($currentTid645 != null) {
                                        if ($e644.tid.equals($currentTid645) ||
                                              !$e644.tid.isDescendantOf(
                                                           $currentTid645)) {
                                            throw $e644;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit642 && $retry643) {
                                {  }
                                continue $label641;
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
                    long expiry$var650 = expiry;
                    boolean atLeastOnce$var651 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm657 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled660 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff658 = 1;
                    boolean $doBackoff659 = true;
                    boolean $retry654 = true;
                    $label652: for (boolean $commit653 = false; !$commit653; ) {
                        if ($backoffEnabled660) {
                            if ($doBackoff659) {
                                if ($backoff658 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff658);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e655) {
                                            
                                        }
                                    }
                                }
                                if ($backoff658 < 5000) $backoff658 *= 2;
                            }
                            $doBackoff659 = $backoff658 <= 32 || !$doBackoff659;
                        }
                        $commit653 = true;
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
                        catch (final fabric.worker.RetryException $e655) {
                            $commit653 = false;
                            continue $label652;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e655) {
                            $commit653 = false;
                            fabric.common.TransactionID $currentTid656 =
                              $tm657.getCurrentTid();
                            if ($e655.tid.isDescendantOf($currentTid656))
                                continue $label652;
                            if ($currentTid656.parent != null) {
                                $retry654 = false;
                                throw $e655;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e655) {
                            $commit653 = false;
                            if ($tm657.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid656 =
                              $tm657.getCurrentTid();
                            if ($e655.tid.isDescendantOf($currentTid656)) {
                                $retry654 = true;
                            }
                            else if ($currentTid656.parent != null) {
                                $retry654 = false;
                                throw $e655;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e655) {
                            $commit653 = false;
                            if ($tm657.checkForStaleObjects())
                                continue $label652;
                            $retry654 = false;
                            throw new fabric.worker.AbortException($e655);
                        }
                        finally {
                            if ($commit653) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e655) {
                                    $commit653 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e655) {
                                    $commit653 = false;
                                    fabric.common.TransactionID $currentTid656 =
                                      $tm657.getCurrentTid();
                                    if ($currentTid656 != null) {
                                        if ($e655.tid.equals($currentTid656) ||
                                              !$e655.tid.isDescendantOf(
                                                           $currentTid656)) {
                                            throw $e655;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit653 && $retry654) {
                                {
                                    expiry = expiry$var650;
                                    atLeastOnce = atLeastOnce$var651;
                                }
                                continue $label652;
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
                        fabric.worker.transaction.TransactionManager $tm666 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled669 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff667 = 1;
                        boolean $doBackoff668 = true;
                        boolean $retry663 = true;
                        $label661: for (boolean $commit662 = false; !$commit662;
                                        ) {
                            if ($backoffEnabled669) {
                                if ($doBackoff668) {
                                    if ($backoff667 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff667);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e664) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff667 < 5000) $backoff667 *= 2;
                                }
                                $doBackoff668 = $backoff667 <= 32 ||
                                                  !$doBackoff668;
                            }
                            $commit662 = true;
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
                            catch (final fabric.worker.RetryException $e664) {
                                $commit662 = false;
                                continue $label661;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e664) {
                                $commit662 = false;
                                fabric.common.TransactionID $currentTid665 =
                                  $tm666.getCurrentTid();
                                if ($e664.tid.isDescendantOf($currentTid665))
                                    continue $label661;
                                if ($currentTid665.parent != null) {
                                    $retry663 = false;
                                    throw $e664;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e664) {
                                $commit662 = false;
                                if ($tm666.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid665 =
                                  $tm666.getCurrentTid();
                                if ($e664.tid.isDescendantOf($currentTid665)) {
                                    $retry663 = true;
                                }
                                else if ($currentTid665.parent != null) {
                                    $retry663 = false;
                                    throw $e664;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e664) {
                                $commit662 = false;
                                if ($tm666.checkForStaleObjects())
                                    continue $label661;
                                $retry663 = false;
                                throw new fabric.worker.AbortException($e664);
                            }
                            finally {
                                if ($commit662) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e664) {
                                        $commit662 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e664) {
                                        $commit662 = false;
                                        fabric.common.TransactionID
                                          $currentTid665 =
                                          $tm666.getCurrentTid();
                                        if ($currentTid665 != null) {
                                            if ($e664.tid.equals(
                                                            $currentTid665) ||
                                                  !$e664.tid.
                                                  isDescendantOf(
                                                    $currentTid665)) {
                                                throw $e664;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit662 && $retry663) {
                                    {  }
                                    continue $label661;
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
    public static final long jlc$SourceLastModified$fabil = 1522607901000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
