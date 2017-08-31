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
    
    public fabric.metrics.util.RunningStats set$updateIntervalStats(
      fabric.metrics.util.RunningStats val);
    
    public fabric.metrics.util.RunningStats get$allStats();
    
    public fabric.metrics.util.RunningStats set$allStats(fabric.metrics.util.RunningStats val);
    
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
        
        public fabric.metrics.util.RunningStats get$allStats() {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).get$allStats(
                                                                    );
        }
        
        public fabric.metrics.util.RunningStats set$allStats(
          fabric.metrics.util.RunningStats val) {
            return ((fabric.metrics.SampledMetric._Impl) fetch()).set$allStats(
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
            this.get$lastUpdate().set(0, -1L);
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
                                    )).fabric$metrics$util$RunningStats$(
                                         java.lang.System.currentTimeMillis()));
            this.set$allStats(
                   ((fabric.metrics.util.RunningStats)
                      new fabric.metrics.util.RunningStats._Impl(
                        this.$getStore(
                               )).$getProxy(
                                    )).fabric$metrics$util$RunningStats$(0));
            fabric$metrics$Metric$();
            return (fabric.metrics.SampledMetric) this.$getProxy();
        }
        
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
                this.get$value().set(0, sample);
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerSample((fabric.metrics.SampledMetric)
                                   this.$getProxy());
            }
        }
        
        public double value(boolean useWeakCache) {
            return (double) this.get$value().get(0);
        }
        
        public double velocity(boolean useWeakCache) {
            return this.get$biasStats().getMean() /
              this.get$updateIntervalStats().getMean();
        }
        
        public double noise(boolean useWeakCache) {
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
        
        public fabric.metrics.util.RunningStats get$allStats() {
            return this.allStats;
        }
        
        public fabric.metrics.util.RunningStats set$allStats(
          fabric.metrics.util.RunningStats val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.allStats = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.util.RunningStats allStats;
        
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
            this.get$allStats().update((newVal -
                                          (double) this.get$value().get(0)) /
                                           (eventTime -
                                              (long)
                                                this.get$lastUpdate().get(0)));
            this.get$lastUpdate().set(0, eventTime);
        }
        
        public double computeValue(boolean useWeakCache) {
            {
                fabric.worker.transaction.TransactionManager $tm242 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff243 = 1;
                boolean $doBackoff244 = true;
                $label238: for (boolean $commit239 = false; !$commit239; ) {
                    if ($doBackoff244) {
                        if ($backoff243 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff243);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e240) {
                                    
                                }
                            }
                        }
                        if ($backoff243 < 5000) $backoff243 *= 1;
                    }
                    $doBackoff244 = $backoff243 <= 32 || !$doBackoff244;
                    $commit239 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { return value(); }
                    catch (final fabric.worker.RetryException $e240) {
                        $commit239 = false;
                        continue $label238;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e240) {
                        $commit239 = false;
                        fabric.common.TransactionID $currentTid241 =
                          $tm242.getCurrentTid();
                        if ($e240.tid.isDescendantOf($currentTid241))
                            continue $label238;
                        if ($currentTid241.parent != null) throw $e240;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e240) {
                        $commit239 = false;
                        if ($tm242.checkForStaleObjects()) continue $label238;
                        throw new fabric.worker.AbortException($e240);
                    }
                    finally {
                        if ($commit239) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e240) {
                                $commit239 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e240) {
                                $commit239 = false;
                                fabric.common.TransactionID $currentTid241 =
                                  $tm242.getCurrentTid();
                                if ($currentTid241 != null) {
                                    if ($e240.tid.equals($currentTid241) ||
                                          !$e240.tid.isDescendantOf(
                                                       $currentTid241)) {
                                        throw $e240;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit239) {
                            {  }
                            continue $label238;
                        }
                    }
                }
            }
            return 0;
        }
        
        public double computeVelocity(boolean useWeakCache) {
            {
                fabric.worker.transaction.TransactionManager $tm249 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff250 = 1;
                boolean $doBackoff251 = true;
                $label245: for (boolean $commit246 = false; !$commit246; ) {
                    if ($doBackoff251) {
                        if ($backoff250 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff250);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e247) {
                                    
                                }
                            }
                        }
                        if ($backoff250 < 5000) $backoff250 *= 1;
                    }
                    $doBackoff251 = $backoff250 <= 32 || !$doBackoff251;
                    $commit246 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { return velocity(); }
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
                            {  }
                            continue $label245;
                        }
                    }
                }
            }
            return 0;
        }
        
        public double computeNoise(boolean useWeakCache) {
            {
                fabric.worker.transaction.TransactionManager $tm256 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff257 = 1;
                boolean $doBackoff258 = true;
                $label252: for (boolean $commit253 = false; !$commit253; ) {
                    if ($doBackoff258) {
                        if ($backoff257 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff257);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e254) {
                                    
                                }
                            }
                        }
                        if ($backoff257 < 5000) $backoff257 *= 1;
                    }
                    $doBackoff258 = $backoff257 <= 32 || !$doBackoff258;
                    $commit253 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { return noise(); }
                    catch (final fabric.worker.RetryException $e254) {
                        $commit253 = false;
                        continue $label252;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e254) {
                        $commit253 = false;
                        fabric.common.TransactionID $currentTid255 =
                          $tm256.getCurrentTid();
                        if ($e254.tid.isDescendantOf($currentTid255))
                            continue $label252;
                        if ($currentTid255.parent != null) throw $e254;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e254) {
                        $commit253 = false;
                        if ($tm256.checkForStaleObjects()) continue $label252;
                        throw new fabric.worker.AbortException($e254);
                    }
                    finally {
                        if ($commit253) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e254) {
                                $commit253 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e254) {
                                $commit253 = false;
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
                        if (!$commit253) {
                            {  }
                            continue $label252;
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
            $writeRef($getStore(), this.value, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.lastUpdate, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.biasStats, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.updateIntervalStats, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.allStats, refTypes, out, intraStoreRefs,
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
            this.allStats =
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
            this.value = src.value;
            this.lastUpdate = src.lastUpdate;
            this.biasStats = src.biasStats;
            this.updateIntervalStats = src.updateIntervalStats;
            this.allStats = src.allStats;
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
    
    public static final byte[] $classHash = new byte[] { -15, 54, -23, -88, 63,
    34, 11, 111, -98, -64, 29, 106, -103, -112, 26, -65, 67, -6, -108, 60, 35,
    -6, 105, 5, -91, -79, 111, 119, 79, 115, -65, 112 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1504028847000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
