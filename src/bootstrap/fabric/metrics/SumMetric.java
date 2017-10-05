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
                                  this.$getStore()).
                                fabric$lang$arrays$ObjectArray$(
                                  this.get$$updateLabel(),
                                  this.get$$updateLabel().confPolicy(),
                                  fabric.metrics.Metric._Proxy.class,
                                  this.get$terms().get$length()).$getProxy();
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
                                           this.get$terms(), 0, newTerms, 0,
                                           this.get$terms().get$length());
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
                                  this.$getStore()).
                                fabric$lang$arrays$ObjectArray$(
                                  this.get$$updateLabel(),
                                  this.get$$updateLabel().confPolicy(),
                                  fabric.metrics.Metric._Proxy.class,
                                  this.get$terms().get$length() + 1).$getProxy(
                                                                       );
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
                                           this.get$terms(), 0, newTerms, 0,
                                           this.get$terms().get$length());
                newTerms.set(this.get$terms().get$length(), other);
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
    
    public static final byte[] $classHash = new byte[] { 39, -73, 92, 3, -88,
    110, -81, -82, 97, -62, 55, 61, 107, 29, 5, -86, 125, -53, -104, 9, 34, 13,
    85, -23, 38, 48, -82, 65, -6, 16, 0, -40 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507151083000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
