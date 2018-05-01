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
    
    public static final byte[] $classHash = new byte[] { 38, -120, 80, 101, 48,
    25, -37, -36, -80, 51, 76, -82, -84, -4, -28, 71, -51, -32, 121, -63, 120,
    -19, 18, 11, -96, 79, 88, 59, 114, -52, 91, -87 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525097266000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
