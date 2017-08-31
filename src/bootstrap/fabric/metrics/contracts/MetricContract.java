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
            {
                fabric.worker.transaction.TransactionManager $tm352 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff353 = 1;
                boolean $doBackoff354 = true;
                $label348: for (boolean $commit349 = false; !$commit349; ) {
                    if ($doBackoff354) {
                        if ($backoff353 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff353);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e350) {
                                    
                                }
                            }
                        }
                        if ($backoff353 < 5000) $backoff353 *= 1;
                    }
                    $doBackoff354 = $backoff353 <= 32 || !$doBackoff354;
                    $commit349 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (isActivated()) return; }
                    catch (final fabric.worker.RetryException $e350) {
                        $commit349 = false;
                        continue $label348;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e350) {
                        $commit349 = false;
                        fabric.common.TransactionID $currentTid351 =
                          $tm352.getCurrentTid();
                        if ($e350.tid.isDescendantOf($currentTid351))
                            continue $label348;
                        if ($currentTid351.parent != null) throw $e350;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e350) {
                        $commit349 = false;
                        if ($tm352.checkForStaleObjects()) continue $label348;
                        throw new fabric.worker.AbortException($e350);
                    }
                    finally {
                        if ($commit349) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e350) {
                                $commit349 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e350) {
                                $commit349 = false;
                                fabric.common.TransactionID $currentTid351 =
                                  $tm352.getCurrentTid();
                                if ($currentTid351 != null) {
                                    if ($e350.tid.equals($currentTid351) ||
                                          !$e350.tid.isDescendantOf(
                                                       $currentTid351)) {
                                        throw $e350;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit349) {
                            {  }
                            continue $label348;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.fine(
                                                   "CREATING A POLICY FOR MC " +
                                                     $getOnum());
            fabric.metrics.contracts.enforcement.EnforcementPolicy startPol =
              null;
            {
                fabric.metrics.contracts.enforcement.EnforcementPolicy
                  startPol$var355 = startPol;
                fabric.worker.transaction.TransactionManager $tm360 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff361 = 1;
                boolean $doBackoff362 = true;
                $label356: for (boolean $commit357 = false; !$commit357; ) {
                    if ($doBackoff362) {
                        if ($backoff361 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff361);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e358) {
                                    
                                }
                            }
                        }
                        if ($backoff361 < 5000) $backoff361 *= 1;
                    }
                    $doBackoff362 = $backoff361 <= 32 || !$doBackoff362;
                    $commit357 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        startPol = this.get$metric().policy(this.get$bound(),
                                                            true);
                    }
                    catch (final fabric.worker.RetryException $e358) {
                        $commit357 = false;
                        continue $label356;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e358) {
                        $commit357 = false;
                        fabric.common.TransactionID $currentTid359 =
                          $tm360.getCurrentTid();
                        if ($e358.tid.isDescendantOf($currentTid359))
                            continue $label356;
                        if ($currentTid359.parent != null) throw $e358;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e358) {
                        $commit357 = false;
                        if ($tm360.checkForStaleObjects()) continue $label356;
                        throw new fabric.worker.AbortException($e358);
                    }
                    finally {
                        if ($commit357) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e358) {
                                $commit357 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e358) {
                                $commit357 = false;
                                fabric.common.TransactionID $currentTid359 =
                                  $tm360.getCurrentTid();
                                if ($currentTid359 != null) {
                                    if ($e358.tid.equals($currentTid359) ||
                                          !$e358.tid.isDescendantOf(
                                                       $currentTid359)) {
                                        throw $e358;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit357) {
                            { startPol = startPol$var355; }
                            continue $label356;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.fine(
                                                   "ACTIVATING POLICY FOR MC " +
                                                     $getOnum());
            startPol.activate();
            if (fabric.lang.Object._Proxy.
                  idEquals(
                    fabric.worker.transaction.TransactionManager.getInstance().
                        getCurrentLog(),
                    null)) {
                fabric.worker.remote.RemoteWorker w =
                  fabric.worker.Worker.getWorker().getWorker(
                                                     $getStore().name());
                finishActivating(startPol);
            }
            else {
                finishActivating(startPol);
            }
            fabric.common.Logging.METRICS_LOGGER.fine("ACTIVATED MC " +
                                                        $getOnum());
        }
        
        public void finishActivating(
          fabric.metrics.contracts.enforcement.EnforcementPolicy p) {
            {
                fabric.worker.transaction.TransactionManager $tm367 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff368 = 1;
                boolean $doBackoff369 = true;
                $label363: for (boolean $commit364 = false; !$commit364; ) {
                    if ($doBackoff369) {
                        if ($backoff368 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff368);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e365) {
                                    
                                }
                            }
                        }
                        if ($backoff368 < 5000) $backoff368 *= 1;
                    }
                    $doBackoff369 = $backoff368 <= 32 || !$doBackoff369;
                    $commit364 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.common.Logging.METRICS_LOGGER.
                          fine("ACTIVATING MC " + $getOnum());
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
                    catch (final fabric.worker.RetryException $e365) {
                        $commit364 = false;
                        continue $label363;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e365) {
                        $commit364 = false;
                        fabric.common.TransactionID $currentTid366 =
                          $tm367.getCurrentTid();
                        if ($e365.tid.isDescendantOf($currentTid366))
                            continue $label363;
                        if ($currentTid366.parent != null) throw $e365;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e365) {
                        $commit364 = false;
                        if ($tm367.checkForStaleObjects()) continue $label363;
                        throw new fabric.worker.AbortException($e365);
                    }
                    finally {
                        if ($commit364) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e365) {
                                $commit364 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e365) {
                                $commit364 = false;
                                fabric.common.TransactionID $currentTid366 =
                                  $tm367.getCurrentTid();
                                if ($currentTid366 != null) {
                                    if ($e365.tid.equals($currentTid366) ||
                                          !$e365.tid.isDescendantOf(
                                                       $currentTid366)) {
                                        throw $e365;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit364) {
                            {  }
                            continue $label363;
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
        public boolean refresh() {
            if (!isActivated()) {
                fabric.common.Logging.METRICS_LOGGER.
                  log(java.util.logging.Level.FINE, "CONTRACT INACTIVE");
                return false;
            }
            long currentTime = java.lang.System.currentTimeMillis();
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null)) {
                if (this.get$currentPolicy().expiry() >= currentTime) {
                    boolean result = update(this.get$currentPolicy().expiry());
                    fabric.common.Logging.METRICS_LOGGER.
                      log(
                        java.util.logging.Level.FINE,
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
            boolean result = update(this.get$currentPolicy().expiry());
            if (this.get$currentPolicy().expiry() >= currentTime)
                this.get$currentPolicy().
                  apply((fabric.metrics.contracts.MetricContract)
                          this.$getProxy());
            fabric.common.Logging.METRICS_LOGGER.log(
                                                   java.util.logging.Level.FINE,
                                                   "CONTRACT TRUE");
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
                fabric.worker.transaction.TransactionManager $tm374 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff375 = 1;
                boolean $doBackoff376 = true;
                $label370: for (boolean $commit371 = false; !$commit371; ) {
                    if ($doBackoff376) {
                        if ($backoff375 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff375);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e372) {
                                    
                                }
                            }
                        }
                        if ($backoff375 < 5000) $backoff375 *= 1;
                    }
                    $doBackoff376 = $backoff375 <= 32 || !$doBackoff376;
                    $commit371 = true;
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
                    catch (final fabric.worker.RetryException $e372) {
                        $commit371 = false;
                        continue $label370;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e372) {
                        $commit371 = false;
                        fabric.common.TransactionID $currentTid373 =
                          $tm374.getCurrentTid();
                        if ($e372.tid.isDescendantOf($currentTid373))
                            continue $label370;
                        if ($currentTid373.parent != null) throw $e372;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e372) {
                        $commit371 = false;
                        if ($tm374.checkForStaleObjects()) continue $label370;
                        throw new fabric.worker.AbortException($e372);
                    }
                    finally {
                        if ($commit371) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e372) {
                                $commit371 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e372) {
                                $commit371 = false;
                                fabric.common.TransactionID $currentTid373 =
                                  $tm374.getCurrentTid();
                                if ($currentTid373 != null) {
                                    if ($e372.tid.equals($currentTid373) ||
                                          !$e372.tid.isDescendantOf(
                                                       $currentTid373)) {
                                        throw $e372;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit371) {
                            {  }
                            continue $label370;
                        }
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
    
    public static final byte[] $classHash = new byte[] { 19, -92, -50, -18, 16,
    109, 79, 90, 15, -23, 124, 44, -30, -66, -9, -89, -5, 111, 101, -34, -28,
    77, -63, -71, 98, 121, 5, -41, 23, 108, -49, 65 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1504028847000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
