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
                        try { if (tmp.isActivated()) return; }
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
                      startPol$var507 = startPol;
                    fabric.worker.transaction.TransactionManager $tm513 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled516 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff514 = 1;
                    boolean $doBackoff515 = true;
                    boolean $retry510 = true;
                    $label508: for (boolean $commit509 = false; !$commit509; ) {
                        if ($backoffEnabled516) {
                            if ($doBackoff515) {
                                if ($backoff514 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff514);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e511) {
                                            
                                        }
                                    }
                                }
                                if ($backoff514 < 5000) $backoff514 *= 2;
                            }
                            $doBackoff515 = $backoff514 <= 32 || !$doBackoff515;
                        }
                        $commit509 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            startPol =
                              tmp.get$metric().equalityPolicy(tmp.get$value(),
                                                              true,
                                                              tmp.$getStore());
                        }
                        catch (final fabric.worker.RetryException $e511) {
                            $commit509 = false;
                            continue $label508;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e511) {
                            $commit509 = false;
                            fabric.common.TransactionID $currentTid512 =
                              $tm513.getCurrentTid();
                            if ($e511.tid.isDescendantOf($currentTid512))
                                continue $label508;
                            if ($currentTid512.parent != null) {
                                $retry510 = false;
                                throw $e511;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e511) {
                            $commit509 = false;
                            if ($tm513.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid512 =
                              $tm513.getCurrentTid();
                            if ($e511.tid.isDescendantOf($currentTid512)) {
                                $retry510 = true;
                            }
                            else if ($currentTid512.parent != null) {
                                $retry510 = false;
                                throw $e511;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e511) {
                            $commit509 = false;
                            if ($tm513.checkForStaleObjects())
                                continue $label508;
                            $retry510 = false;
                            throw new fabric.worker.AbortException($e511);
                        }
                        finally {
                            if ($commit509) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e511) {
                                    $commit509 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e511) {
                                    $commit509 = false;
                                    fabric.common.TransactionID $currentTid512 =
                                      $tm513.getCurrentTid();
                                    if ($currentTid512 != null) {
                                        if ($e511.tid.equals($currentTid512) ||
                                              !$e511.tid.isDescendantOf(
                                                           $currentTid512)) {
                                            throw $e511;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit509 && $retry510) {
                                { startPol = startPol$var507; }
                                continue $label508;
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
                    fabric.worker.transaction.TransactionManager $tm522 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled525 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff523 = 1;
                    boolean $doBackoff524 = true;
                    boolean $retry519 = true;
                    $label517: for (boolean $commit518 = false; !$commit518; ) {
                        if ($backoffEnabled525) {
                            if ($doBackoff524) {
                                if ($backoff523 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff523);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e520) {
                                            
                                        }
                                    }
                                }
                                if ($backoff523 < 5000) $backoff523 *= 2;
                            }
                            $doBackoff524 = $backoff523 <= 32 || !$doBackoff524;
                        }
                        $commit518 = true;
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
                        catch (final fabric.worker.RetryException $e520) {
                            $commit518 = false;
                            continue $label517;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e520) {
                            $commit518 = false;
                            fabric.common.TransactionID $currentTid521 =
                              $tm522.getCurrentTid();
                            if ($e520.tid.isDescendantOf($currentTid521))
                                continue $label517;
                            if ($currentTid521.parent != null) {
                                $retry519 = false;
                                throw $e520;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e520) {
                            $commit518 = false;
                            if ($tm522.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid521 =
                              $tm522.getCurrentTid();
                            if ($e520.tid.isDescendantOf($currentTid521)) {
                                $retry519 = true;
                            }
                            else if ($currentTid521.parent != null) {
                                $retry519 = false;
                                throw $e520;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e520) {
                            $commit518 = false;
                            if ($tm522.checkForStaleObjects())
                                continue $label517;
                            $retry519 = false;
                            throw new fabric.worker.AbortException($e520);
                        }
                        finally {
                            if ($commit518) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e520) {
                                    $commit518 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e520) {
                                    $commit518 = false;
                                    fabric.common.TransactionID $currentTid521 =
                                      $tm522.getCurrentTid();
                                    if ($currentTid521 != null) {
                                        if ($e520.tid.equals($currentTid521) ||
                                              !$e520.tid.isDescendantOf(
                                                           $currentTid521)) {
                                            throw $e520;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit518 && $retry519) {
                                {  }
                                continue $label517;
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
                    fabric.worker.transaction.TransactionManager $tm531 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled534 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff532 = 1;
                    boolean $doBackoff533 = true;
                    boolean $retry528 = true;
                    $label526: for (boolean $commit527 = false; !$commit527; ) {
                        if ($backoffEnabled534) {
                            if ($doBackoff533) {
                                if ($backoff532 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff532);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e529) {
                                            
                                        }
                                    }
                                }
                                if ($backoff532 < 5000) $backoff532 *= 2;
                            }
                            $doBackoff533 = $backoff532 <= 32 || !$doBackoff533;
                        }
                        $commit527 = true;
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
                        catch (final fabric.worker.RetryException $e529) {
                            $commit527 = false;
                            continue $label526;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e529) {
                            $commit527 = false;
                            fabric.common.TransactionID $currentTid530 =
                              $tm531.getCurrentTid();
                            if ($e529.tid.isDescendantOf($currentTid530))
                                continue $label526;
                            if ($currentTid530.parent != null) {
                                $retry528 = false;
                                throw $e529;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e529) {
                            $commit527 = false;
                            if ($tm531.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid530 =
                              $tm531.getCurrentTid();
                            if ($e529.tid.isDescendantOf($currentTid530)) {
                                $retry528 = true;
                            }
                            else if ($currentTid530.parent != null) {
                                $retry528 = false;
                                throw $e529;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e529) {
                            $commit527 = false;
                            if ($tm531.checkForStaleObjects())
                                continue $label526;
                            $retry528 = false;
                            throw new fabric.worker.AbortException($e529);
                        }
                        finally {
                            if ($commit527) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e529) {
                                    $commit527 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e529) {
                                    $commit527 = false;
                                    fabric.common.TransactionID $currentTid530 =
                                      $tm531.getCurrentTid();
                                    if ($currentTid530 != null) {
                                        if ($e529.tid.equals($currentTid530) ||
                                              !$e529.tid.isDescendantOf(
                                                           $currentTid530)) {
                                            throw $e529;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit527 && $retry528) {
                                {  }
                                continue $label526;
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
    
    public static final byte[] $classHash = new byte[] { 44, -37, 33, 60, -128,
    -116, 66, -41, 67, 42, 5, 61, -23, -49, 59, 48, -121, 43, 112, 106, 78, -76,
    -54, -112, 34, 7, -31, -16, 59, -91, -39, -121 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521305458000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
