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
                    fabric.worker.transaction.TransactionManager $tm99 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled102 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff100 = 1;
                    boolean $doBackoff101 = true;
                    boolean $retry96 = true;
                    $label94: for (boolean $commit95 = false; !$commit95; ) {
                        if ($backoffEnabled102) {
                            if ($doBackoff101) {
                                if ($backoff100 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff100);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e97) {
                                            
                                        }
                                    }
                                }
                                if ($backoff100 < 5000) $backoff100 *= 2;
                            }
                            $doBackoff101 = $backoff100 <= 32 || !$doBackoff101;
                        }
                        $commit95 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e97) {
                            $commit95 = false;
                            continue $label94;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e97) {
                            $commit95 = false;
                            fabric.common.TransactionID $currentTid98 =
                              $tm99.getCurrentTid();
                            if ($e97.tid.isDescendantOf($currentTid98))
                                continue $label94;
                            if ($currentTid98.parent != null) {
                                $retry96 = false;
                                throw $e97;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e97) {
                            $commit95 = false;
                            if ($tm99.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid98 =
                              $tm99.getCurrentTid();
                            if ($e97.tid.isDescendantOf($currentTid98)) {
                                $retry96 = true;
                            }
                            else if ($currentTid98.parent != null) {
                                $retry96 = false;
                                throw $e97;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e97) {
                            $commit95 = false;
                            if ($tm99.checkForStaleObjects()) continue $label94;
                            $retry96 = false;
                            throw new fabric.worker.AbortException($e97);
                        }
                        finally {
                            if ($commit95) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e97) {
                                    $commit95 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e97) {
                                    $commit95 = false;
                                    fabric.common.TransactionID $currentTid98 =
                                      $tm99.getCurrentTid();
                                    if ($currentTid98 != null) {
                                        if ($e97.tid.equals($currentTid98) ||
                                              !$e97.tid.isDescendantOf(
                                                          $currentTid98)) {
                                            throw $e97;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit95 && $retry96) {
                                {  }
                                continue $label94;
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
                    fabric.worker.transaction.TransactionManager $tm108 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled111 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff109 = 1;
                    boolean $doBackoff110 = true;
                    boolean $retry105 = true;
                    $label103: for (boolean $commit104 = false; !$commit104; ) {
                        if ($backoffEnabled111) {
                            if ($doBackoff110) {
                                if ($backoff109 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff109);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e106) {
                                            
                                        }
                                    }
                                }
                                if ($backoff109 < 5000) $backoff109 *= 2;
                            }
                            $doBackoff110 = $backoff109 <= 32 || !$doBackoff110;
                        }
                        $commit104 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().thresholdPolicy(
                                                     tmp.get$rate(),
                                                     tmp.get$base(), true,
                                                     tmp.$getStore()));
                        }
                        catch (final fabric.worker.RetryException $e106) {
                            $commit104 = false;
                            continue $label103;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e106) {
                            $commit104 = false;
                            fabric.common.TransactionID $currentTid107 =
                              $tm108.getCurrentTid();
                            if ($e106.tid.isDescendantOf($currentTid107))
                                continue $label103;
                            if ($currentTid107.parent != null) {
                                $retry105 = false;
                                throw $e106;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e106) {
                            $commit104 = false;
                            if ($tm108.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid107 =
                              $tm108.getCurrentTid();
                            if ($e106.tid.isDescendantOf($currentTid107)) {
                                $retry105 = true;
                            }
                            else if ($currentTid107.parent != null) {
                                $retry105 = false;
                                throw $e106;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e106) {
                            $commit104 = false;
                            if ($tm108.checkForStaleObjects())
                                continue $label103;
                            $retry105 = false;
                            throw new fabric.worker.AbortException($e106);
                        }
                        finally {
                            if ($commit104) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e106) {
                                    $commit104 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e106) {
                                    $commit104 = false;
                                    fabric.common.TransactionID $currentTid107 =
                                      $tm108.getCurrentTid();
                                    if ($currentTid107 != null) {
                                        if ($e106.tid.equals($currentTid107) ||
                                              !$e106.tid.isDescendantOf(
                                                           $currentTid107)) {
                                            throw $e106;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit104 && $retry105) {
                                {  }
                                continue $label103;
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
                    fabric.worker.transaction.TransactionManager $tm117 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled120 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff118 = 1;
                    boolean $doBackoff119 = true;
                    boolean $retry114 = true;
                    $label112: for (boolean $commit113 = false; !$commit113; ) {
                        if ($backoffEnabled120) {
                            if ($doBackoff119) {
                                if ($backoff118 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff118);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e115) {
                                            
                                        }
                                    }
                                }
                                if ($backoff118 < 5000) $backoff118 *= 2;
                            }
                            $doBackoff119 = $backoff118 <= 32 || !$doBackoff119;
                        }
                        $commit113 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e115) {
                            $commit113 = false;
                            continue $label112;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e115) {
                            $commit113 = false;
                            fabric.common.TransactionID $currentTid116 =
                              $tm117.getCurrentTid();
                            if ($e115.tid.isDescendantOf($currentTid116))
                                continue $label112;
                            if ($currentTid116.parent != null) {
                                $retry114 = false;
                                throw $e115;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e115) {
                            $commit113 = false;
                            if ($tm117.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid116 =
                              $tm117.getCurrentTid();
                            if ($e115.tid.isDescendantOf($currentTid116)) {
                                $retry114 = true;
                            }
                            else if ($currentTid116.parent != null) {
                                $retry114 = false;
                                throw $e115;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e115) {
                            $commit113 = false;
                            if ($tm117.checkForStaleObjects())
                                continue $label112;
                            $retry114 = false;
                            throw new fabric.worker.AbortException($e115);
                        }
                        finally {
                            if ($commit113) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e115) {
                                    $commit113 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e115) {
                                    $commit113 = false;
                                    fabric.common.TransactionID $currentTid116 =
                                      $tm117.getCurrentTid();
                                    if ($currentTid116 != null) {
                                        if ($e115.tid.equals($currentTid116) ||
                                              !$e115.tid.isDescendantOf(
                                                           $currentTid116)) {
                                            throw $e115;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit113 && $retry114) {
                                {  }
                                continue $label112;
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
