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
                    double result$var253 = result;
                    fabric.worker.transaction.TransactionManager $tm260 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled263 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff261 = 1;
                    boolean $doBackoff262 = true;
                    boolean $retry256 = true;
                    boolean $keepReads257 = false;
                    $label254: for (boolean $commit255 = false; !$commit255; ) {
                        if ($backoffEnabled263) {
                            if ($doBackoff262) {
                                if ($backoff261 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff261));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e258) {
                                            
                                        }
                                    }
                                }
                                if ($backoff261 < 5000) $backoff261 *= 2;
                            }
                            $doBackoff262 = $backoff261 <= 32 || !$doBackoff262;
                        }
                        $commit255 = true;
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
                            catch (final fabric.worker.RetryException $e258) {
                                throw $e258;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e258) {
                                throw $e258;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e258) {
                                throw $e258;
                            }
                            catch (final Throwable $e258) {
                                $tm260.getCurrentLog().checkRetrySignal();
                                throw $e258;
                            }
                        }
                        catch (final fabric.worker.RetryException $e258) {
                            $commit255 = false;
                            continue $label254;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e258) {
                            $commit255 = false;
                            $retry256 = false;
                            $keepReads257 = $e258.keepReads;
                            if ($tm260.checkForStaleObjects()) {
                                $retry256 = true;
                                $keepReads257 = false;
                                continue $label254;
                            }
                            fabric.common.TransactionID $currentTid259 =
                              $tm260.getCurrentTid();
                            if ($e258.tid == null ||
                                  !$e258.tid.isDescendantOf($currentTid259)) {
                                throw $e258;
                            }
                            throw new fabric.worker.UserAbortException($e258);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e258) {
                            $commit255 = false;
                            fabric.common.TransactionID $currentTid259 =
                              $tm260.getCurrentTid();
                            if ($e258.tid.isDescendantOf($currentTid259))
                                continue $label254;
                            if ($currentTid259.parent != null) {
                                $retry256 = false;
                                throw $e258;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e258) {
                            $commit255 = false;
                            if ($tm260.checkForStaleObjects())
                                continue $label254;
                            $retry256 = false;
                            throw new fabric.worker.AbortException($e258);
                        }
                        finally {
                            if ($commit255) {
                                fabric.common.TransactionID $currentTid259 =
                                  $tm260.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e258) {
                                    $commit255 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e258) {
                                    $commit255 = false;
                                    $retry256 = false;
                                    $keepReads257 = $e258.keepReads;
                                    if ($tm260.checkForStaleObjects()) {
                                        $retry256 = true;
                                        $keepReads257 = false;
                                        continue $label254;
                                    }
                                    if ($e258.tid ==
                                          null ||
                                          !$e258.tid.isDescendantOf(
                                                       $currentTid259))
                                        throw $e258;
                                    throw new fabric.worker.UserAbortException(
                                            $e258);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e258) {
                                    $commit255 = false;
                                    $currentTid259 = $tm260.getCurrentTid();
                                    if ($currentTid259 != null) {
                                        if ($e258.tid.equals($currentTid259) ||
                                              !$e258.tid.isDescendantOf(
                                                           $currentTid259)) {
                                            throw $e258;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads257) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit255) {
                                { result = result$var253; }
                                if ($retry256) { continue $label254; }
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
                    long rtn$var264 = rtn;
                    fabric.worker.transaction.TransactionManager $tm271 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled274 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff272 = 1;
                    boolean $doBackoff273 = true;
                    boolean $retry267 = true;
                    boolean $keepReads268 = false;
                    $label265: for (boolean $commit266 = false; !$commit266; ) {
                        if ($backoffEnabled274) {
                            if ($doBackoff273) {
                                if ($backoff272 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff272));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e269) {
                                            
                                        }
                                    }
                                }
                                if ($backoff272 < 5000) $backoff272 *= 2;
                            }
                            $doBackoff273 = $backoff272 <= 32 || !$doBackoff273;
                        }
                        $commit266 = true;
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
                            catch (final fabric.worker.RetryException $e269) {
                                throw $e269;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e269) {
                                throw $e269;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e269) {
                                throw $e269;
                            }
                            catch (final Throwable $e269) {
                                $tm271.getCurrentLog().checkRetrySignal();
                                throw $e269;
                            }
                        }
                        catch (final fabric.worker.RetryException $e269) {
                            $commit266 = false;
                            continue $label265;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e269) {
                            $commit266 = false;
                            $retry267 = false;
                            $keepReads268 = $e269.keepReads;
                            if ($tm271.checkForStaleObjects()) {
                                $retry267 = true;
                                $keepReads268 = false;
                                continue $label265;
                            }
                            fabric.common.TransactionID $currentTid270 =
                              $tm271.getCurrentTid();
                            if ($e269.tid == null ||
                                  !$e269.tid.isDescendantOf($currentTid270)) {
                                throw $e269;
                            }
                            throw new fabric.worker.UserAbortException($e269);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e269) {
                            $commit266 = false;
                            fabric.common.TransactionID $currentTid270 =
                              $tm271.getCurrentTid();
                            if ($e269.tid.isDescendantOf($currentTid270))
                                continue $label265;
                            if ($currentTid270.parent != null) {
                                $retry267 = false;
                                throw $e269;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e269) {
                            $commit266 = false;
                            if ($tm271.checkForStaleObjects())
                                continue $label265;
                            $retry267 = false;
                            throw new fabric.worker.AbortException($e269);
                        }
                        finally {
                            if ($commit266) {
                                fabric.common.TransactionID $currentTid270 =
                                  $tm271.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e269) {
                                    $commit266 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e269) {
                                    $commit266 = false;
                                    $retry267 = false;
                                    $keepReads268 = $e269.keepReads;
                                    if ($tm271.checkForStaleObjects()) {
                                        $retry267 = true;
                                        $keepReads268 = false;
                                        continue $label265;
                                    }
                                    if ($e269.tid ==
                                          null ||
                                          !$e269.tid.isDescendantOf(
                                                       $currentTid270))
                                        throw $e269;
                                    throw new fabric.worker.UserAbortException(
                                            $e269);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e269) {
                                    $commit266 = false;
                                    $currentTid270 = $tm271.getCurrentTid();
                                    if ($currentTid270 != null) {
                                        if ($e269.tid.equals($currentTid270) ||
                                              !$e269.tid.isDescendantOf(
                                                           $currentTid270)) {
                                            throw $e269;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads268) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit266) {
                                { rtn = rtn$var264; }
                                if ($retry267) { continue $label265; }
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
                    long rtn$var275 = rtn;
                    fabric.worker.transaction.TransactionManager $tm282 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled285 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff283 = 1;
                    boolean $doBackoff284 = true;
                    boolean $retry278 = true;
                    boolean $keepReads279 = false;
                    $label276: for (boolean $commit277 = false; !$commit277; ) {
                        if ($backoffEnabled285) {
                            if ($doBackoff284) {
                                if ($backoff283 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff283));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e280) {
                                            
                                        }
                                    }
                                }
                                if ($backoff283 < 5000) $backoff283 *= 2;
                            }
                            $doBackoff284 = $backoff283 <= 32 || !$doBackoff284;
                        }
                        $commit277 = true;
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
                            catch (final fabric.worker.RetryException $e280) {
                                throw $e280;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e280) {
                                throw $e280;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e280) {
                                throw $e280;
                            }
                            catch (final Throwable $e280) {
                                $tm282.getCurrentLog().checkRetrySignal();
                                throw $e280;
                            }
                        }
                        catch (final fabric.worker.RetryException $e280) {
                            $commit277 = false;
                            continue $label276;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e280) {
                            $commit277 = false;
                            $retry278 = false;
                            $keepReads279 = $e280.keepReads;
                            if ($tm282.checkForStaleObjects()) {
                                $retry278 = true;
                                $keepReads279 = false;
                                continue $label276;
                            }
                            fabric.common.TransactionID $currentTid281 =
                              $tm282.getCurrentTid();
                            if ($e280.tid == null ||
                                  !$e280.tid.isDescendantOf($currentTid281)) {
                                throw $e280;
                            }
                            throw new fabric.worker.UserAbortException($e280);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e280) {
                            $commit277 = false;
                            fabric.common.TransactionID $currentTid281 =
                              $tm282.getCurrentTid();
                            if ($e280.tid.isDescendantOf($currentTid281))
                                continue $label276;
                            if ($currentTid281.parent != null) {
                                $retry278 = false;
                                throw $e280;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e280) {
                            $commit277 = false;
                            if ($tm282.checkForStaleObjects())
                                continue $label276;
                            $retry278 = false;
                            throw new fabric.worker.AbortException($e280);
                        }
                        finally {
                            if ($commit277) {
                                fabric.common.TransactionID $currentTid281 =
                                  $tm282.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e280) {
                                    $commit277 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e280) {
                                    $commit277 = false;
                                    $retry278 = false;
                                    $keepReads279 = $e280.keepReads;
                                    if ($tm282.checkForStaleObjects()) {
                                        $retry278 = true;
                                        $keepReads279 = false;
                                        continue $label276;
                                    }
                                    if ($e280.tid ==
                                          null ||
                                          !$e280.tid.isDescendantOf(
                                                       $currentTid281))
                                        throw $e280;
                                    throw new fabric.worker.UserAbortException(
                                            $e280);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e280) {
                                    $commit277 = false;
                                    $currentTid281 = $tm282.getCurrentTid();
                                    if ($currentTid281 != null) {
                                        if ($e280.tid.equals($currentTid281) ||
                                              !$e280.tid.isDescendantOf(
                                                           $currentTid281)) {
                                            throw $e280;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads279) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit277) {
                                { rtn = rtn$var275; }
                                if ($retry278) { continue $label276; }
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
                    double rtn$var286 = rtn;
                    fabric.worker.transaction.TransactionManager $tm293 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled296 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff294 = 1;
                    boolean $doBackoff295 = true;
                    boolean $retry289 = true;
                    boolean $keepReads290 = false;
                    $label287: for (boolean $commit288 = false; !$commit288; ) {
                        if ($backoffEnabled296) {
                            if ($doBackoff295) {
                                if ($backoff294 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff294));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e291) {
                                            
                                        }
                                    }
                                }
                                if ($backoff294 < 5000) $backoff294 *= 2;
                            }
                            $doBackoff295 = $backoff294 <= 32 || !$doBackoff295;
                        }
                        $commit288 = true;
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
                            catch (final fabric.worker.RetryException $e291) {
                                throw $e291;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e291) {
                                throw $e291;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e291) {
                                throw $e291;
                            }
                            catch (final Throwable $e291) {
                                $tm293.getCurrentLog().checkRetrySignal();
                                throw $e291;
                            }
                        }
                        catch (final fabric.worker.RetryException $e291) {
                            $commit288 = false;
                            continue $label287;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e291) {
                            $commit288 = false;
                            $retry289 = false;
                            $keepReads290 = $e291.keepReads;
                            if ($tm293.checkForStaleObjects()) {
                                $retry289 = true;
                                $keepReads290 = false;
                                continue $label287;
                            }
                            fabric.common.TransactionID $currentTid292 =
                              $tm293.getCurrentTid();
                            if ($e291.tid == null ||
                                  !$e291.tid.isDescendantOf($currentTid292)) {
                                throw $e291;
                            }
                            throw new fabric.worker.UserAbortException($e291);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e291) {
                            $commit288 = false;
                            fabric.common.TransactionID $currentTid292 =
                              $tm293.getCurrentTid();
                            if ($e291.tid.isDescendantOf($currentTid292))
                                continue $label287;
                            if ($currentTid292.parent != null) {
                                $retry289 = false;
                                throw $e291;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e291) {
                            $commit288 = false;
                            if ($tm293.checkForStaleObjects())
                                continue $label287;
                            $retry289 = false;
                            throw new fabric.worker.AbortException($e291);
                        }
                        finally {
                            if ($commit288) {
                                fabric.common.TransactionID $currentTid292 =
                                  $tm293.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e291) {
                                    $commit288 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e291) {
                                    $commit288 = false;
                                    $retry289 = false;
                                    $keepReads290 = $e291.keepReads;
                                    if ($tm293.checkForStaleObjects()) {
                                        $retry289 = true;
                                        $keepReads290 = false;
                                        continue $label287;
                                    }
                                    if ($e291.tid ==
                                          null ||
                                          !$e291.tid.isDescendantOf(
                                                       $currentTid292))
                                        throw $e291;
                                    throw new fabric.worker.UserAbortException(
                                            $e291);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e291) {
                                    $commit288 = false;
                                    $currentTid292 = $tm293.getCurrentTid();
                                    if ($currentTid292 != null) {
                                        if ($e291.tid.equals($currentTid292) ||
                                              !$e291.tid.isDescendantOf(
                                                           $currentTid292)) {
                                            throw $e291;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads290) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit288) {
                                { rtn = rtn$var286; }
                                if ($retry289) { continue $label287; }
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
                    double rtn$var297 = rtn;
                    fabric.worker.transaction.TransactionManager $tm304 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled307 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff305 = 1;
                    boolean $doBackoff306 = true;
                    boolean $retry300 = true;
                    boolean $keepReads301 = false;
                    $label298: for (boolean $commit299 = false; !$commit299; ) {
                        if ($backoffEnabled307) {
                            if ($doBackoff306) {
                                if ($backoff305 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff305));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e302) {
                                            
                                        }
                                    }
                                }
                                if ($backoff305 < 5000) $backoff305 *= 2;
                            }
                            $doBackoff306 = $backoff305 <= 32 || !$doBackoff306;
                        }
                        $commit299 = true;
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
                            catch (final fabric.worker.RetryException $e302) {
                                throw $e302;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e302) {
                                throw $e302;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e302) {
                                throw $e302;
                            }
                            catch (final Throwable $e302) {
                                $tm304.getCurrentLog().checkRetrySignal();
                                throw $e302;
                            }
                        }
                        catch (final fabric.worker.RetryException $e302) {
                            $commit299 = false;
                            continue $label298;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e302) {
                            $commit299 = false;
                            $retry300 = false;
                            $keepReads301 = $e302.keepReads;
                            if ($tm304.checkForStaleObjects()) {
                                $retry300 = true;
                                $keepReads301 = false;
                                continue $label298;
                            }
                            fabric.common.TransactionID $currentTid303 =
                              $tm304.getCurrentTid();
                            if ($e302.tid == null ||
                                  !$e302.tid.isDescendantOf($currentTid303)) {
                                throw $e302;
                            }
                            throw new fabric.worker.UserAbortException($e302);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e302) {
                            $commit299 = false;
                            fabric.common.TransactionID $currentTid303 =
                              $tm304.getCurrentTid();
                            if ($e302.tid.isDescendantOf($currentTid303))
                                continue $label298;
                            if ($currentTid303.parent != null) {
                                $retry300 = false;
                                throw $e302;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e302) {
                            $commit299 = false;
                            if ($tm304.checkForStaleObjects())
                                continue $label298;
                            $retry300 = false;
                            throw new fabric.worker.AbortException($e302);
                        }
                        finally {
                            if ($commit299) {
                                fabric.common.TransactionID $currentTid303 =
                                  $tm304.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e302) {
                                    $commit299 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e302) {
                                    $commit299 = false;
                                    $retry300 = false;
                                    $keepReads301 = $e302.keepReads;
                                    if ($tm304.checkForStaleObjects()) {
                                        $retry300 = true;
                                        $keepReads301 = false;
                                        continue $label298;
                                    }
                                    if ($e302.tid ==
                                          null ||
                                          !$e302.tid.isDescendantOf(
                                                       $currentTid303))
                                        throw $e302;
                                    throw new fabric.worker.UserAbortException(
                                            $e302);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e302) {
                                    $commit299 = false;
                                    $currentTid303 = $tm304.getCurrentTid();
                                    if ($currentTid303 != null) {
                                        if ($e302.tid.equals($currentTid303) ||
                                              !$e302.tid.isDescendantOf(
                                                           $currentTid303)) {
                                            throw $e302;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads301) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit299) {
                                { rtn = rtn$var297; }
                                if ($retry300) { continue $label298; }
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
                    double rtn$var308 = rtn;
                    fabric.worker.transaction.TransactionManager $tm315 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled318 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff316 = 1;
                    boolean $doBackoff317 = true;
                    boolean $retry311 = true;
                    boolean $keepReads312 = false;
                    $label309: for (boolean $commit310 = false; !$commit310; ) {
                        if ($backoffEnabled318) {
                            if ($doBackoff317) {
                                if ($backoff316 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff316));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e313) {
                                            
                                        }
                                    }
                                }
                                if ($backoff316 < 5000) $backoff316 *= 2;
                            }
                            $doBackoff317 = $backoff316 <= 32 || !$doBackoff317;
                        }
                        $commit310 = true;
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
                            catch (final fabric.worker.RetryException $e313) {
                                throw $e313;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e313) {
                                throw $e313;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e313) {
                                throw $e313;
                            }
                            catch (final Throwable $e313) {
                                $tm315.getCurrentLog().checkRetrySignal();
                                throw $e313;
                            }
                        }
                        catch (final fabric.worker.RetryException $e313) {
                            $commit310 = false;
                            continue $label309;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e313) {
                            $commit310 = false;
                            $retry311 = false;
                            $keepReads312 = $e313.keepReads;
                            if ($tm315.checkForStaleObjects()) {
                                $retry311 = true;
                                $keepReads312 = false;
                                continue $label309;
                            }
                            fabric.common.TransactionID $currentTid314 =
                              $tm315.getCurrentTid();
                            if ($e313.tid == null ||
                                  !$e313.tid.isDescendantOf($currentTid314)) {
                                throw $e313;
                            }
                            throw new fabric.worker.UserAbortException($e313);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e313) {
                            $commit310 = false;
                            fabric.common.TransactionID $currentTid314 =
                              $tm315.getCurrentTid();
                            if ($e313.tid.isDescendantOf($currentTid314))
                                continue $label309;
                            if ($currentTid314.parent != null) {
                                $retry311 = false;
                                throw $e313;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e313) {
                            $commit310 = false;
                            if ($tm315.checkForStaleObjects())
                                continue $label309;
                            $retry311 = false;
                            throw new fabric.worker.AbortException($e313);
                        }
                        finally {
                            if ($commit310) {
                                fabric.common.TransactionID $currentTid314 =
                                  $tm315.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e313) {
                                    $commit310 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e313) {
                                    $commit310 = false;
                                    $retry311 = false;
                                    $keepReads312 = $e313.keepReads;
                                    if ($tm315.checkForStaleObjects()) {
                                        $retry311 = true;
                                        $keepReads312 = false;
                                        continue $label309;
                                    }
                                    if ($e313.tid ==
                                          null ||
                                          !$e313.tid.isDescendantOf(
                                                       $currentTid314))
                                        throw $e313;
                                    throw new fabric.worker.UserAbortException(
                                            $e313);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e313) {
                                    $commit310 = false;
                                    $currentTid314 = $tm315.getCurrentTid();
                                    if ($currentTid314 != null) {
                                        if ($e313.tid.equals($currentTid314) ||
                                              !$e313.tid.isDescendantOf(
                                                           $currentTid314)) {
                                            throw $e313;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads312) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit310) {
                                { rtn = rtn$var308; }
                                if ($retry311) { continue $label309; }
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
                    double result$var319 = result;
                    fabric.worker.transaction.TransactionManager $tm326 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled329 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff327 = 1;
                    boolean $doBackoff328 = true;
                    boolean $retry322 = true;
                    boolean $keepReads323 = false;
                    $label320: for (boolean $commit321 = false; !$commit321; ) {
                        if ($backoffEnabled329) {
                            if ($doBackoff328) {
                                if ($backoff327 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff327));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e324) {
                                            
                                        }
                                    }
                                }
                                if ($backoff327 < 5000) $backoff327 *= 2;
                            }
                            $doBackoff328 = $backoff327 <= 32 || !$doBackoff328;
                        }
                        $commit321 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { result = tmp.computeValue(weakStats); }
                            catch (final fabric.worker.RetryException $e324) {
                                throw $e324;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e324) {
                                throw $e324;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e324) {
                                throw $e324;
                            }
                            catch (final Throwable $e324) {
                                $tm326.getCurrentLog().checkRetrySignal();
                                throw $e324;
                            }
                        }
                        catch (final fabric.worker.RetryException $e324) {
                            $commit321 = false;
                            continue $label320;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e324) {
                            $commit321 = false;
                            $retry322 = false;
                            $keepReads323 = $e324.keepReads;
                            if ($tm326.checkForStaleObjects()) {
                                $retry322 = true;
                                $keepReads323 = false;
                                continue $label320;
                            }
                            fabric.common.TransactionID $currentTid325 =
                              $tm326.getCurrentTid();
                            if ($e324.tid == null ||
                                  !$e324.tid.isDescendantOf($currentTid325)) {
                                throw $e324;
                            }
                            throw new fabric.worker.UserAbortException($e324);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e324) {
                            $commit321 = false;
                            fabric.common.TransactionID $currentTid325 =
                              $tm326.getCurrentTid();
                            if ($e324.tid.isDescendantOf($currentTid325))
                                continue $label320;
                            if ($currentTid325.parent != null) {
                                $retry322 = false;
                                throw $e324;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e324) {
                            $commit321 = false;
                            if ($tm326.checkForStaleObjects())
                                continue $label320;
                            $retry322 = false;
                            throw new fabric.worker.AbortException($e324);
                        }
                        finally {
                            if ($commit321) {
                                fabric.common.TransactionID $currentTid325 =
                                  $tm326.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e324) {
                                    $commit321 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e324) {
                                    $commit321 = false;
                                    $retry322 = false;
                                    $keepReads323 = $e324.keepReads;
                                    if ($tm326.checkForStaleObjects()) {
                                        $retry322 = true;
                                        $keepReads323 = false;
                                        continue $label320;
                                    }
                                    if ($e324.tid ==
                                          null ||
                                          !$e324.tid.isDescendantOf(
                                                       $currentTid325))
                                        throw $e324;
                                    throw new fabric.worker.UserAbortException(
                                            $e324);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e324) {
                                    $commit321 = false;
                                    $currentTid325 = $tm326.getCurrentTid();
                                    if ($currentTid325 != null) {
                                        if ($e324.tid.equals($currentTid325) ||
                                              !$e324.tid.isDescendantOf(
                                                           $currentTid325)) {
                                            throw $e324;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads323) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit321) {
                                { result = result$var319; }
                                if ($retry322) { continue $label320; }
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
                    long result$var330 = result;
                    fabric.worker.transaction.TransactionManager $tm337 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled340 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff338 = 1;
                    boolean $doBackoff339 = true;
                    boolean $retry333 = true;
                    boolean $keepReads334 = false;
                    $label331: for (boolean $commit332 = false; !$commit332; ) {
                        if ($backoffEnabled340) {
                            if ($doBackoff339) {
                                if ($backoff338 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff338));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e335) {
                                            
                                        }
                                    }
                                }
                                if ($backoff338 < 5000) $backoff338 *= 2;
                            }
                            $doBackoff339 = $backoff338 <= 32 || !$doBackoff339;
                        }
                        $commit332 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { result = tmp.computeSamples(weakStats); }
                            catch (final fabric.worker.RetryException $e335) {
                                throw $e335;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e335) {
                                throw $e335;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e335) {
                                throw $e335;
                            }
                            catch (final Throwable $e335) {
                                $tm337.getCurrentLog().checkRetrySignal();
                                throw $e335;
                            }
                        }
                        catch (final fabric.worker.RetryException $e335) {
                            $commit332 = false;
                            continue $label331;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e335) {
                            $commit332 = false;
                            $retry333 = false;
                            $keepReads334 = $e335.keepReads;
                            if ($tm337.checkForStaleObjects()) {
                                $retry333 = true;
                                $keepReads334 = false;
                                continue $label331;
                            }
                            fabric.common.TransactionID $currentTid336 =
                              $tm337.getCurrentTid();
                            if ($e335.tid == null ||
                                  !$e335.tid.isDescendantOf($currentTid336)) {
                                throw $e335;
                            }
                            throw new fabric.worker.UserAbortException($e335);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e335) {
                            $commit332 = false;
                            fabric.common.TransactionID $currentTid336 =
                              $tm337.getCurrentTid();
                            if ($e335.tid.isDescendantOf($currentTid336))
                                continue $label331;
                            if ($currentTid336.parent != null) {
                                $retry333 = false;
                                throw $e335;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e335) {
                            $commit332 = false;
                            if ($tm337.checkForStaleObjects())
                                continue $label331;
                            $retry333 = false;
                            throw new fabric.worker.AbortException($e335);
                        }
                        finally {
                            if ($commit332) {
                                fabric.common.TransactionID $currentTid336 =
                                  $tm337.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e335) {
                                    $commit332 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e335) {
                                    $commit332 = false;
                                    $retry333 = false;
                                    $keepReads334 = $e335.keepReads;
                                    if ($tm337.checkForStaleObjects()) {
                                        $retry333 = true;
                                        $keepReads334 = false;
                                        continue $label331;
                                    }
                                    if ($e335.tid ==
                                          null ||
                                          !$e335.tid.isDescendantOf(
                                                       $currentTid336))
                                        throw $e335;
                                    throw new fabric.worker.UserAbortException(
                                            $e335);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e335) {
                                    $commit332 = false;
                                    $currentTid336 = $tm337.getCurrentTid();
                                    if ($currentTid336 != null) {
                                        if ($e335.tid.equals($currentTid336) ||
                                              !$e335.tid.isDescendantOf(
                                                           $currentTid336)) {
                                            throw $e335;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads334) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit332) {
                                { result = result$var330; }
                                if ($retry333) { continue $label331; }
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
                    long result$var341 = result;
                    fabric.worker.transaction.TransactionManager $tm348 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled351 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff349 = 1;
                    boolean $doBackoff350 = true;
                    boolean $retry344 = true;
                    boolean $keepReads345 = false;
                    $label342: for (boolean $commit343 = false; !$commit343; ) {
                        if ($backoffEnabled351) {
                            if ($doBackoff350) {
                                if ($backoff349 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff349));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e346) {
                                            
                                        }
                                    }
                                }
                                if ($backoff349 < 5000) $backoff349 *= 2;
                            }
                            $doBackoff350 = $backoff349 <= 32 || !$doBackoff350;
                        }
                        $commit343 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { result = tmp.computeLastUpdate(weakStats); }
                            catch (final fabric.worker.RetryException $e346) {
                                throw $e346;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e346) {
                                throw $e346;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e346) {
                                throw $e346;
                            }
                            catch (final Throwable $e346) {
                                $tm348.getCurrentLog().checkRetrySignal();
                                throw $e346;
                            }
                        }
                        catch (final fabric.worker.RetryException $e346) {
                            $commit343 = false;
                            continue $label342;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e346) {
                            $commit343 = false;
                            $retry344 = false;
                            $keepReads345 = $e346.keepReads;
                            if ($tm348.checkForStaleObjects()) {
                                $retry344 = true;
                                $keepReads345 = false;
                                continue $label342;
                            }
                            fabric.common.TransactionID $currentTid347 =
                              $tm348.getCurrentTid();
                            if ($e346.tid == null ||
                                  !$e346.tid.isDescendantOf($currentTid347)) {
                                throw $e346;
                            }
                            throw new fabric.worker.UserAbortException($e346);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e346) {
                            $commit343 = false;
                            fabric.common.TransactionID $currentTid347 =
                              $tm348.getCurrentTid();
                            if ($e346.tid.isDescendantOf($currentTid347))
                                continue $label342;
                            if ($currentTid347.parent != null) {
                                $retry344 = false;
                                throw $e346;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e346) {
                            $commit343 = false;
                            if ($tm348.checkForStaleObjects())
                                continue $label342;
                            $retry344 = false;
                            throw new fabric.worker.AbortException($e346);
                        }
                        finally {
                            if ($commit343) {
                                fabric.common.TransactionID $currentTid347 =
                                  $tm348.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e346) {
                                    $commit343 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e346) {
                                    $commit343 = false;
                                    $retry344 = false;
                                    $keepReads345 = $e346.keepReads;
                                    if ($tm348.checkForStaleObjects()) {
                                        $retry344 = true;
                                        $keepReads345 = false;
                                        continue $label342;
                                    }
                                    if ($e346.tid ==
                                          null ||
                                          !$e346.tid.isDescendantOf(
                                                       $currentTid347))
                                        throw $e346;
                                    throw new fabric.worker.UserAbortException(
                                            $e346);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e346) {
                                    $commit343 = false;
                                    $currentTid347 = $tm348.getCurrentTid();
                                    if ($currentTid347 != null) {
                                        if ($e346.tid.equals($currentTid347) ||
                                              !$e346.tid.isDescendantOf(
                                                           $currentTid347)) {
                                            throw $e346;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads345) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit343) {
                                { result = result$var341; }
                                if ($retry344) { continue $label342; }
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
                    double result$var352 = result;
                    fabric.worker.transaction.TransactionManager $tm359 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled362 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff360 = 1;
                    boolean $doBackoff361 = true;
                    boolean $retry355 = true;
                    boolean $keepReads356 = false;
                    $label353: for (boolean $commit354 = false; !$commit354; ) {
                        if ($backoffEnabled362) {
                            if ($doBackoff361) {
                                if ($backoff360 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff360));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e357) {
                                            
                                        }
                                    }
                                }
                                if ($backoff360 < 5000) $backoff360 *= 2;
                            }
                            $doBackoff361 = $backoff360 <= 32 || !$doBackoff361;
                        }
                        $commit354 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                result = tmp.computeUpdateInterval(weakStats);
                            }
                            catch (final fabric.worker.RetryException $e357) {
                                throw $e357;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e357) {
                                throw $e357;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e357) {
                                throw $e357;
                            }
                            catch (final Throwable $e357) {
                                $tm359.getCurrentLog().checkRetrySignal();
                                throw $e357;
                            }
                        }
                        catch (final fabric.worker.RetryException $e357) {
                            $commit354 = false;
                            continue $label353;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e357) {
                            $commit354 = false;
                            $retry355 = false;
                            $keepReads356 = $e357.keepReads;
                            if ($tm359.checkForStaleObjects()) {
                                $retry355 = true;
                                $keepReads356 = false;
                                continue $label353;
                            }
                            fabric.common.TransactionID $currentTid358 =
                              $tm359.getCurrentTid();
                            if ($e357.tid == null ||
                                  !$e357.tid.isDescendantOf($currentTid358)) {
                                throw $e357;
                            }
                            throw new fabric.worker.UserAbortException($e357);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e357) {
                            $commit354 = false;
                            fabric.common.TransactionID $currentTid358 =
                              $tm359.getCurrentTid();
                            if ($e357.tid.isDescendantOf($currentTid358))
                                continue $label353;
                            if ($currentTid358.parent != null) {
                                $retry355 = false;
                                throw $e357;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e357) {
                            $commit354 = false;
                            if ($tm359.checkForStaleObjects())
                                continue $label353;
                            $retry355 = false;
                            throw new fabric.worker.AbortException($e357);
                        }
                        finally {
                            if ($commit354) {
                                fabric.common.TransactionID $currentTid358 =
                                  $tm359.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e357) {
                                    $commit354 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e357) {
                                    $commit354 = false;
                                    $retry355 = false;
                                    $keepReads356 = $e357.keepReads;
                                    if ($tm359.checkForStaleObjects()) {
                                        $retry355 = true;
                                        $keepReads356 = false;
                                        continue $label353;
                                    }
                                    if ($e357.tid ==
                                          null ||
                                          !$e357.tid.isDescendantOf(
                                                       $currentTid358))
                                        throw $e357;
                                    throw new fabric.worker.UserAbortException(
                                            $e357);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e357) {
                                    $commit354 = false;
                                    $currentTid358 = $tm359.getCurrentTid();
                                    if ($currentTid358 != null) {
                                        if ($e357.tid.equals($currentTid358) ||
                                              !$e357.tid.isDescendantOf(
                                                           $currentTid358)) {
                                            throw $e357;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads356) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit354) {
                                { result = result$var352; }
                                if ($retry355) { continue $label353; }
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
                    double result$var363 = result;
                    fabric.worker.transaction.TransactionManager $tm370 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled373 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff371 = 1;
                    boolean $doBackoff372 = true;
                    boolean $retry366 = true;
                    boolean $keepReads367 = false;
                    $label364: for (boolean $commit365 = false; !$commit365; ) {
                        if ($backoffEnabled373) {
                            if ($doBackoff372) {
                                if ($backoff371 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff371));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e368) {
                                            
                                        }
                                    }
                                }
                                if ($backoff371 < 5000) $backoff371 *= 2;
                            }
                            $doBackoff372 = $backoff371 <= 32 || !$doBackoff372;
                        }
                        $commit365 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { result = tmp.computeVelocity(weakStats); }
                            catch (final fabric.worker.RetryException $e368) {
                                throw $e368;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e368) {
                                throw $e368;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e368) {
                                throw $e368;
                            }
                            catch (final Throwable $e368) {
                                $tm370.getCurrentLog().checkRetrySignal();
                                throw $e368;
                            }
                        }
                        catch (final fabric.worker.RetryException $e368) {
                            $commit365 = false;
                            continue $label364;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e368) {
                            $commit365 = false;
                            $retry366 = false;
                            $keepReads367 = $e368.keepReads;
                            if ($tm370.checkForStaleObjects()) {
                                $retry366 = true;
                                $keepReads367 = false;
                                continue $label364;
                            }
                            fabric.common.TransactionID $currentTid369 =
                              $tm370.getCurrentTid();
                            if ($e368.tid == null ||
                                  !$e368.tid.isDescendantOf($currentTid369)) {
                                throw $e368;
                            }
                            throw new fabric.worker.UserAbortException($e368);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e368) {
                            $commit365 = false;
                            fabric.common.TransactionID $currentTid369 =
                              $tm370.getCurrentTid();
                            if ($e368.tid.isDescendantOf($currentTid369))
                                continue $label364;
                            if ($currentTid369.parent != null) {
                                $retry366 = false;
                                throw $e368;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e368) {
                            $commit365 = false;
                            if ($tm370.checkForStaleObjects())
                                continue $label364;
                            $retry366 = false;
                            throw new fabric.worker.AbortException($e368);
                        }
                        finally {
                            if ($commit365) {
                                fabric.common.TransactionID $currentTid369 =
                                  $tm370.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e368) {
                                    $commit365 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e368) {
                                    $commit365 = false;
                                    $retry366 = false;
                                    $keepReads367 = $e368.keepReads;
                                    if ($tm370.checkForStaleObjects()) {
                                        $retry366 = true;
                                        $keepReads367 = false;
                                        continue $label364;
                                    }
                                    if ($e368.tid ==
                                          null ||
                                          !$e368.tid.isDescendantOf(
                                                       $currentTid369))
                                        throw $e368;
                                    throw new fabric.worker.UserAbortException(
                                            $e368);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e368) {
                                    $commit365 = false;
                                    $currentTid369 = $tm370.getCurrentTid();
                                    if ($currentTid369 != null) {
                                        if ($e368.tid.equals($currentTid369) ||
                                              !$e368.tid.isDescendantOf(
                                                           $currentTid369)) {
                                            throw $e368;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads367) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit365) {
                                { result = result$var363; }
                                if ($retry366) { continue $label364; }
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
                    double result$var374 = result;
                    fabric.worker.transaction.TransactionManager $tm381 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled384 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff382 = 1;
                    boolean $doBackoff383 = true;
                    boolean $retry377 = true;
                    boolean $keepReads378 = false;
                    $label375: for (boolean $commit376 = false; !$commit376; ) {
                        if ($backoffEnabled384) {
                            if ($doBackoff383) {
                                if ($backoff382 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff382));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e379) {
                                            
                                        }
                                    }
                                }
                                if ($backoff382 < 5000) $backoff382 *= 2;
                            }
                            $doBackoff383 = $backoff382 <= 32 || !$doBackoff383;
                        }
                        $commit376 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try { result = tmp.computeNoise(weakStats); }
                            catch (final fabric.worker.RetryException $e379) {
                                throw $e379;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e379) {
                                throw $e379;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e379) {
                                throw $e379;
                            }
                            catch (final Throwable $e379) {
                                $tm381.getCurrentLog().checkRetrySignal();
                                throw $e379;
                            }
                        }
                        catch (final fabric.worker.RetryException $e379) {
                            $commit376 = false;
                            continue $label375;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e379) {
                            $commit376 = false;
                            $retry377 = false;
                            $keepReads378 = $e379.keepReads;
                            if ($tm381.checkForStaleObjects()) {
                                $retry377 = true;
                                $keepReads378 = false;
                                continue $label375;
                            }
                            fabric.common.TransactionID $currentTid380 =
                              $tm381.getCurrentTid();
                            if ($e379.tid == null ||
                                  !$e379.tid.isDescendantOf($currentTid380)) {
                                throw $e379;
                            }
                            throw new fabric.worker.UserAbortException($e379);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e379) {
                            $commit376 = false;
                            fabric.common.TransactionID $currentTid380 =
                              $tm381.getCurrentTid();
                            if ($e379.tid.isDescendantOf($currentTid380))
                                continue $label375;
                            if ($currentTid380.parent != null) {
                                $retry377 = false;
                                throw $e379;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e379) {
                            $commit376 = false;
                            if ($tm381.checkForStaleObjects())
                                continue $label375;
                            $retry377 = false;
                            throw new fabric.worker.AbortException($e379);
                        }
                        finally {
                            if ($commit376) {
                                fabric.common.TransactionID $currentTid380 =
                                  $tm381.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e379) {
                                    $commit376 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e379) {
                                    $commit376 = false;
                                    $retry377 = false;
                                    $keepReads378 = $e379.keepReads;
                                    if ($tm381.checkForStaleObjects()) {
                                        $retry377 = true;
                                        $keepReads378 = false;
                                        continue $label375;
                                    }
                                    if ($e379.tid ==
                                          null ||
                                          !$e379.tid.isDescendantOf(
                                                       $currentTid380))
                                        throw $e379;
                                    throw new fabric.worker.UserAbortException(
                                            $e379);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e379) {
                                    $commit376 = false;
                                    $currentTid380 = $tm381.getCurrentTid();
                                    if ($currentTid380 != null) {
                                        if ($e379.tid.equals($currentTid380) ||
                                              !$e379.tid.isDescendantOf(
                                                           $currentTid380)) {
                                            throw $e379;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads378) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit376) {
                                { result = result$var374; }
                                if ($retry377) { continue $label375; }
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
    public static final long jlc$SourceLastModified$fabil = 1549063731000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVaC3AV1Rn+Nwl5EUgAgzxDCAENj1zxjVgtufIIBIwJwRqqcbP33LBm793L7rnhBsWxDwu1Y6wlovhgtOJUMcWq4zjVUqU+io9xKmqttgiDOkKRaa2jdkbF/v/Zc5+5d5OdSRn2fJs95z/nf5//7N2BkzDKtqAmqHbqRj3vjTC7fpna2djUrFo2C/gN1bbX4tMObXRB445jvwlU5UFeE5RpatgM65pqdIRtDmObrlN7VF+YcV9bS+Pi9VCiEeEK1d7AIW99Q8yC6ohp9HYZJpeLDJr/jnm+/juvqXgiH8rboVwPt3KV65rfDHMW4+1QFmKhTmbZSwIBFmiHcWHGAq3M0lVD34wDzXA7jLf1rrDKoxazW5htGj00cLwdjTBLrBl/SOybyLYV1bhpIfsVDvtRrhu+Jt3mi5ugMKgzI2BvhBuhoAlGBQ21CwdObIpL4RMz+pbRcxxeqiObVlDVWJykoFsPBzjMyKRISFy7CgcgaVGI8Q1mYqmCsIoPYLzDkqGGu3yt3NLDXTh0lBnFVThMyTkpDiqOqFq32sU6OEzKHNfsdOGoEqEWIuFQmTlMzIQ2m5JhsxRrnVxzcd/14RXhPFCQ5wDTDOK/GImqMohaWJBZLKwxh7BsbtMOdeK+bXkAOLgyY7Az5ukbPvv+/KrnDzhjpmYZc3nndUzjHdruzrFvTvPXLconNoojpq2TK6RJLqzaLHsWxyLo7RMTM1Jnfbzz+ZaXr7ppDzuRB6WNUKiZRjSEXjVOM0MR3WDWchZmlspZoBFKWDjgF/2NUIT3TXqYOU8vDwZtxhuhwBCPCk3xN6ooiFOQiorwXg8Hzfh9ROUbxH0sAgBFeIGC/wcAFm7B+yqA/Jc4NPs2mCHm6zSibBO6tw8vplraBh/GraVrCzQz0uuzLc1nRcNcx5HOcx+6EoLta1VDEYMFVos/65GXyP9hzhjJUbFJUVDFMzQzwDpVG+0lfaeh2cDwWGEaAWZ1aEbfvkaYsG+n8J8S8nkb/VZoSEGbT8vMFqm0/dGGpZ/t7XjN8T2ilQrkMM3hsV7yWJ/GI7JVRlFVj3mqHvPUgBKr9+9qfFQ4T6EtoiwxUxnOdFHEUHnQtEIxUBQh1mmCXngN2rwbcwmmi7K61qtXXrutJh/dNbKpgCyIQ2szgyeZchrxTsWI6NDKtx778rEdW8xkGHGoHRTdgykpOmsydWSZGgtg9ktOP7dafapj35baPMosJZj0uIpuiRmkKnONtChdHM94pI1RTTCadKAa1BVPU6V8g2VuSj4Rth9LzXjHDUhZGQyKZPm91sh9f3vj+DliG4nn1fKUBNzK+OKUWKbJykXUjkvqfq3FGI47dFfz9jtObl0vFI8jZmVbsJZaP8awisFrWjcf2Pje4Q92v52XNBaHwki009C1mJBl3Hf4T8HrFF0UkPSAENOyXyaD6kQ2iNDKc5K8YV4wMDch63ZtWzhkBvSgrnYajDzlm/LZC5/6tK/CMbeBTxzlWTB/6AmSzyc3wE2vXfNVlZhG0WhfSuovOcxJdhOSMy+xLLWX+Ij96OD0nX9W70PPx1Rl65uZyD4g9AHCgGcLXSwQ7cKMvnOpqXG0NU08L7QHJ/5ltIMmfbHdN3DvFP8lJ5yIT/gizTEzS8SvU1PC5Ow9oS/yagpfyoOidqgQm7ca5utUTF3oBu24/dp++bAJxqT1p2+lzr6xOBFr0zLjIGXZzChIZhq8p9F0X+o4vuM4qIgKUtJkvKoBCgoczP+aeidEqD0tpoC4uUiQzBLtHGrqhCLzOBRFLL0HPYtDiR4KRTnZXqwyj0N+N+vNoupmSw9htPTIPZZt67/lu/q+fsfNnEJk1qBaIJXGKUbEKmPEUjFcZabbKoJi2SePbXn24S1bnY16fPq2ujQcDf32r9++Xn/XkVeypOoCw3TSbYXQwfkJFZaDs+9BG8Do6yUaWVS4IocK6XYuNZfG1YYqxc2Nt4ihS6SABJdh7AdMDH7mysl65OCoxLeycNLslZMG+nO165oqQFmjxIuyrNnmdc11Q6/ZhWv9TuIDWdZs97rmmpxrltGaszH71gHUPCzxtixrdmRfE7N2ScQyOYYzC8QS0+bRtKPldH0Sb06ZluN5B08WtqCo5HCmrBg2mVY3sxKFQ0s0HMZywCkcWhMEkzMLAyFaLAeLQi1J3sS/QlnUvSjxjym8pSRUoACcnqv+FsG3+8f9uwKXP7QwT2blpagRbkYWGKyHGSlT1VIoDzrfrRanjmSKPXJi+iJ/98ddTijPyFg5c/QjqwdeWT5H+1Ue5Cdy6aCjTjrR4vQMWmoxPKmF16bl0eqErkpIB6vwmg8w6g8SN6e6R9KpZlHTne4CxZKkV6KdqebkzpaX9GG/cFcx9WaX/e8GaqIcpju+Uyudpjat2qxNMmilizUDryvQS2dLLPcmFpGMlVicW6xUhn/i0ieC40b06y7G22zWLOI2W6Is6jRNg6nhbCJNw+tK5OdhiXd4E4lI+iX2DU+k21z6bqfmFg6lKJIjT4swbC7Or8Z0VO7g6G+8cU4kX0v8Ynic3+XSdzc121M5b3DlXEPOeyRq3jgnkk6JPxwe5w+49D1Izb2pnK9z5VzHZQ9JfNUb50TyisQXhsf5Hpe+AWoeSuV8TU7OzwA6BcCYwxL3e+OcSJ6X+ExuzpXk9uEkoydd2H+Kmr3IPle7mZOBskVvQY+pB7KJVIsXVlljgxJXeROJSFZKvMyDSM+5iCS0+gyHYvIiKuLp76ezMX82XluxgNkocYU35olkucQluZlP2SFELeMXUx9wkUB49AtpRskmgyiCzsHrNjw5rJQ4y0WGzkGFjiCpkTh5SAPEy56q7GWPqHRWq5HsxY7g5k0Xsd+l5nVO71dDkShnCeNlBpMoh1rw2o5cvyOxP4fgWcvNSzi9sqFXxBnFX4WcbbvEm3PrJMWuFdQcFKsecRHwKDXvcZjgLN0xlJwJA+8GGP+pxJe8GZhIXpT47LAizJHjuIscJ6j5iMNYKUBK3vDnMhXu7RN6JV7oyVTUHMpiJprpAol1Xs30uYt4Yic+yaEy3UwuUgpDnY/X7wEq+yQGvBmKSDSJ7R4M9Y2LJKeo+Yo7b6FRhCbV5m2RgMpZTlvhcRn2AUyc6WDloRGxFc30D4lveLSVUphbQoVKWAV3xEnptnIXVJjrYrzeAJjUIHG8N3MRyTiJLnV0prmUchdhxlFTio4npXAkaKQfh3pUI2cmXI/XQWTjsMRc5bM3k9FM/RJ/5tVkk12knErNBA7T0k02tLCJKPsQYGqxg1M+8GY2Ijkk8W0PZqtxEYjOasp0DuXxfM4MU9N5b06DUYx9gjLslNg2IgajmdZKXObVYPNd5KunZg6HiRnblouY8Z1LQaiKSbzCk6kESbPE5R5MdZ6LKBdQc1ayxFhj6rZriaEg7Yx8B6v+NBJ2EjPtl/ikVztd6iIc1aDKokHlRU4ZRQ08BTmZDjDzBYlPuBhpcA0sSB6X+GhuYVLZXOHSt5IaP9bu3HR+t45XnBXidwh6C1+f0jGoxswmYTWyhyVuzSKJNd4kJJKZEqcOT8I2l74rqWnmMEa3W1EMg7Vy0xLm2Zothi7FlTGOZh2WOOAthojkUYm/HpavJY8oyjUuYlxLzVWY86IibS+1uR5CFGOznrXqkAvcame/KnGPNysQySMSH/SQDLpcZNCp6eQwqsf1oLEKJ1wGMGe5xDEjkgVopjIHZ5/ymgU2ukhFD5VuDAyZBXIKlzALbhpn7JXYl0O4HGYhklslbvVgFpf3owq9H1WiHIrsIY4VZJgfAJy5wMEzPh8Rw9BM/5H4kVfD/NRFLiqilBvx1CQN4yKeMM18ZCKIFrpHYsybaYhkk8SNHkxzq4sI9AuLso1DqTH0QWINznodwNxqB+s+HBHr0ExHJbrUb9mtc6eLaDupuR2PStI67hLGXx8pWNvMu08i92YgIrElhjwY6H4XKSjHK/egj0WHd3TAuFVuQFeb7+C84yNiJJrpmMT3vRrJ5WWrQrufsjt5Mh9aykQk3QKw4HGJHpMckdwq0UuSc3nvqtB7V2UvFjo9Qx0WKI76AOrPdXDBVyNiIprpS4nHvJpon4tgz1HzNBYG8e3HRb7EDnQvgO9Zifd4Mw6R3C2xP7cgg4zzsosMB6jZj4VB2PV4QPvP/QBnXeig71QOzr1Zhmb6VuK/vFrmLy5SHaTm1WRhkBQuhuVo2q+P9OXP1Czf4MlvQzX/i2z3x6vmV+b4/m7SoK91Jd3eXeXFp+9qe1d8UZb47rOkCYqDUcNI/UIm5b4wYrGgLhRa4nwvExHyvItpLv2DPS4+h6U7UQS944x7n0OhM47++rvQ5hTRxA8XlRnf/TlKEEPEglOiFn2HPPD56f8tLF57RHz8hVqt3rv/2MfnHS3ZoE/aUXclqz7+z2XFHzz4Ydu/f37mW7945pfXB/8H1Llw5R8tAAA=";
}
