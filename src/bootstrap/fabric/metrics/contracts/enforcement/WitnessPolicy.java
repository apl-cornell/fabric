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
                this.get$w().activate();
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
        
        public static final byte[] $classHash = new byte[] { -66, 109, -102, 61,
        -101, -82, 92, -47, -36, 122, 115, -26, -99, 65, -45, 81, 40, 83, 106,
        -4, 46, 14, 99, -90, -34, 30, -43, 88, 118, 27, -19, 30 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1504028847000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public void refresh();
    
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
                fabric.worker.transaction.TransactionManager $tm409 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff410 = 1;
                boolean $doBackoff411 = true;
                $label405: for (boolean $commit406 = false; !$commit406; ) {
                    if ($doBackoff411) {
                        if ($backoff410 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff410);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e407) {
                                    
                                }
                            }
                        }
                        if ($backoff410 < 5000) $backoff410 *= 1;
                    }
                    $doBackoff411 = $backoff410 <= 32 || !$doBackoff411;
                    $commit406 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (this.get$activated()) return; }
                    catch (final fabric.worker.RetryException $e407) {
                        $commit406 = false;
                        continue $label405;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e407) {
                        $commit406 = false;
                        fabric.common.TransactionID $currentTid408 =
                          $tm409.getCurrentTid();
                        if ($e407.tid.isDescendantOf($currentTid408))
                            continue $label405;
                        if ($currentTid408.parent != null) throw $e407;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e407) {
                        $commit406 = false;
                        if ($tm409.checkForStaleObjects()) continue $label405;
                        throw new fabric.worker.AbortException($e407);
                    }
                    finally {
                        if ($commit406) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e407) {
                                $commit406 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e407) {
                                $commit406 = false;
                                fabric.common.TransactionID $currentTid408 =
                                  $tm409.getCurrentTid();
                                if ($currentTid408 != null) {
                                    if ($e407.tid.equals($currentTid408) ||
                                          !$e407.tid.isDescendantOf(
                                                       $currentTid408)) {
                                        throw $e407;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit406) {
                            {  }
                            continue $label405;
                        }
                    }
                }
            }
            refresh();
        }
        
        public void refresh() {
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
                        java.util.concurrent.Callable c$var412 = c;
                        int i$var413 = i;
                        fabric.worker.transaction.TransactionManager $tm418 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff419 = 1;
                        boolean $doBackoff420 = true;
                        $label414: for (boolean $commit415 = false; !$commit415;
                                        ) {
                            if ($doBackoff420) {
                                if ($backoff419 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff419);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e416) {
                                            
                                        }
                                    }
                                }
                                if ($backoff419 < 5000) $backoff419 *= 1;
                            }
                            $doBackoff420 = $backoff419 <= 32 || !$doBackoff420;
                            $commit415 = true;
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
                            catch (final fabric.worker.RetryException $e416) {
                                $commit415 = false;
                                continue $label414;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e416) {
                                $commit415 = false;
                                fabric.common.TransactionID $currentTid417 =
                                  $tm418.getCurrentTid();
                                if ($e416.tid.isDescendantOf($currentTid417))
                                    continue $label414;
                                if ($currentTid417.parent != null) throw $e416;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e416) {
                                $commit415 = false;
                                if ($tm418.checkForStaleObjects())
                                    continue $label414;
                                throw new fabric.worker.AbortException($e416);
                            }
                            finally {
                                if ($commit415) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e416) {
                                        $commit415 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e416) {
                                        $commit415 = false;
                                        fabric.common.TransactionID
                                          $currentTid417 =
                                          $tm418.getCurrentTid();
                                        if ($currentTid417 != null) {
                                            if ($e416.tid.equals(
                                                            $currentTid417) ||
                                                  !$e416.tid.
                                                  isDescendantOf(
                                                    $currentTid417)) {
                                                throw $e416;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit415) {
                                    {
                                        c = c$var412;
                                        i = i$var413;
                                    }
                                    continue $label414;
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
                fabric.worker.transaction.TransactionManager $tm425 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff426 = 1;
                boolean $doBackoff427 = true;
                $label421: for (boolean $commit422 = false; !$commit422; ) {
                    if ($doBackoff427) {
                        if ($backoff426 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff426);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e423) {
                                    
                                }
                            }
                        }
                        if ($backoff426 < 5000) $backoff426 *= 1;
                    }
                    $doBackoff427 = $backoff426 <= 32 || !$doBackoff427;
                    $commit422 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.set$activated(true); }
                    catch (final fabric.worker.RetryException $e423) {
                        $commit422 = false;
                        continue $label421;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e423) {
                        $commit422 = false;
                        fabric.common.TransactionID $currentTid424 =
                          $tm425.getCurrentTid();
                        if ($e423.tid.isDescendantOf($currentTid424))
                            continue $label421;
                        if ($currentTid424.parent != null) throw $e423;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e423) {
                        $commit422 = false;
                        if ($tm425.checkForStaleObjects()) continue $label421;
                        throw new fabric.worker.AbortException($e423);
                    }
                    finally {
                        if ($commit422) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e423) {
                                $commit422 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e423) {
                                $commit422 = false;
                                fabric.common.TransactionID $currentTid424 =
                                  $tm425.getCurrentTid();
                                if ($currentTid424 != null) {
                                    if ($e423.tid.equals($currentTid424) ||
                                          !$e423.tid.isDescendantOf(
                                                       $currentTid424)) {
                                        throw $e423;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit422) {
                            {  }
                            continue $label421;
                        }
                    }
                }
            }
        }
        
        public long expiry() {
            long expiry = -1;
            boolean atLeastOnce = false;
            {
                long expiry$var428 = expiry;
                boolean atLeastOnce$var429 = atLeastOnce;
                fabric.worker.transaction.TransactionManager $tm434 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff435 = 1;
                boolean $doBackoff436 = true;
                $label430: for (boolean $commit431 = false; !$commit431; ) {
                    if ($doBackoff436) {
                        if ($backoff435 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff435);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e432) {
                                    
                                }
                            }
                        }
                        if ($backoff435 < 5000) $backoff435 *= 1;
                    }
                    $doBackoff436 = $backoff435 <= 32 || !$doBackoff436;
                    $commit431 = true;
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
                    catch (final fabric.worker.RetryException $e432) {
                        $commit431 = false;
                        continue $label430;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e432) {
                        $commit431 = false;
                        fabric.common.TransactionID $currentTid433 =
                          $tm434.getCurrentTid();
                        if ($e432.tid.isDescendantOf($currentTid433))
                            continue $label430;
                        if ($currentTid433.parent != null) throw $e432;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e432) {
                        $commit431 = false;
                        if ($tm434.checkForStaleObjects()) continue $label430;
                        throw new fabric.worker.AbortException($e432);
                    }
                    finally {
                        if ($commit431) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e432) {
                                $commit431 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e432) {
                                $commit431 = false;
                                fabric.common.TransactionID $currentTid433 =
                                  $tm434.getCurrentTid();
                                if ($currentTid433 != null) {
                                    if ($e432.tid.equals($currentTid433) ||
                                          !$e432.tid.isDescendantOf(
                                                       $currentTid433)) {
                                        throw $e432;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit431) {
                            {
                                expiry = expiry$var428;
                                atLeastOnce = atLeastOnce$var429;
                            }
                            continue $label430;
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
              fine(
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
                        fabric.worker.transaction.TransactionManager $tm441 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff442 = 1;
                        boolean $doBackoff443 = true;
                        $label437: for (boolean $commit438 = false; !$commit438;
                                        ) {
                            if ($doBackoff443) {
                                if ($backoff442 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff442);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e439) {
                                            
                                        }
                                    }
                                }
                                if ($backoff442 < 5000) $backoff442 *= 1;
                            }
                            $doBackoff443 = $backoff442 <= 32 || !$doBackoff443;
                            $commit438 = true;
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
                            catch (final fabric.worker.RetryException $e439) {
                                $commit438 = false;
                                continue $label437;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e439) {
                                $commit438 = false;
                                fabric.common.TransactionID $currentTid440 =
                                  $tm441.getCurrentTid();
                                if ($e439.tid.isDescendantOf($currentTid440))
                                    continue $label437;
                                if ($currentTid440.parent != null) throw $e439;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e439) {
                                $commit438 = false;
                                if ($tm441.checkForStaleObjects())
                                    continue $label437;
                                throw new fabric.worker.AbortException($e439);
                            }
                            finally {
                                if ($commit438) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e439) {
                                        $commit438 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e439) {
                                        $commit438 = false;
                                        fabric.common.TransactionID
                                          $currentTid440 =
                                          $tm441.getCurrentTid();
                                        if ($currentTid440 != null) {
                                            if ($e439.tid.equals(
                                                            $currentTid440) ||
                                                  !$e439.tid.
                                                  isDescendantOf(
                                                    $currentTid440)) {
                                                throw $e439;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit438) {
                                    {  }
                                    continue $label437;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 45, -54, 59, -37, -55,
    101, 116, 36, -16, 100, 109, -108, 109, -75, -49, 100, -75, -110, 5, -32,
    -81, 74, 104, -54, -110, -24, -105, 111, 53, -94, 83, 124 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1504028847000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
