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
        
        public static final byte[] $classHash = new byte[] { 124, -32, -62, 15,
        32, 123, -105, 33, -6, -97, -120, 56, 70, -2, 79, 46, 110, -18, 57,
        -126, 42, -47, 104, 56, 5, 75, 82, -109, -44, 113, 125, -85 };
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
    
    public static final byte[] $classHash = new byte[] { 90, 67, 28, -17, 40,
    109, -75, 0, -37, -103, -12, -62, -37, -50, 10, -9, 37, 40, 69, 115, -7,
    -38, 127, -38, -100, 27, -56, -127, -11, 29, 76, 57 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519057354000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
