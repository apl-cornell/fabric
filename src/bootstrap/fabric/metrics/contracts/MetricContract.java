package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.Set;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.DerivedMetric;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
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
   * {@link MetricContract} being considered.
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
    
    /**
   * Given the current transaction context's view of the system state, return
   * a new {@link EnforcementPolicy} to enforce this {@link MetricContract}.
   *
   * @return The {@link EnforcementPolicy} to use for this
   *       {@link MetricContract} after the call completes.
   */
    public abstract fabric.metrics.contracts.enforcement.EnforcementPolicy
      enforcementStrategy();
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      directStrategy();
    
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
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          enforcementStrategy() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              enforcementStrategy();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          directStrategy() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              directStrategy();
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
    
    public abstract static class _Impl
    extends fabric.metrics.contracts.Contract._Impl
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
            return getMetric().expectedTimeToHit(
                                 getBound(),
                                 java.lang.System.currentTimeMillis());
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
                        fabric.common.Logging.METRICS_LOGGER.
                          fine("ASDF POLICY FOR MC " + $getOnum());
                        if (this.get$metric().isSingleStore()) {
                            startPol = directStrategy();
                        } else {
                            startPol = enforcementStrategy();
                        }
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
            fabric.metrics.contracts.enforcement.EnforcementPolicy oldPolicy =
              this.get$currentPolicy();
            if (this.get$bound().test(this.get$metric(), currentTime)) {
                this.get$currentPolicy().refresh();
                if (this.get$currentPolicy().expiry() > currentTime) {
                    fabric.common.Logging.METRICS_LOGGER.
                      log(java.util.logging.Level.FINE, "CONTRACT REFRESHED");
                    return update(this.get$currentPolicy().expiry());
                }
                fabric.metrics.contracts.enforcement.EnforcementPolicy
                  newPolicy;
                if (this.get$metric().isSingleStore()) {
                    newPolicy = directStrategy();
                } else {
                    newPolicy = enforcementStrategy();
                }
                if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null) &&
                      !oldPolicy.equals(newPolicy))
                    oldPolicy.unapply((fabric.metrics.contracts.MetricContract)
                                        this.$getProxy());
                this.set$currentPolicy(newPolicy);
                this.get$currentPolicy().
                  apply((fabric.metrics.contracts.MetricContract)
                          this.$getProxy());
                fabric.common.Logging.METRICS_LOGGER.
                  log(java.util.logging.Level.FINE, "CONTRACT TRUE");
                return update(this.get$currentPolicy().expiry());
            }
            else {
                fabric.common.Logging.METRICS_LOGGER.
                  log(java.util.logging.Level.FINE, "CONTRACT FALSE");
                if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null))
                    oldPolicy.unapply((fabric.metrics.contracts.MetricContract)
                                        this.$getProxy());
                return update(0);
            }
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
   * {@link MetricContract} being considered.
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
            return ((java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(getMetric())).
              toString() +
            " " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(this.get$bound())) +
            " until " +
            getExpiry();
        }
        
        /**
   * Given the current transaction context's view of the system state, return
   * a new {@link EnforcementPolicy} to enforce this {@link MetricContract}.
   *
   * @return The {@link EnforcementPolicy} to use for this
   *       {@link MetricContract} after the call completes.
   */
        public abstract fabric.metrics.contracts.enforcement.EnforcementPolicy
          enforcementStrategy();
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          directStrategy() {
            final fabric.worker.Store s = getStore();
            return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                      new fabric.metrics.contracts.enforcement.DirectPolicy.
                        _Impl(s).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$DirectPolicy$(
                (fabric.metrics.contracts.MetricContract) this.$getProxy());
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
    
    public static final byte[] $classHash = new byte[] { -88, -63, -56, -85, 91,
    -27, -29, -58, 18, 3, 57, 104, 57, 68, 54, -100, 60, 13, 26, 118, 122, -32,
    32, 105, 38, -2, -126, 86, -112, -126, 60, -40 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492660216000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
