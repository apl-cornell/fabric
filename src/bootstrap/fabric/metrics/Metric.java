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
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.RunningMetricStats;
import fabric.worker.metrics.MetricUpdate;
import fabric.worker.metrics.proxies.ProxyMap;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.TreatySet;
import fabric.worker.metrics.treaties.enforcement.DirectPolicy;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.metrics.treaties.statements.EqualityStatement;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import fabric.lang.security.LabelUtil;
import java.util.logging.Level;
import java.util.Iterator;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;
import fabric.common.Logging;
import fabric.common.util.Pair;
import fabric.common.util.LongSet;

/**
 * Abstract class with base implementation of some {@link Metric} methods.
 */
public interface Metric
  extends java.lang.Comparable, fabric.metrics.util.Observer,
          fabric.metrics.util.AbstractSubject
{
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
   *            the rate parameter for the bound on the resulting
   *            {@link MetricTreaty}
   * @param base
   *            the base parameter for the bound on the resulting
   *            {@link MetricTreaty}
   * @param time
   *            the startTime parameter of the bound on the resulting
   *            {@link MetricTreaty}
   * @return a {@link MetricTreaty} which enforces that the {@link Metric}
   *         satisfies a bound with the given parameters at the given time. If
   *         such a {@link MetricTreaty} already exists, it is returned,
   *         otherwise a new one is created and returned (unactivated).
   */
    public fabric.worker.metrics.treaties.MetricTreaty getEqualityTreaty(double value);
    
    /**
   * @param rate
   *            the rate parameter for the bound on the resulting
   *            {@link MetricTreaty}
   * @param base
   *            the base parameter for the bound on the resulting
   *            {@link MetricTreaty}
   * @param time
   *            the startTime parameter of the bound on the resulting
   *            {@link MetricTreaty}
   * @return a {@link MetricTreaty} which enforces that the {@link Metric}
   *         satisfies a bound with the given parameters at the given
   *         time. If such a {@link MetricTreaty} already exists, it is
   *         returned, otherwise a new one is created and returned
   *         (unactivated).
   *
   */
    public fabric.worker.metrics.treaties.MetricTreaty getThresholdTreaty(
      double rate, double base, long time);
    
    /**
   * @param rate
   *            the rate parameter for the bound on the resulting
   *            {@link MetricTreaty}
   * @param base
   *            the base parameter for the bound on the resulting
   *            {@link MetricTreaty}
   * @return a {@link MetricTreaty} which enforces that the {@link Metric}
   *         satisfies a bound with the given parameters at the
   *         current time. If such a {@link MetricTreaty} already exists, it
   *         is returned, otherwise a new one is created and returned
   *         (unactivated).
   */
    public fabric.worker.metrics.treaties.MetricTreaty getThresholdTreaty(
      double rate, double base);
    
    /**
   * Cache of DerivedMetrics using this Metric as a term (helps to break up
   * interning cache to avoid one big map.
   */
    public abstract boolean getUsePreset();
    
    public abstract double getPresetR();
    
    public abstract double getPresetB();
    
    public abstract double getPresetV();
    
    public abstract double getPresetN();
    
    public fabric.worker.metrics.proxies.ProxyMap get$proxies();
    
    public fabric.worker.metrics.proxies.ProxyMap set$proxies(
      fabric.worker.metrics.proxies.ProxyMap val);
    
    /**
   * @param store
   *            the Store that holds this {@link Metric}
   */
    public fabric.metrics.Metric fabric$metrics$Metric$();
    
    /**
   * Get a proxy metric for this metric (returns this if the store is the same).
   */
    public fabric.metrics.Metric getProxy(fabric.worker.Store s);
    
    /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return the current value of the {@link Metric}.
   */
    public abstract double value(fabric.worker.metrics.StatsMap weakStats);
    
    /** @return the number of samples of the {@link Metric}. */
    public long samples();
    
    /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return the number of samples of the {@link Metric}.
   */
    public abstract long samples(fabric.worker.metrics.StatsMap weakStats);
    
    /** @return the time of the last update of the {@link Metric}. */
    public long lastUpdate();
    
    /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return the time of the last update of the {@link Metric}.
   */
    public abstract long lastUpdate(fabric.worker.metrics.StatsMap weakStats);
    
    /** @return the current updateInterval estimate for updates of {@link Metric}. */
    public double updateInterval();
    
    /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return the updateInterval estimated for updates of the {@link Metric}.
   */
    public abstract double updateInterval(
      fabric.worker.metrics.StatsMap weakStats);
    
    /** @return the estimated velocity of the {@link Metric}. */
    public double velocity();
    
    /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return the estimated velocity of the {@link Metric}.
   */
    public abstract double velocity(fabric.worker.metrics.StatsMap weakStats);
    
    /** @return the estimated noise of the {@link Metric}. */
    public double noise();
    
    /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return the estimated velocity of the {@link Metric}.
   */
    public abstract double noise(fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * Used to construct and enforce {@link MetricTreaty}s bounding this
   * {@link Metric}s value.
   * <p>
   * Implementations of this method should use "weak" estimates of value,
   * velocity, and noise to avoid contention issues. The internal
   * implementation is expected to have refreshed these estimates recently
   * prior to the call.
   *
   * @param value
   *            the value we're asserting equality with.
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      equalityPolicy(
      double value, fabric.worker.metrics.StatsMap weakStats, final fabric.worker.Store s);
    
    /**
   * Used to construct and enforce {@link MetricTreaty}s bounding this
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
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      equalityPolicy(double value, final fabric.worker.Store s);
    
    /**
   * Used to construct and enforce {@link MetricTreaty}s bounding this
   * {@link Metric}s value.
   * <p>
   * Implementations of this method should use "weak" estimates of value,
   * velocity, and noise to avoid contention issues. The internal
   * implementation is expected to have refreshed these estimates recently
   * prior to the call.
   *
   * @param bound
   *            a bound that the returned policy enforces.
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      thresholdPolicy(
      double rate,
      double base, fabric.worker.metrics.StatsMap weakStats, final fabric.worker.Store s);
    
    /**
   * Used to construct and enforce {@link MetricTreaty}s bounding this
   * {@link Metric}s value.
   * <p>
   * Implementations of this method should use "weak" estimates of value,
   * velocity, and noise to avoid contention issues. The internal
   * implementation is expected to have refreshed these estimates recently
   * prior to the call.
   *
   * @param bound
   *            a bound that the returned policy enforces.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      thresholdPolicy(double rate, double base, final fabric.worker.Store s);
    
    /**
   * Update the current "weak" estimates to be used by {@link #policy(Bound)}
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   */
    public fabric.worker.metrics.StatsMap refreshWeakEstimates(
      fabric.worker.metrics.StatsMap weakStats);
    
    public fabric.worker.metrics.StatsMap refreshWeakEstimates_remote(
      fabric.lang.security.Principal caller, fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * Utility to allow for running updates as close to the metric as possible.
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   *
   * Ugh, public because we don't allow remote calls for non public methods.
   */
    public fabric.worker.metrics.StatsMap refreshLocally(
      fabric.worker.metrics.StatsMap weakStats);
    
    public fabric.worker.metrics.StatsMap refreshLocally_remote(
      fabric.lang.security.Principal caller, fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
    public abstract double computeValue(fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
    public abstract long computeSamples(fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
    public abstract long computeLastUpdate(fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
    public abstract double computeUpdateInterval(fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a freshly computed weak velocity for this {@link DerivedMetric}.
   */
    public abstract double computeVelocity(fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a freshly computed noise for this {@link DerivedMetric}.
   */
    public abstract double computeNoise(fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * @return true iff all the sampling and transformations on this metric are
   *         stored on a single store.
   */
    public abstract boolean isSingleStore();
    
    /**
   * @param bound
   *        the bound that the treaty will enforce on this
   *        {@link Metric}
   * @return a {@link MetricTreaty} asserting this metric satisfies the
   *       given bound.
   */
    public fabric.worker.metrics.treaties.MetricTreaty createThresholdTreaty(
      double rate, double base, long time);
    
    /**
   * @param bound
   *        the bound that the treaty will enforce on this
   *        {@link Metric}
   * @return a {@link MetricTreaty} asserting this metric satisfies the
   *       given bound.
   */
    public fabric.worker.metrics.treaties.MetricTreaty createEqualityTreaty(
      double value);
    
    public int compareTo(java.lang.Object that);
    
    public void refreshTreaty_remote(fabric.lang.security.Principal p,
                                     boolean asyncExtension, long treatyId,
                                     fabric.worker.metrics.StatsMap weakStats);
    
    public void refreshTreaty(boolean asyncExtension, long treatyId,
                              fabric.worker.metrics.StatsMap weakStats);
    
    public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
      boolean includesObserver, fabric.common.util.LongSet treaties);
    
    /**
   * Handle an update where the metric itself is observing.
   */
    public abstract fabric.worker.metrics.ImmutableObserverSet
      handleDirectUpdates();
    
    /**
   * Given a treaty bound that must hold as a post condition, apply the given
   * update if the bound will still hold on this metric afterwards.
   *
   * TODO Bound is static for now, in the future maybe we can support dynamic
   * bounds.
   *
   * TODO: Might be worth allowing multiple bounds to apply relative to the
   * updates in a static method.
   */
    public boolean updateWithPostcondition(double bound,
                                           fabric.worker.metrics.MetricUpdate[] updates);
    
    /**
   * Create and activate a threshold treaty (purely for
   * updateWithPostcondition), possibly across all proxies of this metric.
   *
   * @param proactive
   *        flag indicating if the treaty should also be proactively established
   *        across all proxies of the metric.
   */
    public void createAndActivateThresholdTreaty(double rate, double base,
                                                 long time, boolean proactive);
    
    public static class _Proxy
    extends fabric.metrics.util.AbstractSubject._Proxy
      implements fabric.metrics.Metric {
        public fabric.worker.metrics.proxies.ProxyMap get$proxies() {
            return ((fabric.metrics.Metric._Impl) fetch()).get$proxies();
        }
        
        public fabric.worker.metrics.proxies.ProxyMap set$proxies(
          fabric.worker.metrics.proxies.ProxyMap val) {
            return ((fabric.metrics.Metric._Impl) fetch()).set$proxies(val);
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
        
        public fabric.worker.metrics.treaties.MetricTreaty getEqualityTreaty(
          double arg1) {
            return ((fabric.metrics.Metric) fetch()).getEqualityTreaty(arg1);
        }
        
        public fabric.worker.metrics.treaties.MetricTreaty getThresholdTreaty(
          double arg1, double arg2, long arg3) {
            return ((fabric.metrics.Metric) fetch()).getThresholdTreaty(arg1,
                                                                        arg2,
                                                                        arg3);
        }
        
        public fabric.worker.metrics.treaties.MetricTreaty getThresholdTreaty(
          double arg1, double arg2) {
            return ((fabric.metrics.Metric) fetch()).getThresholdTreaty(arg1,
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
        
        public fabric.metrics.Metric getProxy(fabric.worker.Store arg1) {
            return ((fabric.metrics.Metric) fetch()).getProxy(arg1);
        }
        
        public double value(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).value(arg1);
        }
        
        public long samples() {
            return ((fabric.metrics.Metric) fetch()).samples();
        }
        
        public long samples(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).samples(arg1);
        }
        
        public long lastUpdate() {
            return ((fabric.metrics.Metric) fetch()).lastUpdate();
        }
        
        public long lastUpdate(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).lastUpdate(arg1);
        }
        
        public double updateInterval() {
            return ((fabric.metrics.Metric) fetch()).updateInterval();
        }
        
        public double updateInterval(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).updateInterval(arg1);
        }
        
        public double velocity() {
            return ((fabric.metrics.Metric) fetch()).velocity();
        }
        
        public double velocity(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).velocity(arg1);
        }
        
        public double noise() {
            return ((fabric.metrics.Metric) fetch()).noise();
        }
        
        public double noise(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).noise(arg1);
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          equalityPolicy(double arg1, fabric.worker.metrics.StatsMap arg2,
                         fabric.worker.Store arg3) {
            return ((fabric.metrics.Metric) fetch()).equalityPolicy(arg1, arg2,
                                                                    arg3);
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          equalityPolicy(double arg1, fabric.worker.Store arg2) {
            return ((fabric.metrics.Metric) fetch()).equalityPolicy(arg1, arg2);
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          thresholdPolicy(double arg1, double arg2,
                          fabric.worker.metrics.StatsMap arg3,
                          fabric.worker.Store arg4) {
            return ((fabric.metrics.Metric) fetch()).thresholdPolicy(arg1, arg2,
                                                                     arg3,
                                                                     arg4);
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          thresholdPolicy(double arg1, double arg2, fabric.worker.Store arg3) {
            return ((fabric.metrics.Metric) fetch()).thresholdPolicy(arg1, arg2,
                                                                     arg3);
        }
        
        public fabric.worker.metrics.StatsMap refreshWeakEstimates(
          fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).refreshWeakEstimates(arg1);
        }
        
        public fabric.worker.metrics.StatsMap refreshWeakEstimates_remote(
          fabric.lang.security.Principal arg1,
          fabric.worker.metrics.StatsMap arg2) {
            return ((fabric.metrics.Metric) fetch()).
              refreshWeakEstimates_remote(arg1, arg2);
        }
        
        public static final java.lang.Class[] $paramTypes0 =
          { fabric.worker.metrics.StatsMap.class };
        
        public fabric.worker.metrics.StatsMap refreshWeakEstimates$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1,
          fabric.worker.metrics.StatsMap arg2) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                return refreshWeakEstimates(arg2);
            else
                try {
                    return (fabric.worker.metrics.StatsMap)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               $remoteWorker.
                                   issueRemoteCall(
                                     this, "refreshWeakEstimates", $paramTypes0,
                                     new java.lang.Object[] { arg2 }));
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public fabric.worker.metrics.StatsMap refreshLocally(
          fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).refreshLocally(arg1);
        }
        
        public fabric.worker.metrics.StatsMap refreshLocally_remote(
          fabric.lang.security.Principal arg1,
          fabric.worker.metrics.StatsMap arg2) {
            return ((fabric.metrics.Metric) fetch()).refreshLocally_remote(
                                                       arg1, arg2);
        }
        
        public static final java.lang.Class[] $paramTypes1 =
          { fabric.worker.metrics.StatsMap.class };
        
        public fabric.worker.metrics.StatsMap refreshLocally$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1,
          fabric.worker.metrics.StatsMap arg2) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                return refreshLocally(arg2);
            else
                try {
                    return (fabric.worker.metrics.StatsMap)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               $remoteWorker.
                                   issueRemoteCall(
                                     this, "refreshLocally", $paramTypes1,
                                     new java.lang.Object[] { arg2 }));
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public double computeValue(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).computeValue(arg1);
        }
        
        public long computeSamples(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).computeSamples(arg1);
        }
        
        public long computeLastUpdate(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).computeLastUpdate(arg1);
        }
        
        public double computeUpdateInterval(
          fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).computeUpdateInterval(
                                                       arg1);
        }
        
        public double computeVelocity(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).computeVelocity(arg1);
        }
        
        public double computeNoise(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).computeNoise(arg1);
        }
        
        public boolean isSingleStore() {
            return ((fabric.metrics.Metric) fetch()).isSingleStore();
        }
        
        public fabric.worker.metrics.treaties.MetricTreaty
          createThresholdTreaty(double arg1, double arg2, long arg3) {
            return ((fabric.metrics.Metric) fetch()).createThresholdTreaty(
                                                       arg1, arg2, arg3);
        }
        
        public fabric.worker.metrics.treaties.MetricTreaty createEqualityTreaty(
          double arg1) {
            return ((fabric.metrics.Metric) fetch()).createEqualityTreaty(arg1);
        }
        
        public int compareTo(java.lang.Object arg1) {
            return ((fabric.metrics.Metric) fetch()).compareTo(arg1);
        }
        
        public void refreshTreaty_remote(fabric.lang.security.Principal arg1,
                                         boolean arg2, long arg3,
                                         fabric.worker.metrics.StatsMap arg4) {
            ((fabric.metrics.Metric) fetch()).refreshTreaty_remote(arg1, arg2,
                                                                   arg3, arg4);
        }
        
        public static final java.lang.Class[] $paramTypes2 = { boolean.class,
        long.class, fabric.worker.metrics.StatsMap.class };
        
        public void refreshTreaty$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, boolean arg2, long arg3,
          fabric.worker.metrics.StatsMap arg4) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                refreshTreaty(arg2, arg3, arg4);
            else
                try {
                    $remoteWorker.issueRemoteCall(this,
                                                  "refreshTreaty",
                                                  $paramTypes2,
                                                  new java.lang.Object[] { arg2,
                                                    arg3, arg4 });
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public void refreshTreaty(boolean arg1, long arg2,
                                  fabric.worker.metrics.StatsMap arg3) {
            ((fabric.metrics.Metric) fetch()).refreshTreaty(arg1, arg2, arg3);
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean arg1, fabric.common.util.LongSet arg2) {
            return ((fabric.metrics.Metric) fetch()).handleUpdates(arg1, arg2);
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleDirectUpdates(
          ) {
            return ((fabric.metrics.Metric) fetch()).handleDirectUpdates();
        }
        
        public boolean updateWithPostcondition(
          double arg1, fabric.worker.metrics.MetricUpdate[] arg2) {
            return ((fabric.metrics.Metric) fetch()).updateWithPostcondition(
                                                       arg1, arg2);
        }
        
        public void createAndActivateThresholdTreaty(double arg1, double arg2,
                                                     long arg3, boolean arg4) {
            ((fabric.metrics.Metric) fetch()).createAndActivateThresholdTreaty(
                                                arg1, arg2, arg3, arg4);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return ((fabric.metrics.Metric) fetch()).getLeafSubjects();
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
        public double value() {
            return value(fabric.worker.metrics.StatsMap.emptyStats());
        }
        
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
                    fabric.metrics.DerivedMetric val$var0 = val;
                    fabric.worker.transaction.TransactionManager $tm6 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled9 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff7 = 1;
                    boolean $doBackoff8 = true;
                    boolean $retry3 = true;
                    $label1: for (boolean $commit2 = false; !$commit2; ) {
                        if ($backoffEnabled9) {
                            if ($doBackoff8) {
                                if ($backoff7 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff7);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e4) {
                                            
                                        }
                                    }
                                }
                                if ($backoff7 < 5000) $backoff7 *= 2;
                            }
                            $doBackoff8 = $backoff7 <= 32 || !$doBackoff8;
                        }
                        $commit2 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                scalar, tmp);
                        }
                        catch (final fabric.worker.RetryException $e4) {
                            $commit2 = false;
                            continue $label1;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e4) {
                            $commit2 = false;
                            fabric.common.TransactionID $currentTid5 =
                              $tm6.getCurrentTid();
                            if ($e4.tid.isDescendantOf($currentTid5))
                                continue $label1;
                            if ($currentTid5.parent != null) {
                                $retry3 = false;
                                throw $e4;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e4) {
                            $commit2 = false;
                            if ($tm6.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid5 =
                              $tm6.getCurrentTid();
                            if ($e4.tid.isDescendantOf($currentTid5)) {
                                $retry3 = true;
                            }
                            else if ($currentTid5.parent != null) {
                                $retry3 = false;
                                throw $e4;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e4) {
                            $commit2 = false;
                            if ($tm6.checkForStaleObjects()) continue $label1;
                            $retry3 = false;
                            throw new fabric.worker.AbortException($e4);
                        }
                        finally {
                            if ($commit2) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.AbortException $e4) {
                                    $commit2 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e4) {
                                    $commit2 = false;
                                    fabric.common.TransactionID $currentTid5 =
                                      $tm6.getCurrentTid();
                                    if ($currentTid5 != null) {
                                        if ($e4.tid.equals($currentTid5) ||
                                              !$e4.tid.isDescendantOf(
                                                         $currentTid5)) {
                                            throw $e4;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit2 && $retry3) {
                                { val = val$var0; }
                                continue $label1;
                            }
                        }
                    }
                }
            }
            return val;
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
                    fabric.metrics.DerivedMetric val$var10 = val;
                    fabric.worker.transaction.TransactionManager $tm16 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled19 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff17 = 1;
                    boolean $doBackoff18 = true;
                    boolean $retry13 = true;
                    $label11: for (boolean $commit12 = false; !$commit12; ) {
                        if ($backoffEnabled19) {
                            if ($doBackoff18) {
                                if ($backoff17 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff17);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e14) {
                                            
                                        }
                                    }
                                }
                                if ($backoff17 < 5000) $backoff17 *= 2;
                            }
                            $doBackoff18 = $backoff17 <= 32 || !$doBackoff18;
                        }
                        $commit12 = true;
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
                        catch (final fabric.worker.RetryException $e14) {
                            $commit12 = false;
                            continue $label11;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e14) {
                            $commit12 = false;
                            fabric.common.TransactionID $currentTid15 =
                              $tm16.getCurrentTid();
                            if ($e14.tid.isDescendantOf($currentTid15))
                                continue $label11;
                            if ($currentTid15.parent != null) {
                                $retry13 = false;
                                throw $e14;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e14) {
                            $commit12 = false;
                            if ($tm16.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid15 =
                              $tm16.getCurrentTid();
                            if ($e14.tid.isDescendantOf($currentTid15)) {
                                $retry13 = true;
                            }
                            else if ($currentTid15.parent != null) {
                                $retry13 = false;
                                throw $e14;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e14) {
                            $commit12 = false;
                            if ($tm16.checkForStaleObjects()) continue $label11;
                            $retry13 = false;
                            throw new fabric.worker.AbortException($e14);
                        }
                        finally {
                            if ($commit12) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e14) {
                                    $commit12 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e14) {
                                    $commit12 = false;
                                    fabric.common.TransactionID $currentTid15 =
                                      $tm16.getCurrentTid();
                                    if ($currentTid15 != null) {
                                        if ($e14.tid.equals($currentTid15) ||
                                              !$e14.tid.isDescendantOf(
                                                          $currentTid15)) {
                                            throw $e14;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit12 && $retry13) {
                                { val = val$var10; }
                                continue $label11;
                            }
                        }
                    }
                }
            }
            return val;
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
                        fabric.metrics.DerivedMetric val$var20 = val;
                        fabric.worker.transaction.TransactionManager $tm26 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled29 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff27 = 1;
                        boolean $doBackoff28 = true;
                        boolean $retry23 = true;
                        $label21: for (boolean $commit22 = false; !$commit22;
                                       ) {
                            if ($backoffEnabled29) {
                                if ($doBackoff28) {
                                    if ($backoff27 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff27);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e24) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff27 < 5000) $backoff27 *= 2;
                                }
                                $doBackoff28 = $backoff27 <= 32 ||
                                                 !$doBackoff28;
                            }
                            $commit22 = true;
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
                            catch (final fabric.worker.RetryException $e24) {
                                $commit22 = false;
                                continue $label21;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e24) {
                                $commit22 = false;
                                fabric.common.TransactionID $currentTid25 =
                                  $tm26.getCurrentTid();
                                if ($e24.tid.isDescendantOf($currentTid25))
                                    continue $label21;
                                if ($currentTid25.parent != null) {
                                    $retry23 = false;
                                    throw $e24;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e24) {
                                $commit22 = false;
                                if ($tm26.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid25 =
                                  $tm26.getCurrentTid();
                                if ($e24.tid.isDescendantOf($currentTid25)) {
                                    $retry23 = true;
                                }
                                else if ($currentTid25.parent != null) {
                                    $retry23 = false;
                                    throw $e24;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e24) {
                                $commit22 = false;
                                if ($tm26.checkForStaleObjects())
                                    continue $label21;
                                $retry23 = false;
                                throw new fabric.worker.AbortException($e24);
                            }
                            finally {
                                if ($commit22) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e24) {
                                        $commit22 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e24) {
                                        $commit22 = false;
                                        fabric.common.TransactionID
                                          $currentTid25 = $tm26.getCurrentTid();
                                        if ($currentTid25 != null) {
                                            if ($e24.tid.equals(
                                                           $currentTid25) ||
                                                  !$e24.tid.isDescendantOf(
                                                              $currentTid25)) {
                                                throw $e24;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit22 && $retry23) {
                                    { val = val$var20; }
                                    continue $label21;
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
                        fabric.metrics.DerivedMetric val$var30 = val;
                        fabric.worker.transaction.TransactionManager $tm36 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled39 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff37 = 1;
                        boolean $doBackoff38 = true;
                        boolean $retry33 = true;
                        $label31: for (boolean $commit32 = false; !$commit32;
                                       ) {
                            if ($backoffEnabled39) {
                                if ($doBackoff38) {
                                    if ($backoff37 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff37);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e34) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff37 < 5000) $backoff37 *= 2;
                                }
                                $doBackoff38 = $backoff37 <= 32 ||
                                                 !$doBackoff38;
                            }
                            $commit32 = true;
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
                            catch (final fabric.worker.RetryException $e34) {
                                $commit32 = false;
                                continue $label31;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e34) {
                                $commit32 = false;
                                fabric.common.TransactionID $currentTid35 =
                                  $tm36.getCurrentTid();
                                if ($e34.tid.isDescendantOf($currentTid35))
                                    continue $label31;
                                if ($currentTid35.parent != null) {
                                    $retry33 = false;
                                    throw $e34;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e34) {
                                $commit32 = false;
                                if ($tm36.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid35 =
                                  $tm36.getCurrentTid();
                                if ($e34.tid.isDescendantOf($currentTid35)) {
                                    $retry33 = true;
                                }
                                else if ($currentTid35.parent != null) {
                                    $retry33 = false;
                                    throw $e34;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e34) {
                                $commit32 = false;
                                if ($tm36.checkForStaleObjects())
                                    continue $label31;
                                $retry33 = false;
                                throw new fabric.worker.AbortException($e34);
                            }
                            finally {
                                if ($commit32) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e34) {
                                        $commit32 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e34) {
                                        $commit32 = false;
                                        fabric.common.TransactionID
                                          $currentTid35 = $tm36.getCurrentTid();
                                        if ($currentTid35 != null) {
                                            if ($e34.tid.equals(
                                                           $currentTid35) ||
                                                  !$e34.tid.isDescendantOf(
                                                              $currentTid35)) {
                                                throw $e34;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit32 && $retry33) {
                                    { val = val$var30; }
                                    continue $label31;
                                }
                            }
                        }
                    }
                }
            }
            return val;
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
   *            the rate parameter for the bound on the resulting
   *            {@link MetricTreaty}
   * @param base
   *            the base parameter for the bound on the resulting
   *            {@link MetricTreaty}
   * @param time
   *            the startTime parameter of the bound on the resulting
   *            {@link MetricTreaty}
   * @return a {@link MetricTreaty} which enforces that the {@link Metric}
   *         satisfies a bound with the given parameters at the given time. If
   *         such a {@link MetricTreaty} already exists, it is returned,
   *         otherwise a new one is created and returned (unactivated).
   */
        public fabric.worker.metrics.treaties.MetricTreaty getEqualityTreaty(
          double value) {
            return fabric.metrics.Metric._Impl.static_getEqualityTreaty(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy(), value);
        }
        
        private static fabric.worker.metrics.treaties.MetricTreaty
          static_getEqualityTreaty(fabric.metrics.Metric tmp, double value) {
            fabric.worker.metrics.treaties.MetricTreaty mc = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                mc = tmp.createEqualityTreaty(value);
            }
            else {
                {
                    fabric.worker.metrics.treaties.MetricTreaty mc$var40 = mc;
                    fabric.worker.transaction.TransactionManager $tm46 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled49 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff47 = 1;
                    boolean $doBackoff48 = true;
                    boolean $retry43 = true;
                    $label41: for (boolean $commit42 = false; !$commit42; ) {
                        if ($backoffEnabled49) {
                            if ($doBackoff48) {
                                if ($backoff47 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff47);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e44) {
                                            
                                        }
                                    }
                                }
                                if ($backoff47 < 5000) $backoff47 *= 2;
                            }
                            $doBackoff48 = $backoff47 <= 32 || !$doBackoff48;
                        }
                        $commit42 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { mc = tmp.createEqualityTreaty(value); }
                        catch (final fabric.worker.RetryException $e44) {
                            $commit42 = false;
                            continue $label41;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e44) {
                            $commit42 = false;
                            fabric.common.TransactionID $currentTid45 =
                              $tm46.getCurrentTid();
                            if ($e44.tid.isDescendantOf($currentTid45))
                                continue $label41;
                            if ($currentTid45.parent != null) {
                                $retry43 = false;
                                throw $e44;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e44) {
                            $commit42 = false;
                            if ($tm46.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid45 =
                              $tm46.getCurrentTid();
                            if ($e44.tid.isDescendantOf($currentTid45)) {
                                $retry43 = true;
                            }
                            else if ($currentTid45.parent != null) {
                                $retry43 = false;
                                throw $e44;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e44) {
                            $commit42 = false;
                            if ($tm46.checkForStaleObjects()) continue $label41;
                            $retry43 = false;
                            throw new fabric.worker.AbortException($e44);
                        }
                        finally {
                            if ($commit42) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e44) {
                                    $commit42 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e44) {
                                    $commit42 = false;
                                    fabric.common.TransactionID $currentTid45 =
                                      $tm46.getCurrentTid();
                                    if ($currentTid45 != null) {
                                        if ($e44.tid.equals($currentTid45) ||
                                              !$e44.tid.isDescendantOf(
                                                          $currentTid45)) {
                                            throw $e44;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit42 && $retry43) {
                                { mc = mc$var40; }
                                continue $label41;
                            }
                        }
                    }
                }
            }
            return mc;
        }
        
        /**
   * @param rate
   *            the rate parameter for the bound on the resulting
   *            {@link MetricTreaty}
   * @param base
   *            the base parameter for the bound on the resulting
   *            {@link MetricTreaty}
   * @param time
   *            the startTime parameter of the bound on the resulting
   *            {@link MetricTreaty}
   * @return a {@link MetricTreaty} which enforces that the {@link Metric}
   *         satisfies a bound with the given parameters at the given
   *         time. If such a {@link MetricTreaty} already exists, it is
   *         returned, otherwise a new one is created and returned
   *         (unactivated).
   *
   */
        public fabric.worker.metrics.treaties.MetricTreaty getThresholdTreaty(
          double rate, double base, long time) {
            return fabric.metrics.Metric._Impl.static_getThresholdTreaty(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy(), rate, base,
                                                 time);
        }
        
        private static fabric.worker.metrics.treaties.MetricTreaty
          static_getThresholdTreaty(fabric.metrics.Metric tmp, double rate,
                                    double base, long time) {
            fabric.worker.metrics.treaties.MetricTreaty mc = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                mc = tmp.createThresholdTreaty(rate, base, time);
            }
            else {
                {
                    fabric.worker.metrics.treaties.MetricTreaty mc$var50 = mc;
                    fabric.worker.transaction.TransactionManager $tm56 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled59 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff57 = 1;
                    boolean $doBackoff58 = true;
                    boolean $retry53 = true;
                    $label51: for (boolean $commit52 = false; !$commit52; ) {
                        if ($backoffEnabled59) {
                            if ($doBackoff58) {
                                if ($backoff57 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff57);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e54) {
                                            
                                        }
                                    }
                                }
                                if ($backoff57 < 5000) $backoff57 *= 2;
                            }
                            $doBackoff58 = $backoff57 <= 32 || !$doBackoff58;
                        }
                        $commit52 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            mc = tmp.createThresholdTreaty(rate, base, time);
                        }
                        catch (final fabric.worker.RetryException $e54) {
                            $commit52 = false;
                            continue $label51;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e54) {
                            $commit52 = false;
                            fabric.common.TransactionID $currentTid55 =
                              $tm56.getCurrentTid();
                            if ($e54.tid.isDescendantOf($currentTid55))
                                continue $label51;
                            if ($currentTid55.parent != null) {
                                $retry53 = false;
                                throw $e54;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e54) {
                            $commit52 = false;
                            if ($tm56.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid55 =
                              $tm56.getCurrentTid();
                            if ($e54.tid.isDescendantOf($currentTid55)) {
                                $retry53 = true;
                            }
                            else if ($currentTid55.parent != null) {
                                $retry53 = false;
                                throw $e54;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e54) {
                            $commit52 = false;
                            if ($tm56.checkForStaleObjects()) continue $label51;
                            $retry53 = false;
                            throw new fabric.worker.AbortException($e54);
                        }
                        finally {
                            if ($commit52) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e54) {
                                    $commit52 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e54) {
                                    $commit52 = false;
                                    fabric.common.TransactionID $currentTid55 =
                                      $tm56.getCurrentTid();
                                    if ($currentTid55 != null) {
                                        if ($e54.tid.equals($currentTid55) ||
                                              !$e54.tid.isDescendantOf(
                                                          $currentTid55)) {
                                            throw $e54;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit52 && $retry53) {
                                { mc = mc$var50; }
                                continue $label51;
                            }
                        }
                    }
                }
            }
            return mc;
        }
        
        /**
   * @param rate
   *            the rate parameter for the bound on the resulting
   *            {@link MetricTreaty}
   * @param base
   *            the base parameter for the bound on the resulting
   *            {@link MetricTreaty}
   * @return a {@link MetricTreaty} which enforces that the {@link Metric}
   *         satisfies a bound with the given parameters at the
   *         current time. If such a {@link MetricTreaty} already exists, it
   *         is returned, otherwise a new one is created and returned
   *         (unactivated).
   */
        public fabric.worker.metrics.treaties.MetricTreaty getThresholdTreaty(
          double rate, double base) {
            return getThresholdTreaty(rate, base, 0);
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
            if (term.$getStore().equals(s)) return term.times(a);
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
                    fabric.metrics.DerivedMetric val$var60 = val;
                    fabric.worker.transaction.TransactionManager $tm66 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled69 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff67 = 1;
                    boolean $doBackoff68 = true;
                    boolean $retry63 = true;
                    $label61: for (boolean $commit62 = false; !$commit62; ) {
                        if ($backoffEnabled69) {
                            if ($doBackoff68) {
                                if ($backoff67 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff67);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e64) {
                                            
                                        }
                                    }
                                }
                                if ($backoff67 < 5000) $backoff67 *= 2;
                            }
                            $doBackoff68 = $backoff67 <= 32 || !$doBackoff68;
                        }
                        $commit62 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                a, term);
                        }
                        catch (final fabric.worker.RetryException $e64) {
                            $commit62 = false;
                            continue $label61;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e64) {
                            $commit62 = false;
                            fabric.common.TransactionID $currentTid65 =
                              $tm66.getCurrentTid();
                            if ($e64.tid.isDescendantOf($currentTid65))
                                continue $label61;
                            if ($currentTid65.parent != null) {
                                $retry63 = false;
                                throw $e64;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e64) {
                            $commit62 = false;
                            if ($tm66.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid65 =
                              $tm66.getCurrentTid();
                            if ($e64.tid.isDescendantOf($currentTid65)) {
                                $retry63 = true;
                            }
                            else if ($currentTid65.parent != null) {
                                $retry63 = false;
                                throw $e64;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e64) {
                            $commit62 = false;
                            if ($tm66.checkForStaleObjects()) continue $label61;
                            $retry63 = false;
                            throw new fabric.worker.AbortException($e64);
                        }
                        finally {
                            if ($commit62) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e64) {
                                    $commit62 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e64) {
                                    $commit62 = false;
                                    fabric.common.TransactionID $currentTid65 =
                                      $tm66.getCurrentTid();
                                    if ($currentTid65 != null) {
                                        if ($e64.tid.equals($currentTid65) ||
                                              !$e64.tid.isDescendantOf(
                                                          $currentTid65)) {
                                            throw $e64;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit62 && $retry63) {
                                { val = val$var60; }
                                continue $label61;
                            }
                        }
                    }
                }
            }
            return val;
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
                    fabric.metrics.DerivedMetric val$var70 = val;
                    fabric.worker.transaction.TransactionManager $tm76 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled79 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff77 = 1;
                    boolean $doBackoff78 = true;
                    boolean $retry73 = true;
                    $label71: for (boolean $commit72 = false; !$commit72; ) {
                        if ($backoffEnabled79) {
                            if ($doBackoff78) {
                                if ($backoff77 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff77);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e74) {
                                            
                                        }
                                    }
                                }
                                if ($backoff77 < 5000) $backoff77 *= 2;
                            }
                            $doBackoff78 = $backoff77 <= 32 || !$doBackoff78;
                        }
                        $commit72 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(terms);
                        }
                        catch (final fabric.worker.RetryException $e74) {
                            $commit72 = false;
                            continue $label71;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e74) {
                            $commit72 = false;
                            fabric.common.TransactionID $currentTid75 =
                              $tm76.getCurrentTid();
                            if ($e74.tid.isDescendantOf($currentTid75))
                                continue $label71;
                            if ($currentTid75.parent != null) {
                                $retry73 = false;
                                throw $e74;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e74) {
                            $commit72 = false;
                            if ($tm76.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid75 =
                              $tm76.getCurrentTid();
                            if ($e74.tid.isDescendantOf($currentTid75)) {
                                $retry73 = true;
                            }
                            else if ($currentTid75.parent != null) {
                                $retry73 = false;
                                throw $e74;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e74) {
                            $commit72 = false;
                            if ($tm76.checkForStaleObjects()) continue $label71;
                            $retry73 = false;
                            throw new fabric.worker.AbortException($e74);
                        }
                        finally {
                            if ($commit72) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e74) {
                                    $commit72 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e74) {
                                    $commit72 = false;
                                    fabric.common.TransactionID $currentTid75 =
                                      $tm76.getCurrentTid();
                                    if ($currentTid75 != null) {
                                        if ($e74.tid.equals($currentTid75) ||
                                              !$e74.tid.isDescendantOf(
                                                          $currentTid75)) {
                                            throw $e74;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit72 && $retry73) {
                                { val = val$var70; }
                                continue $label71;
                            }
                        }
                    }
                }
            }
            return val;
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
                    fabric.metrics.DerivedMetric val$var80 = val;
                    fabric.worker.transaction.TransactionManager $tm86 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled89 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff87 = 1;
                    boolean $doBackoff88 = true;
                    boolean $retry83 = true;
                    $label81: for (boolean $commit82 = false; !$commit82; ) {
                        if ($backoffEnabled89) {
                            if ($doBackoff88) {
                                if ($backoff87 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff87);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e84) {
                                            
                                        }
                                    }
                                }
                                if ($backoff87 < 5000) $backoff87 *= 2;
                            }
                            $doBackoff88 = $backoff87 <= 32 || !$doBackoff88;
                        }
                        $commit82 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(terms);
                        }
                        catch (final fabric.worker.RetryException $e84) {
                            $commit82 = false;
                            continue $label81;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e84) {
                            $commit82 = false;
                            fabric.common.TransactionID $currentTid85 =
                              $tm86.getCurrentTid();
                            if ($e84.tid.isDescendantOf($currentTid85))
                                continue $label81;
                            if ($currentTid85.parent != null) {
                                $retry83 = false;
                                throw $e84;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e84) {
                            $commit82 = false;
                            if ($tm86.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid85 =
                              $tm86.getCurrentTid();
                            if ($e84.tid.isDescendantOf($currentTid85)) {
                                $retry83 = true;
                            }
                            else if ($currentTid85.parent != null) {
                                $retry83 = false;
                                throw $e84;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e84) {
                            $commit82 = false;
                            if ($tm86.checkForStaleObjects()) continue $label81;
                            $retry83 = false;
                            throw new fabric.worker.AbortException($e84);
                        }
                        finally {
                            if ($commit82) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e84) {
                                    $commit82 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e84) {
                                    $commit82 = false;
                                    fabric.common.TransactionID $currentTid85 =
                                      $tm86.getCurrentTid();
                                    if ($currentTid85 != null) {
                                        if ($e84.tid.equals($currentTid85) ||
                                              !$e84.tid.isDescendantOf(
                                                          $currentTid85)) {
                                            throw $e84;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit82 && $retry83) {
                                { val = val$var80; }
                                continue $label81;
                            }
                        }
                    }
                }
            }
            return val;
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
            return fabric.metrics.Metric._Impl.minAtStore(s, terms).times(-1);
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
        
        public fabric.worker.metrics.proxies.ProxyMap get$proxies() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.proxies;
        }
        
        public fabric.worker.metrics.proxies.ProxyMap set$proxies(
          fabric.worker.metrics.proxies.ProxyMap val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.proxies = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.worker.metrics.proxies.ProxyMap proxies;
        
        /**
   * @param store
   *            the Store that holds this {@link Metric}
   */
        public fabric.metrics.Metric fabric$metrics$Metric$() {
            fabric$metrics$util$AbstractSubject$();
            this.set$proxies(fabric.worker.metrics.proxies.ProxyMap.emptyMap());
            return (fabric.metrics.Metric) this.$getProxy();
        }
        
        /**
   * Get a proxy metric for this metric (returns this if the store is the same).
   */
        public fabric.metrics.Metric getProxy(fabric.worker.Store s) {
            if ($getStore().equals(s))
                return (fabric.metrics.Metric) this.$getProxy();
            if (this.get$proxies().containsKey(s))
                return (fabric.metrics.Metric)
                         fabric.lang.Object._Proxy.$getProxy(
                                                     this.get$proxies().get(s));
            fabric.metrics.Metric result =
              ((fabric.metrics.ProxyMetric)
                 new fabric.metrics.ProxyMetric._Impl(s).$getProxy()).
              fabric$metrics$ProxyMetric$((fabric.metrics.Metric)
                                            this.$getProxy());
            this.set$proxies(this.get$proxies().put(s, result));
            return result;
        }
        
        /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return the current value of the {@link Metric}.
   */
        public abstract double value(fabric.worker.metrics.StatsMap weakStats);
        
        /** @return the number of samples of the {@link Metric}. */
        public long samples() {
            return samples(fabric.worker.metrics.StatsMap.emptyStats());
        }
        
        /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return the number of samples of the {@link Metric}.
   */
        public abstract long samples(fabric.worker.metrics.StatsMap weakStats);
        
        /** @return the time of the last update of the {@link Metric}. */
        public long lastUpdate() {
            return lastUpdate(fabric.worker.metrics.StatsMap.emptyStats());
        }
        
        /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return the time of the last update of the {@link Metric}.
   */
        public abstract long lastUpdate(fabric.worker.metrics.StatsMap weakStats);
        
        /** @return the current updateInterval estimate for updates of {@link Metric}. */
        public double updateInterval() {
            return updateInterval(fabric.worker.metrics.StatsMap.emptyStats());
        }
        
        /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return the updateInterval estimated for updates of the {@link Metric}.
   */
        public abstract double updateInterval(
          fabric.worker.metrics.StatsMap weakStats);
        
        /** @return the estimated velocity of the {@link Metric}. */
        public double velocity() {
            return velocity(fabric.worker.metrics.StatsMap.emptyStats());
        }
        
        /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return the estimated velocity of the {@link Metric}.
   */
        public abstract double velocity(
          fabric.worker.metrics.StatsMap weakStats);
        
        /** @return the estimated noise of the {@link Metric}. */
        public double noise() {
            return noise(fabric.worker.metrics.StatsMap.emptyStats());
        }
        
        /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return the estimated velocity of the {@link Metric}.
   */
        public abstract double noise(fabric.worker.metrics.StatsMap weakStats);
        
        /**
   * Used to construct and enforce {@link MetricTreaty}s bounding this
   * {@link Metric}s value.
   * <p>
   * Implementations of this method should use "weak" estimates of value,
   * velocity, and noise to avoid contention issues. The internal
   * implementation is expected to have refreshed these estimates recently
   * prior to the call.
   *
   * @param value
   *            the value we're asserting equality with.
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          equalityPolicy(double value, fabric.worker.metrics.StatsMap weakStats,
                         final fabric.worker.Store s) {
            return fabric.worker.metrics.treaties.enforcement.DirectPolicy.
                     singleton;
        }
        
        /**
   * Used to construct and enforce {@link MetricTreaty}s bounding this
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
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          equalityPolicy(double value, final fabric.worker.Store s) {
            return equalityPolicy(value,
                                  fabric.worker.metrics.StatsMap.emptyStats(),
                                  s);
        }
        
        /**
   * Used to construct and enforce {@link MetricTreaty}s bounding this
   * {@link Metric}s value.
   * <p>
   * Implementations of this method should use "weak" estimates of value,
   * velocity, and noise to avoid contention issues. The internal
   * implementation is expected to have refreshed these estimates recently
   * prior to the call.
   *
   * @param bound
   *            a bound that the returned policy enforces.
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base,
                          fabric.worker.metrics.StatsMap weakStats,
                          final fabric.worker.Store s) {
            return fabric.worker.metrics.treaties.enforcement.DirectPolicy.
                     singleton;
        }
        
        /**
   * Used to construct and enforce {@link MetricTreaty}s bounding this
   * {@link Metric}s value.
   * <p>
   * Implementations of this method should use "weak" estimates of value,
   * velocity, and noise to avoid contention issues. The internal
   * implementation is expected to have refreshed these estimates recently
   * prior to the call.
   *
   * @param bound
   *            a bound that the returned policy enforces.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base,
                          final fabric.worker.Store s) {
            return thresholdPolicy(rate, base,
                                   fabric.worker.metrics.StatsMap.emptyStats(),
                                   s);
        }
        
        /**
   * Update the current "weak" estimates to be used by {@link #policy(Bound)}
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   */
        public fabric.worker.metrics.StatsMap refreshWeakEstimates(
          fabric.worker.metrics.StatsMap weakStats) {
            return refreshLocally(weakStats);
        }
        
        public fabric.worker.metrics.StatsMap refreshWeakEstimates_remote(
          fabric.lang.security.Principal caller,
          fabric.worker.metrics.StatsMap weakStats) {
            return refreshWeakEstimates(weakStats);
        }
        
        /**
   * Utility to allow for running updates as close to the metric as possible.
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   *
   * Ugh, public because we don't allow remote calls for non public methods.
   */
        public fabric.worker.metrics.StatsMap refreshLocally(
          fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.Metric._Impl.static_refreshLocally(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy(), weakStats);
        }
        
        private static fabric.worker.metrics.StatsMap static_refreshLocally(
          fabric.metrics.Metric tmp, fabric.worker.metrics.StatsMap weakStats) {
            fabric.common.Logging.METRICS_LOGGER.
              finest(
                "REFRESHING LOCALLY " +
                  java.lang.String.valueOf(
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         tmp)) +
                  " IN " +
                  fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentLog() +
                  " " +
                  java.lang.Thread.currentThread());
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                return weakStats.put(tmp, tmp.computeValue(weakStats),
                                     tmp.computeSamples(weakStats),
                                     tmp.computeLastUpdate(weakStats),
                                     tmp.computeUpdateInterval(weakStats),
                                     tmp.computeVelocity(weakStats),
                                     tmp.computeNoise(weakStats));
            }
            else {
                fabric.worker.metrics.StatsMap result = null;
                {
                    fabric.worker.metrics.StatsMap result$var90 = result;
                    fabric.worker.transaction.TransactionManager $tm96 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled99 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff97 = 1;
                    boolean $doBackoff98 = true;
                    boolean $retry93 = true;
                    $label91: for (boolean $commit92 = false; !$commit92; ) {
                        if ($backoffEnabled99) {
                            if ($doBackoff98) {
                                if ($backoff97 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff97);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e94) {
                                            
                                        }
                                    }
                                }
                                if ($backoff97 < 5000) $backoff97 *= 2;
                            }
                            $doBackoff98 = $backoff97 <= 32 || !$doBackoff98;
                        }
                        $commit92 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            result =
                              weakStats.put(
                                          tmp, tmp.computeValue(weakStats),
                                          tmp.computeSamples(weakStats),
                                          tmp.computeLastUpdate(weakStats),
                                          tmp.computeUpdateInterval(weakStats),
                                          tmp.computeVelocity(weakStats),
                                          tmp.computeNoise(weakStats));
                        }
                        catch (final fabric.worker.RetryException $e94) {
                            $commit92 = false;
                            continue $label91;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e94) {
                            $commit92 = false;
                            fabric.common.TransactionID $currentTid95 =
                              $tm96.getCurrentTid();
                            if ($e94.tid.isDescendantOf($currentTid95))
                                continue $label91;
                            if ($currentTid95.parent != null) {
                                $retry93 = false;
                                throw $e94;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e94) {
                            $commit92 = false;
                            if ($tm96.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid95 =
                              $tm96.getCurrentTid();
                            if ($e94.tid.isDescendantOf($currentTid95)) {
                                $retry93 = true;
                            }
                            else if ($currentTid95.parent != null) {
                                $retry93 = false;
                                throw $e94;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e94) {
                            $commit92 = false;
                            if ($tm96.checkForStaleObjects()) continue $label91;
                            $retry93 = false;
                            throw new fabric.worker.AbortException($e94);
                        }
                        finally {
                            if ($commit92) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e94) {
                                    $commit92 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e94) {
                                    $commit92 = false;
                                    fabric.common.TransactionID $currentTid95 =
                                      $tm96.getCurrentTid();
                                    if ($currentTid95 != null) {
                                        if ($e94.tid.equals($currentTid95) ||
                                              !$e94.tid.isDescendantOf(
                                                          $currentTid95)) {
                                            throw $e94;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit92 && $retry93) {
                                { result = result$var90; }
                                continue $label91;
                            }
                        }
                    }
                }
                return result;
            }
        }
        
        public fabric.worker.metrics.StatsMap refreshLocally_remote(
          fabric.lang.security.Principal caller,
          fabric.worker.metrics.StatsMap weakStats) {
            return refreshLocally(weakStats);
        }
        
        /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
        public abstract double computeValue(fabric.worker.metrics.StatsMap weakStats);
        
        /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
        public abstract long computeSamples(fabric.worker.metrics.StatsMap weakStats);
        
        /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
        public abstract long computeLastUpdate(fabric.worker.metrics.StatsMap weakStats);
        
        /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a freshly computed weak value for this {@link DerivedMetric}.
   */
        public abstract double computeUpdateInterval(fabric.worker.metrics.StatsMap weakStats);
        
        /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a freshly computed weak velocity for this {@link DerivedMetric}.
   */
        public abstract double computeVelocity(fabric.worker.metrics.StatsMap weakStats);
        
        /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a freshly computed noise for this {@link DerivedMetric}.
   */
        public abstract double computeNoise(fabric.worker.metrics.StatsMap weakStats);
        
        /**
   * @return true iff all the sampling and transformations on this metric are
   *         stored on a single store.
   */
        public abstract boolean isSingleStore();
        
        /**
   * @param bound
   *        the bound that the treaty will enforce on this
   *        {@link Metric}
   * @return a {@link MetricTreaty} asserting this metric satisfies the
   *       given bound.
   */
        public fabric.worker.metrics.treaties.MetricTreaty
          createThresholdTreaty(double rate, double base, long time) {
            return this.get$$treaties().
              create(
                new fabric.worker.metrics.treaties.statements.ThresholdStatement(
                  rate, base, time));
        }
        
        /**
   * @param bound
   *        the bound that the treaty will enforce on this
   *        {@link Metric}
   * @return a {@link MetricTreaty} asserting this metric satisfies the
   *       given bound.
   */
        public fabric.worker.metrics.treaties.MetricTreaty createEqualityTreaty(
          double value) {
            return this.get$$treaties().
              create(
                new fabric.worker.metrics.treaties.statements.EqualityStatement(
                  value));
        }
        
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
        
        public void refreshTreaty_remote(
          fabric.lang.security.Principal p, boolean asyncExtension,
          long treatyId, fabric.worker.metrics.StatsMap weakStats) {
            this.refreshTreaty(asyncExtension, treatyId, weakStats);
        }
        
        private static void refreshTreaty_static(
          fabric.metrics.Metric tmp, boolean asyncExtension, long treatyId,
          fabric.worker.metrics.StatsMap weakStats) {
            fabric.worker.metrics.treaties.MetricTreaty t = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                t = tmp.get$$treaties().get(treatyId);
            }
            else {
                {
                    fabric.worker.metrics.treaties.MetricTreaty t$var100 = t;
                    fabric.worker.transaction.TransactionManager $tm106 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled109 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff107 = 1;
                    boolean $doBackoff108 = true;
                    boolean $retry103 = true;
                    $label101: for (boolean $commit102 = false; !$commit102; ) {
                        if ($backoffEnabled109) {
                            if ($doBackoff108) {
                                if ($backoff107 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff107);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e104) {
                                            
                                        }
                                    }
                                }
                                if ($backoff107 < 5000) $backoff107 *= 2;
                            }
                            $doBackoff108 = $backoff107 <= 32 || !$doBackoff108;
                        }
                        $commit102 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { t = tmp.get$$treaties().get(treatyId); }
                        catch (final fabric.worker.RetryException $e104) {
                            $commit102 = false;
                            continue $label101;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e104) {
                            $commit102 = false;
                            fabric.common.TransactionID $currentTid105 =
                              $tm106.getCurrentTid();
                            if ($e104.tid.isDescendantOf($currentTid105))
                                continue $label101;
                            if ($currentTid105.parent != null) {
                                $retry103 = false;
                                throw $e104;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e104) {
                            $commit102 = false;
                            if ($tm106.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid105 =
                              $tm106.getCurrentTid();
                            if ($e104.tid.isDescendantOf($currentTid105)) {
                                $retry103 = true;
                            }
                            else if ($currentTid105.parent != null) {
                                $retry103 = false;
                                throw $e104;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e104) {
                            $commit102 = false;
                            if ($tm106.checkForStaleObjects())
                                continue $label101;
                            $retry103 = false;
                            throw new fabric.worker.AbortException($e104);
                        }
                        finally {
                            if ($commit102) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e104) {
                                    $commit102 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e104) {
                                    $commit102 = false;
                                    fabric.common.TransactionID $currentTid105 =
                                      $tm106.getCurrentTid();
                                    if ($currentTid105 != null) {
                                        if ($e104.tid.equals($currentTid105) ||
                                              !$e104.tid.isDescendantOf(
                                                           $currentTid105)) {
                                            throw $e104;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit102 && $retry103) {
                                { t = t$var100; }
                                continue $label101;
                            }
                        }
                    }
                }
            }
            t.update(asyncExtension, weakStats);
        }
        
        public void refreshTreaty(boolean asyncExtension, long treatyId,
                                  fabric.worker.metrics.StatsMap weakStats) {
            fabric.metrics.Metric._Impl.refreshTreaty_static(
                                          (fabric.metrics.Metric)
                                            this.$getProxy(), asyncExtension,
                                          treatyId, weakStats);
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean includesObserver, fabric.common.util.LongSet treaties) {
            fabric.common.util.LongSet treatiesToProcess =
              new fabric.common.util.LongHashSet(treaties);
            fabric.worker.metrics.ImmutableObserverSet affected =
              fabric.worker.metrics.ImmutableObserverSet.emptySet();
            if (includesObserver) {
                affected = affected.addAll(handleDirectUpdates());
                treatiesToProcess.addAll(
                                    affected.getTreaties((fabric.metrics.Metric)
                                                           this.$getProxy()));
            }
            for (fabric.common.util.LongIterator iter =
                   treatiesToProcess.iterator();
                 iter.hasNext();
                 ) {
                long treatyId = iter.next();
                fabric.worker.metrics.treaties.MetricTreaty treaty =
                  this.get$$treaties().get(treatyId);
                fabric.common.util.Pair treatyUpdate =
                  treaty.update(false,
                                fabric.worker.metrics.StatsMap.emptyStats());
                affected =
                  affected.addAll((fabric.worker.metrics.ImmutableObserverSet)
                                    treatyUpdate.second);
                affected = affected.remove((fabric.metrics.Metric)
                                             this.$getProxy(), treaty.getId());
            }
            return affected;
        }
        
        /**
   * Handle an update where the metric itself is observing.
   */
        public abstract fabric.worker.metrics.ImmutableObserverSet
          handleDirectUpdates();
        
        /**
   * Given a treaty bound that must hold as a post condition, apply the given
   * update if the bound will still hold on this metric afterwards.
   *
   * TODO Bound is static for now, in the future maybe we can support dynamic
   * bounds.
   *
   * TODO: Might be worth allowing multiple bounds to apply relative to the
   * updates in a static method.
   */
        public boolean updateWithPostcondition(
          double bound, fabric.worker.metrics.MetricUpdate[] updates) {
            fabric.worker.metrics.treaties.MetricTreaty existingTreaty =
              this.get$$treaties().
              get(
                new fabric.worker.metrics.treaties.statements.ThresholdStatement(
                  0, bound, 0));
            if (!fabric.lang.Object._Proxy.idEquals(existingTreaty, null) &&
                  existingTreaty.valid()) {
                boolean success = true;
                for (int i = 0; i < updates.length; i++) {
                    if (updates[i].violatesExistingTreaties()) {
                        success = false;
                        break;
                    }
                }
                if (success) {
                    for (int i = 0; i < updates.length; i++) {
                        updates[i].applyUpdate();
                    }
                    return true;
                }
            }
            fabric.worker.metrics.StatsMap substituted =
              fabric.worker.metrics.StatsMap.emptyStats();
            for (int i = 0; i < updates.length; i++) {
                substituted = substituted.put(updates[i].m, updates[i].newVal,
                                              0, 0, 1, 0, 1);
            }
            if (value(substituted) > bound) {
                for (int i = 0; i < updates.length; i++) {
                    updates[i].applyUpdate();
                }
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
                createAndActivateThresholdTreaty(0, bound, 0, true);
                return true;
            }
            return false;
        }
        
        /**
   * Create and activate a threshold treaty (purely for
   * updateWithPostcondition), possibly across all proxies of this metric.
   *
   * @param proactive
   *        flag indicating if the treaty should also be proactively established
   *        across all proxies of the metric.
   */
        public void createAndActivateThresholdTreaty(double rate, double base,
                                                     long time,
                                                     boolean proactive) {
            createThresholdTreaty(rate, base, time).
              update(false, fabric.worker.metrics.StatsMap.emptyStats());
            if (proactive) {
                for (java.util.Iterator iter =
                       this.get$proxies().entrySet().iterator();
                     iter.hasNext();
                     ) {
                    java.util.Map.Entry entry = (java.util.Map.Entry)
                                                  iter.next();
                    ((fabric.metrics.Metric)
                       fabric.lang.Object._Proxy.
                       $getProxy(
                         fabric.lang.WrappedJavaInlineable.$wrap(
                                                             entry.getValue(
                                                                     )))).
                      createThresholdTreaty(rate, base, time).
                      update(false,
                             fabric.worker.metrics.StatsMap.emptyStats());
                }
            }
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
            $writeInline(out, this.proxies);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.metrics.treaties.TreatySet treaties,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, treaties, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.proxies = (fabric.worker.metrics.proxies.ProxyMap)
                             in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.Metric._Impl src = (fabric.metrics.Metric._Impl)
                                                other;
            this.proxies = src.proxies;
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
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.metrics.treaties.TreatySet treaties,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, treaties, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { 62, 31, -96, 73, 43,
    -35, -117, 110, 27, 15, 121, -52, 124, 111, -95, -127, -107, 120, 58, -17,
    117, -54, 114, 20, -109, -92, -87, 25, -123, 81, -66, 34 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1531507232000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
