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
    
    public boolean implies(fabric.metrics.Metric otherMetric, double otherRate,
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
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
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
                    fabric.worker.transaction.TransactionManager $tm456 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled459 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff457 = 1;
                    boolean $doBackoff458 = true;
                    boolean $retry453 = true;
                    $label451: for (boolean $commit452 = false; !$commit452; ) {
                        if ($backoffEnabled459) {
                            if ($doBackoff458) {
                                if ($backoff457 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff457);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e454) {
                                            
                                        }
                                    }
                                }
                                if ($backoff457 < 5000) $backoff457 *= 2;
                            }
                            $doBackoff458 = $backoff457 <= 32 || !$doBackoff458;
                        }
                        $commit452 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e454) {
                            $commit452 = false;
                            continue $label451;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e454) {
                            $commit452 = false;
                            fabric.common.TransactionID $currentTid455 =
                              $tm456.getCurrentTid();
                            if ($e454.tid.isDescendantOf($currentTid455))
                                continue $label451;
                            if ($currentTid455.parent != null) {
                                $retry453 = false;
                                throw $e454;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e454) {
                            $commit452 = false;
                            if ($tm456.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid455 =
                              $tm456.getCurrentTid();
                            if ($e454.tid.isDescendantOf($currentTid455)) {
                                $retry453 = true;
                            }
                            else if ($currentTid455.parent != null) {
                                $retry453 = false;
                                throw $e454;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e454) {
                            $commit452 = false;
                            if ($tm456.checkForStaleObjects())
                                continue $label451;
                            $retry453 = false;
                            throw new fabric.worker.AbortException($e454);
                        }
                        finally {
                            if ($commit452) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e454) {
                                    $commit452 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e454) {
                                    $commit452 = false;
                                    fabric.common.TransactionID $currentTid455 =
                                      $tm456.getCurrentTid();
                                    if ($currentTid455 != null) {
                                        if ($e454.tid.equals($currentTid455) ||
                                              !$e454.tid.isDescendantOf(
                                                           $currentTid455)) {
                                            throw $e454;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit452 && $retry453) {
                                {  }
                                continue $label451;
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
                                                   tmp.get$base(), false,
                                                   tmp.$getStore());
            }
            else {
                {
                    fabric.metrics.contracts.enforcement.EnforcementPolicy
                      startPol$var460 = startPol;
                    fabric.worker.transaction.TransactionManager $tm466 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled469 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff467 = 1;
                    boolean $doBackoff468 = true;
                    boolean $retry463 = true;
                    $label461: for (boolean $commit462 = false; !$commit462; ) {
                        if ($backoffEnabled469) {
                            if ($doBackoff468) {
                                if ($backoff467 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff467);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e464) {
                                            
                                        }
                                    }
                                }
                                if ($backoff467 < 5000) $backoff467 *= 2;
                            }
                            $doBackoff468 = $backoff467 <= 32 || !$doBackoff468;
                        }
                        $commit462 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            startPol = tmp.get$metric().policy(tmp.get$rate(),
                                                               tmp.get$base(),
                                                               true,
                                                               tmp.$getStore());
                        }
                        catch (final fabric.worker.RetryException $e464) {
                            $commit462 = false;
                            continue $label461;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e464) {
                            $commit462 = false;
                            fabric.common.TransactionID $currentTid465 =
                              $tm466.getCurrentTid();
                            if ($e464.tid.isDescendantOf($currentTid465))
                                continue $label461;
                            if ($currentTid465.parent != null) {
                                $retry463 = false;
                                throw $e464;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e464) {
                            $commit462 = false;
                            if ($tm466.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid465 =
                              $tm466.getCurrentTid();
                            if ($e464.tid.isDescendantOf($currentTid465)) {
                                $retry463 = true;
                            }
                            else if ($currentTid465.parent != null) {
                                $retry463 = false;
                                throw $e464;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e464) {
                            $commit462 = false;
                            if ($tm466.checkForStaleObjects())
                                continue $label461;
                            $retry463 = false;
                            throw new fabric.worker.AbortException($e464);
                        }
                        finally {
                            if ($commit462) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e464) {
                                    $commit462 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e464) {
                                    $commit462 = false;
                                    fabric.common.TransactionID $currentTid465 =
                                      $tm466.getCurrentTid();
                                    if ($currentTid465 != null) {
                                        if ($e464.tid.equals($currentTid465) ||
                                              !$e464.tid.isDescendantOf(
                                                           $currentTid465)) {
                                            throw $e464;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit462 && $retry463) {
                                { startPol = startPol$var460; }
                                continue $label461;
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
                    fabric.worker.transaction.TransactionManager $tm475 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled478 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff476 = 1;
                    boolean $doBackoff477 = true;
                    boolean $retry472 = true;
                    $label470: for (boolean $commit471 = false; !$commit471; ) {
                        if ($backoffEnabled478) {
                            if ($doBackoff477) {
                                if ($backoff476 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff476);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e473) {
                                            
                                        }
                                    }
                                }
                                if ($backoff476 < 5000) $backoff476 *= 2;
                            }
                            $doBackoff477 = $backoff476 <= 32 || !$doBackoff477;
                        }
                        $commit471 = true;
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
                        catch (final fabric.worker.RetryException $e473) {
                            $commit471 = false;
                            continue $label470;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e473) {
                            $commit471 = false;
                            fabric.common.TransactionID $currentTid474 =
                              $tm475.getCurrentTid();
                            if ($e473.tid.isDescendantOf($currentTid474))
                                continue $label470;
                            if ($currentTid474.parent != null) {
                                $retry472 = false;
                                throw $e473;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e473) {
                            $commit471 = false;
                            if ($tm475.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid474 =
                              $tm475.getCurrentTid();
                            if ($e473.tid.isDescendantOf($currentTid474)) {
                                $retry472 = true;
                            }
                            else if ($currentTid474.parent != null) {
                                $retry472 = false;
                                throw $e473;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e473) {
                            $commit471 = false;
                            if ($tm475.checkForStaleObjects())
                                continue $label470;
                            $retry472 = false;
                            throw new fabric.worker.AbortException($e473);
                        }
                        finally {
                            if ($commit471) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e473) {
                                    $commit471 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e473) {
                                    $commit471 = false;
                                    fabric.common.TransactionID $currentTid474 =
                                      $tm475.getCurrentTid();
                                    if ($currentTid474 != null) {
                                        if ($e473.tid.equals($currentTid474) ||
                                              !$e473.tid.isDescendantOf(
                                                           $currentTid474)) {
                                            throw $e473;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit471 && $retry472) {
                                {  }
                                continue $label470;
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
            acquireReconfigLocks();
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
        
        public boolean implies(fabric.metrics.Metric otherMetric,
                               double otherRate, double otherBase) {
            if (!getMetric().equals(otherMetric) || this.get$rate() <
                  otherRate || this.get$base() < otherBase)
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
                return fabric.lang.arrays.internal.Compat.
                  convert(
                    this.$getStore(),
                    this.get$$updateLabel(),
                    this.get$$updateLabel().confPolicy(),
                    new fabric.lang.Object[] { (fabric.metrics.SampledMetric)
                                                 fabric.lang.Object._Proxy.
                                                 $getProxy(m) });
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
                    fabric.worker.transaction.TransactionManager $tm484 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled487 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff485 = 1;
                    boolean $doBackoff486 = true;
                    boolean $retry481 = true;
                    $label479: for (boolean $commit480 = false; !$commit480; ) {
                        if ($backoffEnabled487) {
                            if ($doBackoff486) {
                                if ($backoff485 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff485);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e482) {
                                            
                                        }
                                    }
                                }
                                if ($backoff485 < 5000) $backoff485 *= 2;
                            }
                            $doBackoff486 = $backoff485 <= 32 || !$doBackoff486;
                        }
                        $commit480 = true;
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
                        catch (final fabric.worker.RetryException $e482) {
                            $commit480 = false;
                            continue $label479;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e482) {
                            $commit480 = false;
                            fabric.common.TransactionID $currentTid483 =
                              $tm484.getCurrentTid();
                            if ($e482.tid.isDescendantOf($currentTid483))
                                continue $label479;
                            if ($currentTid483.parent != null) {
                                $retry481 = false;
                                throw $e482;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e482) {
                            $commit480 = false;
                            if ($tm484.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid483 =
                              $tm484.getCurrentTid();
                            if ($e482.tid.isDescendantOf($currentTid483)) {
                                $retry481 = true;
                            }
                            else if ($currentTid483.parent != null) {
                                $retry481 = false;
                                throw $e482;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e482) {
                            $commit480 = false;
                            if ($tm484.checkForStaleObjects())
                                continue $label479;
                            $retry481 = false;
                            throw new fabric.worker.AbortException($e482);
                        }
                        finally {
                            if ($commit480) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e482) {
                                    $commit480 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e482) {
                                    $commit480 = false;
                                    fabric.common.TransactionID $currentTid483 =
                                      $tm484.getCurrentTid();
                                    if ($currentTid483 != null) {
                                        if ($e482.tid.equals($currentTid483) ||
                                              !$e482.tid.isDescendantOf(
                                                           $currentTid483)) {
                                            throw $e482;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit480 && $retry481) {
                                {  }
                                continue $label479;
                            }
                        }
                    }
                }
            }
        }
        
        public void acquireReconfigLocks() {
            this.get$lock().acquire();
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null))
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
    
    public static final byte[] $classHash = new byte[] { -32, -100, 126, 29,
    -49, 106, 56, 20, -70, 127, 31, 87, -23, 121, 51, -84, 25, 103, -77, -96,
    16, 113, 110, -91, -81, 82, -128, -42, 29, -95, -105, 45 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519593048000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
