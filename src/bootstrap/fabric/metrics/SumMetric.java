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
    
    public static final byte[] $classHash = new byte[] { 36, 1, -99, -54, -89,
    125, -107, 125, -74, -27, 9, 66, -70, -107, 26, 41, 66, -63, -40, 21, -53,
    -127, -43, 87, -123, -11, -123, -47, 104, -12, -103, -96 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1495742110000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
