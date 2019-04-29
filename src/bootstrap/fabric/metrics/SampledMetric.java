package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.util.List;
import fabric.metrics.util.Observer;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.RunningMetricStats;
import fabric.worker.metrics.StatsMap;
import fabric.common.ConfigProperties;
import fabric.common.exceptions.InternalError;
import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * A {@link Metric} that is directly updated by applications with new sample
 * values to use for its value.
 * <p>
 * In a transaction which has written the value being measured,
 * {@link #takeSample(double,long)} must be called to ensure that
 * {@link Observer}s of this {@link Metric} are aware of the change and the
 * transaction context performs the proper book-keeping.
 */
public interface SampledMetric extends fabric.metrics.Metric {
    public long get$key();
    
    public long set$key(long val);
    
    public long postInc$key();
    
    public long postDec$key();
    
    /**
   * @param store
   *            the {@link Store} that holds this {@link Metric}
   * @param key
   *            the key the {@link Store} associates with this
   *            {@link SampledMetric}, so it can be easily retrieved later for
   *            configuration purposes.
   * @param init
   *        the initial value of this {@link Metric}
   */
    public fabric.metrics.SampledMetric fabric$metrics$SampledMetric$(
      long key, double init);
    
    public boolean getUsePreset();
    
    public double get$presetR();
    
    public double set$presetR(double val);
    
    public double postInc$presetR();
    
    public double postDec$presetR();
    
    public double getPresetR();
    
    public double get$presetB();
    
    public double set$presetB(double val);
    
    public double postInc$presetB();
    
    public double postDec$presetB();
    
    public double getPresetB();
    
    public double get$presetV();
    
    public double set$presetV(double val);
    
    public double postInc$presetV();
    
    public double postDec$presetV();
    
    public double getPresetV();
    
    public double get$presetN();
    
    public double set$presetN(double val);
    
    public double postInc$presetN();
    
    public double postDec$presetN();
    
    public double getPresetN();
    
    /**
   * Update to a new value of the metric. The sample is assumed to be taken at
   * the time of the call.
   *
   * @param sample
   *        the value the metric is updating to.
   */
    public void takeSample(double sample);
    
    /**
   * Alias for takeSample.
   */
    public void setValue(double value);
    
    /**
   * Update to a new value of the metric.
   *
   * @param sample
   *        the value the metric is updating to.
   * @param time
   *        the time the sample occurred.
   */
    public void takeSample(double sample, long time);
    
    public double computeValue(fabric.worker.metrics.StatsMap weakStats);
    
    public long computeSamples(fabric.worker.metrics.StatsMap weakStats);
    
    public long computeLastUpdate(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeUpdateInterval(
      fabric.worker.metrics.StatsMap weakStats);
    
    public double computeVelocity(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoise(fabric.worker.metrics.StatsMap weakStats);
    
    public java.lang.String toString();
    
    public boolean isSingleStore();
    
    public fabric.worker.metrics.RunningMetricStats get$stats();
    
    public fabric.worker.metrics.RunningMetricStats set$stats(fabric.worker.metrics.RunningMetricStats val);
    
    /**
   * Updates the velocity and interval estimates with the given observation.
   *
   * @param newVal
   *        the new value of the measured quantity
   * @param eventTime
   *        the time, in milliseconds, this update happened
   */
    public void updateEstimates(double newVal, long eventTime);
    
    public double value(fabric.worker.metrics.StatsMap weakStats);
    
    public long samples(fabric.worker.metrics.StatsMap weakStats);
    
    public long lastUpdate(fabric.worker.metrics.StatsMap weakStats);
    
    public double updateInterval(fabric.worker.metrics.StatsMap weakStats);
    
    public double velocity(fabric.worker.metrics.StatsMap weakStats);
    
    public double noise(fabric.worker.metrics.StatsMap weakStats);
    
    public static class _Proxy extends fabric.metrics.Metric._Proxy
      implements fabric.metrics.SampledMetric {
        public long get$key() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$key();
        }
        
        public long set$key(long val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$key(val);
        }
        
        public long postInc$key() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).postInc$key();
        }
        
        public long postDec$key() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).postDec$key();
        }
        
        public double get$presetR() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$presetR();
        }
        
        public double set$presetR(double val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$presetR(
                                                                    val);
        }
        
        public double postInc$presetR() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              postInc$presetR();
        }
        
        public double postDec$presetR() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              postDec$presetR();
        }
        
        public double get$presetB() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$presetB();
        }
        
        public double set$presetB(double val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$presetB(
                                                                    val);
        }
        
        public double postInc$presetB() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              postInc$presetB();
        }
        
        public double postDec$presetB() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              postDec$presetB();
        }
        
        public double get$presetV() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$presetV();
        }
        
        public double set$presetV(double val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$presetV(
                                                                    val);
        }
        
        public double postInc$presetV() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              postInc$presetV();
        }
        
        public double postDec$presetV() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              postDec$presetV();
        }
        
        public double get$presetN() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$presetN();
        }
        
        public double set$presetN(double val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$presetN(
                                                                    val);
        }
        
        public double postInc$presetN() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              postInc$presetN();
        }
        
        public double postDec$presetN() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              postDec$presetN();
        }
        
        public fabric.worker.metrics.RunningMetricStats get$stats() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$stats();
        }
        
        public fabric.worker.metrics.RunningMetricStats set$stats(
          fabric.worker.metrics.RunningMetricStats val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$stats(
                                                                    val);
        }
        
        public fabric.metrics.SampledMetric fabric$metrics$SampledMetric$(
          long arg1, double arg2) {
            return ((fabric.metrics.SampledMetric) fetch()).
              fabric$metrics$SampledMetric$(arg1, arg2);
        }
        
        public void takeSample(double arg1) {
            ((fabric.metrics.SampledMetric) fetch()).takeSample(arg1);
        }
        
        public void setValue(double arg1) {
            ((fabric.metrics.SampledMetric) fetch()).setValue(arg1);
        }
        
        public void takeSample(double arg1, long arg2) {
            ((fabric.metrics.SampledMetric) fetch()).takeSample(arg1, arg2);
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
    
    public static class _Impl extends fabric.metrics.Metric._Impl
      implements fabric.metrics.SampledMetric {
        public long get$key() { return this.key; }
        
        public long set$key(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.key = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$key() {
            long tmp = this.get$key();
            this.set$key((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$key() {
            long tmp = this.get$key();
            this.set$key((long) (tmp - 1));
            return tmp;
        }
        
        private long key;
        
        /**
   * @param store
   *            the {@link Store} that holds this {@link Metric}
   * @param key
   *            the key the {@link Store} associates with this
   *            {@link SampledMetric}, so it can be easily retrieved later for
   *            configuration purposes.
   * @param init
   *        the initial value of this {@link Metric}
   */
        public fabric.metrics.SampledMetric fabric$metrics$SampledMetric$(
          long key, double init) {
            fabric.lang.security.Label lbl =
              fabric.lang.security.LabelUtil._Impl.noComponents();
            fabric.worker.Store s = $getStore();
            this.set$key((long) key);
            this.
              set$stats(
                fabric.worker.metrics.RunningMetricStats.
                    createRunningMetricStats(init, 0, 1));
            fabric.common.ConfigProperties config =
              fabric.worker.Worker.getWorker().config;
            if (getUsePreset()) {
                java.lang.String presetKey = "" + key + "@" + s.name();
                this.set$presetR(
                       (double)
                         (config.rates.containsKey(presetKey)
                            ? ((java.lang.Double)
                                 config.rates.get(presetKey)).doubleValue()
                            : 0.0));
                this.set$presetB(
                       (double)
                         (config.bounds.containsKey(presetKey)
                            ? ((java.lang.Double)
                                 config.bounds.get(presetKey)).doubleValue()
                            : 1.0));
                this.set$presetV(
                       (double)
                         (config.velocities.containsKey(presetKey)
                            ? ((java.lang.Double)
                                 config.velocities.get(presetKey)).doubleValue()
                            : 0.0));
                this.set$presetN(
                       (double)
                         (config.noises.containsKey(presetKey)
                            ? ((java.lang.Double)
                                 config.noises.get(presetKey)).doubleValue()
                            : 0.0));
            } else {
                this.set$presetR((double) 0.0);
                this.set$presetB((double) 0.0);
                this.set$presetV((double) 0.0);
                this.set$presetN((double) 0.0);
            }
            fabric$metrics$Metric$();
            return (fabric.metrics.SampledMetric) this.$getProxy();
        }
        
        public boolean getUsePreset() {
            return fabric.worker.Worker.getWorker().config.usePreset;
        }
        
        public double get$presetR() { return this.presetR; }
        
        public double set$presetR(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.presetR = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$presetR() {
            double tmp = this.get$presetR();
            this.set$presetR((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$presetR() {
            double tmp = this.get$presetR();
            this.set$presetR((double) (tmp - 1));
            return tmp;
        }
        
        public double presetR;
        
        public double getPresetR() { return this.get$presetR(); }
        
        public double get$presetB() { return this.presetB; }
        
        public double set$presetB(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.presetB = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$presetB() {
            double tmp = this.get$presetB();
            this.set$presetB((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$presetB() {
            double tmp = this.get$presetB();
            this.set$presetB((double) (tmp - 1));
            return tmp;
        }
        
        public double presetB;
        
        public double getPresetB() { return this.get$presetB(); }
        
        public double get$presetV() { return this.presetV; }
        
        public double set$presetV(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.presetV = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$presetV() {
            double tmp = this.get$presetV();
            this.set$presetV((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$presetV() {
            double tmp = this.get$presetV();
            this.set$presetV((double) (tmp - 1));
            return tmp;
        }
        
        public double presetV;
        
        public double getPresetV() { return this.get$presetV(); }
        
        public double get$presetN() { return this.presetN; }
        
        public double set$presetN(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.presetN = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$presetN() {
            double tmp = this.get$presetN();
            this.set$presetN((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$presetN() {
            double tmp = this.get$presetN();
            this.set$presetN((double) (tmp - 1));
            return tmp;
        }
        
        public double presetN;
        
        public double getPresetN() { return this.get$presetN(); }
        
        /**
   * Update to a new value of the metric. The sample is assumed to be taken at
   * the time of the call.
   *
   * @param sample
   *        the value the metric is updating to.
   */
        public void takeSample(double sample) {
            takeSample(sample, java.lang.System.currentTimeMillis());
        }
        
        /**
   * Alias for takeSample.
   */
        public void setValue(double value) { takeSample(value); }
        
        /**
   * Update to a new value of the metric.
   *
   * @param sample
   *        the value the metric is updating to.
   * @param time
   *        the time the sample occurred.
   */
        public void takeSample(double sample, long time) {
            if (this.get$stats().getValue() != sample) {
                updateEstimates(sample, time);
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerSample((fabric.metrics.SampledMetric)
                                   this.$getProxy());
            }
        }
        
        public double computeValue(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_computeValue((fabric.metrics.SampledMetric)
                                    this.$getProxy(), weakStats);
        }
        
        private static double static_computeValue(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            double result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.worker.metrics.RunningMetricStats preloaded =
                  tmp.get$stats().preload(tmp.get$key());
                if (!fabric.lang.Object._Proxy.idEquals(preloaded,
                                                        tmp.get$stats()))
                    tmp.set$stats(preloaded);
                result = tmp.get$stats().getValue();
            }
            else {
                {
                    double result$var230 = result;
                    fabric.worker.transaction.TransactionManager $tm237 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled240 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff238 = 1;
                    boolean $doBackoff239 = true;
                    boolean $retry233 = true;
                    boolean $keepReads234 = false;
                    $label231: for (boolean $commit232 = false; !$commit232; ) {
                        if ($backoffEnabled240) {
                            if ($doBackoff239) {
                                if ($backoff238 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff238));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e235) {
                                            
                                        }
                                    }
                                }
                                if ($backoff238 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff238 =
                                      java.lang.Math.
                                        min(
                                          $backoff238 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff239 = $backoff238 <= 32 || !$doBackoff239;
                        }
                        $commit232 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.worker.metrics.RunningMetricStats preloaded =
                              tmp.get$stats().preload(tmp.get$key());
                            if (!fabric.lang.Object._Proxy.idEquals(
                                                             preloaded,
                                                             tmp.get$stats()))
                                tmp.set$stats(preloaded);
                            result = tmp.get$stats().getValue();
                        }
                        catch (final fabric.worker.RetryException $e235) {
                            $commit232 = false;
                            continue $label231;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e235) {
                            $commit232 = false;
                            $retry233 = false;
                            $keepReads234 = $e235.keepReads;
                            fabric.common.TransactionID $currentTid236 =
                              $tm237.getCurrentTid();
                            if ($e235.tid == null ||
                                  !$e235.tid.isDescendantOf($currentTid236)) {
                                throw $e235;
                            }
                            throw new fabric.worker.UserAbortException($e235);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e235) {
                            $commit232 = false;
                            fabric.common.TransactionID $currentTid236 =
                              $tm237.getCurrentTid();
                            if ($e235.tid.isDescendantOf($currentTid236))
                                continue $label231;
                            if ($currentTid236.parent != null) {
                                $retry233 = false;
                                throw $e235;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e235) {
                            $commit232 = false;
                            $retry233 = false;
                            if ($tm237.inNestedTxn()) { $keepReads234 = true; }
                            throw $e235;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid236 =
                              $tm237.getCurrentTid();
                            if ($commit232) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e235) {
                                    $commit232 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e235) {
                                    $commit232 = false;
                                    $retry233 = false;
                                    $keepReads234 = $e235.keepReads;
                                    if ($e235.tid ==
                                          null ||
                                          !$e235.tid.isDescendantOf(
                                                       $currentTid236))
                                        throw $e235;
                                    throw new fabric.worker.UserAbortException(
                                            $e235);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e235) {
                                    $commit232 = false;
                                    $currentTid236 = $tm237.getCurrentTid();
                                    if ($currentTid236 != null) {
                                        if ($e235.tid.equals($currentTid236) ||
                                              !$e235.tid.isDescendantOf(
                                                           $currentTid236)) {
                                            throw $e235;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm237.inNestedTxn() &&
                                      $tm237.checkForStaleObjects()) {
                                    $retry233 = true;
                                    $keepReads234 = false;
                                }
                                if ($keepReads234) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e235) {
                                        $currentTid236 = $tm237.getCurrentTid();
                                        if ($currentTid236 != null &&
                                              ($e235.tid.equals($currentTid236) || !$e235.tid.isDescendantOf($currentTid236))) {
                                            throw $e235;
                                        } else {
                                            $retry233 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit232) {
                                { result = result$var230; }
                                if ($retry233) { continue $label231; }
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        public long computeSamples(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_computeSamples((fabric.metrics.SampledMetric)
                                      this.$getProxy(), weakStats);
        }
        
        private static long static_computeSamples(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.worker.metrics.RunningMetricStats preloaded =
                  tmp.get$stats().preload(tmp.get$key());
                if (!fabric.lang.Object._Proxy.idEquals(preloaded,
                                                        tmp.get$stats()))
                    tmp.set$stats(preloaded);
                return tmp.get$stats().getSamples();
            }
            else {
                long rtn = 0;
                {
                    long rtn$var241 = rtn;
                    fabric.worker.transaction.TransactionManager $tm248 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled251 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff249 = 1;
                    boolean $doBackoff250 = true;
                    boolean $retry244 = true;
                    boolean $keepReads245 = false;
                    $label242: for (boolean $commit243 = false; !$commit243; ) {
                        if ($backoffEnabled251) {
                            if ($doBackoff250) {
                                if ($backoff249 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff249));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e246) {
                                            
                                        }
                                    }
                                }
                                if ($backoff249 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff249 =
                                      java.lang.Math.
                                        min(
                                          $backoff249 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff250 = $backoff249 <= 32 || !$doBackoff250;
                        }
                        $commit243 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.worker.metrics.RunningMetricStats preloaded =
                              tmp.get$stats().preload(tmp.get$key());
                            if (!fabric.lang.Object._Proxy.idEquals(
                                                             preloaded,
                                                             tmp.get$stats()))
                                tmp.set$stats(preloaded);
                            rtn = tmp.get$stats().getSamples();
                        }
                        catch (final fabric.worker.RetryException $e246) {
                            $commit243 = false;
                            continue $label242;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e246) {
                            $commit243 = false;
                            $retry244 = false;
                            $keepReads245 = $e246.keepReads;
                            fabric.common.TransactionID $currentTid247 =
                              $tm248.getCurrentTid();
                            if ($e246.tid == null ||
                                  !$e246.tid.isDescendantOf($currentTid247)) {
                                throw $e246;
                            }
                            throw new fabric.worker.UserAbortException($e246);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e246) {
                            $commit243 = false;
                            fabric.common.TransactionID $currentTid247 =
                              $tm248.getCurrentTid();
                            if ($e246.tid.isDescendantOf($currentTid247))
                                continue $label242;
                            if ($currentTid247.parent != null) {
                                $retry244 = false;
                                throw $e246;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e246) {
                            $commit243 = false;
                            $retry244 = false;
                            if ($tm248.inNestedTxn()) { $keepReads245 = true; }
                            throw $e246;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid247 =
                              $tm248.getCurrentTid();
                            if ($commit243) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e246) {
                                    $commit243 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e246) {
                                    $commit243 = false;
                                    $retry244 = false;
                                    $keepReads245 = $e246.keepReads;
                                    if ($e246.tid ==
                                          null ||
                                          !$e246.tid.isDescendantOf(
                                                       $currentTid247))
                                        throw $e246;
                                    throw new fabric.worker.UserAbortException(
                                            $e246);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e246) {
                                    $commit243 = false;
                                    $currentTid247 = $tm248.getCurrentTid();
                                    if ($currentTid247 != null) {
                                        if ($e246.tid.equals($currentTid247) ||
                                              !$e246.tid.isDescendantOf(
                                                           $currentTid247)) {
                                            throw $e246;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm248.inNestedTxn() &&
                                      $tm248.checkForStaleObjects()) {
                                    $retry244 = true;
                                    $keepReads245 = false;
                                }
                                if ($keepReads245) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e246) {
                                        $currentTid247 = $tm248.getCurrentTid();
                                        if ($currentTid247 != null &&
                                              ($e246.tid.equals($currentTid247) || !$e246.tid.isDescendantOf($currentTid247))) {
                                            throw $e246;
                                        } else {
                                            $retry244 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit243) {
                                { rtn = rtn$var241; }
                                if ($retry244) { continue $label242; }
                            }
                        }
                    }
                }
                return rtn;
            }
        }
        
        public long computeLastUpdate(
          fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_computeLastUpdate((fabric.metrics.SampledMetric)
                                         this.$getProxy(), weakStats);
        }
        
        private static long static_computeLastUpdate(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.worker.metrics.RunningMetricStats preloaded =
                  tmp.get$stats().preload(tmp.get$key());
                if (!fabric.lang.Object._Proxy.idEquals(preloaded,
                                                        tmp.get$stats()))
                    tmp.set$stats(preloaded);
                return tmp.get$stats().getLastUpdate();
            }
            else {
                long rtn = 0;
                {
                    long rtn$var252 = rtn;
                    fabric.worker.transaction.TransactionManager $tm259 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled262 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff260 = 1;
                    boolean $doBackoff261 = true;
                    boolean $retry255 = true;
                    boolean $keepReads256 = false;
                    $label253: for (boolean $commit254 = false; !$commit254; ) {
                        if ($backoffEnabled262) {
                            if ($doBackoff261) {
                                if ($backoff260 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff260));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e257) {
                                            
                                        }
                                    }
                                }
                                if ($backoff260 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff260 =
                                      java.lang.Math.
                                        min(
                                          $backoff260 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff261 = $backoff260 <= 32 || !$doBackoff261;
                        }
                        $commit254 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.worker.metrics.RunningMetricStats preloaded =
                              tmp.get$stats().preload(tmp.get$key());
                            if (!fabric.lang.Object._Proxy.idEquals(
                                                             preloaded,
                                                             tmp.get$stats()))
                                tmp.set$stats(preloaded);
                            rtn = tmp.get$stats().getLastUpdate();
                        }
                        catch (final fabric.worker.RetryException $e257) {
                            $commit254 = false;
                            continue $label253;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e257) {
                            $commit254 = false;
                            $retry255 = false;
                            $keepReads256 = $e257.keepReads;
                            fabric.common.TransactionID $currentTid258 =
                              $tm259.getCurrentTid();
                            if ($e257.tid == null ||
                                  !$e257.tid.isDescendantOf($currentTid258)) {
                                throw $e257;
                            }
                            throw new fabric.worker.UserAbortException($e257);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e257) {
                            $commit254 = false;
                            fabric.common.TransactionID $currentTid258 =
                              $tm259.getCurrentTid();
                            if ($e257.tid.isDescendantOf($currentTid258))
                                continue $label253;
                            if ($currentTid258.parent != null) {
                                $retry255 = false;
                                throw $e257;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e257) {
                            $commit254 = false;
                            $retry255 = false;
                            if ($tm259.inNestedTxn()) { $keepReads256 = true; }
                            throw $e257;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid258 =
                              $tm259.getCurrentTid();
                            if ($commit254) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e257) {
                                    $commit254 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e257) {
                                    $commit254 = false;
                                    $retry255 = false;
                                    $keepReads256 = $e257.keepReads;
                                    if ($e257.tid ==
                                          null ||
                                          !$e257.tid.isDescendantOf(
                                                       $currentTid258))
                                        throw $e257;
                                    throw new fabric.worker.UserAbortException(
                                            $e257);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e257) {
                                    $commit254 = false;
                                    $currentTid258 = $tm259.getCurrentTid();
                                    if ($currentTid258 != null) {
                                        if ($e257.tid.equals($currentTid258) ||
                                              !$e257.tid.isDescendantOf(
                                                           $currentTid258)) {
                                            throw $e257;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm259.inNestedTxn() &&
                                      $tm259.checkForStaleObjects()) {
                                    $retry255 = true;
                                    $keepReads256 = false;
                                }
                                if ($keepReads256) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e257) {
                                        $currentTid258 = $tm259.getCurrentTid();
                                        if ($currentTid258 != null &&
                                              ($e257.tid.equals($currentTid258) || !$e257.tid.isDescendantOf($currentTid258))) {
                                            throw $e257;
                                        } else {
                                            $retry255 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit254) {
                                { rtn = rtn$var252; }
                                if ($retry255) { continue $label253; }
                            }
                        }
                    }
                }
                return rtn;
            }
        }
        
        public double computeUpdateInterval(
          fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_computeUpdateInterval((fabric.metrics.SampledMetric)
                                             this.$getProxy(), weakStats);
        }
        
        private static double static_computeUpdateInterval(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.worker.metrics.RunningMetricStats preloaded =
                  tmp.get$stats().preload(tmp.get$key());
                if (!fabric.lang.Object._Proxy.idEquals(preloaded,
                                                        tmp.get$stats()))
                    tmp.set$stats(preloaded);
                return tmp.get$stats().getIntervalEstimate();
            }
            else {
                double rtn = 0;
                {
                    double rtn$var263 = rtn;
                    fabric.worker.transaction.TransactionManager $tm270 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled273 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff271 = 1;
                    boolean $doBackoff272 = true;
                    boolean $retry266 = true;
                    boolean $keepReads267 = false;
                    $label264: for (boolean $commit265 = false; !$commit265; ) {
                        if ($backoffEnabled273) {
                            if ($doBackoff272) {
                                if ($backoff271 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff271));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e268) {
                                            
                                        }
                                    }
                                }
                                if ($backoff271 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff271 =
                                      java.lang.Math.
                                        min(
                                          $backoff271 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff272 = $backoff271 <= 32 || !$doBackoff272;
                        }
                        $commit265 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.worker.metrics.RunningMetricStats preloaded =
                              tmp.get$stats().preload(tmp.get$key());
                            if (!fabric.lang.Object._Proxy.idEquals(
                                                             preloaded,
                                                             tmp.get$stats()))
                                tmp.set$stats(preloaded);
                            rtn = tmp.get$stats().getIntervalEstimate();
                        }
                        catch (final fabric.worker.RetryException $e268) {
                            $commit265 = false;
                            continue $label264;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e268) {
                            $commit265 = false;
                            $retry266 = false;
                            $keepReads267 = $e268.keepReads;
                            fabric.common.TransactionID $currentTid269 =
                              $tm270.getCurrentTid();
                            if ($e268.tid == null ||
                                  !$e268.tid.isDescendantOf($currentTid269)) {
                                throw $e268;
                            }
                            throw new fabric.worker.UserAbortException($e268);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e268) {
                            $commit265 = false;
                            fabric.common.TransactionID $currentTid269 =
                              $tm270.getCurrentTid();
                            if ($e268.tid.isDescendantOf($currentTid269))
                                continue $label264;
                            if ($currentTid269.parent != null) {
                                $retry266 = false;
                                throw $e268;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e268) {
                            $commit265 = false;
                            $retry266 = false;
                            if ($tm270.inNestedTxn()) { $keepReads267 = true; }
                            throw $e268;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid269 =
                              $tm270.getCurrentTid();
                            if ($commit265) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e268) {
                                    $commit265 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e268) {
                                    $commit265 = false;
                                    $retry266 = false;
                                    $keepReads267 = $e268.keepReads;
                                    if ($e268.tid ==
                                          null ||
                                          !$e268.tid.isDescendantOf(
                                                       $currentTid269))
                                        throw $e268;
                                    throw new fabric.worker.UserAbortException(
                                            $e268);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e268) {
                                    $commit265 = false;
                                    $currentTid269 = $tm270.getCurrentTid();
                                    if ($currentTid269 != null) {
                                        if ($e268.tid.equals($currentTid269) ||
                                              !$e268.tid.isDescendantOf(
                                                           $currentTid269)) {
                                            throw $e268;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm270.inNestedTxn() &&
                                      $tm270.checkForStaleObjects()) {
                                    $retry266 = true;
                                    $keepReads267 = false;
                                }
                                if ($keepReads267) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e268) {
                                        $currentTid269 = $tm270.getCurrentTid();
                                        if ($currentTid269 != null &&
                                              ($e268.tid.equals($currentTid269) || !$e268.tid.isDescendantOf($currentTid269))) {
                                            throw $e268;
                                        } else {
                                            $retry266 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit265) {
                                { rtn = rtn$var263; }
                                if ($retry266) { continue $label264; }
                            }
                        }
                    }
                }
                return rtn;
            }
        }
        
        public double computeVelocity(
          fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_computeVelocity((fabric.metrics.SampledMetric)
                                       this.$getProxy(), weakStats);
        }
        
        private static double static_computeVelocity(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.getUsePreset()) {
                    return tmp.get$presetV();
                }
                else {
                    fabric.worker.metrics.RunningMetricStats preloaded =
                      tmp.get$stats().preload(tmp.get$key());
                    if (!fabric.lang.Object._Proxy.idEquals(preloaded,
                                                            tmp.get$stats()))
                        tmp.set$stats(preloaded);
                    return tmp.get$stats().getVelocityEstimate();
                }
            }
            else {
                double rtn = 0;
                {
                    double rtn$var274 = rtn;
                    fabric.worker.transaction.TransactionManager $tm281 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled284 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff282 = 1;
                    boolean $doBackoff283 = true;
                    boolean $retry277 = true;
                    boolean $keepReads278 = false;
                    $label275: for (boolean $commit276 = false; !$commit276; ) {
                        if ($backoffEnabled284) {
                            if ($doBackoff283) {
                                if ($backoff282 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff282));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e279) {
                                            
                                        }
                                    }
                                }
                                if ($backoff282 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff282 =
                                      java.lang.Math.
                                        min(
                                          $backoff282 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff283 = $backoff282 <= 32 || !$doBackoff283;
                        }
                        $commit276 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.getUsePreset()) {
                                rtn = tmp.get$presetV();
                            }
                            else {
                                fabric.worker.metrics.RunningMetricStats
                                  preloaded =
                                  tmp.get$stats().preload(tmp.get$key());
                                if (!fabric.lang.Object._Proxy.idEquals(
                                                                 preloaded,
                                                                 tmp.get$stats(
                                                                       )))
                                    tmp.set$stats(preloaded);
                                rtn = tmp.get$stats().getVelocityEstimate();
                            }
                        }
                        catch (final fabric.worker.RetryException $e279) {
                            $commit276 = false;
                            continue $label275;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e279) {
                            $commit276 = false;
                            $retry277 = false;
                            $keepReads278 = $e279.keepReads;
                            fabric.common.TransactionID $currentTid280 =
                              $tm281.getCurrentTid();
                            if ($e279.tid == null ||
                                  !$e279.tid.isDescendantOf($currentTid280)) {
                                throw $e279;
                            }
                            throw new fabric.worker.UserAbortException($e279);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e279) {
                            $commit276 = false;
                            fabric.common.TransactionID $currentTid280 =
                              $tm281.getCurrentTid();
                            if ($e279.tid.isDescendantOf($currentTid280))
                                continue $label275;
                            if ($currentTid280.parent != null) {
                                $retry277 = false;
                                throw $e279;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e279) {
                            $commit276 = false;
                            $retry277 = false;
                            if ($tm281.inNestedTxn()) { $keepReads278 = true; }
                            throw $e279;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid280 =
                              $tm281.getCurrentTid();
                            if ($commit276) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e279) {
                                    $commit276 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e279) {
                                    $commit276 = false;
                                    $retry277 = false;
                                    $keepReads278 = $e279.keepReads;
                                    if ($e279.tid ==
                                          null ||
                                          !$e279.tid.isDescendantOf(
                                                       $currentTid280))
                                        throw $e279;
                                    throw new fabric.worker.UserAbortException(
                                            $e279);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e279) {
                                    $commit276 = false;
                                    $currentTid280 = $tm281.getCurrentTid();
                                    if ($currentTid280 != null) {
                                        if ($e279.tid.equals($currentTid280) ||
                                              !$e279.tid.isDescendantOf(
                                                           $currentTid280)) {
                                            throw $e279;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm281.inNestedTxn() &&
                                      $tm281.checkForStaleObjects()) {
                                    $retry277 = true;
                                    $keepReads278 = false;
                                }
                                if ($keepReads278) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e279) {
                                        $currentTid280 = $tm281.getCurrentTid();
                                        if ($currentTid280 != null &&
                                              ($e279.tid.equals($currentTid280) || !$e279.tid.isDescendantOf($currentTid280))) {
                                            throw $e279;
                                        } else {
                                            $retry277 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit276) {
                                { rtn = rtn$var274; }
                                if ($retry277) { continue $label275; }
                            }
                        }
                    }
                }
                return rtn;
            }
        }
        
        public double computeNoise(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_computeNoise((fabric.metrics.SampledMetric)
                                    this.$getProxy(), weakStats);
        }
        
        private static double static_computeNoise(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.getUsePreset()) {
                    return tmp.get$presetN();
                }
                else {
                    fabric.worker.metrics.RunningMetricStats preloaded =
                      tmp.get$stats().preload(tmp.get$key());
                    if (!fabric.lang.Object._Proxy.idEquals(preloaded,
                                                            tmp.get$stats()))
                        tmp.set$stats(preloaded);
                    return tmp.get$stats().getNoiseEstimate();
                }
            }
            else {
                double rtn = 0;
                {
                    double rtn$var285 = rtn;
                    fabric.worker.transaction.TransactionManager $tm292 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled295 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff293 = 1;
                    boolean $doBackoff294 = true;
                    boolean $retry288 = true;
                    boolean $keepReads289 = false;
                    $label286: for (boolean $commit287 = false; !$commit287; ) {
                        if ($backoffEnabled295) {
                            if ($doBackoff294) {
                                if ($backoff293 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff293));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e290) {
                                            
                                        }
                                    }
                                }
                                if ($backoff293 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff293 =
                                      java.lang.Math.
                                        min(
                                          $backoff293 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff294 = $backoff293 <= 32 || !$doBackoff294;
                        }
                        $commit287 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.getUsePreset()) {
                                rtn = tmp.get$presetN();
                            }
                            else {
                                fabric.worker.metrics.RunningMetricStats
                                  preloaded =
                                  tmp.get$stats().preload(tmp.get$key());
                                if (!fabric.lang.Object._Proxy.idEquals(
                                                                 preloaded,
                                                                 tmp.get$stats(
                                                                       )))
                                    tmp.set$stats(preloaded);
                                rtn = tmp.get$stats().getNoiseEstimate();
                            }
                        }
                        catch (final fabric.worker.RetryException $e290) {
                            $commit287 = false;
                            continue $label286;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e290) {
                            $commit287 = false;
                            $retry288 = false;
                            $keepReads289 = $e290.keepReads;
                            fabric.common.TransactionID $currentTid291 =
                              $tm292.getCurrentTid();
                            if ($e290.tid == null ||
                                  !$e290.tid.isDescendantOf($currentTid291)) {
                                throw $e290;
                            }
                            throw new fabric.worker.UserAbortException($e290);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e290) {
                            $commit287 = false;
                            fabric.common.TransactionID $currentTid291 =
                              $tm292.getCurrentTid();
                            if ($e290.tid.isDescendantOf($currentTid291))
                                continue $label286;
                            if ($currentTid291.parent != null) {
                                $retry288 = false;
                                throw $e290;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e290) {
                            $commit287 = false;
                            $retry288 = false;
                            if ($tm292.inNestedTxn()) { $keepReads289 = true; }
                            throw $e290;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid291 =
                              $tm292.getCurrentTid();
                            if ($commit287) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e290) {
                                    $commit287 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e290) {
                                    $commit287 = false;
                                    $retry288 = false;
                                    $keepReads289 = $e290.keepReads;
                                    if ($e290.tid ==
                                          null ||
                                          !$e290.tid.isDescendantOf(
                                                       $currentTid291))
                                        throw $e290;
                                    throw new fabric.worker.UserAbortException(
                                            $e290);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e290) {
                                    $commit287 = false;
                                    $currentTid291 = $tm292.getCurrentTid();
                                    if ($currentTid291 != null) {
                                        if ($e290.tid.equals($currentTid291) ||
                                              !$e290.tid.isDescendantOf(
                                                           $currentTid291)) {
                                            throw $e290;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm292.inNestedTxn() &&
                                      $tm292.checkForStaleObjects()) {
                                    $retry288 = true;
                                    $keepReads289 = false;
                                }
                                if ($keepReads289) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e290) {
                                        $currentTid291 = $tm292.getCurrentTid();
                                        if ($currentTid291 != null &&
                                              ($e290.tid.equals($currentTid291) || !$e290.tid.isDescendantOf($currentTid291))) {
                                            throw $e290;
                                        } else {
                                            $retry288 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit287) {
                                { rtn = rtn$var285; }
                                if ($retry288) { continue $label286; }
                            }
                        }
                    }
                }
                return rtn;
            }
        }
        
        public java.lang.String toString() {
            return "SampledMetric(" + this.get$key() + ")@" + getStore().name();
        }
        
        public boolean isSingleStore() { return true; }
        
        public fabric.worker.metrics.RunningMetricStats get$stats() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.stats;
        }
        
        public fabric.worker.metrics.RunningMetricStats set$stats(
          fabric.worker.metrics.RunningMetricStats val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.stats = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
   * The statistics for this metric.
   */
        protected fabric.worker.metrics.RunningMetricStats stats;
        
        /**
   * Updates the velocity and interval estimates with the given observation.
   *
   * @param newVal
   *        the new value of the measured quantity
   * @param eventTime
   *        the time, in milliseconds, this update happened
   */
        public void updateEstimates(double newVal, long eventTime) {
            fabric.worker.metrics.RunningMetricStats preloaded =
              this.get$stats().preload(this.get$key());
            if (!fabric.lang.Object._Proxy.idEquals(preloaded,
                                                    this.get$stats()))
                this.set$stats(preloaded);
            this.set$stats(this.get$stats().update(newVal));
            if (this.get$stats().getSamples() %
                  fabric.worker.Worker.getWorker(
                                         ).config.metricStatsLogInterval == 0) {
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.INFO,
                    "STATS {0} {1}", new java.lang.Object[] { (java.lang.Object) fabric.lang.WrappedJavaInlineable.$unwrap((fabric.metrics.SampledMetric) this.$getProxy()), this.get$stats() });
            }
        }
        
        public double value(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_value((fabric.metrics.SampledMetric) this.$getProxy(),
                           weakStats);
        }
        
        private static double static_value(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (weakStats.containsKey(tmp)) return weakStats.getValue(tmp);
            double result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                result = tmp.computeValue(weakStats);
            }
            else {
                {
                    double result$var296 = result;
                    fabric.worker.transaction.TransactionManager $tm303 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled306 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff304 = 1;
                    boolean $doBackoff305 = true;
                    boolean $retry299 = true;
                    boolean $keepReads300 = false;
                    $label297: for (boolean $commit298 = false; !$commit298; ) {
                        if ($backoffEnabled306) {
                            if ($doBackoff305) {
                                if ($backoff304 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff304));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e301) {
                                            
                                        }
                                    }
                                }
                                if ($backoff304 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff304 =
                                      java.lang.Math.
                                        min(
                                          $backoff304 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff305 = $backoff304 <= 32 || !$doBackoff305;
                        }
                        $commit298 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeValue(weakStats); }
                        catch (final fabric.worker.RetryException $e301) {
                            $commit298 = false;
                            continue $label297;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e301) {
                            $commit298 = false;
                            $retry299 = false;
                            $keepReads300 = $e301.keepReads;
                            fabric.common.TransactionID $currentTid302 =
                              $tm303.getCurrentTid();
                            if ($e301.tid == null ||
                                  !$e301.tid.isDescendantOf($currentTid302)) {
                                throw $e301;
                            }
                            throw new fabric.worker.UserAbortException($e301);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e301) {
                            $commit298 = false;
                            fabric.common.TransactionID $currentTid302 =
                              $tm303.getCurrentTid();
                            if ($e301.tid.isDescendantOf($currentTid302))
                                continue $label297;
                            if ($currentTid302.parent != null) {
                                $retry299 = false;
                                throw $e301;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e301) {
                            $commit298 = false;
                            $retry299 = false;
                            if ($tm303.inNestedTxn()) { $keepReads300 = true; }
                            throw $e301;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid302 =
                              $tm303.getCurrentTid();
                            if ($commit298) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e301) {
                                    $commit298 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e301) {
                                    $commit298 = false;
                                    $retry299 = false;
                                    $keepReads300 = $e301.keepReads;
                                    if ($e301.tid ==
                                          null ||
                                          !$e301.tid.isDescendantOf(
                                                       $currentTid302))
                                        throw $e301;
                                    throw new fabric.worker.UserAbortException(
                                            $e301);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e301) {
                                    $commit298 = false;
                                    $currentTid302 = $tm303.getCurrentTid();
                                    if ($currentTid302 != null) {
                                        if ($e301.tid.equals($currentTid302) ||
                                              !$e301.tid.isDescendantOf(
                                                           $currentTid302)) {
                                            throw $e301;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm303.inNestedTxn() &&
                                      $tm303.checkForStaleObjects()) {
                                    $retry299 = true;
                                    $keepReads300 = false;
                                }
                                if ($keepReads300) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e301) {
                                        $currentTid302 = $tm303.getCurrentTid();
                                        if ($currentTid302 != null &&
                                              ($e301.tid.equals($currentTid302) || !$e301.tid.isDescendantOf($currentTid302))) {
                                            throw $e301;
                                        } else {
                                            $retry299 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit298) {
                                { result = result$var296; }
                                if ($retry299) { continue $label297; }
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        public long samples(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_samples((fabric.metrics.SampledMetric) this.$getProxy(),
                             weakStats);
        }
        
        private static long static_samples(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (weakStats.containsKey(tmp)) return weakStats.getSamples(tmp);
            long result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                result = tmp.computeSamples(weakStats);
            }
            else {
                {
                    long result$var307 = result;
                    fabric.worker.transaction.TransactionManager $tm314 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled317 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff315 = 1;
                    boolean $doBackoff316 = true;
                    boolean $retry310 = true;
                    boolean $keepReads311 = false;
                    $label308: for (boolean $commit309 = false; !$commit309; ) {
                        if ($backoffEnabled317) {
                            if ($doBackoff316) {
                                if ($backoff315 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff315));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e312) {
                                            
                                        }
                                    }
                                }
                                if ($backoff315 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff315 =
                                      java.lang.Math.
                                        min(
                                          $backoff315 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff316 = $backoff315 <= 32 || !$doBackoff316;
                        }
                        $commit309 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeSamples(weakStats); }
                        catch (final fabric.worker.RetryException $e312) {
                            $commit309 = false;
                            continue $label308;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e312) {
                            $commit309 = false;
                            $retry310 = false;
                            $keepReads311 = $e312.keepReads;
                            fabric.common.TransactionID $currentTid313 =
                              $tm314.getCurrentTid();
                            if ($e312.tid == null ||
                                  !$e312.tid.isDescendantOf($currentTid313)) {
                                throw $e312;
                            }
                            throw new fabric.worker.UserAbortException($e312);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e312) {
                            $commit309 = false;
                            fabric.common.TransactionID $currentTid313 =
                              $tm314.getCurrentTid();
                            if ($e312.tid.isDescendantOf($currentTid313))
                                continue $label308;
                            if ($currentTid313.parent != null) {
                                $retry310 = false;
                                throw $e312;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e312) {
                            $commit309 = false;
                            $retry310 = false;
                            if ($tm314.inNestedTxn()) { $keepReads311 = true; }
                            throw $e312;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid313 =
                              $tm314.getCurrentTid();
                            if ($commit309) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e312) {
                                    $commit309 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e312) {
                                    $commit309 = false;
                                    $retry310 = false;
                                    $keepReads311 = $e312.keepReads;
                                    if ($e312.tid ==
                                          null ||
                                          !$e312.tid.isDescendantOf(
                                                       $currentTid313))
                                        throw $e312;
                                    throw new fabric.worker.UserAbortException(
                                            $e312);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e312) {
                                    $commit309 = false;
                                    $currentTid313 = $tm314.getCurrentTid();
                                    if ($currentTid313 != null) {
                                        if ($e312.tid.equals($currentTid313) ||
                                              !$e312.tid.isDescendantOf(
                                                           $currentTid313)) {
                                            throw $e312;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm314.inNestedTxn() &&
                                      $tm314.checkForStaleObjects()) {
                                    $retry310 = true;
                                    $keepReads311 = false;
                                }
                                if ($keepReads311) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e312) {
                                        $currentTid313 = $tm314.getCurrentTid();
                                        if ($currentTid313 != null &&
                                              ($e312.tid.equals($currentTid313) || !$e312.tid.isDescendantOf($currentTid313))) {
                                            throw $e312;
                                        } else {
                                            $retry310 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit309) {
                                { result = result$var307; }
                                if ($retry310) { continue $label308; }
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        public long lastUpdate(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_lastUpdate((fabric.metrics.SampledMetric) this.$getProxy(),
                                weakStats);
        }
        
        private static long static_lastUpdate(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (weakStats.containsKey(tmp)) return weakStats.getLastUpdate(tmp);
            long result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                result = tmp.computeLastUpdate(weakStats);
            }
            else {
                {
                    long result$var318 = result;
                    fabric.worker.transaction.TransactionManager $tm325 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled328 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff326 = 1;
                    boolean $doBackoff327 = true;
                    boolean $retry321 = true;
                    boolean $keepReads322 = false;
                    $label319: for (boolean $commit320 = false; !$commit320; ) {
                        if ($backoffEnabled328) {
                            if ($doBackoff327) {
                                if ($backoff326 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff326));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e323) {
                                            
                                        }
                                    }
                                }
                                if ($backoff326 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff326 =
                                      java.lang.Math.
                                        min(
                                          $backoff326 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff327 = $backoff326 <= 32 || !$doBackoff327;
                        }
                        $commit320 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeLastUpdate(weakStats); }
                        catch (final fabric.worker.RetryException $e323) {
                            $commit320 = false;
                            continue $label319;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e323) {
                            $commit320 = false;
                            $retry321 = false;
                            $keepReads322 = $e323.keepReads;
                            fabric.common.TransactionID $currentTid324 =
                              $tm325.getCurrentTid();
                            if ($e323.tid == null ||
                                  !$e323.tid.isDescendantOf($currentTid324)) {
                                throw $e323;
                            }
                            throw new fabric.worker.UserAbortException($e323);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e323) {
                            $commit320 = false;
                            fabric.common.TransactionID $currentTid324 =
                              $tm325.getCurrentTid();
                            if ($e323.tid.isDescendantOf($currentTid324))
                                continue $label319;
                            if ($currentTid324.parent != null) {
                                $retry321 = false;
                                throw $e323;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e323) {
                            $commit320 = false;
                            $retry321 = false;
                            if ($tm325.inNestedTxn()) { $keepReads322 = true; }
                            throw $e323;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid324 =
                              $tm325.getCurrentTid();
                            if ($commit320) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e323) {
                                    $commit320 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e323) {
                                    $commit320 = false;
                                    $retry321 = false;
                                    $keepReads322 = $e323.keepReads;
                                    if ($e323.tid ==
                                          null ||
                                          !$e323.tid.isDescendantOf(
                                                       $currentTid324))
                                        throw $e323;
                                    throw new fabric.worker.UserAbortException(
                                            $e323);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e323) {
                                    $commit320 = false;
                                    $currentTid324 = $tm325.getCurrentTid();
                                    if ($currentTid324 != null) {
                                        if ($e323.tid.equals($currentTid324) ||
                                              !$e323.tid.isDescendantOf(
                                                           $currentTid324)) {
                                            throw $e323;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm325.inNestedTxn() &&
                                      $tm325.checkForStaleObjects()) {
                                    $retry321 = true;
                                    $keepReads322 = false;
                                }
                                if ($keepReads322) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e323) {
                                        $currentTid324 = $tm325.getCurrentTid();
                                        if ($currentTid324 != null &&
                                              ($e323.tid.equals($currentTid324) || !$e323.tid.isDescendantOf($currentTid324))) {
                                            throw $e323;
                                        } else {
                                            $retry321 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit320) {
                                { result = result$var318; }
                                if ($retry321) { continue $label319; }
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        public double updateInterval(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_updateInterval((fabric.metrics.SampledMetric)
                                      this.$getProxy(), weakStats);
        }
        
        private static double static_updateInterval(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (weakStats.containsKey(tmp))
                return weakStats.getUpdateInterval(tmp);
            double result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                result = tmp.computeUpdateInterval(weakStats);
            }
            else {
                {
                    double result$var329 = result;
                    fabric.worker.transaction.TransactionManager $tm336 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled339 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff337 = 1;
                    boolean $doBackoff338 = true;
                    boolean $retry332 = true;
                    boolean $keepReads333 = false;
                    $label330: for (boolean $commit331 = false; !$commit331; ) {
                        if ($backoffEnabled339) {
                            if ($doBackoff338) {
                                if ($backoff337 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff337));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e334) {
                                            
                                        }
                                    }
                                }
                                if ($backoff337 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff337 =
                                      java.lang.Math.
                                        min(
                                          $backoff337 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff338 = $backoff337 <= 32 || !$doBackoff338;
                        }
                        $commit331 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeUpdateInterval(weakStats); }
                        catch (final fabric.worker.RetryException $e334) {
                            $commit331 = false;
                            continue $label330;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e334) {
                            $commit331 = false;
                            $retry332 = false;
                            $keepReads333 = $e334.keepReads;
                            fabric.common.TransactionID $currentTid335 =
                              $tm336.getCurrentTid();
                            if ($e334.tid == null ||
                                  !$e334.tid.isDescendantOf($currentTid335)) {
                                throw $e334;
                            }
                            throw new fabric.worker.UserAbortException($e334);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e334) {
                            $commit331 = false;
                            fabric.common.TransactionID $currentTid335 =
                              $tm336.getCurrentTid();
                            if ($e334.tid.isDescendantOf($currentTid335))
                                continue $label330;
                            if ($currentTid335.parent != null) {
                                $retry332 = false;
                                throw $e334;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e334) {
                            $commit331 = false;
                            $retry332 = false;
                            if ($tm336.inNestedTxn()) { $keepReads333 = true; }
                            throw $e334;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid335 =
                              $tm336.getCurrentTid();
                            if ($commit331) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e334) {
                                    $commit331 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e334) {
                                    $commit331 = false;
                                    $retry332 = false;
                                    $keepReads333 = $e334.keepReads;
                                    if ($e334.tid ==
                                          null ||
                                          !$e334.tid.isDescendantOf(
                                                       $currentTid335))
                                        throw $e334;
                                    throw new fabric.worker.UserAbortException(
                                            $e334);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e334) {
                                    $commit331 = false;
                                    $currentTid335 = $tm336.getCurrentTid();
                                    if ($currentTid335 != null) {
                                        if ($e334.tid.equals($currentTid335) ||
                                              !$e334.tid.isDescendantOf(
                                                           $currentTid335)) {
                                            throw $e334;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm336.inNestedTxn() &&
                                      $tm336.checkForStaleObjects()) {
                                    $retry332 = true;
                                    $keepReads333 = false;
                                }
                                if ($keepReads333) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e334) {
                                        $currentTid335 = $tm336.getCurrentTid();
                                        if ($currentTid335 != null &&
                                              ($e334.tid.equals($currentTid335) || !$e334.tid.isDescendantOf($currentTid335))) {
                                            throw $e334;
                                        } else {
                                            $retry332 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit331) {
                                { result = result$var329; }
                                if ($retry332) { continue $label330; }
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        public double velocity(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_velocity((fabric.metrics.SampledMetric) this.$getProxy(),
                              weakStats);
        }
        
        private static double static_velocity(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (!tmp.getUsePreset() && weakStats.containsKey(tmp))
                return weakStats.getVelocity(tmp);
            double result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                result = tmp.computeVelocity(weakStats);
            }
            else {
                {
                    double result$var340 = result;
                    fabric.worker.transaction.TransactionManager $tm347 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled350 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff348 = 1;
                    boolean $doBackoff349 = true;
                    boolean $retry343 = true;
                    boolean $keepReads344 = false;
                    $label341: for (boolean $commit342 = false; !$commit342; ) {
                        if ($backoffEnabled350) {
                            if ($doBackoff349) {
                                if ($backoff348 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff348));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e345) {
                                            
                                        }
                                    }
                                }
                                if ($backoff348 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff348 =
                                      java.lang.Math.
                                        min(
                                          $backoff348 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff349 = $backoff348 <= 32 || !$doBackoff349;
                        }
                        $commit342 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeVelocity(weakStats); }
                        catch (final fabric.worker.RetryException $e345) {
                            $commit342 = false;
                            continue $label341;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e345) {
                            $commit342 = false;
                            $retry343 = false;
                            $keepReads344 = $e345.keepReads;
                            fabric.common.TransactionID $currentTid346 =
                              $tm347.getCurrentTid();
                            if ($e345.tid == null ||
                                  !$e345.tid.isDescendantOf($currentTid346)) {
                                throw $e345;
                            }
                            throw new fabric.worker.UserAbortException($e345);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e345) {
                            $commit342 = false;
                            fabric.common.TransactionID $currentTid346 =
                              $tm347.getCurrentTid();
                            if ($e345.tid.isDescendantOf($currentTid346))
                                continue $label341;
                            if ($currentTid346.parent != null) {
                                $retry343 = false;
                                throw $e345;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e345) {
                            $commit342 = false;
                            $retry343 = false;
                            if ($tm347.inNestedTxn()) { $keepReads344 = true; }
                            throw $e345;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid346 =
                              $tm347.getCurrentTid();
                            if ($commit342) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e345) {
                                    $commit342 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e345) {
                                    $commit342 = false;
                                    $retry343 = false;
                                    $keepReads344 = $e345.keepReads;
                                    if ($e345.tid ==
                                          null ||
                                          !$e345.tid.isDescendantOf(
                                                       $currentTid346))
                                        throw $e345;
                                    throw new fabric.worker.UserAbortException(
                                            $e345);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e345) {
                                    $commit342 = false;
                                    $currentTid346 = $tm347.getCurrentTid();
                                    if ($currentTid346 != null) {
                                        if ($e345.tid.equals($currentTid346) ||
                                              !$e345.tid.isDescendantOf(
                                                           $currentTid346)) {
                                            throw $e345;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm347.inNestedTxn() &&
                                      $tm347.checkForStaleObjects()) {
                                    $retry343 = true;
                                    $keepReads344 = false;
                                }
                                if ($keepReads344) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e345) {
                                        $currentTid346 = $tm347.getCurrentTid();
                                        if ($currentTid346 != null &&
                                              ($e345.tid.equals($currentTid346) || !$e345.tid.isDescendantOf($currentTid346))) {
                                            throw $e345;
                                        } else {
                                            $retry343 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit342) {
                                { result = result$var340; }
                                if ($retry343) { continue $label341; }
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        public double noise(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_noise((fabric.metrics.SampledMetric) this.$getProxy(),
                           weakStats);
        }
        
        private static double static_noise(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (!tmp.getUsePreset() && weakStats.containsKey(tmp))
                return weakStats.getNoise(tmp);
            double result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                result = tmp.computeNoise(weakStats);
            }
            else {
                {
                    double result$var351 = result;
                    fabric.worker.transaction.TransactionManager $tm358 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled361 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff359 = 1;
                    boolean $doBackoff360 = true;
                    boolean $retry354 = true;
                    boolean $keepReads355 = false;
                    $label352: for (boolean $commit353 = false; !$commit353; ) {
                        if ($backoffEnabled361) {
                            if ($doBackoff360) {
                                if ($backoff359 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff359));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e356) {
                                            
                                        }
                                    }
                                }
                                if ($backoff359 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff359 =
                                      java.lang.Math.
                                        min(
                                          $backoff359 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff360 = $backoff359 <= 32 || !$doBackoff360;
                        }
                        $commit353 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeNoise(weakStats); }
                        catch (final fabric.worker.RetryException $e356) {
                            $commit353 = false;
                            continue $label352;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e356) {
                            $commit353 = false;
                            $retry354 = false;
                            $keepReads355 = $e356.keepReads;
                            fabric.common.TransactionID $currentTid357 =
                              $tm358.getCurrentTid();
                            if ($e356.tid == null ||
                                  !$e356.tid.isDescendantOf($currentTid357)) {
                                throw $e356;
                            }
                            throw new fabric.worker.UserAbortException($e356);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e356) {
                            $commit353 = false;
                            fabric.common.TransactionID $currentTid357 =
                              $tm358.getCurrentTid();
                            if ($e356.tid.isDescendantOf($currentTid357))
                                continue $label352;
                            if ($currentTid357.parent != null) {
                                $retry354 = false;
                                throw $e356;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e356) {
                            $commit353 = false;
                            $retry354 = false;
                            if ($tm358.inNestedTxn()) { $keepReads355 = true; }
                            throw $e356;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid357 =
                              $tm358.getCurrentTid();
                            if ($commit353) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e356) {
                                    $commit353 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e356) {
                                    $commit353 = false;
                                    $retry354 = false;
                                    $keepReads355 = $e356.keepReads;
                                    if ($e356.tid ==
                                          null ||
                                          !$e356.tid.isDescendantOf(
                                                       $currentTid357))
                                        throw $e356;
                                    throw new fabric.worker.UserAbortException(
                                            $e356);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e356) {
                                    $commit353 = false;
                                    $currentTid357 = $tm358.getCurrentTid();
                                    if ($currentTid357 != null) {
                                        if ($e356.tid.equals($currentTid357) ||
                                              !$e356.tid.isDescendantOf(
                                                           $currentTid357)) {
                                            throw $e356;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm358.inNestedTxn() &&
                                      $tm358.checkForStaleObjects()) {
                                    $retry354 = true;
                                    $keepReads355 = false;
                                }
                                if ($keepReads355) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e356) {
                                        $currentTid357 = $tm358.getCurrentTid();
                                        if ($currentTid357 != null &&
                                              ($e356.tid.equals($currentTid357) || !$e356.tid.isDescendantOf($currentTid357))) {
                                            throw $e356;
                                        } else {
                                            $retry354 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit353) {
                                { result = result$var351; }
                                if ($retry354) { continue $label352; }
                            }
                        }
                    }
                }
            }
            return result;
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
            out.writeLong(this.key);
            out.writeDouble(this.presetR);
            out.writeDouble(this.presetB);
            out.writeDouble(this.presetV);
            out.writeDouble(this.presetN);
            $writeInline(out, this.stats);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.key = in.readLong();
            this.presetR = in.readDouble();
            this.presetB = in.readDouble();
            this.presetV = in.readDouble();
            this.presetN = in.readDouble();
            this.stats = (fabric.worker.metrics.RunningMetricStats)
                           in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.SampledMetric._Impl src =
              (fabric.metrics.SampledMetric._Impl) other;
            this.key = src.key;
            this.presetR = src.presetR;
            this.presetB = src.presetB;
            this.presetV = src.presetV;
            this.presetN = src.presetN;
            this.stats = src.stats;
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
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.SampledMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -81, -48, -113, -74,
    -116, 53, 82, -11, 79, -112, -81, -29, 5, 94, -45, 69, -46, -12, 96, 48,
    -102, -113, -90, 20, 88, -100, 70, 93, -1, 51, 102, -7 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556306458000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVaC3AV1Rn+Nwl5EUgAgzxDCAENj1zxjdhauPIIBIgJQQ2VuNl7bliz9+5l99xwg+LYh4VxaqglPlBktGKrmELVsU61VK2P4mMcRa3VFmF8jDjIdKxV21Gx/3/23Gfu3WRnUoY932bP+c/53+c/e7f/JIywLagJqh26Uc97IsyuX6p2NDQ2qZbNAn5Dte21+LRdG1nQcNvx3wSq8iCvEco0NWyGdU012sM2h9GN16jdqi/MuK+1uWHheijRiHC5am/kkLd+ccyC6ohp9HQaJpeLDJj/1jm+vts3VDySD+VtUK6HW7jKdc1vhjmL8TYoC7FQB7PsRYEAC7TBmDBjgRZm6aqhb8GBZrgNxtp6Z1jlUYvZzcw2jW4aONaORpgl1ow/JPZNZNuKaty0kP0Kh/0o1w1fo27zhY1QGNSZEbA3wfVQ0AgjgobaiQPHN8al8IkZfUvpOQ4v1ZFNK6hqLE5S0KWHAxymZVIkJK5diQOQtCjE+EYzsVRBWMUHMNZhyVDDnb4WbunhThw6woziKhwm5ZwUBxVHVK1L7WTtHCZkjmtyunBUiVALkXCozBwmZkKbTcqwWYq1Tq6+uPfa8PJwHijIc4BpBvFfjERVGUTNLMgsFtaYQ1g2u/E2dfzB7XkAOLgyY7Az5vHrPvvB3KqnDzljJmcZs6bjGqbxdm1vx+jXp/jrFuQTG8UR09bJFdIkF1Ztkj0LYxH09vGJGamzPt75dPMLV96wj53Ig9IGKNRMIxpCrxqjmaGIbjBrGQszS+Us0AAlLBzwi/4GKML7Rj3MnKdrgkGb8QYoMMSjQlP8jSoK4hSkoiK818NBM34fUflGcR+LAEARXqDg/36A+Vvxvgog/3kOTb6NZoj5Oowo24zu7cOLqZa20Ydxa+naPM2M9PhsS/NZ0TDXcaTz3IeuhGD7WtRQxGCBVeLPeuQl8n+YM0ZyVGxWFFTxNM0MsA7VRntJ31ncZGB4LDeNALPaNaP3YAOMO7hL+E8J+byNfis0pKDNp2Rmi1TavujiJZ/tb3/Z8T2ilQrkMMXhsV7yWJ/GI7JVRlFVj3mqHvNUvxKr9+9peEg4T6EtoiwxUxnOdFHEUHnQtEIxUBQh1mmCXngN2rwLcwmmi7K6lqtWXL29Jh/dNbK5gCyIQ2szgyeZchrwTsWIaNfKtx3/8sBtW81kGHGoHRDdAykpOmsydWSZGgtg9ktOP7tafaz94NbaPMosJZj0uIpuiRmkKnONtChdGM94pI0RjTCSdKAa1BVPU6V8o2VuTj4Rth9NzVjHDUhZGQyKZPm9lsjdf3v1k3PENhLPq+UpCbiF8YUpsUyTlYuoHZPU/VqLMRx35I6mnbee3LZeKB5HzMi2YC21foxhFYPXtG48tOmdo+/tfTMvaSwOhZFoh6FrMSHLmO/wn4LXKbooIOkBIaZlv0wG1YlsEKGVZyV5w7xgYG5C1u3a1nDIDOhBXe0wGHnKN+Uz5z/2aW+FY24DnzjKs2Du4BMkn09cDDe8vOGrKjGNotG+lNRfcpiT7MYlZ15kWWoP8RH70eGpu/6i3o2ej6nK1rcwkX1A6AOEAc8Wupgn2vkZfedSU+Noa4p4XmgPTPxLaQdN+mKbr3/3JP/3TzgRn/BFmmN6lohfp6aEydn7Ql/k1RQ+nwdFbVAhNm81zNepmLrQDdpw+7X98mEjjErrT99KnX1jYSLWpmTGQcqymVGQzDR4T6PpvtRxfMdxUBEVpKSJeFUDFBQ4mP819Y6LUHtaTAFxc5EgmSHaWdTUCUXmcSiKWHo3ehaHEj0UinKyvVhlDof8LtaTRdVNlh7CaOmWeyzb3nfTd/W9fY6bOYXIjAG1QCqNU4yIVUaJpWK4ynS3VQTF0o8PbH3yga3bnI16bPq2uiQcDf32r9++Un/HsRezpOoCw3TSbYXQwfkJFZaDs+9BK8DIayUaWVS4PIcK6XY2NZfE1YYqxc2NN4uhi6SABJdi7AdMDH7mysl65OB9iW9k4aTJKyeL6c9VrmuqAGUNEi/Ksmar1zXXDb5mJ671O4n3Zlmzzeuaq3OuWUZrzsTsWwdQ84DEHVnWbM++JmbtkohlcgxnFoglps2jaUfK6Xol3pgyLcfzDp4sbEFRyeFMWTFsNq0uZiUKh+ZoOIzlgFM4tCQIJmYWBkK0WA4WhVqSvIl/hbKoe07in1J4S0moQAE4NVf9LYJv74/79gTW3D8/T2blJagRbkbmGaybGSlT1VIoDzjfrRKnjmSKPXZi6gJ/10edTihPy1g5c/SDq/pfXDZL+2Ue5Cdy6YCjTjrRwvQMWmoxPKmF16bl0eqErkpIByvxmgsw4o8St6S6R9KpZlDTle4CxZKkR6KdqebkzpaX9GG/cFcx9RaX/e86aqIcpjq+Uyudpjat2qxNMmilizUNr8vQS2dKLPcmFpGMllicW6xUhn/i0ieC43r0607GW23WJOI2W6Is6jBNg6nhbCJNwety5OcBibd6E4lI+iT2Dk2kHS59t1BzE4dSFMmRp1kYNhfnV2E6Kndw5DfeOCeSryV+MTTO73Dpu5OanamcL3blXEPOuyVq3jgnkg6JPxwa5/e69N1Hze5Uzte5cq7jskckvuSNcyJ5UeKzQ+N8n0tfPzX3p3K+OifnZwCdAmDUUYnPeOOcSJ6W+ERuzpXk9uEko0dd2H+Mmv3IPle7mJOBskVvQbepB7KJVIsXVlmjgxJXehOJSFZIvNSDSE+5iCS0+gSHYvIiKuLp78ezMX82XtuwgNkkcbk35olkmcRFuZlP2SFELeMXUx9ykUB49LNpRskmgyiCzsFrB54cVkic4SJDx4BCR5DUSJw4qAHiZU9V9rJHVDqr1Ej2Ykdw87qL2G9T8wqn96uhSJSzhPEyg0mUQ8147USu35LYl0PwrOXm9zm9sqFXxBnFX4WcbafEG3PrJMWuFdQcFqsecxHwfWre4TDOWbp9MDkTBt4LMPZTic97MzCRPCfxySFFmCPHJy5ynKDmQw6jpQApecOfy1S4t4/rkXihJ1NRcySLmWimCyTWeTXT5y7iiZ34JIfKdDO5SCkMdT5efwCo7JUY8GYoItEktnkw1Dcukpyi5ivuvIVGERpVm7dGAipnOW2Fx2U4CDB+uoOVR4bFVjTTPyS+6tFWSmFuCRUqYRXcESek28pdUGGui/F6FWDCYoljvZmLSMZIdKmjM82llLsIM4aaUnQ8KYUjQQP9ONStGjkz4Xq8DiMbRyXmKp+9mYxm6pP4M68mm+gi5WRqxnGYkm6ywYVNRNkHAJOLHZz0njezEckRiW96MFuNi0B0VlOmciiP53NmmJrOe3IajGLsY5Rhl8TWYTEYzbRW4lKvBpvrIl89NbM4jM/YtlzEjO9cCkJVTOJlnkwlSJokLvNgqvNcRLmAmrOSJcZqU7ddSwwFaaflO1j15+Gwk5jpGYmPerXTJS7CUQ2qLBhQXuSUUdTAk5CTqQDTn5X4iIuRBtbAguRhiQ/lFiaVzeUufSuo8WPtzk3nd+t4xVkhfoegt/D1KR0DasxsElYje1ji1iyQWONNQiKZLnHy0CRsdem7nJomDqN0uwXFMFgLNy1hnm3ZYugSXBnjaMZRif3eYohIHpL4qyH5WvKIomxwEeNqaq7EnBcVaXuJzfUQohib9axVh1zgVjvzJYn7vFmBSB6UeJ+HZNDpIoNOTQeHEd2uB42VOOFSgFnLJI4alixAM5U5OPOU1yywyUUqeqh0YWDILJBTuIRZcNM4Y7/E3hzC5TALkdwscZsHs7i8H1Xo/agS5VBkD3KsIMNcAXDmPAfP+HxYDEMz/Uvih14N81MXuaiIUq7HU5M0jIt4wjRzkYkgWuguiTFvpiGSzRI3eTDNzS4i0C8synYOpcbgB4nVOOs1ALOrHaz7YFisQzO9L9GlfstundtdRNtFzS14VJLWcZcw/vpIwdpmzt0SuTcDEYktMeTBQPe4SEE5XrkLfSw6tKMDxq1yHbraXAfnfDIsRqKZjkt816uRXF62KrT7KXuTJ/PBpUxE0k0A8x6W6DHJEcnNEr0kOZf3rgq9d1X2Y6HTPdhhgeKoF6D+XAfnfTUsJqKZvpR43KuJDroI9hQ1j2NhEN9+XORL7EC7AXxPSrzLm3GI5E6JfbkFGWCcF1xkOETNM1gYhF2PB7T/3ANw1oUO+k7l4NybZWimbyX+06tlXnOR6jA1LyULg6RwMSxH0359pC9/Jmf5Bk9+G6r5n2N7P1o5tzLH93cTBnytK+n27ykvPn1P69vii7LEd58ljVAcjBpG6hcyKfeFEYsFdaHQEud7mYiQ521Mc+kf7HHxOSzdiSLoLWfcuxwKnXH019+FNieJJn64qMz47s9RghgiFpwUteg75P7PT/9PYfHaY+LjL9Rq9YHXdvz+5+c1f7HmFwc+GLHhjSWH/331WXfu+PVpV+xeetV35wT/+z8vfm+YHy0AAA==";
}
