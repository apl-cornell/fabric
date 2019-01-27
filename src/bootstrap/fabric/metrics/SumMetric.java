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
                            throw new fabric.worker.UserAbortException($e444);
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
                                            $e444);
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
                            throw new fabric.worker.UserAbortException($e457);
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
                                            $e457);
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
    
    public static final byte[] $classHash = new byte[] { 11, -55, -18, -107,
    -101, -89, -12, 1, 27, 53, -34, -20, -105, -125, 96, 5, 29, -111, -115, 60,
    14, -104, 1, -75, 55, 76, 5, -14, 74, 84, -128, -85 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548260582000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
