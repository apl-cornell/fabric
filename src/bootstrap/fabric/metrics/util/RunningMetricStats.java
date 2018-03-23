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
    
    /** @return the ALPHA learning parameter for these statistics. */
    public double getAlpha();
    
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
        
        public double getAlpha() {
            return ((fabric.metrics.util.RunningMetricStats) fetch()).getAlpha(
                                                                        );
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
                newInterval =
                  curInterval +
                    fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                    get$ALPHA() * (intervalSince - curInterval);
                newBias =
                  curBias *
                    (1.0 -
                       fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                       get$ALPHA());
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
            if (this.get$samples() == 0) return 0.0;
            long intervalSince = java.lang.System.currentTimeMillis() -
              this.get$lastUpdate();
            double curInterval = getMeanInterval();
            double curBias = getMeanDelta();
            double curBiasVar = getVarDelta();
            double newInterval = curInterval;
            double newBias = curBias;
            double newBiasVar = curBiasVar;
            if (intervalSince > 0) {
                newInterval =
                  curInterval +
                    fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                    get$ALPHA() * (intervalSince - curInterval);
                newBias =
                  curBias *
                    (1.0 -
                       fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                       get$ALPHA());
                newBiasVar =
                  (1.0 -
                     fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                     get$ALPHA()) *
                    curBiasVar +
                    fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                    get$ALPHA() *
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
        public double getMeanDelta() { return this.get$meanDelta(); }
        
        /**
   * @return the current estimated meanInterval.
   */
        public double getMeanInterval() { return this.get$meanInterval(); }
        
        /**
   * @return the current estimated variance of the delta.
   */
        public double getVarDelta() { return this.get$varDelta(); }
        
        /**
   * Update with a new observation.
   *
   * @param val
   *        the newly observed value.
   */
        public void update(double newVal) {
            long curTime = java.lang.System.currentTimeMillis();
            double interval = curTime - this.get$lastUpdate();
            this.set$lastUpdate((long) curTime);
            this.
              set$meanInterval(
                (double)
                  (this.get$meanIntervalPrev() +
                     fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                     get$ALPHA() * (interval - this.get$meanIntervalPrev())));
            this.set$meanIntervalPrev((double) this.get$meanInterval());
            double val = newVal - this.get$value();
            this.set$value((double) newVal);
            this.
              set$meanDelta(
                (double)
                  (this.get$meanDeltaPrev() +
                     fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                     get$ALPHA() * (val - this.get$meanDeltaPrev())));
            this.
              set$varDelta(
                (double)
                  ((1.0 -
                      fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                      get$ALPHA()) *
                     this.
                     get$varDeltaPrev() +
                     fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
                     get$ALPHA() *
                     (val - this.get$meanDeltaPrev()) *
                     (val - this.get$meanDelta())));
            this.set$meanDeltaPrev((double) this.get$meanDelta());
            this.set$varDeltaPrev((double) this.get$varDelta());
            this.postInc$samples();
        }
        
        /** @return the ALPHA learning parameter for these statistics. */
        public double getAlpha() {
            return fabric.metrics.util.RunningMetricStats._Static._Proxy.$instance.
              get$ALPHA();
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
                        fabric.worker.transaction.TransactionManager $tm252 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled255 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff253 = 1;
                        boolean $doBackoff254 = true;
                        boolean $retry249 = true;
                        $label247: for (boolean $commit248 = false; !$commit248;
                                        ) {
                            if ($backoffEnabled255) {
                                if ($doBackoff254) {
                                    if ($backoff253 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff253);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e250) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff253 < 5000) $backoff253 *= 2;
                                }
                                $doBackoff254 = $backoff253 <= 32 ||
                                                  !$doBackoff254;
                            }
                            $commit248 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.util.RunningMetricStats._Static.
                                  _Proxy.
                                  $instance.
                                  set$ALPHA((double) 0.001);
                            }
                            catch (final fabric.worker.RetryException $e250) {
                                $commit248 = false;
                                continue $label247;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e250) {
                                $commit248 = false;
                                fabric.common.TransactionID $currentTid251 =
                                  $tm252.getCurrentTid();
                                if ($e250.tid.isDescendantOf($currentTid251))
                                    continue $label247;
                                if ($currentTid251.parent != null) {
                                    $retry249 = false;
                                    throw $e250;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e250) {
                                $commit248 = false;
                                if ($tm252.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid251 =
                                  $tm252.getCurrentTid();
                                if ($e250.tid.isDescendantOf($currentTid251)) {
                                    $retry249 = true;
                                }
                                else if ($currentTid251.parent != null) {
                                    $retry249 = false;
                                    throw $e250;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e250) {
                                $commit248 = false;
                                if ($tm252.checkForStaleObjects())
                                    continue $label247;
                                $retry249 = false;
                                throw new fabric.worker.AbortException($e250);
                            }
                            finally {
                                if ($commit248) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e250) {
                                        $commit248 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e250) {
                                        $commit248 = false;
                                        fabric.common.TransactionID
                                          $currentTid251 =
                                          $tm252.getCurrentTid();
                                        if ($currentTid251 != null) {
                                            if ($e250.tid.equals(
                                                            $currentTid251) ||
                                                  !$e250.tid.
                                                  isDescendantOf(
                                                    $currentTid251)) {
                                                throw $e250;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit248 && $retry249) {
                                    {  }
                                    continue $label247;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -101, -83, -23, 64,
    -87, -87, -104, 103, 110, 13, 30, 28, -16, 97, 108, 15, -53, -79, 96, 52,
    -115, -124, 31, 78, 85, 95, -96, 24, -93, 18, 67, -105 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521834488000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
