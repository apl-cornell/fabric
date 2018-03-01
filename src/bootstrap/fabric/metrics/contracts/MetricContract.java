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
    
    public static final byte[] $classHash = new byte[] { -10, -91, -88, 4, 110,
    88, 123, -57, -50, 117, 63, 66, 38, -88, 13, -47, 30, -26, -74, -104, 43,
    -67, 98, -74, 113, -108, 5, 52, 86, 92, 61, -16 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519623915000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
