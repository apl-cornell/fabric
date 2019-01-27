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
import fabric.worker.metrics.treaties.statements.EqualityStatement;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import fabric.worker.transaction.TransactionManager;
import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;

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
                    fabric.metrics.DerivedMetric val$var240 = val;
                    fabric.worker.transaction.TransactionManager $tm247 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled250 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff248 = 1;
                    boolean $doBackoff249 = true;
                    boolean $retry243 = true;
                    boolean $keepReads244 = false;
                    $label241: for (boolean $commit242 = false; !$commit242; ) {
                        if ($backoffEnabled250) {
                            if ($doBackoff249) {
                                if ($backoff248 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff248));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e245) {
                                            
                                        }
                                    }
                                }
                                if ($backoff248 < 5000) $backoff248 *= 2;
                            }
                            $doBackoff249 = $backoff248 <= 32 || !$doBackoff249;
                        }
                        $commit242 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    newTerms);
                            }
                            catch (final fabric.worker.RetryException $e245) {
                                throw $e245;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e245) {
                                throw $e245;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e245) {
                                throw $e245;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e245) {
                                throw $e245;
                            }
                            catch (final Throwable $e245) {
                                $tm247.getCurrentLog().checkRetrySignal();
                                throw $e245;
                            }
                        }
                        catch (final fabric.worker.RetryException $e245) {
                            $commit242 = false;
                            continue $label241;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e245) {
                            $commit242 = false;
                            $retry243 = false;
                            $keepReads244 = $e245.keepReads;
                            if ($tm247.checkForStaleObjects()) {
                                $retry243 = true;
                                $keepReads244 = false;
                                continue $label241;
                            }
                            fabric.common.TransactionID $currentTid246 =
                              $tm247.getCurrentTid();
                            if ($e245.tid == null ||
                                  !$e245.tid.isDescendantOf($currentTid246)) {
                                throw $e245;
                            }
                            throw new fabric.worker.UserAbortException($e245);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e245) {
                            $commit242 = false;
                            fabric.common.TransactionID $currentTid246 =
                              $tm247.getCurrentTid();
                            if ($e245.tid.isDescendantOf($currentTid246))
                                continue $label241;
                            if ($currentTid246.parent != null) {
                                $retry243 = false;
                                throw $e245;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e245) {
                            $commit242 = false;
                            if ($tm247.checkForStaleObjects())
                                continue $label241;
                            fabric.common.TransactionID $currentTid246 =
                              $tm247.getCurrentTid();
                            if ($e245.tid.isDescendantOf($currentTid246)) {
                                $retry243 = true;
                            }
                            else if ($currentTid246.parent != null) {
                                $retry243 = false;
                                throw $e245;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e245) {
                            $commit242 = false;
                            if ($tm247.checkForStaleObjects())
                                continue $label241;
                            $retry243 = false;
                            throw new fabric.worker.AbortException($e245);
                        }
                        finally {
                            if ($commit242) {
                                fabric.common.TransactionID $currentTid246 =
                                  $tm247.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e245) {
                                    $commit242 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e245) {
                                    $commit242 = false;
                                    $retry243 = false;
                                    $keepReads244 = $e245.keepReads;
                                    if ($tm247.checkForStaleObjects()) {
                                        $retry243 = true;
                                        $keepReads244 = false;
                                        continue $label241;
                                    }
                                    if ($e245.tid ==
                                          null ||
                                          !$e245.tid.isDescendantOf(
                                                       $currentTid246))
                                        throw $e245;
                                    throw new fabric.worker.UserAbortException(
                                            $e245);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e245) {
                                    $commit242 = false;
                                    $currentTid246 = $tm247.getCurrentTid();
                                    if ($currentTid246 != null) {
                                        if ($e245.tid.equals($currentTid246) ||
                                              !$e245.tid.isDescendantOf(
                                                           $currentTid246)) {
                                            throw $e245;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads244) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit242) {
                                { val = val$var240; }
                                if ($retry243) { continue $label241; }
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
                    fabric.metrics.DerivedMetric val$var251 = val;
                    fabric.worker.transaction.TransactionManager $tm258 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled261 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff259 = 1;
                    boolean $doBackoff260 = true;
                    boolean $retry254 = true;
                    boolean $keepReads255 = false;
                    $label252: for (boolean $commit253 = false; !$commit253; ) {
                        if ($backoffEnabled261) {
                            if ($doBackoff260) {
                                if ($backoff259 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff259));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e256) {
                                            
                                        }
                                    }
                                }
                                if ($backoff259 < 5000) $backoff259 *= 2;
                            }
                            $doBackoff260 = $backoff259 <= 32 || !$doBackoff260;
                        }
                        $commit253 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    newTerms);
                            }
                            catch (final fabric.worker.RetryException $e256) {
                                throw $e256;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e256) {
                                throw $e256;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e256) {
                                throw $e256;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e256) {
                                throw $e256;
                            }
                            catch (final Throwable $e256) {
                                $tm258.getCurrentLog().checkRetrySignal();
                                throw $e256;
                            }
                        }
                        catch (final fabric.worker.RetryException $e256) {
                            $commit253 = false;
                            continue $label252;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e256) {
                            $commit253 = false;
                            $retry254 = false;
                            $keepReads255 = $e256.keepReads;
                            if ($tm258.checkForStaleObjects()) {
                                $retry254 = true;
                                $keepReads255 = false;
                                continue $label252;
                            }
                            fabric.common.TransactionID $currentTid257 =
                              $tm258.getCurrentTid();
                            if ($e256.tid == null ||
                                  !$e256.tid.isDescendantOf($currentTid257)) {
                                throw $e256;
                            }
                            throw new fabric.worker.UserAbortException($e256);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e256) {
                            $commit253 = false;
                            fabric.common.TransactionID $currentTid257 =
                              $tm258.getCurrentTid();
                            if ($e256.tid.isDescendantOf($currentTid257))
                                continue $label252;
                            if ($currentTid257.parent != null) {
                                $retry254 = false;
                                throw $e256;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e256) {
                            $commit253 = false;
                            if ($tm258.checkForStaleObjects())
                                continue $label252;
                            fabric.common.TransactionID $currentTid257 =
                              $tm258.getCurrentTid();
                            if ($e256.tid.isDescendantOf($currentTid257)) {
                                $retry254 = true;
                            }
                            else if ($currentTid257.parent != null) {
                                $retry254 = false;
                                throw $e256;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e256) {
                            $commit253 = false;
                            if ($tm258.checkForStaleObjects())
                                continue $label252;
                            $retry254 = false;
                            throw new fabric.worker.AbortException($e256);
                        }
                        finally {
                            if ($commit253) {
                                fabric.common.TransactionID $currentTid257 =
                                  $tm258.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e256) {
                                    $commit253 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e256) {
                                    $commit253 = false;
                                    $retry254 = false;
                                    $keepReads255 = $e256.keepReads;
                                    if ($tm258.checkForStaleObjects()) {
                                        $retry254 = true;
                                        $keepReads255 = false;
                                        continue $label252;
                                    }
                                    if ($e256.tid ==
                                          null ||
                                          !$e256.tid.isDescendantOf(
                                                       $currentTid257))
                                        throw $e256;
                                    throw new fabric.worker.UserAbortException(
                                            $e256);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e256) {
                                    $commit253 = false;
                                    $currentTid257 = $tm258.getCurrentTid();
                                    if ($currentTid257 != null) {
                                        if ($e256.tid.equals($currentTid257) ||
                                              !$e256.tid.isDescendantOf(
                                                           $currentTid257)) {
                                            throw $e256;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads255) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit253) {
                                { val = val$var251; }
                                if ($retry254) { continue $label252; }
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
                        fabric.metrics.DerivedMetric val$var262 = val;
                        int aggIdx$var263 = aggIdx;
                        fabric.worker.transaction.TransactionManager $tm270 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled273 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff271 = 1;
                        boolean $doBackoff272 = true;
                        boolean $retry266 = true;
                        boolean $keepReads267 = false;
                        $label264: for (boolean $commit265 = false; !$commit265;
                                        ) {
                            if ($backoffEnabled273) {
                                if ($doBackoff272) {
                                    if ($backoff271 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff271));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e268) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff271 < 5000) $backoff271 *= 2;
                                }
                                $doBackoff272 = $backoff271 <= 32 ||
                                                  !$doBackoff272;
                            }
                            $commit265 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    val =
                                      ((fabric.metrics.MinMetric)
                                         new fabric.metrics.MinMetric._Impl(s).
                                         $getProxy()).fabric$metrics$MinMetric$(
                                                        newTerms);
                                }
                                catch (final fabric.worker.
                                         RetryException $e268) {
                                    throw $e268;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e268) {
                                    throw $e268;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e268) {
                                    throw $e268;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e268) {
                                    throw $e268;
                                }
                                catch (final Throwable $e268) {
                                    $tm270.getCurrentLog().checkRetrySignal();
                                    throw $e268;
                                }
                            }
                            catch (final fabric.worker.RetryException $e268) {
                                $commit265 = false;
                                continue $label264;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e268) {
                                $commit265 = false;
                                $retry266 = false;
                                $keepReads267 = $e268.keepReads;
                                if ($tm270.checkForStaleObjects()) {
                                    $retry266 = true;
                                    $keepReads267 = false;
                                    continue $label264;
                                }
                                fabric.common.TransactionID $currentTid269 =
                                  $tm270.getCurrentTid();
                                if ($e268.tid ==
                                      null ||
                                      !$e268.tid.isDescendantOf(
                                                   $currentTid269)) {
                                    throw $e268;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e268);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e268) {
                                $commit265 = false;
                                fabric.common.TransactionID $currentTid269 =
                                  $tm270.getCurrentTid();
                                if ($e268.tid.isDescendantOf($currentTid269))
                                    continue $label264;
                                if ($currentTid269.parent != null) {
                                    $retry266 = false;
                                    throw $e268;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e268) {
                                $commit265 = false;
                                if ($tm270.checkForStaleObjects())
                                    continue $label264;
                                fabric.common.TransactionID $currentTid269 =
                                  $tm270.getCurrentTid();
                                if ($e268.tid.isDescendantOf($currentTid269)) {
                                    $retry266 = true;
                                }
                                else if ($currentTid269.parent != null) {
                                    $retry266 = false;
                                    throw $e268;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e268) {
                                $commit265 = false;
                                if ($tm270.checkForStaleObjects())
                                    continue $label264;
                                $retry266 = false;
                                throw new fabric.worker.AbortException($e268);
                            }
                            finally {
                                if ($commit265) {
                                    fabric.common.TransactionID $currentTid269 =
                                      $tm270.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e268) {
                                        $commit265 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e268) {
                                        $commit265 = false;
                                        $retry266 = false;
                                        $keepReads267 = $e268.keepReads;
                                        if ($tm270.checkForStaleObjects()) {
                                            $retry266 = true;
                                            $keepReads267 = false;
                                            continue $label264;
                                        }
                                        if ($e268.tid ==
                                              null ||
                                              !$e268.tid.isDescendantOf(
                                                           $currentTid269))
                                            throw $e268;
                                        throw new fabric.worker.
                                                UserAbortException($e268);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e268) {
                                        $commit265 = false;
                                        $currentTid269 = $tm270.getCurrentTid();
                                        if ($currentTid269 != null) {
                                            if ($e268.tid.equals(
                                                            $currentTid269) ||
                                                  !$e268.tid.
                                                  isDescendantOf(
                                                    $currentTid269)) {
                                                throw $e268;
                                            }
                                        }
                                    }
                                } else if ($keepReads267) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit265) {
                                    {
                                        val = val$var262;
                                        aggIdx = aggIdx$var263;
                                    }
                                    if ($retry266) { continue $label264; }
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
                    fabric.metrics.DerivedMetric val$var274 = val;
                    fabric.worker.transaction.TransactionManager $tm281 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled284 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff282 = 1;
                    boolean $doBackoff283 = true;
                    boolean $retry277 = true;
                    boolean $keepReads278 = false;
                    $label275: for (boolean $commit276 = false; !$commit276; ) {
                        if ($backoffEnabled284) {
                            if ($doBackoff283) {
                                if ($backoff282 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff282));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e279) {
                                            
                                        }
                                    }
                                }
                                if ($backoff282 < 5000) $backoff282 *= 2;
                            }
                            $doBackoff283 = $backoff282 <= 32 || !$doBackoff283;
                        }
                        $commit276 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    newTerms);
                            }
                            catch (final fabric.worker.RetryException $e279) {
                                throw $e279;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e279) {
                                throw $e279;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e279) {
                                throw $e279;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e279) {
                                throw $e279;
                            }
                            catch (final Throwable $e279) {
                                $tm281.getCurrentLog().checkRetrySignal();
                                throw $e279;
                            }
                        }
                        catch (final fabric.worker.RetryException $e279) {
                            $commit276 = false;
                            continue $label275;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e279) {
                            $commit276 = false;
                            $retry277 = false;
                            $keepReads278 = $e279.keepReads;
                            if ($tm281.checkForStaleObjects()) {
                                $retry277 = true;
                                $keepReads278 = false;
                                continue $label275;
                            }
                            fabric.common.TransactionID $currentTid280 =
                              $tm281.getCurrentTid();
                            if ($e279.tid == null ||
                                  !$e279.tid.isDescendantOf($currentTid280)) {
                                throw $e279;
                            }
                            throw new fabric.worker.UserAbortException($e279);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e279) {
                            $commit276 = false;
                            fabric.common.TransactionID $currentTid280 =
                              $tm281.getCurrentTid();
                            if ($e279.tid.isDescendantOf($currentTid280))
                                continue $label275;
                            if ($currentTid280.parent != null) {
                                $retry277 = false;
                                throw $e279;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e279) {
                            $commit276 = false;
                            if ($tm281.checkForStaleObjects())
                                continue $label275;
                            fabric.common.TransactionID $currentTid280 =
                              $tm281.getCurrentTid();
                            if ($e279.tid.isDescendantOf($currentTid280)) {
                                $retry277 = true;
                            }
                            else if ($currentTid280.parent != null) {
                                $retry277 = false;
                                throw $e279;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e279) {
                            $commit276 = false;
                            if ($tm281.checkForStaleObjects())
                                continue $label275;
                            $retry277 = false;
                            throw new fabric.worker.AbortException($e279);
                        }
                        finally {
                            if ($commit276) {
                                fabric.common.TransactionID $currentTid280 =
                                  $tm281.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e279) {
                                    $commit276 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e279) {
                                    $commit276 = false;
                                    $retry277 = false;
                                    $keepReads278 = $e279.keepReads;
                                    if ($tm281.checkForStaleObjects()) {
                                        $retry277 = true;
                                        $keepReads278 = false;
                                        continue $label275;
                                    }
                                    if ($e279.tid ==
                                          null ||
                                          !$e279.tid.isDescendantOf(
                                                       $currentTid280))
                                        throw $e279;
                                    throw new fabric.worker.UserAbortException(
                                            $e279);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e279) {
                                    $commit276 = false;
                                    $currentTid280 = $tm281.getCurrentTid();
                                    if ($currentTid280 != null) {
                                        if ($e279.tid.equals($currentTid280) ||
                                              !$e279.tid.isDescendantOf(
                                                           $currentTid280)) {
                                            throw $e279;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads278) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit276) {
                                { val = val$var274; }
                                if ($retry277) { continue $label275; }
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
            com.google.common.collect.Multimap witnesses =
              com.google.common.collect.HashMultimap.create();
            for (int i = 0; i < this.get$terms().length(); i++) {
                witnesses.
                  put(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(term(i)),
                    new fabric.worker.metrics.treaties.statements.ThresholdStatement(
                      rate, base));
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
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
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
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { 68, -64, -22, 4, -91,
    -117, -89, -57, -125, -48, 72, -111, -58, -107, 57, -10, -115, 3, 92, -55,
    115, -68, -98, 2, 24, 82, -24, 63, 98, 2, 126, 54 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548260582000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
