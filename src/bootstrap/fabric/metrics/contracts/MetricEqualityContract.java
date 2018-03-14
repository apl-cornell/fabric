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
 * of either a {@link Metric} or a set of {@link MetricEqualityContract}s and can be
 * observed by other {@link Contract}s.
 */
public interface MetricEqualityContract
  extends fabric.metrics.contracts.Contract {
    public fabric.metrics.Metric get$metric();
    
    public fabric.metrics.Metric set$metric(fabric.metrics.Metric val);
    
    public double get$value();
    
    public double set$value(double val);
    
    public double postInc$value();
    
    public double postDec$value();
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      get$currentPolicy();
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      set$currentPolicy(fabric.metrics.contracts.enforcement.EnforcementPolicy val);
    
    /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param value
   *        the value of the equality
   * @param base
   *        the base of the bound this {@link MetricEqualityContract} asserts on
   *        metric.
   */
    public fabric.metrics.contracts.MetricEqualityContract
      fabric$metrics$contracts$MetricEqualityContract$(
      fabric.metrics.Metric metric, double value);
    
    /** @return the {@link Metric} that this contract observes. */
    public fabric.metrics.Metric getMetric();
    
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
      implements fabric.metrics.contracts.MetricEqualityContract {
        public fabric.metrics.Metric get$metric() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).get$metric();
        }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).set$metric(val);
        }
        
        public double get$value() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).get$value();
        }
        
        public double set$value(double val) {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).set$value(val);
        }
        
        public double postInc$value() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).postInc$value();
        }
        
        public double postDec$value() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).postDec$value();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          get$currentPolicy() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).get$currentPolicy();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          set$currentPolicy(
          fabric.metrics.contracts.enforcement.EnforcementPolicy val) {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).set$currentPolicy(val);
        }
        
        public fabric.metrics.contracts.MetricEqualityContract
          fabric$metrics$contracts$MetricEqualityContract$(
          fabric.metrics.Metric arg1, double arg2) {
            return ((fabric.metrics.contracts.MetricEqualityContract) fetch()).
              fabric$metrics$contracts$MetricEqualityContract$(arg1, arg2);
        }
        
        public fabric.metrics.Metric getMetric() {
            return ((fabric.metrics.contracts.MetricEqualityContract) fetch()).
              getMetric();
        }
        
        public void finishActivating(
          fabric.metrics.contracts.enforcement.EnforcementPolicy arg1) {
            ((fabric.metrics.contracts.MetricEqualityContract) fetch()).
              finishActivating(arg1);
        }
        
        public void finishActivating_remote(
          fabric.lang.security.Principal arg1,
          fabric.metrics.contracts.enforcement.EnforcementPolicy arg2) {
            ((fabric.metrics.contracts.MetricEqualityContract) fetch()).
              finishActivating_remote(arg1, arg2);
        }
        
        public static final java.lang.Class[] $paramTypes5 =
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
                                    this, "finishActivating", $paramTypes5,
                                    new java.lang.Object[] { arg2 });
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
            return ((fabric.metrics.contracts.MetricEqualityContract) fetch()).
              getLeafSubjects();
        }
        
        public _Proxy(MetricEqualityContract._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.contracts.Contract._Impl
      implements fabric.metrics.contracts.MetricEqualityContract {
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
        
        public double get$value() { return this.value; }
        
        public double set$value(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$value() {
            double tmp = this.get$value();
            this.set$value((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$value() {
            double tmp = this.get$value();
            this.set$value((double) (tmp - 1));
            return tmp;
        }
        
        public double value;
        
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
   * @param value
   *        the value of the equality
   * @param base
   *        the base of the bound this {@link MetricEqualityContract} asserts on
   *        metric.
   */
        public fabric.metrics.contracts.MetricEqualityContract
          fabric$metrics$contracts$MetricEqualityContract$(
          fabric.metrics.Metric metric, double value) {
            this.set$metric(metric);
            this.set$value((double) value);
            fabric$metrics$contracts$Contract$();
            return (fabric.metrics.contracts.MetricEqualityContract)
                     this.$getProxy();
        }
        
        /** @return the {@link Metric} that this contract observes. */
        public fabric.metrics.Metric getMetric() { return this.get$metric(); }
        
        public void activate() {
            fabric.metrics.contracts.MetricEqualityContract._Impl.
              static_activate((fabric.metrics.contracts.MetricEqualityContract)
                                this.$getProxy());
        }
        
        private static void static_activate(
          fabric.metrics.contracts.MetricEqualityContract tmp) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.isActivated()) return;
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
                        try { if (tmp.isActivated()) return; }
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
            fabric.metrics.contracts.enforcement.EnforcementPolicy startPol =
              null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                startPol = tmp.get$metric().equalityPolicy(tmp.get$value(),
                                                           false,
                                                           tmp.$getStore());
            }
            else {
                {
                    fabric.metrics.contracts.enforcement.EnforcementPolicy
                      startPol$var479 = startPol;
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
                            startPol =
                              tmp.get$metric().equalityPolicy(tmp.get$value(),
                                                              true,
                                                              tmp.$getStore());
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
                                { startPol = startPol$var479; }
                                continue $label480;
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
            fabric.metrics.contracts.MetricEqualityContract._Impl.
              static_finishActivating(
                (fabric.metrics.contracts.MetricEqualityContract)
                  this.$getProxy(), p);
        }
        
        private static void static_finishActivating(
          fabric.metrics.contracts.MetricEqualityContract tmp,
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
                  unapply((fabric.metrics.contracts.MetricEqualityContract)
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
              this.get$metric().equalityPolicy(this.get$value(), $getStore());
            if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null) &&
                  !oldPolicy.equals(newPolicy))
                oldPolicy.unapply(
                            (fabric.metrics.contracts.MetricEqualityContract)
                              this.$getProxy());
            newPolicy.activate();
            long newExpiry = newPolicy.expiry();
            boolean result = update(newExpiry);
            if (newExpiry >= currentTime) {
                this.set$currentPolicy(newPolicy);
                this.get$currentPolicy().
                  apply((fabric.metrics.contracts.MetricEqualityContract)
                          this.$getProxy());
            } else {
                this.set$currentPolicy(null);
            }
            return result;
        }
        
        public boolean implies(fabric.metrics.Metric otherMetric,
                               double otherRate, double otherBase) {
            if (!getMetric().equals(otherMetric) ||
                  !fabric.metrics.contracts.Bound._Impl.
                  test(otherRate, otherBase, this.get$value(),
                       java.lang.System.currentTimeMillis()))
                return false;
            return valid();
        }
        
        public java.lang.String toString() {
            return ((java.lang.Comparable)
                      fabric.lang.WrappedJavaInlineable.$unwrap(getMetric())).
              toString() + " == " + this.get$value() + " until " + getExpiry();
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
            fabric.metrics.contracts.MetricEqualityContract._Impl.
              static_removeObserver(
                (fabric.metrics.contracts.MetricEqualityContract)
                  this.$getProxy(), obs);
        }
        
        private static void static_removeObserver(
          fabric.metrics.contracts.MetricEqualityContract tmp,
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
                    fabric.worker.transaction.TransactionManager $tm503 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled506 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff504 = 1;
                    boolean $doBackoff505 = true;
                    boolean $retry500 = true;
                    $label498: for (boolean $commit499 = false; !$commit499; ) {
                        if ($backoffEnabled506) {
                            if ($doBackoff505) {
                                if ($backoff504 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff504);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e501) {
                                            
                                        }
                                    }
                                }
                                if ($backoff504 < 5000) $backoff504 *= 2;
                            }
                            $doBackoff505 = $backoff504 <= 32 || !$doBackoff505;
                        }
                        $commit499 = true;
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
                        catch (final fabric.worker.RetryException $e501) {
                            $commit499 = false;
                            continue $label498;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e501) {
                            $commit499 = false;
                            fabric.common.TransactionID $currentTid502 =
                              $tm503.getCurrentTid();
                            if ($e501.tid.isDescendantOf($currentTid502))
                                continue $label498;
                            if ($currentTid502.parent != null) {
                                $retry500 = false;
                                throw $e501;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e501) {
                            $commit499 = false;
                            if ($tm503.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid502 =
                              $tm503.getCurrentTid();
                            if ($e501.tid.isDescendantOf($currentTid502)) {
                                $retry500 = true;
                            }
                            else if ($currentTid502.parent != null) {
                                $retry500 = false;
                                throw $e501;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e501) {
                            $commit499 = false;
                            if ($tm503.checkForStaleObjects())
                                continue $label498;
                            $retry500 = false;
                            throw new fabric.worker.AbortException($e501);
                        }
                        finally {
                            if ($commit499) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e501) {
                                    $commit499 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e501) {
                                    $commit499 = false;
                                    fabric.common.TransactionID $currentTid502 =
                                      $tm503.getCurrentTid();
                                    if ($currentTid502 != null) {
                                        if ($e501.tid.equals($currentTid502) ||
                                              !$e501.tid.isDescendantOf(
                                                           $currentTid502)) {
                                            throw $e501;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit499 && $retry500) {
                                {  }
                                continue $label498;
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
            return new fabric.metrics.contracts.MetricEqualityContract._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.metric, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            out.writeDouble(this.value);
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
            this.value = in.readDouble();
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
            fabric.metrics.contracts.MetricEqualityContract._Impl src =
              (fabric.metrics.contracts.MetricEqualityContract._Impl) other;
            this.metric = src.metric;
            this.value = src.value;
            this.currentPolicy = src.currentPolicy;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.MetricEqualityContract._Static {
            public _Proxy(fabric.metrics.contracts.MetricEqualityContract.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.MetricEqualityContract.
              _Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  MetricEqualityContract.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    MetricEqualityContract.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.MetricEqualityContract._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.MetricEqualityContract._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.MetricEqualityContract._Static {
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
                return new fabric.metrics.contracts.MetricEqualityContract.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 5, -102, 118, 47, -60,
    -63, 60, -96, -83, 56, -82, -38, -3, -110, 19, 121, -127, -114, 107, 3, 90,
    61, -127, -122, -124, -124, -95, 104, 11, -101, 82, -56 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520977993000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
