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
    
    public static final byte[] $classHash = new byte[] { -60, -128, 84, -117,
    27, -22, -83, 56, 40, -35, -98, 65, 3, 34, -44, -77, -110, 55, -49, 28,
    -120, -6, -30, -96, 27, -24, -66, -111, -112, -30, 36, 25 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524349504000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
