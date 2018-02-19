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
                        fabric.worker.transaction.TransactionManager $tm63 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled66 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff64 = 1;
                        boolean $doBackoff65 = true;
                        boolean $retry60 = true;
                        $label58: for (boolean $commit59 = false; !$commit59;
                                       ) {
                            if ($backoffEnabled66) {
                                if ($doBackoff65) {
                                    if ($backoff64 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff64);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e61) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff64 < 5000) $backoff64 *= 2;
                                }
                                $doBackoff65 = $backoff64 <= 32 ||
                                                 !$doBackoff65;
                            }
                            $commit59 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { if (tmp.isActivated()) return; }
                            catch (final fabric.worker.RetryException $e61) {
                                $commit59 = false;
                                continue $label58;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e61) {
                                $commit59 = false;
                                fabric.common.TransactionID $currentTid62 =
                                  $tm63.getCurrentTid();
                                if ($e61.tid.isDescendantOf($currentTid62))
                                    continue $label58;
                                if ($currentTid62.parent != null) {
                                    $retry60 = false;
                                    throw $e61;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e61) {
                                $commit59 = false;
                                if ($tm63.checkForStaleObjects())
                                    continue $label58;
                                $retry60 = false;
                                throw new fabric.worker.AbortException($e61);
                            }
                            finally {
                                if ($commit59) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e61) {
                                        $commit59 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e61) {
                                        $commit59 = false;
                                        fabric.common.TransactionID
                                          $currentTid62 = $tm63.getCurrentTid();
                                        if ($currentTid62 != null) {
                                            if ($e61.tid.equals(
                                                           $currentTid62) ||
                                                  !$e61.tid.isDescendantOf(
                                                              $currentTid62)) {
                                                throw $e61;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit59 && $retry60) {
                                    {  }
                                    continue $label58;
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
                          targetPol$var67 = targetPol;
                        fabric.worker.transaction.TransactionManager $tm73 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled76 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff74 = 1;
                        boolean $doBackoff75 = true;
                        boolean $retry70 = true;
                        $label68: for (boolean $commit69 = false; !$commit69;
                                       ) {
                            if ($backoffEnabled76) {
                                if ($doBackoff75) {
                                    if ($backoff74 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff74);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e71) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff74 < 5000) $backoff74 *= 2;
                                }
                                $doBackoff75 = $backoff74 <= 32 ||
                                                 !$doBackoff75;
                            }
                            $commit69 = true;
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
                            catch (final fabric.worker.RetryException $e71) {
                                $commit69 = false;
                                continue $label68;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e71) {
                                $commit69 = false;
                                fabric.common.TransactionID $currentTid72 =
                                  $tm73.getCurrentTid();
                                if ($e71.tid.isDescendantOf($currentTid72))
                                    continue $label68;
                                if ($currentTid72.parent != null) {
                                    $retry70 = false;
                                    throw $e71;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e71) {
                                $commit69 = false;
                                if ($tm73.checkForStaleObjects())
                                    continue $label68;
                                $retry70 = false;
                                throw new fabric.worker.AbortException($e71);
                            }
                            finally {
                                if ($commit69) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e71) {
                                        $commit69 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e71) {
                                        $commit69 = false;
                                        fabric.common.TransactionID
                                          $currentTid72 = $tm73.getCurrentTid();
                                        if ($currentTid72 != null) {
                                            if ($e71.tid.equals(
                                                           $currentTid72) ||
                                                  !$e71.tid.isDescendantOf(
                                                              $currentTid72)) {
                                                throw $e71;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit69 && $retry70) {
                                    { targetPol = targetPol$var67; }
                                    continue $label68;
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
                          targetPol$var77 = targetPol;
                        fabric.worker.transaction.TransactionManager $tm83 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled86 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff84 = 1;
                        boolean $doBackoff85 = true;
                        boolean $retry80 = true;
                        $label78: for (boolean $commit79 = false; !$commit79;
                                       ) {
                            if ($backoffEnabled86) {
                                if ($doBackoff85) {
                                    if ($backoff84 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff84);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e81) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff84 < 5000) $backoff84 *= 2;
                                }
                                $doBackoff85 = $backoff84 <= 32 ||
                                                 !$doBackoff85;
                            }
                            $commit79 = true;
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
                            catch (final fabric.worker.RetryException $e81) {
                                $commit79 = false;
                                continue $label78;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e81) {
                                $commit79 = false;
                                fabric.common.TransactionID $currentTid82 =
                                  $tm83.getCurrentTid();
                                if ($e81.tid.isDescendantOf($currentTid82))
                                    continue $label78;
                                if ($currentTid82.parent != null) {
                                    $retry80 = false;
                                    throw $e81;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e81) {
                                $commit79 = false;
                                if ($tm83.checkForStaleObjects())
                                    continue $label78;
                                $retry80 = false;
                                throw new fabric.worker.AbortException($e81);
                            }
                            finally {
                                if ($commit79) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e81) {
                                        $commit79 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e81) {
                                        $commit79 = false;
                                        fabric.common.TransactionID
                                          $currentTid82 = $tm83.getCurrentTid();
                                        if ($currentTid82 != null) {
                                            if ($e81.tid.equals(
                                                           $currentTid82) ||
                                                  !$e81.tid.isDescendantOf(
                                                              $currentTid82)) {
                                                throw $e81;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit79 && $retry80) {
                                    { targetPol = targetPol$var77; }
                                    continue $label78;
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
                          proxy$var87 = proxy;
                        fabric.worker.transaction.TransactionManager $tm93 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled96 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff94 = 1;
                        boolean $doBackoff95 = true;
                        boolean $retry90 = true;
                        $label88: for (boolean $commit89 = false; !$commit89;
                                       ) {
                            if ($backoffEnabled96) {
                                if ($doBackoff95) {
                                    if ($backoff94 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff94);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e91) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff94 < 5000) $backoff94 *= 2;
                                }
                                $doBackoff95 = $backoff94 <= 32 ||
                                                 !$doBackoff95;
                            }
                            $commit89 = true;
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
                            catch (final fabric.worker.RetryException $e91) {
                                $commit89 = false;
                                continue $label88;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e91) {
                                $commit89 = false;
                                fabric.common.TransactionID $currentTid92 =
                                  $tm93.getCurrentTid();
                                if ($e91.tid.isDescendantOf($currentTid92))
                                    continue $label88;
                                if ($currentTid92.parent != null) {
                                    $retry90 = false;
                                    throw $e91;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e91) {
                                $commit89 = false;
                                if ($tm93.checkForStaleObjects())
                                    continue $label88;
                                $retry90 = false;
                                throw new fabric.worker.AbortException($e91);
                            }
                            finally {
                                if ($commit89) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e91) {
                                        $commit89 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e91) {
                                        $commit89 = false;
                                        fabric.common.TransactionID
                                          $currentTid92 = $tm93.getCurrentTid();
                                        if ($currentTid92 != null) {
                                            if ($e91.tid.equals(
                                                           $currentTid92) ||
                                                  !$e91.tid.isDescendantOf(
                                                              $currentTid92)) {
                                                throw $e91;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit89 && $retry90) {
                                    { proxy = proxy$var87; }
                                    continue $label88;
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
        
        public static final java.lang.Class[] $paramTypes1 = null;
        
        public void activate$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                activate();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "activate",
                                                  $paramTypes1, null);
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
        
        public static final java.lang.Class[] $paramTypes2 =
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
                                    this, "finishActivating", $paramTypes2,
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
                    fabric.worker.transaction.TransactionManager $tm102 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled105 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff103 = 1;
                    boolean $doBackoff104 = true;
                    boolean $retry99 = true;
                    $label97: for (boolean $commit98 = false; !$commit98; ) {
                        if ($backoffEnabled105) {
                            if ($doBackoff104) {
                                if ($backoff103 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff103);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e100) {
                                            
                                        }
                                    }
                                }
                                if ($backoff103 < 5000) $backoff103 *= 2;
                            }
                            $doBackoff104 = $backoff103 <= 32 || !$doBackoff104;
                        }
                        $commit98 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e100) {
                            $commit98 = false;
                            continue $label97;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e100) {
                            $commit98 = false;
                            fabric.common.TransactionID $currentTid101 =
                              $tm102.getCurrentTid();
                            if ($e100.tid.isDescendantOf($currentTid101))
                                continue $label97;
                            if ($currentTid101.parent != null) {
                                $retry99 = false;
                                throw $e100;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e100) {
                            $commit98 = false;
                            if ($tm102.checkForStaleObjects())
                                continue $label97;
                            $retry99 = false;
                            throw new fabric.worker.AbortException($e100);
                        }
                        finally {
                            if ($commit98) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e100) {
                                    $commit98 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e100) {
                                    $commit98 = false;
                                    fabric.common.TransactionID $currentTid101 =
                                      $tm102.getCurrentTid();
                                    if ($currentTid101 != null) {
                                        if ($e100.tid.equals($currentTid101) ||
                                              !$e100.tid.isDescendantOf(
                                                           $currentTid101)) {
                                            throw $e100;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit98 && $retry99) {
                                {  }
                                continue $label97;
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
                      startPol$var106 = startPol;
                    fabric.worker.transaction.TransactionManager $tm112 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled115 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff113 = 1;
                    boolean $doBackoff114 = true;
                    boolean $retry109 = true;
                    $label107: for (boolean $commit108 = false; !$commit108; ) {
                        if ($backoffEnabled115) {
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
                                if ($backoff113 < 5000) $backoff113 *= 2;
                            }
                            $doBackoff114 = $backoff113 <= 32 || !$doBackoff114;
                        }
                        $commit108 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            startPol = tmp.get$metric().policy(tmp.get$rate(),
                                                               tmp.get$base(),
                                                               true,
                                                               tmp.$getStore());
                        }
                        catch (final fabric.worker.RetryException $e110) {
                            $commit108 = false;
                            continue $label107;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e110) {
                            $commit108 = false;
                            fabric.common.TransactionID $currentTid111 =
                              $tm112.getCurrentTid();
                            if ($e110.tid.isDescendantOf($currentTid111))
                                continue $label107;
                            if ($currentTid111.parent != null) {
                                $retry109 = false;
                                throw $e110;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e110) {
                            $commit108 = false;
                            if ($tm112.checkForStaleObjects())
                                continue $label107;
                            $retry109 = false;
                            throw new fabric.worker.AbortException($e110);
                        }
                        finally {
                            if ($commit108) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e110) {
                                    $commit108 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e110) {
                                    $commit108 = false;
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
                            if (!$commit108 && $retry109) {
                                { startPol = startPol$var106; }
                                continue $label107;
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
                    fabric.worker.transaction.TransactionManager $tm121 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled124 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff122 = 1;
                    boolean $doBackoff123 = true;
                    boolean $retry118 = true;
                    $label116: for (boolean $commit117 = false; !$commit117; ) {
                        if ($backoffEnabled124) {
                            if ($doBackoff123) {
                                if ($backoff122 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff122);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e119) {
                                            
                                        }
                                    }
                                }
                                if ($backoff122 < 5000) $backoff122 *= 2;
                            }
                            $doBackoff123 = $backoff122 <= 32 || !$doBackoff123;
                        }
                        $commit117 = true;
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
                        catch (final fabric.worker.RetryException $e119) {
                            $commit117 = false;
                            continue $label116;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e119) {
                            $commit117 = false;
                            fabric.common.TransactionID $currentTid120 =
                              $tm121.getCurrentTid();
                            if ($e119.tid.isDescendantOf($currentTid120))
                                continue $label116;
                            if ($currentTid120.parent != null) {
                                $retry118 = false;
                                throw $e119;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e119) {
                            $commit117 = false;
                            if ($tm121.checkForStaleObjects())
                                continue $label116;
                            $retry118 = false;
                            throw new fabric.worker.AbortException($e119);
                        }
                        finally {
                            if ($commit117) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e119) {
                                    $commit117 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e119) {
                                    $commit117 = false;
                                    fabric.common.TransactionID $currentTid120 =
                                      $tm121.getCurrentTid();
                                    if ($currentTid120 != null) {
                                        if ($e119.tid.equals($currentTid120) ||
                                              !$e119.tid.isDescendantOf(
                                                           $currentTid120)) {
                                            throw $e119;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit117 && $retry118) {
                                {  }
                                continue $label116;
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
                    fabric.worker.transaction.TransactionManager $tm130 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled133 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff131 = 1;
                    boolean $doBackoff132 = true;
                    boolean $retry127 = true;
                    $label125: for (boolean $commit126 = false; !$commit126; ) {
                        if ($backoffEnabled133) {
                            if ($doBackoff132) {
                                if ($backoff131 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff131);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e128) {
                                            
                                        }
                                    }
                                }
                                if ($backoff131 < 5000) $backoff131 *= 2;
                            }
                            $doBackoff132 = $backoff131 <= 32 || !$doBackoff132;
                        }
                        $commit126 = true;
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
                        catch (final fabric.worker.RetryException $e128) {
                            $commit126 = false;
                            continue $label125;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e128) {
                            $commit126 = false;
                            fabric.common.TransactionID $currentTid129 =
                              $tm130.getCurrentTid();
                            if ($e128.tid.isDescendantOf($currentTid129))
                                continue $label125;
                            if ($currentTid129.parent != null) {
                                $retry127 = false;
                                throw $e128;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e128) {
                            $commit126 = false;
                            if ($tm130.checkForStaleObjects())
                                continue $label125;
                            $retry127 = false;
                            throw new fabric.worker.AbortException($e128);
                        }
                        finally {
                            if ($commit126) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e128) {
                                    $commit126 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e128) {
                                    $commit126 = false;
                                    fabric.common.TransactionID $currentTid129 =
                                      $tm130.getCurrentTid();
                                    if ($currentTid129 != null) {
                                        if ($e128.tid.equals($currentTid129) ||
                                              !$e128.tid.isDescendantOf(
                                                           $currentTid129)) {
                                            throw $e128;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit126 && $retry127) {
                                {  }
                                continue $label125;
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
                      proxy$var134 = proxy;
                    fabric.worker.transaction.TransactionManager $tm140 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled143 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff141 = 1;
                    boolean $doBackoff142 = true;
                    boolean $retry137 = true;
                    $label135: for (boolean $commit136 = false; !$commit136; ) {
                        if ($backoffEnabled143) {
                            if ($doBackoff142) {
                                if ($backoff141 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff141);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e138) {
                                            
                                        }
                                    }
                                }
                                if ($backoff141 < 5000) $backoff141 *= 2;
                            }
                            $doBackoff142 = $backoff141 <= 32 || !$doBackoff142;
                        }
                        $commit136 = true;
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
                        catch (final fabric.worker.RetryException $e138) {
                            $commit136 = false;
                            continue $label135;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e138) {
                            $commit136 = false;
                            fabric.common.TransactionID $currentTid139 =
                              $tm140.getCurrentTid();
                            if ($e138.tid.isDescendantOf($currentTid139))
                                continue $label135;
                            if ($currentTid139.parent != null) {
                                $retry137 = false;
                                throw $e138;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e138) {
                            $commit136 = false;
                            if ($tm140.checkForStaleObjects())
                                continue $label135;
                            $retry137 = false;
                            throw new fabric.worker.AbortException($e138);
                        }
                        finally {
                            if ($commit136) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e138) {
                                    $commit136 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e138) {
                                    $commit136 = false;
                                    fabric.common.TransactionID $currentTid139 =
                                      $tm140.getCurrentTid();
                                    if ($currentTid139 != null) {
                                        if ($e138.tid.equals($currentTid139) ||
                                              !$e138.tid.isDescendantOf(
                                                           $currentTid139)) {
                                            throw $e138;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit136 && $retry137) {
                                { proxy = proxy$var134; }
                                continue $label135;
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
