package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Arrays;
import fabric.util.Iterator;
import fabric.util.LinkedHashMap;
import fabric.util.Map;
import fabric.metrics.contracts.Bound;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.enforcement.DirectPolicy;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.metrics.treaties.enforcement.WitnessPolicy;
import fabric.worker.metrics.treaties.statements.EqualityStatement;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;

/**
 * A {@link DerivedMetric} for the scaled value of a given metric.
 */
public interface ScaledMetric extends fabric.metrics.DerivedMetric {
    public double get$scalar();
    
    public double set$scalar(double val);
    
    public double postInc$scalar();
    
    public double postDec$scalar();
    
    /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param scalar
     *            The coefficient as a double
     * @param term
     *            The {@link Metric} this applies to
     */
    public fabric.metrics.ScaledMetric fabric$metrics$ScaledMetric$(
      double scalar, fabric.metrics.Metric term);
    
    public double computePresetR();
    
    public double computePresetB();
    
    public double computePresetV();
    
    public double computePresetN();
    
    public double computeValue(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeVelocity(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoise(fabric.worker.metrics.StatsMap weakStats);
    
    public java.lang.String toString();
    
    public fabric.metrics.DerivedMetric times(double otherScalar);
    
    /**
     * {@inheritDoc}
     * <p>
     * {@link ScaledMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      thresholdPolicy(double rate, double base,
                      fabric.worker.metrics.StatsMap weakStats,
                      final fabric.worker.Store s);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy extends fabric.metrics.DerivedMetric._Proxy
      implements fabric.metrics.ScaledMetric {
        public double get$scalar() {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).get$scalar();
        }
        
        public double set$scalar(double val) {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).set$scalar(
                                                                   val);
        }
        
        public double postInc$scalar() {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).postInc$scalar(
                                                                   );
        }
        
        public double postDec$scalar() {
            return ((fabric.metrics.ScaledMetric._Impl) fetch()).postDec$scalar(
                                                                   );
        }
        
        public fabric.metrics.ScaledMetric fabric$metrics$ScaledMetric$(
          double arg1, fabric.metrics.Metric arg2) {
            return ((fabric.metrics.ScaledMetric) fetch()).
              fabric$metrics$ScaledMetric$(arg1, arg2);
        }
        
        public _Proxy(ScaledMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.DerivedMetric._Impl
      implements fabric.metrics.ScaledMetric {
        public double get$scalar() { return this.scalar; }
        
        public double set$scalar(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.scalar = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$scalar() {
            double tmp = this.get$scalar();
            this.set$scalar((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$scalar() {
            double tmp = this.get$scalar();
            this.set$scalar((double) (tmp - 1));
            return tmp;
        }
        
        protected double scalar;
        
        /**
     * @param store
     *            the {@link Store} that holds this {@link Metric}
     * @param scalar
     *            The coefficient as a double
     * @param term
     *            The {@link Metric} this applies to
     */
        public fabric.metrics.ScaledMetric fabric$metrics$ScaledMetric$(
          double scalar, fabric.metrics.Metric term) {
            this.set$scalar((double) scalar);
            fabric$metrics$DerivedMetric$(new fabric.metrics.Metric[] { term });
            initialize();
            return (fabric.metrics.ScaledMetric) this.$getProxy();
        }
        
        public double computePresetR() {
            return this.get$scalar() * term(0).getPresetR();
        }
        
        public double computePresetB() {
            return this.get$scalar() * term(0).getPresetB();
        }
        
        public double computePresetV() {
            return this.get$scalar() * term(0).getPresetV();
        }
        
        public double computePresetN() {
            return this.get$scalar() * this.get$scalar() * term(0).getPresetN();
        }
        
        public double computeValue(fabric.worker.metrics.StatsMap weakStats) {
            return this.get$scalar() * term(0).value(weakStats);
        }
        
        public double computeVelocity(
          fabric.worker.metrics.StatsMap weakStats) {
            return this.get$scalar() * term(0).velocity(weakStats);
        }
        
        public double computeNoise(fabric.worker.metrics.StatsMap weakStats) {
            return this.get$scalar() * this.get$scalar() *
              term(0).noise(weakStats);
        }
        
        public java.lang.String toString() {
            return "(" +
            this.get$scalar() +
            "*" +
            java.lang.String.valueOf(
                               fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                   term(0))) +
            ")@" +
            getStore();
        }
        
        public fabric.metrics.DerivedMetric times(double otherScalar) {
            return fabric.metrics.ScaledMetric._Impl.
              static_times((fabric.metrics.ScaledMetric) this.$getProxy(),
                           otherScalar);
        }
        
        private static fabric.metrics.DerivedMetric static_times(
          fabric.metrics.ScaledMetric tmp, double otherScalar) {
            final fabric.worker.Store s = tmp.$getStore();
            fabric.metrics.DerivedMetric val = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                val =
                  ((fabric.metrics.ScaledMetric)
                     new fabric.metrics.ScaledMetric._Impl(s).$getProxy()).
                    fabric$metrics$ScaledMetric$(otherScalar * tmp.get$scalar(),
                                                 tmp.term(0));
            }
            else {
                {
                    fabric.metrics.DerivedMetric val$var406 = val;
                    fabric.worker.transaction.TransactionManager $tm413 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled416 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff414 = 1;
                    boolean $doBackoff415 = true;
                    boolean $retry409 = true;
                    boolean $keepReads410 = false;
                    $label407: for (boolean $commit408 = false; !$commit408; ) {
                        if ($backoffEnabled416) {
                            if ($doBackoff415) {
                                if ($backoff414 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff414));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e411) {
                                            
                                        }
                                    }
                                }
                                if ($backoff414 < 5000) $backoff414 *= 2;
                            }
                            $doBackoff415 = $backoff414 <= 32 || !$doBackoff415;
                        }
                        $commit408 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                val =
                                  ((fabric.metrics.ScaledMetric)
                                     new fabric.metrics.ScaledMetric._Impl(s).
                                     $getProxy()).fabric$metrics$ScaledMetric$(
                                                    otherScalar *
                                                        tmp.get$scalar(),
                                                    tmp.term(0));
                            }
                            catch (final fabric.worker.RetryException $e411) {
                                throw $e411;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e411) {
                                throw $e411;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e411) {
                                throw $e411;
                            }
                            catch (final Throwable $e411) {
                                $tm413.getCurrentLog().checkRetrySignal();
                                throw $e411;
                            }
                        }
                        catch (final fabric.worker.RetryException $e411) {
                            $commit408 = false;
                            continue $label407;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e411) {
                            $commit408 = false;
                            $retry409 = false;
                            $keepReads410 = $e411.keepReads;
                            if ($tm413.checkForStaleObjects()) {
                                $retry409 = true;
                                $keepReads410 = false;
                                continue $label407;
                            }
                            fabric.common.TransactionID $currentTid412 =
                              $tm413.getCurrentTid();
                            if ($e411.tid == null ||
                                  !$e411.tid.isDescendantOf($currentTid412)) {
                                throw $e411;
                            }
                            throw new fabric.worker.UserAbortException($e411);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e411) {
                            $commit408 = false;
                            fabric.common.TransactionID $currentTid412 =
                              $tm413.getCurrentTid();
                            if ($e411.tid.isDescendantOf($currentTid412))
                                continue $label407;
                            if ($currentTid412.parent != null) {
                                $retry409 = false;
                                throw $e411;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e411) {
                            $commit408 = false;
                            if ($tm413.checkForStaleObjects())
                                continue $label407;
                            $retry409 = false;
                            throw new fabric.worker.AbortException($e411);
                        }
                        finally {
                            if ($commit408) {
                                fabric.common.TransactionID $currentTid412 =
                                  $tm413.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e411) {
                                    $commit408 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e411) {
                                    $commit408 = false;
                                    $retry409 = false;
                                    $keepReads410 = $e411.keepReads;
                                    if ($tm413.checkForStaleObjects()) {
                                        $retry409 = true;
                                        $keepReads410 = false;
                                        continue $label407;
                                    }
                                    if ($e411.tid ==
                                          null ||
                                          !$e411.tid.isDescendantOf(
                                                       $currentTid412))
                                        throw $e411;
                                    throw new fabric.worker.UserAbortException(
                                            $e411);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e411) {
                                    $commit408 = false;
                                    $currentTid412 = $tm413.getCurrentTid();
                                    if ($currentTid412 != null) {
                                        if ($e411.tid.equals($currentTid412) ||
                                              !$e411.tid.isDescendantOf(
                                                           $currentTid412)) {
                                            throw $e411;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads410) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit408) {
                                { val = val$var406; }
                                if ($retry409) { continue $label407; }
                            }
                        }
                    }
                }
            }
            return val;
        }
        
        /**
     * {@inheritDoc}
     * <p>
     * {@link ScaledMetric}s try to consolidate local computations so that there
     * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
     * {@link #handleUpdates()}.
     */
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            return fabric.metrics.ScaledMetric._Impl.
              static_plus((fabric.metrics.ScaledMetric) this.$getProxy(),
                          other);
        }
        
        private static fabric.metrics.DerivedMetric static_plus(
          fabric.metrics.ScaledMetric tmp, fabric.metrics.Metric other) {
            final fabric.worker.Store s = tmp.$getStore();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.ScaledMetric &&
                  other.$getStore().equals(s) &&
                  ((fabric.metrics.ScaledMetric)
                     fabric.lang.Object._Proxy.$getProxy(other)).term(0).
                  equals(tmp.term(0))) {
                fabric.metrics.ScaledMetric that =
                  (fabric.metrics.ScaledMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                fabric.metrics.DerivedMetric val = null;
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    val =
                      ((fabric.metrics.ScaledMetric)
                         new fabric.metrics.ScaledMetric._Impl(s).$getProxy()).
                        fabric$metrics$ScaledMetric$(that.get$scalar() +
                                                         tmp.get$scalar(),
                                                     tmp.term(0));
                }
                else {
                    {
                        fabric.metrics.DerivedMetric val$var417 = val;
                        fabric.worker.transaction.TransactionManager $tm424 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled427 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff425 = 1;
                        boolean $doBackoff426 = true;
                        boolean $retry420 = true;
                        boolean $keepReads421 = false;
                        $label418: for (boolean $commit419 = false; !$commit419;
                                        ) {
                            if ($backoffEnabled427) {
                                if ($doBackoff426) {
                                    if ($backoff425 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff425));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e422) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff425 < 5000) $backoff425 *= 2;
                                }
                                $doBackoff426 = $backoff425 <= 32 ||
                                                  !$doBackoff426;
                            }
                            $commit419 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    val =
                                      ((fabric.metrics.ScaledMetric)
                                         new fabric.metrics.ScaledMetric._Impl(
                                           s).$getProxy()).
                                        fabric$metrics$ScaledMetric$(
                                          that.get$scalar() + tmp.get$scalar(),
                                          tmp.term(0));
                                }
                                catch (final fabric.worker.
                                         RetryException $e422) {
                                    throw $e422;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e422) {
                                    throw $e422;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e422) {
                                    throw $e422;
                                }
                                catch (final Throwable $e422) {
                                    $tm424.getCurrentLog().checkRetrySignal();
                                    throw $e422;
                                }
                            }
                            catch (final fabric.worker.RetryException $e422) {
                                $commit419 = false;
                                continue $label418;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e422) {
                                $commit419 = false;
                                $retry420 = false;
                                $keepReads421 = $e422.keepReads;
                                if ($tm424.checkForStaleObjects()) {
                                    $retry420 = true;
                                    $keepReads421 = false;
                                    continue $label418;
                                }
                                fabric.common.TransactionID $currentTid423 =
                                  $tm424.getCurrentTid();
                                if ($e422.tid ==
                                      null ||
                                      !$e422.tid.isDescendantOf(
                                                   $currentTid423)) {
                                    throw $e422;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e422);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e422) {
                                $commit419 = false;
                                fabric.common.TransactionID $currentTid423 =
                                  $tm424.getCurrentTid();
                                if ($e422.tid.isDescendantOf($currentTid423))
                                    continue $label418;
                                if ($currentTid423.parent != null) {
                                    $retry420 = false;
                                    throw $e422;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e422) {
                                $commit419 = false;
                                if ($tm424.checkForStaleObjects())
                                    continue $label418;
                                $retry420 = false;
                                throw new fabric.worker.AbortException($e422);
                            }
                            finally {
                                if ($commit419) {
                                    fabric.common.TransactionID $currentTid423 =
                                      $tm424.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e422) {
                                        $commit419 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e422) {
                                        $commit419 = false;
                                        $retry420 = false;
                                        $keepReads421 = $e422.keepReads;
                                        if ($tm424.checkForStaleObjects()) {
                                            $retry420 = true;
                                            $keepReads421 = false;
                                            continue $label418;
                                        }
                                        if ($e422.tid ==
                                              null ||
                                              !$e422.tid.isDescendantOf(
                                                           $currentTid423))
                                            throw $e422;
                                        throw new fabric.worker.
                                                UserAbortException($e422);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e422) {
                                        $commit419 = false;
                                        $currentTid423 = $tm424.getCurrentTid();
                                        if ($currentTid423 != null) {
                                            if ($e422.tid.equals(
                                                            $currentTid423) ||
                                                  !$e422.tid.
                                                  isDescendantOf(
                                                    $currentTid423)) {
                                                throw $e422;
                                            }
                                        }
                                    }
                                } else if ($keepReads421) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit419) {
                                    { val = val$var417; }
                                    if ($retry420) { continue $label418; }
                                }
                            }
                        }
                    }
                }
                return val;
            }
            return fabric.metrics.Metric._Impl.static_plus(tmp, other);
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base,
                          fabric.worker.metrics.StatsMap weakStats,
                          final fabric.worker.Store s) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(term(0))) instanceof fabric.metrics.SampledMetric)
                return fabric.worker.metrics.treaties.enforcement.DirectPolicy.
                         singleton;
            long currentTime = java.lang.System.currentTimeMillis();
            double baseNow =
              fabric.metrics.contracts.Bound._Impl.value(rate, base,
                                                         currentTime);
            fabric.metrics.Metric m = term(0);
            rate = rate / this.get$scalar();
            baseNow = baseNow / this.get$scalar();
            if (this.get$scalar() < 0) {
                m = m.times(-1);
                baseNow = -baseNow;
                rate = -rate;
            }
            com.google.common.collect.Multimap witnesses =
              com.google.common.collect.HashMultimap.create();
            witnesses.
              put(
                (java.lang.Object) fabric.lang.WrappedJavaInlineable.$unwrap(m),
                fabric.worker.metrics.treaties.statements.ThresholdStatement.
                    create(rate, baseNow, currentTime));
            return fabric.worker.metrics.treaties.enforcement.WitnessPolicy.
              create(witnesses);
        }
        
        public int hashCode() {
            return java.util.Arrays.hashCode(this.get$terms().array()) * 32 +
              java.lang.Double.hashCode(this.get$scalar());
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.ScaledMetric) {
                fabric.metrics.ScaledMetric that =
                  (fabric.metrics.ScaledMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                return this.get$scalar() ==
                  that.get$scalar() &&
                  java.util.Arrays.deepEquals(this.get$terms().array(),
                                              that.get$terms().array()) &&
                  this.$getStore().equals(that.$getStore());
            }
            return false;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.ScaledMetric._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeDouble(this.scalar);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.scalar = in.readDouble();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.ScaledMetric._Impl src =
              (fabric.metrics.ScaledMetric._Impl) other;
            this.scalar = src.scalar;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.ScaledMetric._Static {
            public _Proxy(fabric.metrics.ScaledMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.ScaledMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  ScaledMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.ScaledMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.ScaledMetric._Static._Impl.class);
                $instance = (fabric.metrics.ScaledMetric._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.ScaledMetric._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.ScaledMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 36, -73, -13, -117, 54,
    -81, 13, -106, -127, 81, -88, -89, 115, -31, 115, -91, -14, 49, -112, -33,
    -73, 3, -30, -58, 87, -125, 71, 111, -42, 74, 42, 24 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549232444000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
