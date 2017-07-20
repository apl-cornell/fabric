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
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.worker.Store;

/**
 * Abstract class with base implementation of some {@link Metric} methods.
 */
public interface AbstractMetric
  extends fabric.metrics.Metric, fabric.metrics.util.AbstractSubject {
    /**
   * @param store
   *            the Store that holds this {@link Metric}
   */
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
    
    public int compareTo(java.lang.Object that);
    
    /**
   * {@inheritDoc}
   *
   * If the {@link Observer} is a {@link MetricContract} and is now
   * {@link Contract#stale()}, this clears it out from the Metric's stored
   * contract set.
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
        /**
   * @param store
   *            the Store that holds this {@link Metric}
   */
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
   * If the {@link Observer} is a {@link MetricContract} and is now
   * {@link Contract#stale()}, this clears it out from the Metric's stored
   * contract set.
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
   *            the {@link Store} we're looking for the given {@link Metric}
   *            on
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
   * @param s
   *            the {@link Store} the returned {@link DerivedMetric} will be
   *            stored at
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
   * @param s
   *            the {@link Store} the returned {@link DerivedMetric} will be
   *            stored at
   * @param terms
   *            the {@link Metric}s to sum
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
   * @param s
   *            the {@link Store} the returned {@link Metric} will be stored
   *            at
   * @param terms
   *        the {@link Metric}s to take the min of
   * @return the locally tracked {@link Metric} for the minimum of the terms.
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
   * @param s
   *            the {@link Store} the returned {@link Metric} will be stored
   *            at
   * @param terms
   *        the {@link Metric}s to take the max of
   * @return the locally tracked {@link Metric} for the maximum of the terms.
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
    
    public static final byte[] $classHash = new byte[] { -105, 70, -127, 85, 1,
    82, -65, -72, 11, -56, 2, 9, -87, 56, -120, 98, 103, -112, 51, -107, -32,
    -115, -111, 120, -95, 84, -87, -101, 67, 89, 78, 102 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500579664000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
