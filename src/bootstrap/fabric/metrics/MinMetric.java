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
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.StatsMap;
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
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
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
                return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
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
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base,
                          fabric.worker.metrics.StatsMap weakStats,
                          final fabric.worker.Store s) {
            fabric.metrics.contracts.Contract[] witnesses =
              new fabric.metrics.contracts.Contract[this.get$terms().length()];
            for (int i = 0; i < this.get$terms().length(); i++) {
                witnesses[i] = term(i).getThresholdContract(rate, base);
            }
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(s).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(witnesses);
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
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.MinMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -59, -47, 31, 85, 45,
    54, -73, 67, -42, -41, -58, -97, 102, -94, -87, 69, 122, 26, 104, -26, -82,
    115, -24, 36, -27, 41, -49, 122, 53, -9, 79, -107 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526846957000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0Za2wUx3nufPiFwS8e4TAG7CvUgO9KeKTEaVN84WF8BtcGJEzBWe/N+Tbe2z125+AMdUUiRZD8sNoUSFAbUiVUSagLaqsofSFRtUmDUqUJjRJQ24RWpSGiKEVRG1RR6PfNzr327ja+Koidbz0z3zff+/tmb+I6mWIapCUiDSmqn43GqelfLw11hXolw6ThoCqZ5laYHZSnerqOXX0+3Owm7hCpkSVN1xRZUgc1k5HpoYekvVJAoyywra+rYyepkhFxo2RGGXHv7EwaZEFcV0eHVZ2JQ/LoH10aOPLk7rofl5HaAVKraP1MYooc1DVGk2yA1MRobIga5tpwmIYHSL1GabifGoqkKvtho64NkAZTGdYkljCo2UdNXd2LGxvMRJwa/MzUJLKvA9tGQma6AezXWewnmKIGQorJOkKkPKJQNWzuId8gnhCZElGlYdg4K5SSIsApBtbjPGyvVoBNIyLJNIXiGVG0MCPz7RhpiX3dsAFQK2KURfX0UR5NggnSYLGkStpwoJ8ZijYMW6foCTiFEW9RorCpMi7JI9IwHWTkLvu+XmsJdlVxtSAKIzPt2zglsJnXZrMsa13ffN/4AW2j5iYu4DlMZRX5rwSkZhtSH41Qg2oytRBrloSOSbPOHnYTAptn2jZbe17++o2vLGs+95q1Z26BPVuGHqIyG5RPDk1/qynYtqYM2aiM66aCrpAjObdqr1jpSMbB22elKeKiP7V4ru/VHQdP0WtuUt1FymVdTcTAq+plPRZXVGpsoBo1JEbDXaSKauEgX+8iFfAeUjRqzW6JREzKuohH5VPlOv8bVBQBEqiiCnhXtIieeo9LLMrfk3FCSAU8xAX/VxDSVg/vXkLKvIxsCET1GA0MqQm6D9w7AA+VDDkagLg1FDlgGnLASGhMgU1iCrwIgBnoUbQe/uoHFuKfHakkcl23z+UChc6X9TAdkkywjvCUzl4VgmGjroapMSir42e7SOPZ49xbqtDDTfBSrg8XWLjJnhuycY8kOtfdOD34uuVpiCvUBVa2+PML/vxp/oClGowfP2QkP2SkCVfSHzzR9QPuJuUmj6c0lRqgcm9clVhEN2JJ4nJxkWZwfO4fYN0RyBqQGGra+ndtevBwSxk4ZnyfB20FW332MMkkly54k8D3B+XaQ1f/febYmJ4JGEZ8eXGcj4lx2GLXj6HLNAx5LkN+yQLppcGzYz435pAqSG9MAgeEXNFsPyMnHjtSuQ21MSVEpqIOJBWXUgmpmkUNfV9mhtt9Og4NlgugsmwM8rT4pf740xff+HAFLxipDFqblWr7KevIilokVsvjsz6j+60GpbDvz0/1fvvo9UM7ueJhR2uhA304BiFaJQhT3Xj0tT2X3n/v5NvujLEYKY8nhlRFTnJZ6u/APxc8t/HB0MMJhJCAgyLsF6TjPo4nL8rwBhlAhSwErJu+bVpMDysRRRpSKXrKrdrPLX/pH+N1lrlVmLGUZ5Bln04gMz+nkxx8ffcnzZyMS8YKlNFfZpuV1hozlNcahjSKfCQfvjDv+G+lp8HzISmZyn7K8wzh+iDcgHdzXbTzcbltbSUOLZa2msQ8/6OVj4twaLN0i69LhF6J+FcuMtgcARtxtTGO44xcmgaZV6zY8EJ58pEjJ8Jbvr/cKgkNuQl8nZaI/fCd//7O/9Tl8wXSRBXT4+0q3UvVrDMb4MiFeV1PD6/FmbC6fG3emuDIlWHr2Pk2Fu27X+yZOL9hkfyEm5SlYzyvAchF6shmFoLNoNC/aCg2zlRzIyxIK3UqKmsdPC2EeNZYsOxOllJFRHIL4XBPGpXruVqg3Bbwpt0eGS9wpdPb3GwtbQIX485lVerdEKpvjn50zNKPvV/I2vjPifevXZg27zTPTx4sFVw+e6OV30fltEdcvJq0TKtQpg54egipuiTgHxjp/v9L3APQUkKLmFMxP0tyVoDMhKbLXrs4xEVvARvYm6r1qLSM4w0EJr7rDX75mlVf09kf6SwsUF+3S1mF6e5TsX+5W8pfcZOKAVLHG2NJY9slkBYS7wCYxAyKyRCZlrOe26ZaPVlH2vOb7J6fday97mTHgIfleD8vNd1JF+Fuuq1w9nHz7MOAqKJJVmuyFJK9SrVhFi2gv15DiUHR2SuaUnr4yON3/ONHrORhde6tec1zNo7VvfODpvHTMIUtdDqFY6z/4MzYL14YO+QW+bWDkTJweXzdlEzb3W3JlHIVK6+jjiEp6hrFEsHX5kBuw+ZF1eEilvYsq3NRdH/6ejRktZ5yMs+z8O/7LQVzHrIyB5fKoTSMOKzFcICr3xQZ+U0xVpeRw/IViymOscGBGvfjTkbmWCHjEyHjS7d7vkzWuz83V0KSI1+AOrRDwM7SciWirBWwo3iuzGZ21GHtAA4Mrst4pYCrXC9mEtbH9w4IL0KwC1w3rCdSZi4gE+T+ij4BO0qTCVHuFXDl5GR61GHtEA4H7TJxPY8V476TkMpuAVeWxj2irBCwfXLcjzusfROHx+zcb3fkHjivekDA9tK4R5RlAi6aHPdPOqwdx+EJO/ebC3E/nYh6uRU46RWwrQj3eb0d5Jm4oTMIWRpO5oo1TdD6vIDzi4uVnXQgHTSLCrhPN0aokS6E+N3H7JHiqQyXez3jbD7roJMXcPgOw088XCe8UBXVyBfh2QW2lQS8x8GeJ/IFR5TVAjp4oyvTIT/HqZ5xEOBHOJyCm1JKAArpXWGjjlaNEFJjCNhdmgyIsknAtSXI8FMHGX6Ow08yRtisK2ZBI/CgaoJHB2a2ChgsLagQpVPA+yYXVL9yWPs1Dr9kpJLp1oe3AuUrayHPQwtJ2A7PGJjrpICPlCYhojws4IFJmWiMU33DQcw3cTgPJRrbVjMlY5OtK83pX3GP1yZeI7HSGRknpO6igN+bZF5xM1IRhwPgcs3wswx+8LWllwZB8hkBv1VcfHemE6zL6OBPDjp4D4e3wYDW0YNcFTh3oZARl8LzPCEzNgm4pDQjIkqbgK2TMuJmTvWKgwAf4HCZEU9cTRRknJunC56fETLrnICPT9Y8+HoRh0sFrIKUHhPQwSntVrGE+shBqBs4fMjIVGGVYrJxo7TCc4EQ72IBa0szCqJMF7CyBKPcdOD/Pzh8DI19TOHftDYXsgl2lX+D1HdQwL7PxCZI6asCBouLU9AmLndxmVwenLzFSLWwSRHRuEkU0NRsQnySgGUlmYSjuC3Yequ4DB7OHedrLD08l8pjjbm9RT/TjfSVKb+hcE11EL0eh3KoxywKNKK6Gu7VVUUeTR212pYy8fZtSDIz/VSDE2Qaoxrzr8u8W+h7CiRTrr45IPsiQhZrAj5YmvoQZVDAHcXVly2h12GtCQeQsjIqmdGgHuZV/GuF+IYIdEGKXHxbwL+Wxjei/EXAP35qJKaU3yCUn3WpdDBzq4Og2Au7mqEM0T0JSbUKou1OVjGk6yqVtCS0w+m7J354nVvg5w/xI5wc/A09eaV72cwiP33clfezqMA7faK2cvaJbe9aH8xSP7BVhUhlJKGq2Z9Lst7L4waNKFzbVdbHkzgXbjncEXLdlPEPa/iG0rkC1r6VIL+1D/9axS3gTQ8XOElvwsCfdCc+nn2zvHLrZf51HZS44JW35m9rX/1y8J13X30m8uyL6/Z7o38/bV71XWn7/f5Vn2w5+j8Y6N+Bah4AAA==";
}
