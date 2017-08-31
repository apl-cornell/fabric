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
            fabric.lang.arrays.ObjectArray newTerms = null;
            {
                fabric.lang.arrays.ObjectArray newTerms$var275 = newTerms;
                fabric.worker.transaction.TransactionManager $tm280 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff281 = 1;
                boolean $doBackoff282 = true;
                $label276: for (boolean $commit277 = false; !$commit277; ) {
                    if ($doBackoff282) {
                        if ($backoff281 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff281);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e278) {
                                    
                                }
                            }
                        }
                        if ($backoff281 < 5000) $backoff281 *= 1;
                    }
                    $doBackoff282 = $backoff281 <= 32 || !$doBackoff282;
                    $commit277 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
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
                    catch (final fabric.worker.RetryException $e278) {
                        $commit277 = false;
                        continue $label276;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e278) {
                        $commit277 = false;
                        fabric.common.TransactionID $currentTid279 =
                          $tm280.getCurrentTid();
                        if ($e278.tid.isDescendantOf($currentTid279))
                            continue $label276;
                        if ($currentTid279.parent != null) throw $e278;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e278) {
                        $commit277 = false;
                        if ($tm280.checkForStaleObjects()) continue $label276;
                        throw new fabric.worker.AbortException($e278);
                    }
                    finally {
                        if ($commit277) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e278) {
                                $commit277 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e278) {
                                $commit277 = false;
                                fabric.common.TransactionID $currentTid279 =
                                  $tm280.getCurrentTid();
                                if ($currentTid279 != null) {
                                    if ($e278.tid.equals($currentTid279) ||
                                          !$e278.tid.isDescendantOf(
                                                       $currentTid279)) {
                                        throw $e278;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit277) {
                            { newTerms = newTerms$var275; }
                            continue $label276;
                        }
                    }
                }
            }
            fabric.util.Arrays._Impl.arraycopy(this.get$terms(), 0, newTerms, 0,
                                               this.get$terms().get$length());
            for (int i = 0; i < newTerms.get$length(); i++) {
                newTerms.set(i,
                             ((fabric.metrics.Metric)
                                newTerms.get(i)).times(scalar));
            }
            final fabric.worker.Store s = $getStore();
            fabric.metrics.DerivedMetric val = null;
            {
                fabric.metrics.DerivedMetric val$var283 = val;
                fabric.lang.arrays.ObjectArray newTerms$var284 = newTerms;
                fabric.worker.transaction.TransactionManager $tm289 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff290 = 1;
                boolean $doBackoff291 = true;
                $label285: for (boolean $commit286 = false; !$commit286; ) {
                    if ($doBackoff291) {
                        if ($backoff290 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff290);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e287) {
                                    
                                }
                            }
                        }
                        if ($backoff290 < 5000) $backoff290 *= 1;
                    }
                    $doBackoff291 = $backoff290 <= 32 || !$doBackoff291;
                    $commit286 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.SumMetric)
                             new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                            fabric$metrics$SumMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e287) {
                        $commit286 = false;
                        continue $label285;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e287) {
                        $commit286 = false;
                        fabric.common.TransactionID $currentTid288 =
                          $tm289.getCurrentTid();
                        if ($e287.tid.isDescendantOf($currentTid288))
                            continue $label285;
                        if ($currentTid288.parent != null) throw $e287;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e287) {
                        $commit286 = false;
                        if ($tm289.checkForStaleObjects()) continue $label285;
                        throw new fabric.worker.AbortException($e287);
                    }
                    finally {
                        if ($commit286) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e287) {
                                $commit286 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e287) {
                                $commit286 = false;
                                fabric.common.TransactionID $currentTid288 =
                                  $tm289.getCurrentTid();
                                if ($currentTid288 != null) {
                                    if ($e287.tid.equals($currentTid288) ||
                                          !$e287.tid.isDescendantOf(
                                                       $currentTid288)) {
                                        throw $e287;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit286) {
                            {
                                val = val$var283;
                                newTerms = newTerms$var284;
                            }
                            continue $label285;
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
                    fabric.lang.arrays.ObjectArray newTerms$var292 = newTerms;
                    int termIdx$var293 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm298 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff299 = 1;
                    boolean $doBackoff300 = true;
                    $label294: for (boolean $commit295 = false; !$commit295; ) {
                        if ($doBackoff300) {
                            if ($backoff299 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff299);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e296) {
                                        
                                    }
                                }
                            }
                            if ($backoff299 < 5000) $backoff299 *= 1;
                        }
                        $doBackoff300 = $backoff299 <= 32 || !$doBackoff300;
                        $commit295 = true;
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
                        catch (final fabric.worker.RetryException $e296) {
                            $commit295 = false;
                            continue $label294;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e296) {
                            $commit295 = false;
                            fabric.common.TransactionID $currentTid297 =
                              $tm298.getCurrentTid();
                            if ($e296.tid.isDescendantOf($currentTid297))
                                continue $label294;
                            if ($currentTid297.parent != null) throw $e296;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e296) {
                            $commit295 = false;
                            if ($tm298.checkForStaleObjects())
                                continue $label294;
                            throw new fabric.worker.AbortException($e296);
                        }
                        finally {
                            if ($commit295) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e296) {
                                    $commit295 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e296) {
                                    $commit295 = false;
                                    fabric.common.TransactionID $currentTid297 =
                                      $tm298.getCurrentTid();
                                    if ($currentTid297 != null) {
                                        if ($e296.tid.equals($currentTid297) ||
                                              !$e296.tid.isDescendantOf(
                                                           $currentTid297)) {
                                            throw $e296;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit295) {
                                {
                                    newTerms = newTerms$var292;
                                    termIdx = termIdx$var293;
                                }
                                continue $label294;
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
                    fabric.lang.arrays.ObjectArray newTerms$var301 = newTerms;
                    int termIdx$var302 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm307 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff308 = 1;
                    boolean $doBackoff309 = true;
                    $label303: for (boolean $commit304 = false; !$commit304; ) {
                        if ($doBackoff309) {
                            if ($backoff308 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff308);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e305) {
                                        
                                    }
                                }
                            }
                            if ($backoff308 < 5000) $backoff308 *= 1;
                        }
                        $doBackoff309 = $backoff308 <= 32 || !$doBackoff309;
                        $commit304 = true;
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
                        catch (final fabric.worker.RetryException $e305) {
                            $commit304 = false;
                            continue $label303;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e305) {
                            $commit304 = false;
                            fabric.common.TransactionID $currentTid306 =
                              $tm307.getCurrentTid();
                            if ($e305.tid.isDescendantOf($currentTid306))
                                continue $label303;
                            if ($currentTid306.parent != null) throw $e305;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e305) {
                            $commit304 = false;
                            if ($tm307.checkForStaleObjects())
                                continue $label303;
                            throw new fabric.worker.AbortException($e305);
                        }
                        finally {
                            if ($commit304) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e305) {
                                    $commit304 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e305) {
                                    $commit304 = false;
                                    fabric.common.TransactionID $currentTid306 =
                                      $tm307.getCurrentTid();
                                    if ($currentTid306 != null) {
                                        if ($e305.tid.equals($currentTid306) ||
                                              !$e305.tid.isDescendantOf(
                                                           $currentTid306)) {
                                            throw $e305;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit304) {
                                {
                                    newTerms = newTerms$var301;
                                    termIdx = termIdx$var302;
                                }
                                continue $label303;
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
                fabric.metrics.DerivedMetric val$var310 = val;
                fabric.lang.arrays.ObjectArray newTerms$var311 = newTerms;
                int termIdx$var312 = termIdx;
                fabric.worker.transaction.TransactionManager $tm317 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff318 = 1;
                boolean $doBackoff319 = true;
                $label313: for (boolean $commit314 = false; !$commit314; ) {
                    if ($doBackoff319) {
                        if ($backoff318 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff318);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e315) {
                                    
                                }
                            }
                        }
                        if ($backoff318 < 5000) $backoff318 *= 1;
                    }
                    $doBackoff319 = $backoff318 <= 32 || !$doBackoff319;
                    $commit314 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.SumMetric)
                             new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                            fabric$metrics$SumMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e315) {
                        $commit314 = false;
                        continue $label313;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e315) {
                        $commit314 = false;
                        fabric.common.TransactionID $currentTid316 =
                          $tm317.getCurrentTid();
                        if ($e315.tid.isDescendantOf($currentTid316))
                            continue $label313;
                        if ($currentTid316.parent != null) throw $e315;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e315) {
                        $commit314 = false;
                        if ($tm317.checkForStaleObjects()) continue $label313;
                        throw new fabric.worker.AbortException($e315);
                    }
                    finally {
                        if ($commit314) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e315) {
                                $commit314 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e315) {
                                $commit314 = false;
                                fabric.common.TransactionID $currentTid316 =
                                  $tm317.getCurrentTid();
                                if ($currentTid316 != null) {
                                    if ($e315.tid.equals($currentTid316) ||
                                          !$e315.tid.isDescendantOf(
                                                       $currentTid316)) {
                                        throw $e315;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit314) {
                            {
                                val = val$var310;
                                newTerms = newTerms$var311;
                                termIdx = termIdx$var312;
                            }
                            continue $label313;
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
    
    public static final byte[] $classHash = new byte[] { 110, -5, 65, -8, 41,
    -120, -65, 5, 112, -90, -116, 37, -57, -89, 124, 69, -122, -55, -54, 72, -3,
    -115, -36, -86, -49, -113, 116, -120, 109, 120, 17, -86 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1504028847000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
