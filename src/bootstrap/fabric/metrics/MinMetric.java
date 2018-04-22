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
                    fabric.metrics.DerivedMetric val$var225 = val;
                    fabric.worker.transaction.TransactionManager $tm231 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled234 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff232 = 1;
                    boolean $doBackoff233 = true;
                    boolean $retry228 = true;
                    $label226: for (boolean $commit227 = false; !$commit227; ) {
                        if ($backoffEnabled234) {
                            if ($doBackoff233) {
                                if ($backoff232 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff232);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e229) {
                                            
                                        }
                                    }
                                }
                                if ($backoff232 < 5000) $backoff232 *= 2;
                            }
                            $doBackoff233 = $backoff232 <= 32 || !$doBackoff233;
                        }
                        $commit227 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e229) {
                            $commit227 = false;
                            continue $label226;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e229) {
                            $commit227 = false;
                            fabric.common.TransactionID $currentTid230 =
                              $tm231.getCurrentTid();
                            if ($e229.tid.isDescendantOf($currentTid230))
                                continue $label226;
                            if ($currentTid230.parent != null) {
                                $retry228 = false;
                                throw $e229;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e229) {
                            $commit227 = false;
                            if ($tm231.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid230 =
                              $tm231.getCurrentTid();
                            if ($e229.tid.isDescendantOf($currentTid230)) {
                                $retry228 = true;
                            }
                            else if ($currentTid230.parent != null) {
                                $retry228 = false;
                                throw $e229;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e229) {
                            $commit227 = false;
                            if ($tm231.checkForStaleObjects())
                                continue $label226;
                            $retry228 = false;
                            throw new fabric.worker.AbortException($e229);
                        }
                        finally {
                            if ($commit227) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e229) {
                                    $commit227 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e229) {
                                    $commit227 = false;
                                    fabric.common.TransactionID $currentTid230 =
                                      $tm231.getCurrentTid();
                                    if ($currentTid230 != null) {
                                        if ($e229.tid.equals($currentTid230) ||
                                              !$e229.tid.isDescendantOf(
                                                           $currentTid230)) {
                                            throw $e229;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit227 && $retry228) {
                                { val = val$var225; }
                                continue $label226;
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
                    fabric.metrics.DerivedMetric val$var235 = val;
                    fabric.worker.transaction.TransactionManager $tm241 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled244 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff242 = 1;
                    boolean $doBackoff243 = true;
                    boolean $retry238 = true;
                    $label236: for (boolean $commit237 = false; !$commit237; ) {
                        if ($backoffEnabled244) {
                            if ($doBackoff243) {
                                if ($backoff242 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff242);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e239) {
                                            
                                        }
                                    }
                                }
                                if ($backoff242 < 5000) $backoff242 *= 2;
                            }
                            $doBackoff243 = $backoff242 <= 32 || !$doBackoff243;
                        }
                        $commit237 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e239) {
                            $commit237 = false;
                            continue $label236;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e239) {
                            $commit237 = false;
                            fabric.common.TransactionID $currentTid240 =
                              $tm241.getCurrentTid();
                            if ($e239.tid.isDescendantOf($currentTid240))
                                continue $label236;
                            if ($currentTid240.parent != null) {
                                $retry238 = false;
                                throw $e239;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e239) {
                            $commit237 = false;
                            if ($tm241.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid240 =
                              $tm241.getCurrentTid();
                            if ($e239.tid.isDescendantOf($currentTid240)) {
                                $retry238 = true;
                            }
                            else if ($currentTid240.parent != null) {
                                $retry238 = false;
                                throw $e239;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e239) {
                            $commit237 = false;
                            if ($tm241.checkForStaleObjects())
                                continue $label236;
                            $retry238 = false;
                            throw new fabric.worker.AbortException($e239);
                        }
                        finally {
                            if ($commit237) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e239) {
                                    $commit237 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e239) {
                                    $commit237 = false;
                                    fabric.common.TransactionID $currentTid240 =
                                      $tm241.getCurrentTid();
                                    if ($currentTid240 != null) {
                                        if ($e239.tid.equals($currentTid240) ||
                                              !$e239.tid.isDescendantOf(
                                                           $currentTid240)) {
                                            throw $e239;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit237 && $retry238) {
                                { val = val$var235; }
                                continue $label236;
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
                        fabric.metrics.DerivedMetric val$var245 = val;
                        int aggIdx$var246 = aggIdx;
                        fabric.worker.transaction.TransactionManager $tm252 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled255 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff253 = 1;
                        boolean $doBackoff254 = true;
                        boolean $retry249 = true;
                        $label247: for (boolean $commit248 = false; !$commit248;
                                        ) {
                            if ($backoffEnabled255) {
                                if ($doBackoff254) {
                                    if ($backoff253 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff253);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e250) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff253 < 5000) $backoff253 *= 2;
                                }
                                $doBackoff254 = $backoff253 <= 32 ||
                                                  !$doBackoff254;
                            }
                            $commit248 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    newTerms);
                            }
                            catch (final fabric.worker.RetryException $e250) {
                                $commit248 = false;
                                continue $label247;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e250) {
                                $commit248 = false;
                                fabric.common.TransactionID $currentTid251 =
                                  $tm252.getCurrentTid();
                                if ($e250.tid.isDescendantOf($currentTid251))
                                    continue $label247;
                                if ($currentTid251.parent != null) {
                                    $retry249 = false;
                                    throw $e250;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e250) {
                                $commit248 = false;
                                if ($tm252.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid251 =
                                  $tm252.getCurrentTid();
                                if ($e250.tid.isDescendantOf($currentTid251)) {
                                    $retry249 = true;
                                }
                                else if ($currentTid251.parent != null) {
                                    $retry249 = false;
                                    throw $e250;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e250) {
                                $commit248 = false;
                                if ($tm252.checkForStaleObjects())
                                    continue $label247;
                                $retry249 = false;
                                throw new fabric.worker.AbortException($e250);
                            }
                            finally {
                                if ($commit248) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e250) {
                                        $commit248 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e250) {
                                        $commit248 = false;
                                        fabric.common.TransactionID
                                          $currentTid251 =
                                          $tm252.getCurrentTid();
                                        if ($currentTid251 != null) {
                                            if ($e250.tid.equals(
                                                            $currentTid251) ||
                                                  !$e250.tid.
                                                  isDescendantOf(
                                                    $currentTid251)) {
                                                throw $e250;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit248 && $retry249) {
                                    {
                                        val = val$var245;
                                        aggIdx = aggIdx$var246;
                                    }
                                    continue $label247;
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
                    fabric.metrics.DerivedMetric val$var256 = val;
                    fabric.worker.transaction.TransactionManager $tm262 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled265 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff263 = 1;
                    boolean $doBackoff264 = true;
                    boolean $retry259 = true;
                    $label257: for (boolean $commit258 = false; !$commit258; ) {
                        if ($backoffEnabled265) {
                            if ($doBackoff264) {
                                if ($backoff263 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff263);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e260) {
                                            
                                        }
                                    }
                                }
                                if ($backoff263 < 5000) $backoff263 *= 2;
                            }
                            $doBackoff264 = $backoff263 <= 32 || !$doBackoff264;
                        }
                        $commit258 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e260) {
                            $commit258 = false;
                            continue $label257;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e260) {
                            $commit258 = false;
                            fabric.common.TransactionID $currentTid261 =
                              $tm262.getCurrentTid();
                            if ($e260.tid.isDescendantOf($currentTid261))
                                continue $label257;
                            if ($currentTid261.parent != null) {
                                $retry259 = false;
                                throw $e260;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e260) {
                            $commit258 = false;
                            if ($tm262.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid261 =
                              $tm262.getCurrentTid();
                            if ($e260.tid.isDescendantOf($currentTid261)) {
                                $retry259 = true;
                            }
                            else if ($currentTid261.parent != null) {
                                $retry259 = false;
                                throw $e260;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e260) {
                            $commit258 = false;
                            if ($tm262.checkForStaleObjects())
                                continue $label257;
                            $retry259 = false;
                            throw new fabric.worker.AbortException($e260);
                        }
                        finally {
                            if ($commit258) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e260) {
                                    $commit258 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e260) {
                                    $commit258 = false;
                                    fabric.common.TransactionID $currentTid261 =
                                      $tm262.getCurrentTid();
                                    if ($currentTid261 != null) {
                                        if ($e260.tid.equals($currentTid261) ||
                                              !$e260.tid.isDescendantOf(
                                                           $currentTid261)) {
                                            throw $e260;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit258 && $retry259) {
                                { val = val$var256; }
                                continue $label257;
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
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.MinMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -87, -24, -45, 35, -15,
    119, -52, -103, 110, 116, 116, -71, 117, 95, -90, 36, -4, -112, -46, -79,
    -42, -11, 61, -105, 27, 76, 83, -8, -33, 14, -101, -22 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524349499000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
