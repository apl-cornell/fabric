package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.util.TreeSet;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.DerivedMetricContract;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.worker.Store;

/**
 * General base class for {@link Metric}s built by computing over other
 * {@link Metrics}. Each {@link DerivedMetric} implementation is responsible for
 * defining how to
 * <ul>
 * <li>construct the {@link #value()}, {@link #velocity()}, and {@link #noise()}
 * from its terms</li>
 * <li>provide a {@link EnforcementPolicy} to enforce a
 * {@link DerivedMetricContract} on it given the {@link Bound}, typically using
 * a {@link WitnessPolicy} using {@link MetricContract}s on the terms it is
 * derived from.</li>
 * </ul>
 */
public interface DerivedMetric
  extends fabric.metrics.util.Observer, fabric.metrics.Metric {
    public double get$lastValue();
    
    public double set$lastValue(double val);
    
    public double postInc$lastValue();
    
    public double postDec$lastValue();
    
    public fabric.lang.arrays.ObjectArray get$terms();
    
    public fabric.lang.arrays.ObjectArray set$terms(fabric.lang.arrays.ObjectArray val);
    
    /**
   * @param s
   *        the {@link Store} this {@link DerivedMetric} will be stored on
   * @param terms
   *        the {@link Metric}s that this {@link DerivedMetric} is
   *        computed from
   */
    public fabric.metrics.DerivedMetric fabric$metrics$DerivedMetric$(fabric.lang.arrays.ObjectArray terms);
    
    /**
   * Method to be called at the end of a constructor for any subclass of
   * {@link DerivedMetric}. Ensures that the {@link DerivedMetric} is added as
   * an {@link Observer} of its terms as well as caches the initial value of
   * the {@link Metric}.
   */
    public void initialize();
    
    public void handleUpdates();
    
    public boolean isSingleStore();
    
    /**
   * Method to be called when a {@link DerivedMetric} is no longer stored.
   * Removes itself from the {@link Observer}s of its terms.
   */
    public void cleanup();
    
    /**
   * @return the terms this {@link DerivedMetric} is defined over
   */
    public fabric.lang.arrays.ObjectArray terms();
    
    /**
   * @param i
   *        an index into the terms array
   * @return the ith term this {@link DerivedMetric} is defined over
   */
    public fabric.metrics.Metric term(int i);
    
    /**
   * @param s
   *        a {@link Store} that will hold the copy of this
   *        {@link DerivedMetric}
   * @return a copy of this {@link DerivedMetric} that is stored on s
   */
    public abstract fabric.metrics.DerivedMetric copyOn(fabric.worker.Store s);
    
    /**
   * @param bound
   *        a {@link Bound} that the returned policy enforces.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *       being above bound.
   */
    public abstract fabric.metrics.contracts.enforcement.EnforcementPolicy
      policyFor(fabric.metrics.contracts.Bound bound);
    
    public static class _Proxy extends fabric.metrics.Metric._Proxy
      implements fabric.metrics.DerivedMetric {
        public double get$lastValue() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).get$lastValue(
                                                                    );
        }
        
        public double set$lastValue(double val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).set$lastValue(
                                                                    val);
        }
        
        public double postInc$lastValue() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$lastValue();
        }
        
        public double postDec$lastValue() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$lastValue();
        }
        
        public fabric.lang.arrays.ObjectArray get$terms() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).get$terms();
        }
        
        public fabric.lang.arrays.ObjectArray set$terms(
          fabric.lang.arrays.ObjectArray val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).set$terms(
                                                                    val);
        }
        
        public fabric.metrics.DerivedMetric fabric$metrics$DerivedMetric$(
          fabric.lang.arrays.ObjectArray arg1) {
            return ((fabric.metrics.DerivedMetric) fetch()).
              fabric$metrics$DerivedMetric$(arg1);
        }
        
        public void initialize() {
            ((fabric.metrics.DerivedMetric) fetch()).initialize();
        }
        
        public void handleUpdates() {
            ((fabric.metrics.DerivedMetric) fetch()).handleUpdates();
        }
        
        public void cleanup() {
            ((fabric.metrics.DerivedMetric) fetch()).cleanup();
        }
        
        public fabric.lang.arrays.ObjectArray terms() {
            return ((fabric.metrics.DerivedMetric) fetch()).terms();
        }
        
        public fabric.metrics.Metric term(int arg1) {
            return ((fabric.metrics.DerivedMetric) fetch()).term(arg1);
        }
        
        public fabric.metrics.DerivedMetric copyOn(fabric.worker.Store arg1) {
            return ((fabric.metrics.DerivedMetric) fetch()).copyOn(arg1);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policyFor(
          fabric.metrics.contracts.Bound arg1) {
            return ((fabric.metrics.DerivedMetric) fetch()).policyFor(arg1);
        }
        
        public _Proxy(DerivedMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.metrics.Metric._Impl
      implements fabric.metrics.DerivedMetric {
        public double get$lastValue() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.lastValue;
        }
        
        public double set$lastValue(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.lastValue = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$lastValue() {
            double tmp = this.get$lastValue();
            this.set$lastValue((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$lastValue() {
            double tmp = this.get$lastValue();
            this.set$lastValue((double) (tmp - 1));
            return tmp;
        }
        
        protected double lastValue;
        
        public fabric.lang.arrays.ObjectArray get$terms() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.terms;
        }
        
        public fabric.lang.arrays.ObjectArray set$terms(
          fabric.lang.arrays.ObjectArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.terms = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.arrays.ObjectArray terms;
        
        /**
   * @param s
   *        the {@link Store} this {@link DerivedMetric} will be stored on
   * @param terms
   *        the {@link Metric}s that this {@link DerivedMetric} is
   *        computed from
   */
        public fabric.metrics.DerivedMetric fabric$metrics$DerivedMetric$(
          fabric.lang.arrays.ObjectArray terms) {
            fabric$metrics$Metric$();
            this.set$terms(
                   (fabric.lang.arrays.ObjectArray)
                     new fabric.lang.arrays.ObjectArray._Impl(
                       this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                           this.get$$updateLabel(),
                                           this.get$$updateLabel().confPolicy(),
                                           fabric.metrics.Metric._Proxy.class,
                                           terms.get$length()).$getProxy());
            fabric.util.Arrays._Impl.arraycopy(terms, 0, this.get$terms(), 0,
                                               terms.get$length());
            if (((fabric.util.TreeSet)
                   new fabric.util.TreeSet._Impl(this.$getStore()).$getProxy()).
                  fabric$util$TreeSet$(fabric.util.Arrays._Impl.asList(terms)).
                  size() != terms.get$length())
                throw new java.lang.IllegalArgumentException(
                        "DerivedMetric terms must not contain duplicates!");
            return (fabric.metrics.DerivedMetric) this.$getProxy();
        }
        
        /**
   * Method to be called at the end of a constructor for any subclass of
   * {@link DerivedMetric}. Ensures that the {@link DerivedMetric} is added as
   * an {@link Observer} of its terms as well as caches the initial value of
   * the {@link Metric}.
   */
        public void initialize() {
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                ((fabric.metrics.Metric) this.get$terms().get(i)).
                  addObserver((fabric.metrics.DerivedMetric) this.$getProxy());
            }
            this.set$lastValue((double) value());
        }
        
        public void handleUpdates() {
            double newValue = value();
            if (newValue != this.get$lastValue()) {
                this.set$lastValue((double) newValue);
                markModified();
            }
        }
        
        public boolean isSingleStore() {
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                if (!((fabric.metrics.Metric) this.get$terms().get(i)).
                      $getStore().equals($getStore()) ||
                      !((fabric.metrics.Metric) this.get$terms().get(i)).
                      isSingleStore())
                    return false;
            }
            return true;
        }
        
        /**
   * Method to be called when a {@link DerivedMetric} is no longer stored.
   * Removes itself from the {@link Observer}s of its terms.
   */
        public void cleanup() {
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                ((fabric.metrics.Metric) this.get$terms().get(i)).
                  removeObserver((fabric.metrics.DerivedMetric)
                                   this.$getProxy());
            }
        }
        
        /**
   * @return the terms this {@link DerivedMetric} is defined over
   */
        public fabric.lang.arrays.ObjectArray terms() {
            fabric.lang.arrays.ObjectArray cTerms =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      fabric.metrics.Metric._Proxy.class,
                                      this.get$terms().get$length()).$getProxy(
                                                                       );
            fabric.util.Arrays._Impl.arraycopy(this.get$terms(), 0, cTerms, 0,
                                               this.get$terms().get$length());
            return cTerms;
        }
        
        /**
   * @param i
   *        an index into the terms array
   * @return the ith term this {@link DerivedMetric} is defined over
   */
        public fabric.metrics.Metric term(int i) {
            return (fabric.metrics.Metric) this.get$terms().get(i);
        }
        
        /**
   * @param s
   *        a {@link Store} that will hold the copy of this
   *        {@link DerivedMetric}
   * @return a copy of this {@link DerivedMetric} that is stored on s
   */
        public abstract fabric.metrics.DerivedMetric copyOn(fabric.worker.Store s);
        
        /**
   * @param bound
   *        a {@link Bound} that the returned policy enforces.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *       being above bound.
   */
        public abstract fabric.metrics.contracts.enforcement.EnforcementPolicy
          policyFor(fabric.metrics.contracts.Bound bound);
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.DerivedMetric._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeDouble(this.lastValue);
            $writeRef($getStore(), this.terms, refTypes, out, intraStoreRefs,
                      interStoreRefs);
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
            this.lastValue = in.readDouble();
            this.terms = (fabric.lang.arrays.ObjectArray)
                           $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.DerivedMetric._Impl src =
              (fabric.metrics.DerivedMetric._Impl) other;
            this.lastValue = src.lastValue;
            this.terms = src.terms;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.DerivedMetric._Static {
            public _Proxy(fabric.metrics.DerivedMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.DerivedMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  DerivedMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.DerivedMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.DerivedMetric._Static._Impl.class);
                $instance = (fabric.metrics.DerivedMetric._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.DerivedMetric._Static {
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
                return new fabric.metrics.DerivedMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 67, 125, -65, -90, 31,
    40, -110, 95, -33, 115, -115, 1, -58, 29, -76, -44, 110, 21, -118, -55, -11,
    61, -109, -42, 87, -60, 115, -83, -67, -8, 88, 118 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491836575000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
