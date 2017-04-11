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
    
    public static final byte[] $classHash = new byte[] { 109, -8, 107, 38, -11,
    35, 97, -106, 123, -83, 86, 88, -24, -107, -86, -88, -43, -31, -66, -126,
    30, 72, -69, 100, -104, 60, 74, -32, 91, 76, -108, -49 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491929446000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
