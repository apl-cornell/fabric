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
    
    public static final byte[] $classHash = new byte[] { 107, -93, 124, -27, 43,
    -35, 68, 16, 46, 58, 102, 8, 120, -47, 15, -41, -120, 112, 83, 62, -21,
    -116, -60, 47, -56, -8, -118, 42, 37, -98, -40, 77 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1528404283000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
