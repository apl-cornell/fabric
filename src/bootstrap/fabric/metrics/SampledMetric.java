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
                double result$var244 = result;
                fabric.worker.transaction.TransactionManager $tm249 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled252 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff250 = 1;
                boolean $doBackoff251 = true;
                $label245: for (boolean $commit246 = false; !$commit246; ) {
                    if ($backoffEnabled252) {
                        if ($doBackoff251) {
                            if ($backoff250 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff250);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e247) {
                                        
                                    }
                                }
                            }
                            if ($backoff250 < 5000) $backoff250 *= 2;
                        }
                        $doBackoff251 = $backoff250 <= 32 || !$doBackoff251;
                    }
                    $commit246 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        this.get$value().set(0,
                                             (double) this.get$value().get(0));
                        result = (double) this.get$value().get(0);
                    }
                    catch (final fabric.worker.RetryException $e247) {
                        $commit246 = false;
                        continue $label245;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e247) {
                        $commit246 = false;
                        fabric.common.TransactionID $currentTid248 =
                          $tm249.getCurrentTid();
                        if ($e247.tid.isDescendantOf($currentTid248))
                            continue $label245;
                        if ($currentTid248.parent != null) throw $e247;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e247) {
                        $commit246 = false;
                        if ($tm249.checkForStaleObjects()) continue $label245;
                        throw new fabric.worker.AbortException($e247);
                    }
                    finally {
                        if ($commit246) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e247) {
                                $commit246 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e247) {
                                $commit246 = false;
                                fabric.common.TransactionID $currentTid248 =
                                  $tm249.getCurrentTid();
                                if ($currentTid248 != null) {
                                    if ($e247.tid.equals($currentTid248) ||
                                          !$e247.tid.isDescendantOf(
                                                       $currentTid248)) {
                                        throw $e247;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit246) {
                            { result = result$var244; }
                            continue $label245;
                        }
                    }
                }
            }
            return result;
        }
        
        public double computeVelocity(boolean useWeakCache) {
            double result = 0;
            {
                double result$var253 = result;
                fabric.worker.transaction.TransactionManager $tm258 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled261 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff259 = 1;
                boolean $doBackoff260 = true;
                $label254: for (boolean $commit255 = false; !$commit255; ) {
                    if ($backoffEnabled261) {
                        if ($doBackoff260) {
                            if ($backoff259 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff259);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e256) {
                                        
                                    }
                                }
                            }
                            if ($backoff259 < 5000) $backoff259 *= 2;
                        }
                        $doBackoff260 = $backoff259 <= 32 || !$doBackoff260;
                    }
                    $commit255 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (this.get$usePreset())
                            result = this.get$presetV();
                        else
                            result = this.get$biasStats().getMean() /
                                       this.get$updateIntervalStats().getMean();
                    }
                    catch (final fabric.worker.RetryException $e256) {
                        $commit255 = false;
                        continue $label254;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e256) {
                        $commit255 = false;
                        fabric.common.TransactionID $currentTid257 =
                          $tm258.getCurrentTid();
                        if ($e256.tid.isDescendantOf($currentTid257))
                            continue $label254;
                        if ($currentTid257.parent != null) throw $e256;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e256) {
                        $commit255 = false;
                        if ($tm258.checkForStaleObjects()) continue $label254;
                        throw new fabric.worker.AbortException($e256);
                    }
                    finally {
                        if ($commit255) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e256) {
                                $commit255 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e256) {
                                $commit255 = false;
                                fabric.common.TransactionID $currentTid257 =
                                  $tm258.getCurrentTid();
                                if ($currentTid257 != null) {
                                    if ($e256.tid.equals($currentTid257) ||
                                          !$e256.tid.isDescendantOf(
                                                       $currentTid257)) {
                                        throw $e256;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit255) {
                            { result = result$var253; }
                            continue $label254;
                        }
                    }
                }
            }
            return result;
        }
        
        public double computeNoise(boolean useWeakCache) {
            double result = 0;
            {
                double result$var262 = result;
                fabric.worker.transaction.TransactionManager $tm267 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled270 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff268 = 1;
                boolean $doBackoff269 = true;
                $label263: for (boolean $commit264 = false; !$commit264; ) {
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
                    $commit264 = true;
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
                    catch (final fabric.worker.RetryException $e265) {
                        $commit264 = false;
                        continue $label263;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e265) {
                        $commit264 = false;
                        fabric.common.TransactionID $currentTid266 =
                          $tm267.getCurrentTid();
                        if ($e265.tid.isDescendantOf($currentTid266))
                            continue $label263;
                        if ($currentTid266.parent != null) throw $e265;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e265) {
                        $commit264 = false;
                        if ($tm267.checkForStaleObjects()) continue $label263;
                        throw new fabric.worker.AbortException($e265);
                    }
                    finally {
                        if ($commit264) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e265) {
                                $commit264 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e265) {
                                $commit264 = false;
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
                        if (!$commit264) {
                            { result = result$var262; }
                            continue $label263;
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
                double result$var271 = result;
                fabric.worker.transaction.TransactionManager $tm276 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled279 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff277 = 1;
                boolean $doBackoff278 = true;
                $label272: for (boolean $commit273 = false; !$commit273; ) {
                    if ($backoffEnabled279) {
                        if ($doBackoff278) {
                            if ($backoff277 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff277);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e274) {
                                        
                                    }
                                }
                            }
                            if ($backoff277 < 5000) $backoff277 *= 2;
                        }
                        $doBackoff278 = $backoff277 <= 32 || !$doBackoff278;
                    }
                    $commit273 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (useWeakCache)
                            result = (double) this.get$weakStats().get(0); else
                            result = computeValue(false);
                    }
                    catch (final fabric.worker.RetryException $e274) {
                        $commit273 = false;
                        continue $label272;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e274) {
                        $commit273 = false;
                        fabric.common.TransactionID $currentTid275 =
                          $tm276.getCurrentTid();
                        if ($e274.tid.isDescendantOf($currentTid275))
                            continue $label272;
                        if ($currentTid275.parent != null) throw $e274;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e274) {
                        $commit273 = false;
                        if ($tm276.checkForStaleObjects()) continue $label272;
                        throw new fabric.worker.AbortException($e274);
                    }
                    finally {
                        if ($commit273) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e274) {
                                $commit273 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e274) {
                                $commit273 = false;
                                fabric.common.TransactionID $currentTid275 =
                                  $tm276.getCurrentTid();
                                if ($currentTid275 != null) {
                                    if ($e274.tid.equals($currentTid275) ||
                                          !$e274.tid.isDescendantOf(
                                                       $currentTid275)) {
                                        throw $e274;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit273) {
                            { result = result$var271; }
                            continue $label272;
                        }
                    }
                }
            }
            return result;
        }
        
        public double velocity(boolean useWeakCache) {
            double result = 0;
            {
                double result$var280 = result;
                fabric.worker.transaction.TransactionManager $tm285 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled288 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff286 = 1;
                boolean $doBackoff287 = true;
                $label281: for (boolean $commit282 = false; !$commit282; ) {
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
                    $commit282 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (useWeakCache)
                            result = (double) this.get$weakStats().get(1); else
                            result = computeVelocity(false);
                    }
                    catch (final fabric.worker.RetryException $e283) {
                        $commit282 = false;
                        continue $label281;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e283) {
                        $commit282 = false;
                        fabric.common.TransactionID $currentTid284 =
                          $tm285.getCurrentTid();
                        if ($e283.tid.isDescendantOf($currentTid284))
                            continue $label281;
                        if ($currentTid284.parent != null) throw $e283;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e283) {
                        $commit282 = false;
                        if ($tm285.checkForStaleObjects()) continue $label281;
                        throw new fabric.worker.AbortException($e283);
                    }
                    finally {
                        if ($commit282) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e283) {
                                $commit282 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e283) {
                                $commit282 = false;
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
                        if (!$commit282) {
                            { result = result$var280; }
                            continue $label281;
                        }
                    }
                }
            }
            return result;
        }
        
        public double noise(boolean useWeakCache) {
            double result = 0;
            {
                double result$var289 = result;
                fabric.worker.transaction.TransactionManager $tm294 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled297 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff295 = 1;
                boolean $doBackoff296 = true;
                $label290: for (boolean $commit291 = false; !$commit291; ) {
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
                    $commit291 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (useWeakCache)
                            result = (double) this.get$weakStats().get(2); else
                            result = computeNoise(false);
                    }
                    catch (final fabric.worker.RetryException $e292) {
                        $commit291 = false;
                        continue $label290;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e292) {
                        $commit291 = false;
                        fabric.common.TransactionID $currentTid293 =
                          $tm294.getCurrentTid();
                        if ($e292.tid.isDescendantOf($currentTid293))
                            continue $label290;
                        if ($currentTid293.parent != null) throw $e292;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e292) {
                        $commit291 = false;
                        if ($tm294.checkForStaleObjects()) continue $label290;
                        throw new fabric.worker.AbortException($e292);
                    }
                    finally {
                        if ($commit291) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e292) {
                                $commit291 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e292) {
                                $commit291 = false;
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
                        if (!$commit291) {
                            { result = result$var289; }
                            continue $label290;
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
    
    public static final byte[] $classHash = new byte[] { 52, 102, -4, 37, 93,
    -57, 31, -86, 93, -103, 119, -91, -10, -106, -55, 68, 42, -72, 49, -52, 74,
    57, -53, -105, 11, 32, -111, 99, 63, 35, 17, -127 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507151083000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
