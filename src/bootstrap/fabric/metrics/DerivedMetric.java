package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.util.Collections;
import fabric.util.HashSet;
import fabric.util.Iterator;
import fabric.util.Set;
import fabric.util.TreeSet;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.DerivedMetricContract;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.worker.Store;
import fabric.worker.Worker;

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
    public abstract fabric.metrics.Metric copyOn(fabric.worker.Store s);
    
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
        
        public fabric.metrics.Metric copyOn(fabric.worker.Store arg1) {
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
          ((fabric.util.HashSet)
             new fabric.util.HashSet._Impl(this.$getStore()).$getProxy()).
          fabric$util$HashSet$();
        
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
            java.lang.System.
              arraycopy(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(terms),
                0,
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(this.get$terms()),
                0, terms.get$length());
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
                    this.get$leafMetrics().add((fabric.metrics.Metric)
                                                 this.get$terms().get(i));
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
        
        public double value() {
            fabric.worker.transaction.TransactionManager.getInstance().
              resolveObservations();
            return this.get$lastValue();
        }
        
        public abstract double computeValue();
        
        public double velocity() {
            fabric.worker.transaction.TransactionManager.getInstance().
              resolveObservations();
            return this.get$lastVelocity();
        }
        
        public abstract double computeVelocity();
        
        public double noise() {
            fabric.worker.transaction.TransactionManager.getInstance().
              resolveObservations();
            return this.get$lastNoise();
        }
        
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
            fabric.util.Iterator iter =
              getContracts(java.lang.System.currentTimeMillis()).iterator();
            while (iter.hasNext()) {
                ((fabric.metrics.contracts.MetricContract)
                   fabric.lang.Object._Proxy.$getProxy(iter.next())).deactivate(
                                                                       );
            }
        }
        
        /**
   * @return the terms this {@link DerivedMetric} is defined over
   */
        public fabric.lang.arrays.ObjectArray terms() {
            fabric.lang.arrays.ObjectArray copy =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      fabric.metrics.Metric._Proxy.class,
                                      this.get$terms().get$length()).$getProxy(
                                                                       );
            java.lang.System.
              arraycopy(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(this.get$terms()),
                0,
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(copy), 0,
                this.get$terms().get$length());
            return copy;
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
            final fabric.worker.Store s = $getStore();
            return ((fabric.metrics.contracts.DerivedMetricContract)
                      new fabric.metrics.contracts.DerivedMetricContract._Impl(
                        s).$getProxy()).
              fabric$metrics$contracts$DerivedMetricContract$(
                (fabric.metrics.DerivedMetric) this.$getProxy(), bound);
        }
        
        /**
   * @param s
   *        a {@link Store} that will hold the copy of this
   *        {@link DerivedMetric}
   * @return a copy of this {@link DerivedMetric} that is stored on s
   */
        public abstract fabric.metrics.Metric copyOn(fabric.worker.Store s);
        
        /**
   * @param bound
   *        a {@link Bound} that the returned policy enforces.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *       being above bound.
   */
        public abstract fabric.metrics.contracts.enforcement.EnforcementPolicy
          policyFor(fabric.metrics.contracts.Bound bound);
        
        public fabric.util.Set getLeafSubjects() {
            return fabric.util.Collections._Impl.
              unmodifiableSet(fabric.worker.Worker.getWorker().getLocalStore(),
                              this.get$leafMetrics());
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
    
    public static final byte[] $classHash = new byte[] { 96, -75, -39, -24, 7,
    110, 35, 47, 0, 26, 113, -86, 82, -54, 86, 113, 80, -13, 12, -82, -63, 73,
    -118, 41, 115, -43, 114, 97, 114, 64, -32, -98 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492108885000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
