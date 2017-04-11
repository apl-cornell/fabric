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
                markModified();
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
    
    public static final byte[] $classHash = new byte[] { 25, -98, -92, 85, 54,
    -95, 54, 72, 100, 11, -126, 30, 98, 96, 67, 81, 17, 91, 48, -113, -126, 81,
    74, 39, 89, -97, -98, -38, 58, 85, -57, 17 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491932465000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
