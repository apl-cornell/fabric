package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.util.List;
import fabric.metrics.util.Observer;
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
    public java.lang.String get$name();
    
    public java.lang.String set$name(java.lang.String val);
    
    /**
   * @param store
   *            the {@link Store} that holds this {@link Metric}
   * @param name
   *            the name the {@link Store} associates with this
   *            {@link SampledMetric}, so it can be easily retrieved later
   * @param init
   *        the initial value of this {@link Metric}
   */
    public fabric.metrics.SampledMetric fabric$metrics$SampledMetric$(
      java.lang.String name, double init);
    
    public boolean get$usePreset();
    
    public boolean set$usePreset(boolean val);
    
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
        public java.lang.String get$name() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$name();
        }
        
        public java.lang.String set$name(java.lang.String val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$name(val);
        }
        
        public boolean get$usePreset() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$usePreset(
                                                                    );
        }
        
        public boolean set$usePreset(boolean val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$usePreset(
                                                                    val);
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
          java.lang.String arg1, double arg2) {
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
        public java.lang.String get$name() { return this.name; }
        
        public java.lang.String set$name(java.lang.String val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.name = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private java.lang.String name;
        
        /**
   * @param store
   *            the {@link Store} that holds this {@link Metric}
   * @param name
   *            the name the {@link Store} associates with this
   *            {@link SampledMetric}, so it can be easily retrieved later
   * @param init
   *        the initial value of this {@link Metric}
   */
        public fabric.metrics.SampledMetric fabric$metrics$SampledMetric$(
          java.lang.String name, double init) {
            fabric.lang.security.Label lbl =
              fabric.lang.security.LabelUtil._Impl.noComponents();
            fabric.worker.Store s = $getStore();
            this.set$name(name);
            this.
              set$stats(
                fabric.worker.metrics.RunningMetricStats.
                    createRunningMetricStats(init, 0, 1));
            fabric.common.ConfigProperties config =
              fabric.worker.Worker.getWorker().config;
            this.set$usePreset(config.usePreset);
            if (this.get$usePreset()) {
                java.lang.String key = name + "@" + s.name();
                this.set$presetR((double)
                                   (config.rates.containsKey(key)
                                      ? ((java.lang.Double)
                                           config.rates.get(key)).doubleValue()
                                      : 0.0));
                this.set$presetB((double)
                                   (config.bounds.containsKey(key)
                                      ? ((java.lang.Double)
                                           config.bounds.get(key)).doubleValue()
                                      : 1.0));
                this.set$presetV(
                       (double)
                         (config.velocities.containsKey(key)
                            ? ((java.lang.Double)
                                 config.velocities.get(key)).doubleValue()
                            : 0.0));
                this.set$presetN((double)
                                   (config.noises.containsKey(key)
                                      ? ((java.lang.Double)
                                           config.noises.get(key)).doubleValue()
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
        
        public boolean get$usePreset() { return this.usePreset; }
        
        public boolean set$usePreset(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.usePreset = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public boolean usePreset;
        
        public boolean getUsePreset() { return this.get$usePreset(); }
        
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
                  tmp.get$stats().preload(tmp.get$name());
                if (!fabric.lang.Object._Proxy.idEquals(preloaded,
                                                        tmp.get$stats()))
                    tmp.set$stats(preloaded);
                result = tmp.get$stats().getValue();
            }
            else {
                {
                    double result$var229 = result;
                    fabric.worker.transaction.TransactionManager $tm235 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled238 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff236 = 1;
                    boolean $doBackoff237 = true;
                    boolean $retry232 = true;
                    $label230: for (boolean $commit231 = false; !$commit231; ) {
                        if ($backoffEnabled238) {
                            if ($doBackoff237) {
                                if ($backoff236 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff236);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e233) {
                                            
                                        }
                                    }
                                }
                                if ($backoff236 < 5000) $backoff236 *= 2;
                            }
                            $doBackoff237 = $backoff236 <= 32 || !$doBackoff237;
                        }
                        $commit231 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.worker.metrics.RunningMetricStats preloaded =
                              tmp.get$stats().preload(tmp.get$name());
                            if (!fabric.lang.Object._Proxy.idEquals(
                                                             preloaded,
                                                             tmp.get$stats()))
                                tmp.set$stats(preloaded);
                            result = tmp.get$stats().getValue();
                        }
                        catch (final fabric.worker.RetryException $e233) {
                            $commit231 = false;
                            continue $label230;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e233) {
                            $commit231 = false;
                            fabric.common.TransactionID $currentTid234 =
                              $tm235.getCurrentTid();
                            if ($e233.tid.isDescendantOf($currentTid234))
                                continue $label230;
                            if ($currentTid234.parent != null) {
                                $retry232 = false;
                                throw $e233;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e233) {
                            $commit231 = false;
                            if ($tm235.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid234 =
                              $tm235.getCurrentTid();
                            if ($e233.tid.isDescendantOf($currentTid234)) {
                                $retry232 = true;
                            }
                            else if ($currentTid234.parent != null) {
                                $retry232 = false;
                                throw $e233;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e233) {
                            $commit231 = false;
                            if ($tm235.checkForStaleObjects())
                                continue $label230;
                            $retry232 = false;
                            throw new fabric.worker.AbortException($e233);
                        }
                        finally {
                            if ($commit231) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e233) {
                                    $commit231 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e233) {
                                    $commit231 = false;
                                    fabric.common.TransactionID $currentTid234 =
                                      $tm235.getCurrentTid();
                                    if ($currentTid234 != null) {
                                        if ($e233.tid.equals($currentTid234) ||
                                              !$e233.tid.isDescendantOf(
                                                           $currentTid234)) {
                                            throw $e233;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit231 && $retry232) {
                                { result = result$var229; }
                                continue $label230;
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
                if (tmp.get$usePreset()) {
                    return 0;
                }
                else {
                    fabric.worker.metrics.RunningMetricStats preloaded =
                      tmp.get$stats().preload(tmp.get$name());
                    if (!fabric.lang.Object._Proxy.idEquals(preloaded,
                                                            tmp.get$stats()))
                        tmp.set$stats(preloaded);
                    return tmp.get$stats().getSamples();
                }
            }
            else {
                long rtn = 0;
                {
                    long rtn$var239 = rtn;
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
                            if (tmp.get$usePreset()) {
                                rtn = 0;
                            }
                            else {
                                fabric.worker.metrics.RunningMetricStats
                                  preloaded =
                                  tmp.get$stats().preload(tmp.get$name());
                                if (!fabric.lang.Object._Proxy.idEquals(
                                                                 preloaded,
                                                                 tmp.get$stats(
                                                                       )))
                                    tmp.set$stats(preloaded);
                                rtn = tmp.get$stats().getSamples();
                            }
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
                                { rtn = rtn$var239; }
                                continue $label240;
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
                if (tmp.get$usePreset()) {
                    return 0;
                }
                else {
                    fabric.worker.metrics.RunningMetricStats preloaded =
                      tmp.get$stats().preload(tmp.get$name());
                    if (!fabric.lang.Object._Proxy.idEquals(preloaded,
                                                            tmp.get$stats()))
                        tmp.set$stats(preloaded);
                    return tmp.get$stats().getLastUpdate();
                }
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
                            if (tmp.get$usePreset()) {
                                rtn = 0;
                            }
                            else {
                                fabric.worker.metrics.RunningMetricStats
                                  preloaded =
                                  tmp.get$stats().preload(tmp.get$name());
                                if (!fabric.lang.Object._Proxy.idEquals(
                                                                 preloaded,
                                                                 tmp.get$stats(
                                                                       )))
                                    tmp.set$stats(preloaded);
                                rtn = tmp.get$stats().getLastUpdate();
                            }
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
                if (tmp.get$usePreset()) {
                    return 0;
                }
                else {
                    fabric.worker.metrics.RunningMetricStats preloaded =
                      tmp.get$stats().preload(tmp.get$name());
                    if (!fabric.lang.Object._Proxy.idEquals(preloaded,
                                                            tmp.get$stats()))
                        tmp.set$stats(preloaded);
                    return tmp.get$stats().getIntervalEstimate();
                }
            }
            else {
                double rtn = 0;
                {
                    double rtn$var259 = rtn;
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
                            if (tmp.get$usePreset()) {
                                rtn = 0;
                            }
                            else {
                                fabric.worker.metrics.RunningMetricStats
                                  preloaded =
                                  tmp.get$stats().preload(tmp.get$name());
                                if (!fabric.lang.Object._Proxy.idEquals(
                                                                 preloaded,
                                                                 tmp.get$stats(
                                                                       )))
                                    tmp.set$stats(preloaded);
                                rtn = tmp.get$stats().getIntervalEstimate();
                            }
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
                if (tmp.get$usePreset()) {
                    return tmp.get$presetV();
                }
                else {
                    fabric.worker.metrics.RunningMetricStats preloaded =
                      tmp.get$stats().preload(tmp.get$name());
                    if (!fabric.lang.Object._Proxy.idEquals(preloaded,
                                                            tmp.get$stats()))
                        tmp.set$stats(preloaded);
                    return tmp.get$stats().getVelocityEstimate();
                }
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
                            if (tmp.get$usePreset()) {
                                rtn = tmp.get$presetV();
                            }
                            else {
                                fabric.worker.metrics.RunningMetricStats
                                  preloaded =
                                  tmp.get$stats().preload(tmp.get$name());
                                if (!fabric.lang.Object._Proxy.idEquals(
                                                                 preloaded,
                                                                 tmp.get$stats(
                                                                       )))
                                    tmp.set$stats(preloaded);
                                rtn = tmp.get$stats().getVelocityEstimate();
                            }
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
                if (tmp.get$usePreset()) {
                    return tmp.get$presetN();
                }
                else {
                    fabric.worker.metrics.RunningMetricStats preloaded =
                      tmp.get$stats().preload(tmp.get$name());
                    if (!fabric.lang.Object._Proxy.idEquals(preloaded,
                                                            tmp.get$stats()))
                        tmp.set$stats(preloaded);
                    return tmp.get$stats().getNoiseEstimate();
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
                            if (tmp.get$usePreset()) {
                                rtn = tmp.get$presetN();
                            }
                            else {
                                fabric.worker.metrics.RunningMetricStats
                                  preloaded =
                                  tmp.get$stats().preload(tmp.get$name());
                                if (!fabric.lang.Object._Proxy.idEquals(
                                                                 preloaded,
                                                                 tmp.get$stats(
                                                                       )))
                                    tmp.set$stats(preloaded);
                                rtn = tmp.get$stats().getNoiseEstimate();
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
        
        public java.lang.String toString() {
            return "SampledMetric(" + this.get$name() + ")@" +
            getStore().name();
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
              this.get$stats().preload(this.get$name());
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
                    double result$var289 = result;
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
                        try { result = tmp.computeValue(weakStats); }
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
                                { result = result$var289; }
                                continue $label290;
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
                    long result$var299 = result;
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
                        try { result = tmp.computeSamples(weakStats); }
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
                        try { result = tmp.computeLastUpdate(weakStats); }
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
                    double result$var319 = result;
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
                        try { result = tmp.computeUpdateInterval(weakStats); }
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
        
        public double velocity(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_velocity((fabric.metrics.SampledMetric) this.$getProxy(),
                              weakStats);
        }
        
        private static double static_velocity(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (weakStats.containsKey(tmp)) return weakStats.getVelocity(tmp);
            double result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                result = tmp.computeVelocity(weakStats);
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
                        try { result = tmp.computeVelocity(weakStats); }
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
        
        public double noise(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.SampledMetric._Impl.
              static_noise((fabric.metrics.SampledMetric) this.$getProxy(),
                           weakStats);
        }
        
        private static double static_noise(
          fabric.metrics.SampledMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (weakStats.containsKey(tmp)) return weakStats.getNoise(tmp);
            double result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                result = tmp.computeNoise(weakStats);
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
                        try { result = tmp.computeNoise(weakStats); }
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
            $writeInline(out, this.name);
            out.writeBoolean(this.usePreset);
            out.writeDouble(this.presetR);
            out.writeDouble(this.presetB);
            out.writeDouble(this.presetV);
            out.writeDouble(this.presetN);
            $writeInline(out, this.stats);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.name = (java.lang.String) in.readObject();
            this.usePreset = in.readBoolean();
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
            this.name = src.name;
            this.usePreset = src.usePreset;
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
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { 89, -68, 53, 79, -122,
    14, -116, -40, -79, -64, 28, 126, -105, -67, -2, -117, 85, -78, -59, 93, 55,
    -119, 119, 98, 30, -10, 70, 90, 38, 26, -23, -10 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527002351000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1aC3AV1Rn+7yVvAgkgD2MIr0DllSuKIqIIyYBELg8TkpYgpJu9J2HN3t3r7rnhgmJ9jOCraBVBWkXHwbEqFR/VapWKjqCMD+zD+mitTK1WisxUrLQztdr/P3vuM3c32Zlksv+3d8/5z/kf5//Pf+7dvSeg0LZgYqfSoel1fGOM2XWLlY7G8ErFslmkQVdsexU+bVcHFzTu+PzhSE0QgmEoVxXDNDRV0dsNm8PQ8BVKjxIyGA+1NDXOWwOlKjEuUez1HIJr6hMWjI+Z+sYu3eRykl7j3z09tH3nusqnBkFFG1RoRjNXuKY2mAZnCd4G5VEW7WCWvTASYZE2GGYwFmlmlqbo2ibsaBptMNzWugyFxy1mNzHb1Huo43A7HmOWmDP5kMQ3UWwrrnLTQvErHfHjXNNDYc3m88JQ1KkxPWJfCddAQRgKO3WlCzuOCie1CIkRQ4vpOXYv01BMq1NRWZKloFszIhzG5XKkNK5dih2QtTjK+HozNVWBoeADGO6IpCtGV6iZW5rRhV0LzTjOwqHKdVDsVBJT1G6li7VzGJPbb6XThL1KhVmIhcPI3G5iJPRZVY7PMrx1YvmF264ylhhBCKDMEabqJH8JMtXkMDWxTmYxQ2UOY/m08A5l1P6bggDYeWROZ6fPr67+csGMmgOvO33OyNNnRccVTOXt6p6Oob+tbpg6dxCJURIzbY2WQpbmwqsrZcu8RAxX+6jUiNRYl2w80HRo9bWPsuNBKGuEItXU41FcVcNUMxrTdGZdwgxmKZxFGqGUGZEG0d4IxXgf1gzmPF3R2Wkz3ggFunhUZIrPaKJOHIJMVIz3mtFpJu9jCl8v7hMxACjGCwL4/ybA7J/gfTXAoLUclobWm1EW6tDjbAMu7xBeTLHU9SGMW0tTQ7alhqy4wTXsJB/hKkKwQ81KNKazyDLxsQ7FiA3scAmSvnJDIICGHaeaEdah2OgluWLqV+oYFEtMPcKsdlXftr8RRuzfJVZNKa10G1ersEsAPV2dmyMyebfH6xd9+Xj7G86KI15pNg7Vjox1Usa6LBlRrHKKpTrMTnWYnfYGEnUNuxsfE0umyBaxlRqpHEe6IKYrvNO0ogkIBIRapwl+sVbQ092YQTBJlE9tXnvpD2+aOAgXaWxDAfkNu9bmhkw60TTinYJx0K5WbP381L4dm8108HCo7RXTvTkpJifm2sgyVRbBnJceftp45Zn2/Ztrg5RPSjHVcQUXI+aNmtw5smJzXjLPkTUKwzCYbKDo1JRMTmV8vWVuSD8Rvh9KZLizDMhYOQKKFHlRc+y+998+do7YPJLZtCIj7TYzPi8jgmmwChGrw9K2X2Uxhv0+umflXXef2LpGGB57TMo3YS3RBoxcBUPWtG58/coPPv7Lnj8E087iUBSLd+iamhC6DPsO/wJ4fUsXhSE9IMRk3CBTwPhUDojRzFPSsmE20DEjoeh2bYsRNSNap6Z06IxWyjcVk2c988W2SsfdOj5xjGfBjL4HSD8/vR6ufWPdv2vEMAGVdqO0/dLdnBQ3Ij3yQstSNpIciet+N3bXa8p9uPIxQdnaJiZyDgh7gHDg2cIWMwWdldM2m8hEx1rV4nmx3TvdL6Z9M70W20J7761qmH/cifjUWqQxJuSJ+FYlI0zOfjT6dXBi0cEgFLdBpdiyFYO3Kpi1cBm04aZrN8iHYRiS1Z69gTq7xbxUrFXnxkHGtLlRkM40eE+96b7MWfjOwkFDVIKTqqEG0/X9Eu+i1hExoqclAiBuLhAskwSdQmSqMGSQQ3HM0npwZXEo1aLROCffi1mmc0cGwTMSixXhWdKrzikMRMPpubnLCUei56XErCAxJ+D1fYDyLRJ5HjEXuYhJt9OIXJwUrTRus5W0ffA8a2GlpUUxnHvk1s9u2n7Ld3Xbtjtx4NRHk3qVKJk8To0k5hoiJkzgLBO8ZhEci/++b/MLP9+81akfhmfv9ouMePQXf/zfm3X3HD2cZy8p7jBNnSmGq/3QtbAW7XZS4id57HdZv+2HbifjNYmul0odCZZhfoqYmKCYpyQqwJDVEi/NI8lqv5LU08dWzzk1nOuAxCfzzLnO75ytfc9pAgz9nsQxeeZU/c653HXOcppzMlCShMnHJL6TZ871+efEnaU0ZpkcUw6LJFLDBmnYwXK4IxIPZQzL8SSGZx47Gehnyqpmg2l1MytV3DTFDQOj3ilumlMM+RNAwkVEYZa0bOKvSJabl0tszZAtI+kDxeBYt5OBiL8912/fHVnx0Kyg3DmWo0W4GZupsx6mZwxVS9Hc6+S5TJyH0tvA0eNj5zZ0f9rlRPO4nJlzez+ybO/hS6aodwZhUCrf9zqEZTPNy87yZRbDM6SxKivXj0/ZqpRssByvqQCFZzlYcDxzeaQX1SQiPHsJlEiWf0j8W66Z07tvML2GF4rlKoa+wWOPvpHINRzGOmunVi6a2qyKuDYt4FXZao3DC7NJ+bMSH/CnFrHcL3GXu1qZAt/m0baNyFZc112MtyS3GXoWzic7bb/tmJHGSSz3JzuxDJZY2D/Z7/Zo20nkDg5lKLsjeJPwoJvkDKfdItH2JzmxWBL1/km+26NN+HxXpuT1npJ347QnJH7oT3Ji+UDi7/sn+cMebY8QeTBT8lZPya/EvWSVxIX+JCeWBRIv6J/kT3i0PUXksUzJl7tKTrvg1bgztko835/kxDJH4ix3yQPpfcLJOs97iP8CkV+i+FzpZk6qyVfPFPSYWiSfSrV43YDyfCTxFX8qEcvLEl/wodJBD5VeI3KAQwmtIjpR0Of9+YQ/G6/b8QjwucSX/AlPLL+R+Jy78BlbQWte2+qm0SWme9tDKxFkh7MclU8vUQGdg9dOgGEvS/yph17dvaocwbJL4h19OiVZ89Tkr3lEmbNMieWvdIQ0H3qofZTIu5y+9o3G4pylHJobYKIWasLrZ4AHSImDXRTPW2vO5/SdEn1znVP5VcrRyhwc9k2/fF1J5E9i1mMeCory468cRjhTt/elZ8rBewFOYxLn+3MwsVwkcXa/os7R46SHHv8icoLDUKlARi454uaqJ3D+kxKfdFEhv6uIfJbHTTTSExIf9OumbzzU+5bIKQ4js93koaVw1Hl4HQYYvUfiJn+OIpaNEmP9d1Sg0F2TQDER4M6X46hCWLF5SyyicObqqxa83gIYM8fB0V8NiK9opJMS+1dJp30VqPDQcBiRUg5jsn3lrahw14V4fQZQ9bXEg/7cRSyvSuzfbuYoM8ZDmSoiI3DhSS0cDRrpN6seRXfNhGvwwmPKGXdKXDIgLqORLpE416/LJnpoSeeaQDWH6myX9a1sMsoCgwBqfiCxzpfbBMtMibU+3DbdQ6GZRKZwqEjmc6abqsY3ujoMYyxQjAIck/jQQDhMjLRH4g6/DjvXQ785REIcRuVsWx5qJneuwHiACQck7nJR08VVxHKPxNt9uGq+hyoLiMxNlxjLTc32LDECWPROXCqxbED8RCOVOjjhv379tMRDOao1A/W9ygtXHUVdXIWSYH0weZCDtSc9nNS7LhYsX0o87q5MppiXebQ1EwljPc/N5tRPfgvzyY2rIlCPcm+SqPqTm1g6JF7eP7nbPNpoiEALhyGa3YxS66yZm5Yweu6XICIyLsaZm/GMON7BKSf8RQaxfCHxE3fhMw8jRI4IUVUPNUjiwDrMZHGRjBfZXIsiir55T1VTUQo049RyB8/8wp8XiOW4xE99hLjuoQP9JBDo4lDY43l8oJjuRMlvlrhiQGKbRloucYHf2O7x0EoQ+uVbxrarcim3cIBp/5T4jotyLm4hliMSD/twy488FLiOyCYOxXYfhwVyTAJguinx3AFxDI00W+IUv4652UOvW4ncgGch6RgP9YRrZqAQWxA+kvhrf64hluclPu3DNXd6qLCdyI85lOl9Hw9ocd8CMLND4uQB8Q6NVCtxlF/v3Ouh2m4iO/AAJL3jrWHyi6IAlh11H0t81p+DiOUZift8OOghDy0eJvIArrF4/w4Eq3DkewFChsTpA+IkGmmaxGq/TtrnoR59HRB4JH3e7lvLVCQ9CnDWVxJ9JjliOSLRT5J7zkMTCubA01i+9PR1BKA4ehxgVo/EuS7C+3MRjXS+xGl+XfSKh2KvEnkRC4Pk9uOhX2oHehGD6TOJh1z0c3EOsRyU+JK7Ir2c84aHDm8ROYSFgeFZ9NP+cwAPL6rEqQPiGRrpTIlVfj3zrodW7xE5ki4M0solsBzN+kGRXjg6I8+rf/JFVLXhVbbn06UzRrq89jem16vBku/x3RUlo3e3vCdeZEu9ZFoahpLOuK5nvpiTcV8Us1inJgxa6rymExP6/BnTXPZ7gly8e0t3ZIDAh06/jzkUOf3o01FhTWHYKmGmkZhOcl43dIwguogJq+IWvfS896vR/ykqWXVUvHOGVh2/+sVzV2wZetv7Tx6ovmbn/m9vbXnq4No5N2/oqDm1uG1K1bFT/weQOjTijC0AAA==";
}
