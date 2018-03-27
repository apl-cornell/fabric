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
                    fabric.worker.transaction.TransactionManager $tm449 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled452 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff450 = 1;
                    boolean $doBackoff451 = true;
                    boolean $retry446 = true;
                    $label444: for (boolean $commit445 = false; !$commit445; ) {
                        if ($backoffEnabled452) {
                            if ($doBackoff451) {
                                if ($backoff450 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff450);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e447) {
                                            
                                        }
                                    }
                                }
                                if ($backoff450 < 5000) $backoff450 *= 2;
                            }
                            $doBackoff451 = $backoff450 <= 32 || !$doBackoff451;
                        }
                        $commit445 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e447) {
                            $commit445 = false;
                            continue $label444;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e447) {
                            $commit445 = false;
                            fabric.common.TransactionID $currentTid448 =
                              $tm449.getCurrentTid();
                            if ($e447.tid.isDescendantOf($currentTid448))
                                continue $label444;
                            if ($currentTid448.parent != null) {
                                $retry446 = false;
                                throw $e447;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e447) {
                            $commit445 = false;
                            if ($tm449.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid448 =
                              $tm449.getCurrentTid();
                            if ($e447.tid.isDescendantOf($currentTid448)) {
                                $retry446 = true;
                            }
                            else if ($currentTid448.parent != null) {
                                $retry446 = false;
                                throw $e447;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e447) {
                            $commit445 = false;
                            if ($tm449.checkForStaleObjects())
                                continue $label444;
                            $retry446 = false;
                            throw new fabric.worker.AbortException($e447);
                        }
                        finally {
                            if ($commit445) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e447) {
                                    $commit445 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e447) {
                                    $commit445 = false;
                                    fabric.common.TransactionID $currentTid448 =
                                      $tm449.getCurrentTid();
                                    if ($currentTid448 != null) {
                                        if ($e447.tid.equals($currentTid448) ||
                                              !$e447.tid.isDescendantOf(
                                                           $currentTid448)) {
                                            throw $e447;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit445 && $retry446) {
                                {  }
                                continue $label444;
                            }
                        }
                    }
                }
            }
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.set$currentPolicy(
                      tmp.get$metric().thresholdPolicy(tmp.get$rate(),
                                                       tmp.get$base(), false,
                                                       tmp.$getStore()));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm458 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled461 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff459 = 1;
                    boolean $doBackoff460 = true;
                    boolean $retry455 = true;
                    $label453: for (boolean $commit454 = false; !$commit454; ) {
                        if ($backoffEnabled461) {
                            if ($doBackoff460) {
                                if ($backoff459 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff459);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e456) {
                                            
                                        }
                                    }
                                }
                                if ($backoff459 < 5000) $backoff459 *= 2;
                            }
                            $doBackoff460 = $backoff459 <= 32 || !$doBackoff460;
                        }
                        $commit454 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().thresholdPolicy(
                                                     tmp.get$rate(),
                                                     tmp.get$base(), true,
                                                     tmp.$getStore()));
                        }
                        catch (final fabric.worker.RetryException $e456) {
                            $commit454 = false;
                            continue $label453;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e456) {
                            $commit454 = false;
                            fabric.common.TransactionID $currentTid457 =
                              $tm458.getCurrentTid();
                            if ($e456.tid.isDescendantOf($currentTid457))
                                continue $label453;
                            if ($currentTid457.parent != null) {
                                $retry455 = false;
                                throw $e456;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e456) {
                            $commit454 = false;
                            if ($tm458.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid457 =
                              $tm458.getCurrentTid();
                            if ($e456.tid.isDescendantOf($currentTid457)) {
                                $retry455 = true;
                            }
                            else if ($currentTid457.parent != null) {
                                $retry455 = false;
                                throw $e456;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e456) {
                            $commit454 = false;
                            if ($tm458.checkForStaleObjects())
                                continue $label453;
                            $retry455 = false;
                            throw new fabric.worker.AbortException($e456);
                        }
                        finally {
                            if ($commit454) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e456) {
                                    $commit454 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e456) {
                                    $commit454 = false;
                                    fabric.common.TransactionID $currentTid457 =
                                      $tm458.getCurrentTid();
                                    if ($currentTid457 != null) {
                                        if ($e456.tid.equals($currentTid457) ||
                                              !$e456.tid.isDescendantOf(
                                                           $currentTid457)) {
                                            throw $e456;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit454 && $retry455) {
                                {  }
                                continue $label453;
                            }
                        }
                    }
                }
            }
            tmp.get$currentPolicy().activate();
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.metrics.contracts.Contract._Impl.static_activate(tmp);
                tmp.getMetric().addContract(tmp);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm467 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled470 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff468 = 1;
                    boolean $doBackoff469 = true;
                    boolean $retry464 = true;
                    $label462: for (boolean $commit463 = false; !$commit463; ) {
                        if ($backoffEnabled470) {
                            if ($doBackoff469) {
                                if ($backoff468 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff468);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e465) {
                                            
                                        }
                                    }
                                }
                                if ($backoff468 < 5000) $backoff468 *= 2;
                            }
                            $doBackoff469 = $backoff468 <= 32 || !$doBackoff469;
                        }
                        $commit463 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e465) {
                            $commit463 = false;
                            continue $label462;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e465) {
                            $commit463 = false;
                            fabric.common.TransactionID $currentTid466 =
                              $tm467.getCurrentTid();
                            if ($e465.tid.isDescendantOf($currentTid466))
                                continue $label462;
                            if ($currentTid466.parent != null) {
                                $retry464 = false;
                                throw $e465;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e465) {
                            $commit463 = false;
                            if ($tm467.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid466 =
                              $tm467.getCurrentTid();
                            if ($e465.tid.isDescendantOf($currentTid466)) {
                                $retry464 = true;
                            }
                            else if ($currentTid466.parent != null) {
                                $retry464 = false;
                                throw $e465;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e465) {
                            $commit463 = false;
                            if ($tm467.checkForStaleObjects())
                                continue $label462;
                            $retry464 = false;
                            throw new fabric.worker.AbortException($e465);
                        }
                        finally {
                            if ($commit463) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e465) {
                                    $commit463 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e465) {
                                    $commit463 = false;
                                    fabric.common.TransactionID $currentTid466 =
                                      $tm467.getCurrentTid();
                                    if ($currentTid466 != null) {
                                        if ($e465.tid.equals($currentTid466) ||
                                              !$e465.tid.isDescendantOf(
                                                           $currentTid466)) {
                                            throw $e465;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit463 && $retry464) {
                                {  }
                                continue $label462;
                            }
                        }
                    }
                }
            }
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
                if (curExpiry >= currentTime) { return update(curExpiry); }
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
              toString() +
            " >= " +
            this.get$rate() +
            " * t + " +
            (this.get$base() +
               this.get$rate() * java.lang.System.currentTimeMillis()) +
            " until " +
            getExpiry();
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
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.MetricContract._Impl src =
              (fabric.metrics.contracts.MetricContract._Impl) other;
            this.metric = src.metric;
            this.rate = src.rate;
            this.base = src.base;
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
    
    public static final byte[] $classHash = new byte[] { 70, -101, -95, 118,
    -109, 40, 96, 25, -16, -80, -106, -119, -50, -101, -45, -27, 11, 67, 20,
    -122, -108, -60, -42, -9, 73, 110, -77, -7, -59, 118, 48, 96 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521834360000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
