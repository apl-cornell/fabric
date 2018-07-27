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
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                otherScalar * tmp.get$scalar(),
                                                tmp.term(0));
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
                                val =
                                  ((fabric.metrics.ScaledMetric)
                                     new fabric.metrics.ScaledMetric._Impl(s).
                                     $getProxy()).fabric$metrics$ScaledMetric$(
                                                    that.get$scalar() +
                                                        tmp.get$scalar(),
                                                    tmp.term(0));
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
    
    public static final byte[] $classHash = new byte[] { 38, -57, -53, 3, 116,
    93, 73, -32, 70, -76, -99, 34, 90, 6, -1, 124, -26, -2, -34, 33, -50, 24,
    -12, -107, 93, 33, -54, 24, -63, 23, -58, 51 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1532368903000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0ZbWwUx3XubJ99xmBj82mMMcZxxEfuBOlXMEXFBw4XDmPZBiWm4K535+yN93aX3Tl8TkNEkRAkjVAVPhKigqqIKAmhQapImiaiTauEJCVN26hpmkp1+FHStBRVKG36Hfre7Nzd3vpuY0u1NO/tzcx7877n7frcdVJhW6Q1KQ2pWoSNm9SOdElD8USPZNlUiWmSbffD7KA8ozx+4qOnlOYgCSZIjSzphq7Kkjao24zMStwr7ZWiOmXR7b3xjp0kLCPhZskeYSS4szNjkRbT0MaHNYOJQybxP74yeuzR3XXfKyO1A6RW1fuYxFQ5ZuiMZtgAqUnR1BC17A2KQpUBMlunVOmjlipp6n2w0dAHSL2tDusSS1vU7qW2oe3FjfV22qQWPzM7ieIbILaVlplhgfh1jvhppmrRhGqzjgQJJVWqKfYe8gApT5CKpCYNw8Z5iawWUc4x2oXzsL1aBTGtpCTTLEn5qKorjCzxUuQ0btsCG4C0MkXZiJE7qlyXYILUOyJpkj4c7WOWqg/D1gojDacw0liSKWyqMiV5VBqmg4ws8O7rcZZgV5ibBUkYmevdxjmBzxo9PnN563r3uiNf1zfrQRIAmRUqayh/FRA1e4h6aZJaVJepQ1izInFCmnfxcJAQ2DzXs9nZ8/37b3xlVfMrbzh7FhXZs23oXiqzQfnM0KxfNsWW31GGYlSZhq1iKBRozr3aI1Y6MiZE+7wcR1yMZBdf6b10z/6z9FqQVMdJSDa0dAqiarZspExVo9adVKeWxKgSJ2GqKzG+HieV8JxQderMbksmbcripFzjUyGD/wYTJYEFmqgSnlU9aWSfTYmN8OeMSQiphEECMCYIafoB4MWElH3IyF3RESNFo0Namo5BeEdhUMmSR6KQt5YqR21LjlppnamwSUxBFAGyo32QpFTZyn9FQArz/8otg7LXjQUCYNYlsqHQIckGH4l46ezRICU2G5pCrUFZO3IxThounuQxE8Y4tyFWuVUC4Ocmb4Vw0x5Ld2668dzgZSfekFYYjZFFjogRIWLELSJIVYOJFIHSFIHSdC6QicROx5/l8RKyeWLlGNUAo7WmJrGkYaUyJBDgWs3h9DxQwM2jUD6gQtQs79t119cOt5ZBhJpj5eg02NrmzZd8lYnDkwRJMCjXHvrok/Mn9hn5zGGkbVJCT6bEhGz1msgyZKpAwcuzX9EiPT94cV9bEItJGOockyASoWg0e88oSMyObJFDa1QkyAy0gaThUrYyVbMRyxjLz3DXz0JQ70QBGssjIK+PX+4zT/3m7T/ezm+ObCmtddXcPso6XOmLzGp5os7O277fohT2/e6xnqPHrx/ayQ0PO5YVO7ANYQzSVoJ8NayDb+x5/4OJM78K5p3FSMhMD2mqnOG6zL4JfwEYn+LAHMQJxFCJYyL/W3IFwMST2/OyQSnQoByB6Hbbdj1lKGpSlYY0ipHyn9pbVj//5yN1jrs1mHGMZ5FVn80gP7+wk+y/vPvvzZxNQMarKG+//DanvjXkOW+wLGkc5ch8453FJ1+XTkHkQ3Wy1fsoLziE24NwB67htriNw9Wetc8haHWs1ZQLeG+t78JLMx+LA9Fz326Mrb/mJHwuFpHH0iIJv0Nypcmas6m/BVtDrwVJ5QCp4/e1pLMdEtQsCIMBuHHtmJhMkJkF64W3p3NVdORyrcmbB65jvVmQLzTwjLvxudoJfCdwwBBz0EitMFoIKd8h8GZcbTARzskECH9Yy0mWcdiOYDk3ZJCRsGkZDKSk0DGE1VQqzdD7/JyVEKo2VDRolybbu8dSU5Aye8XdSg8fe+hm5MgxJ9acBmTZpB7ATeM0Ifygmfy0DJyy1O8UTtH1h/P7Xn563yHngq4vvE436enUd3/937cij115s0i5DikGZB7/XZcpbpEAPq7I5CzM/0LiNrwq8AcuC7vCkqAGi0s1Llz6MweOnVa2Pbk6KGJ7ExidGeZtGt1LNRcrLGhLJzXGW3m7lg/UK9cW3xEbvTrs2GKJ52Tv7me2nnvzznb5kSApy0XkpB6xkKijMA6rLQotrt5fEI0tOVvNQBv0wFhBSMXjAm93R6NTq7nhEcRzpNx81YKkX+Bur5nz9SHohC/+jPHJudBLei5j5xrGxUZ+8N0+NearCPogQx0ebYJHm/tCb8tL31OoM2bgGgiTpMDbpqczknQLvLm0zm55ZZ817pvd8GaE3SN07T3YY7HevK2KSP9FOPodgV+envRI8pLAF6Ymveazxq/gYa/0nb7Sr4Pudb3At05PeiRpF7hlatKnfdbGEJhe6Xf4Sr8Bjj4r8NHpSY8kjwj88NSkf8BnbT+Cca/03cWkn4VEn4fRRUjVGoFnlJC+aJFdnynUZ6ZgUi1woLQ+AdEFiMRvFok/Zlij1Mo34/AWb2+VTL5tobfH5vId9jHGEQQHGL6wc2Pw+72kKb4EYwtI/YLAD5cwBYKDkxVHkm8KfOAzFcefD3Kux30UeBTBt6DdzSpANUNW2bivO6F8h9cK3DA9HZCkXuDwNHQ47aPDdxCczDuh21Dtok7g2dQEYzuc/qzAj08vm5DkpMBHSyvglu8pn7VnEDzBSBUznM8o2Xit4z0ydogR18KkCC2mIWbaAEi7VmA/FxXREEnqBa6ZkotinOsFHzVfQHCekQp8Z7ezOjZ5LuON1IIuTnHdyR71GpBhH4xRQmoOCrxpigUF+thKEw6ANyTsWfnnO095qRcsNwr8hdLqu5qLurwNfuJjg1cRvAQOdI4e5KbAuReLOXEljP2E1FKBS2lZwolIslHg9VNy4j2c62UfBX6G4BIj5aaWLio4d08CBlw3sycEfnKq7sHHHyL4URGvIKczAp+Yulccpd71Ueo9BD9nZIbwSinduFNUGE8TMvduB8/51/ScgiT/FPjj0kqUc/HKeVTlwIPZpGkovMj6mGFRn9trwkf33yN4H4o/GwEeI4am9BiaKo9nj1pX/M5kFgVTwcsD1eEcmaaozuCtKvfsYgIyhVEmuFQkrWixWgjjF4Q0/lbgt6ZnUiS5LPBrUyvHf/FZu4HgT1CORyR7JGYojmU3IFjpiL+RkTJVZ8VUuRXGu4QsGhV4mh0+knQL7NPhe/qaeuEjflM43xJ8ouHfpXUP8CrwCZRGuictaXYxzSuHDEOjkp4B5u63Hvyos6jI11XxpV+OvUrPXN2yam6JL6sLJv3vRdA9d7q2av7p7e/xj4W5r/jhBKlKpjXN/fHD9RwyLZpUubph51OIyfULQ89aeOEw/s8NfEIFA5XOvhowgbMPf83kTmjMgRc5y8a0hf83Ovfx/H+Eqvqv8C93YMeW9tcvl7Fd8StdF061DoRu3v/hpxNL317w1+O7lv50wY/nX7r9fwyUCwvPGgAA";
}
