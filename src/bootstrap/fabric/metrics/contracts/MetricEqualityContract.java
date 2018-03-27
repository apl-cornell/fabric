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
                        try { if (tmp.isActivated()) return; }
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
                                {  }
                                continue $label471;
                            }
                        }
                    }
                }
            }
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.set$currentPolicy(
                      tmp.get$metric().equalityPolicy(tmp.get$value(), false,
                                                      tmp.$getStore()));
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
                            tmp.set$currentPolicy(
                                  tmp.get$metric().equalityPolicy(
                                                     tmp.get$value(), false,
                                                     tmp.$getStore()));
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
            tmp.get$currentPolicy().activate();
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.metrics.contracts.Contract._Impl.static_activate(tmp);
                tmp.getMetric().addContract(tmp);
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
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
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
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.MetricEqualityContract._Impl src =
              (fabric.metrics.contracts.MetricEqualityContract._Impl) other;
            this.metric = src.metric;
            this.value = src.value;
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
    
    public static final byte[] $classHash = new byte[] { 16, 79, -15, 107, 111,
    5, -13, 73, -3, 61, -102, -108, -110, 1, 77, -90, -74, 119, 117, 97, -110,
    -87, 10, -12, 5, -77, 99, -9, -55, 16, 59, 29 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521834376000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
