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
      fabric.lang.arrays.ObjectArray terms);
    
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
      fabric.metrics.contracts.Bound bound, boolean useWeakCache);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy extends fabric.metrics.DerivedMetric._Proxy
      implements fabric.metrics.MinMetric {
        public fabric.metrics.MinMetric fabric$metrics$MinMetric$(
          fabric.lang.arrays.ObjectArray arg1) {
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
          fabric.lang.arrays.ObjectArray terms) {
            fabric$metrics$DerivedMetric$(terms);
            fabric.util.Set termsBag =
              ((fabric.util.TreeSet)
                 new fabric.util.TreeSet._Impl(this.$getStore()).$getProxy()).
              fabric$util$TreeSet$(fabric.util.Arrays._Impl.asList(terms));
            this.
              set$terms(
                (fabric.lang.arrays.ObjectArray)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    termsBag.
                        toArray(
                          (fabric.lang.arrays.ObjectArray)
                            new fabric.lang.arrays.ObjectArray._Impl(
                              this.$getStore()).
                            fabric$lang$arrays$ObjectArray$(
                              this.get$$updateLabel(),
                              this.get$$updateLabel().confPolicy(),
                              fabric.metrics.Metric._Proxy.class,
                              termsBag.size()).$getProxy())));
            fabric.util.Arrays._Impl.sort(this.get$terms());
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
            fabric.lang.arrays.ObjectArray newTerms = null;
            {
                fabric.lang.arrays.ObjectArray newTerms$var130 = newTerms;
                fabric.worker.transaction.TransactionManager $tm135 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled138 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff136 = 1;
                boolean $doBackoff137 = true;
                $label131: for (boolean $commit132 = false; !$commit132; ) {
                    if ($backoffEnabled138) {
                        if ($doBackoff137) {
                            if ($backoff136 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff136);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e133) {
                                        
                                    }
                                }
                            }
                            if ($backoff136 < 5000) $backoff136 *= 2;
                        }
                        $doBackoff137 = $backoff136 <= 32 || !$doBackoff137;
                    }
                    $commit132 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        newTerms =
                          (fabric.lang.arrays.ObjectArray)
                            new fabric.lang.arrays.ObjectArray._Impl(
                              this.$getStore()).
                            fabric$lang$arrays$ObjectArray$(
                              this.get$$updateLabel(),
                              this.get$$updateLabel().confPolicy(),
                              fabric.metrics.Metric._Proxy.class,
                              this.get$terms().get$length()).$getProxy();
                    }
                    catch (final fabric.worker.RetryException $e133) {
                        $commit132 = false;
                        continue $label131;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e133) {
                        $commit132 = false;
                        fabric.common.TransactionID $currentTid134 =
                          $tm135.getCurrentTid();
                        if ($e133.tid.isDescendantOf($currentTid134))
                            continue $label131;
                        if ($currentTid134.parent != null) throw $e133;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e133) {
                        $commit132 = false;
                        if ($tm135.checkForStaleObjects()) continue $label131;
                        throw new fabric.worker.AbortException($e133);
                    }
                    finally {
                        if ($commit132) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e133) {
                                $commit132 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e133) {
                                $commit132 = false;
                                fabric.common.TransactionID $currentTid134 =
                                  $tm135.getCurrentTid();
                                if ($currentTid134 != null) {
                                    if ($e133.tid.equals($currentTid134) ||
                                          !$e133.tid.isDescendantOf(
                                                       $currentTid134)) {
                                        throw $e133;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit132) {
                            { newTerms = newTerms$var130; }
                            continue $label131;
                        }
                    }
                }
            }
            fabric.util.Arrays._Impl.arraycopy(this.get$terms(), 0, newTerms, 0,
                                               this.get$terms().get$length());
            for (int i = 0; i < newTerms.get$length(); i++) {
                newTerms.set(i,
                             ((fabric.metrics.Metric)
                                newTerms.get(i)).times(scalar));
            }
            final fabric.worker.Store s = $getStore();
            fabric.metrics.DerivedMetric val = null;
            {
                fabric.metrics.DerivedMetric val$var139 = val;
                fabric.lang.arrays.ObjectArray newTerms$var140 = newTerms;
                fabric.worker.transaction.TransactionManager $tm145 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled148 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff146 = 1;
                boolean $doBackoff147 = true;
                $label141: for (boolean $commit142 = false; !$commit142; ) {
                    if ($backoffEnabled148) {
                        if ($doBackoff147) {
                            if ($backoff146 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff146);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e143) {
                                        
                                    }
                                }
                            }
                            if ($backoff146 < 5000) $backoff146 *= 2;
                        }
                        $doBackoff147 = $backoff146 <= 32 || !$doBackoff147;
                    }
                    $commit142 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.MinMetric)
                             new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                            fabric$metrics$MinMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e143) {
                        $commit142 = false;
                        continue $label141;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e143) {
                        $commit142 = false;
                        fabric.common.TransactionID $currentTid144 =
                          $tm145.getCurrentTid();
                        if ($e143.tid.isDescendantOf($currentTid144))
                            continue $label141;
                        if ($currentTid144.parent != null) throw $e143;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e143) {
                        $commit142 = false;
                        if ($tm145.checkForStaleObjects()) continue $label141;
                        throw new fabric.worker.AbortException($e143);
                    }
                    finally {
                        if ($commit142) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e143) {
                                $commit142 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e143) {
                                $commit142 = false;
                                fabric.common.TransactionID $currentTid144 =
                                  $tm145.getCurrentTid();
                                if ($currentTid144 != null) {
                                    if ($e143.tid.equals($currentTid144) ||
                                          !$e143.tid.isDescendantOf(
                                                       $currentTid144)) {
                                        throw $e143;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit142) {
                            {
                                val = val$var139;
                                newTerms = newTerms$var140;
                            }
                            continue $label141;
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
            fabric.lang.arrays.ObjectArray newTerms = null;
            {
                fabric.lang.arrays.ObjectArray newTerms$var149 = newTerms;
                fabric.worker.transaction.TransactionManager $tm154 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled157 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff155 = 1;
                boolean $doBackoff156 = true;
                $label150: for (boolean $commit151 = false; !$commit151; ) {
                    if ($backoffEnabled157) {
                        if ($doBackoff156) {
                            if ($backoff155 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff155);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e152) {
                                        
                                    }
                                }
                            }
                            if ($backoff155 < 5000) $backoff155 *= 2;
                        }
                        $doBackoff156 = $backoff155 <= 32 || !$doBackoff156;
                    }
                    $commit151 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        newTerms =
                          (fabric.lang.arrays.ObjectArray)
                            new fabric.lang.arrays.ObjectArray._Impl(
                              this.$getStore()).
                            fabric$lang$arrays$ObjectArray$(
                              this.get$$updateLabel(),
                              this.get$$updateLabel().confPolicy(),
                              fabric.metrics.Metric._Proxy.class,
                              this.get$terms().get$length()).$getProxy();
                    }
                    catch (final fabric.worker.RetryException $e152) {
                        $commit151 = false;
                        continue $label150;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e152) {
                        $commit151 = false;
                        fabric.common.TransactionID $currentTid153 =
                          $tm154.getCurrentTid();
                        if ($e152.tid.isDescendantOf($currentTid153))
                            continue $label150;
                        if ($currentTid153.parent != null) throw $e152;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e152) {
                        $commit151 = false;
                        if ($tm154.checkForStaleObjects()) continue $label150;
                        throw new fabric.worker.AbortException($e152);
                    }
                    finally {
                        if ($commit151) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e152) {
                                $commit151 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e152) {
                                $commit151 = false;
                                fabric.common.TransactionID $currentTid153 =
                                  $tm154.getCurrentTid();
                                if ($currentTid153 != null) {
                                    if ($e152.tid.equals($currentTid153) ||
                                          !$e152.tid.isDescendantOf(
                                                       $currentTid153)) {
                                        throw $e152;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit151) {
                            { newTerms = newTerms$var149; }
                            continue $label150;
                        }
                    }
                }
            }
            fabric.util.Arrays._Impl.arraycopy(this.get$terms(), 0, newTerms, 0,
                                               this.get$terms().get$length());
            for (int i = 0; i < newTerms.get$length(); i++) {
                newTerms.set(i,
                             other.plus((fabric.metrics.Metric)
                                          newTerms.get(i)));
            }
            final fabric.worker.Store s = $getStore();
            fabric.metrics.DerivedMetric val = null;
            {
                fabric.metrics.DerivedMetric val$var158 = val;
                fabric.lang.arrays.ObjectArray newTerms$var159 = newTerms;
                fabric.worker.transaction.TransactionManager $tm164 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled167 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff165 = 1;
                boolean $doBackoff166 = true;
                $label160: for (boolean $commit161 = false; !$commit161; ) {
                    if ($backoffEnabled167) {
                        if ($doBackoff166) {
                            if ($backoff165 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff165);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e162) {
                                        
                                    }
                                }
                            }
                            if ($backoff165 < 5000) $backoff165 *= 2;
                        }
                        $doBackoff166 = $backoff165 <= 32 || !$doBackoff166;
                    }
                    $commit161 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.MinMetric)
                             new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                            fabric$metrics$MinMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e162) {
                        $commit161 = false;
                        continue $label160;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e162) {
                        $commit161 = false;
                        fabric.common.TransactionID $currentTid163 =
                          $tm164.getCurrentTid();
                        if ($e162.tid.isDescendantOf($currentTid163))
                            continue $label160;
                        if ($currentTid163.parent != null) throw $e162;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e162) {
                        $commit161 = false;
                        if ($tm164.checkForStaleObjects()) continue $label160;
                        throw new fabric.worker.AbortException($e162);
                    }
                    finally {
                        if ($commit161) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e162) {
                                $commit161 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e162) {
                                $commit161 = false;
                                fabric.common.TransactionID $currentTid163 =
                                  $tm164.getCurrentTid();
                                if ($currentTid163 != null) {
                                    if ($e162.tid.equals($currentTid163) ||
                                          !$e162.tid.isDescendantOf(
                                                       $currentTid163)) {
                                        throw $e162;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit161) {
                            {
                                val = val$var158;
                                newTerms = newTerms$var159;
                            }
                            continue $label160;
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
            final fabric.worker.Store s = $getStore();
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
                fabric.util.Set termsBag = null;
                {
                    fabric.util.Set termsBag$var168 = termsBag;
                    fabric.worker.transaction.TransactionManager $tm173 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled176 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff174 = 1;
                    boolean $doBackoff175 = true;
                    $label169: for (boolean $commit170 = false; !$commit170; ) {
                        if ($backoffEnabled176) {
                            if ($doBackoff175) {
                                if ($backoff174 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff174);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e171) {
                                            
                                        }
                                    }
                                }
                                if ($backoff174 < 5000) $backoff174 *= 2;
                            }
                            $doBackoff175 = $backoff174 <= 32 || !$doBackoff175;
                        }
                        $commit170 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            termsBag =
                              (fabric.util.TreeSet)
                                new fabric.util.TreeSet._Impl(this.$getStore()).
                                $getProxy();
                        }
                        catch (final fabric.worker.RetryException $e171) {
                            $commit170 = false;
                            continue $label169;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e171) {
                            $commit170 = false;
                            fabric.common.TransactionID $currentTid172 =
                              $tm173.getCurrentTid();
                            if ($e171.tid.isDescendantOf($currentTid172))
                                continue $label169;
                            if ($currentTid172.parent != null) throw $e171;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e171) {
                            $commit170 = false;
                            if ($tm173.checkForStaleObjects())
                                continue $label169;
                            throw new fabric.worker.AbortException($e171);
                        }
                        finally {
                            if ($commit170) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e171) {
                                    $commit170 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e171) {
                                    $commit170 = false;
                                    fabric.common.TransactionID $currentTid172 =
                                      $tm173.getCurrentTid();
                                    if ($currentTid172 != null) {
                                        if ($e171.tid.equals($currentTid172) ||
                                              !$e171.tid.isDescendantOf(
                                                           $currentTid172)) {
                                            throw $e171;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit170) {
                                { termsBag = termsBag$var168; }
                                continue $label169;
                            }
                        }
                    }
                }
                termsBag.addAll(
                           fabric.util.Arrays._Impl.asList(this.get$terms()));
                termsBag.addAll(
                           fabric.util.Arrays._Impl.asList(that.get$terms()));
                fabric.lang.arrays.ObjectArray newTerms = null;
                {
                    fabric.lang.arrays.ObjectArray newTerms$var177 = newTerms;
                    fabric.util.Set termsBag$var178 = termsBag;
                    fabric.worker.transaction.TransactionManager $tm183 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled186 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff184 = 1;
                    boolean $doBackoff185 = true;
                    $label179: for (boolean $commit180 = false; !$commit180; ) {
                        if ($backoffEnabled186) {
                            if ($doBackoff185) {
                                if ($backoff184 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff184);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e181) {
                                            
                                        }
                                    }
                                }
                                if ($backoff184 < 5000) $backoff184 *= 2;
                            }
                            $doBackoff185 = $backoff184 <= 32 || !$doBackoff185;
                        }
                        $commit180 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            newTerms =
                              (fabric.lang.arrays.ObjectArray)
                                new fabric.lang.arrays.ObjectArray._Impl(
                                  this.$getStore()).
                                fabric$lang$arrays$ObjectArray$(
                                  this.get$$updateLabel(),
                                  this.get$$updateLabel().confPolicy(),
                                  fabric.metrics.Metric._Proxy.class,
                                  termsBag.size()).$getProxy();
                        }
                        catch (final fabric.worker.RetryException $e181) {
                            $commit180 = false;
                            continue $label179;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e181) {
                            $commit180 = false;
                            fabric.common.TransactionID $currentTid182 =
                              $tm183.getCurrentTid();
                            if ($e181.tid.isDescendantOf($currentTid182))
                                continue $label179;
                            if ($currentTid182.parent != null) throw $e181;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e181) {
                            $commit180 = false;
                            if ($tm183.checkForStaleObjects())
                                continue $label179;
                            throw new fabric.worker.AbortException($e181);
                        }
                        finally {
                            if ($commit180) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e181) {
                                    $commit180 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e181) {
                                    $commit180 = false;
                                    fabric.common.TransactionID $currentTid182 =
                                      $tm183.getCurrentTid();
                                    if ($currentTid182 != null) {
                                        if ($e181.tid.equals($currentTid182) ||
                                              !$e181.tid.isDescendantOf(
                                                           $currentTid182)) {
                                            throw $e181;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit180) {
                                {
                                    newTerms = newTerms$var177;
                                    termsBag = termsBag$var178;
                                }
                                continue $label179;
                            }
                        }
                    }
                }
                int aggIdx = 0;
                for (fabric.util.Iterator iter = termsBag.iterator();
                     iter.hasNext(); ) {
                    fabric.metrics.Metric m =
                      (fabric.metrics.Metric)
                        fabric.lang.Object._Proxy.$getProxy(iter.next());
                    newTerms.set(aggIdx++, m);
                }
                {
                    fabric.metrics.DerivedMetric val$var187 = val;
                    fabric.lang.arrays.ObjectArray newTerms$var188 = newTerms;
                    fabric.util.Set termsBag$var189 = termsBag;
                    int aggIdx$var190 = aggIdx;
                    fabric.worker.transaction.TransactionManager $tm195 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled198 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff196 = 1;
                    boolean $doBackoff197 = true;
                    $label191: for (boolean $commit192 = false; !$commit192; ) {
                        if ($backoffEnabled198) {
                            if ($doBackoff197) {
                                if ($backoff196 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff196);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e193) {
                                            
                                        }
                                    }
                                }
                                if ($backoff196 < 5000) $backoff196 *= 2;
                            }
                            $doBackoff197 = $backoff196 <= 32 || !$doBackoff197;
                        }
                        $commit192 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e193) {
                            $commit192 = false;
                            continue $label191;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e193) {
                            $commit192 = false;
                            fabric.common.TransactionID $currentTid194 =
                              $tm195.getCurrentTid();
                            if ($e193.tid.isDescendantOf($currentTid194))
                                continue $label191;
                            if ($currentTid194.parent != null) throw $e193;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e193) {
                            $commit192 = false;
                            if ($tm195.checkForStaleObjects())
                                continue $label191;
                            throw new fabric.worker.AbortException($e193);
                        }
                        finally {
                            if ($commit192) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e193) {
                                    $commit192 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e193) {
                                    $commit192 = false;
                                    fabric.common.TransactionID $currentTid194 =
                                      $tm195.getCurrentTid();
                                    if ($currentTid194 != null) {
                                        if ($e193.tid.equals($currentTid194) ||
                                              !$e193.tid.isDescendantOf(
                                                           $currentTid194)) {
                                            throw $e193;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit192) {
                                {
                                    val = val$var187;
                                    newTerms = newTerms$var188;
                                    termsBag = termsBag$var189;
                                    aggIdx = aggIdx$var190;
                                }
                                continue $label191;
                            }
                        }
                    }
                }
                return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
            }
            else if (fabric.util.Arrays._Impl.asList(this.get$terms()).
                       indexOf(other) >= 0) {
                return (fabric.metrics.MinMetric) this.$getProxy();
            }
            fabric.lang.arrays.ObjectArray newTerms = null;
            {
                fabric.metrics.DerivedMetric val$var199 = val;
                fabric.lang.arrays.ObjectArray newTerms$var200 = newTerms;
                fabric.worker.transaction.TransactionManager $tm205 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled208 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff206 = 1;
                boolean $doBackoff207 = true;
                $label201: for (boolean $commit202 = false; !$commit202; ) {
                    if ($backoffEnabled208) {
                        if ($doBackoff207) {
                            if ($backoff206 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff206);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e203) {
                                        
                                    }
                                }
                            }
                            if ($backoff206 < 5000) $backoff206 *= 2;
                        }
                        $doBackoff207 = $backoff206 <= 32 || !$doBackoff207;
                    }
                    $commit202 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        newTerms =
                          (fabric.lang.arrays.ObjectArray)
                            new fabric.lang.arrays.ObjectArray._Impl(
                              this.$getStore()).
                            fabric$lang$arrays$ObjectArray$(
                              this.get$$updateLabel(),
                              this.get$$updateLabel().confPolicy(),
                              fabric.metrics.Metric._Proxy.class,
                              this.get$terms().get$length() + 1).$getProxy();
                    }
                    catch (final fabric.worker.RetryException $e203) {
                        $commit202 = false;
                        continue $label201;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e203) {
                        $commit202 = false;
                        fabric.common.TransactionID $currentTid204 =
                          $tm205.getCurrentTid();
                        if ($e203.tid.isDescendantOf($currentTid204))
                            continue $label201;
                        if ($currentTid204.parent != null) throw $e203;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e203) {
                        $commit202 = false;
                        if ($tm205.checkForStaleObjects()) continue $label201;
                        throw new fabric.worker.AbortException($e203);
                    }
                    finally {
                        if ($commit202) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e203) {
                                $commit202 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e203) {
                                $commit202 = false;
                                fabric.common.TransactionID $currentTid204 =
                                  $tm205.getCurrentTid();
                                if ($currentTid204 != null) {
                                    if ($e203.tid.equals($currentTid204) ||
                                          !$e203.tid.isDescendantOf(
                                                       $currentTid204)) {
                                        throw $e203;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit202) {
                            {
                                val = val$var199;
                                newTerms = newTerms$var200;
                            }
                            continue $label201;
                        }
                    }
                }
            }
            java.lang.System.
              arraycopy(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(this.get$terms()),
                0,
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(newTerms), 0,
                this.get$terms().get$length());
            newTerms.set(this.get$terms().get$length(), other);
            fabric.util.Arrays._Impl.sort(newTerms, 0, newTerms.get$length());
            {
                fabric.metrics.DerivedMetric val$var209 = val;
                fabric.lang.arrays.ObjectArray newTerms$var210 = newTerms;
                fabric.worker.transaction.TransactionManager $tm215 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled218 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff216 = 1;
                boolean $doBackoff217 = true;
                $label211: for (boolean $commit212 = false; !$commit212; ) {
                    if ($backoffEnabled218) {
                        if ($doBackoff217) {
                            if ($backoff216 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff216);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e213) {
                                        
                                    }
                                }
                            }
                            if ($backoff216 < 5000) $backoff216 *= 2;
                        }
                        $doBackoff217 = $backoff216 <= 32 || !$doBackoff217;
                    }
                    $commit212 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.MinMetric)
                             new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                            fabric$metrics$MinMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e213) {
                        $commit212 = false;
                        continue $label211;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e213) {
                        $commit212 = false;
                        fabric.common.TransactionID $currentTid214 =
                          $tm215.getCurrentTid();
                        if ($e213.tid.isDescendantOf($currentTid214))
                            continue $label211;
                        if ($currentTid214.parent != null) throw $e213;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e213) {
                        $commit212 = false;
                        if ($tm215.checkForStaleObjects()) continue $label211;
                        throw new fabric.worker.AbortException($e213);
                    }
                    finally {
                        if ($commit212) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e213) {
                                $commit212 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e213) {
                                $commit212 = false;
                                fabric.common.TransactionID $currentTid214 =
                                  $tm215.getCurrentTid();
                                if ($currentTid214 != null) {
                                    if ($e213.tid.equals($currentTid214) ||
                                          !$e213.tid.isDescendantOf(
                                                       $currentTid214)) {
                                        throw $e213;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit212) {
                            {
                                val = val$var209;
                                newTerms = newTerms$var210;
                            }
                            continue $label211;
                        }
                    }
                }
            }
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          fabric.metrics.contracts.Bound bound, boolean useWeakCache) {
            if (isSingleStore())
                return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                          new fabric.metrics.contracts.enforcement.DirectPolicy.
                            _Impl(this.$getStore()).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$DirectPolicy$(
                    (fabric.metrics.MinMetric) this.$getProxy(), bound);
            fabric.lang.arrays.ObjectArray
              witnesses =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.
                      $getStore()).
                fabric$lang$arrays$ObjectArray$(
                  this.get$$updateLabel(), this.get$$updateLabel().confPolicy(),
                  fabric.metrics.contracts.MetricContract._Proxy.class,
                  this.get$terms().get$length()).$getProxy();
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                witnesses.set(i, term(i).getContract(bound));
            }
            final fabric.worker.Store bndStore = bound.getStore();
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(bndStore).
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
    
    public static final byte[] $classHash = new byte[] { 31, -102, 110, 30,
    -122, -38, -99, -35, -49, -5, -63, 3, 42, -7, -30, -96, -85, -12, 112, 85,
    -30, -77, -61, 28, 96, 70, -28, -39, 3, -35, 109, -18 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506966071000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
