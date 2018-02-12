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
import fabric.metrics.contracts.MetricContract;
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
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
      double rate, double base, boolean useWeakCache,
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
                    fabric.metrics.DerivedMetric val$var149 = val;
                    fabric.worker.transaction.TransactionManager $tm155 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled158 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff156 = 1;
                    boolean $doBackoff157 = true;
                    boolean $retry152 = true;
                    $label150: for (boolean $commit151 = false; !$commit151; ) {
                        if ($backoffEnabled158) {
                            if ($doBackoff157) {
                                if ($backoff156 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff156);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e153) {
                                            
                                        }
                                    }
                                }
                                if ($backoff156 < 5000) $backoff156 *= 2;
                            }
                            $doBackoff157 = $backoff156 <= 32 || !$doBackoff157;
                        }
                        $commit151 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e153) {
                            $commit151 = false;
                            continue $label150;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e153) {
                            $commit151 = false;
                            fabric.common.TransactionID $currentTid154 =
                              $tm155.getCurrentTid();
                            if ($e153.tid.isDescendantOf($currentTid154))
                                continue $label150;
                            if ($currentTid154.parent != null) {
                                $retry152 = false;
                                throw $e153;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e153) {
                            $commit151 = false;
                            if ($tm155.checkForStaleObjects())
                                continue $label150;
                            $retry152 = false;
                            throw new fabric.worker.AbortException($e153);
                        }
                        finally {
                            if ($commit151) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e153) {
                                    $commit151 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e153) {
                                    $commit151 = false;
                                    fabric.common.TransactionID $currentTid154 =
                                      $tm155.getCurrentTid();
                                    if ($currentTid154 != null) {
                                        if ($e153.tid.equals($currentTid154) ||
                                              !$e153.tid.isDescendantOf(
                                                           $currentTid154)) {
                                            throw $e153;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit151 && $retry152) {
                                { val = val$var149; }
                                continue $label150;
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
                    fabric.metrics.DerivedMetric val$var159 = val;
                    fabric.worker.transaction.TransactionManager $tm165 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled168 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff166 = 1;
                    boolean $doBackoff167 = true;
                    boolean $retry162 = true;
                    $label160: for (boolean $commit161 = false; !$commit161; ) {
                        if ($backoffEnabled168) {
                            if ($doBackoff167) {
                                if ($backoff166 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff166);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e163) {
                                            
                                        }
                                    }
                                }
                                if ($backoff166 < 5000) $backoff166 *= 2;
                            }
                            $doBackoff167 = $backoff166 <= 32 || !$doBackoff167;
                        }
                        $commit161 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e163) {
                            $commit161 = false;
                            continue $label160;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e163) {
                            $commit161 = false;
                            fabric.common.TransactionID $currentTid164 =
                              $tm165.getCurrentTid();
                            if ($e163.tid.isDescendantOf($currentTid164))
                                continue $label160;
                            if ($currentTid164.parent != null) {
                                $retry162 = false;
                                throw $e163;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e163) {
                            $commit161 = false;
                            if ($tm165.checkForStaleObjects())
                                continue $label160;
                            $retry162 = false;
                            throw new fabric.worker.AbortException($e163);
                        }
                        finally {
                            if ($commit161) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e163) {
                                    $commit161 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e163) {
                                    $commit161 = false;
                                    fabric.common.TransactionID $currentTid164 =
                                      $tm165.getCurrentTid();
                                    if ($currentTid164 != null) {
                                        if ($e163.tid.equals($currentTid164) ||
                                              !$e163.tid.isDescendantOf(
                                                           $currentTid164)) {
                                            throw $e163;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit161 && $retry162) {
                                { val = val$var159; }
                                continue $label160;
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
                        fabric.metrics.DerivedMetric val$var169 = val;
                        int aggIdx$var170 = aggIdx;
                        fabric.worker.transaction.TransactionManager $tm176 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled179 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff177 = 1;
                        boolean $doBackoff178 = true;
                        boolean $retry173 = true;
                        $label171: for (boolean $commit172 = false; !$commit172;
                                        ) {
                            if ($backoffEnabled179) {
                                if ($doBackoff178) {
                                    if ($backoff177 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff177);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e174) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff177 < 5000) $backoff177 *= 2;
                                }
                                $doBackoff178 = $backoff177 <= 32 ||
                                                  !$doBackoff178;
                            }
                            $commit172 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    newTerms);
                            }
                            catch (final fabric.worker.RetryException $e174) {
                                $commit172 = false;
                                continue $label171;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e174) {
                                $commit172 = false;
                                fabric.common.TransactionID $currentTid175 =
                                  $tm176.getCurrentTid();
                                if ($e174.tid.isDescendantOf($currentTid175))
                                    continue $label171;
                                if ($currentTid175.parent != null) {
                                    $retry173 = false;
                                    throw $e174;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e174) {
                                $commit172 = false;
                                if ($tm176.checkForStaleObjects())
                                    continue $label171;
                                $retry173 = false;
                                throw new fabric.worker.AbortException($e174);
                            }
                            finally {
                                if ($commit172) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e174) {
                                        $commit172 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e174) {
                                        $commit172 = false;
                                        fabric.common.TransactionID
                                          $currentTid175 =
                                          $tm176.getCurrentTid();
                                        if ($currentTid175 != null) {
                                            if ($e174.tid.equals(
                                                            $currentTid175) ||
                                                  !$e174.tid.
                                                  isDescendantOf(
                                                    $currentTid175)) {
                                                throw $e174;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit172 && $retry173) {
                                    {
                                        val = val$var169;
                                        aggIdx = aggIdx$var170;
                                    }
                                    continue $label171;
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
                    fabric.metrics.DerivedMetric val$var180 = val;
                    fabric.worker.transaction.TransactionManager $tm186 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled189 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff187 = 1;
                    boolean $doBackoff188 = true;
                    boolean $retry183 = true;
                    $label181: for (boolean $commit182 = false; !$commit182; ) {
                        if ($backoffEnabled189) {
                            if ($doBackoff188) {
                                if ($backoff187 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff187);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e184) {
                                            
                                        }
                                    }
                                }
                                if ($backoff187 < 5000) $backoff187 *= 2;
                            }
                            $doBackoff188 = $backoff187 <= 32 || !$doBackoff188;
                        }
                        $commit182 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e184) {
                            $commit182 = false;
                            continue $label181;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e184) {
                            $commit182 = false;
                            fabric.common.TransactionID $currentTid185 =
                              $tm186.getCurrentTid();
                            if ($e184.tid.isDescendantOf($currentTid185))
                                continue $label181;
                            if ($currentTid185.parent != null) {
                                $retry183 = false;
                                throw $e184;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e184) {
                            $commit182 = false;
                            if ($tm186.checkForStaleObjects())
                                continue $label181;
                            $retry183 = false;
                            throw new fabric.worker.AbortException($e184);
                        }
                        finally {
                            if ($commit182) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e184) {
                                    $commit182 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e184) {
                                    $commit182 = false;
                                    fabric.common.TransactionID $currentTid185 =
                                      $tm186.getCurrentTid();
                                    if ($currentTid185 != null) {
                                        if ($e184.tid.equals($currentTid185) ||
                                              !$e184.tid.isDescendantOf(
                                                           $currentTid185)) {
                                            throw $e184;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit182 && $retry183) {
                                { val = val$var180; }
                                continue $label181;
                            }
                        }
                    }
                }
            }
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          double rate, double base, boolean useWeakCache,
          final fabric.worker.Store s) {
            fabric.metrics.contracts.MetricContract[] witnesses =
              new fabric.metrics.contracts.MetricContract[this.get$terms(
                                                                 ).get$length(
                                                                     )];
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                witnesses[i] = term(i).getContract(rate, base);
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
    
    public static final byte[] $classHash = new byte[] { 73, 20, -99, -30, 3,
    72, 108, -76, -110, 58, -17, -27, -5, 14, -69, -127, 26, 27, -99, -107,
    -119, -116, -38, -69, -22, -108, 76, -75, -55, -87, -15, -83 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518448064000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
