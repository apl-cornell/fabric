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
    
    public double computePresetNTerm();
    
    public double computeValue(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeVelocity(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoise(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoiseTerm(fabric.worker.metrics.StatsMap weakStats);
    
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
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result = java.lang.Math.max(result, term(i).getPresetN());
            }
            return result;
        }
        
        public double computePresetNTerm() {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result = java.lang.Math.max(result, term(i).getPresetNTerm());
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
        
        public double computeNoiseTerm(
          fabric.worker.metrics.StatsMap weakStats) {
            double noiseTerm = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                noiseTerm = java.lang.Math.max(noiseTerm,
                                               term(i).noiseTerm(weakStats));
            }
            return noiseTerm;
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
                    fabric.metrics.DerivedMetric val$var154 = val;
                    fabric.worker.transaction.TransactionManager $tm161 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled164 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff162 = 1;
                    boolean $doBackoff163 = true;
                    boolean $retry157 = true;
                    boolean $keepReads158 = false;
                    $label155: for (boolean $commit156 = false; !$commit156; ) {
                        if ($backoffEnabled164) {
                            if ($doBackoff163) {
                                if ($backoff162 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff162));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e159) {
                                            
                                        }
                                    }
                                }
                                if ($backoff162 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff162 =
                                      java.lang.Math.
                                        min(
                                          $backoff162 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff163 = $backoff162 <= 32 || !$doBackoff163;
                        }
                        $commit156 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e159) {
                            $commit156 = false;
                            continue $label155;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e159) {
                            $commit156 = false;
                            $retry157 = false;
                            $keepReads158 = $e159.keepReads;
                            fabric.common.TransactionID $currentTid160 =
                              $tm161.getCurrentTid();
                            if ($e159.tid == null ||
                                  !$e159.tid.isDescendantOf($currentTid160)) {
                                throw $e159;
                            }
                            throw new fabric.worker.UserAbortException($e159);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e159) {
                            $commit156 = false;
                            fabric.common.TransactionID $currentTid160 =
                              $tm161.getCurrentTid();
                            if ($e159.tid.isDescendantOf($currentTid160))
                                continue $label155;
                            if ($currentTid160.parent != null) {
                                $retry157 = false;
                                throw $e159;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e159) {
                            $commit156 = false;
                            $retry157 = false;
                            if ($tm161.inNestedTxn()) { $keepReads158 = true; }
                            throw $e159;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid160 =
                              $tm161.getCurrentTid();
                            if ($commit156) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e159) {
                                    $commit156 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e159) {
                                    $commit156 = false;
                                    $retry157 = false;
                                    $keepReads158 = $e159.keepReads;
                                    if ($e159.tid ==
                                          null ||
                                          !$e159.tid.isDescendantOf(
                                                       $currentTid160))
                                        throw $e159;
                                    throw new fabric.worker.UserAbortException(
                                            $e159);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e159) {
                                    $commit156 = false;
                                    $currentTid160 = $tm161.getCurrentTid();
                                    if ($currentTid160 != null) {
                                        if ($e159.tid.equals($currentTid160) ||
                                              !$e159.tid.isDescendantOf(
                                                           $currentTid160)) {
                                            throw $e159;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm161.inNestedTxn() &&
                                      $tm161.checkForStaleObjects()) {
                                    $retry157 = true;
                                    $keepReads158 = false;
                                }
                                if ($keepReads158) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e159) {
                                        $currentTid160 = $tm161.getCurrentTid();
                                        if ($currentTid160 != null &&
                                              ($e159.tid.equals($currentTid160) || !$e159.tid.isDescendantOf($currentTid160))) {
                                            throw $e159;
                                        } else {
                                            $retry157 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit156) {
                                { val = val$var154; }
                                if ($retry157) { continue $label155; }
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
                    fabric.metrics.DerivedMetric val$var165 = val;
                    fabric.worker.transaction.TransactionManager $tm172 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled175 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff173 = 1;
                    boolean $doBackoff174 = true;
                    boolean $retry168 = true;
                    boolean $keepReads169 = false;
                    $label166: for (boolean $commit167 = false; !$commit167; ) {
                        if ($backoffEnabled175) {
                            if ($doBackoff174) {
                                if ($backoff173 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff173));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e170) {
                                            
                                        }
                                    }
                                }
                                if ($backoff173 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff173 =
                                      java.lang.Math.
                                        min(
                                          $backoff173 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff174 = $backoff173 <= 32 || !$doBackoff174;
                        }
                        $commit167 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e170) {
                            $commit167 = false;
                            continue $label166;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e170) {
                            $commit167 = false;
                            $retry168 = false;
                            $keepReads169 = $e170.keepReads;
                            fabric.common.TransactionID $currentTid171 =
                              $tm172.getCurrentTid();
                            if ($e170.tid == null ||
                                  !$e170.tid.isDescendantOf($currentTid171)) {
                                throw $e170;
                            }
                            throw new fabric.worker.UserAbortException($e170);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e170) {
                            $commit167 = false;
                            fabric.common.TransactionID $currentTid171 =
                              $tm172.getCurrentTid();
                            if ($e170.tid.isDescendantOf($currentTid171))
                                continue $label166;
                            if ($currentTid171.parent != null) {
                                $retry168 = false;
                                throw $e170;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e170) {
                            $commit167 = false;
                            $retry168 = false;
                            if ($tm172.inNestedTxn()) { $keepReads169 = true; }
                            throw $e170;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid171 =
                              $tm172.getCurrentTid();
                            if ($commit167) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e170) {
                                    $commit167 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e170) {
                                    $commit167 = false;
                                    $retry168 = false;
                                    $keepReads169 = $e170.keepReads;
                                    if ($e170.tid ==
                                          null ||
                                          !$e170.tid.isDescendantOf(
                                                       $currentTid171))
                                        throw $e170;
                                    throw new fabric.worker.UserAbortException(
                                            $e170);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e170) {
                                    $commit167 = false;
                                    $currentTid171 = $tm172.getCurrentTid();
                                    if ($currentTid171 != null) {
                                        if ($e170.tid.equals($currentTid171) ||
                                              !$e170.tid.isDescendantOf(
                                                           $currentTid171)) {
                                            throw $e170;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm172.inNestedTxn() &&
                                      $tm172.checkForStaleObjects()) {
                                    $retry168 = true;
                                    $keepReads169 = false;
                                }
                                if ($keepReads169) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e170) {
                                        $currentTid171 = $tm172.getCurrentTid();
                                        if ($currentTid171 != null &&
                                              ($e170.tid.equals($currentTid171) || !$e170.tid.isDescendantOf($currentTid171))) {
                                            throw $e170;
                                        } else {
                                            $retry168 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit167) {
                                { val = val$var165; }
                                if ($retry168) { continue $label166; }
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
                        fabric.metrics.DerivedMetric val$var176 = val;
                        int aggIdx$var177 = aggIdx;
                        fabric.worker.transaction.TransactionManager $tm184 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled187 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff185 = 1;
                        boolean $doBackoff186 = true;
                        boolean $retry180 = true;
                        boolean $keepReads181 = false;
                        $label178: for (boolean $commit179 = false; !$commit179;
                                        ) {
                            if ($backoffEnabled187) {
                                if ($doBackoff186) {
                                    if ($backoff185 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff185));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e182) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff185 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff185 =
                                          java.lang.Math.
                                            min(
                                              $backoff185 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff186 = $backoff185 <= 32 ||
                                                  !$doBackoff186;
                            }
                            $commit179 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    newTerms);
                            }
                            catch (final fabric.worker.RetryException $e182) {
                                $commit179 = false;
                                continue $label178;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e182) {
                                $commit179 = false;
                                $retry180 = false;
                                $keepReads181 = $e182.keepReads;
                                fabric.common.TransactionID $currentTid183 =
                                  $tm184.getCurrentTid();
                                if ($e182.tid ==
                                      null ||
                                      !$e182.tid.isDescendantOf(
                                                   $currentTid183)) {
                                    throw $e182;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e182);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e182) {
                                $commit179 = false;
                                fabric.common.TransactionID $currentTid183 =
                                  $tm184.getCurrentTid();
                                if ($e182.tid.isDescendantOf($currentTid183))
                                    continue $label178;
                                if ($currentTid183.parent != null) {
                                    $retry180 = false;
                                    throw $e182;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e182) {
                                $commit179 = false;
                                $retry180 = false;
                                if ($tm184.inNestedTxn()) {
                                    $keepReads181 = true;
                                }
                                throw $e182;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid183 =
                                  $tm184.getCurrentTid();
                                if ($commit179) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e182) {
                                        $commit179 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e182) {
                                        $commit179 = false;
                                        $retry180 = false;
                                        $keepReads181 = $e182.keepReads;
                                        if ($e182.tid ==
                                              null ||
                                              !$e182.tid.isDescendantOf(
                                                           $currentTid183))
                                            throw $e182;
                                        throw new fabric.worker.
                                                UserAbortException($e182);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e182) {
                                        $commit179 = false;
                                        $currentTid183 = $tm184.getCurrentTid();
                                        if ($currentTid183 != null) {
                                            if ($e182.tid.equals(
                                                            $currentTid183) ||
                                                  !$e182.tid.
                                                  isDescendantOf(
                                                    $currentTid183)) {
                                                throw $e182;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm184.inNestedTxn() &&
                                          $tm184.checkForStaleObjects()) {
                                        $retry180 = true;
                                        $keepReads181 = false;
                                    }
                                    if ($keepReads181) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e182) {
                                            $currentTid183 = $tm184.getCurrentTid();
                                            if ($currentTid183 != null &&
                                                  ($e182.tid.equals($currentTid183) || !$e182.tid.isDescendantOf($currentTid183))) {
                                                throw $e182;
                                            } else {
                                                $retry180 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit179) {
                                    {
                                        val = val$var176;
                                        aggIdx = aggIdx$var177;
                                    }
                                    if ($retry180) { continue $label178; }
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
                    fabric.metrics.DerivedMetric val$var188 = val;
                    fabric.worker.transaction.TransactionManager $tm195 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled198 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff196 = 1;
                    boolean $doBackoff197 = true;
                    boolean $retry191 = true;
                    boolean $keepReads192 = false;
                    $label189: for (boolean $commit190 = false; !$commit190; ) {
                        if ($backoffEnabled198) {
                            if ($doBackoff197) {
                                if ($backoff196 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff196));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e193) {
                                            
                                        }
                                    }
                                }
                                if ($backoff196 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff196 =
                                      java.lang.Math.
                                        min(
                                          $backoff196 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff197 = $backoff196 <= 32 || !$doBackoff197;
                        }
                        $commit190 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e193) {
                            $commit190 = false;
                            continue $label189;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e193) {
                            $commit190 = false;
                            $retry191 = false;
                            $keepReads192 = $e193.keepReads;
                            fabric.common.TransactionID $currentTid194 =
                              $tm195.getCurrentTid();
                            if ($e193.tid == null ||
                                  !$e193.tid.isDescendantOf($currentTid194)) {
                                throw $e193;
                            }
                            throw new fabric.worker.UserAbortException($e193);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e193) {
                            $commit190 = false;
                            fabric.common.TransactionID $currentTid194 =
                              $tm195.getCurrentTid();
                            if ($e193.tid.isDescendantOf($currentTid194))
                                continue $label189;
                            if ($currentTid194.parent != null) {
                                $retry191 = false;
                                throw $e193;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e193) {
                            $commit190 = false;
                            $retry191 = false;
                            if ($tm195.inNestedTxn()) { $keepReads192 = true; }
                            throw $e193;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid194 =
                              $tm195.getCurrentTid();
                            if ($commit190) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e193) {
                                    $commit190 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e193) {
                                    $commit190 = false;
                                    $retry191 = false;
                                    $keepReads192 = $e193.keepReads;
                                    if ($e193.tid ==
                                          null ||
                                          !$e193.tid.isDescendantOf(
                                                       $currentTid194))
                                        throw $e193;
                                    throw new fabric.worker.UserAbortException(
                                            $e193);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e193) {
                                    $commit190 = false;
                                    $currentTid194 = $tm195.getCurrentTid();
                                    if ($currentTid194 != null) {
                                        if ($e193.tid.equals($currentTid194) ||
                                              !$e193.tid.isDescendantOf(
                                                           $currentTid194)) {
                                            throw $e193;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm195.inNestedTxn() &&
                                      $tm195.checkForStaleObjects()) {
                                    $retry191 = true;
                                    $keepReads192 = false;
                                }
                                if ($keepReads192) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e193) {
                                        $currentTid194 = $tm195.getCurrentTid();
                                        if ($currentTid194 != null &&
                                              ($e193.tid.equals($currentTid194) || !$e193.tid.isDescendantOf($currentTid194))) {
                                            throw $e193;
                                        } else {
                                            $retry191 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit190) {
                                { val = val$var188; }
                                if ($retry191) { continue $label189; }
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
    
    public static final byte[] $classHash = new byte[] { -53, -1, 18, -5, -111,
    -97, 14, -113, -94, 115, 107, -92, 20, 103, -97, 80, -114, -53, 46, 70, 116,
    36, -125, 99, -41, -128, 65, -11, -112, -32, -8, -43 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556815298000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
