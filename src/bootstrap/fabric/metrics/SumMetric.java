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
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;

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
      fabric.lang.arrays.ObjectArray terms);
    
    public double computePresetR();
    
    public double computePresetB();
    
    public double computePresetV();
    
    public double computePresetN();
    
    public double computeValue(boolean useWeakCache);
    
    public double computeVelocity(boolean useWeakCache);
    
    public double computeNoise(boolean useWeakCache);
    
    public java.lang.String toString();
    
    /**
     * {@inheritDoc}
     * <p>
     * {@link SumMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
      fabric.metrics.contracts.Bound bound, boolean useWeakCache);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy extends fabric.metrics.DerivedMetric._Proxy
      implements fabric.metrics.SumMetric {
        public fabric.metrics.SumMetric fabric$metrics$SumMetric$(
          fabric.lang.arrays.ObjectArray arg1) {
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
          fabric.lang.arrays.ObjectArray terms) {
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
        
        /**
     * {@inheritDoc}
     * <p>
     * {@link SumMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            final fabric.worker.Store s = $getStore();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.SumMetric &&
                  other.$getStore().equals(s)) {
                fabric.metrics.SumMetric that =
                  (fabric.metrics.SumMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                fabric.metrics.DerivedMetric result = (fabric.metrics.SumMetric)
                                                        this.$getProxy();
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
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    if (!((fabric.metrics.Metric) this.get$terms().get(i)).
                          $getStore().equals(other.$getStore()))
                        continue;
                    if (fabric.lang.Object._Proxy.
                          $getProxy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                (fabric.metrics.Metric) this.get$terms().get(i))) instanceof fabric.metrics.DerivedMetric) {
                        fabric.metrics.DerivedMetric derivedTerm =
                          (fabric.metrics.DerivedMetric)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.metrics.Metric)
                                                          this.get$terms(
                                                                 ).get(i));
                        if (derivedTerm.getLeafSubjects().
                              containsAll(derivedOther.getLeafSubjects())) {
                            termIdx = i;
                            break;
                        }
                    }
                    else {
                        fabric.metrics.SampledMetric sampledTerm =
                          (fabric.metrics.SampledMetric)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.metrics.Metric)
                                                          this.get$terms(
                                                                 ).get(i));
                        if (derivedOther.getLeafSubjects().size() ==
                              1 &&
                              derivedOther.getLeafSubjects().contains(
                                                               sampledTerm)) {
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
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    if (!((fabric.metrics.Metric) this.get$terms().get(i)).
                          $getStore().equals(other.$getStore()))
                        continue;
                    if (fabric.lang.Object._Proxy.
                          $getProxy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                (fabric.metrics.Metric) this.get$terms().get(i))) instanceof fabric.metrics.DerivedMetric) {
                        fabric.metrics.DerivedMetric derivedTerm =
                          (fabric.metrics.DerivedMetric)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.metrics.Metric)
                                                          this.get$terms(
                                                                 ).get(i));
                        if (derivedTerm.getLeafSubjects().contains(
                                                            sampledOther)) {
                            termIdx = i;
                            break;
                        }
                    }
                    else {
                        fabric.metrics.SampledMetric sampledTerm =
                          (fabric.metrics.SampledMetric)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.metrics.Metric)
                                                          this.get$terms(
                                                                 ).get(i));
                        if (sampledTerm.equals(sampledOther)) {
                            termIdx = i;
                            break;
                        }
                    }
                }
            }
            fabric.lang.arrays.ObjectArray newTerms = null;
            if (termIdx >= 0) {
                {
                    fabric.lang.arrays.ObjectArray newTerms$var261 = newTerms;
                    int termIdx$var262 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm267 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled270 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff268 = 1;
                    boolean $doBackoff269 = true;
                    $label263: for (boolean $commit264 = false; !$commit264; ) {
                        if ($backoffEnabled270) {
                            if ($doBackoff269) {
                                if ($backoff268 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff268);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e265) {
                                            
                                        }
                                    }
                                }
                                if ($backoff268 < 5000) $backoff268 *= 2;
                            }
                            $doBackoff269 = $backoff268 <= 32 || !$doBackoff269;
                        }
                        $commit264 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            newTerms =
                              (fabric.lang.arrays.ObjectArray)
                                new fabric.lang.arrays.ObjectArray._Impl(
                                  this.$getStore()).
                                fabric$lang$arrays$ObjectArray$(
                                  this.get$$updateLabel(),
                                  this.get$$updateLabel().confPolicy(),
                                  fabric.metrics.Metric._Proxy.class,
                                  this.get$terms().get$length()).$getProxy();
                        }
                        catch (final fabric.worker.RetryException $e265) {
                            $commit264 = false;
                            continue $label263;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e265) {
                            $commit264 = false;
                            fabric.common.TransactionID $currentTid266 =
                              $tm267.getCurrentTid();
                            if ($e265.tid.isDescendantOf($currentTid266))
                                continue $label263;
                            if ($currentTid266.parent != null) throw $e265;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e265) {
                            $commit264 = false;
                            if ($tm267.checkForStaleObjects())
                                continue $label263;
                            throw new fabric.worker.AbortException($e265);
                        }
                        finally {
                            if ($commit264) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e265) {
                                    $commit264 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e265) {
                                    $commit264 = false;
                                    fabric.common.TransactionID $currentTid266 =
                                      $tm267.getCurrentTid();
                                    if ($currentTid266 != null) {
                                        if ($e265.tid.equals($currentTid266) ||
                                              !$e265.tid.isDescendantOf(
                                                           $currentTid266)) {
                                            throw $e265;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit264) {
                                {
                                    newTerms = newTerms$var261;
                                    termIdx = termIdx$var262;
                                }
                                continue $label263;
                            }
                        }
                    }
                }
                fabric.util.Arrays._Impl.arraycopy(
                                           this.get$terms(), 0, newTerms, 0,
                                           this.get$terms().get$length());
                newTerms.set(termIdx,
                             ((fabric.metrics.Metric)
                                newTerms.get(termIdx)).plus(other));
            }
            else {
                {
                    fabric.lang.arrays.ObjectArray newTerms$var271 = newTerms;
                    int termIdx$var272 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm277 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled280 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff278 = 1;
                    boolean $doBackoff279 = true;
                    $label273: for (boolean $commit274 = false; !$commit274; ) {
                        if ($backoffEnabled280) {
                            if ($doBackoff279) {
                                if ($backoff278 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff278);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e275) {
                                            
                                        }
                                    }
                                }
                                if ($backoff278 < 5000) $backoff278 *= 2;
                            }
                            $doBackoff279 = $backoff278 <= 32 || !$doBackoff279;
                        }
                        $commit274 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            newTerms =
                              (fabric.lang.arrays.ObjectArray)
                                new fabric.lang.arrays.ObjectArray._Impl(
                                  this.$getStore()).
                                fabric$lang$arrays$ObjectArray$(
                                  this.get$$updateLabel(),
                                  this.get$$updateLabel().confPolicy(),
                                  fabric.metrics.Metric._Proxy.class,
                                  this.get$terms().get$length() + 1).$getProxy(
                                                                       );
                        }
                        catch (final fabric.worker.RetryException $e275) {
                            $commit274 = false;
                            continue $label273;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e275) {
                            $commit274 = false;
                            fabric.common.TransactionID $currentTid276 =
                              $tm277.getCurrentTid();
                            if ($e275.tid.isDescendantOf($currentTid276))
                                continue $label273;
                            if ($currentTid276.parent != null) throw $e275;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e275) {
                            $commit274 = false;
                            if ($tm277.checkForStaleObjects())
                                continue $label273;
                            throw new fabric.worker.AbortException($e275);
                        }
                        finally {
                            if ($commit274) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e275) {
                                    $commit274 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e275) {
                                    $commit274 = false;
                                    fabric.common.TransactionID $currentTid276 =
                                      $tm277.getCurrentTid();
                                    if ($currentTid276 != null) {
                                        if ($e275.tid.equals($currentTid276) ||
                                              !$e275.tid.isDescendantOf(
                                                           $currentTid276)) {
                                            throw $e275;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit274) {
                                {
                                    newTerms = newTerms$var271;
                                    termIdx = termIdx$var272;
                                }
                                continue $label273;
                            }
                        }
                    }
                }
                fabric.util.Arrays._Impl.arraycopy(
                                           this.get$terms(), 0, newTerms, 0,
                                           this.get$terms().get$length());
                newTerms.set(this.get$terms().get$length(), other);
                fabric.util.Arrays._Impl.sort(newTerms, 0,
                                              newTerms.get$length());
            }
            fabric.metrics.DerivedMetric val = null;
            {
                fabric.metrics.DerivedMetric val$var281 = val;
                fabric.lang.arrays.ObjectArray newTerms$var282 = newTerms;
                int termIdx$var283 = termIdx;
                fabric.worker.transaction.TransactionManager $tm288 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled291 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff289 = 1;
                boolean $doBackoff290 = true;
                $label284: for (boolean $commit285 = false; !$commit285; ) {
                    if ($backoffEnabled291) {
                        if ($doBackoff290) {
                            if ($backoff289 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff289);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e286) {
                                        
                                    }
                                }
                            }
                            if ($backoff289 < 5000) $backoff289 *= 2;
                        }
                        $doBackoff290 = $backoff289 <= 32 || !$doBackoff290;
                    }
                    $commit285 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.SumMetric)
                             new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                            fabric$metrics$SumMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e286) {
                        $commit285 = false;
                        continue $label284;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e286) {
                        $commit285 = false;
                        fabric.common.TransactionID $currentTid287 =
                          $tm288.getCurrentTid();
                        if ($e286.tid.isDescendantOf($currentTid287))
                            continue $label284;
                        if ($currentTid287.parent != null) throw $e286;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e286) {
                        $commit285 = false;
                        if ($tm288.checkForStaleObjects()) continue $label284;
                        throw new fabric.worker.AbortException($e286);
                    }
                    finally {
                        if ($commit285) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e286) {
                                $commit285 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e286) {
                                $commit285 = false;
                                fabric.common.TransactionID $currentTid287 =
                                  $tm288.getCurrentTid();
                                if ($currentTid287 != null) {
                                    if ($e286.tid.equals($currentTid287) ||
                                          !$e286.tid.isDescendantOf(
                                                       $currentTid287)) {
                                        throw $e286;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit285) {
                            {
                                val = val$var281;
                                newTerms = newTerms$var282;
                                termIdx = termIdx$var283;
                            }
                            continue $label284;
                        }
                    }
                }
            }
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          fabric.metrics.contracts.Bound bound, boolean useWeakCache) {
            if (isSingleStore())
                return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                          new fabric.metrics.contracts.enforcement.DirectPolicy.
                            _Impl(this.$getStore()).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$DirectPolicy$(
                    (fabric.metrics.SumMetric) this.$getProxy(), bound);
            fabric.util.Map witnesses =
              ((fabric.util.LinkedHashMap)
                 new fabric.util.LinkedHashMap._Impl(
                   this.$getStore()).$getProxy()).fabric$util$LinkedHashMap$();
            long currentTime = java.lang.System.currentTimeMillis();
            double base = bound.value(currentTime);
            double rate = bound.get$rate();
            double totalValue = value(useWeakCache);
            double totalVelocity = velocity(useWeakCache);
            double totalNoise = noise(useWeakCache);
            double numTerms = this.get$terms().get$length();
            for (int j = 0; j < numTerms; j++) {
                fabric.metrics.Metric m = term(j);
                double scaledX = m.value(useWeakCache);
                double scaledV = m.velocity(useWeakCache);
                double scaledN = m.noise(useWeakCache);
                double r = scaledV - (totalVelocity - rate) / numTerms;
                double b = scaledX - 1.0 / numTerms * (totalValue - base);
                if (totalNoise != 0) {
                    b = scaledX - scaledN / totalNoise * (totalValue - base);
                }
                if (getUsePreset()) {
                    r = m.getPresetR();
                    b = scaledX - m.getPresetB() / getPresetB() *
                          (totalValue - base);
                }
                fabric.metrics.contracts.Bound witnessBound =
                  ((fabric.metrics.contracts.Bound)
                     new fabric.metrics.contracts.Bound._Impl(
                       this.$getStore()).$getProxy()).
                  fabric$metrics$contracts$Bound$(r, b, currentTime);
                if (!witnesses.containsKey(m) ||
                      !((fabric.metrics.contracts.MetricContract)
                          fabric.lang.Object._Proxy.$getProxy(
                                                      witnesses.get(m))).
                      getBound().implies(witnessBound)) {
                    witnesses.put(m, m.getContract(witnessBound));
                }
            }
            fabric.lang.arrays.ObjectArray
              finalWitnesses =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.
                      $getStore()).
                fabric$lang$arrays$ObjectArray$(
                  this.get$$updateLabel(), this.get$$updateLabel().confPolicy(),
                  fabric.metrics.contracts.MetricContract._Proxy.class,
                  witnesses.size()).$getProxy();
            int i = 0;
            for (fabric.util.Iterator iter = witnesses.values().iterator();
                 iter.hasNext(); ) {
                finalWitnesses.set(
                                 i++,
                                 (fabric.metrics.contracts.MetricContract)
                                   fabric.lang.Object._Proxy.$getProxy(
                                                               iter.next()));
            }
            final fabric.worker.Store bndStore = bound.getStore();
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(bndStore).
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
    
    public static final byte[] $classHash = new byte[] { 69, 105, -109, -49,
    122, -8, -64, 0, 0, -45, -95, -74, -92, 36, 17, 123, -44, -37, -2, -11, 50,
    56, -119, 64, -109, 24, -123, -15, -63, 22, -19, 114 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506966071000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
