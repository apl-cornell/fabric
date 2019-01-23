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
                    fabric.metrics.DerivedMetric val$var439 = val;
                    fabric.worker.transaction.TransactionManager $tm446 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled449 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff447 = 1;
                    boolean $doBackoff448 = true;
                    boolean $retry442 = true;
                    boolean $keepReads443 = false;
                    $label440: for (boolean $commit441 = false; !$commit441; ) {
                        if ($backoffEnabled449) {
                            if ($doBackoff448) {
                                if ($backoff447 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff447));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e444) {
                                            
                                        }
                                    }
                                }
                                if ($backoff447 < 5000) $backoff447 *= 2;
                            }
                            $doBackoff448 = $backoff447 <= 32 || !$doBackoff448;
                        }
                        $commit441 = true;
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
                            catch (final fabric.worker.RetryException $e444) {
                                throw $e444;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e444) {
                                throw $e444;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e444) {
                                throw $e444;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e444) {
                                throw $e444;
                            }
                            catch (final Throwable $e444) {
                                $tm446.getCurrentLog().checkRetrySignal();
                                throw $e444;
                            }
                        }
                        catch (final fabric.worker.RetryException $e444) {
                            $commit441 = false;
                            continue $label440;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e444) {
                            $commit441 = false;
                            $retry442 = false;
                            $keepReads443 = $e444.keepReads;
                            if ($tm446.checkForStaleObjects()) {
                                $retry442 = true;
                                $keepReads443 = false;
                                continue $label440;
                            }
                            fabric.common.TransactionID $currentTid445 =
                              $tm446.getCurrentTid();
                            if ($e444.tid == null ||
                                  !$e444.tid.isDescendantOf($currentTid445)) {
                                throw $e444;
                            }
                            throw new fabric.worker.UserAbortException();
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e444) {
                            $commit441 = false;
                            fabric.common.TransactionID $currentTid445 =
                              $tm446.getCurrentTid();
                            if ($e444.tid.isDescendantOf($currentTid445))
                                continue $label440;
                            if ($currentTid445.parent != null) {
                                $retry442 = false;
                                throw $e444;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e444) {
                            $commit441 = false;
                            if ($tm446.checkForStaleObjects())
                                continue $label440;
                            fabric.common.TransactionID $currentTid445 =
                              $tm446.getCurrentTid();
                            if ($e444.tid.isDescendantOf($currentTid445)) {
                                $retry442 = true;
                            }
                            else if ($currentTid445.parent != null) {
                                $retry442 = false;
                                throw $e444;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e444) {
                            $commit441 = false;
                            if ($tm446.checkForStaleObjects())
                                continue $label440;
                            $retry442 = false;
                            throw new fabric.worker.AbortException($e444);
                        }
                        finally {
                            if ($commit441) {
                                fabric.common.TransactionID $currentTid445 =
                                  $tm446.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e444) {
                                    $commit441 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e444) {
                                    $commit441 = false;
                                    $retry442 = false;
                                    $keepReads443 = $e444.keepReads;
                                    if ($tm446.checkForStaleObjects()) {
                                        $retry442 = true;
                                        $keepReads443 = false;
                                        continue $label440;
                                    }
                                    if ($e444.tid ==
                                          null ||
                                          !$e444.tid.isDescendantOf(
                                                       $currentTid445))
                                        throw $e444;
                                    throw new fabric.worker.UserAbortException(
                                            );
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e444) {
                                    $commit441 = false;
                                    $currentTid445 = $tm446.getCurrentTid();
                                    if ($currentTid445 != null) {
                                        if ($e444.tid.equals($currentTid445) ||
                                              !$e444.tid.isDescendantOf(
                                                           $currentTid445)) {
                                            throw $e444;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads443) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit441) {
                                { val = val$var439; }
                                if ($retry442) { continue $label440; }
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
                    fabric.metrics.DerivedMetric val$var450 = val;
                    fabric.metrics.Metric[] newTerms$var451 = newTerms;
                    int termIdx$var452 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm459 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled462 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff460 = 1;
                    boolean $doBackoff461 = true;
                    boolean $retry455 = true;
                    boolean $keepReads456 = false;
                    $label453: for (boolean $commit454 = false; !$commit454; ) {
                        if ($backoffEnabled462) {
                            if ($doBackoff461) {
                                if ($backoff460 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff460));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e457) {
                                            
                                        }
                                    }
                                }
                                if ($backoff460 < 5000) $backoff460 *= 2;
                            }
                            $doBackoff461 = $backoff460 <= 32 || !$doBackoff461;
                        }
                        $commit454 = true;
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
                            catch (final fabric.worker.RetryException $e457) {
                                throw $e457;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e457) {
                                throw $e457;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e457) {
                                throw $e457;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e457) {
                                throw $e457;
                            }
                            catch (final Throwable $e457) {
                                $tm459.getCurrentLog().checkRetrySignal();
                                throw $e457;
                            }
                        }
                        catch (final fabric.worker.RetryException $e457) {
                            $commit454 = false;
                            continue $label453;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e457) {
                            $commit454 = false;
                            $retry455 = false;
                            $keepReads456 = $e457.keepReads;
                            if ($tm459.checkForStaleObjects()) {
                                $retry455 = true;
                                $keepReads456 = false;
                                continue $label453;
                            }
                            fabric.common.TransactionID $currentTid458 =
                              $tm459.getCurrentTid();
                            if ($e457.tid == null ||
                                  !$e457.tid.isDescendantOf($currentTid458)) {
                                throw $e457;
                            }
                            throw new fabric.worker.UserAbortException();
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e457) {
                            $commit454 = false;
                            fabric.common.TransactionID $currentTid458 =
                              $tm459.getCurrentTid();
                            if ($e457.tid.isDescendantOf($currentTid458))
                                continue $label453;
                            if ($currentTid458.parent != null) {
                                $retry455 = false;
                                throw $e457;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e457) {
                            $commit454 = false;
                            if ($tm459.checkForStaleObjects())
                                continue $label453;
                            fabric.common.TransactionID $currentTid458 =
                              $tm459.getCurrentTid();
                            if ($e457.tid.isDescendantOf($currentTid458)) {
                                $retry455 = true;
                            }
                            else if ($currentTid458.parent != null) {
                                $retry455 = false;
                                throw $e457;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e457) {
                            $commit454 = false;
                            if ($tm459.checkForStaleObjects())
                                continue $label453;
                            $retry455 = false;
                            throw new fabric.worker.AbortException($e457);
                        }
                        finally {
                            if ($commit454) {
                                fabric.common.TransactionID $currentTid458 =
                                  $tm459.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e457) {
                                    $commit454 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e457) {
                                    $commit454 = false;
                                    $retry455 = false;
                                    $keepReads456 = $e457.keepReads;
                                    if ($tm459.checkForStaleObjects()) {
                                        $retry455 = true;
                                        $keepReads456 = false;
                                        continue $label453;
                                    }
                                    if ($e457.tid ==
                                          null ||
                                          !$e457.tid.isDescendantOf(
                                                       $currentTid458))
                                        throw $e457;
                                    throw new fabric.worker.UserAbortException(
                                            );
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e457) {
                                    $commit454 = false;
                                    $currentTid458 = $tm459.getCurrentTid();
                                    if ($currentTid458 != null) {
                                        if ($e457.tid.equals($currentTid458) ||
                                              !$e457.tid.isDescendantOf(
                                                           $currentTid458)) {
                                            throw $e457;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads456) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit454) {
                                {
                                    val = val$var450;
                                    newTerms = newTerms$var451;
                                    termIdx = termIdx$var452;
                                }
                                if ($retry455) { continue $label453; }
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
                    long adaptiveRealTimeout = java.lang.Long.MAX_VALUE;
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
                            adaptiveRealTimeout =
                              java.lang.Math.
                                min(
                                  adaptiveRealTimeout,
                                  fabric.worker.metrics.treaties.statements.ThresholdStatement.
                                      hedgedExpiry(m, newStmt.rate,
                                                   newStmt.base, currentTime,
                                                   weakStats));
                        }
                    }
                    if (adaptiveTimeout > staticTimeout &&
                          adaptiveRealTimeout >=
                          java.lang.System.currentTimeMillis() + 60000) {
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
                     fabric.worker.metrics.ImmutableObjectSet associates,

                     fabric.worker.metrics.treaties.TreatySet treaties, long expiry,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, treaties, expiry,
                  labelStore, labelOnum, accessPolicyStore, accessPolicyOnum,
                  in, refTypes, intraStoreRefs, interStoreRefs);
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

                         fabric.worker.metrics.treaties.TreatySet treaties, long expiry,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, treaties, expiry,
                      labelStore, labelOnum, accessPolicyStore,
                      accessPolicyOnum, in, refTypes, intraStoreRefs,
                      interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.SumMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -73, -114, -26, -69, 7,
    -48, -55, 106, -16, -28, 63, 100, -31, 87, -41, 92, 8, -104, 50, -74, 68,
    -59, -94, -34, -64, 101, 95, -49, 6, 113, 70, -36 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1537039040000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
