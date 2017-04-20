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
import fabric.metrics.contracts.SampledMetricContract;
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
public interface SampledMetric extends fabric.metrics.Metric {
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
    
    public fabric.metrics.contracts.MetricContract createContract(
      fabric.metrics.contracts.Bound bound);
    
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
    
    public fabric.metrics.util.RunningStats set$updateIntervalStats(fabric.metrics.util.RunningStats val);
    
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
    
    public static class _Proxy extends fabric.metrics.Metric._Proxy
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
            fabric$metrics$Metric$();
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
            return fabric.metrics.util.RunningStats._Static._Proxy.$instance.
              get$ALPHA() *
              (this.get$updateIntervalStats().getVar() / tSqrd + bSqrd *
                 this.get$biasStats().getVar() / (tSqrd * tSqrd));
        }
        
        public fabric.metrics.contracts.MetricContract createContract(
          fabric.metrics.contracts.Bound bound) {
            final fabric.worker.Store s = getStore();
            return ((fabric.metrics.contracts.SampledMetricContract)
                      new fabric.metrics.contracts.SampledMetricContract._Impl(
                        s).$getProxy()).
              fabric$metrics$contracts$SampledMetricContract$(
                (fabric.metrics.SampledMetric) this.$getProxy(), bound);
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
        protected long lastUpdate = 0;
        
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
    
    public static final byte[] $classHash = new byte[] { 85, -128, -111, -26,
    73, 71, -112, -126, 110, 15, -26, -38, 76, -21, -124, -70, -88, -64, 44,
    -86, -1, 85, 16, 9, 79, -92, 31, -30, 19, 73, 28, -113 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492661602000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
