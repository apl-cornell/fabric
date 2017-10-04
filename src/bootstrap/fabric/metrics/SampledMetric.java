package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.util.List;
import fabric.metrics.util.Observer;
import fabric.metrics.util.RunningStats;
import fabric.common.ConfigProperties;
import fabric.common.exceptions.InternalError;
import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;

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
   * Update to a new value of the metric.
   *
   * @param sample
   *        the value the metric is updating to.
   * @param time
   *        the time the sample occurred.
   */
    public void takeSample(double sample, long time);
    
    public double computeValue(boolean useWeakCache);
    
    public double computeVelocity(boolean useWeakCache);
    
    public double computeNoise(boolean useWeakCache);
    
    public java.lang.String toString();
    
    public boolean isSingleStore();
    
    public fabric.lang.arrays.doubleArray get$value();
    
    public fabric.lang.arrays.doubleArray set$value(
      fabric.lang.arrays.doubleArray val);
    
    public fabric.lang.arrays.longArray get$lastUpdate();
    
    public fabric.lang.arrays.longArray set$lastUpdate(
      fabric.lang.arrays.longArray val);
    
    public fabric.metrics.util.RunningStats get$biasStats();
    
    public fabric.metrics.util.RunningStats set$biasStats(
      fabric.metrics.util.RunningStats val);
    
    public fabric.metrics.util.RunningStats get$updateIntervalStats();
    
    public fabric.metrics.util.RunningStats set$updateIntervalStats(fabric.metrics.util.RunningStats val);
    
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
        
        public fabric.lang.arrays.doubleArray get$value() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$value();
        }
        
        public fabric.lang.arrays.doubleArray set$value(
          fabric.lang.arrays.doubleArray val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$value(
                                                                    val);
        }
        
        public fabric.lang.arrays.longArray get$lastUpdate() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              get$lastUpdate();
        }
        
        public fabric.lang.arrays.longArray set$lastUpdate(
          fabric.lang.arrays.longArray val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              set$lastUpdate(val);
        }
        
        public fabric.metrics.util.RunningStats get$biasStats() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$biasStats(
                                                                    );
        }
        
        public fabric.metrics.util.RunningStats set$biasStats(
          fabric.metrics.util.RunningStats val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$biasStats(
                                                                    val);
        }
        
        public fabric.metrics.util.RunningStats get$updateIntervalStats() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              get$updateIntervalStats();
        }
        
        public fabric.metrics.util.RunningStats set$updateIntervalStats(
          fabric.metrics.util.RunningStats val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).
              set$updateIntervalStats(val);
        }
        
        public fabric.metrics.SampledMetric fabric$metrics$SampledMetric$(
          java.lang.String arg1, double arg2) {
            return ((fabric.metrics.SampledMetric) fetch()).
              fabric$metrics$SampledMetric$(arg1, arg2);
        }
        
        public void takeSample(double arg1) {
            ((fabric.metrics.SampledMetric) fetch()).takeSample(arg1);
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
            this.set$value(
                   (fabric.lang.arrays.doubleArray)
                     new fabric.lang.arrays.doubleArray._Impl(
                       s).fabric$lang$arrays$doubleArray$(lbl, lbl.confPolicy(),
                                                          1).$getProxy());
            this.get$value().set(0, init);
            this.set$lastUpdate(
                   (fabric.lang.arrays.longArray)
                     new fabric.lang.arrays.longArray._Impl(
                       s).fabric$lang$arrays$longArray$(lbl, lbl.confPolicy(),
                                                        1).$getProxy());
            this.get$lastUpdate().set(0,
                                      java.lang.System.currentTimeMillis() - 1);
            this.set$biasStats(
                   ((fabric.metrics.util.RunningStats)
                      new fabric.metrics.util.RunningStats._Impl(
                        this.$getStore(
                               )).$getProxy(
                                    )).fabric$metrics$util$RunningStats$(0));
            this.set$updateIntervalStats(
                   ((fabric.metrics.util.RunningStats)
                      new fabric.metrics.util.RunningStats._Impl(
                        this.$getStore(
                               )).$getProxy(
                                    )).fabric$metrics$util$RunningStats$(1));
            fabric.common.ConfigProperties config =
              fabric.worker.Worker.getWorker().config;
            this.set$usePreset(config.usePreset);
            if (this.get$usePreset()) {
                java.lang.String key = name + "@" + s.name();
                if (!config.rates.containsKey(key))
                    throw new fabric.common.exceptions.InternalError(
                            "Unspecified preset rate for " + key);
                this.set$presetR((double)
                                   ((java.lang.Double)
                                      config.rates.get(key)).doubleValue());
                if (!config.bounds.containsKey(key))
                    throw new fabric.common.exceptions.InternalError(
                            "Unspecified preset bound for " + key);
                this.set$presetB((double)
                                   ((java.lang.Double)
                                      config.bounds.get(key)).doubleValue());
                if (!config.velocities.containsKey(key))
                    throw new fabric.common.exceptions.InternalError(
                            "Unspecified preset velocity for " + key);
                this.set$presetV(
                       (double)
                         ((java.lang.Double)
                            config.velocities.get(key)).doubleValue());
                if (!config.noises.containsKey(key))
                    throw new fabric.common.exceptions.InternalError(
                            "Unspecified preset noise for " + key);
                this.set$presetN((double)
                                   ((java.lang.Double)
                                      config.noises.get(key)).doubleValue());
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
   * Update to a new value of the metric.
   *
   * @param sample
   *        the value the metric is updating to.
   * @param time
   *        the time the sample occurred.
   */
        public void takeSample(double sample, long time) {
            if ((double) this.get$value().get(0) != sample) {
                updateEstimates(sample, time);
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerSample((fabric.metrics.SampledMetric)
                                   this.$getProxy());
            }
        }
        
        public double computeValue(boolean useWeakCache) {
            double result = 0;
            {
                double result$var130 = result;
                fabric.worker.transaction.TransactionManager $tm135 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled138 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff136 = 1;
                boolean $doBackoff137 = true;
                $label131: for (boolean $commit132 = false; !$commit132; ) {
                    if ($backoffEnabled138) {
                        if ($doBackoff137) {
                            if ($backoff136 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff136);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e133) {
                                        
                                    }
                                }
                            }
                            if ($backoff136 < 5000) $backoff136 *= 2;
                        }
                        $doBackoff137 = $backoff136 <= 32 || !$doBackoff137;
                    }
                    $commit132 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { result = (double) this.get$value().get(0); }
                    catch (final fabric.worker.RetryException $e133) {
                        $commit132 = false;
                        continue $label131;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e133) {
                        $commit132 = false;
                        fabric.common.TransactionID $currentTid134 =
                          $tm135.getCurrentTid();
                        if ($e133.tid.isDescendantOf($currentTid134))
                            continue $label131;
                        if ($currentTid134.parent != null) throw $e133;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e133) {
                        $commit132 = false;
                        if ($tm135.checkForStaleObjects()) continue $label131;
                        throw new fabric.worker.AbortException($e133);
                    }
                    finally {
                        if ($commit132) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e133) {
                                $commit132 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e133) {
                                $commit132 = false;
                                fabric.common.TransactionID $currentTid134 =
                                  $tm135.getCurrentTid();
                                if ($currentTid134 != null) {
                                    if ($e133.tid.equals($currentTid134) ||
                                          !$e133.tid.isDescendantOf(
                                                       $currentTid134)) {
                                        throw $e133;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit132) {
                            { result = result$var130; }
                            continue $label131;
                        }
                    }
                }
            }
            return result;
        }
        
        public double computeVelocity(boolean useWeakCache) {
            double result = 0;
            {
                double result$var139 = result;
                fabric.worker.transaction.TransactionManager $tm144 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled147 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff145 = 1;
                boolean $doBackoff146 = true;
                $label140: for (boolean $commit141 = false; !$commit141; ) {
                    if ($backoffEnabled147) {
                        if ($doBackoff146) {
                            if ($backoff145 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff145);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e142) {
                                        
                                    }
                                }
                            }
                            if ($backoff145 < 5000) $backoff145 *= 2;
                        }
                        $doBackoff146 = $backoff145 <= 32 || !$doBackoff146;
                    }
                    $commit141 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (this.get$usePreset())
                            result = this.get$presetV();
                        else
                            result = this.get$biasStats().getMean() /
                                       this.get$updateIntervalStats().getMean();
                    }
                    catch (final fabric.worker.RetryException $e142) {
                        $commit141 = false;
                        continue $label140;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e142) {
                        $commit141 = false;
                        fabric.common.TransactionID $currentTid143 =
                          $tm144.getCurrentTid();
                        if ($e142.tid.isDescendantOf($currentTid143))
                            continue $label140;
                        if ($currentTid143.parent != null) throw $e142;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e142) {
                        $commit141 = false;
                        if ($tm144.checkForStaleObjects()) continue $label140;
                        throw new fabric.worker.AbortException($e142);
                    }
                    finally {
                        if ($commit141) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e142) {
                                $commit141 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e142) {
                                $commit141 = false;
                                fabric.common.TransactionID $currentTid143 =
                                  $tm144.getCurrentTid();
                                if ($currentTid143 != null) {
                                    if ($e142.tid.equals($currentTid143) ||
                                          !$e142.tid.isDescendantOf(
                                                       $currentTid143)) {
                                        throw $e142;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit141) {
                            { result = result$var139; }
                            continue $label140;
                        }
                    }
                }
            }
            return result;
        }
        
        public double computeNoise(boolean useWeakCache) {
            double result = 0;
            {
                double result$var148 = result;
                fabric.worker.transaction.TransactionManager $tm153 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled156 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff154 = 1;
                boolean $doBackoff155 = true;
                $label149: for (boolean $commit150 = false; !$commit150; ) {
                    if ($backoffEnabled156) {
                        if ($doBackoff155) {
                            if ($backoff154 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff154);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e151) {
                                        
                                    }
                                }
                            }
                            if ($backoff154 < 5000) $backoff154 *= 2;
                        }
                        $doBackoff155 = $backoff154 <= 32 || !$doBackoff155;
                    }
                    $commit150 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (this.get$usePreset()) {
                            result = this.get$presetN();
                        }
                        else {
                            double tSqrd =
                              this.get$updateIntervalStats().getMean() *
                              this.get$updateIntervalStats().getMean();
                            result =
                              1 / this.get$updateIntervalStats().getMean() +
                                this.get$biasStats().getVar() / tSqrd;
                        }
                    }
                    catch (final fabric.worker.RetryException $e151) {
                        $commit150 = false;
                        continue $label149;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e151) {
                        $commit150 = false;
                        fabric.common.TransactionID $currentTid152 =
                          $tm153.getCurrentTid();
                        if ($e151.tid.isDescendantOf($currentTid152))
                            continue $label149;
                        if ($currentTid152.parent != null) throw $e151;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e151) {
                        $commit150 = false;
                        if ($tm153.checkForStaleObjects()) continue $label149;
                        throw new fabric.worker.AbortException($e151);
                    }
                    finally {
                        if ($commit150) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e151) {
                                $commit150 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e151) {
                                $commit150 = false;
                                fabric.common.TransactionID $currentTid152 =
                                  $tm153.getCurrentTid();
                                if ($currentTid152 != null) {
                                    if ($e151.tid.equals($currentTid152) ||
                                          !$e151.tid.isDescendantOf(
                                                       $currentTid152)) {
                                        throw $e151;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit150) {
                            { result = result$var148; }
                            continue $label149;
                        }
                    }
                }
            }
            return result;
        }
        
        public java.lang.String toString() {
            return "SampledMetric(" + this.get$name() + ")@" +
            getStore().name();
        }
        
        public boolean isSingleStore() { return true; }
        
        public fabric.lang.arrays.doubleArray get$value() { return this.value; }
        
        public fabric.lang.arrays.doubleArray set$value(
          fabric.lang.arrays.doubleArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.arrays.doubleArray value;
        
        public fabric.lang.arrays.longArray get$lastUpdate() {
            return this.lastUpdate;
        }
        
        public fabric.lang.arrays.longArray set$lastUpdate(
          fabric.lang.arrays.longArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.lastUpdate = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.arrays.longArray lastUpdate;
        
        public fabric.metrics.util.RunningStats get$biasStats() {
            return this.biasStats;
        }
        
        public fabric.metrics.util.RunningStats set$biasStats(
          fabric.metrics.util.RunningStats val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.biasStats = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.util.RunningStats biasStats;
        
        public fabric.metrics.util.RunningStats get$updateIntervalStats() {
            return this.updateIntervalStats;
        }
        
        public fabric.metrics.util.RunningStats set$updateIntervalStats(
          fabric.metrics.util.RunningStats val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.updateIntervalStats = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.util.RunningStats updateIntervalStats;
        
        /**
   * Updates the velocity and interval estimates with the given observation.
   *
   * @param newVal
   *        the new value of the measured quantity
   * @param eventTime
   *        the time, in milliseconds, this update happened
   */
        public void updateEstimates(double newVal, long eventTime) {
            this.get$biasStats().update(newVal -
                                            (double) this.get$value().get(0));
            this.get$updateIntervalStats().update(
                                             eventTime -
                                                 (long)
                                                   this.get$lastUpdate().get(
                                                                           0));
            this.get$lastUpdate().set(0, eventTime);
            this.get$value().set(0, newVal);
        }
        
        public double value(boolean useWeakCache) {
            double result = 0;
            {
                double result$var157 = result;
                fabric.worker.transaction.TransactionManager $tm162 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled165 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff163 = 1;
                boolean $doBackoff164 = true;
                $label158: for (boolean $commit159 = false; !$commit159; ) {
                    if ($backoffEnabled165) {
                        if ($doBackoff164) {
                            if ($backoff163 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff163);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e160) {
                                        
                                    }
                                }
                            }
                            if ($backoff163 < 5000) $backoff163 *= 2;
                        }
                        $doBackoff164 = $backoff163 <= 32 || !$doBackoff164;
                    }
                    $commit159 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (useWeakCache)
                            result = (double) this.get$weakStats().get(0); else
                            result = computeValue(false);
                    }
                    catch (final fabric.worker.RetryException $e160) {
                        $commit159 = false;
                        continue $label158;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e160) {
                        $commit159 = false;
                        fabric.common.TransactionID $currentTid161 =
                          $tm162.getCurrentTid();
                        if ($e160.tid.isDescendantOf($currentTid161))
                            continue $label158;
                        if ($currentTid161.parent != null) throw $e160;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e160) {
                        $commit159 = false;
                        if ($tm162.checkForStaleObjects()) continue $label158;
                        throw new fabric.worker.AbortException($e160);
                    }
                    finally {
                        if ($commit159) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e160) {
                                $commit159 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e160) {
                                $commit159 = false;
                                fabric.common.TransactionID $currentTid161 =
                                  $tm162.getCurrentTid();
                                if ($currentTid161 != null) {
                                    if ($e160.tid.equals($currentTid161) ||
                                          !$e160.tid.isDescendantOf(
                                                       $currentTid161)) {
                                        throw $e160;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit159) {
                            { result = result$var157; }
                            continue $label158;
                        }
                    }
                }
            }
            return result;
        }
        
        public double velocity(boolean useWeakCache) {
            double result = 0;
            {
                double result$var166 = result;
                fabric.worker.transaction.TransactionManager $tm171 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled174 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff172 = 1;
                boolean $doBackoff173 = true;
                $label167: for (boolean $commit168 = false; !$commit168; ) {
                    if ($backoffEnabled174) {
                        if ($doBackoff173) {
                            if ($backoff172 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff172);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e169) {
                                        
                                    }
                                }
                            }
                            if ($backoff172 < 5000) $backoff172 *= 2;
                        }
                        $doBackoff173 = $backoff172 <= 32 || !$doBackoff173;
                    }
                    $commit168 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (useWeakCache)
                            result = (double) this.get$weakStats().get(1); else
                            result = computeVelocity(false);
                    }
                    catch (final fabric.worker.RetryException $e169) {
                        $commit168 = false;
                        continue $label167;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e169) {
                        $commit168 = false;
                        fabric.common.TransactionID $currentTid170 =
                          $tm171.getCurrentTid();
                        if ($e169.tid.isDescendantOf($currentTid170))
                            continue $label167;
                        if ($currentTid170.parent != null) throw $e169;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e169) {
                        $commit168 = false;
                        if ($tm171.checkForStaleObjects()) continue $label167;
                        throw new fabric.worker.AbortException($e169);
                    }
                    finally {
                        if ($commit168) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e169) {
                                $commit168 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e169) {
                                $commit168 = false;
                                fabric.common.TransactionID $currentTid170 =
                                  $tm171.getCurrentTid();
                                if ($currentTid170 != null) {
                                    if ($e169.tid.equals($currentTid170) ||
                                          !$e169.tid.isDescendantOf(
                                                       $currentTid170)) {
                                        throw $e169;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit168) {
                            { result = result$var166; }
                            continue $label167;
                        }
                    }
                }
            }
            return result;
        }
        
        public double noise(boolean useWeakCache) {
            double result = 0;
            {
                double result$var175 = result;
                fabric.worker.transaction.TransactionManager $tm180 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled183 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff181 = 1;
                boolean $doBackoff182 = true;
                $label176: for (boolean $commit177 = false; !$commit177; ) {
                    if ($backoffEnabled183) {
                        if ($doBackoff182) {
                            if ($backoff181 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff181);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e178) {
                                        
                                    }
                                }
                            }
                            if ($backoff181 < 5000) $backoff181 *= 2;
                        }
                        $doBackoff182 = $backoff181 <= 32 || !$doBackoff182;
                    }
                    $commit177 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (useWeakCache)
                            result = (double) this.get$weakStats().get(2); else
                            result = computeNoise(false);
                    }
                    catch (final fabric.worker.RetryException $e178) {
                        $commit177 = false;
                        continue $label176;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e178) {
                        $commit177 = false;
                        fabric.common.TransactionID $currentTid179 =
                          $tm180.getCurrentTid();
                        if ($e178.tid.isDescendantOf($currentTid179))
                            continue $label176;
                        if ($currentTid179.parent != null) throw $e178;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e178) {
                        $commit177 = false;
                        if ($tm180.checkForStaleObjects()) continue $label176;
                        throw new fabric.worker.AbortException($e178);
                    }
                    finally {
                        if ($commit177) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e178) {
                                $commit177 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e178) {
                                $commit177 = false;
                                fabric.common.TransactionID $currentTid179 =
                                  $tm180.getCurrentTid();
                                if ($currentTid179 != null) {
                                    if ($e178.tid.equals($currentTid179) ||
                                          !$e178.tid.isDescendantOf(
                                                       $currentTid179)) {
                                        throw $e178;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit177) {
                            { result = result$var175; }
                            continue $label176;
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
            $writeRef($getStore(), this.value, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.lastUpdate, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.biasStats, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.updateIntervalStats, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.value = (fabric.lang.arrays.doubleArray)
                           $readRef(fabric.lang.arrays.doubleArray._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.lastUpdate =
              (fabric.lang.arrays.longArray)
                $readRef(fabric.lang.arrays.longArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.biasStats =
              (fabric.metrics.util.RunningStats)
                $readRef(fabric.metrics.util.RunningStats._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.updateIntervalStats =
              (fabric.metrics.util.RunningStats)
                $readRef(fabric.metrics.util.RunningStats._Proxy.class,
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
            this.value = src.value;
            this.lastUpdate = src.lastUpdate;
            this.biasStats = src.biasStats;
            this.updateIntervalStats = src.updateIntervalStats;
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
    
    public static final byte[] $classHash = new byte[] { -12, 127, -33, -64,
    -103, -9, 15, 35, 58, -34, -17, -93, 53, 39, 48, -2, 30, -79, 91, 113, 44,
    -111, -68, -86, -120, 57, 14, 119, 38, -54, 93, -78 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507043098000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
