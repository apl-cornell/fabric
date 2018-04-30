package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Arrays;
import fabric.util.LinkedHashMap;
import fabric.util.Map;
import fabric.util.Iterator;
import fabric.common.ConfigProperties;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.metrics.SumStrategy;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * A {@link DerivedMetric} for the sum of the given metric terms.
 */
public interface SumMetric extends fabric.metrics.DerivedMetric {
    /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param terms
     *            The {@link Metric}s this applies to
     */
    public fabric.metrics.SumMetric fabric$metrics$SumMetric$(
      fabric.metrics.Metric[] terms);
    
    public double computePresetR();
    
    public double computePresetB();
    
    public double computePresetV();
    
    public double computePresetN();
    
    public double computeValue(boolean useWeakCache);
    
    public double computeVelocity(boolean useWeakCache);
    
    public double computeNoise(boolean useWeakCache);
    
    public java.lang.String toString();
    
    public fabric.metrics.DerivedMetric times(double scalar);
    
    /**
     * {@inheritDoc}
     * <p>
     * {@link SumMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
    public long get$MIN_ADAPTIVE_EXPIRY();
    
    public long set$MIN_ADAPTIVE_EXPIRY(long val);
    
    public long postInc$MIN_ADAPTIVE_EXPIRY();
    
    public long postDec$MIN_ADAPTIVE_EXPIRY();
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      thresholdPolicy(double rate, double base, boolean useWeakCache,
                      final fabric.worker.Store s);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy extends fabric.metrics.DerivedMetric._Proxy
      implements fabric.metrics.SumMetric {
        public long get$MIN_ADAPTIVE_EXPIRY() {
            return ((fabric.metrics.SumMetric._Impl) fetch()).
              get$MIN_ADAPTIVE_EXPIRY();
        }
        
        public long set$MIN_ADAPTIVE_EXPIRY(long val) {
            return ((fabric.metrics.SumMetric._Impl) fetch()).
              set$MIN_ADAPTIVE_EXPIRY(val);
        }
        
        public long postInc$MIN_ADAPTIVE_EXPIRY() {
            return ((fabric.metrics.SumMetric._Impl) fetch()).
              postInc$MIN_ADAPTIVE_EXPIRY();
        }
        
        public long postDec$MIN_ADAPTIVE_EXPIRY() {
            return ((fabric.metrics.SumMetric._Impl) fetch()).
              postDec$MIN_ADAPTIVE_EXPIRY();
        }
        
        public fabric.metrics.SumMetric fabric$metrics$SumMetric$(
          fabric.metrics.Metric[] arg1) {
            return ((fabric.metrics.SumMetric) fetch()).
              fabric$metrics$SumMetric$(arg1);
        }
        
        public _Proxy(SumMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.DerivedMetric._Impl
      implements fabric.metrics.SumMetric {
        /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param terms
     *            The {@link Metric}s this applies to
     */
        public fabric.metrics.SumMetric fabric$metrics$SumMetric$(
          fabric.metrics.Metric[] terms) {
            fabric$metrics$DerivedMetric$(terms);
            initialize();
            return (fabric.metrics.SumMetric) this.$getProxy();
        }
        
