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
                fabric.worker.transaction.TransactionManager $tm196 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff197 = 1;
                boolean $doBackoff198 = true;
                $label192: for (boolean $commit193 = false; !$commit193; ) {
                    if ($doBackoff198) {
                        if ($backoff197 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff197);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e194) {
                                    
                                }
                            }
                        }
                        if ($backoff197 < 5000) $backoff197 *= 1;
                    }
                    $doBackoff198 = $backoff197 <= 32 || !$doBackoff198;
                    $commit193 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (isActivated()) return; }
                    catch (final fabric.worker.RetryException $e194) {
                        $commit193 = false;
                        continue $label192;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e194) {
                        $commit193 = false;
                        fabric.common.TransactionID $currentTid195 =
                          $tm196.getCurrentTid();
                        if ($e194.tid.isDescendantOf($currentTid195))
                            continue $label192;
                        if ($currentTid195.parent != null) throw $e194;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e194) {
                        $commit193 = false;
                        if ($tm196.checkForStaleObjects()) continue $label192;
                        throw new fabric.worker.AbortException($e194);
                    }
                    finally {
                        if ($commit193) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e194) {
                                $commit193 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e194) {
                                $commit193 = false;
                                fabric.common.TransactionID $currentTid195 =
                                  $tm196.getCurrentTid();
                                if ($currentTid195 != null) {
                                    if ($e194.tid.equals($currentTid195) ||
                                          !$e194.tid.isDescendantOf(
                                                       $currentTid195)) {
                                        throw $e194;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit193) {
                            {  }
                            continue $label192;
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
                  startPol$var199 = startPol;
                fabric.worker.transaction.TransactionManager $tm204 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff205 = 1;
                boolean $doBackoff206 = true;
                $label200: for (boolean $commit201 = false; !$commit201; ) {
                    if ($doBackoff206) {
                        if ($backoff205 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff205);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e202) {
                                    
                                }
                            }
                        }
                        if ($backoff205 < 5000) $backoff205 *= 1;
                    }
                    $doBackoff206 = $backoff205 <= 32 || !$doBackoff206;
                    $commit201 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        startPol = this.get$metric().policy(this.get$bound(),
                                                            true);
                    }
                    catch (final fabric.worker.RetryException $e202) {
                        $commit201 = false;
                        continue $label200;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e202) {
                        $commit201 = false;
                        fabric.common.TransactionID $currentTid203 =
                          $tm204.getCurrentTid();
                        if ($e202.tid.isDescendantOf($currentTid203))
                            continue $label200;
                        if ($currentTid203.parent != null) throw $e202;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e202) {
                        $commit201 = false;
                        if ($tm204.checkForStaleObjects()) continue $label200;
                        throw new fabric.worker.AbortException($e202);
                    }
                    finally {
                        if ($commit201) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e202) {
                                $commit201 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e202) {
                                $commit201 = false;
                                fabric.common.TransactionID $currentTid203 =
                                  $tm204.getCurrentTid();
                                if ($currentTid203 != null) {
                                    if ($e202.tid.equals($currentTid203) ||
                                          !$e202.tid.isDescendantOf(
                                                       $currentTid203)) {
                                        throw $e202;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit201) {
                            { startPol = startPol$var199; }
                            continue $label200;
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
                fabric.worker.transaction.TransactionManager $tm211 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff212 = 1;
                boolean $doBackoff213 = true;
                $label207: for (boolean $commit208 = false; !$commit208; ) {
                    if ($doBackoff213) {
                        if ($backoff212 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff212);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e209) {
                                    
                                }
                            }
                        }
                        if ($backoff212 < 5000) $backoff212 *= 1;
                    }
                    $doBackoff213 = $backoff212 <= 32 || !$doBackoff213;
                    $commit208 = true;
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
                    catch (final fabric.worker.RetryException $e209) {
                        $commit208 = false;
                        continue $label207;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e209) {
                        $commit208 = false;
                        fabric.common.TransactionID $currentTid210 =
                          $tm211.getCurrentTid();
                        if ($e209.tid.isDescendantOf($currentTid210))
                            continue $label207;
                        if ($currentTid210.parent != null) throw $e209;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e209) {
                        $commit208 = false;
                        if ($tm211.checkForStaleObjects()) continue $label207;
                        throw new fabric.worker.AbortException($e209);
                    }
                    finally {
                        if ($commit208) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e209) {
                                $commit208 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e209) {
                                $commit208 = false;
                                fabric.common.TransactionID $currentTid210 =
                                  $tm211.getCurrentTid();
                                if ($currentTid210 != null) {
                                    if ($e209.tid.equals($currentTid210) ||
                                          !$e209.tid.isDescendantOf(
                                                       $currentTid210)) {
                                        throw $e209;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit208) {
                            {  }
                            continue $label207;
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
                fabric.worker.transaction.TransactionManager $tm218 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff219 = 1;
                boolean $doBackoff220 = true;
                $label214: for (boolean $commit215 = false; !$commit215; ) {
                    if ($doBackoff220) {
                        if ($backoff219 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff219);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e216) {
                                    
                                }
                            }
                        }
                        if ($backoff219 < 5000) $backoff219 *= 1;
                    }
                    $doBackoff220 = $backoff219 <= 32 || !$doBackoff220;
                    $commit215 = true;
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
                    catch (final fabric.worker.RetryException $e216) {
                        $commit215 = false;
                        continue $label214;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e216) {
                        $commit215 = false;
                        fabric.common.TransactionID $currentTid217 =
                          $tm218.getCurrentTid();
                        if ($e216.tid.isDescendantOf($currentTid217))
                            continue $label214;
                        if ($currentTid217.parent != null) throw $e216;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e216) {
                        $commit215 = false;
                        if ($tm218.checkForStaleObjects()) continue $label214;
                        throw new fabric.worker.AbortException($e216);
                    }
                    finally {
                        if ($commit215) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e216) {
                                $commit215 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e216) {
                                $commit215 = false;
                                fabric.common.TransactionID $currentTid217 =
                                  $tm218.getCurrentTid();
                                if ($currentTid217 != null) {
                                    if ($e216.tid.equals($currentTid217) ||
                                          !$e216.tid.isDescendantOf(
                                                       $currentTid217)) {
                                        throw $e216;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit215) {
                            {  }
                            continue $label214;
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
    
    public static final byte[] $classHash = new byte[] { 16, 47, 62, 15, 84,
    -40, -10, 19, -113, -119, 14, -10, -7, 5, -47, -96, 30, 84, -116, 78, 99,
    -108, -29, -31, 95, 40, -14, 105, -66, -9, 122, 73 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506965790000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
