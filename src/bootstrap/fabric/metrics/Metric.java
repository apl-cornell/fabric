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
    
    public double get$cachedValue();
    
    public double set$cachedValue(double val);
    
    public double postInc$cachedValue();
    
    public double postDec$cachedValue();
    
    public double get$cachedVelocity();
    
    public double set$cachedVelocity(double val);
    
    public double postInc$cachedVelocity();
    
    public double postDec$cachedVelocity();
    
    public double get$cachedNoise();
    
    public double set$cachedNoise(double val);
    
    public double postInc$cachedNoise();
    
    public double postDec$cachedNoise();
    
    public long get$cachedSamples();
    
    public long set$cachedSamples(long val);
    
    public long postInc$cachedSamples();
    
    public long postDec$cachedSamples();
    
    public long get$cachedLastUpdate();
    
    public long set$cachedLastUpdate(long val);
    
    public long postInc$cachedLastUpdate();
    
    public long postDec$cachedLastUpdate();
    
    public double get$cachedUpdateInterval();
    
    public double set$cachedUpdateInterval(double val);
    
    public double postInc$cachedUpdateInterval();
    
    public double postDec$cachedUpdateInterval();
    
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
        public double get$cachedValue() {
            return ((fabric.metrics.Metric._Impl) fetch()).get$cachedValue();
        }
        
        public double set$cachedValue(double val) {
            return ((fabric.metrics.Metric._Impl) fetch()).set$cachedValue(val);
        }
        
        public double postInc$cachedValue() {
            return ((fabric.metrics.Metric._Impl) fetch()).postInc$cachedValue(
                                                             );
        }
        
        public double postDec$cachedValue() {
            return ((fabric.metrics.Metric._Impl) fetch()).postDec$cachedValue(
                                                             );
        }
        
        public double get$cachedVelocity() {
            return ((fabric.metrics.Metric._Impl) fetch()).get$cachedVelocity();
        }
        
        public double set$cachedVelocity(double val) {
            return ((fabric.metrics.Metric._Impl) fetch()).set$cachedVelocity(
                                                             val);
        }
        
        public double postInc$cachedVelocity() {
            return ((fabric.metrics.Metric._Impl) fetch()).
              postInc$cachedVelocity();
        }
        
        public double postDec$cachedVelocity() {
            return ((fabric.metrics.Metric._Impl) fetch()).
              postDec$cachedVelocity();
        }
        
        public double get$cachedNoise() {
            return ((fabric.metrics.Metric._Impl) fetch()).get$cachedNoise();
        }
        
        public double set$cachedNoise(double val) {
            return ((fabric.metrics.Metric._Impl) fetch()).set$cachedNoise(val);
        }
        
        public double postInc$cachedNoise() {
            return ((fabric.metrics.Metric._Impl) fetch()).postInc$cachedNoise(
                                                             );
        }
        
        public double postDec$cachedNoise() {
            return ((fabric.metrics.Metric._Impl) fetch()).postDec$cachedNoise(
                                                             );
        }
        
        public long get$cachedSamples() {
            return ((fabric.metrics.Metric._Impl) fetch()).get$cachedSamples();
        }
        
        public long set$cachedSamples(long val) {
            return ((fabric.metrics.Metric._Impl) fetch()).set$cachedSamples(
                                                             val);
        }
        
        public long postInc$cachedSamples() {
            return ((fabric.metrics.Metric._Impl) fetch()).
              postInc$cachedSamples();
        }
        
        public long postDec$cachedSamples() {
            return ((fabric.metrics.Metric._Impl) fetch()).
              postDec$cachedSamples();
        }
        
        public long get$cachedLastUpdate() {
            return ((fabric.metrics.Metric._Impl) fetch()).get$cachedLastUpdate(
                                                             );
        }
        
        public long set$cachedLastUpdate(long val) {
            return ((fabric.metrics.Metric._Impl) fetch()).set$cachedLastUpdate(
                                                             val);
        }
        
        public long postInc$cachedLastUpdate() {
            return ((fabric.metrics.Metric._Impl) fetch()).
              postInc$cachedLastUpdate();
        }
        
        public long postDec$cachedLastUpdate() {
            return ((fabric.metrics.Metric._Impl) fetch()).
              postDec$cachedLastUpdate();
        }
        
        public double get$cachedUpdateInterval() {
            return ((fabric.metrics.Metric._Impl) fetch()).
              get$cachedUpdateInterval();
        }
        
        public double set$cachedUpdateInterval(double val) {
            return ((fabric.metrics.Metric._Impl) fetch()).
              set$cachedUpdateInterval(val);
        }
        
        public double postInc$cachedUpdateInterval() {
            return ((fabric.metrics.Metric._Impl) fetch()).
              postInc$cachedUpdateInterval();
        }
        
        public double postDec$cachedUpdateInterval() {
            return ((fabric.metrics.Metric._Impl) fetch()).
              postDec$cachedUpdateInterval();
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
                    fabric.metrics.DerivedMetric val$var126 = val;
                    fabric.worker.transaction.TransactionManager $tm132 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled135 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff133 = 1;
                    boolean $doBackoff134 = true;
                    boolean $retry129 = true;
                    $label127: for (boolean $commit128 = false; !$commit128; ) {
                        if ($backoffEnabled135) {
                            if ($doBackoff134) {
                                if ($backoff133 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff133);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e130) {
                                            
                                        }
                                    }
                                }
                                if ($backoff133 < 5000) $backoff133 *= 2;
                            }
                            $doBackoff134 = $backoff133 <= 32 || !$doBackoff134;
                        }
                        $commit128 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                scalar, tmp);
                        }
                        catch (final fabric.worker.RetryException $e130) {
                            $commit128 = false;
                            continue $label127;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e130) {
                            $commit128 = false;
                            fabric.common.TransactionID $currentTid131 =
                              $tm132.getCurrentTid();
                            if ($e130.tid.isDescendantOf($currentTid131))
                                continue $label127;
                            if ($currentTid131.parent != null) {
                                $retry129 = false;
                                throw $e130;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e130) {
                            $commit128 = false;
                            if ($tm132.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid131 =
                              $tm132.getCurrentTid();
                            if ($e130.tid.isDescendantOf($currentTid131)) {
                                $retry129 = true;
                            }
                            else if ($currentTid131.parent != null) {
                                $retry129 = false;
                                throw $e130;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e130) {
                            $commit128 = false;
                            if ($tm132.checkForStaleObjects())
                                continue $label127;
                            $retry129 = false;
                            throw new fabric.worker.AbortException($e130);
                        }
                        finally {
                            if ($commit128) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e130) {
                                    $commit128 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e130) {
                                    $commit128 = false;
                                    fabric.common.TransactionID $currentTid131 =
                                      $tm132.getCurrentTid();
                                    if ($currentTid131 != null) {
                                        if ($e130.tid.equals($currentTid131) ||
                                              !$e130.tid.isDescendantOf(
                                                           $currentTid131)) {
                                            throw $e130;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit128 && $retry129) {
                                { val = val$var126; }
                                continue $label127;
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
                    fabric.metrics.DerivedMetric val$var136 = val;
                    fabric.worker.transaction.TransactionManager $tm142 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled145 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff143 = 1;
                    boolean $doBackoff144 = true;
                    boolean $retry139 = true;
                    $label137: for (boolean $commit138 = false; !$commit138; ) {
                        if ($backoffEnabled145) {
                            if ($doBackoff144) {
                                if ($backoff143 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff143);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e140) {
                                            
                                        }
                                    }
                                }
                                if ($backoff143 < 5000) $backoff143 *= 2;
                            }
                            $doBackoff144 = $backoff143 <= 32 || !$doBackoff144;
                        }
                        $commit138 = true;
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
                        catch (final fabric.worker.RetryException $e140) {
                            $commit138 = false;
                            continue $label137;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e140) {
                            $commit138 = false;
                            fabric.common.TransactionID $currentTid141 =
                              $tm142.getCurrentTid();
                            if ($e140.tid.isDescendantOf($currentTid141))
                                continue $label137;
                            if ($currentTid141.parent != null) {
                                $retry139 = false;
                                throw $e140;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e140) {
                            $commit138 = false;
                            if ($tm142.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid141 =
                              $tm142.getCurrentTid();
                            if ($e140.tid.isDescendantOf($currentTid141)) {
                                $retry139 = true;
                            }
                            else if ($currentTid141.parent != null) {
                                $retry139 = false;
                                throw $e140;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e140) {
                            $commit138 = false;
                            if ($tm142.checkForStaleObjects())
                                continue $label137;
                            $retry139 = false;
                            throw new fabric.worker.AbortException($e140);
                        }
                        finally {
                            if ($commit138) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e140) {
                                    $commit138 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e140) {
                                    $commit138 = false;
                                    fabric.common.TransactionID $currentTid141 =
                                      $tm142.getCurrentTid();
                                    if ($currentTid141 != null) {
                                        if ($e140.tid.equals($currentTid141) ||
                                              !$e140.tid.isDescendantOf(
                                                           $currentTid141)) {
                                            throw $e140;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit138 && $retry139) {
                                { val = val$var136; }
                                continue $label137;
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
                        fabric.metrics.DerivedMetric val$var146 = val;
                        fabric.worker.transaction.TransactionManager $tm152 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled155 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff153 = 1;
                        boolean $doBackoff154 = true;
                        boolean $retry149 = true;
                        $label147: for (boolean $commit148 = false; !$commit148;
                                        ) {
                            if ($backoffEnabled155) {
                                if ($doBackoff154) {
                                    if ($backoff153 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff153);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e150) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff153 < 5000) $backoff153 *= 2;
                                }
                                $doBackoff154 = $backoff153 <= 32 ||
                                                  !$doBackoff154;
                            }
                            $commit148 = true;
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
                            catch (final fabric.worker.RetryException $e150) {
                                $commit148 = false;
                                continue $label147;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e150) {
                                $commit148 = false;
                                fabric.common.TransactionID $currentTid151 =
                                  $tm152.getCurrentTid();
                                if ($e150.tid.isDescendantOf($currentTid151))
                                    continue $label147;
                                if ($currentTid151.parent != null) {
                                    $retry149 = false;
                                    throw $e150;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e150) {
                                $commit148 = false;
                                if ($tm152.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid151 =
                                  $tm152.getCurrentTid();
                                if ($e150.tid.isDescendantOf($currentTid151)) {
                                    $retry149 = true;
                                }
                                else if ($currentTid151.parent != null) {
                                    $retry149 = false;
                                    throw $e150;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e150) {
                                $commit148 = false;
                                if ($tm152.checkForStaleObjects())
                                    continue $label147;
                                $retry149 = false;
                                throw new fabric.worker.AbortException($e150);
                            }
                            finally {
                                if ($commit148) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e150) {
                                        $commit148 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e150) {
                                        $commit148 = false;
                                        fabric.common.TransactionID
                                          $currentTid151 =
                                          $tm152.getCurrentTid();
                                        if ($currentTid151 != null) {
                                            if ($e150.tid.equals(
                                                            $currentTid151) ||
                                                  !$e150.tid.
                                                  isDescendantOf(
                                                    $currentTid151)) {
                                                throw $e150;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit148 && $retry149) {
                                    { val = val$var146; }
                                    continue $label147;
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
                        fabric.metrics.DerivedMetric val$var156 = val;
                        fabric.worker.transaction.TransactionManager $tm162 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled165 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff163 = 1;
                        boolean $doBackoff164 = true;
                        boolean $retry159 = true;
                        $label157: for (boolean $commit158 = false; !$commit158;
                                        ) {
                            if ($backoffEnabled165) {
                                if ($doBackoff164) {
                                    if ($backoff163 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff163);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e160) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff163 < 5000) $backoff163 *= 2;
                                }
                                $doBackoff164 = $backoff163 <= 32 ||
                                                  !$doBackoff164;
                            }
                            $commit158 = true;
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
                            catch (final fabric.worker.RetryException $e160) {
                                $commit158 = false;
                                continue $label157;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e160) {
                                $commit158 = false;
                                fabric.common.TransactionID $currentTid161 =
                                  $tm162.getCurrentTid();
                                if ($e160.tid.isDescendantOf($currentTid161))
                                    continue $label157;
                                if ($currentTid161.parent != null) {
                                    $retry159 = false;
                                    throw $e160;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e160) {
                                $commit158 = false;
                                if ($tm162.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid161 =
                                  $tm162.getCurrentTid();
                                if ($e160.tid.isDescendantOf($currentTid161)) {
                                    $retry159 = true;
                                }
                                else if ($currentTid161.parent != null) {
                                    $retry159 = false;
                                    throw $e160;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e160) {
                                $commit158 = false;
                                if ($tm162.checkForStaleObjects())
                                    continue $label157;
                                $retry159 = false;
                                throw new fabric.worker.AbortException($e160);
                            }
                            finally {
                                if ($commit158) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e160) {
                                        $commit158 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e160) {
                                        $commit158 = false;
                                        fabric.common.TransactionID
                                          $currentTid161 =
                                          $tm162.getCurrentTid();
                                        if ($currentTid161 != null) {
                                            if ($e160.tid.equals(
                                                            $currentTid161) ||
                                                  !$e160.tid.
                                                  isDescendantOf(
                                                    $currentTid161)) {
                                                throw $e160;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit158 && $retry159) {
                                    { val = val$var156; }
                                    continue $label157;
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
                    fabric.metrics.contracts.Contract mc$var166 = mc;
                    fabric.worker.transaction.TransactionManager $tm172 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled175 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff173 = 1;
                    boolean $doBackoff174 = true;
                    boolean $retry169 = true;
                    $label167: for (boolean $commit168 = false; !$commit168; ) {
                        if ($backoffEnabled175) {
                            if ($doBackoff174) {
                                if ($backoff173 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff173);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e170) {
                                            
                                        }
                                    }
                                }
                                if ($backoff173 < 5000) $backoff173 *= 2;
                            }
                            $doBackoff174 = $backoff173 <= 32 || !$doBackoff174;
                        }
                        $commit168 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { mc = tmp.createEqualityContract(value); }
                        catch (final fabric.worker.RetryException $e170) {
                            $commit168 = false;
                            continue $label167;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e170) {
                            $commit168 = false;
                            fabric.common.TransactionID $currentTid171 =
                              $tm172.getCurrentTid();
                            if ($e170.tid.isDescendantOf($currentTid171))
                                continue $label167;
                            if ($currentTid171.parent != null) {
                                $retry169 = false;
                                throw $e170;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e170) {
                            $commit168 = false;
                            if ($tm172.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid171 =
                              $tm172.getCurrentTid();
                            if ($e170.tid.isDescendantOf($currentTid171)) {
                                $retry169 = true;
                            }
                            else if ($currentTid171.parent != null) {
                                $retry169 = false;
                                throw $e170;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e170) {
                            $commit168 = false;
                            if ($tm172.checkForStaleObjects())
                                continue $label167;
                            $retry169 = false;
                            throw new fabric.worker.AbortException($e170);
                        }
                        finally {
                            if ($commit168) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e170) {
                                    $commit168 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e170) {
                                    $commit168 = false;
                                    fabric.common.TransactionID $currentTid171 =
                                      $tm172.getCurrentTid();
                                    if ($currentTid171 != null) {
                                        if ($e170.tid.equals($currentTid171) ||
                                              !$e170.tid.isDescendantOf(
                                                           $currentTid171)) {
                                            throw $e170;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit168 && $retry169) {
                                { mc = mc$var166; }
                                continue $label167;
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
                    fabric.metrics.contracts.Contract mc$var176 = mc;
                    fabric.worker.transaction.TransactionManager $tm182 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled185 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff183 = 1;
                    boolean $doBackoff184 = true;
                    boolean $retry179 = true;
                    $label177: for (boolean $commit178 = false; !$commit178; ) {
                        if ($backoffEnabled185) {
                            if ($doBackoff184) {
                                if ($backoff183 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff183);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e180) {
                                            
                                        }
                                    }
                                }
                                if ($backoff183 < 5000) $backoff183 *= 2;
                            }
                            $doBackoff184 = $backoff183 <= 32 || !$doBackoff184;
                        }
                        $commit178 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            mc = tmp.createThresholdContract(rate, base, time);
                        }
                        catch (final fabric.worker.RetryException $e180) {
                            $commit178 = false;
                            continue $label177;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e180) {
                            $commit178 = false;
                            fabric.common.TransactionID $currentTid181 =
                              $tm182.getCurrentTid();
                            if ($e180.tid.isDescendantOf($currentTid181))
                                continue $label177;
                            if ($currentTid181.parent != null) {
                                $retry179 = false;
                                throw $e180;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e180) {
                            $commit178 = false;
                            if ($tm182.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid181 =
                              $tm182.getCurrentTid();
                            if ($e180.tid.isDescendantOf($currentTid181)) {
                                $retry179 = true;
                            }
                            else if ($currentTid181.parent != null) {
                                $retry179 = false;
                                throw $e180;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e180) {
                            $commit178 = false;
                            if ($tm182.checkForStaleObjects())
                                continue $label177;
                            $retry179 = false;
                            throw new fabric.worker.AbortException($e180);
                        }
                        finally {
                            if ($commit178) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e180) {
                                    $commit178 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e180) {
                                    $commit178 = false;
                                    fabric.common.TransactionID $currentTid181 =
                                      $tm182.getCurrentTid();
                                    if ($currentTid181 != null) {
                                        if ($e180.tid.equals($currentTid181) ||
                                              !$e180.tid.isDescendantOf(
                                                           $currentTid181)) {
                                            throw $e180;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit178 && $retry179) {
                                { mc = mc$var176; }
                                continue $label177;
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
                    fabric.metrics.DerivedMetric val$var186 = val;
                    fabric.worker.transaction.TransactionManager $tm192 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled195 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff193 = 1;
                    boolean $doBackoff194 = true;
                    boolean $retry189 = true;
                    $label187: for (boolean $commit188 = false; !$commit188; ) {
                        if ($backoffEnabled195) {
                            if ($doBackoff194) {
                                if ($backoff193 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff193);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e190) {
                                            
                                        }
                                    }
                                }
                                if ($backoff193 < 5000) $backoff193 *= 2;
                            }
                            $doBackoff194 = $backoff193 <= 32 || !$doBackoff194;
                        }
                        $commit188 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                a, term);
                        }
                        catch (final fabric.worker.RetryException $e190) {
                            $commit188 = false;
                            continue $label187;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e190) {
                            $commit188 = false;
                            fabric.common.TransactionID $currentTid191 =
                              $tm192.getCurrentTid();
                            if ($e190.tid.isDescendantOf($currentTid191))
                                continue $label187;
                            if ($currentTid191.parent != null) {
                                $retry189 = false;
                                throw $e190;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e190) {
                            $commit188 = false;
                            if ($tm192.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid191 =
                              $tm192.getCurrentTid();
                            if ($e190.tid.isDescendantOf($currentTid191)) {
                                $retry189 = true;
                            }
                            else if ($currentTid191.parent != null) {
                                $retry189 = false;
                                throw $e190;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e190) {
                            $commit188 = false;
                            if ($tm192.checkForStaleObjects())
                                continue $label187;
                            $retry189 = false;
                            throw new fabric.worker.AbortException($e190);
                        }
                        finally {
                            if ($commit188) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e190) {
                                    $commit188 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e190) {
                                    $commit188 = false;
                                    fabric.common.TransactionID $currentTid191 =
                                      $tm192.getCurrentTid();
                                    if ($currentTid191 != null) {
                                        if ($e190.tid.equals($currentTid191) ||
                                              !$e190.tid.isDescendantOf(
                                                           $currentTid191)) {
                                            throw $e190;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit188 && $retry189) {
                                { val = val$var186; }
                                continue $label187;
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
                    fabric.metrics.DerivedMetric val$var196 = val;
                    fabric.worker.transaction.TransactionManager $tm202 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled205 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff203 = 1;
                    boolean $doBackoff204 = true;
                    boolean $retry199 = true;
                    $label197: for (boolean $commit198 = false; !$commit198; ) {
                        if ($backoffEnabled205) {
                            if ($doBackoff204) {
                                if ($backoff203 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff203);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e200) {
                                            
                                        }
                                    }
                                }
                                if ($backoff203 < 5000) $backoff203 *= 2;
                            }
                            $doBackoff204 = $backoff203 <= 32 || !$doBackoff204;
                        }
                        $commit198 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(terms);
                        }
                        catch (final fabric.worker.RetryException $e200) {
                            $commit198 = false;
                            continue $label197;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e200) {
                            $commit198 = false;
                            fabric.common.TransactionID $currentTid201 =
                              $tm202.getCurrentTid();
                            if ($e200.tid.isDescendantOf($currentTid201))
                                continue $label197;
                            if ($currentTid201.parent != null) {
                                $retry199 = false;
                                throw $e200;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e200) {
                            $commit198 = false;
                            if ($tm202.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid201 =
                              $tm202.getCurrentTid();
                            if ($e200.tid.isDescendantOf($currentTid201)) {
                                $retry199 = true;
                            }
                            else if ($currentTid201.parent != null) {
                                $retry199 = false;
                                throw $e200;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e200) {
                            $commit198 = false;
                            if ($tm202.checkForStaleObjects())
                                continue $label197;
                            $retry199 = false;
                            throw new fabric.worker.AbortException($e200);
                        }
                        finally {
                            if ($commit198) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e200) {
                                    $commit198 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e200) {
                                    $commit198 = false;
                                    fabric.common.TransactionID $currentTid201 =
                                      $tm202.getCurrentTid();
                                    if ($currentTid201 != null) {
                                        if ($e200.tid.equals($currentTid201) ||
                                              !$e200.tid.isDescendantOf(
                                                           $currentTid201)) {
                                            throw $e200;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit198 && $retry199) {
                                { val = val$var196; }
                                continue $label197;
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
                    fabric.metrics.DerivedMetric val$var206 = val;
                    fabric.worker.transaction.TransactionManager $tm212 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled215 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff213 = 1;
                    boolean $doBackoff214 = true;
                    boolean $retry209 = true;
                    $label207: for (boolean $commit208 = false; !$commit208; ) {
                        if ($backoffEnabled215) {
                            if ($doBackoff214) {
                                if ($backoff213 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff213);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e210) {
                                            
                                        }
                                    }
                                }
                                if ($backoff213 < 5000) $backoff213 *= 2;
                            }
                            $doBackoff214 = $backoff213 <= 32 || !$doBackoff214;
                        }
                        $commit208 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(terms);
                        }
                        catch (final fabric.worker.RetryException $e210) {
                            $commit208 = false;
                            continue $label207;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e210) {
                            $commit208 = false;
                            fabric.common.TransactionID $currentTid211 =
                              $tm212.getCurrentTid();
                            if ($e210.tid.isDescendantOf($currentTid211))
                                continue $label207;
                            if ($currentTid211.parent != null) {
                                $retry209 = false;
                                throw $e210;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e210) {
                            $commit208 = false;
                            if ($tm212.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid211 =
                              $tm212.getCurrentTid();
                            if ($e210.tid.isDescendantOf($currentTid211)) {
                                $retry209 = true;
                            }
                            else if ($currentTid211.parent != null) {
                                $retry209 = false;
                                throw $e210;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e210) {
                            $commit208 = false;
                            if ($tm212.checkForStaleObjects())
                                continue $label207;
                            $retry209 = false;
                            throw new fabric.worker.AbortException($e210);
                        }
                        finally {
                            if ($commit208) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e210) {
                                    $commit208 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e210) {
                                    $commit208 = false;
                                    fabric.common.TransactionID $currentTid211 =
                                      $tm212.getCurrentTid();
                                    if ($currentTid211 != null) {
                                        if ($e210.tid.equals($currentTid211) ||
                                              !$e210.tid.isDescendantOf(
                                                           $currentTid211)) {
                                            throw $e210;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit208 && $retry209) {
                                { val = val$var206; }
                                continue $label207;
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
        
        public double get$cachedValue() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.cachedValue;
        }
        
        public double set$cachedValue(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.cachedValue = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$cachedValue() {
            double tmp = this.get$cachedValue();
            this.set$cachedValue((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$cachedValue() {
            double tmp = this.get$cachedValue();
            this.set$cachedValue((double) (tmp - 1));
            return tmp;
        }
        
        protected double cachedValue;
        
        public double get$cachedVelocity() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.cachedVelocity;
        }
        
        public double set$cachedVelocity(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.cachedVelocity = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$cachedVelocity() {
            double tmp = this.get$cachedVelocity();
            this.set$cachedVelocity((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$cachedVelocity() {
            double tmp = this.get$cachedVelocity();
            this.set$cachedVelocity((double) (tmp - 1));
            return tmp;
        }
        
        protected double cachedVelocity;
        
        public double get$cachedNoise() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.cachedNoise;
        }
        
        public double set$cachedNoise(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.cachedNoise = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$cachedNoise() {
            double tmp = this.get$cachedNoise();
            this.set$cachedNoise((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$cachedNoise() {
            double tmp = this.get$cachedNoise();
            this.set$cachedNoise((double) (tmp - 1));
            return tmp;
        }
        
        protected double cachedNoise;
        
        public long get$cachedSamples() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.cachedSamples;
        }
        
        public long set$cachedSamples(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.cachedSamples = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$cachedSamples() {
            long tmp = this.get$cachedSamples();
            this.set$cachedSamples((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$cachedSamples() {
            long tmp = this.get$cachedSamples();
            this.set$cachedSamples((long) (tmp - 1));
            return tmp;
        }
        
        protected long cachedSamples;
        
        public long get$cachedLastUpdate() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.cachedLastUpdate;
        }
        
        public long set$cachedLastUpdate(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.cachedLastUpdate = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$cachedLastUpdate() {
            long tmp = this.get$cachedLastUpdate();
            this.set$cachedLastUpdate((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$cachedLastUpdate() {
            long tmp = this.get$cachedLastUpdate();
            this.set$cachedLastUpdate((long) (tmp - 1));
            return tmp;
        }
        
        protected long cachedLastUpdate;
        
        public double get$cachedUpdateInterval() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.cachedUpdateInterval;
        }
        
        public double set$cachedUpdateInterval(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.cachedUpdateInterval = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$cachedUpdateInterval() {
            double tmp = this.get$cachedUpdateInterval();
            this.set$cachedUpdateInterval((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$cachedUpdateInterval() {
            double tmp = this.get$cachedUpdateInterval();
            this.set$cachedUpdateInterval((double) (tmp - 1));
            return tmp;
        }
        
        protected double cachedUpdateInterval;
        
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
                tmp.set$cachedValue((double) tmp.computeValue(true));
                tmp.set$cachedVelocity((double) tmp.computeVelocity(true));
                tmp.set$cachedNoise((double) tmp.computeNoise(true));
                tmp.set$cachedSamples((long) tmp.computeSamples(true));
                tmp.set$cachedLastUpdate(
                      (long) java.lang.System.currentTimeMillis());
                tmp.set$cachedUpdateInterval((double)
                                               tmp.computeUpdateInterval(true));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm221 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled224 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff222 = 1;
                    boolean $doBackoff223 = true;
                    boolean $retry218 = true;
                    $label216: for (boolean $commit217 = false; !$commit217; ) {
                        if ($backoffEnabled224) {
                            if ($doBackoff223) {
                                if ($backoff222 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff222);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e219) {
                                            
                                        }
                                    }
                                }
                                if ($backoff222 < 5000) $backoff222 *= 2;
                            }
                            $doBackoff223 = $backoff222 <= 32 || !$doBackoff223;
                        }
                        $commit217 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$cachedValue((double)
                                                  tmp.computeValue(true));
                            tmp.set$cachedVelocity((double)
                                                     tmp.computeVelocity(true));
                            tmp.set$cachedNoise((double)
                                                  tmp.computeNoise(true));
                            tmp.set$cachedSamples((long)
                                                    tmp.computeSamples(true));
                            tmp.set$cachedLastUpdate(
                                  (long) java.lang.System.currentTimeMillis());
                            tmp.set$cachedUpdateInterval(
                                  (double) tmp.computeUpdateInterval(true));
                        }
                        catch (final fabric.worker.RetryException $e219) {
                            $commit217 = false;
                            continue $label216;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e219) {
                            $commit217 = false;
                            fabric.common.TransactionID $currentTid220 =
                              $tm221.getCurrentTid();
                            if ($e219.tid.isDescendantOf($currentTid220))
                                continue $label216;
                            if ($currentTid220.parent != null) {
                                $retry218 = false;
                                throw $e219;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e219) {
                            $commit217 = false;
                            if ($tm221.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid220 =
                              $tm221.getCurrentTid();
                            if ($e219.tid.isDescendantOf($currentTid220)) {
                                $retry218 = true;
                            }
                            else if ($currentTid220.parent != null) {
                                $retry218 = false;
                                throw $e219;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e219) {
                            $commit217 = false;
                            if ($tm221.checkForStaleObjects())
                                continue $label216;
                            $retry218 = false;
                            throw new fabric.worker.AbortException($e219);
                        }
                        finally {
                            if ($commit217) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e219) {
                                    $commit217 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e219) {
                                    $commit217 = false;
                                    fabric.common.TransactionID $currentTid220 =
                                      $tm221.getCurrentTid();
                                    if ($currentTid220 != null) {
                                        if ($e219.tid.equals($currentTid220) ||
                                              !$e219.tid.isDescendantOf(
                                                           $currentTid220)) {
                                            throw $e219;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit217 && $retry218) {
                                {  }
                                continue $label216;
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
        public double weakValue() { return this.get$cachedValue(); }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #samples()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public long weakSamples() { return this.get$cachedSamples(); }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #lastUpdate()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public long weakLastUpdate() { return this.get$cachedLastUpdate(); }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #updateInterval()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakUpdateInterval() {
            return this.get$cachedUpdateInterval();
        }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #velocity()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakVelocity() {
            return fabric.metrics.util.RunningMetricStats._Impl.
              updatedVelocity(this.get$cachedVelocity(),
                              this.get$cachedUpdateInterval(),
                              this.get$cachedSamples(),
                              this.get$cachedLastUpdate(),
                              java.lang.System.currentTimeMillis());
        }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #noise()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakNoise() {
            return fabric.metrics.util.RunningMetricStats._Impl.
              updatedNoise(this.get$cachedVelocity(), this.get$cachedNoise(),
                           this.get$cachedUpdateInterval(),
                           this.get$cachedSamples(),
                           this.get$cachedLastUpdate(),
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
            out.writeDouble(this.cachedValue);
            out.writeDouble(this.cachedVelocity);
            out.writeDouble(this.cachedNoise);
            out.writeLong(this.cachedSamples);
            out.writeLong(this.cachedLastUpdate);
            out.writeDouble(this.cachedUpdateInterval);
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
            this.cachedValue = in.readDouble();
            this.cachedVelocity = in.readDouble();
            this.cachedNoise = in.readDouble();
            this.cachedSamples = in.readLong();
            this.cachedLastUpdate = in.readLong();
            this.cachedUpdateInterval = in.readDouble();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.Metric._Impl src = (fabric.metrics.Metric._Impl)
                                                other;
            this.cachedValue = src.cachedValue;
            this.cachedVelocity = src.cachedVelocity;
            this.cachedNoise = src.cachedNoise;
            this.cachedSamples = src.cachedSamples;
            this.cachedLastUpdate = src.cachedLastUpdate;
            this.cachedUpdateInterval = src.cachedUpdateInterval;
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
                return new fabric.metrics.Metric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 115, -40, 82, 93, 112,
    -84, 58, 67, 56, -106, -83, 39, -56, 78, 101, -33, 16, -44, -35, -28, 82,
    112, -10, 12, 18, -60, 24, 118, 103, 58, -38, -68 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525097266000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
