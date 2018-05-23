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
    
    public static final byte[] $classHash = new byte[] { -41, -23, -26, -113,
    84, 118, 2, 119, -70, -35, 119, 4, -10, 99, 92, -97, -86, 18, 30, -96, 28,
    9, 124, -102, 74, -76, 11, -40, -79, 76, -39, 23 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527095760000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
