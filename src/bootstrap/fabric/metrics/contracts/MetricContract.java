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
                        fabric.worker.transaction.TransactionManager $tm353 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled356 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff354 = 1;
                        boolean $doBackoff355 = true;
                        boolean $retry350 = true;
                        $label348: for (boolean $commit349 = false; !$commit349;
                                        ) {
                            if ($backoffEnabled356) {
                                if ($doBackoff355) {
                                    if ($backoff354 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff354);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e351) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff354 < 5000) $backoff354 *= 2;
                                }
                                $doBackoff355 = $backoff354 <= 32 ||
                                                  !$doBackoff355;
                            }
                            $commit349 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { if (tmp.isActivated()) return; }
                            catch (final fabric.worker.RetryException $e351) {
                                $commit349 = false;
                                continue $label348;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e351) {
                                $commit349 = false;
                                fabric.common.TransactionID $currentTid352 =
                                  $tm353.getCurrentTid();
                                if ($e351.tid.isDescendantOf($currentTid352))
                                    continue $label348;
                                if ($currentTid352.parent != null) {
                                    $retry350 = false;
                                    throw $e351;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e351) {
                                $commit349 = false;
                                if ($tm353.checkForStaleObjects())
                                    continue $label348;
                                $retry350 = false;
                                throw new fabric.worker.AbortException($e351);
                            }
                            finally {
                                if ($commit349) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e351) {
                                        $commit349 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e351) {
                                        $commit349 = false;
                                        fabric.common.TransactionID
                                          $currentTid352 =
                                          $tm353.getCurrentTid();
                                        if ($currentTid352 != null) {
                                            if ($e351.tid.equals(
                                                            $currentTid352) ||
                                                  !$e351.tid.
                                                  isDescendantOf(
                                                    $currentTid352)) {
                                                throw $e351;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit349 && $retry350) {
                                    {  }
                                    continue $label348;
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
                          targetPol$var357 = targetPol;
                        fabric.worker.transaction.TransactionManager $tm363 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled366 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff364 = 1;
                        boolean $doBackoff365 = true;
                        boolean $retry360 = true;
                        $label358: for (boolean $commit359 = false; !$commit359;
                                        ) {
                            if ($backoffEnabled366) {
                                if ($doBackoff365) {
                                    if ($backoff364 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff364);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e361) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff364 < 5000) $backoff364 *= 2;
                                }
                                $doBackoff365 = $backoff364 <= 32 ||
                                                  !$doBackoff365;
                            }
                            $commit359 = true;
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
                            catch (final fabric.worker.RetryException $e361) {
                                $commit359 = false;
                                continue $label358;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e361) {
                                $commit359 = false;
                                fabric.common.TransactionID $currentTid362 =
                                  $tm363.getCurrentTid();
                                if ($e361.tid.isDescendantOf($currentTid362))
                                    continue $label358;
                                if ($currentTid362.parent != null) {
                                    $retry360 = false;
                                    throw $e361;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e361) {
                                $commit359 = false;
                                if ($tm363.checkForStaleObjects())
                                    continue $label358;
                                $retry360 = false;
                                throw new fabric.worker.AbortException($e361);
                            }
                            finally {
                                if ($commit359) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e361) {
                                        $commit359 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e361) {
                                        $commit359 = false;
                                        fabric.common.TransactionID
                                          $currentTid362 =
                                          $tm363.getCurrentTid();
                                        if ($currentTid362 != null) {
                                            if ($e361.tid.equals(
                                                            $currentTid362) ||
                                                  !$e361.tid.
                                                  isDescendantOf(
                                                    $currentTid362)) {
                                                throw $e361;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit359 && $retry360) {
                                    { targetPol = targetPol$var357; }
                                    continue $label358;
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
                          targetPol$var367 = targetPol;
                        fabric.worker.transaction.TransactionManager $tm373 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled376 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff374 = 1;
                        boolean $doBackoff375 = true;
                        boolean $retry370 = true;
                        $label368: for (boolean $commit369 = false; !$commit369;
                                        ) {
                            if ($backoffEnabled376) {
                                if ($doBackoff375) {
                                    if ($backoff374 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff374);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e371) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff374 < 5000) $backoff374 *= 2;
                                }
                                $doBackoff375 = $backoff374 <= 32 ||
                                                  !$doBackoff375;
                            }
                            $commit369 = true;
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
                            catch (final fabric.worker.RetryException $e371) {
                                $commit369 = false;
                                continue $label368;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e371) {
                                $commit369 = false;
                                fabric.common.TransactionID $currentTid372 =
                                  $tm373.getCurrentTid();
                                if ($e371.tid.isDescendantOf($currentTid372))
                                    continue $label368;
                                if ($currentTid372.parent != null) {
                                    $retry370 = false;
                                    throw $e371;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e371) {
                                $commit369 = false;
                                if ($tm373.checkForStaleObjects())
                                    continue $label368;
                                $retry370 = false;
                                throw new fabric.worker.AbortException($e371);
                            }
                            finally {
                                if ($commit369) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e371) {
                                        $commit369 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e371) {
                                        $commit369 = false;
                                        fabric.common.TransactionID
                                          $currentTid372 =
                                          $tm373.getCurrentTid();
                                        if ($currentTid372 != null) {
                                            if ($e371.tid.equals(
                                                            $currentTid372) ||
                                                  !$e371.tid.
                                                  isDescendantOf(
                                                    $currentTid372)) {
                                                throw $e371;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit369 && $retry370) {
                                    { targetPol = targetPol$var367; }
                                    continue $label368;
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
                          proxy$var377 = proxy;
                        fabric.worker.transaction.TransactionManager $tm383 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled386 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff384 = 1;
                        boolean $doBackoff385 = true;
                        boolean $retry380 = true;
                        $label378: for (boolean $commit379 = false; !$commit379;
                                        ) {
                            if ($backoffEnabled386) {
                                if ($doBackoff385) {
                                    if ($backoff384 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff384);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e381) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff384 < 5000) $backoff384 *= 2;
                                }
                                $doBackoff385 = $backoff384 <= 32 ||
                                                  !$doBackoff385;
                            }
                            $commit379 = true;
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
                            catch (final fabric.worker.RetryException $e381) {
                                $commit379 = false;
                                continue $label378;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e381) {
                                $commit379 = false;
                                fabric.common.TransactionID $currentTid382 =
                                  $tm383.getCurrentTid();
                                if ($e381.tid.isDescendantOf($currentTid382))
                                    continue $label378;
                                if ($currentTid382.parent != null) {
                                    $retry380 = false;
                                    throw $e381;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e381) {
                                $commit379 = false;
                                if ($tm383.checkForStaleObjects())
                                    continue $label378;
                                $retry380 = false;
                                throw new fabric.worker.AbortException($e381);
                            }
                            finally {
                                if ($commit379) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e381) {
                                        $commit379 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e381) {
                                        $commit379 = false;
                                        fabric.common.TransactionID
                                          $currentTid382 =
                                          $tm383.getCurrentTid();
                                        if ($currentTid382 != null) {
                                            if ($e381.tid.equals(
                                                            $currentTid382) ||
                                                  !$e381.tid.
                                                  isDescendantOf(
                                                    $currentTid382)) {
                                                throw $e381;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit379 && $retry380) {
                                    { proxy = proxy$var377; }
                                    continue $label378;
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
        
        public static final byte[] $classHash = new byte[] { -18, -21, 35, 89,
        67, -111, -7, 123, -109, 120, 49, 70, -19, 98, -89, 106, -97, -64, -113,
        -23, 80, -26, 7, 84, 117, -12, -103, 65, -67, -65, 12, 86 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1518458353000L;
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
                    fabric.worker.transaction.TransactionManager $tm392 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled395 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff393 = 1;
                    boolean $doBackoff394 = true;
                    boolean $retry389 = true;
                    $label387: for (boolean $commit388 = false; !$commit388; ) {
                        if ($backoffEnabled395) {
                            if ($doBackoff394) {
                                if ($backoff393 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff393);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e390) {
                                            
                                        }
                                    }
                                }
                                if ($backoff393 < 5000) $backoff393 *= 2;
                            }
                            $doBackoff394 = $backoff393 <= 32 || !$doBackoff394;
                        }
                        $commit388 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e390) {
                            $commit388 = false;
                            continue $label387;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e390) {
                            $commit388 = false;
                            fabric.common.TransactionID $currentTid391 =
                              $tm392.getCurrentTid();
                            if ($e390.tid.isDescendantOf($currentTid391))
                                continue $label387;
                            if ($currentTid391.parent != null) {
                                $retry389 = false;
                                throw $e390;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e390) {
                            $commit388 = false;
                            if ($tm392.checkForStaleObjects())
                                continue $label387;
                            $retry389 = false;
                            throw new fabric.worker.AbortException($e390);
                        }
                        finally {
                            if ($commit388) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e390) {
                                    $commit388 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e390) {
                                    $commit388 = false;
                                    fabric.common.TransactionID $currentTid391 =
                                      $tm392.getCurrentTid();
                                    if ($currentTid391 != null) {
                                        if ($e390.tid.equals($currentTid391) ||
                                              !$e390.tid.isDescendantOf(
                                                           $currentTid391)) {
                                            throw $e390;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit388 && $retry389) {
                                {  }
                                continue $label387;
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
                      startPol$var396 = startPol;
                    fabric.worker.transaction.TransactionManager $tm402 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled405 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff403 = 1;
                    boolean $doBackoff404 = true;
                    boolean $retry399 = true;
                    $label397: for (boolean $commit398 = false; !$commit398; ) {
                        if ($backoffEnabled405) {
                            if ($doBackoff404) {
                                if ($backoff403 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff403);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e400) {
                                            
                                        }
                                    }
                                }
                                if ($backoff403 < 5000) $backoff403 *= 2;
                            }
                            $doBackoff404 = $backoff403 <= 32 || !$doBackoff404;
                        }
                        $commit398 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            startPol = tmp.get$metric().policy(tmp.get$rate(),
                                                               tmp.get$base(),
                                                               true,
                                                               tmp.$getStore());
                        }
                        catch (final fabric.worker.RetryException $e400) {
                            $commit398 = false;
                            continue $label397;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e400) {
                            $commit398 = false;
                            fabric.common.TransactionID $currentTid401 =
                              $tm402.getCurrentTid();
                            if ($e400.tid.isDescendantOf($currentTid401))
                                continue $label397;
                            if ($currentTid401.parent != null) {
                                $retry399 = false;
                                throw $e400;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e400) {
                            $commit398 = false;
                            if ($tm402.checkForStaleObjects())
                                continue $label397;
                            $retry399 = false;
                            throw new fabric.worker.AbortException($e400);
                        }
                        finally {
                            if ($commit398) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e400) {
                                    $commit398 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e400) {
                                    $commit398 = false;
                                    fabric.common.TransactionID $currentTid401 =
                                      $tm402.getCurrentTid();
                                    if ($currentTid401 != null) {
                                        if ($e400.tid.equals($currentTid401) ||
                                              !$e400.tid.isDescendantOf(
                                                           $currentTid401)) {
                                            throw $e400;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit398 && $retry399) {
                                { startPol = startPol$var396; }
                                continue $label397;
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
                    fabric.worker.transaction.TransactionManager $tm411 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled414 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff412 = 1;
                    boolean $doBackoff413 = true;
                    boolean $retry408 = true;
                    $label406: for (boolean $commit407 = false; !$commit407; ) {
                        if ($backoffEnabled414) {
                            if ($doBackoff413) {
                                if ($backoff412 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff412);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e409) {
                                            
                                        }
                                    }
                                }
                                if ($backoff412 < 5000) $backoff412 *= 2;
                            }
                            $doBackoff413 = $backoff412 <= 32 || !$doBackoff413;
                        }
                        $commit407 = true;
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
                                    fabric.common.TransactionID $currentTid410 =
                                      $tm411.getCurrentTid();
                                    if ($currentTid410 != null) {
                                        if ($e409.tid.equals($currentTid410) ||
                                              !$e409.tid.isDescendantOf(
                                                           $currentTid410)) {
                                            throw $e409;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit407 && $retry408) {
                                {  }
                                continue $label406;
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
            fabric.common.Logging.METRICS_LOGGER.
              fine(
                "COORDINATING FOR " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.contracts.MetricContract)
                                    this.$getProxy())) +
                  " IN " +
                  fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentTid());
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
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved()) {
                                if (!fabric.lang.Object._Proxy.
                                      idEquals(tmp.get$currentPolicy(), null))
                                    tmp.get$currentPolicy().unapply(tmp);
                            }
                        }
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
        }
        
        public void acquireReconfigLocks() {
            this.get$lock().acquire();
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
                      proxy$var424 = proxy;
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
                            proxy =
                              ((ProxyMetricContract)
                                 new fabric.metrics.contracts.MetricContract.
                                   ProxyMetricContract._Impl(proxyStore).
                                 $getProxy()).
                                fabric$metrics$contracts$MetricContract$ProxyMetricContract$(
                                  tmp);
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
                                { proxy = proxy$var424; }
                                continue $label425;
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
    
    public static final byte[] $classHash = new byte[] { 30, 23, -106, -100,
    -106, 45, -12, 84, 45, -27, -101, -28, 36, -45, -96, -71, 111, -128, -97,
    37, -57, -10, -13, 109, 44, -56, 51, 109, -21, 103, 123, -104 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518458353000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
