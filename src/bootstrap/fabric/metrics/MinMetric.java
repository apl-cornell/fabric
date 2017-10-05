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
                              this.$getStore()).
                            fabric$lang$arrays$ObjectArray$(
                              this.get$$updateLabel(),
                              this.get$$updateLabel().confPolicy(),
                              fabric.metrics.Metric._Proxy.class,
                              this.get$terms().get$length()).$getProxy();
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
                              this.$getStore()).
                            fabric$lang$arrays$ObjectArray$(
                              this.get$$updateLabel(),
                              this.get$$updateLabel().confPolicy(),
                              fabric.metrics.Metric._Proxy.class,
                              this.get$terms().get$length()).$getProxy();
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
                                new fabric.util.TreeSet._Impl(this.$getStore()).
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
                           fabric.util.Arrays._Impl.asList(this.get$terms()));
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
                                  this.$getStore()).
                                fabric$lang$arrays$ObjectArray$(
                                  this.get$$updateLabel(),
                                  this.get$$updateLabel().confPolicy(),
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
            else if (fabric.util.Arrays._Impl.asList(this.get$terms()).
                       indexOf(other) >= 0) {
                return (fabric.metrics.MinMetric) this.$getProxy();
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
                              this.$getStore()).
                            fabric$lang$arrays$ObjectArray$(
                              this.get$$updateLabel(),
                              this.get$$updateLabel().confPolicy(),
                              fabric.metrics.Metric._Proxy.class,
                              this.get$terms().get$length() + 1).$getProxy();
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
                  fabric.lang.WrappedJavaInlineable.$unwrap(this.get$terms()),
                0,
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(newTerms), 0,
                this.get$terms().get$length());
            newTerms.set(this.get$terms().get$length(), other);
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
    
    public static final byte[] $classHash = new byte[] { -20, -22, -46, -97, 59,
    64, 49, -111, 17, 126, 46, 49, -13, -4, -68, 116, -5, 29, -102, -109, -90,
    -65, 125, 109, -7, -106, 103, 36, -89, -77, 45, 21 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507151083000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
