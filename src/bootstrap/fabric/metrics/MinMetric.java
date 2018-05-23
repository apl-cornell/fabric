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
    
    public static final byte[] $classHash = new byte[] { -16, 38, 25, 86, -11,
    -9, 52, 76, -27, 21, -45, 10, 123, 23, -31, -127, -25, -28, -30, 36, -71, 1,
    19, -63, 8, 26, 84, 97, 97, 124, -103, 30 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527095723000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
