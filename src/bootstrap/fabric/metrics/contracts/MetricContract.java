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
                fabric.worker.transaction.TransactionManager $tm220 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled223 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff221 = 1;
                boolean $doBackoff222 = true;
                $label216: for (boolean $commit217 = false; !$commit217; ) {
                    if ($backoffEnabled223) {
                        if ($doBackoff222) {
                            if ($backoff221 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff221);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e218) {
                                        
                                    }
                                }
                            }
                            if ($backoff221 < 5000) $backoff221 *= 2;
                        }
                        $doBackoff222 = $backoff221 <= 32 || !$doBackoff222;
                    }
                    $commit217 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (isActivated()) return; }
                    catch (final fabric.worker.RetryException $e218) {
                        $commit217 = false;
                        continue $label216;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e218) {
                        $commit217 = false;
                        fabric.common.TransactionID $currentTid219 =
                          $tm220.getCurrentTid();
                        if ($e218.tid.isDescendantOf($currentTid219))
                            continue $label216;
                        if ($currentTid219.parent != null) throw $e218;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e218) {
                        $commit217 = false;
                        if ($tm220.checkForStaleObjects()) continue $label216;
                        throw new fabric.worker.AbortException($e218);
                    }
                    finally {
                        if ($commit217) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e218) {
                                $commit217 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e218) {
                                $commit217 = false;
                                fabric.common.TransactionID $currentTid219 =
                                  $tm220.getCurrentTid();
                                if ($currentTid219 != null) {
                                    if ($e218.tid.equals($currentTid219) ||
                                          !$e218.tid.isDescendantOf(
                                                       $currentTid219)) {
                                        throw $e218;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit217) {
                            {  }
                            continue $label216;
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
                  startPol$var224 = startPol;
                fabric.worker.transaction.TransactionManager $tm229 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled232 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff230 = 1;
                boolean $doBackoff231 = true;
                $label225: for (boolean $commit226 = false; !$commit226; ) {
                    if ($backoffEnabled232) {
                        if ($doBackoff231) {
                            if ($backoff230 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff230);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e227) {
                                        
                                    }
                                }
                            }
                            if ($backoff230 < 5000) $backoff230 *= 2;
                        }
                        $doBackoff231 = $backoff230 <= 32 || !$doBackoff231;
                    }
                    $commit226 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        startPol = this.get$metric().policy(this.get$bound(),
                                                            true);
                    }
                    catch (final fabric.worker.RetryException $e227) {
                        $commit226 = false;
                        continue $label225;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e227) {
                        $commit226 = false;
                        fabric.common.TransactionID $currentTid228 =
                          $tm229.getCurrentTid();
                        if ($e227.tid.isDescendantOf($currentTid228))
                            continue $label225;
                        if ($currentTid228.parent != null) throw $e227;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e227) {
                        $commit226 = false;
                        if ($tm229.checkForStaleObjects()) continue $label225;
                        throw new fabric.worker.AbortException($e227);
                    }
                    finally {
                        if ($commit226) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e227) {
                                $commit226 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e227) {
                                $commit226 = false;
                                fabric.common.TransactionID $currentTid228 =
                                  $tm229.getCurrentTid();
                                if ($currentTid228 != null) {
                                    if ($e227.tid.equals($currentTid228) ||
                                          !$e227.tid.isDescendantOf(
                                                       $currentTid228)) {
                                        throw $e227;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit226) {
                            { startPol = startPol$var224; }
                            continue $label225;
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
                fabric.worker.transaction.TransactionManager $tm237 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled240 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff238 = 1;
                boolean $doBackoff239 = true;
                $label233: for (boolean $commit234 = false; !$commit234; ) {
                    if ($backoffEnabled240) {
                        if ($doBackoff239) {
                            if ($backoff238 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff238);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e235) {
                                        
                                    }
                                }
                            }
                            if ($backoff238 < 5000) $backoff238 *= 2;
                        }
                        $doBackoff239 = $backoff238 <= 32 || !$doBackoff239;
                    }
                    $commit234 = true;
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
                    catch (final fabric.worker.RetryException $e235) {
                        $commit234 = false;
                        continue $label233;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e235) {
                        $commit234 = false;
                        fabric.common.TransactionID $currentTid236 =
                          $tm237.getCurrentTid();
                        if ($e235.tid.isDescendantOf($currentTid236))
                            continue $label233;
                        if ($currentTid236.parent != null) throw $e235;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e235) {
                        $commit234 = false;
                        if ($tm237.checkForStaleObjects()) continue $label233;
                        throw new fabric.worker.AbortException($e235);
                    }
                    finally {
                        if ($commit234) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e235) {
                                $commit234 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e235) {
                                $commit234 = false;
                                fabric.common.TransactionID $currentTid236 =
                                  $tm237.getCurrentTid();
                                if ($currentTid236 != null) {
                                    if ($e235.tid.equals($currentTid236) ||
                                          !$e235.tid.isDescendantOf(
                                                       $currentTid236)) {
                                        throw $e235;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit234) {
                            {  }
                            continue $label233;
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
                fabric.worker.transaction.TransactionManager $tm245 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled248 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff246 = 1;
                boolean $doBackoff247 = true;
                $label241: for (boolean $commit242 = false; !$commit242; ) {
                    if ($backoffEnabled248) {
                        if ($doBackoff247) {
                            if ($backoff246 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff246);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e243) {
                                        
                                    }
                                }
                            }
                            if ($backoff246 < 5000) $backoff246 *= 2;
                        }
                        $doBackoff247 = $backoff246 <= 32 || !$doBackoff247;
                    }
                    $commit242 = true;
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
                    catch (final fabric.worker.RetryException $e243) {
                        $commit242 = false;
                        continue $label241;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e243) {
                        $commit242 = false;
                        fabric.common.TransactionID $currentTid244 =
                          $tm245.getCurrentTid();
                        if ($e243.tid.isDescendantOf($currentTid244))
                            continue $label241;
                        if ($currentTid244.parent != null) throw $e243;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e243) {
                        $commit242 = false;
                        if ($tm245.checkForStaleObjects()) continue $label241;
                        throw new fabric.worker.AbortException($e243);
                    }
                    finally {
                        if ($commit242) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e243) {
                                $commit242 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e243) {
                                $commit242 = false;
                                fabric.common.TransactionID $currentTid244 =
                                  $tm245.getCurrentTid();
                                if ($currentTid244 != null) {
                                    if ($e243.tid.equals($currentTid244) ||
                                          !$e243.tid.isDescendantOf(
                                                       $currentTid244)) {
                                        throw $e243;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit242) {
                            {  }
                            continue $label241;
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
    
    public static final byte[] $classHash = new byte[] { 75, 92, 108, -14, 75,
    117, 0, 47, -59, 48, -1, 77, -3, -80, -33, 67, -106, 23, 105, 82, 22, 48,
    32, 67, -79, 54, 98, -32, 63, -105, -122, 31 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507056003000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
