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
 * of either a {@link Metric} or a set of {@link MetricContract}s and can be
 * observed by other {@link Contract}s.
 */
public interface MetricContract extends fabric.metrics.contracts.Contract {
    public fabric.metrics.Metric get$metric();
    
    public fabric.metrics.Metric set$metric(fabric.metrics.Metric val);
    
    public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics();
    
    public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
      fabric.worker.metrics.ImmutableMetricsVector val);
    
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
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
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
        
        public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              get$leafMetrics();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              set$leafMetrics(val);
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
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
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
                tmp.set$currentPolicy(
                      tmp.get$metric().thresholdPolicy(tmp.get$rate(),
                                                       tmp.get$base(), false,
                                                       tmp.$getStore()));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm507 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled510 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff508 = 1;
                    boolean $doBackoff509 = true;
                    boolean $retry504 = true;
                    $label502: for (boolean $commit503 = false; !$commit503; ) {
                        if ($backoffEnabled510) {
                            if ($doBackoff509) {
                                if ($backoff508 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff508);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e505) {
                                            
                                        }
                                    }
                                }
                                if ($backoff508 < 5000) $backoff508 *= 2;
                            }
                            $doBackoff509 = $backoff508 <= 32 || !$doBackoff509;
                        }
                        $commit503 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().thresholdPolicy(
                                                     tmp.get$rate(),
                                                     tmp.get$base(), true,
                                                     tmp.$getStore()));
                        }
                        catch (final fabric.worker.RetryException $e505) {
                            $commit503 = false;
                            continue $label502;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e505) {
                            $commit503 = false;
                            fabric.common.TransactionID $currentTid506 =
                              $tm507.getCurrentTid();
                            if ($e505.tid.isDescendantOf($currentTid506))
                                continue $label502;
                            if ($currentTid506.parent != null) {
                                $retry504 = false;
                                throw $e505;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e505) {
                            $commit503 = false;
                            if ($tm507.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid506 =
                              $tm507.getCurrentTid();
                            if ($e505.tid.isDescendantOf($currentTid506)) {
                                $retry504 = true;
                            }
                            else if ($currentTid506.parent != null) {
                                $retry504 = false;
                                throw $e505;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e505) {
                            $commit503 = false;
                            if ($tm507.checkForStaleObjects())
                                continue $label502;
                            $retry504 = false;
                            throw new fabric.worker.AbortException($e505);
                        }
                        finally {
                            if ($commit503) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e505) {
                                    $commit503 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e505) {
                                    $commit503 = false;
                                    fabric.common.TransactionID $currentTid506 =
                                      $tm507.getCurrentTid();
                                    if ($currentTid506 != null) {
                                        if ($e505.tid.equals($currentTid506) ||
                                              !$e505.tid.isDescendantOf(
                                                           $currentTid506)) {
                                            throw $e505;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit503 && $retry504) {
                                {  }
                                continue $label502;
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
                    fabric.worker.transaction.TransactionManager $tm516 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled519 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff517 = 1;
                    boolean $doBackoff518 = true;
                    boolean $retry513 = true;
                    $label511: for (boolean $commit512 = false; !$commit512; ) {
                        if ($backoffEnabled519) {
                            if ($doBackoff518) {
                                if ($backoff517 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff517);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e514) {
                                            
                                        }
                                    }
                                }
                                if ($backoff517 < 5000) $backoff517 *= 2;
                            }
                            $doBackoff518 = $backoff517 <= 32 || !$doBackoff518;
                        }
                        $commit512 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e514) {
                            $commit512 = false;
                            continue $label511;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e514) {
                            $commit512 = false;
                            fabric.common.TransactionID $currentTid515 =
                              $tm516.getCurrentTid();
                            if ($e514.tid.isDescendantOf($currentTid515))
                                continue $label511;
                            if ($currentTid515.parent != null) {
                                $retry513 = false;
                                throw $e514;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e514) {
                            $commit512 = false;
                            if ($tm516.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid515 =
                              $tm516.getCurrentTid();
                            if ($e514.tid.isDescendantOf($currentTid515)) {
                                $retry513 = true;
                            }
                            else if ($currentTid515.parent != null) {
                                $retry513 = false;
                                throw $e514;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e514) {
                            $commit512 = false;
                            if ($tm516.checkForStaleObjects())
                                continue $label511;
                            $retry513 = false;
                            throw new fabric.worker.AbortException($e514);
                        }
                        finally {
                            if ($commit512) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e514) {
                                    $commit512 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e514) {
                                    $commit512 = false;
                                    fabric.common.TransactionID $currentTid515 =
                                      $tm516.getCurrentTid();
                                    if ($currentTid515 != null) {
                                        if ($e514.tid.equals($currentTid515) ||
                                              !$e514.tid.isDescendantOf(
                                                           $currentTid515)) {
                                            throw $e514;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit512 && $retry513) {
                                {  }
                                continue $label511;
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
                      apply((fabric.metrics.contracts.MetricContract)
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
              this.get$metric().thresholdPolicy(this.get$rate(),
                                                this.get$base(), $getStore());
            newPolicy.activate();
            long newExpiry = newPolicy.expiry();
            if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null))
                oldPolicy.unapply((fabric.metrics.contracts.MetricContract)
                                    this.$getProxy());
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
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return this.get$leafMetrics();
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
            $writeInline(out, this.leafMetrics);
            out.writeDouble(this.rate);
            out.writeDouble(this.base);
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
            this.rate = in.readDouble();
            this.base = in.readDouble();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.MetricContract._Impl src =
              (fabric.metrics.contracts.MetricContract._Impl) other;
            this.metric = src.metric;
            this.leafMetrics = src.leafMetrics;
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
                return new fabric.metrics.contracts.MetricContract._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 65, 64, 59, -12, 68,
    -124, -118, 103, 31, -84, -82, -93, 50, -17, 50, 84, 58, -82, -39, 84, 61,
    48, 63, 70, -68, -128, 38, -44, 2, -58, 102, 98 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524675608000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
