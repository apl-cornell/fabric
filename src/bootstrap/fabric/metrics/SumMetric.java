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
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
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
                    fabric.metrics.DerivedMetric val$var389 = val;
                    fabric.metrics.Metric[] newTerms$var390 = newTerms;
                    int termIdx$var391 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm397 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled400 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff398 = 1;
                    boolean $doBackoff399 = true;
                    boolean $retry394 = true;
                    $label392: for (boolean $commit393 = false; !$commit393; ) {
                        if ($backoffEnabled400) {
                            if ($doBackoff399) {
                                if ($backoff398 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff398);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e395) {
                                            
                                        }
                                    }
                                }
                                if ($backoff398 < 5000) $backoff398 *= 2;
                            }
                            $doBackoff399 = $backoff398 <= 32 || !$doBackoff399;
                        }
                        $commit393 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e395) {
                            $commit393 = false;
                            continue $label392;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e395) {
                            $commit393 = false;
                            fabric.common.TransactionID $currentTid396 =
                              $tm397.getCurrentTid();
                            if ($e395.tid.isDescendantOf($currentTid396))
                                continue $label392;
                            if ($currentTid396.parent != null) {
                                $retry394 = false;
                                throw $e395;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e395) {
                            $commit393 = false;
                            if ($tm397.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid396 =
                              $tm397.getCurrentTid();
                            if ($e395.tid.isDescendantOf($currentTid396)) {
                                $retry394 = true;
                            }
                            else if ($currentTid396.parent != null) {
                                $retry394 = false;
                                throw $e395;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e395) {
                            $commit393 = false;
                            if ($tm397.checkForStaleObjects())
                                continue $label392;
                            $retry394 = false;
                            throw new fabric.worker.AbortException($e395);
                        }
                        finally {
                            if ($commit393) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e395) {
                                    $commit393 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e395) {
                                    $commit393 = false;
                                    fabric.common.TransactionID $currentTid396 =
                                      $tm397.getCurrentTid();
                                    if ($currentTid396 != null) {
                                        if ($e395.tid.equals($currentTid396) ||
                                              !$e395.tid.isDescendantOf(
                                                           $currentTid396)) {
                                            throw $e395;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit393 && $retry394) {
                                {
                                    val = val$var389;
                                    newTerms = newTerms$var390;
                                    termIdx = termIdx$var391;
                                }
                                continue $label392;
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
            if (config.usePresetStrategy) {
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
                if (config.useDynamic && hasSlackProducer &&
                      (!isSingleStore() || rate > 0)) {
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
                    if (adaptiveTimeout >= staticTimeout) {
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
                        fabric.worker.transaction.TransactionManager $tm406 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled409 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff407 = 1;
                        boolean $doBackoff408 = true;
                        boolean $retry403 = true;
                        $label401: for (boolean $commit402 = false; !$commit402;
                                        ) {
                            if ($backoffEnabled409) {
                                if ($doBackoff408) {
                                    if ($backoff407 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff407);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e404) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff407 < 5000) $backoff407 *= 2;
                                }
                                $doBackoff408 = $backoff407 <= 32 ||
                                                  !$doBackoff408;
                            }
                            $commit402 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.SumMetric._Static._Proxy.
                                  $instance.
                                  set$MIN_ADAPTIVE_EXPIRY((long) 1000);
                            }
                            catch (final fabric.worker.RetryException $e404) {
                                $commit402 = false;
                                continue $label401;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e404) {
                                $commit402 = false;
                                fabric.common.TransactionID $currentTid405 =
                                  $tm406.getCurrentTid();
                                if ($e404.tid.isDescendantOf($currentTid405))
                                    continue $label401;
                                if ($currentTid405.parent != null) {
                                    $retry403 = false;
                                    throw $e404;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e404) {
                                $commit402 = false;
                                if ($tm406.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid405 =
                                  $tm406.getCurrentTid();
                                if ($e404.tid.isDescendantOf($currentTid405)) {
                                    $retry403 = true;
                                }
                                else if ($currentTid405.parent != null) {
                                    $retry403 = false;
                                    throw $e404;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e404) {
                                $commit402 = false;
                                if ($tm406.checkForStaleObjects())
                                    continue $label401;
                                $retry403 = false;
                                throw new fabric.worker.AbortException($e404);
                            }
                            finally {
                                if ($commit402) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e404) {
                                        $commit402 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e404) {
                                        $commit402 = false;
                                        fabric.common.TransactionID
                                          $currentTid405 =
                                          $tm406.getCurrentTid();
                                        if ($currentTid405 != null) {
                                            if ($e404.tid.equals(
                                                            $currentTid405) ||
                                                  !$e404.tid.
                                                  isDescendantOf(
                                                    $currentTid405)) {
                                                throw $e404;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit402 && $retry403) {
                                    {  }
                                    continue $label401;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -21, 61, -24, -17, -53,
    -15, 105, -103, -71, 113, 123, -2, -88, 117, -5, -30, 66, -101, 37, -25,
    115, 83, 105, -26, 90, 8, 66, 78, -87, -60, -77, -126 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1531410311000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
