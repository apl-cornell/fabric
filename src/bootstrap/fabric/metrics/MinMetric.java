package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
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
            this.set$terms(
                   (fabric.lang.arrays.ObjectArray)
                     new fabric.lang.arrays.ObjectArray._Impl(
                       this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                           this.get$$updateLabel(),
                                           this.get$$updateLabel().confPolicy(),
                                           fabric.metrics.Metric._Proxy.class,
                                           termsBag.size()).$getProxy());
            int tIdx = 0;
            for (java.util.Iterator it = termsBag.iterator(); it.hasNext(); ) {
                this.
                  get$terms().
                  set(
                    tIdx++,
                    (fabric.metrics.Metric)
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        fabric.lang.WrappedJavaInlineable.$wrap(it.next())));
            }
            initialize();
            return (fabric.metrics.MinMetric) this.$getProxy();
        }
        
        public double computePresetR() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.max(result,
                                     ((fabric.metrics.Metric)
                                        this.get$terms().get(i)).getPresetR());
            }
            return result;
        }
        
        public double computePresetB() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.max(result,
                                     ((fabric.metrics.Metric)
                                        this.get$terms().get(i)).getPresetB());
            }
            return result;
        }
        
        public double computePresetV() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.min(result,
                                     ((fabric.metrics.Metric)
                                        this.get$terms().get(i)).getPresetV());
            }
            return result;
        }
        
        public double computePresetN() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.max(result,
                                     ((fabric.metrics.Metric)
                                        this.get$terms().get(i)).getPresetN());
            }
            return result;
        }
        
        public double computeValue(boolean useWeakCache) {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.min(
                                   result,
                                   ((fabric.metrics.Metric)
                                      this.get$terms().get(i)).value(
                                                                 useWeakCache));
            }
            return result;
        }
        
        public double computeVelocity(boolean useWeakCache) {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.min(
                                   result,
                                   ((fabric.metrics.Metric)
                                      this.get$terms().get(i)).velocity(
                                                                 useWeakCache));
            }
            return result;
        }
        
        public double computeNoise(boolean useWeakCache) {
            double noise = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                noise =
                  java.lang.Math.max(
                                   noise,
                                   ((fabric.metrics.Metric)
                                      this.get$terms().get(i)).noise(
                                                                 useWeakCache));
            }
            return noise;
        }
        
        public java.lang.String toString() {
            java.lang.String str = "min(";
            boolean nonEmpty = false;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                if (nonEmpty) { str += ", "; }
                nonEmpty = true;
                str += (fabric.metrics.Metric) this.get$terms().get(i);
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
              new fabric.metrics.Metric[tmp.get$terms().get$length()];
            for (int i = 0; i < tmp.get$terms().get$length(); i++) {
                newTerms[i] = ((fabric.metrics.Metric)
                                 tmp.get$terms().get(i)).times(scalar);
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
              new fabric.metrics.Metric[tmp.get$terms().get$length()];
            for (int i = 0; i < newTerms.length; i++) {
                newTerms[i] = other.plus((fabric.metrics.Metric)
                                           tmp.get$terms().get(i));
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
                for (int i = 0; i < tmp.get$terms().get$length(); i++) {
                    termsBag.
                      add(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.Metric)
                                    tmp.get$terms().get(i)));
                }
                for (int i = 0; i < that.get$terms().get$length(); i++) {
                    termsBag.
                      add(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.Metric)
                                    that.get$terms().get(i)));
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
            else if (fabric.util.Arrays._Impl.asList(tmp.get$terms()).
                       indexOf(other) >= 0) {
                return tmp;
            }
            fabric.metrics.Metric[] newTerms =
              new fabric.metrics.Metric[tmp.get$terms().get$length() + 1];
            for (int i = 0; i < tmp.get$terms().get$length(); i++) {
                newTerms[i] = (fabric.metrics.Metric) tmp.get$terms().get(i);
            }
            newTerms[tmp.get$terms().get$length()] = other;
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
              new fabric.metrics.contracts.Contract[this.get$terms().get$length(
                                                                       )];
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                witnesses[i] = term(i).getThresholdContract(rate, base);
            }
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(s).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(witnesses);
        }
        
        public int hashCode() {
            return fabric.util.Arrays._Impl.hashCode(this.get$terms()) * 32 +
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
                return fabric.util.Arrays._Impl.deepEquals(this.get$terms(),
                                                           that.get$terms()) &&
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
    
    public static final byte[] $classHash = new byte[] { 47, -38, -37, 76, -37,
    78, -31, 107, 60, -75, 42, -72, 69, 48, -65, -54, 100, 52, 105, 39, -31, -9,
    30, 57, 69, -31, 41, -20, -117, 71, -36, -3 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524081841000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
