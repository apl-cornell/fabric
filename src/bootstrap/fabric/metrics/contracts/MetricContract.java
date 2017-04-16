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
                    fabric.common.Logging.METRICS_LOGGER.
                      info(
                        "DEFENDING " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.
                                  $unwrap(
                                    (fabric.metrics.contracts.MetricContract)
                                      this.$getProxy())) +
                          " WITH " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.
                                  $unwrap(this.get$currentPolicy())) +
                          " IN " +
                          fabric.worker.transaction.TransactionManager.
                            getInstance().getCurrentTid());
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
            if (!valid(java.lang.System.currentTimeMillis())) refresh();
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
            if (!valid(java.lang.System.currentTimeMillis())) refresh();
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
                        fabric.worker.transaction.TransactionManager $tm33 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff34 = 1;
                        boolean $doBackoff35 = true;
                        $label29: for (boolean $commit30 = false; !$commit30;
                                       ) {
                            if ($doBackoff35) {
                                if ($backoff34 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff34);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e31) {
                                            
                                        }
                                    }
                                }
                                if ($backoff34 < 5000) $backoff34 *= 2;
                            }
                            $doBackoff35 = $backoff34 <= 32 || !$doBackoff35;
                            $commit30 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.MetricContract._Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 2);
                            }
                            catch (final fabric.worker.RetryException $e31) {
                                $commit30 = false;
                                continue $label29;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e31) {
                                $commit30 = false;
                                fabric.common.TransactionID $currentTid32 =
                                  $tm33.getCurrentTid();
                                if ($e31.tid.isDescendantOf($currentTid32))
                                    continue $label29;
                                if ($currentTid32.parent != null) throw $e31;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e31) {
                                $commit30 = false;
                                if ($tm33.checkForStaleObjects())
                                    continue $label29;
                                throw new fabric.worker.AbortException($e31);
                            }
                            finally {
                                if ($commit30) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e31) {
                                        $commit30 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e31) {
                                        $commit30 = false;
                                        fabric.common.TransactionID
                                          $currentTid32 = $tm33.getCurrentTid();
                                        if ($currentTid32 != null) {
                                            if ($e31.tid.equals(
                                                           $currentTid32) ||
                                                  !$e31.tid.isDescendantOf(
                                                              $currentTid32)) {
                                                throw $e31;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit30) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -8, 30, 73, -110, -12,
    21, -97, 126, -109, -83, -42, 23, 74, 20, 115, 57, 98, -65, -108, 73, 47,
    -55, -5, -128, -93, -34, -39, 120, 125, 110, -76, 5 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492364345000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVZfWwUxxWfO38bwxmDMRgwYFzKV+4EiSIRJzT4avCRozi2QcIkcfZ25+yFvd1ld84cSU1p1BYUKaQhhga1kCghShOcRElLU7WFRsoXNFH6oZQ0VaFIFSIVRS1qm6I2bfre7Nzt3Xrvav9RyzNvbmbezHtv3vu92d2xa6TCtkhrUkqoWpjtMakdXi8lYvFuybKpEtUk2+6D3gF5SnnsyMfPKS1BEoyTOlnSDV2VJW1AtxmZFt8hDUsRnbLIlp5Y+3ZSIyNjl2QPMRLc3pGxyELT0PYMagYTm4xb//CKyOi37qt/tYyE+klI1XuZxFQ5auiMZlg/qUvRVIJa9jpFoUo/ma5TqvRSS5U09QGYaOj9pMFWB3WJpS1q91Db0IZxYoOdNqnF98x2ovgGiG2lZWZYIH69I36aqVokrtqsPU4qkyrVFHsX2UvK46QiqUmDMHFWPKtFhK8YWY/9ML1WBTGtpCTTLEv5TlVXGFng5chp3HYXTADWqhRlQ0Zuq3Jdgg7S4IikSfpgpJdZqj4IUyuMNOzCSHPRRWFStSnJO6VBOsDIbO+8bmcIZtVwsyALI43eaXwlOLNmz5nlnda1L91+8EG9Sw+SAMisUFlD+auBqcXD1EOT1KK6TB3GuuXxI9Ks0weChMDkRs9kZ85rX75+58qW1886c+b6zNmc2EFlNiCfSEz75bzosjVlKEa1adgqukKB5vxUu8VIe8YEb5+VWxEHw9nB13ve3rbvBXo1SGpjpFI2tHQKvGq6bKRMVaPWBqpTS2JUiZEaqitRPh4jVdCOqzp1ejcnkzZlMVKu8a5Kg/8GEyVhCTRRFbRVPWlk26bEhng7YxJC6qGQAPwvIWTpWWjPIaSsl5GtkSEjRSMJLU13g3tHoFDJkociELeWKkdsS45YaZ2pMEl0gRcBsSPg6sySZGZHNvGeqPgdBonM/9vKGdSpfncgAOZeIBsKTUg2nJ3wo45uDUKly9AUag3I2sHTMTLj9FHuSzXo/zb4MLdWAM5/nhc58nlH0x2d118aeNfxQ+QVxmTk8464YSFuOCduuFBckLAOgy0M8BUG+BoLZMLR47GT3KcqbR58uUXrYNHbTE1iScNKZUggwDWcyfm5M4Er7ASIARSpW9Z778b7D7SWgRebu8vxYGFqmzemXCSKQUuCQBmQQ/s//uTlIyOGG12MtI0L+vGcGLStXnNZhkwVAEV3+eULpVMDp0faggg4NWgXCbwVgKXFu0dB8LZngRCtUREnU9AGkoZDWfSqZUOWsdvt4W4wDasGxyPQWB4BOYbe0Wse+837f7yZZ5cs3IbycLmXsva8EMfFQjyYp7u277MohXkXnuh+/PC1/du54WHGYr8N27COQmhLENOG9fWzuz76/cUTHwTdw2Kk0kwnNFXOcF2mfwZ/ASj/wYJxih1IAa2jAiMW5kDCxJ2XuLIBXGgAWSC63bZFTxmKmlSlhEbRUz4NfW7VqT8drHeOW4Mex3gWWfm/F3D753SQfe/e948WvkxAxnTl2s+d5mDgDHfldZYl7UE5Ml/91fyj70jHwPMBwWz1AcpBiXB7EH6Aq7ktbuL1Ks/YLVi1Otaax/vL7fH5YD0mVtcX+yNj32mOrr3qBH/OF3GNRT7Bv1XKC5PVL6T+HmytfCtIqvpJPc/pks62SoBl4Ab9kJXtqOiMk6kF44UZ1kkn7blYm+eNg7xtvVHggg60cTa2ax3HdxwHDDELjdQGZS7g+VOCHsLRGSbWMzMBwhu3cZbFvF6C1bKsM9aYlsFASqpkcssGcdkpYrn9gu7NWxZ8WMAf/myEXO8BRQcKcbDZiU+sby2UuxXKPFj4DUFf85E76siN1R3jxUOuMUGfKRCvImGkdSUrXUtRyO7AaUXFrMONVkKZDxv8RdBf+Ii5saSYyPVzQd8uEHOqnLYAB1m3AXiwJyvurUXFpZDhLZmmgCXc6bYd9qJqNKEgESg/g5M8L+gZHzXu9veSMkaqTEsdBvxhmLnwAg2Oo6ZSaYZQwZ1yBSSxrs4vbugcWL8u2re5xydEuy01BSg7LK5s9MDow5+FD4468OTcaxePu1rm8zh3W77dVL5nBnZZVGoXzrH+yssjP/7uyH7n3tdQeEvr1NOpF8//+73wE5fO+WT7SsUAsOa/6zP+5gliczkj1VLC5ufkBhL/C4n7Vo+g0TzL54EaQWXmF7sac0VOPDR6XNn87KqgQMZ74RSYYd6k0WGq5S0VQrOMe/TaxB8IXJi7dHX+mujOy4OOWRZ4dvbOfn7T2LkNS+RDQVKWw7NxTyGFTO2FKFZrUXiI0vsKsGxhzlY1aIN7oMAdtWK2Q8vfyvdS17fHAxk/Aw+CVYtF3hT0jNfwbr4Juie5DqsuvplVIivxXAo2WOoEa5sI1rZcsLYVXgfbXOnVQp0BXEmYkMrlgs4uojNWxngNkaVJ0OnFNcyX/cESYyNYDYNjDVLmovg6P8HBmcnNsOtxQQ9OTnBkeUTQ/RMT/Gslxr6B1VcgDEHwHKx3+cm9GEo7IVU7Bb17cnIjS7egGycm9yMlxh7F6gAjM0DuzozJU3FcTVJ8NOIM2wTUIbmHwUXO0Af91MKYuRNkuiboxcmphSwXBP1wYmodLTH2baxGERXhdoipw1eXYUNVPLrwpAupksRBr22CdpXQxSfjIssGQddOTJdnS4w9h9VTkAUtmoQH2SE/VaoShqFRSfc7mbVQ9sFT+BVBX53cySDLK4KenCyGvVJCr+9hNQayqylTU50Tet5PAcAW8k1CZpQ5tGGSroUsFwQt4VoBF8rr+ao/KiH7T7A6NQHZvwDlaUIaHxa0b3KyI0uvoJsma/w3SijAU9sZCBBxqSuuQTOUk3BpfkjQXZPTAFlMQXdMLBjeKzH2PlbvgNzMcN4gZq+t9fzRDx98wnkDc7wvNzwaNuLCt0P5ASDYLYJOLaKh79WLB3jCk/tnipXqHNr0z4kp/tsSY7/D6gMA6rxbOCgK0DbI796b/YAMI+eHIAcVNFpEsyJAhiwdgq6ZmAp/KDF2GauLjExTVAsSTSnpa5FpIZRfAxrfL+jGItL7Xse2eI6kRiwSE7RjQkjwGN/nWgml/ozVFbiqD1FlkPK0/5hfGAFfoAFugmcEPTKpMOIshwV9dGJH8UmJsRtYXWckBGk/TqVkb5q/LLCz0RQSD4H8IaWX8utmcxF0CCwgZGlU0NWTUwtZVgm6YkJqBQIlxsqw81NGahWaTfzYcywDTld4Ica3RXN9XuGKzwxy9E164vJdKxuLvL6dPe7Dj+B76Xiouun4lg/5W8jcJ4SaOKlOpjUt/61KXrvShNSucnPVOO9YTK5ONSOziz2NM+e9Em+jLQKVDs8UULWQh/GvMdjKnzcNXNaZh79C3OzNbpV1hEVF3wZkLcmnc6mb0xZ+Fxv7a9ONyuq+S/ytI4bxjZbYob81Prn38RfPN22caa9J/HQ0Fjn3r33PXPwoM6J/v+K/bt1pga8bAAA=";
}
