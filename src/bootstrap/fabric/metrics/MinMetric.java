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
import fabric.worker.transaction.TransactionManager;

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
                    fabric.metrics.DerivedMetric val$var197 = val;
                    fabric.worker.transaction.TransactionManager $tm203 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled206 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff204 = 1;
                    boolean $doBackoff205 = true;
                    boolean $retry200 = true;
                    $label198: for (boolean $commit199 = false; !$commit199; ) {
                        if ($backoffEnabled206) {
                            if ($doBackoff205) {
                                if ($backoff204 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff204);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e201) {
                                            
                                        }
                                    }
                                }
                                if ($backoff204 < 5000) $backoff204 *= 2;
                            }
                            $doBackoff205 = $backoff204 <= 32 || !$doBackoff205;
                        }
                        $commit199 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e201) {
                            $commit199 = false;
                            continue $label198;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e201) {
                            $commit199 = false;
                            fabric.common.TransactionID $currentTid202 =
                              $tm203.getCurrentTid();
                            if ($e201.tid.isDescendantOf($currentTid202))
                                continue $label198;
                            if ($currentTid202.parent != null) {
                                $retry200 = false;
                                throw $e201;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e201) {
                            $commit199 = false;
                            if ($tm203.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid202 =
                              $tm203.getCurrentTid();
                            if ($e201.tid.isDescendantOf($currentTid202)) {
                                $retry200 = true;
                            }
                            else if ($currentTid202.parent != null) {
                                $retry200 = false;
                                throw $e201;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e201) {
                            $commit199 = false;
                            if ($tm203.checkForStaleObjects())
                                continue $label198;
                            $retry200 = false;
                            throw new fabric.worker.AbortException($e201);
                        }
                        finally {
                            if ($commit199) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e201) {
                                    $commit199 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e201) {
                                    $commit199 = false;
                                    fabric.common.TransactionID $currentTid202 =
                                      $tm203.getCurrentTid();
                                    if ($currentTid202 != null) {
                                        if ($e201.tid.equals($currentTid202) ||
                                              !$e201.tid.isDescendantOf(
                                                           $currentTid202)) {
                                            throw $e201;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit199 && $retry200) {
                                { val = val$var197; }
                                continue $label198;
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
                    fabric.metrics.DerivedMetric val$var207 = val;
                    fabric.worker.transaction.TransactionManager $tm213 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled216 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff214 = 1;
                    boolean $doBackoff215 = true;
                    boolean $retry210 = true;
                    $label208: for (boolean $commit209 = false; !$commit209; ) {
                        if ($backoffEnabled216) {
                            if ($doBackoff215) {
                                if ($backoff214 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff214);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e211) {
                                            
                                        }
                                    }
                                }
                                if ($backoff214 < 5000) $backoff214 *= 2;
                            }
                            $doBackoff215 = $backoff214 <= 32 || !$doBackoff215;
                        }
                        $commit209 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e211) {
                            $commit209 = false;
                            continue $label208;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e211) {
                            $commit209 = false;
                            fabric.common.TransactionID $currentTid212 =
                              $tm213.getCurrentTid();
                            if ($e211.tid.isDescendantOf($currentTid212))
                                continue $label208;
                            if ($currentTid212.parent != null) {
                                $retry210 = false;
                                throw $e211;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e211) {
                            $commit209 = false;
                            if ($tm213.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid212 =
                              $tm213.getCurrentTid();
                            if ($e211.tid.isDescendantOf($currentTid212)) {
                                $retry210 = true;
                            }
                            else if ($currentTid212.parent != null) {
                                $retry210 = false;
                                throw $e211;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e211) {
                            $commit209 = false;
                            if ($tm213.checkForStaleObjects())
                                continue $label208;
                            $retry210 = false;
                            throw new fabric.worker.AbortException($e211);
                        }
                        finally {
                            if ($commit209) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e211) {
                                    $commit209 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e211) {
                                    $commit209 = false;
                                    fabric.common.TransactionID $currentTid212 =
                                      $tm213.getCurrentTid();
                                    if ($currentTid212 != null) {
                                        if ($e211.tid.equals($currentTid212) ||
                                              !$e211.tid.isDescendantOf(
                                                           $currentTid212)) {
                                            throw $e211;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit209 && $retry210) {
                                { val = val$var207; }
                                continue $label208;
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
                        fabric.metrics.DerivedMetric val$var217 = val;
                        int aggIdx$var218 = aggIdx;
                        fabric.worker.transaction.TransactionManager $tm224 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled227 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff225 = 1;
                        boolean $doBackoff226 = true;
                        boolean $retry221 = true;
                        $label219: for (boolean $commit220 = false; !$commit220;
                                        ) {
                            if ($backoffEnabled227) {
                                if ($doBackoff226) {
                                    if ($backoff225 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff225);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e222) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff225 < 5000) $backoff225 *= 2;
                                }
                                $doBackoff226 = $backoff225 <= 32 ||
                                                  !$doBackoff226;
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
                                        fabric.common.TransactionID
                                          $currentTid223 =
                                          $tm224.getCurrentTid();
                                        if ($currentTid223 != null) {
                                            if ($e222.tid.equals(
                                                            $currentTid223) ||
                                                  !$e222.tid.
                                                  isDescendantOf(
                                                    $currentTid223)) {
                                                throw $e222;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit220 && $retry221) {
                                    {
                                        val = val$var217;
                                        aggIdx = aggIdx$var218;
                                    }
                                    continue $label219;
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
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base,
                          fabric.worker.metrics.StatsMap weakStats,
                          final fabric.worker.Store s) {
            fabric.worker.metrics.treaties.MetricTreaty[] witnesses =
              new fabric.worker.metrics.treaties.MetricTreaty[this.get$terms(
                                                                     ).length(
                                                                         )];
            for (int i = 0; i < this.get$terms().length(); i++) {
                witnesses[i] = term(i).getThresholdTreaty(rate, base);
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
    
    public static final byte[] $classHash = new byte[] { 60, -76, 60, 34, 10,
    -28, -71, 35, -115, -114, -6, 25, -112, 18, -53, -12, -66, -123, -128, -5,
    -61, -45, 0, 10, -124, -74, -100, -107, -90, -13, -19, 71 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1528821850000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
