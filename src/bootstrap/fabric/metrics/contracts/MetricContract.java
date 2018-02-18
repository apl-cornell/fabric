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
        
        public static final byte[] $classHash = new byte[] { -113, -15, -31, -9,
        66, -48, 102, 120, -64, -64, -31, 2, 47, 13, 8, -106, 117, 49, 124, 4,
        -108, -124, -128, -2, -56, 63, 76, 46, -2, -77, 27, 16 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1518990582000L;
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
                                      idEquals(tmp.get$currentPolicy(), null)) {
                                    tmp.get$currentPolicy().unapply(tmp);
                                    tmp.set$currentPolicy(null);
                                }
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
    
    public static final byte[] $classHash = new byte[] { -118, -111, 92, 26, 52,
    -50, 57, -94, 97, -83, -42, 66, 108, -46, 75, -83, 11, 92, 82, 66, -79,
    -101, 89, -23, 120, -47, 117, 91, 50, -58, -127, 76 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518990582000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
