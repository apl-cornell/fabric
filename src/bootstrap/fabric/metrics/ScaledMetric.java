package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.util.Iterator;
import fabric.util.LinkedHashMap;
import fabric.util.Map;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;

/**
 * A {@link Metric} for the scaled value of a given metric.
 */
public interface ScaledMetric extends fabric.metrics.DerivedMetric {
    public double get$scalar();
    
    public double set$scalar(double val);
    
    public double postInc$scalar();
    
    public double postDec$scalar();
    
    /**
     * @param store
     *            the Store that holds this {@link Metric}
     * @param scalar
     *            The coefficient as a double
     * @param term
     *            The {@link Metric} this applies to
     */
    public fabric.metrics.ScaledMetric fabric$metrics$ScaledMetric$(
      double scalar, fabric.metrics.Metric term);
    
    public double computeValue();
    
    public double computeVelocity();
    
    public double computeNoise();
    
    public java.lang.String toString();
    
    public fabric.metrics.DerivedMetric times(double otherScalar);
    
    /**
     * {@inheritDoc}
     * <p>
     * {@link ScaledMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
    public fabric.metrics.DerivedMetric copyOn(fabric.worker.Store s);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
      fabric.metrics.contracts.Bound bound);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy extends fabric.metrics.DerivedMetric._Proxy
      implements fabric.metrics.ScaledMetric {
        public double get$scalar() {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).get$scalar();
        }
        
        public double set$scalar(double val) {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).set$scalar(
                                                                   val);
        }
        
        public double postInc$scalar() {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).postInc$scalar(
                                                                   );
        }
        
        public double postDec$scalar() {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).postDec$scalar(
                                                                   );
        }
        
        public fabric.metrics.ScaledMetric fabric$metrics$ScaledMetric$(
          double arg1, fabric.metrics.Metric arg2) {
            return ((fabric.metrics.ScaledMetric) fetch()).
              fabric$metrics$ScaledMetric$(arg1, arg2);
        }
        
        public _Proxy(ScaledMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.DerivedMetric._Impl
      implements fabric.metrics.ScaledMetric {
        public double get$scalar() { return this.scalar; }
        
        public double set$scalar(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.scalar = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$scalar() {
            double tmp = this.get$scalar();
            this.set$scalar((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$scalar() {
            double tmp = this.get$scalar();
            this.set$scalar((double) (tmp - 1));
            return tmp;
        }
        
        protected double scalar;
        
        private static fabric.lang.arrays.ObjectArray singleton(
          fabric.metrics.Metric term) {
            return fabric.lang.arrays.internal.Compat.
              convert(
                fabric.metrics.ScaledMetric._Static._Proxy.$instance.$getStore(
                                                                       ),
                fabric.metrics.ScaledMetric._Static._Proxy.$instance.
                    get$$updateLabel(),
                fabric.metrics.ScaledMetric._Static._Proxy.$instance.
                    get$$updateLabel().confPolicy(),
                new fabric.lang.Object[] { term });
        }
        
        /**
     * @param store
     *            the Store that holds this {@link Metric}
     * @param scalar
     *            The coefficient as a double
     * @param term
     *            The {@link Metric} this applies to
     */
        public fabric.metrics.ScaledMetric fabric$metrics$ScaledMetric$(
          double scalar, fabric.metrics.Metric term) {
            this.set$scalar((double) scalar);
            fabric$metrics$DerivedMetric$(
              fabric.metrics.ScaledMetric._Impl.singleton(term));
            initialize();
            return (fabric.metrics.ScaledMetric) this.$getProxy();
        }
        
