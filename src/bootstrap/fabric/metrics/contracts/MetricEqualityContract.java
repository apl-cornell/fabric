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
import fabric.worker.metrics.ImmutableSet;
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
        
        public void activate() { refresh(false); }
        
        private static void static_activate(
          fabric.metrics.contracts.MetricEqualityContract tmp) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.set$currentPolicy(
                      tmp.get$metric().equalityPolicy(tmp.get$value(), false,
                                                      tmp.$getStore()));
                tmp.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(tmp.get$currentPolicy()));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm496 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled499 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff497 = 1;
                    boolean $doBackoff498 = true;
                    boolean $retry493 = true;
                    $label491: for (boolean $commit492 = false; !$commit492; ) {
                        if ($backoffEnabled499) {
                            if ($doBackoff498) {
                                if ($backoff497 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff497);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e494) {
                                            
                                        }
                                    }
                                }
                                if ($backoff497 < 5000) $backoff497 *= 2;
                            }
                            $doBackoff498 = $backoff497 <= 32 || !$doBackoff498;
                        }
                        $commit492 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().equalityPolicy(
                                                     tmp.get$value(), false,
                                                     tmp.$getStore()));
                            tmp.
                              set$$associated(
                                fabric.worker.metrics.ImmutableSet.emptySet().
                                    add(tmp.get$currentPolicy()));
                        }
                        catch (final fabric.worker.RetryException $e494) {
                            $commit492 = false;
                            continue $label491;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e494) {
                            $commit492 = false;
                            fabric.common.TransactionID $currentTid495 =
                              $tm496.getCurrentTid();
                            if ($e494.tid.isDescendantOf($currentTid495))
                                continue $label491;
                            if ($currentTid495.parent != null) {
                                $retry493 = false;
                                throw $e494;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e494) {
                            $commit492 = false;
                            if ($tm496.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid495 =
                              $tm496.getCurrentTid();
                            if ($e494.tid.isDescendantOf($currentTid495)) {
                                $retry493 = true;
                            }
                            else if ($currentTid495.parent != null) {
                                $retry493 = false;
                                throw $e494;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e494) {
                            $commit492 = false;
                            if ($tm496.checkForStaleObjects())
                                continue $label491;
                            $retry493 = false;
                            throw new fabric.worker.AbortException($e494);
                        }
                        finally {
                            if ($commit492) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e494) {
                                    $commit492 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e494) {
                                    $commit492 = false;
                                    fabric.common.TransactionID $currentTid495 =
                                      $tm496.getCurrentTid();
                                    if ($currentTid495 != null) {
                                        if ($e494.tid.equals($currentTid495) ||
                                              !$e494.tid.isDescendantOf(
                                                           $currentTid495)) {
                                            throw $e494;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit492 && $retry493) {
                                {  }
                                continue $label491;
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
                    fabric.worker.transaction.TransactionManager $tm505 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled508 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff506 = 1;
                    boolean $doBackoff507 = true;
                    boolean $retry502 = true;
                    $label500: for (boolean $commit501 = false; !$commit501; ) {
                        if ($backoffEnabled508) {
                            if ($doBackoff507) {
                                if ($backoff506 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff506);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e503) {
                                            
                                        }
                                    }
                                }
                                if ($backoff506 < 5000) $backoff506 *= 2;
                            }
                            $doBackoff507 = $backoff506 <= 32 || !$doBackoff507;
                        }
                        $commit501 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e503) {
                            $commit501 = false;
                            continue $label500;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e503) {
                            $commit501 = false;
                            fabric.common.TransactionID $currentTid504 =
                              $tm505.getCurrentTid();
                            if ($e503.tid.isDescendantOf($currentTid504))
                                continue $label500;
                            if ($currentTid504.parent != null) {
                                $retry502 = false;
                                throw $e503;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e503) {
                            $commit501 = false;
                            if ($tm505.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid504 =
                              $tm505.getCurrentTid();
                            if ($e503.tid.isDescendantOf($currentTid504)) {
                                $retry502 = true;
                            }
                            else if ($currentTid504.parent != null) {
                                $retry502 = false;
                                throw $e503;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e503) {
                            $commit501 = false;
                            if ($tm505.checkForStaleObjects())
                                continue $label500;
                            $retry502 = false;
                            throw new fabric.worker.AbortException($e503);
                        }
                        finally {
                            if ($commit501) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e503) {
                                    $commit501 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e503) {
                                    $commit501 = false;
                                    fabric.common.TransactionID $currentTid504 =
                                      $tm505.getCurrentTid();
                                    if ($currentTid504 != null) {
                                        if ($e503.tid.equals($currentTid504) ||
                                              !$e503.tid.isDescendantOf(
                                                           $currentTid504)) {
                                            throw $e503;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit501 && $retry502) {
                                {  }
                                continue $label500;
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
            long currentTime = java.lang.System.currentTimeMillis();
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null)) {
                long curExpiry = this.get$currentPolicy().expiry();
                if (curExpiry >= currentTime) {
                    this.get$currentPolicy().
                      apply((fabric.metrics.contracts.MetricEqualityContract)
                              this.$getProxy());
                    return update(curExpiry, asyncExtension);
                }
            }
            if (asyncExtension) return false;
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            tm.markCoordination();
            fabric.metrics.contracts.enforcement.EnforcementPolicy oldPolicy =
              this.get$currentPolicy();
            fabric.metrics.contracts.enforcement.EnforcementPolicy newPolicy =
              this.get$metric().equalityPolicy(this.get$value(), $getStore());
            newPolicy.activate();
            long newExpiry = newPolicy.expiry();
            if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null))
                oldPolicy.unapply(
                            (fabric.metrics.contracts.MetricEqualityContract)
                              this.$getProxy());
            boolean result = update(newExpiry, asyncExtension);
            if (newExpiry >= currentTime) {
                this.set$currentPolicy(newPolicy);
                this.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(this.get$currentPolicy()));
                this.get$currentPolicy().
                  apply((fabric.metrics.contracts.MetricEqualityContract)
                          this.$getProxy());
            } else {
                this.set$currentPolicy(null);
                this.set$$associated(null);
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
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.MetricEqualityContract.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 119, 70, -22, 61, 108,
    103, 29, -94, -17, -112, -100, 65, -116, -127, 126, -124, 92, -119, -34,
    -30, 30, 76, 120, 90, 48, 30, -36, -9, -125, -64, -26, 68 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526344812000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
