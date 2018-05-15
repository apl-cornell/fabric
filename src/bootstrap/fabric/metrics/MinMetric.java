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
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;
import fabric.worker.metrics.ImmutableMetricsVector;
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
    
    public double computeValue(boolean useWeakCache);
    
    public double computeVelocity(boolean useWeakCache);
    
    public double computeNoise(boolean useWeakCache);
    
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
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      thresholdPolicy(double rate, double base, boolean useWeakCache,
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
        
        public double computeValue(boolean useWeakCache) {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result = java.lang.Math.min(result,
                                            term(i).value(useWeakCache));
            }
            return result;
        }
        
        public double computeVelocity(boolean useWeakCache) {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result = java.lang.Math.min(result,
                                            term(i).velocity(useWeakCache));
            }
            return result;
        }
        
        public double computeNoise(boolean useWeakCache) {
            double noise = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                noise = java.lang.Math.max(noise, term(i).noise(useWeakCache));
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
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
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
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
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
                return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
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
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base, boolean useWeakCache,
                          final fabric.worker.Store s) {
            fabric.metrics.contracts.Contract[] witnesses =
              new fabric.metrics.contracts.Contract[this.get$terms().length()];
            for (int i = 0; i < this.get$terms().length(); i++) {
                witnesses[i] = term(i).getThresholdContract(rate, base);
            }
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(s).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(witnesses);
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
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
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
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { -89, 77, -94, -30, -31,
    126, 22, -77, -87, -109, -119, -45, -58, 122, -48, 80, -31, 107, 104, 99,
    77, -51, 5, -39, -103, 125, 31, -76, -112, -100, -60, 111 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526344812000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
