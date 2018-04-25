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
                    fabric.worker.transaction.TransactionManager $tm79 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled82 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff80 = 1;
                    boolean $doBackoff81 = true;
                    boolean $retry76 = true;
                    $label74: for (boolean $commit75 = false; !$commit75; ) {
                        if ($backoffEnabled82) {
                            if ($doBackoff81) {
                                if ($backoff80 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff80);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e77) {
                                            
                                        }
                                    }
                                }
                                if ($backoff80 < 5000) $backoff80 *= 2;
                            }
                            $doBackoff81 = $backoff80 <= 32 || !$doBackoff81;
                        }
                        $commit75 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().thresholdPolicy(
                                                     tmp.get$rate(),
                                                     tmp.get$base(), true,
                                                     tmp.$getStore()));
                        }
                        catch (final fabric.worker.RetryException $e77) {
                            $commit75 = false;
                            continue $label74;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e77) {
                            $commit75 = false;
                            fabric.common.TransactionID $currentTid78 =
                              $tm79.getCurrentTid();
                            if ($e77.tid.isDescendantOf($currentTid78))
                                continue $label74;
                            if ($currentTid78.parent != null) {
                                $retry76 = false;
                                throw $e77;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e77) {
                            $commit75 = false;
                            if ($tm79.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid78 =
                              $tm79.getCurrentTid();
                            if ($e77.tid.isDescendantOf($currentTid78)) {
                                $retry76 = true;
                            }
                            else if ($currentTid78.parent != null) {
                                $retry76 = false;
                                throw $e77;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e77) {
                            $commit75 = false;
                            if ($tm79.checkForStaleObjects()) continue $label74;
                            $retry76 = false;
                            throw new fabric.worker.AbortException($e77);
                        }
                        finally {
                            if ($commit75) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e77) {
                                    $commit75 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e77) {
                                    $commit75 = false;
                                    fabric.common.TransactionID $currentTid78 =
                                      $tm79.getCurrentTid();
                                    if ($currentTid78 != null) {
                                        if ($e77.tid.equals($currentTid78) ||
                                              !$e77.tid.isDescendantOf(
                                                          $currentTid78)) {
                                            throw $e77;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit75 && $retry76) {
                                {  }
                                continue $label74;
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
                    fabric.worker.transaction.TransactionManager $tm88 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled91 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff89 = 1;
                    boolean $doBackoff90 = true;
                    boolean $retry85 = true;
                    $label83: for (boolean $commit84 = false; !$commit84; ) {
                        if ($backoffEnabled91) {
                            if ($doBackoff90) {
                                if ($backoff89 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff89);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e86) {
                                            
                                        }
                                    }
                                }
                                if ($backoff89 < 5000) $backoff89 *= 2;
                            }
                            $doBackoff90 = $backoff89 <= 32 || !$doBackoff90;
                        }
                        $commit84 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e86) {
                            $commit84 = false;
                            continue $label83;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e86) {
                            $commit84 = false;
                            fabric.common.TransactionID $currentTid87 =
                              $tm88.getCurrentTid();
                            if ($e86.tid.isDescendantOf($currentTid87))
                                continue $label83;
                            if ($currentTid87.parent != null) {
                                $retry85 = false;
                                throw $e86;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e86) {
                            $commit84 = false;
                            if ($tm88.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid87 =
                              $tm88.getCurrentTid();
                            if ($e86.tid.isDescendantOf($currentTid87)) {
                                $retry85 = true;
                            }
                            else if ($currentTid87.parent != null) {
                                $retry85 = false;
                                throw $e86;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e86) {
                            $commit84 = false;
                            if ($tm88.checkForStaleObjects()) continue $label83;
                            $retry85 = false;
                            throw new fabric.worker.AbortException($e86);
                        }
                        finally {
                            if ($commit84) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e86) {
                                    $commit84 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e86) {
                                    $commit84 = false;
                                    fabric.common.TransactionID $currentTid87 =
                                      $tm88.getCurrentTid();
                                    if ($currentTid87 != null) {
                                        if ($e86.tid.equals($currentTid87) ||
                                              !$e86.tid.isDescendantOf(
                                                          $currentTid87)) {
                                            throw $e86;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit84 && $retry85) {
                                {  }
                                continue $label83;
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
                return new fabric.metrics.contracts.MetricContract._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 61, 95, 22, -13, -7,
    -117, -26, -24, 120, -39, -88, -123, -103, 12, 82, -19, -14, 42, 74, 115,
    65, 122, 15, 38, 13, -89, -26, -103, -7, -20, 76, 124 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524613297000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
