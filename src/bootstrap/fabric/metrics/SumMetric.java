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
            fabric.lang.arrays.ObjectArray newTerms = null;
            {
                fabric.lang.arrays.ObjectArray newTerms$var316 = newTerms;
                fabric.worker.transaction.TransactionManager $tm321 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled324 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff322 = 1;
                boolean $doBackoff323 = true;
                $label317: for (boolean $commit318 = false; !$commit318; ) {
                    if ($backoffEnabled324) {
                        if ($doBackoff323) {
                            if ($backoff322 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff322);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e319) {
                                        
                                    }
                                }
                            }
                            if ($backoff322 < 5000) $backoff322 *= 2;
                        }
                        $doBackoff323 = $backoff322 <= 32 || !$doBackoff323;
                    }
                    $commit318 = true;
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
                    catch (final fabric.worker.RetryException $e319) {
                        $commit318 = false;
                        continue $label317;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e319) {
                        $commit318 = false;
                        fabric.common.TransactionID $currentTid320 =
                          $tm321.getCurrentTid();
                        if ($e319.tid.isDescendantOf($currentTid320))
                            continue $label317;
                        if ($currentTid320.parent != null) throw $e319;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e319) {
                        $commit318 = false;
                        if ($tm321.checkForStaleObjects()) continue $label317;
                        throw new fabric.worker.AbortException($e319);
                    }
                    finally {
                        if ($commit318) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e319) {
                                $commit318 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e319) {
                                $commit318 = false;
                                fabric.common.TransactionID $currentTid320 =
                                  $tm321.getCurrentTid();
                                if ($currentTid320 != null) {
                                    if ($e319.tid.equals($currentTid320) ||
                                          !$e319.tid.isDescendantOf(
                                                       $currentTid320)) {
                                        throw $e319;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit318) {
                            { newTerms = newTerms$var316; }
                            continue $label317;
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
                fabric.metrics.DerivedMetric val$var325 = val;
                fabric.lang.arrays.ObjectArray newTerms$var326 = newTerms;
                fabric.worker.transaction.TransactionManager $tm331 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled334 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff332 = 1;
                boolean $doBackoff333 = true;
                $label327: for (boolean $commit328 = false; !$commit328; ) {
                    if ($backoffEnabled334) {
                        if ($doBackoff333) {
                            if ($backoff332 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff332);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e329) {
                                        
                                    }
                                }
                            }
                            if ($backoff332 < 5000) $backoff332 *= 2;
                        }
                        $doBackoff333 = $backoff332 <= 32 || !$doBackoff333;
                    }
                    $commit328 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.SumMetric)
                             new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                            fabric$metrics$SumMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e329) {
                        $commit328 = false;
                        continue $label327;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e329) {
                        $commit328 = false;
                        fabric.common.TransactionID $currentTid330 =
                          $tm331.getCurrentTid();
                        if ($e329.tid.isDescendantOf($currentTid330))
                            continue $label327;
                        if ($currentTid330.parent != null) throw $e329;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e329) {
                        $commit328 = false;
                        if ($tm331.checkForStaleObjects()) continue $label327;
                        throw new fabric.worker.AbortException($e329);
                    }
                    finally {
                        if ($commit328) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e329) {
                                $commit328 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e329) {
                                $commit328 = false;
                                fabric.common.TransactionID $currentTid330 =
                                  $tm331.getCurrentTid();
                                if ($currentTid330 != null) {
                                    if ($e329.tid.equals($currentTid330) ||
                                          !$e329.tid.isDescendantOf(
                                                       $currentTid330)) {
                                        throw $e329;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit328) {
                            {
                                val = val$var325;
                                newTerms = newTerms$var326;
                            }
                            continue $label327;
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
                    fabric.lang.arrays.ObjectArray newTerms$var335 = newTerms;
                    int termIdx$var336 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm341 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled344 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff342 = 1;
                    boolean $doBackoff343 = true;
                    $label337: for (boolean $commit338 = false; !$commit338; ) {
                        if ($backoffEnabled344) {
                            if ($doBackoff343) {
                                if ($backoff342 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff342);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e339) {
                                            
                                        }
                                    }
                                }
                                if ($backoff342 < 5000) $backoff342 *= 2;
                            }
                            $doBackoff343 = $backoff342 <= 32 || !$doBackoff343;
                        }
                        $commit338 = true;
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
                        catch (final fabric.worker.RetryException $e339) {
                            $commit338 = false;
                            continue $label337;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e339) {
                            $commit338 = false;
                            fabric.common.TransactionID $currentTid340 =
                              $tm341.getCurrentTid();
                            if ($e339.tid.isDescendantOf($currentTid340))
                                continue $label337;
                            if ($currentTid340.parent != null) throw $e339;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e339) {
                            $commit338 = false;
                            if ($tm341.checkForStaleObjects())
                                continue $label337;
                            throw new fabric.worker.AbortException($e339);
                        }
                        finally {
                            if ($commit338) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e339) {
                                    $commit338 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e339) {
                                    $commit338 = false;
                                    fabric.common.TransactionID $currentTid340 =
                                      $tm341.getCurrentTid();
                                    if ($currentTid340 != null) {
                                        if ($e339.tid.equals($currentTid340) ||
                                              !$e339.tid.isDescendantOf(
                                                           $currentTid340)) {
                                            throw $e339;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit338) {
                                {
                                    newTerms = newTerms$var335;
                                    termIdx = termIdx$var336;
                                }
                                continue $label337;
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
                    fabric.lang.arrays.ObjectArray newTerms$var345 = newTerms;
                    int termIdx$var346 = termIdx;
                    fabric.worker.transaction.TransactionManager $tm351 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled354 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff352 = 1;
                    boolean $doBackoff353 = true;
                    $label347: for (boolean $commit348 = false; !$commit348; ) {
                        if ($backoffEnabled354) {
                            if ($doBackoff353) {
                                if ($backoff352 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff352);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e349) {
                                            
                                        }
                                    }
                                }
                                if ($backoff352 < 5000) $backoff352 *= 2;
                            }
                            $doBackoff353 = $backoff352 <= 32 || !$doBackoff353;
                        }
                        $commit348 = true;
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
                        catch (final fabric.worker.RetryException $e349) {
                            $commit348 = false;
                            continue $label347;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e349) {
                            $commit348 = false;
                            fabric.common.TransactionID $currentTid350 =
                              $tm351.getCurrentTid();
                            if ($e349.tid.isDescendantOf($currentTid350))
                                continue $label347;
                            if ($currentTid350.parent != null) throw $e349;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e349) {
                            $commit348 = false;
                            if ($tm351.checkForStaleObjects())
                                continue $label347;
                            throw new fabric.worker.AbortException($e349);
                        }
                        finally {
                            if ($commit348) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e349) {
                                    $commit348 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e349) {
                                    $commit348 = false;
                                    fabric.common.TransactionID $currentTid350 =
                                      $tm351.getCurrentTid();
                                    if ($currentTid350 != null) {
                                        if ($e349.tid.equals($currentTid350) ||
                                              !$e349.tid.isDescendantOf(
                                                           $currentTid350)) {
                                            throw $e349;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit348) {
                                {
                                    newTerms = newTerms$var345;
                                    termIdx = termIdx$var346;
                                }
                                continue $label347;
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
                fabric.metrics.DerivedMetric val$var355 = val;
                fabric.lang.arrays.ObjectArray newTerms$var356 = newTerms;
                int termIdx$var357 = termIdx;
                fabric.worker.transaction.TransactionManager $tm362 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled365 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff363 = 1;
                boolean $doBackoff364 = true;
                $label358: for (boolean $commit359 = false; !$commit359; ) {
                    if ($backoffEnabled365) {
                        if ($doBackoff364) {
                            if ($backoff363 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff363);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e360) {
                                        
                                    }
                                }
                            }
                            if ($backoff363 < 5000) $backoff363 *= 2;
                        }
                        $doBackoff364 = $backoff363 <= 32 || !$doBackoff364;
                    }
                    $commit359 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.SumMetric)
                             new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                            fabric$metrics$SumMetric$(newTerms);
                    }
                    catch (final fabric.worker.RetryException $e360) {
                        $commit359 = false;
                        continue $label358;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e360) {
                        $commit359 = false;
                        fabric.common.TransactionID $currentTid361 =
                          $tm362.getCurrentTid();
                        if ($e360.tid.isDescendantOf($currentTid361))
                            continue $label358;
                        if ($currentTid361.parent != null) throw $e360;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e360) {
                        $commit359 = false;
                        if ($tm362.checkForStaleObjects()) continue $label358;
                        throw new fabric.worker.AbortException($e360);
                    }
                    finally {
                        if ($commit359) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e360) {
                                $commit359 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e360) {
                                $commit359 = false;
                                fabric.common.TransactionID $currentTid361 =
                                  $tm362.getCurrentTid();
                                if ($currentTid361 != null) {
                                    if ($e360.tid.equals($currentTid361) ||
                                          !$e360.tid.isDescendantOf(
                                                       $currentTid361)) {
                                        throw $e360;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit359) {
                            {
                                val = val$var355;
                                newTerms = newTerms$var356;
                                termIdx = termIdx$var357;
                            }
                            continue $label358;
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
    
    public static final byte[] $classHash = new byte[] { -42, 12, -62, -21,
    -101, 68, -20, 100, -18, -45, 94, -9, 62, 110, 81, 39, -16, 120, 15, 64,
    -11, -83, 105, 11, 79, 56, 7, -71, -70, 123, -105, -99 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507317673000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
