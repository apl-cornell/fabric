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
                    fabric.metrics.DerivedMetric val$var0 = val;
                    fabric.worker.transaction.TransactionManager $tm6 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled9 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff7 = 1;
                    boolean $doBackoff8 = true;
                    boolean $retry3 = true;
                    $label1: for (boolean $commit2 = false; !$commit2; ) {
                        if ($backoffEnabled9) {
                            if ($doBackoff8) {
                                if ($backoff7 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff7);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e4) {
                                            
                                        }
                                    }
                                }
                                if ($backoff7 < 5000) $backoff7 *= 2;
                            }
                            $doBackoff8 = $backoff7 <= 32 || !$doBackoff8;
                        }
                        $commit2 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e4) {
                            $commit2 = false;
                            continue $label1;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e4) {
                            $commit2 = false;
                            fabric.common.TransactionID $currentTid5 =
                              $tm6.getCurrentTid();
                            if ($e4.tid.isDescendantOf($currentTid5))
                                continue $label1;
                            if ($currentTid5.parent != null) {
                                $retry3 = false;
                                throw $e4;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e4) {
                            $commit2 = false;
                            if ($tm6.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid5 =
                              $tm6.getCurrentTid();
                            if ($e4.tid.isDescendantOf($currentTid5)) {
                                $retry3 = true;
                            }
                            else if ($currentTid5.parent != null) {
                                $retry3 = false;
                                throw $e4;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e4) {
                            $commit2 = false;
                            if ($tm6.checkForStaleObjects()) continue $label1;
                            $retry3 = false;
                            throw new fabric.worker.AbortException($e4);
                        }
                        finally {
                            if ($commit2) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.AbortException $e4) {
                                    $commit2 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e4) {
                                    $commit2 = false;
                                    fabric.common.TransactionID $currentTid5 =
                                      $tm6.getCurrentTid();
                                    if ($currentTid5 != null) {
                                        if ($e4.tid.equals($currentTid5) ||
                                              !$e4.tid.isDescendantOf(
                                                         $currentTid5)) {
                                            throw $e4;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit2 && $retry3) {
                                { val = val$var0; }
                                continue $label1;
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
                    fabric.metrics.DerivedMetric val$var10 = val;
                    fabric.metrics.Metric[] newTerms$var11 = newTerms;
                    int termIdx$var12 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm18 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled21 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff19 = 1;
                    boolean $doBackoff20 = true;
                    boolean $retry15 = true;
                    $label13: for (boolean $commit14 = false; !$commit14; ) {
                        if ($backoffEnabled21) {
                            if ($doBackoff20) {
                                if ($backoff19 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff19);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e16) {
                                            
                                        }
                                    }
                                }
                                if ($backoff19 < 5000) $backoff19 *= 2;
                            }
                            $doBackoff20 = $backoff19 <= 32 || !$doBackoff20;
                        }
                        $commit14 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e16) {
                            $commit14 = false;
                            continue $label13;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e16) {
                            $commit14 = false;
                            fabric.common.TransactionID $currentTid17 =
                              $tm18.getCurrentTid();
                            if ($e16.tid.isDescendantOf($currentTid17))
                                continue $label13;
                            if ($currentTid17.parent != null) {
                                $retry15 = false;
                                throw $e16;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e16) {
                            $commit14 = false;
                            if ($tm18.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid17 =
                              $tm18.getCurrentTid();
                            if ($e16.tid.isDescendantOf($currentTid17)) {
                                $retry15 = true;
                            }
                            else if ($currentTid17.parent != null) {
                                $retry15 = false;
                                throw $e16;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e16) {
                            $commit14 = false;
                            if ($tm18.checkForStaleObjects()) continue $label13;
                            $retry15 = false;
                            throw new fabric.worker.AbortException($e16);
                        }
                        finally {
                            if ($commit14) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e16) {
                                    $commit14 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e16) {
                                    $commit14 = false;
                                    fabric.common.TransactionID $currentTid17 =
                                      $tm18.getCurrentTid();
                                    if ($currentTid17 != null) {
                                        if ($e16.tid.equals($currentTid17) ||
                                              !$e16.tid.isDescendantOf(
                                                          $currentTid17)) {
                                            throw $e16;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit14 && $retry15) {
                                {
                                    val = val$var10;
                                    newTerms = newTerms$var11;
                                    termIdx = termIdx$var12;
                                }
                                continue $label13;
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
                if (adaptiveTimeout > staticTimeout && adaptiveTimeout -
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
    
    public static final byte[] $classHash = new byte[] { 106, 9, 44, 91, -37,
    81, 85, 31, 21, -79, 18, -107, -113, 93, 71, -58, 81, 62, -28, -121, 80,
    -52, -6, -42, 0, -101, 49, -98, 41, 110, -91, -1 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525208526000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
