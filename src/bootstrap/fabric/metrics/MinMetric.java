package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.util.Set;
import fabric.util.TreeSet;
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
 * A {@link DerivedMetric} for the minimum of a group of other {@link Metric}s.
 */
public interface MinMetric extends fabric.metrics.DerivedMetric {
    /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param terms
     *            The {@link Metric}s this applies to
     */
    public fabric.metrics.MinMetric fabric$metrics$MinMetric$(
      fabric.lang.arrays.ObjectArray terms);
    
    public double computeValue();
    
    public double computeWeakValue();
    
    public double computeVelocity();
    
    public double computeWeakVelocity();
    
    public double computeNoise();
    
    public double computeWeakNoise();
    
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
     *            the {@link Store} that holds this {@link Metric}
     * @param terms
     *            The {@link Metric}s this applies to
     */
        public fabric.metrics.MinMetric fabric$metrics$MinMetric$(
          fabric.lang.arrays.ObjectArray terms) {
            fabric$metrics$DerivedMetric$(terms);
            fabric.util.Set termsBag =
              ((fabric.util.TreeSet)
                 new fabric.util.TreeSet._Impl(this.$getStore()).$getProxy()).
              fabric$util$TreeSet$(fabric.util.Arrays._Impl.asList(terms));
            this.
              set$terms(
                (fabric.lang.arrays.ObjectArray)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    termsBag.
                        toArray(
                          (fabric.lang.arrays.ObjectArray)
                            new fabric.lang.arrays.ObjectArray._Impl(
                              this.$getStore()).
                            fabric$lang$arrays$ObjectArray$(
                              this.get$$updateLabel(),
                              this.get$$updateLabel().confPolicy(),
                              fabric.metrics.Metric._Proxy.class,
                              termsBag.size()).$getProxy())));
            fabric.util.Arrays._Impl.sort(this.get$terms());
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
        
        public double computeWeakValue() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.min(result,
                                     ((fabric.metrics.Metric)
                                        this.get$terms().get(i)).weakValue());
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
        
        public double computeWeakVelocity() {
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result =
                  java.lang.Math.min(
                                   result,
                                   ((fabric.metrics.Metric)
                                      this.get$terms().get(i)).weakVelocity());
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
        
        public double computeWeakNoise() {
            double noise = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                noise =
                  java.lang.Math.max(noise,
                                     ((fabric.metrics.Metric)
                                        this.get$terms().get(i)).weakNoise());
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
            return fabric.metrics.Metric._Impl.
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
            return fabric.metrics.Metric._Impl.
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
                return fabric.metrics.Metric._Impl.
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
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.MinMetric)
                   new fabric.metrics.MinMetric._Impl(s).$getProxy()).
                    fabric$metrics$MinMetric$(newTerms));
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
    
    public static final byte[] $classHash = new byte[] { -124, -31, 7, 100,
    -115, 17, 68, -67, 22, -125, -87, -52, -86, -45, -80, -68, 99, -126, 93,
    -86, -52, 78, -63, -75, 8, -93, 12, -86, -33, -26, 3, 125 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1502140261000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
