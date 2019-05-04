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
    
    public double get$presetNTerm();
    
    public double set$presetNTerm(double val);
    
    public double postInc$presetNTerm();
    
    public double postDec$presetNTerm();
    
    public double getPresetNTerm();
    
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
    
    public double computeNoiseTerm(fabric.worker.metrics.StatsMap weakStats);
    
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
    
    public double noiseTerm(fabric.worker.metrics.StatsMap weakStats);
    
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
        
        public double get$presetNTerm() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              get$presetNTerm();
        }
        
        public double set$presetNTerm(double val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              set$presetNTerm(val);
        }
        
        public double postInc$presetNTerm() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              postInc$presetNTerm();
        }
        
        public double postDec$presetNTerm() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              postDec$presetNTerm();
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
                this.set$presetNTerm((double) 0);
            } else {
                this.set$presetR((double) 0.0);
                this.set$presetB((double) 0.0);
                this.set$presetV((double) 0.0);
                this.set$presetN((double) 0.0);
                this.set$presetNTerm((double) 0.0);
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
        
        public double get$presetNTerm() { return this.presetNTerm; }
        
        public double set$presetNTerm(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.presetNTerm = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$presetNTerm() {
            double tmp = this.get$presetNTerm();
            this.set$presetNTerm((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$presetNTerm() {
            double tmp = this.get$presetNTerm();
            this.set$presetNTerm((double) (tmp - 1));
            return tmp;
        }
        
        public double presetNTerm;
        
        public double getPresetNTerm() { return this.get$presetNTerm(); }
        
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
                    double result$var199 = result;
                    fabric.worker.transaction.TransactionManager $tm206 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled209 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff207 = 1;
                    boolean $doBackoff208 = true;
                    boolean $retry202 = true;
                    boolean $keepReads203 = false;
                    $label200: for (boolean $commit201 = false; !$commit201; ) {
                        if ($backoffEnabled209) {
                            if ($doBackoff208) {
                                if ($backoff207 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff207));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e204) {
                                            
                                        }
                                    }
                                }
                                if ($backoff207 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff207 =
                                      java.lang.Math.
                                        min(
                                          $backoff207 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff208 = $backoff207 <= 32 || !$doBackoff208;
                        }
                        $commit201 = true;
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
                        catch (final fabric.worker.RetryException $e204) {
                            $commit201 = false;
                            continue $label200;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e204) {
                            $commit201 = false;
                            $retry202 = false;
                            $keepReads203 = $e204.keepReads;
                            fabric.common.TransactionID $currentTid205 =
                              $tm206.getCurrentTid();
                            if ($e204.tid == null ||
                                  !$e204.tid.isDescendantOf($currentTid205)) {
                                throw $e204;
                            }
                            throw new fabric.worker.UserAbortException($e204);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e204) {
                            $commit201 = false;
                            fabric.common.TransactionID $currentTid205 =
                              $tm206.getCurrentTid();
                            if ($e204.tid.isDescendantOf($currentTid205))
                                continue $label200;
                            if ($currentTid205.parent != null) {
                                $retry202 = false;
                                throw $e204;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e204) {
                            $commit201 = false;
                            $retry202 = false;
                            if ($tm206.inNestedTxn()) { $keepReads203 = true; }
                            throw $e204;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid205 =
                              $tm206.getCurrentTid();
                            if ($commit201) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e204) {
                                    $commit201 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e204) {
                                    $commit201 = false;
                                    $retry202 = false;
                                    $keepReads203 = $e204.keepReads;
                                    if ($e204.tid ==
                                          null ||
                                          !$e204.tid.isDescendantOf(
                                                       $currentTid205))
                                        throw $e204;
                                    throw new fabric.worker.UserAbortException(
                                            $e204);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e204) {
                                    $commit201 = false;
                                    $currentTid205 = $tm206.getCurrentTid();
                                    if ($currentTid205 != null) {
                                        if ($e204.tid.equals($currentTid205) ||
                                              !$e204.tid.isDescendantOf(
                                                           $currentTid205)) {
                                            throw $e204;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm206.inNestedTxn() &&
                                      $tm206.checkForStaleObjects()) {
                                    $retry202 = true;
                                    $keepReads203 = false;
                                }
                                if ($keepReads203) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e204) {
                                        $currentTid205 = $tm206.getCurrentTid();
                                        if ($currentTid205 != null &&
                                              ($e204.tid.equals($currentTid205) || !$e204.tid.isDescendantOf($currentTid205))) {
                                            throw $e204;
                                        } else {
                                            $retry202 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit201) {
                                { result = result$var199; }
                                if ($retry202) { continue $label200; }
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
                    long rtn$var210 = rtn;
                    fabric.worker.transaction.TransactionManager $tm217 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled220 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff218 = 1;
                    boolean $doBackoff219 = true;
                    boolean $retry213 = true;
                    boolean $keepReads214 = false;
                    $label211: for (boolean $commit212 = false; !$commit212; ) {
                        if ($backoffEnabled220) {
                            if ($doBackoff219) {
                                if ($backoff218 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff218));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e215) {
                                            
                                        }
                                    }
                                }
                                if ($backoff218 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff218 =
                                      java.lang.Math.
                                        min(
                                          $backoff218 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff219 = $backoff218 <= 32 || !$doBackoff219;
                        }
                        $commit212 = true;
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
                        catch (final fabric.worker.RetryException $e215) {
                            $commit212 = false;
                            continue $label211;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e215) {
                            $commit212 = false;
                            $retry213 = false;
                            $keepReads214 = $e215.keepReads;
                            fabric.common.TransactionID $currentTid216 =
                              $tm217.getCurrentTid();
                            if ($e215.tid == null ||
                                  !$e215.tid.isDescendantOf($currentTid216)) {
                                throw $e215;
                            }
                            throw new fabric.worker.UserAbortException($e215);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e215) {
                            $commit212 = false;
                            fabric.common.TransactionID $currentTid216 =
                              $tm217.getCurrentTid();
                            if ($e215.tid.isDescendantOf($currentTid216))
                                continue $label211;
                            if ($currentTid216.parent != null) {
                                $retry213 = false;
                                throw $e215;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e215) {
                            $commit212 = false;
                            $retry213 = false;
                            if ($tm217.inNestedTxn()) { $keepReads214 = true; }
                            throw $e215;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid216 =
                              $tm217.getCurrentTid();
                            if ($commit212) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e215) {
                                    $commit212 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e215) {
                                    $commit212 = false;
                                    $retry213 = false;
                                    $keepReads214 = $e215.keepReads;
                                    if ($e215.tid ==
                                          null ||
                                          !$e215.tid.isDescendantOf(
                                                       $currentTid216))
                                        throw $e215;
                                    throw new fabric.worker.UserAbortException(
                                            $e215);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e215) {
                                    $commit212 = false;
                                    $currentTid216 = $tm217.getCurrentTid();
                                    if ($currentTid216 != null) {
                                        if ($e215.tid.equals($currentTid216) ||
                                              !$e215.tid.isDescendantOf(
                                                           $currentTid216)) {
                                            throw $e215;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm217.inNestedTxn() &&
                                      $tm217.checkForStaleObjects()) {
                                    $retry213 = true;
                                    $keepReads214 = false;
                                }
                                if ($keepReads214) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e215) {
                                        $currentTid216 = $tm217.getCurrentTid();
                                        if ($currentTid216 != null &&
                                              ($e215.tid.equals($currentTid216) || !$e215.tid.isDescendantOf($currentTid216))) {
                                            throw $e215;
                                        } else {
                                            $retry213 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit212) {
                                { rtn = rtn$var210; }
                                if ($retry213) { continue $label211; }
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
                    long rtn$var221 = rtn;
                    fabric.worker.transaction.TransactionManager $tm228 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled231 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff229 = 1;
                    boolean $doBackoff230 = true;
                    boolean $retry224 = true;
                    boolean $keepReads225 = false;
                    $label222: for (boolean $commit223 = false; !$commit223; ) {
                        if ($backoffEnabled231) {
                            if ($doBackoff230) {
                                if ($backoff229 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff229));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e226) {
                                            
                                        }
                                    }
                                }
                                if ($backoff229 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff229 =
                                      java.lang.Math.
                                        min(
                                          $backoff229 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff230 = $backoff229 <= 32 || !$doBackoff230;
                        }
                        $commit223 = true;
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
                        catch (final fabric.worker.RetryException $e226) {
                            $commit223 = false;
                            continue $label222;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e226) {
                            $commit223 = false;
                            $retry224 = false;
                            $keepReads225 = $e226.keepReads;
                            fabric.common.TransactionID $currentTid227 =
                              $tm228.getCurrentTid();
                            if ($e226.tid == null ||
                                  !$e226.tid.isDescendantOf($currentTid227)) {
                                throw $e226;
                            }
                            throw new fabric.worker.UserAbortException($e226);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e226) {
                            $commit223 = false;
                            fabric.common.TransactionID $currentTid227 =
                              $tm228.getCurrentTid();
                            if ($e226.tid.isDescendantOf($currentTid227))
                                continue $label222;
                            if ($currentTid227.parent != null) {
                                $retry224 = false;
                                throw $e226;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e226) {
                            $commit223 = false;
                            $retry224 = false;
                            if ($tm228.inNestedTxn()) { $keepReads225 = true; }
                            throw $e226;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid227 =
                              $tm228.getCurrentTid();
                            if ($commit223) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e226) {
                                    $commit223 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e226) {
                                    $commit223 = false;
                                    $retry224 = false;
                                    $keepReads225 = $e226.keepReads;
                                    if ($e226.tid ==
                                          null ||
                                          !$e226.tid.isDescendantOf(
                                                       $currentTid227))
                                        throw $e226;
                                    throw new fabric.worker.UserAbortException(
                                            $e226);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e226) {
                                    $commit223 = false;
                                    $currentTid227 = $tm228.getCurrentTid();
                                    if ($currentTid227 != null) {
                                        if ($e226.tid.equals($currentTid227) ||
                                              !$e226.tid.isDescendantOf(
                                                           $currentTid227)) {
                                            throw $e226;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm228.inNestedTxn() &&
                                      $tm228.checkForStaleObjects()) {
                                    $retry224 = true;
                                    $keepReads225 = false;
                                }
                                if ($keepReads225) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e226) {
                                        $currentTid227 = $tm228.getCurrentTid();
                                        if ($currentTid227 != null &&
                                              ($e226.tid.equals($currentTid227) || !$e226.tid.isDescendantOf($currentTid227))) {
                                            throw $e226;
                                        } else {
                                            $retry224 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit223) {
                                { rtn = rtn$var221; }
                                if ($retry224) { continue $label222; }
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
                    double rtn$var232 = rtn;
                    fabric.worker.transaction.TransactionManager $tm239 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled242 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff240 = 1;
                    boolean $doBackoff241 = true;
                    boolean $retry235 = true;
                    boolean $keepReads236 = false;
                    $label233: for (boolean $commit234 = false; !$commit234; ) {
                        if ($backoffEnabled242) {
                            if ($doBackoff241) {
                                if ($backoff240 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff240));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e237) {
                                            
                                        }
                                    }
                                }
                                if ($backoff240 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff240 =
                                      java.lang.Math.
                                        min(
                                          $backoff240 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff241 = $backoff240 <= 32 || !$doBackoff241;
                        }
                        $commit234 = true;
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
                        catch (final fabric.worker.RetryException $e237) {
                            $commit234 = false;
                            continue $label233;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e237) {
                            $commit234 = false;
                            $retry235 = false;
                            $keepReads236 = $e237.keepReads;
                            fabric.common.TransactionID $currentTid238 =
                              $tm239.getCurrentTid();
                            if ($e237.tid == null ||
                                  !$e237.tid.isDescendantOf($currentTid238)) {
                                throw $e237;
                            }
                            throw new fabric.worker.UserAbortException($e237);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e237) {
                            $commit234 = false;
                            fabric.common.TransactionID $currentTid238 =
                              $tm239.getCurrentTid();
                            if ($e237.tid.isDescendantOf($currentTid238))
                                continue $label233;
                            if ($currentTid238.parent != null) {
                                $retry235 = false;
                                throw $e237;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e237) {
                            $commit234 = false;
                            $retry235 = false;
                            if ($tm239.inNestedTxn()) { $keepReads236 = true; }
                            throw $e237;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid238 =
                              $tm239.getCurrentTid();
                            if ($commit234) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e237) {
                                    $commit234 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e237) {
                                    $commit234 = false;
                                    $retry235 = false;
                                    $keepReads236 = $e237.keepReads;
                                    if ($e237.tid ==
                                          null ||
                                          !$e237.tid.isDescendantOf(
                                                       $currentTid238))
                                        throw $e237;
                                    throw new fabric.worker.UserAbortException(
                                            $e237);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e237) {
                                    $commit234 = false;
                                    $currentTid238 = $tm239.getCurrentTid();
                                    if ($currentTid238 != null) {
                                        if ($e237.tid.equals($currentTid238) ||
                                              !$e237.tid.isDescendantOf(
                                                           $currentTid238)) {
                                            throw $e237;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm239.inNestedTxn() &&
                                      $tm239.checkForStaleObjects()) {
                                    $retry235 = true;
                                    $keepReads236 = false;
                                }
                                if ($keepReads236) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e237) {
                                        $currentTid238 = $tm239.getCurrentTid();
                                        if ($currentTid238 != null &&
                                              ($e237.tid.equals($currentTid238) || !$e237.tid.isDescendantOf($currentTid238))) {
                                            throw $e237;
                                        } else {
                                            $retry235 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit234) {
                                { rtn = rtn$var232; }
                                if ($retry235) { continue $label233; }
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
                    double rtn$var243 = rtn;
                    fabric.worker.transaction.TransactionManager $tm250 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled253 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff251 = 1;
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
                                if ($backoff251 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff251 =
                                      java.lang.Math.
                                        min(
                                          $backoff251 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff252 = $backoff251 <= 32 || !$doBackoff252;
                        }
                        $commit245 = true;
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
                        catch (final fabric.worker.RetryException $e248) {
                            $commit245 = false;
                            continue $label244;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e248) {
                            $commit245 = false;
                            $retry246 = false;
                            $keepReads247 = $e248.keepReads;
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
                            $retry246 = false;
                            if ($tm250.inNestedTxn()) { $keepReads247 = true; }
                            throw $e248;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid249 =
                              $tm250.getCurrentTid();
                            if ($commit245) {
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
                            } else {
                                if (!$tm250.inNestedTxn() &&
                                      $tm250.checkForStaleObjects()) {
                                    $retry246 = true;
                                    $keepReads247 = false;
                                }
                                if ($keepReads247) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e248) {
                                        $currentTid249 = $tm250.getCurrentTid();
                                        if ($currentTid249 != null &&
                                              ($e248.tid.equals($currentTid249) || !$e248.tid.isDescendantOf($currentTid249))) {
                                            throw $e248;
                                        } else {
                                            $retry246 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit245) {
                                { rtn = rtn$var243; }
                                if ($retry246) { continue $label244; }
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
                    double rtn$var254 = rtn;
                    fabric.worker.transaction.TransactionManager $tm261 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled264 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff262 = 1;
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
                                if ($backoff262 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff262 =
                                      java.lang.Math.
                                        min(
                                          $backoff262 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff263 = $backoff262 <= 32 || !$doBackoff263;
                        }
                        $commit256 = true;
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
                        catch (final fabric.worker.RetryException $e259) {
                            $commit256 = false;
                            continue $label255;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e259) {
                            $commit256 = false;
                            $retry257 = false;
                            $keepReads258 = $e259.keepReads;
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
                            $retry257 = false;
                            if ($tm261.inNestedTxn()) { $keepReads258 = true; }
                            throw $e259;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid260 =
                              $tm261.getCurrentTid();
                            if ($commit256) {
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
                            } else {
                                if (!$tm261.inNestedTxn() &&
                                      $tm261.checkForStaleObjects()) {
                                    $retry257 = true;
                                    $keepReads258 = false;
                                }
                                if ($keepReads258) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e259) {
                                        $currentTid260 = $tm261.getCurrentTid();
                                        if ($currentTid260 != null &&
                                              ($e259.tid.equals($currentTid260) || !$e259.tid.isDescendantOf($currentTid260))) {
                                            throw $e259;
                                        } else {
                                            $retry257 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
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
        
        public double computeNoiseTerm(
          fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_computeNoiseTerm((fabric.metrics.SampledMetric)
                                        this.$getProxy(), weakStats);
        }
        
        private static double static_computeNoiseTerm(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.getUsePreset()) {
                    return tmp.get$presetNTerm();
                }
                else {
                    fabric.worker.metrics.RunningMetricStats preloaded =
                      tmp.get$stats().preload(tmp.get$key());
                    if (!fabric.lang.Object._Proxy.idEquals(preloaded,
                                                            tmp.get$stats()))
                        tmp.set$stats(preloaded);
                    return tmp.get$stats().getNoiseTermEstimate();
                }
            }
            else {
                double rtn = 0;
                {
                    double rtn$var265 = rtn;
                    fabric.worker.transaction.TransactionManager $tm272 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled275 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff273 = 1;
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
                                if ($backoff273 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff273 =
                                      java.lang.Math.
                                        min(
                                          $backoff273 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff274 = $backoff273 <= 32 || !$doBackoff274;
                        }
                        $commit267 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.getUsePreset()) {
                                rtn = tmp.get$presetNTerm();
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
                                rtn = tmp.get$stats().getNoiseTermEstimate();
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
                            $retry268 = false;
                            if ($tm272.inNestedTxn()) { $keepReads269 = true; }
                            throw $e270;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid271 =
                              $tm272.getCurrentTid();
                            if ($commit267) {
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
                            } else {
                                if (!$tm272.inNestedTxn() &&
                                      $tm272.checkForStaleObjects()) {
                                    $retry268 = true;
                                    $keepReads269 = false;
                                }
                                if ($keepReads269) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e270) {
                                        $currentTid271 = $tm272.getCurrentTid();
                                        if ($currentTid271 != null &&
                                              ($e270.tid.equals($currentTid271) || !$e270.tid.isDescendantOf($currentTid271))) {
                                            throw $e270;
                                        } else {
                                            $retry268 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
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
                    double result$var276 = result;
                    fabric.worker.transaction.TransactionManager $tm283 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled286 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff284 = 1;
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
                                if ($backoff284 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff284 =
                                      java.lang.Math.
                                        min(
                                          $backoff284 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff285 = $backoff284 <= 32 || !$doBackoff285;
                        }
                        $commit278 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeValue(weakStats); }
                        catch (final fabric.worker.RetryException $e281) {
                            $commit278 = false;
                            continue $label277;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e281) {
                            $commit278 = false;
                            $retry279 = false;
                            $keepReads280 = $e281.keepReads;
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
                            $retry279 = false;
                            if ($tm283.inNestedTxn()) { $keepReads280 = true; }
                            throw $e281;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid282 =
                              $tm283.getCurrentTid();
                            if ($commit278) {
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
                            } else {
                                if (!$tm283.inNestedTxn() &&
                                      $tm283.checkForStaleObjects()) {
                                    $retry279 = true;
                                    $keepReads280 = false;
                                }
                                if ($keepReads280) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e281) {
                                        $currentTid282 = $tm283.getCurrentTid();
                                        if ($currentTid282 != null &&
                                              ($e281.tid.equals($currentTid282) || !$e281.tid.isDescendantOf($currentTid282))) {
                                            throw $e281;
                                        } else {
                                            $retry279 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit278) {
                                { result = result$var276; }
                                if ($retry279) { continue $label277; }
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
                    long result$var287 = result;
                    fabric.worker.transaction.TransactionManager $tm294 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled297 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff295 = 1;
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
                                if ($backoff295 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff295 =
                                      java.lang.Math.
                                        min(
                                          $backoff295 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff296 = $backoff295 <= 32 || !$doBackoff296;
                        }
                        $commit289 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeSamples(weakStats); }
                        catch (final fabric.worker.RetryException $e292) {
                            $commit289 = false;
                            continue $label288;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e292) {
                            $commit289 = false;
                            $retry290 = false;
                            $keepReads291 = $e292.keepReads;
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
                            $retry290 = false;
                            if ($tm294.inNestedTxn()) { $keepReads291 = true; }
                            throw $e292;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid293 =
                              $tm294.getCurrentTid();
                            if ($commit289) {
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
                            } else {
                                if (!$tm294.inNestedTxn() &&
                                      $tm294.checkForStaleObjects()) {
                                    $retry290 = true;
                                    $keepReads291 = false;
                                }
                                if ($keepReads291) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e292) {
                                        $currentTid293 = $tm294.getCurrentTid();
                                        if ($currentTid293 != null &&
                                              ($e292.tid.equals($currentTid293) || !$e292.tid.isDescendantOf($currentTid293))) {
                                            throw $e292;
                                        } else {
                                            $retry290 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit289) {
                                { result = result$var287; }
                                if ($retry290) { continue $label288; }
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
                    long result$var298 = result;
                    fabric.worker.transaction.TransactionManager $tm305 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled308 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff306 = 1;
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
                                if ($backoff306 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff306 =
                                      java.lang.Math.
                                        min(
                                          $backoff306 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff307 = $backoff306 <= 32 || !$doBackoff307;
                        }
                        $commit300 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeLastUpdate(weakStats); }
                        catch (final fabric.worker.RetryException $e303) {
                            $commit300 = false;
                            continue $label299;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e303) {
                            $commit300 = false;
                            $retry301 = false;
                            $keepReads302 = $e303.keepReads;
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
                            $retry301 = false;
                            if ($tm305.inNestedTxn()) { $keepReads302 = true; }
                            throw $e303;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid304 =
                              $tm305.getCurrentTid();
                            if ($commit300) {
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
                            } else {
                                if (!$tm305.inNestedTxn() &&
                                      $tm305.checkForStaleObjects()) {
                                    $retry301 = true;
                                    $keepReads302 = false;
                                }
                                if ($keepReads302) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e303) {
                                        $currentTid304 = $tm305.getCurrentTid();
                                        if ($currentTid304 != null &&
                                              ($e303.tid.equals($currentTid304) || !$e303.tid.isDescendantOf($currentTid304))) {
                                            throw $e303;
                                        } else {
                                            $retry301 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit300) {
                                { result = result$var298; }
                                if ($retry301) { continue $label299; }
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
                    double result$var309 = result;
                    fabric.worker.transaction.TransactionManager $tm316 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled319 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff317 = 1;
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
                                if ($backoff317 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff317 =
                                      java.lang.Math.
                                        min(
                                          $backoff317 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff318 = $backoff317 <= 32 || !$doBackoff318;
                        }
                        $commit311 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeUpdateInterval(weakStats); }
                        catch (final fabric.worker.RetryException $e314) {
                            $commit311 = false;
                            continue $label310;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e314) {
                            $commit311 = false;
                            $retry312 = false;
                            $keepReads313 = $e314.keepReads;
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
                            $retry312 = false;
                            if ($tm316.inNestedTxn()) { $keepReads313 = true; }
                            throw $e314;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid315 =
                              $tm316.getCurrentTid();
                            if ($commit311) {
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
                            } else {
                                if (!$tm316.inNestedTxn() &&
                                      $tm316.checkForStaleObjects()) {
                                    $retry312 = true;
                                    $keepReads313 = false;
                                }
                                if ($keepReads313) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e314) {
                                        $currentTid315 = $tm316.getCurrentTid();
                                        if ($currentTid315 != null &&
                                              ($e314.tid.equals($currentTid315) || !$e314.tid.isDescendantOf($currentTid315))) {
                                            throw $e314;
                                        } else {
                                            $retry312 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
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
                    double result$var320 = result;
                    fabric.worker.transaction.TransactionManager $tm327 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled330 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff328 = 1;
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
                                if ($backoff328 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff328 =
                                      java.lang.Math.
                                        min(
                                          $backoff328 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff329 = $backoff328 <= 32 || !$doBackoff329;
                        }
                        $commit322 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeVelocity(weakStats); }
                        catch (final fabric.worker.RetryException $e325) {
                            $commit322 = false;
                            continue $label321;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e325) {
                            $commit322 = false;
                            $retry323 = false;
                            $keepReads324 = $e325.keepReads;
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
                            $retry323 = false;
                            if ($tm327.inNestedTxn()) { $keepReads324 = true; }
                            throw $e325;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid326 =
                              $tm327.getCurrentTid();
                            if ($commit322) {
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
                            } else {
                                if (!$tm327.inNestedTxn() &&
                                      $tm327.checkForStaleObjects()) {
                                    $retry323 = true;
                                    $keepReads324 = false;
                                }
                                if ($keepReads324) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e325) {
                                        $currentTid326 = $tm327.getCurrentTid();
                                        if ($currentTid326 != null &&
                                              ($e325.tid.equals($currentTid326) || !$e325.tid.isDescendantOf($currentTid326))) {
                                            throw $e325;
                                        } else {
                                            $retry323 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
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
                    double result$var331 = result;
                    fabric.worker.transaction.TransactionManager $tm338 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled341 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff339 = 1;
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
                                if ($backoff339 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff339 =
                                      java.lang.Math.
                                        min(
                                          $backoff339 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff340 = $backoff339 <= 32 || !$doBackoff340;
                        }
                        $commit333 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeNoise(weakStats); }
                        catch (final fabric.worker.RetryException $e336) {
                            $commit333 = false;
                            continue $label332;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e336) {
                            $commit333 = false;
                            $retry334 = false;
                            $keepReads335 = $e336.keepReads;
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
                            $retry334 = false;
                            if ($tm338.inNestedTxn()) { $keepReads335 = true; }
                            throw $e336;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid337 =
                              $tm338.getCurrentTid();
                            if ($commit333) {
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
                            } else {
                                if (!$tm338.inNestedTxn() &&
                                      $tm338.checkForStaleObjects()) {
                                    $retry334 = true;
                                    $keepReads335 = false;
                                }
                                if ($keepReads335) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e336) {
                                        $currentTid337 = $tm338.getCurrentTid();
                                        if ($currentTid337 != null &&
                                              ($e336.tid.equals($currentTid337) || !$e336.tid.isDescendantOf($currentTid337))) {
                                            throw $e336;
                                        } else {
                                            $retry334 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
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
        
        public double noiseTerm(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_noiseTerm((fabric.metrics.SampledMetric) this.$getProxy(),
                               weakStats);
        }
        
        private static double static_noiseTerm(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (!tmp.getUsePreset() && weakStats.containsKey(tmp))
                return weakStats.getNoiseTerm(tmp);
            double result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                result = tmp.computeNoiseTerm(weakStats);
            }
            else {
                {
                    double result$var342 = result;
                    fabric.worker.transaction.TransactionManager $tm349 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled352 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff350 = 1;
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
                                if ($backoff350 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff350 =
                                      java.lang.Math.
                                        min(
                                          $backoff350 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff351 = $backoff350 <= 32 || !$doBackoff351;
                        }
                        $commit344 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeNoiseTerm(weakStats); }
                        catch (final fabric.worker.RetryException $e347) {
                            $commit344 = false;
                            continue $label343;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e347) {
                            $commit344 = false;
                            $retry345 = false;
                            $keepReads346 = $e347.keepReads;
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
                            $retry345 = false;
                            if ($tm349.inNestedTxn()) { $keepReads346 = true; }
                            throw $e347;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid348 =
                              $tm349.getCurrentTid();
                            if ($commit344) {
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
                            } else {
                                if (!$tm349.inNestedTxn() &&
                                      $tm349.checkForStaleObjects()) {
                                    $retry345 = true;
                                    $keepReads346 = false;
                                }
                                if ($keepReads346) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e347) {
                                        $currentTid348 = $tm349.getCurrentTid();
                                        if ($currentTid348 != null &&
                                              ($e347.tid.equals($currentTid348) || !$e347.tid.isDescendantOf($currentTid348))) {
                                            throw $e347;
                                        } else {
                                            $retry345 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
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
            out.writeDouble(this.presetNTerm);
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
            this.presetNTerm = in.readDouble();
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
            this.presetNTerm = src.presetNTerm;
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
    
    public static final byte[] $classHash = new byte[] { 67, -79, -101, 100, 33,
    -43, 9, -49, -15, -102, -107, 2, -128, 104, -90, -16, 69, 119, 44, 82, 19,
    -34, 119, 117, 88, 116, 49, 121, 93, -19, 88, 13 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556815408000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVbC3AV1Rn+9ybkRV6AQR4BQgiP8MgVpKIGsXLLIxIgJsBgGAmbezfJmr13L7vn5kGL1fqAOgx1FCk+wNHis4iPamtb6FgrFqvUURFRp8g4KjiIBRm19dn/P3tyX9ndZGdSxv2/zZ7zn/P/5zv/f87Zu+45DYNMA8pb5GZVq2LdUcWsWig319TWyYaphAKabJor8GlTcHBmzfaTD4fG+sBXC/lBOaJH1KCsNUVMBoW118odsj+iMP/K+prqNZAbJMXFstnGwLdmfpcBZVFd627VdCY66dX+ndP82369tvjpDChqhCI10sBkpgYDeoQpXawR8sNKuFkxzCtCISXUCEMiihJqUAxV1tQNWFGPNMJQU22NyCxmKGa9YupaB1UcasaiisH77HlI5utothELMt1A84st82NM1fy1qsmqayGrRVW0kLkeroPMWhjUosmtWHF4bY8Xft6ifyE9x+p5KppptMhBpUcls12NhBiMS9eIe1yxBCuganZYYW16vKvMiIwPYKhlkiZHWv0NzFAjrVh1kB7DXhiMcmwUK+VE5WC73Ko0MRiRXq/OKsJauXxYSIVBSXo13hJyNiqNsyS2Ti+bu/WnkcURH0hoc0gJamR/DiqNTVOqV1oUQ4kEFUsxf2rtdnn4/s0+AKxcklbZqvOHn5398fSxzx+06oy2qbO8+VolyJqCu5sLXy8NVF6SQWbkRHVTpamQ4jlntU6UVHdFcbYPj7dIhVU9hc/Xv3T19Y8pp3yQVwNZQV2LhXFWDQnq4aiqKcYiJaIYMlNCNZCrREIBXl4D2Xhfq0YU6+nylhZTYTWQqfFHWTr/G4eoBZugIcrGezXSovfcR2XWxu+7ogCQjRdI+N+HAHPewvuxABkHGNT52/Sw4m/WYkonTm8/XopsBNv8GLeGGpwR1KPdftMI+o1YhKlY03rux6mEYPob5HBUU0JL+Z9VaEv0/9BmF/lR3ClJOMTjgnpIaZZN5EvMnfl1GobHYl0LKUZTUNu6vwaG7b+Lz59cmvMmzls+QhJyXpqeLZJ1t8XmLzi7t+kVa+6RrhhABqWWjVXCxqoUG9GsfIqqKsxTVZin9khdVYFdNb/lkyfL5FEWbykfW7o0qsmsRTfCXSBJ3K3zuD6fNch5O+YSTBf5lQ3XXLluc3kGTtdoZyYxiFUr0oMnkXJq8E7GiGgKFm06+eUT2zfqiTBiUNEruntrUnSWp4+RoQeVEGa/RPNTy+Rnm/ZvrPBRZsnFpMdknJaYQcam95ESpdU9GY9GY1AtDKYxkDUq6klTeazN0DsTTzj3hSSGWtOABivNQJ4sL2uI7jz6z08u5MtIT14tSkrADQqrToplaqyIR+2QxNivMBQF6/1rR90dd57etIYPPNaYYNdhBckAxrCMwasbNx9c/877x3Yf9iXIYpAVjTVrarCL+zLkB/wn4fU9XRSQ9IAQ03JAJIOyeDaIUs+TErZhXtAwN6HpZsXKSFgPqS2q3KwpNFO+LZo489lPtxZbdGv4xBo8A6b33UDi+cj5cP0ra78ay5uRgrQuJcYvUc1KdsMSLV9hGHI32dF1wxtj7vq7vBNnPqYqU92g8OwDfDyAEziLj8UMLmemlc0mUW6NVil/nm32TvwLaQVNzMVG/557RwXmnbIiPj4XqY3xNhG/Sk4Kk1mPhb/wlWcd8EF2IxTzxVuOsFUypi6cBo24/JoB8bAWClLKU5dSa92ojsdaaXocJHWbHgWJTIP3VJvu86yJb00cHIhiGqSReJUBZGZamPENlQ6LkjyvSwJ+cylXmcDlJBKVfCB9DLKjhtqBM4tBrhoOxxhxz3uZxiCjXem2Geo6Qw1jtHSINVbZvO3WH6q2brOmmbURmdBrL5CsY21GeC8FvKsu7GW8Wy9cY+GJJzb++ZGNm6yFemjqsrogEgs/fuS7V6t2HH/ZJlVnarqVbov5GFwUH8IisNY9WA0w+JDAv9gM4WKHIaTbqSQu7xk2HFJc3Fg9r3qFcJDgJxj7IR2DX3G1ZC1A/myBE20sqfNqyXz6c6lrnyHs6x6BW2z6XOm1z1V993ktQIHPwvxzNn02eu1zmXuf5Xhh4ikIC2y06bOp330OFn2uUIywY7/51O9EzPrLASadFnjYpt+Qfb+4WuRGDZ1hGlFCXfFmadRgsGjuTYH/SGqW4TkLTzQm1yhhMEXsVDp1o10x4huW+lgkgtsQa8PSEFcYmb4h4a51OZjIhyZhG/+XJTaTLwpMDqikRA4U+GOc9v086Hf/Ytuu0PIHZ/rEarAAR4Tp0Rma0qFoSU1VUgrpda5cyk87idR+/NSYSwLtH7VaKWRcWs/ptR9duuflRZOCt/sgI57Dex2xUpWqUzN3nqHgCTGyIiV/l8XHKpfGYAle0wEG7RO4IXl6JCbVBBLR1CmQI1S6BZrpw5xYUX2JeRzg05U3/XOXdfcGEhsYjLHmToWYNBUpu9yKhIGdqW6Nw2sFztJOgbI3t0hlncBGZ7eSDd7sUnYriRtxXrcqbKWp1PHYtUvQ2c26rilyxM6lUrwa0Z6vBZ7w5hKpfCzweP9c2uZStp3ErxjkoUuWP/WcWCfLcTTzZYHLvFlOKksFLuqf5Ttdyu4jsSPZ8vmulrdgtwcFPuvNclJ5RuDe/ln+kEvZIyTuT7Z8lavlGi4zfoGl3iwnldECS/pn+RMuZU+ReCzZ8mWOlpfhZWC3uwRu8mY5qdwi8Pr+Wf6cS9mfSPyOQWHC8viaa2f9ZLw2AhTuFniLN+tJ5WaBP3e2XkosflYq/auLC38jsQ8Hn8ntipU/7XJPZoeuhuxcqsDrJtzFTBNY7M0lUikSmOvBpUMuLr1G4iCDHIoBOvrQ3wfsjJ+F1214VrlIYIE340klX2CWs/FJ6xvfiQV400dcPDhK4vUUUux84Fu4C/HaATCkyMLiky4+tPbapnGVEwKP9UlAz6ZtrP2mje/TlspR+60at+Z9F7c/IvEuo7fS4WiMKXHy0oOJb+bq8boX7+8WeLWD47Yb5nmMXnTRi/W0rWuxaG21wCv7xSuf9cd5r5+5OHiGxAkGw6yum/ryM07w4wDDnhJ4qzeCSeWXAl1SXlKEWX585eLHf0l8jnlPOJCUNwJOVKH5582zcNh3nqgiccqGJmrpW4GfeaRJkpzdkzLo4TcMSlJpcvGSE0VZ5AWA4Q0CK70RRSpTBI7rP1FSnosn+SQGMevdPbpQK5tsZTQkM8WRq5V4vYQmfCzwgQHhilq6X+AdXrlyWZ6l4SQKGYxI5crdUU7XXLzeBhiZbeGIt73RRSpHBB7yQNcYF2eIdmkETjzhheVBDf2k1iFrjplwDV7voSe7BTY6eOKNMmrpaoFLvFI22cVLigtpPIPSVMr6djYeZZ8CjD4k8DfeaCOVBwTe5YG2C1wcmkViGoOinnyuaHpQZd2OhFGMncFt+FqBowaEMGpppMDBXgm71MW/uSRmMxietmy5uNmzckm4zxp3mcDhnqjiKiUC8z1QFXBxZQGJeYktxjJdNV23GFIB9n5QoNNpwxNPvKVbBHZ55Wmpi3PLSSzqtb1w9JGTNActmQRQ/qBAt3c8NiSRSrfAqAeSVrn4sZrEVYx+4Eg44HSw4kStwranAky42MLycwNCFLX0ucAPvRK1zsXBZhKNDM63Icr1ADkKrQkATMqxcOJXLmT1PrBwlS8FnnF2KNlU1aWsnUQID1pMtz7N6DkeFPOf2uiHpqqkgl4HAjsPy9C8xejhdQJbvXlIKi0C1/XPww6XMi7WMyhQzQZ0Q1MamG7wWNpiF0uXY8+4ZE6psHDyWW+xRCpnBJ5wNt72PCm5vC+V6H2ptAEXqBhfYxeYTA0j8rq2B+NKtKINYGqRhZX/9sYCqXwm8KSzI72SgstrUomOPdKNDAZ1uJ4Kl2CDGlq+RWD9gGQCaukqgQGvmeB2F6+2kdiCgSEygaNzcVo2AEw7J/BNb7SQyhsCX/VAyz0uDuwksZ1BttnHGZCIwaCebgi8eECIoZbmCKx0dsmemAdd/HqYxH14xBXEuLjHqZmORuCcm3Fc4H5v1JDKPoG/90DNXhcXniTxKIM8re9T3zJs9TaAKkXglAFhh1qaLHCkV3ZcXrxK9OJVehrPtYIddw973vVJuwD8Hwj8ozeCSOU5gU97IMjl3atE716lfTjHYv07563AlvHscsF6gVUDQhK1NEOgy3sHe5JecXGPTsXSgcRrlL69jEfSkwAzvxToMcmRyhsCvSS5wy6eHCHxGm50Ovo62VEcPYOTrVvg3AGhiFqqFjjDK0XHXByj3/qko7gx6Fl+XPyLr0AvANA3IIQXHnfwz4EcUnlf4DseyDnh4sMnJD7AjUHE9SxH689LaPlGgQEHy70xQy3NFzjbKzNnXbw6R+JUYmPg6BynheL3MMCPfhD4njdaSOVdgYedvehFy9cuDnxL4gsGuZE+T2/LsdGjABfdJHDhgFBDLS0QOMcjNb5MZ8989FOP9D2ecpKpiTvYhaeFlO8Q6NvD0TZfAYuv04OBF5XdHy2ZXuLwBfCIXv+/gNDbu6so5/xdK9/m37TGvzzPrYWclpimJX+jl3SfFTWUFpUPai6XhVHu02BchVI/GWb8g3y6o0Hw5Vr1ChlkWfXoryI+ovzt1Sg+VHj2K0n78tgaBF6FdzgqZtD/CbHn3Pn/ycpZcZx/foojWxZ46p7Q+LdyXzt7952+69seOrOgc3r9sGOdsdVsZvc1p1cX/A8wISzFoTEAAA==";
}
