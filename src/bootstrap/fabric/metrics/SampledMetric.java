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
    
    public static final byte[] $classHash = new byte[] { 35, -49, 24, -109, 70,
    -30, 7, -87, -38, 43, 27, 89, -100, 78, 97, -18, -103, -40, 5, 71, -98, 18,
    18, -26, -113, -12, 30, -84, -12, 103, 64, -77 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1496782676000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
