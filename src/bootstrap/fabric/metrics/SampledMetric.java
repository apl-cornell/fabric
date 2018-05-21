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
                    double result$var201 = result;
                    fabric.worker.transaction.TransactionManager $tm207 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled210 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff208 = 1;
                    boolean $doBackoff209 = true;
                    boolean $retry204 = true;
                    $label202: for (boolean $commit203 = false; !$commit203; ) {
                        if ($backoffEnabled210) {
                            if ($doBackoff209) {
                                if ($backoff208 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff208);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e205) {
                                            
                                        }
                                    }
                                }
                                if ($backoff208 < 5000) $backoff208 *= 2;
                            }
                            $doBackoff209 = $backoff208 <= 32 || !$doBackoff209;
                        }
                        $commit203 = true;
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
                        catch (final fabric.worker.RetryException $e205) {
                            $commit203 = false;
                            continue $label202;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e205) {
                            $commit203 = false;
                            fabric.common.TransactionID $currentTid206 =
                              $tm207.getCurrentTid();
                            if ($e205.tid.isDescendantOf($currentTid206))
                                continue $label202;
                            if ($currentTid206.parent != null) {
                                $retry204 = false;
                                throw $e205;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e205) {
                            $commit203 = false;
                            if ($tm207.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid206 =
                              $tm207.getCurrentTid();
                            if ($e205.tid.isDescendantOf($currentTid206)) {
                                $retry204 = true;
                            }
                            else if ($currentTid206.parent != null) {
                                $retry204 = false;
                                throw $e205;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e205) {
                            $commit203 = false;
                            if ($tm207.checkForStaleObjects())
                                continue $label202;
                            $retry204 = false;
                            throw new fabric.worker.AbortException($e205);
                        }
                        finally {
                            if ($commit203) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e205) {
                                    $commit203 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e205) {
                                    $commit203 = false;
                                    fabric.common.TransactionID $currentTid206 =
                                      $tm207.getCurrentTid();
                                    if ($currentTid206 != null) {
                                        if ($e205.tid.equals($currentTid206) ||
                                              !$e205.tid.isDescendantOf(
                                                           $currentTid206)) {
                                            throw $e205;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit203 && $retry204) {
                                { result = result$var201; }
                                continue $label202;
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
                    long rtn$var211 = rtn;
                    fabric.worker.transaction.TransactionManager $tm217 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled220 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff218 = 1;
                    boolean $doBackoff219 = true;
                    boolean $retry214 = true;
                    $label212: for (boolean $commit213 = false; !$commit213; ) {
                        if ($backoffEnabled220) {
                            if ($doBackoff219) {
                                if ($backoff218 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff218);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e215) {
                                            
                                        }
                                    }
                                }
                                if ($backoff218 < 5000) $backoff218 *= 2;
                            }
                            $doBackoff219 = $backoff218 <= 32 || !$doBackoff219;
                        }
                        $commit213 = true;
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
                        catch (final fabric.worker.RetryException $e215) {
                            $commit213 = false;
                            continue $label212;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e215) {
                            $commit213 = false;
                            fabric.common.TransactionID $currentTid216 =
                              $tm217.getCurrentTid();
                            if ($e215.tid.isDescendantOf($currentTid216))
                                continue $label212;
                            if ($currentTid216.parent != null) {
                                $retry214 = false;
                                throw $e215;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e215) {
                            $commit213 = false;
                            if ($tm217.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid216 =
                              $tm217.getCurrentTid();
                            if ($e215.tid.isDescendantOf($currentTid216)) {
                                $retry214 = true;
                            }
                            else if ($currentTid216.parent != null) {
                                $retry214 = false;
                                throw $e215;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e215) {
                            $commit213 = false;
                            if ($tm217.checkForStaleObjects())
                                continue $label212;
                            $retry214 = false;
                            throw new fabric.worker.AbortException($e215);
                        }
                        finally {
                            if ($commit213) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e215) {
                                    $commit213 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e215) {
                                    $commit213 = false;
                                    fabric.common.TransactionID $currentTid216 =
                                      $tm217.getCurrentTid();
                                    if ($currentTid216 != null) {
                                        if ($e215.tid.equals($currentTid216) ||
                                              !$e215.tid.isDescendantOf(
                                                           $currentTid216)) {
                                            throw $e215;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit213 && $retry214) {
                                { rtn = rtn$var211; }
                                continue $label212;
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
                    long rtn$var221 = rtn;
                    fabric.worker.transaction.TransactionManager $tm227 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled230 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff228 = 1;
                    boolean $doBackoff229 = true;
                    boolean $retry224 = true;
                    $label222: for (boolean $commit223 = false; !$commit223; ) {
                        if ($backoffEnabled230) {
                            if ($doBackoff229) {
                                if ($backoff228 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff228);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e225) {
                                            
                                        }
                                    }
                                }
                                if ($backoff228 < 5000) $backoff228 *= 2;
                            }
                            $doBackoff229 = $backoff228 <= 32 || !$doBackoff229;
                        }
                        $commit223 = true;
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
                        catch (final fabric.worker.RetryException $e225) {
                            $commit223 = false;
                            continue $label222;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e225) {
                            $commit223 = false;
                            fabric.common.TransactionID $currentTid226 =
                              $tm227.getCurrentTid();
                            if ($e225.tid.isDescendantOf($currentTid226))
                                continue $label222;
                            if ($currentTid226.parent != null) {
                                $retry224 = false;
                                throw $e225;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e225) {
                            $commit223 = false;
                            if ($tm227.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid226 =
                              $tm227.getCurrentTid();
                            if ($e225.tid.isDescendantOf($currentTid226)) {
                                $retry224 = true;
                            }
                            else if ($currentTid226.parent != null) {
                                $retry224 = false;
                                throw $e225;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e225) {
                            $commit223 = false;
                            if ($tm227.checkForStaleObjects())
                                continue $label222;
                            $retry224 = false;
                            throw new fabric.worker.AbortException($e225);
                        }
                        finally {
                            if ($commit223) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e225) {
                                    $commit223 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e225) {
                                    $commit223 = false;
                                    fabric.common.TransactionID $currentTid226 =
                                      $tm227.getCurrentTid();
                                    if ($currentTid226 != null) {
                                        if ($e225.tid.equals($currentTid226) ||
                                              !$e225.tid.isDescendantOf(
                                                           $currentTid226)) {
                                            throw $e225;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit223 && $retry224) {
                                { rtn = rtn$var221; }
                                continue $label222;
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
                    double rtn$var231 = rtn;
                    fabric.worker.transaction.TransactionManager $tm237 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled240 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff238 = 1;
                    boolean $doBackoff239 = true;
                    boolean $retry234 = true;
                    $label232: for (boolean $commit233 = false; !$commit233; ) {
                        if ($backoffEnabled240) {
                            if ($doBackoff239) {
                                if ($backoff238 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff238);
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
                        $commit233 = true;
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
                        catch (final fabric.worker.RetryException $e235) {
                            $commit233 = false;
                            continue $label232;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e235) {
                            $commit233 = false;
                            fabric.common.TransactionID $currentTid236 =
                              $tm237.getCurrentTid();
                            if ($e235.tid.isDescendantOf($currentTid236))
                                continue $label232;
                            if ($currentTid236.parent != null) {
                                $retry234 = false;
                                throw $e235;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e235) {
                            $commit233 = false;
                            if ($tm237.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid236 =
                              $tm237.getCurrentTid();
                            if ($e235.tid.isDescendantOf($currentTid236)) {
                                $retry234 = true;
                            }
                            else if ($currentTid236.parent != null) {
                                $retry234 = false;
                                throw $e235;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e235) {
                            $commit233 = false;
                            if ($tm237.checkForStaleObjects())
                                continue $label232;
                            $retry234 = false;
                            throw new fabric.worker.AbortException($e235);
                        }
                        finally {
                            if ($commit233) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e235) {
                                    $commit233 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e235) {
                                    $commit233 = false;
                                    fabric.common.TransactionID $currentTid236 =
                                      $tm237.getCurrentTid();
                                    if ($currentTid236 != null) {
                                        if ($e235.tid.equals($currentTid236) ||
                                              !$e235.tid.isDescendantOf(
                                                           $currentTid236)) {
                                            throw $e235;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit233 && $retry234) {
                                { rtn = rtn$var231; }
                                continue $label232;
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
                    double rtn$var241 = rtn;
                    fabric.worker.transaction.TransactionManager $tm247 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled250 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff248 = 1;
                    boolean $doBackoff249 = true;
                    boolean $retry244 = true;
                    $label242: for (boolean $commit243 = false; !$commit243; ) {
                        if ($backoffEnabled250) {
                            if ($doBackoff249) {
                                if ($backoff248 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff248);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e245) {
                                            
                                        }
                                    }
                                }
                                if ($backoff248 < 5000) $backoff248 *= 2;
                            }
                            $doBackoff249 = $backoff248 <= 32 || !$doBackoff249;
                        }
                        $commit243 = true;
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
                        catch (final fabric.worker.RetryException $e245) {
                            $commit243 = false;
                            continue $label242;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e245) {
                            $commit243 = false;
                            fabric.common.TransactionID $currentTid246 =
                              $tm247.getCurrentTid();
                            if ($e245.tid.isDescendantOf($currentTid246))
                                continue $label242;
                            if ($currentTid246.parent != null) {
                                $retry244 = false;
                                throw $e245;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e245) {
                            $commit243 = false;
                            if ($tm247.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid246 =
                              $tm247.getCurrentTid();
                            if ($e245.tid.isDescendantOf($currentTid246)) {
                                $retry244 = true;
                            }
                            else if ($currentTid246.parent != null) {
                                $retry244 = false;
                                throw $e245;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e245) {
                            $commit243 = false;
                            if ($tm247.checkForStaleObjects())
                                continue $label242;
                            $retry244 = false;
                            throw new fabric.worker.AbortException($e245);
                        }
                        finally {
                            if ($commit243) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e245) {
                                    $commit243 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e245) {
                                    $commit243 = false;
                                    fabric.common.TransactionID $currentTid246 =
                                      $tm247.getCurrentTid();
                                    if ($currentTid246 != null) {
                                        if ($e245.tid.equals($currentTid246) ||
                                              !$e245.tid.isDescendantOf(
                                                           $currentTid246)) {
                                            throw $e245;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit243 && $retry244) {
                                { rtn = rtn$var241; }
                                continue $label242;
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
                    double rtn$var251 = rtn;
                    fabric.worker.transaction.TransactionManager $tm257 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled260 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff258 = 1;
                    boolean $doBackoff259 = true;
                    boolean $retry254 = true;
                    $label252: for (boolean $commit253 = false; !$commit253; ) {
                        if ($backoffEnabled260) {
                            if ($doBackoff259) {
                                if ($backoff258 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff258);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e255) {
                                            
                                        }
                                    }
                                }
                                if ($backoff258 < 5000) $backoff258 *= 2;
                            }
                            $doBackoff259 = $backoff258 <= 32 || !$doBackoff259;
                        }
                        $commit253 = true;
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
                        catch (final fabric.worker.RetryException $e255) {
                            $commit253 = false;
                            continue $label252;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e255) {
                            $commit253 = false;
                            fabric.common.TransactionID $currentTid256 =
                              $tm257.getCurrentTid();
                            if ($e255.tid.isDescendantOf($currentTid256))
                                continue $label252;
                            if ($currentTid256.parent != null) {
                                $retry254 = false;
                                throw $e255;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e255) {
                            $commit253 = false;
                            if ($tm257.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid256 =
                              $tm257.getCurrentTid();
                            if ($e255.tid.isDescendantOf($currentTid256)) {
                                $retry254 = true;
                            }
                            else if ($currentTid256.parent != null) {
                                $retry254 = false;
                                throw $e255;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e255) {
                            $commit253 = false;
                            if ($tm257.checkForStaleObjects())
                                continue $label252;
                            $retry254 = false;
                            throw new fabric.worker.AbortException($e255);
                        }
                        finally {
                            if ($commit253) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e255) {
                                    $commit253 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e255) {
                                    $commit253 = false;
                                    fabric.common.TransactionID $currentTid256 =
                                      $tm257.getCurrentTid();
                                    if ($currentTid256 != null) {
                                        if ($e255.tid.equals($currentTid256) ||
                                              !$e255.tid.isDescendantOf(
                                                           $currentTid256)) {
                                            throw $e255;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit253 && $retry254) {
                                { rtn = rtn$var251; }
                                continue $label252;
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
                    double result$var261 = result;
                    fabric.worker.transaction.TransactionManager $tm267 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled270 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff268 = 1;
                    boolean $doBackoff269 = true;
                    boolean $retry264 = true;
                    $label262: for (boolean $commit263 = false; !$commit263; ) {
                        if ($backoffEnabled270) {
                            if ($doBackoff269) {
                                if ($backoff268 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff268);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e265) {
                                            
                                        }
                                    }
                                }
                                if ($backoff268 < 5000) $backoff268 *= 2;
                            }
                            $doBackoff269 = $backoff268 <= 32 || !$doBackoff269;
                        }
                        $commit263 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeValue(weakStats); }
                        catch (final fabric.worker.RetryException $e265) {
                            $commit263 = false;
                            continue $label262;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e265) {
                            $commit263 = false;
                            fabric.common.TransactionID $currentTid266 =
                              $tm267.getCurrentTid();
                            if ($e265.tid.isDescendantOf($currentTid266))
                                continue $label262;
                            if ($currentTid266.parent != null) {
                                $retry264 = false;
                                throw $e265;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e265) {
                            $commit263 = false;
                            if ($tm267.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid266 =
                              $tm267.getCurrentTid();
                            if ($e265.tid.isDescendantOf($currentTid266)) {
                                $retry264 = true;
                            }
                            else if ($currentTid266.parent != null) {
                                $retry264 = false;
                                throw $e265;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e265) {
                            $commit263 = false;
                            if ($tm267.checkForStaleObjects())
                                continue $label262;
                            $retry264 = false;
                            throw new fabric.worker.AbortException($e265);
                        }
                        finally {
                            if ($commit263) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e265) {
                                    $commit263 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e265) {
                                    $commit263 = false;
                                    fabric.common.TransactionID $currentTid266 =
                                      $tm267.getCurrentTid();
                                    if ($currentTid266 != null) {
                                        if ($e265.tid.equals($currentTid266) ||
                                              !$e265.tid.isDescendantOf(
                                                           $currentTid266)) {
                                            throw $e265;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit263 && $retry264) {
                                { result = result$var261; }
                                continue $label262;
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
                    long result$var271 = result;
                    fabric.worker.transaction.TransactionManager $tm277 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled280 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff278 = 1;
                    boolean $doBackoff279 = true;
                    boolean $retry274 = true;
                    $label272: for (boolean $commit273 = false; !$commit273; ) {
                        if ($backoffEnabled280) {
                            if ($doBackoff279) {
                                if ($backoff278 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff278);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e275) {
                                            
                                        }
                                    }
                                }
                                if ($backoff278 < 5000) $backoff278 *= 2;
                            }
                            $doBackoff279 = $backoff278 <= 32 || !$doBackoff279;
                        }
                        $commit273 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeSamples(weakStats); }
                        catch (final fabric.worker.RetryException $e275) {
                            $commit273 = false;
                            continue $label272;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e275) {
                            $commit273 = false;
                            fabric.common.TransactionID $currentTid276 =
                              $tm277.getCurrentTid();
                            if ($e275.tid.isDescendantOf($currentTid276))
                                continue $label272;
                            if ($currentTid276.parent != null) {
                                $retry274 = false;
                                throw $e275;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e275) {
                            $commit273 = false;
                            if ($tm277.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid276 =
                              $tm277.getCurrentTid();
                            if ($e275.tid.isDescendantOf($currentTid276)) {
                                $retry274 = true;
                            }
                            else if ($currentTid276.parent != null) {
                                $retry274 = false;
                                throw $e275;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e275) {
                            $commit273 = false;
                            if ($tm277.checkForStaleObjects())
                                continue $label272;
                            $retry274 = false;
                            throw new fabric.worker.AbortException($e275);
                        }
                        finally {
                            if ($commit273) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e275) {
                                    $commit273 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e275) {
                                    $commit273 = false;
                                    fabric.common.TransactionID $currentTid276 =
                                      $tm277.getCurrentTid();
                                    if ($currentTid276 != null) {
                                        if ($e275.tid.equals($currentTid276) ||
                                              !$e275.tid.isDescendantOf(
                                                           $currentTid276)) {
                                            throw $e275;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit273 && $retry274) {
                                { result = result$var271; }
                                continue $label272;
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
                    long result$var281 = result;
                    fabric.worker.transaction.TransactionManager $tm287 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled290 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff288 = 1;
                    boolean $doBackoff289 = true;
                    boolean $retry284 = true;
                    $label282: for (boolean $commit283 = false; !$commit283; ) {
                        if ($backoffEnabled290) {
                            if ($doBackoff289) {
                                if ($backoff288 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff288);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e285) {
                                            
                                        }
                                    }
                                }
                                if ($backoff288 < 5000) $backoff288 *= 2;
                            }
                            $doBackoff289 = $backoff288 <= 32 || !$doBackoff289;
                        }
                        $commit283 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeLastUpdate(weakStats); }
                        catch (final fabric.worker.RetryException $e285) {
                            $commit283 = false;
                            continue $label282;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e285) {
                            $commit283 = false;
                            fabric.common.TransactionID $currentTid286 =
                              $tm287.getCurrentTid();
                            if ($e285.tid.isDescendantOf($currentTid286))
                                continue $label282;
                            if ($currentTid286.parent != null) {
                                $retry284 = false;
                                throw $e285;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e285) {
                            $commit283 = false;
                            if ($tm287.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid286 =
                              $tm287.getCurrentTid();
                            if ($e285.tid.isDescendantOf($currentTid286)) {
                                $retry284 = true;
                            }
                            else if ($currentTid286.parent != null) {
                                $retry284 = false;
                                throw $e285;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e285) {
                            $commit283 = false;
                            if ($tm287.checkForStaleObjects())
                                continue $label282;
                            $retry284 = false;
                            throw new fabric.worker.AbortException($e285);
                        }
                        finally {
                            if ($commit283) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e285) {
                                    $commit283 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e285) {
                                    $commit283 = false;
                                    fabric.common.TransactionID $currentTid286 =
                                      $tm287.getCurrentTid();
                                    if ($currentTid286 != null) {
                                        if ($e285.tid.equals($currentTid286) ||
                                              !$e285.tid.isDescendantOf(
                                                           $currentTid286)) {
                                            throw $e285;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit283 && $retry284) {
                                { result = result$var281; }
                                continue $label282;
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
                    double result$var291 = result;
                    fabric.worker.transaction.TransactionManager $tm297 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled300 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff298 = 1;
                    boolean $doBackoff299 = true;
                    boolean $retry294 = true;
                    $label292: for (boolean $commit293 = false; !$commit293; ) {
                        if ($backoffEnabled300) {
                            if ($doBackoff299) {
                                if ($backoff298 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff298);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e295) {
                                            
                                        }
                                    }
                                }
                                if ($backoff298 < 5000) $backoff298 *= 2;
                            }
                            $doBackoff299 = $backoff298 <= 32 || !$doBackoff299;
                        }
                        $commit293 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeUpdateInterval(weakStats); }
                        catch (final fabric.worker.RetryException $e295) {
                            $commit293 = false;
                            continue $label292;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e295) {
                            $commit293 = false;
                            fabric.common.TransactionID $currentTid296 =
                              $tm297.getCurrentTid();
                            if ($e295.tid.isDescendantOf($currentTid296))
                                continue $label292;
                            if ($currentTid296.parent != null) {
                                $retry294 = false;
                                throw $e295;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e295) {
                            $commit293 = false;
                            if ($tm297.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid296 =
                              $tm297.getCurrentTid();
                            if ($e295.tid.isDescendantOf($currentTid296)) {
                                $retry294 = true;
                            }
                            else if ($currentTid296.parent != null) {
                                $retry294 = false;
                                throw $e295;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e295) {
                            $commit293 = false;
                            if ($tm297.checkForStaleObjects())
                                continue $label292;
                            $retry294 = false;
                            throw new fabric.worker.AbortException($e295);
                        }
                        finally {
                            if ($commit293) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e295) {
                                    $commit293 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e295) {
                                    $commit293 = false;
                                    fabric.common.TransactionID $currentTid296 =
                                      $tm297.getCurrentTid();
                                    if ($currentTid296 != null) {
                                        if ($e295.tid.equals($currentTid296) ||
                                              !$e295.tid.isDescendantOf(
                                                           $currentTid296)) {
                                            throw $e295;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit293 && $retry294) {
                                { result = result$var291; }
                                continue $label292;
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
                    double result$var301 = result;
                    fabric.worker.transaction.TransactionManager $tm307 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled310 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff308 = 1;
                    boolean $doBackoff309 = true;
                    boolean $retry304 = true;
                    $label302: for (boolean $commit303 = false; !$commit303; ) {
                        if ($backoffEnabled310) {
                            if ($doBackoff309) {
                                if ($backoff308 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff308);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e305) {
                                            
                                        }
                                    }
                                }
                                if ($backoff308 < 5000) $backoff308 *= 2;
                            }
                            $doBackoff309 = $backoff308 <= 32 || !$doBackoff309;
                        }
                        $commit303 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeVelocity(weakStats); }
                        catch (final fabric.worker.RetryException $e305) {
                            $commit303 = false;
                            continue $label302;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e305) {
                            $commit303 = false;
                            fabric.common.TransactionID $currentTid306 =
                              $tm307.getCurrentTid();
                            if ($e305.tid.isDescendantOf($currentTid306))
                                continue $label302;
                            if ($currentTid306.parent != null) {
                                $retry304 = false;
                                throw $e305;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e305) {
                            $commit303 = false;
                            if ($tm307.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid306 =
                              $tm307.getCurrentTid();
                            if ($e305.tid.isDescendantOf($currentTid306)) {
                                $retry304 = true;
                            }
                            else if ($currentTid306.parent != null) {
                                $retry304 = false;
                                throw $e305;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e305) {
                            $commit303 = false;
                            if ($tm307.checkForStaleObjects())
                                continue $label302;
                            $retry304 = false;
                            throw new fabric.worker.AbortException($e305);
                        }
                        finally {
                            if ($commit303) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e305) {
                                    $commit303 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e305) {
                                    $commit303 = false;
                                    fabric.common.TransactionID $currentTid306 =
                                      $tm307.getCurrentTid();
                                    if ($currentTid306 != null) {
                                        if ($e305.tid.equals($currentTid306) ||
                                              !$e305.tid.isDescendantOf(
                                                           $currentTid306)) {
                                            throw $e305;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit303 && $retry304) {
                                { result = result$var301; }
                                continue $label302;
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
                    double result$var311 = result;
                    fabric.worker.transaction.TransactionManager $tm317 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled320 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff318 = 1;
                    boolean $doBackoff319 = true;
                    boolean $retry314 = true;
                    $label312: for (boolean $commit313 = false; !$commit313; ) {
                        if ($backoffEnabled320) {
                            if ($doBackoff319) {
                                if ($backoff318 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff318);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e315) {
                                            
                                        }
                                    }
                                }
                                if ($backoff318 < 5000) $backoff318 *= 2;
                            }
                            $doBackoff319 = $backoff318 <= 32 || !$doBackoff319;
                        }
                        $commit313 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.computeNoise(weakStats); }
                        catch (final fabric.worker.RetryException $e315) {
                            $commit313 = false;
                            continue $label312;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e315) {
                            $commit313 = false;
                            fabric.common.TransactionID $currentTid316 =
                              $tm317.getCurrentTid();
                            if ($e315.tid.isDescendantOf($currentTid316))
                                continue $label312;
                            if ($currentTid316.parent != null) {
                                $retry314 = false;
                                throw $e315;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e315) {
                            $commit313 = false;
                            if ($tm317.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid316 =
                              $tm317.getCurrentTid();
                            if ($e315.tid.isDescendantOf($currentTid316)) {
                                $retry314 = true;
                            }
                            else if ($currentTid316.parent != null) {
                                $retry314 = false;
                                throw $e315;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e315) {
                            $commit313 = false;
                            if ($tm317.checkForStaleObjects())
                                continue $label312;
                            $retry314 = false;
                            throw new fabric.worker.AbortException($e315);
                        }
                        finally {
                            if ($commit313) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e315) {
                                    $commit313 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e315) {
                                    $commit313 = false;
                                    fabric.common.TransactionID $currentTid316 =
                                      $tm317.getCurrentTid();
                                    if ($currentTid316 != null) {
                                        if ($e315.tid.equals($currentTid316) ||
                                              !$e315.tid.isDescendantOf(
                                                           $currentTid316)) {
                                            throw $e315;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit313 && $retry314) {
                                { result = result$var311; }
                                continue $label312;
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
    
    public static final byte[] $classHash = new byte[] { 121, -124, -10, 25, 20,
    -28, -88, -64, -83, 41, -117, -67, 72, -1, 63, -44, 98, 52, 92, -33, 28, 21,
    57, 28, -89, 67, -85, 80, -112, -42, -49, 78 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526846750000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1aC3AV1Rk+e/MOgSRAAGMIIVyo4ZErig+MIiQDErmEmJC0BiXd7D0Ja/buXnfPDReUVmsVHy1aRZRW0XFw8IHio1qtUqMjKOMjtvVRbS1MfVSKzFSstjO12v8/e+4zdzfZmWSy/7d3z/nP+R/n/89/7t29x0meZZLaXrlH1erZpgi16lfIPc3BVtm0aKhJky1rLTztVsblNu/4bE+o2kd8QVKiyLqhq4qsdesWIxOCl8kDckCnLNDR1tywjhQpyLhStjYw4lvXGDNJTcTQNvVpBhOTDBv/9nmB7XesL3sih5R2kVJVb2cyU5UmQ2c0xrpISZiGe6hpLQuFaKiLlOuUhtqpqcqauhk6GnoXmWipfbrMoia12qhlaAPYcaIVjVCTzxl/iOIbILYZVZhhgvhltvhRpmqBoGqxhiDJ71WpFrIuJz8iuUGS16vJfdBxSjCuRYCPGFiBz6F7sQpimr2yQuMsuf2qHmJkRiZHQmP/KugArAVhyjYYialydRkekIm2SJqs9wXamanqfdA1z4jCLIxUOg4KnQojstIv99FuRqZl9mu1m6BXETcLsjBSkdmNjwQ+q8zwWYq3jrecu+0KfaXuIxLIHKKKhvIXAlN1BlMb7aUm1RVqM5bMDe6Qp+y/3kcIdK7I6Gz3+c2VXyydXz34qt3n5Cx91vRcRhXWrezumfD7qqa6xTkoRmHEsFRcCmmac6+2ipaGWARW+5TEiNhYH28cbDt48VUP0WM+UtxM8hVDi4ZhVZUrRjiiatS8gOrUlBkNNZMiqoeaeHszKYD7oKpT++ma3l6LsmaSq/FH+Qb/DCbqhSHQRAVwr+q9Rvw+IrMN/D4WIYQUwEUk+H+dkEW/gPsqQnIuZWRVYIMRpoEeLUo3wvIOwEVlU9kQgLg1VSVgmUrAjOpMhU7iEawiACvQLocjGg2t5h/rQYzI2A4XQ+nLNkoSGHaGYoRoj2yBl8SKaWzVIChWGlqImt2Ktm1/M5m0fydfNUW40i1YrdwuEni6KjNHpPJujzYu/+LR7tfsFYe8wmyMVNky1gsZ69NkBLFKMJbqITvVQ3baK8Xqm3Y1P8yXTL7FYysxUgmMdE5Ek1mvYYZjRJK4WpM5P18r4Ol+yCCQJErq2i+98IfX1+bAIo1szEW/QVd/ZsgkE00z3MkQB91K6dbPvt63Y4uRDB5G/MNiejgnxmRtpo1MQ6EhyHnJ4efWyE9179/i92E+KYJUx2RYjJA3qjPnSIvNhnieQ2vkBck4tIGsYVM8ORWzDaaxMfmE+34Ckon2MkBjZQjIU+R57ZG7//Tm0dP55hHPpqUpabedsoaUCMbBSnmslidtv9akFPp9eGfrbbcf37qOGx56zMo2oR9pE0SuDCFrmNe+evn7h/+6+21f0lmM5EeiPZqqxLgu5d/BnwTXt3hhGOIDREjGTSIF1CRyQARnnpOUDbKBBhkJRLf8HXrYCKm9qtyjUVwp35TOXvjU59vKbHdr8MQ2nknmjzxA8vlJjeSq19b/u5oPIym4GyXtl+xmp7hJyZGXmaa8CeWIXf2H6Ttfke+GlQ8JylI3U55zCLcH4Q48jdtiAacLM9oWIam1rVXFnxdYw9P9Ctw3k2uxK7D3rsqmJcfsiE+sRRxjZpaI75RTwuS0h8Jf+WrzD/hIQRcp41u2rLNOGbIWLIMu2HStJvEwSMantadvoPZu0ZCItarMOEiZNjMKkpkG7rE33hfbC99eOGCIMmKnalIN6foegbdh66QI0skxifCbczjLLE7nIKnjhvQxUhAx1QFYWYwUqeFwlKHv+SzzmC0D56mAYoV7FvWqtwsD3nBSZu6ywxHpmQkxS1HMmXB9n5CS6wSyLGIudxATb+ciOT8uWlHUoq24fbAsa6HVVMMQzgNi66fXb7/xu/pt2+04sOujWcNKlFQeu0bic43nE8Zglplus3COFX/ft+W5B7ZsteuHiem7/XI9Gn7k3f+9Xn/nkUNZ9pKCHsPQqKw72g9cSy4Fu50Q+FEW+100avuB29F4bbzrhUJHhNWQn0IGJCjqKolCyPiLBV6YRZKLvUrSiB87XedUYa5BgY9nmXO91zk7R57TIGTC9wROyzKn4nXOFsc5S3DO2QSTJJl9VOBbWebckH1O2FmKIqbBIOXQUCwxrA+HHSeGGxJ4MGVYBicxOPNY8UA/RVQ1Gw2zn5qJ4qYtqusQ9XZx055gyJ4AYg4icrMkZeN/+aLcvERgZ4psKUmfYAxOdzoZ8Pjb/ZPtu0Jr7l/oEztHC1iEGZEFGh2gWspQfozmYSfP1fw8lNwGjhybvrip/5M+O5pnZMyc2fvB1XsPXTBHudVHchL5ftghLJ2pIT3LF5sUzpD62rRcX5OwVRHaoAWuOkLyTrUx91jq8kguqllIWPoSKBQs/xD4caaZk7uvL7mGl/Hlyoe+xmWPvhbJjxiZbq8dv1g0/rSK2J8U8Ip0tWbABdmk5GmB93pTC1nuEbjTWa1UgX/m0rYNyVZY132UdcS3GXwWzCY7br/dkJFmCCzxJjuyjBOYNzrZb3dpuwPJLYwUg+y24G3cg06SU5j2OoGWN8mRxRSojU7yXS5t3Oc7UyVvdJW8H6Y9LvADb5Ijy/sC/zg6yfe4tD2I5L5UyTtdJb8c9pK1Apd5kxxZlgo8Z3SSP+bS9gSSh1Mlb3GUHHfBK2Fn7BR4tjfJkeUsgQudJZeS+4SddZ51Ef85JL8G8ZncT+1Uk62eyR0w1FA2lfxwXQPyfCjwJW8qIcuLAp/zoNIBF5VeQTLISCGuIjxR4Of92YQ/Da6b4QjwmcAXvAmPLL8T+Iyz8ClbQWdW22qG3sene9NFKx5kh9IclU0vXgGdDtcdhJS/KPCXLnr1D6tyOMtOgbeM6JR4zVOdvebhZc5qOZK90uHSfOCi9hEk7zD82jcciTKacGhmgPFaqA2uXxE4QAoc56B41lpzCcPvlPCb64zKr0yMVmxj+Tej8nUZkj/zWY+6KMjLj78xMsmeunskPRMO3kvIZCpwiTcHI8t5AheNKupsPU646PEvJMcZmSAUSMklQ06uegzmPyHwcQcVsrsKyadZ3IQjPSbwPq9u+sZFvW+RfM1IRbqbXLTkjjoTrkOETN0tcLM3RyHLJoGR0TtKynPWRCpAQpj95TioEJQt1hEJyYw6+qoDrjcImXaWjVO/HBNf4UgnBI6ukk76Sip10bAcSREj09J95a4od9e5cH1KSOVXAg94cxeyvCxwdLuZrcw0F2UqkUyChSe0sDVoxt+sBmTNMROugwuOKSffKnDlmLgMR7pA4GKvLqt10RLPNVIVI1XpLhtZ2XiUSTmEVP9AYL0nt3GWBQL9Htw2z0WhBUjmMFIaz+dUMxSVbXJ0GMSYVAACHBV4/1g4jI+0W+AOrw47w0W/s5AEGJmSsW25qBnfuaQaQmYOCtzpoKaDq5DlToE3e3DVEhdVliJZnCwxWgzVci0xJCh6a1cJLB4TP+FIRTbO/K9XP610UQ5rTalxWHnhqCOviytBEqgPZufY6D/h4qThdTFn+ULgMWdlUsW8yKWtHUkQ6nlmtCd+8luWTW5YFVIjyL1ZoOJNbmTpEXjJ6OTucmnDIaQORsarVjtIrdF2Zpjc6JlfgvDIOB9mboczYo2Nc457iwxk+VzgR87Cpx5GkAxxURUXNVBiaT1ksihPxsstpoYBed+sp6o6kALMWFdi4ymfe/MCshwT+ImHENdcdMCfBKQ+RvIGXI8PGNO9IPkNAteMSWzjSC0Cl3qN7QEXrTjBX75FbDsql3ALI2TuPwW+5aCcg1uQZUjgIQ9u+bGLAlcj2cxIgTXCYQEdEyNkniHwjDFxDI60SOAcr465wUWvm5BcA2ch4RgX9bhr5oMQ1wF8KPC33lyDLM8KfNKDa251UWE7kp8zUqyNfDzAxX0jIQt6BM4eE+/gSH6BU7x65y4X1XYh2QEHIOEddw3jXxRJUHbUHxb4tDcHIctTAvd5cND9LlrsQXIvrLHo6A4Ea2HkuwgJ6ALnjYmTcKS5Aqu8Ommfi3r4dYD0YPK8PbKWiUh6iJBTvxToMckhy5BAL0nuGRdNMJilJ6F8GRjpCIBx9CghCwcELnYQ3puLcKSzBc716qKXXBR7GcnzUBjEtx8X/RI70PMQTJ8KPOign4NzkOWAwBecFRnmnNdcdHgDyUEoDHTXoh/3n0E4vCgC68bEMzjSKQIrvXrmHRet3kMylCwMksrFoBxN+0ERXzg6Ocurf+JFVKXpZbr7k1XzKxxe+5s27NVgwffortLCqbs63uMvsiVeMi0KksLeqKalvpiTcp8fMWmvyg1aZL+mE+H6/AXSXPp7goy/e4t3aADpA7vfYUby7X746Qi3JjdsJTdTBaSTjNcNbSPwLnzCyqiJLz3v/XLqf/IL1x7h75yBVWs2/fTrkyZ//MDgI3U37V/53flv9yy65HBVxeKqPU0Pt97y7lDL/wHjcUUzjC0AAA==";
}
