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
    
    public static final byte[] $classHash = new byte[] { 82, 52, 114, 22, -5,
    -89, 84, -77, 0, -49, 63, -44, -85, 48, 115, 52, 114, 21, 127, -83, 68, -85,
    -83, 95, -33, -62, -24, -24, 81, 10, 38, -39 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1528821850000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
