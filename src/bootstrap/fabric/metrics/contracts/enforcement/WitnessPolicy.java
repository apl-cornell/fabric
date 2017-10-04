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
        public static final long jlc$SourceLastModified$fabil = 1506966071000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAAL1XW4wURRStmV1mn7IPHsICy7KMGB5OB4hGHDDujiAjA7uyPOIQWGq6a3Yberqb6hqYRTBqYvTDbKICPiJ84QtWMCbGLxI/fAZjojE+PlB+SDDIh/HFB6L3Vvd098zuov44SVfXVN26devec0/dHrtKpjicdOdpTjcSYsRmTmIdzaUz/ZQ7TEsZ1HG2wOig2lSbPnb5da0zSqIZ0qxS0zJ1lRqDpiPI1Mweup8qJhPK1s3p5A7SoOLC9dQZFiS6o7fESZdtGSNDhiW8TcbpP7pUOfLCrtZ3akhLlrTo5oCgQldTlilYSWRJc4EVcow7PZrGtCxpMxnTBhjXqaEfBEHLzJJ2Rx8yqShy5mxmjmXsR8F2p2gzLvcsD6L5FpjNi6qwOJjf6ppfFLqhZHRHJDMklteZoTn7yKOkNkOm5A06BIIzM+VTKFKjsg7HQbxRBzN5nqqsvKR2r25qgsyvXuGfOL4BBGBpXYGJYcvfqtakMEDaXZMMag4pA4Lr5hCITrGKsIsgHZMqBaF6m6p76RAbFGRWtVy/OwVSDdItuESQGdViUhPErKMqZqFoXd20evQRc70ZJRGwWWOqgfbXw6LOqkWbWZ5xZqrMXdi8JHOMzjz3dJQQEJ5RJezKvHfo5/uWdb7/iSszZwKZvtwepopB9WRu6hdzU4tX1aAZ9bbl6AiFipPLqPZ7M8mSDWif6WvEyUR58v3NHz382Cl2JUoa0ySmWkaxAKhqU62CrRuMP8BMxqlgWpo0MFNLyfk0qYN+RjeZO9qXzztMpEmtIYdilvwPLsqDCnRRHfR1M2+V+zYVw7JfsgkhjfCQGnh2E9LcBe9NMBYRhCrDVoEpOaPIDgC8FXgY5eqwAnnLdVVxuKrwoil0EPKGAEXwchSAuuBUFY7CYFuusgIzhbJdFyZznH7L0NWRBBhn/x+blPCkrQciEQjCfNXSWI46EFEPXb39BiTQesvQGB9UjdFzaTLt3EsSYQ2YFQ4gW/owAqiYW80n4bVHir1rfz4zeN5FJ671XCzIatfyhGd5wrc8EbI8UWF5vEcV+n6KXMFJM+ZlApguAUw3FiklUifSpyX8Yo7MU3+nZtjpHtugAtQWSiQSkceeLtdL3AFq9gIbAeE0Lx7Y+eDup7sh8iX7QC3EHUXj1ekXkFYaehRyalBteery72ePHbaCRBQkPo4fxq/E/O6u9iG3VKYBfwbql3TRdwfPHY5HkZsa0FkUgA0c1Fm9R0WeJ8ucid6YkiFN6ANq4FSZ6BrFMLcOBCMSG1OxaXdhgs6qMlDS7ZoB+/i3n/+4Ul5EZWZuCVH4ABPJEBugshaZ922B77dwxkDuwov9zx+9+tQO6XiQWDjRhnFsU8AClCMInvxk33c/fH/yq2gQLEHqbI4QYSV5mLa/4BeB5wY+mNM4gG9g9pTHJ10+odi49aLAOKAWA+gNbHfiW82Cpel5neYMhlC53nLb8nd/Gm11423AiOs9Tpb9s4JgfHYveez8rj86pZqIildb4MBAzOXLaYHmHs7pCNpRevzLeS99TI8D9IHtHP0gkwQW8dCLRs0QZMV/zzZc2SERsEJquUO2y9F5UjeRc3dh0+16e64cjzrj7551eIkHYM4qY690pO694lKKD2bUsWACStlGQ3m24lTht2h37MMoqcuSVlk/UFNso0CWgKMsVABOyhvMkFsq5itvc/fqSvrJOrc6kULbVqdRQGXQR2nsN7qZ4wKvfIfMgacP+r3eeznOTrOxnV6KENlZLZcslO0ibBaX0dygFwpFgYiRupcKiGs5pLdPGtKNciTl/ZdxdBMZ27t9++rQvgg8zxLSNgve0yB4oxPYd//E9kWxu0SAZ3STGmUDa6E2imN/pdyzdPO1wNVYXbqLQyDygTsvgDwcUC1yjkBNUcNAr0ip2eAnpHjDgjK4VAL4zZusXpK13sknjpzQ+l5d7lY17ZU1yFqzWHjr6z8/S7x48dMJbq2YV/1Wwn3BuKp9o6wlA9RevDJvVWrvpSF3z/lV9lVLv7lx7NMHFqnPRUmND89xBWzlomQlKBs5g/rb3FIBzS4/9E0Yeg2ehwCSb3jvvnDoA8BMhMuYXcwBR5R8hZIQGj1Fm7z3+pDCKgrx6An/9sq96E04RsUmK8gaF/FxD/FxH/HxEInFJykZ4sGJtlf6oRue7dCf6b4b/5rED9jsHH9iXHLDe1+b/MThA+25yZys34GqagHMRjkLWmUWIG0lXNrC8f4SAN8/n0w5wOKcCco570NETX3ATl7asGzGJKXcrHGfht66Myda6m89sfUbWXz4HxkNcLfni4YR5sJQP2ZzltflmRpcZrTlC4qE+L+5jQRpCv2TntjnatgP3xWTaRDufSL74TUj8HFcuUbILz7sheUOAbhdOfx32PbZM9SUo9LuKQzFpUxIlTWn1NxR5PgJPvbLrddi9VsuyqoFgt710a+31yRpz9HTvy3uS3ZkL7zy9oaXdzyTe214+509TWevj/wNjuMklxoQAAA=";
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
                fabric.worker.transaction.TransactionManager $tm393 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled396 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff394 = 1;
                boolean $doBackoff395 = true;
                $label389: for (boolean $commit390 = false; !$commit390; ) {
                    if ($backoffEnabled396) {
                        if ($doBackoff395) {
                            if ($backoff394 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff394);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e391) {
                                        
                                    }
                                }
                            }
                            if ($backoff394 < 5000) $backoff394 *= 2;
                        }
                        $doBackoff395 = $backoff394 <= 32 || !$doBackoff395;
                    }
                    $commit390 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (this.get$activated()) return; }
                    catch (final fabric.worker.RetryException $e391) {
                        $commit390 = false;
                        continue $label389;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e391) {
                        $commit390 = false;
                        fabric.common.TransactionID $currentTid392 =
                          $tm393.getCurrentTid();
                        if ($e391.tid.isDescendantOf($currentTid392))
                            continue $label389;
                        if ($currentTid392.parent != null) throw $e391;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e391) {
                        $commit390 = false;
                        if ($tm393.checkForStaleObjects()) continue $label389;
                        throw new fabric.worker.AbortException($e391);
                    }
                    finally {
                        if ($commit390) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e391) {
                                $commit390 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e391) {
                                $commit390 = false;
                                fabric.common.TransactionID $currentTid392 =
                                  $tm393.getCurrentTid();
                                if ($currentTid392 != null) {
                                    if ($e391.tid.equals($currentTid392) ||
                                          !$e391.tid.isDescendantOf(
                                                       $currentTid392)) {
                                        throw $e391;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit390) {
                            {  }
                            continue $label389;
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
                        java.util.concurrent.Callable c$var397 = c;
                        int i$var398 = i;
                        fabric.worker.transaction.TransactionManager $tm403 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled406 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff404 = 1;
                        boolean $doBackoff405 = true;
                        $label399: for (boolean $commit400 = false; !$commit400;
                                        ) {
                            if ($backoffEnabled406) {
                                if ($doBackoff405) {
                                    if ($backoff404 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff404);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e401) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff404 < 5000) $backoff404 *= 2;
                                }
                                $doBackoff405 = $backoff404 <= 32 ||
                                                  !$doBackoff405;
                            }
                            $commit400 = true;
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
                            catch (final fabric.worker.RetryException $e401) {
                                $commit400 = false;
                                continue $label399;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e401) {
                                $commit400 = false;
                                fabric.common.TransactionID $currentTid402 =
                                  $tm403.getCurrentTid();
                                if ($e401.tid.isDescendantOf($currentTid402))
                                    continue $label399;
                                if ($currentTid402.parent != null) throw $e401;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e401) {
                                $commit400 = false;
                                if ($tm403.checkForStaleObjects())
                                    continue $label399;
                                throw new fabric.worker.AbortException($e401);
                            }
                            finally {
                                if ($commit400) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e401) {
                                        $commit400 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e401) {
                                        $commit400 = false;
                                        fabric.common.TransactionID
                                          $currentTid402 =
                                          $tm403.getCurrentTid();
                                        if ($currentTid402 != null) {
                                            if ($e401.tid.equals(
                                                            $currentTid402) ||
                                                  !$e401.tid.
                                                  isDescendantOf(
                                                    $currentTid402)) {
                                                throw $e401;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit400) {
                                    {
                                        c = c$var397;
                                        i = i$var398;
                                    }
                                    continue $label399;
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
                fabric.worker.transaction.TransactionManager $tm411 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled414 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff412 = 1;
                boolean $doBackoff413 = true;
                $label407: for (boolean $commit408 = false; !$commit408; ) {
                    if ($backoffEnabled414) {
                        if ($doBackoff413) {
                            if ($backoff412 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff412);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e409) {
                                        
                                    }
                                }
                            }
                            if ($backoff412 < 5000) $backoff412 *= 2;
                        }
                        $doBackoff413 = $backoff412 <= 32 || !$doBackoff413;
                    }
                    $commit408 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.set$activated(true); }
                    catch (final fabric.worker.RetryException $e409) {
                        $commit408 = false;
                        continue $label407;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e409) {
                        $commit408 = false;
                        fabric.common.TransactionID $currentTid410 =
                          $tm411.getCurrentTid();
                        if ($e409.tid.isDescendantOf($currentTid410))
                            continue $label407;
                        if ($currentTid410.parent != null) throw $e409;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e409) {
                        $commit408 = false;
                        if ($tm411.checkForStaleObjects()) continue $label407;
                        throw new fabric.worker.AbortException($e409);
                    }
                    finally {
                        if ($commit408) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e409) {
                                $commit408 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e409) {
                                $commit408 = false;
                                fabric.common.TransactionID $currentTid410 =
                                  $tm411.getCurrentTid();
                                if ($currentTid410 != null) {
                                    if ($e409.tid.equals($currentTid410) ||
                                          !$e409.tid.isDescendantOf(
                                                       $currentTid410)) {
                                        throw $e409;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit408) {
                            {  }
                            continue $label407;
                        }
                    }
                }
            }
        }
        
        public long expiry() {
            long expiry = -1;
            boolean atLeastOnce = false;
            {
                long expiry$var415 = expiry;
                boolean atLeastOnce$var416 = atLeastOnce;
                fabric.worker.transaction.TransactionManager $tm421 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled424 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff422 = 1;
                boolean $doBackoff423 = true;
                $label417: for (boolean $commit418 = false; !$commit418; ) {
                    if ($backoffEnabled424) {
                        if ($doBackoff423) {
                            if ($backoff422 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff422);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e419) {
                                        
                                    }
                                }
                            }
                            if ($backoff422 < 5000) $backoff422 *= 2;
                        }
                        $doBackoff423 = $backoff422 <= 32 || !$doBackoff423;
                    }
                    $commit418 = true;
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
                    catch (final fabric.worker.RetryException $e419) {
                        $commit418 = false;
                        continue $label417;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e419) {
                        $commit418 = false;
                        fabric.common.TransactionID $currentTid420 =
                          $tm421.getCurrentTid();
                        if ($e419.tid.isDescendantOf($currentTid420))
                            continue $label417;
                        if ($currentTid420.parent != null) throw $e419;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e419) {
                        $commit418 = false;
                        if ($tm421.checkForStaleObjects()) continue $label417;
                        throw new fabric.worker.AbortException($e419);
                    }
                    finally {
                        if ($commit418) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e419) {
                                $commit418 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e419) {
                                $commit418 = false;
                                fabric.common.TransactionID $currentTid420 =
                                  $tm421.getCurrentTid();
                                if ($currentTid420 != null) {
                                    if ($e419.tid.equals($currentTid420) ||
                                          !$e419.tid.isDescendantOf(
                                                       $currentTid420)) {
                                        throw $e419;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit418) {
                            {
                                expiry = expiry$var415;
                                atLeastOnce = atLeastOnce$var416;
                            }
                            continue $label417;
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
                        fabric.worker.transaction.TransactionManager $tm429 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled432 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff430 = 1;
                        boolean $doBackoff431 = true;
                        $label425: for (boolean $commit426 = false; !$commit426;
                                        ) {
                            if ($backoffEnabled432) {
                                if ($doBackoff431) {
                                    if ($backoff430 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff430);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e427) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff430 < 5000) $backoff430 *= 2;
                                }
                                $doBackoff431 = $backoff430 <= 32 ||
                                                  !$doBackoff431;
                            }
                            $commit426 = true;
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
                            catch (final fabric.worker.RetryException $e427) {
                                $commit426 = false;
                                continue $label425;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e427) {
                                $commit426 = false;
                                fabric.common.TransactionID $currentTid428 =
                                  $tm429.getCurrentTid();
                                if ($e427.tid.isDescendantOf($currentTid428))
                                    continue $label425;
                                if ($currentTid428.parent != null) throw $e427;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e427) {
                                $commit426 = false;
                                if ($tm429.checkForStaleObjects())
                                    continue $label425;
                                throw new fabric.worker.AbortException($e427);
                            }
                            finally {
                                if ($commit426) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e427) {
                                        $commit426 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e427) {
                                        $commit426 = false;
                                        fabric.common.TransactionID
                                          $currentTid428 =
                                          $tm429.getCurrentTid();
                                        if ($currentTid428 != null) {
                                            if ($e427.tid.equals(
                                                            $currentTid428) ||
                                                  !$e427.tid.
                                                  isDescendantOf(
                                                    $currentTid428)) {
                                                throw $e427;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit426) {
                                    {  }
                                    continue $label425;
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
    public static final long jlc$SourceLastModified$fabil = 1506966071000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
