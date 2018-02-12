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
                        fabric.worker.transaction.TransactionManager $tm5 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled8 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff6 = 1;
                        boolean $doBackoff7 = true;
                        boolean $retry2 = true;
                        $label0: for (boolean $commit1 = false; !$commit1; ) {
                            if ($backoffEnabled8) {
                                if ($doBackoff7) {
                                    if ($backoff6 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff6);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e3) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff6 < 5000) $backoff6 *= 2;
                                }
                                $doBackoff7 = $backoff6 <= 32 || !$doBackoff7;
                            }
                            $commit1 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { if (tmp.isActivated()) return; }
                            catch (final fabric.worker.RetryException $e3) {
                                $commit1 = false;
                                continue $label0;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e3) {
                                $commit1 = false;
                                fabric.common.TransactionID $currentTid4 =
                                  $tm5.getCurrentTid();
                                if ($e3.tid.isDescendantOf($currentTid4))
                                    continue $label0;
                                if ($currentTid4.parent != null) {
                                    $retry2 = false;
                                    throw $e3;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e3) {
                                $commit1 = false;
                                if ($tm5.checkForStaleObjects())
                                    continue $label0;
                                $retry2 = false;
                                throw new fabric.worker.AbortException($e3);
                            }
                            finally {
                                if ($commit1) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e3) {
                                        $commit1 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e3) {
                                        $commit1 = false;
                                        fabric.common.TransactionID
                                          $currentTid4 = $tm5.getCurrentTid();
                                        if ($currentTid4 != null) {
                                            if ($e3.tid.equals($currentTid4) ||
                                                  !$e3.tid.isDescendantOf(
                                                             $currentTid4)) {
                                                throw $e3;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit1 && $retry2) {
                                    {  }
                                    continue $label0;
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
                          targetPol$var9 = targetPol;
                        fabric.worker.transaction.TransactionManager $tm15 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled18 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff16 = 1;
                        boolean $doBackoff17 = true;
                        boolean $retry12 = true;
                        $label10: for (boolean $commit11 = false; !$commit11;
                                       ) {
                            if ($backoffEnabled18) {
                                if ($doBackoff17) {
                                    if ($backoff16 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff16);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e13) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff16 < 5000) $backoff16 *= 2;
                                }
                                $doBackoff17 = $backoff16 <= 32 ||
                                                 !$doBackoff17;
                            }
                            $commit11 = true;
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
                            catch (final fabric.worker.RetryException $e13) {
                                $commit11 = false;
                                continue $label10;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e13) {
                                $commit11 = false;
                                fabric.common.TransactionID $currentTid14 =
                                  $tm15.getCurrentTid();
                                if ($e13.tid.isDescendantOf($currentTid14))
                                    continue $label10;
                                if ($currentTid14.parent != null) {
                                    $retry12 = false;
                                    throw $e13;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e13) {
                                $commit11 = false;
                                if ($tm15.checkForStaleObjects())
                                    continue $label10;
                                $retry12 = false;
                                throw new fabric.worker.AbortException($e13);
                            }
                            finally {
                                if ($commit11) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e13) {
                                        $commit11 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e13) {
                                        $commit11 = false;
                                        fabric.common.TransactionID
                                          $currentTid14 = $tm15.getCurrentTid();
                                        if ($currentTid14 != null) {
                                            if ($e13.tid.equals(
                                                           $currentTid14) ||
                                                  !$e13.tid.isDescendantOf(
                                                              $currentTid14)) {
                                                throw $e13;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit11 && $retry12) {
                                    { targetPol = targetPol$var9; }
                                    continue $label10;
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
                          targetPol$var19 = targetPol;
                        fabric.worker.transaction.TransactionManager $tm25 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled28 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff26 = 1;
                        boolean $doBackoff27 = true;
                        boolean $retry22 = true;
                        $label20: for (boolean $commit21 = false; !$commit21;
                                       ) {
                            if ($backoffEnabled28) {
                                if ($doBackoff27) {
                                    if ($backoff26 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff26);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e23) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff26 < 5000) $backoff26 *= 2;
                                }
                                $doBackoff27 = $backoff26 <= 32 ||
                                                 !$doBackoff27;
                            }
                            $commit21 = true;
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
                            catch (final fabric.worker.RetryException $e23) {
                                $commit21 = false;
                                continue $label20;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e23) {
                                $commit21 = false;
                                fabric.common.TransactionID $currentTid24 =
                                  $tm25.getCurrentTid();
                                if ($e23.tid.isDescendantOf($currentTid24))
                                    continue $label20;
                                if ($currentTid24.parent != null) {
                                    $retry22 = false;
                                    throw $e23;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e23) {
                                $commit21 = false;
                                if ($tm25.checkForStaleObjects())
                                    continue $label20;
                                $retry22 = false;
                                throw new fabric.worker.AbortException($e23);
                            }
                            finally {
                                if ($commit21) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e23) {
                                        $commit21 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e23) {
                                        $commit21 = false;
                                        fabric.common.TransactionID
                                          $currentTid24 = $tm25.getCurrentTid();
                                        if ($currentTid24 != null) {
                                            if ($e23.tid.equals(
                                                           $currentTid24) ||
                                                  !$e23.tid.isDescendantOf(
                                                              $currentTid24)) {
                                                throw $e23;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit21 && $retry22) {
                                    { targetPol = targetPol$var19; }
                                    continue $label20;
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
                          proxy$var29 = proxy;
                        fabric.worker.transaction.TransactionManager $tm35 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled38 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff36 = 1;
                        boolean $doBackoff37 = true;
                        boolean $retry32 = true;
                        $label30: for (boolean $commit31 = false; !$commit31;
                                       ) {
                            if ($backoffEnabled38) {
                                if ($doBackoff37) {
                                    if ($backoff36 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff36);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e33) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff36 < 5000) $backoff36 *= 2;
                                }
                                $doBackoff37 = $backoff36 <= 32 ||
                                                 !$doBackoff37;
                            }
                            $commit31 = true;
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
                            catch (final fabric.worker.RetryException $e33) {
                                $commit31 = false;
                                continue $label30;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e33) {
                                $commit31 = false;
                                fabric.common.TransactionID $currentTid34 =
                                  $tm35.getCurrentTid();
                                if ($e33.tid.isDescendantOf($currentTid34))
                                    continue $label30;
                                if ($currentTid34.parent != null) {
                                    $retry32 = false;
                                    throw $e33;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e33) {
                                $commit31 = false;
                                if ($tm35.checkForStaleObjects())
                                    continue $label30;
                                $retry32 = false;
                                throw new fabric.worker.AbortException($e33);
                            }
                            finally {
                                if ($commit31) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e33) {
                                        $commit31 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e33) {
                                        $commit31 = false;
                                        fabric.common.TransactionID
                                          $currentTid34 = $tm35.getCurrentTid();
                                        if ($currentTid34 != null) {
                                            if ($e33.tid.equals(
                                                           $currentTid34) ||
                                                  !$e33.tid.isDescendantOf(
                                                              $currentTid34)) {
                                                throw $e33;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit31 && $retry32) {
                                    { proxy = proxy$var29; }
                                    continue $label30;
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
        
        public static final byte[] $classHash = new byte[] { 8, -18, 117, -66,
        -84, 42, -118, -114, -79, 34, 119, 53, 28, 104, 126, -124, -22, -104,
        32, 5, 85, -107, -91, 93, -52, -10, -70, 73, -12, -127, 26, -77 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1518460890000L;
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
        
        public static final java.lang.Class[] $paramTypes0 = null;
        
        public void activate$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                activate();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "activate",
                                                  $paramTypes0, null);
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
        
        public static final java.lang.Class[] $paramTypes1 =
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
                                    this, "finishActivating", $paramTypes1,
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
                    fabric.worker.transaction.TransactionManager $tm44 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled47 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff45 = 1;
                    boolean $doBackoff46 = true;
                    boolean $retry41 = true;
                    $label39: for (boolean $commit40 = false; !$commit40; ) {
                        if ($backoffEnabled47) {
                            if ($doBackoff46) {
                                if ($backoff45 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff45);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e42) {
                                            
                                        }
                                    }
                                }
                                if ($backoff45 < 5000) $backoff45 *= 2;
                            }
                            $doBackoff46 = $backoff45 <= 32 || !$doBackoff46;
                        }
                        $commit40 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e42) {
                            $commit40 = false;
                            continue $label39;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e42) {
                            $commit40 = false;
                            fabric.common.TransactionID $currentTid43 =
                              $tm44.getCurrentTid();
                            if ($e42.tid.isDescendantOf($currentTid43))
                                continue $label39;
                            if ($currentTid43.parent != null) {
                                $retry41 = false;
                                throw $e42;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e42) {
                            $commit40 = false;
                            if ($tm44.checkForStaleObjects()) continue $label39;
                            $retry41 = false;
                            throw new fabric.worker.AbortException($e42);
                        }
                        finally {
                            if ($commit40) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e42) {
                                    $commit40 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e42) {
                                    $commit40 = false;
                                    fabric.common.TransactionID $currentTid43 =
                                      $tm44.getCurrentTid();
                                    if ($currentTid43 != null) {
                                        if ($e42.tid.equals($currentTid43) ||
                                              !$e42.tid.isDescendantOf(
                                                          $currentTid43)) {
                                            throw $e42;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit40 && $retry41) {
                                {  }
                                continue $label39;
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
                      startPol$var48 = startPol;
                    fabric.worker.transaction.TransactionManager $tm54 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled57 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff55 = 1;
                    boolean $doBackoff56 = true;
                    boolean $retry51 = true;
                    $label49: for (boolean $commit50 = false; !$commit50; ) {
                        if ($backoffEnabled57) {
                            if ($doBackoff56) {
                                if ($backoff55 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff55);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e52) {
                                            
                                        }
                                    }
                                }
                                if ($backoff55 < 5000) $backoff55 *= 2;
                            }
                            $doBackoff56 = $backoff55 <= 32 || !$doBackoff56;
                        }
                        $commit50 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            startPol = tmp.get$metric().policy(tmp.get$rate(),
                                                               tmp.get$base(),
                                                               true,
                                                               tmp.$getStore());
                        }
                        catch (final fabric.worker.RetryException $e52) {
                            $commit50 = false;
                            continue $label49;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e52) {
                            $commit50 = false;
                            fabric.common.TransactionID $currentTid53 =
                              $tm54.getCurrentTid();
                            if ($e52.tid.isDescendantOf($currentTid53))
                                continue $label49;
                            if ($currentTid53.parent != null) {
                                $retry51 = false;
                                throw $e52;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e52) {
                            $commit50 = false;
                            if ($tm54.checkForStaleObjects()) continue $label49;
                            $retry51 = false;
                            throw new fabric.worker.AbortException($e52);
                        }
                        finally {
                            if ($commit50) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e52) {
                                    $commit50 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e52) {
                                    $commit50 = false;
                                    fabric.common.TransactionID $currentTid53 =
                                      $tm54.getCurrentTid();
                                    if ($currentTid53 != null) {
                                        if ($e52.tid.equals($currentTid53) ||
                                              !$e52.tid.isDescendantOf(
                                                          $currentTid53)) {
                                            throw $e52;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit50 && $retry51) {
                                { startPol = startPol$var48; }
                                continue $label49;
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
                    fabric.worker.transaction.TransactionManager $tm63 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled66 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff64 = 1;
                    boolean $doBackoff65 = true;
                    boolean $retry60 = true;
                    $label58: for (boolean $commit59 = false; !$commit59; ) {
                        if ($backoffEnabled66) {
                            if ($doBackoff65) {
                                if ($backoff64 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff64);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e61) {
                                            
                                        }
                                    }
                                }
                                if ($backoff64 < 5000) $backoff64 *= 2;
                            }
                            $doBackoff65 = $backoff64 <= 32 || !$doBackoff65;
                        }
                        $commit59 = true;
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
                            if ($tm63.checkForStaleObjects()) continue $label58;
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
                                    fabric.common.TransactionID $currentTid62 =
                                      $tm63.getCurrentTid();
                                    if ($currentTid62 != null) {
                                        if ($e61.tid.equals($currentTid62) ||
                                              !$e61.tid.isDescendantOf(
                                                          $currentTid62)) {
                                            throw $e61;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit59 && $retry60) {
                                {  }
                                continue $label58;
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
                    fabric.worker.transaction.TransactionManager $tm72 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled75 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff73 = 1;
                    boolean $doBackoff74 = true;
                    boolean $retry69 = true;
                    $label67: for (boolean $commit68 = false; !$commit68; ) {
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
                        $commit68 = true;
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
                        catch (final fabric.worker.RetryException $e70) {
                            $commit68 = false;
                            continue $label67;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e70) {
                            $commit68 = false;
                            fabric.common.TransactionID $currentTid71 =
                              $tm72.getCurrentTid();
                            if ($e70.tid.isDescendantOf($currentTid71))
                                continue $label67;
                            if ($currentTid71.parent != null) {
                                $retry69 = false;
                                throw $e70;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e70) {
                            $commit68 = false;
                            if ($tm72.checkForStaleObjects()) continue $label67;
                            $retry69 = false;
                            throw new fabric.worker.AbortException($e70);
                        }
                        finally {
                            if ($commit68) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e70) {
                                    $commit68 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e70) {
                                    $commit68 = false;
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
                            if (!$commit68 && $retry69) {
                                {  }
                                continue $label67;
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
                      proxy$var76 = proxy;
                    fabric.worker.transaction.TransactionManager $tm82 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled85 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff83 = 1;
                    boolean $doBackoff84 = true;
                    boolean $retry79 = true;
                    $label77: for (boolean $commit78 = false; !$commit78; ) {
                        if ($backoffEnabled85) {
                            if ($doBackoff84) {
                                if ($backoff83 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff83);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e80) {
                                            
                                        }
                                    }
                                }
                                if ($backoff83 < 5000) $backoff83 *= 2;
                            }
                            $doBackoff84 = $backoff83 <= 32 || !$doBackoff84;
                        }
                        $commit78 = true;
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
                        catch (final fabric.worker.RetryException $e80) {
                            $commit78 = false;
                            continue $label77;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e80) {
                            $commit78 = false;
                            fabric.common.TransactionID $currentTid81 =
                              $tm82.getCurrentTid();
                            if ($e80.tid.isDescendantOf($currentTid81))
                                continue $label77;
                            if ($currentTid81.parent != null) {
                                $retry79 = false;
                                throw $e80;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e80) {
                            $commit78 = false;
                            if ($tm82.checkForStaleObjects()) continue $label77;
                            $retry79 = false;
                            throw new fabric.worker.AbortException($e80);
                        }
                        finally {
                            if ($commit78) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e80) {
                                    $commit78 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e80) {
                                    $commit78 = false;
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
                            if (!$commit78 && $retry79) {
                                { proxy = proxy$var76; }
                                continue $label77;
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
    
    public static final byte[] $classHash = new byte[] { -37, 7, -121, 62, -19,
    -58, -4, -42, 117, 59, -19, 57, -120, -6, -80, -10, -109, 108, 11, 14, 69,
    65, 44, -73, 110, 49, -9, -36, 28, -27, 62, 35 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518460890000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
