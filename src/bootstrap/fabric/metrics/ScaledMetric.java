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
                                                (fabric.metrics.Metric)
                                                  tmp.get$terms().get(0));
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
                                                    (fabric.metrics.Metric)
                                                      tmp.get$terms().get(0));
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
    
    public static final byte[] $classHash = new byte[] { -41, -9, -81, 111, 89,
    123, -49, 107, -67, -43, -41, -115, -79, -50, 38, 42, -127, -96, 70, -109,
    91, -99, 17, -104, -117, -101, -25, 80, 18, -99, -89, -71 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524081841000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
