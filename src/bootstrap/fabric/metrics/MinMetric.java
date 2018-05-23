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
    
    public static final byte[] $classHash = new byte[] { -62, -40, -59, 55, -76,
    123, 80, -41, -20, -46, 72, 44, 123, 45, -25, 80, 56, -45, 78, 56, -44, 66,
    2, -64, 72, 59, 18, -86, 93, -57, -103, 114 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527095723000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0Za2wUx3nufPiFwcbmaYwB23XCw3clkAC5tCm+8DA+G8sGpJiCu96bszfe21125+Ac6opEiiD54bYpIUEttCpUSakL6iPqE4k+0gSlQqlLGqK2CT+KSAQoRVFbFKWh3zc799q72/gqEDvfema+b773983exE0yzTJJU1QaVFQ/GzWo5d8kDXaEeyTTopGQKlnWdpgdkKf7Oo6+92Kk0Uu8YVIlS5quKbKkDmgWIzPDj0n7pIBGWWBHb0dwF6mQEXGLZA0z4t3VnjDJEkNXR4dUnYlDcug/tyJw5Pk9NT8uIdX9pFrR+pjEFDmka4wmWD+pitHYIDWtDZEIjfSTWRqlkT5qKpKqPA4bda2f1FrKkCaxuEmtXmrp6j7cWGvFDWryM5OTyL4ObJtxmekmsF9jsx9nihoIKxYLhklpVKFqxNpLvkJ8YTItqkpDsHFuOClFgFMMbMJ52F6pAJtmVJJpEsU3omgRRhY7MVISt3TCBkAti1E2rKeO8mkSTJBamyVV0oYCfcxUtCHYOk2PwymM1BckCpvKDUkekYboACPznft67CXYVcHVgiiMzHFu45TAZvUOm2VY62b3Q+MHtC2al3iA5wiVVeS/HJAaHUi9NEpNqsnURqxaHj4qzT132EsIbJ7j2Gzv+dmXb31hZeP51+w9C/Ps2Tb4GJXZgHxqcOafGkLL1pcgG+WGbinoClmSc6v2iJVgwgBvn5uiiIv+5OL53j88evA0ve4llR2kVNbVeAy8apasxwxFpeZmqlFTYjTSQSqoFgnx9Q5SBu9hRaP27LZo1KKsg/hUPlWq879BRVEggSoqg3dFi+rJd0Niw/w9YRBCyuAhHvi/mpB7j8N7PSEl9YxsDgzrMRoYVON0P7h3AB4qmfJwAOLWVOSAZcoBM64xBTaJKfAiAFagS9G6+KsfWDDuHqkEcl2z3+MBhS6W9QgdlCywjvCU9h4VgmGLrkaoOSCr4+c6SN25Y9xbKtDDLfBSrg8PWLjBmRsycY/E2zfeOjPwuu1piCvUBVa2+fML/vwp/oClKowfP2QkP2SkCU/CHzrR8QPuJqUWj6cUlSqg8qChSiyqm7EE8Xi4SLM5PvcPsO4IZA1IDFXL+nZv/dLhphJwTGO/D20FW1ucYZJOLh3wJoHvD8jVh97799mjY3o6YBhpyYnjXEyMwyanfkxdphHIc2nyy5dILw+cG2vxYg6pgPTGJHBAyBWNzjOy4jGYzG2ojWlhMh11IKm4lExIlWzY1PenZ7jdZ+JQa7sAKsvBIE+Ln+szjl+++P5qXjCSGbQ6I9X2URbMiFokVs3jc1Za99tNSmHf31/o+cZzNw/t4oqHHc35DmzBMQTRKkGY6uZTr+19+913Tl3ypo3FSKkRH1QVOcFlmXUH/nng+QQfDD2cQAgJOCTCfkkq7g08uTXNG2QAFbIQsG617NBiekSJKtKgStFTPq7+zKqXb4zX2OZWYcZWnklWfjqB9PyCdnLw9T3/aeRkPDJWoLT+0tvstFaXprzBNKVR5CPxxOSiY69Kx8HzISlZyuOU5xnC9UG4Ae/jumjj4yrH2hocmmxtNYh5/kczH1txWGbrFl+XC70S8a9UZLAFAtbhap2B4+xsmiZZVKjY8EJ56skjJyLbvrfKLgm12Ql8oxaP/fAv//2j/4UrF/KkiQqmG20q3UfVjDNr4cilOV1PF6/F6bC6cn3R+tDI1SH72MUOFp27v981cWFzq/ysl5SkYjynAchGCmYyC8FmUuhfNBQbZyq5EZaklDodlbURniZCfOttWHInQ6kiIrmFcFibQuV6rhQonwh422mPtBd4UultYaaWtoKLceeyK/UeCNU3Rj84auvH2S9kbPznxLvXJ2csOsPzkw9LBZfP2Wjl9lFZ7REXryol0/0oUxCeLkIq/irgm4x0/v8l7hFoKaFFzKqYd5OcHSBzoOly1i4OcbE+jw2cTdUmVFra8foDE9+qD33+ul1fU9kf6SzNU193ShmF6b7TsX95m0pf8ZKyflLDG2NJYzslkBYSbz+YxAqJyTCZkbWe3abaPVkw5fkNTs/PONZZdzJjwMeyvJ+Xms6Eh3A33ZE/+3h59mFAVNEkuzVZAclepdoQG86jvx5TiUHR2SeaUnr4yDN3/ONH7ORhd+7NOc1zJo7dvfODZvDTMIUtdTuFY2y6dnbsVy+NHfKK/BpkpARcHl+3JlJ299oyJV3FzuuoY0iKukaxRPC1BZDbsHlRdbiIpTzL7lwU3Z+6Hg3araecyPEs/PthW8Gch4zMwaVyKQ0jLmsxHODqN01GfpOM1aTlsH3FZopjbHahxv24nZEFdsi0iJBpSbV7Lems93B2roQkRz4LdehRAduLy5WIskHAYOFcmcnsqMvaARwYXJfxSgFXuR7MJKyX7+0XXoRgN7huRI8nzZxHJsj9Zb0CBouTCVEeFHDN1GR6ymXtEA4HnTJxPY8V4r6dkPJOAdcUxz2irBawbWrcj7usfQ2Hp53c73TlHjiveETAtuK4R5SVArZOjfvnXdaO4fCsk/vufNzPJKJebgdOegRcVoD7nN4O8oxh6gxClkYS2WLNELTuFXBxYbEykw6kg0ZRAffr5gg1U4UQv/tYXZKRzHDZ1zPO5ndddPISDt9k+ImH64QXqoIaWQfPbrCtJOBaF3ueyBUcUR4Q0MUbPekO+SSnetZFgB/hcBpuSkkBKKR3hY26WjVKSJUpYGdxMiDKVgE3FCHDz11k+CUOP0kboVtXrLxG4EHVAI8OzGwXMFRcUCFKu4APTS2ofuOy9jscfs1IOdPtD295ylfGQo6H5pOwDZ4xMNcpAZ8sTkJEeULAA1My0RinetFFzDdwuAAlGttWKyljg6MrzepfcU+9Q7w6YqczMk5IzWUBvzPFvOJlpMyAA+ByzfCzDH7wdaSXWkHy2wJ+vbD43nQnWJPWwd9cdPAODpfAgPbRA1wVODeZz4gr4HmRkNltAs4ozoiIUiVg6ZSM2M2pXnUR4BoOVxjxGWo8L+PcPB3w/IKQuScFjE3VPPh6GYe381gFKakC7pm6VWyhPnAR6hYO7zMyXVilkGzcKM3wTBKy4KaAbxZnFES5JODFIoxy24X/j3D4EBr7mMK/aXXnswl2lf+A1LdJwPl3xSZIaZ6AFUXaxOMtLJPHh5MfM1IpbFJANG4SBTQFTDTfFPCrRZmEo4wLeLiwDD7OHedrLDWcTOaxuuzeoo/pZurKlNtQeKa7iD4Lh1Kox2wYaAzraqRHVxV5NHnUA46UibdvU5KZ5acanCDTGNWYf2P63UbfmyeZcvUtANlbCWn9SMAbxakPUa4LeLWw+jIlrHdZa8ABpCwflqzhkB7hVfyL+fi+Bw6FFHnPMwJqxfGNKDEBhz41EpPKrxXKz7hUupi52UVQ7IU9jVCG6N64pNoF0XEnKxvUdZVKWgLa4dTdEz+8Lszz84f4EU4O/Z6eutq5ck6Bnz7m5/wsKvDOnKgun3dix1v2B7PkD2wVYVIejatq5ueSjPdSw6RRhWu7wv54YnDhVsEdIdtNGf+whm8onSdg71sD8tv78K/7uQXqU8MkJ1kfN/En3YkP590uLd9+hX9dByUu+e3lV9b+9EDPWzcmt6w80HatZ92fu9ddavee3xKsPb371WPm/wAAr/tDah4AAA==";
}
