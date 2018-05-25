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
      fabric$metrics$contracts$ConjunctionContract$(
      fabric.metrics.contracts.Contract[] conjuncts);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy getNewPolicy(
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
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          getNewPolicy(fabric.worker.metrics.StatsMap weakStats) {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(this.$getStore()).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(
                this.get$conjuncts().array());
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
    
    public static final byte[] $classHash = new byte[] { -19, 65, 78, -70, -78,
    -90, 5, -28, 5, 37, -12, -64, 27, -58, -126, -21, 100, 104, 124, 30, 20, -4,
    -77, -73, 24, 53, 107, 25, -83, -84, -30, 59 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527094903000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
