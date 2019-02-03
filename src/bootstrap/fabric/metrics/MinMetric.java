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
                    fabric.metrics.DerivedMetric val$var229 = val;
                    fabric.worker.transaction.TransactionManager $tm236 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled239 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff237 = 1;
                    boolean $doBackoff238 = true;
                    boolean $retry232 = true;
                    boolean $keepReads233 = false;
                    $label230: for (boolean $commit231 = false; !$commit231; ) {
                        if ($backoffEnabled239) {
                            if ($doBackoff238) {
                                if ($backoff237 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff237));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e234) {
                                            
                                        }
                                    }
                                }
                                if ($backoff237 < 5000) $backoff237 *= 2;
                            }
                            $doBackoff238 = $backoff237 <= 32 || !$doBackoff238;
                        }
                        $commit231 = true;
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
                            catch (final fabric.worker.RetryException $e234) {
                                throw $e234;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e234) {
                                throw $e234;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e234) {
                                throw $e234;
                            }
                            catch (final Throwable $e234) {
                                $tm236.getCurrentLog().checkRetrySignal();
                                throw $e234;
                            }
                        }
                        catch (final fabric.worker.RetryException $e234) {
                            $commit231 = false;
                            continue $label230;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e234) {
                            $commit231 = false;
                            $retry232 = false;
                            $keepReads233 = $e234.keepReads;
                            if ($tm236.checkForStaleObjects()) {
                                $retry232 = true;
                                $keepReads233 = false;
                                continue $label230;
                            }
                            fabric.common.TransactionID $currentTid235 =
                              $tm236.getCurrentTid();
                            if ($e234.tid == null ||
                                  !$e234.tid.isDescendantOf($currentTid235)) {
                                throw $e234;
                            }
                            throw new fabric.worker.UserAbortException($e234);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e234) {
                            $commit231 = false;
                            fabric.common.TransactionID $currentTid235 =
                              $tm236.getCurrentTid();
                            if ($e234.tid.isDescendantOf($currentTid235))
                                continue $label230;
                            if ($currentTid235.parent != null) {
                                $retry232 = false;
                                throw $e234;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e234) {
                            $commit231 = false;
                            if ($tm236.checkForStaleObjects())
                                continue $label230;
                            $retry232 = false;
                            throw new fabric.worker.AbortException($e234);
                        }
                        finally {
                            if ($commit231) {
                                fabric.common.TransactionID $currentTid235 =
                                  $tm236.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e234) {
                                    $commit231 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e234) {
                                    $commit231 = false;
                                    $retry232 = false;
                                    $keepReads233 = $e234.keepReads;
                                    if ($tm236.checkForStaleObjects()) {
                                        $retry232 = true;
                                        $keepReads233 = false;
                                        continue $label230;
                                    }
                                    if ($e234.tid ==
                                          null ||
                                          !$e234.tid.isDescendantOf(
                                                       $currentTid235))
                                        throw $e234;
                                    throw new fabric.worker.UserAbortException(
                                            $e234);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e234) {
                                    $commit231 = false;
                                    $currentTid235 = $tm236.getCurrentTid();
                                    if ($currentTid235 != null) {
                                        if ($e234.tid.equals($currentTid235) ||
                                              !$e234.tid.isDescendantOf(
                                                           $currentTid235)) {
                                            throw $e234;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads233) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit231) {
                                { val = val$var229; }
                                if ($retry232) { continue $label230; }
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
                        fabric.metrics.DerivedMetric val$var251 = val;
                        int aggIdx$var252 = aggIdx;
                        fabric.worker.transaction.TransactionManager $tm259 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled262 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff260 = 1;
                        boolean $doBackoff261 = true;
                        boolean $retry255 = true;
                        boolean $keepReads256 = false;
                        $label253: for (boolean $commit254 = false; !$commit254;
                                        ) {
                            if ($backoffEnabled262) {
                                if ($doBackoff261) {
                                    if ($backoff260 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff260));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e257) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff260 < 5000) $backoff260 *= 2;
                                }
                                $doBackoff261 = $backoff260 <= 32 ||
                                                  !$doBackoff261;
                            }
                            $commit254 = true;
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
                                         RetryException $e257) {
                                    throw $e257;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e257) {
                                    throw $e257;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e257) {
                                    throw $e257;
                                }
                                catch (final Throwable $e257) {
                                    $tm259.getCurrentLog().checkRetrySignal();
                                    throw $e257;
                                }
                            }
                            catch (final fabric.worker.RetryException $e257) {
                                $commit254 = false;
                                continue $label253;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e257) {
                                $commit254 = false;
                                $retry255 = false;
                                $keepReads256 = $e257.keepReads;
                                if ($tm259.checkForStaleObjects()) {
                                    $retry255 = true;
                                    $keepReads256 = false;
                                    continue $label253;
                                }
                                fabric.common.TransactionID $currentTid258 =
                                  $tm259.getCurrentTid();
                                if ($e257.tid ==
                                      null ||
                                      !$e257.tid.isDescendantOf(
                                                   $currentTid258)) {
                                    throw $e257;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e257);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e257) {
                                $commit254 = false;
                                fabric.common.TransactionID $currentTid258 =
                                  $tm259.getCurrentTid();
                                if ($e257.tid.isDescendantOf($currentTid258))
                                    continue $label253;
                                if ($currentTid258.parent != null) {
                                    $retry255 = false;
                                    throw $e257;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e257) {
                                $commit254 = false;
                                if ($tm259.checkForStaleObjects())
                                    continue $label253;
                                $retry255 = false;
                                throw new fabric.worker.AbortException($e257);
                            }
                            finally {
                                if ($commit254) {
                                    fabric.common.TransactionID $currentTid258 =
                                      $tm259.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e257) {
                                        $commit254 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e257) {
                                        $commit254 = false;
                                        $retry255 = false;
                                        $keepReads256 = $e257.keepReads;
                                        if ($tm259.checkForStaleObjects()) {
                                            $retry255 = true;
                                            $keepReads256 = false;
                                            continue $label253;
                                        }
                                        if ($e257.tid ==
                                              null ||
                                              !$e257.tid.isDescendantOf(
                                                           $currentTid258))
                                            throw $e257;
                                        throw new fabric.worker.
                                                UserAbortException($e257);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e257) {
                                        $commit254 = false;
                                        $currentTid258 = $tm259.getCurrentTid();
                                        if ($currentTid258 != null) {
                                            if ($e257.tid.equals(
                                                            $currentTid258) ||
                                                  !$e257.tid.
                                                  isDescendantOf(
                                                    $currentTid258)) {
                                                throw $e257;
                                            }
                                        }
                                    }
                                } else if ($keepReads256) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit254) {
                                    {
                                        val = val$var251;
                                        aggIdx = aggIdx$var252;
                                    }
                                    if ($retry255) { continue $label253; }
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
                    fabric.metrics.DerivedMetric val$var263 = val;
                    fabric.worker.transaction.TransactionManager $tm270 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled273 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff271 = 1;
                    boolean $doBackoff272 = true;
                    boolean $retry266 = true;
                    boolean $keepReads267 = false;
                    $label264: for (boolean $commit265 = false; !$commit265; ) {
                        if ($backoffEnabled273) {
                            if ($doBackoff272) {
                                if ($backoff271 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
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
                            $doBackoff272 = $backoff271 <= 32 || !$doBackoff272;
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
                            catch (final fabric.worker.RetryException $e268) {
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
                            if ($e268.tid == null ||
                                  !$e268.tid.isDescendantOf($currentTid269)) {
                                throw $e268;
                            }
                            throw new fabric.worker.UserAbortException($e268);
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
                                    throw new fabric.worker.UserAbortException(
                                            $e268);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e268) {
                                    $commit265 = false;
                                    $currentTid269 = $tm270.getCurrentTid();
                                    if ($currentTid269 != null) {
                                        if ($e268.tid.equals($currentTid269) ||
                                              !$e268.tid.isDescendantOf(
                                                           $currentTid269)) {
                                            throw $e268;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads267) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit265) {
                                { val = val$var263; }
                                if ($retry266) { continue $label264; }
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
                    fabric.worker.metrics.treaties.statements.ThresholdStatement.
                        create(rate, base));
            }
            return fabric.worker.metrics.treaties.enforcement.WitnessPolicy.
              create(witnesses);
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
    
    public static final byte[] $classHash = new byte[] { 3, -78, -52, -87, 86,
    -108, -110, 113, 80, 108, -41, -107, 47, -125, 106, 21, 111, 68, -76, 102,
    -47, -13, 111, -111, -51, 40, 126, 66, 79, 72, -63, -79 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549232372000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
