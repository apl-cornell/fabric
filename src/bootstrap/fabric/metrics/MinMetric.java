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
    
    public static final byte[] $classHash = new byte[] { 108, 28, 17, 81, -105,
    56, 48, 40, -76, -62, -44, -98, -56, -70, -64, -13, -30, 2, 25, -110, -11,
    -114, -4, 76, -30, -109, 118, -42, 40, 40, 39, 110 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748453000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
