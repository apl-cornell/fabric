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
    
    public fabric.metrics.DerivedMetric slice(int start, int end);
    
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
        
        public fabric.metrics.DerivedMetric slice(int arg1, int arg2) {
            return ((fabric.metrics.SampledMetric) fetch()).slice(arg1, arg2);
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
                markModified();
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
            return ((fabric.metrics.contracts.SampledMetricContract)
                      new fabric.metrics.contracts.SampledMetricContract._Impl(
                        this.$getStore()).$getProxy()).
              fabric$metrics$contracts$SampledMetricContract$(
                (fabric.metrics.SampledMetric) this.$getProxy(), bound);
        }
        
        public java.lang.String toString() {
            return "SampledMetric(" + this.get$name() + ")@" +
            $getStore().name();
        }
        
        public boolean isSingleStore() { return true; }
        
        public fabric.metrics.DerivedMetric slice(int start, int end) {
            throw new java.lang.UnsupportedOperationException(
                    "SampledMetrics can\'t be sliced!");
        }
        
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
            fabric.util.List l =
              ((fabric.util.ArrayList)
                 new fabric.util.ArrayList._Impl(this.$getStore()).$getProxy()).
              fabric$util$ArrayList$(2);
            l.add(fabric.lang.WrappedJavaInlineable.$wrap($getStore()));
            l.add(fabric.lang.WrappedJavaInlineable.$wrap(this.get$name()));
            return l.hashCode();
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
                  this.$getStore().equals(that.$getStore());
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
    
    public static final byte[] $classHash = new byte[] { 50, 52, 57, -122, -122,
    -33, 90, 60, -126, 43, 88, -97, 85, -25, -87, 101, 17, 127, -27, -86, 14,
    64, 74, 11, 37, 38, -50, -93, 32, 69, -102, -58 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491836575000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
