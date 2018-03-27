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
    
    public static final byte[] $classHash = new byte[] { 36, 10, 65, 66, 22,
    -22, 101, -52, -72, -52, -128, 53, -46, 18, -59, -7, -79, 19, -3, -109, -95,
    -58, 64, 10, 78, 88, 70, -40, 40, 126, 22, -89 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1522102686000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
