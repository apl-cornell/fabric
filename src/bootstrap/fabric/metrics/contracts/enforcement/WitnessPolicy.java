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
   *        the array of {@link MetricContract}s used to enforce this
   *        policy. If any of the witnesses weren't already active, they
   *        are {@link Contract#activate() activated} here.
   */
    public fabric.metrics.contracts.enforcement.WitnessPolicy
      fabric$metrics$contracts$enforcement$WitnessPolicy$(
      fabric.metrics.contracts.MetricContract[] witnesses);
    
    public void activate();
    
    public static interface Activator
      extends java.util.concurrent.Callable, fabric.lang.Object {
        public fabric.metrics.contracts.MetricContract get$w();
        
        public fabric.metrics.contracts.MetricContract set$w(
          fabric.metrics.contracts.MetricContract val);
        
        public Activator
          fabric$metrics$contracts$enforcement$WitnessPolicy$Activator$(
          fabric.metrics.contracts.MetricContract w);
        
        public java.lang.Object call();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements Activator {
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
        
        public static final byte[] $classHash = new byte[] { -39, -53, -94, 118,
        28, 12, 109, -117, -62, -7, -23, 65, -72, 104, -123, 59, 109, -97, 85,
        106, 126, 18, 18, 83, -32, -76, 57, -112, -104, 51, -128, 3 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1519057331000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.MetricContract mc);
    
    public void unapply(fabric.metrics.contracts.MetricContract mc);
    
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
          fabric.metrics.contracts.MetricContract[] arg1) {
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
        
        public void apply(fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.contracts.enforcement.WitnessPolicy) fetch()).
              apply(arg1);
        }
        
        public void unapply(fabric.metrics.contracts.MetricContract arg1) {
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
   *        the array of {@link MetricContract}s used to enforce this
   *        policy. If any of the witnesses weren't already active, they
   *        are {@link Contract#activate() activated} here.
   */
        public fabric.metrics.contracts.enforcement.WitnessPolicy
          fabric$metrics$contracts$enforcement$WitnessPolicy$(
          fabric.metrics.contracts.MetricContract[] witnesses) {
            fabric$lang$Object$();
            this.
              set$witnesses(
                (fabric.lang.arrays.ObjectArray)
                  new fabric.lang.arrays.ObjectArray._Impl(this.$getStore()).
                  fabric$lang$arrays$ObjectArray$(
                    this.get$$updateLabel(),
                    this.get$$updateLabel().
                        confPolicy(),
                    fabric.metrics.contracts.MetricContract._Proxy.class,
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
                    fabric.worker.transaction.TransactionManager $tm185 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled188 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff186 = 1;
                    boolean $doBackoff187 = true;
                    boolean $retry182 = true;
                    $label180: for (boolean $commit181 = false; !$commit181; ) {
                        if ($backoffEnabled188) {
                            if ($doBackoff187) {
                                if ($backoff186 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff186);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e183) {
                                            
                                        }
                                    }
                                }
                                if ($backoff186 < 5000) $backoff186 *= 2;
                            }
                            $doBackoff187 = $backoff186 <= 32 || !$doBackoff187;
                        }
                        $commit181 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
                        catch (final fabric.worker.RetryException $e183) {
                            $commit181 = false;
                            continue $label180;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e183) {
                            $commit181 = false;
                            fabric.common.TransactionID $currentTid184 =
                              $tm185.getCurrentTid();
                            if ($e183.tid.isDescendantOf($currentTid184))
                                continue $label180;
                            if ($currentTid184.parent != null) {
                                $retry182 = false;
                                throw $e183;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e183) {
                            $commit181 = false;
                            if ($tm185.checkForStaleObjects())
                                continue $label180;
                            $retry182 = false;
                            throw new fabric.worker.AbortException($e183);
                        }
                        finally {
                            if ($commit181) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e183) {
                                    $commit181 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e183) {
                                    $commit181 = false;
                                    fabric.common.TransactionID $currentTid184 =
                                      $tm185.getCurrentTid();
                                    if ($currentTid184 != null) {
                                        if ($e183.tid.equals($currentTid184) ||
                                              !$e183.tid.isDescendantOf(
                                                           $currentTid184)) {
                                            throw $e183;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit181 && $retry182) {
                                {  }
                                continue $label180;
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
                    ((fabric.metrics.contracts.MetricContract)
                       tmp.get$witnesses().get(i)).activate();
                }
            }
            else {
                java.util.concurrent.Future[] futures =
                  new java.util.concurrent.Future[tmp.get$witnesses(
                                                        ).get$length()];
                for (int i = 0; i < futures.length; i++) {
                    final fabric.metrics.contracts.MetricContract w =
                      (fabric.metrics.contracts.MetricContract)
                        tmp.get$witnesses().get(i);
                    java.util.concurrent.Callable c = null;
                    {
                        java.util.concurrent.Callable c$var189 = c;
                        int i$var190 = i;
                        fabric.worker.transaction.TransactionManager $tm196 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled199 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff197 = 1;
                        boolean $doBackoff198 = true;
                        boolean $retry193 = true;
                        $label191: for (boolean $commit192 = false; !$commit192;
                                        ) {
                            if ($backoffEnabled199) {
                                if ($doBackoff198) {
                                    if ($backoff197 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff197);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e194) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff197 < 5000) $backoff197 *= 2;
                                }
                                $doBackoff198 = $backoff197 <= 32 ||
                                                  !$doBackoff198;
                            }
                            $commit192 = true;
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
                            catch (final fabric.worker.RetryException $e194) {
                                $commit192 = false;
                                continue $label191;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e194) {
                                $commit192 = false;
                                fabric.common.TransactionID $currentTid195 =
                                  $tm196.getCurrentTid();
                                if ($e194.tid.isDescendantOf($currentTid195))
                                    continue $label191;
                                if ($currentTid195.parent != null) {
                                    $retry193 = false;
                                    throw $e194;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e194) {
                                $commit192 = false;
                                if ($tm196.checkForStaleObjects())
                                    continue $label191;
                                $retry193 = false;
                                throw new fabric.worker.AbortException($e194);
                            }
                            finally {
                                if ($commit192) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e194) {
                                        $commit192 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e194) {
                                        $commit192 = false;
                                        fabric.common.TransactionID
                                          $currentTid195 =
                                          $tm196.getCurrentTid();
                                        if ($currentTid195 != null) {
                                            if ($e194.tid.equals(
                                                            $currentTid195) ||
                                                  !$e194.tid.
                                                  isDescendantOf(
                                                    $currentTid195)) {
                                                throw $e194;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit192 && $retry193) {
                                    {
                                        c = c$var189;
                                        i = i$var190;
                                    }
                                    continue $label191;
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
                    fabric.worker.transaction.TransactionManager $tm205 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled208 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff206 = 1;
                    boolean $doBackoff207 = true;
                    boolean $retry202 = true;
                    $label200: for (boolean $commit201 = false; !$commit201; ) {
                        if ($backoffEnabled208) {
                            if ($doBackoff207) {
                                if ($backoff206 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff206);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e203) {
                                            
                                        }
                                    }
                                }
                                if ($backoff206 < 5000) $backoff206 *= 2;
                            }
                            $doBackoff207 = $backoff206 <= 32 || !$doBackoff207;
                        }
                        $commit201 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.set$activated(true); }
                        catch (final fabric.worker.RetryException $e203) {
                            $commit201 = false;
                            continue $label200;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e203) {
                            $commit201 = false;
                            fabric.common.TransactionID $currentTid204 =
                              $tm205.getCurrentTid();
                            if ($e203.tid.isDescendantOf($currentTid204))
                                continue $label200;
                            if ($currentTid204.parent != null) {
                                $retry202 = false;
                                throw $e203;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e203) {
                            $commit201 = false;
                            if ($tm205.checkForStaleObjects())
                                continue $label200;
                            $retry202 = false;
                            throw new fabric.worker.AbortException($e203);
                        }
                        finally {
                            if ($commit201) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e203) {
                                    $commit201 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e203) {
                                    $commit201 = false;
                                    fabric.common.TransactionID $currentTid204 =
                                      $tm205.getCurrentTid();
                                    if ($currentTid204 != null) {
                                        if ($e203.tid.equals($currentTid204) ||
                                              !$e203.tid.isDescendantOf(
                                                           $currentTid204)) {
                                            throw $e203;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit201 && $retry202) {
                                {  }
                                continue $label200;
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
                          ((fabric.metrics.contracts.MetricContract)
                             tmp.get$witnesses().get(i)).getExpiry() < expiry) {
                        atLeastOnce = true;
                        expiry = ((fabric.metrics.contracts.MetricContract)
                                    tmp.get$witnesses().get(i)).getExpiry();
                    }
                }
            }
            else {
                {
                    long expiry$var209 = expiry;
                    boolean atLeastOnce$var210 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm216 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled219 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff217 = 1;
                    boolean $doBackoff218 = true;
                    boolean $retry213 = true;
                    $label211: for (boolean $commit212 = false; !$commit212; ) {
                        if ($backoffEnabled219) {
                            if ($doBackoff218) {
                                if ($backoff217 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff217);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e214) {
                                            
                                        }
                                    }
                                }
                                if ($backoff217 < 5000) $backoff217 *= 2;
                            }
                            $doBackoff218 = $backoff217 <= 32 || !$doBackoff218;
                        }
                        $commit212 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            for (int i = 0;
                                 i < tmp.get$witnesses().get$length(); i++) {
                                if (!atLeastOnce ||
                                      ((fabric.metrics.contracts.MetricContract)
                                         tmp.get$witnesses().get(i)).getExpiry(
                                                                       ) <
                                      expiry) {
                                    atLeastOnce = true;
                                    expiry =
                                      ((fabric.metrics.contracts.MetricContract)
                                         tmp.get$witnesses().get(i)).getExpiry(
                                                                       );
                                }
                            }
                        }
                        catch (final fabric.worker.RetryException $e214) {
                            $commit212 = false;
                            continue $label211;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e214) {
                            $commit212 = false;
                            fabric.common.TransactionID $currentTid215 =
                              $tm216.getCurrentTid();
                            if ($e214.tid.isDescendantOf($currentTid215))
                                continue $label211;
                            if ($currentTid215.parent != null) {
                                $retry213 = false;
                                throw $e214;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e214) {
                            $commit212 = false;
                            if ($tm216.checkForStaleObjects())
                                continue $label211;
                            $retry213 = false;
                            throw new fabric.worker.AbortException($e214);
                        }
                        finally {
                            if ($commit212) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e214) {
                                    $commit212 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e214) {
                                    $commit212 = false;
                                    fabric.common.TransactionID $currentTid215 =
                                      $tm216.getCurrentTid();
                                    if ($currentTid215 != null) {
                                        if ($e214.tid.equals($currentTid215) ||
                                              !$e214.tid.isDescendantOf(
                                                           $currentTid215)) {
                                            throw $e214;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit212 && $retry213) {
                                {
                                    expiry = expiry$var209;
                                    atLeastOnce = atLeastOnce$var210;
                                }
                                continue $label211;
                            }
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
                        fabric.worker.transaction.TransactionManager $tm225 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled228 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff226 = 1;
                        boolean $doBackoff227 = true;
                        boolean $retry222 = true;
                        $label220: for (boolean $commit221 = false; !$commit221;
                                        ) {
                            if ($backoffEnabled228) {
                                if ($doBackoff227) {
                                    if ($backoff226 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff226);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e223) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff226 < 5000) $backoff226 *= 2;
                                }
                                $doBackoff227 = $backoff226 <= 32 ||
                                                  !$doBackoff227;
                            }
                            $commit221 = true;
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
                            catch (final fabric.worker.RetryException $e223) {
                                $commit221 = false;
                                continue $label220;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e223) {
                                $commit221 = false;
                                fabric.common.TransactionID $currentTid224 =
                                  $tm225.getCurrentTid();
                                if ($e223.tid.isDescendantOf($currentTid224))
                                    continue $label220;
                                if ($currentTid224.parent != null) {
                                    $retry222 = false;
                                    throw $e223;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e223) {
                                $commit221 = false;
                                if ($tm225.checkForStaleObjects())
                                    continue $label220;
                                $retry222 = false;
                                throw new fabric.worker.AbortException($e223);
                            }
                            finally {
                                if ($commit221) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e223) {
                                        $commit221 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e223) {
                                        $commit221 = false;
                                        fabric.common.TransactionID
                                          $currentTid224 =
                                          $tm225.getCurrentTid();
                                        if ($currentTid224 != null) {
                                            if ($e223.tid.equals(
                                                            $currentTid224) ||
                                                  !$e223.tid.
                                                  isDescendantOf(
                                                    $currentTid224)) {
                                                throw $e223;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit221 && $retry222) {
                                    {  }
                                    continue $label220;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -68, 7, -8, 99, 77,
    -35, 103, 11, -77, -71, 50, -75, 82, -116, 20, -126, 107, 82, -87, -120,
    -58, 9, -53, 103, -64, -61, -92, -70, 44, -12, 69, -90 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519057331000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
