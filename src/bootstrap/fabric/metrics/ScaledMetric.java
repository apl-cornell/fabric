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
                    fabric.metrics.DerivedMetric val$var349 = val;
                    fabric.worker.transaction.TransactionManager $tm355 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled358 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff356 = 1;
                    boolean $doBackoff357 = true;
                    boolean $retry352 = true;
                    $label350: for (boolean $commit351 = false; !$commit351; ) {
                        if ($backoffEnabled358) {
                            if ($doBackoff357) {
                                if ($backoff356 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff356);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e353) {
                                            
                                        }
                                    }
                                }
                                if ($backoff356 < 5000) $backoff356 *= 2;
                            }
                            $doBackoff357 = $backoff356 <= 32 || !$doBackoff357;
                        }
                        $commit351 = true;
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
                        catch (final fabric.worker.RetryException $e353) {
                            $commit351 = false;
                            continue $label350;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e353) {
                            $commit351 = false;
                            fabric.common.TransactionID $currentTid354 =
                              $tm355.getCurrentTid();
                            if ($e353.tid.isDescendantOf($currentTid354))
                                continue $label350;
                            if ($currentTid354.parent != null) {
                                $retry352 = false;
                                throw $e353;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e353) {
                            $commit351 = false;
                            if ($tm355.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid354 =
                              $tm355.getCurrentTid();
                            if ($e353.tid.isDescendantOf($currentTid354)) {
                                $retry352 = true;
                            }
                            else if ($currentTid354.parent != null) {
                                $retry352 = false;
                                throw $e353;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e353) {
                            $commit351 = false;
                            if ($tm355.checkForStaleObjects())
                                continue $label350;
                            $retry352 = false;
                            throw new fabric.worker.AbortException($e353);
                        }
                        finally {
                            if ($commit351) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e353) {
                                    $commit351 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e353) {
                                    $commit351 = false;
                                    fabric.common.TransactionID $currentTid354 =
                                      $tm355.getCurrentTid();
                                    if ($currentTid354 != null) {
                                        if ($e353.tid.equals($currentTid354) ||
                                              !$e353.tid.isDescendantOf(
                                                           $currentTid354)) {
                                            throw $e353;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit351 && $retry352) {
                                { val = val$var349; }
                                continue $label350;
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
                        fabric.metrics.DerivedMetric val$var359 = val;
                        fabric.worker.transaction.TransactionManager $tm365 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled368 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff366 = 1;
                        boolean $doBackoff367 = true;
                        boolean $retry362 = true;
                        $label360: for (boolean $commit361 = false; !$commit361;
                                        ) {
                            if ($backoffEnabled368) {
                                if ($doBackoff367) {
                                    if ($backoff366 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff366);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e363) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff366 < 5000) $backoff366 *= 2;
                                }
                                $doBackoff367 = $backoff366 <= 32 ||
                                                  !$doBackoff367;
                            }
                            $commit361 = true;
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
                            catch (final fabric.worker.RetryException $e363) {
                                $commit361 = false;
                                continue $label360;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e363) {
                                $commit361 = false;
                                fabric.common.TransactionID $currentTid364 =
                                  $tm365.getCurrentTid();
                                if ($e363.tid.isDescendantOf($currentTid364))
                                    continue $label360;
                                if ($currentTid364.parent != null) {
                                    $retry362 = false;
                                    throw $e363;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e363) {
                                $commit361 = false;
                                if ($tm365.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid364 =
                                  $tm365.getCurrentTid();
                                if ($e363.tid.isDescendantOf($currentTid364)) {
                                    $retry362 = true;
                                }
                                else if ($currentTid364.parent != null) {
                                    $retry362 = false;
                                    throw $e363;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e363) {
                                $commit361 = false;
                                if ($tm365.checkForStaleObjects())
                                    continue $label360;
                                $retry362 = false;
                                throw new fabric.worker.AbortException($e363);
                            }
                            finally {
                                if ($commit361) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e363) {
                                        $commit361 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e363) {
                                        $commit361 = false;
                                        fabric.common.TransactionID
                                          $currentTid364 =
                                          $tm365.getCurrentTid();
                                        if ($currentTid364 != null) {
                                            if ($e363.tid.equals(
                                                            $currentTid364) ||
                                                  !$e363.tid.
                                                  isDescendantOf(
                                                    $currentTid364)) {
                                                throw $e363;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit361 && $retry362) {
                                    { val = val$var359; }
                                    continue $label360;
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
    
    public static final byte[] $classHash = new byte[] { -80, 103, 18, -56,
    -116, 58, 50, -74, 61, -39, 28, 49, -44, -6, -70, 86, 109, -21, 106, -87,
    -2, 43, 110, 66, 7, 116, 8, 23, -47, 104, -56, 40 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527882698000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
