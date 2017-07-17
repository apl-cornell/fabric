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
import fabric.util.Set;
import fabric.util.TreeSet;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;

/**
 * A {@link Metric} for the minimum of a group of other metrics.
 */
public interface MinMetric extends fabric.metrics.DerivedMetric {
    /**
     * @param store
     *            the Store that holds this {@link Metric}
     * @param terms
     *            The {@link Metric}s this applies to
     */
    public fabric.metrics.MinMetric fabric$metrics$MinMetric$(
      fabric.lang.arrays.ObjectArray terms);
    
    public double computeValue();
    
    public double computeVelocity();
    
    public double computeNoise();
    
    public java.lang.String toString();
    
    public fabric.metrics.DerivedMetric times(double scalar);
    
    /**
     * {@inheritDoc}
     * <p>
     * {@link MinMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
    /**
     * {@inheritDoc}
     * <p>
     * {@link MinMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
    public fabric.metrics.Metric min(fabric.metrics.Metric other);
    
    public fabric.metrics.DerivedMetric copyOn(fabric.worker.Store s);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
      fabric.metrics.contracts.Bound bound);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy extends fabric.metrics.DerivedMetric._Proxy
      implements fabric.metrics.MinMetric {
        public fabric.metrics.MinMetric fabric$metrics$MinMetric$(
          fabric.lang.arrays.ObjectArray arg1) {
            return ((fabric.metrics.MinMetric) fetch()).
              fabric$metrics$MinMetric$(arg1);
        }
        
        public _Proxy(MinMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.DerivedMetric._Impl
      implements fabric.metrics.MinMetric {
        /**
     * @param store
     *            the Store that holds this {@link Metric}
     * @param terms
     *            The {@link Metric}s this applies to
     */
        public fabric.metrics.MinMetric fabric$metrics$MinMetric$(
          fabric.lang.arrays.ObjectArray terms) {
            fabric$metrics$DerivedMetric$(terms);
            initialize();
            return (fabric.metrics.MinMetric) this.$getProxy();
        }
        
