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
                                                          tmp.get$terms().get(
                                                                            i));
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
                                                          tmp.get$terms().get(
                                                                            i));
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
                    fabric.lang.arrays.ObjectArray newTerms$var316 = newTerms;
                    int termIdx$var317 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm322 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled325 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff323 = 1;
                    boolean $doBackoff324 = true;
                    $label318: for (boolean $commit319 = false; !$commit319; ) {
                        if ($backoffEnabled325) {
                            if ($doBackoff324) {
                                if ($backoff323 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff323);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e320) {
                                            
                                        }
                                    }
                                }
                                if ($backoff323 < 5000) $backoff323 *= 2;
                            }
                            $doBackoff324 = $backoff323 <= 32 || !$doBackoff324;
                        }
                        $commit319 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            newTerms =
                              (fabric.lang.arrays.ObjectArray)
                                new fabric.lang.arrays.ObjectArray._Impl(
                                  fabric.metrics.SumMetric._Static._Proxy.
                                    $instance.
                                      $getStore()).
                                fabric$lang$arrays$ObjectArray$(
                                  fabric.metrics.SumMetric._Static._Proxy.
                                    $instance.
                                      get$$updateLabel(),
                                  fabric.metrics.SumMetric._Static._Proxy.
                                    $instance.
                                      get$$updateLabel().
                                      confPolicy(),
                                  fabric.metrics.Metric._Proxy.class,
                                  tmp.get$terms().get$length()).$getProxy();
                        }
                        catch (final fabric.worker.RetryException $e320) {
                            $commit319 = false;
                            continue $label318;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e320) {
                            $commit319 = false;
                            fabric.common.TransactionID $currentTid321 =
                              $tm322.getCurrentTid();
                            if ($e320.tid.isDescendantOf($currentTid321))
                                continue $label318;
                            if ($currentTid321.parent != null) throw $e320;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e320) {
                            $commit319 = false;
                            if ($tm322.checkForStaleObjects())
                                continue $label318;
                            throw new fabric.worker.AbortException($e320);
                        }
                        finally {
                            if ($commit319) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e320) {
                                    $commit319 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e320) {
                                    $commit319 = false;
                                    fabric.common.TransactionID $currentTid321 =
                                      $tm322.getCurrentTid();
                                    if ($currentTid321 != null) {
                                        if ($e320.tid.equals($currentTid321) ||
                                              !$e320.tid.isDescendantOf(
                                                           $currentTid321)) {
                                            throw $e320;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit319) {
                                {
                                    newTerms = newTerms$var316;
                                    termIdx = termIdx$var317;
                                }
                                continue $label318;
                            }
                        }
                    }
                }
                fabric.util.Arrays._Impl.arraycopy(
                                           tmp.get$terms(), 0, newTerms, 0,
                                           tmp.get$terms().get$length());
                newTerms.set(termIdx,
                             ((fabric.metrics.Metric)
                                newTerms.get(termIdx)).plus(other));
            }
            else {
                {
                    fabric.lang.arrays.ObjectArray newTerms$var326 = newTerms;
                    int termIdx$var327 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm332 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled335 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff333 = 1;
                    boolean $doBackoff334 = true;
                    $label328: for (boolean $commit329 = false; !$commit329; ) {
                        if ($backoffEnabled335) {
                            if ($doBackoff334) {
                                if ($backoff333 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff333);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e330) {
                                            
                                        }
                                    }
                                }
                                if ($backoff333 < 5000) $backoff333 *= 2;
                            }
                            $doBackoff334 = $backoff333 <= 32 || !$doBackoff334;
                        }
                        $commit329 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            newTerms =
                              (fabric.lang.arrays.ObjectArray)
                                new fabric.lang.arrays.ObjectArray._Impl(
                                  fabric.metrics.SumMetric._Static._Proxy.
                                    $instance.
                                      $getStore()).
                                fabric$lang$arrays$ObjectArray$(
                                  fabric.metrics.SumMetric._Static._Proxy.
                                    $instance.
                                      get$$updateLabel(),
                                  fabric.metrics.SumMetric._Static._Proxy.
                                    $instance.
                                      get$$updateLabel().
                                      confPolicy(),
                                  fabric.metrics.Metric._Proxy.class,
                                  tmp.get$terms().get$length() + 1).$getProxy();
                        }
                        catch (final fabric.worker.RetryException $e330) {
                            $commit329 = false;
                            continue $label328;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e330) {
                            $commit329 = false;
                            fabric.common.TransactionID $currentTid331 =
                              $tm332.getCurrentTid();
                            if ($e330.tid.isDescendantOf($currentTid331))
                                continue $label328;
                            if ($currentTid331.parent != null) throw $e330;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e330) {
                            $commit329 = false;
                            if ($tm332.checkForStaleObjects())
                                continue $label328;
                            throw new fabric.worker.AbortException($e330);
                        }
                        finally {
                            if ($commit329) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e330) {
                                    $commit329 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e330) {
                                    $commit329 = false;
                                    fabric.common.TransactionID $currentTid331 =
                                      $tm332.getCurrentTid();
                                    if ($currentTid331 != null) {
                                        if ($e330.tid.equals($currentTid331) ||
                                              !$e330.tid.isDescendantOf(
                                                           $currentTid331)) {
                                            throw $e330;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit329) {
                                {
                                    newTerms = newTerms$var326;
                                    termIdx = termIdx$var327;
                                }
                                continue $label328;
                            }
                        }
                    }
                }
                fabric.util.Arrays._Impl.arraycopy(
                                           tmp.get$terms(), 0, newTerms, 0,
                                           tmp.get$terms().get$length());
                newTerms.set(tmp.get$terms().get$length(), other);
                fabric.util.Arrays._Impl.sort(newTerms, 0,
                                              newTerms.get$length());
            }
            fabric.metrics.DerivedMetric val = null;
            {
                fabric.metrics.DerivedMetric val$var336 = val;
                fabric.lang.arrays.ObjectArray newTerms$var337 = newTerms;
                int termIdx$var338 = termIdx;
                fabric.worker.transaction.TransactionManager $tm343 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled346 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff344 = 1;
                boolean $doBackoff345 = true;
                $label339: for (boolean $commit340 = false; !$commit340; ) {
                    if ($backoffEnabled346) {
                        if ($doBackoff345) {
                            if ($backoff344 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff344);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e341) {
                                        
                                    }
                                }
                            }
                            if ($backoff344 < 5000) $backoff344 *= 2;
                        }
                        $doBackoff345 = $backoff344 <= 32 || !$doBackoff345;
                    }
                    $commit340 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.SumMetric)
                             new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                            fabric$metrics$SumMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e341) {
                        $commit340 = false;
                        continue $label339;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e341) {
                        $commit340 = false;
                        fabric.common.TransactionID $currentTid342 =
                          $tm343.getCurrentTid();
                        if ($e341.tid.isDescendantOf($currentTid342))
                            continue $label339;
                        if ($currentTid342.parent != null) throw $e341;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e341) {
                        $commit340 = false;
                        if ($tm343.checkForStaleObjects()) continue $label339;
                        throw new fabric.worker.AbortException($e341);
                    }
                    finally {
                        if ($commit340) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e341) {
                                $commit340 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e341) {
                                $commit340 = false;
                                fabric.common.TransactionID $currentTid342 =
                                  $tm343.getCurrentTid();
                                if ($currentTid342 != null) {
                                    if ($e341.tid.equals($currentTid342) ||
                                          !$e341.tid.isDescendantOf(
                                                       $currentTid342)) {
                                        throw $e341;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit340) {
                            {
                                val = val$var336;
                                newTerms = newTerms$var337;
                                termIdx = termIdx$var338;
                            }
                            continue $label339;
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
    
    public static final byte[] $classHash = new byte[] { 119, -53, 86, 91, 64,
    42, -4, -18, -54, 84, -74, -43, -94, 9, -26, -97, 65, -101, 21, -13, -94,
    -44, -29, 76, -46, 17, -47, 98, 51, -112, 11, -28 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507234491000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
