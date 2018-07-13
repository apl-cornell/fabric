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
                    fabric.metrics.DerivedMetric val$var198 = val;
                    fabric.worker.transaction.TransactionManager $tm204 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled207 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff205 = 1;
                    boolean $doBackoff206 = true;
                    boolean $retry201 = true;
                    $label199: for (boolean $commit200 = false; !$commit200; ) {
                        if ($backoffEnabled207) {
                            if ($doBackoff206) {
                                if ($backoff205 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff205);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e202) {
                                            
                                        }
                                    }
                                }
                                if ($backoff205 < 5000) $backoff205 *= 2;
                            }
                            $doBackoff206 = $backoff205 <= 32 || !$doBackoff206;
                        }
                        $commit200 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e202) {
                            $commit200 = false;
                            continue $label199;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e202) {
                            $commit200 = false;
                            fabric.common.TransactionID $currentTid203 =
                              $tm204.getCurrentTid();
                            if ($e202.tid.isDescendantOf($currentTid203))
                                continue $label199;
                            if ($currentTid203.parent != null) {
                                $retry201 = false;
                                throw $e202;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e202) {
                            $commit200 = false;
                            if ($tm204.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid203 =
                              $tm204.getCurrentTid();
                            if ($e202.tid.isDescendantOf($currentTid203)) {
                                $retry201 = true;
                            }
                            else if ($currentTid203.parent != null) {
                                $retry201 = false;
                                throw $e202;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e202) {
                            $commit200 = false;
                            if ($tm204.checkForStaleObjects())
                                continue $label199;
                            $retry201 = false;
                            throw new fabric.worker.AbortException($e202);
                        }
                        finally {
                            if ($commit200) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e202) {
                                    $commit200 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e202) {
                                    $commit200 = false;
                                    fabric.common.TransactionID $currentTid203 =
                                      $tm204.getCurrentTid();
                                    if ($currentTid203 != null) {
                                        if ($e202.tid.equals($currentTid203) ||
                                              !$e202.tid.isDescendantOf(
                                                           $currentTid203)) {
                                            throw $e202;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit200 && $retry201) {
                                { val = val$var198; }
                                continue $label199;
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
                    fabric.metrics.DerivedMetric val$var208 = val;
                    fabric.worker.transaction.TransactionManager $tm214 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled217 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff215 = 1;
                    boolean $doBackoff216 = true;
                    boolean $retry211 = true;
                    $label209: for (boolean $commit210 = false; !$commit210; ) {
                        if ($backoffEnabled217) {
                            if ($doBackoff216) {
                                if ($backoff215 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff215);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e212) {
                                            
                                        }
                                    }
                                }
                                if ($backoff215 < 5000) $backoff215 *= 2;
                            }
                            $doBackoff216 = $backoff215 <= 32 || !$doBackoff216;
                        }
                        $commit210 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e212) {
                            $commit210 = false;
                            continue $label209;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e212) {
                            $commit210 = false;
                            fabric.common.TransactionID $currentTid213 =
                              $tm214.getCurrentTid();
                            if ($e212.tid.isDescendantOf($currentTid213))
                                continue $label209;
                            if ($currentTid213.parent != null) {
                                $retry211 = false;
                                throw $e212;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e212) {
                            $commit210 = false;
                            if ($tm214.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid213 =
                              $tm214.getCurrentTid();
                            if ($e212.tid.isDescendantOf($currentTid213)) {
                                $retry211 = true;
                            }
                            else if ($currentTid213.parent != null) {
                                $retry211 = false;
                                throw $e212;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e212) {
                            $commit210 = false;
                            if ($tm214.checkForStaleObjects())
                                continue $label209;
                            $retry211 = false;
                            throw new fabric.worker.AbortException($e212);
                        }
                        finally {
                            if ($commit210) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e212) {
                                    $commit210 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e212) {
                                    $commit210 = false;
                                    fabric.common.TransactionID $currentTid213 =
                                      $tm214.getCurrentTid();
                                    if ($currentTid213 != null) {
                                        if ($e212.tid.equals($currentTid213) ||
                                              !$e212.tid.isDescendantOf(
                                                           $currentTid213)) {
                                            throw $e212;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit210 && $retry211) {
                                { val = val$var208; }
                                continue $label209;
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
                        fabric.metrics.DerivedMetric val$var218 = val;
                        int aggIdx$var219 = aggIdx;
                        fabric.worker.transaction.TransactionManager $tm225 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled228 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff226 = 1;
                        boolean $doBackoff227 = true;
                        boolean $retry222 = true;
                        $label220: for (boolean $commit221 = false; !$commit221;
                                        ) {
                            if ($backoffEnabled228) {
                                if ($doBackoff227) {
                                    if ($backoff226 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff226);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e223) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff226 < 5000) $backoff226 *= 2;
                                }
                                $doBackoff227 = $backoff226 <= 32 ||
                                                  !$doBackoff227;
                            }
                            $commit221 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    newTerms);
                            }
                            catch (final fabric.worker.RetryException $e223) {
                                $commit221 = false;
                                continue $label220;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e223) {
                                $commit221 = false;
                                fabric.common.TransactionID $currentTid224 =
                                  $tm225.getCurrentTid();
                                if ($e223.tid.isDescendantOf($currentTid224))
                                    continue $label220;
                                if ($currentTid224.parent != null) {
                                    $retry222 = false;
                                    throw $e223;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e223) {
                                $commit221 = false;
                                if ($tm225.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid224 =
                                  $tm225.getCurrentTid();
                                if ($e223.tid.isDescendantOf($currentTid224)) {
                                    $retry222 = true;
                                }
                                else if ($currentTid224.parent != null) {
                                    $retry222 = false;
                                    throw $e223;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e223) {
                                $commit221 = false;
                                if ($tm225.checkForStaleObjects())
                                    continue $label220;
                                $retry222 = false;
                                throw new fabric.worker.AbortException($e223);
                            }
                            finally {
                                if ($commit221) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e223) {
                                        $commit221 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e223) {
                                        $commit221 = false;
                                        fabric.common.TransactionID
                                          $currentTid224 =
                                          $tm225.getCurrentTid();
                                        if ($currentTid224 != null) {
                                            if ($e223.tid.equals(
                                                            $currentTid224) ||
                                                  !$e223.tid.
                                                  isDescendantOf(
                                                    $currentTid224)) {
                                                throw $e223;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit221 && $retry222) {
                                    {
                                        val = val$var218;
                                        aggIdx = aggIdx$var219;
                                    }
                                    continue $label220;
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
                    fabric.metrics.DerivedMetric val$var229 = val;
                    fabric.worker.transaction.TransactionManager $tm235 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled238 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff236 = 1;
                    boolean $doBackoff237 = true;
                    boolean $retry232 = true;
                    $label230: for (boolean $commit231 = false; !$commit231; ) {
                        if ($backoffEnabled238) {
                            if ($doBackoff237) {
                                if ($backoff236 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff236);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e233) {
                                            
                                        }
                                    }
                                }
                                if ($backoff236 < 5000) $backoff236 *= 2;
                            }
                            $doBackoff237 = $backoff236 <= 32 || !$doBackoff237;
                        }
                        $commit231 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e233) {
                            $commit231 = false;
                            continue $label230;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e233) {
                            $commit231 = false;
                            fabric.common.TransactionID $currentTid234 =
                              $tm235.getCurrentTid();
                            if ($e233.tid.isDescendantOf($currentTid234))
                                continue $label230;
                            if ($currentTid234.parent != null) {
                                $retry232 = false;
                                throw $e233;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e233) {
                            $commit231 = false;
                            if ($tm235.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid234 =
                              $tm235.getCurrentTid();
                            if ($e233.tid.isDescendantOf($currentTid234)) {
                                $retry232 = true;
                            }
                            else if ($currentTid234.parent != null) {
                                $retry232 = false;
                                throw $e233;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e233) {
                            $commit231 = false;
                            if ($tm235.checkForStaleObjects())
                                continue $label230;
                            $retry232 = false;
                            throw new fabric.worker.AbortException($e233);
                        }
                        finally {
                            if ($commit231) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e233) {
                                    $commit231 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e233) {
                                    $commit231 = false;
                                    fabric.common.TransactionID $currentTid234 =
                                      $tm235.getCurrentTid();
                                    if ($currentTid234 != null) {
                                        if ($e233.tid.equals($currentTid234) ||
                                              !$e233.tid.isDescendantOf(
                                                           $currentTid234)) {
                                            throw $e233;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit231 && $retry232) {
                                { val = val$var229; }
                                continue $label230;
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
    
    public static final byte[] $classHash = new byte[] { -80, -8, 19, 70, -18,
    -114, 61, 38, 46, 54, -103, 76, 18, 122, 38, 32, -115, -32, 20, 38, 63, 40,
    -13, -19, -80, 10, -127, -50, 4, -112, -16, -96 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1531161205000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
