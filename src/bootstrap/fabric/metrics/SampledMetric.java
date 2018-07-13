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
    
    public fabric.worker.metrics.ImmutableObserverSet handleDirectUpdates();
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
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
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return ((fabric.metrics.SampledMetric) fetch()).getLeafSubjects();
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
                    double result$var239 = result;
                    fabric.worker.transaction.TransactionManager $tm245 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled248 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff246 = 1;
                    boolean $doBackoff247 = true;
                    boolean $retry242 = true;
                    $label240: for (boolean $commit241 = false; !$commit241; ) {
                        if ($backoffEnabled248) {
                            if ($doBackoff247) {
                                if ($backoff246 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff246);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e243) {
                                            
                                        }
                                    }
                                }
                                if ($backoff246 < 5000) $backoff246 *= 2;
                            }
                            $doBackoff247 = $backoff246 <= 32 || !$doBackoff247;
                        }
                        $commit241 = true;
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
                        catch (final fabric.worker.RetryException $e243) {
                            $commit241 = false;
                            continue $label240;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e243) {
                            $commit241 = false;
                            fabric.common.TransactionID $currentTid244 =
                              $tm245.getCurrentTid();
                            if ($e243.tid.isDescendantOf($currentTid244))
                                continue $label240;
                            if ($currentTid244.parent != null) {
                                $retry242 = false;
                                throw $e243;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e243) {
                            $commit241 = false;
                            if ($tm245.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid244 =
                              $tm245.getCurrentTid();
                            if ($e243.tid.isDescendantOf($currentTid244)) {
                                $retry242 = true;
                            }
                            else if ($currentTid244.parent != null) {
                                $retry242 = false;
                                throw $e243;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e243) {
                            $commit241 = false;
                            if ($tm245.checkForStaleObjects())
                                continue $label240;
                            $retry242 = false;
                            throw new fabric.worker.AbortException($e243);
                        }
                        finally {
                            if ($commit241) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e243) {
                                    $commit241 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e243) {
                                    $commit241 = false;
                                    fabric.common.TransactionID $currentTid244 =
                                      $tm245.getCurrentTid();
                                    if ($currentTid244 != null) {
                                        if ($e243.tid.equals($currentTid244) ||
                                              !$e243.tid.isDescendantOf(
                                                           $currentTid244)) {
                                            throw $e243;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit241 && $retry242) {
                                { result = result$var239; }
                                continue $label240;
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
                    long rtn$var249 = rtn;
                    fabric.worker.transaction.TransactionManager $tm255 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled258 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff256 = 1;
                    boolean $doBackoff257 = true;
                    boolean $retry252 = true;
                    $label250: for (boolean $commit251 = false; !$commit251; ) {
                        if ($backoffEnabled258) {
                            if ($doBackoff257) {
                                if ($backoff256 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff256);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e253) {
                                            
                                        }
                                    }
                                }
                                if ($backoff256 < 5000) $backoff256 *= 2;
                            }
                            $doBackoff257 = $backoff256 <= 32 || !$doBackoff257;
                        }
                        $commit251 = true;
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
                        catch (final fabric.worker.RetryException $e253) {
                            $commit251 = false;
                            continue $label250;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e253) {
                            $commit251 = false;
                            fabric.common.TransactionID $currentTid254 =
                              $tm255.getCurrentTid();
                            if ($e253.tid.isDescendantOf($currentTid254))
                                continue $label250;
                            if ($currentTid254.parent != null) {
                                $retry252 = false;
                                throw $e253;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e253) {
                            $commit251 = false;
                            if ($tm255.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid254 =
                              $tm255.getCurrentTid();
                            if ($e253.tid.isDescendantOf($currentTid254)) {
                                $retry252 = true;
                            }
                            else if ($currentTid254.parent != null) {
                                $retry252 = false;
                                throw $e253;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e253) {
                            $commit251 = false;
                            if ($tm255.checkForStaleObjects())
                                continue $label250;
                            $retry252 = false;
                            throw new fabric.worker.AbortException($e253);
                        }
                        finally {
                            if ($commit251) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e253) {
                                    $commit251 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e253) {
                                    $commit251 = false;
                                    fabric.common.TransactionID $currentTid254 =
                                      $tm255.getCurrentTid();
                                    if ($currentTid254 != null) {
                                        if ($e253.tid.equals($currentTid254) ||
                                              !$e253.tid.isDescendantOf(
                                                           $currentTid254)) {
                                            throw $e253;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit251 && $retry252) {
                                { rtn = rtn$var249; }
                                continue $label250;
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
                    long rtn$var259 = rtn;
                    fabric.worker.transaction.TransactionManager $tm265 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled268 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff266 = 1;
                    boolean $doBackoff267 = true;
                    boolean $retry262 = true;
                    $label260: for (boolean $commit261 = false; !$commit261; ) {
                        if ($backoffEnabled268) {
                            if ($doBackoff267) {
                                if ($backoff266 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff266);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e263) {
                                            
                                        }
                                    }
                                }
                                if ($backoff266 < 5000) $backoff266 *= 2;
                            }
                            $doBackoff267 = $backoff266 <= 32 || !$doBackoff267;
                        }
                        $commit261 = true;
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
                        catch (final fabric.worker.RetryException $e263) {
                            $commit261 = false;
                            continue $label260;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e263) {
                            $commit261 = false;
                            fabric.common.TransactionID $currentTid264 =
                              $tm265.getCurrentTid();
                            if ($e263.tid.isDescendantOf($currentTid264))
                                continue $label260;
                            if ($currentTid264.parent != null) {
                                $retry262 = false;
                                throw $e263;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e263) {
                            $commit261 = false;
                            if ($tm265.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid264 =
                              $tm265.getCurrentTid();
                            if ($e263.tid.isDescendantOf($currentTid264)) {
                                $retry262 = true;
                            }
                            else if ($currentTid264.parent != null) {
                                $retry262 = false;
                                throw $e263;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e263) {
                            $commit261 = false;
                            if ($tm265.checkForStaleObjects())
                                continue $label260;
                            $retry262 = false;
                            throw new fabric.worker.AbortException($e263);
                        }
                        finally {
                            if ($commit261) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e263) {
                                    $commit261 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e263) {
                                    $commit261 = false;
                                    fabric.common.TransactionID $currentTid264 =
                                      $tm265.getCurrentTid();
                                    if ($currentTid264 != null) {
                                        if ($e263.tid.equals($currentTid264) ||
                                              !$e263.tid.isDescendantOf(
                                                           $currentTid264)) {
                                            throw $e263;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit261 && $retry262) {
                                { rtn = rtn$var259; }
                                continue $label260;
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
                    double rtn$var269 = rtn;
                    fabric.worker.transaction.TransactionManager $tm275 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled278 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff276 = 1;
                    boolean $doBackoff277 = true;
                    boolean $retry272 = true;
                    $label270: for (boolean $commit271 = false; !$commit271; ) {
                        if ($backoffEnabled278) {
                            if ($doBackoff277) {
                                if ($backoff276 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff276);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e273) {
                                            
                                        }
                                    }
                                }
                                if ($backoff276 < 5000) $backoff276 *= 2;
                            }
                            $doBackoff277 = $backoff276 <= 32 || !$doBackoff277;
                        }
                        $commit271 = true;
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
                        catch (final fabric.worker.RetryException $e273) {
                            $commit271 = false;
                            continue $label270;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e273) {
                            $commit271 = false;
                            fabric.common.TransactionID $currentTid274 =
                              $tm275.getCurrentTid();
                            if ($e273.tid.isDescendantOf($currentTid274))
                                continue $label270;
                            if ($currentTid274.parent != null) {
                                $retry272 = false;
                                throw $e273;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e273) {
                            $commit271 = false;
                            if ($tm275.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid274 =
                              $tm275.getCurrentTid();
                            if ($e273.tid.isDescendantOf($currentTid274)) {
                                $retry272 = true;
                            }
                            else if ($currentTid274.parent != null) {
                                $retry272 = false;
                                throw $e273;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e273) {
                            $commit271 = false;
                            if ($tm275.checkForStaleObjects())
                                continue $label270;
                            $retry272 = false;
                            throw new fabric.worker.AbortException($e273);
                        }
                        finally {
                            if ($commit271) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e273) {
                                    $commit271 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e273) {
                                    $commit271 = false;
                                    fabric.common.TransactionID $currentTid274 =
                                      $tm275.getCurrentTid();
                                    if ($currentTid274 != null) {
                                        if ($e273.tid.equals($currentTid274) ||
                                              !$e273.tid.isDescendantOf(
                                                           $currentTid274)) {
                                            throw $e273;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit271 && $retry272) {
                                { rtn = rtn$var269; }
                                continue $label270;
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
                    double rtn$var279 = rtn;
                    fabric.worker.transaction.TransactionManager $tm285 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled288 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff286 = 1;
                    boolean $doBackoff287 = true;
                    boolean $retry282 = true;
                    $label280: for (boolean $commit281 = false; !$commit281; ) {
                        if ($backoffEnabled288) {
                            if ($doBackoff287) {
                                if ($backoff286 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff286);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e283) {
                                            
                                        }
                                    }
                                }
                                if ($backoff286 < 5000) $backoff286 *= 2;
                            }
                            $doBackoff287 = $backoff286 <= 32 || !$doBackoff287;
                        }
                        $commit281 = true;
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
                        catch (final fabric.worker.RetryException $e283) {
                            $commit281 = false;
                            continue $label280;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e283) {
                            $commit281 = false;
                            fabric.common.TransactionID $currentTid284 =
                              $tm285.getCurrentTid();
                            if ($e283.tid.isDescendantOf($currentTid284))
                                continue $label280;
                            if ($currentTid284.parent != null) {
                                $retry282 = false;
                                throw $e283;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e283) {
                            $commit281 = false;
                            if ($tm285.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid284 =
                              $tm285.getCurrentTid();
                            if ($e283.tid.isDescendantOf($currentTid284)) {
                                $retry282 = true;
                            }
                            else if ($currentTid284.parent != null) {
                                $retry282 = false;
                                throw $e283;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e283) {
                            $commit281 = false;
                            if ($tm285.checkForStaleObjects())
                                continue $label280;
                            $retry282 = false;
                            throw new fabric.worker.AbortException($e283);
                        }
                        finally {
                            if ($commit281) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e283) {
                                    $commit281 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e283) {
                                    $commit281 = false;
                                    fabric.common.TransactionID $currentTid284 =
                                      $tm285.getCurrentTid();
                                    if ($currentTid284 != null) {
                                        if ($e283.tid.equals($currentTid284) ||
                                              !$e283.tid.isDescendantOf(
                                                           $currentTid284)) {
                                            throw $e283;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit281 && $retry282) {
                                { rtn = rtn$var279; }
                                continue $label280;
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
                    double rtn$var289 = rtn;
                    fabric.worker.transaction.TransactionManager $tm295 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled298 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff296 = 1;
                    boolean $doBackoff297 = true;
                    boolean $retry292 = true;
                    $label290: for (boolean $commit291 = false; !$commit291; ) {
                        if ($backoffEnabled298) {
                            if ($doBackoff297) {
                                if ($backoff296 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff296);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e293) {
                                            
                                        }
                                    }
                                }
                                if ($backoff296 < 5000) $backoff296 *= 2;
                            }
                            $doBackoff297 = $backoff296 <= 32 || !$doBackoff297;
                        }
                        $commit291 = true;
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
                        catch (final fabric.worker.RetryException $e293) {
                            $commit291 = false;
                            continue $label290;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e293) {
                            $commit291 = false;
                            fabric.common.TransactionID $currentTid294 =
                              $tm295.getCurrentTid();
                            if ($e293.tid.isDescendantOf($currentTid294))
                                continue $label290;
                            if ($currentTid294.parent != null) {
                                $retry292 = false;
                                throw $e293;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e293) {
                            $commit291 = false;
                            if ($tm295.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid294 =
                              $tm295.getCurrentTid();
                            if ($e293.tid.isDescendantOf($currentTid294)) {
                                $retry292 = true;
                            }
                            else if ($currentTid294.parent != null) {
                                $retry292 = false;
                                throw $e293;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e293) {
                            $commit291 = false;
                            if ($tm295.checkForStaleObjects())
                                continue $label290;
                            $retry292 = false;
                            throw new fabric.worker.AbortException($e293);
                        }
                        finally {
                            if ($commit291) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e293) {
                                    $commit291 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e293) {
                                    $commit291 = false;
                                    fabric.common.TransactionID $currentTid294 =
                                      $tm295.getCurrentTid();
                                    if ($currentTid294 != null) {
                                        if ($e293.tid.equals($currentTid294) ||
                                              !$e293.tid.isDescendantOf(
                                                           $currentTid294)) {
                                            throw $e293;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit291 && $retry292) {
                                { rtn = rtn$var289; }
                                continue $label290;
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
                    double result$var299 = result;
                    fabric.worker.transaction.TransactionManager $tm305 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled308 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff306 = 1;
                    boolean $doBackoff307 = true;
                    boolean $retry302 = true;
                    $label300: for (boolean $commit301 = false; !$commit301; ) {
                        if ($backoffEnabled308) {
                            if ($doBackoff307) {
                                if ($backoff306 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff306);
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
                        $commit301 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeValue(weakStats); }
                        catch (final fabric.worker.RetryException $e303) {
                            $commit301 = false;
                            continue $label300;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e303) {
                            $commit301 = false;
                            fabric.common.TransactionID $currentTid304 =
                              $tm305.getCurrentTid();
                            if ($e303.tid.isDescendantOf($currentTid304))
                                continue $label300;
                            if ($currentTid304.parent != null) {
                                $retry302 = false;
                                throw $e303;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e303) {
                            $commit301 = false;
                            if ($tm305.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid304 =
                              $tm305.getCurrentTid();
                            if ($e303.tid.isDescendantOf($currentTid304)) {
                                $retry302 = true;
                            }
                            else if ($currentTid304.parent != null) {
                                $retry302 = false;
                                throw $e303;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e303) {
                            $commit301 = false;
                            if ($tm305.checkForStaleObjects())
                                continue $label300;
                            $retry302 = false;
                            throw new fabric.worker.AbortException($e303);
                        }
                        finally {
                            if ($commit301) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e303) {
                                    $commit301 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e303) {
                                    $commit301 = false;
                                    fabric.common.TransactionID $currentTid304 =
                                      $tm305.getCurrentTid();
                                    if ($currentTid304 != null) {
                                        if ($e303.tid.equals($currentTid304) ||
                                              !$e303.tid.isDescendantOf(
                                                           $currentTid304)) {
                                            throw $e303;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit301 && $retry302) {
                                { result = result$var299; }
                                continue $label300;
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
                    long result$var309 = result;
                    fabric.worker.transaction.TransactionManager $tm315 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled318 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff316 = 1;
                    boolean $doBackoff317 = true;
                    boolean $retry312 = true;
                    $label310: for (boolean $commit311 = false; !$commit311; ) {
                        if ($backoffEnabled318) {
                            if ($doBackoff317) {
                                if ($backoff316 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff316);
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
                        $commit311 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeSamples(weakStats); }
                        catch (final fabric.worker.RetryException $e313) {
                            $commit311 = false;
                            continue $label310;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e313) {
                            $commit311 = false;
                            fabric.common.TransactionID $currentTid314 =
                              $tm315.getCurrentTid();
                            if ($e313.tid.isDescendantOf($currentTid314))
                                continue $label310;
                            if ($currentTid314.parent != null) {
                                $retry312 = false;
                                throw $e313;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e313) {
                            $commit311 = false;
                            if ($tm315.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid314 =
                              $tm315.getCurrentTid();
                            if ($e313.tid.isDescendantOf($currentTid314)) {
                                $retry312 = true;
                            }
                            else if ($currentTid314.parent != null) {
                                $retry312 = false;
                                throw $e313;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e313) {
                            $commit311 = false;
                            if ($tm315.checkForStaleObjects())
                                continue $label310;
                            $retry312 = false;
                            throw new fabric.worker.AbortException($e313);
                        }
                        finally {
                            if ($commit311) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e313) {
                                    $commit311 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e313) {
                                    $commit311 = false;
                                    fabric.common.TransactionID $currentTid314 =
                                      $tm315.getCurrentTid();
                                    if ($currentTid314 != null) {
                                        if ($e313.tid.equals($currentTid314) ||
                                              !$e313.tid.isDescendantOf(
                                                           $currentTid314)) {
                                            throw $e313;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit311 && $retry312) {
                                { result = result$var309; }
                                continue $label310;
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
                    long result$var319 = result;
                    fabric.worker.transaction.TransactionManager $tm325 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled328 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff326 = 1;
                    boolean $doBackoff327 = true;
                    boolean $retry322 = true;
                    $label320: for (boolean $commit321 = false; !$commit321; ) {
                        if ($backoffEnabled328) {
                            if ($doBackoff327) {
                                if ($backoff326 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff326);
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
                        $commit321 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeLastUpdate(weakStats); }
                        catch (final fabric.worker.RetryException $e323) {
                            $commit321 = false;
                            continue $label320;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e323) {
                            $commit321 = false;
                            fabric.common.TransactionID $currentTid324 =
                              $tm325.getCurrentTid();
                            if ($e323.tid.isDescendantOf($currentTid324))
                                continue $label320;
                            if ($currentTid324.parent != null) {
                                $retry322 = false;
                                throw $e323;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e323) {
                            $commit321 = false;
                            if ($tm325.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid324 =
                              $tm325.getCurrentTid();
                            if ($e323.tid.isDescendantOf($currentTid324)) {
                                $retry322 = true;
                            }
                            else if ($currentTid324.parent != null) {
                                $retry322 = false;
                                throw $e323;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e323) {
                            $commit321 = false;
                            if ($tm325.checkForStaleObjects())
                                continue $label320;
                            $retry322 = false;
                            throw new fabric.worker.AbortException($e323);
                        }
                        finally {
                            if ($commit321) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e323) {
                                    $commit321 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e323) {
                                    $commit321 = false;
                                    fabric.common.TransactionID $currentTid324 =
                                      $tm325.getCurrentTid();
                                    if ($currentTid324 != null) {
                                        if ($e323.tid.equals($currentTid324) ||
                                              !$e323.tid.isDescendantOf(
                                                           $currentTid324)) {
                                            throw $e323;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit321 && $retry322) {
                                { result = result$var319; }
                                continue $label320;
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
                    fabric.worker.transaction.TransactionManager $tm335 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled338 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff336 = 1;
                    boolean $doBackoff337 = true;
                    boolean $retry332 = true;
                    $label330: for (boolean $commit331 = false; !$commit331; ) {
                        if ($backoffEnabled338) {
                            if ($doBackoff337) {
                                if ($backoff336 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff336);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e333) {
                                            
                                        }
                                    }
                                }
                                if ($backoff336 < 5000) $backoff336 *= 2;
                            }
                            $doBackoff337 = $backoff336 <= 32 || !$doBackoff337;
                        }
                        $commit331 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeUpdateInterval(weakStats); }
                        catch (final fabric.worker.RetryException $e333) {
                            $commit331 = false;
                            continue $label330;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e333) {
                            $commit331 = false;
                            fabric.common.TransactionID $currentTid334 =
                              $tm335.getCurrentTid();
                            if ($e333.tid.isDescendantOf($currentTid334))
                                continue $label330;
                            if ($currentTid334.parent != null) {
                                $retry332 = false;
                                throw $e333;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e333) {
                            $commit331 = false;
                            if ($tm335.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid334 =
                              $tm335.getCurrentTid();
                            if ($e333.tid.isDescendantOf($currentTid334)) {
                                $retry332 = true;
                            }
                            else if ($currentTid334.parent != null) {
                                $retry332 = false;
                                throw $e333;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e333) {
                            $commit331 = false;
                            if ($tm335.checkForStaleObjects())
                                continue $label330;
                            $retry332 = false;
                            throw new fabric.worker.AbortException($e333);
                        }
                        finally {
                            if ($commit331) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e333) {
                                    $commit331 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e333) {
                                    $commit331 = false;
                                    fabric.common.TransactionID $currentTid334 =
                                      $tm335.getCurrentTid();
                                    if ($currentTid334 != null) {
                                        if ($e333.tid.equals($currentTid334) ||
                                              !$e333.tid.isDescendantOf(
                                                           $currentTid334)) {
                                            throw $e333;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit331 && $retry332) {
                                { result = result$var329; }
                                continue $label330;
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
                    double result$var339 = result;
                    fabric.worker.transaction.TransactionManager $tm345 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled348 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff346 = 1;
                    boolean $doBackoff347 = true;
                    boolean $retry342 = true;
                    $label340: for (boolean $commit341 = false; !$commit341; ) {
                        if ($backoffEnabled348) {
                            if ($doBackoff347) {
                                if ($backoff346 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff346);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e343) {
                                            
                                        }
                                    }
                                }
                                if ($backoff346 < 5000) $backoff346 *= 2;
                            }
                            $doBackoff347 = $backoff346 <= 32 || !$doBackoff347;
                        }
                        $commit341 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeVelocity(weakStats); }
                        catch (final fabric.worker.RetryException $e343) {
                            $commit341 = false;
                            continue $label340;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e343) {
                            $commit341 = false;
                            fabric.common.TransactionID $currentTid344 =
                              $tm345.getCurrentTid();
                            if ($e343.tid.isDescendantOf($currentTid344))
                                continue $label340;
                            if ($currentTid344.parent != null) {
                                $retry342 = false;
                                throw $e343;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e343) {
                            $commit341 = false;
                            if ($tm345.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid344 =
                              $tm345.getCurrentTid();
                            if ($e343.tid.isDescendantOf($currentTid344)) {
                                $retry342 = true;
                            }
                            else if ($currentTid344.parent != null) {
                                $retry342 = false;
                                throw $e343;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e343) {
                            $commit341 = false;
                            if ($tm345.checkForStaleObjects())
                                continue $label340;
                            $retry342 = false;
                            throw new fabric.worker.AbortException($e343);
                        }
                        finally {
                            if ($commit341) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e343) {
                                    $commit341 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e343) {
                                    $commit341 = false;
                                    fabric.common.TransactionID $currentTid344 =
                                      $tm345.getCurrentTid();
                                    if ($currentTid344 != null) {
                                        if ($e343.tid.equals($currentTid344) ||
                                              !$e343.tid.isDescendantOf(
                                                           $currentTid344)) {
                                            throw $e343;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit341 && $retry342) {
                                { result = result$var339; }
                                continue $label340;
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
                    double result$var349 = result;
                    fabric.worker.transaction.TransactionManager $tm355 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled358 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff356 = 1;
                    boolean $doBackoff357 = true;
                    boolean $retry352 = true;
                    $label350: for (boolean $commit351 = false; !$commit351; ) {
                        if ($backoffEnabled358) {
                            if ($doBackoff357) {
                                if ($backoff356 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff356);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e353) {
                                            
                                        }
                                    }
                                }
                                if ($backoff356 < 5000) $backoff356 *= 2;
                            }
                            $doBackoff357 = $backoff356 <= 32 || !$doBackoff357;
                        }
                        $commit351 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeNoise(weakStats); }
                        catch (final fabric.worker.RetryException $e353) {
                            $commit351 = false;
                            continue $label350;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e353) {
                            $commit351 = false;
                            fabric.common.TransactionID $currentTid354 =
                              $tm355.getCurrentTid();
                            if ($e353.tid.isDescendantOf($currentTid354))
                                continue $label350;
                            if ($currentTid354.parent != null) {
                                $retry352 = false;
                                throw $e353;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e353) {
                            $commit351 = false;
                            if ($tm355.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid354 =
                              $tm355.getCurrentTid();
                            if ($e353.tid.isDescendantOf($currentTid354)) {
                                $retry352 = true;
                            }
                            else if ($currentTid354.parent != null) {
                                $retry352 = false;
                                throw $e353;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e353) {
                            $commit351 = false;
                            if ($tm355.checkForStaleObjects())
                                continue $label350;
                            $retry352 = false;
                            throw new fabric.worker.AbortException($e353);
                        }
                        finally {
                            if ($commit351) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e353) {
                                    $commit351 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e353) {
                                    $commit351 = false;
                                    fabric.common.TransactionID $currentTid354 =
                                      $tm355.getCurrentTid();
                                    if ($currentTid354 != null) {
                                        if ($e353.tid.equals($currentTid354) ||
                                              !$e353.tid.isDescendantOf(
                                                           $currentTid354)) {
                                            throw $e353;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit351 && $retry352) {
                                { result = result$var349; }
                                continue $label350;
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleDirectUpdates(
          ) {
            throw new fabric.common.exceptions.InternalError(
                    "This should never happen, sampled metrics don\'t observe anything.");
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return fabric.worker.metrics.ImmutableMetricsVector.
              createVector(
                new fabric.metrics.Metric[] { (fabric.metrics.SampledMetric)
                                                this.$getProxy() });
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
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.metrics.treaties.TreatySet treaties,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, treaties, labelStore,
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
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.metrics.treaties.TreatySet treaties,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, treaties, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { -28, 29, -113, 109, 74,
    117, -52, 44, -60, 84, -75, 43, 39, -80, -104, 14, -127, 101, 4, -43, -123,
    -7, -22, 80, 3, 98, 71, -28, -58, 46, -106, -107 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1531161205000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1aDXAU1R1/ewn5IpAABvkIIYQAAuFO0KoYK4WTj0CAmBCsoRr39t4la/Z2j9134UBxtC2FccYwloDgB6MVp4opfpVhqqViRYsf41SqtbZFqGLFIuNYq7ajYv//t+8+c7fJzoRh32+z7/3f+3+//9vbvnNkmGWSmpAcUDUv2xihlneJHGhobJJNiwb9mmxZa+BpuzI8v2HXmV8GqzzE00hKFVk3dFWRtXbdYmRk481yt+zTKfO1NjfUryPFChIuk61ORjzrFsVMUh0xtI0dmsHEIv3m3znb13vPjeVP55GyNlKm6i1MZqriN3RGY6yNlIZpOEBNa2EwSINtZJROabCFmqqsqZtgoKG3kdGW2qHLLGpSq5lahtaNA0db0Qg1+Zrxh8i+AWybUYUZJrBfbrMfZarma1QtVt9ICkIq1YLWenIbyW8kw0Ka3AEDxzbGpfDxGX1L8DkML1GBTTMkKzROkt+l6kFGJmdSJCSuXQEDgLQwTFmnkVgqX5fhARlts6TJeoevhZmq3gFDhxlRWIWRCTknhUFFEVnpkjtoOyPjMsc12V0wqpirBUkYqcgcxmcCm03IsFmKtc6tuqrnFn2Z7iES8Bykiob8FwFRVQZRMw1Rk+oKtQlLZzXuksce3uYhBAZXZAy2xxy69bMf1FUdOWaPmZhlzOrAzVRh7cq+wMg3K/0z5+chG0URw1LRFdIk51ZtEj31sQh4+9jEjNjpjXceaX75+tv307MeUtJAChRDi4bBq0YpRjiiatRcSnVqyowGG0gx1YN+3t9ACuG+UdWp/XR1KGRR1kDyNf6owOB/g4pCMAWqqBDuVT1kxO8jMuvk97EIIaQQLiLB/0OEzPsQ7qsIyXuJkRW+TiNMfQEtSjeAe/vgorKpdPogbk1V8Vmm4jOjOlNhkHgEXgRg+VrkcESjwZX8Ty+wERna6WLIffkGSQLFTlaMIA3IFlhJeMyiJg2CYpmhBanZrmg9hxvImMN7uNcUo6db4K1cLxJYujIzR6TS9kYXLf7sQPtrtschrVAbI5U2j17BozeNR2CrFGPJC9nJC9mpT4p5/XsbHucuU2Dx2ErMVAozXRnRZBYyzHCMSBIX6wJOz30FLN0FGQSSROnMlhuW37StJg+cNLIhH+0GQ2szQyaZaBrgToY4aFfKtp758oldm41k8DBS2y+m+1NiTNZk6sg0FBqEnJecfla1fLD98OZaD+aTYkh1TAZnhLxRlblGWmzWx/McamNYIxmOOpA17IonpxLWaRobkk+47UdiM9p2A1RWBoM8RX6/JfLAX974+BK+ecSzaVlK2m2hrD4lgnGyMh6ro5K6X2NSCuNO7G7asfPc1nVc8TBiarYFa7H1Q+TKELKGueXY+ndPvrfvLU/SWIwURKIBTVViXJZR38E/Ca7zeGEY4gNESMZ+kQKqEzkggitPT/IG2UCDjASsW7WtetgIqiFVDmgUPeWbsmlzD37SU26bW4MntvJMUjfwBMnn4xeR21+78asqPo2k4G6U1F9ymJ3ixiRnXmia8kbkI3bH8Ul7/iA/AJ4PCcpSN1GecwjXB+EGnMd1MYe3czP6LsWmxtZWJX9eYPVP90tw30z6Ypuv7/4J/qvP2hGf8EWcY0qWiF8rp4TJvP3hLzw1BS95SGEbKedbtqyztTJkLXCDNth0Lb942EhGpPWnb6D2blGfiLXKzDhIWTYzCpKZBu5xNN6X2I5vOw4oohyVNB6uakLy823M+xp7x0SwvSAmEX5zJSeZytvp2MzkivQwUhgx1W7wLEaK1XA4ytD2fJXZjOR10Y1ZVN1kqmGIlm6xs9JtvXd+5+3ptd3MLj+m9qsAUmnsEoSvMoIvFYNVpjitwimWfPTE5uce3bzV3p5Hp2+mi/Vo+Fd//vZ17+5Tr2RJ1fmaYafbcq6DyxIqLCP2bkdaCRl+i0AtiwqX5VAh3s7CZkFcbaBS2NdYMx+6UAiIcA3EftCA4KeOnKwDDt4X+KcsnDS55WQR/rnScU2ZkNIGgVdmWbPV7ZprB16zA9Z6UuBDWdZsc7vmqpxrluKa0yD7ziSk5lGB27Os2Z59TcjaxRHTYBDONBhLTOvBaYeL6XoEbkmZlsEpB84TFqeoYOQiUTFsMMwuaiYKh+aorkM5YBcOLQmC8ZmFARctloNFrpYkb/xfgSjljgr8XQpvKQmVYABOylV18+Db9+PevcHVj8z1iKy8GDTCjMgcjXZTLWWq6RjK/U51K/lZI5liT52dNN/f9WGHHcqTM1bOHP3Yyr5Xlk5Xfu4heYlc2u+Ak05Un55BS0wK5zN9TVoerU7oqhh1sAKuOkKG/VbgplT3SDrVVGy60l2gSJBsFGhlqjm5s3mSPuzn7sqn3uSw/92KTZSRSbbv1AqnqU2rNmuTDJrpYk2G61rw0mkCy9yJhSQjBRblFiuV4Z849PHguA38uoOyVos28bjNligLA4ahUVnPJlIlXNcBP48K3OlOJCTpFdgzOJG2O/Tdjc2djJSASLY8zdywuTi/AdJRmY3Dv3HHOZJ8LfCLwXG+26HvXmx2pHK+yJFzBTjvFqi44xxJAgJ/NDjOH3Loexib+1M5X+vIuQrLnhD4qjvOkeQVgS8OjvP9Dn192DySyvmqnJzPIHgKICNOCnzBHedIckTgs7k5l5Lbh52MnnFg/yA2B4B9JndROwNli978bkMNZhOpFi6oskaGBK5wJxKSLBd4jQuRnncQiWv1WUaK0IuwiMe/D2Vjfh5cW6GAWS9wmTvmkWSpwIW5mU/ZIXgt4+dTH3OQgHv0i2lGySYDL4IugWs7nByWC5zqIEOgX6HDSWoEjh/QAPGypyp72cMrnZVyJHuxw7l500Hsd7B5neFb1XAkymjCeJnBxMuhZrh2ANdvC+zNIXjWcvNqhq9s8MVwRvFXLmbbIXBLbp2k2LUcm+N81VMOAr6PzbuMjLGXbh9IzoSB9xEy+hOBL7kzMJIcFfjcoCLMluNjBznOYnOakZFCgJS84c9lKtjbx2wUeIUrU2FzIouZcKbLBc50a6bPHcTjO/E5RirSzeQgJTfUZXD9hpCKHoFBd4ZCEkVgmwtDfeMgyXlsvmL2u2cQoVG2WGskKDOa01ZwXCaHCRk7xcaKE0NiK5zp7wLfcGkrqSC3hBKWsBLsiOPSbeUsKDfXVXC9Qci4RQJHuzMXkowS6FBHZ5pLKnMQZhQ2JeB4Qgpbggb8Sahb1nJmwnVwHQc2TgrMVT67MxnO1CvwZ25NNt5ByonYjGGkMt1kAwubiLIPCJlYZOOE99yZDUlOCHzLhdlqHATCs5o0iZGyeD6nmqGobGNOg2GMfQQy7BHYOiQGw5nWCFzi1mB1DvJ5sZnOyNiMbctBzPjOJQFUxQRe68pUnKRJ4FIXpvqegyiXY3NxssRYZaiWY4khAe3kPBurfj8UduIzvSDwGbd2WuAgHNag0vx+5UVOGXkNPAE4mUTIlBcFPu1gpP41MCd5SuDjuYVJZXOZQ99ybPxQuzPD/rU6XnGW898h8C28N6WjX42ZTcJqYA9K3Jr5AmvcSYgkUwROHJyErQ5912HTxMgI1WoBMTTawgyTm2drthhaACtDHE09KbDPXQwhyeMCfzEoX0seUaQbHcS4CZvrIedFedpebDE1DMjHZj1rzQQuYKud9qrA/e6sgCSPCXzYRTLocJBBxSbAyLBux4PGCphwCSHTlwocMSRZAGcqtXHaebdZYL2DVPhQ6oLAEFkgp3AJs8CmMeOAwJ4cwuUwC5LcJXCrC7M4vB+V8P2oFGWk0BrgWIGG+SEhF82xccbnQ2IYnOnfAk+7NcxPHeTCIkq6DU5NwjAO4nHT1AETIbDQfQJj7kyDJBsErndhmrscRMBfWKRtjJRoAx8kVsGsNxMyq9rGmR8MiXVwpvcFOtRv2a1zj4Noe7C5G45KwjrOEsZfH0lQ28x+QCBzZyAksQSGXRjoQQcpMMdL94GPRQd3dIC4lW4FV6uzcfbHQ2IknOmMwL+6NZLDy1YJdz9pX/JkPrCUiUi6k5A5Twl0meSQ5C6BbpKcw3tXCd+7Sgeg0Oke6LCAcdRDiPdSG+d8NSQmwpm+FHjGrYkOOwj2PDaHoDCIbz8O8iV2oPsJ8T0n8D53xkGSewX25hakn3FedpDhGDYvQGGgOx4PcP95kJCLr7DRdz4H5+4sgzN9K/BTt5b5o4NUx7F5NVkY5BSOmwUPb1AUzD0icJc7syDJToHbc0uRyt+7Dn1/w+YtONp0ynpQo9eoJlVEek78Fj8r+0vphvhXMKsDFqQJaraIHygHdXgARqRfQ6rvERhypwYkoQLbB6eGfzr0YaRK/4Dg6qCskcqhlij/IimhgroBVCC+ZFxL+QfLWZUQg6NJ2i/R+BXYxCzfY4qvgxX/UbrvwxV1FTm+xRzX73ttQXdgb1nRhXtb3+FfFya+/C1uJEWhqKalfi2Vcl8QMWlI5Yovtr+dinDFfAZbXvrHm4x/EI13vCD+1B73H0YK7HH41xfcGBN4E1dhRcY3oLYS+BC+4ISoiV+i931+4X8Litac4h8CgnmqT0/aHl4efb3u6JqDs2c8uXvkHTT/7S3/+1dTXmDp6Ze9u3b+H1AgYxUhLwAA";
}
