package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.util.LinkedHashMap;
import fabric.util.Map;
import fabric.util.Iterator;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;

/**
 * A {@link DerivedMetric} for the sum of the given metric terms.
 */
public interface SumMetric extends fabric.metrics.DerivedMetric {
    /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param terms
     *            The {@link Metric}s this applies to
     */
    public fabric.metrics.SumMetric fabric$metrics$SumMetric$(
      fabric.lang.arrays.ObjectArray terms);
    
    public double computeValue();
    
    public double computeVelocity();
    
    public double computeNoise();
    
    public java.lang.String toString();
    
    public fabric.metrics.DerivedMetric times(double scalar);
    
    /**
     * {@inheritDoc}
     * <p>
     * {@link SumMetric}s try to consolidate local computations so that there
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
      implements fabric.metrics.SumMetric {
        public fabric.metrics.SumMetric fabric$metrics$SumMetric$(
          fabric.lang.arrays.ObjectArray arg1) {
            return ((fabric.metrics.SumMetric) fetch()).
              fabric$metrics$SumMetric$(arg1);
        }
        
        public _Proxy(SumMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.DerivedMetric._Impl
      implements fabric.metrics.SumMetric {
        /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param terms
     *            The {@link Metric}s this applies to
     */
        public fabric.metrics.SumMetric fabric$metrics$SumMetric$(
          fabric.lang.arrays.ObjectArray terms) {
            fabric$metrics$DerivedMetric$(terms);
            initialize();
            return (fabric.metrics.SumMetric) this.$getProxy();
        }
        
