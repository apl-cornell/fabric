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
        protected fabric.metrics.util.RunningStats biasStats =
          ((fabric.metrics.util.RunningStats)
             new fabric.metrics.util.RunningStats._Impl(
               this.$getStore()).$getProxy()).fabric$metrics$util$RunningStats$(
                                                0);
        
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
        protected fabric.metrics.util.RunningStats updateIntervalStats =
          ((fabric.metrics.util.RunningStats)
             new fabric.metrics.util.RunningStats._Impl(
               this.$getStore()).$getProxy()).fabric$metrics$util$RunningStats$(1.0);
        
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
    
    public static final byte[] $classHash = new byte[] { -58, -84, -76, 47, 6,
    38, 116, -26, 64, -50, -116, -95, 15, 125, -9, 100, 7, -16, 59, -1, 36, -11,
    -92, 84, 104, 120, 90, -116, -103, -104, -73, -17 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492522047000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
