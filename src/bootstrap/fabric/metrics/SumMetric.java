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
    
    public static final byte[] $classHash = new byte[] { 36, -19, 43, 39, -115,
    54, 107, -109, -74, 27, 115, -62, 123, -36, 98, 81, -128, 40, -83, -29, -57,
    126, -113, -108, 11, -7, -30, 103, -21, 68, 120, 86 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1496782676000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
