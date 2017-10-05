package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
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
import java.util.logging.Level;

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
   * @param bound
   *            the {@link Bound} that the returned {@link MetricContract}
   *            will enforce on this {@link Metric}
   * @return a {@link MetricContract} that enforces that the {@link Metric}
   *         satisfies the given {@link Bound}. If such a
   *         {@link MetricContract}, it is returned, otherwise a new one is
   *         created and returned (unactivated).
   */
    public fabric.metrics.contracts.MetricContract getContract(fabric.metrics.contracts.Bound bound);
    
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
    public fabric.metrics.contracts.MetricContract getContract(double rate,
                                                               double base);
    
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
      fabric.metrics.contracts.Bound bound, boolean useWeakCache);
    
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
      fabric.metrics.contracts.Bound bound);
    
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
    public fabric.metrics.contracts.MetricContract createContract(fabric.metrics.contracts.Bound bound);
    
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
        
        public fabric.metrics.Metric min(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.Metric) fetch()).min(arg1);
        }
        
        public fabric.metrics.Metric max(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.Metric) fetch()).max(arg1);
        }
        
        public fabric.metrics.contracts.MetricContract getContract(
          fabric.metrics.contracts.Bound arg1) {
            return ((fabric.metrics.Metric) fetch()).getContract(arg1);
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
          fabric.worker.Store arg1, fabric.lang.arrays.ObjectArray arg2) {
            return fabric.metrics.Metric._Impl.addAtStore(arg1, arg2);
        }
        
        public static fabric.metrics.Metric minAtStore(
          fabric.worker.Store arg1, fabric.lang.arrays.ObjectArray arg2) {
            return fabric.metrics.Metric._Impl.minAtStore(arg1, arg2);
        }
        
        public static fabric.metrics.Metric maxAtStore(
          fabric.worker.Store arg1, fabric.lang.arrays.ObjectArray arg2) {
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
          fabric.metrics.contracts.Bound arg1, boolean arg2) {
            return ((fabric.metrics.Metric) fetch()).policy(arg1, arg2);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          fabric.metrics.contracts.Bound arg1) {
            return ((fabric.metrics.Metric) fetch()).policy(arg1);
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
        
        public double weakVelocity() {
            return ((fabric.metrics.Metric) fetch()).weakVelocity();
        }
        
        public double weakNoise() {
            return ((fabric.metrics.Metric) fetch()).weakNoise();
        }
        
        public double computeValue(boolean arg1) {
            return ((fabric.metrics.Metric) fetch()).computeValue(arg1);
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
          fabric.metrics.contracts.Bound arg1) {
            return ((fabric.metrics.Metric) fetch()).createContract(arg1);
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
            final fabric.worker.Store s = $getStore();
            fabric.metrics.DerivedMetric val = null;
            {
                fabric.metrics.DerivedMetric val$var67 = val;
                fabric.worker.transaction.TransactionManager $tm72 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled75 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff73 = 1;
                boolean $doBackoff74 = true;
                $label68: for (boolean $commit69 = false; !$commit69; ) {
                    if ($backoffEnabled75) {
                        if ($doBackoff74) {
                            if ($backoff73 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff73);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e70) {
                                        
                                    }
                                }
                            }
                            if ($backoff73 < 5000) $backoff73 *= 2;
                        }
                        $doBackoff74 = $backoff73 <= 32 || !$doBackoff74;
                    }
                    $commit69 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.ScaledMetric)
                             new fabric.metrics.ScaledMetric._Impl(s).$getProxy(
                                                                        )).
                            fabric$metrics$ScaledMetric$(scalar,
                                                         (fabric.metrics.Metric)
                                                           this.$getProxy());
                    }
                    catch (final fabric.worker.RetryException $e70) {
                        $commit69 = false;
                        continue $label68;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e70) {
                        $commit69 = false;
                        fabric.common.TransactionID $currentTid71 =
                          $tm72.getCurrentTid();
                        if ($e70.tid.isDescendantOf($currentTid71))
                            continue $label68;
                        if ($currentTid71.parent != null) throw $e70;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e70) {
                        $commit69 = false;
                        if ($tm72.checkForStaleObjects()) continue $label68;
                        throw new fabric.worker.AbortException($e70);
                    }
                    finally {
                        if ($commit69) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e70) {
                                $commit69 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e70) {
                                $commit69 = false;
                                fabric.common.TransactionID $currentTid71 =
                                  $tm72.getCurrentTid();
                                if ($currentTid71 != null) {
                                    if ($e70.tid.equals($currentTid71) ||
                                          !$e70.tid.isDescendantOf(
                                                      $currentTid71)) {
                                        throw $e70;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit69) {
                            { val = val$var67; }
                            continue $label68;
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
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.SumMetric)
                return other.plus((fabric.metrics.Metric) this.$getProxy());
            final fabric.worker.Store s = $getStore();
            fabric.metrics.DerivedMetric val = null;
            {
                fabric.metrics.DerivedMetric val$var76 = val;
                fabric.worker.transaction.TransactionManager $tm81 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled84 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff82 = 1;
                boolean $doBackoff83 = true;
                $label77: for (boolean $commit78 = false; !$commit78; ) {
                    if ($backoffEnabled84) {
                        if ($doBackoff83) {
                            if ($backoff82 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff82);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e79) {
                                        
                                    }
                                }
                            }
                            if ($backoff82 < 5000) $backoff82 *= 2;
                        }
                        $doBackoff83 = $backoff82 <= 32 || !$doBackoff83;
                    }
                    $commit78 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.SumMetric)
                             new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                            fabric$metrics$SumMetric$(
                              fabric.lang.arrays.internal.Compat.
                                  convert(
                                    this.$getStore(), this.get$$updateLabel(),
                                    this.get$$updateLabel().confPolicy(),
                                    new fabric.lang.Object[] { (fabric.metrics.Metric) this.$getProxy(), other }));
                    }
                    catch (final fabric.worker.RetryException $e79) {
                        $commit78 = false;
                        continue $label77;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e79) {
                        $commit78 = false;
                        fabric.common.TransactionID $currentTid80 =
                          $tm81.getCurrentTid();
                        if ($e79.tid.isDescendantOf($currentTid80))
                            continue $label77;
                        if ($currentTid80.parent != null) throw $e79;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e79) {
                        $commit78 = false;
                        if ($tm81.checkForStaleObjects()) continue $label77;
                        throw new fabric.worker.AbortException($e79);
                    }
                    finally {
                        if ($commit78) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e79) {
                                $commit78 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e79) {
                                $commit78 = false;
                                fabric.common.TransactionID $currentTid80 =
                                  $tm81.getCurrentTid();
                                if ($currentTid80 != null) {
                                    if ($e79.tid.equals($currentTid80) ||
                                          !$e79.tid.isDescendantOf(
                                                      $currentTid80)) {
                                        throw $e79;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit78) {
                            { val = val$var76; }
                            continue $label77;
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
            if (this.equals(other))
                return (fabric.metrics.Metric) this.$getProxy();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.MinMetric)
                return other.min((fabric.metrics.Metric) this.$getProxy());
            final fabric.worker.Store s = $getStore();
            fabric.metrics.DerivedMetric val = null;
            if (this.compareTo(
                       (java.lang.Object)
                         fabric.lang.WrappedJavaInlineable.$unwrap(other)) >
                  0) {
                {
                    fabric.metrics.DerivedMetric val$var85 = val;
                    fabric.worker.transaction.TransactionManager $tm90 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled93 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff91 = 1;
                    boolean $doBackoff92 = true;
                    $label86: for (boolean $commit87 = false; !$commit87; ) {
                        if ($backoffEnabled93) {
                            if ($doBackoff92) {
                                if ($backoff91 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff91);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e88) {
                                            
                                        }
                                    }
                                }
                                if ($backoff91 < 5000) $backoff91 *= 2;
                            }
                            $doBackoff92 = $backoff91 <= 32 || !$doBackoff92;
                        }
                        $commit87 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).
                                fabric$metrics$MinMetric$(
                                  fabric.lang.arrays.internal.Compat.
                                      convert(
                                        this.$getStore(),
                                        this.get$$updateLabel(),
                                        this.get$$updateLabel().confPolicy(),
                                        new fabric.lang.Object[] { other,
                                          (fabric.metrics.Metric)
                                            this.$getProxy() }));
                        }
                        catch (final fabric.worker.RetryException $e88) {
                            $commit87 = false;
                            continue $label86;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e88) {
                            $commit87 = false;
                            fabric.common.TransactionID $currentTid89 =
                              $tm90.getCurrentTid();
                            if ($e88.tid.isDescendantOf($currentTid89))
                                continue $label86;
                            if ($currentTid89.parent != null) throw $e88;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e88) {
                            $commit87 = false;
                            if ($tm90.checkForStaleObjects()) continue $label86;
                            throw new fabric.worker.AbortException($e88);
                        }
                        finally {
                            if ($commit87) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e88) {
                                    $commit87 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e88) {
                                    $commit87 = false;
                                    fabric.common.TransactionID $currentTid89 =
                                      $tm90.getCurrentTid();
                                    if ($currentTid89 != null) {
                                        if ($e88.tid.equals($currentTid89) ||
                                              !$e88.tid.isDescendantOf(
                                                          $currentTid89)) {
                                            throw $e88;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit87) {
                                { val = val$var85; }
                                continue $label86;
                            }
                        }
                    }
                }
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var94 = val;
                    fabric.worker.transaction.TransactionManager $tm99 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled102 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff100 = 1;
                    boolean $doBackoff101 = true;
                    $label95: for (boolean $commit96 = false; !$commit96; ) {
                        if ($backoffEnabled102) {
                            if ($doBackoff101) {
                                if ($backoff100 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff100);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e97) {
                                            
                                        }
                                    }
                                }
                                if ($backoff100 < 5000) $backoff100 *= 2;
                            }
                            $doBackoff101 = $backoff100 <= 32 || !$doBackoff101;
                        }
                        $commit96 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.MinMetric)
                                 new fabric.metrics.MinMetric._Impl(s).
                                 $getProxy()).
                                fabric$metrics$MinMetric$(
                                  fabric.lang.arrays.internal.Compat.
                                      convert(
                                        this.$getStore(),
                                        this.get$$updateLabel(),
                                        this.get$$updateLabel(
                                               ).confPolicy(
                                                   ), new fabric.lang.Object[] { (fabric.metrics.Metric) this.$getProxy(), other }));
                        }
                        catch (final fabric.worker.RetryException $e97) {
                            $commit96 = false;
                            continue $label95;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e97) {
                            $commit96 = false;
                            fabric.common.TransactionID $currentTid98 =
                              $tm99.getCurrentTid();
                            if ($e97.tid.isDescendantOf($currentTid98))
                                continue $label95;
                            if ($currentTid98.parent != null) throw $e97;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e97) {
                            $commit96 = false;
                            if ($tm99.checkForStaleObjects()) continue $label95;
                            throw new fabric.worker.AbortException($e97);
                        }
                        finally {
                            if ($commit96) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e97) {
                                    $commit96 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e97) {
                                    $commit96 = false;
                                    fabric.common.TransactionID $currentTid98 =
                                      $tm99.getCurrentTid();
                                    if ($currentTid98 != null) {
                                        if ($e97.tid.equals($currentTid98) ||
                                              !$e97.tid.isDescendantOf(
                                                          $currentTid98)) {
                                            throw $e97;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit96) {
                                { val = val$var94; }
                                continue $label95;
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
   * @param bound
   *            the {@link Bound} that the returned {@link MetricContract}
   *            will enforce on this {@link Metric}
   * @return a {@link MetricContract} that enforces that the {@link Metric}
   *         satisfies the given {@link Bound}. If such a
   *         {@link MetricContract}, it is returned, otherwise a new one is
   *         created and returned (unactivated).
   */
        public fabric.metrics.contracts.MetricContract getContract(
          fabric.metrics.contracts.Bound bound) {
            fabric.metrics.contracts.MetricContract mc = null;
            {
                fabric.metrics.contracts.MetricContract mc$var103 = mc;
                fabric.worker.transaction.TransactionManager $tm108 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled111 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff109 = 1;
                boolean $doBackoff110 = true;
                $label104: for (boolean $commit105 = false; !$commit105; ) {
                    if ($backoffEnabled111) {
                        if ($doBackoff110) {
                            if ($backoff109 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff109);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e106) {
                                        
                                    }
                                }
                            }
                            if ($backoff109 < 5000) $backoff109 *= 2;
                        }
                        $doBackoff110 = $backoff109 <= 32 || !$doBackoff110;
                    }
                    $commit105 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { mc = createContract(bound); }
                    catch (final fabric.worker.RetryException $e106) {
                        $commit105 = false;
                        continue $label104;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e106) {
                        $commit105 = false;
                        fabric.common.TransactionID $currentTid107 =
                          $tm108.getCurrentTid();
                        if ($e106.tid.isDescendantOf($currentTid107))
                            continue $label104;
                        if ($currentTid107.parent != null) throw $e106;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e106) {
                        $commit105 = false;
                        if ($tm108.checkForStaleObjects()) continue $label104;
                        throw new fabric.worker.AbortException($e106);
                    }
                    finally {
                        if ($commit105) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e106) {
                                $commit105 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e106) {
                                $commit105 = false;
                                fabric.common.TransactionID $currentTid107 =
                                  $tm108.getCurrentTid();
                                if ($currentTid107 != null) {
                                    if ($e106.tid.equals($currentTid107) ||
                                          !$e106.tid.isDescendantOf(
                                                       $currentTid107)) {
                                        throw $e106;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit105) {
                            { mc = mc$var103; }
                            continue $label104;
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
            return getContract(
                     ((fabric.metrics.contracts.Bound)
                        new fabric.metrics.contracts.Bound._Impl(
                          this.$getStore()).$getProxy()).
                         fabric$metrics$contracts$Bound$(rate, base, time));
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
            return getContract(
                     ((fabric.metrics.contracts.Bound)
                        new fabric.metrics.contracts.Bound._Impl(
                          this.$getStore()).$getProxy()).
                         fabric$metrics$contracts$Bound$(
                           rate, base, java.lang.System.currentTimeMillis()));
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
            {
                fabric.metrics.DerivedMetric val$var112 = val;
                fabric.worker.transaction.TransactionManager $tm117 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled120 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff118 = 1;
                boolean $doBackoff119 = true;
                $label113: for (boolean $commit114 = false; !$commit114; ) {
                    if ($backoffEnabled120) {
                        if ($doBackoff119) {
                            if ($backoff118 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff118);
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
                    $commit114 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.ScaledMetric)
                             new fabric.metrics.ScaledMetric._Impl(s).$getProxy(
                                                                        )).
                            fabric$metrics$ScaledMetric$(a, term);
                    }
                    catch (final fabric.worker.RetryException $e115) {
                        $commit114 = false;
                        continue $label113;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e115) {
                        $commit114 = false;
                        fabric.common.TransactionID $currentTid116 =
                          $tm117.getCurrentTid();
                        if ($e115.tid.isDescendantOf($currentTid116))
                            continue $label113;
                        if ($currentTid116.parent != null) throw $e115;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e115) {
                        $commit114 = false;
                        if ($tm117.checkForStaleObjects()) continue $label113;
                        throw new fabric.worker.AbortException($e115);
                    }
                    finally {
                        if ($commit114) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e115) {
                                $commit114 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e115) {
                                $commit114 = false;
                                fabric.common.TransactionID $currentTid116 =
                                  $tm117.getCurrentTid();
                                if ($currentTid116 != null) {
                                    if ($e115.tid.equals($currentTid116) ||
                                          !$e115.tid.isDescendantOf(
                                                       $currentTid116)) {
                                        throw $e115;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit114) {
                            { val = val$var112; }
                            continue $label113;
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
          final fabric.worker.Store s, fabric.lang.arrays.ObjectArray terms) {
            if (terms.get$length() == 0)
                throw new java.lang.IllegalArgumentException(
                        "Must have at least 1 term!");
            if (terms.get$length() == 1)
                return fabric.metrics.Metric._Impl.scaleAtStore(
                                                     s,
                                                     1.0,
                                                     (fabric.metrics.Metric)
                                                       terms.get(0));
            fabric.metrics.DerivedMetric val = null;
            {
                fabric.metrics.DerivedMetric val$var121 = val;
                fabric.worker.transaction.TransactionManager $tm126 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled129 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff127 = 1;
                boolean $doBackoff128 = true;
                $label122: for (boolean $commit123 = false; !$commit123; ) {
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
                    $commit123 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.SumMetric)
                             new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                            fabric$metrics$SumMetric$(terms);
                    }
                    catch (final fabric.worker.RetryException $e124) {
                        $commit123 = false;
                        continue $label122;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e124) {
                        $commit123 = false;
                        fabric.common.TransactionID $currentTid125 =
                          $tm126.getCurrentTid();
                        if ($e124.tid.isDescendantOf($currentTid125))
                            continue $label122;
                        if ($currentTid125.parent != null) throw $e124;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e124) {
                        $commit123 = false;
                        if ($tm126.checkForStaleObjects()) continue $label122;
                        throw new fabric.worker.AbortException($e124);
                    }
                    finally {
                        if ($commit123) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e124) {
                                $commit123 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e124) {
                                $commit123 = false;
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
                        if (!$commit123) {
                            { val = val$var121; }
                            continue $label122;
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
          final fabric.worker.Store s, fabric.lang.arrays.ObjectArray terms) {
            if (terms.get$length() == 0)
                throw new java.lang.IllegalArgumentException(
                        "Must have at least 1 term!");
            if (terms.get$length() == 1)
                return fabric.metrics.Metric._Impl.scaleAtStore(
                                                     s,
                                                     1.0,
                                                     (fabric.metrics.Metric)
                                                       terms.get(0));
            fabric.metrics.DerivedMetric val = null;
            {
                fabric.metrics.DerivedMetric val$var130 = val;
                fabric.worker.transaction.TransactionManager $tm135 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled138 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff136 = 1;
                boolean $doBackoff137 = true;
                $label131: for (boolean $commit132 = false; !$commit132; ) {
                    if ($backoffEnabled138) {
                        if ($doBackoff137) {
                            if ($backoff136 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff136);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e133) {
                                        
                                    }
                                }
                            }
                            if ($backoff136 < 5000) $backoff136 *= 2;
                        }
                        $doBackoff137 = $backoff136 <= 32 || !$doBackoff137;
                    }
                    $commit132 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.MinMetric)
                             new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                            fabric$metrics$MinMetric$(terms);
                    }
                    catch (final fabric.worker.RetryException $e133) {
                        $commit132 = false;
                        continue $label131;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e133) {
                        $commit132 = false;
                        fabric.common.TransactionID $currentTid134 =
                          $tm135.getCurrentTid();
                        if ($e133.tid.isDescendantOf($currentTid134))
                            continue $label131;
                        if ($currentTid134.parent != null) throw $e133;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e133) {
                        $commit132 = false;
                        if ($tm135.checkForStaleObjects()) continue $label131;
                        throw new fabric.worker.AbortException($e133);
                    }
                    finally {
                        if ($commit132) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e133) {
                                $commit132 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e133) {
                                $commit132 = false;
                                fabric.common.TransactionID $currentTid134 =
                                  $tm135.getCurrentTid();
                                if ($currentTid134 != null) {
                                    if ($e133.tid.equals($currentTid134) ||
                                          !$e133.tid.isDescendantOf(
                                                       $currentTid134)) {
                                        throw $e133;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit132) {
                            { val = val$var130; }
                            continue $label131;
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
          final fabric.worker.Store s, fabric.lang.arrays.ObjectArray terms) {
            if (terms.get$length() == 0)
                throw new java.lang.IllegalArgumentException(
                        "Must have at least 1 term!");
            if (terms.get$length() == 1)
                return fabric.metrics.Metric._Impl.scaleAtStore(
                                                     s,
                                                     1.0,
                                                     (fabric.metrics.Metric)
                                                       terms.get(0));
            for (int i = 0; i < terms.get$length(); i++) {
                terms.set(i, ((fabric.metrics.Metric) terms.get(i)).times(-1));
            }
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                s, fabric.metrics.Metric._Impl.minAtStore(s, terms).times(-1));
        }
        
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
                                                          3).$getProxy());
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
          fabric.metrics.contracts.Bound bound, boolean useWeakCache) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                      new fabric.metrics.contracts.enforcement.DirectPolicy.
                        _Impl(this.$getStore()).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$DirectPolicy$(
                (fabric.metrics.Metric) this.$getProxy(), bound);
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
          fabric.metrics.contracts.Bound bound) {
            return policy(bound, false);
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
        public void refreshWeakEstimates() {
            fabric.common.Logging.METRICS_LOGGER.
              finest(
                "REFRESHING" +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.Metric) this.$getProxy())));
            refreshLocally();
        }
        
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
            {
                fabric.worker.transaction.TransactionManager $tm143 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled146 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff144 = 1;
                boolean $doBackoff145 = true;
                $label139: for (boolean $commit140 = false; !$commit140; ) {
                    if ($backoffEnabled146) {
                        if ($doBackoff145) {
                            if ($backoff144 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff144);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e141) {
                                        
                                    }
                                }
                            }
                            if ($backoff144 < 5000) $backoff144 *= 2;
                        }
                        $doBackoff145 = $backoff144 <= 32 || !$doBackoff145;
                    }
                    $commit140 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.common.Logging.METRICS_LOGGER.
                          finest(
                            "REFRESHING LOCALLY " +
                              java.lang.String.
                                valueOf(
                                  fabric.lang.WrappedJavaInlineable.
                                      $unwrap((fabric.metrics.Metric)
                                                this.$getProxy())));
                        this.get$weakStats().set(0, computeValue(true));
                        this.get$weakStats().set(1, computeVelocity(true));
                        this.get$weakStats().set(2, computeNoise(true));
                    }
                    catch (final fabric.worker.RetryException $e141) {
                        $commit140 = false;
                        continue $label139;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e141) {
                        $commit140 = false;
                        fabric.common.TransactionID $currentTid142 =
                          $tm143.getCurrentTid();
                        if ($e141.tid.isDescendantOf($currentTid142))
                            continue $label139;
                        if ($currentTid142.parent != null) throw $e141;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e141) {
                        $commit140 = false;
                        if ($tm143.checkForStaleObjects()) continue $label139;
                        throw new fabric.worker.AbortException($e141);
                    }
                    finally {
                        if ($commit140) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e141) {
                                $commit140 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e141) {
                                $commit140 = false;
                                fabric.common.TransactionID $currentTid142 =
                                  $tm143.getCurrentTid();
                                if ($currentTid142 != null) {
                                    if ($e141.tid.equals($currentTid142) ||
                                          !$e141.tid.isDescendantOf(
                                                       $currentTid142)) {
                                        throw $e141;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit140) {
                            {  }
                            continue $label139;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.
              finest(
                "REFRESHED " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.Metric) this.$getProxy())) +
                  " to " +
                  (double) this.get$weakStats().get(0) +
                  ", " +
                  (double) this.get$weakStats().get(1) +
                  ", " +
                  (double) this.get$weakStats().get(2));
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
          fabric.metrics.contracts.Bound bound) {
            return ((fabric.metrics.contracts.MetricContract)
                      new fabric.metrics.contracts.MetricContract._Impl(
                        this.$getStore()).$getProxy()).
              fabric$metrics$contracts$MetricContract$((fabric.metrics.Metric)
                                                         this.$getProxy(),
                                                       bound);
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
            fabric.metrics.DerivedMetric orig =
              (fabric.metrics.DerivedMetric)
                fabric.lang.Object._Proxy.$getProxy(s.derivedMap().get(m));
            if (fabric.lang.Object._Proxy.idEquals(orig, null)) {
                {
                    fabric.worker.transaction.TransactionManager $tm151 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled154 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff152 = 1;
                    boolean $doBackoff153 = true;
                    $label147: for (boolean $commit148 = false; !$commit148; ) {
                        if ($backoffEnabled154) {
                            if ($doBackoff153) {
                                if ($backoff152 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff152);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e149) {
                                            
                                        }
                                    }
                                }
                                if ($backoff152 < 5000) $backoff152 *= 2;
                            }
                            $doBackoff153 = $backoff152 <= 32 || !$doBackoff153;
                        }
                        $commit148 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { s.derivedMap().put(m, m); }
                        catch (final fabric.worker.RetryException $e149) {
                            $commit148 = false;
                            continue $label147;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e149) {
                            $commit148 = false;
                            fabric.common.TransactionID $currentTid150 =
                              $tm151.getCurrentTid();
                            if ($e149.tid.isDescendantOf($currentTid150))
                                continue $label147;
                            if ($currentTid150.parent != null) throw $e149;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e149) {
                            $commit148 = false;
                            if ($tm151.checkForStaleObjects())
                                continue $label147;
                            throw new fabric.worker.AbortException($e149);
                        }
                        finally {
                            if ($commit148) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e149) {
                                    $commit148 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e149) {
                                    $commit148 = false;
                                    fabric.common.TransactionID $currentTid150 =
                                      $tm151.getCurrentTid();
                                    if ($currentTid150 != null) {
                                        if ($e149.tid.equals($currentTid150) ||
                                              !$e149.tid.isDescendantOf(
                                                           $currentTid150)) {
                                            throw $e149;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit148) {
                                {  }
                                continue $label147;
                            }
                        }
                    }
                }
                orig = m;
            }
            return orig;
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
    
    public static final byte[] $classHash = new byte[] { 21, 42, 32, -22, 35,
    -34, 55, 53, 77, 61, -15, -50, 25, -54, -18, -18, -67, -83, 78, -87, 113,
    109, -78, -58, 5, 33, -71, 36, 108, 69, 12, 50 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507151083000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
