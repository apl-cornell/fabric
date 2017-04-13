package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.HashSet;
import fabric.util.Iterator;
import fabric.util.Set;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.util.Subject;
import fabric.metrics.util.Matrix;
import fabric.worker.Store;

/**
 * Represents an observable quantity that changes over time. Internally, this
 * class estimates the velocity of the observed quantity, and the interval
 * between updates. Instances of this class can be observed by
 * {@link LinearMetric}s and {@link MetricContract}s.
 */
public interface Metric
  extends java.lang.Comparable, fabric.metrics.util.Subject {
    public fabric.metrics.Metric fabric$metrics$Metric$();
    
    public fabric.util.Set get$contracts();
    
    public fabric.util.Set set$contracts(fabric.util.Set val);
    
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
    public fabric.util.Set getContracts(long time);
    
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
   *        another {@link Metric} to take the maximum of along with this
   *        {@link Metric}
   * @return a {@link LinearMetric} that tracks the value of the maximum of
   *       other and this's entries.
   */
    public fabric.metrics.Metric max(fabric.metrics.Metric other);
    
    /**
   * Allows for sorting {@link Metric}s to help with normalizing
   * {@link DerivedMetric}s.
   */
    public int compareTo(java.lang.Object other);
    
    public static class _Proxy extends fabric.metrics.util.Subject._Proxy
      implements fabric.metrics.Metric {
        public fabric.util.Set get$contracts() {
            return ((fabric.metrics.Metric._Impl) fetch()).get$contracts();
        }
        
        public fabric.util.Set set$contracts(fabric.util.Set val) {
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
        
        public fabric.util.Set getContracts(long arg1) {
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
        
        public static fabric.metrics.Metric findMetric(
          fabric.worker.Store arg1, fabric.metrics.Metric arg2) {
            return fabric.metrics.Metric._Impl.findMetric(arg1, arg2);
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
        
        public fabric.metrics.Metric max(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.Metric) fetch()).max(arg1);
        }
        
        public int compareTo(java.lang.Object arg1) {
            return ((fabric.metrics.Metric) fetch()).compareTo(arg1);
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
        
        public fabric.util.Set get$contracts() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.contracts;
        }
        
        public fabric.util.Set set$contracts(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.contracts = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Set contracts =
          ((fabric.util.HashSet)
             new fabric.util.HashSet._Impl(this.$getStore()).$getProxy()).
          fabric$util$HashSet$();
        
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
            return ((fabric.metrics.contracts.Bound)
                      new fabric.metrics.contracts.Bound._Impl(
                        this.$getStore()).$getProxy()).
              fabric$metrics$contracts$Bound$(adjustedRate, bound.get$base(),
                                              bound.get$startTime()).
              trueExpiry((fabric.metrics.Metric) this.$getProxy(), time);
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
        public fabric.util.Set getContracts(long time) {
            fabric.util.Iterator cIter =
              ((fabric.util.HashSet)
                 new fabric.util.HashSet._Impl(this.$getStore()).$getProxy()).
              fabric$util$HashSet$(this.get$contracts()).iterator();
            while (cIter.hasNext()) {
                fabric.metrics.contracts.MetricContract c =
                  (fabric.metrics.contracts.MetricContract)
                    fabric.lang.Object._Proxy.$getProxy(cIter.next());
                if (!c.isActive() || !c.isObserved() || !c.valid(time)) {
                    this.removeObserver(c);
                    this.get$contracts().remove(c);
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
            fabric.util.Iterator cIter =
              getContracts(java.lang.System.currentTimeMillis()).iterator();
            while (cIter.hasNext()) {
                fabric.metrics.contracts.MetricContract existing =
                  (fabric.metrics.contracts.MetricContract)
                    fabric.lang.Object._Proxy.$getProxy(cIter.next());
                if (existing.enforces((fabric.metrics.Metric) this.$getProxy(),
                                      bound)) { return existing; }
            }
            fabric.metrics.contracts.MetricContract mc = createContract(bound);
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
            this.get$contracts().add(contract);
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
            if (fabric.lang.Object._Proxy.
                  idEquals(
                    s.
                        derivedMap().
                        get(
                          fabric.lang.WrappedJavaInlineable.
                              $wrap(
                                ((java.lang.Object)
                                   fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                       m)).
                                    toString())),
                    null))
                s.
                  derivedMap().
                  put(
                    fabric.lang.WrappedJavaInlineable.
                        $wrap(
                          ((java.lang.Object)
                             fabric.lang.WrappedJavaInlineable.$unwrap(m)).
                              toString()),
                    m);
            else if (fabric.lang.Object._Proxy.
                       $getProxy(
                         (java.lang.Object)
                           fabric.lang.WrappedJavaInlineable.
                           $unwrap(m)) instanceof fabric.metrics.DerivedMetric)
                ((fabric.metrics.DerivedMetric)
                   fabric.lang.Object._Proxy.$getProxy(m)).cleanup();
            return (fabric.metrics.Metric)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       s.
                           derivedMap().
                           get(
                             fabric.lang.WrappedJavaInlineable.
                                 $wrap(
                                   ((java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                          m)).
                                       toString())));
        }
        
        /**
   * @param scalar
   *        a double scalar to scale this metric by
   * @return A {@link LinearMetric} that tracks the scaled value of this
   *       {@link Metric}
   */
        public fabric.metrics.Metric times(double scalar) {
            if (scalar == 1.0) return (fabric.metrics.Metric) this.$getProxy();
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
            if (this.equals(other))
                return (fabric.metrics.Metric) this.$getProxy();
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
   * @param other
   *        another {@link Metric} to take the maximum of along with this
   *        {@link Metric}
   * @return a {@link LinearMetric} that tracks the value of the maximum of
   *       other and this's entries.
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
            this.contracts = (fabric.util.Set)
                               $readRef(fabric.util.Set._Proxy.class,
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
    
    public static final byte[] $classHash = new byte[] { 94, 12, 105, 58, 85,
    -118, -38, 51, 28, 71, 27, -118, -52, 103, -107, -115, -91, 104, -102, -26,
    -66, 84, 20, 12, 45, -16, -40, -85, -108, 103, 97, -17 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492108437000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
