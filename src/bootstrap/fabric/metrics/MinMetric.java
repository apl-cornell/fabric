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
                    fabric.metrics.DerivedMetric val$var185 = val;
                    fabric.worker.transaction.TransactionManager $tm192 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled195 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff193 = 1;
                    boolean $doBackoff194 = true;
                    boolean $retry188 = true;
                    boolean $keepReads189 = false;
                    $label186: for (boolean $commit187 = false; !$commit187; ) {
                        if ($backoffEnabled195) {
                            if ($doBackoff194) {
                                if ($backoff193 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff193));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e190) {
                                            
                                        }
                                    }
                                }
                                if ($backoff193 < 5000) $backoff193 *= 2;
                            }
                            $doBackoff194 = $backoff193 <= 32 || !$doBackoff194;
                        }
                        $commit187 = true;
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
                            catch (final fabric.worker.RetryException $e190) {
                                throw $e190;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e190) {
                                throw $e190;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e190) {
                                throw $e190;
                            }
                            catch (final Throwable $e190) {
                                $tm192.getCurrentLog().checkRetrySignal();
                                throw $e190;
                            }
                        }
                        catch (final fabric.worker.RetryException $e190) {
                            $commit187 = false;
                            continue $label186;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e190) {
                            $commit187 = false;
                            $retry188 = false;
                            $keepReads189 = $e190.keepReads;
                            if ($tm192.checkForStaleObjects()) {
                                $retry188 = true;
                                $keepReads189 = false;
                                continue $label186;
                            }
                            fabric.common.TransactionID $currentTid191 =
                              $tm192.getCurrentTid();
                            if ($e190.tid == null ||
                                  !$e190.tid.isDescendantOf($currentTid191)) {
                                throw $e190;
                            }
                            throw new fabric.worker.UserAbortException($e190);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e190) {
                            $commit187 = false;
                            fabric.common.TransactionID $currentTid191 =
                              $tm192.getCurrentTid();
                            if ($e190.tid.isDescendantOf($currentTid191))
                                continue $label186;
                            if ($currentTid191.parent != null) {
                                $retry188 = false;
                                throw $e190;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e190) {
                            $commit187 = false;
                            if ($tm192.checkForStaleObjects())
                                continue $label186;
                            $retry188 = false;
                            throw new fabric.worker.AbortException($e190);
                        }
                        finally {
                            if ($commit187) {
                                fabric.common.TransactionID $currentTid191 =
                                  $tm192.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e190) {
                                    $commit187 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e190) {
                                    $commit187 = false;
                                    $retry188 = false;
                                    $keepReads189 = $e190.keepReads;
                                    if ($tm192.checkForStaleObjects()) {
                                        $retry188 = true;
                                        $keepReads189 = false;
                                        continue $label186;
                                    }
                                    if ($e190.tid ==
                                          null ||
                                          !$e190.tid.isDescendantOf(
                                                       $currentTid191))
                                        throw $e190;
                                    throw new fabric.worker.UserAbortException(
                                            $e190);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e190) {
                                    $commit187 = false;
                                    $currentTid191 = $tm192.getCurrentTid();
                                    if ($currentTid191 != null) {
                                        if ($e190.tid.equals($currentTid191) ||
                                              !$e190.tid.isDescendantOf(
                                                           $currentTid191)) {
                                            throw $e190;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads189) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit187) {
                                { val = val$var185; }
                                if ($retry188) { continue $label186; }
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
                    fabric.metrics.DerivedMetric val$var196 = val;
                    fabric.worker.transaction.TransactionManager $tm203 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled206 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff204 = 1;
                    boolean $doBackoff205 = true;
                    boolean $retry199 = true;
                    boolean $keepReads200 = false;
                    $label197: for (boolean $commit198 = false; !$commit198; ) {
                        if ($backoffEnabled206) {
                            if ($doBackoff205) {
                                if ($backoff204 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff204));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e201) {
                                            
                                        }
                                    }
                                }
                                if ($backoff204 < 5000) $backoff204 *= 2;
                            }
                            $doBackoff205 = $backoff204 <= 32 || !$doBackoff205;
                        }
                        $commit198 = true;
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
                            catch (final fabric.worker.RetryException $e201) {
                                throw $e201;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e201) {
                                throw $e201;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e201) {
                                throw $e201;
                            }
                            catch (final Throwable $e201) {
                                $tm203.getCurrentLog().checkRetrySignal();
                                throw $e201;
                            }
                        }
                        catch (final fabric.worker.RetryException $e201) {
                            $commit198 = false;
                            continue $label197;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e201) {
                            $commit198 = false;
                            $retry199 = false;
                            $keepReads200 = $e201.keepReads;
                            if ($tm203.checkForStaleObjects()) {
                                $retry199 = true;
                                $keepReads200 = false;
                                continue $label197;
                            }
                            fabric.common.TransactionID $currentTid202 =
                              $tm203.getCurrentTid();
                            if ($e201.tid == null ||
                                  !$e201.tid.isDescendantOf($currentTid202)) {
                                throw $e201;
                            }
                            throw new fabric.worker.UserAbortException($e201);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e201) {
                            $commit198 = false;
                            fabric.common.TransactionID $currentTid202 =
                              $tm203.getCurrentTid();
                            if ($e201.tid.isDescendantOf($currentTid202))
                                continue $label197;
                            if ($currentTid202.parent != null) {
                                $retry199 = false;
                                throw $e201;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e201) {
                            $commit198 = false;
                            if ($tm203.checkForStaleObjects())
                                continue $label197;
                            $retry199 = false;
                            throw new fabric.worker.AbortException($e201);
                        }
                        finally {
                            if ($commit198) {
                                fabric.common.TransactionID $currentTid202 =
                                  $tm203.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e201) {
                                    $commit198 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e201) {
                                    $commit198 = false;
                                    $retry199 = false;
                                    $keepReads200 = $e201.keepReads;
                                    if ($tm203.checkForStaleObjects()) {
                                        $retry199 = true;
                                        $keepReads200 = false;
                                        continue $label197;
                                    }
                                    if ($e201.tid ==
                                          null ||
                                          !$e201.tid.isDescendantOf(
                                                       $currentTid202))
                                        throw $e201;
                                    throw new fabric.worker.UserAbortException(
                                            $e201);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e201) {
                                    $commit198 = false;
                                    $currentTid202 = $tm203.getCurrentTid();
                                    if ($currentTid202 != null) {
                                        if ($e201.tid.equals($currentTid202) ||
                                              !$e201.tid.isDescendantOf(
                                                           $currentTid202)) {
                                            throw $e201;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads200) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit198) {
                                { val = val$var196; }
                                if ($retry199) { continue $label197; }
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
                        fabric.metrics.DerivedMetric val$var207 = val;
                        int aggIdx$var208 = aggIdx;
                        fabric.worker.transaction.TransactionManager $tm215 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled218 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff216 = 1;
                        boolean $doBackoff217 = true;
                        boolean $retry211 = true;
                        boolean $keepReads212 = false;
                        $label209: for (boolean $commit210 = false; !$commit210;
                                        ) {
                            if ($backoffEnabled218) {
                                if ($doBackoff217) {
                                    if ($backoff216 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff216));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e213) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff216 < 5000) $backoff216 *= 2;
                                }
                                $doBackoff217 = $backoff216 <= 32 ||
                                                  !$doBackoff217;
                            }
                            $commit210 = true;
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
                                         RetryException $e213) {
                                    throw $e213;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e213) {
                                    throw $e213;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e213) {
                                    throw $e213;
                                }
                                catch (final Throwable $e213) {
                                    $tm215.getCurrentLog().checkRetrySignal();
                                    throw $e213;
                                }
                            }
                            catch (final fabric.worker.RetryException $e213) {
                                $commit210 = false;
                                continue $label209;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e213) {
                                $commit210 = false;
                                $retry211 = false;
                                $keepReads212 = $e213.keepReads;
                                if ($tm215.checkForStaleObjects()) {
                                    $retry211 = true;
                                    $keepReads212 = false;
                                    continue $label209;
                                }
                                fabric.common.TransactionID $currentTid214 =
                                  $tm215.getCurrentTid();
                                if ($e213.tid ==
                                      null ||
                                      !$e213.tid.isDescendantOf(
                                                   $currentTid214)) {
                                    throw $e213;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e213);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e213) {
                                $commit210 = false;
                                fabric.common.TransactionID $currentTid214 =
                                  $tm215.getCurrentTid();
                                if ($e213.tid.isDescendantOf($currentTid214))
                                    continue $label209;
                                if ($currentTid214.parent != null) {
                                    $retry211 = false;
                                    throw $e213;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e213) {
                                $commit210 = false;
                                if ($tm215.checkForStaleObjects())
                                    continue $label209;
                                $retry211 = false;
                                throw new fabric.worker.AbortException($e213);
                            }
                            finally {
                                if ($commit210) {
                                    fabric.common.TransactionID $currentTid214 =
                                      $tm215.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e213) {
                                        $commit210 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e213) {
                                        $commit210 = false;
                                        $retry211 = false;
                                        $keepReads212 = $e213.keepReads;
                                        if ($tm215.checkForStaleObjects()) {
                                            $retry211 = true;
                                            $keepReads212 = false;
                                            continue $label209;
                                        }
                                        if ($e213.tid ==
                                              null ||
                                              !$e213.tid.isDescendantOf(
                                                           $currentTid214))
                                            throw $e213;
                                        throw new fabric.worker.
                                                UserAbortException($e213);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e213) {
                                        $commit210 = false;
                                        $currentTid214 = $tm215.getCurrentTid();
                                        if ($currentTid214 != null) {
                                            if ($e213.tid.equals(
                                                            $currentTid214) ||
                                                  !$e213.tid.
                                                  isDescendantOf(
                                                    $currentTid214)) {
                                                throw $e213;
                                            }
                                        }
                                    }
                                } else if ($keepReads212) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit210) {
                                    {
                                        val = val$var207;
                                        aggIdx = aggIdx$var208;
                                    }
                                    if ($retry211) { continue $label209; }
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
                    fabric.metrics.DerivedMetric val$var219 = val;
                    fabric.worker.transaction.TransactionManager $tm226 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled229 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff227 = 1;
                    boolean $doBackoff228 = true;
                    boolean $retry222 = true;
                    boolean $keepReads223 = false;
                    $label220: for (boolean $commit221 = false; !$commit221; ) {
                        if ($backoffEnabled229) {
                            if ($doBackoff228) {
                                if ($backoff227 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff227));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e224) {
                                            
                                        }
                                    }
                                }
                                if ($backoff227 < 5000) $backoff227 *= 2;
                            }
                            $doBackoff228 = $backoff227 <= 32 || !$doBackoff228;
                        }
                        $commit221 = true;
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
                            catch (final fabric.worker.RetryException $e224) {
                                throw $e224;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e224) {
                                throw $e224;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e224) {
                                throw $e224;
                            }
                            catch (final Throwable $e224) {
                                $tm226.getCurrentLog().checkRetrySignal();
                                throw $e224;
                            }
                        }
                        catch (final fabric.worker.RetryException $e224) {
                            $commit221 = false;
                            continue $label220;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e224) {
                            $commit221 = false;
                            $retry222 = false;
                            $keepReads223 = $e224.keepReads;
                            if ($tm226.checkForStaleObjects()) {
                                $retry222 = true;
                                $keepReads223 = false;
                                continue $label220;
                            }
                            fabric.common.TransactionID $currentTid225 =
                              $tm226.getCurrentTid();
                            if ($e224.tid == null ||
                                  !$e224.tid.isDescendantOf($currentTid225)) {
                                throw $e224;
                            }
                            throw new fabric.worker.UserAbortException($e224);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e224) {
                            $commit221 = false;
                            fabric.common.TransactionID $currentTid225 =
                              $tm226.getCurrentTid();
                            if ($e224.tid.isDescendantOf($currentTid225))
                                continue $label220;
                            if ($currentTid225.parent != null) {
                                $retry222 = false;
                                throw $e224;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e224) {
                            $commit221 = false;
                            if ($tm226.checkForStaleObjects())
                                continue $label220;
                            $retry222 = false;
                            throw new fabric.worker.AbortException($e224);
                        }
                        finally {
                            if ($commit221) {
                                fabric.common.TransactionID $currentTid225 =
                                  $tm226.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e224) {
                                    $commit221 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e224) {
                                    $commit221 = false;
                                    $retry222 = false;
                                    $keepReads223 = $e224.keepReads;
                                    if ($tm226.checkForStaleObjects()) {
                                        $retry222 = true;
                                        $keepReads223 = false;
                                        continue $label220;
                                    }
                                    if ($e224.tid ==
                                          null ||
                                          !$e224.tid.isDescendantOf(
                                                       $currentTid225))
                                        throw $e224;
                                    throw new fabric.worker.UserAbortException(
                                            $e224);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e224) {
                                    $commit221 = false;
                                    $currentTid225 = $tm226.getCurrentTid();
                                    if ($currentTid225 != null) {
                                        if ($e224.tid.equals($currentTid225) ||
                                              !$e224.tid.isDescendantOf(
                                                           $currentTid225)) {
                                            throw $e224;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads223) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit221) {
                                { val = val$var219; }
                                if ($retry222) { continue $label220; }
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
    
    public static final byte[] $classHash = new byte[] { -108, -16, 27, -10, 75,
    90, 104, -106, -105, -105, -76, 53, -113, 107, 28, -17, 5, -114, 46, 92,
    -69, -84, -68, 12, 105, -104, -49, 48, 35, -27, -127, -42 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1550000445000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
