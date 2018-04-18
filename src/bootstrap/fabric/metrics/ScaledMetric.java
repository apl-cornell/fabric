package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.util.Iterator;
import fabric.util.LinkedHashMap;
import fabric.util.Map;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;

/**
 * A {@link DerivedMetric} for the scaled value of a given metric.
 */
public interface ScaledMetric extends fabric.metrics.DerivedMetric {
    public double get$scalar();
    
    public double set$scalar(double val);
    
    public double postInc$scalar();
    
    public double postDec$scalar();
    
    /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param scalar
     *            The coefficient as a double
     * @param term
     *            The {@link Metric} this applies to
     */
    public fabric.metrics.ScaledMetric fabric$metrics$ScaledMetric$(
      double scalar, fabric.metrics.Metric term);
    
    public double computePresetR();
    
    public double computePresetB();
    
    public double computePresetV();
    
    public double computePresetN();
    
    public double computeValue(boolean useWeakCache);
    
    public double computeVelocity(boolean useWeakCache);
    
    public double computeNoise(boolean useWeakCache);
    
    public java.lang.String toString();
    
    public fabric.metrics.DerivedMetric times(double otherScalar);
    
    /**
     * {@inheritDoc}
     * <p>
     * {@link ScaledMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      thresholdPolicy(double rate, double base, boolean useWeakCache,
                      final fabric.worker.Store s);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy extends fabric.metrics.DerivedMetric._Proxy
      implements fabric.metrics.ScaledMetric {
        public double get$scalar() {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).get$scalar();
        }
        
        public double set$scalar(double val) {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).set$scalar(
                                                                   val);
        }
        
        public double postInc$scalar() {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).postInc$scalar(
                                                                   );
        }
        
        public double postDec$scalar() {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).postDec$scalar(
                                                                   );
        }
        
        public fabric.metrics.ScaledMetric fabric$metrics$ScaledMetric$(
          double arg1, fabric.metrics.Metric arg2) {
            return ((fabric.metrics.ScaledMetric) fetch()).
              fabric$metrics$ScaledMetric$(arg1, arg2);
        }
        
        public _Proxy(ScaledMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.DerivedMetric._Impl
      implements fabric.metrics.ScaledMetric {
        public double get$scalar() { return this.scalar; }
        
        public double set$scalar(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.scalar = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$scalar() {
            double tmp = this.get$scalar();
            this.set$scalar((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$scalar() {
            double tmp = this.get$scalar();
            this.set$scalar((double) (tmp - 1));
            return tmp;
        }
        
        protected double scalar;
        
        /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param scalar
     *            The coefficient as a double
     * @param term
     *            The {@link Metric} this applies to
     */
        public fabric.metrics.ScaledMetric fabric$metrics$ScaledMetric$(
          double scalar, fabric.metrics.Metric term) {
            this.set$scalar((double) scalar);
            fabric$metrics$DerivedMetric$(new fabric.metrics.Metric[] { term });
            initialize();
            return (fabric.metrics.ScaledMetric) this.$getProxy();
        }
        
