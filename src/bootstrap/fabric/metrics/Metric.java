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
                    int $backoff105 = 1;
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
                                if ($backoff105 < 5000) $backoff105 *= 2;
                            }
                            $doBackoff106 = $backoff105 <= 32 || !$doBackoff106;
                        }
                        $commit99 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                val =
                                  ((fabric.metrics.ScaledMetric)
                                     new fabric.metrics.ScaledMetric._Impl(s).
                                     $getProxy()).fabric$metrics$ScaledMetric$(
                                                    scalar, tmp);
                            }
                            catch (final fabric.worker.RetryException $e102) {
                                throw $e102;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e102) {
                                throw $e102;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e102) {
                                throw $e102;
                            }
                            catch (final Throwable $e102) {
                                $tm104.getCurrentLog().checkRetrySignal();
                                throw $e102;
                            }
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
                            if ($tm104.checkForStaleObjects()) {
                                $retry100 = true;
                                $keepReads101 = false;
                                continue $label98;
                            }
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
                            if ($tm104.checkForStaleObjects())
                                continue $label98;
                            $retry100 = false;
                            throw new fabric.worker.AbortException($e102);
                        }
                        finally {
                            if ($commit99) {
                                fabric.common.TransactionID $currentTid103 =
                                  $tm104.getCurrentTid();
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
                                    if ($tm104.checkForStaleObjects()) {
                                        $retry100 = true;
                                        $keepReads101 = false;
                                        continue $label98;
                                    }
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
                            }
                            else if ($keepReads101) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                    int $backoff116 = 1;
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
                                if ($backoff116 < 5000) $backoff116 *= 2;
                            }
                            $doBackoff117 = $backoff116 <= 32 || !$doBackoff117;
                        }
                        $commit110 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                val =
                                  ((fabric.metrics.SumMetric)
                                     new fabric.metrics.SumMetric._Impl(s).
                                     $getProxy()).
                                    fabric$metrics$SumMetric$(
                                      new fabric.metrics.Metric[] { tmp,
                                        other });
                            }
                            catch (final fabric.worker.RetryException $e113) {
                                throw $e113;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e113) {
                                throw $e113;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e113) {
                                throw $e113;
                            }
                            catch (final Throwable $e113) {
                                $tm115.getCurrentLog().checkRetrySignal();
                                throw $e113;
                            }
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
                            if ($tm115.checkForStaleObjects()) {
                                $retry111 = true;
                                $keepReads112 = false;
                                continue $label109;
                            }
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
                            if ($tm115.checkForStaleObjects())
                                continue $label109;
                            $retry111 = false;
                            throw new fabric.worker.AbortException($e113);
                        }
                        finally {
                            if ($commit110) {
                                fabric.common.TransactionID $currentTid114 =
                                  $tm115.getCurrentTid();
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
                                    if ($tm115.checkForStaleObjects()) {
                                        $retry111 = true;
                                        $keepReads112 = false;
                                        continue $label109;
                                    }
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
                            }
                            else if ($keepReads112) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                        int $backoff127 = 1;
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
                                    if ($backoff127 < 5000) $backoff127 *= 2;
                                }
                                $doBackoff128 = $backoff127 <= 32 ||
                                                  !$doBackoff128;
                            }
                            $commit121 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    val =
                                      ((fabric.metrics.MinMetric)
                                         new fabric.metrics.MinMetric._Impl(s).
                                         $getProxy()).
                                        fabric$metrics$MinMetric$(
                                          new fabric.metrics.Metric[] { other,
                                            tmp });
                                }
                                catch (final fabric.worker.
                                         RetryException $e124) {
                                    throw $e124;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e124) {
                                    throw $e124;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e124) {
                                    throw $e124;
                                }
                                catch (final Throwable $e124) {
                                    $tm126.getCurrentLog().checkRetrySignal();
                                    throw $e124;
                                }
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
                                if ($tm126.checkForStaleObjects()) {
                                    $retry122 = true;
                                    $keepReads123 = false;
                                    continue $label120;
                                }
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
                                if ($tm126.checkForStaleObjects())
                                    continue $label120;
                                $retry122 = false;
                                throw new fabric.worker.AbortException($e124);
                            }
                            finally {
                                if ($commit121) {
                                    fabric.common.TransactionID $currentTid125 =
                                      $tm126.getCurrentTid();
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
                                        if ($tm126.checkForStaleObjects()) {
                                            $retry122 = true;
                                            $keepReads123 = false;
                                            continue $label120;
                                        }
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
                                } else if ($keepReads123) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
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
                        int $backoff138 = 1;
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
                                    if ($backoff138 < 5000) $backoff138 *= 2;
                                }
                                $doBackoff139 = $backoff138 <= 32 ||
                                                  !$doBackoff139;
                            }
                            $commit132 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    val =
                                      ((fabric.metrics.MinMetric)
                                         new fabric.metrics.MinMetric._Impl(s).
                                         $getProxy()).
                                        fabric$metrics$MinMetric$(
                                          new fabric.metrics.Metric[] { tmp,
                                            other });
                                }
                                catch (final fabric.worker.
                                         RetryException $e135) {
                                    throw $e135;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e135) {
                                    throw $e135;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e135) {
                                    throw $e135;
                                }
                                catch (final Throwable $e135) {
                                    $tm137.getCurrentLog().checkRetrySignal();
                                    throw $e135;
                                }
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
                                if ($tm137.checkForStaleObjects()) {
                                    $retry133 = true;
                                    $keepReads134 = false;
                                    continue $label131;
                                }
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
                                if ($tm137.checkForStaleObjects())
                                    continue $label131;
                                $retry133 = false;
                                throw new fabric.worker.AbortException($e135);
                            }
                            finally {
                                if ($commit132) {
                                    fabric.common.TransactionID $currentTid136 =
                                      $tm137.getCurrentTid();
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
                                        if ($tm137.checkForStaleObjects()) {
                                            $retry133 = true;
                                            $keepReads134 = false;
                                            continue $label131;
                                        }
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
                                } else if ($keepReads134) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
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
            fabric.metrics.treaties.Treaty mc = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                mc = tmp.createEqualityTreaty(value);
            }
            else {
                {
                    fabric.metrics.treaties.Treaty mc$var141 = mc;
                    fabric.worker.transaction.TransactionManager $tm148 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled151 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff149 = 1;
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
                                if ($backoff149 < 5000) $backoff149 *= 2;
                            }
                            $doBackoff150 = $backoff149 <= 32 || !$doBackoff150;
                        }
                        $commit143 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { mc = tmp.createEqualityTreaty(value); }
                            catch (final fabric.worker.RetryException $e146) {
                                throw $e146;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e146) {
                                throw $e146;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e146) {
                                throw $e146;
                            }
                            catch (final Throwable $e146) {
                                $tm148.getCurrentLog().checkRetrySignal();
                                throw $e146;
                            }
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
                            if ($tm148.checkForStaleObjects()) {
                                $retry144 = true;
                                $keepReads145 = false;
                                continue $label142;
                            }
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
                            if ($tm148.checkForStaleObjects())
                                continue $label142;
                            $retry144 = false;
                            throw new fabric.worker.AbortException($e146);
                        }
                        finally {
                            if ($commit143) {
                                fabric.common.TransactionID $currentTid147 =
                                  $tm148.getCurrentTid();
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
                                    if ($tm148.checkForStaleObjects()) {
                                        $retry144 = true;
                                        $keepReads145 = false;
                                        continue $label142;
                                    }
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
                            }
                            else if ($keepReads145) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit143) {
                                { mc = mc$var141; }
                                if ($retry144) { continue $label142; }
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
            fabric.metrics.treaties.Treaty mc = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                mc = tmp.createThresholdTreaty(rate, base, time);
            }
            else {
                {
                    fabric.metrics.treaties.Treaty mc$var152 = mc;
                    fabric.worker.transaction.TransactionManager $tm159 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled162 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff160 = 1;
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
                                if ($backoff160 < 5000) $backoff160 *= 2;
                            }
                            $doBackoff161 = $backoff160 <= 32 || !$doBackoff161;
                        }
                        $commit154 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                mc = tmp.createThresholdTreaty(rate, base,
                                                               time);
                            }
                            catch (final fabric.worker.RetryException $e157) {
                                throw $e157;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e157) {
                                throw $e157;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e157) {
                                throw $e157;
                            }
                            catch (final Throwable $e157) {
                                $tm159.getCurrentLog().checkRetrySignal();
                                throw $e157;
                            }
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
                            if ($tm159.checkForStaleObjects()) {
                                $retry155 = true;
                                $keepReads156 = false;
                                continue $label153;
                            }
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
                            if ($tm159.checkForStaleObjects())
                                continue $label153;
                            $retry155 = false;
                            throw new fabric.worker.AbortException($e157);
                        }
                        finally {
                            if ($commit154) {
                                fabric.common.TransactionID $currentTid158 =
                                  $tm159.getCurrentTid();
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
                                    if ($tm159.checkForStaleObjects()) {
                                        $retry155 = true;
                                        $keepReads156 = false;
                                        continue $label153;
                                    }
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
                            }
                            else if ($keepReads156) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit154) {
                                { mc = mc$var152; }
                                if ($retry155) { continue $label153; }
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
                    fabric.metrics.DerivedMetric val$var163 = val;
                    fabric.worker.transaction.TransactionManager $tm170 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled173 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff171 = 1;
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
                                if ($backoff171 < 5000) $backoff171 *= 2;
                            }
                            $doBackoff172 = $backoff171 <= 32 || !$doBackoff172;
                        }
                        $commit165 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                val =
                                  ((fabric.metrics.ScaledMetric)
                                     new fabric.metrics.ScaledMetric._Impl(s).
                                     $getProxy()).fabric$metrics$ScaledMetric$(
                                                    a, term);
                            }
                            catch (final fabric.worker.RetryException $e168) {
                                throw $e168;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e168) {
                                throw $e168;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e168) {
                                throw $e168;
                            }
                            catch (final Throwable $e168) {
                                $tm170.getCurrentLog().checkRetrySignal();
                                throw $e168;
                            }
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
                            if ($tm170.checkForStaleObjects()) {
                                $retry166 = true;
                                $keepReads167 = false;
                                continue $label164;
                            }
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
                            if ($tm170.checkForStaleObjects())
                                continue $label164;
                            $retry166 = false;
                            throw new fabric.worker.AbortException($e168);
                        }
                        finally {
                            if ($commit165) {
                                fabric.common.TransactionID $currentTid169 =
                                  $tm170.getCurrentTid();
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
                                    if ($tm170.checkForStaleObjects()) {
                                        $retry166 = true;
                                        $keepReads167 = false;
                                        continue $label164;
                                    }
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
                            }
                            else if ($keepReads167) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                    fabric.metrics.DerivedMetric val$var174 = val;
                    fabric.worker.transaction.TransactionManager $tm181 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled184 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff182 = 1;
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
                                if ($backoff182 < 5000) $backoff182 *= 2;
                            }
                            $doBackoff183 = $backoff182 <= 32 || !$doBackoff183;
                        }
                        $commit176 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                val =
                                  ((fabric.metrics.SumMetric)
                                     new fabric.metrics.SumMetric._Impl(s).
                                     $getProxy()).fabric$metrics$SumMetric$(
                                                    terms);
                            }
                            catch (final fabric.worker.RetryException $e179) {
                                throw $e179;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e179) {
                                throw $e179;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e179) {
                                throw $e179;
                            }
                            catch (final Throwable $e179) {
                                $tm181.getCurrentLog().checkRetrySignal();
                                throw $e179;
                            }
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
                            if ($tm181.checkForStaleObjects()) {
                                $retry177 = true;
                                $keepReads178 = false;
                                continue $label175;
                            }
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
                            if ($tm181.checkForStaleObjects())
                                continue $label175;
                            $retry177 = false;
                            throw new fabric.worker.AbortException($e179);
                        }
                        finally {
                            if ($commit176) {
                                fabric.common.TransactionID $currentTid180 =
                                  $tm181.getCurrentTid();
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
                                    if ($tm181.checkForStaleObjects()) {
                                        $retry177 = true;
                                        $keepReads178 = false;
                                        continue $label175;
                                    }
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
                            }
                            else if ($keepReads178) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit176) {
                                { val = val$var174; }
                                if ($retry177) { continue $label175; }
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
                    fabric.metrics.DerivedMetric val$var185 = val;
                    fabric.worker.transaction.TransactionManager $tm192 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled195 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff193 = 1;
                    boolean $doBackoff194 = true;
                    boolean $retry188 = true;
                    boolean $keepReads189 = false;
                    $label186: for (boolean $commit187 = false; !$commit187; ) {
                        if ($backoffEnabled195) {
                            if ($doBackoff194) {
                                if ($backoff193 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff193));
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
                        $commit187 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                val =
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    terms);
                            }
                            catch (final fabric.worker.RetryException $e190) {
                                throw $e190;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e190) {
                                throw $e190;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e190) {
                                throw $e190;
                            }
                            catch (final Throwable $e190) {
                                $tm192.getCurrentLog().checkRetrySignal();
                                throw $e190;
                            }
                        }
                        catch (final fabric.worker.RetryException $e190) {
                            $commit187 = false;
                            continue $label186;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e190) {
                            $commit187 = false;
                            $retry188 = false;
                            $keepReads189 = $e190.keepReads;
                            if ($tm192.checkForStaleObjects()) {
                                $retry188 = true;
                                $keepReads189 = false;
                                continue $label186;
                            }
                            fabric.common.TransactionID $currentTid191 =
                              $tm192.getCurrentTid();
                            if ($e190.tid == null ||
                                  !$e190.tid.isDescendantOf($currentTid191)) {
                                throw $e190;
                            }
                            throw new fabric.worker.UserAbortException($e190);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e190) {
                            $commit187 = false;
                            fabric.common.TransactionID $currentTid191 =
                              $tm192.getCurrentTid();
                            if ($e190.tid.isDescendantOf($currentTid191))
                                continue $label186;
                            if ($currentTid191.parent != null) {
                                $retry188 = false;
                                throw $e190;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e190) {
                            $commit187 = false;
                            if ($tm192.checkForStaleObjects())
                                continue $label186;
                            $retry188 = false;
                            throw new fabric.worker.AbortException($e190);
                        }
                        finally {
                            if ($commit187) {
                                fabric.common.TransactionID $currentTid191 =
                                  $tm192.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e190) {
                                    $commit187 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e190) {
                                    $commit187 = false;
                                    $retry188 = false;
                                    $keepReads189 = $e190.keepReads;
                                    if ($tm192.checkForStaleObjects()) {
                                        $retry188 = true;
                                        $keepReads189 = false;
                                        continue $label186;
                                    }
                                    if ($e190.tid ==
                                          null ||
                                          !$e190.tid.isDescendantOf(
                                                       $currentTid191))
                                        throw $e190;
                                    throw new fabric.worker.UserAbortException(
                                            $e190);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e190) {
                                    $commit187 = false;
                                    $currentTid191 = $tm192.getCurrentTid();
                                    if ($currentTid191 != null) {
                                        if ($e190.tid.equals($currentTid191) ||
                                              !$e190.tid.isDescendantOf(
                                                           $currentTid191)) {
                                            throw $e190;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads189) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit187) {
                                { val = val$var185; }
                                if ($retry188) { continue $label186; }
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
          equalityPolicy(double value, fabric.worker.metrics.StatsMap weakStats,
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
          thresholdPolicy(double rate, double base,
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
                    fabric.worker.metrics.StatsMap result$var196 = result;
                    fabric.worker.transaction.TransactionManager $tm203 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled206 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff204 = 1;
                    boolean $doBackoff205 = true;
                    boolean $retry199 = true;
                    boolean $keepReads200 = false;
                    $label197: for (boolean $commit198 = false; !$commit198; ) {
                        if ($backoffEnabled206) {
                            if ($doBackoff205) {
                                if ($backoff204 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff204));
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
                        $commit198 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                result =
                                  weakStats.put(
                                              tmp,
                                              tmp.computeValue(weakStats),
                                              tmp.computeSamples(weakStats),
                                              tmp.computeLastUpdate(weakStats),
                                              tmp.computeUpdateInterval(
                                                    weakStats),
                                              tmp.computeVelocity(weakStats),
                                              tmp.computeNoise(weakStats));
                            }
                            catch (final fabric.worker.RetryException $e201) {
                                throw $e201;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e201) {
                                throw $e201;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e201) {
                                throw $e201;
                            }
                            catch (final Throwable $e201) {
                                $tm203.getCurrentLog().checkRetrySignal();
                                throw $e201;
                            }
                        }
                        catch (final fabric.worker.RetryException $e201) {
                            $commit198 = false;
                            continue $label197;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e201) {
                            $commit198 = false;
                            $retry199 = false;
                            $keepReads200 = $e201.keepReads;
                            if ($tm203.checkForStaleObjects()) {
                                $retry199 = true;
                                $keepReads200 = false;
                                continue $label197;
                            }
                            fabric.common.TransactionID $currentTid202 =
                              $tm203.getCurrentTid();
                            if ($e201.tid == null ||
                                  !$e201.tid.isDescendantOf($currentTid202)) {
                                throw $e201;
                            }
                            throw new fabric.worker.UserAbortException($e201);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e201) {
                            $commit198 = false;
                            fabric.common.TransactionID $currentTid202 =
                              $tm203.getCurrentTid();
                            if ($e201.tid.isDescendantOf($currentTid202))
                                continue $label197;
                            if ($currentTid202.parent != null) {
                                $retry199 = false;
                                throw $e201;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e201) {
                            $commit198 = false;
                            if ($tm203.checkForStaleObjects())
                                continue $label197;
                            $retry199 = false;
                            throw new fabric.worker.AbortException($e201);
                        }
                        finally {
                            if ($commit198) {
                                fabric.common.TransactionID $currentTid202 =
                                  $tm203.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e201) {
                                    $commit198 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e201) {
                                    $commit198 = false;
                                    $retry199 = false;
                                    $keepReads200 = $e201.keepReads;
                                    if ($tm203.checkForStaleObjects()) {
                                        $retry199 = true;
                                        $keepReads200 = false;
                                        continue $label197;
                                    }
                                    if ($e201.tid ==
                                          null ||
                                          !$e201.tid.isDescendantOf(
                                                       $currentTid202))
                                        throw $e201;
                                    throw new fabric.worker.UserAbortException(
                                            $e201);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e201) {
                                    $commit198 = false;
                                    $currentTid202 = $tm203.getCurrentTid();
                                    if ($currentTid202 != null) {
                                        if ($e201.tid.equals($currentTid202) ||
                                              !$e201.tid.isDescendantOf(
                                                           $currentTid202)) {
                                            throw $e201;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads200) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit198) {
                                { result = result$var196; }
                                if ($retry199) { continue $label197; }
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
            fabric.metrics.treaties.Treaty t = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                t =
                  tmp.get$treatiesBox().get$treaties().
                    create(
                      fabric.worker.metrics.treaties.statements.ThresholdStatement.create(
                                                                                     rate,
                                                                                     base,
                                                                                     time),
                      weakStats);
            }
            else {
                {
                    fabric.metrics.treaties.Treaty t$var207 = t;
                    fabric.worker.transaction.TransactionManager $tm214 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled217 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff215 = 1;
                    boolean $doBackoff216 = true;
                    boolean $retry210 = true;
                    boolean $keepReads211 = false;
                    $label208: for (boolean $commit209 = false; !$commit209; ) {
                        if ($backoffEnabled217) {
                            if ($doBackoff216) {
                                if ($backoff215 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff215));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e212) {
                                            
                                        }
                                    }
                                }
                                if ($backoff215 < 5000) $backoff215 *= 2;
                            }
                            $doBackoff216 = $backoff215 <= 32 || !$doBackoff216;
                        }
                        $commit209 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                t =
                                  tmp.get$treatiesBox().get$treaties().
                                    create(
                                      fabric.worker.metrics.treaties.statements.ThresholdStatement.create(
                                                                                                     rate,
                                                                                                     base,
                                                                                                     time),
                                      weakStats);
                            }
                            catch (final fabric.worker.RetryException $e212) {
                                throw $e212;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e212) {
                                throw $e212;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e212) {
                                throw $e212;
                            }
                            catch (final Throwable $e212) {
                                $tm214.getCurrentLog().checkRetrySignal();
                                throw $e212;
                            }
                        }
                        catch (final fabric.worker.RetryException $e212) {
                            $commit209 = false;
                            continue $label208;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e212) {
                            $commit209 = false;
                            $retry210 = false;
                            $keepReads211 = $e212.keepReads;
                            if ($tm214.checkForStaleObjects()) {
                                $retry210 = true;
                                $keepReads211 = false;
                                continue $label208;
                            }
                            fabric.common.TransactionID $currentTid213 =
                              $tm214.getCurrentTid();
                            if ($e212.tid == null ||
                                  !$e212.tid.isDescendantOf($currentTid213)) {
                                throw $e212;
                            }
                            throw new fabric.worker.UserAbortException($e212);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e212) {
                            $commit209 = false;
                            fabric.common.TransactionID $currentTid213 =
                              $tm214.getCurrentTid();
                            if ($e212.tid.isDescendantOf($currentTid213))
                                continue $label208;
                            if ($currentTid213.parent != null) {
                                $retry210 = false;
                                throw $e212;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e212) {
                            $commit209 = false;
                            if ($tm214.checkForStaleObjects())
                                continue $label208;
                            $retry210 = false;
                            throw new fabric.worker.AbortException($e212);
                        }
                        finally {
                            if ($commit209) {
                                fabric.common.TransactionID $currentTid213 =
                                  $tm214.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e212) {
                                    $commit209 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e212) {
                                    $commit209 = false;
                                    $retry210 = false;
                                    $keepReads211 = $e212.keepReads;
                                    if ($tm214.checkForStaleObjects()) {
                                        $retry210 = true;
                                        $keepReads211 = false;
                                        continue $label208;
                                    }
                                    if ($e212.tid ==
                                          null ||
                                          !$e212.tid.isDescendantOf(
                                                       $currentTid213))
                                        throw $e212;
                                    throw new fabric.worker.UserAbortException(
                                            $e212);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e212) {
                                    $commit209 = false;
                                    $currentTid213 = $tm214.getCurrentTid();
                                    if ($currentTid213 != null) {
                                        if ($e212.tid.equals($currentTid213) ||
                                              !$e212.tid.isDescendantOf(
                                                           $currentTid213)) {
                                            throw $e212;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads211) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit209) {
                                { t = t$var207; }
                                if ($retry210) { continue $label208; }
                            }
                        }
                    }
                }
            }
            return t;
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
            fabric.metrics.treaties.Treaty t = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                t =
                  tmp.get$treatiesBox().get$treaties().
                    create(
                      fabric.worker.metrics.treaties.statements.EqualityStatement.create(
                                                                                    value),
                      weakStats);
            }
            else {
                {
                    fabric.metrics.treaties.Treaty t$var218 = t;
                    fabric.worker.transaction.TransactionManager $tm225 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled228 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff226 = 1;
                    boolean $doBackoff227 = true;
                    boolean $retry221 = true;
                    boolean $keepReads222 = false;
                    $label219: for (boolean $commit220 = false; !$commit220; ) {
                        if ($backoffEnabled228) {
                            if ($doBackoff227) {
                                if ($backoff226 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff226));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e223) {
                                            
                                        }
                                    }
                                }
                                if ($backoff226 < 5000) $backoff226 *= 2;
                            }
                            $doBackoff227 = $backoff226 <= 32 || !$doBackoff227;
                        }
                        $commit220 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                t =
                                  tmp.get$treatiesBox().get$treaties().
                                    create(
                                      fabric.worker.metrics.treaties.statements.EqualityStatement.create(
                                                                                                    value),
                                      weakStats);
                            }
                            catch (final fabric.worker.RetryException $e223) {
                                throw $e223;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e223) {
                                throw $e223;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e223) {
                                throw $e223;
                            }
                            catch (final Throwable $e223) {
                                $tm225.getCurrentLog().checkRetrySignal();
                                throw $e223;
                            }
                        }
                        catch (final fabric.worker.RetryException $e223) {
                            $commit220 = false;
                            continue $label219;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e223) {
                            $commit220 = false;
                            $retry221 = false;
                            $keepReads222 = $e223.keepReads;
                            if ($tm225.checkForStaleObjects()) {
                                $retry221 = true;
                                $keepReads222 = false;
                                continue $label219;
                            }
                            fabric.common.TransactionID $currentTid224 =
                              $tm225.getCurrentTid();
                            if ($e223.tid == null ||
                                  !$e223.tid.isDescendantOf($currentTid224)) {
                                throw $e223;
                            }
                            throw new fabric.worker.UserAbortException($e223);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e223) {
                            $commit220 = false;
                            fabric.common.TransactionID $currentTid224 =
                              $tm225.getCurrentTid();
                            if ($e223.tid.isDescendantOf($currentTid224))
                                continue $label219;
                            if ($currentTid224.parent != null) {
                                $retry221 = false;
                                throw $e223;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e223) {
                            $commit220 = false;
                            if ($tm225.checkForStaleObjects())
                                continue $label219;
                            $retry221 = false;
                            throw new fabric.worker.AbortException($e223);
                        }
                        finally {
                            if ($commit220) {
                                fabric.common.TransactionID $currentTid224 =
                                  $tm225.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e223) {
                                    $commit220 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e223) {
                                    $commit220 = false;
                                    $retry221 = false;
                                    $keepReads222 = $e223.keepReads;
                                    if ($tm225.checkForStaleObjects()) {
                                        $retry221 = true;
                                        $keepReads222 = false;
                                        continue $label219;
                                    }
                                    if ($e223.tid ==
                                          null ||
                                          !$e223.tid.isDescendantOf(
                                                       $currentTid224))
                                        throw $e223;
                                    throw new fabric.worker.UserAbortException(
                                            $e223);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e223) {
                                    $commit220 = false;
                                    $currentTid224 = $tm225.getCurrentTid();
                                    if ($currentTid224 != null) {
                                        if ($e223.tid.equals($currentTid224) ||
                                              !$e223.tid.isDescendantOf(
                                                           $currentTid224)) {
                                            throw $e223;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads222) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit220) {
                                { t = t$var218; }
                                if ($retry221) { continue $label219; }
                            }
                        }
                    }
                }
            }
            return t;
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
              this.get$treatiesBox().get$treaties().get(stmt);
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
    
    public static final byte[] $classHash = new byte[] { -57, 112, 114, 66, 24,
    79, 53, 113, -11, -100, 18, -39, -30, 7, 40, -74, -86, -76, -127, 54, 121,
    58, -49, 95, -110, -91, -58, -3, -66, -45, 69, 52 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549233147000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1cCXQVVZr+X2WBhCUhhH0Voy1boiIqiwtEkGiASBJQaAiV9ypJQeXVs+o+eHFr7RkGT48yNiKDjstxWo+tok47tMtpcZlWFO0Rl552GbfpUcc56KDHdjl9dJz/v/W/Je9V3bzqSc6p/6tU3fvf/7v/vf9dql7t/wzKXAdmdOmdplUv+hKGW79c72xqbtEd14g1WrrrtuHVjuiw0qa9n9wTm6qB1gzDo3rcjptR3eqIuwJGNm/Rt+kNcUM0tK9pWrQBKqKUcYXu9gjQNixNOTA9YVt93ZYtuJAC/TfNbtjz95uqHy6BqvVQZcZbhS7MaKMdF0ZKrIfhvUZvp+G4S2IxI7YeRsUNI9ZqOKZumZdhQju+Hmpcszuui6RjuGsM17a2UcIaN5kwHFlm+iKZb6PZTjIqbAfNr/bMTwrTamg2XbGoGcq7TMOKuZfCVVDaDGVdlt6NCcc2p1k0SI0Ny+k6Jq800UynS48a6SylW814TMC0/BwZxnUXYgLMOqTXED12pqjSuI4XoMYzydLj3Q2twjHj3Zi0zE5iKQImBirFREMTenSr3m10CBifn67Fu4WpKmS1UBYBY/KTSU3os4l5Psvx1merFu+6PL4irkEEbY4ZUYvsH4qZpuZlWmN0GY4RjxpexuGzmvfqYw9eqwFg4jF5ib00j17xxblzpj79gpdmkk+a1Z1bjKjoiN7VOfLVyY0zF5SQGUMTtmtSU+jHXHq1he8sSiWwtY/NaKSb9embT685dMnV9xlHNahsgvKobSV7sVWNitq9CdMynPONuOHowog1QYURjzXK+00wBM+bzbjhXV3d1eUaoglKLXmp3Jb/YxV1oQqqoiF4bsa77PR5Qhc98jyVAIBqPCACUHIGQKIRz08CKEMXNTX02L1GQ6eVNLZj827Aw9CdaE8D9lvHjM6N2om+BteJNjjJuDAxpXe9AZsSgtuwUmI9GpEYTGUpsrx6eySClTotaseMTt1FD3FrWdpiYYdYYVsxw+mIWrsONsHogzfLFlNBrdzFlirrJIJenpwfH3Lz7kkuXfbFgx0vea2N8nKVYeP1jKtn4+o949Ce4dSB6jEk1WNI2h9J1Tfe3nS/bCflruxQGRXDUcXChKWLLtvpTUEkIvnUyvyygaB7t2LYwMgwfGbrxgs2XzujBFtmYnspOQuT1uX3k2x0acIzHRt/R7Rq5ydfP7T3SjvbYwTUFXTkwpzUEWfkV45jR40YBrqs+lnT9V93HLyyTqMgUoHxTejYAjFYTM0vo1+HXJQOblQbZc0wjOpAt+hWOiJVih7H3p69Ip0+kkSN53+qrDwDZVw8qzVx25sv//c8OWKkQ2hVTqxtNcSinG5LyqpkBx2Vrfs2xzAw3bv7Wm686bOdG2TFY4rj/QqsI9mI3VXHfmo7O1649K3337vr91rWWQLKE8lOy4ymJJdRP+BfBI//pYP6Hl0gxAjcyP1+eqbjJ6jkE7O2YQiwMAyh6W5de7zXjpldpt5pGdRSvqs64ZRff7qr2nO3hVe8ynNgzsAKstcnLIWrX9r0zVSpJhKlIShbf9lkXlwbndW8xHH0PrIjdc1rU25+Xr8NWz5GJde8zJCBBmR9gHTgqbIu5kp5St6900jM8GprsryuuYUxfjkNltm2uL5h/60TG88+6nX1TFskHcf5dPW1ek43OfW+3q+0GeXPaTBkPVTLcVqPi7U6BitsButxpHUb+WIzjOh3v/+o6Q0RizJ9bXJ+P8gpNr8XZEMMnlNqOq/0Gr7XcLAihlMlzcRGFQWov5vxWro7OkGyNhUBebJQZjleyhNJzEw3xoqEYwu00oilMmo1UjuM1e1kvCpHrYAhmC1lGq7MM0bAiRwDt9vOVsPJhEJORdEi1bdST8jkE/LjndeFSZ6esaGKbPgR2oLlr0swXuxDbZk/NY1OZyE/s7c3KahFy2JmCxgmHAOna4a71E6lzZ+WF8LT/T6djpJNlGam1MUN1TsxeupRka1N+VfFA2mMsT2HSU7LjqQN8kI/taJ6jibIIF17FVR7lo1T31QKu8KUoDmSnN/d9dM9t8dW332KN5Op6T/vWBZP9j7wh+9/V7/vg8M+I1uFsBNzLWObYeXYuAyLPK5gsr5STiGzneiDo1MWNG79qNsrdlqeifmp7125//D5J0Z3a1CS6S0F89b+mRb17yOVjoHT7nhbv54yPeOECnLCODxOBih/nvHx3OaUbYSF3UQ6N69/DGUljzE+nO9R/2gWU9zrIrFJQNk2iiU+Qa7FMXtxnNrGE1nj2j0/+6F+1x7Pc95s//iCCXduHm/GLwsbIbsDtZ/jVKXIHMv/66Erf/PLK3dqbOg6HMRidjLdJDf0r+bZeCwEGPI242MB1UxCL6xUyvIo46+CKzWSdc0WqdVV1GySBE7Ky2gymQlak/N6/Xm4mMPFmTd/k10+j57szsvxOBtNXc04schWpMmoaW7DYVzQBJDWlnltqppVTmAcFkxfy4ad6mwdXKOog78i0Yex1yu6Q1YFXdvu50QcVeAigMobGBPhnEhZbEazKCdWS60/UxC4jsQOgQsWK+lruFy60JKlXQ5fEiv/tVj3yE5O4qo8r1Sxpt8xPlW8VzxSexSk9pLYhaMSeyWIm3TKcXhYACOOMb4RzimU5Q+Mr4Rwym0K++8gsU9ASa8Zz1qQ12UW44Fj+Mh1jHPC+ORKP59Us6bZjFPC+uQeBad7SdwpoJJ9EkAt45IbAWreZ3w+nEsoyyHGp0O45J8U5suR6H5yiZ4KtLsBj3sAxg7zcMxH4eymLB8yvleU3V6Aelxh929IHBAwqtsQyy5N6pYp+uQcrC8dsKfmBez0TM6bqvVRKt+QfQEe9yPZv2NcOyjtjzS1M55ffPvzKuI5RUXINvSUgPHc/grqg+4f9PPq+Xi8ADBxDWNtOK9SltGMI4IZlUhbSyQZKeQduYc026unrRilLTveLct8WUH1dRKHcdmEHNt6HMPtsa1YMEnpzU48MBZP/DPjvYPiTdL0S8ZbgrmXSlWlWW9mxBFZ9r8ryL5L4t8ETMj6tQjOcpF3Dh4fAUxtYzxO4dizChZyMst0xnFFNdUt2ab6oYLSxyTeL9p/coRuwuMbgOn7GDvC+C9whCZNmxgvGrDtpiPK6P7r1lZhO5llVuEiVVaKF4D/R1EpfyLxCc20cIlmLBFSa5ZYXnVgn43gP8e/yHjjYFSH1LSbcUdRHj+GS4FJueu6C3AJKjdxvNXGplFb9Ff6ju31VnT5G/M5CT/f//7R10ZMeVDuA5bSfiypr8x/olH4wKLfcwhJfXiG2HwitggPDG3DhjOWC2j5f+4k95vz8+70oOvMcWPOqCj/Px1FBHgzI1KqcjUuX7rMuG6lNzLKLSPeLXr8gm8J1i2d/pDK93Sm4edsMGCkNmh/gW61pRN4OxCmXZ95xJVOkfIlssEjIkvNGWSkXcF9JTJGcY8iVaQGaUfJwrRh1VnLvQ02zyhZ8ncKbZPp4tc4q9NjsWL65ByAH61izI2Zf3mfJE1jGSuK65OeE8n8OgU1siAyFanhTLUYanjMeo3xHwaFGmm6hfH6sNTqFdROJnESUdNTCmo1lOEkNGAjwNx3GYPm4sHUVudRG8WaDjE+Gkwt1+gzFfcWkjgVBwccMdtdo4WCi/Drw0M6bdsy9Lgf0xPQHhzs6ks9nPvFoDAlTZ8z/kdxTJcp7tGcOHIOug6ZejTXULItQYQ6kNBsxvGDQog0jWMcWhyh1Yp7NK2INOUSWqoktBmLXcN4zqAQIk1nMzYUR+gSxT0Zs1tzCa1VEtKx2D7GrkEhRJoMxrXFEYoq7lFsiGzMJbTKj5CcUlNTM3EhPJOxMoAQicIptcxSwQjFWW4p7tEOQ6RbwFhvLlHHc4k6bxJR5xfz0vsPEQFw8q8Yb1awKFzxySz7GHcHs8hZxx+T5iYVVLaTwGnUUOkEO9XnZ7xsU6eizh0InzO+EmB8uDZFmo4wDrynkp5WTPV/gkXvALmBT64k358o6oKm3pG+9Ja+X1tMP5mIXAswbwXjwnBepCwLGOcV1xavU9zbRWInjkCu3pvgPf4jQf7DRcZplzNuGBT/kab1jBcW0yYjV0uz9yoo7SNxg5qS9MREVHkTFvwO40vhPEFZXmR8tjhP3KG4dyeJWzCeWbor2hMxXRiBzsAVUuQOgNMneTj/20FxBmn6hvGTEM64V8HqfhK/GJCV9Md01PqPyGo34+Xh/EFZLmMUxfnjYcW9AyQeEDAyKa1uohUs9my/fi19ggvWyH6AMz5hfCrA+nA+IU1PMj4UwidPKJg9SeKRophl+slDAGeexzgvgFmAXyjLqYxzginkWqjYMY3QTD/yNA452wzLjpre5pOvR6iXPA6w4ArGSwbFI6TpYsamEB45ouD0KonDA3DKjB5PYMmvMT4bwCnAF5Tlt4wHi/PFm4p7b5N4HYe8uG26vkNeZuzAZdWiUg8XfjgojiBN/8n4+xCO+KOCEFkWeUdFSHqBhqy3AM4u8/CsA+G8QFn+mfGBYMvz9t4jV9OZNyc7quDwGYmPsXsb/CChxbbMaObBymL/yU/m+YoRx+lO1Og14qJ+WfY8R0nB1MivipYjv6MA5+5mbA9XRZSljXFVcBXl72J7tfOtonb+TOLLgtqhq8f8eHSjerTqvBRj0CsA/jxklgmMo4N55DxqyDxlyPG3FglmpFEDiXwnoEqkN+UHoNSK9tQCnF/r4fIXw1GiLIcZf1t0683xj1apYDOcRFkINgvRlOkATVUerngvHBvK8i7jG8Fs8qOINlrBYQyJkQJqHaOLSKwz9K3LXGH24mjrShV+RHrQijokcjfj8nBEKMsyxnOCieTtDaeXQnKP1TWiSQe7BL0WFI+aCd3y7+/ZWpisqIUZJMYJmORXCx2O0WvLuaB/ZZyGNp4McOESxrHhKoOyjGGsCuHVmQo+s0nUYeRgPs30Vp7V50eBDliN5SONC19ivD6Agu+oF/TsUmq6jvGqYGZ5T6KZ3jwFvfkk5goYw08sB2YpHdWJpaxEsg8wtoVzFGVpZVxZDB1tSpbOYgWds0mcgXT681C0uzGUdzGWsg6g5WvGI2GcRmUWTFVqWdPLjE+EaI7LFQRXkDhX0K+LehNJYawN2nXI8OoAWFPr4UWfDQov0vQp49sheLUoeNHGsXYBdjPm1Rq8ipfMzsHycZBunc84bFCYkaZKD9d8G4LZegWzH5NoE95vfJBZs3JJLMlRQLcB2n7MGOptqkBypGk244QQ5BQvtGq0R6xtwv7G5NoHXFtmvLcdoP1qxlCv6wQSJE3tjI0hCMYVBEmD1oMTk3R/UyzVMl3uJwBrb2Z0BoUaabqUsSMEtZSC2mUknGwoWaVczc3E0ncArFvO2BCGV9BqTmqqZ5wWzCvX7J8q7v01iSsEjDDdVjPebRnpx4mRxX6jGK5QtesBLh7p4bpwr8jJLB8yDvyKXHrutWCAlRgNxHLtlX7prTVzgTT47lBrf6uolJ+T+BvyM6kzBnjBbAMS+jnWyT2M4VZwMksbY1ErODmya9dJS/cpWNAbW9qNAkbnssiO6/5klmIZ2BEvuYxxSTgylOVcxoVFkbku2/XuVJD5BYlbi3XJVlR+G8D66R5e8mQ4FpTlIKPi8XZ25cYuyVK5T0FlP4m7i/eLfEWBBrY7kdF9jJtCBxOfVxSkpo2MLcUQlZPkXKIHFEQfIfFgPlFv2hzougvQnIcBNrYzhntdU2YZzRjmdU0S8m1F7aCCEe1Va4/R4O0xGvjtPsnJRYMeAdh0kocbQzZHynKQUdEcs3sjsjkWEDukIPYCiWcETPYlNlDQ2ITGPQPQcQPjonD8KMtCxtOK4ZdLLdsUFRvHGm0cay+GdtxOtOo5gM1bPewIWg0EEKMsnzJ+HEysTBpa5ue4LDvF9rJGSwrt9b/AezK00I7dYQB9loeb/2VQQgtpeobxQDHcC94dznJX7ERrtBOtvRPIfYBoQ/OZdwCilzOuCedgynIRY1GPZb3XhzXFtrRGbUz7WECtR6jIt9xpEvIBQGyWh9E3wxGhLG8wvhpMJG8Swmz+pGDzNYljAib5sRkortDM+SiAEWc8JRwpynIy46xiSPF2sjT8ewWpH0h8G9ZFuIrUjgF0neChEfTgM4ANZXmS8bFgNnmTkhxKJeXBlEroVbCSSHg/yQiyGc36Epk9zZgYlAhCmmzGzmIIZyMIE65WEK4hURlEeICwMRcLROg5nbEmlCtlllGMit8z5qxNJ0ibJyj4TCJRK+iH/fTbaKONvmUSqfCzfhUWPR4nybUebgnauwuwnrK8zPhCUbFCzhcXSzNnKCjQG10lUwSM81yyJB5bEhXyx6E5P3nK/0nNNtuM+ZE8Ay1swE5X52H863AkKctXjMeKcZG3ICtRvLdbQu/tlszE+bDuuoYjWmxXRO14LP2VjZITUwLKvZfb6LsRk3w+3cIfEYo2Pmvc9dGFc8YEfLZlfMFnnTjfg7dXDR13e/sb3u8Q0h8IqmiGoV1Jy8r9vkLOeXnCMbpMWWkVUo5MSD7zBYzs/+szIX+vQGdUASXzvHRnIi0vHf23QFakfDzo/TANV/jH+31rYAl/NqA1mXmlfaIsfmLSoc9X7f9y3LflQ9s+kB8SwTqe/nzCWTp+9fxLv7q15q0/DjnpkfsOXHN638IjHbvvPvT9k68vO+3/ANY8/xlWSwAA";
}
