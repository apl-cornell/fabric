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
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      thresholdPolicy(double rate, double base, boolean useWeakCache,
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
                    fabric.metrics.DerivedMetric val$var308 = val;
                    fabric.worker.transaction.TransactionManager $tm314 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled317 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff315 = 1;
                    boolean $doBackoff316 = true;
                    boolean $retry311 = true;
                    $label309: for (boolean $commit310 = false; !$commit310; ) {
                        if ($backoffEnabled317) {
                            if ($doBackoff316) {
                                if ($backoff315 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff315);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e312) {
                                            
                                        }
                                    }
                                }
                                if ($backoff315 < 5000) $backoff315 *= 2;
                            }
                            $doBackoff316 = $backoff315 <= 32 || !$doBackoff316;
                        }
                        $commit310 = true;
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
                                    fabric.common.TransactionID $currentTid313 =
                                      $tm314.getCurrentTid();
                                    if ($currentTid313 != null) {
                                        if ($e312.tid.equals($currentTid313) ||
                                              !$e312.tid.isDescendantOf(
                                                           $currentTid313)) {
                                            throw $e312;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                        fabric.metrics.DerivedMetric val$var318 = val;
                        fabric.worker.transaction.TransactionManager $tm324 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled327 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff325 = 1;
                        boolean $doBackoff326 = true;
                        boolean $retry321 = true;
                        $label319: for (boolean $commit320 = false; !$commit320;
                                        ) {
                            if ($backoffEnabled327) {
                                if ($doBackoff326) {
                                    if ($backoff325 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff325);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e322) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff325 < 5000) $backoff325 *= 2;
                                }
                                $doBackoff326 = $backoff325 <= 32 ||
                                                  !$doBackoff326;
                            }
                            $commit320 = true;
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
                            catch (final fabric.worker.RetryException $e322) {
                                $commit320 = false;
                                continue $label319;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e322) {
                                $commit320 = false;
                                fabric.common.TransactionID $currentTid323 =
                                  $tm324.getCurrentTid();
                                if ($e322.tid.isDescendantOf($currentTid323))
                                    continue $label319;
                                if ($currentTid323.parent != null) {
                                    $retry321 = false;
                                    throw $e322;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e322) {
                                $commit320 = false;
                                if ($tm324.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid323 =
                                  $tm324.getCurrentTid();
                                if ($e322.tid.isDescendantOf($currentTid323)) {
                                    $retry321 = true;
                                }
                                else if ($currentTid323.parent != null) {
                                    $retry321 = false;
                                    throw $e322;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e322) {
                                $commit320 = false;
                                if ($tm324.checkForStaleObjects())
                                    continue $label319;
                                $retry321 = false;
                                throw new fabric.worker.AbortException($e322);
                            }
                            finally {
                                if ($commit320) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e322) {
                                        $commit320 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e322) {
                                        $commit320 = false;
                                        fabric.common.TransactionID
                                          $currentTid323 =
                                          $tm324.getCurrentTid();
                                        if ($currentTid323 != null) {
                                            if ($e322.tid.equals(
                                                            $currentTid323) ||
                                                  !$e322.tid.
                                                  isDescendantOf(
                                                    $currentTid323)) {
                                                throw $e322;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit320 && $retry321) {
                                    { val = val$var318; }
                                    continue $label319;
                                }
                            }
                        }
                    }
                }
                return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
            }
            return fabric.metrics.Metric._Impl.static_plus(tmp, other);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base, boolean useWeakCache,
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
            witness = m.getThresholdContract(rate, baseNow, currentTime);
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
    
    public static final byte[] $classHash = new byte[] { 36, -48, 75, 109, -64,
    3, 87, 27, 105, -74, 29, -57, 4, 26, -29, 6, 118, 2, 43, -125, -19, -61,
    115, 36, 61, -66, -16, 46, -105, -73, 39, 113 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520977993000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
