package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.Set;
import fabric.metrics.DerivedMetric;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.common.TransactionID;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * A contract asserting that a {@link Metric}'s value is above or below a
 * vectorized boundary expression <code>r\u20d7 * (t - startTime) + b\u20d7</code> until
 * the associated expriation time.
 * <p>
 * This class follows the subject-observer pattern. An instance is an observer
 * of either a {@link Metric} or a set of {@link MetricContract}s and can be
 * observed by other {@link Contract}s.
 */
public interface MetricContract extends fabric.metrics.contracts.Contract {
    public fabric.metrics.Metric get$metric();
    
    public fabric.metrics.Metric set$metric(fabric.metrics.Metric val);
    
    public double get$rate();
    
    public double set$rate(double val);
    
    public double postInc$rate();
    
    public double postDec$rate();
    
    public double get$base();
    
    public double set$base(double val);
    
    public double postInc$base();
    
    public double postDec$base();
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      get$currentPolicy();
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      set$currentPolicy(fabric.metrics.contracts.enforcement.EnforcementPolicy val);
    
    /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param rate
   *        the rate of the bound this {@link MetricContract} asserts on
   *        metric.
   * @param base
   *        the base of the bound this {@link MetricContract} asserts on
   *        metric.
   */
    public fabric.metrics.contracts.MetricContract
      fabric$metrics$contracts$MetricContract$(fabric.metrics.Metric metric,
                                               double rate, double base);
    
    /** @return the {@link Metric} that this contract observes. */
    public fabric.metrics.Metric getMetric();
    
    /**
   * @return the expected lifetime of this {@link MetricContract} given the
   *       associated {@link Metric}s current velocity.
   */
    public long getExpectedLifetime();
    
    public void activate_remote(fabric.lang.security.Principal caller);
    
    public void activate();
    
    public void finishActivating(
      fabric.metrics.contracts.enforcement.EnforcementPolicy p);
    
    public void finishActivating_remote(
      fabric.lang.security.Principal caller,
      fabric.metrics.contracts.enforcement.EnforcementPolicy p);
    
    public boolean handleUpdates();
    
    /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
    public boolean refresh(boolean asyncExtension);
    
    /**
   * Check if this implies another {@link MetricContract} being considered.
   *
   * @param otherMetric
   *        the {@link Metric} the other {@link MetricContract} would
   *        assert a bound on
   * @param otherRate
   *        the rate of the bound that would be used by the other
   *        {@link MetricContract}
   * @param otherBase
   *        the base of the bound that would be used by the other
   *        {@link MetricContract}
   * @return true iff this would imply (and therefore) can enforce another
   *       {@link MetricContract} with the given metric and bound.
   */
    public boolean
      implies(fabric.metrics.Metric otherMetric, double otherRate, double otherBase);
    
    /**
   * Check if this implies the other {@link MetricContract}.
   *
   * @param other
   *        the other {@link MetricContract} this is being compared with
   * @return true iff this would imply (and therefore) can enforce other for
   *       the entire duration of other.
   */
    public boolean implies(fabric.metrics.contracts.MetricContract other);
    
    /**
   * Check if this enforces the <strong>same</strong> bound as another
   * {@link MetricContract} being considered. Attempts to refresh this
   * contract if it's gone stale and would otherwise enforce the bound.
   *
   * @param otherMetric
   *        the {@link Metric} the other {@link MetricContract} would
   *        assert a bound on
   * @param otherRate
   *        the rate of the bound that would be used by the other
   *        {@link MetricContract}
   * @param otherBase
   *        the base of the bound that would be used by the other
   *        {@link MetricContract}
   * @return true iff this enforces another {@link MetricContract} with the
   *       given parameters.
   */
    public boolean enforces(fabric.metrics.Metric otherMetric, double otherRate,
                            double otherBase);
    
    public java.lang.String toString();
    
    public fabric.lang.arrays.ObjectArray getLeafSubjects();
    
    /**
   * {@inheritDoc}
   *
   * Stops observing any evidence used by the current enforcement policy (by
   * unapplying the policy).
   */
    public void removeObserver(fabric.metrics.util.Observer obs);
    
    public void acquireReconfigLocks();
    
    /**
   * Create a proxy for this contract on the given store.
   */
    public ProxyMetricContract getProxyContract(final fabric.worker.Store proxyStore);
    
