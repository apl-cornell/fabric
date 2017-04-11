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
    
    public static final byte[] $classHash = new byte[] { 53, 59, -57, -124, -14,
    -12, 2, -10, 124, -29, -1, 18, 98, -47, -80, 106, -113, -126, -63, -94, -54,
    98, 114, -11, -125, 117, 2, -65, -110, 60, -41, -18 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491925438000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
