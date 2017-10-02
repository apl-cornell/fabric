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
                fabric.metrics.DerivedMetric val$var238 = val;
                fabric.worker.transaction.TransactionManager $tm243 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff244 = 1;
                boolean $doBackoff245 = true;
                $label239: for (boolean $commit240 = false; !$commit240; ) {
                    if ($doBackoff245) {
                        if ($backoff244 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff244);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e241) {
                                    
                                }
                            }
                        }
                        if ($backoff244 < 5000) $backoff244 *= 1;
                    }
                    $doBackoff245 = $backoff244 <= 32 || !$doBackoff245;
                    $commit240 = true;
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
                    catch (final fabric.worker.RetryException $e241) {
                        $commit240 = false;
                        continue $label239;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e241) {
                        $commit240 = false;
                        fabric.common.TransactionID $currentTid242 =
                          $tm243.getCurrentTid();
                        if ($e241.tid.isDescendantOf($currentTid242))
                            continue $label239;
                        if ($currentTid242.parent != null) throw $e241;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e241) {
                        $commit240 = false;
                        if ($tm243.checkForStaleObjects()) continue $label239;
                        throw new fabric.worker.AbortException($e241);
                    }
                    finally {
                        if ($commit240) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e241) {
                                $commit240 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e241) {
                                $commit240 = false;
                                fabric.common.TransactionID $currentTid242 =
                                  $tm243.getCurrentTid();
                                if ($currentTid242 != null) {
                                    if ($e241.tid.equals($currentTid242) ||
                                          !$e241.tid.isDescendantOf(
                                                       $currentTid242)) {
                                        throw $e241;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit240) {
                            { val = val$var238; }
                            continue $label239;
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
                    fabric.metrics.DerivedMetric val$var246 = val;
                    fabric.worker.transaction.TransactionManager $tm251 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff252 = 1;
                    boolean $doBackoff253 = true;
                    $label247: for (boolean $commit248 = false; !$commit248; ) {
                        if ($doBackoff253) {
                            if ($backoff252 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff252);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e249) {
                                        
                                    }
                                }
                            }
                            if ($backoff252 < 5000) $backoff252 *= 1;
                        }
                        $doBackoff253 = $backoff252 <= 32 || !$doBackoff253;
                        $commit248 = true;
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
                        catch (final fabric.worker.RetryException $e249) {
                            $commit248 = false;
                            continue $label247;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e249) {
                            $commit248 = false;
                            fabric.common.TransactionID $currentTid250 =
                              $tm251.getCurrentTid();
                            if ($e249.tid.isDescendantOf($currentTid250))
                                continue $label247;
                            if ($currentTid250.parent != null) throw $e249;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e249) {
                            $commit248 = false;
                            if ($tm251.checkForStaleObjects())
                                continue $label247;
                            throw new fabric.worker.AbortException($e249);
                        }
                        finally {
                            if ($commit248) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e249) {
                                    $commit248 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e249) {
                                    $commit248 = false;
                                    fabric.common.TransactionID $currentTid250 =
                                      $tm251.getCurrentTid();
                                    if ($currentTid250 != null) {
                                        if ($e249.tid.equals($currentTid250) ||
                                              !$e249.tid.isDescendantOf(
                                                           $currentTid250)) {
                                            throw $e249;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit248) {
                                { val = val$var246; }
                                continue $label247;
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
    
    public static final byte[] $classHash = new byte[] { -89, -117, 79, -66, 34,
    -118, -110, -59, 35, 81, -91, 108, 114, -109, -57, 11, 41, 95, -16, 86, -30,
    59, -84, 53, -114, 21, -104, -24, 39, -106, -106, -117 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506451157000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
