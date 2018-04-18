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
    
    public long computeSamples(boolean useWeakCache);
    
    public long computeLastUpdate(boolean useWeakCache);
    
    public double computeUpdateInterval(boolean useWeakCache);
    
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
                    double result$var197 = result;
                    fabric.worker.transaction.TransactionManager $tm203 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled206 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff204 = 1;
                    boolean $doBackoff205 = true;
                    boolean $retry200 = true;
                    $label198: for (boolean $commit199 = false; !$commit199; ) {
                        if ($backoffEnabled206) {
                            if ($doBackoff205) {
                                if ($backoff204 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff204);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e201) {
                                            
                                        }
                                    }
                                }
                                if ($backoff204 < 5000) $backoff204 *= 2;
                            }
                            $doBackoff205 = $backoff204 <= 32 || !$doBackoff205;
                        }
                        $commit199 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.get$stats().preload(tmp.get$name());
                            result = tmp.get$stats().getValue();
                        }
                        catch (final fabric.worker.RetryException $e201) {
                            $commit199 = false;
                            continue $label198;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e201) {
                            $commit199 = false;
                            fabric.common.TransactionID $currentTid202 =
                              $tm203.getCurrentTid();
                            if ($e201.tid.isDescendantOf($currentTid202))
                                continue $label198;
                            if ($currentTid202.parent != null) {
                                $retry200 = false;
                                throw $e201;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e201) {
                            $commit199 = false;
                            if ($tm203.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid202 =
                              $tm203.getCurrentTid();
                            if ($e201.tid.isDescendantOf($currentTid202)) {
                                $retry200 = true;
                            }
                            else if ($currentTid202.parent != null) {
                                $retry200 = false;
                                throw $e201;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e201) {
                            $commit199 = false;
                            if ($tm203.checkForStaleObjects())
                                continue $label198;
                            $retry200 = false;
                            throw new fabric.worker.AbortException($e201);
                        }
                        finally {
                            if ($commit199) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e201) {
                                    $commit199 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e201) {
                                    $commit199 = false;
                                    fabric.common.TransactionID $currentTid202 =
                                      $tm203.getCurrentTid();
                                    if ($currentTid202 != null) {
                                        if ($e201.tid.equals($currentTid202) ||
                                              !$e201.tid.isDescendantOf(
                                                           $currentTid202)) {
                                            throw $e201;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit199 && $retry200) {
                                { result = result$var197; }
                                continue $label198;
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
                    long rtn$var207 = rtn;
                    fabric.worker.transaction.TransactionManager $tm213 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled216 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff214 = 1;
                    boolean $doBackoff215 = true;
                    boolean $retry210 = true;
                    $label208: for (boolean $commit209 = false; !$commit209; ) {
                        if ($backoffEnabled216) {
                            if ($doBackoff215) {
                                if ($backoff214 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff214);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e211) {
                                            
                                        }
                                    }
                                }
                                if ($backoff214 < 5000) $backoff214 *= 2;
                            }
                            $doBackoff215 = $backoff214 <= 32 || !$doBackoff215;
                        }
                        $commit209 = true;
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
                        catch (final fabric.worker.RetryException $e211) {
                            $commit209 = false;
                            continue $label208;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e211) {
                            $commit209 = false;
                            fabric.common.TransactionID $currentTid212 =
                              $tm213.getCurrentTid();
                            if ($e211.tid.isDescendantOf($currentTid212))
                                continue $label208;
                            if ($currentTid212.parent != null) {
                                $retry210 = false;
                                throw $e211;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e211) {
                            $commit209 = false;
                            if ($tm213.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid212 =
                              $tm213.getCurrentTid();
                            if ($e211.tid.isDescendantOf($currentTid212)) {
                                $retry210 = true;
                            }
                            else if ($currentTid212.parent != null) {
                                $retry210 = false;
                                throw $e211;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e211) {
                            $commit209 = false;
                            if ($tm213.checkForStaleObjects())
                                continue $label208;
                            $retry210 = false;
                            throw new fabric.worker.AbortException($e211);
                        }
                        finally {
                            if ($commit209) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e211) {
                                    $commit209 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e211) {
                                    $commit209 = false;
                                    fabric.common.TransactionID $currentTid212 =
                                      $tm213.getCurrentTid();
                                    if ($currentTid212 != null) {
                                        if ($e211.tid.equals($currentTid212) ||
                                              !$e211.tid.isDescendantOf(
                                                           $currentTid212)) {
                                            throw $e211;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit209 && $retry210) {
                                { rtn = rtn$var207; }
                                continue $label208;
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
                    long rtn$var217 = rtn;
                    fabric.worker.transaction.TransactionManager $tm223 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled226 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff224 = 1;
                    boolean $doBackoff225 = true;
                    boolean $retry220 = true;
                    $label218: for (boolean $commit219 = false; !$commit219; ) {
                        if ($backoffEnabled226) {
                            if ($doBackoff225) {
                                if ($backoff224 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff224);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e221) {
                                            
                                        }
                                    }
                                }
                                if ($backoff224 < 5000) $backoff224 *= 2;
                            }
                            $doBackoff225 = $backoff224 <= 32 || !$doBackoff225;
                        }
                        $commit219 = true;
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
                        catch (final fabric.worker.RetryException $e221) {
                            $commit219 = false;
                            continue $label218;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e221) {
                            $commit219 = false;
                            fabric.common.TransactionID $currentTid222 =
                              $tm223.getCurrentTid();
                            if ($e221.tid.isDescendantOf($currentTid222))
                                continue $label218;
                            if ($currentTid222.parent != null) {
                                $retry220 = false;
                                throw $e221;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e221) {
                            $commit219 = false;
                            if ($tm223.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid222 =
                              $tm223.getCurrentTid();
                            if ($e221.tid.isDescendantOf($currentTid222)) {
                                $retry220 = true;
                            }
                            else if ($currentTid222.parent != null) {
                                $retry220 = false;
                                throw $e221;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e221) {
                            $commit219 = false;
                            if ($tm223.checkForStaleObjects())
                                continue $label218;
                            $retry220 = false;
                            throw new fabric.worker.AbortException($e221);
                        }
                        finally {
                            if ($commit219) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e221) {
                                    $commit219 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e221) {
                                    $commit219 = false;
                                    fabric.common.TransactionID $currentTid222 =
                                      $tm223.getCurrentTid();
                                    if ($currentTid222 != null) {
                                        if ($e221.tid.equals($currentTid222) ||
                                              !$e221.tid.isDescendantOf(
                                                           $currentTid222)) {
                                            throw $e221;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit219 && $retry220) {
                                { rtn = rtn$var217; }
                                continue $label218;
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
                    double rtn$var227 = rtn;
                    fabric.worker.transaction.TransactionManager $tm233 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled236 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff234 = 1;
                    boolean $doBackoff235 = true;
                    boolean $retry230 = true;
                    $label228: for (boolean $commit229 = false; !$commit229; ) {
                        if ($backoffEnabled236) {
                            if ($doBackoff235) {
                                if ($backoff234 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff234);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e231) {
                                            
                                        }
                                    }
                                }
                                if ($backoff234 < 5000) $backoff234 *= 2;
                            }
                            $doBackoff235 = $backoff234 <= 32 || !$doBackoff235;
                        }
                        $commit229 = true;
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
                        catch (final fabric.worker.RetryException $e231) {
                            $commit229 = false;
                            continue $label228;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e231) {
                            $commit229 = false;
                            fabric.common.TransactionID $currentTid232 =
                              $tm233.getCurrentTid();
                            if ($e231.tid.isDescendantOf($currentTid232))
                                continue $label228;
                            if ($currentTid232.parent != null) {
                                $retry230 = false;
                                throw $e231;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e231) {
                            $commit229 = false;
                            if ($tm233.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid232 =
                              $tm233.getCurrentTid();
                            if ($e231.tid.isDescendantOf($currentTid232)) {
                                $retry230 = true;
                            }
                            else if ($currentTid232.parent != null) {
                                $retry230 = false;
                                throw $e231;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e231) {
                            $commit229 = false;
                            if ($tm233.checkForStaleObjects())
                                continue $label228;
                            $retry230 = false;
                            throw new fabric.worker.AbortException($e231);
                        }
                        finally {
                            if ($commit229) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e231) {
                                    $commit229 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e231) {
                                    $commit229 = false;
                                    fabric.common.TransactionID $currentTid232 =
                                      $tm233.getCurrentTid();
                                    if ($currentTid232 != null) {
                                        if ($e231.tid.equals($currentTid232) ||
                                              !$e231.tid.isDescendantOf(
                                                           $currentTid232)) {
                                            throw $e231;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit229 && $retry230) {
                                { rtn = rtn$var227; }
                                continue $label228;
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
                    double rtn$var237 = rtn;
                    fabric.worker.transaction.TransactionManager $tm243 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled246 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff244 = 1;
                    boolean $doBackoff245 = true;
                    boolean $retry240 = true;
                    $label238: for (boolean $commit239 = false; !$commit239; ) {
                        if ($backoffEnabled246) {
                            if ($doBackoff245) {
                                if ($backoff244 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff244);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e241) {
                                            
                                        }
                                    }
                                }
                                if ($backoff244 < 5000) $backoff244 *= 2;
                            }
                            $doBackoff245 = $backoff244 <= 32 || !$doBackoff245;
                        }
                        $commit239 = true;
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
                        catch (final fabric.worker.RetryException $e241) {
                            $commit239 = false;
                            continue $label238;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e241) {
                            $commit239 = false;
                            fabric.common.TransactionID $currentTid242 =
                              $tm243.getCurrentTid();
                            if ($e241.tid.isDescendantOf($currentTid242))
                                continue $label238;
                            if ($currentTid242.parent != null) {
                                $retry240 = false;
                                throw $e241;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e241) {
                            $commit239 = false;
                            if ($tm243.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid242 =
                              $tm243.getCurrentTid();
                            if ($e241.tid.isDescendantOf($currentTid242)) {
                                $retry240 = true;
                            }
                            else if ($currentTid242.parent != null) {
                                $retry240 = false;
                                throw $e241;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e241) {
                            $commit239 = false;
                            if ($tm243.checkForStaleObjects())
                                continue $label238;
                            $retry240 = false;
                            throw new fabric.worker.AbortException($e241);
                        }
                        finally {
                            if ($commit239) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e241) {
                                    $commit239 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e241) {
                                    $commit239 = false;
                                    fabric.common.TransactionID $currentTid242 =
                                      $tm243.getCurrentTid();
                                    if ($currentTid242 != null) {
                                        if ($e241.tid.equals($currentTid242) ||
                                              !$e241.tid.isDescendantOf(
                                                           $currentTid242)) {
                                            throw $e241;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit239 && $retry240) {
                                { rtn = rtn$var237; }
                                continue $label238;
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
                    double rtn$var247 = rtn;
                    fabric.worker.transaction.TransactionManager $tm253 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled256 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff254 = 1;
                    boolean $doBackoff255 = true;
                    boolean $retry250 = true;
                    $label248: for (boolean $commit249 = false; !$commit249; ) {
                        if ($backoffEnabled256) {
                            if ($doBackoff255) {
                                if ($backoff254 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff254);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e251) {
                                            
                                        }
                                    }
                                }
                                if ($backoff254 < 5000) $backoff254 *= 2;
                            }
                            $doBackoff255 = $backoff254 <= 32 || !$doBackoff255;
                        }
                        $commit249 = true;
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
                        catch (final fabric.worker.RetryException $e251) {
                            $commit249 = false;
                            continue $label248;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e251) {
                            $commit249 = false;
                            fabric.common.TransactionID $currentTid252 =
                              $tm253.getCurrentTid();
                            if ($e251.tid.isDescendantOf($currentTid252))
                                continue $label248;
                            if ($currentTid252.parent != null) {
                                $retry250 = false;
                                throw $e251;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e251) {
                            $commit249 = false;
                            if ($tm253.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid252 =
                              $tm253.getCurrentTid();
                            if ($e251.tid.isDescendantOf($currentTid252)) {
                                $retry250 = true;
                            }
                            else if ($currentTid252.parent != null) {
                                $retry250 = false;
                                throw $e251;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e251) {
                            $commit249 = false;
                            if ($tm253.checkForStaleObjects())
                                continue $label248;
                            $retry250 = false;
                            throw new fabric.worker.AbortException($e251);
                        }
                        finally {
                            if ($commit249) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e251) {
                                    $commit249 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e251) {
                                    $commit249 = false;
                                    fabric.common.TransactionID $currentTid252 =
                                      $tm253.getCurrentTid();
                                    if ($currentTid252 != null) {
                                        if ($e251.tid.equals($currentTid252) ||
                                              !$e251.tid.isDescendantOf(
                                                           $currentTid252)) {
                                            throw $e251;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit249 && $retry250) {
                                { rtn = rtn$var247; }
                                continue $label248;
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
                if (useWeakCache) {
                    result = tmp.get$cachedValue();
                } else {
                    result = tmp.computeValue(false);
                }
            }
            else {
                {
                    double result$var257 = result;
                    fabric.worker.transaction.TransactionManager $tm263 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled266 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff264 = 1;
                    boolean $doBackoff265 = true;
                    boolean $retry260 = true;
                    $label258: for (boolean $commit259 = false; !$commit259; ) {
                        if ($backoffEnabled266) {
                            if ($doBackoff265) {
                                if ($backoff264 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff264);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e261) {
                                            
                                        }
                                    }
                                }
                                if ($backoff264 < 5000) $backoff264 *= 2;
                            }
                            $doBackoff265 = $backoff264 <= 32 || !$doBackoff265;
                        }
                        $commit259 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache) {
                                result = tmp.get$cachedValue();
                            } else {
                                result = tmp.computeValue(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e261) {
                            $commit259 = false;
                            continue $label258;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e261) {
                            $commit259 = false;
                            fabric.common.TransactionID $currentTid262 =
                              $tm263.getCurrentTid();
                            if ($e261.tid.isDescendantOf($currentTid262))
                                continue $label258;
                            if ($currentTid262.parent != null) {
                                $retry260 = false;
                                throw $e261;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e261) {
                            $commit259 = false;
                            if ($tm263.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid262 =
                              $tm263.getCurrentTid();
                            if ($e261.tid.isDescendantOf($currentTid262)) {
                                $retry260 = true;
                            }
                            else if ($currentTid262.parent != null) {
                                $retry260 = false;
                                throw $e261;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e261) {
                            $commit259 = false;
                            if ($tm263.checkForStaleObjects())
                                continue $label258;
                            $retry260 = false;
                            throw new fabric.worker.AbortException($e261);
                        }
                        finally {
                            if ($commit259) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e261) {
                                    $commit259 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e261) {
                                    $commit259 = false;
                                    fabric.common.TransactionID $currentTid262 =
                                      $tm263.getCurrentTid();
                                    if ($currentTid262 != null) {
                                        if ($e261.tid.equals($currentTid262) ||
                                              !$e261.tid.isDescendantOf(
                                                           $currentTid262)) {
                                            throw $e261;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit259 && $retry260) {
                                { result = result$var257; }
                                continue $label258;
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
                    long result$var267 = result;
                    fabric.worker.transaction.TransactionManager $tm273 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled276 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff274 = 1;
                    boolean $doBackoff275 = true;
                    boolean $retry270 = true;
                    $label268: for (boolean $commit269 = false; !$commit269; ) {
                        if ($backoffEnabled276) {
                            if ($doBackoff275) {
                                if ($backoff274 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff274);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e271) {
                                            
                                        }
                                    }
                                }
                                if ($backoff274 < 5000) $backoff274 *= 2;
                            }
                            $doBackoff275 = $backoff274 <= 32 || !$doBackoff275;
                        }
                        $commit269 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache) {
                                result = tmp.get$cachedSamples();
                            } else {
                                result = tmp.computeSamples(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e271) {
                            $commit269 = false;
                            continue $label268;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e271) {
                            $commit269 = false;
                            fabric.common.TransactionID $currentTid272 =
                              $tm273.getCurrentTid();
                            if ($e271.tid.isDescendantOf($currentTid272))
                                continue $label268;
                            if ($currentTid272.parent != null) {
                                $retry270 = false;
                                throw $e271;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e271) {
                            $commit269 = false;
                            if ($tm273.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid272 =
                              $tm273.getCurrentTid();
                            if ($e271.tid.isDescendantOf($currentTid272)) {
                                $retry270 = true;
                            }
                            else if ($currentTid272.parent != null) {
                                $retry270 = false;
                                throw $e271;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e271) {
                            $commit269 = false;
                            if ($tm273.checkForStaleObjects())
                                continue $label268;
                            $retry270 = false;
                            throw new fabric.worker.AbortException($e271);
                        }
                        finally {
                            if ($commit269) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e271) {
                                    $commit269 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e271) {
                                    $commit269 = false;
                                    fabric.common.TransactionID $currentTid272 =
                                      $tm273.getCurrentTid();
                                    if ($currentTid272 != null) {
                                        if ($e271.tid.equals($currentTid272) ||
                                              !$e271.tid.isDescendantOf(
                                                           $currentTid272)) {
                                            throw $e271;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit269 && $retry270) {
                                { result = result$var267; }
                                continue $label268;
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
                    long result$var277 = result;
                    fabric.worker.transaction.TransactionManager $tm283 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled286 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff284 = 1;
                    boolean $doBackoff285 = true;
                    boolean $retry280 = true;
                    $label278: for (boolean $commit279 = false; !$commit279; ) {
                        if ($backoffEnabled286) {
                            if ($doBackoff285) {
                                if ($backoff284 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff284);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e281) {
                                            
                                        }
                                    }
                                }
                                if ($backoff284 < 5000) $backoff284 *= 2;
                            }
                            $doBackoff285 = $backoff284 <= 32 || !$doBackoff285;
                        }
                        $commit279 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache) {
                                result = tmp.get$cachedLastUpdate();
                            } else {
                                result = tmp.computeLastUpdate(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e281) {
                            $commit279 = false;
                            continue $label278;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e281) {
                            $commit279 = false;
                            fabric.common.TransactionID $currentTid282 =
                              $tm283.getCurrentTid();
                            if ($e281.tid.isDescendantOf($currentTid282))
                                continue $label278;
                            if ($currentTid282.parent != null) {
                                $retry280 = false;
                                throw $e281;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e281) {
                            $commit279 = false;
                            if ($tm283.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid282 =
                              $tm283.getCurrentTid();
                            if ($e281.tid.isDescendantOf($currentTid282)) {
                                $retry280 = true;
                            }
                            else if ($currentTid282.parent != null) {
                                $retry280 = false;
                                throw $e281;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e281) {
                            $commit279 = false;
                            if ($tm283.checkForStaleObjects())
                                continue $label278;
                            $retry280 = false;
                            throw new fabric.worker.AbortException($e281);
                        }
                        finally {
                            if ($commit279) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e281) {
                                    $commit279 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e281) {
                                    $commit279 = false;
                                    fabric.common.TransactionID $currentTid282 =
                                      $tm283.getCurrentTid();
                                    if ($currentTid282 != null) {
                                        if ($e281.tid.equals($currentTid282) ||
                                              !$e281.tid.isDescendantOf(
                                                           $currentTid282)) {
                                            throw $e281;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit279 && $retry280) {
                                { result = result$var277; }
                                continue $label278;
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
                    double result$var287 = result;
                    fabric.worker.transaction.TransactionManager $tm293 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled296 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff294 = 1;
                    boolean $doBackoff295 = true;
                    boolean $retry290 = true;
                    $label288: for (boolean $commit289 = false; !$commit289; ) {
                        if ($backoffEnabled296) {
                            if ($doBackoff295) {
                                if ($backoff294 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff294);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e291) {
                                            
                                        }
                                    }
                                }
                                if ($backoff294 < 5000) $backoff294 *= 2;
                            }
                            $doBackoff295 = $backoff294 <= 32 || !$doBackoff295;
                        }
                        $commit289 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache) {
                                result = tmp.get$cachedUpdateInterval();
                            } else {
                                result = tmp.computeUpdateInterval(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e291) {
                            $commit289 = false;
                            continue $label288;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e291) {
                            $commit289 = false;
                            fabric.common.TransactionID $currentTid292 =
                              $tm293.getCurrentTid();
                            if ($e291.tid.isDescendantOf($currentTid292))
                                continue $label288;
                            if ($currentTid292.parent != null) {
                                $retry290 = false;
                                throw $e291;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e291) {
                            $commit289 = false;
                            if ($tm293.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid292 =
                              $tm293.getCurrentTid();
                            if ($e291.tid.isDescendantOf($currentTid292)) {
                                $retry290 = true;
                            }
                            else if ($currentTid292.parent != null) {
                                $retry290 = false;
                                throw $e291;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e291) {
                            $commit289 = false;
                            if ($tm293.checkForStaleObjects())
                                continue $label288;
                            $retry290 = false;
                            throw new fabric.worker.AbortException($e291);
                        }
                        finally {
                            if ($commit289) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e291) {
                                    $commit289 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e291) {
                                    $commit289 = false;
                                    fabric.common.TransactionID $currentTid292 =
                                      $tm293.getCurrentTid();
                                    if ($currentTid292 != null) {
                                        if ($e291.tid.equals($currentTid292) ||
                                              !$e291.tid.isDescendantOf(
                                                           $currentTid292)) {
                                            throw $e291;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit289 && $retry290) {
                                { result = result$var287; }
                                continue $label288;
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
                    double result$var297 = result;
                    fabric.worker.transaction.TransactionManager $tm303 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled306 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff304 = 1;
                    boolean $doBackoff305 = true;
                    boolean $retry300 = true;
                    $label298: for (boolean $commit299 = false; !$commit299; ) {
                        if ($backoffEnabled306) {
                            if ($doBackoff305) {
                                if ($backoff304 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff304);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e301) {
                                            
                                        }
                                    }
                                }
                                if ($backoff304 < 5000) $backoff304 *= 2;
                            }
                            $doBackoff305 = $backoff304 <= 32 || !$doBackoff305;
                        }
                        $commit299 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache) {
                                result = tmp.get$cachedVelocity();
                            } else {
                                result = tmp.computeVelocity(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e301) {
                            $commit299 = false;
                            continue $label298;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e301) {
                            $commit299 = false;
                            fabric.common.TransactionID $currentTid302 =
                              $tm303.getCurrentTid();
                            if ($e301.tid.isDescendantOf($currentTid302))
                                continue $label298;
                            if ($currentTid302.parent != null) {
                                $retry300 = false;
                                throw $e301;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e301) {
                            $commit299 = false;
                            if ($tm303.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid302 =
                              $tm303.getCurrentTid();
                            if ($e301.tid.isDescendantOf($currentTid302)) {
                                $retry300 = true;
                            }
                            else if ($currentTid302.parent != null) {
                                $retry300 = false;
                                throw $e301;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e301) {
                            $commit299 = false;
                            if ($tm303.checkForStaleObjects())
                                continue $label298;
                            $retry300 = false;
                            throw new fabric.worker.AbortException($e301);
                        }
                        finally {
                            if ($commit299) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e301) {
                                    $commit299 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e301) {
                                    $commit299 = false;
                                    fabric.common.TransactionID $currentTid302 =
                                      $tm303.getCurrentTid();
                                    if ($currentTid302 != null) {
                                        if ($e301.tid.equals($currentTid302) ||
                                              !$e301.tid.isDescendantOf(
                                                           $currentTid302)) {
                                            throw $e301;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit299 && $retry300) {
                                { result = result$var297; }
                                continue $label298;
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
                    double result$var307 = result;
                    fabric.worker.transaction.TransactionManager $tm313 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled316 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff314 = 1;
                    boolean $doBackoff315 = true;
                    boolean $retry310 = true;
                    $label308: for (boolean $commit309 = false; !$commit309; ) {
                        if ($backoffEnabled316) {
                            if ($doBackoff315) {
                                if ($backoff314 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff314);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e311) {
                                            
                                        }
                                    }
                                }
                                if ($backoff314 < 5000) $backoff314 *= 2;
                            }
                            $doBackoff315 = $backoff314 <= 32 || !$doBackoff315;
                        }
                        $commit309 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache) {
                                result = tmp.get$cachedNoise();
                            } else {
                                result = tmp.computeNoise(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e311) {
                            $commit309 = false;
                            continue $label308;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e311) {
                            $commit309 = false;
                            fabric.common.TransactionID $currentTid312 =
                              $tm313.getCurrentTid();
                            if ($e311.tid.isDescendantOf($currentTid312))
                                continue $label308;
                            if ($currentTid312.parent != null) {
                                $retry310 = false;
                                throw $e311;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e311) {
                            $commit309 = false;
                            if ($tm313.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid312 =
                              $tm313.getCurrentTid();
                            if ($e311.tid.isDescendantOf($currentTid312)) {
                                $retry310 = true;
                            }
                            else if ($currentTid312.parent != null) {
                                $retry310 = false;
                                throw $e311;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e311) {
                            $commit309 = false;
                            if ($tm313.checkForStaleObjects())
                                continue $label308;
                            $retry310 = false;
                            throw new fabric.worker.AbortException($e311);
                        }
                        finally {
                            if ($commit309) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e311) {
                                    $commit309 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e311) {
                                    $commit309 = false;
                                    fabric.common.TransactionID $currentTid312 =
                                      $tm313.getCurrentTid();
                                    if ($currentTid312 != null) {
                                        if ($e311.tid.equals($currentTid312) ||
                                              !$e311.tid.isDescendantOf(
                                                           $currentTid312)) {
                                            throw $e311;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit309 && $retry310) {
                                { result = result$var307; }
                                continue $label308;
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
    
    public static final byte[] $classHash = new byte[] { -37, 86, 16, -80, -77,
    84, -103, -57, -24, 20, 5, 81, -30, 33, -108, 73, -5, 126, 34, 56, 43, -47,
    108, -55, 13, 108, 14, 122, -45, 113, -71, 15 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524080166000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1aDXAV1RW+7+U/hCQQEiEkIYQnFoE8wJ9RYlGSgkRfICaQaqzEzXs3ycK+3cfufeEBotQWQW3TjkaKjuCoWBGjtLW2tEr9qQoMrRa1Kv6gY4cRx9Kp7fgzSrXn3L3vN7ub7EwY7vk2u/fce8797jn33n07dJrkGDqp65G6ZaWebYhQo36p1N0caJV0g4aaFMkwVsLdruC47OYdpx4O1XiJN0CKgpKqqXJQUrpUg5HiwBqpX/KrlPlXtTU3XEsKgqi4TDL6GPFe2xjTSW1EUzb0KhoTnQxr/67Z/sFfrC79TRYp6SQlstrOJCYHmzSV0RjrJEVhGu6murE4FKKhTjJBpTTUTnVZUuSNUFFTO8lEQ+5VJRbVqdFGDU3px4oTjWiE6rzP+E00XwOz9WiQaTqYX2qaH2Wy4g/IBmsIkNwemSohYx25kWQHSE6PIvVCxYpA3As/b9G/FO9D9UIZzNR7pCCNq2SvldUQI9MyNRIe+66ECqCaF6asT0t0la1KcINMNE1SJLXX3850We2FqjlaFHphpNK2UaiUH5GCa6Ve2sXI5Mx6reYjqFXAhwVVGCnPrMZbAs4qMzhLYev08ksGNqnLVC/xgM0hGlTQ/nxQqslQaqM9VKdqkJqKRecGdkgVB7d7CYHK5RmVzTq/v+HTy+bUPHvYrDPVos6K7jU0yLqCe7qLj1U1zbo4C83Ij2iGjFMhzXPOaqt40hCLwGyvSLSID+vjD59te+maLfvoJ15S2Exyg5oSDcOsmhDUwhFZofrlVKW6xGiomRRQNdTEnzeTPLgOyCo1767o6TEoaybZCr+Vq/G/YYh6oAkcojy4ltUeLX4dkVgfv45FCCF5UIgH/p8iZMEBuJ5KSNb5jFzp79PC1N+tROl6mN5+KFTSg31+iFtdDvoNPejXoyqToZK4BbMIwPC3S+GIQkMt/M96MCMyts3F0PrS9R4PDOy0oBai3ZIBLIkZ09iqQFAs05QQ1buCysDBZlJ28G4+awpwphswW/m4eIDpqswckao7GG1c8unjXUfNGYe6YtgYqTJtrBc21qfZCGYVYSzVQ3aqh+w05InVN+1ufpRPmVyDx1aipSJoaWFEkViPpodjxOPhbk3i+nyuANNrIYNAkiia1X7dFddvr8uCSRpZn428QVVfZsgkE00zXEkQB13Bkm2nPt+/Y7OWDB5GfMNiergmxmRd5hjpWpCGIOclmz+3Vnqy6+BmnxfzSQGkOibBZIS8UZPZR1psNsTzHI5GToCMwzGQFHwUT06FrE/X1ifvcO6LUUw0pwEOVoaBPEV+tz2y662XPz6PLx7xbFqSknbbKWtIiWBsrITH6oTk2K/UKYV67+1svfOu09uu5QMPNWZYdehD2QSRK0HIavrWw+uOv39iz+veJFmM5Eai3YocjHFfJnwL/zxQvsGCYYg3ECEZN4kUUJvIARHseWbSNsgGCmQkMN3wrVLDWkjukaVuheJMOVNy9vwn/zlQatKtwB1z8HQyZ+QGkvenNJItR1d/UcOb8QRxNUqOX7KameLKki0v1nVpA9oR++Gr1XcfknbBzIcEZcgbKc85hI8H4QQu4GMxl8v5Gc/OR1FnjlYVv59nDE/3S3HdTM7FTv/QvZVNiz4xIz4xF7GN6RYR3yGlhMmCfeHPvHW5L3pJXicp5Uu2pLIOCbIWTINOWHSNJnEzQManPU9fQM3VoiERa1WZcZDSbWYUJDMNXGNtvC40J745cWAgSnGQqqBUQ7ruF7gGn5ZFUE6KeQi/WMhVZnA5E8UsPpBeRvIiutwPM4uRAjkcjjLknvcym5k2cJ1y2KxwZtGvenNjwB9MycxdZjiivDBhZgmaOR1KByFFusDrLcxcYmMmXp6L4tK4aQVRg7bi8sEs5kKrLochnPvF0k+3D972bf3AoBkH5v5oxrAtSqqOuUfifY3nHcagl+lOvXCNpR/t3/zU3s3bzP3DxPTVfokaDT/2xv/+Ur/zgyMWa0let6YpVFJtx68Gyg9g3E4IfMVi/K4a9fgB7Th4bbzqFcJHhBbITyENEhR1tKSbkPFLBF5oYck1bi1pxD87HPvsg76GBO6y6HO12z47Ru5TJaS4TGC2RZ9Bt30ut+1zIvY5G1aISyBYXhQ4ZNFnn20wF0R0jUHKoaG0rnMgxTAjHsgzM3YtfHa2RVUVYtrcurQnqluHd8zaAA93OpZwiP/LFZvJ8wTOTXEoJaUTjLBqu30/j649Nw/uDq14aL5XrAvLwV+mReYqtJ8qKU35MFaHnStb+GknmeQ/+KT64qa1J3vNWJ2W0XNm7Udaho5cPjN4h5dkJbL5sCNWulJDeg4v1CmcENWVaZm8NjFWBTgGy6F8h5CcPBOzn08lPzllZqAwEqpeVM0XKs8JfCpzmJNrqzc5QxfzycibvtlhBf4xis2MVJszxydmji9tv+tLGrgx3a1pUK6GjPWAwJ+4cwtVbhe41d6tVINvd3j2UxS3wLzupWxVfBHBewEr23FxXQ35ZpyJRV+6sx1VvhD46ehsH3R4tgPFzxgpBNtNw9s4g3aWh8ByXWCXO8tRZbXAq0dn+S6HZ/eh2JlqeaOj5Wug2+MCX3JnOaq8KPCZ0Vn+S4dne1Hcn2p5h6PlsLUtvkzgXHeWo8ocgTNHZ/l+h2e/RrEv1fLltpafA2UTrHuNAn3uLEeVGQKr7S33JNcJM+sccDD/jyieAPOZtJaaqcZqt5Ldr8khK5d8UG4Ge44IfNydS6jymMCHXbj0goNLfCY/w0g+ziI8L+DfT1sZvwDKAGzwXxf4qDvjUWWfwD32xqcsBR2WY6toai/v7q8OXr2K4nAaUVZ+FaHCBVB2EDJhv8BbbPyy3FsosXRPx4lGtgq8YVQ0BXg/bzs49C6K1xm+rg1HoowmqMoMHb7LWQnlHti+XWrihDOjdIkP+yKG74LwjXOGb6Wita8Fnh4Vi6VJB086OPgRihOMlJldd43kZ4K6Rwgp2yvwJocp+eZwolDlRoFRF0T9y8GPf6P4mJFi4UBKlnjZjiqI6UkLTSz7whVVKD60oAlb+lzgKbc0feng3lco/sNIeTpNDl5yoi6C8gIhFXEsckcUqsTRO3qiPB57TzxZePMMM19qgwsByWCrIiGJUVuuvg/lMJjwN4G3jQlX2NKtAje55MozzsFDfE/gyWFkcjpXzo5yuhZBeZ+QyW8L3OuOLlR5WOBuF3Q57Co8FShKYOIJL0wPmvG3pn5Jsc2E10H5ByFTbhA4b0wow5b8Aqe7pazGwctaFJMZqUqnbGRnE1H2FZxpBwR2u6MNVSSBDvvrYbSd4+DQLBR1jJTE8zlVtKDMNtgShjH2DWxbfSZOPTEmhGFL7wl8xd41a8LmO/h3HorZjFRkLFsObsZXLk85ITWXC6xxRRVXqRY4yQVVCx1cuQTFBcktxnJNNhy3GJ4p0PubAgfHgife0p0CHQ7W1jx9z8G5pSgWDdte2PrId7yVYMk8QmqPCzzsQNLwHS9XOSTwOXtnUs1scXi2AsUy2KkzrT3xU91iK7troVOYYNNbBV7szm5UuUjggtHZ3eHwDFOJ5ypGxstGO1it0Ham6XzQM19v8MiAPatnKSF1ZwQecRcZqHJYoMNRO/WYgeJlbmqXgxsSik7IZFGejJcYTA4D8rqW5yV8bwpR4hsUqLtjAVXWCVzrIsT7HHzA32Hw57KcfsfjQws0eDUhZ1eY6Ht7TGIbWzou8Kjb2NYdvMJXZR4FEpeIbVvnErSECZl5UuCf3dGCKs8LfNoFLZscHNiMop+RPGOEw0ILb5KcIwu0e5njjhhsaY7AGnuXrInZ6uDXNhQ3wVlIEOPgHqemHoy4hZBZiwVWuaMGVaYKLHdBzYCDCz9HcSsjhcrIxwNMt7dB30cF2r1UdscOtnS7wM1u2dnp4No9KO6AA5Bgx9lDTtD5YMd9EEJPCrQ7ANkQhCq3CvyRC4Lud/DiQRT3whyLju5A0AEtP0jInGaB2TYuuCMJW8oycfZnbkl61MG9x1A8lDxvj+xlIpIOAFwqsNIdUagyRWCZC6J+6+DJ71Dsh+1L/0hHAIyjp6HnQwK3jwlF2NI2gRvcUvQnB8eeRXEANgbx5cfBv8QKdIyQeQ8I3OKOHFS5SaCDI8PIOeTgA26yPM/DxkB13PTj+vN3Qub7TJx3ysZyd8xgSx8JPO6WmWMOXr2G4mhyY5B0Lgbb0bSfCvFDoakWn+yJD0iDTS/QPSevnFNu87ne5GGf9Aq9x3eX5J+1e9Wb/AO0xMehBQGS3xNVlNQPalKucyM67ZH5gBaYn9dEuD9vQZpL/6Wc8W9m8QoHwPOGWe8dRnLNevjXu3w0eQao5MNUDukk4wd3cxB4Fd5hZVTHj5WH/nvWl7n5Kz/g34rBqNa+01H6qydW3n3o1KScqz6cPtj89Y11F80+phwZrxRvfG3dH0r+D8GKZwVELQAA";
}
