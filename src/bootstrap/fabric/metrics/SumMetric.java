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
                            try {
                                val =
                                  ((fabric.metrics.SumMetric)
                                     new fabric.metrics.SumMetric._Impl(s).
                                     $getProxy()).fabric$metrics$SumMetric$(
                                                    newTerms);
                            }
                            catch (final fabric.worker.RetryException $e403) {
                                throw $e403;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e403) {
                                throw $e403;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e403) {
                                throw $e403;
                            }
                            catch (final Throwable $e403) {
                                $tm405.getCurrentLog().checkRetrySignal();
                                throw $e403;
                            }
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
                            try {
                                val =
                                  ((fabric.metrics.SumMetric)
                                     new fabric.metrics.SumMetric._Impl(s).
                                     $getProxy()).fabric$metrics$SumMetric$(
                                                    newTerms);
                            }
                            catch (final fabric.worker.RetryException $e415) {
                                throw $e415;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e415) {
                                throw $e415;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e415) {
                                throw $e415;
                            }
                            catch (final Throwable $e415) {
                                $tm417.getCurrentLog().checkRetrySignal();
                                throw $e415;
                            }
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
                                try {
                                    fabric.metrics.SumMetric._Static._Proxy.
                                      $instance.
                                      set$MIN_ADAPTIVE_EXPIRY((long) 1000);
                                }
                                catch (final fabric.worker.
                                         RetryException $e424) {
                                    throw $e424;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e424) {
                                    throw $e424;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e424) {
                                    throw $e424;
                                }
                                catch (final Throwable $e424) {
                                    $tm426.getCurrentLog().checkRetrySignal();
                                    throw $e424;
                                }
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
    
    public static final byte[] $classHash = new byte[] { -27, -81, -114, 77, 89,
    43, -128, -45, 125, 98, 99, 112, 82, 124, -123, -72, -57, -26, -99, -22,
    -117, 101, -27, -86, -122, 34, -55, 81, 75, -102, -112, -118 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1533241119000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
