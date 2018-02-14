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
    
    public void
      finishActivating_remote(fabric.lang.security.Principal caller,
                              fabric.metrics.contracts.enforcement.EnforcementPolicy p);
    
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
                    final fabric.worker.Store s = tmp.$getStore();
                    tmp.set$currentPolicy(targetPol);
                    tmp.set$$expiry((long) tmp.get$currentPolicy().expiry());
                    fabric.metrics.contracts.Contract._Impl.static_activate(
                                                              tmp);
                    if (tmp.get$$expiry() >=
                          java.lang.System.currentTimeMillis()) {
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
                                final fabric.worker.Store s = tmp.$getStore();
                                tmp.set$currentPolicy(targetPol);
                                tmp.set$$expiry(
                                      (long) tmp.get$currentPolicy().expiry());
                                fabric.metrics.contracts.Contract._Impl.
                                  static_activate(tmp);
                                if (tmp.get$$expiry() >=
                                      java.lang.System.currentTimeMillis()) {
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
        
        public static final byte[] $classHash = new byte[] { -127, 5, 35, -2,
        92, 119, 122, 13, -39, -43, 13, -31, 91, -109, 27, 116, -77, -46, 62,
        29, 90, -78, -48, -79, -81, 57, -58, -25, -74, 71, 95, -3 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1518623604000L;
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
                tmp.set$currentPolicy(p);
                tmp.set$$expiry((long) tmp.get$currentPolicy().expiry());
                fabric.metrics.contracts.Contract._Impl.static_activate(tmp);
                if (tmp.get$$expiry() >= java.lang.System.currentTimeMillis()) {
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
                            tmp.set$currentPolicy(p);
                            tmp.set$$expiry((long)
                                              tmp.get$currentPolicy().expiry());
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            if (tmp.get$$expiry() >=
                                  java.lang.System.currentTimeMillis()) {
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
            if (!this.get$lock().held()) {
                tm.addContractToAcquire(
                     (fabric.metrics.contracts.MetricContract)
                       this.$getProxy());
                fabric.common.TransactionID current = tm.getCurrentTid();
                if (!fabric.lang.Object._Proxy.idEquals(current, null)) {
                    while (!fabric.lang.Object._Proxy.idEquals(current.parent,
                                                               null))
                        current = current.parent;
                    throw new fabric.worker.TransactionRestartingException(
                            new fabric.common.TransactionID(current.topTid));
                }
            }
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
            this.set$currentPolicy(newPolicy);
            this.get$currentPolicy().activate();
            long newExpiry = this.get$currentPolicy().expiry();
            boolean result = update(newExpiry);
            if (newExpiry >= currentTime)
                this.get$currentPolicy().
                  apply((fabric.metrics.contracts.MetricContract)
                          this.$getProxy());
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
                                                     null))
                        tmp.get$currentPolicy().unapply(tmp);
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
                                      idEquals(tmp.get$currentPolicy(), null))
                                    tmp.get$currentPolicy().unapply(tmp);
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
    
    public static final byte[] $classHash = new byte[] { 84, -99, -56, 63, 80,
    -47, 31, 114, -20, 102, -77, -81, 19, 15, 50, -57, 104, -71, -112, -122, 31,
    -120, 78, 22, -118, -112, -3, 67, -105, 122, -50, -34 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518623604000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
