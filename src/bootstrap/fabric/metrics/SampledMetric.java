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
            this.get$stats().preload(this.get$name());
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

    public static final byte[] $classHash = new byte[] { 24, 126, 102, 31, -116,
    27, -83, -65, 98, -29, 40, -93, -95, -38, 16, -120, 99, -77, 2, -63, 74,
    112, -70, -79, 23, -47, -114, -22, -48, -66, -87, -118 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1522180156000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
