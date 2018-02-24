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
    
    public static final byte[] $classHash = new byte[] { 42, -69, -72, 43, 13,
    25, 80, -96, -73, -11, -53, -38, 26, -82, 104, -68, 127, -70, 29, -79, -47,
    28, 124, -63, -23, 42, 47, -15, -60, 124, 113, 91 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519498028000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
