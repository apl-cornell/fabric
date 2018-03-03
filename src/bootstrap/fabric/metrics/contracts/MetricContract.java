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
                    fabric.worker.transaction.TransactionManager $tm428 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled431 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff429 = 1;
                    boolean $doBackoff430 = true;
                    boolean $retry425 = true;
                    $label423: for (boolean $commit424 = false; !$commit424; ) {
                        if ($backoffEnabled431) {
                            if ($doBackoff430) {
                                if ($backoff429 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff429);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e426) {
                                            
                                        }
                                    }
                                }
                                if ($backoff429 < 5000) $backoff429 *= 2;
                            }
                            $doBackoff430 = $backoff429 <= 32 || !$doBackoff430;
                        }
                        $commit424 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e426) {
                            $commit424 = false;
                            continue $label423;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e426) {
                            $commit424 = false;
                            fabric.common.TransactionID $currentTid427 =
                              $tm428.getCurrentTid();
                            if ($e426.tid.isDescendantOf($currentTid427))
                                continue $label423;
                            if ($currentTid427.parent != null) {
                                $retry425 = false;
                                throw $e426;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e426) {
                            $commit424 = false;
                            if ($tm428.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid427 =
                              $tm428.getCurrentTid();
                            if ($e426.tid.isDescendantOf($currentTid427)) {
                                $retry425 = true;
                            }
                            else if ($currentTid427.parent != null) {
                                $retry425 = false;
                                throw $e426;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e426) {
                            $commit424 = false;
                            if ($tm428.checkForStaleObjects())
                                continue $label423;
                            $retry425 = false;
                            throw new fabric.worker.AbortException($e426);
                        }
                        finally {
                            if ($commit424) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e426) {
                                    $commit424 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e426) {
                                    $commit424 = false;
                                    fabric.common.TransactionID $currentTid427 =
                                      $tm428.getCurrentTid();
                                    if ($currentTid427 != null) {
                                        if ($e426.tid.equals($currentTid427) ||
                                              !$e426.tid.isDescendantOf(
                                                           $currentTid427)) {
                                            throw $e426;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit424 && $retry425) {
                                {  }
                                continue $label423;
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
                      startPol$var432 = startPol;
                    fabric.worker.transaction.TransactionManager $tm438 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled441 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff439 = 1;
                    boolean $doBackoff440 = true;
                    boolean $retry435 = true;
                    $label433: for (boolean $commit434 = false; !$commit434; ) {
                        if ($backoffEnabled441) {
                            if ($doBackoff440) {
                                if ($backoff439 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff439);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e436) {
                                            
                                        }
                                    }
                                }
                                if ($backoff439 < 5000) $backoff439 *= 2;
                            }
                            $doBackoff440 = $backoff439 <= 32 || !$doBackoff440;
                        }
                        $commit434 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            startPol = tmp.get$metric().policy(tmp.get$rate(),
                                                               tmp.get$base(),
                                                               true,
                                                               tmp.$getStore());
                        }
                        catch (final fabric.worker.RetryException $e436) {
                            $commit434 = false;
                            continue $label433;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e436) {
                            $commit434 = false;
                            fabric.common.TransactionID $currentTid437 =
                              $tm438.getCurrentTid();
                            if ($e436.tid.isDescendantOf($currentTid437))
                                continue $label433;
                            if ($currentTid437.parent != null) {
                                $retry435 = false;
                                throw $e436;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e436) {
                            $commit434 = false;
                            if ($tm438.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid437 =
                              $tm438.getCurrentTid();
                            if ($e436.tid.isDescendantOf($currentTid437)) {
                                $retry435 = true;
                            }
                            else if ($currentTid437.parent != null) {
                                $retry435 = false;
                                throw $e436;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e436) {
                            $commit434 = false;
                            if ($tm438.checkForStaleObjects())
                                continue $label433;
                            $retry435 = false;
                            throw new fabric.worker.AbortException($e436);
                        }
                        finally {
                            if ($commit434) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e436) {
                                    $commit434 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e436) {
                                    $commit434 = false;
                                    fabric.common.TransactionID $currentTid437 =
                                      $tm438.getCurrentTid();
                                    if ($currentTid437 != null) {
                                        if ($e436.tid.equals($currentTid437) ||
                                              !$e436.tid.isDescendantOf(
                                                           $currentTid437)) {
                                            throw $e436;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit434 && $retry435) {
                                { startPol = startPol$var432; }
                                continue $label433;
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
                    fabric.worker.transaction.TransactionManager $tm447 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled450 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff448 = 1;
                    boolean $doBackoff449 = true;
                    boolean $retry444 = true;
                    $label442: for (boolean $commit443 = false; !$commit443; ) {
                        if ($backoffEnabled450) {
                            if ($doBackoff449) {
                                if ($backoff448 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff448);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e445) {
                                            
                                        }
                                    }
                                }
                                if ($backoff448 < 5000) $backoff448 *= 2;
                            }
                            $doBackoff449 = $backoff448 <= 32 || !$doBackoff449;
                        }
                        $commit443 = true;
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
                        catch (final fabric.worker.RetryException $e445) {
                            $commit443 = false;
                            continue $label442;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e445) {
                            $commit443 = false;
                            fabric.common.TransactionID $currentTid446 =
                              $tm447.getCurrentTid();
                            if ($e445.tid.isDescendantOf($currentTid446))
                                continue $label442;
                            if ($currentTid446.parent != null) {
                                $retry444 = false;
                                throw $e445;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e445) {
                            $commit443 = false;
                            if ($tm447.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid446 =
                              $tm447.getCurrentTid();
                            if ($e445.tid.isDescendantOf($currentTid446)) {
                                $retry444 = true;
                            }
                            else if ($currentTid446.parent != null) {
                                $retry444 = false;
                                throw $e445;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e445) {
                            $commit443 = false;
                            if ($tm447.checkForStaleObjects())
                                continue $label442;
                            $retry444 = false;
                            throw new fabric.worker.AbortException($e445);
                        }
                        finally {
                            if ($commit443) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e445) {
                                    $commit443 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e445) {
                                    $commit443 = false;
                                    fabric.common.TransactionID $currentTid446 =
                                      $tm447.getCurrentTid();
                                    if ($currentTid446 != null) {
                                        if ($e445.tid.equals($currentTid446) ||
                                              !$e445.tid.isDescendantOf(
                                                           $currentTid446)) {
                                            throw $e445;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit443 && $retry444) {
                                {  }
                                continue $label442;
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
    
    public static final byte[] $classHash = new byte[] { 75, 123, 71, -92, 3,
    66, -108, -30, -35, 49, 40, 115, -69, -87, 25, 88, -88, -2, 30, -81, 22, 47,
    20, -95, -4, -112, 73, 8, 84, 10, -22, -62 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520116324000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