    /**
   * A MetricContract which basically acts as a proxy for another MetricContract
   * to allow local access on another store while the contract is valid.
   *
   * Basically operates by using the original MetricContract as the only witness
   * of this metric contract.
   */
    public static interface ProxyMetricContract
      extends fabric.metrics.contracts.MetricContract {
        public fabric.metrics.contracts.MetricContract get$target();
        
        public fabric.metrics.contracts.MetricContract set$target(
          fabric.metrics.contracts.MetricContract val);
        
        /**
     * @param target
     *        the {@link MetricContract} this proxies
     */
        public ProxyMetricContract
          fabric$metrics$contracts$MetricContract$ProxyMetricContract$(
          fabric.metrics.contracts.MetricContract target);
        
        public void activate();
        
        public boolean refresh(boolean asyncExtension);
        
        public java.lang.String toString();
        
        public ProxyMetricContract getProxyContract(
          final fabric.worker.Store proxyStore);
        
        public static class _Proxy
        extends fabric.metrics.contracts.MetricContract._Proxy
          implements ProxyMetricContract {
            public fabric.metrics.contracts.MetricContract get$target() {
                return ((fabric.metrics.contracts.MetricContract.
                          ProxyMetricContract._Impl) fetch()).get$target();
            }
            
            public fabric.metrics.contracts.MetricContract set$target(
              fabric.metrics.contracts.MetricContract val) {
                return ((fabric.metrics.contracts.MetricContract.
                          ProxyMetricContract._Impl) fetch()).set$target(val);
            }
            
            public fabric.metrics.contracts.MetricContract.ProxyMetricContract
              fabric$metrics$contracts$MetricContract$ProxyMetricContract$(
              fabric.metrics.contracts.MetricContract arg1) {
                return ((fabric.metrics.contracts.MetricContract.
                          ProxyMetricContract) fetch()).
                  fabric$metrics$contracts$MetricContract$ProxyMetricContract$(
                    arg1);
            }
            
            public _Proxy(ProxyMetricContract._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.metrics.contracts.MetricContract._Impl
          implements ProxyMetricContract {
            public fabric.metrics.contracts.MetricContract get$target() {
                return this.target;
            }
            
            public fabric.metrics.contracts.MetricContract set$target(
              fabric.metrics.contracts.MetricContract val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.target = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            protected fabric.metrics.contracts.MetricContract target;
            
            /**
     * @param target
     *        the {@link MetricContract} this proxies
     */
            public ProxyMetricContract
              fabric$metrics$contracts$MetricContract$ProxyMetricContract$(
              fabric.metrics.contracts.MetricContract target) {
                this.set$target(target);
                fabric$metrics$contracts$MetricContract$(target.get$metric(),
                                                         target.get$rate(),
                                                         target.get$base());
                return (ProxyMetricContract) this.$getProxy();
            }
            
            public void activate() {
                fabric.metrics.contracts.MetricContract.ProxyMetricContract._Impl.
                  static_activate((ProxyMetricContract) this.$getProxy());
            }
            
            private static void static_activate(ProxyMetricContract tmp) {
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    if (tmp.isActivated()) return;
                }
                else {
                    {
                        fabric.worker.transaction.TransactionManager $tm381 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled384 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff382 = 1;
                        boolean $doBackoff383 = true;
                        boolean $retry378 = true;
                        $label376: for (boolean $commit377 = false; !$commit377;
                                        ) {
                            if ($backoffEnabled384) {
                                if ($doBackoff383) {
                                    if ($backoff382 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff382);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e379) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff382 < 5000) $backoff382 *= 2;
                                }
                                $doBackoff383 = $backoff382 <= 32 ||
                                                  !$doBackoff383;
                            }
                            $commit377 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { if (tmp.isActivated()) return; }
                            catch (final fabric.worker.RetryException $e379) {
                                $commit377 = false;
                                continue $label376;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e379) {
                                $commit377 = false;
                                fabric.common.TransactionID $currentTid380 =
                                  $tm381.getCurrentTid();
                                if ($e379.tid.isDescendantOf($currentTid380))
                                    continue $label376;
                                if ($currentTid380.parent != null) {
                                    $retry378 = false;
                                    throw $e379;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e379) {
                                $commit377 = false;
                                if ($tm381.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid380 =
                                  $tm381.getCurrentTid();
                                if ($e379.tid.isDescendantOf($currentTid380)) {
                                    $retry378 = true;
                                }
                                else if ($currentTid380.parent != null) {
                                    $retry378 = false;
                                    throw $e379;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e379) {
                                $commit377 = false;
                                if ($tm381.checkForStaleObjects())
                                    continue $label376;
                                $retry378 = false;
                                throw new fabric.worker.AbortException($e379);
                            }
                            finally {
                                if ($commit377) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e379) {
                                        $commit377 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e379) {
                                        $commit377 = false;
                                        fabric.common.TransactionID
                                          $currentTid380 =
                                          $tm381.getCurrentTid();
                                        if ($currentTid380 != null) {
                                            if ($e379.tid.equals(
                                                            $currentTid380) ||
                                                  !$e379.tid.
                                                  isDescendantOf(
                                                    $currentTid380)) {
                                                throw $e379;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit377 && $retry378) {
                                    {  }
                                    continue $label376;
                                }
                            }
                        }
                    }
                }
                fabric.metrics.contracts.enforcement.EnforcementPolicy
                  targetPol = null;
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    final fabric.worker.Store s = tmp.$getStore();
                    targetPol =
                      ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                         new fabric.metrics.contracts.enforcement.WitnessPolicy.
                           _Impl(s).
                         $getProxy()).
                        fabric$metrics$contracts$enforcement$WitnessPolicy$(
                          new fabric.metrics.contracts.MetricContract[] { tmp.
                              get$target() });
                }
                else {
                    {
                        fabric.metrics.contracts.enforcement.EnforcementPolicy
                          targetPol$var385 = targetPol;
                        fabric.worker.transaction.TransactionManager $tm391 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled394 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff392 = 1;
                        boolean $doBackoff393 = true;
                        boolean $retry388 = true;
                        $label386: for (boolean $commit387 = false; !$commit387;
                                        ) {
                            if ($backoffEnabled394) {
                                if ($doBackoff393) {
                                    if ($backoff392 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff392);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e389) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff392 < 5000) $backoff392 *= 2;
                                }
                                $doBackoff393 = $backoff392 <= 32 ||
                                                  !$doBackoff393;
                            }
                            $commit387 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                final fabric.worker.Store s = tmp.$getStore();
                                targetPol =
                                  ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                                     new fabric.metrics.contracts.enforcement.
                                       WitnessPolicy._Impl(s).
                                     $getProxy()).
                                    fabric$metrics$contracts$enforcement$WitnessPolicy$(
                                      new fabric.metrics.contracts.MetricContract[] { tmp.
                                          get$target() });
                            }
                            catch (final fabric.worker.RetryException $e389) {
                                $commit387 = false;
                                continue $label386;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e389) {
                                $commit387 = false;
                                fabric.common.TransactionID $currentTid390 =
                                  $tm391.getCurrentTid();
                                if ($e389.tid.isDescendantOf($currentTid390))
                                    continue $label386;
                                if ($currentTid390.parent != null) {
                                    $retry388 = false;
                                    throw $e389;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e389) {
                                $commit387 = false;
                                if ($tm391.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid390 =
                                  $tm391.getCurrentTid();
                                if ($e389.tid.isDescendantOf($currentTid390)) {
                                    $retry388 = true;
                                }
                                else if ($currentTid390.parent != null) {
                                    $retry388 = false;
                                    throw $e389;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e389) {
                                $commit387 = false;
                                if ($tm391.checkForStaleObjects())
                                    continue $label386;
                                $retry388 = false;
                                throw new fabric.worker.AbortException($e389);
                            }
                            finally {
                                if ($commit387) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e389) {
                                        $commit387 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e389) {
                                        $commit387 = false;
                                        fabric.common.TransactionID
                                          $currentTid390 =
                                          $tm391.getCurrentTid();
                                        if ($currentTid390 != null) {
                                            if ($e389.tid.equals(
                                                            $currentTid390) ||
                                                  !$e389.tid.
                                                  isDescendantOf(
                                                    $currentTid390)) {
                                                throw $e389;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit387 && $retry388) {
                                    { targetPol = targetPol$var385; }
                                    continue $label386;
                                }
                            }
                        }
                    }
                }
                targetPol.activate();
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    tmp.set$$expiry((long) targetPol.expiry());
                    fabric.metrics.contracts.Contract._Impl.static_activate(
                                                              tmp);
                    if (tmp.get$$expiry() >=
                          java.lang.System.currentTimeMillis()) {
                        tmp.set$currentPolicy(targetPol);
                        tmp.get$currentPolicy().apply(tmp);
                        tmp.getMetric().addContract(tmp);
                    }
                }
                else {
                    {
                        fabric.metrics.contracts.enforcement.EnforcementPolicy
                          targetPol$var395 = targetPol;
                        fabric.worker.transaction.TransactionManager $tm401 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled404 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff402 = 1;
                        boolean $doBackoff403 = true;
                        boolean $retry398 = true;
                        $label396: for (boolean $commit397 = false; !$commit397;
                                        ) {
                            if ($backoffEnabled404) {
                                if ($doBackoff403) {
                                    if ($backoff402 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff402);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e399) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff402 < 5000) $backoff402 *= 2;
                                }
                                $doBackoff403 = $backoff402 <= 32 ||
                                                  !$doBackoff403;
                            }
                            $commit397 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                tmp.set$$expiry((long) targetPol.expiry());
                                fabric.metrics.contracts.Contract._Impl.
                                  static_activate(tmp);
                                if (tmp.get$$expiry() >=
                                      java.lang.System.currentTimeMillis()) {
                                    tmp.set$currentPolicy(targetPol);
                                    tmp.get$currentPolicy().apply(tmp);
                                    tmp.getMetric().addContract(tmp);
                                }
                            }
                            catch (final fabric.worker.RetryException $e399) {
                                $commit397 = false;
                                continue $label396;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e399) {
                                $commit397 = false;
                                fabric.common.TransactionID $currentTid400 =
                                  $tm401.getCurrentTid();
                                if ($e399.tid.isDescendantOf($currentTid400))
                                    continue $label396;
                                if ($currentTid400.parent != null) {
                                    $retry398 = false;
                                    throw $e399;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e399) {
                                $commit397 = false;
                                if ($tm401.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid400 =
                                  $tm401.getCurrentTid();
                                if ($e399.tid.isDescendantOf($currentTid400)) {
                                    $retry398 = true;
                                }
                                else if ($currentTid400.parent != null) {
                                    $retry398 = false;
                                    throw $e399;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e399) {
                                $commit397 = false;
                                if ($tm401.checkForStaleObjects())
                                    continue $label396;
                                $retry398 = false;
                                throw new fabric.worker.AbortException($e399);
                            }
                            finally {
                                if ($commit397) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e399) {
                                        $commit397 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e399) {
                                        $commit397 = false;
                                        fabric.common.TransactionID
                                          $currentTid400 =
                                          $tm401.getCurrentTid();
                                        if ($currentTid400 != null) {
                                            if ($e399.tid.equals(
                                                            $currentTid400) ||
                                                  !$e399.tid.
                                                  isDescendantOf(
                                                    $currentTid400)) {
                                                throw $e399;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit397 && $retry398) {
                                    { targetPol = targetPol$var395; }
                                    continue $label396;
                                }
                            }
                        }
                    }
                }
            }
            
            public boolean refresh(boolean asyncExtension) {
                if (!isActivated()) { return false; }
                long currentTime = java.lang.System.currentTimeMillis();
                if (!fabric.lang.Object._Proxy.idEquals(
                                                 this.get$currentPolicy(),
                                                 null)) {
                    long curExpiry = this.get$currentPolicy().expiry();
                    if (curExpiry < currentTime) {
                        this.get$currentPolicy().unapply((ProxyMetricContract)
                                                           this.$getProxy());
                        this.set$currentPolicy(null);
                    }
                    boolean result = update(curExpiry);
                    return result;
                }
                return false;
            }
            
            public java.lang.String toString() {
                return "Proxy @ " +
                $getStore() +
                " for " +
                java.lang.String.
                  valueOf(
                    fabric.lang.WrappedJavaInlineable.$unwrap(
                                                        this.get$target())) +
                " until " +
                this.get$$expiry();
            }
            
            public ProxyMetricContract getProxyContract(
              final fabric.worker.Store proxyStore) {
                return fabric.metrics.contracts.MetricContract.ProxyMetricContract._Impl.
                  static_getProxyContract((ProxyMetricContract)
                                            this.$getProxy(), proxyStore);
            }
            
            private static ProxyMetricContract static_getProxyContract(
              ProxyMetricContract tmp, final fabric.worker.Store proxyStore) {
                ProxyMetricContract proxy = null;
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    proxy =
                      ((ProxyMetricContract)
                         new fabric.metrics.contracts.MetricContract.
                           ProxyMetricContract._Impl(proxyStore).
                         $getProxy()).
                        fabric$metrics$contracts$MetricContract$ProxyMetricContract$(
                          tmp.get$target());
                }
                else {
                    {
                        fabric.metrics.contracts.MetricContract.ProxyMetricContract
                          proxy$var405 = proxy;
                        fabric.worker.transaction.TransactionManager $tm411 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled414 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff412 = 1;
                        boolean $doBackoff413 = true;
                        boolean $retry408 = true;
                        $label406: for (boolean $commit407 = false; !$commit407;
                                        ) {
                            if ($backoffEnabled414) {
                                if ($doBackoff413) {
                                    if ($backoff412 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff412);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e409) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff412 < 5000) $backoff412 *= 2;
                                }
                                $doBackoff413 = $backoff412 <= 32 ||
                                                  !$doBackoff413;
                            }
                            $commit407 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                proxy =
                                  ((ProxyMetricContract)
                                     new fabric.metrics.contracts.
                                       MetricContract.ProxyMetricContract._Impl(
                                       proxyStore).
                                     $getProxy()).
                                    fabric$metrics$contracts$MetricContract$ProxyMetricContract$(
                                      tmp.get$target());
                            }
                            catch (final fabric.worker.RetryException $e409) {
                                $commit407 = false;
                                continue $label406;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e409) {
                                $commit407 = false;
                                fabric.common.TransactionID $currentTid410 =
                                  $tm411.getCurrentTid();
                                if ($e409.tid.isDescendantOf($currentTid410))
                                    continue $label406;
                                if ($currentTid410.parent != null) {
                                    $retry408 = false;
                                    throw $e409;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e409) {
                                $commit407 = false;
                                if ($tm411.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid410 =
                                  $tm411.getCurrentTid();
                                if ($e409.tid.isDescendantOf($currentTid410)) {
                                    $retry408 = true;
                                }
                                else if ($currentTid410.parent != null) {
                                    $retry408 = false;
                                    throw $e409;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e409) {
                                $commit407 = false;
                                if ($tm411.checkForStaleObjects())
                                    continue $label406;
                                $retry408 = false;
                                throw new fabric.worker.AbortException($e409);
                            }
                            finally {
                                if ($commit407) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e409) {
                                        $commit407 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e409) {
                                        $commit407 = false;
                                        fabric.common.TransactionID
                                          $currentTid410 =
                                          $tm411.getCurrentTid();
                                        if ($currentTid410 != null) {
                                            if ($e409.tid.equals(
                                                            $currentTid410) ||
                                                  !$e409.tid.
                                                  isDescendantOf(
                                                    $currentTid410)) {
                                                throw $e409;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit407 && $retry408) {
                                    { proxy = proxy$var405; }
                                    continue $label406;
                                }
                            }
                        }
                    }
                }
                if (tmp.isActivated()) proxy.activate();
                return proxy;
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.MetricContract.
                         ProxyMetricContract._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.target, refTypes, out,
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
                this.target =
                  (fabric.metrics.contracts.MetricContract)
                    $readRef(
                      fabric.metrics.contracts.MetricContract._Proxy.class,
                      (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                      intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.
                  metrics.
                  contracts.
                  MetricContract.
                  ProxyMetricContract.
                  _Impl
                  src =
                  (fabric.metrics.contracts.MetricContract.ProxyMetricContract.
                    _Impl) other;
                this.target = src.target;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy
            extends fabric.
              lang.
              Object.
              _Proxy
              implements fabric.metrics.contracts.MetricContract.
                           ProxyMetricContract._Static
            {
                public _Proxy(fabric.metrics.contracts.MetricContract.
                                ProxyMetricContract._Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.metrics.contracts.MetricContract.
                  ProxyMetricContract._Static $instance;
                
                static {
                    fabric.
                      metrics.
                      contracts.
                      MetricContract.
                      ProxyMetricContract.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        metrics.
                        contracts.
                        MetricContract.
                        ProxyMetricContract.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.metrics.contracts.MetricContract.
                            ProxyMetricContract._Static._Impl.class);
                    $instance =
                      (fabric.metrics.contracts.MetricContract.
                        ProxyMetricContract._Static) impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl
            extends fabric.
              lang.
              Object.
              _Impl
              implements fabric.metrics.contracts.MetricContract.
                           ProxyMetricContract._Static
            {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store, long onum, int version,
                             long expiry, fabric.worker.Store labelStore,
                             long labelOnum,
                             fabric.worker.Store accessPolicyStore,
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
                    return new fabric.metrics.contracts.MetricContract.
                             ProxyMetricContract._Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -53, 49, -74, 62,
        16, 82, 58, 63, 0, 11, 97, 114, 2, -76, -32, -91, -50, -74, -68, -32,
        27, 77, -9, 97, 121, -27, -69, 37, -15, -87, 73, 93 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1519057354000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static class _Proxy extends fabric.metrics.contracts.Contract._Proxy
      implements fabric.metrics.contracts.MetricContract {
        public fabric.metrics.Metric get$metric() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              get$metric();
        }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              set$metric(val);
        }
        
        public double get$rate() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              get$rate();
        }
        
        public double set$rate(double val) {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              set$rate(val);
        }
        
        public double postInc$rate() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              postInc$rate();
        }
        
        public double postDec$rate() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              postDec$rate();
        }
        
        public double get$base() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              get$base();
        }
        
        public double set$base(double val) {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              set$base(val);
        }
        
        public double postInc$base() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              postInc$base();
        }
        
        public double postDec$base() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              postDec$base();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          get$currentPolicy() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              get$currentPolicy();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          set$currentPolicy(
          fabric.metrics.contracts.enforcement.EnforcementPolicy val) {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              set$currentPolicy(val);
        }
        
        public fabric.metrics.contracts.MetricContract
          fabric$metrics$contracts$MetricContract$(fabric.metrics.Metric arg1,
                                                   double arg2, double arg3) {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              fabric$metrics$contracts$MetricContract$(arg1, arg2, arg3);
        }
        
        public fabric.metrics.Metric getMetric() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              getMetric();
        }
        
        public long getExpectedLifetime() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              getExpectedLifetime();
        }
        
        public void activate_remote(fabric.lang.security.Principal arg1) {
            ((fabric.metrics.contracts.MetricContract) fetch()).activate_remote(
                                                                  arg1);
        }
        
        public static final java.lang.Class[] $paramTypes3 = null;
        
        public void activate$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                activate();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "activate",
                                                  $paramTypes3, null);
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public void finishActivating(
          fabric.metrics.contracts.enforcement.EnforcementPolicy arg1) {
            ((fabric.metrics.contracts.MetricContract) fetch()).
              finishActivating(arg1);
        }
        
        public void finishActivating_remote(
          fabric.lang.security.Principal arg1,
          fabric.metrics.contracts.enforcement.EnforcementPolicy arg2) {
            ((fabric.metrics.contracts.MetricContract) fetch()).
              finishActivating_remote(arg1, arg2);
        }
        
        public static final java.lang.Class[] $paramTypes4 =
          { fabric.metrics.contracts.enforcement.EnforcementPolicy.class };
        
        public void finishActivating$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1,
          fabric.metrics.contracts.enforcement.EnforcementPolicy arg2) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                finishActivating(arg2);
            else
                try {
                    $remoteWorker.issueRemoteCall(
                                    this, "finishActivating", $paramTypes4,
                                    new java.lang.Object[] { arg2 });
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public boolean implies(fabric.metrics.Metric arg1, double arg2,
                               double arg3) {
            return ((fabric.metrics.contracts.MetricContract) fetch()).implies(
                                                                         arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public boolean implies(fabric.metrics.contracts.MetricContract arg1) {
            return ((fabric.metrics.contracts.MetricContract) fetch()).implies(
                                                                         arg1);
        }
        
        public boolean enforces(fabric.metrics.Metric arg1, double arg2,
                                double arg3) {
            return ((fabric.metrics.contracts.MetricContract) fetch()).enforces(
                                                                         arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              getLeafSubjects();
        }
        
        public fabric.metrics.contracts.MetricContract.ProxyMetricContract
          getProxyContract(fabric.worker.Store arg1) {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              getProxyContract(arg1);
        }
        
        public _Proxy(MetricContract._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.contracts.Contract._Impl
      implements fabric.metrics.contracts.MetricContract {
        public fabric.metrics.Metric get$metric() { return this.metric; }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.metric = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.Metric metric;
        
        public double get$rate() { return this.rate; }
        
        public double set$rate(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.rate = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$rate() {
            double tmp = this.get$rate();
            this.set$rate((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$rate() {
            double tmp = this.get$rate();
            this.set$rate((double) (tmp - 1));
            return tmp;
        }
        
        public double rate;
        
        public double get$base() { return this.base; }
        
        public double set$base(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.base = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$base() {
            double tmp = this.get$base();
            this.set$base((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$base() {
            double tmp = this.get$base();
            this.set$base((double) (tmp - 1));
            return tmp;
        }
        
        public double base;
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          get$currentPolicy() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.currentPolicy;
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          set$currentPolicy(
          fabric.metrics.contracts.enforcement.EnforcementPolicy val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.currentPolicy = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.contracts.enforcement.EnforcementPolicy currentPolicy;
        
        /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param rate
   *        the rate of the bound this {@link MetricContract} asserts on
   *        metric.
   * @param base
   *        the base of the bound this {@link MetricContract} asserts on
   *        metric.
   */
        public fabric.metrics.contracts.MetricContract
          fabric$metrics$contracts$MetricContract$(fabric.metrics.Metric metric,
                                                   double rate, double base) {
            this.set$metric(metric);
            this.set$rate((double) rate);
            this.set$base((double) base);
            fabric$metrics$contracts$Contract$();
            return (fabric.metrics.contracts.MetricContract) this.$getProxy();
        }
        
        /** @return the {@link Metric} that this contract observes. */
        public fabric.metrics.Metric getMetric() { return this.get$metric(); }
        
        /**
   * @return the expected lifetime of this {@link MetricContract} given the
   *       associated {@link Metric}s current velocity.
   */
        public long getExpectedLifetime() {
            long time = java.lang.System.currentTimeMillis();
            fabric.metrics.Metric m = getMetric();
            double adjustedRate = this.get$rate() - m.velocity();
            return (long)
                     (time +
                        (m.value() -
                           fabric.metrics.contracts.Bound._Impl.value(
                                                                  this.get$rate(
                                                                         ),
                                                                  this.get$base(
                                                                         ),
                                                                  time)) /
                        adjustedRate);
        }
        
        public void activate_remote(fabric.lang.security.Principal caller) {
            activate();
        }
        
        public void activate() {
            fabric.metrics.contracts.MetricContract._Impl.
              static_activate((fabric.metrics.contracts.MetricContract)
                                this.$getProxy());
        }
        
        private static void static_activate(
          fabric.metrics.contracts.MetricContract tmp) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.isActivated()) return;
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm420 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled423 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff421 = 1;
                    boolean $doBackoff422 = true;
                    boolean $retry417 = true;
                    $label415: for (boolean $commit416 = false; !$commit416; ) {
                        if ($backoffEnabled423) {
                            if ($doBackoff422) {
                                if ($backoff421 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff421);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e418) {
                                            
                                        }
                                    }
                                }
                                if ($backoff421 < 5000) $backoff421 *= 2;
                            }
                            $doBackoff422 = $backoff421 <= 32 || !$doBackoff422;
                        }
                        $commit416 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e418) {
                            $commit416 = false;
                            continue $label415;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e418) {
                            $commit416 = false;
                            fabric.common.TransactionID $currentTid419 =
                              $tm420.getCurrentTid();
                            if ($e418.tid.isDescendantOf($currentTid419))
                                continue $label415;
                            if ($currentTid419.parent != null) {
                                $retry417 = false;
                                throw $e418;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e418) {
                            $commit416 = false;
                            if ($tm420.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid419 =
                              $tm420.getCurrentTid();
                            if ($e418.tid.isDescendantOf($currentTid419)) {
                                $retry417 = true;
                            }
                            else if ($currentTid419.parent != null) {
                                $retry417 = false;
                                throw $e418;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e418) {
                            $commit416 = false;
                            if ($tm420.checkForStaleObjects())
                                continue $label415;
                            $retry417 = false;
                            throw new fabric.worker.AbortException($e418);
                        }
                        finally {
                            if ($commit416) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e418) {
                                    $commit416 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e418) {
                                    $commit416 = false;
                                    fabric.common.TransactionID $currentTid419 =
                                      $tm420.getCurrentTid();
                                    if ($currentTid419 != null) {
                                        if ($e418.tid.equals($currentTid419) ||
                                              !$e418.tid.isDescendantOf(
                                                           $currentTid419)) {
                                            throw $e418;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit416 && $retry417) {
                                {  }
                                continue $label415;
                            }
                        }
                    }
                }
            }
            fabric.metrics.contracts.enforcement.EnforcementPolicy startPol =
              null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                startPol = tmp.get$metric().policy(tmp.get$rate(),
                                                   tmp.get$base(), true,
                                                   tmp.$getStore());
            }
            else {
                {
                    fabric.metrics.contracts.enforcement.EnforcementPolicy
                      startPol$var424 = startPol;
                    fabric.worker.transaction.TransactionManager $tm430 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled433 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff431 = 1;
                    boolean $doBackoff432 = true;
                    boolean $retry427 = true;
                    $label425: for (boolean $commit426 = false; !$commit426; ) {
                        if ($backoffEnabled433) {
                            if ($doBackoff432) {
                                if ($backoff431 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff431);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e428) {
                                            
                                        }
                                    }
                                }
                                if ($backoff431 < 5000) $backoff431 *= 2;
                            }
                            $doBackoff432 = $backoff431 <= 32 || !$doBackoff432;
                        }
                        $commit426 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            startPol = tmp.get$metric().policy(tmp.get$rate(),
                                                               tmp.get$base(),
                                                               true,
                                                               tmp.$getStore());
                        }
                        catch (final fabric.worker.RetryException $e428) {
                            $commit426 = false;
                            continue $label425;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e428) {
                            $commit426 = false;
                            fabric.common.TransactionID $currentTid429 =
                              $tm430.getCurrentTid();
                            if ($e428.tid.isDescendantOf($currentTid429))
                                continue $label425;
                            if ($currentTid429.parent != null) {
                                $retry427 = false;
                                throw $e428;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e428) {
                            $commit426 = false;
                            if ($tm430.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid429 =
                              $tm430.getCurrentTid();
                            if ($e428.tid.isDescendantOf($currentTid429)) {
                                $retry427 = true;
                            }
                            else if ($currentTid429.parent != null) {
                                $retry427 = false;
                                throw $e428;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e428) {
                            $commit426 = false;
                            if ($tm430.checkForStaleObjects())
                                continue $label425;
                            $retry427 = false;
                            throw new fabric.worker.AbortException($e428);
                        }
                        finally {
                            if ($commit426) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e428) {
                                    $commit426 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e428) {
                                    $commit426 = false;
                                    fabric.common.TransactionID $currentTid429 =
                                      $tm430.getCurrentTid();
                                    if ($currentTid429 != null) {
                                        if ($e428.tid.equals($currentTid429) ||
                                              !$e428.tid.isDescendantOf(
                                                           $currentTid429)) {
                                            throw $e428;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit426 && $retry427) {
                                { startPol = startPol$var424; }
                                continue $label425;
                            }
                        }
                    }
                }
            }
            startPol.activate();
            tmp.finishActivating(startPol);
        }
        
        public void finishActivating(
          fabric.metrics.contracts.enforcement.EnforcementPolicy p) {
            fabric.metrics.contracts.MetricContract._Impl.
              static_finishActivating((fabric.metrics.contracts.MetricContract)
                                        this.$getProxy(), p);
        }
        
        private static void static_finishActivating(
          fabric.metrics.contracts.MetricContract tmp,
          fabric.metrics.contracts.enforcement.EnforcementPolicy p) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.set$$expiry((long) p.expiry());
                fabric.metrics.contracts.Contract._Impl.static_activate(tmp);
                if (tmp.get$$expiry() >= java.lang.System.currentTimeMillis()) {
                    tmp.set$currentPolicy(p);
                    tmp.get$currentPolicy().apply(tmp);
                    tmp.getMetric().addContract(tmp);
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm439 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled442 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff440 = 1;
                    boolean $doBackoff441 = true;
                    boolean $retry436 = true;
                    $label434: for (boolean $commit435 = false; !$commit435; ) {
                        if ($backoffEnabled442) {
                            if ($doBackoff441) {
                                if ($backoff440 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff440);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e437) {
                                            
                                        }
                                    }
                                }
                                if ($backoff440 < 5000) $backoff440 *= 2;
                            }
                            $doBackoff441 = $backoff440 <= 32 || !$doBackoff441;
                        }
                        $commit435 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$$expiry((long) p.expiry());
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            if (tmp.get$$expiry() >=
                                  java.lang.System.currentTimeMillis()) {
                                tmp.set$currentPolicy(p);
                                tmp.get$currentPolicy().apply(tmp);
                                tmp.getMetric().addContract(tmp);
                            }
                        }
                        catch (final fabric.worker.RetryException $e437) {
                            $commit435 = false;
                            continue $label434;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e437) {
                            $commit435 = false;
                            fabric.common.TransactionID $currentTid438 =
                              $tm439.getCurrentTid();
                            if ($e437.tid.isDescendantOf($currentTid438))
                                continue $label434;
                            if ($currentTid438.parent != null) {
                                $retry436 = false;
                                throw $e437;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e437) {
                            $commit435 = false;
                            if ($tm439.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid438 =
                              $tm439.getCurrentTid();
                            if ($e437.tid.isDescendantOf($currentTid438)) {
                                $retry436 = true;
                            }
                            else if ($currentTid438.parent != null) {
                                $retry436 = false;
                                throw $e437;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e437) {
                            $commit435 = false;
                            if ($tm439.checkForStaleObjects())
                                continue $label434;
                            $retry436 = false;
                            throw new fabric.worker.AbortException($e437);
                        }
                        finally {
                            if ($commit435) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e437) {
                                    $commit435 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e437) {
                                    $commit435 = false;
                                    fabric.common.TransactionID $currentTid438 =
                                      $tm439.getCurrentTid();
                                    if ($currentTid438 != null) {
                                        if ($e437.tid.equals($currentTid438) ||
                                              !$e437.tid.isDescendantOf(
                                                           $currentTid438)) {
                                            throw $e437;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit435 && $retry436) {
                                {  }
                                continue $label434;
                            }
                        }
                    }
                }
            }
        }
        
        public void finishActivating_remote(
          fabric.lang.security.Principal caller,
          fabric.metrics.contracts.enforcement.EnforcementPolicy p) {
            finishActivating(p);
        }
        
        public boolean handleUpdates() {
            if (valid()) {
                this.get$lock().checkForRead();
                return refresh(false);
            }
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null)) {
                this.get$currentPolicy().
                  unapply((fabric.metrics.contracts.MetricContract)
                            this.$getProxy());
                this.set$currentPolicy(null);
            }
            return false;
        }
        
        /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
        public boolean refresh(boolean asyncExtension) {
            if (!isActivated()) { return false; }
            long currentTime = java.lang.System.currentTimeMillis();
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null)) {
                long curExpiry = this.get$currentPolicy().expiry();
                if (curExpiry >= currentTime) {
                    boolean result = update(curExpiry);
                    return result;
                }
            }
            if (asyncExtension) return false;
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            acquireReconfigLocks();
            tm.markCoordination();
            fabric.metrics.contracts.enforcement.EnforcementPolicy oldPolicy =
              this.get$currentPolicy();
            fabric.metrics.contracts.enforcement.EnforcementPolicy newPolicy =
              this.get$metric().policy(this.get$rate(), this.get$base(),
                                       $getStore());
            if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null) &&
                  !oldPolicy.equals(newPolicy))
                oldPolicy.unapply((fabric.metrics.contracts.MetricContract)
                                    this.$getProxy());
            newPolicy.activate();
            long newExpiry = newPolicy.expiry();
            boolean result = update(newExpiry);
            if (newExpiry >= currentTime) {
                this.set$currentPolicy(newPolicy);
                this.get$currentPolicy().
                  apply((fabric.metrics.contracts.MetricContract)
                          this.$getProxy());
            } else {
                this.set$currentPolicy(null);
            }
            return result;
        }
        
        /**
   * Check if this implies another {@link MetricContract} being considered.
   *
   * @param otherMetric
   *        the {@link Metric} the other {@link MetricContract} would
   *        assert a bound on
   * @param otherRate
   *        the rate of the bound that would be used by the other
   *        {@link MetricContract}
   * @param otherBase
   *        the base of the bound that would be used by the other
   *        {@link MetricContract}
   * @return true iff this would imply (and therefore) can enforce another
   *       {@link MetricContract} with the given metric and bound.
   */
        public boolean implies(fabric.metrics.Metric otherMetric,
                               double otherRate, double otherBase) {
            if (!getMetric().equals(otherMetric) || this.get$rate() <
                  otherRate || this.get$base() < otherBase)
                return false;
            return valid();
        }
        
        /**
   * Check if this implies the other {@link MetricContract}.
   *
   * @param other
   *        the other {@link MetricContract} this is being compared with
   * @return true iff this would imply (and therefore) can enforce other for
   *       the entire duration of other.
   */
        public boolean implies(fabric.metrics.contracts.MetricContract other) {
            return valid(other.getExpiry()) &&
              implies(other.getMetric(), other.get$rate(), other.get$base());
        }
        
        /**
   * Check if this enforces the <strong>same</strong> bound as another
   * {@link MetricContract} being considered. Attempts to refresh this
   * contract if it's gone stale and would otherwise enforce the bound.
   *
   * @param otherMetric
   *        the {@link Metric} the other {@link MetricContract} would
   *        assert a bound on
   * @param otherRate
   *        the rate of the bound that would be used by the other
   *        {@link MetricContract}
   * @param otherBase
   *        the base of the bound that would be used by the other
   *        {@link MetricContract}
   * @return true iff this enforces another {@link MetricContract} with the
   *       given parameters.
   */
        public boolean enforces(fabric.metrics.Metric otherMetric,
                                double otherRate, double otherBase) {
            if (!getMetric().equals(otherMetric) || otherRate !=
                  this.get$rate() || otherBase != this.get$base())
                return false;
            return valid();
        }
        
        public java.lang.String toString() {
            return ((java.lang.Comparable)
                      fabric.lang.WrappedJavaInlineable.$unwrap(getMetric())).
              toString() + " >= " + this.get$rate() + " * t + " +
            this.get$base() + " until " + getExpiry();
        }
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
            fabric.metrics.Metric m = getMetric();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(m)) instanceof fabric.metrics.SampledMetric) {
                return (fabric.lang.arrays.ObjectArray)
                         new fabric.lang.arrays.ObjectArray._Impl(
                           this.$getStore()).
                         fabric$lang$arrays$ObjectArray$(
                           this.get$$updateLabel(),
                           this.get$$updateLabel().confPolicy(),
                           fabric.metrics.SampledMetric._Proxy.class, 0).
                         $getProxy();
            }
            else if (fabric.lang.Object._Proxy.
                       $getProxy(
                         (java.lang.Object)
                           fabric.lang.WrappedJavaInlineable.
                           $unwrap(
                             m)) instanceof fabric.metrics.DerivedMetric) {
                return ((fabric.metrics.DerivedMetric)
                          fabric.lang.Object._Proxy.$getProxy(m)).
                  getLeafSubjects();
            }
            else {
                throw new java.lang.IllegalStateException(
                        "All metrics should be either sampled or derived!");
            }
        }
        
        /**
   * {@inheritDoc}
   *
   * Stops observing any evidence used by the current enforcement policy (by
   * unapplying the policy).
   */
        public void removeObserver(fabric.metrics.util.Observer obs) {
            fabric.metrics.contracts.MetricContract._Impl.
              static_removeObserver((fabric.metrics.contracts.MetricContract)
                                      this.$getProxy(), obs);
        }
        
        private static void static_removeObserver(
          fabric.metrics.contracts.MetricContract tmp,
          fabric.metrics.util.Observer obs) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.metrics.contracts.Contract._Impl.static_removeObserver(
                                                          tmp, obs);
                if (!tmp.isObserved()) {
                    if (!fabric.lang.Object._Proxy.idEquals(
                                                     tmp.get$currentPolicy(),
                                                     null)) {
                        tmp.get$currentPolicy().unapply(tmp);
                        tmp.set$currentPolicy(null);
                    }
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm448 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled451 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff449 = 1;
                    boolean $doBackoff450 = true;
                    boolean $retry445 = true;
                    $label443: for (boolean $commit444 = false; !$commit444; ) {
                        if ($backoffEnabled451) {
                            if ($doBackoff450) {
                                if ($backoff449 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff449);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e446) {
                                            
                                        }
                                    }
                                }
                                if ($backoff449 < 5000) $backoff449 *= 2;
                            }
                            $doBackoff450 = $backoff449 <= 32 || !$doBackoff450;
                        }
                        $commit444 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved()) {
                                if (!fabric.lang.Object._Proxy.
                                      idEquals(tmp.get$currentPolicy(), null)) {
                                    tmp.get$currentPolicy().unapply(tmp);
                                    tmp.set$currentPolicy(null);
                                }
                            }
                        }
                        catch (final fabric.worker.RetryException $e446) {
                            $commit444 = false;
                            continue $label443;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e446) {
                            $commit444 = false;
                            fabric.common.TransactionID $currentTid447 =
                              $tm448.getCurrentTid();
                            if ($e446.tid.isDescendantOf($currentTid447))
                                continue $label443;
                            if ($currentTid447.parent != null) {
                                $retry445 = false;
                                throw $e446;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e446) {
                            $commit444 = false;
                            if ($tm448.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid447 =
                              $tm448.getCurrentTid();
                            if ($e446.tid.isDescendantOf($currentTid447)) {
                                $retry445 = true;
                            }
                            else if ($currentTid447.parent != null) {
                                $retry445 = false;
                                throw $e446;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e446) {
                            $commit444 = false;
                            if ($tm448.checkForStaleObjects())
                                continue $label443;
                            $retry445 = false;
                            throw new fabric.worker.AbortException($e446);
                        }
                        finally {
                            if ($commit444) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e446) {
                                    $commit444 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e446) {
                                    $commit444 = false;
                                    fabric.common.TransactionID $currentTid447 =
                                      $tm448.getCurrentTid();
                                    if ($currentTid447 != null) {
                                        if ($e446.tid.equals($currentTid447) ||
                                              !$e446.tid.isDescendantOf(
                                                           $currentTid447)) {
                                            throw $e446;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit444 && $retry445) {
                                {  }
                                continue $label443;
                            }
                        }
                    }
                }
            }
        }
        
        public void acquireReconfigLocks() {
            this.get$lock().acquire();
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null))
                this.get$currentPolicy().acquireReconfigLocks();
        }
        
        /**
   * Create a proxy for this contract on the given store.
   */
        public ProxyMetricContract getProxyContract(
          final fabric.worker.Store proxyStore) {
            return fabric.metrics.contracts.MetricContract._Impl.
              static_getProxyContract((fabric.metrics.contracts.MetricContract)
                                        this.$getProxy(), proxyStore);
        }
        
        private static ProxyMetricContract static_getProxyContract(
          fabric.metrics.contracts.MetricContract tmp,
          final fabric.worker.Store proxyStore) {
            ProxyMetricContract proxy = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                proxy =
                  ((ProxyMetricContract)
                     new fabric.metrics.contracts.MetricContract.
                       ProxyMetricContract._Impl(proxyStore).
                     $getProxy()).
                    fabric$metrics$contracts$MetricContract$ProxyMetricContract$(
                      tmp);
            }
            else {
                {
                    fabric.metrics.contracts.MetricContract.ProxyMetricContract
                      proxy$var452 = proxy;
                    fabric.worker.transaction.TransactionManager $tm458 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled461 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff459 = 1;
                    boolean $doBackoff460 = true;
                    boolean $retry455 = true;
                    $label453: for (boolean $commit454 = false; !$commit454; ) {
                        if ($backoffEnabled461) {
                            if ($doBackoff460) {
                                if ($backoff459 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff459);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e456) {
                                            
                                        }
                                    }
                                }
                                if ($backoff459 < 5000) $backoff459 *= 2;
                            }
                            $doBackoff460 = $backoff459 <= 32 || !$doBackoff460;
                        }
                        $commit454 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            proxy =
                              ((ProxyMetricContract)
                                 new fabric.metrics.contracts.MetricContract.
                                   ProxyMetricContract._Impl(proxyStore).
                                 $getProxy()).
                                fabric$metrics$contracts$MetricContract$ProxyMetricContract$(
                                  tmp);
                        }
                        catch (final fabric.worker.RetryException $e456) {
                            $commit454 = false;
                            continue $label453;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e456) {
                            $commit454 = false;
                            fabric.common.TransactionID $currentTid457 =
                              $tm458.getCurrentTid();
                            if ($e456.tid.isDescendantOf($currentTid457))
                                continue $label453;
                            if ($currentTid457.parent != null) {
                                $retry455 = false;
                                throw $e456;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e456) {
                            $commit454 = false;
                            if ($tm458.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid457 =
                              $tm458.getCurrentTid();
                            if ($e456.tid.isDescendantOf($currentTid457)) {
                                $retry455 = true;
                            }
                            else if ($currentTid457.parent != null) {
                                $retry455 = false;
                                throw $e456;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e456) {
                            $commit454 = false;
                            if ($tm458.checkForStaleObjects())
                                continue $label453;
                            $retry455 = false;
                            throw new fabric.worker.AbortException($e456);
                        }
                        finally {
                            if ($commit454) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e456) {
                                    $commit454 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e456) {
                                    $commit454 = false;
                                    fabric.common.TransactionID $currentTid457 =
                                      $tm458.getCurrentTid();
                                    if ($currentTid457 != null) {
                                        if ($e456.tid.equals($currentTid457) ||
                                              !$e456.tid.isDescendantOf(
                                                           $currentTid457)) {
                                            throw $e456;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit454 && $retry455) {
                                { proxy = proxy$var452; }
                                continue $label453;
                            }
                        }
                    }
                }
            }
            if (tmp.isActivated()) proxy.activate();
            return proxy;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.MetricContract._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.metric, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            out.writeDouble(this.rate);
            out.writeDouble(this.base);
            $writeRef($getStore(), this.currentPolicy, refTypes, out,
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
            this.metric = (fabric.metrics.Metric)
                            $readRef(fabric.metrics.Metric._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            this.rate = in.readDouble();
            this.base = in.readDouble();
            this.currentPolicy =
              (fabric.
                metrics.
                contracts.
                enforcement.
                EnforcementPolicy)
                $readRef(
                  fabric.metrics.contracts.enforcement.EnforcementPolicy.
                    _Proxy.class, (fabric.common.RefTypeEnum) refTypes.next(),
                  in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.MetricContract._Impl src =
              (fabric.metrics.contracts.MetricContract._Impl) other;
            this.metric = src.metric;
            this.rate = src.rate;
            this.base = src.base;
            this.currentPolicy = src.currentPolicy;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.MetricContract._Static {
            public _Proxy(fabric.metrics.contracts.MetricContract._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.MetricContract._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  MetricContract.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    MetricContract.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.MetricContract._Static.
                        _Impl.class);
                $instance = (fabric.metrics.contracts.MetricContract._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.MetricContract._Static {
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
                return new fabric.metrics.contracts.MetricContract._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 93, 52, 74, 41, 120,
    26, 69, -103, -91, 85, 69, -118, -113, 64, -7, 59, -121, 106, 27, 42, 126,
    -46, -22, 43, 117, 117, 42, 19, 6, -5, 68, 70 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519057354000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
