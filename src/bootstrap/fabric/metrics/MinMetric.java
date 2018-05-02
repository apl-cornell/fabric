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
    
    public static final byte[] $classHash = new byte[] { 121, 3, -66, 115, 109,
    -17, -90, -26, -22, 73, 62, 65, -26, 124, 75, 70, -80, 24, 44, -92, -49,
    -72, 74, 125, 106, 3, 76, -89, -68, 9, -62, -9 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525209021000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
