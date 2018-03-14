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
                        fabric.worker.transaction.TransactionManager $tm692 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled695 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff693 = 1;
                        boolean $doBackoff694 = true;
                        boolean $retry689 = true;
                        $label687: for (boolean $commit688 = false; !$commit688;
                                        ) {
                            if ($backoffEnabled695) {
                                if ($doBackoff694) {
                                    if ($backoff693 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff693);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e690) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff693 < 5000) $backoff693 *= 2;
                                }
                                $doBackoff694 = $backoff693 <= 32 ||
                                                  !$doBackoff694;
                            }
                            $commit688 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.util.RunningMetricStats._Static.
                                  _Proxy.
                                  $instance.
                                  set$ALPHA((double) 0.001);
                            }
                            catch (final fabric.worker.RetryException $e690) {
                                $commit688 = false;
                                continue $label687;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e690) {
                                $commit688 = false;
                                fabric.common.TransactionID $currentTid691 =
                                  $tm692.getCurrentTid();
                                if ($e690.tid.isDescendantOf($currentTid691))
                                    continue $label687;
                                if ($currentTid691.parent != null) {
                                    $retry689 = false;
                                    throw $e690;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e690) {
                                $commit688 = false;
                                if ($tm692.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid691 =
                                  $tm692.getCurrentTid();
                                if ($e690.tid.isDescendantOf($currentTid691)) {
                                    $retry689 = true;
                                }
                                else if ($currentTid691.parent != null) {
                                    $retry689 = false;
                                    throw $e690;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e690) {
                                $commit688 = false;
                                if ($tm692.checkForStaleObjects())
                                    continue $label687;
                                $retry689 = false;
                                throw new fabric.worker.AbortException($e690);
                            }
                            finally {
                                if ($commit688) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e690) {
                                        $commit688 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e690) {
                                        $commit688 = false;
                                        fabric.common.TransactionID
                                          $currentTid691 =
                                          $tm692.getCurrentTid();
                                        if ($currentTid691 != null) {
                                            if ($e690.tid.equals(
                                                            $currentTid691) ||
                                                  !$e690.tid.
                                                  isDescendantOf(
                                                    $currentTid691)) {
                                                throw $e690;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit688 && $retry689) {
                                    {  }
                                    continue $label687;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 47, -6, -64, -61, -48,
    -112, -76, 48, -13, 21, 93, -19, -34, -104, 72, -49, 17, 45, -58, -2, 78,
    -46, 62, -106, 61, 121, 22, 29, -36, 51, -44, -68 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520977993000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
