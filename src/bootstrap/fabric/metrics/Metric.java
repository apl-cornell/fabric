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
import fabric.metrics.contracts.MetricEqualityContract;
import fabric.metrics.contracts.enforcement.DirectEqualityPolicy;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.metrics.util.RunningMetricStats;
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
    public fabric.metrics.contracts.Contract getEqualityContract(double value);
    
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
    public fabric.metrics.contracts.Contract getThresholdContract(double rate,
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
    public fabric.metrics.contracts.Contract getThresholdContract(double rate, double base);
    
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
    public long samples();
    
    /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the number of samples of the {@link Metric}.
   */
    public abstract long samples(boolean useWeakCache);
    
    /** @return the time of the last update of the {@link Metric}. */
    public long lastUpdate();
    
    /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the time of the last update of the {@link Metric}.
   */
    public abstract long lastUpdate(boolean useWeakCache);
    
    /** @return the current updateInterval estimate for updates of {@link Metric}. */
    public double updateInterval();
    
    /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the updateInterval estimated for updates of the {@link Metric}.
   */
    public abstract double updateInterval(boolean useWeakCache);
    
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
   * @param value
   *            the value we're asserting equality with.
   * @param useWeakCache
   *            flag indicating if weakly consistent cached values should be
   *            used
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      equalityPolicy(double value, boolean useWeakCache, final fabric.worker.Store s);
    
    /**
   * Used to construct and enforce {@link Contract}s bounding this
   * {@link Metric}s value.
   * <p>
   * Implementations of this method should use "weak" estimates of value,
   * velocity, and noise to avoid contention issues. The internal
   * implementation is expected to have refreshed these estimates recently
   * prior to the call.
   *
   * @param value
   *            the value we're asserting equality with.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      equalityPolicy(double value, final fabric.worker.Store s);
    
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
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      thresholdPolicy(
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
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      thresholdPolicy(double rate, double base, final fabric.worker.Store s);
    
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
    public long weakSamples();
    
    /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #lastUpdate()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
    public long weakLastUpdate();
    
    /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #updateInterval()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
    public double weakUpdateInterval();
    
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
    public abstract long computeSamples(boolean useWeakCache);
    
    /**
   * @param useWeakCache
   *            Flag to indicate if weak values should be used.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
    public abstract long computeLastUpdate(boolean useWeakCache);
    
    /**
   * @param useWeakCache
   *            Flag to indicate if weak values should be used.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
    public abstract double computeUpdateInterval(boolean useWeakCache);
    
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
    public fabric.metrics.contracts.Contract createThresholdContract(
      double rate, double base, long time);
    
    /**
   * @param bound
   *        the {@link Bound} that the contract will enforce on this
   *        {@link Metric}
   * @return a {@link Contract} asserting this metric satisfies the
   *       given {@link Bound}.
   */
    public fabric.metrics.contracts.Contract createEqualityContract(double value);
    
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
        
        public fabric.metrics.contracts.Contract getEqualityContract(
          double arg1) {
            return ((fabric.metrics.Metric) fetch()).getEqualityContract(arg1);
        }
        
        public fabric.metrics.contracts.Contract getThresholdContract(
          double arg1, double arg2, long arg3) {
            return ((fabric.metrics.Metric) fetch()).getThresholdContract(arg1,
                                                                          arg2,
                                                                          arg3);
        }
        
        public fabric.metrics.contracts.Contract getThresholdContract(
          double arg1, double arg2) {
            return ((fabric.metrics.Metric) fetch()).getThresholdContract(arg1,
                                                                          arg2);
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
        
        public long samples() {
            return ((fabric.metrics.Metric) fetch()).samples();
        }
        
        public long samples(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).samples(arg1);
        }
        
        public long lastUpdate() {
            return ((fabric.metrics.Metric) fetch()).lastUpdate();
        }
        
        public long lastUpdate(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).lastUpdate(arg1);
        }
        
        public double updateInterval() {
            return ((fabric.metrics.Metric) fetch()).updateInterval();
        }
        
        public double updateInterval(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).updateInterval(arg1);
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
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          equalityPolicy(double arg1, boolean arg2, fabric.worker.Store arg3) {
            return ((fabric.metrics.Metric) fetch()).equalityPolicy(arg1, arg2,
                                                                    arg3);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          equalityPolicy(double arg1, fabric.worker.Store arg2) {
            return ((fabric.metrics.Metric) fetch()).equalityPolicy(arg1, arg2);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          thresholdPolicy(double arg1, double arg2, boolean arg3,
                          fabric.worker.Store arg4) {
            return ((fabric.metrics.Metric) fetch()).thresholdPolicy(arg1, arg2,
                                                                     arg3,
                                                                     arg4);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          thresholdPolicy(double arg1, double arg2, fabric.worker.Store arg3) {
            return ((fabric.metrics.Metric) fetch()).thresholdPolicy(arg1, arg2,
                                                                     arg3);
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
        
        public long weakSamples() {
            return ((fabric.metrics.Metric) fetch()).weakSamples();
        }
        
        public long weakLastUpdate() {
            return ((fabric.metrics.Metric) fetch()).weakLastUpdate();
        }
        
        public double weakUpdateInterval() {
            return ((fabric.metrics.Metric) fetch()).weakUpdateInterval();
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
        
        public long computeSamples(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).computeSamples(arg1);
        }
        
        public long computeLastUpdate(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).computeLastUpdate(arg1);
        }
        
        public double computeUpdateInterval(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).computeUpdateInterval(
                                                       arg1);
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
        
        public fabric.metrics.contracts.Contract createThresholdContract(
          double arg1, double arg2, long arg3) {
            return ((fabric.metrics.Metric) fetch()).createThresholdContract(
                                                       arg1, arg2, arg3);
        }
        
        public fabric.metrics.contracts.Contract createEqualityContract(
          double arg1) {
            return ((fabric.metrics.Metric) fetch()).createEqualityContract(
                                                       arg1);
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
                    fabric.metrics.DerivedMetric val$var108 = val;
                    fabric.worker.transaction.TransactionManager $tm114 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled117 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff115 = 1;
                    boolean $doBackoff116 = true;
                    boolean $retry111 = true;
                    $label109: for (boolean $commit110 = false; !$commit110; ) {
                        if ($backoffEnabled117) {
                            if ($doBackoff116) {
                                if ($backoff115 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff115);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e112) {
                                            
                                        }
                                    }
                                }
                                if ($backoff115 < 5000) $backoff115 *= 2;
                            }
                            $doBackoff116 = $backoff115 <= 32 || !$doBackoff116;
                        }
                        $commit110 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                scalar, tmp);
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
                                    fabric.common.TransactionID $currentTid113 =
                                      $tm114.getCurrentTid();
                                    if ($currentTid113 != null) {
                                        if ($e112.tid.equals($currentTid113) ||
                                              !$e112.tid.isDescendantOf(
                                                           $currentTid113)) {
                                            throw $e112;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit110 && $retry111) {
                                { val = val$var108; }
                                continue $label109;
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
                    fabric.metrics.DerivedMetric val$var118 = val;
                    fabric.worker.transaction.TransactionManager $tm124 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled127 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff125 = 1;
                    boolean $doBackoff126 = true;
                    boolean $retry121 = true;
                    $label119: for (boolean $commit120 = false; !$commit120; ) {
                        if ($backoffEnabled127) {
                            if ($doBackoff126) {
                                if ($backoff125 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff125);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e122) {
                                            
                                        }
                                    }
                                }
                                if ($backoff125 < 5000) $backoff125 *= 2;
                            }
                            $doBackoff126 = $backoff125 <= 32 || !$doBackoff126;
                        }
                        $commit120 = true;
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
                                    fabric.common.TransactionID $currentTid123 =
                                      $tm124.getCurrentTid();
                                    if ($currentTid123 != null) {
                                        if ($e122.tid.equals($currentTid123) ||
                                              !$e122.tid.isDescendantOf(
                                                           $currentTid123)) {
                                            throw $e122;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit120 && $retry121) {
                                { val = val$var118; }
                                continue $label119;
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
                        fabric.metrics.DerivedMetric val$var128 = val;
                        fabric.worker.transaction.TransactionManager $tm134 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled137 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff135 = 1;
                        boolean $doBackoff136 = true;
                        boolean $retry131 = true;
                        $label129: for (boolean $commit130 = false; !$commit130;
                                        ) {
                            if ($backoffEnabled137) {
                                if ($doBackoff136) {
                                    if ($backoff135 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff135);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e132) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff135 < 5000) $backoff135 *= 2;
                                }
                                $doBackoff136 = $backoff135 <= 32 ||
                                                  !$doBackoff136;
                            }
                            $commit130 = true;
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
                                        fabric.common.TransactionID
                                          $currentTid133 =
                                          $tm134.getCurrentTid();
                                        if ($currentTid133 != null) {
                                            if ($e132.tid.equals(
                                                            $currentTid133) ||
                                                  !$e132.tid.
                                                  isDescendantOf(
                                                    $currentTid133)) {
                                                throw $e132;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit130 && $retry131) {
                                    { val = val$var128; }
                                    continue $label129;
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
                        fabric.metrics.DerivedMetric val$var138 = val;
                        fabric.worker.transaction.TransactionManager $tm144 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled147 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff145 = 1;
                        boolean $doBackoff146 = true;
                        boolean $retry141 = true;
                        $label139: for (boolean $commit140 = false; !$commit140;
                                        ) {
                            if ($backoffEnabled147) {
                                if ($doBackoff146) {
                                    if ($backoff145 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff145);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e142) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff145 < 5000) $backoff145 *= 2;
                                }
                                $doBackoff146 = $backoff145 <= 32 ||
                                                  !$doBackoff146;
                            }
                            $commit140 = true;
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
                                        fabric.common.TransactionID
                                          $currentTid143 =
                                          $tm144.getCurrentTid();
                                        if ($currentTid143 != null) {
                                            if ($e142.tid.equals(
                                                            $currentTid143) ||
                                                  !$e142.tid.
                                                  isDescendantOf(
                                                    $currentTid143)) {
                                                throw $e142;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit140 && $retry141) {
                                    { val = val$var138; }
                                    continue $label139;
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
        public fabric.metrics.contracts.Contract getEqualityContract(
          double value) {
            return fabric.metrics.Metric._Impl.static_getEqualityContract(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy(), value);
        }
        
        private static fabric.metrics.contracts.Contract
          static_getEqualityContract(fabric.metrics.Metric tmp, double value) {
            fabric.metrics.contracts.Contract mc = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                mc = tmp.createEqualityContract(value);
            }
            else {
                {
                    fabric.metrics.contracts.Contract mc$var148 = mc;
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
                        try { mc = tmp.createEqualityContract(value); }
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
                                { mc = mc$var148; }
                                continue $label149;
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
        public fabric.metrics.contracts.Contract getThresholdContract(
          double rate, double base, long time) {
            return fabric.metrics.Metric._Impl.static_getThresholdContract(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy(), rate, base,
                                                 time);
        }
        
        private static fabric.metrics.contracts.Contract
          static_getThresholdContract(fabric.metrics.Metric tmp, double rate,
                                      double base, long time) {
            fabric.metrics.contracts.Contract mc = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                mc = tmp.createThresholdContract(rate, base, time);
            }
            else {
                {
                    fabric.metrics.contracts.Contract mc$var158 = mc;
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
                            mc = tmp.createThresholdContract(rate, base, time);
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
                                { mc = mc$var158; }
                                continue $label159;
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
        public fabric.metrics.contracts.Contract getThresholdContract(
          double rate, double base) {
            return getThresholdContract(rate, base, 0);
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
                    fabric.metrics.DerivedMetric val$var168 = val;
                    fabric.worker.transaction.TransactionManager $tm174 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled177 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff175 = 1;
                    boolean $doBackoff176 = true;
                    boolean $retry171 = true;
                    $label169: for (boolean $commit170 = false; !$commit170; ) {
                        if ($backoffEnabled177) {
                            if ($doBackoff176) {
                                if ($backoff175 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff175);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e172) {
                                            
                                        }
                                    }
                                }
                                if ($backoff175 < 5000) $backoff175 *= 2;
                            }
                            $doBackoff176 = $backoff175 <= 32 || !$doBackoff176;
                        }
                        $commit170 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                a, term);
                        }
                        catch (final fabric.worker.RetryException $e172) {
                            $commit170 = false;
                            continue $label169;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e172) {
                            $commit170 = false;
                            fabric.common.TransactionID $currentTid173 =
                              $tm174.getCurrentTid();
                            if ($e172.tid.isDescendantOf($currentTid173))
                                continue $label169;
                            if ($currentTid173.parent != null) {
                                $retry171 = false;
                                throw $e172;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e172) {
                            $commit170 = false;
                            if ($tm174.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid173 =
                              $tm174.getCurrentTid();
                            if ($e172.tid.isDescendantOf($currentTid173)) {
                                $retry171 = true;
                            }
                            else if ($currentTid173.parent != null) {
                                $retry171 = false;
                                throw $e172;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e172) {
                            $commit170 = false;
                            if ($tm174.checkForStaleObjects())
                                continue $label169;
                            $retry171 = false;
                            throw new fabric.worker.AbortException($e172);
                        }
                        finally {
                            if ($commit170) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e172) {
                                    $commit170 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e172) {
                                    $commit170 = false;
                                    fabric.common.TransactionID $currentTid173 =
                                      $tm174.getCurrentTid();
                                    if ($currentTid173 != null) {
                                        if ($e172.tid.equals($currentTid173) ||
                                              !$e172.tid.isDescendantOf(
                                                           $currentTid173)) {
                                            throw $e172;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit170 && $retry171) {
                                { val = val$var168; }
                                continue $label169;
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
                    fabric.metrics.DerivedMetric val$var178 = val;
                    fabric.worker.transaction.TransactionManager $tm184 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled187 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff185 = 1;
                    boolean $doBackoff186 = true;
                    boolean $retry181 = true;
                    $label179: for (boolean $commit180 = false; !$commit180; ) {
                        if ($backoffEnabled187) {
                            if ($doBackoff186) {
                                if ($backoff185 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff185);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e182) {
                                            
                                        }
                                    }
                                }
                                if ($backoff185 < 5000) $backoff185 *= 2;
                            }
                            $doBackoff186 = $backoff185 <= 32 || !$doBackoff186;
                        }
                        $commit180 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(terms);
                        }
                        catch (final fabric.worker.RetryException $e182) {
                            $commit180 = false;
                            continue $label179;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e182) {
                            $commit180 = false;
                            fabric.common.TransactionID $currentTid183 =
                              $tm184.getCurrentTid();
                            if ($e182.tid.isDescendantOf($currentTid183))
                                continue $label179;
                            if ($currentTid183.parent != null) {
                                $retry181 = false;
                                throw $e182;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e182) {
                            $commit180 = false;
                            if ($tm184.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid183 =
                              $tm184.getCurrentTid();
                            if ($e182.tid.isDescendantOf($currentTid183)) {
                                $retry181 = true;
                            }
                            else if ($currentTid183.parent != null) {
                                $retry181 = false;
                                throw $e182;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e182) {
                            $commit180 = false;
                            if ($tm184.checkForStaleObjects())
                                continue $label179;
                            $retry181 = false;
                            throw new fabric.worker.AbortException($e182);
                        }
                        finally {
                            if ($commit180) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e182) {
                                    $commit180 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e182) {
                                    $commit180 = false;
                                    fabric.common.TransactionID $currentTid183 =
                                      $tm184.getCurrentTid();
                                    if ($currentTid183 != null) {
                                        if ($e182.tid.equals($currentTid183) ||
                                              !$e182.tid.isDescendantOf(
                                                           $currentTid183)) {
                                            throw $e182;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit180 && $retry181) {
                                { val = val$var178; }
                                continue $label179;
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
                    fabric.metrics.DerivedMetric val$var188 = val;
                    fabric.worker.transaction.TransactionManager $tm194 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled197 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff195 = 1;
                    boolean $doBackoff196 = true;
                    boolean $retry191 = true;
                    $label189: for (boolean $commit190 = false; !$commit190; ) {
                        if ($backoffEnabled197) {
                            if ($doBackoff196) {
                                if ($backoff195 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff195);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e192) {
                                            
                                        }
                                    }
                                }
                                if ($backoff195 < 5000) $backoff195 *= 2;
                            }
                            $doBackoff196 = $backoff195 <= 32 || !$doBackoff196;
                        }
                        $commit190 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(terms);
                        }
                        catch (final fabric.worker.RetryException $e192) {
                            $commit190 = false;
                            continue $label189;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e192) {
                            $commit190 = false;
                            fabric.common.TransactionID $currentTid193 =
                              $tm194.getCurrentTid();
                            if ($e192.tid.isDescendantOf($currentTid193))
                                continue $label189;
                            if ($currentTid193.parent != null) {
                                $retry191 = false;
                                throw $e192;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e192) {
                            $commit190 = false;
                            if ($tm194.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid193 =
                              $tm194.getCurrentTid();
                            if ($e192.tid.isDescendantOf($currentTid193)) {
                                $retry191 = true;
                            }
                            else if ($currentTid193.parent != null) {
                                $retry191 = false;
                                throw $e192;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e192) {
                            $commit190 = false;
                            if ($tm194.checkForStaleObjects())
                                continue $label189;
                            $retry191 = false;
                            throw new fabric.worker.AbortException($e192);
                        }
                        finally {
                            if ($commit190) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e192) {
                                    $commit190 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e192) {
                                    $commit190 = false;
                                    fabric.common.TransactionID $currentTid193 =
                                      $tm194.getCurrentTid();
                                    if ($currentTid193 != null) {
                                        if ($e192.tid.equals($currentTid193) ||
                                              !$e192.tid.isDescendantOf(
                                                           $currentTid193)) {
                                            throw $e192;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit190 && $retry191) {
                                { val = val$var188; }
                                continue $label189;
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
                                                          6).$getProxy());
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
        public long samples() { return samples(false); }
        
        /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the number of samples of the {@link Metric}.
   */
        public abstract long samples(boolean useWeakCache);
        
        /** @return the time of the last update of the {@link Metric}. */
        public long lastUpdate() { return lastUpdate(false); }
        
        /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the time of the last update of the {@link Metric}.
   */
        public abstract long lastUpdate(boolean useWeakCache);
        
        /** @return the current updateInterval estimate for updates of {@link Metric}. */
        public double updateInterval() { return updateInterval(false); }
        
        /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the updateInterval estimated for updates of the {@link Metric}.
   */
        public abstract double updateInterval(boolean useWeakCache);
        
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
   * @param value
   *            the value we're asserting equality with.
   * @param useWeakCache
   *            flag indicating if weakly consistent cached values should be
   *            used
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          equalityPolicy(double value, boolean useWeakCache,
                         final fabric.worker.Store s) {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
                      new fabric.metrics.contracts.enforcement.
                        DirectEqualityPolicy._Impl(s).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$DirectEqualityPolicy$(
                (fabric.metrics.Metric) this.$getProxy(), value);
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
   * @param value
   *            the value we're asserting equality with.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          equalityPolicy(double value, final fabric.worker.Store s) {
            return equalityPolicy(value, false, s);
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
   * @param useWeakCache
   *            flag indicating if weakly consistent cached values should be
   *            used
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base, boolean useWeakCache,
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
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base,
                          final fabric.worker.Store s) {
            return thresholdPolicy(rate, base, false, s);
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
                tmp.get$weakStats().set(4,
                                        java.lang.System.currentTimeMillis());
                tmp.get$weakStats().set(5, tmp.computeUpdateInterval(true));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm203 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled206 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff204 = 1;
                    boolean $doBackoff205 = true;
                    boolean $retry200 = true;
                    $label198: for (boolean $commit199 = false; !$commit199; ) {
                        if ($backoffEnabled206) {
                            if ($doBackoff205) {
                                if ($backoff204 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff204);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e201) {
                                            
                                        }
                                    }
                                }
                                if ($backoff204 < 5000) $backoff204 *= 2;
                            }
                            $doBackoff205 = $backoff204 <= 32 || !$doBackoff205;
                        }
                        $commit199 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.get$weakStats().set(0, tmp.computeValue(true));
                            tmp.get$weakStats().set(1,
                                                    tmp.computeVelocity(true));
                            tmp.get$weakStats().set(2, tmp.computeNoise(true));
                            tmp.get$weakStats().set(3,
                                                    tmp.computeSamples(true));
                            tmp.get$weakStats().
                              set(4, java.lang.System.currentTimeMillis());
                            tmp.get$weakStats().set(
                                                  5,
                                                  tmp.computeUpdateInterval(
                                                        true));
                        }
                        catch (final fabric.worker.RetryException $e201) {
                            $commit199 = false;
                            continue $label198;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e201) {
                            $commit199 = false;
                            fabric.common.TransactionID $currentTid202 =
                              $tm203.getCurrentTid();
                            if ($e201.tid.isDescendantOf($currentTid202))
                                continue $label198;
                            if ($currentTid202.parent != null) {
                                $retry200 = false;
                                throw $e201;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e201) {
                            $commit199 = false;
                            if ($tm203.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid202 =
                              $tm203.getCurrentTid();
                            if ($e201.tid.isDescendantOf($currentTid202)) {
                                $retry200 = true;
                            }
                            else if ($currentTid202.parent != null) {
                                $retry200 = false;
                                throw $e201;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e201) {
                            $commit199 = false;
                            if ($tm203.checkForStaleObjects())
                                continue $label198;
                            $retry200 = false;
                            throw new fabric.worker.AbortException($e201);
                        }
                        finally {
                            if ($commit199) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e201) {
                                    $commit199 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e201) {
                                    $commit199 = false;
                                    fabric.common.TransactionID $currentTid202 =
                                      $tm203.getCurrentTid();
                                    if ($currentTid202 != null) {
                                        if ($e201.tid.equals($currentTid202) ||
                                              !$e201.tid.isDescendantOf(
                                                           $currentTid202)) {
                                            throw $e201;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit199 && $retry200) {
                                {  }
                                continue $label198;
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
        public long weakSamples() {
            return (long) (double) this.get$weakStats().get(3);
        }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #lastUpdate()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public long weakLastUpdate() {
            return (long) (double) this.get$weakStats().get(4);
        }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #updateInterval()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakUpdateInterval() {
            return (double) this.get$weakStats().get(5);
        }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #velocity()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakVelocity() {
            return fabric.metrics.util.RunningMetricStats._Impl.
              updatedVelocity((double) this.get$weakStats().get(1),
                              (double) this.get$weakStats().get(5),
                              (long) (double) this.get$weakStats().get(3),
                              (long) (double) this.get$weakStats().get(4),
                              java.lang.System.currentTimeMillis());
        }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #noise()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakNoise() {
            return fabric.metrics.util.RunningMetricStats._Impl.
              updatedNoise((double) this.get$weakStats().get(1),
                           (double) this.get$weakStats().get(2),
                           (double) this.get$weakStats().get(5),
                           (long) (double) this.get$weakStats().get(3),
                           (long) (double) this.get$weakStats().get(4),
                           java.lang.System.currentTimeMillis());
        }
        
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
        public abstract long computeSamples(boolean useWeakCache);
        
        /**
   * @param useWeakCache
   *            Flag to indicate if weak values should be used.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
        public abstract long computeLastUpdate(boolean useWeakCache);
        
        /**
   * @param useWeakCache
   *            Flag to indicate if weak values should be used.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
        public abstract double computeUpdateInterval(boolean useWeakCache);
        
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
        public fabric.metrics.contracts.Contract createThresholdContract(
          double rate, double base, long time) {
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
   * @param bound
   *        the {@link Bound} that the contract will enforce on this
   *        {@link Metric}
   * @return a {@link Contract} asserting this metric satisfies the
   *       given {@link Bound}.
   */
        public fabric.metrics.contracts.Contract createEqualityContract(
          double value) {
            return ((fabric.metrics.contracts.MetricEqualityContract)
                      new fabric.metrics.contracts.MetricEqualityContract._Impl(
                        this.$getStore()).$getProxy()).
              fabric$metrics$contracts$MetricEqualityContract$(
                (fabric.metrics.Metric) this.$getProxy(), value);
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
    
    public static final byte[] $classHash = new byte[] { -118, -117, 55, 88,
    -103, -86, 13, 12, 124, 26, 65, -69, 65, -111, -42, 53, 99, -109, -46, 40,
    54, -45, -45, -42, 18, -38, 84, 117, 14, -127, -60, 124 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1523310434000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
