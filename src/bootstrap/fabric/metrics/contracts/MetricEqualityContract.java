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
        
        public void activate() { refresh(false); }
        
        private static void static_activate(
          fabric.metrics.contracts.MetricEqualityContract tmp) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.set$currentPolicy(
                      tmp.get$metric().equalityPolicy(tmp.get$value(), false,
                                                      tmp.$getStore()));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm487 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled490 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff488 = 1;
                    boolean $doBackoff489 = true;
                    boolean $retry484 = true;
                    $label482: for (boolean $commit483 = false; !$commit483; ) {
                        if ($backoffEnabled490) {
                            if ($doBackoff489) {
                                if ($backoff488 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff488);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e485) {
                                            
                                        }
                                    }
                                }
                                if ($backoff488 < 5000) $backoff488 *= 2;
                            }
                            $doBackoff489 = $backoff488 <= 32 || !$doBackoff489;
                        }
                        $commit483 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().equalityPolicy(
                                                     tmp.get$value(), false,
                                                     tmp.$getStore()));
                        }
                        catch (final fabric.worker.RetryException $e485) {
                            $commit483 = false;
                            continue $label482;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e485) {
                            $commit483 = false;
                            fabric.common.TransactionID $currentTid486 =
                              $tm487.getCurrentTid();
                            if ($e485.tid.isDescendantOf($currentTid486))
                                continue $label482;
                            if ($currentTid486.parent != null) {
                                $retry484 = false;
                                throw $e485;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e485) {
                            $commit483 = false;
                            if ($tm487.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid486 =
                              $tm487.getCurrentTid();
                            if ($e485.tid.isDescendantOf($currentTid486)) {
                                $retry484 = true;
                            }
                            else if ($currentTid486.parent != null) {
                                $retry484 = false;
                                throw $e485;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e485) {
                            $commit483 = false;
                            if ($tm487.checkForStaleObjects())
                                continue $label482;
                            $retry484 = false;
                            throw new fabric.worker.AbortException($e485);
                        }
                        finally {
                            if ($commit483) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e485) {
                                    $commit483 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e485) {
                                    $commit483 = false;
                                    fabric.common.TransactionID $currentTid486 =
                                      $tm487.getCurrentTid();
                                    if ($currentTid486 != null) {
                                        if ($e485.tid.equals($currentTid486) ||
                                              !$e485.tid.isDescendantOf(
                                                           $currentTid486)) {
                                            throw $e485;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit483 && $retry484) {
                                {  }
                                continue $label482;
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
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
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
    
    public static final byte[] $classHash = new byte[] { 20, -19, 96, -54, -56,
    28, -97, -30, -15, 53, 60, 119, 41, -1, -86, -90, 37, 99, -127, 82, -104,
    55, -14, -19, 19, -123, -107, 9, -64, -110, 42, -20 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525096549000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
