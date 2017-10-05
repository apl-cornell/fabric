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
            final fabric.worker.Store s = $getStore();
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
                              otherScalar * this.get$scalar(),
                              (fabric.metrics.Metric) this.get$terms().get(0));
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
            final fabric.worker.Store s = $getStore();
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
                  equals((fabric.metrics.Metric) this.get$terms().get(0))) {
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
                                                    this.get$scalar(),
                                                (fabric.metrics.Metric)
                                                  this.get$terms().get(0));
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
            return super.plus(other);
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
    
    public static final byte[] $classHash = new byte[] { 125, -103, -65, -50,
    73, -35, -98, 18, -113, -35, -32, -82, 34, -72, -84, -74, 95, -89, 123, 53,
    -25, 125, 72, -50, -69, 43, 95, -12, 27, 91, 70, -51 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507151083000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
