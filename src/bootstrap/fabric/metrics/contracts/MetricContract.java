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
                        fabric.worker.transaction.TransactionManager $tm41 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled44 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff42 = 1;
                        boolean $doBackoff43 = true;
                        boolean $retry38 = true;
                        $label36: for (boolean $commit37 = false; !$commit37;
                                       ) {
                            if ($backoffEnabled44) {
                                if ($doBackoff43) {
                                    if ($backoff42 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff42);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e39) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff42 < 5000) $backoff42 *= 2;
                                }
                                $doBackoff43 = $backoff42 <= 32 ||
                                                 !$doBackoff43;
                            }
                            $commit37 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { if (tmp.isActivated()) return; }
                            catch (final fabric.worker.RetryException $e39) {
                                $commit37 = false;
                                continue $label36;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e39) {
                                $commit37 = false;
                                fabric.common.TransactionID $currentTid40 =
                                  $tm41.getCurrentTid();
                                if ($e39.tid.isDescendantOf($currentTid40))
                                    continue $label36;
                                if ($currentTid40.parent != null) {
                                    $retry38 = false;
                                    throw $e39;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e39) {
                                $commit37 = false;
                                if ($tm41.checkForStaleObjects())
                                    continue $label36;
                                $retry38 = false;
                                throw new fabric.worker.AbortException($e39);
                            }
                            finally {
                                if ($commit37) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e39) {
                                        $commit37 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e39) {
                                        $commit37 = false;
                                        fabric.common.TransactionID
                                          $currentTid40 = $tm41.getCurrentTid();
                                        if ($currentTid40 != null) {
                                            if ($e39.tid.equals(
                                                           $currentTid40) ||
                                                  !$e39.tid.isDescendantOf(
                                                              $currentTid40)) {
                                                throw $e39;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit37 && $retry38) {
                                    {  }
                                    continue $label36;
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
                          targetPol$var45 = targetPol;
                        fabric.worker.transaction.TransactionManager $tm51 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled54 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff52 = 1;
                        boolean $doBackoff53 = true;
                        boolean $retry48 = true;
                        $label46: for (boolean $commit47 = false; !$commit47;
                                       ) {
                            if ($backoffEnabled54) {
                                if ($doBackoff53) {
                                    if ($backoff52 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff52);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e49) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff52 < 5000) $backoff52 *= 2;
                                }
                                $doBackoff53 = $backoff52 <= 32 ||
                                                 !$doBackoff53;
                            }
                            $commit47 = true;
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
                            catch (final fabric.worker.RetryException $e49) {
                                $commit47 = false;
                                continue $label46;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e49) {
                                $commit47 = false;
                                fabric.common.TransactionID $currentTid50 =
                                  $tm51.getCurrentTid();
                                if ($e49.tid.isDescendantOf($currentTid50))
                                    continue $label46;
                                if ($currentTid50.parent != null) {
                                    $retry48 = false;
                                    throw $e49;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e49) {
                                $commit47 = false;
                                if ($tm51.checkForStaleObjects())
                                    continue $label46;
                                $retry48 = false;
                                throw new fabric.worker.AbortException($e49);
                            }
                            finally {
                                if ($commit47) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e49) {
                                        $commit47 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e49) {
                                        $commit47 = false;
                                        fabric.common.TransactionID
                                          $currentTid50 = $tm51.getCurrentTid();
                                        if ($currentTid50 != null) {
                                            if ($e49.tid.equals(
                                                           $currentTid50) ||
                                                  !$e49.tid.isDescendantOf(
                                                              $currentTid50)) {
                                                throw $e49;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit47 && $retry48) {
                                    { targetPol = targetPol$var45; }
                                    continue $label46;
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
                          targetPol$var55 = targetPol;
                        fabric.worker.transaction.TransactionManager $tm61 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled64 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff62 = 1;
                        boolean $doBackoff63 = true;
                        boolean $retry58 = true;
                        $label56: for (boolean $commit57 = false; !$commit57;
                                       ) {
                            if ($backoffEnabled64) {
                                if ($doBackoff63) {
                                    if ($backoff62 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff62);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e59) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff62 < 5000) $backoff62 *= 2;
                                }
                                $doBackoff63 = $backoff62 <= 32 ||
                                                 !$doBackoff63;
                            }
                            $commit57 = true;
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
                            catch (final fabric.worker.RetryException $e59) {
                                $commit57 = false;
                                continue $label56;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e59) {
                                $commit57 = false;
                                fabric.common.TransactionID $currentTid60 =
                                  $tm61.getCurrentTid();
                                if ($e59.tid.isDescendantOf($currentTid60))
                                    continue $label56;
                                if ($currentTid60.parent != null) {
                                    $retry58 = false;
                                    throw $e59;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e59) {
                                $commit57 = false;
                                if ($tm61.checkForStaleObjects())
                                    continue $label56;
                                $retry58 = false;
                                throw new fabric.worker.AbortException($e59);
                            }
                            finally {
                                if ($commit57) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e59) {
                                        $commit57 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e59) {
                                        $commit57 = false;
                                        fabric.common.TransactionID
                                          $currentTid60 = $tm61.getCurrentTid();
                                        if ($currentTid60 != null) {
                                            if ($e59.tid.equals(
                                                           $currentTid60) ||
                                                  !$e59.tid.isDescendantOf(
                                                              $currentTid60)) {
                                                throw $e59;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit57 && $retry58) {
                                    { targetPol = targetPol$var55; }
                                    continue $label56;
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
                          proxy$var65 = proxy;
                        fabric.worker.transaction.TransactionManager $tm71 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled74 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff72 = 1;
                        boolean $doBackoff73 = true;
                        boolean $retry68 = true;
                        $label66: for (boolean $commit67 = false; !$commit67;
                                       ) {
                            if ($backoffEnabled74) {
                                if ($doBackoff73) {
                                    if ($backoff72 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff72);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e69) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff72 < 5000) $backoff72 *= 2;
                                }
                                $doBackoff73 = $backoff72 <= 32 ||
                                                 !$doBackoff73;
                            }
                            $commit67 = true;
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
                            catch (final fabric.worker.RetryException $e69) {
                                $commit67 = false;
                                continue $label66;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e69) {
                                $commit67 = false;
                                fabric.common.TransactionID $currentTid70 =
                                  $tm71.getCurrentTid();
                                if ($e69.tid.isDescendantOf($currentTid70))
                                    continue $label66;
                                if ($currentTid70.parent != null) {
                                    $retry68 = false;
                                    throw $e69;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e69) {
                                $commit67 = false;
                                if ($tm71.checkForStaleObjects())
                                    continue $label66;
                                $retry68 = false;
                                throw new fabric.worker.AbortException($e69);
                            }
                            finally {
                                if ($commit67) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e69) {
                                        $commit67 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e69) {
                                        $commit67 = false;
                                        fabric.common.TransactionID
                                          $currentTid70 = $tm71.getCurrentTid();
                                        if ($currentTid70 != null) {
                                            if ($e69.tid.equals(
                                                           $currentTid70) ||
                                                  !$e69.tid.isDescendantOf(
                                                              $currentTid70)) {
                                                throw $e69;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit67 && $retry68) {
                                    { proxy = proxy$var65; }
                                    continue $label66;
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
        
        public static final byte[] $classHash = new byte[] { -3, 36, 80, -91,
        -83, 99, 44, -56, 116, -90, -90, -73, 102, -66, 97, -62, 80, 77, -94, 1,
        106, -98, -107, 39, -82, 78, 36, 49, 8, 90, -13, 123 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1518538099000L;
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
                    fabric.worker.transaction.TransactionManager $tm80 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled83 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff81 = 1;
                    boolean $doBackoff82 = true;
                    boolean $retry77 = true;
                    $label75: for (boolean $commit76 = false; !$commit76; ) {
                        if ($backoffEnabled83) {
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
                                if ($backoff81 < 5000) $backoff81 *= 2;
                            }
                            $doBackoff82 = $backoff81 <= 32 || !$doBackoff82;
                        }
                        $commit76 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e78) {
                            $commit76 = false;
                            continue $label75;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e78) {
                            $commit76 = false;
                            fabric.common.TransactionID $currentTid79 =
                              $tm80.getCurrentTid();
                            if ($e78.tid.isDescendantOf($currentTid79))
                                continue $label75;
                            if ($currentTid79.parent != null) {
                                $retry77 = false;
                                throw $e78;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e78) {
                            $commit76 = false;
                            if ($tm80.checkForStaleObjects()) continue $label75;
                            $retry77 = false;
                            throw new fabric.worker.AbortException($e78);
                        }
                        finally {
                            if ($commit76) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e78) {
                                    $commit76 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e78) {
                                    $commit76 = false;
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
                            if (!$commit76 && $retry77) {
                                {  }
                                continue $label75;
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
                      startPol$var84 = startPol;
                    fabric.worker.transaction.TransactionManager $tm90 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled93 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff91 = 1;
                    boolean $doBackoff92 = true;
                    boolean $retry87 = true;
                    $label85: for (boolean $commit86 = false; !$commit86; ) {
                        if ($backoffEnabled93) {
                            if ($doBackoff92) {
                                if ($backoff91 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff91);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e88) {
                                            
                                        }
                                    }
                                }
                                if ($backoff91 < 5000) $backoff91 *= 2;
                            }
                            $doBackoff92 = $backoff91 <= 32 || !$doBackoff92;
                        }
                        $commit86 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            startPol = tmp.get$metric().policy(tmp.get$rate(),
                                                               tmp.get$base(),
                                                               true,
                                                               tmp.$getStore());
                        }
                        catch (final fabric.worker.RetryException $e88) {
                            $commit86 = false;
                            continue $label85;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e88) {
                            $commit86 = false;
                            fabric.common.TransactionID $currentTid89 =
                              $tm90.getCurrentTid();
                            if ($e88.tid.isDescendantOf($currentTid89))
                                continue $label85;
                            if ($currentTid89.parent != null) {
                                $retry87 = false;
                                throw $e88;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e88) {
                            $commit86 = false;
                            if ($tm90.checkForStaleObjects()) continue $label85;
                            $retry87 = false;
                            throw new fabric.worker.AbortException($e88);
                        }
                        finally {
                            if ($commit86) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e88) {
                                    $commit86 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e88) {
                                    $commit86 = false;
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
                            if (!$commit86 && $retry87) {
                                { startPol = startPol$var84; }
                                continue $label85;
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
                    fabric.worker.transaction.TransactionManager $tm99 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled102 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff100 = 1;
                    boolean $doBackoff101 = true;
                    boolean $retry96 = true;
                    $label94: for (boolean $commit95 = false; !$commit95; ) {
                        if ($backoffEnabled102) {
                            if ($doBackoff101) {
                                if ($backoff100 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff100);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e97) {
                                            
                                        }
                                    }
                                }
                                if ($backoff100 < 5000) $backoff100 *= 2;
                            }
                            $doBackoff101 = $backoff100 <= 32 || !$doBackoff101;
                        }
                        $commit95 = true;
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
                        catch (final fabric.worker.RetryException $e97) {
                            $commit95 = false;
                            continue $label94;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e97) {
                            $commit95 = false;
                            fabric.common.TransactionID $currentTid98 =
                              $tm99.getCurrentTid();
                            if ($e97.tid.isDescendantOf($currentTid98))
                                continue $label94;
                            if ($currentTid98.parent != null) {
                                $retry96 = false;
                                throw $e97;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e97) {
                            $commit95 = false;
                            if ($tm99.checkForStaleObjects()) continue $label94;
                            $retry96 = false;
                            throw new fabric.worker.AbortException($e97);
                        }
                        finally {
                            if ($commit95) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e97) {
                                    $commit95 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e97) {
                                    $commit95 = false;
                                    fabric.common.TransactionID $currentTid98 =
                                      $tm99.getCurrentTid();
                                    if ($currentTid98 != null) {
                                        if ($e97.tid.equals($currentTid98) ||
                                              !$e97.tid.isDescendantOf(
                                                          $currentTid98)) {
                                            throw $e97;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit95 && $retry96) {
                                {  }
                                continue $label94;
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
                    fabric.worker.transaction.TransactionManager $tm108 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled111 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff109 = 1;
                    boolean $doBackoff110 = true;
                    boolean $retry105 = true;
                    $label103: for (boolean $commit104 = false; !$commit104; ) {
                        if ($backoffEnabled111) {
                            if ($doBackoff110) {
                                if ($backoff109 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff109);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e106) {
                                            
                                        }
                                    }
                                }
                                if ($backoff109 < 5000) $backoff109 *= 2;
                            }
                            $doBackoff110 = $backoff109 <= 32 || !$doBackoff110;
                        }
                        $commit104 = true;
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
                        catch (final fabric.worker.RetryException $e106) {
                            $commit104 = false;
                            continue $label103;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e106) {
                            $commit104 = false;
                            fabric.common.TransactionID $currentTid107 =
                              $tm108.getCurrentTid();
                            if ($e106.tid.isDescendantOf($currentTid107))
                                continue $label103;
                            if ($currentTid107.parent != null) {
                                $retry105 = false;
                                throw $e106;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e106) {
                            $commit104 = false;
                            if ($tm108.checkForStaleObjects())
                                continue $label103;
                            $retry105 = false;
                            throw new fabric.worker.AbortException($e106);
                        }
                        finally {
                            if ($commit104) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e106) {
                                    $commit104 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e106) {
                                    $commit104 = false;
                                    fabric.common.TransactionID $currentTid107 =
                                      $tm108.getCurrentTid();
                                    if ($currentTid107 != null) {
                                        if ($e106.tid.equals($currentTid107) ||
                                              !$e106.tid.isDescendantOf(
                                                           $currentTid107)) {
                                            throw $e106;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit104 && $retry105) {
                                {  }
                                continue $label103;
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
                      proxy$var112 = proxy;
                    fabric.worker.transaction.TransactionManager $tm118 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled121 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff119 = 1;
                    boolean $doBackoff120 = true;
                    boolean $retry115 = true;
                    $label113: for (boolean $commit114 = false; !$commit114; ) {
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
                        $commit114 = true;
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
                        catch (final fabric.worker.RetryException $e116) {
                            $commit114 = false;
                            continue $label113;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e116) {
                            $commit114 = false;
                            fabric.common.TransactionID $currentTid117 =
                              $tm118.getCurrentTid();
                            if ($e116.tid.isDescendantOf($currentTid117))
                                continue $label113;
                            if ($currentTid117.parent != null) {
                                $retry115 = false;
                                throw $e116;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e116) {
                            $commit114 = false;
                            if ($tm118.checkForStaleObjects())
                                continue $label113;
                            $retry115 = false;
                            throw new fabric.worker.AbortException($e116);
                        }
                        finally {
                            if ($commit114) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e116) {
                                    $commit114 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e116) {
                                    $commit114 = false;
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
                            if (!$commit114 && $retry115) {
                                { proxy = proxy$var112; }
                                continue $label113;
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
    
    public static final byte[] $classHash = new byte[] { 28, 127, 13, 98, -77,
    -53, -60, -111, -40, 106, -82, 69, -57, 76, -46, 54, -128, -40, -57, -124,
    124, -43, 29, 78, -6, -43, -34, -60, 89, -121, -32, -88 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518538099000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
