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
      thresholdPolicy(double rate, double base, long time,
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
                    long $backoff370 = 1;
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
                                if ($backoff370 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff370 =
                                      java.lang.Math.
                                        min(
                                          $backoff370 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff371 = $backoff370 <= 32 || !$doBackoff371;
                        }
                        $commit364 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            val =
                              ((fabric.metrics.ScaledMetric)
                                 new fabric.metrics.ScaledMetric._Impl(s).
                                 $getProxy()).fabric$metrics$ScaledMetric$(
                                                otherScalar * tmp.get$scalar(),
                                                tmp.term(0));
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
                            $retry365 = false;
                            if ($tm369.inNestedTxn()) { $keepReads366 = true; }
                            throw $e367;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid368 =
                              $tm369.getCurrentTid();
                            if ($commit364) {
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
                            } else {
                                if (!$tm369.inNestedTxn() &&
                                      $tm369.checkForStaleObjects()) {
                                    $retry365 = true;
                                    $keepReads366 = false;
                                }
                                if ($keepReads366) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e367) {
                                        $currentTid368 = $tm369.getCurrentTid();
                                        if ($currentTid368 != null &&
                                              ($e367.tid.equals($currentTid368) || !$e367.tid.isDescendantOf($currentTid368))) {
                                            throw $e367;
                                        } else {
                                            $retry365 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
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
                        long $backoff381 = 1;
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
                                    if ($backoff381 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff381 =
                                          java.lang.Math.
                                            min(
                                              $backoff381 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff382 = $backoff381 <= 32 ||
                                                  !$doBackoff382;
                            }
                            $commit375 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                val =
                                  ((fabric.metrics.ScaledMetric)
                                     new fabric.metrics.ScaledMetric._Impl(s).
                                     $getProxy()).fabric$metrics$ScaledMetric$(
                                                    that.get$scalar() +
                                                        tmp.get$scalar(),
                                                    tmp.term(0));
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
                                $retry376 = false;
                                if ($tm380.inNestedTxn()) {
                                    $keepReads377 = true;
                                }
                                throw $e378;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid379 =
                                  $tm380.getCurrentTid();
                                if ($commit375) {
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
                                } else {
                                    if (!$tm380.inNestedTxn() &&
                                          $tm380.checkForStaleObjects()) {
                                        $retry376 = true;
                                        $keepReads377 = false;
                                    }
                                    if ($keepReads377) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e378) {
                                            $currentTid379 = $tm380.getCurrentTid();
                                            if ($currentTid379 != null &&
                                                  ($e378.tid.equals($currentTid379) || !$e378.tid.isDescendantOf($currentTid379))) {
                                                throw $e378;
                                            } else {
                                                $retry376 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
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
          thresholdPolicy(double rate, double base, long time,
                          fabric.worker.metrics.StatsMap weakStats,
                          final fabric.worker.Store s) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(term(0))) instanceof fabric.metrics.SampledMetric)
                return fabric.worker.metrics.treaties.enforcement.DirectPolicy.
                         singleton;
            double baseNow = fabric.metrics.contracts.Bound._Impl.value(rate,
                                                                        base,
                                                                        time);
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
                    create(rate, baseNow, time));
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
    
    public static final byte[] $classHash = new byte[] { 19, 25, 13, -87, 48,
    57, 78, -98, 28, -95, 40, 58, 103, 45, 22, -111, 82, -114, 29, -103, 116,
    -51, -31, -85, 45, -42, -13, 18, -1, -109, -33, -125 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556552347000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
