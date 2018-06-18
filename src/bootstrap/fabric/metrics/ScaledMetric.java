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
                    fabric.metrics.DerivedMetric val$var359 = val;
                    fabric.worker.transaction.TransactionManager $tm365 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled368 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff366 = 1;
                    boolean $doBackoff367 = true;
                    boolean $retry362 = true;
                    $label360: for (boolean $commit361 = false; !$commit361; ) {
                        if ($backoffEnabled368) {
                            if ($doBackoff367) {
                                if ($backoff366 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff366);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e363) {
                                            
                                        }
                                    }
                                }
                                if ($backoff366 < 5000) $backoff366 *= 2;
                            }
                            $doBackoff367 = $backoff366 <= 32 || !$doBackoff367;
                        }
                        $commit361 = true;
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
                                    fabric.common.TransactionID $currentTid364 =
                                      $tm365.getCurrentTid();
                                    if ($currentTid364 != null) {
                                        if ($e363.tid.equals($currentTid364) ||
                                              !$e363.tid.isDescendantOf(
                                                           $currentTid364)) {
                                            throw $e363;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                        fabric.metrics.DerivedMetric val$var369 = val;
                        fabric.worker.transaction.TransactionManager $tm375 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled378 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff376 = 1;
                        boolean $doBackoff377 = true;
                        boolean $retry372 = true;
                        $label370: for (boolean $commit371 = false; !$commit371;
                                        ) {
                            if ($backoffEnabled378) {
                                if ($doBackoff377) {
                                    if ($backoff376 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff376);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e373) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff376 < 5000) $backoff376 *= 2;
                                }
                                $doBackoff377 = $backoff376 <= 32 ||
                                                  !$doBackoff377;
                            }
                            $commit371 = true;
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
                            catch (final fabric.worker.RetryException $e373) {
                                $commit371 = false;
                                continue $label370;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e373) {
                                $commit371 = false;
                                fabric.common.TransactionID $currentTid374 =
                                  $tm375.getCurrentTid();
                                if ($e373.tid.isDescendantOf($currentTid374))
                                    continue $label370;
                                if ($currentTid374.parent != null) {
                                    $retry372 = false;
                                    throw $e373;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e373) {
                                $commit371 = false;
                                if ($tm375.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid374 =
                                  $tm375.getCurrentTid();
                                if ($e373.tid.isDescendantOf($currentTid374)) {
                                    $retry372 = true;
                                }
                                else if ($currentTid374.parent != null) {
                                    $retry372 = false;
                                    throw $e373;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e373) {
                                $commit371 = false;
                                if ($tm375.checkForStaleObjects())
                                    continue $label370;
                                $retry372 = false;
                                throw new fabric.worker.AbortException($e373);
                            }
                            finally {
                                if ($commit371) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e373) {
                                        $commit371 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e373) {
                                        $commit371 = false;
                                        fabric.common.TransactionID
                                          $currentTid374 =
                                          $tm375.getCurrentTid();
                                        if ($currentTid374 != null) {
                                            if ($e373.tid.equals(
                                                            $currentTid374) ||
                                                  !$e373.tid.
                                                  isDescendantOf(
                                                    $currentTid374)) {
                                                throw $e373;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit371 && $retry372) {
                                    { val = val$var369; }
                                    continue $label370;
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
    
    public static final byte[] $classHash = new byte[] { 36, -34, -86, 80, -48,
    70, -65, 68, 58, 75, -104, -84, 108, -44, 23, -31, -87, -100, 38, 78, 59,
    -56, 62, 77, -122, -37, 124, -122, -78, -8, 85, -35 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1529351168000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
