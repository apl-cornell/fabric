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
    
    public static final byte[] $classHash = new byte[] { 115, -83, 89, -117,
    -16, 9, 109, 98, 41, -4, 45, 45, 89, 68, 59, 65, -63, 102, 75, 37, -118,
    104, 70, -116, 13, 98, 22, -46, 44, -125, 75, 33 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520096235000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
