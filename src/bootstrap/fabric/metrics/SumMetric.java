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
                    fabric.metrics.DerivedMetric val$var290 = val;
                    fabric.worker.transaction.TransactionManager $tm296 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled299 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff297 = 1;
                    boolean $doBackoff298 = true;
                    boolean $retry293 = true;
                    $label291: for (boolean $commit292 = false; !$commit292; ) {
                        if ($backoffEnabled299) {
                            if ($doBackoff298) {
                                if ($backoff297 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff297);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e294) {
                                            
                                        }
                                    }
                                }
                                if ($backoff297 < 5000) $backoff297 *= 2;
                            }
                            $doBackoff298 = $backoff297 <= 32 || !$doBackoff298;
                        }
                        $commit292 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e294) {
                            $commit292 = false;
                            continue $label291;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e294) {
                            $commit292 = false;
                            fabric.common.TransactionID $currentTid295 =
                              $tm296.getCurrentTid();
                            if ($e294.tid.isDescendantOf($currentTid295))
                                continue $label291;
                            if ($currentTid295.parent != null) {
                                $retry293 = false;
                                throw $e294;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e294) {
                            $commit292 = false;
                            if ($tm296.checkForStaleObjects())
                                continue $label291;
                            $retry293 = false;
                            throw new fabric.worker.AbortException($e294);
                        }
                        finally {
                            if ($commit292) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e294) {
                                    $commit292 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e294) {
                                    $commit292 = false;
                                    fabric.common.TransactionID $currentTid295 =
                                      $tm296.getCurrentTid();
                                    if ($currentTid295 != null) {
                                        if ($e294.tid.equals($currentTid295) ||
                                              !$e294.tid.isDescendantOf(
                                                           $currentTid295)) {
                                            throw $e294;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit292 && $retry293) {
                                { val = val$var290; }
                                continue $label291;
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
                    fabric.metrics.DerivedMetric val$var300 = val;
                    fabric.metrics.Metric[] newTerms$var301 = newTerms;
                    int termIdx$var302 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm308 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled311 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff309 = 1;
                    boolean $doBackoff310 = true;
                    boolean $retry305 = true;
                    $label303: for (boolean $commit304 = false; !$commit304; ) {
                        if ($backoffEnabled311) {
                            if ($doBackoff310) {
                                if ($backoff309 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff309);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e306) {
                                            
                                        }
                                    }
                                }
                                if ($backoff309 < 5000) $backoff309 *= 2;
                            }
                            $doBackoff310 = $backoff309 <= 32 || !$doBackoff310;
                        }
                        $commit304 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e306) {
                            $commit304 = false;
                            continue $label303;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e306) {
                            $commit304 = false;
                            fabric.common.TransactionID $currentTid307 =
                              $tm308.getCurrentTid();
                            if ($e306.tid.isDescendantOf($currentTid307))
                                continue $label303;
                            if ($currentTid307.parent != null) {
                                $retry305 = false;
                                throw $e306;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e306) {
                            $commit304 = false;
                            if ($tm308.checkForStaleObjects())
                                continue $label303;
                            $retry305 = false;
                            throw new fabric.worker.AbortException($e306);
                        }
                        finally {
                            if ($commit304) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e306) {
                                    $commit304 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e306) {
                                    $commit304 = false;
                                    fabric.common.TransactionID $currentTid307 =
                                      $tm308.getCurrentTid();
                                    if ($currentTid307 != null) {
                                        if ($e306.tid.equals($currentTid307) ||
                                              !$e306.tid.isDescendantOf(
                                                           $currentTid307)) {
                                            throw $e306;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit304 && $retry305) {
                                {
                                    val = val$var300;
                                    newTerms = newTerms$var301;
                                    termIdx = termIdx$var302;
                                }
                                continue $label303;
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
    public static final long jlc$SourceLastModified$fabil = 1518448064000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0ZbZAUR7V3b++TgzuOHB/HcRzHicXXrhAShNNEbuVjYYErDig9EtbZ2d67yc3ODDO9sEc8RasQoinUSCAYwShoDLmAlSqKsihSVCqJIIkKWgHLivDDKCkkJlImaiXG93p6v2Z3h1srFNOvt7vf6/fdr/tGbpFKyyQdcSmqqH42ZFDLv0KKhsI9kmnRWFCVLGsjjEbkMb7QgRtPx9q8xBsm9bKk6ZoiS2pEsxgZF35I2i4FNMoCmzaEuraQWhkRV0nWACPeLd0pk7QbujrUr+pMbFJA//G5gf0HtzY+X0Ea+kiDovUyiSlyUNcYTbE+Up+giSg1rWWxGI31kfEapbFeaiqSquyEhbrWR5ospV+TWNKk1gZq6ep2XNhkJQ1q8j3Tg8i+DmybSZnpJrDfaLOfZIoaCCsW6wqTqrhC1Zi1jXyF+MKkMq5K/bBwYjgtRYBTDKzAcVhepwCbZlySaRrFN6hoMUamOzEyEneugQWAWp2gbEDPbOXTJBggTTZLqqT1B3qZqWj9sLRST8IujLSUJAqLagxJHpT6aYSRyc51PfYUrKrlakEURpqdyzglsFmLw2Y51rq17jP7HtZWaV7iAZ5jVFaR/xpAanMgbaBxalJNpjZi/ZzwAWni2b1eQmBxs2Oxveb0l9/93Ly2c+ftNVOLrFkffYjKLCIfi4671BqcvaQC2agxdEtBV8iTnFu1R8x0pQzw9okZijjpT0+e2/DKF3cdpze9pC5EqmRdTSbAq8bLesJQVGqupBo1JUZjIVJLtViQz4dINfTDikbt0fXxuEVZiPhUPlSl89+gojiQQBVVQ1/R4nq6b0hsgPdTBiGkGj7igf9fIGTBq9CfRIj3WUZWBgb0BA1E1STdAe4dgI9KpjwQgLg1FTlgmXLATGpMgUViCLwIgBXoTSbW8q4fWDA+PlIp5Lpxh8cDCp0u6zEalSywjvCU7h4VgmGVrsaoGZHVfWdDZMLZQ9xbatHDLfBSrg8PWLjVmRtycfcnu5e/eyJy0fY0xBXqAivb/PkFf/4Mf8BSPcaPHzKSHzLSiCflDx4JPcvdpMri8ZShUg9UlhqqxOK6mUgRj4eLdBfH5/4B1h2ErAGJoX5274Orv7S3owIc09jhQ1vB0k5nmGSTSwh6Evh+RG7Yc+O9kweG9WzAMNJZEMeFmBiHHU79mLpMY5DnsuTntEunImeHO72YQ2ohvTEJHBByRZtzj7x47ErnNtRGZZiMQR1IKk6lE1IdGzD1HdkRbvdx2DTZLoDKcjDI0+Jne43DV3/91t38wEhn0IacVNtLWVdO1CKxBh6f47O632hSCuveeKLne4/f2rOFKx5WzCy2YSe2QYhWCcJUN3ef3/aHa3869ntv1liMVBnJqKrIKS7L+I/gnwe+/+KHoYcDCCEBB0XYt2fi3sCdZ2V5gwygQhYC1q3OTVpCjylxRYqqFD3lg4ZPLDj1t32NtrlVGLGVZ5J5dyaQHZ/STXZd3Pp+GyfjkfEEyuovu8xOaxOylJeZpjSEfKS+dnnaoV9Kh8HzISlZyk7K8wzh+iDcgAu5LubzdoFjbhE2Hba2WsU4/zGTt7OwmW3rFrtzhF6J+FclMthxAY/h7AQD27vyaZpkWqnDhh+Ux76+/0hs/U8W2EdCU34CX64lE8+9/uGr/ieuXyiSJmqZbsxX6Xaq5uyJbjujoOpZy8/ibFhdvzltSXDwzX572+kOFp2rn1k7cmHlLPkxL6nIxHhBAZCP1JXLLASbSaF+0VBsHKnjRmjPKHUMKms5fNMJqfiVgN/JUaqISG4hbBZnULme6wTKtwV8xGmPrBd4Multaq6WVoOLceeyT+qtEKq/Hfr7AVs/znohZ+E7I9duXh477QTPTz48Krh8zkKrsI7KK4+4ePUZme5Bmbrgu4+QmlYBJzKy5v8/4j4PJSWUiHkn5sdJzg6QZii6HGeXvQQnW4rYwFlUrUClZR2vLzDyg5bgfTft8zWT/ZHOjCLn62Yp52BaeDzxT29H1cteUt1HGnlhLGlsswTSQuLtA5NYQTEYJmPz5vPLVLsm68p4fqvT83O2dZ47uTHgY3nez4+aNSkP4W66qXj28fLsw4Cookl2aTIXkr1KtX42UER/PaaSgENnuyhK6d793/zIv2+/nTzsyn1mQfGci2NX73yjsXw3TGEz3HbhGCv+enL4zM+G93hFfu1ipAJcHrurUxm7e22Z0q5i53XUMSRFXaN4RPC5KZDbsHhRdbiIZTzLrlwU3Z+5HkXt0lNOFXgW/r7fVjDnISdzcKlcjoZBl7kENnD1q5SR3zRjjVk5bF+xmeIYK12ocT/uZmSKHTKdImQ6M+VeZzbr3Z+fKzvgmwXJTxJwdXm5ElFCAgZL58pcZodc5h7GhsF1Ga8UcJXrwUzCNvC1fcKLEDwIrhvTk2kzF5EpQEjlEgE7ypMJUWYIOHV0Mu12mduDzS6nTN04OlyK+09DZTDehpX/KY97RPm3gLdHx/0+lzl+dj7i5H6zK/fLgPu3BLxcHveIcknAi6Pj/qDL3CFsHnNyv64Y9+MQaTF8q+GS+bqAz5XgvqC2gzxjmDqDkKWxVL5YYwWtEQGPlhYrN+k43L06qusqlTTOxY9dRH4amycZvuBwkfk5VFLgpfD1Qk3wmoA/cjHXkUK5EOUpAQ/eUS78eZRTPeEiwM+xeQYuQmkBKGRvhQ25Gu0BQmpfEvDJ8mRAlO8L+N0yZDjtIsMvsHk+a4R1umIVNQKPGSzMKPj/YQEfLS9mEOVbAu4eXcycc5l7EZszjNQw3X5XK3I65UxMcb4PFJNwPnw7Qd0vCvhUeRIiyg8FPDQqEw1zqq+5iPkbbM7DCYxVqZWWsdVRdOaVp7imxSHeBCS4Bj6wQMN7Ar4wyrThhbg2YAO4OzN8dcH3XEf2aBIkzwp4srT43myh15jVwR9ddPAGNr8DA9pbR7gqcOxSMSPOhe8oSPwNAePlGRFRqICRURlxHaf6ZxcB/oLNNUZ8hposyjg3DxYopwhp/kDAV0ZrHuxeweZqEasgpZcFPD16q9hCve0i1DvY3GBkjLBKKdm4UaKgK+h0PirgPWUZhaMsEtBfWggfZ8/HvSrTHE0HzQQRNDt0c5CakBx0M1N+5+cGztH7LrJ/iM0/8BVKVxV5KL3DvY6wxAucKcnM8lMNCMs0QTXmX57t93D0YgHLtTYFRIag/VS3gIvK0xqi3C3g/FFlXE+Vy1wNNlBC1AxI1kBQj/GT4oFifH8SNu2BTS8IWKo+KcE3oowI+NM7hmBa+U1C+Tn3ktLW9TS4CNqMTR1Yl25LSvZb09EUlE6Zewo+0k0t8lQu/mAjB1+ix95cM6+5xDP55II/oQm8E0caaiYd2XTFflxJ/zGmNkxq4klVzb1a5/SrDJPGFa7WWvuibXApWqCezPdHxh9hsIcyeSbb66aBoPY6/NXGVd2SaS5xki1JE//8N3J70r+qajZe5y+xoK32hV9dfLvtoNlzZevIzuPGwVVbbrw98sLMM1eru5ZGzC3qvf8D+SiPjpYcAAA=";
}
