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
    
    public static final byte[] $classHash = new byte[] { 14, -23, -101, -43, 11,
    -104, 41, -112, -26, -19, 2, -128, 52, 103, -33, -23, 42, 2, 85, 5, -105,
    36, 74, -10, -23, -97, 104, -110, -122, 123, 86, 35 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524081841000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
