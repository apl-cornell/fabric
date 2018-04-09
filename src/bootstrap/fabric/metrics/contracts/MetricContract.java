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
                    fabric.worker.transaction.TransactionManager $tm509 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled512 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff510 = 1;
                    boolean $doBackoff511 = true;
                    boolean $retry506 = true;
                    $label504: for (boolean $commit505 = false; !$commit505; ) {
                        if ($backoffEnabled512) {
                            if ($doBackoff511) {
                                if ($backoff510 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff510);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e507) {
                                            
                                        }
                                    }
                                }
                                if ($backoff510 < 5000) $backoff510 *= 2;
                            }
                            $doBackoff511 = $backoff510 <= 32 || !$doBackoff511;
                        }
                        $commit505 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e507) {
                            $commit505 = false;
                            continue $label504;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e507) {
                            $commit505 = false;
                            fabric.common.TransactionID $currentTid508 =
                              $tm509.getCurrentTid();
                            if ($e507.tid.isDescendantOf($currentTid508))
                                continue $label504;
                            if ($currentTid508.parent != null) {
                                $retry506 = false;
                                throw $e507;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e507) {
                            $commit505 = false;
                            if ($tm509.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid508 =
                              $tm509.getCurrentTid();
                            if ($e507.tid.isDescendantOf($currentTid508)) {
                                $retry506 = true;
                            }
                            else if ($currentTid508.parent != null) {
                                $retry506 = false;
                                throw $e507;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e507) {
                            $commit505 = false;
                            if ($tm509.checkForStaleObjects())
                                continue $label504;
                            $retry506 = false;
                            throw new fabric.worker.AbortException($e507);
                        }
                        finally {
                            if ($commit505) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e507) {
                                    $commit505 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e507) {
                                    $commit505 = false;
                                    fabric.common.TransactionID $currentTid508 =
                                      $tm509.getCurrentTid();
                                    if ($currentTid508 != null) {
                                        if ($e507.tid.equals($currentTid508) ||
                                              !$e507.tid.isDescendantOf(
                                                           $currentTid508)) {
                                            throw $e507;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit505 && $retry506) {
                                {  }
                                continue $label504;
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
                    fabric.worker.transaction.TransactionManager $tm518 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled521 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff519 = 1;
                    boolean $doBackoff520 = true;
                    boolean $retry515 = true;
                    $label513: for (boolean $commit514 = false; !$commit514; ) {
                        if ($backoffEnabled521) {
                            if ($doBackoff520) {
                                if ($backoff519 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff519);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e516) {
                                            
                                        }
                                    }
                                }
                                if ($backoff519 < 5000) $backoff519 *= 2;
                            }
                            $doBackoff520 = $backoff519 <= 32 || !$doBackoff520;
                        }
                        $commit514 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().thresholdPolicy(
                                                     tmp.get$rate(),
                                                     tmp.get$base(), true,
                                                     tmp.$getStore()));
                        }
                        catch (final fabric.worker.RetryException $e516) {
                            $commit514 = false;
                            continue $label513;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e516) {
                            $commit514 = false;
                            fabric.common.TransactionID $currentTid517 =
                              $tm518.getCurrentTid();
                            if ($e516.tid.isDescendantOf($currentTid517))
                                continue $label513;
                            if ($currentTid517.parent != null) {
                                $retry515 = false;
                                throw $e516;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e516) {
                            $commit514 = false;
                            if ($tm518.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid517 =
                              $tm518.getCurrentTid();
                            if ($e516.tid.isDescendantOf($currentTid517)) {
                                $retry515 = true;
                            }
                            else if ($currentTid517.parent != null) {
                                $retry515 = false;
                                throw $e516;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e516) {
                            $commit514 = false;
                            if ($tm518.checkForStaleObjects())
                                continue $label513;
                            $retry515 = false;
                            throw new fabric.worker.AbortException($e516);
                        }
                        finally {
                            if ($commit514) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e516) {
                                    $commit514 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e516) {
                                    $commit514 = false;
                                    fabric.common.TransactionID $currentTid517 =
                                      $tm518.getCurrentTid();
                                    if ($currentTid517 != null) {
                                        if ($e516.tid.equals($currentTid517) ||
                                              !$e516.tid.isDescendantOf(
                                                           $currentTid517)) {
                                            throw $e516;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit514 && $retry515) {
                                {  }
                                continue $label513;
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
                    fabric.worker.transaction.TransactionManager $tm527 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled530 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff528 = 1;
                    boolean $doBackoff529 = true;
                    boolean $retry524 = true;
                    $label522: for (boolean $commit523 = false; !$commit523; ) {
                        if ($backoffEnabled530) {
                            if ($doBackoff529) {
                                if ($backoff528 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff528);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e525) {
                                            
                                        }
                                    }
                                }
                                if ($backoff528 < 5000) $backoff528 *= 2;
                            }
                            $doBackoff529 = $backoff528 <= 32 || !$doBackoff529;
                        }
                        $commit523 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e525) {
                            $commit523 = false;
                            continue $label522;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e525) {
                            $commit523 = false;
                            fabric.common.TransactionID $currentTid526 =
                              $tm527.getCurrentTid();
                            if ($e525.tid.isDescendantOf($currentTid526))
                                continue $label522;
                            if ($currentTid526.parent != null) {
                                $retry524 = false;
                                throw $e525;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e525) {
                            $commit523 = false;
                            if ($tm527.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid526 =
                              $tm527.getCurrentTid();
                            if ($e525.tid.isDescendantOf($currentTid526)) {
                                $retry524 = true;
                            }
                            else if ($currentTid526.parent != null) {
                                $retry524 = false;
                                throw $e525;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e525) {
                            $commit523 = false;
                            if ($tm527.checkForStaleObjects())
                                continue $label522;
                            $retry524 = false;
                            throw new fabric.worker.AbortException($e525);
                        }
                        finally {
                            if ($commit523) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e525) {
                                    $commit523 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e525) {
                                    $commit523 = false;
                                    fabric.common.TransactionID $currentTid526 =
                                      $tm527.getCurrentTid();
                                    if ($currentTid526 != null) {
                                        if ($e525.tid.equals($currentTid526) ||
                                              !$e525.tid.isDescendantOf(
                                                           $currentTid526)) {
                                            throw $e525;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit523 && $retry524) {
                                {  }
                                continue $label522;
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
                if (curExpiry >= currentTime) {
                    return update(curExpiry, asyncExtension);
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
            boolean result = update(newExpiry, asyncExtension);
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
    
    public static final byte[] $classHash = new byte[] { -85, -97, 71, -45, 30,
    37, 9, 49, 57, 51, 25, -43, 17, 46, 58, -26, -61, -34, 125, 94, -40, 93, 69,
    62, 53, 122, -90, -71, 119, -46, 62, 80 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1523309488000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
