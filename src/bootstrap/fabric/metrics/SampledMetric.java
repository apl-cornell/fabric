package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.util.List;
import fabric.metrics.util.Observer;
import fabric.metrics.util.RunningMetricStats;
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
    
    public double computeValue(boolean useWeakCache);
    
    public double computeSamples(boolean useWeakCache);
    
    public double computeVelocity(boolean useWeakCache);
    
    public double computeNoise(boolean useWeakCache);
    
    public java.lang.String toString();
    
    public boolean isSingleStore();
    
    public fabric.metrics.util.RunningMetricStats get$stats();
    
    public fabric.metrics.util.RunningMetricStats set$stats(fabric.metrics.util.RunningMetricStats val);
    
    /**
   * Updates the velocity and interval estimates with the given observation.
   *
   * @param newVal
   *        the new value of the measured quantity
   * @param eventTime
   *        the time, in milliseconds, this update happened
   */
    public void updateEstimates(double newVal, long eventTime);
    
    public double value(boolean useWeakCache);
    
    public double samples(boolean useWeakCache);
    
    public double velocity(boolean useWeakCache);
    
    public double noise(boolean useWeakCache);
    
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
        
        public fabric.metrics.util.RunningMetricStats get$stats() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$stats();
        }
        
        public fabric.metrics.util.RunningMetricStats set$stats(
          fabric.metrics.util.RunningMetricStats val) {
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
            this.set$stats(
                   ((fabric.metrics.util.RunningMetricStats)
                      new fabric.metrics.util.RunningMetricStats._Impl(
                        this.$getStore(
                               )).$getProxy(
                                    )).fabric$metrics$util$RunningMetricStats$(
                                         init, 0, 1));
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
        
        public double computeValue(boolean useWeakCache) {
            return fabric.metrics.SampledMetric._Impl.
              static_computeValue((fabric.metrics.SampledMetric)
                                    this.$getProxy(), useWeakCache);
        }
        
        private static double static_computeValue(
          fabric.metrics.SampledMetric tmp, boolean useWeakCache) {
            double result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                result = tmp.get$stats().getValue();
            }
            else {
                {
                    double result$var228 = result;
                    fabric.worker.transaction.TransactionManager $tm234 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled237 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff235 = 1;
                    boolean $doBackoff236 = true;
                    boolean $retry231 = true;
                    $label229: for (boolean $commit230 = false; !$commit230; ) {
                        if ($backoffEnabled237) {
                            if ($doBackoff236) {
                                if ($backoff235 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff235);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e232) {
                                            
                                        }
                                    }
                                }
                                if ($backoff235 < 5000) $backoff235 *= 2;
                            }
                            $doBackoff236 = $backoff235 <= 32 || !$doBackoff236;
                        }
                        $commit230 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.get$stats().getValue(); }
                        catch (final fabric.worker.RetryException $e232) {
                            $commit230 = false;
                            continue $label229;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e232) {
                            $commit230 = false;
                            fabric.common.TransactionID $currentTid233 =
                              $tm234.getCurrentTid();
                            if ($e232.tid.isDescendantOf($currentTid233))
                                continue $label229;
                            if ($currentTid233.parent != null) {
                                $retry231 = false;
                                throw $e232;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e232) {
                            $commit230 = false;
                            if ($tm234.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid233 =
                              $tm234.getCurrentTid();
                            if ($e232.tid.isDescendantOf($currentTid233)) {
                                $retry231 = true;
                            }
                            else if ($currentTid233.parent != null) {
                                $retry231 = false;
                                throw $e232;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e232) {
                            $commit230 = false;
                            if ($tm234.checkForStaleObjects())
                                continue $label229;
                            $retry231 = false;
                            throw new fabric.worker.AbortException($e232);
                        }
                        finally {
                            if ($commit230) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e232) {
                                    $commit230 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e232) {
                                    $commit230 = false;
                                    fabric.common.TransactionID $currentTid233 =
                                      $tm234.getCurrentTid();
                                    if ($currentTid233 != null) {
                                        if ($e232.tid.equals($currentTid233) ||
                                              !$e232.tid.isDescendantOf(
                                                           $currentTid233)) {
                                            throw $e232;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit230 && $retry231) {
                                { result = result$var228; }
                                continue $label229;
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        public double computeSamples(boolean useWeakCache) {
            return fabric.metrics.SampledMetric._Impl.
              static_computeSamples((fabric.metrics.SampledMetric)
                                      this.$getProxy(), useWeakCache);
        }
        
        private static double static_computeSamples(
          fabric.metrics.SampledMetric tmp, boolean useWeakCache) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.get$usePreset()) {
                    return 0;
                } else {
                    return tmp.get$stats().getSamples();
                }
            }
            else {
                double rtn = 0;
                {
                    double rtn$var238 = rtn;
                    fabric.worker.transaction.TransactionManager $tm244 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled247 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff245 = 1;
                    boolean $doBackoff246 = true;
                    boolean $retry241 = true;
                    $label239: for (boolean $commit240 = false; !$commit240; ) {
                        if ($backoffEnabled247) {
                            if ($doBackoff246) {
                                if ($backoff245 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff245);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e242) {
                                            
                                        }
                                    }
                                }
                                if ($backoff245 < 5000) $backoff245 *= 2;
                            }
                            $doBackoff246 = $backoff245 <= 32 || !$doBackoff246;
                        }
                        $commit240 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$usePreset()) {
                                rtn = 0;
                            } else {
                                rtn = tmp.get$stats().getSamples();
                            }
                        }
                        catch (final fabric.worker.RetryException $e242) {
                            $commit240 = false;
                            continue $label239;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e242) {
                            $commit240 = false;
                            fabric.common.TransactionID $currentTid243 =
                              $tm244.getCurrentTid();
                            if ($e242.tid.isDescendantOf($currentTid243))
                                continue $label239;
                            if ($currentTid243.parent != null) {
                                $retry241 = false;
                                throw $e242;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e242) {
                            $commit240 = false;
                            if ($tm244.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid243 =
                              $tm244.getCurrentTid();
                            if ($e242.tid.isDescendantOf($currentTid243)) {
                                $retry241 = true;
                            }
                            else if ($currentTid243.parent != null) {
                                $retry241 = false;
                                throw $e242;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e242) {
                            $commit240 = false;
                            if ($tm244.checkForStaleObjects())
                                continue $label239;
                            $retry241 = false;
                            throw new fabric.worker.AbortException($e242);
                        }
                        finally {
                            if ($commit240) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e242) {
                                    $commit240 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e242) {
                                    $commit240 = false;
                                    fabric.common.TransactionID $currentTid243 =
                                      $tm244.getCurrentTid();
                                    if ($currentTid243 != null) {
                                        if ($e242.tid.equals($currentTid243) ||
                                              !$e242.tid.isDescendantOf(
                                                           $currentTid243)) {
                                            throw $e242;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit240 && $retry241) {
                                { rtn = rtn$var238; }
                                continue $label239;
                            }
                        }
                    }
                }
                return rtn;
            }
        }
        
        public double computeVelocity(boolean useWeakCache) {
            return fabric.metrics.SampledMetric._Impl.
              static_computeVelocity((fabric.metrics.SampledMetric)
                                       this.$getProxy(), useWeakCache);
        }
        
        private static double static_computeVelocity(
          fabric.metrics.SampledMetric tmp, boolean useWeakCache) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.get$usePreset()) {
                    return tmp.get$presetV();
                } else {
                    return tmp.get$stats().getVelocityEstimate();
                }
            }
            else {
                double rtn = 0;
                {
                    double rtn$var248 = rtn;
                    fabric.worker.transaction.TransactionManager $tm254 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled257 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff255 = 1;
                    boolean $doBackoff256 = true;
                    boolean $retry251 = true;
                    $label249: for (boolean $commit250 = false; !$commit250; ) {
                        if ($backoffEnabled257) {
                            if ($doBackoff256) {
                                if ($backoff255 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff255);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e252) {
                                            
                                        }
                                    }
                                }
                                if ($backoff255 < 5000) $backoff255 *= 2;
                            }
                            $doBackoff256 = $backoff255 <= 32 || !$doBackoff256;
                        }
                        $commit250 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$usePreset()) {
                                rtn = tmp.get$presetV();
                            } else {
                                rtn = tmp.get$stats().getVelocityEstimate();
                            }
                        }
                        catch (final fabric.worker.RetryException $e252) {
                            $commit250 = false;
                            continue $label249;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e252) {
                            $commit250 = false;
                            fabric.common.TransactionID $currentTid253 =
                              $tm254.getCurrentTid();
                            if ($e252.tid.isDescendantOf($currentTid253))
                                continue $label249;
                            if ($currentTid253.parent != null) {
                                $retry251 = false;
                                throw $e252;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e252) {
                            $commit250 = false;
                            if ($tm254.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid253 =
                              $tm254.getCurrentTid();
                            if ($e252.tid.isDescendantOf($currentTid253)) {
                                $retry251 = true;
                            }
                            else if ($currentTid253.parent != null) {
                                $retry251 = false;
                                throw $e252;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e252) {
                            $commit250 = false;
                            if ($tm254.checkForStaleObjects())
                                continue $label249;
                            $retry251 = false;
                            throw new fabric.worker.AbortException($e252);
                        }
                        finally {
                            if ($commit250) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e252) {
                                    $commit250 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e252) {
                                    $commit250 = false;
                                    fabric.common.TransactionID $currentTid253 =
                                      $tm254.getCurrentTid();
                                    if ($currentTid253 != null) {
                                        if ($e252.tid.equals($currentTid253) ||
                                              !$e252.tid.isDescendantOf(
                                                           $currentTid253)) {
                                            throw $e252;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit250 && $retry251) {
                                { rtn = rtn$var248; }
                                continue $label249;
                            }
                        }
                    }
                }
                return rtn;
            }
        }
        
        public double computeNoise(boolean useWeakCache) {
            return fabric.metrics.SampledMetric._Impl.
              static_computeNoise((fabric.metrics.SampledMetric)
                                    this.$getProxy(), useWeakCache);
        }
        
        private static double static_computeNoise(
          fabric.metrics.SampledMetric tmp, boolean useWeakCache) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.get$usePreset()) {
                    return tmp.get$presetN();
                } else {
                    return tmp.get$stats().getNoiseEstimate();
                }
            }
            else {
                double rtn = 0;
                {
                    double rtn$var258 = rtn;
                    fabric.worker.transaction.TransactionManager $tm264 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled267 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff265 = 1;
                    boolean $doBackoff266 = true;
                    boolean $retry261 = true;
                    $label259: for (boolean $commit260 = false; !$commit260; ) {
                        if ($backoffEnabled267) {
                            if ($doBackoff266) {
                                if ($backoff265 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff265);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e262) {
                                            
                                        }
                                    }
                                }
                                if ($backoff265 < 5000) $backoff265 *= 2;
                            }
                            $doBackoff266 = $backoff265 <= 32 || !$doBackoff266;
                        }
                        $commit260 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$usePreset()) {
                                rtn = tmp.get$presetN();
                            } else {
                                rtn = tmp.get$stats().getNoiseEstimate();
                            }
                        }
                        catch (final fabric.worker.RetryException $e262) {
                            $commit260 = false;
                            continue $label259;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e262) {
                            $commit260 = false;
                            fabric.common.TransactionID $currentTid263 =
                              $tm264.getCurrentTid();
                            if ($e262.tid.isDescendantOf($currentTid263))
                                continue $label259;
                            if ($currentTid263.parent != null) {
                                $retry261 = false;
                                throw $e262;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e262) {
                            $commit260 = false;
                            if ($tm264.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid263 =
                              $tm264.getCurrentTid();
                            if ($e262.tid.isDescendantOf($currentTid263)) {
                                $retry261 = true;
                            }
                            else if ($currentTid263.parent != null) {
                                $retry261 = false;
                                throw $e262;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e262) {
                            $commit260 = false;
                            if ($tm264.checkForStaleObjects())
                                continue $label259;
                            $retry261 = false;
                            throw new fabric.worker.AbortException($e262);
                        }
                        finally {
                            if ($commit260) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e262) {
                                    $commit260 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e262) {
                                    $commit260 = false;
                                    fabric.common.TransactionID $currentTid263 =
                                      $tm264.getCurrentTid();
                                    if ($currentTid263 != null) {
                                        if ($e262.tid.equals($currentTid263) ||
                                              !$e262.tid.isDescendantOf(
                                                           $currentTid263)) {
                                            throw $e262;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit260 && $retry261) {
                                { rtn = rtn$var258; }
                                continue $label259;
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
        
        public fabric.metrics.util.RunningMetricStats get$stats() {
            return this.stats;
        }
        
        public fabric.metrics.util.RunningMetricStats set$stats(
          fabric.metrics.util.RunningMetricStats val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.stats = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.util.RunningMetricStats stats;
        
        /**
   * Updates the velocity and interval estimates with the given observation.
   *
   * @param newVal
   *        the new value of the measured quantity
   * @param eventTime
   *        the time, in milliseconds, this update happened
   */
        public void updateEstimates(double newVal, long eventTime) {
            this.get$stats().update(newVal);
            if (this.get$stats().getSamples() %
                  fabric.worker.Worker.getWorker(
                                         ).config.metricStatsLogInterval == 0) {
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.INFO,
                    "STATS {0} {1}", new java.lang.Object[] { (java.lang.Object) fabric.lang.WrappedJavaInlineable.$unwrap((fabric.metrics.SampledMetric) this.$getProxy()), (java.lang.Object) fabric.lang.WrappedJavaInlineable.$unwrap(this.get$stats()) });
            }
        }
        
        public double value(boolean useWeakCache) {
            return fabric.metrics.SampledMetric._Impl.
              static_value((fabric.metrics.SampledMetric) this.$getProxy(),
                           useWeakCache);
        }
        
        private static double static_value(fabric.metrics.SampledMetric tmp,
                                           boolean useWeakCache) {
            double result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (useWeakCache) result = (double) tmp.get$weakStats().get(0);
                else result = tmp.computeValue(false);
            }
            else {
                {
                    double result$var268 = result;
                    fabric.worker.transaction.TransactionManager $tm274 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled277 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff275 = 1;
                    boolean $doBackoff276 = true;
                    boolean $retry271 = true;
                    $label269: for (boolean $commit270 = false; !$commit270; ) {
                        if ($backoffEnabled277) {
                            if ($doBackoff276) {
                                if ($backoff275 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff275);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e272) {
                                            
                                        }
                                    }
                                }
                                if ($backoff275 < 5000) $backoff275 *= 2;
                            }
                            $doBackoff276 = $backoff275 <= 32 || !$doBackoff276;
                        }
                        $commit270 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache)
                                result = (double) tmp.get$weakStats().get(0);
                            else result = tmp.computeValue(false);
                        }
                        catch (final fabric.worker.RetryException $e272) {
                            $commit270 = false;
                            continue $label269;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e272) {
                            $commit270 = false;
                            fabric.common.TransactionID $currentTid273 =
                              $tm274.getCurrentTid();
                            if ($e272.tid.isDescendantOf($currentTid273))
                                continue $label269;
                            if ($currentTid273.parent != null) {
                                $retry271 = false;
                                throw $e272;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e272) {
                            $commit270 = false;
                            if ($tm274.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid273 =
                              $tm274.getCurrentTid();
                            if ($e272.tid.isDescendantOf($currentTid273)) {
                                $retry271 = true;
                            }
                            else if ($currentTid273.parent != null) {
                                $retry271 = false;
                                throw $e272;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e272) {
                            $commit270 = false;
                            if ($tm274.checkForStaleObjects())
                                continue $label269;
                            $retry271 = false;
                            throw new fabric.worker.AbortException($e272);
                        }
                        finally {
                            if ($commit270) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e272) {
                                    $commit270 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e272) {
                                    $commit270 = false;
                                    fabric.common.TransactionID $currentTid273 =
                                      $tm274.getCurrentTid();
                                    if ($currentTid273 != null) {
                                        if ($e272.tid.equals($currentTid273) ||
                                              !$e272.tid.isDescendantOf(
                                                           $currentTid273)) {
                                            throw $e272;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit270 && $retry271) {
                                { result = result$var268; }
                                continue $label269;
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        public double samples(boolean useWeakCache) {
            return fabric.metrics.SampledMetric._Impl.
              static_samples((fabric.metrics.SampledMetric) this.$getProxy(),
                             useWeakCache);
        }
        
        private static double static_samples(fabric.metrics.SampledMetric tmp,
                                             boolean useWeakCache) {
            double result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (useWeakCache) result = (double) tmp.get$weakStats().get(3);
                else result = tmp.computeSamples(false);
            }
            else {
                {
                    double result$var278 = result;
                    fabric.worker.transaction.TransactionManager $tm284 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled287 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff285 = 1;
                    boolean $doBackoff286 = true;
                    boolean $retry281 = true;
                    $label279: for (boolean $commit280 = false; !$commit280; ) {
                        if ($backoffEnabled287) {
                            if ($doBackoff286) {
                                if ($backoff285 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff285);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e282) {
                                            
                                        }
                                    }
                                }
                                if ($backoff285 < 5000) $backoff285 *= 2;
                            }
                            $doBackoff286 = $backoff285 <= 32 || !$doBackoff286;
                        }
                        $commit280 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache)
                                result = (double) tmp.get$weakStats().get(3);
                            else result = tmp.computeSamples(false);
                        }
                        catch (final fabric.worker.RetryException $e282) {
                            $commit280 = false;
                            continue $label279;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e282) {
                            $commit280 = false;
                            fabric.common.TransactionID $currentTid283 =
                              $tm284.getCurrentTid();
                            if ($e282.tid.isDescendantOf($currentTid283))
                                continue $label279;
                            if ($currentTid283.parent != null) {
                                $retry281 = false;
                                throw $e282;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e282) {
                            $commit280 = false;
                            if ($tm284.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid283 =
                              $tm284.getCurrentTid();
                            if ($e282.tid.isDescendantOf($currentTid283)) {
                                $retry281 = true;
                            }
                            else if ($currentTid283.parent != null) {
                                $retry281 = false;
                                throw $e282;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e282) {
                            $commit280 = false;
                            if ($tm284.checkForStaleObjects())
                                continue $label279;
                            $retry281 = false;
                            throw new fabric.worker.AbortException($e282);
                        }
                        finally {
                            if ($commit280) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e282) {
                                    $commit280 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e282) {
                                    $commit280 = false;
                                    fabric.common.TransactionID $currentTid283 =
                                      $tm284.getCurrentTid();
                                    if ($currentTid283 != null) {
                                        if ($e282.tid.equals($currentTid283) ||
                                              !$e282.tid.isDescendantOf(
                                                           $currentTid283)) {
                                            throw $e282;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit280 && $retry281) {
                                { result = result$var278; }
                                continue $label279;
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        public double velocity(boolean useWeakCache) {
            return fabric.metrics.SampledMetric._Impl.
              static_velocity((fabric.metrics.SampledMetric) this.$getProxy(),
                              useWeakCache);
        }
        
        private static double static_velocity(fabric.metrics.SampledMetric tmp,
                                              boolean useWeakCache) {
            double result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (useWeakCache) result = (double) tmp.get$weakStats().get(1);
                else result = tmp.computeVelocity(false);
            }
            else {
                {
                    double result$var288 = result;
                    fabric.worker.transaction.TransactionManager $tm294 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled297 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff295 = 1;
                    boolean $doBackoff296 = true;
                    boolean $retry291 = true;
                    $label289: for (boolean $commit290 = false; !$commit290; ) {
                        if ($backoffEnabled297) {
                            if ($doBackoff296) {
                                if ($backoff295 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff295);
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
                        $commit290 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache)
                                result = (double) tmp.get$weakStats().get(1);
                            else result = tmp.computeVelocity(false);
                        }
                        catch (final fabric.worker.RetryException $e292) {
                            $commit290 = false;
                            continue $label289;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e292) {
                            $commit290 = false;
                            fabric.common.TransactionID $currentTid293 =
                              $tm294.getCurrentTid();
                            if ($e292.tid.isDescendantOf($currentTid293))
                                continue $label289;
                            if ($currentTid293.parent != null) {
                                $retry291 = false;
                                throw $e292;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e292) {
                            $commit290 = false;
                            if ($tm294.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid293 =
                              $tm294.getCurrentTid();
                            if ($e292.tid.isDescendantOf($currentTid293)) {
                                $retry291 = true;
                            }
                            else if ($currentTid293.parent != null) {
                                $retry291 = false;
                                throw $e292;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e292) {
                            $commit290 = false;
                            if ($tm294.checkForStaleObjects())
                                continue $label289;
                            $retry291 = false;
                            throw new fabric.worker.AbortException($e292);
                        }
                        finally {
                            if ($commit290) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e292) {
                                    $commit290 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e292) {
                                    $commit290 = false;
                                    fabric.common.TransactionID $currentTid293 =
                                      $tm294.getCurrentTid();
                                    if ($currentTid293 != null) {
                                        if ($e292.tid.equals($currentTid293) ||
                                              !$e292.tid.isDescendantOf(
                                                           $currentTid293)) {
                                            throw $e292;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit290 && $retry291) {
                                { result = result$var288; }
                                continue $label289;
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        public double noise(boolean useWeakCache) {
            return fabric.metrics.SampledMetric._Impl.
              static_noise((fabric.metrics.SampledMetric) this.$getProxy(),
                           useWeakCache);
        }
        
        private static double static_noise(fabric.metrics.SampledMetric tmp,
                                           boolean useWeakCache) {
            double result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (useWeakCache) result = (double) tmp.get$weakStats().get(2);
                else result = tmp.computeNoise(false);
            }
            else {
                {
                    double result$var298 = result;
                    fabric.worker.transaction.TransactionManager $tm304 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled307 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff305 = 1;
                    boolean $doBackoff306 = true;
                    boolean $retry301 = true;
                    $label299: for (boolean $commit300 = false; !$commit300; ) {
                        if ($backoffEnabled307) {
                            if ($doBackoff306) {
                                if ($backoff305 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff305);
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
                        $commit300 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache)
                                result = (double) tmp.get$weakStats().get(2);
                            else result = tmp.computeNoise(false);
                        }
                        catch (final fabric.worker.RetryException $e302) {
                            $commit300 = false;
                            continue $label299;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e302) {
                            $commit300 = false;
                            fabric.common.TransactionID $currentTid303 =
                              $tm304.getCurrentTid();
                            if ($e302.tid.isDescendantOf($currentTid303))
                                continue $label299;
                            if ($currentTid303.parent != null) {
                                $retry301 = false;
                                throw $e302;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e302) {
                            $commit300 = false;
                            if ($tm304.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid303 =
                              $tm304.getCurrentTid();
                            if ($e302.tid.isDescendantOf($currentTid303)) {
                                $retry301 = true;
                            }
                            else if ($currentTid303.parent != null) {
                                $retry301 = false;
                                throw $e302;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e302) {
                            $commit300 = false;
                            if ($tm304.checkForStaleObjects())
                                continue $label299;
                            $retry301 = false;
                            throw new fabric.worker.AbortException($e302);
                        }
                        finally {
                            if ($commit300) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e302) {
                                    $commit300 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e302) {
                                    $commit300 = false;
                                    fabric.common.TransactionID $currentTid303 =
                                      $tm304.getCurrentTid();
                                    if ($currentTid303 != null) {
                                        if ($e302.tid.equals($currentTid303) ||
                                              !$e302.tid.isDescendantOf(
                                                           $currentTid303)) {
                                            throw $e302;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit300 && $retry301) {
                                { result = result$var298; }
                                continue $label299;
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
            $writeRef($getStore(), this.stats, refTypes, out, intraStoreRefs,
                      interStoreRefs);
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
            this.name = (java.lang.String) in.readObject();
            this.usePreset = in.readBoolean();
            this.presetR = in.readDouble();
            this.presetB = in.readDouble();
            this.presetV = in.readDouble();
            this.presetN = in.readDouble();
            this.stats =
              (fabric.metrics.util.RunningMetricStats)
                $readRef(fabric.metrics.util.RunningMetricStats._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
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
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.SampledMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -42, -41, 6, -103, 108,
    -67, 63, -98, -106, 24, -61, -4, 7, 111, -8, -46, 100, 4, -39, 16, 13, -44,
    -73, 123, -98, 110, -102, -19, -125, -41, -37, -19 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521305458000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
