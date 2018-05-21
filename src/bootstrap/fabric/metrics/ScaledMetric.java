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
            return this.get$scalar() * term(0).value(weakStats);
        }
        
        public double computeVelocity(
          fabric.worker.metrics.StatsMap weakStats) {
            return this.get$scalar() * term(0).velocity(weakStats);
        }
        
        public double computeNoise(fabric.worker.metrics.StatsMap weakStats) {
            return this.get$scalar() * this.get$scalar() *
              term(0).noise(weakStats);
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
                    fabric.metrics.DerivedMetric val$var321 = val;
                    fabric.worker.transaction.TransactionManager $tm327 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled330 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff328 = 1;
                    boolean $doBackoff329 = true;
                    boolean $retry324 = true;
                    $label322: for (boolean $commit323 = false; !$commit323; ) {
                        if ($backoffEnabled330) {
                            if ($doBackoff329) {
                                if ($backoff328 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff328);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e325) {
                                            
                                        }
                                    }
                                }
                                if ($backoff328 < 5000) $backoff328 *= 2;
                            }
                            $doBackoff329 = $backoff328 <= 32 || !$doBackoff329;
                        }
                        $commit323 = true;
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
                        catch (final fabric.worker.RetryException $e325) {
                            $commit323 = false;
                            continue $label322;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e325) {
                            $commit323 = false;
                            fabric.common.TransactionID $currentTid326 =
                              $tm327.getCurrentTid();
                            if ($e325.tid.isDescendantOf($currentTid326))
                                continue $label322;
                            if ($currentTid326.parent != null) {
                                $retry324 = false;
                                throw $e325;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e325) {
                            $commit323 = false;
                            if ($tm327.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid326 =
                              $tm327.getCurrentTid();
                            if ($e325.tid.isDescendantOf($currentTid326)) {
                                $retry324 = true;
                            }
                            else if ($currentTid326.parent != null) {
                                $retry324 = false;
                                throw $e325;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e325) {
                            $commit323 = false;
                            if ($tm327.checkForStaleObjects())
                                continue $label322;
                            $retry324 = false;
                            throw new fabric.worker.AbortException($e325);
                        }
                        finally {
                            if ($commit323) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e325) {
                                    $commit323 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e325) {
                                    $commit323 = false;
                                    fabric.common.TransactionID $currentTid326 =
                                      $tm327.getCurrentTid();
                                    if ($currentTid326 != null) {
                                        if ($e325.tid.equals($currentTid326) ||
                                              !$e325.tid.isDescendantOf(
                                                           $currentTid326)) {
                                            throw $e325;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit323 && $retry324) {
                                { val = val$var321; }
                                continue $label322;
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
                        fabric.metrics.DerivedMetric val$var331 = val;
                        fabric.worker.transaction.TransactionManager $tm337 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled340 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff338 = 1;
                        boolean $doBackoff339 = true;
                        boolean $retry334 = true;
                        $label332: for (boolean $commit333 = false; !$commit333;
                                        ) {
                            if ($backoffEnabled340) {
                                if ($doBackoff339) {
                                    if ($backoff338 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff338);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e335) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff338 < 5000) $backoff338 *= 2;
                                }
                                $doBackoff339 = $backoff338 <= 32 ||
                                                  !$doBackoff339;
                            }
                            $commit333 = true;
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
                            catch (final fabric.worker.RetryException $e335) {
                                $commit333 = false;
                                continue $label332;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e335) {
                                $commit333 = false;
                                fabric.common.TransactionID $currentTid336 =
                                  $tm337.getCurrentTid();
                                if ($e335.tid.isDescendantOf($currentTid336))
                                    continue $label332;
                                if ($currentTid336.parent != null) {
                                    $retry334 = false;
                                    throw $e335;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e335) {
                                $commit333 = false;
                                if ($tm337.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid336 =
                                  $tm337.getCurrentTid();
                                if ($e335.tid.isDescendantOf($currentTid336)) {
                                    $retry334 = true;
                                }
                                else if ($currentTid336.parent != null) {
                                    $retry334 = false;
                                    throw $e335;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e335) {
                                $commit333 = false;
                                if ($tm337.checkForStaleObjects())
                                    continue $label332;
                                $retry334 = false;
                                throw new fabric.worker.AbortException($e335);
                            }
                            finally {
                                if ($commit333) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e335) {
                                        $commit333 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e335) {
                                        $commit333 = false;
                                        fabric.common.TransactionID
                                          $currentTid336 =
                                          $tm337.getCurrentTid();
                                        if ($currentTid336 != null) {
                                            if ($e335.tid.equals(
                                                            $currentTid336) ||
                                                  !$e335.tid.
                                                  isDescendantOf(
                                                    $currentTid336)) {
                                                throw $e335;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit333 && $retry334) {
                                    { val = val$var331; }
                                    continue $label332;
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
    
    public static final byte[] $classHash = new byte[] { 17, 115, -99, 114, -33,
    -48, 23, 105, -68, 117, 116, -101, 124, -100, -80, 39, 36, 105, 76, 55, 94,
    -125, 90, 109, -63, -79, 67, 79, -111, -125, 92, 117 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526846993000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0Zf3AUV/ndJbn8IJCQ8KOEEEI46fCjd4JabaMM5Arl6AFXAkhDJW523+W27O0uu+/IBUul7XSgjsNUC7RMC+NUnNoai1ar9Qe2OrUF0ZY6KvYPKX+UEQcZRaviTBW/7+27u73N3ZKbMTPv+/bee9/3vt/v283YFVJnW6QnJQ2pWoSNmtSOrJaG4omkZNlUiWmSbW+C2UF5Um388KVnla4gCSZIsyzphq7Kkjao24xMSdwn7ZKiOmXRzRvjvdtIo4yEayQ7zUhwW1/OIt2moY0OawYTh4zjf2hx9OAT21tfrCEtA6RF1fuZxFQ5ZuiM5tgAac7QzBC17JWKQpUBMlWnVOmnlipp6m7YaOgDpM1Wh3WJZS1qb6S2oe3CjW121qQWPzM/ieIbILaVlZlhgfitjvhZpmrRhGqz3gQJpVSqKfZO8gCpTZC6lCYNw8YZibwWUc4xuhrnYXuTCmJaKUmmeZLaHaquMDLXS1HQOHwXbADS+gxlaaNwVK0uwQRpc0TSJH042s8sVR+GrXVGFk5hpKMiU9jUYEryDmmYDjJyk3df0lmCXY3cLEjCyHTvNs4JfNbh8ZnLW1fWf/LA5/Q1epAEQGaFyhrK3wBEXR6ijTRFLarL1CFsXpQ4LM04uT9ICGye7tns7Pn+/VdXLOl69ZSzZ3aZPRuG7qMyG5SPD015uzO28LYaFKPBNGwVQ6FEc+7VpFjpzZkQ7TMKHHExkl98dePr9+x9nl4OkqY4CcmGls1AVE2VjYypatS6k+rUkhhV4qSR6kqMr8dJPTwnVJ06sxtSKZuyOKnV+FTI4L/BRClggSaqh2dVTxn5Z1Niaf6cMwkh9TBIAMY5QjrnAJ5JSPA4I2ujaSNDo0Nalo5AeEdhUMmS01HIW0uVo7YlR62szlTYJKYgigDZ0X5IUqqs478iIIX5f+WWQ9lbRwIBMOtc2VDokGSDj0S89CU1SIk1hqZQa1DWDpyMk/aTR3jMNGKc2xCr3CoB8HOnt0K4aQ9m+1ZdfWHwjBNvSCuMxshsR8SIEDHiFhGkasZEikBpikBpGgvkIrFj8W/weAnZPLEKjJqB0e2mJrGUYWVyJBDgWk3j9DxQwM07oHxAhWhe2P+ZtZ/d31MDEWqO1KLTYGvYmy/FKhOHJwmSYFBu2XfpnycO7zGKmcNIeFxCj6fEhOzxmsgyZKpAwSuyX9QtvTR4ck84iMWkEeockyASoWh0ec8oSczefJFDa9QlyCS0gaThUr4yNbG0ZYwUZ7jrpyBoc6IAjeURkNfHT/WbR3//5p8+wm+OfCltcdXcfsp6XemLzFp4ok4t2n6TRSns+8OTyccPXdm3jRsedswvd2AYYQzSVoJ8NaxHTu18593zx38TLDqLkZCZHdJUOcd1mXod/gIw/osDcxAnEEMljon87y4UABNPXlCUDUqBBuUIRLfDm/WMoagpVRrSKEbKBy0fWvrSnw+0Ou7WYMYxnkWW3JhBcX5WH9l7Zvu/ujibgIxXUdF+xW1OfWsvcl5pWdIoypF78NdzjrwhHYXIh+pkq7spLziE24NwBy7jtriFw6WetY8i6HGs1VkIeG+tX42XZjEWB6JjT3fEll92Er4Qi8hjXpmE3yK50mTZ85l/BHtCPw+S+gHSyu9rSWdbJKhZEAYDcOPaMTGZIJNL1ktvT+eq6C3kWqc3D1zHerOgWGjgGXfjc5MT+E7ggCGmoZF6YHQQUjNN4BCutpsIp+UChD/czknmc7gAwUJuyCAjjaZlMJCSQsfQqGYyWYbe5+cshlC1oaJBuzTe3klLzUDK7BJ3K91/8AvXIwcOOrHmNCDzx/UAbhqnCeEHTean5eCUeX6ncIrVfzyx50df37PPuaDbSq/TVXo2883f/eeXkScvnC5TrkOKAZnHf7fmylskgI+LcgUL87+QuA2/KvBRl4VdYUlQgzmVGhcu/fGHDh5TNnxtaVDE9iowOjPMWzS6i2ouVljQ5o1rjNfxdq0YqBcuz7kttuPisGOLuZ6TvbufWzd2+s4F8peDpKYQkeN6xFKi3tI4bLIotLj6ppJo7C7YahLaIAkjTEjtVoHb3dHo1GpueATxAik3X5MgaRO42WvmYn0IOuGLP2N8cjr0kp7L2LmGcbGDH7zVp8bci6AfMtThERY8wu4LPVyUPlmqM2bgEkLqwgJPrk5nJGkWOFRZZ7e8ss8a9812eDPC7hG69iT2WGxj0VZlpAcedY8JPFqd9EiSE9iamPSazxq/goe90vf5Sn8rHP2BwBerkx5J3hP4/MSkz/qsjSAwvdJv8ZW+FwpMWuC7q5MeSZICr52Y9A/4rO1FMOqVfn056acg0cdgrICj/yrw6QrSly2yy3Ol+kwWTE4J/EplfQKiCxCJ3yUSf8SwdlCr2IzDW7y9TjL5tlneHpvLt9/HGAcQPMTwhZ0bg9/vFU3xCRir4CWKCby2gikQPDJecSSJC7zihorjz0c510M+CjyB4DFod/MKUM2QVTbq6861IMA1gd+uTgckOSvwG1XocMxHh68gOFJ0wnpDtcs6gWdTJ4wNhDSoAm+tLpuQ5NMC311ZAbd8z/qsPYfgGUYamOF8RsnHayvvkbFDjLgWxkVoOQ2XwdgE4l0T2M9FZTREkrMC/2JCLopxrt/1UfN7CE4wUofv7HZex07PZXwHtaCLU1x3ske9dmTYD4OCqjGBayZYUKCPrTfhAHhDwp6Vf77zlJc2wTLo4Kb3K6vvai5aizb4mY8NXkPwQ3Cgc/QgNwXOvVzOiYth7IacWyFwd3VORJK5As+akBPv4VzP+CjwKwSvM1JratmygnP3JGB8EZrTHwi8d6LuwcefIHiljFeQ0+cF3jlxrzhK/dZHqXMI3mJkkvBKJd24U7BmPANvVEkHt/+tOqcgyVWBL1dWopaLV8ujqgAezSdNe+lF1s8Mi/rcXud9dH8PwTtQ/FkaeKQNTUkamiqP5o+61ZOf+IZsSTKzI1SHE2SaoTqD96nCs0NeLnO5+WbBOA3vn8sF/nB15kOSqMALJ1Z6r/is/QXBJSi9aclOxwzFseJKBIudCLyDkRpVZ+VUuRnGmyDHWwJ/pzpVkORFgcdumJ55f7QJf/Bbwflu4OP5f/vofh3B+1AG6c6spNnlNK8fMgyNSnoOmLvfcPADzuwyX1LFV3059ho9fvGuJdMrfEW9adz/WQTdC8daGmYe23yOfxgsfLFvTJCGVFbT3B86XM8h06IplavU6Hz2MBEFoMWaUhq8jP8jA59QwUCds68JTODsw1+TuBM6CuBlzrIja+H/iMb+PvNaqGHTBf6VDuzYPdU+ar17dqb64yx76v6nv3VzWE18fPvDA5mffju24UsP35v9H8MOTmK7GgAA";
}
