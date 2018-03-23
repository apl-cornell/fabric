package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.Set;
import fabric.metrics.DerivedMetric;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.common.TransactionID;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * A contract asserting that a {@link Metric}'s value is above or below a
 * vectorized boundary expression <code>r\u20d7 * (t - startTime) + b\u20d7</code> until
 * the associated expriation time.
 * <p>
 * This class follows the subject-observer pattern. An instance is an observer
 * of either a {@link Metric} or a set of {@link MetricEqualityContract}s and can be
 * observed by other {@link Contract}s.
 */
public interface MetricEqualityContract
  extends fabric.metrics.contracts.Contract {
    public fabric.metrics.Metric get$metric();
    
    public fabric.metrics.Metric set$metric(fabric.metrics.Metric val);
    
    public double get$value();
    
    public double set$value(double val);
    
    public double postInc$value();
    
    public double postDec$value();
    
    /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param value
   *        the value of the equality
   * @param base
   *        the base of the bound this {@link MetricEqualityContract} asserts on
   *        metric.
   */
    public fabric.metrics.contracts.MetricEqualityContract
      fabric$metrics$contracts$MetricEqualityContract$(
      fabric.metrics.Metric metric, double value);
    
    /** @return the {@link Metric} that this contract observes. */
    public fabric.metrics.Metric getMetric();
    
    public void activate();
    
    /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
    public boolean refresh(boolean asyncExtension);
    
    public boolean implies(fabric.metrics.Metric otherMetric, double otherRate,
                           double otherBase);
    
    public java.lang.String toString();
    
    public fabric.lang.arrays.ObjectArray getLeafSubjects();
    
    public static class _Proxy extends fabric.metrics.contracts.Contract._Proxy
      implements fabric.metrics.contracts.MetricEqualityContract {
        public fabric.metrics.Metric get$metric() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).get$metric();
        }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).set$metric(val);
        }
        
        public double get$value() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).get$value();
        }
        
        public double set$value(double val) {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).set$value(val);
        }
        
        public double postInc$value() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).postInc$value();
        }
        
        public double postDec$value() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).postDec$value();
        }
        
        public fabric.metrics.contracts.MetricEqualityContract
          fabric$metrics$contracts$MetricEqualityContract$(
          fabric.metrics.Metric arg1, double arg2) {
            return ((fabric.metrics.contracts.MetricEqualityContract) fetch()).
              fabric$metrics$contracts$MetricEqualityContract$(arg1, arg2);
        }
        
        public fabric.metrics.Metric getMetric() {
            return ((fabric.metrics.contracts.MetricEqualityContract) fetch()).
              getMetric();
        }
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
            return ((fabric.metrics.contracts.MetricEqualityContract) fetch()).
              getLeafSubjects();
        }
        
        public _Proxy(MetricEqualityContract._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.contracts.Contract._Impl
      implements fabric.metrics.contracts.MetricEqualityContract {
        public fabric.metrics.Metric get$metric() { return this.metric; }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.metric = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.Metric metric;
        
        public double get$value() { return this.value; }
        
        public double set$value(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$value() {
            double tmp = this.get$value();
            this.set$value((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$value() {
            double tmp = this.get$value();
            this.set$value((double) (tmp - 1));
            return tmp;
        }
        
        public double value;
        
        /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param value
   *        the value of the equality
   * @param base
   *        the base of the bound this {@link MetricEqualityContract} asserts on
   *        metric.
   */
        public fabric.metrics.contracts.MetricEqualityContract
          fabric$metrics$contracts$MetricEqualityContract$(
          fabric.metrics.Metric metric, double value) {
            this.set$metric(metric);
            this.set$value((double) value);
            fabric$metrics$contracts$Contract$();
            return (fabric.metrics.contracts.MetricEqualityContract)
                     this.$getProxy();
        }
        
        /** @return the {@link Metric} that this contract observes. */
        public fabric.metrics.Metric getMetric() { return this.get$metric(); }
        
        public void activate() {
            fabric.metrics.contracts.MetricEqualityContract._Impl.
              static_activate((fabric.metrics.contracts.MetricEqualityContract)
                                this.$getProxy());
        }
        
        private static void static_activate(
          fabric.metrics.contracts.MetricEqualityContract tmp) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.isActivated()) return;
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm126 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled129 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff127 = 1;
                    boolean $doBackoff128 = true;
                    boolean $retry123 = true;
                    $label121: for (boolean $commit122 = false; !$commit122; ) {
                        if ($backoffEnabled129) {
                            if ($doBackoff128) {
                                if ($backoff127 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff127);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e124) {
                                            
                                        }
                                    }
                                }
                                if ($backoff127 < 5000) $backoff127 *= 2;
                            }
                            $doBackoff128 = $backoff127 <= 32 || !$doBackoff128;
                        }
                        $commit122 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e124) {
                            $commit122 = false;
                            continue $label121;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e124) {
                            $commit122 = false;
                            fabric.common.TransactionID $currentTid125 =
                              $tm126.getCurrentTid();
                            if ($e124.tid.isDescendantOf($currentTid125))
                                continue $label121;
                            if ($currentTid125.parent != null) {
                                $retry123 = false;
                                throw $e124;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e124) {
                            $commit122 = false;
                            if ($tm126.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid125 =
                              $tm126.getCurrentTid();
                            if ($e124.tid.isDescendantOf($currentTid125)) {
                                $retry123 = true;
                            }
                            else if ($currentTid125.parent != null) {
                                $retry123 = false;
                                throw $e124;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e124) {
                            $commit122 = false;
                            if ($tm126.checkForStaleObjects())
                                continue $label121;
                            $retry123 = false;
                            throw new fabric.worker.AbortException($e124);
                        }
                        finally {
                            if ($commit122) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e124) {
                                    $commit122 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e124) {
                                    $commit122 = false;
                                    fabric.common.TransactionID $currentTid125 =
                                      $tm126.getCurrentTid();
                                    if ($currentTid125 != null) {
                                        if ($e124.tid.equals($currentTid125) ||
                                              !$e124.tid.isDescendantOf(
                                                           $currentTid125)) {
                                            throw $e124;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit122 && $retry123) {
                                {  }
                                continue $label121;
                            }
                        }
                    }
                }
            }
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.set$currentPolicy(
                      tmp.get$metric().equalityPolicy(tmp.get$value(), false,
                                                      tmp.$getStore()));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm135 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled138 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff136 = 1;
                    boolean $doBackoff137 = true;
                    boolean $retry132 = true;
                    $label130: for (boolean $commit131 = false; !$commit131; ) {
                        if ($backoffEnabled138) {
                            if ($doBackoff137) {
                                if ($backoff136 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff136);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e133) {
                                            
                                        }
                                    }
                                }
                                if ($backoff136 < 5000) $backoff136 *= 2;
                            }
                            $doBackoff137 = $backoff136 <= 32 || !$doBackoff137;
                        }
                        $commit131 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().equalityPolicy(
                                                     tmp.get$value(), false,
                                                     tmp.$getStore()));
                        }
                        catch (final fabric.worker.RetryException $e133) {
                            $commit131 = false;
                            continue $label130;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e133) {
                            $commit131 = false;
                            fabric.common.TransactionID $currentTid134 =
                              $tm135.getCurrentTid();
                            if ($e133.tid.isDescendantOf($currentTid134))
                                continue $label130;
                            if ($currentTid134.parent != null) {
                                $retry132 = false;
                                throw $e133;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e133) {
                            $commit131 = false;
                            if ($tm135.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid134 =
                              $tm135.getCurrentTid();
                            if ($e133.tid.isDescendantOf($currentTid134)) {
                                $retry132 = true;
                            }
                            else if ($currentTid134.parent != null) {
                                $retry132 = false;
                                throw $e133;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e133) {
                            $commit131 = false;
                            if ($tm135.checkForStaleObjects())
                                continue $label130;
                            $retry132 = false;
                            throw new fabric.worker.AbortException($e133);
                        }
                        finally {
                            if ($commit131) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e133) {
                                    $commit131 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e133) {
                                    $commit131 = false;
                                    fabric.common.TransactionID $currentTid134 =
                                      $tm135.getCurrentTid();
                                    if ($currentTid134 != null) {
                                        if ($e133.tid.equals($currentTid134) ||
                                              !$e133.tid.isDescendantOf(
                                                           $currentTid134)) {
                                            throw $e133;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit131 && $retry132) {
                                {  }
                                continue $label130;
                            }
                        }
                    }
                }
            }
            tmp.get$currentPolicy().activate();
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.metrics.contracts.Contract._Impl.static_activate(tmp);
                tmp.getMetric().addContract(tmp);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm144 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled147 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff145 = 1;
                    boolean $doBackoff146 = true;
                    boolean $retry141 = true;
                    $label139: for (boolean $commit140 = false; !$commit140; ) {
                        if ($backoffEnabled147) {
                            if ($doBackoff146) {
                                if ($backoff145 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff145);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e142) {
                                            
                                        }
                                    }
                                }
                                if ($backoff145 < 5000) $backoff145 *= 2;
                            }
                            $doBackoff146 = $backoff145 <= 32 || !$doBackoff146;
                        }
                        $commit140 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e142) {
                            $commit140 = false;
                            continue $label139;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e142) {
                            $commit140 = false;
                            fabric.common.TransactionID $currentTid143 =
                              $tm144.getCurrentTid();
                            if ($e142.tid.isDescendantOf($currentTid143))
                                continue $label139;
                            if ($currentTid143.parent != null) {
                                $retry141 = false;
                                throw $e142;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e142) {
                            $commit140 = false;
                            if ($tm144.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid143 =
                              $tm144.getCurrentTid();
                            if ($e142.tid.isDescendantOf($currentTid143)) {
                                $retry141 = true;
                            }
                            else if ($currentTid143.parent != null) {
                                $retry141 = false;
                                throw $e142;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e142) {
                            $commit140 = false;
                            if ($tm144.checkForStaleObjects())
                                continue $label139;
                            $retry141 = false;
                            throw new fabric.worker.AbortException($e142);
                        }
                        finally {
                            if ($commit140) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e142) {
                                    $commit140 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e142) {
                                    $commit140 = false;
                                    fabric.common.TransactionID $currentTid143 =
                                      $tm144.getCurrentTid();
                                    if ($currentTid143 != null) {
                                        if ($e142.tid.equals($currentTid143) ||
                                              !$e142.tid.isDescendantOf(
                                                           $currentTid143)) {
                                            throw $e142;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit140 && $retry141) {
                                {  }
                                continue $label139;
                            }
                        }
                    }
                }
            }
        }
        
        /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
        public boolean refresh(boolean asyncExtension) {
            if (!isActivated()) { return false; }
            long currentTime = java.lang.System.currentTimeMillis();
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null)) {
                long curExpiry = this.get$currentPolicy().expiry();
                if (curExpiry >= currentTime) { return update(curExpiry); }
            }
            if (asyncExtension) return false;
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            acquireReconfigLocks();
            tm.markCoordination();
            fabric.metrics.contracts.enforcement.EnforcementPolicy oldPolicy =
              this.get$currentPolicy();
            fabric.metrics.contracts.enforcement.EnforcementPolicy newPolicy =
              this.get$metric().equalityPolicy(this.get$value(), $getStore());
            if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null) &&
                  !oldPolicy.equals(newPolicy))
                oldPolicy.unapply(
                            (fabric.metrics.contracts.MetricEqualityContract)
                              this.$getProxy());
            newPolicy.activate();
            long newExpiry = newPolicy.expiry();
            boolean result = update(newExpiry);
            if (newExpiry >= currentTime) {
                this.set$currentPolicy(newPolicy);
                this.get$currentPolicy().
                  apply((fabric.metrics.contracts.MetricEqualityContract)
                          this.$getProxy());
            } else {
                this.set$currentPolicy(null);
            }
            return result;
        }
        
        public boolean implies(fabric.metrics.Metric otherMetric,
                               double otherRate, double otherBase) {
            if (!getMetric().equals(otherMetric) ||
                  !fabric.metrics.contracts.Bound._Impl.
                  test(otherRate, otherBase, this.get$value(),
                       java.lang.System.currentTimeMillis()))
                return false;
            return valid();
        }
        
        public java.lang.String toString() {
            return ((java.lang.Comparable)
                      fabric.lang.WrappedJavaInlineable.$unwrap(getMetric())).
              toString() + " == " + this.get$value() + " until " + getExpiry();
        }
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
            fabric.metrics.Metric m = getMetric();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(m)) instanceof fabric.metrics.SampledMetric) {
                final fabric.worker.Store local =
                  fabric.worker.Worker.getWorker().getLocalStore();
                return fabric.lang.arrays.internal.Compat.
                  convert(
                    local,
                    this.get$$updateLabel(),
                    this.get$$updateLabel().confPolicy(),
                    new fabric.lang.Object[] { (fabric.metrics.SampledMetric)
                                                 fabric.lang.Object._Proxy.
                                                 $getProxy(m) });
            }
            else if (fabric.lang.Object._Proxy.
                       $getProxy(
                         (java.lang.Object)
                           fabric.lang.WrappedJavaInlineable.
                           $unwrap(
                             m)) instanceof fabric.metrics.DerivedMetric) {
                return ((fabric.metrics.DerivedMetric)
                          fabric.lang.Object._Proxy.$getProxy(m)).
                  getLeafSubjects();
            }
            else {
                throw new java.lang.IllegalStateException(
                        "All metrics should be either sampled or derived!");
            }
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.MetricEqualityContract._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.metric, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            out.writeDouble(this.value);
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
            this.metric = (fabric.metrics.Metric)
                            $readRef(fabric.metrics.Metric._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            this.value = in.readDouble();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.MetricEqualityContract._Impl src =
              (fabric.metrics.contracts.MetricEqualityContract._Impl) other;
            this.metric = src.metric;
            this.value = src.value;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.MetricEqualityContract._Static {
            public _Proxy(fabric.metrics.contracts.MetricEqualityContract.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.MetricEqualityContract.
              _Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  MetricEqualityContract.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    MetricEqualityContract.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.MetricEqualityContract._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.MetricEqualityContract._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.MetricEqualityContract._Static {
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
                return new fabric.metrics.contracts.MetricEqualityContract.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 16, 79, -15, 107, 111,
    5, -13, 73, -3, 61, -102, -108, -110, 1, 77, -90, -74, 119, 117, 97, -110,
    -87, 10, -12, 5, -77, 99, -9, -55, 16, 59, 29 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521834376000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
