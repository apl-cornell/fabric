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
    
    public fabric.metrics.contracts.MetricContract getContract(double rate,
                                                               double base);
    
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
    
    /**
   * {@inheritDoc}
   *
   * If the observer is a MetricContract and is now stale, this clears it out
   * from the Metric's stored contract set.
   */
    public void removeObserver(fabric.metrics.util.Observer obs);
    
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
                if (c.stale(time)) {
                    removeObserver(c);
                    i--;
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
                if (c.stale()) {
                    removeObserver(c);
                    i--;
                } else if (fabric.lang.Object._Proxy.idEquals(mc, null) &&
                             c.isActivated() &&
                             c.enforces((fabric.metrics.AbstractMetric)
                                          this.$getProxy(), bound)) {
                    mc = c;
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
            if (this.compareTo(
                       (java.lang.Object)
                         fabric.lang.WrappedJavaInlineable.$unwrap(other)) >
                  0)
                return fabric.metrics.AbstractMetric._Impl.
                  findDerivedMetric(
                    s,
                    ((fabric.metrics.MinMetric)
                       new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                        fabric$metrics$MinMetric$(
                          fabric.lang.arrays.internal.Compat.
                              convert(this.$getStore(),
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      new fabric.lang.Object[] { other,
                                        (fabric.metrics.AbstractMetric)
                                          this.$getProxy() })));
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
   * Utility for removing a contract if it's no longer observing and is now
   * stale.
   *
   * @param contract
   *            a {@link MetricContract} to stop storing with this
   *            {@link Metric} (if it is now invalid).
   * @throws IllegalArgumentException
   *             if contract isn't defined on this {@link Metric}
   */
        private void clearContract(
          fabric.metrics.contracts.MetricContract contract) {
            if (!contract.getMetric().equals((fabric.metrics.AbstractMetric)
                                               this.$getProxy()))
                throw new java.lang.IllegalArgumentException(
                        "clearing a contract for a different metric!");
            if (contract.stale()) this.get$contracts().remove(contract);
        }
        
        /**
   * {@inheritDoc}
   *
   * If the observer is a MetricContract and is now stale, this clears it out
   * from the Metric's stored contract set.
   */
        public void removeObserver(fabric.metrics.util.Observer obs) {
            super.removeObserver(obs);
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        obs)) instanceof fabric.metrics.contracts.MetricContract) {
                fabric.metrics.contracts.MetricContract mc =
                  (fabric.metrics.contracts.MetricContract)
                    fabric.lang.Object._Proxy.$getProxy(obs);
                ((fabric.metrics.AbstractMetric._Impl) this.fetch()).
                  clearContract(mc);
            }
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
    
    public static final byte[] $classHash = new byte[] { -18, -113, -33, 81, 60,
    -103, 10, -123, -74, -61, 107, 44, 14, -82, -113, 118, 85, -46, -79, 55,
    -50, -29, -80, 27, 52, -2, -123, -90, 114, -19, 15, -37 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500326717000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
