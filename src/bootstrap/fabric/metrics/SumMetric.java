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
                    fabric.metrics.DerivedMetric val$var328 = val;
                    fabric.worker.transaction.TransactionManager $tm334 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled337 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff335 = 1;
                    boolean $doBackoff336 = true;
                    boolean $retry331 = true;
                    $label329: for (boolean $commit330 = false; !$commit330; ) {
                        if ($backoffEnabled337) {
                            if ($doBackoff336) {
                                if ($backoff335 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff335);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e332) {
                                            
                                        }
                                    }
                                }
                                if ($backoff335 < 5000) $backoff335 *= 2;
                            }
                            $doBackoff336 = $backoff335 <= 32 || !$doBackoff336;
                        }
                        $commit330 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e332) {
                            $commit330 = false;
                            continue $label329;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e332) {
                            $commit330 = false;
                            fabric.common.TransactionID $currentTid333 =
                              $tm334.getCurrentTid();
                            if ($e332.tid.isDescendantOf($currentTid333))
                                continue $label329;
                            if ($currentTid333.parent != null) {
                                $retry331 = false;
                                throw $e332;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e332) {
                            $commit330 = false;
                            if ($tm334.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid333 =
                              $tm334.getCurrentTid();
                            if ($e332.tid.isDescendantOf($currentTid333)) {
                                $retry331 = true;
                            }
                            else if ($currentTid333.parent != null) {
                                $retry331 = false;
                                throw $e332;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e332) {
                            $commit330 = false;
                            if ($tm334.checkForStaleObjects())
                                continue $label329;
                            $retry331 = false;
                            throw new fabric.worker.AbortException($e332);
                        }
                        finally {
                            if ($commit330) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e332) {
                                    $commit330 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e332) {
                                    $commit330 = false;
                                    fabric.common.TransactionID $currentTid333 =
                                      $tm334.getCurrentTid();
                                    if ($currentTid333 != null) {
                                        if ($e332.tid.equals($currentTid333) ||
                                              !$e332.tid.isDescendantOf(
                                                           $currentTid333)) {
                                            throw $e332;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit330 && $retry331) {
                                { val = val$var328; }
                                continue $label329;
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
                    fabric.metrics.DerivedMetric val$var338 = val;
                    fabric.metrics.Metric[] newTerms$var339 = newTerms;
                    int termIdx$var340 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm346 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled349 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff347 = 1;
                    boolean $doBackoff348 = true;
                    boolean $retry343 = true;
                    $label341: for (boolean $commit342 = false; !$commit342; ) {
                        if ($backoffEnabled349) {
                            if ($doBackoff348) {
                                if ($backoff347 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff347);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e344) {
                                            
                                        }
                                    }
                                }
                                if ($backoff347 < 5000) $backoff347 *= 2;
                            }
                            $doBackoff348 = $backoff347 <= 32 || !$doBackoff348;
                        }
                        $commit342 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e344) {
                            $commit342 = false;
                            continue $label341;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e344) {
                            $commit342 = false;
                            fabric.common.TransactionID $currentTid345 =
                              $tm346.getCurrentTid();
                            if ($e344.tid.isDescendantOf($currentTid345))
                                continue $label341;
                            if ($currentTid345.parent != null) {
                                $retry343 = false;
                                throw $e344;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e344) {
                            $commit342 = false;
                            if ($tm346.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid345 =
                              $tm346.getCurrentTid();
                            if ($e344.tid.isDescendantOf($currentTid345)) {
                                $retry343 = true;
                            }
                            else if ($currentTid345.parent != null) {
                                $retry343 = false;
                                throw $e344;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e344) {
                            $commit342 = false;
                            if ($tm346.checkForStaleObjects())
                                continue $label341;
                            $retry343 = false;
                            throw new fabric.worker.AbortException($e344);
                        }
                        finally {
                            if ($commit342) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e344) {
                                    $commit342 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e344) {
                                    $commit342 = false;
                                    fabric.common.TransactionID $currentTid345 =
                                      $tm346.getCurrentTid();
                                    if ($currentTid345 != null) {
                                        if ($e344.tid.equals($currentTid345) ||
                                              !$e344.tid.isDescendantOf(
                                                           $currentTid345)) {
                                            throw $e344;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit342 && $retry343) {
                                {
                                    val = val$var338;
                                    newTerms = newTerms$var339;
                                    termIdx = termIdx$var340;
                                }
                                continue $label341;
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
    
    public static final byte[] $classHash = new byte[] { 37, -17, 36, 52, -24,
    90, -37, 3, -99, -15, 38, 94, -62, -69, 50, -89, 100, -41, 95, -93, 42, -76,
    42, -23, 9, 69, -28, 30, 20, -80, -24, 5 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521305458000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
