package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.metrics.PresetMetricStatistics;

/**
 * Utility class for tracking the sample mean and sample standard deviation of a
 * value that is updated periodically.
 * <p>
 * This was originally based on a post on John D. Cook's blog here:
 * https://www.johndcook.com/blog/standard_deviation/
 * <p>
 * That post assumed a stable distribution. This has since been modified to use
 * EWMA.
 */
public interface RunningMetricStats extends fabric.lang.Object {
    public double get$value();
    
    public double set$value(double val);
    
    public double postInc$value();
    
    public double postDec$value();
    
    public double get$startDelta();
    
    public double set$startDelta(double val);
    
    public double postInc$startDelta();
    
    public double postDec$startDelta();
    
    public double get$startInterval();
    
    public double set$startInterval(double val);
    
    public double postInc$startInterval();
    
    public double postDec$startInterval();
    
    public double get$intervalEst();
    
    public double set$intervalEst(double val);
    
    public double postInc$intervalEst();
    
    public double postDec$intervalEst();
    
    public double get$velocityEst();
    
    public double set$velocityEst(double val);
    
    public double postInc$velocityEst();
    
    public double postDec$velocityEst();
    
    public double get$noiseEst();
    
    public double set$noiseEst(double val);
    
    public double postInc$noiseEst();
    
    public double postDec$noiseEst();
    
    public long get$lastUpdate();
    
    public long set$lastUpdate(long val);
    
    public long postInc$lastUpdate();
    
    public long postDec$lastUpdate();
    
    public long get$samples();
    
    public long set$samples(long val);
    
    public long postInc$samples();
    
    public long postDec$samples();
    
    public java.lang.String toString();
    
    /**
   * @param startValue
   *        initial guess for the mean of the value we're keeping
   *        statistics on.
   */
    public fabric.metrics.util.RunningMetricStats
      fabric$metrics$util$RunningMetricStats$(double startValue,
                                              double startDelta,
                                              double startInterval);
    
    /**
   * Reset estimation to just the startValue.
   */
    public void reset();
    
    /**
   * @return the current value.
   */
    public double getValue();
    
    /**
   * Compute an estimated velocity, assuming normal distribution of values and
   * exponential distribution of intervals.
   *
   * @return the current estimated velocity.
   */
    public double getVelocityEstimate();
    
    /**
   * Compute an estimated noise, assuming normal distribution of values and
   * exponential distribution of intervals.
   *
   * @return the current estimated noise.
   */
    public double getNoiseEstimate();
    
    /**
   * @return the last update time.
   */
    public long getLastUpdate();
    
    /**
   * @return the last update time.
   */
    public double getIntervalEstimate();
    
    /**
   * @return the number of samples taken.
   */
    public long getSamples();
    
    /**
   * Update with a new observation.
   *
   * @param val
   *        the newly observed value.
   */
    public void update(double newVal);
    
    /**
   * Support preloading based on a string key.
   *
   * Should be called right before the next update to ensure the lastUpdate
   * configuration is roughly accurate.
   */
    public void preload(java.lang.String key);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.RunningMetricStats {
        public double get$value() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$value();
        }
        
        public double set$value(double val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$value(val);
        }
        
