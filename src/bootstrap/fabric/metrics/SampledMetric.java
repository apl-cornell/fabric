package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.util.List;
import fabric.metrics.util.Observer;
import fabric.metrics.util.RunningStats;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;

/**
 * A {@link Metric} that is directly updated by applications with new sample
 * values to use for its value.
 * <p>
 * In a transaction which has written the value being measured,
 * {@link #takeSample(double,long)} must be called to ensure that
 * {@link Observer}s of this {@link Metric} are aware of the change and the
 * transaction context performs the proper book-keeping.
 */
public interface SampledMetric extends fabric.metrics.Metric {
    public java.lang.String get$name();
    
    public java.lang.String set$name(java.lang.String val);
    
    /**
   * @param store
   *            the {@link Store} that holds this {@link Metric}
   * @param name
   *            the name the {@link Store} associates with this
   *            {@link SampledMetric}, so it can be easily retrieved later
   * @param init
   *        the initial value of this {@link Metric}
   */
    public fabric.metrics.SampledMetric fabric$metrics$SampledMetric$(
      java.lang.String name, double init);
    
    /**
   * Update to a new value of the metric.
   *
   * @param sample
   *        the value the metric is updating to.
   * @param time
   *        the time the sample occurred.
   */
    public void takeSample(double sample, long time);
    
    public double value();
    
    public double velocity();
    
    public double noise();
    
    public java.lang.String toString();
    
    public boolean isSingleStore();
    
    public fabric.lang.arrays.doubleArray get$value();
    
    public fabric.lang.arrays.doubleArray set$value(
      fabric.lang.arrays.doubleArray val);
    
    public fabric.lang.arrays.longArray get$lastUpdate();
    
    public fabric.lang.arrays.longArray set$lastUpdate(
      fabric.lang.arrays.longArray val);
    
    public fabric.metrics.util.RunningStats get$biasStats();
    
    public fabric.metrics.util.RunningStats set$biasStats(
      fabric.metrics.util.RunningStats val);
    
    public fabric.metrics.util.RunningStats get$updateIntervalStats();
    
    public fabric.metrics.util.RunningStats set$updateIntervalStats(
      fabric.metrics.util.RunningStats val);
    
    public fabric.metrics.util.RunningStats get$allStats();
    
    public fabric.metrics.util.RunningStats set$allStats(fabric.metrics.util.RunningStats val);
    
