package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.util.List;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.util.Observer;
import fabric.metrics.util.RunningStats;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;

/**
 * Takes measurements of a persistent value of the parameter type T. When
 * constructed, the programmer provides a custom function for converting the
 * value to a double (the measurement) as well as a pointer to the value being
 * tracked.
 * <p>
 * In a transaction which has written the value being measured,
 * {@link #takeSample(double,long)} must be called to ensure that
 * {@link Observer}s of this {@link Metric} are aware of the change and the
 * transaction context performs the proper book-keeping.
 */
public interface SampledMetric extends fabric.metrics.AbstractMetric {
    public java.lang.String get$name();
    
    public java.lang.String set$name(java.lang.String val);
    
    /**
   * @param store
   *        the Store that holds this {@link Metric}
   * @param name
   *        the name the store associates with this {@link SampledMetric}
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
    
    public double get$value();
    
    public double set$value(double val);
    
    public double postInc$value();
    
    public double postDec$value();
    
    public long get$lastUpdate();
    
    public long set$lastUpdate(long val);
    
    public long postInc$lastUpdate();
    
    public long postDec$lastUpdate();
    
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
    
    public static class _Proxy extends fabric.metrics.AbstractMetric._Proxy
      implements fabric.metrics.SampledMetric {
        public java.lang.String get$name() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$name();
        }
        
        public java.lang.String set$name(java.lang.String val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$name(val);
        }
        
        public double get$value() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$value();
        }
        
        public double set$value(double val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$value(
                                                                    val);
        }
        
        public double postInc$value() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).postInc$value(
                                                                    );
        }
        
        public double postDec$value() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).postDec$value(
                                                                    );
        }
        
        public long get$lastUpdate() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              get$lastUpdate();
        }
        
        public long set$lastUpdate(long val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              set$lastUpdate(val);
        }
        
        public long postInc$lastUpdate() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              postInc$lastUpdate();
        }
        
        public long postDec$lastUpdate() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              postDec$lastUpdate();
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
        
        public double value() {
            return ((fabric.metrics.SampledMetric) fetch()).value();
        }
        
        public double velocity() {
            return ((fabric.metrics.SampledMetric) fetch()).velocity();
        }
        
        public double noise() {
            return ((fabric.metrics.SampledMetric) fetch()).noise();
        }
        
        public boolean isSingleStore() {
            return ((fabric.metrics.SampledMetric) fetch()).isSingleStore();
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
    
    public static class _Impl extends fabric.metrics.AbstractMetric._Impl
      implements fabric.metrics.SampledMetric {
        public java.lang.String get$name() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.name;
        }
        
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
   *        the Store that holds this {@link Metric}
   * @param name
   *        the name the store associates with this {@link SampledMetric}
   * @param init
   *        the initial value of this {@link Metric}
   */
        public fabric.metrics.SampledMetric fabric$metrics$SampledMetric$(
          java.lang.String name, double init) {
            this.set$name(name);
            fabric$metrics$AbstractMetric$();
            this.set$value((double) init);
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
            if (this.get$value() != sample) {
                updateEstimates(sample, time);
                this.set$value((double) sample);
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerSample((fabric.metrics.SampledMetric)
                                   this.$getProxy());
            }
        }
        
        public double value() { return this.get$value(); }
        
        public double velocity() {
            return this.get$biasStats().getMean() /
              this.get$updateIntervalStats().getMean();
        }
        
        public double noise() {
            double tSqrd = this.get$updateIntervalStats().getMean() *
              this.get$updateIntervalStats().getMean();
            double bSqrd = this.get$biasStats().getMean() *
              this.get$biasStats().getMean();
            return 1 / this.get$updateIntervalStats().getMean() +
              this.get$biasStats().getVar() / tSqrd;
        }
        
        public java.lang.String toString() {
            return "SampledMetric(" + this.get$name() + ")@" +
            getStore().name();
        }
        
        public boolean isSingleStore() { return true; }
        
        public double get$value() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.value;
        }
        
        public double set$value(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$value() {
            double tmp = this.get$value();
            this.set$value((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$value() {
            double tmp = this.get$value();
            this.set$value((double) (tmp - 1));
            return tmp;
        }
        
        /**
   * The current measurement.
   */
        protected double value;
        
        public long get$lastUpdate() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.lastUpdate;
        }
        
        public long set$lastUpdate(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.lastUpdate = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$lastUpdate() {
            long tmp = this.get$lastUpdate();
            this.set$lastUpdate((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$lastUpdate() {
            long tmp = this.get$lastUpdate();
            this.set$lastUpdate((long) (tmp - 1));
            return tmp;
        }
        
        /**
   * The time of the last update to this measure, in milliseconds since the
   * UNIX epoch.
   */
        protected long lastUpdate = -1;
        
        public fabric.metrics.util.RunningStats get$biasStats() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
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
        
        /**
   * The statistics of update magnitudes for this measure. This is computed as
   * an exponentially weighted moving average.
   */
        protected fabric.metrics.util.RunningStats biasStats;
        
        public fabric.metrics.util.RunningStats get$updateIntervalStats() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
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
        
        /**
   * The estimated time interval between updates, in milliseconds. This is
   * computed as an exponentially weighted moving average.
   */
        protected fabric.metrics.util.RunningStats updateIntervalStats;
        
        public fabric.metrics.util.RunningStats get$allStats() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
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
        
        /**
   * Directly measured stats.
   */
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
            this.get$biasStats().update(newVal - this.get$value());
            this.get$updateIntervalStats().update(eventTime -
                                                      this.get$lastUpdate());
            this.get$allStats().update((newVal - this.get$value()) /
                                           (eventTime - this.get$lastUpdate()));
            this.set$lastUpdate((long) eventTime);
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
            out.writeDouble(this.value);
            out.writeLong(this.lastUpdate);
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
            this.value = in.readDouble();
            this.lastUpdate = in.readLong();
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
    
    public static final byte[] $classHash = new byte[] { 112, 89, -56, 73, 23,
    92, 93, 100, 18, 56, 34, -126, 13, 97, 121, 80, 64, 83, -35, 116, -76, -82,
    -74, -97, 115, 41, 99, -106, -51, -126, 77, 87 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1495741485000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
