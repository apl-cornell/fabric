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
            fabric.metrics.contracts.Contract[] witnesses =
              new fabric.metrics.contracts.Contract[this.get$terms().get$length(
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
    
    public static final byte[] $classHash = new byte[] { 48, 101, -100, -103,
    116, 121, 17, -28, 122, -50, 109, 61, -122, 32, -63, -68, -113, 112, 60, 90,
    51, 80, 49, 0, -47, -35, 59, -33, 16, -101, 78, -106 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519498015000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
