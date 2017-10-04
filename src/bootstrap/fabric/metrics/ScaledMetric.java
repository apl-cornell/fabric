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
                fabric.metrics.DerivedMetric val$var243 = val;
                fabric.worker.transaction.TransactionManager $tm248 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled251 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff249 = 1;
                boolean $doBackoff250 = true;
                $label244: for (boolean $commit245 = false; !$commit245; ) {
                    if ($backoffEnabled251) {
                        if ($doBackoff250) {
                            if ($backoff249 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff249);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e246) {
                                        
                                    }
                                }
                            }
                            if ($backoff249 < 5000) $backoff249 *= 2;
                        }
                        $doBackoff250 = $backoff249 <= 32 || !$doBackoff250;
                    }
                    $commit245 = true;
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
                    catch (final fabric.worker.RetryException $e246) {
                        $commit245 = false;
                        continue $label244;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e246) {
                        $commit245 = false;
                        fabric.common.TransactionID $currentTid247 =
                          $tm248.getCurrentTid();
                        if ($e246.tid.isDescendantOf($currentTid247))
                            continue $label244;
                        if ($currentTid247.parent != null) throw $e246;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e246) {
                        $commit245 = false;
                        if ($tm248.checkForStaleObjects()) continue $label244;
                        throw new fabric.worker.AbortException($e246);
                    }
                    finally {
                        if ($commit245) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e246) {
                                $commit245 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e246) {
                                $commit245 = false;
                                fabric.common.TransactionID $currentTid247 =
                                  $tm248.getCurrentTid();
                                if ($currentTid247 != null) {
                                    if ($e246.tid.equals($currentTid247) ||
                                          !$e246.tid.isDescendantOf(
                                                       $currentTid247)) {
                                        throw $e246;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit245) {
                            { val = val$var243; }
                            continue $label244;
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
                    fabric.metrics.DerivedMetric val$var252 = val;
                    fabric.worker.transaction.TransactionManager $tm257 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled260 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff258 = 1;
                    boolean $doBackoff259 = true;
                    $label253: for (boolean $commit254 = false; !$commit254; ) {
                        if ($backoffEnabled260) {
                            if ($doBackoff259) {
                                if ($backoff258 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff258);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e255) {
                                            
                                        }
                                    }
                                }
                                if ($backoff258 < 5000) $backoff258 *= 2;
                            }
                            $doBackoff259 = $backoff258 <= 32 || !$doBackoff259;
                        }
                        $commit254 = true;
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
                        catch (final fabric.worker.RetryException $e255) {
                            $commit254 = false;
                            continue $label253;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e255) {
                            $commit254 = false;
                            fabric.common.TransactionID $currentTid256 =
                              $tm257.getCurrentTid();
                            if ($e255.tid.isDescendantOf($currentTid256))
                                continue $label253;
                            if ($currentTid256.parent != null) throw $e255;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e255) {
                            $commit254 = false;
                            if ($tm257.checkForStaleObjects())
                                continue $label253;
                            throw new fabric.worker.AbortException($e255);
                        }
                        finally {
                            if ($commit254) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e255) {
                                    $commit254 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e255) {
                                    $commit254 = false;
                                    fabric.common.TransactionID $currentTid256 =
                                      $tm257.getCurrentTid();
                                    if ($currentTid256 != null) {
                                        if ($e255.tid.equals($currentTid256) ||
                                              !$e255.tid.isDescendantOf(
                                                           $currentTid256)) {
                                            throw $e255;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit254) {
                                { val = val$var252; }
                                continue $label253;
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
    
    public static final byte[] $classHash = new byte[] { -99, 81, -49, 65, 98,
    -26, 35, -35, -112, 88, -69, 53, -124, 119, -66, -6, -38, 119, 95, 70, 99,
    66, -40, 26, -62, -77, 102, 15, 110, -34, 109, -88 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506966071000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
