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
    
    public static final byte[] $classHash = new byte[] { -87, -57, -98, 68, 57,
    -63, 123, -75, 121, 45, -96, 106, 93, -111, 125, 16, 3, 115, -65, 6, -103,
    101, 120, 46, -110, -34, -14, 29, -123, 107, 36, 81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1495741640000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
