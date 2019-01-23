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
                            throw new fabric.worker.UserAbortException();
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
                                            );
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
                                throw new fabric.worker.UserAbortException();
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
                                                UserAbortException();
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

                     fabric.worker.metrics.treaties.TreatySet treaties, long expiry,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, treaties, expiry,
                  labelStore, labelOnum, accessPolicyStore, accessPolicyOnum,
                  in, refTypes, intraStoreRefs, interStoreRefs);
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

                         fabric.worker.metrics.treaties.TreatySet treaties, long expiry,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, treaties, expiry,
                      labelStore, labelOnum, accessPolicyStore,
                      accessPolicyOnum, in, refTypes, intraStoreRefs,
                      interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.ScaledMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -56, 67, 10, -48, -115,
    -127, 67, 64, -6, 53, -71, 94, -69, 35, 88, 83, 19, -43, -42, 17, 96, 102,
    122, 118, 9, 2, -55, -48, 105, -85, -113, -45 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1537039040000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
