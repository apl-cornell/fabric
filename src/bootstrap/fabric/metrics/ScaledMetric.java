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
import fabric.worker.metrics.treaties.statements.EqualityStatement;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;

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
                    fabric.metrics.DerivedMetric val$var417 = val;
                    fabric.worker.transaction.TransactionManager $tm424 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled427 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff425 = 1;
                    boolean $doBackoff426 = true;
                    boolean $retry420 = true;
                    boolean $keepReads421 = false;
                    $label418: for (boolean $commit419 = false; !$commit419; ) {
                        if ($backoffEnabled427) {
                            if ($doBackoff426) {
                                if ($backoff425 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff425));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e422) {
                                            
                                        }
                                    }
                                }
                                if ($backoff425 < 5000) $backoff425 *= 2;
                            }
                            $doBackoff426 = $backoff425 <= 32 || !$doBackoff426;
                        }
                        $commit419 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                val =
                                  ((fabric.metrics.ScaledMetric)
                                     new fabric.metrics.ScaledMetric._Impl(s).
                                     $getProxy()).fabric$metrics$ScaledMetric$(
                                                    otherScalar *
                                                        tmp.get$scalar(),
                                                    tmp.term(0));
                            }
                            catch (final fabric.worker.RetryException $e422) {
                                throw $e422;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e422) {
                                throw $e422;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e422) {
                                throw $e422;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e422) {
                                throw $e422;
                            }
                            catch (final Throwable $e422) {
                                $tm424.getCurrentLog().checkRetrySignal();
                                throw $e422;
                            }
                        }
                        catch (final fabric.worker.RetryException $e422) {
                            $commit419 = false;
                            continue $label418;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e422) {
                            $commit419 = false;
                            $retry420 = false;
                            $keepReads421 = $e422.keepReads;
                            if ($tm424.checkForStaleObjects()) {
                                $retry420 = true;
                                $keepReads421 = false;
                                continue $label418;
                            }
                            fabric.common.TransactionID $currentTid423 =
                              $tm424.getCurrentTid();
                            if ($e422.tid == null ||
                                  !$e422.tid.isDescendantOf($currentTid423)) {
                                throw $e422;
                            }
                            throw new fabric.worker.UserAbortException($e422);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e422) {
                            $commit419 = false;
                            fabric.common.TransactionID $currentTid423 =
                              $tm424.getCurrentTid();
                            if ($e422.tid.isDescendantOf($currentTid423))
                                continue $label418;
                            if ($currentTid423.parent != null) {
                                $retry420 = false;
                                throw $e422;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e422) {
                            $commit419 = false;
                            if ($tm424.checkForStaleObjects())
                                continue $label418;
                            fabric.common.TransactionID $currentTid423 =
                              $tm424.getCurrentTid();
                            if ($e422.tid.isDescendantOf($currentTid423)) {
                                $retry420 = true;
                            }
                            else if ($currentTid423.parent != null) {
                                $retry420 = false;
                                throw $e422;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e422) {
                            $commit419 = false;
                            if ($tm424.checkForStaleObjects())
                                continue $label418;
                            $retry420 = false;
                            throw new fabric.worker.AbortException($e422);
                        }
                        finally {
                            if ($commit419) {
                                fabric.common.TransactionID $currentTid423 =
                                  $tm424.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e422) {
                                    $commit419 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e422) {
                                    $commit419 = false;
                                    $retry420 = false;
                                    $keepReads421 = $e422.keepReads;
                                    if ($tm424.checkForStaleObjects()) {
                                        $retry420 = true;
                                        $keepReads421 = false;
                                        continue $label418;
                                    }
                                    if ($e422.tid ==
                                          null ||
                                          !$e422.tid.isDescendantOf(
                                                       $currentTid423))
                                        throw $e422;
                                    throw new fabric.worker.UserAbortException(
                                            $e422);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e422) {
                                    $commit419 = false;
                                    $currentTid423 = $tm424.getCurrentTid();
                                    if ($currentTid423 != null) {
                                        if ($e422.tid.equals($currentTid423) ||
                                              !$e422.tid.isDescendantOf(
                                                           $currentTid423)) {
                                            throw $e422;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads421) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit419) {
                                { val = val$var417; }
                                if ($retry420) { continue $label418; }
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
                        fabric.metrics.DerivedMetric val$var428 = val;
                        fabric.worker.transaction.TransactionManager $tm435 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled438 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff436 = 1;
                        boolean $doBackoff437 = true;
                        boolean $retry431 = true;
                        boolean $keepReads432 = false;
                        $label429: for (boolean $commit430 = false; !$commit430;
                                        ) {
                            if ($backoffEnabled438) {
                                if ($doBackoff437) {
                                    if ($backoff436 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff436));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e433) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff436 < 5000) $backoff436 *= 2;
                                }
                                $doBackoff437 = $backoff436 <= 32 ||
                                                  !$doBackoff437;
                            }
                            $commit430 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    val =
                                      ((fabric.metrics.ScaledMetric)
                                         new fabric.metrics.ScaledMetric._Impl(
                                           s).$getProxy()).
                                        fabric$metrics$ScaledMetric$(
                                          that.get$scalar() + tmp.get$scalar(),
                                          tmp.term(0));
                                }
                                catch (final fabric.worker.
                                         RetryException $e433) {
                                    throw $e433;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e433) {
                                    throw $e433;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e433) {
                                    throw $e433;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e433) {
                                    throw $e433;
                                }
                                catch (final Throwable $e433) {
                                    $tm435.getCurrentLog().checkRetrySignal();
                                    throw $e433;
                                }
                            }
                            catch (final fabric.worker.RetryException $e433) {
                                $commit430 = false;
                                continue $label429;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e433) {
                                $commit430 = false;
                                $retry431 = false;
                                $keepReads432 = $e433.keepReads;
                                if ($tm435.checkForStaleObjects()) {
                                    $retry431 = true;
                                    $keepReads432 = false;
                                    continue $label429;
                                }
                                fabric.common.TransactionID $currentTid434 =
                                  $tm435.getCurrentTid();
                                if ($e433.tid ==
                                      null ||
                                      !$e433.tid.isDescendantOf(
                                                   $currentTid434)) {
                                    throw $e433;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e433);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e433) {
                                $commit430 = false;
                                fabric.common.TransactionID $currentTid434 =
                                  $tm435.getCurrentTid();
                                if ($e433.tid.isDescendantOf($currentTid434))
                                    continue $label429;
                                if ($currentTid434.parent != null) {
                                    $retry431 = false;
                                    throw $e433;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e433) {
                                $commit430 = false;
                                if ($tm435.checkForStaleObjects())
                                    continue $label429;
                                fabric.common.TransactionID $currentTid434 =
                                  $tm435.getCurrentTid();
                                if ($e433.tid.isDescendantOf($currentTid434)) {
                                    $retry431 = true;
                                }
                                else if ($currentTid434.parent != null) {
                                    $retry431 = false;
                                    throw $e433;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e433) {
                                $commit430 = false;
                                if ($tm435.checkForStaleObjects())
                                    continue $label429;
                                $retry431 = false;
                                throw new fabric.worker.AbortException($e433);
                            }
                            finally {
                                if ($commit430) {
                                    fabric.common.TransactionID $currentTid434 =
                                      $tm435.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e433) {
                                        $commit430 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e433) {
                                        $commit430 = false;
                                        $retry431 = false;
                                        $keepReads432 = $e433.keepReads;
                                        if ($tm435.checkForStaleObjects()) {
                                            $retry431 = true;
                                            $keepReads432 = false;
                                            continue $label429;
                                        }
                                        if ($e433.tid ==
                                              null ||
                                              !$e433.tid.isDescendantOf(
                                                           $currentTid434))
                                            throw $e433;
                                        throw new fabric.worker.
                                                UserAbortException($e433);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e433) {
                                        $commit430 = false;
                                        $currentTid434 = $tm435.getCurrentTid();
                                        if ($currentTid434 != null) {
                                            if ($e433.tid.equals(
                                                            $currentTid434) ||
                                                  !$e433.tid.
                                                  isDescendantOf(
                                                    $currentTid434)) {
                                                throw $e433;
                                            }
                                        }
                                    }
                                } else if ($keepReads432) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit430) {
                                    { val = val$var428; }
                                    if ($retry431) { continue $label429; }
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
            com.google.common.collect.Multimap witnesses =
              com.google.common.collect.HashMultimap.create();
            witnesses.
              put(
                (java.lang.Object) fabric.lang.WrappedJavaInlineable.$unwrap(m),
                new fabric.worker.metrics.treaties.statements.ThresholdStatement(
                  rate, baseNow, currentTime));
            return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                     witnesses);
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
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
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
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { -25, 59, 77, -81, 63,
    -59, -106, 114, 125, -112, -43, 115, -99, 8, 70, 113, 50, -59, -93, -83, 24,
    5, 51, -2, -19, 77, -35, -34, 115, -101, 6, -5 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548260582000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
