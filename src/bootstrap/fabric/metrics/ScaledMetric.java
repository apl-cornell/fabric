package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Arrays;
import fabric.util.Iterator;
import fabric.util.LinkedHashMap;
import fabric.util.Map;
import fabric.metrics.contracts.Bound;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.enforcement.DirectPolicy;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.metrics.treaties.enforcement.WitnessPolicy;
import fabric.worker.metrics.treaties.statements.EqualityStatement;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;

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
    
    public double computeValue(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeVelocity(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoise(fabric.worker.metrics.StatsMap weakStats);
    
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
    
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      thresholdPolicy(double rate, double base,
                      fabric.worker.metrics.StatsMap weakStats,
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
            return this.get$scalar() * term(0).getPresetR();
        }
        
        public double computePresetB() {
            return this.get$scalar() * term(0).getPresetB();
        }
        
        public double computePresetV() {
            return this.get$scalar() * term(0).getPresetV();
        }
        
        public double computePresetN() {
            return this.get$scalar() * this.get$scalar() * term(0).getPresetN();
        }
        
        public double computeValue(fabric.worker.metrics.StatsMap weakStats) {
            return this.get$scalar() * term(0).value(weakStats);
        }
        
        public double computeVelocity(
          fabric.worker.metrics.StatsMap weakStats) {
            return this.get$scalar() * term(0).velocity(weakStats);
        }
        
        public double computeNoise(fabric.worker.metrics.StatsMap weakStats) {
            return this.get$scalar() * this.get$scalar() *
              term(0).noise(weakStats);
        }
        
        public java.lang.String toString() {
            return "(" +
            this.get$scalar() +
            "*" +
            java.lang.String.valueOf(
                               fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                   term(0))) +
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
                                                 tmp.term(0));
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var379 = val;
                    fabric.worker.transaction.TransactionManager $tm385 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled388 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff386 = 1;
                    boolean $doBackoff387 = true;
                    boolean $retry382 = true;
                    $label380: for (boolean $commit381 = false; !$commit381; ) {
                        if ($backoffEnabled388) {
                            if ($doBackoff387) {
                                if ($backoff386 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff386);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e383) {
                                            
                                        }
                                    }
                                }
                                if ($backoff386 < 5000) $backoff386 *= 2;
                            }
                            $doBackoff387 = $backoff386 <= 32 || !$doBackoff387;
                        }
                        $commit381 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                val =
                                  ((fabric.metrics.ScaledMetric)
                                     new fabric.metrics.ScaledMetric._Impl(s).
                                     $getProxy()).fabric$metrics$ScaledMetric$(
                                                    otherScalar *
                                                        tmp.get$scalar(),
                                                    tmp.term(0));
                            }
                            catch (final fabric.worker.RetryException $e383) {
                                throw $e383;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e383) {
                                throw $e383;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e383) {
                                throw $e383;
                            }
                            catch (final Throwable $e383) {
                                $tm385.getCurrentLog().checkRetrySignal();
                                throw $e383;
                            }
                        }
                        catch (final fabric.worker.RetryException $e383) {
                            $commit381 = false;
                            continue $label380;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e383) {
                            $commit381 = false;
                            fabric.common.TransactionID $currentTid384 =
                              $tm385.getCurrentTid();
                            if ($e383.tid.isDescendantOf($currentTid384))
                                continue $label380;
                            if ($currentTid384.parent != null) {
                                $retry382 = false;
                                throw $e383;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e383) {
                            $commit381 = false;
                            if ($tm385.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid384 =
                              $tm385.getCurrentTid();
                            if ($e383.tid.isDescendantOf($currentTid384)) {
                                $retry382 = true;
                            }
                            else if ($currentTid384.parent != null) {
                                $retry382 = false;
                                throw $e383;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e383) {
                            $commit381 = false;
                            if ($tm385.checkForStaleObjects())
                                continue $label380;
                            $retry382 = false;
                            throw new fabric.worker.AbortException($e383);
                        }
                        finally {
                            if ($commit381) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e383) {
                                    $commit381 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e383) {
                                    $commit381 = false;
                                    fabric.common.TransactionID $currentTid384 =
                                      $tm385.getCurrentTid();
                                    if ($currentTid384 != null) {
                                        if ($e383.tid.equals($currentTid384) ||
                                              !$e383.tid.isDescendantOf(
                                                           $currentTid384)) {
                                            throw $e383;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit381 && $retry382) {
                                { val = val$var379; }
                                continue $label380;
                            }
                        }
                    }
                }
            }
            return val;
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
                  ((fabric.metrics.ScaledMetric)
                     fabric.lang.Object._Proxy.$getProxy(other)).term(0).
                  equals(tmp.term(0))) {
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
                                                     tmp.term(0));
                }
                else {
                    {
                        fabric.metrics.DerivedMetric val$var389 = val;
                        fabric.worker.transaction.TransactionManager $tm395 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled398 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff396 = 1;
                        boolean $doBackoff397 = true;
                        boolean $retry392 = true;
                        $label390: for (boolean $commit391 = false; !$commit391;
                                        ) {
                            if ($backoffEnabled398) {
                                if ($doBackoff397) {
                                    if ($backoff396 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff396);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e393) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff396 < 5000) $backoff396 *= 2;
                                }
                                $doBackoff397 = $backoff396 <= 32 ||
                                                  !$doBackoff397;
                            }
                            $commit391 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    val =
                                      ((fabric.metrics.ScaledMetric)
                                         new fabric.metrics.ScaledMetric._Impl(
                                           s).$getProxy()).
                                        fabric$metrics$ScaledMetric$(
                                          that.get$scalar() + tmp.get$scalar(),
                                          tmp.term(0));
                                }
                                catch (final fabric.worker.
                                         RetryException $e393) {
                                    throw $e393;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e393) {
                                    throw $e393;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e393) {
                                    throw $e393;
                                }
                                catch (final Throwable $e393) {
                                    $tm395.getCurrentLog().checkRetrySignal();
                                    throw $e393;
                                }
                            }
                            catch (final fabric.worker.RetryException $e393) {
                                $commit391 = false;
                                continue $label390;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e393) {
                                $commit391 = false;
                                fabric.common.TransactionID $currentTid394 =
                                  $tm395.getCurrentTid();
                                if ($e393.tid.isDescendantOf($currentTid394))
                                    continue $label390;
                                if ($currentTid394.parent != null) {
                                    $retry392 = false;
                                    throw $e393;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e393) {
                                $commit391 = false;
                                if ($tm395.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid394 =
                                  $tm395.getCurrentTid();
                                if ($e393.tid.isDescendantOf($currentTid394)) {
                                    $retry392 = true;
                                }
                                else if ($currentTid394.parent != null) {
                                    $retry392 = false;
                                    throw $e393;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e393) {
                                $commit391 = false;
                                if ($tm395.checkForStaleObjects())
                                    continue $label390;
                                $retry392 = false;
                                throw new fabric.worker.AbortException($e393);
                            }
                            finally {
                                if ($commit391) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e393) {
                                        $commit391 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e393) {
                                        $commit391 = false;
                                        fabric.common.TransactionID
                                          $currentTid394 =
                                          $tm395.getCurrentTid();
                                        if ($currentTid394 != null) {
                                            if ($e393.tid.equals(
                                                            $currentTid394) ||
                                                  !$e393.tid.
                                                  isDescendantOf(
                                                    $currentTid394)) {
                                                throw $e393;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit391 && $retry392) {
                                    { val = val$var389; }
                                    continue $label390;
                                }
                            }
                        }
                    }
                }
                return val;
            }
            return fabric.metrics.Metric._Impl.static_plus(tmp, other);
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base,
                          fabric.worker.metrics.StatsMap weakStats,
                          final fabric.worker.Store s) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(term(0))) instanceof fabric.metrics.SampledMetric)
                return fabric.worker.metrics.treaties.enforcement.DirectPolicy.
                         singleton;
            fabric.worker.metrics.treaties.MetricTreaty witness = null;
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
            com.google.common.collect.Multimap witnesses =
              com.google.common.collect.HashMultimap.create();
            witnesses.
              put(
                (java.lang.Object) fabric.lang.WrappedJavaInlineable.$unwrap(m),
                new fabric.worker.metrics.treaties.statements.ThresholdStatement(
                  rate, baseNow, currentTime));
            return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                     witnesses);
        }
        
        public int hashCode() {
            return java.util.Arrays.hashCode(this.get$terms().array()) * 32 +
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
                  java.util.Arrays.deepEquals(this.get$terms().array(),
                                              that.get$terms().array()) &&
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
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.metrics.treaties.TreatySet treaties,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, treaties, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.metrics.treaties.TreatySet treaties,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, treaties, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.ScaledMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -36, 55, 120, 81, 105,
    -71, 41, 109, 35, 83, -47, 54, 78, -65, -105, 50, 0, -25, 74, -9, -68, 55,
    -28, -63, 57, -60, 17, 85, 37, -112, 88, 60 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1533241119000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
