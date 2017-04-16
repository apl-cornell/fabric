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
            fabric.common.Logging.MISC_LOGGER.
              info(
                "CHECKING FOR UPDATE ON " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.DerivedMetric)
                                    this.$getProxy())) +
                  "(" +
                  this.get$lastValue() +
                  ") IN " +
                  fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentTid());
            double newValue = computeValue();
            if (newValue != this.get$lastValue()) {
                fabric.common.Logging.MISC_LOGGER.
                  info(
                    "UPDATING " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.
                              $unwrap((fabric.metrics.DerivedMetric)
                                        this.$getProxy())) +
                      " to " +
                      newValue +
                      " IN " +
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     ).
                        getCurrentTid());
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
                if (!((fabric.metrics.Metric) this.get$terms().get(i)).
                      $getStore().equals($getStore()) ||
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
    
    public static final byte[] $classHash = new byte[] { -37, 111, 20, 103, 97,
    -59, -24, 56, 4, 124, 23, 9, -67, -20, 50, -105, 79, -62, 6, 42, 45, 48,
    109, -27, 16, 85, -127, 112, -51, 123, -42, 59 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492364271000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
