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
        
        private fabric.util.Set leafMetrics;
        
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
            this.set$leafMetrics(
                   ((fabric.util.HashSet)
                      new fabric.util.HashSet._Impl(
                        this.$getStore()).$getProxy()).fabric$util$HashSet$());
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
                fabric.common.Logging.METRICS_LOGGER.
                  log(java.util.logging.Level.FINE, "UPDATE ON DERIVED METRIC");
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
                this.set$lastValue((double) computeValue());
                this.set$lastVelocity((double) computeVelocity());
                this.set$lastNoise((double) computeNoise());
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
    
    public static final byte[] $classHash = new byte[] { 13, -89, -123, 14, 78,
    45, -15, -75, -112, 105, 104, -48, 47, 39, -66, -66, 105, -32, 73, -111,
    115, 1, -3, -40, -120, -83, -62, 108, 86, -38, 8, 56 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492661824000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