        public double computePresetR() {
            return this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).getPresetR();
        }
        
        public double computePresetB() {
            return this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).getPresetB();
        }
        
        public double computePresetV() {
            return this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).getPresetV();
        }
        
        public double computePresetN() {
            return this.get$scalar() * this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).getPresetN();
        }
        
        public double computeValue(boolean useWeakCache) {
            return this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).value(
                                                                  useWeakCache);
        }
        
        public double computeVelocity(boolean useWeakCache) {
            return this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).velocity(
                                                                  useWeakCache);
        }
        
        public double computeNoise(boolean useWeakCache) {
            return this.get$scalar() *
              this.get$scalar() *
              ((fabric.metrics.Metric)
                 this.get$terms().get(0)).noise(useWeakCache);
        }
        
        public java.lang.String toString() {
            return "(" +
            this.get$scalar() +
            "*" +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(
                                                    (fabric.metrics.Metric)
                                                      this.get$terms().get(
                                                                         0))) +
            ")@" +
            getStore();
        }
        
        public fabric.metrics.DerivedMetric times(double otherScalar) {
            return fabric.metrics.ScaledMetric._Impl.
              static_times((fabric.metrics.ScaledMetric) this.$getProxy(),
                           otherScalar);
        }
        
        private static fabric.metrics.DerivedMetric static_times(
          fabric.metrics.ScaledMetric tmp, double otherScalar) {
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.ScaledMetric)
                     new fabric.metrics.ScaledMetric._Impl(s).$getProxy()).
                    fabric$metrics$ScaledMetric$(otherScalar * tmp.get$scalar(),
                                                 (fabric.metrics.Metric)
                                                   tmp.get$terms().get(0));
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var386 = val;
                    fabric.worker.transaction.TransactionManager $tm392 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled395 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff393 = 1;
                    boolean $doBackoff394 = true;
                    boolean $retry389 = true;
                    $label387: for (boolean $commit388 = false; !$commit388; ) {
                        if ($backoffEnabled395) {
                            if ($doBackoff394) {
                                if ($backoff393 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff393);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e390) {
                                            
                                        }
                                    }
                                }
                                if ($backoff393 < 5000) $backoff393 *= 2;
                            }
                            $doBackoff394 = $backoff393 <= 32 || !$doBackoff394;
                        }
                        $commit388 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                otherScalar * tmp.get$scalar(),
                                                (fabric.metrics.Metric)
                                                  tmp.get$terms().get(0));
                        }
                        catch (final fabric.worker.RetryException $e390) {
                            $commit388 = false;
                            continue $label387;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e390) {
                            $commit388 = false;
                            fabric.common.TransactionID $currentTid391 =
                              $tm392.getCurrentTid();
                            if ($e390.tid.isDescendantOf($currentTid391))
                                continue $label387;
                            if ($currentTid391.parent != null) {
                                $retry389 = false;
                                throw $e390;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e390) {
                            $commit388 = false;
                            if ($tm392.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid391 =
                              $tm392.getCurrentTid();
                            if ($e390.tid.isDescendantOf($currentTid391)) {
                                $retry389 = true;
                            }
                            else if ($currentTid391.parent != null) {
                                $retry389 = false;
                                throw $e390;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e390) {
                            $commit388 = false;
                            if ($tm392.checkForStaleObjects())
                                continue $label387;
                            $retry389 = false;
                            throw new fabric.worker.AbortException($e390);
                        }
                        finally {
                            if ($commit388) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e390) {
                                    $commit388 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e390) {
                                    $commit388 = false;
                                    fabric.common.TransactionID $currentTid391 =
                                      $tm392.getCurrentTid();
                                    if ($currentTid391 != null) {
                                        if ($e390.tid.equals($currentTid391) ||
                                              !$e390.tid.isDescendantOf(
                                                           $currentTid391)) {
                                            throw $e390;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit388 && $retry389) {
                                { val = val$var386; }
                                continue $label387;
                            }
                        }
                    }
                }
            }
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        /**
     * {@inheritDoc}
     * <p>
     * {@link ScaledMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            return fabric.metrics.ScaledMetric._Impl.
              static_plus((fabric.metrics.ScaledMetric) this.$getProxy(),
                          other);
        }
        
        private static fabric.metrics.DerivedMetric static_plus(
          fabric.metrics.ScaledMetric tmp, fabric.metrics.Metric other) {
            final fabric.worker.Store s = tmp.$getStore();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.ScaledMetric &&
                  other.$getStore().equals(s) &&
                  ((fabric.metrics.Metric)
                     ((fabric.metrics.ScaledMetric)
                        fabric.lang.Object._Proxy.$getProxy(other)).get$terms(
                                                                      ).get(0)).
                  equals((fabric.metrics.Metric) tmp.get$terms().get(0))) {
                fabric.metrics.ScaledMetric that =
                  (fabric.metrics.ScaledMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                fabric.metrics.DerivedMetric val = null;
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    val =
                      ((fabric.metrics.ScaledMetric)
                         new fabric.metrics.ScaledMetric._Impl(s).$getProxy()).
                        fabric$metrics$ScaledMetric$(that.get$scalar() +
                                                         tmp.get$scalar(),
                                                     (fabric.metrics.Metric)
                                                       tmp.get$terms().get(0));
                }
                else {
                    {
                        fabric.metrics.DerivedMetric val$var396 = val;
                        fabric.worker.transaction.TransactionManager $tm402 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled405 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff403 = 1;
                        boolean $doBackoff404 = true;
                        boolean $retry399 = true;
                        $label397: for (boolean $commit398 = false; !$commit398;
                                        ) {
                            if ($backoffEnabled405) {
                                if ($doBackoff404) {
                                    if ($backoff403 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff403);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e400) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff403 < 5000) $backoff403 *= 2;
                                }
                                $doBackoff404 = $backoff403 <= 32 ||
                                                  !$doBackoff404;
                            }
                            $commit398 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                val =
                                  ((fabric.metrics.ScaledMetric)
                                     new fabric.metrics.ScaledMetric._Impl(s).
                                     $getProxy()).fabric$metrics$ScaledMetric$(
                                                    that.get$scalar() +
                                                        tmp.get$scalar(),
                                                    (fabric.metrics.Metric)
                                                      tmp.get$terms().get(0));
                            }
                            catch (final fabric.worker.RetryException $e400) {
                                $commit398 = false;
                                continue $label397;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e400) {
                                $commit398 = false;
                                fabric.common.TransactionID $currentTid401 =
                                  $tm402.getCurrentTid();
                                if ($e400.tid.isDescendantOf($currentTid401))
                                    continue $label397;
                                if ($currentTid401.parent != null) {
                                    $retry399 = false;
                                    throw $e400;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e400) {
                                $commit398 = false;
                                if ($tm402.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid401 =
                                  $tm402.getCurrentTid();
                                if ($e400.tid.isDescendantOf($currentTid401)) {
                                    $retry399 = true;
                                }
                                else if ($currentTid401.parent != null) {
                                    $retry399 = false;
                                    throw $e400;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e400) {
                                $commit398 = false;
                                if ($tm402.checkForStaleObjects())
                                    continue $label397;
                                $retry399 = false;
                                throw new fabric.worker.AbortException($e400);
                            }
                            finally {
                                if ($commit398) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e400) {
                                        $commit398 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e400) {
                                        $commit398 = false;
                                        fabric.common.TransactionID
                                          $currentTid401 =
                                          $tm402.getCurrentTid();
                                        if ($currentTid401 != null) {
                                            if ($e400.tid.equals(
                                                            $currentTid401) ||
                                                  !$e400.tid.
                                                  isDescendantOf(
                                                    $currentTid401)) {
                                                throw $e400;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit398 && $retry399) {
                                    { val = val$var396; }
                                    continue $label397;
                                }
                            }
                        }
                    }
                }
                return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
            }
            return fabric.metrics.Metric._Impl.static_plus(tmp, other);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base, boolean useWeakCache,
                          final fabric.worker.Store s) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(term(0))) instanceof fabric.metrics.SampledMetric)
                return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                          new fabric.metrics.contracts.enforcement.DirectPolicy.
                            _Impl(s).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$DirectPolicy$(
                    (fabric.metrics.ScaledMetric) this.$getProxy(), rate, base);
            fabric.metrics.contracts.Contract witness = null;
            long currentTime = java.lang.System.currentTimeMillis();
            double baseNow =
              fabric.metrics.contracts.Bound._Impl.value(rate, base,
                                                         currentTime);
            fabric.metrics.Metric m = term(0);
            rate = rate / this.get$scalar();
            baseNow = baseNow / this.get$scalar();
            if (this.get$scalar() < 0) {
                m = m.times(-1);
                baseNow = -baseNow;
                rate = -rate;
            }
            witness = m.getThresholdContract(rate, baseNow, currentTime);
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(s).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(
                new fabric.metrics.contracts.Contract[] { witness });
        }
        
        public int hashCode() {
            return fabric.util.Arrays._Impl.hashCode(this.get$terms()) * 32 +
              java.lang.Double.hashCode(this.get$scalar());
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.ScaledMetric) {
                fabric.metrics.ScaledMetric that =
                  (fabric.metrics.ScaledMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                return this.get$scalar() ==
                  that.get$scalar() &&
                  fabric.util.Arrays._Impl.deepEquals(this.get$terms(),
                                                      that.get$terms()) &&
                  this.$getStore().equals(that.$getStore());
            }
            return false;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.ScaledMetric._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeDouble(this.scalar);
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
            this.scalar = in.readDouble();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.ScaledMetric._Impl src =
              (fabric.metrics.ScaledMetric._Impl) other;
            this.scalar = src.scalar;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.ScaledMetric._Static {
            public _Proxy(fabric.metrics.ScaledMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.ScaledMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  ScaledMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.ScaledMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.ScaledMetric._Static._Impl.class);
                $instance = (fabric.metrics.ScaledMetric._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.ScaledMetric._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
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
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.ScaledMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -88, 32, -128, -54,
    -81, 19, 14, 16, -2, -58, 84, 47, -9, 49, 99, 13, 80, 104, 76, 22, -16, -28,
    -94, 89, -104, -102, -30, -92, -36, -1, -54, 14 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1523829668000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
