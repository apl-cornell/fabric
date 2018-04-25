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
        
        public static final byte[] $classHash = new byte[] { 34, -44, 40, 88,
        121, 92, 42, -61, -72, 6, 8, 54, -94, 15, -25, -120, 25, -95, -78, -99,
        -13, 31, -69, 94, -30, 36, -85, -55, 67, 100, -5, -85 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1524613282000L;
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
                    fabric.worker.transaction.TransactionManager $tm115 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled118 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff116 = 1;
                    boolean $doBackoff117 = true;
                    boolean $retry112 = true;
                    $label110: for (boolean $commit111 = false; !$commit111; ) {
                        if ($backoffEnabled118) {
                            if ($doBackoff117) {
                                if ($backoff116 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff116);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e113) {
                                            
                                        }
                                    }
                                }
                                if ($backoff116 < 5000) $backoff116 *= 2;
                            }
                            $doBackoff117 = $backoff116 <= 32 || !$doBackoff117;
                        }
                        $commit111 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
                        catch (final fabric.worker.RetryException $e113) {
                            $commit111 = false;
                            continue $label110;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e113) {
                            $commit111 = false;
                            fabric.common.TransactionID $currentTid114 =
                              $tm115.getCurrentTid();
                            if ($e113.tid.isDescendantOf($currentTid114))
                                continue $label110;
                            if ($currentTid114.parent != null) {
                                $retry112 = false;
                                throw $e113;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e113) {
                            $commit111 = false;
                            if ($tm115.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid114 =
                              $tm115.getCurrentTid();
                            if ($e113.tid.isDescendantOf($currentTid114)) {
                                $retry112 = true;
                            }
                            else if ($currentTid114.parent != null) {
                                $retry112 = false;
                                throw $e113;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e113) {
                            $commit111 = false;
                            if ($tm115.checkForStaleObjects())
                                continue $label110;
                            $retry112 = false;
                            throw new fabric.worker.AbortException($e113);
                        }
                        finally {
                            if ($commit111) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e113) {
                                    $commit111 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e113) {
                                    $commit111 = false;
                                    fabric.common.TransactionID $currentTid114 =
                                      $tm115.getCurrentTid();
                                    if ($currentTid114 != null) {
                                        if ($e113.tid.equals($currentTid114) ||
                                              !$e113.tid.isDescendantOf(
                                                           $currentTid114)) {
                                            throw $e113;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit111 && $retry112) {
                                {  }
                                continue $label110;
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
                        java.util.concurrent.Callable c$var119 = c;
                        int i$var120 = i;
                        fabric.worker.transaction.TransactionManager $tm126 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled129 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff127 = 1;
                        boolean $doBackoff128 = true;
                        boolean $retry123 = true;
                        $label121: for (boolean $commit122 = false; !$commit122;
                                        ) {
                            if ($backoffEnabled129) {
                                if ($doBackoff128) {
                                    if ($backoff127 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff127);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e124) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff127 < 5000) $backoff127 *= 2;
                                }
                                $doBackoff128 = $backoff127 <= 32 ||
                                                  !$doBackoff128;
                            }
                            $commit122 = true;
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
                            catch (final fabric.worker.RetryException $e124) {
                                $commit122 = false;
                                continue $label121;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e124) {
                                $commit122 = false;
                                fabric.common.TransactionID $currentTid125 =
                                  $tm126.getCurrentTid();
                                if ($e124.tid.isDescendantOf($currentTid125))
                                    continue $label121;
                                if ($currentTid125.parent != null) {
                                    $retry123 = false;
                                    throw $e124;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e124) {
                                $commit122 = false;
                                if ($tm126.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid125 =
                                  $tm126.getCurrentTid();
                                if ($e124.tid.isDescendantOf($currentTid125)) {
                                    $retry123 = true;
                                }
                                else if ($currentTid125.parent != null) {
                                    $retry123 = false;
                                    throw $e124;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e124) {
                                $commit122 = false;
                                if ($tm126.checkForStaleObjects())
                                    continue $label121;
                                $retry123 = false;
                                throw new fabric.worker.AbortException($e124);
                            }
                            finally {
                                if ($commit122) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e124) {
                                        $commit122 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e124) {
                                        $commit122 = false;
                                        fabric.common.TransactionID
                                          $currentTid125 =
                                          $tm126.getCurrentTid();
                                        if ($currentTid125 != null) {
                                            if ($e124.tid.equals(
                                                            $currentTid125) ||
                                                  !$e124.tid.
                                                  isDescendantOf(
                                                    $currentTid125)) {
                                                throw $e124;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit122 && $retry123) {
                                    {
                                        c = c$var119;
                                        i = i$var120;
                                    }
                                    continue $label121;
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
                    fabric.worker.transaction.TransactionManager $tm135 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled138 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff136 = 1;
                    boolean $doBackoff137 = true;
                    boolean $retry132 = true;
                    $label130: for (boolean $commit131 = false; !$commit131; ) {
                        if ($backoffEnabled138) {
                            if ($doBackoff137) {
                                if ($backoff136 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff136);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e133) {
                                            
                                        }
                                    }
                                }
                                if ($backoff136 < 5000) $backoff136 *= 2;
                            }
                            $doBackoff137 = $backoff136 <= 32 || !$doBackoff137;
                        }
                        $commit131 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.set$activated(true); }
                        catch (final fabric.worker.RetryException $e133) {
                            $commit131 = false;
                            continue $label130;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e133) {
                            $commit131 = false;
                            fabric.common.TransactionID $currentTid134 =
                              $tm135.getCurrentTid();
                            if ($e133.tid.isDescendantOf($currentTid134))
                                continue $label130;
                            if ($currentTid134.parent != null) {
                                $retry132 = false;
                                throw $e133;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e133) {
                            $commit131 = false;
                            if ($tm135.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid134 =
                              $tm135.getCurrentTid();
                            if ($e133.tid.isDescendantOf($currentTid134)) {
                                $retry132 = true;
                            }
                            else if ($currentTid134.parent != null) {
                                $retry132 = false;
                                throw $e133;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e133) {
                            $commit131 = false;
                            if ($tm135.checkForStaleObjects())
                                continue $label130;
                            $retry132 = false;
                            throw new fabric.worker.AbortException($e133);
                        }
                        finally {
                            if ($commit131) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e133) {
                                    $commit131 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e133) {
                                    $commit131 = false;
                                    fabric.common.TransactionID $currentTid134 =
                                      $tm135.getCurrentTid();
                                    if ($currentTid134 != null) {
                                        if ($e133.tid.equals($currentTid134) ||
                                              !$e133.tid.isDescendantOf(
                                                           $currentTid134)) {
                                            throw $e133;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit131 && $retry132) {
                                {  }
                                continue $label130;
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
                    long expiry$var139 = expiry;
                    boolean atLeastOnce$var140 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm146 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled149 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff147 = 1;
                    boolean $doBackoff148 = true;
                    boolean $retry143 = true;
                    $label141: for (boolean $commit142 = false; !$commit142; ) {
                        if ($backoffEnabled149) {
                            if ($doBackoff148) {
                                if ($backoff147 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff147);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e144) {
                                            
                                        }
                                    }
                                }
                                if ($backoff147 < 5000) $backoff147 *= 2;
                            }
                            $doBackoff148 = $backoff147 <= 32 || !$doBackoff148;
                        }
                        $commit142 = true;
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
                        catch (final fabric.worker.RetryException $e144) {
                            $commit142 = false;
                            continue $label141;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e144) {
                            $commit142 = false;
                            fabric.common.TransactionID $currentTid145 =
                              $tm146.getCurrentTid();
                            if ($e144.tid.isDescendantOf($currentTid145))
                                continue $label141;
                            if ($currentTid145.parent != null) {
                                $retry143 = false;
                                throw $e144;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e144) {
                            $commit142 = false;
                            if ($tm146.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid145 =
                              $tm146.getCurrentTid();
                            if ($e144.tid.isDescendantOf($currentTid145)) {
                                $retry143 = true;
                            }
                            else if ($currentTid145.parent != null) {
                                $retry143 = false;
                                throw $e144;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e144) {
                            $commit142 = false;
                            if ($tm146.checkForStaleObjects())
                                continue $label141;
                            $retry143 = false;
                            throw new fabric.worker.AbortException($e144);
                        }
                        finally {
                            if ($commit142) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e144) {
                                    $commit142 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e144) {
                                    $commit142 = false;
                                    fabric.common.TransactionID $currentTid145 =
                                      $tm146.getCurrentTid();
                                    if ($currentTid145 != null) {
                                        if ($e144.tid.equals($currentTid145) ||
                                              !$e144.tid.isDescendantOf(
                                                           $currentTid145)) {
                                            throw $e144;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit142 && $retry143) {
                                {
                                    expiry = expiry$var139;
                                    atLeastOnce = atLeastOnce$var140;
                                }
                                continue $label141;
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
                        fabric.worker.transaction.TransactionManager $tm155 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled158 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff156 = 1;
                        boolean $doBackoff157 = true;
                        boolean $retry152 = true;
                        $label150: for (boolean $commit151 = false; !$commit151;
                                        ) {
                            if ($backoffEnabled158) {
                                if ($doBackoff157) {
                                    if ($backoff156 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff156);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e153) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff156 < 5000) $backoff156 *= 2;
                                }
                                $doBackoff157 = $backoff156 <= 32 ||
                                                  !$doBackoff157;
                            }
                            $commit151 = true;
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
                            catch (final fabric.worker.RetryException $e153) {
                                $commit151 = false;
                                continue $label150;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e153) {
                                $commit151 = false;
                                fabric.common.TransactionID $currentTid154 =
                                  $tm155.getCurrentTid();
                                if ($e153.tid.isDescendantOf($currentTid154))
                                    continue $label150;
                                if ($currentTid154.parent != null) {
                                    $retry152 = false;
                                    throw $e153;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e153) {
                                $commit151 = false;
                                if ($tm155.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid154 =
                                  $tm155.getCurrentTid();
                                if ($e153.tid.isDescendantOf($currentTid154)) {
                                    $retry152 = true;
                                }
                                else if ($currentTid154.parent != null) {
                                    $retry152 = false;
                                    throw $e153;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e153) {
                                $commit151 = false;
                                if ($tm155.checkForStaleObjects())
                                    continue $label150;
                                $retry152 = false;
                                throw new fabric.worker.AbortException($e153);
                            }
                            finally {
                                if ($commit151) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e153) {
                                        $commit151 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e153) {
                                        $commit151 = false;
                                        fabric.common.TransactionID
                                          $currentTid154 =
                                          $tm155.getCurrentTid();
                                        if ($currentTid154 != null) {
                                            if ($e153.tid.equals(
                                                            $currentTid154) ||
                                                  !$e153.tid.
                                                  isDescendantOf(
                                                    $currentTid154)) {
                                                throw $e153;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit151 && $retry152) {
                                    {  }
                                    continue $label150;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 87, -104, 15, -79, 51,
    -8, 75, 45, 53, 90, 51, 66, -19, -103, 31, 102, 88, -24, -109, 119, -18,
    -24, 122, 34, 88, -26, -69, -14, 77, 57, 52, 81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524613282000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAL0ZC2wcR3Xu/Hcc23HifJy/c43I705JSiA1jWgOp7n6Uhs7SVMbYtZ7c+eN93Y3u3P2OanbkrZKQCKqWjcfoBFEKfTjJAIRQESRiqDQkKqC8ilF0AaJ0pQQSkCUSFDKezNzd3vn9cWWClZm3tzMezPvP282Y9dImWOT5rjSp+lBNmxRJ7hF6YtEOxTbobGwrjjOdpjtVaeVRo5c+XpskZ/4o6RGVQzT0FRF7zUcRmqje5RBJWRQFtrRGWnpIVUqEm5VnH5G/D2b0zZZYpn6cEI3mTxk3P5PrAqNHt1d/80SUtdN6jSjiylMU8OmwWiadZOaJE32Udu5IxajsW4yw6A01kVtTdG1fYBoGt2kwdEShsJSNnU6qWPqg4jY4KQsavMzM5PIvgls2ymVmTawXy/YTzFND0U1h7VESXlco3rM2UvuJ6VRUhbXlQQgzo5mpAjxHUNbcB7QqzVg044rKs2QlA5oRoyRxYUUWYkDbYAApBVJyvrN7FGlhgITpEGwpCtGItTFbM1IAGqZmYJTGGmacFNAqrQUdUBJ0F5G5hbidYglwKriakESRhoL0fhOYLOmApu5rHXt7o8d3m9sNfzEBzzHqKoj/5VAtKiAqJPGqU0NlQrCmpXRI8rsC4f8hAByYwGywPnOfdc/vnrR8y8KnPkeOO19e6jKetVTfbU/WxBesbEE2ai0TEdDV8iTnFu1Q660pC3w9tnZHXExmFl8vvNH9z74LL3qJ9URUq6aeioJXjVDNZOWplP7TmpQW2E0FiFV1IiF+XqEVMA4qhlUzLbH4w5lEVKq86lyk/8GFcVhC1RRBYw1I25mxpbC+vk4bRFCKqARH7TvEdJ4DWAjIf6TjCihfjNJQ316ig6Be4egUcVW+0MQt7amhhxbDdkpg2mAJKfAiwA4IXB1Zisqc0IUjrVVmqQGC92jMYM6Toepa+pwEJiz/h+HpFHS+iGfD4ywWDVjtE9xwKLSuzZ36BBAW009Ru1eVT98IUJmXjjOPawKo8IBz+Y69IFXLCjMJ27a0dTm1utnei8J70RaqWJG1gnOg5LzYJbzoIvzYB7nwGwNRmMQ8lsQ8tuYLx0Mn4g8x52u3OHRmd2/Bva/zdIVBpsl08Tn48LO4vTc28BXBiAHQZqpWdH16bs+c6i5BNzcGipFywNqoDDocqkqAiMFIqlXrTt45d2zR0bMXPgxEhiXFcZTYlQ3F2rONlUag6yZ237lEuVc74WRgB8zUhWqSAF3hsyzqPCMvOhuyWRK1EZZlExDHSg6LmXSWzXrt82h3Az3iFrsGoRzoLIKGORJ9vYu68lfv/z2en79ZPJxnStxd1HW4soBuFkdj/YZOd1vtykFvN8d63j8iWsHe7jiAWOZ14EB7MMQ+woEvWk/8uLe1954/dQv/DljMVJupfrAQ9Jclhnvw58P2n+wYSDjBEJI52GZRJZks4iFJy/P8Qb5RIecBqw7gR1G0oxpcU3p0yl6yr/rbll77s+H64W5dZgRyrPJ6ptvkJuft5k8eGn3PxfxbXwq3mc5/eXQRJKcmdv5DttWhpGP9GdfWXj8x8qT4PmQ4hxtH+VZi3B9EG7AdVwXa3i/tmDtVuyahbYW8PlSZ/yFsQVv3pwvdofGvtwU3nRV5IGsL+IeSz3ywE7FFSbrnk3+w99c/oKfVHSTen7pKwbbqUCGAzfohmvbCcvJKJmet55/BYv7piUbawsK48B1bGEU5PIPjBEbx9XC8YXjgCLqUEnroM0lpKRNwg/h6kwL+1lpH+GD2zjJMt4vx24FV6QfhysZqdKSyRRDs/MDVsHMkMhl8A9nGhkJyhQ4ZNoD1M5mwkiGMpxJiTspL5CQbF5hZhPBiv2GrBDVKMQCaPOB+aMSPuIhxCe8hYCIqrBsbRDCI53d1I+bVsnNHpZwxLUpiAjMcqqYhzt12FoSMsKgrD/oodHPvx88PCpCSRRpy8bVSW4aUahxgadznabhlKXFTuEUW946O3L+6ZGDoohpyC85Wo1U8vSv3nspeOzyRY9LqqLPNHWqGF5KnoP6WImBRUjtRQm/7aHkTm8ll+BwK8OLCwts/LUp6ysd7e3R3q5IdyunapPiIribkRIocifk6F4McvDjZQLW/t6Do55iHGG3M4+bCofag5osqcFvA7mMBKGqpmwbr+rWNFVT4KZdLmTw1ir0Vt2EF4pw1fQELsfjJudt/K9cFl5flfBLLllcycuXYWzDpGqK1txY1hWegYXetXCiwpt71qkDoydi7U+t9cu02g7SMtNao9NBqrv4q0Y/Hfew28afG7kcefnqwo3hgTcTwk8XF5xciP3MtrGLdy5XH/OTkmwyHPfGySdqyU+B1TaFJ5qxPS8RLskaACOdJKCBI5W+JeHn3M6Uc8Fl2NH8TFEpSQ5JeKDQdrmryZetuea7tXQXeBm/8UTC2A31w0+H3zki9FP4JHIh/nXsjauvTF94hhdNpVjZcvkK35Ljn4p5L0AuXk1WJrwKSBe0ELjlgIRwc/d8ELU6JPo9KYPf+ZmcL58C/8vt05mwWTph2GTQEbFpnL149sHuvkxiOVD8UiyLa4bMA5BWynVqJMSjaxd2+9PZE/yCLMOgKIGwAIAINA2KlyMu9WUQRFmvmcHsl4gMRtqTa0twzU91eTBnrEjd9GiRtcew+wLIqCKHGcbqc5yL0kUwxSnSRXY7ih0U1uuFaQLSNIGsaQKujBbIeyUFcnFp5UczlDJkFSFlX5Pw6NSiGUmOSPjoxNHsFuNEkbWvYHeckcpM0eB11ZUOmlqsQBZ+PXxUhqBPwLLfTCCLpyuKO66gtKmXO70m4aWbJixxn2H/XBE5T2P3FLyRxD3fmxEXp096mWk2tP1wg7dJuGlqZkKS2yX8yOTM9K0ia7yiOQvRCk8MzR72NBJ4fMLLSBugPQBsPCPhwx+IkXCnhyRMTcFI3y8i5Q+wO8/IdGkkISxOftfLREugQV3doEoYnZqJkKRNwtZJSfAA3/UnRSR4CbsXIP8olqUPT+hczdCgnJo5KuH+qXGOJPskZFPg/OdFOP8ldi9DmZkyivPeBO1pQmatlHDe1HhHkrkSNkwuMH5bZO117F6F/MVM8WHYI+e7FsbVl14S3gLtNLD3RQnvn5qESDIi4dBNrZPht0Fe/65baoKKGGffLqKSd7D7A+aKvSlFVMDbvMQMQDsHJf2QhJ+amphI0iPhjskZ8t0iazewuw5FhKLuTWk27aRwy8a1RNRUBzjByTQkhrw7Fr/LzPf4biq/+KvhH9JTb7atbpzgm+nccf8HI+nOnKirnHNix6uidM18za+Kksp4Stfd3y9c43LLpnGNi1IlvmZYHLwHL7XJPIgYmeb6xSX+F9/BRxiZO9EOTHwD4mM3TQkjtfk0jBfWOHLjlYObCDz8VcEt2OTR/YnL0pSy8f+vxv4+50Z55fbL/OMf5uF7jtV9Y/2NtjUf7l6/+drxxfFdVx4f+suVfc27/nj+b9s23vrJ/wJh7LbfVxsAAA==";
}
