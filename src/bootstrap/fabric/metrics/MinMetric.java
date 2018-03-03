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
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e153) {
                            $commit151 = false;
                            if ($tm155.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid154 =
                              $tm155.getCurrentTid();
                            if ($e153.tid.isDescendantOf($currentTid154)) {
                                $retry152 = true;
                            }
                            else if ($currentTid154.parent != null) {
                                $retry152 = false;
                                throw $e153;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
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
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e163) {
                            $commit161 = false;
                            if ($tm165.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid164 =
                              $tm165.getCurrentTid();
                            if ($e163.tid.isDescendantOf($currentTid164)) {
                                $retry162 = true;
                            }
                            else if ($currentTid164.parent != null) {
                                $retry162 = false;
                                throw $e163;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
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
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e174) {
                                $commit172 = false;
                                if ($tm176.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid175 =
                                  $tm176.getCurrentTid();
                                if ($e174.tid.isDescendantOf($currentTid175)) {
                                    $retry173 = true;
                                }
                                else if ($currentTid175.parent != null) {
                                    $retry173 = false;
                                    throw $e174;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
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
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e184) {
                            $commit182 = false;
                            if ($tm186.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid185 =
                              $tm186.getCurrentTid();
                            if ($e184.tid.isDescendantOf($currentTid185)) {
                                $retry183 = true;
                            }
                            else if ($currentTid185.parent != null) {
                                $retry183 = false;
                                throw $e184;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
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
            fabric.metrics.contracts.Contract[] witnesses =
              new fabric.metrics.contracts.Contract[this.get$terms().get$length(
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
    
    public static final byte[] $classHash = new byte[] { 55, -68, -96, 10, 80,
    79, -9, 22, 86, 43, -12, -18, 87, 122, 122, -58, -13, 3, 26, -117, 8, 50,
    -41, -30, -62, 114, -81, -31, -87, 13, -62, 119 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520116324000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
