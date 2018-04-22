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
import fabric.worker.metrics.ImmutableMetricsVector;
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
    
    public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics();
    
    public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
      fabric.worker.metrics.ImmutableMetricsVector val);
    
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
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
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
        
        public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).get$leafMetrics();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).set$leafMetrics(val);
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
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
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
        
        public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics() {
            return this.leafMetrics;
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.leafMetrics = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.worker.metrics.ImmutableMetricsVector leafMetrics;
        
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
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        metric)) instanceof fabric.metrics.SampledMetric) {
                this.
                  set$leafMetrics(
                    fabric.worker.metrics.ImmutableMetricsVector.
                        createVector(
                          new fabric.metrics.SampledMetric[] { (fabric.metrics.SampledMetric)
                                                                 fabric.lang.Object._Proxy.
                                                                 $getProxy(
                                                                   metric) }));
            }
            else if (fabric.lang.Object._Proxy.
                       $getProxy(
                         (java.lang.Object)
                           fabric.lang.WrappedJavaInlineable.
                           $unwrap(
                             metric)) instanceof fabric.metrics.DerivedMetric) {
                this.set$leafMetrics(
                       ((fabric.metrics.DerivedMetric)
                          fabric.lang.Object._Proxy.$getProxy(
                                                      metric)).getLeafSubjects(
                                                                 ));
            }
            else {
                throw new java.lang.IllegalStateException(
                        "All metrics should be either sampled or derived!");
            }
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
                        try { if (tmp.isActivated()) return; }
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
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.set$currentPolicy(
                      tmp.get$metric().equalityPolicy(tmp.get$value(), false,
                                                      tmp.$getStore()));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm563 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled566 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff564 = 1;
                    boolean $doBackoff565 = true;
                    boolean $retry560 = true;
                    $label558: for (boolean $commit559 = false; !$commit559; ) {
                        if ($backoffEnabled566) {
                            if ($doBackoff565) {
                                if ($backoff564 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff564);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e561) {
                                            
                                        }
                                    }
                                }
                                if ($backoff564 < 5000) $backoff564 *= 2;
                            }
                            $doBackoff565 = $backoff564 <= 32 || !$doBackoff565;
                        }
                        $commit559 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().equalityPolicy(
                                                     tmp.get$value(), false,
                                                     tmp.$getStore()));
                        }
                        catch (final fabric.worker.RetryException $e561) {
                            $commit559 = false;
                            continue $label558;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e561) {
                            $commit559 = false;
                            fabric.common.TransactionID $currentTid562 =
                              $tm563.getCurrentTid();
                            if ($e561.tid.isDescendantOf($currentTid562))
                                continue $label558;
                            if ($currentTid562.parent != null) {
                                $retry560 = false;
                                throw $e561;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e561) {
                            $commit559 = false;
                            if ($tm563.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid562 =
                              $tm563.getCurrentTid();
                            if ($e561.tid.isDescendantOf($currentTid562)) {
                                $retry560 = true;
                            }
                            else if ($currentTid562.parent != null) {
                                $retry560 = false;
                                throw $e561;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e561) {
                            $commit559 = false;
                            if ($tm563.checkForStaleObjects())
                                continue $label558;
                            $retry560 = false;
                            throw new fabric.worker.AbortException($e561);
                        }
                        finally {
                            if ($commit559) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e561) {
                                    $commit559 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e561) {
                                    $commit559 = false;
                                    fabric.common.TransactionID $currentTid562 =
                                      $tm563.getCurrentTid();
                                    if ($currentTid562 != null) {
                                        if ($e561.tid.equals($currentTid562) ||
                                              !$e561.tid.isDescendantOf(
                                                           $currentTid562)) {
                                            throw $e561;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit559 && $retry560) {
                                {  }
                                continue $label558;
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
                    fabric.worker.transaction.TransactionManager $tm572 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled575 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff573 = 1;
                    boolean $doBackoff574 = true;
                    boolean $retry569 = true;
                    $label567: for (boolean $commit568 = false; !$commit568; ) {
                        if ($backoffEnabled575) {
                            if ($doBackoff574) {
                                if ($backoff573 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff573);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e570) {
                                            
                                        }
                                    }
                                }
                                if ($backoff573 < 5000) $backoff573 *= 2;
                            }
                            $doBackoff574 = $backoff573 <= 32 || !$doBackoff574;
                        }
                        $commit568 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e570) {
                            $commit568 = false;
                            continue $label567;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e570) {
                            $commit568 = false;
                            fabric.common.TransactionID $currentTid571 =
                              $tm572.getCurrentTid();
                            if ($e570.tid.isDescendantOf($currentTid571))
                                continue $label567;
                            if ($currentTid571.parent != null) {
                                $retry569 = false;
                                throw $e570;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e570) {
                            $commit568 = false;
                            if ($tm572.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid571 =
                              $tm572.getCurrentTid();
                            if ($e570.tid.isDescendantOf($currentTid571)) {
                                $retry569 = true;
                            }
                            else if ($currentTid571.parent != null) {
                                $retry569 = false;
                                throw $e570;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e570) {
                            $commit568 = false;
                            if ($tm572.checkForStaleObjects())
                                continue $label567;
                            $retry569 = false;
                            throw new fabric.worker.AbortException($e570);
                        }
                        finally {
                            if ($commit568) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e570) {
                                    $commit568 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e570) {
                                    $commit568 = false;
                                    fabric.common.TransactionID $currentTid571 =
                                      $tm572.getCurrentTid();
                                    if ($currentTid571 != null) {
                                        if ($e570.tid.equals($currentTid571) ||
                                              !$e570.tid.isDescendantOf(
                                                           $currentTid571)) {
                                            throw $e570;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit568 && $retry569) {
                                {  }
                                continue $label567;
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
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return this.get$leafMetrics();
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
            $writeInline(out, this.leafMetrics);
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
            this.leafMetrics = (fabric.worker.metrics.ImmutableMetricsVector)
                                 in.readObject();
            this.value = in.readDouble();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.MetricEqualityContract._Impl src =
              (fabric.metrics.contracts.MetricEqualityContract._Impl) other;
            this.metric = src.metric;
            this.leafMetrics = src.leafMetrics;
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
    
    public static final byte[] $classHash = new byte[] { 125, -60, 83, -73, 42,
    98, -106, 111, 42, 24, 22, -20, -50, -67, 114, -2, 17, 107, -102, 99, 85,
    -124, 94, 62, -103, 97, -114, -9, 73, 76, -9, 60 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524349481000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
