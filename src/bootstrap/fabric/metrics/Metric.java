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
    public double samples();
    
    /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the number of samples of the {@link Metric}.
   */
    public abstract double samples(boolean useWeakCache);
    
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
    
    public fabric.lang.arrays.doubleArray get$weakStats();
    
    public fabric.lang.arrays.doubleArray set$weakStats(fabric.lang.arrays.doubleArray val);
    
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
    public double weakSamples();
    
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
    public abstract double computeSamples(boolean useWeakCache);
    
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
        public fabric.lang.arrays.doubleArray get$weakStats() {
            return ((fabric.metrics.Metric._Impl) fetch()).get$weakStats();
        }
        
        public fabric.lang.arrays.doubleArray set$weakStats(
          fabric.lang.arrays.doubleArray val) {
            return ((fabric.metrics.Metric._Impl) fetch()).set$weakStats(val);
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
        
        public double samples() {
            return ((fabric.metrics.Metric) fetch()).samples();
        }
        
        public double samples(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).samples(arg1);
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
        
        public double weakSamples() {
            return ((fabric.metrics.Metric) fetch()).weakSamples();
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
        
        public double computeSamples(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).computeSamples(arg1);
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
                                                scalar, tmp);
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
                                 $getProxy()).
                                fabric$metrics$SumMetric$(
                                  new fabric.metrics.Metric[] { tmp, other });
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
                        fabric.metrics.DerivedMetric val$var80 = val;
                        fabric.worker.transaction.TransactionManager $tm86 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled89 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff87 = 1;
                        boolean $doBackoff88 = true;
                        boolean $retry83 = true;
                        $label81: for (boolean $commit82 = false; !$commit82;
                                       ) {
                            if ($backoffEnabled89) {
                                if ($doBackoff88) {
                                    if ($backoff87 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff87);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e84) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff87 < 5000) $backoff87 *= 2;
                                }
                                $doBackoff88 = $backoff87 <= 32 ||
                                                 !$doBackoff88;
                            }
                            $commit82 = true;
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
                                if ($tm86.checkForStaleObjects())
                                    continue $label81;
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
                                        fabric.common.TransactionID
                                          $currentTid85 = $tm86.getCurrentTid();
                                        if ($currentTid85 != null) {
                                            if ($e84.tid.equals(
                                                           $currentTid85) ||
                                                  !$e84.tid.isDescendantOf(
                                                              $currentTid85)) {
                                                throw $e84;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit82 && $retry83) {
                                    { val = val$var80; }
                                    continue $label81;
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
                        fabric.metrics.DerivedMetric val$var90 = val;
                        fabric.worker.transaction.TransactionManager $tm96 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled99 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff97 = 1;
                        boolean $doBackoff98 = true;
                        boolean $retry93 = true;
                        $label91: for (boolean $commit92 = false; !$commit92;
                                       ) {
                            if ($backoffEnabled99) {
                                if ($doBackoff98) {
                                    if ($backoff97 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff97);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e94) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff97 < 5000) $backoff97 *= 2;
                                }
                                $doBackoff98 = $backoff97 <= 32 ||
                                                 !$doBackoff98;
                            }
                            $commit92 = true;
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
                                if ($tm96.checkForStaleObjects())
                                    continue $label91;
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
                                        fabric.common.TransactionID
                                          $currentTid95 = $tm96.getCurrentTid();
                                        if ($currentTid95 != null) {
                                            if ($e94.tid.equals(
                                                           $currentTid95) ||
                                                  !$e94.tid.isDescendantOf(
                                                              $currentTid95)) {
                                                throw $e94;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit92 && $retry93) {
                                    { val = val$var90; }
                                    continue $label91;
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
                    fabric.metrics.contracts.Contract mc$var100 = mc;
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
                        try { mc = tmp.createEqualityContract(value); }
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
                                { mc = mc$var100; }
                                continue $label101;
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
                    fabric.metrics.contracts.Contract mc$var110 = mc;
                    fabric.worker.transaction.TransactionManager $tm116 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled119 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff117 = 1;
                    boolean $doBackoff118 = true;
                    boolean $retry113 = true;
                    $label111: for (boolean $commit112 = false; !$commit112; ) {
                        if ($backoffEnabled119) {
                            if ($doBackoff118) {
                                if ($backoff117 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff117);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e114) {
                                            
                                        }
                                    }
                                }
                                if ($backoff117 < 5000) $backoff117 *= 2;
                            }
                            $doBackoff118 = $backoff117 <= 32 || !$doBackoff118;
                        }
                        $commit112 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            mc = tmp.createThresholdContract(rate, base, time);
                        }
                        catch (final fabric.worker.RetryException $e114) {
                            $commit112 = false;
                            continue $label111;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e114) {
                            $commit112 = false;
                            fabric.common.TransactionID $currentTid115 =
                              $tm116.getCurrentTid();
                            if ($e114.tid.isDescendantOf($currentTid115))
                                continue $label111;
                            if ($currentTid115.parent != null) {
                                $retry113 = false;
                                throw $e114;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e114) {
                            $commit112 = false;
                            if ($tm116.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid115 =
                              $tm116.getCurrentTid();
                            if ($e114.tid.isDescendantOf($currentTid115)) {
                                $retry113 = true;
                            }
                            else if ($currentTid115.parent != null) {
                                $retry113 = false;
                                throw $e114;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e114) {
                            $commit112 = false;
                            if ($tm116.checkForStaleObjects())
                                continue $label111;
                            $retry113 = false;
                            throw new fabric.worker.AbortException($e114);
                        }
                        finally {
                            if ($commit112) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e114) {
                                    $commit112 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e114) {
                                    $commit112 = false;
                                    fabric.common.TransactionID $currentTid115 =
                                      $tm116.getCurrentTid();
                                    if ($currentTid115 != null) {
                                        if ($e114.tid.equals($currentTid115) ||
                                              !$e114.tid.isDescendantOf(
                                                           $currentTid115)) {
                                            throw $e114;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit112 && $retry113) {
                                { mc = mc$var110; }
                                continue $label111;
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
                    fabric.metrics.DerivedMetric val$var120 = val;
                    fabric.worker.transaction.TransactionManager $tm126 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled129 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff127 = 1;
                    boolean $doBackoff128 = true;
                    boolean $retry123 = true;
                    $label121: for (boolean $commit122 = false; !$commit122; ) {
                        if ($backoffEnabled129) {
                            if ($doBackoff128) {
                                if ($backoff127 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff127);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e124) {
                                            
                                        }
                                    }
                                }
                                if ($backoff127 < 5000) $backoff127 *= 2;
                            }
                            $doBackoff128 = $backoff127 <= 32 || !$doBackoff128;
                        }
                        $commit122 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                a, term);
                        }
                        catch (final fabric.worker.RetryException $e124) {
                            $commit122 = false;
                            continue $label121;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e124) {
                            $commit122 = false;
                            fabric.common.TransactionID $currentTid125 =
                              $tm126.getCurrentTid();
                            if ($e124.tid.isDescendantOf($currentTid125))
                                continue $label121;
                            if ($currentTid125.parent != null) {
                                $retry123 = false;
                                throw $e124;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e124) {
                            $commit122 = false;
                            if ($tm126.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid125 =
                              $tm126.getCurrentTid();
                            if ($e124.tid.isDescendantOf($currentTid125)) {
                                $retry123 = true;
                            }
                            else if ($currentTid125.parent != null) {
                                $retry123 = false;
                                throw $e124;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e124) {
                            $commit122 = false;
                            if ($tm126.checkForStaleObjects())
                                continue $label121;
                            $retry123 = false;
                            throw new fabric.worker.AbortException($e124);
                        }
                        finally {
                            if ($commit122) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e124) {
                                    $commit122 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e124) {
                                    $commit122 = false;
                                    fabric.common.TransactionID $currentTid125 =
                                      $tm126.getCurrentTid();
                                    if ($currentTid125 != null) {
                                        if ($e124.tid.equals($currentTid125) ||
                                              !$e124.tid.isDescendantOf(
                                                           $currentTid125)) {
                                            throw $e124;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit122 && $retry123) {
                                { val = val$var120; }
                                continue $label121;
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
                    fabric.metrics.DerivedMetric val$var130 = val;
                    fabric.worker.transaction.TransactionManager $tm136 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled139 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff137 = 1;
                    boolean $doBackoff138 = true;
                    boolean $retry133 = true;
                    $label131: for (boolean $commit132 = false; !$commit132; ) {
                        if ($backoffEnabled139) {
                            if ($doBackoff138) {
                                if ($backoff137 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff137);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e134) {
                                            
                                        }
                                    }
                                }
                                if ($backoff137 < 5000) $backoff137 *= 2;
                            }
                            $doBackoff138 = $backoff137 <= 32 || !$doBackoff138;
                        }
                        $commit132 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(terms);
                        }
                        catch (final fabric.worker.RetryException $e134) {
                            $commit132 = false;
                            continue $label131;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e134) {
                            $commit132 = false;
                            fabric.common.TransactionID $currentTid135 =
                              $tm136.getCurrentTid();
                            if ($e134.tid.isDescendantOf($currentTid135))
                                continue $label131;
                            if ($currentTid135.parent != null) {
                                $retry133 = false;
                                throw $e134;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e134) {
                            $commit132 = false;
                            if ($tm136.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid135 =
                              $tm136.getCurrentTid();
                            if ($e134.tid.isDescendantOf($currentTid135)) {
                                $retry133 = true;
                            }
                            else if ($currentTid135.parent != null) {
                                $retry133 = false;
                                throw $e134;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e134) {
                            $commit132 = false;
                            if ($tm136.checkForStaleObjects())
                                continue $label131;
                            $retry133 = false;
                            throw new fabric.worker.AbortException($e134);
                        }
                        finally {
                            if ($commit132) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e134) {
                                    $commit132 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e134) {
                                    $commit132 = false;
                                    fabric.common.TransactionID $currentTid135 =
                                      $tm136.getCurrentTid();
                                    if ($currentTid135 != null) {
                                        if ($e134.tid.equals($currentTid135) ||
                                              !$e134.tid.isDescendantOf(
                                                           $currentTid135)) {
                                            throw $e134;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit132 && $retry133) {
                                { val = val$var130; }
                                continue $label131;
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
                    fabric.metrics.DerivedMetric val$var140 = val;
                    fabric.worker.transaction.TransactionManager $tm146 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled149 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff147 = 1;
                    boolean $doBackoff148 = true;
                    boolean $retry143 = true;
                    $label141: for (boolean $commit142 = false; !$commit142; ) {
                        if ($backoffEnabled149) {
                            if ($doBackoff148) {
                                if ($backoff147 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff147);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e144) {
                                            
                                        }
                                    }
                                }
                                if ($backoff147 < 5000) $backoff147 *= 2;
                            }
                            $doBackoff148 = $backoff147 <= 32 || !$doBackoff148;
                        }
                        $commit142 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(terms);
                        }
                        catch (final fabric.worker.RetryException $e144) {
                            $commit142 = false;
                            continue $label141;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e144) {
                            $commit142 = false;
                            fabric.common.TransactionID $currentTid145 =
                              $tm146.getCurrentTid();
                            if ($e144.tid.isDescendantOf($currentTid145))
                                continue $label141;
                            if ($currentTid145.parent != null) {
                                $retry143 = false;
                                throw $e144;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e144) {
                            $commit142 = false;
                            if ($tm146.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid145 =
                              $tm146.getCurrentTid();
                            if ($e144.tid.isDescendantOf($currentTid145)) {
                                $retry143 = true;
                            }
                            else if ($currentTid145.parent != null) {
                                $retry143 = false;
                                throw $e144;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e144) {
                            $commit142 = false;
                            if ($tm146.checkForStaleObjects())
                                continue $label141;
                            $retry143 = false;
                            throw new fabric.worker.AbortException($e144);
                        }
                        finally {
                            if ($commit142) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e144) {
                                    $commit142 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e144) {
                                    $commit142 = false;
                                    fabric.common.TransactionID $currentTid145 =
                                      $tm146.getCurrentTid();
                                    if ($currentTid145 != null) {
                                        if ($e144.tid.equals($currentTid145) ||
                                              !$e144.tid.isDescendantOf(
                                                           $currentTid145)) {
                                            throw $e144;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit142 && $retry143) {
                                { val = val$var140; }
                                continue $label141;
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
            fabric.lang.security.Label lbl =
              fabric.lang.security.LabelUtil._Impl.noComponents();
            fabric.worker.Store s = $getStore();
            this.set$weakStats(
                   (fabric.lang.arrays.doubleArray)
                     new fabric.lang.arrays.doubleArray._Impl(
                       s).fabric$lang$arrays$doubleArray$(lbl, lbl.confPolicy(),
                                                          4).$getProxy());
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
        public double samples() { return samples(false); }
        
        /**
   * @param useWeakCache
   *            Flag to indicate if a weakly consistent value should be
   *            returned.  If false, a strongly consistent value is returned.
   * @return the number of samples of the {@link Metric}.
   */
        public abstract double samples(boolean useWeakCache);
        
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
        
        public fabric.lang.arrays.doubleArray get$weakStats() {
            return this.weakStats;
        }
        
        public fabric.lang.arrays.doubleArray set$weakStats(
          fabric.lang.arrays.doubleArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.weakStats = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.arrays.doubleArray weakStats;
        
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
                tmp.get$weakStats().set(0, tmp.computeValue(true));
                tmp.get$weakStats().set(1, tmp.computeVelocity(true));
                tmp.get$weakStats().set(2, tmp.computeNoise(true));
                tmp.get$weakStats().set(3, tmp.computeSamples(true));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm155 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled158 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff156 = 1;
                    boolean $doBackoff157 = true;
                    boolean $retry152 = true;
                    $label150: for (boolean $commit151 = false; !$commit151; ) {
                        if ($backoffEnabled158) {
                            if ($doBackoff157) {
                                if ($backoff156 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff156);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e153) {
                                            
                                        }
                                    }
                                }
                                if ($backoff156 < 5000) $backoff156 *= 2;
                            }
                            $doBackoff157 = $backoff156 <= 32 || !$doBackoff157;
                        }
                        $commit151 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.get$weakStats().set(0, tmp.computeValue(true));
                            tmp.get$weakStats().set(1,
                                                    tmp.computeVelocity(true));
                            tmp.get$weakStats().set(2, tmp.computeNoise(true));
                            tmp.get$weakStats().set(3,
                                                    tmp.computeSamples(true));
                        }
                        catch (final fabric.worker.RetryException $e153) {
                            $commit151 = false;
                            continue $label150;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e153) {
                            $commit151 = false;
                            fabric.common.TransactionID $currentTid154 =
                              $tm155.getCurrentTid();
                            if ($e153.tid.isDescendantOf($currentTid154))
                                continue $label150;
                            if ($currentTid154.parent != null) {
                                $retry152 = false;
                                throw $e153;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e153) {
                            $commit151 = false;
                            if ($tm155.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid154 =
                              $tm155.getCurrentTid();
                            if ($e153.tid.isDescendantOf($currentTid154)) {
                                $retry152 = true;
                            }
                            else if ($currentTid154.parent != null) {
                                $retry152 = false;
                                throw $e153;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e153) {
                            $commit151 = false;
                            if ($tm155.checkForStaleObjects())
                                continue $label150;
                            $retry152 = false;
                            throw new fabric.worker.AbortException($e153);
                        }
                        finally {
                            if ($commit151) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e153) {
                                    $commit151 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e153) {
                                    $commit151 = false;
                                    fabric.common.TransactionID $currentTid154 =
                                      $tm155.getCurrentTid();
                                    if ($currentTid154 != null) {
                                        if ($e153.tid.equals($currentTid154) ||
                                              !$e153.tid.isDescendantOf(
                                                           $currentTid154)) {
                                            throw $e153;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit151 && $retry152) {
                                {  }
                                continue $label150;
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
        public double weakValue() { return (double) this.get$weakStats().get(0); }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #samples()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakSamples() {
            return (double) this.get$weakStats().get(3);
        }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #velocity()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakVelocity() {
            return (double) this.get$weakStats().get(1);
        }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #noise()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakNoise() { return (double) this.get$weakStats().get(2); }
        
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
        public abstract double computeSamples(boolean useWeakCache);
        
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
            $writeRef($getStore(), this.weakStats, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.weakStats =
              (fabric.lang.arrays.doubleArray)
                $readRef(fabric.lang.arrays.doubleArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.Metric._Impl src = (fabric.metrics.Metric._Impl)
                                                other;
            this.weakStats = src.weakStats;
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
    
    public static final byte[] $classHash = new byte[] { 121, -110, 87, 71, -93,
    -113, -18, -67, -103, -56, -92, -105, 127, -28, 31, -23, -35, -71, -113, 42,
    85, 103, -13, -45, 127, 27, -66, 5, 13, 109, -24, 30 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520201179000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
