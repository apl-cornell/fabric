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
                    fabric.metrics.DerivedMetric val$var407 = val;
                    fabric.worker.transaction.TransactionManager $tm414 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled417 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff415 = 1;
                    boolean $doBackoff416 = true;
                    boolean $retry410 = true;
                    boolean $keepReads411 = false;
                    $label408: for (boolean $commit409 = false; !$commit409; ) {
                        if ($backoffEnabled417) {
                            if ($doBackoff416) {
                                if ($backoff415 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff415));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e412) {
                                            
                                        }
                                    }
                                }
                                if ($backoff415 < 5000) $backoff415 *= 2;
                            }
                            $doBackoff416 = $backoff415 <= 32 || !$doBackoff416;
                        }
                        $commit409 = true;
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
                            catch (final fabric.worker.RetryException $e412) {
                                throw $e412;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e412) {
                                throw $e412;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e412) {
                                throw $e412;
                            }
                            catch (final Throwable $e412) {
                                $tm414.getCurrentLog().checkRetrySignal();
                                throw $e412;
                            }
                        }
                        catch (final fabric.worker.RetryException $e412) {
                            $commit409 = false;
                            continue $label408;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e412) {
                            $commit409 = false;
                            $retry410 = false;
                            $keepReads411 = $e412.keepReads;
                            if ($tm414.checkForStaleObjects()) {
                                $retry410 = true;
                                $keepReads411 = false;
                                continue $label408;
                            }
                            fabric.common.TransactionID $currentTid413 =
                              $tm414.getCurrentTid();
                            if ($e412.tid == null ||
                                  !$e412.tid.isDescendantOf($currentTid413)) {
                                throw $e412;
                            }
                            throw new fabric.worker.UserAbortException($e412);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e412) {
                            $commit409 = false;
                            fabric.common.TransactionID $currentTid413 =
                              $tm414.getCurrentTid();
                            if ($e412.tid.isDescendantOf($currentTid413))
                                continue $label408;
                            if ($currentTid413.parent != null) {
                                $retry410 = false;
                                throw $e412;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e412) {
                            $commit409 = false;
                            if ($tm414.checkForStaleObjects())
                                continue $label408;
                            $retry410 = false;
                            throw new fabric.worker.AbortException($e412);
                        }
                        finally {
                            if ($commit409) {
                                fabric.common.TransactionID $currentTid413 =
                                  $tm414.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e412) {
                                    $commit409 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e412) {
                                    $commit409 = false;
                                    $retry410 = false;
                                    $keepReads411 = $e412.keepReads;
                                    if ($tm414.checkForStaleObjects()) {
                                        $retry410 = true;
                                        $keepReads411 = false;
                                        continue $label408;
                                    }
                                    if ($e412.tid ==
                                          null ||
                                          !$e412.tid.isDescendantOf(
                                                       $currentTid413))
                                        throw $e412;
                                    throw new fabric.worker.UserAbortException(
                                            $e412);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e412) {
                                    $commit409 = false;
                                    $currentTid413 = $tm414.getCurrentTid();
                                    if ($currentTid413 != null) {
                                        if ($e412.tid.equals($currentTid413) ||
                                              !$e412.tid.isDescendantOf(
                                                           $currentTid413)) {
                                            throw $e412;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads411) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit409) {
                                { val = val$var407; }
                                if ($retry410) { continue $label408; }
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
                    fabric.metrics.DerivedMetric val$var418 = val;
                    fabric.metrics.Metric[] newTerms$var419 = newTerms;
                    int termIdx$var420 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm427 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled430 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff428 = 1;
                    boolean $doBackoff429 = true;
                    boolean $retry423 = true;
                    boolean $keepReads424 = false;
                    $label421: for (boolean $commit422 = false; !$commit422; ) {
                        if ($backoffEnabled430) {
                            if ($doBackoff429) {
                                if ($backoff428 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff428));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e425) {
                                            
                                        }
                                    }
                                }
                                if ($backoff428 < 5000) $backoff428 *= 2;
                            }
                            $doBackoff429 = $backoff428 <= 32 || !$doBackoff429;
                        }
                        $commit422 = true;
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
                            catch (final fabric.worker.RetryException $e425) {
                                throw $e425;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e425) {
                                throw $e425;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e425) {
                                throw $e425;
                            }
                            catch (final Throwable $e425) {
                                $tm427.getCurrentLog().checkRetrySignal();
                                throw $e425;
                            }
                        }
                        catch (final fabric.worker.RetryException $e425) {
                            $commit422 = false;
                            continue $label421;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e425) {
                            $commit422 = false;
                            $retry423 = false;
                            $keepReads424 = $e425.keepReads;
                            if ($tm427.checkForStaleObjects()) {
                                $retry423 = true;
                                $keepReads424 = false;
                                continue $label421;
                            }
                            fabric.common.TransactionID $currentTid426 =
                              $tm427.getCurrentTid();
                            if ($e425.tid == null ||
                                  !$e425.tid.isDescendantOf($currentTid426)) {
                                throw $e425;
                            }
                            throw new fabric.worker.UserAbortException($e425);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e425) {
                            $commit422 = false;
                            fabric.common.TransactionID $currentTid426 =
                              $tm427.getCurrentTid();
                            if ($e425.tid.isDescendantOf($currentTid426))
                                continue $label421;
                            if ($currentTid426.parent != null) {
                                $retry423 = false;
                                throw $e425;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e425) {
                            $commit422 = false;
                            if ($tm427.checkForStaleObjects())
                                continue $label421;
                            $retry423 = false;
                            throw new fabric.worker.AbortException($e425);
                        }
                        finally {
                            if ($commit422) {
                                fabric.common.TransactionID $currentTid426 =
                                  $tm427.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e425) {
                                    $commit422 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e425) {
                                    $commit422 = false;
                                    $retry423 = false;
                                    $keepReads424 = $e425.keepReads;
                                    if ($tm427.checkForStaleObjects()) {
                                        $retry423 = true;
                                        $keepReads424 = false;
                                        continue $label421;
                                    }
                                    if ($e425.tid ==
                                          null ||
                                          !$e425.tid.isDescendantOf(
                                                       $currentTid426))
                                        throw $e425;
                                    throw new fabric.worker.UserAbortException(
                                            $e425);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e425) {
                                    $commit422 = false;
                                    $currentTid426 = $tm427.getCurrentTid();
                                    if ($currentTid426 != null) {
                                        if ($e425.tid.equals($currentTid426) ||
                                              !$e425.tid.isDescendantOf(
                                                           $currentTid426)) {
                                            throw $e425;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads424) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit422) {
                                {
                                    val = val$var418;
                                    newTerms = newTerms$var419;
                                    termIdx = termIdx$var420;
                                }
                                if ($retry423) { continue $label421; }
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
    
    public static final byte[] $classHash = new byte[] { 102, -29, -91, 88, 81,
    108, 7, -42, -42, 19, 105, -4, -22, 125, -52, -116, 48, 66, 52, -80, 69, 69,
    -43, -30, -77, 23, -89, -119, 71, 13, 84, 37 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549232462000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0Za2wUx3nuOL/AxsbmaYwx5iDldVcefRCTEnwxcHCAawNqTYO73puzF+/tLrtzcE7qKqmEIFGF0pSQoDb8aIhCqQttoyhRGyTa9BEUlKY0oUnVAkqFkpSilKKm+ZEm/b6ZudfeebHVIHa+vZn5vvne3zfrkRukzLFJa0Lp0/QQG7KoE1qv9EVjnYrt0HhEVxxnO8z2qpMC0aPvPhNv9hN/jFSrimEamqrovYbDyOTYHmWfEjYoC+/oirbtIlUqIm5UnAFG/Lva0zZpsUx9qF83mTykiP5jS8JHHt9d97MJpLaH1GpGN1OYpkZMg9E06yHVSZrso7azLh6n8R4yxaA03k1tTdG1+2CjafSQekfrNxSWsqnTRR1T34cb652URW1+ZmYS2TeBbTulMtMG9usE+ymm6eGY5rC2GClPaFSPO3vJN0kgRsoSutIPG6fHMlKEOcXwepyH7RM1YNNOKCrNoAQGNSPOyFw3Rlbi4GbYAKgVScoGzOxRAUOBCVIvWNIVoz/czWzN6IetZWYKTmGkcVSisKnSUtRBpZ/2MjLTva9TLMGuKq4WRGFkmnsbpwQ2a3TZLM9aN7auOXy/sdHwEx/wHKeqjvxXAlKzC6mLJqhNDZUKxOrFsaPK9LOH/ITA5mmuzWLP89+4effS5nMviz2zS+zZ1reHqqxXPdE3+Q9NkUWrJyAblZbpaOgKBZJzq3bKlba0Bd4+PUsRF0OZxXNdv/3qA6fodT+ZGCXlqqmnkuBVU1QzaWk6tTdQg9oKo/EoqaJGPMLXo6QC3mOaQcXstkTCoSxKAjqfKjf5b1BRAkigiirgXTMSZubdUtgAf09bhJAKeIgP/r9NSPRf8N5KSOBuRmLhATNJw316iu4H9w7DQxVbHQhD3Nqaukw1raGwY6thO2UwDXaK+TC4EgAn3J1KbuGvIeDD+pTppZH/uv0+H6h2rmrGaZ/igJ2kz7R36hAWG009Tu1eVT98Nkoazh7jflOFvu6Av3LN+MDWTe4skY97JNXecfN07yvC5xBXKg7sLfgLSf5CWf6ApWqMpBDkphDkphFfOhQ5Hv0Rd5hyh0dWlko1ULnT0hWWMO1kmvh8XKSpHJ97Cth5EPIHpIjqRd33bvr6odYJ4KLW/gBaDbYG3QGTSzNReFMgCnrV2oPvfnDm6LCZCx1GgkURXYyJEdnq1o9tqjQOGS9HfnGL8lzv2eGgH7NJFSQ6poArQtZodp9REJltmSyH2iiLkUmoA0XHpUxqmsgGbHN/bobbfTIO9cIFUFkuBnmCvKvbevLNV99byUtHJpfW5iXdbsra8uIXidXySJ2S0/12m1LY99cnOr/72I2Du7jiYcf8UgcGcYxA3CoQsKZ94OW9b125fOJ1f85YjJRbqT5dU9NclimfwD8fPB/jg0GIEwghFUdkAmjJZgALT16Y4w1ygQ75CFh3gjuMpBnXEprSp1P0lI9qFyx/7h+H64S5dZgRyrPJ0tsTyM3PaicPvLL7P82cjE/FWpTTX26bSHANOcrrbFsZQj7SD16cc+x3ypPg+ZCeHO0+yjMO4fog3IAruC6W8XG5a20VDq1CW01ynv+Yz8eFOCwSusXXxVKvRP4rl7lsrYSrcbXBwnFqIU2bzBmt7PCSeeJbR47Htz29XBSH+sJU3mGkkj++9N8LoSeuni+RJqqYaS3T6T6q552JbjuvqP/ZwqtyLqyuXp+zOjJ4rV8cO9fFonv3D7eMnN+wUH3UTyZkY7yoFShEastnFoLNptDJGCg2zkzkRmjJKnUSKqsDnsWElPVKuCBPqTIiuYVw+EIWlet5okQJStjstkfOC3zZ9DY7X0ubwMW4c4mavRtC9bWh948K/bg7h7yN/xy5cv1izZzTPD8FsFRw+dwtV3FHVdAocfGqszJ9DmVqg6cLVFMtYTkjnf9nnbsHOkzoGAtq56dOU4TKNGjEXFVMbMHFxhLWcDda61F9ORfsCY98vzHypeui0mbrANKZV6LS7lTyStSKU8l/+1vLf+MnFT2kjjfLisF2KiAypOAeMI4TkZMxUlOwXti6ij6tLRsDTe4YyDvWXYHyoyHACuKAF53NaR/hDrujdB7y8zzEgKhmKKJJWQJpX6dGPxsoob9OW0tC+dknG1V66MjDn4QOHxFpRHTz84sa6nwc0dHzg2r4aZjM5nmdwjHWv3Nm+Bcnhw/6ZaZtY2QCOD++bkpn7e4XMmVcRWR41DGkR9OgWCz42izIctjG6CZczrKeJXoYzQxlr0x9oh1V00Wehb/XCgVzHvJyCJfKo0gMeqwlcYDrYJmK/GYYq8vJIXxFMMUxNnhQ437czsgsETJBGTLBbOMXzOW/tYVZE8oPkiv7QMIr48uaiHJZwjdHz5r5zA55rN2PA4MrNF4z4HrXiemEdfG9PdKLENwLrhs3Uxkzl5DpTqiyv5fwhfHJhCjPS/jTscl0wGPtIA4PuGVqx9nh0biPwOXnpITfGR/3iPKIhA+PjfvDHmuP4PCQm/udntzHCKl8UMI94+MeUTQJ1bFx/7jH2jEcHnVzv7UU95OJrJw7CKlKSrh+FO6LujzIM5ZtMghZGk8XilUjaXVIuGZ0sfKTDqSDZlkB95v2ILVz1zmmMGeLYmUyXOFFjbP5Aw+dnMTheww/+3Cd8EI1qka+CM9usM1uCVd52PN4seCIslLCxbcVHH8+xame8RCAx+QpuDNlBKCQ3jU25GnVfvDPbRJ+ZnwyIModEt6+M8zJ8IKHDD/H4dmcEbaamlPSCDyomuCB60l1UMKG8QUVotRLWD22oPqlx9pLOLzISCUzxce4EuUrb6HIQ0tJuAyeA4TURiW8Y3wSIspCCVvGZKJhTvVVDzFfw+E8lGhsW52MjE2urrSgf8U9jS7xGpDgZniOEjJlWMKOMeYVPyMVFhwA12yGH2jwI7ArvdRLkvdI+PnRxffnOsG6nA7+4qGDyzi8DgYUR/dyVeDcxVJGXALPaUKm3pLwj+MzIqJclPDCmIy4lVO95iHAOzhcZSRg6amSjHPzoNedI2SGJuHKsZoHX3nb81YJqyClFRIGx24VIdT7HkLdxOE9RiZJq4wmGzcKyOSbScjCuwRcML4Gj6NcltCjwQtw9gLcq7LDU5mgaSgsZN3MtLP9eYnq9aGH7B/jcAuSPxsAGgOmHu80dU0dyhy1pnTNZDYFVcGNgxpwjkqT1GChjtx7HpEx5atZoJI3CNnYKWH7+LSKKOskbBtTRvZVeaxNwiEAGXlAcQYiZpxXkq+V4hvqmO/PcOgVCV8aH9+I8isJX7xtiGZsUi9tknexGd36vnoPQWfiUAOpkO5NKbpIyq57QUWfaepUMdLQkmXvP/gZcHaJj/Hyj0Nq5Nf0xLXNS6eN8iF+ZtGf6yTe6eO1lTOO7/iT+HyT+cNPVYxUJlK6nn9lz3svt2ya0Li2q8QF3uLCtUCfWlhdGP/Mg28ona9Z7JsP8ot9+CvILdCYHS5yko0pG//UOHJrxofllduv8m+9oMSWxN+e/sqX9YpLlxq0j/4+fOHbn21f9ZOOjjfefnbGMw9tqNm+4H/vyrjEAh0AAA==";
}
