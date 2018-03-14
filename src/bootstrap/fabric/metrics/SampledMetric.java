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
                    double result$var200 = result;
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
                        try { result = tmp.get$stats().getValue(); }
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
                                { result = result$var200; }
                                continue $label201;
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
                                rtn = 0;
                            } else {
                                rtn = tmp.get$stats().getSamples();
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
                                rtn = tmp.get$presetV();
                            } else {
                                rtn = tmp.get$stats().getVelocityEstimate();
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
                    double rtn$var230 = rtn;
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
                            if (tmp.get$usePreset()) {
                                rtn = tmp.get$presetN();
                            } else {
                                rtn = tmp.get$stats().getNoiseEstimate();
                            }
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
                                { rtn = rtn$var230; }
                                continue $label231;
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
                                result = (double) tmp.get$weakStats().get(0);
                            else result = tmp.computeValue(false);
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
                                result = (double) tmp.get$weakStats().get(3);
                            else result = tmp.computeSamples(false);
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
                                result = (double) tmp.get$weakStats().get(1);
                            else result = tmp.computeVelocity(false);
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
                    double result$var270 = result;
                    fabric.worker.transaction.TransactionManager $tm276 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled279 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff277 = 1;
                    boolean $doBackoff278 = true;
                    boolean $retry273 = true;
                    $label271: for (boolean $commit272 = false; !$commit272; ) {
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
                        $commit272 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (useWeakCache)
                                result = (double) tmp.get$weakStats().get(2);
                            else result = tmp.computeNoise(false);
                        }
                        catch (final fabric.worker.RetryException $e274) {
                            $commit272 = false;
                            continue $label271;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e274) {
                            $commit272 = false;
                            fabric.common.TransactionID $currentTid275 =
                              $tm276.getCurrentTid();
                            if ($e274.tid.isDescendantOf($currentTid275))
                                continue $label271;
                            if ($currentTid275.parent != null) {
                                $retry273 = false;
                                throw $e274;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e274) {
                            $commit272 = false;
                            if ($tm276.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid275 =
                              $tm276.getCurrentTid();
                            if ($e274.tid.isDescendantOf($currentTid275)) {
                                $retry273 = true;
                            }
                            else if ($currentTid275.parent != null) {
                                $retry273 = false;
                                throw $e274;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e274) {
                            $commit272 = false;
                            if ($tm276.checkForStaleObjects())
                                continue $label271;
                            $retry273 = false;
                            throw new fabric.worker.AbortException($e274);
                        }
                        finally {
                            if ($commit272) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e274) {
                                    $commit272 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e274) {
                                    $commit272 = false;
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
                            if (!$commit272 && $retry273) {
                                { result = result$var270; }
                                continue $label271;
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
    
    public static final byte[] $classHash = new byte[] { 38, -95, -126, 102,
    -23, -83, 105, -57, -105, 34, -101, -87, -24, 46, -52, -23, 123, -86, -19,
    49, 114, 13, 14, 68, -47, -42, -36, -86, 100, 118, -73, -74 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520977993000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
