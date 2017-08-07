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
    
    public void activate();
    
    /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
    public boolean refresh();
    
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
        
        public void activate() {
            fabric.common.Logging.METRICS_LOGGER.fine("ASDF ACTIVATING MC " +
                                                        $getOnum());
            {
                fabric.worker.transaction.TransactionManager $tm61 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff62 = 1;
                boolean $doBackoff63 = true;
                $label57: for (boolean $commit58 = false; !$commit58; ) {
                    if ($doBackoff63) {
                        if ($backoff62 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff62);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e59) {  }
                            }
                        }
                        if ($backoff62 < 5000) $backoff62 *= 2;
                    }
                    $doBackoff63 = $backoff62 <= 32 || !$doBackoff63;
                    $commit58 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (isActivated()) return; }
                    catch (final fabric.worker.RetryException $e59) {
                        $commit58 = false;
                        continue $label57;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e59) {
                        $commit58 = false;
                        fabric.common.TransactionID $currentTid60 =
                          $tm61.getCurrentTid();
                        if ($e59.tid.isDescendantOf($currentTid60))
                            continue $label57;
                        if ($currentTid60.parent != null) throw $e59;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e59) {
                        $commit58 = false;
                        if ($tm61.checkForStaleObjects()) continue $label57;
                        throw new fabric.worker.AbortException($e59);
                    }
                    finally {
                        if ($commit58) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e59) {
                                $commit58 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e59) {
                                $commit58 = false;
                                fabric.common.TransactionID $currentTid60 =
                                  $tm61.getCurrentTid();
                                if ($currentTid60 != null) {
                                    if ($e59.tid.equals($currentTid60) ||
                                          !$e59.tid.isDescendantOf(
                                                      $currentTid60)) {
                                        throw $e59;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit58) {  }
                    }
                }
            }
            this.get$metric().refreshWeakEstimates();
            fabric.metrics.contracts.enforcement.EnforcementPolicy startPol =
              null;
            {
                fabric.metrics.contracts.enforcement.EnforcementPolicy
                  startPol$var64 = startPol;
                fabric.worker.transaction.TransactionManager $tm69 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff70 = 1;
                boolean $doBackoff71 = true;
                $label65: for (boolean $commit66 = false; !$commit66; ) {
                    if ($doBackoff71) {
                        if ($backoff70 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff70);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e67) {  }
                            }
                        }
                        if ($backoff70 < 5000) $backoff70 *= 2;
                    }
                    $doBackoff71 = $backoff70 <= 32 || !$doBackoff71;
                    $commit66 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        startPol = this.get$metric().policy(this.get$bound());
                    }
                    catch (final fabric.worker.RetryException $e67) {
                        $commit66 = false;
                        continue $label65;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e67) {
                        $commit66 = false;
                        fabric.common.TransactionID $currentTid68 =
                          $tm69.getCurrentTid();
                        if ($e67.tid.isDescendantOf($currentTid68))
                            continue $label65;
                        if ($currentTid68.parent != null) throw $e67;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e67) {
                        $commit66 = false;
                        if ($tm69.checkForStaleObjects()) continue $label65;
                        throw new fabric.worker.AbortException($e67);
                    }
                    finally {
                        if ($commit66) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e67) {
                                $commit66 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e67) {
                                $commit66 = false;
                                fabric.common.TransactionID $currentTid68 =
                                  $tm69.getCurrentTid();
                                if ($currentTid68 != null) {
                                    if ($e67.tid.equals($currentTid68) ||
                                          !$e67.tid.isDescendantOf(
                                                      $currentTid68)) {
                                        throw $e67;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit66) { startPol = startPol$var64; }
                    }
                }
            }
            startPol.activate();
            {
                fabric.metrics.contracts.enforcement.EnforcementPolicy
                  startPol$var72 = startPol;
                fabric.worker.transaction.TransactionManager $tm77 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff78 = 1;
                boolean $doBackoff79 = true;
                $label73: for (boolean $commit74 = false; !$commit74; ) {
                    if ($doBackoff79) {
                        if ($backoff78 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff78);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e75) {  }
                            }
                        }
                        if ($backoff78 < 5000) $backoff78 *= 2;
                    }
                    $doBackoff79 = $backoff78 <= 32 || !$doBackoff79;
                    $commit74 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.common.Logging.METRICS_LOGGER.
                          fine("ASDF DONE ACTIVATING MC " + $getOnum());
                        this.set$currentPolicy(startPol);
                        this.set$$expiry((long)
                                           this.get$currentPolicy().expiry());
                        super.activate();
                        if (this.get$currentPolicy().expiry() >=
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
                    catch (final fabric.worker.RetryException $e75) {
                        $commit74 = false;
                        continue $label73;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e75) {
                        $commit74 = false;
                        fabric.common.TransactionID $currentTid76 =
                          $tm77.getCurrentTid();
                        if ($e75.tid.isDescendantOf($currentTid76))
                            continue $label73;
                        if ($currentTid76.parent != null) throw $e75;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e75) {
                        $commit74 = false;
                        if ($tm77.checkForStaleObjects()) continue $label73;
                        throw new fabric.worker.AbortException($e75);
                    }
                    finally {
                        if ($commit74) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e75) {
                                $commit74 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e75) {
                                $commit74 = false;
                                fabric.common.TransactionID $currentTid76 =
                                  $tm77.getCurrentTid();
                                if ($currentTid76 != null) {
                                    if ($e75.tid.equals($currentTid76) ||
                                          !$e75.tid.isDescendantOf(
                                                      $currentTid76)) {
                                        throw $e75;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit74) { startPol = startPol$var72; }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.
              fine("ASDF FINISHED ACTIVATING MC " + $getOnum());
        }
        
        /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
        public boolean refresh() {
            if (!isActivated()) {
                fabric.common.Logging.METRICS_LOGGER.
                  log(java.util.logging.Level.FINE, "CONTRACT INACTIVE");
                return false;
            }
            long currentTime = java.lang.System.currentTimeMillis();
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null)) {
                this.get$currentPolicy().refresh();
                if (this.get$currentPolicy().expiry() >= currentTime) {
                    fabric.common.Logging.METRICS_LOGGER.
                      log(java.util.logging.Level.FINE, "CONTRACT REFRESHED");
                    return update(this.get$currentPolicy().expiry());
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
            if (this.get$currentPolicy().expiry() >= currentTime)
                this.get$currentPolicy().
                  apply((fabric.metrics.contracts.MetricContract)
                          this.$getProxy());
            fabric.common.Logging.METRICS_LOGGER.log(
                                                   java.util.logging.Level.FINE,
                                                   "CONTRACT TRUE");
            return update(this.get$currentPolicy().expiry());
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
                fabric.worker.transaction.TransactionManager $tm84 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff85 = 1;
                boolean $doBackoff86 = true;
                $label80: for (boolean $commit81 = false; !$commit81; ) {
                    if ($doBackoff86) {
                        if ($backoff85 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff85);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e82) {  }
                            }
                        }
                        if ($backoff85 < 5000) $backoff85 *= 2;
                    }
                    $doBackoff86 = $backoff85 <= 32 || !$doBackoff86;
                    $commit81 = true;
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
                    catch (final fabric.worker.RetryException $e82) {
                        $commit81 = false;
                        continue $label80;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e82) {
                        $commit81 = false;
                        fabric.common.TransactionID $currentTid83 =
                          $tm84.getCurrentTid();
                        if ($e82.tid.isDescendantOf($currentTid83))
                            continue $label80;
                        if ($currentTid83.parent != null) throw $e82;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e82) {
                        $commit81 = false;
                        if ($tm84.checkForStaleObjects()) continue $label80;
                        throw new fabric.worker.AbortException($e82);
                    }
                    finally {
                        if ($commit81) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e82) {
                                $commit81 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e82) {
                                $commit81 = false;
                                fabric.common.TransactionID $currentTid83 =
                                  $tm84.getCurrentTid();
                                if ($currentTid83 != null) {
                                    if ($e82.tid.equals($currentTid83) ||
                                          !$e82.tid.isDescendantOf(
                                                      $currentTid83)) {
                                        throw $e82;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit81) {  }
                    }
                }
            }
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
    
    public static final byte[] $classHash = new byte[] { -21, -124, -77, -116,
    33, -127, 37, 20, 27, -27, -94, -50, 127, -63, 105, -27, -75, 98, 121, -30,
    57, -7, 101, -79, 98, 35, 50, -113, 42, -66, 44, -69 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1502139977000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
