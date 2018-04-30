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
        
        public void activate() { refresh(false); }
        
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
                    fabric.worker.transaction.TransactionManager $tm469 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled472 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff470 = 1;
                    boolean $doBackoff471 = true;
                    boolean $retry466 = true;
                    $label464: for (boolean $commit465 = false; !$commit465; ) {
                        if ($backoffEnabled472) {
                            if ($doBackoff471) {
                                if ($backoff470 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff470);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e467) {
                                            
                                        }
                                    }
                                }
                                if ($backoff470 < 5000) $backoff470 *= 2;
                            }
                            $doBackoff471 = $backoff470 <= 32 || !$doBackoff471;
                        }
                        $commit465 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().thresholdPolicy(
                                                     tmp.get$rate(),
                                                     tmp.get$base(), true,
                                                     tmp.$getStore()));
                        }
                        catch (final fabric.worker.RetryException $e467) {
                            $commit465 = false;
                            continue $label464;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e467) {
                            $commit465 = false;
                            fabric.common.TransactionID $currentTid468 =
                              $tm469.getCurrentTid();
                            if ($e467.tid.isDescendantOf($currentTid468))
                                continue $label464;
                            if ($currentTid468.parent != null) {
                                $retry466 = false;
                                throw $e467;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e467) {
                            $commit465 = false;
                            if ($tm469.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid468 =
                              $tm469.getCurrentTid();
                            if ($e467.tid.isDescendantOf($currentTid468)) {
                                $retry466 = true;
                            }
                            else if ($currentTid468.parent != null) {
                                $retry466 = false;
                                throw $e467;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e467) {
                            $commit465 = false;
                            if ($tm469.checkForStaleObjects())
                                continue $label464;
                            $retry466 = false;
                            throw new fabric.worker.AbortException($e467);
                        }
                        finally {
                            if ($commit465) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e467) {
                                    $commit465 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e467) {
                                    $commit465 = false;
                                    fabric.common.TransactionID $currentTid468 =
                                      $tm469.getCurrentTid();
                                    if ($currentTid468 != null) {
                                        if ($e467.tid.equals($currentTid468) ||
                                              !$e467.tid.isDescendantOf(
                                                           $currentTid468)) {
                                            throw $e467;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit465 && $retry466) {
                                {  }
                                continue $label464;
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
                    fabric.worker.transaction.TransactionManager $tm478 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled481 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff479 = 1;
                    boolean $doBackoff480 = true;
                    boolean $retry475 = true;
                    $label473: for (boolean $commit474 = false; !$commit474; ) {
                        if ($backoffEnabled481) {
                            if ($doBackoff480) {
                                if ($backoff479 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff479);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e476) {
                                            
                                        }
                                    }
                                }
                                if ($backoff479 < 5000) $backoff479 *= 2;
                            }
                            $doBackoff480 = $backoff479 <= 32 || !$doBackoff480;
                        }
                        $commit474 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e476) {
                            $commit474 = false;
                            continue $label473;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e476) {
                            $commit474 = false;
                            fabric.common.TransactionID $currentTid477 =
                              $tm478.getCurrentTid();
                            if ($e476.tid.isDescendantOf($currentTid477))
                                continue $label473;
                            if ($currentTid477.parent != null) {
                                $retry475 = false;
                                throw $e476;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e476) {
                            $commit474 = false;
                            if ($tm478.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid477 =
                              $tm478.getCurrentTid();
                            if ($e476.tid.isDescendantOf($currentTid477)) {
                                $retry475 = true;
                            }
                            else if ($currentTid477.parent != null) {
                                $retry475 = false;
                                throw $e476;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e476) {
                            $commit474 = false;
                            if ($tm478.checkForStaleObjects())
                                continue $label473;
                            $retry475 = false;
                            throw new fabric.worker.AbortException($e476);
                        }
                        finally {
                            if ($commit474) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e476) {
                                    $commit474 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e476) {
                                    $commit474 = false;
                                    fabric.common.TransactionID $currentTid477 =
                                      $tm478.getCurrentTid();
                                    if ($currentTid477 != null) {
                                        if ($e476.tid.equals($currentTid477) ||
                                              !$e476.tid.isDescendantOf(
                                                           $currentTid477)) {
                                            throw $e476;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit474 && $retry475) {
                                {  }
                                continue $label473;
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
                    boolean rtn = update(curExpiry, asyncExtension);
                    this.get$currentPolicy().
                      apply((fabric.metrics.contracts.MetricContract)
                              this.$getProxy());
                    return rtn;
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
    
    public static final byte[] $classHash = new byte[] { -47, 90, -76, -118,
    117, -15, -48, 83, -10, 73, -49, -71, -30, -50, -25, 15, 37, 24, -3, -112,
    -103, -100, -31, 102, -104, 89, -108, 50, 48, 117, 24, 103 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525096543000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
