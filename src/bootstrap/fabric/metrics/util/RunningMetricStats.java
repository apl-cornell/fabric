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
                        fabric.worker.transaction.TransactionManager $tm723 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled726 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff724 = 1;
                        boolean $doBackoff725 = true;
                        boolean $retry720 = true;
                        $label718: for (boolean $commit719 = false; !$commit719;
                                        ) {
                            if ($backoffEnabled726) {
                                if ($doBackoff725) {
                                    if ($backoff724 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff724);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e721) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff724 < 5000) $backoff724 *= 2;
                                }
                                $doBackoff725 = $backoff724 <= 32 ||
                                                  !$doBackoff725;
                            }
                            $commit719 = true;
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
                            catch (final fabric.worker.RetryException $e721) {
                                $commit719 = false;
                                continue $label718;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e721) {
                                $commit719 = false;
                                fabric.common.TransactionID $currentTid722 =
                                  $tm723.getCurrentTid();
                                if ($e721.tid.isDescendantOf($currentTid722))
                                    continue $label718;
                                if ($currentTid722.parent != null) {
                                    $retry720 = false;
                                    throw $e721;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e721) {
                                $commit719 = false;
                                if ($tm723.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid722 =
                                  $tm723.getCurrentTid();
                                if ($e721.tid.isDescendantOf($currentTid722)) {
                                    $retry720 = true;
                                }
                                else if ($currentTid722.parent != null) {
                                    $retry720 = false;
                                    throw $e721;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e721) {
                                $commit719 = false;
                                if ($tm723.checkForStaleObjects())
                                    continue $label718;
                                $retry720 = false;
                                throw new fabric.worker.AbortException($e721);
                            }
                            finally {
                                if ($commit719) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e721) {
                                        $commit719 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e721) {
                                        $commit719 = false;
                                        fabric.common.TransactionID
                                          $currentTid722 =
                                          $tm723.getCurrentTid();
                                        if ($currentTid722 != null) {
                                            if ($e721.tid.equals(
                                                            $currentTid722) ||
                                                  !$e721.tid.
                                                  isDescendantOf(
                                                    $currentTid722)) {
                                                throw $e721;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit719 && $retry720) {
                                    {  }
                                    continue $label718;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 76, -45, -59, -66, 27,
    78, -73, -33, -35, 60, 49, -34, 1, -121, -41, 42, 77, 34, 5, 83, -78, -81,
    -92, 8, 103, -117, 87, 90, 107, 78, 48, 35 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524505527000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
