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
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.metrics.SumStrategy;
import fabric.worker.metrics.StatsMap;
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
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
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
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
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
                          !((fabric.metrics.contracts.Contract)
                              fabric.lang.Object._Proxy.
                              $getProxy(
                                fabric.lang.WrappedJavaInlineable.
                                    $wrap(
                                      witnesses.
                                          get(
                                            (java.lang.Object)
                                              fabric.lang.WrappedJavaInlineable.
                                              $unwrap(m))))).implies(
                                                               m, normalized[0],
                                                               normalized[1])) {
                        witnesses.
                          put(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(m),
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(m.getThresholdContract(r, b,
                                                             currentTime)));
                    }
                }
                fabric.metrics.contracts.Contract[] finalWitnesses =
                  new fabric.metrics.contracts.Contract[witnesses.size()];
                int i = 0;
                for (java.util.Iterator iter = witnesses.values().iterator();
                     iter.hasNext(); ) {
                    finalWitnesses[i++] =
                      (fabric.metrics.contracts.Contract)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          fabric.lang.WrappedJavaInlineable.$wrap(iter.next()));
                }
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                          new fabric.metrics.contracts.enforcement.
                            WitnessPolicy._Impl(s).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$WitnessPolicy$(
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
                          !((fabric.metrics.contracts.Contract)
                              fabric.lang.Object._Proxy.
                              $getProxy(
                                fabric.lang.WrappedJavaInlineable.
                                    $wrap(
                                      staticWitnesses.
                                          get(
                                            (java.lang.Object)
                                              fabric.lang.WrappedJavaInlineable.
                                              $unwrap(m))))).implies(
                                                               m, normalized[0],
                                                               normalized[1])) {
                        staticWitnesses.
                          put(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(m),
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(m.getThresholdContract(r, b,
                                                             currentTime)));
                    }
                    staticTimeout =
                      java.lang.Math.
                        min(
                          staticTimeout,
                          fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
                              hedgedEstimate(m, normalized[0], normalized[1],
                                             currentTime, weakStats));
                }
                fabric.metrics.contracts.Contract[] finalWitnesses =
                  new fabric.metrics.contracts.Contract[staticWitnesses.size()];
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
                              !((fabric.metrics.contracts.Contract)
                                  fabric.lang.Object._Proxy.
                                  $getProxy(
                                    fabric.lang.WrappedJavaInlineable.
                                        $wrap(
                                          adaptiveWitnesses.
                                              get(
                                                (java.lang.Object)
                                                  fabric.lang.WrappedJavaInlineable.
                                                  $unwrap(m))))).
                              implies(m, normalized[0], normalized[1])) {
                            adaptiveWitnesses.
                              put(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(m),
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.
                                  $unwrap(m.getThresholdContract(r, b,
                                                                 currentTime)));
                        }
                        adaptiveTimeout =
                          java.lang.Math.
                            min(
                              adaptiveTimeout,
                              fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
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
                              (fabric.metrics.contracts.Contract)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  fabric.lang.WrappedJavaInlineable.
                                      $wrap(iter.next()));
                        }
                    }
                    else {
                        int i = 0;
                        for (java.util.Iterator iter =
                               staticWitnesses.values().iterator();
                             iter.hasNext();
                             ) {
                            finalWitnesses[i++] =
                              (fabric.metrics.contracts.Contract)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  fabric.lang.WrappedJavaInlineable.
                                      $wrap(iter.next()));
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
                          (fabric.metrics.contracts.Contract)
                            fabric.lang.Object._Proxy.
                            $getProxy(
                              fabric.lang.WrappedJavaInlineable.$wrap(
                                                                  iter.next()));
                    }
                }
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                          new fabric.metrics.contracts.enforcement.
                            WitnessPolicy._Impl(s).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$WitnessPolicy$(
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
    
    public static final byte[] $classHash = new byte[] { -125, -71, 6, -77, 84,
    -81, 14, 105, -116, 47, -72, 25, -99, -30, -60, 59, 119, -117, -71, -71, 48,
    -57, 27, -88, 109, -63, -56, -106, -40, -51, -31, 22 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527095760000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
