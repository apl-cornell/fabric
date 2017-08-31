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
                fabric.metrics.DerivedMetric val$var259 = val;
                fabric.worker.transaction.TransactionManager $tm264 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff265 = 1;
                boolean $doBackoff266 = true;
                $label260: for (boolean $commit261 = false; !$commit261; ) {
                    if ($doBackoff266) {
                        if ($backoff265 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff265);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e262) {
                                    
                                }
                            }
                        }
                        if ($backoff265 < 5000) $backoff265 *= 1;
                    }
                    $doBackoff266 = $backoff265 <= 32 || !$doBackoff266;
                    $commit261 = true;
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
                    catch (final fabric.worker.RetryException $e262) {
                        $commit261 = false;
                        continue $label260;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e262) {
                        $commit261 = false;
                        fabric.common.TransactionID $currentTid263 =
                          $tm264.getCurrentTid();
                        if ($e262.tid.isDescendantOf($currentTid263))
                            continue $label260;
                        if ($currentTid263.parent != null) throw $e262;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e262) {
                        $commit261 = false;
                        if ($tm264.checkForStaleObjects()) continue $label260;
                        throw new fabric.worker.AbortException($e262);
                    }
                    finally {
                        if ($commit261) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e262) {
                                $commit261 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e262) {
                                $commit261 = false;
                                fabric.common.TransactionID $currentTid263 =
                                  $tm264.getCurrentTid();
                                if ($currentTid263 != null) {
                                    if ($e262.tid.equals($currentTid263) ||
                                          !$e262.tid.isDescendantOf(
                                                       $currentTid263)) {
                                        throw $e262;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit261) {
                            { val = val$var259; }
                            continue $label260;
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
                    fabric.metrics.DerivedMetric val$var267 = val;
                    fabric.worker.transaction.TransactionManager $tm272 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff273 = 1;
                    boolean $doBackoff274 = true;
                    $label268: for (boolean $commit269 = false; !$commit269; ) {
                        if ($doBackoff274) {
                            if ($backoff273 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff273);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e270) {
                                        
                                    }
                                }
                            }
                            if ($backoff273 < 5000) $backoff273 *= 1;
                        }
                        $doBackoff274 = $backoff273 <= 32 || !$doBackoff274;
                        $commit269 = true;
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
                        catch (final fabric.worker.RetryException $e270) {
                            $commit269 = false;
                            continue $label268;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e270) {
                            $commit269 = false;
                            fabric.common.TransactionID $currentTid271 =
                              $tm272.getCurrentTid();
                            if ($e270.tid.isDescendantOf($currentTid271))
                                continue $label268;
                            if ($currentTid271.parent != null) throw $e270;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e270) {
                            $commit269 = false;
                            if ($tm272.checkForStaleObjects())
                                continue $label268;
                            throw new fabric.worker.AbortException($e270);
                        }
                        finally {
                            if ($commit269) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e270) {
                                    $commit269 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e270) {
                                    $commit269 = false;
                                    fabric.common.TransactionID $currentTid271 =
                                      $tm272.getCurrentTid();
                                    if ($currentTid271 != null) {
                                        if ($e270.tid.equals($currentTid271) ||
                                              !$e270.tid.isDescendantOf(
                                                           $currentTid271)) {
                                            throw $e270;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit269) {
                                { val = val$var267; }
                                continue $label268;
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
    
    public static final byte[] $classHash = new byte[] { 71, 97, -33, 55, -75,
    -94, 66, 102, -126, -50, 34, -34, -68, -121, 109, 0, -6, 123, -40, -58, 36,
    -46, 50, 43, 74, 125, 126, 33, 11, 99, 47, 13 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1504028847000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
