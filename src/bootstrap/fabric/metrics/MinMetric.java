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
                for (int j = 0; j < this.get$terms().get$length(); j++) {
                    if (nonEmpty) { str += ", "; }
                    nonEmpty = true;
                    str += (fabric.metrics.Metric) this.get$terms().get(j);
                }
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
            fabric.util.Map witnesses =
              ((fabric.util.LinkedHashMap)
                 new fabric.util.LinkedHashMap._Impl(
                   this.$getStore()).$getProxy()).fabric$util$LinkedHashMap$();
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                fabric.metrics.Metric m = term(i);
                witnesses.put(m, m.getContract(bound));
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
    
    public static final byte[] $classHash = new byte[] { 56, 3, 94, -98, 8, 61,
    120, 119, -84, 54, -100, 56, -60, -2, 45, 85, 121, -103, 127, -47, 110, 79,
    -113, 93, 16, 64, 115, -78, 23, 101, 107, -122 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1496782676000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
