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
                    fabric.metrics.DerivedMetric val$var66 = val;
                    fabric.worker.transaction.TransactionManager $tm73 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled76 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff74 = 1;
                    boolean $doBackoff75 = true;
                    boolean $retry69 = true;
                    boolean $keepReads70 = false;
                    $label67: for (boolean $commit68 = false; !$commit68; ) {
                        if ($backoffEnabled76) {
                            if ($doBackoff75) {
                                if ($backoff74 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff74));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e71) {
                                            
                                        }
                                    }
                                }
                                if ($backoff74 < 5000) $backoff74 *= 2;
                            }
                            $doBackoff75 = $backoff74 <= 32 || !$doBackoff75;
                        }
                        $commit68 = true;
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
                            catch (final fabric.worker.RetryException $e71) {
                                throw $e71;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e71) {
                                throw $e71;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e71) {
                                throw $e71;
                            }
                            catch (final Throwable $e71) {
                                $tm73.getCurrentLog().checkRetrySignal();
                                throw $e71;
                            }
                        }
                        catch (final fabric.worker.RetryException $e71) {
                            $commit68 = false;
                            continue $label67;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e71) {
                            $commit68 = false;
                            $retry69 = false;
                            $keepReads70 = $e71.keepReads;
                            if ($tm73.checkForStaleObjects()) {
                                $retry69 = true;
                                $keepReads70 = false;
                                continue $label67;
                            }
                            fabric.common.TransactionID $currentTid72 =
                              $tm73.getCurrentTid();
                            if ($e71.tid == null ||
                                  !$e71.tid.isDescendantOf($currentTid72)) {
                                throw $e71;
                            }
                            throw new fabric.worker.UserAbortException($e71);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e71) {
                            $commit68 = false;
                            fabric.common.TransactionID $currentTid72 =
                              $tm73.getCurrentTid();
                            if ($e71.tid.isDescendantOf($currentTid72))
                                continue $label67;
                            if ($currentTid72.parent != null) {
                                $retry69 = false;
                                throw $e71;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e71) {
                            $commit68 = false;
                            if ($tm73.checkForStaleObjects()) continue $label67;
                            $retry69 = false;
                            throw new fabric.worker.AbortException($e71);
                        }
                        finally {
                            if ($commit68) {
                                fabric.common.TransactionID $currentTid72 =
                                  $tm73.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e71) {
                                    $commit68 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e71) {
                                    $commit68 = false;
                                    $retry69 = false;
                                    $keepReads70 = $e71.keepReads;
                                    if ($tm73.checkForStaleObjects()) {
                                        $retry69 = true;
                                        $keepReads70 = false;
                                        continue $label67;
                                    }
                                    if ($e71.tid ==
                                          null ||
                                          !$e71.tid.isDescendantOf(
                                                      $currentTid72))
                                        throw $e71;
                                    throw new fabric.worker.UserAbortException(
                                            $e71);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e71) {
                                    $commit68 = false;
                                    $currentTid72 = $tm73.getCurrentTid();
                                    if ($currentTid72 != null) {
                                        if ($e71.tid.equals($currentTid72) ||
                                              !$e71.tid.isDescendantOf(
                                                          $currentTid72)) {
                                            throw $e71;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads70) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit68) {
                                { val = val$var66; }
                                if ($retry69) { continue $label67; }
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
                    fabric.metrics.DerivedMetric val$var77 = val;
                    fabric.worker.transaction.TransactionManager $tm84 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled87 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff85 = 1;
                    boolean $doBackoff86 = true;
                    boolean $retry80 = true;
                    boolean $keepReads81 = false;
                    $label78: for (boolean $commit79 = false; !$commit79; ) {
                        if ($backoffEnabled87) {
                            if ($doBackoff86) {
                                if ($backoff85 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff85));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e82) {
                                            
                                        }
                                    }
                                }
                                if ($backoff85 < 5000) $backoff85 *= 2;
                            }
                            $doBackoff86 = $backoff85 <= 32 || !$doBackoff86;
                        }
                        $commit79 = true;
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
                            catch (final fabric.worker.RetryException $e82) {
                                throw $e82;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e82) {
                                throw $e82;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e82) {
                                throw $e82;
                            }
                            catch (final Throwable $e82) {
                                $tm84.getCurrentLog().checkRetrySignal();
                                throw $e82;
                            }
                        }
                        catch (final fabric.worker.RetryException $e82) {
                            $commit79 = false;
                            continue $label78;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e82) {
                            $commit79 = false;
                            $retry80 = false;
                            $keepReads81 = $e82.keepReads;
                            if ($tm84.checkForStaleObjects()) {
                                $retry80 = true;
                                $keepReads81 = false;
                                continue $label78;
                            }
                            fabric.common.TransactionID $currentTid83 =
                              $tm84.getCurrentTid();
                            if ($e82.tid == null ||
                                  !$e82.tid.isDescendantOf($currentTid83)) {
                                throw $e82;
                            }
                            throw new fabric.worker.UserAbortException($e82);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e82) {
                            $commit79 = false;
                            fabric.common.TransactionID $currentTid83 =
                              $tm84.getCurrentTid();
                            if ($e82.tid.isDescendantOf($currentTid83))
                                continue $label78;
                            if ($currentTid83.parent != null) {
                                $retry80 = false;
                                throw $e82;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e82) {
                            $commit79 = false;
                            if ($tm84.checkForStaleObjects()) continue $label78;
                            $retry80 = false;
                            throw new fabric.worker.AbortException($e82);
                        }
                        finally {
                            if ($commit79) {
                                fabric.common.TransactionID $currentTid83 =
                                  $tm84.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e82) {
                                    $commit79 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e82) {
                                    $commit79 = false;
                                    $retry80 = false;
                                    $keepReads81 = $e82.keepReads;
                                    if ($tm84.checkForStaleObjects()) {
                                        $retry80 = true;
                                        $keepReads81 = false;
                                        continue $label78;
                                    }
                                    if ($e82.tid ==
                                          null ||
                                          !$e82.tid.isDescendantOf(
                                                      $currentTid83))
                                        throw $e82;
                                    throw new fabric.worker.UserAbortException(
                                            $e82);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e82) {
                                    $commit79 = false;
                                    $currentTid83 = $tm84.getCurrentTid();
                                    if ($currentTid83 != null) {
                                        if ($e82.tid.equals($currentTid83) ||
                                              !$e82.tid.isDescendantOf(
                                                          $currentTid83)) {
                                            throw $e82;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads81) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit79) {
                                { val = val$var77; }
                                if ($retry80) { continue $label78; }
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
                        fabric.metrics.DerivedMetric val$var88 = val;
                        fabric.worker.transaction.TransactionManager $tm95 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled98 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff96 = 1;
                        boolean $doBackoff97 = true;
                        boolean $retry91 = true;
                        boolean $keepReads92 = false;
                        $label89: for (boolean $commit90 = false; !$commit90;
                                       ) {
                            if ($backoffEnabled98) {
                                if ($doBackoff97) {
                                    if ($backoff96 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff96));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e93) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff96 < 5000) $backoff96 *= 2;
                                }
                                $doBackoff97 = $backoff96 <= 32 ||
                                                 !$doBackoff97;
                            }
                            $commit90 = true;
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
                                         RetryException $e93) {
                                    throw $e93;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e93) {
                                    throw $e93;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e93) {
                                    throw $e93;
                                }
                                catch (final Throwable $e93) {
                                    $tm95.getCurrentLog().checkRetrySignal();
                                    throw $e93;
                                }
                            }
                            catch (final fabric.worker.RetryException $e93) {
                                $commit90 = false;
                                continue $label89;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e93) {
                                $commit90 = false;
                                $retry91 = false;
                                $keepReads92 = $e93.keepReads;
                                if ($tm95.checkForStaleObjects()) {
                                    $retry91 = true;
                                    $keepReads92 = false;
                                    continue $label89;
                                }
                                fabric.common.TransactionID $currentTid94 =
                                  $tm95.getCurrentTid();
                                if ($e93.tid == null ||
                                      !$e93.tid.isDescendantOf($currentTid94)) {
                                    throw $e93;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e93);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e93) {
                                $commit90 = false;
                                fabric.common.TransactionID $currentTid94 =
                                  $tm95.getCurrentTid();
                                if ($e93.tid.isDescendantOf($currentTid94))
                                    continue $label89;
                                if ($currentTid94.parent != null) {
                                    $retry91 = false;
                                    throw $e93;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e93) {
                                $commit90 = false;
                                if ($tm95.checkForStaleObjects())
                                    continue $label89;
                                $retry91 = false;
                                throw new fabric.worker.AbortException($e93);
                            }
                            finally {
                                if ($commit90) {
                                    fabric.common.TransactionID $currentTid94 =
                                      $tm95.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e93) {
                                        $commit90 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e93) {
                                        $commit90 = false;
                                        $retry91 = false;
                                        $keepReads92 = $e93.keepReads;
                                        if ($tm95.checkForStaleObjects()) {
                                            $retry91 = true;
                                            $keepReads92 = false;
                                            continue $label89;
                                        }
                                        if ($e93.tid ==
                                              null ||
                                              !$e93.tid.isDescendantOf(
                                                          $currentTid94))
                                            throw $e93;
                                        throw new fabric.worker.
                                                UserAbortException($e93);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e93) {
                                        $commit90 = false;
                                        $currentTid94 = $tm95.getCurrentTid();
                                        if ($currentTid94 != null) {
                                            if ($e93.tid.equals(
                                                           $currentTid94) ||
                                                  !$e93.tid.isDescendantOf(
                                                              $currentTid94)) {
                                                throw $e93;
                                            }
                                        }
                                    }
                                } else if ($keepReads92) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit90) {
                                    { val = val$var88; }
                                    if ($retry91) { continue $label89; }
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
                        fabric.metrics.DerivedMetric val$var99 = val;
                        fabric.worker.transaction.TransactionManager $tm106 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled109 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff107 = 1;
                        boolean $doBackoff108 = true;
                        boolean $retry102 = true;
                        boolean $keepReads103 = false;
                        $label100: for (boolean $commit101 = false; !$commit101;
                                        ) {
                            if ($backoffEnabled109) {
                                if ($doBackoff108) {
                                    if ($backoff107 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff107));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e104) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff107 < 5000) $backoff107 *= 2;
                                }
                                $doBackoff108 = $backoff107 <= 32 ||
                                                  !$doBackoff108;
                            }
                            $commit101 = true;
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
                                         RetryException $e104) {
                                    throw $e104;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e104) {
                                    throw $e104;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e104) {
                                    throw $e104;
                                }
                                catch (final Throwable $e104) {
                                    $tm106.getCurrentLog().checkRetrySignal();
                                    throw $e104;
                                }
                            }
                            catch (final fabric.worker.RetryException $e104) {
                                $commit101 = false;
                                continue $label100;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e104) {
                                $commit101 = false;
                                $retry102 = false;
                                $keepReads103 = $e104.keepReads;
                                if ($tm106.checkForStaleObjects()) {
                                    $retry102 = true;
                                    $keepReads103 = false;
                                    continue $label100;
                                }
                                fabric.common.TransactionID $currentTid105 =
                                  $tm106.getCurrentTid();
                                if ($e104.tid ==
                                      null ||
                                      !$e104.tid.isDescendantOf(
                                                   $currentTid105)) {
                                    throw $e104;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e104);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e104) {
                                $commit101 = false;
                                fabric.common.TransactionID $currentTid105 =
                                  $tm106.getCurrentTid();
                                if ($e104.tid.isDescendantOf($currentTid105))
                                    continue $label100;
                                if ($currentTid105.parent != null) {
                                    $retry102 = false;
                                    throw $e104;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e104) {
                                $commit101 = false;
                                if ($tm106.checkForStaleObjects())
                                    continue $label100;
                                $retry102 = false;
                                throw new fabric.worker.AbortException($e104);
                            }
                            finally {
                                if ($commit101) {
                                    fabric.common.TransactionID $currentTid105 =
                                      $tm106.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e104) {
                                        $commit101 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e104) {
                                        $commit101 = false;
                                        $retry102 = false;
                                        $keepReads103 = $e104.keepReads;
                                        if ($tm106.checkForStaleObjects()) {
                                            $retry102 = true;
                                            $keepReads103 = false;
                                            continue $label100;
                                        }
                                        if ($e104.tid ==
                                              null ||
                                              !$e104.tid.isDescendantOf(
                                                           $currentTid105))
                                            throw $e104;
                                        throw new fabric.worker.
                                                UserAbortException($e104);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e104) {
                                        $commit101 = false;
                                        $currentTid105 = $tm106.getCurrentTid();
                                        if ($currentTid105 != null) {
                                            if ($e104.tid.equals(
                                                            $currentTid105) ||
                                                  !$e104.tid.
                                                  isDescendantOf(
                                                    $currentTid105)) {
                                                throw $e104;
                                            }
                                        }
                                    }
                                } else if ($keepReads103) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit101) {
                                    { val = val$var99; }
                                    if ($retry102) { continue $label100; }
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
                    fabric.metrics.treaties.Treaty mc$var110 = mc;
                    fabric.worker.transaction.TransactionManager $tm117 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled120 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff118 = 1;
                    boolean $doBackoff119 = true;
                    boolean $retry113 = true;
                    boolean $keepReads114 = false;
                    $label111: for (boolean $commit112 = false; !$commit112; ) {
                        if ($backoffEnabled120) {
                            if ($doBackoff119) {
                                if ($backoff118 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff118));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e115) {
                                            
                                        }
                                    }
                                }
                                if ($backoff118 < 5000) $backoff118 *= 2;
                            }
                            $doBackoff119 = $backoff118 <= 32 || !$doBackoff119;
                        }
                        $commit112 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { mc = tmp.createEqualityTreaty(value); }
                            catch (final fabric.worker.RetryException $e115) {
                                throw $e115;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e115) {
                                throw $e115;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e115) {
                                throw $e115;
                            }
                            catch (final Throwable $e115) {
                                $tm117.getCurrentLog().checkRetrySignal();
                                throw $e115;
                            }
                        }
                        catch (final fabric.worker.RetryException $e115) {
                            $commit112 = false;
                            continue $label111;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e115) {
                            $commit112 = false;
                            $retry113 = false;
                            $keepReads114 = $e115.keepReads;
                            if ($tm117.checkForStaleObjects()) {
                                $retry113 = true;
                                $keepReads114 = false;
                                continue $label111;
                            }
                            fabric.common.TransactionID $currentTid116 =
                              $tm117.getCurrentTid();
                            if ($e115.tid == null ||
                                  !$e115.tid.isDescendantOf($currentTid116)) {
                                throw $e115;
                            }
                            throw new fabric.worker.UserAbortException($e115);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e115) {
                            $commit112 = false;
                            fabric.common.TransactionID $currentTid116 =
                              $tm117.getCurrentTid();
                            if ($e115.tid.isDescendantOf($currentTid116))
                                continue $label111;
                            if ($currentTid116.parent != null) {
                                $retry113 = false;
                                throw $e115;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e115) {
                            $commit112 = false;
                            if ($tm117.checkForStaleObjects())
                                continue $label111;
                            $retry113 = false;
                            throw new fabric.worker.AbortException($e115);
                        }
                        finally {
                            if ($commit112) {
                                fabric.common.TransactionID $currentTid116 =
                                  $tm117.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e115) {
                                    $commit112 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e115) {
                                    $commit112 = false;
                                    $retry113 = false;
                                    $keepReads114 = $e115.keepReads;
                                    if ($tm117.checkForStaleObjects()) {
                                        $retry113 = true;
                                        $keepReads114 = false;
                                        continue $label111;
                                    }
                                    if ($e115.tid ==
                                          null ||
                                          !$e115.tid.isDescendantOf(
                                                       $currentTid116))
                                        throw $e115;
                                    throw new fabric.worker.UserAbortException(
                                            $e115);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e115) {
                                    $commit112 = false;
                                    $currentTid116 = $tm117.getCurrentTid();
                                    if ($currentTid116 != null) {
                                        if ($e115.tid.equals($currentTid116) ||
                                              !$e115.tid.isDescendantOf(
                                                           $currentTid116)) {
                                            throw $e115;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads114) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit112) {
                                { mc = mc$var110; }
                                if ($retry113) { continue $label111; }
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
                    fabric.metrics.treaties.Treaty mc$var121 = mc;
                    fabric.worker.transaction.TransactionManager $tm128 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled131 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff129 = 1;
                    boolean $doBackoff130 = true;
                    boolean $retry124 = true;
                    boolean $keepReads125 = false;
                    $label122: for (boolean $commit123 = false; !$commit123; ) {
                        if ($backoffEnabled131) {
                            if ($doBackoff130) {
                                if ($backoff129 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff129));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e126) {
                                            
                                        }
                                    }
                                }
                                if ($backoff129 < 5000) $backoff129 *= 2;
                            }
                            $doBackoff130 = $backoff129 <= 32 || !$doBackoff130;
                        }
                        $commit123 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                mc = tmp.createThresholdTreaty(rate, base,
                                                               time);
                            }
                            catch (final fabric.worker.RetryException $e126) {
                                throw $e126;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e126) {
                                throw $e126;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e126) {
                                throw $e126;
                            }
                            catch (final Throwable $e126) {
                                $tm128.getCurrentLog().checkRetrySignal();
                                throw $e126;
                            }
                        }
                        catch (final fabric.worker.RetryException $e126) {
                            $commit123 = false;
                            continue $label122;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e126) {
                            $commit123 = false;
                            $retry124 = false;
                            $keepReads125 = $e126.keepReads;
                            if ($tm128.checkForStaleObjects()) {
                                $retry124 = true;
                                $keepReads125 = false;
                                continue $label122;
                            }
                            fabric.common.TransactionID $currentTid127 =
                              $tm128.getCurrentTid();
                            if ($e126.tid == null ||
                                  !$e126.tid.isDescendantOf($currentTid127)) {
                                throw $e126;
                            }
                            throw new fabric.worker.UserAbortException($e126);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e126) {
                            $commit123 = false;
                            fabric.common.TransactionID $currentTid127 =
                              $tm128.getCurrentTid();
                            if ($e126.tid.isDescendantOf($currentTid127))
                                continue $label122;
                            if ($currentTid127.parent != null) {
                                $retry124 = false;
                                throw $e126;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e126) {
                            $commit123 = false;
                            if ($tm128.checkForStaleObjects())
                                continue $label122;
                            $retry124 = false;
                            throw new fabric.worker.AbortException($e126);
                        }
                        finally {
                            if ($commit123) {
                                fabric.common.TransactionID $currentTid127 =
                                  $tm128.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e126) {
                                    $commit123 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e126) {
                                    $commit123 = false;
                                    $retry124 = false;
                                    $keepReads125 = $e126.keepReads;
                                    if ($tm128.checkForStaleObjects()) {
                                        $retry124 = true;
                                        $keepReads125 = false;
                                        continue $label122;
                                    }
                                    if ($e126.tid ==
                                          null ||
                                          !$e126.tid.isDescendantOf(
                                                       $currentTid127))
                                        throw $e126;
                                    throw new fabric.worker.UserAbortException(
                                            $e126);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e126) {
                                    $commit123 = false;
                                    $currentTid127 = $tm128.getCurrentTid();
                                    if ($currentTid127 != null) {
                                        if ($e126.tid.equals($currentTid127) ||
                                              !$e126.tid.isDescendantOf(
                                                           $currentTid127)) {
                                            throw $e126;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads125) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit123) {
                                { mc = mc$var121; }
                                if ($retry124) { continue $label122; }
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
                    fabric.metrics.DerivedMetric val$var132 = val;
                    fabric.worker.transaction.TransactionManager $tm139 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled142 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff140 = 1;
                    boolean $doBackoff141 = true;
                    boolean $retry135 = true;
                    boolean $keepReads136 = false;
                    $label133: for (boolean $commit134 = false; !$commit134; ) {
                        if ($backoffEnabled142) {
                            if ($doBackoff141) {
                                if ($backoff140 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff140));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e137) {
                                            
                                        }
                                    }
                                }
                                if ($backoff140 < 5000) $backoff140 *= 2;
                            }
                            $doBackoff141 = $backoff140 <= 32 || !$doBackoff141;
                        }
                        $commit134 = true;
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
                            catch (final fabric.worker.RetryException $e137) {
                                throw $e137;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e137) {
                                throw $e137;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e137) {
                                throw $e137;
                            }
                            catch (final Throwable $e137) {
                                $tm139.getCurrentLog().checkRetrySignal();
                                throw $e137;
                            }
                        }
                        catch (final fabric.worker.RetryException $e137) {
                            $commit134 = false;
                            continue $label133;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e137) {
                            $commit134 = false;
                            $retry135 = false;
                            $keepReads136 = $e137.keepReads;
                            if ($tm139.checkForStaleObjects()) {
                                $retry135 = true;
                                $keepReads136 = false;
                                continue $label133;
                            }
                            fabric.common.TransactionID $currentTid138 =
                              $tm139.getCurrentTid();
                            if ($e137.tid == null ||
                                  !$e137.tid.isDescendantOf($currentTid138)) {
                                throw $e137;
                            }
                            throw new fabric.worker.UserAbortException($e137);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e137) {
                            $commit134 = false;
                            fabric.common.TransactionID $currentTid138 =
                              $tm139.getCurrentTid();
                            if ($e137.tid.isDescendantOf($currentTid138))
                                continue $label133;
                            if ($currentTid138.parent != null) {
                                $retry135 = false;
                                throw $e137;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e137) {
                            $commit134 = false;
                            if ($tm139.checkForStaleObjects())
                                continue $label133;
                            $retry135 = false;
                            throw new fabric.worker.AbortException($e137);
                        }
                        finally {
                            if ($commit134) {
                                fabric.common.TransactionID $currentTid138 =
                                  $tm139.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e137) {
                                    $commit134 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e137) {
                                    $commit134 = false;
                                    $retry135 = false;
                                    $keepReads136 = $e137.keepReads;
                                    if ($tm139.checkForStaleObjects()) {
                                        $retry135 = true;
                                        $keepReads136 = false;
                                        continue $label133;
                                    }
                                    if ($e137.tid ==
                                          null ||
                                          !$e137.tid.isDescendantOf(
                                                       $currentTid138))
                                        throw $e137;
                                    throw new fabric.worker.UserAbortException(
                                            $e137);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e137) {
                                    $commit134 = false;
                                    $currentTid138 = $tm139.getCurrentTid();
                                    if ($currentTid138 != null) {
                                        if ($e137.tid.equals($currentTid138) ||
                                              !$e137.tid.isDescendantOf(
                                                           $currentTid138)) {
                                            throw $e137;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads136) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit134) {
                                { val = val$var132; }
                                if ($retry135) { continue $label133; }
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
                    fabric.metrics.DerivedMetric val$var143 = val;
                    fabric.worker.transaction.TransactionManager $tm150 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled153 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff151 = 1;
                    boolean $doBackoff152 = true;
                    boolean $retry146 = true;
                    boolean $keepReads147 = false;
                    $label144: for (boolean $commit145 = false; !$commit145; ) {
                        if ($backoffEnabled153) {
                            if ($doBackoff152) {
                                if ($backoff151 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff151));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e148) {
                                            
                                        }
                                    }
                                }
                                if ($backoff151 < 5000) $backoff151 *= 2;
                            }
                            $doBackoff152 = $backoff151 <= 32 || !$doBackoff152;
                        }
                        $commit145 = true;
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
                            catch (final fabric.worker.RetryException $e148) {
                                throw $e148;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e148) {
                                throw $e148;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e148) {
                                throw $e148;
                            }
                            catch (final Throwable $e148) {
                                $tm150.getCurrentLog().checkRetrySignal();
                                throw $e148;
                            }
                        }
                        catch (final fabric.worker.RetryException $e148) {
                            $commit145 = false;
                            continue $label144;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e148) {
                            $commit145 = false;
                            $retry146 = false;
                            $keepReads147 = $e148.keepReads;
                            if ($tm150.checkForStaleObjects()) {
                                $retry146 = true;
                                $keepReads147 = false;
                                continue $label144;
                            }
                            fabric.common.TransactionID $currentTid149 =
                              $tm150.getCurrentTid();
                            if ($e148.tid == null ||
                                  !$e148.tid.isDescendantOf($currentTid149)) {
                                throw $e148;
                            }
                            throw new fabric.worker.UserAbortException($e148);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e148) {
                            $commit145 = false;
                            fabric.common.TransactionID $currentTid149 =
                              $tm150.getCurrentTid();
                            if ($e148.tid.isDescendantOf($currentTid149))
                                continue $label144;
                            if ($currentTid149.parent != null) {
                                $retry146 = false;
                                throw $e148;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e148) {
                            $commit145 = false;
                            if ($tm150.checkForStaleObjects())
                                continue $label144;
                            $retry146 = false;
                            throw new fabric.worker.AbortException($e148);
                        }
                        finally {
                            if ($commit145) {
                                fabric.common.TransactionID $currentTid149 =
                                  $tm150.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e148) {
                                    $commit145 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e148) {
                                    $commit145 = false;
                                    $retry146 = false;
                                    $keepReads147 = $e148.keepReads;
                                    if ($tm150.checkForStaleObjects()) {
                                        $retry146 = true;
                                        $keepReads147 = false;
                                        continue $label144;
                                    }
                                    if ($e148.tid ==
                                          null ||
                                          !$e148.tid.isDescendantOf(
                                                       $currentTid149))
                                        throw $e148;
                                    throw new fabric.worker.UserAbortException(
                                            $e148);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e148) {
                                    $commit145 = false;
                                    $currentTid149 = $tm150.getCurrentTid();
                                    if ($currentTid149 != null) {
                                        if ($e148.tid.equals($currentTid149) ||
                                              !$e148.tid.isDescendantOf(
                                                           $currentTid149)) {
                                            throw $e148;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads147) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit145) {
                                { val = val$var143; }
                                if ($retry146) { continue $label144; }
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
                    fabric.metrics.DerivedMetric val$var154 = val;
                    fabric.worker.transaction.TransactionManager $tm161 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled164 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff162 = 1;
                    boolean $doBackoff163 = true;
                    boolean $retry157 = true;
                    boolean $keepReads158 = false;
                    $label155: for (boolean $commit156 = false; !$commit156; ) {
                        if ($backoffEnabled164) {
                            if ($doBackoff163) {
                                if ($backoff162 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff162));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e159) {
                                            
                                        }
                                    }
                                }
                                if ($backoff162 < 5000) $backoff162 *= 2;
                            }
                            $doBackoff163 = $backoff162 <= 32 || !$doBackoff163;
                        }
                        $commit156 = true;
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
                            catch (final fabric.worker.RetryException $e159) {
                                throw $e159;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e159) {
                                throw $e159;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e159) {
                                throw $e159;
                            }
                            catch (final Throwable $e159) {
                                $tm161.getCurrentLog().checkRetrySignal();
                                throw $e159;
                            }
                        }
                        catch (final fabric.worker.RetryException $e159) {
                            $commit156 = false;
                            continue $label155;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e159) {
                            $commit156 = false;
                            $retry157 = false;
                            $keepReads158 = $e159.keepReads;
                            if ($tm161.checkForStaleObjects()) {
                                $retry157 = true;
                                $keepReads158 = false;
                                continue $label155;
                            }
                            fabric.common.TransactionID $currentTid160 =
                              $tm161.getCurrentTid();
                            if ($e159.tid == null ||
                                  !$e159.tid.isDescendantOf($currentTid160)) {
                                throw $e159;
                            }
                            throw new fabric.worker.UserAbortException($e159);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e159) {
                            $commit156 = false;
                            fabric.common.TransactionID $currentTid160 =
                              $tm161.getCurrentTid();
                            if ($e159.tid.isDescendantOf($currentTid160))
                                continue $label155;
                            if ($currentTid160.parent != null) {
                                $retry157 = false;
                                throw $e159;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e159) {
                            $commit156 = false;
                            if ($tm161.checkForStaleObjects())
                                continue $label155;
                            $retry157 = false;
                            throw new fabric.worker.AbortException($e159);
                        }
                        finally {
                            if ($commit156) {
                                fabric.common.TransactionID $currentTid160 =
                                  $tm161.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e159) {
                                    $commit156 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e159) {
                                    $commit156 = false;
                                    $retry157 = false;
                                    $keepReads158 = $e159.keepReads;
                                    if ($tm161.checkForStaleObjects()) {
                                        $retry157 = true;
                                        $keepReads158 = false;
                                        continue $label155;
                                    }
                                    if ($e159.tid ==
                                          null ||
                                          !$e159.tid.isDescendantOf(
                                                       $currentTid160))
                                        throw $e159;
                                    throw new fabric.worker.UserAbortException(
                                            $e159);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e159) {
                                    $commit156 = false;
                                    $currentTid160 = $tm161.getCurrentTid();
                                    if ($currentTid160 != null) {
                                        if ($e159.tid.equals($currentTid160) ||
                                              !$e159.tid.isDescendantOf(
                                                           $currentTid160)) {
                                            throw $e159;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads158) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit156) {
                                { val = val$var154; }
                                if ($retry157) { continue $label155; }
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
                    fabric.worker.metrics.StatsMap result$var165 = result;
                    fabric.worker.transaction.TransactionManager $tm172 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled175 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff173 = 1;
                    boolean $doBackoff174 = true;
                    boolean $retry168 = true;
                    boolean $keepReads169 = false;
                    $label166: for (boolean $commit167 = false; !$commit167; ) {
                        if ($backoffEnabled175) {
                            if ($doBackoff174) {
                                if ($backoff173 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff173));
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
                        $commit167 = true;
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
                            catch (final fabric.worker.RetryException $e170) {
                                throw $e170;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e170) {
                                throw $e170;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e170) {
                                throw $e170;
                            }
                            catch (final Throwable $e170) {
                                $tm172.getCurrentLog().checkRetrySignal();
                                throw $e170;
                            }
                        }
                        catch (final fabric.worker.RetryException $e170) {
                            $commit167 = false;
                            continue $label166;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e170) {
                            $commit167 = false;
                            $retry168 = false;
                            $keepReads169 = $e170.keepReads;
                            if ($tm172.checkForStaleObjects()) {
                                $retry168 = true;
                                $keepReads169 = false;
                                continue $label166;
                            }
                            fabric.common.TransactionID $currentTid171 =
                              $tm172.getCurrentTid();
                            if ($e170.tid == null ||
                                  !$e170.tid.isDescendantOf($currentTid171)) {
                                throw $e170;
                            }
                            throw new fabric.worker.UserAbortException($e170);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e170) {
                            $commit167 = false;
                            fabric.common.TransactionID $currentTid171 =
                              $tm172.getCurrentTid();
                            if ($e170.tid.isDescendantOf($currentTid171))
                                continue $label166;
                            if ($currentTid171.parent != null) {
                                $retry168 = false;
                                throw $e170;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e170) {
                            $commit167 = false;
                            if ($tm172.checkForStaleObjects())
                                continue $label166;
                            $retry168 = false;
                            throw new fabric.worker.AbortException($e170);
                        }
                        finally {
                            if ($commit167) {
                                fabric.common.TransactionID $currentTid171 =
                                  $tm172.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e170) {
                                    $commit167 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e170) {
                                    $commit167 = false;
                                    $retry168 = false;
                                    $keepReads169 = $e170.keepReads;
                                    if ($tm172.checkForStaleObjects()) {
                                        $retry168 = true;
                                        $keepReads169 = false;
                                        continue $label166;
                                    }
                                    if ($e170.tid ==
                                          null ||
                                          !$e170.tid.isDescendantOf(
                                                       $currentTid171))
                                        throw $e170;
                                    throw new fabric.worker.UserAbortException(
                                            $e170);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e170) {
                                    $commit167 = false;
                                    $currentTid171 = $tm172.getCurrentTid();
                                    if ($currentTid171 != null) {
                                        if ($e170.tid.equals($currentTid171) ||
                                              !$e170.tid.isDescendantOf(
                                                           $currentTid171)) {
                                            throw $e170;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads169) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit167) {
                                { result = result$var165; }
                                if ($retry168) { continue $label166; }
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
                  tmp.get$treatiesBox().
                    create(
                      fabric.worker.metrics.treaties.statements.ThresholdStatement.create(
                                                                                     rate,
                                                                                     base,
                                                                                     time),
                      weakStats);
            }
            else {
                {
                    fabric.metrics.treaties.Treaty t$var176 = t;
                    fabric.worker.transaction.TransactionManager $tm183 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled186 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff184 = 1;
                    boolean $doBackoff185 = true;
                    boolean $retry179 = true;
                    boolean $keepReads180 = false;
                    $label177: for (boolean $commit178 = false; !$commit178; ) {
                        if ($backoffEnabled186) {
                            if ($doBackoff185) {
                                if ($backoff184 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff184));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e181) {
                                            
                                        }
                                    }
                                }
                                if ($backoff184 < 5000) $backoff184 *= 2;
                            }
                            $doBackoff185 = $backoff184 <= 32 || !$doBackoff185;
                        }
                        $commit178 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                t =
                                  tmp.get$treatiesBox().
                                    create(
                                      fabric.worker.metrics.treaties.statements.ThresholdStatement.create(
                                                                                                     rate,
                                                                                                     base,
                                                                                                     time),
                                      weakStats);
                            }
                            catch (final fabric.worker.RetryException $e181) {
                                throw $e181;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e181) {
                                throw $e181;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e181) {
                                throw $e181;
                            }
                            catch (final Throwable $e181) {
                                $tm183.getCurrentLog().checkRetrySignal();
                                throw $e181;
                            }
                        }
                        catch (final fabric.worker.RetryException $e181) {
                            $commit178 = false;
                            continue $label177;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e181) {
                            $commit178 = false;
                            $retry179 = false;
                            $keepReads180 = $e181.keepReads;
                            if ($tm183.checkForStaleObjects()) {
                                $retry179 = true;
                                $keepReads180 = false;
                                continue $label177;
                            }
                            fabric.common.TransactionID $currentTid182 =
                              $tm183.getCurrentTid();
                            if ($e181.tid == null ||
                                  !$e181.tid.isDescendantOf($currentTid182)) {
                                throw $e181;
                            }
                            throw new fabric.worker.UserAbortException($e181);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e181) {
                            $commit178 = false;
                            fabric.common.TransactionID $currentTid182 =
                              $tm183.getCurrentTid();
                            if ($e181.tid.isDescendantOf($currentTid182))
                                continue $label177;
                            if ($currentTid182.parent != null) {
                                $retry179 = false;
                                throw $e181;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e181) {
                            $commit178 = false;
                            if ($tm183.checkForStaleObjects())
                                continue $label177;
                            $retry179 = false;
                            throw new fabric.worker.AbortException($e181);
                        }
                        finally {
                            if ($commit178) {
                                fabric.common.TransactionID $currentTid182 =
                                  $tm183.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e181) {
                                    $commit178 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e181) {
                                    $commit178 = false;
                                    $retry179 = false;
                                    $keepReads180 = $e181.keepReads;
                                    if ($tm183.checkForStaleObjects()) {
                                        $retry179 = true;
                                        $keepReads180 = false;
                                        continue $label177;
                                    }
                                    if ($e181.tid ==
                                          null ||
                                          !$e181.tid.isDescendantOf(
                                                       $currentTid182))
                                        throw $e181;
                                    throw new fabric.worker.UserAbortException(
                                            $e181);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e181) {
                                    $commit178 = false;
                                    $currentTid182 = $tm183.getCurrentTid();
                                    if ($currentTid182 != null) {
                                        if ($e181.tid.equals($currentTid182) ||
                                              !$e181.tid.isDescendantOf(
                                                           $currentTid182)) {
                                            throw $e181;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads180) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit178) {
                                { t = t$var176; }
                                if ($retry179) { continue $label177; }
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
                  tmp.get$treatiesBox().
                    create(
                      fabric.worker.metrics.treaties.statements.EqualityStatement.create(
                                                                                    value),
                      weakStats);
            }
            else {
                {
                    fabric.metrics.treaties.Treaty t$var187 = t;
                    fabric.worker.transaction.TransactionManager $tm194 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled197 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff195 = 1;
                    boolean $doBackoff196 = true;
                    boolean $retry190 = true;
                    boolean $keepReads191 = false;
                    $label188: for (boolean $commit189 = false; !$commit189; ) {
                        if ($backoffEnabled197) {
                            if ($doBackoff196) {
                                if ($backoff195 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff195));
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
                        $commit189 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                t =
                                  tmp.get$treatiesBox().
                                    create(
                                      fabric.worker.metrics.treaties.statements.EqualityStatement.create(
                                                                                                    value),
                                      weakStats);
                            }
                            catch (final fabric.worker.RetryException $e192) {
                                throw $e192;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e192) {
                                throw $e192;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e192) {
                                throw $e192;
                            }
                            catch (final Throwable $e192) {
                                $tm194.getCurrentLog().checkRetrySignal();
                                throw $e192;
                            }
                        }
                        catch (final fabric.worker.RetryException $e192) {
                            $commit189 = false;
                            continue $label188;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e192) {
                            $commit189 = false;
                            $retry190 = false;
                            $keepReads191 = $e192.keepReads;
                            if ($tm194.checkForStaleObjects()) {
                                $retry190 = true;
                                $keepReads191 = false;
                                continue $label188;
                            }
                            fabric.common.TransactionID $currentTid193 =
                              $tm194.getCurrentTid();
                            if ($e192.tid == null ||
                                  !$e192.tid.isDescendantOf($currentTid193)) {
                                throw $e192;
                            }
                            throw new fabric.worker.UserAbortException($e192);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e192) {
                            $commit189 = false;
                            fabric.common.TransactionID $currentTid193 =
                              $tm194.getCurrentTid();
                            if ($e192.tid.isDescendantOf($currentTid193))
                                continue $label188;
                            if ($currentTid193.parent != null) {
                                $retry190 = false;
                                throw $e192;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e192) {
                            $commit189 = false;
                            if ($tm194.checkForStaleObjects())
                                continue $label188;
                            $retry190 = false;
                            throw new fabric.worker.AbortException($e192);
                        }
                        finally {
                            if ($commit189) {
                                fabric.common.TransactionID $currentTid193 =
                                  $tm194.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e192) {
                                    $commit189 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e192) {
                                    $commit189 = false;
                                    $retry190 = false;
                                    $keepReads191 = $e192.keepReads;
                                    if ($tm194.checkForStaleObjects()) {
                                        $retry190 = true;
                                        $keepReads191 = false;
                                        continue $label188;
                                    }
                                    if ($e192.tid ==
                                          null ||
                                          !$e192.tid.isDescendantOf(
                                                       $currentTid193))
                                        throw $e192;
                                    throw new fabric.worker.UserAbortException(
                                            $e192);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e192) {
                                    $commit189 = false;
                                    $currentTid193 = $tm194.getCurrentTid();
                                    if ($currentTid193 != null) {
                                        if ($e192.tid.equals($currentTid193) ||
                                              !$e192.tid.isDescendantOf(
                                                           $currentTid193)) {
                                            throw $e192;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads191) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit189) {
                                { t = t$var187; }
                                if ($retry190) { continue $label188; }
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
    
    public static final byte[] $classHash = new byte[] { -31, 127, 74, 47, 112,
    -96, 67, 92, -101, 67, -44, 42, 79, -98, -121, -128, -76, 120, -28, 0, -79,
    87, -128, -19, -113, 89, 65, 39, -51, -92, 36, 1 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748453000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1cCXQc1ZV9XVpsyYvkfV8wCuBNDZjNSzC2sLGMbAtLsrGNLUrdJansUldT9dtusTqZYczJgEPAeAwJMEzMEMDAwDgsJ5hlwmZI2CckDFtmgDDHOIZDBjg5ZJj3fr1e1F311ZWRzql3S1X/v//uf/+/v1R1HTgKFa4DMzr1DtOqF71Jw61frnc0NjXrjmvEGyzddVvxantsSHnj3k/uik/VQGuCoTE9YSfMmG61J1wBw5u26tv1aMIQ0ba1jQs3QVWMMq7Q3W4B2qalaQemJ22rt8uyBRdSpP+m2dE9/7Cl9qEyqNkINWaiRejCjDXYCWGkxUYY2mP0dBiOuyQeN+IbYUTCMOIthmPqlnkpJrQTG2Gka3YldJFyDHet4drWdko40k0lDUeWmblI5ttotpOKCdtB82s981PCtKJNpisWNkFlp2lYcfcSuBLKm6Ci09K7MOHYpgyLqNQYXU7XMXm1iWY6nXrMyGQp32Ym4gKmFebIMq47HxNg1kE9hui2s0WVJ3S8ACM9kyw90RVtEY6Z6MKkFXYKSxEwMVApJhqc1GPb9C6jXcD4wnTN3i1MVSWrhbIIGFOYTGpCn00s8Fmet46uXrT7ssSKhAYRtDluxCyyfzBmmlqQaa3RaThGImZ4GYfOatqrjz10jQaAiccUJPbSPHL55+fMmfrUC16aST5p1nRsNWKiPba/Y/hrkxtmzi8jMwYnbdekptCHufRqM99ZmE5iax+b1Ug36zM3n1r73Iad9xhHNKhuhMqYbaV6sFWNiNk9SdMynPOMhOHowog3QpWRiDfI+40wCM+bzIThXV3T2ekaohHKLXmp0pb/YxV1ogqqokF4biY67cx5Uhfd8jydBIBaPCACUHYmQHIsnp8EUIEuaox22z1GtMNKGTuweUfxMHQn1h3FfuuYsbkxO9kbdZ1Y1EklhIkpvetRbEoIbnSVxHo0IjmQytJkee2OSAQrdVrMjhsduose4taytNnCDrHCtuKG0x6zdh9qhFGHbpYtpopauYstVdZJBL08uTA+5Ofdk1q67PP721/yWhvl5SrDxusZV8/G1XvGoT1DqQPVY0iqx5B0IJKub7it8V7ZTipd2aGyKoaiigVJSxedttOThkhE8hkt88sGgu7dhmEDI8PQmS2bV158zYwybJnJHeXkLExaV9hPctGlEc90bPztsZpdn3z5wN4r7FyPEVBX1JGLc1JHnFFYOY4dM+IY6HLqZ03Xf95+6Io6jYJIFcY3oWMLxGAxtbCMPh1yYSa4UW1UNMEQqgPdoluZiFQtuh17R+6KdPpwEiM9/1NlFRgo4+J3W5K3/vbl/54nR4xMCK3Ji7UthliY121JWY3soCNydd/qGAame3df8403Hd21SVY8pjjer8A6kg3YXXXsp7Zz9QuX/O799/a/qeWcJaAymeqwzFhachnxLf5F8PhfOqjv0QVCjMAN3O+nZzt+kko+IWcbhgALwxCa7ta1JXrsuNlp6h2WQS3lm5rvnPLzT3fXeu628IpXeQ7M6V9B7vqEpbDzpS1fTZVqIjEagnL1l0vmxbVROc1LHEfvJTvS33t9ys3P67diy8eo5JqXGjLQgKwPkA48VdbFXClPKbh3GokZXm1Nltc1tzjGL6fBMtcWN0YP/GRiw9lHvK6ebYuk4zifrr5Oz+smp97T8z/ajMpnNRi0EWrlOK0nxDodgxU2g4040roNfLEJhvW533fU9IaIhdm+NrmwH+QVW9gLciEGzyk1nVd7Dd9rOFgRQ6mSZmKjigHU38l4Dd0dlSQ5Oh0BebJAZjleyhNIzMw0xqqkYwu00oins2o1UjuE1e1ivDJPrYBBmC1tGq7MM0bACRwDd9jONsPJhkJORdEi3btKT8rkEwrjndeFSZ6RtaGGbDgRbcHy1ycZL/Shtsyfmkans5Cf2dOTEtSiZTGzBQwRjoHTNcNdaqcz5k8rCOGZfp9JR8kmSjPT6uIG6x0YPfWYyNWm/KvhgTTO2JbHJK9lRzIGeaGfWlE9RxNkkKm9Kqo9y8apbzqNXWFK0BxJzu/2f3/PbfE1d57izWRG9p13LEukeu77zV9+Vb/vg8M+I1uVsJNzLWO7YeXZuAyLPK5osr5KTiFzneiDI1PmN2z7qMsrdlqBiYWp71514PB5J8Ru0KAs21uK5q19My3s20eqHQOn3YnWPj1letYJVeSEcXicDFD5PONj+c0p1wiLu4l0bkH/GMxKHmV8qNCj/tEsrrjXSWKLgIrtFEt8glyzY/bgOLWdJ7LGNXt+8G397j2e57zZ/vFFE+78PN6MXxY2THYHaj/HqUqROZb/4YErfvGzK3ZpbOh6HMTidirTJDf1rebZeCwAGPQ246MB1UxCL65UyvII44PBlRrJuWar1OoqajZFAiflFTSZzAatyQW9/lxczOHizJu/yS5fQE925+V4nI2mrmGcWGIr0mTUNLfjMC5oAkhry4I2VcsqJzAOCaav5cJOba4Ovqeog78h0Yux1yu6XVYFXdvh50QcVeACgOrrGZPhnEhZbEazJCfWSq0/UBC4lsTVAhcsVsrXcLl0acCjTQ5fEqt/Xap7ZCcncWWBV2pY068YnyzdKx6pPQpSe0nsxlGJvRLETTrlODwsgGHHGN8K5xTK8hvGV0M45VaF/beT2CegrMdM5Cwo6DKL8MAxfPh6xjlhfHKFn09qWdNsxilhfXKXgtPdJO4QUM0+CaCWdcmNACPfZ3w+nEsoy3OMT4Vwyb8ozJcj0b3kEj0daHcUj7sAxg7xcMxH4eymLB8yvleS3V6Aekxh9y9IHBQwossQyy5J6ZYpeuUcrDcTsKcWBOzMTM6bqvVSKt+QvRKPe5HsDxnXDUj7I01tjOeV3v68inhWURGyDT0pYDy3v6L6oPuH/Lx6Hh4vAExcyzg6nFcpyyjGYcGMyqStZZKMFPKO3EOa7dXTNozSlp3okmW+rKD6BonDuGxCjq3djuF221Y8mKT0ZgceGIsn/pnx7gHxJmn6GeMtwdzLparynDez4hVZ9n8oyL5L4t8FTMj5tQTOcpG3GI+PAKa2Mh6ncOx3ixZyMst0xnElNdWtuab6oYLSxyTeL9l/coRuxOMrgOn7GNvD+C9whCZNWxgv6LftZiLKqL7r1hZhO9llVvEiVVaKF4D/qKiUP5H4hGZauEQzlgipNUesoDqwz0bwn+NfZLxxIKpDarqB8eqSPH4MlwKT8td1K3EJKjdxvNXGlhFb9Vd7j+31VnSFG/N5CT878P6R14dNuV/uA5bTfiypry58olH8wKLPcwhJfWiW2OlEbCEeGNqGDGWsFND8/9xJ7jPn593pAdeZ58a8UVH+fwaKCPBmRqRc5WpcvnSaCd3KbGRUWkaiS3T7Bd8yrFs6/TZd6Olsw8/bYMBIbdD+At1qzSTwdiBMuz77iCuTIu1LZJNHRJaaN8hIu4L7SmSM4h5FqshIpB0jCzOG1eYs9zbYPKNkyd8otE2mi1/irE6Px0vpk3MATlzNmB8z//o+SZrGMlaV1ic9J5L5dQpqZEFkKlLDmWop1PCY9TrjjweEGmm6hfG6sNTqFdROJnESUdPTCmojKcNJaMBmgLnvMgbNxYOprSmgNoI1Pcf4SDC1fKPPUtxbQOJUHBxwxGxzjWYKLsKvDw/qsG3L0BN+TL+D9uBgV1/u4dzPB4QpafqM8felMV2muEdz4shidB0y9WiupWRbgwi1I6HZjOMHhBBpGsc4uDRCaxT3aFoRacwntFRJ6GIsdi3j4gEhRJrOZoyWRmiD4p6M2S35hNYpCelYbC9j54AQIk0G47rSCMUU9yg2RDbnE1rtR0hOqampmbgQnslYHUCIRPGUWmapYoTSLLcU92iHIdIlYKw3l6jjuUSdN4mo84t5mf2HiAA4+UHGmxUsild8Mss+xhuCWeSt449Jc1MKKjtI4DRqsHSCne71M162qVNR59UInzG+GmB8uDZFml5h7H9PJTOtmOr/BIveAXIDn1xJvlcp6oKm3pHezJa+X1vMPJmIXAMwbwXjgnBepCzzGeeV1havVdzbTWIXjkCu3pPkPf5XgvyHi4zTLmPcNCD+I00bGc8vpU1Gdkqz9yoo7SNxvZqS9MREVHkTFvwO40vhPEFZXmR8pjRP3K64dweJWzCeWbor2pJxXRiBzsAVUuR2gDMmeXj61wPiDNL0FeMnIZxxt4LVvSR+2i8r6Y/pqPWfkNUNjJeF8wdluZRRlOaPhxT3DpK4T8DwlLS6kVaw2LP9+rX0CS5YIwcAzvyE8ckA68P5hDQ9wfhACJ88rmD2BImHS2KW7ScPAJx1LuO8AGYBfqEspzLOCaaQb6FixzRCM/3IUzjkbDcsO2Z6m0++HqFe8hjA/MsZNwyIR0jThYyNITzyioLTayQO98MpO3o8jiW/zvhMAKcAX1CWXzIeKs0Xv1Xce5vEGzjkJWzT9R3ysmMHLqsWlnu44MMBcQRp+i/GN0M44j8VhMiyyDsqQtILNGT9DuDsCg+/ezCcFyjLvzLeF2x5wd57ZCedeXOyIwoOR0l8jN3b4AcJzbZlxrIPVhb5T36yz1eMBE53YkaPkRD1y3LneUqKpkZ+VbQc+R0BOOcGxrZwVURZWhlXB1dR4S62VztfK2rnzyS+KKodunrMj0cXqkerzk0zBr0C4M9DZpnAOCqYR96jhuxThjx/a5FgRho1kMg3AmpEZlO+H0otaM9ogPNGe7j8xXCUKMthxl+W3Hrz/KNVK9gMJVERgs0CNGU6QGONhyveC8eGsrzL+FYwm8Iooo1ScBhDYriA0Y7RSSTWG/q2Za4we3C0daUKPyLdaEUdErmTcXk4IpRlGePiYCIFe8OZpZDcY3WNWMrBLkGvBSViZlK3/Pt7rhYmK2phBolxAib51UK7Y/TYci7oXxmnoY0nA5y/hHFsuMqgLGMYa0J4daaCz2wSdRg5mE8TvZVn9fpRoAPWYPlI4/yXGK8LoOA76gU9u5SarmW8MphZwZNopjdPQe90EnMFjOEnlv2zlI7qwFJWIdn7GFvDOYqytDCuKoWONiVHZ5GCztkkzkQ6fXko2t0YyrsIS1kP0Pwl4ythnEZlFk1VRrOmlxkfD9EclysIriBxjqBfF/UkU8JYF7TrkOXVDrB2tIcXHB0QXqTpU8a3Q/BqVvCijWNtJXYz5tUSvIqXzBZj+ThIt5zOOGRAmJGmag/Xfh2C2UYFs4tItArvNz7IrEm5JJbkKKDbAK0XMYZ6myqQHGmazTghBDnFC60a7RFrW7C/Mbm2fteWWe/tAGjbyRjqdZ1AgqSpjbEhBMGEgiBp0LpxYpLpb4qlWrbLXQWw7mZGZ0CokaZLGNtDUEsrqF1KwsmFktXK1dxMLP1qgPXLGaNheAWt5qSmesZpwbzyzf6+4t7fkrhcwDDTbTETXZaReZwYWeQ3iuEKVbsO4MLhHq4P94qczPIhY/+vyGXmXvP7WYnRQCzXXpmX3lqyF0iD7w619veKSvkRib8jP5M6o58XzDYhoR9hndzFGG4FJ7O0Mpa0gpMju3attHSfggW9saXdKGBUPovcuO5PZimWgR1xw6WMS8KRoSznMC4oicy1ua53h4LMT0n8pFSXbEPltwJsnO7hhifCsaAshxgVj7dzKzd2SY7KPQoqB0jcWbpf5CsKNLDdgYzuYdwSOpj4vKIgNW1mbC6FqJwk5xM9qCD6MIn7C4l60+ZA161Ecx4C2NzGGO51TZllFGOY1zVJyLcVtUMKRrRXrT1Kg7fHqP+3+yQnFw16GGDLSR5uDtkcKcshRkVzzO2NyOZYROw5BbEXSDwtYLIvsf6CxhY07mmA9usZF4bjR1kWMJ5WCr98armmqNg41mjjWHsxtON2oVXPAly8zcP2oNVAADHK8injx8HEKqShFX6Oy7FTbC9rtKTQ3vgrvCdDC+3YHQbQZ3l48b8NSGghTU8zHiyFe9G7wznuip1ojXaitXcCufcTbWg+8w5AzGQ8J5yDKctixvnBJAtf+dcU29IatTHtYwGjPUIlvuVOk5APAOLjPYw9F44IZXmWsaTf8nBLlRb/ScHmSxLHBEzyY9NfXKGZ8xEA4yLGaeFIUZapjONLIcXbydLwvyhIfUvi67AuwlWkdgygc5SHxn3h2FCWA4z/HMymYFKSR6msMphSGb0KVhYJ7ycZQS5Gs75AZvczDszkhDRtZix9cpJPuFZBeCSJ6iDC/YSNuVggQvdID7v+GMqVMstRxj+UEjYiE6TNExR8JpEYLeiH/fTbaKOVvmUSqfKzfjUWjXFi62eM94aznrLcw7i/pFgh54uLpJkzFBToja6yKQLGeS5ZkogviQn549C8nzwV/qRmu23G/UieiRZGZaeTmHg9HEnK8hrjS8Ekcy7yFmRlivd2y+i93bKZOB/WXddwRLPtipidiGe+slF2QlpApfdyG303YpLPp1v4I0KxhmeM/R+dP2dMwGdbxhd91onz3X9bzeBxt7W95f0OIfOBoKomGNyZsqz87yvknVcmHaPTlJVWJeXwpORzuoDhfX99JuTvFeiMKqBsnpfuLKTlpaP/5suKlI8HvR+m4Qr/eL9vDSzhzwa0pLKvtE+UxU9MOfT5qgNfjPu6cnDrB/JDIljH039/1cpo8h8bLvpxw5uz1ty2a+fB9Ifw4PqdR3+4YcmJv95fF/k/ZCdkxFZLAAA=";
}
