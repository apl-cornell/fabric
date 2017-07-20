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
  extends fabric.metrics.util.Observer, fabric.metrics.AbstractMetric {
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
   * Method to be called when a {@link DerivedMetric} is no longer stored.
   *
   * TODO: not clear this does anything useful anymore.
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
    
    public fabric.util.Set getLeafSubjects();
    
    public static class _Proxy extends fabric.metrics.AbstractMetric._Proxy
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
        
        public double value() {
            return ((fabric.metrics.DerivedMetric) fetch()).value();
        }
        
        public double computeValue() {
            return ((fabric.metrics.DerivedMetric) fetch()).computeValue();
        }
        
        public double velocity() {
            return ((fabric.metrics.DerivedMetric) fetch()).velocity();
        }
        
        public double computeVelocity() {
            return ((fabric.metrics.DerivedMetric) fetch()).computeVelocity();
        }
        
        public double noise() {
            return ((fabric.metrics.DerivedMetric) fetch()).noise();
        }
        
        public double computeNoise() {
            return ((fabric.metrics.DerivedMetric) fetch()).computeNoise();
        }
        
        public boolean isSingleStore() {
            return ((fabric.metrics.DerivedMetric) fetch()).isSingleStore();
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
        
        public fabric.util.Set getLeafSubjects() {
            return ((fabric.metrics.DerivedMetric) fetch()).getLeafSubjects();
        }
        
        public _Proxy(DerivedMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.metrics.AbstractMetric._Impl
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
            fabric$metrics$AbstractMetric$();
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
   * Method to be called when a {@link DerivedMetric} is no longer stored.
   *
   * TODO: not clear this does anything useful anymore.
   */
        public void cleanup() {  }
        
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
        
        /**
   * @param s
   *        a {@link Store} that will hold the copy of this
   *        {@link DerivedMetric}
   * @return a copy of this {@link DerivedMetric} that is stored on s
   */
        public abstract fabric.metrics.DerivedMetric copyOn(
          fabric.worker.Store s);
        
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
    
    public static final byte[] $classHash = new byte[] { -15, -21, 3, 53, -65,
    -38, -15, -118, -92, 71, 10, 88, -65, 16, 64, -5, -125, -92, -99, 122, 84,
    -11, -27, 115, 63, 5, 60, -112, -64, 108, 25, 43 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500579667000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
