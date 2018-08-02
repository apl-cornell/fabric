package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Arrays;
import fabric.util.Set;
import fabric.util.TreeSet;
import fabric.util.Iterator;
import fabric.util.LinkedHashMap;
import fabric.util.Map;
import fabric.metrics.contracts.Bound;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.enforcement.DirectPolicy;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.metrics.treaties.enforcement.WitnessPolicy;
import fabric.worker.metrics.treaties.statements.EqualityStatement;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import fabric.worker.transaction.TransactionManager;
import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;

/**
 * A {@link DerivedMetric} for the minimum of a group of other {@link Metric}s.
 */
public interface MinMetric extends fabric.metrics.DerivedMetric {
    /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param terms
     *            The {@link Metric}s this applies to
     */
    public fabric.metrics.MinMetric fabric$metrics$MinMetric$(
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
     * {@link MinMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
    /**
     * {@inheritDoc}
     * <p>
     * {@link MinMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
    public fabric.metrics.Metric min(fabric.metrics.Metric other);
    
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      thresholdPolicy(double rate, double base,
                      fabric.worker.metrics.StatsMap weakStats,
                      final fabric.worker.Store s);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy extends fabric.metrics.DerivedMetric._Proxy
      implements fabric.metrics.MinMetric {
        public fabric.metrics.MinMetric fabric$metrics$MinMetric$(
          fabric.metrics.Metric[] arg1) {
            return ((fabric.metrics.MinMetric) fetch()).
              fabric$metrics$MinMetric$(arg1);
        }
        
        public _Proxy(MinMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.DerivedMetric._Impl
      implements fabric.metrics.MinMetric {
        /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param terms
     *            The {@link Metric}s this applies to
     */
        public fabric.metrics.MinMetric fabric$metrics$MinMetric$(
          fabric.metrics.Metric[] terms) {
            fabric$metrics$DerivedMetric$(terms);
            java.util.Set termsBag =
              new java.util.TreeSet(java.util.Arrays.asList(terms));
            fabric.metrics.Metric[] termsA =
              new fabric.metrics.Metric[termsBag.size()];
            int tIdx = 0;
            for (java.util.Iterator it = termsBag.iterator(); it.hasNext(); ) {
                termsA[tIdx++] =
                  (fabric.metrics.Metric)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      fabric.lang.WrappedJavaInlineable.$wrap(it.next()));
            }
            this.set$terms(
                   fabric.worker.metrics.ImmutableMetricsVector.createVector(
                                                                  termsA));
            initialize();
            return (fabric.metrics.MinMetric) this.$getProxy();
        }
        
        public double computePresetR() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result = java.lang.Math.max(result, term(i).getPresetR());
            }
            return result;
        }
        
        public double computePresetB() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result = java.lang.Math.max(result, term(i).getPresetB());
            }
            return result;
        }
        
        public double computePresetV() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result = java.lang.Math.min(result, term(i).getPresetV());
            }
            return result;
        }
        
        public double computePresetN() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result = java.lang.Math.max(result, term(i).getPresetN());
            }
            return result;
        }
        
        public double computeValue(fabric.worker.metrics.StatsMap weakStats) {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result = java.lang.Math.min(result, term(i).value(weakStats));
            }
            return result;
        }
        
        public double computeVelocity(
          fabric.worker.metrics.StatsMap weakStats) {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result = java.lang.Math.min(result,
                                            term(i).velocity(weakStats));
            }
            return result;
        }
        
        public double computeNoise(fabric.worker.metrics.StatsMap weakStats) {
            double noise = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                noise = java.lang.Math.max(noise, term(i).noise(weakStats));
            }
            return noise;
        }
        
        public java.lang.String toString() {
            java.lang.String str = "min(";
            boolean nonEmpty = false;
            for (int i = 0; i < this.get$terms().length(); i++) {
                if (nonEmpty) { str += ", "; }
                nonEmpty = true;
                str += term(i);
            }
            return str + ")@" + $getStore();
        }
        
        public fabric.metrics.DerivedMetric times(double scalar) {
            return fabric.metrics.MinMetric._Impl.static_times(
                                                    (fabric.metrics.MinMetric)
                                                      this.$getProxy(), scalar);
        }
        
        private static fabric.metrics.DerivedMetric static_times(
          fabric.metrics.MinMetric tmp, double scalar) {
            fabric.metrics.Metric[] newTerms =
              new fabric.metrics.Metric[tmp.get$terms().length()];
            for (int i = 0; i < tmp.get$terms().length(); i++) {
                newTerms[i] = tmp.term(i).times(scalar);
            }
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.MinMetric)
                     new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                    fabric$metrics$MinMetric$(newTerms);
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var218 = val;
                    fabric.worker.transaction.TransactionManager $tm224 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled227 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff225 = 1;
                    boolean $doBackoff226 = true;
                    boolean $retry221 = true;
                    $label219: for (boolean $commit220 = false; !$commit220; ) {
                        if ($backoffEnabled227) {
                            if ($doBackoff226) {
                                if ($backoff225 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff225);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e222) {
                                            
                                        }
                                    }
                                }
                                if ($backoff225 < 5000) $backoff225 *= 2;
                            }
                            $doBackoff226 = $backoff225 <= 32 || !$doBackoff226;
                        }
                        $commit220 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    newTerms);
                            }
                            catch (final fabric.worker.RetryException $e222) {
                                throw $e222;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e222) {
                                throw $e222;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e222) {
                                throw $e222;
                            }
                            catch (final Throwable $e222) {
                                $tm224.getCurrentLog().checkRetrySignal();
                                throw $e222;
                            }
                        }
                        catch (final fabric.worker.RetryException $e222) {
                            $commit220 = false;
                            continue $label219;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e222) {
                            $commit220 = false;
                            fabric.common.TransactionID $currentTid223 =
                              $tm224.getCurrentTid();
                            if ($e222.tid.isDescendantOf($currentTid223))
                                continue $label219;
                            if ($currentTid223.parent != null) {
                                $retry221 = false;
                                throw $e222;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e222) {
                            $commit220 = false;
                            if ($tm224.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid223 =
                              $tm224.getCurrentTid();
                            if ($e222.tid.isDescendantOf($currentTid223)) {
                                $retry221 = true;
                            }
                            else if ($currentTid223.parent != null) {
                                $retry221 = false;
                                throw $e222;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e222) {
                            $commit220 = false;
                            if ($tm224.checkForStaleObjects())
                                continue $label219;
                            $retry221 = false;
                            throw new fabric.worker.AbortException($e222);
                        }
                        finally {
                            if ($commit220) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e222) {
                                    $commit220 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e222) {
                                    $commit220 = false;
                                    fabric.common.TransactionID $currentTid223 =
                                      $tm224.getCurrentTid();
                                    if ($currentTid223 != null) {
                                        if ($e222.tid.equals($currentTid223) ||
                                              !$e222.tid.isDescendantOf(
                                                           $currentTid223)) {
                                            throw $e222;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit220 && $retry221) {
                                { val = val$var218; }
                                continue $label219;
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
     * {@link MinMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            return fabric.metrics.MinMetric._Impl.static_plus(
                                                    (fabric.metrics.MinMetric)
                                                      this.$getProxy(), other);
        }
        
        private static fabric.metrics.DerivedMetric static_plus(
          fabric.metrics.MinMetric tmp, fabric.metrics.Metric other) {
            fabric.metrics.Metric[] newTerms =
              new fabric.metrics.Metric[tmp.get$terms().length()];
            for (int i = 0; i < newTerms.length; i++) {
                newTerms[i] = other.plus(tmp.term(i));
            }
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.MinMetric)
                     new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                    fabric$metrics$MinMetric$(newTerms);
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var228 = val;
                    fabric.worker.transaction.TransactionManager $tm234 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled237 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff235 = 1;
                    boolean $doBackoff236 = true;
                    boolean $retry231 = true;
                    $label229: for (boolean $commit230 = false; !$commit230; ) {
                        if ($backoffEnabled237) {
                            if ($doBackoff236) {
                                if ($backoff235 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff235);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e232) {
                                            
                                        }
                                    }
                                }
                                if ($backoff235 < 5000) $backoff235 *= 2;
                            }
                            $doBackoff236 = $backoff235 <= 32 || !$doBackoff236;
                        }
                        $commit230 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    newTerms);
                            }
                            catch (final fabric.worker.RetryException $e232) {
                                throw $e232;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e232) {
                                throw $e232;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e232) {
                                throw $e232;
                            }
                            catch (final Throwable $e232) {
                                $tm234.getCurrentLog().checkRetrySignal();
                                throw $e232;
                            }
                        }
                        catch (final fabric.worker.RetryException $e232) {
                            $commit230 = false;
                            continue $label229;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e232) {
                            $commit230 = false;
                            fabric.common.TransactionID $currentTid233 =
                              $tm234.getCurrentTid();
                            if ($e232.tid.isDescendantOf($currentTid233))
                                continue $label229;
                            if ($currentTid233.parent != null) {
                                $retry231 = false;
                                throw $e232;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e232) {
                            $commit230 = false;
                            if ($tm234.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid233 =
                              $tm234.getCurrentTid();
                            if ($e232.tid.isDescendantOf($currentTid233)) {
                                $retry231 = true;
                            }
                            else if ($currentTid233.parent != null) {
                                $retry231 = false;
                                throw $e232;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e232) {
                            $commit230 = false;
                            if ($tm234.checkForStaleObjects())
                                continue $label229;
                            $retry231 = false;
                            throw new fabric.worker.AbortException($e232);
                        }
                        finally {
                            if ($commit230) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e232) {
                                    $commit230 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e232) {
                                    $commit230 = false;
                                    fabric.common.TransactionID $currentTid233 =
                                      $tm234.getCurrentTid();
                                    if ($currentTid233 != null) {
                                        if ($e232.tid.equals($currentTid233) ||
                                              !$e232.tid.isDescendantOf(
                                                           $currentTid233)) {
                                            throw $e232;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit230 && $retry231) {
                                { val = val$var228; }
                                continue $label229;
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
     * {@link MinMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
        public fabric.metrics.Metric min(fabric.metrics.Metric other) {
            return fabric.metrics.MinMetric._Impl.static_min(
                                                    (fabric.metrics.MinMetric)
                                                      this.$getProxy(), other);
        }
        
        private static fabric.metrics.Metric static_min(
          fabric.metrics.MinMetric tmp, fabric.metrics.Metric other) {
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.MinMetric &&
                  other.$getStore().equals(s)) {
                fabric.metrics.MinMetric that =
                  (fabric.metrics.MinMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                java.util.Set termsBag = new java.util.TreeSet();
                for (int i = 0; i < tmp.get$terms().length(); i++) {
                    termsBag.add(
                               (java.lang.Object)
                                 fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                     tmp.term(
                                                                           i)));
                }
                for (int i = 0; i < that.get$terms().length(); i++) {
                    termsBag.
                      add(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.$unwrap(
                                                              that.term(i)));
                }
                fabric.metrics.Metric[] newTerms =
                  new fabric.metrics.Metric[termsBag.size()];
                int aggIdx = 0;
                for (java.util.Iterator iter = termsBag.iterator();
                     iter.hasNext(); ) {
                    fabric.metrics.Metric
                      m =
                      (fabric.metrics.Metric)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          fabric.lang.WrappedJavaInlineable.$wrap(iter.next()));
                    newTerms[aggIdx++] = m;
                }
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    val =
                      ((fabric.metrics.MinMetric)
                         new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                        fabric$metrics$MinMetric$(newTerms);
                }
                else {
                    {
                        fabric.metrics.DerivedMetric val$var238 = val;
                        int aggIdx$var239 = aggIdx;
                        fabric.worker.transaction.TransactionManager $tm245 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled248 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff246 = 1;
                        boolean $doBackoff247 = true;
                        boolean $retry242 = true;
                        $label240: for (boolean $commit241 = false; !$commit241;
                                        ) {
                            if ($backoffEnabled248) {
                                if ($doBackoff247) {
                                    if ($backoff246 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff246);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e243) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff246 < 5000) $backoff246 *= 2;
                                }
                                $doBackoff247 = $backoff246 <= 32 ||
                                                  !$doBackoff247;
                            }
                            $commit241 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    val =
                                      ((fabric.metrics.MinMetric)
                                         new fabric.metrics.MinMetric._Impl(s).
                                         $getProxy()).fabric$metrics$MinMetric$(
                                                        newTerms);
                                }
                                catch (final fabric.worker.
                                         RetryException $e243) {
                                    throw $e243;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e243) {
                                    throw $e243;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e243) {
                                    throw $e243;
                                }
                                catch (final Throwable $e243) {
                                    $tm245.getCurrentLog().checkRetrySignal();
                                    throw $e243;
                                }
                            }
                            catch (final fabric.worker.RetryException $e243) {
                                $commit241 = false;
                                continue $label240;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e243) {
                                $commit241 = false;
                                fabric.common.TransactionID $currentTid244 =
                                  $tm245.getCurrentTid();
                                if ($e243.tid.isDescendantOf($currentTid244))
                                    continue $label240;
                                if ($currentTid244.parent != null) {
                                    $retry242 = false;
                                    throw $e243;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e243) {
                                $commit241 = false;
                                if ($tm245.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid244 =
                                  $tm245.getCurrentTid();
                                if ($e243.tid.isDescendantOf($currentTid244)) {
                                    $retry242 = true;
                                }
                                else if ($currentTid244.parent != null) {
                                    $retry242 = false;
                                    throw $e243;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e243) {
                                $commit241 = false;
                                if ($tm245.checkForStaleObjects())
                                    continue $label240;
                                $retry242 = false;
                                throw new fabric.worker.AbortException($e243);
                            }
                            finally {
                                if ($commit241) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e243) {
                                        $commit241 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e243) {
                                        $commit241 = false;
                                        fabric.common.TransactionID
                                          $currentTid244 =
                                          $tm245.getCurrentTid();
                                        if ($currentTid244 != null) {
                                            if ($e243.tid.equals(
                                                            $currentTid244) ||
                                                  !$e243.tid.
                                                  isDescendantOf(
                                                    $currentTid244)) {
                                                throw $e243;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit241 && $retry242) {
                                    {
                                        val = val$var238;
                                        aggIdx = aggIdx$var239;
                                    }
                                    continue $label240;
                                }
                            }
                        }
                    }
                }
                return val;
            }
            else if (java.util.Arrays.asList(tmp.get$terms().array()).
                       indexOf(
                         (java.lang.Object)
                           fabric.lang.WrappedJavaInlineable.$unwrap(other)) >=
                       0) {
                return tmp;
            }
            fabric.metrics.Metric[] newTerms =
              new fabric.metrics.Metric[tmp.get$terms().length() + 1];
            for (int i = 0; i < tmp.get$terms().length(); i++) {
                newTerms[i] = tmp.term(i);
            }
            newTerms[tmp.get$terms().length()] = other;
            java.util.Arrays.sort(newTerms, 0, newTerms.length);
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.MinMetric)
                     new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                    fabric$metrics$MinMetric$(newTerms);
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var249 = val;
                    fabric.worker.transaction.TransactionManager $tm255 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled258 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff256 = 1;
                    boolean $doBackoff257 = true;
                    boolean $retry252 = true;
                    $label250: for (boolean $commit251 = false; !$commit251; ) {
                        if ($backoffEnabled258) {
                            if ($doBackoff257) {
                                if ($backoff256 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff256);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e253) {
                                            
                                        }
                                    }
                                }
                                if ($backoff256 < 5000) $backoff256 *= 2;
                            }
                            $doBackoff257 = $backoff256 <= 32 || !$doBackoff257;
                        }
                        $commit251 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    newTerms);
                            }
                            catch (final fabric.worker.RetryException $e253) {
                                throw $e253;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e253) {
                                throw $e253;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e253) {
                                throw $e253;
                            }
                            catch (final Throwable $e253) {
                                $tm255.getCurrentLog().checkRetrySignal();
                                throw $e253;
                            }
                        }
                        catch (final fabric.worker.RetryException $e253) {
                            $commit251 = false;
                            continue $label250;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e253) {
                            $commit251 = false;
                            fabric.common.TransactionID $currentTid254 =
                              $tm255.getCurrentTid();
                            if ($e253.tid.isDescendantOf($currentTid254))
                                continue $label250;
                            if ($currentTid254.parent != null) {
                                $retry252 = false;
                                throw $e253;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e253) {
                            $commit251 = false;
                            if ($tm255.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid254 =
                              $tm255.getCurrentTid();
                            if ($e253.tid.isDescendantOf($currentTid254)) {
                                $retry252 = true;
                            }
                            else if ($currentTid254.parent != null) {
                                $retry252 = false;
                                throw $e253;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e253) {
                            $commit251 = false;
                            if ($tm255.checkForStaleObjects())
                                continue $label250;
                            $retry252 = false;
                            throw new fabric.worker.AbortException($e253);
                        }
                        finally {
                            if ($commit251) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e253) {
                                    $commit251 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e253) {
                                    $commit251 = false;
                                    fabric.common.TransactionID $currentTid254 =
                                      $tm255.getCurrentTid();
                                    if ($currentTid254 != null) {
                                        if ($e253.tid.equals($currentTid254) ||
                                              !$e253.tid.isDescendantOf(
                                                           $currentTid254)) {
                                            throw $e253;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit251 && $retry252) {
                                { val = val$var249; }
                                continue $label250;
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
            com.google.common.collect.Multimap witnesses =
              com.google.common.collect.HashMultimap.create();
            for (int i = 0; i < this.get$terms().length(); i++) {
                witnesses.
                  put(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(term(i)),
                    new fabric.worker.metrics.treaties.statements.ThresholdStatement(
                      rate, base));
            }
            return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                     witnesses);
        }
        
        public int hashCode() {
            return java.util.Arrays.hashCode(this.get$terms().array()) * 32 +
              $getStore().hashCode();
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.MinMetric) {
                fabric.metrics.MinMetric that =
                  (fabric.metrics.MinMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                return java.util.Arrays.deepEquals(this.get$terms().array(),
                                                   that.get$terms().array()) &&
                  this.$getStore().equals(that.$getStore());
            }
            return false;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.MinMetric._Proxy(this);
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
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.MinMetric._Static {
            public _Proxy(fabric.metrics.MinMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.MinMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  MinMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.MinMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.MinMetric._Static._Impl.class);
                $instance = (fabric.metrics.MinMetric._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.MinMetric._Static {
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
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.MinMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -44, 15, 54, 117, 81,
    -126, -70, 56, -15, -20, 50, 44, 74, -62, 5, 63, 42, -105, 17, 67, -57, 2,
    -114, 42, 92, -56, 30, -14, -39, -70, 24, -42 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1533241119000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
