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
                    double result$var243 = result;
                    fabric.worker.transaction.TransactionManager $tm250 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled253 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff251 = 1;
                    boolean $doBackoff252 = true;
                    boolean $retry246 = true;
                    boolean $keepReads247 = false;
                    $label244: for (boolean $commit245 = false; !$commit245; ) {
                        if ($backoffEnabled253) {
                            if ($doBackoff252) {
                                if ($backoff251 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff251));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e248) {
                                            
                                        }
                                    }
                                }
                                if ($backoff251 < 5000) $backoff251 *= 2;
                            }
                            $doBackoff252 = $backoff251 <= 32 || !$doBackoff252;
                        }
                        $commit245 = true;
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
                            catch (final fabric.worker.RetryException $e248) {
                                throw $e248;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e248) {
                                throw $e248;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e248) {
                                throw $e248;
                            }
                            catch (final Throwable $e248) {
                                $tm250.getCurrentLog().checkRetrySignal();
                                throw $e248;
                            }
                        }
                        catch (final fabric.worker.RetryException $e248) {
                            $commit245 = false;
                            continue $label244;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e248) {
                            $commit245 = false;
                            $retry246 = false;
                            $keepReads247 = $e248.keepReads;
                            if ($tm250.checkForStaleObjects()) {
                                $retry246 = true;
                                $keepReads247 = false;
                                continue $label244;
                            }
                            fabric.common.TransactionID $currentTid249 =
                              $tm250.getCurrentTid();
                            if ($e248.tid == null ||
                                  !$e248.tid.isDescendantOf($currentTid249)) {
                                throw $e248;
                            }
                            throw new fabric.worker.UserAbortException($e248);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e248) {
                            $commit245 = false;
                            fabric.common.TransactionID $currentTid249 =
                              $tm250.getCurrentTid();
                            if ($e248.tid.isDescendantOf($currentTid249))
                                continue $label244;
                            if ($currentTid249.parent != null) {
                                $retry246 = false;
                                throw $e248;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e248) {
                            $commit245 = false;
                            if ($tm250.checkForStaleObjects())
                                continue $label244;
                            $retry246 = false;
                            throw new fabric.worker.AbortException($e248);
                        }
                        finally {
                            if ($commit245) {
                                fabric.common.TransactionID $currentTid249 =
                                  $tm250.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e248) {
                                    $commit245 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e248) {
                                    $commit245 = false;
                                    $retry246 = false;
                                    $keepReads247 = $e248.keepReads;
                                    if ($tm250.checkForStaleObjects()) {
                                        $retry246 = true;
                                        $keepReads247 = false;
                                        continue $label244;
                                    }
                                    if ($e248.tid ==
                                          null ||
                                          !$e248.tid.isDescendantOf(
                                                       $currentTid249))
                                        throw $e248;
                                    throw new fabric.worker.UserAbortException(
                                            $e248);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e248) {
                                    $commit245 = false;
                                    $currentTid249 = $tm250.getCurrentTid();
                                    if ($currentTid249 != null) {
                                        if ($e248.tid.equals($currentTid249) ||
                                              !$e248.tid.isDescendantOf(
                                                           $currentTid249)) {
                                            throw $e248;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads247) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit245) {
                                { result = result$var243; }
                                if ($retry246) { continue $label244; }
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
                    long rtn$var254 = rtn;
                    fabric.worker.transaction.TransactionManager $tm261 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled264 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff262 = 1;
                    boolean $doBackoff263 = true;
                    boolean $retry257 = true;
                    boolean $keepReads258 = false;
                    $label255: for (boolean $commit256 = false; !$commit256; ) {
                        if ($backoffEnabled264) {
                            if ($doBackoff263) {
                                if ($backoff262 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff262));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e259) {
                                            
                                        }
                                    }
                                }
                                if ($backoff262 < 5000) $backoff262 *= 2;
                            }
                            $doBackoff263 = $backoff262 <= 32 || !$doBackoff263;
                        }
                        $commit256 = true;
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
                            catch (final fabric.worker.RetryException $e259) {
                                throw $e259;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e259) {
                                throw $e259;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e259) {
                                throw $e259;
                            }
                            catch (final Throwable $e259) {
                                $tm261.getCurrentLog().checkRetrySignal();
                                throw $e259;
                            }
                        }
                        catch (final fabric.worker.RetryException $e259) {
                            $commit256 = false;
                            continue $label255;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e259) {
                            $commit256 = false;
                            $retry257 = false;
                            $keepReads258 = $e259.keepReads;
                            if ($tm261.checkForStaleObjects()) {
                                $retry257 = true;
                                $keepReads258 = false;
                                continue $label255;
                            }
                            fabric.common.TransactionID $currentTid260 =
                              $tm261.getCurrentTid();
                            if ($e259.tid == null ||
                                  !$e259.tid.isDescendantOf($currentTid260)) {
                                throw $e259;
                            }
                            throw new fabric.worker.UserAbortException($e259);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e259) {
                            $commit256 = false;
                            fabric.common.TransactionID $currentTid260 =
                              $tm261.getCurrentTid();
                            if ($e259.tid.isDescendantOf($currentTid260))
                                continue $label255;
                            if ($currentTid260.parent != null) {
                                $retry257 = false;
                                throw $e259;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e259) {
                            $commit256 = false;
                            if ($tm261.checkForStaleObjects())
                                continue $label255;
                            $retry257 = false;
                            throw new fabric.worker.AbortException($e259);
                        }
                        finally {
                            if ($commit256) {
                                fabric.common.TransactionID $currentTid260 =
                                  $tm261.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e259) {
                                    $commit256 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e259) {
                                    $commit256 = false;
                                    $retry257 = false;
                                    $keepReads258 = $e259.keepReads;
                                    if ($tm261.checkForStaleObjects()) {
                                        $retry257 = true;
                                        $keepReads258 = false;
                                        continue $label255;
                                    }
                                    if ($e259.tid ==
                                          null ||
                                          !$e259.tid.isDescendantOf(
                                                       $currentTid260))
                                        throw $e259;
                                    throw new fabric.worker.UserAbortException(
                                            $e259);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e259) {
                                    $commit256 = false;
                                    $currentTid260 = $tm261.getCurrentTid();
                                    if ($currentTid260 != null) {
                                        if ($e259.tid.equals($currentTid260) ||
                                              !$e259.tid.isDescendantOf(
                                                           $currentTid260)) {
                                            throw $e259;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads258) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit256) {
                                { rtn = rtn$var254; }
                                if ($retry257) { continue $label255; }
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
                    long rtn$var265 = rtn;
                    fabric.worker.transaction.TransactionManager $tm272 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled275 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff273 = 1;
                    boolean $doBackoff274 = true;
                    boolean $retry268 = true;
                    boolean $keepReads269 = false;
                    $label266: for (boolean $commit267 = false; !$commit267; ) {
                        if ($backoffEnabled275) {
                            if ($doBackoff274) {
                                if ($backoff273 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff273));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e270) {
                                            
                                        }
                                    }
                                }
                                if ($backoff273 < 5000) $backoff273 *= 2;
                            }
                            $doBackoff274 = $backoff273 <= 32 || !$doBackoff274;
                        }
                        $commit267 = true;
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
                            catch (final fabric.worker.RetryException $e270) {
                                throw $e270;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e270) {
                                throw $e270;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e270) {
                                throw $e270;
                            }
                            catch (final Throwable $e270) {
                                $tm272.getCurrentLog().checkRetrySignal();
                                throw $e270;
                            }
                        }
                        catch (final fabric.worker.RetryException $e270) {
                            $commit267 = false;
                            continue $label266;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e270) {
                            $commit267 = false;
                            $retry268 = false;
                            $keepReads269 = $e270.keepReads;
                            if ($tm272.checkForStaleObjects()) {
                                $retry268 = true;
                                $keepReads269 = false;
                                continue $label266;
                            }
                            fabric.common.TransactionID $currentTid271 =
                              $tm272.getCurrentTid();
                            if ($e270.tid == null ||
                                  !$e270.tid.isDescendantOf($currentTid271)) {
                                throw $e270;
                            }
                            throw new fabric.worker.UserAbortException($e270);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e270) {
                            $commit267 = false;
                            fabric.common.TransactionID $currentTid271 =
                              $tm272.getCurrentTid();
                            if ($e270.tid.isDescendantOf($currentTid271))
                                continue $label266;
                            if ($currentTid271.parent != null) {
                                $retry268 = false;
                                throw $e270;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e270) {
                            $commit267 = false;
                            if ($tm272.checkForStaleObjects())
                                continue $label266;
                            $retry268 = false;
                            throw new fabric.worker.AbortException($e270);
                        }
                        finally {
                            if ($commit267) {
                                fabric.common.TransactionID $currentTid271 =
                                  $tm272.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e270) {
                                    $commit267 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e270) {
                                    $commit267 = false;
                                    $retry268 = false;
                                    $keepReads269 = $e270.keepReads;
                                    if ($tm272.checkForStaleObjects()) {
                                        $retry268 = true;
                                        $keepReads269 = false;
                                        continue $label266;
                                    }
                                    if ($e270.tid ==
                                          null ||
                                          !$e270.tid.isDescendantOf(
                                                       $currentTid271))
                                        throw $e270;
                                    throw new fabric.worker.UserAbortException(
                                            $e270);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e270) {
                                    $commit267 = false;
                                    $currentTid271 = $tm272.getCurrentTid();
                                    if ($currentTid271 != null) {
                                        if ($e270.tid.equals($currentTid271) ||
                                              !$e270.tid.isDescendantOf(
                                                           $currentTid271)) {
                                            throw $e270;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads269) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit267) {
                                { rtn = rtn$var265; }
                                if ($retry268) { continue $label266; }
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
                    double rtn$var276 = rtn;
                    fabric.worker.transaction.TransactionManager $tm283 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled286 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff284 = 1;
                    boolean $doBackoff285 = true;
                    boolean $retry279 = true;
                    boolean $keepReads280 = false;
                    $label277: for (boolean $commit278 = false; !$commit278; ) {
                        if ($backoffEnabled286) {
                            if ($doBackoff285) {
                                if ($backoff284 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff284));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e281) {
                                            
                                        }
                                    }
                                }
                                if ($backoff284 < 5000) $backoff284 *= 2;
                            }
                            $doBackoff285 = $backoff284 <= 32 || !$doBackoff285;
                        }
                        $commit278 = true;
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
                            catch (final fabric.worker.RetryException $e281) {
                                throw $e281;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e281) {
                                throw $e281;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e281) {
                                throw $e281;
                            }
                            catch (final Throwable $e281) {
                                $tm283.getCurrentLog().checkRetrySignal();
                                throw $e281;
                            }
                        }
                        catch (final fabric.worker.RetryException $e281) {
                            $commit278 = false;
                            continue $label277;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e281) {
                            $commit278 = false;
                            $retry279 = false;
                            $keepReads280 = $e281.keepReads;
                            if ($tm283.checkForStaleObjects()) {
                                $retry279 = true;
                                $keepReads280 = false;
                                continue $label277;
                            }
                            fabric.common.TransactionID $currentTid282 =
                              $tm283.getCurrentTid();
                            if ($e281.tid == null ||
                                  !$e281.tid.isDescendantOf($currentTid282)) {
                                throw $e281;
                            }
                            throw new fabric.worker.UserAbortException($e281);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e281) {
                            $commit278 = false;
                            fabric.common.TransactionID $currentTid282 =
                              $tm283.getCurrentTid();
                            if ($e281.tid.isDescendantOf($currentTid282))
                                continue $label277;
                            if ($currentTid282.parent != null) {
                                $retry279 = false;
                                throw $e281;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e281) {
                            $commit278 = false;
                            if ($tm283.checkForStaleObjects())
                                continue $label277;
                            $retry279 = false;
                            throw new fabric.worker.AbortException($e281);
                        }
                        finally {
                            if ($commit278) {
                                fabric.common.TransactionID $currentTid282 =
                                  $tm283.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e281) {
                                    $commit278 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e281) {
                                    $commit278 = false;
                                    $retry279 = false;
                                    $keepReads280 = $e281.keepReads;
                                    if ($tm283.checkForStaleObjects()) {
                                        $retry279 = true;
                                        $keepReads280 = false;
                                        continue $label277;
                                    }
                                    if ($e281.tid ==
                                          null ||
                                          !$e281.tid.isDescendantOf(
                                                       $currentTid282))
                                        throw $e281;
                                    throw new fabric.worker.UserAbortException(
                                            $e281);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e281) {
                                    $commit278 = false;
                                    $currentTid282 = $tm283.getCurrentTid();
                                    if ($currentTid282 != null) {
                                        if ($e281.tid.equals($currentTid282) ||
                                              !$e281.tid.isDescendantOf(
                                                           $currentTid282)) {
                                            throw $e281;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads280) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit278) {
                                { rtn = rtn$var276; }
                                if ($retry279) { continue $label277; }
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
                    double rtn$var287 = rtn;
                    fabric.worker.transaction.TransactionManager $tm294 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled297 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff295 = 1;
                    boolean $doBackoff296 = true;
                    boolean $retry290 = true;
                    boolean $keepReads291 = false;
                    $label288: for (boolean $commit289 = false; !$commit289; ) {
                        if ($backoffEnabled297) {
                            if ($doBackoff296) {
                                if ($backoff295 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff295));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e292) {
                                            
                                        }
                                    }
                                }
                                if ($backoff295 < 5000) $backoff295 *= 2;
                            }
                            $doBackoff296 = $backoff295 <= 32 || !$doBackoff296;
                        }
                        $commit289 = true;
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
                            catch (final fabric.worker.RetryException $e292) {
                                throw $e292;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e292) {
                                throw $e292;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e292) {
                                throw $e292;
                            }
                            catch (final Throwable $e292) {
                                $tm294.getCurrentLog().checkRetrySignal();
                                throw $e292;
                            }
                        }
                        catch (final fabric.worker.RetryException $e292) {
                            $commit289 = false;
                            continue $label288;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e292) {
                            $commit289 = false;
                            $retry290 = false;
                            $keepReads291 = $e292.keepReads;
                            if ($tm294.checkForStaleObjects()) {
                                $retry290 = true;
                                $keepReads291 = false;
                                continue $label288;
                            }
                            fabric.common.TransactionID $currentTid293 =
                              $tm294.getCurrentTid();
                            if ($e292.tid == null ||
                                  !$e292.tid.isDescendantOf($currentTid293)) {
                                throw $e292;
                            }
                            throw new fabric.worker.UserAbortException($e292);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e292) {
                            $commit289 = false;
                            fabric.common.TransactionID $currentTid293 =
                              $tm294.getCurrentTid();
                            if ($e292.tid.isDescendantOf($currentTid293))
                                continue $label288;
                            if ($currentTid293.parent != null) {
                                $retry290 = false;
                                throw $e292;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e292) {
                            $commit289 = false;
                            if ($tm294.checkForStaleObjects())
                                continue $label288;
                            $retry290 = false;
                            throw new fabric.worker.AbortException($e292);
                        }
                        finally {
                            if ($commit289) {
                                fabric.common.TransactionID $currentTid293 =
                                  $tm294.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e292) {
                                    $commit289 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e292) {
                                    $commit289 = false;
                                    $retry290 = false;
                                    $keepReads291 = $e292.keepReads;
                                    if ($tm294.checkForStaleObjects()) {
                                        $retry290 = true;
                                        $keepReads291 = false;
                                        continue $label288;
                                    }
                                    if ($e292.tid ==
                                          null ||
                                          !$e292.tid.isDescendantOf(
                                                       $currentTid293))
                                        throw $e292;
                                    throw new fabric.worker.UserAbortException(
                                            $e292);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e292) {
                                    $commit289 = false;
                                    $currentTid293 = $tm294.getCurrentTid();
                                    if ($currentTid293 != null) {
                                        if ($e292.tid.equals($currentTid293) ||
                                              !$e292.tid.isDescendantOf(
                                                           $currentTid293)) {
                                            throw $e292;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads291) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit289) {
                                { rtn = rtn$var287; }
                                if ($retry290) { continue $label288; }
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
                    double rtn$var298 = rtn;
                    fabric.worker.transaction.TransactionManager $tm305 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled308 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff306 = 1;
                    boolean $doBackoff307 = true;
                    boolean $retry301 = true;
                    boolean $keepReads302 = false;
                    $label299: for (boolean $commit300 = false; !$commit300; ) {
                        if ($backoffEnabled308) {
                            if ($doBackoff307) {
                                if ($backoff306 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff306));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e303) {
                                            
                                        }
                                    }
                                }
                                if ($backoff306 < 5000) $backoff306 *= 2;
                            }
                            $doBackoff307 = $backoff306 <= 32 || !$doBackoff307;
                        }
                        $commit300 = true;
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
                            catch (final fabric.worker.RetryException $e303) {
                                throw $e303;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e303) {
                                throw $e303;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e303) {
                                throw $e303;
                            }
                            catch (final Throwable $e303) {
                                $tm305.getCurrentLog().checkRetrySignal();
                                throw $e303;
                            }
                        }
                        catch (final fabric.worker.RetryException $e303) {
                            $commit300 = false;
                            continue $label299;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e303) {
                            $commit300 = false;
                            $retry301 = false;
                            $keepReads302 = $e303.keepReads;
                            if ($tm305.checkForStaleObjects()) {
                                $retry301 = true;
                                $keepReads302 = false;
                                continue $label299;
                            }
                            fabric.common.TransactionID $currentTid304 =
                              $tm305.getCurrentTid();
                            if ($e303.tid == null ||
                                  !$e303.tid.isDescendantOf($currentTid304)) {
                                throw $e303;
                            }
                            throw new fabric.worker.UserAbortException($e303);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e303) {
                            $commit300 = false;
                            fabric.common.TransactionID $currentTid304 =
                              $tm305.getCurrentTid();
                            if ($e303.tid.isDescendantOf($currentTid304))
                                continue $label299;
                            if ($currentTid304.parent != null) {
                                $retry301 = false;
                                throw $e303;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e303) {
                            $commit300 = false;
                            if ($tm305.checkForStaleObjects())
                                continue $label299;
                            $retry301 = false;
                            throw new fabric.worker.AbortException($e303);
                        }
                        finally {
                            if ($commit300) {
                                fabric.common.TransactionID $currentTid304 =
                                  $tm305.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e303) {
                                    $commit300 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e303) {
                                    $commit300 = false;
                                    $retry301 = false;
                                    $keepReads302 = $e303.keepReads;
                                    if ($tm305.checkForStaleObjects()) {
                                        $retry301 = true;
                                        $keepReads302 = false;
                                        continue $label299;
                                    }
                                    if ($e303.tid ==
                                          null ||
                                          !$e303.tid.isDescendantOf(
                                                       $currentTid304))
                                        throw $e303;
                                    throw new fabric.worker.UserAbortException(
                                            $e303);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e303) {
                                    $commit300 = false;
                                    $currentTid304 = $tm305.getCurrentTid();
                                    if ($currentTid304 != null) {
                                        if ($e303.tid.equals($currentTid304) ||
                                              !$e303.tid.isDescendantOf(
                                                           $currentTid304)) {
                                            throw $e303;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads302) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit300) {
                                { rtn = rtn$var298; }
                                if ($retry301) { continue $label299; }
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
                    double result$var309 = result;
                    fabric.worker.transaction.TransactionManager $tm316 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled319 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff317 = 1;
                    boolean $doBackoff318 = true;
                    boolean $retry312 = true;
                    boolean $keepReads313 = false;
                    $label310: for (boolean $commit311 = false; !$commit311; ) {
                        if ($backoffEnabled319) {
                            if ($doBackoff318) {
                                if ($backoff317 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff317));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e314) {
                                            
                                        }
                                    }
                                }
                                if ($backoff317 < 5000) $backoff317 *= 2;
                            }
                            $doBackoff318 = $backoff317 <= 32 || !$doBackoff318;
                        }
                        $commit311 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { result = tmp.computeValue(weakStats); }
                            catch (final fabric.worker.RetryException $e314) {
                                throw $e314;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e314) {
                                throw $e314;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e314) {
                                throw $e314;
                            }
                            catch (final Throwable $e314) {
                                $tm316.getCurrentLog().checkRetrySignal();
                                throw $e314;
                            }
                        }
                        catch (final fabric.worker.RetryException $e314) {
                            $commit311 = false;
                            continue $label310;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e314) {
                            $commit311 = false;
                            $retry312 = false;
                            $keepReads313 = $e314.keepReads;
                            if ($tm316.checkForStaleObjects()) {
                                $retry312 = true;
                                $keepReads313 = false;
                                continue $label310;
                            }
                            fabric.common.TransactionID $currentTid315 =
                              $tm316.getCurrentTid();
                            if ($e314.tid == null ||
                                  !$e314.tid.isDescendantOf($currentTid315)) {
                                throw $e314;
                            }
                            throw new fabric.worker.UserAbortException($e314);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e314) {
                            $commit311 = false;
                            fabric.common.TransactionID $currentTid315 =
                              $tm316.getCurrentTid();
                            if ($e314.tid.isDescendantOf($currentTid315))
                                continue $label310;
                            if ($currentTid315.parent != null) {
                                $retry312 = false;
                                throw $e314;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e314) {
                            $commit311 = false;
                            if ($tm316.checkForStaleObjects())
                                continue $label310;
                            $retry312 = false;
                            throw new fabric.worker.AbortException($e314);
                        }
                        finally {
                            if ($commit311) {
                                fabric.common.TransactionID $currentTid315 =
                                  $tm316.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e314) {
                                    $commit311 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e314) {
                                    $commit311 = false;
                                    $retry312 = false;
                                    $keepReads313 = $e314.keepReads;
                                    if ($tm316.checkForStaleObjects()) {
                                        $retry312 = true;
                                        $keepReads313 = false;
                                        continue $label310;
                                    }
                                    if ($e314.tid ==
                                          null ||
                                          !$e314.tid.isDescendantOf(
                                                       $currentTid315))
                                        throw $e314;
                                    throw new fabric.worker.UserAbortException(
                                            $e314);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e314) {
                                    $commit311 = false;
                                    $currentTid315 = $tm316.getCurrentTid();
                                    if ($currentTid315 != null) {
                                        if ($e314.tid.equals($currentTid315) ||
                                              !$e314.tid.isDescendantOf(
                                                           $currentTid315)) {
                                            throw $e314;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads313) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit311) {
                                { result = result$var309; }
                                if ($retry312) { continue $label310; }
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
                    long result$var320 = result;
                    fabric.worker.transaction.TransactionManager $tm327 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled330 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff328 = 1;
                    boolean $doBackoff329 = true;
                    boolean $retry323 = true;
                    boolean $keepReads324 = false;
                    $label321: for (boolean $commit322 = false; !$commit322; ) {
                        if ($backoffEnabled330) {
                            if ($doBackoff329) {
                                if ($backoff328 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff328));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e325) {
                                            
                                        }
                                    }
                                }
                                if ($backoff328 < 5000) $backoff328 *= 2;
                            }
                            $doBackoff329 = $backoff328 <= 32 || !$doBackoff329;
                        }
                        $commit322 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { result = tmp.computeSamples(weakStats); }
                            catch (final fabric.worker.RetryException $e325) {
                                throw $e325;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e325) {
                                throw $e325;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e325) {
                                throw $e325;
                            }
                            catch (final Throwable $e325) {
                                $tm327.getCurrentLog().checkRetrySignal();
                                throw $e325;
                            }
                        }
                        catch (final fabric.worker.RetryException $e325) {
                            $commit322 = false;
                            continue $label321;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e325) {
                            $commit322 = false;
                            $retry323 = false;
                            $keepReads324 = $e325.keepReads;
                            if ($tm327.checkForStaleObjects()) {
                                $retry323 = true;
                                $keepReads324 = false;
                                continue $label321;
                            }
                            fabric.common.TransactionID $currentTid326 =
                              $tm327.getCurrentTid();
                            if ($e325.tid == null ||
                                  !$e325.tid.isDescendantOf($currentTid326)) {
                                throw $e325;
                            }
                            throw new fabric.worker.UserAbortException($e325);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e325) {
                            $commit322 = false;
                            fabric.common.TransactionID $currentTid326 =
                              $tm327.getCurrentTid();
                            if ($e325.tid.isDescendantOf($currentTid326))
                                continue $label321;
                            if ($currentTid326.parent != null) {
                                $retry323 = false;
                                throw $e325;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e325) {
                            $commit322 = false;
                            if ($tm327.checkForStaleObjects())
                                continue $label321;
                            $retry323 = false;
                            throw new fabric.worker.AbortException($e325);
                        }
                        finally {
                            if ($commit322) {
                                fabric.common.TransactionID $currentTid326 =
                                  $tm327.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e325) {
                                    $commit322 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e325) {
                                    $commit322 = false;
                                    $retry323 = false;
                                    $keepReads324 = $e325.keepReads;
                                    if ($tm327.checkForStaleObjects()) {
                                        $retry323 = true;
                                        $keepReads324 = false;
                                        continue $label321;
                                    }
                                    if ($e325.tid ==
                                          null ||
                                          !$e325.tid.isDescendantOf(
                                                       $currentTid326))
                                        throw $e325;
                                    throw new fabric.worker.UserAbortException(
                                            $e325);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e325) {
                                    $commit322 = false;
                                    $currentTid326 = $tm327.getCurrentTid();
                                    if ($currentTid326 != null) {
                                        if ($e325.tid.equals($currentTid326) ||
                                              !$e325.tid.isDescendantOf(
                                                           $currentTid326)) {
                                            throw $e325;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads324) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit322) {
                                { result = result$var320; }
                                if ($retry323) { continue $label321; }
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
                    long result$var331 = result;
                    fabric.worker.transaction.TransactionManager $tm338 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled341 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff339 = 1;
                    boolean $doBackoff340 = true;
                    boolean $retry334 = true;
                    boolean $keepReads335 = false;
                    $label332: for (boolean $commit333 = false; !$commit333; ) {
                        if ($backoffEnabled341) {
                            if ($doBackoff340) {
                                if ($backoff339 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff339));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e336) {
                                            
                                        }
                                    }
                                }
                                if ($backoff339 < 5000) $backoff339 *= 2;
                            }
                            $doBackoff340 = $backoff339 <= 32 || !$doBackoff340;
                        }
                        $commit333 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { result = tmp.computeLastUpdate(weakStats); }
                            catch (final fabric.worker.RetryException $e336) {
                                throw $e336;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e336) {
                                throw $e336;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e336) {
                                throw $e336;
                            }
                            catch (final Throwable $e336) {
                                $tm338.getCurrentLog().checkRetrySignal();
                                throw $e336;
                            }
                        }
                        catch (final fabric.worker.RetryException $e336) {
                            $commit333 = false;
                            continue $label332;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e336) {
                            $commit333 = false;
                            $retry334 = false;
                            $keepReads335 = $e336.keepReads;
                            if ($tm338.checkForStaleObjects()) {
                                $retry334 = true;
                                $keepReads335 = false;
                                continue $label332;
                            }
                            fabric.common.TransactionID $currentTid337 =
                              $tm338.getCurrentTid();
                            if ($e336.tid == null ||
                                  !$e336.tid.isDescendantOf($currentTid337)) {
                                throw $e336;
                            }
                            throw new fabric.worker.UserAbortException($e336);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e336) {
                            $commit333 = false;
                            fabric.common.TransactionID $currentTid337 =
                              $tm338.getCurrentTid();
                            if ($e336.tid.isDescendantOf($currentTid337))
                                continue $label332;
                            if ($currentTid337.parent != null) {
                                $retry334 = false;
                                throw $e336;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e336) {
                            $commit333 = false;
                            if ($tm338.checkForStaleObjects())
                                continue $label332;
                            $retry334 = false;
                            throw new fabric.worker.AbortException($e336);
                        }
                        finally {
                            if ($commit333) {
                                fabric.common.TransactionID $currentTid337 =
                                  $tm338.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e336) {
                                    $commit333 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e336) {
                                    $commit333 = false;
                                    $retry334 = false;
                                    $keepReads335 = $e336.keepReads;
                                    if ($tm338.checkForStaleObjects()) {
                                        $retry334 = true;
                                        $keepReads335 = false;
                                        continue $label332;
                                    }
                                    if ($e336.tid ==
                                          null ||
                                          !$e336.tid.isDescendantOf(
                                                       $currentTid337))
                                        throw $e336;
                                    throw new fabric.worker.UserAbortException(
                                            $e336);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e336) {
                                    $commit333 = false;
                                    $currentTid337 = $tm338.getCurrentTid();
                                    if ($currentTid337 != null) {
                                        if ($e336.tid.equals($currentTid337) ||
                                              !$e336.tid.isDescendantOf(
                                                           $currentTid337)) {
                                            throw $e336;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads335) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit333) {
                                { result = result$var331; }
                                if ($retry334) { continue $label332; }
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
                    double result$var342 = result;
                    fabric.worker.transaction.TransactionManager $tm349 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled352 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff350 = 1;
                    boolean $doBackoff351 = true;
                    boolean $retry345 = true;
                    boolean $keepReads346 = false;
                    $label343: for (boolean $commit344 = false; !$commit344; ) {
                        if ($backoffEnabled352) {
                            if ($doBackoff351) {
                                if ($backoff350 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff350));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e347) {
                                            
                                        }
                                    }
                                }
                                if ($backoff350 < 5000) $backoff350 *= 2;
                            }
                            $doBackoff351 = $backoff350 <= 32 || !$doBackoff351;
                        }
                        $commit344 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                result = tmp.computeUpdateInterval(weakStats);
                            }
                            catch (final fabric.worker.RetryException $e347) {
                                throw $e347;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e347) {
                                throw $e347;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e347) {
                                throw $e347;
                            }
                            catch (final Throwable $e347) {
                                $tm349.getCurrentLog().checkRetrySignal();
                                throw $e347;
                            }
                        }
                        catch (final fabric.worker.RetryException $e347) {
                            $commit344 = false;
                            continue $label343;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e347) {
                            $commit344 = false;
                            $retry345 = false;
                            $keepReads346 = $e347.keepReads;
                            if ($tm349.checkForStaleObjects()) {
                                $retry345 = true;
                                $keepReads346 = false;
                                continue $label343;
                            }
                            fabric.common.TransactionID $currentTid348 =
                              $tm349.getCurrentTid();
                            if ($e347.tid == null ||
                                  !$e347.tid.isDescendantOf($currentTid348)) {
                                throw $e347;
                            }
                            throw new fabric.worker.UserAbortException($e347);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e347) {
                            $commit344 = false;
                            fabric.common.TransactionID $currentTid348 =
                              $tm349.getCurrentTid();
                            if ($e347.tid.isDescendantOf($currentTid348))
                                continue $label343;
                            if ($currentTid348.parent != null) {
                                $retry345 = false;
                                throw $e347;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e347) {
                            $commit344 = false;
                            if ($tm349.checkForStaleObjects())
                                continue $label343;
                            $retry345 = false;
                            throw new fabric.worker.AbortException($e347);
                        }
                        finally {
                            if ($commit344) {
                                fabric.common.TransactionID $currentTid348 =
                                  $tm349.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e347) {
                                    $commit344 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e347) {
                                    $commit344 = false;
                                    $retry345 = false;
                                    $keepReads346 = $e347.keepReads;
                                    if ($tm349.checkForStaleObjects()) {
                                        $retry345 = true;
                                        $keepReads346 = false;
                                        continue $label343;
                                    }
                                    if ($e347.tid ==
                                          null ||
                                          !$e347.tid.isDescendantOf(
                                                       $currentTid348))
                                        throw $e347;
                                    throw new fabric.worker.UserAbortException(
                                            $e347);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e347) {
                                    $commit344 = false;
                                    $currentTid348 = $tm349.getCurrentTid();
                                    if ($currentTid348 != null) {
                                        if ($e347.tid.equals($currentTid348) ||
                                              !$e347.tid.isDescendantOf(
                                                           $currentTid348)) {
                                            throw $e347;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads346) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit344) {
                                { result = result$var342; }
                                if ($retry345) { continue $label343; }
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
                    double result$var353 = result;
                    fabric.worker.transaction.TransactionManager $tm360 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled363 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff361 = 1;
                    boolean $doBackoff362 = true;
                    boolean $retry356 = true;
                    boolean $keepReads357 = false;
                    $label354: for (boolean $commit355 = false; !$commit355; ) {
                        if ($backoffEnabled363) {
                            if ($doBackoff362) {
                                if ($backoff361 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff361));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e358) {
                                            
                                        }
                                    }
                                }
                                if ($backoff361 < 5000) $backoff361 *= 2;
                            }
                            $doBackoff362 = $backoff361 <= 32 || !$doBackoff362;
                        }
                        $commit355 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { result = tmp.computeVelocity(weakStats); }
                            catch (final fabric.worker.RetryException $e358) {
                                throw $e358;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e358) {
                                throw $e358;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e358) {
                                throw $e358;
                            }
                            catch (final Throwable $e358) {
                                $tm360.getCurrentLog().checkRetrySignal();
                                throw $e358;
                            }
                        }
                        catch (final fabric.worker.RetryException $e358) {
                            $commit355 = false;
                            continue $label354;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e358) {
                            $commit355 = false;
                            $retry356 = false;
                            $keepReads357 = $e358.keepReads;
                            if ($tm360.checkForStaleObjects()) {
                                $retry356 = true;
                                $keepReads357 = false;
                                continue $label354;
                            }
                            fabric.common.TransactionID $currentTid359 =
                              $tm360.getCurrentTid();
                            if ($e358.tid == null ||
                                  !$e358.tid.isDescendantOf($currentTid359)) {
                                throw $e358;
                            }
                            throw new fabric.worker.UserAbortException($e358);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e358) {
                            $commit355 = false;
                            fabric.common.TransactionID $currentTid359 =
                              $tm360.getCurrentTid();
                            if ($e358.tid.isDescendantOf($currentTid359))
                                continue $label354;
                            if ($currentTid359.parent != null) {
                                $retry356 = false;
                                throw $e358;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e358) {
                            $commit355 = false;
                            if ($tm360.checkForStaleObjects())
                                continue $label354;
                            $retry356 = false;
                            throw new fabric.worker.AbortException($e358);
                        }
                        finally {
                            if ($commit355) {
                                fabric.common.TransactionID $currentTid359 =
                                  $tm360.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e358) {
                                    $commit355 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e358) {
                                    $commit355 = false;
                                    $retry356 = false;
                                    $keepReads357 = $e358.keepReads;
                                    if ($tm360.checkForStaleObjects()) {
                                        $retry356 = true;
                                        $keepReads357 = false;
                                        continue $label354;
                                    }
                                    if ($e358.tid ==
                                          null ||
                                          !$e358.tid.isDescendantOf(
                                                       $currentTid359))
                                        throw $e358;
                                    throw new fabric.worker.UserAbortException(
                                            $e358);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e358) {
                                    $commit355 = false;
                                    $currentTid359 = $tm360.getCurrentTid();
                                    if ($currentTid359 != null) {
                                        if ($e358.tid.equals($currentTid359) ||
                                              !$e358.tid.isDescendantOf(
                                                           $currentTid359)) {
                                            throw $e358;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads357) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit355) {
                                { result = result$var353; }
                                if ($retry356) { continue $label354; }
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
                    double result$var364 = result;
                    fabric.worker.transaction.TransactionManager $tm371 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled374 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff372 = 1;
                    boolean $doBackoff373 = true;
                    boolean $retry367 = true;
                    boolean $keepReads368 = false;
                    $label365: for (boolean $commit366 = false; !$commit366; ) {
                        if ($backoffEnabled374) {
                            if ($doBackoff373) {
                                if ($backoff372 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff372));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e369) {
                                            
                                        }
                                    }
                                }
                                if ($backoff372 < 5000) $backoff372 *= 2;
                            }
                            $doBackoff373 = $backoff372 <= 32 || !$doBackoff373;
                        }
                        $commit366 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { result = tmp.computeNoise(weakStats); }
                            catch (final fabric.worker.RetryException $e369) {
                                throw $e369;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e369) {
                                throw $e369;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e369) {
                                throw $e369;
                            }
                            catch (final Throwable $e369) {
                                $tm371.getCurrentLog().checkRetrySignal();
                                throw $e369;
                            }
                        }
                        catch (final fabric.worker.RetryException $e369) {
                            $commit366 = false;
                            continue $label365;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e369) {
                            $commit366 = false;
                            $retry367 = false;
                            $keepReads368 = $e369.keepReads;
                            if ($tm371.checkForStaleObjects()) {
                                $retry367 = true;
                                $keepReads368 = false;
                                continue $label365;
                            }
                            fabric.common.TransactionID $currentTid370 =
                              $tm371.getCurrentTid();
                            if ($e369.tid == null ||
                                  !$e369.tid.isDescendantOf($currentTid370)) {
                                throw $e369;
                            }
                            throw new fabric.worker.UserAbortException($e369);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e369) {
                            $commit366 = false;
                            fabric.common.TransactionID $currentTid370 =
                              $tm371.getCurrentTid();
                            if ($e369.tid.isDescendantOf($currentTid370))
                                continue $label365;
                            if ($currentTid370.parent != null) {
                                $retry367 = false;
                                throw $e369;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e369) {
                            $commit366 = false;
                            if ($tm371.checkForStaleObjects())
                                continue $label365;
                            $retry367 = false;
                            throw new fabric.worker.AbortException($e369);
                        }
                        finally {
                            if ($commit366) {
                                fabric.common.TransactionID $currentTid370 =
                                  $tm371.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e369) {
                                    $commit366 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e369) {
                                    $commit366 = false;
                                    $retry367 = false;
                                    $keepReads368 = $e369.keepReads;
                                    if ($tm371.checkForStaleObjects()) {
                                        $retry367 = true;
                                        $keepReads368 = false;
                                        continue $label365;
                                    }
                                    if ($e369.tid ==
                                          null ||
                                          !$e369.tid.isDescendantOf(
                                                       $currentTid370))
                                        throw $e369;
                                    throw new fabric.worker.UserAbortException(
                                            $e369);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e369) {
                                    $commit366 = false;
                                    $currentTid370 = $tm371.getCurrentTid();
                                    if ($currentTid370 != null) {
                                        if ($e369.tid.equals($currentTid370) ||
                                              !$e369.tid.isDescendantOf(
                                                           $currentTid370)) {
                                            throw $e369;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads368) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit366) {
                                { result = result$var364; }
                                if ($retry367) { continue $label365; }
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
    
    public static final byte[] $classHash = new byte[] { -82, -63, -24, -27, 53,
    -30, 9, 104, 105, 24, -106, 41, 87, 101, 32, -23, -22, 70, 8, -34, -93, -29,
    85, -16, -119, 40, -45, -117, -70, -112, 123, 102 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748453000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVaC3AV1Rn+Nwl5EUgAgzxDCAENj1zxjVgtufIIBIwJwRqqcbP33LBm793L7rnhBsWxDwu1Y6wlovhgtOJUMcWq4zjVUqU+io9xKmqttgiDOkKRaa2jdkbF/v/Zc5+5d5OdSRn2fJs95z/nf5//7N2BkzDKtqAmqHbqRj3vjTC7fpna2djUrFo2C/gN1bbX4tMObXRB445jvwlU5UFeE5RpatgM65pqdIRtDmObrlN7VF+YcV9bS+Pi9VCiEeEK1d7AIW99Q8yC6ohp9HYZJpeLDJr/jnm+/juvqXgiH8rboVwPt3KV65rfDHMW4+1QFmKhTmbZSwIBFmiHcWHGAq3M0lVD34wDzXA7jLf1rrDKoxazW5htGj00cLwdjTBLrBl/SOybyLYV1bhpIfsVDvtRrhu+Jt3mi5ugMKgzI2BvhBuhoAlGBQ21CwdObIpL4RMz+pbRcxxeqiObVlDVWJykoFsPBzjMyKRISFy7CgcgaVGI8Q1mYqmCsIoPYLzDkqGGu3yt3NLDXTh0lBnFVThMyTkpDiqOqFq32sU6OEzKHNfsdOGoEqEWIuFQmTlMzIQ2m5JhsxRrnVxzcd/14RXhPFCQ5wDTDOK/GImqMohaWJBZLKwxh7BsbtMOdeK+bXkAOLgyY7Az5ukbPvv+/KrnDzhjpmYZc3nndUzjHdruzrFvTvPXLconNoojpq2TK6RJLqzaLHsWxyLo7RMTM1Jnfbzz+ZaXr7ppDzuRB6WNUKiZRjSEXjVOM0MR3WDWchZmlspZoBFKWDjgF/2NUIT3TXqYOU8vDwZtxhuhwBCPCk3xN6ooiFOQiorwXg8Hzfh9ROUbxH0sAgBFeIGC/wcAFm7B+yqA/Jc4NPs2mCHm6zSibBO6tw8vplraBh/GraVrCzQz0uuzLc1nRcNcx5HOcx+6EoLta1VDEYMFVos/65GXyP9hzhjJUbFJUVDFMzQzwDpVG+0lfaeh2cDwWGEaAWZ1aEbfvkaYsG+n8J8S8nkb/VZoSEGbT8vMFqm0/dGGpZ/t7XjN8T2ilQrkMM3hsV7yWJ/GI7JVRlFVj3mqHvPUgBKr9+9qfFQ4T6EtoiwxUxnOdFHEUHnQtEIxUBQh1mmCXngN2rwbcwmmi7K61qtXXrutJh/dNbKpgCyIQ2szgyeZchrxTsWI6NDKtx778rEdW8xkGHGoHRTdgykpOmsydWSZGgtg9ktOP7dafapj35baPMosJZj0uIpuiRmkKnONtChdHM94pI1RTTCadKAa1BVPU6V8g2VuSj4Rth9LzXjHDUhZGQyKZPm91sh9f3vj+DliG4nn1fKUBNzK+OKUWKbJykXUjkvqfq3FGI47dFfz9jtObl0vFI8jZmVbsJZaP8awisFrWjcf2Pje4Q92v52XNBaHwki009C1mJBl3Hf4T8HrFF0UkPSAENOyXyaD6kQ2iNDKc5K8YV4wMDch63ZtWzhkBvSgrnYajDzlm/LZC5/6tK/CMbeBTxzlWTB/6AmSzyc3wE2vXfNVlZhG0WhfSuovOcxJdhOSMy+xLLWX+Ij96OD0nX9W70PPx1Rl65uZyD4g9AHCgGcLXSwQ7cKMvnOpqXG0NU08L7QHJ/5ltIMmfbHdN3DvFP8lJ5yIT/gizTEzS8SvU1PC5Ow9oS/yagpfyoOidqgQm7ca5utUTF3oBu24/dp++bAJxqT1p2+lzr6xOBFr0zLjIGXZzChIZhq8p9F0X+o4vuM4qIgKUtJkvKoBCgoczP+aeidEqD0tpoC4uUiQzBLtHGrqhCLzOBRFLL0HPYtDiR4KRTnZXqwyj0N+N+vNoupmSw9htPTIPZZt67/lu/q+fsfNnEJk1qBaIJXGKUbEKmPEUjFcZabbKoJi2SePbXn24S1bnY16fPq2ujQcDf32r9++Xn/XkVeypOoCw3TSbYXQwfkJFZaDs+9BG8Do6yUaWVS4IocK6XYuNZfG1YYqxc2Nt4ihS6SABJdh7AdMDH7mysl65OCoxLeycNLslZMG+nO165oqQFmjxIuyrNnmdc11Q6/ZhWv9TuIDWdZs97rmmpxrltGaszH71gHUPCzxtixrdmRfE7N2ScQyOYYzC8QS0+bRtKPldH0Sb06ZluN5B08WtqCo5HCmrBg2mVY3sxKFQ0s0HMZywCkcWhMEkzMLAyFaLAeLQi1J3sS/QlnUvSjxjym8pSRUoACcnqv+FsG3+8f9uwKXP7QwT2blpagRbkYWGKyHGSlT1VIoDzrfrRanjmSKPXJi+iJ/98ddTijPyFg5c/QjqwdeWT5H+1Ue5Cdy6aCjTjrR4vQMWmoxPKmF16bl0eqErkpIB6vwmg8w6g8SN6e6R9KpZlHTne4CxZKkV6KdqebkzpaX9GG/cFcx9WaX/e8GaqIcpju+Uyudpjat2qxNMmilizUDryvQS2dLLPcmFpGMlVicW6xUhn/i0ieC40b06y7G22zWLOI2W6Is6jRNg6nhbCJNw+tK5OdhiXd4E4lI+iX2DU+k21z6bqfmFg6lKJIjT4swbC7Or8Z0VO7g6G+8cU4kX0v8Ynic3+XSdzc121M5b3DlXEPOeyRq3jgnkk6JPxwe5w+49D1Izb2pnK9z5VzHZQ9JfNUb50TyisQXhsf5Hpe+AWoeSuV8TU7OzwA6BcCYwxL3e+OcSJ6X+ExuzpXk9uEkoydd2H+Kmr3IPle7mZOBskVvQY+pB7KJVIsXVlljgxJXeROJSFZKvMyDSM+5iCS0+gyHYvIiKuLp76ezMX82XluxgNkocYU35olkucQluZlP2SFELeMXUx9wkUB49AtpRskmgyiCzsHrNjw5rJQ4y0WGzkGFjiCpkTh5SAPEy56q7GWPqHRWq5HsxY7g5k0Xsd+l5nVO71dDkShnCeNlBpMoh1rw2o5cvyOxP4fgWcvNSzi9sqFXxBnFX4WcbbvEm3PrJMWuFdQcFKsecRHwKDXvcZjgLN0xlJwJA+8GGP+pxJe8GZhIXpT47LAizJHjuIscJ6j5iMNYKUBK3vDnMhXu7RN6JV7oyVTUHMpiJprpAol1Xs30uYt4Yic+yaEy3UwuUgpDnY/X7wEq+yQGvBmKSDSJ7R4M9Y2LJKeo+Yo7b6FRhCbV5m2RgMpZTlvhcRn2AUyc6WDloRGxFc30D4lveLSVUphbQoVKWAV3xEnptnIXVJjrYrzeAJjUIHG8N3MRyTiJLnV0prmUchdhxlFTio4npXAkaKQfh3pUI2cmXI/XQWTjsMRc5bM3k9FM/RJ/5tVkk12knErNBA7T0k02tLCJKPsQYGqxg1M+8GY2Ijkk8W0PZqtxEYjOasp0DuXxfM4MU9N5b06DUYx9gjLslNg2IgajmdZKXObVYPNd5KunZg6HiRnblouY8Z1LQaiKSbzCk6kESbPE5R5MdZ6LKBdQc1ayxFhj6rZriaEg7Yx8B6v+NBJ2EjPtl/ikVztd6iIc1aDKokHlRU4ZRQ08BTmZDjDzBYlPuBhpcA0sSB6X+GhuYVLZXOHSt5IaP9bu3HR+t45XnBXidwh6C1+f0jGoxswmYTWyhyVuzSKJNd4kJJKZEqcOT8I2l74rqWnmMEa3W1EMg7Vy0xLm2Zothi7FlTGOZh2WOOAthojkUYm/HpavJY8oyjUuYlxLzVWY86IibS+1uR5CFGOznrXqkAvcame/KnGPNysQySMSH/SQDLpcZNCp6eQwqsf1oLEKJ1wGMGe5xDEjkgVopjIHZ5/ymgU2ukhFD5VuDAyZBXIKlzALbhpn7JXYl0O4HGYhklslbvVgFpf3owq9H1WiHIrsIY4VZJgfAJy5wMEzPh8Rw9BM/5H4kVfD/NRFLiqilBvx1CQN4yKeMM18ZCKIFrpHYsybaYhkk8SNHkxzq4sI9AuLso1DqTH0QWINznodwNxqB+s+HBHr0ExHJbrUb9mtc6eLaDupuR2PStI67hLGXx8pWNvMu08i92YgIrElhjwY6H4XKSjHK/egj0WHd3TAuFVuQFeb7+C84yNiJJrpmMT3vRrJ5WWrQrufsjt5Mh9aykQk3QKw4HGJHpMckdwq0UuSc3nvqtB7V2UvFjo9Qx0WKI76AOrPdXDBVyNiIprpS4nHvJpon4tgz1HzNBYG8e3HRb7EDnQvgO9Zifd4Mw6R3C2xP7cgg4zzsosMB6jZj4VB2PV4QPvP/QBnXeig71QOzr1Zhmb6VuK/vFrmLy5SHaTm1WRhkBQuhuVo2q+P9OXP1Czf4MlvQzX/i2z3x6vmV+b4/m7SoK91Jd3eXeXFp+9qe1d8UZb47rOkCYqDUcNI/UIm5b4wYrGgLhRa4nwvExHyvItpLv2DPS4+h6U7UQS944x7n0OhM47++rvQ5hTRxA8XlRnf/TlKEEPEglOiFn2HPPD56f8tLF57RHz8hVqt3rv/2MfnHS3ZoE/aUXclqz7+z2XFHzz4Ydu/f37mW7945pfXB/8H1Llw5R8tAAA=";
}
