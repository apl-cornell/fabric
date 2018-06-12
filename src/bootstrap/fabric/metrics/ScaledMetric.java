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
                    fabric.metrics.DerivedMetric val$var358 = val;
                    fabric.worker.transaction.TransactionManager $tm364 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled367 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff365 = 1;
                    boolean $doBackoff366 = true;
                    boolean $retry361 = true;
                    $label359: for (boolean $commit360 = false; !$commit360; ) {
                        if ($backoffEnabled367) {
                            if ($doBackoff366) {
                                if ($backoff365 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff365);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e362) {
                                            
                                        }
                                    }
                                }
                                if ($backoff365 < 5000) $backoff365 *= 2;
                            }
                            $doBackoff366 = $backoff365 <= 32 || !$doBackoff366;
                        }
                        $commit360 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                otherScalar * tmp.get$scalar(),
                                                tmp.term(0));
                        }
                        catch (final fabric.worker.RetryException $e362) {
                            $commit360 = false;
                            continue $label359;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e362) {
                            $commit360 = false;
                            fabric.common.TransactionID $currentTid363 =
                              $tm364.getCurrentTid();
                            if ($e362.tid.isDescendantOf($currentTid363))
                                continue $label359;
                            if ($currentTid363.parent != null) {
                                $retry361 = false;
                                throw $e362;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e362) {
                            $commit360 = false;
                            if ($tm364.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid363 =
                              $tm364.getCurrentTid();
                            if ($e362.tid.isDescendantOf($currentTid363)) {
                                $retry361 = true;
                            }
                            else if ($currentTid363.parent != null) {
                                $retry361 = false;
                                throw $e362;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e362) {
                            $commit360 = false;
                            if ($tm364.checkForStaleObjects())
                                continue $label359;
                            $retry361 = false;
                            throw new fabric.worker.AbortException($e362);
                        }
                        finally {
                            if ($commit360) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e362) {
                                    $commit360 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e362) {
                                    $commit360 = false;
                                    fabric.common.TransactionID $currentTid363 =
                                      $tm364.getCurrentTid();
                                    if ($currentTid363 != null) {
                                        if ($e362.tid.equals($currentTid363) ||
                                              !$e362.tid.isDescendantOf(
                                                           $currentTid363)) {
                                            throw $e362;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit360 && $retry361) {
                                { val = val$var358; }
                                continue $label359;
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
                        fabric.metrics.DerivedMetric val$var368 = val;
                        fabric.worker.transaction.TransactionManager $tm374 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled377 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff375 = 1;
                        boolean $doBackoff376 = true;
                        boolean $retry371 = true;
                        $label369: for (boolean $commit370 = false; !$commit370;
                                        ) {
                            if ($backoffEnabled377) {
                                if ($doBackoff376) {
                                    if ($backoff375 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff375);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e372) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff375 < 5000) $backoff375 *= 2;
                                }
                                $doBackoff376 = $backoff375 <= 32 ||
                                                  !$doBackoff376;
                            }
                            $commit370 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                val =
                                  ((fabric.metrics.ScaledMetric)
                                     new fabric.metrics.ScaledMetric._Impl(s).
                                     $getProxy()).fabric$metrics$ScaledMetric$(
                                                    that.get$scalar() +
                                                        tmp.get$scalar(),
                                                    tmp.term(0));
                            }
                            catch (final fabric.worker.RetryException $e372) {
                                $commit370 = false;
                                continue $label369;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e372) {
                                $commit370 = false;
                                fabric.common.TransactionID $currentTid373 =
                                  $tm374.getCurrentTid();
                                if ($e372.tid.isDescendantOf($currentTid373))
                                    continue $label369;
                                if ($currentTid373.parent != null) {
                                    $retry371 = false;
                                    throw $e372;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e372) {
                                $commit370 = false;
                                if ($tm374.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid373 =
                                  $tm374.getCurrentTid();
                                if ($e372.tid.isDescendantOf($currentTid373)) {
                                    $retry371 = true;
                                }
                                else if ($currentTid373.parent != null) {
                                    $retry371 = false;
                                    throw $e372;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e372) {
                                $commit370 = false;
                                if ($tm374.checkForStaleObjects())
                                    continue $label369;
                                $retry371 = false;
                                throw new fabric.worker.AbortException($e372);
                            }
                            finally {
                                if ($commit370) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e372) {
                                        $commit370 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e372) {
                                        $commit370 = false;
                                        fabric.common.TransactionID
                                          $currentTid373 =
                                          $tm374.getCurrentTid();
                                        if ($currentTid373 != null) {
                                            if ($e372.tid.equals(
                                                            $currentTid373) ||
                                                  !$e372.tid.
                                                  isDescendantOf(
                                                    $currentTid373)) {
                                                throw $e372;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit370 && $retry371) {
                                    { val = val$var368; }
                                    continue $label369;
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
            witness = m.getThresholdTreaty(rate, baseNow, currentTime);
            return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                     new fabric.worker.metrics.treaties.MetricTreaty[] { witness });
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
    
    public static final byte[] $classHash = new byte[] { -44, -36, 89, 110, 30,
    40, 77, 62, 74, 46, 29, -107, -48, -46, 28, -37, -91, 124, 1, 35, 95, 120,
    69, 73, -19, 109, -28, 11, 16, -43, 68, -62 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1528821850000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
