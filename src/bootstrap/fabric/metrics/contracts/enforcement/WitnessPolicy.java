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
        
        public static final byte[] $classHash = new byte[] { 72, 105, 77, -124,
        66, 106, 125, -33, -72, -90, -73, 76, 50, 75, 33, 6, -10, 88, -23, -117,
        -100, 94, 124, 125, 66, -61, -121, 113, -22, -32, 83, -82 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1524675608000L;
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
                    fabric.worker.transaction.TransactionManager $tm606 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled609 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff607 = 1;
                    boolean $doBackoff608 = true;
                    boolean $retry603 = true;
                    $label601: for (boolean $commit602 = false; !$commit602; ) {
                        if ($backoffEnabled609) {
                            if ($doBackoff608) {
                                if ($backoff607 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff607);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e604) {
                                            
                                        }
                                    }
                                }
                                if ($backoff607 < 5000) $backoff607 *= 2;
                            }
                            $doBackoff608 = $backoff607 <= 32 || !$doBackoff608;
                        }
                        $commit602 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
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
                                    fabric.common.TransactionID $currentTid605 =
                                      $tm606.getCurrentTid();
                                    if ($currentTid605 != null) {
                                        if ($e604.tid.equals($currentTid605) ||
                                              !$e604.tid.isDescendantOf(
                                                           $currentTid605)) {
                                            throw $e604;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit602 && $retry603) {
                                {  }
                                continue $label601;
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
                        java.util.concurrent.Callable c$var610 = c;
                        int i$var611 = i;
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
                                    {
                                        c = c$var610;
                                        i = i$var611;
                                    }
                                    continue $label612;
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
                        try { tmp.set$activated(true); }
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
                    long expiry$var630 = expiry;
                    boolean atLeastOnce$var631 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm637 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled640 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff638 = 1;
                    boolean $doBackoff639 = true;
                    boolean $retry634 = true;
                    $label632: for (boolean $commit633 = false; !$commit633; ) {
                        if ($backoffEnabled640) {
                            if ($doBackoff639) {
                                if ($backoff638 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff638);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e635) {
                                            
                                        }
                                    }
                                }
                                if ($backoff638 < 5000) $backoff638 *= 2;
                            }
                            $doBackoff639 = $backoff638 <= 32 || !$doBackoff639;
                        }
                        $commit633 = true;
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
                                    fabric.common.TransactionID $currentTid636 =
                                      $tm637.getCurrentTid();
                                    if ($currentTid636 != null) {
                                        if ($e635.tid.equals($currentTid636) ||
                                              !$e635.tid.isDescendantOf(
                                                           $currentTid636)) {
                                            throw $e635;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit633 && $retry634) {
                                {
                                    expiry = expiry$var630;
                                    atLeastOnce = atLeastOnce$var631;
                                }
                                continue $label632;
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
                        fabric.worker.transaction.TransactionManager $tm646 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled649 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff647 = 1;
                        boolean $doBackoff648 = true;
                        boolean $retry643 = true;
                        $label641: for (boolean $commit642 = false; !$commit642;
                                        ) {
                            if ($backoffEnabled649) {
                                if ($doBackoff648) {
                                    if ($backoff647 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff647);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e644) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff647 < 5000) $backoff647 *= 2;
                                }
                                $doBackoff648 = $backoff647 <= 32 ||
                                                  !$doBackoff648;
                            }
                            $commit642 = true;
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
                                        fabric.common.TransactionID
                                          $currentTid645 =
                                          $tm646.getCurrentTid();
                                        if ($currentTid645 != null) {
                                            if ($e644.tid.equals(
                                                            $currentTid645) ||
                                                  !$e644.tid.
                                                  isDescendantOf(
                                                    $currentTid645)) {
                                                throw $e644;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
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
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 86, 96, 19, 9, -120,
    -99, -59, -61, 113, 85, 56, -89, 17, -14, -103, 64, -60, 124, -83, -5, 23,
    11, 70, 96, 104, -17, 99, 111, 52, -85, 3, 71 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524675608000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
