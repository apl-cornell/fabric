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
                    fabric.metrics.DerivedMetric val$var80 = val;
                    fabric.worker.transaction.TransactionManager $tm86 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled89 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff87 = 1;
                    boolean $doBackoff88 = true;
                    boolean $retry83 = true;
                    $label81: for (boolean $commit82 = false; !$commit82; ) {
                        if ($backoffEnabled89) {
                            if ($doBackoff88) {
                                if ($backoff87 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff87);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e84) {
                                            
                                        }
                                    }
                                }
                                if ($backoff87 < 5000) $backoff87 *= 2;
                            }
                            $doBackoff88 = $backoff87 <= 32 || !$doBackoff88;
                        }
                        $commit82 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e84) {
                            $commit82 = false;
                            continue $label81;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e84) {
                            $commit82 = false;
                            fabric.common.TransactionID $currentTid85 =
                              $tm86.getCurrentTid();
                            if ($e84.tid.isDescendantOf($currentTid85))
                                continue $label81;
                            if ($currentTid85.parent != null) {
                                $retry83 = false;
                                throw $e84;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e84) {
                            $commit82 = false;
                            if ($tm86.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid85 =
                              $tm86.getCurrentTid();
                            if ($e84.tid.isDescendantOf($currentTid85)) {
                                $retry83 = true;
                            }
                            else if ($currentTid85.parent != null) {
                                $retry83 = false;
                                throw $e84;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e84) {
                            $commit82 = false;
                            if ($tm86.checkForStaleObjects()) continue $label81;
                            $retry83 = false;
                            throw new fabric.worker.AbortException($e84);
                        }
                        finally {
                            if ($commit82) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e84) {
                                    $commit82 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e84) {
                                    $commit82 = false;
                                    fabric.common.TransactionID $currentTid85 =
                                      $tm86.getCurrentTid();
                                    if ($currentTid85 != null) {
                                        if ($e84.tid.equals($currentTid85) ||
                                              !$e84.tid.isDescendantOf(
                                                          $currentTid85)) {
                                            throw $e84;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit82 && $retry83) {
                                { val = val$var80; }
                                continue $label81;
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
                    fabric.metrics.DerivedMetric val$var90 = val;
                    fabric.metrics.Metric[] newTerms$var91 = newTerms;
                    int termIdx$var92 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm98 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled101 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff99 = 1;
                    boolean $doBackoff100 = true;
                    boolean $retry95 = true;
                    $label93: for (boolean $commit94 = false; !$commit94; ) {
                        if ($backoffEnabled101) {
                            if ($doBackoff100) {
                                if ($backoff99 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff99);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e96) {
                                            
                                        }
                                    }
                                }
                                if ($backoff99 < 5000) $backoff99 *= 2;
                            }
                            $doBackoff100 = $backoff99 <= 32 || !$doBackoff100;
                        }
                        $commit94 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e96) {
                            $commit94 = false;
                            continue $label93;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e96) {
                            $commit94 = false;
                            fabric.common.TransactionID $currentTid97 =
                              $tm98.getCurrentTid();
                            if ($e96.tid.isDescendantOf($currentTid97))
                                continue $label93;
                            if ($currentTid97.parent != null) {
                                $retry95 = false;
                                throw $e96;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e96) {
                            $commit94 = false;
                            if ($tm98.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid97 =
                              $tm98.getCurrentTid();
                            if ($e96.tid.isDescendantOf($currentTid97)) {
                                $retry95 = true;
                            }
                            else if ($currentTid97.parent != null) {
                                $retry95 = false;
                                throw $e96;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e96) {
                            $commit94 = false;
                            if ($tm98.checkForStaleObjects()) continue $label93;
                            $retry95 = false;
                            throw new fabric.worker.AbortException($e96);
                        }
                        finally {
                            if ($commit94) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e96) {
                                    $commit94 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e96) {
                                    $commit94 = false;
                                    fabric.common.TransactionID $currentTid97 =
                                      $tm98.getCurrentTid();
                                    if ($currentTid97 != null) {
                                        if ($e96.tid.equals($currentTid97) ||
                                              !$e96.tid.isDescendantOf(
                                                          $currentTid97)) {
                                            throw $e96;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit94 && $retry95) {
                                {
                                    val = val$var90;
                                    newTerms = newTerms$var91;
                                    termIdx = termIdx$var92;
                                }
                                continue $label93;
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
            double baseFactor = 0.0;
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
    
    public static final byte[] $classHash = new byte[] { 127, 103, -49, 47, -19,
    121, -110, -80, -34, -91, 99, -101, 52, 80, -54, -12, 65, -91, -106, -91,
    88, -56, 55, -15, 53, -6, -39, -126, -27, 126, 22, 103 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1522535884000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
