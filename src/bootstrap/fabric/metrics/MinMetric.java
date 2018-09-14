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
                            throw new fabric.worker.UserAbortException();
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
                                            );
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
                            throw new fabric.worker.UserAbortException();
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
                                            );
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
                                throw new fabric.worker.UserAbortException();
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
                                                UserAbortException();
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
                            throw new fabric.worker.UserAbortException();
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
                                            );
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
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.metrics.treaties.TreatySet treaties,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, treaties, labelStore,
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
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.metrics.treaties.TreatySet treaties,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, treaties, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { -12, 45, 45, -12, 87,
    31, 64, 122, -57, -46, -85, 69, -95, -76, -87, 28, -35, 11, -36, -37, -119,
    24, -46, 116, 116, -50, -40, -114, -38, 59, 56, -81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1536940425000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
