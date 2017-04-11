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
   * @return a {@link MetricContract} which enforces that the metric satisfies
   *       a {@link Bound} that enforces the given {@link Bound}
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
        
        public static fabric.metrics.DerivedMetric findDerivedMetric(
          fabric.worker.Store arg1, fabric.metrics.DerivedMetric arg2) {
            return fabric.metrics.Metric._Impl.findDerivedMetric(arg1, arg2);
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
          (fabric.util.HashSet)
            new fabric.util.HashSet._Impl(this.$getStore()).$getProxy();
        
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
            double curVelocity = velocity();
            double adjustedRate = bound.get$rate() - curVelocity;
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
                if (!c.isActive() || !c.isObserved() || !c.valid(time))
                    this.get$contracts().remove(c);
            }
            return this.get$contracts();
        }
        
        /**
   * @param bound
   *        the {@link Bound} that the returned {@link MetricContract}
   *        will enforce on this {@link Metric}
   * @return a {@link MetricContract} which enforces that the metric satisfies
   *       a {@link Bound} that enforces the given {@link Bound}
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
   * Dumb hack to allow us to get at a map on the store for avoiding duplicated
   * (inconsistent) metric computations being distributed.
   */
        public static fabric.metrics.DerivedMetric findDerivedMetric(
          fabric.worker.Store s, fabric.metrics.DerivedMetric m) {
            if (fabric.lang.Object._Proxy.
                  idEquals(
                    s.derivedMap().
                        get(
                          fabric.lang.WrappedJavaInlineable.$wrap(
                                                              m.toString())),
                    null))
                s.derivedMap().put(
                                 fabric.lang.WrappedJavaInlineable.$wrap(
                                                                     m.toString(
                                                                         )), m);
            else
                m.cleanup();
            return (fabric.metrics.DerivedMetric)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       s.derivedMap().
                           get(
                             fabric.lang.WrappedJavaInlineable.$wrap(
                                                                 m.toString(
                                                                     ))));
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
              findDerivedMetric(
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
              findDerivedMetric(
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
              findDerivedMetric(
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
        
        public static fabric.metrics.Metric scaleAtStore(
          final fabric.worker.Store s, double a, fabric.metrics.Metric m) {
            if (m.$getStore().equals(s)) return m.times(a);
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
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
                            new fabric.lang.Object[] { m })));
        }
        
        public static fabric.metrics.Metric addAtStore(
          final fabric.worker.Store s, fabric.lang.arrays.ObjectArray terms) {
            if (terms.get$length() == 0)
                throw new java.lang.IllegalArgumentException(
                        "Need at least one metric to sum!");
            if (terms.get$length() == 1)
                return fabric.metrics.Metric._Impl.scaleAtStore(
                                                     s,
                                                     1,
                                                     (fabric.metrics.Metric)
                                                       terms.get(0));
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.LinearMetric)
                   new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                    fabric$metrics$LinearMetric$(
                      fabric.metrics.util.Matrix._Impl.constant(
                                                         1, terms.get$length(),
                                                         1), terms));
        }
        
        public static fabric.metrics.Metric minAtStore(
          final fabric.worker.Store s, fabric.lang.arrays.ObjectArray terms) {
            if (terms.get$length() == 0)
                throw new java.lang.IllegalArgumentException(
                        "Need at least one metric to min!");
            if (terms.get$length() == 1)
                return fabric.metrics.Metric._Impl.scaleAtStore(
                                                     s,
                                                     1,
                                                     (fabric.metrics.Metric)
                                                       terms.get(0));
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.LinearMetric)
                   new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                    fabric$metrics$LinearMetric$(
                      fabric.metrics.util.Matrix._Impl.identity(
                                                         terms.get$length()),
                      terms));
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
    
    public static final byte[] $classHash = new byte[] { 110, -81, 110, 95, -58,
    77, 117, 40, 48, -98, 35, -100, -43, -118, -120, 112, -51, -61, 59, 71, 109,
    -23, 98, 122, 100, 61, -39, 97, 66, -115, -18, -109 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491926288000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
