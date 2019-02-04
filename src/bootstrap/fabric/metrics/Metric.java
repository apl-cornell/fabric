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
                    fabric.metrics.DerivedMetric val$var76 = val;
                    fabric.worker.transaction.TransactionManager $tm83 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled86 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff84 = 1;
                    boolean $doBackoff85 = true;
                    boolean $retry79 = true;
                    boolean $keepReads80 = false;
                    $label77: for (boolean $commit78 = false; !$commit78; ) {
                        if ($backoffEnabled86) {
                            if ($doBackoff85) {
                                if ($backoff84 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff84));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e81) {
                                            
                                        }
                                    }
                                }
                                if ($backoff84 < 5000) $backoff84 *= 2;
                            }
                            $doBackoff85 = $backoff84 <= 32 || !$doBackoff85;
                        }
                        $commit78 = true;
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
                            catch (final fabric.worker.RetryException $e81) {
                                throw $e81;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e81) {
                                throw $e81;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e81) {
                                throw $e81;
                            }
                            catch (final Throwable $e81) {
                                $tm83.getCurrentLog().checkRetrySignal();
                                throw $e81;
                            }
                        }
                        catch (final fabric.worker.RetryException $e81) {
                            $commit78 = false;
                            continue $label77;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e81) {
                            $commit78 = false;
                            $retry79 = false;
                            $keepReads80 = $e81.keepReads;
                            if ($tm83.checkForStaleObjects()) {
                                $retry79 = true;
                                $keepReads80 = false;
                                continue $label77;
                            }
                            fabric.common.TransactionID $currentTid82 =
                              $tm83.getCurrentTid();
                            if ($e81.tid == null ||
                                  !$e81.tid.isDescendantOf($currentTid82)) {
                                throw $e81;
                            }
                            throw new fabric.worker.UserAbortException($e81);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e81) {
                            $commit78 = false;
                            fabric.common.TransactionID $currentTid82 =
                              $tm83.getCurrentTid();
                            if ($e81.tid.isDescendantOf($currentTid82))
                                continue $label77;
                            if ($currentTid82.parent != null) {
                                $retry79 = false;
                                throw $e81;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e81) {
                            $commit78 = false;
                            if ($tm83.checkForStaleObjects()) continue $label77;
                            $retry79 = false;
                            throw new fabric.worker.AbortException($e81);
                        }
                        finally {
                            if ($commit78) {
                                fabric.common.TransactionID $currentTid82 =
                                  $tm83.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e81) {
                                    $commit78 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e81) {
                                    $commit78 = false;
                                    $retry79 = false;
                                    $keepReads80 = $e81.keepReads;
                                    if ($tm83.checkForStaleObjects()) {
                                        $retry79 = true;
                                        $keepReads80 = false;
                                        continue $label77;
                                    }
                                    if ($e81.tid ==
                                          null ||
                                          !$e81.tid.isDescendantOf(
                                                      $currentTid82))
                                        throw $e81;
                                    throw new fabric.worker.UserAbortException(
                                            $e81);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e81) {
                                    $commit78 = false;
                                    $currentTid82 = $tm83.getCurrentTid();
                                    if ($currentTid82 != null) {
                                        if ($e81.tid.equals($currentTid82) ||
                                              !$e81.tid.isDescendantOf(
                                                          $currentTid82)) {
                                            throw $e81;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads80) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit78) {
                                { val = val$var76; }
                                if ($retry79) { continue $label77; }
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
                    fabric.metrics.DerivedMetric val$var87 = val;
                    fabric.worker.transaction.TransactionManager $tm94 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled97 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff95 = 1;
                    boolean $doBackoff96 = true;
                    boolean $retry90 = true;
                    boolean $keepReads91 = false;
                    $label88: for (boolean $commit89 = false; !$commit89; ) {
                        if ($backoffEnabled97) {
                            if ($doBackoff96) {
                                if ($backoff95 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff95));
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
                        $commit89 = true;
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
                            catch (final fabric.worker.RetryException $e92) {
                                throw $e92;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e92) {
                                throw $e92;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e92) {
                                throw $e92;
                            }
                            catch (final Throwable $e92) {
                                $tm94.getCurrentLog().checkRetrySignal();
                                throw $e92;
                            }
                        }
                        catch (final fabric.worker.RetryException $e92) {
                            $commit89 = false;
                            continue $label88;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e92) {
                            $commit89 = false;
                            $retry90 = false;
                            $keepReads91 = $e92.keepReads;
                            if ($tm94.checkForStaleObjects()) {
                                $retry90 = true;
                                $keepReads91 = false;
                                continue $label88;
                            }
                            fabric.common.TransactionID $currentTid93 =
                              $tm94.getCurrentTid();
                            if ($e92.tid == null ||
                                  !$e92.tid.isDescendantOf($currentTid93)) {
                                throw $e92;
                            }
                            throw new fabric.worker.UserAbortException($e92);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e92) {
                            $commit89 = false;
                            fabric.common.TransactionID $currentTid93 =
                              $tm94.getCurrentTid();
                            if ($e92.tid.isDescendantOf($currentTid93))
                                continue $label88;
                            if ($currentTid93.parent != null) {
                                $retry90 = false;
                                throw $e92;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e92) {
                            $commit89 = false;
                            if ($tm94.checkForStaleObjects()) continue $label88;
                            $retry90 = false;
                            throw new fabric.worker.AbortException($e92);
                        }
                        finally {
                            if ($commit89) {
                                fabric.common.TransactionID $currentTid93 =
                                  $tm94.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e92) {
                                    $commit89 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e92) {
                                    $commit89 = false;
                                    $retry90 = false;
                                    $keepReads91 = $e92.keepReads;
                                    if ($tm94.checkForStaleObjects()) {
                                        $retry90 = true;
                                        $keepReads91 = false;
                                        continue $label88;
                                    }
                                    if ($e92.tid ==
                                          null ||
                                          !$e92.tid.isDescendantOf(
                                                      $currentTid93))
                                        throw $e92;
                                    throw new fabric.worker.UserAbortException(
                                            $e92);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e92) {
                                    $commit89 = false;
                                    $currentTid93 = $tm94.getCurrentTid();
                                    if ($currentTid93 != null) {
                                        if ($e92.tid.equals($currentTid93) ||
                                              !$e92.tid.isDescendantOf(
                                                          $currentTid93)) {
                                            throw $e92;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads91) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit89) {
                                { val = val$var87; }
                                if ($retry90) { continue $label88; }
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
                        fabric.metrics.DerivedMetric val$var98 = val;
                        fabric.worker.transaction.TransactionManager $tm105 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled108 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff106 = 1;
                        boolean $doBackoff107 = true;
                        boolean $retry101 = true;
                        boolean $keepReads102 = false;
                        $label99: for (boolean $commit100 = false; !$commit100;
                                       ) {
                            if ($backoffEnabled108) {
                                if ($doBackoff107) {
                                    if ($backoff106 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff106));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e103) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff106 < 5000) $backoff106 *= 2;
                                }
                                $doBackoff107 = $backoff106 <= 32 ||
                                                  !$doBackoff107;
                            }
                            $commit100 = true;
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
                                         RetryException $e103) {
                                    throw $e103;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e103) {
                                    throw $e103;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e103) {
                                    throw $e103;
                                }
                                catch (final Throwable $e103) {
                                    $tm105.getCurrentLog().checkRetrySignal();
                                    throw $e103;
                                }
                            }
                            catch (final fabric.worker.RetryException $e103) {
                                $commit100 = false;
                                continue $label99;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e103) {
                                $commit100 = false;
                                $retry101 = false;
                                $keepReads102 = $e103.keepReads;
                                if ($tm105.checkForStaleObjects()) {
                                    $retry101 = true;
                                    $keepReads102 = false;
                                    continue $label99;
                                }
                                fabric.common.TransactionID $currentTid104 =
                                  $tm105.getCurrentTid();
                                if ($e103.tid ==
                                      null ||
                                      !$e103.tid.isDescendantOf(
                                                   $currentTid104)) {
                                    throw $e103;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e103);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e103) {
                                $commit100 = false;
                                fabric.common.TransactionID $currentTid104 =
                                  $tm105.getCurrentTid();
                                if ($e103.tid.isDescendantOf($currentTid104))
                                    continue $label99;
                                if ($currentTid104.parent != null) {
                                    $retry101 = false;
                                    throw $e103;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e103) {
                                $commit100 = false;
                                if ($tm105.checkForStaleObjects())
                                    continue $label99;
                                $retry101 = false;
                                throw new fabric.worker.AbortException($e103);
                            }
                            finally {
                                if ($commit100) {
                                    fabric.common.TransactionID $currentTid104 =
                                      $tm105.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e103) {
                                        $commit100 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e103) {
                                        $commit100 = false;
                                        $retry101 = false;
                                        $keepReads102 = $e103.keepReads;
                                        if ($tm105.checkForStaleObjects()) {
                                            $retry101 = true;
                                            $keepReads102 = false;
                                            continue $label99;
                                        }
                                        if ($e103.tid ==
                                              null ||
                                              !$e103.tid.isDescendantOf(
                                                           $currentTid104))
                                            throw $e103;
                                        throw new fabric.worker.
                                                UserAbortException($e103);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e103) {
                                        $commit100 = false;
                                        $currentTid104 = $tm105.getCurrentTid();
                                        if ($currentTid104 != null) {
                                            if ($e103.tid.equals(
                                                            $currentTid104) ||
                                                  !$e103.tid.
                                                  isDescendantOf(
                                                    $currentTid104)) {
                                                throw $e103;
                                            }
                                        }
                                    }
                                } else if ($keepReads102) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit100) {
                                    { val = val$var98; }
                                    if ($retry101) { continue $label99; }
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
                        fabric.metrics.DerivedMetric val$var109 = val;
                        fabric.worker.transaction.TransactionManager $tm116 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled119 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff117 = 1;
                        boolean $doBackoff118 = true;
                        boolean $retry112 = true;
                        boolean $keepReads113 = false;
                        $label110: for (boolean $commit111 = false; !$commit111;
                                        ) {
                            if ($backoffEnabled119) {
                                if ($doBackoff118) {
                                    if ($backoff117 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff117));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e114) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff117 < 5000) $backoff117 *= 2;
                                }
                                $doBackoff118 = $backoff117 <= 32 ||
                                                  !$doBackoff118;
                            }
                            $commit111 = true;
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
                                         RetryException $e114) {
                                    throw $e114;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e114) {
                                    throw $e114;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e114) {
                                    throw $e114;
                                }
                                catch (final Throwable $e114) {
                                    $tm116.getCurrentLog().checkRetrySignal();
                                    throw $e114;
                                }
                            }
                            catch (final fabric.worker.RetryException $e114) {
                                $commit111 = false;
                                continue $label110;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e114) {
                                $commit111 = false;
                                $retry112 = false;
                                $keepReads113 = $e114.keepReads;
                                if ($tm116.checkForStaleObjects()) {
                                    $retry112 = true;
                                    $keepReads113 = false;
                                    continue $label110;
                                }
                                fabric.common.TransactionID $currentTid115 =
                                  $tm116.getCurrentTid();
                                if ($e114.tid ==
                                      null ||
                                      !$e114.tid.isDescendantOf(
                                                   $currentTid115)) {
                                    throw $e114;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e114);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e114) {
                                $commit111 = false;
                                fabric.common.TransactionID $currentTid115 =
                                  $tm116.getCurrentTid();
                                if ($e114.tid.isDescendantOf($currentTid115))
                                    continue $label110;
                                if ($currentTid115.parent != null) {
                                    $retry112 = false;
                                    throw $e114;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e114) {
                                $commit111 = false;
                                if ($tm116.checkForStaleObjects())
                                    continue $label110;
                                $retry112 = false;
                                throw new fabric.worker.AbortException($e114);
                            }
                            finally {
                                if ($commit111) {
                                    fabric.common.TransactionID $currentTid115 =
                                      $tm116.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e114) {
                                        $commit111 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e114) {
                                        $commit111 = false;
                                        $retry112 = false;
                                        $keepReads113 = $e114.keepReads;
                                        if ($tm116.checkForStaleObjects()) {
                                            $retry112 = true;
                                            $keepReads113 = false;
                                            continue $label110;
                                        }
                                        if ($e114.tid ==
                                              null ||
                                              !$e114.tid.isDescendantOf(
                                                           $currentTid115))
                                            throw $e114;
                                        throw new fabric.worker.
                                                UserAbortException($e114);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e114) {
                                        $commit111 = false;
                                        $currentTid115 = $tm116.getCurrentTid();
                                        if ($currentTid115 != null) {
                                            if ($e114.tid.equals(
                                                            $currentTid115) ||
                                                  !$e114.tid.
                                                  isDescendantOf(
                                                    $currentTid115)) {
                                                throw $e114;
                                            }
                                        }
                                    }
                                } else if ($keepReads113) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit111) {
                                    { val = val$var109; }
                                    if ($retry112) { continue $label110; }
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
                    fabric.metrics.treaties.Treaty mc$var120 = mc;
                    fabric.worker.transaction.TransactionManager $tm127 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled130 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff128 = 1;
                    boolean $doBackoff129 = true;
                    boolean $retry123 = true;
                    boolean $keepReads124 = false;
                    $label121: for (boolean $commit122 = false; !$commit122; ) {
                        if ($backoffEnabled130) {
                            if ($doBackoff129) {
                                if ($backoff128 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff128));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e125) {
                                            
                                        }
                                    }
                                }
                                if ($backoff128 < 5000) $backoff128 *= 2;
                            }
                            $doBackoff129 = $backoff128 <= 32 || !$doBackoff129;
                        }
                        $commit122 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { mc = tmp.createEqualityTreaty(value); }
                            catch (final fabric.worker.RetryException $e125) {
                                throw $e125;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e125) {
                                throw $e125;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e125) {
                                throw $e125;
                            }
                            catch (final Throwable $e125) {
                                $tm127.getCurrentLog().checkRetrySignal();
                                throw $e125;
                            }
                        }
                        catch (final fabric.worker.RetryException $e125) {
                            $commit122 = false;
                            continue $label121;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e125) {
                            $commit122 = false;
                            $retry123 = false;
                            $keepReads124 = $e125.keepReads;
                            if ($tm127.checkForStaleObjects()) {
                                $retry123 = true;
                                $keepReads124 = false;
                                continue $label121;
                            }
                            fabric.common.TransactionID $currentTid126 =
                              $tm127.getCurrentTid();
                            if ($e125.tid == null ||
                                  !$e125.tid.isDescendantOf($currentTid126)) {
                                throw $e125;
                            }
                            throw new fabric.worker.UserAbortException($e125);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e125) {
                            $commit122 = false;
                            fabric.common.TransactionID $currentTid126 =
                              $tm127.getCurrentTid();
                            if ($e125.tid.isDescendantOf($currentTid126))
                                continue $label121;
                            if ($currentTid126.parent != null) {
                                $retry123 = false;
                                throw $e125;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e125) {
                            $commit122 = false;
                            if ($tm127.checkForStaleObjects())
                                continue $label121;
                            $retry123 = false;
                            throw new fabric.worker.AbortException($e125);
                        }
                        finally {
                            if ($commit122) {
                                fabric.common.TransactionID $currentTid126 =
                                  $tm127.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e125) {
                                    $commit122 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e125) {
                                    $commit122 = false;
                                    $retry123 = false;
                                    $keepReads124 = $e125.keepReads;
                                    if ($tm127.checkForStaleObjects()) {
                                        $retry123 = true;
                                        $keepReads124 = false;
                                        continue $label121;
                                    }
                                    if ($e125.tid ==
                                          null ||
                                          !$e125.tid.isDescendantOf(
                                                       $currentTid126))
                                        throw $e125;
                                    throw new fabric.worker.UserAbortException(
                                            $e125);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e125) {
                                    $commit122 = false;
                                    $currentTid126 = $tm127.getCurrentTid();
                                    if ($currentTid126 != null) {
                                        if ($e125.tid.equals($currentTid126) ||
                                              !$e125.tid.isDescendantOf(
                                                           $currentTid126)) {
                                            throw $e125;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads124) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit122) {
                                { mc = mc$var120; }
                                if ($retry123) { continue $label121; }
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
                    fabric.metrics.treaties.Treaty mc$var131 = mc;
                    fabric.worker.transaction.TransactionManager $tm138 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled141 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff139 = 1;
                    boolean $doBackoff140 = true;
                    boolean $retry134 = true;
                    boolean $keepReads135 = false;
                    $label132: for (boolean $commit133 = false; !$commit133; ) {
                        if ($backoffEnabled141) {
                            if ($doBackoff140) {
                                if ($backoff139 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff139));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e136) {
                                            
                                        }
                                    }
                                }
                                if ($backoff139 < 5000) $backoff139 *= 2;
                            }
                            $doBackoff140 = $backoff139 <= 32 || !$doBackoff140;
                        }
                        $commit133 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                mc = tmp.createThresholdTreaty(rate, base,
                                                               time);
                            }
                            catch (final fabric.worker.RetryException $e136) {
                                throw $e136;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e136) {
                                throw $e136;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e136) {
                                throw $e136;
                            }
                            catch (final Throwable $e136) {
                                $tm138.getCurrentLog().checkRetrySignal();
                                throw $e136;
                            }
                        }
                        catch (final fabric.worker.RetryException $e136) {
                            $commit133 = false;
                            continue $label132;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e136) {
                            $commit133 = false;
                            $retry134 = false;
                            $keepReads135 = $e136.keepReads;
                            if ($tm138.checkForStaleObjects()) {
                                $retry134 = true;
                                $keepReads135 = false;
                                continue $label132;
                            }
                            fabric.common.TransactionID $currentTid137 =
                              $tm138.getCurrentTid();
                            if ($e136.tid == null ||
                                  !$e136.tid.isDescendantOf($currentTid137)) {
                                throw $e136;
                            }
                            throw new fabric.worker.UserAbortException($e136);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e136) {
                            $commit133 = false;
                            fabric.common.TransactionID $currentTid137 =
                              $tm138.getCurrentTid();
                            if ($e136.tid.isDescendantOf($currentTid137))
                                continue $label132;
                            if ($currentTid137.parent != null) {
                                $retry134 = false;
                                throw $e136;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e136) {
                            $commit133 = false;
                            if ($tm138.checkForStaleObjects())
                                continue $label132;
                            $retry134 = false;
                            throw new fabric.worker.AbortException($e136);
                        }
                        finally {
                            if ($commit133) {
                                fabric.common.TransactionID $currentTid137 =
                                  $tm138.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e136) {
                                    $commit133 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e136) {
                                    $commit133 = false;
                                    $retry134 = false;
                                    $keepReads135 = $e136.keepReads;
                                    if ($tm138.checkForStaleObjects()) {
                                        $retry134 = true;
                                        $keepReads135 = false;
                                        continue $label132;
                                    }
                                    if ($e136.tid ==
                                          null ||
                                          !$e136.tid.isDescendantOf(
                                                       $currentTid137))
                                        throw $e136;
                                    throw new fabric.worker.UserAbortException(
                                            $e136);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e136) {
                                    $commit133 = false;
                                    $currentTid137 = $tm138.getCurrentTid();
                                    if ($currentTid137 != null) {
                                        if ($e136.tid.equals($currentTid137) ||
                                              !$e136.tid.isDescendantOf(
                                                           $currentTid137)) {
                                            throw $e136;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads135) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit133) {
                                { mc = mc$var131; }
                                if ($retry134) { continue $label132; }
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
                    fabric.metrics.DerivedMetric val$var142 = val;
                    fabric.worker.transaction.TransactionManager $tm149 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled152 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff150 = 1;
                    boolean $doBackoff151 = true;
                    boolean $retry145 = true;
                    boolean $keepReads146 = false;
                    $label143: for (boolean $commit144 = false; !$commit144; ) {
                        if ($backoffEnabled152) {
                            if ($doBackoff151) {
                                if ($backoff150 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff150));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e147) {
                                            
                                        }
                                    }
                                }
                                if ($backoff150 < 5000) $backoff150 *= 2;
                            }
                            $doBackoff151 = $backoff150 <= 32 || !$doBackoff151;
                        }
                        $commit144 = true;
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
                            catch (final fabric.worker.RetryException $e147) {
                                throw $e147;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e147) {
                                throw $e147;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e147) {
                                throw $e147;
                            }
                            catch (final Throwable $e147) {
                                $tm149.getCurrentLog().checkRetrySignal();
                                throw $e147;
                            }
                        }
                        catch (final fabric.worker.RetryException $e147) {
                            $commit144 = false;
                            continue $label143;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e147) {
                            $commit144 = false;
                            $retry145 = false;
                            $keepReads146 = $e147.keepReads;
                            if ($tm149.checkForStaleObjects()) {
                                $retry145 = true;
                                $keepReads146 = false;
                                continue $label143;
                            }
                            fabric.common.TransactionID $currentTid148 =
                              $tm149.getCurrentTid();
                            if ($e147.tid == null ||
                                  !$e147.tid.isDescendantOf($currentTid148)) {
                                throw $e147;
                            }
                            throw new fabric.worker.UserAbortException($e147);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e147) {
                            $commit144 = false;
                            fabric.common.TransactionID $currentTid148 =
                              $tm149.getCurrentTid();
                            if ($e147.tid.isDescendantOf($currentTid148))
                                continue $label143;
                            if ($currentTid148.parent != null) {
                                $retry145 = false;
                                throw $e147;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e147) {
                            $commit144 = false;
                            if ($tm149.checkForStaleObjects())
                                continue $label143;
                            $retry145 = false;
                            throw new fabric.worker.AbortException($e147);
                        }
                        finally {
                            if ($commit144) {
                                fabric.common.TransactionID $currentTid148 =
                                  $tm149.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e147) {
                                    $commit144 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e147) {
                                    $commit144 = false;
                                    $retry145 = false;
                                    $keepReads146 = $e147.keepReads;
                                    if ($tm149.checkForStaleObjects()) {
                                        $retry145 = true;
                                        $keepReads146 = false;
                                        continue $label143;
                                    }
                                    if ($e147.tid ==
                                          null ||
                                          !$e147.tid.isDescendantOf(
                                                       $currentTid148))
                                        throw $e147;
                                    throw new fabric.worker.UserAbortException(
                                            $e147);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e147) {
                                    $commit144 = false;
                                    $currentTid148 = $tm149.getCurrentTid();
                                    if ($currentTid148 != null) {
                                        if ($e147.tid.equals($currentTid148) ||
                                              !$e147.tid.isDescendantOf(
                                                           $currentTid148)) {
                                            throw $e147;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads146) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit144) {
                                { val = val$var142; }
                                if ($retry145) { continue $label143; }
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
                    fabric.metrics.DerivedMetric val$var153 = val;
                    fabric.worker.transaction.TransactionManager $tm160 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled163 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff161 = 1;
                    boolean $doBackoff162 = true;
                    boolean $retry156 = true;
                    boolean $keepReads157 = false;
                    $label154: for (boolean $commit155 = false; !$commit155; ) {
                        if ($backoffEnabled163) {
                            if ($doBackoff162) {
                                if ($backoff161 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff161));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e158) {
                                            
                                        }
                                    }
                                }
                                if ($backoff161 < 5000) $backoff161 *= 2;
                            }
                            $doBackoff162 = $backoff161 <= 32 || !$doBackoff162;
                        }
                        $commit155 = true;
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
                            catch (final fabric.worker.RetryException $e158) {
                                throw $e158;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e158) {
                                throw $e158;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e158) {
                                throw $e158;
                            }
                            catch (final Throwable $e158) {
                                $tm160.getCurrentLog().checkRetrySignal();
                                throw $e158;
                            }
                        }
                        catch (final fabric.worker.RetryException $e158) {
                            $commit155 = false;
                            continue $label154;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e158) {
                            $commit155 = false;
                            $retry156 = false;
                            $keepReads157 = $e158.keepReads;
                            if ($tm160.checkForStaleObjects()) {
                                $retry156 = true;
                                $keepReads157 = false;
                                continue $label154;
                            }
                            fabric.common.TransactionID $currentTid159 =
                              $tm160.getCurrentTid();
                            if ($e158.tid == null ||
                                  !$e158.tid.isDescendantOf($currentTid159)) {
                                throw $e158;
                            }
                            throw new fabric.worker.UserAbortException($e158);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e158) {
                            $commit155 = false;
                            fabric.common.TransactionID $currentTid159 =
                              $tm160.getCurrentTid();
                            if ($e158.tid.isDescendantOf($currentTid159))
                                continue $label154;
                            if ($currentTid159.parent != null) {
                                $retry156 = false;
                                throw $e158;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e158) {
                            $commit155 = false;
                            if ($tm160.checkForStaleObjects())
                                continue $label154;
                            $retry156 = false;
                            throw new fabric.worker.AbortException($e158);
                        }
                        finally {
                            if ($commit155) {
                                fabric.common.TransactionID $currentTid159 =
                                  $tm160.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e158) {
                                    $commit155 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e158) {
                                    $commit155 = false;
                                    $retry156 = false;
                                    $keepReads157 = $e158.keepReads;
                                    if ($tm160.checkForStaleObjects()) {
                                        $retry156 = true;
                                        $keepReads157 = false;
                                        continue $label154;
                                    }
                                    if ($e158.tid ==
                                          null ||
                                          !$e158.tid.isDescendantOf(
                                                       $currentTid159))
                                        throw $e158;
                                    throw new fabric.worker.UserAbortException(
                                            $e158);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e158) {
                                    $commit155 = false;
                                    $currentTid159 = $tm160.getCurrentTid();
                                    if ($currentTid159 != null) {
                                        if ($e158.tid.equals($currentTid159) ||
                                              !$e158.tid.isDescendantOf(
                                                           $currentTid159)) {
                                            throw $e158;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads157) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit155) {
                                { val = val$var153; }
                                if ($retry156) { continue $label154; }
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
                    fabric.metrics.DerivedMetric val$var164 = val;
                    fabric.worker.transaction.TransactionManager $tm171 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled174 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff172 = 1;
                    boolean $doBackoff173 = true;
                    boolean $retry167 = true;
                    boolean $keepReads168 = false;
                    $label165: for (boolean $commit166 = false; !$commit166; ) {
                        if ($backoffEnabled174) {
                            if ($doBackoff173) {
                                if ($backoff172 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff172));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e169) {
                                            
                                        }
                                    }
                                }
                                if ($backoff172 < 5000) $backoff172 *= 2;
                            }
                            $doBackoff173 = $backoff172 <= 32 || !$doBackoff173;
                        }
                        $commit166 = true;
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
                            catch (final fabric.worker.RetryException $e169) {
                                throw $e169;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e169) {
                                throw $e169;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e169) {
                                throw $e169;
                            }
                            catch (final Throwable $e169) {
                                $tm171.getCurrentLog().checkRetrySignal();
                                throw $e169;
                            }
                        }
                        catch (final fabric.worker.RetryException $e169) {
                            $commit166 = false;
                            continue $label165;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e169) {
                            $commit166 = false;
                            $retry167 = false;
                            $keepReads168 = $e169.keepReads;
                            if ($tm171.checkForStaleObjects()) {
                                $retry167 = true;
                                $keepReads168 = false;
                                continue $label165;
                            }
                            fabric.common.TransactionID $currentTid170 =
                              $tm171.getCurrentTid();
                            if ($e169.tid == null ||
                                  !$e169.tid.isDescendantOf($currentTid170)) {
                                throw $e169;
                            }
                            throw new fabric.worker.UserAbortException($e169);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e169) {
                            $commit166 = false;
                            fabric.common.TransactionID $currentTid170 =
                              $tm171.getCurrentTid();
                            if ($e169.tid.isDescendantOf($currentTid170))
                                continue $label165;
                            if ($currentTid170.parent != null) {
                                $retry167 = false;
                                throw $e169;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e169) {
                            $commit166 = false;
                            if ($tm171.checkForStaleObjects())
                                continue $label165;
                            $retry167 = false;
                            throw new fabric.worker.AbortException($e169);
                        }
                        finally {
                            if ($commit166) {
                                fabric.common.TransactionID $currentTid170 =
                                  $tm171.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e169) {
                                    $commit166 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e169) {
                                    $commit166 = false;
                                    $retry167 = false;
                                    $keepReads168 = $e169.keepReads;
                                    if ($tm171.checkForStaleObjects()) {
                                        $retry167 = true;
                                        $keepReads168 = false;
                                        continue $label165;
                                    }
                                    if ($e169.tid ==
                                          null ||
                                          !$e169.tid.isDescendantOf(
                                                       $currentTid170))
                                        throw $e169;
                                    throw new fabric.worker.UserAbortException(
                                            $e169);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e169) {
                                    $commit166 = false;
                                    $currentTid170 = $tm171.getCurrentTid();
                                    if ($currentTid170 != null) {
                                        if ($e169.tid.equals($currentTid170) ||
                                              !$e169.tid.isDescendantOf(
                                                           $currentTid170)) {
                                            throw $e169;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads168) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit166) {
                                { val = val$var164; }
                                if ($retry167) { continue $label165; }
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
                    fabric.worker.metrics.StatsMap result$var175 = result;
                    fabric.worker.transaction.TransactionManager $tm182 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled185 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff183 = 1;
                    boolean $doBackoff184 = true;
                    boolean $retry178 = true;
                    boolean $keepReads179 = false;
                    $label176: for (boolean $commit177 = false; !$commit177; ) {
                        if ($backoffEnabled185) {
                            if ($doBackoff184) {
                                if ($backoff183 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff183));
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
                        $commit177 = true;
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
                            catch (final fabric.worker.RetryException $e180) {
                                throw $e180;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e180) {
                                throw $e180;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e180) {
                                throw $e180;
                            }
                            catch (final Throwable $e180) {
                                $tm182.getCurrentLog().checkRetrySignal();
                                throw $e180;
                            }
                        }
                        catch (final fabric.worker.RetryException $e180) {
                            $commit177 = false;
                            continue $label176;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e180) {
                            $commit177 = false;
                            $retry178 = false;
                            $keepReads179 = $e180.keepReads;
                            if ($tm182.checkForStaleObjects()) {
                                $retry178 = true;
                                $keepReads179 = false;
                                continue $label176;
                            }
                            fabric.common.TransactionID $currentTid181 =
                              $tm182.getCurrentTid();
                            if ($e180.tid == null ||
                                  !$e180.tid.isDescendantOf($currentTid181)) {
                                throw $e180;
                            }
                            throw new fabric.worker.UserAbortException($e180);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e180) {
                            $commit177 = false;
                            fabric.common.TransactionID $currentTid181 =
                              $tm182.getCurrentTid();
                            if ($e180.tid.isDescendantOf($currentTid181))
                                continue $label176;
                            if ($currentTid181.parent != null) {
                                $retry178 = false;
                                throw $e180;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e180) {
                            $commit177 = false;
                            if ($tm182.checkForStaleObjects())
                                continue $label176;
                            $retry178 = false;
                            throw new fabric.worker.AbortException($e180);
                        }
                        finally {
                            if ($commit177) {
                                fabric.common.TransactionID $currentTid181 =
                                  $tm182.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e180) {
                                    $commit177 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e180) {
                                    $commit177 = false;
                                    $retry178 = false;
                                    $keepReads179 = $e180.keepReads;
                                    if ($tm182.checkForStaleObjects()) {
                                        $retry178 = true;
                                        $keepReads179 = false;
                                        continue $label176;
                                    }
                                    if ($e180.tid ==
                                          null ||
                                          !$e180.tid.isDescendantOf(
                                                       $currentTid181))
                                        throw $e180;
                                    throw new fabric.worker.UserAbortException(
                                            $e180);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e180) {
                                    $commit177 = false;
                                    $currentTid181 = $tm182.getCurrentTid();
                                    if ($currentTid181 != null) {
                                        if ($e180.tid.equals($currentTid181) ||
                                              !$e180.tid.isDescendantOf(
                                                           $currentTid181)) {
                                            throw $e180;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads179) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit177) {
                                { result = result$var175; }
                                if ($retry178) { continue $label176; }
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
                    fabric.metrics.treaties.Treaty t$var186 = t;
                    fabric.worker.transaction.TransactionManager $tm193 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled196 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff194 = 1;
                    boolean $doBackoff195 = true;
                    boolean $retry189 = true;
                    boolean $keepReads190 = false;
                    $label187: for (boolean $commit188 = false; !$commit188; ) {
                        if ($backoffEnabled196) {
                            if ($doBackoff195) {
                                if ($backoff194 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff194));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e191) {
                                            
                                        }
                                    }
                                }
                                if ($backoff194 < 5000) $backoff194 *= 2;
                            }
                            $doBackoff195 = $backoff194 <= 32 || !$doBackoff195;
                        }
                        $commit188 = true;
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
                            catch (final fabric.worker.RetryException $e191) {
                                throw $e191;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e191) {
                                throw $e191;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e191) {
                                throw $e191;
                            }
                            catch (final Throwable $e191) {
                                $tm193.getCurrentLog().checkRetrySignal();
                                throw $e191;
                            }
                        }
                        catch (final fabric.worker.RetryException $e191) {
                            $commit188 = false;
                            continue $label187;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e191) {
                            $commit188 = false;
                            $retry189 = false;
                            $keepReads190 = $e191.keepReads;
                            if ($tm193.checkForStaleObjects()) {
                                $retry189 = true;
                                $keepReads190 = false;
                                continue $label187;
                            }
                            fabric.common.TransactionID $currentTid192 =
                              $tm193.getCurrentTid();
                            if ($e191.tid == null ||
                                  !$e191.tid.isDescendantOf($currentTid192)) {
                                throw $e191;
                            }
                            throw new fabric.worker.UserAbortException($e191);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e191) {
                            $commit188 = false;
                            fabric.common.TransactionID $currentTid192 =
                              $tm193.getCurrentTid();
                            if ($e191.tid.isDescendantOf($currentTid192))
                                continue $label187;
                            if ($currentTid192.parent != null) {
                                $retry189 = false;
                                throw $e191;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e191) {
                            $commit188 = false;
                            if ($tm193.checkForStaleObjects())
                                continue $label187;
                            $retry189 = false;
                            throw new fabric.worker.AbortException($e191);
                        }
                        finally {
                            if ($commit188) {
                                fabric.common.TransactionID $currentTid192 =
                                  $tm193.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e191) {
                                    $commit188 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e191) {
                                    $commit188 = false;
                                    $retry189 = false;
                                    $keepReads190 = $e191.keepReads;
                                    if ($tm193.checkForStaleObjects()) {
                                        $retry189 = true;
                                        $keepReads190 = false;
                                        continue $label187;
                                    }
                                    if ($e191.tid ==
                                          null ||
                                          !$e191.tid.isDescendantOf(
                                                       $currentTid192))
                                        throw $e191;
                                    throw new fabric.worker.UserAbortException(
                                            $e191);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e191) {
                                    $commit188 = false;
                                    $currentTid192 = $tm193.getCurrentTid();
                                    if ($currentTid192 != null) {
                                        if ($e191.tid.equals($currentTid192) ||
                                              !$e191.tid.isDescendantOf(
                                                           $currentTid192)) {
                                            throw $e191;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads190) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit188) {
                                { t = t$var186; }
                                if ($retry189) { continue $label187; }
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
                    fabric.metrics.treaties.Treaty t$var197 = t;
                    fabric.worker.transaction.TransactionManager $tm204 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled207 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff205 = 1;
                    boolean $doBackoff206 = true;
                    boolean $retry200 = true;
                    boolean $keepReads201 = false;
                    $label198: for (boolean $commit199 = false; !$commit199; ) {
                        if ($backoffEnabled207) {
                            if ($doBackoff206) {
                                if ($backoff205 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff205));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e202) {
                                            
                                        }
                                    }
                                }
                                if ($backoff205 < 5000) $backoff205 *= 2;
                            }
                            $doBackoff206 = $backoff205 <= 32 || !$doBackoff206;
                        }
                        $commit199 = true;
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
                            catch (final fabric.worker.RetryException $e202) {
                                throw $e202;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e202) {
                                throw $e202;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e202) {
                                throw $e202;
                            }
                            catch (final Throwable $e202) {
                                $tm204.getCurrentLog().checkRetrySignal();
                                throw $e202;
                            }
                        }
                        catch (final fabric.worker.RetryException $e202) {
                            $commit199 = false;
                            continue $label198;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e202) {
                            $commit199 = false;
                            $retry200 = false;
                            $keepReads201 = $e202.keepReads;
                            if ($tm204.checkForStaleObjects()) {
                                $retry200 = true;
                                $keepReads201 = false;
                                continue $label198;
                            }
                            fabric.common.TransactionID $currentTid203 =
                              $tm204.getCurrentTid();
                            if ($e202.tid == null ||
                                  !$e202.tid.isDescendantOf($currentTid203)) {
                                throw $e202;
                            }
                            throw new fabric.worker.UserAbortException($e202);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e202) {
                            $commit199 = false;
                            fabric.common.TransactionID $currentTid203 =
                              $tm204.getCurrentTid();
                            if ($e202.tid.isDescendantOf($currentTid203))
                                continue $label198;
                            if ($currentTid203.parent != null) {
                                $retry200 = false;
                                throw $e202;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e202) {
                            $commit199 = false;
                            if ($tm204.checkForStaleObjects())
                                continue $label198;
                            $retry200 = false;
                            throw new fabric.worker.AbortException($e202);
                        }
                        finally {
                            if ($commit199) {
                                fabric.common.TransactionID $currentTid203 =
                                  $tm204.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e202) {
                                    $commit199 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e202) {
                                    $commit199 = false;
                                    $retry200 = false;
                                    $keepReads201 = $e202.keepReads;
                                    if ($tm204.checkForStaleObjects()) {
                                        $retry200 = true;
                                        $keepReads201 = false;
                                        continue $label198;
                                    }
                                    if ($e202.tid ==
                                          null ||
                                          !$e202.tid.isDescendantOf(
                                                       $currentTid203))
                                        throw $e202;
                                    throw new fabric.worker.UserAbortException(
                                            $e202);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e202) {
                                    $commit199 = false;
                                    $currentTid203 = $tm204.getCurrentTid();
                                    if ($currentTid203 != null) {
                                        if ($e202.tid.equals($currentTid203) ||
                                              !$e202.tid.isDescendantOf(
                                                           $currentTid203)) {
                                            throw $e202;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads201) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit199) {
                                { t = t$var197; }
                                if ($retry200) { continue $label198; }
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
    public static final long jlc$SourceLastModified$fabil = 1549295496000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1cCXQc1ZV9XVpsyYvkfV8wCuBNDZjNSzC2sLGMbAtLsrGNLUrdJansUldT9dtusTqZYczJgEPAeAwJMEzMEMDAwDgsJ5hlwmZI2CckDFtmgDDHOIZDBjg5ZJj3fr1e1F311ZWRzql3S1X/v//uf/+/v1R1HTgKFa4DMzr1DtOqF71Jw61frnc0NjXrjmvEGyzddVvxantsSHnj3k/uik/VQGuCoTE9YSfMmG61J1wBw5u26tv1aMIQ0ba1jQs3QVWMMq7Q3W4B2qalaQemJ22rt8uyBRdSpP+m2dE9/7Cl9qEyqNkINWaiRejCjDXYCWGkxUYY2mP0dBiOuyQeN+IbYUTCMOIthmPqlnkpJrQTG2Gka3YldJFyDHet4drWdko40k0lDUeWmblI5ttotpOKCdtB82s981PCtKJNpisWNkFlp2lYcfcSuBLKm6Ci09K7MOHYpgyLqNQYXU7XMXm1iWY6nXrMyGQp32Ym4gKmFebIMq47HxNg1kE9hui2s0WVJ3S8ACM9kyw90RVtEY6Z6MKkFXYKSxEwMVApJhqc1GPb9C6jXcD4wnTN3i1MVSWrhbIIGFOYTGpCn00s8Fmet46uXrT7ssSKhAYRtDluxCyyfzBmmlqQaa3RaThGImZ4GYfOatqrjz10jQaAiccUJPbSPHL55+fMmfrUC16aST5p1nRsNWKiPba/Y/hrkxtmzi8jMwYnbdekptCHufRqM99ZmE5iax+b1Ug36zM3n1r73Iad9xhHNKhuhMqYbaV6sFWNiNk9SdMynPOMhOHowog3QpWRiDfI+40wCM+bzIThXV3T2ekaohHKLXmp0pb/YxV1ogqqokF4biY67cx5Uhfd8jydBIBaPCACUHYmQHIsnp8EUIEuaox22z1GtMNKGTuweUfxMHQn1h3FfuuYsbkxO9kbdZ1Y1EklhIkpvetRbEoIbnSVxHo0IjmQytJkee2OSAQrdVrMjhsduose4taytNnCDrHCtuKG0x6zdh9qhFGHbpYtpopauYstVdZJBL08uTA+5Ofdk1q67PP721/yWhvl5SrDxusZV8/G1XvGoT1DqQPVY0iqx5B0IJKub7it8V7ZTipd2aGyKoaiigVJSxedttOThkhE8hkt88sGgu7dhmEDI8PQmS2bV158zYwybJnJHeXkLExaV9hPctGlEc90bPztsZpdn3z5wN4r7FyPEVBX1JGLc1JHnFFYOY4dM+IY6HLqZ03Xf95+6Io6jYJIFcY3oWMLxGAxtbCMPh1yYSa4UW1UNMEQqgPdoluZiFQtuh17R+6KdPpwEiM9/1NlFRgo4+J3W5K3/vbl/54nR4xMCK3Ji7UthliY121JWY3soCNydd/qGAame3df8403Hd21SVY8pjjer8A6kg3YXXXsp7Zz9QuX/O799/a/qeWcJaAymeqwzFhachnxLf5F8PhfOqjv0QVCjMAN3O+nZzt+kko+IWcbhgALwxCa7ta1JXrsuNlp6h2WQS3lm5rvnPLzT3fXeu628IpXeQ7M6V9B7vqEpbDzpS1fTZVqIjEagnL1l0vmxbVROc1LHEfvJTvS33t9ys3P67diy8eo5JqXGjLQgKwPkA48VdbFXClPKbh3GokZXm1Nltc1tzjGL6fBMtcWN0YP/GRiw9lHvK6ebYuk4zifrr5Oz+smp97T8z/ajMpnNRi0EWrlOK0nxDodgxU2g4040roNfLEJhvW533fU9IaIhdm+NrmwH+QVW9gLciEGzyk1nVd7Dd9rOFgRQ6mSZmKjigHU38l4Dd0dlSQ5Oh0BebJAZjleyhNIzMw0xqqkYwu00oins2o1UjuE1e1ivDJPrYBBmC1tGq7MM0bACRwDd9jONsPJhkJORdEi3btKT8rkEwrjndeFSZ6RtaGGbDgRbcHy1ycZL/Shtsyfmkans5Cf2dOTEtSiZTGzBQwRjoHTNcNdaqcz5k8rCOGZfp9JR8kmSjPT6uIG6x0YPfWYyNWm/KvhgTTO2JbHJK9lRzIGeaGfWlE9RxNkkKm9Kqo9y8apbzqNXWFK0BxJzu/2f3/PbfE1d57izWRG9p13LEukeu77zV9+Vb/vg8M+I1uVsJNzLWO7YeXZuAyLPK5osr5KTiFzneiDI1PmN2z7qMsrdlqBiYWp71514PB5J8Ru0KAs21uK5q19My3s20eqHQOn3YnWPj1letYJVeSEcXicDFD5PONj+c0p1wiLu4l0bkH/GMxKHmV8qNCj/tEsrrjXSWKLgIrtFEt8glyzY/bgOLWdJ7LGNXt+8G397j2e57zZ/vFFE+78PN6MXxY2THYHaj/HqUqROZb/4YErfvGzK3ZpbOh6HMTidirTJDf1rebZeCwAGPQ246MB1UxCL65UyvII44PBlRrJuWar1OoqajZFAiflFTSZzAatyQW9/lxczOHizJu/yS5fQE925+V4nI2mrmGcWGIr0mTUNLfjMC5oAkhry4I2VcsqJzAOCaav5cJOba4Ovqeog78h0Yux1yu6XVYFXdvh50QcVeACgOrrGZPhnEhZbEazJCfWSq0/UBC4lsTVAhcsVsrXcLl0acCjTQ5fEqt/Xap7ZCcncWWBV2pY068YnyzdKx6pPQpSe0nsxlGJvRLETTrlODwsgGHHGN8K5xTK8hvGV0M45VaF/beT2CegrMdM5Cwo6DKL8MAxfPh6xjlhfHKFn09qWdNsxilhfXKXgtPdJO4QUM0+CaCWdcmNACPfZ3w+nEsoy3OMT4Vwyb8ozJcj0b3kEj0daHcUj7sAxg7xcMxH4eymLB8yvleS3V6Aekxh9y9IHBQwossQyy5J6ZYpeuUcrDcTsKcWBOzMTM6bqvVSKt+QvRKPe5HsDxnXDUj7I01tjOeV3v68inhWURGyDT0pYDy3v6L6oPuH/Lx6Hh4vAExcyzg6nFcpyyjGYcGMyqStZZKMFPKO3EOa7dXTNozSlp3okmW+rKD6BonDuGxCjq3djuF221Y8mKT0ZgceGIsn/pnx7gHxJmn6GeMtwdzLparynDez4hVZ9n8oyL5L4t8FTMj5tQTOcpG3GI+PAKa2Mh6ncOx3ixZyMst0xnElNdWtuab6oYLSxyTeL9l/coRuxOMrgOn7GNvD+C9whCZNWxgv6LftZiLKqL7r1hZhO9llVvEiVVaKF4D/qKiUP5H4hGZauEQzlgipNUesoDqwz0bwn+NfZLxxIKpDarqB8eqSPH4MlwKT8td1K3EJKjdxvNXGlhFb9Vd7j+31VnSFG/N5CT878P6R14dNuV/uA5bTfiypry58olH8wKLPcwhJfWiW2OlEbCEeGNqGDGWsFND8/9xJ7jPn593pAdeZ58a8UVH+fwaKCPBmRqRc5WpcvnSaCd3KbGRUWkaiS3T7Bd8yrFs6/TZd6Olsw8/bYMBIbdD+At1qzSTwdiBMuz77iCuTIu1LZJNHRJaaN8hIu4L7SmSM4h5FqshIpB0jCzOG1eYs9zbYPKNkyd8otE2mi1/irE6Px0vpk3MATlzNmB8z//o+SZrGMlaV1ic9J5L5dQpqZEFkKlLDmWop1PCY9TrjjweEGmm6hfG6sNTqFdROJnESUdPTCmojKcNJaMBmgLnvMgbNxYOprSmgNoI1Pcf4SDC1fKPPUtxbQOJUHBxwxGxzjWYKLsKvDw/qsG3L0BN+TL+D9uBgV1/u4dzPB4QpafqM8felMV2muEdz4shidB0y9WiupWRbgwi1I6HZjOMHhBBpGsc4uDRCaxT3aFoRacwntFRJ6GIsdi3j4gEhRJrOZoyWRmiD4p6M2S35hNYpCelYbC9j54AQIk0G47rSCMUU9yg2RDbnE1rtR0hOqampmbgQnslYHUCIRPGUWmapYoTSLLcU92iHIdIlYKw3l6jjuUSdN4mo84t5mf2HiAA4+UHGmxUsild8Mss+xhuCWeSt449Jc1MKKjtI4DRqsHSCne71M162qVNR59UInzG+GmB8uDZFml5h7H9PJTOtmOr/BIveAXIDn1xJvlcp6oKm3pHezJa+X1vMPJmIXAMwbwXjgnBepCzzGeeV1havVdzbTWIXjkCu3pPkPf5XgvyHi4zTLmPcNCD+I00bGc8vpU1Gdkqz9yoo7SNxvZqS9MREVHkTFvwO40vhPEFZXmR8pjRP3K64dweJWzCeWbor2pJxXRiBzsAVUuR2gDMmeXj61wPiDNL0FeMnIZxxt4LVvSR+2i8r6Y/pqPWfkNUNjJeF8wdluZRRlOaPhxT3DpK4T8DwlLS6kVaw2LP9+rX0CS5YIwcAzvyE8ckA68P5hDQ9wfhACJ88rmD2BImHS2KW7ScPAJx1LuO8AGYBfqEspzLOCaaQb6FixzRCM/3IUzjkbDcsO2Z6m0++HqFe8hjA/MsZNwyIR0jThYyNITzyioLTayQO98MpO3o8jiW/zvhMAKcAX1CWXzIeKs0Xv1Xce5vEGzjkJWzT9R3ysmMHLqsWlnu44MMBcQRp+i/GN0M44j8VhMiyyDsqQtILNGT9DuDsCg+/ezCcFyjLvzLeF2x5wd57ZCedeXOyIwoOR0l8jN3b4AcJzbZlxrIPVhb5T36yz1eMBE53YkaPkRD1y3LneUqKpkZ+VbQc+R0BOOcGxrZwVURZWhlXB1dR4S62VztfK2rnzyS+KKodunrMj0cXqkerzk0zBr0C4M9DZpnAOCqYR96jhuxThjx/a5FgRho1kMg3AmpEZlO+H0otaM9ogPNGe7j8xXCUKMthxl+W3Hrz/KNVK9gMJVERgs0CNGU6QGONhyveC8eGsrzL+FYwm8Iooo1ScBhDYriA0Y7RSSTWG/q2Za4we3C0daUKPyLdaEUdErmTcXk4IpRlGePiYCIFe8OZpZDcY3WNWMrBLkGvBSViZlK3/Pt7rhYmK2phBolxAib51UK7Y/TYci7oXxmnoY0nA5y/hHFsuMqgLGMYa0J4daaCz2wSdRg5mE8TvZVn9fpRoAPWYPlI4/yXGK8LoOA76gU9u5SarmW8MphZwZNopjdPQe90EnMFjOEnlv2zlI7qwFJWIdn7GFvDOYqytDCuKoWONiVHZ5GCztkkzkQ6fXko2t0YyrsIS1kP0Pwl4ythnEZlFk1VRrOmlxkfD9EclysIriBxjqBfF/UkU8JYF7TrkOXVDrB2tIcXHB0QXqTpU8a3Q/BqVvCijWNtJXYz5tUSvIqXzBZj+ThIt5zOOGRAmJGmag/Xfh2C2UYFs4tItArvNz7IrEm5JJbkKKDbAK0XMYZ6myqQHGmazTghBDnFC60a7RFrW7C/Mbm2fteWWe/tAGjbyRjqdZ1AgqSpjbEhBMGEgiBp0LpxYpLpb4qlWrbLXQWw7mZGZ0CokaZLGNtDUEsrqF1KwsmFktXK1dxMLP1qgPXLGaNheAWt5qSmesZpwbzyzf6+4t7fkrhcwDDTbTETXZaReZwYWeQ3iuEKVbsO4MLhHq4P94qczPIhY/+vyGXmXvP7WYnRQCzXXpmX3lqyF0iD7w619veKSvkRib8jP5M6o58XzDYhoR9hndzFGG4FJ7O0Mpa0gpMju3attHSfggW9saXdKGBUPovcuO5PZimWgR1xw6WMS8KRoSznMC4oicy1ua53h4LMT0n8pFSXbEPltwJsnO7hhifCsaAshxgVj7dzKzd2SY7KPQoqB0jcWbpf5CsKNLDdgYzuYdwSOpj4vKIgNW1mbC6FqJwk5xM9qCD6MIn7C4l60+ZA161Ecx4C2NzGGO51TZllFGOY1zVJyLcVtUMKRrRXrT1Kg7fHqP+3+yQnFw16GGDLSR5uDtkcKcshRkVzzO2NyOZYROw5BbEXSDwtYLIvsf6CxhY07mmA9usZF4bjR1kWMJ5WCr98armmqNg41mjjWHsxtON2oVXPAly8zcP2oNVAADHK8injx8HEKqShFX6Oy7FTbC9rtKTQ3vgrvCdDC+3YHQbQZ3l48b8NSGghTU8zHiyFe9G7wznuip1ojXaitXcCufcTbWg+8w5AzGQ8J5yDKctixvnBJAtf+dcU29IatTHtYwGjPUIlvuVOk5APAOLjPYw9F44IZXmWsaTf8nBLlRb/ScHmSxLHBEzyY9NfXKGZ8xEA4yLGaeFIUZapjONLIcXbydLwvyhIfUvi67AuwlWkdgygc5SHxn3h2FCWA4z/HMymYFKSR6msMphSGb0KVhYJ7ycZQS5Gs75AZvczDszkhDRtZix9cpJPuFZBeCSJ6iDC/YSNuVggQvdID7v+GMqVMstRxj+UEjYiE6TNExR8JpEYLeiH/fTbaKOVvmUSqfKzfjUWjXFi62eM94aznrLcw7i/pFgh54uLpJkzFBToja6yKQLGeS5ZkogviQn549C8nzwV/qRmu23G/UieiRZGZaeTmHg9HEnK8hrjS8Ekcy7yFmRlivd2y+i93bKZOB/WXddwRLPtipidiGe+slF2QlpApfdyG303YpLPp1v4I0KxhmeM/R+dP2dMwGdbxhd91onz3X9bzeBxt7W95f0OIfOBoKomGNyZsqz87yvknVcmHaPTlJVWJeXwpORzuoDhfX99JuTvFeiMKqBsnpfuLKTlpaP/5suKlI8HvR+m4Qr/eL9vDSzhzwa0pLKvtE+UxU9MOfT5qgNfjPu6cnDrB/JDIljH039/1cpo8h8bLvpxw5uz1ty2a+fB9Ifw4PqdR3+4YcmJv95fF/k/ZCdkxFZLAAA=";
}
