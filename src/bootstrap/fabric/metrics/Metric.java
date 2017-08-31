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
    public fabric.metrics.contracts.MetricContract getContract(double rate, double base);
    
    /**
   * @param store
   *            the Store that holds this {@link Metric}
   */
    public fabric.metrics.Metric fabric$metrics$Metric$();
    
    public fabric.util.List get$contracts();
    
    public fabric.util.List set$contracts(fabric.util.List val);
    
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
    public fabric.util.List getContracts(long time);
    
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
        public fabric.util.List get$contracts() {
            return ((fabric.metrics.Metric._Impl) fetch()).get$contracts();
        }
        
        public fabric.util.List set$contracts(fabric.util.List val) {
            return ((fabric.metrics.Metric._Impl) fetch()).set$contracts(val);
        }
        
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
        
        public void refreshLocally() {
            ((fabric.metrics.Metric) fetch()).refreshLocally();
        }
        
        public void refreshLocally_remote(fabric.lang.security.Principal arg1) {
            ((fabric.metrics.Metric) fetch()).refreshLocally_remote(arg1);
        }
        
        public static final java.lang.Class[] $paramTypes0 = null;
        
        public void refreshLocally$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                refreshLocally();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "refreshLocally",
                                                  $paramTypes0, null);
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
        
        public fabric.util.List getContracts(long arg1) {
            return ((fabric.metrics.Metric) fetch()).getContracts(arg1);
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
                fabric.metrics.DerivedMetric val$var59 = val;
                fabric.worker.transaction.TransactionManager $tm64 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff65 = 1;
                boolean $doBackoff66 = true;
                $label60: for (boolean $commit61 = false; !$commit61; ) {
                    if ($doBackoff66) {
                        if ($backoff65 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff65);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e62) {  }
                            }
                        }
                        if ($backoff65 < 5000) $backoff65 *= 1;
                    }
                    $doBackoff66 = $backoff65 <= 32 || !$doBackoff66;
                    $commit61 = true;
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
                    catch (final fabric.worker.RetryException $e62) {
                        $commit61 = false;
                        continue $label60;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e62) {
                        $commit61 = false;
                        fabric.common.TransactionID $currentTid63 =
                          $tm64.getCurrentTid();
                        if ($e62.tid.isDescendantOf($currentTid63))
                            continue $label60;
                        if ($currentTid63.parent != null) throw $e62;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e62) {
                        $commit61 = false;
                        if ($tm64.checkForStaleObjects()) continue $label60;
                        throw new fabric.worker.AbortException($e62);
                    }
                    finally {
                        if ($commit61) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e62) {
                                $commit61 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e62) {
                                $commit61 = false;
                                fabric.common.TransactionID $currentTid63 =
                                  $tm64.getCurrentTid();
                                if ($currentTid63 != null) {
                                    if ($e62.tid.equals($currentTid63) ||
                                          !$e62.tid.isDescendantOf(
                                                      $currentTid63)) {
                                        throw $e62;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit61) {
                            { val = val$var59; }
                            continue $label60;
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
                fabric.metrics.DerivedMetric val$var67 = val;
                fabric.worker.transaction.TransactionManager $tm72 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff73 = 1;
                boolean $doBackoff74 = true;
                $label68: for (boolean $commit69 = false; !$commit69; ) {
                    if ($doBackoff74) {
                        if ($backoff73 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff73);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e70) {  }
                            }
                        }
                        if ($backoff73 < 5000) $backoff73 *= 1;
                    }
                    $doBackoff74 = $backoff73 <= 32 || !$doBackoff74;
                    $commit69 = true;
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
                    fabric.metrics.DerivedMetric val$var75 = val;
                    fabric.worker.transaction.TransactionManager $tm80 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff81 = 1;
                    boolean $doBackoff82 = true;
                    $label76: for (boolean $commit77 = false; !$commit77; ) {
                        if ($doBackoff82) {
                            if ($backoff81 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff81);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e78) {
                                        
                                    }
                                }
                            }
                            if ($backoff81 < 5000) $backoff81 *= 1;
                        }
                        $doBackoff82 = $backoff81 <= 32 || !$doBackoff82;
                        $commit77 = true;
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
                        catch (final fabric.worker.RetryException $e78) {
                            $commit77 = false;
                            continue $label76;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e78) {
                            $commit77 = false;
                            fabric.common.TransactionID $currentTid79 =
                              $tm80.getCurrentTid();
                            if ($e78.tid.isDescendantOf($currentTid79))
                                continue $label76;
                            if ($currentTid79.parent != null) throw $e78;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e78) {
                            $commit77 = false;
                            if ($tm80.checkForStaleObjects()) continue $label76;
                            throw new fabric.worker.AbortException($e78);
                        }
                        finally {
                            if ($commit77) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e78) {
                                    $commit77 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e78) {
                                    $commit77 = false;
                                    fabric.common.TransactionID $currentTid79 =
                                      $tm80.getCurrentTid();
                                    if ($currentTid79 != null) {
                                        if ($e78.tid.equals($currentTid79) ||
                                              !$e78.tid.isDescendantOf(
                                                          $currentTid79)) {
                                            throw $e78;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit77) {
                                { val = val$var75; }
                                continue $label76;
                            }
                        }
                    }
                }
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var83 = val;
                    fabric.worker.transaction.TransactionManager $tm88 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff89 = 1;
                    boolean $doBackoff90 = true;
                    $label84: for (boolean $commit85 = false; !$commit85; ) {
                        if ($doBackoff90) {
                            if ($backoff89 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff89);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e86) {
                                        
                                    }
                                }
                            }
                            if ($backoff89 < 5000) $backoff89 *= 1;
                        }
                        $doBackoff90 = $backoff89 <= 32 || !$doBackoff90;
                        $commit85 = true;
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
                        catch (final fabric.worker.RetryException $e86) {
                            $commit85 = false;
                            continue $label84;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e86) {
                            $commit85 = false;
                            fabric.common.TransactionID $currentTid87 =
                              $tm88.getCurrentTid();
                            if ($e86.tid.isDescendantOf($currentTid87))
                                continue $label84;
                            if ($currentTid87.parent != null) throw $e86;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e86) {
                            $commit85 = false;
                            if ($tm88.checkForStaleObjects()) continue $label84;
                            throw new fabric.worker.AbortException($e86);
                        }
                        finally {
                            if ($commit85) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e86) {
                                    $commit85 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e86) {
                                    $commit85 = false;
                                    fabric.common.TransactionID $currentTid87 =
                                      $tm88.getCurrentTid();
                                    if ($currentTid87 != null) {
                                        if ($e86.tid.equals($currentTid87) ||
                                              !$e86.tid.isDescendantOf(
                                                          $currentTid87)) {
                                            throw $e86;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit85) {
                                { val = val$var83; }
                                continue $label84;
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
                fabric.metrics.contracts.MetricContract mc$var91 = mc;
                fabric.worker.transaction.TransactionManager $tm96 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff97 = 1;
                boolean $doBackoff98 = true;
                $label92: for (boolean $commit93 = false; !$commit93; ) {
                    if ($doBackoff98) {
                        if ($backoff97 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff97);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e94) {  }
                            }
                        }
                        if ($backoff97 < 5000) $backoff97 *= 1;
                    }
                    $doBackoff98 = $backoff97 <= 32 || !$doBackoff98;
                    $commit93 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        for (int i = 0; i < this.get$contracts().size(); i++) {
                            fabric.metrics.contracts.MetricContract c =
                              (fabric.metrics.contracts.MetricContract)
                                fabric.lang.Object._Proxy.$getProxy(
                                                            this.get$contracts(
                                                                   ).get(i));
                            if (c.stale()) {
                                removeObserver(c);
                                i--;
                            }
                            else if (fabric.lang.Object._Proxy.idEquals(mc,
                                                                        null) &&
                                       c.isActivated() &&
                                       c.enforces((fabric.metrics.Metric)
                                                    this.$getProxy(), bound)) {
                                mc = c;
                            }
                        }
                        if (fabric.lang.Object._Proxy.idEquals(mc, null))
                            mc = createContract(bound);
                    }
                    catch (final fabric.worker.RetryException $e94) {
                        $commit93 = false;
                        continue $label92;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e94) {
                        $commit93 = false;
                        fabric.common.TransactionID $currentTid95 =
                          $tm96.getCurrentTid();
                        if ($e94.tid.isDescendantOf($currentTid95))
                            continue $label92;
                        if ($currentTid95.parent != null) throw $e94;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e94) {
                        $commit93 = false;
                        if ($tm96.checkForStaleObjects()) continue $label92;
                        throw new fabric.worker.AbortException($e94);
                    }
                    finally {
                        if ($commit93) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e94) {
                                $commit93 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e94) {
                                $commit93 = false;
                                fabric.common.TransactionID $currentTid95 =
                                  $tm96.getCurrentTid();
                                if ($currentTid95 != null) {
                                    if ($e94.tid.equals($currentTid95) ||
                                          !$e94.tid.isDescendantOf(
                                                      $currentTid95)) {
                                        throw $e94;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit93) {
                            { mc = mc$var91; }
                            continue $label92;
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
                fabric.metrics.DerivedMetric val$var99 = val;
                fabric.worker.transaction.TransactionManager $tm104 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff105 = 1;
                boolean $doBackoff106 = true;
                $label100: for (boolean $commit101 = false; !$commit101; ) {
                    if ($doBackoff106) {
                        if ($backoff105 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff105);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e102) {
                                    
                                }
                            }
                        }
                        if ($backoff105 < 5000) $backoff105 *= 1;
                    }
                    $doBackoff106 = $backoff105 <= 32 || !$doBackoff106;
                    $commit101 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.ScaledMetric)
                             new fabric.metrics.ScaledMetric._Impl(s).$getProxy(
                                                                        )).
                            fabric$metrics$ScaledMetric$(a, term);
                    }
                    catch (final fabric.worker.RetryException $e102) {
                        $commit101 = false;
                        continue $label100;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e102) {
                        $commit101 = false;
                        fabric.common.TransactionID $currentTid103 =
                          $tm104.getCurrentTid();
                        if ($e102.tid.isDescendantOf($currentTid103))
                            continue $label100;
                        if ($currentTid103.parent != null) throw $e102;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e102) {
                        $commit101 = false;
                        if ($tm104.checkForStaleObjects()) continue $label100;
                        throw new fabric.worker.AbortException($e102);
                    }
                    finally {
                        if ($commit101) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e102) {
                                $commit101 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e102) {
                                $commit101 = false;
                                fabric.common.TransactionID $currentTid103 =
                                  $tm104.getCurrentTid();
                                if ($currentTid103 != null) {
                                    if ($e102.tid.equals($currentTid103) ||
                                          !$e102.tid.isDescendantOf(
                                                       $currentTid103)) {
                                        throw $e102;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit101) {
                            { val = val$var99; }
                            continue $label100;
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
                fabric.metrics.DerivedMetric val$var107 = val;
                fabric.worker.transaction.TransactionManager $tm112 =
                  fabric.worker.transaction.TransactionManager.getInstance();
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
                                catch (java.lang.InterruptedException $e110) {
                                    
                                }
                            }
                        }
                        if ($backoff113 < 5000) $backoff113 *= 1;
                    }
                    $doBackoff114 = $backoff113 <= 32 || !$doBackoff114;
                    $commit109 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.SumMetric)
                             new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                            fabric$metrics$SumMetric$(terms);
                    }
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
                        if ($tm112.checkForStaleObjects()) continue $label108;
                        throw new fabric.worker.AbortException($e110);
                    }
                    finally {
                        if ($commit109) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e110) {
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
                            { val = val$var107; }
                            continue $label108;
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
                fabric.metrics.DerivedMetric val$var115 = val;
                fabric.worker.transaction.TransactionManager $tm120 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff121 = 1;
                boolean $doBackoff122 = true;
                $label116: for (boolean $commit117 = false; !$commit117; ) {
                    if ($doBackoff122) {
                        if ($backoff121 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff121);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e118) {
                                    
                                }
                            }
                        }
                        if ($backoff121 < 5000) $backoff121 *= 1;
                    }
                    $doBackoff122 = $backoff121 <= 32 || !$doBackoff122;
                    $commit117 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        val =
                          ((fabric.metrics.MinMetric)
                             new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                            fabric$metrics$MinMetric$(terms);
                    }
                    catch (final fabric.worker.RetryException $e118) {
                        $commit117 = false;
                        continue $label116;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e118) {
                        $commit117 = false;
                        fabric.common.TransactionID $currentTid119 =
                          $tm120.getCurrentTid();
                        if ($e118.tid.isDescendantOf($currentTid119))
                            continue $label116;
                        if ($currentTid119.parent != null) throw $e118;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e118) {
                        $commit117 = false;
                        if ($tm120.checkForStaleObjects()) continue $label116;
                        throw new fabric.worker.AbortException($e118);
                    }
                    finally {
                        if ($commit117) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e118) {
                                $commit117 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e118) {
                                $commit117 = false;
                                fabric.common.TransactionID $currentTid119 =
                                  $tm120.getCurrentTid();
                                if ($currentTid119 != null) {
                                    if ($e118.tid.equals($currentTid119) ||
                                          !$e118.tid.isDescendantOf(
                                                       $currentTid119)) {
                                        throw $e118;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit117) {
                            { val = val$var115; }
                            continue $label116;
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
        
        /**
   * @param store
   *            the Store that holds this {@link Metric}
   */
        public fabric.metrics.Metric fabric$metrics$Metric$() {
            this.set$contracts(
                   ((fabric.util.ArrayList)
                      new fabric.util.ArrayList._Impl(
                        this.$getStore()).$getProxy()).fabric$util$ArrayList$(
                                                         ));
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
        
        public fabric.util.List get$contracts() { return this.contracts; }
        
        public fabric.util.List set$contracts(fabric.util.List val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.contracts = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.List contracts;
        
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
            if (fabric.lang.Object._Proxy.
                  idEquals(
                    fabric.worker.transaction.TransactionManager.getInstance().
                        getCurrentLog(),
                    null)) {
                fabric.worker.remote.RemoteWorker w =
                  fabric.worker.Worker.getWorker().getWorker(
                                                     $getStore().name());
                refreshLocally();
            }
            else {
                refreshLocally();
            }
        }
        
        /**
   * Utility to allow for running updates as close to the metric as possible.
   *
   * Ugh, public because we don't allow remote calls for non public methods.
   */
        public void refreshLocally() {
            {
                fabric.worker.transaction.TransactionManager $tm127 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff128 = 1;
                boolean $doBackoff129 = true;
                $label123: for (boolean $commit124 = false; !$commit124; ) {
                    if ($doBackoff129) {
                        if ($backoff128 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff128);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e125) {
                                    
                                }
                            }
                        }
                        if ($backoff128 < 5000) $backoff128 *= 1;
                    }
                    $doBackoff129 = $backoff128 <= 32 || !$doBackoff129;
                    $commit124 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        this.get$weakStats().set(0, computeValue(true));
                        this.get$weakStats().set(1, computeVelocity(true));
                        this.get$weakStats().set(2, computeNoise(true));
                    }
                    catch (final fabric.worker.RetryException $e125) {
                        $commit124 = false;
                        continue $label123;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e125) {
                        $commit124 = false;
                        fabric.common.TransactionID $currentTid126 =
                          $tm127.getCurrentTid();
                        if ($e125.tid.isDescendantOf($currentTid126))
                            continue $label123;
                        if ($currentTid126.parent != null) throw $e125;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e125) {
                        $commit124 = false;
                        if ($tm127.checkForStaleObjects()) continue $label123;
                        throw new fabric.worker.AbortException($e125);
                    }
                    finally {
                        if ($commit124) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e125) {
                                $commit124 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e125) {
                                $commit124 = false;
                                fabric.common.TransactionID $currentTid126 =
                                  $tm127.getCurrentTid();
                                if ($currentTid126 != null) {
                                    if ($e125.tid.equals($currentTid126) ||
                                          !$e125.tid.isDescendantOf(
                                                       $currentTid126)) {
                                        throw $e125;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit124) {
                            {  }
                            continue $label123;
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
            if (!contract.getMetric().equals((fabric.metrics.Metric)
                                               this.$getProxy()))
                throw new java.lang.IllegalArgumentException(
                        "Adding a contract for a different metric!");
            if (!this.get$contracts().contains(contract)) {
                fabric.worker.transaction.TransactionManager $tm134 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff135 = 1;
                boolean $doBackoff136 = true;
                $label130: for (boolean $commit131 = false; !$commit131; ) {
                    if ($doBackoff136) {
                        if ($backoff135 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff135);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e132) {
                                    
                                }
                            }
                        }
                        if ($backoff135 < 5000) $backoff135 *= 1;
                    }
                    $doBackoff136 = $backoff135 <= 32 || !$doBackoff136;
                    $commit131 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.get$contracts().add(contract); }
                    catch (final fabric.worker.RetryException $e132) {
                        $commit131 = false;
                        continue $label130;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e132) {
                        $commit131 = false;
                        fabric.common.TransactionID $currentTid133 =
                          $tm134.getCurrentTid();
                        if ($e132.tid.isDescendantOf($currentTid133))
                            continue $label130;
                        if ($currentTid133.parent != null) throw $e132;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e132) {
                        $commit131 = false;
                        if ($tm134.checkForStaleObjects()) continue $label130;
                        throw new fabric.worker.AbortException($e132);
                    }
                    finally {
                        if ($commit131) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e132) {
                                $commit131 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e132) {
                                $commit131 = false;
                                fabric.common.TransactionID $currentTid133 =
                                  $tm134.getCurrentTid();
                                if ($currentTid133 != null) {
                                    if ($e132.tid.equals($currentTid133) ||
                                          !$e132.tid.isDescendantOf(
                                                       $currentTid133)) {
                                        throw $e132;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit131) {
                            {  }
                            continue $label130;
                        }
                    }
                }
            }
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
        public fabric.util.List getContracts(long time) {
            {
                fabric.worker.transaction.TransactionManager $tm141 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff142 = 1;
                boolean $doBackoff143 = true;
                $label137: for (boolean $commit138 = false; !$commit138; ) {
                    if ($doBackoff143) {
                        if ($backoff142 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff142);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e139) {
                                    
                                }
                            }
                        }
                        if ($backoff142 < 5000) $backoff142 *= 1;
                    }
                    $doBackoff143 = $backoff142 <= 32 || !$doBackoff143;
                    $commit138 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        for (int i = 0; i < this.get$contracts().size(); i++) {
                            fabric.metrics.contracts.MetricContract c =
                              (fabric.metrics.contracts.MetricContract)
                                fabric.lang.Object._Proxy.$getProxy(
                                                            this.get$contracts(
                                                                   ).get(i));
                            if (c.stale(time)) {
                                removeObserver(c);
                                i--;
                            }
                        }
                    }
                    catch (final fabric.worker.RetryException $e139) {
                        $commit138 = false;
                        continue $label137;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e139) {
                        $commit138 = false;
                        fabric.common.TransactionID $currentTid140 =
                          $tm141.getCurrentTid();
                        if ($e139.tid.isDescendantOf($currentTid140))
                            continue $label137;
                        if ($currentTid140.parent != null) throw $e139;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e139) {
                        $commit138 = false;
                        if ($tm141.checkForStaleObjects()) continue $label137;
                        throw new fabric.worker.AbortException($e139);
                    }
                    finally {
                        if ($commit138) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e139) {
                                $commit138 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e139) {
                                $commit138 = false;
                                fabric.common.TransactionID $currentTid140 =
                                  $tm141.getCurrentTid();
                                if ($currentTid140 != null) {
                                    if ($e139.tid.equals($currentTid140) ||
                                          !$e139.tid.isDescendantOf(
                                                       $currentTid140)) {
                                        throw $e139;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit138) {
                            {  }
                            continue $label137;
                        }
                    }
                }
            }
            return this.get$contracts();
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
            if (!contract.getMetric().equals((fabric.metrics.Metric)
                                               this.$getProxy()))
                throw new java.lang.IllegalArgumentException(
                        "clearing a contract for a different metric!");
            if (contract.stale()) {
                fabric.worker.transaction.TransactionManager $tm148 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff149 = 1;
                boolean $doBackoff150 = true;
                $label144: for (boolean $commit145 = false; !$commit145; ) {
                    if ($doBackoff150) {
                        if ($backoff149 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff149);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e146) {
                                    
                                }
                            }
                        }
                        if ($backoff149 < 5000) $backoff149 *= 1;
                    }
                    $doBackoff150 = $backoff149 <= 32 || !$doBackoff150;
                    $commit145 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.get$contracts().remove(contract); }
                    catch (final fabric.worker.RetryException $e146) {
                        $commit145 = false;
                        continue $label144;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e146) {
                        $commit145 = false;
                        fabric.common.TransactionID $currentTid147 =
                          $tm148.getCurrentTid();
                        if ($e146.tid.isDescendantOf($currentTid147))
                            continue $label144;
                        if ($currentTid147.parent != null) throw $e146;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e146) {
                        $commit145 = false;
                        if ($tm148.checkForStaleObjects()) continue $label144;
                        throw new fabric.worker.AbortException($e146);
                    }
                    finally {
                        if ($commit145) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e146) {
                                $commit145 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e146) {
                                $commit145 = false;
                                fabric.common.TransactionID $currentTid147 =
                                  $tm148.getCurrentTid();
                                if ($currentTid147 != null) {
                                    if ($e146.tid.equals($currentTid147) ||
                                          !$e146.tid.isDescendantOf(
                                                       $currentTid147)) {
                                        throw $e146;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit145) {
                            {  }
                            continue $label144;
                        }
                    }
                }
            }
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
                    fabric.worker.transaction.TransactionManager $tm155 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff156 = 1;
                    boolean $doBackoff157 = true;
                    $label151: for (boolean $commit152 = false; !$commit152; ) {
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
                            if ($backoff156 < 5000) $backoff156 *= 1;
                        }
                        $doBackoff157 = $backoff156 <= 32 || !$doBackoff157;
                        $commit152 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { s.derivedMap().put(m, m); }
                        catch (final fabric.worker.RetryException $e153) {
                            $commit152 = false;
                            continue $label151;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e153) {
                            $commit152 = false;
                            fabric.common.TransactionID $currentTid154 =
                              $tm155.getCurrentTid();
                            if ($e153.tid.isDescendantOf($currentTid154))
                                continue $label151;
                            if ($currentTid154.parent != null) throw $e153;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e153) {
                            $commit152 = false;
                            if ($tm155.checkForStaleObjects())
                                continue $label151;
                            throw new fabric.worker.AbortException($e153);
                        }
                        finally {
                            if ($commit152) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e153) {
                                    $commit152 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e153) {
                                    $commit152 = false;
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
                            if (!$commit152) {
                                {  }
                                continue $label151;
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
            $writeRef($getStore(), this.contracts, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.contracts = (fabric.util.List)
                               $readRef(fabric.util.List._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(), in, store,
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
            this.contracts = src.contracts;
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
    
    public static final byte[] $classHash = new byte[] { 119, 123, -94, 66,
    -124, 119, 27, 57, -24, -116, -111, 25, -71, 65, 14, -31, -85, -79, 48,
    -123, 68, 79, -76, 4, -116, -70, 82, -9, 90, -89, 31, -21 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1504203624000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0aC3QU1fXNEvIhIQmBgIQQAgQsCLv8xEIUCeEXskAkgDVW4mT2JRkyO7PMzCaLiqVWC62WFkWLVTmnFQ+KET/FcijF6qnUbxE/9dP6Qa2CB23L8UM8/nrvm7e/2Z3Jbg97ztw7O/Pufff/7puZ3k/IQEMn49rFNlnxmhtD1PAuFtsa/E2ibtBAvSIaxmq42ioV5jTcdnJPoMpDPH5SJImqpsqSqLSqhkmK/evFbtGnUtO3ZlVD7eWkQELCpaLRaRLP5QsiOqkOacrGDkUz+SQp/G89z7fj1+tKHxlASlpIiaw2m6IpS/WaatKI2UKKgjTYRnWjLhCggRYyRKU00Ex1WVTkq2CgpraQMkPuUEUzrFNjFTU0pRsHlhnhENXZnNGLKL4GYuthydR0EL/UEj9syorPLxtmrZ/ktstUCRgbyLUkx08GtitiBwwc7o9q4WMcfYvxOgwfJIOYerso0ShJTpesBkwyxk4R07imEQYAaV6Qmp1abKocVYQLpMwSSRHVDl+zqctqBwwdqIVhFpNUODKFQfkhUeoSO2irSc6xj2uybsGoAmYWJDFJuX0Y4wQ+q7D5LMFbn6y4cNvV6lLVQwSQOUAlBeXPB6IqG9Eq2k51qkrUIiya7L9NHH54q4cQGFxuG2yNOXDN6flTqh5/2hozKs2YlW3rqWS2Srvbil+srJ80ZwCKkR/SDBlDIUlz5tUmfqc2EoJoHx7jiDe90ZuPr/rrZZv30lMeMqiB5EqaEg5CVA2RtGBIVqi+hKpUF00aaCAFVA3Us/sNJA/O/bJKrasr29sNajaQHIVdytXYfzBRO7BAE+XBuay2a9HzkGh2svNIiBBSCgcRCPEsImTZWDgfCed9Jlno69SC1NemhGkPhLcPDirqUqcP8laXJZ+hSz49rJoyDOKXIIoAGb7lDHth/tBZ4hNBeUt7BAFMOUbSArRNNMAvPEYWNCmQBks1JUD1VknZdriBDD18O4uTAoxtA+KTWUIA31baq0Ii7Y7wgkWn97U+Z8UY0nJDQchawnm5cF5LOJCnCNPGC4XIC4WoV4h463c13M+iI9dgaRRjUQQs5oYU0WzX9GCECALTZxijZ2EBTu2CYgH1oGhS8xXLrtw6bgDEY6gnB10EQ2vs2RGvKQ1wJkLIt0olW05+8eBtm7R4npikJiV9Uykx/cbZjaNrEg1AeYuzn1wtPtp6eFONB0tHAVQ1U4S4gxJRZZ8jKQ1royUNrTHQTwrRBqKCt6J1aJDZqWs98SvM6cUIyiz/o7FsArJqeFFz6K7Xj340k60T0cJZklBhm6lZm5CsyKyEpeWQuO1X65TCuLd2Nt1y6ydbLmeGhxHj001Yg7AeklSE7NT0G57e8MY7b+9+xRN3lklyQ+E2RZYiTJch38FPgONbPDDj8AJiqLv1PNurY+kewpknxmWDxFeg+IDoRs0aNagF5HZZbFMoRsrXJROmP/rxtlLL3QpcsYynkyn9M4hfH7mAbH5u3ZkqxkaQcOGJ2y8+zKpmQ+Oc63Rd3IhyRH780ujbnxLvgsiHWmTIV1FWXgizB2EOnMFsMZXB6bZ7sxCMs6xVya57jNTKvhiXyHgstvh676yon3fKSvVYLCKPsWlSfa2YkCYz9gY/94zLPeIheS2klK3OomquFaFOQRi0wPpq1POLfjI46X7yWmktDLWxXKu050HCtPYsiJcYOMfReD7ICnwrcKIVeiYEVRUhE77k+FW8OzSEcFhEIOxkLiMZz+BEBJMsQ5okL6TL3RBZJimQg8Gwib5ns5xnWnbTRck0GGE5NCe81PGaZEVUhZWCCGfHRCtD0SaASEFCpp3m+O00otU7ilYQ0jUTDEgDePnimFQ9VOzChsyAMBht6xVBOBZ31tp9dE/fyMM1H/VZa7e9g0gY+N/ed069NHj0Pla6cnAJYea2t16pnVVSw8QkLGJiRtKEaJMuB6HKdPPmg27d8fPvvNt2WOlpdWjjU5qkRBqrS2OzDI7NMtZtFkax+MSDmw7du2mLZYWy5H5jkRoOPvDqN897dx5/Js3alhvQoFjRWGIKfMVhzrZkAD/a/uLJGge34ulkE2JdVkUl6tJchaodZicb7OeaIVppkgFgcjxtTM9PYPwsPgguQ9DCCCIxoT28avAgtmoUZig0kZpKxaiCIyG4cCFWNNhLRKLDrVVY1ryxDr/N6p7aIilmAXekbF6WswiJl5fjp0bPqe/6oMNyxxib++yj71ve+8ySidLNHjIgVkdS+vhkotrk6jFIp7ANUVcn1ZBqy8oZWtalOqsu91ggrAdnS2jmWA2Jm98qkJYtWRGJuEdNvthmsIIUidUZ9ivhzekZjk8m1JmEdUNIdqkVAdZaHXcp1BSnnQbLpt3X7dgVWHnPdA9XsgmCxtRCUxXaTZWEySaw846YoAUo6Ag4oJPOaeHYn1gQ42V0PAIlRupB0nxO0sjxIruO6X1wvcu9nyL4EfinG9cu/LMindDnWcfAKziuzU5oJJnL8SxnoYV41K1gXH/hIvkvEWwFyXFzEFudKm2N+EJIWNhiW/04W6nSqTcJDpAv7xwL5/Zlpx6SnOH4dEbqlTKuO13U+w2CW0zYkClhNmB7OsFxS7YcztdzvCo7wZHkEo4bsxD8ty6C343gTqjaQVmNS5BG7k5CinZzfFN2ciPJjRzfkIXce13k7kVwD8otRhzlngZHNyHFH3N8JDu5keRJjh/rV+5oTFfZYjrWknkXaGGVdUYVbPLfu6h3AME+kxR2ULM+yoBPcK7jBFbeRMc75s9SOO4gZFiphYe+mJ1ZkOQYx886m2UAU2cAKw/xSmVrFnJgnelgcz7uYg3mt0PJ1sBLB9NpdxEcvVbhRjz8+ey0Q5LnOD7irJ0nvsytiFfA512UOIrgqf6VYFuEBjgeg6XxJxwvc1DCad3NNdjzz0iyfiWcW5T7hf16L9aB8Zjr0fQuqnubYacc68CSH4XELGLl8GsuFnkLwTFgYED3RutMxjVdMjOTzIfjFUIqF3FcnpVJELySxhzIaRjHeRm5+3V2sSF534RLkQ/WE8pxq0ka//+HZUnLIH/2djbZJZgr7RbhRPIW4UR0i/Bxfxb+Qbq+/sNsNwIfpmwE8O+VCAKpHTwLFUtMRoygyyXqPne5dwbBpwg2INAj7OoHLhRfInjHJIPEQCCTCD5FyJhGjkedlQhGThUcD84sgi0jAxQEZ9UEVrq/AtWgNchANQEkGncJx2PPhmqMUzXHQ7NVrdBFNbSTMBBVEyMuqhUhAXTBQjkhNcc4fjhD1Vh8L7FpVciZPMTxHmetEuUtd7k3AkGJSYZbWV/Ds77GSveadHqxkjULRJhIyLnVFp74bdYu67YpN4Rz+objfzsrl5i8tpYgr03TFCqqTLkxLoqjasLIfjdDkBsC7BjOvYbjLgc9EaT2A4xkPceBzLzlsvsWpiKYCBtj2HtqkmxuTCc3888FMOlMQr73N47vPSv+QU57ON7Zr39Q2Gom9/kuOl2AwNePTtHdtDAbNnDjOXZawx18gSTDOC7OzBfzXO7NRzAHAkjVZCNtAMUSBerbZJVjp0cA2TkCOTVy7NKHpThiqYtC2CUKC9wUYl5YCAybCZm6juPx2XkBScZxXOkseUJV3h8X/xIX8ZsR+PF1i6bI0sZo7znbcb9DVWg5JRqkquldFD9vYuRInXb74wXZoU+bNtbCvq+zUx9JvuL480wcR/Yz7X7oovk6BJfGNMd/a9KJXgMcdUKmb+B4bXaiI8kajldmlj/U5R52XcKVJhmm03ZoRzsvpWLXIgM6UNHkj3jsu71uTQ6k06sKhNpMyMxyjkl2egHJjO84/jIzvTa43MOLgmKSYq6XH58uK8wrcjrpl8HUW0HqPo4PZCc9kvyB44f6DahoVkQfM7BnogaVwjoUXnyXoEpySLSmSdmcMfWuclF9M4KwScqTVW/VaVAzaToLFEX9dzNUys84fs/ZAkJZakeEJO9y/Hpm/tvicu9nCK7j753WOjUHTHCsAXcQcn4fx+9nJziSvMfxG5kJ/iuXezcjuBGcxgR3WUxjRt9NyOwvOP5XdrIjyfsc/yMz2W93uXcHgh3c6Cuc1p9yHD8PZn2AkO8PtPAFbzoInnZBXYLAvqAO45z+yfEz/abRhtiKdLeLUvcguMvEL76CobBJHYOJ6VUHfPeDXl9zfOys6IWcXuD4cBZ6PeCi14MI9pikJKqXS6zFXHaIkDknOf7zWVENOT3GcW8Wqh1wUe0ggofjLnMMRdbbQVcvPEHI3Fs5vjYbvRx7O+S0iWM1s9R6wuXeXxAcMslg2WiW1Q6FRjesQnW65WgGzPwsIbUvc7zXuS6kW46Q5D6O787EKeQgk/JZFw3wGaxwxCSFYiCQ8Owz/WIC3b0AiXzRhxz/Kbu6hiSHOH4kI/mt/uxlF/n/juAodASSTqG5cXt8y1S4EDiD+BdHOF6anQpIsoTjeRmp8AQT800XFfBLDuE1yAv7G4W6dDE0Ffh+Tsj8zzh2KmUOMYQkL3CcUS0mOhPS5UmbcALBcfaNC74ApqvZR5AtNukH4fjzQRT4s7CV41kO0tuzmkkyz5bQBZzJTI6nZJET/3HRB189Ch9BVksKFXW3rGAemQzTw7Z5cRvHdVl5hJHM53huvxpEm0z7+1n2AcrKNoPq3danWexNltDnoiburoRPWTMd1LppAnGKnkORaDgIWUtIgwh4LuBKBz2dVxr7k8QyzmkUx4XO6tufJG7HGT05zup5clGRb00ypF1WAymvr7dHYE9n/cVv7Ual+dyVf24t1T9Jd3/QOKXc4VPXc1I+gOd0+3aV5I/YteY160Oo6KfUBX6S3x5WlMRv0hLOc0PQ38vMnAUMFoeYNoXgp2SXm+yDKTxD9T0F1rhiUMsah/9KmC3ZR2UV0cgZny5y6vjHIM3h2Gck1qvQirCOH/r3fjqiLzd/9XH28SVYuLrn6t8tuL5n1JyTN20febCu+N37H552w8KV+3Nu+uOqMy17xpz6H5oRmbaAMAAA";
}
