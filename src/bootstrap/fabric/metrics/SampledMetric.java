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
public interface SampledMetric extends fabric.metrics.AbstractMetric {
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
    
    public fabric.lang.arrays.doubleArray get$valueCell();
    
    public fabric.lang.arrays.doubleArray set$valueCell(
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
    
    public static class _Proxy extends fabric.metrics.AbstractMetric._Proxy
      implements fabric.metrics.SampledMetric {
        public java.lang.String get$name() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$name();
        }
        
        public java.lang.String set$name(java.lang.String val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$name(val);
        }
        
        public fabric.lang.arrays.doubleArray get$valueCell() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$valueCell(
                                                                    );
        }
        
        public fabric.lang.arrays.doubleArray set$valueCell(
          fabric.lang.arrays.doubleArray val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$valueCell(
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
            fabric$metrics$AbstractMetric$();
            this.set$valueCell(
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
            if ((double) this.get$valueCell().get(0) != sample) {
                updateEstimates(sample, time);
                this.get$valueCell().set(0, sample);
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerSample((fabric.metrics.SampledMetric)
                                   this.$getProxy());
            }
        }
        
        public double value() { return (double) this.get$valueCell().get(0); }
        
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
        
        public fabric.lang.arrays.doubleArray get$valueCell() {
            return this.valueCell;
        }
        
        public fabric.lang.arrays.doubleArray set$valueCell(
          fabric.lang.arrays.doubleArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.valueCell = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.arrays.doubleArray valueCell;
        
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
                                            (double)
                                              this.get$valueCell().get(0));
            this.get$updateIntervalStats().update(
                                             eventTime -
                                                 (long)
                                                   this.get$lastUpdate().get(
                                                                           0));
            this.get$allStats().update((newVal -
                                          (double)
                                            this.get$valueCell().get(0)) /
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
            $writeRef($getStore(), this.valueCell, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.valueCell =
              (fabric.lang.arrays.doubleArray)
                $readRef(fabric.lang.arrays.doubleArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
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
            this.valueCell = src.valueCell;
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
    
    public static final byte[] $classHash = new byte[] { 101, -74, -17, 0, -74,
    -63, -106, 5, 2, -24, 115, 93, 88, -93, 43, 78, -53, -91, -66, -25, -122,
    -106, 69, 114, -21, -57, -62, 52, 48, -121, 83, -102 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500579678000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
