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
            " velocity " + getVelocityEstimate() + " noise " +
            getNoiseEstimate() + " ]";
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
            this.set$meanFreq((double) (1.0 / startInterval));
            this.set$lastUpdate((long)
                                  (java.lang.System.currentTimeMillis() - 1));
            this.set$firstUpdate((long) this.get$lastUpdate());
            return (fabric.metrics.util.RunningMetricStats) this.$getProxy();
        }

        /**
   * Reset estimation to just the startValue.
   */
        public void reset() {
            this.set$meanFreq((double) (1.0 / this.get$startInterval()));
            this.set$meanDelta((double) this.get$startDelta());
            this.set$varDelta((double) 0.0);
            this.set$varFreq((double) 0.0);
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
            return this.get$varFreq();
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
            return this.get$meanFreq();
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
                    this.set$meanDelta((double) p.getMeanDelta());
                    this.set$varDelta((double) p.getVarDelta());
                    this.set$meanFreq((double) p.getMeanFreq());
                    this.set$lastUpdate((long) p.getLastUpdate());
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

    public static final byte[] $classHash = new byte[] { 120, 19, 55, -21, -65,
    36, -48, -34, -111, -27, -70, -96, -39, 34, 18, 101, -84, 32, -53, -36, -92,
    -9, -86, 33, -5, -86, -66, 20, -48, 74, 100, -25 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1522347193000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVafZAURxXv3fs+jvvk6w44juMC8pFbwVQUTjGwfC3scVccH3ool7nZ3rvJzc4sM73HQkDAj4LKH6QwhIRSKC1RI8FQSUQtAUNVMAHxI7E0BismWEiFSKiIGLFiBN/r7r39mp3s/uEV3W+2u1/3771+7/XrGY7fICW2RVrDSr+mt7OtUWq3L1P6A8FuxbJpyK8rtr0WWvvUUcWBg9e+H2r2Em+QVKmKYRqaquh9hs1IdfAhZVjxGZT51q0JdGwkFSoyrlDsQUa8GxfHLdISNfWtA7rJ5CJZ8z8+23fgiU21zxWRml5Soxk9TGGa6jcNRuOsl1RFaKSfWvaiUIiGekmdQWmoh1qaomvbYKBp9JJ6WxswFBazqL2G2qY+jAPr7ViUWnzNRCPCNwG2FVOZaQH8WgE/xjTdF9Rs1hEkpWGN6iF7M/kSKQ6SkrCuDMDAccGEFD4+o28ZtsPwSg1gWmFFpQmW4iHNCDEyJZNjROK2VTAAWMsilA2aI0sVGwo0kHoBSVeMAV8PszRjAIaWmDFYhZGmnJPCoPKoog4pA7SPkQmZ47pFF4yq4GpBFkbGZg7jM8GeNWXsWcpu3Vj96X0PGysML/EA5hBVdcRfDkzNGUxraJha1FCpYKyaFTyojDuz10sIDB6bMViM+cn2mw/MaT57XoyZ6DCmq/8hqrI+9Wh/9auT/DPnFyGM8qhpa2gKaZLzXe2WPR3xKFj7uJEZsbM90Xl2zUuf33WMXveSygApVU09FgGrqlPNSFTTqbWcGtRSGA0FSAU1Qn7eHyBl8BzUDCpau8Jhm7IAKdZ5U6nJf4OKwjAFqqgMnjUjbCaeowob5M/xKCGkDArxwL+PETJtHDzXwPNpRtb5Bs0I9fXrMboFzNsHhSqWOugDv7U01Wdbqs+KGUyDQbIJrAiILeRfEzMMsKFO3oaeZbcDoOj/a+I4SlS7xeMBZU9RzRDtV2zYOWlFi7t1cJQVph6iVp+q7zsTIA1nDnFLqkDrt8GCua48sPuTMuNGKu+B2OKlN5/puyisEHmlKhmZLtC2S7Rip7PRAsAq9LR2iF3tELuOe+Lt/iOBp7lBldrc80bmrII5F0R1hYVNKxInHg8XcAzn5/ODHQxBfIEQUjWz54srH9zbWgQmHN1SjLsKQ9syHSoZhgLwpICX9Kk1e67968TBHWbStRhpy/L4bE702NZMbVmmSkMQEZPTz2pRTvad2dHmxWhTAYGQKWCqEFWaM9dI89yORBREbZQEySjUgaJjVyJ0VbJBy9ySbOFWUI1VvTAIVFYGQB5AP9MTPfz6b975BD9aErG2JiUo91DWkeLfOFkN9+S6pO7XWpTCuD8/2f3Y4zf2bOSKhxHTnBZsw9oPfq2AQ5vW185vvvTWm0d/701uFiOl0Vi/rqlxLkvdXfjzQLmDBZ0UG5BCqPbLANEyEiGiuPL0JDaIFTrEK4But60zImZIC2tKv07RUj6suWfuyXf31Yrt1qFFKM8icz56gmR742Ky6+Km2818Go+KZ1VSf8lhIgA2JGdeZFnKVsQR3/27yYdeVg6D5UP4srVtlEckwvVB+AbO47q4l9dzM/ruw6pVaGsSbx9tZx8Gy/BUTdpir+/4N5v8C68L3x+xRZxjqoPvr1dS3GTescj73tbSX3hJWS+p5Qe6YrD1CkQyMINeOJJtv2wMktFp/enHqzhLOkZ8bVKmH6Qsm+kFyZgDzzganyuF4QvDAUVgHCdLoTQQ4t0i6QrsbYhiPSbuIfxhAWeZxuvpWM3kiizCx1kMwxGmRIxUaJFIjOH+85VmM1KyKNi9YpGDwrstLQI+MyxPX7r3wCN32/cdEMYmUpRpWVlCKo9IU/g6o/licVhlqtsqnGPZ2yd2nHpqxx5xhNenH7hLjVjkh6/991ftT16+4BC6S0MmuB4VIQPr+9NV2QllLKjwlqSnHVS5wk2VWC3E6rMJ/VV3dwV6erpW920IrF7StYGzLZLiIlnCwDVNcRRkYqpETONFKaoS1HvXAVO3MyaINWVRSxuGwBEfmdSLk1bIye5IejtlUtjzYbRl/OHPCWsqlAkAq13S8Q6whLTTsOrJXh+5xklak7Z+RYQqxhKqM8UdQwuURuDuknS+A4YvuGJArk9JOjcNQ/mwYrlDqMVJWqE0AbMl6YMOEBTn3fHi49o0Y6kEP7RYnpJPhOUek3SHw7JhV8mRa7uksXTJUfvLLLrZHcIUKJOA+XlJjzhAGHKFgFyHJT2YBqEMlO+KgOv+HiiTgfeSpOccEETz1v1orvsA3rbA/N1lh50mzbDiPyX9q8PKMVfZkeuKpG+kyV4JVyG2LhoCp8WWzpwgJopNKK4VtMgpMGxzBYFcdyRNjwCjwpqVPwqwxuI5kk50QLHTFQVyNUnakG4GthKJ6iLny42gRbhh8ROS7nZA8FVXBMi1S9KtaQiqJAI4hkwrCSOeI97i46xkqOV/pUTctU5JejJlgZSchuDpNznXtZiffEe/fOBIqOu7c70yMVoKkZKZ0Xt1Okz1lKnq8RzNeu3SyV8GJLOcy9cnz/cPXR0Q5+iUjJUzR/+g8/iF5dPVr3tJ0Ug6k/UGIp2pIz2JqbQoi1nG2rRUpmVEVxUJvwKvLn5R0udStzJpAHwfH0nfx3LJ8qykT2eq2Tm5POTS9w2sDkBAZKZ4UcJHjWWklie5mOK1p3Q0Zl7jeOv+dAl3QrkfjOIfkm4vTEJkeVjSWG4Ji5IpiT9Z8fm/5yLwU1h9m5EZ4obbJm+4bZhatWXfcNuSeDOkhNwJ09LyIUn7CpMSWTZJ+rn89vGES9+zWB2DtAZfQTDHDGzY1EJOgqBBdsPzXElbChMEWaZI2pifIKdc+s5g9WMwyAEqrht8W51w49HYC/FxpqSjC8ONLFWSluaH+5xL30tYvcBIA+KmuqlqbOtSm2kRebo4ioCZlUbIqCuS/rIwEZDlgqQv5ifCr136fovVeXB9EGG1qdn0I/FDRkK2EVLdKml1YfiRZbSkZfnhf82l73WsXoUsB/AH0xOMXHa/G1beL+muwsAjy05Jt+YH/i2Xvr9g9SdIjQB8T0pK4IQcEiqyF07aEkGr/14YcmR5T9J38kN+zaXvb1hdgYMBkHemXWpymcyjgP07kj5aGHZk2Sfp3vyw33Tpu4XVu5AL8mhjuUOfBuUQ5OVU0lWFQUeWlZIuyQ3dk0yzxGn2gQv+D7F6Hy79sRFbfz4DOs8f26D8lJAxlYI2uFmMQ/KILO9Jei0v6J04q6coN3RPCTbeAYMX0BNXIUf406HcgEvUVyQdKAw+soQl7SsAfpULfAx1njLhr+tTbnJO8GdA+QBM/6qk5wuDjywvS3q2APhjXeDjqwxPrTD8ztS7sBP+WTAtpPhT5kvaWBB+zjJB0roC8De74MckxdMozqo1VKUG2wC3B3OLY9CslN7rGQUI/iDp6cKEQJZTkv4or8DjmeHSNxOrVoHfH7MW6dFBJWf0SbiwB+5WLXMkrS8MP7LUSVqZH/6Pu/TNw2o2I3WYK9A4cxeAh8+psHojkAWStroIkB0+OUtiiom5BUixosMc6XwXKTqwuo+/QYSUTQnxABRnpD77IoAv/ic6fIyTn4tV/zl69OqqOWNzfIibkPUBX/I9c6SmfPyRdX/kH5RGPgVXBEl5OKbrqS/IU55LAW9Y40qrEK/Lo1yeByD/dPhyB8k/ElSBZ6EY6WekOn0k49/S8Sl13DI4W8Q4/LWcq7wpWXGtwm2xXs7F74vikwDvyrovcrRNMQv/X8PxW+P/XVq+9jL/cARb0hJv+OT1F9peeXP/1Z9961JrPT3ecvGNo7ePTf3PsZ+PeWVl6O3/ARidD7ZvIQAA";
}
