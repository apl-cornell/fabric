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
    
    public static final byte[] $classHash = new byte[] { -106, -103, -102, -16,
    -111, -95, 13, 89, 124, -91, 104, 116, 66, 117, -110, -107, 13, 71, -73,
    124, 40, 111, -112, -9, 26, 103, 87, 127, 125, 78, -73, -8 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527094903000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
