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
        public static final long jlc$SourceLastModified$fabil = 1521832647000L;
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
                    fabric.worker.transaction.TransactionManager $tm566 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled569 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff567 = 1;
                    boolean $doBackoff568 = true;
                    boolean $retry563 = true;
                    $label561: for (boolean $commit562 = false; !$commit562; ) {
                        if ($backoffEnabled569) {
                            if ($doBackoff568) {
                                if ($backoff567 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff567);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e564) {
                                            
                                        }
                                    }
                                }
                                if ($backoff567 < 5000) $backoff567 *= 2;
                            }
                            $doBackoff568 = $backoff567 <= 32 || !$doBackoff568;
                        }
                        $commit562 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
                        catch (final fabric.worker.RetryException $e564) {
                            $commit562 = false;
                            continue $label561;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e564) {
                            $commit562 = false;
                            fabric.common.TransactionID $currentTid565 =
                              $tm566.getCurrentTid();
                            if ($e564.tid.isDescendantOf($currentTid565))
                                continue $label561;
                            if ($currentTid565.parent != null) {
                                $retry563 = false;
                                throw $e564;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e564) {
                            $commit562 = false;
                            if ($tm566.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid565 =
                              $tm566.getCurrentTid();
                            if ($e564.tid.isDescendantOf($currentTid565)) {
                                $retry563 = true;
                            }
                            else if ($currentTid565.parent != null) {
                                $retry563 = false;
                                throw $e564;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e564) {
                            $commit562 = false;
                            if ($tm566.checkForStaleObjects())
                                continue $label561;
                            $retry563 = false;
                            throw new fabric.worker.AbortException($e564);
                        }
                        finally {
                            if ($commit562) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e564) {
                                    $commit562 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e564) {
                                    $commit562 = false;
                                    fabric.common.TransactionID $currentTid565 =
                                      $tm566.getCurrentTid();
                                    if ($currentTid565 != null) {
                                        if ($e564.tid.equals($currentTid565) ||
                                              !$e564.tid.isDescendantOf(
                                                           $currentTid565)) {
                                            throw $e564;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit562 && $retry563) {
                                {  }
                                continue $label561;
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
                        java.util.concurrent.Callable c$var570 = c;
                        int i$var571 = i;
                        fabric.worker.transaction.TransactionManager $tm577 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled580 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff578 = 1;
                        boolean $doBackoff579 = true;
                        boolean $retry574 = true;
                        $label572: for (boolean $commit573 = false; !$commit573;
                                        ) {
                            if ($backoffEnabled580) {
                                if ($doBackoff579) {
                                    if ($backoff578 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff578);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e575) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff578 < 5000) $backoff578 *= 2;
                                }
                                $doBackoff579 = $backoff578 <= 32 ||
                                                  !$doBackoff579;
                            }
                            $commit573 = true;
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
                                        fabric.common.TransactionID
                                          $currentTid576 =
                                          $tm577.getCurrentTid();
                                        if ($currentTid576 != null) {
                                            if ($e575.tid.equals(
                                                            $currentTid576) ||
                                                  !$e575.tid.
                                                  isDescendantOf(
                                                    $currentTid576)) {
                                                throw $e575;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit573 && $retry574) {
                                    {
                                        c = c$var570;
                                        i = i$var571;
                                    }
                                    continue $label572;
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
                    fabric.worker.transaction.TransactionManager $tm586 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled589 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff587 = 1;
                    boolean $doBackoff588 = true;
                    boolean $retry583 = true;
                    $label581: for (boolean $commit582 = false; !$commit582; ) {
                        if ($backoffEnabled589) {
                            if ($doBackoff588) {
                                if ($backoff587 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff587);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e584) {
                                            
                                        }
                                    }
                                }
                                if ($backoff587 < 5000) $backoff587 *= 2;
                            }
                            $doBackoff588 = $backoff587 <= 32 || !$doBackoff588;
                        }
                        $commit582 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.set$activated(true); }
                        catch (final fabric.worker.RetryException $e584) {
                            $commit582 = false;
                            continue $label581;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e584) {
                            $commit582 = false;
                            fabric.common.TransactionID $currentTid585 =
                              $tm586.getCurrentTid();
                            if ($e584.tid.isDescendantOf($currentTid585))
                                continue $label581;
                            if ($currentTid585.parent != null) {
                                $retry583 = false;
                                throw $e584;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e584) {
                            $commit582 = false;
                            if ($tm586.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid585 =
                              $tm586.getCurrentTid();
                            if ($e584.tid.isDescendantOf($currentTid585)) {
                                $retry583 = true;
                            }
                            else if ($currentTid585.parent != null) {
                                $retry583 = false;
                                throw $e584;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e584) {
                            $commit582 = false;
                            if ($tm586.checkForStaleObjects())
                                continue $label581;
                            $retry583 = false;
                            throw new fabric.worker.AbortException($e584);
                        }
                        finally {
                            if ($commit582) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e584) {
                                    $commit582 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e584) {
                                    $commit582 = false;
                                    fabric.common.TransactionID $currentTid585 =
                                      $tm586.getCurrentTid();
                                    if ($currentTid585 != null) {
                                        if ($e584.tid.equals($currentTid585) ||
                                              !$e584.tid.isDescendantOf(
                                                           $currentTid585)) {
                                            throw $e584;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit582 && $retry583) {
                                {  }
                                continue $label581;
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
                    long expiry$var590 = expiry;
                    boolean atLeastOnce$var591 = atLeastOnce;
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
                                {
                                    expiry = expiry$var590;
                                    atLeastOnce = atLeastOnce$var591;
                                }
                                continue $label592;
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
                        fabric.worker.transaction.TransactionManager $tm606 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled609 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff607 = 1;
                        boolean $doBackoff608 = true;
                        boolean $retry603 = true;
                        $label601: for (boolean $commit602 = false; !$commit602;
                                        ) {
                            if ($backoffEnabled609) {
                                if ($doBackoff608) {
                                    if ($backoff607 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff607);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e604) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff607 < 5000) $backoff607 *= 2;
                                }
                                $doBackoff608 = $backoff607 <= 32 ||
                                                  !$doBackoff608;
                            }
                            $commit602 = true;
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
                            catch (final fabric.worker.RetryException $e604) {
                                $commit602 = false;
                                continue $label601;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e604) {
                                $commit602 = false;
                                fabric.common.TransactionID $currentTid605 =
                                  $tm606.getCurrentTid();
                                if ($e604.tid.isDescendantOf($currentTid605))
                                    continue $label601;
                                if ($currentTid605.parent != null) {
                                    $retry603 = false;
                                    throw $e604;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e604) {
                                $commit602 = false;
                                if ($tm606.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid605 =
                                  $tm606.getCurrentTid();
                                if ($e604.tid.isDescendantOf($currentTid605)) {
                                    $retry603 = true;
                                }
                                else if ($currentTid605.parent != null) {
                                    $retry603 = false;
                                    throw $e604;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e604) {
                                $commit602 = false;
                                if ($tm606.checkForStaleObjects())
                                    continue $label601;
                                $retry603 = false;
                                throw new fabric.worker.AbortException($e604);
                            }
                            finally {
                                if ($commit602) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e604) {
                                        $commit602 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e604) {
                                        $commit602 = false;
                                        fabric.common.TransactionID
                                          $currentTid605 =
                                          $tm606.getCurrentTid();
                                        if ($currentTid605 != null) {
                                            if ($e604.tid.equals(
                                                            $currentTid605) ||
                                                  !$e604.tid.
                                                  isDescendantOf(
                                                    $currentTid605)) {
                                                throw $e604;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit602 && $retry603) {
                                    {  }
                                    continue $label601;
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
    public static final long jlc$SourceLastModified$fabil = 1521832647000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
