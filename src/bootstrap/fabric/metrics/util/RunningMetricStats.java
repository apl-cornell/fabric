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
                        fabric.worker.transaction.TransactionManager $tm646 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled649 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff647 = 1;
                        boolean $doBackoff648 = true;
                        boolean $retry643 = true;
                        $label641: for (boolean $commit642 = false; !$commit642;
                                        ) {
                            if ($backoffEnabled649) {
                                if ($doBackoff648) {
                                    if ($backoff647 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff647);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e644) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff647 < 5000) $backoff647 *= 2;
                                }
                                $doBackoff648 = $backoff647 <= 32 ||
                                                  !$doBackoff648;
                            }
                            $commit642 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.util.RunningMetricStats._Static.
                                  _Proxy.
                                  $instance.
                                  set$ALPHA((double) 0.001);
                            }
                            catch (final fabric.worker.RetryException $e644) {
                                $commit642 = false;
                                continue $label641;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e644) {
                                $commit642 = false;
                                fabric.common.TransactionID $currentTid645 =
                                  $tm646.getCurrentTid();
                                if ($e644.tid.isDescendantOf($currentTid645))
                                    continue $label641;
                                if ($currentTid645.parent != null) {
                                    $retry643 = false;
                                    throw $e644;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e644) {
                                $commit642 = false;
                                if ($tm646.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid645 =
                                  $tm646.getCurrentTid();
                                if ($e644.tid.isDescendantOf($currentTid645)) {
                                    $retry643 = true;
                                }
                                else if ($currentTid645.parent != null) {
                                    $retry643 = false;
                                    throw $e644;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e644) {
                                $commit642 = false;
                                if ($tm646.checkForStaleObjects())
                                    continue $label641;
                                $retry643 = false;
                                throw new fabric.worker.AbortException($e644);
                            }
                            finally {
                                if ($commit642) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e644) {
                                        $commit642 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e644) {
                                        $commit642 = false;
                                        fabric.common.TransactionID
                                          $currentTid645 =
                                          $tm646.getCurrentTid();
                                        if ($currentTid645 != null) {
                                            if ($e644.tid.equals(
                                                            $currentTid645) ||
                                                  !$e644.tid.
                                                  isDescendantOf(
                                                    $currentTid645)) {
                                                throw $e644;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit642 && $retry643) {
                                    {  }
                                    continue $label641;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 99, -45, -93, -5, 114,
    116, -67, 54, 20, 45, -109, -113, -62, -6, 35, -106, 49, 91, -66, 32, 25,
    -51, -78, 26, -24, 74, 49, -81, 37, 41, 3, -48 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520104105000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
