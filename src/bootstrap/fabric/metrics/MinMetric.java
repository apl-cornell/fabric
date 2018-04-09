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
                    fabric.metrics.DerivedMetric val$var217 = val;
                    fabric.worker.transaction.TransactionManager $tm223 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled226 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff224 = 1;
                    boolean $doBackoff225 = true;
                    boolean $retry220 = true;
                    $label218: for (boolean $commit219 = false; !$commit219; ) {
                        if ($backoffEnabled226) {
                            if ($doBackoff225) {
                                if ($backoff224 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff224);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e221) {
                                            
                                        }
                                    }
                                }
                                if ($backoff224 < 5000) $backoff224 *= 2;
                            }
                            $doBackoff225 = $backoff224 <= 32 || !$doBackoff225;
                        }
                        $commit219 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e221) {
                            $commit219 = false;
                            continue $label218;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e221) {
                            $commit219 = false;
                            fabric.common.TransactionID $currentTid222 =
                              $tm223.getCurrentTid();
                            if ($e221.tid.isDescendantOf($currentTid222))
                                continue $label218;
                            if ($currentTid222.parent != null) {
                                $retry220 = false;
                                throw $e221;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e221) {
                            $commit219 = false;
                            if ($tm223.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid222 =
                              $tm223.getCurrentTid();
                            if ($e221.tid.isDescendantOf($currentTid222)) {
                                $retry220 = true;
                            }
                            else if ($currentTid222.parent != null) {
                                $retry220 = false;
                                throw $e221;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e221) {
                            $commit219 = false;
                            if ($tm223.checkForStaleObjects())
                                continue $label218;
                            $retry220 = false;
                            throw new fabric.worker.AbortException($e221);
                        }
                        finally {
                            if ($commit219) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e221) {
                                    $commit219 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e221) {
                                    $commit219 = false;
                                    fabric.common.TransactionID $currentTid222 =
                                      $tm223.getCurrentTid();
                                    if ($currentTid222 != null) {
                                        if ($e221.tid.equals($currentTid222) ||
                                              !$e221.tid.isDescendantOf(
                                                           $currentTid222)) {
                                            throw $e221;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit219 && $retry220) {
                                { val = val$var217; }
                                continue $label218;
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
                        fabric.metrics.DerivedMetric val$var227 = val;
                        int aggIdx$var228 = aggIdx;
                        fabric.worker.transaction.TransactionManager $tm234 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled237 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff235 = 1;
                        boolean $doBackoff236 = true;
                        boolean $retry231 = true;
                        $label229: for (boolean $commit230 = false; !$commit230;
                                        ) {
                            if ($backoffEnabled237) {
                                if ($doBackoff236) {
                                    if ($backoff235 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff235);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e232) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff235 < 5000) $backoff235 *= 2;
                                }
                                $doBackoff236 = $backoff235 <= 32 ||
                                                  !$doBackoff236;
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
                                        fabric.common.TransactionID
                                          $currentTid233 =
                                          $tm234.getCurrentTid();
                                        if ($currentTid233 != null) {
                                            if ($e232.tid.equals(
                                                            $currentTid233) ||
                                                  !$e232.tid.
                                                  isDescendantOf(
                                                    $currentTid233)) {
                                                throw $e232;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit230 && $retry231) {
                                    {
                                        val = val$var227;
                                        aggIdx = aggIdx$var228;
                                    }
                                    continue $label229;
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
                    fabric.metrics.DerivedMetric val$var238 = val;
                    fabric.worker.transaction.TransactionManager $tm244 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled247 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff245 = 1;
                    boolean $doBackoff246 = true;
                    boolean $retry241 = true;
                    $label239: for (boolean $commit240 = false; !$commit240; ) {
                        if ($backoffEnabled247) {
                            if ($doBackoff246) {
                                if ($backoff245 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff245);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e242) {
                                            
                                        }
                                    }
                                }
                                if ($backoff245 < 5000) $backoff245 *= 2;
                            }
                            $doBackoff246 = $backoff245 <= 32 || !$doBackoff246;
                        }
                        $commit240 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e242) {
                            $commit240 = false;
                            continue $label239;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e242) {
                            $commit240 = false;
                            fabric.common.TransactionID $currentTid243 =
                              $tm244.getCurrentTid();
                            if ($e242.tid.isDescendantOf($currentTid243))
                                continue $label239;
                            if ($currentTid243.parent != null) {
                                $retry241 = false;
                                throw $e242;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e242) {
                            $commit240 = false;
                            if ($tm244.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid243 =
                              $tm244.getCurrentTid();
                            if ($e242.tid.isDescendantOf($currentTid243)) {
                                $retry241 = true;
                            }
                            else if ($currentTid243.parent != null) {
                                $retry241 = false;
                                throw $e242;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e242) {
                            $commit240 = false;
                            if ($tm244.checkForStaleObjects())
                                continue $label239;
                            $retry241 = false;
                            throw new fabric.worker.AbortException($e242);
                        }
                        finally {
                            if ($commit240) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e242) {
                                    $commit240 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e242) {
                                    $commit240 = false;
                                    fabric.common.TransactionID $currentTid243 =
                                      $tm244.getCurrentTid();
                                    if ($currentTid243 != null) {
                                        if ($e242.tid.equals($currentTid243) ||
                                              !$e242.tid.isDescendantOf(
                                                           $currentTid243)) {
                                            throw $e242;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit240 && $retry241) {
                                { val = val$var238; }
                                continue $label239;
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
    
    public static final byte[] $classHash = new byte[] { 51, -36, -88, -49, 111,
    111, 82, -120, 109, -63, 108, 22, 123, -125, -22, -34, -114, 18, 69, -84,
    -26, 77, -111, 72, -90, 97, 90, -31, 67, -74, 13, -96 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1522607901000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
