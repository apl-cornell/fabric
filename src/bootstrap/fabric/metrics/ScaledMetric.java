package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Arrays;
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
            return this.get$scalar() * term(0).getPresetR();
        }
        
        public double computePresetB() {
            return this.get$scalar() * term(0).getPresetB();
        }
        
        public double computePresetV() {
            return this.get$scalar() * term(0).getPresetV();
        }
        
        public double computePresetN() {
            return this.get$scalar() * this.get$scalar() * term(0).getPresetN();
        }
        
        public double computeValue(boolean useWeakCache) {
            return this.get$scalar() * term(0).value(useWeakCache);
        }
        
        public double computeVelocity(boolean useWeakCache) {
            return this.get$scalar() * term(0).velocity(useWeakCache);
        }
        
        public double computeNoise(boolean useWeakCache) {
            return this.get$scalar() * this.get$scalar() *
              term(0).noise(useWeakCache);
        }
        
        public java.lang.String toString() {
            return "(" +
            this.get$scalar() +
            "*" +
            java.lang.String.valueOf(
                               fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                   term(0))) +
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
                                                 tmp.term(0));
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var386 = val;
                    fabric.worker.transaction.TransactionManager $tm392 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled395 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff393 = 1;
                    boolean $doBackoff394 = true;
                    boolean $retry389 = true;
                    $label387: for (boolean $commit388 = false; !$commit388; ) {
                        if ($backoffEnabled395) {
                            if ($doBackoff394) {
                                if ($backoff393 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff393);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e390) {
                                            
                                        }
                                    }
                                }
                                if ($backoff393 < 5000) $backoff393 *= 2;
                            }
                            $doBackoff394 = $backoff393 <= 32 || !$doBackoff394;
                        }
                        $commit388 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                otherScalar * tmp.get$scalar(),
                                                tmp.term(0));
                        }
                        catch (final fabric.worker.RetryException $e390) {
                            $commit388 = false;
                            continue $label387;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e390) {
                            $commit388 = false;
                            fabric.common.TransactionID $currentTid391 =
                              $tm392.getCurrentTid();
                            if ($e390.tid.isDescendantOf($currentTid391))
                                continue $label387;
                            if ($currentTid391.parent != null) {
                                $retry389 = false;
                                throw $e390;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e390) {
                            $commit388 = false;
                            if ($tm392.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid391 =
                              $tm392.getCurrentTid();
                            if ($e390.tid.isDescendantOf($currentTid391)) {
                                $retry389 = true;
                            }
                            else if ($currentTid391.parent != null) {
                                $retry389 = false;
                                throw $e390;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e390) {
                            $commit388 = false;
                            if ($tm392.checkForStaleObjects())
                                continue $label387;
                            $retry389 = false;
                            throw new fabric.worker.AbortException($e390);
                        }
                        finally {
                            if ($commit388) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e390) {
                                    $commit388 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e390) {
                                    $commit388 = false;
                                    fabric.common.TransactionID $currentTid391 =
                                      $tm392.getCurrentTid();
                                    if ($currentTid391 != null) {
                                        if ($e390.tid.equals($currentTid391) ||
                                              !$e390.tid.isDescendantOf(
                                                           $currentTid391)) {
                                            throw $e390;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit388 && $retry389) {
                                { val = val$var386; }
                                continue $label387;
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
                  ((fabric.metrics.ScaledMetric)
                     fabric.lang.Object._Proxy.$getProxy(other)).term(0).
                  equals(tmp.term(0))) {
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
                                                     tmp.term(0));
                }
                else {
                    {
                        fabric.metrics.DerivedMetric val$var396 = val;
                        fabric.worker.transaction.TransactionManager $tm402 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled405 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff403 = 1;
                        boolean $doBackoff404 = true;
                        boolean $retry399 = true;
                        $label397: for (boolean $commit398 = false; !$commit398;
                                        ) {
                            if ($backoffEnabled405) {
                                if ($doBackoff404) {
                                    if ($backoff403 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff403);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e400) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff403 < 5000) $backoff403 *= 2;
                                }
                                $doBackoff404 = $backoff403 <= 32 ||
                                                  !$doBackoff404;
                            }
                            $commit398 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                val =
                                  ((fabric.metrics.ScaledMetric)
                                     new fabric.metrics.ScaledMetric._Impl(s).
                                     $getProxy()).fabric$metrics$ScaledMetric$(
                                                    that.get$scalar() +
                                                        tmp.get$scalar(),
                                                    tmp.term(0));
                            }
                            catch (final fabric.worker.RetryException $e400) {
                                $commit398 = false;
                                continue $label397;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e400) {
                                $commit398 = false;
                                fabric.common.TransactionID $currentTid401 =
                                  $tm402.getCurrentTid();
                                if ($e400.tid.isDescendantOf($currentTid401))
                                    continue $label397;
                                if ($currentTid401.parent != null) {
                                    $retry399 = false;
                                    throw $e400;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e400) {
                                $commit398 = false;
                                if ($tm402.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid401 =
                                  $tm402.getCurrentTid();
                                if ($e400.tid.isDescendantOf($currentTid401)) {
                                    $retry399 = true;
                                }
                                else if ($currentTid401.parent != null) {
                                    $retry399 = false;
                                    throw $e400;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e400) {
                                $commit398 = false;
                                if ($tm402.checkForStaleObjects())
                                    continue $label397;
                                $retry399 = false;
                                throw new fabric.worker.AbortException($e400);
                            }
                            finally {
                                if ($commit398) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e400) {
                                        $commit398 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e400) {
                                        $commit398 = false;
                                        fabric.common.TransactionID
                                          $currentTid401 =
                                          $tm402.getCurrentTid();
                                        if ($currentTid401 != null) {
                                            if ($e400.tid.equals(
                                                            $currentTid401) ||
                                                  !$e400.tid.
                                                  isDescendantOf(
                                                    $currentTid401)) {
                                                throw $e400;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit398 && $retry399) {
                                    { val = val$var396; }
                                    continue $label397;
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
            return java.util.Arrays.hashCode(this.get$terms().array()) * 32 +
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
                  java.util.Arrays.deepEquals(this.get$terms().array(),
                                              that.get$terms().array()) &&
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
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.ScaledMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -75, -37, 81, -34, -78,
    43, 99, -65, -30, 74, -25, 104, -48, -34, 113, -61, 107, 68, -83, 107, -44,
    69, -98, -2, 24, 32, -20, 75, -52, 127, -94, -87 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525439166000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
