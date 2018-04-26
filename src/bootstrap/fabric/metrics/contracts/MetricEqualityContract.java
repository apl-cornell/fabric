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
                tmp.set$currentPolicy(
                      tmp.get$metric().equalityPolicy(tmp.get$value(), false,
                                                      tmp.$getStore()));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm525 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled528 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff526 = 1;
                    boolean $doBackoff527 = true;
                    boolean $retry522 = true;
                    $label520: for (boolean $commit521 = false; !$commit521; ) {
                        if ($backoffEnabled528) {
                            if ($doBackoff527) {
                                if ($backoff526 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff526);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e523) {
                                            
                                        }
                                    }
                                }
                                if ($backoff526 < 5000) $backoff526 *= 2;
                            }
                            $doBackoff527 = $backoff526 <= 32 || !$doBackoff527;
                        }
                        $commit521 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().equalityPolicy(
                                                     tmp.get$value(), false,
                                                     tmp.$getStore()));
                        }
                        catch (final fabric.worker.RetryException $e523) {
                            $commit521 = false;
                            continue $label520;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e523) {
                            $commit521 = false;
                            fabric.common.TransactionID $currentTid524 =
                              $tm525.getCurrentTid();
                            if ($e523.tid.isDescendantOf($currentTid524))
                                continue $label520;
                            if ($currentTid524.parent != null) {
                                $retry522 = false;
                                throw $e523;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e523) {
                            $commit521 = false;
                            if ($tm525.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid524 =
                              $tm525.getCurrentTid();
                            if ($e523.tid.isDescendantOf($currentTid524)) {
                                $retry522 = true;
                            }
                            else if ($currentTid524.parent != null) {
                                $retry522 = false;
                                throw $e523;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e523) {
                            $commit521 = false;
                            if ($tm525.checkForStaleObjects())
                                continue $label520;
                            $retry522 = false;
                            throw new fabric.worker.AbortException($e523);
                        }
                        finally {
                            if ($commit521) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e523) {
                                    $commit521 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e523) {
                                    $commit521 = false;
                                    fabric.common.TransactionID $currentTid524 =
                                      $tm525.getCurrentTid();
                                    if ($currentTid524 != null) {
                                        if ($e523.tid.equals($currentTid524) ||
                                              !$e523.tid.isDescendantOf(
                                                           $currentTid524)) {
                                            throw $e523;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit521 && $retry522) {
                                {  }
                                continue $label520;
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
                    fabric.worker.transaction.TransactionManager $tm534 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled537 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff535 = 1;
                    boolean $doBackoff536 = true;
                    boolean $retry531 = true;
                    $label529: for (boolean $commit530 = false; !$commit530; ) {
                        if ($backoffEnabled537) {
                            if ($doBackoff536) {
                                if ($backoff535 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff535);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e532) {
                                            
                                        }
                                    }
                                }
                                if ($backoff535 < 5000) $backoff535 *= 2;
                            }
                            $doBackoff536 = $backoff535 <= 32 || !$doBackoff536;
                        }
                        $commit530 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e532) {
                            $commit530 = false;
                            continue $label529;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e532) {
                            $commit530 = false;
                            fabric.common.TransactionID $currentTid533 =
                              $tm534.getCurrentTid();
                            if ($e532.tid.isDescendantOf($currentTid533))
                                continue $label529;
                            if ($currentTid533.parent != null) {
                                $retry531 = false;
                                throw $e532;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e532) {
                            $commit530 = false;
                            if ($tm534.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid533 =
                              $tm534.getCurrentTid();
                            if ($e532.tid.isDescendantOf($currentTid533)) {
                                $retry531 = true;
                            }
                            else if ($currentTid533.parent != null) {
                                $retry531 = false;
                                throw $e532;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e532) {
                            $commit530 = false;
                            if ($tm534.checkForStaleObjects())
                                continue $label529;
                            $retry531 = false;
                            throw new fabric.worker.AbortException($e532);
                        }
                        finally {
                            if ($commit530) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e532) {
                                    $commit530 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e532) {
                                    $commit530 = false;
                                    fabric.common.TransactionID $currentTid533 =
                                      $tm534.getCurrentTid();
                                    if ($currentTid533 != null) {
                                        if ($e532.tid.equals($currentTid533) ||
                                              !$e532.tid.isDescendantOf(
                                                           $currentTid533)) {
                                            throw $e532;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit530 && $retry531) {
                                {  }
                                continue $label529;
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
    
    public static final byte[] $classHash = new byte[] { -44, 35, 52, 118, -64,
    -99, -70, 99, -46, -91, 105, -87, -97, 23, -125, 45, 13, -102, 48, 10, -43,
    -98, 9, -7, -101, 36, -63, 66, 82, -123, 61, 33 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524675608000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
