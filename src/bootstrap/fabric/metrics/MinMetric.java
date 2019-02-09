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
    
    public static final byte[] $classHash = new byte[] { -89, -4, -114, 20,
    -110, -82, -103, -96, 70, 126, 34, -56, 119, -47, 60, -25, 24, 60, 91, -51,
    -90, -97, 63, 115, 5, 13, -1, 63, -1, 22, 108, 105 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748453000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
