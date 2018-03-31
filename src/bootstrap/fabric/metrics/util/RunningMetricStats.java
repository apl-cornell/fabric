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
    
    public double get$meanDelta();
    
    public double set$meanDelta(double val);
    
    public double postInc$meanDelta();
    
    public double postDec$meanDelta();
    
    public double get$varDelta();
    
    public double set$varDelta(double val);
    
    public double postInc$varDelta();
    
    public double postDec$varDelta();
    
    public double get$startDelta();
    
    public double set$startDelta(double val);
    
    public double postInc$startDelta();
    
    public double postDec$startDelta();
    
    public double get$meanFreq();
    
    public double set$meanFreq(double val);
    
    public double postInc$meanFreq();
    
    public double postDec$meanFreq();
    
    public double get$varFreq();
    
    public double set$varFreq(double val);
    
    public double postInc$varFreq();
    
    public double postDec$varFreq();
    
    public double get$startInterval();
    
    public double set$startInterval(double val);
    
    public double postInc$startInterval();
    
    public double postDec$startInterval();
    
    public long get$lastUpdate();
    
    public long set$lastUpdate(long val);
    
    public long postInc$lastUpdate();
    
    public long postDec$lastUpdate();
    
    public long get$firstUpdate();
    
    public long set$firstUpdate(long val);
    
    public long postInc$firstUpdate();
    
    public long postDec$firstUpdate();
    
    public long get$samples();
    
    public long set$samples(long val);
    
    public long postInc$samples();
    
    public long postDec$samples();
    
    public long get$samplesPrior();
    
    public long set$samplesPrior(long val);
    
    public long postInc$samplesPrior();
    
    public long postDec$samplesPrior();
    
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
   * @return the number of samples taken.
   */
    public long getSamples();
    
    /**
   * @return the current estimated meanDelta.
   */
    public double getMeanDelta();
    
    /**
   * @return the current estimated variance of the delta.
   */
    public double getVarDelta();
    
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
        
        public double get$meanDelta() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$meanDelta();
        }
        
        public double set$meanDelta(double val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$meanDelta(val);
        }
        
        public double postInc$meanDelta() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$meanDelta();
        }
        
        public double postDec$meanDelta() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$meanDelta();
        }
        
        public double get$varDelta() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$varDelta();
        }
        
        public double set$varDelta(double val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$varDelta(val);
        }
        
        public double postInc$varDelta() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$varDelta();
        }
        
        public double postDec$varDelta() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$varDelta();
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
        
        public double get$meanFreq() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$meanFreq();
        }
        
        public double set$meanFreq(double val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$meanFreq(val);
        }
        
        public double postInc$meanFreq() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$meanFreq();
        }
        
        public double postDec$meanFreq() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$meanFreq();
        }
        
        public double get$varFreq() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$varFreq();
        }
        
        public double set$varFreq(double val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$varFreq(val);
        }
        
        public double postInc$varFreq() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$varFreq();
        }
        
        public double postDec$varFreq() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$varFreq();
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
        
        public long get$firstUpdate() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$firstUpdate();
        }
        
        public long set$firstUpdate(long val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$firstUpdate(val);
        }
        
        public long postInc$firstUpdate() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$firstUpdate();
        }
        
        public long postDec$firstUpdate() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$firstUpdate();
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
        
        public long get$samplesPrior() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$samplesPrior();
        }
        
        public long set$samplesPrior(long val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$samplesPrior(val);
        }
        
        public long postInc$samplesPrior() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$samplesPrior();
        }
        
        public long postDec$samplesPrior() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$samplesPrior();
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
        
        public long getSamples() {
            return ((fabric.metrics.util.RunningMetricStats) fetch()).
              getSamples();
        }
        
        public double getMeanDelta() {
            return ((fabric.metrics.util.RunningMetricStats) fetch()).
              getMeanDelta();
        }
        
        public double getVarDelta() {
            return ((fabric.metrics.util.RunningMetricStats) fetch()).
              getVarDelta();
        }
        
        public void update(double arg1) {
            ((fabric.metrics.util.RunningMetricStats) fetch()).update(arg1);
        }
        
        public void preload(java.lang.String arg1) {
            ((fabric.metrics.util.RunningMetricStats) fetch()).preload(arg1);
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
        
        public double get$meanDelta() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.meanDelta;
        }
        
        public double set$meanDelta(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.meanDelta = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$meanDelta() {
            double tmp = this.get$meanDelta();
            this.set$meanDelta((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$meanDelta() {
            double tmp = this.get$meanDelta();
            this.set$meanDelta((double) (tmp - 1));
            return tmp;
        }
        
        private double meanDelta = 0.0;
        
        public double get$varDelta() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.varDelta;
        }
        
        public double set$varDelta(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.varDelta = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$varDelta() {
            double tmp = this.get$varDelta();
            this.set$varDelta((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$varDelta() {
            double tmp = this.get$varDelta();
            this.set$varDelta((double) (tmp - 1));
            return tmp;
        }
        
        private double varDelta = 0.0;
        
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
        
        public double get$meanFreq() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.meanFreq;
        }
        
        public double set$meanFreq(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.meanFreq = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$meanFreq() {
            double tmp = this.get$meanFreq();
            this.set$meanFreq((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$meanFreq() {
            double tmp = this.get$meanFreq();
            this.set$meanFreq((double) (tmp - 1));
            return tmp;
        }
        
        private double meanFreq = 0.0;
        
        public double get$varFreq() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.varFreq;
        }
        
        public double set$varFreq(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.varFreq = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$varFreq() {
            double tmp = this.get$varFreq();
            this.set$varFreq((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$varFreq() {
            double tmp = this.get$varFreq();
            this.set$varFreq((double) (tmp - 1));
            return tmp;
        }
        
        private double varFreq = 0.0;
        
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
        
        public long get$firstUpdate() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.firstUpdate;
        }
        
        public long set$firstUpdate(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.firstUpdate = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$firstUpdate() {
            long tmp = this.get$firstUpdate();
            this.set$firstUpdate((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$firstUpdate() {
            long tmp = this.get$firstUpdate();
            this.set$firstUpdate((long) (tmp - 1));
            return tmp;
        }
        
        private long firstUpdate;
        
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
        
        public long get$samplesPrior() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.samplesPrior;
        }
        
        public long set$samplesPrior(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.samplesPrior = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$samplesPrior() {
            long tmp = this.get$samplesPrior();
            this.set$samplesPrior((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$samplesPrior() {
            long tmp = this.get$samplesPrior();
            this.set$samplesPrior((long) (tmp - 1));
            return tmp;
        }
        
        private long samplesPrior = 0;
        
        public java.lang.String toString() {
            return "[" +
            " value " +
            this.get$value() +
            " samples " +
            this.get$samples() +
            " meanDelta " +
            this.get$meanDelta() +
            " varDelta " +
            this.get$varDelta() +
            " meanFreq " +
            ((fabric.metrics.util.RunningMetricStats._Impl)
               this.fetch()).getMeanFreq(java.lang.System.currentTimeMillis()) +
            " varFreq " +
            ((fabric.metrics.util.RunningMetricStats._Impl) this.fetch()).
              getVarFreq(java.lang.System.currentTimeMillis()) + " velocity " +
            getVelocityEstimate() + " noise " + getNoiseEstimate() + " ]";
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
            this.set$meanDelta((double) startDelta);
            this.set$meanFreq((double) 0.0);
            this.set$lastUpdate((long)
                                  (java.lang.System.currentTimeMillis() - 1));
            this.set$firstUpdate((long) this.get$lastUpdate());
            return (fabric.metrics.util.RunningMetricStats) this.$getProxy();
        }
        
        /**
   * Reset estimation to just the startValue.
   */
        public void reset() {
            this.set$meanFreq((double) 0.0);
            this.set$varFreq((double) 0.0);
            this.set$meanDelta((double) this.get$startDelta());
            this.set$varDelta((double) 0.0);
            this.set$samples((long) 0);
            this.set$samplesPrior((long) 0);
            this.set$lastUpdate((long)
                                  (java.lang.System.currentTimeMillis() - 1));
            this.set$firstUpdate((long) this.get$lastUpdate());
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
            if (this.get$samples() == 0) return 0.0;
            double delta = getMeanDelta();
            double freq =
              ((fabric.metrics.util.RunningMetricStats._Impl) this.fetch()).
              getMeanFreq(java.lang.System.currentTimeMillis());
            return delta * freq;
        }
        
        /**
   * Compute an estimated noise, assuming normal distribution of values and
   * exponential distribution of intervals.
   *
   * @return the current estimated noise.
   */
        public double getNoiseEstimate() {
            if (this.get$samples() <= 1) return 0.0;
            double meanDelta = getMeanDelta();
            double varDelta = getVarDelta();
            long curTime = java.lang.System.currentTimeMillis();
            double meanFreq = ((fabric.metrics.util.RunningMetricStats._Impl)
                                 this.fetch()).getMeanFreq(curTime);
            double varFreq = ((fabric.metrics.util.RunningMetricStats._Impl)
                                this.fetch()).getVarFreq(curTime);
            return varFreq * varDelta + meanFreq * varDelta + varFreq *
              meanDelta;
        }
        
        /**
   * @return the last update time.
   */
        public long getLastUpdate() { return this.get$lastUpdate(); }
        
        /**
   * @return the number of samples taken.
   */
        public long getSamples() { return this.get$samples(); }
        
        /**
   * @return the current estimated meanDelta.
   */
        public double getMeanDelta() {
            return this.get$samples() > 0
              ? this.get$meanDelta()
              : this.get$startDelta();
        }
        
        /**
   * @return the current estimated variance of the delta.
   */
        public double getVarDelta() {
            return this.get$samples() > 1 ? this.get$varDelta() : 0.0;
        }
        
        /**
   * Update with a new observation.
   *
   * @param val
   *        the newly observed value.
   */
        public void update(double newVal) {
            long curTime = java.lang.System.currentTimeMillis();
            double val = newVal - this.get$value();
            this.set$value((double) newVal);
            if (this.get$samples() == 0) {
                this.set$meanDelta((double) val);
                this.set$firstUpdate((long) curTime);
                this.set$lastUpdate((long) curTime);
            }
            else {
                double curAlphaDelta =
                  ((fabric.metrics.util.RunningMetricStats._Impl) this.fetch()).
                  getCurAlphaDelta();
                double meanDeltaPrev = this.get$meanDelta();
                this.set$meanDelta((double)
                                     (meanDeltaPrev + curAlphaDelta *
                                        (val - meanDeltaPrev)));
                this.set$varDelta((double)
                                    ((1.0 - curAlphaDelta) *
                                       this.get$varDelta() + curAlphaDelta *
                                       (val - meanDeltaPrev) *
                                       (val - this.get$meanDelta())));
                ((fabric.metrics.util.RunningMetricStats._Impl) this.fetch()).
                  updateFreq(curTime);
            }
            this.postInc$samples();
        }
        
        private void updateFreq(long time) {
            if (this.get$samples() == 0) { return; }
            long oldWindows =
              (this.get$lastUpdate() - this.get$firstUpdate()) /
              fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
              get$POISSON_WINDOW();
            long windowsUnprocessed =
              ((fabric.metrics.util.RunningMetricStats._Impl) this.fetch()).
              getRecentWindows(time);
            for (long windowSince = 1; windowSince < windowsUnprocessed;
                 windowSince++) {
                double alpha =
                  java.lang.Math.
                  max(
                    fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.get$ALPHA(
                                                                                      ),
                    1.0 / (oldWindows + windowSince));
                double meanFreqPrev = this.get$meanFreq();
                double varFreqPrev = this.get$varFreq();
                double unfinishedWindow = (this.get$samples() -
                                             this.get$samplesPrior()) /
                  (double)
                    fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.get$POISSON_WINDOW();
                this.set$samplesPrior((long) this.get$samples());
                if (alpha ==
                      fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                      get$ALPHA()) {
                    long windowsLeft = windowsUnprocessed - windowSince - 1;
                    this.set$meanFreq((double)
                                        (java.lang.Math.pow(1.0 - alpha,
                                                            windowsLeft) *
                                           ((1.0 - alpha) * meanFreqPrev +
                                              alpha * unfinishedWindow)));
                    this.set$varFreq(
                           (double)
                             (java.lang.Math.pow(1.0 - alpha, windowsLeft) *
                                ((1.0 - alpha) *
                                   (varFreqPrev +
                                      alpha *
                                      ((unfinishedWindow - meanFreqPrev) *
                                         (unfinishedWindow - meanFreqPrev))) +
                                   (1.0 -
                                      java.lang.Math.pow(1.0 - alpha,
                                                         windowsLeft)) *
                                   (((1.0 - alpha) * meanFreqPrev - alpha *
                                       unfinishedWindow) *
                                      ((1.0 - alpha) * meanFreqPrev - alpha *
                                         unfinishedWindow)))));
                    break;
                }
                else {
                    this.set$meanFreq((double)
                                        (this.get$meanFreq() +
                                           alpha *
                                           (unfinishedWindow -
                                              this.get$meanFreq())));
                    this.set$varFreq((double)
                                       ((1.0 - alpha) *
                                          this.get$varFreq() +
                                          alpha *
                                          (unfinishedWindow - meanFreqPrev) *
                                          (unfinishedWindow -
                                             this.get$meanFreq())));
                }
            }
            this.set$lastUpdate((long) time);
        }
        
        /**
   * Calculate the mean frequency, accounting for empty windows since the last
   * update.
   */
        private double getVarFreq(long time) {
            long totalWindows =
              (time - this.get$firstUpdate()) /
              fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
              get$POISSON_WINDOW();
            if (totalWindows <= 1) return 0.0;
            ((fabric.metrics.util.RunningMetricStats._Impl) this.fetch()).
              updateFreq(time);
            long oldWindows =
              (this.get$lastUpdate() - this.get$firstUpdate()) /
              fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
              get$POISSON_WINDOW();
            double
              alpha =
              java.lang.Math.
              max(
                fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                    get$ALPHA(),
                1.0 / (oldWindows + 1));
            long timeInCurWindow = time -
              (long)
                (time /
                   fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.get$POISSON_WINDOW()) *
              fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.get$POISSON_WINDOW();
            long timeLeftInCurWindow =
              fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
              get$POISSON_WINDOW() - timeInCurWindow;
            double interpolatedWindow =
              (this.get$meanFreq() * timeLeftInCurWindow +
                 (this.get$samples() - this.get$samplesPrior())) /
              fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
              get$POISSON_WINDOW();
            double meanFreqNext = this.get$meanFreq() + alpha *
              (interpolatedWindow - this.get$meanFreq());
            return (1.0 - alpha) * this.get$varFreq() + alpha *
              (interpolatedWindow - this.get$meanFreq()) *
              (interpolatedWindow - meanFreqNext);
        }
        
        /**
   * Calculate the mean frequency, accounting for empty windows since the last
   * update.
   */
        private double getMeanFreq(long time) {
            long totalWindows =
              (time - this.get$firstUpdate()) /
              fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
              get$POISSON_WINDOW();
            if (totalWindows == 0) return 0.0;
            ((fabric.metrics.util.RunningMetricStats._Impl) this.fetch()).
              updateFreq(time);
            long oldWindows =
              (this.get$lastUpdate() - this.get$firstUpdate()) /
              fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
              get$POISSON_WINDOW();
            double
              alpha =
              java.lang.Math.
              max(
                fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                    get$ALPHA(),
                1.0 / (oldWindows + 1));
            long timeInCurWindow = time -
              (long)
                (time /
                   fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.get$POISSON_WINDOW()) *
              fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.get$POISSON_WINDOW();
            long timeLeftInCurWindow =
              fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
              get$POISSON_WINDOW() - timeInCurWindow;
            double interpolatedWindow =
              (this.get$meanFreq() * timeLeftInCurWindow +
                 (this.get$samples() - this.get$samplesPrior())) /
              fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
              get$POISSON_WINDOW();
            return this.get$meanFreq() + alpha *
              (interpolatedWindow - this.get$meanFreq());
        }
        
        /**
   * Return the total number of windows we've seen since the last update.
   */
        private long getRecentWindows(long time) {
            if (this.get$samples() == 0) return 0;
            return java.lang.Math.
              max(
                0,
                time /
                    fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                    get$POISSON_WINDOW() - this.get$lastUpdate() /
                    fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.get$POISSON_WINDOW());
        }
        
        private double getCurAlphaDelta() {
            return java.lang.Math.
              max(
                fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                    get$ALPHA(),
                1.0 / this.get$samples());
        }
        
        private double getNextAlphaDelta() {
            return java.lang.Math.
              max(
                fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                    get$ALPHA(),
                1.0 / (this.get$samples() + 1));
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
                if (!fabric.lang.Object._Proxy.idEquals(p, null)) {
                    this.set$samples((long) p.getSamples());
                    this.set$samplesPrior((long) this.get$samples());
                    this.set$meanDelta((double) p.getMeanDelta());
                    this.set$varDelta((double) p.getVarDelta());
                    this.set$meanFreq((double) p.getMeanFreq());
                    this.set$varFreq((double) p.getVarFreq());
                    this.set$lastUpdate((long) p.getLastUpdate());
                    this.set$firstUpdate((long) p.getFirstUpdate());
                }
            }
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
            out.writeDouble(this.meanDelta);
            out.writeDouble(this.varDelta);
            out.writeDouble(this.startDelta);
            out.writeDouble(this.meanFreq);
            out.writeDouble(this.varFreq);
            out.writeDouble(this.startInterval);
            out.writeLong(this.lastUpdate);
            out.writeLong(this.firstUpdate);
            out.writeLong(this.samples);
            out.writeLong(this.samplesPrior);
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
            this.meanDelta = in.readDouble();
            this.varDelta = in.readDouble();
            this.startDelta = in.readDouble();
            this.meanFreq = in.readDouble();
            this.varFreq = in.readDouble();
            this.startInterval = in.readDouble();
            this.lastUpdate = in.readLong();
            this.firstUpdate = in.readLong();
            this.samples = in.readLong();
            this.samplesPrior = in.readLong();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.RunningMetricStats._Impl src =
              (fabric.metrics.util.RunningMetricStats._Impl) other;
            this.value = src.value;
            this.meanDelta = src.meanDelta;
            this.varDelta = src.varDelta;
            this.startDelta = src.startDelta;
            this.meanFreq = src.meanFreq;
            this.varFreq = src.varFreq;
            this.startInterval = src.startInterval;
            this.lastUpdate = src.lastUpdate;
            this.firstUpdate = src.firstUpdate;
            this.samples = src.samples;
            this.samplesPrior = src.samplesPrior;
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
                        fabric.worker.transaction.TransactionManager $tm107 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled110 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff108 = 1;
                        boolean $doBackoff109 = true;
                        boolean $retry104 = true;
                        $label102: for (boolean $commit103 = false; !$commit103;
                                        ) {
                            if ($backoffEnabled110) {
                                if ($doBackoff109) {
                                    if ($backoff108 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff108);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e105) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff108 < 5000) $backoff108 *= 2;
                                }
                                $doBackoff109 = $backoff108 <= 32 ||
                                                  !$doBackoff109;
                            }
                            $commit103 = true;
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
                            catch (final fabric.worker.RetryException $e105) {
                                $commit103 = false;
                                continue $label102;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e105) {
                                $commit103 = false;
                                fabric.common.TransactionID $currentTid106 =
                                  $tm107.getCurrentTid();
                                if ($e105.tid.isDescendantOf($currentTid106))
                                    continue $label102;
                                if ($currentTid106.parent != null) {
                                    $retry104 = false;
                                    throw $e105;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e105) {
                                $commit103 = false;
                                if ($tm107.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid106 =
                                  $tm107.getCurrentTid();
                                if ($e105.tid.isDescendantOf($currentTid106)) {
                                    $retry104 = true;
                                }
                                else if ($currentTid106.parent != null) {
                                    $retry104 = false;
                                    throw $e105;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e105) {
                                $commit103 = false;
                                if ($tm107.checkForStaleObjects())
                                    continue $label102;
                                $retry104 = false;
                                throw new fabric.worker.AbortException($e105);
                            }
                            finally {
                                if ($commit103) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e105) {
                                        $commit103 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e105) {
                                        $commit103 = false;
                                        fabric.common.TransactionID
                                          $currentTid106 =
                                          $tm107.getCurrentTid();
                                        if ($currentTid106 != null) {
                                            if ($e105.tid.equals(
                                                            $currentTid106) ||
                                                  !$e105.tid.
                                                  isDescendantOf(
                                                    $currentTid106)) {
                                                throw $e105;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit103 && $retry104) {
                                    {  }
                                    continue $label102;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 68, 120, -68, -4, 17,
    81, 4, 106, -69, -48, 92, 126, 51, 13, 7, 106, -5, 79, 65, 76, 127, 41, 0,
    -34, 20, 84, -74, -45, 40, -3, -78, 104 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1522535838000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
