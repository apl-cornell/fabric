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
                    fabric.worker.transaction.TransactionManager $tm506 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled509 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff507 = 1;
                    boolean $doBackoff508 = true;
                    boolean $retry503 = true;
                    $label501: for (boolean $commit502 = false; !$commit502; ) {
                        if ($backoffEnabled509) {
                            if ($doBackoff508) {
                                if ($backoff507 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff507);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e504) {
                                            
                                        }
                                    }
                                }
                                if ($backoff507 < 5000) $backoff507 *= 2;
                            }
                            $doBackoff508 = $backoff507 <= 32 || !$doBackoff508;
                        }
                        $commit502 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().thresholdPolicy(
                                                     tmp.get$rate(),
                                                     tmp.get$base(), true,
                                                     tmp.$getStore()));
                        }
                        catch (final fabric.worker.RetryException $e504) {
                            $commit502 = false;
                            continue $label501;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e504) {
                            $commit502 = false;
                            fabric.common.TransactionID $currentTid505 =
                              $tm506.getCurrentTid();
                            if ($e504.tid.isDescendantOf($currentTid505))
                                continue $label501;
                            if ($currentTid505.parent != null) {
                                $retry503 = false;
                                throw $e504;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e504) {
                            $commit502 = false;
                            if ($tm506.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid505 =
                              $tm506.getCurrentTid();
                            if ($e504.tid.isDescendantOf($currentTid505)) {
                                $retry503 = true;
                            }
                            else if ($currentTid505.parent != null) {
                                $retry503 = false;
                                throw $e504;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e504) {
                            $commit502 = false;
                            if ($tm506.checkForStaleObjects())
                                continue $label501;
                            $retry503 = false;
                            throw new fabric.worker.AbortException($e504);
                        }
                        finally {
                            if ($commit502) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e504) {
                                    $commit502 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e504) {
                                    $commit502 = false;
                                    fabric.common.TransactionID $currentTid505 =
                                      $tm506.getCurrentTid();
                                    if ($currentTid505 != null) {
                                        if ($e504.tid.equals($currentTid505) ||
                                              !$e504.tid.isDescendantOf(
                                                           $currentTid505)) {
                                            throw $e504;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit502 && $retry503) {
                                {  }
                                continue $label501;
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
                    fabric.worker.transaction.TransactionManager $tm515 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled518 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff516 = 1;
                    boolean $doBackoff517 = true;
                    boolean $retry512 = true;
                    $label510: for (boolean $commit511 = false; !$commit511; ) {
                        if ($backoffEnabled518) {
                            if ($doBackoff517) {
                                if ($backoff516 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff516);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e513) {
                                            
                                        }
                                    }
                                }
                                if ($backoff516 < 5000) $backoff516 *= 2;
                            }
                            $doBackoff517 = $backoff516 <= 32 || !$doBackoff517;
                        }
                        $commit511 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e513) {
                            $commit511 = false;
                            continue $label510;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e513) {
                            $commit511 = false;
                            fabric.common.TransactionID $currentTid514 =
                              $tm515.getCurrentTid();
                            if ($e513.tid.isDescendantOf($currentTid514))
                                continue $label510;
                            if ($currentTid514.parent != null) {
                                $retry512 = false;
                                throw $e513;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e513) {
                            $commit511 = false;
                            if ($tm515.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid514 =
                              $tm515.getCurrentTid();
                            if ($e513.tid.isDescendantOf($currentTid514)) {
                                $retry512 = true;
                            }
                            else if ($currentTid514.parent != null) {
                                $retry512 = false;
                                throw $e513;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e513) {
                            $commit511 = false;
                            if ($tm515.checkForStaleObjects())
                                continue $label510;
                            $retry512 = false;
                            throw new fabric.worker.AbortException($e513);
                        }
                        finally {
                            if ($commit511) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e513) {
                                    $commit511 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e513) {
                                    $commit511 = false;
                                    fabric.common.TransactionID $currentTid514 =
                                      $tm515.getCurrentTid();
                                    if ($currentTid514 != null) {
                                        if ($e513.tid.equals($currentTid514) ||
                                              !$e513.tid.isDescendantOf(
                                                           $currentTid514)) {
                                            throw $e513;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit511 && $retry512) {
                                {  }
                                continue $label510;
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
    
    public static final byte[] $classHash = new byte[] { 66, 81, -86, 81, 29,
    64, -109, -96, 42, 122, -23, 55, 22, -91, -75, -58, -108, -78, -89, -128,
    25, 111, 84, -55, -68, -53, 39, 91, -113, -108, 22, -54 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525364618000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVYfWwcxRWfO9tnn2NixyEmcRLHsY/QhHDXQEUVDC32NR9XLo1jO0Y4Kte93Tl78d7usjtnX5KmCkgoEUVuRZwQULAKNQJSl6hUUauCpagqHyEICkX9+KNt/kEFhfyBqpaqpaXvzc7d3q33DrtST5p5czPvzbz35r3fzOzcVVJnW6QrI6VVLcoOmtSO7pTSiWS/ZNlUiWuSbQ9Bb0peVps49cGzSkeQBJOkSZZ0Q1dlSUvpNiPLk/dJE1JMpyy2fyDRc4CEZRTcLdljjAQP9OUt0mka2sFRzWBikQXzn7wxNv3YvS0v1pDmEdKs6oNMYqocN3RG82yENGVpNk0tu1dRqDJCVuiUKoPUUiVNPQSMhj5CWm11VJdYzqL2ALUNbQIZW+2cSS2+ZqET1TdAbSsnM8MC9Vsc9XNM1WJJ1WY9SRLKqFRT7PvJd0htktRlNGkUGNuSBStifMbYTuwH9kYV1LQykkwLIrXjqq4wssErUbQ4chcwgGh9lrIxo7hUrS5BB2l1VNIkfTQ2yCxVHwXWOiMHqzDSXnFSYGowJXlcGqUpRlZ7+fqdIeAKc7egCCOrvGx8Jtizds+elezW1W/cPnVY360HSQB0Vqisof4NINThERqgGWpRXaaOYNOW5Cmpbf54kBBgXuVhdnh+9u2P79zaceF1h2etD8/e9H1UZil5Nr38nXXxzdtrUI0G07BVDIUyy/mu9ouRnrwJ0d5WnBEHo4XBCwOv3nP0LL0SJI0JEpINLZeFqFohG1lT1ai1i+rUkhhVEiRMdSXOxxOkHtpJVadO795MxqYsQWo13hUy+H9wUQamQBfVQ1vVM0ahbUpsjLfzJiGkHgoJQHmPkLWngW4gpLaBkeHYmJGlsbSWo5MQ3jEoVLLksRjkraXKMduSY1ZOZyowiS6IIiB2DEKdWZLM7Nge3hMX/6Ogkfl/mzmPNrVMBgLg7g2yodC0ZMPeiTjq69cgVXYbmkKtlKxNzSfIyvnHeSyFMf5tiGHurQDs/zovcpTKTuf6dnz8QuqSE4coK5zJyA2OulGhbrSobrRcXdCwCZMtCvAVBfiaC+Sj8ZnEj3hMhWyefMVJm2DS20xNYhnDyuZJIMAtvJbL82CCUBgHiAEUado8+M2vf+t4Vw1EsTlZixsLrBFvTrlIlICWBImSkpuPffD3c6eOGG52MRJZkPQLJTFpu7zusgyZKgCK7vRbOqXzqfkjkSACThj9IkG0ArB0eNcoS96eAhCiN+qSZBn6QNJwqIBejWzMMibdHh4Gy7FqdSICneVRkGPoHYPmk79/68Nb+OlSgNvmElwepKynJMVxsmaezCtc3w9ZlALfH0/3nzh59dgB7njg6PZbMIJ1HFJbgpw2rIdev/8Pf/7T7HtBd7MYCZm5tKbKeW7Lis/gF4DyHyyYp9iBFNA6LjCiswgSJq68ydUN4EIDyALV7ch+PWsoakaV0hrFSPm0+fpt5z+aanG2W4Mex3kW2fr5E7j9a/rI0Uv3ftLBpwnIeFy5/nPZHAxc6c7ca1nSQdQj/8C76x9/TXoSIh8QzFYPUQ5KhPuD8A28mfviJl5v84x9Casux1vreH+tvfA82IkHqxuLI7G5M+3xr1xxkr8YizjHRp/kH5ZK0uTms9m/BbtCrwRJ/Qhp4We6pLNhCbAMwmAETmU7LjqT5Jqy8fIT1jlOeoq5ts6bByXLerPABR1oIze2G53AdwIHHNGKTuqEshGcsk/QOI6uNLG+Nh8gvHEbF+nm9SasNnNHBhkJm5bBQEsKt4qwms3mGO4+X+dGCFWBcvh3FRzpHuxzEA8H2500xPrWonotqN4tULpArYcETfuoF6+oXr1pqRMQ+Nj51YJWyzQqZZy17YJqW4Vqk4Y1Tq2ihomCRYJ9mPLLGQqt8cKunwnNaMJaKN2g+lOCnvQxYU8FE7C5pUz7Wkxknwjut9QsgNCEuNHQ49MPfxadmnay17n2dS+4eZXKOFc/vsw1fK08rLKx2ipcYudfzh156bkjx5xrUWv5JWaHnsv++Lf/fjN6+vJFn8MwpBiAZbSq5yLgsXlBf+LjuXsW7zk87bE9xBfM+wsGuGC+qAj/hcSlp17QQIkiJchC0GXrK91PubtmH5yeUfY+sy0o4GkY8oYZ5k0anaBayVRhdP6C988efit3sebylfXb4+PvjzrO3+BZ2cv9/J65i7s2yY8GSU0RVBY8BcqFesqhpNGi8JLRh8oApbPoqzD6QIXyRXCZLGhN6aa5W92NVaooGkTRBiESdGjdp143uxBfw73E5+4t7ukQn1+vchrwuVRGvuDke0QkeqR4DYuUX8MirsKZcjMxNntB5bcEfXlpZqLIS4Ker2xmqe6TVcYOYgV3pfAoZS6s9vopDhBEEtA+I+iDS1McRR4Q9PDiFD9aZYyvfpiRlaD4jrzJj5KkmqF4tecCAwKLkOyHJNYMfdTPrNVQBghpfFrQE0szC0UeFfSRxZn1SJWxKayOMdIAMcRPIF9bJgxV8djCIWc7lLsJWdbt0MZ/VbDFF/R2MXwf4GeKfLmZLWK2fwr6UWUzAy4UtvAFn6hi6xmsTsDF2Fk1VTAZu7/vt1WQ2uS7YOongv56aVuFIm8LevFzbfBzfH3aMOASoPPFfljFtOewmgEBi2bgDcrfxLN+JvVD+SUha2YEvXtpJqHIsKD9/xPonatiBT82z4IVatbUVCcUfa1oh3IJiCLo4NKsQJEBQZOLy6GfVxn7BVY/hRxihvOxqXBZa+GvBLwjR0sGFlzI/Cz8GpR3ALzzgt6xNAtR5HZBb12cha9UGXsNqwuQOQB+SbiUDub4lZ/zJvKMLC8/jPCFtNbns4X4tCbHf0Vn379r66oKnyxWL/jYKeRemGluuG5m/+/4y7v42SwMD9tMTtNKXxIl7ZAJOaFyM8LOu8Lk5E1GVlf6xsGctxRvcyPfcGTeBlPLZRj/Alm8oQu+dwHaHD789xvu+3a3KsTHxorfWAqe5Ox8yvachd+C5/563T9CDUOX+Usbtqezb9/ZfevvPPGDLYc+/HLbM+dfnX7x2aNrjKGLL1+64cD3ptve+C8k4/7PoxYAAA==";
}
