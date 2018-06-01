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
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.enforcement.DirectPolicy;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.metrics.treaties.enforcement.WitnessPolicy;

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
    
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
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
            return val;
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
                return val;
            }
            return fabric.metrics.Metric._Impl.static_plus(tmp, other);
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base,
                          fabric.worker.metrics.StatsMap weakStats,
                          final fabric.worker.Store s) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(term(0))) instanceof fabric.metrics.SampledMetric)
                return fabric.worker.metrics.treaties.enforcement.DirectPolicy.
                         singleton;
            fabric.worker.metrics.treaties.MetricTreaty witness = null;
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
            witness = m.getThresholdTreaty(rate, baseNow, currentTime);
            return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                     new fabric.worker.metrics.treaties.MetricTreaty[] { witness });
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
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, labelStore, labelOnum,
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
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, labelStore, labelOnum,
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
    
    public static final byte[] $classHash = new byte[] { -113, 24, -12, 110,
    113, 67, 70, 104, 2, 55, 37, 84, 46, 110, 24, -95, -35, 27, -32, -94, 3,
    -43, -96, 104, 67, -53, -125, 72, -99, 24, 79, 101 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527874708000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0ZDXAUV/ndJbnkQiAh4SeEEEJIsfz0TlCrbWotuUI5epAMCYwNlXSz+y63zd7usvuOXFCQVhmo48SOBlpmhKkMTm2NZcaxijpYxqm1DLaO2oodh8pMQavAOIxaHf/q9719d7e3uVuSGTPz3vfuvfd97/t/375M3iBVtkU6ktKQqkXYmEntyEZpKJ7olSybKjFNsu1+mB2UZ1XGj777rNIWJMEEqZMl3dBVWdIGdZuROYlHpT1SVKcsun1bvGsnCcuIuEmyU4wEd3ZnLdJuGtrYsGYwccgU+kdWRyee2tXw7QpSP0DqVb2PSUyVY4bOaJYNkLo0TQ9Ry16vKFQZIHN1SpU+aqmSpu6FjYY+QBptdViXWMai9jZqG9oe3NhoZ0xq8TNzk8i+AWxbGZkZFrDf4LCfYaoWTag260qQUFKlmmLvJvtJZYJUJTVpGDYuSOSkiHKK0Y04D9trVWDTSkoyzaFUjqi6wshSL0Ze4s4HYQOgVqcpSxn5oyp1CSZIo8OSJunD0T5mqfowbK0yMnAKIy1licKmGlOSR6RhOshIs3dfr7MEu8JcLYjCyHzvNk4JbNbisZnLWje23jP+aX2THiQB4Fmhsob81wBSmwdpG01Si+oydRDrViWOSgvOHg4SApvnezY7e773mZv3rWk796qzZ3GJPT1Dj1KZDcqnhub8ojW28q4KZKPGNGwVXaFIcm7VXrHSlTXB2xfkKeJiJLd4btsrDx14nl4Lkto4CcmGlkmDV82VjbSpatR6gOrUkhhV4iRMdSXG1+OkGsYJVafObE8yaVMWJ5UanwoZ/DeoKAkkUEXVMFb1pJEbmxJL8XHWJIRUQyMBaBcJWbwV4EJCgucY2RxNGWkaHdIydBTcOwqNSpacikLcWqoctS05amV0psImMQVeBMCO9kGQUmUL/xUBLsz/K7Us8t4wGgiAWpfKhkKHJBtsJPylu1eDkNhkaAq1BmVt/GycNJ09xn0mjH5ug69yrQTAzq3eDOHGnch0b7j5wuAFx98QVyiNkcUOixHBYsTNInBVh4EUgdQUgdQ0GchGYifi3+T+ErJ5YOUJ1QGhu01NYknDSmdJIMClmsfxuaOAmUcgfUCGqFvZ96nNjxzuqAAPNUcr0WiwtdMbL4UsE4eRBEEwKNcfeve900f3GYXIYaRzSkBPxcSA7PCqyDJkqkDCK5Bf1S69OHh2X2cQk0kY8hyTwBMhabR5zygKzK5ckkNtVCXILNSBpOFSLjPVspRljBZmuOnnYNfoeAEqy8Mgz48f7zOP/+b1P36I3xy5VFrvyrl9lHW5wheJ1fNAnVvQfb9FKey79HTvV47cOLSTKx52LC91YCf2MQhbCeLVsA6+uvut37196o1gwViMhMzMkKbKWS7L3PfhLwDtv9gwBnECIWTimIj/9nwCMPHkFQXeIBVokI6Adbtzu542FDWpSkMaRU/5d/1ta1+8Pt7gmFuDGUd5FllzawKF+UXd5MCFXX9v42QCMl5FBf0Vtjn5ralAeb1lSWPIR/axXy459lPpOHg+ZCdb3Ut5wiFcH4QbcB3XxR28X+tZ+zB2HY62WvMO7831G/HSLPjiQHTyqy2xe685AZ/3RaSxrETA75BcYbLu+fTfgh2hnwRJ9QBp4Pe1pLMdEuQscIMBuHHtmJhMkNlF68W3p3NVdOVjrdUbB65jvVFQSDQwxt04rnUc33EcUMQ8VFIHtBZCKj4oYAeuNpnYz8sGCB/czVGW834Fdiu5IoOMhE3LYMAlhYohrKbTGYbW5+esBle1IaNBuTRV372WmoaQ2SPuVnp44gvvR8YnHF9zCpDlU2oAN45ThPCDZvPTsnDKMr9TOMbGP5ze98Nv7DvkXNCNxdfpBj2T/tav//OzyNOXz5dI1yHFgMjjvxuypTUSwOGqbF7D/C8kbsOXBPy+S8MutyQowZJyhQvn/tTjEyeUnq+vDQrf3gBKZ4Z5h0b3UM1FChPasimF8RZerhUc9fK1JXfFRq4OO7pY6jnZu/u5LZPnH1ghfzlIKvIeOaVGLEbqKvbDWotCiav3F3lje15Xs1AHvdA6CbisgFG3Nzq5miseu3gelauvVqBEBLzdq+ZCfgg67os/Y3xyPtSSnsvYuYZxsYUf/EmfHPMwdn0QoQ6NTkGj032hdxa47y2WGSNwDSFV9wm4cmYyI8rtAnaUl9nNr+yzxm2zC76MsHqEqr0Xayy2raCrEtwDjapJAY/MjHtEmRBwfHrcaz5r/Aoe9nLf7cv9nRCizQIGZsb9nU50I6z65/S4z/isjWJnernf4ct9F7DweQHTM+MeUTQBk9Pjfr/P2gHsxrzcby3F/RxE+gg08PnqOgeGrpbhvmSSvTdbLM9sQeSKgL8tL09AVAEi8NtE4I8a1gi1CsU4fMXbWySTb1vkrbE5f4d9lMF9+XGGH+xcGfx+L6uKj0HbAKp4UsBkGVVgd3Cq4IhCBXz4loLjzyc41SM+AjyF3ZNQ7uYEoJohq2zM15ybCamZ58DqGzOTAVGuC/jODGQ44SPDM9gdKxhhq6HaJY3Ao6kVWg8IcFBANrNoQhRbwHR5Adz8Peuz9hx2JxmwYTjPKDl/beA1MlaIEdfCFA8tJeE6aP2EhOc5sMbPRCUkRJTrAv5+WiaKcarf8RHzu9idZqQKv9ntnIytnsv4fmpBFae47mSPeE1IsA8ahMGsQQGXTjOhQB1bbcIB8IWENSt/vvOkl0ZBsk3AhvLiu4qLhoIOfuyjg5ex+wEY0Dl6kKsC586UMuJqaHsh5j4hYDkpyxgRUdoEbJ6WER/iVC/4CPAadq8wUmlqmZKMc/MkoH0RitMzAn52uubB4Y+we6mEVZDSfgHN6VvFEepNH6EuYvdzRmYJq5STjRtFhXYSvqjWObDpjZkZBVF+JeBr5YWo5OxVcq/Kd0/kgqap+CLrY4ZFfW6vt31kv4LdW5D8WQpopAxN6TU0VR7LHXVP6TuTWRRUBR8PVIdzZJqmOoOvqvzYRQR4CiNPcKlIWslktQjaeQCGgNLMVIoojwg4ML10/GeftZvY/QnScUqyUzFDcTS7HrvVDvv3M1Kh6qyUKB+A9jr/vOZw0ZWZiYIo7wh46ZYhm7NRo7ARvymctwQfb/hXedkDPAu8B6mR7s5Iml1K8uohw9CopGeBuPurBx91Fpd4XRUv/XLsZXrq6oNr5pd5WW2e8r8XgffCifqahSe2X+SPhflX/HCC1CQzmuZ+/HCNQ6ZFkyoXN+w8hZhcvjDUrMUXDuP/3MARChiodvbVgQqcffhrNjdCS747w0m2ZCz8v9HkXxb+I1TTf5m/3IEe27/U/Fd9d2xjKvjR2/ojevPXLi2+fLLizWdSsQuf23S8uYf+D74vQbbPGgAA";
}
