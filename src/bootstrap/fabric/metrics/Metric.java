package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.util.List;
import fabric.util.Set;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.worker.Store;

/**
 * Abstract class with base implementation of some {@link Metric} methods.
 */
public interface Metric
  extends java.lang.Comparable, fabric.metrics.util.AbstractSubject {
    /** @return the current value of the {@link Metric}. */
    public abstract double value();
    
    /**
   * @param scalar
   *            a double scalar to scale this metric by
   * @return A {@link Metric} that tracks the scaled value of this
   *         {@link Metric}.
   */
    public fabric.metrics.DerivedMetric times(double scalar);
    
    /**
   * @param other
   *            another {@link Metric} to add with this {@link Metric}.
   * @return a {@link Metric} that tracks the value of the sum of other and
   *         this.
   */
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
    /**
   * @param other
   *            another {@link Metric} to take the minimum of along with this
   *            {@link Metric}.
   * @return a {@link Metric} that tracks the value of the minimum of this and
   *         the other {@link Metric}.
   */
    public fabric.metrics.Metric min(fabric.metrics.Metric other);
    
    /**
   * @param other
   *            another {@link Metric} to take the maximum of along with this
   *            {@link Metric}.
   * @return a {@link Metric} that tracks the value of the maximum of this and
   *         the other {@link Metric}.
   */
    public fabric.metrics.Metric max(fabric.metrics.Metric other);
    
    /**
   * @param bound
   *            the {@link Bound} that the returned {@link MetricContract}
   *            will enforce on this {@link Metric}
   * @return a {@link MetricContract} that enforces that the {@link Metric}
   *         satisfies the given {@link Bound}. If such a
   *         {@link MetricContract}, it is returned, otherwise a new one is
   *         created and returned (unactivated).
   */
    public fabric.metrics.contracts.MetricContract getContract(fabric.metrics.contracts.Bound bound);
    
    /**
   * @param rate
   *            the rate parameter for the {@link Bound} on the resuling
   *            {@link MetricContract}
   * @param base
   *            the base parameter for the {@link Bound} on the resuling
   *            {@link MetricContract}
   * @param time
   *            the startTime parameter of the {@link Bound} on the resulting
   *            {@link MetricContract}
   * @return a {@link MetricContract} which enforces that the {@link Metric}
   *         satisfies a {@link Bound} with the given parameters at the given
   *         time. If such a {@link MetricContract} already exists, it is
   *         returned, otherwise a new one is created and returned
   *         (unactivated).
   *
   */
    public fabric.metrics.contracts.MetricContract getContract(double rate,
                                                               double base, long time);
    
    /**
   * @param rate
   *            the rate parameter for the {@link Bound} on the resuling
   *            {@link MetricContract}
   * @param base
   *            the base parameter for the {@link Bound} on the resuling
   *            {@link MetricContract}
   * @return a {@link MetricContract} which enforces that the {@link Metric}
   *         satisfies a {@link Bound} with the given parameters at the
   *         current time. If such a {@link MetricContract} already exists, it
   *         is returned, otherwise a new one is created and returned
   *         (unactivated).
   */
    public fabric.metrics.contracts.MetricContract getContract(double rate, double base);
    
    /**
   * @param store
   *            the Store that holds this {@link Metric}
   */
    public fabric.metrics.Metric fabric$metrics$Metric$();
    
    public fabric.util.List get$contracts();
    
    public fabric.util.List set$contracts(fabric.util.List val);
    
    /** @return the estimated velocity of the {@link Metric}. */
    public abstract double velocity();
    
    /**
   * @return the estimated noise (the <em>variance</em> of the velocity
   *         estimate) of the {@link Metric}.
   */
    public abstract double noise();
    
    /**
   * Used to construct and enforce {@link MetricContract}s bounding this
   * {@link Metric}s value.
   * <p>
   * Implementations of this method should use "weak" estimates of value,
   * velocity, and noise to avoid contention issues. The internal
   * implementation is expected to have refreshed these estimates recently
   * prior to the call.
   *
   * @param bound
   *            a {@link Bound} that the returned policy enforces.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
    public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
      fabric.metrics.contracts.Bound bound);
    
    public fabric.lang.arrays.doubleArray get$weakStats();
    
    public fabric.lang.arrays.doubleArray set$weakStats(fabric.lang.arrays.doubleArray val);
    
    /**
   * Update the current "weak" estimates to be used by {@link #policy(Bound)}
   */
    public void refreshWeakEstimates();
    
    /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #value()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
    public double weakValue();
    
    /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #velocity()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
    public double weakVelocity();
    
    /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #noise()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
    public double weakNoise();
    
    /** @return a freshly computed weak value for this {@link DerivedMetric}. */
    public abstract double computeWeakValue();
    
    /**
   * @return a freshly computed weak velocity for this {@link DerivedMetric}.
   */
    public abstract double computeWeakVelocity();
    
    /** @return a freshly computed weak noise for this {@link DerivedMetric}. */
    public abstract double computeWeakNoise();
    
    /**
   * @return true iff all the sampling and transformations on this metric are
   *         stored on a single store.
   */
    public abstract boolean isSingleStore();
    
    /**
   * @param contract
   *            a {@link MetricContract} to store with this {@link Metric}
   * @throws IllegalArgumentException
   *             if the {@link MetricContract} doesn't enforce a {@link Bound}
   *             on this {@link Metric}
   */
    public void addContract(fabric.metrics.contracts.MetricContract contract);
    
    /**
   * @param bound
   *        the {@link Bound} that the contract will enforce on this
   *        {@link Metric}
   * @return a {@link MetricContract} asserting this metric satisfies the
   *       given {@link Bound}.
   */
    public fabric.metrics.contracts.MetricContract createContract(fabric.metrics.contracts.Bound bound);
    
    /**
   * @param time
   *        the time we're searching for {@link MetricContract}s for this
   *        {@link Metric}
   * @return a {@link Set} of {@link MetricContract}s that are currently
   *       enforced for this {@link Metric}
   */
    public fabric.util.List getContracts(long time);
    
    public int compareTo(java.lang.Object that);
    
    /**
   * {@inheritDoc}
   *
   * If the {@link Observer} is a {@link MetricContract} and is now
   * {@link Contract#stale()}, this clears it out from the Metric's stored
   * contract set.
   */
    public void removeObserver(fabric.metrics.util.Observer obs);
    
    public static class _Proxy
    extends fabric.metrics.util.AbstractSubject._Proxy
      implements fabric.metrics.Metric {
        public fabric.util.List get$contracts() {
            return ((fabric.metrics.Metric._Impl) fetch()).get$contracts();
        }
        
        public fabric.util.List set$contracts(fabric.util.List val) {
            return ((fabric.metrics.Metric._Impl) fetch()).set$contracts(val);
        }
        
        public fabric.lang.arrays.doubleArray get$weakStats() {
            return ((fabric.metrics.Metric._Impl) fetch()).get$weakStats();
        }
        
        public fabric.lang.arrays.doubleArray set$weakStats(
          fabric.lang.arrays.doubleArray val) {
            return ((fabric.metrics.Metric._Impl) fetch()).set$weakStats(val);
        }
        
        public double value() {
            return ((fabric.metrics.Metric) fetch()).value();
        }
        
        public fabric.metrics.DerivedMetric times(double arg1) {
            return ((fabric.metrics.Metric) fetch()).times(arg1);
        }
        
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.Metric) fetch()).plus(arg1);
        }
        
        public fabric.metrics.Metric min(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.Metric) fetch()).min(arg1);
        }
        
        public fabric.metrics.Metric max(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.Metric) fetch()).max(arg1);
        }
        
        public fabric.metrics.contracts.MetricContract getContract(
          fabric.metrics.contracts.Bound arg1) {
            return ((fabric.metrics.Metric) fetch()).getContract(arg1);
        }
        
        public fabric.metrics.contracts.MetricContract getContract(double arg1,
                                                                   double arg2,
                                                                   long arg3) {
            return ((fabric.metrics.Metric) fetch()).getContract(arg1, arg2,
                                                                 arg3);
        }
        
        public fabric.metrics.contracts.MetricContract getContract(
          double arg1, double arg2) {
            return ((fabric.metrics.Metric) fetch()).getContract(arg1, arg2);
        }
        
        public static fabric.metrics.Metric scaleAtStore(
          fabric.worker.Store arg1, double arg2, fabric.metrics.Metric arg3) {
            return fabric.metrics.Metric._Impl.scaleAtStore(arg1, arg2, arg3);
        }
        
        public static fabric.metrics.Metric addAtStore(
          fabric.worker.Store arg1, fabric.lang.arrays.ObjectArray arg2) {
            return fabric.metrics.Metric._Impl.addAtStore(arg1, arg2);
        }
        
        public static fabric.metrics.Metric minAtStore(
          fabric.worker.Store arg1, fabric.lang.arrays.ObjectArray arg2) {
            return fabric.metrics.Metric._Impl.minAtStore(arg1, arg2);
        }
        
        public static fabric.metrics.Metric maxAtStore(
          fabric.worker.Store arg1, fabric.lang.arrays.ObjectArray arg2) {
            return fabric.metrics.Metric._Impl.maxAtStore(arg1, arg2);
        }
        
        public fabric.metrics.Metric fabric$metrics$Metric$() {
            return ((fabric.metrics.Metric) fetch()).fabric$metrics$Metric$();
        }
        
        public double velocity() {
            return ((fabric.metrics.Metric) fetch()).velocity();
        }
        
        public double noise() {
            return ((fabric.metrics.Metric) fetch()).noise();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          fabric.metrics.contracts.Bound arg1) {
            return ((fabric.metrics.Metric) fetch()).policy(arg1);
        }
        
        public void refreshWeakEstimates() {
            ((fabric.metrics.Metric) fetch()).refreshWeakEstimates();
        }
        
        public double weakValue() {
            return ((fabric.metrics.Metric) fetch()).weakValue();
        }
        
        public double weakVelocity() {
            return ((fabric.metrics.Metric) fetch()).weakVelocity();
        }
        
        public double weakNoise() {
            return ((fabric.metrics.Metric) fetch()).weakNoise();
        }
        
        public double computeWeakValue() {
            return ((fabric.metrics.Metric) fetch()).computeWeakValue();
        }
        
        public double computeWeakVelocity() {
            return ((fabric.metrics.Metric) fetch()).computeWeakVelocity();
        }
        
        public double computeWeakNoise() {
            return ((fabric.metrics.Metric) fetch()).computeWeakNoise();
        }
        
        public boolean isSingleStore() {
            return ((fabric.metrics.Metric) fetch()).isSingleStore();
        }
        
        public void addContract(fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.Metric) fetch()).addContract(arg1);
        }
        
        public fabric.metrics.contracts.MetricContract createContract(
          fabric.metrics.contracts.Bound arg1) {
            return ((fabric.metrics.Metric) fetch()).createContract(arg1);
        }
        
        public fabric.util.List getContracts(long arg1) {
            return ((fabric.metrics.Metric) fetch()).getContracts(arg1);
        }
        
        public int compareTo(java.lang.Object arg1) {
            return ((fabric.metrics.Metric) fetch()).compareTo(arg1);
        }
        
        public static fabric.metrics.DerivedMetric findDerivedMetric(
          fabric.worker.Store arg1, fabric.metrics.DerivedMetric arg2) {
            return fabric.metrics.Metric._Impl.findDerivedMetric(arg1, arg2);
        }
        
        public _Proxy(Metric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.metrics.util.AbstractSubject._Impl
      implements fabric.metrics.Metric {
        /** @return the current value of the {@link Metric}. */
        public abstract double value();
        
        /**
   * @param scalar
   *            a double scalar to scale this metric by
   * @return A {@link Metric} that tracks the scaled value of this
   *         {@link Metric}.
   */
        public fabric.metrics.DerivedMetric times(double scalar) {
            final fabric.worker.Store s = $getStore();
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.ScaledMetric)
                   new fabric.metrics.ScaledMetric._Impl(s).$getProxy()).
                    fabric$metrics$ScaledMetric$(scalar,
                                                 (fabric.metrics.Metric)
                                                   this.$getProxy()));
        }
        
        /**
   * @param other
   *            another {@link Metric} to add with this {@link Metric}.
   * @return a {@link Metric} that tracks the value of the sum of other and
   *         this.
   */
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.SumMetric)
                return other.plus((fabric.metrics.Metric) this.$getProxy());
            final fabric.worker.Store s = $getStore();
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.SumMetric)
                   new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                    fabric$metrics$SumMetric$(
                      fabric.lang.arrays.internal.Compat.
                          convert(
                            this.$getStore(),
                            this.get$$updateLabel(),
                            this.get$$updateLabel().confPolicy(),
                            new fabric.lang.Object[] { (fabric.metrics.Metric)
                                                         this.$getProxy(),
                              other })));
        }
        
        /**
   * @param other
   *            another {@link Metric} to take the minimum of along with this
   *            {@link Metric}.
   * @return a {@link Metric} that tracks the value of the minimum of this and
   *         the other {@link Metric}.
   */
        public fabric.metrics.Metric min(fabric.metrics.Metric other) {
            if (this.equals(other))
                return (fabric.metrics.Metric) this.$getProxy();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.MinMetric)
                return other.min((fabric.metrics.Metric) this.$getProxy());
            final fabric.worker.Store s = $getStore();
            if (this.compareTo(
                       (java.lang.Object)
                         fabric.lang.WrappedJavaInlineable.$unwrap(other)) >
                  0)
                return fabric.metrics.Metric._Impl.
                  findDerivedMetric(
                    s,
                    ((fabric.metrics.MinMetric)
                       new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                        fabric$metrics$MinMetric$(
                          fabric.lang.arrays.internal.Compat.
                              convert(this.$getStore(),
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      new fabric.lang.Object[] { other,
                                        (fabric.metrics.Metric)
                                          this.$getProxy() })));
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.MinMetric)
                   new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                    fabric$metrics$MinMetric$(
                      fabric.lang.arrays.internal.Compat.
                          convert(
                            this.$getStore(),
                            this.get$$updateLabel(),
                            this.get$$updateLabel().confPolicy(),
                            new fabric.lang.Object[] { (fabric.metrics.Metric)
                                                         this.$getProxy(),
                              other })));
        }
        
        /**
   * @param other
   *            another {@link Metric} to take the maximum of along with this
   *            {@link Metric}.
   * @return a {@link Metric} that tracks the value of the maximum of this and
   *         the other {@link Metric}.
   */
        public fabric.metrics.Metric max(fabric.metrics.Metric other) {
            return this.times(-1).min(other.times(-1)).times(-1);
        }
        
        /**
   * @param bound
   *            the {@link Bound} that the returned {@link MetricContract}
   *            will enforce on this {@link Metric}
   * @return a {@link MetricContract} that enforces that the {@link Metric}
   *         satisfies the given {@link Bound}. If such a
   *         {@link MetricContract}, it is returned, otherwise a new one is
   *         created and returned (unactivated).
   */
        public fabric.metrics.contracts.MetricContract getContract(
          fabric.metrics.contracts.Bound bound) {
            fabric.metrics.contracts.MetricContract mc = null;
            for (int i = 0; i < this.get$contracts().size(); i++) {
                fabric.metrics.contracts.MetricContract c =
                  (fabric.metrics.contracts.MetricContract)
                    fabric.lang.Object._Proxy.$getProxy(
                                                this.get$contracts().get(i));
                if (c.stale()) {
                    removeObserver(c);
                    i--;
                } else if (fabric.lang.Object._Proxy.idEquals(mc, null) &&
                             c.isActivated() &&
                             c.enforces((fabric.metrics.Metric)
                                          this.$getProxy(), bound)) {
                    mc = c;
                }
            }
            if (fabric.lang.Object._Proxy.idEquals(mc, null))
                mc = createContract(bound);
            return mc;
        }
        
        /**
   * @param rate
   *            the rate parameter for the {@link Bound} on the resuling
   *            {@link MetricContract}
   * @param base
   *            the base parameter for the {@link Bound} on the resuling
   *            {@link MetricContract}
   * @param time
   *            the startTime parameter of the {@link Bound} on the resulting
   *            {@link MetricContract}
   * @return a {@link MetricContract} which enforces that the {@link Metric}
   *         satisfies a {@link Bound} with the given parameters at the given
   *         time. If such a {@link MetricContract} already exists, it is
   *         returned, otherwise a new one is created and returned
   *         (unactivated).
   *
   */
        public fabric.metrics.contracts.MetricContract getContract(double rate,
                                                                   double base,
                                                                   long time) {
            return getContract(
                     ((fabric.metrics.contracts.Bound)
                        new fabric.metrics.contracts.Bound._Impl(
                          this.$getStore()).$getProxy()).
                         fabric$metrics$contracts$Bound$(rate, base, time));
        }
        
        /**
   * @param rate
   *            the rate parameter for the {@link Bound} on the resuling
   *            {@link MetricContract}
   * @param base
   *            the base parameter for the {@link Bound} on the resuling
   *            {@link MetricContract}
   * @return a {@link MetricContract} which enforces that the {@link Metric}
   *         satisfies a {@link Bound} with the given parameters at the
   *         current time. If such a {@link MetricContract} already exists, it
   *         is returned, otherwise a new one is created and returned
   *         (unactivated).
   */
        public fabric.metrics.contracts.MetricContract getContract(
          double rate, double base) {
            return getContract(
                     ((fabric.metrics.contracts.Bound)
                        new fabric.metrics.contracts.Bound._Impl(
                          this.$getStore()).$getProxy()).
                         fabric$metrics$contracts$Bound$(
                           rate, base, java.lang.System.currentTimeMillis()));
        }
        
        /**
   * Create a locally tracked {@link DerivedMetric} that takes a scaled value
   * of another {@link Metric}.
   *
   * @param s
   *            the {@link Store} the returned {@link DerivedMetric} will be
   *            stored at
   * @param a
   *        the scaling factor
   * @param term
   *        the {@link Metric}
   * @return the locally tracked {@link DerivedMetric} for the scaled value of
   *       the given {@link Metric}.
   */
        public static fabric.metrics.Metric scaleAtStore(
          final fabric.worker.Store s, double a, fabric.metrics.Metric term) {
            if (term.$getStore().equals(s))
                return fabric.metrics.Metric._Impl.findDerivedMetric(
                                                     s, term.times(a));
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.ScaledMetric)
                   new fabric.metrics.ScaledMetric._Impl(s).$getProxy()).
                    fabric$metrics$ScaledMetric$(a, term));
        }
        
        /**
   * Create a locally tracked {@link DerivedMetric} that takes the sum of
   * other {@link Metric}s.
   *
   * @param s
   *            the {@link Store} the returned {@link DerivedMetric} will be
   *            stored at
   * @param terms
   *            the {@link Metric}s to sum
   * @return the locally tracked {@link DerivedMetric} for the sum of the
   *       terms.
   */
        public static fabric.metrics.Metric addAtStore(
          final fabric.worker.Store s, fabric.lang.arrays.ObjectArray terms) {
            if (terms.get$length() == 0)
                throw new java.lang.IllegalArgumentException(
                        "Must have at least 1 term!");
            if (terms.get$length() == 1)
                return fabric.metrics.Metric._Impl.scaleAtStore(
                                                     s,
                                                     1.0,
                                                     (fabric.metrics.Metric)
                                                       terms.get(0));
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.SumMetric)
                   new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                    fabric$metrics$SumMetric$(terms));
        }
        
        /**
   * Create a locally tracked {@link DerivedMetric} that takes the minimum of
   * other {@link Metric}s.
   *
   * @param s
   *            the {@link Store} the returned {@link Metric} will be stored
   *            at
   * @param terms
   *        the {@link Metric}s to take the min of
   * @return the locally tracked {@link Metric} for the minimum of the terms.
   */
        public static fabric.metrics.Metric minAtStore(
          final fabric.worker.Store s, fabric.lang.arrays.ObjectArray terms) {
            if (terms.get$length() == 0)
                throw new java.lang.IllegalArgumentException(
                        "Must have at least 1 term!");
            if (terms.get$length() == 1)
                return fabric.metrics.Metric._Impl.scaleAtStore(
                                                     s,
                                                     1.0,
                                                     (fabric.metrics.Metric)
                                                       terms.get(0));
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.MinMetric)
                   new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                    fabric$metrics$MinMetric$(terms));
        }
        
        /**
   * Create a locally tracked {@link DerivedMetric} that takes the maximum of
   * other {@link Metric}s.
   *
   * @param s
   *            the {@link Store} the returned {@link Metric} will be stored
   *            at
   * @param terms
   *        the {@link Metric}s to take the max of
   * @return the locally tracked {@link Metric} for the maximum of the terms.
   */
        public static fabric.metrics.Metric maxAtStore(
          final fabric.worker.Store s, fabric.lang.arrays.ObjectArray terms) {
            if (terms.get$length() == 0)
                throw new java.lang.IllegalArgumentException(
                        "Must have at least 1 term!");
            if (terms.get$length() == 1)
                return fabric.metrics.Metric._Impl.scaleAtStore(
                                                     s,
                                                     1.0,
                                                     (fabric.metrics.Metric)
                                                       terms.get(0));
            for (int i = 0; i < terms.get$length(); i++) {
                terms.set(i, ((fabric.metrics.Metric) terms.get(i)).times(-1));
            }
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                s, fabric.metrics.Metric._Impl.minAtStore(s, terms).times(-1));
        }
        
        /**
   * @param store
   *            the Store that holds this {@link Metric}
   */
        public fabric.metrics.Metric fabric$metrics$Metric$() {
            this.set$contracts(
                   ((fabric.util.ArrayList)
                      new fabric.util.ArrayList._Impl(
                        this.$getStore()).$getProxy()).fabric$util$ArrayList$(
                                                         ));
            this.set$weakStats(
                   (fabric.lang.arrays.doubleArray)
                     new fabric.lang.arrays.doubleArray._Impl(
                       this.$getStore()).fabric$lang$arrays$doubleArray$(
                                           this.get$$updateLabel(),
                                           this.get$$updateLabel().confPolicy(),
                                           3).$getProxy());
            fabric$metrics$util$AbstractSubject$();
            return (fabric.metrics.Metric) this.$getProxy();
        }
        
        public fabric.util.List get$contracts() { return this.contracts; }
        
        public fabric.util.List set$contracts(fabric.util.List val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.contracts = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.List contracts;
        
        /** @return the estimated velocity of the {@link Metric}. */
        public abstract double velocity();
        
        /**
   * @return the estimated noise (the <em>variance</em> of the velocity
   *         estimate) of the {@link Metric}.
   */
        public abstract double noise();
        
        /**
   * Used to construct and enforce {@link MetricContract}s bounding this
   * {@link Metric}s value.
   * <p>
   * Implementations of this method should use "weak" estimates of value,
   * velocity, and noise to avoid contention issues. The internal
   * implementation is expected to have refreshed these estimates recently
   * prior to the call.
   *
   * @param bound
   *            a {@link Bound} that the returned policy enforces.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          fabric.metrics.contracts.Bound bound) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                      new fabric.metrics.contracts.enforcement.DirectPolicy.
                        _Impl(this.$getStore()).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$DirectPolicy$(
                (fabric.metrics.Metric) this.$getProxy(), bound);
        }
        
        public fabric.lang.arrays.doubleArray get$weakStats() {
            return this.weakStats;
        }
        
        public fabric.lang.arrays.doubleArray set$weakStats(
          fabric.lang.arrays.doubleArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.weakStats = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.arrays.doubleArray weakStats;
        
        /**
   * Update the current "weak" estimates to be used by {@link #policy(Bound)}
   */
        public void refreshWeakEstimates() {
            {
                fabric.worker.transaction.TransactionManager $tm26 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff27 = 1;
                boolean $doBackoff28 = true;
                $label22: for (boolean $commit23 = false; !$commit23; ) {
                    if ($doBackoff28) {
                        if ($backoff27 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff27);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e24) {  }
                            }
                        }
                        if ($backoff27 < 5000) $backoff27 *= 2;
                    }
                    $doBackoff28 = $backoff27 <= 32 || !$doBackoff28;
                    $commit23 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        this.get$weakStats().set(0, computeWeakValue());
                        this.get$weakStats().set(1, computeWeakVelocity());
                        this.get$weakStats().set(2, computeWeakNoise());
                    }
                    catch (final fabric.worker.RetryException $e24) {
                        $commit23 = false;
                        continue $label22;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e24) {
                        $commit23 = false;
                        fabric.common.TransactionID $currentTid25 =
                          $tm26.getCurrentTid();
                        if ($e24.tid.isDescendantOf($currentTid25))
                            continue $label22;
                        if ($currentTid25.parent != null) throw $e24;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e24) {
                        $commit23 = false;
                        if ($tm26.checkForStaleObjects()) continue $label22;
                        throw new fabric.worker.AbortException($e24);
                    }
                    finally {
                        if ($commit23) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e24) {
                                $commit23 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e24) {
                                $commit23 = false;
                                fabric.common.TransactionID $currentTid25 =
                                  $tm26.getCurrentTid();
                                if ($currentTid25 != null) {
                                    if ($e24.tid.equals($currentTid25) ||
                                          !$e24.tid.isDescendantOf(
                                                      $currentTid25)) {
                                        throw $e24;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit23) {  }
                    }
                }
            }
        }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #value()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakValue() { return (double) this.get$weakStats().get(0); }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #velocity()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakVelocity() {
            return (double) this.get$weakStats().get(1);
        }
        
        /**
   * @return an estimated (not necessarily consistent) estimate of the
   *         {@link #noise()} as captured in the last call to
   *         {@link #refreshWeakEstimates()}.
   */
        public double weakNoise() { return (double) this.get$weakStats().get(2); }
        
        /** @return a freshly computed weak value for this {@link DerivedMetric}. */
        public abstract double computeWeakValue();
        
        /**
   * @return a freshly computed weak velocity for this {@link DerivedMetric}.
   */
        public abstract double computeWeakVelocity();
        
        /** @return a freshly computed weak noise for this {@link DerivedMetric}. */
        public abstract double computeWeakNoise();
        
        /**
   * @return true iff all the sampling and transformations on this metric are
   *         stored on a single store.
   */
        public abstract boolean isSingleStore();
        
        /**
   * @param contract
   *            a {@link MetricContract} to store with this {@link Metric}
   * @throws IllegalArgumentException
   *             if the {@link MetricContract} doesn't enforce a {@link Bound}
   *             on this {@link Metric}
   */
        public void addContract(
          fabric.metrics.contracts.MetricContract contract) {
            if (!contract.getMetric().equals((fabric.metrics.Metric)
                                               this.$getProxy()))
                throw new java.lang.IllegalArgumentException(
                        "Adding a contract for a different metric!");
            if (!this.get$contracts().contains(contract))
                this.get$contracts().add(contract);
        }
        
        /**
   * @param bound
   *        the {@link Bound} that the contract will enforce on this
   *        {@link Metric}
   * @return a {@link MetricContract} asserting this metric satisfies the
   *       given {@link Bound}.
   */
        public fabric.metrics.contracts.MetricContract createContract(
          fabric.metrics.contracts.Bound bound) {
            return ((fabric.metrics.contracts.MetricContract)
                      new fabric.metrics.contracts.MetricContract._Impl(
                        this.$getStore()).$getProxy()).
              fabric$metrics$contracts$MetricContract$((fabric.metrics.Metric)
                                                         this.$getProxy(),
                                                       bound);
        }
        
        /**
   * @param time
   *        the time we're searching for {@link MetricContract}s for this
   *        {@link Metric}
   * @return a {@link Set} of {@link MetricContract}s that are currently
   *       enforced for this {@link Metric}
   */
        public fabric.util.List getContracts(long time) {
            for (int i = 0; i < this.get$contracts().size(); i++) {
                fabric.metrics.contracts.MetricContract c =
                  (fabric.metrics.contracts.MetricContract)
                    fabric.lang.Object._Proxy.$getProxy(
                                                this.get$contracts().get(i));
                if (c.stale(time)) {
                    removeObserver(c);
                    i--;
                }
            }
            return this.get$contracts();
        }
        
        public int compareTo(java.lang.Object that) {
            if (!(fabric.lang.Object._Proxy.
                    $getProxy(that) instanceof fabric.metrics.Metric)) return 0;
            fabric.metrics.Metric
              other =
              (fabric.metrics.Metric)
                fabric.lang.Object._Proxy.
                $getProxy(fabric.lang.WrappedJavaInlineable.$wrap(that));
            int thisHash = hashCode();
            int thatHash =
              ((java.lang.Comparable)
                 fabric.lang.WrappedJavaInlineable.$unwrap(other)).hashCode();
            if (thisHash == thatHash) {
                if (other.equals((fabric.metrics.Metric) this.$getProxy())) {
                    return 0;
                }
                else {
                    return toString().
                      compareTo(
                        ((java.lang.Comparable)
                           fabric.lang.WrappedJavaInlineable.$unwrap(other)).
                            toString());
                }
            }
            return thisHash - thatHash;
        }
        
        /**
   * Utility for removing a contract if it's no longer observing and is now
   * stale.
   *
   * @param contract
   *            a {@link MetricContract} to stop storing with this
   *            {@link Metric} (if it is now invalid).
   * @throws IllegalArgumentException
   *             if contract isn't defined on this {@link Metric}
   */
        private void clearContract(
          fabric.metrics.contracts.MetricContract contract) {
            if (!contract.getMetric().equals((fabric.metrics.Metric)
                                               this.$getProxy()))
                throw new java.lang.IllegalArgumentException(
                        "clearing a contract for a different metric!");
            if (contract.stale()) this.get$contracts().remove(contract);
        }
        
        /**
   * {@inheritDoc}
   *
   * If the {@link Observer} is a {@link MetricContract} and is now
   * {@link Contract#stale()}, this clears it out from the Metric's stored
   * contract set.
   */
        public void removeObserver(fabric.metrics.util.Observer obs) {
            super.removeObserver(obs);
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        obs)) instanceof fabric.metrics.contracts.MetricContract) {
                fabric.metrics.contracts.MetricContract mc =
                  (fabric.metrics.contracts.MetricContract)
                    fabric.lang.Object._Proxy.$getProxy(obs);
                ((fabric.metrics.Metric._Impl) this.fetch()).clearContract(mc);
            }
        }
        
        /**
   * @param s
   *            the {@link Store} we're looking for the given {@link Metric}
   *            on
   * @param m
   *        the transformed metric we're looking up
   * @return the existing equivalent {@link DerivedMetric} tracked by this
   *       {@link Store}, if one exists. Otherwise, starts tracking
   *       <code>m</code> and returns it.
   */
        public static fabric.metrics.DerivedMetric findDerivedMetric(
          final fabric.worker.Store s, fabric.metrics.DerivedMetric m) {
            fabric.metrics.DerivedMetric orig =
              (fabric.metrics.DerivedMetric)
                fabric.lang.Object._Proxy.$getProxy(s.derivedMap().get(m));
            if (fabric.lang.Object._Proxy.idEquals(orig, null)) {
                s.derivedMap().put(m, m);
                return m;
            } else {
                return orig;
            }
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.Metric._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.contracts, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.weakStats, refTypes, out,
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
            this.contracts = (fabric.util.List)
                               $readRef(fabric.util.List._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(), in, store,
                                        intraStoreRefs, interStoreRefs);
            this.weakStats =
              (fabric.lang.arrays.doubleArray)
                $readRef(fabric.lang.arrays.doubleArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.Metric._Impl src = (fabric.metrics.Metric._Impl)
                                                other;
            this.contracts = src.contracts;
            this.weakStats = src.weakStats;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.Metric._Static {
            public _Proxy(fabric.metrics.Metric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.Metric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  Metric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.Metric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.Metric._Static._Impl.class);
                $instance = (fabric.metrics.Metric._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.Metric._Static {
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
                return new fabric.metrics.Metric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 62, 64, -110, 109, 110,
    112, 127, 3, -18, -11, -11, -27, 97, -95, 81, 57, -66, 68, -99, -4, -103,
    -52, -11, -1, -37, 46, -20, -56, -94, 57, 76, 36 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1502140552000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
