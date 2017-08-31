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
                fabric.lang.arrays.ObjectArray newTerms$var158 = newTerms;
                fabric.worker.transaction.TransactionManager $tm163 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff164 = 1;
                boolean $doBackoff165 = true;
                $label159: for (boolean $commit160 = false; !$commit160; ) {
                    if ($doBackoff165) {
                        if ($backoff164 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff164);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e161) {
                                    
                                }
                            }
                        }
                        if ($backoff164 < 5000) $backoff164 *= 1;
                    }
                    $doBackoff165 = $backoff164 <= 32 || !$doBackoff165;
                    $commit160 = true;
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
                    catch (final fabric.worker.RetryException $e161) {
                        $commit160 = false;
                        continue $label159;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e161) {
                        $commit160 = false;
                        fabric.common.TransactionID $currentTid162 =
                          $tm163.getCurrentTid();
                        if ($e161.tid.isDescendantOf($currentTid162))
                            continue $label159;
                        if ($currentTid162.parent != null) throw $e161;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e161) {
                        $commit160 = false;
                        if ($tm163.checkForStaleObjects()) continue $label159;
                        throw new fabric.worker.AbortException($e161);
                    }
                    finally {
                        if ($commit160) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e161) {
                                $commit160 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e161) {
                                $commit160 = false;
                                fabric.common.TransactionID $currentTid162 =
                                  $tm163.getCurrentTid();
                                if ($currentTid162 != null) {
                                    if ($e161.tid.equals($currentTid162) ||
                                          !$e161.tid.isDescendantOf(
                                                       $currentTid162)) {
                                        throw $e161;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit160) {
                            { newTerms = newTerms$var158; }
                            continue $label159;
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
                fabric.metrics.DerivedMetric val$var166 = val;
                fabric.lang.arrays.ObjectArray newTerms$var167 = newTerms;
                fabric.worker.transaction.TransactionManager $tm172 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff173 = 1;
                boolean $doBackoff174 = true;
                $label168: for (boolean $commit169 = false; !$commit169; ) {
                    if ($doBackoff174) {
                        if ($backoff173 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff173);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e170) {
                                    
                                }
                            }
                        }
                        if ($backoff173 < 5000) $backoff173 *= 1;
                    }
                    $doBackoff174 = $backoff173 <= 32 || !$doBackoff174;
                    $commit169 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.MinMetric)
                             new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                            fabric$metrics$MinMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e170) {
                        $commit169 = false;
                        continue $label168;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e170) {
                        $commit169 = false;
                        fabric.common.TransactionID $currentTid171 =
                          $tm172.getCurrentTid();
                        if ($e170.tid.isDescendantOf($currentTid171))
                            continue $label168;
                        if ($currentTid171.parent != null) throw $e170;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e170) {
                        $commit169 = false;
                        if ($tm172.checkForStaleObjects()) continue $label168;
                        throw new fabric.worker.AbortException($e170);
                    }
                    finally {
                        if ($commit169) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e170) {
                                $commit169 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e170) {
                                $commit169 = false;
                                fabric.common.TransactionID $currentTid171 =
                                  $tm172.getCurrentTid();
                                if ($currentTid171 != null) {
                                    if ($e170.tid.equals($currentTid171) ||
                                          !$e170.tid.isDescendantOf(
                                                       $currentTid171)) {
                                        throw $e170;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit169) {
                            {
                                val = val$var166;
                                newTerms = newTerms$var167;
                            }
                            continue $label168;
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
                fabric.lang.arrays.ObjectArray newTerms$var175 = newTerms;
                fabric.worker.transaction.TransactionManager $tm180 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff181 = 1;
                boolean $doBackoff182 = true;
                $label176: for (boolean $commit177 = false; !$commit177; ) {
                    if ($doBackoff182) {
                        if ($backoff181 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff181);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e178) {
                                    
                                }
                            }
                        }
                        if ($backoff181 < 5000) $backoff181 *= 1;
                    }
                    $doBackoff182 = $backoff181 <= 32 || !$doBackoff182;
                    $commit177 = true;
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
                    catch (final fabric.worker.RetryException $e178) {
                        $commit177 = false;
                        continue $label176;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e178) {
                        $commit177 = false;
                        fabric.common.TransactionID $currentTid179 =
                          $tm180.getCurrentTid();
                        if ($e178.tid.isDescendantOf($currentTid179))
                            continue $label176;
                        if ($currentTid179.parent != null) throw $e178;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e178) {
                        $commit177 = false;
                        if ($tm180.checkForStaleObjects()) continue $label176;
                        throw new fabric.worker.AbortException($e178);
                    }
                    finally {
                        if ($commit177) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e178) {
                                $commit177 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e178) {
                                $commit177 = false;
                                fabric.common.TransactionID $currentTid179 =
                                  $tm180.getCurrentTid();
                                if ($currentTid179 != null) {
                                    if ($e178.tid.equals($currentTid179) ||
                                          !$e178.tid.isDescendantOf(
                                                       $currentTid179)) {
                                        throw $e178;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit177) {
                            { newTerms = newTerms$var175; }
                            continue $label176;
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
                int $backoff190 = 1;
                boolean $doBackoff191 = true;
                $label185: for (boolean $commit186 = false; !$commit186; ) {
                    if ($doBackoff191) {
                        if ($backoff190 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff190);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e187) {
                                    
                                }
                            }
                        }
                        if ($backoff190 < 5000) $backoff190 *= 1;
                    }
                    $doBackoff191 = $backoff190 <= 32 || !$doBackoff191;
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
                    fabric.util.Set termsBag$var192 = termsBag;
                    fabric.worker.transaction.TransactionManager $tm197 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff198 = 1;
                    boolean $doBackoff199 = true;
                    $label193: for (boolean $commit194 = false; !$commit194; ) {
                        if ($doBackoff199) {
                            if ($backoff198 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff198);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e195) {
                                        
                                    }
                                }
                            }
                            if ($backoff198 < 5000) $backoff198 *= 1;
                        }
                        $doBackoff199 = $backoff198 <= 32 || !$doBackoff199;
                        $commit194 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            termsBag =
                              (fabric.util.TreeSet)
                                new fabric.util.TreeSet._Impl(this.$getStore()).
                                $getProxy();
                        }
                        catch (final fabric.worker.RetryException $e195) {
                            $commit194 = false;
                            continue $label193;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e195) {
                            $commit194 = false;
                            fabric.common.TransactionID $currentTid196 =
                              $tm197.getCurrentTid();
                            if ($e195.tid.isDescendantOf($currentTid196))
                                continue $label193;
                            if ($currentTid196.parent != null) throw $e195;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e195) {
                            $commit194 = false;
                            if ($tm197.checkForStaleObjects())
                                continue $label193;
                            throw new fabric.worker.AbortException($e195);
                        }
                        finally {
                            if ($commit194) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e195) {
                                    $commit194 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e195) {
                                    $commit194 = false;
                                    fabric.common.TransactionID $currentTid196 =
                                      $tm197.getCurrentTid();
                                    if ($currentTid196 != null) {
                                        if ($e195.tid.equals($currentTid196) ||
                                              !$e195.tid.isDescendantOf(
                                                           $currentTid196)) {
                                            throw $e195;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit194) {
                                { termsBag = termsBag$var192; }
                                continue $label193;
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
                    fabric.lang.arrays.ObjectArray newTerms$var200 = newTerms;
                    fabric.util.Set termsBag$var201 = termsBag;
                    fabric.worker.transaction.TransactionManager $tm206 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff207 = 1;
                    boolean $doBackoff208 = true;
                    $label202: for (boolean $commit203 = false; !$commit203; ) {
                        if ($doBackoff208) {
                            if ($backoff207 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff207);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e204) {
                                        
                                    }
                                }
                            }
                            if ($backoff207 < 5000) $backoff207 *= 1;
                        }
                        $doBackoff208 = $backoff207 <= 32 || !$doBackoff208;
                        $commit203 = true;
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
                        catch (final fabric.worker.RetryException $e204) {
                            $commit203 = false;
                            continue $label202;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e204) {
                            $commit203 = false;
                            fabric.common.TransactionID $currentTid205 =
                              $tm206.getCurrentTid();
                            if ($e204.tid.isDescendantOf($currentTid205))
                                continue $label202;
                            if ($currentTid205.parent != null) throw $e204;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e204) {
                            $commit203 = false;
                            if ($tm206.checkForStaleObjects())
                                continue $label202;
                            throw new fabric.worker.AbortException($e204);
                        }
                        finally {
                            if ($commit203) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e204) {
                                    $commit203 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e204) {
                                    $commit203 = false;
                                    fabric.common.TransactionID $currentTid205 =
                                      $tm206.getCurrentTid();
                                    if ($currentTid205 != null) {
                                        if ($e204.tid.equals($currentTid205) ||
                                              !$e204.tid.isDescendantOf(
                                                           $currentTid205)) {
                                            throw $e204;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit203) {
                                {
                                    newTerms = newTerms$var200;
                                    termsBag = termsBag$var201;
                                }
                                continue $label202;
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
                    fabric.metrics.DerivedMetric val$var209 = val;
                    fabric.lang.arrays.ObjectArray newTerms$var210 = newTerms;
                    fabric.util.Set termsBag$var211 = termsBag;
                    int aggIdx$var212 = aggIdx;
                    fabric.worker.transaction.TransactionManager $tm217 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff218 = 1;
                    boolean $doBackoff219 = true;
                    $label213: for (boolean $commit214 = false; !$commit214; ) {
                        if ($doBackoff219) {
                            if ($backoff218 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff218);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e215) {
                                        
                                    }
                                }
                            }
                            if ($backoff218 < 5000) $backoff218 *= 1;
                        }
                        $doBackoff219 = $backoff218 <= 32 || !$doBackoff219;
                        $commit214 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e215) {
                            $commit214 = false;
                            continue $label213;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e215) {
                            $commit214 = false;
                            fabric.common.TransactionID $currentTid216 =
                              $tm217.getCurrentTid();
                            if ($e215.tid.isDescendantOf($currentTid216))
                                continue $label213;
                            if ($currentTid216.parent != null) throw $e215;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e215) {
                            $commit214 = false;
                            if ($tm217.checkForStaleObjects())
                                continue $label213;
                            throw new fabric.worker.AbortException($e215);
                        }
                        finally {
                            if ($commit214) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e215) {
                                    $commit214 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e215) {
                                    $commit214 = false;
                                    fabric.common.TransactionID $currentTid216 =
                                      $tm217.getCurrentTid();
                                    if ($currentTid216 != null) {
                                        if ($e215.tid.equals($currentTid216) ||
                                              !$e215.tid.isDescendantOf(
                                                           $currentTid216)) {
                                            throw $e215;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit214) {
                                {
                                    val = val$var209;
                                    newTerms = newTerms$var210;
                                    termsBag = termsBag$var211;
                                    aggIdx = aggIdx$var212;
                                }
                                continue $label213;
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
                fabric.metrics.DerivedMetric val$var220 = val;
                fabric.lang.arrays.ObjectArray newTerms$var221 = newTerms;
                fabric.worker.transaction.TransactionManager $tm226 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff227 = 1;
                boolean $doBackoff228 = true;
                $label222: for (boolean $commit223 = false; !$commit223; ) {
                    if ($doBackoff228) {
                        if ($backoff227 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff227);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e224) {
                                    
                                }
                            }
                        }
                        if ($backoff227 < 5000) $backoff227 *= 1;
                    }
                    $doBackoff228 = $backoff227 <= 32 || !$doBackoff228;
                    $commit223 = true;
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
                    catch (final fabric.worker.RetryException $e224) {
                        $commit223 = false;
                        continue $label222;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e224) {
                        $commit223 = false;
                        fabric.common.TransactionID $currentTid225 =
                          $tm226.getCurrentTid();
                        if ($e224.tid.isDescendantOf($currentTid225))
                            continue $label222;
                        if ($currentTid225.parent != null) throw $e224;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e224) {
                        $commit223 = false;
                        if ($tm226.checkForStaleObjects()) continue $label222;
                        throw new fabric.worker.AbortException($e224);
                    }
                    finally {
                        if ($commit223) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e224) {
                                $commit223 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e224) {
                                $commit223 = false;
                                fabric.common.TransactionID $currentTid225 =
                                  $tm226.getCurrentTid();
                                if ($currentTid225 != null) {
                                    if ($e224.tid.equals($currentTid225) ||
                                          !$e224.tid.isDescendantOf(
                                                       $currentTid225)) {
                                        throw $e224;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit223) {
                            {
                                val = val$var220;
                                newTerms = newTerms$var221;
                            }
                            continue $label222;
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
                fabric.metrics.DerivedMetric val$var229 = val;
                fabric.lang.arrays.ObjectArray newTerms$var230 = newTerms;
                fabric.worker.transaction.TransactionManager $tm235 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff236 = 1;
                boolean $doBackoff237 = true;
                $label231: for (boolean $commit232 = false; !$commit232; ) {
                    if ($doBackoff237) {
                        if ($backoff236 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff236);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e233) {
                                    
                                }
                            }
                        }
                        if ($backoff236 < 5000) $backoff236 *= 1;
                    }
                    $doBackoff237 = $backoff236 <= 32 || !$doBackoff237;
                    $commit232 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.MinMetric)
                             new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                            fabric$metrics$MinMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e233) {
                        $commit232 = false;
                        continue $label231;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e233) {
                        $commit232 = false;
                        fabric.common.TransactionID $currentTid234 =
                          $tm235.getCurrentTid();
                        if ($e233.tid.isDescendantOf($currentTid234))
                            continue $label231;
                        if ($currentTid234.parent != null) throw $e233;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e233) {
                        $commit232 = false;
                        if ($tm235.checkForStaleObjects()) continue $label231;
                        throw new fabric.worker.AbortException($e233);
                    }
                    finally {
                        if ($commit232) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e233) {
                                $commit232 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e233) {
                                $commit232 = false;
                                fabric.common.TransactionID $currentTid234 =
                                  $tm235.getCurrentTid();
                                if ($currentTid234 != null) {
                                    if ($e233.tid.equals($currentTid234) ||
                                          !$e233.tid.isDescendantOf(
                                                       $currentTid234)) {
                                        throw $e233;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit232) {
                            {
                                val = val$var229;
                                newTerms = newTerms$var230;
                            }
                            continue $label231;
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
    
    public static final byte[] $classHash = new byte[] { -40, -36, -71, 112,
    -87, -121, -18, 93, -16, -110, -4, -92, -61, -13, -75, 123, 46, 93, -75,
    -19, -126, -17, 25, -23, 56, 65, 9, -63, -74, -84, 32, 92 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1504028847000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
