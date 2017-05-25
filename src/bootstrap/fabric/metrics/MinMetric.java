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
    
    public static final byte[] $classHash = new byte[] { -51, 7, 59, 96, -29,
    -42, 63, -93, -100, 28, 113, -44, -24, 5, 123, -23, -69, 99, 57, 2, -122,
    -45, 60, 80, 104, -1, -54, -59, 27, 101, 81, -98 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1495742222000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
