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
    
    public static final byte[] $classHash = new byte[] { -97, 10, -112, -67, 85,
    62, 87, -23, 33, 32, -49, 5, -37, -8, 68, -88, 102, 34, 1, 0, -94, -17, -6,
    113, -30, -30, 35, 99, 94, -86, -77, 26 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748453000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
