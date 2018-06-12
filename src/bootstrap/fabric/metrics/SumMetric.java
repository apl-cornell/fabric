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
    
    public static final byte[] $classHash = new byte[] { -91, 38, 2, -13, -72,
    109, 104, -111, 30, 3, 26, -66, -30, 103, -38, 40, -42, -38, -8, 76, -23,
    -36, 20, 65, -40, -12, -125, 67, -108, -110, 97, -12 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1528821850000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
