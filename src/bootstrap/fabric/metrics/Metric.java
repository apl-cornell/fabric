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
                fabric.metrics.DerivedMetric val$var42 = val;
                fabric.worker.transaction.TransactionManager $tm47 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled50 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff48 = 1;
                boolean $doBackoff49 = true;
                $label43: for (boolean $commit44 = false; !$commit44; ) {
                    if ($backoffEnabled50) {
                        if ($doBackoff49) {
                            if ($backoff48 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff48);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e45) {
                                        
                                    }
                                }
                            }
                            if ($backoff48 < 5000) $backoff48 *= 2;
                        }
                        $doBackoff49 = $backoff48 <= 32 || !$doBackoff49;
                    }
                    $commit44 = true;
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
                    catch (final fabric.worker.RetryException $e45) {
                        $commit44 = false;
                        continue $label43;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e45) {
                        $commit44 = false;
                        fabric.common.TransactionID $currentTid46 =
                          $tm47.getCurrentTid();
                        if ($e45.tid.isDescendantOf($currentTid46))
                            continue $label43;
                        if ($currentTid46.parent != null) throw $e45;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e45) {
                        $commit44 = false;
                        if ($tm47.checkForStaleObjects()) continue $label43;
                        throw new fabric.worker.AbortException($e45);
                    }
                    finally {
                        if ($commit44) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e45) {
                                $commit44 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e45) {
                                $commit44 = false;
                                fabric.common.TransactionID $currentTid46 =
                                  $tm47.getCurrentTid();
                                if ($currentTid46 != null) {
                                    if ($e45.tid.equals($currentTid46) ||
                                          !$e45.tid.isDescendantOf(
                                                      $currentTid46)) {
                                        throw $e45;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit44) {
                            { val = val$var42; }
                            continue $label43;
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
                fabric.metrics.DerivedMetric val$var51 = val;
                fabric.worker.transaction.TransactionManager $tm56 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled59 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff57 = 1;
                boolean $doBackoff58 = true;
                $label52: for (boolean $commit53 = false; !$commit53; ) {
                    if ($backoffEnabled59) {
                        if ($doBackoff58) {
                            if ($backoff57 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff57);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e54) {
                                        
                                    }
                                }
                            }
                            if ($backoff57 < 5000) $backoff57 *= 2;
                        }
                        $doBackoff58 = $backoff57 <= 32 || !$doBackoff58;
                    }
                    $commit53 = true;
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
                    catch (final fabric.worker.RetryException $e54) {
                        $commit53 = false;
                        continue $label52;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e54) {
                        $commit53 = false;
                        fabric.common.TransactionID $currentTid55 =
                          $tm56.getCurrentTid();
                        if ($e54.tid.isDescendantOf($currentTid55))
                            continue $label52;
                        if ($currentTid55.parent != null) throw $e54;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e54) {
                        $commit53 = false;
                        if ($tm56.checkForStaleObjects()) continue $label52;
                        throw new fabric.worker.AbortException($e54);
                    }
                    finally {
                        if ($commit53) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e54) {
                                $commit53 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e54) {
                                $commit53 = false;
                                fabric.common.TransactionID $currentTid55 =
                                  $tm56.getCurrentTid();
                                if ($currentTid55 != null) {
                                    if ($e54.tid.equals($currentTid55) ||
                                          !$e54.tid.isDescendantOf(
                                                      $currentTid55)) {
                                        throw $e54;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit53) {
                            { val = val$var51; }
                            continue $label52;
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
                    fabric.metrics.DerivedMetric val$var60 = val;
                    fabric.worker.transaction.TransactionManager $tm65 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled68 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff66 = 1;
                    boolean $doBackoff67 = true;
                    $label61: for (boolean $commit62 = false; !$commit62; ) {
                        if ($backoffEnabled68) {
                            if ($doBackoff67) {
                                if ($backoff66 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff66);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e63) {
                                            
                                        }
                                    }
                                }
                                if ($backoff66 < 5000) $backoff66 *= 2;
                            }
                            $doBackoff67 = $backoff66 <= 32 || !$doBackoff67;
                        }
                        $commit62 = true;
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
                        catch (final fabric.worker.RetryException $e63) {
                            $commit62 = false;
                            continue $label61;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e63) {
                            $commit62 = false;
                            fabric.common.TransactionID $currentTid64 =
                              $tm65.getCurrentTid();
                            if ($e63.tid.isDescendantOf($currentTid64))
                                continue $label61;
                            if ($currentTid64.parent != null) throw $e63;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e63) {
                            $commit62 = false;
                            if ($tm65.checkForStaleObjects()) continue $label61;
                            throw new fabric.worker.AbortException($e63);
                        }
                        finally {
                            if ($commit62) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e63) {
                                    $commit62 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e63) {
                                    $commit62 = false;
                                    fabric.common.TransactionID $currentTid64 =
                                      $tm65.getCurrentTid();
                                    if ($currentTid64 != null) {
                                        if ($e63.tid.equals($currentTid64) ||
                                              !$e63.tid.isDescendantOf(
                                                          $currentTid64)) {
                                            throw $e63;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit62) {
                                { val = val$var60; }
                                continue $label61;
                            }
                        }
                    }
                }
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var69 = val;
                    fabric.worker.transaction.TransactionManager $tm74 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled77 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff75 = 1;
                    boolean $doBackoff76 = true;
                    $label70: for (boolean $commit71 = false; !$commit71; ) {
                        if ($backoffEnabled77) {
                            if ($doBackoff76) {
                                if ($backoff75 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff75);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e72) {
                                            
                                        }
                                    }
                                }
                                if ($backoff75 < 5000) $backoff75 *= 2;
                            }
                            $doBackoff76 = $backoff75 <= 32 || !$doBackoff76;
                        }
                        $commit71 = true;
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
                        catch (final fabric.worker.RetryException $e72) {
                            $commit71 = false;
                            continue $label70;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e72) {
                            $commit71 = false;
                            fabric.common.TransactionID $currentTid73 =
                              $tm74.getCurrentTid();
                            if ($e72.tid.isDescendantOf($currentTid73))
                                continue $label70;
                            if ($currentTid73.parent != null) throw $e72;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e72) {
                            $commit71 = false;
                            if ($tm74.checkForStaleObjects()) continue $label70;
                            throw new fabric.worker.AbortException($e72);
                        }
                        finally {
                            if ($commit71) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e72) {
                                    $commit71 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e72) {
                                    $commit71 = false;
                                    fabric.common.TransactionID $currentTid73 =
                                      $tm74.getCurrentTid();
                                    if ($currentTid73 != null) {
                                        if ($e72.tid.equals($currentTid73) ||
                                              !$e72.tid.isDescendantOf(
                                                          $currentTid73)) {
                                            throw $e72;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit71) {
                                { val = val$var69; }
                                continue $label70;
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
                fabric.metrics.contracts.MetricContract mc$var78 = mc;
                fabric.worker.transaction.TransactionManager $tm83 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled86 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff84 = 1;
                boolean $doBackoff85 = true;
                $label79: for (boolean $commit80 = false; !$commit80; ) {
                    if ($backoffEnabled86) {
                        if ($doBackoff85) {
                            if ($backoff84 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff84);
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
                    $commit80 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { mc = createContract(bound); }
                    catch (final fabric.worker.RetryException $e81) {
                        $commit80 = false;
                        continue $label79;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e81) {
                        $commit80 = false;
                        fabric.common.TransactionID $currentTid82 =
                          $tm83.getCurrentTid();
                        if ($e81.tid.isDescendantOf($currentTid82))
                            continue $label79;
                        if ($currentTid82.parent != null) throw $e81;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e81) {
                        $commit80 = false;
                        if ($tm83.checkForStaleObjects()) continue $label79;
                        throw new fabric.worker.AbortException($e81);
                    }
                    finally {
                        if ($commit80) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e81) {
                                $commit80 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e81) {
                                $commit80 = false;
                                fabric.common.TransactionID $currentTid82 =
                                  $tm83.getCurrentTid();
                                if ($currentTid82 != null) {
                                    if ($e81.tid.equals($currentTid82) ||
                                          !$e81.tid.isDescendantOf(
                                                      $currentTid82)) {
                                        throw $e81;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit80) {
                            { mc = mc$var78; }
                            continue $label79;
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
                fabric.metrics.DerivedMetric val$var87 = val;
                fabric.worker.transaction.TransactionManager $tm92 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled95 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff93 = 1;
                boolean $doBackoff94 = true;
                $label88: for (boolean $commit89 = false; !$commit89; ) {
                    if ($backoffEnabled95) {
                        if ($doBackoff94) {
                            if ($backoff93 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff93);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e90) {
                                        
                                    }
                                }
                            }
                            if ($backoff93 < 5000) $backoff93 *= 2;
                        }
                        $doBackoff94 = $backoff93 <= 32 || !$doBackoff94;
                    }
                    $commit89 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.ScaledMetric)
                             new fabric.metrics.ScaledMetric._Impl(s).$getProxy(
                                                                        )).
                            fabric$metrics$ScaledMetric$(a, term);
                    }
                    catch (final fabric.worker.RetryException $e90) {
                        $commit89 = false;
                        continue $label88;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e90) {
                        $commit89 = false;
                        fabric.common.TransactionID $currentTid91 =
                          $tm92.getCurrentTid();
                        if ($e90.tid.isDescendantOf($currentTid91))
                            continue $label88;
                        if ($currentTid91.parent != null) throw $e90;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e90) {
                        $commit89 = false;
                        if ($tm92.checkForStaleObjects()) continue $label88;
                        throw new fabric.worker.AbortException($e90);
                    }
                    finally {
                        if ($commit89) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e90) {
                                $commit89 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e90) {
                                $commit89 = false;
                                fabric.common.TransactionID $currentTid91 =
                                  $tm92.getCurrentTid();
                                if ($currentTid91 != null) {
                                    if ($e90.tid.equals($currentTid91) ||
                                          !$e90.tid.isDescendantOf(
                                                      $currentTid91)) {
                                        throw $e90;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit89) {
                            { val = val$var87; }
                            continue $label88;
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
                fabric.metrics.DerivedMetric val$var96 = val;
                fabric.worker.transaction.TransactionManager $tm101 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled104 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff102 = 1;
                boolean $doBackoff103 = true;
                $label97: for (boolean $commit98 = false; !$commit98; ) {
                    if ($backoffEnabled104) {
                        if ($doBackoff103) {
                            if ($backoff102 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff102);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e99) {
                                        
                                    }
                                }
                            }
                            if ($backoff102 < 5000) $backoff102 *= 2;
                        }
                        $doBackoff103 = $backoff102 <= 32 || !$doBackoff103;
                    }
                    $commit98 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.SumMetric)
                             new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                            fabric$metrics$SumMetric$(terms);
                    }
                    catch (final fabric.worker.RetryException $e99) {
                        $commit98 = false;
                        continue $label97;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e99) {
                        $commit98 = false;
                        fabric.common.TransactionID $currentTid100 =
                          $tm101.getCurrentTid();
                        if ($e99.tid.isDescendantOf($currentTid100))
                            continue $label97;
                        if ($currentTid100.parent != null) throw $e99;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e99) {
                        $commit98 = false;
                        if ($tm101.checkForStaleObjects()) continue $label97;
                        throw new fabric.worker.AbortException($e99);
                    }
                    finally {
                        if ($commit98) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e99) {
                                $commit98 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e99) {
                                $commit98 = false;
                                fabric.common.TransactionID $currentTid100 =
                                  $tm101.getCurrentTid();
                                if ($currentTid100 != null) {
                                    if ($e99.tid.equals($currentTid100) ||
                                          !$e99.tid.isDescendantOf(
                                                      $currentTid100)) {
                                        throw $e99;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit98) {
                            { val = val$var96; }
                            continue $label97;
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
                fabric.metrics.DerivedMetric val$var105 = val;
                fabric.worker.transaction.TransactionManager $tm110 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled113 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff111 = 1;
                boolean $doBackoff112 = true;
                $label106: for (boolean $commit107 = false; !$commit107; ) {
                    if ($backoffEnabled113) {
                        if ($doBackoff112) {
                            if ($backoff111 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff111);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e108) {
                                        
                                    }
                                }
                            }
                            if ($backoff111 < 5000) $backoff111 *= 2;
                        }
                        $doBackoff112 = $backoff111 <= 32 || !$doBackoff112;
                    }
                    $commit107 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.MinMetric)
                             new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                            fabric$metrics$MinMetric$(terms);
                    }
                    catch (final fabric.worker.RetryException $e108) {
                        $commit107 = false;
                        continue $label106;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e108) {
                        $commit107 = false;
                        fabric.common.TransactionID $currentTid109 =
                          $tm110.getCurrentTid();
                        if ($e108.tid.isDescendantOf($currentTid109))
                            continue $label106;
                        if ($currentTid109.parent != null) throw $e108;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e108) {
                        $commit107 = false;
                        if ($tm110.checkForStaleObjects()) continue $label106;
                        throw new fabric.worker.AbortException($e108);
                    }
                    finally {
                        if ($commit107) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e108) {
                                $commit107 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e108) {
                                $commit107 = false;
                                fabric.common.TransactionID $currentTid109 =
                                  $tm110.getCurrentTid();
                                if ($currentTid109 != null) {
                                    if ($e108.tid.equals($currentTid109) ||
                                          !$e108.tid.isDescendantOf(
                                                       $currentTid109)) {
                                        throw $e108;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit107) {
                            { val = val$var105; }
                            continue $label106;
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
                fabric.worker.transaction.TransactionManager $tm118 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled121 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff119 = 1;
                boolean $doBackoff120 = true;
                $label114: for (boolean $commit115 = false; !$commit115; ) {
                    if ($backoffEnabled121) {
                        if ($doBackoff120) {
                            if ($backoff119 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff119);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e116) {
                                        
                                    }
                                }
                            }
                            if ($backoff119 < 5000) $backoff119 *= 2;
                        }
                        $doBackoff120 = $backoff119 <= 32 || !$doBackoff120;
                    }
                    $commit115 = true;
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
                    catch (final fabric.worker.RetryException $e116) {
                        $commit115 = false;
                        continue $label114;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e116) {
                        $commit115 = false;
                        fabric.common.TransactionID $currentTid117 =
                          $tm118.getCurrentTid();
                        if ($e116.tid.isDescendantOf($currentTid117))
                            continue $label114;
                        if ($currentTid117.parent != null) throw $e116;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e116) {
                        $commit115 = false;
                        if ($tm118.checkForStaleObjects()) continue $label114;
                        throw new fabric.worker.AbortException($e116);
                    }
                    finally {
                        if ($commit115) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e116) {
                                $commit115 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e116) {
                                $commit115 = false;
                                fabric.common.TransactionID $currentTid117 =
                                  $tm118.getCurrentTid();
                                if ($currentTid117 != null) {
                                    if ($e116.tid.equals($currentTid117) ||
                                          !$e116.tid.isDescendantOf(
                                                       $currentTid117)) {
                                        throw $e116;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit115) {
                            {  }
                            continue $label114;
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
                    fabric.worker.transaction.TransactionManager $tm126 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { s.derivedMap().put(m, m); }
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
                            if ($tm126.checkForStaleObjects())
                                continue $label122;
                            throw new fabric.worker.AbortException($e124);
                        }
                        finally {
                            if ($commit123) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e124) {
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
                                {  }
                                continue $label122;
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
    public static final long jlc$SourceLastModified$fabil = 1507043109000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
