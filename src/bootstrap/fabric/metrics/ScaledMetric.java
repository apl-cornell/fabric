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
import fabric.worker.metrics.StatsMap;

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
    
    public double computeValue(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeVelocity(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoise(fabric.worker.metrics.StatsMap weakStats);
    
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
      thresholdPolicy(double rate, double base,
                      fabric.worker.metrics.StatsMap weakStats,
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
        
        public double computeValue(fabric.worker.metrics.StatsMap weakStats) {
            fabric.metrics.Metric term = term(0);
            if (weakStats.containsKey(term)) {
                return this.get$scalar() * weakStats.getValue(term);
            } else {
                return this.get$scalar() * term.value(weakStats);
            }
        }
        
        public double computeVelocity(
          fabric.worker.metrics.StatsMap weakStats) {
            fabric.metrics.Metric term = term(0);
            if (weakStats.containsKey(term)) {
                return this.get$scalar() * weakStats.getVelocity(term);
            } else {
                return this.get$scalar() * term.velocity(weakStats);
            }
        }
        
        public double computeNoise(fabric.worker.metrics.StatsMap weakStats) {
            fabric.metrics.Metric term = term(0);
            if (weakStats.containsKey(term)) {
                return this.get$scalar() * this.get$scalar() *
                  weakStats.getNoise(term);
            } else {
                return this.get$scalar() * this.get$scalar() *
                  term.noise(weakStats);
            }
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
                    fabric.metrics.DerivedMetric val$var349 = val;
                    fabric.worker.transaction.TransactionManager $tm355 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled358 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff356 = 1;
                    boolean $doBackoff357 = true;
                    boolean $retry352 = true;
                    $label350: for (boolean $commit351 = false; !$commit351; ) {
                        if ($backoffEnabled358) {
                            if ($doBackoff357) {
                                if ($backoff356 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff356);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e353) {
                                            
                                        }
                                    }
                                }
                                if ($backoff356 < 5000) $backoff356 *= 2;
                            }
                            $doBackoff357 = $backoff356 <= 32 || !$doBackoff357;
                        }
                        $commit351 = true;
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
                        catch (final fabric.worker.RetryException $e353) {
                            $commit351 = false;
                            continue $label350;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e353) {
                            $commit351 = false;
                            fabric.common.TransactionID $currentTid354 =
                              $tm355.getCurrentTid();
                            if ($e353.tid.isDescendantOf($currentTid354))
                                continue $label350;
                            if ($currentTid354.parent != null) {
                                $retry352 = false;
                                throw $e353;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e353) {
                            $commit351 = false;
                            if ($tm355.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid354 =
                              $tm355.getCurrentTid();
                            if ($e353.tid.isDescendantOf($currentTid354)) {
                                $retry352 = true;
                            }
                            else if ($currentTid354.parent != null) {
                                $retry352 = false;
                                throw $e353;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e353) {
                            $commit351 = false;
                            if ($tm355.checkForStaleObjects())
                                continue $label350;
                            $retry352 = false;
                            throw new fabric.worker.AbortException($e353);
                        }
                        finally {
                            if ($commit351) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e353) {
                                    $commit351 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e353) {
                                    $commit351 = false;
                                    fabric.common.TransactionID $currentTid354 =
                                      $tm355.getCurrentTid();
                                    if ($currentTid354 != null) {
                                        if ($e353.tid.equals($currentTid354) ||
                                              !$e353.tid.isDescendantOf(
                                                           $currentTid354)) {
                                            throw $e353;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit351 && $retry352) {
                                { val = val$var349; }
                                continue $label350;
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
                        fabric.metrics.DerivedMetric val$var359 = val;
                        fabric.worker.transaction.TransactionManager $tm365 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled368 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff366 = 1;
                        boolean $doBackoff367 = true;
                        boolean $retry362 = true;
                        $label360: for (boolean $commit361 = false; !$commit361;
                                        ) {
                            if ($backoffEnabled368) {
                                if ($doBackoff367) {
                                    if ($backoff366 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff366);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e363) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff366 < 5000) $backoff366 *= 2;
                                }
                                $doBackoff367 = $backoff366 <= 32 ||
                                                  !$doBackoff367;
                            }
                            $commit361 = true;
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
                            catch (final fabric.worker.RetryException $e363) {
                                $commit361 = false;
                                continue $label360;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e363) {
                                $commit361 = false;
                                fabric.common.TransactionID $currentTid364 =
                                  $tm365.getCurrentTid();
                                if ($e363.tid.isDescendantOf($currentTid364))
                                    continue $label360;
                                if ($currentTid364.parent != null) {
                                    $retry362 = false;
                                    throw $e363;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e363) {
                                $commit361 = false;
                                if ($tm365.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid364 =
                                  $tm365.getCurrentTid();
                                if ($e363.tid.isDescendantOf($currentTid364)) {
                                    $retry362 = true;
                                }
                                else if ($currentTid364.parent != null) {
                                    $retry362 = false;
                                    throw $e363;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e363) {
                                $commit361 = false;
                                if ($tm365.checkForStaleObjects())
                                    continue $label360;
                                $retry362 = false;
                                throw new fabric.worker.AbortException($e363);
                            }
                            finally {
                                if ($commit361) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e363) {
                                        $commit361 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e363) {
                                        $commit361 = false;
                                        fabric.common.TransactionID
                                          $currentTid364 =
                                          $tm365.getCurrentTid();
                                        if ($currentTid364 != null) {
                                            if ($e363.tid.equals(
                                                            $currentTid364) ||
                                                  !$e363.tid.
                                                  isDescendantOf(
                                                    $currentTid364)) {
                                                throw $e363;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit361 && $retry362) {
                                    { val = val$var359; }
                                    continue $label360;
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
          thresholdPolicy(double rate, double base,
                          fabric.worker.metrics.StatsMap weakStats,
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
    
    public static final byte[] $classHash = new byte[] { 69, -97, 27, -71, -70,
    124, 54, 116, -97, -19, 80, 16, 29, -65, -115, 14, 31, 108, -15, 115, -80,
    -26, -72, 103, 16, -89, -94, -49, -58, 62, 54, 66 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526753800000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
