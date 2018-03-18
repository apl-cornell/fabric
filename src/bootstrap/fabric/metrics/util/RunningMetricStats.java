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
                        fabric.worker.transaction.TransactionManager $tm720 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled723 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff721 = 1;
                        boolean $doBackoff722 = true;
                        boolean $retry717 = true;
                        $label715: for (boolean $commit716 = false; !$commit716;
                                        ) {
                            if ($backoffEnabled723) {
                                if ($doBackoff722) {
                                    if ($backoff721 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff721);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e718) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff721 < 5000) $backoff721 *= 2;
                                }
                                $doBackoff722 = $backoff721 <= 32 ||
                                                  !$doBackoff722;
                            }
                            $commit716 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.util.RunningMetricStats._Static.
                                  _Proxy.
                                  $instance.
                                  set$ALPHA((double) 0.001);
                            }
                            catch (final fabric.worker.RetryException $e718) {
                                $commit716 = false;
                                continue $label715;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e718) {
                                $commit716 = false;
                                fabric.common.TransactionID $currentTid719 =
                                  $tm720.getCurrentTid();
                                if ($e718.tid.isDescendantOf($currentTid719))
                                    continue $label715;
                                if ($currentTid719.parent != null) {
                                    $retry717 = false;
                                    throw $e718;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e718) {
                                $commit716 = false;
                                if ($tm720.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid719 =
                                  $tm720.getCurrentTid();
                                if ($e718.tid.isDescendantOf($currentTid719)) {
                                    $retry717 = true;
                                }
                                else if ($currentTid719.parent != null) {
                                    $retry717 = false;
                                    throw $e718;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e718) {
                                $commit716 = false;
                                if ($tm720.checkForStaleObjects())
                                    continue $label715;
                                $retry717 = false;
                                throw new fabric.worker.AbortException($e718);
                            }
                            finally {
                                if ($commit716) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e718) {
                                        $commit716 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e718) {
                                        $commit716 = false;
                                        fabric.common.TransactionID
                                          $currentTid719 =
                                          $tm720.getCurrentTid();
                                        if ($currentTid719 != null) {
                                            if ($e718.tid.equals(
                                                            $currentTid719) ||
                                                  !$e718.tid.
                                                  isDescendantOf(
                                                    $currentTid719)) {
                                                throw $e718;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit716 && $retry717) {
                                    {  }
                                    continue $label715;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -7, 89, 33, -16, -79,
    44, -39, -125, 94, 60, 8, 75, 57, -127, -32, 119, 6, -50, 87, 2, -75, 89,
    76, -19, -78, -39, -58, 81, 41, -29, 75, 54 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521305458000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