        public double postInc$value() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$value();
        }
        
        public double postDec$value() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$value();
        }
        
        public double get$startDelta() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$startDelta();
        }
        
        public double set$startDelta(double val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$startDelta(val);
        }
        
        public double postInc$startDelta() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$startDelta();
        }
        
        public double postDec$startDelta() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$startDelta();
        }
        
        public double get$startInterval() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$startInterval();
        }
        
        public double set$startInterval(double val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$startInterval(val);
        }
        
        public double postInc$startInterval() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$startInterval();
        }
        
        public double postDec$startInterval() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$startInterval();
        }
        
        public double get$intervalEst() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$intervalEst();
        }
        
        public double set$intervalEst(double val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$intervalEst(val);
        }
        
        public double postInc$intervalEst() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$intervalEst();
        }
        
        public double postDec$intervalEst() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$intervalEst();
        }
        
        public double get$velocityEst() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$velocityEst();
        }
        
        public double set$velocityEst(double val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$velocityEst(val);
        }
        
        public double postInc$velocityEst() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$velocityEst();
        }
        
        public double postDec$velocityEst() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$velocityEst();
        }
        
        public double get$noiseEst() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$noiseEst();
        }
        
        public double set$noiseEst(double val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$noiseEst(val);
        }
        
        public double postInc$noiseEst() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$noiseEst();
        }
        
        public double postDec$noiseEst() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$noiseEst();
        }
        
        public long get$lastUpdate() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$lastUpdate();
        }
        
        public long set$lastUpdate(long val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$lastUpdate(val);
        }
        
        public long postInc$lastUpdate() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$lastUpdate();
        }
        
        public long postDec$lastUpdate() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$lastUpdate();
        }
        
        public long get$samples() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$samples();
        }
        
        public long set$samples(long val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$samples(val);
        }
        
        public long postInc$samples() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$samples();
        }
        
        public long postDec$samples() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$samples();
        }
        
        public fabric.metrics.util.RunningMetricStats
          fabric$metrics$util$RunningMetricStats$(double arg1, double arg2,
                                                  double arg3) {
            return ((fabric.metrics.util.RunningMetricStats) fetch()).
              fabric$metrics$util$RunningMetricStats$(arg1, arg2, arg3);
        }
        
        public void reset() {
            ((fabric.metrics.util.RunningMetricStats) fetch()).reset();
        }
        
        public double getValue() {
            return ((fabric.metrics.util.RunningMetricStats) fetch()).getValue(
                                                                        );
        }
        
        public double getVelocityEstimate() {
            return ((fabric.metrics.util.RunningMetricStats) fetch()).
              getVelocityEstimate();
        }
        
        public double getNoiseEstimate() {
            return ((fabric.metrics.util.RunningMetricStats) fetch()).
              getNoiseEstimate();
        }
        
        public long getLastUpdate() {
            return ((fabric.metrics.util.RunningMetricStats) fetch()).
              getLastUpdate();
        }
        
        public double getIntervalEstimate() {
            return ((fabric.metrics.util.RunningMetricStats) fetch()).
              getIntervalEstimate();
        }
        
        public long getSamples() {
            return ((fabric.metrics.util.RunningMetricStats) fetch()).
              getSamples();
        }
        
        public void update(double arg1) {
            ((fabric.metrics.util.RunningMetricStats) fetch()).update(arg1);
        }
        
        public void preload(java.lang.String arg1) {
            ((fabric.metrics.util.RunningMetricStats) fetch()).preload(arg1);
        }
        
        public static double updatedVelocity(double arg1, double arg2,
                                             long arg3, long arg4, long arg5) {
            return fabric.metrics.util.RunningMetricStats._Impl.updatedVelocity(
                                                                  arg1, arg2,
                                                                  arg3, arg4,
                                                                  arg5);
        }
        
        public static double updatedNoise(double arg1, double arg2, double arg3,
                                          long arg4, long arg5, long arg6) {
            return fabric.metrics.util.RunningMetricStats._Impl.updatedNoise(
                                                                  arg1, arg2,
                                                                  arg3, arg4,
                                                                  arg5, arg6);
        }
        
        public _Proxy(RunningMetricStats._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.RunningMetricStats {
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
        
        private double value;
        
        public double get$startDelta() { return this.startDelta; }
        
        public double set$startDelta(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.startDelta = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$startDelta() {
            double tmp = this.get$startDelta();
            this.set$startDelta((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$startDelta() {
            double tmp = this.get$startDelta();
            this.set$startDelta((double) (tmp - 1));
            return tmp;
        }
        
        private double startDelta;
        
        public double get$startInterval() { return this.startInterval; }
        
        public double set$startInterval(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.startInterval = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$startInterval() {
            double tmp = this.get$startInterval();
            this.set$startInterval((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$startInterval() {
            double tmp = this.get$startInterval();
            this.set$startInterval((double) (tmp - 1));
            return tmp;
        }
        
        private double startInterval;
        
        public double get$intervalEst() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.intervalEst;
        }
        
        public double set$intervalEst(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.intervalEst = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$intervalEst() {
            double tmp = this.get$intervalEst();
            this.set$intervalEst((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$intervalEst() {
            double tmp = this.get$intervalEst();
            this.set$intervalEst((double) (tmp - 1));
            return tmp;
        }
        
        private double intervalEst = 0.0;
        
        public double get$velocityEst() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.velocityEst;
        }
        
        public double set$velocityEst(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.velocityEst = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$velocityEst() {
            double tmp = this.get$velocityEst();
            this.set$velocityEst((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$velocityEst() {
            double tmp = this.get$velocityEst();
            this.set$velocityEst((double) (tmp - 1));
            return tmp;
        }
        
        private double velocityEst = 0.0;
        
        public double get$noiseEst() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.noiseEst;
        }
        
        public double set$noiseEst(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.noiseEst = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$noiseEst() {
            double tmp = this.get$noiseEst();
            this.set$noiseEst((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$noiseEst() {
            double tmp = this.get$noiseEst();
            this.set$noiseEst((double) (tmp - 1));
            return tmp;
        }
        
        private double noiseEst = 0.0;
        
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
        
        private long lastUpdate;
        
        public long get$samples() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.samples;
        }
        
        public long set$samples(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.samples = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$samples() {
            long tmp = this.get$samples();
            this.set$samples((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$samples() {
            long tmp = this.get$samples();
            this.set$samples((long) (tmp - 1));
            return tmp;
        }
        
        private long samples = 0;
        
        public java.lang.String toString() {
            return "[" + " value " + this.get$value() + " samples " +
            this.get$samples() + " lastUpdate " + this.get$lastUpdate() +
            " intervalEst " + getIntervalEstimate() + " velocityEst " +
            getVelocityEstimate() + " noiseEst " + getNoiseEstimate() + " ]";
        }
        
        /**
   * @param startValue
   *        initial guess for the mean of the value we're keeping
   *        statistics on.
   */
        public fabric.metrics.util.RunningMetricStats
          fabric$metrics$util$RunningMetricStats$(double startValue,
                                                  double startDelta,
                                                  double startInterval) {
            this.set$value((double) startValue);
            this.set$startDelta((double) startDelta);
            this.set$startInterval((double) startInterval);
            fabric$lang$Object$();
            this.set$intervalEst((double) startInterval);
            this.set$velocityEst((double) (startDelta / startInterval));
            this.set$noiseEst((double) 0);
            this.set$lastUpdate((long)
                                  (java.lang.System.currentTimeMillis() - 1));
            this.set$samples((long) 0);
            return (fabric.metrics.util.RunningMetricStats) this.$getProxy();
        }
        
        /**
   * Reset estimation to just the startValue.
   */
        public void reset() {
            this.set$intervalEst((double) this.get$startInterval());
            this.set$velocityEst((double)
                                   (this.get$startDelta() /
                                      this.get$startInterval()));
            this.set$noiseEst((double) 0);
            this.set$lastUpdate((long)
                                  (java.lang.System.currentTimeMillis() - 1));
            this.set$samples((long) 0);
        }
        
        /**
   * @return the current value.
   */
        public double getValue() { return this.get$value(); }
        
        /**
   * Compute an estimated velocity, assuming normal distribution of values and
   * exponential distribution of intervals.
   *
   * @return the current estimated velocity.
   */
        public double getVelocityEstimate() {
            if (this.get$samples() <= 1) return 0.0;
            return fabric.metrics.util.RunningMetricStats._Impl.
              updatedVelocity(this.get$velocityEst(), this.get$intervalEst(),
                              this.get$samples(), this.get$lastUpdate(),
                              java.lang.System.currentTimeMillis());
        }
        
        /**
   * Compute an estimated noise, assuming normal distribution of values and
   * exponential distribution of intervals.
   *
   * @return the current estimated noise.
   */
        public double getNoiseEstimate() {
            if (this.get$samples() <= 1) return 0.0;
            return fabric.metrics.util.RunningMetricStats._Impl.
              updatedNoise(this.get$velocityEst(), this.get$noiseEst(),
                           this.get$intervalEst(), this.get$samples(),
                           this.get$lastUpdate(),
                           java.lang.System.currentTimeMillis());
        }
        
        /**
   * @return the last update time.
   */
        public long getLastUpdate() { return this.get$lastUpdate(); }
        
        /**
   * @return the last update time.
   */
        public double getIntervalEstimate() { return this.get$intervalEst(); }
        
        /**
   * @return the number of samples taken.
   */
        public long getSamples() { return this.get$samples(); }
        
        /**
   * Update with a new observation.
   *
   * @param val
   *        the newly observed value.
   */
        public void update(double newVal) {
            long curTime = java.lang.System.currentTimeMillis();
            double dx = newVal - this.get$value();
            this.set$value((double) newVal);
            if (this.get$samples() == 0) {
                
            } else if (this.get$samples() == 1) {
                double dt = java.lang.Math.max(0.5,
                                               curTime - this.get$lastUpdate());
                this.set$intervalEst((double) dt);
                this.set$velocityEst((double) (dx / dt));
                this.set$noiseEst((double) 0.0);
            } else {
                double dt = curTime - this.get$lastUpdate();
                double alpha = ((fabric.metrics.util.RunningMetricStats._Impl)
                                  this.fetch()).getCurAlpha();
                this.set$intervalEst((double)
                                       ((1.0 - alpha) * this.get$intervalEst() +
                                          alpha * dt));
                double newV = dx / this.get$intervalEst();
                double oldVelocity = this.get$velocityEst();
                this.set$velocityEst((double)
                                       ((1.0 - alpha) * this.get$velocityEst() +
                                          alpha * newV));
                this.set$noiseEst((double)
                                    ((1.0 - alpha) * this.get$noiseEst() +
                                       alpha * (newV - this.get$velocityEst()) *
                                       (newV - oldVelocity)));
            }
            this.set$lastUpdate((long) curTime);
            this.postInc$samples();
        }
        
        private double getCurAlpha() {
            return fabric.metrics.util.RunningMetricStats._Impl.
              getAlpha(this.get$samples());
        }
        
        private static double getAlpha(long samples) {
            return java.lang.Math.
              max(
                fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                    get$ALPHA(),
                1.0 / java.lang.Math.max(1, samples));
        }
        
        /**
   * Support preloading based on a string key.
   *
   * Should be called right before the next update to ensure the lastUpdate
   * configuration is roughly accurate.
   */
        public void preload(java.lang.String key) {
            if (this.get$samples() == 0) {
                fabric.worker.metrics.PresetMetricStatistics p =
                  (fabric.worker.metrics.PresetMetricStatistics)
                    fabric.worker.Worker.getWorker().config.presets.get(key);
                if (!fabric.lang.Object._Proxy.idEquals(p, null)) {  }
            }
        }
        
        /**
   * Utility for converting a stale velocity estimate to a properly "decayed"
   * estimate.
   */
        public static double updatedVelocity(double velocityEst,
                                             double intervalEst, long samples,
                                             long lastUpdate, long curTime) {
            if (samples <= 1) return velocityEst;
            if (curTime - lastUpdate <= intervalEst / 2.0) return velocityEst;
            double alpha =
              fabric.metrics.util.RunningMetricStats._Impl.getAlpha(samples);
            return (1.0 - alpha) * velocityEst;
        }
        
        /**
   * Utility for converting a stale noise estimate to a properly "decayed"
   * estimate.
   */
        public static double updatedNoise(double velocityEst, double noiseEst,
                                          double intervalEst, long samples,
                                          long lastUpdate, long curTime) {
            if (samples <= 1) return noiseEst;
            if (curTime - lastUpdate <= intervalEst / 2.0) return noiseEst;
            double alpha =
              fabric.metrics.util.RunningMetricStats._Impl.getAlpha(samples);
            double dt = 2 * (curTime - lastUpdate);
            double t = (1.0 - alpha) * intervalEst + alpha * dt;
            double v = (1.0 - alpha) * velocityEst;
            return (1.0 - alpha) * noiseEst + alpha * v * velocityEst;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.RunningMetricStats._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeDouble(this.value);
            out.writeDouble(this.startDelta);
            out.writeDouble(this.startInterval);
            out.writeDouble(this.intervalEst);
            out.writeDouble(this.velocityEst);
            out.writeDouble(this.noiseEst);
            out.writeLong(this.lastUpdate);
            out.writeLong(this.samples);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.value = in.readDouble();
            this.startDelta = in.readDouble();
            this.startInterval = in.readDouble();
            this.intervalEst = in.readDouble();
            this.velocityEst = in.readDouble();
            this.noiseEst = in.readDouble();
            this.lastUpdate = in.readLong();
            this.samples = in.readLong();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.RunningMetricStats._Impl src =
              (fabric.metrics.util.RunningMetricStats._Impl) other;
            this.value = src.value;
            this.startDelta = src.startDelta;
            this.startInterval = src.startInterval;
            this.intervalEst = src.intervalEst;
            this.velocityEst = src.velocityEst;
            this.noiseEst = src.noiseEst;
            this.lastUpdate = src.lastUpdate;
            this.samples = src.samples;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public double get$ALPHA();
        
        public double set$ALPHA(double val);
        
        public double postInc$ALPHA();
        
        public double postDec$ALPHA();
        
        public long get$POISSON_WINDOW();
        
        public long set$POISSON_WINDOW(long val);
        
        public long postInc$POISSON_WINDOW();
        
        public long postDec$POISSON_WINDOW();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.RunningMetricStats._Static {
            public double get$ALPHA() {
                return ((fabric.metrics.util.RunningMetricStats._Static._Impl)
                          fetch()).get$ALPHA();
            }
            
            public double set$ALPHA(double val) {
                return ((fabric.metrics.util.RunningMetricStats._Static._Impl)
                          fetch()).set$ALPHA(val);
            }
            
            public double postInc$ALPHA() {
                return ((fabric.metrics.util.RunningMetricStats._Static._Impl)
                          fetch()).postInc$ALPHA();
            }
            
            public double postDec$ALPHA() {
                return ((fabric.metrics.util.RunningMetricStats._Static._Impl)
                          fetch()).postDec$ALPHA();
            }
            
            public long get$POISSON_WINDOW() {
                return ((fabric.metrics.util.RunningMetricStats._Static._Impl)
                          fetch()).get$POISSON_WINDOW();
            }
            
            public long set$POISSON_WINDOW(long val) {
                return ((fabric.metrics.util.RunningMetricStats._Static._Impl)
                          fetch()).set$POISSON_WINDOW(val);
            }
            
            public long postInc$POISSON_WINDOW() {
                return ((fabric.metrics.util.RunningMetricStats._Static._Impl)
                          fetch()).postInc$POISSON_WINDOW();
            }
            
            public long postDec$POISSON_WINDOW() {
                return ((fabric.metrics.util.RunningMetricStats._Static._Impl)
                          fetch()).postDec$POISSON_WINDOW();
            }
            
            public _Proxy(fabric.metrics.util.RunningMetricStats._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.RunningMetricStats._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  RunningMetricStats.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    util.
                    RunningMetricStats.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.RunningMetricStats._Static.
                        _Impl.class);
                $instance = (fabric.metrics.util.RunningMetricStats._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.RunningMetricStats._Static {
            public double get$ALPHA() { return this.ALPHA; }
            
            public double set$ALPHA(double val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.ALPHA = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public double postInc$ALPHA() {
                double tmp = this.get$ALPHA();
                this.set$ALPHA((double) (tmp + 1));
                return tmp;
            }
            
            public double postDec$ALPHA() {
                double tmp = this.get$ALPHA();
                this.set$ALPHA((double) (tmp - 1));
                return tmp;
            }
            
            public double ALPHA;
            
            public long get$POISSON_WINDOW() { return this.POISSON_WINDOW; }
            
            public long set$POISSON_WINDOW(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.POISSON_WINDOW = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public long postInc$POISSON_WINDOW() {
                long tmp = this.get$POISSON_WINDOW();
                this.set$POISSON_WINDOW((long) (tmp + 1));
                return tmp;
            }
            
            public long postDec$POISSON_WINDOW() {
                long tmp = this.get$POISSON_WINDOW();
                this.set$POISSON_WINDOW((long) (tmp - 1));
                return tmp;
            }
            
            public long POISSON_WINDOW;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeDouble(this.ALPHA);
                out.writeLong(this.POISSON_WINDOW);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.ALPHA = in.readDouble();
                this.POISSON_WINDOW = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.RunningMetricStats._Static.
                         _Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm673 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled676 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff674 = 1;
                        boolean $doBackoff675 = true;
                        boolean $retry670 = true;
                        $label668: for (boolean $commit669 = false; !$commit669;
                                        ) {
                            if ($backoffEnabled676) {
                                if ($doBackoff675) {
                                    if ($backoff674 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff674);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e671) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff674 < 5000) $backoff674 *= 2;
                                }
                                $doBackoff675 = $backoff674 <= 32 ||
                                                  !$doBackoff675;
                            }
                            $commit669 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.util.RunningMetricStats._Static.
                                  _Proxy.
                                  $instance.
                                  set$ALPHA(
                                    (double)
                                      fabric.worker.Worker.getWorker().config.
                                        alpha);
                                fabric.metrics.util.RunningMetricStats._Static.
                                  _Proxy.
                                  $instance.
                                  set$POISSON_WINDOW(
                                    (long)
                                      fabric.worker.Worker.getWorker().config.
                                        window);
                            }
                            catch (final fabric.worker.RetryException $e671) {
                                $commit669 = false;
                                continue $label668;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e671) {
                                $commit669 = false;
                                fabric.common.TransactionID $currentTid672 =
                                  $tm673.getCurrentTid();
                                if ($e671.tid.isDescendantOf($currentTid672))
                                    continue $label668;
                                if ($currentTid672.parent != null) {
                                    $retry670 = false;
                                    throw $e671;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e671) {
                                $commit669 = false;
                                if ($tm673.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid672 =
                                  $tm673.getCurrentTid();
                                if ($e671.tid.isDescendantOf($currentTid672)) {
                                    $retry670 = true;
                                }
                                else if ($currentTid672.parent != null) {
                                    $retry670 = false;
                                    throw $e671;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e671) {
                                $commit669 = false;
                                if ($tm673.checkForStaleObjects())
                                    continue $label668;
                                $retry670 = false;
                                throw new fabric.worker.AbortException($e671);
                            }
                            finally {
                                if ($commit669) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e671) {
                                        $commit669 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e671) {
                                        $commit669 = false;
                                        fabric.common.TransactionID
                                          $currentTid672 =
                                          $tm673.getCurrentTid();
                                        if ($currentTid672 != null) {
                                            if ($e671.tid.equals(
                                                            $currentTid672) ||
                                                  !$e671.tid.
                                                  isDescendantOf(
                                                    $currentTid672)) {
                                                throw $e671;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit669 && $retry670) {
                                    {  }
                                    continue $label668;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 2, 26, 105, -25, -121,
    36, -17, 82, -35, 6, -11, -68, -44, -2, -33, -66, 43, -69, -76, -100, -3,
    -17, 2, 110, 77, 27, -87, -97, -114, -108, 6, -19 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524675608000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVZDWwUxxWeO9vnH4zP2JgfY4wxhoq/O0GrtolbGnNAfM0ZuzaG1rRx1ntz9sZ7u8funDkSSFIIBaWRK6WGBCUQ1BIRqEPatDSq+BH9DYiqShBqixoa1JaSiiAaRYRWaqDvzY7vd2+xpdbSzlvPzHvzvTfvZ+Z29AYpMg3SGJH6FNXHtsSo6Vsj9QVDHZJh0nBAlUxzHfT2ypMKg3vfPxyudxN3iJTLkqZriiypvZrJSEXoUWlI8muU+bs7g80bSamMjK2SOcCIe+PKhEEaYrq6pV/VmVgkR/6exf6R5x+ufKOAeHuIV9G6mMQUOaBrjCZYDymP0mgfNcyWcJiGe8gUjdJwFzUUSVUeg4m61kOqTKVfk1jcoGYnNXV1CCdWmfEYNfiaY50IXwfYRlxmugHwKy34caao/pBisuYQ8UQUqobNTeQJUhgiRRFV6oeJ00JjWvi5RP8a7IfpZQrANCKSTMdYCgcVLczInGyOpMZND8EEYC2OUjagJ5cq1CToIFUWJFXS+v1dzFC0fphapMdhFUZq8wqFSSUxSR6U+mkvIzOy53VYQzCrlJsFWRipyZ7GJcGe1WbtWdpu3Vj7heHHtVbNTVyAOUxlFfGXAFN9FlMnjVCDajK1GMsXhfZK007tdhMCk2uyJltz3tz64QNL6s+ctebMspnT3vcolVmvfKiv4p26wML7ChBGSUw3FXSFDM35rnaIkeZEDLx9WlIiDvrGBs90/uZrTx2l192kLEg8sq7Go+BVU2Q9GlNUajxINWpIjIaDpJRq4QAfD5JieA8pGrV62yMRk7IgKVR5l0fn/4OJIiACTVQM74oW0cfeYxIb4O+JGCGkGB7igucaIbM2AvXCvycZ6fYP6FHq71PjdDO4tx8eKhnygB/i1lBkv2nIfiOuMQUmiS7wIiCmpX9nXNPAh9p4H0aW6QNAsf+X4ARqVLnZ5QJjz5H1MO2TTNg54UUrO1QIlFZdDVOjV1aHTwVJ9al93JNK0ftN8GBuKxfsfl123kjnHYmvXP3hsd7zlhcirzAlIwsstD6B1trpXLQAsBwjzQe5ywe5a9SV8AUOBH/AHcpj8shLyiwHmffHVIlFdCOaIC4XV3Aq5+fywQ8GIb9ACilf2PWNLz+yu7EAXDi2uRB3FaY2ZQdUKg0F4U2CKOmVvbve//j1vdv0VGgx0pQT8bmcGLGN2dYydJmGISOmxC9qkI73ntrW5MZsUwqJkEngqpBV6rPXyIjc5rEsiNYoCpFJaANJxaGx1FXGBgx9c6qHe0EFNlWWQ6CxsgDyBPrFrtj+P/7uH5/mpWUs13rTknIXZc1p8Y3CvDySp6Rsv86gFOZdfqHju3tu7NrIDQ8z5tkt2IRtAOJagoDWjZ1nN11678+HLrpTm8WIJxbvUxU5wXWZchf+XPDcwQeDFDuQQqoOiATRkMwQMVx5QQob5AoV8hVAN5u6tageViKK1KdS9JT/eOcvO/7BcKW13Sr0WMYzyJJ7C0j1z1xJnjr/8O16LsYlY61K2S81zUqA1SnJLYYhbUEciW9emL3vLWk/eD6kL1N5jPKMRLg9CN/A5dwWS3m7LGvsM9g0Wtaq4/1lZm4xWINVNeWLPf7Rl2oDK65bsZ/0RZQx1yb210tpYbL8aPSWu9Hzazcp7iGVvKBLGlsvQSYDN+iBkmwGRGeITM4YzyyvVi1pTsZaXXYcpC2bHQWpnAPvOBvfyyzHtxwHDDENjbQanmpC3JsFbcXR6hi2UxMuwl/u5yzzeLsAm4XckAX4uohhOsIjESOlSjQaZ7j/fKXFjBS1hDpaW2wM3mEoUYiZIVF96e6RZ+76hkcsZ7OOKPNyTgnpPNYxha8zmS+WgFXmOq3COdZce33biVe37bJKeFVmwV2txaOv/f6T3/peuHLOJnV7wjqEHrVSBrafzTRlGzw1YMKPBD1pY8pWJ1NiswKbL43Zr6KjPdjV1b62d0Nw7ar2DZytRaiLZBWD0NStUpCNqQwxTbeegnKLuu/aYOqwxwS5pjhmKEOQOBJJoW4UWiqE3RH0dppQ2PMh9GX8J2AHqxIlNMIzA2D5BZ1tA2uDPSw3vq7LsFIZOKDBVlGVSc7LzodnJizXLegDNst+fdzLTubLBvGEDSrnXZnvwzx4amHFLYI+YrOyZK2MTVeuwZGrV9CvZhh8kiIgrLZytDOKWcC/X9CnbVBEHFEg1w5Bt2aiGKKqLitsyz1RNMBTB/ynBX3VBsWgIwrkOizowQwUJZqumPSeEMCIZDYwXxL0nA2EmCME5Dor6M8zIJTBbYR1x8IQN9jTlhcE2JDUA/stQf9mAyLuCAK5/irouxkgik0pGlOtA4+FIJEnynnmSQU4//OIE/4JQY+nyU6rpARz7ux8lzGebw9tHzkQbn9lmVuU49VQJZgeW6pScJc0UV7M3jmX/TZ+BU3V1ivXZ98XGLzab2XvOVkrZ88+0jZ67sEF8nNuUpAsojn33kym5szSWWZQuLZr6zIKaEPSVqVjrjSXkMK5glam72Jq7/kWPp65hSWCxStoabaZ7Y80zziMPYvNTogDplvXcz6rhpFKfrTCg4UvbWBm9uWB927P1PBJeGAdz0KLFv14YhoiyxuCjubXsCBVCAOphst/3kHhfdg8x8inrHtVk7hXNWFBb8q9VzWl8GZpCRWbtMBN96eCHp2YlshyRNDvj28fDzqMfQ+bF6GY4sWX2db9IV0J2ymCDhkCUIcF3TsxRZBlj6DfGZ8iow5jx7A5DA7ZT61DLt9WO9xYnNfD+8uC7pgYbmTZLujW8eE+7jD2JjY/ZKQacacqmxIVad1WBTzWhAmZ9BVBPz8xFZDlc4IuG58Kpx3GzmDzMwh9UGGtKIvO+KGYEbhclR8U9NmJ4UeWbwu6c3z433IY40X5F3DOAvyhzJqaz3/gFjO52qLltycGHlk+FvSf4wP/tsPYBWzOW/4TTJ3PnO2PofsEqLBDUGNiKiDLJkEHx6fCJYexP2FzEQ40oEJX2nHCDjmeLHcTUrFU0OqJIUeWKkHL8yN3pY4sVmX4iwN8fpy6DNe2eNJvDmVB58cwODyR1+Dy+6Kguxyg25zBkOVbgj45PqNfdxi7gc3f4TgNRg/EjRY1NiDZ+Qs/q/ng+RGse1PQC3mg57/MrMi62lUKSe8I+stxbUYbX+yWg1o8GG9aZSCvTtyT8BwF586aQotO/WBinoQs1wW9Oi7ww1zqnfzgXQQ7/82vw1AJpLCdK/H75XJ4wHLTrwJ9GyibyH4sstsPr5BkChrJr1IRF1XEDZts2lINV6XEQc0ybNyMeK2ACY+VPbut4uqugecy3KRfAfou0AX/E3VR0nxBZ+RX18NFeTLVtdW5ykHnqdiUwylY6MzrpJVfGKnKPT/ir5SzbL4ciG9bcuBX9NDVh5bU5PlqMCPna6PgO3bAWzL9QPcf+K/fye9WpSFSEomravqveWnvHvDHiMItXWr9thfjSsGpvtrmMwOcGZGgHVzTrZl1jFRkzmT8wx++pc+bA2nUmof/NfANqU01Y5eMKiGLXzOs3y/trxkcbW3cwI+wox9N/5enZN0V/is37EuDu1a5tqvpZudlz62TF++8d3rxiZ+89MlNt9Y268jLwyOeG/8Fiw1UqBweAAA=";
}
