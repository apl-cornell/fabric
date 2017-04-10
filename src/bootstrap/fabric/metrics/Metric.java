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
                if (!c.valid(time) || !c.isObserved() || !c.isActive())
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
    
    public static final byte[] $classHash = new byte[] { 12, -128, 15, -84, 53,
    92, 26, -25, -105, -105, -60, 77, 46, -50, -3, -126, 45, 47, 60, 63, 92,
    -63, 56, 63, -85, -88, -72, 36, 127, -57, -13, 121 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491862192000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
