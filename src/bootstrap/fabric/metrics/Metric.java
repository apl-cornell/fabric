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
                    stopTracking(c);
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
                    stopTracking(c);
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
    
    public static final byte[] $classHash = new byte[] { 104, -82, -61, 103, 30,
    -43, 85, -115, 84, 69, -91, 21, -69, -89, 76, -61, 58, 107, 33, 44, -13, 84,
    -42, 86, -10, 74, 98, 59, -128, -61, 3, 63 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492539456000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
