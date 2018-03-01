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
import fabric.metrics.contracts.Contract;
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
            fabric.metrics.contracts.Contract witness = null;
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
                new fabric.metrics.contracts.Contract[] { witness });
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
    
    public static final byte[] $classHash = new byte[] { -17, 102, 41, -78, 116,
    -98, -56, -35, 35, 78, -119, -90, -82, -104, -53, 118, -83, -13, 21, 46, 13,
    87, 84, 12, 122, -115, -108, 49, -56, -36, 32, 82 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519623915000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
