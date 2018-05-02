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
                    fabric.metrics.DerivedMetric val$var0 = val;
                    fabric.worker.transaction.TransactionManager $tm6 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled9 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff7 = 1;
                    boolean $doBackoff8 = true;
                    boolean $retry3 = true;
                    $label1: for (boolean $commit2 = false; !$commit2; ) {
                        if ($backoffEnabled9) {
                            if ($doBackoff8) {
                                if ($backoff7 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff7);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e4) {
                                            
                                        }
                                    }
                                }
                                if ($backoff7 < 5000) $backoff7 *= 2;
                            }
                            $doBackoff8 = $backoff7 <= 32 || !$doBackoff8;
                        }
                        $commit2 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e4) {
                            $commit2 = false;
                            continue $label1;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e4) {
                            $commit2 = false;
                            fabric.common.TransactionID $currentTid5 =
                              $tm6.getCurrentTid();
                            if ($e4.tid.isDescendantOf($currentTid5))
                                continue $label1;
                            if ($currentTid5.parent != null) {
                                $retry3 = false;
                                throw $e4;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e4) {
                            $commit2 = false;
                            if ($tm6.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid5 =
                              $tm6.getCurrentTid();
                            if ($e4.tid.isDescendantOf($currentTid5)) {
                                $retry3 = true;
                            }
                            else if ($currentTid5.parent != null) {
                                $retry3 = false;
                                throw $e4;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e4) {
                            $commit2 = false;
                            if ($tm6.checkForStaleObjects()) continue $label1;
                            $retry3 = false;
                            throw new fabric.worker.AbortException($e4);
                        }
                        finally {
                            if ($commit2) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.AbortException $e4) {
                                    $commit2 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e4) {
                                    $commit2 = false;
                                    fabric.common.TransactionID $currentTid5 =
                                      $tm6.getCurrentTid();
                                    if ($currentTid5 != null) {
                                        if ($e4.tid.equals($currentTid5) ||
                                              !$e4.tid.isDescendantOf(
                                                         $currentTid5)) {
                                            throw $e4;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit2 && $retry3) {
                                { val = val$var0; }
                                continue $label1;
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
                    fabric.metrics.DerivedMetric val$var10 = val;
                    fabric.metrics.Metric[] newTerms$var11 = newTerms;
                    int termIdx$var12 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm18 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled21 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff19 = 1;
                    boolean $doBackoff20 = true;
                    boolean $retry15 = true;
                    $label13: for (boolean $commit14 = false; !$commit14; ) {
                        if ($backoffEnabled21) {
                            if ($doBackoff20) {
                                if ($backoff19 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff19);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e16) {
                                            
                                        }
                                    }
                                }
                                if ($backoff19 < 5000) $backoff19 *= 2;
                            }
                            $doBackoff20 = $backoff19 <= 32 || !$doBackoff20;
                        }
                        $commit14 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e16) {
                            $commit14 = false;
                            continue $label13;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e16) {
                            $commit14 = false;
                            fabric.common.TransactionID $currentTid17 =
                              $tm18.getCurrentTid();
                            if ($e16.tid.isDescendantOf($currentTid17))
                                continue $label13;
                            if ($currentTid17.parent != null) {
                                $retry15 = false;
                                throw $e16;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e16) {
                            $commit14 = false;
                            if ($tm18.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid17 =
                              $tm18.getCurrentTid();
                            if ($e16.tid.isDescendantOf($currentTid17)) {
                                $retry15 = true;
                            }
                            else if ($currentTid17.parent != null) {
                                $retry15 = false;
                                throw $e16;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e16) {
                            $commit14 = false;
                            if ($tm18.checkForStaleObjects()) continue $label13;
                            $retry15 = false;
                            throw new fabric.worker.AbortException($e16);
                        }
                        finally {
                            if ($commit14) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e16) {
                                    $commit14 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e16) {
                                    $commit14 = false;
                                    fabric.common.TransactionID $currentTid17 =
                                      $tm18.getCurrentTid();
                                    if ($currentTid17 != null) {
                                        if ($e16.tid.equals($currentTid17) ||
                                              !$e16.tid.isDescendantOf(
                                                          $currentTid17)) {
                                            throw $e16;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit14 && $retry15) {
                                {
                                    val = val$var10;
                                    newTerms = newTerms$var11;
                                    termIdx = termIdx$var12;
                                }
                                continue $label13;
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
                java.util.Map adaptiveWitnesses =
                  new java.util.HashMap();
                java.util.Map staticWitnesses =
                  new java.util.HashMap();
                double[] velocities = new double[numTerms];
                double[] adaptiveRates = new double[numTerms];
                double[] staticRates = new double[numTerms];
                double[] noises = new double[numTerms];
                for (int j = 0;
                     j < numTerms; j++) {
                    fabric.metrics.Metric m =
                      term(j);
                    velocities[j] =
                      m.velocity(useWeakCache);
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
                double[] adaptiveSlacks =
                  fabric.worker.metrics.SumStrategy.getSplitEqualVelocity(
                                                      velocities, noises,
                                                      adaptiveRates,
                                                      totalValue - baseNow);
                double[] staticSlacks =
                  fabric.worker.metrics.SumStrategy.getSplit(velocities,
                                                             noises,
                                                             staticRates,
                                                             totalValue -
                                                                 baseNow);
                long adaptiveTimeout = java.lang.Long.MAX_VALUE;
                long staticTimeout = java.lang.Long.MAX_VALUE;
                for (int j = 0; j < numTerms; j++) {
                    fabric.metrics.Metric m = term(j);
                    double scaledX = m.value(useWeakCache);
                    double r = adaptiveRates[j];
                    double b = scaledX - adaptiveSlacks[j];
                    double[] normalized =
                      fabric.metrics.contracts.Bound._Impl.createBound(
                                                             r, b, currentTime);
                    if (!adaptiveWitnesses.
                          containsKey(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(m)) ||
                          !((fabric.metrics.contracts.Contract)
                              fabric.lang.Object._Proxy.
                              $getProxy(
                                fabric.lang.WrappedJavaInlineable.
                                    $wrap(
                                      adaptiveWitnesses.
                                          get(
                                            (java.lang.Object)
                                              fabric.lang.WrappedJavaInlineable.
                                              $unwrap(m))))).implies(
                                                               m, normalized[0],
                                                               normalized[1])) {
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
                              hedgedEstimate(m, normalized[0], normalized[1],
                                             currentTime, useWeakCache));
                    double r2 = staticRates[j];
                    double b2 = scaledX - staticSlacks[j];
                    double[] normalized2 =
                      fabric.metrics.contracts.Bound._Impl.createBound(
                                                             r2, b2,
                                                             currentTime);
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
                                              $unwrap(m))))).
                          implies(m, normalized2[0], normalized2[1])) {
                        staticWitnesses.
                          put(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(m),
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(m.getThresholdContract(r2, b2,
                                                             currentTime)));
                    }
                    staticTimeout =
                      java.lang.Math.
                        min(
                          staticTimeout,
                          fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
                              hedgedEstimate(m, normalized2[0], normalized2[1],
                                             currentTime, useWeakCache));
                }
                fabric.metrics.contracts.Contract[] finalWitnesses =
                  new fabric.metrics.contracts.Contract[adaptiveWitnesses.size(
                                                                            )];
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
                              fabric.lang.WrappedJavaInlineable.$wrap(
                                                                  iter.next()));
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
                        fabric.worker.transaction.TransactionManager $tm27 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled30 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff28 = 1;
                        boolean $doBackoff29 = true;
                        boolean $retry24 = true;
                        $label22: for (boolean $commit23 = false; !$commit23;
                                       ) {
                            if ($backoffEnabled30) {
                                if ($doBackoff29) {
                                    if ($backoff28 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff28);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e25) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff28 < 5000) $backoff28 *= 2;
                                }
                                $doBackoff29 = $backoff28 <= 32 ||
                                                 !$doBackoff29;
                            }
                            $commit23 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.SumMetric._Static._Proxy.
                                  $instance.
                                  set$MIN_ADAPTIVE_EXPIRY((long) 1000);
                            }
                            catch (final fabric.worker.RetryException $e25) {
                                $commit23 = false;
                                continue $label22;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e25) {
                                $commit23 = false;
                                fabric.common.TransactionID $currentTid26 =
                                  $tm27.getCurrentTid();
                                if ($e25.tid.isDescendantOf($currentTid26))
                                    continue $label22;
                                if ($currentTid26.parent != null) {
                                    $retry24 = false;
                                    throw $e25;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e25) {
                                $commit23 = false;
                                if ($tm27.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid26 =
                                  $tm27.getCurrentTid();
                                if ($e25.tid.isDescendantOf($currentTid26)) {
                                    $retry24 = true;
                                }
                                else if ($currentTid26.parent != null) {
                                    $retry24 = false;
                                    throw $e25;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e25) {
                                $commit23 = false;
                                if ($tm27.checkForStaleObjects())
                                    continue $label22;
                                $retry24 = false;
                                throw new fabric.worker.AbortException($e25);
                            }
                            finally {
                                if ($commit23) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e25) {
                                        $commit23 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e25) {
                                        $commit23 = false;
                                        fabric.common.TransactionID
                                          $currentTid26 = $tm27.getCurrentTid();
                                        if ($currentTid26 != null) {
                                            if ($e25.tid.equals(
                                                           $currentTid26) ||
                                                  !$e25.tid.isDescendantOf(
                                                              $currentTid26)) {
                                                throw $e25;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit23 && $retry24) {
                                    {  }
                                    continue $label22;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -126, 0, 4, -15, -9,
    -22, 122, 96, -74, 76, 124, -116, -45, 99, 37, 87, 10, -114, -78, -83, 83,
    -30, 116, -60, -118, 92, -66, 65, 26, 56, -118, 27 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525272775000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
