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
    
    public abstract double getPresetNTerm();
    
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
    
    /** @return the estimated noise of the {@link Metric}. */
    public double noiseTerm();
    
    /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return the estimated velocity of the {@link Metric}.
   */
    public abstract double noiseTerm(fabric.worker.metrics.StatsMap weakStats);
    
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
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a freshly computed noise for this {@link DerivedMetric}.
   */
    public abstract double computeNoiseTerm(fabric.worker.metrics.StatsMap weakStats);
    
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
        
        public double getPresetNTerm() {
            return ((fabric.metrics.Metric) fetch()).getPresetNTerm();
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
        
        public double noiseTerm() {
            return ((fabric.metrics.Metric) fetch()).noiseTerm();
        }
        
        public double noiseTerm(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).noiseTerm(arg1);
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
        
        public double computeNoiseTerm(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.Metric) fetch()).computeNoiseTerm(arg1);
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
                    long $backoff74 = 1;
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
                                if ($backoff74 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff74 =
                                      java.lang.Math.
                                        min(
                                          $backoff74 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff75 = $backoff74 <= 32 || !$doBackoff75;
                        }
                        $commit68 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                scalar, tmp);
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
                            $retry69 = false;
                            if ($tm73.inNestedTxn()) { $keepReads70 = true; }
                            throw $e71;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid72 =
                              $tm73.getCurrentTid();
                            if ($commit68) {
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
                            } else {
                                if (!$tm73.inNestedTxn() &&
                                      $tm73.checkForStaleObjects()) {
                                    $retry69 = true;
                                    $keepReads70 = false;
                                }
                                if ($keepReads70) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e71) {
                                        $currentTid72 = $tm73.getCurrentTid();
                                        if ($currentTid72 !=
                                              null &&
                                              ($e71.tid.equals($currentTid72) ||
                                                 !$e71.tid.isDescendantOf($currentTid72))) {
                                            throw $e71;
                                        } else {
                                            $retry69 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
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
                    long $backoff85 = 1;
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
                                if ($backoff85 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff85 =
                                      java.lang.Math.
                                        min(
                                          $backoff85 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff86 = $backoff85 <= 32 || !$doBackoff86;
                        }
                        $commit79 = true;
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
                        catch (final fabric.worker.RetryException $e82) {
                            $commit79 = false;
                            continue $label78;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e82) {
                            $commit79 = false;
                            $retry80 = false;
                            $keepReads81 = $e82.keepReads;
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
                            $retry80 = false;
                            if ($tm84.inNestedTxn()) { $keepReads81 = true; }
                            throw $e82;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid83 =
                              $tm84.getCurrentTid();
                            if ($commit79) {
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
                            } else {
                                if (!$tm84.inNestedTxn() &&
                                      $tm84.checkForStaleObjects()) {
                                    $retry80 = true;
                                    $keepReads81 = false;
                                }
                                if ($keepReads81) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e82) {
                                        $currentTid83 = $tm84.getCurrentTid();
                                        if ($currentTid83 !=
                                              null &&
                                              ($e82.tid.equals($currentTid83) ||
                                                 !$e82.tid.isDescendantOf($currentTid83))) {
                                            throw $e82;
                                        } else {
                                            $retry80 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
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
                        long $backoff96 = 1;
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
                                    if ($backoff96 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff96 =
                                          java.lang.Math.
                                            min(
                                              $backoff96 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff97 = $backoff96 <= 32 ||
                                                 !$doBackoff97;
                            }
                            $commit90 = true;
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
                            catch (final fabric.worker.RetryException $e93) {
                                $commit90 = false;
                                continue $label89;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e93) {
                                $commit90 = false;
                                $retry91 = false;
                                $keepReads92 = $e93.keepReads;
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
                                $retry91 = false;
                                if ($tm95.inNestedTxn()) {
                                    $keepReads92 = true;
                                }
                                throw $e93;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid94 =
                                  $tm95.getCurrentTid();
                                if ($commit90) {
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
                                } else {
                                    if (!$tm95.inNestedTxn() &&
                                          $tm95.checkForStaleObjects()) {
                                        $retry91 = true;
                                        $keepReads92 = false;
                                    }
                                    if ($keepReads92) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e93) {
                                            $currentTid94 = $tm95.getCurrentTid();
                                            if ($currentTid94 != null &&
                                                  ($e93.tid.equals($currentTid94) || !$e93.tid.isDescendantOf($currentTid94))) {
                                                throw $e93;
                                            } else {
                                                $retry91 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
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
                        long $backoff107 = 1;
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
                                    if ($backoff107 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff107 =
                                          java.lang.Math.
                                            min(
                                              $backoff107 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff108 = $backoff107 <= 32 ||
                                                  !$doBackoff108;
                            }
                            $commit101 = true;
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
                            catch (final fabric.worker.RetryException $e104) {
                                $commit101 = false;
                                continue $label100;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e104) {
                                $commit101 = false;
                                $retry102 = false;
                                $keepReads103 = $e104.keepReads;
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
                                $retry102 = false;
                                if ($tm106.inNestedTxn()) {
                                    $keepReads103 = true;
                                }
                                throw $e104;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid105 =
                                  $tm106.getCurrentTid();
                                if ($commit101) {
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
                                } else {
                                    if (!$tm106.inNestedTxn() &&
                                          $tm106.checkForStaleObjects()) {
                                        $retry102 = true;
                                        $keepReads103 = false;
                                    }
                                    if ($keepReads103) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e104) {
                                            $currentTid105 = $tm106.getCurrentTid();
                                            if ($currentTid105 != null &&
                                                  ($e104.tid.equals($currentTid105) || !$e104.tid.isDescendantOf($currentTid105))) {
                                                throw $e104;
                                            } else {
                                                $retry102 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
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
                    fabric.metrics.DerivedMetric val$var110 = val;
                    fabric.worker.transaction.TransactionManager $tm117 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled120 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff118 = 1;
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
                                if ($backoff118 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff118 =
                                      java.lang.Math.
                                        min(
                                          $backoff118 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff119 = $backoff118 <= 32 || !$doBackoff119;
                        }
                        $commit112 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                a, term);
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
                            $retry113 = false;
                            if ($tm117.inNestedTxn()) { $keepReads114 = true; }
                            throw $e115;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid116 =
                              $tm117.getCurrentTid();
                            if ($commit112) {
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
                            } else {
                                if (!$tm117.inNestedTxn() &&
                                      $tm117.checkForStaleObjects()) {
                                    $retry113 = true;
                                    $keepReads114 = false;
                                }
                                if ($keepReads114) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e115) {
                                        $currentTid116 = $tm117.getCurrentTid();
                                        if ($currentTid116 != null &&
                                              ($e115.tid.equals($currentTid116) || !$e115.tid.isDescendantOf($currentTid116))) {
                                            throw $e115;
                                        } else {
                                            $retry113 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit112) {
                                { val = val$var110; }
                                if ($retry113) { continue $label111; }
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
                    fabric.metrics.DerivedMetric val$var121 = val;
                    fabric.worker.transaction.TransactionManager $tm128 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled131 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff129 = 1;
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
                                if ($backoff129 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff129 =
                                      java.lang.Math.
                                        min(
                                          $backoff129 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff130 = $backoff129 <= 32 || !$doBackoff130;
                        }
                        $commit123 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(terms);
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
                            $retry124 = false;
                            if ($tm128.inNestedTxn()) { $keepReads125 = true; }
                            throw $e126;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid127 =
                              $tm128.getCurrentTid();
                            if ($commit123) {
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
                            } else {
                                if (!$tm128.inNestedTxn() &&
                                      $tm128.checkForStaleObjects()) {
                                    $retry124 = true;
                                    $keepReads125 = false;
                                }
                                if ($keepReads125) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e126) {
                                        $currentTid127 = $tm128.getCurrentTid();
                                        if ($currentTid127 != null &&
                                              ($e126.tid.equals($currentTid127) || !$e126.tid.isDescendantOf($currentTid127))) {
                                            throw $e126;
                                        } else {
                                            $retry124 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit123) {
                                { val = val$var121; }
                                if ($retry124) { continue $label122; }
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
                    fabric.metrics.DerivedMetric val$var132 = val;
                    fabric.worker.transaction.TransactionManager $tm139 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled142 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff140 = 1;
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
                                if ($backoff140 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff140 =
                                      java.lang.Math.
                                        min(
                                          $backoff140 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff141 = $backoff140 <= 32 || !$doBackoff141;
                        }
                        $commit134 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(terms);
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
                            $retry135 = false;
                            if ($tm139.inNestedTxn()) { $keepReads136 = true; }
                            throw $e137;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid138 =
                              $tm139.getCurrentTid();
                            if ($commit134) {
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
                            } else {
                                if (!$tm139.inNestedTxn() &&
                                      $tm139.checkForStaleObjects()) {
                                    $retry135 = true;
                                    $keepReads136 = false;
                                }
                                if ($keepReads136) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e137) {
                                        $currentTid138 = $tm139.getCurrentTid();
                                        if ($currentTid138 != null &&
                                              ($e137.tid.equals($currentTid138) || !$e137.tid.isDescendantOf($currentTid138))) {
                                            throw $e137;
                                        } else {
                                            $retry135 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
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
        
        public abstract double getPresetNTerm();
        
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
        
        /** @return the estimated noise of the {@link Metric}. */
        public double noiseTerm() {
            return noiseTerm(fabric.worker.metrics.StatsMap.emptyStats());
        }
        
        /**
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return the estimated velocity of the {@link Metric}.
   */
        public abstract double noiseTerm(fabric.worker.metrics.StatsMap weakStats);
        
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
                                     tmp.computeNoise(weakStats),
                                     tmp.computeNoiseTerm(weakStats));
            }
            else {
                fabric.worker.metrics.StatsMap result = null;
                {
                    fabric.worker.metrics.StatsMap result$var143 = result;
                    fabric.worker.transaction.TransactionManager $tm150 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled153 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff151 = 1;
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
                                if ($backoff151 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff151 =
                                      java.lang.Math.
                                        min(
                                          $backoff151 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff152 = $backoff151 <= 32 || !$doBackoff152;
                        }
                        $commit145 = true;
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
                                          tmp.computeNoise(weakStats),
                                          tmp.computeNoiseTerm(weakStats));
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
                            $retry146 = false;
                            if ($tm150.inNestedTxn()) { $keepReads147 = true; }
                            throw $e148;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid149 =
                              $tm150.getCurrentTid();
                            if ($commit145) {
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
                            } else {
                                if (!$tm150.inNestedTxn() &&
                                      $tm150.checkForStaleObjects()) {
                                    $retry146 = true;
                                    $keepReads147 = false;
                                }
                                if ($keepReads147) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e148) {
                                        $currentTid149 = $tm150.getCurrentTid();
                                        if ($currentTid149 != null &&
                                              ($e148.tid.equals($currentTid149) || !$e148.tid.isDescendantOf($currentTid149))) {
                                            throw $e148;
                                        } else {
                                            $retry146 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit145) {
                                { result = result$var143; }
                                if ($retry146) { continue $label144; }
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
   * @param weakStats
   *            StatsMap for mapping metrics to weakly consistent values to use
   *            for metric stats.
   * @return a freshly computed noise for this {@link DerivedMetric}.
   */
        public abstract double computeNoiseTerm(fabric.worker.metrics.StatsMap weakStats);
        
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
    
    public static final byte[] $classHash = new byte[] { -35, 90, -115, -114,
    101, 62, -39, 90, 64, 35, -87, -4, 68, 88, 82, 32, -15, 126, 120, 27, 116,
    -37, -69, 34, 126, -90, -123, -21, -83, 64, 47, -105 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556814046000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1cC5gUxbWu6WEXFhZ2l5fyfq0oAjOixCiLCGxAFxZYWR66KkvvTO/S0Ds9dNcssxoMST4uXqNoouIzGBWNCuinxqv5DJ8m5qLExzUaNZoomhufaBI1icbnPaf6zPRsT3ftdO7u93X9vd11Tp2/TtWpR/f0vg9YmW2xye1qm27EeHdas2OL1baGxibVsrVkvaHa9iq42poY1K9h1zs/TY5XmNLIKhNqykzpCdVoTdmcDWncqHap8ZTG46tXNtSdyyoSKHimam/gTDl3YdZiE9Om0d1hmJwKKdJ/9fT4Vdesq74vyqpaWJWeauYq1xP1ZoprWd7CKju1zjbNshckk1qyhdWkNC3ZrFm6augXQEYz1cKG2npHSuUZS7NXarZpdGHGoXYmrVmizNxFNN8Es61MgpsWmF/tmJ/huhFv1G1e18jK23XNSNqb2UWsXyMrazfUDsg4sjHHIi40xhfjdcg+UAczrXY1oeVE+m3SU0nOJngl8oxrl0IGEO3fqfENZr6ofikVLrChjkmGmuqIN3NLT3VA1jIzA6VwNjpQKWQakFYTm9QOrZWzo735mpxbkKtCVAuKcDbCm01oAp+N9viswFsfLJ+788LUmSmFRcDmpJYw0P4BIDTeI7RSa9csLZXQHMHK4xt3qSMPXKwwBplHeDI7eR789ofzZ4x/9AknzxifPCvaNmoJ3prY0zbkt2Prp50aRTMGpE1bx6bQg7nwahPdqcumobWPzGvEm7HczUdXHjxn213aEYUNbGDlCdPIdEKrqkmYnWnd0KwztJRmqVxLNrAKLZWsF/cbWH84b9RTmnN1RXu7rfEG1s8Ql8pN8T9UUTuowCrqD+d6qt3MnadVvkGcZ9OMsWo4WISx6FbGLlwD58cxVgYuaohvMDu1eJuR0bZA847DoalWYkMc+q2lJ2YmzHR33LYScSuT4jrkdK7HoSkB2PFlAmNgRLovlWXR8uotkQhU6oSEmdTaVBs8RK1lYZMBHeJM00hqVmvC2HmggQ07cJ1oMRXYym1oqaJOIuDlsd74UCh7VWbhog/vbn3SaW0oS1UGjdcxLkbGxRzjwJ5K7EAxCEkxCEn7ItlY/e6GvaKdlNuiQ+VVVIKKOWlD5e2m1ZllkYjgM1zIiwYC7t0EYQMiQ+W05vOXrL94chRaZnpLP3QWZK319hM3ujTAmQqNvzVRteOdf96za6vp9hjOaos6crEkdsTJ3sqxzISWhEDnqj9+ovpA64GttQoGkQqIb1yFFgjBYry3jB4dsi4X3LA2yhrZIKwD1cBbuYg0kG+wzC3uFeH0IZgMdfyPleUxUMTF05rTP/79M++eJEaMXAitKoi1zRqvK+i2qKxKdNAat+5XWZoG+V69tunKqz/Yca6oeMgxxa/AWkzrobuq0E9Na/sTm18+/Nqe3ymuszgrT2faDD2RFVxqvoa/CBxf4YF9Dy8gQgSup34/Md/x01jyVNc2CAEGhCEw3a5dneo0k3q7rrYZGraUL6qOmfXA+zurHXcbcMWpPIvN6F2Be33UQrbtyXWfjBdqIgkcgtz6c7M5cW2Yq3mBZandaEf2u8+Nu+5x9cfQ8iEq2foFmgg0TNQHEw48UdTFTJHO8tybjclkp7bGiuuKXRzjF+Ng6bbFlvi+G0fXzzvidPV8W0Qdk3y6+hq1oJuceFfnP5TJ5f+tsP4trFqM02qKr1EhWEEzaIGR1q6ni41scI/7PUdNZ4ioy/e1sd5+UFCstxe4IQbOMTeeD3QavtNwoCIqsZKmQaNqZyx+NmEd3h2WxnR4NsLEyRwhMkWkUzGZlmuMFWnL5GCllszm1SqodhCpm0M4q0AtZ/1BLKtrtpAZwdlUioFbTGuTZuVDIeXCaJHtXqamRfZR3njndGFMT87bUIU2HAu2PMmY+ifC3/hQW+RPTcHT44Gf3tmZ4diiRTHTORvELQ2ma5q90MzmzJ/gCeG5ft8jn7/dWXn5A9Q2CKdqgrvVK/6qaGRNEq4uoFbQ1CM5C52xAJtVjMILUMqZVYFmGSbMhbNZ6BvjgiZNYsK353tX7U6uuG2WM7UZ2nMisiiV6dz/4pdPxa59/ZDPUFfBzfRMQ+vSjAIbl0KRk4pm78vEnNLtVa8fGXdq/aY3O5xiJ3hM9Oa+c9m+Q2dMTfxIYdF89ymayPYUquvZaQZaGszDU6t6dJ2JeSdUoBOOguMExsofJ/x5YftyW2VxvxHO9XSYAaTkIcL7vB71D2/tkntiWraes7IuDC4+Ua/J0jth4Oqima128VWXfB3beZXjOWf6P6VoBl4o4ywBRGGDRf/A9jNJVoqQWPz2PVsfvmPrDoUMPQdGtaSZyTXJ83tW83Q4IIz0f4XwoYBqxiRRXKko8iDhvcGVGnFd48wKM5Ka3YIJNOgynF3mo9hYTxj4FqzuYLXmTOgwz2gPPdGdF8MxD0xdQTi6xFakiDCqd8G4znFGiItNT5uqJpWjCAcF01fcsFPt1sH3JXWwHZMLIag5RbeKqsBr3X5OhGGGncXYwCsI0+GciCImoV6SE6uF1kslBHZisoPDCsbI+Bou1jL1cKwW45nAgU+X6h7RyTHZ5vFKFWl6ivCR0r3ikNolIXUtJlfAMEVeCeImnDIJDoOxwX8lfCmcU1DkRcJnQzjlJon9N2NyPWfRTj3lWuDpMnPhgDngkLWEM8L45Dt+PqkmTdMJx4X1yZ0STnsxuZWzgeSTAGp5l1zJ2NDDhI+HcwmKHCR8NIRL7pOY/zNM9qNL1Gyg3XE4fsrYyEEOjngznN0o8mfC10qy2wlQD0vsPoDJf3FW06HxRZszqqHzbjEp684F7PGegJ2b2sUKshVN2/wa5BI49gL7ywnX9EmDRE2rCc8ovUE6NXNIUjNPYvIYZ0dTgyyqILz/iJ+bz4DjEGOjryBcGc7NKHIW4dJgRlFha1SQEYm4sxGT6U49dULYNsxUhyjzOQlVEdKehoUVcFy1wdLsDaaRDCYpvNkGx9OMjTnFwdFv94k3UdNbhL8P5t5PqOrnejOfPC/KPiwh+wYmL3M2yvVrCZzFMvB0ON5mbPwfCe+UOPa0oqWeELmD8KaSmqrhNtV3JJTew+R/S/afGLIb4PgXhNKJDk4M5b/AIRs1vUX4cq9tNxdihvVc2TZz08qvu4qXg6JSnIj8kaRSPsXkfZx6wZpNW8CFVpeYpzqgz0aGMVa7knBMX1SH0DSacHBJHv8Y1gZjChd6S2BNKrZ5nOXHupqN6rPdf93lLPG8W/cFGf+27/CR5waPu1vsFPbDHVtUP9D7zKP4kUaPJxWCemWe2DeQWB0czdCiTyCEFXjT/3OvuccigPav+1xngRsLhknx/8mQRPrRdkdkgMzVsJ5p11OqkdvqKDe0VAff4Bd8o1C3qC+a9Xo63/ALdhwgUmu44YC31uQyOFsSuhnLPwTL5cj6EjnfISJKLRhkhF3BfSUySnIPe0JkJNBOoIU5w6pdy50tOMcoIcEk2ibixc9gmqcmk6X0yRhjx75IWBgz//0+iZp2E/6wtD7pOBHT4yTUsMTIZKAGU9dSqEEEnt5COKVPqKGmyYQjwlKT7A9HcH84MgOpqVkJtaEocBwY0MpYTCNcEZraWR5qNaRpOeHpwdQKjZ4ruTcPk5NhcIARc7WtNWFw4X59uH+baRqamvJjegzYsx7suZSwu0+YoqYsoWQVX8imQXIPZ5CRenAdMHVoiumoEURIhWLvJ/xJnxBCTTcRXlEaoWbJPdzFjSwrJLRQSggmqrFXCIMWiuEIoaaDhA+WRuh8yb1WTNYWElojJZSAhWTEwdhf+oQQavqAULKwLDS6Q3IPW21ELSS0PJAQPllJAqEZhCP6hBBqGk5YVhqhzZJ7eDGyibMhLqFVmtXpR0qsE6ZD0QYU/UfCAwGkMCleJwiRXxDeX5r1F0jufRuTDGcjnQlSLU2Qap2ZUa1rT/EuS2QLY7OShI0SFsXLWCGylHBRMIuC3YqPhbmS7dQIbqdGLuJsgHCEme32M160qxNB58WMnXQr4ff7pF2hpu8R2r1yys2Vxvs/uMNXn+zAB3aC72WSurgSkx25Bxd+bTH3/CXyAzD3c8IPwnkRRd4nfKu0tni95N6NmFwNw6qtdqbpScbzQf67GhYcMcIhfeI/1DTYwdlfldImIzuF2XsklG7HZLeckvAELgSvBQMuJ7wonCdQZCvhltI8sV9y7x5M7oAgbag2X51OqlwLdAYs+yK3MHbyM4R7+8QZqOkuwhtCOONBCSt8uhi5t1dWwh8TQettjH1zEWEsnD9QZCbhsaX541HJvV9h8jCMMRlhdQMuy6Fn+/Vr4RNYhUfuYeyUGwitPvEJatpM2BbCJ7+RMHsKk1+XxCzfT+6D8j8hfCuAWYBfUORNwsOl+eV5yb0XMHkGhpwuzTATurOj5usR7CUwbM+JE1b2iUdQ0yAHT/0ihEdelXDCaom81Aun/OjxCFiwnXBLAKcAX6BIF2E62PhC296S3HsHkzdgyEuZuu075OXHjkPQQx4kvKZPHIGadhHuCOGIv0kIfYTJezJCwgtjQOFTjM2tcbDu63BeQJGvCD8tzQv/ktz7HJO/c1YhjA6aCAtPnAylvsjYaUsJjw0wPJwnUNNUwqNK94QSDSal4Coh8mVvpIQ3cGPjXcbmr3Xw9M/DeQNFPiP8e7D1BY84nAcbggeeiWmyUikhg5MlpT9EXI0eWDWZhp7IP9Gb6z8fzT/Y01IwA01onVqKxxa55wVKenveJ+oJ97ag2dVvI4yHqycUiREeF1xPnsdghVUk2dVUcFdTGV5URXjVdzG0GCyrBhjn4KLXQ5ERIocJJc9FvI9+HB6TJTxw+aaMK52HBeqPZWzJEAcbbgvHA0X2EO4O5lEmLCzL8/BtwZINaWUmJlM5q+K5x1m98OoAo+Yytmyxg43vhOOFIm8TvlF6p/S2uNkSSviMQYmHoNQM9ixnrKnGwRXh3nIQIgcJJW85FD1GdtmcJmGDe7DKKSHYzAFTICKsrHTwrD+EY4MirxD+LphNUcxfLOFwJiYLOBtuae1IYq2mblpkc70TZqm2UOFHZANYsQ6I3EJYH44IiiwknNtrJMiF7NwWgnjgYmuJjAVdHV8aTCX0tGr4B2W3FlZIagH3U5UlnI3xq4VWS+s0xRrKvzJmg40bGVs1j3BYuMpAkaGElcGVUeTV8yR81mGyFiIi8WnEd3aNbj8KeLAVUH4Kyn+C8JIACr5zlKAXGYSm/yS8sFc3i2JcepL3VRV8X1VZz9kIen2hd5bCUW1QCthzjuLg2bJXF3wchSJ3EN5SCh2lyaVjSuhsxmQj0OnJQ9LuRqAsxGnlSphiLCWsDeM0fPhTNLEcTpqmENaEaI7dEoLofMXm+GPEznSGa2uCduvyvGBFf65OuLBPeKGmBYTxELwkW7EKbsUqW6GbEa/m4N0vwex0KP9Wxs67hHB9nzBDTa2Ey0Iwk2ysKpdjsoM7PwkEZo3SrSRBbhGYsJex8+8l/G6fkENN2whTIchdIyF3HSY/hP5G5Fb3uieT9979jK17gTDUDmAgQdR0F+G1IQjeLCF4KyY3wMQk198kWxz5LvcwY61/IQz1jDCQGmo6SPizENT2Sqjtx+Q2N5QsD9o0ELzmQ+mPMaYOcnB90IolHC/UdJjw6RC8HpDwwmeoyj0cfwfm8pLuK0wDCw4Bt4cIbw7DLWhfQWj6CaHkzZBC0x+R3PslJj/nbLBuN+upDkPLvTcRme83Qp8IJf8WBupthGsDOGHiM0KjyBrCpl4dk5tXntrLVgBOMsTiP/e6b3P+AmrwfWqlPCGplGcweQzbMKrTenmT9lwgBCEnMcrBtpBLIBQ5SChZAnlmLcohYekLEhb4wqzyLGfDClm4cxZ/MjjrfxXIvE94fzgyKHIf4b6SyBxyu99rEjIYGJSXS3XJJlD+BmPJHxGeEI4FisQJpwWzcFel5BKXytsSKu9i8qfS/SLexToPzHmTMW2sg8lnAxgFBxOfd7GEpv8h/FUpRMUCoJDohxKiH2NyxEvUWRIEum4JmPNPxjoeJ/yPcK5Dke2E3ymJkee1bOUzCSN8tqH8AycmDqPeX2MWnGww6DNYlF9PGLI5okicUNIc3X0f0Ry9xKKSneUo7oIpX3E21pdYb0FjHVRlGWMbFQf1/aH4CZF9hLeXwq9ov05wkGw2R3GzOdo/tON2gFVwsvFVwtZwxFBkHeHZwcTcjcgix7nsRkjY4fOFaPW/4T0RWrJg4WCImTcSzu6L0CI0nUQ4tRTuRT+ScLlPkHCfhMmoQO69RBuYz0QnMZY6k3BCOAejyHjCo4NJen/sFJW8oRvFGo1O4Wy4Q6jEn/PAJCR6DFjxCWG4LXMhsodQsmXumYQQm1kSNidhMoOzMX5seosri8GiExhL1zlofhWOFIp8SfhJKaRoq1wYPkdCCrdEo98I6yITTJkNbD4ivDQcGxT5AeH2YDaeSUkhpXoJJXz5LTovvJ9EBFkPBZ7C2ObLCE/rkwiCmuYSSjaEPJOTQsLLJITxje/oGUGEewkbM6FAaJz2SsJwb+UIkZwKyVs5BevTccLmNRI+OL5Ez+L4jRP8KoS2Cj/rFBnsZ/1yKBqGpa4THMwcCWc9irxH+OeSYoWYL84XZrZKKKiYtHB2lOOSBankggQXP4sv+BWn97eDXaae9CP5TbAww9gF8wmHhiOJIjWEkh/buy5yFmTRTRJ+uFUQbYf5sGrbmsWbTJsnzFQy98GhaCLLWbnzwit+QmeMz1es6Htqifpfa3veXDpjRMAXrI4u+sIdyd29u2rAUbtXv+T84Cr3rbSKRjagPWMYhZ+aKTgvT1tauy4qrUKkQ9KCj8XZkJ6/u+Xih1l4hhUQTTv5MkDLyYf/dYmKFN9GcL6iACv8KX6fXVlAH0xpzojf7oi8vmt5Njpj4af99n181KflA1a9Lj6yBJU+8dWWy3Zq815umT/lzi++dfbKiR9elB3D//Dw5Itu335k//z4Nf8H0D7f13JQAAA=";
}