        public double computeValue() {
            double result = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result += ((fabric.metrics.Metric)
                             this.get$terms().get(i)).value();
            }
            return result;
        }
        
        public double computeVelocity() {
            double result = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result += ((fabric.metrics.Metric)
                             this.get$terms().get(i)).velocity();
            }
            return result;
        }
        
        public double computeNoise() {
            double result = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result += ((fabric.metrics.Metric)
                             this.get$terms().get(i)).noise();
            }
            return result;
        }
        
        public java.lang.String toString() {
            java.lang.String str = "(";
            boolean nonEmpty = false;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                if (nonEmpty) str += " + ";
                nonEmpty = true;
                str += (fabric.metrics.Metric) this.get$terms().get(i);
            }
            return str + ")@" + getStore();
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
                ((fabric.metrics.SumMetric)
                   new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                    fabric$metrics$SumMetric$(newTerms));
        }
        
        /**
     * {@inheritDoc}
     * <p>
     * {@link SumMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            final fabric.worker.Store s = $getStore();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.SumMetric &&
                  other.$getStore().equals(s)) {
                fabric.metrics.SumMetric that =
                  (fabric.metrics.SumMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                fabric.metrics.DerivedMetric result = (fabric.metrics.SumMetric)
                                                        this.$getProxy();
                for (int i = 0; i < that.get$terms().get$length(); i++) {
                    result = result.plus((fabric.metrics.Metric)
                                           that.get$terms().get(i));
                }
                return result;
            }
            int termIdx = -1;
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.DerivedMetric) {
                fabric.metrics.DerivedMetric derivedOther =
                  (fabric.metrics.DerivedMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    if (!((fabric.metrics.Metric) this.get$terms().get(i)).
                          $getStore().equals(other.$getStore()))
                        continue;
                    if (fabric.lang.Object._Proxy.
                          $getProxy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                (fabric.metrics.Metric) this.get$terms().get(i))) instanceof fabric.metrics.DerivedMetric) {
                        fabric.metrics.DerivedMetric derivedTerm =
                          (fabric.metrics.DerivedMetric)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.metrics.Metric)
                                                          this.get$terms(
                                                                 ).get(i));
                        if (derivedTerm.getLeafSubjects().
                              containsAll(derivedOther.getLeafSubjects())) {
                            termIdx = i;
                            break;
                        }
                    }
                    else {
                        fabric.metrics.SampledMetric sampledTerm =
                          (fabric.metrics.SampledMetric)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.metrics.Metric)
                                                          this.get$terms(
                                                                 ).get(i));
                        if (derivedOther.getLeafSubjects().size() ==
                              1 &&
                              derivedOther.getLeafSubjects().contains(
                                                               sampledTerm)) {
                            termIdx = i;
                            break;
                        }
                    }
                }
            }
            else {
                fabric.metrics.SampledMetric sampledOther =
                  (fabric.metrics.SampledMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    if (!((fabric.metrics.Metric) this.get$terms().get(i)).
                          $getStore().equals(other.$getStore()))
                        continue;
                    if (fabric.lang.Object._Proxy.
                          $getProxy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                (fabric.metrics.Metric) this.get$terms().get(i))) instanceof fabric.metrics.DerivedMetric) {
                        fabric.metrics.DerivedMetric derivedTerm =
                          (fabric.metrics.DerivedMetric)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.metrics.Metric)
                                                          this.get$terms(
                                                                 ).get(i));
                        if (derivedTerm.getLeafSubjects().contains(
                                                            sampledOther)) {
                            termIdx = i;
                            break;
                        }
                    }
                    else {
                        fabric.metrics.SampledMetric sampledTerm =
                          (fabric.metrics.SampledMetric)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.metrics.Metric)
                                                          this.get$terms(
                                                                 ).get(i));
                        if (sampledTerm.equals(sampledOther)) {
                            termIdx = i;
                            break;
                        }
                    }
                }
            }
            fabric.lang.arrays.ObjectArray newTerms;
            if (termIdx >= 0) {
                newTerms =
                  (fabric.lang.arrays.ObjectArray)
                    new fabric.lang.arrays.ObjectArray._Impl(this.$getStore()).
                    fabric$lang$arrays$ObjectArray$(
                      this.get$$updateLabel(),
                      this.get$$updateLabel().confPolicy(),
                      fabric.metrics.Metric._Proxy.class,
                      this.get$terms().get$length()).$getProxy();
                fabric.util.Arrays._Impl.arraycopy(
                                           this.get$terms(), 0, newTerms, 0,
                                           this.get$terms().get$length());
                newTerms.set(termIdx,
                             ((fabric.metrics.Metric)
                                newTerms.get(termIdx)).plus(other));
            }
            else {
                newTerms =
                  (fabric.lang.arrays.ObjectArray)
                    new fabric.lang.arrays.ObjectArray._Impl(this.$getStore()).
                    fabric$lang$arrays$ObjectArray$(
                      this.get$$updateLabel(),
                      this.get$$updateLabel().confPolicy(),
                      fabric.metrics.Metric._Proxy.class,
                      this.get$terms().get$length() + 1).$getProxy();
                fabric.util.Arrays._Impl.arraycopy(
                                           this.get$terms(), 0, newTerms, 0,
                                           this.get$terms().get$length());
                newTerms.set(this.get$terms().get$length(), other);
                fabric.util.Arrays._Impl.sort(newTerms, 0,
                                              newTerms.get$length());
            }
            return fabric.metrics.AbstractMetric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.SumMetric)
                   new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                    fabric$metrics$SumMetric$(newTerms));
        }
        
        public fabric.metrics.DerivedMetric copyOn(fabric.worker.Store s) {
            return fabric.metrics.AbstractMetric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.SumMetric)
                   new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                    fabric$metrics$SumMetric$(this.get$terms()));
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          fabric.metrics.contracts.Bound bound) {
            if (isSingleStore())
                return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                          new fabric.metrics.contracts.enforcement.DirectPolicy.
                            _Impl(this.$getStore()).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$DirectPolicy$(
                    (fabric.metrics.SumMetric) this.$getProxy(), bound);
            fabric.util.Map witnesses =
              ((fabric.util.LinkedHashMap)
                 new fabric.util.LinkedHashMap._Impl(
                   this.$getStore()).$getProxy()).fabric$util$LinkedHashMap$();
            long currentTime = java.lang.System.currentTimeMillis();
            double base = bound.value(currentTime);
            double rate = bound.get$rate();
            double totalValue = computeValue();
            double totalVelocity = computeVelocity();
            double totalNoise = computeNoise();
            double numTerms = this.get$terms().get$length();
            for (int j = 0; j < numTerms; j++) {
                fabric.metrics.Metric m = term(j);
                double scaledX = m.value();
                double scaledV = m.velocity();
                double scaledN = m.noise();
                double r = scaledV - (totalVelocity - rate) / numTerms;
                double b = scaledX - scaledN / totalNoise * (totalValue - base);
                fabric.metrics.contracts.Bound witnessBound =
                  ((fabric.metrics.contracts.Bound)
                     new fabric.metrics.contracts.Bound._Impl(
                       this.$getStore()).$getProxy()).
                  fabric$metrics$contracts$Bound$(r, b, currentTime);
                if (!witnesses.containsKey(m) ||
                      !((fabric.metrics.contracts.MetricContract)
                          fabric.lang.Object._Proxy.$getProxy(
                                                      witnesses.get(m))).
                      getBound().implies(witnessBound)) {
                    witnesses.put(m, m.getContract(witnessBound));
                }
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
              getStore().hashCode();
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.SumMetric) {
                fabric.metrics.SumMetric that =
                  (fabric.metrics.SumMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                return fabric.util.Arrays._Impl.deepEquals(this.get$terms(),
                                                           that.get$terms()) &&
                  this.$getStore().equals(that.$getStore());
            }
            return false;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.SumMetric._Proxy(this);
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
          implements fabric.metrics.SumMetric._Static {
            public _Proxy(fabric.metrics.SumMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.SumMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  SumMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.SumMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.SumMetric._Static._Impl.class);
                $instance = (fabric.metrics.SumMetric._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.SumMetric._Static {
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
                return new fabric.metrics.SumMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 124, -47, 71, -109, 78,
    75, -7, 126, -95, -59, 30, -82, -74, -70, -40, -7, 96, -105, 9, -27, -117,
    10, -31, -79, -66, -96, 53, -55, 44, -34, 106, 15 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500579689000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
