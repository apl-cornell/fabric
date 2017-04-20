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
            this.set$contracts(
                   ((fabric.util.ArrayList)
                      new fabric.util.ArrayList._Impl(
                        this.$getStore()).$getProxy()).fabric$util$ArrayList$(
                                                         ));
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
        
        private fabric.util.List contracts;
        
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
                if (!c.isActive()) {
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
                } else if (!c.isActive()) {
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
    
    public static final byte[] $classHash = new byte[] { 39, 60, 83, 112, 91,
    70, 6, 86, -106, 107, -11, 40, -67, -112, 82, -10, -68, -108, 19, -24, 86,
    114, 110, 49, 57, 85, 10, 59, -58, -117, -9, 93 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492661472000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1aDZAUxRXu3fs/Du6O40SOOziOC8rfbjCJEQ6jcPytLOHCHVQ8ope52d678WZnlpneY5GgEmNBaUIscyBUBaoSSRkRMVoxppJQkvIPxLLEJJJfQVMmGKQSYlSSMjHvdffu7M7tDrvWUXS/2el+r9/3+r3XPd13+AIpsy3SFlX6NT3AtsSpHVih9IfCXYpl00inrth2D7ztU8eVhvacezgyzU/8YVKjKoZpaKqi9xk2IxPCtynDStCgLLh+XahjI6lSkXGVYg8y4t+4NGmR1ripbxnQTSYHGSV/99zgyIO31j1ZQmp7Sa1mdDOFaWqnaTCaZL2kJkZj/dSyl0QiNNJL6g1KI93U0hRdux06mkYvmWhrA4bCEha111Hb1Iex40Q7EacWHzP1EtU3QW0roTLTAvXrhPoJpunBsGazjjApj2pUj9ibyB2kNEzKoroyAB2vCKdQBLnE4Ap8D92rNVDTiioqTbGUDmlGhJHpbo404vbV0AFYK2KUDZrpoUoNBV6QiUIlXTEGgt3M0owB6FpmJmAURpryCoVOlXFFHVIGaB8jV7r7dYkm6FXFzYIsjDS6u3FJMGdNrjnLmK0LX1y8a6uxyvATH+gcoaqO+lcC0zQX0zoapRY1VCoYa+aE9yhXHN3pJwQ6N7o6iz5Pf+3ijfOmHTsu+kzN0Wdt/21UZX3qwf4Jp5o7Zy8sQTUq46atoStkIeez2iVbOpJx8PYr0hKxMZBqPLbuhZvvOkTP+0l1iJSrpp6IgVfVq2YsrunUWkkNaimMRkKkihqRTt4eIhXwHNYMKt6ujUZtykKkVOevyk3+G0wUBRFoogp41oyomXqOK2yQPyfjhJA6KMQH/7sImTsCz42E+A8ysiw4aMZosF9P0M3g3kEoVLHUwSDEraWpQdtSg1bCYBp0kq/Ai4DYwTWcBmD8+BjJSaK+dZt9PjDldNWM0H7FhnmRPrK0S4cwWGXqEWr1qfquoyHScHQf95Mq9G0b/JNbwgdz2+zOCpm8I4mlyy8e6TspfAx5paHAZYVyAalcQCgH+tRg2AQgEQUgER32JQOdB0KPcu8ot3kYpUXUgIhFcV1hUdOKJYnPx/FM4vzcLWBShyBZQD6omd19y01f3dlWAv4Y31yKUwRd293R4eSUEDwp4PJ9au2Ocx88vmeb6cQJI+2jwnc0J4Zfm9s4lqnSCKQ3R/ycVuWpvqPb2v2YOqogqzEF/A5SxDT3GFlh2JFKaWiNsjAZhzZQdGxK5aFqNmiZm503fNInYDVRzD8ay6Ugz4bXd8f3//aVdz7D14lU4qzNyLDdlHVkBCsKq+VhWe/YvseiFPr9aW/Xd3Zf2LGRGx56zMw1YDvWnRCkCkSnad1zfNPvzrxx8Nd+Z7IYKY8n+nVNTXIs9R/DPx+U/2HBiMMXSCHvdspob02HexxHnuXoBoGvQ/IB1e329UbMjGhRTenXKXrKR7WfWvDUu7vqxHTr8EYYzyLzLi/AeT9lKbnr5K0fTuNifCouPI79nG4imzU4kpdYlrIF9Uhuf61l34vKfvB8yEW2djvl6YVwexA+gddwW8zn9QJX22exahPWak47vDuzr8Al0vHF3uDh7zZ1fuG8CPW0L6KMGTlCfYOSESbXHIq9728rf95PKnpJHV+dFYNtUCBPgRv0wvpqd8qXYTI+qz17rRQLQ0c61prdcZAxrDsKnBQDz9gbn6uF4wvHAUNMQiN9HkoLISXPS/p9bG2IYz0p6SP8YRFnmcnrWVjNTjljRdzShsGzkmmhfhRaJYU9KOn9GUKZMKilqMzmXI2wa5E5UCYrm/GGKe7MxoM1mVsZPz7OYaRS6be5cEcl/q9WrkAPSTqSoVKWc0iVRPbEiQjIgAS3TqlVhWrpJuwZk0nwppZ8mwu+MTr49ZEDkbU/WCC2ABOzF+zlRiL22Ov/fTmw9+yJHItDFTPj83U6TPUMHa+EIWeM2uWu4Xsvxw/Pnm9Z2Dn09oAYdrpLRXfvR9YcPrFylvqAn5SkHW7Uhi+bqSPbzaotCvtVoyfL2VrTk1CDkzAXymTwh3pB/f/MdDaRivN5WlXcMhnEA424fG2clHVR0nPuic2dF27xaOvDagOkKuGW7XJpbhdLc7ujaHdalYnI3CpK6RxJmwuEJ3wXq5UucPVS0lRJawoDN+DRpmGlMFI2jCknRy7ssrQYLGfDcpdLd47c+3Fg14jwTvEpMHPUbjyTR3wO8MHGYzUXY2SG1yicY8VfH9/28x9u2+GXiq6FtS5iJlJh57L1TFFK90m6c0xsjZJ2SJoszNabPdq2YLUJkhIEsKlqjP+O5XOd2YSUXS9pcEzgoKSApNMLg7Pdo+1urLaC6ximZtO8WGA0EoQRz0l6akywoKRXJf1lYVju82j7Flb3MDJes7thL63TbthwCU8bkk6LBD7AKvpNU6eK4YJalVo9ryOkokzQ8tOFJjQO1YWyUgp5XdJX86P0C4OlFqtprm+I9AIbWGomjEju1TQX1lLdNAa44ns9bPc9rB5gpB62Yjwl98BHVo+5SuNbxP0uQzUi3w1QlgHEJyUtKlx7c/nEJClph6SJ/NbyOTbfxwd7xAPco1g9xMgE1aKws+lMmVKa+qq8pharQ1b/3DZ3TMMXxcVQ1oA/nZP0p3lMg9XNo9c+ZHla0iMF2WA/l/oTDxtwFZ4A5QcoywK0JFcQfBpKH4CpFnTcnz0A7B7t8sjylqR/LGISn/EAwBPEzxgZlwEAXz2WS/9VULYRUjdD0Nq3itMfWd6U9A/59S/h6pXwrJmuxFQc90DyElbPFogEV49vQha+U9LVxSFBlpskXXbZ5OMg4aJPeYD4FVYvFwjiGih7CGn4l6QniwOBLC9J+lxB7vQYl/p7D/25W/4G9Fcikax4cKfPYVOL5MLUDuUgpMJDkn67OEzIskvSnQVhEnPyFw9MfJ/8JizmeEJmOzq4NG+DAkl78nZJB4vTHFkGJFUK0ryOS/27h+YXsfobWDuuJ/IrjtH8LCFTTkj6RHGKI8uPJD1UhOKXPBT/D1bvMVIS0wxPvU8T0nyvpFZxeiPLJkmHCtfb58uvt48nrY9QbyWZV++FUM7C5/52Sb9UnN7I0iXpTZfVO7Ua1zlf6eK4BN+HudLVHoDw8M9Xxk8i8NMe9i65orlEM5gLKT/UXgTlAiHTV0rakgdp7v0tw+NbvA9y7WdqpbRmSWvzG8G1+2uQW5LNpjVErYCzkc1zgILgmzys04pVAyPVUfgqE7uaXLPObRGCyQDOGf+W9FhRtsCBJo+2A5f0jKRP5reDs6T6pvKk5+C72gMfH7YNbGOrik6XMG6wvAhvBD1gr9neIujMC2OCECW9K+mZy840R4gHTa5TH5gZfk4qvtRfefjSlKPt71wSJz7uG6+Mjv84fOb8a+NbjvCj9lK88sDxqt1XhaNvArMu+Dj4muy9LH7/XQ0faGslDTGy+pPf0yyjljZMI1nXPmMpLmP63LnlWjT5tXgA6vqJDx3e8V0GcaMI4XMh2nVqDPCLMV8dVp/LzSy/CDkTdrvBYUjmDfyMM0r4cKN4RMkTYKqDOMTUzED6ejnVI5kTdbeAmaEmz9ZcKY+AWu3RtgarVWAUFTXEH+OSvMHj0N7XhdU8yECw3yokPmcRMuvHkn5jTOITJd0t6XBh8SnmCusve0DDb1nfOoAGe4BCoF1HyOzlkjaOCTSUNEnSimKh9XtAi2D1FYSmJD2g8d1CABRYQcicC5IezwON+9+o3QJneVFSj8Mg9ybfN+ShPq4evigj42F5tlgP7PGH5CXrG7kQzAfB8M0NES5oV3EIkGWtpKEiEDAPBOioPhMXNmbGswAkIQ+JvIfXbFNz3HTLv7RQO5+jB99ePa8xzy33laP+9kXyHTlQWzn5wPrTYk1J/RVFVZhURhO6nnkdlfFcHrdoVOPWqhKXU3EOZCsjE7LPWRhfe/AJkfu2iH53ACzRD3/dyS3YxKtUBpzqOq7hly7difResYkP25Sw8G97Dr83+VJ5Zc9Zft8KRm29anF3fOOK8g17ht6/+uj96z74xUjDuQ2WsWDh+uqOF+778Jb/A9jzAE5zJAAA";
}
