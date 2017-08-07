package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.util.Collections;
import fabric.util.Set;
import fabric.util.TreeSet;
import fabric.util.HashSet;
import fabric.util.Iterator;
import fabric.metrics.contracts.Bound;
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
    
    public boolean get$singleStore();
    
    public boolean set$singleStore(boolean val);
    
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
   * {@link DerivedMetric}. Ensures that the {@link getLeafSubjects()} result
   * is precomputed after the representation has been normalized.
   */
    public void initialize();
    
    public boolean handleUpdates();
    
    public double value();
    
    /** @return a freshly computed value for this {@link DerivedMetric}. */
    public abstract double computeValue();
    
    public double velocity();
    
    /** @return a freshly estimated velocity for this {@link DerivedMetric}. */
    public abstract double computeVelocity();
    
    public double noise();
    
    /** @return a freshly estimated noise for this {@link DerivedMetric}. */
    public abstract double computeNoise();
    
    public boolean isSingleStore();
    
    /**
   * {@inheritDoc}
   *
   * If this is the first observer, then this metric is being monitored for
   * changes and so it stops computing on demand and instead caches the last
   * updated value (computed on checks). This metric then becomes an observer
   * of its terms.
   */
    public void addObserver(fabric.metrics.util.Observer obs);
    
    /**
   * {@inheritDoc}
   *
   * If there are no observers after removing the given one, this metric stops
   * acting as an observer of its terms (and goes back to computing on demand
   * rather than caching the last updated value).
   */
    public void removeObserver(fabric.metrics.util.Observer obs);
    
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
    
    public fabric.util.Set getLeafSubjects();
    
    public void refreshWeakEstimates();
    
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
        
        public boolean get$singleStore() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              get$singleStore();
        }
        
        public boolean set$singleStore(boolean val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              set$singleStore(val);
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
        
        public fabric.lang.arrays.ObjectArray terms() {
            return ((fabric.metrics.DerivedMetric) fetch()).terms();
        }
        
        public fabric.metrics.Metric term(int arg1) {
            return ((fabric.metrics.DerivedMetric) fetch()).term(arg1);
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
        
        /** Last computed value of the {@link DerivedMetric} */
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
        
        /** Last estimated velocity of the {@link DerivedMetric} */
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
        
        /** Last estimated noise of the {@link DerivedMetric} */
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
        
        /**
   * The {@link Metric} terms that this {@link DerivedMetric} is computed
   * from.
   */
        protected fabric.lang.arrays.ObjectArray terms;
        
        public fabric.util.Set get$leafMetrics() { return this.leafMetrics; }
        
        public fabric.util.Set set$leafMetrics(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.leafMetrics = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Set leafMetrics;
        
        public boolean get$singleStore() { return this.singleStore; }
        
        public boolean set$singleStore(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.singleStore = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private boolean singleStore;
        
        /**
   * @param s
   *        the {@link Store} this {@link DerivedMetric} will be stored on
   * @param terms
   *        the {@link Metric}s that this {@link DerivedMetric} is
   *        computed from
   */
        public fabric.metrics.DerivedMetric fabric$metrics$DerivedMetric$(
          fabric.lang.arrays.ObjectArray terms) {
            if (((fabric.util.TreeSet)
                   new fabric.util.TreeSet._Impl(this.$getStore()).$getProxy()).
                  fabric$util$TreeSet$(fabric.util.Arrays._Impl.asList(terms)).
                  size() != terms.get$length())
                throw new java.lang.IllegalArgumentException(
                        "DerivedMetric terms must not contain duplicates: " +
                          fabric.util.Arrays._Impl.deepToString(terms));
            this.set$leafMetrics(
                   ((fabric.util.HashSet)
                      new fabric.util.HashSet._Impl(
                        this.$getStore()).$getProxy()).fabric$util$HashSet$());
            boolean single = true;
            for (int i = 0; i < terms.get$length(); i++) {
                if (!((fabric.metrics.Metric) terms.get(i)).isSingleStore() ||
                      !((fabric.metrics.Metric) terms.get(i)).$getStore().
                      equals($getStore())) {
                    single = false;
                    break;
                }
            }
            this.set$singleStore(single);
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
            return (fabric.metrics.DerivedMetric) this.$getProxy();
        }
        
        /**
   * Method to be called at the end of a constructor for any subclass of
   * {@link DerivedMetric}. Ensures that the {@link getLeafSubjects()} result
   * is precomputed after the representation has been normalized.
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
        
        /** @return a freshly computed value for this {@link DerivedMetric}. */
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
        
        /** @return a freshly estimated velocity for this {@link DerivedMetric}. */
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
        
        /** @return a freshly estimated noise for this {@link DerivedMetric}. */
        public abstract double computeNoise();
        
        public boolean isSingleStore() { return this.get$singleStore(); }
        
        /**
   * {@inheritDoc}
   *
   * If this is the first observer, then this metric is being monitored for
   * changes and so it stops computing on demand and instead caches the last
   * updated value (computed on checks). This metric then becomes an observer
   * of its terms.
   */
        public void addObserver(fabric.metrics.util.Observer obs) {
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
            super.addObserver(obs);
        }
        
        /**
   * {@inheritDoc}
   *
   * If there are no observers after removing the given one, this metric stops
   * acting as an observer of its terms (and goes back to computing on demand
   * rather than caching the last updated value).
   */
        public void removeObserver(fabric.metrics.util.Observer obs) {
            super.removeObserver(obs);
            if (!isObserved()) {
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    ((fabric.metrics.Metric) this.get$terms().get(i)).
                      removeObserver((fabric.metrics.DerivedMetric)
                                       this.$getProxy());
                }
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
        
        public fabric.util.Set getLeafSubjects() {
            return fabric.util.Collections._Impl.
              unmodifiableSet(fabric.worker.Worker.getWorker().getLocalStore(),
                              this.get$leafMetrics());
        }
        
        public void refreshWeakEstimates() {
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                ((fabric.metrics.Metric) this.get$terms().get(i)).
                  refreshWeakEstimates();
            }
            super.refreshWeakEstimates();
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
            out.writeBoolean(this.singleStore);
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
            this.singleStore = in.readBoolean();
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
            this.singleStore = src.singleStore;
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
    
    public static final byte[] $classHash = new byte[] { -88, 75, -111, 55, -10,
    33, -107, 57, 94, 46, -56, -28, -48, 26, -15, 15, -36, -93, -76, 92, -107,
    -97, -3, 57, 91, -81, 45, -35, 92, 5, 18, -71 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1502140537000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
