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
                    fabric.worker.metrics.treaties.MetricTreaty mc$var128 = mc;
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
                        try { mc = tmp.createEqualityTreaty(value); }
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
                    fabric.worker.metrics.treaties.MetricTreaty mc$var138 = mc;
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
                            mc = tmp.createThresholdTreaty(rate, base, time);
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
                                { mc = mc$var138; }
                                continue $label139;
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
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                a, term);
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
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(terms);
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
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(terms);
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
                    fabric.worker.metrics.StatsMap result$var178 = result;
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
                            result =
                              weakStats.put(
                                          tmp, tmp.computeValue(weakStats),
                                          tmp.computeSamples(weakStats),
                                          tmp.computeLastUpdate(weakStats),
                                          tmp.computeUpdateInterval(weakStats),
                                          tmp.computeVelocity(weakStats),
                                          tmp.computeNoise(weakStats));
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
                                { result = result$var178; }
                                continue $label179;
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
                    fabric.worker.metrics.treaties.MetricTreaty t$var188 = t;
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
                        try { t = tmp.get$$treaties().get(treatyId); }
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
                                { t = t$var188; }
                                continue $label189;
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
    
    public static final byte[] $classHash = new byte[] { -122, 54, 47, -37,
    -116, 104, -54, -17, -53, -31, 78, -65, -19, -87, -2, -39, -48, -47, 101,
    -117, -28, 2, -47, -34, -44, -52, -11, 63, 68, -76, 93, -89 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1529351168000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
