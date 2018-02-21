package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
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
 * A {@link DerivedMetric} for the scaled value of a given metric.
 */
public interface ScaledMetric extends fabric.metrics.DerivedMetric {
    public double get$scalar();
    
    public double set$scalar(double val);
    
    public double postInc$scalar();
    
    public double postDec$scalar();
    
    /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param scalar
     *            The coefficient as a double
     * @param term
     *            The {@link Metric} this applies to
     */
    public fabric.metrics.ScaledMetric fabric$metrics$ScaledMetric$(
      double scalar, fabric.metrics.Metric term);
    
    public double computePresetR();
    
    public double computePresetB();
    
    public double computePresetV();
    
    public double computePresetN();
    
    public double computeValue(boolean useWeakCache);
    
    public double computeVelocity(boolean useWeakCache);
    
    public double computeNoise(boolean useWeakCache);
    
    public java.lang.String toString();
    
    public fabric.metrics.DerivedMetric times(double otherScalar);
    
    /**
     * {@inheritDoc}
     * <p>
     * {@link ScaledMetric}s try to consolidate local computations so that there
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
      implements fabric.metrics.ScaledMetric {
        public double get$scalar() {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).get$scalar();
        }
        
        public double set$scalar(double val) {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).set$scalar(
                                                                   val);
        }
        
        public double postInc$scalar() {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).postInc$scalar(
                                                                   );
        }
        
        public double postDec$scalar() {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).postDec$scalar(
                                                                   );
        }
        
        public fabric.metrics.ScaledMetric fabric$metrics$ScaledMetric$(
          double arg1, fabric.metrics.Metric arg2) {
            return ((fabric.metrics.ScaledMetric) fetch()).
              fabric$metrics$ScaledMetric$(arg1, arg2);
        }
        
        public _Proxy(ScaledMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.DerivedMetric._Impl
      implements fabric.metrics.ScaledMetric {
        public double get$scalar() { return this.scalar; }
        
        public double set$scalar(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.scalar = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$scalar() {
            double tmp = this.get$scalar();
            this.set$scalar((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$scalar() {
            double tmp = this.get$scalar();
            this.set$scalar((double) (tmp - 1));
            return tmp;
        }
        
        protected double scalar;
        
        /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param scalar
     *            The coefficient as a double
     * @param term
     *            The {@link Metric} this applies to
     */
        public fabric.metrics.ScaledMetric fabric$metrics$ScaledMetric$(
          double scalar, fabric.metrics.Metric term) {
            this.set$scalar((double) scalar);
            fabric$metrics$DerivedMetric$(new fabric.metrics.Metric[] { term });
            initialize();
            return (fabric.metrics.ScaledMetric) this.$getProxy();
        }
        
