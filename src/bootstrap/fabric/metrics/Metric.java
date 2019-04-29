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
import fabric.metrics.util.TreatiesBox;
import fabric.metrics.treaties.Treaty;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.metrics.ImmutableObjectSet;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.RunningMetricStats;
import fabric.worker.metrics.proxies.ProxyMap;
import fabric.worker.metrics.treaties.TreatySet;
import fabric.worker.metrics.treaties.enforcement.DirectPolicy;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.metrics.treaties.statements.EqualityStatement;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import fabric.lang.security.LabelUtil;
import java.util.logging.Level;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import fabric.common.Logging;
import fabric.common.util.Pair;

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
   *            the rate parameter for the bound on the resulting
   *            {@link Treaty}
   * @param base
   *            the base parameter for the bound on the resulting
   *            {@link Treaty}
   * @param time
   *            the startTime parameter of the bound on the resulting
   *            {@link Treaty}
   * @return a {@link Treaty} which enforces that the {@link Metric}
   *         satisfies a bound with the given parameters at the given time. If
   *         such a {@link Treaty} already exists, it is returned,
   *         otherwise a new one is created and returned (unactivated).
   */
    public fabric.metrics.treaties.Treaty getEqualityTreaty(double value);
    
    /**
   * @param rate
   *            the rate parameter for the bound on the resulting
   *            {@link Treaty}
   * @param base
   *            the base parameter for the bound on the resulting
   *            {@link Treaty}
   * @param time
   *            the startTime parameter of the bound on the resulting
   *            {@link Treaty}
   * @return a {@link Treaty} which enforces that the {@link Metric}
   *         satisfies a bound with the given parameters at the given
   *         time. If such a {@link Treaty} already exists, it is
   *         returned, otherwise a new one is created and returned
   *         (unactivated).
   *
   */
    public fabric.metrics.treaties.Treaty getThresholdTreaty(double rate,
                                                             double base, long time);
    
    /**
   * @param rate
   *            the rate parameter for the bound on the resulting
   *            {@link Treaty}
   * @param base
   *            the base parameter for the bound on the resulting
   *            {@link Treaty}
   * @return a {@link Treaty} which enforces that the {@link Metric}
   *         satisfies a bound with the given parameters at the
   *         current time. If such a {@link Treaty} already exists, it
   *         is returned, otherwise a new one is created and returned
   *         (unactivated).
   */
    public fabric.metrics.treaties.Treaty getThresholdTreaty(double rate, double base);
    
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
   * Used to construct and enforce {@link Treaty}s bounding this
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
      double value,
      long time, fabric.worker.metrics.StatsMap weakStats, final fabric.worker.Store s);
    
    /**
   * Used to construct and enforce {@link Treaty}s bounding this
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
   * Used to construct and enforce {@link Treaty}s bounding this
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
   * Used to construct and enforce {@link Treaty}s bounding this
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
      double rate, double base,
      long time, fabric.worker.metrics.StatsMap weakStats, final fabric.worker.Store s);
    
    /**
   * Used to construct and enforce {@link Treaty}s bounding this
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
   * Used to construct and enforce {@link Treaty}s bounding this
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
    
    public fabric.metrics.util.TreatiesBox get$treatiesBox();
    
    public fabric.metrics.util.TreatiesBox set$treatiesBox(fabric.metrics.util.TreatiesBox val);
    
    /**
   * @return a {@link Treaty} asserting this metric satisfies the
   *       given statement.
   */
    public fabric.metrics.treaties.Treaty createTreaty(
      fabric.worker.metrics.treaties.statements.TreatyStatement stmt);
    
    public fabric.metrics.treaties.Treaty
      createTreaty_remote(fabric.lang.security.Principal p,
                          fabric.worker.metrics.treaties.statements.TreatyStatement stmt);
    
    /**
   * @return a {@link Treaty} asserting this metric satisfies the
   *       given statement.
   */
    public fabric.metrics.treaties.Treaty createTreaty(
      fabric.worker.metrics.treaties.statements.TreatyStatement stmt,
      fabric.worker.metrics.StatsMap stats);
    
    public fabric.metrics.treaties.Treaty createTreaty_remote(
      fabric.lang.security.Principal p,
      fabric.worker.metrics.treaties.statements.TreatyStatement stmt, fabric.worker.metrics.StatsMap stats);
    
    /**
   * @param bound
   *        the bound that the treaty will enforce on this
   *        {@link Metric}
   * @return a {@link Treaty} asserting this metric satisfies the
   *       given bound.
   */
    public fabric.metrics.treaties.Treaty createThresholdTreaty(double rate,
                                                                double base,
                                                                long time);
    
    public fabric.metrics.treaties.Treaty createThresholdTreaty_remote(
      fabric.lang.security.Principal p, double rate, double base, long time);
    
    /**
   * @param bound
   *        the bound that the treaty will enforce on this
   *        {@link Metric}
   * @return a {@link Treaty} asserting this metric satisfies the
   *       given bound.
   */
    public fabric.metrics.treaties.Treaty createThresholdTreaty(
      double rate, double base, long time,
      fabric.worker.metrics.StatsMap weakStats);
    
    public fabric.metrics.treaties.Treaty createThresholdTreaty_remote(
      fabric.lang.security.Principal p,
      double rate, double base, long time, fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * @param bound
   *        the bound that the treaty will enforce on this
   *        {@link Metric}
   * @return a {@link Treaty} asserting this metric satisfies the
   *       given bound.
   */
    public fabric.metrics.treaties.Treaty createEqualityTreaty(double value);
    
    public fabric.metrics.treaties.Treaty
      createEqualityTreaty_remote(fabric.lang.security.Principal p, double value);
    
    /**
   * @param bound
   *        the bound that the treaty will enforce on this
   *        {@link Metric}
   * @return a {@link Treaty} asserting this metric satisfies the
   *       given bound.
   */
    public fabric.metrics.treaties.Treaty createEqualityTreaty(
      double value, fabric.worker.metrics.StatsMap weakStats);
    
    public fabric.metrics.treaties.Treaty createEqualityTreaty_remote(
      fabric.lang.security.Principal p, double value,
      fabric.worker.metrics.StatsMap weakStats);
    
    public int compareTo(java.lang.Object that);
    
    /**
   * Create and activate a threshold treaty (purely for
   * updateWithPostcondition), possibly across all proxies of this metric.
   *
   * @param proactive
   *        flag indicating if the treaty should also be proactively established
   *        across all proxies of the metric.
   */
    public void createAndActivateTreaty(
      fabric.worker.metrics.treaties.statements.TreatyStatement stmt,
      boolean proactive);
    
    public void assertPostcondition(
      fabric.worker.metrics.treaties.statements.TreatyStatement stmt);
    
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
        
        public fabric.metrics.util.TreatiesBox get$treatiesBox() {
            return ((fabric.metrics.Metric._Impl) fetch()).get$treatiesBox();
        }
        
        public fabric.metrics.util.TreatiesBox set$treatiesBox(
          fabric.metrics.util.TreatiesBox val) {
            return ((fabric.metrics.Metric._Impl) fetch()).set$treatiesBox(val);
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
        
        public fabric.metrics.treaties.Treaty getEqualityTreaty(double arg1) {
            return ((fabric.metrics.Metric) fetch()).getEqualityTreaty(arg1);
        }
        
        public fabric.metrics.treaties.Treaty getThresholdTreaty(double arg1,
                                                                 double arg2,
                                                                 long arg3) {
            return ((fabric.metrics.Metric) fetch()).getThresholdTreaty(arg1,
                                                                        arg2,
                                                                        arg3);
        }
        
        public fabric.metrics.treaties.Treaty getThresholdTreaty(double arg1,
                                                                 double arg2) {
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
          equalityPolicy(double arg1, long arg2,
                         fabric.worker.metrics.StatsMap arg3,
                         fabric.worker.Store arg4) {
            return ((fabric.metrics.Metric) fetch()).equalityPolicy(arg1, arg2,
                                                                    arg3, arg4);
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
          thresholdPolicy(double arg1, double arg2, long arg3,
                          fabric.worker.metrics.StatsMap arg4,
                          fabric.worker.Store arg5) {
            return ((fabric.metrics.Metric) fetch()).thresholdPolicy(arg1, arg2,
                                                                     arg3, arg4,
                                                                     arg5);
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
        
        public fabric.metrics.treaties.Treaty createTreaty(
          fabric.worker.metrics.treaties.statements.TreatyStatement arg1) {
            return ((fabric.metrics.Metric) fetch()).createTreaty(arg1);
        }
        
        public fabric.metrics.treaties.Treaty createTreaty_remote(
          fabric.lang.security.Principal arg1,
          fabric.worker.metrics.treaties.statements.TreatyStatement arg2) {
            return ((fabric.metrics.Metric) fetch()).createTreaty_remote(arg1,
                                                                         arg2);
        }
        
        public static final java.lang.Class[] $paramTypes2 =
          { fabric.worker.metrics.treaties.statements.TreatyStatement.class };
        
        public fabric.metrics.treaties.Treaty createTreaty$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1,
          fabric.worker.metrics.treaties.statements.TreatyStatement arg2) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                return createTreaty(arg2);
            else
                try {
                    return (fabric.metrics.treaties.Treaty)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               $remoteWorker.
                                   issueRemoteCall(
                                     this, "createTreaty", $paramTypes2,
                                     new java.lang.Object[] { arg2 }));
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public fabric.metrics.treaties.Treaty createTreaty(
          fabric.worker.metrics.treaties.statements.TreatyStatement arg1,
          fabric.worker.metrics.StatsMap arg2) {
            return ((fabric.metrics.Metric) fetch()).createTreaty(arg1, arg2);
        }
        
        public fabric.metrics.treaties.Treaty createTreaty_remote(
          fabric.lang.security.Principal arg1,
          fabric.worker.metrics.treaties.statements.TreatyStatement arg2,
          fabric.worker.metrics.StatsMap arg3) {
            return ((fabric.metrics.Metric) fetch()).createTreaty_remote(arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public static final java.lang.Class[] $paramTypes3 =
          { fabric.worker.metrics.treaties.statements.TreatyStatement.class,
        fabric.worker.metrics.StatsMap.class };
        
        public fabric.metrics.treaties.Treaty createTreaty$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1,
          fabric.worker.metrics.treaties.statements.TreatyStatement arg2,
          fabric.worker.metrics.StatsMap arg3) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                return createTreaty(arg2, arg3);
            else
                try {
                    return (fabric.metrics.treaties.Treaty)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               $remoteWorker.issueRemoteCall(
                                               this,
                                               "createTreaty",
                                               $paramTypes3,
                                               new java.lang.Object[] { arg2,
                                                 arg3 }));
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public static fabric.metrics.treaties.Treaty createTreaty_static(
          fabric.metrics.Metric arg1,
          fabric.worker.metrics.treaties.statements.TreatyStatement arg2,
          fabric.worker.metrics.StatsMap arg3) {
            return fabric.metrics.Metric._Impl.createTreaty_static(arg1, arg2,
                                                                   arg3);
        }
        
        public fabric.metrics.treaties.Treaty createThresholdTreaty(double arg1,
                                                                    double arg2,
                                                                    long arg3) {
            return ((fabric.metrics.Metric) fetch()).createThresholdTreaty(
                                                       arg1, arg2, arg3);
        }
        
        public fabric.metrics.treaties.Treaty createThresholdTreaty_remote(
          fabric.lang.security.Principal arg1, double arg2, double arg3,
          long arg4) {
            return ((fabric.metrics.Metric) fetch()).
              createThresholdTreaty_remote(arg1, arg2, arg3, arg4);
        }
        
        public static final java.lang.Class[] $paramTypes4 = { double.class,
        double.class, long.class };
        
        public fabric.metrics.treaties.Treaty createThresholdTreaty$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, double arg2, double arg3,
          long arg4) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                return createThresholdTreaty(arg2, arg3, arg4);
            else
                try {
                    return (fabric.metrics.treaties.Treaty)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               $remoteWorker.issueRemoteCall(
                                               this,
                                               "createThresholdTreaty",
                                               $paramTypes4,
                                               new java.lang.Object[] { arg2,
                                                 arg3, arg4 }));
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public fabric.metrics.treaties.Treaty createThresholdTreaty(
          double arg1, double arg2, long arg3,
          fabric.worker.metrics.StatsMap arg4) {
            return ((fabric.metrics.Metric) fetch()).createThresholdTreaty(
                                                       arg1, arg2, arg3, arg4);
        }
        
        public fabric.metrics.treaties.Treaty createThresholdTreaty_remote(
          fabric.lang.security.Principal arg1, double arg2, double arg3,
          long arg4, fabric.worker.metrics.StatsMap arg5) {
            return ((fabric.metrics.Metric) fetch()).
              createThresholdTreaty_remote(arg1, arg2, arg3, arg4, arg5);
        }
        
        public static final java.lang.Class[] $paramTypes5 = { double.class,
        double.class, long.class, fabric.worker.metrics.StatsMap.class };
        
        public fabric.metrics.treaties.Treaty createThresholdTreaty$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, double arg2, double arg3,
          long arg4, fabric.worker.metrics.StatsMap arg5) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                return createThresholdTreaty(arg2, arg3, arg4, arg5);
            else
                try {
                    return (fabric.metrics.treaties.Treaty)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               $remoteWorker.issueRemoteCall(
                                               this,
                                               "createThresholdTreaty",
                                               $paramTypes5,
                                               new java.lang.Object[] { arg2,
                                                 arg3, arg4, arg5 }));
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public static fabric.metrics.treaties.Treaty
          createThresholdTreaty_static(fabric.metrics.Metric arg1, double arg2,
                                       double arg3, long arg4,
                                       fabric.worker.metrics.StatsMap arg5) {
            return fabric.metrics.Metric._Impl.createThresholdTreaty_static(
                                                 arg1, arg2, arg3, arg4, arg5);
        }
        
        public fabric.metrics.treaties.Treaty createEqualityTreaty(
          double arg1) {
            return ((fabric.metrics.Metric) fetch()).createEqualityTreaty(arg1);
        }
        
        public fabric.metrics.treaties.Treaty createEqualityTreaty_remote(
          fabric.lang.security.Principal arg1, double arg2) {
            return ((fabric.metrics.Metric) fetch()).
              createEqualityTreaty_remote(arg1, arg2);
        }
        
        public static final java.lang.Class[] $paramTypes6 = { double.class };
        
        public fabric.metrics.treaties.Treaty createEqualityTreaty$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, double arg2) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                return createEqualityTreaty(arg2);
            else
                try {
                    return (fabric.metrics.treaties.Treaty)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               $remoteWorker.
                                   issueRemoteCall(
                                     this, "createEqualityTreaty", $paramTypes6,
                                     new java.lang.Object[] { arg2 }));
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public fabric.metrics.treaties.Treaty createEqualityTreaty(
          double arg1, fabric.worker.metrics.StatsMap arg2) {
            return ((fabric.metrics.Metric) fetch()).createEqualityTreaty(arg1,
                                                                          arg2);
        }
        
        public fabric.metrics.treaties.Treaty createEqualityTreaty_remote(
          fabric.lang.security.Principal arg1, double arg2,
          fabric.worker.metrics.StatsMap arg3) {
            return ((fabric.metrics.Metric) fetch()).
              createEqualityTreaty_remote(arg1, arg2, arg3);
        }
        
        public static final java.lang.Class[] $paramTypes7 = { double.class,
        fabric.worker.metrics.StatsMap.class };
        
        public fabric.metrics.treaties.Treaty createEqualityTreaty$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, double arg2,
          fabric.worker.metrics.StatsMap arg3) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                return createEqualityTreaty(arg2, arg3);
            else
                try {
                    return (fabric.metrics.treaties.Treaty)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               $remoteWorker.issueRemoteCall(
                                               this,
                                               "createEqualityTreaty",
                                               $paramTypes7,
                                               new java.lang.Object[] { arg2,
                                                 arg3 }));
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public static fabric.metrics.treaties.Treaty
          createEqualityTreaty_static(fabric.metrics.Metric arg1, double arg2,
                                      fabric.worker.metrics.StatsMap arg3) {
            return fabric.metrics.Metric._Impl.createEqualityTreaty_static(
                                                 arg1, arg2, arg3);
        }
        
        public int compareTo(java.lang.Object arg1) {
            return ((fabric.metrics.Metric) fetch()).compareTo(arg1);
        }
        
        public void createAndActivateTreaty(
          fabric.worker.metrics.treaties.statements.TreatyStatement arg1,
          boolean arg2) {
            ((fabric.metrics.Metric) fetch()).createAndActivateTreaty(arg1,
                                                                      arg2);
        }
        
        public void assertPostcondition(
          fabric.worker.metrics.treaties.statements.TreatyStatement arg1) {
            ((fabric.metrics.Metric) fetch()).assertPostcondition(arg1);
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
                    fabric.metrics.DerivedMetric val$var97 = val;
                    fabric.worker.transaction.TransactionManager $tm104 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled107 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff105 = 1;
                    boolean $doBackoff106 = true;
                    boolean $retry100 = true;
                    boolean $keepReads101 = false;
                    $label98: for (boolean $commit99 = false; !$commit99; ) {
                        if ($backoffEnabled107) {
                            if ($doBackoff106) {
                                if ($backoff105 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff105));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e102) {
                                            
                                        }
                                    }
                                }
                                if ($backoff105 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff105 =
                                      java.lang.Math.
                                        min(
                                          $backoff105 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff106 = $backoff105 <= 32 || !$doBackoff106;
                        }
                        $commit99 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                scalar, tmp);
                        }
                        catch (final fabric.worker.RetryException $e102) {
                            $commit99 = false;
                            continue $label98;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e102) {
                            $commit99 = false;
                            $retry100 = false;
                            $keepReads101 = $e102.keepReads;
                            fabric.common.TransactionID $currentTid103 =
                              $tm104.getCurrentTid();
                            if ($e102.tid == null ||
                                  !$e102.tid.isDescendantOf($currentTid103)) {
                                throw $e102;
                            }
                            throw new fabric.worker.UserAbortException($e102);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e102) {
                            $commit99 = false;
                            fabric.common.TransactionID $currentTid103 =
                              $tm104.getCurrentTid();
                            if ($e102.tid.isDescendantOf($currentTid103))
                                continue $label98;
                            if ($currentTid103.parent != null) {
                                $retry100 = false;
                                throw $e102;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e102) {
                            $commit99 = false;
                            $retry100 = false;
                            if ($tm104.inNestedTxn()) { $keepReads101 = true; }
                            throw $e102;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid103 =
                              $tm104.getCurrentTid();
                            if ($commit99) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e102) {
                                    $commit99 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e102) {
                                    $commit99 = false;
                                    $retry100 = false;
                                    $keepReads101 = $e102.keepReads;
                                    if ($e102.tid ==
                                          null ||
                                          !$e102.tid.isDescendantOf(
                                                       $currentTid103))
                                        throw $e102;
                                    throw new fabric.worker.UserAbortException(
                                            $e102);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e102) {
                                    $commit99 = false;
                                    $currentTid103 = $tm104.getCurrentTid();
                                    if ($currentTid103 != null) {
                                        if ($e102.tid.equals($currentTid103) ||
                                              !$e102.tid.isDescendantOf(
                                                           $currentTid103)) {
                                            throw $e102;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm104.inNestedTxn() &&
                                      $tm104.checkForStaleObjects()) {
                                    $retry100 = true;
                                    $keepReads101 = false;
                                }
                                if ($keepReads101) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e102) {
                                        $currentTid103 = $tm104.getCurrentTid();
                                        if ($currentTid103 != null &&
                                              ($e102.tid.equals($currentTid103) || !$e102.tid.isDescendantOf($currentTid103))) {
                                            throw $e102;
                                        } else {
                                            $retry100 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit99) {
                                { val = val$var97; }
                                if ($retry100) { continue $label98; }
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
                    fabric.metrics.DerivedMetric val$var108 = val;
                    fabric.worker.transaction.TransactionManager $tm115 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled118 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff116 = 1;
                    boolean $doBackoff117 = true;
                    boolean $retry111 = true;
                    boolean $keepReads112 = false;
                    $label109: for (boolean $commit110 = false; !$commit110; ) {
                        if ($backoffEnabled118) {
                            if ($doBackoff117) {
                                if ($backoff116 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff116));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e113) {
                                            
                                        }
                                    }
                                }
                                if ($backoff116 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff116 =
                                      java.lang.Math.
                                        min(
                                          $backoff116 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff117 = $backoff116 <= 32 || !$doBackoff117;
                        }
                        $commit110 = true;
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
                        catch (final fabric.worker.RetryException $e113) {
                            $commit110 = false;
                            continue $label109;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e113) {
                            $commit110 = false;
                            $retry111 = false;
                            $keepReads112 = $e113.keepReads;
                            fabric.common.TransactionID $currentTid114 =
                              $tm115.getCurrentTid();
                            if ($e113.tid == null ||
                                  !$e113.tid.isDescendantOf($currentTid114)) {
                                throw $e113;
                            }
                            throw new fabric.worker.UserAbortException($e113);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e113) {
                            $commit110 = false;
                            fabric.common.TransactionID $currentTid114 =
                              $tm115.getCurrentTid();
                            if ($e113.tid.isDescendantOf($currentTid114))
                                continue $label109;
                            if ($currentTid114.parent != null) {
                                $retry111 = false;
                                throw $e113;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e113) {
                            $commit110 = false;
                            $retry111 = false;
                            if ($tm115.inNestedTxn()) { $keepReads112 = true; }
                            throw $e113;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid114 =
                              $tm115.getCurrentTid();
                            if ($commit110) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e113) {
                                    $commit110 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e113) {
                                    $commit110 = false;
                                    $retry111 = false;
                                    $keepReads112 = $e113.keepReads;
                                    if ($e113.tid ==
                                          null ||
                                          !$e113.tid.isDescendantOf(
                                                       $currentTid114))
                                        throw $e113;
                                    throw new fabric.worker.UserAbortException(
                                            $e113);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e113) {
                                    $commit110 = false;
                                    $currentTid114 = $tm115.getCurrentTid();
                                    if ($currentTid114 != null) {
                                        if ($e113.tid.equals($currentTid114) ||
                                              !$e113.tid.isDescendantOf(
                                                           $currentTid114)) {
                                            throw $e113;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm115.inNestedTxn() &&
                                      $tm115.checkForStaleObjects()) {
                                    $retry111 = true;
                                    $keepReads112 = false;
                                }
                                if ($keepReads112) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e113) {
                                        $currentTid114 = $tm115.getCurrentTid();
                                        if ($currentTid114 != null &&
                                              ($e113.tid.equals($currentTid114) || !$e113.tid.isDescendantOf($currentTid114))) {
                                            throw $e113;
                                        } else {
                                            $retry111 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit110) {
                                { val = val$var108; }
                                if ($retry111) { continue $label109; }
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
                        fabric.metrics.DerivedMetric val$var119 = val;
                        fabric.worker.transaction.TransactionManager $tm126 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled129 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff127 = 1;
                        boolean $doBackoff128 = true;
                        boolean $retry122 = true;
                        boolean $keepReads123 = false;
                        $label120: for (boolean $commit121 = false; !$commit121;
                                        ) {
                            if ($backoffEnabled129) {
                                if ($doBackoff128) {
                                    if ($backoff127 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff127));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e124) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff127 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff127 =
                                          java.lang.Math.
                                            min(
                                              $backoff127 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff128 = $backoff127 <= 32 ||
                                                  !$doBackoff128;
                            }
                            $commit121 = true;
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
                            catch (final fabric.worker.RetryException $e124) {
                                $commit121 = false;
                                continue $label120;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e124) {
                                $commit121 = false;
                                $retry122 = false;
                                $keepReads123 = $e124.keepReads;
                                fabric.common.TransactionID $currentTid125 =
                                  $tm126.getCurrentTid();
                                if ($e124.tid ==
                                      null ||
                                      !$e124.tid.isDescendantOf(
                                                   $currentTid125)) {
                                    throw $e124;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e124);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e124) {
                                $commit121 = false;
                                fabric.common.TransactionID $currentTid125 =
                                  $tm126.getCurrentTid();
                                if ($e124.tid.isDescendantOf($currentTid125))
                                    continue $label120;
                                if ($currentTid125.parent != null) {
                                    $retry122 = false;
                                    throw $e124;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e124) {
                                $commit121 = false;
                                $retry122 = false;
                                if ($tm126.inNestedTxn()) {
                                    $keepReads123 = true;
                                }
                                throw $e124;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid125 =
                                  $tm126.getCurrentTid();
                                if ($commit121) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e124) {
                                        $commit121 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e124) {
                                        $commit121 = false;
                                        $retry122 = false;
                                        $keepReads123 = $e124.keepReads;
                                        if ($e124.tid ==
                                              null ||
                                              !$e124.tid.isDescendantOf(
                                                           $currentTid125))
                                            throw $e124;
                                        throw new fabric.worker.
                                                UserAbortException($e124);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e124) {
                                        $commit121 = false;
                                        $currentTid125 = $tm126.getCurrentTid();
                                        if ($currentTid125 != null) {
                                            if ($e124.tid.equals(
                                                            $currentTid125) ||
                                                  !$e124.tid.
                                                  isDescendantOf(
                                                    $currentTid125)) {
                                                throw $e124;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm126.inNestedTxn() &&
                                          $tm126.checkForStaleObjects()) {
                                        $retry122 = true;
                                        $keepReads123 = false;
                                    }
                                    if ($keepReads123) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e124) {
                                            $currentTid125 = $tm126.getCurrentTid();
                                            if ($currentTid125 != null &&
                                                  ($e124.tid.equals($currentTid125) || !$e124.tid.isDescendantOf($currentTid125))) {
                                                throw $e124;
                                            } else {
                                                $retry122 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit121) {
                                    { val = val$var119; }
                                    if ($retry122) { continue $label120; }
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
                        fabric.metrics.DerivedMetric val$var130 = val;
                        fabric.worker.transaction.TransactionManager $tm137 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled140 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff138 = 1;
                        boolean $doBackoff139 = true;
                        boolean $retry133 = true;
                        boolean $keepReads134 = false;
                        $label131: for (boolean $commit132 = false; !$commit132;
                                        ) {
                            if ($backoffEnabled140) {
                                if ($doBackoff139) {
                                    if ($backoff138 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff138));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e135) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff138 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff138 =
                                          java.lang.Math.
                                            min(
                                              $backoff138 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff139 = $backoff138 <= 32 ||
                                                  !$doBackoff139;
                            }
                            $commit132 = true;
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
                            catch (final fabric.worker.RetryException $e135) {
                                $commit132 = false;
                                continue $label131;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e135) {
                                $commit132 = false;
                                $retry133 = false;
                                $keepReads134 = $e135.keepReads;
                                fabric.common.TransactionID $currentTid136 =
                                  $tm137.getCurrentTid();
                                if ($e135.tid ==
                                      null ||
                                      !$e135.tid.isDescendantOf(
                                                   $currentTid136)) {
                                    throw $e135;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e135);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e135) {
                                $commit132 = false;
                                fabric.common.TransactionID $currentTid136 =
                                  $tm137.getCurrentTid();
                                if ($e135.tid.isDescendantOf($currentTid136))
                                    continue $label131;
                                if ($currentTid136.parent != null) {
                                    $retry133 = false;
                                    throw $e135;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e135) {
                                $commit132 = false;
                                $retry133 = false;
                                if ($tm137.inNestedTxn()) {
                                    $keepReads134 = true;
                                }
                                throw $e135;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid136 =
                                  $tm137.getCurrentTid();
                                if ($commit132) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e135) {
                                        $commit132 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e135) {
                                        $commit132 = false;
                                        $retry133 = false;
                                        $keepReads134 = $e135.keepReads;
                                        if ($e135.tid ==
                                              null ||
                                              !$e135.tid.isDescendantOf(
                                                           $currentTid136))
                                            throw $e135;
                                        throw new fabric.worker.
                                                UserAbortException($e135);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e135) {
                                        $commit132 = false;
                                        $currentTid136 = $tm137.getCurrentTid();
                                        if ($currentTid136 != null) {
                                            if ($e135.tid.equals(
                                                            $currentTid136) ||
                                                  !$e135.tid.
                                                  isDescendantOf(
                                                    $currentTid136)) {
                                                throw $e135;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm137.inNestedTxn() &&
                                          $tm137.checkForStaleObjects()) {
                                        $retry133 = true;
                                        $keepReads134 = false;
                                    }
                                    if ($keepReads134) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e135) {
                                            $currentTid136 = $tm137.getCurrentTid();
                                            if ($currentTid136 != null &&
                                                  ($e135.tid.equals($currentTid136) || !$e135.tid.isDescendantOf($currentTid136))) {
                                                throw $e135;
                                            } else {
                                                $retry133 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit132) {
                                    { val = val$var130; }
                                    if ($retry133) { continue $label131; }
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
   *            {@link Treaty}
   * @param base
   *            the base parameter for the bound on the resulting
   *            {@link Treaty}
   * @param time
   *            the startTime parameter of the bound on the resulting
   *            {@link Treaty}
   * @return a {@link Treaty} which enforces that the {@link Metric}
   *         satisfies a bound with the given parameters at the given time. If
   *         such a {@link Treaty} already exists, it is returned,
   *         otherwise a new one is created and returned (unactivated).
   */
        public fabric.metrics.treaties.Treaty getEqualityTreaty(double value) {
            return fabric.metrics.Metric._Impl.static_getEqualityTreaty(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy(), value);
        }
        
        private static fabric.metrics.treaties.Treaty static_getEqualityTreaty(
          fabric.metrics.Metric tmp, double value) {
            return tmp.createEqualityTreaty(value);
        }
        
        /**
   * @param rate
   *            the rate parameter for the bound on the resulting
   *            {@link Treaty}
   * @param base
   *            the base parameter for the bound on the resulting
   *            {@link Treaty}
   * @param time
   *            the startTime parameter of the bound on the resulting
   *            {@link Treaty}
   * @return a {@link Treaty} which enforces that the {@link Metric}
   *         satisfies a bound with the given parameters at the given
   *         time. If such a {@link Treaty} already exists, it is
   *         returned, otherwise a new one is created and returned
   *         (unactivated).
   *
   */
        public fabric.metrics.treaties.Treaty getThresholdTreaty(double rate,
                                                                 double base,
                                                                 long time) {
            return fabric.metrics.Metric._Impl.static_getThresholdTreaty(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy(), rate, base,
                                                 time);
        }
        
        private static fabric.metrics.treaties.Treaty static_getThresholdTreaty(
          fabric.metrics.Metric tmp, double rate, double base, long time) {
            return tmp.createThresholdTreaty(rate, base, time);
        }
        
        /**
   * @param rate
   *            the rate parameter for the bound on the resulting
   *            {@link Treaty}
   * @param base
   *            the base parameter for the bound on the resulting
   *            {@link Treaty}
   * @return a {@link Treaty} which enforces that the {@link Metric}
   *         satisfies a bound with the given parameters at the
   *         current time. If such a {@link Treaty} already exists, it
   *         is returned, otherwise a new one is created and returned
   *         (unactivated).
   */
        public fabric.metrics.treaties.Treaty getThresholdTreaty(double rate,
                                                                 double base) {
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
                    fabric.metrics.DerivedMetric val$var141 = val;
                    fabric.worker.transaction.TransactionManager $tm148 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled151 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff149 = 1;
                    boolean $doBackoff150 = true;
                    boolean $retry144 = true;
                    boolean $keepReads145 = false;
                    $label142: for (boolean $commit143 = false; !$commit143; ) {
                        if ($backoffEnabled151) {
                            if ($doBackoff150) {
                                if ($backoff149 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff149));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e146) {
                                            
                                        }
                                    }
                                }
                                if ($backoff149 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff149 =
                                      java.lang.Math.
                                        min(
                                          $backoff149 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff150 = $backoff149 <= 32 || !$doBackoff150;
                        }
                        $commit143 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                a, term);
                        }
                        catch (final fabric.worker.RetryException $e146) {
                            $commit143 = false;
                            continue $label142;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e146) {
                            $commit143 = false;
                            $retry144 = false;
                            $keepReads145 = $e146.keepReads;
                            fabric.common.TransactionID $currentTid147 =
                              $tm148.getCurrentTid();
                            if ($e146.tid == null ||
                                  !$e146.tid.isDescendantOf($currentTid147)) {
                                throw $e146;
                            }
                            throw new fabric.worker.UserAbortException($e146);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e146) {
                            $commit143 = false;
                            fabric.common.TransactionID $currentTid147 =
                              $tm148.getCurrentTid();
                            if ($e146.tid.isDescendantOf($currentTid147))
                                continue $label142;
                            if ($currentTid147.parent != null) {
                                $retry144 = false;
                                throw $e146;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e146) {
                            $commit143 = false;
                            $retry144 = false;
                            if ($tm148.inNestedTxn()) { $keepReads145 = true; }
                            throw $e146;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid147 =
                              $tm148.getCurrentTid();
                            if ($commit143) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e146) {
                                    $commit143 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e146) {
                                    $commit143 = false;
                                    $retry144 = false;
                                    $keepReads145 = $e146.keepReads;
                                    if ($e146.tid ==
                                          null ||
                                          !$e146.tid.isDescendantOf(
                                                       $currentTid147))
                                        throw $e146;
                                    throw new fabric.worker.UserAbortException(
                                            $e146);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e146) {
                                    $commit143 = false;
                                    $currentTid147 = $tm148.getCurrentTid();
                                    if ($currentTid147 != null) {
                                        if ($e146.tid.equals($currentTid147) ||
                                              !$e146.tid.isDescendantOf(
                                                           $currentTid147)) {
                                            throw $e146;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm148.inNestedTxn() &&
                                      $tm148.checkForStaleObjects()) {
                                    $retry144 = true;
                                    $keepReads145 = false;
                                }
                                if ($keepReads145) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e146) {
                                        $currentTid147 = $tm148.getCurrentTid();
                                        if ($currentTid147 != null &&
                                              ($e146.tid.equals($currentTid147) || !$e146.tid.isDescendantOf($currentTid147))) {
                                            throw $e146;
                                        } else {
                                            $retry144 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit143) {
                                { val = val$var141; }
                                if ($retry144) { continue $label142; }
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
                    fabric.metrics.DerivedMetric val$var152 = val;
                    fabric.worker.transaction.TransactionManager $tm159 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled162 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff160 = 1;
                    boolean $doBackoff161 = true;
                    boolean $retry155 = true;
                    boolean $keepReads156 = false;
                    $label153: for (boolean $commit154 = false; !$commit154; ) {
                        if ($backoffEnabled162) {
                            if ($doBackoff161) {
                                if ($backoff160 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff160));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e157) {
                                            
                                        }
                                    }
                                }
                                if ($backoff160 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff160 =
                                      java.lang.Math.
                                        min(
                                          $backoff160 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff161 = $backoff160 <= 32 || !$doBackoff161;
                        }
                        $commit154 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(terms);
                        }
                        catch (final fabric.worker.RetryException $e157) {
                            $commit154 = false;
                            continue $label153;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e157) {
                            $commit154 = false;
                            $retry155 = false;
                            $keepReads156 = $e157.keepReads;
                            fabric.common.TransactionID $currentTid158 =
                              $tm159.getCurrentTid();
                            if ($e157.tid == null ||
                                  !$e157.tid.isDescendantOf($currentTid158)) {
                                throw $e157;
                            }
                            throw new fabric.worker.UserAbortException($e157);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e157) {
                            $commit154 = false;
                            fabric.common.TransactionID $currentTid158 =
                              $tm159.getCurrentTid();
                            if ($e157.tid.isDescendantOf($currentTid158))
                                continue $label153;
                            if ($currentTid158.parent != null) {
                                $retry155 = false;
                                throw $e157;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e157) {
                            $commit154 = false;
                            $retry155 = false;
                            if ($tm159.inNestedTxn()) { $keepReads156 = true; }
                            throw $e157;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid158 =
                              $tm159.getCurrentTid();
                            if ($commit154) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e157) {
                                    $commit154 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e157) {
                                    $commit154 = false;
                                    $retry155 = false;
                                    $keepReads156 = $e157.keepReads;
                                    if ($e157.tid ==
                                          null ||
                                          !$e157.tid.isDescendantOf(
                                                       $currentTid158))
                                        throw $e157;
                                    throw new fabric.worker.UserAbortException(
                                            $e157);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e157) {
                                    $commit154 = false;
                                    $currentTid158 = $tm159.getCurrentTid();
                                    if ($currentTid158 != null) {
                                        if ($e157.tid.equals($currentTid158) ||
                                              !$e157.tid.isDescendantOf(
                                                           $currentTid158)) {
                                            throw $e157;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm159.inNestedTxn() &&
                                      $tm159.checkForStaleObjects()) {
                                    $retry155 = true;
                                    $keepReads156 = false;
                                }
                                if ($keepReads156) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e157) {
                                        $currentTid158 = $tm159.getCurrentTid();
                                        if ($currentTid158 != null &&
                                              ($e157.tid.equals($currentTid158) || !$e157.tid.isDescendantOf($currentTid158))) {
                                            throw $e157;
                                        } else {
                                            $retry155 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit154) {
                                { val = val$var152; }
                                if ($retry155) { continue $label153; }
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
                    fabric.metrics.DerivedMetric val$var163 = val;
                    fabric.worker.transaction.TransactionManager $tm170 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled173 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff171 = 1;
                    boolean $doBackoff172 = true;
                    boolean $retry166 = true;
                    boolean $keepReads167 = false;
                    $label164: for (boolean $commit165 = false; !$commit165; ) {
                        if ($backoffEnabled173) {
                            if ($doBackoff172) {
                                if ($backoff171 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff171));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e168) {
                                            
                                        }
                                    }
                                }
                                if ($backoff171 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff171 =
                                      java.lang.Math.
                                        min(
                                          $backoff171 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff172 = $backoff171 <= 32 || !$doBackoff172;
                        }
                        $commit165 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(terms);
                        }
                        catch (final fabric.worker.RetryException $e168) {
                            $commit165 = false;
                            continue $label164;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e168) {
                            $commit165 = false;
                            $retry166 = false;
                            $keepReads167 = $e168.keepReads;
                            fabric.common.TransactionID $currentTid169 =
                              $tm170.getCurrentTid();
                            if ($e168.tid == null ||
                                  !$e168.tid.isDescendantOf($currentTid169)) {
                                throw $e168;
                            }
                            throw new fabric.worker.UserAbortException($e168);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e168) {
                            $commit165 = false;
                            fabric.common.TransactionID $currentTid169 =
                              $tm170.getCurrentTid();
                            if ($e168.tid.isDescendantOf($currentTid169))
                                continue $label164;
                            if ($currentTid169.parent != null) {
                                $retry166 = false;
                                throw $e168;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e168) {
                            $commit165 = false;
                            $retry166 = false;
                            if ($tm170.inNestedTxn()) { $keepReads167 = true; }
                            throw $e168;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid169 =
                              $tm170.getCurrentTid();
                            if ($commit165) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e168) {
                                    $commit165 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e168) {
                                    $commit165 = false;
                                    $retry166 = false;
                                    $keepReads167 = $e168.keepReads;
                                    if ($e168.tid ==
                                          null ||
                                          !$e168.tid.isDescendantOf(
                                                       $currentTid169))
                                        throw $e168;
                                    throw new fabric.worker.UserAbortException(
                                            $e168);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e168) {
                                    $commit165 = false;
                                    $currentTid169 = $tm170.getCurrentTid();
                                    if ($currentTid169 != null) {
                                        if ($e168.tid.equals($currentTid169) ||
                                              !$e168.tid.isDescendantOf(
                                                           $currentTid169)) {
                                            throw $e168;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm170.inNestedTxn() &&
                                      $tm170.checkForStaleObjects()) {
                                    $retry166 = true;
                                    $keepReads167 = false;
                                }
                                if ($keepReads167) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e168) {
                                        $currentTid169 = $tm170.getCurrentTid();
                                        if ($currentTid169 != null &&
                                              ($e168.tid.equals($currentTid169) || !$e168.tid.isDescendantOf($currentTid169))) {
                                            throw $e168;
                                        } else {
                                            $retry166 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit165) {
                                { val = val$var163; }
                                if ($retry166) { continue $label164; }
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
            this.set$treatiesBox(
                   ((fabric.metrics.util.TreatiesBox)
                      new fabric.metrics.util.TreatiesBox._Impl(
                        this.$getStore()).$getProxy(
                                            )).fabric$metrics$util$TreatiesBox$(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy()));
            fabric$metrics$util$AbstractSubject$();
            this.set$proxies(fabric.worker.metrics.proxies.ProxyMap.emptyMap());
            this.
              set$$associates(
                fabric.worker.metrics.ImmutableObjectSet.emptySet().
                    add(this.get$treatiesBox()));
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
            this.set$$associates(this.get$$associates().add(result));
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
   * Used to construct and enforce {@link Treaty}s bounding this
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
          equalityPolicy(double value, long time,
                         fabric.worker.metrics.StatsMap weakStats,
                         final fabric.worker.Store s) {
            return fabric.worker.metrics.treaties.enforcement.DirectPolicy.
                     singleton;
        }
        
        /**
   * Used to construct and enforce {@link Treaty}s bounding this
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
            return equalityPolicy(value, 0, weakStats, s);
        }
        
        /**
   * Used to construct and enforce {@link Treaty}s bounding this
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
   * Used to construct and enforce {@link Treaty}s bounding this
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
          thresholdPolicy(double rate, double base, long time,
                          fabric.worker.metrics.StatsMap weakStats,
                          final fabric.worker.Store s) {
            return fabric.worker.metrics.treaties.enforcement.DirectPolicy.
                     singleton;
        }
        
        /**
   * Used to construct and enforce {@link Treaty}s bounding this
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
            return thresholdPolicy(rate, base,
                                   java.lang.System.currentTimeMillis(),
                                   weakStats, s);
        }
        
        /**
   * Used to construct and enforce {@link Treaty}s bounding this
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
                    fabric.worker.metrics.StatsMap result$var174 = result;
                    fabric.worker.transaction.TransactionManager $tm181 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled184 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff182 = 1;
                    boolean $doBackoff183 = true;
                    boolean $retry177 = true;
                    boolean $keepReads178 = false;
                    $label175: for (boolean $commit176 = false; !$commit176; ) {
                        if ($backoffEnabled184) {
                            if ($doBackoff183) {
                                if ($backoff182 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff182));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e179) {
                                            
                                        }
                                    }
                                }
                                if ($backoff182 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff182 =
                                      java.lang.Math.
                                        min(
                                          $backoff182 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff183 = $backoff182 <= 32 || !$doBackoff183;
                        }
                        $commit176 = true;
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
                        catch (final fabric.worker.RetryException $e179) {
                            $commit176 = false;
                            continue $label175;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e179) {
                            $commit176 = false;
                            $retry177 = false;
                            $keepReads178 = $e179.keepReads;
                            fabric.common.TransactionID $currentTid180 =
                              $tm181.getCurrentTid();
                            if ($e179.tid == null ||
                                  !$e179.tid.isDescendantOf($currentTid180)) {
                                throw $e179;
                            }
                            throw new fabric.worker.UserAbortException($e179);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e179) {
                            $commit176 = false;
                            fabric.common.TransactionID $currentTid180 =
                              $tm181.getCurrentTid();
                            if ($e179.tid.isDescendantOf($currentTid180))
                                continue $label175;
                            if ($currentTid180.parent != null) {
                                $retry177 = false;
                                throw $e179;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e179) {
                            $commit176 = false;
                            $retry177 = false;
                            if ($tm181.inNestedTxn()) { $keepReads178 = true; }
                            throw $e179;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid180 =
                              $tm181.getCurrentTid();
                            if ($commit176) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e179) {
                                    $commit176 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e179) {
                                    $commit176 = false;
                                    $retry177 = false;
                                    $keepReads178 = $e179.keepReads;
                                    if ($e179.tid ==
                                          null ||
                                          !$e179.tid.isDescendantOf(
                                                       $currentTid180))
                                        throw $e179;
                                    throw new fabric.worker.UserAbortException(
                                            $e179);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e179) {
                                    $commit176 = false;
                                    $currentTid180 = $tm181.getCurrentTid();
                                    if ($currentTid180 != null) {
                                        if ($e179.tid.equals($currentTid180) ||
                                              !$e179.tid.isDescendantOf(
                                                           $currentTid180)) {
                                            throw $e179;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm181.inNestedTxn() &&
                                      $tm181.checkForStaleObjects()) {
                                    $retry177 = true;
                                    $keepReads178 = false;
                                }
                                if ($keepReads178) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e179) {
                                        $currentTid180 = $tm181.getCurrentTid();
                                        if ($currentTid180 != null &&
                                              ($e179.tid.equals($currentTid180) || !$e179.tid.isDescendantOf($currentTid180))) {
                                            throw $e179;
                                        } else {
                                            $retry177 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit176) {
                                { result = result$var174; }
                                if ($retry177) { continue $label175; }
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
        
        public fabric.metrics.util.TreatiesBox get$treatiesBox() {
            return this.treatiesBox;
        }
        
        public fabric.metrics.util.TreatiesBox set$treatiesBox(
          fabric.metrics.util.TreatiesBox val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.treatiesBox = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.util.TreatiesBox treatiesBox;
        
        /**
   * @return a {@link Treaty} asserting this metric satisfies the
   *       given statement.
   */
        public fabric.metrics.treaties.Treaty createTreaty(
          fabric.worker.metrics.treaties.statements.TreatyStatement stmt) {
            return createTreaty(stmt,
                                fabric.worker.metrics.StatsMap.emptyStats());
        }
        
        public fabric.metrics.treaties.Treaty createTreaty_remote(
          fabric.lang.security.Principal p,
          fabric.worker.metrics.treaties.statements.TreatyStatement stmt) {
            return createTreaty(stmt);
        }
        
        /**
   * @return a {@link Treaty} asserting this metric satisfies the
   *       given statement.
   */
        public fabric.metrics.treaties.Treaty createTreaty(
          fabric.worker.metrics.treaties.statements.TreatyStatement stmt,
          fabric.worker.metrics.StatsMap stats) {
            return fabric.metrics.Metric._Impl.createTreaty_static(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy(), stmt,
                                                 stats);
        }
        
        public fabric.metrics.treaties.Treaty createTreaty_remote(
          fabric.lang.security.Principal p,
          fabric.worker.metrics.treaties.statements.TreatyStatement stmt,
          fabric.worker.metrics.StatsMap stats) {
            return createTreaty(stmt, stats);
        }
        
        public static fabric.metrics.treaties.Treaty createTreaty_static(
          fabric.metrics.Metric tmp,
          fabric.worker.metrics.treaties.statements.TreatyStatement stmt,
          fabric.worker.metrics.StatsMap stats) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    stmt) instanceof fabric.worker.metrics.treaties.statements.ThresholdStatement) {
                return fabric.metrics.Metric._Impl.
                  createThresholdTreaty_static(
                    tmp,
                    ((fabric.worker.metrics.treaties.statements.ThresholdStatement)
                       stmt).rate(),
                    ((fabric.worker.metrics.treaties.statements.ThresholdStatement)
                       stmt).base(), 0, stats);
            }
            else if (fabric.lang.Object._Proxy.
                       $getProxy(
                         stmt) instanceof fabric.worker.metrics.treaties.statements.EqualityStatement) {
                return fabric.metrics.Metric._Impl.
                  createEqualityTreaty_static(
                    tmp,
                    ((fabric.worker.metrics.treaties.statements.EqualityStatement)
                       stmt).value(), stats);
            }
            else {
                throw new java.lang.InternalError("Unknown statement type.");
            }
        }
        
        /**
   * @param bound
   *        the bound that the treaty will enforce on this
   *        {@link Metric}
   * @return a {@link Treaty} asserting this metric satisfies the
   *       given bound.
   */
        public fabric.metrics.treaties.Treaty createThresholdTreaty(double rate,
                                                                    double base,
                                                                    long time) {
            return fabric.metrics.Metric._Impl.
              createThresholdTreaty_static(
                (fabric.metrics.Metric) this.$getProxy(), rate, base, time,
                fabric.worker.metrics.StatsMap.emptyStats());
        }
        
        public fabric.metrics.treaties.Treaty createThresholdTreaty_remote(
          fabric.lang.security.Principal p, double rate, double base,
          long time) {
            return createThresholdTreaty(rate, base, time);
        }
        
        /**
   * @param bound
   *        the bound that the treaty will enforce on this
   *        {@link Metric}
   * @return a {@link Treaty} asserting this metric satisfies the
   *       given bound.
   */
        public fabric.metrics.treaties.Treaty createThresholdTreaty(
          double rate, double base, long time,
          fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.Metric._Impl.createThresholdTreaty_static(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy(), rate, base,
                                                 time, weakStats);
        }
        
        public fabric.metrics.treaties.Treaty createThresholdTreaty_remote(
          fabric.lang.security.Principal p, double rate, double base, long time,
          fabric.worker.metrics.StatsMap weakStats) {
            return createThresholdTreaty(rate, base, time, weakStats);
        }
        
        public static fabric.metrics.treaties.Treaty
          createThresholdTreaty_static(
          fabric.metrics.Metric tmp, double rate, double base, long time,
          fabric.worker.metrics.StatsMap weakStats) {
            return tmp.
              get$treatiesBox().
              create(
                fabric.worker.metrics.treaties.statements.ThresholdStatement.
                    create(rate, base, time),
                weakStats);
        }
        
        /**
   * @param bound
   *        the bound that the treaty will enforce on this
   *        {@link Metric}
   * @return a {@link Treaty} asserting this metric satisfies the
   *       given bound.
   */
        public fabric.metrics.treaties.Treaty createEqualityTreaty(
          double value) {
            return fabric.metrics.Metric._Impl.
              createEqualityTreaty_static(
                (fabric.metrics.Metric) this.$getProxy(), value,
                fabric.worker.metrics.StatsMap.emptyStats());
        }
        
        public fabric.metrics.treaties.Treaty createEqualityTreaty_remote(
          fabric.lang.security.Principal p, double value) {
            return createEqualityTreaty(value);
        }
        
        /**
   * @param bound
   *        the bound that the treaty will enforce on this
   *        {@link Metric}
   * @return a {@link Treaty} asserting this metric satisfies the
   *       given bound.
   */
        public fabric.metrics.treaties.Treaty createEqualityTreaty(
          double value, fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.Metric._Impl.createEqualityTreaty_static(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy(), value,
                                                 weakStats);
        }
        
        public fabric.metrics.treaties.Treaty createEqualityTreaty_remote(
          fabric.lang.security.Principal p, double value,
          fabric.worker.metrics.StatsMap weakStats) {
            return createEqualityTreaty(value, weakStats);
        }
        
        public static fabric.metrics.treaties.Treaty
          createEqualityTreaty_static(
          fabric.metrics.Metric tmp, double value,
          fabric.worker.metrics.StatsMap weakStats) {
            return tmp.
              get$treatiesBox().
              create(
                fabric.worker.metrics.treaties.statements.EqualityStatement.
                    create(value),
                weakStats);
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
        
        /**
   * Create and activate a threshold treaty (purely for
   * updateWithPostcondition), possibly across all proxies of this metric.
   *
   * @param proactive
   *        flag indicating if the treaty should also be proactively established
   *        across all proxies of the metric.
   */
        public void createAndActivateTreaty(
          fabric.worker.metrics.treaties.statements.TreatyStatement stmt,
          boolean proactive) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    stmt) instanceof fabric.worker.metrics.treaties.statements.ThresholdStatement) {
                double rate =
                  ((fabric.worker.metrics.treaties.statements.ThresholdStatement)
                     stmt).rate();
                double base =
                  ((fabric.worker.metrics.treaties.statements.ThresholdStatement)
                     stmt).base();
                createThresholdTreaty(rate, base, 0);
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
                          createThresholdTreaty(rate, base, 0);
                    }
                }
            }
            else if (fabric.lang.Object._Proxy.
                       $getProxy(
                         stmt) instanceof fabric.worker.metrics.treaties.statements.EqualityStatement) {
                double value =
                  ((fabric.worker.metrics.treaties.statements.EqualityStatement)
                     stmt).value();
                createEqualityTreaty(value);
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
                          createEqualityTreaty(value);
                    }
                }
            }
        }
        
        public void assertPostcondition(
          fabric.worker.metrics.treaties.statements.TreatyStatement stmt) {
            fabric.metrics.treaties.Treaty existing =
              this.get$treatiesBox().get(stmt);
            if (!fabric.lang.Object._Proxy.idEquals(existing, null) &&
                  !existing.invalid()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  addTreatiedPostcondition(existing);
            }
            else {
                fabric.worker.transaction.TransactionManager.getInstance().
                  addUntreatiedPostcondition((fabric.metrics.Metric)
                                               this.$getProxy(), stmt);
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
            $writeRef($getStore(), this.treatiesBox, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.proxies = (fabric.worker.metrics.proxies.ProxyMap)
                             in.readObject();
            this.treatiesBox =
              (fabric.metrics.util.TreatiesBox)
                $readRef(fabric.metrics.util.TreatiesBox._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.Metric._Impl src = (fabric.metrics.Metric._Impl)
                                                other;
            this.proxies = src.proxies;
            this.treatiesBox = src.treatiesBox;
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
                return new fabric.metrics.Metric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 108, 93, -6, 81, 61,
    -107, -59, 95, 18, 4, 47, -63, -62, -34, 57, -79, -25, -38, 75, -114, 0,
    -104, 40, 70, 114, 38, -123, -108, -94, -24, 68, 19 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556552347000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1cCXgcxZV+0yPLkixb8oVtWQhhhMEYawIhBlvmsIUPYdmWJR9gY8utmZY0Vs/00NMjj9g1RwKf+QifF7BxnAOzLOQjgGPuAEtMIFkOcyfsAiFcIRjYOIawBJaAs+x71W8O9XSXphPp+/r9re6qV++vV/Xq6O7ZexhGJE2Y1q12RfVGayChJRsXqV0trW2qmdQizbqaTK7Cq53hUSUtuz68LVKngNIKlWE1bsSjYVXvjCctGNO6We1XQ3HNCq1ub2laD+VhyrhETfZaoKxfkDahPmHoAz26YXEhBfpvmBna+b2N1fcGoWodVEXjHZZqRcPNRtzS0tY6qIxpsS7NTM6PRLTIOhgb17RIh2ZGVT16MSY04utgXDLaE1etlKkl27WkofdTwnHJVEIzRZmZi2S+gWabqbBlmGh+tW1+yorqodZo0mpqhdLuqKZHkhfBJVDSCiO6dbUHEx7VmmEREhpDi+g6Jq+IoplmtxrWMllK+qLxiAXHOHNkGTcsxQSYdWRMs3qNbFElcRUvwDjbJF2N94Q6LDMa78GkI4wUlmJBjadSTFSWUMN9ao/WacFkZ7o2+xamKhfVQlksmOhMJjShz2ocPsvz1uHl87b/U3xJXIEA2hzRwjrZX4aZ6hyZ2rVuzdTiYc3OWHlS6y71qP1XKQCYeKIjsZ3mwX/+5JyT6x572k4z1SXNiq7NWtjqDN/aNebXtc0z5gTJjLKEkYxSUxjEXHi1je80pRPY2o/KaqSbjZmbj7U/ecFld2iHFKhogdKwoadi2KrGho1YIqpr5mItrpmqpUVaoFyLR5rF/RYYieet0bhmX13R3Z3UrBYo0cWlUkP8j1XUjSqoikbieTTebWTOE6rVK87TCQCoxgMCAEENqU/D8xMBRqCLWkK9RkwLdekpbQs27xAemmqGe0PYb81oeFbYSAyEkmY4ZKbiVhRT2tdD2JQQkqFlAhvRiMRwKkuT5dVbAgGs1GPCRkTrUpPoIW4tC9p07BBLDD2imZ1hffv+Fhi///uixZRTK09iSxV1EkAv1zrjQ37enakFCz/Z1/ms3dooL1cZNl7buEY2rtE2Du2ppA7UiCGpEUPS3kC6sXlPy52inZQmRYfKqqhEFXMTump1G2YsDYGA4DNB5BcNBN3bh2EDI0PljI4N5226aloQ3ZPYUkLOwqQNzn6Siy4teKZi4+8MV2378PO7dm01cj3GgoaCjlyYkzriNGflmEZYi2Cgy6k/qV59oHP/1gaFgkg5xjdLxRaIwaLOWcagDtmUCW5UGyNaYRTVgarTrUxEqrB6TWNL7opw+hgS42z/U2U5DBRx8cyOxI2vvfDf3xQjRiaEVuXF2g7NasrrtqSsSnTQsbm6X2VqGqZ7c3fbjhsOb1svKh5THOdWYAPJZuyuKvZTw7zy6Yt++/Zbt/6nknOWBaWJVJceDacFl7Ff418Aj/+jg/oeXSDECNzM/b4+2/ETVPL0nG0YAnQMQ2h6smF1PGZEot1RtUvXqKUcqTr+lAf+tL3adreOV+zKM+HkoRXkrk9ZAJc9u/F/64SaQJiGoFz95ZLZcW18TvN801QHyI705b85+vtPqTdiy8eolIxerIlAA6I+QDjwVFEXs4Q8xXHvNBLT7NqqFdeVZGGMX0SDZa4trgvt/VFN81mH7K6ebYuk41iXrr5Gzesmp94R+0yZVvqEAiPXQbUYp9W4tUbFYIXNYB2OtMlmvtgKowfdHzxq2kNEU7av1Tr7QV6xzl6QCzF4TqnpvMJu+HbDwYqopEqagY0KY3VoFmM13R2fIDkhHQBxMldkOU7I6SRmZBpjecI0LLRSi6SzahVSO4rVVTGW5qm1YCRmS0e1pMgz0YLpHAO3GGafZmZDIaeiaJEeWKYmRPIpznhnd2GSs7M2VJENJ6At9wFs3Me4y4XaQndqCp2ehPyisVjKohYtiplpwSjL1HC6piUXGOmM+cc4Qnim32fSUbIaYWZaXlyZ2oXRUw1budoUf1U8kEYYV+cxyWvZgYxBduinVtTI0QQZZGqvnGpPN3Dqm05jVzjaa44k5ne3fnvnnsiKH59iz2TGDZ53LIynYj995W/PNe5+54DLyFZuGYlZutav6Xk2LsYijy2YrC8TU8hcJ3rn0NFzmvsO9tjFHuMw0Zn69mV7DyyeHr5egWC2txTMWwdnahrcRypMDafd8VWDekp91gnl5IRJeHwDoPQpxofzm1OuERZ2E+FcR/8oYyUPMd7r9Kh7NItI7nWT2GjBiH6KJS5Brs2MxnCc6ueJrHbVzqu/bty+0/acPds/rmDCnZ/HnvGLwkaL7kDt51hZKSLHog/u2vrIT7ZuU9jQtTiIRYxUpkmuH1zNM/GYCzDydcaHPKqZhFpYqZTlQcZ7vCs1kHPNZqE1KanZFAmclI+gyWQ2aNU6ev25uJjDxZk9fxNd3kFPdOdFeJyFpq5grCmyFSkiakb7cRi3aAJIa0tHm6pmlVMYR3nTV3JhpzpXB5dL6uA7JAYw9tpFd4qqoGtb3JyIowqsBKi4ljHhz4mUxWCMFuXEaqH1agmBa0hcaeGCRU+5Gi6WLs14rBbDl8CK54t1j+jkJC5xeKWKNT3H+IvivWKT2ikhJYa07TgqsVe8uAmnHIuHDjD6Y8ZX/TmFsrzC+JIPp9wosf8mErstCMai8ZwFji4zDw+c8o1Zy3iyH59sdfNJNWuayXi0X5/cJuF0O4mbLahgn3hQy7pkB8C4txmf8ucSyvIk42M+XHK3xHwxEt1JLlHTnnaH8LgN4KhRNk486M9uyvIe41tF2W0HqIcldj9C4n4LxvZo1sKLUqoetQbEHGwgE7DrHAE7M5Ozp2oDlMo1ZJ+Hx51I9l8Y1wxL+yNNqxkXF9/+7Ip4QlIRog39woLJ3P4K6oPu73fz6mI8DgDUXMvY7s+rlGUl41JvRkFha1CQEULcEXtIM+166sMorRvxHlHmCxKqL5M4gMsm5Liq19SSvYYe8SYpvNmFx/MAU8+wseaDYfEmaXqf8TVv7iVCVUnOm1nxoij7dxKyb5L4Lwum5PxaBGexyDsbjw8A6t5gvF3i2DMLFnIiy08YbyqqqW7ONdX3JJTeJ/F20f4TI3QLHn/FyFlvY70v/3mO0KTpfcbfDtl2MxFl/OB1a4dlmNllVuEiVVSKHYA/klTKX0h8SDMtXKJp8y2hNUfMUR3YZwPjARraGacOR3UITTWMo4vy+Me4FJiav647D5egYhPHXm1sHLtZfWng4132is65MZ+X8M973z70m9FH7xP7gCW0H0vqK5xPNAofWAx6DiGoV2aJfYuINeGB1TSqkrHUgrZ/cCd50Jyfd6eHXWeeG/NGRfH/bBQB4M2MQInM1bh86Y7GVT2zkVGqa/Eeq9ct+Aaxbun067TT09mGn7fBgJFao/0FurUqk8DegYgajdlHXJkUaVci620iotS8QUbY5d1XAhMl9yaRGIe0w2RhxrDqnOX2BpttlCj5iERbLV38HGd1aiRSTJ9sBDjhFcb8mPn390nStIfxuuL6pO1EMr9BQo0sCNQhNZypFkMNI/DMdYzHDQs10jSNcaJfao0Sat8gcSJRU9MSauMow4loQCdAo8a4wje1FQ5qY1nTcsazvanlG32G5N5cEqfi4IAj5uqk1kbBxXLrwyO7DEPX1Lgb0+PRnk1ozzWMA8PClDSlGSWL9nw2CyX3aE4cOBtdh0xtmmI6utmLkIrF3sf4r8NCiDTdxHhtcYRWSO6tJNGST2iBlBBOVBtfZ/RaF/ojRJqeZHywOEIXSO6JmN2RT2iNlFAY140BGxs/GhZCpOkwo2QdmW90WHKPYkNgQz6h5W6ExJR6Jhbbh4TuYbzegxCJwim1yHId41XFWa5L7tEOQ6DHgqPsuUQDzyUa7ElEg1vMy+w/BPoBTpnDeKyEReGKT2SpZ6zxZpG3jv9YmJuSUNlCAqdRZcIJRnrAzXjRpk5FndsAvplmPH9Y2hRpWsu4ZEhOmWlFnfsTLHoHKOn55ErwvVRSF1eSGMhs6bu1xcyTicDVaO5LjL/050XK8jjjI8W1xWsk97aT2IYjUFKNJXiP/0Uv/+3EuXnQxtP+MCz+I03vMr5cTJsMXCbM3iWhtJvEtXJKwhO0ZvoeUoowdvjzBGVpZ2wtzhM3Se7dTOIHGM90NWmtTkRUS/N0Bq6QAjcDzL6F8bJhcQZpupQx4U2owBm3S1jdSeKWIVkJf1BwuhXg9BrGoD9/UBbFxtlHivPHvZJ795P4qQVjUsLqFlrBYs9269fCJ7hgDewDOCPBuNjDen8+IU2LGE/34ZOfS5g9SuJnRTHL9hMcOc94jvERD2YefqEs/854f3F+keyYBmh2FXgMh5x+TTfCUXvzydUj1EseAZhbYuOc3w+LR0jTO4y/9uGRFyWcSE/gwBCcsqPHo8hpHWOrBycPX1CWpYwLi/PFa5J7r5N4GYe8uBFNug552bHjaewh1zDqw+II0tTHeKEPR7wrIfQeiTdkhIQXaNX5O4CzdjPO8+cFytLE+C1vy/P2n+1dZ8GBzuyJ2SEJkcMk3sc+rvHThDZDj4azT1fmuc+Asg9ZtDjOecJaTItbjQtz53lKCuZHbvVErfTPAPMfZ/T3ZFlkMRgli1THM4r8KvpCUkVfkvi0oIro6sduZDAMK2jauRsZ/U2/RZZ6Rsn027kvL3goAW8eChEPHCmeh4nqpwAsWWHj4vf98aAsBxnf9uYxQlg4IsvDrQUrFRJalSRGWFBlZZ41DMGrB41qBFi6jXGGP16U5UTGacV3SkeLU8ZLKNFumTLGB6UOtOccgOXtjGX+KFGWkYyBovtPfrurlbCpIzHJB5u5QK/mQtsyxlp/bCjLVEbJvqMz3ivHSzicQKLeggmm1k0k1mpq38KkFY3hvCgpVLgR6UUr2tCKPzBe4Y8IZfkO49YhI0EmZGcWrWI3PKmFUyZ2dXqBKx6OJlTdPSjnakGy8arQa7fKDAumutVCp6nFDDFrd6+M09DGCwHaL2Vc5a8yKEsH4zIfXm2S8DmTxGyMiMynld6f1AfcKNABFAk3YW8rt7H9WQ8KrvMTr6fMQtMzjD8f0s2imBw9yWarQisL5WwLJvKz5aFZCkd1YSkDAGsOMco2wVwcRVmuY/xuMXSUUI7OcgmdNhItSGcwD0m7m0h552Ep2IvOP41xtAcdV6edRcI5qZzAmiptXPulj+Z4voTgOhLtFn0HFkukLG2N1/5Qltd3AS5oZ5w5LLxI00mMk33w6pLwipC4ELsZ8+rw3m8RzM7G8nfgpDDJ2DIszEjTEsbZPphJdmgV2qFVui37ayxk1irdvBDkFqIJPwRY/wPGzcNCjjRFGdf6INcvIScEfcvE5FYPuQuQ9d4tABc+yrhzWAiSph2Ml/sgeJmE4LdJXIwTk0x/kyyqs13uToANrzLePSzUSNNdjDf6oCZ5L1ah92KVK3KhZLl03T0DS78HYOMVjDE/vLzW3UKTzrjBm1e+2ZK3YhV6K1bZbsHoaLIjGu/RtcyD38A8t1HsVCz5YYDOVkavZ9okXEYxyjKNsdbbeMcDgzlDLJdpIBYL5MzriR3ZC6TB9VmCInnVVrmFxG7yM6nThngVcD0S2o+EDjLu8FcnlOV6xmu868Qxsit7hKV3SFjsJfFjC8bns8iN6+5kFmAZTwBseojxUn9kKMsljOmiyOzJdb37JGQeILGvWJf0ofIDAOpGGzcd8ceCsnzF+Jk3i9zKjV2SoyLZAFZoA1h5sHi/iJdJcIKuPI+MPmT8ke9g4vIyidD0Q8btxRAVk+R8ok9KiD5N4jEnUXva7Om689CctwAiOxj9vVgrsqxk9PNiLQnxXqki2SlWaKdYeYYGb5vR0O9hCk405XoXQOu2MeKzOVKWrxglzTG3NyKaYwExyVayQlvJyssW1LoSGypoUDc7DND9AqPXSzIe/ChLmtEshl/Bnpbg8HsJP3peqrzh23G0mfUJQM/tjF6rAQ9ilOUkxgZvYrnNugLH5dj9UcLuTyQO/h3eE6GFqv4zgN5eRhiW0IKaer5m/J9iuBe85Z3j/hcJ989JfOTJfYhog/OZIJ70TbBx8yFfDhZZ/sj4njdJ58cZyt8khL4m8YUFE2xCRX6PgJOQ4Ggk8ivGi/wRoSwJxs3eRByTEJtNsNSbTZD2TIMBC6a6sRkqrixCiyYDxEbaqD/jjxRlOcD4q2JI8XayMLxKQmosiQq/LjLQlFpk8yjj+f7YUJa1jCu92TgmJfmUJkso0TOR4Hj/fhIRZBMWWA8Qv4CxfDgiiNBUZmPsq2II5yIIE54mIUzxOFjrRXiIsDELC2wESNTZaHzpQdjDlZTlr4yfFhM2AlOEzZLXu4OzSEy36CcY6Ct2bRX96kyg3M365Vg0di3zCOOD/qynLD9jvNvbeuccf54w8zQJBdo/CoYsmGS7ZH48Mj9sic948z5Oc3781G9EI24kT0cLN6D6Shv73/JHkrK8yfiqN8mci+wFWfAcCb8FJJpwPqwmk5pptRlJK2zEI5nfQwmekbag1H4NkX7hY6rLj+zwzz2Fm/9Du/Xg0pMnevzAzuSCH+DifPv2VJVN2rP6VfuLkcxPOZW3Qll3Stfzfwkj77w0YWrdUVFp5UKOSQg+iy0YM/g7QUt8WUJnVAHBhXa685CWnY7+WyoqUjyDtT8hxBX+cW6/CjGff+ChI5X9+KBGFF+TMumHxvZ+OumL0rJV74iffME6rtc3fLnyzBue6BxXEnr8l2/NueeD15duh90nLjKnX7nz3z48d/z/AyTsVHkATQAA";
}
