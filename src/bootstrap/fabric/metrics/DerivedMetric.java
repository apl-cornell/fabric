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
    
    public static final byte[] $classHash = new byte[] { -19, 52, -92, 64, 110,
    -95, 81, -113, -126, -69, -81, -44, 39, 56, -88, 21, -56, -125, 91, -57, -9,
    50, -88, 112, -67, -37, -43, 70, -57, -107, -76, 72 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491927931000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
