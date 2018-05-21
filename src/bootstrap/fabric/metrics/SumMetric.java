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
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
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
    
    public static final byte[] $classHash = new byte[] { -36, -34, -101, 71, 33,
    48, 24, -111, 5, 98, -30, -95, -41, -86, 65, 4, -78, -114, -27, 57, 104, 82,
    -35, -99, 100, 111, -42, -26, 21, -69, 97, 36 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526847026000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
