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
                    fabric.lang.arrays.ObjectArray newTerms$var136 = newTerms;
                    int termIdx$var137 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm142 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff143 = 1;
                    boolean $doBackoff144 = true;
                    $label138: for (boolean $commit139 = false; !$commit139; ) {
                        if ($doBackoff144) {
                            if ($backoff143 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff143);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e140) {
                                        
                                    }
                                }
                            }
                            if ($backoff143 < 5000) $backoff143 *= 1;
                        }
                        $doBackoff144 = $backoff143 <= 32 || !$doBackoff144;
                        $commit139 = true;
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
                        catch (final fabric.worker.RetryException $e140) {
                            $commit139 = false;
                            continue $label138;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e140) {
                            $commit139 = false;
                            fabric.common.TransactionID $currentTid141 =
                              $tm142.getCurrentTid();
                            if ($e140.tid.isDescendantOf($currentTid141))
                                continue $label138;
                            if ($currentTid141.parent != null) throw $e140;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e140) {
                            $commit139 = false;
                            if ($tm142.checkForStaleObjects())
                                continue $label138;
                            throw new fabric.worker.AbortException($e140);
                        }
                        finally {
                            if ($commit139) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e140) {
                                    $commit139 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e140) {
                                    $commit139 = false;
                                    fabric.common.TransactionID $currentTid141 =
                                      $tm142.getCurrentTid();
                                    if ($currentTid141 != null) {
                                        if ($e140.tid.equals($currentTid141) ||
                                              !$e140.tid.isDescendantOf(
                                                           $currentTid141)) {
                                            throw $e140;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit139) {
                                {
                                    newTerms = newTerms$var136;
                                    termIdx = termIdx$var137;
                                }
                                continue $label138;
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
                    fabric.lang.arrays.ObjectArray newTerms$var145 = newTerms;
                    int termIdx$var146 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm151 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff152 = 1;
                    boolean $doBackoff153 = true;
                    $label147: for (boolean $commit148 = false; !$commit148; ) {
                        if ($doBackoff153) {
                            if ($backoff152 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff152);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e149) {
                                        
                                    }
                                }
                            }
                            if ($backoff152 < 5000) $backoff152 *= 1;
                        }
                        $doBackoff153 = $backoff152 <= 32 || !$doBackoff153;
                        $commit148 = true;
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
                        catch (final fabric.worker.RetryException $e149) {
                            $commit148 = false;
                            continue $label147;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e149) {
                            $commit148 = false;
                            fabric.common.TransactionID $currentTid150 =
                              $tm151.getCurrentTid();
                            if ($e149.tid.isDescendantOf($currentTid150))
                                continue $label147;
                            if ($currentTid150.parent != null) throw $e149;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e149) {
                            $commit148 = false;
                            if ($tm151.checkForStaleObjects())
                                continue $label147;
                            throw new fabric.worker.AbortException($e149);
                        }
                        finally {
                            if ($commit148) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e149) {
                                    $commit148 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e149) {
                                    $commit148 = false;
                                    fabric.common.TransactionID $currentTid150 =
                                      $tm151.getCurrentTid();
                                    if ($currentTid150 != null) {
                                        if ($e149.tid.equals($currentTid150) ||
                                              !$e149.tid.isDescendantOf(
                                                           $currentTid150)) {
                                            throw $e149;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit148) {
                                {
                                    newTerms = newTerms$var145;
                                    termIdx = termIdx$var146;
                                }
                                continue $label147;
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
                fabric.metrics.DerivedMetric val$var154 = val;
                fabric.lang.arrays.ObjectArray newTerms$var155 = newTerms;
                int termIdx$var156 = termIdx;
                fabric.worker.transaction.TransactionManager $tm161 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff162 = 1;
                boolean $doBackoff163 = true;
                $label157: for (boolean $commit158 = false; !$commit158; ) {
                    if ($doBackoff163) {
                        if ($backoff162 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff162);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e159) {
                                    
                                }
                            }
                        }
                        if ($backoff162 < 5000) $backoff162 *= 1;
                    }
                    $doBackoff163 = $backoff162 <= 32 || !$doBackoff163;
                    $commit158 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.SumMetric)
                             new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                            fabric$metrics$SumMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e159) {
                        $commit158 = false;
                        continue $label157;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e159) {
                        $commit158 = false;
                        fabric.common.TransactionID $currentTid160 =
                          $tm161.getCurrentTid();
                        if ($e159.tid.isDescendantOf($currentTid160))
                            continue $label157;
                        if ($currentTid160.parent != null) throw $e159;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e159) {
                        $commit158 = false;
                        if ($tm161.checkForStaleObjects()) continue $label157;
                        throw new fabric.worker.AbortException($e159);
                    }
                    finally {
                        if ($commit158) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e159) {
                                $commit158 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e159) {
                                $commit158 = false;
                                fabric.common.TransactionID $currentTid160 =
                                  $tm161.getCurrentTid();
                                if ($currentTid160 != null) {
                                    if ($e159.tid.equals($currentTid160) ||
                                          !$e159.tid.isDescendantOf(
                                                       $currentTid160)) {
                                        throw $e159;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit158) {
                            {
                                val = val$var154;
                                newTerms = newTerms$var155;
                                termIdx = termIdx$var156;
                            }
                            continue $label157;
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
    public static final long jlc$SourceLastModified$fabil = 1506965674000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
