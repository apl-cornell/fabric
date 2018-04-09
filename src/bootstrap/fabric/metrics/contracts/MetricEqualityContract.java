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
                    fabric.worker.transaction.TransactionManager $tm536 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled539 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff537 = 1;
                    boolean $doBackoff538 = true;
                    boolean $retry533 = true;
                    $label531: for (boolean $commit532 = false; !$commit532; ) {
                        if ($backoffEnabled539) {
                            if ($doBackoff538) {
                                if ($backoff537 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff537);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e534) {
                                            
                                        }
                                    }
                                }
                                if ($backoff537 < 5000) $backoff537 *= 2;
                            }
                            $doBackoff538 = $backoff537 <= 32 || !$doBackoff538;
                        }
                        $commit532 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e534) {
                            $commit532 = false;
                            continue $label531;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e534) {
                            $commit532 = false;
                            fabric.common.TransactionID $currentTid535 =
                              $tm536.getCurrentTid();
                            if ($e534.tid.isDescendantOf($currentTid535))
                                continue $label531;
                            if ($currentTid535.parent != null) {
                                $retry533 = false;
                                throw $e534;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e534) {
                            $commit532 = false;
                            if ($tm536.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid535 =
                              $tm536.getCurrentTid();
                            if ($e534.tid.isDescendantOf($currentTid535)) {
                                $retry533 = true;
                            }
                            else if ($currentTid535.parent != null) {
                                $retry533 = false;
                                throw $e534;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e534) {
                            $commit532 = false;
                            if ($tm536.checkForStaleObjects())
                                continue $label531;
                            $retry533 = false;
                            throw new fabric.worker.AbortException($e534);
                        }
                        finally {
                            if ($commit532) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e534) {
                                    $commit532 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e534) {
                                    $commit532 = false;
                                    fabric.common.TransactionID $currentTid535 =
                                      $tm536.getCurrentTid();
                                    if ($currentTid535 != null) {
                                        if ($e534.tid.equals($currentTid535) ||
                                              !$e534.tid.isDescendantOf(
                                                           $currentTid535)) {
                                            throw $e534;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit532 && $retry533) {
                                {  }
                                continue $label531;
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
                    fabric.worker.transaction.TransactionManager $tm545 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled548 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff546 = 1;
                    boolean $doBackoff547 = true;
                    boolean $retry542 = true;
                    $label540: for (boolean $commit541 = false; !$commit541; ) {
                        if ($backoffEnabled548) {
                            if ($doBackoff547) {
                                if ($backoff546 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff546);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e543) {
                                            
                                        }
                                    }
                                }
                                if ($backoff546 < 5000) $backoff546 *= 2;
                            }
                            $doBackoff547 = $backoff546 <= 32 || !$doBackoff547;
                        }
                        $commit541 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().equalityPolicy(
                                                     tmp.get$value(), false,
                                                     tmp.$getStore()));
                        }
                        catch (final fabric.worker.RetryException $e543) {
                            $commit541 = false;
                            continue $label540;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e543) {
                            $commit541 = false;
                            fabric.common.TransactionID $currentTid544 =
                              $tm545.getCurrentTid();
                            if ($e543.tid.isDescendantOf($currentTid544))
                                continue $label540;
                            if ($currentTid544.parent != null) {
                                $retry542 = false;
                                throw $e543;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e543) {
                            $commit541 = false;
                            if ($tm545.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid544 =
                              $tm545.getCurrentTid();
                            if ($e543.tid.isDescendantOf($currentTid544)) {
                                $retry542 = true;
                            }
                            else if ($currentTid544.parent != null) {
                                $retry542 = false;
                                throw $e543;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e543) {
                            $commit541 = false;
                            if ($tm545.checkForStaleObjects())
                                continue $label540;
                            $retry542 = false;
                            throw new fabric.worker.AbortException($e543);
                        }
                        finally {
                            if ($commit541) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e543) {
                                    $commit541 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e543) {
                                    $commit541 = false;
                                    fabric.common.TransactionID $currentTid544 =
                                      $tm545.getCurrentTid();
                                    if ($currentTid544 != null) {
                                        if ($e543.tid.equals($currentTid544) ||
                                              !$e543.tid.isDescendantOf(
                                                           $currentTid544)) {
                                            throw $e543;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit541 && $retry542) {
                                {  }
                                continue $label540;
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
                    fabric.worker.transaction.TransactionManager $tm554 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled557 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff555 = 1;
                    boolean $doBackoff556 = true;
                    boolean $retry551 = true;
                    $label549: for (boolean $commit550 = false; !$commit550; ) {
                        if ($backoffEnabled557) {
                            if ($doBackoff556) {
                                if ($backoff555 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff555);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e552) {
                                            
                                        }
                                    }
                                }
                                if ($backoff555 < 5000) $backoff555 *= 2;
                            }
                            $doBackoff556 = $backoff555 <= 32 || !$doBackoff556;
                        }
                        $commit550 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e552) {
                            $commit550 = false;
                            continue $label549;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e552) {
                            $commit550 = false;
                            fabric.common.TransactionID $currentTid553 =
                              $tm554.getCurrentTid();
                            if ($e552.tid.isDescendantOf($currentTid553))
                                continue $label549;
                            if ($currentTid553.parent != null) {
                                $retry551 = false;
                                throw $e552;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e552) {
                            $commit550 = false;
                            if ($tm554.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid553 =
                              $tm554.getCurrentTid();
                            if ($e552.tid.isDescendantOf($currentTid553)) {
                                $retry551 = true;
                            }
                            else if ($currentTid553.parent != null) {
                                $retry551 = false;
                                throw $e552;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e552) {
                            $commit550 = false;
                            if ($tm554.checkForStaleObjects())
                                continue $label549;
                            $retry551 = false;
                            throw new fabric.worker.AbortException($e552);
                        }
                        finally {
                            if ($commit550) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e552) {
                                    $commit550 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e552) {
                                    $commit550 = false;
                                    fabric.common.TransactionID $currentTid553 =
                                      $tm554.getCurrentTid();
                                    if ($currentTid553 != null) {
                                        if ($e552.tid.equals($currentTid553) ||
                                              !$e552.tid.isDescendantOf(
                                                           $currentTid553)) {
                                            throw $e552;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit550 && $retry551) {
                                {  }
                                continue $label549;
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
              this.get$metric().equalityPolicy(this.get$value(), $getStore());
            if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null) &&
                  !oldPolicy.equals(newPolicy))
                oldPolicy.unapply(
                            (fabric.metrics.contracts.MetricEqualityContract)
                              this.$getProxy());
            newPolicy.activate();
            long newExpiry = newPolicy.expiry();
            boolean result = update(newExpiry, asyncExtension);
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
    
    public static final byte[] $classHash = new byte[] { 61, -43, -59, 61, 96,
    111, 104, 118, -48, 66, 105, 44, -78, 18, -84, 4, 37, -110, 1, -111, -86,
    -98, 69, 23, 46, 53, -76, 85, 57, -16, -17, -90 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1523309517000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
