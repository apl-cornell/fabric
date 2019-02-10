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
                    fabric.metrics.DerivedMetric val$var0 = val;
                    fabric.worker.transaction.TransactionManager $tm7 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled10 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff8 = 1;
                    boolean $doBackoff9 = true;
                    boolean $retry3 = true;
                    boolean $keepReads4 = false;
                    $label1: for (boolean $commit2 = false; !$commit2; ) {
                        if ($backoffEnabled10) {
                            if ($doBackoff9) {
                                if ($backoff8 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff8));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e5) {
                                            
                                        }
                                    }
                                }
                                if ($backoff8 < 5000) $backoff8 *= 2;
                            }
                            $doBackoff9 = $backoff8 <= 32 || !$doBackoff9;
                        }
                        $commit2 = true;
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
                            catch (final fabric.worker.RetryException $e5) {
                                throw $e5;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e5) {
                                throw $e5;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e5) {
                                throw $e5;
                            }
                            catch (final Throwable $e5) {
                                $tm7.getCurrentLog().checkRetrySignal();
                                throw $e5;
                            }
                        }
                        catch (final fabric.worker.RetryException $e5) {
                            $commit2 = false;
                            continue $label1;
                        }
                        catch (fabric.worker.TransactionAbortingException $e5) {
                            $commit2 = false;
                            $retry3 = false;
                            $keepReads4 = $e5.keepReads;
                            if ($tm7.checkForStaleObjects()) {
                                $retry3 = true;
                                $keepReads4 = false;
                                continue $label1;
                            }
                            fabric.common.TransactionID $currentTid6 =
                              $tm7.getCurrentTid();
                            if ($e5.tid == null ||
                                  !$e5.tid.isDescendantOf($currentTid6)) {
                                throw $e5;
                            }
                            throw new fabric.worker.UserAbortException($e5);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e5) {
                            $commit2 = false;
                            fabric.common.TransactionID $currentTid6 =
                              $tm7.getCurrentTid();
                            if ($e5.tid.isDescendantOf($currentTid6))
                                continue $label1;
                            if ($currentTid6.parent != null) {
                                $retry3 = false;
                                throw $e5;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e5) {
                            $commit2 = false;
                            if ($tm7.checkForStaleObjects()) continue $label1;
                            $retry3 = false;
                            throw new fabric.worker.AbortException($e5);
                        }
                        finally {
                            if ($commit2) {
                                fabric.common.TransactionID $currentTid6 =
                                  $tm7.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.AbortException $e5) {
                                    $commit2 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e5) {
                                    $commit2 = false;
                                    $retry3 = false;
                                    $keepReads4 = $e5.keepReads;
                                    if ($tm7.checkForStaleObjects()) {
                                        $retry3 = true;
                                        $keepReads4 = false;
                                        continue $label1;
                                    }
                                    if ($e5.tid == null ||
                                          !$e5.tid.isDescendantOf($currentTid6))
                                        throw $e5;
                                    throw new fabric.worker.UserAbortException(
                                            $e5);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e5) {
                                    $commit2 = false;
                                    $currentTid6 = $tm7.getCurrentTid();
                                    if ($currentTid6 != null) {
                                        if ($e5.tid.equals($currentTid6) ||
                                              !$e5.tid.isDescendantOf(
                                                         $currentTid6)) {
                                            throw $e5;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads4) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit2) {
                                { val = val$var0; }
                                if ($retry3) { continue $label1; }
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
                    fabric.metrics.DerivedMetric val$var11 = val;
                    fabric.worker.transaction.TransactionManager $tm18 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled21 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff19 = 1;
                    boolean $doBackoff20 = true;
                    boolean $retry14 = true;
                    boolean $keepReads15 = false;
                    $label12: for (boolean $commit13 = false; !$commit13; ) {
                        if ($backoffEnabled21) {
                            if ($doBackoff20) {
                                if ($backoff19 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff19));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e16) {
                                            
                                        }
                                    }
                                }
                                if ($backoff19 < 5000) $backoff19 *= 2;
                            }
                            $doBackoff20 = $backoff19 <= 32 || !$doBackoff20;
                        }
                        $commit13 = true;
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
                            catch (final fabric.worker.RetryException $e16) {
                                throw $e16;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e16) {
                                throw $e16;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e16) {
                                throw $e16;
                            }
                            catch (final Throwable $e16) {
                                $tm18.getCurrentLog().checkRetrySignal();
                                throw $e16;
                            }
                        }
                        catch (final fabric.worker.RetryException $e16) {
                            $commit13 = false;
                            continue $label12;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e16) {
                            $commit13 = false;
                            $retry14 = false;
                            $keepReads15 = $e16.keepReads;
                            if ($tm18.checkForStaleObjects()) {
                                $retry14 = true;
                                $keepReads15 = false;
                                continue $label12;
                            }
                            fabric.common.TransactionID $currentTid17 =
                              $tm18.getCurrentTid();
                            if ($e16.tid == null ||
                                  !$e16.tid.isDescendantOf($currentTid17)) {
                                throw $e16;
                            }
                            throw new fabric.worker.UserAbortException($e16);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e16) {
                            $commit13 = false;
                            fabric.common.TransactionID $currentTid17 =
                              $tm18.getCurrentTid();
                            if ($e16.tid.isDescendantOf($currentTid17))
                                continue $label12;
                            if ($currentTid17.parent != null) {
                                $retry14 = false;
                                throw $e16;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e16) {
                            $commit13 = false;
                            if ($tm18.checkForStaleObjects()) continue $label12;
                            $retry14 = false;
                            throw new fabric.worker.AbortException($e16);
                        }
                        finally {
                            if ($commit13) {
                                fabric.common.TransactionID $currentTid17 =
                                  $tm18.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e16) {
                                    $commit13 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e16) {
                                    $commit13 = false;
                                    $retry14 = false;
                                    $keepReads15 = $e16.keepReads;
                                    if ($tm18.checkForStaleObjects()) {
                                        $retry14 = true;
                                        $keepReads15 = false;
                                        continue $label12;
                                    }
                                    if ($e16.tid ==
                                          null ||
                                          !$e16.tid.isDescendantOf(
                                                      $currentTid17))
                                        throw $e16;
                                    throw new fabric.worker.UserAbortException(
                                            $e16);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e16) {
                                    $commit13 = false;
                                    $currentTid17 = $tm18.getCurrentTid();
                                    if ($currentTid17 != null) {
                                        if ($e16.tid.equals($currentTid17) ||
                                              !$e16.tid.isDescendantOf(
                                                          $currentTid17)) {
                                            throw $e16;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads15) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit13) {
                                { val = val$var11; }
                                if ($retry14) { continue $label12; }
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
                        fabric.metrics.DerivedMetric val$var22 = val;
                        fabric.worker.transaction.TransactionManager $tm29 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled32 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff30 = 1;
                        boolean $doBackoff31 = true;
                        boolean $retry25 = true;
                        boolean $keepReads26 = false;
                        $label23: for (boolean $commit24 = false; !$commit24;
                                       ) {
                            if ($backoffEnabled32) {
                                if ($doBackoff31) {
                                    if ($backoff30 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff30));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e27) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff30 < 5000) $backoff30 *= 2;
                                }
                                $doBackoff31 = $backoff30 <= 32 ||
                                                 !$doBackoff31;
                            }
                            $commit24 = true;
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
                                         RetryException $e27) {
                                    throw $e27;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e27) {
                                    throw $e27;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e27) {
                                    throw $e27;
                                }
                                catch (final Throwable $e27) {
                                    $tm29.getCurrentLog().checkRetrySignal();
                                    throw $e27;
                                }
                            }
                            catch (final fabric.worker.RetryException $e27) {
                                $commit24 = false;
                                continue $label23;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e27) {
                                $commit24 = false;
                                $retry25 = false;
                                $keepReads26 = $e27.keepReads;
                                if ($tm29.checkForStaleObjects()) {
                                    $retry25 = true;
                                    $keepReads26 = false;
                                    continue $label23;
                                }
                                fabric.common.TransactionID $currentTid28 =
                                  $tm29.getCurrentTid();
                                if ($e27.tid == null ||
                                      !$e27.tid.isDescendantOf($currentTid28)) {
                                    throw $e27;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e27);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e27) {
                                $commit24 = false;
                                fabric.common.TransactionID $currentTid28 =
                                  $tm29.getCurrentTid();
                                if ($e27.tid.isDescendantOf($currentTid28))
                                    continue $label23;
                                if ($currentTid28.parent != null) {
                                    $retry25 = false;
                                    throw $e27;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e27) {
                                $commit24 = false;
                                if ($tm29.checkForStaleObjects())
                                    continue $label23;
                                $retry25 = false;
                                throw new fabric.worker.AbortException($e27);
                            }
                            finally {
                                if ($commit24) {
                                    fabric.common.TransactionID $currentTid28 =
                                      $tm29.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e27) {
                                        $commit24 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e27) {
                                        $commit24 = false;
                                        $retry25 = false;
                                        $keepReads26 = $e27.keepReads;
                                        if ($tm29.checkForStaleObjects()) {
                                            $retry25 = true;
                                            $keepReads26 = false;
                                            continue $label23;
                                        }
                                        if ($e27.tid ==
                                              null ||
                                              !$e27.tid.isDescendantOf(
                                                          $currentTid28))
                                            throw $e27;
                                        throw new fabric.worker.
                                                UserAbortException($e27);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e27) {
                                        $commit24 = false;
                                        $currentTid28 = $tm29.getCurrentTid();
                                        if ($currentTid28 != null) {
                                            if ($e27.tid.equals(
                                                           $currentTid28) ||
                                                  !$e27.tid.isDescendantOf(
                                                              $currentTid28)) {
                                                throw $e27;
                                            }
                                        }
                                    }
                                } else if ($keepReads26) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit24) {
                                    { val = val$var22; }
                                    if ($retry25) { continue $label23; }
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
                        fabric.metrics.DerivedMetric val$var33 = val;
                        fabric.worker.transaction.TransactionManager $tm40 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled43 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff41 = 1;
                        boolean $doBackoff42 = true;
                        boolean $retry36 = true;
                        boolean $keepReads37 = false;
                        $label34: for (boolean $commit35 = false; !$commit35;
                                       ) {
                            if ($backoffEnabled43) {
                                if ($doBackoff42) {
                                    if ($backoff41 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff41));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e38) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff41 < 5000) $backoff41 *= 2;
                                }
                                $doBackoff42 = $backoff41 <= 32 ||
                                                 !$doBackoff42;
                            }
                            $commit35 = true;
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
                                         RetryException $e38) {
                                    throw $e38;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e38) {
                                    throw $e38;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e38) {
                                    throw $e38;
                                }
                                catch (final Throwable $e38) {
                                    $tm40.getCurrentLog().checkRetrySignal();
                                    throw $e38;
                                }
                            }
                            catch (final fabric.worker.RetryException $e38) {
                                $commit35 = false;
                                continue $label34;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e38) {
                                $commit35 = false;
                                $retry36 = false;
                                $keepReads37 = $e38.keepReads;
                                if ($tm40.checkForStaleObjects()) {
                                    $retry36 = true;
                                    $keepReads37 = false;
                                    continue $label34;
                                }
                                fabric.common.TransactionID $currentTid39 =
                                  $tm40.getCurrentTid();
                                if ($e38.tid == null ||
                                      !$e38.tid.isDescendantOf($currentTid39)) {
                                    throw $e38;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e38);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e38) {
                                $commit35 = false;
                                fabric.common.TransactionID $currentTid39 =
                                  $tm40.getCurrentTid();
                                if ($e38.tid.isDescendantOf($currentTid39))
                                    continue $label34;
                                if ($currentTid39.parent != null) {
                                    $retry36 = false;
                                    throw $e38;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e38) {
                                $commit35 = false;
                                if ($tm40.checkForStaleObjects())
                                    continue $label34;
                                $retry36 = false;
                                throw new fabric.worker.AbortException($e38);
                            }
                            finally {
                                if ($commit35) {
                                    fabric.common.TransactionID $currentTid39 =
                                      $tm40.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e38) {
                                        $commit35 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e38) {
                                        $commit35 = false;
                                        $retry36 = false;
                                        $keepReads37 = $e38.keepReads;
                                        if ($tm40.checkForStaleObjects()) {
                                            $retry36 = true;
                                            $keepReads37 = false;
                                            continue $label34;
                                        }
                                        if ($e38.tid ==
                                              null ||
                                              !$e38.tid.isDescendantOf(
                                                          $currentTid39))
                                            throw $e38;
                                        throw new fabric.worker.
                                                UserAbortException($e38);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e38) {
                                        $commit35 = false;
                                        $currentTid39 = $tm40.getCurrentTid();
                                        if ($currentTid39 != null) {
                                            if ($e38.tid.equals(
                                                           $currentTid39) ||
                                                  !$e38.tid.isDescendantOf(
                                                              $currentTid39)) {
                                                throw $e38;
                                            }
                                        }
                                    }
                                } else if ($keepReads37) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit35) {
                                    { val = val$var33; }
                                    if ($retry36) { continue $label34; }
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
                    fabric.metrics.DerivedMetric val$var44 = val;
                    fabric.worker.transaction.TransactionManager $tm51 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled54 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff52 = 1;
                    boolean $doBackoff53 = true;
                    boolean $retry47 = true;
                    boolean $keepReads48 = false;
                    $label45: for (boolean $commit46 = false; !$commit46; ) {
                        if ($backoffEnabled54) {
                            if ($doBackoff53) {
                                if ($backoff52 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff52));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e49) {
                                            
                                        }
                                    }
                                }
                                if ($backoff52 < 5000) $backoff52 *= 2;
                            }
                            $doBackoff53 = $backoff52 <= 32 || !$doBackoff53;
                        }
                        $commit46 = true;
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
                            catch (final fabric.worker.RetryException $e49) {
                                throw $e49;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e49) {
                                throw $e49;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e49) {
                                throw $e49;
                            }
                            catch (final Throwable $e49) {
                                $tm51.getCurrentLog().checkRetrySignal();
                                throw $e49;
                            }
                        }
                        catch (final fabric.worker.RetryException $e49) {
                            $commit46 = false;
                            continue $label45;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e49) {
                            $commit46 = false;
                            $retry47 = false;
                            $keepReads48 = $e49.keepReads;
                            if ($tm51.checkForStaleObjects()) {
                                $retry47 = true;
                                $keepReads48 = false;
                                continue $label45;
                            }
                            fabric.common.TransactionID $currentTid50 =
                              $tm51.getCurrentTid();
                            if ($e49.tid == null ||
                                  !$e49.tid.isDescendantOf($currentTid50)) {
                                throw $e49;
                            }
                            throw new fabric.worker.UserAbortException($e49);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e49) {
                            $commit46 = false;
                            fabric.common.TransactionID $currentTid50 =
                              $tm51.getCurrentTid();
                            if ($e49.tid.isDescendantOf($currentTid50))
                                continue $label45;
                            if ($currentTid50.parent != null) {
                                $retry47 = false;
                                throw $e49;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e49) {
                            $commit46 = false;
                            if ($tm51.checkForStaleObjects()) continue $label45;
                            $retry47 = false;
                            throw new fabric.worker.AbortException($e49);
                        }
                        finally {
                            if ($commit46) {
                                fabric.common.TransactionID $currentTid50 =
                                  $tm51.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e49) {
                                    $commit46 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e49) {
                                    $commit46 = false;
                                    $retry47 = false;
                                    $keepReads48 = $e49.keepReads;
                                    if ($tm51.checkForStaleObjects()) {
                                        $retry47 = true;
                                        $keepReads48 = false;
                                        continue $label45;
                                    }
                                    if ($e49.tid ==
                                          null ||
                                          !$e49.tid.isDescendantOf(
                                                      $currentTid50))
                                        throw $e49;
                                    throw new fabric.worker.UserAbortException(
                                            $e49);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e49) {
                                    $commit46 = false;
                                    $currentTid50 = $tm51.getCurrentTid();
                                    if ($currentTid50 != null) {
                                        if ($e49.tid.equals($currentTid50) ||
                                              !$e49.tid.isDescendantOf(
                                                          $currentTid50)) {
                                            throw $e49;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads48) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit46) {
                                { val = val$var44; }
                                if ($retry47) { continue $label45; }
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
                    fabric.metrics.DerivedMetric val$var55 = val;
                    fabric.worker.transaction.TransactionManager $tm62 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled65 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff63 = 1;
                    boolean $doBackoff64 = true;
                    boolean $retry58 = true;
                    boolean $keepReads59 = false;
                    $label56: for (boolean $commit57 = false; !$commit57; ) {
                        if ($backoffEnabled65) {
                            if ($doBackoff64) {
                                if ($backoff63 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff63));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e60) {
                                            
                                        }
                                    }
                                }
                                if ($backoff63 < 5000) $backoff63 *= 2;
                            }
                            $doBackoff64 = $backoff63 <= 32 || !$doBackoff64;
                        }
                        $commit57 = true;
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
                            catch (final fabric.worker.RetryException $e60) {
                                throw $e60;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e60) {
                                throw $e60;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e60) {
                                throw $e60;
                            }
                            catch (final Throwable $e60) {
                                $tm62.getCurrentLog().checkRetrySignal();
                                throw $e60;
                            }
                        }
                        catch (final fabric.worker.RetryException $e60) {
                            $commit57 = false;
                            continue $label56;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e60) {
                            $commit57 = false;
                            $retry58 = false;
                            $keepReads59 = $e60.keepReads;
                            if ($tm62.checkForStaleObjects()) {
                                $retry58 = true;
                                $keepReads59 = false;
                                continue $label56;
                            }
                            fabric.common.TransactionID $currentTid61 =
                              $tm62.getCurrentTid();
                            if ($e60.tid == null ||
                                  !$e60.tid.isDescendantOf($currentTid61)) {
                                throw $e60;
                            }
                            throw new fabric.worker.UserAbortException($e60);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e60) {
                            $commit57 = false;
                            fabric.common.TransactionID $currentTid61 =
                              $tm62.getCurrentTid();
                            if ($e60.tid.isDescendantOf($currentTid61))
                                continue $label56;
                            if ($currentTid61.parent != null) {
                                $retry58 = false;
                                throw $e60;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e60) {
                            $commit57 = false;
                            if ($tm62.checkForStaleObjects()) continue $label56;
                            $retry58 = false;
                            throw new fabric.worker.AbortException($e60);
                        }
                        finally {
                            if ($commit57) {
                                fabric.common.TransactionID $currentTid61 =
                                  $tm62.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e60) {
                                    $commit57 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e60) {
                                    $commit57 = false;
                                    $retry58 = false;
                                    $keepReads59 = $e60.keepReads;
                                    if ($tm62.checkForStaleObjects()) {
                                        $retry58 = true;
                                        $keepReads59 = false;
                                        continue $label56;
                                    }
                                    if ($e60.tid ==
                                          null ||
                                          !$e60.tid.isDescendantOf(
                                                      $currentTid61))
                                        throw $e60;
                                    throw new fabric.worker.UserAbortException(
                                            $e60);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e60) {
                                    $commit57 = false;
                                    $currentTid61 = $tm62.getCurrentTid();
                                    if ($currentTid61 != null) {
                                        if ($e60.tid.equals($currentTid61) ||
                                              !$e60.tid.isDescendantOf(
                                                          $currentTid61)) {
                                            throw $e60;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads59) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit57) {
                                { val = val$var55; }
                                if ($retry58) { continue $label56; }
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
                                  ((fabric.metrics.MinMetric)
                                     new fabric.metrics.MinMetric._Impl(s).
                                     $getProxy()).fabric$metrics$MinMetric$(
                                                    terms);
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
                    fabric.worker.metrics.StatsMap result$var77 = result;
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
                                { result = result$var77; }
                                if ($retry80) { continue $label78; }
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
    
    public static final byte[] $classHash = new byte[] { -122, 62, 108, -97, 33,
    77, -90, 112, -64, 72, 104, -22, 96, 122, -11, -92, -24, 52, -112, -120,
    -23, -77, 90, -63, 80, 99, 1, -100, 76, 113, -86, -16 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549835361000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1cC3wV1Zn/ZhJCEh4JbwkPeaQoIklVQF4+IAYIBAiEh4RKnNw7SYZM7lxmzoUbWyvtbovbtZQKolWEstD1hXSrVN1SWrVbgUVdrVu1FoS2irauVdeta/3Z2u87c+4jc2dO7vSX/H5z/jcz53zz/b/vO995zNx76F3o49gwoVVrMcwq1hXXnaoFWktdfYNmO3q0xtQcZxWebY70K6zb/fZ90bEqqPXQP6LFrJgR0czmmMNgYP1GbbNWHdNZ9eqVdXPWQ0mEGi7SnHYG6vr5SRvGxS2zq820mLhJjvw7plTvunND+SMFUNYEZUaskWnMiNRYMaYnWRP079Q7W3TbmReN6tEmGBTT9WijbhuaadyEFa1YEwx2jLaYxhK27qzUHcvcTBUHO4m4bvN7pk6S+haqbScizLJR/XJX/QQzzOp6w2Fz6qGo1dDNqLMJvgyF9dCn1dTasOLw+hSLai6xegGdx+qlBqppt2oRPdWksMOIRRlc6G2RZly5BCtg076dOmu30rcqjGl4Aga7KplarK26kdlGrA2r9rESeBcGFYFCsVJxXIt0aG16M4MLvPUa3EtYq4SbhZowGOatxiWhzyo8Psvy1rvL5m7/YmxRTAUFdY7qEZP0L8ZGYz2NVuqtuq3HIrrbsP8l9bu14cduVQGw8jBPZbfO41/64NpLxz55wq0zyqfO8paNeoQ1Rw62DHxxdM3kWQWkRnHccgwKhW7MuVcbxJU5yThG+/C0RLpYlbr45Mpn1m19UH9HhdI6KIpYZqITo2pQxOqMG6ZuL9Rjuq0xPVoHJXosWsOv10Ff/FxvxHT37PLWVkdndVBo8lNFFv8fTdSKIshEffGzEWu1Up/jGmvnn5NxACjHAxSAgjkAm0z8fDFAH3RRXXW71alXt5gJfQuGdzUeumZH2qux39pGZGrEindVO3ak2k7EmIE13fPVGEoITvVSjlWoRLw3hSVJ8/ItioJGvTBiRfUWzUEPiWiZ32Bih1hkmVHdbo6Y24/VwZBj3+ERU0JR7mCkcpso6OXR3vyQ3XZXYn7tB4ebT7nRRm2FyTB4XeWqhHJVrnKoT3/qQFWYkqowJR1SklU1e+se4nFS5PAOlRbRH0XMjpsaa7XsziQoCuczlLfnAYLu7cC0gZmh/+TGGxbfeOuEAozM+JZCchZWrfT2k0x2qcNPGgZ/c6Rs29sffX/3zVamxzCozOnIuS2pI07wGse2InoUE11G/CXjtB82H7u5UqUkUoL5jWkYgZgsxnrv0a1DzkklN7JGn3roRzbQTLqUykilrN22tmTOcKcPpGKw638ylkdBnhevaozf++rzv7+CjxipFFqWlWsbdTYnq9uSsDLeQQdlbL/K1nWsd+auhp13vLttPTc81pjod8NKKmuwu2rYTy37ayc2/ers6wf/W804i0FRPNFiGpEk5zLoM/xT8PgrHdT36AQhZuAa0e/HpTt+nO48KaMbpgAT0xCq7lSujnVaUaPV0FpMnSLl07LPXfbD/9le7rrbxDOu8Wy4tGcBmfMj58PWUxv+fywXo0RoCMrYL1PNzWtDMpLn2bbWRXokv/KLMd85rt2LkY9ZyTFu0nmiAW4P4A68nNtiKi8v81ybRsUE11qj+XnVyc3xC2iwzMRiU/WhPRU1V7/jdvV0LJKM8T5dfY2W1U0uf7DzT+qEop+r0LcJyvk4rcXYGg2TFYZBE460To04WQ8Dul3vPmq6Q8ScdF8b7e0HWbf19oJMisHPVJs+l7qB7wYOGqI/GWkyBpUOUD1VYDldHRKncmhSAf5hNm8ykZeTqJicCsaSuG0x1FKPJtNiVRLbT4grE1iUJZZBX2yWNHSHtxnGYJLIgVssu0O306lQ1KJskexaqsV59ZHefOd2YSpnpHUoIx0uQl3+CWDtxwLP+VCr9aem0sdLkJ/R2ZlgFNH8NlMY9GO2jtM13ZlvJVPqX+hJ4al+n6pH1Sq4mkn57Yq1FsyeWoRlrMn/ysRAGhW4OotJVmQrKYXc1E9RVCWyCTJIWa+ErGdaOPVNJrErjAmaI/H53cGv7tobXf69y9yZzODu847aWKLz4Zf/8mzVXedO+oxsJcyKTzX1zbqZpWMt3nJ8zmR9KZ9CZjrRuXfGzKrpeLPNve2FHhW9tR9YeujkwkmR21UoSPeWnHlr90ZzuveRUlvHaXdsVbeeMi7thBJywgg8Pg9QdFzgv2eHUyYIc7sJd66nfxQLIU8IfMTrUf9sFpVca6ViA4M+mymX+CS5BtvoxHFqs5jI6rfu+sZnVdt3uZ5zZ/sTcybc2W3cGT+/2QDeHSh+xsvuwlsseOv7Nx+9/+ZtqlB0LQ5iUSuRCsn13c08BY/ZAH1fE/hEgJmp0HKNSk0eF/iDYKMqGdds5FIdiWUTVOCkvA9NJtNJa7Sn11+HizlcnIn5m2+q8vDl/XsBHlej7ssFVuQZVipPo8ZmHNcZzQhpsekJsnIhcqTAfsH2UDN5qDxjlH+QGOVrVHwRGbq3bua2oXNb/LyKwwysACjdITAezqvUxBJo5OXVci71NgmB7VRsY7iCMRO+ivO1TA0eq/l4xrH0uXzdw3s9FVs9XikTkp4V+NP8veKS2i0hdRcVO3CYEl4J4sadMh4PXKMNeE/gK+GcQk1eFvhCCKfsk+i/n4q7GRR0GrGMBp4uMxcPnAMOXCvw0jA+ucXPJ+VC0hSBY8L65AEJp4eoOMCgVPgkgFraJTsBBp8VeDycS6jJMwKfDOGSRyTqH6HiYXKJlgzUuxqP+wCG93Nx2Jvh9KYmbwh8PS+93QR1VKL3MSoeYzCoTWe1mxKaabAuPinrSmXwsZ4MnprauXO3LqpV4Rd/i/F4CMl+S+CaXok/krRa4ML84881xHGJIU5S8RSDC0T85diDrv/Uz6sL8TgJULFD4MpwXqUmKwQuCWZUwHUt4GR4wa/wTaUprp06MEubVqyN3/MFCdVfUnEK11HIcVW7rTvtlhkNJsm92YLHcwCjZrpY8VaveJMknRf4ajD3Qi6qMOPNdPEiv/cZCdmzVLzCYGTGr3lw5qu+a/B4C2DsaYEPSBx7Vc7Kjje5X+C+vEJ1YyZUz0sovU3Fb/L2Hx+h6/D4M2bOcS6OC+W/wBGaJJ0X+KseYzeVUYZ0X8g2MstOr7tyV63cKG4Cfl9ilI+o+APNtHDNps9jXGqGmMcc2GeVIQCVKwWO6g1zcEkVAgfk5fEPcG0wKnuhtxjXpHxXx11+bBi0UXuh673d7hLPu1OfVfH9Q2ff+cWAMYf5xmAhbdCS+FLvI47cJxjdHkxw6v3TxLiNrxSE5guc62+qrJGH/z8DC0UVOwhKocycuGZoNWKamdo9KDL1WBtr90twBag/yVOSXmumgytrVY/ZUKdFPV1alargLvsNqyr9XClVI+lLZL1LhN81K5FzvYLjURkmuTaCisFIO0IaphQrz2ju7mq5SnGr/FUibTSd/BhnTlo0mk/cVwFc9LLA7Lz098c9Sdor8Nv5xb3rRCorJdRIA2UsUsPZYD7UMMtNaRI4sVeokaQJAoeFpVYlofZ5Ki4malpSQm0wNbgYFWgGqNIFLg9NbbmH2iAhaZnAa4KpZSs9U3JtNhWXYwLGUWm1ozfQ8ybm14f7tliWqWsxP6afQ31uRH1uE9jVK0xJUlKgZGGczaZWco3mnco16Dpk6tLkU76NQYQ0vO2jAr/bK4RI0j6BO/IjtFxybQUVddmE5ksJ4WSw6jWBQWuvcIRI0jMCH8+P0DrJNZ6zG7MJrZESiuDaTHGx6o+9QogkvStQslbLVjoiuUa5Qbkhm9AyP0J82orLc6UDCf1A4O0BhKjInbbyJt8WeGt+mpuSa7SKV9oYDHcnfZViGVnp7gBW+uW81Bpf2YxTkFkCx0tY5K6qeJNxAiuCWWStlT/g6iYkVLZQEWdQzJ1gJbv8lOcxdTnK3AZwRVLg9b0SUyRprcBFPXJKTSvG+j82ohdvnMDHRZzvLRJb0Mam0pXaR/eLxdTjAOUbqO4LAp8O50Vq8pTAo/nFomQzU6HNTGUbjkCO1hkXG+svBvlvF8D0Ahen/a5X/EeSfivwpXxiUtnK1ZZsZSq0lanskFPinqB1yZ1IKSqwMZwnqMlKgfX5eUKyg6nQDqZyN+YzU3PY6nhUY3qgM6bjbfcDzDggcGuvOIMk3SIwHkwoxxmSPUyF9jCVAz2y4v6g5HQQV1cVAgvC+YOaqC7O+DQ/f0i2LxXavlQeZjAwwbWuo1Ui9my/fs19MgdvfRhgZlzgwgDtw/mEJC0QeGUIn/xYwuwnVDyWF7N0P8GRc+azAo8GMAvwCzX5kcAj+fnl55JrNLtSnsQhZ7NuWhHD3eDx9Qj1kqMAswtdnPWbXvEISTon8MUQHvkvCSeSo5zsgVN69PgJcmoSWB/AKcAX1GSJwNr8fPGq5NprVLyEQ17MMhzfIS89dpzAHnKbQLNXHEGSOgR+IYQjfish9AYVp2WEuBfI+r8GuPqbAgM2gIK8QE3mCJwerLlnf1vhWd6dk70j4fAuFeexe+tis77BMo1I+uHFXP/JT/oZhh7D6U5E79RjrKo28zlLSE+Pp7mJKGnhbH9ehYvXnglnImpyWuDLwSby7hS71vlYYp1PqPgwxzp09j0/Hm0oHlvXgovX/UsoHrzJfoH3BPPI2s5P7+Rn+VtVghmpFCDKpwzKWGrjuwdKjajPcICFewSGezrDm6wQGObpTIZNqYRNfyr6hGAzG1WZAFB3p8DWcGyoiS6wOf8sog6RcKBdMXUgg6G23kok1upaR63DjE4cbR0uwo9IO2oxCWDxVBfrng9HhJo8J/B4MBHP3nBqKcT3WB09krCxS9C7OLGIEddM//6escJoiRUmUDGCwSg/KzTbeqfF54L+xpiGOuKwseSkwL3hjEFN7hV4ZwivTpbwmUJFJWYOwaeeXoUzu/wo0AHL8f4zAOobBQ4LoOA76gU9H+SShgosCmbmedor6F0hoUcDkTqVwTDxVLBnltxRLXgXZNow3cXlp8M5ipr8WuAv86GjjsnQmSuhczUVVyKd7jwkcTeM2s7Fu6wDWPFlgWvDOI3umTNVGSokrRF4XYhwXCAhSNsd6rWMvtLTGU8wfU3QrkOalwawco/ARK/wIklMYEsIXg0SXjQaqYuxmwlejcGreM7sGry/gYPaMYE7e4UZSbpd4NYQzJokzGiSqq5i7hdrkFm9dEnMydVyPWHVGwKP9Ao5kvSowP0hyEneIlVp2FU3YH8T5Fb3uLZMe68LYE2xi6tf7xWCJOmMwFMhCMYkBEmC2o4Tk1R/kyzV0l3uKwBrx7u45pNeoUaS/izwrRDUkhJqN1FhZ1LJMulqbjLefRvyel7g42F4Ba3muKTHBN4XzCtb7a9Krv0jFV9iMMBwGo1Ym6mnHicqc/1GMZwnqN8CuH63wM4ATlT4jGLUxBTYGqy8Zxt6Vg8rMRqI+dor9WJZY/oESfDdoVb/WWIUehCtfp38TOL0Hl7iWo+EdgKsq3bx+nArON7ktMC8VnB8ZFdv45reJWFxNxU7GQzJZpEZ1/3JzMd73IMLd9XFdSfDkaEmJwQ+nReZ2zJdb7+EzAEq9uTrkg4Uvg9Z3C9wQTgW1KRWoOTxdmblJlySofKghMohKr6Xv1/4KwpfQHUOYKxd7mLT+dDJxOcVBS7pTYGv5kOUT5KziR6REH2MisNeou60OdB1i1GdIwA3nBG4J5zrqMk9Au/Ii5HnjUD1mIQRvUquPkGDt8uo5zfoOCcHFXoCYMO/CQwZjtSkVqAkHDN7Izwcc4g9IyF2goqnGIz2JdZT0tiAyv0M4MYLXGz+WTh+1ORpgT/Oh182tUwoSjaOVaqm/mdox+Egqx5HYh8KDFoNBBCjJkxgLJhYH65oHz/HZdhJtpdV2l5WX/o7vMdTSxI1PAWgPSJwca+kFpJUJ3BuPtxz3s/NcJfsRKu0E62eDuTeQ7ahdHoWILpB4PQA7gEOpibTBFYFk/S+Vq9KtqVV2pZWzzMY6hLK801ymoT8DkAf4GI03LMn3uRHAiXPnjyTEMHm/yRs6N1a9T0Go/zY9JRXFuC9/gjQukLg0HCkqMkQgfm9U7sxE3N/kZD6jIqPw7rIwpv8L0BbsYutIXfIqcl+gZIdcs+kJItSQVEwpYJiKpTwfuIZ5EZU6yNkdkBgY69kEJK0UqDkGZxncpJNuFxCeDAVpUGEe0gbU/GGKMpoF3hdAGF/V/ImNQKvyidtKCO5ziMlfOg19IKhjL5NT19I1lfRD4goJX7aL8NbjwYwFwhUwmk/mn8jjWPHJ8Hae+f4c7maEyQU6I2ugjEMRrgumReLzosw/gXMrK8Veb+2stkyon4kr0QNrwCIrxF4cTiS1OQigeODSWZc5C7ICiTv7RbQe7sFk3E+rDmObrMGy2ERKxZN/bRFwaQkgyL35Tb6sYZRPr+XIn65J1LzH/rBN5dcOizgt1IuyPktJdHu8N6y4hF7V7/ivuuf+lWeknoobk2YZvaPGmR9LorbeqvBjVbCy4Fxzmc6g4Hdv+HF+HcC6BMZoOAKt95MpOXWo/9mcUPyF+vcL3/hCn+i3xf854nv6jcm+CvtvK7vWh4qEjb9iNShD0d8XFS86hz/OQ80+rivX23uG7/0X+NPLmr/w403/eng29N23Pr7R5ueaogoe+o3Pfj+3wBXJfmL3EoAAA==";
}
