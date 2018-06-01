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
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.enforcement.DirectPolicy;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.metrics.treaties.enforcement.WitnessPolicy;
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
    
    public double computeValue(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeVelocity(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoise(fabric.worker.metrics.StatsMap weakStats);
    
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
    
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      thresholdPolicy(double rate, double base,
                      fabric.worker.metrics.StatsMap weakStats,
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
        
        public double computeValue(fabric.worker.metrics.StatsMap weakStats) {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result = java.lang.Math.min(result, term(i).value(weakStats));
            }
            return result;
        }
        
        public double computeVelocity(
          fabric.worker.metrics.StatsMap weakStats) {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result = java.lang.Math.min(result,
                                            term(i).velocity(weakStats));
            }
            return result;
        }
        
        public double computeNoise(fabric.worker.metrics.StatsMap weakStats) {
            double noise = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                noise = java.lang.Math.max(noise, term(i).noise(weakStats));
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
                    fabric.metrics.DerivedMetric val$var160 = val;
                    fabric.worker.transaction.TransactionManager $tm166 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled169 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff167 = 1;
                    boolean $doBackoff168 = true;
                    boolean $retry163 = true;
                    $label161: for (boolean $commit162 = false; !$commit162; ) {
                        if ($backoffEnabled169) {
                            if ($doBackoff168) {
                                if ($backoff167 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff167);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e164) {
                                            
                                        }
                                    }
                                }
                                if ($backoff167 < 5000) $backoff167 *= 2;
                            }
                            $doBackoff168 = $backoff167 <= 32 || !$doBackoff168;
                        }
                        $commit162 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e164) {
                            $commit162 = false;
                            continue $label161;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e164) {
                            $commit162 = false;
                            fabric.common.TransactionID $currentTid165 =
                              $tm166.getCurrentTid();
                            if ($e164.tid.isDescendantOf($currentTid165))
                                continue $label161;
                            if ($currentTid165.parent != null) {
                                $retry163 = false;
                                throw $e164;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e164) {
                            $commit162 = false;
                            if ($tm166.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid165 =
                              $tm166.getCurrentTid();
                            if ($e164.tid.isDescendantOf($currentTid165)) {
                                $retry163 = true;
                            }
                            else if ($currentTid165.parent != null) {
                                $retry163 = false;
                                throw $e164;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e164) {
                            $commit162 = false;
                            if ($tm166.checkForStaleObjects())
                                continue $label161;
                            $retry163 = false;
                            throw new fabric.worker.AbortException($e164);
                        }
                        finally {
                            if ($commit162) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e164) {
                                    $commit162 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e164) {
                                    $commit162 = false;
                                    fabric.common.TransactionID $currentTid165 =
                                      $tm166.getCurrentTid();
                                    if ($currentTid165 != null) {
                                        if ($e164.tid.equals($currentTid165) ||
                                              !$e164.tid.isDescendantOf(
                                                           $currentTid165)) {
                                            throw $e164;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit162 && $retry163) {
                                { val = val$var160; }
                                continue $label161;
                            }
                        }
                    }
                }
            }
            return val;
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
                    fabric.metrics.DerivedMetric val$var170 = val;
                    fabric.worker.transaction.TransactionManager $tm176 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled179 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff177 = 1;
                    boolean $doBackoff178 = true;
                    boolean $retry173 = true;
                    $label171: for (boolean $commit172 = false; !$commit172; ) {
                        if ($backoffEnabled179) {
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
                                if ($backoff177 < 5000) $backoff177 *= 2;
                            }
                            $doBackoff178 = $backoff177 <= 32 || !$doBackoff178;
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
                            if (!$commit172 && $retry173) {
                                { val = val$var170; }
                                continue $label171;
                            }
                        }
                    }
                }
            }
            return val;
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
                        fabric.metrics.DerivedMetric val$var180 = val;
                        int aggIdx$var181 = aggIdx;
                        fabric.worker.transaction.TransactionManager $tm187 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled190 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff188 = 1;
                        boolean $doBackoff189 = true;
                        boolean $retry184 = true;
                        $label182: for (boolean $commit183 = false; !$commit183;
                                        ) {
                            if ($backoffEnabled190) {
                                if ($doBackoff189) {
                                    if ($backoff188 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff188);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e185) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff188 < 5000) $backoff188 *= 2;
                                }
                                $doBackoff189 = $backoff188 <= 32 ||
                                                  !$doBackoff189;
                            }
                            $commit183 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    newTerms);
                            }
                            catch (final fabric.worker.RetryException $e185) {
                                $commit183 = false;
                                continue $label182;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e185) {
                                $commit183 = false;
                                fabric.common.TransactionID $currentTid186 =
                                  $tm187.getCurrentTid();
                                if ($e185.tid.isDescendantOf($currentTid186))
                                    continue $label182;
                                if ($currentTid186.parent != null) {
                                    $retry184 = false;
                                    throw $e185;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e185) {
                                $commit183 = false;
                                if ($tm187.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid186 =
                                  $tm187.getCurrentTid();
                                if ($e185.tid.isDescendantOf($currentTid186)) {
                                    $retry184 = true;
                                }
                                else if ($currentTid186.parent != null) {
                                    $retry184 = false;
                                    throw $e185;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e185) {
                                $commit183 = false;
                                if ($tm187.checkForStaleObjects())
                                    continue $label182;
                                $retry184 = false;
                                throw new fabric.worker.AbortException($e185);
                            }
                            finally {
                                if ($commit183) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e185) {
                                        $commit183 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e185) {
                                        $commit183 = false;
                                        fabric.common.TransactionID
                                          $currentTid186 =
                                          $tm187.getCurrentTid();
                                        if ($currentTid186 != null) {
                                            if ($e185.tid.equals(
                                                            $currentTid186) ||
                                                  !$e185.tid.
                                                  isDescendantOf(
                                                    $currentTid186)) {
                                                throw $e185;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit183 && $retry184) {
                                    {
                                        val = val$var180;
                                        aggIdx = aggIdx$var181;
                                    }
                                    continue $label182;
                                }
                            }
                        }
                    }
                }
                return val;
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
                    fabric.metrics.DerivedMetric val$var191 = val;
                    fabric.worker.transaction.TransactionManager $tm197 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled200 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff198 = 1;
                    boolean $doBackoff199 = true;
                    boolean $retry194 = true;
                    $label192: for (boolean $commit193 = false; !$commit193; ) {
                        if ($backoffEnabled200) {
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
                                if ($backoff198 < 5000) $backoff198 *= 2;
                            }
                            $doBackoff199 = $backoff198 <= 32 || !$doBackoff199;
                        }
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
                        catch (final fabric.worker.RetryException $e195) {
                            $commit193 = false;
                            continue $label192;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e195) {
                            $commit193 = false;
                            fabric.common.TransactionID $currentTid196 =
                              $tm197.getCurrentTid();
                            if ($e195.tid.isDescendantOf($currentTid196))
                                continue $label192;
                            if ($currentTid196.parent != null) {
                                $retry194 = false;
                                throw $e195;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e195) {
                            $commit193 = false;
                            if ($tm197.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid196 =
                              $tm197.getCurrentTid();
                            if ($e195.tid.isDescendantOf($currentTid196)) {
                                $retry194 = true;
                            }
                            else if ($currentTid196.parent != null) {
                                $retry194 = false;
                                throw $e195;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e195) {
                            $commit193 = false;
                            if ($tm197.checkForStaleObjects())
                                continue $label192;
                            $retry194 = false;
                            throw new fabric.worker.AbortException($e195);
                        }
                        finally {
                            if ($commit193) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e195) {
                                    $commit193 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e195) {
                                    $commit193 = false;
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
                            if (!$commit193 && $retry194) {
                                { val = val$var191; }
                                continue $label192;
                            }
                        }
                    }
                }
            }
            return val;
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base,
                          fabric.worker.metrics.StatsMap weakStats,
                          final fabric.worker.Store s) {
            fabric.worker.metrics.treaties.MetricTreaty[] witnesses =
              new fabric.worker.metrics.treaties.MetricTreaty[this.get$terms(
                                                                     ).length(
                                                                         )];
            for (int i = 0; i < this.get$terms().length(); i++) {
                witnesses[i] = term(i).getThresholdTreaty(rate, base);
            }
            return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                     witnesses);
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
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, labelStore, labelOnum,
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
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, labelStore, labelOnum,
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
    
    public static final byte[] $classHash = new byte[] { -2, -109, 82, -34, 35,
    -104, 60, -83, -62, 116, 125, 126, 42, 17, 120, -122, 79, 62, 74, -19, -23,
    -120, -110, 67, -72, -9, 15, -47, 85, -28, -35, -106 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527874708000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
