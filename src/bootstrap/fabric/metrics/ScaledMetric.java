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
    
    public double computeWeakValue();
    
    public double computeVelocity();
    
    public double computeWeakVelocity();
    
    public double computeNoise();
    
    public double computeWeakNoise();
    
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
        
        public double computeWeakValue() {
            return this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).weakValue();
        }
        
        public double computeVelocity() {
            return this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).velocity();
        }
        
        public double computeWeakVelocity() {
            return this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).weakVelocity();
        }
        
        public double computeNoise() {
            return this.get$scalar() * this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).noise();
        }
        
        public double computeWeakNoise() {
            return this.get$scalar() * this.get$scalar() *
              ((fabric.metrics.Metric) this.get$terms().get(0)).weakNoise();
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
            return fabric.metrics.Metric._Impl.
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
                return fabric.metrics.Metric._Impl.
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
    
    public static final byte[] $classHash = new byte[] { -35, -109, -27, 70,
    -74, 37, 123, 54, -127, 74, -47, 95, 21, 40, -86, -7, 4, -114, 103, -46,
    -39, 10, 121, 48, -83, -10, 13, -101, 85, -6, -92, -12 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1502139730000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
