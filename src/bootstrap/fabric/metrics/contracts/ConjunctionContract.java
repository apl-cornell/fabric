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
                    fabric.worker.transaction.TransactionManager $tm405 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled408 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff406 = 1;
                    boolean $doBackoff407 = true;
                    boolean $retry402 = true;
                    $label400: for (boolean $commit401 = false; !$commit401; ) {
                        if ($backoffEnabled408) {
                            if ($doBackoff407) {
                                if ($backoff406 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff406);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e403) {
                                            
                                        }
                                    }
                                }
                                if ($backoff406 < 5000) $backoff406 *= 2;
                            }
                            $doBackoff407 = $backoff406 <= 32 || !$doBackoff407;
                        }
                        $commit401 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved() && tmp.get$observing()) {
                                tmp.set$observing(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e403) {
                            $commit401 = false;
                            continue $label400;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e403) {
                            $commit401 = false;
                            fabric.common.TransactionID $currentTid404 =
                              $tm405.getCurrentTid();
                            if ($e403.tid.isDescendantOf($currentTid404))
                                continue $label400;
                            if ($currentTid404.parent != null) {
                                $retry402 = false;
                                throw $e403;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e403) {
                            $commit401 = false;
                            if ($tm405.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid404 =
                              $tm405.getCurrentTid();
                            if ($e403.tid.isDescendantOf($currentTid404)) {
                                $retry402 = true;
                            }
                            else if ($currentTid404.parent != null) {
                                $retry402 = false;
                                throw $e403;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e403) {
                            $commit401 = false;
                            if ($tm405.checkForStaleObjects())
                                continue $label400;
                            $retry402 = false;
                            throw new fabric.worker.AbortException($e403);
                        }
                        finally {
                            if ($commit401) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e403) {
                                    $commit401 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e403) {
                                    $commit401 = false;
                                    fabric.common.TransactionID $currentTid404 =
                                      $tm405.getCurrentTid();
                                    if ($currentTid404 != null) {
                                        if ($e403.tid.equals($currentTid404) ||
                                              !$e403.tid.isDescendantOf(
                                                           $currentTid404)) {
                                            throw $e403;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit401 && $retry402) {
                                {  }
                                continue $label400;
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
    public static final long jlc$SourceLastModified$fabil = 1526756043000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
