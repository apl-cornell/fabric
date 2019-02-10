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
                    fabric.metrics.DerivedMetric val$var362 = val;
                    fabric.worker.transaction.TransactionManager $tm369 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled372 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff370 = 1;
                    boolean $doBackoff371 = true;
                    boolean $retry365 = true;
                    boolean $keepReads366 = false;
                    $label363: for (boolean $commit364 = false; !$commit364; ) {
                        if ($backoffEnabled372) {
                            if ($doBackoff371) {
                                if ($backoff370 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff370));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e367) {
                                            
                                        }
                                    }
                                }
                                if ($backoff370 < 5000) $backoff370 *= 2;
                            }
                            $doBackoff371 = $backoff370 <= 32 || !$doBackoff371;
                        }
                        $commit364 = true;
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
                            catch (final fabric.worker.RetryException $e367) {
                                throw $e367;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e367) {
                                throw $e367;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e367) {
                                throw $e367;
                            }
                            catch (final Throwable $e367) {
                                $tm369.getCurrentLog().checkRetrySignal();
                                throw $e367;
                            }
                        }
                        catch (final fabric.worker.RetryException $e367) {
                            $commit364 = false;
                            continue $label363;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e367) {
                            $commit364 = false;
                            $retry365 = false;
                            $keepReads366 = $e367.keepReads;
                            if ($tm369.checkForStaleObjects()) {
                                $retry365 = true;
                                $keepReads366 = false;
                                continue $label363;
                            }
                            fabric.common.TransactionID $currentTid368 =
                              $tm369.getCurrentTid();
                            if ($e367.tid == null ||
                                  !$e367.tid.isDescendantOf($currentTid368)) {
                                throw $e367;
                            }
                            throw new fabric.worker.UserAbortException($e367);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e367) {
                            $commit364 = false;
                            fabric.common.TransactionID $currentTid368 =
                              $tm369.getCurrentTid();
                            if ($e367.tid.isDescendantOf($currentTid368))
                                continue $label363;
                            if ($currentTid368.parent != null) {
                                $retry365 = false;
                                throw $e367;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e367) {
                            $commit364 = false;
                            if ($tm369.checkForStaleObjects())
                                continue $label363;
                            $retry365 = false;
                            throw new fabric.worker.AbortException($e367);
                        }
                        finally {
                            if ($commit364) {
                                fabric.common.TransactionID $currentTid368 =
                                  $tm369.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e367) {
                                    $commit364 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e367) {
                                    $commit364 = false;
                                    $retry365 = false;
                                    $keepReads366 = $e367.keepReads;
                                    if ($tm369.checkForStaleObjects()) {
                                        $retry365 = true;
                                        $keepReads366 = false;
                                        continue $label363;
                                    }
                                    if ($e367.tid ==
                                          null ||
                                          !$e367.tid.isDescendantOf(
                                                       $currentTid368))
                                        throw $e367;
                                    throw new fabric.worker.UserAbortException(
                                            $e367);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e367) {
                                    $commit364 = false;
                                    $currentTid368 = $tm369.getCurrentTid();
                                    if ($currentTid368 != null) {
                                        if ($e367.tid.equals($currentTid368) ||
                                              !$e367.tid.isDescendantOf(
                                                           $currentTid368)) {
                                            throw $e367;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads366) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit364) {
                                { val = val$var362; }
                                if ($retry365) { continue $label363; }
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
                        fabric.metrics.DerivedMetric val$var373 = val;
                        fabric.worker.transaction.TransactionManager $tm380 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled383 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff381 = 1;
                        boolean $doBackoff382 = true;
                        boolean $retry376 = true;
                        boolean $keepReads377 = false;
                        $label374: for (boolean $commit375 = false; !$commit375;
                                        ) {
                            if ($backoffEnabled383) {
                                if ($doBackoff382) {
                                    if ($backoff381 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff381));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e378) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff381 < 5000) $backoff381 *= 2;
                                }
                                $doBackoff382 = $backoff381 <= 32 ||
                                                  !$doBackoff382;
                            }
                            $commit375 = true;
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
                                         RetryException $e378) {
                                    throw $e378;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e378) {
                                    throw $e378;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e378) {
                                    throw $e378;
                                }
                                catch (final Throwable $e378) {
                                    $tm380.getCurrentLog().checkRetrySignal();
                                    throw $e378;
                                }
                            }
                            catch (final fabric.worker.RetryException $e378) {
                                $commit375 = false;
                                continue $label374;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e378) {
                                $commit375 = false;
                                $retry376 = false;
                                $keepReads377 = $e378.keepReads;
                                if ($tm380.checkForStaleObjects()) {
                                    $retry376 = true;
                                    $keepReads377 = false;
                                    continue $label374;
                                }
                                fabric.common.TransactionID $currentTid379 =
                                  $tm380.getCurrentTid();
                                if ($e378.tid ==
                                      null ||
                                      !$e378.tid.isDescendantOf(
                                                   $currentTid379)) {
                                    throw $e378;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e378);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e378) {
                                $commit375 = false;
                                fabric.common.TransactionID $currentTid379 =
                                  $tm380.getCurrentTid();
                                if ($e378.tid.isDescendantOf($currentTid379))
                                    continue $label374;
                                if ($currentTid379.parent != null) {
                                    $retry376 = false;
                                    throw $e378;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e378) {
                                $commit375 = false;
                                if ($tm380.checkForStaleObjects())
                                    continue $label374;
                                $retry376 = false;
                                throw new fabric.worker.AbortException($e378);
                            }
                            finally {
                                if ($commit375) {
                                    fabric.common.TransactionID $currentTid379 =
                                      $tm380.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e378) {
                                        $commit375 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e378) {
                                        $commit375 = false;
                                        $retry376 = false;
                                        $keepReads377 = $e378.keepReads;
                                        if ($tm380.checkForStaleObjects()) {
                                            $retry376 = true;
                                            $keepReads377 = false;
                                            continue $label374;
                                        }
                                        if ($e378.tid ==
                                              null ||
                                              !$e378.tid.isDescendantOf(
                                                           $currentTid379))
                                            throw $e378;
                                        throw new fabric.worker.
                                                UserAbortException($e378);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e378) {
                                        $commit375 = false;
                                        $currentTid379 = $tm380.getCurrentTid();
                                        if ($currentTid379 != null) {
                                            if ($e378.tid.equals(
                                                            $currentTid379) ||
                                                  !$e378.tid.
                                                  isDescendantOf(
                                                    $currentTid379)) {
                                                throw $e378;
                                            }
                                        }
                                    }
                                } else if ($keepReads377) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit375) {
                                    { val = val$var373; }
                                    if ($retry376) { continue $label374; }
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
    
    public static final byte[] $classHash = new byte[] { -42, 34, -19, -55, -4,
    -60, -32, 37, -116, -38, 51, 3, 13, -58, 108, 47, -84, 127, 72, -24, 124,
    67, 9, -29, 50, -35, -74, -86, 90, -110, 65, 27 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748453000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
