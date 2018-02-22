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
    
    public static final byte[] $classHash = new byte[] { 4, 59, 14, 112, 117,
    -18, 115, -38, 111, 73, -76, 123, -63, -15, 39, -35, -24, 104, -101, -108,
    -84, 69, -74, 11, 24, 3, 26, -91, 11, -110, -56, 44 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518448064000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
