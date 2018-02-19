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
import fabric.metrics.contracts.MetricContract;
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
                      !((fabric.metrics.contracts.MetricContract)
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
                      !((fabric.metrics.contracts.MetricContract)
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
            fabric.metrics.contracts.MetricContract[]
              finalWitnesses =
              new fabric.metrics.contracts.MetricContract[adaptiveWitnesses.
                                                            size()];
            if (adaptiveTimeout > staticTimeout || adaptiveTimeout -
                  java.lang.System.currentTimeMillis() >
                  this.get$MIN_ADAPTIVE_EXPIRY()) {
                int i = 0;
                for (java.util.Iterator iter =
                       adaptiveWitnesses.values().iterator();
                     iter.hasNext();
                     ) {
                    finalWitnesses[i++] =
                      (fabric.metrics.contracts.MetricContract)
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
                      (fabric.metrics.contracts.MetricContract)
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
    
    public static final byte[] $classHash = new byte[] { -23, -121, -12, -75,
    87, 94, -27, 42, -30, -67, -93, -126, -45, -107, 126, -78, -38, 94, -113,
    -4, 44, -62, -19, -127, 119, -66, -94, -114, -93, 9, -127, -99 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519058682000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
