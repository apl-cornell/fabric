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
                    fabric.metrics.DerivedMetric val$var384 = val;
                    fabric.worker.transaction.TransactionManager $tm391 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled394 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff392 = 1;
                    boolean $doBackoff393 = true;
                    boolean $retry387 = true;
                    boolean $keepReads388 = false;
                    $label385: for (boolean $commit386 = false; !$commit386; ) {
                        if ($backoffEnabled394) {
                            if ($doBackoff393) {
                                if ($backoff392 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff392));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e389) {
                                            
                                        }
                                    }
                                }
                                if ($backoff392 < 5000) $backoff392 *= 2;
                            }
                            $doBackoff393 = $backoff392 <= 32 || !$doBackoff393;
                        }
                        $commit386 = true;
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
                            catch (final fabric.worker.RetryException $e389) {
                                throw $e389;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e389) {
                                throw $e389;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e389) {
                                throw $e389;
                            }
                            catch (final Throwable $e389) {
                                $tm391.getCurrentLog().checkRetrySignal();
                                throw $e389;
                            }
                        }
                        catch (final fabric.worker.RetryException $e389) {
                            $commit386 = false;
                            continue $label385;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e389) {
                            $commit386 = false;
                            $retry387 = false;
                            $keepReads388 = $e389.keepReads;
                            if ($tm391.checkForStaleObjects()) {
                                $retry387 = true;
                                $keepReads388 = false;
                                continue $label385;
                            }
                            fabric.common.TransactionID $currentTid390 =
                              $tm391.getCurrentTid();
                            if ($e389.tid == null ||
                                  !$e389.tid.isDescendantOf($currentTid390)) {
                                throw $e389;
                            }
                            throw new fabric.worker.UserAbortException($e389);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e389) {
                            $commit386 = false;
                            fabric.common.TransactionID $currentTid390 =
                              $tm391.getCurrentTid();
                            if ($e389.tid.isDescendantOf($currentTid390))
                                continue $label385;
                            if ($currentTid390.parent != null) {
                                $retry387 = false;
                                throw $e389;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e389) {
                            $commit386 = false;
                            if ($tm391.checkForStaleObjects())
                                continue $label385;
                            $retry387 = false;
                            throw new fabric.worker.AbortException($e389);
                        }
                        finally {
                            if ($commit386) {
                                fabric.common.TransactionID $currentTid390 =
                                  $tm391.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e389) {
                                    $commit386 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e389) {
                                    $commit386 = false;
                                    $retry387 = false;
                                    $keepReads388 = $e389.keepReads;
                                    if ($tm391.checkForStaleObjects()) {
                                        $retry387 = true;
                                        $keepReads388 = false;
                                        continue $label385;
                                    }
                                    if ($e389.tid ==
                                          null ||
                                          !$e389.tid.isDescendantOf(
                                                       $currentTid390))
                                        throw $e389;
                                    throw new fabric.worker.UserAbortException(
                                            $e389);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e389) {
                                    $commit386 = false;
                                    $currentTid390 = $tm391.getCurrentTid();
                                    if ($currentTid390 != null) {
                                        if ($e389.tid.equals($currentTid390) ||
                                              !$e389.tid.isDescendantOf(
                                                           $currentTid390)) {
                                            throw $e389;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads388) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit386) {
                                { val = val$var384; }
                                if ($retry387) { continue $label385; }
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
                    fabric.metrics.DerivedMetric val$var395 = val;
                    fabric.metrics.Metric[] newTerms$var396 = newTerms;
                    int termIdx$var397 = termIdx;
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
                                {
                                    val = val$var395;
                                    newTerms = newTerms$var396;
                                    termIdx = termIdx$var397;
                                }
                                if ($retry400) { continue $label398; }
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
    
    public static final byte[] $classHash = new byte[] { 40, 120, -59, 20, -100,
    -79, -95, 99, 16, -75, -116, -51, -80, -83, -52, -27, -107, -75, -112, 119,
    -119, -15, 26, 100, 63, 76, -76, 45, 95, -58, 28, -45 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1550000445000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
