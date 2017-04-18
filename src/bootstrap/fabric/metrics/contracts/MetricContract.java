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
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;

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
    public boolean refresh();
    
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
    public boolean enforces(fabric.metrics.Metric otherMetric,
                            fabric.metrics.contracts.Bound otherBound);
    
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
    
    public fabric.util.Set getLeafSubjects();
    
    public void deactivate();
    
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
        
        public fabric.util.Set getLeafSubjects() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              getLeafSubjects();
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
                                                 bound.get$base(), 0));
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
        public boolean refresh() {
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
                if (newPolicy.expiry() >= currentTime) {
                    if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null) &&
                          !oldPolicy.equals(newPolicy))
                        oldPolicy.unapply(
                                    (fabric.metrics.contracts.MetricContract)
                                      this.$getProxy());
                    this.set$currentPolicy(newPolicy);
                    this.get$currentPolicy().
                      apply((fabric.metrics.contracts.MetricContract)
                              this.$getProxy());
                    return update(newPolicy.expiry());
                }
                else {
                    if (!fabric.lang.Object._Proxy.idEquals(
                                                     oldPolicy, null))
                        oldPolicy.unapply(
                                    (fabric.metrics.contracts.MetricContract)
                                      this.$getProxy());
                    this.set$currentPolicy(null);
                    return update(currentTime);
                }
            } else {
                if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null))
                    oldPolicy.unapply((fabric.metrics.contracts.MetricContract)
                                        this.$getProxy());
                return update(0);
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
            if (!getMetric().equals(otherMetric) ||
                  !this.get$bound().implies(otherBound))
                return false;
            return valid(java.lang.System.currentTimeMillis());
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
            if (!getMetric().equals(otherMetric) ||
                  !this.get$bound().equals(otherBound))
                return false;
            return valid(java.lang.System.currentTimeMillis());
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
            if (getExpiry() <= trueTime) {
                hedgedTime = java.lang.Math.max(getExpiry(), hedgedTime);
            }
            final fabric.worker.Store s = getStore();
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
                        fabric.worker.transaction.TransactionManager $tm40 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff41 = 1;
                        boolean $doBackoff42 = true;
                        $label36: for (boolean $commit37 = false; !$commit37;
                                       ) {
                            if ($doBackoff42) {
                                if ($backoff41 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff41);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e38) {
                                            
                                        }
                                    }
                                }
                                if ($backoff41 < 5000) $backoff41 *= 2;
                            }
                            $doBackoff42 = $backoff41 <= 32 || !$doBackoff42;
                            $commit37 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.MetricContract._Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e38) {
                                $commit37 = false;
                                continue $label36;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e38) {
                                $commit37 = false;
                                fabric.common.TransactionID $currentTid39 =
                                  $tm40.getCurrentTid();
                                if ($e38.tid.isDescendantOf($currentTid39))
                                    continue $label36;
                                if ($currentTid39.parent != null) throw $e38;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e38) {
                                $commit37 = false;
                                if ($tm40.checkForStaleObjects())
                                    continue $label36;
                                throw new fabric.worker.AbortException($e38);
                            }
                            finally {
                                if ($commit37) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e38) {
                                        $commit37 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e38) {
                                        $commit37 = false;
                                        fabric.common.TransactionID
                                          $currentTid39 = $tm40.getCurrentTid();
                                        if ($currentTid39 != null) {
                                            if ($e38.tid.equals(
                                                           $currentTid39) ||
                                                  !$e38.tid.isDescendantOf(
                                                              $currentTid39)) {
                                                throw $e38;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit37) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 28, -83, 105, -84, -39,
    108, -27, 14, 31, -35, -120, 21, 23, -85, -67, -75, -116, 53, -8, 64, -44,
    -35, 69, -72, 126, 91, -57, -21, -126, 57, -89, -115 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492522047000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
