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
    
    public long computeSamples(boolean useWeakCache);
    
    public long computeLastUpdate(boolean useWeakCache);
    
    public double computeUpdateInterval(boolean useWeakCache);
    
    public double computeVelocity(boolean useWeakCache);
    
    public double computeNoise(boolean useWeakCache);
    
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
    
    public double value(boolean useWeakCache);
    
    public long samples(boolean useWeakCache);
    
    public long lastUpdate(boolean useWeakCache);
    
    public double updateInterval(boolean useWeakCache);
    
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
                tmp.get$stats().preload(tmp.get$name());
                result = tmp.get$stats().getValue();
            }
            else {
                {
                    double result$var266 = result;
                    fabric.worker.transaction.TransactionManager $tm272 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled275 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff273 = 1;
                    boolean $doBackoff274 = true;
                    boolean $retry269 = true;
                    $label267: for (boolean $commit268 = false; !$commit268; ) {
                        if ($backoffEnabled275) {
                            if ($doBackoff274) {
                                if ($backoff273 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff273);
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
                        $commit268 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.get$stats().preload(tmp.get$name());
                            result = tmp.get$stats().getValue();
                        }
                        catch (final fabric.worker.RetryException $e270) {
                            $commit268 = false;
                            continue $label267;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e270) {
                            $commit268 = false;
                            fabric.common.TransactionID $currentTid271 =
                              $tm272.getCurrentTid();
                            if ($e270.tid.isDescendantOf($currentTid271))
                                continue $label267;
                            if ($currentTid271.parent != null) {
                                $retry269 = false;
                                throw $e270;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e270) {
                            $commit268 = false;
                            if ($tm272.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid271 =
                              $tm272.getCurrentTid();
                            if ($e270.tid.isDescendantOf($currentTid271)) {
                                $retry269 = true;
                            }
                            else if ($currentTid271.parent != null) {
                                $retry269 = false;
                                throw $e270;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e270) {
                            $commit268 = false;
                            if ($tm272.checkForStaleObjects())
                                continue $label267;
                            $retry269 = false;
                            throw new fabric.worker.AbortException($e270);
                        }
                        finally {
                            if ($commit268) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e270) {
                                    $commit268 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e270) {
                                    $commit268 = false;
                                    fabric.common.TransactionID $currentTid271 =
                                      $tm272.getCurrentTid();
                                    if ($currentTid271 != null) {
                                        if ($e270.tid.equals($currentTid271) ||
                                              !$e270.tid.isDescendantOf(
                                                           $currentTid271)) {
                                            throw $e270;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit268 && $retry269) {
                                { result = result$var266; }
                                continue $label267;
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        public long computeSamples(boolean useWeakCache) {
            return fabric.metrics.SampledMetric._Impl.
              static_computeSamples((fabric.metrics.SampledMetric)
                                      this.$getProxy(), useWeakCache);
        }
        
        private static long static_computeSamples(
          fabric.metrics.SampledMetric tmp, boolean useWeakCache) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.get$usePreset()) {
                    return 0;
                } else {
                    tmp.get$stats().preload(tmp.get$name());
                    return tmp.get$stats().getSamples();
                }
            }
            else {
                long rtn = 0;
                {
                    long rtn$var276 = rtn;
                    fabric.worker.transaction.TransactionManager $tm282 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled285 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff283 = 1;
                    boolean $doBackoff284 = true;
                    boolean $retry279 = true;
                    $label277: for (boolean $commit278 = false; !$commit278; ) {
                        if ($backoffEnabled285) {
                            if ($doBackoff284) {
                                if ($backoff283 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff283);
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
                        $commit278 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$usePreset()) {
                                rtn = 0;
                            } else {
                                tmp.get$stats().preload(tmp.get$name());
                                rtn = tmp.get$stats().getSamples();
                            }
                        }
                        catch (final fabric.worker.RetryException $e280) {
                            $commit278 = false;
                            continue $label277;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e280) {
                            $commit278 = false;
                            fabric.common.TransactionID $currentTid281 =
                              $tm282.getCurrentTid();
                            if ($e280.tid.isDescendantOf($currentTid281))
                                continue $label277;
                            if ($currentTid281.parent != null) {
                                $retry279 = false;
                                throw $e280;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e280) {
                            $commit278 = false;
                            if ($tm282.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid281 =
                              $tm282.getCurrentTid();
                            if ($e280.tid.isDescendantOf($currentTid281)) {
                                $retry279 = true;
                            }
                            else if ($currentTid281.parent != null) {
                                $retry279 = false;
                                throw $e280;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e280) {
                            $commit278 = false;
                            if ($tm282.checkForStaleObjects())
                                continue $label277;
                            $retry279 = false;
                            throw new fabric.worker.AbortException($e280);
                        }
                        finally {
                            if ($commit278) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e280) {
                                    $commit278 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e280) {
                                    $commit278 = false;
                                    fabric.common.TransactionID $currentTid281 =
                                      $tm282.getCurrentTid();
                                    if ($currentTid281 != null) {
                                        if ($e280.tid.equals($currentTid281) ||
                                              !$e280.tid.isDescendantOf(
                                                           $currentTid281)) {
                                            throw $e280;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit278 && $retry279) {
                                { rtn = rtn$var276; }
                                continue $label277;
                            }
                        }
                    }
                }
                return rtn;
            }
        }
        
        public long computeLastUpdate(boolean useWeakCache) {
            return fabric.metrics.SampledMetric._Impl.
              static_computeLastUpdate((fabric.metrics.SampledMetric)
                                         this.$getProxy(), useWeakCache);
        }
        
        private static long static_computeLastUpdate(
          fabric.metrics.SampledMetric tmp, boolean useWeakCache) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.get$usePreset()) {
                    return 0;
                } else {
                    tmp.get$stats().preload(tmp.get$name());
                    return tmp.get$stats().getLastUpdate();
                }
            }
            else {
                long rtn = 0;
                {
                    long rtn$var286 = rtn;
                    fabric.worker.transaction.TransactionManager $tm292 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled295 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff293 = 1;
                    boolean $doBackoff294 = true;
                    boolean $retry289 = true;
                    $label287: for (boolean $commit288 = false; !$commit288; ) {
                        if ($backoffEnabled295) {
                            if ($doBackoff294) {
                                if ($backoff293 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff293);
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
                        $commit288 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$usePreset()) {
                                rtn = 0;
                            } else {
                                tmp.get$stats().preload(tmp.get$name());
                                rtn = tmp.get$stats().getLastUpdate();
                            }
                        }
                        catch (final fabric.worker.RetryException $e290) {
                            $commit288 = false;
                            continue $label287;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e290) {
                            $commit288 = false;
                            fabric.common.TransactionID $currentTid291 =
                              $tm292.getCurrentTid();
                            if ($e290.tid.isDescendantOf($currentTid291))
                                continue $label287;
                            if ($currentTid291.parent != null) {
                                $retry289 = false;
                                throw $e290;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e290) {
                            $commit288 = false;
                            if ($tm292.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid291 =
                              $tm292.getCurrentTid();
                            if ($e290.tid.isDescendantOf($currentTid291)) {
                                $retry289 = true;
                            }
                            else if ($currentTid291.parent != null) {
                                $retry289 = false;
                                throw $e290;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e290) {
                            $commit288 = false;
                            if ($tm292.checkForStaleObjects())
                                continue $label287;
                            $retry289 = false;
                            throw new fabric.worker.AbortException($e290);
                        }
                        finally {
                            if ($commit288) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e290) {
                                    $commit288 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e290) {
                                    $commit288 = false;
                                    fabric.common.TransactionID $currentTid291 =
                                      $tm292.getCurrentTid();
                                    if ($currentTid291 != null) {
                                        if ($e290.tid.equals($currentTid291) ||
                                              !$e290.tid.isDescendantOf(
                                                           $currentTid291)) {
                                            throw $e290;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit288 && $retry289) {
                                { rtn = rtn$var286; }
                                continue $label287;
                            }
                        }
                    }
                }
                return rtn;
            }
        }
        
        public double computeUpdateInterval(boolean useWeakCache) {
            return fabric.metrics.SampledMetric._Impl.
              static_computeUpdateInterval((fabric.metrics.SampledMetric)
                                             this.$getProxy(), useWeakCache);
        }
        
        private static double static_computeUpdateInterval(
          fabric.metrics.SampledMetric tmp, boolean useWeakCache) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.get$usePreset()) {
                    return 0;
                } else {
                    tmp.get$stats().preload(tmp.get$name());
                    return tmp.get$stats().getIntervalEstimate();
                }
            }
            else {
                double rtn = 0;
                {
                    double rtn$var296 = rtn;
                    fabric.worker.transaction.TransactionManager $tm302 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled305 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff303 = 1;
                    boolean $doBackoff304 = true;
                    boolean $retry299 = true;
                    $label297: for (boolean $commit298 = false; !$commit298; ) {
                        if ($backoffEnabled305) {
                            if ($doBackoff304) {
                                if ($backoff303 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff303);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e300) {
                                            
                                        }
                                    }
                                }
                                if ($backoff303 < 5000) $backoff303 *= 2;
                            }
                            $doBackoff304 = $backoff303 <= 32 || !$doBackoff304;
                        }
                        $commit298 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$usePreset()) {
                                rtn = 0;
                            } else {
                                tmp.get$stats().preload(tmp.get$name());
                                rtn = tmp.get$stats().getIntervalEstimate();
                            }
                        }
                        catch (final fabric.worker.RetryException $e300) {
                            $commit298 = false;
                            continue $label297;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e300) {
                            $commit298 = false;
                            fabric.common.TransactionID $currentTid301 =
                              $tm302.getCurrentTid();
                            if ($e300.tid.isDescendantOf($currentTid301))
                                continue $label297;
                            if ($currentTid301.parent != null) {
                                $retry299 = false;
                                throw $e300;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e300) {
                            $commit298 = false;
                            if ($tm302.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid301 =
                              $tm302.getCurrentTid();
                            if ($e300.tid.isDescendantOf($currentTid301)) {
                                $retry299 = true;
                            }
                            else if ($currentTid301.parent != null) {
                                $retry299 = false;
                                throw $e300;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e300) {
                            $commit298 = false;
                            if ($tm302.checkForStaleObjects())
                                continue $label297;
                            $retry299 = false;
                            throw new fabric.worker.AbortException($e300);
                        }
                        finally {
                            if ($commit298) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e300) {
                                    $commit298 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e300) {
                                    $commit298 = false;
                                    fabric.common.TransactionID $currentTid301 =
                                      $tm302.getCurrentTid();
                                    if ($currentTid301 != null) {
                                        if ($e300.tid.equals($currentTid301) ||
                                              !$e300.tid.isDescendantOf(
                                                           $currentTid301)) {
                                            throw $e300;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit298 && $retry299) {
                                { rtn = rtn$var296; }
                                continue $label297;
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
                    tmp.get$stats().preload(tmp.get$name());
                    return tmp.get$stats().getVelocityEstimate();
                }
            }
            else {
                double rtn = 0;
                {
                    double rtn$var306 = rtn;
                    fabric.worker.transaction.TransactionManager $tm312 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled315 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff313 = 1;
                    boolean $doBackoff314 = true;
                    boolean $retry309 = true;
                    $label307: for (boolean $commit308 = false; !$commit308; ) {
                        if ($backoffEnabled315) {
                            if ($doBackoff314) {
                                if ($backoff313 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff313);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e310) {
                                            
                                        }
                                    }
                                }
                                if ($backoff313 < 5000) $backoff313 *= 2;
                            }
                            $doBackoff314 = $backoff313 <= 32 || !$doBackoff314;
                        }
                        $commit308 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$usePreset()) {
                                rtn = tmp.get$presetV();
                            } else {
                                tmp.get$stats().preload(tmp.get$name());
                                rtn = tmp.get$stats().getVelocityEstimate();
                            }
                        }
                        catch (final fabric.worker.RetryException $e310) {
                            $commit308 = false;
                            continue $label307;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e310) {
                            $commit308 = false;
                            fabric.common.TransactionID $currentTid311 =
                              $tm312.getCurrentTid();
                            if ($e310.tid.isDescendantOf($currentTid311))
                                continue $label307;
                            if ($currentTid311.parent != null) {
                                $retry309 = false;
                                throw $e310;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e310) {
                            $commit308 = false;
                            if ($tm312.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid311 =
                              $tm312.getCurrentTid();
                            if ($e310.tid.isDescendantOf($currentTid311)) {
                                $retry309 = true;
                            }
                            else if ($currentTid311.parent != null) {
                                $retry309 = false;
                                throw $e310;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e310) {
                            $commit308 = false;
                            if ($tm312.checkForStaleObjects())
                                continue $label307;
                            $retry309 = false;
                            throw new fabric.worker.AbortException($e310);
                        }
                        finally {
                            if ($commit308) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e310) {
                                    $commit308 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e310) {
                                    $commit308 = false;
                                    fabric.common.TransactionID $currentTid311 =
                                      $tm312.getCurrentTid();
                                    if ($currentTid311 != null) {
                                        if ($e310.tid.equals($currentTid311) ||
                                              !$e310.tid.isDescendantOf(
                                                           $currentTid311)) {
                                            throw $e310;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit308 && $retry309) {
                                { rtn = rtn$var306; }
                                continue $label307;
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
                    tmp.get$stats().preload(tmp.get$name());
                    return tmp.get$stats().getNoiseEstimate();
                }
            }
            else {
                double rtn = 0;
                {
                    double rtn$var316 = rtn;
                    fabric.worker.transaction.TransactionManager $tm322 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled325 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff323 = 1;
                    boolean $doBackoff324 = true;
                    boolean $retry319 = true;
                    $label317: for (boolean $commit318 = false; !$commit318; ) {
                        if ($backoffEnabled325) {
                            if ($doBackoff324) {
                                if ($backoff323 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff323);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e320) {
                                            
                                        }
                                    }
                                }
                                if ($backoff323 < 5000) $backoff323 *= 2;
                            }
                            $doBackoff324 = $backoff323 <= 32 || !$doBackoff324;
                        }
                        $commit318 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$usePreset()) {
                                rtn = tmp.get$presetN();
                            } else {
                                tmp.get$stats().preload(tmp.get$name());
                                rtn = tmp.get$stats().getNoiseEstimate();
                            }
                        }
                        catch (final fabric.worker.RetryException $e320) {
                            $commit318 = false;
                            continue $label317;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e320) {
                            $commit318 = false;
                            fabric.common.TransactionID $currentTid321 =
                              $tm322.getCurrentTid();
                            if ($e320.tid.isDescendantOf($currentTid321))
                                continue $label317;
                            if ($currentTid321.parent != null) {
                                $retry319 = false;
                                throw $e320;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e320) {
                            $commit318 = false;
                            if ($tm322.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid321 =
                              $tm322.getCurrentTid();
                            if ($e320.tid.isDescendantOf($currentTid321)) {
                                $retry319 = true;
                            }
                            else if ($currentTid321.parent != null) {
                                $retry319 = false;
                                throw $e320;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e320) {
                            $commit318 = false;
                            if ($tm322.checkForStaleObjects())
                                continue $label317;
                            $retry319 = false;
                            throw new fabric.worker.AbortException($e320);
                        }
                        finally {
                            if ($commit318) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e320) {
                                    $commit318 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e320) {
                                    $commit318 = false;
                                    fabric.common.TransactionID $currentTid321 =
                                      $tm322.getCurrentTid();
                                    if ($currentTid321 != null) {
                                        if ($e320.tid.equals($currentTid321) ||
                                              !$e320.tid.isDescendantOf(
                                                           $currentTid321)) {
                                            throw $e320;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit318 && $retry319) {
                                { rtn = rtn$var316; }
                                continue $label317;
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
            this.get$stats().preload(this.get$name());
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
                if (useWeakCache) {
                    result = tmp.get$cachedValue();
                } else {
                    result = tmp.computeValue(false);
                }
            }
            else {
                {
                    double result$var326 = result;
                    fabric.worker.transaction.TransactionManager $tm332 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled335 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff333 = 1;
                    boolean $doBackoff334 = true;
                    boolean $retry329 = true;
                    $label327: for (boolean $commit328 = false; !$commit328; ) {
                        if ($backoffEnabled335) {
                            if ($doBackoff334) {
                                if ($backoff333 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff333);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e330) {
                                            
                                        }
                                    }
                                }
                                if ($backoff333 < 5000) $backoff333 *= 2;
                            }
                            $doBackoff334 = $backoff333 <= 32 || !$doBackoff334;
                        }
                        $commit328 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache) {
                                result = tmp.get$cachedValue();
                            } else {
                                result = tmp.computeValue(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e330) {
                            $commit328 = false;
                            continue $label327;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e330) {
                            $commit328 = false;
                            fabric.common.TransactionID $currentTid331 =
                              $tm332.getCurrentTid();
                            if ($e330.tid.isDescendantOf($currentTid331))
                                continue $label327;
                            if ($currentTid331.parent != null) {
                                $retry329 = false;
                                throw $e330;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e330) {
                            $commit328 = false;
                            if ($tm332.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid331 =
                              $tm332.getCurrentTid();
                            if ($e330.tid.isDescendantOf($currentTid331)) {
                                $retry329 = true;
                            }
                            else if ($currentTid331.parent != null) {
                                $retry329 = false;
                                throw $e330;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e330) {
                            $commit328 = false;
                            if ($tm332.checkForStaleObjects())
                                continue $label327;
                            $retry329 = false;
                            throw new fabric.worker.AbortException($e330);
                        }
                        finally {
                            if ($commit328) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e330) {
                                    $commit328 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e330) {
                                    $commit328 = false;
                                    fabric.common.TransactionID $currentTid331 =
                                      $tm332.getCurrentTid();
                                    if ($currentTid331 != null) {
                                        if ($e330.tid.equals($currentTid331) ||
                                              !$e330.tid.isDescendantOf(
                                                           $currentTid331)) {
                                            throw $e330;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit328 && $retry329) {
                                { result = result$var326; }
                                continue $label327;
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        public long samples(boolean useWeakCache) {
            return fabric.metrics.SampledMetric._Impl.
              static_samples((fabric.metrics.SampledMetric) this.$getProxy(),
                             useWeakCache);
        }
        
        private static long static_samples(fabric.metrics.SampledMetric tmp,
                                           boolean useWeakCache) {
            long result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (useWeakCache) {
                    result = tmp.get$cachedSamples();
                } else {
                    result = tmp.computeSamples(false);
                }
            }
            else {
                {
                    long result$var336 = result;
                    fabric.worker.transaction.TransactionManager $tm342 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled345 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff343 = 1;
                    boolean $doBackoff344 = true;
                    boolean $retry339 = true;
                    $label337: for (boolean $commit338 = false; !$commit338; ) {
                        if ($backoffEnabled345) {
                            if ($doBackoff344) {
                                if ($backoff343 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff343);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e340) {
                                            
                                        }
                                    }
                                }
                                if ($backoff343 < 5000) $backoff343 *= 2;
                            }
                            $doBackoff344 = $backoff343 <= 32 || !$doBackoff344;
                        }
                        $commit338 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache) {
                                result = tmp.get$cachedSamples();
                            } else {
                                result = tmp.computeSamples(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e340) {
                            $commit338 = false;
                            continue $label337;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e340) {
                            $commit338 = false;
                            fabric.common.TransactionID $currentTid341 =
                              $tm342.getCurrentTid();
                            if ($e340.tid.isDescendantOf($currentTid341))
                                continue $label337;
                            if ($currentTid341.parent != null) {
                                $retry339 = false;
                                throw $e340;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e340) {
                            $commit338 = false;
                            if ($tm342.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid341 =
                              $tm342.getCurrentTid();
                            if ($e340.tid.isDescendantOf($currentTid341)) {
                                $retry339 = true;
                            }
                            else if ($currentTid341.parent != null) {
                                $retry339 = false;
                                throw $e340;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e340) {
                            $commit338 = false;
                            if ($tm342.checkForStaleObjects())
                                continue $label337;
                            $retry339 = false;
                            throw new fabric.worker.AbortException($e340);
                        }
                        finally {
                            if ($commit338) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e340) {
                                    $commit338 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e340) {
                                    $commit338 = false;
                                    fabric.common.TransactionID $currentTid341 =
                                      $tm342.getCurrentTid();
                                    if ($currentTid341 != null) {
                                        if ($e340.tid.equals($currentTid341) ||
                                              !$e340.tid.isDescendantOf(
                                                           $currentTid341)) {
                                            throw $e340;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit338 && $retry339) {
                                { result = result$var336; }
                                continue $label337;
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        public long lastUpdate(boolean useWeakCache) {
            return fabric.metrics.SampledMetric._Impl.
              static_lastUpdate((fabric.metrics.SampledMetric) this.$getProxy(),
                                useWeakCache);
        }
        
        private static long static_lastUpdate(fabric.metrics.SampledMetric tmp,
                                              boolean useWeakCache) {
            long result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (useWeakCache) {
                    result = tmp.get$cachedLastUpdate();
                } else {
                    result = tmp.computeLastUpdate(false);
                }
            }
            else {
                {
                    long result$var346 = result;
                    fabric.worker.transaction.TransactionManager $tm352 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled355 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff353 = 1;
                    boolean $doBackoff354 = true;
                    boolean $retry349 = true;
                    $label347: for (boolean $commit348 = false; !$commit348; ) {
                        if ($backoffEnabled355) {
                            if ($doBackoff354) {
                                if ($backoff353 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff353);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e350) {
                                            
                                        }
                                    }
                                }
                                if ($backoff353 < 5000) $backoff353 *= 2;
                            }
                            $doBackoff354 = $backoff353 <= 32 || !$doBackoff354;
                        }
                        $commit348 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache) {
                                result = tmp.get$cachedLastUpdate();
                            } else {
                                result = tmp.computeLastUpdate(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e350) {
                            $commit348 = false;
                            continue $label347;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e350) {
                            $commit348 = false;
                            fabric.common.TransactionID $currentTid351 =
                              $tm352.getCurrentTid();
                            if ($e350.tid.isDescendantOf($currentTid351))
                                continue $label347;
                            if ($currentTid351.parent != null) {
                                $retry349 = false;
                                throw $e350;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e350) {
                            $commit348 = false;
                            if ($tm352.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid351 =
                              $tm352.getCurrentTid();
                            if ($e350.tid.isDescendantOf($currentTid351)) {
                                $retry349 = true;
                            }
                            else if ($currentTid351.parent != null) {
                                $retry349 = false;
                                throw $e350;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e350) {
                            $commit348 = false;
                            if ($tm352.checkForStaleObjects())
                                continue $label347;
                            $retry349 = false;
                            throw new fabric.worker.AbortException($e350);
                        }
                        finally {
                            if ($commit348) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e350) {
                                    $commit348 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e350) {
                                    $commit348 = false;
                                    fabric.common.TransactionID $currentTid351 =
                                      $tm352.getCurrentTid();
                                    if ($currentTid351 != null) {
                                        if ($e350.tid.equals($currentTid351) ||
                                              !$e350.tid.isDescendantOf(
                                                           $currentTid351)) {
                                            throw $e350;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit348 && $retry349) {
                                { result = result$var346; }
                                continue $label347;
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        public double updateInterval(boolean useWeakCache) {
            return fabric.metrics.SampledMetric._Impl.
              static_updateInterval((fabric.metrics.SampledMetric)
                                      this.$getProxy(), useWeakCache);
        }
        
        private static double static_updateInterval(
          fabric.metrics.SampledMetric tmp, boolean useWeakCache) {
            double result = 0;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (useWeakCache) {
                    result = tmp.get$cachedUpdateInterval();
                } else {
                    result = tmp.computeUpdateInterval(false);
                }
            }
            else {
                {
                    double result$var356 = result;
                    fabric.worker.transaction.TransactionManager $tm362 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled365 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff363 = 1;
                    boolean $doBackoff364 = true;
                    boolean $retry359 = true;
                    $label357: for (boolean $commit358 = false; !$commit358; ) {
                        if ($backoffEnabled365) {
                            if ($doBackoff364) {
                                if ($backoff363 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff363);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e360) {
                                            
                                        }
                                    }
                                }
                                if ($backoff363 < 5000) $backoff363 *= 2;
                            }
                            $doBackoff364 = $backoff363 <= 32 || !$doBackoff364;
                        }
                        $commit358 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache) {
                                result = tmp.get$cachedUpdateInterval();
                            } else {
                                result = tmp.computeUpdateInterval(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e360) {
                            $commit358 = false;
                            continue $label357;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e360) {
                            $commit358 = false;
                            fabric.common.TransactionID $currentTid361 =
                              $tm362.getCurrentTid();
                            if ($e360.tid.isDescendantOf($currentTid361))
                                continue $label357;
                            if ($currentTid361.parent != null) {
                                $retry359 = false;
                                throw $e360;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e360) {
                            $commit358 = false;
                            if ($tm362.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid361 =
                              $tm362.getCurrentTid();
                            if ($e360.tid.isDescendantOf($currentTid361)) {
                                $retry359 = true;
                            }
                            else if ($currentTid361.parent != null) {
                                $retry359 = false;
                                throw $e360;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e360) {
                            $commit358 = false;
                            if ($tm362.checkForStaleObjects())
                                continue $label357;
                            $retry359 = false;
                            throw new fabric.worker.AbortException($e360);
                        }
                        finally {
                            if ($commit358) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e360) {
                                    $commit358 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e360) {
                                    $commit358 = false;
                                    fabric.common.TransactionID $currentTid361 =
                                      $tm362.getCurrentTid();
                                    if ($currentTid361 != null) {
                                        if ($e360.tid.equals($currentTid361) ||
                                              !$e360.tid.isDescendantOf(
                                                           $currentTid361)) {
                                            throw $e360;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit358 && $retry359) {
                                { result = result$var356; }
                                continue $label357;
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
                if (useWeakCache) {
                    result = tmp.get$cachedVelocity();
                } else {
                    result = tmp.computeVelocity(false);
                }
            }
            else {
                {
                    double result$var366 = result;
                    fabric.worker.transaction.TransactionManager $tm372 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled375 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff373 = 1;
                    boolean $doBackoff374 = true;
                    boolean $retry369 = true;
                    $label367: for (boolean $commit368 = false; !$commit368; ) {
                        if ($backoffEnabled375) {
                            if ($doBackoff374) {
                                if ($backoff373 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff373);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e370) {
                                            
                                        }
                                    }
                                }
                                if ($backoff373 < 5000) $backoff373 *= 2;
                            }
                            $doBackoff374 = $backoff373 <= 32 || !$doBackoff374;
                        }
                        $commit368 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache) {
                                result = tmp.get$cachedVelocity();
                            } else {
                                result = tmp.computeVelocity(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e370) {
                            $commit368 = false;
                            continue $label367;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e370) {
                            $commit368 = false;
                            fabric.common.TransactionID $currentTid371 =
                              $tm372.getCurrentTid();
                            if ($e370.tid.isDescendantOf($currentTid371))
                                continue $label367;
                            if ($currentTid371.parent != null) {
                                $retry369 = false;
                                throw $e370;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e370) {
                            $commit368 = false;
                            if ($tm372.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid371 =
                              $tm372.getCurrentTid();
                            if ($e370.tid.isDescendantOf($currentTid371)) {
                                $retry369 = true;
                            }
                            else if ($currentTid371.parent != null) {
                                $retry369 = false;
                                throw $e370;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e370) {
                            $commit368 = false;
                            if ($tm372.checkForStaleObjects())
                                continue $label367;
                            $retry369 = false;
                            throw new fabric.worker.AbortException($e370);
                        }
                        finally {
                            if ($commit368) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e370) {
                                    $commit368 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e370) {
                                    $commit368 = false;
                                    fabric.common.TransactionID $currentTid371 =
                                      $tm372.getCurrentTid();
                                    if ($currentTid371 != null) {
                                        if ($e370.tid.equals($currentTid371) ||
                                              !$e370.tid.isDescendantOf(
                                                           $currentTid371)) {
                                            throw $e370;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit368 && $retry369) {
                                { result = result$var366; }
                                continue $label367;
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
                if (useWeakCache) {
                    result = tmp.get$cachedNoise();
                } else {
                    result = tmp.computeNoise(false);
                }
            }
            else {
                {
                    double result$var376 = result;
                    fabric.worker.transaction.TransactionManager $tm382 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled385 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff383 = 1;
                    boolean $doBackoff384 = true;
                    boolean $retry379 = true;
                    $label377: for (boolean $commit378 = false; !$commit378; ) {
                        if ($backoffEnabled385) {
                            if ($doBackoff384) {
                                if ($backoff383 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff383);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e380) {
                                            
                                        }
                                    }
                                }
                                if ($backoff383 < 5000) $backoff383 *= 2;
                            }
                            $doBackoff384 = $backoff383 <= 32 || !$doBackoff384;
                        }
                        $commit378 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache) {
                                result = tmp.get$cachedNoise();
                            } else {
                                result = tmp.computeNoise(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e380) {
                            $commit378 = false;
                            continue $label377;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e380) {
                            $commit378 = false;
                            fabric.common.TransactionID $currentTid381 =
                              $tm382.getCurrentTid();
                            if ($e380.tid.isDescendantOf($currentTid381))
                                continue $label377;
                            if ($currentTid381.parent != null) {
                                $retry379 = false;
                                throw $e380;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e380) {
                            $commit378 = false;
                            if ($tm382.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid381 =
                              $tm382.getCurrentTid();
                            if ($e380.tid.isDescendantOf($currentTid381)) {
                                $retry379 = true;
                            }
                            else if ($currentTid381.parent != null) {
                                $retry379 = false;
                                throw $e380;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e380) {
                            $commit378 = false;
                            if ($tm382.checkForStaleObjects())
                                continue $label377;
                            $retry379 = false;
                            throw new fabric.worker.AbortException($e380);
                        }
                        finally {
                            if ($commit378) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e380) {
                                    $commit378 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e380) {
                                    $commit378 = false;
                                    fabric.common.TransactionID $currentTid381 =
                                      $tm382.getCurrentTid();
                                    if ($currentTid381 != null) {
                                        if ($e380.tid.equals($currentTid381) ||
                                              !$e380.tid.isDescendantOf(
                                                           $currentTid381)) {
                                            throw $e380;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit378 && $retry379) {
                                { result = result$var376; }
                                continue $label377;
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
    
    public static final byte[] $classHash = new byte[] { -27, 39, 95, -1, -94,
    54, -107, -29, -42, 27, 122, -42, -53, -102, 30, -12, -48, 117, 90, -77,
    110, -38, 9, 93, -88, 44, -114, 22, 79, 71, -50, 46 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525439166000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1aC3QU1Rm+u+RNIAkxEUIIIQQQwV3Ax1GjtJCCRDYhJpBirMTJ7E0yZHZmnbkbFijWx7Go7Ul9BASrtFWsD6JYfNSq6aEtCpQerVXRWqueKkc81rZqH9Rjtf9/5+57dpI5Jxzm/3Zn7v/f/33v3czwxyTXNEhdj9StqD62OUxN30qpuynQKhkmDTaqkmmuhbtd8sScpp0nHwjWeIk3QIplSdM1RZbULs1kZHJgozQg+TXK/OvamhquIIUyMq6SzD5GvFcsjxqkNqyrm3tVnYlJMuTvWOAfunND6YEJpKSTlChaO5OYIjfqGqNR1kmKQzTUTQ1zWTBIg52kTKM02E4NRVKVLTBQ1zrJFFPp1SQWMajZRk1dHcCBU8xImBp8zthNVF8HtY2IzHQD1C+11I8wRfUHFJM1BEhej0LVoHk1uYbkBEhujyr1wsDKQMwKP5foX4n3YXiRAmoaPZJMYyw5/YoWZGRmOkfc4vrVMABY80OU9enxqXI0CW6QKZZKqqT1+tuZoWi9MDRXj8AsjFRlFQqDCsKS3C/10i5GpqaPa7UewahC7hZkYaQifRiXBDGrSotZUrQ+brlocKu2SvMSD+gcpLKK+hcAU00aUxvtoQbVZGoxFp8Z2ClVjtzkJQQGV6QNtsb8/NuffH1hzcEj1pjpNmPWdG+kMuuS93ZPfrm6cf4FE1CNgrBuKpgKKZbzqLaKJw3RMGR7ZVwiPvTFHh5se+Hyax+mH3lJURPJk3U1EoKsKpP1UFhRqXEJ1aghMRpsIoVUCzby500kHz4HFI1ad9f09JiUNZEcld/K0/l3cFEPiEAX5cNnRevRY5/DEuvjn6NhQkg+XMQD/08SsuQB+DydkAnnMbLa36eHqL9bjdBNkN5+uKhkyH1+qFtDkf2mIfuNiMYUGCRuQRYBmP52KRRWabCZf/WBGuHxFRdF7Us3eTzg2JmyHqTdkglREhmzvFWFolilq0FqdMnq4EgTKR/ZzbOmEDPdhGzlfvFApKvTe0Qy71Bk+YpPHu06ZmUc8gq3MVJt6egTOvpSdAS1irGWfNCdfNCdhj1RX+Oepn08ZfJMXltxScUg6cKwKrEe3QhFicfDzTqN8/NcgUj3QweBJlE8v/3KS6+6qW4CJGl4Uw7GDYbWp5dMotE0wScJ6qBLLtl+8t/7d27TE8XDSH1GTWdyYk3WpfvI0GUahJ6XEH9mrfRk18i2ei/2k0JodUyCZIS+UZM+R0ptNsT6HHojN0Amog8kFR/FmlMR6zP0TYk7PPaTkUyx0gCdlaYgb5EXt4fveePFD8/mi0esm5Yktd12yhqSKhiFlfBaLUv4fq1BKYz7867WO3Z8vP0K7ngYMdtuwnqkjVC5EpSsbtx45Oo/vvP23le9iWAxkheOdKuKHOW2lH0F/zxwfYkXliHeQIRm3ChaQG28B4Rx5rkJ3aAbqNCRQHWzfp0W0oNKjyJ1qxQz5YuSOYuf/OtgqRVuFe5YzjPIwtEFJO5PW06uPbbhPzVcjEfG1Sjhv8Qwq8WVJyQvMwxpM+oRve4PM3Yflu6BzIcGZSpbKO85hPuD8AAu4b44i9PFac/OQVJneaua3883M9v9Slw3E7nY6R++u6px6UdWxcdzEWXMsqn4DimpTJY8HPqXty7veS/J7ySlfMmWNNYhQdeCNOiERddsFDcDZFLK89QF1FotGuK1Vp1eB0nTpldBotPAZxyNn4usxLcSBxxRik6qhmsGtOuoQL4iloeRnhb1EP7hQs4ym9O5SOZzR3oZyQ8bygBkFiOFSigUYRh7PssCZunAeSpgs8Iji3b5rI0BfzAtvXdZ5Yj0vLiaJajmLLg6CCnuEthso+aKLGrixzORfC2mWmHEpK24fDCbXGg1lBCU84BY+ulNQ7d85RscsurA2h/NztiiJPNYeyQ+1yQ+YRRmmeU0C+dY+cH+bc8+uG27tX+Ykrrar9AioUeO/+93vl3vHrVZS/K7dV2lkpbVfzVwfQv8dkzgczb+u2zM/oOwo/Pa+NBLhY0IzdCfgjo0KOqoSTchk5YInG2jyeVuNVmOXzsc5+yDuXYLvMVmzg1u5+wYfU6NkMnEwkmf2Mwpu52zJeucxTjPHFghLoJiGRZ4h82cffZzwspSGDZ0Bi2HBqNxsV4UO1GIu13gzUliGZzE4Mxjxgr9DLGr2aQb/dSIb27aIpoGVW9tbtrjDPYNIJpFRe6WhG78X57Ybp4r0J+kW1LTJ1iDM7KdDHj97b1+aE9wzf2LvWLlaAGPMD18lkoHqJokqh6rOePk2czPQ4ll4N2PZlzQ2H+i16rmmWkzp49+qHn46CVz5du9ZEK832ccwlKZGlK7fJFB4QyprU3p9bVxXxWiD1rgOoOQ3EILcw4lp0ciqXhFstQUKBAsvxE4ku7mxOrrTeTwMp6uXPQNDmv0jUiuYWSGlTv1ImnqU3bE9QkFt6aaNROu9VABPxC42Z1ZyBIVaGQ3K1nh7zs8G0SyHfK6l7J1sWUG7wXsdMfldwNMfErgCXe6I8v7At8em+47HJ7dieRWRopAd0vxNh7BbJoHoa91CQy40xxZVgtcMTbN9zg8+zGS3cmaL3fUfCNM+4LAx91pjiwHBA6PTfMHHJ49hOTeZM07HDWHze/kswRWudMcWaYJLB+b5o85PDuAZF+y5i1ZNZ8H11ZYGf0Cy9xpjiylAouya+5JrBNW1/mFg/rPInkC1GdSP7Vajd1+JmdAV4J2JtXDdT3o85TAH7ozCVnuEjjkwqTnHUw6jOQgIwWYRXiiwO8jdsrj7msQjgC/ErjLnfLIcqfA27Irn7QUdNj6VtW1Xj7diw5WvYLkaEqg7OziOyDcBuwkpOxugaaDXf0ZuxzOYghUxhSUAJf6poP6byF5jeHPt6FwhNF4YNILhe9p1sIFaTFloYVlH2YxwHbPuJThb0P4C3TaDq5USDsp8M0xxaw0YeAJBwM/QPI2I+XW1F2j2RkP1EOElO8QqGaxM0ugkKVfYLeLQP3NwY5/IPmQkcnCgKSe8FK2UD1CyGnzLCx/31WokPzFJkwo6T2Br7kN0ykH8z5H8ikjFalhcrCSB+p8uA4RUjnHwor/ugsUspwS+PexB8rjyW6JZwLe/IJZP3KDCQHJZOvCQYnRrLH6JlxHwIoRgZvGJVYoaUBgn8tYeSY6WIi/G3hyGZmaGitnQ3m4lsL1DiFTDwvc4S5cyDIk8HsuwuWwh/BUIimBxBNWWBY04d+eBiQ1aye8Ei4ohGmKwBnjEjKUVC1wstuQ1ThYWYtkKiPVqSEb3dh4lX0OJ9itAte4CxuytAh02E1nhG2eg0HzkdQxUhLr51TVZYVtzhowrLEvYZNaZuH0Y+MSMJT0W4HPZTfNPmCLHew7G8kCRirTli0HM2Mrl6eCkJpzBE50FSrOUiTQ4yJUFzqYchGScxNbjBZdMR23GB44BtQcEnjdeMSJS7pWoMMx2j5O33AwbiWSpRnbi6w28v1tFWiyiJDaFwQ+6RCkzP0tZ3lC4KPZjUlWs9nhGVazZxXsy5neHv/T3TI7vWthUkiwWRcLnOtOb2SZI3Dm2PTucHi2HslljExSzHbQWqXtTDe409N/zOCV8TWYeSUhde8JfMxdZSDLfoE/HVMG8ZPyS1zVLgczJCSd0MkivBmvMJkSAuRjbU9HC0ALqJL6GwRK7qKALFcJ7HRR4n0ONmxEIjOSO+B4fGgGgesJmZNrYf3RcaltlHRE4DNua9twsAp/GPOo0LhEbWc1Lh6WECFzXxV4wF1YkOVnAve5CMtWBwO2IRlgJN8c5bDQzEWSeesFVo9LYFDSdIGT3AbmRge7tiP5DpyFRGAczOOh8YES3yVk/iKBTouiTWiQpUhgjovQDDqYcCuSmxkpUkc/HrSC1Ftg7mcEbh2X6KCkLQLV7FbZR2eXg2l3IbkdDkAiOs4W8gDhbuVHUEJ7Bbr7kZyzRAU6rO4ZAfqJgxX3IbkbciwytgNBB0i+j5CF51u44LNxCRJK+lTg+9ktsw/SPgfzHkFyf+K8PbqV8Up6GiCGhe4ChSwx9LoI1BMOljyFZD9sXwZGOwJgHT0HMz8lcFyO2VzSgEDXx+xfOhh2EMnTsDGILT8O9sVXoJcJWXSbQN1dcJBFE+hgSEZwDjvYgCu859ewMdAcN/24/rxGyOJyCxe9Pi6RQUnHBR5xG5mXHax6BcmxxMYgYVwUtqMpfxjEF4em27zCJ14olRsP0b0nVi+syPL63tSMV3wF36N7SgpO37Pudf5CWvxl0cIAKeiJqGryCzZJn/PCBu1RuEMLrddtwtyeN6DNpb7vx/g7tPgJHeA5bo37EyN51jj89hb3Jv8rTxV3UwW0k7TXBi0n8CF8wqqIgS8vD392+qm8grXv8nfHwKu1J+Z1fXXveTveOz59y/Fjd9X88/eRzse1NwuvfHDhYOWaS170/R9Yk0ysVC0AAA==";
}
