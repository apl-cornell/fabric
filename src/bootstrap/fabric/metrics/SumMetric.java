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
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
      double rate, double base, boolean useWeakCache,
      final fabric.worker.Store s);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy extends fabric.metrics.DerivedMetric._Proxy
      implements fabric.metrics.SumMetric {
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
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          double rate, double base, boolean useWeakCache,
          final fabric.worker.Store s) {
            java.util.Map witnesses = new java.util.HashMap();
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
            for (int j = 0; j < numTerms; j++) {
                fabric.metrics.Metric m = term(j);
                double scaledX = m.value(useWeakCache);
                double scaledV = m.velocity(useWeakCache);
                double scaledN = m.noise(useWeakCache);
                double r = scaledV * newFactor -
                  (totalVelocity * newFactor - rate) / numTerms;
                double b = scaledX -
                  (baseFactor / numTerms + newFactor * (scaledN / totalNoise)) *
                  (totalValue - baseNow);
                if (totalNoise != 0) {
                    b = scaledX - scaledN / totalNoise * (totalValue - baseNow);
                }
                if (getUsePreset()) {
                    r = m.getPresetR();
                    b = scaledX - m.getPresetB() / getPresetB() *
                          (totalValue - baseNow);
                }
                double[] normalized =
                  fabric.metrics.contracts.Bound._Impl.createBound(r, b,
                                                                   currentTime);
                if (!witnesses.
                      containsKey(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.$unwrap(m)) ||
                      !((fabric.metrics.contracts.MetricContract)
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
                          $unwrap(m.getContract(r, b, currentTime)));
                }
            }
            fabric.metrics.contracts.MetricContract[] finalWitnesses =
              new fabric.metrics.contracts.MetricContract[witnesses.size()];
            int i = 0;
            for (java.util.Iterator iter = witnesses.values().iterator();
                 iter.hasNext(); ) {
                finalWitnesses[i++] =
                  (fabric.metrics.contracts.MetricContract)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      fabric.lang.WrappedJavaInlineable.$wrap(iter.next()));
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
    
    public static final byte[] $classHash = new byte[] { 50, 127, 55, -13, 30,
    -105, 114, 80, -41, 94, -84, 122, -86, 112, -105, 72, 91, -24, -18, -84,
    -66, 35, -69, -40, 7, 59, 58, 95, 114, 91, 108, 54 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519057402000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
