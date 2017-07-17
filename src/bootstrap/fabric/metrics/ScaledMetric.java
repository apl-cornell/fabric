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
    
    public static final byte[] $classHash = new byte[] { -103, -24, -54, 113,
    126, 63, 44, 35, 99, -96, 103, -20, -126, 27, 72, 21, 44, -109, -34, 119,
    -60, 70, 23, -9, -87, -101, 9, -11, 107, -125, -79, -22 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500325652000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
