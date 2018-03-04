package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.util.Set;
import fabric.util.TreeSet;
import fabric.util.Iterator;
import fabric.util.LinkedHashMap;
import fabric.util.Map;
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
 * A {@link DerivedMetric} for the minimum of a group of other {@link Metric}s.
 */
public interface MinMetric extends fabric.metrics.DerivedMetric {
    /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param terms
     *            The {@link Metric}s this applies to
     */
    public fabric.metrics.MinMetric fabric$metrics$MinMetric$(
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
     * {@link MinMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
    /**
     * {@inheritDoc}
     * <p>
     * {@link MinMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
    public fabric.metrics.Metric min(fabric.metrics.Metric other);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      thresholdPolicy(double rate, double base, boolean useWeakCache,
                      final fabric.worker.Store s);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy extends fabric.metrics.DerivedMetric._Proxy
      implements fabric.metrics.MinMetric {
        public fabric.metrics.MinMetric fabric$metrics$MinMetric$(
          fabric.metrics.Metric[] arg1) {
            return ((fabric.metrics.MinMetric) fetch()).
              fabric$metrics$MinMetric$(arg1);
        }
        
        public _Proxy(MinMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.DerivedMetric._Impl
      implements fabric.metrics.MinMetric {
        /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param terms
     *            The {@link Metric}s this applies to
     */
        public fabric.metrics.MinMetric fabric$metrics$MinMetric$(
          fabric.metrics.Metric[] terms) {
            fabric$metrics$DerivedMetric$(terms);
            java.util.Set termsBag =
              new java.util.TreeSet(java.util.Arrays.asList(terms));
            this.set$terms(
                   (fabric.lang.arrays.ObjectArray)
                     new fabric.lang.arrays.ObjectArray._Impl(
                       this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                           this.get$$updateLabel(),
                                           this.get$$updateLabel().confPolicy(),
                                           fabric.metrics.Metric._Proxy.class,
                                           termsBag.size()).$getProxy());
            int tIdx = 0;
            for (java.util.Iterator it = termsBag.iterator(); it.hasNext(); ) {
                this.
                  get$terms().
                  set(
                    tIdx++,
                    (fabric.metrics.Metric)
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        fabric.lang.WrappedJavaInlineable.$wrap(it.next())));
            }
            initialize();
            return (fabric.metrics.MinMetric) this.$getProxy();
        }
        
        public double computePresetR() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.max(result,
                                     ((fabric.metrics.Metric)
                                        this.get$terms().get(i)).getPresetR());
            }
            return result;
        }
        
        public double computePresetB() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.max(result,
                                     ((fabric.metrics.Metric)
                                        this.get$terms().get(i)).getPresetB());
            }
            return result;
        }
        
        public double computePresetV() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.min(result,
                                     ((fabric.metrics.Metric)
                                        this.get$terms().get(i)).getPresetV());
            }
            return result;
        }
        
        public double computePresetN() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.max(result,
                                     ((fabric.metrics.Metric)
                                        this.get$terms().get(i)).getPresetN());
            }
            return result;
        }
        
        public double computeValue(boolean useWeakCache) {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.min(
                                   result,
                                   ((fabric.metrics.Metric)
                                      this.get$terms().get(i)).value(
                                                                 useWeakCache));
            }
            return result;
        }
        
        public double computeVelocity(boolean useWeakCache) {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.min(
                                   result,
                                   ((fabric.metrics.Metric)
                                      this.get$terms().get(i)).velocity(
                                                                 useWeakCache));
            }
            return result;
        }
        
        public double computeNoise(boolean useWeakCache) {
            double noise = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                noise =
                  java.lang.Math.max(
                                   noise,
                                   ((fabric.metrics.Metric)
                                      this.get$terms().get(i)).noise(
                                                                 useWeakCache));
            }
            return noise;
        }
        
        public java.lang.String toString() {
            java.lang.String str = "min(";
            boolean nonEmpty = false;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                if (nonEmpty) { str += ", "; }
                nonEmpty = true;
                str += (fabric.metrics.Metric) this.get$terms().get(i);
            }
            return str + ")@" + $getStore();
        }
        
        public fabric.metrics.DerivedMetric times(double scalar) {
            return fabric.metrics.MinMetric._Impl.static_times(
                                                    (fabric.metrics.MinMetric)
                                                      this.$getProxy(), scalar);
        }
        
        private static fabric.metrics.DerivedMetric static_times(
          fabric.metrics.MinMetric tmp, double scalar) {
            fabric.metrics.Metric[] newTerms =
              new fabric.metrics.Metric[tmp.get$terms().get$length()];
            for (int i = 0; i < tmp.get$terms().get$length(); i++) {
                newTerms[i] = ((fabric.metrics.Metric)
                                 tmp.get$terms().get(i)).times(scalar);
            }
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.MinMetric)
                     new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                    fabric$metrics$MinMetric$(newTerms);
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var177 = val;
                    fabric.worker.transaction.TransactionManager $tm183 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled186 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff184 = 1;
                    boolean $doBackoff185 = true;
                    boolean $retry180 = true;
                    $label178: for (boolean $commit179 = false; !$commit179; ) {
                        if ($backoffEnabled186) {
                            if ($doBackoff185) {
                                if ($backoff184 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff184);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e181) {
                                            
                                        }
                                    }
                                }
                                if ($backoff184 < 5000) $backoff184 *= 2;
                            }
                            $doBackoff185 = $backoff184 <= 32 || !$doBackoff185;
                        }
                        $commit179 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e181) {
                            $commit179 = false;
                            continue $label178;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e181) {
                            $commit179 = false;
                            fabric.common.TransactionID $currentTid182 =
                              $tm183.getCurrentTid();
                            if ($e181.tid.isDescendantOf($currentTid182))
                                continue $label178;
                            if ($currentTid182.parent != null) {
                                $retry180 = false;
                                throw $e181;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e181) {
                            $commit179 = false;
                            if ($tm183.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid182 =
                              $tm183.getCurrentTid();
                            if ($e181.tid.isDescendantOf($currentTid182)) {
                                $retry180 = true;
                            }
                            else if ($currentTid182.parent != null) {
                                $retry180 = false;
                                throw $e181;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e181) {
                            $commit179 = false;
                            if ($tm183.checkForStaleObjects())
                                continue $label178;
                            $retry180 = false;
                            throw new fabric.worker.AbortException($e181);
                        }
                        finally {
                            if ($commit179) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e181) {
                                    $commit179 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e181) {
                                    $commit179 = false;
                                    fabric.common.TransactionID $currentTid182 =
                                      $tm183.getCurrentTid();
                                    if ($currentTid182 != null) {
                                        if ($e181.tid.equals($currentTid182) ||
                                              !$e181.tid.isDescendantOf(
                                                           $currentTid182)) {
                                            throw $e181;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit179 && $retry180) {
                                { val = val$var177; }
                                continue $label178;
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
     * {@link MinMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            return fabric.metrics.MinMetric._Impl.static_plus(
                                                    (fabric.metrics.MinMetric)
                                                      this.$getProxy(), other);
        }
        
        private static fabric.metrics.DerivedMetric static_plus(
          fabric.metrics.MinMetric tmp, fabric.metrics.Metric other) {
            fabric.metrics.Metric[] newTerms =
              new fabric.metrics.Metric[tmp.get$terms().get$length()];
            for (int i = 0; i < newTerms.length; i++) {
                newTerms[i] = other.plus((fabric.metrics.Metric)
                                           tmp.get$terms().get(i));
            }
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.MinMetric)
                     new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                    fabric$metrics$MinMetric$(newTerms);
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var187 = val;
                    fabric.worker.transaction.TransactionManager $tm193 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled196 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff194 = 1;
                    boolean $doBackoff195 = true;
                    boolean $retry190 = true;
                    $label188: for (boolean $commit189 = false; !$commit189; ) {
                        if ($backoffEnabled196) {
                            if ($doBackoff195) {
                                if ($backoff194 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff194);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e191) {
                                            
                                        }
                                    }
                                }
                                if ($backoff194 < 5000) $backoff194 *= 2;
                            }
                            $doBackoff195 = $backoff194 <= 32 || !$doBackoff195;
                        }
                        $commit189 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e191) {
                            $commit189 = false;
                            continue $label188;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e191) {
                            $commit189 = false;
                            fabric.common.TransactionID $currentTid192 =
                              $tm193.getCurrentTid();
                            if ($e191.tid.isDescendantOf($currentTid192))
                                continue $label188;
                            if ($currentTid192.parent != null) {
                                $retry190 = false;
                                throw $e191;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e191) {
                            $commit189 = false;
                            if ($tm193.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid192 =
                              $tm193.getCurrentTid();
                            if ($e191.tid.isDescendantOf($currentTid192)) {
                                $retry190 = true;
                            }
                            else if ($currentTid192.parent != null) {
                                $retry190 = false;
                                throw $e191;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e191) {
                            $commit189 = false;
                            if ($tm193.checkForStaleObjects())
                                continue $label188;
                            $retry190 = false;
                            throw new fabric.worker.AbortException($e191);
                        }
                        finally {
                            if ($commit189) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e191) {
                                    $commit189 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e191) {
                                    $commit189 = false;
                                    fabric.common.TransactionID $currentTid192 =
                                      $tm193.getCurrentTid();
                                    if ($currentTid192 != null) {
                                        if ($e191.tid.equals($currentTid192) ||
                                              !$e191.tid.isDescendantOf(
                                                           $currentTid192)) {
                                            throw $e191;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit189 && $retry190) {
                                { val = val$var187; }
                                continue $label188;
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
     * {@link MinMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
        public fabric.metrics.Metric min(fabric.metrics.Metric other) {
            return fabric.metrics.MinMetric._Impl.static_min(
                                                    (fabric.metrics.MinMetric)
                                                      this.$getProxy(), other);
        }
        
        private static fabric.metrics.Metric static_min(
          fabric.metrics.MinMetric tmp, fabric.metrics.Metric other) {
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.MinMetric &&
                  other.$getStore().equals(s)) {
                fabric.metrics.MinMetric that =
                  (fabric.metrics.MinMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                java.util.Set termsBag = new java.util.TreeSet();
                for (int i = 0; i < tmp.get$terms().get$length(); i++) {
                    termsBag.
                      add(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.Metric)
                                    tmp.get$terms().get(i)));
                }
                for (int i = 0; i < that.get$terms().get$length(); i++) {
                    termsBag.
                      add(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.Metric)
                                    that.get$terms().get(i)));
                }
                fabric.metrics.Metric[] newTerms =
                  new fabric.metrics.Metric[termsBag.size()];
                int aggIdx = 0;
                for (java.util.Iterator iter = termsBag.iterator();
                     iter.hasNext(); ) {
                    fabric.metrics.Metric
                      m =
                      (fabric.metrics.Metric)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          fabric.lang.WrappedJavaInlineable.$wrap(iter.next()));
                    newTerms[aggIdx++] = m;
                }
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    val =
                      ((fabric.metrics.MinMetric)
                         new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                        fabric$metrics$MinMetric$(newTerms);
                }
                else {
                    {
                        fabric.metrics.DerivedMetric val$var197 = val;
                        int aggIdx$var198 = aggIdx;
                        fabric.worker.transaction.TransactionManager $tm204 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled207 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff205 = 1;
                        boolean $doBackoff206 = true;
                        boolean $retry201 = true;
                        $label199: for (boolean $commit200 = false; !$commit200;
                                        ) {
                            if ($backoffEnabled207) {
                                if ($doBackoff206) {
                                    if ($backoff205 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff205);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e202) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff205 < 5000) $backoff205 *= 2;
                                }
                                $doBackoff206 = $backoff205 <= 32 ||
                                                  !$doBackoff206;
                            }
                            $commit200 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    newTerms);
                            }
                            catch (final fabric.worker.RetryException $e202) {
                                $commit200 = false;
                                continue $label199;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e202) {
                                $commit200 = false;
                                fabric.common.TransactionID $currentTid203 =
                                  $tm204.getCurrentTid();
                                if ($e202.tid.isDescendantOf($currentTid203))
                                    continue $label199;
                                if ($currentTid203.parent != null) {
                                    $retry201 = false;
                                    throw $e202;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e202) {
                                $commit200 = false;
                                if ($tm204.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid203 =
                                  $tm204.getCurrentTid();
                                if ($e202.tid.isDescendantOf($currentTid203)) {
                                    $retry201 = true;
                                }
                                else if ($currentTid203.parent != null) {
                                    $retry201 = false;
                                    throw $e202;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e202) {
                                $commit200 = false;
                                if ($tm204.checkForStaleObjects())
                                    continue $label199;
                                $retry201 = false;
                                throw new fabric.worker.AbortException($e202);
                            }
                            finally {
                                if ($commit200) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e202) {
                                        $commit200 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e202) {
                                        $commit200 = false;
                                        fabric.common.TransactionID
                                          $currentTid203 =
                                          $tm204.getCurrentTid();
                                        if ($currentTid203 != null) {
                                            if ($e202.tid.equals(
                                                            $currentTid203) ||
                                                  !$e202.tid.
                                                  isDescendantOf(
                                                    $currentTid203)) {
                                                throw $e202;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit200 && $retry201) {
                                    {
                                        val = val$var197;
                                        aggIdx = aggIdx$var198;
                                    }
                                    continue $label199;
                                }
                            }
                        }
                    }
                }
                return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
            }
            else if (fabric.util.Arrays._Impl.asList(tmp.get$terms()).
                       indexOf(other) >= 0) {
                return tmp;
            }
            fabric.metrics.Metric[] newTerms =
              new fabric.metrics.Metric[tmp.get$terms().get$length() + 1];
            for (int i = 0; i < tmp.get$terms().get$length(); i++) {
                newTerms[i] = (fabric.metrics.Metric) tmp.get$terms().get(i);
            }
            newTerms[tmp.get$terms().get$length()] = other;
            java.util.Arrays.sort(newTerms, 0, newTerms.length);
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.MinMetric)
                     new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                    fabric$metrics$MinMetric$(newTerms);
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var208 = val;
                    fabric.worker.transaction.TransactionManager $tm214 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled217 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff215 = 1;
                    boolean $doBackoff216 = true;
                    boolean $retry211 = true;
                    $label209: for (boolean $commit210 = false; !$commit210; ) {
                        if ($backoffEnabled217) {
                            if ($doBackoff216) {
                                if ($backoff215 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff215);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e212) {
                                            
                                        }
                                    }
                                }
                                if ($backoff215 < 5000) $backoff215 *= 2;
                            }
                            $doBackoff216 = $backoff215 <= 32 || !$doBackoff216;
                        }
                        $commit210 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(
                                                newTerms);
                        }
                        catch (final fabric.worker.RetryException $e212) {
                            $commit210 = false;
                            continue $label209;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e212) {
                            $commit210 = false;
                            fabric.common.TransactionID $currentTid213 =
                              $tm214.getCurrentTid();
                            if ($e212.tid.isDescendantOf($currentTid213))
                                continue $label209;
                            if ($currentTid213.parent != null) {
                                $retry211 = false;
                                throw $e212;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e212) {
                            $commit210 = false;
                            if ($tm214.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid213 =
                              $tm214.getCurrentTid();
                            if ($e212.tid.isDescendantOf($currentTid213)) {
                                $retry211 = true;
                            }
                            else if ($currentTid213.parent != null) {
                                $retry211 = false;
                                throw $e212;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e212) {
                            $commit210 = false;
                            if ($tm214.checkForStaleObjects())
                                continue $label209;
                            $retry211 = false;
                            throw new fabric.worker.AbortException($e212);
                        }
                        finally {
                            if ($commit210) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e212) {
                                    $commit210 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e212) {
                                    $commit210 = false;
                                    fabric.common.TransactionID $currentTid213 =
                                      $tm214.getCurrentTid();
                                    if ($currentTid213 != null) {
                                        if ($e212.tid.equals($currentTid213) ||
                                              !$e212.tid.isDescendantOf(
                                                           $currentTid213)) {
                                            throw $e212;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit210 && $retry211) {
                                { val = val$var208; }
                                continue $label209;
                            }
                        }
                    }
                }
            }
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base, boolean useWeakCache,
                          final fabric.worker.Store s) {
            fabric.metrics.contracts.Contract[] witnesses =
              new fabric.metrics.contracts.Contract[this.get$terms().get$length(
                                                                       )];
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                witnesses[i] = term(i).getThresholdContract(rate, base);
            }
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(s).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(witnesses);
        }
        
        public int hashCode() {
            return fabric.util.Arrays._Impl.hashCode(this.get$terms()) * 32 +
              $getStore().hashCode();
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.MinMetric) {
                fabric.metrics.MinMetric that =
                  (fabric.metrics.MinMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                return fabric.util.Arrays._Impl.deepEquals(this.get$terms(),
                                                           that.get$terms()) &&
                  this.$getStore().equals(that.$getStore());
            }
            return false;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.MinMetric._Proxy(this);
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
          implements fabric.metrics.MinMetric._Static {
            public _Proxy(fabric.metrics.MinMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.MinMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  MinMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.MinMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.MinMetric._Static._Impl.class);
                $instance = (fabric.metrics.MinMetric._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.MinMetric._Static {
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
                return new fabric.metrics.MinMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -104, 1, -116, -19,
    -73, 80, -31, 44, -69, -111, 11, 14, -114, 86, 90, -104, -124, -28, -42, 17,
    -39, 96, -46, -56, 124, -66, -63, 74, -121, -63, -6, 111 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520199201000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
