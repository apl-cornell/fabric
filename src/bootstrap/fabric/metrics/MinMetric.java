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
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
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
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
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
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    newTerms);
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
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
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
    
    public static final byte[] $classHash = new byte[] { -38, 10, -124, -13, 37,
    -1, 14, -45, 58, 20, -112, 82, 92, -53, 52, -90, -127, 43, 46, -31, -76, 16,
    -35, 57, -111, -67, -74, 51, -3, -26, -54, 113 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1532368893000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0ZC3BUV/XusvkS8uNXQhJCCLT8doeWqpBqS7Z8QpYQE2CmgRJf3t7NPvL2veW9u2EDTYdaEHQsUzGlZWoZR1ArpjBqq46asdqqUJwOpR1LHdtSZ+hHZCrDqB2nFc+57+7v7e5r1oHh3fNy7z3nnv859+3YVVJkGqQ5JPUrqpcNR6npXSP1twe6JMOkQb8qmeYmmO2TJ3vaj7z//WCjm7gDpEKWNF1TZEnt00xGKgM7pCHJp1Hm29zd3rqVlMmIuE4yw4y4t7bFDdIU1dXhAVVn4pAs+o8t9o0+vr36x5NIVS+pUrQeJjFF9usao3HWSyoiNNJPDXNVMEiDvaRGozTYQw1FUpXdsFHXekmtqQxoEosZ1Oympq4O4cZaMxalBj8zMYns68C2EZOZbgD71Rb7MaaovoBistYAKQ4pVA2aO8mDxBMgRSFVGoCNMwIJKXycom8NzsP2cgXYNEKSTBMonkFFCzIyx46RlLilAzYAakmEsrCePMqjSTBBai2WVEkb8PUwQ9EGYGuRHoNTGKnLSxQ2lUYleVAaoH2M3GLf12Utwa4yrhZEYWS6fRunBDars9kszVpXO+86tEdbp7mJC3gOUllF/ksBqdGG1E1D1KCaTC3EikWBI9KM8YNuQmDzdNtma8/PHrh2z5LG589Ye2bn2LOxfweVWZ98or/ylXr/whWTkI3SqG4q6AoZknOrdomV1ngUvH1GkiIuehOLz3f//r69J+kVNylvJ8WyrsYi4FU1sh6JKio11lKNGhKjwXZSRrWgn6+3kxJ4DygatWY3hkImZe3Eo/KpYp3/DSoKAQlUUQm8K1pIT7xHJRbm7/EoIaQEHuKC/ysIWbgb3psI8WxjZK0vrEeor1+N0V3g3j54qGTIYR/EraHIPtOQfUZMYwpsElPgRQBM3wZF28BfvcBC9OaRiiPX1btcLlDoHFkP0n7JBOsIT2nrUiEY1ulqkBp9snpovJ1MHT/KvaUMPdwEL+X6cIGF6+25IR13NNa2+tqpvnOWpyGuUBdY2eLPK/jzJvkDliowfryQkbyQkcZcca//WPsPuZsUmzyeklQqgMrKqCqxkG5E4sTl4iJN4/jcP8C6g5A1IDFULOy5f/2XDjZPAseM7vKgrWBriz1MUsmlHd4k8P0+uerA+/86fWRETwUMIy1ZcZyNiXHYbNePocs0CHkuRX5Rk/Rc3/hIixtzSBmkNyaBA0KuaLSfkRGPrYnchtooCpDJqANJxaVEQipnYUPflZrhdq/EodZyAVSWjUGeFj/fE33q4ssf3MELRiKDVqWl2h7KWtOiFolV8fisSel+k0Ep7Hvzia5vPnb1wFaueNgxL9eBLTj6IVolCFPd2H9m5xtvv3XiNXfKWIwUR2P9qiLHuSw1N+CfC57/4oOhhxMIIQH7Rdg3JeM+iicvSPEGGUCFLASsmy2btYgeVEKK1K9S9JSPq+Yve+7vh6otc6swYynPIEs+nUBqflYb2Xtu+78bORmXjBUopb/UNiutTU1RXmUY0jDyEX/oQsPRP0hPgedDUjKV3ZTnGcL1QbgBb+e6WMrHZba15Tg0W9qqF/P8j3l8XIDDQku3+LpI6JWIf8Uig20VcDOuTo3iOC2TpkEa8hUbXihPfHn0WHDjd5dZJaE2M4Gv1mKRZ/70yR+9T1w6myNNlDE9ulSlQ1RNO7MWjpyb1fVs4LU4FVaXrjSs8A9eHrCOnWNj0b77BxvGzq5dIB92k0nJGM9qADKRWtOZhWAzKPQvGoqNM+XcCE1JpU5GZa2G5zZCivYIuCpNqSIiuYVw+GwSleu5XKDcI+BKuz1SXuBKprfZ6VpaDy7Gncuq1NshVM8Pf3jE0o+9X0jb+I+xt69cmNJwiucnD5YKLp+90cruozLaIy5eRVKmO1GmVni6QTXVApYz0vH/l7h7oaWEFjGjYt5MclaATIemy167OMTFuhw2sDdVa1BpKcfr9Y19q87/hStWfU1mf6QzN0d93SKlFabbT0b+6W4u/p2blPSSat4YSxrbIoG0kHh7wSSmX0wGyJSM9cw21erJWpOeX2/3/LRj7XUnPQY8LMP7eanpiLsId9PNubOPm2cfBkQVTbJak8WQ7FWqDbBwDv11GUoEis6QaErpwdGv3fAeGrWSh9W5z8tqntNxrO6dHzSFn4YpbK7TKRxjzXunR3759MgBt8ivrYxMApfH1/XxpN3dlkwJV7HyOuoYkqKuUSwRfG0W5DZsXlQdLmJJz7I6F0X3Jq9H/VbrKcezPAv/vttSMOchLXNwqRxKw6DDWgQHuPoVychvgrHqlByWr1hMcYy1DtS4H7cxMssKmRYRMi3Jdq8llfXuzsyVzfB8BtrqJwXcV1iuRJSHBRzJnyvTmR12WNuDA4PrMl4p4CrXhZmEdfO9vcKLENwPrhvUYwkz55DpbkJKRwUcKUwmRHlAwKGJybTfYe0ADnvtMrXh7Eg+7tcRUvaIgEOFcY8oMQH1iXF/yGHtURy+aud+iyP3XwROviKgXhj3iKIJGJ4Y9487rB3F4bCd+85c3FcSUS97QZLDAg7m4T6rt4M8EzV0BiFLg/FMsaYIWjsElPKLlZ50IB00igq4SzcGqZEshPjdx9wgRRMZLvN6xtn8joNOnsbhSYafeLhOeKHKq5HPwSMTUnFcwGEHex7LFhxR4gI6eKMr1SEf51RPOwjwIxxOwk0pIQCF9K6wYUerqsDPswI+UpgMiPJ1AR8uQIafO8jwCxx+kjJCp66YOY3Ag6oeHojpyiMC7i8sqBBln4APTiyofuOw9gIOv2KklOnWh7cc5SttIctDc0m4FB6oI1XvCvhiYRIiygsCjk/IRCOc6ssOYp7H4SyUaGxbzYSM9bauNKN/xT11NvGmIsEOeKAa1dZZsOavE8wrbkZKonAAXK4ZfpbBD7629FIrSL4j4Kv5xXenOsHqlA7+4qCDt3B4DQxoHd3HVYFzF3IZcTE8zxAyXRdwY2FGRJROAddNyIidnOplBwHew+ESI56oGsvJODdPOzy/JmTmZQFPT9Q8+HoRhzdyWAUpnRLw2xO3iiXUhw5CXcPhA0YmC6vkk40bZR48FwmZ7RNwZmFGQZQZAlYXYJSPHPj/Dw7XobGPKPybVmcum6yC52+ENBwUsPem2AQp3SdgR4E2cbnzy+Ty4OTHjJQLm+QRjZtEAU01EDLfZ8GWVwoyCUc5L+BL+WXwcO44XyPJ4Xgij03N7C16mG4kr0zZDYVrsoPoNTgUQz1mYaAR1tVgl64q8nDiqLtytzHMoKApuARSDc6RaYRqzLs69W4RQRpyLiXOAg0sIeTW1wU8W5gSEeWMgL/Nr8R0Oesc1upxAFlLw5IZ9utBXsu35eL7VjgUsG8bELCjML4RZb2A935qPCZMUCtMkHa1dDD2PAdBF+LQCMWI7oxJqlUWbTezkn5dV6mkxaEpTt5A8fPr7Bw/goif4mT/i/TE5Y4l0/P8AHJL1o+jAu/UsarSmcc2v259Nkv8zFYWIKWhmKqmfzRJey+OGjSkcG2XWZ9Qoly4ZXBTyKzvjH9ewzeUzuWz9i0H+a19+Ned3AJ1yeECJ1kXM/CH3bHrMz8qLt10iX9jByU2/bl83/X5NypfXTnt0e5t55Z/76HF3neerX5zxTfGf3rHJ+++tPN/f2gvsXAeAAA=";
}
