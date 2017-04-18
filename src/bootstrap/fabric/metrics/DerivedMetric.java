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
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;

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
    
    public boolean handleUpdates();
    
    public double value();
    
    public abstract double computeValue();
    
    public double velocity();
    
    public abstract double computeVelocity();
    
    public double noise();
    
    public abstract double computeNoise();
    
    public boolean isSingleStore();
    
    public void startTracking(fabric.metrics.contracts.MetricContract mc);
    
    public void stopTracking(fabric.metrics.contracts.MetricContract mc);
    
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
        
        public boolean handleUpdates() {
            return ((fabric.metrics.DerivedMetric) fetch()).handleUpdates();
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
        
        public boolean handleUpdates() {
            fabric.common.Logging.METRICS_LOGGER.
              log(java.util.logging.Level.FINE,
                  "CHECKING FOR UPDATE ON DERIVED METRIC");
            double newValue = computeValue();
            if (newValue != this.get$lastValue()) {
                this.set$lastValue((double) newValue);
                this.set$lastVelocity((double) computeVelocity());
                this.set$lastNoise((double) computeNoise());
                return true;
            }
            return false;
        }
        
        public double value() {
            if (isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
            } else {
                this.set$lastValue((double) computeValue());
            }
            return this.get$lastValue();
        }
        
        public abstract double computeValue();
        
        public double velocity() {
            if (isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
            } else {
                this.set$lastVelocity((double) computeVelocity());
            }
            return this.get$lastVelocity();
        }
        
        public abstract double computeVelocity();
        
        public double noise() {
            if (isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
            } else {
                this.set$lastNoise((double) computeNoise());
            }
            return this.get$lastNoise();
        }
        
        public abstract double computeNoise();
        
        public boolean isSingleStore() {
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                if (!((fabric.metrics.Metric) this.get$terms().get(i)).getStore(
                                                                         ).
                      equals(getStore()) ||
                      !((fabric.metrics.Metric) this.get$terms().get(i)).
                      isSingleStore())
                    return false;
            }
            return true;
        }
        
        public void startTracking(fabric.metrics.contracts.MetricContract mc) {
            if (!isObserved()) {
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    ((fabric.metrics.Metric) this.get$terms().get(i)).
                      addObserver((fabric.metrics.DerivedMetric)
                                    this.$getProxy());
                }
            }
            addObserver(mc);
        }
        
        public void stopTracking(fabric.metrics.contracts.MetricContract mc) {
            removeObserver(mc);
            if (!isObserved()) {
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    ((fabric.metrics.Metric) this.get$terms().get(i)).
                      removeObserver((fabric.metrics.DerivedMetric)
                                       this.$getProxy());
                }
            }
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
            fabric.util.Arrays._Impl.arraycopy(this.get$terms(), 0, copy, 0,
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
            final fabric.worker.Store s = getStore();
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
    
    public static final byte[] $classHash = new byte[] { -92, -67, -99, 23, 110,
    9, 75, -65, 123, -106, -14, -92, 120, -46, -126, 100, -65, -110, 21, 14, 3,
    7, 20, 122, 64, 110, 46, -24, 75, -25, -30, -16 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492522047000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
