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
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
      double rate, double base, boolean useWeakCache,
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
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          double rate, double base, boolean useWeakCache,
          final fabric.worker.Store s) {
            java.util.Map adaptiveWitnesses = new java.util.HashMap();
            java.util.Map staticWitnesses = new java.util.HashMap();
            long currentTime = java.lang.System.currentTimeMillis();
            double baseNow =
              fabric.metrics.contracts.Bound._Impl.value(rate, base,
                                                         currentTime);
            double totalSamples = weakSamples();
            double totalValue = value(useWeakCache);
            double totalVelocity = velocity(useWeakCache);
            double totalNoise = noise(useWeakCache);
            double numTerms = this.get$terms().get$length();
            double baseFactor = totalSamples > 10
              ? 1.0 / java.lang.Math.sqrt(totalSamples)
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
                          $unwrap(m.getContract(r, b, currentTime)));
                }
                adaptiveTimeout =
                  java.lang.Math.
                    min(
                      adaptiveTimeout,
                      fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
                          hedged(m, normalized[0], normalized[1], currentTime));
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
                          $unwrap(m.getContract(r2, b2, currentTime)));
                }
                staticTimeout =
                  java.lang.Math.
                    min(
                      staticTimeout,
                      fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
                          hedged(m, normalized2[0], normalized2[1],
                                 currentTime));
            }
            fabric.metrics.contracts.Contract[] finalWitnesses =
              new fabric.metrics.contracts.Contract[adaptiveWitnesses.size()];
            if (adaptiveTimeout > staticTimeout || adaptiveTimeout -
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
    
    public static final byte[] $classHash = new byte[] { 4, -108, -11, -7, 35,
    -89, 31, -76, 2, -30, 51, -52, -72, -83, -43, -108, 91, 98, 20, -112, 24,
    -103, 117, 90, 46, 72, -31, -89, 35, -32, 43, -34 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519498081000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
