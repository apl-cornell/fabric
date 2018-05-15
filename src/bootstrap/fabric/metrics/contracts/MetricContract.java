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
                tmp.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(tmp.get$currentPolicy()));
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
                            tmp.set$currentPolicy(
                                  tmp.get$metric().thresholdPolicy(
                                                     tmp.get$rate(),
                                                     tmp.get$base(), true,
                                                     tmp.$getStore()));
                            tmp.
                              set$$associated(
                                fabric.worker.metrics.ImmutableSet.emptySet().
                                    add(tmp.get$currentPolicy()));
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
            tmp.get$currentPolicy().activate();
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.metrics.contracts.Contract._Impl.static_activate(tmp);
                tmp.getMetric().addContract(tmp);
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
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
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
                this.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(this.get$currentPolicy()));
                this.get$currentPolicy().
                  apply((fabric.metrics.contracts.MetricContract)
                          this.$getProxy());
            } else {
                this.set$currentPolicy(null);
                this.set$$associated(null);
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
    
    public static final byte[] $classHash = new byte[] { 40, -1, 117, -3, 126,
    81, -58, 69, -90, -20, -80, 93, -6, 85, 61, -108, -102, -60, 38, 70, -119,
    -49, 81, 40, -8, -91, -3, -91, 46, 65, -89, -75 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526344812000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
