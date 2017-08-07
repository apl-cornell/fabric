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
     * {@link SumMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
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
        
        public double computeWeakValue() {
            double result = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result += ((fabric.metrics.Metric)
                             this.get$terms().get(i)).weakValue();
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
        
        public double computeWeakVelocity() {
            double result = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result += ((fabric.metrics.Metric)
                             this.get$terms().get(i)).weakVelocity();
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
        
        public double computeWeakNoise() {
            double result = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                result += ((fabric.metrics.Metric)
                             this.get$terms().get(i)).weakNoise();
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
            return fabric.metrics.Metric._Impl.
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
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.SumMetric)
                   new fabric.metrics.SumMetric._Impl(s).$getProxy()).
                    fabric$metrics$SumMetric$(newTerms));
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
            double totalValue = weakValue();
            double totalVelocity = weakVelocity();
            double totalNoise = weakNoise();
            double numTerms = this.get$terms().get$length();
            for (int j = 0; j < numTerms; j++) {
                fabric.metrics.Metric m = term(j);
                double scaledX = m.weakValue();
                double scaledV = m.weakVelocity();
                double scaledN = m.weakNoise();
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
    
    public static final byte[] $classHash = new byte[] { 27, 26, 28, 43, 18,
    125, -10, 20, 70, -92, -117, 29, -127, -64, -1, -114, -21, 100, -80, -2, 61,
    -121, -20, -98, -25, -51, 32, 93, -113, -29, 52, -11 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1502140578000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