        public double computePresetR() {
            return this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).getPresetR();
        }
        
        public double computePresetB() {
            return this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).getPresetB();
        }
        
        public double computePresetV() {
            return this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).getPresetV();
        }
        
        public double computePresetN() {
            return this.get$scalar() * this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).getPresetN();
        }
        
        public double computeValue(boolean useWeakCache) {
            return this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).value(
                                                                  useWeakCache);
        }
        
        public double computeVelocity(boolean useWeakCache) {
            return this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).velocity(
                                                                  useWeakCache);
        }
        
        public double computeNoise(boolean useWeakCache) {
            return this.get$scalar() *
              this.get$scalar() *
              ((fabric.metrics.Metric)
                 this.get$terms().get(0)).noise(useWeakCache);
        }
        
        public java.lang.String toString() {
            return "(" +
            this.get$scalar() +
            "*" +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(
                                                    (fabric.metrics.Metric)
                                                      this.get$terms().get(
                                                                         0))) +
            ")@" +
            getStore();
        }
        
        public fabric.metrics.DerivedMetric times(double otherScalar) {
            return fabric.metrics.ScaledMetric._Impl.
              static_times((fabric.metrics.ScaledMetric) this.$getProxy(),
                           otherScalar);
        }
        
        private static fabric.metrics.DerivedMetric static_times(
          fabric.metrics.ScaledMetric tmp, double otherScalar) {
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.ScaledMetric)
                     new fabric.metrics.ScaledMetric._Impl(s).$getProxy()).
                    fabric$metrics$ScaledMetric$(otherScalar * tmp.get$scalar(),
                                                 (fabric.metrics.Metric)
                                                   tmp.get$terms().get(0));
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var298 = val;
                    fabric.worker.transaction.TransactionManager $tm304 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled307 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff305 = 1;
                    boolean $doBackoff306 = true;
                    boolean $retry301 = true;
                    $label299: for (boolean $commit300 = false; !$commit300; ) {
                        if ($backoffEnabled307) {
                            if ($doBackoff306) {
                                if ($backoff305 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff305);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e302) {
                                            
                                        }
                                    }
                                }
                                if ($backoff305 < 5000) $backoff305 *= 2;
                            }
                            $doBackoff306 = $backoff305 <= 32 || !$doBackoff306;
                        }
                        $commit300 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                otherScalar * tmp.get$scalar(),
                                                (fabric.metrics.Metric)
                                                  tmp.get$terms().get(0));
                        }
                        catch (final fabric.worker.RetryException $e302) {
                            $commit300 = false;
                            continue $label299;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e302) {
                            $commit300 = false;
                            fabric.common.TransactionID $currentTid303 =
                              $tm304.getCurrentTid();
                            if ($e302.tid.isDescendantOf($currentTid303))
                                continue $label299;
                            if ($currentTid303.parent != null) {
                                $retry301 = false;
                                throw $e302;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e302) {
                            $commit300 = false;
                            if ($tm304.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid303 =
                              $tm304.getCurrentTid();
                            if ($e302.tid.isDescendantOf($currentTid303)) {
                                $retry301 = true;
                            }
                            else if ($currentTid303.parent != null) {
                                $retry301 = false;
                                throw $e302;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e302) {
                            $commit300 = false;
                            if ($tm304.checkForStaleObjects())
                                continue $label299;
                            $retry301 = false;
                            throw new fabric.worker.AbortException($e302);
                        }
                        finally {
                            if ($commit300) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e302) {
                                    $commit300 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e302) {
                                    $commit300 = false;
                                    fabric.common.TransactionID $currentTid303 =
                                      $tm304.getCurrentTid();
                                    if ($currentTid303 != null) {
                                        if ($e302.tid.equals($currentTid303) ||
                                              !$e302.tid.isDescendantOf(
                                                           $currentTid303)) {
                                            throw $e302;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit300 && $retry301) {
                                { val = val$var298; }
                                continue $label299;
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
     * {@link ScaledMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            return fabric.metrics.ScaledMetric._Impl.
              static_plus((fabric.metrics.ScaledMetric) this.$getProxy(),
                          other);
        }
        
        private static fabric.metrics.DerivedMetric static_plus(
          fabric.metrics.ScaledMetric tmp, fabric.metrics.Metric other) {
            final fabric.worker.Store s = tmp.$getStore();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.ScaledMetric &&
                  other.$getStore().equals(s) &&
                  ((fabric.metrics.Metric)
                     ((fabric.metrics.ScaledMetric)
                        fabric.lang.Object._Proxy.$getProxy(other)).get$terms(
                                                                      ).get(0)).
                  equals((fabric.metrics.Metric) tmp.get$terms().get(0))) {
                fabric.metrics.ScaledMetric that =
                  (fabric.metrics.ScaledMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                fabric.metrics.DerivedMetric val = null;
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    val =
                      ((fabric.metrics.ScaledMetric)
                         new fabric.metrics.ScaledMetric._Impl(s).$getProxy()).
                        fabric$metrics$ScaledMetric$(that.get$scalar() +
                                                         tmp.get$scalar(),
                                                     (fabric.metrics.Metric)
                                                       tmp.get$terms().get(0));
                }
                else {
                    {
                        fabric.metrics.DerivedMetric val$var308 = val;
                        fabric.worker.transaction.TransactionManager $tm314 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled317 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff315 = 1;
                        boolean $doBackoff316 = true;
                        boolean $retry311 = true;
                        $label309: for (boolean $commit310 = false; !$commit310;
                                        ) {
                            if ($backoffEnabled317) {
                                if ($doBackoff316) {
                                    if ($backoff315 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff315);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e312) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff315 < 5000) $backoff315 *= 2;
                                }
                                $doBackoff316 = $backoff315 <= 32 ||
                                                  !$doBackoff316;
                            }
                            $commit310 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                val =
                                  ((fabric.metrics.ScaledMetric)
                                     new fabric.metrics.ScaledMetric._Impl(s).
                                     $getProxy()).fabric$metrics$ScaledMetric$(
                                                    that.get$scalar() +
                                                        tmp.get$scalar(),
                                                    (fabric.metrics.Metric)
                                                      tmp.get$terms().get(0));
                            }
                            catch (final fabric.worker.RetryException $e312) {
                                $commit310 = false;
                                continue $label309;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e312) {
                                $commit310 = false;
                                fabric.common.TransactionID $currentTid313 =
                                  $tm314.getCurrentTid();
                                if ($e312.tid.isDescendantOf($currentTid313))
                                    continue $label309;
                                if ($currentTid313.parent != null) {
                                    $retry311 = false;
                                    throw $e312;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e312) {
                                $commit310 = false;
                                if ($tm314.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid313 =
                                  $tm314.getCurrentTid();
                                if ($e312.tid.isDescendantOf($currentTid313)) {
                                    $retry311 = true;
                                }
                                else if ($currentTid313.parent != null) {
                                    $retry311 = false;
                                    throw $e312;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e312) {
                                $commit310 = false;
                                if ($tm314.checkForStaleObjects())
                                    continue $label309;
                                $retry311 = false;
                                throw new fabric.worker.AbortException($e312);
                            }
                            finally {
                                if ($commit310) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e312) {
                                        $commit310 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e312) {
                                        $commit310 = false;
                                        fabric.common.TransactionID
                                          $currentTid313 =
                                          $tm314.getCurrentTid();
                                        if ($currentTid313 != null) {
                                            if ($e312.tid.equals(
                                                            $currentTid313) ||
                                                  !$e312.tid.
                                                  isDescendantOf(
                                                    $currentTid313)) {
                                                throw $e312;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit310 && $retry311) {
                                    { val = val$var308; }
                                    continue $label309;
                                }
                            }
                        }
                    }
                }
                return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
            }
            return fabric.metrics.Metric._Impl.static_plus(tmp, other);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          double rate, double base, boolean useWeakCache,
          final fabric.worker.Store s) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(term(0))) instanceof fabric.metrics.SampledMetric)
                return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                          new fabric.metrics.contracts.enforcement.DirectPolicy.
                            _Impl(s).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$DirectPolicy$(
                    (fabric.metrics.ScaledMetric) this.$getProxy(), rate, base);
            fabric.metrics.contracts.MetricContract witness = null;
            long currentTime = java.lang.System.currentTimeMillis();
            double baseNow =
              fabric.metrics.contracts.Bound._Impl.value(rate, base,
                                                         currentTime);
            fabric.metrics.Metric m = term(0);
            rate = rate / this.get$scalar();
            baseNow = baseNow / this.get$scalar();
            if (this.get$scalar() < 0) {
                m = m.times(-1);
                baseNow = -baseNow;
                rate = -rate;
            }
            witness = m.getContract(rate, baseNow, currentTime);
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(s).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(
                new fabric.metrics.contracts.MetricContract[] { witness });
        }
        
        public int hashCode() {
            return fabric.util.Arrays._Impl.hashCode(this.get$terms()) * 32 +
              java.lang.Double.hashCode(this.get$scalar());
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.ScaledMetric) {
                fabric.metrics.ScaledMetric that =
                  (fabric.metrics.ScaledMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                return this.get$scalar() ==
                  that.get$scalar() &&
                  fabric.util.Arrays._Impl.deepEquals(this.get$terms(),
                                                      that.get$terms()) &&
                  this.$getStore().equals(that.$getStore());
            }
            return false;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.ScaledMetric._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeDouble(this.scalar);
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
            this.scalar = in.readDouble();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.ScaledMetric._Impl src =
              (fabric.metrics.ScaledMetric._Impl) other;
            this.scalar = src.scalar;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.ScaledMetric._Static {
            public _Proxy(fabric.metrics.ScaledMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.ScaledMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  ScaledMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.ScaledMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.ScaledMetric._Static._Impl.class);
                $instance = (fabric.metrics.ScaledMetric._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.ScaledMetric._Static {
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
                return new fabric.metrics.ScaledMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 81, -112, 98, 122, -23,
    70, 76, -64, 65, -72, 40, -53, 88, -5, -18, -21, 36, 122, 50, 96, -4, 66,
    63, 35, -86, -50, 56, -100, -24, 123, -95, 74 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518448064000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
