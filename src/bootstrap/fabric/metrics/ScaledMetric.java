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
                    fabric.metrics.DerivedMetric val$var368 = val;
                    fabric.worker.transaction.TransactionManager $tm374 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled377 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff375 = 1;
                    boolean $doBackoff376 = true;
                    boolean $retry371 = true;
                    $label369: for (boolean $commit370 = false; !$commit370; ) {
                        if ($backoffEnabled377) {
                            if ($doBackoff376) {
                                if ($backoff375 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff375);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e372) {
                                            
                                        }
                                    }
                                }
                                if ($backoff375 < 5000) $backoff375 *= 2;
                            }
                            $doBackoff376 = $backoff375 <= 32 || !$doBackoff376;
                        }
                        $commit370 = true;
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
                        catch (final fabric.worker.RetryException $e372) {
                            $commit370 = false;
                            continue $label369;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e372) {
                            $commit370 = false;
                            fabric.common.TransactionID $currentTid373 =
                              $tm374.getCurrentTid();
                            if ($e372.tid.isDescendantOf($currentTid373))
                                continue $label369;
                            if ($currentTid373.parent != null) {
                                $retry371 = false;
                                throw $e372;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e372) {
                            $commit370 = false;
                            if ($tm374.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid373 =
                              $tm374.getCurrentTid();
                            if ($e372.tid.isDescendantOf($currentTid373)) {
                                $retry371 = true;
                            }
                            else if ($currentTid373.parent != null) {
                                $retry371 = false;
                                throw $e372;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e372) {
                            $commit370 = false;
                            if ($tm374.checkForStaleObjects())
                                continue $label369;
                            $retry371 = false;
                            throw new fabric.worker.AbortException($e372);
                        }
                        finally {
                            if ($commit370) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e372) {
                                    $commit370 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e372) {
                                    $commit370 = false;
                                    fabric.common.TransactionID $currentTid373 =
                                      $tm374.getCurrentTid();
                                    if ($currentTid373 != null) {
                                        if ($e372.tid.equals($currentTid373) ||
                                              !$e372.tid.isDescendantOf(
                                                           $currentTid373)) {
                                            throw $e372;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit370 && $retry371) {
                                { val = val$var368; }
                                continue $label369;
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
                        fabric.metrics.DerivedMetric val$var378 = val;
                        fabric.worker.transaction.TransactionManager $tm384 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled387 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff385 = 1;
                        boolean $doBackoff386 = true;
                        boolean $retry381 = true;
                        $label379: for (boolean $commit380 = false; !$commit380;
                                        ) {
                            if ($backoffEnabled387) {
                                if ($doBackoff386) {
                                    if ($backoff385 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff385);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e382) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff385 < 5000) $backoff385 *= 2;
                                }
                                $doBackoff386 = $backoff385 <= 32 ||
                                                  !$doBackoff386;
                            }
                            $commit380 = true;
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
                            catch (final fabric.worker.RetryException $e382) {
                                $commit380 = false;
                                continue $label379;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e382) {
                                $commit380 = false;
                                fabric.common.TransactionID $currentTid383 =
                                  $tm384.getCurrentTid();
                                if ($e382.tid.isDescendantOf($currentTid383))
                                    continue $label379;
                                if ($currentTid383.parent != null) {
                                    $retry381 = false;
                                    throw $e382;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e382) {
                                $commit380 = false;
                                if ($tm384.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid383 =
                                  $tm384.getCurrentTid();
                                if ($e382.tid.isDescendantOf($currentTid383)) {
                                    $retry381 = true;
                                }
                                else if ($currentTid383.parent != null) {
                                    $retry381 = false;
                                    throw $e382;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e382) {
                                $commit380 = false;
                                if ($tm384.checkForStaleObjects())
                                    continue $label379;
                                $retry381 = false;
                                throw new fabric.worker.AbortException($e382);
                            }
                            finally {
                                if ($commit380) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e382) {
                                        $commit380 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e382) {
                                        $commit380 = false;
                                        fabric.common.TransactionID
                                          $currentTid383 =
                                          $tm384.getCurrentTid();
                                        if ($currentTid383 != null) {
                                            if ($e382.tid.equals(
                                                            $currentTid383) ||
                                                  !$e382.tid.
                                                  isDescendantOf(
                                                    $currentTid383)) {
                                                throw $e382;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit380 && $retry381) {
                                    { val = val$var378; }
                                    continue $label379;
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
    
    public static final byte[] $classHash = new byte[] { -73, -92, -81, -122,
    -97, -123, -9, -125, 117, 18, 86, 0, 32, -84, 119, -82, -52, -98, 78, 121,
    30, 112, 82, -102, -117, 120, -89, 83, -75, 75, 74, -51 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1522607901000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
