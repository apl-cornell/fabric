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
    
    public static final byte[] $classHash = new byte[] { -102, -102, 76, -104,
    -51, 54, 104, -99, -106, 106, 100, 88, -113, 40, 79, -23, -77, 50, -77, -13,
    35, 28, 61, 105, -69, 113, 55, 105, 60, 47, 55, -112 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507317673000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
