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
              fabric.lang.arrays.internal.Compat.
                  convert(this.$getStore(), this.get$$updateLabel(),
                          this.get$$updateLabel().confPolicy(),
                          new fabric.lang.Object[] { term }));
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
    
    public static final byte[] $classHash = new byte[] { 94, 92, 40, -45, -107,
    58, 47, -14, 36, -13, -102, -96, -97, -76, -116, -69, 62, -87, 75, 98, -47,
    -59, -20, 82, -37, -15, -45, 61, 92, -35, 117, -126 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1495741942000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
