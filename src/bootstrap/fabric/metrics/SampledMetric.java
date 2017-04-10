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
    
    public static final byte[] $classHash = new byte[] { 18, 93, -12, 24, -110,
    -33, 118, 110, -55, 70, -4, 69, -98, -65, -106, 105, 39, -61, -46, 34, 117,
    58, 9, -75, -112, -56, 11, -51, -10, 86, 86, 31 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491857897000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
