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
 * A {@link DerivedMetric} for the scaled value of a given metric.
 */
public interface ScaledMetric extends fabric.metrics.DerivedMetric {
    public double get$scalar();
    
    public double set$scalar(double val);
    
    public double postInc$scalar();
    
    public double postDec$scalar();
    
    /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
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
     *            the {@link Store} that holds this {@link Metric}
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
            fabric.metrics.contracts.MetricContract witness = null;
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
            witness = m.getContract(witnessBound);
            final fabric.worker.Store bndStore = bound.getStore();
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(bndStore).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(
                fabric.lang.arrays.internal.Compat.
                    convert(this.$getStore(), this.get$$updateLabel(),
                            this.get$$updateLabel().confPolicy(),
                            new fabric.lang.Object[] { witness }));
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
    
    public static final byte[] $classHash = new byte[] { -97, -9, 68, 104, 1,
    22, -71, -15, 97, 95, -120, 107, -99, 47, -56, 60, 98, 61, -55, -21, 80, 70,
    -12, -33, 6, 56, -32, 105, 83, -34, 67, -4 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500579684000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
