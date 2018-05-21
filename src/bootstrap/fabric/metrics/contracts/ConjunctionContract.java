package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.Set;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
import fabric.common.TransactionID;
import fabric.worker.Store;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableContractsVector;
import fabric.worker.metrics.ImmutableSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * A contract asserting that a {@link Metric}'s value is above or below a
 * vectorized boundary expression <code>r\u20d7 * (t - startTime) + b\u20d7</code> until
 * the associated expriation time.
 * <p>
 * This class follows the subject-observer pattern. An instance is an observer
 * of either a {@link Metric} or a set of {@link ConjunctionContract}s and can be
 * observed by other {@link Contract}s.
 */
public interface ConjunctionContract extends fabric.metrics.contracts.Contract {
    public boolean get$observing();
    
    public boolean set$observing(boolean val);
    
    public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics();
    
    public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
      fabric.worker.metrics.ImmutableMetricsVector val);
    
    public fabric.worker.metrics.ImmutableContractsVector get$conjuncts();
    
    public fabric.worker.metrics.ImmutableContractsVector set$conjuncts(fabric.worker.metrics.ImmutableContractsVector val);
    
    /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param rate
   *        the rate of the bound this {@link ConjunctionContract} asserts on
   *        metric.
   * @param base
   *        the base of the bound this {@link ConjunctionContract} asserts on
   *        metric.
   */
    public fabric.metrics.contracts.ConjunctionContract
      fabric$metrics$contracts$ConjunctionContract$(fabric.metrics.contracts.Contract[] conjuncts);
    
    /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
    public boolean refresh(boolean asyncExtension,
                           fabric.worker.metrics.StatsMap weakStats);
    
    public boolean implies(fabric.metrics.Metric otherMetric, double otherRate,
                           double otherBase);
    
    public java.lang.String toString();
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
    /**
   * {@inheritDoc}
   *
   * Stops observing any evidence used by the current enforcement policy (by
   * unapplying the policy).
   */
    public void removeObserver(fabric.metrics.util.Observer obs);
    
    public static class _Proxy extends fabric.metrics.contracts.Contract._Proxy
      implements fabric.metrics.contracts.ConjunctionContract {
        public boolean get$observing() {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).get$observing();
        }
        
        public boolean set$observing(boolean val) {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).set$observing(val);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics() {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).get$leafMetrics();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).set$leafMetrics(val);
        }
        
        public fabric.worker.metrics.ImmutableContractsVector get$conjuncts() {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).get$conjuncts();
        }
        
        public fabric.worker.metrics.ImmutableContractsVector set$conjuncts(
          fabric.worker.metrics.ImmutableContractsVector val) {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).set$conjuncts(val);
        }
        
        public fabric.metrics.contracts.ConjunctionContract
          fabric$metrics$contracts$ConjunctionContract$(
          fabric.metrics.contracts.Contract[] arg1) {
            return ((fabric.metrics.contracts.ConjunctionContract) fetch()).
              fabric$metrics$contracts$ConjunctionContract$(arg1);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return ((fabric.metrics.contracts.ConjunctionContract) fetch()).
              getLeafSubjects();
        }
        
        public _Proxy(ConjunctionContract._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.contracts.Contract._Impl
      implements fabric.metrics.contracts.ConjunctionContract {
        public boolean get$observing() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.observing;
        }
        
        public boolean set$observing(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.observing = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private boolean observing;
        
        public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics() {
            return this.leafMetrics;
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.leafMetrics = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.worker.metrics.ImmutableMetricsVector leafMetrics;
        
        public fabric.worker.metrics.ImmutableContractsVector get$conjuncts() {
            return this.conjuncts;
        }
        
        public fabric.worker.metrics.ImmutableContractsVector set$conjuncts(
          fabric.worker.metrics.ImmutableContractsVector val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.conjuncts = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.worker.metrics.ImmutableContractsVector conjuncts;
        
        /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param rate
   *        the rate of the bound this {@link ConjunctionContract} asserts on
   *        metric.
   * @param base
   *        the base of the bound this {@link ConjunctionContract} asserts on
   *        metric.
   */
        public fabric.metrics.contracts.ConjunctionContract
          fabric$metrics$contracts$ConjunctionContract$(
          fabric.metrics.contracts.Contract[] conjuncts) {
            java.util.HashSet conjunctsBag = new java.util.HashSet();
            for (int i = 0; i < conjuncts.length; i++) {
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.$unwrap(conjuncts[i])) instanceof fabric.metrics.contracts.ConjunctionContract) {
                    fabric.metrics.contracts.ConjunctionContract other =
                      (fabric.metrics.contracts.ConjunctionContract)
                        fabric.lang.Object._Proxy.$getProxy(conjuncts[i]);
                    for (int j = 0; j < other.get$conjuncts().length(); j++) {
                        conjunctsBag.
                          add(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(other.get$conjuncts().get(j)));
                    }
                }
                else {
                    conjunctsBag.
                      add(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.$unwrap(
                                                              conjuncts[i]));
                }
            }
            final fabric.worker.Store s = this.$getStore();
            final fabric.lang.security.Label lbl =
              fabric.lang.security.LabelUtil._Impl.noComponents();
            fabric.metrics.contracts.Contract[] conjuncts1 =
              new fabric.metrics.contracts.Contract[conjunctsBag.size()];
            int idx = 0;
            for (java.util.Iterator it = conjunctsBag.iterator(); it.hasNext();
                 ) {
                fabric.metrics.contracts.Contract
                  c =
                  (fabric.metrics.contracts.Contract)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      fabric.lang.WrappedJavaInlineable.$wrap(it.next()));
                conjuncts1[idx++] = c;
            }
            this.
              set$conjuncts(
                fabric.worker.metrics.ImmutableContractsVector.createVector(
                                                                 conjuncts1));
            java.util.HashSet leavesBag = new java.util.HashSet();
            for (int i = 0; i < this.get$conjuncts().length(); i++) {
                fabric.worker.metrics.ImmutableMetricsVector leaves =
                  this.get$conjuncts().get(i).getLeafSubjects();
                for (int j = 0; j < leaves.length(); j++) {
                    leavesBag.
                      add(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.$unwrap(
                                                              leaves.get(j)));
                }
            }
            fabric.metrics.Metric[] leaves =
              new fabric.metrics.Metric[leavesBag.size()];
            idx = 0;
            for (java.util.Iterator iter = leavesBag.iterator(); iter.hasNext();
                 ) {
                leaves[idx++] =
                  (fabric.metrics.Metric)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      fabric.lang.WrappedJavaInlineable.$wrap(iter.next()));
            }
            this.set$leafMetrics(
                   fabric.worker.metrics.ImmutableMetricsVector.createVector(
                                                                  leaves));
            fabric$metrics$contracts$Contract$();
            this.set$observing(false);
            this.
              set$currentPolicy(
                ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                   new fabric.metrics.contracts.enforcement.WitnessPolicy._Impl(
                     this.$getStore()).$getProxy()).
                    fabric$metrics$contracts$enforcement$WitnessPolicy$(
                      conjuncts1));
            this.
              set$$associated(
                fabric.worker.metrics.ImmutableSet.emptySet().
                    add(this.get$currentPolicy()));
            return (fabric.metrics.contracts.ConjunctionContract)
                     this.$getProxy();
        }
        
        /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
        public boolean refresh(boolean asyncExtension,
                               fabric.worker.metrics.StatsMap weakStats) {
            long currentTime = java.lang.System.currentTimeMillis();
            if (this.get$observing()) {
                long curExpiry = this.get$currentPolicy().expiry(weakStats);
                if (curExpiry < currentTime) {
                    this.get$currentPolicy().
                      unapply((fabric.metrics.contracts.ConjunctionContract)
                                this.$getProxy());
                    this.set$currentPolicy(null);
                    this.set$$associated(null);
                    this.set$observing(false);
                }
                else {
                    this.get$currentPolicy().
                      apply((fabric.metrics.contracts.ConjunctionContract)
                              this.$getProxy());
                }
                return update(curExpiry, asyncExtension);
            }
            return false;
        }
        
        public boolean implies(fabric.metrics.Metric otherMetric,
                               double otherRate, double otherBase) {
            return false;
        }
        
        public java.lang.String toString() {
            java.lang.String result = "(";
            for (int i = 0; i < this.get$conjuncts().length(); i++) {
                if (i != 0) result += " ^ ";
                result += this.get$conjuncts().get(i).toString();
            }
            return result + ")";
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return this.get$leafMetrics();
        }
        
        /**
   * {@inheritDoc}
   *
   * Stops observing any evidence used by the current enforcement policy (by
   * unapplying the policy).
   */
        public void removeObserver(fabric.metrics.util.Observer obs) {
            fabric.metrics.contracts.ConjunctionContract._Impl.
              static_removeObserver(
                (fabric.metrics.contracts.ConjunctionContract) this.$getProxy(),
                obs);
        }
        
        private static void static_removeObserver(
          fabric.metrics.contracts.ConjunctionContract tmp,
          fabric.metrics.util.Observer obs) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.metrics.contracts.Contract._Impl.static_removeObserver(
                                                          tmp, obs);
                if (!tmp.isObserved() && tmp.get$observing()) {
                    tmp.set$observing(false);
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm377 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled380 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff378 = 1;
                    boolean $doBackoff379 = true;
                    boolean $retry374 = true;
                    $label372: for (boolean $commit373 = false; !$commit373; ) {
                        if ($backoffEnabled380) {
                            if ($doBackoff379) {
                                if ($backoff378 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff378);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e375) {
                                            
                                        }
                                    }
                                }
                                if ($backoff378 < 5000) $backoff378 *= 2;
                            }
                            $doBackoff379 = $backoff378 <= 32 || !$doBackoff379;
                        }
                        $commit373 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved() && tmp.get$observing()) {
                                tmp.set$observing(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e375) {
                            $commit373 = false;
                            continue $label372;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e375) {
                            $commit373 = false;
                            fabric.common.TransactionID $currentTid376 =
                              $tm377.getCurrentTid();
                            if ($e375.tid.isDescendantOf($currentTid376))
                                continue $label372;
                            if ($currentTid376.parent != null) {
                                $retry374 = false;
                                throw $e375;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e375) {
                            $commit373 = false;
                            if ($tm377.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid376 =
                              $tm377.getCurrentTid();
                            if ($e375.tid.isDescendantOf($currentTid376)) {
                                $retry374 = true;
                            }
                            else if ($currentTid376.parent != null) {
                                $retry374 = false;
                                throw $e375;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e375) {
                            $commit373 = false;
                            if ($tm377.checkForStaleObjects())
                                continue $label372;
                            $retry374 = false;
                            throw new fabric.worker.AbortException($e375);
                        }
                        finally {
                            if ($commit373) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e375) {
                                    $commit373 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e375) {
                                    $commit373 = false;
                                    fabric.common.TransactionID $currentTid376 =
                                      $tm377.getCurrentTid();
                                    if ($currentTid376 != null) {
                                        if ($e375.tid.equals($currentTid376) ||
                                              !$e375.tid.isDescendantOf(
                                                           $currentTid376)) {
                                            throw $e375;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit373 && $retry374) {
                                {  }
                                continue $label372;
                            }
                        }
                    }
                }
            }
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.ConjunctionContract._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeBoolean(this.observing);
            $writeInline(out, this.leafMetrics);
            $writeInline(out, this.conjuncts);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.observing = in.readBoolean();
            this.leafMetrics = (fabric.worker.metrics.ImmutableMetricsVector)
                                 in.readObject();
            this.conjuncts = (fabric.worker.metrics.ImmutableContractsVector)
                               in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.ConjunctionContract._Impl src =
              (fabric.metrics.contracts.ConjunctionContract._Impl) other;
            this.observing = src.observing;
            this.leafMetrics = src.leafMetrics;
            this.conjuncts = src.conjuncts;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.ConjunctionContract._Static {
            public _Proxy(fabric.metrics.contracts.ConjunctionContract._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.ConjunctionContract.
              _Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  ConjunctionContract.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    ConjunctionContract.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.ConjunctionContract._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.ConjunctionContract._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.ConjunctionContract._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.ConjunctionContract._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -103, -37, 86, 111,
    -51, -6, 83, -26, 68, 76, -76, 93, 105, 96, 14, -74, -51, 30, -15, 13, 58,
    -124, -37, -58, 74, 83, 7, -91, -70, -86, -104, 30 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526767069000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
