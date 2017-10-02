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
    
    public double value(boolean useWeakCache);
    
    public double velocity(boolean useWeakCache);
    
    public double noise(boolean useWeakCache);
    
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
    
    public double computeValue(boolean useWeakCache);
    
    public double computeVelocity(boolean useWeakCache);
    
    public double computeNoise(boolean useWeakCache);
    
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
        
        public double value(boolean useWeakCache) {
            this.get$value().set(0, (double) this.get$value().get(0));
            return (double) this.get$value().get(0);
        }
        
        public double velocity(boolean useWeakCache) {
            if (this.get$usePreset()) return this.get$presetV();
            return this.get$biasStats().getMean() /
              this.get$updateIntervalStats().getMean();
        }
        
        public double noise(boolean useWeakCache) {
            if (this.get$usePreset()) return this.get$presetN();
            double tSqrd = this.get$updateIntervalStats().getMean() *
              this.get$updateIntervalStats().getMean();
            return 1 / this.get$updateIntervalStats().getMean() +
              this.get$biasStats().getVar() / tSqrd;
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
        
        public double computeValue(boolean useWeakCache) {
            {
                fabric.worker.transaction.TransactionManager $tm119 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff120 = 1;
                boolean $doBackoff121 = true;
                $label115: for (boolean $commit116 = false; !$commit116; ) {
                    if ($doBackoff121) {
                        if ($backoff120 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff120);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e117) {
                                    
                                }
                            }
                        }
                        if ($backoff120 < 5000) $backoff120 *= 1;
                    }
                    $doBackoff121 = $backoff120 <= 32 || !$doBackoff121;
                    $commit116 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { return value(); }
                    catch (final fabric.worker.RetryException $e117) {
                        $commit116 = false;
                        continue $label115;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e117) {
                        $commit116 = false;
                        fabric.common.TransactionID $currentTid118 =
                          $tm119.getCurrentTid();
                        if ($e117.tid.isDescendantOf($currentTid118))
                            continue $label115;
                        if ($currentTid118.parent != null) throw $e117;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e117) {
                        $commit116 = false;
                        if ($tm119.checkForStaleObjects()) continue $label115;
                        throw new fabric.worker.AbortException($e117);
                    }
                    finally {
                        if ($commit116) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e117) {
                                $commit116 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e117) {
                                $commit116 = false;
                                fabric.common.TransactionID $currentTid118 =
                                  $tm119.getCurrentTid();
                                if ($currentTid118 != null) {
                                    if ($e117.tid.equals($currentTid118) ||
                                          !$e117.tid.isDescendantOf(
                                                       $currentTid118)) {
                                        throw $e117;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit116) {
                            {  }
                            continue $label115;
                        }
                    }
                }
            }
            return 0;
        }
        
        public double computeVelocity(boolean useWeakCache) {
            {
                fabric.worker.transaction.TransactionManager $tm126 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff127 = 1;
                boolean $doBackoff128 = true;
                $label122: for (boolean $commit123 = false; !$commit123; ) {
                    if ($doBackoff128) {
                        if ($backoff127 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff127);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e124) {
                                    
                                }
                            }
                        }
                        if ($backoff127 < 5000) $backoff127 *= 1;
                    }
                    $doBackoff128 = $backoff127 <= 32 || !$doBackoff128;
                    $commit123 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { return velocity(); }
                    catch (final fabric.worker.RetryException $e124) {
                        $commit123 = false;
                        continue $label122;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e124) {
                        $commit123 = false;
                        fabric.common.TransactionID $currentTid125 =
                          $tm126.getCurrentTid();
                        if ($e124.tid.isDescendantOf($currentTid125))
                            continue $label122;
                        if ($currentTid125.parent != null) throw $e124;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e124) {
                        $commit123 = false;
                        if ($tm126.checkForStaleObjects()) continue $label122;
                        throw new fabric.worker.AbortException($e124);
                    }
                    finally {
                        if ($commit123) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e124) {
                                $commit123 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e124) {
                                $commit123 = false;
                                fabric.common.TransactionID $currentTid125 =
                                  $tm126.getCurrentTid();
                                if ($currentTid125 != null) {
                                    if ($e124.tid.equals($currentTid125) ||
                                          !$e124.tid.isDescendantOf(
                                                       $currentTid125)) {
                                        throw $e124;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit123) {
                            {  }
                            continue $label122;
                        }
                    }
                }
            }
            return 0;
        }
        
        public double computeNoise(boolean useWeakCache) {
            {
                fabric.worker.transaction.TransactionManager $tm133 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff134 = 1;
                boolean $doBackoff135 = true;
                $label129: for (boolean $commit130 = false; !$commit130; ) {
                    if ($doBackoff135) {
                        if ($backoff134 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff134);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e131) {
                                    
                                }
                            }
                        }
                        if ($backoff134 < 5000) $backoff134 *= 1;
                    }
                    $doBackoff135 = $backoff134 <= 32 || !$doBackoff135;
                    $commit130 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { return noise(); }
                    catch (final fabric.worker.RetryException $e131) {
                        $commit130 = false;
                        continue $label129;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e131) {
                        $commit130 = false;
                        fabric.common.TransactionID $currentTid132 =
                          $tm133.getCurrentTid();
                        if ($e131.tid.isDescendantOf($currentTid132))
                            continue $label129;
                        if ($currentTid132.parent != null) throw $e131;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e131) {
                        $commit130 = false;
                        if ($tm133.checkForStaleObjects()) continue $label129;
                        throw new fabric.worker.AbortException($e131);
                    }
                    finally {
                        if ($commit130) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e131) {
                                $commit130 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e131) {
                                $commit130 = false;
                                fabric.common.TransactionID $currentTid132 =
                                  $tm133.getCurrentTid();
                                if ($currentTid132 != null) {
                                    if ($e131.tid.equals($currentTid132) ||
                                          !$e131.tid.isDescendantOf(
                                                       $currentTid132)) {
                                        throw $e131;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit130) {
                            {  }
                            continue $label129;
                        }
                    }
                }
            }
            return 0;
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
    
    public static final byte[] $classHash = new byte[] { -61, 77, 63, -90, 116,
    11, 50, 104, 43, -107, -75, -37, -104, 107, -107, -68, -44, 7, -69, -44,
    112, 49, -30, 65, 69, 49, -61, -95, 2, -66, -48, 62 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506965915000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
