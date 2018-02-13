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
        
        public static final byte[] $classHash = new byte[] { 4, -34, -21, -68,
        26, 97, -26, -72, -25, -94, 78, -2, 85, -87, 88, -96, -37, 79, 108,
        -125, 42, -41, 76, 27, 61, -101, -56, -95, 81, 68, -22, -67 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1518538111000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.MetricContract mc);
    
    public void unapply(fabric.metrics.contracts.MetricContract mc);
    
    public java.lang.String toString();
    
    public boolean equals(fabric.lang.Object other);
    
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
                    fabric.worker.transaction.TransactionManager $tm163 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled166 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff164 = 1;
                    boolean $doBackoff165 = true;
                    boolean $retry160 = true;
                    $label158: for (boolean $commit159 = false; !$commit159; ) {
                        if ($backoffEnabled166) {
                            if ($doBackoff165) {
                                if ($backoff164 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff164);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e161) {
                                            
                                        }
                                    }
                                }
                                if ($backoff164 < 5000) $backoff164 *= 2;
                            }
                            $doBackoff165 = $backoff164 <= 32 || !$doBackoff165;
                        }
                        $commit159 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.get$activated()) return; }
                        catch (final fabric.worker.RetryException $e161) {
                            $commit159 = false;
                            continue $label158;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e161) {
                            $commit159 = false;
                            fabric.common.TransactionID $currentTid162 =
                              $tm163.getCurrentTid();
                            if ($e161.tid.isDescendantOf($currentTid162))
                                continue $label158;
                            if ($currentTid162.parent != null) {
                                $retry160 = false;
                                throw $e161;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e161) {
                            $commit159 = false;
                            if ($tm163.checkForStaleObjects())
                                continue $label158;
                            $retry160 = false;
                            throw new fabric.worker.AbortException($e161);
                        }
                        finally {
                            if ($commit159) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e161) {
                                    $commit159 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e161) {
                                    $commit159 = false;
                                    fabric.common.TransactionID $currentTid162 =
                                      $tm163.getCurrentTid();
                                    if ($currentTid162 != null) {
                                        if ($e161.tid.equals($currentTid162) ||
                                              !$e161.tid.isDescendantOf(
                                                           $currentTid162)) {
                                            throw $e161;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit159 && $retry160) {
                                {  }
                                continue $label158;
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
                        java.util.concurrent.Callable c$var167 = c;
                        int i$var168 = i;
                        fabric.worker.transaction.TransactionManager $tm174 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled177 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff175 = 1;
                        boolean $doBackoff176 = true;
                        boolean $retry171 = true;
                        $label169: for (boolean $commit170 = false; !$commit170;
                                        ) {
                            if ($backoffEnabled177) {
                                if ($doBackoff176) {
                                    if ($backoff175 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff175);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e172) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff175 < 5000) $backoff175 *= 2;
                                }
                                $doBackoff176 = $backoff175 <= 32 ||
                                                  !$doBackoff176;
                            }
                            $commit170 = true;
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
                            catch (final fabric.worker.RetryException $e172) {
                                $commit170 = false;
                                continue $label169;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e172) {
                                $commit170 = false;
                                fabric.common.TransactionID $currentTid173 =
                                  $tm174.getCurrentTid();
                                if ($e172.tid.isDescendantOf($currentTid173))
                                    continue $label169;
                                if ($currentTid173.parent != null) {
                                    $retry171 = false;
                                    throw $e172;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e172) {
                                $commit170 = false;
                                if ($tm174.checkForStaleObjects())
                                    continue $label169;
                                $retry171 = false;
                                throw new fabric.worker.AbortException($e172);
                            }
                            finally {
                                if ($commit170) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e172) {
                                        $commit170 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e172) {
                                        $commit170 = false;
                                        fabric.common.TransactionID
                                          $currentTid173 =
                                          $tm174.getCurrentTid();
                                        if ($currentTid173 != null) {
                                            if ($e172.tid.equals(
                                                            $currentTid173) ||
                                                  !$e172.tid.
                                                  isDescendantOf(
                                                    $currentTid173)) {
                                                throw $e172;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit170 && $retry171) {
                                    {
                                        c = c$var167;
                                        i = i$var168;
                                    }
                                    continue $label169;
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
                    fabric.worker.transaction.TransactionManager $tm183 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled186 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff184 = 1;
                    boolean $doBackoff185 = true;
                    boolean $retry180 = true;
                    $label178: for (boolean $commit179 = false; !$commit179; ) {
                        if ($backoffEnabled186) {
                            if ($doBackoff185) {
                                if ($backoff184 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff184);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e181) {
                                            
                                        }
                                    }
                                }
                                if ($backoff184 < 5000) $backoff184 *= 2;
                            }
                            $doBackoff185 = $backoff184 <= 32 || !$doBackoff185;
                        }
                        $commit179 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.set$activated(true); }
                        catch (final fabric.worker.RetryException $e181) {
                            $commit179 = false;
                            continue $label178;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e181) {
                            $commit179 = false;
                            fabric.common.TransactionID $currentTid182 =
                              $tm183.getCurrentTid();
                            if ($e181.tid.isDescendantOf($currentTid182))
                                continue $label178;
                            if ($currentTid182.parent != null) {
                                $retry180 = false;
                                throw $e181;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e181) {
                            $commit179 = false;
                            if ($tm183.checkForStaleObjects())
                                continue $label178;
                            $retry180 = false;
                            throw new fabric.worker.AbortException($e181);
                        }
                        finally {
                            if ($commit179) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e181) {
                                    $commit179 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e181) {
                                    $commit179 = false;
                                    fabric.common.TransactionID $currentTid182 =
                                      $tm183.getCurrentTid();
                                    if ($currentTid182 != null) {
                                        if ($e181.tid.equals($currentTid182) ||
                                              !$e181.tid.isDescendantOf(
                                                           $currentTid182)) {
                                            throw $e181;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit179 && $retry180) {
                                {  }
                                continue $label178;
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
                    long expiry$var187 = expiry;
                    boolean atLeastOnce$var188 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm194 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled197 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff195 = 1;
                    boolean $doBackoff196 = true;
                    boolean $retry191 = true;
                    $label189: for (boolean $commit190 = false; !$commit190; ) {
                        if ($backoffEnabled197) {
                            if ($doBackoff196) {
                                if ($backoff195 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff195);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e192) {
                                            
                                        }
                                    }
                                }
                                if ($backoff195 < 5000) $backoff195 *= 2;
                            }
                            $doBackoff196 = $backoff195 <= 32 || !$doBackoff196;
                        }
                        $commit190 = true;
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
                        catch (final fabric.worker.RetryException $e192) {
                            $commit190 = false;
                            continue $label189;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e192) {
                            $commit190 = false;
                            fabric.common.TransactionID $currentTid193 =
                              $tm194.getCurrentTid();
                            if ($e192.tid.isDescendantOf($currentTid193))
                                continue $label189;
                            if ($currentTid193.parent != null) {
                                $retry191 = false;
                                throw $e192;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e192) {
                            $commit190 = false;
                            if ($tm194.checkForStaleObjects())
                                continue $label189;
                            $retry191 = false;
                            throw new fabric.worker.AbortException($e192);
                        }
                        finally {
                            if ($commit190) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e192) {
                                    $commit190 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e192) {
                                    $commit190 = false;
                                    fabric.common.TransactionID $currentTid193 =
                                      $tm194.getCurrentTid();
                                    if ($currentTid193 != null) {
                                        if ($e192.tid.equals($currentTid193) ||
                                              !$e192.tid.isDescendantOf(
                                                           $currentTid193)) {
                                            throw $e192;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit190 && $retry191) {
                                {
                                    expiry = expiry$var187;
                                    atLeastOnce = atLeastOnce$var188;
                                }
                                continue $label189;
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
                        fabric.worker.transaction.TransactionManager $tm203 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled206 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff204 = 1;
                        boolean $doBackoff205 = true;
                        boolean $retry200 = true;
                        $label198: for (boolean $commit199 = false; !$commit199;
                                        ) {
                            if ($backoffEnabled206) {
                                if ($doBackoff205) {
                                    if ($backoff204 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff204);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e201) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff204 < 5000) $backoff204 *= 2;
                                }
                                $doBackoff205 = $backoff204 <= 32 ||
                                                  !$doBackoff205;
                            }
                            $commit199 = true;
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
                            catch (final fabric.worker.RetryException $e201) {
                                $commit199 = false;
                                continue $label198;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e201) {
                                $commit199 = false;
                                fabric.common.TransactionID $currentTid202 =
                                  $tm203.getCurrentTid();
                                if ($e201.tid.isDescendantOf($currentTid202))
                                    continue $label198;
                                if ($currentTid202.parent != null) {
                                    $retry200 = false;
                                    throw $e201;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e201) {
                                $commit199 = false;
                                if ($tm203.checkForStaleObjects())
                                    continue $label198;
                                $retry200 = false;
                                throw new fabric.worker.AbortException($e201);
                            }
                            finally {
                                if ($commit199) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e201) {
                                        $commit199 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e201) {
                                        $commit199 = false;
                                        fabric.common.TransactionID
                                          $currentTid202 =
                                          $tm203.getCurrentTid();
                                        if ($currentTid202 != null) {
                                            if ($e201.tid.equals(
                                                            $currentTid202) ||
                                                  !$e201.tid.
                                                  isDescendantOf(
                                                    $currentTid202)) {
                                                throw $e201;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit199 && $retry200) {
                                    {  }
                                    continue $label198;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -74, 19, 108, -122, 74,
    9, -109, 122, -95, 8, 17, -49, 115, -110, -89, 125, 125, -38, -8, -77, -123,
    61, 31, 72, 104, 74, 3, 83, -124, 30, 21, -42 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518538111000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
