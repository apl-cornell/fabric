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
    
    public static final byte[] $classHash = new byte[] { 34, 116, 76, -103,
    -118, -116, -38, 101, -9, 16, -111, 40, 5, 19, -1, 98, 54, -79, 46, -8, -30,
    63, 54, -17, -44, -26, -122, -102, -58, 12, 88, -45 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1529351168000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
