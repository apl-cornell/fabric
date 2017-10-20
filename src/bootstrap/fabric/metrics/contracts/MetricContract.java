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
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.common.TransactionID;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;

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
    
    public fabric.metrics.contracts.Bound get$bound();
    
    public fabric.metrics.contracts.Bound set$bound(
      fabric.metrics.contracts.Bound val);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      get$currentPolicy();
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      set$currentPolicy(fabric.metrics.contracts.enforcement.EnforcementPolicy val);
    
    /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param bound
   *        the {@link Bound} this {@link MetricContract} asserts on
   *        metric.
   */
    public fabric.metrics.contracts.MetricContract
      fabric$metrics$contracts$MetricContract$(
      fabric.metrics.Metric metric, fabric.metrics.contracts.Bound bound);
    
    /** @return the {@link Metric} that this contract observes. */
    public fabric.metrics.Metric getMetric();
    
    /** @return the {@link Bound} that this contract observes. */
    public fabric.metrics.contracts.Bound getBound();
    
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
   * @param otherBound
   *        the {@link Bound} that would be used by the other
   *        {@link MetricContract}
   * @return true iff this would imply (and therefore) can enforce another
   *       {@link MetricContract} with the given metric and bound.
   */
    public boolean implies(
      fabric.metrics.Metric otherMetric, fabric.metrics.contracts.Bound otherBound);
    
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
   * @param otherBound
   *        the {@link Bound} that would be used by the other
   *        {@link MetricContract}
   * @return true iff this enforces another {@link MetricContract} with the
   *       given parameters.
   */
    public boolean enforces(fabric.metrics.Metric otherMetric,
                            fabric.metrics.contracts.Bound otherBound);
    
    public java.lang.String toString();
    
    public fabric.util.Set getLeafSubjects();
    
    /**
   * {@inheritDoc}
   *
   * Stops observing any evidence used by the current enforcement policy (by
   * unapplying the policy).
   */
    public void removeObserver(fabric.metrics.util.Observer obs);
    
    public void acquireReconfigLocks();
    
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
        
        public fabric.metrics.contracts.Bound get$bound() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              get$bound();
        }
        
        public fabric.metrics.contracts.Bound set$bound(
          fabric.metrics.contracts.Bound val) {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              set$bound(val);
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
          fabric$metrics$contracts$MetricContract$(
          fabric.metrics.Metric arg1, fabric.metrics.contracts.Bound arg2) {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              fabric$metrics$contracts$MetricContract$(arg1, arg2);
        }
        
        public fabric.metrics.Metric getMetric() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              getMetric();
        }
        
        public fabric.metrics.contracts.Bound getBound() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).getBound(
                                                                         );
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
        
        public boolean implies(fabric.metrics.Metric arg1,
                               fabric.metrics.contracts.Bound arg2) {
            return ((fabric.metrics.contracts.MetricContract) fetch()).implies(
                                                                         arg1,
                                                                         arg2);
        }
        
        public boolean implies(fabric.metrics.contracts.MetricContract arg1) {
            return ((fabric.metrics.contracts.MetricContract) fetch()).implies(
                                                                         arg1);
        }
        
        public boolean enforces(fabric.metrics.Metric arg1,
                                fabric.metrics.contracts.Bound arg2) {
            return ((fabric.metrics.contracts.MetricContract) fetch()).enforces(
                                                                         arg1,
                                                                         arg2);
        }
        
        public fabric.util.Set getLeafSubjects() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              getLeafSubjects();
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
        
        public fabric.metrics.contracts.Bound get$bound() { return this.bound; }
        
        public fabric.metrics.contracts.Bound set$bound(
          fabric.metrics.contracts.Bound val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.bound = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.contracts.Bound bound;
        
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
   * @param bound
   *        the {@link Bound} this {@link MetricContract} asserts on
   *        metric.
   */
        public fabric.metrics.contracts.MetricContract
          fabric$metrics$contracts$MetricContract$(
          fabric.metrics.Metric metric, fabric.metrics.contracts.Bound bound) {
            this.set$metric(metric);
            this.set$bound(
                   ((fabric.metrics.contracts.Bound)
                      new fabric.metrics.contracts.Bound._Impl(
                        this.$getStore()).$getProxy(
                                            )).fabric$metrics$contracts$Bound$(
                                                 bound.get$rate(),
                                                 bound.get$base(), 0));
            fabric$metrics$contracts$Contract$();
            return (fabric.metrics.contracts.MetricContract) this.$getProxy();
        }
        
        /** @return the {@link Metric} that this contract observes. */
        public fabric.metrics.Metric getMetric() { return this.get$metric(); }
        
        /** @return the {@link Bound} that this contract observes. */
        public fabric.metrics.contracts.Bound getBound() {
            return this.get$bound();
        }
        
        /**
   * @return the expected lifetime of this {@link MetricContract} given the
   *       associated {@link Metric}s current velocity.
   */
        public long getExpectedLifetime() {
            long time = java.lang.System.currentTimeMillis();
            fabric.metrics.Metric m = getMetric();
            fabric.metrics.contracts.Bound bound = getBound();
            double adjustedRate = bound.get$rate() - m.velocity();
            return (long)
                     (time + (m.value() - bound.value(time)) / adjustedRate);
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
            {
                fabric.worker.transaction.TransactionManager $tm402 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled405 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff403 = 1;
                boolean $doBackoff404 = true;
                $label398: for (boolean $commit399 = false; !$commit399; ) {
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
                    $commit399 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (tmp.isActivated()) return; }
                    catch (final fabric.worker.RetryException $e400) {
                        $commit399 = false;
                        continue $label398;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e400) {
                        $commit399 = false;
                        fabric.common.TransactionID $currentTid401 =
                          $tm402.getCurrentTid();
                        if ($e400.tid.isDescendantOf($currentTid401))
                            continue $label398;
                        if ($currentTid401.parent != null) throw $e400;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e400) {
                        $commit399 = false;
                        if ($tm402.checkForStaleObjects()) continue $label398;
                        throw new fabric.worker.AbortException($e400);
                    }
                    finally {
                        if ($commit399) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e400) {
                                $commit399 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e400) {
                                $commit399 = false;
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
                        if (!$commit399) {
                            {  }
                            continue $label398;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.
              finer(
                "CREATING A POLICY FOR MC " +
                  java.lang.String.valueOf(
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         tmp)));
            fabric.metrics.contracts.enforcement.EnforcementPolicy startPol =
              null;
            {
                fabric.metrics.contracts.enforcement.EnforcementPolicy
                  startPol$var406 = startPol;
                fabric.worker.transaction.TransactionManager $tm411 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled414 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff412 = 1;
                boolean $doBackoff413 = true;
                $label407: for (boolean $commit408 = false; !$commit408; ) {
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
                    $commit408 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        startPol = tmp.get$metric().policy(tmp.get$bound(),
                                                           true);
                    }
                    catch (final fabric.worker.RetryException $e409) {
                        $commit408 = false;
                        continue $label407;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e409) {
                        $commit408 = false;
                        fabric.common.TransactionID $currentTid410 =
                          $tm411.getCurrentTid();
                        if ($e409.tid.isDescendantOf($currentTid410))
                            continue $label407;
                        if ($currentTid410.parent != null) throw $e409;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e409) {
                        $commit408 = false;
                        if ($tm411.checkForStaleObjects()) continue $label407;
                        throw new fabric.worker.AbortException($e409);
                    }
                    finally {
                        if ($commit408) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e409) {
                                $commit408 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e409) {
                                $commit408 = false;
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
                        if (!$commit408) {
                            { startPol = startPol$var406; }
                            continue $label407;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.
              finer(
                "ACTIVATING POLICY FOR MC " +
                  java.lang.String.valueOf(
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         tmp)));
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
            {
                fabric.worker.transaction.TransactionManager $tm419 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled422 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff420 = 1;
                boolean $doBackoff421 = true;
                $label415: for (boolean $commit416 = false; !$commit416; ) {
                    if ($backoffEnabled422) {
                        if ($doBackoff421) {
                            if ($backoff420 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff420);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e417) {
                                        
                                    }
                                }
                            }
                            if ($backoff420 < 5000) $backoff420 *= 2;
                        }
                        $doBackoff421 = $backoff420 <= 32 || !$doBackoff421;
                    }
                    $commit416 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.common.Logging.METRICS_LOGGER.
                          finer(
                            "ACTIVATING MC " +
                              java.lang.String.
                                valueOf(
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      tmp)) +
                              " IN " +
                              fabric.worker.transaction.TransactionManager.
                                getInstance().getCurrentLog());
                        tmp.set$currentPolicy(p);
                        tmp.set$$expiry((long)
                                          tmp.get$currentPolicy().expiry());
                        fabric.metrics.contracts.Contract._Impl.static_activate(
                                                                  tmp);
                        if (tmp.get$$expiry() >=
                              java.lang.System.currentTimeMillis()) {
                            tmp.get$currentPolicy().apply(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                    }
                    catch (final fabric.worker.RetryException $e417) {
                        $commit416 = false;
                        continue $label415;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e417) {
                        $commit416 = false;
                        fabric.common.TransactionID $currentTid418 =
                          $tm419.getCurrentTid();
                        if ($e417.tid.isDescendantOf($currentTid418))
                            continue $label415;
                        if ($currentTid418.parent != null) throw $e417;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e417) {
                        $commit416 = false;
                        if ($tm419.checkForStaleObjects()) continue $label415;
                        throw new fabric.worker.AbortException($e417);
                    }
                    finally {
                        if ($commit416) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e417) {
                                $commit416 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e417) {
                                $commit416 = false;
                                fabric.common.TransactionID $currentTid418 =
                                  $tm419.getCurrentTid();
                                if ($currentTid418 != null) {
                                    if ($e417.tid.equals($currentTid418) ||
                                          !$e417.tid.isDescendantOf(
                                                       $currentTid418)) {
                                        throw $e417;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit416) {
                            {  }
                            continue $label415;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.
              finer(
                "ACTIVATED MC " +
                  java.lang.String.valueOf(
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         tmp)) +
                  " IN " +
                  fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentLog());
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
            if (!isActivated()) {
                fabric.common.Logging.METRICS_LOGGER.
                  log(java.util.logging.Level.FINER, "CONTRACT INACTIVE");
                return false;
            }
            long currentTime = java.lang.System.currentTimeMillis();
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null)) {
                long curExpiry = this.get$currentPolicy().expiry();
                if (curExpiry >= currentTime) {
                    boolean result = update(curExpiry);
                    fabric.common.Logging.METRICS_LOGGER.
                      log(
                        java.util.logging.Level.FINER,
                        "DEFENDING " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.
                                  $unwrap(
                                    (fabric.metrics.contracts.MetricContract)
                                      this.$getProxy())) +
                          " WITH " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.
                                  $unwrap(this.get$currentPolicy())));
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
                  " " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                          this.get$bound())) +
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
              this.get$metric().policy(this.get$bound());
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
            fabric.common.Logging.METRICS_LOGGER.
              log(java.util.logging.Level.FINER, "CONTRACT TRUE");
            return result;
        }
        
        /**
   * Check if this implies another {@link MetricContract} being considered.
   *
   * @param otherMetric
   *        the {@link Metric} the other {@link MetricContract} would
   *        assert a bound on
   * @param otherBound
   *        the {@link Bound} that would be used by the other
   *        {@link MetricContract}
   * @return true iff this would imply (and therefore) can enforce another
   *       {@link MetricContract} with the given metric and bound.
   */
        public boolean implies(fabric.metrics.Metric otherMetric,
                               fabric.metrics.contracts.Bound otherBound) {
            if (!getMetric().equals(otherMetric) ||
                  !this.get$bound().implies(otherBound))
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
              implies(other.getMetric(), other.getBound());
        }
        
        /**
   * Check if this enforces the <strong>same</strong> bound as another
   * {@link MetricContract} being considered. Attempts to refresh this
   * contract if it's gone stale and would otherwise enforce the bound.
   *
   * @param otherMetric
   *        the {@link Metric} the other {@link MetricContract} would
   *        assert a bound on
   * @param otherBound
   *        the {@link Bound} that would be used by the other
   *        {@link MetricContract}
   * @return true iff this enforces another {@link MetricContract} with the
   *       given parameters.
   */
        public boolean enforces(fabric.metrics.Metric otherMetric,
                                fabric.metrics.contracts.Bound otherBound) {
            if (!getMetric().equals(otherMetric) ||
                  !this.get$bound().equals(otherBound))
                return false;
            return valid();
        }
        
        public java.lang.String toString() {
            return ((java.lang.Comparable)
                      fabric.lang.WrappedJavaInlineable.$unwrap(getMetric())).
              toString() +
            " " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(this.get$bound())) +
            " until " +
            getExpiry();
        }
        
        public fabric.util.Set getLeafSubjects() {
            fabric.metrics.Metric m = getMetric();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(m)) instanceof fabric.metrics.SampledMetric) {
                return fabric.util.Collections._Impl.
                  singleton((fabric.metrics.SampledMetric)
                              fabric.lang.Object._Proxy.$getProxy(m));
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
            {
                fabric.worker.transaction.TransactionManager $tm427 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled430 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff428 = 1;
                boolean $doBackoff429 = true;
                $label423: for (boolean $commit424 = false; !$commit424; ) {
                    if ($backoffEnabled430) {
                        if ($doBackoff429) {
                            if ($backoff428 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff428);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e425) {
                                        
                                    }
                                }
                            }
                            if ($backoff428 < 5000) $backoff428 *= 2;
                        }
                        $doBackoff429 = $backoff428 <= 32 || !$doBackoff429;
                    }
                    $commit424 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.metrics.contracts.Contract._Impl.
                          static_removeObserver(tmp, obs);
                        if (!tmp.isObserved()) {
                            if (!fabric.lang.Object._Proxy.
                                  idEquals(tmp.get$currentPolicy(), null))
                                tmp.get$currentPolicy().unapply(tmp);
                        }
                    }
                    catch (final fabric.worker.RetryException $e425) {
                        $commit424 = false;
                        continue $label423;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e425) {
                        $commit424 = false;
                        fabric.common.TransactionID $currentTid426 =
                          $tm427.getCurrentTid();
                        if ($e425.tid.isDescendantOf($currentTid426))
                            continue $label423;
                        if ($currentTid426.parent != null) throw $e425;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e425) {
                        $commit424 = false;
                        if ($tm427.checkForStaleObjects()) continue $label423;
                        throw new fabric.worker.AbortException($e425);
                    }
                    finally {
                        if ($commit424) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e425) {
                                $commit424 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e425) {
                                $commit424 = false;
                                fabric.common.TransactionID $currentTid426 =
                                  $tm427.getCurrentTid();
                                if ($currentTid426 != null) {
                                    if ($e425.tid.equals($currentTid426) ||
                                          !$e425.tid.isDescendantOf(
                                                       $currentTid426)) {
                                        throw $e425;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit424) {
                            {  }
                            continue $label423;
                        }
                    }
                }
            }
        }
        
        public void acquireReconfigLocks() {
            fabric.common.Logging.METRICS_LOGGER.
              finer(
                "ACQUIRING LOCK AT " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.contracts.MetricContract)
                                    this.$getProxy())) +
                  " IN " +
                  fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentLog());
            this.get$lock().acquire();
            this.get$currentPolicy().acquireReconfigLocks();
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
            $writeRef($getStore(), this.bound, refTypes, out, intraStoreRefs,
                      interStoreRefs);
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
            this.bound = (fabric.metrics.contracts.Bound)
                           $readRef(fabric.metrics.contracts.Bound._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
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
            this.bound = src.bound;
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
    
    public static final byte[] $classHash = new byte[] { 18, 73, 64, 17, -54,
    -7, -48, 12, -1, 29, 1, -81, -49, 24, -71, 53, -104, -79, -48, -51, -12, 36,
    65, -83, 54, 39, -75, -47, -93, -71, -112, 21 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1508291571000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
