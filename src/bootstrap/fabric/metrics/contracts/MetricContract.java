package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.Set;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.DerivedMetric;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
import fabric.metrics.util.Subject;
import fabric.worker.Store;

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
    
    public fabric.util.Set getLeafSubjects();
    
    public void deactivate();
    
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
        
        public fabric.util.Set getLeafSubjects() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              getLeafSubjects();
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
            this.set$bound(
                   ((fabric.metrics.contracts.Bound)
                      new fabric.metrics.contracts.Bound._Impl(
                        this.$getStore()).$getProxy(
                                            )).fabric$metrics$contracts$Bound$(
                                                 bound.get$rate(),
                                                 bound.get$base(),
                                                 bound.get$startTime()));
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
        
        public fabric.util.Set getLeafSubjects() {
            fabric.metrics.Metric m = getMetric();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(m)) instanceof fabric.metrics.SampledMetric) {
                return fabric.util.Collections._Impl.
                  singleton((fabric.metrics.SampledMetric)
                              fabric.lang.Object._Proxy.$getProxy(m));
            }
            else if (fabric.lang.Object._Proxy.
                       $getProxy(
                         (java.lang.Object)
                           fabric.lang.WrappedJavaInlineable.
                           $unwrap(
                             m)) instanceof fabric.metrics.DerivedMetric) {
                return ((fabric.metrics.DerivedMetric)
                          fabric.lang.Object._Proxy.$getProxy(m)).
                  getLeafSubjects();
            }
            else {
                throw new java.lang.IllegalStateException(
                        "All metrics should be either sampled or derived!");
            }
        }
        
        public void deactivate() {
            if (!isObserved()) {
                if (!fabric.lang.Object._Proxy.idEquals(
                                                 this.get$currentPolicy(),
                                                 null))
                    this.get$currentPolicy().
                      unapply((fabric.metrics.contracts.MetricContract)
                                this.$getProxy());
            }
            super.deactivate();
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
            if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null))
                oldPolicy.unapply((fabric.metrics.contracts.MetricContract)
                                    this.$getProxy());
            if (this.get$bound().test(this.get$metric(), currentTime)) {
                fabric.metrics.contracts.enforcement.EnforcementPolicy
                  newPolicy;
                if (this.get$metric().isSingleStore()) {
                    newPolicy = directStrategy();
                } else {
                    newPolicy = enforcementStrategy();
                }
                if (newPolicy.expiry() >= currentTime) {
                    this.set$currentPolicy(newPolicy);
                    this.get$currentPolicy().
                      apply((fabric.metrics.contracts.MetricContract)
                              this.$getProxy());
                    update(newPolicy.expiry());
                } else {
                    this.set$currentPolicy(null);
                    update(currentTime);
                }
            } else {
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
            return valid(other.getExpiryUpper()) &&
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
            return valid(other.getExpiryUpper()) &&
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
            java.lang.String.valueOf(
                               fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                   getExpiry(
                                                                     )));
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
            if (getExpiryUpper() <= trueTime) {
                hedgedTime = java.lang.Math.max(getExpiryLower(), hedgedTime);
            }
            final fabric.worker.Store s = $getStore();
            return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                      new fabric.metrics.contracts.enforcement.DirectPolicy.
                        _Impl(s).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$DirectPolicy$(hedgedTime);
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
            double r = getBound().get$rate();
            double b = getBound().value(time);
            fabric.metrics.Metric m = getMetric();
            double x = m.value();
            double v = m.velocity();
            double n =
              m.noise() *
              fabric.metrics.contracts.MetricContract._Static._Proxy.$instance.
              get$HEDGE_FACTOR() *
              fabric.metrics.contracts.MetricContract._Static._Proxy.$instance.
              get$HEDGE_FACTOR();
            long hedgedResult = getBound().trueExpiry(m, time);
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
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeDouble(this.HEDGE_FACTOR);
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
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.MetricContract._Static.
                         _Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm29 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff30 = 1;
                        $label25: for (boolean $commit26 = false; !$commit26;
                                       ) {
                            if ($backoff30 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff30);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e27) {
                                        
                                    }
                                }
                            }
                            if ($backoff30 < 5000) $backoff30 *= 2;
                            $commit26 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.MetricContract._Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e27) {
                                $commit26 = false;
                                continue $label25;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e27) {
                                $commit26 = false;
                                fabric.common.TransactionID $currentTid28 =
                                  $tm29.getCurrentTid();
                                if ($e27.tid.isDescendantOf($currentTid28))
                                    continue $label25;
                                if ($currentTid28.parent != null) throw $e27;
                                throw new InternalError(
                                        "Something is broken with transaction management. Got a signal to restart a different transaction than the one being managed.");
                            }
                            catch (final Throwable $e27) {
                                $commit26 = false;
                                if ($tm29.checkForStaleObjects())
                                    continue $label25;
                                throw new fabric.worker.AbortException($e27);
                            }
                            finally {
                                if ($commit26) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e27) {
                                        $commit26 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e27) {
                                        $commit26 = false;
                                        fabric.common.TransactionID
                                          $currentTid28 = $tm29.getCurrentTid();
                                        if ($currentTid28 ==
                                              null ||
                                              $e27.tid.isDescendantOf(
                                                         $currentTid28) &&
                                              !$currentTid28.equals($e27.tid))
                                            continue $label25;
                                        throw $e27;
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit26) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 49, 104, -23, 24, -64,
    70, 72, -60, -34, 120, -69, 37, 112, -32, -9, 99, 42, -31, -114, -44, 100,
    98, -6, 22, 92, -121, 37, -119, -71, -29, 52, -119 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492107672000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
