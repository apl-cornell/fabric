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
        
        public static final java.lang.Class[] $paramTypes0 =
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
                                    this, "finishActivating", $paramTypes0,
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
                    fabric.worker.transaction.TransactionManager $tm5 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled8 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff6 = 1;
                    boolean $doBackoff7 = true;
                    boolean $retry2 = true;
                    $label0: for (boolean $commit1 = false; !$commit1; ) {
                        if ($backoffEnabled8) {
                            if ($doBackoff7) {
                                if ($backoff6 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff6);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e3) {
                                            
                                        }
                                    }
                                }
                                if ($backoff6 < 5000) $backoff6 *= 2;
                            }
                            $doBackoff7 = $backoff6 <= 32 || !$doBackoff7;
                        }
                        $commit1 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e3) {
                            $commit1 = false;
                            continue $label0;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e3) {
                            $commit1 = false;
                            fabric.common.TransactionID $currentTid4 =
                              $tm5.getCurrentTid();
                            if ($e3.tid.isDescendantOf($currentTid4))
                                continue $label0;
                            if ($currentTid4.parent != null) {
                                $retry2 = false;
                                throw $e3;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e3) {
                            $commit1 = false;
                            if ($tm5.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid4 =
                              $tm5.getCurrentTid();
                            if ($e3.tid.isDescendantOf($currentTid4)) {
                                $retry2 = true;
                            }
                            else if ($currentTid4.parent != null) {
                                $retry2 = false;
                                throw $e3;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e3) {
                            $commit1 = false;
                            if ($tm5.checkForStaleObjects()) continue $label0;
                            $retry2 = false;
                            throw new fabric.worker.AbortException($e3);
                        }
                        finally {
                            if ($commit1) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.AbortException $e3) {
                                    $commit1 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e3) {
                                    $commit1 = false;
                                    fabric.common.TransactionID $currentTid4 =
                                      $tm5.getCurrentTid();
                                    if ($currentTid4 != null) {
                                        if ($e3.tid.equals($currentTid4) ||
                                              !$e3.tid.isDescendantOf(
                                                         $currentTid4)) {
                                            throw $e3;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit1 && $retry2) {
                                {  }
                                continue $label0;
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
                      startPol$var9 = startPol;
                    fabric.worker.transaction.TransactionManager $tm15 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled18 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff16 = 1;
                    boolean $doBackoff17 = true;
                    boolean $retry12 = true;
                    $label10: for (boolean $commit11 = false; !$commit11; ) {
                        if ($backoffEnabled18) {
                            if ($doBackoff17) {
                                if ($backoff16 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff16);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e13) {
                                            
                                        }
                                    }
                                }
                                if ($backoff16 < 5000) $backoff16 *= 2;
                            }
                            $doBackoff17 = $backoff16 <= 32 || !$doBackoff17;
                        }
                        $commit11 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            startPol = tmp.get$metric().policy(tmp.get$rate(),
                                                               tmp.get$base(),
                                                               true,
                                                               tmp.$getStore());
                        }
                        catch (final fabric.worker.RetryException $e13) {
                            $commit11 = false;
                            continue $label10;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e13) {
                            $commit11 = false;
                            fabric.common.TransactionID $currentTid14 =
                              $tm15.getCurrentTid();
                            if ($e13.tid.isDescendantOf($currentTid14))
                                continue $label10;
                            if ($currentTid14.parent != null) {
                                $retry12 = false;
                                throw $e13;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e13) {
                            $commit11 = false;
                            if ($tm15.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid14 =
                              $tm15.getCurrentTid();
                            if ($e13.tid.isDescendantOf($currentTid14)) {
                                $retry12 = true;
                            }
                            else if ($currentTid14.parent != null) {
                                $retry12 = false;
                                throw $e13;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e13) {
                            $commit11 = false;
                            if ($tm15.checkForStaleObjects()) continue $label10;
                            $retry12 = false;
                            throw new fabric.worker.AbortException($e13);
                        }
                        finally {
                            if ($commit11) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e13) {
                                    $commit11 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e13) {
                                    $commit11 = false;
                                    fabric.common.TransactionID $currentTid14 =
                                      $tm15.getCurrentTid();
                                    if ($currentTid14 != null) {
                                        if ($e13.tid.equals($currentTid14) ||
                                              !$e13.tid.isDescendantOf(
                                                          $currentTid14)) {
                                            throw $e13;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit11 && $retry12) {
                                { startPol = startPol$var9; }
                                continue $label10;
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
                    fabric.worker.transaction.TransactionManager $tm24 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled27 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff25 = 1;
                    boolean $doBackoff26 = true;
                    boolean $retry21 = true;
                    $label19: for (boolean $commit20 = false; !$commit20; ) {
                        if ($backoffEnabled27) {
                            if ($doBackoff26) {
                                if ($backoff25 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff25);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e22) {
                                            
                                        }
                                    }
                                }
                                if ($backoff25 < 5000) $backoff25 *= 2;
                            }
                            $doBackoff26 = $backoff25 <= 32 || !$doBackoff26;
                        }
                        $commit20 = true;
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
                        catch (final fabric.worker.RetryException $e22) {
                            $commit20 = false;
                            continue $label19;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e22) {
                            $commit20 = false;
                            fabric.common.TransactionID $currentTid23 =
                              $tm24.getCurrentTid();
                            if ($e22.tid.isDescendantOf($currentTid23))
                                continue $label19;
                            if ($currentTid23.parent != null) {
                                $retry21 = false;
                                throw $e22;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e22) {
                            $commit20 = false;
                            if ($tm24.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid23 =
                              $tm24.getCurrentTid();
                            if ($e22.tid.isDescendantOf($currentTid23)) {
                                $retry21 = true;
                            }
                            else if ($currentTid23.parent != null) {
                                $retry21 = false;
                                throw $e22;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e22) {
                            $commit20 = false;
                            if ($tm24.checkForStaleObjects()) continue $label19;
                            $retry21 = false;
                            throw new fabric.worker.AbortException($e22);
                        }
                        finally {
                            if ($commit20) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e22) {
                                    $commit20 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e22) {
                                    $commit20 = false;
                                    fabric.common.TransactionID $currentTid23 =
                                      $tm24.getCurrentTid();
                                    if ($currentTid23 != null) {
                                        if ($e22.tid.equals($currentTid23) ||
                                              !$e22.tid.isDescendantOf(
                                                          $currentTid23)) {
                                            throw $e22;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit20 && $retry21) {
                                {  }
                                continue $label19;
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
                    fabric.worker.transaction.TransactionManager $tm33 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled36 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff34 = 1;
                    boolean $doBackoff35 = true;
                    boolean $retry30 = true;
                    $label28: for (boolean $commit29 = false; !$commit29; ) {
                        if ($backoffEnabled36) {
                            if ($doBackoff35) {
                                if ($backoff34 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff34);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e31) {
                                            
                                        }
                                    }
                                }
                                if ($backoff34 < 5000) $backoff34 *= 2;
                            }
                            $doBackoff35 = $backoff34 <= 32 || !$doBackoff35;
                        }
                        $commit29 = true;
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
                        catch (final fabric.worker.RetryException $e31) {
                            $commit29 = false;
                            continue $label28;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e31) {
                            $commit29 = false;
                            fabric.common.TransactionID $currentTid32 =
                              $tm33.getCurrentTid();
                            if ($e31.tid.isDescendantOf($currentTid32))
                                continue $label28;
                            if ($currentTid32.parent != null) {
                                $retry30 = false;
                                throw $e31;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e31) {
                            $commit29 = false;
                            if ($tm33.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid32 =
                              $tm33.getCurrentTid();
                            if ($e31.tid.isDescendantOf($currentTid32)) {
                                $retry30 = true;
                            }
                            else if ($currentTid32.parent != null) {
                                $retry30 = false;
                                throw $e31;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e31) {
                            $commit29 = false;
                            if ($tm33.checkForStaleObjects()) continue $label28;
                            $retry30 = false;
                            throw new fabric.worker.AbortException($e31);
                        }
                        finally {
                            if ($commit29) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e31) {
                                    $commit29 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e31) {
                                    $commit29 = false;
                                    fabric.common.TransactionID $currentTid32 =
                                      $tm33.getCurrentTid();
                                    if ($currentTid32 != null) {
                                        if ($e31.tid.equals($currentTid32) ||
                                              !$e31.tid.isDescendantOf(
                                                          $currentTid32)) {
                                            throw $e31;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit29 && $retry30) {
                                {  }
                                continue $label28;
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