        public double computeValue() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.min(result,
                                     ((fabric.metrics.Metric)
                                        this.get$terms().get(i)).value());
            }
            return result;
        }
        
        public double computeVelocity() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.min(result,
                                     ((fabric.metrics.Metric)
                                        this.get$terms().get(i)).velocity());
            }
            return result;
        }
        
        public double computeNoise() {
            double noise = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                noise = java.lang.Math.max(noise,
                                           ((fabric.metrics.Metric)
                                              this.get$terms().get(i)).noise());
            }
            return noise;
        }
        
        public java.lang.String toString() {
            java.lang.String str = "min(";
            boolean nonEmpty = false;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                if (nonEmpty) { str += ", "; }
                nonEmpty = true;
                str += (fabric.metrics.Metric) this.get$terms().get(i);
            }
            return str + ")@" + $getStore();
        }
        
        public fabric.metrics.DerivedMetric times(double scalar) {
            fabric.lang.arrays.ObjectArray newTerms =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      fabric.metrics.Metric._Proxy.class,
                                      this.get$terms().get$length()).$getProxy(
                                                                       );
            fabric.util.Arrays._Impl.arraycopy(this.get$terms(), 0, newTerms, 0,
                                               this.get$terms().get$length());
            for (int i = 0; i < newTerms.get$length(); i++) {
                newTerms.set(i,
                             ((fabric.metrics.Metric)
                                newTerms.get(i)).times(scalar));
            }
            final fabric.worker.Store s = $getStore();
            return fabric.metrics.AbstractMetric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.MinMetric)
                   new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                    fabric$metrics$MinMetric$(this.get$terms()));
        }
        
        /**
     * {@inheritDoc}
     * <p>
     * {@link MinMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            fabric.lang.arrays.ObjectArray newTerms =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      fabric.metrics.Metric._Proxy.class,
                                      this.get$terms().get$length()).$getProxy(
                                                                       );
            fabric.util.Arrays._Impl.arraycopy(this.get$terms(), 0, newTerms, 0,
                                               this.get$terms().get$length());
            for (int i = 0; i < newTerms.get$length(); i++) {
                newTerms.set(i,
                             other.plus((fabric.metrics.Metric)
                                          newTerms.get(i)));
            }
            final fabric.worker.Store s = $getStore();
            return fabric.metrics.AbstractMetric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.MinMetric)
                   new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                    fabric$metrics$MinMetric$(this.get$terms()));
        }
        
        /**
     * {@inheritDoc}
     * <p>
     * {@link MinMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
        public fabric.metrics.Metric min(fabric.metrics.Metric other) {
            final fabric.worker.Store s = $getStore();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.MinMetric &&
                  other.$getStore().equals(s)) {
                fabric.metrics.MinMetric that =
                  (fabric.metrics.MinMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                fabric.util.Set termsBag =
                  (fabric.util.TreeSet)
                    new fabric.util.TreeSet._Impl(this.$getStore()).$getProxy();
                termsBag.addAll(
                           fabric.util.Arrays._Impl.asList(this.get$terms()));
                termsBag.addAll(
                           fabric.util.Arrays._Impl.asList(that.get$terms()));
                fabric.lang.arrays.ObjectArray newTerms =
                  (fabric.lang.arrays.ObjectArray)
                    new fabric.lang.arrays.ObjectArray._Impl(
                      this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                          this.get$$updateLabel(),
                                          this.get$$updateLabel().confPolicy(),
                                          fabric.metrics.Metric._Proxy.class,
                                          termsBag.size()).$getProxy();
                int aggIdx = 0;
                for (fabric.util.Iterator iter = termsBag.iterator();
                     iter.hasNext(); ) {
                    fabric.metrics.Metric m =
                      (fabric.metrics.Metric)
                        fabric.lang.Object._Proxy.$getProxy(iter.next());
                    newTerms.set(aggIdx++, m);
                }
                return fabric.metrics.AbstractMetric._Impl.
                  findDerivedMetric(
                    s,
                    ((fabric.metrics.MinMetric)
                       new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                        fabric$metrics$MinMetric$(newTerms));
            }
            else if (fabric.util.Arrays._Impl.asList(this.get$terms()).
                       indexOf(other) >= 0) {
                return (fabric.metrics.MinMetric) this.$getProxy();
            }
            fabric.lang.arrays.ObjectArray newTerms =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      fabric.metrics.Metric._Proxy.class,
                                      this.get$terms().get$length() +
                                          1).$getProxy();
            java.lang.System.
              arraycopy(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(this.get$terms()),
                0,
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(newTerms), 0,
                this.get$terms().get$length());
            newTerms.set(this.get$terms().get$length(), other);
            fabric.util.Arrays._Impl.sort(newTerms, 0, newTerms.get$length());
            return fabric.metrics.AbstractMetric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.MinMetric)
                   new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                    fabric$metrics$MinMetric$(newTerms));
        }
        
        public fabric.metrics.DerivedMetric copyOn(fabric.worker.Store s) {
            return fabric.metrics.AbstractMetric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.MinMetric)
                   new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                    fabric$metrics$MinMetric$(this.get$terms()));
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          fabric.metrics.contracts.Bound bound) {
            if (isSingleStore())
                return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                          new fabric.metrics.contracts.enforcement.DirectPolicy.
                            _Impl(this.$getStore()).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$DirectPolicy$(
                    (fabric.metrics.MinMetric) this.$getProxy(), bound);
            fabric.lang.arrays.ObjectArray
              witnesses =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.
                      $getStore()).
                fabric$lang$arrays$ObjectArray$(
                  this.get$$updateLabel(), this.get$$updateLabel().confPolicy(),
                  fabric.metrics.contracts.MetricContract._Proxy.class,
                  this.get$terms().get$length()).$getProxy();
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                witnesses.set(i, term(i).getContract(bound));
            }
            final fabric.worker.Store bndStore = bound.getStore();
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(bndStore).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(witnesses);
        }
        
        public int hashCode() {
            return fabric.util.Arrays._Impl.hashCode(this.get$terms()) * 32 +
              $getStore().hashCode();
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.MinMetric) {
                fabric.metrics.MinMetric that =
                  (fabric.metrics.MinMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                return fabric.util.Arrays._Impl.deepEquals(this.get$terms(),
                                                           that.get$terms()) &&
                  this.$getStore().equals(that.$getStore());
            }
            return false;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.MinMetric._Proxy(this);
        }
        
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
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.MinMetric._Static {
            public _Proxy(fabric.metrics.MinMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.MinMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  MinMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.MinMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.MinMetric._Static._Impl.class);
                $instance = (fabric.metrics.MinMetric._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.MinMetric._Static {
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
                return new fabric.metrics.MinMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 55, 27, -20, 11, -3,
    -60, -6, -32, -21, 43, -120, -64, 67, 78, 10, 30, -48, 83, 36, 113, 71, -98,
    77, 14, -85, 85, 108, -59, -24, 5, 82, 117 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500326832000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
