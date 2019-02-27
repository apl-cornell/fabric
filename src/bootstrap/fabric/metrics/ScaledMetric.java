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
    
    public static final byte[] $classHash = new byte[] { -110, -97, -104, -35,
    -66, -52, -26, 37, 115, -65, 107, -33, 33, -18, 51, 71, -10, 31, 31, -74,
    -66, -3, 15, -73, 26, -112, 54, -99, -95, 114, -88, 17 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1550000445000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
