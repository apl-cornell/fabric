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
                    int $backoff238 = 1;
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
                                if ($backoff238 < 5000) $backoff238 *= 2;
                            }
                            $doBackoff239 = $backoff238 <= 32 || !$doBackoff239;
                        }
                        $commit232 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                fabric.worker.metrics.RunningMetricStats
                                  preloaded =
                                  tmp.get$stats().preload(tmp.get$key());
                                if (!fabric.lang.Object._Proxy.idEquals(
                                                                 preloaded,
                                                                 tmp.get$stats(
                                                                       )))
                                    tmp.set$stats(preloaded);
                                result = tmp.get$stats().getValue();
                            }
                            catch (final fabric.worker.RetryException $e235) {
                                throw $e235;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e235) {
                                throw $e235;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e235) {
                                throw $e235;
                            }
                            catch (final Throwable $e235) {
                                $tm237.getCurrentLog().checkRetrySignal();
                                throw $e235;
                            }
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
                            if ($tm237.checkForStaleObjects()) {
                                $retry233 = true;
                                $keepReads234 = false;
                                continue $label231;
                            }
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
                            if ($tm237.checkForStaleObjects())
                                continue $label231;
                            $retry233 = false;
                            throw new fabric.worker.AbortException($e235);
                        }
                        finally {
                            if ($commit232) {
                                fabric.common.TransactionID $currentTid236 =
                                  $tm237.getCurrentTid();
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
                                    if ($tm237.checkForStaleObjects()) {
                                        $retry233 = true;
                                        $keepReads234 = false;
                                        continue $label231;
                                    }
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
                            }
                            else if ($keepReads234) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                    int $backoff249 = 1;
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
                                if ($backoff249 < 5000) $backoff249 *= 2;
                            }
                            $doBackoff250 = $backoff249 <= 32 || !$doBackoff250;
                        }
                        $commit243 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                fabric.worker.metrics.RunningMetricStats
                                  preloaded =
                                  tmp.get$stats().preload(tmp.get$key());
                                if (!fabric.lang.Object._Proxy.idEquals(
                                                                 preloaded,
                                                                 tmp.get$stats(
                                                                       )))
                                    tmp.set$stats(preloaded);
                                rtn = tmp.get$stats().getSamples();
                            }
                            catch (final fabric.worker.RetryException $e246) {
                                throw $e246;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e246) {
                                throw $e246;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e246) {
                                throw $e246;
                            }
                            catch (final Throwable $e246) {
                                $tm248.getCurrentLog().checkRetrySignal();
                                throw $e246;
                            }
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
                            if ($tm248.checkForStaleObjects()) {
                                $retry244 = true;
                                $keepReads245 = false;
                                continue $label242;
                            }
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
                            if ($tm248.checkForStaleObjects())
                                continue $label242;
                            $retry244 = false;
                            throw new fabric.worker.AbortException($e246);
                        }
                        finally {
                            if ($commit243) {
                                fabric.common.TransactionID $currentTid247 =
                                  $tm248.getCurrentTid();
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
                                    if ($tm248.checkForStaleObjects()) {
                                        $retry244 = true;
                                        $keepReads245 = false;
                                        continue $label242;
                                    }
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
                            }
                            else if ($keepReads245) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                    int $backoff260 = 1;
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
                                if ($backoff260 < 5000) $backoff260 *= 2;
                            }
                            $doBackoff261 = $backoff260 <= 32 || !$doBackoff261;
                        }
                        $commit254 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                fabric.worker.metrics.RunningMetricStats
                                  preloaded =
                                  tmp.get$stats().preload(tmp.get$key());
                                if (!fabric.lang.Object._Proxy.idEquals(
                                                                 preloaded,
                                                                 tmp.get$stats(
                                                                       )))
                                    tmp.set$stats(preloaded);
                                rtn = tmp.get$stats().getLastUpdate();
                            }
                            catch (final fabric.worker.RetryException $e257) {
                                throw $e257;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e257) {
                                throw $e257;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e257) {
                                throw $e257;
                            }
                            catch (final Throwable $e257) {
                                $tm259.getCurrentLog().checkRetrySignal();
                                throw $e257;
                            }
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
                            if ($tm259.checkForStaleObjects()) {
                                $retry255 = true;
                                $keepReads256 = false;
                                continue $label253;
                            }
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
                            if ($tm259.checkForStaleObjects())
                                continue $label253;
                            $retry255 = false;
                            throw new fabric.worker.AbortException($e257);
                        }
                        finally {
                            if ($commit254) {
                                fabric.common.TransactionID $currentTid258 =
                                  $tm259.getCurrentTid();
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
                                    if ($tm259.checkForStaleObjects()) {
                                        $retry255 = true;
                                        $keepReads256 = false;
                                        continue $label253;
                                    }
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
                            }
                            else if ($keepReads256) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                    int $backoff271 = 1;
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
                                if ($backoff271 < 5000) $backoff271 *= 2;
                            }
                            $doBackoff272 = $backoff271 <= 32 || !$doBackoff272;
                        }
                        $commit265 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                fabric.worker.metrics.RunningMetricStats
                                  preloaded =
                                  tmp.get$stats().preload(tmp.get$key());
                                if (!fabric.lang.Object._Proxy.idEquals(
                                                                 preloaded,
                                                                 tmp.get$stats(
                                                                       )))
                                    tmp.set$stats(preloaded);
                                rtn = tmp.get$stats().getIntervalEstimate();
                            }
                            catch (final fabric.worker.RetryException $e268) {
                                throw $e268;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e268) {
                                throw $e268;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e268) {
                                throw $e268;
                            }
                            catch (final Throwable $e268) {
                                $tm270.getCurrentLog().checkRetrySignal();
                                throw $e268;
                            }
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
                            if ($tm270.checkForStaleObjects()) {
                                $retry266 = true;
                                $keepReads267 = false;
                                continue $label264;
                            }
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
                            if ($tm270.checkForStaleObjects())
                                continue $label264;
                            $retry266 = false;
                            throw new fabric.worker.AbortException($e268);
                        }
                        finally {
                            if ($commit265) {
                                fabric.common.TransactionID $currentTid269 =
                                  $tm270.getCurrentTid();
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
                                    if ($tm270.checkForStaleObjects()) {
                                        $retry266 = true;
                                        $keepReads267 = false;
                                        continue $label264;
                                    }
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
                            }
                            else if ($keepReads267) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                    int $backoff282 = 1;
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
                                if ($backoff282 < 5000) $backoff282 *= 2;
                            }
                            $doBackoff283 = $backoff282 <= 32 || !$doBackoff283;
                        }
                        $commit276 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (tmp.getUsePreset()) {
                                    rtn = tmp.get$presetV();
                                }
                                else {
                                    fabric.worker.metrics.RunningMetricStats
                                      preloaded =
                                      tmp.get$stats().preload(tmp.get$key());
                                    if (!fabric.lang.Object._Proxy.
                                          idEquals(preloaded, tmp.get$stats()))
                                        tmp.set$stats(preloaded);
                                    rtn = tmp.get$stats().getVelocityEstimate();
                                }
                            }
                            catch (final fabric.worker.RetryException $e279) {
                                throw $e279;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e279) {
                                throw $e279;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e279) {
                                throw $e279;
                            }
                            catch (final Throwable $e279) {
                                $tm281.getCurrentLog().checkRetrySignal();
                                throw $e279;
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
                            if ($tm281.checkForStaleObjects()) {
                                $retry277 = true;
                                $keepReads278 = false;
                                continue $label275;
                            }
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
                            if ($tm281.checkForStaleObjects())
                                continue $label275;
                            $retry277 = false;
                            throw new fabric.worker.AbortException($e279);
                        }
                        finally {
                            if ($commit276) {
                                fabric.common.TransactionID $currentTid280 =
                                  $tm281.getCurrentTid();
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
                                    if ($tm281.checkForStaleObjects()) {
                                        $retry277 = true;
                                        $keepReads278 = false;
                                        continue $label275;
                                    }
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
                            }
                            else if ($keepReads278) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                    int $backoff293 = 1;
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
                                if ($backoff293 < 5000) $backoff293 *= 2;
                            }
                            $doBackoff294 = $backoff293 <= 32 || !$doBackoff294;
                        }
                        $commit287 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (tmp.getUsePreset()) {
                                    rtn = tmp.get$presetN();
                                }
                                else {
                                    fabric.worker.metrics.RunningMetricStats
                                      preloaded =
                                      tmp.get$stats().preload(tmp.get$key());
                                    if (!fabric.lang.Object._Proxy.
                                          idEquals(preloaded, tmp.get$stats()))
                                        tmp.set$stats(preloaded);
                                    rtn = tmp.get$stats().getNoiseEstimate();
                                }
                            }
                            catch (final fabric.worker.RetryException $e290) {
                                throw $e290;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e290) {
                                throw $e290;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e290) {
                                throw $e290;
                            }
                            catch (final Throwable $e290) {
                                $tm292.getCurrentLog().checkRetrySignal();
                                throw $e290;
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
                            if ($tm292.checkForStaleObjects()) {
                                $retry288 = true;
                                $keepReads289 = false;
                                continue $label286;
                            }
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
                            if ($tm292.checkForStaleObjects())
                                continue $label286;
                            $retry288 = false;
                            throw new fabric.worker.AbortException($e290);
                        }
                        finally {
                            if ($commit287) {
                                fabric.common.TransactionID $currentTid291 =
                                  $tm292.getCurrentTid();
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
                                    if ($tm292.checkForStaleObjects()) {
                                        $retry288 = true;
                                        $keepReads289 = false;
                                        continue $label286;
                                    }
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
                            }
                            else if ($keepReads289) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                    int $backoff304 = 1;
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
                                if ($backoff304 < 5000) $backoff304 *= 2;
                            }
                            $doBackoff305 = $backoff304 <= 32 || !$doBackoff305;
                        }
                        $commit298 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { result = tmp.computeValue(weakStats); }
                            catch (final fabric.worker.RetryException $e301) {
                                throw $e301;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e301) {
                                throw $e301;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e301) {
                                throw $e301;
                            }
                            catch (final Throwable $e301) {
                                $tm303.getCurrentLog().checkRetrySignal();
                                throw $e301;
                            }
                        }
                        catch (final fabric.worker.RetryException $e301) {
                            $commit298 = false;
                            continue $label297;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e301) {
                            $commit298 = false;
                            $retry299 = false;
                            $keepReads300 = $e301.keepReads;
                            if ($tm303.checkForStaleObjects()) {
                                $retry299 = true;
                                $keepReads300 = false;
                                continue $label297;
                            }
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
                            if ($tm303.checkForStaleObjects())
                                continue $label297;
                            $retry299 = false;
                            throw new fabric.worker.AbortException($e301);
                        }
                        finally {
                            if ($commit298) {
                                fabric.common.TransactionID $currentTid302 =
                                  $tm303.getCurrentTid();
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
                                    if ($tm303.checkForStaleObjects()) {
                                        $retry299 = true;
                                        $keepReads300 = false;
                                        continue $label297;
                                    }
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
                            }
                            else if ($keepReads300) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                    int $backoff315 = 1;
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
                                if ($backoff315 < 5000) $backoff315 *= 2;
                            }
                            $doBackoff316 = $backoff315 <= 32 || !$doBackoff316;
                        }
                        $commit309 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { result = tmp.computeSamples(weakStats); }
                            catch (final fabric.worker.RetryException $e312) {
                                throw $e312;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e312) {
                                throw $e312;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e312) {
                                throw $e312;
                            }
                            catch (final Throwable $e312) {
                                $tm314.getCurrentLog().checkRetrySignal();
                                throw $e312;
                            }
                        }
                        catch (final fabric.worker.RetryException $e312) {
                            $commit309 = false;
                            continue $label308;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e312) {
                            $commit309 = false;
                            $retry310 = false;
                            $keepReads311 = $e312.keepReads;
                            if ($tm314.checkForStaleObjects()) {
                                $retry310 = true;
                                $keepReads311 = false;
                                continue $label308;
                            }
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
                            if ($tm314.checkForStaleObjects())
                                continue $label308;
                            $retry310 = false;
                            throw new fabric.worker.AbortException($e312);
                        }
                        finally {
                            if ($commit309) {
                                fabric.common.TransactionID $currentTid313 =
                                  $tm314.getCurrentTid();
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
                                    if ($tm314.checkForStaleObjects()) {
                                        $retry310 = true;
                                        $keepReads311 = false;
                                        continue $label308;
                                    }
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
                            }
                            else if ($keepReads311) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                    int $backoff326 = 1;
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
                                if ($backoff326 < 5000) $backoff326 *= 2;
                            }
                            $doBackoff327 = $backoff326 <= 32 || !$doBackoff327;
                        }
                        $commit320 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { result = tmp.computeLastUpdate(weakStats); }
                            catch (final fabric.worker.RetryException $e323) {
                                throw $e323;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e323) {
                                throw $e323;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e323) {
                                throw $e323;
                            }
                            catch (final Throwable $e323) {
                                $tm325.getCurrentLog().checkRetrySignal();
                                throw $e323;
                            }
                        }
                        catch (final fabric.worker.RetryException $e323) {
                            $commit320 = false;
                            continue $label319;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e323) {
                            $commit320 = false;
                            $retry321 = false;
                            $keepReads322 = $e323.keepReads;
                            if ($tm325.checkForStaleObjects()) {
                                $retry321 = true;
                                $keepReads322 = false;
                                continue $label319;
                            }
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
                            if ($tm325.checkForStaleObjects())
                                continue $label319;
                            $retry321 = false;
                            throw new fabric.worker.AbortException($e323);
                        }
                        finally {
                            if ($commit320) {
                                fabric.common.TransactionID $currentTid324 =
                                  $tm325.getCurrentTid();
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
                                    if ($tm325.checkForStaleObjects()) {
                                        $retry321 = true;
                                        $keepReads322 = false;
                                        continue $label319;
                                    }
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
                            }
                            else if ($keepReads322) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                    int $backoff337 = 1;
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
                                if ($backoff337 < 5000) $backoff337 *= 2;
                            }
                            $doBackoff338 = $backoff337 <= 32 || !$doBackoff338;
                        }
                        $commit331 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                result = tmp.computeUpdateInterval(weakStats);
                            }
                            catch (final fabric.worker.RetryException $e334) {
                                throw $e334;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e334) {
                                throw $e334;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e334) {
                                throw $e334;
                            }
                            catch (final Throwable $e334) {
                                $tm336.getCurrentLog().checkRetrySignal();
                                throw $e334;
                            }
                        }
                        catch (final fabric.worker.RetryException $e334) {
                            $commit331 = false;
                            continue $label330;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e334) {
                            $commit331 = false;
                            $retry332 = false;
                            $keepReads333 = $e334.keepReads;
                            if ($tm336.checkForStaleObjects()) {
                                $retry332 = true;
                                $keepReads333 = false;
                                continue $label330;
                            }
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
                            if ($tm336.checkForStaleObjects())
                                continue $label330;
                            $retry332 = false;
                            throw new fabric.worker.AbortException($e334);
                        }
                        finally {
                            if ($commit331) {
                                fabric.common.TransactionID $currentTid335 =
                                  $tm336.getCurrentTid();
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
                                    if ($tm336.checkForStaleObjects()) {
                                        $retry332 = true;
                                        $keepReads333 = false;
                                        continue $label330;
                                    }
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
                            }
                            else if ($keepReads333) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                    int $backoff348 = 1;
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
                                if ($backoff348 < 5000) $backoff348 *= 2;
                            }
                            $doBackoff349 = $backoff348 <= 32 || !$doBackoff349;
                        }
                        $commit342 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { result = tmp.computeVelocity(weakStats); }
                            catch (final fabric.worker.RetryException $e345) {
                                throw $e345;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e345) {
                                throw $e345;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e345) {
                                throw $e345;
                            }
                            catch (final Throwable $e345) {
                                $tm347.getCurrentLog().checkRetrySignal();
                                throw $e345;
                            }
                        }
                        catch (final fabric.worker.RetryException $e345) {
                            $commit342 = false;
                            continue $label341;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e345) {
                            $commit342 = false;
                            $retry343 = false;
                            $keepReads344 = $e345.keepReads;
                            if ($tm347.checkForStaleObjects()) {
                                $retry343 = true;
                                $keepReads344 = false;
                                continue $label341;
                            }
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
                            if ($tm347.checkForStaleObjects())
                                continue $label341;
                            $retry343 = false;
                            throw new fabric.worker.AbortException($e345);
                        }
                        finally {
                            if ($commit342) {
                                fabric.common.TransactionID $currentTid346 =
                                  $tm347.getCurrentTid();
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
                                    if ($tm347.checkForStaleObjects()) {
                                        $retry343 = true;
                                        $keepReads344 = false;
                                        continue $label341;
                                    }
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
                            }
                            else if ($keepReads344) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                    int $backoff359 = 1;
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
                                if ($backoff359 < 5000) $backoff359 *= 2;
                            }
                            $doBackoff360 = $backoff359 <= 32 || !$doBackoff360;
                        }
                        $commit353 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { result = tmp.computeNoise(weakStats); }
                            catch (final fabric.worker.RetryException $e356) {
                                throw $e356;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e356) {
                                throw $e356;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e356) {
                                throw $e356;
                            }
                            catch (final Throwable $e356) {
                                $tm358.getCurrentLog().checkRetrySignal();
                                throw $e356;
                            }
                        }
                        catch (final fabric.worker.RetryException $e356) {
                            $commit353 = false;
                            continue $label352;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e356) {
                            $commit353 = false;
                            $retry354 = false;
                            $keepReads355 = $e356.keepReads;
                            if ($tm358.checkForStaleObjects()) {
                                $retry354 = true;
                                $keepReads355 = false;
                                continue $label352;
                            }
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
                            if ($tm358.checkForStaleObjects())
                                continue $label352;
                            $retry354 = false;
                            throw new fabric.worker.AbortException($e356);
                        }
                        finally {
                            if ($commit353) {
                                fabric.common.TransactionID $currentTid357 =
                                  $tm358.getCurrentTid();
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
                                    if ($tm358.checkForStaleObjects()) {
                                        $retry354 = true;
                                        $keepReads355 = false;
                                        continue $label352;
                                    }
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
                            }
                            else if ($keepReads355) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
    
    public static final byte[] $classHash = new byte[] { -77, -6, 125, -81, 45,
    -109, -114, -63, -61, 95, -110, 75, 14, -11, 55, 18, -106, 100, 58, 17, -72,
    -105, -67, -37, -26, -124, 84, -11, -67, 89, -19, -7 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1550000445000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVaC3AV1Rn+9ybkRSAhGOQZQggor1xRqwJWC1cegYAxIVhDNW72noQ1e+9eds8NNyqObbUwzhhHiSg+GK04VUyx6ji22lQpavExTqVaqxZhfFQcZDrWQduq2P8/e+4z926yMynDnm+z5/zn/O/zn73bfwJG2RbUdKjtulHHeyLMrluhttc3NKqWzYIBQ7Xt9fi0TRudX7/z2K+CVT7wNUCppobNsK6pRlvY5jC24Rq1W/WHGfe3NNUv2QjFGhGuUu1NHHwbl8UsqI6YRk+nYXK5yKD575zn77vrqvIn86CsFcr0cDNXua4FzDBnMd4KpSEWameWvTQYZMFWGBdmLNjMLF019GtxoBluhQpb7wyrPGoxu4nZptFNAyvsaIRZYs34Q2LfRLatqMZNC9kvd9iPct3wN+g2X9IABR06M4L2ZrgB8htgVIehduLACQ1xKfxiRv8Keo7DS3Rk0+pQNRYnye/Sw0EO0zMpEhLXrsEBSFoYYnyTmVgqP6ziA6hwWDLUcKe/mVt6uBOHjjKjuAqHyTknxUFFEVXrUjtZG4eJmeManS4cVSzUQiQcKjOHiZnQZpMzbJZirRPrLuy9Lrwq7AMFeQ4yzSD+i5CoKoOoiXUwi4U15hCWzm3YqU4Y2O4DwMGVGYOdMc9c/8WP5le9cNAZMyXLmEvbr2Eab9P2tI99c2pgzqI8YqMoYto6uUKa5MKqjbJnSSyC3j4hMSN11sU7X2h6+Yob97LjPiiphwLNNKIh9KpxmhmK6AazVrIws1TOgvVQzMLBgOivh0K8b9DDzHl6aUeHzXg95BviUYEp/kYVdeAUpKJCvNfDHWb8PqLyTeI+FgGAQrxAwf/9AAu34n0VQN5LHBr9m8wQ87cbUbYF3duPF1MtbZMf49bStQWaGenx25bmt6JhruNI57kfXQnB9jeroYjBgmvFn3XIS+T/MGeM5Cjfoiio4umaGWTtqo32kr6zrNHA8FhlGkFmtWlG70A9jB/YJfynmHzeRr8VGlLQ5lMzs0UqbV902fIv9rW95vge0UoFcpjq8FgneaxL4xHZKqWoqsM8VYd5ql+J1QV21z8mnKfAFlGWmKkUZ1ocMVTeYVqhGCiKEOs0QS+8Bm3ehbkE00XpnOYrV1+9vSYP3TWyJZ8siENrM4MnmXLq8U7FiGjTyrYd++rxnVvNZBhxqB0U3YMpKTprMnVkmRoLYvZLTj+3Wn26bWBrrY8ySzEmPa6iW2IGqcpcIy1Kl8QzHmljVAOMJh2oBnXF01QJ32SZW5JPhO3HUlPhuAEpK4NBkSx/2By5/29vfHaO2EbiebUsJQE3M74kJZZpsjIRteOSul9vMYbjDt/duOPOE9s2CsXjiJnZFqylNoAxrGLwmtbNBze/e+SDPW/5ksbiUBCJthu6FhOyjPse/yl4naKLApIeEGJaDshkUJ3IBhFaeXaSN8wLBuYmZN2ubQmHzKDeoavtBiNP+bZs1sKnP+8td8xt4BNHeRbMH3qC5PNJy+DG1676ukpMo2i0LyX1lxzmJLvxyZmXWpbaQ3zEfnpo2q4/qfej52OqsvVrmcg+IPQBwoBnC10sEO3CjL5zqalxtDVVPC+wByf+FbSDJn2x1d9/3+TARcediE/4Is0xI0vEb1BTwuTsvaGTvpqCl3xQ2ArlYvNWw3yDiqkL3aAVt187IB82wJi0/vSt1Nk3liRibWpmHKQsmxkFyUyD9zSa7kscx3ccBxVRTkqahFc1QH6+g3nfUO/4CLWnxRQQN4sFyUzRzqZmjlCkj0NhxNK70bM4FOuhUJST7cUq8zjkdbGeLKputPQQRku33GPZ9r5bvq/r7XPczClEZg6qBVJpnGJErDJGLBXDVWa4rSIoVnz6+NbnHtm6zdmoK9K31eXhaOjXf/3u9bq7j76SJVXnG6aTbsuFDs5LqLAMnH0PWgBGXyfRyKLCVTlUSLdzqbk4rjZUKW5uvEkMXSoFJLgEYz9oYvAzV042IgcfSvxLFk4avXKyjP5c67qmClBaL3FxljVbvK65Yeg1O3Gt30h8MMuarV7XXJdzzVJacxZm3zkANY9IvC3Lmm3Z18SsXRyxTI7hzIKxxLQ+mna0nK5X4s0p03I87+DJwhYUlRzOlBXDFtPqYlaicGiKhsNYDjiFQ3OCYFJmYSBEi+VgUaglyZv4VyCLuhcl/iGFt5SEChSA03LV3yL49vysb3fw0ocX+mRWXo4a4WZkgcG6mZEyVS2F8qDz3Vpx6kim2KPHpy0KdH3S6YTy9IyVM0c/urb/lZWztTt8kJfIpYOOOulES9IzaInF8KQWXp+WR6sTuiomHazBaz7AqN9LvDbVPZJONZOarnQXKJIkPRLtTDUndzZf0ocDwl3F1Ne67H/XUxPlMM3xnVrpNLVp1WZtkkErXazpeF2GXjpLYpk3sYhkrMSi3GKlMvxzlz4RHDegX3cy3mKzRhG32RJlYbtpGkwNZxNpKl6XIz+PSLzTm0hE0iexd3gi3ebSdzs1t3AoQZEceZqEYXNxfiWmozIHR3/rjXMi+UbiyeFxfrdL3z3U7EjlfJkr5xpy3i1R88Y5kbRL/MnwOH/Qpe8hau5L5XyDK+c6LntY4qveOCeSVyQeGB7ne136+ql5OJXzdTk5PwPoFABjjkjc741zInlB4rO5OVeS24eTjJ5yYf9pavYh+1ztYk4Gyha9+d2mHswmUi1eWGWN7ZC4xptIRLJa4iUeRHreRSSh1Wc5FJEXURFPfz+Tjfmz8dqGBcxmiau8MU8kKyUuzc18yg4hapmAmPqgiwTCow+kGSWbDKIIOgev2/DksFriTBcZ2gcVOoKkRuKkIQ0QL3uqspc9otJZq0ayFzuCmzddxH6Hmtc5vV8NRaKcJYyXGUyiHGrCawdy/bbEvhyCZy03L+L0yoZeEWcUf+Vyth0Sb86tkxS7llNzSKx61EXAD6l5l8N4Z+m2oeRMGHgPQMXnEl/yZmAieVHic8OKMEeOz1zkOE7NxxzGSgFS8kYgl6lwbx/fI/ECT6ai5nAWM9FM50uc49VMX7qIJ3biExwq083kIqUw1Hl4/Q6gsldi0JuhiEST2OrBUN+6SHKKmq+58xYaRWhQbd4SCaqc5bQVHpdhAGDCDAcrD4+IrWimv0t8w6OtlILcEipUwiq4I05Mt5W7oMJcF+L1BsDEZRIrvJmLSMZJdKmjM82llLkIM46aEnQ8KYUjQT39ONStGjkz4Ua8DiEbRyTmKp+9mYxm6pP4C68mm+Qi5RRqxnOYmm6yoYVNRNlHAFOKHJz8gTezEclhiW95MFuNi0B0VlOmcSiL53NmmJrOe3IajGLsU5Rhl8SWETEYzbRe4gqvBpvvIl8dNbM5TMjYtlzEjO9cCkJVTOJlnkwlSBolrvRgqh+4iHI+NWclS4x1pm67lhgK0k7Pc7DqjyNhJzHTfolPebXTxS7CUQ2qLBpUXuSUUdTAk5GTaQAzDkh80sVIg2tgQfKExMdyC5PK5iqXvtXUBLB256bzu3W84iwXv0PQW/i6lI5BNWY2CauRPSxxaxZJrPEmIZHMkDhleBK2uPRdTk0jhzG63YxiGKyZm5Ywz7ZsMXQxroxxNPOIxH5vMUQkj0n85bB8LXlEUa5yEeNqaq7AnBcVaXu5zfUQohib9aw1B7nArXbWqxL3erMCkTwq8SEPyaDTRQadmnYOo7pdDxprcMIVALNXShwzIlmAZip1cNYpr1lgs4tU9FDpwsCQWSCncAmz4KZxxj6JvTmEy2EWIrlV4jYPZnF5P6rQ+1ElyqHQHuJYQYb5McCZCxw848sRMQzN9C+JH3s1zE0uclERpdyApyZpGBfxhGnmIxMdaKF7Jca8mYZItkjc7ME0t7qIQL+wKNs5lBhDHyTW4azXAMytdnDORyNiHZrpQ4ku9Vt269zlItouam7Ho5K0jruE8ddHCtY28+6XyL0ZiEhsiSEPBnrARQrK8cq96GPR4R0dMG6V69HV5js477MRMRLNdEzie16N5PKyVaHdT9mTPJkPLWUikm4BWPCERI9Jjkhuleglybm8d1XovauyDwud7qEOCxRHvQB15zq44OsRMRHN9JXEY15NNOAi2PPUPIOFQXz7cZEvsQPdB+B/TuK93oxDJPdI7MstyCDjvOwiw0Fq9mNhEHY9HtD+8wDAWRc46D+Vg3NvlqGZvpP4T6+W+bOLVIeoeTVZGCSFi2E5mvbrI335MyXLN3jy21At8CLb88ma+ZU5vr+bOOhrXUm3b3dZ0em7W94RX5QlvvssboCijqhhpH4hk3JfELFYhy4UWux8LxMR8ryDaS79gz0uPoelO1EEve2Me49DgTOO/npfaHOyaOKHi8qM7/4cJYghYsHJUYu+Q+7/8vR/FxStPyo+/kKtVj/1362PL9jRu/9A2x1rxp48v2JncPG439418P4/blp/cuCKE//5H0PtcwUfLQAA";
}
