package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Arrays;
import fabric.util.LinkedHashMap;
import fabric.util.Map;
import fabric.util.Iterator;
import fabric.common.ConfigProperties;
import fabric.metrics.contracts.Bound;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.metrics.SumStrategy;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.enforcement.DirectPolicy;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.metrics.treaties.enforcement.WitnessPolicy;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * A {@link DerivedMetric} for the sum of the given metric terms.
 */
public interface SumMetric extends fabric.metrics.DerivedMetric {
    /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param terms
     *            The {@link Metric}s this applies to
     */
    public fabric.metrics.SumMetric fabric$metrics$SumMetric$(
      fabric.metrics.Metric[] terms);
    
    public double computePresetR();
    
    public double computePresetB();
    
    public double computePresetV();
    
    public double computePresetN();
    
    public double computeValue(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeVelocity(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoise(fabric.worker.metrics.StatsMap weakStats);
    
    public java.lang.String toString();
    
    public fabric.metrics.DerivedMetric times(double scalar);
    
    /**
     * {@inheritDoc}
     * <p>
     * {@link SumMetric}s try to consolidate local computations so that there
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
      implements fabric.metrics.SumMetric {
        public fabric.metrics.SumMetric fabric$metrics$SumMetric$(
          fabric.metrics.Metric[] arg1) {
            return ((fabric.metrics.SumMetric) fetch()).
              fabric$metrics$SumMetric$(arg1);
        }
        
        public _Proxy(SumMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.DerivedMetric._Impl
      implements fabric.metrics.SumMetric {
        /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param terms
     *            The {@link Metric}s this applies to
     */
        public fabric.metrics.SumMetric fabric$metrics$SumMetric$(
          fabric.metrics.Metric[] terms) {
            fabric$metrics$DerivedMetric$(terms);
            initialize();
            return (fabric.metrics.SumMetric) this.$getProxy();
        }
        
        public double computePresetR() {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).getPresetR();
            }
            return result;
        }
        
