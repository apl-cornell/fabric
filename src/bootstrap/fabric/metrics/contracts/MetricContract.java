package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.Metric;
import fabric.metrics.util.Subject;

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
    
    public fabric.metrics.contracts.Bound get$bound();
    
    public fabric.metrics.contracts.Bound set$bound(
      fabric.metrics.contracts.Bound val);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      get$currentPolicy();
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      set$currentPolicy(fabric.metrics.contracts.enforcement.EnforcementPolicy val);
    
    /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param bound
   *        the {@link Bound} this {@link MetricContract} asserts on
   *        metric.
   */
    public fabric.metrics.contracts.MetricContract
      fabric$metrics$contracts$MetricContract$(
      fabric.metrics.Metric metric, fabric.metrics.contracts.Bound bound);
    
    /** @return the {@link Metric} that this contract observes. */
    public fabric.metrics.Metric getMetric();
    
    /** @return the {@link Bound} that this contract observes. */
    public fabric.metrics.contracts.Bound getBound();
    
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
    public void refresh();
    
    /**
   * Check if this implies another {@link MetricContract} being considered.
   *
   * @param otherMetric
   *        the {@link Metric} the other {@link MetricContract} would
   *        assert a bound on
   * @param otherBound
   *        the {@link Bound} that would be used by the other
   *        {@link MetricContract}
   * @return true iff this would imply (and therefore) can enforce another
   *       {@link MetricContract} with the given metric and bound.
   */
    public boolean implies(
      fabric.metrics.Metric otherMetric, fabric.metrics.contracts.Bound otherBound);
    
    /**
   * Check if this implies the other {@link MetricContract}.
   *
   * @param other
   *        the other {@link MetricContract} this is being compared with
   * @return true iff this would imply (and therefore) can enforce other for
   *       the entire duration of other.
   */
    public boolean implies(fabric.metrics.contracts.MetricContract other);
    
    /**
   * Check if this enforces the <strong>same</strong> bound as another
   * {@link MetricContract} being considered.
   *
   * @param otherMetric
   *        the {@link Metric} the other {@link MetricContract} would
   *        assert a bound on
   * @param otherBound
   *        the {@link Bound} that would be used by the other
   *        {@link MetricContract}
   * @return true iff this enforces another {@link MetricContract} with the
   *       given parameters.
   */
    public boolean enforces(
      fabric.metrics.Metric otherMetric, fabric.metrics.contracts.Bound otherBound);
    
    /**
   * Check if this enforces the <strong>same</strong> bound as another
   * {@link MetricContract}.
   *
   * @param other
   *        the other {@link MetricContract} this is being compared with
   * @return true iff this enforces the same bound as other.
   */
    public boolean enforces(fabric.metrics.contracts.MetricContract other);
    
    public java.lang.String toString();
    
    /**
   * Given the current transaction context's view of the system state, return
   * a new {@link EnforcementPolicy} to enforce this {@link MetricContract}.
   *
   * @return The {@link EnforcementPolicy} to use for this
   *       {@link MetricContract} after the call completes.
   */
    public abstract fabric.metrics.contracts.enforcement.EnforcementPolicy
      enforcementStrategy();
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      directStrategy();
    
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
        
        public fabric.metrics.contracts.Bound get$bound() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              get$bound();
        }
        
        public fabric.metrics.contracts.Bound set$bound(
          fabric.metrics.contracts.Bound val) {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              set$bound(val);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          get$currentPolicy() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              get$currentPolicy();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          set$currentPolicy(
          fabric.metrics.contracts.enforcement.EnforcementPolicy val) {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              set$currentPolicy(val);
        }
        
        public fabric.metrics.contracts.MetricContract
          fabric$metrics$contracts$MetricContract$(
          fabric.metrics.Metric arg1, fabric.metrics.contracts.Bound arg2) {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              fabric$metrics$contracts$MetricContract$(arg1, arg2);
        }
        
        public fabric.metrics.Metric getMetric() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              getMetric();
        }
        
        public fabric.metrics.contracts.Bound getBound() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).getBound(
                                                                         );
        }
        
        public long getExpectedLifetime() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              getExpectedLifetime();
        }
        
        public boolean implies(fabric.metrics.Metric arg1,
                               fabric.metrics.contracts.Bound arg2) {
            return ((fabric.metrics.contracts.MetricContract) fetch()).implies(
                                                                         arg1,
                                                                         arg2);
        }
        
        public boolean implies(fabric.metrics.contracts.MetricContract arg1) {
            return ((fabric.metrics.contracts.MetricContract) fetch()).implies(
                                                                         arg1);
        }
        
        public boolean enforces(fabric.metrics.Metric arg1,
                                fabric.metrics.contracts.Bound arg2) {
            return ((fabric.metrics.contracts.MetricContract) fetch()).enforces(
                                                                         arg1,
                                                                         arg2);
        }
        
        public boolean enforces(fabric.metrics.contracts.MetricContract arg1) {
            return ((fabric.metrics.contracts.MetricContract) fetch()).enforces(
                                                                         arg1);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          enforcementStrategy() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              enforcementStrategy();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          directStrategy() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              directStrategy();
        }
        
        public _Proxy(MetricContract._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.metrics.contracts.Contract._Impl
      implements fabric.metrics.contracts.MetricContract {
        public fabric.metrics.Metric get$metric() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.metric;
        }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.metric = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.Metric metric;
        
        public fabric.metrics.contracts.Bound get$bound() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.bound;
        }
        
        public fabric.metrics.contracts.Bound set$bound(
          fabric.metrics.contracts.Bound val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.bound = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.contracts.Bound bound;
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          get$currentPolicy() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.currentPolicy;
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          set$currentPolicy(
          fabric.metrics.contracts.enforcement.EnforcementPolicy val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.currentPolicy = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.contracts.enforcement.EnforcementPolicy currentPolicy;
        
        /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param bound
   *        the {@link Bound} this {@link MetricContract} asserts on
   *        metric.
   */
        public fabric.metrics.contracts.MetricContract
          fabric$metrics$contracts$MetricContract$(
          fabric.metrics.Metric metric, fabric.metrics.contracts.Bound bound) {
            this.set$metric(metric);
            this.set$bound(bound);
            fabric$metrics$contracts$Contract$();
            return (fabric.metrics.contracts.MetricContract) this.$getProxy();
        }
        
        /** @return the {@link Metric} that this contract observes. */
        public fabric.metrics.Metric getMetric() { return this.get$metric(); }
        
        /** @return the {@link Bound} that this contract observes. */
        public fabric.metrics.contracts.Bound getBound() {
            return this.get$bound();
        }
        
        /**
   * @return the expected lifetime of this {@link MetricContract} given the
   *       associated {@link Metric}s current velocity.
   */
        public long getExpectedLifetime() {
            return getMetric().expectedTimeToHit(
                                 getBound(),
                                 java.lang.System.currentTimeMillis());
        }
        
        public void activate() {
            super.activate();
            getMetric().addContract((fabric.metrics.contracts.MetricContract)
                                      this.$getProxy());
        }
        
        /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
        public void refresh() {
            long currentTime = java.lang.System.currentTimeMillis();
            fabric.metrics.contracts.enforcement.EnforcementPolicy oldPolicy =
              this.get$currentPolicy();
            if (this.get$bound().test(this.get$metric(), currentTime)) {
                fabric.metrics.contracts.enforcement.EnforcementPolicy
                  newPolicy;
                if (this.get$metric().isSingleStore()) {
                    newPolicy = directStrategy();
                } else {
                    newPolicy = enforcementStrategy();
                }
                if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null))
                    oldPolicy.unapply((fabric.metrics.contracts.MetricContract)
                                        this.$getProxy());
                if (newPolicy.expiry() > currentTime) {
                    this.set$currentPolicy(newPolicy);
                    this.get$currentPolicy().
                      apply((fabric.metrics.contracts.MetricContract)
                              this.$getProxy());
                } else {
                    this.set$currentPolicy(null);
                }
                update(
                  java.lang.Math.
                      min(
                        newPolicy.expiry(),
                        currentTime +
                            fabric.metrics.contracts.MetricContract._Static._Proxy.$instance.
                            get$EXTENSION_LIMIT()));
            } else {
                if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null))
                    oldPolicy.unapply((fabric.metrics.contracts.MetricContract)
                                        this.$getProxy());
                update(0);
            }
        }
        
        /**
   * Check if this implies another {@link MetricContract} being considered.
   *
   * @param otherMetric
   *        the {@link Metric} the other {@link MetricContract} would
   *        assert a bound on
   * @param otherBound
   *        the {@link Bound} that would be used by the other
   *        {@link MetricContract}
   * @return true iff this would imply (and therefore) can enforce another
   *       {@link MetricContract} with the given metric and bound.
   */
        public boolean implies(fabric.metrics.Metric otherMetric,
                               fabric.metrics.contracts.Bound otherBound) {
            return getMetric().equals(otherMetric) &&
              valid(otherBound.get$startTime()) &&
              this.get$bound().implies(otherBound);
        }
        
        /**
   * Check if this implies the other {@link MetricContract}.
   *
   * @param other
   *        the other {@link MetricContract} this is being compared with
   * @return true iff this would imply (and therefore) can enforce other for
   *       the entire duration of other.
   */
        public boolean implies(fabric.metrics.contracts.MetricContract other) {
            return valid(other.getExpiry()) &&
              implies(other.getMetric(), other.getBound());
        }
        
        /**
   * Check if this enforces the <strong>same</strong> bound as another
   * {@link MetricContract} being considered.
   *
   * @param otherMetric
   *        the {@link Metric} the other {@link MetricContract} would
   *        assert a bound on
   * @param otherBound
   *        the {@link Bound} that would be used by the other
   *        {@link MetricContract}
   * @return true iff this enforces another {@link MetricContract} with the
   *       given parameters.
   */
        public boolean enforces(fabric.metrics.Metric otherMetric,
                                fabric.metrics.contracts.Bound otherBound) {
            return getMetric().equals(otherMetric) &&
              valid(otherBound.get$startTime()) &&
              this.get$bound().equals(otherBound);
        }
        
        /**
   * Check if this enforces the <strong>same</strong> bound as another
   * {@link MetricContract}.
   *
   * @param other
   *        the other {@link MetricContract} this is being compared with
   * @return true iff this enforces the same bound as other.
   */
        public boolean enforces(fabric.metrics.contracts.MetricContract other) {
            return valid(other.getExpiry()) &&
              enforces(other.getMetric(), other.getBound());
        }
        
        public java.lang.String toString() {
            return ((java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(getMetric())).
              toString() +
            " " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(this.get$bound())) +
            " until " +
            getExpiry();
        }
        
        /**
   * Given the current transaction context's view of the system state, return
   * a new {@link EnforcementPolicy} to enforce this {@link MetricContract}.
   *
   * @return The {@link EnforcementPolicy} to use for this
   *       {@link MetricContract} after the call completes.
   */
        public abstract fabric.metrics.contracts.enforcement.EnforcementPolicy
          enforcementStrategy();
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          directStrategy() {
            long currentTime = java.lang.System.currentTimeMillis();
            long trueTime = getBound().trueExpiry(getMetric(), currentTime);
            long hedgedTime = ((fabric.metrics.contracts.MetricContract._Impl)
                                 this.fetch()).hedged(currentTime);
            if (getExpiry() > trueTime || hedgedTime > getExpiry() +
                  fabric.metrics.contracts.MetricContract._Static._Proxy.$instance.get$EXTENSION_BUFFER()) {
                return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                          new fabric.metrics.contracts.enforcement.DirectPolicy.
                            _Impl(this.$getStore()).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$DirectPolicy$(
                    hedgedTime);
            }
            return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                      new fabric.metrics.contracts.enforcement.DirectPolicy.
                        _Impl(this.$getStore()).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$DirectPolicy$(getExpiry());
        }
        
        /**
   * Using the associated {@link Metric's} statistics and some tuning
   * constants, figure out a time to advertise given the current time.
   *
   * @param time
   *        the time we're calculating this hedged expiration at
   * @return an appropriately conservative time to advertise to other nodes in
   *       the system for this contract.
   */
        private long hedged(long time) {
            fabric.metrics.Metric m = getMetric();
            long hedgedResult = getBound().trueExpiry(m, time);
            long startTime = getBound().get$startTime();
            double r = getBound().get$rate();
            double b = getBound().get$base() + r * (time - startTime);
            double x = m.value();
            double v = m.velocity();
            double n = m.noise();
            double bm = b - x;
            double rv = r - v;
            double minYs = x - (v > 0 ? n : -n) /
              (4.0 * (v * java.lang.Math.sqrt(v * v + 1)));
            long min = getBound().trueExpiry(minYs, time);
            if (minYs < x && getBound().test(minYs, time)) {
                hedgedResult = java.lang.Math.min(min, hedgedResult);
            }
            else if (bm * rv > 0) {
                double rotatedY1 =
                  1.0 - java.lang.Math.sqrt(4.0 * rv * bm + 1.0) / (2.0 * rv);
                double rotatedX1 =
                  (rotatedY1 + bm) / rv;
                double rotatedY2 =
                  1.0 + java.lang.Math.sqrt(4.0 * rv * bm + 1.0) / (2.0 * rv);
                double rotatedX2 =
                  (rotatedY2 + bm) / rv;
                double xxFact =
                  1 / java.lang.Math.sqrt(v * v + 1);
                double xyFact =
                  v / java.lang.Math.sqrt(v * v + 1);
                double intersectX1 = rotatedX1 *
                  xxFact +
                  rotatedY1 * xyFact;
                double intersectX2 = rotatedX2 *
                  xxFact +
                  rotatedY2 * xyFact;
                double soonestX1 =
                  intersectX1 >
                  0
                  ? time +
                  intersectX1
                  : java.lang.Long.MAX_VALUE;
                double soonestX2 =
                  intersectX2 >
                  0
                  ? time +
                  intersectX2
                  : java.lang.Long.MAX_VALUE;
                hedgedResult =
                  java.lang.Math.min((long)
                                       java.lang.Math.min(soonestX1, soonestX2),
                                     hedgedResult);
            }
            return hedgedResult;
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
            $writeRef($getStore(), this.bound, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.currentPolicy, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.bound = (fabric.metrics.contracts.Bound)
                           $readRef(fabric.metrics.contracts.Bound._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.currentPolicy =
              (fabric.
                metrics.
                contracts.
                enforcement.
                EnforcementPolicy)
                $readRef(
                  fabric.metrics.contracts.enforcement.EnforcementPolicy.
                    _Proxy.class, (fabric.common.RefTypeEnum) refTypes.next(),
                  in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.MetricContract._Impl src =
              (fabric.metrics.contracts.MetricContract._Impl) other;
            this.metric = src.metric;
            this.bound = src.bound;
            this.currentPolicy = src.currentPolicy;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public double get$HEDGE_FACTOR();
        
        public double set$HEDGE_FACTOR(double val);
        
        public double postInc$HEDGE_FACTOR();
        
        public double postDec$HEDGE_FACTOR();
        
        public long get$EXTENSION_BUFFER();
        
        public long set$EXTENSION_BUFFER(long val);
        
        public long postInc$EXTENSION_BUFFER();
        
        public long postDec$EXTENSION_BUFFER();
        
        public long get$EXTENSION_LIMIT();
        
        public long set$EXTENSION_LIMIT(long val);
        
        public long postInc$EXTENSION_LIMIT();
        
        public long postDec$EXTENSION_LIMIT();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.MetricContract._Static {
            public double get$HEDGE_FACTOR() {
                return ((fabric.metrics.contracts.MetricContract._Static._Impl)
                          fetch()).get$HEDGE_FACTOR();
            }
            
            public double set$HEDGE_FACTOR(double val) {
                return ((fabric.metrics.contracts.MetricContract._Static._Impl)
                          fetch()).set$HEDGE_FACTOR(val);
            }
            
            public double postInc$HEDGE_FACTOR() {
                return ((fabric.metrics.contracts.MetricContract._Static._Impl)
                          fetch()).postInc$HEDGE_FACTOR();
            }
            
            public double postDec$HEDGE_FACTOR() {
                return ((fabric.metrics.contracts.MetricContract._Static._Impl)
                          fetch()).postDec$HEDGE_FACTOR();
            }
            
            public long get$EXTENSION_BUFFER() {
                return ((fabric.metrics.contracts.MetricContract._Static._Impl)
                          fetch()).get$EXTENSION_BUFFER();
            }
            
            public long set$EXTENSION_BUFFER(long val) {
                return ((fabric.metrics.contracts.MetricContract._Static._Impl)
                          fetch()).set$EXTENSION_BUFFER(val);
            }
            
            public long postInc$EXTENSION_BUFFER() {
                return ((fabric.metrics.contracts.MetricContract._Static._Impl)
                          fetch()).postInc$EXTENSION_BUFFER();
            }
            
            public long postDec$EXTENSION_BUFFER() {
                return ((fabric.metrics.contracts.MetricContract._Static._Impl)
                          fetch()).postDec$EXTENSION_BUFFER();
            }
            
            public long get$EXTENSION_LIMIT() {
                return ((fabric.metrics.contracts.MetricContract._Static._Impl)
                          fetch()).get$EXTENSION_LIMIT();
            }
            
            public long set$EXTENSION_LIMIT(long val) {
                return ((fabric.metrics.contracts.MetricContract._Static._Impl)
                          fetch()).set$EXTENSION_LIMIT(val);
            }
            
            public long postInc$EXTENSION_LIMIT() {
                return ((fabric.metrics.contracts.MetricContract._Static._Impl)
                          fetch()).postInc$EXTENSION_LIMIT();
            }
            
            public long postDec$EXTENSION_LIMIT() {
                return ((fabric.metrics.contracts.MetricContract._Static._Impl)
                          fetch()).postDec$EXTENSION_LIMIT();
            }
            
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
            public double get$HEDGE_FACTOR() { return this.HEDGE_FACTOR; }
            
            public double set$HEDGE_FACTOR(double val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.HEDGE_FACTOR = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public double postInc$HEDGE_FACTOR() {
                double tmp = this.get$HEDGE_FACTOR();
                this.set$HEDGE_FACTOR((double) (tmp + 1));
                return tmp;
            }
            
            public double postDec$HEDGE_FACTOR() {
                double tmp = this.get$HEDGE_FACTOR();
                this.set$HEDGE_FACTOR((double) (tmp - 1));
                return tmp;
            }
            
            private double HEDGE_FACTOR;
            
            public long get$EXTENSION_BUFFER() { return this.EXTENSION_BUFFER; }
            
            public long set$EXTENSION_BUFFER(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.EXTENSION_BUFFER = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public long postInc$EXTENSION_BUFFER() {
                long tmp = this.get$EXTENSION_BUFFER();
                this.set$EXTENSION_BUFFER((long) (tmp + 1));
                return tmp;
            }
            
            public long postDec$EXTENSION_BUFFER() {
                long tmp = this.get$EXTENSION_BUFFER();
                this.set$EXTENSION_BUFFER((long) (tmp - 1));
                return tmp;
            }
            
            private long EXTENSION_BUFFER;
            
            public long get$EXTENSION_LIMIT() { return this.EXTENSION_LIMIT; }
            
            public long set$EXTENSION_LIMIT(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.EXTENSION_LIMIT = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public long postInc$EXTENSION_LIMIT() {
                long tmp = this.get$EXTENSION_LIMIT();
                this.set$EXTENSION_LIMIT((long) (tmp + 1));
                return tmp;
            }
            
            public long postDec$EXTENSION_LIMIT() {
                long tmp = this.get$EXTENSION_LIMIT();
                this.set$EXTENSION_LIMIT((long) (tmp - 1));
                return tmp;
            }
            
            private long EXTENSION_LIMIT;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeDouble(this.HEDGE_FACTOR);
                out.writeLong(this.EXTENSION_BUFFER);
                out.writeLong(this.EXTENSION_LIMIT);
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
                this.HEDGE_FACTOR = in.readDouble();
                this.EXTENSION_BUFFER = in.readLong();
                this.EXTENSION_LIMIT = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.MetricContract._Static.
                         _Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm23 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff24 = 1;
                        $label19: for (boolean $commit20 = false; !$commit20;
                                       ) {
                            if ($backoff24 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff24);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e21) {
                                        
                                    }
                                }
                            }
                            if ($backoff24 < 5000) $backoff24 *= 2;
                            $commit20 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.MetricContract._Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 2);
                                fabric.metrics.contracts.MetricContract._Static.
                                  _Proxy.
                                  $instance.
                                  set$EXTENSION_BUFFER((long) 20);
                                fabric.metrics.contracts.MetricContract._Static.
                                  _Proxy.
                                  $instance.
                                  set$EXTENSION_LIMIT((long) 500L);
                            }
                            catch (final fabric.worker.RetryException $e21) {
                                $commit20 = false;
                                continue $label19;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e21) {
                                $commit20 = false;
                                fabric.common.TransactionID $currentTid22 =
                                  $tm23.getCurrentTid();
                                if ($e21.tid.isDescendantOf($currentTid22))
                                    continue $label19;
                                if ($currentTid22.parent != null) throw $e21;
                                throw new InternalError(
                                        "Something is broken with transaction management. Got a signal to restart a different transaction than the one being managed.");
                            }
                            catch (final Throwable $e21) {
                                $commit20 = false;
                                if ($tm23.checkForStaleObjects())
                                    continue $label19;
                                throw new fabric.worker.AbortException($e21);
                            }
                            finally {
                                if ($commit20) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e21) {
                                        $commit20 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e21) {
                                        $commit20 = false;
                                        fabric.common.TransactionID
                                          $currentTid22 = $tm23.getCurrentTid();
                                        if ($currentTid22 ==
                                              null ||
                                              $e21.tid.isDescendantOf(
                                                         $currentTid22) &&
                                              !$currentTid22.equals($e21.tid))
                                            continue $label19;
                                        throw $e21;
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit20) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 1, -17, -73, -1, -104,
    -21, -17, 57, -89, -69, 54, -98, 82, -2, 14, 34, 85, -33, -56, 86, -78, 40,
    -30, 110, -9, -96, 64, -78, 13, -55, -90, -107 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491836575000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
