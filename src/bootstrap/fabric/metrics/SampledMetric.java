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
                tmp.get$stats().preload(tmp.get$name());
                result = tmp.get$stats().getValue();
            }
            else {
                {
                    double result$var0 = result;
                    fabric.worker.transaction.TransactionManager $tm6 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled9 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff7 = 1;
                    boolean $doBackoff8 = true;
                    boolean $retry3 = true;
                    $label1: for (boolean $commit2 = false; !$commit2; ) {
                        if ($backoffEnabled9) {
                            if ($doBackoff8) {
                                if ($backoff7 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff7);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e4) {
                                            
                                        }
                                    }
                                }
                                if ($backoff7 < 5000) $backoff7 *= 2;
                            }
                            $doBackoff8 = $backoff7 <= 32 || !$doBackoff8;
                        }
                        $commit2 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.get$stats().preload(tmp.get$name());
                            result = tmp.get$stats().getValue();
                        }
                        catch (final fabric.worker.RetryException $e4) {
                            $commit2 = false;
                            continue $label1;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e4) {
                            $commit2 = false;
                            fabric.common.TransactionID $currentTid5 =
                              $tm6.getCurrentTid();
                            if ($e4.tid.isDescendantOf($currentTid5))
                                continue $label1;
                            if ($currentTid5.parent != null) {
                                $retry3 = false;
                                throw $e4;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e4) {
                            $commit2 = false;
                            if ($tm6.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid5 =
                              $tm6.getCurrentTid();
                            if ($e4.tid.isDescendantOf($currentTid5)) {
                                $retry3 = true;
                            }
                            else if ($currentTid5.parent != null) {
                                $retry3 = false;
                                throw $e4;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e4) {
                            $commit2 = false;
                            if ($tm6.checkForStaleObjects()) continue $label1;
                            $retry3 = false;
                            throw new fabric.worker.AbortException($e4);
                        }
                        finally {
                            if ($commit2) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.AbortException $e4) {
                                    $commit2 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e4) {
                                    $commit2 = false;
                                    fabric.common.TransactionID $currentTid5 =
                                      $tm6.getCurrentTid();
                                    if ($currentTid5 != null) {
                                        if ($e4.tid.equals($currentTid5) ||
                                              !$e4.tid.isDescendantOf(
                                                         $currentTid5)) {
                                            throw $e4;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit2 && $retry3) {
                                { result = result$var0; }
                                continue $label1;
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
                    tmp.get$stats().preload(tmp.get$name());
                    return tmp.get$stats().getSamples();
                }
            }
            else {
                double rtn = 0;
                {
                    double rtn$var10 = rtn;
                    fabric.worker.transaction.TransactionManager $tm16 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled19 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff17 = 1;
                    boolean $doBackoff18 = true;
                    boolean $retry13 = true;
                    $label11: for (boolean $commit12 = false; !$commit12; ) {
                        if ($backoffEnabled19) {
                            if ($doBackoff18) {
                                if ($backoff17 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff17);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e14) {
                                            
                                        }
                                    }
                                }
                                if ($backoff17 < 5000) $backoff17 *= 2;
                            }
                            $doBackoff18 = $backoff17 <= 32 || !$doBackoff18;
                        }
                        $commit12 = true;
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
                        catch (final fabric.worker.RetryException $e14) {
                            $commit12 = false;
                            continue $label11;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e14) {
                            $commit12 = false;
                            fabric.common.TransactionID $currentTid15 =
                              $tm16.getCurrentTid();
                            if ($e14.tid.isDescendantOf($currentTid15))
                                continue $label11;
                            if ($currentTid15.parent != null) {
                                $retry13 = false;
                                throw $e14;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e14) {
                            $commit12 = false;
                            if ($tm16.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid15 =
                              $tm16.getCurrentTid();
                            if ($e14.tid.isDescendantOf($currentTid15)) {
                                $retry13 = true;
                            }
                            else if ($currentTid15.parent != null) {
                                $retry13 = false;
                                throw $e14;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e14) {
                            $commit12 = false;
                            if ($tm16.checkForStaleObjects()) continue $label11;
                            $retry13 = false;
                            throw new fabric.worker.AbortException($e14);
                        }
                        finally {
                            if ($commit12) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e14) {
                                    $commit12 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e14) {
                                    $commit12 = false;
                                    fabric.common.TransactionID $currentTid15 =
                                      $tm16.getCurrentTid();
                                    if ($currentTid15 != null) {
                                        if ($e14.tid.equals($currentTid15) ||
                                              !$e14.tid.isDescendantOf(
                                                          $currentTid15)) {
                                            throw $e14;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit12 && $retry13) {
                                { rtn = rtn$var10; }
                                continue $label11;
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
                    double rtn$var20 = rtn;
                    fabric.worker.transaction.TransactionManager $tm26 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled29 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff27 = 1;
                    boolean $doBackoff28 = true;
                    boolean $retry23 = true;
                    $label21: for (boolean $commit22 = false; !$commit22; ) {
                        if ($backoffEnabled29) {
                            if ($doBackoff28) {
                                if ($backoff27 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff27);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e24) {
                                            
                                        }
                                    }
                                }
                                if ($backoff27 < 5000) $backoff27 *= 2;
                            }
                            $doBackoff28 = $backoff27 <= 32 || !$doBackoff28;
                        }
                        $commit22 = true;
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
                        catch (final fabric.worker.RetryException $e24) {
                            $commit22 = false;
                            continue $label21;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e24) {
                            $commit22 = false;
                            fabric.common.TransactionID $currentTid25 =
                              $tm26.getCurrentTid();
                            if ($e24.tid.isDescendantOf($currentTid25))
                                continue $label21;
                            if ($currentTid25.parent != null) {
                                $retry23 = false;
                                throw $e24;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e24) {
                            $commit22 = false;
                            if ($tm26.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid25 =
                              $tm26.getCurrentTid();
                            if ($e24.tid.isDescendantOf($currentTid25)) {
                                $retry23 = true;
                            }
                            else if ($currentTid25.parent != null) {
                                $retry23 = false;
                                throw $e24;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e24) {
                            $commit22 = false;
                            if ($tm26.checkForStaleObjects()) continue $label21;
                            $retry23 = false;
                            throw new fabric.worker.AbortException($e24);
                        }
                        finally {
                            if ($commit22) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e24) {
                                    $commit22 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e24) {
                                    $commit22 = false;
                                    fabric.common.TransactionID $currentTid25 =
                                      $tm26.getCurrentTid();
                                    if ($currentTid25 != null) {
                                        if ($e24.tid.equals($currentTid25) ||
                                              !$e24.tid.isDescendantOf(
                                                          $currentTid25)) {
                                            throw $e24;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit22 && $retry23) {
                                { rtn = rtn$var20; }
                                continue $label21;
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
                    double rtn$var30 = rtn;
                    fabric.worker.transaction.TransactionManager $tm36 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled39 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff37 = 1;
                    boolean $doBackoff38 = true;
                    boolean $retry33 = true;
                    $label31: for (boolean $commit32 = false; !$commit32; ) {
                        if ($backoffEnabled39) {
                            if ($doBackoff38) {
                                if ($backoff37 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff37);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e34) {
                                            
                                        }
                                    }
                                }
                                if ($backoff37 < 5000) $backoff37 *= 2;
                            }
                            $doBackoff38 = $backoff37 <= 32 || !$doBackoff38;
                        }
                        $commit32 = true;
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
                        catch (final fabric.worker.RetryException $e34) {
                            $commit32 = false;
                            continue $label31;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e34) {
                            $commit32 = false;
                            fabric.common.TransactionID $currentTid35 =
                              $tm36.getCurrentTid();
                            if ($e34.tid.isDescendantOf($currentTid35))
                                continue $label31;
                            if ($currentTid35.parent != null) {
                                $retry33 = false;
                                throw $e34;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e34) {
                            $commit32 = false;
                            if ($tm36.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid35 =
                              $tm36.getCurrentTid();
                            if ($e34.tid.isDescendantOf($currentTid35)) {
                                $retry33 = true;
                            }
                            else if ($currentTid35.parent != null) {
                                $retry33 = false;
                                throw $e34;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e34) {
                            $commit32 = false;
                            if ($tm36.checkForStaleObjects()) continue $label31;
                            $retry33 = false;
                            throw new fabric.worker.AbortException($e34);
                        }
                        finally {
                            if ($commit32) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e34) {
                                    $commit32 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e34) {
                                    $commit32 = false;
                                    fabric.common.TransactionID $currentTid35 =
                                      $tm36.getCurrentTid();
                                    if ($currentTid35 != null) {
                                        if ($e34.tid.equals($currentTid35) ||
                                              !$e34.tid.isDescendantOf(
                                                          $currentTid35)) {
                                            throw $e34;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit32 && $retry33) {
                                { rtn = rtn$var30; }
                                continue $label31;
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
                    double result$var40 = result;
                    fabric.worker.transaction.TransactionManager $tm46 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled49 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff47 = 1;
                    boolean $doBackoff48 = true;
                    boolean $retry43 = true;
                    $label41: for (boolean $commit42 = false; !$commit42; ) {
                        if ($backoffEnabled49) {
                            if ($doBackoff48) {
                                if ($backoff47 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff47);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e44) {
                                            
                                        }
                                    }
                                }
                                if ($backoff47 < 5000) $backoff47 *= 2;
                            }
                            $doBackoff48 = $backoff47 <= 32 || !$doBackoff48;
                        }
                        $commit42 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache)
                                result = (double) tmp.get$weakStats().get(0);
                            else result = tmp.computeValue(false);
                        }
                        catch (final fabric.worker.RetryException $e44) {
                            $commit42 = false;
                            continue $label41;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e44) {
                            $commit42 = false;
                            fabric.common.TransactionID $currentTid45 =
                              $tm46.getCurrentTid();
                            if ($e44.tid.isDescendantOf($currentTid45))
                                continue $label41;
                            if ($currentTid45.parent != null) {
                                $retry43 = false;
                                throw $e44;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e44) {
                            $commit42 = false;
                            if ($tm46.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid45 =
                              $tm46.getCurrentTid();
                            if ($e44.tid.isDescendantOf($currentTid45)) {
                                $retry43 = true;
                            }
                            else if ($currentTid45.parent != null) {
                                $retry43 = false;
                                throw $e44;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e44) {
                            $commit42 = false;
                            if ($tm46.checkForStaleObjects()) continue $label41;
                            $retry43 = false;
                            throw new fabric.worker.AbortException($e44);
                        }
                        finally {
                            if ($commit42) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e44) {
                                    $commit42 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e44) {
                                    $commit42 = false;
                                    fabric.common.TransactionID $currentTid45 =
                                      $tm46.getCurrentTid();
                                    if ($currentTid45 != null) {
                                        if ($e44.tid.equals($currentTid45) ||
                                              !$e44.tid.isDescendantOf(
                                                          $currentTid45)) {
                                            throw $e44;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit42 && $retry43) {
                                { result = result$var40; }
                                continue $label41;
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
                    double result$var50 = result;
                    fabric.worker.transaction.TransactionManager $tm56 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled59 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff57 = 1;
                    boolean $doBackoff58 = true;
                    boolean $retry53 = true;
                    $label51: for (boolean $commit52 = false; !$commit52; ) {
                        if ($backoffEnabled59) {
                            if ($doBackoff58) {
                                if ($backoff57 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff57);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e54) {
                                            
                                        }
                                    }
                                }
                                if ($backoff57 < 5000) $backoff57 *= 2;
                            }
                            $doBackoff58 = $backoff57 <= 32 || !$doBackoff58;
                        }
                        $commit52 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache)
                                result = (double) tmp.get$weakStats().get(3);
                            else result = tmp.computeSamples(false);
                        }
                        catch (final fabric.worker.RetryException $e54) {
                            $commit52 = false;
                            continue $label51;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e54) {
                            $commit52 = false;
                            fabric.common.TransactionID $currentTid55 =
                              $tm56.getCurrentTid();
                            if ($e54.tid.isDescendantOf($currentTid55))
                                continue $label51;
                            if ($currentTid55.parent != null) {
                                $retry53 = false;
                                throw $e54;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e54) {
                            $commit52 = false;
                            if ($tm56.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid55 =
                              $tm56.getCurrentTid();
                            if ($e54.tid.isDescendantOf($currentTid55)) {
                                $retry53 = true;
                            }
                            else if ($currentTid55.parent != null) {
                                $retry53 = false;
                                throw $e54;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e54) {
                            $commit52 = false;
                            if ($tm56.checkForStaleObjects()) continue $label51;
                            $retry53 = false;
                            throw new fabric.worker.AbortException($e54);
                        }
                        finally {
                            if ($commit52) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e54) {
                                    $commit52 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e54) {
                                    $commit52 = false;
                                    fabric.common.TransactionID $currentTid55 =
                                      $tm56.getCurrentTid();
                                    if ($currentTid55 != null) {
                                        if ($e54.tid.equals($currentTid55) ||
                                              !$e54.tid.isDescendantOf(
                                                          $currentTid55)) {
                                            throw $e54;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit52 && $retry53) {
                                { result = result$var50; }
                                continue $label51;
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
                    double result$var60 = result;
                    fabric.worker.transaction.TransactionManager $tm66 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled69 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff67 = 1;
                    boolean $doBackoff68 = true;
                    boolean $retry63 = true;
                    $label61: for (boolean $commit62 = false; !$commit62; ) {
                        if ($backoffEnabled69) {
                            if ($doBackoff68) {
                                if ($backoff67 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff67);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e64) {
                                            
                                        }
                                    }
                                }
                                if ($backoff67 < 5000) $backoff67 *= 2;
                            }
                            $doBackoff68 = $backoff67 <= 32 || !$doBackoff68;
                        }
                        $commit62 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache)
                                result = (double) tmp.get$weakStats().get(1);
                            else result = tmp.computeVelocity(false);
                        }
                        catch (final fabric.worker.RetryException $e64) {
                            $commit62 = false;
                            continue $label61;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e64) {
                            $commit62 = false;
                            fabric.common.TransactionID $currentTid65 =
                              $tm66.getCurrentTid();
                            if ($e64.tid.isDescendantOf($currentTid65))
                                continue $label61;
                            if ($currentTid65.parent != null) {
                                $retry63 = false;
                                throw $e64;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e64) {
                            $commit62 = false;
                            if ($tm66.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid65 =
                              $tm66.getCurrentTid();
                            if ($e64.tid.isDescendantOf($currentTid65)) {
                                $retry63 = true;
                            }
                            else if ($currentTid65.parent != null) {
                                $retry63 = false;
                                throw $e64;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e64) {
                            $commit62 = false;
                            if ($tm66.checkForStaleObjects()) continue $label61;
                            $retry63 = false;
                            throw new fabric.worker.AbortException($e64);
                        }
                        finally {
                            if ($commit62) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e64) {
                                    $commit62 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e64) {
                                    $commit62 = false;
                                    fabric.common.TransactionID $currentTid65 =
                                      $tm66.getCurrentTid();
                                    if ($currentTid65 != null) {
                                        if ($e64.tid.equals($currentTid65) ||
                                              !$e64.tid.isDescendantOf(
                                                          $currentTid65)) {
                                            throw $e64;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit62 && $retry63) {
                                { result = result$var60; }
                                continue $label61;
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
                    double result$var70 = result;
                    fabric.worker.transaction.TransactionManager $tm76 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled79 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff77 = 1;
                    boolean $doBackoff78 = true;
                    boolean $retry73 = true;
                    $label71: for (boolean $commit72 = false; !$commit72; ) {
                        if ($backoffEnabled79) {
                            if ($doBackoff78) {
                                if ($backoff77 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff77);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e74) {
                                            
                                        }
                                    }
                                }
                                if ($backoff77 < 5000) $backoff77 *= 2;
                            }
                            $doBackoff78 = $backoff77 <= 32 || !$doBackoff78;
                        }
                        $commit72 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache)
                                result = (double) tmp.get$weakStats().get(2);
                            else result = tmp.computeNoise(false);
                        }
                        catch (final fabric.worker.RetryException $e74) {
                            $commit72 = false;
                            continue $label71;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e74) {
                            $commit72 = false;
                            fabric.common.TransactionID $currentTid75 =
                              $tm76.getCurrentTid();
                            if ($e74.tid.isDescendantOf($currentTid75))
                                continue $label71;
                            if ($currentTid75.parent != null) {
                                $retry73 = false;
                                throw $e74;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e74) {
                            $commit72 = false;
                            if ($tm76.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid75 =
                              $tm76.getCurrentTid();
                            if ($e74.tid.isDescendantOf($currentTid75)) {
                                $retry73 = true;
                            }
                            else if ($currentTid75.parent != null) {
                                $retry73 = false;
                                throw $e74;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e74) {
                            $commit72 = false;
                            if ($tm76.checkForStaleObjects()) continue $label71;
                            $retry73 = false;
                            throw new fabric.worker.AbortException($e74);
                        }
                        finally {
                            if ($commit72) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e74) {
                                    $commit72 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e74) {
                                    $commit72 = false;
                                    fabric.common.TransactionID $currentTid75 =
                                      $tm76.getCurrentTid();
                                    if ($currentTid75 != null) {
                                        if ($e74.tid.equals($currentTid75) ||
                                              !$e74.tid.isDescendantOf(
                                                          $currentTid75)) {
                                            throw $e74;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit72 && $retry73) {
                                { result = result$var70; }
                                continue $label71;
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
    
    public static final byte[] $classHash = new byte[] { 105, -47, -20, 38, 11,
    -30, 77, 50, 79, -11, 75, -23, -57, -42, 105, 45, -12, 77, -83, -114, 6, 69,
    31, 79, -81, 56, 113, 78, 79, -7, -102, 23 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1522535845000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
