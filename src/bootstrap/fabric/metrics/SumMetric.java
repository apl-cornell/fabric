package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
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
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result += ((fabric.metrics.Metric)
                             this.get$terms().get(i)).getPresetR();
            }
            return result;
        }
        
        public double computePresetB() {
            double result = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result += ((fabric.metrics.Metric)
                             this.get$terms().get(i)).getPresetB();
            }
            return result;
        }
        
        public double computePresetV() {
            double result = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result += ((fabric.metrics.Metric)
                             this.get$terms().get(i)).getPresetV();
            }
            return result;
        }
        
        public double computePresetN() {
            double result = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result += ((fabric.metrics.Metric)
                             this.get$terms().get(i)).getPresetN();
            }
            return result;
        }
        
        public double computeValue(boolean useWeakCache) {
            double result = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result += ((fabric.metrics.Metric)
                             this.get$terms().get(i)).value(useWeakCache);
            }
            return result;
        }
        
        public double computeVelocity(boolean useWeakCache) {
            double result = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result += ((fabric.metrics.Metric)
                             this.get$terms().get(i)).velocity(useWeakCache);
            }
            return result;
        }
        
        public double computeNoise(boolean useWeakCache) {
            double result = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result += ((fabric.metrics.Metric)
                             this.get$terms().get(i)).noise(useWeakCache);
            }
            return result;
        }
        
        public java.lang.String toString() {
            java.lang.String str = "(";
            boolean nonEmpty = false;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                if (nonEmpty) str += " + ";
                nonEmpty = true;
                str += (fabric.metrics.Metric) this.get$terms().get(i);
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
              new fabric.metrics.Metric[tmp.get$terms().get$length()];
            for (int i = 0; i < tmp.get$terms().get$length(); i++)
                newTerms[i] = ((fabric.metrics.Metric)
                                 tmp.get$terms().get(i)).times(scalar);
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
                    fabric.metrics.DerivedMetric val$var318 = val;
                    fabric.worker.transaction.TransactionManager $tm324 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled327 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff325 = 1;
                    boolean $doBackoff326 = true;
                    boolean $retry321 = true;
                    $label319: for (boolean $commit320 = false; !$commit320; ) {
                        if ($backoffEnabled327) {
                            if ($doBackoff326) {
                                if ($backoff325 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff325);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e322) {
                                            
                                        }
                                    }
                                }
                                if ($backoff325 < 5000) $backoff325 *= 2;
                            }
                            $doBackoff326 = $backoff325 <= 32 || !$doBackoff326;
                        }
                        $commit320 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e322) {
                            $commit320 = false;
                            continue $label319;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e322) {
                            $commit320 = false;
                            fabric.common.TransactionID $currentTid323 =
                              $tm324.getCurrentTid();
                            if ($e322.tid.isDescendantOf($currentTid323))
                                continue $label319;
                            if ($currentTid323.parent != null) {
                                $retry321 = false;
                                throw $e322;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e322) {
                            $commit320 = false;
                            if ($tm324.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid323 =
                              $tm324.getCurrentTid();
                            if ($e322.tid.isDescendantOf($currentTid323)) {
                                $retry321 = true;
                            }
                            else if ($currentTid323.parent != null) {
                                $retry321 = false;
                                throw $e322;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e322) {
                            $commit320 = false;
                            if ($tm324.checkForStaleObjects())
                                continue $label319;
                            $retry321 = false;
                            throw new fabric.worker.AbortException($e322);
                        }
                        finally {
                            if ($commit320) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e322) {
                                    $commit320 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e322) {
                                    $commit320 = false;
                                    fabric.common.TransactionID $currentTid323 =
                                      $tm324.getCurrentTid();
                                    if ($currentTid323 != null) {
                                        if ($e322.tid.equals($currentTid323) ||
                                              !$e322.tid.isDescendantOf(
                                                           $currentTid323)) {
                                            throw $e322;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit320 && $retry321) {
                                { val = val$var318; }
                                continue $label319;
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
                for (int i = 0; i < that.get$terms().get$length(); i++) {
                    result = result.plus((fabric.metrics.Metric)
                                           that.get$terms().get(i));
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
                for (int i = 0; i < tmp.get$terms().get$length(); i++) {
                    if (!((fabric.metrics.Metric) tmp.get$terms().get(i)).
                          $getStore().equals(other.$getStore()))
                        continue;
                    if (fabric.lang.Object._Proxy.
                          $getProxy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                (fabric.metrics.Metric)
                                  tmp.get$terms().
                                  get(i))) instanceof fabric.metrics.DerivedMetric) {
                        fabric.metrics.DerivedMetric derivedTerm =
                          (fabric.metrics.DerivedMetric)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.metrics.Metric)
                                                          tmp.get$terms().get(
                                                                            i));
                        if (fabric.util.Arrays._Impl.
                              asList(derivedTerm.getLeafSubjects()).
                              containsAll(
                                fabric.util.Arrays._Impl.
                                    asList(derivedOther.getLeafSubjects()))) {
                            termIdx = i;
                            break;
                        }
                    }
                    else {
                        fabric.metrics.SampledMetric sampledTerm =
                          (fabric.metrics.SampledMetric)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.metrics.Metric)
                                                          tmp.get$terms().get(
                                                                            i));
                        if (derivedOther.getLeafSubjects().get$length() ==
                              1 &&
                              fabric.util.Arrays._Impl.
                              asList(derivedOther.getLeafSubjects()).
                              contains(sampledTerm)) {
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
                for (int i = 0; i < tmp.get$terms().get$length(); i++) {
                    if (!((fabric.metrics.Metric) tmp.get$terms().get(i)).
                          $getStore().equals(other.$getStore()))
                        continue;
                    if (fabric.lang.Object._Proxy.
                          $getProxy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                (fabric.metrics.Metric)
                                  tmp.get$terms().
                                  get(i))) instanceof fabric.metrics.DerivedMetric) {
                        fabric.metrics.DerivedMetric derivedTerm =
                          (fabric.metrics.DerivedMetric)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.metrics.Metric)
                                                          tmp.get$terms().get(
                                                                            i));
                        if (fabric.util.Arrays._Impl.
                              asList(derivedTerm.getLeafSubjects()).
                              contains(sampledOther)) {
                            termIdx = i;
                            break;
                        }
                    }
                    else {
                        fabric.metrics.SampledMetric sampledTerm =
                          (fabric.metrics.SampledMetric)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.metrics.Metric)
                                                          tmp.get$terms().get(
                                                                            i));
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
                  (new fabric.metrics.Metric[tmp.get$terms().get$length()]);
                for (int i = 0; i < tmp.get$terms().get$length(); i++)
                    newTerms[i] = (fabric.metrics.Metric)
                                    tmp.get$terms().get(i);
                newTerms[termIdx] = newTerms[termIdx].plus(other);
            }
            else {
                newTerms =
                  (new fabric.metrics.Metric[tmp.get$terms().get$length() + 1]);
                for (int i = 0; i < tmp.get$terms().get$length(); i++)
                    newTerms[i] = (fabric.metrics.Metric)
                                    tmp.get$terms().get(i);
                newTerms[tmp.get$terms().get$length()] = other;
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
                    fabric.metrics.DerivedMetric val$var328 = val;
                    fabric.metrics.Metric[] newTerms$var329 = newTerms;
                    int termIdx$var330 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm336 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled339 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff337 = 1;
                    boolean $doBackoff338 = true;
                    boolean $retry333 = true;
                    $label331: for (boolean $commit332 = false; !$commit332; ) {
                        if ($backoffEnabled339) {
                            if ($doBackoff338) {
                                if ($backoff337 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff337);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e334) {
                                            
                                        }
                                    }
                                }
                                if ($backoff337 < 5000) $backoff337 *= 2;
                            }
                            $doBackoff338 = $backoff337 <= 32 || !$doBackoff338;
                        }
                        $commit332 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e334) {
                            $commit332 = false;
                            continue $label331;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e334) {
                            $commit332 = false;
                            fabric.common.TransactionID $currentTid335 =
                              $tm336.getCurrentTid();
                            if ($e334.tid.isDescendantOf($currentTid335))
                                continue $label331;
                            if ($currentTid335.parent != null) {
                                $retry333 = false;
                                throw $e334;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e334) {
                            $commit332 = false;
                            if ($tm336.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid335 =
                              $tm336.getCurrentTid();
                            if ($e334.tid.isDescendantOf($currentTid335)) {
                                $retry333 = true;
                            }
                            else if ($currentTid335.parent != null) {
                                $retry333 = false;
                                throw $e334;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e334) {
                            $commit332 = false;
                            if ($tm336.checkForStaleObjects())
                                continue $label331;
                            $retry333 = false;
                            throw new fabric.worker.AbortException($e334);
                        }
                        finally {
                            if ($commit332) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e334) {
                                    $commit332 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e334) {
                                    $commit332 = false;
                                    fabric.common.TransactionID $currentTid335 =
                                      $tm336.getCurrentTid();
                                    if ($currentTid335 != null) {
                                        if ($e334.tid.equals($currentTid335) ||
                                              !$e334.tid.isDescendantOf(
                                                           $currentTid335)) {
                                            throw $e334;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit332 && $retry333) {
                                {
                                    val = val$var328;
                                    newTerms = newTerms$var329;
                                    termIdx = termIdx$var330;
                                }
                                continue $label331;
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
            java.util.Map adaptiveWitnesses = new java.util.HashMap();
            java.util.Map staticWitnesses = new java.util.HashMap();
            long currentTime = java.lang.System.currentTimeMillis();
            double baseNow =
              fabric.metrics.contracts.Bound._Impl.value(rate, base,
                                                         currentTime);
            double totalSamples = samples(useWeakCache);
            double totalValue = value(useWeakCache);
            double totalVelocity = velocity(useWeakCache);
            double totalNoise = noise(useWeakCache);
            double numTerms = this.get$terms().get$length();
            double baseFactor = totalSamples > 10
              ? 0.0 / java.lang.Math.sqrt(totalSamples)
              : 1.0;
            double newFactor = 1.0 - baseFactor;
            fabric.common.ConfigProperties config =
              fabric.worker.Worker.getWorker().config;
            if (config.usePreset) {
                baseFactor = 0.0;
                newFactor = 1.0;
            }
            long adaptiveTimeout = java.lang.Long.MAX_VALUE;
            long staticTimeout = java.lang.Long.MAX_VALUE;
            for (int j = 0; j < numTerms; j++) {
                fabric.metrics.Metric m = term(j);
                double scaledX = m.value(useWeakCache);
                double scaledV = m.velocity(useWeakCache);
                double scaledN = m.noise(useWeakCache);
                double r = newFactor * (scaledV - totalVelocity / numTerms) +
                  rate / numTerms;
                double b = scaledX -
                  (totalValue - baseNow) *
                  (totalNoise != 0
                     ? baseFactor / numTerms + newFactor *
                     (scaledN / totalNoise)
                     : 1.0 / numTerms);
                if (getUsePreset()) {
                    r = m.getPresetR();
                    b = scaledX - m.getPresetB() / getPresetB() *
                          (totalValue - baseNow);
                }
                double[] normalized =
                  fabric.metrics.contracts.Bound._Impl.createBound(r, b,
                                                                   currentTime);
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
                          $unwrap(m.getThresholdContract(r, b, currentTime)));
                }
                adaptiveTimeout =
                  java.lang.Math.
                    min(
                      adaptiveTimeout,
                      fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
                          hedgedEstimate(m, normalized[0], normalized[1],
                                         currentTime, useWeakCache));
                double r2 = rate / numTerms;
                double b2 = scaledX -
                  (totalValue - baseNow) *
                  (totalNoise != 0
                     ? baseFactor / numTerms + newFactor *
                     (scaledN / totalNoise)
                     : 1.0 / numTerms);
                if (getUsePreset()) {
                    r2 = m.getPresetR();
                    b2 = scaledX - m.getPresetB() / getPresetB() *
                           (totalValue - baseNow);
                }
                double[] normalized2 =
                  fabric.metrics.contracts.Bound._Impl.createBound(r2, b2,
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
                                          $unwrap(m))))).implies(
                                                           m, normalized2[0],
                                                           normalized2[1])) {
                    staticWitnesses.
                      put(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.$unwrap(m),
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(m.getThresholdContract(r2, b2, currentTime)));
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
              new fabric.metrics.contracts.Contract[adaptiveWitnesses.size()];
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
                          fabric.lang.WrappedJavaInlineable.$wrap(iter.next()));
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
                          fabric.lang.WrappedJavaInlineable.$wrap(iter.next()));
                }
            }
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(s).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(
                finalWitnesses);
        }
        
        public int hashCode() {
            return fabric.util.Arrays._Impl.hashCode(this.get$terms()) * 32 +
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
                return fabric.util.Arrays._Impl.deepEquals(this.get$terms(),
                                                           that.get$terms()) &&
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
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.SumMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 1, 69, -94, 13, -48, 0,
    -46, -29, -72, 76, -58, 50, 69, 19, 3, 71, 122, -62, -116, -69, 89, 55, 90,
    77, 100, 90, -8, 83, -112, -119, 121, 114 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520199292000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
