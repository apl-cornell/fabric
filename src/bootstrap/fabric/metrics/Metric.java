package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.util.HashMap;
import fabric.util.List;
import fabric.util.Set;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.transaction.TransactionManager;
import fabric.lang.security.LabelUtil;

/**
 * Abstract class with base implementation of some {@link Metric} methods.
 */
public interface Metric
  extends java.lang.Comparable, fabric.metrics.util.AbstractSubject {
    /** @return the current value of the {@link Metric}. */
    public double value();
    
    /**
   * @param scalar
   *            a double scalar to scale this metric by
   * @return A {@link Metric} that tracks the scaled value of this
   *         {@link Metric}.
   */
    public fabric.metrics.DerivedMetric times(double scalar);
    
    /**
   * @param other
   *            another {@link Metric} to add with this {@link Metric}.
   * @return a {@link Metric} that tracks the value of the sum of other and
   *         this.
   */
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
    /**
   * @param other
   *            another {@link Metric} to take the minimum of along with this
   *            {@link Metric}.
   * @return a {@link Metric} that tracks the value of the minimum of this and
   *         the other {@link Metric}.
   */
    public fabric.metrics.Metric min(fabric.metrics.Metric other);
    
    /**
   * @param other
   *            another {@link Metric} to take the maximum of along with this
   *            {@link Metric}.
   * @return a {@link Metric} that tracks the value of the maximum of this and
   *         the other {@link Metric}.
   */
    public fabric.metrics.Metric max(fabric.metrics.Metric other);
    
    /**
   * @param rate
   *            the rate parameter for the {@link Bound} on the resuling
   *            {@link Contract}
   * @param base
   *            the base parameter for the {@link Bound} on the resuling
   *            {@link Contract}
   * @param time
   *            the startTime parameter of the {@link Bound} on the resulting
   *            {@link Contract}
   * @return a {@link Contract} which enforces that the {@link Metric}
   *         satisfies a {@link Bound} with the given parameters at the given
   *         time. If such a {@link Contract} already exists, it is
   *         returned, otherwise a new one is created and returned
   *         (unactivated).
   *
   */
    public fabric.metrics.contracts.Contract getContract(double rate,
                                                         double base, long time);
    
    /**
   * @param rate
   *            the rate parameter for the {@link Bound} on the resuling
   *            {@link Contract}
   * @param base
   *            the base parameter for the {@link Bound} on the resuling
   *            {@link Contract}
   * @return a {@link Contract} which enforces that the {@link Metric}
   *         satisfies a {@link Bound} with the given parameters at the
   *         current time. If such a {@link Contract} already exists, it
   *         is returned, otherwise a new one is created and returned
   *         (unactivated).
   */
    public fabric.metrics.contracts.Contract getContract(double rate, double base);
    
    /**
   * Cache of DerivedMetrics using this Metric as a term (helps to break up
   * interning cache to avoid one big map.
   */
    public abstract boolean getUsePreset();
    
    public abstract double getPresetR();
    
    public abstract double getPresetB();
    
    public abstract double getPresetV();
    
    public abstract double getPresetN();
    
    /**
   * @param store
   *            the Store that holds this {@link Metric}
   */
    public fabric.metrics.Metric fabric$metrics$Metric$();
    
    /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the current value of the {@link Metric}.
   */
    public abstract double value(boolean useWeakCache);
    
    /** @return the number of samples of the {@link Metric}. */
    public double samples();
    
    /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the number of samples of the {@link Metric}.
   */
    public abstract double samples(boolean useWeakCache);
    
    /** @return the estimated velocity of the {@link Metric}. */
    public double velocity();
    
    /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the estimated velocity of the {@link Metric}.
   */
    public abstract double velocity(boolean useWeakCache);
    
    /** @return the estimated noise of the {@link Metric}. */
    public double noise();
    
    /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the estimated velocity of the {@link Metric}.
   */
    public abstract double noise(boolean useWeakCache);
    
    /**
   * Used to construct and enforce {@link Contract}s bounding this
   * {@link Metric}s value.
   * <p>
   * Implementations of this method should use "weak" estimates of value,
   * velocity, and noise to avoid contention issues. The internal
   * implementation is expected to have refreshed these estimates recently
   * prior to the call.
   *
   * @param bound
   *            a {@link Bound} that the returned policy enforces.
   * @param useWeakCache
   *            flag indicating if weakly consistent cached values should be
   *            used
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
    public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
      double rate, double base, boolean useWeakCache, final fabric.worker.Store s);
    
    /**
   * Used to construct and enforce {@link Contract}s bounding this
   * {@link Metric}s value.
   * <p>
   * Implementations of this method should use "weak" estimates of value,
   * velocity, and noise to avoid contention issues. The internal
   * implementation is expected to have refreshed these estimates recently
   * prior to the call.
   *
   * @param bound
   *            a {@link Bound} that the returned policy enforces.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
    public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
      double rate, double base, final fabric.worker.Store s);
    
    public fabric.lang.arrays.doubleArray get$weakStats();
    
    public fabric.lang.arrays.doubleArray set$weakStats(fabric.lang.arrays.doubleArray val);
    
    /**
   * Update the current "weak" estimates to be used by {@link #policy(Bound)}
   */
    public void refreshWeakEstimates();
    
    public void refreshWeakEstimates_remote(fabric.lang.security.Principal caller);
    
    /**
   * Utility to allow for running updates as close to the metric as possible.
   *
   * Ugh, public because we don't allow remote calls for non public methods.
   */
    public void refreshLocally();
    
    public void refreshLocally_remote(fabric.lang.security.Principal caller);
    
    /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #value()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
    public double weakValue();
    
    /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #samples()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
    public double weakSamples();
    
    /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #velocity()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
    public double weakVelocity();
    
    /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #noise()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
    public double weakNoise();
    
    /**
   * @param useWeakCache
   *            Flag to indicate if weak values should be used.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
    public abstract double computeValue(boolean useWeakCache);
    
    /**
   * @param useWeakCache
   *            Flag to indicate if weak values should be used.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
    public abstract double computeSamples(boolean useWeakCache);
    
    /**
   * @param useWeakCache
   *            Flag to indicate if weak values should be used.
   * @return a freshly computed weak velocity for this {@link DerivedMetric}.
   */
    public abstract double computeVelocity(boolean useWeakCache);
    
    /**
   * @param useWeakCache
   *            Flag to indicate if weak values should be used.
   * @return a freshly computed noise for this {@link DerivedMetric}.
   */
    public abstract double computeNoise(boolean useWeakCache);
    
    /**
   * @return true iff all the sampling and transformations on this metric are
   *         stored on a single store.
   */
    public abstract boolean isSingleStore();
    
    /**
   * @param contract
   *            a {@link Contract} to store with this {@link Metric}
   * @throws IllegalArgumentException
   *             if the {@link Contract} doesn't enforce a {@link Bound}
   *             on this {@link Metric}
   */
    public void addContract(fabric.metrics.contracts.Contract contract);
    
    /**
   * @param bound
   *        the {@link Bound} that the contract will enforce on this
   *        {@link Metric}
   * @return a {@link Contract} asserting this metric satisfies the
   *       given {@link Bound}.
   */
    public fabric.metrics.contracts.Contract createContract(double rate,
                                                            double base, long time);
    
    /**
   * @param time
   *        the time we're searching for {@link Contract}s for this
   *        {@link Metric}
   * @return a {@link Set} of {@link Contract}s that are currently
   *       enforced for this {@link Metric}
   */
    public int compareTo(java.lang.Object that);
    
    /**
   * {@inheritDoc}
   *
   * If the {@link Observer} is a {@link Contract} and is now
   * {@link Contract#stale()}, this clears it out from the Metric's stored
   * contract set.
   */
    public void removeObserver(fabric.metrics.util.Observer obs);
    
    public static class _Proxy
    extends fabric.metrics.util.AbstractSubject._Proxy
      implements fabric.metrics.Metric {
        public fabric.lang.arrays.doubleArray get$weakStats() {
            return ((fabric.metrics.Metric._Impl) fetch()).get$weakStats();
        }
        
        public fabric.lang.arrays.doubleArray set$weakStats(
          fabric.lang.arrays.doubleArray val) {
            return ((fabric.metrics.Metric._Impl) fetch()).set$weakStats(val);
        }
        
        public double value() {
            return ((fabric.metrics.Metric) fetch()).value();
        }
        
        public fabric.metrics.DerivedMetric times(double arg1) {
            return ((fabric.metrics.Metric) fetch()).times(arg1);
        }
        
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.Metric) fetch()).plus(arg1);
        }
        
        public static fabric.metrics.DerivedMetric static_plus(
          fabric.metrics.Metric arg1, fabric.metrics.Metric arg2) {
            return fabric.metrics.Metric._Impl.static_plus(arg1, arg2);
        }
        
        public fabric.metrics.Metric min(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.Metric) fetch()).min(arg1);
        }
        
        public fabric.metrics.Metric max(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.Metric) fetch()).max(arg1);
        }
        
        public fabric.metrics.contracts.Contract getContract(double arg1,
                                                             double arg2,
                                                             long arg3) {
            return ((fabric.metrics.Metric) fetch()).getContract(arg1, arg2,
                                                                 arg3);
        }
        
        public fabric.metrics.contracts.Contract getContract(double arg1,
                                                             double arg2) {
            return ((fabric.metrics.Metric) fetch()).getContract(arg1, arg2);
        }
        
        public static fabric.metrics.Metric scaleAtStore(
          fabric.worker.Store arg1, double arg2, fabric.metrics.Metric arg3) {
            return fabric.metrics.Metric._Impl.scaleAtStore(arg1, arg2, arg3);
        }
        
        public static fabric.metrics.Metric addAtStore(
          fabric.worker.Store arg1, fabric.metrics.Metric[] arg2) {
            return fabric.metrics.Metric._Impl.addAtStore(arg1, arg2);
        }
        
        public static fabric.metrics.Metric minAtStore(
          fabric.worker.Store arg1, fabric.metrics.Metric[] arg2) {
            return fabric.metrics.Metric._Impl.minAtStore(arg1, arg2);
        }
        
        public static fabric.metrics.Metric maxAtStore(
          fabric.worker.Store arg1, fabric.metrics.Metric[] arg2) {
            return fabric.metrics.Metric._Impl.maxAtStore(arg1, arg2);
        }
        
        public boolean getUsePreset() {
            return ((fabric.metrics.Metric) fetch()).getUsePreset();
        }
        
        public double getPresetR() {
            return ((fabric.metrics.Metric) fetch()).getPresetR();
        }
        
        public double getPresetB() {
            return ((fabric.metrics.Metric) fetch()).getPresetB();
        }
        
        public double getPresetV() {
            return ((fabric.metrics.Metric) fetch()).getPresetV();
        }
        
        public double getPresetN() {
            return ((fabric.metrics.Metric) fetch()).getPresetN();
        }
        
        public fabric.metrics.Metric fabric$metrics$Metric$() {
            return ((fabric.metrics.Metric) fetch()).fabric$metrics$Metric$();
        }
        
        public double value(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).value(arg1);
        }
        
        public double samples() {
            return ((fabric.metrics.Metric) fetch()).samples();
        }
        
        public double samples(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).samples(arg1);
        }
        
        public double velocity() {
            return ((fabric.metrics.Metric) fetch()).velocity();
        }
        
        public double velocity(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).velocity(arg1);
        }
        
        public double noise() {
            return ((fabric.metrics.Metric) fetch()).noise();
        }
        
        public double noise(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).noise(arg1);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          double arg1, double arg2, boolean arg3, fabric.worker.Store arg4) {
            return ((fabric.metrics.Metric) fetch()).policy(arg1, arg2, arg3,
                                                            arg4);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          double arg1, double arg2, fabric.worker.Store arg3) {
            return ((fabric.metrics.Metric) fetch()).policy(arg1, arg2, arg3);
        }
        
        public void refreshWeakEstimates() {
            ((fabric.metrics.Metric) fetch()).refreshWeakEstimates();
        }
        
        public void refreshWeakEstimates_remote(
          fabric.lang.security.Principal arg1) {
            ((fabric.metrics.Metric) fetch()).refreshWeakEstimates_remote(arg1);
        }
        
        public static final java.lang.Class[] $paramTypes0 = null;
        
        public void refreshWeakEstimates$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                refreshWeakEstimates();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "refreshWeakEstimates",
                                                  $paramTypes0, null);
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public void refreshLocally() {
            ((fabric.metrics.Metric) fetch()).refreshLocally();
        }
        
        public void refreshLocally_remote(fabric.lang.security.Principal arg1) {
            ((fabric.metrics.Metric) fetch()).refreshLocally_remote(arg1);
        }
        
        public static final java.lang.Class[] $paramTypes1 = null;
        
        public void refreshLocally$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                refreshLocally();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "refreshLocally",
                                                  $paramTypes1, null);
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public double weakValue() {
            return ((fabric.metrics.Metric) fetch()).weakValue();
        }
        
        public double weakSamples() {
            return ((fabric.metrics.Metric) fetch()).weakSamples();
        }
        
        public double weakVelocity() {
            return ((fabric.metrics.Metric) fetch()).weakVelocity();
        }
        
        public double weakNoise() {
            return ((fabric.metrics.Metric) fetch()).weakNoise();
        }
        
        public double computeValue(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).computeValue(arg1);
        }
        
        public double computeSamples(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).computeSamples(arg1);
        }
        
        public double computeVelocity(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).computeVelocity(arg1);
        }
        
        public double computeNoise(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).computeNoise(arg1);
        }
        
        public boolean isSingleStore() {
            return ((fabric.metrics.Metric) fetch()).isSingleStore();
        }
        
        public void addContract(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.Metric) fetch()).addContract(arg1);
        }
        
        public fabric.metrics.contracts.Contract createContract(double arg1,
                                                                double arg2,
                                                                long arg3) {
            return ((fabric.metrics.Metric) fetch()).createContract(arg1, arg2,
                                                                    arg3);
        }
        
        public int compareTo(java.lang.Object arg1) {
            return ((fabric.metrics.Metric) fetch()).compareTo(arg1);
        }
        
        public static fabric.metrics.DerivedMetric findDerivedMetric(
          fabric.worker.Store arg1, fabric.metrics.DerivedMetric arg2) {
            return fabric.metrics.Metric._Impl.findDerivedMetric(arg1, arg2);
        }
        
        public _Proxy(Metric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.metrics.util.AbstractSubject._Impl
      implements fabric.metrics.Metric {
        /** @return the current value of the {@link Metric}. */
        public double value() { return value(false); }
        
        /**
   * @param scalar
   *            a double scalar to scale this metric by
   * @return A {@link Metric} that tracks the scaled value of this
   *         {@link Metric}.
   */
        public fabric.metrics.DerivedMetric times(double scalar) {
            return fabric.metrics.Metric._Impl.static_times(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy(), scalar);
        }
        
        private static fabric.metrics.DerivedMetric static_times(
          fabric.metrics.Metric tmp, double scalar) {
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.ScaledMetric)
                     new fabric.metrics.ScaledMetric._Impl(s).$getProxy()).
                    fabric$metrics$ScaledMetric$(scalar, tmp);
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var88 = val;
                    fabric.worker.transaction.TransactionManager $tm94 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled97 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff95 = 1;
                    boolean $doBackoff96 = true;
                    boolean $retry91 = true;
                    $label89: for (boolean $commit90 = false; !$commit90; ) {
                        if ($backoffEnabled97) {
                            if ($doBackoff96) {
                                if ($backoff95 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff95);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e92) {
                                            
                                        }
                                    }
                                }
                                if ($backoff95 < 5000) $backoff95 *= 2;
                            }
                            $doBackoff96 = $backoff95 <= 32 || !$doBackoff96;
                        }
                        $commit90 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                scalar, tmp);
                        }
                        catch (final fabric.worker.RetryException $e92) {
                            $commit90 = false;
                            continue $label89;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e92) {
                            $commit90 = false;
                            fabric.common.TransactionID $currentTid93 =
                              $tm94.getCurrentTid();
                            if ($e92.tid.isDescendantOf($currentTid93))
                                continue $label89;
                            if ($currentTid93.parent != null) {
                                $retry91 = false;
                                throw $e92;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e92) {
                            $commit90 = false;
                            if ($tm94.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid93 =
                              $tm94.getCurrentTid();
                            if ($e92.tid.isDescendantOf($currentTid93)) {
                                $retry91 = true;
                            }
                            else if ($currentTid93.parent != null) {
                                $retry91 = false;
                                throw $e92;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e92) {
                            $commit90 = false;
                            if ($tm94.checkForStaleObjects()) continue $label89;
                            $retry91 = false;
                            throw new fabric.worker.AbortException($e92);
                        }
                        finally {
                            if ($commit90) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e92) {
                                    $commit90 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e92) {
                                    $commit90 = false;
                                    fabric.common.TransactionID $currentTid93 =
                                      $tm94.getCurrentTid();
                                    if ($currentTid93 != null) {
                                        if ($e92.tid.equals($currentTid93) ||
                                              !$e92.tid.isDescendantOf(
                                                          $currentTid93)) {
                                            throw $e92;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit90 && $retry91) {
                                { val = val$var88; }
                                continue $label89;
                            }
                        }
                    }
                }
            }
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        /**
   * @param other
   *            another {@link Metric} to add with this {@link Metric}.
   * @return a {@link Metric} that tracks the value of the sum of other and
   *         this.
   */
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            return fabric.metrics.Metric._Impl.static_plus(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy(), other);
        }
        
        public static fabric.metrics.DerivedMetric static_plus(
          fabric.metrics.Metric tmp, fabric.metrics.Metric other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.SumMetric)
                return other.plus(tmp);
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.SumMetric)
                     new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                    fabric$metrics$SumMetric$(new fabric.metrics.Metric[] { tmp,
                                                other });
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var98 = val;
                    fabric.worker.transaction.TransactionManager $tm104 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled107 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff105 = 1;
                    boolean $doBackoff106 = true;
                    boolean $retry101 = true;
                    $label99: for (boolean $commit100 = false; !$commit100; ) {
                        if ($backoffEnabled107) {
                            if ($doBackoff106) {
                                if ($backoff105 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff105);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e102) {
                                            
                                        }
                                    }
                                }
                                if ($backoff105 < 5000) $backoff105 *= 2;
                            }
                            $doBackoff106 = $backoff105 <= 32 || !$doBackoff106;
                        }
                        $commit100 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).
                                fabric$metrics$SumMetric$(
                                  new fabric.metrics.Metric[] { tmp, other });
                        }
                        catch (final fabric.worker.RetryException $e102) {
                            $commit100 = false;
                            continue $label99;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e102) {
                            $commit100 = false;
                            fabric.common.TransactionID $currentTid103 =
                              $tm104.getCurrentTid();
                            if ($e102.tid.isDescendantOf($currentTid103))
                                continue $label99;
                            if ($currentTid103.parent != null) {
                                $retry101 = false;
                                throw $e102;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e102) {
                            $commit100 = false;
                            if ($tm104.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid103 =
                              $tm104.getCurrentTid();
                            if ($e102.tid.isDescendantOf($currentTid103)) {
                                $retry101 = true;
                            }
                            else if ($currentTid103.parent != null) {
                                $retry101 = false;
                                throw $e102;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e102) {
                            $commit100 = false;
                            if ($tm104.checkForStaleObjects())
                                continue $label99;
                            $retry101 = false;
                            throw new fabric.worker.AbortException($e102);
                        }
                        finally {
                            if ($commit100) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e102) {
                                    $commit100 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e102) {
                                    $commit100 = false;
                                    fabric.common.TransactionID $currentTid103 =
                                      $tm104.getCurrentTid();
                                    if ($currentTid103 != null) {
                                        if ($e102.tid.equals($currentTid103) ||
                                              !$e102.tid.isDescendantOf(
                                                           $currentTid103)) {
                                            throw $e102;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit100 && $retry101) {
                                { val = val$var98; }
                                continue $label99;
                            }
                        }
                    }
                }
            }
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        /**
   * @param other
   *            another {@link Metric} to take the minimum of along with this
   *            {@link Metric}.
   * @return a {@link Metric} that tracks the value of the minimum of this and
   *         the other {@link Metric}.
   */
        public fabric.metrics.Metric min(fabric.metrics.Metric other) {
            return fabric.metrics.Metric._Impl.static_min(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy(), other);
        }
        
        private static fabric.metrics.Metric static_min(
          fabric.metrics.Metric tmp, fabric.metrics.Metric other) {
            if (tmp.equals(other)) return tmp;
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.MinMetric)
                return other.min(tmp);
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            if (tmp.compareTo(
                      (java.lang.Object)
                        fabric.lang.WrappedJavaInlineable.$unwrap(other)) >
                  0) {
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    val =
                      ((fabric.metrics.MinMetric)
                         new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                        fabric$metrics$MinMetric$(
                          new fabric.metrics.Metric[] { other, tmp });
                }
                else {
                    {
                        fabric.metrics.DerivedMetric val$var108 = val;
                        fabric.worker.transaction.TransactionManager $tm114 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled117 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff115 = 1;
                        boolean $doBackoff116 = true;
                        boolean $retry111 = true;
                        $label109: for (boolean $commit110 = false; !$commit110;
                                        ) {
                            if ($backoffEnabled117) {
                                if ($doBackoff116) {
                                    if ($backoff115 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff115);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e112) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff115 < 5000) $backoff115 *= 2;
                                }
                                $doBackoff116 = $backoff115 <= 32 ||
                                                  !$doBackoff116;
                            }
                            $commit110 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).
                                    fabric$metrics$MinMetric$(
                                      new fabric.metrics.Metric[] { other,
                                        tmp });
                            }
                            catch (final fabric.worker.RetryException $e112) {
                                $commit110 = false;
                                continue $label109;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e112) {
                                $commit110 = false;
                                fabric.common.TransactionID $currentTid113 =
                                  $tm114.getCurrentTid();
                                if ($e112.tid.isDescendantOf($currentTid113))
                                    continue $label109;
                                if ($currentTid113.parent != null) {
                                    $retry111 = false;
                                    throw $e112;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e112) {
                                $commit110 = false;
                                if ($tm114.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid113 =
                                  $tm114.getCurrentTid();
                                if ($e112.tid.isDescendantOf($currentTid113)) {
                                    $retry111 = true;
                                }
                                else if ($currentTid113.parent != null) {
                                    $retry111 = false;
                                    throw $e112;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e112) {
                                $commit110 = false;
                                if ($tm114.checkForStaleObjects())
                                    continue $label109;
                                $retry111 = false;
                                throw new fabric.worker.AbortException($e112);
                            }
                            finally {
                                if ($commit110) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e112) {
                                        $commit110 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e112) {
                                        $commit110 = false;
                                        fabric.common.TransactionID
                                          $currentTid113 =
                                          $tm114.getCurrentTid();
                                        if ($currentTid113 != null) {
                                            if ($e112.tid.equals(
                                                            $currentTid113) ||
                                                  !$e112.tid.
                                                  isDescendantOf(
                                                    $currentTid113)) {
                                                throw $e112;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit110 && $retry111) {
                                    { val = val$var108; }
                                    continue $label109;
                                }
                            }
                        }
                    }
                }
            }
            else {
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    val =
                      ((fabric.metrics.MinMetric)
                         new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                        fabric$metrics$MinMetric$(
                          new fabric.metrics.Metric[] { tmp, other });
                }
                else {
                    {
                        fabric.metrics.DerivedMetric val$var118 = val;
                        fabric.worker.transaction.TransactionManager $tm124 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled127 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff125 = 1;
                        boolean $doBackoff126 = true;
                        boolean $retry121 = true;
                        $label119: for (boolean $commit120 = false; !$commit120;
                                        ) {
                            if ($backoffEnabled127) {
                                if ($doBackoff126) {
                                    if ($backoff125 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff125);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e122) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff125 < 5000) $backoff125 *= 2;
                                }
                                $doBackoff126 = $backoff125 <= 32 ||
                                                  !$doBackoff126;
                            }
                            $commit120 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).
                                    fabric$metrics$MinMetric$(
                                      new fabric.metrics.Metric[] { tmp,
                                        other });
                            }
                            catch (final fabric.worker.RetryException $e122) {
                                $commit120 = false;
                                continue $label119;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e122) {
                                $commit120 = false;
                                fabric.common.TransactionID $currentTid123 =
                                  $tm124.getCurrentTid();
                                if ($e122.tid.isDescendantOf($currentTid123))
                                    continue $label119;
                                if ($currentTid123.parent != null) {
                                    $retry121 = false;
                                    throw $e122;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e122) {
                                $commit120 = false;
                                if ($tm124.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid123 =
                                  $tm124.getCurrentTid();
                                if ($e122.tid.isDescendantOf($currentTid123)) {
                                    $retry121 = true;
                                }
                                else if ($currentTid123.parent != null) {
                                    $retry121 = false;
                                    throw $e122;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e122) {
                                $commit120 = false;
                                if ($tm124.checkForStaleObjects())
                                    continue $label119;
                                $retry121 = false;
                                throw new fabric.worker.AbortException($e122);
                            }
                            finally {
                                if ($commit120) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e122) {
                                        $commit120 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e122) {
                                        $commit120 = false;
                                        fabric.common.TransactionID
                                          $currentTid123 =
                                          $tm124.getCurrentTid();
                                        if ($currentTid123 != null) {
                                            if ($e122.tid.equals(
                                                            $currentTid123) ||
                                                  !$e122.tid.
                                                  isDescendantOf(
                                                    $currentTid123)) {
                                                throw $e122;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit120 && $retry121) {
                                    { val = val$var118; }
                                    continue $label119;
                                }
                            }
                        }
                    }
                }
            }
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        /**
   * @param other
   *            another {@link Metric} to take the maximum of along with this
   *            {@link Metric}.
   * @return a {@link Metric} that tracks the value of the maximum of this and
   *         the other {@link Metric}.
   */
        public fabric.metrics.Metric max(fabric.metrics.Metric other) {
            return this.times(-1).min(other.times(-1)).times(-1);
        }
        
        /**
   * @param rate
   *            the rate parameter for the {@link Bound} on the resuling
   *            {@link Contract}
   * @param base
   *            the base parameter for the {@link Bound} on the resuling
   *            {@link Contract}
   * @param time
   *            the startTime parameter of the {@link Bound} on the resulting
   *            {@link Contract}
   * @return a {@link Contract} which enforces that the {@link Metric}
   *         satisfies a {@link Bound} with the given parameters at the given
   *         time. If such a {@link Contract} already exists, it is
   *         returned, otherwise a new one is created and returned
   *         (unactivated).
   *
   */
        public fabric.metrics.contracts.Contract getContract(double rate,
                                                             double base,
                                                             long time) {
            return fabric.metrics.Metric._Impl.static_getContract(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy(), rate, base,
                                                 time);
        }
        
        private static fabric.metrics.contracts.Contract static_getContract(
          fabric.metrics.Metric tmp, double rate, double base, long time) {
            fabric.metrics.contracts.Contract mc = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                mc = tmp.createContract(rate, base, time);
            }
            else {
                {
                    fabric.metrics.contracts.Contract mc$var128 = mc;
                    fabric.worker.transaction.TransactionManager $tm134 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled137 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff135 = 1;
                    boolean $doBackoff136 = true;
                    boolean $retry131 = true;
                    $label129: for (boolean $commit130 = false; !$commit130; ) {
                        if ($backoffEnabled137) {
                            if ($doBackoff136) {
                                if ($backoff135 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff135);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e132) {
                                            
                                        }
                                    }
                                }
                                if ($backoff135 < 5000) $backoff135 *= 2;
                            }
                            $doBackoff136 = $backoff135 <= 32 || !$doBackoff136;
                        }
                        $commit130 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { mc = tmp.createContract(rate, base, time); }
                        catch (final fabric.worker.RetryException $e132) {
                            $commit130 = false;
                            continue $label129;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e132) {
                            $commit130 = false;
                            fabric.common.TransactionID $currentTid133 =
                              $tm134.getCurrentTid();
                            if ($e132.tid.isDescendantOf($currentTid133))
                                continue $label129;
                            if ($currentTid133.parent != null) {
                                $retry131 = false;
                                throw $e132;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e132) {
                            $commit130 = false;
                            if ($tm134.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid133 =
                              $tm134.getCurrentTid();
                            if ($e132.tid.isDescendantOf($currentTid133)) {
                                $retry131 = true;
                            }
                            else if ($currentTid133.parent != null) {
                                $retry131 = false;
                                throw $e132;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e132) {
                            $commit130 = false;
                            if ($tm134.checkForStaleObjects())
                                continue $label129;
                            $retry131 = false;
                            throw new fabric.worker.AbortException($e132);
                        }
                        finally {
                            if ($commit130) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e132) {
                                    $commit130 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e132) {
                                    $commit130 = false;
                                    fabric.common.TransactionID $currentTid133 =
                                      $tm134.getCurrentTid();
                                    if ($currentTid133 != null) {
                                        if ($e132.tid.equals($currentTid133) ||
                                              !$e132.tid.isDescendantOf(
                                                           $currentTid133)) {
                                            throw $e132;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit130 && $retry131) {
                                { mc = mc$var128; }
                                continue $label129;
                            }
                        }
                    }
                }
            }
            return mc;
        }
        
        /**
   * @param rate
   *            the rate parameter for the {@link Bound} on the resuling
   *            {@link Contract}
   * @param base
   *            the base parameter for the {@link Bound} on the resuling
   *            {@link Contract}
   * @return a {@link Contract} which enforces that the {@link Metric}
   *         satisfies a {@link Bound} with the given parameters at the
   *         current time. If such a {@link Contract} already exists, it
   *         is returned, otherwise a new one is created and returned
   *         (unactivated).
   */
        public fabric.metrics.contracts.Contract getContract(double rate,
                                                             double base) {
            return getContract(rate, base, 0);
        }
        
        /**
   * Create a locally tracked {@link DerivedMetric} that takes a scaled value
   * of another {@link Metric}.
   *
   * @param s
   *            the {@link Store} the returned {@link DerivedMetric} will be
   *            stored at
   * @param a
   *        the scaling factor
   * @param term
   *        the {@link Metric}
   * @return the locally tracked {@link DerivedMetric} for the scaled value of
   *       the given {@link Metric}.
   */
        public static fabric.metrics.Metric scaleAtStore(
          final fabric.worker.Store s, double a, fabric.metrics.Metric term) {
            if (term.$getStore().equals(s))
                return fabric.metrics.Metric._Impl.findDerivedMetric(
                                                     s, term.times(a));
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.ScaledMetric)
                     new fabric.metrics.ScaledMetric._Impl(s).$getProxy()).
                    fabric$metrics$ScaledMetric$(a, term);
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var138 = val;
                    fabric.worker.transaction.TransactionManager $tm144 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled147 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff145 = 1;
                    boolean $doBackoff146 = true;
                    boolean $retry141 = true;
                    $label139: for (boolean $commit140 = false; !$commit140; ) {
                        if ($backoffEnabled147) {
                            if ($doBackoff146) {
                                if ($backoff145 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff145);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e142) {
                                            
                                        }
                                    }
                                }
                                if ($backoff145 < 5000) $backoff145 *= 2;
                            }
                            $doBackoff146 = $backoff145 <= 32 || !$doBackoff146;
                        }
                        $commit140 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                a, term);
                        }
                        catch (final fabric.worker.RetryException $e142) {
                            $commit140 = false;
                            continue $label139;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e142) {
                            $commit140 = false;
                            fabric.common.TransactionID $currentTid143 =
                              $tm144.getCurrentTid();
                            if ($e142.tid.isDescendantOf($currentTid143))
                                continue $label139;
                            if ($currentTid143.parent != null) {
                                $retry141 = false;
                                throw $e142;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e142) {
                            $commit140 = false;
                            if ($tm144.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid143 =
                              $tm144.getCurrentTid();
                            if ($e142.tid.isDescendantOf($currentTid143)) {
                                $retry141 = true;
                            }
                            else if ($currentTid143.parent != null) {
                                $retry141 = false;
                                throw $e142;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e142) {
                            $commit140 = false;
                            if ($tm144.checkForStaleObjects())
                                continue $label139;
                            $retry141 = false;
                            throw new fabric.worker.AbortException($e142);
                        }
                        finally {
                            if ($commit140) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e142) {
                                    $commit140 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e142) {
                                    $commit140 = false;
                                    fabric.common.TransactionID $currentTid143 =
                                      $tm144.getCurrentTid();
                                    if ($currentTid143 != null) {
                                        if ($e142.tid.equals($currentTid143) ||
                                              !$e142.tid.isDescendantOf(
                                                           $currentTid143)) {
                                            throw $e142;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit140 && $retry141) {
                                { val = val$var138; }
                                continue $label139;
                            }
                        }
                    }
                }
            }
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        /**
   * Create a locally tracked {@link DerivedMetric} that takes the sum of
   * other {@link Metric}s.
   *
   * @param s
   *            the {@link Store} the returned {@link DerivedMetric} will be
   *            stored at
   * @param terms
   *            the {@link Metric}s to sum
   * @return the locally tracked {@link DerivedMetric} for the sum of the
   *       terms.
   */
        public static fabric.metrics.Metric addAtStore(
          final fabric.worker.Store s, fabric.metrics.Metric[] terms) {
            if (terms.length == 0)
                throw new java.lang.IllegalArgumentException(
                        "Must have at least 1 term!");
            if (terms.length == 1)
                return fabric.metrics.Metric._Impl.scaleAtStore(s, 1.0,
                                                                terms[0]);
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.SumMetric)
                     new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                    fabric$metrics$SumMetric$(terms);
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var148 = val;
                    fabric.worker.transaction.TransactionManager $tm154 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled157 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff155 = 1;
                    boolean $doBackoff156 = true;
                    boolean $retry151 = true;
                    $label149: for (boolean $commit150 = false; !$commit150; ) {
                        if ($backoffEnabled157) {
                            if ($doBackoff156) {
                                if ($backoff155 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff155);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e152) {
                                            
                                        }
                                    }
                                }
                                if ($backoff155 < 5000) $backoff155 *= 2;
                            }
                            $doBackoff156 = $backoff155 <= 32 || !$doBackoff156;
                        }
                        $commit150 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(terms);
                        }
                        catch (final fabric.worker.RetryException $e152) {
                            $commit150 = false;
                            continue $label149;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e152) {
                            $commit150 = false;
                            fabric.common.TransactionID $currentTid153 =
                              $tm154.getCurrentTid();
                            if ($e152.tid.isDescendantOf($currentTid153))
                                continue $label149;
                            if ($currentTid153.parent != null) {
                                $retry151 = false;
                                throw $e152;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e152) {
                            $commit150 = false;
                            if ($tm154.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid153 =
                              $tm154.getCurrentTid();
                            if ($e152.tid.isDescendantOf($currentTid153)) {
                                $retry151 = true;
                            }
                            else if ($currentTid153.parent != null) {
                                $retry151 = false;
                                throw $e152;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e152) {
                            $commit150 = false;
                            if ($tm154.checkForStaleObjects())
                                continue $label149;
                            $retry151 = false;
                            throw new fabric.worker.AbortException($e152);
                        }
                        finally {
                            if ($commit150) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e152) {
                                    $commit150 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e152) {
                                    $commit150 = false;
                                    fabric.common.TransactionID $currentTid153 =
                                      $tm154.getCurrentTid();
                                    if ($currentTid153 != null) {
                                        if ($e152.tid.equals($currentTid153) ||
                                              !$e152.tid.isDescendantOf(
                                                           $currentTid153)) {
                                            throw $e152;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit150 && $retry151) {
                                { val = val$var148; }
                                continue $label149;
                            }
                        }
                    }
                }
            }
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        /**
   * Create a locally tracked {@link DerivedMetric} that takes the minimum of
   * other {@link Metric}s.
   *
   * @param s
   *            the {@link Store} the returned {@link Metric} will be stored
   *            at
   * @param terms
   *        the {@link Metric}s to take the min of
   * @return the locally tracked {@link Metric} for the minimum of the terms.
   */
        public static fabric.metrics.Metric minAtStore(
          final fabric.worker.Store s, fabric.metrics.Metric[] terms) {
            if (terms.length == 0)
                throw new java.lang.IllegalArgumentException(
                        "Must have at least 1 term!");
            if (terms.length == 1)
                return fabric.metrics.Metric._Impl.scaleAtStore(s, 1.0,
                                                                terms[0]);
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.MinMetric)
                     new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                    fabric$metrics$MinMetric$(terms);
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var158 = val;
                    fabric.worker.transaction.TransactionManager $tm164 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled167 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff165 = 1;
                    boolean $doBackoff166 = true;
                    boolean $retry161 = true;
                    $label159: for (boolean $commit160 = false; !$commit160; ) {
                        if ($backoffEnabled167) {
                            if ($doBackoff166) {
                                if ($backoff165 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff165);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e162) {
                                            
                                        }
                                    }
                                }
                                if ($backoff165 < 5000) $backoff165 *= 2;
                            }
                            $doBackoff166 = $backoff165 <= 32 || !$doBackoff166;
                        }
                        $commit160 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(terms);
                        }
                        catch (final fabric.worker.RetryException $e162) {
                            $commit160 = false;
                            continue $label159;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e162) {
                            $commit160 = false;
                            fabric.common.TransactionID $currentTid163 =
                              $tm164.getCurrentTid();
                            if ($e162.tid.isDescendantOf($currentTid163))
                                continue $label159;
                            if ($currentTid163.parent != null) {
                                $retry161 = false;
                                throw $e162;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e162) {
                            $commit160 = false;
                            if ($tm164.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid163 =
                              $tm164.getCurrentTid();
                            if ($e162.tid.isDescendantOf($currentTid163)) {
                                $retry161 = true;
                            }
                            else if ($currentTid163.parent != null) {
                                $retry161 = false;
                                throw $e162;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e162) {
                            $commit160 = false;
                            if ($tm164.checkForStaleObjects())
                                continue $label159;
                            $retry161 = false;
                            throw new fabric.worker.AbortException($e162);
                        }
                        finally {
                            if ($commit160) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e162) {
                                    $commit160 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e162) {
                                    $commit160 = false;
                                    fabric.common.TransactionID $currentTid163 =
                                      $tm164.getCurrentTid();
                                    if ($currentTid163 != null) {
                                        if ($e162.tid.equals($currentTid163) ||
                                              !$e162.tid.isDescendantOf(
                                                           $currentTid163)) {
                                            throw $e162;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit160 && $retry161) {
                                { val = val$var158; }
                                continue $label159;
                            }
                        }
                    }
                }
            }
            return fabric.metrics.Metric._Impl.findDerivedMetric(s, val);
        }
        
        /**
   * Create a locally tracked {@link DerivedMetric} that takes the maximum of
   * other {@link Metric}s.
   *
   * @param s
   *            the {@link Store} the returned {@link Metric} will be stored
   *            at
   * @param terms
   *        the {@link Metric}s to take the max of
   * @return the locally tracked {@link Metric} for the maximum of the terms.
   */
        public static fabric.metrics.Metric maxAtStore(
          final fabric.worker.Store s, fabric.metrics.Metric[] terms) {
            if (terms.length == 0)
                throw new java.lang.IllegalArgumentException(
                        "Must have at least 1 term!");
            if (terms.length == 1)
                return fabric.metrics.Metric._Impl.scaleAtStore(s, 1.0,
                                                                terms[0]);
            for (int i = 0; i < terms.length; i++) {
                terms[i] = terms[i].times(-1);
            }
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                s, fabric.metrics.Metric._Impl.minAtStore(s, terms).times(-1));
        }
        
        /**
   * Cache of DerivedMetrics using this Metric as a term (helps to break up
   * interning cache to avoid one big map.
   */
        public abstract boolean getUsePreset();
        
        public abstract double getPresetR();
        
        public abstract double getPresetB();
        
        public abstract double getPresetV();
        
        public abstract double getPresetN();
        
        /**
   * @param store
   *            the Store that holds this {@link Metric}
   */
        public fabric.metrics.Metric fabric$metrics$Metric$() {
            fabric.lang.security.Label lbl =
              fabric.lang.security.LabelUtil._Impl.noComponents();
            fabric.worker.Store s = $getStore();
            this.set$weakStats(
                   (fabric.lang.arrays.doubleArray)
                     new fabric.lang.arrays.doubleArray._Impl(
                       s).fabric$lang$arrays$doubleArray$(lbl, lbl.confPolicy(),
                                                          4).$getProxy());
            fabric$metrics$util$AbstractSubject$();
            return (fabric.metrics.Metric) this.$getProxy();
        }
        
        /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the current value of the {@link Metric}.
   */
        public abstract double value(boolean useWeakCache);
        
        /** @return the number of samples of the {@link Metric}. */
        public double samples() { return samples(false); }
        
        /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the number of samples of the {@link Metric}.
   */
        public abstract double samples(boolean useWeakCache);
        
        /** @return the estimated velocity of the {@link Metric}. */
        public double velocity() { return velocity(false); }
        
        /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the estimated velocity of the {@link Metric}.
   */
        public abstract double velocity(boolean useWeakCache);
        
        /** @return the estimated noise of the {@link Metric}. */
        public double noise() { return noise(false); }
        
        /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the estimated velocity of the {@link Metric}.
   */
        public abstract double noise(boolean useWeakCache);
        
        /**
   * Used to construct and enforce {@link Contract}s bounding this
   * {@link Metric}s value.
   * <p>
   * Implementations of this method should use "weak" estimates of value,
   * velocity, and noise to avoid contention issues. The internal
   * implementation is expected to have refreshed these estimates recently
   * prior to the call.
   *
   * @param bound
   *            a {@link Bound} that the returned policy enforces.
   * @param useWeakCache
   *            flag indicating if weakly consistent cached values should be
   *            used
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          double rate, double base, boolean useWeakCache,
          final fabric.worker.Store s) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                      new fabric.metrics.contracts.enforcement.DirectPolicy.
                        _Impl(s).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$DirectPolicy$(
                (fabric.metrics.Metric) this.$getProxy(), rate, base);
        }
        
        /**
   * Used to construct and enforce {@link Contract}s bounding this
   * {@link Metric}s value.
   * <p>
   * Implementations of this method should use "weak" estimates of value,
   * velocity, and noise to avoid contention issues. The internal
   * implementation is expected to have refreshed these estimates recently
   * prior to the call.
   *
   * @param bound
   *            a {@link Bound} that the returned policy enforces.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          double rate, double base, final fabric.worker.Store s) {
            return policy(rate, base, false, s);
        }
        
        public fabric.lang.arrays.doubleArray get$weakStats() {
            return this.weakStats;
        }
        
        public fabric.lang.arrays.doubleArray set$weakStats(
          fabric.lang.arrays.doubleArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.weakStats = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.arrays.doubleArray weakStats;
        
        /**
   * Update the current "weak" estimates to be used by {@link #policy(Bound)}
   */
        public void refreshWeakEstimates() { refreshLocally(); }
        
        public void refreshWeakEstimates_remote(
          fabric.lang.security.Principal caller) {
            refreshWeakEstimates();
        }
        
        /**
   * Utility to allow for running updates as close to the metric as possible.
   *
   * Ugh, public because we don't allow remote calls for non public methods.
   */
        public void refreshLocally() {
            fabric.metrics.Metric._Impl.static_refreshLocally(
                                          (fabric.metrics.Metric)
                                            this.$getProxy());
        }
        
        private static void static_refreshLocally(fabric.metrics.Metric tmp) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.get$weakStats().set(0, tmp.computeValue(true));
                tmp.get$weakStats().set(1, tmp.computeVelocity(true));
                tmp.get$weakStats().set(2, tmp.computeNoise(true));
                tmp.get$weakStats().set(3, tmp.computeSamples(true));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm173 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled176 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff174 = 1;
                    boolean $doBackoff175 = true;
                    boolean $retry170 = true;
                    $label168: for (boolean $commit169 = false; !$commit169; ) {
                        if ($backoffEnabled176) {
                            if ($doBackoff175) {
                                if ($backoff174 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff174);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e171) {
                                            
                                        }
                                    }
                                }
                                if ($backoff174 < 5000) $backoff174 *= 2;
                            }
                            $doBackoff175 = $backoff174 <= 32 || !$doBackoff175;
                        }
                        $commit169 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.get$weakStats().set(0, tmp.computeValue(true));
                            tmp.get$weakStats().set(1,
                                                    tmp.computeVelocity(true));
                            tmp.get$weakStats().set(2, tmp.computeNoise(true));
                            tmp.get$weakStats().set(3,
                                                    tmp.computeSamples(true));
                        }
                        catch (final fabric.worker.RetryException $e171) {
                            $commit169 = false;
                            continue $label168;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e171) {
                            $commit169 = false;
                            fabric.common.TransactionID $currentTid172 =
                              $tm173.getCurrentTid();
                            if ($e171.tid.isDescendantOf($currentTid172))
                                continue $label168;
                            if ($currentTid172.parent != null) {
                                $retry170 = false;
                                throw $e171;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e171) {
                            $commit169 = false;
                            if ($tm173.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid172 =
                              $tm173.getCurrentTid();
                            if ($e171.tid.isDescendantOf($currentTid172)) {
                                $retry170 = true;
                            }
                            else if ($currentTid172.parent != null) {
                                $retry170 = false;
                                throw $e171;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e171) {
                            $commit169 = false;
                            if ($tm173.checkForStaleObjects())
                                continue $label168;
                            $retry170 = false;
                            throw new fabric.worker.AbortException($e171);
                        }
                        finally {
                            if ($commit169) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e171) {
                                    $commit169 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e171) {
                                    $commit169 = false;
                                    fabric.common.TransactionID $currentTid172 =
                                      $tm173.getCurrentTid();
                                    if ($currentTid172 != null) {
                                        if ($e171.tid.equals($currentTid172) ||
                                              !$e171.tid.isDescendantOf(
                                                           $currentTid172)) {
                                            throw $e171;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit169 && $retry170) {
                                {  }
                                continue $label168;
                            }
                        }
                    }
                }
            }
        }
        
        public void refreshLocally_remote(
          fabric.lang.security.Principal caller) {
            refreshLocally();
        }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #value()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakValue() { return (double) this.get$weakStats().get(0); }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #samples()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakSamples() {
            return (double) this.get$weakStats().get(3);
        }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #velocity()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakVelocity() {
            return (double) this.get$weakStats().get(1);
        }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #noise()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakNoise() { return (double) this.get$weakStats().get(2); }
        
        /**
   * @param useWeakCache
   *            Flag to indicate if weak values should be used.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
        public abstract double computeValue(boolean useWeakCache);
        
        /**
   * @param useWeakCache
   *            Flag to indicate if weak values should be used.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
        public abstract double computeSamples(boolean useWeakCache);
        
        /**
   * @param useWeakCache
   *            Flag to indicate if weak values should be used.
   * @return a freshly computed weak velocity for this {@link DerivedMetric}.
   */
        public abstract double computeVelocity(boolean useWeakCache);
        
        /**
   * @param useWeakCache
   *            Flag to indicate if weak values should be used.
   * @return a freshly computed noise for this {@link DerivedMetric}.
   */
        public abstract double computeNoise(boolean useWeakCache);
        
        /**
   * @return true iff all the sampling and transformations on this metric are
   *         stored on a single store.
   */
        public abstract boolean isSingleStore();
        
        /**
   * @param contract
   *            a {@link Contract} to store with this {@link Metric}
   * @throws IllegalArgumentException
   *             if the {@link Contract} doesn't enforce a {@link Bound}
   *             on this {@link Metric}
   */
        public void addContract(fabric.metrics.contracts.Contract contract) {  }
        
        /**
   * @param bound
   *        the {@link Bound} that the contract will enforce on this
   *        {@link Metric}
   * @return a {@link Contract} asserting this metric satisfies the
   *       given {@link Bound}.
   */
        public fabric.metrics.contracts.Contract createContract(double rate,
                                                                double base,
                                                                long time) {
            double[] normalized =
              fabric.metrics.contracts.Bound._Impl.createBound(rate, base,
                                                               time);
            return ((fabric.metrics.contracts.MetricContract)
                      new fabric.metrics.contracts.MetricContract._Impl(
                        this.$getStore()).$getProxy()).
              fabric$metrics$contracts$MetricContract$((fabric.metrics.Metric)
                                                         this.$getProxy(),
                                                       normalized[0],
                                                       normalized[1]);
        }
        
        /**
   * @param time
   *        the time we're searching for {@link Contract}s for this
   *        {@link Metric}
   * @return a {@link Set} of {@link Contract}s that are currently
   *       enforced for this {@link Metric}
   */
        public int compareTo(java.lang.Object that) {
            if (!(fabric.lang.Object._Proxy.
                    $getProxy(that) instanceof fabric.metrics.Metric)) return 0;
            fabric.metrics.Metric
              other =
              (fabric.metrics.Metric)
                fabric.lang.Object._Proxy.
                $getProxy(fabric.lang.WrappedJavaInlineable.$wrap(that));
            int thisHash = hashCode();
            int thatHash =
              ((java.lang.Comparable)
                 fabric.lang.WrappedJavaInlineable.$unwrap(other)).hashCode();
            if (thisHash == thatHash) {
                if (other.equals((fabric.metrics.Metric) this.$getProxy())) {
                    return 0;
                }
                else {
                    return toString().
                      compareTo(
                        ((java.lang.Comparable)
                           fabric.lang.WrappedJavaInlineable.$unwrap(other)).
                            toString());
                }
            }
            return thisHash - thatHash;
        }
        
        /**
   * Utility for removing a contract if it's no longer observing and is now
   * stale.
   *
   * @param contract
   *            a {@link Contract} to stop storing with this
   *            {@link Metric} (if it is now invalid).
   * @throws IllegalArgumentException
   *             if contract isn't defined on this {@link Metric}
   */
        private void clearContract(fabric.metrics.contracts.Contract contract) {
            
        }
        
        /**
   * {@inheritDoc}
   *
   * If the {@link Observer} is a {@link Contract} and is now
   * {@link Contract#stale()}, this clears it out from the Metric's stored
   * contract set.
   */
        public void removeObserver(fabric.metrics.util.Observer obs) {
            super.removeObserver(obs);
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        obs)) instanceof fabric.metrics.contracts.Contract) {
                fabric.metrics.contracts.Contract mc =
                  (fabric.metrics.contracts.Contract)
                    fabric.lang.Object._Proxy.$getProxy(obs);
                ((fabric.metrics.Metric._Impl) this.fetch()).clearContract(mc);
            }
        }
        
        /**
   * @param s
   *            the {@link Store} we're looking for the given {@link Metric}
   *            on
   * @param m
   *        the transformed metric we're looking up
   * @return the existing equivalent {@link DerivedMetric} tracked by this
   *       {@link Store}, if one exists. Otherwise, starts tracking
   *       <code>m</code> and returns it.
   */
        public static fabric.metrics.DerivedMetric findDerivedMetric(
          final fabric.worker.Store s, fabric.metrics.DerivedMetric m) {
            return m;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.Metric._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.weakStats, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.weakStats =
              (fabric.lang.arrays.doubleArray)
                $readRef(fabric.lang.arrays.doubleArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.Metric._Impl src = (fabric.metrics.Metric._Impl)
                                                other;
            this.weakStats = src.weakStats;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.Metric._Static {
            public _Proxy(fabric.metrics.Metric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.Metric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  Metric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.Metric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.Metric._Static._Impl.class);
                $instance = (fabric.metrics.Metric._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.Metric._Static {
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
                return new fabric.metrics.Metric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 70, -101, 74, -109,
    -100, -66, -83, -19, -99, -12, 19, 87, -126, -66, -121, -20, -58, -20, 55,
    -122, -126, -100, 32, 5, 98, 92, -31, 43, 19, 89, 51, 18 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520096235000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
