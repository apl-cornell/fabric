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
 * A {@link Metric} for the sum of the given metric terms.
 */
public interface SumMetric extends fabric.metrics.DerivedMetric {
    /**
     * @param store
     *            the Store that holds this {@link Metric}
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
     *            the Store that holds this {@link Metric}
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
    
    public static final byte[] $classHash = new byte[] { 24, 116, 126, 7, -121,
    21, -125, 66, -104, 88, -7, -84, -88, -66, -46, -56, 12, -117, 26, 65, -99,
    13, 107, 92, -21, -81, -126, 94, -45, -110, -86, 69 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1496245541000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
