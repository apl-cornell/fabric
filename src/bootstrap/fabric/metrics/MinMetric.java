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
            return fabric.metrics.MinMetric._Impl.static_times(
                                                    (fabric.metrics.MinMetric)
                                                      this.$getProxy(), scalar);
        }
        
        private static fabric.metrics.DerivedMetric static_times(
          fabric.metrics.MinMetric tmp, double scalar) {
            fabric.lang.arrays.ObjectArray newTerms = null;
            {
                fabric.lang.arrays.ObjectArray newTerms$var155 = newTerms;
                fabric.worker.transaction.TransactionManager $tm160 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled163 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff161 = 1;
                boolean $doBackoff162 = true;
                $label156: for (boolean $commit157 = false; !$commit157; ) {
                    if ($backoffEnabled163) {
                        if ($doBackoff162) {
                            if ($backoff161 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff161);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e158) {
                                        
                                    }
                                }
                            }
                            if ($backoff161 < 5000) $backoff161 *= 2;
                        }
                        $doBackoff162 = $backoff161 <= 32 || !$doBackoff162;
                    }
                    $commit157 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        newTerms =
                          (fabric.lang.arrays.ObjectArray)
                            new fabric.lang.arrays.ObjectArray._Impl(
                              fabric.metrics.MinMetric._Static._Proxy.$instance.
                                  $getStore()).
                            fabric$lang$arrays$ObjectArray$(
                              fabric.metrics.MinMetric._Static._Proxy.$instance.
                                  get$$updateLabel(),
                              fabric.metrics.MinMetric._Static._Proxy.$instance.
                                  get$$updateLabel().confPolicy(),
                              fabric.metrics.Metric._Proxy.class,
                              tmp.get$terms().get$length()).$getProxy();
                    }
                    catch (final fabric.worker.RetryException $e158) {
                        $commit157 = false;
                        continue $label156;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e158) {
                        $commit157 = false;
                        fabric.common.TransactionID $currentTid159 =
                          $tm160.getCurrentTid();
                        if ($e158.tid.isDescendantOf($currentTid159))
                            continue $label156;
                        if ($currentTid159.parent != null) throw $e158;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e158) {
                        $commit157 = false;
                        if ($tm160.checkForStaleObjects()) continue $label156;
                        throw new fabric.worker.AbortException($e158);
                    }
                    finally {
                        if ($commit157) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e158) {
                                $commit157 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e158) {
                                $commit157 = false;
                                fabric.common.TransactionID $currentTid159 =
                                  $tm160.getCurrentTid();
                                if ($currentTid159 != null) {
                                    if ($e158.tid.equals($currentTid159) ||
                                          !$e158.tid.isDescendantOf(
                                                       $currentTid159)) {
                                        throw $e158;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit157) {
                            { newTerms = newTerms$var155; }
                            continue $label156;
                        }
                    }
                }
            }
            fabric.util.Arrays._Impl.arraycopy(tmp.get$terms(), 0, newTerms, 0,
                                               tmp.get$terms().get$length());
            for (int i = 0; i < newTerms.get$length(); i++) {
                newTerms.set(i,
                             ((fabric.metrics.Metric)
                                newTerms.get(i)).times(scalar));
            }
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            {
                fabric.metrics.DerivedMetric val$var164 = val;
                fabric.lang.arrays.ObjectArray newTerms$var165 = newTerms;
                fabric.worker.transaction.TransactionManager $tm170 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled173 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff171 = 1;
                boolean $doBackoff172 = true;
                $label166: for (boolean $commit167 = false; !$commit167; ) {
                    if ($backoffEnabled173) {
                        if ($doBackoff172) {
                            if ($backoff171 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff171);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e168) {
                                        
                                    }
                                }
                            }
                            if ($backoff171 < 5000) $backoff171 *= 2;
                        }
                        $doBackoff172 = $backoff171 <= 32 || !$doBackoff172;
                    }
                    $commit167 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.MinMetric)
                             new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                            fabric$metrics$MinMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e168) {
                        $commit167 = false;
                        continue $label166;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e168) {
                        $commit167 = false;
                        fabric.common.TransactionID $currentTid169 =
                          $tm170.getCurrentTid();
                        if ($e168.tid.isDescendantOf($currentTid169))
                            continue $label166;
                        if ($currentTid169.parent != null) throw $e168;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e168) {
                        $commit167 = false;
                        if ($tm170.checkForStaleObjects()) continue $label166;
                        throw new fabric.worker.AbortException($e168);
                    }
                    finally {
                        if ($commit167) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e168) {
                                $commit167 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e168) {
                                $commit167 = false;
                                fabric.common.TransactionID $currentTid169 =
                                  $tm170.getCurrentTid();
                                if ($currentTid169 != null) {
                                    if ($e168.tid.equals($currentTid169) ||
                                          !$e168.tid.isDescendantOf(
                                                       $currentTid169)) {
                                        throw $e168;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit167) {
                            {
                                val = val$var164;
                                newTerms = newTerms$var165;
                            }
                            continue $label166;
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
            fabric.lang.arrays.ObjectArray newTerms = null;
            {
                fabric.lang.arrays.ObjectArray newTerms$var174 = newTerms;
                fabric.worker.transaction.TransactionManager $tm179 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled182 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff180 = 1;
                boolean $doBackoff181 = true;
                $label175: for (boolean $commit176 = false; !$commit176; ) {
                    if ($backoffEnabled182) {
                        if ($doBackoff181) {
                            if ($backoff180 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff180);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e177) {
                                        
                                    }
                                }
                            }
                            if ($backoff180 < 5000) $backoff180 *= 2;
                        }
                        $doBackoff181 = $backoff180 <= 32 || !$doBackoff181;
                    }
                    $commit176 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        newTerms =
                          (fabric.lang.arrays.ObjectArray)
                            new fabric.lang.arrays.ObjectArray._Impl(
                              fabric.metrics.MinMetric._Static._Proxy.$instance.
                                  $getStore()).
                            fabric$lang$arrays$ObjectArray$(
                              fabric.metrics.MinMetric._Static._Proxy.$instance.
                                  get$$updateLabel(),
                              fabric.metrics.MinMetric._Static._Proxy.$instance.
                                  get$$updateLabel().confPolicy(),
                              fabric.metrics.Metric._Proxy.class,
                              tmp.get$terms().get$length()).$getProxy();
                    }
                    catch (final fabric.worker.RetryException $e177) {
                        $commit176 = false;
                        continue $label175;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e177) {
                        $commit176 = false;
                        fabric.common.TransactionID $currentTid178 =
                          $tm179.getCurrentTid();
                        if ($e177.tid.isDescendantOf($currentTid178))
                            continue $label175;
                        if ($currentTid178.parent != null) throw $e177;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e177) {
                        $commit176 = false;
                        if ($tm179.checkForStaleObjects()) continue $label175;
                        throw new fabric.worker.AbortException($e177);
                    }
                    finally {
                        if ($commit176) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e177) {
                                $commit176 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e177) {
                                $commit176 = false;
                                fabric.common.TransactionID $currentTid178 =
                                  $tm179.getCurrentTid();
                                if ($currentTid178 != null) {
                                    if ($e177.tid.equals($currentTid178) ||
                                          !$e177.tid.isDescendantOf(
                                                       $currentTid178)) {
                                        throw $e177;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit176) {
                            { newTerms = newTerms$var174; }
                            continue $label175;
                        }
                    }
                }
            }
            fabric.util.Arrays._Impl.arraycopy(tmp.get$terms(), 0, newTerms, 0,
                                               tmp.get$terms().get$length());
            for (int i = 0; i < newTerms.get$length(); i++) {
                newTerms.set(i,
                             other.plus((fabric.metrics.Metric)
                                          newTerms.get(i)));
            }
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            {
                fabric.metrics.DerivedMetric val$var183 = val;
                fabric.lang.arrays.ObjectArray newTerms$var184 = newTerms;
                fabric.worker.transaction.TransactionManager $tm189 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled192 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff190 = 1;
                boolean $doBackoff191 = true;
                $label185: for (boolean $commit186 = false; !$commit186; ) {
                    if ($backoffEnabled192) {
                        if ($doBackoff191) {
                            if ($backoff190 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff190);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e187) {
                                        
                                    }
                                }
                            }
                            if ($backoff190 < 5000) $backoff190 *= 2;
                        }
                        $doBackoff191 = $backoff190 <= 32 || !$doBackoff191;
                    }
                    $commit186 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.MinMetric)
                             new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                            fabric$metrics$MinMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e187) {
                        $commit186 = false;
                        continue $label185;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e187) {
                        $commit186 = false;
                        fabric.common.TransactionID $currentTid188 =
                          $tm189.getCurrentTid();
                        if ($e187.tid.isDescendantOf($currentTid188))
                            continue $label185;
                        if ($currentTid188.parent != null) throw $e187;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e187) {
                        $commit186 = false;
                        if ($tm189.checkForStaleObjects()) continue $label185;
                        throw new fabric.worker.AbortException($e187);
                    }
                    finally {
                        if ($commit186) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e187) {
                                $commit186 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e187) {
                                $commit186 = false;
                                fabric.common.TransactionID $currentTid188 =
                                  $tm189.getCurrentTid();
                                if ($currentTid188 != null) {
                                    if ($e187.tid.equals($currentTid188) ||
                                          !$e187.tid.isDescendantOf(
                                                       $currentTid188)) {
                                        throw $e187;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit186) {
                            {
                                val = val$var183;
                                newTerms = newTerms$var184;
                            }
                            continue $label185;
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
                fabric.util.Set termsBag = null;
                {
                    fabric.util.Set termsBag$var193 = termsBag;
                    fabric.worker.transaction.TransactionManager $tm198 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled201 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff199 = 1;
                    boolean $doBackoff200 = true;
                    $label194: for (boolean $commit195 = false; !$commit195; ) {
                        if ($backoffEnabled201) {
                            if ($doBackoff200) {
                                if ($backoff199 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff199);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e196) {
                                            
                                        }
                                    }
                                }
                                if ($backoff199 < 5000) $backoff199 *= 2;
                            }
                            $doBackoff200 = $backoff199 <= 32 || !$doBackoff200;
                        }
                        $commit195 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            termsBag =
                              (fabric.util.TreeSet)
                                new fabric.
                                  util.
                                  TreeSet.
                                  _Impl(
                                  fabric.metrics.MinMetric._Static._Proxy.
                                    $instance.
                                      $getStore()).
                                $getProxy();
                        }
                        catch (final fabric.worker.RetryException $e196) {
                            $commit195 = false;
                            continue $label194;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e196) {
                            $commit195 = false;
                            fabric.common.TransactionID $currentTid197 =
                              $tm198.getCurrentTid();
                            if ($e196.tid.isDescendantOf($currentTid197))
                                continue $label194;
                            if ($currentTid197.parent != null) throw $e196;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e196) {
                            $commit195 = false;
                            if ($tm198.checkForStaleObjects())
                                continue $label194;
                            throw new fabric.worker.AbortException($e196);
                        }
                        finally {
                            if ($commit195) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e196) {
                                    $commit195 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e196) {
                                    $commit195 = false;
                                    fabric.common.TransactionID $currentTid197 =
                                      $tm198.getCurrentTid();
                                    if ($currentTid197 != null) {
                                        if ($e196.tid.equals($currentTid197) ||
                                              !$e196.tid.isDescendantOf(
                                                           $currentTid197)) {
                                            throw $e196;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit195) {
                                { termsBag = termsBag$var193; }
                                continue $label194;
                            }
                        }
                    }
                }
                termsBag.addAll(
                           fabric.util.Arrays._Impl.asList(tmp.get$terms()));
                termsBag.addAll(
                           fabric.util.Arrays._Impl.asList(that.get$terms()));
                fabric.lang.arrays.ObjectArray newTerms = null;
                {
                    fabric.lang.arrays.ObjectArray newTerms$var202 = newTerms;
                    fabric.util.Set termsBag$var203 = termsBag;
                    fabric.worker.transaction.TransactionManager $tm208 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled211 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff209 = 1;
                    boolean $doBackoff210 = true;
                    $label204: for (boolean $commit205 = false; !$commit205; ) {
                        if ($backoffEnabled211) {
                            if ($doBackoff210) {
                                if ($backoff209 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff209);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e206) {
                                            
                                        }
                                    }
                                }
                                if ($backoff209 < 5000) $backoff209 *= 2;
                            }
                            $doBackoff210 = $backoff209 <= 32 || !$doBackoff210;
                        }
                        $commit205 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            newTerms =
                              (fabric.lang.arrays.ObjectArray)
                                new fabric.lang.arrays.ObjectArray._Impl(
                                  fabric.metrics.MinMetric._Static._Proxy.
                                    $instance.
                                      $getStore()).
                                fabric$lang$arrays$ObjectArray$(
                                  fabric.metrics.MinMetric._Static._Proxy.
                                    $instance.
                                      get$$updateLabel(),
                                  fabric.metrics.MinMetric._Static._Proxy.
                                    $instance.
                                      get$$updateLabel().
                                      confPolicy(),
                                  fabric.metrics.Metric._Proxy.class,
                                  termsBag.size()).$getProxy();
                        }
                        catch (final fabric.worker.RetryException $e206) {
                            $commit205 = false;
                            continue $label204;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e206) {
                            $commit205 = false;
                            fabric.common.TransactionID $currentTid207 =
                              $tm208.getCurrentTid();
                            if ($e206.tid.isDescendantOf($currentTid207))
                                continue $label204;
                            if ($currentTid207.parent != null) throw $e206;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e206) {
                            $commit205 = false;
                            if ($tm208.checkForStaleObjects())
                                continue $label204;
                            throw new fabric.worker.AbortException($e206);
                        }
                        finally {
                            if ($commit205) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e206) {
                                    $commit205 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e206) {
                                    $commit205 = false;
                                    fabric.common.TransactionID $currentTid207 =
                                      $tm208.getCurrentTid();
                                    if ($currentTid207 != null) {
                                        if ($e206.tid.equals($currentTid207) ||
                                              !$e206.tid.isDescendantOf(
                                                           $currentTid207)) {
                                            throw $e206;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit205) {
                                {
                                    newTerms = newTerms$var202;
                                    termsBag = termsBag$var203;
                                }
                                continue $label204;
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
                    fabric.metrics.DerivedMetric val$var212 = val;
                    fabric.lang.arrays.ObjectArray newTerms$var213 = newTerms;
                    fabric.util.Set termsBag$var214 = termsBag;
                    int aggIdx$var215 = aggIdx;
                    fabric.worker.transaction.TransactionManager $tm220 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled223 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff221 = 1;
                    boolean $doBackoff222 = true;
                    $label216: for (boolean $commit217 = false; !$commit217; ) {
                        if ($backoffEnabled223) {
                            if ($doBackoff222) {
                                if ($backoff221 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff221);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e218) {
                                            
                                        }
                                    }
                                }
                                if ($backoff221 < 5000) $backoff221 *= 2;
                            }
                            $doBackoff222 = $backoff221 <= 32 || !$doBackoff222;
                        }
                        $commit217 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e218) {
                            $commit217 = false;
                            continue $label216;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e218) {
                            $commit217 = false;
                            fabric.common.TransactionID $currentTid219 =
                              $tm220.getCurrentTid();
                            if ($e218.tid.isDescendantOf($currentTid219))
                                continue $label216;
                            if ($currentTid219.parent != null) throw $e218;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e218) {
                            $commit217 = false;
                            if ($tm220.checkForStaleObjects())
                                continue $label216;
                            throw new fabric.worker.AbortException($e218);
                        }
                        finally {
                            if ($commit217) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e218) {
                                    $commit217 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e218) {
                                    $commit217 = false;
                                    fabric.common.TransactionID $currentTid219 =
                                      $tm220.getCurrentTid();
                                    if ($currentTid219 != null) {
                                        if ($e218.tid.equals($currentTid219) ||
                                              !$e218.tid.isDescendantOf(
                                                           $currentTid219)) {
                                            throw $e218;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit217) {
                                {
                                    val = val$var212;
                                    newTerms = newTerms$var213;
                                    termsBag = termsBag$var214;
                                    aggIdx = aggIdx$var215;
                                }
                                continue $label216;
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
            fabric.lang.arrays.ObjectArray newTerms = null;
            {
                fabric.metrics.DerivedMetric val$var224 = val;
                fabric.lang.arrays.ObjectArray newTerms$var225 = newTerms;
                fabric.worker.transaction.TransactionManager $tm230 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled233 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff231 = 1;
                boolean $doBackoff232 = true;
                $label226: for (boolean $commit227 = false; !$commit227; ) {
                    if ($backoffEnabled233) {
                        if ($doBackoff232) {
                            if ($backoff231 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff231);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e228) {
                                        
                                    }
                                }
                            }
                            if ($backoff231 < 5000) $backoff231 *= 2;
                        }
                        $doBackoff232 = $backoff231 <= 32 || !$doBackoff232;
                    }
                    $commit227 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        newTerms =
                          (fabric.lang.arrays.ObjectArray)
                            new fabric.lang.arrays.ObjectArray._Impl(
                              fabric.metrics.MinMetric._Static._Proxy.$instance.
                                  $getStore()).
                            fabric$lang$arrays$ObjectArray$(
                              fabric.metrics.MinMetric._Static._Proxy.$instance.
                                  get$$updateLabel(),
                              fabric.metrics.MinMetric._Static._Proxy.$instance.
                                  get$$updateLabel().confPolicy(),
                              fabric.metrics.Metric._Proxy.class,
                              tmp.get$terms().get$length() + 1).$getProxy();
                    }
                    catch (final fabric.worker.RetryException $e228) {
                        $commit227 = false;
                        continue $label226;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e228) {
                        $commit227 = false;
                        fabric.common.TransactionID $currentTid229 =
                          $tm230.getCurrentTid();
                        if ($e228.tid.isDescendantOf($currentTid229))
                            continue $label226;
                        if ($currentTid229.parent != null) throw $e228;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e228) {
                        $commit227 = false;
                        if ($tm230.checkForStaleObjects()) continue $label226;
                        throw new fabric.worker.AbortException($e228);
                    }
                    finally {
                        if ($commit227) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e228) {
                                $commit227 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e228) {
                                $commit227 = false;
                                fabric.common.TransactionID $currentTid229 =
                                  $tm230.getCurrentTid();
                                if ($currentTid229 != null) {
                                    if ($e228.tid.equals($currentTid229) ||
                                          !$e228.tid.isDescendantOf(
                                                       $currentTid229)) {
                                        throw $e228;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit227) {
                            {
                                val = val$var224;
                                newTerms = newTerms$var225;
                            }
                            continue $label226;
                        }
                    }
                }
            }
            java.lang.System.
              arraycopy(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(tmp.get$terms()),
                0,
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(newTerms), 0,
                tmp.get$terms().get$length());
            newTerms.set(tmp.get$terms().get$length(), other);
            fabric.util.Arrays._Impl.sort(newTerms, 0, newTerms.get$length());
            {
                fabric.metrics.DerivedMetric val$var234 = val;
                fabric.lang.arrays.ObjectArray newTerms$var235 = newTerms;
                fabric.worker.transaction.TransactionManager $tm240 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled243 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff241 = 1;
                boolean $doBackoff242 = true;
                $label236: for (boolean $commit237 = false; !$commit237; ) {
                    if ($backoffEnabled243) {
                        if ($doBackoff242) {
                            if ($backoff241 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff241);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e238) {
                                        
                                    }
                                }
                            }
                            if ($backoff241 < 5000) $backoff241 *= 2;
                        }
                        $doBackoff242 = $backoff241 <= 32 || !$doBackoff242;
                    }
                    $commit237 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.MinMetric)
                             new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                            fabric$metrics$MinMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e238) {
                        $commit237 = false;
                        continue $label236;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e238) {
                        $commit237 = false;
                        fabric.common.TransactionID $currentTid239 =
                          $tm240.getCurrentTid();
                        if ($e238.tid.isDescendantOf($currentTid239))
                            continue $label236;
                        if ($currentTid239.parent != null) throw $e238;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e238) {
                        $commit237 = false;
                        if ($tm240.checkForStaleObjects()) continue $label236;
                        throw new fabric.worker.AbortException($e238);
                    }
                    finally {
                        if ($commit237) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e238) {
                                $commit237 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e238) {
                                $commit237 = false;
                                fabric.common.TransactionID $currentTid239 =
                                  $tm240.getCurrentTid();
                                if ($currentTid239 != null) {
                                    if ($e238.tid.equals($currentTid239) ||
                                          !$e238.tid.isDescendantOf(
                                                       $currentTid239)) {
                                        throw $e238;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit237) {
                            {
                                val = val$var234;
                                newTerms = newTerms$var235;
                            }
                            continue $label236;
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
    
    public static final byte[] $classHash = new byte[] { -65, 4, 62, -122, -67,
    29, -28, 101, -105, -5, 124, -52, 74, -119, 108, -67, 103, 24, -113, 25,
    -48, 59, 61, 30, 31, 9, 46, 25, 41, -24, 99, -50 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507234283000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1ZCWxUxxmeXdbHGoMPThsfYAwV124hVaPECSl2OAwLuFxSTcF9+3bWfuHte4/3Zs1CIaKtGkgU0aacqRJXlUhIqEt60UhNnEZN0yYiQilC6YGaoDQoUBclNKIld/9/ZvZ63l28FZZm/ueZ+Wf+85v/vR28Skocm7RElbCmB9hOizqBZUq4M9Sl2A6NdOiK42yA0R51rK/zyOUTkSYv8YZIpaoYpqGpit5jOIyMD92n9CtBg7LgxnWdbZuJX0XGFYrTx4h3c3vCJtMtU9/Zq5tMHjJi/8PzgoeObq3+xRhS1U2qNGM9U5imdpgGownWTSpjNBamtrMkEqGRblJjUBpZT21N0bVdsNA0ukmto/UaCovb1FlHHVPvx4W1TtyiNj8zOYjimyC2HVeZaYP41UL8ONP0YEhzWFuIlEY1qkec7eR+4guRkqiu9MLCyaGkFkG+Y3AZjsPyCg3EtKOKSpMsvm2aEWGk2c2R0rh1FSwA1rIYZX1m6iifocAAqRUi6YrRG1zPbM3ohaUlZhxOYaQ+76awqNxS1G1KL+1hZKp7XZeYglV+bhZkYWSSexnfCXxW7/JZhreurrnrwDeNFYaXeEDmCFV1lL8cmJpcTOtolNrUUKlgrJwbOqJMHtrvJQQWT3ItFmue3X3tK/ObXnxFrJmWY83a8H1UZT3q8fD4PzV0zLljDIpRbpmOhqGQpTn3apecaUtYEO2TUzviZCA5+eK6P3xt70k67CUVnaRUNfV4DKKqRjVjlqZTezk1qK0wGukkfmpEOvh8JymD55BmUDG6Nhp1KOskPp0PlZr8fzBRFLZAE5XBs2ZEzeSzpbA+/pywCCFl0IgH2qeENL8AdAoh3kcYWR7sM2M0GNbjdAeEdxAaVWy1Lwh5a2tq0LHVoB03mAaL5BBEERAnuFozVvPHAIhg3bqtEih19Q6PBwzarJoRGlYc8I6MlPYuHZJhhalHqN2j6geGOsmEoUd5tPgxwh2IUm4PD3i4wY0NmbyH4u1Lr53qOSMiDXmlucDLQr6AlC+Qkg9EqsT8CQAiBQCRBj2JQMdA5094mJQ6PJ9Su1TCLndausKiph1LEI+HqzSR8/P4AO9uA9QAYKics37Lym/sbxkDgWnt8KGvYGmrO03S4NIJTwrEfo9ate/yf545ssdMJwwjrSPyeCQn5mGL2z62qdII4Fx6+7nTldM9Q3tavYghfoA3pkAAAlY0uc/Iyse2JLahNUpCZCzaQNFxKglIFazPNnekR7jfx2NXK0IAjeUSkMPi3eutx/9y9spt/MJIImhVBtSup6wtI2txsyqenzVp22+wKYV1fz/WdfDw1X2bueFhxcxcB7Zi3wHZqkCamvZ3X9n+17fePH7em3YWI6VWPKxraoLrUvM5/HmgfYYNUw8HkAIAd8i0n57KewtPnp2WDRBABxQC0Z3WjUbMjGhRTQnrFCPlk6pZC0//60C1cLcOI8J4Npl/8w3S43XtZO+Zrf9t4tt4VLyB0vZLLxOwNiG98xLbVnaiHIlvnWt89I/K4xD5AEqOtotynCHcHoQ7cBG3xQLeL3TNfQm7FmGtBjnO/5nJ+9nYzRG2xce50q5E/pVKBPu+pA/h7AQL+4nZe9qkMd9lwy/K498+NBBZ+8RCcSXUZgP4UiMe++kbn74WOHbx1Rww4WemtUCn/VTPOLMWjpwxoupZze/idFpdHG68o2PbpV5xbLNLRPfqp1cPvrp8tvoDLxmTyvERBUA2U1umsJBsNoX6xUC1caSCO2F6yqhj0ViLoTUTMuZpSfszjCozknsIu9tTrNzOFZIlLqnp9kc6CjwpeGt0WQnwlYeXuKvPnrhRN9R65YawkLtiyFj4/uBbw+fGNZ7iCOXDy4Jr6C61RlZSWQUSV7AypVUtajUV2hch2B6W9AFGVv3/l9y9UFRCkZh1Z97K7USKTIKyy317cYqT9Tm84C6rlqHR0qHXHRx8rL5j8bC4YVP4j/vMyHHDblIyrqZFJ2PXvS2lL3tJWTep5qWxYrBNCmgL0NsNLnE65GCIjMuazy5URVXWlor9BnfsZxzrvnkys8DHsuKfXzarEh5i8cVfTf2LD5tyw5GXwxGDMzRDEbXKPEB/nRq9rC+HObtsLQa3UL+sUun+Qw99HjhwSKCJKOVnjqimM3lEOc8PGsdPQ0ybUegUzrHs3Wf2PPfUnn1eCbhtjIyBDMDHlQWBlp+BXTd2WzhDIhU3XmGEZKiJmwF9BLBqGhQvGT5XB+iI5Y9uwqtcKjJF7aOZgdQLVlgUr9HEiMjE/+8RHskQmmMPF7HA5WIUmOO+3Qb+U1HepGDVaT1ErAmhOMfyArvFsWtnpE6kXKtMudZUwdiaxs17stG2BdocQkrqJPUWh7bI4hHU93F+tM0UdneBufux2wEv3PhSAi+DXYhEbB1fu1mGHZKtEOsRM550cw6dFgFQ1gha8lFxOiHLh5J+MDqd9heY40XBd9w6tePo3nzSt8Grkl/Q0n8XJz2yXJN0eHTSP1Jg7iB2D7ul31RQ+nsJKfcIWvbP4qRHliuS/mN00v+wwNxj2B12S78ml/Tjkel2aKsJ8Y8XtPxSHulHgBbgjGWbDFKWRhLZao2Te70j6YX8amWCjivcy8KmqVPF4FI8UUDlk9gNMPwGxFXm91hehe+EtglMP0tQ/4cF3PXjkXohyw1J37upXvjvk3zXnxdQ4JfYDcKrVFIBCuitsZ0FndYD4XeXpBOK0wFZaiX1F6HDbwro8Dx2p9NOWGNqTk4n8JxpgNZHSGWLpDXF5QyyVEtaMbqceanA3MvYvcBIOTPFl7kct1PGRJ37C0MuDRdA6ydYOQi6tjgNkWWNpCtG5aK9fNfXC6h5DrszcANjVeskdWxwFa1Z5S2uqXepNwE3XAVtF8j2nqTPjhI2vJDXFhwAb98Mv9vgF2EXetTKLX8t6VP51femK8PqtA3eLGCDi9i9AQ4UR/dwU+DY+VxOnAftICE1CyQdV5wTkaVS0tJROXEN3/XdAgpcwe5tRnyWHs8pOHdPJ7SjcOoJSbeP1j34+DfsLuTwCu5kSRoevVeEUtcKKMVrjmFGxkqv5NONO2UmtJ8RMvGCpC8V5xRk+Z2kzxfhlI8KyP8Jdteh0I9p/KPXmlw+WQLtV4RMCknadEt8gjs1SlpVpE88vvw6eXjAfsZIhfRJHtW4S5ZBe5uQacck3VKcS5Dl65JuuqkOSeBqcgEXviLbisqcQLsZNyIcurDjd5dnXAFV8fbxlOOnRFPX1J3JA76c9wBqAO6rNEYNFliafu7i7Lkwk1sJXzjeJ6TpNklnFWclZGmVtCm/lTIVqy8w14AdaFnepzh9HWaEX9Zbcsn9BWjX4dDnJP1RcXIjy4Ckx26acEnj10rjZ7wa5r58uTIzCyg6B7sm8C7dHlfEB8MnE1C9pl4V8UvrtBy/d8hf3dSO39Pjl1bNn5Tnt46pI34HlXynBqrKpwxs/LP4Ppb8Rc0fIuXRuK5nfh3JeC61bBrVuFn94luJxbUIQEmfHY+Mf0fDJ9TJM1+sWwiKinX43yIrlQa8O8+3rI/b+Bvu4AdTbpSWb7jIP6eDtab/1rf4gaHGd+jRj3e/tvJBfah36vfqXm+7u6nZH6ibc1k9+z+rg/iuWx4AAA==";
}
