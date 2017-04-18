package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.util.Iterator;
import fabric.util.List;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.util.Subject;
import fabric.metrics.util.Matrix;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;

/**
 * Represents an observable quantity that changes over time. Internally, this
 * class estimates the velocity of the observed quantity, and the interval
 * between updates. Instances of this class can be observed by
 * {@link LinearMetric}s and {@link MetricContract}s.
 */
public interface Metric
  extends java.lang.Comparable, fabric.metrics.util.Subject {
    public fabric.metrics.Metric fabric$metrics$Metric$();
    
    public fabric.util.List get$contracts();
    
    public fabric.util.List set$contracts(fabric.util.List val);
    
    /** @return the current value of the quantity being measured. */
    public abstract double value();
    
    /** @return the estimated value of the measured quantity's velocity */
    public abstract double velocity();
    
    /**
   * @return the estimated value of the measured quantity's noise (the
   *       variance of the velocity estimate)
   */
    public abstract double noise();
    
    /**
   * @return true iff all the sampling and transformations on this metric are
   *       stored on a single store.
   */
    public abstract boolean isSingleStore();
    
    /**
   * @param bound
   *        a {@link Bound} we're checking the expected time to hit with
   *        this metric.
   * @return the time we expect to hit the bound, given the current velocity
   *       estimated.
   */
    public long expectedTimeToHit(fabric.metrics.contracts.Bound bound, long time);
    
    /**
   * @param bound
   *        the {@link Bound} that the contract will enforce on this
   *        {@link Metric}
   * @return a {@link MetricContract} asserting this metric satisfies the
   *       given {@link Bound}.
   */
    public abstract fabric.metrics.contracts.MetricContract createContract(fabric.metrics.contracts.Bound bound);
    
    /**
   * @param time
   *        the time we're searching for {@link MetricContract}s for this
   *        {@link Metric}
   * @return a {@link Set} of {@link MetricContract}s that are currently
   *       enforced for this {@link Metric}
   */
    public fabric.util.List getContracts(long time);
    
    /**
   * @param bound
   *        the {@link Bound} that the returned {@link MetricContract}
   *        will enforce on this {@link Metric}
   * @return a {@link MetricContract} that enforces that the metric satisfies
   *       the given {@link Bound}
   */
    public fabric.metrics.contracts.MetricContract getContract(fabric.metrics.contracts.Bound bound);
    
    /**
   * @param rate
   *        the rate parameter for the {@link Bound} on the resuling
   *        {@link MetricContract}
   * @param base
   *        the base parameter for the {@link Bound} on the resuling
   *        {@link MetricContract}
   * @param time
   *        the startTime parameter of the {@link Bound} on the resulting
   *        {@link MetricContract}
   * @return a {@link MetricContract} which enforces that the metric satisfies
   *       a {@link Bound} that enforces a {@link Bound} with the given
   *       parameters at the given time.
   */
    public fabric.metrics.contracts.MetricContract getContract(double rate,
                                                               double base, long time);
    
    /**
   * @param rate
   *        the rate parameter for the {@link Bound} on the resuling
   *        {@link MetricContract}
   * @param base
   *        the base parameter for the {@link Bound} on the resuling
   *        {@link MetricContract}
   * @return a {@link MetricContract} which enforces that the metric satisfies
   *       a {@link Bound} that enforces a {@link Bound} with the given
   *       parameters at the current time.
   */
    public fabric.metrics.contracts.MetricContract getContract(double rate, double base);
    
    /**
   * @param contract
   *        a {@link MetricContract} to store with this {@link Metric}
   * @throws IllegalArgumentException
   *         if contract isn't defined on this {@link Metric}
   */
    public void addContract(fabric.metrics.contracts.MetricContract contract);
    
    /**
   * @param scalar
   *        a double scalar to scale this metric by
   * @return A {@link LinearMetric} that tracks the scaled value of this
   *       {@link Metric}
   */
    public fabric.metrics.Metric times(double scalar);
    
    /**
   * @param other
   *        another {@link Metric} to add with this {@link Metric},
   *        element wise.
   * @return a {@link LinearMetric} that tracks the value of the sum of other
   *       and this
   */
    public fabric.metrics.Metric plus(fabric.metrics.Metric other);
    
    /**
   * @param other
   *        another {@link Metric} to take the minimum of along with this
   *        {@link Metric}
   * @return a {@link LinearMetric} that tracks the value of the minimum of
   *       other and this's entries.
   */
    public fabric.metrics.Metric min(fabric.metrics.Metric other);
    
    /**
   * @param other
   *            another {@link Metric} to take the maximum of along with this
   *            {@link Metric}
   * @return a {@link LinearMetric} that tracks the value of the maximum of
   *         other and this's entries.
   */
    public fabric.metrics.Metric max(fabric.metrics.Metric other);
    
    /**
   * Allows for sorting {@link Metric}s to help with normalizing
   * {@link DerivedMetric}s.
   */
    public int compareTo(java.lang.Object other);
    
    /**
   * Track directly by a MetricContract.
   */
    public void startTracking(fabric.metrics.contracts.MetricContract mc);
    
    /**
   * Stop tracking directly by a MetricContract.
   */
    public void stopTracking(fabric.metrics.contracts.MetricContract mc);
    
    public static class _Proxy extends fabric.metrics.util.Subject._Proxy
      implements fabric.metrics.Metric {
        public fabric.util.List get$contracts() {
            return ((fabric.metrics.Metric._Impl) fetch()).get$contracts();
        }
        
        public fabric.util.List set$contracts(fabric.util.List val) {
            return ((fabric.metrics.Metric._Impl) fetch()).set$contracts(val);
        }
        
        public fabric.metrics.Metric fabric$metrics$Metric$() {
            return ((fabric.metrics.Metric) fetch()).fabric$metrics$Metric$();
        }
        
        public double value() {
            return ((fabric.metrics.Metric) fetch()).value();
        }
        
        public double velocity() {
            return ((fabric.metrics.Metric) fetch()).velocity();
        }
        
        public double noise() {
            return ((fabric.metrics.Metric) fetch()).noise();
        }
        
        public boolean isSingleStore() {
            return ((fabric.metrics.Metric) fetch()).isSingleStore();
        }
        
        public long expectedTimeToHit(fabric.metrics.contracts.Bound arg1,
                                      long arg2) {
            return ((fabric.metrics.Metric) fetch()).expectedTimeToHit(arg1,
                                                                       arg2);
        }
        
        public fabric.metrics.contracts.MetricContract createContract(
          fabric.metrics.contracts.Bound arg1) {
            return ((fabric.metrics.Metric) fetch()).createContract(arg1);
        }
        
        public fabric.util.List getContracts(long arg1) {
            return ((fabric.metrics.Metric) fetch()).getContracts(arg1);
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
        
        public void addContract(fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.Metric) fetch()).addContract(arg1);
        }
        
        public fabric.metrics.Metric times(double arg1) {
            return ((fabric.metrics.Metric) fetch()).times(arg1);
        }
        
        public fabric.metrics.Metric plus(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.Metric) fetch()).plus(arg1);
        }
        
        public fabric.metrics.Metric min(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.Metric) fetch()).min(arg1);
        }
        
        public fabric.metrics.Metric max(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.Metric) fetch()).max(arg1);
        }
        
        public int compareTo(java.lang.Object arg1) {
            return ((fabric.metrics.Metric) fetch()).compareTo(arg1);
        }
        
        public static fabric.metrics.Metric findMetric(
          fabric.worker.Store arg1, fabric.metrics.Metric arg2) {
            return fabric.metrics.Metric._Impl.findMetric(arg1, arg2);
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
        
        public void startTracking(
          fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.Metric) fetch()).startTracking(arg1);
        }
        
        public void stopTracking(fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.Metric) fetch()).stopTracking(arg1);
        }
        
        public _Proxy(Metric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.metrics.util.Subject._Impl
      implements fabric.metrics.Metric {
        public fabric.metrics.Metric fabric$metrics$Metric$() {
            fabric$metrics$util$Subject$();
            return (fabric.metrics.Metric) this.$getProxy();
        }
        
        public fabric.util.List get$contracts() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.contracts;
        }
        
        public fabric.util.List set$contracts(fabric.util.List val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.contracts = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.List contracts =
          ((fabric.util.ArrayList)
             new fabric.util.ArrayList._Impl(this.$getStore()).$getProxy()).
          fabric$util$ArrayList$();
        
        /** @return the current value of the quantity being measured. */
        public abstract double value();
        
        /** @return the estimated value of the measured quantity's velocity */
        public abstract double velocity();
        
        /**
   * @return the estimated value of the measured quantity's noise (the
   *       variance of the velocity estimate)
   */
        public abstract double noise();
        
        /**
   * @return true iff all the sampling and transformations on this metric are
   *       stored on a single store.
   */
        public abstract boolean isSingleStore();
        
        /**
   * @param bound
   *        a {@link Bound} we're checking the expected time to hit with
   *        this metric.
   * @return the time we expect to hit the bound, given the current velocity
   *       estimated.
   */
        public long expectedTimeToHit(fabric.metrics.contracts.Bound bound,
                                      long time) {
            double adjustedRate = bound.get$rate() - velocity();
            return (long) (time + (value() - bound.value(time)) / adjustedRate);
        }
        
        /**
   * @param bound
   *        the {@link Bound} that the contract will enforce on this
   *        {@link Metric}
   * @return a {@link MetricContract} asserting this metric satisfies the
   *       given {@link Bound}.
   */
        public abstract fabric.metrics.contracts.MetricContract createContract(fabric.metrics.contracts.Bound bound);
        
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
                if (!c.isActive() || !c.isObserved() || !c.valid(time)) {
                    removeObserver(c);
                    this.get$contracts().remove(i--);
                }
            }
            return this.get$contracts();
        }
        
        /**
   * @param bound
   *        the {@link Bound} that the returned {@link MetricContract}
   *        will enforce on this {@link Metric}
   * @return a {@link MetricContract} that enforces that the metric satisfies
   *       the given {@link Bound}
   */
        public fabric.metrics.contracts.MetricContract getContract(
          fabric.metrics.contracts.Bound bound) {
            fabric.metrics.contracts.MetricContract mc = null;
            for (int i = 0; i < this.get$contracts().size(); i++) {
                fabric.metrics.contracts.MetricContract c =
                  (fabric.metrics.contracts.MetricContract)
                    fabric.lang.Object._Proxy.$getProxy(
                                                this.get$contracts().get(i));
                if (fabric.lang.Object._Proxy.idEquals(mc, null) &&
                      c.isActive() &&
                      c.enforces((fabric.metrics.Metric) this.$getProxy(),
                                 bound)) {
                    mc = c;
                } else if (!c.isActive() || !c.isObserved() ||
                             !c.valid(java.lang.System.currentTimeMillis())) {
                    removeObserver(c);
                    this.get$contracts().remove(i--);
                }
            }
            if (fabric.lang.Object._Proxy.idEquals(mc, null))
                mc = createContract(bound);
            return mc;
        }
        
        /**
   * @param rate
   *        the rate parameter for the {@link Bound} on the resuling
   *        {@link MetricContract}
   * @param base
   *        the base parameter for the {@link Bound} on the resuling
   *        {@link MetricContract}
   * @param time
   *        the startTime parameter of the {@link Bound} on the resulting
   *        {@link MetricContract}
   * @return a {@link MetricContract} which enforces that the metric satisfies
   *       a {@link Bound} that enforces a {@link Bound} with the given
   *       parameters at the given time.
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
   *        the rate parameter for the {@link Bound} on the resuling
   *        {@link MetricContract}
   * @param base
   *        the base parameter for the {@link Bound} on the resuling
   *        {@link MetricContract}
   * @return a {@link MetricContract} which enforces that the metric satisfies
   *       a {@link Bound} that enforces a {@link Bound} with the given
   *       parameters at the current time.
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
   * @param contract
   *        a {@link MetricContract} to store with this {@link Metric}
   * @throws IllegalArgumentException
   *         if contract isn't defined on this {@link Metric}
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
   * @param scalar
   *        a double scalar to scale this metric by
   * @return A {@link LinearMetric} that tracks the scaled value of this
   *       {@link Metric}
   */
        public fabric.metrics.Metric times(double scalar) {
            final fabric.worker.Store thisStore = getStore();
            return fabric.metrics.Metric._Impl.
              findMetric(
                getStore(),
                ((fabric.metrics.LinearMetric)
                   new fabric.metrics.LinearMetric._Impl(thisStore).$getProxy(
                                                                      )).
                    fabric$metrics$LinearMetric$(
                      fabric.metrics.util.Matrix._Impl.constant(1, 1, scalar),
                      fabric.lang.arrays.internal.Compat.
                          convert(
                            this.$getStore(),
                            this.get$$updateLabel(),
                            this.get$$updateLabel().confPolicy(),
                            new fabric.lang.Object[] { (fabric.metrics.Metric)
                                                         this.$getProxy() })));
        }
        
        /**
   * @param other
   *        another {@link Metric} to add with this {@link Metric},
   *        element wise.
   * @return a {@link LinearMetric} that tracks the value of the sum of other
   *       and this
   */
        public fabric.metrics.Metric plus(fabric.metrics.Metric other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.DerivedMetric)
                return other.plus((fabric.metrics.Metric) this.$getProxy());
            final fabric.worker.Store thisStore = getStore();
            return fabric.metrics.Metric._Impl.
              findMetric(
                getStore(),
                ((fabric.metrics.LinearMetric)
                   new fabric.metrics.LinearMetric._Impl(thisStore).$getProxy(
                                                                      )).
                    fabric$metrics$LinearMetric$(
                      fabric.metrics.util.Matrix._Impl.constant(1, 2, 1),
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
   *        another {@link Metric} to take the minimum of along with this
   *        {@link Metric}
   * @return a {@link LinearMetric} that tracks the value of the minimum of
   *       other and this's entries.
   */
        public fabric.metrics.Metric min(fabric.metrics.Metric other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.DerivedMetric)
                return other.min((fabric.metrics.Metric) this.$getProxy());
            if (this.equals(other)) return this.times(1);
            final fabric.worker.Store thisStore = getStore();
            return fabric.metrics.Metric._Impl.
              findMetric(
                getStore(),
                ((fabric.metrics.LinearMetric)
                   new fabric.metrics.LinearMetric._Impl(thisStore).$getProxy(
                                                                      )).
                    fabric$metrics$LinearMetric$(
                      fabric.metrics.util.Matrix._Impl.identity(2),
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
   *            {@link Metric}
   * @return a {@link LinearMetric} that tracks the value of the maximum of
   *         other and this's entries.
   */
        public fabric.metrics.Metric max(fabric.metrics.Metric other) {
            return this.times(-1).min(other.times(-1)).times(-1);
        }
        
        /**
   * Allows for sorting {@link Metric}s to help with normalizing
   * {@link DerivedMetric}s.
   */
        public int compareTo(java.lang.Object other) {
            return toString().compareTo(other.toString());
        }
        
        /**
   * @param s
   *        the store we're looking for the given metric on
   * @param m
   *        the transformed metric we're looking up
   * @return the existing equivalent {@link DerivedMetric} tracked by this
   *       {@link Store}, if one exists. Otherwise, starts tracking
   *       <code>m</code> and returns it.
   */
        public static fabric.metrics.Metric findMetric(
          final fabric.worker.Store s, fabric.metrics.Metric m) {
            fabric.metrics.DerivedMetric
              orig =
              (fabric.metrics.DerivedMetric)
                fabric.lang.Object._Proxy.
                $getProxy(
                  s.
                      derivedMap().
                      get(
                        fabric.lang.WrappedJavaInlineable.
                            $wrap(
                              ((java.lang.Object)
                                 fabric.lang.WrappedJavaInlineable.$unwrap(m)).
                                  toString())));
            if (fabric.lang.Object._Proxy.idEquals(orig, null)) {
                s.
                  derivedMap().
                  put(
                    fabric.lang.WrappedJavaInlineable.
                        $wrap(
                          ((java.lang.Object)
                             fabric.lang.WrappedJavaInlineable.$unwrap(m)).
                              toString()),
                    m);
                return m;
            }
            else {
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(m)) instanceof fabric.metrics.DerivedMetric)
                    ((fabric.metrics.DerivedMetric)
                       fabric.lang.Object._Proxy.$getProxy(m)).cleanup();
                return orig;
            }
        }
        
        /**
   * Create a locally tracked {@link DerivedMetric} that takes a scaled value
   * of another {@link Metric}.
   *
   * @param a
   *        the scaling factor
   * @param term
   *        the {@link Metric}
   * @return the locally tracked {@link DerivedMetric} for the scaled value of
   *       the given {@link Metric}.
   */
        public static fabric.metrics.Metric scaleAtStore(
          final fabric.worker.Store s, double a, fabric.metrics.Metric term) {
            if (term.getStore().equals(s))
                return fabric.metrics.Metric._Impl.findMetric(s, term.times(a));
            return fabric.metrics.Metric._Impl.
              findMetric(
                s,
                ((fabric.metrics.LinearMetric)
                   new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                    fabric$metrics$LinearMetric$(
                      fabric.metrics.util.Matrix._Impl.constant(1, 1, a),
                      fabric.lang.arrays.internal.Compat.
                          convert(
                            fabric.metrics.Metric._Static._Proxy.$instance.
                                $getStore(),
                            fabric.metrics.Metric._Static._Proxy.$instance.
                                get$$updateLabel(),
                            fabric.metrics.Metric._Static._Proxy.$instance.
                                get$$updateLabel().confPolicy(),
                            new fabric.lang.Object[] { term })));
        }
        
        /**
   * Create a locally tracked {@link DerivedMetric} that takes the sum of
   * other {@link Metric}s.
   *
   * @param terms
   *        the {@link Metric}s
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
              findMetric(
                s,
                ((fabric.metrics.LinearMetric)
                   new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                    fabric$metrics$LinearMetric$(
                      fabric.metrics.util.Matrix._Impl.constant(
                                                         1, terms.get$length(),
                                                         1), terms));
        }
        
        /**
   * Create a locally tracked {@link DerivedMetric} that takes the minimum of
   * other {@link Metric}s.
   *
   * @param terms
   *        the {@link Metric}s to take the min of
   * @return the locally tracked {@link DerivedMetric} for the minimum of the
   *       terms.
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
              findMetric(
                s,
                ((fabric.metrics.LinearMetric)
                   new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                    fabric$metrics$LinearMetric$(
                      fabric.metrics.util.Matrix._Impl.identity(
                                                         terms.get$length()),
                      terms));
        }
        
        /**
   * Create a locally tracked {@link DerivedMetric} that takes the maximum of
   * other {@link Metric}s.
   *
   * @param terms
   *        the {@link Metric}s to take the max of
   * @return the locally tracked {@link DerivedMetric} for the maximum of the
   *       terms.
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
              findMetric(
                s, fabric.metrics.Metric._Impl.minAtStore(s, terms).times(-1));
        }
        
        /**
   * Track directly by a MetricContract.
   */
        public void startTracking(fabric.metrics.contracts.MetricContract mc) {
            addObserver(mc);
        }
        
        /**
   * Stop tracking directly by a MetricContract.
   */
        public void stopTracking(fabric.metrics.contracts.MetricContract mc) {
            removeObserver(mc);
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
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.Metric._Impl src = (fabric.metrics.Metric._Impl)
                                                other;
            this.contracts = src.contracts;
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
    
    public static final byte[] $classHash = new byte[] { 32, -88, 41, -63, -59,
    33, -111, -93, 79, 64, 23, 77, 78, -88, -11, 95, 58, 58, -45, -45, 56, 52,
    -116, -58, -45, 59, 27, -15, 97, 62, -104, 85 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492522047000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
