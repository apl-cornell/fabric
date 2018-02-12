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
        
        public static final byte[] $classHash = new byte[] { 74, -2, -109, -2,
        63, -127, -17, -82, -114, -117, 58, 26, -94, -112, -30, 82, -66, 126,
        -73, -64, -119, -114, 21, 61, 100, -5, -16, 109, 21, 98, 116, -77 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1518459162000L;
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
            fabric.common.Logging.METRICS_LOGGER.
              fine(
                "COORDINATING FOR " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.contracts.MetricContract)
                                    this.$getProxy())) +
                  " IN " +
                  tm.getCurrentTid());
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
    
    public static final byte[] $classHash = new byte[] { -115, 2, 72, 52, -97,
    99, -57, 110, 73, -12, 43, -46, 47, 120, 70, 31, -42, -5, -90, 58, -100,
    -51, 54, 11, 107, -72, 20, -19, 96, 57, -80, 97 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518459162000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
