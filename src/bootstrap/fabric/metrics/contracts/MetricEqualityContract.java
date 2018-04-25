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
                    fabric.worker.transaction.TransactionManager $tm97 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled100 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff98 = 1;
                    boolean $doBackoff99 = true;
                    boolean $retry94 = true;
                    $label92: for (boolean $commit93 = false; !$commit93; ) {
                        if ($backoffEnabled100) {
                            if ($doBackoff99) {
                                if ($backoff98 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff98);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e95) {
                                            
                                        }
                                    }
                                }
                                if ($backoff98 < 5000) $backoff98 *= 2;
                            }
                            $doBackoff99 = $backoff98 <= 32 || !$doBackoff99;
                        }
                        $commit93 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().equalityPolicy(
                                                     tmp.get$value(), false,
                                                     tmp.$getStore()));
                        }
                        catch (final fabric.worker.RetryException $e95) {
                            $commit93 = false;
                            continue $label92;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e95) {
                            $commit93 = false;
                            fabric.common.TransactionID $currentTid96 =
                              $tm97.getCurrentTid();
                            if ($e95.tid.isDescendantOf($currentTid96))
                                continue $label92;
                            if ($currentTid96.parent != null) {
                                $retry94 = false;
                                throw $e95;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e95) {
                            $commit93 = false;
                            if ($tm97.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid96 =
                              $tm97.getCurrentTid();
                            if ($e95.tid.isDescendantOf($currentTid96)) {
                                $retry94 = true;
                            }
                            else if ($currentTid96.parent != null) {
                                $retry94 = false;
                                throw $e95;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e95) {
                            $commit93 = false;
                            if ($tm97.checkForStaleObjects()) continue $label92;
                            $retry94 = false;
                            throw new fabric.worker.AbortException($e95);
                        }
                        finally {
                            if ($commit93) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e95) {
                                    $commit93 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e95) {
                                    $commit93 = false;
                                    fabric.common.TransactionID $currentTid96 =
                                      $tm97.getCurrentTid();
                                    if ($currentTid96 != null) {
                                        if ($e95.tid.equals($currentTid96) ||
                                              !$e95.tid.isDescendantOf(
                                                          $currentTid96)) {
                                            throw $e95;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit93 && $retry94) {
                                {  }
                                continue $label92;
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
                    fabric.worker.transaction.TransactionManager $tm106 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled109 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff107 = 1;
                    boolean $doBackoff108 = true;
                    boolean $retry103 = true;
                    $label101: for (boolean $commit102 = false; !$commit102; ) {
                        if ($backoffEnabled109) {
                            if ($doBackoff108) {
                                if ($backoff107 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff107);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e104) {
                                            
                                        }
                                    }
                                }
                                if ($backoff107 < 5000) $backoff107 *= 2;
                            }
                            $doBackoff108 = $backoff107 <= 32 || !$doBackoff108;
                        }
                        $commit102 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e104) {
                            $commit102 = false;
                            continue $label101;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e104) {
                            $commit102 = false;
                            fabric.common.TransactionID $currentTid105 =
                              $tm106.getCurrentTid();
                            if ($e104.tid.isDescendantOf($currentTid105))
                                continue $label101;
                            if ($currentTid105.parent != null) {
                                $retry103 = false;
                                throw $e104;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e104) {
                            $commit102 = false;
                            if ($tm106.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid105 =
                              $tm106.getCurrentTid();
                            if ($e104.tid.isDescendantOf($currentTid105)) {
                                $retry103 = true;
                            }
                            else if ($currentTid105.parent != null) {
                                $retry103 = false;
                                throw $e104;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e104) {
                            $commit102 = false;
                            if ($tm106.checkForStaleObjects())
                                continue $label101;
                            $retry103 = false;
                            throw new fabric.worker.AbortException($e104);
                        }
                        finally {
                            if ($commit102) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e104) {
                                    $commit102 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e104) {
                                    $commit102 = false;
                                    fabric.common.TransactionID $currentTid105 =
                                      $tm106.getCurrentTid();
                                    if ($currentTid105 != null) {
                                        if ($e104.tid.equals($currentTid105) ||
                                              !$e104.tid.isDescendantOf(
                                                           $currentTid105)) {
                                            throw $e104;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit102 && $retry103) {
                                {  }
                                continue $label101;
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
    
    public static final byte[] $classHash = new byte[] { 63, 12, -45, -15, 41,
    -98, 25, 61, 87, 66, 18, -79, 121, -8, -99, 74, 21, 17, -104, -58, -55,
    -107, -112, 88, 82, -12, -120, -8, 50, -68, 65, -62 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524613302000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
