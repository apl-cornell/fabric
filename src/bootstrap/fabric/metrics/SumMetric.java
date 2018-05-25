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
                    fabric.metrics.DerivedMetric val$var369 = val;
                    fabric.worker.transaction.TransactionManager $tm375 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled378 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff376 = 1;
                    boolean $doBackoff377 = true;
                    boolean $retry372 = true;
                    $label370: for (boolean $commit371 = false; !$commit371; ) {
                        if ($backoffEnabled378) {
                            if ($doBackoff377) {
                                if ($backoff376 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff376);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e373) {
                                            
                                        }
                                    }
                                }
                                if ($backoff376 < 5000) $backoff376 *= 2;
                            }
                            $doBackoff377 = $backoff376 <= 32 || !$doBackoff377;
                        }
                        $commit371 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
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
                                    fabric.common.TransactionID $currentTid374 =
                                      $tm375.getCurrentTid();
                                    if ($currentTid374 != null) {
                                        if ($e373.tid.equals($currentTid374) ||
                                              !$e373.tid.isDescendantOf(
                                                           $currentTid374)) {
                                            throw $e373;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                    fabric.metrics.DerivedMetric val$var379 = val;
                    fabric.metrics.Metric[] newTerms$var380 = newTerms;
                    int termIdx$var381 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm387 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled390 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff388 = 1;
                    boolean $doBackoff389 = true;
                    boolean $retry384 = true;
                    $label382: for (boolean $commit383 = false; !$commit383; ) {
                        if ($backoffEnabled390) {
                            if ($doBackoff389) {
                                if ($backoff388 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff388);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e385) {
                                            
                                        }
                                    }
                                }
                                if ($backoff388 < 5000) $backoff388 *= 2;
                            }
                            $doBackoff389 = $backoff388 <= 32 || !$doBackoff389;
                        }
                        $commit383 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e385) {
                            $commit383 = false;
                            continue $label382;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e385) {
                            $commit383 = false;
                            fabric.common.TransactionID $currentTid386 =
                              $tm387.getCurrentTid();
                            if ($e385.tid.isDescendantOf($currentTid386))
                                continue $label382;
                            if ($currentTid386.parent != null) {
                                $retry384 = false;
                                throw $e385;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e385) {
                            $commit383 = false;
                            if ($tm387.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid386 =
                              $tm387.getCurrentTid();
                            if ($e385.tid.isDescendantOf($currentTid386)) {
                                $retry384 = true;
                            }
                            else if ($currentTid386.parent != null) {
                                $retry384 = false;
                                throw $e385;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e385) {
                            $commit383 = false;
                            if ($tm387.checkForStaleObjects())
                                continue $label382;
                            $retry384 = false;
                            throw new fabric.worker.AbortException($e385);
                        }
                        finally {
                            if ($commit383) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e385) {
                                    $commit383 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e385) {
                                    $commit383 = false;
                                    fabric.common.TransactionID $currentTid386 =
                                      $tm387.getCurrentTid();
                                    if ($currentTid386 != null) {
                                        if ($e385.tid.equals($currentTid386) ||
                                              !$e385.tid.isDescendantOf(
                                                           $currentTid386)) {
                                            throw $e385;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit383 && $retry384) {
                                {
                                    val = val$var379;
                                    newTerms = newTerms$var380;
                                    termIdx = termIdx$var381;
                                }
                                continue $label382;
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
                this.MIN_ADAPTIVE_EXPIRY = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.SumMetric._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm396 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled399 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff397 = 1;
                        boolean $doBackoff398 = true;
                        boolean $retry393 = true;
                        $label391: for (boolean $commit392 = false; !$commit392;
                                        ) {
                            if ($backoffEnabled399) {
                                if ($doBackoff398) {
                                    if ($backoff397 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff397);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e394) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff397 < 5000) $backoff397 *= 2;
                                }
                                $doBackoff398 = $backoff397 <= 32 ||
                                                  !$doBackoff398;
                            }
                            $commit392 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.SumMetric._Static._Proxy.
                                  $instance.
                                  set$MIN_ADAPTIVE_EXPIRY((long) 1000);
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
                                        fabric.common.TransactionID
                                          $currentTid395 =
                                          $tm396.getCurrentTid();
                                        if ($currentTid395 != null) {
                                            if ($e394.tid.equals(
                                                            $currentTid395) ||
                                                  !$e394.tid.
                                                  isDescendantOf(
                                                    $currentTid395)) {
                                                throw $e394;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit392 && $retry393) {
                                    {  }
                                    continue $label391;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 120, 67, -14, 27, 90,
    106, 26, -52, -70, 93, -49, 44, -110, 71, -20, 83, 91, -28, -105, -70, 35,
    40, 15, 13, -81, -113, 51, -73, 31, -11, 127, 76 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527195048000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
