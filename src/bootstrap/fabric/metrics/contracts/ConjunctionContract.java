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
    
    public static final byte[] $classHash = new byte[] { 62, -67, -88, -123, -3,
    -85, -10, 125, -124, 19, -90, -17, 11, 93, 109, -81, 50, 2, 96, -111, -58,
    48, 102, -32, 3, -2, 35, 96, -40, -101, -70, -27 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527094903000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
