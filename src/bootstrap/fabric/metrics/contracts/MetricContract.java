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
        public fabric.metrics.Metric get$metric() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.metric;
        }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.metric = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.Metric metric;
        
        public fabric.metrics.contracts.Bound get$bound() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.bound;
        }
        
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
                fabric.worker.transaction.TransactionManager $tm54 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff55 = 1;
                boolean $doBackoff56 = true;
                $label50: for (boolean $commit51 = false; !$commit51; ) {
                    if ($doBackoff56) {
                        if ($backoff55 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff55);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e52) {  }
                            }
                        }
                        if ($backoff55 < 5000) $backoff55 *= 2;
                    }
                    $doBackoff56 = $backoff55 <= 32 || !$doBackoff56;
                    $commit51 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (isActivated()) return; }
                    catch (final fabric.worker.RetryException $e52) {
                        $commit51 = false;
                        continue $label50;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e52) {
                        $commit51 = false;
                        fabric.common.TransactionID $currentTid53 =
                          $tm54.getCurrentTid();
                        if ($e52.tid.isDescendantOf($currentTid53))
                            continue $label50;
                        if ($currentTid53.parent != null) throw $e52;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e52) {
                        $commit51 = false;
                        if ($tm54.checkForStaleObjects()) continue $label50;
                        throw new fabric.worker.AbortException($e52);
                    }
                    finally {
                        if ($commit51) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e52) {
                                $commit51 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e52) {
                                $commit51 = false;
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
                        if (!$commit51) {  }
                    }
                }
            }
            fabric.metrics.contracts.enforcement.EnforcementPolicy startPol =
              null;
            {
                fabric.metrics.contracts.enforcement.EnforcementPolicy
                  startPol$var57 = startPol;
                fabric.worker.transaction.TransactionManager $tm62 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff63 = 1;
                boolean $doBackoff64 = true;
                $label58: for (boolean $commit59 = false; !$commit59; ) {
                    if ($doBackoff64) {
                        if ($backoff63 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff63);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e60) {  }
                            }
                        }
                        if ($backoff63 < 5000) $backoff63 *= 2;
                    }
                    $doBackoff64 = $backoff63 <= 32 || !$doBackoff64;
                    $commit59 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        startPol = this.get$metric().policy(this.get$bound());
                    }
                    catch (final fabric.worker.RetryException $e60) {
                        $commit59 = false;
                        continue $label58;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e60) {
                        $commit59 = false;
                        fabric.common.TransactionID $currentTid61 =
                          $tm62.getCurrentTid();
                        if ($e60.tid.isDescendantOf($currentTid61))
                            continue $label58;
                        if ($currentTid61.parent != null) throw $e60;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e60) {
                        $commit59 = false;
                        if ($tm62.checkForStaleObjects()) continue $label58;
                        throw new fabric.worker.AbortException($e60);
                    }
                    finally {
                        if ($commit59) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e60) {
                                $commit59 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e60) {
                                $commit59 = false;
                                fabric.common.TransactionID $currentTid61 =
                                  $tm62.getCurrentTid();
                                if ($currentTid61 != null) {
                                    if ($e60.tid.equals($currentTid61) ||
                                          !$e60.tid.isDescendantOf(
                                                      $currentTid61)) {
                                        throw $e60;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit59) { startPol = startPol$var57; }
                    }
                }
            }
            startPol.activate();
            {
                fabric.metrics.contracts.enforcement.EnforcementPolicy
                  startPol$var65 = startPol;
                fabric.worker.transaction.TransactionManager $tm70 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff71 = 1;
                boolean $doBackoff72 = true;
                $label66: for (boolean $commit67 = false; !$commit67; ) {
                    if ($doBackoff72) {
                        if ($backoff71 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff71);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e68) {  }
                            }
                        }
                        if ($backoff71 < 5000) $backoff71 *= 2;
                    }
                    $doBackoff72 = $backoff71 <= 32 || !$doBackoff72;
                    $commit67 = true;
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
                    catch (final fabric.worker.RetryException $e68) {
                        $commit67 = false;
                        continue $label66;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e68) {
                        $commit67 = false;
                        fabric.common.TransactionID $currentTid69 =
                          $tm70.getCurrentTid();
                        if ($e68.tid.isDescendantOf($currentTid69))
                            continue $label66;
                        if ($currentTid69.parent != null) throw $e68;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e68) {
                        $commit67 = false;
                        if ($tm70.checkForStaleObjects()) continue $label66;
                        throw new fabric.worker.AbortException($e68);
                    }
                    finally {
                        if ($commit67) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e68) {
                                $commit67 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e68) {
                                $commit67 = false;
                                fabric.common.TransactionID $currentTid69 =
                                  $tm70.getCurrentTid();
                                if ($currentTid69 != null) {
                                    if ($e68.tid.equals($currentTid69) ||
                                          !$e68.tid.isDescendantOf(
                                                      $currentTid69)) {
                                        throw $e68;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit67) { startPol = startPol$var65; }
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
            return getMetric().toString() +
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
                        if (!$commit74) {  }
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
    
    public static final byte[] $classHash = new byte[] { -113, 1, 57, 57, -111,
    -38, -42, 43, -103, 27, 85, -71, 21, -48, -126, 86, -27, 49, -40, -88, 37,
    110, -16, -93, -122, 81, -78, -75, 69, 17, 38, -97 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500579641000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
