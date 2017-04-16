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
            final fabric.worker.Store thisStore = $getStore();
            return fabric.metrics.Metric._Impl.
              findMetric(
                $getStore(),
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
            final fabric.worker.Store thisStore = $getStore();
            return fabric.metrics.Metric._Impl.
              findMetric(
                $getStore(),
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
            final fabric.worker.Store thisStore = $getStore();
            return fabric.metrics.Metric._Impl.
              findMetric(
                $getStore(),
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
            if (term.$getStore().equals(s))
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
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINER,
                "TRACKING {0}",
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                      (fabric.metrics.Metric)
                                                        this.$getProxy()));
            addObserver(mc);
        }
        
        /**
   * Stop tracking directly by a MetricContract.
   */
        public void stopTracking(fabric.metrics.contracts.MetricContract mc) {
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINER,
                "STOP TRACKING {0}",
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                      (fabric.metrics.Metric)
                                                        this.$getProxy()));
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
    
    public static final byte[] $classHash = new byte[] { 106, 70, 69, -74, 84,
    18, 100, 90, 58, -94, -78, 5, 90, 97, -43, -68, 70, 50, -20, -15, -56, 109,
    -119, 98, 113, -8, 122, -21, -27, -85, -107, 39 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492372241000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
