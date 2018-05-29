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
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
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
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { -24, 94, 34, 9, 66,
    -89, 101, 85, -70, 42, 11, -42, -38, 17, 40, -42, 29, 86, -7, 49, 36, -124,
    -71, 1, 57, 90, 34, -103, 76, 36, 88, -81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527195033000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
