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
    
    public static final byte[] $classHash = new byte[] { 101, -84, 115, 126, 99,
    -68, 95, 98, -47, -19, 110, 113, 63, -9, 107, 78, 99, 53, 26, -46, 48, 26,
    -64, -122, 61, 17, 70, -58, 84, 26, 56, -31 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524505527000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
