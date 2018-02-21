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
import fabric.metrics.contracts.MetricContract;
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
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
      double rate, double base, boolean useWeakCache,
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
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          double rate, double base, boolean useWeakCache,
          final fabric.worker.Store s) {
            fabric.metrics.contracts.MetricContract[] witnesses =
              new fabric.metrics.contracts.MetricContract[this.get$terms(
                                                                 ).get$length(
                                                                     )];
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                witnesses[i] = term(i).getContract(rate, base);
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
    
    public static final byte[] $classHash = new byte[] { 73, 20, -99, -30, 3,
    72, 108, -76, -110, 58, -17, -27, -5, 14, -69, -127, 26, 27, -99, -107,
    -119, -116, -38, -69, -22, -108, 76, -75, -55, -87, -15, -83 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518448064000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
