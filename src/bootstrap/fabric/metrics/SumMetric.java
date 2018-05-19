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
    
    public double computeValue(boolean useWeakCache);
    
    public double computeVelocity(boolean useWeakCache);
    
    public double computeNoise(boolean useWeakCache);
    
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
      thresholdPolicy(double rate, double base, boolean useWeakCache,
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
        
        public double computeValue(boolean useWeakCache) {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).value(useWeakCache);
            }
            return result;
        }
        
        public double computeVelocity(boolean useWeakCache) {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).velocity(useWeakCache);
            }
            return result;
        }
        
        public double computeNoise(boolean useWeakCache) {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).noise(useWeakCache);
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
                    fabric.metrics.DerivedMetric val$var406 = val;
                    fabric.worker.transaction.TransactionManager $tm412 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled415 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff413 = 1;
                    boolean $doBackoff414 = true;
                    boolean $retry409 = true;
                    $label407: for (boolean $commit408 = false; !$commit408; ) {
                        if ($backoffEnabled415) {
                            if ($doBackoff414) {
                                if ($backoff413 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff413);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e410) {
                                            
                                        }
                                    }
                                }
                                if ($backoff413 < 5000) $backoff413 *= 2;
                            }
                            $doBackoff414 = $backoff413 <= 32 || !$doBackoff414;
                        }
                        $commit408 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e410) {
                            $commit408 = false;
                            continue $label407;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e410) {
                            $commit408 = false;
                            fabric.common.TransactionID $currentTid411 =
                              $tm412.getCurrentTid();
                            if ($e410.tid.isDescendantOf($currentTid411))
                                continue $label407;
                            if ($currentTid411.parent != null) {
                                $retry409 = false;
                                throw $e410;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e410) {
                            $commit408 = false;
                            if ($tm412.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid411 =
                              $tm412.getCurrentTid();
                            if ($e410.tid.isDescendantOf($currentTid411)) {
                                $retry409 = true;
                            }
                            else if ($currentTid411.parent != null) {
                                $retry409 = false;
                                throw $e410;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e410) {
                            $commit408 = false;
                            if ($tm412.checkForStaleObjects())
                                continue $label407;
                            $retry409 = false;
                            throw new fabric.worker.AbortException($e410);
                        }
                        finally {
                            if ($commit408) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e410) {
                                    $commit408 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e410) {
                                    $commit408 = false;
                                    fabric.common.TransactionID $currentTid411 =
                                      $tm412.getCurrentTid();
                                    if ($currentTid411 != null) {
                                        if ($e410.tid.equals($currentTid411) ||
                                              !$e410.tid.isDescendantOf(
                                                           $currentTid411)) {
                                            throw $e410;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit408 && $retry409) {
                                { val = val$var406; }
                                continue $label407;
                            }
                        }
                    }
                }
            }
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
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
                    fabric.metrics.DerivedMetric val$var416 = val;
                    fabric.metrics.Metric[] newTerms$var417 = newTerms;
                    int termIdx$var418 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm424 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled427 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff425 = 1;
                    boolean $doBackoff426 = true;
                    boolean $retry421 = true;
                    $label419: for (boolean $commit420 = false; !$commit420; ) {
                        if ($backoffEnabled427) {
                            if ($doBackoff426) {
                                if ($backoff425 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff425);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e422) {
                                            
                                        }
                                    }
                                }
                                if ($backoff425 < 5000) $backoff425 *= 2;
                            }
                            $doBackoff426 = $backoff425 <= 32 || !$doBackoff426;
                        }
                        $commit420 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e422) {
                            $commit420 = false;
                            continue $label419;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e422) {
                            $commit420 = false;
                            fabric.common.TransactionID $currentTid423 =
                              $tm424.getCurrentTid();
                            if ($e422.tid.isDescendantOf($currentTid423))
                                continue $label419;
                            if ($currentTid423.parent != null) {
                                $retry421 = false;
                                throw $e422;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e422) {
                            $commit420 = false;
                            if ($tm424.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid423 =
                              $tm424.getCurrentTid();
                            if ($e422.tid.isDescendantOf($currentTid423)) {
                                $retry421 = true;
                            }
                            else if ($currentTid423.parent != null) {
                                $retry421 = false;
                                throw $e422;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e422) {
                            $commit420 = false;
                            if ($tm424.checkForStaleObjects())
                                continue $label419;
                            $retry421 = false;
                            throw new fabric.worker.AbortException($e422);
                        }
                        finally {
                            if ($commit420) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e422) {
                                    $commit420 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e422) {
                                    $commit420 = false;
                                    fabric.common.TransactionID $currentTid423 =
                                      $tm424.getCurrentTid();
                                    if ($currentTid423 != null) {
                                        if ($e422.tid.equals($currentTid423) ||
                                              !$e422.tid.isDescendantOf(
                                                           $currentTid423)) {
                                            throw $e422;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit420 && $retry421) {
                                {
                                    val = val$var416;
                                    newTerms = newTerms$var417;
                                    termIdx = termIdx$var418;
                                }
                                continue $label419;
                            }
                        }
                    }
                }
            }
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base, boolean useWeakCache,
                          final fabric.worker.Store s) {
            long currentTime = java.lang.System.currentTimeMillis();
            double baseNow =
              fabric.metrics.contracts.Bound._Impl.value(rate, base,
                                                         currentTime);
            double totalSamples = samples(useWeakCache);
            double totalValue = value(useWeakCache);
            double totalVelocity = velocity(useWeakCache);
            double totalNoise = noise(useWeakCache);
            int numTerms = this.get$terms().length();
            fabric.common.ConfigProperties config =
              fabric.worker.Worker.getWorker().config;
            if (config.usePreset) {
                java.util.Map witnesses = new java.util.HashMap();
                for (int j = 0; j < numTerms; j++) {
                    fabric.metrics.Metric m = term(j);
                    double scaledX = m.value(useWeakCache);
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
                      m.velocity(useWeakCache);
                    hasSlackProducer =
                      hasSlackProducer || velocities[j] > rate;
                    noises[j] =
                      m.noise(useWeakCache);
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
                    double scaledX = m.value(useWeakCache);
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
                                             currentTime, useWeakCache));
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
                        double scaledX = m.value(useWeakCache);
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
                                                 useWeakCache));
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
                        fabric.worker.transaction.TransactionManager $tm433 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled436 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff434 = 1;
                        boolean $doBackoff435 = true;
                        boolean $retry430 = true;
                        $label428: for (boolean $commit429 = false; !$commit429;
                                        ) {
                            if ($backoffEnabled436) {
                                if ($doBackoff435) {
                                    if ($backoff434 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff434);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e431) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff434 < 5000) $backoff434 *= 2;
                                }
                                $doBackoff435 = $backoff434 <= 32 ||
                                                  !$doBackoff435;
                            }
                            $commit429 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.SumMetric._Static._Proxy.
                                  $instance.
                                  set$MIN_ADAPTIVE_EXPIRY((long) 1000);
                            }
                            catch (final fabric.worker.RetryException $e431) {
                                $commit429 = false;
                                continue $label428;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e431) {
                                $commit429 = false;
                                fabric.common.TransactionID $currentTid432 =
                                  $tm433.getCurrentTid();
                                if ($e431.tid.isDescendantOf($currentTid432))
                                    continue $label428;
                                if ($currentTid432.parent != null) {
                                    $retry430 = false;
                                    throw $e431;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e431) {
                                $commit429 = false;
                                if ($tm433.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid432 =
                                  $tm433.getCurrentTid();
                                if ($e431.tid.isDescendantOf($currentTid432)) {
                                    $retry430 = true;
                                }
                                else if ($currentTid432.parent != null) {
                                    $retry430 = false;
                                    throw $e431;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e431) {
                                $commit429 = false;
                                if ($tm433.checkForStaleObjects())
                                    continue $label428;
                                $retry430 = false;
                                throw new fabric.worker.AbortException($e431);
                            }
                            finally {
                                if ($commit429) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e431) {
                                        $commit429 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e431) {
                                        $commit429 = false;
                                        fabric.common.TransactionID
                                          $currentTid432 =
                                          $tm433.getCurrentTid();
                                        if ($currentTid432 != null) {
                                            if ($e431.tid.equals(
                                                            $currentTid432) ||
                                                  !$e431.tid.
                                                  isDescendantOf(
                                                    $currentTid432)) {
                                                throw $e431;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit429 && $retry430) {
                                    {  }
                                    continue $label428;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -119, 68, 0, 13, -80,
    36, -7, 127, -72, 71, 81, -39, 80, -47, 34, 31, -84, 33, -48, -77, 79, -85,
    122, 70, 19, 126, -66, -77, 125, 81, -122, -119 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526752273000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
