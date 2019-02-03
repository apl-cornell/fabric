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
                    fabric.metrics.DerivedMetric val$var428 = val;
                    fabric.worker.transaction.TransactionManager $tm435 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled438 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff436 = 1;
                    boolean $doBackoff437 = true;
                    boolean $retry431 = true;
                    boolean $keepReads432 = false;
                    $label429: for (boolean $commit430 = false; !$commit430; ) {
                        if ($backoffEnabled438) {
                            if ($doBackoff437) {
                                if ($backoff436 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff436));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e433) {
                                            
                                        }
                                    }
                                }
                                if ($backoff436 < 5000) $backoff436 *= 2;
                            }
                            $doBackoff437 = $backoff436 <= 32 || !$doBackoff437;
                        }
                        $commit430 = true;
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
                            catch (final fabric.worker.RetryException $e433) {
                                throw $e433;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e433) {
                                throw $e433;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e433) {
                                throw $e433;
                            }
                            catch (final Throwable $e433) {
                                $tm435.getCurrentLog().checkRetrySignal();
                                throw $e433;
                            }
                        }
                        catch (final fabric.worker.RetryException $e433) {
                            $commit430 = false;
                            continue $label429;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e433) {
                            $commit430 = false;
                            $retry431 = false;
                            $keepReads432 = $e433.keepReads;
                            if ($tm435.checkForStaleObjects()) {
                                $retry431 = true;
                                $keepReads432 = false;
                                continue $label429;
                            }
                            fabric.common.TransactionID $currentTid434 =
                              $tm435.getCurrentTid();
                            if ($e433.tid == null ||
                                  !$e433.tid.isDescendantOf($currentTid434)) {
                                throw $e433;
                            }
                            throw new fabric.worker.UserAbortException($e433);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e433) {
                            $commit430 = false;
                            fabric.common.TransactionID $currentTid434 =
                              $tm435.getCurrentTid();
                            if ($e433.tid.isDescendantOf($currentTid434))
                                continue $label429;
                            if ($currentTid434.parent != null) {
                                $retry431 = false;
                                throw $e433;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e433) {
                            $commit430 = false;
                            if ($tm435.checkForStaleObjects())
                                continue $label429;
                            $retry431 = false;
                            throw new fabric.worker.AbortException($e433);
                        }
                        finally {
                            if ($commit430) {
                                fabric.common.TransactionID $currentTid434 =
                                  $tm435.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e433) {
                                    $commit430 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e433) {
                                    $commit430 = false;
                                    $retry431 = false;
                                    $keepReads432 = $e433.keepReads;
                                    if ($tm435.checkForStaleObjects()) {
                                        $retry431 = true;
                                        $keepReads432 = false;
                                        continue $label429;
                                    }
                                    if ($e433.tid ==
                                          null ||
                                          !$e433.tid.isDescendantOf(
                                                       $currentTid434))
                                        throw $e433;
                                    throw new fabric.worker.UserAbortException(
                                            $e433);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e433) {
                                    $commit430 = false;
                                    $currentTid434 = $tm435.getCurrentTid();
                                    if ($currentTid434 != null) {
                                        if ($e433.tid.equals($currentTid434) ||
                                              !$e433.tid.isDescendantOf(
                                                           $currentTid434)) {
                                            throw $e433;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads432) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit430) {
                                { val = val$var428; }
                                if ($retry431) { continue $label429; }
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
                    fabric.metrics.DerivedMetric val$var439 = val;
                    fabric.metrics.Metric[] newTerms$var440 = newTerms;
                    int termIdx$var441 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm448 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled451 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff449 = 1;
                    boolean $doBackoff450 = true;
                    boolean $retry444 = true;
                    boolean $keepReads445 = false;
                    $label442: for (boolean $commit443 = false; !$commit443; ) {
                        if ($backoffEnabled451) {
                            if ($doBackoff450) {
                                if ($backoff449 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff449));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e446) {
                                            
                                        }
                                    }
                                }
                                if ($backoff449 < 5000) $backoff449 *= 2;
                            }
                            $doBackoff450 = $backoff449 <= 32 || !$doBackoff450;
                        }
                        $commit443 = true;
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
                            catch (final fabric.worker.RetryException $e446) {
                                throw $e446;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e446) {
                                throw $e446;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e446) {
                                throw $e446;
                            }
                            catch (final Throwable $e446) {
                                $tm448.getCurrentLog().checkRetrySignal();
                                throw $e446;
                            }
                        }
                        catch (final fabric.worker.RetryException $e446) {
                            $commit443 = false;
                            continue $label442;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e446) {
                            $commit443 = false;
                            $retry444 = false;
                            $keepReads445 = $e446.keepReads;
                            if ($tm448.checkForStaleObjects()) {
                                $retry444 = true;
                                $keepReads445 = false;
                                continue $label442;
                            }
                            fabric.common.TransactionID $currentTid447 =
                              $tm448.getCurrentTid();
                            if ($e446.tid == null ||
                                  !$e446.tid.isDescendantOf($currentTid447)) {
                                throw $e446;
                            }
                            throw new fabric.worker.UserAbortException($e446);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e446) {
                            $commit443 = false;
                            fabric.common.TransactionID $currentTid447 =
                              $tm448.getCurrentTid();
                            if ($e446.tid.isDescendantOf($currentTid447))
                                continue $label442;
                            if ($currentTid447.parent != null) {
                                $retry444 = false;
                                throw $e446;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e446) {
                            $commit443 = false;
                            if ($tm448.checkForStaleObjects())
                                continue $label442;
                            $retry444 = false;
                            throw new fabric.worker.AbortException($e446);
                        }
                        finally {
                            if ($commit443) {
                                fabric.common.TransactionID $currentTid447 =
                                  $tm448.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e446) {
                                    $commit443 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e446) {
                                    $commit443 = false;
                                    $retry444 = false;
                                    $keepReads445 = $e446.keepReads;
                                    if ($tm448.checkForStaleObjects()) {
                                        $retry444 = true;
                                        $keepReads445 = false;
                                        continue $label442;
                                    }
                                    if ($e446.tid ==
                                          null ||
                                          !$e446.tid.isDescendantOf(
                                                       $currentTid447))
                                        throw $e446;
                                    throw new fabric.worker.UserAbortException(
                                            $e446);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e446) {
                                    $commit443 = false;
                                    $currentTid447 = $tm448.getCurrentTid();
                                    if ($currentTid447 != null) {
                                        if ($e446.tid.equals($currentTid447) ||
                                              !$e446.tid.isDescendantOf(
                                                           $currentTid447)) {
                                            throw $e446;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads445) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit443) {
                                {
                                    val = val$var439;
                                    newTerms = newTerms$var440;
                                    termIdx = termIdx$var441;
                                }
                                if ($retry444) { continue $label442; }
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
                    if (adaptiveTimeout >
                          staticTimeout &&
                          adaptiveRealTimeout >=
                          java.lang.System.currentTimeMillis() +
                          fabric.worker.transaction.TransactionManager.
                          getInstance().getCurrentTreatyTimeout()) {
                        if (adaptiveTimeout != java.lang.Long.MAX_VALUE)
                            fabric.common.Logging.METRICS_LOGGER.
                              log(
                                java.util.logging.Level.FINE,
                                "Using adaptive strategy for {0} with adaptive {1} vs static {2} and expected {3}",
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
                                    valueOf(adaptiveRealTimeout) });
                        finalWitnesses = adaptiveWitnesses;
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
    
    public static final byte[] $classHash = new byte[] { -123, 77, -26, 46, 26,
    -93, 127, -29, -29, -118, 94, 51, 7, -109, -24, 47, -28, 92, 26, -54, 115,
    34, 41, -6, 124, -62, 64, -79, 15, 78, -21, 60 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549232462000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
