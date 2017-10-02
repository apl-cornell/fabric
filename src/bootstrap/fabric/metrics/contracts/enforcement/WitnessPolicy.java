package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.MetricContract;
import fabric.worker.transaction.TransactionManager;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * An {@link EnforcementPolicy} which enforces a {@link MetricContract} by
 * relying on a set of <em>witnesses</em>, other {@link MetricContract}s that in
 * conjunction imply the enforced {@link MetricContract}.
 */
public interface WitnessPolicy
  extends fabric.metrics.contracts.enforcement.EnforcementPolicy {
    public fabric.lang.arrays.ObjectArray get$witnesses();
    
    public fabric.lang.arrays.ObjectArray set$witnesses(
      fabric.lang.arrays.ObjectArray val);
    
    public boolean get$activated();
    
    public boolean set$activated(boolean val);
    
    /**
   * @param witnesses
   *        the array of {@link MetricContract}s used to enforce this
   *        policy. If any of the witnesses weren't already active, they
   *        are {@link Contract#activate() activated} here.
   */
    public fabric.metrics.contracts.enforcement.WitnessPolicy
      fabric$metrics$contracts$enforcement$WitnessPolicy$(
      fabric.lang.arrays.ObjectArray witnesses);
    
    public void activate();
    
    public static interface Activator
      extends java.util.concurrent.Callable, fabric.lang.Object {
        public fabric.metrics.contracts.enforcement.WitnessPolicy get$out$();
        
        public fabric.metrics.contracts.MetricContract get$w();
        
        public fabric.metrics.contracts.MetricContract set$w(
          fabric.metrics.contracts.MetricContract val);
        
        public Activator
          fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
          fabric.metrics.contracts.MetricContract w);
        
        public java.lang.Object call();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements Activator {
            public fabric.metrics.contracts.enforcement.WitnessPolicy get$out$(
              ) {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          Activator._Impl) fetch()).get$out$();
            }
            
            public fabric.metrics.contracts.MetricContract get$w() {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          Activator._Impl) fetch()).get$w();
            }
            
            public fabric.metrics.contracts.MetricContract set$w(
              fabric.metrics.contracts.MetricContract val) {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy.
                          Activator._Impl) fetch()).set$w(val);
            }
            
            public fabric.metrics.contracts.enforcement.WitnessPolicy.Activator
              fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
              fabric.metrics.contracts.MetricContract arg1) {
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
            public fabric.metrics.contracts.enforcement.WitnessPolicy get$out$(
              ) {
                return this.out$;
            }
            
            private fabric.metrics.contracts.enforcement.WitnessPolicy out$;
            
            public fabric.metrics.contracts.MetricContract get$w() {
                return this.w;
            }
            
            public fabric.metrics.contracts.MetricContract set$w(
              fabric.metrics.contracts.MetricContract val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.w = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            fabric.metrics.contracts.MetricContract w;
            
            public Activator
              fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
              fabric.metrics.contracts.MetricContract w) {
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
                    ((fabric.metrics.contracts.MetricContract._Proxy)
                       this.get$w()).activate$remote(worker, null);
                }
                else {
                    this.get$w().activate();
                }
                return null;
            }
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.metrics.contracts.enforcement.WitnessPolicy out$) {
                super($location);
                this.out$ = out$;
            }
            
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
                $writeRef($getStore(), this.out$, refTypes, out, intraStoreRefs,
                          interStoreRefs);
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
                this.out$ =
                  (fabric.
                    metrics.
                    contracts.
                    enforcement.
                    WitnessPolicy)
                    $readRef(
                      fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Proxy.class,
                      (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                      intraStoreRefs, interStoreRefs);
                this.w =
                  (fabric.metrics.contracts.MetricContract)
                    $readRef(
                      fabric.metrics.contracts.MetricContract._Proxy.class,
                      (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                      intraStoreRefs, interStoreRefs);
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
                this.out$ = src.out$;
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
        
        public static final byte[] $classHash = new byte[] { -58, -12, 39, 3,
        59, 97, 65, -107, -85, -11, 41, 79, 59, 26, 90, -35, -100, -80, 75,
        -102, 91, -116, 98, -90, 104, 87, 53, 65, 11, -81, -4, 121 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1506965809000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.MetricContract mc);
    
    public void unapply(fabric.metrics.contracts.MetricContract mc);
    
    public java.lang.String toString();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy
    extends fabric.metrics.contracts.enforcement.EnforcementPolicy._Proxy
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
          fabric.lang.arrays.ObjectArray arg1) {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      fetch()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(arg1);
        }
        
        public _Proxy(WitnessPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl
    extends fabric.metrics.contracts.enforcement.EnforcementPolicy._Impl
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
   *        the array of {@link MetricContract}s used to enforce this
   *        policy. If any of the witnesses weren't already active, they
   *        are {@link Contract#activate() activated} here.
   */
        public fabric.metrics.contracts.enforcement.WitnessPolicy
          fabric$metrics$contracts$enforcement$WitnessPolicy$(
          fabric.lang.arrays.ObjectArray witnesses) {
            fabric$metrics$contracts$enforcement$EnforcementPolicy$();
            this.
              set$witnesses(
                (fabric.lang.arrays.ObjectArray)
                  new fabric.lang.arrays.ObjectArray._Impl(this.$getStore()).
                  fabric$lang$arrays$ObjectArray$(
                    this.get$$updateLabel(),
                    this.get$$updateLabel().
                        confPolicy(),
                    fabric.metrics.contracts.MetricContract._Proxy.class,
                    witnesses.get$length()).$getProxy());
            fabric.util.Arrays._Impl.arraycopy(witnesses, 0,
                                               this.get$witnesses(), 0,
                                               witnesses.get$length());
            this.set$activated(false);
            return (fabric.metrics.contracts.enforcement.WitnessPolicy)
                     this.$getProxy();
        }
        
        public void activate() {
            {
                fabric.worker.transaction.TransactionManager $tm253 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff254 = 1;
                boolean $doBackoff255 = true;
                $label249: for (boolean $commit250 = false; !$commit250; ) {
                    if ($doBackoff255) {
                        if ($backoff254 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff254);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e251) {
                                    
                                }
                            }
                        }
                        if ($backoff254 < 5000) $backoff254 *= 1;
                    }
                    $doBackoff255 = $backoff254 <= 32 || !$doBackoff255;
                    $commit250 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (this.get$activated()) return; }
                    catch (final fabric.worker.RetryException $e251) {
                        $commit250 = false;
                        continue $label249;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e251) {
                        $commit250 = false;
                        fabric.common.TransactionID $currentTid252 =
                          $tm253.getCurrentTid();
                        if ($e251.tid.isDescendantOf($currentTid252))
                            continue $label249;
                        if ($currentTid252.parent != null) throw $e251;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e251) {
                        $commit250 = false;
                        if ($tm253.checkForStaleObjects()) continue $label249;
                        throw new fabric.worker.AbortException($e251);
                    }
                    finally {
                        if ($commit250) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e251) {
                                $commit250 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e251) {
                                $commit250 = false;
                                fabric.common.TransactionID $currentTid252 =
                                  $tm253.getCurrentTid();
                                if ($currentTid252 != null) {
                                    if ($e251.tid.equals($currentTid252) ||
                                          !$e251.tid.isDescendantOf(
                                                       $currentTid252)) {
                                        throw $e251;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit250) {
                            {  }
                            continue $label249;
                        }
                    }
                }
            }
            if (!fabric.lang.Object._Proxy.
                  idEquals(
                    fabric.worker.transaction.TransactionManager.getInstance().
                        getCurrentLog(),
                    null)) {
                for (int i = 0; i < this.get$witnesses().get$length(); i++) {
                    ((fabric.metrics.contracts.MetricContract)
                       this.get$witnesses().get(i)).activate();
                }
            }
            else {
                java.util.concurrent.Future[] futures =
                  new java.util.concurrent.Future[this.get$witnesses(
                                                         ).get$length()];
                for (int i = 0; i < futures.length; i++) {
                    final fabric.metrics.contracts.MetricContract w =
                      (fabric.metrics.contracts.MetricContract)
                        this.get$witnesses().get(i);
                    java.util.concurrent.Callable c = null;
                    {
                        java.util.concurrent.Callable c$var256 = c;
                        int i$var257 = i;
                        fabric.worker.transaction.TransactionManager $tm262 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff263 = 1;
                        boolean $doBackoff264 = true;
                        $label258: for (boolean $commit259 = false; !$commit259;
                                        ) {
                            if ($doBackoff264) {
                                if ($backoff263 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff263);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e260) {
                                            
                                        }
                                    }
                                }
                                if ($backoff263 < 5000) $backoff263 *= 1;
                            }
                            $doBackoff264 = $backoff263 <= 32 || !$doBackoff264;
                            $commit259 = true;
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
                                           this.$getStore(),
                                           (fabric.metrics.contracts.enforcement.WitnessPolicy)
                                             this.$getProxy()).
                                         $getProxy()).
                                          fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
                                            w));
                            }
                            catch (final fabric.worker.RetryException $e260) {
                                $commit259 = false;
                                continue $label258;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e260) {
                                $commit259 = false;
                                fabric.common.TransactionID $currentTid261 =
                                  $tm262.getCurrentTid();
                                if ($e260.tid.isDescendantOf($currentTid261))
                                    continue $label258;
                                if ($currentTid261.parent != null) throw $e260;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e260) {
                                $commit259 = false;
                                if ($tm262.checkForStaleObjects())
                                    continue $label258;
                                throw new fabric.worker.AbortException($e260);
                            }
                            finally {
                                if ($commit259) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e260) {
                                        $commit259 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e260) {
                                        $commit259 = false;
                                        fabric.common.TransactionID
                                          $currentTid261 =
                                          $tm262.getCurrentTid();
                                        if ($currentTid261 != null) {
                                            if ($e260.tid.equals(
                                                            $currentTid261) ||
                                                  !$e260.tid.
                                                  isDescendantOf(
                                                    $currentTid261)) {
                                                throw $e260;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit259) {
                                    {
                                        c = c$var256;
                                        i = i$var257;
                                    }
                                    continue $label258;
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
            {
                fabric.worker.transaction.TransactionManager $tm269 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff270 = 1;
                boolean $doBackoff271 = true;
                $label265: for (boolean $commit266 = false; !$commit266; ) {
                    if ($doBackoff271) {
                        if ($backoff270 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff270);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e267) {
                                    
                                }
                            }
                        }
                        if ($backoff270 < 5000) $backoff270 *= 1;
                    }
                    $doBackoff271 = $backoff270 <= 32 || !$doBackoff271;
                    $commit266 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.set$activated(true); }
                    catch (final fabric.worker.RetryException $e267) {
                        $commit266 = false;
                        continue $label265;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e267) {
                        $commit266 = false;
                        fabric.common.TransactionID $currentTid268 =
                          $tm269.getCurrentTid();
                        if ($e267.tid.isDescendantOf($currentTid268))
                            continue $label265;
                        if ($currentTid268.parent != null) throw $e267;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e267) {
                        $commit266 = false;
                        if ($tm269.checkForStaleObjects()) continue $label265;
                        throw new fabric.worker.AbortException($e267);
                    }
                    finally {
                        if ($commit266) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e267) {
                                $commit266 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e267) {
                                $commit266 = false;
                                fabric.common.TransactionID $currentTid268 =
                                  $tm269.getCurrentTid();
                                if ($currentTid268 != null) {
                                    if ($e267.tid.equals($currentTid268) ||
                                          !$e267.tid.isDescendantOf(
                                                       $currentTid268)) {
                                        throw $e267;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit266) {
                            {  }
                            continue $label265;
                        }
                    }
                }
            }
        }
        
        public long expiry() {
            long expiry = -1;
            boolean atLeastOnce = false;
            {
                long expiry$var272 = expiry;
                boolean atLeastOnce$var273 = atLeastOnce;
                fabric.worker.transaction.TransactionManager $tm278 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff279 = 1;
                boolean $doBackoff280 = true;
                $label274: for (boolean $commit275 = false; !$commit275; ) {
                    if ($doBackoff280) {
                        if ($backoff279 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff279);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e276) {
                                    
                                }
                            }
                        }
                        if ($backoff279 < 5000) $backoff279 *= 1;
                    }
                    $doBackoff280 = $backoff279 <= 32 || !$doBackoff280;
                    $commit275 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        for (int i = 0; i < this.get$witnesses().get$length();
                             i++) {
                            if (!atLeastOnce ||
                                  ((fabric.metrics.contracts.MetricContract)
                                     this.get$witnesses().get(i)).getExpiry() <
                                  expiry) {
                                atLeastOnce = true;
                                expiry =
                                  ((fabric.metrics.contracts.MetricContract)
                                     this.get$witnesses().get(i)).getExpiry();
                            }
                        }
                    }
                    catch (final fabric.worker.RetryException $e276) {
                        $commit275 = false;
                        continue $label274;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e276) {
                        $commit275 = false;
                        fabric.common.TransactionID $currentTid277 =
                          $tm278.getCurrentTid();
                        if ($e276.tid.isDescendantOf($currentTid277))
                            continue $label274;
                        if ($currentTid277.parent != null) throw $e276;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e276) {
                        $commit275 = false;
                        if ($tm278.checkForStaleObjects()) continue $label274;
                        throw new fabric.worker.AbortException($e276);
                    }
                    finally {
                        if ($commit275) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e276) {
                                $commit275 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e276) {
                                $commit275 = false;
                                fabric.common.TransactionID $currentTid277 =
                                  $tm278.getCurrentTid();
                                if ($currentTid277 != null) {
                                    if ($e276.tid.equals($currentTid277) ||
                                          !$e276.tid.isDescendantOf(
                                                       $currentTid277)) {
                                        throw $e276;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit275) {
                            {
                                expiry = expiry$var272;
                                atLeastOnce = atLeastOnce$var273;
                            }
                            continue $label274;
                        }
                    }
                }
            }
            return expiry;
        }
        
        public void apply(fabric.metrics.contracts.MetricContract mc) {
            if (!this.get$activated()) activate();
            for (int i = 0; i < this.get$witnesses().get$length(); i++) {
                ((fabric.metrics.contracts.MetricContract)
                   this.get$witnesses().get(i)).
                  addObserver(
                    (fabric.metrics.util.Observer)
                      fabric.lang.Object._Proxy.$getProxy(mc.fetch()));
            }
            fabric.common.Logging.METRICS_LOGGER.
              finer(
                "DEFENDING " +
                  java.lang.String.
                    valueOf(fabric.lang.WrappedJavaInlineable.$unwrap(mc)) +
                  " WITH " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap(
                            (fabric.metrics.contracts.enforcement.WitnessPolicy)
                              this.$getProxy())));
        }
        
        public void unapply(fabric.metrics.contracts.MetricContract mc) {
            for (int i = 0; i < this.get$witnesses().get$length(); i++) {
                ((fabric.metrics.contracts.MetricContract)
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
                        fabric.worker.transaction.TransactionManager $tm285 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff286 = 1;
                        boolean $doBackoff287 = true;
                        $label281: for (boolean $commit282 = false; !$commit282;
                                        ) {
                            if ($doBackoff287) {
                                if ($backoff286 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff286);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e283) {
                                            
                                        }
                                    }
                                }
                                if ($backoff286 < 5000) $backoff286 *= 1;
                            }
                            $doBackoff287 = $backoff286 <= 32 || !$doBackoff287;
                            $commit282 = true;
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
                            catch (final fabric.worker.RetryException $e283) {
                                $commit282 = false;
                                continue $label281;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e283) {
                                $commit282 = false;
                                fabric.common.TransactionID $currentTid284 =
                                  $tm285.getCurrentTid();
                                if ($e283.tid.isDescendantOf($currentTid284))
                                    continue $label281;
                                if ($currentTid284.parent != null) throw $e283;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e283) {
                                $commit282 = false;
                                if ($tm285.checkForStaleObjects())
                                    continue $label281;
                                throw new fabric.worker.AbortException($e283);
                            }
                            finally {
                                if ($commit282) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e283) {
                                        $commit282 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e283) {
                                        $commit282 = false;
                                        fabric.common.TransactionID
                                          $currentTid284 =
                                          $tm285.getCurrentTid();
                                        if ($currentTid284 != null) {
                                            if ($e283.tid.equals(
                                                            $currentTid284) ||
                                                  !$e283.tid.
                                                  isDescendantOf(
                                                    $currentTid284)) {
                                                throw $e283;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit282) {
                                    {  }
                                    continue $label281;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 30, -39, -5, 89, 28,
    -6, 69, 111, 69, -57, 22, 25, 72, -64, -115, 86, 56, 81, -56, -50, -20, -82,
    115, -119, 92, 40, -90, 85, 99, 74, 4, 111 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506965809000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
