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
                fabric.metrics.DerivedMetric val$var37 = val;
                fabric.worker.transaction.TransactionManager $tm42 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff43 = 1;
                boolean $doBackoff44 = true;
                $label38: for (boolean $commit39 = false; !$commit39; ) {
                    if ($doBackoff44) {
                        if ($backoff43 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff43);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e40) {  }
                            }
                        }
                        if ($backoff43 < 5000) $backoff43 *= 1;
                    }
                    $doBackoff44 = $backoff43 <= 32 || !$doBackoff44;
                    $commit39 = true;
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
                    catch (final fabric.worker.RetryException $e40) {
                        $commit39 = false;
                        continue $label38;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e40) {
                        $commit39 = false;
                        fabric.common.TransactionID $currentTid41 =
                          $tm42.getCurrentTid();
                        if ($e40.tid.isDescendantOf($currentTid41))
                            continue $label38;
                        if ($currentTid41.parent != null) throw $e40;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e40) {
                        $commit39 = false;
                        if ($tm42.checkForStaleObjects()) continue $label38;
                        throw new fabric.worker.AbortException($e40);
                    }
                    finally {
                        if ($commit39) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e40) {
                                $commit39 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e40) {
                                $commit39 = false;
                                fabric.common.TransactionID $currentTid41 =
                                  $tm42.getCurrentTid();
                                if ($currentTid41 != null) {
                                    if ($e40.tid.equals($currentTid41) ||
                                          !$e40.tid.isDescendantOf(
                                                      $currentTid41)) {
                                        throw $e40;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit39) {
                            { val = val$var37; }
                            continue $label38;
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
                fabric.metrics.DerivedMetric val$var45 = val;
                fabric.worker.transaction.TransactionManager $tm50 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff51 = 1;
                boolean $doBackoff52 = true;
                $label46: for (boolean $commit47 = false; !$commit47; ) {
                    if ($doBackoff52) {
                        if ($backoff51 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff51);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e48) {  }
                            }
                        }
                        if ($backoff51 < 5000) $backoff51 *= 1;
                    }
                    $doBackoff52 = $backoff51 <= 32 || !$doBackoff52;
                    $commit47 = true;
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
                    catch (final fabric.worker.RetryException $e48) {
                        $commit47 = false;
                        continue $label46;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e48) {
                        $commit47 = false;
                        fabric.common.TransactionID $currentTid49 =
                          $tm50.getCurrentTid();
                        if ($e48.tid.isDescendantOf($currentTid49))
                            continue $label46;
                        if ($currentTid49.parent != null) throw $e48;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e48) {
                        $commit47 = false;
                        if ($tm50.checkForStaleObjects()) continue $label46;
                        throw new fabric.worker.AbortException($e48);
                    }
                    finally {
                        if ($commit47) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e48) {
                                $commit47 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e48) {
                                $commit47 = false;
                                fabric.common.TransactionID $currentTid49 =
                                  $tm50.getCurrentTid();
                                if ($currentTid49 != null) {
                                    if ($e48.tid.equals($currentTid49) ||
                                          !$e48.tid.isDescendantOf(
                                                      $currentTid49)) {
                                        throw $e48;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit47) {
                            { val = val$var45; }
                            continue $label46;
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
                    fabric.metrics.DerivedMetric val$var53 = val;
                    fabric.worker.transaction.TransactionManager $tm58 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff59 = 1;
                    boolean $doBackoff60 = true;
                    $label54: for (boolean $commit55 = false; !$commit55; ) {
                        if ($doBackoff60) {
                            if ($backoff59 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff59);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e56) {
                                        
                                    }
                                }
                            }
                            if ($backoff59 < 5000) $backoff59 *= 1;
                        }
                        $doBackoff60 = $backoff59 <= 32 || !$doBackoff60;
                        $commit55 = true;
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
                        catch (final fabric.worker.RetryException $e56) {
                            $commit55 = false;
                            continue $label54;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e56) {
                            $commit55 = false;
                            fabric.common.TransactionID $currentTid57 =
                              $tm58.getCurrentTid();
                            if ($e56.tid.isDescendantOf($currentTid57))
                                continue $label54;
                            if ($currentTid57.parent != null) throw $e56;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e56) {
                            $commit55 = false;
                            if ($tm58.checkForStaleObjects()) continue $label54;
                            throw new fabric.worker.AbortException($e56);
                        }
                        finally {
                            if ($commit55) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e56) {
                                    $commit55 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e56) {
                                    $commit55 = false;
                                    fabric.common.TransactionID $currentTid57 =
                                      $tm58.getCurrentTid();
                                    if ($currentTid57 != null) {
                                        if ($e56.tid.equals($currentTid57) ||
                                              !$e56.tid.isDescendantOf(
                                                          $currentTid57)) {
                                            throw $e56;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit55) {
                                { val = val$var53; }
                                continue $label54;
                            }
                        }
                    }
                }
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var61 = val;
                    fabric.worker.transaction.TransactionManager $tm66 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff67 = 1;
                    boolean $doBackoff68 = true;
                    $label62: for (boolean $commit63 = false; !$commit63; ) {
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
                            if ($backoff67 < 5000) $backoff67 *= 1;
                        }
                        $doBackoff68 = $backoff67 <= 32 || !$doBackoff68;
                        $commit63 = true;
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
                        catch (final fabric.worker.RetryException $e64) {
                            $commit63 = false;
                            continue $label62;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e64) {
                            $commit63 = false;
                            fabric.common.TransactionID $currentTid65 =
                              $tm66.getCurrentTid();
                            if ($e64.tid.isDescendantOf($currentTid65))
                                continue $label62;
                            if ($currentTid65.parent != null) throw $e64;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e64) {
                            $commit63 = false;
                            if ($tm66.checkForStaleObjects()) continue $label62;
                            throw new fabric.worker.AbortException($e64);
                        }
                        finally {
                            if ($commit63) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e64) {
                                    $commit63 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e64) {
                                    $commit63 = false;
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
                            if (!$commit63) {
                                { val = val$var61; }
                                continue $label62;
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
                fabric.metrics.contracts.MetricContract mc$var69 = mc;
                fabric.worker.transaction.TransactionManager $tm74 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff75 = 1;
                boolean $doBackoff76 = true;
                $label70: for (boolean $commit71 = false; !$commit71; ) {
                    if ($doBackoff76) {
                        if ($backoff75 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff75);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e72) {  }
                            }
                        }
                        if ($backoff75 < 5000) $backoff75 *= 1;
                    }
                    $doBackoff76 = $backoff75 <= 32 || !$doBackoff76;
                    $commit71 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { mc = createContract(bound); }
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
                            catch (final fabric.worker.AbortException $e72) {
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
                            { mc = mc$var69; }
                            continue $label70;
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
                fabric.metrics.DerivedMetric val$var77 = val;
                fabric.worker.transaction.TransactionManager $tm82 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff83 = 1;
                boolean $doBackoff84 = true;
                $label78: for (boolean $commit79 = false; !$commit79; ) {
                    if ($doBackoff84) {
                        if ($backoff83 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff83);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e80) {  }
                            }
                        }
                        if ($backoff83 < 5000) $backoff83 *= 1;
                    }
                    $doBackoff84 = $backoff83 <= 32 || !$doBackoff84;
                    $commit79 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.ScaledMetric)
                             new fabric.metrics.ScaledMetric._Impl(s).$getProxy(
                                                                        )).
                            fabric$metrics$ScaledMetric$(a, term);
                    }
                    catch (final fabric.worker.RetryException $e80) {
                        $commit79 = false;
                        continue $label78;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e80) {
                        $commit79 = false;
                        fabric.common.TransactionID $currentTid81 =
                          $tm82.getCurrentTid();
                        if ($e80.tid.isDescendantOf($currentTid81))
                            continue $label78;
                        if ($currentTid81.parent != null) throw $e80;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e80) {
                        $commit79 = false;
                        if ($tm82.checkForStaleObjects()) continue $label78;
                        throw new fabric.worker.AbortException($e80);
                    }
                    finally {
                        if ($commit79) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e80) {
                                $commit79 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e80) {
                                $commit79 = false;
                                fabric.common.TransactionID $currentTid81 =
                                  $tm82.getCurrentTid();
                                if ($currentTid81 != null) {
                                    if ($e80.tid.equals($currentTid81) ||
                                          !$e80.tid.isDescendantOf(
                                                      $currentTid81)) {
                                        throw $e80;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit79) {
                            { val = val$var77; }
                            continue $label78;
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
                fabric.metrics.DerivedMetric val$var85 = val;
                fabric.worker.transaction.TransactionManager $tm90 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff91 = 1;
                boolean $doBackoff92 = true;
                $label86: for (boolean $commit87 = false; !$commit87; ) {
                    if ($doBackoff92) {
                        if ($backoff91 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff91);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e88) {  }
                            }
                        }
                        if ($backoff91 < 5000) $backoff91 *= 1;
                    }
                    $doBackoff92 = $backoff91 <= 32 || !$doBackoff92;
                    $commit87 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.SumMetric)
                             new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                            fabric$metrics$SumMetric$(terms);
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
                            catch (final fabric.worker.AbortException $e88) {
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
                fabric.metrics.DerivedMetric val$var93 = val;
                fabric.worker.transaction.TransactionManager $tm98 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff99 = 1;
                boolean $doBackoff100 = true;
                $label94: for (boolean $commit95 = false; !$commit95; ) {
                    if ($doBackoff100) {
                        if ($backoff99 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff99);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e96) {  }
                            }
                        }
                        if ($backoff99 < 5000) $backoff99 *= 1;
                    }
                    $doBackoff100 = $backoff99 <= 32 || !$doBackoff100;
                    $commit95 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.MinMetric)
                             new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                            fabric$metrics$MinMetric$(terms);
                    }
                    catch (final fabric.worker.RetryException $e96) {
                        $commit95 = false;
                        continue $label94;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e96) {
                        $commit95 = false;
                        fabric.common.TransactionID $currentTid97 =
                          $tm98.getCurrentTid();
                        if ($e96.tid.isDescendantOf($currentTid97))
                            continue $label94;
                        if ($currentTid97.parent != null) throw $e96;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e96) {
                        $commit95 = false;
                        if ($tm98.checkForStaleObjects()) continue $label94;
                        throw new fabric.worker.AbortException($e96);
                    }
                    finally {
                        if ($commit95) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e96) {
                                $commit95 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e96) {
                                $commit95 = false;
                                fabric.common.TransactionID $currentTid97 =
                                  $tm98.getCurrentTid();
                                if ($currentTid97 != null) {
                                    if ($e96.tid.equals($currentTid97) ||
                                          !$e96.tid.isDescendantOf(
                                                      $currentTid97)) {
                                        throw $e96;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit95) {
                            { val = val$var93; }
                            continue $label94;
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
            {
                fabric.worker.transaction.TransactionManager $tm105 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff106 = 1;
                boolean $doBackoff107 = true;
                $label101: for (boolean $commit102 = false; !$commit102; ) {
                    if ($doBackoff107) {
                        if ($backoff106 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff106);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e103) {
                                    
                                }
                            }
                        }
                        if ($backoff106 < 5000) $backoff106 *= 1;
                    }
                    $doBackoff107 = $backoff106 <= 32 || !$doBackoff107;
                    $commit102 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.common.Logging.METRICS_LOGGER.
                          finest(
                            "REFRESHING " +
                              java.lang.String.
                                valueOf(
                                  fabric.lang.WrappedJavaInlineable.
                                      $unwrap((fabric.metrics.Metric)
                                                this.$getProxy())));
                        this.get$weakStats().set(0, computeValue(true));
                        this.get$weakStats().set(1, computeVelocity(true));
                        this.get$weakStats().set(2, computeNoise(true));
                    }
                    catch (final fabric.worker.RetryException $e103) {
                        $commit102 = false;
                        continue $label101;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e103) {
                        $commit102 = false;
                        fabric.common.TransactionID $currentTid104 =
                          $tm105.getCurrentTid();
                        if ($e103.tid.isDescendantOf($currentTid104))
                            continue $label101;
                        if ($currentTid104.parent != null) throw $e103;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e103) {
                        $commit102 = false;
                        if ($tm105.checkForStaleObjects()) continue $label101;
                        throw new fabric.worker.AbortException($e103);
                    }
                    finally {
                        if ($commit102) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e103) {
                                $commit102 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e103) {
                                $commit102 = false;
                                fabric.common.TransactionID $currentTid104 =
                                  $tm105.getCurrentTid();
                                if ($currentTid104 != null) {
                                    if ($e103.tid.equals($currentTid104) ||
                                          !$e103.tid.isDescendantOf(
                                                       $currentTid104)) {
                                        throw $e103;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit102) {
                            {  }
                            continue $label101;
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
                    fabric.worker.transaction.TransactionManager $tm112 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff113 = 1;
                    boolean $doBackoff114 = true;
                    $label108: for (boolean $commit109 = false; !$commit109; ) {
                        if ($doBackoff114) {
                            if ($backoff113 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff113);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e110) {
                                        
                                    }
                                }
                            }
                            if ($backoff113 < 5000) $backoff113 *= 1;
                        }
                        $doBackoff114 = $backoff113 <= 32 || !$doBackoff114;
                        $commit109 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { s.derivedMap().put(m, m); }
                        catch (final fabric.worker.RetryException $e110) {
                            $commit109 = false;
                            continue $label108;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e110) {
                            $commit109 = false;
                            fabric.common.TransactionID $currentTid111 =
                              $tm112.getCurrentTid();
                            if ($e110.tid.isDescendantOf($currentTid111))
                                continue $label108;
                            if ($currentTid111.parent != null) throw $e110;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e110) {
                            $commit109 = false;
                            if ($tm112.checkForStaleObjects())
                                continue $label108;
                            throw new fabric.worker.AbortException($e110);
                        }
                        finally {
                            if ($commit109) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e110) {
                                    $commit109 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e110) {
                                    $commit109 = false;
                                    fabric.common.TransactionID $currentTid111 =
                                      $tm112.getCurrentTid();
                                    if ($currentTid111 != null) {
                                        if ($e110.tid.equals($currentTid111) ||
                                              !$e110.tid.isDescendantOf(
                                                           $currentTid111)) {
                                            throw $e110;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit109) {
                                {  }
                                continue $label108;
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
    
    public static final byte[] $classHash = new byte[] { 31, -29, -118, 113,
    -12, -61, -31, -69, -78, -42, -74, 59, 11, -43, 33, -106, -58, 67, -74, -87,
    117, 10, -31, -17, 14, 82, 58, 93, 83, -67, -119, 105 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506965756000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
