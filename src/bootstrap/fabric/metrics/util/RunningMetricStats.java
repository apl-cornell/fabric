package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
    
    public double get$meanDeltaPrev();
    
    public double set$meanDeltaPrev(double val);
    
    public double postInc$meanDeltaPrev();
    
    public double postDec$meanDeltaPrev();
    
    public double get$varDeltaPrev();
    
    public double set$varDeltaPrev(double val);
    
    public double postInc$varDeltaPrev();
    
    public double postDec$varDeltaPrev();
    
    public double get$startDelta();
    
    public double set$startDelta(double val);
    
    public double postInc$startDelta();
    
    public double postDec$startDelta();
    
    public double get$meanInterval();
    
    public double set$meanInterval(double val);
    
    public double postInc$meanInterval();
    
    public double postDec$meanInterval();
    
    public double get$meanIntervalPrev();
    
    public double set$meanIntervalPrev(double val);
    
    public double postInc$meanIntervalPrev();
    
    public double postDec$meanIntervalPrev();
    
    public double get$startInterval();
    
    public double set$startInterval(double val);
    
    public double postInc$startInterval();
    
    public double postDec$startInterval();
    
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
   * @return the number of samples taken.
   */
    public long getSamples();
    
    /**
   * @return the current estimated meanDelta.
   */
    public double getMeanDelta();
    
    /**
   * @return the current estimated meanInterval.
   */
    public double getMeanInterval();
    
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
        
        public double get$meanDeltaPrev() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$meanDeltaPrev();
        }
        
        public double set$meanDeltaPrev(double val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$meanDeltaPrev(val);
        }
        
        public double postInc$meanDeltaPrev() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$meanDeltaPrev();
        }
        
        public double postDec$meanDeltaPrev() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$meanDeltaPrev();
        }
        
        public double get$varDeltaPrev() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$varDeltaPrev();
        }
        
        public double set$varDeltaPrev(double val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$varDeltaPrev(val);
        }
        
        public double postInc$varDeltaPrev() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$varDeltaPrev();
        }
        
        public double postDec$varDeltaPrev() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$varDeltaPrev();
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
        
        public double get$meanInterval() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$meanInterval();
        }
        
        public double set$meanInterval(double val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$meanInterval(val);
        }
        
        public double postInc$meanInterval() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$meanInterval();
        }
        
        public double postDec$meanInterval() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$meanInterval();
        }
        
        public double get$meanIntervalPrev() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              get$meanIntervalPrev();
        }
        
        public double set$meanIntervalPrev(double val) {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              set$meanIntervalPrev(val);
        }
        
        public double postInc$meanIntervalPrev() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postInc$meanIntervalPrev();
        }
        
        public double postDec$meanIntervalPrev() {
            return ((fabric.metrics.util.RunningMetricStats._Impl) fetch()).
              postDec$meanIntervalPrev();
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
        
        public long getSamples() {
            return ((fabric.metrics.util.RunningMetricStats) fetch()).
              getSamples();
        }
        
        public double getMeanDelta() {
            return ((fabric.metrics.util.RunningMetricStats) fetch()).
              getMeanDelta();
        }
        
        public double getMeanInterval() {
            return ((fabric.metrics.util.RunningMetricStats) fetch()).
              getMeanInterval();
        }
        
        public double getVarDelta() {
            return ((fabric.metrics.util.RunningMetricStats) fetch()).
              getVarDelta();
        }
        
        public void update(double arg1) {
            ((fabric.metrics.util.RunningMetricStats) fetch()).update(arg1);
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
        
        public double get$meanDeltaPrev() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.meanDeltaPrev;
        }
        
        public double set$meanDeltaPrev(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.meanDeltaPrev = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$meanDeltaPrev() {
            double tmp = this.get$meanDeltaPrev();
            this.set$meanDeltaPrev((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$meanDeltaPrev() {
            double tmp = this.get$meanDeltaPrev();
            this.set$meanDeltaPrev((double) (tmp - 1));
            return tmp;
        }
        
        private double meanDeltaPrev = 0.0;
        
        public double get$varDeltaPrev() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.varDeltaPrev;
        }
        
        public double set$varDeltaPrev(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.varDeltaPrev = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$varDeltaPrev() {
            double tmp = this.get$varDeltaPrev();
            this.set$varDeltaPrev((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$varDeltaPrev() {
            double tmp = this.get$varDeltaPrev();
            this.set$varDeltaPrev((double) (tmp - 1));
            return tmp;
        }
        
        private double varDeltaPrev = 0.0;
        
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
        
        public double get$meanInterval() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.meanInterval;
        }
        
        public double set$meanInterval(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.meanInterval = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$meanInterval() {
            double tmp = this.get$meanInterval();
            this.set$meanInterval((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$meanInterval() {
            double tmp = this.get$meanInterval();
            this.set$meanInterval((double) (tmp - 1));
            return tmp;
        }
        
        private double meanInterval = 0.0;
        
        public double get$meanIntervalPrev() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.meanIntervalPrev;
        }
        
        public double set$meanIntervalPrev(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.meanIntervalPrev = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$meanIntervalPrev() {
            double tmp = this.get$meanIntervalPrev();
            this.set$meanIntervalPrev((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$meanIntervalPrev() {
            double tmp = this.get$meanIntervalPrev();
            this.set$meanIntervalPrev((double) (tmp - 1));
            return tmp;
        }
        
        private double meanIntervalPrev = 0.0;
        
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
            this.get$samples() + " meanDelta " + this.get$meanDelta() +
            " varDelta " + this.get$varDelta() + " meanInterval " +
            this.get$meanInterval() + " velocity " + getVelocityEstimate() +
            " noise " + getNoiseEstimate() + " ]";
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
            this.set$meanDeltaPrev((double) startDelta);
            this.set$meanDelta((double) startDelta);
            this.set$meanIntervalPrev((double) startInterval);
            this.set$meanInterval((double) startInterval);
            this.set$lastUpdate((long)
                                  (java.lang.System.currentTimeMillis() - 1));
            return (fabric.metrics.util.RunningMetricStats) this.$getProxy();
        }
        
        /**
   * Reset estimation to just the startValue.
   */
        public void reset() {
            this.set$meanIntervalPrev((double) this.get$startInterval());
            this.set$meanInterval((double) this.get$startInterval());
            this.set$meanDeltaPrev((double) this.get$startDelta());
            this.set$meanDelta((double) this.get$startDelta());
            this.set$varDeltaPrev((double) 0.0);
            this.set$varDelta((double) 0.0);
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
            if (this.get$samples() == 0) return 0.0;
            long intervalSince = java.lang.System.currentTimeMillis() -
              this.get$lastUpdate();
            double curInterval = getMeanInterval();
            double curBias = getMeanDelta();
            double newInterval = curInterval;
            double newBias = curBias;
            if (intervalSince > 0) {
                double nextAlpha =
                  ((fabric.metrics.util.RunningMetricStats._Impl) this.fetch()).
                  getNextAlpha();
                newInterval = curInterval + nextAlpha *
                                (intervalSince - curInterval);
                newBias = curBias * (1.0 - nextAlpha);
            }
            return newBias / newInterval;
        }
        
        /**
   * Compute an estimated noise, assuming normal distribution of values and
   * exponential distribution of intervals.
   *
   * @return the current estimated noise.
   */
        public double getNoiseEstimate() {
            if (this.get$samples() <= 1) return 0.0;
            long intervalSince = java.lang.System.currentTimeMillis() -
              this.get$lastUpdate();
            double curInterval = getMeanInterval();
            double curBias = getMeanDelta();
            double curBiasVar = getVarDelta();
            double newInterval = curInterval;
            double newBias = curBias;
            double newBiasVar = curBiasVar;
            if (intervalSince > 0) {
                double nextAlpha =
                  ((fabric.metrics.util.RunningMetricStats._Impl) this.fetch()).
                  getNextAlpha();
                newInterval = curInterval + nextAlpha *
                                (intervalSince - curInterval);
                newBias = curBias * (1.0 - nextAlpha);
                newBiasVar = (1.0 - nextAlpha) * curBiasVar + nextAlpha *
                               (curBias * newBias);
            }
            double tSqrd = newInterval * newInterval;
            return 1 / newInterval + newBiasVar / tSqrd;
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
   * @return the current estimated meanInterval.
   */
        public double getMeanInterval() {
            return this.get$samples() > 0
              ? this.get$meanInterval()
              : this.get$startInterval();
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
            this.postInc$samples();
            long curTime = java.lang.System.currentTimeMillis();
            double interval = curTime - this.get$lastUpdate();
            double val = newVal - this.get$value();
            this.set$lastUpdate((long) curTime);
            this.set$value((double) newVal);
            if (this.get$samples() == 1) {
                this.set$meanInterval((double) interval);
                this.set$meanIntervalPrev((double) interval);
                this.set$meanDelta((double) val);
                this.set$meanDeltaPrev((double) val);
                this.set$varDeltaPrev((double) 0.0);
            }
            else {
                double curAlpha =
                  ((fabric.metrics.util.RunningMetricStats._Impl) this.fetch()).
                  getCurAlpha();
                this.set$meanInterval((double)
                                        (this.get$meanIntervalPrev() +
                                           curAlpha *
                                           (interval -
                                              this.get$meanIntervalPrev())));
                this.set$meanIntervalPrev((double) this.get$meanInterval());
                this.set$meanDelta((double)
                                     (this.get$meanDeltaPrev() + curAlpha *
                                        (val - this.get$meanDeltaPrev())));
                this.set$varDelta((double)
                                    ((1.0 - curAlpha) *
                                       this.get$varDeltaPrev() + curAlpha *
                                       (val - this.get$meanDeltaPrev()) *
                                       (val - this.get$meanDelta())));
                this.set$meanDeltaPrev((double) this.get$meanDelta());
                this.set$varDeltaPrev((double) this.get$varDelta());
            }
        }
        
        private double getCurAlpha() {
            return java.lang.Math.
              max(
                fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                    get$ALPHA(),
                1 / (this.get$samples() - 1));
        }
        
        private double getNextAlpha() {
            return java.lang.Math.
              max(
                fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                    get$ALPHA(),
                1 / this.get$samples());
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
            out.writeDouble(this.meanDeltaPrev);
            out.writeDouble(this.varDeltaPrev);
            out.writeDouble(this.startDelta);
            out.writeDouble(this.meanInterval);
            out.writeDouble(this.meanIntervalPrev);
            out.writeDouble(this.startInterval);
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
            this.meanDelta = in.readDouble();
            this.varDelta = in.readDouble();
            this.meanDeltaPrev = in.readDouble();
            this.varDeltaPrev = in.readDouble();
            this.startDelta = in.readDouble();
            this.meanInterval = in.readDouble();
            this.meanIntervalPrev = in.readDouble();
            this.startInterval = in.readDouble();
            this.lastUpdate = in.readLong();
            this.samples = in.readLong();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.RunningMetricStats._Impl src =
              (fabric.metrics.util.RunningMetricStats._Impl) other;
            this.value = src.value;
            this.meanDelta = src.meanDelta;
            this.varDelta = src.varDelta;
            this.meanDeltaPrev = src.meanDeltaPrev;
            this.varDeltaPrev = src.varDeltaPrev;
            this.startDelta = src.startDelta;
            this.meanInterval = src.meanInterval;
            this.meanIntervalPrev = src.meanIntervalPrev;
            this.startInterval = src.startInterval;
            this.lastUpdate = src.lastUpdate;
            this.samples = src.samples;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public double get$ALPHA();
        
        public double set$ALPHA(double val);
        
        public double postInc$ALPHA();
        
        public double postDec$ALPHA();
        
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
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeDouble(this.ALPHA);
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
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.RunningMetricStats._Static.
                         _Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm5 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled8 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff6 = 1;
                        boolean $doBackoff7 = true;
                        boolean $retry2 = true;
                        $label0: for (boolean $commit1 = false; !$commit1; ) {
                            if ($backoffEnabled8) {
                                if ($doBackoff7) {
                                    if ($backoff6 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff6);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e3) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff6 < 5000) $backoff6 *= 2;
                                }
                                $doBackoff7 = $backoff6 <= 32 || !$doBackoff7;
                            }
                            $commit1 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.util.RunningMetricStats._Static.
                                  _Proxy.
                                  $instance.
                                  set$ALPHA((double) 0.001);
                            }
                            catch (final fabric.worker.RetryException $e3) {
                                $commit1 = false;
                                continue $label0;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e3) {
                                $commit1 = false;
                                fabric.common.TransactionID $currentTid4 =
                                  $tm5.getCurrentTid();
                                if ($e3.tid.isDescendantOf($currentTid4))
                                    continue $label0;
                                if ($currentTid4.parent != null) {
                                    $retry2 = false;
                                    throw $e3;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e3) {
                                $commit1 = false;
                                if ($tm5.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid4 =
                                  $tm5.getCurrentTid();
                                if ($e3.tid.isDescendantOf($currentTid4)) {
                                    $retry2 = true;
                                }
                                else if ($currentTid4.parent != null) {
                                    $retry2 = false;
                                    throw $e3;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e3) {
                                $commit1 = false;
                                if ($tm5.checkForStaleObjects())
                                    continue $label0;
                                $retry2 = false;
                                throw new fabric.worker.AbortException($e3);
                            }
                            finally {
                                if ($commit1) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e3) {
                                        $commit1 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e3) {
                                        $commit1 = false;
                                        fabric.common.TransactionID
                                          $currentTid4 = $tm5.getCurrentTid();
                                        if ($currentTid4 != null) {
                                            if ($e3.tid.equals($currentTid4) ||
                                                  !$e3.tid.isDescendantOf(
                                                             $currentTid4)) {
                                                throw $e3;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit1 && $retry2) {
                                    {  }
                                    continue $label0;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -24, 127, -103, 72,
    -32, -100, -89, -101, 17, -108, 115, 12, -94, 118, 122, -54, -58, -110, -58,
    -13, 68, -83, 49, -100, 59, 66, 43, -7, 65, -93, -2, -62 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1522158672000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
