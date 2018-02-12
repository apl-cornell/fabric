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
   *            {@link MetricContract}
   * @param base
   *            the base parameter for the {@link Bound} on the resuling
   *            {@link MetricContract}
   * @param time
   *            the startTime parameter of the {@link Bound} on the resulting
   *            {@link MetricContract}
   * @return a {@link MetricContract} which enforces that the {@link Metric}
   *         satisfies a {@link Bound} with the given parameters at the given
   *         time. If such a {@link MetricContract} already exists, it is
   *         returned, otherwise a new one is created and returned
   *         (unactivated).
   *
   */
    public fabric.metrics.contracts.MetricContract getContract(double rate,
                                                               double base, long time);
    
    /**
   * @param rate
   *            the rate parameter for the {@link Bound} on the resuling
   *            {@link MetricContract}
   * @param base
   *            the base parameter for the {@link Bound} on the resuling
   *            {@link MetricContract}
   * @return a {@link MetricContract} which enforces that the {@link Metric}
   *         satisfies a {@link Bound} with the given parameters at the
   *         current time. If such a {@link MetricContract} already exists, it
   *         is returned, otherwise a new one is created and returned
   *         (unactivated).
   */
    public fabric.metrics.contracts.MetricContract getContract(double rate, double base);
    
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
   * Used to construct and enforce {@link MetricContract}s bounding this
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
    public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
      double rate, double base, boolean useWeakCache, final fabric.worker.Store s);
    
    /**
   * Used to construct and enforce {@link MetricContract}s bounding this
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
    public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
      double rate, double base, final fabric.worker.Store s);
    
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
   *            a {@link MetricContract} to store with this {@link Metric}
   * @throws IllegalArgumentException
   *             if the {@link MetricContract} doesn't enforce a {@link Bound}
   *             on this {@link Metric}
   */
    public void addContract(fabric.metrics.contracts.MetricContract contract);
    
    /**
   * @param bound
   *        the {@link Bound} that the contract will enforce on this
   *        {@link Metric}
   * @return a {@link MetricContract} asserting this metric satisfies the
   *       given {@link Bound}.
   */
    public fabric.metrics.contracts.MetricContract createContract(double rate,
                                                                  double base, long time);
    
    /**
   * @param time
   *        the time we're searching for {@link MetricContract}s for this
   *        {@link Metric}
   * @return a {@link Set} of {@link MetricContract}s that are currently
   *       enforced for this {@link Metric}
   */
    public int compareTo(java.lang.Object that);
    
    /**
   * {@inheritDoc}
   *
   * If the {@link Observer} is a {@link MetricContract} and is now
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
        
        public fabric.metrics.contracts.MetricContract getContract(double arg1,
                                                                   double arg2,
                                                                   long arg3) {
            return ((fabric.metrics.Metric) fetch()).getContract(arg1, arg2,
                                                                 arg3);
        }
        
        public fabric.metrics.contracts.MetricContract getContract(
          double arg1, double arg2) {
            return ((fabric.metrics.Metric) fetch()).getContract(arg1, arg2);
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
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          double arg1, double arg2, boolean arg3, fabric.worker.Store arg4) {
            return ((fabric.metrics.Metric) fetch()).policy(arg1, arg2, arg3,
                                                            arg4);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          double arg1, double arg2, fabric.worker.Store arg3) {
            return ((fabric.metrics.Metric) fetch()).policy(arg1, arg2, arg3);
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
        
        public void addContract(fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.Metric) fetch()).addContract(arg1);
        }
        
        public fabric.metrics.contracts.MetricContract createContract(
          double arg1, double arg2, long arg3) {
            return ((fabric.metrics.Metric) fetch()).createContract(arg1, arg2,
                                                                    arg3);
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
   *            {@link MetricContract}
   * @param base
   *            the base parameter for the {@link Bound} on the resuling
   *            {@link MetricContract}
   * @param time
   *            the startTime parameter of the {@link Bound} on the resulting
   *            {@link MetricContract}
   * @return a {@link MetricContract} which enforces that the {@link Metric}
   *         satisfies a {@link Bound} with the given parameters at the given
   *         time. If such a {@link MetricContract} already exists, it is
   *         returned, otherwise a new one is created and returned
   *         (unactivated).
   *
   */
        public fabric.metrics.contracts.MetricContract getContract(double rate,
                                                                   double base,
                                                                   long time) {
            return fabric.metrics.Metric._Impl.static_getContract(
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy(), rate, base,
                                                 time);
        }
        
        private static fabric.metrics.contracts.MetricContract
          static_getContract(fabric.metrics.Metric tmp, double rate,
                             double base, long time) {
            fabric.metrics.contracts.MetricContract mc = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                mc = tmp.createContract(rate, base, time);
            }
            else {
                {
                    fabric.metrics.contracts.MetricContract mc$var100 = mc;
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
                        try { mc = tmp.createContract(rate, base, time); }
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
   *            {@link MetricContract}
   * @param base
   *            the base parameter for the {@link Bound} on the resuling
   *            {@link MetricContract}
   * @return a {@link MetricContract} which enforces that the {@link Metric}
   *         satisfies a {@link Bound} with the given parameters at the
   *         current time. If such a {@link MetricContract} already exists, it
   *         is returned, otherwise a new one is created and returned
   *         (unactivated).
   */
        public fabric.metrics.contracts.MetricContract getContract(
          double rate, double base) {
            return getContract(rate, base, 0);
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
                    fabric.metrics.DerivedMetric val$var110 = val;
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
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                a, term);
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
                                { val = val$var110; }
                                continue $label111;
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
                              ((fabric.metrics.SumMetric)
                                 new fabric.metrics.SumMetric._Impl(s).
                                 $getProxy()).fabric$metrics$SumMetric$(terms);
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
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).fabric$metrics$MinMetric$(terms);
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
   * Used to construct and enforce {@link MetricContract}s bounding this
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
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          double rate, double base, boolean useWeakCache,
          final fabric.worker.Store s) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                      new fabric.metrics.contracts.enforcement.DirectPolicy.
                        _Impl(s).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$DirectPolicy$(
                (fabric.metrics.Metric) this.$getProxy(), rate, base);
        }
        
        /**
   * Used to construct and enforce {@link MetricContract}s bounding this
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
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          double rate, double base, final fabric.worker.Store s) {
            return policy(rate, base, false, s);
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
                    fabric.worker.transaction.TransactionManager $tm145 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled148 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff146 = 1;
                    boolean $doBackoff147 = true;
                    boolean $retry142 = true;
                    $label140: for (boolean $commit141 = false; !$commit141; ) {
                        if ($backoffEnabled148) {
                            if ($doBackoff147) {
                                if ($backoff146 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff146);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e143) {
                                            
                                        }
                                    }
                                }
                                if ($backoff146 < 5000) $backoff146 *= 2;
                            }
                            $doBackoff147 = $backoff146 <= 32 || !$doBackoff147;
                        }
                        $commit141 = true;
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
                        catch (final fabric.worker.RetryException $e143) {
                            $commit141 = false;
                            continue $label140;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e143) {
                            $commit141 = false;
                            fabric.common.TransactionID $currentTid144 =
                              $tm145.getCurrentTid();
                            if ($e143.tid.isDescendantOf($currentTid144))
                                continue $label140;
                            if ($currentTid144.parent != null) {
                                $retry142 = false;
                                throw $e143;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e143) {
                            $commit141 = false;
                            if ($tm145.checkForStaleObjects())
                                continue $label140;
                            $retry142 = false;
                            throw new fabric.worker.AbortException($e143);
                        }
                        finally {
                            if ($commit141) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e143) {
                                    $commit141 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e143) {
                                    $commit141 = false;
                                    fabric.common.TransactionID $currentTid144 =
                                      $tm145.getCurrentTid();
                                    if ($currentTid144 != null) {
                                        if ($e143.tid.equals($currentTid144) ||
                                              !$e143.tid.isDescendantOf(
                                                           $currentTid144)) {
                                            throw $e143;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit141 && $retry142) {
                                {  }
                                continue $label140;
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
   *            a {@link MetricContract} to store with this {@link Metric}
   * @throws IllegalArgumentException
   *             if the {@link MetricContract} doesn't enforce a {@link Bound}
   *             on this {@link Metric}
   */
        public void addContract(
          fabric.metrics.contracts.MetricContract contract) {
            
        }
        
        /**
   * @param bound
   *        the {@link Bound} that the contract will enforce on this
   *        {@link Metric}
   * @return a {@link MetricContract} asserting this metric satisfies the
   *       given {@link Bound}.
   */
        public fabric.metrics.contracts.MetricContract createContract(
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
   * @param time
   *        the time we're searching for {@link MetricContract}s for this
   *        {@link Metric}
   * @return a {@link Set} of {@link MetricContract}s that are currently
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
   *            a {@link MetricContract} to stop storing with this
   *            {@link Metric} (if it is now invalid).
   * @throws IllegalArgumentException
   *             if contract isn't defined on this {@link Metric}
   */
        private void clearContract(
          fabric.metrics.contracts.MetricContract contract) {
            
        }
        
        /**
   * {@inheritDoc}
   *
   * If the {@link Observer} is a {@link MetricContract} and is now
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
                        obs)) instanceof fabric.metrics.contracts.MetricContract) {
                fabric.metrics.contracts.MetricContract mc =
                  (fabric.metrics.contracts.MetricContract)
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
    
    public static final byte[] $classHash = new byte[] { 55, 45, -114, 2, 55,
    17, -107, -58, -99, -46, 126, 89, -124, -28, 81, 90, 45, 60, 21, 17, -124,
    124, 54, -41, 95, -69, 9, 48, 2, -74, -11, -63 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518448064000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
