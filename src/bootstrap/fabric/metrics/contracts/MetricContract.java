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
            {
                fabric.worker.transaction.TransactionManager $tm383 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled386 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff384 = 1;
                boolean $doBackoff385 = true;
                $label379: for (boolean $commit380 = false; !$commit380; ) {
                    if ($backoffEnabled386) {
                        if ($doBackoff385) {
                            if ($backoff384 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff384);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e381) {
                                        
                                    }
                                }
                            }
                            if ($backoff384 < 5000) $backoff384 *= 2;
                        }
                        $doBackoff385 = $backoff384 <= 32 || !$doBackoff385;
                    }
                    $commit380 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (isActivated()) return; }
                    catch (final fabric.worker.RetryException $e381) {
                        $commit380 = false;
                        continue $label379;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e381) {
                        $commit380 = false;
                        fabric.common.TransactionID $currentTid382 =
                          $tm383.getCurrentTid();
                        if ($e381.tid.isDescendantOf($currentTid382))
                            continue $label379;
                        if ($currentTid382.parent != null) throw $e381;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e381) {
                        $commit380 = false;
                        if ($tm383.checkForStaleObjects()) continue $label379;
                        throw new fabric.worker.AbortException($e381);
                    }
                    finally {
                        if ($commit380) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e381) {
                                $commit380 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e381) {
                                $commit380 = false;
                                fabric.common.TransactionID $currentTid382 =
                                  $tm383.getCurrentTid();
                                if ($currentTid382 != null) {
                                    if ($e381.tid.equals($currentTid382) ||
                                          !$e381.tid.isDescendantOf(
                                                       $currentTid382)) {
                                        throw $e381;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit380) {
                            {  }
                            continue $label379;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.
              finer(
                "CREATING A POLICY FOR MC " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.contracts.MetricContract)
                                    this.$getProxy())));
            fabric.metrics.contracts.enforcement.EnforcementPolicy startPol =
              null;
            {
                fabric.metrics.contracts.enforcement.EnforcementPolicy
                  startPol$var387 = startPol;
                fabric.worker.transaction.TransactionManager $tm392 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled395 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff393 = 1;
                boolean $doBackoff394 = true;
                $label388: for (boolean $commit389 = false; !$commit389; ) {
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
                    $commit389 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        startPol = this.get$metric().policy(this.get$bound(),
                                                            true);
                    }
                    catch (final fabric.worker.RetryException $e390) {
                        $commit389 = false;
                        continue $label388;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e390) {
                        $commit389 = false;
                        fabric.common.TransactionID $currentTid391 =
                          $tm392.getCurrentTid();
                        if ($e390.tid.isDescendantOf($currentTid391))
                            continue $label388;
                        if ($currentTid391.parent != null) throw $e390;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e390) {
                        $commit389 = false;
                        if ($tm392.checkForStaleObjects()) continue $label388;
                        throw new fabric.worker.AbortException($e390);
                    }
                    finally {
                        if ($commit389) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e390) {
                                $commit389 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e390) {
                                $commit389 = false;
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
                        if (!$commit389) {
                            { startPol = startPol$var387; }
                            continue $label388;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.
              finer(
                "ACTIVATING POLICY FOR MC " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.contracts.MetricContract)
                                    this.$getProxy())));
            startPol.activate();
            finishActivating(startPol);
        }
        
        public void finishActivating(
          fabric.metrics.contracts.enforcement.EnforcementPolicy p) {
            {
                fabric.worker.transaction.TransactionManager $tm400 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled403 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff401 = 1;
                boolean $doBackoff402 = true;
                $label396: for (boolean $commit397 = false; !$commit397; ) {
                    if ($backoffEnabled403) {
                        if ($doBackoff402) {
                            if ($backoff401 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff401);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e398) {
                                        
                                    }
                                }
                            }
                            if ($backoff401 < 5000) $backoff401 *= 2;
                        }
                        $doBackoff402 = $backoff401 <= 32 || !$doBackoff402;
                    }
                    $commit397 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.common.Logging.METRICS_LOGGER.
                          finer(
                            "ACTIVATING MC " +
                              java.lang.String.
                                valueOf(
                                  fabric.lang.WrappedJavaInlineable.
                                      $unwrap(
                                        (fabric.metrics.contracts.MetricContract)
                                          this.$getProxy())) +
                              " IN " +
                              fabric.worker.transaction.TransactionManager.
                                getInstance().getCurrentLog());
                        this.set$currentPolicy(p);
                        this.set$$expiry((long)
                                           this.get$currentPolicy().expiry());
                        super.activate();
                        if (this.get$$expiry() >=
                              java.lang.System.currentTimeMillis()) {
                            this.get$currentPolicy().
                              apply((fabric.metrics.contracts.MetricContract)
                                      this.$getProxy());
                            getMetric().
                              addContract(
                                (fabric.metrics.contracts.MetricContract)
                                  this.$getProxy());
                        }
                    }
                    catch (final fabric.worker.RetryException $e398) {
                        $commit397 = false;
                        continue $label396;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e398) {
                        $commit397 = false;
                        fabric.common.TransactionID $currentTid399 =
                          $tm400.getCurrentTid();
                        if ($e398.tid.isDescendantOf($currentTid399))
                            continue $label396;
                        if ($currentTid399.parent != null) throw $e398;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e398) {
                        $commit397 = false;
                        if ($tm400.checkForStaleObjects()) continue $label396;
                        throw new fabric.worker.AbortException($e398);
                    }
                    finally {
                        if ($commit397) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e398) {
                                $commit397 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e398) {
                                $commit397 = false;
                                fabric.common.TransactionID $currentTid399 =
                                  $tm400.getCurrentTid();
                                if ($currentTid399 != null) {
                                    if ($e398.tid.equals($currentTid399) ||
                                          !$e398.tid.isDescendantOf(
                                                       $currentTid399)) {
                                        throw $e398;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit397) {
                            {  }
                            continue $label396;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.
              finer(
                "ACTIVATED MC " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.contracts.MetricContract)
                                    this.$getProxy())) +
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
                  " " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                          this.get$bound())) +
                  " IN " +
                  fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentTid());
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
            {
                fabric.worker.transaction.TransactionManager $tm408 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled411 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff409 = 1;
                boolean $doBackoff410 = true;
                $label404: for (boolean $commit405 = false; !$commit405; ) {
                    if ($backoffEnabled411) {
                        if ($doBackoff410) {
                            if ($backoff409 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff409);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e406) {
                                        
                                    }
                                }
                            }
                            if ($backoff409 < 5000) $backoff409 *= 2;
                        }
                        $doBackoff410 = $backoff409 <= 32 || !$doBackoff410;
                    }
                    $commit405 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        super.removeObserver(obs);
                        if (!isObserved()) {
                            if (!fabric.lang.Object._Proxy.
                                  idEquals(this.get$currentPolicy(), null))
                                this.get$currentPolicy().
                                  unapply(
                                    (fabric.metrics.contracts.MetricContract)
                                      this.$getProxy());
                        }
                    }
                    catch (final fabric.worker.RetryException $e406) {
                        $commit405 = false;
                        continue $label404;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e406) {
                        $commit405 = false;
                        fabric.common.TransactionID $currentTid407 =
                          $tm408.getCurrentTid();
                        if ($e406.tid.isDescendantOf($currentTid407))
                            continue $label404;
                        if ($currentTid407.parent != null) throw $e406;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e406) {
                        $commit405 = false;
                        if ($tm408.checkForStaleObjects()) continue $label404;
                        throw new fabric.worker.AbortException($e406);
                    }
                    finally {
                        if ($commit405) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e406) {
                                $commit405 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e406) {
                                $commit405 = false;
                                fabric.common.TransactionID $currentTid407 =
                                  $tm408.getCurrentTid();
                                if ($currentTid407 != null) {
                                    if ($e406.tid.equals($currentTid407) ||
                                          !$e406.tid.isDescendantOf(
                                                       $currentTid407)) {
                                        throw $e406;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit405) {
                            {  }
                            continue $label404;
                        }
                    }
                }
            }
        }
        
        public void acquireReconfigLocks() {
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
    
    public static final byte[] $classHash = new byte[] { -9, 50, 40, 12, 32,
    -97, 115, -112, -51, -48, 15, 113, -32, 26, -85, 104, 85, 87, 78, 103, 97,
    -56, 23, -84, -68, 7, -61, -76, -105, 58, -114, -48 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507217540000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVZf2xTxx0/O4kTQ4hD+G1CCIlH+WkPuiGVrHTgQmMwJSJAtbCRPT+fnVee33u8dw5OOxCtVEEnLZpaoCANqml06yAr2la0TWu0au06qq4bnba2m9aWrqvoRmHqto5JG+u+33tnP/vFduM/FuXu+97dfe8+3+99f9w9j10jDZZJulJSQlHDbMSgVniTlIjF+yTTosmoKlnWDmgdlKfWx46/961kh5d446RZljRdU2RJHdQsRlri90rDUkSjLLJze6xnN/HLyNgrWUOMeHdvyJmk09DVkbSqM7HIhPmPLY8cfWxP6/fqSGCABBStn0lMkaO6xmiODZDmDM0kqGmtTyZpcoBM1yhN9lNTkVTlPhioawOkzVLSmsSyJrW2U0tXh3Fgm5U1qMnXzDcifB1gm1mZ6SbAb7XhZ5miRuKKxXrixJdSqJq09pGDpD5OGlKqlIaBs+N5KSJ8xsgmbIfhUxSAaaYkmeZZ6vcqWpKRhW6OgsShLTAAWBszlA3phaXqNQkaSJsNSZW0dKSfmYqWhqENehZWYSRYcVIY1GRI8l4pTQcZmese12d3wSg/VwuyMDLLPYzPBHsWdO1Z0W5du/szo/drvZqXeABzksoq4m8Cpg4X03aaoibVZGozNi+LH5dmjx/xEgKDZ7kG22N+8KUPPrui49mL9pj5ZcZsS9xLZTYon0m0vNIeXXpbHcJoMnRLQVMokZzvap/o6ckZYO2zCzNiZzjf+ez2Fz536Cy96iVTYsQn62o2A1Y1XdYzhqJS8y6qUVNiNBkjfqolo7w/RhrhOa5o1G7dlkpZlMVIvcqbfDp/BxWlYApUUSM8K1pKzz8bEhvizzmDENIIhXjgfzoh3SfheT4hdSOM7IoM6RkaSahZuh/MOwKFSqY8FAG/NRU5YplyxMxqTIFBogmsCIgVAVNnpiQzK7KVt0TFexgQGf+3mXMoU+t+jwfUvVDWkzQhWbB3wo429KngKr26mqTmoKyOjsfIjPGT3Jb8aP8W2DDXlgf2v90dOYp5j2Y3bPzgqcGXbDtEXqFMRm6x4YYF3HABbrgULiBsRmcLQ/gKQ/ga8+TC0dOxc9ymfBZ3vsKkzTDpWkOVWEo3Mzni8XAJZ3J+bkxgCnshxEAUaV7a/4XNXzzSVQdWbOyvx42FoSG3TzmRKAZPEjjKoBw4/N4/zx8/oDvexUhogtNP5ESn7XKry9RlmoSg6Ey/rFO6MDh+IOTFgONHvUhgrRBYOtxrlDhvTz4QojYa4mQq6kBSsSsfvaawIVPf77RwM2jBqs22CFSWCyCPobf3G6de/+Wfb+XZJR9uA0VxuZ+yniIXx8kC3JmnO7rfYVIK49440ffosWuHd3PFw4jucguGsI6Ca0vg07r50MV9v3vrzTO/8TqbxYjPyCZURc5xWaZ/BH8eKP/Fgn6KDUghWkdFjOgsBAkDV17sYINwoULIAuhWaKeW0ZNKSpESKkVL+U/gE6suvD/aam+3Ci228kyy4uMncNrnbSCHXtpzo4NP45ExXTn6c4bZMXCGM/N605RGEEfugV8vOPlz6RRYPkQwS7mP8qBEuD4I38DVXBcreb3K1fcprLpsbbXz9jprYj7YhInVscWByNjXgtF1V23nL9gizrGojPPvkorcZPXZzIfeLt/PvKRxgLTynC5pbJcEsQzMYACyshUVjXEyraS/NMPa6aSn4Gvtbj8oWtbtBU7QgWccjc9TbMO3DQcU0YZK6oSyAJTyU0Gfxt4ZBtYzcx7CH9Zylm5eL8ZqKVeklxG/YeoMUFI4VfiVTCbLcPf5OsvBVEWUw9dZkNJdsc+OeNgZtN0Q6zWl8DrsUve2oL8tAy9aAR4+rsPqjjyghoSe1ZJ5PB0VY/EGHFYRWDMCWwFlISH1LTatu14G2ObywDwcWK4wnxfnmyrmuSbon4rmY2AnWRNCHuvTwfVH8gKsqSgAhWRuyjQDLOGNzrPN7giWqwJwmQOQ//lE4s8JahYBLPIukgP3WlDpjMbPl2cePHo6ue2JVfZJqq303LNRy2a+8+rNX4RPXH6xTP70M91YqdJhqhatiQF80YTLwlZ+hHUc8/LVBbdF976btpdd6ILoHv3trWMv3rVYfsRL6goeOOHcXMrUU+p3U0wKx35tR4n3dRaU6kdlfR7KEkIa1ti0/p1iK3Jsrxur/lKDaRIsfxT0D+79cOKh1/GH9Vj18qnlKlGTI97DyBLbwkLCwkIFCwuVHldCDtbdpRKCqZBPgvX0CrqmNgmR5dOCRipLWIxdrdLHU3QazChNmRN+1pcDPg8KrOx7RtCx2oAjyzlBn5gc8GyVvv1Ygf03AfBCdOoth7sbyu1waH9YUL023MiiCTo0OdwHq/QdwgruCjMA98acwVNFXElRPLqXycN9ppKBo9SwuJfRI0e//FF49KgdCOzLa/eE+2Mxj32B5UtP40Efw9Giaqtwjk1Xzh/48ZMHDnsF7HsYHHl0LV1OwXdC2QjKGhf0wdoUjCwPCHp/ZQV7xOHclavwZBC2KKQDhY2gIJqsGJJt8vPcNwGO5itVdudRrI7AmRZcWBmGE+KgSTOQ0PngB4X+kDwE+hjWlWQ5fcyFshmEuSno9dr0gSzXBL0yOYM7VaXvcaxOgKPkRcL3Y+Vw3wolCYn3OUFP1oYbWU4I+sjH7iO+9vFZv1kF/JNYfZ2R1pSiKdbQelsEcdUrK4QExSKkZatNp31YmxDI8g9Br1cWoiiBjDqSnK8iyXexOsvIHLckwsLKCdSc35WDhATOC3qkikBbJx6hkOWwoAcn510uO29M6LpKJY0v9sMqIo5j9X1gMGnKpBYPlj8qt0froMDxOpgWdHVte4QsqwRdPqk9Kkryz1cR4AWsfgICKBlDVWynLytAGMolQtpXCNpYmwDI4hOUTMpTWvmsL1fB/iusLk4C+x1Q3oEj9UybdrxZG3ZkeUPQ12pV/qtVBHgdq1cgTomjemUJglD+AhJcEPRMbRIgyzcEPVVZgmJsb1Xpexur3wNuptufgPMZqpXf3Xl+KuqYkJPKSYhr/BWuojFBF9UmIbJ0ChqcnITvV+njcfAKJEU4ssSplOrP8ou4lRc0IFIxv67AlR6bg+XEWgbWDCC7VEG31CQWZ9ks6J2TC2WArt11J+QotyUsag7bHzCCfOkbVRTwb6z+xkgLhuphWsxcNgeFYPlpcPCMCRquTU5kWSnoLZPaPo+3Sl89Nt5kZKYk78sqJt1O4dKSUtJxXd7LGY7lQLTS6wt+e5pf5oOw+NFCjj5Pz7y7ZcWsCh+D5074GUnwPXU60DTn9M7X+DfNwg8S/jhpSmVVtfgbTdGzz4CEonDF+e0vNgYXzM/I3EoXfmZ/peLPKKSn0eZpBlFLeRj/bQefiscFGPHZ4/CtlW9A0KnyxrWo4geHvCYdCwtmTfyVbezvc/7la9pxmX/DhD3qvLF6SXPn49ZXX74U2Hc5eG5o5z13p6WLc8aeaXzu6cfWjl76H9PVsxv9GwAA";
}
