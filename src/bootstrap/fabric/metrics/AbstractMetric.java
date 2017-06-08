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
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.worker.Store;

/**
 * Represents an observable quantity that changes over time. Internally, this
 * class estimates the velocity of the observed quantity, and the interval
 * between updates. Instances of this class can be observed by
 * {@link LinearMetric}s and {@link MetricContract}s.
 */
public interface AbstractMetric
  extends fabric.metrics.Metric, fabric.metrics.util.AbstractSubject {
    public fabric.metrics.AbstractMetric fabric$metrics$AbstractMetric$();
    
    public fabric.util.List get$contracts();
    
    public fabric.util.List set$contracts(fabric.util.List val);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(fabric.metrics.contracts.Bound bound);
    
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
    
    public fabric.metrics.contracts.MetricContract getContract(
      fabric.metrics.contracts.Bound bound);
    
    public fabric.metrics.contracts.MetricContract getContract(double rate,
                                                               double base,
                                                               long time);
    
    public fabric.metrics.contracts.MetricContract getContract(double rate, double base);
    
    /**
   * @param contract
   *        a {@link MetricContract} to store with this {@link Metric}
   * @throws IllegalArgumentException
   *         if contract isn't defined on this {@link Metric}
   */
    public void addContract(fabric.metrics.contracts.MetricContract contract);
    
    public fabric.metrics.DerivedMetric times(double scalar);
    
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
    public fabric.metrics.Metric min(fabric.metrics.Metric other);
    
    public fabric.metrics.Metric max(fabric.metrics.Metric other);
    
    /**
   * Allows for sorting {@link Metric}s to help with normalizing
   * {@link DerivedMetric}s.
   */
    public int compareTo(java.lang.Object that);
    
    public void startTracking(fabric.metrics.util.Observer obs);
    
    public void stopTracking(fabric.metrics.util.Observer obs);
    
    public static class _Proxy
    extends fabric.metrics.util.AbstractSubject._Proxy
      implements fabric.metrics.AbstractMetric {
        public fabric.util.List get$contracts() {
            return ((fabric.metrics.AbstractMetric._Impl) fetch()).
              get$contracts();
        }
        
        public fabric.util.List set$contracts(fabric.util.List val) {
            return ((fabric.metrics.AbstractMetric._Impl) fetch()).
              set$contracts(val);
        }
        
        public fabric.metrics.AbstractMetric fabric$metrics$AbstractMetric$() {
            return ((fabric.metrics.AbstractMetric) fetch()).
              fabric$metrics$AbstractMetric$();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          fabric.metrics.contracts.Bound arg1) {
            return ((fabric.metrics.AbstractMetric) fetch()).policy(arg1);
        }
        
        public fabric.metrics.contracts.MetricContract createContract(
          fabric.metrics.contracts.Bound arg1) {
            return ((fabric.metrics.AbstractMetric) fetch()).createContract(
                                                               arg1);
        }
        
        public fabric.util.List getContracts(long arg1) {
            return ((fabric.metrics.AbstractMetric) fetch()).getContracts(arg1);
        }
        
        public fabric.metrics.contracts.MetricContract getContract(
          fabric.metrics.contracts.Bound arg1) {
            return ((fabric.metrics.AbstractMetric) fetch()).getContract(arg1);
        }
        
        public fabric.metrics.contracts.MetricContract getContract(double arg1,
                                                                   double arg2,
                                                                   long arg3) {
            return ((fabric.metrics.AbstractMetric) fetch()).getContract(arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public fabric.metrics.contracts.MetricContract getContract(
          double arg1, double arg2) {
            return ((fabric.metrics.AbstractMetric) fetch()).getContract(arg1,
                                                                         arg2);
        }
        
        public void addContract(fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.AbstractMetric) fetch()).addContract(arg1);
        }
        
        public fabric.metrics.DerivedMetric times(double arg1) {
            return ((fabric.metrics.AbstractMetric) fetch()).times(arg1);
        }
        
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.AbstractMetric) fetch()).plus(arg1);
        }
        
        public fabric.metrics.Metric min(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.AbstractMetric) fetch()).min(arg1);
        }
        
        public fabric.metrics.Metric max(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.AbstractMetric) fetch()).max(arg1);
        }
        
        public int compareTo(java.lang.Object arg1) {
            return ((fabric.metrics.AbstractMetric) fetch()).compareTo(arg1);
        }
        
        public static fabric.metrics.DerivedMetric findDerivedMetric(
          fabric.worker.Store arg1, fabric.metrics.DerivedMetric arg2) {
            return fabric.metrics.AbstractMetric._Impl.findDerivedMetric(arg1,
                                                                         arg2);
        }
        
        public static fabric.metrics.Metric scaleAtStore(
          fabric.worker.Store arg1, double arg2, fabric.metrics.Metric arg3) {
            return fabric.metrics.AbstractMetric._Impl.scaleAtStore(arg1, arg2,
                                                                    arg3);
        }
        
        public static fabric.metrics.Metric addAtStore(
          fabric.worker.Store arg1, fabric.lang.arrays.ObjectArray arg2) {
            return fabric.metrics.AbstractMetric._Impl.addAtStore(arg1, arg2);
        }
        
        public static fabric.metrics.Metric minAtStore(
          fabric.worker.Store arg1, fabric.lang.arrays.ObjectArray arg2) {
            return fabric.metrics.AbstractMetric._Impl.minAtStore(arg1, arg2);
        }
        
        public static fabric.metrics.Metric maxAtStore(
          fabric.worker.Store arg1, fabric.lang.arrays.ObjectArray arg2) {
            return fabric.metrics.AbstractMetric._Impl.maxAtStore(arg1, arg2);
        }
        
        public void startTracking(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.AbstractMetric) fetch()).startTracking(arg1);
        }
        
        public void stopTracking(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.AbstractMetric) fetch()).stopTracking(arg1);
        }
        
        public double value() {
            return ((fabric.metrics.AbstractMetric) fetch()).value();
        }
        
        public double velocity() {
            return ((fabric.metrics.AbstractMetric) fetch()).velocity();
        }
        
        public double noise() {
            return ((fabric.metrics.AbstractMetric) fetch()).noise();
        }
        
        public boolean isSingleStore() {
            return ((fabric.metrics.AbstractMetric) fetch()).isSingleStore();
        }
        
        public _Proxy(AbstractMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.metrics.util.AbstractSubject._Impl
      implements fabric.metrics.AbstractMetric {
        public fabric.metrics.AbstractMetric fabric$metrics$AbstractMetric$() {
            this.set$contracts(
                   ((fabric.util.ArrayList)
                      new fabric.util.ArrayList._Impl(
                        this.$getStore()).$getProxy()).fabric$util$ArrayList$(
                                                         ));
            fabric$metrics$util$AbstractSubject$();
            return (fabric.metrics.AbstractMetric) this.$getProxy();
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
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          fabric.metrics.contracts.Bound bound) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                      new fabric.metrics.contracts.enforcement.DirectPolicy.
                        _Impl(this.$getStore()).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$DirectPolicy$(
                (fabric.metrics.AbstractMetric) this.$getProxy(), bound);
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
              fabric$metrics$contracts$MetricContract$(
                (fabric.metrics.AbstractMetric) this.$getProxy(), bound);
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
                if (!c.isActive()) {
                    stopTracking(c);
                    this.get$contracts().remove(i--);
                }
            }
            return this.get$contracts();
        }
        
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
                      c.enforces((fabric.metrics.AbstractMetric)
                                   this.$getProxy(), bound)) {
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
        
        public fabric.metrics.contracts.MetricContract getContract(double rate,
                                                                   double base,
                                                                   long time) {
            return getContract(
                     ((fabric.metrics.contracts.Bound)
                        new fabric.metrics.contracts.Bound._Impl(
                          this.$getStore()).$getProxy()).
                         fabric$metrics$contracts$Bound$(rate, base, time));
        }
        
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
            if (!contract.getMetric().equals((fabric.metrics.AbstractMetric)
                                               this.$getProxy()))
                throw new java.lang.IllegalArgumentException(
                        "Adding a contract for a different metric!");
            if (!this.get$contracts().contains(contract))
                this.get$contracts().add(contract);
        }
        
        public fabric.metrics.DerivedMetric times(double scalar) {
            final fabric.worker.Store s = $getStore();
            return fabric.metrics.AbstractMetric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.ScaledMetric)
                   new fabric.metrics.ScaledMetric._Impl(s).$getProxy()).
                    fabric$metrics$ScaledMetric$(scalar,
                                                 (fabric.metrics.AbstractMetric)
                                                   this.$getProxy()));
        }
        
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.SumMetric)
                return other.plus((fabric.metrics.AbstractMetric)
                                    this.$getProxy());
            final fabric.worker.Store s = $getStore();
            return fabric.metrics.AbstractMetric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.SumMetric)
                   new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                    fabric$metrics$SumMetric$(
                      fabric.lang.arrays.internal.Compat.
                          convert(
                            this.$getStore(),
                            this.get$$updateLabel(),
                            this.get$$updateLabel().confPolicy(
                                                      ), new fabric.lang.Object[] { (fabric.metrics.AbstractMetric) this.$getProxy(), other })));
        }
        
        public fabric.metrics.Metric min(fabric.metrics.Metric other) {
            if (this.equals(other))
                return (fabric.metrics.AbstractMetric) this.$getProxy();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.MinMetric)
                return other.min((fabric.metrics.AbstractMetric)
                                   this.$getProxy());
            final fabric.worker.Store s = $getStore();
            return fabric.metrics.AbstractMetric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.MinMetric)
                   new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                    fabric$metrics$MinMetric$(
                      fabric.lang.arrays.internal.Compat.
                          convert(
                            this.$getStore(),
                            this.get$$updateLabel(),
                            this.get$$updateLabel().confPolicy(
                                                      ), new fabric.lang.Object[] { (fabric.metrics.AbstractMetric) this.$getProxy(), other })));
        }
        
        public fabric.metrics.Metric max(fabric.metrics.Metric other) {
            return this.times(-1).min(other.times(-1)).times(-1);
        }
        
        /**
   * Allows for sorting {@link Metric}s to help with normalizing
   * {@link DerivedMetric}s.
   */
        public int compareTo(java.lang.Object that) {
            if (!(fabric.lang.Object._Proxy.
                    $getProxy(that) instanceof fabric.metrics.Metric)) return 0;
            fabric.metrics.Metric
              other =
              (fabric.metrics.Metric)
                fabric.lang.Object._Proxy.
                $getProxy(fabric.lang.WrappedJavaInlineable.$wrap(that));
            int thisHash = hashCode();
            int thatHash = other.hashCode();
            if (thisHash == thatHash) {
                if (other.equals((fabric.metrics.AbstractMetric)
                                   this.$getProxy())) {
                    return 0;
                } else {
                    return toString().compareTo(other.toString());
                }
            }
            return thisHash - thatHash;
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
        public static fabric.metrics.DerivedMetric findDerivedMetric(
          final fabric.worker.Store s, fabric.metrics.DerivedMetric m) {
            fabric.metrics.DerivedMetric orig =
              (fabric.metrics.DerivedMetric)
                fabric.lang.Object._Proxy.$getProxy(s.derivedMap().get(m));
            if (fabric.lang.Object._Proxy.idEquals(orig, null)) {
                s.derivedMap().put(m, m);
                return m;
            } else {
                m.cleanup();
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
                return fabric.metrics.AbstractMetric._Impl.findDerivedMetric(
                                                             s, term.times(a));
            return fabric.metrics.AbstractMetric._Impl.
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
                return fabric.metrics.AbstractMetric._Impl.
                  scaleAtStore(s, 1.0, (fabric.metrics.Metric) terms.get(0));
            return fabric.metrics.AbstractMetric._Impl.
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
                return fabric.metrics.AbstractMetric._Impl.
                  scaleAtStore(s, 1.0, (fabric.metrics.Metric) terms.get(0));
            return fabric.metrics.AbstractMetric._Impl.
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
                return fabric.metrics.AbstractMetric._Impl.
                  scaleAtStore(s, 1.0, (fabric.metrics.Metric) terms.get(0));
            for (int i = 0; i < terms.get$length(); i++) {
                terms.set(i, ((fabric.metrics.Metric) terms.get(i)).times(-1));
            }
            return fabric.metrics.AbstractMetric._Impl.
              findDerivedMetric(
                s,
                fabric.metrics.AbstractMetric._Impl.minAtStore(s, terms).times(
                                                                           -1));
        }
        
        public void startTracking(fabric.metrics.util.Observer obs) {
            addObserver(obs);
        }
        
        public void stopTracking(fabric.metrics.util.Observer obs) {
            removeObserver(obs);
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.AbstractMetric._Proxy(this);
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
            fabric.metrics.AbstractMetric._Impl src =
              (fabric.metrics.AbstractMetric._Impl) other;
            this.contracts = src.contracts;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.AbstractMetric._Static {
            public _Proxy(fabric.metrics.AbstractMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.AbstractMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  AbstractMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.AbstractMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.AbstractMetric._Static._Impl.class);
                $instance = (fabric.metrics.AbstractMetric._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.AbstractMetric._Static {
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
                return new fabric.metrics.AbstractMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -101, 56, 6, -70, 43,
    -29, -47, 67, 103, -91, -49, -93, 106, 76, -71, -98, -32, -87, 51, -99, 99,
    -30, -128, -120, -101, -28, -121, -28, -115, -8, -57, -1 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1496782676000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
