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
    
    public double computePresetNTerm();
    
    public double computeValue(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeVelocity(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoise(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoiseTerm(fabric.worker.metrics.StatsMap weakStats);
    
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
        
        public double computePresetNTerm() {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).getPresetNTerm();
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
        
        public double computeNoiseTerm(
          fabric.worker.metrics.StatsMap weakStats) {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).noiseTerm(weakStats);
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
                    fabric.metrics.DerivedMetric val$var375 = val;
                    fabric.worker.transaction.TransactionManager $tm382 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled385 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff383 = 1;
                    boolean $doBackoff384 = true;
                    boolean $retry378 = true;
                    boolean $keepReads379 = false;
                    $label376: for (boolean $commit377 = false; !$commit377; ) {
                        if ($backoffEnabled385) {
                            if ($doBackoff384) {
                                if ($backoff383 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff383));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e380) {
                                            
                                        }
                                    }
                                }
                                if ($backoff383 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff383 =
                                      java.lang.Math.
                                        min(
                                          $backoff383 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff384 = $backoff383 <= 32 || !$doBackoff384;
                        }
                        $commit377 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e380) {
                            $commit377 = false;
                            continue $label376;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e380) {
                            $commit377 = false;
                            $retry378 = false;
                            $keepReads379 = $e380.keepReads;
                            fabric.common.TransactionID $currentTid381 =
                              $tm382.getCurrentTid();
                            if ($e380.tid == null ||
                                  !$e380.tid.isDescendantOf($currentTid381)) {
                                throw $e380;
                            }
                            throw new fabric.worker.UserAbortException($e380);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e380) {
                            $commit377 = false;
                            fabric.common.TransactionID $currentTid381 =
                              $tm382.getCurrentTid();
                            if ($e380.tid.isDescendantOf($currentTid381))
                                continue $label376;
                            if ($currentTid381.parent != null) {
                                $retry378 = false;
                                throw $e380;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e380) {
                            $commit377 = false;
                            $retry378 = false;
                            if ($tm382.inNestedTxn()) { $keepReads379 = true; }
                            throw $e380;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid381 =
                              $tm382.getCurrentTid();
                            if ($commit377) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e380) {
                                    $commit377 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e380) {
                                    $commit377 = false;
                                    $retry378 = false;
                                    $keepReads379 = $e380.keepReads;
                                    if ($e380.tid ==
                                          null ||
                                          !$e380.tid.isDescendantOf(
                                                       $currentTid381))
                                        throw $e380;
                                    throw new fabric.worker.UserAbortException(
                                            $e380);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e380) {
                                    $commit377 = false;
                                    $currentTid381 = $tm382.getCurrentTid();
                                    if ($currentTid381 != null) {
                                        if ($e380.tid.equals($currentTid381) ||
                                              !$e380.tid.isDescendantOf(
                                                           $currentTid381)) {
                                            throw $e380;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm382.inNestedTxn() &&
                                      $tm382.checkForStaleObjects()) {
                                    $retry378 = true;
                                    $keepReads379 = false;
                                }
                                if ($keepReads379) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e380) {
                                        $currentTid381 = $tm382.getCurrentTid();
                                        if ($currentTid381 != null &&
                                              ($e380.tid.equals($currentTid381) || !$e380.tid.isDescendantOf($currentTid381))) {
                                            throw $e380;
                                        } else {
                                            $retry378 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit377) {
                                { val = val$var375; }
                                if ($retry378) { continue $label376; }
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
                    fabric.metrics.DerivedMetric val$var386 = val;
                    fabric.metrics.Metric[] newTerms$var387 = newTerms;
                    int termIdx$var388 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm395 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled398 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff396 = 1;
                    boolean $doBackoff397 = true;
                    boolean $retry391 = true;
                    boolean $keepReads392 = false;
                    $label389: for (boolean $commit390 = false; !$commit390; ) {
                        if ($backoffEnabled398) {
                            if ($doBackoff397) {
                                if ($backoff396 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff396));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e393) {
                                            
                                        }
                                    }
                                }
                                if ($backoff396 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff396 =
                                      java.lang.Math.
                                        min(
                                          $backoff396 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff397 = $backoff396 <= 32 || !$doBackoff397;
                        }
                        $commit390 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e393) {
                            $commit390 = false;
                            continue $label389;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e393) {
                            $commit390 = false;
                            $retry391 = false;
                            $keepReads392 = $e393.keepReads;
                            fabric.common.TransactionID $currentTid394 =
                              $tm395.getCurrentTid();
                            if ($e393.tid == null ||
                                  !$e393.tid.isDescendantOf($currentTid394)) {
                                throw $e393;
                            }
                            throw new fabric.worker.UserAbortException($e393);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e393) {
                            $commit390 = false;
                            fabric.common.TransactionID $currentTid394 =
                              $tm395.getCurrentTid();
                            if ($e393.tid.isDescendantOf($currentTid394))
                                continue $label389;
                            if ($currentTid394.parent != null) {
                                $retry391 = false;
                                throw $e393;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e393) {
                            $commit390 = false;
                            $retry391 = false;
                            if ($tm395.inNestedTxn()) { $keepReads392 = true; }
                            throw $e393;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid394 =
                              $tm395.getCurrentTid();
                            if ($commit390) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e393) {
                                    $commit390 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e393) {
                                    $commit390 = false;
                                    $retry391 = false;
                                    $keepReads392 = $e393.keepReads;
                                    if ($e393.tid ==
                                          null ||
                                          !$e393.tid.isDescendantOf(
                                                       $currentTid394))
                                        throw $e393;
                                    throw new fabric.worker.UserAbortException(
                                            $e393);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e393) {
                                    $commit390 = false;
                                    $currentTid394 = $tm395.getCurrentTid();
                                    if ($currentTid394 != null) {
                                        if ($e393.tid.equals($currentTid394) ||
                                              !$e393.tid.isDescendantOf(
                                                           $currentTid394)) {
                                            throw $e393;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm395.inNestedTxn() &&
                                      $tm395.checkForStaleObjects()) {
                                    $retry391 = true;
                                    $keepReads392 = false;
                                }
                                if ($keepReads392) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e393) {
                                        $currentTid394 = $tm395.getCurrentTid();
                                        if ($currentTid394 != null &&
                                              ($e393.tid.equals($currentTid394) || !$e393.tid.isDescendantOf($currentTid394))) {
                                            throw $e393;
                                        } else {
                                            $retry391 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit390) {
                                {
                                    val = val$var386;
                                    newTerms = newTerms$var387;
                                    termIdx = termIdx$var388;
                                }
                                if ($retry391) { continue $label389; }
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
    
    public static final byte[] $classHash = new byte[] { -19, 82, -108, -106,
    -120, -117, 121, -68, -108, 32, 108, 16, 21, -104, -52, -13, 29, -116, 56,
    108, -84, 12, -39, -30, -16, 57, -112, 21, 15, -13, 9, 123 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556814969000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0ZC2wUx3Xu/P+AjY0JGGOMcUwxcBeSRsUxLcRXPgcHWDYg1TS46705e/He7rI7B2eoqyRSBEkrRFOHj9qgqqVKQlxQW9FEbZCo2rT5oLSlv0RVA0pJm5TQkqJAK5LQ92bnfnt3i08NYuetZ+a9ef/3Zm/iCimxTNIakQYV1cdGDWr5VkuDwVCPZFo0HFAly9oMswNyVXHw0DtPhZu9xBsi1bKk6ZoiS+qAZjEyNbRD2iX5Ncr8W3qDXdtIhYyIayVrmBHvtu64SVoMXR0dUnUmDsmi/8Qi//jh7bU/LCI1/aRG0fqYxBQ5oGuMxlk/qY7S6CA1rfvDYRruJ9M0SsN91FQkVdkDG3Wtn9RZypAmsZhJrV5q6eou3FhnxQxq8jMTk8i+DmybMZnpJrBfa7MfY4rqDykW6wqR0ohC1bC1k3yFFIdISUSVhmDjjFBCCj+n6F+N87C9UgE2zYgk0wRK8YiihRmZ68RISty2HjYAalmUsmE9eVSxJsEEqbNZUiVtyN/HTEUbgq0legxOYaQxL1HYVG5I8og0RAcYmenc12Mvwa4KrhZEYaTBuY1TAps1OmyWZq0rG5cf2Kut1bzEAzyHqawi/+WA1OxA6qURalJNpjZidUfokDTjzH4vIbC5wbHZ3vPcl99fubj57Ev2ntk59mwa3EFlNiAfH5z626bAws4iZKPc0C0FXSFDcm7VHrHSFTfA22ckKeKiL7F4tveXX3jwBL3sJZVBUirraiwKXjVN1qOGolJzDdWoKTEaDpIKqoUDfD1IyuA9pGjUnt0UiViUBUmxyqdKdf43qCgCJFBFZfCuaBE98W5IbJi/xw1CSBk8xAP/3yMkuAjeWwkpXslIyD+sR6l/UI3R3eDefnioZMrDfohbU5GXyLox6rdM2W/GNKbATnveD64EwPL3xaIb+KsP+DA+YXpx5L92t8cDqp0r62E6KFlgJ+Ez3T0qhMVaXQ1Tc0BWD5wJkvozR7nfVKCvW+CvXDMesHWTM0uk447Hule9f3LgVdvnEFcoDuxt8+cT/PmS/AFL1RhJPshNPshNE564L3As+Cx3mFKLR1aSSjVQuc9QJRbRzWiceDxcpOkcn3sK2HkE8gekiOqFfQ+s+9L+1iJwUWN3MVoNtrY5AyaVZoLwJkEUDMg1+965furQmJ4KHUbasiI6GxMjstWpH1OXaRgyXop8R4t0euDMWJsXs0kFJDomgStC1mh2npERmV2JLIfaKAmRKtSBpOJSIjVVsmFT352a4XafikOd7QKoLAeDPEF+ts948vXX3r2Hl45ELq1JS7p9lHWlxS8Sq+GROi2l+80mpbDvL0d6vvHElX3buOJhx/xcB7bhGIC4lSBgdfORl3a+ceHN47/3pozFSKkRG1QVOc5lmXYL/nng+RgfDEKcQAipOCASQEsyAxh4cnuKN8gFKuQjYN1q26JF9bASUaRBlaKnfFhz59LT7x2otc2twoytPJMsvj2B1PysbvLgq9tvNHMyHhlrUUp/qW12gqtPUb7fNKVR5CP+0Pk5R38lPQmeD+nJUvZQnnEI1wfhBryb62IJH5c61j6NQ6utrSYxz/+Yz8d2HBbausXXDqFXIv6Vily2QsBOXK03cJyeSdMkc/KVHV4yjz88fiy86XtL7eJQl5nKV2mx6Pf/+NE535GLL+dIExVMN5aodBdV086sgyPnZfU/G3hVToXVxctzOgMjbw/Zx851sOjc/cyGiZfXtMuPe0lRMsazWoFMpK50ZiHYTAqdjIZi40wlN0JLUqlVqKxV8HQQUjIg4J1pShURyS2Ew2eSqFzPlQKlTcBmpz1SXuBJprfZ6VpaBy7Gncuu2dshVH8z+q9Dtn6cnUPaxqsTFy6fnzLnJM9PxVgquHzOliu7o8polLh41UmZ7kWZuuDpA9XcJWAHIz3/Z537PHSY0DFm1M5PnKYdKg3QiDmqmL0FFxtzWMPZaK1G9aVcsN8/8a3GwOcu25U2WQeQzrwclXarlFai7j4R/cDbWvqil5T1k1reLEsa2yqByJCC+8E4VkBMhsiUjPXM1tXu07qSMdDkjIG0Y50VKD0aillGHPCisz7uIdxht+TOQ16ehxgQVTTJblIWQdpXqTbEhnPor8dUolB+dolGle4ff+yW78C4nUbsbn5+VkOdjmN39PygKfw0TGbz3E7hGKv/fmrsp0+P7fOKTNvFSBE4P76uiyft7rVlSriKneFRx5AedY1iseBrsyDLYRuj6nA5S3qW3cMoui95ZRq021E5nuVZ+PcKW8Gch7QcwqVyKRIjLmtRHOA6WCIjvwnGalNy2L5iM8Ux1rhQ437czcgsO2TaRMi0JRu/tlT+W5GZNaH8ILmS6wJeKCxrIsqbAr6eP2umMzvqsrYXBwZXaLxmwPWuB9MJ6+V7+4UXIXgAXDesxxJmziHTfVBlfy3g84XJhCjPCfiDycn0iMvaPhwedMrUjbNj+bgPwOXnaQG/Xhj3iHJQwMcmx/0Bl7WDODzq5H6rK/chQsofEnBHYdwjiiKgPDnuD7usHcXhcSf3G/Ny3w7PFkIqtgsYKIx7ROkWcPnkuP+2y9p3cPgm1JBM7jdTM5pLgqlE1H7gvnKjgAvySJDVp0KmNEydQdKh4XimaFMErXYB5+QXLT1tQkJrFjV8t26OUDN1IWUSszZIRiJHZ141OZvPuuiFB+Vxhh+uuF54qc2rkWXwDIF9VwjY4GLTp7IFR5TpAlbdVnD8c4JTfd5FgJ/g8CO49SUEoFCgFDbqalW4n1TfJWBFYTIgSrkNq24VIMPPXGT4OQ4vpIywUVes/EbohGcUmGm1YfWNwgRAlOsCXilAgFdcBDiHw4sMO7qUAPlii2eHJngeBoZuCviPwrIDorwr4F8nlx1+57L2BxxeY6Sc6fY30RxdRNpCVpjlknAJPIcJmdZiw9qPC5MQUT4S8EZ+CdPMNMapXnQR8y0c/gydEt4erISMTY7LQcY1Avc0OsSrR4Lr4YGyWr9ZwKZJJkcvI2UGHCAx6OhLLf4t3pEj6wTJ2QJOyS++N9WQ16Z08E8XHVzF4W9gQPvoAa4KnLuUy4j4pfQsITNeEfBEYUZElGcE/O6kjLiRU73hIsB/cfg3I8WGGsvJODdPEJ7zhMxaKWDlZM2DrzwYL+ewClKqsOHMm5O3ChfK48kvlKcIJ28yUiWskk82bpS9oKsOQj7VbsMFPy7IKBzltICn8gtRwtkr4V6VSmOOzrkYrhz8M+9EIprqM8t0H9PN5P0puzZ7Kl2UUo9DCZQ2Ngw0hnU13KOrijyaOGp57o6AmRR0CDdCqsE5Mo1SjflWpd7TiEwqkc0CXV0gZM2jAu4tTN2IskdAll/d6YI3u6y14AB8lw9L1nBAD/M6+cVcfC+AQy8RsnamDdd8WBjfiHJTwA9uG7sJm9QJm6RdPF2sv8BF0CU4tEKOpDtjkmrl8r6yQV1XqaTFoeFM3k/xM+3sHD+WiB/v5MAv6PG31y9uyPNDycysn1MF3sljNeV3HNvyJ/vzWuKHuYoQKY/EVDX9k0rae6lh0ojCtV1hf2AxuHD3wj0is+ww/hkO31A6zz32vmUgv70P/+rkFmhMDpc4ycaYiT8FT1y74z+l5Zsv8m/xoMSWK73jh/Z/dfSF8Ra1tuHIuWtzvrZMnah+462rnQcbaq5V7P0f7ZWFd6IeAAA=";
}