        public double computeValue() {
            return this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).value();
        }
        
        public double computeVelocity() {
            return this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).velocity();
        }
        
        public double computeNoise() {
            return this.get$scalar() * this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).noise();
        }
        
        public java.lang.String toString() {
            return "(" +
            this.get$scalar() +
            "*" +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(
                                                    (fabric.metrics.Metric)
                                                      this.get$terms().get(
                                                                         0))) +
            ")@" +
            getStore();
        }
        
        public fabric.metrics.DerivedMetric times(double otherScalar) {
            final fabric.worker.Store s = $getStore();
            return fabric.metrics.AbstractMetric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.ScaledMetric)
                   new fabric.metrics.ScaledMetric._Impl(s).$getProxy()).
                    fabric$metrics$ScaledMetric$(otherScalar *
                                                     this.get$scalar(),
                                                 (fabric.metrics.Metric)
                                                   this.get$terms().get(0)));
        }
        
        /**
     * {@inheritDoc}
     * <p>
     * {@link ScaledMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            final fabric.worker.Store s = $getStore();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.ScaledMetric &&
                  other.$getStore().equals(s) &&
                  ((fabric.metrics.Metric)
                     ((fabric.metrics.ScaledMetric)
                        fabric.lang.Object._Proxy.$getProxy(other)).get$terms(
                                                                      ).get(0)).
                  equals((fabric.metrics.Metric) this.get$terms().get(0))) {
                fabric.metrics.ScaledMetric that =
                  (fabric.metrics.ScaledMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                return fabric.metrics.AbstractMetric._Impl.
                  findDerivedMetric(
                    s,
                    ((fabric.metrics.ScaledMetric)
                       new fabric.metrics.ScaledMetric._Impl(s).$getProxy()).
                        fabric$metrics$ScaledMetric$(
                          that.get$scalar() + this.get$scalar(),
                          (fabric.metrics.Metric) this.get$terms().get(0)));
            }
            return super.plus(other);
        }
        
        public fabric.metrics.DerivedMetric copyOn(fabric.worker.Store s) {
            return fabric.metrics.AbstractMetric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.ScaledMetric)
                   new fabric.metrics.ScaledMetric._Impl(s).$getProxy()).
                    fabric$metrics$ScaledMetric$(this.get$scalar(),
                                                 (fabric.metrics.Metric)
                                                   this.get$terms().get(0)));
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          fabric.metrics.contracts.Bound bound) {
            if (isSingleStore())
                return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                          new fabric.metrics.contracts.enforcement.DirectPolicy.
                            _Impl(this.$getStore()).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$DirectPolicy$(
                    (fabric.metrics.ScaledMetric) this.$getProxy(), bound);
            fabric.util.Map witnesses =
              ((fabric.util.LinkedHashMap)
                 new fabric.util.LinkedHashMap._Impl(
                   this.$getStore()).$getProxy()).fabric$util$LinkedHashMap$();
            long currentTime = java.lang.System.currentTimeMillis();
            double base = bound.value(currentTime);
            double rate = bound.get$rate();
            fabric.metrics.Metric m = term(0);
            rate = rate / this.get$scalar();
            base = base / this.get$scalar();
            if (this.get$scalar() < 0) {
                m = m.times(-1);
                base = -base;
                rate = -rate;
            }
            fabric.metrics.contracts.Bound witnessBound =
              ((fabric.metrics.contracts.Bound)
                 new fabric.metrics.contracts.Bound._Impl(
                   this.$getStore()).$getProxy()).
              fabric$metrics$contracts$Bound$(rate, base, currentTime);
            if (!witnesses.containsKey(m) ||
                  !((fabric.metrics.contracts.MetricContract)
                      fabric.lang.Object._Proxy.$getProxy(witnesses.get(m))).
                  getBound().implies(witnessBound)) {
                witnesses.put(m, m.getContract(witnessBound));
            }
            fabric.lang.arrays.ObjectArray
              finalWitnesses =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.
                      $getStore()).
                fabric$lang$arrays$ObjectArray$(
                  this.get$$updateLabel(), this.get$$updateLabel().confPolicy(),
                  fabric.metrics.contracts.MetricContract._Proxy.class,
                  witnesses.size()).$getProxy();
            int i = 0;
            for (fabric.util.Iterator iter = witnesses.values().iterator();
                 iter.hasNext(); ) {
                finalWitnesses.set(
                                 i++,
                                 (fabric.metrics.contracts.MetricContract)
                                   fabric.lang.Object._Proxy.$getProxy(
                                                               iter.next()));
            }
            final fabric.worker.Store bndStore = bound.getStore();
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(bndStore).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(
                finalWitnesses);
        }
        
        public int hashCode() {
            return fabric.util.Arrays._Impl.hashCode(this.get$terms()) * 32 +
              java.lang.Double.hashCode(this.get$scalar());
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.ScaledMetric) {
                fabric.metrics.ScaledMetric that =
                  (fabric.metrics.ScaledMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                return this.get$scalar() ==
                  that.get$scalar() &&
                  fabric.util.Arrays._Impl.deepEquals(this.get$terms(),
                                                      that.get$terms()) &&
                  this.$getStore().equals(that.$getStore());
            }
            return false;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.ScaledMetric._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeDouble(this.scalar);
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
            this.scalar = in.readDouble();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.ScaledMetric._Impl src =
              (fabric.metrics.ScaledMetric._Impl) other;
            this.scalar = src.scalar;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.ScaledMetric._Static {
            public _Proxy(fabric.metrics.ScaledMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.ScaledMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  ScaledMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.ScaledMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.ScaledMetric._Static._Impl.class);
                $instance = (fabric.metrics.ScaledMetric._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.ScaledMetric._Static {
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
                return new fabric.metrics.ScaledMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -27, 99, 40, -42, 86,
    -110, 45, -23, 78, -48, 5, -69, -11, -100, 81, 79, -122, 74, -94, 83, 81,
    -123, 110, 122, -41, -81, 46, -113, 80, -82, 92, -32 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1496245529000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
