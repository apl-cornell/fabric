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
        public static final long jlc$SourceLastModified$fabil = 1526752590000L;
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
                    fabric.worker.transaction.TransactionManager $tm551 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled554 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff552 = 1;
                    boolean $doBackoff553 = true;
                    boolean $retry548 = true;
                    $label546: for (boolean $commit547 = false; !$commit547; ) {
                        if ($backoffEnabled554) {
                            if ($doBackoff553) {
                                if ($backoff552 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff552);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e549) {
                                            
                                        }
                                    }
                                }
                                if ($backoff552 < 5000) $backoff552 *= 2;
                            }
                            $doBackoff553 = $backoff552 <= 32 || !$doBackoff553;
                        }
                        $commit547 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
                        catch (final fabric.worker.RetryException $e549) {
                            $commit547 = false;
                            continue $label546;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e549) {
                            $commit547 = false;
                            fabric.common.TransactionID $currentTid550 =
                              $tm551.getCurrentTid();
                            if ($e549.tid.isDescendantOf($currentTid550))
                                continue $label546;
                            if ($currentTid550.parent != null) {
                                $retry548 = false;
                                throw $e549;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e549) {
                            $commit547 = false;
                            if ($tm551.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid550 =
                              $tm551.getCurrentTid();
                            if ($e549.tid.isDescendantOf($currentTid550)) {
                                $retry548 = true;
                            }
                            else if ($currentTid550.parent != null) {
                                $retry548 = false;
                                throw $e549;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e549) {
                            $commit547 = false;
                            if ($tm551.checkForStaleObjects())
                                continue $label546;
                            $retry548 = false;
                            throw new fabric.worker.AbortException($e549);
                        }
                        finally {
                            if ($commit547) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e549) {
                                    $commit547 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e549) {
                                    $commit547 = false;
                                    fabric.common.TransactionID $currentTid550 =
                                      $tm551.getCurrentTid();
                                    if ($currentTid550 != null) {
                                        if ($e549.tid.equals($currentTid550) ||
                                              !$e549.tid.isDescendantOf(
                                                           $currentTid550)) {
                                            throw $e549;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit547 && $retry548) {
                                {  }
                                continue $label546;
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
                        java.util.concurrent.Callable c$var555 = c;
                        int i$var556 = i;
                        fabric.worker.transaction.TransactionManager $tm562 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled565 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff563 = 1;
                        boolean $doBackoff564 = true;
                        boolean $retry559 = true;
                        $label557: for (boolean $commit558 = false; !$commit558;
                                        ) {
                            if ($backoffEnabled565) {
                                if ($doBackoff564) {
                                    if ($backoff563 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff563);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e560) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff563 < 5000) $backoff563 *= 2;
                                }
                                $doBackoff564 = $backoff563 <= 32 ||
                                                  !$doBackoff564;
                            }
                            $commit558 = true;
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
                            catch (final fabric.worker.RetryException $e560) {
                                $commit558 = false;
                                continue $label557;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e560) {
                                $commit558 = false;
                                fabric.common.TransactionID $currentTid561 =
                                  $tm562.getCurrentTid();
                                if ($e560.tid.isDescendantOf($currentTid561))
                                    continue $label557;
                                if ($currentTid561.parent != null) {
                                    $retry559 = false;
                                    throw $e560;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e560) {
                                $commit558 = false;
                                if ($tm562.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid561 =
                                  $tm562.getCurrentTid();
                                if ($e560.tid.isDescendantOf($currentTid561)) {
                                    $retry559 = true;
                                }
                                else if ($currentTid561.parent != null) {
                                    $retry559 = false;
                                    throw $e560;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e560) {
                                $commit558 = false;
                                if ($tm562.checkForStaleObjects())
                                    continue $label557;
                                $retry559 = false;
                                throw new fabric.worker.AbortException($e560);
                            }
                            finally {
                                if ($commit558) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e560) {
                                        $commit558 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e560) {
                                        $commit558 = false;
                                        fabric.common.TransactionID
                                          $currentTid561 =
                                          $tm562.getCurrentTid();
                                        if ($currentTid561 != null) {
                                            if ($e560.tid.equals(
                                                            $currentTid561) ||
                                                  !$e560.tid.
                                                  isDescendantOf(
                                                    $currentTid561)) {
                                                throw $e560;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit558 && $retry559) {
                                    {
                                        c = c$var555;
                                        i = i$var556;
                                    }
                                    continue $label557;
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
                    fabric.worker.transaction.TransactionManager $tm571 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled574 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff572 = 1;
                    boolean $doBackoff573 = true;
                    boolean $retry568 = true;
                    $label566: for (boolean $commit567 = false; !$commit567; ) {
                        if ($backoffEnabled574) {
                            if ($doBackoff573) {
                                if ($backoff572 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff572);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e569) {
                                            
                                        }
                                    }
                                }
                                if ($backoff572 < 5000) $backoff572 *= 2;
                            }
                            $doBackoff573 = $backoff572 <= 32 || !$doBackoff573;
                        }
                        $commit567 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.set$activated(true); }
                        catch (final fabric.worker.RetryException $e569) {
                            $commit567 = false;
                            continue $label566;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e569) {
                            $commit567 = false;
                            fabric.common.TransactionID $currentTid570 =
                              $tm571.getCurrentTid();
                            if ($e569.tid.isDescendantOf($currentTid570))
                                continue $label566;
                            if ($currentTid570.parent != null) {
                                $retry568 = false;
                                throw $e569;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e569) {
                            $commit567 = false;
                            if ($tm571.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid570 =
                              $tm571.getCurrentTid();
                            if ($e569.tid.isDescendantOf($currentTid570)) {
                                $retry568 = true;
                            }
                            else if ($currentTid570.parent != null) {
                                $retry568 = false;
                                throw $e569;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e569) {
                            $commit567 = false;
                            if ($tm571.checkForStaleObjects())
                                continue $label566;
                            $retry568 = false;
                            throw new fabric.worker.AbortException($e569);
                        }
                        finally {
                            if ($commit567) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e569) {
                                    $commit567 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e569) {
                                    $commit567 = false;
                                    fabric.common.TransactionID $currentTid570 =
                                      $tm571.getCurrentTid();
                                    if ($currentTid570 != null) {
                                        if ($e569.tid.equals($currentTid570) ||
                                              !$e569.tid.isDescendantOf(
                                                           $currentTid570)) {
                                            throw $e569;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit567 && $retry568) {
                                {  }
                                continue $label566;
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
                    long expiry$var575 = expiry;
                    boolean atLeastOnce$var576 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm582 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled585 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff583 = 1;
                    boolean $doBackoff584 = true;
                    boolean $retry579 = true;
                    $label577: for (boolean $commit578 = false; !$commit578; ) {
                        if ($backoffEnabled585) {
                            if ($doBackoff584) {
                                if ($backoff583 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff583);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e580) {
                                            
                                        }
                                    }
                                }
                                if ($backoff583 < 5000) $backoff583 *= 2;
                            }
                            $doBackoff584 = $backoff583 <= 32 || !$doBackoff584;
                        }
                        $commit578 = true;
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
                        catch (final fabric.worker.RetryException $e580) {
                            $commit578 = false;
                            continue $label577;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e580) {
                            $commit578 = false;
                            fabric.common.TransactionID $currentTid581 =
                              $tm582.getCurrentTid();
                            if ($e580.tid.isDescendantOf($currentTid581))
                                continue $label577;
                            if ($currentTid581.parent != null) {
                                $retry579 = false;
                                throw $e580;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e580) {
                            $commit578 = false;
                            if ($tm582.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid581 =
                              $tm582.getCurrentTid();
                            if ($e580.tid.isDescendantOf($currentTid581)) {
                                $retry579 = true;
                            }
                            else if ($currentTid581.parent != null) {
                                $retry579 = false;
                                throw $e580;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e580) {
                            $commit578 = false;
                            if ($tm582.checkForStaleObjects())
                                continue $label577;
                            $retry579 = false;
                            throw new fabric.worker.AbortException($e580);
                        }
                        finally {
                            if ($commit578) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e580) {
                                    $commit578 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e580) {
                                    $commit578 = false;
                                    fabric.common.TransactionID $currentTid581 =
                                      $tm582.getCurrentTid();
                                    if ($currentTid581 != null) {
                                        if ($e580.tid.equals($currentTid581) ||
                                              !$e580.tid.isDescendantOf(
                                                           $currentTid581)) {
                                            throw $e580;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit578 && $retry579) {
                                {
                                    expiry = expiry$var575;
                                    atLeastOnce = atLeastOnce$var576;
                                }
                                continue $label577;
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
                        fabric.worker.transaction.TransactionManager $tm591 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled594 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff592 = 1;
                        boolean $doBackoff593 = true;
                        boolean $retry588 = true;
                        $label586: for (boolean $commit587 = false; !$commit587;
                                        ) {
                            if ($backoffEnabled594) {
                                if ($doBackoff593) {
                                    if ($backoff592 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff592);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e589) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff592 < 5000) $backoff592 *= 2;
                                }
                                $doBackoff593 = $backoff592 <= 32 ||
                                                  !$doBackoff593;
                            }
                            $commit587 = true;
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
                            catch (final fabric.worker.RetryException $e589) {
                                $commit587 = false;
                                continue $label586;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e589) {
                                $commit587 = false;
                                fabric.common.TransactionID $currentTid590 =
                                  $tm591.getCurrentTid();
                                if ($e589.tid.isDescendantOf($currentTid590))
                                    continue $label586;
                                if ($currentTid590.parent != null) {
                                    $retry588 = false;
                                    throw $e589;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e589) {
                                $commit587 = false;
                                if ($tm591.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid590 =
                                  $tm591.getCurrentTid();
                                if ($e589.tid.isDescendantOf($currentTid590)) {
                                    $retry588 = true;
                                }
                                else if ($currentTid590.parent != null) {
                                    $retry588 = false;
                                    throw $e589;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e589) {
                                $commit587 = false;
                                if ($tm591.checkForStaleObjects())
                                    continue $label586;
                                $retry588 = false;
                                throw new fabric.worker.AbortException($e589);
                            }
                            finally {
                                if ($commit587) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e589) {
                                        $commit587 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e589) {
                                        $commit587 = false;
                                        fabric.common.TransactionID
                                          $currentTid590 =
                                          $tm591.getCurrentTid();
                                        if ($currentTid590 != null) {
                                            if ($e589.tid.equals(
                                                            $currentTid590) ||
                                                  !$e589.tid.
                                                  isDescendantOf(
                                                    $currentTid590)) {
                                                throw $e589;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit587 && $retry588) {
                                    {  }
                                    continue $label586;
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
    public static final long jlc$SourceLastModified$fabil = 1526752590000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