    /**
   * Updates the velocity and interval estimates with the given observation.
   *
   * @param newVal
   *        the new value of the measured quantity
   * @param eventTime
   *        the time, in milliseconds, this update happened
   */
    public void updateEstimates(double newVal, long eventTime);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object other);
    
    public double computeWeakValue();
    
    public double computeWeakVelocity();
    
    public double computeWeakNoise();
    
    public static class _Proxy extends fabric.metrics.Metric._Proxy
      implements fabric.metrics.SampledMetric {
        public java.lang.String get$name() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$name();
        }
        
        public java.lang.String set$name(java.lang.String val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$name(val);
        }
        
        public fabric.lang.arrays.doubleArray get$value() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$value();
        }
        
        public fabric.lang.arrays.doubleArray set$value(
          fabric.lang.arrays.doubleArray val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$value(
                                                                    val);
        }
        
        public fabric.lang.arrays.longArray get$lastUpdate() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              get$lastUpdate();
        }
        
        public fabric.lang.arrays.longArray set$lastUpdate(
          fabric.lang.arrays.longArray val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              set$lastUpdate(val);
        }
        
        public fabric.metrics.util.RunningStats get$biasStats() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$biasStats(
                                                                    );
        }
        
        public fabric.metrics.util.RunningStats set$biasStats(
          fabric.metrics.util.RunningStats val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$biasStats(
                                                                    val);
        }
        
        public fabric.metrics.util.RunningStats get$updateIntervalStats() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              get$updateIntervalStats();
        }
        
        public fabric.metrics.util.RunningStats set$updateIntervalStats(
          fabric.metrics.util.RunningStats val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              set$updateIntervalStats(val);
        }
        
        public fabric.metrics.util.RunningStats get$allStats() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$allStats(
                                                                    );
        }
        
        public fabric.metrics.util.RunningStats set$allStats(
          fabric.metrics.util.RunningStats val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$allStats(
                                                                    val);
        }
        
        public fabric.metrics.SampledMetric fabric$metrics$SampledMetric$(
          java.lang.String arg1, double arg2) {
            return ((fabric.metrics.SampledMetric) fetch()).
              fabric$metrics$SampledMetric$(arg1, arg2);
        }
        
        public void takeSample(double arg1, long arg2) {
            ((fabric.metrics.SampledMetric) fetch()).takeSample(arg1, arg2);
        }
        
        public void updateEstimates(double arg1, long arg2) {
            ((fabric.metrics.SampledMetric) fetch()).updateEstimates(arg1,
                                                                     arg2);
        }
        
        public _Proxy(SampledMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.Metric._Impl
      implements fabric.metrics.SampledMetric {
        public java.lang.String get$name() { return this.name; }
        
        public java.lang.String set$name(java.lang.String val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.name = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private java.lang.String name;
        
        /**
   * @param store
   *            the {@link Store} that holds this {@link Metric}
   * @param name
   *            the name the {@link Store} associates with this
   *            {@link SampledMetric}, so it can be easily retrieved later
   * @param init
   *        the initial value of this {@link Metric}
   */
        public fabric.metrics.SampledMetric fabric$metrics$SampledMetric$(
          java.lang.String name, double init) {
            this.set$name(name);
            fabric$metrics$Metric$();
            this.set$value(
                   fabric.lang.arrays.internal.Compat.convert(
                                                        this.$getStore(),
                                                        this.get$$updateLabel(),
                                                        this.get$$updateLabel(
                                                               ).confPolicy(),
                                                        new double[] { init }));
            this.set$lastUpdate(
                   fabric.lang.arrays.internal.Compat.convert(
                                                        this.$getStore(),
                                                        this.get$$updateLabel(),
                                                        this.get$$updateLabel(
                                                               ).confPolicy(),
                                                        new long[] { -1L }));
            this.set$biasStats(
                   ((fabric.metrics.util.RunningStats)
                      new fabric.metrics.util.RunningStats._Impl(
                        this.$getStore(
                               )).$getProxy(
                                    )).fabric$metrics$util$RunningStats$(0));
            this.set$updateIntervalStats(
                   ((fabric.metrics.util.RunningStats)
                      new fabric.metrics.util.RunningStats._Impl(
                        this.$getStore(
                               )).$getProxy(
                                    )).fabric$metrics$util$RunningStats$(1.0));
            this.set$allStats(
                   ((fabric.metrics.util.RunningStats)
                      new fabric.metrics.util.RunningStats._Impl(
                        this.$getStore(
                               )).$getProxy(
                                    )).fabric$metrics$util$RunningStats$(0));
            return (fabric.metrics.SampledMetric) this.$getProxy();
        }
        
        /**
   * Update to a new value of the metric.
   *
   * @param sample
   *        the value the metric is updating to.
   * @param time
   *        the time the sample occurred.
   */
        public void takeSample(double sample, long time) {
            if ((double) this.get$value().get(0) != sample) {
                updateEstimates(sample, time);
                this.get$value().set(0, sample);
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerSample((fabric.metrics.SampledMetric)
                                   this.$getProxy());
            }
        }
        
        public double value() { return (double) this.get$value().get(0); }
        
        public double velocity() {
            return this.get$biasStats().getMean() /
              this.get$updateIntervalStats().getMean();
        }
        
        public double noise() {
            double tSqrd = this.get$updateIntervalStats().getMean() *
              this.get$updateIntervalStats().getMean();
            return 1 / this.get$updateIntervalStats().getMean() +
              this.get$biasStats().getVar() / tSqrd;
        }
        
        public java.lang.String toString() {
            return "SampledMetric(" + this.get$name() + ")@" +
            getStore().name();
        }
        
        public boolean isSingleStore() { return true; }
        
        public fabric.lang.arrays.doubleArray get$value() { return this.value; }
        
        public fabric.lang.arrays.doubleArray set$value(
          fabric.lang.arrays.doubleArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.arrays.doubleArray value;
        
        public fabric.lang.arrays.longArray get$lastUpdate() {
            return this.lastUpdate;
        }
        
        public fabric.lang.arrays.longArray set$lastUpdate(
          fabric.lang.arrays.longArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.lastUpdate = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.arrays.longArray lastUpdate;
        
        public fabric.metrics.util.RunningStats get$biasStats() {
            return this.biasStats;
        }
        
        public fabric.metrics.util.RunningStats set$biasStats(
          fabric.metrics.util.RunningStats val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.biasStats = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.util.RunningStats biasStats;
        
        public fabric.metrics.util.RunningStats get$updateIntervalStats() {
            return this.updateIntervalStats;
        }
        
        public fabric.metrics.util.RunningStats set$updateIntervalStats(
          fabric.metrics.util.RunningStats val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.updateIntervalStats = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.util.RunningStats updateIntervalStats;
        
        public fabric.metrics.util.RunningStats get$allStats() {
            return this.allStats;
        }
        
        public fabric.metrics.util.RunningStats set$allStats(
          fabric.metrics.util.RunningStats val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.allStats = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.util.RunningStats allStats;
        
        /**
   * Updates the velocity and interval estimates with the given observation.
   *
   * @param newVal
   *        the new value of the measured quantity
   * @param eventTime
   *        the time, in milliseconds, this update happened
   */
        public void updateEstimates(double newVal, long eventTime) {
            this.get$biasStats().update(newVal -
                                            (double) this.get$value().get(0));
            this.get$updateIntervalStats().update(
                                             eventTime -
                                                 (long)
                                                   this.get$lastUpdate().get(
                                                                           0));
            this.get$allStats().update((newVal -
                                          (double) this.get$value().get(0)) /
                                           (eventTime -
                                              (long)
                                                this.get$lastUpdate().get(0)));
            this.get$lastUpdate().set(0, eventTime);
        }
        
        public int hashCode() {
            return getStore().hashCode() * 32 + this.get$name().hashCode();
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.SampledMetric) {
                fabric.metrics.SampledMetric that =
                  (fabric.metrics.SampledMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                return this.get$name().equals(that.get$name()) &&
                  this.getStore().equals(that.getStore());
            }
            return false;
        }
        
        public double computeWeakValue() { return value(); }
        
        public double computeWeakVelocity() { return velocity(); }
        
        public double computeWeakNoise() { return noise(); }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.SampledMetric._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeInline(out, this.name);
            $writeRef($getStore(), this.value, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.lastUpdate, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.biasStats, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.updateIntervalStats, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.allStats, refTypes, out, intraStoreRefs,
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
            this.name = (java.lang.String) in.readObject();
            this.value = (fabric.lang.arrays.doubleArray)
                           $readRef(fabric.lang.arrays.doubleArray._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.lastUpdate =
              (fabric.lang.arrays.longArray)
                $readRef(fabric.lang.arrays.longArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.biasStats =
              (fabric.metrics.util.RunningStats)
                $readRef(fabric.metrics.util.RunningStats._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.updateIntervalStats =
              (fabric.metrics.util.RunningStats)
                $readRef(fabric.metrics.util.RunningStats._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.allStats =
              (fabric.metrics.util.RunningStats)
                $readRef(fabric.metrics.util.RunningStats._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.SampledMetric._Impl src =
              (fabric.metrics.SampledMetric._Impl) other;
            this.name = src.name;
            this.value = src.value;
            this.lastUpdate = src.lastUpdate;
            this.biasStats = src.biasStats;
            this.updateIntervalStats = src.updateIntervalStats;
            this.allStats = src.allStats;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.SampledMetric._Static {
            public _Proxy(fabric.metrics.SampledMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.SampledMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  SampledMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.SampledMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.SampledMetric._Static._Impl.class);
                $instance = (fabric.metrics.SampledMetric._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.SampledMetric._Static {
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
                return new fabric.metrics.SampledMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 25, -82, 31, -53, 38,
    108, 98, 67, 33, -12, -59, -29, 97, -79, 36, -40, 59, 51, -27, 10, -112, 62,
    -76, -22, 49, -116, -104, 47, -51, 35, 58, -28 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1502140573000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
