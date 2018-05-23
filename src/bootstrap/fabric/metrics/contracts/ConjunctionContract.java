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
    
    public static final byte[] $classHash = new byte[] { -87, -118, -88, -46,
    39, -76, -19, -15, 38, 12, -74, -110, -124, 95, 46, -74, 64, 122, -34, -77,
    -59, 127, -29, -73, 14, -44, -30, -72, 98, 108, -64, -107 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527094903000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
