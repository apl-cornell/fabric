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
    public fabric.metrics.DerivedMetric times(double scalar);
    
    /**
   * @param other
   *        another {@link Metric} to add with this {@link Metric},
   *        element wise.
   * @return a {@link LinearMetric} that tracks the value of the sum of other
   *       and this
   */
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
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
   * @param scalar
   *        a double scalar to scale this metric by
   * @return A {@link LinearMetric} that tracks the scaled value of this
   *       {@link Metric}
   */
        public fabric.metrics.DerivedMetric times(double scalar) {
            final fabric.worker.Store thisStore = $getStore();
            return ((fabric.metrics.LinearMetric)
                      new fabric.metrics.LinearMetric._Impl(thisStore).
                      $getProxy()).
              fabric$metrics$LinearMetric$(
                fabric.metrics.util.Matrix._Impl.constant(1, 1, scalar),
                fabric.lang.arrays.internal.Compat.
                    convert(this.$getStore(),
                            this.get$$updateLabel(),
                            this.get$$updateLabel().confPolicy(),
                            new fabric.lang.Object[] { (fabric.metrics.Metric)
                                                         this.$getProxy() }));
        }
        
        /**
   * @param other
   *        another {@link Metric} to add with this {@link Metric},
   *        element wise.
   * @return a {@link LinearMetric} that tracks the value of the sum of other
   *       and this
   */
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.DerivedMetric)
                return other.plus((fabric.metrics.Metric) this.$getProxy());
            final fabric.worker.Store thisStore = $getStore();
            return ((fabric.metrics.LinearMetric)
                      new fabric.metrics.LinearMetric._Impl(thisStore).
                      $getProxy()).
              fabric$metrics$LinearMetric$(
                fabric.metrics.util.Matrix._Impl.constant(1, 2, 1),
                fabric.lang.arrays.internal.Compat.
                    convert(this.$getStore(),
                            this.get$$updateLabel(),
                            this.get$$updateLabel().confPolicy(),
                            new fabric.lang.Object[] { (fabric.metrics.Metric)
                                                         this.$getProxy(),
                              other }));
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
            return ((fabric.metrics.LinearMetric)
                      new fabric.metrics.LinearMetric._Impl(thisStore).
                      $getProxy()).
              fabric$metrics$LinearMetric$(
                fabric.metrics.util.Matrix._Impl.identity(2),
                fabric.lang.arrays.internal.Compat.
                    convert(this.$getStore(),
                            this.get$$updateLabel(),
                            this.get$$updateLabel().confPolicy(),
                            new fabric.lang.Object[] { (fabric.metrics.Metric)
                                                         this.$getProxy(),
                              other }));
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
    
    public static final byte[] $classHash = new byte[] { -43, -21, 107, -21, 28,
    -4, 23, 83, -124, -113, 65, 0, -81, 88, -102, 18, 50, 38, -1, -18, 77, -33,
    -14, -124, 87, -5, 70, -117, -54, 43, 38, 61 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491845597000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1ZDXBUVxW+u0k2PwTyAwQIIQSIdPjbFXR0aMSWLFACi0QSWgzS+Pa9u8kjb99b3rsbliLaKgraGexYSsEpdHSolZJCp2N1sGbETltb8ad1KrXjTxnHajsRnfpTO2MrnnPv3X27L7vL7gyZufe83HvPueec+51z7ns7dpVUOTZZGFOiuhFk+xLUCW5Qoj2RXsV2qBY2FMfph9FBdUplz7E3H9Xa/cQfIfWqYlqmrirGoOkwMi2yWxlVQiZloe3berp2kloVGTcqzjAj/p3dKZt0JCxj35BhMbnJJPkPLAsdffDOxicrSMMAadDNPqYwXQ1bJqMpNkDq4zQepbazVtOoNkCaTEq1PmrriqHfBQstc4A0O/qQqbCkTZ1t1LGMUVzY7CQT1OZ7pgdRfQvUtpMqs2xQv1Gon2S6EYroDuuKkEBMp4bm7CGfI5URUhUzlCFY2BJJWxHiEkMbcByW1+mgph1TVJpmqRzRTY2R+V6OjMWdm2EBsFbHKRu2MltVmgoMkGahkqGYQ6E+ZuvmECytspKwCyOtBYXCopqEoo4oQ3SQkdnedb1iClbVcrcgCyMzvcu4JDizVs+ZZZ3W1U987Mh+c6PpJz7QWaOqgfrXAFO7h2kbjVGbmioVjPVLI8eUlvHDfkJg8UzPYrHm+599+9bl7RdfEGvm5lmzNbqbqmxQPR2d9nJbeMnqClSjJmE5OkIhx3J+qr1ypiuVALS3ZCTiZDA9eXHb85+6+zE64Sd1PSSgWkYyDqhqUq14QjeofRs1qa0wqvWQWmpqYT7fQ6rhOaKbVIxujcUcynpIpcGHAhb/H1wUAxHoomp41s2YlX5OKGyYP6cShJBGaMQH7XeEzPse0BmE+A1G1oWGrTgNRY0k3QvwDkGjiq0OhyBubV0NObYaspMm02GRHAIUAXFCWzgNwv6JGyQnhfo27vX5wJXzVUujUcWBc5EY6e41IAw2WoZG7UHVODLeQ6aPn+A4qUVsO4BP7gkfnG2bNytk8x5Ndq9/+9zgJYEx5JWOAsgK5YJSuaBQDvSpx7AJQiIKQiIa86WC4VM9Zzk6Ag4Po4yIehBxc8JQWMyy4yni83F7ZnB+Dgs41BFIFpAP6pf07dr0mcMLKwCPib2VeESwtNMbHW5O6YEnBSA/qDYcevOd88cOWG6cMNI5KXwnc2L4LfQ6x7ZUqkF6c8Uv7VCeGhw/0OnH1FELWY0pgDtIEe3ePXLCsCud0tAbVREyBX2gGDiVzkN1bNi29roj/NCnYdcszh+d5VGQZ8M1fYmTv/nFWx/idSKdOBuyMmwfZV1ZwYrCGnhYNrm+77cphXW/P957/wNXD+3kjocVi/Jt2Il9GIJUgei07C+9sOe11/9w+hW/e1iMBBLJqKGrKW5L0zX480H7HzaMOBxACnk3LKO9IxPuCdx5sasbBL4ByQdUdzq3m3FL02O6EjUoIuW9hg+sfOqvRxrFcRswIpxnk+XXF+COz+kmd1+68z/tXIxPxcLj+s9dJrLZdFfyWttW9qEeqXt+Ne/ET5STgHzIRY5+F+XphXB/EH6Aq7gvVvB+pWfuw9gtFN5qywDem9k3YIl0sTgQGnuoNfzxCRHqGSyijAV5Qv12JStMVj0W/7d/YeA5P6keII28Oismu12BPAUwGID66oTlYIRMzZnPrZWiMHRlYq3NGwdZ23qjwE0x8Iyr8blOAF8ABxxRh07qhjaXkIodknbg7PQE9jNSPsIfbuYsi3i/GLslaTBWJ2x9FJCVygj1o9BaKWy+pLOzhDLhUFtRmcO5ZkJMyRzITx68hMOtPDRT+bf24+NSRmqUqMNFuQrwvwZZb0Yk/XSWAjlQkAqIXIluD8rwAxDzyTmgL6ZXw4IbYioF2JlX6CrBr0Gnv3D0lLb1kZWi4Dfnluf1ZjL++OX3fxY8fuXFPKWgllmJFQYdpUaWjk2w5YJJd9ot/Kblou7KxLzV4ZE3hsS28z0qelef2TL24m2L1a/7SUUGXpOud7lMXbmgqrMp3E7N/hxodWQOoR4PYRm0FnD+a5L+IBtaIvEWwlVtwrYYoJ9qHmRNkbIuSPqE92DzZ4GdReZ2YdcPiUmAsFMW4k5RiDtdRT+ZUaUZmTugtQO4xyU9X6J5ArvYrfcY1yQlnZP04dKMo0Xm+CUB7s9Vo5hg8mS+XluPQ/EalXdaevjoV68FjxwV6BQX/0WT7t7ZPOLyzzebit0yjJEFxXbhHBv+cv7A0985cMgvFd0ClU2zkumw8/h6EbQFhFQul3TuDfE1SmqVdEppvk4WmduLnQVJCQLYUnW2D/8fKQSdm2DTlyR95oaYg5J+LOmTpZnz+SJz92C3D6BjWrpDC9qyBNoKQqr2S6rdEFtQkippf2m2fKXI3L3YfZGRqbrTBzdng/bB9UogTZegRQJpsDpqWQZVTI+pWNHIR6F9hJDAcUl3l5rQuKkeK2ukEF1StbCVfuGwdLFq97wxZMppsNtKmtz/rflMqzQsc4jreayIq05idx8jTXDP4hm4H96g+q2NOq/KJzx+mYl8t0ALg0UPSWqXA4Ed+SAwQ0raI+lgYef4XBc/yDd7pIhxj2L3MCPTVJvCtSWc9pz07E0FPSuKQXq962LXE7zkdUGLAFpelvRbBTyB3R2TKxuyfFPS4yWZfIJLfaKIyTwTnIWXxCHKcuxdmw/iH4S2C4zZJumyIgbcPxnQyLJU0s4yzuxCEQOexu67jEzJMgCHzuTTfyPyEtLYImjDq+XpjyyXJX2psP4VXL0KnhMznTiKZ4pY8ix24yVasgbalyHHMklvLc8SZLlF0tXXTS2uJVz0pSJG/By750s0YhW0+wiZ/pakF8szAll+JOmFkuB0hkt9pYj+l7H7JeivaFpOPHiz5aila/lswmvtSch8ByTdWZ5NyDIgaZHKlmWTOJMrRWz6I3a/hVKNX7syb1Vtnmy2jsLLGtVEKsuXwrh5WM/PEjLLkDRSnnnIslnS9SWZ18ilThQx7yp2f4YjSRji/vWnfIrDLY78kJA5z0n6eHmKI8uYpN8uQ/F/FVH8Hez+zkhFXDddDfLoDRfBtoOSGuXpjSwjktIy9H6/iN7XsHsX9VZSBfVeDQ1e7ebtl3RzeXojyyZJ111X7zSmG90XdfF9BMc34Xa+QGGDfHXY+finB3y7h/tMvpCv0E2WgvcQESH4QWpunm/C8jcJNfwsPf3G5uUzC3wPnj3pVyLJd+5UQ82sU9tf5R86M7831EZITSxpGNkfbrKeAwmbxnTu1lrxGSfBDYPyMC03zBn/GQaf0Cxfo1gHrguIdfhfC3d1q5sDYHquJ1uIbzHJjJNb+batSRt/BRv756x3AzX9V/iXSXByx68nRiba3pvVd/Bra8n5Hd9oXrX42t+2vP6Pg3f8d8O9P122eM3/Aasdf1+dGwAA";
}
