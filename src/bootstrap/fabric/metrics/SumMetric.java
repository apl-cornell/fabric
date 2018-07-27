package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Arrays;
import fabric.util.LinkedHashMap;
import fabric.util.Map;
import java.util.Iterator;
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
import fabric.worker.metrics.treaties.statements.EqualityStatement;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;
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
                    fabric.metrics.DerivedMetric val$var399 = val;
                    fabric.worker.transaction.TransactionManager $tm405 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled408 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff406 = 1;
                    boolean $doBackoff407 = true;
                    boolean $retry402 = true;
                    $label400: for (boolean $commit401 = false; !$commit401; ) {
                        if ($backoffEnabled408) {
                            if ($doBackoff407) {
                                if ($backoff406 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff406);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e403) {
                                            
                                        }
                                    }
                                }
                                if ($backoff406 < 5000) $backoff406 *= 2;
                            }
                            $doBackoff407 = $backoff406 <= 32 || !$doBackoff407;
                        }
                        $commit401 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
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
                                    fabric.common.TransactionID $currentTid404 =
                                      $tm405.getCurrentTid();
                                    if ($currentTid404 != null) {
                                        if ($e403.tid.equals($currentTid404) ||
                                              !$e403.tid.isDescendantOf(
                                                           $currentTid404)) {
                                            throw $e403;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit401 && $retry402) {
                                { val = val$var399; }
                                continue $label400;
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
                    fabric.metrics.DerivedMetric val$var409 = val;
                    fabric.metrics.Metric[] newTerms$var410 = newTerms;
                    int termIdx$var411 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm417 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled420 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff418 = 1;
                    boolean $doBackoff419 = true;
                    boolean $retry414 = true;
                    $label412: for (boolean $commit413 = false; !$commit413; ) {
                        if ($backoffEnabled420) {
                            if ($doBackoff419) {
                                if ($backoff418 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff418);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e415) {
                                            
                                        }
                                    }
                                }
                                if ($backoff418 < 5000) $backoff418 *= 2;
                            }
                            $doBackoff419 = $backoff418 <= 32 || !$doBackoff419;
                        }
                        $commit413 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e415) {
                            $commit413 = false;
                            continue $label412;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e415) {
                            $commit413 = false;
                            fabric.common.TransactionID $currentTid416 =
                              $tm417.getCurrentTid();
                            if ($e415.tid.isDescendantOf($currentTid416))
                                continue $label412;
                            if ($currentTid416.parent != null) {
                                $retry414 = false;
                                throw $e415;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e415) {
                            $commit413 = false;
                            if ($tm417.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid416 =
                              $tm417.getCurrentTid();
                            if ($e415.tid.isDescendantOf($currentTid416)) {
                                $retry414 = true;
                            }
                            else if ($currentTid416.parent != null) {
                                $retry414 = false;
                                throw $e415;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e415) {
                            $commit413 = false;
                            if ($tm417.checkForStaleObjects())
                                continue $label412;
                            $retry414 = false;
                            throw new fabric.worker.AbortException($e415);
                        }
                        finally {
                            if ($commit413) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e415) {
                                    $commit413 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e415) {
                                    $commit413 = false;
                                    fabric.common.TransactionID $currentTid416 =
                                      $tm417.getCurrentTid();
                                    if ($currentTid416 != null) {
                                        if ($e415.tid.equals($currentTid416) ||
                                              !$e415.tid.isDescendantOf(
                                                           $currentTid416)) {
                                            throw $e415;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit413 && $retry414) {
                                {
                                    val = val$var409;
                                    newTerms = newTerms$var410;
                                    termIdx = termIdx$var411;
                                }
                                continue $label412;
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
                com.google.common.collect.Multimap witnesses =
                  com.google.common.collect.HashMultimap.create();
                for (int j = 0; j < numTerms; j++) {
                    fabric.metrics.Metric m = term(j);
                    double scaledX = m.value(weakStats);
                    double r = m.getPresetR();
                    double b = scaledX - m.getPresetB() / getPresetB() *
                      (totalValue - baseNow);
                    fabric.worker.metrics.treaties.statements.TreatyStatement
                      newStmt =
                      new fabric.worker.metrics.treaties.statements.ThresholdStatement(
                        r, b, currentTime);
                    boolean implied = false;
                    for (java.util.Iterator iter =
                           witnesses.
                           get((java.lang.Object)
                                 fabric.lang.WrappedJavaInlineable.$unwrap(m)).
                           iterator();
                         iter.hasNext();
                         ) {
                        fabric.worker.metrics.treaties.statements.TreatyStatement
                          stmt =
                          (fabric.worker.metrics.treaties.statements.TreatyStatement)
                            iter.next();
                        if (stmt.implies(newStmt)) {
                            implied = true;
                            break;
                        }
                    }
                    if (!implied) {
                        witnesses.put(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                          m),
                                    newStmt);
                    }
                }
                return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                         witnesses);
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
                com.google.common.collect.Multimap staticWitnesses =
                  com.google.common.collect.HashMultimap.create();
                long staticTimeout = java.lang.Long.MAX_VALUE;
                for (int j = 0; j < numTerms; j++) {
                    fabric.metrics.Metric m = term(j);
                    double scaledX = m.value(weakStats);
                    double r = staticRates[j];
                    double b = scaledX - staticSlacks[j];
                    fabric.worker.metrics.treaties.statements.ThresholdStatement
                      newStmt =
                      new fabric.worker.metrics.treaties.statements.ThresholdStatement(
                        r, b, currentTime);
                    boolean implied = false;
                    for (java.util.Iterator iter =
                           staticWitnesses.
                           get((java.lang.Object)
                                 fabric.lang.WrappedJavaInlineable.$unwrap(m)).
                           iterator();
                         iter.hasNext();
                         ) {
                        fabric.worker.metrics.treaties.statements.TreatyStatement
                          stmt =
                          (fabric.worker.metrics.treaties.statements.TreatyStatement)
                            iter.next();
                        if (stmt.implies(newStmt)) {
                            implied = true;
                            break;
                        }
                    }
                    if (!implied) {
                        staticWitnesses.
                          put((java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.$unwrap(m),
                              newStmt);
                        staticTimeout =
                          java.lang.Math.
                            min(
                              staticTimeout,
                              fabric.worker.metrics.treaties.statements.ThresholdStatement.
                                  hedgedEstimate(m, newStmt.rate, newStmt.base,
                                                 currentTime, weakStats));
                    }
                }
                com.google.common.collect.Multimap finalWitnesses =
                  staticWitnesses;
                if (config.useDynamic && hasSlackProducer &&
                      (!isSingleStore() || rate > 0)) {
                    double[] adaptiveSlacks =
                      fabric.worker.metrics.SumStrategy.getSplitEqualVelocity(
                                                          velocities, noises,
                                                          adaptiveRates,
                                                          totalValue - baseNow);
                    com.google.common.collect.Multimap adaptiveWitnesses =
                      com.google.common.collect.HashMultimap.create();
                    long adaptiveTimeout = java.lang.Long.MAX_VALUE;
                    for (int j = 0; j < numTerms; j++) {
                        fabric.metrics.Metric m = term(j);
                        double scaledX = m.value(weakStats);
                        double r = adaptiveRates[j];
                        double b = scaledX - adaptiveSlacks[j];
                        fabric.worker.metrics.treaties.statements.ThresholdStatement
                          newStmt =
                          new fabric.worker.metrics.treaties.statements.ThresholdStatement(
                            r, b, currentTime);
                        boolean implied = false;
                        for (java.util.Iterator iter =
                               adaptiveWitnesses.
                               get(
                                 (java.lang.Object)
                                   fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                       m)).
                               iterator();
                             iter.hasNext();
                             ) {
                            fabric.worker.metrics.treaties.statements.TreatyStatement
                              stmt =
                              (fabric.worker.metrics.treaties.statements.TreatyStatement)
                                iter.next();
                            if (stmt.implies(newStmt)) {
                                implied = true;
                                break;
                            }
                        }
                        if (!implied) {
                            adaptiveWitnesses.
                              put(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(m),
                                newStmt);
                            adaptiveTimeout =
                              java.lang.Math.
                                min(
                                  adaptiveTimeout,
                                  fabric.worker.metrics.treaties.statements.ThresholdStatement.
                                      hedgedEstimate(m, newStmt.rate,
                                                     newStmt.base, currentTime,
                                                     weakStats));
                        }
                    }
                    if (adaptiveTimeout >= staticTimeout) {
                        finalWitnesses = adaptiveWitnesses;
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
                        fabric.worker.transaction.TransactionManager $tm426 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled429 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff427 = 1;
                        boolean $doBackoff428 = true;
                        boolean $retry423 = true;
                        $label421: for (boolean $commit422 = false; !$commit422;
                                        ) {
                            if ($backoffEnabled429) {
                                if ($doBackoff428) {
                                    if ($backoff427 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff427);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e424) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff427 < 5000) $backoff427 *= 2;
                                }
                                $doBackoff428 = $backoff427 <= 32 ||
                                                  !$doBackoff428;
                            }
                            $commit422 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.SumMetric._Static._Proxy.
                                  $instance.
                                  set$MIN_ADAPTIVE_EXPIRY((long) 1000);
                            }
                            catch (final fabric.worker.RetryException $e424) {
                                $commit422 = false;
                                continue $label421;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e424) {
                                $commit422 = false;
                                fabric.common.TransactionID $currentTid425 =
                                  $tm426.getCurrentTid();
                                if ($e424.tid.isDescendantOf($currentTid425))
                                    continue $label421;
                                if ($currentTid425.parent != null) {
                                    $retry423 = false;
                                    throw $e424;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e424) {
                                $commit422 = false;
                                if ($tm426.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid425 =
                                  $tm426.getCurrentTid();
                                if ($e424.tid.isDescendantOf($currentTid425)) {
                                    $retry423 = true;
                                }
                                else if ($currentTid425.parent != null) {
                                    $retry423 = false;
                                    throw $e424;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e424) {
                                $commit422 = false;
                                if ($tm426.checkForStaleObjects())
                                    continue $label421;
                                $retry423 = false;
                                throw new fabric.worker.AbortException($e424);
                            }
                            finally {
                                if ($commit422) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e424) {
                                        $commit422 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e424) {
                                        $commit422 = false;
                                        fabric.common.TransactionID
                                          $currentTid425 =
                                          $tm426.getCurrentTid();
                                        if ($currentTid425 != null) {
                                            if ($e424.tid.equals(
                                                            $currentTid425) ||
                                                  !$e424.tid.
                                                  isDescendantOf(
                                                    $currentTid425)) {
                                                throw $e424;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit422 && $retry423) {
                                    {  }
                                    continue $label421;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -34, 82, 4, 94, -51,
    15, 70, 96, 74, -25, 22, -41, -56, 22, 51, 22, 67, -128, 45, 76, 127, -52,
    -75, 123, -56, 40, 15, -29, 108, -21, 0, 57 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1532375616000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0ZbWwUx3XufD5/YLCxwYAxBowh5etOkLYqMY2CDxsOznC1DQI75bK3N+db2Ns9dufwmYQ0bZSC8oOqgQCpAlJUoqapATUqqqrIVdqSlIhQNelH0ooS1AqRyKENjdpGVdr0vdm5r70P7CqInTfeee/N+543e2O3SaVpkPaoFFZUDxtNUNPTI4X9gaBkmDTiUyXTHIC3IXmay3/ive9F2pzEGSB1sqTpmiJLakgzGZkR2CsdkLwaZd4dff7OIVIjI+FmyYwx4hzqShlkUUJXR4dVnYlNCvg/vdJ7/OSehpcqSP0gqVe0fiYxRfbpGqMpNkjq4jQepoa5IRKhkUEyU6M00k8NRVKVg4Coa4Ok0VSGNYklDWr2UVNXDyBio5lMUIPvmX6J4usgtpGUmW6A+A2W+EmmqN6AYrLOAHFHFapGzP3kUeIKkMqoKg0DYnMgrYWXc/T24HtAr1VATCMqyTRN4tqnaBFGFtopMhp3bAUEIK2KUxbTM1u5NAlekEZLJFXShr39zFC0YUCt1JOwCyMtJZkCUnVCkvdJwzTEyFw7XtBaAqwabhYkYWS2HY1zAp+12HyW463b29YffVjbrDmJA2SOUFlF+auBqM1G1Eej1KCaTC3CuhWBE1Lz+BEnIYA824Zs4fz4kTsPrGp75bKFM78IzvbwXiqzkHw2POPNVt/ydRUoRnVCNxUMhTzNuVeDYqUzlYBob85wxEVPevGVvtd2P/YinXCSWj9xy7qajENUzZT1eEJRqbGJatSQGI34SQ3VIj6+7idVMA8oGrXebo9GTcr8xKXyV26d/w0migILNFEVzBUtqqfnCYnF+DyVIIRUwUMc8P93hHR/BPMlhLgYI5u8MT1OvWE1SUcgvL3wUMmQY17IW0ORvaYhe42kxhRAEq8gigCY3v5kvJdPPSBC4rNjlUKpG0YcDjDoQlmP0LBkgndEpHQFVUiGzboaoUZIVo+O+0nT+DM8Wmowwk2IUm4PB3i41V4bcmmPJ7u675wPXbEiDWmFucDLlnweIZ8nIx+IVIf544GK5IGKNOZIeXxn/D/gYeI2eT5luNQBl/sSqsSiuhFPEYeDqzSL0/P4AO/ug6oBhaFuef9Xtzx0pL0CAjMx4kJfAWqHPU2yxcUPMwliPyTXH37vnxdOHNKzCcNIR0EeF1JiHrbb7WPoMo1AncuyX7FIuhgaP9ThxBpSA+WNSRCAUCva7Hvk5WNnurahNSoDZBraQFJxKV2QalnM0Eeyb7jfZ+DQaIUAGssmIC+LX+5PnH7nV+/fyw+MdAWtzym1/ZR15mQtMqvn+Tkza/sBg1LA+9Op4LGnbx8e4oYHjCXFNuzA0QfZKkGa6sYTl/f/4d3rZ3/rzDqLEXciGVYVOcV1mfkp/HPA8198MPXwBUIowD6R9osyeZ/AnZdlZYMKoEIVAtHNjh1aXI8oUUUKqxQj5ZP6pWsufnC0wXK3Cm8s4xlk1d0ZZN/P6yKPXdnzrzbOxiHjCZS1XxbNKmtNWc4bDEMaRTlSX39rwTO/lE5D5ENRMpWDlNcZwu1BuAPXclus5uMa29rncWi3rNWaCXh7ie/BszIbi4PesWdbfPdPWNmeiUXksbhItu+UctJk7Yvxfzjb3a86SdUgaeDHtKSxnRJUKwiDQThoTZ94GSDT89bzD03rhOjM5FqrPQ9ytrVnQbbKwByxcV5rBb4VOGCIuWikL4FBmglZekfAN3G1KYHjrJSD8Ml9nGQJH5fhsJwbsgKnKxiWI2x0GKlR4vEkQ//znVYy0tTr3xbasHFDcMC/szvUvSvo79tdxPxBQ4lDBh0QJyw9cvzJTz1Hj1uhZ7UhSwo6gVwaqxXhu07nW6dgl8XlduEUPbcuHHr5hUOHrWO6Mf9Q7daS8XO//88bnlM3Xi9Sul2qbpXghlRx8zi4eVIZc/N/bnEimgLGc8ydE6ME5V9Qqnnhsp/9xvEzke3Pr3GKQO8G+zM9sVqlB6iawwqr2+KC5riXt2zZqL0xsWCdb9/NYcsSC20727G/3zv2+qZl8lNOUpEJz4I+MZ+oMz8oaw0Kba42kBeaizK2moY26IZnJSGVxwTcnRuaVuHmhsfBnyHl5qsVJLsE7LObOVssHJmiMD/XSlsgD3kNskJyD1T0X4/+7YRlH3tbmYP44di7E29NX3CeH2Mu7Ci4fvZ+vLDdzuuiuXp1GZ2+gDp1wtMHpmkQsJaRrf9/J7QRbh5wk8hrrD5Ldlbcz4be3NbiWCi42FLgA/z7izg8mC494eK55RSlpzKqaJKarjZulWrDLMaRN4gygGAjIxVgb5wOpTKbOi1OaTmtswcrLySarlEsY3xtHiQWNliqDpfFjFpWd6XonswVLmy1x/FUUbWCllpchpyw5VKWOb6SZdZGcIAGqVJGedOCNWT1sE4QSyhOsasMt0dw6GdknuWvDuGvjkxL2pFNuWB+orbDsxZq2woBm6aWqEjSKGBd6UTNFfbxMmtP4PAoXOnx2gPXzSCGMesrFhXuiJ5Mu7mITpBxVRUWdH8wNZ2QZELAm5PT6Vtl1r6Nw5N2nbrw7eFS0m8E6a8J+OrUpEeSSwL+dHLSnyqz9h0cjtml31lW+l5Cqi8KeGZq0iPJaQFPTk7658qsfReHZ+3Sbysm/QwiivVOQmqeE/BgCekL+gWoMwlDZ5CyNJLKV2u64DUqYKK0WrlFB8pBmyi/I7qxjxrZiya0bGavlEhXuPwrJBdzrIxNXsLheYafobhNePta0iLQY5IQ+OYpAWNl/PlCoeJIMizgnrsqjn+e41x/UkaBl3H4Edzm0gpQKO8KGy3r1RjE5+MCPjg1HZBkSMC7NyNZHX5eRodLOIxnnbBNV8yiTuBJ1QrPfkLqdgm4aWpJhSQ9Aj4wuaS6UmbtKg6vMVLNdOvjYJHjK2ehIEKLabganm8SUn9IwKGpaYgkgwIOTMpFhznXd8qo+UccfgNHNPZMZlrHVltLlNc8IU6LTb0mZLgVnpOEzPyhgKOTrCtORqoSsIHEeHDcbystjYJdSsC9pVV3Znuvhqz+N8vofwuH6+A864oY4mbAd9eKORA6dXKBkNn3CFg1NQciiVtAMikHhjjXv5ZR4EMc3ofbXkJNFhWcu8YPz88ImXNawOHJuganfy7lFeQUFXDX5L1iKfVxGaX+jcPfGZkmvFJKN+4UBWw1j5BlmoANU3IKJ6kXsKa0Ei4unotHVWY4l06YpvxDrJ/pRqY3Lzy5HI7Sujt4TH0ChZ/FgEdMVyNBXVXk0fRW64ufl8ygYCq46lEN9pFpnGrM052dW0yQR7yYEUF7x2Xop74ioG9qRkSSLgHXT6r4OhrKrDXiMA2Kb0wyYz49wuvCcDG5IRMdV2HTGwJemprcSPILAcfvmpFpFzQKF+TcYco4e14ZRRfiMAv6fLo/KalW/bVdAarCuq5SSUtB95W56uC3yPlFfhEQv0vJvkv07M2tq2aX+DVgbsEvhYLu/Jn66jlndrxtfRxI/+ZUEyDV0aSq5n6zy5m7EwaNKtzaNdYXvARX7h5oSfMPEsY/IuAMtXMstfDwC52Fh3+t5B5oyQzXOMuWpIG/co59NOdjd/XADf7BGYy46Hqfa8/V+p6Httxqfvty873NvsdWB772xsWHL3+u/i/qBFn3P9YSHCV9HQAA";
}
