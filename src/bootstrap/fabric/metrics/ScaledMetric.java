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
    
    public static final byte[] $classHash = new byte[] { 18, -95, -112, 76, -50,
    -126, 109, 58, 61, -109, -113, -49, -71, -25, 17, -68, -83, 61, -89, 76,
    111, -32, 84, 26, 65, -46, -89, 39, -92, 119, -65, -81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1531161205000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
