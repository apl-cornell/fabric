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
                    fabric.metrics.DerivedMetric val$var397 = val;
                    fabric.worker.transaction.TransactionManager $tm404 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled407 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff405 = 1;
                    boolean $doBackoff406 = true;
                    boolean $retry400 = true;
                    boolean $keepReads401 = false;
                    $label398: for (boolean $commit399 = false; !$commit399; ) {
                        if ($backoffEnabled407) {
                            if ($doBackoff406) {
                                if ($backoff405 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff405));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e402) {
                                            
                                        }
                                    }
                                }
                                if ($backoff405 < 5000) $backoff405 *= 2;
                            }
                            $doBackoff406 = $backoff405 <= 32 || !$doBackoff406;
                        }
                        $commit399 = true;
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
                            catch (final fabric.worker.RetryException $e402) {
                                throw $e402;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e402) {
                                throw $e402;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e402) {
                                throw $e402;
                            }
                            catch (final Throwable $e402) {
                                $tm404.getCurrentLog().checkRetrySignal();
                                throw $e402;
                            }
                        }
                        catch (final fabric.worker.RetryException $e402) {
                            $commit399 = false;
                            continue $label398;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e402) {
                            $commit399 = false;
                            $retry400 = false;
                            $keepReads401 = $e402.keepReads;
                            if ($tm404.checkForStaleObjects()) {
                                $retry400 = true;
                                $keepReads401 = false;
                                continue $label398;
                            }
                            fabric.common.TransactionID $currentTid403 =
                              $tm404.getCurrentTid();
                            if ($e402.tid == null ||
                                  !$e402.tid.isDescendantOf($currentTid403)) {
                                throw $e402;
                            }
                            throw new fabric.worker.UserAbortException($e402);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e402) {
                            $commit399 = false;
                            fabric.common.TransactionID $currentTid403 =
                              $tm404.getCurrentTid();
                            if ($e402.tid.isDescendantOf($currentTid403))
                                continue $label398;
                            if ($currentTid403.parent != null) {
                                $retry400 = false;
                                throw $e402;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e402) {
                            $commit399 = false;
                            if ($tm404.checkForStaleObjects())
                                continue $label398;
                            $retry400 = false;
                            throw new fabric.worker.AbortException($e402);
                        }
                        finally {
                            if ($commit399) {
                                fabric.common.TransactionID $currentTid403 =
                                  $tm404.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e402) {
                                    $commit399 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e402) {
                                    $commit399 = false;
                                    $retry400 = false;
                                    $keepReads401 = $e402.keepReads;
                                    if ($tm404.checkForStaleObjects()) {
                                        $retry400 = true;
                                        $keepReads401 = false;
                                        continue $label398;
                                    }
                                    if ($e402.tid ==
                                          null ||
                                          !$e402.tid.isDescendantOf(
                                                       $currentTid403))
                                        throw $e402;
                                    throw new fabric.worker.UserAbortException(
                                            $e402);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e402) {
                                    $commit399 = false;
                                    $currentTid403 = $tm404.getCurrentTid();
                                    if ($currentTid403 != null) {
                                        if ($e402.tid.equals($currentTid403) ||
                                              !$e402.tid.isDescendantOf(
                                                           $currentTid403)) {
                                            throw $e402;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads401) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit399) {
                                { val = val$var397; }
                                if ($retry400) { continue $label398; }
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
                    fabric.metrics.DerivedMetric val$var408 = val;
                    fabric.metrics.Metric[] newTerms$var409 = newTerms;
                    int termIdx$var410 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm417 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled420 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff418 = 1;
                    boolean $doBackoff419 = true;
                    boolean $retry413 = true;
                    boolean $keepReads414 = false;
                    $label411: for (boolean $commit412 = false; !$commit412; ) {
                        if ($backoffEnabled420) {
                            if ($doBackoff419) {
                                if ($backoff418 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff418));
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
                        $commit412 = true;
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
                                     TransactionAbortingException $e415) {
                                throw $e415;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e415) {
                                throw $e415;
                            }
                            catch (final Throwable $e415) {
                                $tm417.getCurrentLog().checkRetrySignal();
                                throw $e415;
                            }
                        }
                        catch (final fabric.worker.RetryException $e415) {
                            $commit412 = false;
                            continue $label411;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e415) {
                            $commit412 = false;
                            $retry413 = false;
                            $keepReads414 = $e415.keepReads;
                            if ($tm417.checkForStaleObjects()) {
                                $retry413 = true;
                                $keepReads414 = false;
                                continue $label411;
                            }
                            fabric.common.TransactionID $currentTid416 =
                              $tm417.getCurrentTid();
                            if ($e415.tid == null ||
                                  !$e415.tid.isDescendantOf($currentTid416)) {
                                throw $e415;
                            }
                            throw new fabric.worker.UserAbortException($e415);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e415) {
                            $commit412 = false;
                            fabric.common.TransactionID $currentTid416 =
                              $tm417.getCurrentTid();
                            if ($e415.tid.isDescendantOf($currentTid416))
                                continue $label411;
                            if ($currentTid416.parent != null) {
                                $retry413 = false;
                                throw $e415;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e415) {
                            $commit412 = false;
                            if ($tm417.checkForStaleObjects())
                                continue $label411;
                            $retry413 = false;
                            throw new fabric.worker.AbortException($e415);
                        }
                        finally {
                            if ($commit412) {
                                fabric.common.TransactionID $currentTid416 =
                                  $tm417.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e415) {
                                    $commit412 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e415) {
                                    $commit412 = false;
                                    $retry413 = false;
                                    $keepReads414 = $e415.keepReads;
                                    if ($tm417.checkForStaleObjects()) {
                                        $retry413 = true;
                                        $keepReads414 = false;
                                        continue $label411;
                                    }
                                    if ($e415.tid ==
                                          null ||
                                          !$e415.tid.isDescendantOf(
                                                       $currentTid416))
                                        throw $e415;
                                    throw new fabric.worker.UserAbortException(
                                            $e415);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e415) {
                                    $commit412 = false;
                                    $currentTid416 = $tm417.getCurrentTid();
                                    if ($currentTid416 != null) {
                                        if ($e415.tid.equals($currentTid416) ||
                                              !$e415.tid.isDescendantOf(
                                                           $currentTid416)) {
                                            throw $e415;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads414) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit412) {
                                {
                                    val = val$var408;
                                    newTerms = newTerms$var409;
                                    termIdx = termIdx$var410;
                                }
                                if ($retry413) { continue $label411; }
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
                      fabric.worker.metrics.treaties.statements.ThresholdStatement.
                      create(r, b, currentTime);
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
                return fabric.worker.metrics.treaties.enforcement.WitnessPolicy.
                  create(witnesses);
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
                long staticRealTimeout = java.lang.Long.MAX_VALUE;
                for (int j = 0; j < numTerms; j++) {
                    fabric.metrics.Metric m = term(j);
                    double scaledX = m.value(weakStats);
                    double r = staticRates[j];
                    double b = scaledX - staticSlacks[j];
                    fabric.worker.metrics.treaties.statements.ThresholdStatement
                      newStmt =
                      fabric.worker.metrics.treaties.statements.ThresholdStatement.
                      create(r, b, currentTime);
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
                                  hedgedEstimate(m, newStmt.rate(),
                                                 newStmt.base(), currentTime,
                                                 weakStats));
                        staticRealTimeout =
                          java.lang.Math.
                            min(
                              staticRealTimeout,
                              fabric.worker.metrics.treaties.statements.ThresholdStatement.
                                  hedgedExpiry(m, newStmt.rate(),
                                               newStmt.base(), currentTime,
                                               weakStats));
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
                    long adaptiveRealTimeout = java.lang.Long.MAX_VALUE;
                    for (int j = 0; j < numTerms; j++) {
                        fabric.metrics.Metric m = term(j);
                        double scaledX = m.value(weakStats);
                        double r = adaptiveRates[j];
                        double b = scaledX - adaptiveSlacks[j];
                        fabric.worker.metrics.treaties.statements.ThresholdStatement
                          newStmt =
                          fabric.worker.metrics.treaties.statements.ThresholdStatement.
                          create(r, b, currentTime);
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
                                      hedgedEstimate(m, newStmt.rate(),
                                                     newStmt.base(),
                                                     currentTime, weakStats));
                            adaptiveRealTimeout =
                              java.lang.Math.
                                min(
                                  adaptiveRealTimeout,
                                  fabric.worker.metrics.treaties.statements.ThresholdStatement.
                                      hedgedExpiry(m, newStmt.rate(),
                                                   newStmt.base(), currentTime,
                                                   weakStats));
                        }
                    }
                    if (adaptiveTimeout > staticTimeout) {
                        if (adaptiveTimeout != java.lang.Long.MAX_VALUE)
                            fabric.common.Logging.METRICS_LOGGER.
                              log(
                                java.util.logging.Level.FINE,
                                "Using adaptive strategy for {0} with adaptive {1} vs static {2} and expected adaptive {3} vs expected static {4}",
                                new java.lang.Object[] { (java.lang.Object)
                                                           fabric.lang.WrappedJavaInlineable.
                                                           $unwrap(
                                                             (fabric.metrics.SumMetric)
                                                               this.$getProxy(
                                                                      )),
                                  java.lang.Long.
                                    valueOf(adaptiveTimeout),
                                  java.lang.Long.
                                    valueOf(staticTimeout),
                                  java.lang.Long.
                                    valueOf(adaptiveRealTimeout),
                                  java.lang.Long.
                                    valueOf(staticRealTimeout) });
                        finalWitnesses = adaptiveWitnesses;
                    }
                    else {
                        fabric.common.Logging.METRICS_LOGGER.
                          log(
                            java.util.logging.Level.FINE,
                            "Using nonadaptive strategy for {0} with adaptive {1} vs static {2} and expected adaptive {3} vs expected static {4}",
                            new java.lang.Object[] { (java.lang.Object)
                                                       fabric.lang.WrappedJavaInlineable.
                                                       $unwrap(
                                                         (fabric.metrics.SumMetric)
                                                           this.$getProxy()),
                              java.lang.Long.
                                valueOf(adaptiveTimeout),
                              java.lang.Long.
                                valueOf(staticTimeout),
                              java.lang.Long.
                                valueOf(adaptiveRealTimeout),
                              java.lang.Long.
                                valueOf(staticRealTimeout) });
                    }
                }
                return fabric.worker.metrics.treaties.enforcement.WitnessPolicy.
                  create(finalWitnesses);
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
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.SumMetric._Static {
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
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.SumMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -31, -110, -80, 60,
    -39, -103, 59, 127, -5, 47, -109, 22, 19, 5, 49, -84, 102, 29, 114, -58, 77,
    74, -94, 77, -110, -53, -36, -78, 43, -87, -40, -62 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748481000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
