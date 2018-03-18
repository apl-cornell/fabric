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
                        try { if (tmp.isActivated()) return; }
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
                                {  }
                                continue $label461;
                            }
                        }
                    }
                }
            }
            fabric.metrics.contracts.enforcement.EnforcementPolicy startPol =
              null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                startPol = tmp.get$metric().thresholdPolicy(tmp.get$rate(),
                                                            tmp.get$base(),
                                                            false,
                                                            tmp.$getStore());
            }
            else {
                {
                    fabric.metrics.contracts.enforcement.EnforcementPolicy
                      startPol$var470 = startPol;
                    fabric.worker.transaction.TransactionManager $tm476 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled479 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff477 = 1;
                    boolean $doBackoff478 = true;
                    boolean $retry473 = true;
                    $label471: for (boolean $commit472 = false; !$commit472; ) {
                        if ($backoffEnabled479) {
                            if ($doBackoff478) {
                                if ($backoff477 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff477);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e474) {
                                            
                                        }
                                    }
                                }
                                if ($backoff477 < 5000) $backoff477 *= 2;
                            }
                            $doBackoff478 = $backoff477 <= 32 || !$doBackoff478;
                        }
                        $commit472 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            startPol =
                              tmp.get$metric().thresholdPolicy(tmp.get$rate(),
                                                               tmp.get$base(),
                                                               true,
                                                               tmp.$getStore());
                        }
                        catch (final fabric.worker.RetryException $e474) {
                            $commit472 = false;
                            continue $label471;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e474) {
                            $commit472 = false;
                            fabric.common.TransactionID $currentTid475 =
                              $tm476.getCurrentTid();
                            if ($e474.tid.isDescendantOf($currentTid475))
                                continue $label471;
                            if ($currentTid475.parent != null) {
                                $retry473 = false;
                                throw $e474;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e474) {
                            $commit472 = false;
                            if ($tm476.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid475 =
                              $tm476.getCurrentTid();
                            if ($e474.tid.isDescendantOf($currentTid475)) {
                                $retry473 = true;
                            }
                            else if ($currentTid475.parent != null) {
                                $retry473 = false;
                                throw $e474;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e474) {
                            $commit472 = false;
                            if ($tm476.checkForStaleObjects())
                                continue $label471;
                            $retry473 = false;
                            throw new fabric.worker.AbortException($e474);
                        }
                        finally {
                            if ($commit472) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e474) {
                                    $commit472 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e474) {
                                    $commit472 = false;
                                    fabric.common.TransactionID $currentTid475 =
                                      $tm476.getCurrentTid();
                                    if ($currentTid475 != null) {
                                        if ($e474.tid.equals($currentTid475) ||
                                              !$e474.tid.isDescendantOf(
                                                           $currentTid475)) {
                                            throw $e474;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit472 && $retry473) {
                                { startPol = startPol$var470; }
                                continue $label471;
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
                    fabric.worker.transaction.TransactionManager $tm485 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled488 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff486 = 1;
                    boolean $doBackoff487 = true;
                    boolean $retry482 = true;
                    $label480: for (boolean $commit481 = false; !$commit481; ) {
                        if ($backoffEnabled488) {
                            if ($doBackoff487) {
                                if ($backoff486 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff486);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e483) {
                                            
                                        }
                                    }
                                }
                                if ($backoff486 < 5000) $backoff486 *= 2;
                            }
                            $doBackoff487 = $backoff486 <= 32 || !$doBackoff487;
                        }
                        $commit481 = true;
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
                        catch (final fabric.worker.RetryException $e483) {
                            $commit481 = false;
                            continue $label480;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e483) {
                            $commit481 = false;
                            fabric.common.TransactionID $currentTid484 =
                              $tm485.getCurrentTid();
                            if ($e483.tid.isDescendantOf($currentTid484))
                                continue $label480;
                            if ($currentTid484.parent != null) {
                                $retry482 = false;
                                throw $e483;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e483) {
                            $commit481 = false;
                            if ($tm485.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid484 =
                              $tm485.getCurrentTid();
                            if ($e483.tid.isDescendantOf($currentTid484)) {
                                $retry482 = true;
                            }
                            else if ($currentTid484.parent != null) {
                                $retry482 = false;
                                throw $e483;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e483) {
                            $commit481 = false;
                            if ($tm485.checkForStaleObjects())
                                continue $label480;
                            $retry482 = false;
                            throw new fabric.worker.AbortException($e483);
                        }
                        finally {
                            if ($commit481) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e483) {
                                    $commit481 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e483) {
                                    $commit481 = false;
                                    fabric.common.TransactionID $currentTid484 =
                                      $tm485.getCurrentTid();
                                    if ($currentTid484 != null) {
                                        if ($e483.tid.equals($currentTid484) ||
                                              !$e483.tid.isDescendantOf(
                                                           $currentTid484)) {
                                            throw $e483;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit481 && $retry482) {
                                {  }
                                continue $label480;
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
              this.get$metric().thresholdPolicy(this.get$rate(),
                                                this.get$base(), $getStore());
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
                final fabric.worker.Store local =
                  fabric.worker.Worker.getWorker().getLocalStore();
                return fabric.lang.arrays.internal.Compat.
                  convert(
                    local,
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
                    fabric.worker.transaction.TransactionManager $tm494 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled497 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff495 = 1;
                    boolean $doBackoff496 = true;
                    boolean $retry491 = true;
                    $label489: for (boolean $commit490 = false; !$commit490; ) {
                        if ($backoffEnabled497) {
                            if ($doBackoff496) {
                                if ($backoff495 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff495);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e492) {
                                            
                                        }
                                    }
                                }
                                if ($backoff495 < 5000) $backoff495 *= 2;
                            }
                            $doBackoff496 = $backoff495 <= 32 || !$doBackoff496;
                        }
                        $commit490 = true;
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
                        catch (final fabric.worker.RetryException $e492) {
                            $commit490 = false;
                            continue $label489;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e492) {
                            $commit490 = false;
                            fabric.common.TransactionID $currentTid493 =
                              $tm494.getCurrentTid();
                            if ($e492.tid.isDescendantOf($currentTid493))
                                continue $label489;
                            if ($currentTid493.parent != null) {
                                $retry491 = false;
                                throw $e492;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e492) {
                            $commit490 = false;
                            if ($tm494.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid493 =
                              $tm494.getCurrentTid();
                            if ($e492.tid.isDescendantOf($currentTid493)) {
                                $retry491 = true;
                            }
                            else if ($currentTid493.parent != null) {
                                $retry491 = false;
                                throw $e492;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e492) {
                            $commit490 = false;
                            if ($tm494.checkForStaleObjects())
                                continue $label489;
                            $retry491 = false;
                            throw new fabric.worker.AbortException($e492);
                        }
                        finally {
                            if ($commit490) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e492) {
                                    $commit490 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e492) {
                                    $commit490 = false;
                                    fabric.common.TransactionID $currentTid493 =
                                      $tm494.getCurrentTid();
                                    if ($currentTid493 != null) {
                                        if ($e492.tid.equals($currentTid493) ||
                                              !$e492.tid.isDescendantOf(
                                                           $currentTid493)) {
                                            throw $e492;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit490 && $retry491) {
                                {  }
                                continue $label489;
                            }
                        }
                    }
                }
            }
        }
        
        public void acquireReconfigLocks() {
            this.get$lock().acquireOptimistic();
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
    
    public static final byte[] $classHash = new byte[] { 66, 46, 74, 106, 71,
    -108, 22, 24, 62, -51, 0, 14, -118, -36, 84, 76, 38, -41, 124, -69, 10, 16,
    -100, -121, 11, -30, 96, 98, -127, -34, 49, -109 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521305458000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
