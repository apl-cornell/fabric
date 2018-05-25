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
    
    public static final byte[] $classHash = new byte[] { 11, -94, 51, -4, -67,
    -3, -47, 27, 2, -42, -126, -116, -17, -121, -28, 125, -7, -91, 108, 110, 87,
    -71, 112, 24, 83, 15, 57, 36, -60, 70, 112, -122 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527195033000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
