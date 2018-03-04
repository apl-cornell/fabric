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
        public static final long jlc$SourceLastModified$fabil = 1520199002000L;
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
                    fabric.worker.transaction.TransactionManager $tm593 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled596 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff594 = 1;
                    boolean $doBackoff595 = true;
                    boolean $retry590 = true;
                    $label588: for (boolean $commit589 = false; !$commit589; ) {
                        if ($backoffEnabled596) {
                            if ($doBackoff595) {
                                if ($backoff594 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff594);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e591) {
                                            
                                        }
                                    }
                                }
                                if ($backoff594 < 5000) $backoff594 *= 2;
                            }
                            $doBackoff595 = $backoff594 <= 32 || !$doBackoff595;
                        }
                        $commit589 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
                        catch (final fabric.worker.RetryException $e591) {
                            $commit589 = false;
                            continue $label588;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e591) {
                            $commit589 = false;
                            fabric.common.TransactionID $currentTid592 =
                              $tm593.getCurrentTid();
                            if ($e591.tid.isDescendantOf($currentTid592))
                                continue $label588;
                            if ($currentTid592.parent != null) {
                                $retry590 = false;
                                throw $e591;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e591) {
                            $commit589 = false;
                            if ($tm593.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid592 =
                              $tm593.getCurrentTid();
                            if ($e591.tid.isDescendantOf($currentTid592)) {
                                $retry590 = true;
                            }
                            else if ($currentTid592.parent != null) {
                                $retry590 = false;
                                throw $e591;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e591) {
                            $commit589 = false;
                            if ($tm593.checkForStaleObjects())
                                continue $label588;
                            $retry590 = false;
                            throw new fabric.worker.AbortException($e591);
                        }
                        finally {
                            if ($commit589) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e591) {
                                    $commit589 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e591) {
                                    $commit589 = false;
                                    fabric.common.TransactionID $currentTid592 =
                                      $tm593.getCurrentTid();
                                    if ($currentTid592 != null) {
                                        if ($e591.tid.equals($currentTid592) ||
                                              !$e591.tid.isDescendantOf(
                                                           $currentTid592)) {
                                            throw $e591;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit589 && $retry590) {
                                {  }
                                continue $label588;
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
                        java.util.concurrent.Callable c$var597 = c;
                        int i$var598 = i;
                        fabric.worker.transaction.TransactionManager $tm604 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled607 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff605 = 1;
                        boolean $doBackoff606 = true;
                        boolean $retry601 = true;
                        $label599: for (boolean $commit600 = false; !$commit600;
                                        ) {
                            if ($backoffEnabled607) {
                                if ($doBackoff606) {
                                    if ($backoff605 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff605);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e602) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff605 < 5000) $backoff605 *= 2;
                                }
                                $doBackoff606 = $backoff605 <= 32 ||
                                                  !$doBackoff606;
                            }
                            $commit600 = true;
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
                            catch (final fabric.worker.RetryException $e602) {
                                $commit600 = false;
                                continue $label599;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e602) {
                                $commit600 = false;
                                fabric.common.TransactionID $currentTid603 =
                                  $tm604.getCurrentTid();
                                if ($e602.tid.isDescendantOf($currentTid603))
                                    continue $label599;
                                if ($currentTid603.parent != null) {
                                    $retry601 = false;
                                    throw $e602;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e602) {
                                $commit600 = false;
                                if ($tm604.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid603 =
                                  $tm604.getCurrentTid();
                                if ($e602.tid.isDescendantOf($currentTid603)) {
                                    $retry601 = true;
                                }
                                else if ($currentTid603.parent != null) {
                                    $retry601 = false;
                                    throw $e602;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e602) {
                                $commit600 = false;
                                if ($tm604.checkForStaleObjects())
                                    continue $label599;
                                $retry601 = false;
                                throw new fabric.worker.AbortException($e602);
                            }
                            finally {
                                if ($commit600) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e602) {
                                        $commit600 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e602) {
                                        $commit600 = false;
                                        fabric.common.TransactionID
                                          $currentTid603 =
                                          $tm604.getCurrentTid();
                                        if ($currentTid603 != null) {
                                            if ($e602.tid.equals(
                                                            $currentTid603) ||
                                                  !$e602.tid.
                                                  isDescendantOf(
                                                    $currentTid603)) {
                                                throw $e602;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit600 && $retry601) {
                                    {
                                        c = c$var597;
                                        i = i$var598;
                                    }
                                    continue $label599;
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
                    fabric.worker.transaction.TransactionManager $tm613 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled616 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff614 = 1;
                    boolean $doBackoff615 = true;
                    boolean $retry610 = true;
                    $label608: for (boolean $commit609 = false; !$commit609; ) {
                        if ($backoffEnabled616) {
                            if ($doBackoff615) {
                                if ($backoff614 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff614);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e611) {
                                            
                                        }
                                    }
                                }
                                if ($backoff614 < 5000) $backoff614 *= 2;
                            }
                            $doBackoff615 = $backoff614 <= 32 || !$doBackoff615;
                        }
                        $commit609 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.set$activated(true); }
                        catch (final fabric.worker.RetryException $e611) {
                            $commit609 = false;
                            continue $label608;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e611) {
                            $commit609 = false;
                            fabric.common.TransactionID $currentTid612 =
                              $tm613.getCurrentTid();
                            if ($e611.tid.isDescendantOf($currentTid612))
                                continue $label608;
                            if ($currentTid612.parent != null) {
                                $retry610 = false;
                                throw $e611;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e611) {
                            $commit609 = false;
                            if ($tm613.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid612 =
                              $tm613.getCurrentTid();
                            if ($e611.tid.isDescendantOf($currentTid612)) {
                                $retry610 = true;
                            }
                            else if ($currentTid612.parent != null) {
                                $retry610 = false;
                                throw $e611;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e611) {
                            $commit609 = false;
                            if ($tm613.checkForStaleObjects())
                                continue $label608;
                            $retry610 = false;
                            throw new fabric.worker.AbortException($e611);
                        }
                        finally {
                            if ($commit609) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e611) {
                                    $commit609 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e611) {
                                    $commit609 = false;
                                    fabric.common.TransactionID $currentTid612 =
                                      $tm613.getCurrentTid();
                                    if ($currentTid612 != null) {
                                        if ($e611.tid.equals($currentTid612) ||
                                              !$e611.tid.isDescendantOf(
                                                           $currentTid612)) {
                                            throw $e611;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit609 && $retry610) {
                                {  }
                                continue $label608;
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
                    long expiry$var617 = expiry;
                    boolean atLeastOnce$var618 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm624 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled627 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff625 = 1;
                    boolean $doBackoff626 = true;
                    boolean $retry621 = true;
                    $label619: for (boolean $commit620 = false; !$commit620; ) {
                        if ($backoffEnabled627) {
                            if ($doBackoff626) {
                                if ($backoff625 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff625);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e622) {
                                            
                                        }
                                    }
                                }
                                if ($backoff625 < 5000) $backoff625 *= 2;
                            }
                            $doBackoff626 = $backoff625 <= 32 || !$doBackoff626;
                        }
                        $commit620 = true;
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
                        catch (final fabric.worker.RetryException $e622) {
                            $commit620 = false;
                            continue $label619;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e622) {
                            $commit620 = false;
                            fabric.common.TransactionID $currentTid623 =
                              $tm624.getCurrentTid();
                            if ($e622.tid.isDescendantOf($currentTid623))
                                continue $label619;
                            if ($currentTid623.parent != null) {
                                $retry621 = false;
                                throw $e622;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e622) {
                            $commit620 = false;
                            if ($tm624.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid623 =
                              $tm624.getCurrentTid();
                            if ($e622.tid.isDescendantOf($currentTid623)) {
                                $retry621 = true;
                            }
                            else if ($currentTid623.parent != null) {
                                $retry621 = false;
                                throw $e622;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e622) {
                            $commit620 = false;
                            if ($tm624.checkForStaleObjects())
                                continue $label619;
                            $retry621 = false;
                            throw new fabric.worker.AbortException($e622);
                        }
                        finally {
                            if ($commit620) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e622) {
                                    $commit620 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e622) {
                                    $commit620 = false;
                                    fabric.common.TransactionID $currentTid623 =
                                      $tm624.getCurrentTid();
                                    if ($currentTid623 != null) {
                                        if ($e622.tid.equals($currentTid623) ||
                                              !$e622.tid.isDescendantOf(
                                                           $currentTid623)) {
                                            throw $e622;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit620 && $retry621) {
                                {
                                    expiry = expiry$var617;
                                    atLeastOnce = atLeastOnce$var618;
                                }
                                continue $label619;
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
                        fabric.worker.transaction.TransactionManager $tm633 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled636 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff634 = 1;
                        boolean $doBackoff635 = true;
                        boolean $retry630 = true;
                        $label628: for (boolean $commit629 = false; !$commit629;
                                        ) {
                            if ($backoffEnabled636) {
                                if ($doBackoff635) {
                                    if ($backoff634 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff634);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e631) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff634 < 5000) $backoff634 *= 2;
                                }
                                $doBackoff635 = $backoff634 <= 32 ||
                                                  !$doBackoff635;
                            }
                            $commit629 = true;
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
                            catch (final fabric.worker.RetryException $e631) {
                                $commit629 = false;
                                continue $label628;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e631) {
                                $commit629 = false;
                                fabric.common.TransactionID $currentTid632 =
                                  $tm633.getCurrentTid();
                                if ($e631.tid.isDescendantOf($currentTid632))
                                    continue $label628;
                                if ($currentTid632.parent != null) {
                                    $retry630 = false;
                                    throw $e631;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e631) {
                                $commit629 = false;
                                if ($tm633.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid632 =
                                  $tm633.getCurrentTid();
                                if ($e631.tid.isDescendantOf($currentTid632)) {
                                    $retry630 = true;
                                }
                                else if ($currentTid632.parent != null) {
                                    $retry630 = false;
                                    throw $e631;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e631) {
                                $commit629 = false;
                                if ($tm633.checkForStaleObjects())
                                    continue $label628;
                                $retry630 = false;
                                throw new fabric.worker.AbortException($e631);
                            }
                            finally {
                                if ($commit629) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e631) {
                                        $commit629 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e631) {
                                        $commit629 = false;
                                        fabric.common.TransactionID
                                          $currentTid632 =
                                          $tm633.getCurrentTid();
                                        if ($currentTid632 != null) {
                                            if ($e631.tid.equals(
                                                            $currentTid632) ||
                                                  !$e631.tid.
                                                  isDescendantOf(
                                                    $currentTid632)) {
                                                throw $e631;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit629 && $retry630) {
                                    {  }
                                    continue $label628;
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
    public static final long jlc$SourceLastModified$fabil = 1520199002000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
