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
      fabric.metrics.contracts.Bound bound, boolean useWeakCache);
    
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
        
        private static fabric.lang.arrays.ObjectArray singleton(
          fabric.metrics.Metric term) {
            return fabric.lang.arrays.internal.Compat.
              convert(
                fabric.metrics.ScaledMetric._Static._Proxy.$instance.$getStore(
                                                                       ),
                fabric.metrics.ScaledMetric._Static._Proxy.$instance.
                    get$$updateLabel(),
                fabric.metrics.ScaledMetric._Static._Proxy.$instance.
                    get$$updateLabel().confPolicy(),
                new fabric.lang.Object[] { term });
        }
        
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
            fabric$metrics$DerivedMetric$(
              fabric.metrics.ScaledMetric._Impl.singleton(term));
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
            {
                fabric.metrics.DerivedMetric val$var298 = val;
                fabric.worker.transaction.TransactionManager $tm303 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled306 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff304 = 1;
                boolean $doBackoff305 = true;
                $label299: for (boolean $commit300 = false; !$commit300; ) {
                    if ($backoffEnabled306) {
                        if ($doBackoff305) {
                            if ($backoff304 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff304);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e301) {
                                        
                                    }
                                }
                            }
                            if ($backoff304 < 5000) $backoff304 *= 2;
                        }
                        $doBackoff305 = $backoff304 <= 32 || !$doBackoff305;
                    }
                    $commit300 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.ScaledMetric)
                             new fabric.metrics.ScaledMetric._Impl(s).$getProxy(
                                                                        )).
                            fabric$metrics$ScaledMetric$(
                              otherScalar * tmp.get$scalar(),
                              (fabric.metrics.Metric) tmp.get$terms().get(0));
                    }
                    catch (final fabric.worker.RetryException $e301) {
                        $commit300 = false;
                        continue $label299;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e301) {
                        $commit300 = false;
                        fabric.common.TransactionID $currentTid302 =
                          $tm303.getCurrentTid();
                        if ($e301.tid.isDescendantOf($currentTid302))
                            continue $label299;
                        if ($currentTid302.parent != null) throw $e301;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e301) {
                        $commit300 = false;
                        if ($tm303.checkForStaleObjects()) continue $label299;
                        throw new fabric.worker.AbortException($e301);
                    }
                    finally {
                        if ($commit300) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e301) {
                                $commit300 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e301) {
                                $commit300 = false;
                                fabric.common.TransactionID $currentTid302 =
                                  $tm303.getCurrentTid();
                                if ($currentTid302 != null) {
                                    if ($e301.tid.equals($currentTid302) ||
                                          !$e301.tid.isDescendantOf(
                                                       $currentTid302)) {
                                        throw $e301;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit300) {
                            { val = val$var298; }
                            continue $label299;
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
                {
                    fabric.metrics.DerivedMetric val$var307 = val;
                    fabric.worker.transaction.TransactionManager $tm312 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled315 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff313 = 1;
                    boolean $doBackoff314 = true;
                    $label308: for (boolean $commit309 = false; !$commit309; ) {
                        if ($backoffEnabled315) {
                            if ($doBackoff314) {
                                if ($backoff313 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff313);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e310) {
                                            
                                        }
                                    }
                                }
                                if ($backoff313 < 5000) $backoff313 *= 2;
                            }
                            $doBackoff314 = $backoff313 <= 32 || !$doBackoff314;
                        }
                        $commit309 = true;
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
                        catch (final fabric.worker.RetryException $e310) {
                            $commit309 = false;
                            continue $label308;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e310) {
                            $commit309 = false;
                            fabric.common.TransactionID $currentTid311 =
                              $tm312.getCurrentTid();
                            if ($e310.tid.isDescendantOf($currentTid311))
                                continue $label308;
                            if ($currentTid311.parent != null) throw $e310;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e310) {
                            $commit309 = false;
                            if ($tm312.checkForStaleObjects())
                                continue $label308;
                            throw new fabric.worker.AbortException($e310);
                        }
                        finally {
                            if ($commit309) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e310) {
                                    $commit309 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e310) {
                                    $commit309 = false;
                                    fabric.common.TransactionID $currentTid311 =
                                      $tm312.getCurrentTid();
                                    if ($currentTid311 != null) {
                                        if ($e310.tid.equals($currentTid311) ||
                                              !$e310.tid.isDescendantOf(
                                                           $currentTid311)) {
                                            throw $e310;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit309) {
                                { val = val$var307; }
                                continue $label308;
                            }
                        }
                    }
                }
                return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
            }
            return fabric.metrics.Metric._Impl.static_plus(tmp, other);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          fabric.metrics.contracts.Bound bound, boolean useWeakCache) {
            if (isSingleStore())
                return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                          new fabric.metrics.contracts.enforcement.DirectPolicy.
                            _Impl(this.$getStore()).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$DirectPolicy$(
                    (fabric.metrics.ScaledMetric) this.$getProxy(), bound);
            fabric.metrics.contracts.MetricContract witness = null;
            long currentTime = java.lang.System.currentTimeMillis();
            double base = bound.value(currentTime);
            double rate = bound.get$rate();
            fabric.metrics.Metric m = term(0);
            rate = rate / this.get$scalar();
            base = base / this.get$scalar();
            if (this.get$scalar() < 0) {
                m = m.times(-1);
                base = -base;
                rate = -rate;
            }
            fabric.metrics.contracts.Bound witnessBound =
              ((fabric.metrics.contracts.Bound)
                 new fabric.metrics.contracts.Bound._Impl(
                   this.$getStore()).$getProxy()).
              fabric$metrics$contracts$Bound$(rate, base, currentTime);
            witness = m.getContract(witnessBound);
            final fabric.worker.Store bndStore = bound.getStore();
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(bndStore).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(
                fabric.lang.arrays.internal.Compat.
                    convert(this.$getStore(), this.get$$updateLabel(),
                            this.get$$updateLabel().confPolicy(),
                            new fabric.lang.Object[] { witness }));
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
    
    public static final byte[] $classHash = new byte[] { 103, 84, 69, -29, -100,
    -113, -60, -55, 1, -47, 77, 119, -46, -26, 123, -75, 56, 107, 20, -97, 95,
    -40, 110, -67, 57, -29, -63, 116, -15, -79, -40, 28 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507234287000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
