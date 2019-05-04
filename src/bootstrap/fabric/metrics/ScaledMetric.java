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
    
    public double computePresetNTerm();
    
    public double computeValue(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeVelocity(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoise(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoiseTerm(fabric.worker.metrics.StatsMap weakStats);
    
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
        
        public double computePresetNTerm() {
            return this.get$scalar() * term(0).getPresetNTerm();
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
        
        public double computeNoiseTerm(
          fabric.worker.metrics.StatsMap weakStats) {
            return this.get$scalar() * term(0).noiseTerm(weakStats);
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
                    fabric.metrics.DerivedMetric val$var353 = val;
                    fabric.worker.transaction.TransactionManager $tm360 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled363 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff361 = 1;
                    boolean $doBackoff362 = true;
                    boolean $retry356 = true;
                    boolean $keepReads357 = false;
                    $label354: for (boolean $commit355 = false; !$commit355; ) {
                        if ($backoffEnabled363) {
                            if ($doBackoff362) {
                                if ($backoff361 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff361));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e358) {
                                            
                                        }
                                    }
                                }
                                if ($backoff361 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff361 =
                                      java.lang.Math.
                                        min(
                                          $backoff361 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff362 = $backoff361 <= 32 || !$doBackoff362;
                        }
                        $commit355 = true;
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
                        catch (final fabric.worker.RetryException $e358) {
                            $commit355 = false;
                            continue $label354;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e358) {
                            $commit355 = false;
                            $retry356 = false;
                            $keepReads357 = $e358.keepReads;
                            fabric.common.TransactionID $currentTid359 =
                              $tm360.getCurrentTid();
                            if ($e358.tid == null ||
                                  !$e358.tid.isDescendantOf($currentTid359)) {
                                throw $e358;
                            }
                            throw new fabric.worker.UserAbortException($e358);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e358) {
                            $commit355 = false;
                            fabric.common.TransactionID $currentTid359 =
                              $tm360.getCurrentTid();
                            if ($e358.tid.isDescendantOf($currentTid359))
                                continue $label354;
                            if ($currentTid359.parent != null) {
                                $retry356 = false;
                                throw $e358;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e358) {
                            $commit355 = false;
                            $retry356 = false;
                            if ($tm360.inNestedTxn()) { $keepReads357 = true; }
                            throw $e358;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid359 =
                              $tm360.getCurrentTid();
                            if ($commit355) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e358) {
                                    $commit355 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e358) {
                                    $commit355 = false;
                                    $retry356 = false;
                                    $keepReads357 = $e358.keepReads;
                                    if ($e358.tid ==
                                          null ||
                                          !$e358.tid.isDescendantOf(
                                                       $currentTid359))
                                        throw $e358;
                                    throw new fabric.worker.UserAbortException(
                                            $e358);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e358) {
                                    $commit355 = false;
                                    $currentTid359 = $tm360.getCurrentTid();
                                    if ($currentTid359 != null) {
                                        if ($e358.tid.equals($currentTid359) ||
                                              !$e358.tid.isDescendantOf(
                                                           $currentTid359)) {
                                            throw $e358;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm360.inNestedTxn() &&
                                      $tm360.checkForStaleObjects()) {
                                    $retry356 = true;
                                    $keepReads357 = false;
                                }
                                if ($keepReads357) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e358) {
                                        $currentTid359 = $tm360.getCurrentTid();
                                        if ($currentTid359 != null &&
                                              ($e358.tid.equals($currentTid359) || !$e358.tid.isDescendantOf($currentTid359))) {
                                            throw $e358;
                                        } else {
                                            $retry356 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit355) {
                                { val = val$var353; }
                                if ($retry356) { continue $label354; }
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
                        fabric.metrics.DerivedMetric val$var364 = val;
                        fabric.worker.transaction.TransactionManager $tm371 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled374 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff372 = 1;
                        boolean $doBackoff373 = true;
                        boolean $retry367 = true;
                        boolean $keepReads368 = false;
                        $label365: for (boolean $commit366 = false; !$commit366;
                                        ) {
                            if ($backoffEnabled374) {
                                if ($doBackoff373) {
                                    if ($backoff372 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff372));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e369) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff372 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff372 =
                                          java.lang.Math.
                                            min(
                                              $backoff372 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff373 = $backoff372 <= 32 ||
                                                  !$doBackoff373;
                            }
                            $commit366 = true;
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
                            catch (final fabric.worker.RetryException $e369) {
                                $commit366 = false;
                                continue $label365;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e369) {
                                $commit366 = false;
                                $retry367 = false;
                                $keepReads368 = $e369.keepReads;
                                fabric.common.TransactionID $currentTid370 =
                                  $tm371.getCurrentTid();
                                if ($e369.tid ==
                                      null ||
                                      !$e369.tid.isDescendantOf(
                                                   $currentTid370)) {
                                    throw $e369;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e369);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e369) {
                                $commit366 = false;
                                fabric.common.TransactionID $currentTid370 =
                                  $tm371.getCurrentTid();
                                if ($e369.tid.isDescendantOf($currentTid370))
                                    continue $label365;
                                if ($currentTid370.parent != null) {
                                    $retry367 = false;
                                    throw $e369;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e369) {
                                $commit366 = false;
                                $retry367 = false;
                                if ($tm371.inNestedTxn()) {
                                    $keepReads368 = true;
                                }
                                throw $e369;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid370 =
                                  $tm371.getCurrentTid();
                                if ($commit366) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e369) {
                                        $commit366 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e369) {
                                        $commit366 = false;
                                        $retry367 = false;
                                        $keepReads368 = $e369.keepReads;
                                        if ($e369.tid ==
                                              null ||
                                              !$e369.tid.isDescendantOf(
                                                           $currentTid370))
                                            throw $e369;
                                        throw new fabric.worker.
                                                UserAbortException($e369);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e369) {
                                        $commit366 = false;
                                        $currentTid370 = $tm371.getCurrentTid();
                                        if ($currentTid370 != null) {
                                            if ($e369.tid.equals(
                                                            $currentTid370) ||
                                                  !$e369.tid.
                                                  isDescendantOf(
                                                    $currentTid370)) {
                                                throw $e369;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm371.inNestedTxn() &&
                                          $tm371.checkForStaleObjects()) {
                                        $retry367 = true;
                                        $keepReads368 = false;
                                    }
                                    if ($keepReads368) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e369) {
                                            $currentTid370 = $tm371.getCurrentTid();
                                            if ($currentTid370 != null &&
                                                  ($e369.tid.equals($currentTid370) || !$e369.tid.isDescendantOf($currentTid370))) {
                                                throw $e369;
                                            } else {
                                                $retry367 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit366) {
                                    { val = val$var364; }
                                    if ($retry367) { continue $label365; }
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
    
    public static final byte[] $classHash = new byte[] { -123, 31, -33, 78, 57,
    120, 124, -116, 29, 88, 38, 84, 82, -27, 8, -50, -110, -5, 48, 52, 119,
    -128, 110, 52, -126, 12, 43, -101, 20, -53, -85, -97 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556815009000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
