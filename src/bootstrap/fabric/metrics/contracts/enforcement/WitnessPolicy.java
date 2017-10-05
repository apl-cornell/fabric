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
        
        public static final byte[] $classHash = new byte[] { -94, 114, -99, 40,
        -90, -108, 5, -43, -11, -69, 74, 122, 114, 57, -30, -118, -74, 31, -52,
        66, 127, 70, 26, -99, 121, 24, -104, -78, 101, 40, -111, -39 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1507217540000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAAL1Xa2wUVRS+u7TbB5U+eBcopazIy50AiRGLRroKLCy0Uh6xBMrdmbvtwOzMcOcubFF8JUbjDxIVERLbHwZfWDEhAX8YEn74gEBMNL74oRITEgzygxiUH77OuTM7M7ttUf+4ydy5e++55557zne+e2b4Oql0OGnL0oxuJMSAzZzEKppJpbsod5iWNKjjbILRXnV8Rerw1be0liiJpkmdSk3L1FVq9JqOIBPSu+heqphMKJs3ptq3kRoVF66hTr8g0W0dBU5abcsY6DMs4W0yQv8ri5RDr+5oODmO1PeQet3sFlToatIyBSuIHlKXY7kM485KTWNaD2k0GdO6Gdepoe8HQcvsIU2O3mdSkefM2cgcy9iLgk1O3mZc7lkcRPMtMJvnVWFxML/BNT8vdENJ645oT5NYVmeG5uwhT5CKNKnMGrQPBKeki6dQpEZlFY6DeK0OZvIsVVlxScVu3dQEmV2+wj9xfB0IwNKqHBP9lr9VhUlhgDS5JhnU7FO6BdfNPhCttPKwiyDNYyoFoWqbqrtpH+sVZFq5XJc7BVI10i24RJDJ5WJSE8SsuSxmoWhd37Di4GPmGjNKImCzxlQD7a+GRS1lizayLOPMVJm7sG5h+jCdcub5KCEgPLlM2JX54PEbDy5uOXvOlZkxikxnZhdTRa96LDPh85nJBcvHoRnVtuXoCIWSk8uodnkz7QUb0D7F14iTieLk2Y2fPPrUcXYtSmpTJKZaRj4HqGpUrZytG4yvZibjVDAtRWqYqSXlfIpUQT+tm8wd7cxmHSZSpMKQQzFL/gcXZUEFuqgK+rqZtYp9m4p+2S/YhJBaeMg4eHYSUtcK7w0wFhGEKv1WjikZI8/2AbwVeBjlar8Cect1VXG4qvC8KXQQ8oYARfByFIC64FQVjsJgW66yHDOFslUXJnOcLsvQ1YEEGGf/H5sU8KQN+yIRCMJs1dJYhjoQUQ9dHV0GJNAay9AY71WNg2dSZOKZoxJhNZgVDiBb+jACqJhZzifhtYfyHQ/fONF7wUUnrvVcLMgK1/KEZ3nCtzwRsjxRYnl8pSr0vRS5gpM6zMsEMF0CmG44Ukgkh1LvSvjFHJmn/k51sNN9tkEFqM0VSCQijz1Jrpe4A9TsBjYCwqlb0L197c7n2yDyBXtfBcQdRePl6ReQVgp6FHKqV61/7uqv7x8+YAWJKEh8BD+MXIn53VbuQ26pTAP+DNQvbKWnes8ciEeRm2rQWRSADRzUUr5HSZ63FzkTvVGZJuPRB9TAqSLR1Yp+bu0LRiQ2JmDT5MIEnVVmoKTb+7vtwW8/+2mZvIiKzFwfovBuJtpDbIDK6mXeNwa+38QZA7nvjnS9/Mr157ZJx4PE3NE2jGObBBagHEHw7Lk9l374/tiX0SBYglTZHCHCCvIwjX/BLwLPn/hgTuMAvoHZkx6ftPqEYuPW8wLjgFoMoDew3YlvNnOWpmd1mjEYQuX3+juXnPr5YIMbbwNGXO9xsvifFQTj0zvIUxd2/NYi1URUvNoCBwZiLl9ODDSv5JwOoB2Fp7+YdfRTOgjQB7Zz9P1MEljEQy8aNVmQpf8923Bls0TAUqnlbtkuQedJ3UTO3YNNm+vtmXI86oy8e1bhJR6AuUcZfq05+cA1l1J8MKOOOaNQyhYayrOlx3M3o22xj6Okqoc0yPqBmmILBbIEHPVABeAkvcE0uaNkvvQ2d6+udj9ZZ5YnUmjb8jQKqAz6KI39WjdzXOAV75AZ8HRCv8N7L8HZiTa2kwoRIjsr5JK5sp2HzYIimmv0XC4vEDFS9yIBcS2G9K4xQ7pejiS9/zKObiJje69vXxXaF4HnCCGNp+E9EYJ3cBT7Hhrdvih2FwrwjG5So2hgBdRGcewvk3sWbr8WuBqrS3dxCEQ+cGcFkIcDqnnOEahJahjoFSk1HfyEFG9YUAYXCgC/WWPVS7LWO/bMoSGt840lblXTVFqDPGzmc+99/cfFxJHL50e5tWJe9VsK9zkjqvb1spYMUHv52qzlyd1X+tw9Z5fZVy79zvrh86vnqS9FyTgfniMK2NJF7aWgrOUM6m9zUwk0W/3Qj8fQa/A8ApB823t3hkMfAGY0XMbsfAY4ouArlIRQ6yna4L3XhBSWUYhHT/i3Q+5Fb8MxKjY9gtzvIj7uIT7uIz4eIrH4GCVDPDjR1lI/tMGzFfpT3HftX2P4AZvtI0+MS/703rfGPnH4QLtuMyfrd6CqCgCzUcyCBpkFSFsJl7ZwvKsAwPfPJ1MOsDhjlHLO+xBRkx+xY1fWLZ48Rik3bcSnobfuxFB99dShzd/I4sP/yKiBuz2bN4wwF4b6MZuzrC7PVOMyoy1fUCTE/81tJMj40D/piT2uhr3wXTGWBuHeJ7IfXjMAH8ela4T84sNeWO5xALcrh/8O2D57hppiVJo8haG4FAmptOaUmpvzHD/Bh3+ZeitWvemyrFog6K2v88H5bx6q/Ormh2v38+U/vnB69sWOJ1c1Dw5MO3KSzX/x0t9G6kWnGhAAAA==";
    }
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.MetricContract mc);
    
    public void unapply(fabric.metrics.contracts.MetricContract mc);
    
    public java.lang.String toString();
    
    public boolean equals(fabric.lang.Object other);
    
    public void acquireReconfigLocks();
    
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
                fabric.worker.transaction.TransactionManager $tm448 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled451 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff449 = 1;
                boolean $doBackoff450 = true;
                $label444: for (boolean $commit445 = false; !$commit445; ) {
                    if ($backoffEnabled451) {
                        if ($doBackoff450) {
                            if ($backoff449 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff449);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e446) {
                                        
                                    }
                                }
                            }
                            if ($backoff449 < 5000) $backoff449 *= 2;
                        }
                        $doBackoff450 = $backoff449 <= 32 || !$doBackoff450;
                    }
                    $commit445 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (this.get$activated()) return; }
                    catch (final fabric.worker.RetryException $e446) {
                        $commit445 = false;
                        continue $label444;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e446) {
                        $commit445 = false;
                        fabric.common.TransactionID $currentTid447 =
                          $tm448.getCurrentTid();
                        if ($e446.tid.isDescendantOf($currentTid447))
                            continue $label444;
                        if ($currentTid447.parent != null) throw $e446;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e446) {
                        $commit445 = false;
                        if ($tm448.checkForStaleObjects()) continue $label444;
                        throw new fabric.worker.AbortException($e446);
                    }
                    finally {
                        if ($commit445) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e446) {
                                $commit445 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e446) {
                                $commit445 = false;
                                fabric.common.TransactionID $currentTid447 =
                                  $tm448.getCurrentTid();
                                if ($currentTid447 != null) {
                                    if ($e446.tid.equals($currentTid447) ||
                                          !$e446.tid.isDescendantOf(
                                                       $currentTid447)) {
                                        throw $e446;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit445) {
                            {  }
                            continue $label444;
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
                        java.util.concurrent.Callable c$var452 = c;
                        int i$var453 = i;
                        fabric.worker.transaction.TransactionManager $tm458 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled461 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff459 = 1;
                        boolean $doBackoff460 = true;
                        $label454: for (boolean $commit455 = false; !$commit455;
                                        ) {
                            if ($backoffEnabled461) {
                                if ($doBackoff460) {
                                    if ($backoff459 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff459);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e456) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff459 < 5000) $backoff459 *= 2;
                                }
                                $doBackoff460 = $backoff459 <= 32 ||
                                                  !$doBackoff460;
                            }
                            $commit455 = true;
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
                            catch (final fabric.worker.RetryException $e456) {
                                $commit455 = false;
                                continue $label454;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e456) {
                                $commit455 = false;
                                fabric.common.TransactionID $currentTid457 =
                                  $tm458.getCurrentTid();
                                if ($e456.tid.isDescendantOf($currentTid457))
                                    continue $label454;
                                if ($currentTid457.parent != null) throw $e456;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e456) {
                                $commit455 = false;
                                if ($tm458.checkForStaleObjects())
                                    continue $label454;
                                throw new fabric.worker.AbortException($e456);
                            }
                            finally {
                                if ($commit455) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e456) {
                                        $commit455 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e456) {
                                        $commit455 = false;
                                        fabric.common.TransactionID
                                          $currentTid457 =
                                          $tm458.getCurrentTid();
                                        if ($currentTid457 != null) {
                                            if ($e456.tid.equals(
                                                            $currentTid457) ||
                                                  !$e456.tid.
                                                  isDescendantOf(
                                                    $currentTid457)) {
                                                throw $e456;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit455) {
                                    {
                                        c = c$var452;
                                        i = i$var453;
                                    }
                                    continue $label454;
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
                fabric.worker.transaction.TransactionManager $tm466 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled469 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff467 = 1;
                boolean $doBackoff468 = true;
                $label462: for (boolean $commit463 = false; !$commit463; ) {
                    if ($backoffEnabled469) {
                        if ($doBackoff468) {
                            if ($backoff467 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff467);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e464) {
                                        
                                    }
                                }
                            }
                            if ($backoff467 < 5000) $backoff467 *= 2;
                        }
                        $doBackoff468 = $backoff467 <= 32 || !$doBackoff468;
                    }
                    $commit463 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.set$activated(true); }
                    catch (final fabric.worker.RetryException $e464) {
                        $commit463 = false;
                        continue $label462;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e464) {
                        $commit463 = false;
                        fabric.common.TransactionID $currentTid465 =
                          $tm466.getCurrentTid();
                        if ($e464.tid.isDescendantOf($currentTid465))
                            continue $label462;
                        if ($currentTid465.parent != null) throw $e464;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e464) {
                        $commit463 = false;
                        if ($tm466.checkForStaleObjects()) continue $label462;
                        throw new fabric.worker.AbortException($e464);
                    }
                    finally {
                        if ($commit463) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e464) {
                                $commit463 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e464) {
                                $commit463 = false;
                                fabric.common.TransactionID $currentTid465 =
                                  $tm466.getCurrentTid();
                                if ($currentTid465 != null) {
                                    if ($e464.tid.equals($currentTid465) ||
                                          !$e464.tid.isDescendantOf(
                                                       $currentTid465)) {
                                        throw $e464;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit463) {
                            {  }
                            continue $label462;
                        }
                    }
                }
            }
        }
        
        public long expiry() {
            long expiry = -1;
            boolean atLeastOnce = false;
            {
                long expiry$var470 = expiry;
                boolean atLeastOnce$var471 = atLeastOnce;
                fabric.worker.transaction.TransactionManager $tm476 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled479 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff477 = 1;
                boolean $doBackoff478 = true;
                $label472: for (boolean $commit473 = false; !$commit473; ) {
                    if ($backoffEnabled479) {
                        if ($doBackoff478) {
                            if ($backoff477 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff477);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e474) {
                                        
                                    }
                                }
                            }
                            if ($backoff477 < 5000) $backoff477 *= 2;
                        }
                        $doBackoff478 = $backoff477 <= 32 || !$doBackoff478;
                    }
                    $commit473 = true;
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
                    catch (final fabric.worker.RetryException $e474) {
                        $commit473 = false;
                        continue $label472;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e474) {
                        $commit473 = false;
                        fabric.common.TransactionID $currentTid475 =
                          $tm476.getCurrentTid();
                        if ($e474.tid.isDescendantOf($currentTid475))
                            continue $label472;
                        if ($currentTid475.parent != null) throw $e474;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e474) {
                        $commit473 = false;
                        if ($tm476.checkForStaleObjects()) continue $label472;
                        throw new fabric.worker.AbortException($e474);
                    }
                    finally {
                        if ($commit473) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e474) {
                                $commit473 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e474) {
                                $commit473 = false;
                                fabric.common.TransactionID $currentTid475 =
                                  $tm476.getCurrentTid();
                                if ($currentTid475 != null) {
                                    if ($e474.tid.equals($currentTid475) ||
                                          !$e474.tid.isDescendantOf(
                                                       $currentTid475)) {
                                        throw $e474;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit473) {
                            {
                                expiry = expiry$var470;
                                atLeastOnce = atLeastOnce$var471;
                            }
                            continue $label472;
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
        
        public void acquireReconfigLocks() {
            for (int i = 0; i < this.get$witnesses().get$length(); i++) {
                ((fabric.metrics.contracts.MetricContract)
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
                        fabric.worker.transaction.TransactionManager $tm484 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled487 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff485 = 1;
                        boolean $doBackoff486 = true;
                        $label480: for (boolean $commit481 = false; !$commit481;
                                        ) {
                            if ($backoffEnabled487) {
                                if ($doBackoff486) {
                                    if ($backoff485 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff485);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e482) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff485 < 5000) $backoff485 *= 2;
                                }
                                $doBackoff486 = $backoff485 <= 32 ||
                                                  !$doBackoff486;
                            }
                            $commit481 = true;
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
                            catch (final fabric.worker.RetryException $e482) {
                                $commit481 = false;
                                continue $label480;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e482) {
                                $commit481 = false;
                                fabric.common.TransactionID $currentTid483 =
                                  $tm484.getCurrentTid();
                                if ($e482.tid.isDescendantOf($currentTid483))
                                    continue $label480;
                                if ($currentTid483.parent != null) throw $e482;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e482) {
                                $commit481 = false;
                                if ($tm484.checkForStaleObjects())
                                    continue $label480;
                                throw new fabric.worker.AbortException($e482);
                            }
                            finally {
                                if ($commit481) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e482) {
                                        $commit481 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e482) {
                                        $commit481 = false;
                                        fabric.common.TransactionID
                                          $currentTid483 =
                                          $tm484.getCurrentTid();
                                        if ($currentTid483 != null) {
                                            if ($e482.tid.equals(
                                                            $currentTid483) ||
                                                  !$e482.tid.
                                                  isDescendantOf(
                                                    $currentTid483)) {
                                                throw $e482;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit481) {
                                    {  }
                                    continue $label480;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -68, -35, -63, -35,
    -56, 80, 109, 106, -36, 121, 16, -85, -13, -75, -65, 18, -84, 96, 107, 18,
    -23, 26, 105, 22, 113, 17, 48, -94, 2, 17, 90, 40 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507217540000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
