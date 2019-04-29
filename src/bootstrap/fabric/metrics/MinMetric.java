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
      thresholdPolicy(double rate, double base, long time,
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
                    long $backoff193 = 1;
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
                                if ($backoff193 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff193 =
                                      java.lang.Math.
                                        min(
                                          $backoff193 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff194 = $backoff193 <= 32 || !$doBackoff194;
                        }
                        $commit187 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
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
                            $retry188 = false;
                            if ($tm192.inNestedTxn()) { $keepReads189 = true; }
                            throw $e190;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid191 =
                              $tm192.getCurrentTid();
                            if ($commit187) {
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
                            } else {
                                if (!$tm192.inNestedTxn() &&
                                      $tm192.checkForStaleObjects()) {
                                    $retry188 = true;
                                    $keepReads189 = false;
                                }
                                if ($keepReads189) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e190) {
                                        $currentTid191 = $tm192.getCurrentTid();
                                        if ($currentTid191 != null &&
                                              ($e190.tid.equals($currentTid191) || !$e190.tid.isDescendantOf($currentTid191))) {
                                            throw $e190;
                                        } else {
                                            $retry188 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
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
                    long $backoff204 = 1;
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
                                if ($backoff204 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff204 =
                                      java.lang.Math.
                                        min(
                                          $backoff204 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff205 = $backoff204 <= 32 || !$doBackoff205;
                        }
                        $commit198 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
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
                            $retry199 = false;
                            if ($tm203.inNestedTxn()) { $keepReads200 = true; }
                            throw $e201;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid202 =
                              $tm203.getCurrentTid();
                            if ($commit198) {
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
                            } else {
                                if (!$tm203.inNestedTxn() &&
                                      $tm203.checkForStaleObjects()) {
                                    $retry199 = true;
                                    $keepReads200 = false;
                                }
                                if ($keepReads200) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e201) {
                                        $currentTid202 = $tm203.getCurrentTid();
                                        if ($currentTid202 != null &&
                                              ($e201.tid.equals($currentTid202) || !$e201.tid.isDescendantOf($currentTid202))) {
                                            throw $e201;
                                        } else {
                                            $retry199 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
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
                        long $backoff216 = 1;
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
                                    if ($backoff216 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff216 =
                                          java.lang.Math.
                                            min(
                                              $backoff216 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff217 = $backoff216 <= 32 ||
                                                  !$doBackoff217;
                            }
                            $commit210 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    newTerms);
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
                                $retry211 = false;
                                if ($tm215.inNestedTxn()) {
                                    $keepReads212 = true;
                                }
                                throw $e213;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid214 =
                                  $tm215.getCurrentTid();
                                if ($commit210) {
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
                                } else {
                                    if (!$tm215.inNestedTxn() &&
                                          $tm215.checkForStaleObjects()) {
                                        $retry211 = true;
                                        $keepReads212 = false;
                                    }
                                    if ($keepReads212) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e213) {
                                            $currentTid214 = $tm215.getCurrentTid();
                                            if ($currentTid214 != null &&
                                                  ($e213.tid.equals($currentTid214) || !$e213.tid.isDescendantOf($currentTid214))) {
                                                throw $e213;
                                            } else {
                                                $retry211 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
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
                    long $backoff227 = 1;
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
                                if ($backoff227 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff227 =
                                      java.lang.Math.
                                        min(
                                          $backoff227 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff228 = $backoff227 <= 32 || !$doBackoff228;
                        }
                        $commit221 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
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
                            $retry222 = false;
                            if ($tm226.inNestedTxn()) { $keepReads223 = true; }
                            throw $e224;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid225 =
                              $tm226.getCurrentTid();
                            if ($commit221) {
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
                            } else {
                                if (!$tm226.inNestedTxn() &&
                                      $tm226.checkForStaleObjects()) {
                                    $retry222 = true;
                                    $keepReads223 = false;
                                }
                                if ($keepReads223) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e224) {
                                        $currentTid225 = $tm226.getCurrentTid();
                                        if ($currentTid225 != null &&
                                              ($e224.tid.equals($currentTid225) || !$e224.tid.isDescendantOf($currentTid225))) {
                                            throw $e224;
                                        } else {
                                            $retry222 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
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
          thresholdPolicy(double rate, double base, long time,
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
    
    public static final byte[] $classHash = new byte[] { 7, 67, -120, 32, 33,
    -108, -78, -101, -32, -24, 8, -52, 2, -104, -97, 64, 92, -102, -92, 105, 51,
    -101, 100, 74, -84, 31, -19, 105, -14, -50, -104, 17 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556553199000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
