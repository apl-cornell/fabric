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
                    fabric.metrics.DerivedMetric val$var188 = val;
                    fabric.worker.transaction.TransactionManager $tm194 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled197 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff195 = 1;
                    boolean $doBackoff196 = true;
                    boolean $retry191 = true;
                    $label189: for (boolean $commit190 = false; !$commit190; ) {
                        if ($backoffEnabled197) {
                            if ($doBackoff196) {
                                if ($backoff195 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff195);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e192) {
                                            
                                        }
                                    }
                                }
                                if ($backoff195 < 5000) $backoff195 *= 2;
                            }
                            $doBackoff196 = $backoff195 <= 32 || !$doBackoff196;
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
                        catch (final fabric.worker.RetryException $e192) {
                            $commit190 = false;
                            continue $label189;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e192) {
                            $commit190 = false;
                            fabric.common.TransactionID $currentTid193 =
                              $tm194.getCurrentTid();
                            if ($e192.tid.isDescendantOf($currentTid193))
                                continue $label189;
                            if ($currentTid193.parent != null) {
                                $retry191 = false;
                                throw $e192;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e192) {
                            $commit190 = false;
                            if ($tm194.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid193 =
                              $tm194.getCurrentTid();
                            if ($e192.tid.isDescendantOf($currentTid193)) {
                                $retry191 = true;
                            }
                            else if ($currentTid193.parent != null) {
                                $retry191 = false;
                                throw $e192;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e192) {
                            $commit190 = false;
                            if ($tm194.checkForStaleObjects())
                                continue $label189;
                            $retry191 = false;
                            throw new fabric.worker.AbortException($e192);
                        }
                        finally {
                            if ($commit190) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e192) {
                                    $commit190 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e192) {
                                    $commit190 = false;
                                    fabric.common.TransactionID $currentTid193 =
                                      $tm194.getCurrentTid();
                                    if ($currentTid193 != null) {
                                        if ($e192.tid.equals($currentTid193) ||
                                              !$e192.tid.isDescendantOf(
                                                           $currentTid193)) {
                                            throw $e192;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit190 && $retry191) {
                                { val = val$var188; }
                                continue $label189;
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
                    fabric.metrics.DerivedMetric val$var198 = val;
                    fabric.worker.transaction.TransactionManager $tm204 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled207 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff205 = 1;
                    boolean $doBackoff206 = true;
                    boolean $retry201 = true;
                    $label199: for (boolean $commit200 = false; !$commit200; ) {
                        if ($backoffEnabled207) {
                            if ($doBackoff206) {
                                if ($backoff205 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff205);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e202) {
                                            
                                        }
                                    }
                                }
                                if ($backoff205 < 5000) $backoff205 *= 2;
                            }
                            $doBackoff206 = $backoff205 <= 32 || !$doBackoff206;
                        }
                        $commit200 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e202) {
                            $commit200 = false;
                            continue $label199;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e202) {
                            $commit200 = false;
                            fabric.common.TransactionID $currentTid203 =
                              $tm204.getCurrentTid();
                            if ($e202.tid.isDescendantOf($currentTid203))
                                continue $label199;
                            if ($currentTid203.parent != null) {
                                $retry201 = false;
                                throw $e202;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e202) {
                            $commit200 = false;
                            if ($tm204.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid203 =
                              $tm204.getCurrentTid();
                            if ($e202.tid.isDescendantOf($currentTid203)) {
                                $retry201 = true;
                            }
                            else if ($currentTid203.parent != null) {
                                $retry201 = false;
                                throw $e202;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e202) {
                            $commit200 = false;
                            if ($tm204.checkForStaleObjects())
                                continue $label199;
                            $retry201 = false;
                            throw new fabric.worker.AbortException($e202);
                        }
                        finally {
                            if ($commit200) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e202) {
                                    $commit200 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e202) {
                                    $commit200 = false;
                                    fabric.common.TransactionID $currentTid203 =
                                      $tm204.getCurrentTid();
                                    if ($currentTid203 != null) {
                                        if ($e202.tid.equals($currentTid203) ||
                                              !$e202.tid.isDescendantOf(
                                                           $currentTid203)) {
                                            throw $e202;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit200 && $retry201) {
                                { val = val$var198; }
                                continue $label199;
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
                        fabric.metrics.DerivedMetric val$var208 = val;
                        int aggIdx$var209 = aggIdx;
                        fabric.worker.transaction.TransactionManager $tm215 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled218 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff216 = 1;
                        boolean $doBackoff217 = true;
                        boolean $retry212 = true;
                        $label210: for (boolean $commit211 = false; !$commit211;
                                        ) {
                            if ($backoffEnabled218) {
                                if ($doBackoff217) {
                                    if ($backoff216 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff216);
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
                            $commit211 = true;
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
                                $commit211 = false;
                                continue $label210;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e213) {
                                $commit211 = false;
                                fabric.common.TransactionID $currentTid214 =
                                  $tm215.getCurrentTid();
                                if ($e213.tid.isDescendantOf($currentTid214))
                                    continue $label210;
                                if ($currentTid214.parent != null) {
                                    $retry212 = false;
                                    throw $e213;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e213) {
                                $commit211 = false;
                                if ($tm215.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid214 =
                                  $tm215.getCurrentTid();
                                if ($e213.tid.isDescendantOf($currentTid214)) {
                                    $retry212 = true;
                                }
                                else if ($currentTid214.parent != null) {
                                    $retry212 = false;
                                    throw $e213;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e213) {
                                $commit211 = false;
                                if ($tm215.checkForStaleObjects())
                                    continue $label210;
                                $retry212 = false;
                                throw new fabric.worker.AbortException($e213);
                            }
                            finally {
                                if ($commit211) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e213) {
                                        $commit211 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e213) {
                                        $commit211 = false;
                                        fabric.common.TransactionID
                                          $currentTid214 =
                                          $tm215.getCurrentTid();
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
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit211 && $retry212) {
                                    {
                                        val = val$var208;
                                        aggIdx = aggIdx$var209;
                                    }
                                    continue $label210;
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
                    fabric.worker.transaction.TransactionManager $tm225 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled228 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff226 = 1;
                    boolean $doBackoff227 = true;
                    boolean $retry222 = true;
                    $label220: for (boolean $commit221 = false; !$commit221; ) {
                        if ($backoffEnabled228) {
                            if ($doBackoff227) {
                                if ($backoff226 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff226);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e223) {
                                            
                                        }
                                    }
                                }
                                if ($backoff226 < 5000) $backoff226 *= 2;
                            }
                            $doBackoff227 = $backoff226 <= 32 || !$doBackoff227;
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
                        catch (final fabric.worker.RetryException $e223) {
                            $commit221 = false;
                            continue $label220;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e223) {
                            $commit221 = false;
                            fabric.common.TransactionID $currentTid224 =
                              $tm225.getCurrentTid();
                            if ($e223.tid.isDescendantOf($currentTid224))
                                continue $label220;
                            if ($currentTid224.parent != null) {
                                $retry222 = false;
                                throw $e223;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e223) {
                            $commit221 = false;
                            if ($tm225.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid224 =
                              $tm225.getCurrentTid();
                            if ($e223.tid.isDescendantOf($currentTid224)) {
                                $retry222 = true;
                            }
                            else if ($currentTid224.parent != null) {
                                $retry222 = false;
                                throw $e223;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e223) {
                            $commit221 = false;
                            if ($tm225.checkForStaleObjects())
                                continue $label220;
                            $retry222 = false;
                            throw new fabric.worker.AbortException($e223);
                        }
                        finally {
                            if ($commit221) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e223) {
                                    $commit221 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e223) {
                                    $commit221 = false;
                                    fabric.common.TransactionID $currentTid224 =
                                      $tm225.getCurrentTid();
                                    if ($currentTid224 != null) {
                                        if ($e223.tid.equals($currentTid224) ||
                                              !$e223.tid.isDescendantOf(
                                                           $currentTid224)) {
                                            throw $e223;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit221 && $retry222) {
                                { val = val$var219; }
                                continue $label220;
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
    
    public static final byte[] $classHash = new byte[] { -92, 54, 35, -108, 95,
    -114, 111, -19, 120, 70, -95, -11, -118, -66, -122, -23, -127, -21, 93, 22,
    58, 107, -98, 124, -113, -93, -66, 55, 7, 112, 80, 124 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527195040000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
