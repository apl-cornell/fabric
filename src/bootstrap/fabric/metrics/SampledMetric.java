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
            final fabric.worker.Store s = $getStore();
            return ((fabric.metrics.contracts.SampledMetricContract)
                      new fabric.metrics.contracts.SampledMetricContract._Impl(
                        s).$getProxy()).
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
    
    public static final byte[] $classHash = new byte[] { -4, 93, -8, -12, 105,
    -73, -113, -117, -25, -87, 105, 47, -55, 17, -95, -89, -2, -19, -70, 23,
    -30, 11, 33, 26, 58, -6, -52, -77, -11, 39, -125, 67 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492108299000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
