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
                    fabric.metrics.DerivedMetric val$var341 = val;
                    fabric.worker.transaction.TransactionManager $tm347 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled350 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff348 = 1;
                    boolean $doBackoff349 = true;
                    boolean $retry344 = true;
                    $label342: for (boolean $commit343 = false; !$commit343; ) {
                        if ($backoffEnabled350) {
                            if ($doBackoff349) {
                                if ($backoff348 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff348);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e345) {
                                            
                                        }
                                    }
                                }
                                if ($backoff348 < 5000) $backoff348 *= 2;
                            }
                            $doBackoff349 = $backoff348 <= 32 || !$doBackoff349;
                        }
                        $commit343 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e345) {
                            $commit343 = false;
                            continue $label342;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e345) {
                            $commit343 = false;
                            fabric.common.TransactionID $currentTid346 =
                              $tm347.getCurrentTid();
                            if ($e345.tid.isDescendantOf($currentTid346))
                                continue $label342;
                            if ($currentTid346.parent != null) {
                                $retry344 = false;
                                throw $e345;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e345) {
                            $commit343 = false;
                            if ($tm347.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid346 =
                              $tm347.getCurrentTid();
                            if ($e345.tid.isDescendantOf($currentTid346)) {
                                $retry344 = true;
                            }
                            else if ($currentTid346.parent != null) {
                                $retry344 = false;
                                throw $e345;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e345) {
                            $commit343 = false;
                            if ($tm347.checkForStaleObjects())
                                continue $label342;
                            $retry344 = false;
                            throw new fabric.worker.AbortException($e345);
                        }
                        finally {
                            if ($commit343) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e345) {
                                    $commit343 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e345) {
                                    $commit343 = false;
                                    fabric.common.TransactionID $currentTid346 =
                                      $tm347.getCurrentTid();
                                    if ($currentTid346 != null) {
                                        if ($e345.tid.equals($currentTid346) ||
                                              !$e345.tid.isDescendantOf(
                                                           $currentTid346)) {
                                            throw $e345;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit343 && $retry344) {
                                { val = val$var341; }
                                continue $label342;
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
                    fabric.metrics.DerivedMetric val$var351 = val;
                    fabric.metrics.Metric[] newTerms$var352 = newTerms;
                    int termIdx$var353 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm359 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled362 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff360 = 1;
                    boolean $doBackoff361 = true;
                    boolean $retry356 = true;
                    $label354: for (boolean $commit355 = false; !$commit355; ) {
                        if ($backoffEnabled362) {
                            if ($doBackoff361) {
                                if ($backoff360 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff360);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e357) {
                                            
                                        }
                                    }
                                }
                                if ($backoff360 < 5000) $backoff360 *= 2;
                            }
                            $doBackoff361 = $backoff360 <= 32 || !$doBackoff361;
                        }
                        $commit355 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e357) {
                            $commit355 = false;
                            continue $label354;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e357) {
                            $commit355 = false;
                            fabric.common.TransactionID $currentTid358 =
                              $tm359.getCurrentTid();
                            if ($e357.tid.isDescendantOf($currentTid358))
                                continue $label354;
                            if ($currentTid358.parent != null) {
                                $retry356 = false;
                                throw $e357;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e357) {
                            $commit355 = false;
                            if ($tm359.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid358 =
                              $tm359.getCurrentTid();
                            if ($e357.tid.isDescendantOf($currentTid358)) {
                                $retry356 = true;
                            }
                            else if ($currentTid358.parent != null) {
                                $retry356 = false;
                                throw $e357;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e357) {
                            $commit355 = false;
                            if ($tm359.checkForStaleObjects())
                                continue $label354;
                            $retry356 = false;
                            throw new fabric.worker.AbortException($e357);
                        }
                        finally {
                            if ($commit355) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e357) {
                                    $commit355 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e357) {
                                    $commit355 = false;
                                    fabric.common.TransactionID $currentTid358 =
                                      $tm359.getCurrentTid();
                                    if ($currentTid358 != null) {
                                        if ($e357.tid.equals($currentTid358) ||
                                              !$e357.tid.isDescendantOf(
                                                           $currentTid358)) {
                                            throw $e357;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit355 && $retry356) {
                                {
                                    val = val$var351;
                                    newTerms = newTerms$var352;
                                    termIdx = termIdx$var353;
                                }
                                continue $label354;
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
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.MIN_ADAPTIVE_EXPIRY = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.SumMetric._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm368 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled371 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff369 = 1;
                        boolean $doBackoff370 = true;
                        boolean $retry365 = true;
                        $label363: for (boolean $commit364 = false; !$commit364;
                                        ) {
                            if ($backoffEnabled371) {
                                if ($doBackoff370) {
                                    if ($backoff369 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff369);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e366) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff369 < 5000) $backoff369 *= 2;
                                }
                                $doBackoff370 = $backoff369 <= 32 ||
                                                  !$doBackoff370;
                            }
                            $commit364 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.SumMetric._Static._Proxy.
                                  $instance.
                                  set$MIN_ADAPTIVE_EXPIRY((long) 1000);
                            }
                            catch (final fabric.worker.RetryException $e366) {
                                $commit364 = false;
                                continue $label363;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e366) {
                                $commit364 = false;
                                fabric.common.TransactionID $currentTid367 =
                                  $tm368.getCurrentTid();
                                if ($e366.tid.isDescendantOf($currentTid367))
                                    continue $label363;
                                if ($currentTid367.parent != null) {
                                    $retry365 = false;
                                    throw $e366;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e366) {
                                $commit364 = false;
                                if ($tm368.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid367 =
                                  $tm368.getCurrentTid();
                                if ($e366.tid.isDescendantOf($currentTid367)) {
                                    $retry365 = true;
                                }
                                else if ($currentTid367.parent != null) {
                                    $retry365 = false;
                                    throw $e366;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e366) {
                                $commit364 = false;
                                if ($tm368.checkForStaleObjects())
                                    continue $label363;
                                $retry365 = false;
                                throw new fabric.worker.AbortException($e366);
                            }
                            finally {
                                if ($commit364) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e366) {
                                        $commit364 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e366) {
                                        $commit364 = false;
                                        fabric.common.TransactionID
                                          $currentTid367 =
                                          $tm368.getCurrentTid();
                                        if ($currentTid367 != null) {
                                            if ($e366.tid.equals(
                                                            $currentTid367) ||
                                                  !$e366.tid.
                                                  isDescendantOf(
                                                    $currentTid367)) {
                                                throw $e366;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit364 && $retry365) {
                                    {  }
                                    continue $label363;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -22, -108, -66, 50,
    113, -76, 70, -4, 104, 123, -33, -54, 12, 37, -66, 105, 1, -109, 95, 19,
    117, 23, 18, -26, -85, -47, -15, -67, 103, 68, 33, -117 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527874708000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