        public double computePresetB() {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).getPresetB();
            }
            return result;
        }
        
        public double computePresetV() {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).getPresetV();
            }
            return result;
        }
        
        public double computePresetN() {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).getPresetN();
            }
            return result;
        }
        
        public double computeValue(fabric.worker.metrics.StatsMap weakStats) {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).value(weakStats);
            }
            return result;
        }
        
        public double computeVelocity(
          fabric.worker.metrics.StatsMap weakStats) {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).velocity(weakStats);
            }
            return result;
        }
        
        public double computeNoise(fabric.worker.metrics.StatsMap weakStats) {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).noise(weakStats);
            }
            return result;
        }
        
        public java.lang.String toString() {
            java.lang.String str = "(";
            boolean nonEmpty = false;
            for (int i = 0; i < this.get$terms().length(); i++) {
                if (nonEmpty) str += " + ";
                nonEmpty = true;
                str += term(i);
            }
            return str + ")@" + getStore();
        }
        
        public fabric.metrics.DerivedMetric times(double scalar) {
            return fabric.metrics.SumMetric._Impl.static_times(
                                                    (fabric.metrics.SumMetric)
                                                      this.$getProxy(), scalar);
        }
        
        private static fabric.metrics.DerivedMetric static_times(
          fabric.metrics.SumMetric tmp, double scalar) {
            fabric.metrics.Metric[] newTerms =
              new fabric.metrics.Metric[tmp.get$terms().length()];
            for (int i = 0; i < tmp.get$terms().length(); i++)
                newTerms[i] = tmp.term(i).times(scalar);
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.SumMetric)
                     new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                    fabric$metrics$SumMetric$(newTerms);
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var378 = val;
                    fabric.worker.transaction.TransactionManager $tm384 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled387 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff385 = 1;
                    boolean $doBackoff386 = true;
                    boolean $retry381 = true;
                    $label379: for (boolean $commit380 = false; !$commit380; ) {
                        if ($backoffEnabled387) {
                            if ($doBackoff386) {
                                if ($backoff385 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff385);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e382) {
                                            
                                        }
                                    }
                                }
                                if ($backoff385 < 5000) $backoff385 *= 2;
                            }
                            $doBackoff386 = $backoff385 <= 32 || !$doBackoff386;
                        }
                        $commit380 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e382) {
                            $commit380 = false;
                            continue $label379;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e382) {
                            $commit380 = false;
                            fabric.common.TransactionID $currentTid383 =
                              $tm384.getCurrentTid();
                            if ($e382.tid.isDescendantOf($currentTid383))
                                continue $label379;
                            if ($currentTid383.parent != null) {
                                $retry381 = false;
                                throw $e382;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e382) {
                            $commit380 = false;
                            if ($tm384.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid383 =
                              $tm384.getCurrentTid();
                            if ($e382.tid.isDescendantOf($currentTid383)) {
                                $retry381 = true;
                            }
                            else if ($currentTid383.parent != null) {
                                $retry381 = false;
                                throw $e382;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e382) {
                            $commit380 = false;
                            if ($tm384.checkForStaleObjects())
                                continue $label379;
                            $retry381 = false;
                            throw new fabric.worker.AbortException($e382);
                        }
                        finally {
                            if ($commit380) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e382) {
                                    $commit380 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e382) {
                                    $commit380 = false;
                                    fabric.common.TransactionID $currentTid383 =
                                      $tm384.getCurrentTid();
                                    if ($currentTid383 != null) {
                                        if ($e382.tid.equals($currentTid383) ||
                                              !$e382.tid.isDescendantOf(
                                                           $currentTid383)) {
                                            throw $e382;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit380 && $retry381) {
                                { val = val$var378; }
                                continue $label379;
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
     * {@link SumMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            return fabric.metrics.SumMetric._Impl.static_plus(
                                                    (fabric.metrics.SumMetric)
                                                      this.$getProxy(), other);
        }
        
        private static fabric.metrics.DerivedMetric static_plus(
          fabric.metrics.SumMetric tmp, fabric.metrics.Metric other) {
            final fabric.worker.Store s = tmp.$getStore();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.SumMetric &&
                  other.$getStore().equals(s)) {
                fabric.metrics.SumMetric that =
                  (fabric.metrics.SumMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                fabric.metrics.DerivedMetric result = tmp;
                for (int i = 0; i < that.get$terms().length(); i++) {
                    result = result.plus(that.term(i));
                }
                return result;
            }
            int termIdx = -1;
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.DerivedMetric) {
                fabric.metrics.DerivedMetric derivedOther =
                  (fabric.metrics.DerivedMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                for (int i = 0; i < tmp.get$terms().length(); i++) {
                    if (!tmp.term(i).$getStore().equals(other.$getStore()))
                        continue;
                    if (fabric.lang.Object._Proxy.
                          $getProxy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                tmp.term(
                                      i))) instanceof fabric.metrics.DerivedMetric) {
                        fabric.metrics.DerivedMetric derivedTerm =
                          (fabric.metrics.DerivedMetric)
                            fabric.lang.Object._Proxy.$getProxy(tmp.term(i));
                        if (java.util.Arrays.asList(
                                               derivedTerm.getLeafSubjects(
                                                             ).array()).
                              containsAll(
                                java.util.Arrays.asList(
                                                   derivedOther.getLeafSubjects(
                                                                  ).array()))) {
                            termIdx = i;
                            break;
                        }
                    }
                    else {
                        fabric.metrics.SampledMetric sampledTerm =
                          (fabric.metrics.SampledMetric)
                            fabric.lang.Object._Proxy.$getProxy(tmp.term(i));
                        if (derivedOther.
                              getLeafSubjects().
                              length() ==
                              1 &&
                              java.util.Arrays.
                              asList(
                                derivedOther.
                                    getLeafSubjects().array()).
                              contains(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.
                                  $unwrap(sampledTerm))) {
                            termIdx = i;
                            break;
                        }
                    }
                }
            }
            else {
                fabric.metrics.SampledMetric sampledOther =
                  (fabric.metrics.SampledMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                for (int i = 0; i < tmp.get$terms().length(); i++) {
                    if (!tmp.term(i).$getStore().equals(other.$getStore()))
                        continue;
                    if (fabric.lang.Object._Proxy.
                          $getProxy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                tmp.term(
                                      i))) instanceof fabric.metrics.DerivedMetric) {
                        fabric.metrics.DerivedMetric derivedTerm =
                          (fabric.metrics.DerivedMetric)
                            fabric.lang.Object._Proxy.$getProxy(tmp.term(i));
                        if (java.util.Arrays.
                              asList(derivedTerm.getLeafSubjects().array()).
                              contains(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.
                                  $unwrap(sampledOther))) {
                            termIdx = i;
                            break;
                        }
                    }
                    else {
                        fabric.metrics.SampledMetric sampledTerm =
                          (fabric.metrics.SampledMetric)
                            fabric.lang.Object._Proxy.$getProxy(tmp.term(i));
                        if (sampledTerm.equals(sampledOther)) {
                            termIdx = i;
                            break;
                        }
                    }
                }
            }
            fabric.metrics.Metric[] newTerms = null;
            if (termIdx >= 0) {
                newTerms =
                  (new fabric.metrics.Metric[tmp.get$terms().length()]);
                for (int i = 0; i < tmp.get$terms().length(); i++)
                    newTerms[i] = tmp.term(i);
                newTerms[termIdx] = newTerms[termIdx].plus(other);
            } else {
                newTerms = (new fabric.metrics.Metric[tmp.get$terms().length() +
                                                        1]);
                for (int i = 0; i < tmp.get$terms().length(); i++)
                    newTerms[i] = tmp.term(i);
                newTerms[tmp.get$terms().length()] = other;
                java.util.Arrays.sort(newTerms, 0, newTerms.length);
            }
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.SumMetric)
                     new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                    fabric$metrics$SumMetric$(newTerms);
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var388 = val;
                    fabric.metrics.Metric[] newTerms$var389 = newTerms;
                    int termIdx$var390 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm396 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled399 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff397 = 1;
                    boolean $doBackoff398 = true;
                    boolean $retry393 = true;
                    $label391: for (boolean $commit392 = false; !$commit392; ) {
                        if ($backoffEnabled399) {
                            if ($doBackoff398) {
                                if ($backoff397 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff397);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e394) {
                                            
                                        }
                                    }
                                }
                                if ($backoff397 < 5000) $backoff397 *= 2;
                            }
                            $doBackoff398 = $backoff397 <= 32 || !$doBackoff398;
                        }
                        $commit392 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e394) {
                            $commit392 = false;
                            continue $label391;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e394) {
                            $commit392 = false;
                            fabric.common.TransactionID $currentTid395 =
                              $tm396.getCurrentTid();
                            if ($e394.tid.isDescendantOf($currentTid395))
                                continue $label391;
                            if ($currentTid395.parent != null) {
                                $retry393 = false;
                                throw $e394;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e394) {
                            $commit392 = false;
                            if ($tm396.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid395 =
                              $tm396.getCurrentTid();
                            if ($e394.tid.isDescendantOf($currentTid395)) {
                                $retry393 = true;
                            }
                            else if ($currentTid395.parent != null) {
                                $retry393 = false;
                                throw $e394;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e394) {
                            $commit392 = false;
                            if ($tm396.checkForStaleObjects())
                                continue $label391;
                            $retry393 = false;
                            throw new fabric.worker.AbortException($e394);
                        }
                        finally {
                            if ($commit392) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e394) {
                                    $commit392 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e394) {
                                    $commit392 = false;
                                    fabric.common.TransactionID $currentTid395 =
                                      $tm396.getCurrentTid();
                                    if ($currentTid395 != null) {
                                        if ($e394.tid.equals($currentTid395) ||
                                              !$e394.tid.isDescendantOf(
                                                           $currentTid395)) {
                                            throw $e394;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit392 && $retry393) {
                                {
                                    val = val$var388;
                                    newTerms = newTerms$var389;
                                    termIdx = termIdx$var390;
                                }
                                continue $label391;
                            }
                        }
                    }
                }
            }
            return val;
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base,
                          fabric.worker.metrics.StatsMap weakStats,
                          final fabric.worker.Store s) {
            long currentTime = java.lang.System.currentTimeMillis();
            double baseNow =
              fabric.metrics.contracts.Bound._Impl.value(rate, base,
                                                         currentTime);
            double totalSamples = samples(weakStats);
            double totalValue = value(weakStats);
            double totalVelocity = velocity(weakStats);
            double totalNoise = noise(weakStats);
            int numTerms = this.get$terms().length();
            fabric.common.ConfigProperties config =
              fabric.worker.Worker.getWorker().config;
            if (config.usePreset) {
                java.util.Map witnesses = new java.util.HashMap();
                for (int j = 0; j < numTerms; j++) {
                    fabric.metrics.Metric m = term(j);
                    double scaledX = m.value(weakStats);
                    double r = m.getPresetR();
                    double b = scaledX - m.getPresetB() / getPresetB() *
                      (totalValue - baseNow);
                    double[] normalized =
                      fabric.metrics.contracts.Bound._Impl.createBound(
                                                             r, b, currentTime);
                    if (!witnesses.
                          containsKey(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(m)) ||
                          !((fabric.worker.metrics.treaties.MetricTreaty)
                              witnesses.
                              get(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      m))).
                          implies(m, normalized[0], normalized[1])) {
                        witnesses.put(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                          m),
                                    m.getThresholdTreaty(r, b, currentTime));
                    }
                }
                fabric.worker.metrics.treaties.MetricTreaty[]
                  finalWitnesses =
                  new fabric.worker.metrics.treaties.MetricTreaty[witnesses.
                                                                    size()];
                int i = 0;
                for (java.util.Iterator iter = witnesses.values().iterator();
                     iter.hasNext(); ) {
                    finalWitnesses[i++] =
                      (fabric.worker.metrics.treaties.MetricTreaty) iter.next();
                }
                return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                         finalWitnesses);
            }
            else {
                double baseFactor = 0.0;
                double newFactor = 1.0 - baseFactor;
                double[] velocities = new double[numTerms];
                double[] adaptiveRates = new double[numTerms];
                double[] staticRates = new double[numTerms];
                double[] noises = new double[numTerms];
                boolean hasSlackProducer = false;
                for (int j = 0;
                     j < numTerms; j++) {
                    fabric.metrics.Metric m =
                      term(j);
                    velocities[j] =
                      m.velocity(weakStats);
                    hasSlackProducer =
                      hasSlackProducer || velocities[j] > rate;
                    noises[j] =
                      m.noise(weakStats);
                    adaptiveRates[j] =
                      newFactor * (velocities[j] - totalVelocity / numTerms) +
                        rate / numTerms;
                    staticRates[j] =
                      rate / numTerms;
                }
                if (fabric.common.Logging.METRICS_LOGGER.
                      isLoggable(java.util.logging.Level.FINE)) {
                    fabric.common.Logging.METRICS_LOGGER.
                      log(
                        java.util.logging.Level.FINE,
                        "Running strategy for {0} with velocities {1} and noises {2}",
                        new java.lang.Object[] { (java.lang.Object)
                                                   fabric.lang.WrappedJavaInlineable.
                                                   $unwrap(
                                                     (fabric.metrics.SumMetric)
                                                       this.$getProxy()),
                          java.util.Arrays.
                            toString(velocities),
                          java.util.Arrays.
                            toString(noises) });
                }
                double[] staticSlacks =
                  fabric.worker.metrics.SumStrategy.getSplit(velocities,
                                                             noises,
                                                             staticRates,
                                                             totalValue -
                                                                 baseNow);
                java.util.Map staticWitnesses = new java.util.HashMap();
                long staticTimeout = java.lang.Long.MAX_VALUE;
                for (int j = 0; j < numTerms; j++) {
                    fabric.metrics.Metric m = term(j);
                    double scaledX = m.value(weakStats);
                    double r = staticRates[j];
                    double b = scaledX - staticSlacks[j];
                    double[] normalized =
                      fabric.metrics.contracts.Bound._Impl.createBound(
                                                             r, b, currentTime);
                    if (!staticWitnesses.
                          containsKey(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(m)) ||
                          !((fabric.worker.metrics.treaties.MetricTreaty)
                              staticWitnesses.
                              get(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      m))).
                          implies(m, normalized[0], normalized[1])) {
                        staticWitnesses.
                          put((java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.$unwrap(m),
                              m.getThresholdTreaty(r, b, currentTime));
                    }
                    staticTimeout =
                      java.lang.Math.
                        min(
                          staticTimeout,
                          fabric.worker.metrics.treaties.statements.ThresholdStatement.
                              hedgedEstimate(m, normalized[0], normalized[1],
                                             currentTime, weakStats));
                }
                fabric.worker.metrics.treaties.MetricTreaty[] finalWitnesses =
                  new fabric.worker.metrics.treaties.MetricTreaty[staticWitnesses.size(
                                                                                    )];
                if (hasSlackProducer) {
                    double[] adaptiveSlacks =
                      fabric.worker.metrics.SumStrategy.getSplitEqualVelocity(
                                                          velocities, noises,
                                                          adaptiveRates,
                                                          totalValue - baseNow);
                    java.util.Map adaptiveWitnesses = new java.util.HashMap();
                    long adaptiveTimeout = java.lang.Long.MAX_VALUE;
                    for (int j = 0; j < numTerms; j++) {
                        fabric.metrics.Metric m = term(j);
                        double scaledX = m.value(weakStats);
                        double r = adaptiveRates[j];
                        double b = scaledX - adaptiveSlacks[j];
                        double[] normalized =
                          fabric.metrics.contracts.Bound._Impl.createBound(
                                                                 r, b,
                                                                 currentTime);
                        if (!adaptiveWitnesses.
                              containsKey(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      m)) ||
                              !((fabric.worker.metrics.treaties.MetricTreaty)
                                  adaptiveWitnesses.
                                  get(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                          m))).
                              implies(m, normalized[0], normalized[1])) {
                            adaptiveWitnesses.
                              put(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(m),
                                m.getThresholdTreaty(r, b, currentTime));
                        }
                        adaptiveTimeout =
                          java.lang.Math.
                            min(
                              adaptiveTimeout,
                              fabric.worker.metrics.treaties.statements.ThresholdStatement.
                                  hedgedEstimate(m, normalized[0],
                                                 normalized[1], currentTime,
                                                 weakStats));
                    }
                    if (adaptiveTimeout >
                          staticTimeout &&
                          adaptiveTimeout -
                          java.lang.System.currentTimeMillis() >
                          fabric.metrics.SumMetric._Static._Proxy.$instance.
                          get$MIN_ADAPTIVE_EXPIRY()) {
                        int i = 0;
                        for (java.util.Iterator iter =
                               adaptiveWitnesses.values().iterator();
                             iter.hasNext();
                             ) {
                            finalWitnesses[i++] =
                              (fabric.worker.metrics.treaties.MetricTreaty)
                                iter.next();
                        }
                    }
                    else {
                        int i = 0;
                        for (java.util.Iterator iter =
                               staticWitnesses.values().iterator();
                             iter.hasNext();
                             ) {
                            finalWitnesses[i++] =
                              (fabric.worker.metrics.treaties.MetricTreaty)
                                iter.next();
                        }
                    }
                }
                else {
                    int i = 0;
                    for (java.util.Iterator iter =
                           staticWitnesses.values().iterator();
                         iter.hasNext();
                         ) {
                        finalWitnesses[i++] =
                          (fabric.worker.metrics.treaties.MetricTreaty)
                            iter.next();
                    }
                }
                return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                         finalWitnesses);
            }
        }
        
        public int hashCode() {
            return java.util.Arrays.hashCode(this.get$terms().array()) * 32 +
              getStore().hashCode();
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.SumMetric) {
                fabric.metrics.SumMetric that =
                  (fabric.metrics.SumMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                return java.util.Arrays.deepEquals(this.get$terms().array(),
                                                   that.get$terms().array()) &&
                  this.$getStore().equals(that.$getStore());
            }
            return false;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.SumMetric._Proxy(this);
        }
        
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
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public long get$MIN_ADAPTIVE_EXPIRY();
        
        public long set$MIN_ADAPTIVE_EXPIRY(long val);
        
        public long postInc$MIN_ADAPTIVE_EXPIRY();
        
        public long postDec$MIN_ADAPTIVE_EXPIRY();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.SumMetric._Static {
            public long get$MIN_ADAPTIVE_EXPIRY() {
                return ((fabric.metrics.SumMetric._Static._Impl) fetch()).
                  get$MIN_ADAPTIVE_EXPIRY();
            }
            
            public long set$MIN_ADAPTIVE_EXPIRY(long val) {
                return ((fabric.metrics.SumMetric._Static._Impl) fetch()).
                  set$MIN_ADAPTIVE_EXPIRY(val);
            }
            
            public long postInc$MIN_ADAPTIVE_EXPIRY() {
                return ((fabric.metrics.SumMetric._Static._Impl) fetch()).
                  postInc$MIN_ADAPTIVE_EXPIRY();
            }
            
            public long postDec$MIN_ADAPTIVE_EXPIRY() {
                return ((fabric.metrics.SumMetric._Static._Impl) fetch()).
                  postDec$MIN_ADAPTIVE_EXPIRY();
            }
            
            public _Proxy(fabric.metrics.SumMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.SumMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  SumMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.SumMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.SumMetric._Static._Impl.class);
                $instance = (fabric.metrics.SumMetric._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.SumMetric._Static {
            public long get$MIN_ADAPTIVE_EXPIRY() {
                return this.MIN_ADAPTIVE_EXPIRY;
            }
            
            public long set$MIN_ADAPTIVE_EXPIRY(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.MIN_ADAPTIVE_EXPIRY = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public long postInc$MIN_ADAPTIVE_EXPIRY() {
                long tmp = this.get$MIN_ADAPTIVE_EXPIRY();
                this.set$MIN_ADAPTIVE_EXPIRY((long) (tmp + 1));
                return tmp;
            }
            
            public long postDec$MIN_ADAPTIVE_EXPIRY() {
                long tmp = this.get$MIN_ADAPTIVE_EXPIRY();
                this.set$MIN_ADAPTIVE_EXPIRY((long) (tmp - 1));
                return tmp;
            }
            
            public long MIN_ADAPTIVE_EXPIRY;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeLong(this.MIN_ADAPTIVE_EXPIRY);
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
                this.MIN_ADAPTIVE_EXPIRY = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.SumMetric._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm405 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled408 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff406 = 1;
                        boolean $doBackoff407 = true;
                        boolean $retry402 = true;
                        $label400: for (boolean $commit401 = false; !$commit401;
                                        ) {
                            if ($backoffEnabled408) {
                                if ($doBackoff407) {
                                    if ($backoff406 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff406);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e403) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff406 < 5000) $backoff406 *= 2;
                                }
                                $doBackoff407 = $backoff406 <= 32 ||
                                                  !$doBackoff407;
                            }
                            $commit401 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.SumMetric._Static._Proxy.
                                  $instance.
                                  set$MIN_ADAPTIVE_EXPIRY((long) 1000);
                            }
                            catch (final fabric.worker.RetryException $e403) {
                                $commit401 = false;
                                continue $label400;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e403) {
                                $commit401 = false;
                                fabric.common.TransactionID $currentTid404 =
                                  $tm405.getCurrentTid();
                                if ($e403.tid.isDescendantOf($currentTid404))
                                    continue $label400;
                                if ($currentTid404.parent != null) {
                                    $retry402 = false;
                                    throw $e403;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e403) {
                                $commit401 = false;
                                if ($tm405.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid404 =
                                  $tm405.getCurrentTid();
                                if ($e403.tid.isDescendantOf($currentTid404)) {
                                    $retry402 = true;
                                }
                                else if ($currentTid404.parent != null) {
                                    $retry402 = false;
                                    throw $e403;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e403) {
                                $commit401 = false;
                                if ($tm405.checkForStaleObjects())
                                    continue $label400;
                                $retry402 = false;
                                throw new fabric.worker.AbortException($e403);
                            }
                            finally {
                                if ($commit401) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e403) {
                                        $commit401 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e403) {
                                        $commit401 = false;
                                        fabric.common.TransactionID
                                          $currentTid404 =
                                          $tm405.getCurrentTid();
                                        if ($currentTid404 != null) {
                                            if ($e403.tid.equals(
                                                            $currentTid404) ||
                                                  !$e403.tid.
                                                  isDescendantOf(
                                                    $currentTid404)) {
                                                throw $e403;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit401 && $retry402) {
                                    {  }
                                    continue $label400;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 92, 28, 33, -60, -28,
    -22, 71, -106, 115, 6, -85, 15, -4, -51, 60, 112, -106, 93, -24, -117, -120,
    -13, -68, 87, 47, 127, 51, -128, -89, 75, -68, 32 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1528821850000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
