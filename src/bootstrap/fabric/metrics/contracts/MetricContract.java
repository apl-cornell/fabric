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
    public boolean refresh(boolean force);
    
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
    
    public void deactivate();
    
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
                    try { if (isActive()) return; }
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
                        this.get$currentPolicy().
                          apply((fabric.metrics.contracts.MetricContract)
                                  this.$getProxy());
                        getMetric().addContract(
                                      (fabric.metrics.contracts.MetricContract)
                                        this.$getProxy());
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
        public boolean refresh(boolean force) {
            if (!isActive()) {
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
            this.get$currentPolicy().apply(
                                       (fabric.metrics.contracts.MetricContract)
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
            return valid(java.lang.System.currentTimeMillis());
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
            return valid(java.lang.System.currentTimeMillis());
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
        
        public void deactivate() {
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
                        if (!isObserved()) {
                            if (!fabric.lang.Object._Proxy.
                                  idEquals(this.get$currentPolicy(), null))
                                this.get$currentPolicy().
                                  unapply(
                                    (fabric.metrics.contracts.MetricContract)
                                      this.$getProxy());
                        }
                        super.deactivate();
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
    
    public static final byte[] $classHash = new byte[] { -111, -68, 33, 24, -37,
    -91, -49, 44, 88, -7, -126, -75, -79, 3, 53, -77, 66, 59, -69, 35, 113, 2,
    56, -26, -30, 124, 65, -42, -29, -39, -107, 105 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1495740956000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
