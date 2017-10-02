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
                fabric.lang.arrays.ObjectArray newTerms$var137 = newTerms;
                fabric.worker.transaction.TransactionManager $tm142 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff143 = 1;
                boolean $doBackoff144 = true;
                $label138: for (boolean $commit139 = false; !$commit139; ) {
                    if ($doBackoff144) {
                        if ($backoff143 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff143);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e140) {
                                    
                                }
                            }
                        }
                        if ($backoff143 < 5000) $backoff143 *= 1;
                    }
                    $doBackoff144 = $backoff143 <= 32 || !$doBackoff144;
                    $commit139 = true;
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
                    catch (final fabric.worker.RetryException $e140) {
                        $commit139 = false;
                        continue $label138;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e140) {
                        $commit139 = false;
                        fabric.common.TransactionID $currentTid141 =
                          $tm142.getCurrentTid();
                        if ($e140.tid.isDescendantOf($currentTid141))
                            continue $label138;
                        if ($currentTid141.parent != null) throw $e140;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e140) {
                        $commit139 = false;
                        if ($tm142.checkForStaleObjects()) continue $label138;
                        throw new fabric.worker.AbortException($e140);
                    }
                    finally {
                        if ($commit139) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e140) {
                                $commit139 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e140) {
                                $commit139 = false;
                                fabric.common.TransactionID $currentTid141 =
                                  $tm142.getCurrentTid();
                                if ($currentTid141 != null) {
                                    if ($e140.tid.equals($currentTid141) ||
                                          !$e140.tid.isDescendantOf(
                                                       $currentTid141)) {
                                        throw $e140;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit139) {
                            { newTerms = newTerms$var137; }
                            continue $label138;
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
                fabric.metrics.DerivedMetric val$var145 = val;
                fabric.lang.arrays.ObjectArray newTerms$var146 = newTerms;
                fabric.worker.transaction.TransactionManager $tm151 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff152 = 1;
                boolean $doBackoff153 = true;
                $label147: for (boolean $commit148 = false; !$commit148; ) {
                    if ($doBackoff153) {
                        if ($backoff152 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff152);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e149) {
                                    
                                }
                            }
                        }
                        if ($backoff152 < 5000) $backoff152 *= 1;
                    }
                    $doBackoff153 = $backoff152 <= 32 || !$doBackoff153;
                    $commit148 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.MinMetric)
                             new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                            fabric$metrics$MinMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e149) {
                        $commit148 = false;
                        continue $label147;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e149) {
                        $commit148 = false;
                        fabric.common.TransactionID $currentTid150 =
                          $tm151.getCurrentTid();
                        if ($e149.tid.isDescendantOf($currentTid150))
                            continue $label147;
                        if ($currentTid150.parent != null) throw $e149;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e149) {
                        $commit148 = false;
                        if ($tm151.checkForStaleObjects()) continue $label147;
                        throw new fabric.worker.AbortException($e149);
                    }
                    finally {
                        if ($commit148) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e149) {
                                $commit148 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e149) {
                                $commit148 = false;
                                fabric.common.TransactionID $currentTid150 =
                                  $tm151.getCurrentTid();
                                if ($currentTid150 != null) {
                                    if ($e149.tid.equals($currentTid150) ||
                                          !$e149.tid.isDescendantOf(
                                                       $currentTid150)) {
                                        throw $e149;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit148) {
                            {
                                val = val$var145;
                                newTerms = newTerms$var146;
                            }
                            continue $label147;
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
                fabric.lang.arrays.ObjectArray newTerms$var154 = newTerms;
                fabric.worker.transaction.TransactionManager $tm159 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff160 = 1;
                boolean $doBackoff161 = true;
                $label155: for (boolean $commit156 = false; !$commit156; ) {
                    if ($doBackoff161) {
                        if ($backoff160 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff160);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e157) {
                                    
                                }
                            }
                        }
                        if ($backoff160 < 5000) $backoff160 *= 1;
                    }
                    $doBackoff161 = $backoff160 <= 32 || !$doBackoff161;
                    $commit156 = true;
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
                    catch (final fabric.worker.RetryException $e157) {
                        $commit156 = false;
                        continue $label155;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e157) {
                        $commit156 = false;
                        fabric.common.TransactionID $currentTid158 =
                          $tm159.getCurrentTid();
                        if ($e157.tid.isDescendantOf($currentTid158))
                            continue $label155;
                        if ($currentTid158.parent != null) throw $e157;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e157) {
                        $commit156 = false;
                        if ($tm159.checkForStaleObjects()) continue $label155;
                        throw new fabric.worker.AbortException($e157);
                    }
                    finally {
                        if ($commit156) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e157) {
                                $commit156 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e157) {
                                $commit156 = false;
                                fabric.common.TransactionID $currentTid158 =
                                  $tm159.getCurrentTid();
                                if ($currentTid158 != null) {
                                    if ($e157.tid.equals($currentTid158) ||
                                          !$e157.tid.isDescendantOf(
                                                       $currentTid158)) {
                                        throw $e157;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit156) {
                            { newTerms = newTerms$var154; }
                            continue $label155;
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
                fabric.metrics.DerivedMetric val$var162 = val;
                fabric.lang.arrays.ObjectArray newTerms$var163 = newTerms;
                fabric.worker.transaction.TransactionManager $tm168 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff169 = 1;
                boolean $doBackoff170 = true;
                $label164: for (boolean $commit165 = false; !$commit165; ) {
                    if ($doBackoff170) {
                        if ($backoff169 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff169);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e166) {
                                    
                                }
                            }
                        }
                        if ($backoff169 < 5000) $backoff169 *= 1;
                    }
                    $doBackoff170 = $backoff169 <= 32 || !$doBackoff170;
                    $commit165 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.MinMetric)
                             new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                            fabric$metrics$MinMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e166) {
                        $commit165 = false;
                        continue $label164;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e166) {
                        $commit165 = false;
                        fabric.common.TransactionID $currentTid167 =
                          $tm168.getCurrentTid();
                        if ($e166.tid.isDescendantOf($currentTid167))
                            continue $label164;
                        if ($currentTid167.parent != null) throw $e166;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e166) {
                        $commit165 = false;
                        if ($tm168.checkForStaleObjects()) continue $label164;
                        throw new fabric.worker.AbortException($e166);
                    }
                    finally {
                        if ($commit165) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e166) {
                                $commit165 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e166) {
                                $commit165 = false;
                                fabric.common.TransactionID $currentTid167 =
                                  $tm168.getCurrentTid();
                                if ($currentTid167 != null) {
                                    if ($e166.tid.equals($currentTid167) ||
                                          !$e166.tid.isDescendantOf(
                                                       $currentTid167)) {
                                        throw $e166;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit165) {
                            {
                                val = val$var162;
                                newTerms = newTerms$var163;
                            }
                            continue $label164;
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
                    fabric.util.Set termsBag$var171 = termsBag;
                    fabric.worker.transaction.TransactionManager $tm176 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff177 = 1;
                    boolean $doBackoff178 = true;
                    $label172: for (boolean $commit173 = false; !$commit173; ) {
                        if ($doBackoff178) {
                            if ($backoff177 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff177);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e174) {
                                        
                                    }
                                }
                            }
                            if ($backoff177 < 5000) $backoff177 *= 1;
                        }
                        $doBackoff178 = $backoff177 <= 32 || !$doBackoff178;
                        $commit173 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            termsBag =
                              (fabric.util.TreeSet)
                                new fabric.util.TreeSet._Impl(this.$getStore()).
                                $getProxy();
                        }
                        catch (final fabric.worker.RetryException $e174) {
                            $commit173 = false;
                            continue $label172;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e174) {
                            $commit173 = false;
                            fabric.common.TransactionID $currentTid175 =
                              $tm176.getCurrentTid();
                            if ($e174.tid.isDescendantOf($currentTid175))
                                continue $label172;
                            if ($currentTid175.parent != null) throw $e174;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e174) {
                            $commit173 = false;
                            if ($tm176.checkForStaleObjects())
                                continue $label172;
                            throw new fabric.worker.AbortException($e174);
                        }
                        finally {
                            if ($commit173) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e174) {
                                    $commit173 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e174) {
                                    $commit173 = false;
                                    fabric.common.TransactionID $currentTid175 =
                                      $tm176.getCurrentTid();
                                    if ($currentTid175 != null) {
                                        if ($e174.tid.equals($currentTid175) ||
                                              !$e174.tid.isDescendantOf(
                                                           $currentTid175)) {
                                            throw $e174;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit173) {
                                { termsBag = termsBag$var171; }
                                continue $label172;
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
                    fabric.lang.arrays.ObjectArray newTerms$var179 = newTerms;
                    fabric.util.Set termsBag$var180 = termsBag;
                    fabric.worker.transaction.TransactionManager $tm185 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff186 = 1;
                    boolean $doBackoff187 = true;
                    $label181: for (boolean $commit182 = false; !$commit182; ) {
                        if ($doBackoff187) {
                            if ($backoff186 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff186);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e183) {
                                        
                                    }
                                }
                            }
                            if ($backoff186 < 5000) $backoff186 *= 1;
                        }
                        $doBackoff187 = $backoff186 <= 32 || !$doBackoff187;
                        $commit182 = true;
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
                        catch (final fabric.worker.RetryException $e183) {
                            $commit182 = false;
                            continue $label181;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e183) {
                            $commit182 = false;
                            fabric.common.TransactionID $currentTid184 =
                              $tm185.getCurrentTid();
                            if ($e183.tid.isDescendantOf($currentTid184))
                                continue $label181;
                            if ($currentTid184.parent != null) throw $e183;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e183) {
                            $commit182 = false;
                            if ($tm185.checkForStaleObjects())
                                continue $label181;
                            throw new fabric.worker.AbortException($e183);
                        }
                        finally {
                            if ($commit182) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e183) {
                                    $commit182 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e183) {
                                    $commit182 = false;
                                    fabric.common.TransactionID $currentTid184 =
                                      $tm185.getCurrentTid();
                                    if ($currentTid184 != null) {
                                        if ($e183.tid.equals($currentTid184) ||
                                              !$e183.tid.isDescendantOf(
                                                           $currentTid184)) {
                                            throw $e183;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit182) {
                                {
                                    newTerms = newTerms$var179;
                                    termsBag = termsBag$var180;
                                }
                                continue $label181;
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
                    fabric.metrics.DerivedMetric val$var188 = val;
                    fabric.lang.arrays.ObjectArray newTerms$var189 = newTerms;
                    fabric.util.Set termsBag$var190 = termsBag;
                    int aggIdx$var191 = aggIdx;
                    fabric.worker.transaction.TransactionManager $tm196 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff197 = 1;
                    boolean $doBackoff198 = true;
                    $label192: for (boolean $commit193 = false; !$commit193; ) {
                        if ($doBackoff198) {
                            if ($backoff197 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff197);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e194) {
                                        
                                    }
                                }
                            }
                            if ($backoff197 < 5000) $backoff197 *= 1;
                        }
                        $doBackoff198 = $backoff197 <= 32 || !$doBackoff198;
                        $commit193 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e194) {
                            $commit193 = false;
                            continue $label192;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e194) {
                            $commit193 = false;
                            fabric.common.TransactionID $currentTid195 =
                              $tm196.getCurrentTid();
                            if ($e194.tid.isDescendantOf($currentTid195))
                                continue $label192;
                            if ($currentTid195.parent != null) throw $e194;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e194) {
                            $commit193 = false;
                            if ($tm196.checkForStaleObjects())
                                continue $label192;
                            throw new fabric.worker.AbortException($e194);
                        }
                        finally {
                            if ($commit193) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e194) {
                                    $commit193 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e194) {
                                    $commit193 = false;
                                    fabric.common.TransactionID $currentTid195 =
                                      $tm196.getCurrentTid();
                                    if ($currentTid195 != null) {
                                        if ($e194.tid.equals($currentTid195) ||
                                              !$e194.tid.isDescendantOf(
                                                           $currentTid195)) {
                                            throw $e194;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit193) {
                                {
                                    val = val$var188;
                                    newTerms = newTerms$var189;
                                    termsBag = termsBag$var190;
                                    aggIdx = aggIdx$var191;
                                }
                                continue $label192;
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
                int $backoff206 = 1;
                boolean $doBackoff207 = true;
                $label201: for (boolean $commit202 = false; !$commit202; ) {
                    if ($doBackoff207) {
                        if ($backoff206 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff206);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e203) {
                                    
                                }
                            }
                        }
                        if ($backoff206 < 5000) $backoff206 *= 1;
                    }
                    $doBackoff207 = $backoff206 <= 32 || !$doBackoff207;
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
                fabric.metrics.DerivedMetric val$var208 = val;
                fabric.lang.arrays.ObjectArray newTerms$var209 = newTerms;
                fabric.worker.transaction.TransactionManager $tm214 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff215 = 1;
                boolean $doBackoff216 = true;
                $label210: for (boolean $commit211 = false; !$commit211; ) {
                    if ($doBackoff216) {
                        if ($backoff215 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff215);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e212) {
                                    
                                }
                            }
                        }
                        if ($backoff215 < 5000) $backoff215 *= 1;
                    }
                    $doBackoff216 = $backoff215 <= 32 || !$doBackoff216;
                    $commit211 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.MinMetric)
                             new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                            fabric$metrics$MinMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e212) {
                        $commit211 = false;
                        continue $label210;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e212) {
                        $commit211 = false;
                        fabric.common.TransactionID $currentTid213 =
                          $tm214.getCurrentTid();
                        if ($e212.tid.isDescendantOf($currentTid213))
                            continue $label210;
                        if ($currentTid213.parent != null) throw $e212;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e212) {
                        $commit211 = false;
                        if ($tm214.checkForStaleObjects()) continue $label210;
                        throw new fabric.worker.AbortException($e212);
                    }
                    finally {
                        if ($commit211) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e212) {
                                $commit211 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e212) {
                                $commit211 = false;
                                fabric.common.TransactionID $currentTid213 =
                                  $tm214.getCurrentTid();
                                if ($currentTid213 != null) {
                                    if ($e212.tid.equals($currentTid213) ||
                                          !$e212.tid.isDescendantOf(
                                                       $currentTid213)) {
                                        throw $e212;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit211) {
                            {
                                val = val$var208;
                                newTerms = newTerms$var209;
                            }
                            continue $label210;
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
    
    public static final byte[] $classHash = new byte[] { 15, -43, -117, -20,
    -121, 16, -54, 2, 67, -104, 90, -33, -104, -40, -72, -49, 14, 71, -128, 97,
    57, -23, 62, -20, -125, 24, 9, -29, -114, 119, 21, -49 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506451157000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
