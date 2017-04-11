package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.util.Collections;
import fabric.util.HashSet;
import fabric.util.Set;
import fabric.util.TreeSet;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.DerivedMetricContract;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.worker.Worker;
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
    
    public double get$lastVelocity();
    
    public double set$lastVelocity(double val);
    
    public double postInc$lastVelocity();
    
    public double postDec$lastVelocity();
    
    public double get$lastNoise();
    
    public double set$lastNoise(double val);
    
    public double postInc$lastNoise();
    
    public double postDec$lastNoise();
    
    public fabric.lang.arrays.ObjectArray get$terms();
    
    public fabric.lang.arrays.ObjectArray set$terms(
      fabric.lang.arrays.ObjectArray val);
    
    public fabric.util.Set get$leafMetrics();
    
    public fabric.util.Set set$leafMetrics(fabric.util.Set val);
    
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
    
    public double value();
    
    public abstract double computeValue();
    
    public double velocity();
    
    public abstract double computeVelocity();
    
    public double noise();
    
    public abstract double computeNoise();
    
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
    
    public fabric.metrics.contracts.MetricContract createContract(fabric.metrics.contracts.Bound bound);
    
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
    
    public fabric.util.Set getLeafSubjects();
    
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
        
        public double get$lastVelocity() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              get$lastVelocity();
        }
        
        public double set$lastVelocity(double val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              set$lastVelocity(val);
        }
        
        public double postInc$lastVelocity() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$lastVelocity();
        }
        
        public double postDec$lastVelocity() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$lastVelocity();
        }
        
        public double get$lastNoise() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).get$lastNoise(
                                                                    );
        }
        
        public double set$lastNoise(double val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).set$lastNoise(
                                                                    val);
        }
        
        public double postInc$lastNoise() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$lastNoise();
        }
        
        public double postDec$lastNoise() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$lastNoise();
        }
        
        public fabric.lang.arrays.ObjectArray get$terms() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).get$terms();
        }
        
        public fabric.lang.arrays.ObjectArray set$terms(
          fabric.lang.arrays.ObjectArray val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).set$terms(
                                                                    val);
        }
        
        public fabric.util.Set get$leafMetrics() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              get$leafMetrics();
        }
        
        public fabric.util.Set set$leafMetrics(fabric.util.Set val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              set$leafMetrics(val);
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
        
        public double computeValue() {
            return ((fabric.metrics.DerivedMetric) fetch()).computeValue();
        }
        
        public double computeVelocity() {
            return ((fabric.metrics.DerivedMetric) fetch()).computeVelocity();
        }
        
        public double computeNoise() {
            return ((fabric.metrics.DerivedMetric) fetch()).computeNoise();
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
        
        public fabric.util.Set getLeafSubjects() {
            return ((fabric.metrics.DerivedMetric) fetch()).getLeafSubjects();
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
        
        public double get$lastVelocity() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.lastVelocity;
        }
        
        public double set$lastVelocity(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.lastVelocity = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$lastVelocity() {
            double tmp = this.get$lastVelocity();
            this.set$lastVelocity((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$lastVelocity() {
            double tmp = this.get$lastVelocity();
            this.set$lastVelocity((double) (tmp - 1));
            return tmp;
        }
        
        protected double lastVelocity;
        
        public double get$lastNoise() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.lastNoise;
        }
        
        public double set$lastNoise(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.lastNoise = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$lastNoise() {
            double tmp = this.get$lastNoise();
            this.set$lastNoise((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$lastNoise() {
            double tmp = this.get$lastNoise();
            this.set$lastNoise((double) (tmp - 1));
            return tmp;
        }
        
        protected double lastNoise;
        
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
        
        public fabric.util.Set get$leafMetrics() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.leafMetrics;
        }
        
        public fabric.util.Set set$leafMetrics(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.leafMetrics = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Set leafMetrics =
          (fabric.util.HashSet)
            new fabric.util.HashSet._Impl(this.$getStore()).$getProxy();
        
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
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(
                            (fabric.metrics.Metric)
                              this.get$terms().
                              get(
                                i))) instanceof fabric.metrics.SampledMetric) {
                    this.get$leafMetrics().
                      add(
                        (fabric.metrics.SampledMetric)
                          fabric.lang.Object._Proxy.$getProxy(
                                                      (fabric.metrics.Metric)
                                                        this.get$terms().get(
                                                                           i)));
                }
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 (fabric.metrics.Metric) this.get$terms().get(i))) instanceof fabric.metrics.DerivedMetric) {
                    this.
                      get$leafMetrics().
                      addAll(
                        ((fabric.metrics.DerivedMetric)
                           fabric.lang.Object._Proxy.
                           $getProxy((fabric.metrics.Metric)
                                       this.get$terms().get(i))).
                            getLeafSubjects());
                }
                else {
                    throw new java.lang.IllegalStateException(
                            "This shouldn\'t happen, all metrics should either be a SampledMetric or a DerivedMetric!");
                }
            }
            this.set$lastValue((double) computeValue());
            this.set$lastVelocity((double) computeVelocity());
            this.set$lastNoise((double) computeNoise());
        }
        
        public void handleUpdates() {
            double newValue = computeValue();
            if (newValue != this.get$lastValue()) {
                this.set$lastValue((double) newValue);
                this.set$lastVelocity((double) computeVelocity());
                this.set$lastNoise((double) computeNoise());
                markModified();
            }
        }
        
        public double value() { return this.get$lastValue(); }
        
        public abstract double computeValue();
        
        public double velocity() { return this.get$lastVelocity(); }
        
        public abstract double computeVelocity();
        
        public double noise() { return this.get$lastNoise(); }
        
        public abstract double computeNoise();
        
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
        
        public fabric.metrics.contracts.MetricContract createContract(
          fabric.metrics.contracts.Bound bound) {
            return ((fabric.metrics.contracts.DerivedMetricContract)
                      new fabric.metrics.contracts.DerivedMetricContract._Impl(
                        this.$getStore()).$getProxy()).
              fabric$metrics$contracts$DerivedMetricContract$(
                (fabric.metrics.DerivedMetric) this.$getProxy(), bound);
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
        
        public fabric.util.Set getLeafSubjects() {
            final fabric.worker.Store s =
              fabric.worker.Worker.getWorker().getLocalStore();
            return fabric.util.Collections._Impl.unmodifiableSet(
                                                   s, this.get$leafMetrics());
        }
        
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
            out.writeDouble(this.lastVelocity);
            out.writeDouble(this.lastNoise);
            $writeRef($getStore(), this.terms, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.leafMetrics, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.lastVelocity = in.readDouble();
            this.lastNoise = in.readDouble();
            this.terms = (fabric.lang.arrays.ObjectArray)
                           $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.leafMetrics = (fabric.util.Set)
                                 $readRef(fabric.util.Set._Proxy.class,
                                          (fabric.common.RefTypeEnum)
                                            refTypes.next(), in, store,
                                          intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.DerivedMetric._Impl src =
              (fabric.metrics.DerivedMetric._Impl) other;
            this.lastValue = src.lastValue;
            this.lastVelocity = src.lastVelocity;
            this.lastNoise = src.lastNoise;
            this.terms = src.terms;
            this.leafMetrics = src.leafMetrics;
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
    
    public static final byte[] $classHash = new byte[] { -124, 4, 56, 40, 16,
    -84, -21, -9, 10, -2, 5, -76, 50, -56, -31, 45, 21, 59, -70, -120, 61, 23,
    -37, -103, 127, -120, -64, 78, -119, 123, -51, 122 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491929446000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