        public double computePresetR() {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).getPresetR();
            }
            return result;
        }
        
        public double computePresetB() {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).getPresetB();
            }
            return result;
        }
        
        public double computePresetV() {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).getPresetV();
            }
            return result;
        }
        
        public double computePresetN() {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).getPresetN();
            }
            return result;
        }
        
        public double computeValue(boolean useWeakCache) {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).value(useWeakCache);
            }
            return result;
        }
        
        public double computeVelocity(boolean useWeakCache) {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).velocity(useWeakCache);
            }
            return result;
        }
        
        public double computeNoise(boolean useWeakCache) {
            double result = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                result += term(i).noise(useWeakCache);
            }
            return result;
        }
        
        public java.lang.String toString() {
            java.lang.String str = "(";
            boolean nonEmpty = false;
            for (int i = 0; i < this.get$terms().length(); i++) {
                if (nonEmpty) str += " + ";
                nonEmpty = true;
                str += term(i);
            }
            return str + ")@" + getStore();
        }
        
        public fabric.metrics.DerivedMetric times(double scalar) {
            return fabric.metrics.SumMetric._Impl.static_times(
                                                    (fabric.metrics.SumMetric)
                                                      this.$getProxy(), scalar);
        }
        
        private static fabric.metrics.DerivedMetric static_times(
          fabric.metrics.SumMetric tmp, double scalar) {
            fabric.metrics.Metric[] newTerms =
              new fabric.metrics.Metric[tmp.get$terms().length()];
            for (int i = 0; i < tmp.get$terms().length(); i++)
                newTerms[i] = tmp.term(i).times(scalar);
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.SumMetric)
                     new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                    fabric$metrics$SumMetric$(newTerms);
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var378 = val;
                    fabric.worker.transaction.TransactionManager $tm384 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled387 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff385 = 1;
                    boolean $doBackoff386 = true;
                    boolean $retry381 = true;
                    $label379: for (boolean $commit380 = false; !$commit380; ) {
                        if ($backoffEnabled387) {
                            if ($doBackoff386) {
                                if ($backoff385 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff385);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e382) {
                                            
                                        }
                                    }
                                }
                                if ($backoff385 < 5000) $backoff385 *= 2;
                            }
                            $doBackoff386 = $backoff385 <= 32 || !$doBackoff386;
                        }
                        $commit380 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e382) {
                            $commit380 = false;
                            continue $label379;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e382) {
                            $commit380 = false;
                            fabric.common.TransactionID $currentTid383 =
                              $tm384.getCurrentTid();
                            if ($e382.tid.isDescendantOf($currentTid383))
                                continue $label379;
                            if ($currentTid383.parent != null) {
                                $retry381 = false;
                                throw $e382;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e382) {
                            $commit380 = false;
                            if ($tm384.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid383 =
                              $tm384.getCurrentTid();
                            if ($e382.tid.isDescendantOf($currentTid383)) {
                                $retry381 = true;
                            }
                            else if ($currentTid383.parent != null) {
                                $retry381 = false;
                                throw $e382;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e382) {
                            $commit380 = false;
                            if ($tm384.checkForStaleObjects())
                                continue $label379;
                            $retry381 = false;
                            throw new fabric.worker.AbortException($e382);
                        }
                        finally {
                            if ($commit380) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e382) {
                                    $commit380 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e382) {
                                    $commit380 = false;
                                    fabric.common.TransactionID $currentTid383 =
                                      $tm384.getCurrentTid();
                                    if ($currentTid383 != null) {
                                        if ($e382.tid.equals($currentTid383) ||
                                              !$e382.tid.isDescendantOf(
                                                           $currentTid383)) {
                                            throw $e382;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit380 && $retry381) {
                                { val = val$var378; }
                                continue $label379;
                            }
                        }
                    }
                }
            }
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        /**
     * {@inheritDoc}
     * <p>
     * {@link SumMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            return fabric.metrics.SumMetric._Impl.static_plus(
                                                    (fabric.metrics.SumMetric)
                                                      this.$getProxy(), other);
        }
        
        private static fabric.metrics.DerivedMetric static_plus(
          fabric.metrics.SumMetric tmp, fabric.metrics.Metric other) {
            final fabric.worker.Store s = tmp.$getStore();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.SumMetric &&
                  other.$getStore().equals(s)) {
                fabric.metrics.SumMetric that =
                  (fabric.metrics.SumMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                fabric.metrics.DerivedMetric result = tmp;
                for (int i = 0; i < that.get$terms().length(); i++) {
                    result = result.plus(that.term(i));
                }
                return result;
            }
            int termIdx = -1;
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.DerivedMetric) {
                fabric.metrics.DerivedMetric derivedOther =
                  (fabric.metrics.DerivedMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                for (int i = 0; i < tmp.get$terms().length(); i++) {
                    if (!tmp.term(i).$getStore().equals(other.$getStore()))
                        continue;
                    if (fabric.lang.Object._Proxy.
                          $getProxy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                tmp.term(
                                      i))) instanceof fabric.metrics.DerivedMetric) {
                        fabric.metrics.DerivedMetric derivedTerm =
                          (fabric.metrics.DerivedMetric)
                            fabric.lang.Object._Proxy.$getProxy(tmp.term(i));
                        if (java.util.Arrays.asList(
                                               derivedTerm.getLeafSubjects(
                                                             ).array()).
                              containsAll(
                                java.util.Arrays.asList(
                                                   derivedOther.getLeafSubjects(
                                                                  ).array()))) {
                            termIdx = i;
                            break;
                        }
                    }
                    else {
                        fabric.metrics.SampledMetric sampledTerm =
                          (fabric.metrics.SampledMetric)
                            fabric.lang.Object._Proxy.$getProxy(tmp.term(i));
                        if (derivedOther.
                              getLeafSubjects().
                              length() ==
                              1 &&
                              java.util.Arrays.
                              asList(
                                derivedOther.
                                    getLeafSubjects().array()).
                              contains(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.
                                  $unwrap(sampledTerm))) {
                            termIdx = i;
                            break;
                        }
                    }
                }
            }
            else {
                fabric.metrics.SampledMetric sampledOther =
                  (fabric.metrics.SampledMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                for (int i = 0; i < tmp.get$terms().length(); i++) {
                    if (!tmp.term(i).$getStore().equals(other.$getStore()))
                        continue;
                    if (fabric.lang.Object._Proxy.
                          $getProxy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                tmp.term(
                                      i))) instanceof fabric.metrics.DerivedMetric) {
                        fabric.metrics.DerivedMetric derivedTerm =
                          (fabric.metrics.DerivedMetric)
                            fabric.lang.Object._Proxy.$getProxy(tmp.term(i));
                        if (java.util.Arrays.
                              asList(derivedTerm.getLeafSubjects().array()).
                              contains(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.
                                  $unwrap(sampledOther))) {
                            termIdx = i;
                            break;
                        }
                    }
                    else {
                        fabric.metrics.SampledMetric sampledTerm =
                          (fabric.metrics.SampledMetric)
                            fabric.lang.Object._Proxy.$getProxy(tmp.term(i));
                        if (sampledTerm.equals(sampledOther)) {
                            termIdx = i;
                            break;
                        }
                    }
                }
            }
            fabric.metrics.Metric[] newTerms = null;
            if (termIdx >= 0) {
                newTerms =
                  (new fabric.metrics.Metric[tmp.get$terms().length()]);
                for (int i = 0; i < tmp.get$terms().length(); i++)
                    newTerms[i] = tmp.term(i);
                newTerms[termIdx] = newTerms[termIdx].plus(other);
            } else {
                newTerms = (new fabric.metrics.Metric[tmp.get$terms().length() +
                                                        1]);
                for (int i = 0; i < tmp.get$terms().length(); i++)
                    newTerms[i] = tmp.term(i);
                newTerms[tmp.get$terms().length()] = other;
                java.util.Arrays.sort(newTerms, 0, newTerms.length);
            }
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.SumMetric)
                     new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                    fabric$metrics$SumMetric$(newTerms);
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var388 = val;
                    fabric.metrics.Metric[] newTerms$var389 = newTerms;
                    int termIdx$var390 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm396 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled399 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff397 = 1;
                    boolean $doBackoff398 = true;
                    boolean $retry393 = true;
                    $label391: for (boolean $commit392 = false; !$commit392; ) {
                        if ($backoffEnabled399) {
                            if ($doBackoff398) {
                                if ($backoff397 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff397);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e394) {
                                            
                                        }
                                    }
                                }
                                if ($backoff397 < 5000) $backoff397 *= 2;
                            }
                            $doBackoff398 = $backoff397 <= 32 || !$doBackoff398;
                        }
                        $commit392 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e394) {
                            $commit392 = false;
                            continue $label391;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e394) {
                            $commit392 = false;
                            fabric.common.TransactionID $currentTid395 =
                              $tm396.getCurrentTid();
                            if ($e394.tid.isDescendantOf($currentTid395))
                                continue $label391;
                            if ($currentTid395.parent != null) {
                                $retry393 = false;
                                throw $e394;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e394) {
                            $commit392 = false;
                            if ($tm396.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid395 =
                              $tm396.getCurrentTid();
                            if ($e394.tid.isDescendantOf($currentTid395)) {
                                $retry393 = true;
                            }
                            else if ($currentTid395.parent != null) {
                                $retry393 = false;
                                throw $e394;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e394) {
                            $commit392 = false;
                            if ($tm396.checkForStaleObjects())
                                continue $label391;
                            $retry393 = false;
                            throw new fabric.worker.AbortException($e394);
                        }
                        finally {
                            if ($commit392) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e394) {
                                    $commit392 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e394) {
                                    $commit392 = false;
                                    fabric.common.TransactionID $currentTid395 =
                                      $tm396.getCurrentTid();
                                    if ($currentTid395 != null) {
                                        if ($e394.tid.equals($currentTid395) ||
                                              !$e394.tid.isDescendantOf(
                                                           $currentTid395)) {
                                            throw $e394;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit392 && $retry393) {
                                {
                                    val = val$var388;
                                    newTerms = newTerms$var389;
                                    termIdx = termIdx$var390;
                                }
                                continue $label391;
                            }
                        }
                    }
                }
            }
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        public long get$MIN_ADAPTIVE_EXPIRY() {
            return this.MIN_ADAPTIVE_EXPIRY;
        }
        
        public long set$MIN_ADAPTIVE_EXPIRY(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.MIN_ADAPTIVE_EXPIRY = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$MIN_ADAPTIVE_EXPIRY() {
            long tmp = this.get$MIN_ADAPTIVE_EXPIRY();
            this.set$MIN_ADAPTIVE_EXPIRY((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$MIN_ADAPTIVE_EXPIRY() {
            long tmp = this.get$MIN_ADAPTIVE_EXPIRY();
            this.set$MIN_ADAPTIVE_EXPIRY((long) (tmp - 1));
            return tmp;
        }
        
        public long MIN_ADAPTIVE_EXPIRY = 1000;
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base, boolean useWeakCache,
                          final fabric.worker.Store s) {
            long currentTime = java.lang.System.currentTimeMillis();
            double baseNow =
              fabric.metrics.contracts.Bound._Impl.value(rate, base,
                                                         currentTime);
            double totalSamples = samples(useWeakCache);
            double totalValue = value(useWeakCache);
            double totalVelocity = velocity(useWeakCache);
            double totalNoise = noise(useWeakCache);
            int numTerms = this.get$terms().length();
            fabric.common.ConfigProperties config =
              fabric.worker.Worker.getWorker().config;
            if (config.usePreset) {
                java.util.Map witnesses = new java.util.HashMap();
                for (int j = 0; j < numTerms; j++) {
                    fabric.metrics.Metric m = term(j);
                    double scaledX = m.value(useWeakCache);
                    double scaledV = m.velocity(useWeakCache);
                    double scaledN = m.noise(useWeakCache);
                    double r = m.getPresetR();
                    double b = scaledX - m.getPresetB() / getPresetB() *
                      (totalValue - baseNow);
                    double[] normalized =
                      fabric.metrics.contracts.Bound._Impl.createBound(
                                                             r, b, currentTime);
                    if (!witnesses.
                          containsKey(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(m)) ||
                          !((fabric.metrics.contracts.Contract)
                              fabric.lang.Object._Proxy.
                              $getProxy(
                                fabric.lang.WrappedJavaInlineable.
                                    $wrap(
                                      witnesses.
                                          get(
                                            (java.lang.Object)
                                              fabric.lang.WrappedJavaInlineable.
                                              $unwrap(m))))).implies(
                                                               m, normalized[0],
                                                               normalized[1])) {
                        witnesses.
                          put(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(m),
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(m.getThresholdContract(r, b,
                                                             currentTime)));
                    }
                }
                fabric.metrics.contracts.Contract[] finalWitnesses =
                  new fabric.metrics.contracts.Contract[witnesses.size()];
                int i = 0;
                for (java.util.Iterator iter = witnesses.values().iterator();
                     iter.hasNext(); ) {
                    finalWitnesses[i++] =
                      (fabric.metrics.contracts.Contract)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          fabric.lang.WrappedJavaInlineable.$wrap(iter.next()));
                }
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                          new fabric.metrics.contracts.enforcement.
                            WitnessPolicy._Impl(s).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$WitnessPolicy$(
                    finalWitnesses);
            }
            else {
                double baseFactor = 0.0;
                double newFactor = 1.0 - baseFactor;
                java.util.Map adaptiveWitnesses =
                  new java.util.HashMap();
                java.util.Map staticWitnesses =
                  new java.util.HashMap();
                double[] velocities = new double[numTerms];
                double[] adaptiveRates = new double[numTerms];
                double[] staticRates = new double[numTerms];
                double[] noises = new double[numTerms];
                for (int j = 0;
                     j < numTerms; j++) {
                    fabric.metrics.Metric m =
                      term(j);
                    velocities[j] =
                      m.velocity(useWeakCache);
                    noises[j] =
                      m.noise(useWeakCache);
                    adaptiveRates[j] =
                      newFactor * (velocities[j] - totalVelocity / numTerms) +
                        rate / numTerms;
                    staticRates[j] =
                      rate / numTerms;
                }
                if (fabric.common.Logging.METRICS_LOGGER.
                      isLoggable(java.util.logging.Level.FINE)) {
                    fabric.common.Logging.METRICS_LOGGER.
                      log(
                        java.util.logging.Level.FINE,
                        "Running strategy for {0} with velocities {1} and noises {2}",
                        new java.lang.Object[] { (java.lang.Object)
                                                   fabric.lang.WrappedJavaInlineable.
                                                   $unwrap(
                                                     (fabric.metrics.SumMetric)
                                                       this.$getProxy()),
                          java.util.Arrays.
                            toString(velocities),
                          java.util.Arrays.
                            toString(noises) });
                }
                double[] adaptiveSlacks =
                  fabric.worker.metrics.SumStrategy.getSplitEqualVelocity(
                                                      velocities, noises,
                                                      adaptiveRates,
                                                      totalValue - baseNow);
                double[] staticSlacks =
                  fabric.worker.metrics.SumStrategy.getSplit(velocities,
                                                             noises,
                                                             staticRates,
                                                             totalValue -
                                                                 baseNow);
                long adaptiveTimeout = java.lang.Long.MAX_VALUE;
                long staticTimeout = java.lang.Long.MAX_VALUE;
                for (int j = 0; j < numTerms; j++) {
                    fabric.metrics.Metric m = term(j);
                    double scaledX = m.value(useWeakCache);
                    double scaledV = m.velocity(useWeakCache);
                    double scaledN = m.noise(useWeakCache);
                    double r = adaptiveRates[j];
                    double b = scaledX - adaptiveSlacks[j];
                    double[] normalized =
                      fabric.metrics.contracts.Bound._Impl.createBound(
                                                             r, b, currentTime);
                    if (!adaptiveWitnesses.
                          containsKey(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(m)) ||
                          !((fabric.metrics.contracts.Contract)
                              fabric.lang.Object._Proxy.
                              $getProxy(
                                fabric.lang.WrappedJavaInlineable.
                                    $wrap(
                                      adaptiveWitnesses.
                                          get(
                                            (java.lang.Object)
                                              fabric.lang.WrappedJavaInlineable.
                                              $unwrap(m))))).implies(
                                                               m, normalized[0],
                                                               normalized[1])) {
                        adaptiveWitnesses.
                          put(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(m),
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(m.getThresholdContract(r, b,
                                                             currentTime)));
                    }
                    adaptiveTimeout =
                      java.lang.Math.
                        min(
                          adaptiveTimeout,
                          fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
                              hedgedEstimate(m, normalized[0], normalized[1],
                                             currentTime, useWeakCache));
                    double r2 = staticRates[j];
                    double b2 = scaledX - staticSlacks[j];
                    double[] normalized2 =
                      fabric.metrics.contracts.Bound._Impl.createBound(
                                                             r2, b2,
                                                             currentTime);
                    if (!staticWitnesses.
                          containsKey(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(m)) ||
                          !((fabric.metrics.contracts.Contract)
                              fabric.lang.Object._Proxy.
                              $getProxy(
                                fabric.lang.WrappedJavaInlineable.
                                    $wrap(
                                      staticWitnesses.
                                          get(
                                            (java.lang.Object)
                                              fabric.lang.WrappedJavaInlineable.
                                              $unwrap(m))))).
                          implies(m, normalized2[0], normalized2[1])) {
                        staticWitnesses.
                          put(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(m),
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(m.getThresholdContract(r2, b2,
                                                             currentTime)));
                    }
                    staticTimeout =
                      java.lang.Math.
                        min(
                          staticTimeout,
                          fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
                              hedgedEstimate(m, normalized2[0], normalized2[1],
                                             currentTime, useWeakCache));
                }
                fabric.metrics.contracts.Contract[] finalWitnesses =
                  new fabric.metrics.contracts.Contract[adaptiveWitnesses.size(
                                                                            )];
                if (adaptiveTimeout >= staticTimeout && adaptiveTimeout -
                      java.lang.System.currentTimeMillis() >
                      this.get$MIN_ADAPTIVE_EXPIRY()) {
                    int i = 0;
                    for (java.util.Iterator iter =
                           adaptiveWitnesses.values().iterator();
                         iter.hasNext();
                         ) {
                        finalWitnesses[i++] =
                          (fabric.metrics.contracts.Contract)
                            fabric.lang.Object._Proxy.
                            $getProxy(
                              fabric.lang.WrappedJavaInlineable.$wrap(
                                                                  iter.next()));
                    }
                }
                else {
                    int i = 0;
                    for (java.util.Iterator iter =
                           staticWitnesses.values().iterator();
                         iter.hasNext();
                         ) {
                        finalWitnesses[i++] =
                          (fabric.metrics.contracts.Contract)
                            fabric.lang.Object._Proxy.
                            $getProxy(
                              fabric.lang.WrappedJavaInlineable.$wrap(
                                                                  iter.next()));
                    }
                }
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                          new fabric.metrics.contracts.enforcement.
                            WitnessPolicy._Impl(s).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$WitnessPolicy$(
                    finalWitnesses);
            }
        }
        
        public int hashCode() {
            return java.util.Arrays.hashCode(this.get$terms().array()) * 32 +
              getStore().hashCode();
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.SumMetric) {
                fabric.metrics.SumMetric that =
                  (fabric.metrics.SumMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                return java.util.Arrays.deepEquals(this.get$terms().array(),
                                                   that.get$terms().array()) &&
                  this.$getStore().equals(that.$getStore());
            }
            return false;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.SumMetric._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeLong(this.MIN_ADAPTIVE_EXPIRY);
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
            this.MIN_ADAPTIVE_EXPIRY = in.readLong();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.SumMetric._Impl src =
              (fabric.metrics.SumMetric._Impl) other;
            this.MIN_ADAPTIVE_EXPIRY = src.MIN_ADAPTIVE_EXPIRY;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.SumMetric._Static {
            public _Proxy(fabric.metrics.SumMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.SumMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  SumMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.SumMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.SumMetric._Static._Impl.class);
                $instance = (fabric.metrics.SumMetric._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.SumMetric._Static {
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
                return new fabric.metrics.SumMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -56, -77, -120, 82, 22,
    -125, 40, -29, 122, -105, 21, 15, 60, 11, -119, 40, -65, 22, -53, 45, -6,
    24, 42, 39, 8, -36, -19, 51, 58, 1, -27, -34 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524675608000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
