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
      thresholdPolicy(double rate, double base, long currentTime,
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
                    long $backoff392 = 1;
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
                                if ($backoff392 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff392 =
                                      java.lang.Math.
                                        min(
                                          $backoff392 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff393 = $backoff392 <= 32 || !$doBackoff393;
                        }
                        $commit386 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
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
                            $retry387 = false;
                            if ($tm391.inNestedTxn()) { $keepReads388 = true; }
                            throw $e389;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid390 =
                              $tm391.getCurrentTid();
                            if ($commit386) {
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
                            } else {
                                if (!$tm391.inNestedTxn() &&
                                      $tm391.checkForStaleObjects()) {
                                    $retry387 = true;
                                    $keepReads388 = false;
                                }
                                if ($keepReads388) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e389) {
                                        $currentTid390 = $tm391.getCurrentTid();
                                        if ($currentTid390 != null &&
                                              ($e389.tid.equals($currentTid390) || !$e389.tid.isDescendantOf($currentTid390))) {
                                            throw $e389;
                                        } else {
                                            $retry387 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
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
                    long $backoff405 = 1;
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
                                if ($backoff405 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff405 =
                                      java.lang.Math.
                                        min(
                                          $backoff405 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff406 = $backoff405 <= 32 || !$doBackoff406;
                        }
                        $commit399 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
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
                            $retry400 = false;
                            if ($tm404.inNestedTxn()) { $keepReads401 = true; }
                            throw $e402;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid403 =
                              $tm404.getCurrentTid();
                            if ($commit399) {
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
                            } else {
                                if (!$tm404.inNestedTxn() &&
                                      $tm404.checkForStaleObjects()) {
                                    $retry400 = true;
                                    $keepReads401 = false;
                                }
                                if ($keepReads401) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e402) {
                                        $currentTid403 = $tm404.getCurrentTid();
                                        if ($currentTid403 != null &&
                                              ($e402.tid.equals($currentTid403) || !$e402.tid.isDescendantOf($currentTid403))) {
                                            throw $e402;
                                        } else {
                                            $retry400 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
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
          thresholdPolicy(double rate, double base, long currentTime,
                          fabric.worker.metrics.StatsMap weakStats,
                          final fabric.worker.Store s) {
            fabric.worker.metrics.treaties.statements.ThresholdStatement
              topStmt =
              fabric.worker.metrics.treaties.statements.ThresholdStatement.
              create(rate, base, currentTime);
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
                    }
                }
                fabric.worker.metrics.treaties.enforcement.WitnessPolicy
                  finalPolicy =
                  fabric.worker.metrics.treaties.enforcement.WitnessPolicy.
                  create(staticWitnesses);
                if (config.useDynamic && hasSlackProducer &&
                      (!isSingleStore() || rate > 0)) {
                    double[] adaptiveSlacks =
                      fabric.worker.metrics.SumStrategy.getSplitEqualVelocity(
                                                          velocities, noises,
                                                          adaptiveRates,
                                                          totalValue - baseNow);
                    com.google.common.collect.Multimap adaptiveWitnesses =
                      com.google.common.collect.HashMultimap.create();
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
                        }
                    }
                    fabric.worker.metrics.treaties.enforcement.WitnessPolicy
                      adaptivePolicy =
                      fabric.worker.metrics.treaties.enforcement.WitnessPolicy.
                      create(adaptiveWitnesses);
                    long adaptiveEstimate =
                      adaptivePolicy.estimatedTrueExpiry(
                                       (fabric.metrics.SumMetric)
                                         this.$getProxy(), topStmt, currentTime,
                                       weakStats);
                    long adaptiveActual =
                      adaptivePolicy.estimatedHedgedExpiry(
                                       (fabric.metrics.SumMetric)
                                         this.$getProxy(), topStmt, currentTime,
                                       weakStats);
                    long staticEstimate =
                      finalPolicy.estimatedTrueExpiry((fabric.metrics.SumMetric)
                                                        this.$getProxy(),
                                                      topStmt, currentTime,
                                                      weakStats);
                    if (adaptiveEstimate > staticEstimate && adaptiveActual >
                          lastUpdate(weakStats) + 3 *
                          updateInterval(weakStats)) {
                        finalPolicy = adaptivePolicy;
                    }
                }
                return finalPolicy;
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
    
    public static final byte[] $classHash = new byte[] { -79, 30, -7, -93, 82,
    -115, -51, 102, -78, -63, 19, -60, -96, 85, 4, -39, 117, 122, -40, 3, 21,
    -121, -20, -71, -118, 104, -89, 18, 111, 95, 108, 126 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556553199000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
