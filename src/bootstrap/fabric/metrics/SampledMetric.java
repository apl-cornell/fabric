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
            return fabric.metrics.SampledMetric._Impl.
              static_computeValue((fabric.metrics.SampledMetric)
                                    this.$getProxy(), useWeakCache);
        }
        
        private static double static_computeValue(
          fabric.metrics.SampledMetric tmp, boolean useWeakCache) {
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
                        tmp.get$value().set(0, (double) tmp.get$value().get(0));
                        result = (double) tmp.get$value().get(0);
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
            return fabric.metrics.SampledMetric._Impl.
              static_computeVelocity((fabric.metrics.SampledMetric)
                                       this.$getProxy(), useWeakCache);
        }
        
        private static double static_computeVelocity(
          fabric.metrics.SampledMetric tmp, boolean useWeakCache) {
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
                        if (tmp.get$usePreset())
                            result = tmp.get$presetV();
                        else
                            result = tmp.get$biasStats().getMean() /
                                       tmp.get$updateIntervalStats().getMean();
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
            return fabric.metrics.SampledMetric._Impl.
              static_computeNoise((fabric.metrics.SampledMetric)
                                    this.$getProxy(), useWeakCache);
        }
        
        private static double static_computeNoise(
          fabric.metrics.SampledMetric tmp, boolean useWeakCache) {
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
                        if (tmp.get$usePreset()) {
                            result = tmp.get$presetN();
                        }
                        else {
                            double tSqrd =
                              tmp.get$updateIntervalStats().getMean() *
                              tmp.get$updateIntervalStats().getMean();
                            result = 1 /
                                       tmp.get$updateIntervalStats().getMean() +
                                       tmp.get$biasStats().getVar() / tSqrd;
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
            return fabric.metrics.SampledMetric._Impl.
              static_value((fabric.metrics.SampledMetric) this.$getProxy(),
                           useWeakCache);
        }
        
        private static double static_value(fabric.metrics.SampledMetric tmp,
                                           boolean useWeakCache) {
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
                            result = (double) tmp.get$weakStats().get(0); else
                            result = tmp.computeValue(false);
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
            return fabric.metrics.SampledMetric._Impl.
              static_velocity((fabric.metrics.SampledMetric) this.$getProxy(),
                              useWeakCache);
        }
        
        private static double static_velocity(fabric.metrics.SampledMetric tmp,
                                              boolean useWeakCache) {
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
                            result = (double) tmp.get$weakStats().get(1); else
                            result = tmp.computeVelocity(false);
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
            return fabric.metrics.SampledMetric._Impl.
              static_noise((fabric.metrics.SampledMetric) this.$getProxy(),
                           useWeakCache);
        }
        
        private static double static_noise(fabric.metrics.SampledMetric tmp,
                                           boolean useWeakCache) {
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
                            result = (double) tmp.get$weakStats().get(2); else
                            result = tmp.computeNoise(false);
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
    
    public static final byte[] $classHash = new byte[] { 122, 79, 67, 95, -22,
    -20, -86, 4, -49, 2, -111, 122, 114, 20, 73, -128, 89, 96, 110, -100, -68,
    89, -20, -108, 89, -99, 127, -27, -94, 1, 102, -128 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507317673000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
