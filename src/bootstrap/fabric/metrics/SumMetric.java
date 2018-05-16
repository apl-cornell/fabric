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
                    fabric.metrics.DerivedMetric val$var120 = val;
                    fabric.worker.transaction.TransactionManager $tm126 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled129 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff127 = 1;
                    boolean $doBackoff128 = true;
                    boolean $retry123 = true;
                    $label121: for (boolean $commit122 = false; !$commit122; ) {
                        if ($backoffEnabled129) {
                            if ($doBackoff128) {
                                if ($backoff127 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff127);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e124) {
                                            
                                        }
                                    }
                                }
                                if ($backoff127 < 5000) $backoff127 *= 2;
                            }
                            $doBackoff128 = $backoff127 <= 32 || !$doBackoff128;
                        }
                        $commit122 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e124) {
                            $commit122 = false;
                            continue $label121;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e124) {
                            $commit122 = false;
                            fabric.common.TransactionID $currentTid125 =
                              $tm126.getCurrentTid();
                            if ($e124.tid.isDescendantOf($currentTid125))
                                continue $label121;
                            if ($currentTid125.parent != null) {
                                $retry123 = false;
                                throw $e124;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e124) {
                            $commit122 = false;
                            if ($tm126.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid125 =
                              $tm126.getCurrentTid();
                            if ($e124.tid.isDescendantOf($currentTid125)) {
                                $retry123 = true;
                            }
                            else if ($currentTid125.parent != null) {
                                $retry123 = false;
                                throw $e124;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e124) {
                            $commit122 = false;
                            if ($tm126.checkForStaleObjects())
                                continue $label121;
                            $retry123 = false;
                            throw new fabric.worker.AbortException($e124);
                        }
                        finally {
                            if ($commit122) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e124) {
                                    $commit122 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e124) {
                                    $commit122 = false;
                                    fabric.common.TransactionID $currentTid125 =
                                      $tm126.getCurrentTid();
                                    if ($currentTid125 != null) {
                                        if ($e124.tid.equals($currentTid125) ||
                                              !$e124.tid.isDescendantOf(
                                                           $currentTid125)) {
                                            throw $e124;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit122 && $retry123) {
                                { val = val$var120; }
                                continue $label121;
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
                    fabric.metrics.DerivedMetric val$var130 = val;
                    fabric.metrics.Metric[] newTerms$var131 = newTerms;
                    int termIdx$var132 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm138 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled141 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff139 = 1;
                    boolean $doBackoff140 = true;
                    boolean $retry135 = true;
                    $label133: for (boolean $commit134 = false; !$commit134; ) {
                        if ($backoffEnabled141) {
                            if ($doBackoff140) {
                                if ($backoff139 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff139);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e136) {
                                            
                                        }
                                    }
                                }
                                if ($backoff139 < 5000) $backoff139 *= 2;
                            }
                            $doBackoff140 = $backoff139 <= 32 || !$doBackoff140;
                        }
                        $commit134 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e136) {
                            $commit134 = false;
                            continue $label133;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e136) {
                            $commit134 = false;
                            fabric.common.TransactionID $currentTid137 =
                              $tm138.getCurrentTid();
                            if ($e136.tid.isDescendantOf($currentTid137))
                                continue $label133;
                            if ($currentTid137.parent != null) {
                                $retry135 = false;
                                throw $e136;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e136) {
                            $commit134 = false;
                            if ($tm138.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid137 =
                              $tm138.getCurrentTid();
                            if ($e136.tid.isDescendantOf($currentTid137)) {
                                $retry135 = true;
                            }
                            else if ($currentTid137.parent != null) {
                                $retry135 = false;
                                throw $e136;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e136) {
                            $commit134 = false;
                            if ($tm138.checkForStaleObjects())
                                continue $label133;
                            $retry135 = false;
                            throw new fabric.worker.AbortException($e136);
                        }
                        finally {
                            if ($commit134) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e136) {
                                    $commit134 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e136) {
                                    $commit134 = false;
                                    fabric.common.TransactionID $currentTid137 =
                                      $tm138.getCurrentTid();
                                    if ($currentTid137 != null) {
                                        if ($e136.tid.equals($currentTid137) ||
                                              !$e136.tid.isDescendantOf(
                                                           $currentTid137)) {
                                            throw $e136;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit134 && $retry135) {
                                {
                                    val = val$var130;
                                    newTerms = newTerms$var131;
                                    termIdx = termIdx$var132;
                                }
                                continue $label133;
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
                        fabric.worker.transaction.TransactionManager $tm147 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled150 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff148 = 1;
                        boolean $doBackoff149 = true;
                        boolean $retry144 = true;
                        $label142: for (boolean $commit143 = false; !$commit143;
                                        ) {
                            if ($backoffEnabled150) {
                                if ($doBackoff149) {
                                    if ($backoff148 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff148);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e145) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff148 < 5000) $backoff148 *= 2;
                                }
                                $doBackoff149 = $backoff148 <= 32 ||
                                                  !$doBackoff149;
                            }
                            $commit143 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.SumMetric._Static._Proxy.
                                  $instance.
                                  set$MIN_ADAPTIVE_EXPIRY((long) 1000);
                            }
                            catch (final fabric.worker.RetryException $e145) {
                                $commit143 = false;
                                continue $label142;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e145) {
                                $commit143 = false;
                                fabric.common.TransactionID $currentTid146 =
                                  $tm147.getCurrentTid();
                                if ($e145.tid.isDescendantOf($currentTid146))
                                    continue $label142;
                                if ($currentTid146.parent != null) {
                                    $retry144 = false;
                                    throw $e145;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e145) {
                                $commit143 = false;
                                if ($tm147.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid146 =
                                  $tm147.getCurrentTid();
                                if ($e145.tid.isDescendantOf($currentTid146)) {
                                    $retry144 = true;
                                }
                                else if ($currentTid146.parent != null) {
                                    $retry144 = false;
                                    throw $e145;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e145) {
                                $commit143 = false;
                                if ($tm147.checkForStaleObjects())
                                    continue $label142;
                                $retry144 = false;
                                throw new fabric.worker.AbortException($e145);
                            }
                            finally {
                                if ($commit143) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e145) {
                                        $commit143 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e145) {
                                        $commit143 = false;
                                        fabric.common.TransactionID
                                          $currentTid146 =
                                          $tm147.getCurrentTid();
                                        if ($currentTid146 != null) {
                                            if ($e145.tid.equals(
                                                            $currentTid146) ||
                                                  !$e145.tid.
                                                  isDescendantOf(
                                                    $currentTid146)) {
                                                throw $e145;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit143 && $retry144) {
                                    {  }
                                    continue $label142;
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
    public static final long jlc$SourceLastModified$fabil = 1526419906000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0Zf3BUR3nvklxyIb9I+E34FQ4qEO6EIrWk7QghgSsHXBNACEL68m4veeTde8d7e+TAQlsVof2DqgWkDmXGEUYsgWqZjrVKp06ltpZxWnSs2FGYcaooYqVYLaNt/b7dvV8vd0cyI8Pbb7P7fbvf7/12b/A6KbMt0hRVejTdz3bGqe1vV3qCobBi2TTSqiu2vQ5Gu9VRpcHDV78bmeom7hCpUhXDNDRV0bsNm5Ga0DZlhxIwKAus7wi2bCZeFQlXKnYfI+7Ny5IWmR439Z29usnkJkPWPzQvcPCbW+ueKyG1XaRWMzqZwjS11TQYTbIuUhWjsR5q2UsjERrpIqMNSiOd1NIUXdsFiKbRReptrddQWMKidge1TX0HItbbiTi1+J6pQWTfBLathMpMC9ivE+wnmKYHQprNWkLEE9WoHrG3kz2kNETKorrSC4jjQikpAnzFQDuOA3qlBmxaUUWlKZLSfs2IMDLNSZGW2LcKEIC0PEZZn5neqtRQYIDUC5Z0xegNdDJLM3oBtcxMwC6MTCq4KCBVxBW1X+ml3YxMcOKFxRRgeblakISRsU40vhLYbJLDZlnWur7mngNfNFYabuICniNU1ZH/CiCa6iDqoFFqUUOlgrBqbuiwMu7cfjchgDzWgSxwfvjQjc81T335NYEzOQ/O2p5tVGXd6vGemrcaW+fcXYJsVMRNW0NXyJGcWzUsZ1qScfD2cekVcdKfmny549VNjzxDr7lJZZB4VFNPxMCrRqtmLK7p1FpBDWopjEaCxEuNSCufD5Jy6Ic0g4rRtdGoTVmQlOp8yGPyv0FFUVgCVVQOfc2Imql+XGF9vJ+ME0LK4SMu+P8qIW3nod9ISEk1IysCfWaMBnr0BB0A9w7ARxVL7QtA3FqaGrAtNWAlDKYBkhwCLwJgBzoTsdW86wcW4v+/pZLIdd2AywUKnaaaEdqj2GAd6SnLwjoEw0pTj1CrW9UPnAuShnNPcW/xoofb4KVcHy6wcKMzN2TTHkwsa7txpvsN4WlIK9UFVhb8+SV//jR/wFIVxo8fMpIfMtKgK+lvPRY8xd3EY/N4Sq9SBassiesKi5pWLElcLi7SGE7P/QOs2w9ZAxJD1ZzOLfc/uL+pBBwzPlCKtgJUnzNMMsklCD0FfL9brd139V/PHt5tZgKGEd+QOB5KiXHY5NSPZao0Ankus/zc6crz3ed2+9yYQ7yQ3pgCDgi5Yqpzj5x4bEnlNtRGWYiMQh0oOk6lElIl67PMgcwIt3sNNvXCBVBZDgZ5Wry3M/70b3/5lzv5gZHKoLVZqbaTspasqMXFanl8js7ofp1FKeD9/kj4yUPX923migeMmfk29GHbCtGqQJia1t7Xtl+6/Ifjv3ZnjMWIJ57o0TU1yWUZ/Qn8c8H3MX4YejiAEBJwqwz76em4j+POszO8QQbQIQsB67ZvvREzI1pUU3p0ip7y39pZC57/24E6YW4dRoTyLNJ8+wUy4xOXkUfe2PrvqXwZl4onUEZ/GTSR1hoyKy+1LGUn8pF89OKUp36uPA2eD0nJ1nZRnmcI1wfhBlzIdTGftwscc4uwaRLaakw7vDPFt+NZmfHFrsDg0Umt910T0Z72RVxjRp5o36BkhcnCZ2IfuJs8592kvIvU8WNaMdgGBbIVuEEXHLR2qxwMkeqc+dxDU5wQLelYa3TGQda2zijIZBnoIzb2K4XjC8cBRUxAJX0WFFJLiO93Er6Isw1xbMckXYR3lnCSmbydjc0crsgS7M5lmI6w0GHEq8ViCYb25zvNY6RhdXBN99LlS8Prghvauts2hoMdm/KoP2xpMYigHfKEpfsPPv6J/8BB4XqiDJk5pBLIphGlCN+1mm+dhF1mFNuFU7T/+dndPz65e584putzD9U2IxE7/ZuPLviPXHk9T+ou1U2RguuS+dXj4upJptXN/3nkiVgloSdL3Vk+SpD/KYWKF8778S8dPBZZe2KBWzp6G+ifmfH5Ot1B9aylMLvNGFIcr+YlW8Zrr1ybcndr/7u9QhPTHDs7sb+3evD1FbPVb7hJSdo9h9SJuUQtuU5ZaVEoc411Oa45Pa2rUaiDNvh8hJQ2C1jyfrZrisTNFY9NME3K1VcpSW5IeM2p5kyycKWTwuRsLd0PcchzkHDJrZDR39z53mGhH2dZmYX4j8HL1y5WTznDj7FSrCi4fM56fGi5nVNFc/Gq0jIFUKbPSHU0SjguWx0cdSxUwo6CQlYTODvRWSgMUQH+vRibL6Qivye/a7tl5JdFNUPRU8Hu0anRy/o48lIZhQiWM1IC4mJ3c2ZTt1gpxbhI/Zj4wM9Ng2IWSbHtRbZ1E+5qaTlFcaOZ/vQNqkdUpwXECguxOA9ZXsO5LHJ6JIrMDWAD9UmZivymGKvLyCESuGCKU2wsstpD2HQyMlEY0CcN6EtXhL6Mx4dz46QJvnngEK9IODiyOEGSUxKeKBwn2cx+ucjcXmz2wI0abx1w2wtjec468nmFJ2ImUmbOI9MiQsqOSrh3ZDIhyVck3DM8mZ4oMvd1bB53yrQMR/cV4v5eSPYJCbeOjHsk2SLh54fH/ZEic9/C5kkn9xuKct8ON7k1Et41Mu6RZLGEnx4e998uMvcdbI46uV+Tj/saJLoLvrWEVNwjYUMB7occ15Bn4pbJIGRpJJkrVrVcq15Cb2GxspOOw93Le0xTp4rBuRgsIvIPsDnB8JGHi8yLw4ICL4FvIyHeRRJWFDHXyaFyIUm5gBUf31Yu/PM0X/WFIgLwIvIs3JVSAlDI3hrbWdRoCrhPs4QlI5MBSdwCem+NQIafFpHhFWx+kjHCGlOz8xqBxwweytug7xGw8oORxQyS/FPCvw8vZn5RZO4CNucZqWCmeHrLczplTeSvDBwSzofvYTBXs4SVI5MQSbwSlhSWMMtE+/iqbxcR8xI2F+EExqceOyVjo6MEWg4Vwg4auW0llJG3AXdYBd/XoHx+UMJPDTOPuCHQ47Aj3Ldx8D5HKqmXy90h4cTCunBnaq26jEL+VEQhV7G5DNKJG1k31wuOvZPPonDgk5Mg7U0JfzUyiyLJRQkvDMuiW/iq7xUR4AY2f4XLVVxP5GWcmyYI34uEjNMkvHO4psHuHwtZBVdaKKFv+FYRQt0qItR/sLnJyChplUKycaP0g66Am1mbBPTdKiBbfqNwkg8lfL+wEKWcvVLuVenmdCqCGmQEDZhWP7UgU5hWuhbPDRzkyOUuLLuLH0YfwUnA+mCNPlOPhE1dU3emtlrsCFZ8a7EUldl+asAOKo1Rg8FVPN3PIh9W2poIyvgRIctnSjh2ZPpEkjES1gwrMbsaiszh7q4aSMx9it3XakZ4iujNxzfkB9dLsOlzEh4eGd9IckjCJ24bnClr1EtrZF1fiti9sYigTdiMhxKfbk8o4jnidBIqrPR1Bp/7Jud5dJc//aitP6PH313VPLbAg/uEIT/GSbozx2orxh9b/7a4f6d+1vGGSEU0oevZz2JZfU/colGNq9UrHsniXIo7oOzMdVDG7+nYQ5lcswQePoIJPPxrHlf1pHTzDl9yUsLCHxIHb47/0FOx7gp/0wVtTX9sOan+vu/Wwy+seOBS+K2maYMz3jy79tSu9oY9L53d/cBXH/sfb+QDEOAcAAA=";
}
