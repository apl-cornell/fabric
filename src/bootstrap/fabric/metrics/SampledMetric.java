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
                    double result$var190 = result;
                    fabric.worker.transaction.TransactionManager $tm196 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled199 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff197 = 1;
                    boolean $doBackoff198 = true;
                    boolean $retry193 = true;
                    $label191: for (boolean $commit192 = false; !$commit192; ) {
                        if ($backoffEnabled199) {
                            if ($doBackoff198) {
                                if ($backoff197 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff197);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e194) {
                                            
                                        }
                                    }
                                }
                                if ($backoff197 < 5000) $backoff197 *= 2;
                            }
                            $doBackoff198 = $backoff197 <= 32 || !$doBackoff198;
                        }
                        $commit192 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { result = tmp.get$stats().getValue(); }
                        catch (final fabric.worker.RetryException $e194) {
                            $commit192 = false;
                            continue $label191;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e194) {
                            $commit192 = false;
                            fabric.common.TransactionID $currentTid195 =
                              $tm196.getCurrentTid();
                            if ($e194.tid.isDescendantOf($currentTid195))
                                continue $label191;
                            if ($currentTid195.parent != null) {
                                $retry193 = false;
                                throw $e194;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e194) {
                            $commit192 = false;
                            if ($tm196.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid195 =
                              $tm196.getCurrentTid();
                            if ($e194.tid.isDescendantOf($currentTid195)) {
                                $retry193 = true;
                            }
                            else if ($currentTid195.parent != null) {
                                $retry193 = false;
                                throw $e194;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e194) {
                            $commit192 = false;
                            if ($tm196.checkForStaleObjects())
                                continue $label191;
                            $retry193 = false;
                            throw new fabric.worker.AbortException($e194);
                        }
                        finally {
                            if ($commit192) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e194) {
                                    $commit192 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e194) {
                                    $commit192 = false;
                                    fabric.common.TransactionID $currentTid195 =
                                      $tm196.getCurrentTid();
                                    if ($currentTid195 != null) {
                                        if ($e194.tid.equals($currentTid195) ||
                                              !$e194.tid.isDescendantOf(
                                                           $currentTid195)) {
                                            throw $e194;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit192 && $retry193) {
                                { result = result$var190; }
                                continue $label191;
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
                    double rtn$var200 = rtn;
                    fabric.worker.transaction.TransactionManager $tm206 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled209 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff207 = 1;
                    boolean $doBackoff208 = true;
                    boolean $retry203 = true;
                    $label201: for (boolean $commit202 = false; !$commit202; ) {
                        if ($backoffEnabled209) {
                            if ($doBackoff208) {
                                if ($backoff207 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff207);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e204) {
                                            
                                        }
                                    }
                                }
                                if ($backoff207 < 5000) $backoff207 *= 2;
                            }
                            $doBackoff208 = $backoff207 <= 32 || !$doBackoff208;
                        }
                        $commit202 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$usePreset()) {
                                rtn = 0;
                            } else {
                                rtn = tmp.get$stats().getSamples();
                            }
                        }
                        catch (final fabric.worker.RetryException $e204) {
                            $commit202 = false;
                            continue $label201;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e204) {
                            $commit202 = false;
                            fabric.common.TransactionID $currentTid205 =
                              $tm206.getCurrentTid();
                            if ($e204.tid.isDescendantOf($currentTid205))
                                continue $label201;
                            if ($currentTid205.parent != null) {
                                $retry203 = false;
                                throw $e204;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e204) {
                            $commit202 = false;
                            if ($tm206.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid205 =
                              $tm206.getCurrentTid();
                            if ($e204.tid.isDescendantOf($currentTid205)) {
                                $retry203 = true;
                            }
                            else if ($currentTid205.parent != null) {
                                $retry203 = false;
                                throw $e204;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e204) {
                            $commit202 = false;
                            if ($tm206.checkForStaleObjects())
                                continue $label201;
                            $retry203 = false;
                            throw new fabric.worker.AbortException($e204);
                        }
                        finally {
                            if ($commit202) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e204) {
                                    $commit202 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e204) {
                                    $commit202 = false;
                                    fabric.common.TransactionID $currentTid205 =
                                      $tm206.getCurrentTid();
                                    if ($currentTid205 != null) {
                                        if ($e204.tid.equals($currentTid205) ||
                                              !$e204.tid.isDescendantOf(
                                                           $currentTid205)) {
                                            throw $e204;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit202 && $retry203) {
                                { rtn = rtn$var200; }
                                continue $label201;
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
                    double rtn$var210 = rtn;
                    fabric.worker.transaction.TransactionManager $tm216 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled219 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff217 = 1;
                    boolean $doBackoff218 = true;
                    boolean $retry213 = true;
                    $label211: for (boolean $commit212 = false; !$commit212; ) {
                        if ($backoffEnabled219) {
                            if ($doBackoff218) {
                                if ($backoff217 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff217);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e214) {
                                            
                                        }
                                    }
                                }
                                if ($backoff217 < 5000) $backoff217 *= 2;
                            }
                            $doBackoff218 = $backoff217 <= 32 || !$doBackoff218;
                        }
                        $commit212 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$usePreset()) {
                                rtn = tmp.get$presetV();
                            } else {
                                rtn = tmp.get$stats().getVelocityEstimate();
                            }
                        }
                        catch (final fabric.worker.RetryException $e214) {
                            $commit212 = false;
                            continue $label211;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e214) {
                            $commit212 = false;
                            fabric.common.TransactionID $currentTid215 =
                              $tm216.getCurrentTid();
                            if ($e214.tid.isDescendantOf($currentTid215))
                                continue $label211;
                            if ($currentTid215.parent != null) {
                                $retry213 = false;
                                throw $e214;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e214) {
                            $commit212 = false;
                            if ($tm216.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid215 =
                              $tm216.getCurrentTid();
                            if ($e214.tid.isDescendantOf($currentTid215)) {
                                $retry213 = true;
                            }
                            else if ($currentTid215.parent != null) {
                                $retry213 = false;
                                throw $e214;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e214) {
                            $commit212 = false;
                            if ($tm216.checkForStaleObjects())
                                continue $label211;
                            $retry213 = false;
                            throw new fabric.worker.AbortException($e214);
                        }
                        finally {
                            if ($commit212) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e214) {
                                    $commit212 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e214) {
                                    $commit212 = false;
                                    fabric.common.TransactionID $currentTid215 =
                                      $tm216.getCurrentTid();
                                    if ($currentTid215 != null) {
                                        if ($e214.tid.equals($currentTid215) ||
                                              !$e214.tid.isDescendantOf(
                                                           $currentTid215)) {
                                            throw $e214;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit212 && $retry213) {
                                { rtn = rtn$var210; }
                                continue $label211;
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
                    double rtn$var220 = rtn;
                    fabric.worker.transaction.TransactionManager $tm226 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled229 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff227 = 1;
                    boolean $doBackoff228 = true;
                    boolean $retry223 = true;
                    $label221: for (boolean $commit222 = false; !$commit222; ) {
                        if ($backoffEnabled229) {
                            if ($doBackoff228) {
                                if ($backoff227 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff227);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e224) {
                                            
                                        }
                                    }
                                }
                                if ($backoff227 < 5000) $backoff227 *= 2;
                            }
                            $doBackoff228 = $backoff227 <= 32 || !$doBackoff228;
                        }
                        $commit222 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$usePreset()) {
                                rtn = tmp.get$presetN();
                            } else {
                                rtn = tmp.get$stats().getNoiseEstimate();
                            }
                        }
                        catch (final fabric.worker.RetryException $e224) {
                            $commit222 = false;
                            continue $label221;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e224) {
                            $commit222 = false;
                            fabric.common.TransactionID $currentTid225 =
                              $tm226.getCurrentTid();
                            if ($e224.tid.isDescendantOf($currentTid225))
                                continue $label221;
                            if ($currentTid225.parent != null) {
                                $retry223 = false;
                                throw $e224;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e224) {
                            $commit222 = false;
                            if ($tm226.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid225 =
                              $tm226.getCurrentTid();
                            if ($e224.tid.isDescendantOf($currentTid225)) {
                                $retry223 = true;
                            }
                            else if ($currentTid225.parent != null) {
                                $retry223 = false;
                                throw $e224;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e224) {
                            $commit222 = false;
                            if ($tm226.checkForStaleObjects())
                                continue $label221;
                            $retry223 = false;
                            throw new fabric.worker.AbortException($e224);
                        }
                        finally {
                            if ($commit222) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e224) {
                                    $commit222 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e224) {
                                    $commit222 = false;
                                    fabric.common.TransactionID $currentTid225 =
                                      $tm226.getCurrentTid();
                                    if ($currentTid225 != null) {
                                        if ($e224.tid.equals($currentTid225) ||
                                              !$e224.tid.isDescendantOf(
                                                           $currentTid225)) {
                                            throw $e224;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit222 && $retry223) {
                                { rtn = rtn$var220; }
                                continue $label221;
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
                    double result$var230 = result;
                    fabric.worker.transaction.TransactionManager $tm236 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled239 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff237 = 1;
                    boolean $doBackoff238 = true;
                    boolean $retry233 = true;
                    $label231: for (boolean $commit232 = false; !$commit232; ) {
                        if ($backoffEnabled239) {
                            if ($doBackoff238) {
                                if ($backoff237 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff237);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e234) {
                                            
                                        }
                                    }
                                }
                                if ($backoff237 < 5000) $backoff237 *= 2;
                            }
                            $doBackoff238 = $backoff237 <= 32 || !$doBackoff238;
                        }
                        $commit232 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache)
                                result = (double) tmp.get$weakStats().get(0);
                            else result = tmp.computeValue(false);
                        }
                        catch (final fabric.worker.RetryException $e234) {
                            $commit232 = false;
                            continue $label231;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e234) {
                            $commit232 = false;
                            fabric.common.TransactionID $currentTid235 =
                              $tm236.getCurrentTid();
                            if ($e234.tid.isDescendantOf($currentTid235))
                                continue $label231;
                            if ($currentTid235.parent != null) {
                                $retry233 = false;
                                throw $e234;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e234) {
                            $commit232 = false;
                            if ($tm236.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid235 =
                              $tm236.getCurrentTid();
                            if ($e234.tid.isDescendantOf($currentTid235)) {
                                $retry233 = true;
                            }
                            else if ($currentTid235.parent != null) {
                                $retry233 = false;
                                throw $e234;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e234) {
                            $commit232 = false;
                            if ($tm236.checkForStaleObjects())
                                continue $label231;
                            $retry233 = false;
                            throw new fabric.worker.AbortException($e234);
                        }
                        finally {
                            if ($commit232) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e234) {
                                    $commit232 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e234) {
                                    $commit232 = false;
                                    fabric.common.TransactionID $currentTid235 =
                                      $tm236.getCurrentTid();
                                    if ($currentTid235 != null) {
                                        if ($e234.tid.equals($currentTid235) ||
                                              !$e234.tid.isDescendantOf(
                                                           $currentTid235)) {
                                            throw $e234;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit232 && $retry233) {
                                { result = result$var230; }
                                continue $label231;
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
                    double result$var240 = result;
                    fabric.worker.transaction.TransactionManager $tm246 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled249 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff247 = 1;
                    boolean $doBackoff248 = true;
                    boolean $retry243 = true;
                    $label241: for (boolean $commit242 = false; !$commit242; ) {
                        if ($backoffEnabled249) {
                            if ($doBackoff248) {
                                if ($backoff247 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff247);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e244) {
                                            
                                        }
                                    }
                                }
                                if ($backoff247 < 5000) $backoff247 *= 2;
                            }
                            $doBackoff248 = $backoff247 <= 32 || !$doBackoff248;
                        }
                        $commit242 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache)
                                result = (double) tmp.get$weakStats().get(3);
                            else result = tmp.computeSamples(false);
                        }
                        catch (final fabric.worker.RetryException $e244) {
                            $commit242 = false;
                            continue $label241;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e244) {
                            $commit242 = false;
                            fabric.common.TransactionID $currentTid245 =
                              $tm246.getCurrentTid();
                            if ($e244.tid.isDescendantOf($currentTid245))
                                continue $label241;
                            if ($currentTid245.parent != null) {
                                $retry243 = false;
                                throw $e244;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e244) {
                            $commit242 = false;
                            if ($tm246.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid245 =
                              $tm246.getCurrentTid();
                            if ($e244.tid.isDescendantOf($currentTid245)) {
                                $retry243 = true;
                            }
                            else if ($currentTid245.parent != null) {
                                $retry243 = false;
                                throw $e244;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e244) {
                            $commit242 = false;
                            if ($tm246.checkForStaleObjects())
                                continue $label241;
                            $retry243 = false;
                            throw new fabric.worker.AbortException($e244);
                        }
                        finally {
                            if ($commit242) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e244) {
                                    $commit242 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e244) {
                                    $commit242 = false;
                                    fabric.common.TransactionID $currentTid245 =
                                      $tm246.getCurrentTid();
                                    if ($currentTid245 != null) {
                                        if ($e244.tid.equals($currentTid245) ||
                                              !$e244.tid.isDescendantOf(
                                                           $currentTid245)) {
                                            throw $e244;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit242 && $retry243) {
                                { result = result$var240; }
                                continue $label241;
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
                    double result$var250 = result;
                    fabric.worker.transaction.TransactionManager $tm256 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled259 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff257 = 1;
                    boolean $doBackoff258 = true;
                    boolean $retry253 = true;
                    $label251: for (boolean $commit252 = false; !$commit252; ) {
                        if ($backoffEnabled259) {
                            if ($doBackoff258) {
                                if ($backoff257 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff257);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e254) {
                                            
                                        }
                                    }
                                }
                                if ($backoff257 < 5000) $backoff257 *= 2;
                            }
                            $doBackoff258 = $backoff257 <= 32 || !$doBackoff258;
                        }
                        $commit252 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache)
                                result = (double) tmp.get$weakStats().get(1);
                            else result = tmp.computeVelocity(false);
                        }
                        catch (final fabric.worker.RetryException $e254) {
                            $commit252 = false;
                            continue $label251;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e254) {
                            $commit252 = false;
                            fabric.common.TransactionID $currentTid255 =
                              $tm256.getCurrentTid();
                            if ($e254.tid.isDescendantOf($currentTid255))
                                continue $label251;
                            if ($currentTid255.parent != null) {
                                $retry253 = false;
                                throw $e254;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e254) {
                            $commit252 = false;
                            if ($tm256.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid255 =
                              $tm256.getCurrentTid();
                            if ($e254.tid.isDescendantOf($currentTid255)) {
                                $retry253 = true;
                            }
                            else if ($currentTid255.parent != null) {
                                $retry253 = false;
                                throw $e254;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e254) {
                            $commit252 = false;
                            if ($tm256.checkForStaleObjects())
                                continue $label251;
                            $retry253 = false;
                            throw new fabric.worker.AbortException($e254);
                        }
                        finally {
                            if ($commit252) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e254) {
                                    $commit252 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e254) {
                                    $commit252 = false;
                                    fabric.common.TransactionID $currentTid255 =
                                      $tm256.getCurrentTid();
                                    if ($currentTid255 != null) {
                                        if ($e254.tid.equals($currentTid255) ||
                                              !$e254.tid.isDescendantOf(
                                                           $currentTid255)) {
                                            throw $e254;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit252 && $retry253) {
                                { result = result$var250; }
                                continue $label251;
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
                    double result$var260 = result;
                    fabric.worker.transaction.TransactionManager $tm266 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled269 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff267 = 1;
                    boolean $doBackoff268 = true;
                    boolean $retry263 = true;
                    $label261: for (boolean $commit262 = false; !$commit262; ) {
                        if ($backoffEnabled269) {
                            if ($doBackoff268) {
                                if ($backoff267 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff267);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e264) {
                                            
                                        }
                                    }
                                }
                                if ($backoff267 < 5000) $backoff267 *= 2;
                            }
                            $doBackoff268 = $backoff267 <= 32 || !$doBackoff268;
                        }
                        $commit262 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache)
                                result = (double) tmp.get$weakStats().get(2);
                            else result = tmp.computeNoise(false);
                        }
                        catch (final fabric.worker.RetryException $e264) {
                            $commit262 = false;
                            continue $label261;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e264) {
                            $commit262 = false;
                            fabric.common.TransactionID $currentTid265 =
                              $tm266.getCurrentTid();
                            if ($e264.tid.isDescendantOf($currentTid265))
                                continue $label261;
                            if ($currentTid265.parent != null) {
                                $retry263 = false;
                                throw $e264;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e264) {
                            $commit262 = false;
                            if ($tm266.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid265 =
                              $tm266.getCurrentTid();
                            if ($e264.tid.isDescendantOf($currentTid265)) {
                                $retry263 = true;
                            }
                            else if ($currentTid265.parent != null) {
                                $retry263 = false;
                                throw $e264;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e264) {
                            $commit262 = false;
                            if ($tm266.checkForStaleObjects())
                                continue $label261;
                            $retry263 = false;
                            throw new fabric.worker.AbortException($e264);
                        }
                        finally {
                            if ($commit262) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e264) {
                                    $commit262 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e264) {
                                    $commit262 = false;
                                    fabric.common.TransactionID $currentTid265 =
                                      $tm266.getCurrentTid();
                                    if ($currentTid265 != null) {
                                        if ($e264.tid.equals($currentTid265) ||
                                              !$e264.tid.isDescendantOf(
                                                           $currentTid265)) {
                                            throw $e264;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit262 && $retry263) {
                                { result = result$var260; }
                                continue $label261;
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
    
    public static final byte[] $classHash = new byte[] { -8, -33, -30, -89, -37,
    -101, 86, -112, -5, 85, 20, 101, -21, 88, -14, -39, -128, -6, -87, -38,
    -128, -123, 121, 15, 32, -45, 55, 55, 53, 19, 26, -51 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520116324000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
